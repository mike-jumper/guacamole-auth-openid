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

package org.glyptodon.guacamole.auth.oauth.token;

import com.google.inject.Inject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.representation.Form;
import javax.ws.rs.core.MediaType;
import org.glyptodon.guacamole.GuacamoleException;
import org.glyptodon.guacamole.GuacamoleServerException;
import org.glyptodon.guacamole.auth.oauth.AuthenticationProviderService;
import org.glyptodon.guacamole.auth.oauth.conf.ConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides relatively abstract means of producing authentication tokens from
 * the codes received from OAuth services.
 *
 * @author Michael Jumper
 */
public class TokenService {

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
     * Jersey HTTP client.
     */
    @Inject
    private Client client;

    /**
     * Given an authorization code previously received from the OAuth service
     * via the "code" parameter provided to the redirect URL, retrieves and
     * returns an authentication token.
     *
     * @param code
     *     The value of the "code" parameter received from the OAuth service.
     *
     * @return
     *     The authentication roken response received from the OAuth service.
     *
     * @throws GuacamoleException
     *     If required properties within guacamole.properties cannot be read,
     *     or if an error occurs while contacting the OAuth service.
     */
    public TokenResponse getTokenFromCode(String code)
            throws GuacamoleException {

        try {

            // Generate POST data
            Form form = new Form();
            form.add("code", code);
            form.add("client_id", confService.getClientID());
            form.add("client_secret", confService.getClientSecret());
            form.add("redirect_uri", confService.getRedirectURI());
            form.add("grant_type", "authorization_code");

            // POST code and client information to OAuth token endpoint
            return client.resource(confService.getTokenEndpoint())
                .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(TokenResponse.class, form);

        }

        // Log any failure reaching the OAuth service
        catch (UniformInterfaceException e) {
            logger.debug("POST to token endpoint failed.", e);
            throw new GuacamoleServerException("Unable to POST to token endpoint.", e);
        }

    }

}
