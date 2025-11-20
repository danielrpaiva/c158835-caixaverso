package br.gov.caixa.caixaverso.mapper;

import br.gov.caixa.caixaverso.dto.produto.ProdutoRetornoDto;
import br.gov.caixa.caixaverso.enums.NivelRisco;
import br.gov.caixa.caixaverso.enums.TipoProduto;
import br.gov.caixa.caixaverso.model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProdutoMapperTest {

    private ProdutoMapper mapper;

    @BeforeEach
    void setUp(){
        mapper = new ProdutoMapper();
    }

    private Produto setupProduto(Long id) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome("Tesouro Prefixado");
        produto.setTipo(TipoProduto.TESOURO_DIRETO);
        produto.setRentabilidadeAnual(0.105);
        produto.setRisco(NivelRisco.BAIXO);
        produto.setPontuacaoIdeal(20);
        return produto;
    }

    @Test
    void testToProdutoRetornoDto() {
        Produto produto = setupProduto(5L);

        ProdutoRetornoDto dto = mapper.toProdutoRetornoDto(produto);

        assertNotNull(dto);
        assertEquals(5L, dto.getId());
        assertEquals("Tesouro Prefixado", dto.getNome());
        assertEquals(TipoProduto.TESOURO_DIRETO, dto.getTipo());
        assertEquals(0.105, dto.getRentabilidadeAnual());
        assertEquals(NivelRisco.BAIXO, dto.getRisco());
        // PontuacaoIdeal não é mapeado na saída, mas verificamos os campos mapeados
    }

    @Test
    void testToProdutoRetornoDtoList() {
        Produto produto1 = setupProduto(1L);
        Produto produto2 = setupProduto(2L);
        List<Produto> listaEntrada = List.of(produto1, produto2);

        List<ProdutoRetornoDto> listaDto = mapper.toProdutoRetornoDtoList(listaEntrada);

        assertNotNull(listaDto);
        assertEquals(2, listaDto.size());
        assertEquals(1L, listaDto.get(0).getId());
        assertEquals(2L, listaDto.get(1).getId());
        assertEquals(TipoProduto.TESOURO_DIRETO, listaDto.get(1).getTipo());
    }

    @Test
    void testToProdutoRetornoDtoListListaVazia() {
        List<Produto> listaEntrada = Collections.emptyList();

        List<ProdutoRetornoDto> listaDto = mapper.toProdutoRetornoDtoList(listaEntrada);

        assertNotNull(listaDto);
        assertEquals(0, listaDto.size());
    }
}