
# Task D — Role-Based Access Control (RBAC)

## 1. Access decisions

| User | Assigned role | Requested action | Decision | Explanation |
|---|---|---|---|---|
| Alex | viewer | invoices:read | ALLOW | viewer has invoices:read permission. |
| Alex | viewer | invoices:update | DENY | viewer does not have invoices:update permission. |
| Sam | finance-analyst | reports:read | ALLOW | finance-analyst has reports:read permission. |
| Riley | billing-admin | users:manage | ALLOW | billing-admin has users:manage permission. |

## 2. Advantage of RBAC

One advantage of RBAC is centralized permission management. Administrators can assign roles to users instead of giving permissions individually.

## 3. Limitation of RBAC

One limitation is that RBAC does not easily consider contextual information, such as the user's department, device security or resource ownership. It can also become difficult to manage when many roles are required.
