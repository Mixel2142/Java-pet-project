package portal.education.Monolit.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.abstractModel.AbstractEntity;
import portal.education.Monolit.data.model.person.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
public class RefreshToken   extends AbstractEntity<Long> {

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("refreshTokens")
    User user;

    @Column(length = 500)
    String token;

    Date expiryDate; // срок жизни

    public RefreshToken(User user, String token, Date expiryDate) {
        this.user = user;
        this.token = token;
        this.expiryDate= expiryDate;
    }
}
