//package portal.education.Monolit.service.person;
//
//import portal.education.Monolit.data.model.notification.AccountConfirmation;
//import portal.education.Monolit.data.model.person.User;
//import portal.education.Monolit.data.repos.notification.AccountConfirmationRepository;
//import portal.education.Monolit.service.notification.controller.ConfirmNotification;
//import portal.education.Monolit.utils.JwtUtilForMonolit;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//import javax.transaction.Transactional;
//import java.sql.Date;
//import java.time.Instant;
//import java.util.Optional;
//
//@Service
//@Transactional
//public class AccountConfirmationServiceImpl implements AccountConfirmationService {
//
//    @Autowired
//    private AccountConfirmationRepository accountConfirmDao;
//
//    @Autowired
//    JwtUtilForMonolit tokenUtil;
//
//    @Autowired
//    UserCrudService userCrud;
//
//    @Override
//    public void validAccountOrThrowError(String token) {
//
////        String nick = tokenUtil.getNicknameFromToken(token);
//
//        User user = userCrud.getByNicknameOrNull(nick);
//
//        var confirmation = findByUserOrThrow(user);
//
//        if(confirmation.isConfirm())
//            throw new ResponseStatusException(HttpStatus.OK,"Аккаунт уже подтверждён");
//
////        confirmation.setAccountIdentification(tokenUtil.getClaimsToString(token));
//        confirmation.setTypeAccount(tokenUtil.getTypeAccount(token));
//
//        confirmation.setNextAttemptTime(Date.from(Instant.now().plusMillis(ConfirmNotification.TIME_INTERVAL_BETWEEN_GROUP_ATTEMPTS)));
//
//        accountConfirmDao.setConfirm(user, true);
//    }
//
//    @Override
//    public AccountConfirmation findByUserOrThrow(User user) {
//        return Optional.ofNullable(accountConfirmDao.findByUser(user))
//                .orElseThrow(() -> {
//                    throw new ResponseStatusException(
//                            HttpStatus.INTERNAL_SERVER_ERROR,
//                            "Аккаунт не был подтверждён"
//                    );
//                });
//    }
//
//    @Override
//    public AccountConfirmation findByUserOrCreate(Short attempts, User user, String typeAccount, String accountIdentity) {
//
//         //С его помощью мы можем легко сделать вывод, что параметр orElse () вычисляется даже при наличии непустого Optional (в этом случае верно и обратное)
//        return Optional.ofNullable(accountConfirmDao.findByUser(user))
//                .orElseGet(
//                        () -> createAccountConfirmation(attempts, user, typeAccount, accountIdentity)
//                );
//    }
//
//    private AccountConfirmation createAccountConfirmation(Short attempts, User user, String typeAccount, String accountIdentity) {
//        return accountConfirmDao.save(new AccountConfirmation(attempts, user, typeAccount, accountIdentity));
//    }
//
//}
