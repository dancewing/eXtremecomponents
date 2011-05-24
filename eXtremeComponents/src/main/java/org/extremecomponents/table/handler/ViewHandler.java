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

import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.Cell;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.core.TableModelUtils;
import org.extremecomponents.table.core.PreferencesConstants;
import org.extremecomponents.table.view.View;

/**
 * @author Jeff Johnston
 */
public class ViewHandler {
    private TableModel model;
    private View view;

    public ViewHandler(TableModel model) {
        this.model = model;
    }

    public View getView() {
        return view;
    }

    public void setView() throws Exception {
        boolean isExported = model.getLimit().isExported();
        boolean isAjaxTable = model.getTableHandler().getTable().isAjaxTable();
        boolean isAjaxPost = model.getLimit().isAjaxPosted();

        String currentView = null;

        if (isExported) {
            currentView = model.getExportHandler().getCurrentExport().getView();
            String preference = model.getPreferences().getPreference(PreferencesConstants.EXPORT_VIEW + currentView);
            if (StringUtils.isNotBlank(preference)) {
                currentView = preference;
            }
        } else if (isAjaxTable) {
            if (isAjaxPost) {
                currentView = TableConstants.VIEW_AJAX_POST;
            } else {
                currentView = TableConstants.VIEW_AJAX_GET;
            }
            String preference = model.getPreferences().getPreference(PreferencesConstants.TABLE_VIEW + currentView);
            if (StringUtils.isNotBlank(preference)) {
                currentView = preference;
            }


        } else {
            currentView = model.getTableHandler().getTable().getView();
            String preference = model.getPreferences().getPreference(PreferencesConstants.TABLE_VIEW + currentView);
            if (StringUtils.isNotBlank(preference)) {
                currentView = preference;
            }
        }

        Class classDefinition = Class.forName(currentView);
        this.view = (View) classDefinition.newInstance();

    }

    public void doBeforeBody() {
        getView().beforeBody(model);
    }

    public void addColumnValueToView(Column column) {

        Cell cell = TableModelUtils.getCell(column);

        boolean isExported = model.getLimit().isExported();
        if (!isExported) {
            column.setCellDisplay(cell.getHtmlDisplay(model, column));
        } else {
            column.setCellDisplay(cell.getExportDisplay(model, column));
        }

        getView().body(model, column);
    }
}
