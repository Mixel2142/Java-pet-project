package portal.education.Monolit.service.person;

import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.data.repos.person.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@NoArgsConstructor
@Transactional
public class UserCrudServiceImpl implements UserCrudService {

    @Autowired
    private UserRepository<User> userDao;

    @Override
    public User getByNicknameOrNull(String nickName) {
        return userDao.findByNickname(nickName).orElse(null);
    }
}
