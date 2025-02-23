# CodeSphere API Documentation

## Authentication

### POST /api/auth/login
Authenticate user and receive JWT token.

**Request:**
```json
{
    "username": "string",
    "password": "string"
}
```

**Response:**
```json
{
    "token": "string",
    "username": "string"
}
```

## Code Execution

### POST /api/code/execute
Execute code in specified language.

**Request:**
```json
{
    "code": "string",
    "language": "string",
    "input": "string"
}
```

**Response:**
```json
{
    "output": "string",
    "status": "SUCCESS|ERROR",
    "executionTime": "number"
}
```

## Code Conversion

### POST /api/ai/convert
Convert code between programming languages.

**Request:**
```json
{
    "sourceCode": "string",
    "sourceLanguage": "string",
    "targetLanguage": "string"
}
```

**Response:** Server-Sent Events stream of converted code.
```