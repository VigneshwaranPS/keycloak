package org.dockerLearn;

import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.services.messages.Messages;

public class UserIdAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(UserIdAuthenticator.class);

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        String userId = context.getHttpRequest().getDecodedFormParameters().getFirst("userId");

        if (userId == null || userId.isEmpty()) {
            Response challenge = context.form()
                    .setError(Messages.INVALID_USER)
                    .createForm("login.ftl");
            context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR, challenge);
            logger.error("User ID is null or empty.");
            return;
        }

        UserModel user = context.getSession().users().getUserById(context.getRealm(), userId);

        if (user == null) {
            Response challenge = context.form()
                    .setError(Messages.INVALID_USER)
                    .createForm("login.ftl");
            context.failureChallenge(AuthenticationFlowError.INVALID_USER, challenge);
            logger.error("User not found for ID: " + userId);
            return;
        }

        // Assuming user is valid, set the user and proceed with authentication
        context.setUser(user);
        context.success();
        logger.info("User authenticated successfully: " + userId);
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        // No action required for this authenticator
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return false;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        // No required actions
    }

    @Override
    public void close() {
        // Nothing to close
    }
}
