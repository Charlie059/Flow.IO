/**
 * Represents the client credentials for OAuth2 authentication.
 * @export
 * @interface IClientCredentials
 * @property {string} id - The client identifier.
 * @property {string} secret - The client secret.
 */
export interface IClientCredentials {
  id: string; // The client identifier
  secret: string; // The client secret
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
export interface IProviderConfiguration {
  authorizeUrl: string; // The URL for the OAuth2 authorization endpoint
  tokenUrl: string; // The URL for the OAuth2 token endpoint
  callbackUrl: string; // The URL for the OAuth2 callback after authentication
  verifyTokenUrl?: string; // The URL for the OAuth2 token verification endpoint
  refreshTokenUrl?: string; // The URL for the OAuth2 token refresh endpoint
  tokenRefreshBuffer?: number; // Time in seconds before expiration to refresh the token
  callbackUrlParams?: {
    // Optional additional parameters for the callback URL
    [key: string]: string;
  };
}

/**
 * Represents the full configuration for OAuth2 authentication.
 * @export
 * @interface IOAuth2Config
 * @property {string[]} scope - The list of OAuth2 scopes requested.
 * @property {IClientCredentials} credentials - The client credentials.
 * @property {IProviderConfiguration} provider - The OAuth2 provider's endpoint configuration.
 */
export interface IOAuth2Config {
  scope: string[]; // The list of OAuth2 scopes requested
  credentials: IClientCredentials; // The client credentials
  provider: IProviderConfiguration; // The OAuth2 provider's endpoint configuration
}

/**
 * Represents the parameters to be sent along with the OAuth2 authorization URL.
 * @export
 * @interface OAuth2UrlParams
 * @property {string} [response_type] - The response type parameter to be sent along with the OAuth2 authorization URL. Defaults to 'code'.
 * @property {string} [access_type] - The access type parameter to be sent along with the OAuth2 authorization URL. Defaults to 'offline'.
 * @property {{ [key: string]: string }} [key:string] - Optional additional parameters to be sent along with the OAuth2 authorization URL.
 */
export interface OAuth2UrlParams {
  response_type?: string; // Optional, with default values provided in function
  access_type?: string; // Optional, with default values provided in function
  [key: string]: string | undefined; // Allow additional string parameters
}

/**
 * Represents the options of OAuth2 requests.
 * @export
 * @interface OAuth2RequestOptions
 * @property {{ [key: string]: string }} [params] - Optional additional parameters to be sent along with the OAuth2 request.
 * @property {{ [key: string]: string }} [headers] - Optional additional headers to be sent along with the OAuth2 request.
 * @property {{ [key: string]: string }} [payloads] - Optional additional body to be sent along with the OAuth2 request.
 */
export interface OAuth2RequestOptions {
  params?: { [key: string]: string };
  headers?: { [key: string]: string };
  payloads?: { [key: string]: string };
}

/**
 * Represents the parameters to be sent along with the OAuth2 token exchange request.
 * @export
 * @interface IOAuth2State
 * @property {string} userId - The user ID.
 * @property {{ provider: string; version: string }} providerInfo - The provider information.
 */
export interface IOAuth2State {
  userId: string;
  providerInfo: {
    provider: string;
    version: string;
  };
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
export interface TokenVerificationResponse {
  isValid: boolean;
  expiresIn?: number;
  scopes?: string[];
  // Other fields as per your application's requirements
  [key: string]: any;
}
