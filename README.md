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
|Foosball Booking Read/Write Client |secret        |Authorization Code, Implicit, Resource Owner Password Credentials |Read_Booking_List, Add_Booking |

Configured Users:
|User Name |User Password|
|Peter     |secret|
|Bob     |secret|

These users can be used for the Authorization Code Grant, Implicit Grant und Resource Owner Password Credentials Grant.

Getting Started 
===============
* You need Java JDK 8 and Maven 3.* to build and run the foosball-booking-service. 
* Simply build and run it with mvn tomcat7:run

Client Credential Grant Sample
==============================

1. Get an access token:
   ```HTTP
   curl -X POST -H "Authorization: Basic Rm9vc2JhbGwgQm9va2luZyBSZWFkIENsaWVudDpzZWNyZXQ=" -H "Content-Type:    application/x-www-form-urlencoded" -d 'grant_type=client_credentials' http://localhost:8080/foosball-booking-service/oauth/token
   ```
   
2. Request the booking list:
   Copy the returned access token from step 1 in the authorization header:
   ```HTTP
   curl -X GET -H "Authorization: Bearer <returned bearer token>" -H "Accept: application/json" -H "Cache-Control: no-cache" http://localhost:8080/foosball-booking-service/bookings
   ```

Authorization Code Grant Sample
===============================
For a Authorization Code Grant sample try the Foosball Booking Client (https://github.com/KaiHofstetter/foosball-booking-service)

Postman Samples
===============
Postman is a handy REST client, running as a Chrome browser app. It can be found in the Google Chrome Web Store. Please use the packaged app.

The dump file (postman/Backup.postman_dump) can be used to import the requests into Postman. Go to Settings/Data/Import Data and simply import Backup.postman_dump.

Request an access token with one of the 4 grants. Use this token to either get the list of bookings or add a booking to the list. The access token from the Client Credentials Grant just has permission to get the booking list! The request for an Authorization Code with the Authorization Code Grant cannot be done with Postman, since Postman can not follow redirets. Simply copy the URL from Postman in a browser and copy the returend code from the browser back to Postman to request an access token. The same goes for requesting an access token with the Implicit Grant.  




 

