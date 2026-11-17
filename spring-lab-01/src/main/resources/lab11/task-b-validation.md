
# Task B — Token Validation Checklist

## 1. Validation checklist

| Check | Scenario | Expected result |
|---|---|---|
| Signature | Signature cannot be verified with a trusted issuer key | FAIL — Reject |
| Issuer (iss) | Issuer differs from configured identity provider | FAIL — Reject |
| Audience (aud) | Token is intended for another API | FAIL — Reject |
| Expiry (exp) | Current time is later than exp | FAIL — Reject |
| Scope | Token lacks invoices:read for a read request | FAIL — Deny request |
| Token purpose | An ID Token is presented to an API as an access token | FAIL — Reject |

## 2. Why does successful Base64URL decoding not establish token authenticity?

Successful Base64URL decoding only allows us to read the token contents. It does not verify the digital signature or prove who issued the token. The token could have been modified or created by an attacker. A resource server must verify the signature using a trusted key and validate the issuer, audience, expiry and token purpose before accepting it.
