package portal.education.Monolit.service;

import portal.education.Monolit.data.dto.ItemsDto;
import portal.education.Monolit.data.model.Category;
import portal.education.Monolit.data.repos.CategoryRepository;
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

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;


@Service
@NoArgsConstructor
@Log4j2(topic = "ASYNC__JSON__FILE__APPENDER")
@Transactional
public class CategoryCrudServiceImpl implements CategoryCrudService {

    @Autowired
    private CategoryRepository categoryDao;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void addRatingOne(Long categoryId) {
        Category ctg = this.getById(categoryId);
        ctg.setRating(ctg.getRating() + 1);
        entityManager.flush();
    }

    @Override
    public Category getByName(String name) {
        return categoryDao.findByName(name)
                .orElseThrow(() -> new RuntimeException(
                                ExcMsg.CategoryNotFound(name)
                        )
                );
    }

    @Override
    public Category getById(Long categoryId) throws RuntimeException {
        return categoryDao.findById(categoryId)
                .orElseThrow(() -> new RuntimeException(
                                ExcMsg.CategoryNotFound(categoryId)
                        )
                );
    }

    @Override
    public ItemsDto<Category> getTopByRating(int page, int size) {
        Pageable pagination = PageRequest.of(page, size);

        var dto = new ItemsDto<Category>();

        dto.setItems(this.getTopByRatingInternal(pagination));

        dto.setTotal(categoryDao.count());

        return dto;
    }

    private List<Category> getTopByRatingInternal(Pageable pagination) {
        return categoryDao.findAllByOrderByRatingDesc(pagination).orElseThrow(() -> {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Page error"
            );
        });
    }


    @Override
    public ItemsDto<Category> getTopByAlphabet(String partOfCategory) {

        var dto = new ItemsDto<Category>();

        dto.setTotal(categoryDao.countByNameStartingWith(partOfCategory));

        dto.setItems(this.getTopByAlphabetInternal(partOfCategory));

        return dto;

    }

    private List<Category> getTopByAlphabetInternal(String partOfName) {
        return categoryDao.findByNameStartingWithOrderByRatingDesc(partOfName)
                .orElseThrow(() -> {
                            throw new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "Page error"
                            );
                        }
                );
    }

    @Override
    public ItemsDto<Category> getAll(int page, int size) {
        Pageable pagination = PageRequest.of(page, size);
        var dto = new ItemsDto<Category>();

        dto.setTotal(categoryDao.count());
        dto.setItems(categoryDao.findAll(pagination).toList());

        return dto;
    }

    @Override
    public void delete(Long categoryId) {
        entityManager.remove(getById(categoryId));
    }


}
