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

import org.glyptodon.guacamole.properties.StringGuacamoleProperty;

/**
 * Provides properties required for use of the OAuth authentication provider.
 * These properties will be read from guacamole.properties when the OAuth
 * authentication provider is used.
 *
 * @author Michael Jumper
 */
public class OAuthGuacamoleProperties {

    /**
     * This class should not be instantiated.
     */
    private OAuthGuacamoleProperties() {}

    /**
     * The authorization endpoint (URI) of the OAuth service.
     */
    public static final StringGuacamoleProperty OAUTH_AUTHORIZATION_ENDPOINT =
            new StringGuacamoleProperty() {

        @Override
        public String getName() { return "oauth-authorization-endpoint"; }

    };

    /**
     * OAuth client ID which should be submitted to the OAuth service when
     * necessary. This value is typically provided by the OAuth service when
     * OAuth credentials are generated for your application.
     */
    public static final StringGuacamoleProperty OAUTH_CLIENT_ID =
            new StringGuacamoleProperty() {

        @Override
        public String getName() { return "oauth-client-id"; }

    };

    /**
     * OAuth client secret which should be submitted to the OAuth service when
     * necessary. This value is typically provided by the OAuth service when
     * OAuth credentials are generated for your application.
     */
    public static final StringGuacamoleProperty OAUTH_CLIENT_SECRET =
            new StringGuacamoleProperty() {

        @Override
        public String getName() { return "oauth-client-secret"; }

    };

    /**
     * The URI that the OAuth service should redirect to after the
     * authentication process is complete. This must be the full URL that a
     * user would enter into their browser to access Guacamole.
     */
    public static final StringGuacamoleProperty OAUTH_REDIRECT_URI =
            new StringGuacamoleProperty() {

        @Override
        public String getName() { return "oauth-redirect-uri"; }

    };

}
