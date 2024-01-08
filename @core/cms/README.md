# Credential Management System

The Credential Management System exposes itself as a microservice and provides the ability to retrieve, encrypt, and store user credentials of external identity providers (e.g. access and refresh tokens), verify the validity of the credentials, and keep certain credentials up-to-date (e.g. getting a new access token via the refresh token before the current one expires).

### Features:

1. Encryption/decryption
2. CRUD operations on credentials
3. Extensible identity provider integration
4. Various credential types and auth modes/flows (OAuth, JWT, API Keys, ...)

### Tech Stack:

1. NestJS
2. Node 18
3. Vault (encryption/decryption, credential storage)
4. PostgreSQL (general data storage)
5. Redis (messaging)

## How to Run

### 1. Set up environment variables

e.g.

```bash
export DB_HOST=db
```

See docker-compose.yml for what's required.

### 2. Put configurations in a `.env` file in the cms folder

See .env.example for what's required. You can also use it as a template and rename it to `.env` when you're done.

### 3. Docker Compose Up

```bash
docker-compose up
```

## API

### 1. Create Credential

Upsert a crendential in the database.

- channel: "create_credential"
- message:
  ```TypeScript
  {
    userId: number,
    serviceName: string,
    nodeAccessIds: number[],
    credentialType: string,
    encryptedCredential: string,
  }
  ```

### 2. Generate OAuth URL

Generate an OAuth link of the specified provider.

- channel: "oauth"
- message:
  ```TypeScript
  TBD
  ```

### 3. Verify Token

Check the validity and expiration time of a token.

- channel: "oauth_verify_token"
- message:
  ```TypeScript
  {
    providerKey: string,
    token: string,
  }
  ```

### 4. Refresh Token

Refresh an access token via a refresh token.

- channel: "oauth_refresh_token"
- message:
  ```TypeScript
  {
    providerKey: string,
    refreshToken: string,
  }
  ```

## Security Notes

Make sure that your application secrets and database credentials are securely managed and not hard-coded or exposed in your code or Docker configuration. Use environment variables or secure secret management solutions.
