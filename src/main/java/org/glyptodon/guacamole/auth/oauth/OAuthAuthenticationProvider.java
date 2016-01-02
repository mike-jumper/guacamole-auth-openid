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

import org.glyptodon.guacamole.GuacamoleException;
import org.glyptodon.guacamole.net.auth.AuthenticatedUser;
import org.glyptodon.guacamole.net.auth.AuthenticationProvider;
import org.glyptodon.guacamole.net.auth.Credentials;
import org.glyptodon.guacamole.net.auth.UserContext;
import org.glyptodon.guacamole.net.auth.credentials.CredentialsInfo;
import org.glyptodon.guacamole.net.auth.credentials.GuacamoleInvalidCredentialsException;

/**
 * Guacamole authentication backend which authenticates users using an
 * arbitrary external system implementing OAuth. No storage for connections is
 * provided - only authentication. Storage must be provided by some other
 * extension.
 *
 * @author Michael Jumper
 */
public class OAuthAuthenticationProvider implements AuthenticationProvider {

    @Override
    public String getIdentifier() {
        return "oauth";
    }

    @Override
    public AuthenticatedUser authenticateUser(Credentials credentials)
            throws GuacamoleException {

        // STUB
        throw new GuacamoleInvalidCredentialsException(
            "Invalid login.",
            CredentialsInfo.USERNAME_PASSWORD
        );

    }

    @Override
    public AuthenticatedUser updateAuthenticatedUser(
            AuthenticatedUser authenticatedUser, Credentials credentials)
            throws GuacamoleException {

        // No update necessary
        return authenticatedUser;

    }

    @Override
    public UserContext getUserContext(AuthenticatedUser authenticatedUser)
            throws GuacamoleException {

        // No associated data whatsoever
        return null;

    }

    @Override
    public UserContext updateUserContext(UserContext context,
            AuthenticatedUser authenticatedUser) throws GuacamoleException {

        // No update necessary
        return context;

    }

}
