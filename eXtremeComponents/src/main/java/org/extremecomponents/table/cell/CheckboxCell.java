package org.extremecomponents.table.cell;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.html.ColumnBuilder;
import org.extremecomponents.util.HtmlBuilder;

/**
 * Created by jeff
 */
public class CheckboxCell implements Cell {
    public String getExportDisplay(TableModel model, Column column) {
        return null;
    }

    public String getHtmlDisplay(TableModel model, Column column) {
        ColumnBuilder columnBuilder = new ColumnBuilder(column);

        columnBuilder.tdStart();

        try {
            String cid = column.getProperty();
            if (cid == null) {
                cid = column.getAlias();
            }
            HtmlBuilder html = columnBuilder.getHtmlBuilder();

            html.input(column.getAlias()).name(cid).value(String.valueOf(column.getValue()));
            html.onclick("CheckboxClick(this)");
            html.xclose();

        } catch (Exception e) {
        }

        columnBuilder.tdEnd();

        return columnBuilder.toString();
    }
}
