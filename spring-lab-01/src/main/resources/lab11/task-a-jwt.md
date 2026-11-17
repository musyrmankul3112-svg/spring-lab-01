
# Task A — Inspect a Sample JWT

## 1. What are the three parts of a compact signed JWT?

A JWT consists of three parts:
- Header — contains token type and signing algorithm.
- Payload — contains claims and information about the user.
- Signature — verifies the integrity and authenticity of the token.

## 2. Which claim identifies the issuer? Which identifies the subject?

- iss (issuer) identifies who issued the token.
- sub (subject) identifies the user or subject of the token.

## 3. What is the intended audience of this token?

The aud claim identifies the intended recipient of the token. In this example, it is https://api.example.edu.

## 4. What permissions are represented by the scope claim?

The scope claim contains:
- invoices:read — permission to read invoices.
- profile — permission to access profile information.

## 5. Which information is a role, and which is an attribute?

- Role: finance-analyst.
- Attribute: department = finance.

A role defines the user's permissions, while an attribute describes a characteristic of the user.

## 6. Why can this sample not be accepted as proof of identity or access?

This sample is unsigned and is only an educational example. Its signature cannot be verified. Reading or decoding the payload does not prove that the token is authentic. A real server must verify the signature and validate the token claims before granting access.
