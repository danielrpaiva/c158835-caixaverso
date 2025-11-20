package br.gov.caixa.caixaverso.service;

import br.gov.caixa.caixaverso.dto.investimento.InvestimentoCreateDto;
import br.gov.caixa.caixaverso.dto.investimento.InvestimentoRetornoDto;
import br.gov.caixa.caixaverso.enums.NivelRisco;
import br.gov.caixa.caixaverso.enums.PerfilCliente;
import br.gov.caixa.caixaverso.enums.TipoProduto;
import br.gov.caixa.caixaverso.exception.ClienteNaoEncontradoException;
import br.gov.caixa.caixaverso.exception.ProdutoNaoEncontradoException;
import br.gov.caixa.caixaverso.mapper.InvestimentoMapper;
import br.gov.caixa.caixaverso.model.Cliente;
import br.gov.caixa.caixaverso.model.Investimento;
import br.gov.caixa.caixaverso.model.Produto;
import br.gov.caixa.caixaverso.repository.ClienteRepository;
import br.gov.caixa.caixaverso.repository.InvestimentoRepository;
import br.gov.caixa.caixaverso.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InvestimentoServiceTest {

    private InvestimentoService investimentoService;
    private MotorRecomendacaoService motorRecomendacaoService;
    private InvestimentoRepository investimentoRepository;
    private ProdutoRepository produtoRepository;
    private ClienteRepository clienteRepository;
    private InvestimentoMapper mapper;

    @BeforeEach
    void setUp(){
        motorRecomendacaoService = Mockito.mock(MotorRecomendacaoService.class);
        investimentoRepository = Mockito.mock(InvestimentoRepository.class);
        produtoRepository = Mockito.mock(ProdutoRepository.class);
        clienteRepository = Mockito.mock(ClienteRepository.class);
        mapper = Mockito.mock(InvestimentoMapper.class);

        investimentoService = new InvestimentoService(
                motorRecomendacaoService,
                investimentoRepository,
                produtoRepository,
                clienteRepository,
                mapper
        );
    }

    private Cliente setupCliente(Long id, PerfilCliente perfil, int pontuacao, Double saldoBaixo) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setPerfil(perfil);
        cliente.setPontuacao(pontuacao);
        cliente.setSaldoRiscoBaixo(saldoBaixo);
        cliente.setSaldoRiscoMedio(0.0);
        cliente.setSaldoRiscoAlto(0.0);
        return cliente;
    }

    private Produto setupProduto(Long id, NivelRisco risco) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setRisco(risco);
        produto.setTipo(TipoProduto.CDB);
        return produto;
    }

    private InvestimentoCreateDto setupInvestimentoCreateDto(Long clienteId, Long produtoId, Double valor, Double rentabilidade) {
        InvestimentoCreateDto dto = new InvestimentoCreateDto();
        dto.setClienteId(clienteId);
        dto.setProdutoId(produtoId);
        dto.setValor(valor);
        dto.setRentabilidade(rentabilidade);
        return dto;
    }

    @Test
    void testObterInvestimentosPorClienteSucesso() {
        Long clienteId = 1L;
        Cliente cliente = setupCliente(clienteId, PerfilCliente.CONSERVADOR, 10, 500.0);
        Investimento investimento = new Investimento();
        InvestimentoRetornoDto retornoDto = new InvestimentoRetornoDto();
        List<Investimento> investimentosList = Collections.singletonList(investimento);
        List<InvestimentoRetornoDto> retornoList = Collections.singletonList(retornoDto);

        when(clienteRepository.findById(clienteId)).thenReturn(cliente);
        when(investimentoRepository.buscarPorCliente(cliente)).thenReturn(investimentosList);
        when(mapper.toInvestimentoRetornoDtoList(investimentosList)).thenReturn(retornoList);

        List<InvestimentoRetornoDto> resultado = investimentoService.obterInvestimentosPorCliente(clienteId);

        assertEquals(1, resultado.size());
        assertEquals(retornoList, resultado);
        verify(investimentoRepository).buscarPorCliente(cliente);
    }

    @Test
    void testObterInvestimentosPorClienteClienteNaoEncontrado() {
        Long clienteId = 99L;
        when(clienteRepository.findById(clienteId)).thenReturn(null);

        assertThrows(ClienteNaoEncontradoException.class, () ->
                investimentoService.obterInvestimentosPorCliente(clienteId)
        );
    }

    @Test
    void testRegistrarNovoInvestimentoSucesso() {
        Long clienteId = 1L;
        Long produtoId = 10L;
        Double valorInvestido = 200.0;

        Cliente cliente = setupCliente(clienteId, PerfilCliente.CONSERVADOR, 10, 500.0);
        Produto produto = setupProduto(produtoId, NivelRisco.BAIXO);
        InvestimentoCreateDto dto = setupInvestimentoCreateDto(clienteId, produtoId, valorInvestido, 0.05);
        InvestimentoRetornoDto retornoEsperado = new InvestimentoRetornoDto();

        // Mocks de Repositório e Mapeamento
        when(produtoRepository.findById(produtoId)).thenReturn(produto);
        when(clienteRepository.findById(clienteId)).thenReturn(cliente);
        when(mapper.toInvestimentoRetornoDto(any(Investimento.class))).thenReturn(retornoEsperado);

        // Mocks do Motor de Recomendação
        when(motorRecomendacaoService.definirPontuacao(any(Cliente.class))).thenReturn(30);
        when(motorRecomendacaoService.definirPerfil(30)).thenReturn(PerfilCliente.MODERADO);

        // Simula a persistência (para garantir que o método seja chamado)
        doAnswer(invocation -> null).when(investimentoRepository).persist(any(Investimento.class));

        InvestimentoRetornoDto resultado = investimentoService.registrarNovoInvestimento(dto);

        // Verifica o retorno
        assertEquals(retornoEsperado, resultado);

        // Verifica a atualização dos saldos do cliente
        assertEquals(700.0, cliente.getSaldoRiscoBaixo());

        // Verifica a atualização do perfil e pontuação do cliente
        assertEquals(30, cliente.getPontuacao());
        assertEquals(PerfilCliente.MODERADO, cliente.getPerfil());

        // Verifica se a persistência e o motor de recomendação foram chamados
        verify(investimentoRepository).persist(any(Investimento.class));
        verify(motorRecomendacaoService).definirPontuacao(cliente);
    }

    @Test
    void testRegistrarNovoInvestimentoProdutoNaoEncontrado() {
        Long produtoId = 99L;
        InvestimentoCreateDto dto = setupInvestimentoCreateDto(1L, produtoId, 100.0, 0.05);

        when(produtoRepository.findById(produtoId)).thenReturn(null);

        assertThrows(ProdutoNaoEncontradoException.class, () ->
                investimentoService.registrarNovoInvestimento(dto)
        );
    }

    @Test
    void testRegistrarNovoInvestimentoClienteNaoEncontrado() {
        Long clienteId = 98L;
        Long produtoId = 10L;
        InvestimentoCreateDto dto = setupInvestimentoCreateDto(clienteId, produtoId, 100.0, 0.05);

        when(produtoRepository.findById(produtoId)).thenReturn(setupProduto(produtoId, NivelRisco.BAIXO));
        when(clienteRepository.findById(clienteId)).thenReturn(null); // Cliente não encontrado aqui

        assertThrows(ClienteNaoEncontradoException.class, () ->
                investimentoService.registrarNovoInvestimento(dto)
        );
    }
}