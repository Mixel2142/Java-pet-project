package portal.education.Monolit.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;

@Component
@Scope(value = "singleton")
public class JwtTokenUtil {


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Value("${jwt.secret}")
    private String secret;

    public static Long ACCESSTOKEN_TTL;

    public static Long REFRESHTOKEN_TTL;

    @Value("${jwt.time.validity.accesstoken}")
    public void setAccesstoken(Long accesstoken) {
        this.ACCESSTOKEN_TTL = accesstoken * 1000;
    }

    @Value("${jwt.time.validity.refreshtoken}")
    public void setRefreshtoken(Long refreshtoken) {
        this.REFRESHTOKEN_TTL = refreshtoken * 1000;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Autowired
    private UserDetailsService userDetailsService;


    //retrieve username from jwt token
    public String getNicknameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

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
    public String getAccountIdentification(String token) {//сообщение из прошлого :parser() идает ошибку если токен истёк(

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


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = getAllClaimsFromToken(token);

        return claimsResolver.apply(claims);

    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

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

    //generate token for user
    public String generateDataPayloadToken(String nickname, final Long time) {

        Map<String, Object> claims = new HashMap<>();

        final UserDetails userDetails = userDetailsService.loadUserByUsername(nickname);


        return doGenerateToken(claims, userDetails.getUsername(), time);//userDetails.getUsername() поменять на свою строчку
    }

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

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {

        final String username = getNicknameFromToken(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

}
