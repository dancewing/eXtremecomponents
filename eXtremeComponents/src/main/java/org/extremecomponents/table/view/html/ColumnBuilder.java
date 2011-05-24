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
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.util.HtmlBuilder;

/**
 * @author Jeff Johnston
 */
public class ColumnBuilder {
    private HtmlBuilder html;
    private Column column;

    public ColumnBuilder(Column column) {
        this(new HtmlBuilder(), column);
    }

    public ColumnBuilder(HtmlBuilder html, Column column) {
        this.html = html;
        this.column = column;
    }

    public HtmlBuilder getHtmlBuilder() {
        return html;
    }

    protected Column getColumn() {
        return column;
    }

    public void tdStart() {
        html.td(2);
        styleClass();
        style();
        width();
        html.close();
    }

    public void tdEnd() {
        html.tdEnd();
    }

    public void style() {
        String style = column.getStyle();
        html.style(style);
    }

    public void styleClass() {
        String styleClass = column.getStyleClass();
        html.styleClass(styleClass);
    }

    public void width() {
        String width = column.getWidth();
        html.width(width);
    }

    public void tdBody(String value) {
        if (StringUtils.isNotBlank(value)) {
            html.append(value);
        } else {
            html.nbsp();
        }
    }
    
    public String toString() {
        return html.toString();
    }
}
