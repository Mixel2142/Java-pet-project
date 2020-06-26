package portal.education.Monolit.service.person;

import portal.education.Monolit.data.model.person.User;

public interface UserCrudService {

    User getByNicknameOrNull(String nickName);
}
