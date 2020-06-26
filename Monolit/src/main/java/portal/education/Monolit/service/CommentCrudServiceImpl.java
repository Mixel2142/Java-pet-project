package portal.education.Monolit.service;

import portal.education.Monolit.data.dto.CommentDto;
import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.comment.Comment;
import portal.education.Monolit.data.model.comment.CommentLike;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.data.repos.comment.CommentLikeRepository;
import portal.education.Monolit.data.repos.comment.CommentRepository;
import portal.education.Monolit.service.article.ArticleCrudService;
import portal.education.Monolit.utils.ExcMsg;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@NoArgsConstructor
@Transactional
public class CommentCrudServiceImpl implements CommentCrudService {

    @Autowired
    CommentRepository commentDao;

    @Autowired
    CommentLikeRepository likeCommentDao;

    @Autowired
    ArticleCrudService articleCrud;

    @Override
    public Long doLike(User user, Long commentId, boolean like) {

        Comment comment = this.getOrElseThrow(commentId);

        CommentLike likeCom = likeCommentDao.findByUserAndComment(user, comment)
                .orElseGet(() -> new CommentLike(user, comment));

        Optional<Boolean> bLike = Optional.of(likeCom).flatMap(commentLike -> Optional.ofNullable(commentLike.isValue()));

        bLike.ifPresentOrElse(
                aBoolean -> {
                    if (aBoolean.booleanValue() != like)
                        comment.setLikesCounter(comment.getLikesCounter() + (like ? 1 : -1));
                },
                () -> {
                    comment.setLikesCounter(comment.getLikesCounter() + (like ? 1 : -1));
                }
        );

        likeCom.setValue(like);

        likeCommentDao.save(likeCom);
        return comment.getLikesCounter();
    }

    @Override
    public void delete(Long commentId) {
        commentDao.deleteById(commentId);
    }

    @Override
    public Comment getOrElseThrow(Long commentId) {
        return commentDao.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                ExcMsg.CommentNotFound(commentId)
                        )
                );
    }

    @Override
    public Comment put(User user, CommentDto dto) {

        Article article = articleCrud.getById(dto.getArticleId());

        return commentDao.save(new Comment(article, user, dto.getText()));
    }

}
