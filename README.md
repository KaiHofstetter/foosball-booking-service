Foosball Booking Service
========================
The Foosball Booking Service is a short OAuth 2.0 sample REST service.
It provides the following REST API:

|Function            |API                         |
|--------------------|----------------------------|
|Get list of bookings|GET /bookings               |
|Add booking         |POST /bookings              |
|Get booking         |GET /bookings/{bookingId}   |
|Delete booking      |DELETE /bookings/{bookingId}|

The API is secured via OAuth 2.0 using Spring Security OAuth.

Getting Started 
===============
* You need Java JDK 8 and Maven 3.* to build and run the foosball-booking-service. 
* Simply build and run it with mvn tomcat7:run
