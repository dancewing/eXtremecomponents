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

import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.bean.Export;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.PdfView;
import org.extremecomponents.table.view.html.BuilderConstants;

/**
 * @jsp.tag name="exportPdf" display-name="ExportPdfTag" body-content="JSP"
 *          description="Export data for a pdf view."
 * 
 * @author Jeff Johnston
 */
public class ExportPdfTag extends ExportTag {

    private String headerBackgroundColor;
    private String headerTitle;
    private String headerColor;
    private String font;

    /**
     * @jsp.attribute description="The background color on the header column."
     *                required="false" rtexprvalue="true"
     */
    public void setHeaderBackgroundColor(String headerBackgroundColor) {
        this.headerBackgroundColor = headerBackgroundColor;
    }

    /**
     * @jsp.attribute description="The font color for the header column."
     *                required="false" rtexprvalue="true"
     */
    public void setHeaderColor(String headerColor) {
        this.headerColor = headerColor;
    }

    /**
     * @jsp.attribute description="The title displayed at the top of the page."
     *                required="false" rtexprvalue="true"
     */
    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public void addExportAttributes(TableModel model, Export export) {
        if (StringUtils.isBlank(export.getView())) {
            export.setView(TableConstants.VIEW_PDF);
        }

        if (StringUtils.isBlank(export.getViewResolver())){
            export.setViewResolver(TableConstants.VIEW_PDF);
        }

        if (StringUtils.isBlank(export.getImageName())) {
            export.setImageName(TableConstants.VIEW_PDF);
        }
        
        if (StringUtils.isBlank(export.getText())) {
            export.setText(BuilderConstants.TOOLBAR_PDF_TEXT);
        }

        export.addAttribute(PdfView.HEADER_BACKGROUND_COLOR, TagUtils.evaluateExpressionAsString("headerBackgroundColor", headerBackgroundColor, pageContext));
        export.addAttribute(PdfView.HEADER_COLOR, TagUtils.evaluateExpressionAsString("headerColor", headerColor, pageContext));
        export.addAttribute(PdfView.HEADER_TITLE, TagUtils.evaluateExpressionAsString("headerTitle", headerTitle, pageContext));
        export.addAttribute(PdfView.FONT, TagUtils.evaluateExpressionAsString("font", font, pageContext));
    }

    public void release() {
        headerBackgroundColor = null;
        headerTitle = null;
        headerColor = null;
        super.release();
    }
}
