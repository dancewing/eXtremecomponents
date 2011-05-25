package org.extremecomponents.util;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.limit.Sort;
import org.extremecomponents.table.view.html.BuilderUtils;
import org.extremecomponents.util.StringUtils;

import java.util.*;

/**
 * Created by jeff
 */
public class ParamsBuilder {
    private Map<String, Object> params;
    private TableModel model;

    public ParamsBuilder(TableModel model) {
        this.model = model;
        this.params = new HashMap<String, Object>();
    }

    public Map<String, Object> getParams() {
        instanceParameter();
        exportTableIdParameter();
        exportParameters();
        rowsDisplayedParameter();
        filterParameter();
        pageParameters();
        sortParameters();
        aliasParameters();
        formActionParameters();
        userDefinedParameters();
        return params;
    }

    private void formActionParameters() {
        params.put(model.getTableHandler().prefixWithTableId() + TableConstants.EXTREME_FORM_ACTION,
                model.getTableHandler().getTable().getAction());
    }

    public String toJson() {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, Object>> entries = params.entrySet();
        sb.append("{");
        for (Map.Entry<String, Object> entry : entries) {
            Object value = entry.getValue();
            sb.append(entry.getKey());
            sb.append(":");
            if (value == null) {
                sb.append("''");
            } else {
                //TODO change to safe
                sb.append("'");
                if (value instanceof String) {
                    sb.append((String) value);
                } else if (value instanceof Number) {
                    sb.append(value);
                } else {
                    sb.append(value);
                }
                sb.append("'");
            }
            sb.append(",");

        }
        int k = sb.lastIndexOf(",");
        if (k>0){
           sb.deleteCharAt(k);
        }
        sb.append("}");
        return sb.toString();
    }

    private void userDefinedParameters() {
        Map parameterMap = model.getRegistry().getParameterMap();
        Set keys = parameterMap.keySet();
        for (Iterator iter = keys.iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();

            if (name.startsWith(model.getTableHandler().prefixWithTableId())) {
                continue;
            }

            String values[] = (String[]) parameterMap.get(name);
            if (values == null || values.length == 0) {
                params.put(name, "");
            } else {
                params.put(name, StringUtils.arrayToDelimitedString(values, ","));
            }
        }
    }

    private void aliasParameters() {
        List columns = model.getColumnHandler().getColumns();
        for (Iterator iter = columns.iterator(); iter.hasNext(); ) {
            Column column = (Column) iter.next();
            if (StringUtils.hasText(column.getProperty()) && !column.getProperty().equals(column.getAlias())) {
                params.put(model.getTableHandler().prefixWithTableId() + TableConstants.ALIAS + column.getAlias(),
                        column.getProperty());
            }
        }
    }

    private void sortParameters() {
        List columns = model.getColumnHandler().getColumns();
        for (Iterator iter = columns.iterator(); iter.hasNext(); ) {
            Column column = (Column) iter.next();
            if (column.isSortable()) {
                Sort sort = model.getLimit().getSort();
                String sortOrder = null;
                if (sort.isSorted() && sort.getAlias().equals(column.getAlias())) {
                    sortOrder = sort.getSortOrder();
                }
                params.put(model.getTableHandler().prefixWithTableId() + TableConstants.SORT + column.getAlias(),
                        sortOrder);
            }
        }
    }

    private void pageParameters() {
        params.put(model.getTableHandler().prefixWithTableId() + TableConstants.PAGE,
                String.valueOf(model.getLimit().getPage()));
    }

    private void filterParameter() {
        if (model.getLimit().isFiltered()) {
            params.put(model.getTableHandler().prefixWithTableId() + TableConstants.FILTER + TableConstants.ACTION,
                    TableConstants.FILTER_ACTION);
        }
    }

    private void rowsDisplayedParameter() {
        params.put(model.getTableHandler().prefixWithTableId() + TableConstants.CURRENT_ROWS_DISPLAYED,
                String.valueOf(model.getLimit().getCurrentRowsDisplayed()));
    }

    private void exportParameters() {
        if (!BuilderUtils.showExports(model)) {
            return;
        }
        params.put(model.getTableHandler().prefixWithTableId() + TableConstants.EXPORT_VIEW, "");
        params.put(model.getTableHandler().prefixWithTableId() + TableConstants.EXPORT_FILE_NAME, "");
    }

    private void exportTableIdParameter() {
        if (!BuilderUtils.showExports(model)) {
            return;
        }

        String form = BuilderUtils.getForm(model);
        String existingForm = (String) model.getContext().getRequestAttribute(TableConstants.EXPORT_TABLE_ID);
        if (form.equals(existingForm)) {
            return;
        }

        params.put(TableConstants.EXPORT_TABLE_ID, "");
        // set to key off to other tables in the same form
        model.getContext().setRequestAttribute(TableConstants.EXPORT_TABLE_ID, form);
    }

    private void instanceParameter() {
        params.put(TableConstants.EXTREME_COMPONENTS_INSTANCE, model.getTableHandler().getTable().getTableId());
    }
}
