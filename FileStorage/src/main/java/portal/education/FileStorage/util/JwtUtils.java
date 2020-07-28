package portal.education.FileStorage.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        JwtUtils.secret = secret;
    }

    private static String secret;

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

}
