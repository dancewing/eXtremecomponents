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
package org.extremecomponents.table.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.core.TableModelUtils;
import org.extremecomponents.table.interceptor.ColumnInterceptor;
import org.extremecomponents.util.ExceptionUtils;

/**
 * @author Jeff Johnston
 * @jsp.tag name="column" display-name="ColumnTag" body-content="JSP"
 * description="The container which holds all the column specific
 * information. A copy of each column will be fed to the Model."
 */
public class ColumnTag extends BodyTagSupport implements ColumnInterceptor {
    private String alias;
    private String calc;
    private String calcTitle;
    private String cell;
    private Object filterOptions;
    private String filterable;
    private String filterCell;
    private String filterClass;
    private String filterStyle;
    private String format;
    private String headerCell;
    private String headerClass;
    private String headerStyle;
    private String interceptor;
    private String parse;
    private String property;
    private String sortable;
    private String style;
    private String styleClass;
    private String title;
    private Object value;
    private String viewsAllowed;
    private String viewsDenied;
    private String width;
    private String escapeAutoFormat;

    /**
     * @jsp.attribute description="Used to uniquely identify the column when the
     * same property is used for more than one column."
     * required="false" rtexprvalue="true"
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @jsp.attribute description="A fully qualified class name to a
     * custom Calc implementation. Could also be a named type
     * in the preferences. Used to do math on a column."
     * required="false" rtexprvalue="true"
     */
    public void setCalc(String calc) {
        this.calc = calc;
    }

    /**
     * @jsp.attribute description="The title of the calc."
     * required="false" rtexprvalue="true"
     */
    public void setCalcTitle(String totalTitle) {
        this.calcTitle = totalTitle;
    }

    /**
     * @jsp.attribute description="Display for the column. The valid values are
     * display, currency, rowCount, and date. The default value is
     * display. The cell can also be a fully qualified class name
     * to a custom Cell. Be sure to implement the Cell interface
     * or extend AbstractCell if making a custom cell."
     * required="false" rtexprvalue="true"
     */
    public void setCell(String cell) {
        this.cell = cell;
    }


    /**
     * @jsp.attribute description="Specify whether auto format of value will be
     * skipped.  False by default, and is only effective if
     * autoformatting is implement in the view."
     * required="false" rtexprvalue="true"
     */
    public void setEscapeAutoFormat(String escapeAutoFormat) {
        this.escapeAutoFormat = escapeAutoFormat;
    }

    /**
     * @jsp.attribute description="Specify whether or not the column should be
     * filterable. Acceptable values are true or false. The default
     * is to use the value for the table filterable attribute."
     * required="false" rtexprvalue="true"
     */
    public void setFilterable(String filterable) {
        this.filterable = filterable;
    }

    /**
     * @jsp.attribute description="Displays the filter column. The valid values
     * are filter and droplist. The default is filter. The cell
     * can also be a fully qualified class name to a custom
     * cell." required="false" rtexprvalue="true"
     */
    public void setFilterCell(String filterCell) {
        this.filterCell = filterCell;
    }

    /**
     * @jsp.attribute description="The css class style sheet used to define what
     * the table filter column looks like." required="false"
     * rtexprvalue="true"
     */
    public void setFilterClass(String filterClass) {
        this.filterClass = filterClass;
    }

    /**
     * @jsp.attribute description="The object that contains the collection of elements
     * that implement the Option interface."
     * required="false" rtexprvalue="true"
     */
    public void setFilterOptions(Object filterOptions) {
        this.filterOptions = filterOptions;
    }

    /**
     * @jsp.attribute description="The css class style sheet to use for the filter column."
     * required="false" rtexprvalue="true"
     */
    public void setFilterStyle(String filterStyle) {
        this.filterStyle = filterStyle;
    }

    /**
     * @jsp.attribute description="The format to use for the cell. For
     * instance if used with a date cell then the format can be
     * MM/dd/yyyy." required="false" rtexprvalue="true"
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @jsp.attribute description="Display for the header column. The default is
     * header. The cell can also be a fully qualified class name
     * to a custom cell." required="false" rtexprvalue="true"
     */
    public void setHeaderCell(String headerCell) {
        this.headerCell = headerCell;
    }

    /**
     * @jsp.attribute description="The css class style sheet used to define what
     * the table header column looks like." required="false"
     * rtexprvalue="true"
     */
    public void setHeaderClass(String headerClass) {
        this.headerClass = headerClass;
    }

