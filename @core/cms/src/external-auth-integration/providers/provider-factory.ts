import { GoogleProvider } from "./google.provider";

export class ProviderFactory {
  static getProvider(provider: string) {
    switch (provider) {
      case 'Google':
        return new GoogleProvider();
      default:
        throw new Error('Unsupported provider');
    }
  }
}
