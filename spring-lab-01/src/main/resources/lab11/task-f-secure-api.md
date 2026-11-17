
# Task F — Design a Secure API Access Decision

## 1. Scenario

The university finance API provides the endpoint:

GET /invoices/{id}

The resource server validates the access token, checks the required scope and applies ABAC rules before returning invoice data.

## 2. Required security checks

Before returning invoice data, the resource server should:

1. Verify the token signature using a trusted key.
2. Check that the signing algorithm is allowed.
3. Validate the issuer (iss).
4. Validate the audience (aud).
5. Check token expiration (exp) and not-before (nbf), if applicable.
6. Verify that the token is an access token, not an ID Token.
7. Check that the token contains the required invoices:read scope.
8. Check that the user's department matches the invoice department.
9. Check that the invoice classification is internal or public.
10. Verify that the user's device is managed.

## 3. Required claims and attributes

The ABAC policy requires:

- Subject department — the user's department.
- Resource department — the invoice department.
- Resource classification — internal or public.
- Environment device_managed — whether the device is managed.

The token must also provide the subject identity and required scope.

## 4. Negative test cases

| Test case | Expected result |
|---|---|
| Expired access token | 401 Unauthorized |
| Valid token but department mismatch | 403 Forbidden |

## 5. Audit logging

The audit log should record:
- User subject (sub).
- Requested action.
- Resource ID.
- Access decision (ALLOW or DENY).
- Timestamp.
- Reason for the decision.
- Policy version.

The raw bearer token must never be stored in the audit log because it contains sensitive authentication information.
