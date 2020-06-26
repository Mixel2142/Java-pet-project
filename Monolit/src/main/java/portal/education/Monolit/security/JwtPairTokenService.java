package portal.education.Monolit.security;

import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.service.person.UserService;
import portal.education.Monolit.service.person.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import portal.education.Monolit.data.repos.RefreshTokenRepository;
import portal.education.Monolit.data.model.RefreshToken;
import portal.education.Monolit.utils.JwtTokenUtil;
import portal.education.Monolit.data.dto.JwtPairTokenDto;

import java.util.Map;

@Service
public class JwtPairTokenService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RefreshTokenRepository refreshTokenDao;

    @Autowired
    private UserService userService;

    public String getRedirectOkFromToken(String token) {
        return jwtTokenUtil.getRedirectOkUrlFromToken(token);
    }

    public String getRedirectErrorFromToken(String token) {
        return jwtTokenUtil.getRedirectErrorUrlFromToken(token);
    }

    public JwtPairTokenDto createFromLogin(String login) {
        return create(login);
    }

    public JwtPairTokenDto createFromTokin(String token) {
        return create(jwtTokenUtil.getNicknameFromToken(token));
    }

    public JwtPairTokenDto createFromTokenWithRedirectUrl(String token, String redirecturl) {
        return create(jwtTokenUtil.getNicknameFromToken(token), redirecturl);
    }

    public JwtPairTokenDto createFromNicknameWithRedirectUrl(String nickname, String redirecturl) {
        return create(nickname, redirecturl);
    }

    public JwtPairTokenDto createFromNicknameWithRedirectUrls(String nickname, Map<String, String> urls) {
        return create(nickname, urls);
    }

    private JwtPairTokenDto create(String login) {
        final String accessToken = jwtTokenUtil.generateDataPayloadToken(login, jwtTokenUtil.ACCESSTOKEN_TTL);
        final String refreshToken = jwtTokenUtil.generateDataPayloadToken(login, jwtTokenUtil.REFRESHTOKEN_TTL);
        refreshTokenDao.save(new RefreshToken
                (
                        userService.findByNickname(login),
                        refreshToken,
                        jwtTokenUtil.getExpirationDateFromToken(refreshToken)
                )
        );
        return new JwtPairTokenDto(accessToken, refreshToken);
    }


    private JwtPairTokenDto create(String nickname, String redirectUrl) {
        final String accessToken = jwtTokenUtil.generateDataPayloadToken(nickname, jwtTokenUtil.ACCESSTOKEN_TTL);
        final String refreshToken = jwtTokenUtil.generateDataPayloadToken(nickname, redirectUrl, jwtTokenUtil.REFRESHTOKEN_TTL);

        refreshTokenDao.save(new RefreshToken
                (
                        userService.findByNickname(nickname),
                        refreshToken,
                        jwtTokenUtil.getExpirationDateFromToken(refreshToken)
                )
        );
        return new JwtPairTokenDto(accessToken, refreshToken);
    }

    private JwtPairTokenDto create(String nickname, Map<String, String> urls) {
        final String accessToken = jwtTokenUtil.generateDataPayloadToken(nickname, jwtTokenUtil.ACCESSTOKEN_TTL);
        final String refreshToken = jwtTokenUtil.generateDataPayloadToken(nickname, urls, jwtTokenUtil.REFRESHTOKEN_TTL);
        refreshTokenDao.save(new RefreshToken
                (userService.findByNickname(nickname),
                        refreshToken,
                        jwtTokenUtil.getExpirationDateFromToken(refreshToken)
                )
        );
        return new JwtPairTokenDto(accessToken, refreshToken);
    }

    public void validateRefreshToken(String token) throws Exception {
        try {
            UserDetails userDetails = userService.loadUserByUsername(jwtTokenUtil.getNicknameFromToken(token));

            if (!jwtTokenUtil.validateToken(token, userDetails))
                throw new Exception("Refresh Token does't valid!");

            refreshTokenDao.delete(refreshTokenDao.findByToken(token));

        } catch (UsernameNotFoundException e) {
            throw new Exception("Usertest already doesn't exist!");
        }
    }

    public void validateAccessToken(String token) throws Exception {
        try {
            UserDetails userDetails = userService.loadUserByUsername(jwtTokenUtil.getNicknameFromToken(token));

            if (!jwtTokenUtil.validateToken(token, userDetails))
                throw new Exception("Refresh Token does't valid!");

        } catch (UsernameNotFoundException e) {
            throw new Exception("Usertest already doesn't exist!");
        }
    }

    public void deleteRefreshToken(String token, User user) throws Exception {
        refreshTokenDao.deleteByUserAndToken(user, token);
    }

}


