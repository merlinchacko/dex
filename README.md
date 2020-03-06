### Assignment
Using your favorite java framework / libraries build a service, that will accept a request with text parameter on input.

It will return maximum of 5 books and maximum of 5 albums that are related to the input term. The response elements will only contain title, authors(/artists) and information whether it's a book or an album.

###  Features
1. Retrieve maximum of 5 books and albums related to searched keyword and result is sorted based on the title.
2. Exposes health check (http://localhost:8080/actuator/health).
3. Exposes metrics (http://localhost:8080/actuator/prometheus).
4. Used Swagger for documentation (http://localhost:8080/swagger-ui.html).
5. Used Circuit-Breakers for achieving resilience.
6. Used caching for retrieve data faster as there are external API calls.

###  Data Definition
##### MediaInfo 
* Title String 
* Authors String[]
* MediaType String [BOOK, ALBUM]

###  Description of Application
I have implemented the application based on microservice architecture using Spting boot technology in Java8.
This gives the flexibility to design better-fit solutions in a more cost-effective way. What I like most on microservices is, 
they are smaller in size and have minimal dependencies, they allow the migration of services that use end-of-life
 technologies with minimal cost.

This application consist of both frontend and backend.Backend is a rest API to retrieve the results using Google Books API and iTunes Search Api. 
You will get a collection of MediaInfo(with 5 books and 5 albums based on the input keyword). Currently the rest api is fetching results on 
a limit of 5, but it can configured in application.yaml file. For production/test purpose use application-production.yaml/application-test.yaml,
 update the limit as needed.
 
Frontend is a seprate angular app with a UI , where user can search for a keyword. 
It will then list the results in a table format for valid response and shows warning message for 
invalid responses.

I have added unit test for endpoints and services. Also one to check the circuit breaker.  

###  Technologies used
#### Backend
Java 8, Spring Boot, Gradle

* It provides an easy way to manage REST endpoints.
* Everything is auto-configured; no manual configurations are needed.
* It offers annotation-based spring application
* Easy to understand and develop

#### Frontend
Angular8, HTML,css etc

* It offers clean code development
* Higher Performance
* Support TypeScript 3.4

### Deployment Instructions
 
1. Clone the git repo “https://github.com/merlinchacko/media-search.git” locally.
2. Run command "./gradlew clean build".
3. Run command "./gradlew bootRun" for running the backend.
5. Run command " ng serve --open" for running the frontend. 
    This will open a browser locally with url "http://localhost:4200/"
