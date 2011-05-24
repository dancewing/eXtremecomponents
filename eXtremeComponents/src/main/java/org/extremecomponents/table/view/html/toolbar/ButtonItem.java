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
package org.extremecomponents.table.view.html.toolbar;

import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.util.HtmlBuilder;

/**
 * @author Jeff Johnston
 */
public class ButtonItem extends AbstractItem implements ToolbarItem {
    private String contents;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void disabled(HtmlBuilder html) {
        html.button().disabled().close().append(getContents()).buttonEnd();
    }

    public void enabled(HtmlBuilder html, TableModel model) {
        boolean showTooltips = model.getTableHandler().getTable().isShowTooltips();
        if (showTooltips) {
            html.button();
            html.title(getTooltip());
            html.onclick(getAction());
            html.styleClass(getStyleClass()).style(getStyle());
            html.onmouseover(getOnmouseover()).onmouseout(getOnmouseout());
            html.close();
            html.append(contents);
            html.buttonEnd();
        } else {
            html.button();
            html.onclick(getAction());
            html.styleClass(getStyleClass()).style(getStyle());
            html.onmouseover(getOnmouseover()).onmouseout(getOnmouseout());
            html.close();
            html.append(contents);
            html.buttonEnd();
        }
    }
}
