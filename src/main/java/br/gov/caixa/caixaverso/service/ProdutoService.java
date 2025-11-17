package br.gov.caixa.caixaverso.service;

import br.gov.caixa.caixaverso.dto.produto.ProdutoCreateDto;
import br.gov.caixa.caixaverso.dto.produto.ProdutoRetornoDto;
import br.gov.caixa.caixaverso.mapper.ProdutoMapper;
import br.gov.caixa.caixaverso.model.Produto;
import br.gov.caixa.caixaverso.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper mapper;

    public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper mapper) {
        this.mapper = mapper;
        this.produtoRepository = produtoRepository;
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
}
