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
import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.GuacamoleSecurityException;
import org.apache.guacamole.GuacamoleServerException;
import org.glyptodon.guacamole.auth.oauth.conf.ConfigurationService;
import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.resolvers.HttpsJwksVerificationKeyResolver;

/**
 * Service for validating ID tokens forwarded to us by the client, verifying
 * that they did indeed come from the OAuth service.
 *
 * @author Michael Jumper
 */
public class TokenValidationService {

    /**
     * Service for retrieving OAuth configuration information.
     */
    @Inject
    private ConfigurationService confService;

    /**
     * Validates and parses the given ID token, returning the username contained
     * therein, as defined by the username claim type given in
     * guacamole.properties. If the username claim type is missing or the ID
     * token is invalid, an exception is thrown instead.
     *
     * @param token
     *     The ID token to validate and parse.
     *
     * @return
     *     The username contained within the given ID token.
     *
     * @throws GuacamoleException
     *     If the ID token is not valid, the username claim type is missing, or
     *     guacamole.properties could not be parsed.
     */
    public String processUsername(String token) throws GuacamoleException {

        // Validating the token requires a JWKS key resolver
        HttpsJwks jwks = new HttpsJwks(confService.getJWKSEndpoint());
        HttpsJwksVerificationKeyResolver resolver = new HttpsJwksVerificationKeyResolver(jwks);

        // Create JWT consumer for validating received token
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setMaxFutureValidityInMinutes(300)
                .setAllowedClockSkewInSeconds(30)
                .setRequireSubject()
                .setExpectedIssuer(confService.getIssuer())
                .setExpectedAudience(confService.getClientID())
                .setVerificationKeyResolver(resolver)
                .build();

        try {

            // Validate JWT
            JwtClaims claims = jwtConsumer.processToClaims(token);

            // Pull username from claims
            String username = claims.getStringClaimValue(confService.getUsernameClaimType());
            if (username == null)
                throw new GuacamoleSecurityException("Username missing from token");

            // Username successfully retrieved from the JWT
            return username;

        }

        // Rethrow any failures to validate/parse the JWT
        catch (InvalidJwtException e) {
            throw new GuacamoleSecurityException("Invalid ID token.", e);
        }
        catch (MalformedClaimException e) {
            throw new GuacamoleServerException("Unable to parse JWT claims.", e);
        }

    }

}
