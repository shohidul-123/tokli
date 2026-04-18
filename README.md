# Tokli Shopping Suite (User App + Admin App)

This repository contains two Android applications:

1. **User App** (`user-app`) for customers
2. **Admin Panel App** (`admin-app`) for administrators

Both apps use Firebase Authentication, Firestore, and Firebase Storage.

## Features Implemented

### User App
- Email/password registration and login
- Real-time product listing from Firestore
- Product details view (image, name, price, description)
- Add to cart
- Cart total calculation
- Place order to Firestore
- Order history with real-time updates

### Admin App
- Admin login (email gate: `@admin.com`)
- Add/update products (with Firebase Storage image upload pipeline)
- Delete products
- View all orders in real time
- Update order status (`Pending`, `Shipped`, `Delivered`)
- Dashboard summary (products count + orders count)

## Project Structure

- `user-app/` - customer-facing Android app
- `admin-app/` - admin Android app
- `shared-docs/` - setup references

## Firebase Data Model

### `products`
```json
{
  "id": "autoDocId",
  "name": "Wireless Headphones",
  "price": 49.99,
  "description": "Noise-cancelling over-ear headphones",
  "imageUrl": "https://..."
}
```

### `users`
```json
{
  "id": "uid",
  "name": "Jane Doe",
  "email": "jane@example.com"
}
```

### `orders`
```json
{
  "id": "autoDocId",
  "userId": "uid",
  "productList": [
    {
      "product": {
        "id": "productId",
        "name": "Item",
        "price": 10.5,
        "description": "...",
        "imageUrl": "..."
      },
      "quantity": 2
    }
  ],
  "totalPrice": 21.0,
  "status": "Pending",
  "timestamp": 1710000000000
}
```

## Step-by-Step Setup

1. Install latest Android Studio.
2. Open this repository root in Android Studio.
3. Create a Firebase project.
4. Add **two Android apps** in Firebase Console:
   - `com.tokli.userapp`
   - `com.tokli.adminapp`
5. Download both `google-services.json` files and place them in:
   - `user-app/google-services.json`
   - `admin-app/google-services.json`
6. In Firebase Console, enable:
   - Authentication > Email/Password
   - Firestore Database (production or test mode)
   - Storage
7. Build and run each module from Android Studio run configurations.

## Build APKs

From repository root:

```bash
./gradlew :user-app:assembleDebug
./gradlew :admin-app:assembleDebug
```

Expected output APK locations:

- `user-app/build/outputs/apk/debug/user-app-debug.apk`
- `admin-app/build/outputs/apk/debug/admin-app-debug.apk`

## Suggested Firestore Rules (Starter)

See `shared-docs/firebase-rules.md`.

## Beginner-Friendly + Scalable Design Notes

- Repositories isolate Firebase read/write concerns.
- `streamProducts` and `streamOrders` implement real-time sync.
- Activities are kept simple and separated by feature package.
- Models map closely to Firestore schema.

