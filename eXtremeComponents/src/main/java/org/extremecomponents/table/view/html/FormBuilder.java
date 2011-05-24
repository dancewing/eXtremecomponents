/*
 * Copyright 2004 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.extremecomponents.table.view.html;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.limit.Sort;
import org.extremecomponents.util.HtmlBuilder;

/**
 * @author Jeff Johnston
 */
public class FormBuilder {
    private HtmlBuilder html;
    private TableModel model;

    public FormBuilder(TableModel model) {
        this(new HtmlBuilder(), model);
    }

    public FormBuilder(HtmlBuilder html, TableModel model) {
        this.html = html;
        this.model = model;
    }

    public HtmlBuilder getHtmlBuilder() {
        return html;
    }

    protected TableModel getTableModel() {
        return model;
    }

    public void formStart() {
        formAttributes();
        html.newline();
        html.div().close();
        instanceParameter();
        exportTableIdParameter();
        exportParameters();
        rowsDisplayedParameter();
        filterParameter();
        pageParameters();
        sortParameters();
        aliasParameters();
        userDefinedParameters();
        html.newline();
        html.divEnd();
    }

    public void formEnd() {
        String form = model.getTableHandler().getTable().getForm();
        if (StringUtils.isBlank(form)) {
            html.formEnd();
        }
    }

    public void formAttributes() {
        String form = model.getTableHandler().getTable().getForm();
        if (StringUtils.isBlank(form)) {
            html.form();
            html.id(model.getTableHandler().getTable().getTableId());
            html.action(model.getTableHandler().getTable().getAction());
            html.method(model.getTableHandler().getTable().getMethod());
            html.close();
        }
    }

    public void instanceParameter() {
        html.newline();
        html.input("hidden");
        html.name(TableConstants.EXTREME_COMPONENTS_INSTANCE);
        html.value(model.getTableHandler().getTable().getTableId());
        html.xclose();
    }

    public void filterParameter() {
        if (BuilderUtils.filterable(model)) {
            html.newline();
            html.input("hidden");
            html.name(model.getTableHandler().prefixWithTableId() + TableConstants.FILTER + TableConstants.ACTION);
            if (model.getLimit().isFiltered()) {
                html.value(TableConstants.FILTER_ACTION);
            }
            html.xclose();
        }
    }

    public void rowsDisplayedParameter() {
        html.newline();
        html.input("hidden");
        html.name(model.getTableHandler().prefixWithTableId() + TableConstants.CURRENT_ROWS_DISPLAYED);
        int currentRowsDisplayed = model.getLimit().getCurrentRowsDisplayed();
        html.value(String.valueOf(currentRowsDisplayed));
        html.xclose();
    }

    public void pageParameters() {
        html.newline();
        html.input("hidden");
        html.name(model.getTableHandler().prefixWithTableId() + TableConstants.PAGE);
        int page = model.getLimit().getPage();
        if (page > 0) {
            html.value(String.valueOf(page));
        }
        html.xclose();
    }

    /**
     * The exported table id parameter is used to uniquely identify this table when exporting.
     * If there is more than one table in the form then make sure the other table did
     * not already set the exported table id parameter.
     */
    public void exportTableIdParameter() {
        if (!BuilderUtils.showExports(model)) {
            return;
        }

        String form = BuilderUtils.getForm(model);
        String existingForm = (String)model.getContext().getRequestAttribute(TableConstants.EXPORT_TABLE_ID);
        if (form.equals(existingForm)) {
            return;
        }

        html.newline();
        html.input("hidden");
        html.name(TableConstants.EXPORT_TABLE_ID);
        html.xclose();

        // set to key off to other tables in the same form
        model.getContext().setRequestAttribute(TableConstants.EXPORT_TABLE_ID, form);
    }

    /**
     * The parameters neccessary to do the exports. This includes the
     * ViewResolver and the export file name.
     */
    public void exportParameters() {
        if (!BuilderUtils.showExports(model)) {
            return;
        }

        html.newline();
        html.input("hidden");
        html.name(model.getTableHandler().prefixWithTableId() + TableConstants.EXPORT_VIEW);
        html.xclose();

        html.newline();
        html.input("hidden");
        html.name(model.getTableHandler().prefixWithTableId() + TableConstants.EXPORT_FILE_NAME);
        html.xclose();
    }

    public void sortParameters() {
        List columns = model.getColumnHandler().getColumns();
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            if (column.isSortable()) {
                html.newline();
                html.input("hidden");
                html.name(model.getTableHandler().prefixWithTableId() + TableConstants.SORT + column.getAlias());
                Sort sort = model.getLimit().getSort();
                if (sort.isSorted() && sort.getAlias().equals(column.getAlias())) {
                    html.value(sort.getSortOrder());
                }
                html.xclose();
            }
        }
    }

    public void userDefinedParameters() {
        Map parameterMap = model.getRegistry().getParameterMap();
        Set keys = parameterMap.keySet();
        for (Iterator iter = keys.iterator(); iter.hasNext();) {
            String name = (String) iter.next();

            if (name.startsWith(model.getTableHandler().prefixWithTableId())) {
                continue;
            }

            String values[] = (String[]) parameterMap.get(name);
            if (values == null || values.length == 0) {
                html.newline();
                html.input("hidden").name(name).xclose();
            } else {
                for (int i = 0; i < values.length; i++) {
                    html.newline();
                    html.input("hidden").name(name).value(values[i]).xclose();
                }
            }
        }
    }

    /**
     * If the column has a alias, it will keep the column property by the parameter
     */
    public void aliasParameters() {
        List columns = model.getColumnHandler().getColumns();
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            if (StringUtils.isNotBlank(column.getProperty()) && !column.getProperty().equals(column.getAlias())) {
                html.newline();
                html.input("hidden");
                html.name(model.getTableHandler().prefixWithTableId() + TableConstants.ALIAS + column.getAlias());
                html.value(column.getProperty());
                html.xclose();
            }
        }
    }


    public String toString() {
        return html.toString();
    }
}
