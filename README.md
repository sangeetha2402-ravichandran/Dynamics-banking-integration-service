# GBank Banking Integration Platform — Azure Implementation

## Overview

This document describes the Azure work completed for the **GBank Banking Integration Platform**.

The goal of this Azure implementation is to host a Java Spring Boot banking backend in Azure, expose the APIs through Azure API Management, and secure those APIs using Microsoft Entra ID with OAuth 2.0 client credentials.

The current Azure solution supports the following Spring Boot APIs:

- Customer API
- Transaction API
- Fraud Case API
- Loan API

The backend is deployed to Azure App Service, documented using Swagger/OpenAPI, imported into Azure API Management, and protected using Microsoft Entra ID access tokens, application roles, JWT validation, and APIM subscription keys.

---

## Current Azure Architecture

```text
Postman / Future Dynamics 365 / Copilot
                |
                | Client ID + Client Secret
                v
        Microsoft Entra ID
                |
                | OAuth 2.0 Access Token
                v
      Azure API Management (APIM)
                |
                | Validate JWT
                | Validate Audience
                | Validate Api.Access Role
                | Validate Subscription Key
                v
        Azure App Service
                |
                v
        Java Spring Boot
                |
                v
    Customer / Transaction /
      Fraud / Loan APIs
                |
                v
            H2 Database
```

---

# 1. Azure Subscription

The Azure environment was created using:

```text
Subscription: Azure for Students
Region: Australia East
```

A separate Azure resource group was created for the banking project.

```text
Resource Group: gbank-banking-api-san_group
```

The resource group is used to keep the banking project Azure resources together.

---

# 2. Azure App Service

## Purpose

Azure App Service is used to **host and run the Spring Boot backend application**.

Before deployment, the Spring Boot APIs were available only locally:

```text
http://localhost:8080
```

After deployment to Azure App Service, the APIs became available through a public HTTPS endpoint.

Conceptually:

```text
Local Spring Boot
      |
      v
Build JAR
      |
      v
Azure App Service
      |
      v
Public HTTPS API
```

## App Service Configuration

The App Service was created with settings similar to:

```text
App Name: gbank-banking-api-san
Publish: Code
Runtime: Java 17
Web Server Stack: Java SE
Operating System: Linux
Region: Australia East
Pricing Tier: Free F1
```

Java SE was selected because the Spring Boot application already contains its embedded web server.

---


### Azure App Service — Portal Screenshot

The screenshot below shows the deployed **`gbank-banking-api-san`** App Service running in Azure with:

- **Status:** Running
- **Runtime:** Java 17 SE
- **Operating System:** Linux
- **Region:** Australia East
- **Deployment:** Successful

![Azure App Service overview](assets/azure-app-service-overview.png)

# 3. Building the Spring Boot Application

The Spring Boot application was packaged as an executable JAR using Maven.

Command:

```cmd
mvnw.cmd clean package
```

This generated a JAR similar to:

```text
target/banking-integration-service-0.0.1-SNAPSHOT.jar
```

The JAR contains the compiled Spring Boot application including controllers, services, repositories, entities, configuration, and dependencies.

---

# 4. Azure Cloud Shell Deployment

Azure Cloud Shell was used to deploy the JAR to Azure App Service.

The JAR was uploaded to Cloud Shell and deployed using Azure CLI.

Example command:

```bash
az webapp deploy   --resource-group gbank-banking-api-san_group   --name gbank-banking-api-san   --src-path banking-integration-service-0.0.1-SNAPSHOT.jar   --type jar
```

The deployment completed successfully.

The deployment result confirmed:

```text
numberOfInstancesFailed: 0
numberOfInstancesSuccessful: 1
status: RuntimeSuccessful
```

---

# 5. Public API Testing

After deployment, the Spring Boot application was tested using the Azure App Service default domain.

Example endpoint:

```text
GET https://<app-service-domain>/api/customers/CUST1001
```

The endpoint returned customer data successfully.

Example response:

```json
{
  "customerId": "CUST1001",
  "firstName": "John",
  "lastName": "Smith",
  "email": "john.smith@email.com",
  "phone": "0211234567",
  "customerType": "PREMIUM",
  "vip": true
}
```

This confirmed:

```text
Internet Client
      |
      v
Azure App Service
      |
      v
Spring Boot
      |
      v
Database
```

