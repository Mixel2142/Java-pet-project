# Java-pet-project
This project helps me to learn java-technologies in which I am interested in practice.
This project is a backend server that is built on a Spring Boot v 2.2.6. The server provides storage of user credentials, their articles and various searches on them.
---
## How to run backend?

```bash
% Go to root directory
#promt> cd Backend\

%            Build JARs
% For Linux
#promt> mvn clean install spring-boot:repackage -Dmaven.test.skip=true

% For Windows
#promt>

% Run docker-compose
#promt> docker-compose up --build  
OR
#promt> docker-compose -f docker-compose-dev.yml up --build

% Check links:
% http://localhost:8090/api/free/swagger-ui.html 
% http://localhost:8090/api/free/graphiql 
% http://localhost:8761/admin 
```
---
### Schema ###
![markdown logo](./schema.png)
## Short list of technologies were used:
* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring Web MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html) - The web framework used
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa) - The web framework used
* [Spring Security](https://spring.io/projects/spring-security) - Authentication and access-control
* [PostgreSQL](https://www.postgresql.org/) - SQL DB
* [Lombok](https://projectlombok.org/) - Code generator
* [Jsoup](https://jsoup.org/) - for working with real-world HTML
* [Gson](https://github.com/google/gson) - for working with json
* [Jjwt](https://github.com/jwtk/jjwt) - for working with jwt tokens
* [Mockito](https://site.mockito.org/) -  is a mocking framework
* [Open Api 3](https://github.com/springdoc/springdoc-openapi) -  automating the generation of API documentation
* [Log4j2](https://logging.apache.org/log4j/2.x/) -  logging
* [Junit](https://junit.org/junit5/) -  testing
* [Graphql](https://graphql.org/) - interactions between front and database
* [Eureka](https://github.com/Netflix/eureka) - communication server for services
* [Redis](https://redis.io/) - NOSQL for caching
* [Docker](https://www.docker.com/) - images, compose, swarm

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
