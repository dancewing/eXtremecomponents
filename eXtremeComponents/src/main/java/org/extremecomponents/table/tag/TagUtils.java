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
package org.extremecomponents.table.tag;

import java.util.Collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.util.ExceptionUtils;
import org.extremecomponents.util.NumberUtils;

/**
 * @author Jeff Johnston
 */
public final class TagUtils {
    private static Log logger = LogFactory.getLog(ColumnTag.class);

    private TagUtils() {
    }

    public  static String evaluateExpressionAsString(String attributeName, String attribute, PageContext pageContext) {

            if (attribute != null) {
                //return attribute;
                try {
                    return ExpressionEvaluationUtils.evaluateString(attributeName,attribute ,pageContext);
                } catch (JspException e) {
                    logger.error("Could not resolve EL for [" + attributeName + "] - " + ExceptionUtils.formatStackTrace(e));
                }
            }

        return null;
    }

    public  static Object evaluateExpressionAsObject(String attributeName, Object attribute, PageContext pageContext) {
        try {
            if (attribute != null && attribute instanceof String) {
                return ExpressionEvaluationUtils.evaluate(attributeName, (String)attribute ,pageContext);
            }
        } catch (JspException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("The attribute [" + attributeName  + "] is null or not a Collection.");
            }
        }

        return attribute;
    }


    public  static Boolean evaluateExpressionAsBoolean(String attributeName, String attribute, PageContext pageContext) {
        Boolean evaluated = false;
        try {
            evaluated = ExpressionEvaluationUtils.evaluateBoolean(attributeName, attribute, pageContext);
        } catch (JspException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("The attribute [" + attributeName  + "] is null or not a Boolean.");
            }
        }

        return evaluated;

    }

    public static int evaluateExpressionAsInt(String attributeName, String attribute, PageContext pageContext) {

        Integer ret = null;
        try {
            ret = ExpressionEvaluationUtils.evaluateInteger(attributeName, attribute, pageContext);
        } catch (JspException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("The attribute [" + attributeName  + "] is Integer value.");
            }
            e.printStackTrace();
        }
        return ret==null ? 0 : ret;
    }

    public static TableModel getModel(Tag tag) {
        TableTag tableTag = (TableTag) TagSupport.findAncestorWithClass(tag, TableTag.class);
        return tableTag.getModel();
    }

    public static ColumnTag checkColumnParent(Tag tag) {
        return (ColumnTag) TagSupport.findAncestorWithClass(tag, ColumnTag.class);
    }


    public static boolean isIteratingBody(Tag tag) {
        return getModel(tag).getCurrentRowBean() != null;
    }
}
