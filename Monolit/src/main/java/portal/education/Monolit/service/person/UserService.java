package portal.education.Monolit.service.person;

import portal.education.Monolit.data.model.person.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.UUID;


public interface UserService extends UserDetailsService {

    void save(User user);

    User findByNickname(String nickname)throws UsernameNotFoundException;

    User findByUserId(UUID userId)throws UsernameNotFoundException;

    User findByEmail(String email)throws UsernameNotFoundException;

    void changeUserPassword(final User user, final String password);

    boolean checkIfValidOldPassword(final User user, final String oldPassword);

    void emailConfirmedChangeTrue(String token) throws Exception;

    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    void changeActiveStatus(String nickName, boolean isOnline);

    void purgeAllExpiredTokens(String nickName);

    List<User> getUsersTopByAlphabet(int numberPage, int sizePage, String partOfName);
}

