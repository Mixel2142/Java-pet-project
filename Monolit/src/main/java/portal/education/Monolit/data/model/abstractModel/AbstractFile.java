package portal.education.Monolit.data.model.abstractModel;

import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.jsonview.JsonViews;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;


@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class AbstractFile {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView({JsonViews.Public.class, JsonViews.Short.class, JsonViews.Internal.class, JsonViews.Author.class})
    public UUID id;

    @JsonView({JsonViews.Public.class, JsonViews.Short.class, JsonViews.Internal.class})
    String сontentType;

    @JsonView({JsonViews.Public.class, JsonViews.Short.class, JsonViews.Internal.class})
    Long size;

    @JsonView({JsonViews.Public.class, JsonViews.Short.class, JsonViews.Internal.class})
    @Lob
    private Blob data;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @JsonView(JsonViews.Public.class)
    protected Date createdOn;

    public AbstractFile(String сontentType, Long size, Blob data) {
        this.сontentType = сontentType;
        this.size = size;
        this.data = data;
    }
}
