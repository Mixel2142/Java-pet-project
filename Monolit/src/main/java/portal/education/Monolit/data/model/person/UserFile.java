package portal.education.Monolit.data.model.person;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.abstractModel.AbstractFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.sql.Blob;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserFile extends AbstractFile {

    @OneToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("avatar")
    @JsonView(JsonViews.Public.class)
    private User user;

    public UserFile(String сontentType, Long size, Blob data, User user) {
        super(сontentType, size, data);
        this.user = user;
    }
}
