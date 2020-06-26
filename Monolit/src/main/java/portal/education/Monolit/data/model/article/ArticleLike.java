package portal.education.Monolit.data.model.article;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.abstractModel.AbstractLike;
import portal.education.Monolit.data.model.person.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "articles_likes")
@Getter
@Setter
@NoArgsConstructor
public class ArticleLike extends AbstractLike {

    public ArticleLike(User user, Article article) {
        super(user);
        this.article = article;
    }

    @ManyToOne()
    @JoinColumn(name = "article_id")
    @JsonIgnoreProperties("likes")
    @JsonView(JsonViews.Internal.class)
    private Article article;
}
