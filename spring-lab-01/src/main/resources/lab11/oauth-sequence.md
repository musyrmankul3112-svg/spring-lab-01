
# OAuth 2.1 / OIDC Sequence Diagram

## 1. Participants

- Client — starts the authorization process.
- Authorization Server — authenticates the user and issues tokens.
- Resource Server — validates tokens and protects API resources.

## 2. Sequence Diagram

```mermaid
sequenceDiagram
    participant C as Client
    participant AS as Authorization Server
    participant RS as Resource Server

    C->>C: Generate code_verifier and code_challenge
    C->>AS: Authorization request with state and code_challenge
    AS->>AS: Authenticate user and obtain consent
    AS-->>C: Redirect with authorization code
    C->>AS: Exchange code with code_verifier
    AS-->>C: Return access token and ID token
    C->>RS: GET /invoices/{id} with access token
    RS->>RS: Validate signature, issuer and audience
    RS->>RS: Check expiry and invoices:read scope
    RS->>RS: Evaluate ABAC policy
    RS-->>C: Return invoice data or access denied
```

## 3. Explanation

The client starts the authorization process and generates PKCE values. The authorization server authenticates the user and returns an authorization code. The client exchanges this code for tokens using the code_verifier. Finally, the client sends the access token to the resource server, which validates the token, checks permissions and applies ABAC rules before returning invoice data.
