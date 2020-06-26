package portal.education.Monolit.security;

import org.springframework.security.core.AuthenticationException;

public interface SecurityService {

    String findLoggedInNickname();

    void autoLogin(String nickname, String password) throws AuthenticationException;

}
