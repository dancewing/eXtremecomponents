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

import org.extremecomponents.table.core.TableDataSource;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.util.StringUtils;

/**
 * @author Jeff Johnston
 */
public class Table extends Attributes {
    private TableModel model;

    private String action;
    private Boolean autoIncludeParameters;
    private String border;
    private Boolean bufferView;
    private String cellpadding;
    private String cellspacing;
    private Boolean filterable;
    private String form;
    private String imagePath;
    private String interceptor;
    private String locale;
    private int maxRowsDisplayed;
    private int medianRowsDisplayed;
    private String method;
    private String onInvokeAction;
    private int rowsDisplayed;
    private Boolean saveFilterSort;
    private String scope;
    private Boolean showExports;
    private Boolean showPagination;
    private Boolean showStatusBar;
    private Boolean showTitle;
    private Boolean showTooltips;
    private Boolean sortable;
    private String state;
    private String stateAttr;
    private String style;
    private String styleClass;
    private String tableId;
    private String title;
    private String theme;
    private String var;
    private String view;
    private String width;
    private String retrieveRowsCallback;

    private TableDataSource dataSource;
    private String inPlace;

    public Table(TableModel model) {
        this.model = model;
    }

    public void defaults() {
        this.tableId = TableDefaults.getTableId(tableId); //must be first
        
        this.autoIncludeParameters = TableDefaults.getAutoIncludeParameters(model, autoIncludeParameters);
        this.border = TableDefaults.getBorder(model, border);
        this.bufferView = TableDefaults.isBufferView(model, bufferView);
        this.cellpadding = TableDefaults.getCellpadding(model, cellpadding);
        this.cellspacing = TableDefaults.getCellspacing(model, cellspacing);
        this.filterable = TableDefaults.isFilterable(model, filterable);
        this.imagePath = TableDefaults.getImagePath(model, imagePath);
        this.maxRowsDisplayed = TableDefaults.getMaxRowsDisplayed(model, maxRowsDisplayed);
        this.medianRowsDisplayed = TableDefaults.getMedianRowsDisplayed(model, medianRowsDisplayed);
        this.method = TableDefaults.getMethod(model, method);
        this.rowsDisplayed = TableDefaults.getRowsDisplayed(model, rowsDisplayed);
        this.showPagination = TableDefaults.isShowPagination(model, showPagination);
        this.showExports = TableDefaults.isShowExports(model, showExports);
        this.showStatusBar = TableDefaults.isShowStatusBar(model, showStatusBar);
        this.showTitle = TableDefaults.isShowTitle(model, showTitle);
        this.showTooltips = TableDefaults.isShowTooltips(model, showTooltips);
        this.state = TableDefaults.getState(model, state);
        this.stateAttr = TableDefaults.getStateAttr(model, stateAttr);
        this.sortable = TableDefaults.isSortable(model, sortable);
        this.styleClass = TableDefaults.getStyleClass(model, styleClass);
        this.theme = TableDefaults.getTheme(model, theme);
        this.title = TableDefaults.getTitle(model, title);
        this.var = TableDefaults.getVar(var, tableId);
        this.view = TableDefaults.getView(view);
        this.width = TableDefaults.getWidth(model, width);

        this.retrieveRowsCallback = TableDefaults.getRetrieveRowsCallback(model, retrieveRowsCallback);
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isAutoIncludeParameters() {
        return autoIncludeParameters.booleanValue();
    }

    public void setAutoIncludeParameters(Boolean autoIncludeParameters) {
        this.autoIncludeParameters = autoIncludeParameters;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public boolean isBufferView() {
        return bufferView.booleanValue();
    }

    public void setBufferView(Boolean bufferView) {
        this.bufferView = bufferView;
    }

    public String getCellpadding() {
        return cellpadding;
    }

    public void setCellpadding(String cellpadding) {
        this.cellpadding = cellpadding;
    }

    public String getCellspacing() {
        return cellspacing;
    }

    public void setCellspacing(String cellspacing) {
        this.cellspacing = cellspacing;
    }

    public boolean isFilterable() {
        return filterable.booleanValue();
    }

    public void setFilterable(Boolean filterable) {
        this.filterable = filterable;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(String interceptor) {
        this.interceptor = interceptor;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getOnInvokeAction() {
        return onInvokeAction;
    }

    public void setOnInvokeAction(String onInvokeAction) {
        this.onInvokeAction = onInvokeAction;
    }

    public boolean isSaveFilterSort() {
        return saveFilterSort.booleanValue();
    }

    public void setSaveFilterSort(Boolean saveFilterSort) {
        this.saveFilterSort = saveFilterSort;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isShowExports() {
        return showExports.booleanValue();
    }

    public void setShowExports(Boolean showExports) {
        this.showExports = showExports;
    }

    public boolean isShowPagination() {
        return showPagination.booleanValue();
    }

    public void setShowPagination(Boolean showPagination) {
        this.showPagination = showPagination;
    }

    public boolean isShowStatusBar() {
        return showStatusBar.booleanValue();
    }

    public void setShowStatusBar(Boolean showStatusBar) {
        this.showStatusBar = showStatusBar;
    }

    public boolean isShowTitle() {
        return showTitle.booleanValue();
    }

    public void setShowTitle(Boolean showTitle) {
        this.showTitle = showTitle;
    }

    public boolean isShowTooltips() {
        return showTooltips.booleanValue();
    }

    public void setShowTooltips(Boolean showTooltips) {
        this.showTooltips = showTooltips;
    }

    public boolean isSortable() {
        return sortable.booleanValue();
    }

    public void setSortable(Boolean sortable) {
        this.sortable = sortable;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateAttr() {
        return stateAttr;
    }

    public void setStateAttr(String stateAttr) {
        this.stateAttr = stateAttr;
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

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public int getRowsDisplayed() {
        return rowsDisplayed;
    }

    public void setRowsDisplayed(int rowsDisplayed) {
        this.rowsDisplayed = rowsDisplayed;
    }

    public int getMedianRowsDisplayed() {
        return medianRowsDisplayed;
    }

    public void setMedianRowsDisplayed(int medianRowsDisplayed) {
        this.medianRowsDisplayed = medianRowsDisplayed;
    }

    public int getMaxRowsDisplayed() {
        return maxRowsDisplayed;
    }

    public void setMaxRowsDisplayed(int maxRowsDisplayed) {
        this.maxRowsDisplayed = maxRowsDisplayed;
    }

    public String getRetrieveRowsCallback() {
        return retrieveRowsCallback;
    }

    public void setRetrieveRowsCallback(String retrieveRowsCallback) {
        this.retrieveRowsCallback = retrieveRowsCallback;
    }

    public void setDataSource(TableDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public TableDataSource getDataSource() {
        return dataSource;
    }

    public String getInPlace() {
        return inPlace;
    }

    public void setInPlace(String inPlace) {
        this.inPlace = inPlace;
    }

    public boolean isAjaxTable() {
        return StringUtils.hasText(inPlace);
    }
}