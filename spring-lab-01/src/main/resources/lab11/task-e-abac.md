
# Task E — Attribute-Based Access Control (ABAC)

## 1. Access decisions

A user may read an invoice only when all three conditions are true:
- The user's department matches the invoice department.
- The invoice classification is internal or public.
- The user's device is managed.

| Case | User department | Invoice department | Classification | Managed device | Decision |
|---|---|---|---|---|---|
| 1 | finance | finance | internal | Yes | ALLOW |
| 2 | sales | finance | internal | Yes | DENY |
| 3 | finance | finance | confidential | Yes | DENY |
| 4 | finance | finance | public | No | DENY |

## 2. Explanation of decisions

- Case 1: ALLOW — all three conditions are satisfied.
- Case 2: DENY — the user's department does not match the invoice department.
- Case 3: DENY — confidential classification is not permitted.
- Case 4: DENY — the device is not managed.

## 3. Additional attribute

One additional attribute could be resource ownership. The system could check whether the user owns the invoice before granting access. This would provide an additional level of security.
