package keycloak.handson;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.policy.PasswordPolicyProvider;
import org.keycloak.policy.PasswordPolicyProviderFactory;

public class PasswordPrefixProviderFactory implements PasswordPolicyProviderFactory {
    // KEY NAME
    public static final String ID = "passwordPrefix";
    public static final String DEFAULT_PASSWORD_PREFIX = "AM";

    // NAME IN UI
    @Override
    public String getDisplayName() {
        return "Password Prefix Provider";
    }

    // KEY DATA TYPE
    @Override
    public String getConfigType() {
        return PasswordPolicyProvider.STRING_CONFIG_TYPE;
    }

    // DEFAULT VALUE
    @Override
    public String getDefaultConfigValue() {
        return DEFAULT_PASSWORD_PREFIX;
    }

    // CAN WE ADD IT MORE THAN ONCE WITH DIFF VALUE
    @Override
    public boolean isMultiplSupported() {
        return false;
    }

    @Override
    public PasswordPolicyProvider create(KeycloakSession session) {
        return new PasswordPrefixProvider(session.getContext());
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
        return ID;
    }
}
