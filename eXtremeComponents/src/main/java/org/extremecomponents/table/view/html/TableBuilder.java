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

import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.bean.Table;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.util.HtmlBuilder;

/**
 * @author Jeff Johnston
 */
public class TableBuilder {
    private HtmlBuilder html;
    private TableModel model;
    private Table table;

    public TableBuilder(TableModel model) {
        this(new HtmlBuilder(), model);
    }

    public TableBuilder(HtmlBuilder html, TableModel model) {
        this.html = html;
        this.model = model;
        this.table = model.getTableHandler().getTable();
    }

    public HtmlBuilder getHtmlBuilder() {
        return html;
    }

    protected TableModel getTableModel() {
        return model;
    }

    protected Table getTable() {
        return table;
    }

    public void tableStart() {
        html.table(0);
        id();
        border();
        cellSpacing();
        cellPadding();
        styleClass();
        style();
        width();
        html.close();
    }

    public void tableEnd() {
        html.tableEnd(0);
    }

    public void id() {
        html.id(model.getTableHandler().prefixWithTableId() + BuilderConstants.TABLE);
    }

    public void border() {
        String border = table.getBorder();
        html.border(border);
    }

    public void cellSpacing() {
        String cellSpacing = table.getCellspacing();
        html.cellSpacing(cellSpacing);
    }

    public void cellPadding() {
        String cellPadding = table.getCellpadding();
        html.cellPadding(cellPadding);
    }

    public void styleClass() {
        String styleClass = table.getStyleClass();
        html.styleClass(styleClass);
    }

    public void style() {
        String style = table.getStyle();
        html.style(style);
    }

    public void width() {
        String width = table.getWidth();
        html.width(width);
    }

    public void title() {
        boolean showTitle = BuilderUtils.showTitle(model);
        if (showTitle) {
            String title = model.getTableHandler().getTable().getTitle();
            if (StringUtils.isNotBlank(title)) {
                html.span().styleClass(BuilderConstants.TITLE_CSS).close().append(title).spanEnd();
            }
        }
    }

    public void titleRowSpanColumns() {
        boolean showTitle = BuilderUtils.showTitle(model);
        if (showTitle) {
            String title = model.getTableHandler().getTable().getTitle();
            if (StringUtils.isNotBlank(title)) {
                int columnCount = model.getColumnHandler().columnCount();
                html.tr(1).styleClass(BuilderConstants.TITLE_ROW_CSS).close();
                html.td(2).colSpan("" + columnCount).close();
                html.span().close().append(title).spanEnd();
                html.tdEnd();
                html.trEnd(1);
            }
        }
    }

    public void headerRow() {
        int columnDepth = model.getColumnHandler().getColumnDepth();

        for (int k = 1; k <= columnDepth; k++) {
            html.tr(1).close();

            List columns = model.getColumnHandler().getHeaderColumns();
            for (Iterator iter = columns.iterator(); iter.hasNext(); ) {

                Column column = (Column) iter.next();
                if (column.getDepth()==k) html.append(column.getCellDisplay());
            }

            html.trEnd(1);
        }

    }

    public void filterRow() {
        if (!model.getTableHandler().getTable().isFilterable()) {
            return;
        }

        html.tr(1).styleClass(BuilderConstants.FILTER_CSS).close();

        List columns = model.getColumnHandler().getFilterColumns();
        for (Iterator iter = columns.iterator(); iter.hasNext(); ) {
            Column column = (Column) iter.next();
            html.append(column.getCellDisplay());
        }

        html.trEnd(1);
    }

    public void theadStart() {
        html.thead(1).close();
    }

    public void theadEnd() {
        html.theadEnd(1);
    }

    public void tbodyStart() {
        html.tbody(1).styleClass(BuilderConstants.TABLE_BODY_CSS).close();
    }

    public void tbodyEnd() {
        html.tbodyEnd(1);
    }

    public void themeStart() {
        html.newline();
        String theme = model.getTableHandler().getTable().getTheme();
        html.div().styleClass(theme);
        html.close();
    }

    public void themeEnd() {
        html.newline();
        html.divEnd();
    }

    public String toString() {
        return html.toString();
    }
}
