package portal.education.Monolit.service.person;

import portal.education.Monolit.data.model.notification.AccountConfirmation;
import portal.education.Monolit.data.model.person.User;

public interface AccountConfirmationService {

    void validAccountOrThrowError(String token);

    AccountConfirmation findByUserOrThrow(User user);

    AccountConfirmation findByUserOrCreate(Short attempts, User user, String typeAccount, String accountIdentity);
}
