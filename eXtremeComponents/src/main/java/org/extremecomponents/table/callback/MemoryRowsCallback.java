package org.extremecomponents.table.callback;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.limit.Filter;
import org.extremecomponents.table.limit.Sort;
import org.extremecomponents.util.FilterPredicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by jeff
 */
public class MemoryRowsCallback implements RetrieveRowsCallback {
    @Override
    public Collection retrieveRows(TableModel model, Collection rows) throws Exception {
        List result = new ArrayList();
        result.addAll(rows);
        result = applyFilter(result, model.getLimit().getFilterSet().getFilters());
        result = applySort(result,model.getLimit().getSort());
        int startIndex = model.getLimit().getRowStart();
        int endIndex = model.getLimit().getRowEnd();
        if (startIndex>=0 && endIndex<= result.size()) {
            result = result.subList(startIndex,endIndex);
        } else {
            result = Collections.emptyList();
        }
        return result;
    }

    private List applySort(List filterData, Sort sort) {
        if (StringUtils.isEmpty(sort.getSortOrder()) || StringUtils.isEmpty(sort.getProperty())) return filterData;

        if (sort.getSortOrder().equals(TableConstants.SORT_ASC)) {
            BeanComparator comparator = new BeanComparator(sort.getProperty(), new NullComparator());
            Collections.sort(filterData, comparator);
        } else if (sort.getSortOrder().equals(TableConstants.SORT_DESC)) {
            BeanComparator reversedNaturalOrderBeanComparator = new BeanComparator(sort.getProperty(), new ReverseComparator(new NullComparator()));
            Collections.sort(filterData, reversedNaturalOrderBeanComparator);
        }
        return filterData;
    }

    private List applyFilter(List filterData, Filter filters[]) {
        if (filters == null || filters.length > 0) {
            FilterPredicate predicate = new FilterPredicate(filters);
            List resultData = new ArrayList();
            CollectionUtils.select(filterData, predicate, resultData);
            return resultData;
        } else {
            return filterData;
        }

    }

}
