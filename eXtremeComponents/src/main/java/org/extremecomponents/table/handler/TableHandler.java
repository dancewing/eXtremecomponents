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

import org.extremecomponents.table.bean.Table;
import org.extremecomponents.table.core.PreferencesConstants;
import org.extremecomponents.table.core.TableCache;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.core.TableModelUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * The first pass over the table just loads up the column properties. The
 * properties will be loaded up and held here.
 * 
 * @author Jeff Johnston
 */
public class TableHandler {
    protected TableModel model;
    private Table table;

    public TableHandler(TableModel model) {
        this.model = model;
    }

    public Table getTable() {
        return table;
    }

    public void addTable(Table table) {
        this.table = table;
        addTableAttributes();
        table.defaults();
    }
    
    public void addTableAttributes() {
        String interceptor = TableModelUtils.getInterceptPreference(model, table.getInterceptor(), PreferencesConstants.TABLE_INTERCEPTOR);
        table.setInterceptor(interceptor);
        TableCache.getInstance().getTableInterceptor(interceptor).addTableAttributes(model, table);
    }

    public String prefixWithTableId() {
        return table.getTableId() + "_";
    }

    /**
     * 
     * @return The totalRows that will be used for the Limit.setAttributes().
     */
    public Integer getTotalRows() {
        return table.getDataSource().getAvailableRows(Arrays.asList(model.getLimit().getFilterSet().getFilters()),model.getLimit().getSort());
    }

    public Collection getData(int rowStart, int rowEnd) {
        return table.getDataSource().getData(rowStart,rowEnd);
    }
}
