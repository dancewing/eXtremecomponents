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
package org.extremecomponents.table.view.html;

import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.util.HtmlBuilder;

/**
 * @author Jeff Johnston
 */
public class StatusBarBuilder {
    private HtmlBuilder html;
    private TableModel model;

    public StatusBarBuilder(TableModel model) {
        this(new HtmlBuilder(), model);
    }
    
    public StatusBarBuilder(HtmlBuilder html, TableModel model) {
        this.html = html;
        this.model = model;
    }

    public HtmlBuilder getHtmlBuilder() {
        return html;
    }

    protected TableModel getTableModel() {
        return model;
    }

    public void statusMessage() {
        if (model.getLimit().getTotalRows() == 0) {
            html.append(model.getMessages().getMessage(BuilderConstants.STATUSBAR_NO_RESULTS_FOUND));
        } else {
            Integer total = new Integer(model.getLimit().getTotalRows());
            Integer from = new Integer(model.getLimit().getRowStart() + 1);
            Integer to = new Integer(model.getLimit().getRowEnd());
            Object[] messageArguments = { total, from, to };
            html.append(model.getMessages().getMessage(BuilderConstants.STATUSBAR_RESULTS_FOUND, messageArguments));
        }
    }
    
    public String toString() {
        return html.toString();
    }
}
