package org.extremecomponents.table.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.extremecomponents.table.bean.Row;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.interceptor.RowInterceptor;
import org.extremecomponents.util.ExceptionUtils;

/**
 * @jsp.tag name="row" display-name="RowTag" body-content="JSP" description="The
 *          container which holds all the row specific information."
 * 
 * @author Jeff Johnston
 */
public class RowTag extends TagSupport implements RowInterceptor {

    private String highlightClass;
    private String highlightRow;
    private String interceptor;
    private String onclick;
    private String onmouseout;
    private String onmouseover;
    private String style;
    private String styleClass;

    /**
     * @jsp.attribute description="The css class style sheet when highlighting rows." required="false"
     *                rtexprvalue="true"
     */
    public void setHighlightClass(String highlightClass) {
        this.highlightClass = highlightClass;
    }

    /**
     * @jsp.attribute description="Used to turn the highlight feature on and
     *                off. Acceptable values are true or false. The default is false." 
     *                required="false" rtexprvalue="true"
     */
    public void setHighlightRow(String showHighlight) {
        this.highlightRow = showHighlight;
    }

    /**
     * @jsp.attribute description="A fully qualified class name to a custom
     *                InterceptRow implementation. Could also be a named type
     *                in the preferences. Used to add or modify row attributes." 
     *                required="false" rtexprvalue="true"
     */
    public void setInterceptor(String interceptor) {
        this.interceptor = interceptor;
    }

    /**
     * @jsp.attribute description="The javascript onclick action"
     *                required="false" rtexprvalue="true"
     */
    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    /**
     * @jsp.attribute description="The javascript onmouseout action"
     *                required="false" rtexprvalue="true"
     */
    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    /**
     * @jsp.attribute description="The javascript onmouseover action"
     *                required="false" rtexprvalue="true"
     */
    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    /**
     * @jsp.attribute description="The css inline style sheet." required="false"
     *                rtexprvalue="true"
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * @jsp.attribute description="The css class style sheet." required="false"
     *                rtexprvalue="true"
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public int doStartTag() throws JspException {
        try {
            TableModel model = TagUtils.getModel(this);

            if (TagUtils.isIteratingBody(this)) {
                Row row = model.getRowHandler().getRow();
                row.setOnclick(TagUtils.evaluateExpressionAsString("onclick", onclick, pageContext));
                row.setOnmouseout(TagUtils.evaluateExpressionAsString("onmouseout", onmouseout, pageContext));
                row.setOnmouseover(TagUtils.evaluateExpressionAsString("onmouseover", onmouseover, pageContext));
                
                modifyRowAttributes(model, row);
                model.getRowHandler().modifyRowAttributes();
            } else {
                Row row = new Row(model);
                row.setHighlightClass(TagUtils.evaluateExpressionAsString("highlightClass", this.highlightClass, pageContext));
                row.setHighlightRow(TagUtils.evaluateExpressionAsBoolean("highlightRow", this.highlightRow, pageContext));
                row.setInterceptor(TagUtils.evaluateExpressionAsString("interceptor", this.interceptor, pageContext));
                row.setOnclick(TagUtils.evaluateExpressionAsString("onclick", onclick, pageContext));
                row.setOnmouseout(TagUtils.evaluateExpressionAsString("onmouseout", onmouseout, pageContext));
                row.setOnmouseover(TagUtils.evaluateExpressionAsString("onmouseover", onmouseover, pageContext));
                row.setStyle(TagUtils.evaluateExpressionAsString("style", style, pageContext));
                row.setStyleClass(TagUtils.evaluateExpressionAsString("styleClass", styleClass, pageContext));

                addRowAttributes(model, row);
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new JspException("RowTag.doStartTag() Problem: " + ExceptionUtils.formatStackTrace(e));
        }
        
        return EVAL_BODY_INCLUDE;
    }

    public void addRowAttributes(TableModel model, Row row) {
    }

    public void modifyRowAttributes(TableModel model, Row row) {
    }

    public void release() {
        highlightClass = null;
        highlightRow = null;
        interceptor = null;
        onclick = null;
        onmouseout = null;
        onmouseover = null;
        style = null;
        styleClass = null;
        super.release();
    }
}
