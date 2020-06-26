package portal.education.Monolit.service.person;

import portal.education.Monolit.data.dto.ItemsDto;
import portal.education.Monolit.data.model.person.Author;

import java.util.UUID;

public interface AuthorCrudService {

    ItemsDto<Author> getTopByAlphabet(int numberPage, int sizePage, String partOfName);

    ItemsDto<Author> getTopByRating(int numberPage, int sizePage);

    ItemsDto<Author> getAll(int numberPage, int sizePage);

     Author getById(UUID authorId);
}
