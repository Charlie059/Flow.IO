export interface IOAuth {
  authenticate(): Promise<string>;
  handleCallback(query: any, res: any): void;
}
