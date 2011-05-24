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
package org.extremecomponents.table.view;

import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.bean.Export;
import org.extremecomponents.table.core.TableModel;

/**
 * For producing a delimited datafile view of the table. Default delimiter is
 * comma.
 * 
 * @author Brad Parks
 */
public class CsvView implements View {
    public final static String DELIMITER = "delimiter";
    final static String DEFAULT_DELIMITER = ",";
    private StringBuffer plainData = new StringBuffer();

    public void beforeBody(TableModel model) {
    }

    public void body(TableModel model, Column column) {
        Export export = model.getExportHandler().getCurrentExport();
        String delimiter = export.getAttributeAsString(DELIMITER);
        if (StringUtils.isBlank(delimiter)) {
            delimiter = DEFAULT_DELIMITER;
        }

        String value = ExportViewUtils.parseCSV(column.getCellDisplay());

        plainData.append(value.trim());
        plainData.append(delimiter);
        if (column.isLastColumn()) {
            plainData.append("\r\n");
        }
    }

    public Object afterBody(TableModel model) {
        return plainData.toString();
    }
}
