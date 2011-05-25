package org.extremecomponents.table.core.internal;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.commons.lang.StringUtils;
import org.extremecomponents.util.FilterPredicate;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.limit.Filter;
import org.extremecomponents.table.limit.Sort;
import org.extremecomponents.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by jeff
 */
public class CollectionTableDataSource<T> {

    private List<T> data;
    private List<T> filterData;


    public CollectionTableDataSource(List<T> data) {
        Assert.notNull(data, "CollectionTableDataSource can not be null");
        this.data = data;
    }

    public int getAvailableRows(List<Filter> filter, Sort sort) {

        filterData = new ArrayList<T>(data);
        if (filter!=null && filter.size()>0) {
            applyFilter(filter);
        }
        int availableRows = filterData.size();

        if (sort!=null) {
           applySort(sort);
        }
        return availableRows;

    }

    private void applySort(Sort sort) {
        if (StringUtils.isEmpty(sort.getSortOrder()) || StringUtils.isEmpty(sort.getProperty())) return;

        if (sort.getSortOrder().equals(TableConstants.SORT_ASC)) {
            BeanComparator comparator = new BeanComparator(sort.getProperty(), new NullComparator());
            Collections.sort(filterData, comparator);
        } else if (sort.getSortOrder().equals(TableConstants.SORT_DESC)) {
            BeanComparator reversedNaturalOrderBeanComparator = new BeanComparator(sort.getProperty(), new ReverseComparator(new NullComparator()));
            Collections.sort(filterData, reversedNaturalOrderBeanComparator);
        }
    }

    private void applyFilter(List<Filter> filters) {
        FilterPredicate predicate = new FilterPredicate(filters);
        filterData = new ArrayList<T>();
        CollectionUtils.select(data, predicate, filterData);
    }

    public Collection getData(int startIndex, int endIndex) {
        int availabelRows = filterData.size();
        if (startIndex >= 0 && endIndex <= availabelRows) {
            filterData = filterData.subList(startIndex, endIndex);
        } else {
            filterData = Collections.emptyList();
        }
        return filterData;
    }


}
