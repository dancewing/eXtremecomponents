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
package org.extremecomponents.table.filter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.core.TableConstants;

/**
 * @author Jeff Johnston
 */
public final class ExtremeFilterUtils {
    private static Log logger = LogFactory.getLog(ExtremeFilterUtils.class);

    private ExtremeFilterUtils() {
    }

    public static boolean isExported(Context context) {
        return StringUtils.isNotBlank(getTableId(context));
    }

    public static String getExportFileName(Context context) {
        String tableId = getTableId(context);

        if (StringUtils.isNotBlank(tableId)) {
            String exportFileNameStr = tableId + "_" + TableConstants.EXPORT_FILE_NAME;
            String exportFileName = context.getParameter(exportFileNameStr);

            if (logger.isDebugEnabled()) {
                logger.debug("eXtremeTable export file name [" + exportFileNameStr + "] is [" + exportFileName + "]");
            }

            return exportFileName;
        }

        return null;
    }

    /**
     * There can only be one table instance (tableId) per form. If the instance
     * variable exists that means there is an export being done.
     * 
     * @param context
     * @return
     */
    public static String getTableId(Context context) {
        return context.getParameter(TableConstants.EXPORT_TABLE_ID);
    }


}
