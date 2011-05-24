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
import org.extremecomponents.table.view.CsvView;
import org.extremecomponents.table.view.html.BuilderConstants;

/**
 * @jsp.tag name="exportCsv" display-name="ExportCsvTag" body-content="JSP"
 *          description="Export data for a csv view."
 * 
 * @author Jeff Johnston
 */
public class ExportCsvTag extends ExportTag {
    private String delimiter;

    public String getDelimiter() {
        return TagUtils.evaluateExpressionAsString("delimiter", delimiter, pageContext);
    }

    /**
     * @jsp.attribute description="What to use as the file delimiter. The
     *                default is a comma." required="false" rtexprvalue="true"
     */
    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public void addExportAttributes(TableModel model, Export export) {
        if (StringUtils.isBlank(export.getView())) {
            export.setView(TableConstants.VIEW_CSV);
        }

        if (StringUtils.isBlank(export.getViewResolver())){
            export.setViewResolver(TableConstants.VIEW_CSV);
        }

        if (StringUtils.isBlank(export.getImageName())) {
            export.setImageName(TableConstants.VIEW_CSV);
        }
        
        if (StringUtils.isBlank(export.getText())) {
            export.setText(BuilderConstants.TOOLBAR_CSV_TEXT);
        }

        export.addAttribute(CsvView.DELIMITER, TagUtils.evaluateExpressionAsString("delimiter", delimiter , pageContext));
    }

    public void release() {
        delimiter = null;
        super.release();
    }
}
