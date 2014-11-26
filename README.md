Foosball Booking Service
========================
The Foosball Booking Service is a short OAuth 2.0 sample REST service.
The API is secured via OAuth 2.0 using Spring Security OAuth.
It provides the following REST API:

|Function             |API                         |Needed OAuth 2.0 Scope |
|---------------------|----------------------------|-----------------------|
|Get list of bookings |GET /bookings               |Read_Booking_List      |
|Add booking          |POST /bookings              |Add_Booking            |

Configured OAuth 2.0 Clients:

|Client Id                          |Client Secret |Configured Grants  |Configured Scopes              |
|-----------------------------------|--------------|-------------------|-------------------------------|
|Foosball Booking Read Client       |secret        |Client Credentials |Read_Booking_List              |
|Foosball Booking Read/Write Client |secret        |Authorization Code |Read_Booking_List, Add_Booking |

Getting Started 
===============
* You need Java JDK 8 and Maven 3.* to build and run the foosball-booking-service. 
* Simply build and run it with mvn tomcat7:run

Client Credential Grant Sample
==============================

1. Get an access token:
```HTTP
curl -X POST -H "Authorization: Basic Rm9vc2JhbGwgQm9va2luZyBSZWFkIENsaWVudDpzZWNyZXQ=" -H "Content-Type: application/x-www-form-urlencoded" -d 'grant_type=client_credentials' http://localhost:8080/foosball-booking-service/oauth/token
```

2. Request the booking list:
Copy the returned access token from step 1 in the authorization header:
```HTTP
curl -X GET -H "Authorization: Bearer <returned bearer token>" -H "Accept: application/json" -H "Cache-Control: no-cache" http://localhost:8080/foosball-booking-service/bookings
```

Authorization Code Grant Sample
===============================
For a Authorization Code Grant sample try the Foosball Booking Client (https://github.com/KaiHofstetter/foosball-booking-service)

 

