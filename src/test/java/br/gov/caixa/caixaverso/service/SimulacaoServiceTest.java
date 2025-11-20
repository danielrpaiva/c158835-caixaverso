package br.gov.caixa.caixaverso.service;

import br.gov.caixa.caixaverso.dto.simulacao.SimulacaoCreateDto;
import br.gov.caixa.caixaverso.dto.simulacao.SimulacaoCreateRetornoDto;
import br.gov.caixa.caixaverso.dto.simulacao.SimulacaoFiltroDto;
import br.gov.caixa.caixaverso.dto.simulacao.SimulacaoItemRetornoDto;
import br.gov.caixa.caixaverso.dto.simulacao.SimulacaoProdutoDiaRetornoDto;
import br.gov.caixa.caixaverso.enums.NivelRisco;
import br.gov.caixa.caixaverso.enums.TipoProduto;
import br.gov.caixa.caixaverso.exception.ClienteNaoEncontradoException;
import br.gov.caixa.caixaverso.exception.DataSimulacaoInvalidaException;
import br.gov.caixa.caixaverso.exception.TipoProdutoInvalidoException;
import br.gov.caixa.caixaverso.mapper.SimulacaoMapper;
import br.gov.caixa.caixaverso.model.Cliente;
import br.gov.caixa.caixaverso.model.Produto;
import br.gov.caixa.caixaverso.model.Simulacao;
import br.gov.caixa.caixaverso.repository.ClienteRepository;
import br.gov.caixa.caixaverso.repository.SimulacaoRepository;
import br.gov.caixa.caixaverso.util.Utilidades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SimulacaoServiceTest {

    private SimulacaoRepository simulacaoRepository;
    private ClienteRepository clienteRepository;
    private MotorRecomendacaoService motorRecomendacaoService;
    private SimulacaoMapper mapper;
    private SimulacaoService simulacaoService;

    @BeforeEach
    void setUp(){
        simulacaoRepository = Mockito.mock(SimulacaoRepository.class);
        clienteRepository = Mockito.mock(ClienteRepository.class);
        motorRecomendacaoService = Mockito.mock(MotorRecomendacaoService.class);
        mapper = Mockito.mock(SimulacaoMapper.class);

        simulacaoService = new SimulacaoService(
                simulacaoRepository,
                mapper,
                clienteRepository,
                motorRecomendacaoService
        );
    }

    private Cliente setupCliente(Long id) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        return cliente;
    }

    private Produto setupProduto(Long id, Double rentabilidade) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setRentabilidadeAnual(rentabilidade);
        produto.setRisco(NivelRisco.BAIXO);
        produto.setTipo(TipoProduto.CDB);
        return produto;
    }

    private SimulacaoCreateDto setupSimulacaoCreateDto(Long clienteId, String tipoProduto, Double valor, Integer prazo) {
        SimulacaoCreateDto dto = new SimulacaoCreateDto();
        dto.setClienteId(clienteId);
        dto.setTipoProduto(tipoProduto);
        dto.setValor(valor);
        dto.setPrazoMeses(prazo);
        return dto;
    }

    @Test
    void testBuscarSimulacoesSucesso() {
        SimulacaoFiltroDto filtro = new SimulacaoFiltroDto();
        Simulacao simulacao = new Simulacao();
        SimulacaoItemRetornoDto retornoDto = new SimulacaoItemRetornoDto();
        List<Simulacao> simulacoesList = Collections.singletonList(simulacao);
        List<SimulacaoItemRetornoDto> retornoList = Collections.singletonList(retornoDto);

        when(simulacaoRepository.buscarComFiltro(filtro)).thenReturn(simulacoesList);
        when(mapper.toSimulacaoRetornoList(simulacoesList)).thenReturn(retornoList);

        List<SimulacaoItemRetornoDto> resultado = simulacaoService.buscarSimulacoes(filtro);

        assertEquals(1, resultado.size());
        verify(simulacaoRepository).buscarComFiltro(filtro);
    }

    @Test
    void testObterSimulacoesPorProdutoEDiaComData() {
        String dataString = "2024-05-15";
        LocalDate dataConsulta = LocalDate.parse(dataString);
        Simulacao simulacao = new Simulacao();
        SimulacaoProdutoDiaRetornoDto retornoDto = new SimulacaoProdutoDiaRetornoDto();
        List<Simulacao> simulacoesList = Collections.singletonList(simulacao);
        List<SimulacaoProdutoDiaRetornoDto> retornoList = Collections.singletonList(retornoDto);

        when(simulacaoRepository.buscarPorData(dataConsulta)).thenReturn(simulacoesList);
        when(mapper.toSimulacaoProdutoDiaRetornoList(simulacoesList, dataString)).thenReturn(retornoList);

        List<SimulacaoProdutoDiaRetornoDto> resultado = simulacaoService.obterSimulacoesPorProdutoEDia(dataString);

        assertEquals(1, resultado.size());
        verify(simulacaoRepository).buscarPorData(dataConsulta);
    }

    @Test
    void testObterSimulacoesPorProdutoEDiaSemDataUsaHoje() {
        String dataString = LocalDate.now().toString();

        when(simulacaoRepository.buscarPorData(any(LocalDate.class))).thenReturn(Collections.emptyList());
        when(mapper.toSimulacaoProdutoDiaRetornoList(any(), anyString())).thenReturn(Collections.emptyList());

        simulacaoService.obterSimulacoesPorProdutoEDia(null);

        verify(simulacaoRepository).buscarPorData(LocalDate.now());
    }

    @Test
    void testObterSimulacoesPorProdutoEDiaFormatoDataInvalidoLancaExcecao() {
        String dataInvalida = "15/05/2024";

        assertThrows(DataSimulacaoInvalidaException.class, () ->
                simulacaoService.obterSimulacoesPorProdutoEDia(dataInvalida)
        );
    }

    @Test
    void testRegistrarSimulacaoSucesso() {
        Long clienteId = 1L;
        String tipoProdutoString = "CDB";
        Double valor = 1000.0;
        Integer prazo = 12;
        Double rentabilidade = 0.10;
        LocalDateTime dataSimulacao = LocalDateTime.now();

        SimulacaoCreateDto dto = setupSimulacaoCreateDto(clienteId, tipoProdutoString, valor, prazo);
        Cliente cliente = setupCliente(clienteId);
        Produto produto = setupProduto(10L, rentabilidade);
        SimulacaoCreateRetornoDto retornoEsperado = new SimulacaoCreateRetornoDto();

        try (MockedStatic<TipoProduto> tipoProdutoMock = mockStatic(TipoProduto.class);
             MockedStatic<Utilidades> utilidadesMock = mockStatic(Utilidades.class)) {

            tipoProdutoMock.when(() -> TipoProduto.obterTipoProduto(tipoProdutoString)).thenReturn(TipoProduto.CDB);
            when(clienteRepository.findById(clienteId)).thenReturn(cliente);
            when(motorRecomendacaoService.buscarProdutoParaSimulacao(cliente, TipoProduto.CDB)).thenReturn(produto);

            utilidadesMock.when(() -> Utilidades.calcularValorFinal(anyDouble(), anyInt(), anyDouble())).thenReturn(1104.71);
            utilidadesMock.when(() -> Utilidades.calcularRentabilidadeEfetiva(anyDouble(), anyInt(), anyDouble())).thenReturn(0.1047);

            doAnswer(invocation -> null).when(simulacaoRepository).persist(any(Simulacao.class));
            when(mapper.toSimulacaoCreateRetornoDto(any(Simulacao.class))).thenReturn(retornoEsperado);

            SimulacaoCreateRetornoDto resultado = simulacaoService.registrarSimulacao(dto);

            assertEquals(retornoEsperado, resultado);
            verify(simulacaoRepository).persist(any(Simulacao.class));

            utilidadesMock.verify(() -> Utilidades.calcularValorFinal(valor, prazo, rentabilidade));
            utilidadesMock.verify(() -> Utilidades.calcularRentabilidadeEfetiva(valor, prazo, rentabilidade));
        }
    }

    @Test
    void testRegistrarSimulacaoTipoProdutoInvalido() {
        Long clienteId = 1L;
        String tipoInvalido = "INVALIDO";
        SimulacaoCreateDto dto = setupSimulacaoCreateDto(clienteId, tipoInvalido, 100.0, 12);

        try (MockedStatic<TipoProduto> tipoProdutoMock = mockStatic(TipoProduto.class)) {
            tipoProdutoMock.when(() -> TipoProduto.obterTipoProduto(tipoInvalido)).thenReturn(null);

            assertThrows(TipoProdutoInvalidoException.class, () ->
                    simulacaoService.registrarSimulacao(dto)
            );
        }
    }

    @Test
    void testRegistrarSimulacaoClienteNaoEncontrado() {
        Long clienteId = 99L;
        String tipoProdutoString = "LCA";
        SimulacaoCreateDto dto = setupSimulacaoCreateDto(clienteId, tipoProdutoString, 100.0, 12);

        try (MockedStatic<TipoProduto> tipoProdutoMock = mockStatic(TipoProduto.class)) {
            tipoProdutoMock.when(() -> TipoProduto.obterTipoProduto(tipoProdutoString)).thenReturn(TipoProduto.LCA);
            when(clienteRepository.findById(clienteId)).thenReturn(null);

            assertThrows(ClienteNaoEncontradoException.class, () ->
                    simulacaoService.registrarSimulacao(dto)
            );
        }
    }
}