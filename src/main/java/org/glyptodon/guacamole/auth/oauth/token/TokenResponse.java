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

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * The response produced from a successful request to the token endpoint of an
 * OAuth service.
 *
 * @author Michael Jumper
 */
public class TokenResponse {

    /**
     * An arbitrary access token which can be used for future requests against
     * the API associated with the OAuth service.
     */
    private String accessToken;

    /**
     * The type of token present. This will always be "Bearer".
     */
    private String tokenType;

    /**
     * The number of seconds the access token will remain valid.
     */
    private int expiresIn;

    /**
     * A JWT (JSON Web Token) which containing identity information which has
     * been cryptographically signed.
     */
    private String idToken;

    /**
     * Returns an arbitrary access token which can be used for future requests
     * against the API associated with the OAuth service.
     *
     * @return
     *     An arbitrary access token provided by the OAuth service.
     */
    @JsonProperty("access_token")
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets the arbitrary access token which can be used for future requests
     * against the API associated with the OAuth service.
     *
     * @param accessToken
     *     The arbitrary access token provided by the OAuth service.
     */
    @JsonProperty("access_token")
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Returns the type of token present in this response. This should always
     * be "Bearer".
     *
     * @return
     *     The type of token present in this response.
     */
    @JsonProperty("token_type")
    public String getTokenType() {
        return tokenType;
    }

    /**
     * Sets the type of token present in this response. This should always be
     * "Bearer".
     *
     * @param tokenType
     *     The type of token present in this response, which should be
     *     "Bearer".
     */
    @JsonProperty("token_type")
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * Returns the number of seconds the access token within this response will
     * remain valid.
     *
     * @return
     *     The number of seconds the access token within this response will
     *     remain valid.
     */
    @JsonProperty("expires_in")
    public int getExpiresIn() {
        return expiresIn;
    }

    /**
     * Sets the number of seconds the access token within this response will
     * remain valid.
     *
     * @param expiresIn
     *     The number of seconds the access token within this response will
     *     remain valid.
     */
    @JsonProperty("expires_in")
    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    /**
     * Returns a JWT (JSON Web Token) containing identity information which has
     * been cryptographically signed by the OAuth service.
     *
     * @return
     *     A JWT (JSON Web Token) containing identity information which has
     *     been cryptographically signed by the OAuth service.
     */
    @JsonProperty("id_token")
    public String getIdToken() {
        return idToken;
    }

    /**
     * Sets the JWT (JSON Web Token) containing identity information which has
     * been cryptographically signed by the OAuth service.
     *
     * @param idToken
     *     A JWT (JSON Web Token) containing identity information which has
     *     been cryptographically signed by the OAuth service.
     */
    @JsonProperty("id_token")
    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

}
