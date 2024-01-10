/**
 * This interface represents an OAuth 2.0 provider.
 */
export interface IBasicAuth {
  /**
   * Returns the authentication URL for the OAuth provider.
   */
  authenticate(): Promise<any>;

  /**
   * Verify the access token.
   * However, the OAuth 2.0 spec (RFC 6749) doesn't clearly define the interaction between a Resource Server (RS) and Authorization Server (AS) for access token (AT) validation.
   * @param accessToken The access token.
   */
  verify?(): Promise<any>;
}
