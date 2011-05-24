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

import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.bean.Row;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.util.HtmlBuilder;

/**
 * @author Jeff Johnston
 */
public class RowBuilder {
    private HtmlBuilder html;
    private TableModel model;
    private Row row;

    public RowBuilder(TableModel model) {
        this(new HtmlBuilder(), model);
    }

    public RowBuilder(HtmlBuilder html, TableModel model) {
        this.html = html;
        this.model = model;
        this.row = model.getRowHandler().getRow();
    }

    public HtmlBuilder getHtmlBuilder() {
        return html;
    }

    protected TableModel getTableModel() {
        return model;
    }

    protected Row getRow() {
        return row;
    }

    public void rowStart() {
        html.tr(1);
        styleClass();
        style();
        onclick();
        onmouseover();
        onmouseout();
        html.close();
    }

    public void rowEnd() {
        html.trEnd(1);
    }

    public void style() {
        String style = row.getStyle();
        html.style(style);
    }

    public void styleClass() {
        String styleClass = getStyleClass();
        html.styleClass(styleClass);
    }

    public void onclick() {
        String onclick = row.getOnclick();
        html.onclick(onclick);
    }

    public void onmouseover() {
        boolean highlightRow = row.isHighlightRow();
        if (highlightRow) {
            String highlightClass = row.getHighlightClass();
            if (StringUtils.isNotBlank(row.getOnmouseover())) {
                html.onmouseover("this.className='" + highlightClass + "';" + row.getOnmouseover());
            } else {
                html.onmouseover("this.className='" + highlightClass + "'");
            }
        } else {
            html.onmouseover(row.getOnmouseover());
        }
    }

    public void onmouseout() {
        boolean highlightRow = row.isHighlightRow();
        if (highlightRow) {
            String styleClass = getStyleClass();
            if (StringUtils.isNotBlank(row.getOnmouseout())) {
                html.onmouseout("this.className='" + styleClass + "';" + row.getOnmouseout());
            } else {
                html.onmouseout("this.className='" + styleClass + "'");
            }
        } else {
            html.onmouseout(row.getOnmouseout());
        }
    }

    protected String getStyleClass() {
        String styleClass = row.getStyleClass();
        if (StringUtils.isNotBlank(styleClass)) {
            return styleClass;
        }

        if (model.getRowHandler().isRowEven()) {
            return BuilderConstants.ROW_EVEN_CSS;
        }

        return BuilderConstants.ROW_ODD_CSS;
    }
    
    public String toString() {
        return html.toString();
    }
}
