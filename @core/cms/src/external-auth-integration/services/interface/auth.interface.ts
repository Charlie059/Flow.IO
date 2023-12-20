// service/interface/auth.interface.ts

export interface IAuth {
  /**
   * Authenticate method, should return a result or token or a redirect url
   * @param args
   */
  authenticate(...args: any[]): any;

  /**
   * Validate method, should return a result
   * @param args
   */
  validate(...args: any[]): any;
}
