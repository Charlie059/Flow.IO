/**
 * Represents the client credentials for OAuth2 authentication.
 * @export
 * @interface ICredentials
 * @property {string} id - The client identifier.
 * @property {string} secret - The client secret.
 */
export interface ICredentials {
  id: string; // The client identifier
  secret: string; // The client secret
}

/**
 * Defines the configuration for an OAuth2 provider's endpoints.
 * @export
 * @interface IProviderConfiguration
 * @property {string} authorizeUrl - The URL for the OAuth2 authorization endpoint.
 * @property {string} tokenUrl - The URL for the OAuth2 token endpoint.
 */
export interface IProviderConfiguration {
  authorizeUrl: string; // The URL for the OAuth2 authorization endpoint
  tokenUrl: string; // The URL for the OAuth2 token endpoint
  callbackUri: string; // The URI for the OAuth2 callback after authentication
  callbackUriParams?: {
    // Optional additional parameters for the callback URI
    [key: string]: string;
  };
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
export interface IOAuth2Config {
  scope: string[]; // The list of OAuth2 scopes requested
  credentials: ICredentials; // The client credentials
  provider: IProviderConfiguration; // The OAuth2 provider's endpoint configuration
}
/**
 * Represents the parameters to be sent along with the OAuth2 authorization URL.
 * @export
 * @interface OAuth2UrlParams
 * @property {string} [response_type] - The response type parameter to be sent along with the OAuth2 authorization URL. Defaults to 'code'.
 * @property {string} [access_type] - The access type parameter to be sent along with the OAuth2 authorization URL. Defaults to 'offline'.
 * @property {{ [key: string]: string }} [key: string] - Optional additional parameters to be sent along with the OAuth2 authorization URL.
 */
export interface OAuth2UrlParams {
  response_type?: string; // Optional, with default values provided in function
  access_type?: string; // Optional, with default values provided in function
  [key: string]: string | undefined; // Allow additional string parameters
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
