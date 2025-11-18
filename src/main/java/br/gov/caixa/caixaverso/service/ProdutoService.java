package br.gov.caixa.caixaverso.service;

import br.gov.caixa.caixaverso.dto.produto.ProdutoCreateDto;
import br.gov.caixa.caixaverso.dto.produto.ProdutoRetornoDto;
import br.gov.caixa.caixaverso.enums.PerfilCliente;
import br.gov.caixa.caixaverso.exception.ClienteNaoEncontradoException;
import br.gov.caixa.caixaverso.mapper.ProdutoMapper;
import br.gov.caixa.caixaverso.model.Cliente;
import br.gov.caixa.caixaverso.model.Produto;
import br.gov.caixa.caixaverso.repository.ClienteRepository;
import br.gov.caixa.caixaverso.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;
    private final MotorRecomendacaoService motorRecomendacaoService;
    private final ProdutoMapper mapper;

    public ProdutoService(
            ProdutoRepository produtoRepository,
            ClienteRepository clienteRepository,
            ProdutoMapper mapper,
            MotorRecomendacaoService motorRecomendacaoService
    ) {
        this.mapper = mapper;
        this.produtoRepository = produtoRepository;
        this.clienteRepository = clienteRepository;
        this.motorRecomendacaoService = motorRecomendacaoService;
    }

    @Transactional
    public ProdutoRetornoDto criarProduto(ProdutoCreateDto dto) {

        Produto novoProduto = new Produto();
        novoProduto.setNome(dto.getNome());
        novoProduto.setTipo(dto.getTipo());
        novoProduto.setRisco(dto.getRisco());
        novoProduto.setRentabilidadeAnual(dto.getRentabilidadeAnual());

        produtoRepository.persist(novoProduto);

        return mapper.toProdutoRetornoDto(novoProduto);
    }

    @Transactional
    public List<ProdutoRetornoDto> listarProdutos() {
        List<Produto> produtos = produtoRepository.listAll();
        return mapper.toProdutoRetornoDtoList(produtos);
    }

    @Transactional
    public List<ProdutoRetornoDto> listarProdutosRecomendadosPerfil(PerfilCliente perfilCliente) {
        List<Produto> produtos = motorRecomendacaoService.buscarProdutosRecomendadosPorPerfil(perfilCliente);
        return mapper.toProdutoRetornoDtoList(produtos);
    }

    @Transactional
    public List<ProdutoRetornoDto> listarProdutosRecomendadosCliente(Long clienteId) {

        Cliente cliente = clienteRepository.findById(clienteId);

        if(cliente == null) {
            throw new ClienteNaoEncontradoException(clienteId);
        }

        List<Produto> produtos = motorRecomendacaoService.buscarProdutosRecomendadosPorCliente(cliente);
        return mapper.toProdutoRetornoDtoList(produtos);
    }
}
