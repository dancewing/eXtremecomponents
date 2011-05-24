package org.extremecomponents.table.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.util.ExceptionUtils;

/**
 * @jsp.tag name="columns" display-name="ColumnsTag" body-content="JSP"
 *          description="Auto generate the columns."
 * 
 * @author Jeff Johnston
 */

public class ColumnsTag extends TagSupport {
    private static Log logger = LogFactory.getLog(ColumnsTag.class);

    private String autoGenerateColumns;

    /**
     * @jsp.attribute description="A fully qualified class name to a custom
     *                AutoGenerateColumns implementation. Could also be a named
     *                type in the preferences. Used to generate columns on the
     *                fly." required="true" rtexprvalue="true"
     */
    public void setAutoGenerateColumns(String autoGenerateColumns) {
        this.autoGenerateColumns = autoGenerateColumns;
    }

    public int doEndTag() throws JspException {
        try {
            TableModel model = TagUtils.getModel(this);

            if (!TagUtils.isIteratingBody(this)) {
                String autoGenerateColumns = TagUtils.evaluateExpressionAsString("autoGenerateColumns", this.autoGenerateColumns, pageContext);
                model.addColumns(autoGenerateColumns);
            } else {
                model.setColumnValues();
            }

            return EVAL_PAGE;
        } catch (Exception e) {
            logger.error(ExceptionUtils.formatStackTrace(e));
            throw new JspException("ColumnsTag.doEndTag() Problem: " + ExceptionUtils.formatStackTrace(e));
        }
    }

    public void release() {
        autoGenerateColumns = null;
        super.release();
    }
}
