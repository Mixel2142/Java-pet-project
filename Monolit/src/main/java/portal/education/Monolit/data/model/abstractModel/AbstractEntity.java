package portal.education.Monolit.data.model.abstractModel;

import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.jsonview.JsonViews;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractEntity<ID extends Number> implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView({JsonViews.Public.class, JsonViews.Short.class, JsonViews.Internal.class,JsonViews.Author.class})
    public ID id;

}
