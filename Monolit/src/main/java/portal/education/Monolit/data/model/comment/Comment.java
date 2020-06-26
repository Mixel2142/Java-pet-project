package portal.education.Monolit.data.model.comment;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.abstractModel.AbstractEntity;
import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.person.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment extends AbstractEntity<Long> {

    @JsonView(JsonViews.Public.class)
    private String text;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = {"articles", "emailConfirmed", "online", "lockoutEnd", "authorities", "rating", "accountNonLocked", "comments"})
    @JsonView(JsonViews.Public.class)
    private User user;


    @ManyToOne()
    @JoinColumn(name = "article_id")
    @JsonIgnoreProperties("comments")
    @JsonView(JsonViews.Internal.class)
    private Article article;

    @Column(name = "article_id", insertable = false, updatable = false)
    private UUID articleId;

    @Column(nullable = false)
    @JsonView({JsonViews.Public.class})
    private Long likesCounter = 0L;

    @JsonIgnore
    @OneToMany(mappedBy = "comment")
    private Collection<CommentLike> likes;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(JsonViews.Public.class)
    @CreationTimestamp
    private Date createdOn;

    public Comment(Article article, User user, String text) {
        this.setArticle(article);
        this.setUser(user);
        this.setText(text);
    }

}
