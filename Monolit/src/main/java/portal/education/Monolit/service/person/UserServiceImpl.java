package portal.education.Monolit.service.person;

import portal.education.Monolit.data.repos.RefreshTokenRepository;
import portal.education.Monolit.data.repos.person.RoleRepository;
import portal.education.Monolit.data.repos.person.UserRepository;
import portal.education.Monolit.data.model.person.Role;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.utils.ExcMsg;
import portal.education.Monolit.utils.JwtTokenUtil;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;

@Service
@NoArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository<User> userDao;

    @Autowired
    private RoleRepository roleDao;

    @Autowired
    private RefreshTokenRepository refreshTokenDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtTokenUtil tokenUtil;

    public void emailConfirmedChangeTrue(String token) throws RuntimeException {
        String nickname = tokenUtil.getNicknameFromToken(token);

        User user = this.findByNickname(nickname);

        user.setAccountIdentification(tokenUtil.getAccountIdentification(token));
        if (user.isAccountConfirmed())
            throw new RuntimeException("Email уже подтверждён!");

        user.setAccountConfirmed(true);
    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword());
        user.setRoles(Set.of(roleDao.findByName("ROLE_USER")));

        userDao.save(user);
    }

    @Override
    public User findByNickname(String nickname) throws UsernameNotFoundException {
        return userDao.findByNickname(nickname).orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        ExcMsg.UserNotFound(nickname)
                )
        );
    }

    @Override
    public User findByUserId(UUID userId) throws UsernameNotFoundException {
        return userDao.findById(userId).orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        ExcMsg.UserNotFound(userId.toString())
                )
        );
    }


    @Override
    public User findByEmail(String email) throws UsernameNotFoundException {
        return userDao.findByAccountIdentification(email).orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        ExcMsg.UserNotFound(email)
                )
        );
    }

    @Override
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        userDao.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        return this.findByNickname(nickname);
    }

    @Override
    public void changeActiveStatus(String nickName, boolean isOnline) {
        try {
            userDao.setActiveByNickName(nickName, isOnline);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Status not change(");
        }
    }

    @Override
    public void purgeAllExpiredTokens(String nickName) {
        try {
            Date now = Date.from(Instant.now());
            refreshTokenDao.deleteByNickNameAllExpiredSince(findByNickname(nickName), now);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Delete tokens exception");
        }
    }

    @Override
    public List<User> getUsersTopByAlphabet(int page, int size, String partOfName) {
        Pageable pagination = PageRequest.of(page, size);
        return userDao.findByNicknameStartingWith(partOfName, pagination)
                .orElseThrow(() -> {
                            throw new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "Page error"
                            );
                        }
                );
    }

    private static List<GrantedAuthority> getAuthorities(Collection<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
}
