# Firebase Rules (Starter)

## Firestore Rules

```txt
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {

    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }

    match /products/{productId} {
      allow read: if request.auth != null;
      allow write: if request.auth != null && request.auth.token.email.matches('.*@admin.com$');
    }

    match /orders/{orderId} {
      allow create: if request.auth != null;
      allow read: if request.auth != null &&
        (
          resource.data.userId == request.auth.uid ||
          request.auth.token.email.matches('.*@admin.com$')
        );
      allow update: if request.auth != null && request.auth.token.email.matches('.*@admin.com$');
    }
  }
}
```

## Storage Rules

```txt
rules_version = '2';
service firebase.storage {
  match /b/{bucket}/o {
    match /products/{allPaths=**} {
      allow read: if request.auth != null;
      allow write: if request.auth != null && request.auth.token.email.matches('.*@admin.com$');
    }
  }
}
```
