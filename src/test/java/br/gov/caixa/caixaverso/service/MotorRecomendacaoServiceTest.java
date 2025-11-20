package br.gov.caixa.caixaverso.service;

import br.gov.caixa.caixaverso.enums.NivelRisco;
import br.gov.caixa.caixaverso.enums.PerfilCliente;
import br.gov.caixa.caixaverso.enums.TipoProduto;
import br.gov.caixa.caixaverso.exception.ProdutoDesseTipoNaoEncontradoException;
import br.gov.caixa.caixaverso.model.Cliente;
import br.gov.caixa.caixaverso.model.Produto;
import br.gov.caixa.caixaverso.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MotorRecomendacaoServiceTest {

    private ProdutoRepository produtoRepository;
    private MotorRecomendacaoService motorRecomendacaoService;

    @BeforeEach
    void setUp(){
        produtoRepository = Mockito.mock(ProdutoRepository.class);
        motorRecomendacaoService = new MotorRecomendacaoService(produtoRepository);
    }

    private Cliente setupCliente(Double baixo, Double medio, Double alto, int pontuacao) {
        Cliente cliente = new Cliente();
        cliente.setSaldoRiscoBaixo(baixo);
        cliente.setSaldoRiscoMedio(medio);
        cliente.setSaldoRiscoAlto(alto);
        cliente.setPontuacao(pontuacao);
        return cliente;
    }

    private Produto setupProduto(String nome, Integer pontuacaoIdeal) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setPontuacaoIdeal(pontuacaoIdeal);
        produto.setTipo(TipoProduto.CDB);
        return produto;
    }

    @Test
    void testDefinirPontuacaoZeroInvestimento() {
        Cliente cliente = setupCliente(0.0, 0.0, 0.0, 0);
        Integer pontuacao = motorRecomendacaoService.definirPontuacao(cliente);
        assertEquals(0, pontuacao);
    }

    @Test
    void testDefinirPontuacaoConservadorTotalmente() {
        Cliente cliente = setupCliente(1000.0, 0.0, 0.0, 0);
        Integer pontuacao = motorRecomendacaoService.definirPontuacao(cliente);
        assertEquals(0, pontuacao); // 100% * 1 (Arredonda para 0)
    }

    @Test
    void testDefinirPontuacaoModerado() {
        Cliente cliente = setupCliente(500.0, 500.0, 0.0, 0);
        Integer pontuacao = motorRecomendacaoService.definirPontuacao(cliente);
        // (0.5 * 1) + (0.5 * 50) = 0.5 + 25 = 25.5 -> 26
        assertEquals(26, pontuacao);
    }

    @Test
    void testDefinirPontuacaoAgressivo() {
        Cliente cliente = setupCliente(0.0, 500.0, 500.0, 0);
        Integer pontuacao = motorRecomendacaoService.definirPontuacao(cliente);
        // (0.5 * 50) + (0.5 * 100) = 25 + 50 = 75
        assertEquals(75, pontuacao);
    }

    @Test
    void testDefinirPontuacaoTotalmenteAgressivo() {
        Cliente cliente = setupCliente(0.0, 0.0, 1000.0, 0);
        Integer pontuacao = motorRecomendacaoService.definirPontuacao(cliente);
        assertEquals(100, pontuacao); // 100% * 100
    }

    @Test
    void testDefinirPerfilConservadorLimiteInferior() {
        PerfilCliente perfil = motorRecomendacaoService.definirPerfil(0);
        assertEquals(PerfilCliente.CONSERVADOR, perfil);
    }

    @Test
    void testDefinirPerfilConservadorLimiteSuperior() {
        PerfilCliente perfil = motorRecomendacaoService.definirPerfil(34);
        assertEquals(PerfilCliente.CONSERVADOR, perfil);
    }

    @Test
    void testDefinirPerfilModeradoLimiteInferior() {
        PerfilCliente perfil = motorRecomendacaoService.definirPerfil(35);
        assertEquals(PerfilCliente.MODERADO, perfil);
    }

    @Test
    void testDefinirPerfilModeradoLimiteSuperior() {
        PerfilCliente perfil = motorRecomendacaoService.definirPerfil(69);
        assertEquals(PerfilCliente.MODERADO, perfil);
    }

    @Test
    void testDefinirPerfilAgressivoLimiteInferior() {
        PerfilCliente perfil = motorRecomendacaoService.definirPerfil(70);
        assertEquals(PerfilCliente.AGRESSIVO, perfil);
    }

    @Test
    void testDefinirPerfilAgressivoLimiteSuperior() {
        PerfilCliente perfil = motorRecomendacaoService.definirPerfil(100);
        assertEquals(PerfilCliente.AGRESSIVO, perfil);
    }

    @Test
    void testBuscarProdutosRecomendadosPorPerfilConservador() {
        PerfilCliente perfil = PerfilCliente.CONSERVADOR;
        List<Produto> listaEsperada = Collections.singletonList(new Produto());

        when(produtoRepository.buscarPorRisco(NivelRisco.BAIXO)).thenReturn(listaEsperada);

        List<Produto> resultado = motorRecomendacaoService.buscarProdutosRecomendadosPorPerfil(perfil);

        verify(produtoRepository).buscarPorRisco(NivelRisco.BAIXO);
        assertEquals(listaEsperada, resultado);
    }

    @Test
    void testBuscarProdutosRecomendadosPorPerfilModerado() {
        PerfilCliente perfil = PerfilCliente.MODERADO;

        motorRecomendacaoService.buscarProdutosRecomendadosPorPerfil(perfil);

        verify(produtoRepository).buscarPorRisco(NivelRisco.MEDIO);
    }

    @Test
    void testBuscarProdutosRecomendadosPorPerfilAgressivo() {
        PerfilCliente perfil = PerfilCliente.AGRESSIVO;

        motorRecomendacaoService.buscarProdutosRecomendadosPorPerfil(perfil);

        verify(produtoRepository).buscarPorRisco(NivelRisco.ALTO);
    }

    @Test
    void testBuscarProdutosRecomendadosPorCliente() {
        Cliente cliente = setupCliente(100.0, 500.0, 400.0, 65);

        motorRecomendacaoService.buscarProdutosRecomendadosPorCliente(cliente);

        verify(produtoRepository).buscarPorPontuacao(65, 10);
    }

    @Test
    void testBuscarProdutoParaSimulacaoEncontrado() {
        Cliente cliente = setupCliente(0.0, 0.0, 0.0, 30); // Pontuacao 30
        TipoProduto tipo = TipoProduto.CDB;

        Produto prod1 = setupProduto("CDB Baixo", 10);
        Produto prod2 = setupProduto("CDB Médio", 50);
        Produto prod3 = setupProduto("CDB Perto", 40);

        List<Produto> produtos = Arrays.asList(prod1, prod2, prod3);
        when(produtoRepository.buscarPorTipo(tipo)).thenReturn(produtos);

        Produto produtoEscolhido = motorRecomendacaoService.buscarProdutoParaSimulacao(cliente, tipo);

        assertEquals(prod3.getNome(), produtoEscolhido.getNome());
    }

    @Test
    void testBuscarProdutoParaSimulacaoPontuacaoIgualAoPrimeiro() {
        Cliente cliente = setupCliente(0.0, 0.0, 0.0, 50); // Pontuacao 50
        TipoProduto tipo = TipoProduto.LCI;

        Produto prod1 = setupProduto("LCI Médio", 50); // Distância 0
        Produto prod2 = setupProduto("LCI Alto", 70);
        List<Produto> produtos = Arrays.asList(prod1, prod2);
        when(produtoRepository.buscarPorTipo(tipo)).thenReturn(produtos);

        Produto produtoEscolhido = motorRecomendacaoService.buscarProdutoParaSimulacao(cliente, tipo);

        assertEquals(prod1.getNome(), produtoEscolhido.getNome());
    }

    @Test
    void testBuscarProdutoParaSimulacaoListaVaziaLancaExcecao() {
        Cliente cliente = setupCliente(0.0, 0.0, 0.0, 50);
        TipoProduto tipo = TipoProduto.ACAO;

        when(produtoRepository.buscarPorTipo(tipo)).thenReturn(Collections.emptyList());

        assertThrows(ProdutoDesseTipoNaoEncontradoException.class, () ->
                motorRecomendacaoService.buscarProdutoParaSimulacao(cliente, tipo)
        );
    }
}