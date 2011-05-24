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
package org.extremecomponents.table.core;

/**
 * @author Jeff Johnston
 */
public class TableConstants {
    private TableConstants() {
    }

    // need in set the column property to alias
    public final static String ALIAS="a_";

    // need in controller and tag
    public final static String ROWCOUNT = "ROWCOUNT";

    // ec attributes
    public final static String EXTREME_COMPONENTS = "ec";
    public final static String EXTREME_COMPONENTS_INSTANCE = "ec_i";
    public final static String EXTREME_FORM_ACTION = "act";
    // export attributes
    public final static String EXPORT_TABLE_ID = "ec_eti"; // throw-away parameter for export

    // ajaxView attributes
    public final static String AJAX_TABLE_ID = "ec_ajt";
    public final static String AJAX_TABLE_IN_PLACE = "atp";
    public final static String AJAX_VIEW_DATA = "ajaxViewData";

    // column attributes
    public final static String IS_AUTO_GENERATE_COLUMN = "isAutoGenerateColumn";
    public final static String DATE = "date";
    public final static String CURRENCY = "currency";

    // web.xml attributes
    public final static String MESSAGES_LOCATION = "extremecomponentsMessagesLocation";
    public final static String PREFERENCES_LOCATION = "extremecomponentsPreferencesLocation";

    // limit attributes
    public final static String FILTER = "f_";
    public final static String SORT = "s_";
    public final static String PAGE = "p";
    public final static String CURRENT_ROWS_DISPLAYED = "crd";
    public final static String EXPORT_VIEW = "ev";
    public final static String EXPORT_FILE_NAME = "efn";

    public final static String ACTION = "a";
    public final static String FILTER_ACTION = "fa";
    public final static String CLEAR_ACTION = "ca";

    public final static String SORT_ASC = "asc";
    public final static String SORT_DESC = "desc";
    public final static String SORT_DEFAULT = "default";

    // not used for calculations
    public final static String ROWS_DISPLAYED = "rd";

    // callback attributes
    public final static String CALLBACK_DEFAULT = "default";

    // view attributes
    public final static String VIEW_HTML = "html";
    public final static String VIEW_PDF = "pdf";
    public final static String VIEW_XLS = "xls";
    public final static String VIEW_CSV = "csv";
    public final static String VIEW_AJAX_GET = "ajax_get";
    public final static String VIEW_AJAX_POST = "ajax_post";
    public final static String VIEW_DATA = "viewData";
    public final static String VIEW_RESOLVER = "viewResolver";

    // state attributes
    public final static String STATE = "s_";
    public final static String STATE_DEFAULT = "default";
    public final static String STATE_NOTIFY_TO_DEFAULT = "notifyToDefault";
    public final static String STATE_PERSIST = "persist";
    public final static String STATE_NOTIFY_TO_PERSIST = "notifyToPersist";

    // cell attributes
    public final static String CELL_DISPLAY = "display";
    public final static String CELL_FILTER = "filter";
    public final static String CELL_HEADER = "header";


    // interceptors
    public final static String DEFAULT_INTERCEPT = "default";

    // calc attributes
    public final static String CALC_TOTAL = "total";

    //scope attributes
    public static String PAGE_SCOPE = "page";
    public static String REQUEST_SCOPE = "request";
    public static String SESSION_SCOPE = "session";
    public static String APPLICATION_SCOPE = "application";

    // total rows
    //public final static String TOTAL_ROWS = "totalRows";
}
