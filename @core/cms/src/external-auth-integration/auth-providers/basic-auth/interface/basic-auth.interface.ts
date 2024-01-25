export interface IBasicAuth {
  authenticate(username: string, password: string): Promise<any>;

  verify?(username: string, password: string): Promise<any>;
}
