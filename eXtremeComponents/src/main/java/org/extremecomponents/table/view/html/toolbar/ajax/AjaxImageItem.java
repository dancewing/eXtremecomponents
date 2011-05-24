package org.extremecomponents.table.view.html.toolbar.ajax;

import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.html.toolbar.ImageItem;
import org.extremecomponents.util.HtmlBuilder;

/**
 * Created by jeff
 */
public class AjaxImageItem extends ImageItem {
    @Override
    public void enabled(HtmlBuilder html, TableModel model) {
        boolean showTooltips = model.getTableHandler().getTable().isShowTooltips();
        if (showTooltips) {
            html.img().id(getId()).src(getImage()).style(getStyle()).title(getTooltip()).onmouseover(getOnmouseover()).onmouseout(getOnmouseout()).alt(getAlt()).xclose();
        } else {
            html.img().id(getId()).src(getImage()).style(getStyle()).onmouseover(getOnmouseover()).onmouseout(getOnmouseout()).alt(getAlt()).xclose();
        }
    }
}
