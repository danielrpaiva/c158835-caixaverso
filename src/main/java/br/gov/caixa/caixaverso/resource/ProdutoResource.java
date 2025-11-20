package br.gov.caixa.caixaverso.resource;

import br.gov.caixa.caixaverso.dto.produto.ProdutoCreateDto;
import br.gov.caixa.caixaverso.enums.PerfilCliente;
import br.gov.caixa.caixaverso.service.ProdutoService;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/api/produtos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    private final ProdutoService produtoService;

    public ProdutoResource(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @POST
    @Authenticated
    public Response adicionarProduto(ProdutoCreateDto dto) {
        return Response
                .status(Response.Status.CREATED)
                .entity(produtoService.criarProduto(dto))
                .build();
    }

    @GET
    @Authenticated
    public Response listarProdutos() {
        return Response.ok(produtoService.listarProdutos()).build();
    }

    // 6. Produtos Recomendados
    @GET
    @Authenticated
    @Path("/produtos-recomendados/{perfil}")
    public Response listarProdutosRecomendadosPerfil(@PathParam("perfil") PerfilCliente perfilCliente) {
        return Response.ok(produtoService.listarProdutosRecomendadosPerfil(perfilCliente)).build();
    }

    // Recomendação adicional só que feita pelo cliente em si ao invés do perfil
    @GET
    @Authenticated
    @Path("/produtos-recomendados-cliente/{clienteId}")
    public Response listarProdutosRecomendadosCliente(@PathParam("clienteId") Long clienteId) {
        return Response.ok(produtoService.listarProdutosRecomendadosCliente(clienteId)).build();
    }
}
