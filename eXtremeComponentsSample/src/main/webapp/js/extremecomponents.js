(function($) {
    var ALIAS="a_";

    // need in controller and tag
    var ROWCOUNT = "ROWCOUNT";

    // ec attributes
    var EXTREME_COMPONENTS = "ec";
    var EXTREME_COMPONENTS_INSTANCE = "ec_i";
    //ec form ation url
    var EXTREME_FORM_ACTION = "act";

    // export attributes
    var EXPORT_TABLE_ID = "ec_eti"; // throw-away parameter for export

    // ajaxView attributes
    var AJAX_TABLE_ID = "ec_ajt";
    var AJAX_TABLE_IN_PLACE = "atp";
    var AJAX_VIEW_DATA = "ajaxViewData";

    // column attributes
    var IS_AUTO_GENERATE_COLUMN = "isAutoGenerateColumn";
    var DATE = "date";
    var CURRENCY = "currency";

    // web.xml attributes
    var MESSAGES_LOCATION = "extremecomponentsMessagesLocation";
    var PREFERENCES_LOCATION = "extremecomponentsPreferencesLocation";

    // limit attributes
    var FILTER = "f_";
    var SORT = "s_";
    var PAGE = "p";
    var CURRENT_ROWS_DISPLAYED = "crd";
    var EXPORT_VIEW = "ev";
    var EXPORT_FILE_NAME = "efn";

    var ACTION = "a";
    var FILTER_ACTION = "fa";
    var CLEAR_ACTION = "ca";

    var SORT_ASC = "asc";
    var SORT_DESC = "desc";
    var SORT_DEFAULT = "default";

    // not used for calculations
    var ROWS_DISPLAYED = "rd";

    // callback attributes
    var CALLBACK_DEFAULT = "default";

    // view attributes
    var VIEW_HTML = "html";
    var VIEW_PDF = "pdf";
    var VIEW_XLS = "xls";
    var VIEW_CSV = "csv";
    var VIEW_AJAX = "ajax";
    var VIEW_DATA = "viewData";
    var VIEW_RESOLVER = "viewResolver";

    // state attributes
    var STATE = "s_";
    var STATE_DEFAULT = "default";
    var STATE_NOTIFY_TO_DEFAULT = "notifyToDefault";
    var STATE_PERSIST = "persist";
    var STATE_NOTIFY_TO_PERSIST = "notifyToPersist";

    // cell attributes
    var CELL_DISPLAY = "display";
    var CELL_FILTER = "filter";
    var CELL_HEADER = "header";


    // interceptors
    var DEFAULT_INTERCEPT = "default";

    // calc attributes
    var CALC_TOTAL = "total";

    //scope attributes
    var PAGE_SCOPE = "page";
    var REQUEST_SCOPE = "request";
    var SESSION_SCOPE = "session";
    var APPLICATION_SCOPE = "application";
    $.extend($.fn, {
                eXtreme: function(eXsetting) {
                    var settings = {
                    };
                    $.extend({}, settings, eXsetting);
                    return new EXtremePlugin().init(eXsetting);
                }
            });
    function EXtremePlugin() {
        return {
            tableId:null,
            settings:null,
            init:function(obj) {
                this.settings = obj;
                this.tableId = obj[EXTREME_COMPONENTS_INSTANCE];
                return this;
            },
            prefixWithTableId:function(s){
               return this.tableId + '_'+s;
            },
            getPrefixValue:function(key){
               return this.settings[this.prefixWithTableId(key)];
            },
            getValue:function(key){
               return this.settings[key];
            },
            ajaxViewRequest:function() {
                var url = this.getPrefixValue(EXTREME_FORM_ACTION);
                var tmpParam = AJAX_TABLE_ID + '=' + this.tableId;
                var inPlaceDiv = $("#" + this.getPrefixValue([AJAX_TABLE_IN_PLACE]));
                var loadHtml = "<span class='eXtremeTableLoading' style='height: 16px;width: 16px;display: block;'/>";
                $.ajax({
                            type: "POST",
                            url: url,
                            data: tmpParam,
                            beforeSend:function(e,xhr,o) {
                                 inPlaceDiv.html(loadHtml);
                                 inPlaceDiv.show();
                            },
                            success: function(msg) {
                                inPlaceDiv.html(msg);
                            },
                            error: function(XMLHttpRequest, textStatus, errorThrown) {
                                inPlaceDiv.innerHTML = XMLHttpRequest.responseText;
                            }

                        });
            }
        }
    }

})(jQuery);