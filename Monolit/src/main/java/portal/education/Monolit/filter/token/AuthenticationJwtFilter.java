package portal.education.Monolit.filter.token;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import portal.education.Monolit.service.JsonObjectConverter;
import portal.education.Monolit.utils.JwtUtilForMonolit;
import portal.education.serialize.MyGrantedAuthority;
import portal.education.serialize.MyUserDetails;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@Component
@Log4j2(topic = "ASYNC__JSON__FILE__APPENDER")
public class AuthenticationJwtFilter extends OncePerRequestFilter {

    @Autowired
    private JsonObjectConverter jsonObjectConverter;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws AuthenticationServiceException {


        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);

            AuthData authData = initVariablesForAuthentication(jwt);

            if (authData.getTypeToken().equals("access"))
                if (SecurityContextHolder.getContext().getAuthentication() == null) {

                    SecurityContextHolder.getContext()
                            .setAuthentication(
                                    (Authentication) buildPreAuthenticatedAuthenticationToken(
                                            authData.getUserUuid(),
                                            authData.getRoles(),
                                            authData.getCredentials()
                                    )
                            );
                }
        }
        chain.doFilter(request, response);
    }

    private AuthData initVariablesForAuthentication(String jwt) {
        try {
            Claims claims = JwtUtilForMonolit.getAllClaimsFromToken(jwt);
            return AuthData.builder()
                    .userUuid(claims.getSubject())
                    .credentials(claims.get("cred", String.class))
                    .typeToken(claims.get("aud", String.class))
                    .roles(claims.get("roles", String.class))
                    .build();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
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

    @Data
    @Builder
    static class AuthData {
        private String typeToken, roles, credentials, userUuid;
    }

//    public static Object convertFromByteString(String byteString) throws IOException, ClassNotFoundException {
//        final byte[] bytes = decompress(Base64.getDecoder().decode(byteString));
//
//        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes); ObjectInput in = new ObjectInputStream(bis)) {
//            return in.readObject();
//        }
//    }
//
//    private static Inflater inflater = new Inflater();
//
//    public static byte[] decompress(byte[] in) {
//        try {
//            int size = (int) (in.length * 1.3);
//
//            ByteArrayOutputStream out = new ByteArrayOutputStream(size + 1);
//            InflaterOutputStream infl = new InflaterOutputStream(out, inflater, size);
//            infl.write(in);
//            infl.flush();
//            infl.close();
//
//            return out.toByteArray();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.exit(150);
//            return null;
//        }
//    }

}