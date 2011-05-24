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
package org.extremecomponents.table.cell;

import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.Cell;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.util.HtmlBuilder;

/**
 * @author Jeff Johnston
 */
public class SelectAllHeaderCell implements Cell {
    public String getExportDisplay(TableModel model, Column column) {
        return column.getTitle();
    }

    public String getHtmlDisplay(TableModel model, Column column) {
        //display filter on this column
        column.setFilterable(false);

        HtmlBuilder html = new HtmlBuilder();

        html.td(2);

        if (column.getColspan()>1) {
            html.colSpan(String.valueOf(column.getColspan()));
        }
        if (column.getRowspan()>1) {
            html.rowSpan(String.valueOf(column.getRowspan()));
        }

        if (StringUtils.isNotEmpty(column.getHeaderClass())) {
            html.styleClass(column.getHeaderClass());
        }

        if (StringUtils.isNotEmpty(column.getHeaderStyle())) {
            html.style(column.getHeaderStyle());
        }

        if (StringUtils.isNotEmpty(column.getWidth())) {
            html.width(column.getWidth());
        }

        html.close();

        String controlName = column.getAlias() + "_selector";
        String selectableControlName = column.getAlias();
        
        html.input("checkbox");
        html.id(controlName);
        html.name(controlName);
        html.title("(Un)Select All");

        html.onclick("for(i = 0; i < document.getElementsByName('" + selectableControlName
                + "').length; i++)document.getElementsByName('" + selectableControlName
                + "').item(i).checked=document.getElementById('" + controlName + "').checked;");
        html.close();

        html.tdEnd();

        return html.toString();
    }
}
