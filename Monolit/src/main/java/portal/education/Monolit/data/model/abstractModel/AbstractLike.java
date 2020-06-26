package portal.education.Monolit.data.model.abstractModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.person.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class AbstractLike extends AbstractEntity<Long>{

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = {"articles", "emailConfirmed", "online", "lockoutEnd", "authorities", "rating", "accountNonLocked", "comments"})
    @JsonView(JsonViews.Public.class)
    protected User user;

    protected boolean value;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(JsonViews.Public.class)
    public Date date;

    public AbstractLike(User user) {
        this.user = user;
        this.date = new Timestamp(System.currentTimeMillis());
    }
}
