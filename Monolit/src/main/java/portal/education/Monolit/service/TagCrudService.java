package portal.education.Monolit.service;

import portal.education.Monolit.data.model.Category;
import portal.education.Monolit.data.model.Tag;

import java.util.List;
import java.util.Set;


public interface TagCrudService {
    void addRatingOne(Long tagId);

    Tag getByName(String name);

    Set<Tag> getByNames(List<String> names);

    List<Tag> getTagsTopByRating(int numberPage, int size);

    List<Tag> getTagsTopByAlphabet(String partOfName);
}