    /**
     * @jsp.attribute description="The css class style sheet to use for the header column."
     * required="false" rtexprvalue="true"
     */
    public void setHeaderStyle(String headerStyle) {
        this.headerStyle = headerStyle;
    }

    /**
     * @jsp.attribute description="A fully qualified class name to a custom
     * InterceptColumn implementation. Could also be a named type
     * in the preferences. Used to add or modify column attributes."
     * required="false" rtexprvalue="true"
     */
    public void setInterceptor(String interceptor) {
        this.interceptor = interceptor;
    }

    /**
     * @jsp.attribute description="Used if the format needs to be interpreted.
     * For instance, a date needs to be parsed in the specific
     * format, such as MM-dd-yyyy."
     * required="false" rtexprvalue="true"
     */
    public void setParse(String parse) {
        this.parse = parse;
    }

    /**
     * @jsp.attribute description="The bean attribute to use for the column."
     * required="false" rtexprvalue="true"
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * @jsp.attribute description="Specify whether or not the column should be
     * sortable. The acceptable values are true or false. The default
     * is to use the value for the table sortable attribute."
     * required="false" rtexprvalue="true"
     */
    public void setSortable(String sortable) {
        this.sortable = sortable;
    }

    /**
     * @jsp.attribute description="The css inline style sheet." required="false"
     * rtexprvalue="true"
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * @jsp.attribute description="The css class style sheet." required="false"
     * rtexprvalue="true"
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * @jsp.attribute description="The display for the table
     * column header. If the title is not specified then it will
     * default to the name of the property, changing the
     * camelcase syntax to separate words." required="false"
     * rtexprvalue="true"
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @jsp.attribute description="The value for the column. If the value
     * attribute is not specifed then it will be retrieved
     * automatically using the property attribute. The value
     * can also be defined within the column body."
     * required="false" rtexprvalue="true"
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * @jsp.attribute description="The comma separated list of views that this
     * column will be used in." required="false"
     * rtexprvalue="true"
     */
    public void setViewsAllowed(String viewsAllowed) {
        this.viewsAllowed = viewsAllowed;
    }

    /**
     * @jsp.attribute description="The comma separated list of views that this
     * column will not be used in." required="false"
     * rtexprvalue="true"
     */
    public void setViewsDenied(String viewsDenied) {
        this.viewsDenied = viewsDenied;
    }

    /**
     * @jsp.attribute description="Specify the column width." required="false"
     * rtexprvalue="true"
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * Get the value for the column. First look to see if it displayed in the
     * body of the column. If it is not in the body, then use the value
     * attribute. If the value attribute is not specified then use the property
     * attribute to find the value in the bean. Note: Weblogic will always
     * return a bodyContent so do additional checking.
     */
    protected Object getColumnValue(Object propertyValue) throws JspException {
        Object result = value;

        if (result == null && bodyContent != null) {
            result = getBodyContent().getString();
        }

        if (result != null) {
            result = TagUtils.evaluateExpressionAsObject("result", result, pageContext);
        }

        if (result == null || (result != null && result instanceof String && StringUtils.isBlank(result.toString()))) {
            result = propertyValue;
        }

        return result;
    }

