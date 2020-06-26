package portal.education.Monolit.data.repos.comment;

import portal.education.Monolit.data.model.comment.Comment;
import portal.education.Monolit.data.model.comment.CommentLike;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.data.repos.abstractRepos.AbstractLikeRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CommentLikeRepository extends AbstractLikeRepository<CommentLike> {

    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
}
