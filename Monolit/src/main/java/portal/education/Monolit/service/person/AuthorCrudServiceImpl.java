package portal.education.Monolit.service.person;

import portal.education.Monolit.data.dto.ItemsDto;
import portal.education.Monolit.data.model.person.Author;
import portal.education.Monolit.data.repos.person.AuthorRepository;
import portal.education.Monolit.utils.ExcMsg;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor
@Log4j2(topic = "ASYNC__JSON__FILE__APPENDER")
@Transactional
public class AuthorCrudServiceImpl implements AuthorCrudService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AuthorRepository authorDao;


    @Override
    public ItemsDto<Author> getTopByAlphabet(int numberPage, int sizePage, String partOfName) {
        Pageable pagination = PageRequest.of(numberPage, sizePage);
        var dto = new ItemsDto<Author>();

        dto.setItems(authorDao.findByNicknameStartingWithOrderByRatingDesc(partOfName, pagination)
                .orElse(List.of()));

        dto.setTotal(authorDao.count());

        return dto;
    }

    @Override
    public ItemsDto<Author> getTopByRating(int numberPage, int sizePage) {
        Pageable pagination = PageRequest.of(numberPage, sizePage);
        var dto = new ItemsDto<Author>();

        dto.setItems(authorDao.findAllByOrderByRatingDesc(pagination)
                .orElse(List.of()));
        dto.setTotal(authorDao.count());

        return dto;
    }

    @Override
    public ItemsDto<Author> getAll(int numberPage, int sizePage) {
        Pageable pagination = PageRequest.of(numberPage, sizePage);
        var dto = new ItemsDto<Author>();

        dto.setItems( authorDao.findAll(pagination).getContent());
        dto.setTotal(authorDao.count());

        return dto;
    }

    @Override
    public Author getById(UUID authorId) {
        return authorDao.findById(authorId).orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        ExcMsg.AuthorNotFound(authorId)
                )
        );
    }
}