was working correctly.

---

# 6. Swagger and OpenAPI

Swagger/OpenAPI was enabled in the Spring Boot application.

The OpenAPI documentation is available from:

```text
/v3/api-docs
```

Swagger UI is available from:

```text
/swagger-ui/index.html
```

The OpenAPI definition describes the Spring Boot banking endpoints.

Examples include:

```text
GET  /api/customers/{customerId}
GET  /api/customers/{customerId}/transactions
GET  /api/transactions/{transactionId}
GET  /api/customers/{customerId}/loans
GET  /api/loans/{loanId}
POST /api/fraud-cases
GET  /api/fraud-cases/{caseId}
```

---

# 7. Azure API Management

## Purpose

Azure API Management is used as the **API gateway** in front of Azure App Service.

Without APIM:

```text
Client
  |
  v
Azure App Service
  |
  v
Spring Boot
```

With APIM:

```text
Client
  |
  v
Azure API Management
  |
  v
Azure App Service
  |
  v
Spring Boot
```

APIM provides a central place for:

- Authentication
- Authorization
- Subscription keys
- JWT validation
- Rate limiting
- API policies
- API versioning
- Monitoring
- Request routing

---

# 8. APIM Service Creation

An Azure API Management service was created.

```text
APIM Name: gbank-apim-san
Region: Australia East
Resource Group: gbank-banking-api-san_group
```

Provisioning took significantly longer than App Service because APIM creates API gateway and management infrastructure.

---


### Azure API Management — Portal Screenshot

The screenshot below shows the **`gbank-apim-san`** API Management service online in Azure. It also shows the APIM Gateway URL and Developer tier configuration used for this project.

![Azure API Management overview](assets/azure-apim-overview.png)

# 9. Importing OpenAPI into APIM

The Spring Boot OpenAPI document was imported into APIM.

The OpenAPI URL follows this pattern:

```text
https://<app-service-domain>/v3/api-docs
```

APIM used this document to automatically discover the Spring Boot operations.

After import, APIM displayed real API operations such as:

```text
createFraudCase
getCustomer
getFraudCaseById
getLoanById
getLoansByCustomer
getTransactionById
getTransactionsByCustomer
```

APIM was configured so that its backend points to the Azure App Service hosting the Spring Boot application.

---

# 10. APIM Routing Test

The Customer API was tested directly from the APIM Test console.

Example:

```text
GET /api/customers/CUST1001
```

The result was:

```text
HTTP/1.1 200 OK
```

Example response:

```json
{
  "customerId": "CUST1001",
  "firstName": "John",
  "lastName": "Smith",
  "email": "john.smith@email.com",
  "phone": "0211234567",
  "customerType": "PREMIUM",
  "vip": true
}
```

This confirmed the routing path:

```text
APIM
  |
  v
Azure App Service
  |
  v
Spring Boot
  |
  v
Database
```

---

# 11. Microsoft Entra ID Security

Microsoft Entra ID was configured to secure the banking APIs using OAuth 2.0 client credentials.

Two app registrations were created.

---

# 12. App Registration 1 — gbank-api-client

## Purpose

`gbank-api-client` represents the **calling application**.

Examples of callers could be:

- Postman
- Dynamics 365
- Copilot Studio
- Power Automate
- Another backend service

This registration provides the client identity used to request an access token.

Important values:

```text
Tenant ID
Client ID
Client Secret
```

Meaning:

```text
Tenant ID     = Which Entra directory the application belongs to
Client ID     = Application identity
Client Secret = Application credential/password
```

The Client Secret must never be committed to GitHub or stored in source code.

---

# 13. App Registration 2 — gbank-api

## Purpose

`gbank-api` represents the **protected banking API**.

In simple terms:

```text
gbank-api-client = WHO is calling

gbank-api        = WHAT is being called
```

An Application ID URI was configured for the protected API.

```text
api://<gbank-api-application-id>
```

For this project the API URI was configured in the same format.

---

# 14. Application Role — Api.Access

An application role was created on `gbank-api`.

```text
Display Name: Api.Access
Value: Api.Access
Allowed Member Type: Applications
```

This role represents permission to access the banking API.

Conceptually:

```text
gbank-api-client
        |
        | Api.Access
        v
     gbank-api
```

---

# 15. API Permission Configuration

The `gbank-api-client` application was given application permission to access `gbank-api`.

