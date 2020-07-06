package portal.education.AuthService.tokenFacilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import portal.education.serialize.MyGrantedAuthority;
import portal.education.serialize.MyUserDetails;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

@Component
public class JwtUtilForServer {

    final static Logger logger = LoggerFactory.getLogger(JwtUtilForServer.class);

    private static Deflater deflate = new Deflater(Deflater.BEST_SPEED);

    private static String secret;

    public static Long ACCESSTOKEN_TTL;

    public static Long REFRESHTOKEN_TTL;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        JwtUtilForServer.secret = secret;
    }

    @Value("${jwt.time.validity.accesstoken}")
    public void setAccesstoken(Long accesstoken) {
        this.ACCESSTOKEN_TTL = accesstoken * 1000;
    }

    @Value("${jwt.time.validity.refreshtoken}")
    public void setRefreshtoken(Long refreshtoken) {
        this.REFRESHTOKEN_TTL = refreshtoken * 1000;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //retrieve username from jwt token
    public static String getUserIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

    }

    //check if the token has expired
    public static Boolean isTokenExpired(String token) {
        try {
            final Date expiration = getExpirationDateFromToken(token);//создаёт исклучение
            return expiration.after(Date.from(Instant.now()));
        } catch (Exception e) {
            return false;
        }
    }

    //check if the token has expired
    public static Boolean isOneTypeToken(String token, String typeToken) {
        try {
            return getClaimFromToken(token, Claims::getAudience).equals(typeToken);
        } catch (Exception e) {
            return false;
        }
    }

    public static String generateDataPayloadToken(String uuid, String typeToken, Map<String, String> urls, final Long time) {

        Map<String, Object> claims = new HashMap<>();

        JwtBuilder jwtBuilder = Jwts.builder().setClaims(claims)
                .setSubject(uuid).setAudience(typeToken);

        urls.forEach((key, value) -> {
            jwtBuilder.claim(key, value);
        });

        return doGeneratePayloadTimeToken(jwtBuilder, time);
    }

    private static String doGeneratePayloadTimeToken(JwtBuilder jwtBuilder, final Long time) {

        return jwtBuilder.setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(time)))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public static PreAuthenticatedAuthenticationToken buildPreAuthenticatedAuthenticationToken(String userId, String roles, String credentials) throws AuthenticationException {

        try {
            var myGrantedAuthority = new MyGrantedAuthority(roles);

            final Object myCredentials = null;
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
        } catch (Exception ex) {
            throw new BadCredentialsException(ex.getMessage());
        }
    }

    private <T extends Enum<T>> Set<T> arrayStrToSet(Class<T> clazz, String[] values) {
        Set<T> set = new HashSet<>();
        for (String level : values) {
            set.add(Enum.valueOf(clazz, level));
        }
        return set;
    }

    public static String convertToByteString(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return Base64.getEncoder().encodeToString(compress(bos.toByteArray()));
        }
    }

    public static byte[] compress(byte[] in) {
        try {
            int size = (int) (in.length * 0.8f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(size + 1);
            DeflaterOutputStream defl = new DeflaterOutputStream(out, deflate, size);

            defl.write(in);
            defl.flush();
            defl.close();
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
