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
 * @deprecated Use the non-static ColumnBuilder class instead.
 * 
 * @author Jeff Johnston
 */
public class CellBuilder {
    private CellBuilder() {
    }

    /**
     * @deprecated Use the non-static ColumnBuilder class instead.
     */
    public static void tdStart(HtmlBuilder html, Column column) {
        html.td(2);
        styleClass(html, column);
        style(html, column);
        width(html, column);
        html.close();
    }

    /**
     * @deprecated Use the non-static ColumnBuilder class instead.
     */
    public static void tdEnd(HtmlBuilder html) {
        html.tdEnd();
    }

    /**
     * @deprecated Use the non-static ColumnBuilder class instead.
     */
    public static void style(HtmlBuilder html, Column column) {
        String style = column.getStyle();
        html.style(style);
    }

    /**
     * @deprecated Use the non-static ColumnBuilder class instead.
     */
    public static void styleClass(HtmlBuilder html, Column column) {
        String styleClass = column.getStyleClass();
        html.styleClass(styleClass);
    }

    /**
     * @deprecated Use the non-static ColumnBuilder class instead.
     */
    public static void width(HtmlBuilder html, Column column) {
        String width = column.getWidth();
        html.width(width);
    }

    /**
     * @deprecated Use the non-static ColumnBuilder class instead.
     */
    public static void tdBody(HtmlBuilder html, String value) {
        if (StringUtils.isNotBlank(value)) {
            html.append(value);
        } else {
            html.nbsp();
        }
    }
}
