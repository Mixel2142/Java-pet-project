package portal.education.AuthService.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import portal.education.AuthService.domain.AuthData;
import portal.education.AuthService.dto.AccountDto;
import portal.education.AuthService.dto.PairTokenDto;
import portal.education.AuthService.repo.AuthRepo;
import portal.education.AuthService.tokenFacilities.JwtUtilForServer;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    @NonNull
    private AuthRepo authRepo;

    @Autowired
    @NonNull
    private PasswordEncoder encoder;

    @SneakyThrows
    public Mono<PairTokenDto> updateAuthData(AuthData data) {
        Map<String, String> claims = Map.of(
                "roles",data.getRoles(),
                "cred",data.getCredentials()
        );

        String refreshTkn = JwtUtilForServer.generateDataPayloadToken(data.getUserId().toString(), "refresh", claims, JwtUtilForServer.REFRESHTOKEN_TTL);
        String accessTkn = JwtUtilForServer.generateDataPayloadToken(data.getUserId().toString(), "access", claims, JwtUtilForServer.ACCESSTOKEN_TTL);

        data.setAccessToken(accessTkn);
        data.setRefreshToken(refreshTkn);
        data.setPassword(encoder.encode(data.getPassword()));

        return authRepo.save(data).flatMap(result ->
                Mono.just(
                        PairTokenDto.builder()
                                .accessToken(result.getAccessToken())
                                .refreshToken(result.getRefreshToken())
                                .build()
                )
        );
    }

    public Mono<PairTokenDto> createAuthData(AuthData data) {
        return updateAuthData(data);
    }

    public Mono<PairTokenDto> generatePairFromRefreshToken(String refreshToken) {

        if (JwtUtilForServer.isTokenExpired(refreshToken) && JwtUtilForServer.isOneTypeToken(refreshToken, "refresh")) {

            return authRepo.findById(
                    UUID.fromString(
                            JwtUtilForServer.getUserIdFromToken(refreshToken)
                    )
            ).flatMap(result -> this.updateAuthData(result));
        }
        throw new IllegalArgumentException("Refresh token is not valid!");
    }

    public Mono<PairTokenDto> generatePairFromLoginAndPassword(AccountDto accountDto) {

        return authRepo.findByLogin(accountDto.getLogin())
                .flatMap(authData -> {
                    if (encoder.matches(accountDto.getPassword(), authData.getPassword())) {
                        return this.updateAuthData(authData);
                    }
                    throw new IllegalArgumentException("Password is not valid!");
                });
    }
}