The permission path was:

```text
gbank-api-client
    |
    v
API Permissions
    |
    v
My APIs
    |
    v
gbank-api
    |
    v
Application Permissions
    |
    v
Api.Access
```

Admin consent was granted for the application permission.

---


### Microsoft Entra ID — API Permission Screenshot

The screenshot below shows the **`gbank-api-client`** application permission configuration.  
The client application has the **`Api.Access`** application permission for **`gbank-api`**, and admin consent has been granted.

![Microsoft Entra ID API permissions](assets/entra-api-permissions.png)

# 16. OAuth 2.0 Client Credentials Flow

Postman was used to request an access token from Microsoft Entra ID.

Token endpoint pattern:

```text
POST https://login.microsoftonline.com/<tenant-id>/oauth2/v2.0/token
```

Body type:

```text
x-www-form-urlencoded
```

Parameters:

```text
grant_type    = client_credentials
client_id     = <gbank-api-client-client-id>
client_secret = <gbank-api-client-secret>
scope         = api://<gbank-api-application-id>/.default
```

Entra ID validated:

```text
Is the Client ID valid?
Is the Client Secret valid?
Does the client have Api.Access permission?
Is the requested API valid?
```

If all checks passed, Entra ID returned an OAuth access token.

Example response structure:

```json
{
  "token_type": "Bearer",
  "expires_in": 3599,
  "access_token": "<JWT_ACCESS_TOKEN>"
}
```

No real secret or access token should ever be stored in this README.

---

# 17. Authentication vs Authorization

The project uses both authentication and authorization.

## Authentication

Authentication answers:

```text
WHO are you?
```

The caller proves its identity using:

```text
Client ID
Client Secret
```

Microsoft Entra ID then issues an access token.

## Authorization

Authorization answers:

```text
WHAT are you allowed to access?
```

The protected API defines:

```text
Api.Access
```

The caller must have this application role.

---

# 18. APIM JWT Validation

APIM was configured to validate the Entra ID access token before forwarding the request to Spring Boot.

The APIM inbound policy validates:

- Tenant
- Token validity
- API audience
- `Api.Access` role

Conceptual policy:

```xml
<validate-azure-ad-token
    tenant-id="<TENANT-ID>"
    failed-validation-httpcode="401"
    failed-validation-error-message="Unauthorized">

    <audiences>
        <audience>api://<GBANK-API-APPLICATION-ID></audience>
    </audiences>

    <required-claims>
        <claim name="roles" match="any">
            <value>Api.Access</value>
        </claim>
    </required-claims>

</validate-azure-ad-token>
```

Secrets are not stored in the APIM inbound policy.

---

# 19. Why JWT Validation Is Required

Before JWT validation:

```text
Anyone who knows the APIM URL
        |
        v
       APIM
        |
        v
Spring Boot API
```

After JWT validation:

```text
Client
  |
  | Bearer Token
  v
APIM
  |
  | Validate Token
  | Validate Audience
  | Validate Api.Access
  v
If valid
  |
  v
Spring Boot

If invalid
  |
  v
401 Unauthorized
```

---

# 20. APIM Subscription Key

APIM was also configured to require a subscription key.

When Postman called the APIM gateway with only the Bearer token, APIM returned:

```text
Access denied due to missing subscription key.
```

The APIM subscription key was then added using the request header:

```text
Ocp-Apim-Subscription-Key: <APIM_SUBSCRIPTION_KEY>
```

The final request therefore contains both:

```text
Authorization: Bearer <ACCESS_TOKEN>
Ocp-Apim-Subscription-Key: <APIM_SUBSCRIPTION_KEY>
```

The subscription key must not be committed to source control.

---

# 21. Final Secured Postman Test

The final secured request was tested successfully from Postman.

Flow:

```text
Postman
   |
   | Bearer Access Token
   | APIM Subscription Key
   v
Azure API Management
   |
   | Validate Subscription
   | Validate JWT
   | Validate Audience
   | Validate Api.Access
   v
Azure App Service
   |
   v
Spring Boot
   |
   v
Customer API
   |
   v
200 OK
```

Expected security behaviour:

```text
No token             -> 401 Unauthorized
Invalid token        -> 401 Unauthorized
Missing subscription -> APIM subscription-key error
Valid token + key    -> 200 OK
```

---

