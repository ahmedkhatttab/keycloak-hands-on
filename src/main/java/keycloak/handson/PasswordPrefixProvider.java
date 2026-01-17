package keycloak.handson;

import org.keycloak.models.KeycloakContext;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.policy.PasswordPolicyProvider;
import org.keycloak.policy.PolicyError;

public class PasswordPrefixProvider implements PasswordPolicyProvider {
    private KeycloakContext context;

    public PasswordPrefixProvider(KeycloakContext context) {
        this.context = context;
    }

    @Override
    public PolicyError validate(RealmModel realm, UserModel user, String password) {
        return validate(user.getUsername(), password);
    }

    @Override
    public PolicyError validate(String user, String password) {
        // read config by key name
        String policyConfigValue = context.getRealm().getPasswordPolicy().getPolicyConfig(
                PasswordPrefixProviderFactory.ID);
        System.out.println(">>>>>>>>>>> policyConfigValue: " + policyConfigValue);
        return password.startsWith(policyConfigValue) ? null : new PolicyError("Password should start with: ", policyConfigValue);
    }

    // parse config based on expected stored value must not return null
    @Override
    public Object parseConfig(String value) {
        return value;
    }

    @Override
    public void close() {

    }
}

