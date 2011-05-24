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

import org.extremecomponents.table.bean.Export;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.html.BuilderUtils;
import org.extremecomponents.table.view.html.TableActions;
import org.extremecomponents.util.HtmlBuilder;

/**
 * @author Jeff Johnston
 */
public final class ToolbarItemUtils {
    public static void buildFirstPage(HtmlBuilder html, TableModel model, ToolbarItem item) {
        String action = new TableActions(model).getPageAction(1);
        item.setAction(action);
        int page = model.getLimit().getPage();
        if (!BuilderUtils.isFirstPageEnabled(page)) {
            item.disabled(html);
        } else {
            item.enabled(html, model);
        }
    }

    public static void buildPrevPage(HtmlBuilder html, TableModel model, ToolbarItem item) {
        int page = model.getLimit().getPage();
        String action = new TableActions(model).getPageAction(page - 1);
        item.setAction(action);

        if (!BuilderUtils.isPrevPageEnabled(page)) {
            item.disabled(html);
        } else {
            item.enabled(html, model);
        }
    }

    public static void buildNextPage(HtmlBuilder html, TableModel model, ToolbarItem item) {
        int page = model.getLimit().getPage();
        String action = new TableActions(model).getPageAction(page + 1);
        item.setAction(action);

        int totalPages = BuilderUtils.getTotalPages(model);
        if (!BuilderUtils.isNextPageEnabled(page, totalPages)) {
            item.disabled(html);
        } else {
            item.enabled(html, model);
        }
    }

    public static void buildLastPage(HtmlBuilder html, TableModel model, ToolbarItem item) {
        int totalPages = BuilderUtils.getTotalPages(model);
        String action = new TableActions(model).getPageAction(totalPages);
        item.setAction(action);

        int page = model.getLimit().getPage();
        if (!BuilderUtils.isLastPageEnabled(page, totalPages)) {
            item.disabled(html);
        } else {
            item.enabled(html, model);
        }
    }

    public static void buildFilter(HtmlBuilder html, TableModel model, ToolbarItem item) {
        item.setAction(new TableActions(model).getFilterAction());
        item.enabled(html, model);
    }

    public static void buildClear(HtmlBuilder html, TableModel model, ToolbarItem item) {
        item.setAction(new TableActions(model).getClearAction());
        item.enabled(html, model);
    }

    public static void buildExport(HtmlBuilder html, TableModel model, ToolbarItem item, Export export) {
        String action = new TableActions(model).getExportAction(export.getView(), export.getFileName());
        item.setAction(action);
        item.enabled(html, model);
    }
}
