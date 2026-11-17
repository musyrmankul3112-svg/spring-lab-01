
# Task C — OAuth 2.1 and OpenID Connect

## 1. Correct order of events

The correct order of the events as listed in the assignment is:

5, 3, 1, 6, 2, 4.

Explanation:

1. The client creates a code_verifier and derives a code_challenge.
2. The client redirects the user to the authorization endpoint.
3. The user authenticates and grants consent.
4. The authorization server redirects to the registered redirect URI with an authorization code.
5. The client exchanges the authorization code and code_verifier at the token endpoint.
6. The authorization server returns tokens to the client.

## 2. What problem does PKCE help mitigate?

PKCE protects against authorization code interception. It ensures that only the client that started the authorization process can exchange the authorization code for tokens.

## 3. What is the purpose of the state parameter?

The state parameter connects the authorization request with its response and helps protect against Cross-Site Request Forgery (CSRF) attacks.

## 4. What is the main difference between an OAuth access token and an OIDC ID Token?

An access token is used to access protected resources on a resource server.

An ID Token contains authentication information about the user and is intended for the OIDC client.

## 5. Which component issues tokens? Which component validates/enforces access to an API?

- Authorization Server — issues tokens.
- Resource Server — validates access tokens and enforces access permissions for an API.
