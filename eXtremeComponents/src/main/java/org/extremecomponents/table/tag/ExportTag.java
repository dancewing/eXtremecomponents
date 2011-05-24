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
import javax.servlet.jsp.tagext.TagSupport;

import org.extremecomponents.table.bean.Export;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.interceptor.ExportInterceptor;
import org.extremecomponents.util.ExceptionUtils;

/**
 * @jsp.tag name="export" display-name="ExportTag" body-content="JSP"
 *          description="Export data to a given view. For example pdf or xls."
 * 
 * @author Jeff Johnston
 */
public class ExportTag extends TagSupport implements ExportInterceptor {
    private String encoding;
    private String fileName;
    private String imageName;
    private String interceptor;
    private String text;
    private String tooltip;
    private String view;
    private String viewResolver;

    /**
     * @jsp.attribute description="The encoding that set is support UTF-8." 
     *                required="false" rtexprvalue="true"
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * @jsp.attribute description="The name of the export file." 
     *                required="true" rtexprvalue="true"
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @jsp.attribute description="The image name." 
     *                required="false" rtexprvalue="true"
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * @jsp.attribute description="A fully qualified class name to a custom
     *                InterceptExport implementation. Could also be a named type
     *                in the preferences. Used to add or modify export attributes." 
     *                required="false" rtexprvalue="true"
     */
    public void setInterceptor(String interceptor) {
        this.interceptor = interceptor;
    }

    /**
     * @jsp.attribute description="A fully qualified class name to a custom
     *                View implementation. Could also be a named type
     *                in the preferences. Default types are pdf, xls, or csv." 
     *                required="false" rtexprvalue="true"
     */
    public void setView(String view) {
        this.view = view;
    }

    /**
     * @jsp.attribute description="A fully qualified class name to a custom
     *                ViewResolver implementation. Could also be a named type
     *                in the preferences. Default types are pdf, xls, or csv." 
     *                required="false" rtexprvalue="true"
     */
    public void setViewResolver(String viewResolver) {
        this.viewResolver = viewResolver;
    }
    
    /**
     * @jsp.attribute description="The text for the export view." 
     *                required="false" rtexprvalue="true"
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @jsp.attribute description="The tooltip that shows up when you mouseover
     *                the export image." required="false" rtexprvalue="true"
     */
    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public int doEndTag() throws JspException {
        if (TagUtils.isIteratingBody(this)) {
            return EVAL_PAGE;
        }
        
        try {
            TableModel model = TagUtils.getModel(this);

            Export export = new Export(model);
            export.setEncoding(TagUtils.evaluateExpressionAsString("encoding", this.encoding, pageContext));
            export.setFileName(TagUtils.evaluateExpressionAsString("fileName", this.fileName, pageContext));
            export.setImageName(TagUtils.evaluateExpressionAsString("imageName", this.imageName, pageContext));
            export.setInterceptor(TagUtils.evaluateExpressionAsString("interceptor", this.interceptor, pageContext));
            export.setText(TagUtils.evaluateExpressionAsString("text", this.text, pageContext));
            export.setTooltip(TagUtils.evaluateExpressionAsString("tooltip", this.tooltip, pageContext));
            export.setView(TagUtils.evaluateExpressionAsString("view", view, pageContext));
            export.setViewResolver(TagUtils.evaluateExpressionAsString("viewResolver", this.viewResolver, pageContext));

            addExportAttributes(model, export);
            model.addExport(export);
        } catch (Exception e) {
            throw new JspException("ExportTag.doEndTag() Problem: " + ExceptionUtils.formatStackTrace(e));
        }

        return EVAL_PAGE;
    }

    public void addExportAttributes(TableModel model, Export export) {
    }

    public void release() {
        encoding = null;
        fileName = null;
        imageName = null;
        interceptor = null;
        view = null;
        viewResolver = null;
        text = null;
        tooltip = null;
        super.release();
    }
}
