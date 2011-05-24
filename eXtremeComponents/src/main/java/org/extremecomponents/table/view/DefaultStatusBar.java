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

import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.html.BuilderConstants;
import org.extremecomponents.table.view.html.BuilderUtils;
import org.extremecomponents.table.view.html.StatusBarBuilder;
import org.extremecomponents.table.view.html.ToolbarBuilder;
import org.extremecomponents.table.view.html.TwoColumnRowLayout;
import org.extremecomponents.util.HtmlBuilder;

/**
 * @author Jeff Johnston
 */
public class DefaultStatusBar extends TwoColumnRowLayout {
    public DefaultStatusBar(HtmlBuilder html, TableModel model) {
        super(html, model);
    }

    protected boolean showLayout(TableModel model) {
        boolean showStatusBar = BuilderUtils.showStatusBar(model);
        boolean filterable = BuilderUtils.filterable(model);
        if (!showStatusBar && !filterable) {
            return false;
        }

        return true;
    }

    protected void columnLeft(HtmlBuilder html, TableModel model) {
        boolean showStatusBar = BuilderUtils.showStatusBar(model);
        if (!showStatusBar) {
            return;
        }

        html.td(4).styleClass(BuilderConstants.STATUS_BAR_CSS).close();

        new StatusBarBuilder(html, model).statusMessage();

        html.tdEnd();
    }

    protected void columnRight(HtmlBuilder html, TableModel model) {
        boolean filterable = BuilderUtils.filterable(model);
        if (!filterable) {
            return;
        }

        html.td(4).styleClass(BuilderConstants.FILTER_BUTTONS_CSS).close();

        html.img();
        html.src(BuilderUtils.getImage(model, BuilderConstants.TOOLBAR_FILTER_ARROW_IMAGE));
        html.style("border:0");
        html.alt("Arrow");
        html.xclose();

        html.nbsp();

        ToolbarBuilder toolbarBuilder = new ToolbarBuilder(html, model);
        toolbarBuilder.filterItemAsImage();

        html.nbsp();

        toolbarBuilder.clearItemAsImage();

        html.tdEnd();
    }
}
