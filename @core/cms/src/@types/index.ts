export interface OAuth2Options {
  scope: string[]
  credentials: Credentials
  callbackUri: string
  callbackUriParams?: {
    [key: string]: string
  };
}

export interface ProviderConfiguration {
  authorizeUrl: string
  tokenUrl: string
}

export interface Credentials {
  client: {
    id: string
    secret: string
  };
  auth: ProviderConfiguration
}
