/*! SmartAdmin - v1.5 - 2014-09-27 */

!function(a) {
    a.jgrid=a.jgrid|| {}
    ,
    a.extend(a.jgrid, {
        "defaults": {
            "recordtext":"View {0} - {1} of {2}", "emptyrecords":"No records to view", "loadtext":"Loading...", "pgtext":"Page {0} of {1}"
        }
        , "search": {
            "caption":"Search...", "Find":"Find", "Reset":"Reset", "odata":[ {
                "oper": "eq", "text": "equal"
            }
            , {
                "oper": "ne", "text": "not equal"
            }
            , {
                "oper": "lt", "text": "less"
            }
            , {
                "oper": "le", "text": "less or equal"
            }
            , {
                "oper": "gt", "text": "greater"
            }
            , {
                "oper": "ge", "text": "greater or equal"
            }
            , {
                "oper": "bw", "text": "begins with"
            }
            , {
                "oper": "bn", "text": "does not begin with"
            }
            , {
                "oper": "in", "text": "is in"
            }
            , {
                "oper": "ni", "text": "is not in"
            }
            , {
                "oper": "ew", "text": "ends with"
            }
            , {
                "oper": "en", "text": "does not end with"
            }
            , {
                "oper": "cn", "text": "contains"
            }
            , {
                "oper": "nc", "text": "does not contain"
            }
            ], "groupOps":[ {
                "op": "AND", "text": "all"
            }
            , {
                "op": "OR", "text": "any"
            }
            ]
        }
        , "edit": {
            "addCaption":"Add Record", "editCaption":"Edit Record", "bSubmit":"Submit", "bCancel":"Cancel", "bClose":"Close", "saveData":"Data has been changed! Save changes?", "bYes":"Yes", "bNo":"No", "bExit":"Cancel", "msg": {
                "required": "Field is required", "number": "Please, enter valid number", "minValue": "value must be greater than or equal to ", "maxValue": "value must be less than or equal to", "email": "is not a valid e-mail", "integer": "Please, enter valid integer value", "date": "Please, enter valid date value", "url": "is not a valid URL. Prefix required ('http://' or 'https://')", "nodefined": " is not defined!", "novalue": " return value is required!", "customarray": "Custom function should return array!", "customfcheck": "Custom function should be present in case of custom checking!"
            }
        }
        , "view": {
            "caption": "View Record", "bClose": "Close"
        }
        , "del": {
            "caption": "Delete", "msg": "Delete selected record(s)?", "bSubmit": "Delete", "bCancel": "Cancel"
        }
        , "nav": {
            "edittext": "", "edittitle": "Edit selected row", "addtext": "", "addtitle": "Add new row", "deltext": "", "deltitle": "Delete selected row", "searchtext": "", "searchtitle": "Find records", "refreshtext": "", "refreshtitle": "Reload Grid", "alertcap": "Warning", "alerttext": "Please, select row", "viewtext": "", "viewtitle": "View selected row"
        }
        , "col": {
            "caption": "Select columns", "bSubmit": "Ok", "bCancel": "Cancel"
        }
        , "errors": {
            "errcap": "Error", "nourl": "No url is set", "norecords": "No records to process", "model": "Length of colNames <> colModel!"
        }
        , "formatter": {
            "integer": {
                "thousandsSeparator": ",", "defaultValue": "0"
            }
            , "number": {
                "decimalSeparator": ".", "thousandsSeparator": ",", "decimalPlaces": 2, "defaultValue": "0.00"
            }
            , "currency": {
                "decimalSeparator": ".", "thousandsSeparator": ",", "decimalPlaces": 2, "prefix": "", "suffix": "", "defaultValue": "0.00"
            }
            , "date": {
                "dayNames":["Sun", "Mon", "Tue", "Wed", "Thr", "Fri", "Sat", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"], "monthNames":["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"], "AmPm":["am", "pm", "AM", "PM"], "S":function(a) {
                    return 11>a||a>13?["st", "nd", "rd", "th"][Math.min((a-1)%10, 3)]: "th"
                }
                , "srcformat":"Y-m-d", "newformat":"n/j/Y", "parseRe":"/[Tt\\\/:_;., \t\s-]/", "masks": {
                    "ISO8601Long": "Y-m-d H:i:s", "ISO8601Short": "Y-m-d", "ShortDate": "n/j/Y", "LongDate": "l, F d, Y", "FullDateTime": "l, F d, Y g:i:s A", "MonthDay": "F d", "ShortTime": "g:i A", "LongTime": "g:i:s A", "SortableDateTime": "Y-m-d\\TH:i:s", "UniversalSortableDateTime": "Y-m-d H:i:sO", "YearMonth": "F, Y"
                }
                , "reformatAfterEdit":!1
            }
            , "baseLinkUrl":"", "showAction":"", "target":"", "checkbox": {
                "disabled": !0
            }
            , "idName":"id"
        }
    }
    )
}

(jQuery);