package br.gov.caixa.caixaverso.mapper;

import br.gov.caixa.caixaverso.dto.investimento.InvestimentoRetornoDto;
import br.gov.caixa.caixaverso.enums.NivelRisco;
import br.gov.caixa.caixaverso.enums.TipoProduto;
import br.gov.caixa.caixaverso.model.Investimento;
import br.gov.caixa.caixaverso.model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InvestimentoMapperTest {

    private InvestimentoMapper mapper;

    @BeforeEach
    void setUp(){
        mapper = new InvestimentoMapper();
    }

    private Produto setupProduto(String nomeTipo) {
        Produto produto = new Produto();
        produto.setId(10L);
        produto.setNome("Tesouro Selic");
        produto.setTipo(TipoProduto.obterTipoProduto(nomeTipo));
        produto.setRisco(NivelRisco.BAIXO);
        produto.setRentabilidadeAnual(0.12);
        return produto;
    }

    private Investimento setupInvestimento(Long id, Produto produto, Double valor, Double rentabilidade, LocalDateTime data) {
        Investimento investimento = new Investimento();
        investimento.setId(id);
        investimento.setProduto(produto);
        investimento.setValor(valor);
        investimento.setRentabilidade(rentabilidade);
        investimento.setDataInvestimento(data);
        return investimento;
    }

    @Test
    void testToInvestimentoRetornoDto() {
        LocalDateTime data = LocalDateTime.of(2024, 1, 15, 10, 0);
        Produto produto = setupProduto("Tesouro Direto");
        Investimento investimento = setupInvestimento(
                1L,
                produto,
                5000.0,
                0.005,
                data
        );

        InvestimentoRetornoDto dto = mapper.toInvestimentoRetornoDto(investimento);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(5000.0, dto.getValor());
        assertEquals(0.005, dto.getRentabilidade());
        assertEquals("Tesouro Direto", dto.getTipo());
        assertEquals(data.toString(), dto.getData());
    }

    @Test
    void testToInvestimentoRetornoDtoList() {
        LocalDateTime data = LocalDateTime.now();
        Produto produto = setupProduto("CDB");
        Investimento investimento1 = setupInvestimento(2L, produto, 100.0, 0.01, data);
        List<Investimento> listaEntrada = Collections.singletonList(investimento1);

        List<InvestimentoRetornoDto> listaDto = mapper.toInvestimentoRetornoDtoList(listaEntrada);

        assertNotNull(listaDto);
        assertEquals(1, listaDto.size());
        assertEquals(2L, listaDto.getFirst().getId());
        assertEquals("CDB", listaDto.getFirst().getTipo());
    }
}