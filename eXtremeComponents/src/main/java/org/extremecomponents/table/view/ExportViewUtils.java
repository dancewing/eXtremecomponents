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

/**
 * org.extremecomponents.base.ViewUtils - Utility methods for parsing and
 * replacing characters and Strings.
 * 
 * @author phorn
 */
public class ExportViewUtils {
    public static String parseXLS(String value) {
        if (StringUtils.isBlank(value))
            return "";

        value = replaceNonBreakingSpaces(value);

        return value;
    }

    public static String parsePDF(String value) {
        if (StringUtils.isBlank(value))
            return "";

        value = replaceNonBreakingSpaces(value);
        value = escapeChars(value);

        return value;
    }

    public static String parseCSV(String value) {
        if (StringUtils.isBlank(value))
            return "";

        value = replaceNonBreakingSpaces(value);

        return value;
    }

    public static String replaceNonBreakingSpaces(String value) {
        if (StringUtils.isBlank(value))
            return "";

        if (StringUtils.contains(value, "&nbsp;")) {
            value = StringUtils.replace(value, "&nbsp;", "");
        }

        return value;
    }

    public static String escapeChars(String value) {
        if (StringUtils.isBlank(value))
            return "";

        if (StringUtils.contains(value, "&")) {
            value = StringUtils.replace(value, "&", "&#38;");
        }

        if (StringUtils.contains(value, ">")) {
            value = StringUtils.replace(value, ">", "&gt;");
        }

        if (StringUtils.contains(value, "<")) {
            value = StringUtils.replace(value, "<", "&lt;");
        }

        return value;
    }
}
