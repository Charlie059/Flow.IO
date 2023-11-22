// common/interfaces/ICredential.ts

export interface ICredential {
  authenticate(requestOptions: unknown): Promise<unknown>;
  validate?(): Promise<boolean>;
}
