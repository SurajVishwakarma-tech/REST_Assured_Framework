# REST Assured API Automation Framework

This project is a production-ready REST API automation framework built with Java 21, Rest Assured, TestNG, Gradle, JWT-aware authentication handling, and Extent Reports.

## What is included

- Environment-based configuration with override support via system properties and environment variables.
- REST Assured wrappers for reusable request and response specifications.
- Authentication and protected-endpoint validation flow for registration, login, logout, and `GET /api/auth/me`.
- Thread-safe JWT token context implementation for parallel execution.
- Extent Reports integration for HTML reporting.
- TestNG suite structure for positive and negative API tests.

## Project structure

- `config` package: framework configuration and environment loading
- `constants` package: endpoint and HTTP constants
- `clients` package: shared REST client methods
- `specifications` package: request/response specifications
- `models` package: request and response POJOs
- `services` package: domain-specific API service wrappers
- `auth` package: token lifecycle helpers and thread-local token context
- `tests` package: authentication and negative API tests
- `reporting` package: Extent report initialization and lifecycle
- `resources` package: configuration and TestNG suite files

## Configuration

The default configuration is stored in `src/test/resources/config/application.properties`.

You can override values through environment variables or JVM system properties:

- `API_BASE_URI`
- `API_BASE_PATH`
- `AUTH_REGISTER_ENDPOINT`
- `AUTH_LOGIN_ENDPOINT`
- `AUTH_LOGOUT_ENDPOINT`
- `AUTH_ME_ENDPOINT`

Example:

```bash
export API_BASE_URI=http://localhost:8080
export API_BASE_PATH=/api
./gradlew test
```

## Assumptions and contract notes

This framework intentionally treats request and response contracts as configurable assumptions instead of fixed API guarantees. Update the endpoint constants and POJOs in this project when a real application contract is available.

The authentication layer does not assume a JWT is invalidated on logout unless the backend enforces token revocation. The framework clears the local execution-scoped token only after logout and does not claim server-side token invalidation without server evidence.

## Run tests

If Gradle is installed locally, either command works:

```bash
gradlew test
```

Or with a specific suite:

```bash
gradlew test --tests "com.example.api.tests.auth.*"
```

## CI/CD

A sample GitHub Actions workflow is available under `.github/workflows/ci.yml`.
