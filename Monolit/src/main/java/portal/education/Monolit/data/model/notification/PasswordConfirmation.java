package portal.education.Monolit.data.model.notification;

import portal.education.Monolit.data.model.abstractModel.AbstractConfirmation;
import portal.education.Monolit.data.model.person.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class PasswordConfirmation extends AbstractConfirmation {

    public PasswordConfirmation(Short attempts, User user) {
        super(attempts,user);
    }

}
