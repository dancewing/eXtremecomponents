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
package org.extremecomponents.util;

import java.util.*;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.limit.Filter;

/**
 * Use the Jakarta Collections predicate pattern to filter out the table.
 * 
 * @author Jeff Johnston
 */
public final class FilterPredicate implements Predicate {
    private static Log logger = LogFactory.getLog(FilterPredicate.class);

    private List<Filter> filters;

    public FilterPredicate(Filter filters[]) {
        Assert.notNull(filters);
        this.filters = Arrays.asList(filters);
    }

    public FilterPredicate(List<Filter> filters) {
        Assert.notNull(filters);
        this.filters = filters;
    }

    /**
     * Use the filter parameters to filter out the table.
     */
    public boolean evaluate(Object bean) {
        boolean match = false;

        try {
            for (Iterator<Filter> iter = filters.iterator(); iter.hasNext();) {
                Filter filter = iter.next();

                String filterValue = filter.getValue();

                if (StringUtils.isEmpty(filterValue)) {
                    continue;
                }

                String property = filter.getProperty();
                Object value = PropertyUtils.getProperty(bean, property);

                if (value == null) {
                    continue;
                }

                if (!isSearchMatch(value.toString(), filterValue)) {
                    match = false; // as soon as fail just short circuit

                    break;
                }

                match = true;
            }
        } catch (Exception e) {
            logger.error("FilterPredicate.evaluate() had problems", e);
        }

        return match;
    }

    private boolean isSearchMatch(String value, String search) {
        value = value.toLowerCase().trim();
        search = search.toLowerCase().trim();

        if (search.startsWith("*") && value.endsWith(StringUtils.replace(search, "*", ""))) {
            return true;
        } else if (search.endsWith("*") && value.startsWith(StringUtils.replace(search, "*", ""))) {
            return true;
        } else if (StringUtils.contains(value, search)) {
            return true;
        }

        return false;
    }
}
