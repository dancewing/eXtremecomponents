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
package org.extremecomponents.table.bean;

import org.extremecomponents.table.core.TableModel;

public class Row extends Attributes {
    private TableModel model;

    private String highlightClass;
    private Boolean highlightRow;
    private String interceptor;
    private String onclick;
    private String onmouseout;
    private String onmouseover;
    private String style;
    private String styleClass;

    private int rowCount;

    public Row(TableModel model) {
        this.model = model;
    }

    public void defaults() {
        this.highlightClass = RowDefaults.getHighlightClass(model, highlightClass);
        this.highlightRow = RowDefaults.isHighlightRow(model, highlightRow);
    }

    public String getHighlightClass() {
        return highlightClass;
    }

    public void setHighlightClass(String highlightClass) {
        this.highlightClass = highlightClass;
    }

    public boolean isHighlightRow() {
        return highlightRow;
    }

    public void setHighlightRow(Boolean highlightRow) {
        this.highlightRow = highlightRow;
    }
    
    public String getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(String interceptor) {
        this.interceptor = interceptor;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getOnmouseout() {
        return onmouseout;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public String getOnmouseover() {
        return onmouseover;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowcount) {
        this.rowCount = rowcount;
    }
}
