import { OAuth2Options } from "src/@types";

export class GoogleProvider {
  private config: OAuth2Options = {
    credentials: {
      client: {
        id: process.env.GOOGLE_CLIENT_ID,
        secret: process.env.GOOGLE_CLIENT_SECRET
      },
      auth: {
        authorizeUrl: process.env.GOOGLE_AUTH_URL,
        tokenUrl: '' // TODO: Add token url
      },
    },
    scope: ['email', 'profile'],
    callbackUri: process.env.GOOGLE_REDIRECT_URI
  };

  getAuthUrl(userId: number): string {
    const params = new URLSearchParams({
      client_id: this.config.credentials.client.id,
      redirect_uri: this.config.callbackUri,
      response_type: 'code',
      scope: this.config.scope.join(' '),
      access_type: 'offline',
      include_granted_scopes: 'true',
      state: userId.toString() // TODO: better state
    });

    return `${this.config.credentials.auth.authorizeUrl}?${params.toString()}`;
  }
}
