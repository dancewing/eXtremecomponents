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
package org.extremecomponents.table.core;

import java.util.Collection;
import java.util.Locale;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.bean.Export;
import org.extremecomponents.table.bean.Row;
import org.extremecomponents.table.bean.Table;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.handler.ColumnHandler;
import org.extremecomponents.table.handler.ExportHandler;
import org.extremecomponents.table.handler.RowHandler;
import org.extremecomponents.table.handler.TableHandler;
import org.extremecomponents.table.handler.ViewHandler;
import org.extremecomponents.table.limit.Limit;

/**
 * @author Jeff Johnston
 */
public interface TableModel {
    public Context getContext();

    public Preferences getPreferences();

    public Messages getMessages();

    public Registry getRegistry();

    public void addTable(Table table);

    public void addExport(Export export);

    public void addRow(Row row);

    public void addColumns(String autoGenerateColumns);

    public void addParameter(String name, Object value);

    public TableHandler getTableHandler();

    public RowHandler getRowHandler();

    public ColumnHandler getColumnHandler();

    public ViewHandler getViewHandler();

    public ExportHandler getExportHandler();

    public Object getCurrentRowBean();

    public void setCurrentRowBean(Object bean);

    public Collection getCollectionOfBeans();

    public Limit getLimit();

    public Locale getLocale();

    public Collection execute() throws Exception;

    public void setColumnValues() throws Exception;

    public Object getViewData() throws Exception;

    public Object assemble() throws Exception;

}