package portal.education.Monolit.data.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.dto.CategoryDto;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.abstractModel.AbstractEntity;
import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.person.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;


@Entity
@Data
@NoArgsConstructor
public class Category  extends AbstractEntity<Long> {

    @Column(unique = true, nullable = false)
    @JsonView({JsonViews.Public.class, JsonViews.Short.class})
    String name;

    @JsonIgnore
    User author;

    @JsonView({JsonViews.Public.class,JsonViews.Internal.class, JsonViews.Short.class})
    private Long rating = 0L;

    public Category(String name, User author) {
        this.name = name;
        this.author = author;
    }

    public Category(CategoryDto dto) {
        this.name = dto.getName();
        this.author = dto.getAuthor();
    }

    @OneToMany(mappedBy = "category")
    @JsonView({JsonViews.Internal.class})
    @JsonIgnoreProperties("category")
    private Collection<Article> articles;
}
