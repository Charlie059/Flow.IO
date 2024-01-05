/**
 * This interface represents an OAuth 2.0 provider.
 */
export interface IOAuth {
  /**
   * Returns the authentication URL for the OAuth provider.
   */
  authenticate(): Promise<string>;

  /**
   * Handles the callback from the OAuth provider.
   * @param query Query parameters from the callback request.
   * @param res {@link Response}
   */
  handleCallback(query: any, res: Response): void;

  /**
   * Verify the access token.
   * However, the OAuth 2.0 spec (RFC 6749) doesn't clearly define the interaction between a Resource Server (RS) and Authorization Server (AS) for access token (AT) validation.
   * @param accessToken The access token.
   */
  verifyToken?(accessToken: string): Promise<any>;

  /**
   * Refreshes the access token using the refresh token.
   * @param refreshToken The refresh token.
   */
  refreshToken?(refreshToken: string): Promise<any>;
}
