//package portal.education.AuthService.controller;
//
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import org.springframework.web.reactive.function.BodyInserters;
//import portal.education.AuthService.domain.AuthData;
//import portal.education.AuthService.repo.AuthRepo;
//
//import java.util.UUID;
//
//@ExtendWith(SpringExtension.class)
////  We create a `@SpringBootTest`, starting an actual server on a `RANDOM_PORT`
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class RegistrationControllerTest {
//
//
//    // Spring Boot will create a `WebTestClient` for you,
//    // already configure and ready to issue requests against "localhost:RANDOM_PORT"
//    @Autowired
//    private WebTestClient webTestClient;
//
//    @Autowired
//    private AuthRepo authRepo;
//
//    static UUID uuid;
//
//    @BeforeAll
//    static void init() {
//        uuid = UUID.randomUUID();
//    }
//
//
//    @Test
//    @Order(1)
//    void doCreateAuthData() {
//        authRepo.deleteAll();
//        AuthData data = AuthData.builder()
//                .userId(uuid)
//                .login("login")
//                .password("password")
//                .roles("ROLE_USER|ROLE_ADMIN|ROLE_WRITER")
//                .credentials("WRITE")
//                .accessToken("accessToken")
//                .refreshToken("refreshToken")
//                .build();
//
//        webTestClient.post().uri("/auth/data")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromObject(data))
//                .exchange()
//                // and use the dedicated DSL to test assertions against the response
//                .expectStatus().isOk();
//
//    }
//
//    @Test
//    @Order(2)
//    void doUpdateAuthData() {
//        AuthData data = AuthData.builder()
//                .userId(uuid)
//                .login("login2")
//                .password("password2")
//                .roles("ROLE_USER|ROLE_ADMIN|ROLE_WRITER2")
//                .credentials("WRITE2")
//                .build();
//
//        webTestClient.patch().uri("/auth/data")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromObject(data))
//                .exchange()
//                // and use the dedicated DSL to test assertions against the response
//                .expectStatus().isOk();
//
//    }
//
//
////    @Test
////    @Order(9)
////    void getAuthDataAfterCreate() {
////
////        webTestClient.get().uri("/auth/data?id=" + uuid)
////                .exchange()
////                // and use the dedicated DSL to test assertions against the response
////                .expectStatus().isOk()
////                .expectBody(String.class);
////
////    }
//
//    @Test
//    @Order(3)
//    void removeRegistryAccount() {
//        webTestClient.delete().uri("/auth/data?id=" + uuid)
//                .exchange()
//                // and use the dedicated DSL to test assertions against the response
//                .expectStatus().isOk();
//    }
////
////    @Test
////    void updateAccessToken() {
////    }
////
////    @Test
////    void testUpdateAccessToken() {
////    }
//}