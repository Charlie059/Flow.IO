import { TokenVerificationResponse } from "../@types";

export interface IOAuth {
  authenticate(): Promise<string>;
  handleCallback(query: any, res: any): void;
  verifyToken?(accessToken: string): Promise<any>; // Verify the access token. However, the OAuth 2.0 spec (RFC 6749) doesn't clearly define the interaction between a Resource Server (RS) and Authorization Server (AS) for access token (AT) validation.
  refreshToken(refreshToken: string): Promise<any>; // Use the refresh token to get a new access token
}
