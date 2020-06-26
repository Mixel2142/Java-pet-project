package portal.education.Monolit.service;

import portal.education.Monolit.data.model.Tag;
import portal.education.Monolit.data.repos.article.TagRepository;
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
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@NoArgsConstructor
@Log4j2(topic = "ASYNC__JSON__FILE__APPENDER")
@Transactional
public class TagsCrudServiceImpl implements TagCrudService {
    @Autowired
    private TagRepository tagDao;

    @Override
    public void addRatingOne(Long tagId) {

    }

    @Override
    public Tag getByName(String name) {
        return tagDao.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                ExcMsg.TagNotFound(name)
                        )
                );
    }

    @Override
    public Set<Tag> getByNames(List<String> names) {

        var tags = new HashSet<Tag>();

        for (String name : names) {
            tags.add(tagDao.findByName(name)
                    .orElseThrow(() -> new ResponseStatusException(
                                    HttpStatus.INTERNAL_SERVER_ERROR,
                                    ExcMsg.TagNotFound(name)
                            )
                    )
            );
        }
        return tags;
    }

    @Override
    public List<Tag> getTagsTopByRating(int numberPage, int size) {
        Pageable pagination = PageRequest.of(numberPage, size);
        return tagDao.findAllByOrderByRatingDesc(pagination)
                .orElse(List.of());
    }

    @Override
    public List<Tag> getTagsTopByAlphabet(String partOfName) {
        return tagDao.findByNameStartingWithOrderByRatingDesc(partOfName)
                .orElse(List.of());
    }
}
