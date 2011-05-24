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
package org.extremecomponents.table.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.xpath.operations.And;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.calc.CalcResult;
import org.extremecomponents.table.calc.CalcUtils;
import org.extremecomponents.table.cell.Cell;
import org.extremecomponents.table.core.PreferencesConstants;
import org.extremecomponents.table.core.TableCache;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.core.TableModelUtils;
import org.extremecomponents.util.CollectionUtils;

/**
 * The first pass over the table just loads up the column properties. The
 * properties will be loaded up and held here.
 *
 * @author Jeff Johnston
 */
public class ColumnHandler {
    private TableModel model;
    private List<Column> columns = new ArrayList<Column>();
    private Column firstColumn;
    private Column lastColumn;

    private int columnDepth = 0;
    private int columnCount = 0;

    public ColumnHandler(TableModel model) {
        this.model = model;
    }

    public void addAutoGenerateColumn(Column column) {
        column.addAttribute(TableConstants.IS_AUTO_GENERATE_COLUMN, "true");
        addColumn(column);
    }

    public void addColumn(Column column) {
        column.defaults();

        addColumnAttributes(column);

        if (!isViewAllowed(column)) {
            return;
        }

        if (firstColumn == null) {
            firstColumn = column;
            column.setFirstColumn(true);
        }

        if (lastColumn != null) {
            lastColumn.setLastColumn(false);
        }

        lastColumn = column;
        column.setLastColumn(true);

        if (column.getDepth() > columnDepth) columnDepth = column.getDepth();

        if (column.getParent() != null) {
            column.getParent().addChildren(column);
            if (column.getParent().isFirstColumn() && column.getParent().getChildren().size() == 1) {
                column.setFirstColumn(true);
            }
        }

        if (column.getParent() == null) {
            columnCount++;
        } else {
            if (column.getParent().getChildren().size() > 1) {
                columnCount++;
            }
        }

        columns.add(column);
        calcColumns();
    }

    private void calcColumns() {
        if (columnDepth > 1) {
            for (Column column : columns) {
                column.setColspan(calcColumnCols(column));
                column.setRowspan(calcColumnRows(column));
            }
        }

    }

    private int calcColumnRows(Column column) {
        if (CollectionUtils.isEmpty(column.getChildren())) {
            return columnDepth - column.getDepth() + 1;
        } else {
            return 1;
        }

    }

    private int calcColumnCols(Column column) {
        int result = 0;
        if (!CollectionUtils.isEmpty(column.getChildren())) {
            for (Column c : column.getChildren()) {
                if (!CollectionUtils.isEmpty(c.getChildren())) {
                    result += calcColumnCols(c);
                } else {
                    result++;
                }
            }
        } else {
            result = 1;
        }

        return result;
    }

    public void addColumnAttributes(Column column) {
        String interceptor = TableModelUtils.getInterceptPreference(model, column.getInterceptor(), PreferencesConstants.COLUMN_INTERCEPTOR);
        column.setInterceptor(interceptor);
        TableCache.getInstance().getColumnInterceptor(interceptor).addColumnAttributes(model, column);
    }

    public void modifyColumnAttributes(Column column) {
        TableCache.getInstance().getColumnInterceptor(column.getInterceptor()).modifyColumnAttributes(model, column);
    }

    public int columnCount() {
        // return columns.size();
        return columnCount;
    }

    public List<Column> getColumns() {
        List<Column> result = new ArrayList<Column>();
        for (Column column : columns) {
            if (column.getChildren() == null) {
                result.add(column);
            }
        }
        return result;
    }

    public Column getColumnByAlias(String alias) {
        for (Iterator iter = columns.iterator(); iter.hasNext(); ) {
            Column column = (Column) iter.next();
            String columnAlias = column.getAlias();
            if (columnAlias != null && columnAlias.equals(alias)) {
                return column;
            }
        }

        return null;
    }

    public boolean hasMetatData() {
        return columnCount() > 0;
    }

    public List<Column> getFilterColumns() {
        boolean cleared = model.getLimit().isCleared();
        List<Column> result = new ArrayList<Column>();

        for (Iterator iter = columns.iterator(); iter.hasNext(); ) {
            Column column = (Column) iter.next();
            if (column.getChildren() != null) continue;

            String value = model.getLimit().getFilterSet().getFilterValue(column.getAlias());
            if (cleared || StringUtils.isEmpty(value)) {
                value = "";
            }
            Cell cell = TableModelUtils.getFilterCell(column, value);
            column.setCellDisplay(cell.getHtmlDisplay(model, column));
            result.add(column);
        }

        return result;
    }

    public List<Column> getHeaderColumns() {
        boolean isExported = model.getLimit().isExported();
        for (Column column : columns) {
            Cell cell = TableModelUtils.getHeaderCell(column, column.getTitle());
            if (!isExported) {
                column.setCellDisplay(cell.getHtmlDisplay(model, column));
            } else {
                column.setCellDisplay(cell.getExportDisplay(model, column));
            }
        }
        return columns;
    }

    private boolean isViewAllowed(Column column) {
        String view = model.getTableHandler().getTable().getView();

        boolean isExported = model.getLimit().isExported();
        if (isExported) {
            view = model.getExportHandler().getCurrentExport().getView();
        }

        boolean allowView = allowView(column, view);
        boolean denyView = denyView(column, view);

        if (allowView & !denyView) {
            return true;
        }

        return false;
    }

    private boolean allowView(Column column, String view) {
        String viewsAllowed[] = column.getViewsAllowed();
        if (viewsAllowed == null || viewsAllowed.length == 0) {
            return true;
        }

        for (int i = 0; i < viewsAllowed.length; i++) {
            if (view.equals(viewsAllowed[i])) {
                return true;
            }
        }

        return false;
    }

    private boolean denyView(Column column, String view) {
        String viewsDenied[] = column.getViewsDenied();
        if (viewsDenied == null || viewsDenied.length == 0) {
            return false;
        }

        for (int i = 0; i < viewsDenied.length; i++) {
            if (view.equals(viewsDenied[i])) {
                return true;
            }
        }

        return false;
    }

    public Column getFirstCalcColumn() {
        for (Iterator iter = columns.iterator(); iter.hasNext(); ) {
            Column column = (Column) iter.next();
            if (column.isCalculated()) {
                return column;
            }
        }

        return null;
    }

    public CalcResult[] getCalcResults(Column column) {
        if (!column.isCalculated()) {
            return null;
        }

        return CalcUtils.getCalcResults(model, column);
    }

    public int getColumnDepth() {
        return columnDepth;
    }
}