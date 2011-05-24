package org.extremecomponents.table.limit;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.extremecomponents.table.core.TableConstants;

/**
 * Created by jeff
 */
public class SortSet {
    private String action;
    private Sort[] sorts;

    public SortSet() {
    }

    public SortSet(String action, Sort sorts[]) {
        this.action = action;
        this.sorts = sorts;
    }

    public boolean isFiltered() {
        return (action != null && action.equals(TableConstants.FILTER_ACTION) && sorts != null && sorts.length > 0);
    }

    public boolean isCleared() {
        return action != null && action.equals(TableConstants.CLEAR_ACTION);
    }

    public String getAction() {
        return action;
    }

    public Sort[] getSorts() {
        return sorts;
    }

    /**
     * For a given filter, referenced by the alias, retrieve the value.
     *
     * @param alias The Filter alias
     * @return The Filter value
     */
    public String getSortOrder(String alias) {
        for (int i = 0; i < sorts.length; i++) {
            Sort sort = sorts[i];
            if (sort.getAlias().equals(alias)) {
                return sort.getSortOrder();
            }
        }

        return "";
    }

    /**
     * For a given filter, referenced by the alias, retrieve the Filter.
     *
     * @param alias The Filter alias
     * @return The Filter value
     */
    public Sort getSort(String alias) {
        for (int i = 0; i < sorts.length; i++) {
            Sort sort = sorts[i];
            if (sort.getAlias().equals(alias)) {
                return sort;
            }
        }

        return null;
    }

    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("action", action);

        if (sorts != null) {
            for (int i = 0; i < sorts.length; i++) {
                Sort sort = sorts[i];
                builder.append(sort.toString());
            }
        }

        return builder.toString();
    }
}
