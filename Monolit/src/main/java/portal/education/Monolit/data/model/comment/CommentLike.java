package portal.education.Monolit.data.model.comment;

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
@Table(name = "comments_likes")
@Getter
@Setter
@NoArgsConstructor
public class CommentLike extends AbstractLike {

    public CommentLike(User user, Comment comment) {
        super(user);
        this.comment = comment;
    }

    @ManyToOne()
    @JoinColumn(name = "comment_id")
    @JsonIgnoreProperties("likes")
    @JsonView(JsonViews.Internal.class)
    private Comment comment;
}