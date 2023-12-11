# JWT Token-Based Authentication Service
This README provides information about a JWT token-based authentication service. This service allows for user registration and login, issuing refreshing tokens and access tokens to clients. It is secured using Spring Security, built with Spring Boot version 3.2.0, and requires JDK 17. The service uses PostgreSQL to store user information.

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
      "refreshToken": "1234567",
      "accessToken": "abcdefg",
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
      "refreshToken": "1234567",
      "accessToken": "abcdefg",
      "message": "Successfully authenticated."
  }
  ```

### 3. Get Access Token

Get a new AccessToken using RefreshToken, 
it won't revoke the old AccessToken.

- Curl Command:

  ```bash
  curl -X POST http://localhost:8080/api/v1/auth/get-access-token \
       -H 'Content-Type: application/json' \
       -d '{"refreshToken": "1234567"}'
  ```

- Typical Response:

  ```json
  {
      "refreshToken": "null",
      "accessToken": "bcdefgh",
      "message": "Access token successfully generated."
  }
  ```

### 4. Renew Refresh Token

Get a new RefreshToken using RefreshToken,
it will revoke the old RefreshToken.


- Curl Command:

  ```bash
  curl -X POST http://localhost:8080/api/v1/auth/renew-refresh-token \
       -H 'Content-Type: application/json' \
       -d '{"refreshToken": "1234567"}'
  ```

- Typical Response:

  ```json
  {
      "refreshToken": "2345678",
      "accessToken": "null",
      "message": "Refresh token successfully renewed."
  }
  ```


### 5. Revoke Refresh Token (TODO)

Revoke a RefreshToken by sending the token you want to revoke.

- Curl Command:

  ```bash
  curl -X POST http://localhost:8080/api/v1/auth/revoke-refresh-token \
       -H 'Content-Type: application/json' \
       -d '{"refreshToken": "1234567"}'
  ```

- Typical Response:

  ```json
  {
      "refreshToken": "null",
      "accessToken": "null",
      "message": "Successfully revoked."
  }
  ```

### 6. PingPong (Authenticated Endpoint)

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