package portal.education.Monolit.service;

import portal.education.Monolit.data.dto.CommentDto;
import portal.education.Monolit.data.dto.ItemsDto;
import portal.education.Monolit.data.model.comment.Comment;
import portal.education.Monolit.data.model.person.User;

import java.util.List;
import java.util.UUID;


public interface CommentCrudService {

    Long doLike(User user, Long commentId, boolean like);

    void delete(Long commentId);

    Comment getOrElseThrow(Long commentId);

    Comment put(User user, CommentDto dto);


}
