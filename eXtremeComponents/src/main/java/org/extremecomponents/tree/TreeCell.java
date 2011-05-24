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
package org.extremecomponents.tree;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.Cell;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.html.BuilderUtils;
import org.extremecomponents.table.view.html.CellBuilder;
import org.extremecomponents.util.HtmlBuilder;

/**
 * Will generate a simple cell to display.
 * 
 * @author Paul Horn
 */
public final class TreeCell implements Cell {
    private static Log logger = LogFactory.getLog(TreeCell.class);
    public static final String PLUS_TOP_IMAGE = "plus_top";
    public static final String PLUS_CENTER_IMAGE = "plus_center";
    public static final String PLUS_BOTTOM_IMAGE = "plus_bottom";
    public static final String MINUS_TOP_IMAGE = "minus_top";
    public static final String MINUS_CENTER_IMAGE = "minus_center";
    public static final String MINUS_BOTTOM_IMAGE = "minus_bottom";

    public String getExportDisplay(TableModel model, Column column) {
        return null;
    }

    public String getHtmlDisplay(TableModel model, Column column) {
        HtmlBuilder html = new HtmlBuilder();

        CellBuilder.tdStart(html, column);
        RowPostion postion;
        int rowCount = Integer.parseInt((String)model.getContext().getPageAttribute(TableConstants.ROWCOUNT));
        int filterCount = model.getTableHandler().getTable().getAttributeAsInt(TreeConstants.FILTERED_COUNT);
        if (rowCount==1) {
            postion = RowPostion.top;
        } else {
            if (rowCount==filterCount) {
                postion = RowPostion.bottom;
            } else {
                postion = RowPostion.center;
            }
        }

        String value = column.getValueAsString();
        if (StringUtils.isNotBlank(value)) {
            try {
                buildNodeCell(html, model, value, postion);
            } catch (Exception e) {
                logger.error("TreeCell.html() Problem", e);
            }
        } else {
            html.nbsp();
        }

        CellBuilder.tdEnd(html);

        return html.toString();
    }

    private void buildNodeCell(HtmlBuilder html, TableModel model, String value, RowPostion postion) throws Exception {
        html.table(0).cellPadding("0").cellSpacing("0").border("0").close().tr(1).close();

        TreeNode node = (TreeNode) model.getCurrentRowBean();

        for (int i = 0; i < node.getDepth(); i++) {
            html.td(2).close().nbsp().nbsp().nbsp().tdEnd();
        }

        if ((node.getChildren() != null) && (node.getChildren().size() > 0)) {
            buildLink(html, model, node, value, postion);
        } else {
            html.td(2).close();

            switch (postion) {
                case top:
                    html.img(BuilderUtils.getImage(model, MINUS_TOP_IMAGE));
                    break;
                case center:
                    html.img(BuilderUtils.getImage(model, MINUS_CENTER_IMAGE));
                    break;
                case bottom:
                    html.img(BuilderUtils.getImage(model, MINUS_BOTTOM_IMAGE));
                    break;
            }
            html.tdEnd();

            html.td(2).close().nbsp().append(value).tdEnd();
        }

        html.trEnd(1).tableEnd(0);
    }

    private void buildLink(HtmlBuilder html, TableModel model, TreeNode node, String value , RowPostion postion) throws Exception {
        html.td(2).close();

        html.a().quote();

        String action = model.getTableHandler().getTable().getAction();
        if (StringUtils.isNotEmpty(action)) {
            html.append(action);
        }

        html.append(getQueryString(node, model));

        html.quote().close();

        if (node.isOpen()) {
            switch (postion) {
                case top:
                    html.img(BuilderUtils.getImage(model, MINUS_TOP_IMAGE));
                    break;
                case center:
                    html.img(BuilderUtils.getImage(model, MINUS_CENTER_IMAGE));
                    break;
                case bottom:
                    html.img(BuilderUtils.getImage(model, MINUS_BOTTOM_IMAGE));
                    break;
            }
        } else {
            switch (postion) {
                case top:
                    html.img(BuilderUtils.getImage(model, PLUS_TOP_IMAGE));
                    break;
                case center:
                    html.img(BuilderUtils.getImage(model, PLUS_CENTER_IMAGE));
                    break;
                case bottom:
                    html.img(BuilderUtils.getImage(model, PLUS_BOTTOM_IMAGE));
                    break;
            }
        }

        html.aEnd();

        html.tdEnd().td(2).close().nbsp().append(value).tdEnd();
    }



    private String getQueryString(TreeNode node, TableModel model) throws Exception {
        HtmlBuilder html = new HtmlBuilder();

        html.append(TreeRegistryUtils.getURLParameterString(model, true, true, false, false));

        String identifier = BeanUtils.getProperty(node, model.getTableHandler().getTable().getAttributeAsString(TreeConstants.IDENTIFIER));
        String currentCellOpenKey = TreeModelUtils.getNodeKey(model, identifier);

        if (!node.isOpen()) {
            html.append("&amp;").append(currentCellOpenKey).equals().append("true");
        }

        Map openNodes = (Map) model.getTableHandler().getTable().getAttribute(TreeConstants.OPEN_NODES);
        Object[] keys = openNodes.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            if (keys[i].equals(currentCellOpenKey)) {
                continue;
            }
            html.append("&amp;").append(keys[i]).equals().append("true");
        }

        if (html.length() == 0) {
            return "";
        }

        return "?" + StringUtils.substringAfter(html.toString(), "&amp;");
    }

    public enum RowPostion {
        top,
        center,
        bottom
    }
}
