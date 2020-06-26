package portal.education.Monolit.filter.token;

import portal.education.Monolit.utils.ExcMsg;
import portal.education.Monolit.utils.JwtTokenUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Log4j2(topic = "ASYNC__JSON__FILE__APPENDER")
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws AuthenticationServiceException, ServletException, IOException {


        log.debug("Пришёл запрос с URI:" + request.getRequestURI() + "\n" +
                "HeaderNames :" + request.getHeaderNames() + "\n" +
                "Authorization :" + request.getHeader("Authorization")
        );

        String requesrURIstr = request.getRequestURI();


        // TODO delete on prod
        if (true) {
            chain.doFilter(request, response);
            return;
        }// END

        if (!requesrURIstr.contains("/free/")) {

            String header = request.getHeader("Authorization");

            if (header == null || !header.startsWith("Bearer ")) {
                log.debug("В хедере не нашли Authorization или Authorization не начинается со строки Bearer");
                throw new AuthenticationServiceException("No JWT token found in request headers");
            }

            String jwtToken = header.substring(7);

            request.setAttribute("token", jwtToken);

            String username = null;

            if (jwtToken == null) {
                log.debug("Не смог получить jwtToken");
                throw new AuthenticationServiceException("token does not exist");
            }

            try {

                username = jwtTokenUtil.getNicknameFromToken(jwtToken);

                if (username == null) {
                    log.debug("username == null т.е. в токене не храниться имя пользователя");
                    throw new AuthenticationServiceException("Invalid token");
                }

                // (SecurityContextHolder.getContext().getAuthentication() == null) Once we get the token validate it.
                if (SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                    if (!jwtTokenUtil.validateToken(jwtToken, userDetails))
                        throw new AuthenticationServiceException("Invalid token");


                    doLogin(userDetails, request);
                }

            } catch (Exception e) {

                if (requesrURIstr.contains("/token/refresh"))
                    throw new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            ExcMsg.AccessTokenNotValid("Refresh: " + e.getMessage())
                    );
                response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            }
        }
        chain.doFilter(request, response);
    }


    private void doLogin(UserDetails userDetails, HttpServletRequest request) {

        UsernamePasswordAuthenticationToken userPasswordAuthToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());

        userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().
                buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(userPasswordAuthToken);
    }

}
