package portal.education.Monolit.data.model.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.abstractModel.AbstractConfirmation;
import portal.education.Monolit.data.model.person.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class AccountConfirmation extends AbstractConfirmation {

    @JsonIgnore
    private String accountIdentification;

    @JsonIgnore
    private String typeAccount;

    public AccountConfirmation(Short attempts, User user,String typeAccount,String accountIdentity) {
        super(attempts, user);
        this.accountIdentification = accountIdentity;
        this.typeAccount = typeAccount;
    }
}
