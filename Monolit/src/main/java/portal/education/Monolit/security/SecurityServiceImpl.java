package portal.education.Monolit.security;

import portal.education.Monolit.service.person.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Log4j2(topic = "ASYNC__JSON__FILE__APPENDER")
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Override
    public String findLoggedInNickname() {

        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();

        if (userDetails instanceof UserDetails) {
            return ((UserDetails) userDetails).getUsername();
        }

        return null;
    }

    @Override
    public void autoLogin(String login, String password) throws AuthenticationServiceException {

        UserDetails userDetails;

        try {
            userDetails = userDetailsService.loadUserByUsername(login);
        } catch (Exception e) {
            throw new AuthenticationServiceException("Usertest does not exist");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                password,
                userDetails.getAuthorities());

        authenticationManager.authenticate(authenticationToken);

        if (!authenticationToken.isAuthenticated())
            throw new AuthenticationServiceException("Usertest does not authenticate");

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info(String.format("Successfully %s auto logged in", login));
    }

}


