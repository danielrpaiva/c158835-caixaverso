package br.gov.caixa.caixaverso.resource;

import br.gov.caixa.caixaverso.dto.produto.ProdutoCreateDto;
import br.gov.caixa.caixaverso.dto.produto.ProdutoRetornoDto;
import br.gov.caixa.caixaverso.enums.NivelRisco;
import br.gov.caixa.caixaverso.enums.PerfilCliente;
import br.gov.caixa.caixaverso.enums.TipoProduto;
import br.gov.caixa.caixaverso.service.ProdutoService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProdutoResourceTest {

    private ProdutoService produtoService;
    private ProdutoResource produtoResource;

    @BeforeEach
    void setUp(){
        produtoService = Mockito.mock(ProdutoService.class);
        produtoResource = new ProdutoResource(produtoService);
    }

    private ProdutoRetornoDto setupProdutoRetornoDto(Long id, String nome, NivelRisco risco, TipoProduto tipo) {
        return new ProdutoRetornoDto(
                id,
                nome,
                tipo,
                0.10,
                risco,
                50
        );
    }

    @Test
    void testAdicionarProduto() {
        ProdutoCreateDto inputDto = new ProdutoCreateDto(
                "Produto Novo",
                TipoProduto.CDB,
                0.12,
                NivelRisco.BAIXO,
                10
        );

        ProdutoRetornoDto retornoDtoEsperado = setupProdutoRetornoDto(1L, "Produto Novo", NivelRisco.BAIXO, TipoProduto.CDB);

        when(produtoService.criarProduto(inputDto)).thenReturn(retornoDtoEsperado);

        try (Response response = produtoResource.adicionarProduto(inputDto)) {
            ProdutoRetornoDto responseDto = (ProdutoRetornoDto) response.getEntity();

            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
            assertEquals(1L, responseDto.getId());
            assertEquals("Produto Novo", responseDto.getNome());
        }
    }

    @Test
    void testListarProdutos() {
        List<ProdutoRetornoDto> listaEsperada = Arrays.asList(
                setupProdutoRetornoDto(1L, "Produto A", NivelRisco.BAIXO, TipoProduto.LCI),
                setupProdutoRetornoDto(2L, "Produto B", NivelRisco.ALTO, TipoProduto.ACAO)
        );

        when(produtoService.listarProdutos()).thenReturn(listaEsperada);

        try (Response response = produtoResource.listarProdutos()) {
            List<ProdutoRetornoDto> responseList = (List<ProdutoRetornoDto>) response.getEntity();

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(2, responseList.size());
            assertEquals(NivelRisco.ALTO, responseList.get(1).getRisco());
        }
    }

    @Test
    void testListarProdutosRecomendadosPerfil() {
        PerfilCliente perfil = PerfilCliente.MODERADO;

        List<ProdutoRetornoDto> listaEsperada = Arrays.asList(
                setupProdutoRetornoDto(3L, "Fundo Moderado", NivelRisco.MEDIO, TipoProduto.FUNDO_DE_INVESTIMENTO)
        );

        when(produtoService.listarProdutosRecomendadosPerfil(perfil)).thenReturn(listaEsperada);

        try (Response response = produtoResource.listarProdutosRecomendadosPerfil(perfil)) {
            List<ProdutoRetornoDto> responseList = (List<ProdutoRetornoDto>) response.getEntity();

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(1, responseList.size());
            assertEquals("Fundo Moderado", responseList.getFirst().getNome());
        }
    }

    @Test
    void testListarProdutosRecomendadosCliente() {
        Long clienteId = 15L;

        List<ProdutoRetornoDto> listaEsperada = Arrays.asList(
                setupProdutoRetornoDto(4L, "Ação Exclusiva", NivelRisco.ALTO, TipoProduto.ACAO),
                setupProdutoRetornoDto(5L, "LCA Caixa", NivelRisco.BAIXO, TipoProduto.LCA)
        );

        when(produtoService.listarProdutosRecomendadosCliente(clienteId)).thenReturn(listaEsperada);

        try (Response response = produtoResource.listarProdutosRecomendadosCliente(clienteId)) {
            List<ProdutoRetornoDto> responseList = (List<ProdutoRetornoDto>) response.getEntity();

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals(2, responseList.size());
            assertEquals(4L, responseList.get(0).getId());
        }
    }
}