package br.gov.caixa.caixaverso.mapper;

import br.gov.caixa.caixaverso.dto.produto.ProdutoRetornoDto;
import br.gov.caixa.caixaverso.model.Produto;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProdutoMapper {

    public ProdutoRetornoDto toProdutoRetornoDto(Produto produto) {
        ProdutoRetornoDto dto = new ProdutoRetornoDto();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setTipo(produto.getTipo());
        dto.setRentabilidadeAnual(produto.getRentabilidadeAnual());
        dto.setRisco(produto.getRisco());
        return dto;
    }

    public List<ProdutoRetornoDto> toProdutoRetornoDtoList(List<Produto> produtos) {
        List<ProdutoRetornoDto> dtoList = new ArrayList<ProdutoRetornoDto>();
        for (Produto produto : produtos) {
            dtoList.add(toProdutoRetornoDto(produto));
        }
        return dtoList;
    }
}
