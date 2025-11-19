package br.gov.caixa.caixaverso.model;

import br.gov.caixa.caixaverso.enums.NivelRisco;
import br.gov.caixa.caixaverso.enums.PerfilCliente;
import br.gov.caixa.caixaverso.enums.TipoProduto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class InvestimentoTest {

    private Cliente setupCliente(Long id, String nome) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome(nome);
        cliente.setPerfil(PerfilCliente.MODERADO);
        cliente.setKeycloakId("key-" + id);
        cliente.setPontuacao(50);
        return cliente;
    }

    private Produto setupProduto(Long id, String nome) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(nome);
        produto.setTipo(TipoProduto.FUNDO_DE_INVESTIMENTO);
        produto.setRisco(NivelRisco.MEDIO);
        produto.setRentabilidadeAnual(0.08);
        produto.setPontuacaoIdeal(50);
        return produto;
    }

    @Test
    void testEqualsEHashCode() {
        Cliente clienteA = setupCliente(1L, "Cli A");
        Produto produtoX = setupProduto(10L, "Prod X");
        LocalDateTime data = LocalDateTime.now();

        Investimento inv1 = new Investimento();
        inv1.setId(100L);
        inv1.setCliente(clienteA);
        inv1.setProduto(produtoX);
        inv1.setValor(5000.00);
        inv1.setRentabilidade(0.05);
        inv1.setDataInvestimento(data);

        Investimento inv1Alt = new Investimento();
        inv1Alt.setId(100L);
        inv1Alt.setCliente(clienteA);
        inv1Alt.setProduto(produtoX);
        inv1Alt.setValor(5000.00);
        inv1Alt.setRentabilidade(0.05);
        inv1Alt.setDataInvestimento(data);

        Investimento inv2 = new Investimento();
        inv2.setId(200L);
        inv2.setCliente(clienteA);
        inv2.setProduto(produtoX);
        inv2.setValor(5000.00);
        inv2.setRentabilidade(0.05);
        inv2.setDataInvestimento(data);

        assertEquals(inv1, inv1Alt);
        assertNotEquals(inv1, inv2);

        assertEquals(inv1.hashCode(), inv1Alt.hashCode());
        assertNotEquals(inv1.hashCode(), inv2.hashCode());
    }
}