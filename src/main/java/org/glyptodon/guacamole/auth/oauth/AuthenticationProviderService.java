/*
 * Copyright (C) 2015 Glyptodon LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.glyptodon.guacamole.auth.oauth;

import org.glyptodon.guacamole.auth.oauth.token.TokenResponse;
import org.glyptodon.guacamole.auth.oauth.form.OAuthCodeField;
import org.glyptodon.guacamole.auth.oauth.conf.ConfigurationService;
import com.google.inject.Inject;
import com.google.inject.Provider;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.glyptodon.guacamole.GuacamoleException;
import org.glyptodon.guacamole.auth.oauth.token.TokenService;
import org.glyptodon.guacamole.auth.oauth.user.AuthenticatedUser;
import org.glyptodon.guacamole.form.Field;
import org.glyptodon.guacamole.net.auth.Credentials;
import org.glyptodon.guacamole.net.auth.credentials.CredentialsInfo;
import org.glyptodon.guacamole.net.auth.credentials.GuacamoleInvalidCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service providing convenience functions for the OAuth AuthenticationProvider
 * implementation.
 *
 * @author Michael Jumper
 */
public class AuthenticationProviderService {

    /**
     * Logger for this class.
     */
    private final Logger logger = LoggerFactory.getLogger(AuthenticationProviderService.class);

    /**
     * Service for retrieving OAuth configuration information.
     */
    @Inject
    private ConfigurationService confService;

    /**
     * Service for producing authentication tokens from OAuth codes.
     */
    @Inject
    private TokenService tokenService;

    /**
     * Provider for AuthenticatedUser objects.
     */
    @Inject
    private Provider<AuthenticatedUser> authenticatedUserProvider;

    /**
     * Returns an AuthenticatedUser representing the user authenticated by the
     * given credentials.
     *
     * @param credentials
     *     The credentials to use for authentication.
     *
     * @return
     *     An AuthenticatedUser representing the user authenticated by the
     *     given credentials.
     *
     * @throws GuacamoleException
     *     If an error occurs while authenticating the user, or if access is
     *     denied.
     */
    public AuthenticatedUser authenticateUser(Credentials credentials)
            throws GuacamoleException {

        String code = null;

        // Pull OAuth code from request if present
        HttpServletRequest request = credentials.getRequest();
        if (request != null)
            code = request.getParameter(OAuthCodeField.PARAMETER_NAME);

        // TODO: Actually complete authentication using received code
        if (code != null) {

            // POST code and client information to OAuth token endpoint
            TokenResponse response = tokenService.getTokenFromCode(code);
            logger.debug("RESPONSE: {}", response);

            // Create corresponding authenticated user
            AuthenticatedUser authenticatedUser = authenticatedUserProvider.get();
            authenticatedUser.init("STUB", credentials);
            return authenticatedUser;

        }

        // Request auth code
        throw new GuacamoleInvalidCredentialsException("Invalid login.",
            new CredentialsInfo(Arrays.asList(new Field[] {

                // Normal username/password fields
                CredentialsInfo.USERNAME,
                CredentialsInfo.PASSWORD,

                // OAuth-specific code (will be rendered as an appropriate
                // "Log in with..." button
                new OAuthCodeField(
                    confService.getAuthorizationEndpoint(),
                    confService.getClientID(),
                    confService.getRedirectURI()
                )

            }))
        );

    }

}
