package required.action;

import org.keycloak.Config;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class UserSurveyRequiredActionFactory implements RequiredActionFactory {
    @Override
    public String getDisplayText() {
        return "User Survey Required";
    }

    @Override
    public RequiredActionProvider create(KeycloakSession session) {
        return new UserSurveyRequiredActionProvider();
    }

    @Override
    public void init(Config.Scope config) {

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "user-survey-required-action";
    }
}
