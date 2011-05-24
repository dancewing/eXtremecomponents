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
package org.extremecomponents.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.extremecomponents.util.FilterPredicate;
import org.extremecomponents.table.callback.RetrieveRowsCallback;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.limit.Sort;

/**
 * @author phorn
 */
public final class ProcessTreeRowsCallback implements RetrieveRowsCallback {

    @Override
    public Collection retrieveRows(TableModel model ,Collection rows) throws Exception {
        model.getTableHandler().getTable().addAttribute(TreeConstants.OPEN_NODES,
                getParameters(model.getRegistry().getParameterMap(), TreeConstants.OPEN, model.getTableHandler().prefixWithTableId()));

        rows = TreeModelUtils.loadTreeStructure(model, rows);

        setFilteredCount(model, rows);

        return rows;
    }

    private Map getParameters(Map parameterMap, String parameter, String prefixWithTableId) {
        Map subset = new HashMap();

        String find = prefixWithTableId + parameter;

        Set set = parameterMap.keySet();
        for (Iterator iter = set.iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            if (name.startsWith(find)) {
                String[] values = (String[]) parameterMap.get(name);
                subset.put(name, values);
            }
        }

        return subset;
    }

    private void setFilteredCount(TableModel model, Collection rows) {
        if (rows == null) {
            model.getTableHandler().getTable().addAttribute(TreeConstants.FILTERED_COUNT, "0");
            return;
        }
        model.getTableHandler().getTable().addAttribute(TreeConstants.FILTERED_COUNT, rows.size() + "");
    }
}
