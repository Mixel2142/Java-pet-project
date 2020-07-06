//package portal.education.Monolit.security;
//
//import portal.education.Monolit.data.model.person.User;
//import portal.education.Monolit.service.person.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import portal.education.Monolit.data.repos.RefreshTokenRepository;
//import portal.education.Monolit.data.model.RefreshToken;
//import portal.education.Monolit.utils.JwtUtilForMonolit;
//import portal.education.Monolit.data.dto.JwtPairTokenDto;
//
//import java.util.Map;
//
//@Service
//public class JwtPairTokenService {
//
//    @Autowired
//    private JwtUtilForMonolit jwtUtil;
//
//    @Autowired
//    private RefreshTokenRepository refreshTokenDao;
//
//    @Autowired
//    private UserService userService;
//
//    public String getRedirectOkFromToken(String token) {
//        return jwtUtil.getRedirectOkUrlFromToken(token);
//    }
//
//    public String getRedirectErrorFromToken(String token) {
//        return jwtUtil.getRedirectErrorUrlFromToken(token);
//    }
//
//    public JwtPairTokenDto createFromLogin(String login) {
//        return create(login);
//    }
//
//    public JwtPairTokenDto createFromTokin(String token) {
////        return create(jwtUtil.getNicknameFromToken(token));
//    }
//
//    public JwtPairTokenDto createFromTokenWithRedirectUrl(String token, String redirecturl) {
////        return create(jwtUtil.getNicknameFromToken(token), redirecturl);
//    }
//
//    public JwtPairTokenDto createFromNicknameWithRedirectUrl(String nickname, String redirecturl) {
//        return create(nickname, redirecturl);
//    }
//
//    public JwtPairTokenDto createFromNicknameWithRedirectUrls(String nickname, Map<String, String> urls) {
//        return create(nickname, urls);
//    }
//
//    private JwtPairTokenDto create(String login) {
////        final String accessToken = jwtUtil.generateDataPayloadToken(login, jwtUtil.ACCESSTOKEN_TTL);
////        final String refreshToken = jwtUtil.generateDataPayloadToken(login, jwtUtil.REFRESHTOKEN_TTL);
////        refreshTokenDao.save(new RefreshToken
////                (
////                        userService.findByNickname(login),
////                        refreshToken,
////                        jwtUtil.getExpirationDateFromToken(refreshToken)
////                )
////        );
////        return new JwtPairTokenDto(accessToken, refreshToken);
//    }
//
//
//    private JwtPairTokenDto create(String nickname, String redirectUrl) {
//        final String accessToken = jwtUtil.generateDataPayloadToken(nickname, jwtUtil.ACCESSTOKEN_TTL);
//        final String refreshToken = jwtUtil.generateDataPayloadToken(nickname, redirectUrl, jwtUtil.REFRESHTOKEN_TTL);
//
//        refreshTokenDao.save(new RefreshToken
//                (
//                        userService.findByNickname(nickname),
//                        refreshToken,
//                        jwtUtil.getExpirationDateFromToken(refreshToken)
//                )
//        );
//        return new JwtPairTokenDto(accessToken, refreshToken);
//    }
//
//    private JwtPairTokenDto create(String nickname, Map<String, String> urls) {
//        final String accessToken = jwtUtil.generateDataPayloadToken(nickname, jwtUtil.ACCESSTOKEN_TTL);
//        final String refreshToken = jwtUtil.generateDataPayloadToken(nickname, urls, jwtUtil.REFRESHTOKEN_TTL);
//        refreshTokenDao.save(new RefreshToken
//                (userService.findByNickname(nickname),
//                        refreshToken,
//                        jwtUtil.getExpirationDateFromToken(refreshToken)
//                )
//        );
//        return new JwtPairTokenDto(accessToken, refreshToken);
//    }
//
//    public void validateRefreshToken(String token) throws Exception {
//        try {
//            UserDetails userDetails = userService.loadUserByUsername(jwtUtil.getNicknameFromToken(token));
//
//            if (!jwtUtil.validateToken(token, userDetails))
//                throw new Exception("Refresh Token does't valid!");
//
//            refreshTokenDao.delete(refreshTokenDao.findByToken(token));
//
//        } catch (UsernameNotFoundException e) {
//            throw new Exception("Usertest already doesn't exist!");
//        }
//    }
//
//    public void validateAccessToken(String token) throws Exception {
//        try {
//            UserDetails userDetails = userService.loadUserByUsername(jwtUtil.getNicknameFromToken(token));
//
//            if (!jwtUtil.validateToken(token, userDetails))
//                throw new Exception("Refresh Token does't valid!");
//
//        } catch (UsernameNotFoundException e) {
//            throw new Exception("Usertest already doesn't exist!");
//        }
//    }
//
//    public void deleteRefreshToken(String token, User user) throws Exception {
//        refreshTokenDao.deleteByUserAndToken(user, token);
//    }
//
//}
//
//
