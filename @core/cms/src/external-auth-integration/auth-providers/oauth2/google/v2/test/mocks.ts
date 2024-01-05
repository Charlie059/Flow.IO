import { of } from "rxjs";

/**
 * Mocked HttpService for testing purposes.
 */
export const mockHttpService = {
  get: jest.fn((url: string, config?: any) =>
    of({
      data: `Mocked response for GET request to ${url}`,
      status: 200,
      statusText: "OK",
      headers: {},
      config,
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
