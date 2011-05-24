package org.extremecomponents.tree;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Set;

import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.util.HtmlBuilder;

public class TreeRegistryUtils {
    /**
     * Take the parameters and format into hidden fields
     * 
     * @deprecated Is used by now deprecated HtmlView
     */
    public static String getHiddenFields(TableModel model, String parameter) {
        HtmlBuilder html = new HtmlBuilder();

        Set keys = model.getRegistry().getParameterMap().keySet();

        for (Iterator iter = keys.iterator(); iter.hasNext();) {
            String name = (String) iter.next();

            if (((parameter == null) && !name.startsWith(model.getTableHandler().prefixWithTableId())) 
                    || (name.startsWith(model.getTableHandler().prefixWithTableId() + parameter))) {
                String values[] = (String[]) model.getRegistry().getParameterMap().get(name);
                if (values == null || values.length == 0) {
                    html.newline();
                    html.input("hidden").name(name).xclose();
                } else {
                    for (int i = 0; i < values.length; i++) {
                        html.newline();
                        html.input("hidden").name(name).value(values[i]).xclose();
                    }
                }
            }
        }

        return html.toString();
    }

    /**
     * Get a URI formated parameter string. Will look like &key=value
     * 
     * @deprecated Is used by now deprecated HtmlView
     */
    public static String getParameterString(TableModel model, String parameter) {
        HtmlBuilder html = new HtmlBuilder();

        Set keys = model.getRegistry().getParameterMap().keySet();

        for (Iterator iter = keys.iterator(); iter.hasNext();) {
            String name = (String) iter.next();

            if (((parameter == null) 
                    && !name.startsWith(model.getTableHandler().prefixWithTableId())) 
                    || (name.startsWith(model.getTableHandler().prefixWithTableId() + parameter))) {
                String values[] = (String[]) model.getRegistry().getParameterMap().get(name);
                if (values == null || values.length == 0) {
                    html.ampersand().append(name).equals();
                } else {
                    for (int i = 0; i < values.length; i++) {
                        String encodedValue = URLEncoder.encode(values[i]);
                        html.append("&amp;").append(name).equals().append(encodedValue);
                    }
                }

            }
        }

        return html.toString();
    }

    /**
     * Get parameter datum.
     * 
     * @deprecated Is used by now deprecated HtmlView
     */
    public static String getURLParameterString(TableModel model, boolean filter, boolean sort, boolean page, boolean rowsDisplayed) {
        StringBuffer sb = new StringBuffer();

        if (filter) {
            sb.append(getParameterString(model, TableConstants.FILTER));
        }

        if (sort) {
            sb.append(getParameterString(model, TableConstants.SORT));
        }

        if (page) {
            sb.append(getParameterString(model, TableConstants.PAGE));
        }

        if (rowsDisplayed) {
            sb.append(getParameterString(model, TableConstants.CURRENT_ROWS_DISPLAYED));
        }

        sb.append(getParameterString(model, null));

        return sb.toString();
    }

    /**
     * Get all the action as hidden fields.
     * 
     * @deprecated Is used by now deprecated HtmlView
     */
    public static String getFormHiddenFields(TableModel model, boolean filter, boolean sort, boolean page, boolean rowsDisplayed) {
        StringBuffer sb = new StringBuffer();

        if (filter) {
            sb.append(getHiddenFields(model, TableConstants.FILTER));
        }

        if (sort) {
            sb.append(getHiddenFields(model, TableConstants.SORT));
        }

        if (page) {
            sb.append(getHiddenFields(model, TableConstants.PAGE));
        }

        if (rowsDisplayed) {
            sb.append(getHiddenFields(model, TableConstants.CURRENT_ROWS_DISPLAYED));
        }

        sb.append(getHiddenFields(model, null));

        return sb.toString();
    }
    
}
