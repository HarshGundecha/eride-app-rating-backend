- [Technical assesment - Harsh Gundecha](#technical-assesment---harsh-gundecha)
  - [All Assumptions made](#all-assumptions-made)
  - [Approach to your solution](#approach-to-your-solution)
    - [The **happy scenario** or **expected flow** is as follows](#the-happy-scenario-or-expected-flow-is-as-follows)
  - [DB schema or setup scripts if used](#db-schema-or-setup-scripts-if-used)
  - [Steps to run your application](#steps-to-run-your-application)
  - [Accessing application or code](#accessing-application-or-code)

# Technical assesment - Harsh Gundecha

## All Assumptions made
- In the app. it is assumed that there exists some functionality that gets a ride for a user and enters the ride related info. in DB which is currently simulated by inserting a record in ride table.
- Once a ride is accepted by the driver, it is assumed that the ride ends successfully after which both party can rate each another.

## Approach to your solution
- There's a single `User` class/entity with a `UserType` field to identify whether its a **passenger** or a **driver**. (though in ideal world we would either have separate tables or role based access that may have inhertance based user table).
- Then there's a `Ride` class/entity that stores the basic ride related info. for the app.
  - This includes rideId, driverId, passengerId, rating by both etc.

### The **happy scenario** or **expected flow** is as follows
- Some user & some driver registers into the system with `/register` endpoint.
- Note : After this point **all the calls** made will need a valid JWT token for the request to proceed or else system will return `401 UnAuthorised`
- Then the user can request for a new ride.
- Driver can view all available rides and can accept a ride request with `/ride` endpoint.
- Once the driver accepts the ride, it is assumed that the ride successfully ends
- After this both the parties are capable of rating each another as well as viewing their average rating by `/rating` endpoint.

## DB schema or setup scripts if used
- DB schema is auto generated from Entity/Class structure.

## Steps to run your application
- Clone application from https://github.com/HarshGundecha/eride-app-rating-backend.git
- Open in your favourite editor or run mvn run package
- Set required below environment variables
  ````properties
  SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/postgres
  SPRING_DATASOURCE_USERNAME:postgres
  SPRING_DATASOURCE_PASSWORD:postgres
  HIBERNATE_DDL_AUTO:create
  ````
- Run either from IDE by GUI or by Java -jar command with passing variables

## Accessing application or code
- The App is currnetly deployed on [Heroku](https://e-ride-app-rating-backend.herokuapp.com) free tier and can be access by https://e-ride-app-rating-backend.herokuapp.com
- The [Postman collection](https://www.getpostman.com/collections/04a9e4ab8e92ac75b979) for the same can be found here https://www.getpostman.com/collections/04a9e4ab8e92ac75b979
  - Was about to add swagger document but due to workload at current company, wasn't able to do so
- The Source code can be found on [Github](https://github.com/HarshGundecha/eride-app-rating-backend) at https://github.com/HarshGundecha/eride-app-rating-backend