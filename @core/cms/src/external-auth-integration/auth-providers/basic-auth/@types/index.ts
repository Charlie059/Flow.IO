export type BasicAuthString = `basic-${string}`;

export interface BasicAuthProvider {
  authenticateUrl: string;
  verifyUrl?: string;
}

export interface BasicAuthConfig {
  provider: BasicAuthProvider;
}

export interface BasicAuthVerificationResponse {
  isValid: boolean;
  expiresIn?: number;
  // Other fields as per your application's requirements
  [key: string]: any;
}
