package portal.education.Monolit.filter.token;

import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import portal.education.Monolit.service.JsonObjectConverter;
import portal.education.Monolit.utils.JwtUtilForMonolit;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

@Component
@Log4j2(topic = "ASYNC__JSON__FILE__APPENDER")
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JsonObjectConverter jsonObjectConverter;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws AuthenticationServiceException, ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);

            if (JwtUtilForMonolit.isOneTypeToken(jwt, "access"))
                if (SecurityContextHolder.getContext().getAuthentication() == null) {

                    SecurityContextHolder.getContext()
                            .setAuthentication(
                                    (Authentication) convertFromByteString(
                                            JwtUtilForMonolit.getPreTokenFromJwt(jwt)
                                    )
                            );
                }
        }
        chain.doFilter(request, response);
    }

    public static Object convertFromByteString(String byteString) throws IOException, ClassNotFoundException {
        final byte[] bytes = decompress(Base64.getDecoder().decode(byteString));

        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes); ObjectInput in = new ObjectInputStream(bis)) {
            return in.readObject();
        }
    }

    private static Inflater inflater = new Inflater();

    public static byte[] decompress(byte[] in) {
        try {
            int size = (int) (in.length * 1.3);

            ByteArrayOutputStream out = new ByteArrayOutputStream(size + 1);
            InflaterOutputStream infl = new InflaterOutputStream(out, inflater, size);
            infl.write(in);
            infl.flush();
            infl.close();

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(150);
            return null;
        }
    }
}
