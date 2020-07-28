package portal.education.Monolit.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtilForMonolit {


    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        JwtUtilForMonolit.secret = secret;
    }

    private static String secret;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// НОВЫЙ ФУНКЦИОНАЛ

    //retrieve username from jwt token
    public String getUserIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }


    //check if the token has expired
    public static Boolean isOneTypeToken(String token, String typeToken) {
        try {
            return getClaimFromToken(token, Claims::getAudience).equals(typeToken);
        } catch (Exception e) {
            return false;
        }
    }

    public static String getPreTokenFromJwt(String jwt) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(jwt).getBody().get("token", String.class);
    }

    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getAllClaimsFromToken(token));
    }

    //for retrieveing any information from token we will need the secret key
    public static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //retrieve username from jwt token
    public String getRedirectOkUrlFromToken(String token) {

        Claims claims = Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody();

        return claims.get("redirectOk", String.class);
    }

    //retrieve username from jwt token
    public String getRedirectErrorUrlFromToken(String token) {//сообщение из прошлого :parser() идает ошибку если токен истёк(

        Claims claims = Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody();

        return claims.get("redirectError", String.class);
    }

    //retrieve username from jwt token
    public String getClaimsInString(String token) {//сообщение из прошлого :parser() идает ошибку если токен истёк(

        Claims claims = Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody();

        return claims.get("accountIdentification", String.class);
    }

    //retrieve username from jwt token
    public String getTypeAccount(String token) {//сообщение из прошлого :parser() идает ошибку если токен истёк(

        Claims claims = Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody();

        return claims.get("typeAccount", String.class);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    //check if the token has expired
    public Boolean isTokenExpired(String token) {
        try {
            final Date expiration = getExpirationDateFromToken(token);//создаёт исклучение
            return expiration.before(Date.from(Instant.now()));
        } catch (Exception e) {
            return false;
        }
    }

//    //generate token for user
//    public String generateDataPayloadToken(String nickname, final Long time) {
//
//        Map<String, Object> claims = new HashMap<>();
//
////        final UserDetails userDetails = userDetailsService.loadUserByUsername(nickname);
//
//
//        return doGenerateToken(claims, userDetails.getUsername(), time);//userDetails.getUsername() поменять на свою строчку
//    }

    public String generateDataPayloadToken(String nickname, String redirectURL, final Long time) {

        Map<String, Object> claims = new HashMap<>();

        JwtBuilder jwtBuilder = Jwts.builder().setClaims(claims)
                .setSubject(nickname)
                .claim("redirectURL", redirectURL);

        return doGeneratePayloadTimeToken(jwtBuilder, time);
    }

    public String generateDataPayloadToken(String nickname, Map<String, String> urls, final Long time) {

        Map<String, Object> claims = new HashMap<>();

        JwtBuilder jwtBuilder = Jwts.builder().setClaims(claims)
                .setSubject(nickname);

        urls.forEach((key, value) -> {
            jwtBuilder.claim(key, value);
        });

        return doGeneratePayloadTimeToken(jwtBuilder, time);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, final Long time) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date.from(Instant.now()))

                .setExpiration(Date.from(Instant.now().plusMillis(time)))

                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }

    private String doGeneratePayloadTimeToken(JwtBuilder jwtBuilder, final Long time) {

        return jwtBuilder.setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(time)))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

//    //validate token
//    public Boolean validateToken(String token, UserDetails userDetails) {
//
//        final String username = getNicknameFromToken(token);
//
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//
//    }

}
