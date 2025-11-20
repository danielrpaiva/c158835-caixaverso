package br.gov.caixa.caixaverso.service;

import br.gov.caixa.caixaverso.dto.produto.ProdutoCreateDto;
import br.gov.caixa.caixaverso.dto.produto.ProdutoRetornoDto;
import br.gov.caixa.caixaverso.enums.NivelRisco;
import br.gov.caixa.caixaverso.enums.PerfilCliente;
import br.gov.caixa.caixaverso.enums.TipoProduto;
import br.gov.caixa.caixaverso.exception.ClienteNaoEncontradoException;
import br.gov.caixa.caixaverso.mapper.ProdutoMapper;
import br.gov.caixa.caixaverso.model.Cliente;
import br.gov.caixa.caixaverso.model.Produto;
import br.gov.caixa.caixaverso.repository.ClienteRepository;
import br.gov.caixa.caixaverso.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProdutoServiceTest {

    private ProdutoRepository produtoRepository;
    private ClienteRepository clienteRepository;
    private MotorRecomendacaoService motorRecomendacaoService;
    private ProdutoMapper mapper;
    private ProdutoService produtoService;

    @BeforeEach
    void setUp(){
        produtoRepository = Mockito.mock(ProdutoRepository.class);
        clienteRepository = Mockito.mock(ClienteRepository.class);
        motorRecomendacaoService = Mockito.mock(MotorRecomendacaoService.class);
        mapper = Mockito.mock(ProdutoMapper.class);

        produtoService = new ProdutoService(
                produtoRepository,
                clienteRepository,
                mapper,
                motorRecomendacaoService
        );
    }

    private ProdutoCreateDto setupProdutoCreateDto() {
        ProdutoCreateDto dto = new ProdutoCreateDto();
        dto.setNome("Ação XPTO");
        dto.setTipo(TipoProduto.ACAO);
        dto.setRisco(NivelRisco.ALTO);
        dto.setRentabilidadeAnual(0.15);
        dto.setPontuacaoIdeal(85);
        return dto;
    }

    private Produto setupProduto(Long id, String nome, TipoProduto tipo) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(nome);
        produto.setTipo(tipo);
        return produto;
    }

    private ProdutoRetornoDto setupProdutoRetornoDto(Long id) {
        ProdutoRetornoDto dto = new ProdutoRetornoDto();
        dto.setId(id);
        dto.setNome("Produto Mocado");
        return dto;
    }

    @Test
    void testCriarProdutoSucesso() {
        ProdutoCreateDto dto = setupProdutoCreateDto();
        ProdutoRetornoDto retornoEsperado = setupProdutoRetornoDto(1L);

        doAnswer(invocation -> {
            Produto p = invocation.getArgument(0);
            p.setId(1L);
            return null;
        }).when(produtoRepository).persist(any(Produto.class));

        when(mapper.toProdutoRetornoDto(any(Produto.class))).thenReturn(retornoEsperado);

        ProdutoRetornoDto resultado = produtoService.criarProduto(dto);

        assertNotNull(resultado.getId());
        assertEquals(1L, resultado.getId());
        verify(produtoRepository).persist(any(Produto.class));
        verify(mapper).toProdutoRetornoDto(any(Produto.class));
    }

    @Test
    void testListarProdutosSucesso() {
        List<Produto> produtos = Collections.singletonList(setupProduto(1L, "CDB", TipoProduto.CDB));
        List<ProdutoRetornoDto> dtosEsperados = Collections.singletonList(setupProdutoRetornoDto(1L));

        when(produtoRepository.listAll()).thenReturn(produtos);
        when(mapper.toProdutoRetornoDtoList(produtos)).thenReturn(dtosEsperados);

        List<ProdutoRetornoDto> resultado = produtoService.listarProdutos();

        assertEquals(1, resultado.size());
        assertEquals(dtosEsperados, resultado);
        verify(produtoRepository).listAll();
    }

    @Test
    void testListarProdutosRecomendadosPerfilSucesso() {
        PerfilCliente perfil = PerfilCliente.MODERADO;
        List<Produto> produtosRecomendados = Collections.singletonList(setupProduto(2L, "Fundo", TipoProduto.FUNDO_DE_INVESTIMENTO));
        List<ProdutoRetornoDto> dtosEsperados = Collections.singletonList(setupProdutoRetornoDto(2L));

        when(motorRecomendacaoService.buscarProdutosRecomendadosPorPerfil(perfil)).thenReturn(produtosRecomendados);
        when(mapper.toProdutoRetornoDtoList(produtosRecomendados)).thenReturn(dtosEsperados);

        List<ProdutoRetornoDto> resultado = produtoService.listarProdutosRecomendadosPerfil(perfil);

        assertEquals(1, resultado.size());
        verify(motorRecomendacaoService).buscarProdutosRecomendadosPorPerfil(perfil);
    }

    @Test
    void testListarProdutosRecomendadosClienteSucesso() {
        Long clienteId = 10L;
        Cliente cliente = new Cliente();

        List<Produto> produtosRecomendados = Collections.singletonList(setupProduto(3L, "LCI", TipoProduto.LCI));
        List<ProdutoRetornoDto> dtosEsperados = Collections.singletonList(setupProdutoRetornoDto(3L));

        when(clienteRepository.findById(clienteId)).thenReturn(cliente);
        when(motorRecomendacaoService.buscarProdutosRecomendadosPorCliente(cliente)).thenReturn(produtosRecomendados);
        when(mapper.toProdutoRetornoDtoList(produtosRecomendados)).thenReturn(dtosEsperados);

        List<ProdutoRetornoDto> resultado = produtoService.listarProdutosRecomendadosCliente(clienteId);

        assertEquals(1, resultado.size());
        verify(clienteRepository).findById(clienteId);
        verify(motorRecomendacaoService).buscarProdutosRecomendadosPorCliente(cliente);
    }

    @Test
    void testListarProdutosRecomendadosClienteNaoEncontrado() {
        Long clienteId = 99L;

        when(clienteRepository.findById(anyLong())).thenReturn(null);

        assertThrows(ClienteNaoEncontradoException.class, () ->
                produtoService.listarProdutosRecomendadosCliente(clienteId)
        );
        verify(clienteRepository).findById(clienteId);
        // O motorRecomendacaoService não deve ser chamado
        verify(motorRecomendacaoService, Mockito.never()).buscarProdutosRecomendadosPorCliente(any(Cliente.class));
    }
}