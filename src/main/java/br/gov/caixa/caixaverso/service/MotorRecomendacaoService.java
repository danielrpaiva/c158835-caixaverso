package br.gov.caixa.caixaverso.service;

import br.gov.caixa.caixaverso.enums.NivelRisco;
import br.gov.caixa.caixaverso.enums.PerfilCliente;
import br.gov.caixa.caixaverso.model.Cliente;
import br.gov.caixa.caixaverso.model.Produto;
import br.gov.caixa.caixaverso.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class MotorRecomendacaoService {

    private final Integer VALOR_PONTUACAO_BAIXO = 1;
    private final Integer VALOR_PONTUACAO_MEDIO = 50;
    private final Integer VALOR_PONTUACAO_ALTO = 100;

    private final Integer MARGEM_RECOMENDACAO = 10;

    private final ProdutoRepository produtoRepository;

    public MotorRecomendacaoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    /*
    * Implementacao para definir pontuação do cliente de 0 a 100
    * sempre que um novo investimento é registrado
    */
    public Integer definirPontuacao(Cliente cliente) {

        double totalInvestido = cliente.getTotalInvestido();

        if (totalInvestido == 0.0) {
            return 0;
        }

        double porcentagemBaixo = cliente.getSaldoRiscoBaixo() / totalInvestido;
        double porcentagemMedio = cliente.getSaldoRiscoMedio() / totalInvestido;
        double porcentagemAlto = cliente.getSaldoRiscoAlto() / totalInvestido;

        // Se todos os investimentos são em baixo risco, pontuação é 0
        if (cliente.getSaldoRiscoBaixo() == totalInvestido) {
            return 0;
        }

        // Pontuação baseada no quanto é alocado em cada nível de risco
        int pontuacao = (int) Math.round(
                porcentagemBaixo * VALOR_PONTUACAO_BAIXO
                + porcentagemMedio * VALOR_PONTUACAO_MEDIO
                + porcentagemAlto  * VALOR_PONTUACAO_ALTO);

        // Coloquei isso por causa de imprecisões do Double
        if (pontuacao < 0) {
            pontuacao = 0;
        } else if (pontuacao > 100) {
            pontuacao = 100;
        }

        return pontuacao;
    }

    /* Implementacao para definir o perfil do cliente com base
     * nas porcentagens de volume de investimento dele de risco baixo, médio e alto
     * Recaucula o seu perfil sempre que um novo investimento é registrado
     */
    public PerfilCliente definirPerfil(Integer pontuacao) {

        if (pontuacao >= 0 && pontuacao < 35) {
            return PerfilCliente.CONSERVADOR;
        } else if (pontuacao >= 35 && pontuacao < 70) {
            return PerfilCliente.MODERADO;
        } else {
            return PerfilCliente.AGRESSIVO;
        }
    }

    /*
    * Implementação para recomendar produtos de investimento
    * com base num perfil de cliente
    */
    public List<Produto> buscarProdutosRecomendadosPorPerfil(PerfilCliente perfilCliente) {

        NivelRisco risco;
        if(perfilCliente == PerfilCliente.CONSERVADOR) {
            risco = NivelRisco.BAIXO;
        } else if (perfilCliente == PerfilCliente.MODERADO) {
            risco = NivelRisco.MEDIO;
        } else {
            risco = NivelRisco.ALTO;
        }

        return produtoRepository.buscarPorRisco(risco);
    }

    /*
     * Implementação para recomendar produtos de investimento
     * com base na pontuação de um determinado cliente
     * OBS: Achei mais interessante recomendar pelos dados do cliente específico ao invés do perfil geral
     * Deixei ambas implementadas
     */
    public List<Produto> buscarProdutosRecomendadosPorCliente(Cliente cliente) {
        return produtoRepository.buscarPorPontuacao(cliente.getPontuacao(), MARGEM_RECOMENDACAO);
    }

   /*
    * TODO: Implementação para buscar o produto apropriado para simular
    *
    */
    public Produto buscarProdutoParaSimulacao(Cliente cliente) {
        // Implementação futura
        return null;
    }
}
