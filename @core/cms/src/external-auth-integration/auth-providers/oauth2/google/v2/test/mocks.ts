import { of } from "rxjs";

/**
 * Mocked HttpService for testing purposes.
 */
export const mockHttpService = {
  get: jest.fn((url: string, config?: any) => {
    return of(
      url.includes("https://oauth2.googleapis.com/tokeninfo")
        ? {
            data: {
              scope: "https://www.googleapis.com/auth/userinfo.email",
              expires_in: 3599,
              token_type: "Bearer",
            },
            status: 200,
            statusText: "OK",
            headers: {},
            config,
          }
        : {
            data: `Mocked response for GET request to ${url}`,
            status: 200,
            statusText: "OK",
            headers: {},
            config,
          },
    );
  }),

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  post: jest.fn((url, data, config) =>
    of({
      data: {
        access_token: "mock_access_token",
        refresh_token: "mock_refresh_token",
        expires_in: 3600,
        token_type: "Bearer",
      },

      status: 200,
      statusText: "OK",
      headers: {},
      config: config || {},
    }),
  ),
};

/**
 * Mocked EncryptionDecryptionService for testing purposes.
 */
export const mockEncryptionDecryptionService = {
  encryptData: jest.fn((data: string) => Promise.resolve(data)),
  decryptData: jest.fn((data: string) => Promise.resolve(data)),
};
