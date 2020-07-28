package portal.education.AuthService.config;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import portal.education.AuthService.tokenFacilities.JwtUtilForServer;
import portal.education.serialize.MyGrantedAuthority;
import portal.education.serialize.MyUserDetails;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Component
@Slf4j
public class SecurityContextRepository implements ServerSecurityContextRepository {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange serverWebExchange, SecurityContext securityContext) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange swe) {
        String token = swe.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);


        if (token != null) {
            AuthData authData = null;

            if (token.startsWith("Bearer ")) {
                String jwt = token.substring(7);
                authData = initVariablesForAuthentication(jwt);


                if (authData.getTypeToken().equals("access"))
                    return this.authenticationManager
                            .authenticate(
                                    (Authentication) buildPreAuthenticatedAuthenticationToken(
                                            authData.getUserUuid(),
                                            authData.getRoles(),
                                            authData.getCredentials()
                                    )
                            ).map(
                                    (authentication) -> new SecurityContextImpl(authentication)
                            );
            }
            if (token.startsWith("Basic ")) {

            }
        }
        return Mono.empty();
    }

    @Data
    @Builder
    static class AuthData {
        private String typeToken, roles, credentials, userUuid;
    }

    private AuthData initVariablesForAuthentication(String jwt) {
        try {
            Claims claims = JwtUtilForServer.getAllClaimsFromToken(jwt);
            return AuthData.builder()
                    .userUuid(claims.getSubject())
                    .credentials(claims.get("cred", String.class))
                    .typeToken(claims.get("aud", String.class))
                    .roles(claims.get("roles", String.class))
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        }

    }

    public static PreAuthenticatedAuthenticationToken buildPreAuthenticatedAuthenticationToken(String userId, String roles, String credentials) throws AuthenticationException {
        try {
            var myGrantedAuthority = new MyGrantedAuthority(roles);

            final Object myCredentials = credentials;
            final Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) myGrantedAuthority.getAuthority();

            final UserDetails principal = MyUserDetails.builder()
                    .roles(myGrantedAuthority.getRoles())
                    .userId(userId)
                    .randomPassword(RandomStringUtils.randomAlphanumeric(20))
                    .accountEnabled(true).build();

            return new PreAuthenticatedAuthenticationToken(
                    principal,
                    credentials,
                    authorities
            );
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException(ex.getMessage());
        }
    }

}
