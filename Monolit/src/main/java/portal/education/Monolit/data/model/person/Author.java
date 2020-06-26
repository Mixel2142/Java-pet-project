package portal.education.Monolit.data.model.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.dto.AuthorDto;
import portal.education.Monolit.data.dto.UserDto;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.article.Article;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;


@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Author extends User {

    @JsonView({JsonViews.Internal.class, JsonViews.Short.class})
    private long rating;

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties(value = {"author","body","comments","rating","views","confirm"})
    @JsonIgnore
    private Collection<Article> articles;

    public Author(UserDto data) {
        super(data);
        this.rating = 0L;
    }

    public void patch(AuthorDto dto) {

        if (dto.getRating() != null)
            this.rating = dto.getRating();
    }

    public Author(User data) {
        super(data);
        this.rating = 0L;
        this.articles = null;
    }
}
