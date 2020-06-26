package portal.education.Monolit.data.model.article;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.abstractModel.AbstractView;
import portal.education.Monolit.data.model.person.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "articles_views")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleView extends AbstractView {

    @ManyToOne()
    @JoinColumn(name = "article_id")
    @JsonIgnoreProperties("views")
    @JsonView({JsonViews.Internal.class, JsonViews.Public.class})
    private Article article;

    public ArticleView(Article article, User user, Long timeView) {
        super(user, timeView);
        this.article = article;
    }
}
