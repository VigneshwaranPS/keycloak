package org.dockerLearn;

import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.ArrayList;
import java.util.List;

public class UserIdAuthenticatorFactory implements AuthenticatorFactory {


    private static UserIdAuthenticator SINGLETON = new UserIdAuthenticator();

    @Override
    public Authenticator create(KeycloakSession session) {
        return SINGLETON;
    }


    @Override
    public String getId() {
        return "user-id-authenticator";
    }

    @Override
    public String getDisplayType() {
        return "User ID Authenticator";
    }

    @Override
    public String getHelpText() {
        return "Authenticate users using User ID without a password";
    }

    @Override
    public String getReferenceCategory() {
        return "user-id-authenticator";
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return new ArrayList<>();
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public void close() {
        // No cleanup needed
    }

    @Override
    public void init(Config.Scope config) {
        // No initialization needed
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // No post-initialization needed
    }
}
