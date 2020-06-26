package portal.education.Monolit.service;

import portal.education.Monolit.data.dto.ItemsDto;
import portal.education.Monolit.data.model.Category;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface CategoryCrudService {
    void addRatingOne(Long categoryId);

    Category getByName(String name);

    Category getById(Long categoryId);

    void delete(Long categoryId);

    ItemsDto<Category> getTopByRating(int numberPage, int size);

    ItemsDto<Category> getTopByAlphabet(String partOfCategory);

    ItemsDto<Category> getAll(int numberPage, int size);
}
