# About spring_auth_service
Microservice providing basic authorization.

## Run the application
To run the application, run the following command in a terminal window directory:
`./gradlew bootRun`

## Demo

### Login as admin:
`POST` /api/auth/login
```json
{
  "username": "admin",
  "password": "12345678"
}
```

### Login as journalist:
`POST` /api/auth/login
```json
{
  "username": "journalist",
  "password": "12345678"
}
```

### Login as user:
`POST` /api/auth/login
```json
{
  "username": "user",
  "password": "12345678"
}
```
