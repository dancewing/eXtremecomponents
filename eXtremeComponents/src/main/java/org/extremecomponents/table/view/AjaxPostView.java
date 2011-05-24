package org.extremecomponents.table.view;

import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.util.HtmlBuilder;

/**
 * Created by jeff
 */
public class AjaxPostView extends CompactView {
    @Override
    protected void toolbar(HtmlBuilder html, TableModel model) {
        new AjaxPostToolbar(html,model).layout();
    }
}
