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

import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.util.HtmlBuilder;

/**
 * @author Jeff Johnston
 */
public class HtmlView extends AbstractHtmlView {
    protected void beforeBodyInternal(TableModel model) {
        toolbar(getHtmlBuilder(), getTableModel());
        
        getTableBuilder().tableStart();

        getTableBuilder().theadStart();
        
        statusBar(getHtmlBuilder(), getTableModel());
        
        getTableBuilder().filterRow();

        getTableBuilder().headerRow();

        getTableBuilder().theadEnd();

        getTableBuilder().tbodyStart();
    }

    protected void afterBodyInternal(TableModel model) {
        getCalcBuilder().defaultCalcLayout();

        getTableBuilder().tbodyEnd();

        getTableBuilder().tableEnd();
    }
    
    protected void toolbar(HtmlBuilder html, TableModel model) {
        new DefaultToolbar(html, model).layout();
    }

    protected void statusBar(HtmlBuilder html, TableModel model) {
        new DefaultStatusBar(html, model).layout();
    }
}
