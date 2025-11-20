package br.gov.caixa.caixaverso.mapper;

import br.gov.caixa.caixaverso.dto.simulacao.ProdutoValidadoSimulacaoRetornoDto;
import br.gov.caixa.caixaverso.dto.simulacao.ResultadoSimulacaoRetornoDto;
import br.gov.caixa.caixaverso.dto.simulacao.SimulacaoCreateRetornoDto;
import br.gov.caixa.caixaverso.dto.simulacao.SimulacaoItemRetornoDto;
import br.gov.caixa.caixaverso.dto.simulacao.SimulacaoProdutoDiaRetornoDto;
import br.gov.caixa.caixaverso.enums.NivelRisco;
import br.gov.caixa.caixaverso.enums.TipoProduto;
import br.gov.caixa.caixaverso.model.Cliente;
import br.gov.caixa.caixaverso.model.Produto;
import br.gov.caixa.caixaverso.model.Simulacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SimulacaoMapperTest {

    private SimulacaoMapper mapper;

    @BeforeEach
    void setUp(){
        mapper = new SimulacaoMapper();
    }

    private Produto setupProduto(Long id, String nome, NivelRisco risco, TipoProduto tipo, Double rentabilidade) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(nome);
        produto.setRisco(risco);
        produto.setTipo(tipo);
        produto.setRentabilidadeAnual(rentabilidade);
        return produto;
    }

    private Cliente setupCliente(Long id) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        return cliente;
    }

    private Simulacao setupSimulacao(
            Long id, Cliente cliente, Produto produto, Double valorInvestido, Double valorFinal, Integer prazo, Double rentabilidadeEfetiva
    ) {
        Simulacao simulacao = new Simulacao();
        simulacao.setId(id);
        simulacao.setCliente(cliente);
        simulacao.setProduto(produto);
        simulacao.setValorInvestido(valorInvestido);
        simulacao.setValorFinal(valorFinal);
        simulacao.setPrazoMeses(prazo);
        simulacao.setRentabilidadeEfetiva(rentabilidadeEfetiva);
        simulacao.setDataSimulacao(LocalDateTime.of(2024, 10, 20, 10, 30));
        return simulacao;
    }

    @Test
    void testToSimulacaoRetornoList() {
        Produto produto = setupProduto(1L, "CDB 100", NivelRisco.BAIXO, TipoProduto.CDB, 0.10);
        Cliente cliente = setupCliente(5L);
        Simulacao simulacao = setupSimulacao(10L, cliente, produto, 1000.0, 1100.0, 12, 0.10);
        List<Simulacao> listaEntrada = Collections.singletonList(simulacao);

        List<SimulacaoItemRetornoDto> listaDto = mapper.toSimulacaoRetornoList(listaEntrada);

        assertNotNull(listaDto);
        assertEquals(1, listaDto.size());
        assertEquals(10L, listaDto.getFirst().getId());
        assertEquals(5L, listaDto.getFirst().getClienteId());
        assertEquals("CDB 100", listaDto.getFirst().getProduto());
        assertEquals(1100.0, listaDto.getFirst().getValorFinal());
        assertEquals(12, listaDto.getFirst().getPrazoMeses());
    }

    @Test
    void testToSimulacaoProdutoDiaRetornoListAgrupamentoEmedia() {
        String data = "2024-10-20";
        Cliente cliente = setupCliente(5L);

        Produto prodA = setupProduto(10L, "Fundo Médio", NivelRisco.MEDIO, TipoProduto.FUNDO_DE_INVESTIMENTO, 0.08);
        Simulacao simA1 = setupSimulacao(101L, cliente, prodA, 1000.0, 1080.0, 12, 0.08);
        Simulacao simA2 = setupSimulacao(102L, cliente, prodA, 2000.0, 2160.0, 12, 0.08);

        Produto prodB = setupProduto(20L, "Ação Qualquer", NivelRisco.ALTO, TipoProduto.ACAO, 0.20);
        Simulacao simB1 = setupSimulacao(103L, cliente, prodB, 500.0, 600.0, 12, 0.20);

        List<Simulacao> listaEntrada = Arrays.asList(simA1, simA2, simB1);

        List<SimulacaoProdutoDiaRetornoDto> listaDto = mapper.toSimulacaoProdutoDiaRetornoList(listaEntrada, data);

        assertNotNull(listaDto);
        assertEquals(2, listaDto.size());

        SimulacaoProdutoDiaRetornoDto dtoA = listaDto.stream().filter(
                d -> d.getProduto().equals("Fundo Médio")
        ).findFirst().get();
        assertEquals(2L, dtoA.getQuantidadeSimulacoes());
        assertEquals(1620.0, dtoA.getMediaValorFinal());

        SimulacaoProdutoDiaRetornoDto dtoB = listaDto.stream().filter(
                d -> d.getProduto().equals("Ação Qualquer")
        ).findFirst().get();
        assertEquals(1L, dtoB.getQuantidadeSimulacoes());
        assertEquals(600.0, dtoB.getMediaValorFinal());
        assertEquals(data, dtoB.getData());
    }

    @Test
    void testToSimulacaoCreateRetornoDto() {
        Produto produto = setupProduto(50L, "Tesouro SELIC", NivelRisco.BAIXO, TipoProduto.TESOURO_DIRETO, 0.06);
        Cliente cliente = setupCliente(8L);
        Simulacao simulacao = setupSimulacao(
                20L,
                cliente,
                produto,
                100.0,
                106.17,
                12,
                0.0617
        );
        LocalDateTime dataEsperada = simulacao.getDataSimulacao();

        SimulacaoCreateRetornoDto dto = mapper.toSimulacaoCreateRetornoDto(simulacao);

        assertNotNull(dto);
        assertEquals(dataEsperada, dto.getDataSimulacao());

        ProdutoValidadoSimulacaoRetornoDto prodDto = dto.getProdutoValidado();
        assertEquals(50L, prodDto.getId());
        assertEquals("Tesouro SELIC", prodDto.getNome());
        assertEquals("Tesouro Direto", prodDto.getTipo());
        assertEquals("Baixo", prodDto.getRisco());
        assertEquals(0.06, prodDto.getRentabilidade());

        ResultadoSimulacaoRetornoDto resDto = dto.getResultadoSimulacao();
        assertEquals(106.17, resDto.getValorFinal());
        assertEquals(12, resDto.getPrazoMeses());
        assertEquals(0.0617, resDto.getRentabilidadeEfetiva());
    }
}