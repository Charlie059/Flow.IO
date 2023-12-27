/**
 * Represents the configuration for OAuth2 authentication.
 * @export
 * @interface IOAuth2Config
 * @property {string[]} scope - The list of OAuth2 scopes requested.
 * @property {ICredentials} credentials - The client credentials for OAuth2 authentication.
 * @property {string} callbackUri - The URI to which the OAuth2 provider will send the callback after authentication.
 * @property {{ [key: string]: string }} [callbackUriParams] - Optional additional parameters to be sent along with the callback URI.
 */
export interface IOAuth2Config {
  scope: string[];
  credentials: ICredentials;
  callbackUri: string;
  callbackUriParams?: {
    [key: string]: string;
  };
}

/**
 * Defines the configuration for an OAuth2 provider, including URLs for authorization and token exchange.
 * @export
 * @interface IProviderConfiguration
 * @property {string} authorizeUrl - The URL for the OAuth2 authorization endpoint.
 * @property {string} tokenUrl - The URL for the OAuth2 token endpoint.
 */
export interface IProviderConfiguration {
  authorizeUrl: string;
  tokenUrl: string;
}

/**
 * Represents the client credentials and authorization URL configuration for OAuth2.
 * @export
 * @interface ICredentials
 * @property {Object} client - The client credentials object.
 * @property {string} client.id - The client identifier.
 * @property {string} client.secret - The client secret.
 * @property {IProviderConfiguration} authUrl - The configuration for the OAuth2 provider.
 */
export interface ICredentials {
  client: {
    id: string;
    secret: string;
  };
  authUrl: IProviderConfiguration;
}

/**
 * Represents the parameters to be sent along with the OAuth2 authorization URL.
 * @export
 * @interface OAuth2UrlParams
 * @property {string} client_id - The client identifier.
 * @property {string} redirect_uri - The URI to which the OAuth2 provider will send the callback after authentication.
 * @property {string} scope - The list of OAuth2 scopes requested.
 * @property {string} state - The state parameter to be sent along with the OAuth2 authorization URL.
 * @property {string} [response_type] - The response type parameter to be sent along with the OAuth2 authorization URL. Defaults to 'code'.
 * @property {string} [access_type] - The access type parameter to be sent along with the OAuth2 authorization URL. Defaults to 'offline'.
 * @property {{ [key: string]: string }} [key: string] - Optional additional parameters to be sent along with the OAuth2 authorization URL.
 */
export interface OAuth2UrlParams {
  client_id: string;
  redirect_uri: string;
  scope: string;
  state: string;
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
