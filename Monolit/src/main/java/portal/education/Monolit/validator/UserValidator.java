package portal.education.Monolit.validator;

import portal.education.Monolit.data.repos.article.ArticleViewRepository;
import portal.education.Monolit.data.repos.person.UserRepository;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.utils.ExcMsg;
import portal.education.Monolit.service.person.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserValidator implements Validator {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository<User> userDao;

    @Autowired
    ArticleViewRepository articleViewRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nickname", HttpStatus.I_AM_A_TEAPOT.toString(), "Required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", HttpStatus.I_AM_A_TEAPOT.toString(), "Required.");

        userDao.findByNickname(user.getNickname())
                .ifPresent(obj -> {
                            throw new ResponseStatusException(
                                    HttpStatus.INTERNAL_SERVER_ERROR,
                                    ExcMsg.UserAlreadyExist(obj.getNickname())
                            );
                        }
                );
    }
}
