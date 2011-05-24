package org.extremecomponents.table.tag;

import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.bean.Export;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.interceptor.ExportInterceptor;
import org.extremecomponents.util.ExceptionUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by jeff
 */
public class ExportAjaxTag extends TagSupport implements ExportInterceptor {

    private String encoding;

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public int doEndTag() throws JspException {
        if (TagUtils.isIteratingBody(this)) {
            return EVAL_PAGE;
        }

        try {
            TableModel model = TagUtils.getModel(this);

            Export export = new Export(model);
            export.setView(TableConstants.VIEW_HTML);
            export.setViewResolver(TableConstants.VIEW_HTML);
            export.setEncoding(TagUtils.evaluateExpressionAsString("encoding", this.encoding, pageContext));

            addExportAttributes(model, export);
            model.addExport(export);
        } catch (Exception e) {
            throw new JspException("ExportTag.doEndTag() Problem: " + ExceptionUtils.formatStackTrace(e));
        }

        return EVAL_PAGE;
    }

    public void addExportAttributes(TableModel model, Export export) {
    }

    @Override
    public void release() {
        super.release();
    }
}
