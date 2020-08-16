# Backend API Documentation
This document details the current api endpoints (implemented and to-be
implemented), and their usage information.

## 1. Authentication

### 1.1. Check Authentication
```
GET /api/auth
```

#### Response Structure
```
200 OK
{
    "status": "OK",
    "message": "Successfully authorized."
}

401 Unauthorized
{
    "status": "UNAUTHORIZED",
    "message": "No valid user session found."
}
```

### 1.2. Logout
```
POST /api/auth/logout
```

#### Response Structure
```
200 OK
{
    "status": "OK",
    "message": "Successfully logged out."
}

401 Unauthorized
{
    "status": "UNAUTHORIZED",
    "message": "No valid user session found."
}
```

### 1.3. Owner Login
```
POST /api/auth/owner/login
```

#### Request Structure
```
{
    "username": "string",
    "password": "string"
}
```

#### Response Structure
```
200 OK
{
    "status": "OK",
    "message": "Successfully logged in."
}

401 Unauthorized
{
    "status": "UNAUTHORIZED",
    "message": "Invalid username or password."
}
```

### 1.4. Owner Registration
```
POST /api/auth/owner/register
```

#### Request Structure
```
{
    "username": "string",
    "password": "string",
    "checkPassword": "string"
}
```

#### Response Structure
```
201 Created
{
    "status": "CREATED",
    "message": "Owner successfully created."
}

400 Bad Request
{
    "status": "BAD REQUEST",
    "message": "Provided passwords do not match."
}

409 Conflict
{
    "status": "CONFLICT",
    "message": "An owner with that username already exists."
}
```

### 1.5. Customer Login
```
POST /api/auth/customer/login
```

#### Request Structure
```
{
    "username": "string",
    "password": "string"
}
```

#### Response Structure
```
200 OK
{
    "status": "OK",
    "message": "Successfully logged in."
}

401 Unauthorized
{
    "status": "UNAUTHORIZED",
    "message": "Invalid username or password."
}
```

### 1.6. Customer Registration
```
POST /api/auth/customer/register
```

#### Request Structure
```
{
    "username": "string",
    "password": "string",
    "checkPassword": "string",
}
```

#### Response Structure
```
201 Created
{
    "status": "OK",
    "message": "Customer successfully created."
}

400 Bad Request
{
    "status": "BAD REQUEST",
    "message": "Provided passwords do not match."
}

409 Conflict
{
    "status": "CONFLICT",
    "message": "A customer with that username already exists."
}
```

### 1.7. Worker Login
```
POST /api/auth/worker/login
```

#### Request Structure
```
{
    "username": "string",
    "password": "string"
}
```

#### Response Structure
```
200 OK
{
    "status": "OK",
    "message": "Successfully logged in."
}

401 Unauthorized
{
    "status": "UNAUTHORIZED",
    "message": "Invalid username or password."
}
```

### 1.8. Worker Registration
```
POST /api/auth/worker/register
```

#### Request Structure
```
{
    "username": "string",
    "password": "string",
    "checkPassword": "string"
}
```

#### Response Structure
```
200 OK
{
    "status": "OK",
    "message": "Worker successfully created."
}

400 Bad Request
{
    "status": "BAD REQUEST",
    "message": "Provided passwords do not match."
}

409 Conflict
{
    "status": "CONFLICT",
    "message": "A worker with that username already exists."
}
```

