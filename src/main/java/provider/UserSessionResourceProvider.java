package provider;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class UserSessionResourceProvider implements RealmResourceProvider {

    private final KeycloakSession session;

    public UserSessionResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public UserSessionResource getResource() {
        return new UserSessionResource(this.session);
    }

    @Override
    public void close() {

    }
}
