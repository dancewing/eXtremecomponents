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
package org.extremecomponents.test;

import org.extremecomponents.table.core.TableConstants;

public class ExtremeTableTestHelper {
    private TableParameters parameters;
    private String prefixWithTableId;
    
    public ExtremeTableTestHelper(TableParameters parameters) {
        this(parameters, TableConstants.EXTREME_COMPONENTS);
    }
    
    public ExtremeTableTestHelper(TableParameters parameters, String tableId) {
        this.parameters = parameters;
        this.prefixWithTableId = tableId + "_";
        parameters.addParameter(TableConstants.EXPORT_TABLE_ID , tableId);
    }
    
    public void addFilter(String property, String value) {
        parameters.addParameter(getFilter(property), value);
    }
    
    public void addPage(int page) {
        parameters.addParameter(prefixWithTableId + TableConstants.PAGE, String.valueOf(page));
    }

    public void addSortAsc(String property) {
        parameters.addParameter(prefixWithTableId + TableConstants.SORT + property, TableConstants.SORT_ASC);
    }
    
    public void addSortDesc(String property) {
        parameters.addParameter(prefixWithTableId + TableConstants.SORT + property, TableConstants.SORT_ASC);
    }

    public void addSortDefault(String property) {
        parameters.addParameter(prefixWithTableId + TableConstants.SORT + property, TableConstants.SORT_DEFAULT);
    }
    
    public void doFilter() {
        parameters.addParameter(prefixWithTableId + TableConstants.FILTER + TableConstants.ACTION, TableConstants.FILTER_ACTION);
    }
    
    public void doClear() {
        parameters.addParameter(prefixWithTableId + TableConstants.FILTER + TableConstants.ACTION, TableConstants.CLEAR_ACTION);
    }
    
    public void addExportView(String view) {
        parameters.addParameter(prefixWithTableId + TableConstants.EXPORT_VIEW, view);
    }
    
    public void addExportFileName(String fileName) {
        parameters.addParameter(prefixWithTableId + TableConstants.EXPORT_FILE_NAME, fileName);
    }
    
    public String getFilter(String property) {
        return prefixWithTableId + TableConstants.FILTER + property;
    }
}
