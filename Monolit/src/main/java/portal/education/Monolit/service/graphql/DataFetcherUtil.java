package portal.education.Monolit.service.graphql;


import graphql.schema.DataFetchingEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataFetcherUtil {

    public static final Pageable buildSort(DataFetchingEnvironment dataFetchingEnvironment) {
        Integer page = dataFetchingEnvironment.getArgument("page");
        Integer size = dataFetchingEnvironment.getArgument("size");

        var properties = new ArrayList<String>(dataFetchingEnvironment.getArgument("sortedBy"));

        List<Sort.Order> orders = properties.stream().map(Sort.Order::by).collect(Collectors.toList());

        if (dataFetchingEnvironment.containsArgument("direction")) {
            var directions = new ArrayList<String>(dataFetchingEnvironment.getArgument("direction"));

            for (int i = 0; i < properties.size() && i < directions.size(); i++) {
                Sort.Order order = directions.get(i).equals("ASC") ?
                        Sort.Order.asc(properties.get(i)) : Sort.Order.desc(properties.get(i));
                orders.set(i, order);
            }
        }

        return PageRequest.of(page, size, Sort.by(orders));
    }

}
