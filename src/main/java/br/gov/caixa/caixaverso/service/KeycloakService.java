package br.gov.caixa.caixaverso.service;

import br.gov.caixa.caixaverso.dto.cliente.RegistrarClienteDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

@ApplicationScoped
public class KeycloakService {

    private static final String SERVER_URL = "http://keycloak:8080";
    private static final String REALM = "quarkus-app";
    private static final String CLIENT_ID = "admin-cli";
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "admin";

    private Keycloak getKeycloakClient() {
        return KeycloakBuilder.builder()
                .serverUrl(SERVER_URL)
                .realm("master")
                .clientId(CLIENT_ID)
                .username(ADMIN_USER)
                .password(ADMIN_PASS)
                .build();
    }

    public String criarUsuario(RegistrarClienteDto dto) {
        Keycloak kc = getKeycloakClient();

        UserRepresentation usuario = new UserRepresentation();
        usuario.setEnabled(true);
        usuario.setUsername(dto.getEmail());
        usuario.setEmail(dto.getEmail());
        usuario.setFirstName(dto.getNome());
        usuario.setLastName(dto.getSobrenome());

        CredentialRepresentation senha = new CredentialRepresentation();
        senha.setTemporary(false);
        senha.setType(CredentialRepresentation.PASSWORD);
        senha.setValue(dto.getSenha());

        usuario.setCredentials(List.of(senha));

        Response res = kc.realm(REALM).users().create(usuario);

        if (res.getStatus() != 201) {
            throw new RuntimeException("Usuário não foi criado com sucesso no Kc: " + res.getStatus());
        }

        return res.getLocation().getPath().replaceAll(".*/", "");
    }
}
