package portal.education.Monolit.data.dto;

import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.jsonview.JsonViews;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@JsonView({JsonViews.Short.class, JsonViews.Public.class, JsonViews.Internal.class, JsonViews.Author.class})
@NoArgsConstructor
public class ItemsDto<E> {
    Long total = 0L;
    List<E> items;

    public ItemsDto(List<E> items) {
        this.items = items;
        this.total = Long.valueOf(items.size());
    }

    public E getItem(Integer id) {
        return items.get(id);
    }
}
