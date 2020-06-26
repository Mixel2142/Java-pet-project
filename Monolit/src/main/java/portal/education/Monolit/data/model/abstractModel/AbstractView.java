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
public class AbstractView extends AbstractEntity<Long> {

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("views")
    @JsonView({JsonViews.Internal.class, JsonViews.Public.class})
    private User user;

    @JsonView({JsonViews.Internal.class, JsonViews.Public.class})
    private Long timeView;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(JsonViews.Public.class)
    protected Date date;

    public AbstractView(User user, Long timeView) {
        this.user = user;
        this.timeView = timeView;
        this.date = new Timestamp(System.currentTimeMillis());
    }
}
