# JWT Token-Based Authentication Service
This README provides information about a JWT token-based authentication service. 

### Features:
1. One-time refresh token / refresh token rotation
(using the refresh token multiple times will invalidate the entire refresh token family.)
2. Access token
3. User registration
4. User Login

### Tech Stacks:

1. Spring Boot 3.2.0
2. Java 17
3. Spring Security
4. PostgreSQL for user information
5. Redis for refresh token

## How to Run
### 1. Set up Secret_Key environment (TODO)



### 2. Docker Compose Up

To run the service, use Docker:

```bash
docker-compose up
```



## APIs

### 1. Register

Register a new user with the service,
and retrieve an accessToken and a refreshToken.

- Curl Command:

  ```bash
  curl -X POST http://localhost:8080/api/v1/auth/register \
       -H 'Content-Type: application/json' \
       -d '{"firstname": "fname", "lastname": "lname", "loginEmail": "test@test.com", "password": "1234"}'
  ```

- Typical Response:

  ```json
  {
      "refreshToken": "new token",
      "accessToken": "new token",
      "message": "Successfully registered."
  }
  ```

### 2. Authenticate with Email address and Password

Authenticate a user with Email address and Password,
and retrieve an access token and a refresh token.

- Curl Command:

  ```bash
  curl -X POST http://localhost:8080/api/v1/auth/authenticate \
       -H 'Content-Type: application/json' \
       -d '{"loginEmail": "test@test.com", "password": "1234"}'
  ```

- Typical Response:

  ```json
  {
      "refreshToken": "new token",
      "accessToken": "new token",
      "message": "Successfully authenticated."
  }
  ```

### 3. Get Access Token

Get a new AccessToken using a one time RefreshToken, 
it won't revoke the old AccessToken, but will revoke the one time RefreshToken.

- Curl Command:

  ```bash
  curl -X POST http://localhost:8080/api/v1/auth/get-access-token \
       -H 'Content-Type: application/json' \
       -d '{"refreshToken": "refresh-token"}'
  ```

- Typical Response:

  ```json
  {
      "refreshToken": "new token",
      "accessToken": "new token",
      "message": "Access token successfully generated."
  }
  ```

  ```json
  {
      "refreshToken": null,
      "accessToken": null,
      "message": "The refresh token has exceeded the usage limit."
  }
  ```

### 4. Revoke Refresh Token

Revoke a RefreshToken by sending the token you want to revoke.

- Curl Command:

  ```bash
  curl -X POST http://localhost:8080/api/v1/auth/revoke-refresh-token \
       -H 'Content-Type: application/json' \
       -d '{"refreshToken": "token"}'
  ```

- Typical Response:

  ```json
  {
      "refreshToken": "null",
      "accessToken": "null",
      "message": "Revoked."
  }
  ```

### 5. PingPong (Authenticated Endpoint)

Check the service status. Requires the user to be logged in.

- **Endpoint:** `GET http://localhost:8080/api/v1/ping`

- Curl Command:

  ```bash
  curl -X GET http://localhost:8080/api/v1/ping \
       -H 'Authorization: Bearer [Your JWT Token]'
  ```

- Typical Response:

  ```
  Pong
  ```



## Security Notes
Ensure that your application secrets and database credentials are securely managed and not hard-coded or exposed in your code or Docker configuration. Use environment variables or secure secret management solutions.



## To-Do List

1. **Read the Secret Key from the Environment:** Modify the application to securely read the `SECRET_KEY` used for JWT token generation and verification from the environment variables. This approach enhances security by avoiding hard-coding sensitive information.
2. **Respond with Refreshing Token:** Implement functionality to issue a refreshing token alongside the access token during the authentication process. This token can be used by clients to obtain a new access token when the current one expires, without requiring user credentials again.