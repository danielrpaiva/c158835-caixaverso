package br.gov.caixa.caixaverso.model;

import br.gov.caixa.caixaverso.enums.NivelRisco;
import br.gov.caixa.caixaverso.enums.TipoProduto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ProdutoTest {

    @Test
    void testEqualsEHashCode(){
        Produto prod1 = new Produto();
        prod1.setId(1L);
        prod1.setTipo(TipoProduto.CDB);
        prod1.setRisco(NivelRisco.BAIXO);
        prod1.setNome("PROD1");
        prod1.setPontuacaoIdeal(5);
        prod1.setRentabilidadeAnual(0.12);

        Produto prod1Alt = new Produto();
        prod1Alt.setId(1L);
        prod1Alt.setTipo(TipoProduto.CDB);
        prod1Alt.setRisco(NivelRisco.BAIXO);
        prod1Alt.setNome("PROD1");
        prod1Alt.setPontuacaoIdeal(5);
        prod1Alt.setRentabilidadeAnual(0.12);

        Produto prod2 = new Produto();
        prod2.setId(2L);
        prod2.setTipo(TipoProduto.LCA);
        prod2.setRisco(NivelRisco.BAIXO);
        prod2.setNome("PROD2");
        prod2.setPontuacaoIdeal(10);
        prod2.setRentabilidadeAnual(0.1);

        assertEquals(prod1, prod1Alt);
        assertNotEquals(prod1, prod2);

        assertEquals(prod1.hashCode(), prod1Alt.hashCode());
        assertNotEquals(prod1.hashCode(), prod2.hashCode());
    }
}
