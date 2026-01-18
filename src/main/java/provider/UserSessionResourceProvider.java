package provider;

import org.jboss.logging.Logger;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class UserSessionResourceProvider implements RealmResourceProvider {

    private final KeycloakSession session;
    Logger logger = Logger.getLogger(UserSessionResourceProvider.class);

    public UserSessionResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public UserSessionResource getResource() {
        logger.infov(">>>>> UserSessionResourceProvider");
        return new UserSessionResource(this.session);
    }

    @Override
    public void close() {

    }
}
