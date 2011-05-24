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
import org.extremecomponents.table.core.MessagesConstants;
import org.extremecomponents.table.core.PreferencesConstants;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.core.TableModelUtils;
import org.extremecomponents.table.view.html.BuilderConstants;
import org.extremecomponents.table.view.html.BuilderUtils;
import org.extremecomponents.table.view.html.TableActions;
import org.extremecomponents.util.HtmlBuilder;

/**
 * A header cell.
 *
 * @author Jeff Johnston
 */
public class HeaderCell implements Cell {
    public String getExportDisplay(TableModel model, Column column) {
        return column.getTitle();
    }

    public String getHtmlDisplay(TableModel model, Column column) {

        HtmlBuilder html = new HtmlBuilder();

        String headerClass = null;
        String sortImage = null;
        String sortOrder = null;

        headerClass = column.getHeaderClass();

        if (TableModelUtils.isSorted(model, column.getAlias())) {
            sortOrder = model.getLimit().getSort().getSortOrder();

            if (sortOrder.equals(TableConstants.SORT_DEFAULT)) {
                sortOrder = TableConstants.SORT_ASC;
            } else if (sortOrder.equals(TableConstants.SORT_ASC)) {
                headerClass = model.getPreferences().getPreference(PreferencesConstants.TABLE_HEADER_SORT_CLASS);
                sortImage = BuilderUtils.getImage(model, BuilderConstants.SORT_ASC_IMAGE);
                sortOrder = TableConstants.SORT_DESC;
            } else if (sortOrder.equals(TableConstants.SORT_DESC)) {
                headerClass = model.getPreferences().getPreference(PreferencesConstants.TABLE_HEADER_SORT_CLASS);
                sortImage = BuilderUtils.getImage(model, BuilderConstants.SORT_DESC_IMAGE);
                sortOrder = TableConstants.SORT_DEFAULT;
            }
        } else {
            sortOrder = TableConstants.SORT_ASC; // the default
        }

        buildHeaderHtml(html, model, column, headerClass, sortImage, sortOrder);

        return html.toString();
    }

    protected void buildHeaderHtml(HtmlBuilder html, TableModel model, Column column, String headerClass, String sortImage, String sortOrder) {
        html.td(2);

        if (column.getColspan() > 1) {
            html.styleClass(BuilderConstants.TABLE_HEADER_ROW_CSS);
        } else if (StringUtils.isNotEmpty(headerClass)) {
            html.styleClass(headerClass);
        }

        if (StringUtils.isNotEmpty(column.getHeaderStyle())) {
            html.style(column.getHeaderStyle());
        }

        if (StringUtils.isNotEmpty(column.getWidth())) {
            html.width(column.getWidth());
        }

        if (column.getColspan() > 1) {
            html.colSpan(String.valueOf(column.getColspan()));
        }
        if (column.getRowspan() > 1) {
            html.rowSpan(String.valueOf(column.getRowspan()));
        }

        if (column.isSortable()) {
            if (sortOrder.equals(TableConstants.SORT_ASC)) {
                html.onmouseover("this.className='" + BuilderConstants.TABLE_HEADER_SORT_CSS + "';this.style.cursor='pointer'");
                if (StringUtils.isNotEmpty(headerClass)) {
                    html.onmouseout("this.className='" + headerClass + "';this.style.cursor='default'");
                } else {
                    html.onmouseout("this.className='" + BuilderConstants.TABLE_HEADER_CSS + "';this.style.cursor='default'");
                }
            }

            if (sortOrder.equals(TableConstants.SORT_DEFAULT) || sortOrder.equals(TableConstants.SORT_DESC)) {
                html.onmouseover("this.style.cursor='pointer'");
                html.onmouseout("this.style.cursor='default'");
            }

            html.onclick(new TableActions(model).getSortAction(column, sortOrder));

            boolean showTooltips = model.getTableHandler().getTable().isShowTooltips();
            if (showTooltips) {
                String headercellTooltip = model.getMessages().getMessage(MessagesConstants.COLUMN_HEADERCELL_TOOLTIP_SORT);
                html.title(headercellTooltip + " " + column.getTitle());
            }
        }

        html.close();

        html.append(column.getTitle());

        if (column.isSortable()) {
            if (StringUtils.isNotEmpty(sortImage)) {
                html.nbsp();
                html.img();
                html.src(sortImage);
                html.style("border:0");
                html.alt("Arrow");
                html.xclose();
            }
        }

        html.tdEnd();
    }
}
