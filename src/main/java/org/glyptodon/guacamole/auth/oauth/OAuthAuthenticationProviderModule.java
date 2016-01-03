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

import org.glyptodon.guacamole.auth.oauth.conf.ConfigurationService;
import com.google.inject.AbstractModule;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.map.DeserializationConfig;
import org.glyptodon.guacamole.GuacamoleException;
import org.glyptodon.guacamole.auth.oauth.token.TokenService;
import org.glyptodon.guacamole.environment.Environment;
import org.glyptodon.guacamole.environment.LocalEnvironment;
import org.glyptodon.guacamole.net.auth.AuthenticationProvider;

/**
 * Guice module which configures OAuth-specific injections.
 *
 * @author Michael Jumper
 */
public class OAuthAuthenticationProviderModule extends AbstractModule {

    /**
     * Guacamole server environment.
     */
    private final Environment environment;

    /**
     * A reference to the OAuthAuthenticationProvider on behalf of which this
     * module has configured injection.
     */
    private final AuthenticationProvider authProvider;

    /**
     * A reference to the shared HTTP client to be used when making calls to
     * the OAuth service.
     */
    private final Client client;

    /**
     * Creates a new OAuth authentication provider module which configures
     * injection for the OAuthAuthenticationProvider.
     *
     * @param authProvider
     *     The AuthenticationProvider for which injection is being configured.
     *
     * @throws GuacamoleException
     *     If an error occurs while retrieving the Guacamole server
     *     environment.
     */
    public OAuthAuthenticationProviderModule(AuthenticationProvider authProvider)
            throws GuacamoleException {

        // Get local environment
        this.environment = new LocalEnvironment();

        // Store associated auth provider
        this.authProvider = authProvider;

        // Set up configuration for HTTP client
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getSingletons().add(new JacksonJaxbJsonProvider()
            .configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        );

        // Store pre-configured HTTP client
        this.client = Client.create(clientConfig);

    }

    @Override
    protected void configure() {

        // Bind core implementations of guacamole-ext classes
        bind(AuthenticationProvider.class).toInstance(authProvider);
        bind(Environment.class).toInstance(environment);

        // Bind OAuth-specific services
        bind(ConfigurationService.class);
        bind(TokenService.class);

        // Bind HTTP client
        bind(Client.class).toInstance(client);

    }

}
