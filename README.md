# Transformers War
Java Backend Developer Technical Test

### Tech
  * [Swagger] 
  * [H2 Database] 
  * [Spring Boot] 
  * [Maven] 
  * [Mockito]

### REST API

| Method   |        Path                              |
|----------|:----------------------------------------:|
| GET      |    /api/transformers                     |
| POST     |    /api/transformers                     |
| DELETE   | /api/transformers/deleteAll              |
| POST     | /api/transformers/random/{fightersNumber}|
| DELETE   |    /api/transformers/{id}                |
| GET      |    /api/transformers/{id}                |
| PUT      |    api/transformers/{id}                 |
| GET      |    /api/transformers/war                 |
| POST     |    /api/transformers/war                 |
| GET      |    /api/transformers/war/noverbose       |

### Some considerations

- All CRUD methods for maintaining transformers have been implemented.
- I created the paged search already imagining that there could be a situation of having a large number of transformers.
- An option to create random transformers was implemented.
- I created requests related to the execution of the war.
  - It is possible to see the result of the war after executing the war-related GET methods
  - An option has been implemented to bring in more detail or not the result of the war.

- I made the implementation of the battles to support a good number of transforming fighters.
-  Despite this, I have set a minimum value, at least in the option of generating random transformers, of 50,0000 transformers that can fight in a battle.

- I assumed that the order of precedence of the rules
  - "Transformer named Optimus Prime or Predaking face each other (or a duplicate of each other), the game immediately ends with all competitors destroyed"
  - "Transformer named Optimus Prime or Predaking wins his fight automatically"
  - "Fighter is down 4 or more points of courage and 3 or more points of strength compared to their opponent"
  - "Fighter is 3 or more points of skill above their opponent"
  - "Default Rule"

- It is possible to consult the result of the last battle until starting a new one. Battles have a persistence control in the database.

- Until the previous battle is over, it is not possible to create a new one.

- I configured a Documentation API using swagger. It is available in the /swagger-ui.html path.

### Prerequisites

- Maven
- Java 8

### How to run

- At the root of the project:

  - Running the jar packed by maven;
    ```sh
    $ mvn clean package
    $ java -jar target/transformers-0.0.1-SNAPSHOT.jar 
    ``` 

  - Using the maven spring boot plugin;
    ```sh
    $ mvn spring-boot:run
    ```

  - Or Providing a docker image and deploying anywhere
    ```sh
    $ mvn clean package
    $ docker build -f Dockerfile  -t transformers .
    $ docker run -p 8080:8080 transformers
    ``` 

[Swagger]: <https://swagger.io/>
[H2 Database]: <http://www.h2database.com/>
[Spring Boot]: <https://spring.io/>
[Maven]: <https://maven.apache.org/>
[Mockito]: <https://site.mockito.org/>