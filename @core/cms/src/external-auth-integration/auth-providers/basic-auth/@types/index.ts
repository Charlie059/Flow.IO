export type BasicAuthString = `basic-${string}`;

/**
 * Represents the client credential for Basic HTTP Authentication.
 * @export
 * @interface ICredential
 * @property {string} id - The client identifier.
 * @property {string} secret - The client secret.
 */
export interface BasicAuthCredential {
  username: string;
  password: string;
}

/**
 * Defines the configuration for an OAuth2 provider's endpoints.
 * @export
 * @interface IProviderConfiguration
 * @property {string} authorizeUrl - The URL for the OAuth2 authorization endpoint.
 * @property {string} tokenUrl - The URL for the OAuth2 token endpoint.
 * @property {string} callbackUri - The URI for the OAuth2 callback after authentication.·
 * @property {string} [verifyTokenUrl] - The URL for the OAuth2 token verification endpoint.
 * @property {string} refreshTokenUrl - The URL for the OAuth2 token refresh endpoint.
 * @property {number} [tokenRefreshBuffer] - Time in seconds before expiration to refresh the token.
 * @property {{ [key: string]: string }} [callbackUriParams] - Optional additional parameters for the callback URI.
 */
export interface BasicAuthProvider {
  authenticateUrl: string;
  verifyUrl?: string;
}

/**
 * Represents the full configuration for OAuth2 authentication.
 * @export
 * @interface IOAuth2Config
 * @property {string[]} scope - The list of OAuth2 scopes requested.
 * @property {ICredentials} credentials - The client credentials.
 * @property {IProviderConfiguration} provider - The OAuth2 provider's endpoint configuration.
 * @property {string} callbackUri - The URI for the OAuth2 callback after authentication.
 * @property {{ [key: string]: string }} [callbackUriParams] - Optional additional parameters for the callback URI.
 */
export interface BasicAuthConfig {
  provider: BasicAuthProvider;
  credential: BasicAuthCredential;
}

/**
 * Represents the response from the OAuth2 token verification endpoint.
 * @export
 * @interface TokenVerificationResponse
 * @property {boolean} isValid - Whether the token is valid.
 * @property {number} [expiresIn] - The number of seconds until the token expires.
 * @property {string[]} [scopes] - The list of scopes.
 * @property {{ [key: string]: any }} [key: string] - Optional additional fields as per your application's requirements.
 */
export interface BasicAuthVerificationResponse {
  isValid: boolean;
  expiresIn?: number;
  // Other fields as per your application's requirements
  [key: string]: any;
}