    public int doStartTag() throws JspException {
        try {
            TableModel model = TagUtils.getModel(this);

            ColumnTag parentTag = TagUtils.checkColumnParent(this);
            boolean hasParent = parentTag != null;
            Column parent = null;
            if (hasParent) {
                parent = model.getColumnHandler().getColumnByAlias(TableModelUtils.getAlias(parentTag.getAlias(), parentTag.getProperty()));
            }

            if (!TagUtils.isIteratingBody(this)) {

                Column column = new Column(model);
                column.setAlias(TagUtils.evaluateExpressionAsString("alias", this.alias, pageContext));
                column.setCalc(TagUtils.evaluateExpressionAsString("calc", this.calc, pageContext));
                column.setCalcTitle(TagUtils.evaluateExpressionAsString("calcTitle", calcTitle, pageContext));
                column.setCell(TagUtils.evaluateExpressionAsString("cell", this.cell, pageContext));
                column.setEscapeAutoFormat(TagUtils.evaluateExpressionAsBoolean("escapeAutoFormat", this.escapeAutoFormat, pageContext));
                column.setFilterable(TagUtils.evaluateExpressionAsBoolean("filterable", this.filterable, pageContext));
                column.setFilterCell(TagUtils.evaluateExpressionAsString("filterCell", this.filterCell, pageContext));
                column.setFilterClass(TagUtils.evaluateExpressionAsString("filterClass", this.filterClass, pageContext));
                column.setFilterOptions(TagUtils.evaluateExpressionAsObject("filterOptions", this.filterOptions, pageContext));
                column.setFilterStyle(TagUtils.evaluateExpressionAsString("filterStyle", this.filterStyle, pageContext));
                column.setFormat(TagUtils.evaluateExpressionAsString("format", this.format, pageContext));
                column.setHeaderCell(TagUtils.evaluateExpressionAsString("headerCell", this.headerCell, pageContext));
                column.setHeaderClass(TagUtils.evaluateExpressionAsString("headerClass", this.headerClass, pageContext));
                column.setHeaderStyle(TagUtils.evaluateExpressionAsString("headerStyle", this.headerStyle, pageContext));
                column.setInterceptor(TagUtils.evaluateExpressionAsString("interceptor", this.interceptor, pageContext));
                column.setParse(TagUtils.evaluateExpressionAsString("parse", this.parse, pageContext));
                column.setProperty(TagUtils.evaluateExpressionAsString("property", this.property, pageContext));
                column.setSortable(TagUtils.evaluateExpressionAsBoolean("sortable", this.sortable, pageContext));
                column.setStyle(TagUtils.evaluateExpressionAsString("style", this.style, pageContext));
                column.setStyleClass(TagUtils.evaluateExpressionAsString("styleClass", this.styleClass, pageContext));
                column.setTitle(TagUtils.evaluateExpressionAsString("title", this.title, pageContext));
                column.setViewsAllowed(TagUtils.evaluateExpressionAsString("viewsToAllow", this.viewsAllowed, pageContext));
                column.setViewsDenied(TagUtils.evaluateExpressionAsString("viewsToDeny", this.viewsDenied, pageContext));
                column.setWidth(TagUtils.evaluateExpressionAsString("width", this.width, pageContext));

                if (hasParent) column.setParent(parent);

                addColumnAttributes(model, column);
                model.getColumnHandler().addColumn(column);
            }

        } catch (Exception e) {
            throw new JspException("ColumnTag.doEndTag() Problem: " + ExceptionUtils.formatStackTrace(e));
        }

        return EVAL_BODY_BUFFERED;
    }

    public String getAlias() {
        return alias;
    }

    public String getProperty() {
        return property;
    }

    /**
     * Must make a copy of the column because this tag may be reused. Send the
     * copy up to the Model.
     */


    public int doEndTag() throws JspException {
        try {
            TableModel model = TagUtils.getModel(this);

            if (TagUtils.isIteratingBody(this)) {
                String alias = TagUtils.evaluateExpressionAsString("alias", this.alias, pageContext);
                String property = TagUtils.evaluateExpressionAsString("property", this.property, pageContext);

                Column column = model.getColumnHandler().getColumnByAlias(TableModelUtils.getAlias(alias, property));
                if (column != null) { // null if view not allowed
                    if (column.getChildren() == null) {
                        Object bean = TagUtils.getModel(this).getCurrentRowBean();
                        Object propertyValue = TableModelUtils.getColumnPropertyValue(bean, property);
                        column.setValue(getColumnValue(propertyValue));
                        column.setPropertyValue(propertyValue);

                        modifyColumnAttributes(model, column);
                        model.getColumnHandler().modifyColumnAttributes(column);
                        model.getViewHandler().addColumnValueToView(column);
                    }
                }
            }

        } catch (Exception e) {
            throw new JspException("ColumnTag.doEndTag() Problem: " + ExceptionUtils.formatStackTrace(e));
        }
        return EVAL_PAGE;
    }

    public void addColumnAttributes(TableModel model, Column column) {
    }

    public void modifyColumnAttributes(TableModel model, Column column) {
    }

    public void release() {
        alias = null;
        calc = null;
        calcTitle = null;
        cell = null;
        escapeAutoFormat = null;
        filterable = null;
        filterCell = null;
        filterClass = null;
        filterStyle = null;
        format = null;
        headerCell = null;
        headerClass = null;
        headerStyle = null;
        interceptor = null;
        parse = null;
        property = null;
        sortable = null;
        style = null;
        styleClass = null;
        title = null;
        value = null;
        viewsAllowed = null;
        viewsDenied = null;
        width = null;
        super.release();
    }

}