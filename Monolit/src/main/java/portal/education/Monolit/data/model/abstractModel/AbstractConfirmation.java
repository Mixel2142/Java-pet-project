package portal.education.Monolit.data.model.abstractModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.person.User;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;


//@Entity
@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractConfirmation extends AbstractNotification {

    @OneToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = {"articles", "emailConfirmed", "online", "lockoutEnd", "authorities", "rating", "accountNonLocked", "comments"})
    @JsonIgnore
    private User user;

    @JsonView(JsonViews.Internal.class)
    private Short attempts;// Сколько осталось попыток

    @JsonView(JsonViews.Internal.class)
    private Date nextAttemptTime;

    @JsonView(JsonViews.Internal.class)
    boolean confirm = false;

    public AbstractConfirmation(Short attempts, User user) {
        this.user = user;
        this.attempts = attempts;
        this.nextAttemptTime = Date.from(Instant.now());
    }
}
