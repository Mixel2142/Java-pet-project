package portal.education.Monolit.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.dto.AbstractTagDto;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.abstractModel.AbstractEntity;
import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.person.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Tag extends AbstractEntity<Long> {

    @Column(unique = true, nullable = false)
    @JsonView({JsonViews.Public.class, JsonViews.Short.class})
    String name;

    @JsonIgnore
    User author;

    @JsonView({JsonViews.Public.class, JsonViews.Internal.class, JsonViews.Short.class})
    private Long rating = 0L;

    public Tag(String name, User author) {
        this.name = name;
        this.author = author;
    }

    public Tag(AbstractTagDto dto) {
        this.name = dto.getName();
        this.author = dto.getAuthor();
    }


    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<Article> articles;
}
