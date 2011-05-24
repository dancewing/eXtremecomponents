package org.extremecomponents.table.view.ajax;

import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.util.ParamsBuilder;
import org.extremecomponents.util.HtmlBuilder;

/**
 * Created by jeff
 */
public class JQueryBuilder {
    private HtmlBuilder html;
    private TableModel model;
    private String jQueryVar;


    public JQueryBuilder(TableModel model) {
        this(new HtmlBuilder(), model);
    }

    public JQueryBuilder(HtmlBuilder html, TableModel model) {
        this.html = html;
        this.model = model;
        this.jQueryVar = model.getTableHandler().getTable().getTableId() + "_jc";
    }

    public void start() {
        html.scriptStart();
        html.scriptNewLine("$(document).ready(function() {");
        initVar();
        ajaxViewRequest();

    }

    public void end() {
        html.scriptEndLine("});");
        html.scriptEnd();
    }

    public void initVar() {
        html.scriptNewLine("var ");
        html.append(this.jQueryVar);
        html.append(" = $.fn.eXtreme(");
        ParamsBuilder pb = new ParamsBuilder(model);
        pb.getParams();
        html.append(pb.toJson());
        html.append(");");
    }

    public void ajaxViewRequest() {
        html.scriptNewLine(this.jQueryVar);
        html.append(".ajaxViewRequest();");
    }

    public String toString() {
        return html.toString();
    }

}
