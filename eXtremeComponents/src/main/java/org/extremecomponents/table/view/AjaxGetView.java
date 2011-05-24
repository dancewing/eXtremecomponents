package org.extremecomponents.table.view;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.ajax.JQueryBuilder;
import org.extremecomponents.util.HtmlBuilder;

/**
 * Created by jeff
 */
public class AjaxGetView implements View {
    private JQueryBuilder jQueryBuilder;
    private TableModel model;
    private boolean bufferView;

    @Override
    public void beforeBody(TableModel model) {
        this.model = model;

        bufferView = model.getTableHandler().getTable().isBufferView();
        HtmlBuilder html;
        if (bufferView) {
            html = new HtmlBuilder();
        } else {
            html = new HtmlBuilder(model.getContext().getWriter());
        }
        jQueryBuilder = new JQueryBuilder(html,model);
        jQueryBuilder.start();
    }

    @Override
    public void body(TableModel model, Column column) {
    }

    @Override
    public Object afterBody(TableModel model) {
        jQueryBuilder.end();

        if (bufferView) {
            return jQueryBuilder.toString();
        }

        return "";
    }
}
