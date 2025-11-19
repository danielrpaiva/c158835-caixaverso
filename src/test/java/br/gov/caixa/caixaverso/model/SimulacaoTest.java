package br.gov.caixa.caixaverso.model;

import br.gov.caixa.caixaverso.enums.NivelRisco;
import br.gov.caixa.caixaverso.enums.PerfilCliente;
import br.gov.caixa.caixaverso.enums.TipoProduto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SimulacaoTest {

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
        produto.setTipo(TipoProduto.LCI);
        produto.setRisco(NivelRisco.BAIXO);
        produto.setRentabilidadeAnual(0.06);
        produto.setPontuacaoIdeal(20);
        return produto;
    }

    @Test
    void testEqualsEHashCode() {
        Cliente clienteA = setupCliente(1L, "Cli A");
        Produto produtoX = setupProduto(10L, "Prod X");
        LocalDateTime data = LocalDateTime.now();

        Simulacao sim1 = new Simulacao();
        sim1.setId(500L);
        sim1.setCliente(clienteA);
        sim1.setProduto(produtoX);
        sim1.setValorInvestido(1000.00);
        sim1.setValorFinal(1200.00);
        sim1.setPrazoMeses(24);
        sim1.setDataSimulacao(data);
        sim1.setRentabilidadeEfetiva(0.10);

        Simulacao sim1Alt = new Simulacao();
        sim1Alt.setId(500L);
        sim1Alt.setCliente(clienteA);
        sim1Alt.setProduto(produtoX);
        sim1Alt.setValorInvestido(1000.00);
        sim1Alt.setValorFinal(1200.00);
        sim1Alt.setPrazoMeses(24);
        sim1Alt.setDataSimulacao(data);
        sim1Alt.setRentabilidadeEfetiva(0.10);

        Simulacao sim2 = new Simulacao();
        sim2.setId(501L);
        sim2.setCliente(clienteA);
        sim2.setProduto(produtoX);
        sim2.setValorInvestido(1000.00);
        sim2.setValorFinal(1200.00);
        sim2.setPrazoMeses(24);
        sim2.setDataSimulacao(data);
        sim2.setRentabilidadeEfetiva(0.10);

        assertEquals(sim1, sim1Alt);
        assertNotEquals(sim1, sim2);

        assertEquals(sim1.hashCode(), sim1Alt.hashCode());
        assertNotEquals(sim1.hashCode(), sim2.hashCode());
    }
}