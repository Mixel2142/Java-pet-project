# Java-pet-project (Blogs)
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
---
### Token agreement ###
> Описание того, как используются служебные поля в _JWT_.
 - ___sub___ - Хранит __Id__ пользователя.
 - ___aud___ - Хранит тип токена: __access/refresh__.
 - ___claims___ - Хранит: __roles__ & __credentials__.
    - _"roles": "USER,ADMIN,WRITER"_
    - _"cred": "some,some"_
    
    
---
### Short Description of Services ###
#### Gateay ####
 - Балансировка запросов между сервисами.
 - Требует добавлять токены в запросы.
 - Проверяет токены на устаревание.
 - Отсеивает запросы имеющие неправомерные uri.

#### AuthService ####
 - Хранит Id, Login, Password, Roles, Credentials, AccessToken, RefreshToken  пользователя.
 - Генерирует новые пары токенов для пользователя (по паролю и refreshToken).
 
#### FileStorage ####
 - Хранит файлы.
 - Отдаёт файлы по id.
 - Удаляет файлы, если те устарели.
 
#### EmailSender ####
 - Отправляет письма на почту

#### Monolit ####
 - Бизнес логика.

---
## Short list of technologies were used:
* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring Web MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html) - The web framework used
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa) - The web framework used
* [Spring Security](https://spring.io/projects/spring-security) - Authentication and access-control
* [PostgreSQL](https://www.postgresql.org/) - SQL DB
* [Lombok](https://projectlombok.org/) - Code generator
* [Jsoup](https://jsoup.org/) - for working with real-world HTML
* [Gson](https://github.com/google/gson) - for working with json
* [Jwt](https://github.com/jwtk/jjwt) - for working with jwt tokens
* [Mockito](https://site.mockito.org/) -  is a mocking framework
* [Open Api 3](https://github.com/springdoc/springdoc-openapi) -  automating the generation of API documentation
* [Log4j2](https://logging.apache.org/log4j/2.x/) -  logging
* [Junit](https://junit.org/junit5/) -  testing
* [Graphql](https://graphql.org/) - interactions between front and database
* [Eureka](https://github.com/Netflix/eureka) - communication server for services
* [Redis](https://redis.io/) - NOSQL for caching
* [Docker](https://www.docker.com/) - images, compose, swarm
* [FlyWay](https://flywaydb.org/) - Version control for your database. Robust schema evolution across all your environments.
* [WebFlux](https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html) - "Reactive Spring MVC "

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
