/*! SmartAdmin - v1.5 - 2014-09-27 */
function tableToGrid(a, b) {
    jQuery(a).each(function() {
        if (!this.grid) {
            jQuery(this).width("99%");
            var a = jQuery(this).width(),
                c = jQuery("tr td:first-child input[type=checkbox]:first", jQuery(this)),
                d = jQuery("tr td:first-child input[type=radio]:first", jQuery(this)),
                c = 0 < c.length,
                d = !c && 0 < d.length,
                e = c || d,
                f = [],
                g = [];
            jQuery("th", jQuery(this)).each(function() {
                0 === f.length && e ? (f.push({
                    "name": "__selection__",
                    "index": "__selection__",
                    "width": 0,
                    "hidden": !0
                }), g.push("__selection__")) : (f.push({
                    "name": jQuery(this).attr("id") || jQuery.trim(jQuery.jgrid.stripHtml(jQuery(this).html())).split(" ").join("_"),
                    "index": jQuery(this).attr("id") || jQuery.trim(jQuery.jgrid.stripHtml(jQuery(this).html())).split(" ").join("_"),
                    "width": jQuery(this).width() || 150
                }), g.push(jQuery(this).html()))
            });
            var h = [],
                i = [],
                j = [];
            for (jQuery("tbody > tr", jQuery(this)).each(function() {
                    var a = {},
                        b = 0;
                    jQuery("td", jQuery(this)).each(function() {
                        if (0 === b && e) {
                            var c = jQuery("input", jQuery(this)),
                                d = c.attr("value");
                            i.push(d || h.length), c.is(":checked") && j.push(d), a[f[b].name] = c.attr("value")
                        } else a[f[b].name] = jQuery(this).html();
                        b++
                    }), b > 0 && h.push(a)
                }), jQuery(this).empty(), jQuery(this).addClass("scroll"), jQuery(this).jqGrid(jQuery.extend({
                    "datatype": "local",
                    "width": a,
                    "colNames": g,
                    "colModel": f,
                    "multiselect": c
                }, b || {})), a = 0; a < h.length; a++) d = null, 0 < i.length && (d = i[a]) && d.replace && (d = encodeURIComponent(d).replace(/[.\-%]/g, "_")), null === d && (d = a + 1), jQuery(this).jqGrid("addRowData", d, h[a]);
            for (a = 0; a < j.length; a++) jQuery(this).jqGrid("setSelection", j[a])
        }
    })
}! function(b) {
    b.jgrid = b.jgrid || {}, b.extend(b.jgrid, {
        "version": "4.5.3",
        "htmlDecode": function(a) {
            return a && ("&nbsp;" === a || "&#160;" === a || 1 === a.length && 160 === a.charCodeAt(0)) ? "" : a ? ("" + a).replace(/&gt;/g, ">").replace(/&lt;/g, "<").replace(/&quot;/g, '"').replace(/&amp;/g, "&") : a
        },
        "htmlEncode": function(a) {
            return a ? ("" + a).replace(/&/g, "&amp;").replace(/\"/g, "&quot;").replace(/</g, "&lt;").replace(/>/g, "&gt;") : a
        },
        "format": function(a) {
            var c = b.makeArray(arguments).slice(1);
            return null == a && (a = ""), a.replace(/\{(\d+)\}/g, function(a, b) {
                return c[b]
            })
        },
        "msie": "Microsoft Internet Explorer" === navigator.appName,
        "msiever": function() {
            var a = -1;
            return null != /MSIE ([0-9]{1,}[.0-9]{0,})/.exec(navigator.userAgent) && (a = parseFloat(RegExp.$1)), a
        },
        "getCellIndex": function(a) {
            return a = b(a), a.is("tr") ? -1 : (a = (a.is("td") || a.is("th") ? a : a.closest("td,th"))[0], b.jgrid.msie ? b.inArray(a, a.parentNode.cells) : a.cellIndex)
        },
        "stripHtml": function(a) {
            var a = "" + a,
                b = /<("[^"]*"|'[^']*'|[^'">])*>/gi;
            return a ? (a = a.replace(b, "")) && "&nbsp;" !== a && "&#160;" !== a ? a.replace(/\"/g, "'") : "" : a
        },
        "stripPref": function(a, c) {
            var d = b.type(a);
            return ("string" === d || "number" === d) && (a = "" + a, c = "" !== a ? ("" + c).replace("" + a, "") : c), c
        },
        "parse": function(d) {
            return "while(1);" === d.substr(0, 9) && (d = d.substr(9)), "/*" === d.substr(0, 2) && (d = d.substr(2, d.length - 4)), d || (d = "{}"), !0 === b.jgrid.useJSON && "object" == typeof JSON && "function" == typeof JSON.parse ? JSON.parse(d) : eval("(" + d + ")")
        },
        "parseDate": function(a, c, d, e) {
            var f, g, h = /^\/Date\((([-+])?[0-9]+)(([-+])([0-9]{2})([0-9]{2}))?\)\/$/,
                i = "string" == typeof c ? c.match(h) : null,
                h = function(a, b) {
                    for (a = "" + a, b = parseInt(b, 10) || 2; a.length < b;) a = "0" + a;
                    return a
                },
                j = {
                    "m": 1,
                    "d": 1,
                    "y": 1970,
                    "h": 0,
                    "i": 0,
                    "s": 0,
                    "u": 0
                },
                k = 0,
                k = function(a, b) {
                    return 0 === a ? 12 === b && (b = 0) : 12 !== b && (b += 12), b
                };
            if (void 0 === e && (e = b.jgrid.formatter.date), void 0 === e.parseRe && (e.parseRe = /[Tt\\\/:_;.,\t\s-]/), e.masks.hasOwnProperty(a) && (a = e.masks[a]), c && null != c)
                if (isNaN(c - 0) || "u" !== ("" + a).toLowerCase())
                    if (c.constructor === Date) k = c;
                    else if (null !== i) k = new Date(parseInt(i[1], 10)), i[3] && (a = 60 * Number(i[5]) + Number(i[6]), a *= "-" === i[4] ? 1 : -1, a -= k.getTimezoneOffset(), k.setTime(Number(Number(k) + 6e4 * a)));
            else {
                for (c = ("" + c).replace(/\\T/g, "T").replace(/\\t/, "t").split(e.parseRe), a = a.replace(/\\T/g, "T").replace(/\\t/, "t").split(e.parseRe), f = 0, g = a.length; g > f; f++) "M" === a[f] && (i = b.inArray(c[f], e.monthNames), -1 !== i && 12 > i && (c[f] = i + 1, j.m = c[f])), "F" === a[f] && (i = b.inArray(c[f], e.monthNames, 12), -1 !== i && i > 11 && (c[f] = i + 1 - 12, j.m = c[f])), "a" === a[f] && (i = b.inArray(c[f], e.AmPm), -1 !== i && 2 > i && c[f] === e.AmPm[i] && (c[f] = i, j.h = k(c[f], j.h))), "A" === a[f] && (i = b.inArray(c[f], e.AmPm), -1 !== i && i > 1 && c[f] === e.AmPm[i] && (c[f] = i - 2, j.h = k(c[f], j.h))), "g" === a[f] && (j.h = parseInt(c[f], 10)), void 0 !== c[f] && (j[a[f].toLowerCase()] = parseInt(c[f], 10));
                if (j.f && (j.m = j.f), 0 === j.m && 0 === j.y && 0 === j.d) return "&#160;";
                j.m = parseInt(j.m, 10) - 1, k = j.y, k >= 70 && 99 >= k ? j.y = 1900 + j.y : k >= 0 && 69 >= k && (j.y = 2e3 + j.y), k = new Date(j.y, j.m, j.d, j.h, j.i, j.s, j.u)
            } else k = new Date(1e3 * parseFloat(c));
            else k = new Date(j.y, j.m, j.d, j.h, j.i, j.s, j.u);
            if (void 0 === d) return k;
            e.masks.hasOwnProperty(d) ? d = e.masks[d] : d || (d = "Y-m-d"), a = k.getHours(), c = k.getMinutes(), j = k.getDate(), i = k.getMonth() + 1, f = k.getTimezoneOffset(), g = k.getSeconds();
            var l = k.getMilliseconds(),
                m = k.getDay(),
                n = k.getFullYear(),
                o = (m + 6) % 7 + 1,
                p = (new Date(n, i - 1, j) - new Date(n, 0, 1)) / 864e5,
                q = {
                    "d": h(j),
                    "D": e.dayNames[m],
                    "j": j,
                    "l": e.dayNames[m + 7],
                    "N": o,
                    "S": e.S(j),
                    "w": m,
                    "z": p,
                    "W": 5 > o ? Math.floor((p + o - 1) / 7) + 1 : Math.floor((p + o - 1) / 7) || (4 > (new Date(n - 1, 0, 1).getDay() + 6) % 7 ? 53 : 52),
                    "F": e.monthNames[i - 1 + 12],
                    "m": h(i),
                    "M": e.monthNames[i - 1],
                    "n": i,
                    "t": "?",
                    "L": "?",
                    "o": "?",
                    "Y": n,
                    "y": ("" + n).substring(2),
                    "a": 12 > a ? e.AmPm[0] : e.AmPm[1],
                    "A": 12 > a ? e.AmPm[2] : e.AmPm[3],
                    "B": "?",
                    "g": a % 12 || 12,
                    "G": a,
                    "h": h(a % 12 || 12),
                    "H": h(a),
                    "i": h(c),
                    "s": h(g),
                    "u": l,
                    "e": "?",
                    "I": "?",
                    "O": (f > 0 ? "-" : "+") + h(100 * Math.floor(Math.abs(f) / 60) + Math.abs(f) % 60, 4),
                    "P": "?",
                    "T": (("" + k).match(/\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g) || [""]).pop().replace(/[^-+\dA-Z]/g, ""),
                    "Z": "?",
                    "c": "?",
                    "r": "?",
                    "U": Math.floor(k / 1e3)
                };
            return d.replace(/\\.|[dDjlNSwzWFmMntLoYyaABgGhHisueIOPTZcrU]/g, function(a) {
                return q.hasOwnProperty(a) ? q[a] : a.substring(1)
            })
        },
        "jqID": function(a) {
            return ("" + a).replace(/[!"#$%&'()*+,.\/:; <=>?@\[\\\]\^`{|}~]/g, "\\$&")
        },
        "guid": 1,
        "uidPref": "jqg",
        "randId": function(a) {
            return (a || b.jgrid.uidPref) + b.jgrid.guid++
        },
        "getAccessor": function(a, b) {
            var c, d, e, f = [];
            if ("function" == typeof b) return b(a);
            if (c = a[b], void 0 === c) try {
                if ("string" == typeof b && (f = b.split(".")), e = f.length)
                    for (c = a; c && e--;) d = f.shift(), c = c[d]
            } catch (g) {}
            return c
        },
        "getXmlData": function(a, c, d) {
            var e = "string" == typeof c ? c.match(/^(.*)\[(\w+)\]$/) : null;
            return "function" == typeof c ? c(a) : e && e[2] ? e[1] ? b(e[1], a).attr(e[2]) : b(a).attr(e[2]) : (a = b(c, a), d ? a : 0 < a.length ? b(a).text() : void 0)
        },
        "cellWidth": function() {
            var a = b("<div class='ui-jqgrid' style='left:10000px'><table class='ui-jqgrid-btable' style='width:5px;'><tr class='jqgrow'><td style='width:5px;display:block;'></td></tr></table></div>"),
                c = a.appendTo("body").find("td").width();
            return a.remove(), .1 < Math.abs(c - 5)
        },
        "cell_width": !0,
        "ajaxOptions": {},
        "from": function(d) {
            return new function(d, c) {
                "string" == typeof d && (d = b.data(d));
                var e = this,
                    a = d,
                    i = !0,
                    f = !1,
                    h = c,
                    j = /[\$,%]/g,
                    k = null,
                    m = null,
                    n = 0,
                    l = !1,
                    r = "",
                    u = [],
                    E = !0;
                if ("object" != typeof d || !d.push) throw "data provides is not an array";
                return 0 < d.length && (E = "object" != typeof d[0] ? !1 : !0), this._hasData = function() {
                    return null === a ? !1 : 0 === a.length ? !1 : !0
                }, this._getStr = function(a) {
                    var b = [];
                    return f && b.push("jQuery.trim("), b.push("String(" + a + ")"), f && b.push(")"), i || b.push(".toLowerCase()"), b.join("")
                }, this._strComp = function(a) {
                    return "string" == typeof a ? ".toString()" : ""
                }, this._group = function(a, b) {
                    return {
                        "field": a.toString(),
                        "unique": b,
                        "items": []
                    }
                }, this._toStr = function(a) {
                    return f && (a = b.trim(a)), a = a.toString().replace(/\\/g, "\\\\").replace(/\"/g, '\\"'), i ? a : a.toLowerCase()
                }, this._funcLoop = function(c) {
                    var d = [];
                    return b.each(a, function(a, b) {
                        d.push(c(b))
                    }), d
                }, this._append = function(a) {
                    var b;
                    for (h = null === h ? "" : h + ("" === r ? " && " : r), b = 0; n > b; b++) h += "(";
                    l && (h += "!"), h += "(" + a + ")", l = !1, r = "", n = 0
                }, this._setCommand = function(a, b) {
                    k = a, m = b
                }, this._resetNegate = function() {
                    l = !1
                }, this._repeatCommand = function(a, b) {
                    return null === k ? e : null !== a && null !== b ? k(a, b) : null !== m && E ? k(m, a) : k(a)
                }, this._equals = function(a, b) {
                    return 0 === e._compare(a, b, 1)
                }, this._compare = function(a, b, c) {
                    var d = Object.prototype.toString;
                    return void 0 === c && (c = 1), void 0 === a && (a = null), void 0 === b && (b = null), null === a && null === b ? 0 : null === a && null !== b ? 1 : null !== a && null === b ? -1 : "[object Date]" === d.call(a) && "[object Date]" === d.call(b) ? b > a ? -c : a > b ? c : 0 : (!i && "number" != typeof a && "number" != typeof b && (a = "" + a, b = "" + b), b > a ? -c : a > b ? c : 0)
                }, this._performSort = function() {
                    0 !== u.length && (a = e._doSort(a, 0))
                }, this._doSort = function(a, b) {
                    var c = u[b].by,
                        d = u[b].dir,
                        f = u[b].type,
                        g = u[b].datefmt;
                    if (b === u.length - 1) return e._getOrder(a, c, d, f, g);
                    b++;
                    for (var h, c = e._getGroup(a, c, d, f, g), d = [], f = 0; f < c.length; f++)
                        for (h = e._doSort(c[f].items, b), g = 0; g < h.length; g++) d.push(h[g]);
                    return d
                }, this._getOrder = function(a, c, d, f, g) {
                    var h, k, l = [],
                        m = [],
                        n = "a" === d ? 1 : -1;
                    void 0 === f && (f = "text"), k = "float" === f || "number" === f || "currency" === f || "numeric" === f ? function(a) {
                        return a = parseFloat(("" + a).replace(j, "")), isNaN(a) ? 0 : a
                    } : "int" === f || "integer" === f ? function(a) {
                        return a ? parseFloat(("" + a).replace(j, "")) : 0
                    } : "date" === f || "datetime" === f ? function(a) {
                        return b.jgrid.parseDate(g, a).getTime()
                    } : b.isFunction(f) ? f : function(a) {
                        return a = a ? b.trim("" + a) : "", i ? a : a.toLowerCase()
                    }, b.each(a, function(a, d) {
                        h = "" !== c ? b.jgrid.getAccessor(d, c) : d, void 0 === h && (h = ""), h = k(h, d), m.push({
                            "vSort": h,
                            "index": a
                        })
                    }), m.sort(function(a, b) {
                        return a = a.vSort, b = b.vSort, e._compare(a, b, n)
                    });
                    for (var f = 0, o = a.length; o > f;) d = m[f].index, l.push(a[d]), f++;
                    return l
                }, this._getGroup = function(a, c, d, f, g) {
                    var h, i = [],
                        j = null,
                        k = null;
                    return b.each(e._getOrder(a, c, d, f, g), function(a, d) {
                        h = b.jgrid.getAccessor(d, c), null == h && (h = ""), e._equals(k, h) || (k = h, null !== j && i.push(j), j = e._group(c, h)), j.items.push(d)
                    }), null !== j && i.push(j), i
                }, this.ignoreCase = function() {
                    return i = !1, e
                }, this.useCase = function() {
                    return i = !0, e
                }, this.trim = function() {
                    return f = !0, e
                }, this.noTrim = function() {
                    return f = !1, e
                }, this.execute = function() {
                    var d = h,
                        c = [];
                    return null === d ? e : (b.each(a, function() {
                        eval(d) && c.push(this)
                    }), a = c, e)
                }, this.data = function() {
                    return a
                }, this.select = function(c) {
                    if (e._performSort(), !e._hasData()) return [];
                    if (e.execute(), b.isFunction(c)) {
                        var d = [];
                        return b.each(a, function(a, b) {
                            d.push(c(b))
                        }), d
                    }
                    return a
                }, this.hasMatch = function() {
                    return e._hasData() ? (e.execute(), 0 < a.length) : !1
                }, this.andNot = function(a, b, c) {
                    return l = !l, e.and(a, b, c)
                }, this.orNot = function(a, b, c) {
                    return l = !l, e.or(a, b, c)
                }, this.not = function(a, b, c) {
                    return e.andNot(a, b, c)
                }, this.and = function(a, b, c) {
                    return r = " && ", void 0 === a ? e : e._repeatCommand(a, b, c)
                }, this.or = function(a, b, c) {
                    return r = " || ", void 0 === a ? e : e._repeatCommand(a, b, c)
                }, this.orBegin = function() {
                    return n++, e
                }, this.orEnd = function() {
                    return null !== h && (h += ")"), e
                }, this.isNot = function(a) {
                    return l = !l, e.is(a)
                }, this.is = function(a) {
                    return e._append("this." + a), e._resetNegate(), e
                }, this._compareValues = function(a, c, d, f, g) {
                    var h;
                    h = E ? "jQuery.jgrid.getAccessor(this,'" + c + "')" : "this", void 0 === d && (d = null);
                    var i = d,
                        k = void 0 === g.stype ? "text" : g.stype;
                    if (null !== d) switch (k) {
                        case "int":
                        case "integer":
                            i = isNaN(Number(i)) || "" === i ? "0" : i, h = "parseInt(" + h + ",10)", i = "parseInt(" + i + ",10)";
                            break;
                        case "float":
                        case "number":
                        case "numeric":
                            i = ("" + i).replace(j, ""), i = isNaN(Number(i)) || "" === i ? "0" : i, h = "parseFloat(" + h + ")", i = "parseFloat(" + i + ")";
                            break;
                        case "date":
                        case "datetime":
                            i = "" + b.jgrid.parseDate(g.newfmt || "Y-m-d", i).getTime(), h = 'jQuery.jgrid.parseDate("' + g.srcfmt + '",' + h + ").getTime()";
                            break;
                        default:
                            h = e._getStr(h), i = e._getStr('"' + e._toStr(i) + '"')
                    }
                    return e._append(h + " " + f + " " + i), e._setCommand(a, c), e._resetNegate(), e
                }, this.equals = function(a, b, c) {
                    return e._compareValues(e.equals, a, b, "==", c)
                }, this.notEquals = function(a, b, c) {
                    return e._compareValues(e.equals, a, b, "!==", c)
                }, this.isNull = function(a, b, c) {
                    return e._compareValues(e.equals, a, null, "===", c)
                }, this.greater = function(a, b, c) {
                    return e._compareValues(e.greater, a, b, ">", c)
                }, this.less = function(a, b, c) {
                    return e._compareValues(e.less, a, b, "<", c)
                }, this.greaterOrEquals = function(a, b, c) {
                    return e._compareValues(e.greaterOrEquals, a, b, ">=", c)
                }, this.lessOrEquals = function(a, b, c) {
                    return e._compareValues(e.lessOrEquals, a, b, "<=", c)
                }, this.startsWith = function(a, c) {
                    var d = null == c ? a : c,
                        d = f ? b.trim(d.toString()).length : d.toString().length;
                    return E ? e._append(e._getStr("jQuery.jgrid.getAccessor(this,'" + a + "')") + ".substr(0," + d + ") == " + e._getStr('"' + e._toStr(c) + '"')) : (d = f ? b.trim(c.toString()).length : c.toString().length, e._append(e._getStr("this") + ".substr(0," + d + ") == " + e._getStr('"' + e._toStr(a) + '"'))), e._setCommand(e.startsWith, a), e._resetNegate(), e
                }, this.endsWith = function(a, c) {
                    var d = null == c ? a : c,
                        d = f ? b.trim(d.toString()).length : d.toString().length;
                    return e._append(E ? e._getStr("jQuery.jgrid.getAccessor(this,'" + a + "')") + ".substr(" + e._getStr("jQuery.jgrid.getAccessor(this,'" + a + "')") + ".length-" + d + "," + d + ') == "' + e._toStr(c) + '"' : e._getStr("this") + ".substr(" + e._getStr("this") + '.length-"' + e._toStr(a) + '".length,"' + e._toStr(a) + '".length) == "' + e._toStr(a) + '"'), e._setCommand(e.endsWith, a), e._resetNegate(), e
                }, this.contains = function(a, b) {
                    return e._append(E ? e._getStr("jQuery.jgrid.getAccessor(this,'" + a + "')") + '.indexOf("' + e._toStr(b) + '",0) > -1' : e._getStr("this") + '.indexOf("' + e._toStr(a) + '",0) > -1'), e._setCommand(e.contains, a), e._resetNegate(), e
                }, this.groupBy = function(b, c, d, f) {
                    return e._hasData() ? e._getGroup(a, b, c, d, f) : null
                }, this.orderBy = function(a, c, d, f) {
                    return c = null == c ? "a" : b.trim(c.toString().toLowerCase()), null == d && (d = "text"), null == f && (f = "Y-m-d"), ("desc" === c || "descending" === c) && (c = "d"), ("asc" === c || "ascending" === c) && (c = "a"), u.push({
                        "by": a,
                        "dir": c,
                        "type": d,
                        "datefmt": f
                    }), e
                }, e
            }(d, null)
        },
        "getMethod": function(a) {
            return this.getAccessor(b.fn.jqGrid, a)
        },
        "extend": function(a) {
            b.extend(b.fn.jqGrid, a), this.no_legacy_api || b.fn.extend(a)
        }
    }), b.fn.jqGrid = function(a) {
        if ("string" == typeof a) {
            var c = b.jgrid.getMethod(a);
            if (!c) throw "jqGrid - No such method: " + a;
            var d = b.makeArray(arguments).slice(1);
            return c.apply(this, d)
        }
        return this.each(function() {
            if (!this.grid) {
                var c = b.extend(!0, {
                        "url": "",
                        "height": 150,
                        "page": 1,
                        "rowNum": 20,
                        "rowTotal": null,
                        "records": 0,
                        "pager": "",
                        "pgbuttons": !0,
                        "pginput": !0,
                        "colModel": [],
                        "rowList": [],
                        "colNames": [],
                        "sortorder": "asc",
                        "sortname": "",
                        "datatype": "xml",
                        "mtype": "GET",
                        "altRows": !1,
                        "selarrrow": [],
                        "savedRow": [],
                        "shrinkToFit": !0,
                        "xmlReader": {},
                        "jsonReader": {},
                        "subGrid": !1,
                        "subGridModel": [],
                        "reccount": 0,
                        "lastpage": 0,
                        "lastsort": 0,
                        "selrow": null,
                        "beforeSelectRow": null,
                        "onSelectRow": null,
                        "onSortCol": null,
                        "ondblClickRow": null,
                        "onRightClickRow": null,
                        "onPaging": null,
                        "onSelectAll": null,
                        "onInitGrid": null,
                        "loadComplete": null,
                        "gridComplete": null,
                        "loadError": null,
                        "loadBeforeSend": null,
                        "afterInsertRow": null,
                        "beforeRequest": null,
                        "beforeProcessing": null,
                        "onHeaderClick": null,
                        "viewrecords": !1,
                        "loadonce": !1,
                        "multiselect": !1,
                        "multikey": !1,
                        "editurl": null,
                        "search": !1,
                        "caption": "",
                        "hidegrid": !0,
                        "hiddengrid": !1,
                        "postData": {},
                        "userData": {},
                        "treeGrid": !1,
                        "treeGridModel": "nested",
                        "treeReader": {},
                        "treeANode": -1,
                        "ExpandColumn": null,
                        "tree_root_level": 0,
                        "prmNames": {
                            "page": "page",
                            "rows": "rows",
                            "sort": "sidx",
                            "order": "sord",
                            "search": "_search",
                            "nd": "nd",
                            "id": "id",
                            "oper": "oper",
                            "editoper": "edit",
                            "addoper": "add",
                            "deloper": "del",
                            "subgridid": "id",
                            "npage": null,
                            "totalrows": "totalrows"
                        },
                        "forceFit": !1,
                        "gridstate": "visible",
                        "cellEdit": !1,
                        "cellsubmit": "remote",
                        "nv": 0,
                        "loadui": "enable",
                        "toolbar": [!1, ""],
                        "scroll": !1,
                        "multiboxonly": !1,
                        "deselectAfterSort": !0,
                        "scrollrows": !1,
                        "autowidth": !1,
                        "scrollOffset": 18,
                        "cellLayout": 5,
                        "subGridWidth": 20,
                        "multiselectWidth": 20,
                        "gridview": !1,
                        "rownumWidth": 25,
                        "rownumbers": !1,
                        "pagerpos": "center",
                        "recordpos": "right",
                        "footerrow": !1,
                        "userDataOnFooter": !1,
                        "hoverrows": !0,
                        "altclass": "ui-priority-secondary",
                        "viewsortcols": [!1, "vertical", !0],
                        "resizeclass": "",
                        "autoencode": !1,
                        "remapColumns": [],
                        "ajaxGridOptions": {},
                        "direction": "ltr",
                        "toppager": !1,
                        "headertitles": !1,
                        "scrollTimeout": 40,
                        "data": [],
                        "_index": {},
                        "grouping": !1,
                        "groupingView": {
                            "groupField": [],
                            "groupOrder": [],
                            "groupText": [],
                            "groupColumnShow": [],
                            "groupSummary": [],
                            "showSummaryOnHide": !1,
                            "sortitems": [],
                            "sortnames": [],
                            "summary": [],
                            "summaryval": [],
                            "plusicon": "ui-icon-circlesmall-plus",
                            "minusicon": "ui-icon-circlesmall-minus",
                            "displayField": []
                        },
                        "ignoreCase": !1,
                        "cmTemplate": {},
                        "idPrefix": "",
                        "multiSort": !1
                    }, b.jgrid.defaults, a || {}),
                    d = this,
                    e = {
                        "headers": [],
                        "cols": [],
                        "footers": [],
                        "dragStart": function(a, e, f) {
                            this.resizing = {
                                "idx": a,
                                "startX": e.clientX,
                                "sOL": e.clientX - 6
                            }, this.hDiv.style.cursor = "col-resize", this.curGbox = b("#rs_m" + b.jgrid.jqID(c.id), "#gbox_" + b.jgrid.jqID(c.id)), this.curGbox.css({
                                "display": "block",
                                "left": e.clientX - 6,
                                "top": f[1],
                                "height": f[2]
                            }), b(d).triggerHandler("jqGridResizeStart", [e, a]), b.isFunction(c.resizeStart) && c.resizeStart.call(d, e, a), document.onselectstart = function() {
                                return !1
                            }
                        },
                        "dragMove": function(a) {
                            if (this.resizing) {
                                var b, d = a.clientX - this.resizing.startX,
                                    a = this.headers[this.resizing.idx],
                                    e = "ltr" === c.direction ? a.width + d : a.width - d;
                                e > 33 && (this.curGbox.css({
                                    "left": this.resizing.sOL + d
                                }), !0 === c.forceFit ? (b = this.headers[this.resizing.idx + c.nv], d = "ltr" === c.direction ? b.width - d : b.width + d, d > 33 && (a.newWidth = e, b.newWidth = d)) : (this.newWidth = "ltr" === c.direction ? c.tblwidth + d : c.tblwidth - d, a.newWidth = e))
                            }
                        },
                        "dragEnd": function() {
                            if (this.hDiv.style.cursor = "default", this.resizing) {
                                var a = this.resizing.idx,
                                    e = this.headers[a].newWidth || this.headers[a].width,
                                    e = parseInt(e, 10);
                                this.resizing = !1, b("#rs_m" + b.jgrid.jqID(c.id)).css("display", "none"), c.colModel[a].width = e, this.headers[a].width = e, this.headers[a].el.style.width = e + "px", this.cols[a].style.width = e + "px", 0 < this.footers.length && (this.footers[a].style.width = e + "px"), !0 === c.forceFit ? (e = this.headers[a + c.nv].newWidth || this.headers[a + c.nv].width, this.headers[a + c.nv].width = e, this.headers[a + c.nv].el.style.width = e + "px", this.cols[a + c.nv].style.width = e + "px", 0 < this.footers.length && (this.footers[a + c.nv].style.width = e + "px"), c.colModel[a + c.nv].width = e) : (c.tblwidth = this.newWidth || c.tblwidth, b("table:first", this.bDiv).css("width", c.tblwidth + "px"), b("table:first", this.hDiv).css("width", c.tblwidth + "px"), this.hDiv.scrollLeft = this.bDiv.scrollLeft, c.footerrow && (b("table:first", this.sDiv).css("width", c.tblwidth + "px"), this.sDiv.scrollLeft = this.bDiv.scrollLeft)), b(d).triggerHandler("jqGridResizeStop", [e, a]), b.isFunction(c.resizeStop) && c.resizeStop.call(d, e, a)
                            }
                            this.curGbox = null, document.onselectstart = function() {
                                return !0
                            }
                        },
                        "populateVisible": function() {
                            e.timer && clearTimeout(e.timer), e.timer = null;
                            var a = b(e.bDiv).height();
                            if (a) {
                                var d, f, g = b("table:first", e.bDiv);
                                if (g[0].rows.length) try {
                                    f = (d = g[0].rows[1]) ? b(d).outerHeight() || e.prevRowHeight : e.prevRowHeight
                                } catch (h) {
                                    f = e.prevRowHeight
                                }
                                if (f) {
                                    e.prevRowHeight = f;
                                    var i = c.rowNum;
                                    d = e.scrollTop = e.bDiv.scrollTop;
                                    var j = Math.round(g.position().top) - d,
                                        k = j + g.height();
                                    f *= i;
                                    var l, m, n;
                                    a > k && 0 >= j && (void 0 === c.lastpage || parseInt((k + d + f - 1) / f, 10) <= c.lastpage) && (m = parseInt((a - k + f - 1) / f, 10), k >= 0 || 2 > m || !0 === c.scroll ? (l = Math.round((k + d) / f) + 1, j = -1) : j = 1), j > 0 && (l = parseInt(d / f, 10) + 1, m = parseInt((d + a) / f, 10) + 2 - l, n = !0), !m || c.lastpage && (l > c.lastpage || 1 === c.lastpage || l === c.page && l === c.lastpage) || (e.hDiv.loading ? e.timer = setTimeout(e.populateVisible, c.scrollTimeout) : (c.page = l, n && (e.selectionPreserver(g[0]), e.emptyRows.call(g[0], !1, !1)), e.populate(m)))
                                }
                            }
                        },
                        "scrollGrid": function(a) {
                            if (c.scroll) {
                                var b = e.bDiv.scrollTop;
                                void 0 === e.scrollTop && (e.scrollTop = 0), b !== e.scrollTop && (e.scrollTop = b, e.timer && clearTimeout(e.timer), e.timer = setTimeout(e.populateVisible, c.scrollTimeout))
                            }
                            e.hDiv.scrollLeft = e.bDiv.scrollLeft, c.footerrow && (e.sDiv.scrollLeft = e.bDiv.scrollLeft), a && a.stopPropagation()
                        },
                        "selectionPreserver": function(a) {
                            var c = a.p,
                                d = c.selrow,
                                e = c.selarrrow ? b.makeArray(c.selarrrow) : null,
                                f = a.grid.bDiv.scrollLeft,
                                g = function() {
                                    var h;
                                    if (c.selrow = null, c.selarrrow = [], c.multiselect && e && 0 < e.length)
                                        for (h = 0; h < e.length; h++) e[h] !== d && b(a).jqGrid("setSelection", e[h], !1, null);
                                    d && b(a).jqGrid("setSelection", d, !1, null), a.grid.bDiv.scrollLeft = f, b(a).unbind(".selectionPreserver", g)
                                };
                            b(a).bind("jqGridGridComplete.selectionPreserver", g)
                        }
                    };
                if ("TABLE" !== this.tagName.toUpperCase()) alert("Element is not a table");
                else if (void 0 !== document.documentMode && 5 >= document.documentMode) alert("Grid can not be used in this ('quirks') mode!");
                else {
                    b(this).empty().attr("tabindex", "0"), this.p = c, this.p.useProp = !!b.fn.prop;
                    var f, g;
                    if (0 === this.p.colNames.length)
                        for (f = 0; f < this.p.colModel.length; f++) this.p.colNames[f] = this.p.colModel[f].label || this.p.colModel[f].name;
                    if (this.p.colNames.length !== this.p.colModel.length) alert(b.jgrid.errors.model);
                    else {
                        var h = b("<div class='ui-jqgrid-view'></div>"),
                            i = b.jgrid.msie;
                        d.p.direction = b.trim(d.p.direction.toLowerCase()), -1 === b.inArray(d.p.direction, ["ltr", "rtl"]) && (d.p.direction = "ltr"), g = d.p.direction, b(h).insertBefore(this), b(this).removeClass("scroll").appendTo(h);
                        var j = b("<div class='ui-jqgrid ui-widget ui-widget-content ui-corner-all'></div>");
                        b(j).attr({
                            "id": "gbox_" + this.id,
                            "dir": g
                        }).insertBefore(h), b(h).attr("id", "gview_" + this.id).appendTo(j), b("<div class='ui-widget-overlay jqgrid-overlay' id='lui_" + this.id + "'></div>").insertBefore(h), b("<div class='loading ui-state-default ui-state-active' id='load_" + this.id + "'>" + this.p.loadtext + "</div>").insertBefore(h), b(this).attr({
                            "cellspacing": "0",
                            "cellpadding": "0",
                            "border": "0",
                            "role": "grid",
                            "aria-multiselectable": !!this.p.multiselect,
                            "aria-labelledby": "gbox_" + this.id
                        });
                        var k = function(a, b) {
                                return a = parseInt(a, 10), isNaN(a) ? b || 0 : a
                            },
                            l = function(a, c, f, g, h, i) {
                                var j = d.p.colModel[a],
                                    k = j.align,
                                    l = 'style="',
                                    m = j.classes,
                                    n = j.name,
                                    o = [];
                                return k && (l += "text-align:" + k + ";"), j.hidden === !0 && (l += "display:none;"), 0 === c ? l += "width: " + e.headers[a].width + "px;" : j.cellattr && b.isFunction(j.cellattr) && (a = j.cellattr.call(d, h, f, g, j, i)) && "string" == typeof a && (a = a.replace(/style/i, "style").replace(/title/i, "title"), a.indexOf("title") > -1 && (j.title = !1), a.indexOf("class") > -1 && (m = void 0), o = a.replace("-style", "-sti").split(/style/), 2 === o.length ? (o[1] = b.trim(o[1].replace("-sti", "-style").replace("=", "")), (0 === o[1].indexOf("'") || 0 === o[1].indexOf('"')) && (o[1] = o[1].substring(1)), l += o[1].replace(/'/gi, '"')) : l += '"'), o.length || (o[0] = "", l += '"'), l += (void 0 !== m ? ' class="' + m + '"' : "") + (j.title && f ? ' title="' + b.jgrid.stripHtml(f) + '"' : ""), l += ' aria-describedby="' + d.p.id + "_" + n + '"', l + o[0]
                            },
                            m = function(a) {
                                return null == a || "" === a ? "&#160;" : d.p.autoencode ? b.jgrid.htmlEncode(a) : "" + a
                            },
                            n = function(a, c, e, f, g) {
                                var h = d.p.colModel[e];
                                return void 0 !== h.formatter ? (a = "" + d.p.idPrefix != "" ? b.jgrid.stripPref(d.p.idPrefix, a) : a, a = {
                                    "rowId": a,
                                    "colModel": h,
                                    "gid": d.p.id,
                                    "pos": e
                                }, c = b.isFunction(h.formatter) ? h.formatter.call(d, c, a, f, g) : b.fmatter ? b.fn.fmatter.call(d, h.formatter, c, a, f, g) : m(c)) : c = m(c), c
                            },
                            o = function(a, b, c, d, e, f) {
                                return b = n(a, b, c, e, "add"), '<td role="gridcell" ' + l(c, d, b, e, a, f) + ">" + b + "</td>"
                            },
                            p = function(a, b, c, e) {
                                return e = '<input role="checkbox" type="checkbox" id="jqg_' + d.p.id + "_" + a + '" class="cbox" name="jqg_' + d.p.id + "_" + a + '"' + (e ? 'checked="checked"' : "") + "/>", '<td role="gridcell" ' + l(b, c, "", null, a, !0) + ">" + e + "</td>"
                            },
                            q = function(a, b, c, d) {
                                return c = (parseInt(c, 10) - 1) * parseInt(d, 10) + 1 + b, '<td role="gridcell" class="ui-state-default jqgrid-rownum" ' + l(a, b, c, null, b, !0) + ">" + c + "</td>"
                            },
                            r = function(a) {
                                var b, c, e = [],
                                    f = 0;
                                for (c = 0; c < d.p.colModel.length; c++) b = d.p.colModel[c], "cb" !== b.name && "subgrid" !== b.name && "rn" !== b.name && (e[f] = "local" === a ? b.name : "xml" === a || "xmlstring" === a ? b.xmlmap || b.name : b.jsonmap || b.name, d.p.keyIndex !== !1 && b.key === !0 && (d.p.keyName = e[f]), f++);
                                return e
                            },
                            s = function(a) {
                                var c = d.p.remapColumns;
                                return c && c.length || (c = b.map(d.p.colModel, function(a, b) {
                                    return b
                                })), a && (c = b.map(c, function(b) {
                                    return a > b ? null : b - a
                                })), c
                            },
                            t = function(a, c) {
                                var d;
                                this.p.deepempty ? b(this.rows).slice(1).remove() : (d = this.rows.length > 0 ? this.rows[0] : null, b(this.firstChild).empty().append(d)), a && this.p.scroll && (b(this.grid.bDiv.firstChild).css({
                                    "height": "auto"
                                }), b(this.grid.bDiv.firstChild.firstChild).css({
                                    "height": 0,
                                    "display": "none"
                                }), 0 !== this.grid.bDiv.scrollTop && (this.grid.bDiv.scrollTop = 0)), c === !0 && this.p.treeGrid && (this.p.data = [], this.p._index = {})
                            },
                            u = function() {
                                var a, c, e, f = d.p.data.length;
                                for (a = d.p.rownumbers === !0 ? 1 : 0, c = d.p.multiselect === !0 ? 1 : 0, e = d.p.subGrid === !0 ? 1 : 0, a = d.p.keyIndex === !1 || d.p.loadonce === !0 ? d.p.localReader.id : d.p.colModel[d.p.keyIndex + c + e + a].name, c = 0; f > c; c++) e = b.jgrid.getAccessor(d.p.data[c], a), void 0 === e && (e = "" + (c + 1)), d.p._index[e] = c
                            },
                            v = function(a, c, e, f, g, h) {
                                var i, j = "-1",
                                    k = "",
                                    c = c ? "display:none;" : "",
                                    e = "ui-widget-content jqgrow ui-row-" + d.p.direction + (e ? " " + e : "") + (h ? " ui-state-highlight" : ""),
                                    h = b(d).triggerHandler("jqGridRowAttr", [f, g, a]);
                                if ("object" != typeof h && (h = b.isFunction(d.p.rowattr) ? d.p.rowattr.call(d, f, g, a) : {}), !b.isEmptyObject(h)) {
                                    h.hasOwnProperty("id") && (a = h.id, delete h.id), h.hasOwnProperty("tabindex") && (j = h.tabindex, delete h.tabindex), h.hasOwnProperty("style") && (c += h.style, delete h.style), h.hasOwnProperty("class") && (e += " " + h["class"], delete h["class"]);
                                    try {
                                        delete h.role
                                    } catch (l) {}
                                    for (i in h) h.hasOwnProperty(i) && (k += " " + i + "=" + h[i])
                                }
                                return '<tr role="row" id="' + a + '" tabindex="' + j + '" class="' + e + '"' + ("" === c ? "" : ' style="' + c + '"') + k + ">"
                            },
                            w = function(a, c, e, f, g) {
                                var h = new Date,
                                    i = "local" !== d.p.datatype && d.p.loadonce || "xmlstring" === d.p.datatype,
                                    j = d.p.xmlReader,
                                    l = "local" === d.p.datatype ? "local" : "xml";
                                if (i && (d.p.data = [], d.p._index = {}, d.p.localReader.id = "_id_"), d.p.reccount = 0, b.isXMLDoc(a)) {
                                    -1 !== d.p.treeANode || d.p.scroll ? e = e > 1 ? e : 1 : (t.call(d, !1, !0), e = 1);
                                    var m, n, u, w, x, y, z, A, B, C = b(d),
                                        D = 0,
                                        E = d.p.multiselect === !0 ? 1 : 0,
                                        F = 0,
                                        G = d.p.rownumbers === !0 ? 1 : 0,
                                        H = [],
                                        I = {},
                                        J = [],
                                        K = d.p.altRows === !0 ? d.p.altclass : "";
                                    d.p.subGrid === !0 && (F = 1, w = b.jgrid.getMethod("addSubGridCell")), j.repeatitems || (H = r(l)), x = d.p.keyIndex === !1 ? b.isFunction(j.id) ? j.id.call(d, a) : j.id : d.p.keyIndex, H.length > 0 && !isNaN(x) && (x = d.p.keyName), l = -1 === ("" + x).indexOf("[") ? H.length ? function(a, c) {
                                        return b(x, a).text() || c
                                    } : function(a, c) {
                                        return b(j.cell, a).eq(x).text() || c
                                    } : function(a, b) {
                                        return a.getAttribute(x.replace(/[\[\]]/g, "")) || b
                                    }, d.p.userData = {}, d.p.page = k(b.jgrid.getXmlData(a, j.page), d.p.page), d.p.lastpage = k(b.jgrid.getXmlData(a, j.total), 1), d.p.records = k(b.jgrid.getXmlData(a, j.records)), b.isFunction(j.userdata) ? d.p.userData = j.userdata.call(d, a) || {} : b.jgrid.getXmlData(a, j.userdata, !0).each(function() {
                                        d.p.userData[this.getAttribute("name")] = b(this).text()
                                    }), a = b.jgrid.getXmlData(a, j.root, !0), (a = b.jgrid.getXmlData(a, j.row, !0)) || (a = []);
                                    var L = a.length,
                                        M = 0,
                                        N = [],
                                        O = parseInt(d.p.rowNum, 10),
                                        P = d.p.scroll ? b.jgrid.randId() : 1;
                                    if (L > 0 && d.p.page <= 0 && (d.p.page = 1), a && L) {
                                        g && (O *= g + 1);
                                        var Q, g = b.isFunction(d.p.afterInsertRow),
                                            R = !1;
                                        for (d.p.grouping && (R = d.p.groupingView.groupCollapse === !0, Q = b.jgrid.getMethod("groupingPrepare")); L > M;) {
                                            z = a[M], A = l(z, P + M), A = d.p.idPrefix + A, m = 0 === e ? 0 : e + 1, B = (m + M) % 2 === 1 ? K : "";
                                            var S = J.length;
                                            if (J.push(""), G && J.push(q(0, M, d.p.page, d.p.rowNum)), E && J.push(p(A, G, M, !1)), F && J.push(w.call(C, E + G, M + e)), j.repeatitems) {
                                                y || (y = s(E + F + G));
                                                var T = b.jgrid.getXmlData(z, j.cell, !0);
                                                b.each(y, function(a) {
                                                    var b = T[this];
                                                    return b ? (u = b.textContent || b.text, I[d.p.colModel[a + E + F + G].name] = u, void J.push(o(A, u, a + E + F + G, M + e, z, I))) : !1
                                                })
                                            } else
                                                for (m = 0; m < H.length; m++) u = b.jgrid.getXmlData(z, H[m]), I[d.p.colModel[m + E + F + G].name] = u, J.push(o(A, u, m + E + F + G, M + e, z, I));
                                            if (J[S] = v(A, R, B, I, z, !1), J.push("</tr>"), d.p.grouping && (N = Q.call(C, J, N, I, M), J = []), (i || d.p.treeGrid === !0) && (I._id_ = b.jgrid.stripPref(d.p.idPrefix, A), d.p.data.push(I), d.p._index[I._id_] = d.p.data.length - 1), d.p.gridview === !1 && (b("tbody:first", c).append(J.join("")), C.triggerHandler("jqGridAfterInsertRow", [A, I, z]), g && d.p.afterInsertRow.call(d, A, I, z), J = []), I = {}, D++, M++, D === O) break
                                        }
                                    }
                                    if (d.p.gridview === !0 && (n = d.p.treeANode > -1 ? d.p.treeANode : 0, d.p.grouping ? (C.jqGrid("groupingRender", N, d.p.colModel.length), N = null) : d.p.treeGrid === !0 && n > 0 ? b(d.rows[n]).after(J.join("")) : b("tbody:first", c).append(J.join(""))), d.p.subGrid === !0) try {
                                        C.jqGrid("addSubGrid", E + G)
                                    } catch (U) {}
                                    if (d.p.totaltime = new Date - h, D > 0 && 0 === d.p.records && (d.p.records = L), J = null, d.p.treeGrid === !0) try {
                                        C.jqGrid("setTreeNode", n + 1, D + n + 1)
                                    } catch (V) {}
                                    if (d.p.treeGrid || d.p.scroll || (d.grid.bDiv.scrollTop = 0), d.p.reccount = D, d.p.treeANode = -1, d.p.userDataOnFooter && C.jqGrid("footerData", "set", d.p.userData, !0), i && (d.p.records = L, d.p.lastpage = Math.ceil(L / O)), f || d.updatepager(!1, !0), i)
                                        for (; L > D;) {
                                            if (z = a[D], A = l(z, D + P), A = d.p.idPrefix + A, j.repeatitems) {
                                                y || (y = s(E + F + G));
                                                var W = b.jgrid.getXmlData(z, j.cell, !0);
                                                b.each(y, function(a) {
                                                    var b = W[this];
                                                    return b ? (u = b.textContent || b.text, void(I[d.p.colModel[a + E + F + G].name] = u)) : !1
                                                })
                                            } else
                                                for (m = 0; m < H.length; m++) u = b.jgrid.getXmlData(z, H[m]), I[d.p.colModel[m + E + F + G].name] = u;
                                            I._id_ = b.jgrid.stripPref(d.p.idPrefix, A), d.p.data.push(I), d.p._index[I._id_] = d.p.data.length - 1, I = {}, D++
                                        }
                                }
                            },
                            x = function(a, c, e, f, g) {
                                var h = new Date;
                                if (a) {
                                    -1 !== d.p.treeANode || d.p.scroll ? e = e > 1 ? e : 1 : (t.call(d, !1, !0), e = 1);
                                    var i, j = "local" !== d.p.datatype && d.p.loadonce || "jsonstring" === d.p.datatype;
                                    j && (d.p.data = [], d.p._index = {}, d.p.localReader.id = "_id_"), d.p.reccount = 0, "local" === d.p.datatype ? (c = d.p.localReader, i = "local") : (c = d.p.jsonReader, i = "json");
                                    var l, m, n, u, w = b(d),
                                        x = 0,
                                        y = [],
                                        z = d.p.multiselect ? 1 : 0,
                                        A = d.p.subGrid === !0 ? 1 : 0,
                                        B = d.p.rownumbers === !0 ? 1 : 0,
                                        C = s(z + A + B);
                                    i = r(i);
                                    var D, E, F, G, H, I, J = {},
                                        K = [],
                                        L = d.p.altRows === !0 ? d.p.altclass : "";
                                    d.p.page = k(b.jgrid.getAccessor(a, c.page), d.p.page), d.p.lastpage = k(b.jgrid.getAccessor(a, c.total), 1), d.p.records = k(b.jgrid.getAccessor(a, c.records)), d.p.userData = b.jgrid.getAccessor(a, c.userdata) || {}, A && (u = b.jgrid.getMethod("addSubGridCell")), F = d.p.keyIndex === !1 ? b.isFunction(c.id) ? c.id.call(d, a) : c.id : d.p.keyIndex, c.repeatitems || (y = i, y.length > 0 && !isNaN(F) && (F = d.p.keyName)), E = b.jgrid.getAccessor(a, c.root), null == E && b.isArray(a) && (E = a), E || (E = []), a = E.length, m = 0, a > 0 && d.p.page <= 0 && (d.p.page = 1);
                                    var M, N = parseInt(d.p.rowNum, 10),
                                        O = d.p.scroll ? b.jgrid.randId() : 1,
                                        P = !1;
                                    g && (N *= g + 1), "local" === d.p.datatype && !d.p.deselectAfterSort && (P = !0);
                                    var Q, R = b.isFunction(d.p.afterInsertRow),
                                        S = [],
                                        T = !1;
                                    for (d.p.grouping && (T = d.p.groupingView.groupCollapse === !0, Q = b.jgrid.getMethod("groupingPrepare")); a > m;) {
                                        g = E[m], H = b.jgrid.getAccessor(g, F), void 0 === H && ("number" == typeof F && null != d.p.colModel[F + z + A + B] && (H = b.jgrid.getAccessor(g, d.p.colModel[F + z + A + B].name)), void 0 === H && (H = O + m, 0 === y.length && c.cell && (l = b.jgrid.getAccessor(g, c.cell) || g, H = null != l && void 0 !== l[F] ? l[F] : H))), H = d.p.idPrefix + H, l = 1 === e ? 0 : e, I = (l + m) % 2 === 1 ? L : "", P && (M = d.p.multiselect ? -1 !== b.inArray(H, d.p.selarrrow) : H === d.p.selrow);
                                        var U = K.length;
                                        for (K.push(""), B && K.push(q(0, m, d.p.page, d.p.rowNum)), z && K.push(p(H, B, m, M)), A && K.push(u.call(w, z + B, m + e)), D = i, c.repeatitems && (c.cell && (g = b.jgrid.getAccessor(g, c.cell) || g), b.isArray(g) && (D = C)), n = 0; n < D.length; n++) l = b.jgrid.getAccessor(g, D[n]), J[d.p.colModel[n + z + A + B].name] = l, K.push(o(H, l, n + z + A + B, m + e, g, J));
                                        if (K[U] = v(H, T, I, J, g, M), K.push("</tr>"), d.p.grouping && (S = Q.call(w, K, S, J, m), K = []), (j || d.p.treeGrid === !0) && (J._id_ = b.jgrid.stripPref(d.p.idPrefix, H), d.p.data.push(J), d.p._index[J._id_] = d.p.data.length - 1), d.p.gridview === !1 && (b("#" + b.jgrid.jqID(d.p.id) + " tbody:first").append(K.join("")), w.triggerHandler("jqGridAfterInsertRow", [H, J, g]), R && d.p.afterInsertRow.call(d, H, J, g), K = []), J = {}, x++, m++, x === N) break
                                    }
                                    if (d.p.gridview === !0 && (G = d.p.treeANode > -1 ? d.p.treeANode : 0, d.p.grouping ? w.jqGrid("groupingRender", S, d.p.colModel.length) : d.p.treeGrid === !0 && G > 0 ? b(d.rows[G]).after(K.join("")) : b("#" + b.jgrid.jqID(d.p.id) + " tbody:first").append(K.join(""))), d.p.subGrid === !0) try {
                                        w.jqGrid("addSubGrid", z + B)
                                    } catch (V) {}
                                    if (d.p.totaltime = new Date - h, x > 0 && 0 === d.p.records && (d.p.records = a), d.p.treeGrid === !0) try {
                                        w.jqGrid("setTreeNode", G + 1, x + G + 1)
                                    } catch (W) {}
                                    if (d.p.treeGrid || d.p.scroll || (d.grid.bDiv.scrollTop = 0), d.p.reccount = x, d.p.treeANode = -1, d.p.userDataOnFooter && w.jqGrid("footerData", "set", d.p.userData, !0), j && (d.p.records = a, d.p.lastpage = Math.ceil(a / N)), f || d.updatepager(!1, !0), j)
                                        for (; a > x && E[x];) {
                                            if (g = E[x], H = b.jgrid.getAccessor(g, F), void 0 === H && ("number" == typeof F && null != d.p.colModel[F + z + A + B] && (H = b.jgrid.getAccessor(g, d.p.colModel[F + z + A + B].name)), void 0 === H && (H = O + x, 0 === y.length && c.cell && (e = b.jgrid.getAccessor(g, c.cell) || g, H = null != e && void 0 !== e[F] ? e[F] : H))), g) {
                                                for (H = d.p.idPrefix + H, D = i, c.repeatitems && (c.cell && (g = b.jgrid.getAccessor(g, c.cell) || g), b.isArray(g) && (D = C)), n = 0; n < D.length; n++) J[d.p.colModel[n + z + A + B].name] = b.jgrid.getAccessor(g, D[n]);
                                                J._id_ = b.jgrid.stripPref(d.p.idPrefix, H), d.p.data.push(J), d.p._index[J._id_] = d.p.data.length - 1, J = {}
                                            }
                                            x++
                                        }
                                }
                            },
                            y = function() {
                                function a(b) {
                                    var c, d, e, f, g, h = 0;
                                    if (null != b.groups) {
                                        for ((d = b.groups.length && "OR" === b.groupOp.toString().toUpperCase()) && q.orBegin(), c = 0; c < b.groups.length; c++) {
                                            h > 0 && d && q.or();
                                            try {
                                                a(b.groups[c])
                                            } catch (i) {
                                                alert(i)
                                            }
                                            h++
                                        }
                                        d && q.orEnd()
                                    }
                                    if (null != b.rules) try {
                                        for ((e = b.rules.length && "OR" === b.groupOp.toString().toUpperCase()) && q.orBegin(), c = 0; c < b.rules.length; c++) g = b.rules[c], f = b.groupOp.toString().toUpperCase(), p[g.op] && g.field && (h > 0 && f && "OR" === f && (q = q.or()), q = p[g.op](q, f)(g.field, g.data, j[g.field])), h++;
                                        e && q.orEnd()
                                    } catch (k) {
                                        alert(k)
                                    }
                                }
                                var c, e, f, g = d.p.multiSort ? [] : "",
                                    h = [],
                                    i = !1,
                                    j = {},
                                    k = [],
                                    l = [];
                                if (b.isArray(d.p.data)) {
                                    var m, n, o = d.p.grouping ? d.p.groupingView : !1;
                                    if (b.each(d.p.colModel, function() {
                                            if (e = this.sorttype || "text", "date" === e || "datetime" === e ? (this.formatter && "string" == typeof this.formatter && "date" === this.formatter ? (c = this.formatoptions && this.formatoptions.srcformat ? this.formatoptions.srcformat : b.jgrid.formatter.date.srcformat, f = this.formatoptions && this.formatoptions.newformat ? this.formatoptions.newformat : b.jgrid.formatter.date.newformat) : c = f = this.datefmt || "Y-m-d", j[this.name] = {
                                                    "stype": e,
                                                    "srcfmt": c,
                                                    "newfmt": f
                                                }) : j[this.name] = {
                                                    "stype": e,
                                                    "srcfmt": "",
                                                    "newfmt": ""
                                                }, d.p.grouping)
                                                for (n = 0, m = o.groupField.length; m > n; n++)
                                                    if (this.name === o.groupField[n]) {
                                                        var a = this.name;
                                                        this.index && (a = this.index), k[n] = j[a], l[n] = a
                                                    }
                                            d.p.multiSort ? this.lso && (g.push(this.name), a = this.lso.split("-"), h.push(a[a.length - 1])) : i || this.index !== d.p.sortname && this.name !== d.p.sortname || (g = this.name, i = !0)
                                        }), !d.p.treeGrid) {
                                        var p = {
                                                "eq": function(a) {
                                                    return a.equals
                                                },
                                                "ne": function(a) {
                                                    return a.notEquals
                                                },
                                                "lt": function(a) {
                                                    return a.less
                                                },
                                                "le": function(a) {
                                                    return a.lessOrEquals
                                                },
                                                "gt": function(a) {
                                                    return a.greater
                                                },
                                                "ge": function(a) {
                                                    return a.greaterOrEquals
                                                },
                                                "cn": function(a) {
                                                    return a.contains
                                                },
                                                "nc": function(a, b) {
                                                    return "OR" === b ? a.orNot().contains : a.andNot().contains
                                                },
                                                "bw": function(a) {
                                                    return a.startsWith
                                                },
                                                "bn": function(a, b) {
                                                    return "OR" === b ? a.orNot().startsWith : a.andNot().startsWith
                                                },
                                                "en": function(a, b) {
                                                    return "OR" === b ? a.orNot().endsWith : a.andNot().endsWith
                                                },
                                                "ew": function(a) {
                                                    return a.endsWith
                                                },
                                                "ni": function(a, b) {
                                                    return "OR" === b ? a.orNot().equals : a.andNot().equals
                                                },
                                                "in": function(a) {
                                                    return a.equals
                                                },
                                                "nu": function(a) {
                                                    return a.isNull
                                                },
                                                "nn": function(a, b) {
                                                    return "OR" === b ? a.orNot().isNull : a.andNot().isNull
                                                }
                                            },
                                            q = b.jgrid.from(d.p.data);
                                        if (d.p.ignoreCase && (q = q.ignoreCase()), d.p.search === !0) {
                                            var r = d.p.postData.filters;
                                            if (r) "string" == typeof r && (r = b.jgrid.parse(r)), a(r);
                                            else try {
                                                q = p[d.p.postData.searchOper](q)(d.p.postData.searchField, d.p.postData.searchString, j[d.p.postData.searchField])
                                            } catch (s) {}
                                        }
                                        if (d.p.grouping)
                                            for (n = 0; m > n; n++) q.orderBy(l[n], o.groupOrder[n], k[n].stype, k[n].srcfmt);
                                        d.p.multiSort ? b.each(g, function(a) {
                                            q.orderBy(this, h[a], j[this].stype, j[this].srcfmt)
                                        }) : g && d.p.sortorder && i && ("DESC" === d.p.sortorder.toUpperCase() ? q.orderBy(d.p.sortname, "d", j[g].stype, j[g].srcfmt) : q.orderBy(d.p.sortname, "a", j[g].stype, j[g].srcfmt));
                                        var r = q.select(),
                                            t = parseInt(d.p.rowNum, 10),
                                            u = r.length,
                                            v = parseInt(d.p.page, 10),
                                            w = Math.ceil(u / t),
                                            x = {},
                                            r = r.slice((v - 1) * t, v * t),
                                            j = q = null;
                                        return x[d.p.localReader.total] = w, x[d.p.localReader.page] = v, x[d.p.localReader.records] = u, x[d.p.localReader.root] = r, x[d.p.localReader.userdata] = d.p.userData, r = null, x
                                    }
                                    b(d).jqGrid("SortTree", g, d.p.sortorder, j[g].stype || "text", j[g].srcfmt || "")
                                }
                            },
                            z = function() {
                                if (d.grid.hDiv.loading = !0, !d.p.hiddengrid) switch (d.p.loadui) {
                                    case "enable":
                                        b("#load_" + b.jgrid.jqID(d.p.id)).show();
                                        break;
                                    case "block":
                                        b("#lui_" + b.jgrid.jqID(d.p.id)).show(), b("#load_" + b.jgrid.jqID(d.p.id)).show()
                                }
                            },
                            A = function() {
                                switch (d.grid.hDiv.loading = !1, d.p.loadui) {
                                    case "enable":
                                        b("#load_" + b.jgrid.jqID(d.p.id)).hide();
                                        break;
                                    case "block":
                                        b("#lui_" + b.jgrid.jqID(d.p.id)).hide(), b("#load_" + b.jgrid.jqID(d.p.id)).hide()
                                }
                            },
                            B = function(a) {
                                if (!d.grid.hDiv.loading) {
                                    var c, e = d.p.scroll && a === !1,
                                        f = {},
                                        g = d.p.prmNames;
                                    d.p.page <= 0 && (d.p.page = Math.min(1, d.p.lastpage)), null !== g.search && (f[g.search] = d.p.search), null !== g.nd && (f[g.nd] = (new Date).getTime()), null !== g.rows && (f[g.rows] = d.p.rowNum), null !== g.page && (f[g.page] = d.p.page), null !== g.sort && (f[g.sort] = d.p.sortname), null !== g.order && (f[g.order] = d.p.sortorder), null !== d.p.rowTotal && null !== g.totalrows && (f[g.totalrows] = d.p.rowTotal);
                                    var h = b.isFunction(d.p.loadComplete),
                                        i = h ? d.p.loadComplete : null,
                                        j = 0,
                                        a = a || 1;
                                    if (a > 1 ? null !== g.npage ? (f[g.npage] = a, j = a - 1, a = 1) : i = function(b) {
                                            d.p.page++, d.grid.hDiv.loading = !1, h && d.p.loadComplete.call(d, b), B(a - 1)
                                        } : null !== g.npage && delete d.p.postData[g.npage], d.p.grouping) {
                                        b(d).jqGrid("groupingSetup");
                                        var k, l = d.p.groupingView,
                                            m = "";
                                        for (k = 0; k < l.groupField.length; k++) {
                                            var n = l.groupField[k];
                                            b.each(d.p.colModel, function(a, b) {
                                                b.name === n && b.index && (n = b.index)
                                            }), m += n + " " + l.groupOrder[k] + ", "
                                        }
                                        f[g.sort] = m + f[g.sort]
                                    }
                                    b.extend(d.p.postData, f);
                                    var o = d.p.scroll ? d.rows.length - 1 : 1,
                                        f = b(d).triggerHandler("jqGridBeforeRequest");
                                    if (f !== !1 && "stop" !== f)
                                        if (b.isFunction(d.p.datatype)) d.p.datatype.call(d, d.p.postData, "load_" + d.p.id, o, a, j);
                                        else {
                                            if (b.isFunction(d.p.beforeRequest) && (f = d.p.beforeRequest.call(d), void 0 === f && (f = !0), f === !1)) return;
                                            switch (c = d.p.datatype.toLowerCase()) {
                                                case "json":
                                                case "jsonp":
                                                case "xml":
                                                case "script":
                                                    b.ajax(b.extend({
                                                        "url": d.p.url,
                                                        "type": d.p.mtype,
                                                        "dataType": c,
                                                        "data": b.isFunction(d.p.serializeGridData) ? d.p.serializeGridData.call(d, d.p.postData) : d.p.postData,
                                                        "success": function(f, g, h) {
                                                            b.isFunction(d.p.beforeProcessing) && d.p.beforeProcessing.call(d, f, g, h) === !1 ? A() : ("xml" === c ? w(f, d.grid.bDiv, o, a > 1, j) : x(f, d.grid.bDiv, o, a > 1, j), b(d).triggerHandler("jqGridLoadComplete", [f]), i && i.call(d, f), b(d).triggerHandler("jqGridAfterLoadComplete", [f]), e && d.grid.populateVisible(), (d.p.loadonce || d.p.treeGrid) && (d.p.datatype = "local"), 1 === a && A())
                                                        },
                                                        "error": function(c, e, f) {
                                                            b.isFunction(d.p.loadError) && d.p.loadError.call(d, c, e, f), 1 === a && A()
                                                        },
                                                        "beforeSend": function(a, c) {
                                                            var e = !0;
                                                            return b.isFunction(d.p.loadBeforeSend) && (e = d.p.loadBeforeSend.call(d, a, c)), void 0 === e && (e = !0), e === !1 ? !1 : void z()
                                                        }
                                                    }, b.jgrid.ajaxOptions, d.p.ajaxGridOptions));
                                                    break;
                                                case "xmlstring":
                                                    z(), f = "string" != typeof d.p.datastr ? d.p.datastr : b.parseXML(d.p.datastr), w(f, d.grid.bDiv), b(d).triggerHandler("jqGridLoadComplete", [f]), h && d.p.loadComplete.call(d, f), b(d).triggerHandler("jqGridAfterLoadComplete", [f]), d.p.datatype = "local", d.p.datastr = null, A();
                                                    break;
                                                case "jsonstring":
                                                    z(), f = "string" == typeof d.p.datastr ? b.jgrid.parse(d.p.datastr) : d.p.datastr, x(f, d.grid.bDiv), b(d).triggerHandler("jqGridLoadComplete", [f]), h && d.p.loadComplete.call(d, f), b(d).triggerHandler("jqGridAfterLoadComplete", [f]), d.p.datatype = "local", d.p.datastr = null, A();
                                                    break;
                                                case "local":
                                                case "clientside":
                                                    z(), d.p.datatype = "local", f = y(), x(f, d.grid.bDiv, o, a > 1, j), b(d).triggerHandler("jqGridLoadComplete", [f]), i && i.call(d, f), b(d).triggerHandler("jqGridAfterLoadComplete", [f]), e && d.grid.populateVisible(), A()
                                            }
                                        }
                                }
                            },
                            C = function(a) {
                                b("#cb_" + b.jgrid.jqID(d.p.id), d.grid.hDiv)[d.p.useProp ? "prop" : "attr"]("checked", a), d.p.frozenColumns && b("#cb_" + b.jgrid.jqID(d.p.id), d.grid.fhDiv)[d.p.useProp ? "prop" : "attr"]("checked", a)
                            },
                            D = function(a, c) {
                                var e, f, h, i, j = "",
                                    l = "<table cellspacing='0' cellpadding='0' border='0' style='table-layout:auto;' class='ui-pg-table'><tbody><tr>",
                                    m = "",
                                    n = function(a) {
                                        var c;
                                        return b.isFunction(d.p.onPaging) && (c = d.p.onPaging.call(d, a)), "stop" === c ? !1 : (d.p.selrow = null, d.p.multiselect && (d.p.selarrrow = [], C(!1)), d.p.savedRow = [], !0)
                                    },
                                    a = a.substr(1),
                                    c = c + ("_" + a);
                                if (e = "pg_" + a, f = a + "_left", h = a + "_center", i = a + "_right", b("#" + b.jgrid.jqID(a)).append("<div id='" + e + "' class='ui-pager-control' role='group'><table cellspacing='0' cellpadding='0' border='0' class='ui-pg-table' style='width:100%;table-layout:fixed;height:100%;' role='row'><tbody><tr><td id='" + f + "' align='left'></td><td id='" + h + "' align='center' style='white-space:pre;'></td><td id='" + i + "' align='right'></td></tr></tbody></table></div>").attr("dir", "ltr"), d.p.rowList.length > 0) {
                                    for (m = "<td dir='" + g + "'>", m += "<select class='ui-pg-selbox' role='listbox'>", f = 0; f < d.p.rowList.length; f++) m += '<option role="option" value="' + d.p.rowList[f] + '"' + (d.p.rowNum === d.p.rowList[f] ? ' selected="selected"' : "") + ">" + d.p.rowList[f] + "</option>";
                                    m += "</select></td>"
                                }
                                "rtl" === g && (l += m), d.p.pginput === !0 && (j = "<td dir='" + g + "'>" + b.jgrid.format(d.p.pgtext || "", "<input class='ui-pg-input' type='text' size='2' maxlength='7' value='0' role='textbox'/>", "<span id='sp_1_" + b.jgrid.jqID(a) + "'></span>") + "</td>"), d.p.pgbuttons === !0 ? (f = ["first" + c, "prev" + c, "next" + c, "last" + c], "rtl" === g && f.reverse(), l += "<td id='" + f[0] + "' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-first'></span></td>", l += "<td id='" + f[1] + "' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-prev'></span></td>", l = l + ("" !== j ? "<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='ui-separator'></span></td>" + j + "<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='ui-separator'></span></td>" : "") + ("<td id='" + f[2] + "' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-next'></span></td>"), l += "<td id='" + f[3] + "' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-end'></span></td>") : "" !== j && (l += j), "ltr" === g && (l += m), l += "</tr></tbody></table>", d.p.viewrecords === !0 && b("td#" + a + "_" + d.p.recordpos, "#" + e).append("<div dir='" + g + "' style='text-align:" + d.p.recordpos + "' class='ui-paging-info'></div>"), b("td#" + a + "_" + d.p.pagerpos, "#" + e).append(l), m = b(".ui-jqgrid").css("font-size") || "11px", b(document.body).append("<div id='testpg' class='ui-jqgrid ui-widget ui-widget-content' style='font-size:" + m + ";visibility:hidden;' ></div>"), l = b(l).clone().appendTo("#testpg").width(), b("#testpg").remove(), l > 0 && ("" !== j && (l += 50), b("td#" + a + "_" + d.p.pagerpos, "#" + e).width(l)), d.p._nvtd = [], d.p._nvtd[0] = Math.floor(l ? (d.p.width - l) / 2 : d.p.width / 3), d.p._nvtd[1] = 0, l = null, b(".ui-pg-selbox", "#" + e).bind("change", function() {
                                    return n("records") ? (d.p.page = Math.round(d.p.rowNum * (d.p.page - 1) / this.value - .5) + 1, d.p.rowNum = this.value, d.p.pager && b(".ui-pg-selbox", d.p.pager).val(this.value), d.p.toppager && b(".ui-pg-selbox", d.p.toppager).val(this.value), B(), !1) : !1
                                }), d.p.pgbuttons === !0 && (b(".ui-pg-button", "#" + e).hover(function() {
                                    b(this).hasClass("ui-state-disabled") ? this.style.cursor = "default" : (b(this).addClass("ui-state-hover"), this.style.cursor = "pointer")
                                }, function() {
                                    b(this).hasClass("ui-state-disabled") || (b(this).removeClass("ui-state-hover"), this.style.cursor = "default")
                                }), b("#first" + b.jgrid.jqID(c) + ", #prev" + b.jgrid.jqID(c) + ", #next" + b.jgrid.jqID(c) + ", #last" + b.jgrid.jqID(c)).click(function() {
                                    var a = k(d.p.page, 1),
                                        b = k(d.p.lastpage, 1),
                                        e = !1,
                                        f = !0,
                                        g = !0,
                                        h = !0,
                                        i = !0;
                                    return 0 === b || 1 === b ? i = h = g = f = !1 : b > 1 && a >= 1 ? 1 === a ? g = f = !1 : a === b && (i = h = !1) : b > 1 && 0 === a && (i = h = !1, a = b - 1), n(this.id) ? (this.id === "first" + c && f && (d.p.page = 1, e = !0), this.id === "prev" + c && g && (d.p.page = a - 1, e = !0), this.id === "next" + c && h && (d.p.page = a + 1, e = !0), this.id === "last" + c && i && (d.p.page = b, e = !0), e && B(), !1) : !1
                                })), d.p.pginput === !0 && b("input.ui-pg-input", "#" + e).keypress(function(a) {
                                    return 13 === (a.charCode || a.keyCode || 0) ? n("user") ? (b(this).val(k(b(this).val(), 1)), d.p.page = b(this).val() > 0 ? b(this).val() : d.p.page, B(), !1) : !1 : this
                                })
                            },
                            E = function(a, c) {
                                var e, f, g = "",
                                    h = d.p.colModel,
                                    i = !1;
                                f = d.p.frozenColumns ? c : d.grid.headers[a].el;
                                var j = "";
                                b("span.ui-grid-ico-sort", f).addClass("ui-state-disabled"), b(f).attr("aria-selected", "false"), h[a].lso ? "asc" === h[a].lso ? (h[a].lso = h[a].lso + "-desc", j = "desc") : "desc" === h[a].lso ? (h[a].lso = h[a].lso + "-asc", j = "asc") : ("asc-desc" === h[a].lso || "desc-asc" === h[a].lso) && (h[a].lso = "") : h[a].lso = j = h[a].firstsortorder || "asc", j ? (b("span.s-ico", f).show(), b("span.ui-icon-" + j, f).removeClass("ui-state-disabled"), b(f).attr("aria-selected", "true")) : d.p.viewsortcols[0] || b("span.s-ico", f).hide(), d.p.sortorder = "", b.each(h, function(a) {
                                    this.lso && (a > 0 && i && (g += ", "), e = this.lso.split("-"), g += h[a].index || h[a].name, g += " " + e[e.length - 1], i = !0, d.p.sortorder = e[e.length - 1])
                                }), f = g.lastIndexOf(d.p.sortorder), g = g.substring(0, f), d.p.sortname = g
                            },
                            F = function(a, c, e, f, g) {
                                if (d.p.colModel[c].sortable && !(d.p.savedRow.length > 0)) {
                                    if (e || (d.p.lastsort === c ? "asc" === d.p.sortorder ? d.p.sortorder = "desc" : "desc" === d.p.sortorder && (d.p.sortorder = "asc") : d.p.sortorder = d.p.colModel[c].firstsortorder || "asc", d.p.page = 1), d.p.multiSort) E(c, g);
                                    else {
                                        if (f) {
                                            if (d.p.lastsort === c && d.p.sortorder === f && !e) return;
                                            d.p.sortorder = f
                                        }
                                        e = d.grid.headers[d.p.lastsort].el, g = d.p.frozenColumns ? g : d.grid.headers[c].el, b("span.ui-grid-ico-sort", e).addClass("ui-state-disabled"), b(e).attr("aria-selected", "false"), d.p.frozenColumns && (d.grid.fhDiv.find("span.ui-grid-ico-sort").addClass("ui-state-disabled"), d.grid.fhDiv.find("th").attr("aria-selected", "false")), b("span.ui-icon-" + d.p.sortorder, g).removeClass("ui-state-disabled"), b(g).attr("aria-selected", "true"), d.p.viewsortcols[0] || d.p.lastsort === c || (d.p.frozenColumns && d.grid.fhDiv.find("span.s-ico").hide(), b("span.s-ico", e).hide(), b("span.s-ico", g).show()), a = a.substring(5 + d.p.id.length + 1), d.p.sortname = d.p.colModel[c].index || a
                                    }
                                    "stop" === b(d).triggerHandler("jqGridSortCol", [d.p.sortname, c, d.p.sortorder]) ? d.p.lastsort = c : b.isFunction(d.p.onSortCol) && "stop" === d.p.onSortCol.call(d, d.p.sortname, c, d.p.sortorder) ? d.p.lastsort = c : ("local" === d.p.datatype ? d.p.deselectAfterSort && b(d).jqGrid("resetSelection") : (d.p.selrow = null, d.p.multiselect && C(!1), d.p.selarrrow = [], d.p.savedRow = []), d.p.scroll && (g = d.grid.bDiv.scrollLeft, t.call(d, !0, !1), d.grid.hDiv.scrollLeft = g), d.p.subGrid && "local" === d.p.datatype && b("td.sgexpanded", "#" + b.jgrid.jqID(d.p.id)).each(function() {
                                        b(this).trigger("click")
                                    }), B(), d.p.lastsort = c, d.p.sortname !== a && c && (d.p.lastsort = c))
                                }
                            },
                            G = function(a) {
                                return a = b(d.grid.headers[a].el), a = [a.position().left + a.outerWidth()], "rtl" === d.p.direction && (a[0] = d.p.width - a[0]), a[0] = a[0] - d.grid.bDiv.scrollLeft, a.push(b(d.grid.hDiv).position().top), a.push(b(d.grid.bDiv).offset().top - b(d.grid.hDiv).offset().top + b(d.grid.bDiv).height()), a
                            },
                            H = function(a) {
                                var c, e = d.grid.headers,
                                    f = b.jgrid.getCellIndex(a);
                                for (c = 0; c < e.length; c++)
                                    if (a === e[c].el) {
                                        f = c;
                                        break
                                    }
                                return f
                            };
                        for (this.p.id = this.id, -1 === b.inArray(d.p.multikey, ["shiftKey", "altKey", "ctrlKey"]) && (d.p.multikey = !1), d.p.keyIndex = !1, d.p.keyName = !1, f = 0; f < d.p.colModel.length; f++) d.p.colModel[f] = b.extend(!0, {}, d.p.cmTemplate, d.p.colModel[f].template || {}, d.p.colModel[f]), !1 === d.p.keyIndex && !0 === d.p.colModel[f].key && (d.p.keyIndex = f);
                        if (d.p.sortorder = d.p.sortorder.toLowerCase(), b.jgrid.cell_width = b.jgrid.cellWidth(), !0 === d.p.grouping && (d.p.scroll = !1, d.p.rownumbers = !1, d.p.treeGrid = !1, d.p.gridview = !0), !0 === this.p.treeGrid) {
                            try {
                                b(this).jqGrid("setTreeGrid")
                            } catch (I) {}
                            "local" !== d.p.datatype && (d.p.localReader = {
                                "id": "_id_"
                            })
                        }
                        if (this.p.subGrid) try {
                            b(d).jqGrid("setSubGrid")
                        } catch (J) {}
                        this.p.multiselect && (this.p.colNames.unshift("<input role='checkbox' id='cb_" + this.p.id + "' class='cbox' type='checkbox'/>"), this.p.colModel.unshift({
                            "name": "cb",
                            "width": b.jgrid.cell_width ? d.p.multiselectWidth + d.p.cellLayout : d.p.multiselectWidth,
                            "sortable": !1,
                            "resizable": !1,
                            "hidedlg": !0,
                            "search": !1,
                            "align": "center",
                            "fixed": !0
                        })), this.p.rownumbers && (this.p.colNames.unshift(""), this.p.colModel.unshift({
                            "name": "rn",
                            "width": d.p.rownumWidth,
                            "sortable": !1,
                            "resizable": !1,
                            "hidedlg": !0,
                            "search": !1,
                            "align": "center",
                            "fixed": !0
                        })), d.p.xmlReader = b.extend(!0, {
                            "root": "rows",
                            "row": "row",
                            "page": "rows>page",
                            "total": "rows>total",
                            "records": "rows>records",
                            "repeatitems": !0,
                            "cell": "cell",
                            "id": "[id]",
                            "userdata": "userdata",
                            "subgrid": {
                                "root": "rows",
                                "row": "row",
                                "repeatitems": !0,
                                "cell": "cell"
                            }
                        }, d.p.xmlReader), d.p.jsonReader = b.extend(!0, {
                            "root": "rows",
                            "page": "page",
                            "total": "total",
                            "records": "records",
                            "repeatitems": !0,
                            "cell": "cell",
                            "id": "id",
                            "userdata": "userdata",
                            "subgrid": {
                                "root": "rows",
                                "repeatitems": !0,
                                "cell": "cell"
                            }
                        }, d.p.jsonReader), d.p.localReader = b.extend(!0, {
                            "root": "rows",
                            "page": "page",
                            "total": "total",
                            "records": "records",
                            "repeatitems": !1,
                            "cell": "cell",
                            "id": "id",
                            "userdata": "userdata",
                            "subgrid": {
                                "root": "rows",
                                "repeatitems": !0,
                                "cell": "cell"
                            }
                        }, d.p.localReader), d.p.scroll && (d.p.pgbuttons = !1, d.p.pginput = !1, d.p.rowList = []), d.p.data.length && u();
                        var K, L, M, N, O, P, Q, R, S = "<thead><tr class='ui-jqgrid-labels' role='rowheader'>",
                            T = R = "",
                            U = [],
                            V = [];
                        if (L = [], !0 === d.p.shrinkToFit && !0 === d.p.forceFit)
                            for (f = d.p.colModel.length - 1; f >= 0; f--)
                                if (!d.p.colModel[f].hidden) {
                                    d.p.colModel[f].resizable = !1;
                                    break
                                }
                        if ("horizontal" === d.p.viewsortcols[1] && (R = " ui-i-asc", T = " ui-i-desc"), K = i ? "class='ui-th-div-ie'" : "", R = "<span class='s-ico' style='display:none'><span sort='asc' class='ui-grid-ico-sort ui-icon-asc" + R + " ui-state-disabled ui-icon ui-icon-triangle-1-n ui-sort-" + g + "'></span>" + ("<span sort='desc' class='ui-grid-ico-sort ui-icon-desc" + T + " ui-state-disabled ui-icon ui-icon-triangle-1-s ui-sort-" + g + "'></span></span>"), d.p.multiSort)
                            for (U = d.p.sortname.split(","), f = 0; f < U.length; f++) L = b.trim(U[f]).split(" "), U[f] = b.trim(L[0]), V[f] = L[1] ? b.trim(L[1]) : d.p.sortorder || "asc";
                        for (f = 0; f < this.p.colNames.length; f++) L = d.p.headertitles ? ' title="' + b.jgrid.stripHtml(d.p.colNames[f]) + '"' : "", S += "<th id='" + d.p.id + "_" + d.p.colModel[f].name + "' role='columnheader' class='ui-state-default ui-th-column ui-th-" + g + "'" + L + ">", L = d.p.colModel[f].index || d.p.colModel[f].name, S += "<div id='jqgh_" + d.p.id + "_" + d.p.colModel[f].name + "' " + K + ">" + d.p.colNames[f], d.p.colModel[f].width = d.p.colModel[f].width ? parseInt(d.p.colModel[f].width, 10) : 150, "boolean" != typeof d.p.colModel[f].title && (d.p.colModel[f].title = !0), d.p.colModel[f].lso = "", L === d.p.sortname && (d.p.lastsort = f), d.p.multiSort && (L = b.inArray(L, U), -1 !== L && (d.p.colModel[f].lso = V[L])), S += R + "</div></th>";
                        if (R = null, b(this).append(S + "</tr></thead>"), b("thead tr:first th", this).hover(function() {
                                b(this).addClass("ui-state-hover")
                            }, function() {
                                b(this).removeClass("ui-state-hover")
                            }), this.p.multiselect) {
                            var W, X = [];
                            b("#cb_" + b.jgrid.jqID(d.p.id), this).bind("click", function() {
                                d.p.selarrrow = [];
                                var a = d.p.frozenColumns === !0 ? d.p.id + "_frozen" : "";
                                this.checked ? (b(d.rows).each(function(c) {
                                    c > 0 && !b(this).hasClass("ui-subgrid") && !b(this).hasClass("jqgroup") && !b(this).hasClass("ui-state-disabled") && (b("#jqg_" + b.jgrid.jqID(d.p.id) + "_" + b.jgrid.jqID(this.id))[d.p.useProp ? "prop" : "attr"]("checked", !0), b(this).addClass("ui-state-highlight").attr("aria-selected", "true"), d.p.selarrrow.push(this.id), d.p.selrow = this.id, a && (b("#jqg_" + b.jgrid.jqID(d.p.id) + "_" + b.jgrid.jqID(this.id), d.grid.fbDiv)[d.p.useProp ? "prop" : "attr"]("checked", !0), b("#" + b.jgrid.jqID(this.id), d.grid.fbDiv).addClass("ui-state-highlight")))
                                }), W = !0, X = []) : (b(d.rows).each(function(c) {
                                    c > 0 && !b(this).hasClass("ui-subgrid") && !b(this).hasClass("ui-state-disabled") && (b("#jqg_" + b.jgrid.jqID(d.p.id) + "_" + b.jgrid.jqID(this.id))[d.p.useProp ? "prop" : "attr"]("checked", !1), b(this).removeClass("ui-state-highlight").attr("aria-selected", "false"), X.push(this.id), a && (b("#jqg_" + b.jgrid.jqID(d.p.id) + "_" + b.jgrid.jqID(this.id), d.grid.fbDiv)[d.p.useProp ? "prop" : "attr"]("checked", !1), b("#" + b.jgrid.jqID(this.id), d.grid.fbDiv).removeClass("ui-state-highlight")))
                                }), d.p.selrow = null, W = !1), b(d).triggerHandler("jqGridSelectAll", [W ? d.p.selarrrow : X, W]), b.isFunction(d.p.onSelectAll) && d.p.onSelectAll.call(d, W ? d.p.selarrrow : X, W)
                            })
                        }!0 === d.p.autowidth && (S = b(j).innerWidth(), d.p.width = S > 0 ? S : "nw"),
                            function() {
                                var a, c, f, g, h = 0,
                                    i = b.jgrid.cell_width ? 0 : k(d.p.cellLayout, 0),
                                    j = 0,
                                    l = k(d.p.scrollOffset, 0),
                                    m = !1,
                                    n = 0;
                                b.each(d.p.colModel, function() {
                                    if (void 0 === this.hidden && (this.hidden = !1), d.p.grouping && d.p.autowidth) {
                                        var a = b.inArray(this.name, d.p.groupingView.groupField);
                                        a >= 0 && d.p.groupingView.groupColumnShow.length > a && (this.hidden = !d.p.groupingView.groupColumnShow[a])
                                    }
                                    this.widthOrg = c = k(this.width, 0), this.hidden === !1 && (h += c + i, this.fixed ? n += c + i : j++)
                                }), isNaN(d.p.width) && (d.p.width = h + (d.p.shrinkToFit !== !1 || isNaN(d.p.height) ? 0 : l)), e.width = d.p.width, d.p.tblwidth = h, d.p.shrinkToFit === !1 && d.p.forceFit === !0 && (d.p.forceFit = !1), d.p.shrinkToFit === !0 && j > 0 && (f = e.width - i * j - n, isNaN(d.p.height) || (f -= l, m = !0), h = 0, b.each(d.p.colModel, function(b) {
                                    this.hidden !== !1 || this.fixed || (this.width = c = Math.round(f * this.width / (d.p.tblwidth - i * j - n)), h += c, a = b)
                                }), g = 0, m ? e.width - n - (h + i * j) !== l && (g = e.width - n - (h + i * j) - l) : !m && 1 !== Math.abs(e.width - n - (h + i * j)) && (g = e.width - n - (h + i * j)), d.p.colModel[a].width = d.p.colModel[a].width + g, d.p.tblwidth = h + g + i * j + n, d.p.tblwidth > d.p.width && (d.p.colModel[a].width = d.p.colModel[a].width - (d.p.tblwidth - parseInt(d.p.width, 10)), d.p.tblwidth = d.p.width))
                            }(), b(j).css("width", e.width + "px").append("<div class='ui-jqgrid-resize-mark' id='rs_m" + d.p.id + "'>&#160;</div>"), b(h).css("width", e.width + "px");
                        var S = b("thead:first", d).get(0),
                            Y = "";
                        d.p.footerrow && (Y += "<table role='grid' style='width:" + d.p.tblwidth + "px' class='ui-jqgrid-ftable' cellspacing='0' cellpadding='0' border='0'><tbody><tr role='row' class='ui-widget-content footrow footrow-" + g + "'>");
                        var h = b("tr:first", S),
                            Z = "<tr class='jqgfirstrow' role='row' style='height:auto'>";
                        if (d.p.disableClick = !1, b("th", h).each(function(a) {
                                M = d.p.colModel[a].width, void 0 === d.p.colModel[a].resizable && (d.p.colModel[a].resizable = !0), d.p.colModel[a].resizable ? (N = document.createElement("span"), b(N).html("&#160;").addClass("ui-jqgrid-resize ui-jqgrid-resize-" + g).css("cursor", "col-resize"), b(this).addClass(d.p.resizeclass)) : N = "", b(this).css("width", M + "px").prepend(N), N = null;
                                var c = "";
                                d.p.colModel[a].hidden && (b(this).css("display", "none"), c = "display:none;"), Z += "<td role='gridcell' style='height:0px;width:" + M + "px;" + c + "'></td>", e.headers[a] = {
                                    "width": M,
                                    "el": this
                                }, O = d.p.colModel[a].sortable, "boolean" != typeof O && (O = d.p.colModel[a].sortable = !0), c = d.p.colModel[a].name, "cb" === c || "subgrid" === c || "rn" === c || d.p.viewsortcols[2] && b(">div", this).addClass("ui-jqgrid-sortable"), O && (d.p.multiSort ? d.p.viewsortcols[0] ? (b("div span.s-ico", this).show(), d.p.colModel[a].lso && b("div span.ui-icon-" + d.p.colModel[a].lso, this).removeClass("ui-state-disabled")) : d.p.colModel[a].lso && (b("div span.s-ico", this).show(), b("div span.ui-icon-" + d.p.colModel[a].lso, this).removeClass("ui-state-disabled")) : d.p.viewsortcols[0] ? (b("div span.s-ico", this).show(), a === d.p.lastsort && b("div span.ui-icon-" + d.p.sortorder, this).removeClass("ui-state-disabled")) : a === d.p.lastsort && (b("div span.s-ico", this).show(), b("div span.ui-icon-" + d.p.sortorder, this).removeClass("ui-state-disabled"))), d.p.footerrow && (Y += "<td role='gridcell' " + l(a, 0, "", null, "", !1) + ">&#160;</td>")
                            }).mousedown(function(a) {
                                if (1 === b(a.target).closest("th>span.ui-jqgrid-resize").length) {
                                    var c = H(this);
                                    if (d.p.forceFit === !0) {
                                        var f, g = d.p,
                                            h = c;
                                        for (f = c + 1; f < d.p.colModel.length; f++)
                                            if (d.p.colModel[f].hidden !== !0) {
                                                h = f;
                                                break
                                            }
                                        g.nv = h - c
                                    }
                                    return e.dragStart(c, a, G(c)), !1
                                }
                            }).click(function(a) {
                                if (d.p.disableClick) return d.p.disableClick = !1;
                                var c, e, f = "th>div.ui-jqgrid-sortable";
                                if (d.p.viewsortcols[2] || (f = "th>div>span>span.ui-grid-ico-sort"), a = b(a.target).closest(f), 1 === a.length) {
                                    var g;
                                    if (d.p.frozenColumns) {
                                        var h = b(this)[0].id.substring(d.p.id.length + 1);
                                        b(d.p.colModel).each(function(a) {
                                            return this.name === h ? (g = a, !1) : void 0
                                        })
                                    } else g = H(this);
                                    return d.p.viewsortcols[2] || (c = !0, e = a.attr("sort")), null != g && F(b("div", this)[0].id, g, c, e, this), !1
                                }
                            }), d.p.sortable && b.fn.sortable) try {
                            b(d).jqGrid("sortableColumns", h)
                        } catch ($) {}
                        d.p.footerrow && (Y += "</tr></tbody></table>"), Z += "</tr>", this.appendChild(document.createElement("tbody")), b(this).addClass("ui-jqgrid-btable").append(Z);
                        var Z = null,
                            h = b("<table class='ui-jqgrid-htable' style='width:" + d.p.tblwidth + "px' role='grid' aria-labelledby='gbox_" + this.id + "' cellspacing='0' cellpadding='0' border='0'></table>").append(S),
                            _ = d.p.caption && !0 === d.p.hiddengrid ? !0 : !1;
                        f = b("<div class='ui-jqgrid-hbox" + ("rtl" === g ? "-rtl" : "") + "'></div>"), S = null, e.hDiv = document.createElement("div"), b(e.hDiv).css({
                            "width": e.width + "px"
                        }).addClass("ui-state-default ui-jqgrid-hdiv").append(f), b(f).append(h), h = null, _ && b(e.hDiv).hide(), d.p.pager && ("string" == typeof d.p.pager ? "#" !== d.p.pager.substr(0, 1) && (d.p.pager = "#" + d.p.pager) : d.p.pager = "#" + b(d.p.pager).attr("id"), b(d.p.pager).css({
                            "width": e.width + "px"
                        }).addClass("ui-state-default ui-jqgrid-pager ui-corner-bottom").appendTo(j), _ && b(d.p.pager).hide(), D(d.p.pager, "")), !1 === d.p.cellEdit && !0 === d.p.hoverrows && b(d).bind("mouseover", function(a) {
                            Q = b(a.target).closest("tr.jqgrow"), "ui-subgrid" !== b(Q).attr("class") && b(Q).addClass("ui-state-hover")
                        }).bind("mouseout", function(a) {
                            Q = b(a.target).closest("tr.jqgrow"), b(Q).removeClass("ui-state-hover")
                        });
                        var ab, bb, cb;
                        b(d).before(e.hDiv).click(function(a) {
                            if (P = a.target, Q = b(P, d.rows).closest("tr.jqgrow"), 0 === b(Q).length || Q[0].className.indexOf("ui-state-disabled") > -1 || (b(P, d).closest("table.ui-jqgrid-btable").attr("id") || "").replace("_frozen", "") !== d.id) return this;
                            var c = b(P).hasClass("cbox"),
                                e = b(d).triggerHandler("jqGridBeforeSelectRow", [Q[0].id, a]);
                            if ((e = e === !1 || "stop" === e ? !1 : !0) && b.isFunction(d.p.beforeSelectRow) && (e = d.p.beforeSelectRow.call(d, Q[0].id, a)), "A" !== P.tagName && ("INPUT" !== P.tagName && "TEXTAREA" !== P.tagName && "OPTION" !== P.tagName && "SELECT" !== P.tagName || c) && e === !0)
                                if (ab = Q[0].id, bb = b.jgrid.getCellIndex(P), cb = b(P).closest("td,th").html(), b(d).triggerHandler("jqGridCellSelect", [ab, bb, cb, a]), b.isFunction(d.p.onCellSelect) && d.p.onCellSelect.call(d, ab, bb, cb, a), d.p.cellEdit === !0)
                                    if (d.p.multiselect && c) b(d).jqGrid("setSelection", ab, !0, a);
                                    else {
                                        ab = Q[0].rowIndex;
                                        try {
                                            b(d).jqGrid("editCell", ab, bb, !0)
                                        } catch (f) {}
                                    } else if (d.p.multikey) a[d.p.multikey] ? b(d).jqGrid("setSelection", ab, !0, a) : d.p.multiselect && c && (c = b("#jqg_" + b.jgrid.jqID(d.p.id) + "_" + ab).is(":checked"), b("#jqg_" + b.jgrid.jqID(d.p.id) + "_" + ab)[d.p.useProp ? "prop" : "attr"]("checked", c));
                            else {
                                if (d.p.multiselect && d.p.multiboxonly && !c) {
                                    var g = d.p.frozenColumns ? d.p.id + "_frozen" : "";
                                    b(d.p.selarrrow).each(function(a, c) {
                                        var e = b(d).jqGrid("getGridRowById", c);
                                        b(e).removeClass("ui-state-highlight"), b("#jqg_" + b.jgrid.jqID(d.p.id) + "_" + b.jgrid.jqID(c))[d.p.useProp ? "prop" : "attr"]("checked", !1), g && (b("#" + b.jgrid.jqID(c), "#" + b.jgrid.jqID(g)).removeClass("ui-state-highlight"), b("#jqg_" + b.jgrid.jqID(d.p.id) + "_" + b.jgrid.jqID(c), "#" + b.jgrid.jqID(g))[d.p.useProp ? "prop" : "attr"]("checked", !1))
                                    }), d.p.selarrrow = []
                                }
                                b(d).jqGrid("setSelection", ab, !0, a)
                            }
                        }).bind("reloadGrid", function(a, c) {
                            if (d.p.treeGrid === !0 && (d.p.datatype = d.p.treedatatype), c && c.current && d.grid.selectionPreserver(d), "local" === d.p.datatype ? (b(d).jqGrid("resetSelection"), d.p.data.length && u()) : d.p.treeGrid || (d.p.selrow = null, d.p.multiselect && (d.p.selarrrow = [], C(!1)), d.p.savedRow = []), d.p.scroll && t.call(d, !0, !1), c && c.page) {
                                var e = c.page;
                                e > d.p.lastpage && (e = d.p.lastpage), 1 > e && (e = 1), d.p.page = e, d.grid.bDiv.scrollTop = d.grid.prevRowHeight ? (e - 1) * d.grid.prevRowHeight * d.p.rowNum : 0
                            }
                            return d.grid.prevRowHeight && d.p.scroll ? (delete d.p.lastpage, d.grid.populateVisible()) : d.grid.populate(), d.p._inlinenav === !0 && b(d).jqGrid("showAddEditButtons"), !1
                        }).dblclick(function(a) {
                            P = a.target, Q = b(P, d.rows).closest("tr.jqgrow"), 0 !== b(Q).length && (ab = Q[0].rowIndex, bb = b.jgrid.getCellIndex(P), b(d).triggerHandler("jqGridDblClickRow", [b(Q).attr("id"), ab, bb, a]), b.isFunction(d.p.ondblClickRow) && d.p.ondblClickRow.call(d, b(Q).attr("id"), ab, bb, a))
                        }).bind("contextmenu", function(a) {
                            P = a.target, Q = b(P, d.rows).closest("tr.jqgrow"), 0 !== b(Q).length && (d.p.multiselect || b(d).jqGrid("setSelection", Q[0].id, !0, a), ab = Q[0].rowIndex, bb = b.jgrid.getCellIndex(P), b(d).triggerHandler("jqGridRightClickRow", [b(Q).attr("id"), ab, bb, a]), b.isFunction(d.p.onRightClickRow) && d.p.onRightClickRow.call(d, b(Q).attr("id"), ab, bb, a))
                        }), e.bDiv = document.createElement("div"), i && "auto" === ("" + d.p.height).toLowerCase() && (d.p.height = "100%"), b(e.bDiv).append(b('<div style="position:relative;' + (i && 8 > b.jgrid.msiever() ? "height:0.01%;" : "") + '"></div>').append("<div></div>").append(this)).addClass("ui-jqgrid-bdiv").css({
                            "height": d.p.height + (isNaN(d.p.height) ? "" : "px"),
                            "width": e.width + "px"
                        }).scroll(e.scrollGrid), b("table:first", e.bDiv).css({
                            "width": d.p.tblwidth + "px"
                        }), b.support.tbody || 2 === b("tbody", this).length && b("tbody:gt(0)", this).remove(), d.p.multikey && (b.jgrid.msie ? b(e.bDiv).bind("selectstart", function() {
                            return !1
                        }) : b(e.bDiv).bind("mousedown", function() {
                            return !1
                        })), _ && b(e.bDiv).hide(), e.cDiv = document.createElement("div");
                        var db = !0 === d.p.hidegrid ? b("<a role='link' class='ui-jqgrid-titlebar-close HeaderButton' />").hover(function() {
                            db.addClass("ui-state-hover")
                        }, function() {
                            db.removeClass("ui-state-hover")
                        }).append("<span class='ui-icon ui-icon-circle-triangle-n'></span>").css("rtl" === g ? "left" : "right", "0px") : "";
                        if (b(e.cDiv).append(db).append("<span class='ui-jqgrid-title" + ("rtl" === g ? "-rtl" : "") + "'>" + d.p.caption + "</span>").addClass("ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix"), b(e.cDiv).insertBefore(e.hDiv), d.p.toolbar[0] && (e.uDiv = document.createElement("div"), "top" === d.p.toolbar[1] ? b(e.uDiv).insertBefore(e.hDiv) : "bottom" === d.p.toolbar[1] && b(e.uDiv).insertAfter(e.hDiv), "both" === d.p.toolbar[1] ? (e.ubDiv = document.createElement("div"), b(e.uDiv).addClass("ui-userdata ui-state-default").attr("id", "t_" + this.id).insertBefore(e.hDiv), b(e.ubDiv).addClass("ui-userdata ui-state-default").attr("id", "tb_" + this.id).insertAfter(e.hDiv), _ && b(e.ubDiv).hide()) : b(e.uDiv).width(e.width).addClass("ui-userdata ui-state-default").attr("id", "t_" + this.id), _ && b(e.uDiv).hide()), d.p.toppager && (d.p.toppager = b.jgrid.jqID(d.p.id) + "_toppager", e.topDiv = b("<div id='" + d.p.toppager + "'></div>")[0], d.p.toppager = "#" + d.p.toppager, b(e.topDiv).addClass("ui-state-default ui-jqgrid-toppager").width(e.width).insertBefore(e.hDiv), D(d.p.toppager, "_t")), d.p.footerrow && (e.sDiv = b("<div class='ui-jqgrid-sdiv'></div>")[0], f = b("<div class='ui-jqgrid-hbox" + ("rtl" === g ? "-rtl" : "") + "'></div>"), b(e.sDiv).append(f).width(e.width).insertAfter(e.hDiv), b(f).append(Y), e.footers = b(".ui-jqgrid-ftable", e.sDiv)[0].rows[0].cells, d.p.rownumbers && (e.footers[0].className = "ui-state-default jqgrid-rownum"), _ && b(e.sDiv).hide()), f = null, d.p.caption) {
                            var eb = d.p.datatype;
                            !0 === d.p.hidegrid && (b(".ui-jqgrid-titlebar-close", e.cDiv).click(function(a) {
                                var c, f = b.isFunction(d.p.onHeaderClick),
                                    g = ".ui-jqgrid-bdiv, .ui-jqgrid-hdiv, .ui-jqgrid-pager, .ui-jqgrid-sdiv",
                                    h = this;
                                return d.p.toolbar[0] === !0 && ("both" === d.p.toolbar[1] && (g += ", #" + b(e.ubDiv).attr("id")), g += ", #" + b(e.uDiv).attr("id")), c = b(g, "#gview_" + b.jgrid.jqID(d.p.id)).length, "visible" === d.p.gridstate ? b(g, "#gbox_" + b.jgrid.jqID(d.p.id)).slideUp("fast", function() {
                                    c--, 0 === c && (b("span", h).removeClass("ui-icon-circle-triangle-n").addClass("ui-icon-circle-triangle-s"), d.p.gridstate = "hidden", b("#gbox_" + b.jgrid.jqID(d.p.id)).hasClass("ui-resizable") && b(".ui-resizable-handle", "#gbox_" + b.jgrid.jqID(d.p.id)).hide(), b(d).triggerHandler("jqGridHeaderClick", [d.p.gridstate, a]), f && (_ || d.p.onHeaderClick.call(d, d.p.gridstate, a)))
                                }) : "hidden" === d.p.gridstate && b(g, "#gbox_" + b.jgrid.jqID(d.p.id)).slideDown("fast", function() {
                                    c--, 0 === c && (b("span", h).removeClass("ui-icon-circle-triangle-s").addClass("ui-icon-circle-triangle-n"), _ && (d.p.datatype = eb, B(), _ = !1), d.p.gridstate = "visible", b("#gbox_" + b.jgrid.jqID(d.p.id)).hasClass("ui-resizable") && b(".ui-resizable-handle", "#gbox_" + b.jgrid.jqID(d.p.id)).show(), b(d).triggerHandler("jqGridHeaderClick", [d.p.gridstate, a]), f && (_ || d.p.onHeaderClick.call(d, d.p.gridstate, a)))
                                }), !1
                            }), _ && (d.p.datatype = "local", b(".ui-jqgrid-titlebar-close", e.cDiv).trigger("click")))
                        } else b(e.cDiv).hide();
                        b(e.hDiv).after(e.bDiv).mousemove(function(a) {
                            return e.resizing ? (e.dragMove(a), !1) : void 0
                        }), b(".ui-jqgrid-labels", e.hDiv).bind("selectstart", function() {
                            return !1
                        }), b(document).bind("mouseup.jqGrid" + d.p.id, function() {
                            return e.resizing ? (e.dragEnd(), !1) : !0
                        }), d.formatCol = l, d.sortData = F, d.updatepager = function(a, c) {
                            var e, f, g, h, i, j, l, m = "",
                                n = d.p.pager ? "_" + b.jgrid.jqID(d.p.pager.substr(1)) : "",
                                o = d.p.toppager ? "_" + d.p.toppager.substr(1) : "";
                            g = parseInt(d.p.page, 10) - 1, 0 > g && (g = 0), g *= parseInt(d.p.rowNum, 10), i = g + d.p.reccount, d.p.scroll && (e = b("tbody:first > tr:gt(0)", d.grid.bDiv), g = i - e.length, d.p.reccount = e.length, (e = e.outerHeight() || d.grid.prevRowHeight) && (f = g * e, l = parseInt(d.p.records, 10) * e, b(">div:first", d.grid.bDiv).css({
                                "height": l
                            }).children("div:first").css({
                                "height": f,
                                "display": f ? "" : "none"
                            }), 0 == d.grid.bDiv.scrollTop && d.p.page > 1 && (d.grid.bDiv.scrollTop = d.p.rowNum * (d.p.page - 1) * e)), d.grid.bDiv.scrollLeft = d.grid.hDiv.scrollLeft), m = d.p.pager || "", (m += d.p.toppager ? m ? "," + d.p.toppager : d.p.toppager : "") && (l = b.jgrid.formatter.integer || {}, e = k(d.p.page), f = k(d.p.lastpage), b(".selbox", m)[this.p.useProp ? "prop" : "attr"]("disabled", !1), d.p.pginput === !0 && (b(".ui-pg-input", m).val(d.p.page), h = d.p.toppager ? "#sp_1" + n + ",#sp_1" + o : "#sp_1" + n, b(h).html(b.fmatter ? b.fmatter.util.NumberFormat(d.p.lastpage, l) : d.p.lastpage)), d.p.viewrecords && (0 === d.p.reccount ? b(".ui-paging-info", m).html(d.p.emptyrecords) : (h = g + 1, j = d.p.records, b.fmatter && (h = b.fmatter.util.NumberFormat(h, l), i = b.fmatter.util.NumberFormat(i, l), j = b.fmatter.util.NumberFormat(j, l)), b(".ui-paging-info", m).html(b.jgrid.format(d.p.recordtext, h, i, j)))), d.p.pgbuttons === !0 && (0 >= e && (e = f = 0), 1 === e || 0 === e ? (b("#first" + n + ", #prev" + n).addClass("ui-state-disabled").removeClass("ui-state-hover"), d.p.toppager && b("#first_t" + o + ", #prev_t" + o).addClass("ui-state-disabled").removeClass("ui-state-hover")) : (b("#first" + n + ", #prev" + n).removeClass("ui-state-disabled"), d.p.toppager && b("#first_t" + o + ", #prev_t" + o).removeClass("ui-state-disabled")), e === f || 0 === e ? (b("#next" + n + ", #last" + n).addClass("ui-state-disabled").removeClass("ui-state-hover"), d.p.toppager && b("#next_t" + o + ", #last_t" + o).addClass("ui-state-disabled").removeClass("ui-state-hover")) : (b("#next" + n + ", #last" + n).removeClass("ui-state-disabled"), d.p.toppager && b("#next_t" + o + ", #last_t" + o).removeClass("ui-state-disabled")))), a === !0 && d.p.rownumbers === !0 && b(">td.jqgrid-rownum", d.rows).each(function(a) {
                                b(this).html(g + 1 + a)
                            }), c && d.p.jqgdnd && b(d).jqGrid("gridDnD", "updateDnD"), b(d).triggerHandler("jqGridGridComplete"), b.isFunction(d.p.gridComplete) && d.p.gridComplete.call(d), b(d).triggerHandler("jqGridAfterGridComplete")
                        }, d.refreshIndex = u, d.setHeadCheckBox = C, d.constructTr = v, d.formatter = function(a, b, c, d, e) {
                            return n(a, b, c, d, e)
                        }, b.extend(e, {
                            "populate": B,
                            "emptyRows": t,
                            "beginReq": z,
                            "endReq": A
                        }), this.grid = e, d.addXmlData = function(a) {
                            w(a, d.grid.bDiv)
                        }, d.addJSONData = function(a) {
                            x(a, d.grid.bDiv)
                        }, this.grid.cols = this.rows[0].cells, b(d).triggerHandler("jqGridInitGrid"), b.isFunction(d.p.onInitGrid) && d.p.onInitGrid.call(d), B(), d.p.hiddengrid = !1
                    }
                }
            }
        })
    }, b.jgrid.extend({
        "getGridParam": function(a) {
            var b = this[0];
            return b && b.grid ? a ? void 0 !== b.p[a] ? b.p[a] : null : b.p : void 0
        },
        "setGridParam": function(a) {
            return this.each(function() {
                this.grid && "object" == typeof a && b.extend(!0, this.p, a)
            })
        },
        "getGridRowById": function(a) {
            var c;
            return this.each(function() {
                try {
                    c = this.rows.namedItem(a)
                } catch (d) {
                    c = b(this.grid.bDiv).find("#" + b.jgrid.jqID(a))
                }
            }), c
        },
        "getDataIDs": function() {
            var a, c = [],
                d = 0,
                e = 0;
            return this.each(function() {
                if ((a = this.rows.length) && a > 0)
                    for (; a > d;) b(this.rows[d]).hasClass("jqgrow") && (c[e] = this.rows[d].id, e++), d++
            }), c
        },
        "setSelection": function(a, c, d) {
            return this.each(function() {
                var e, f, g, h, i, j;
                void 0 === a || (c = !1 === c ? !1 : !0, !(f = b(this).jqGrid("getGridRowById", a)) || !f.className || -1 < f.className.indexOf("ui-state-disabled")) || (!0 === this.p.scrollrows && (g = b(this).jqGrid("getGridRowById", a).rowIndex, g >= 0 && (e = b(this.grid.bDiv)[0].clientHeight, h = b(this.grid.bDiv)[0].scrollTop, i = b(this.rows[g]).position().top, g = this.rows[g].clientHeight, i + g >= e + h ? b(this.grid.bDiv)[0].scrollTop = i - (e + h) + g + h : e + h > i && h > i && (b(this.grid.bDiv)[0].scrollTop = i))), !0 === this.p.frozenColumns && (j = this.p.id + "_frozen"), this.p.multiselect ? (this.setHeadCheckBox(!1), this.p.selrow = f.id, h = b.inArray(this.p.selrow, this.p.selarrrow), -1 === h ? ("ui-subgrid" !== f.className && b(f).addClass("ui-state-highlight").attr("aria-selected", "true"), e = !0, this.p.selarrrow.push(this.p.selrow)) : ("ui-subgrid" !== f.className && b(f).removeClass("ui-state-highlight").attr("aria-selected", "false"), e = !1, this.p.selarrrow.splice(h, 1), i = this.p.selarrrow[0], this.p.selrow = void 0 === i ? null : i), b("#jqg_" + b.jgrid.jqID(this.p.id) + "_" + b.jgrid.jqID(f.id))[this.p.useProp ? "prop" : "attr"]("checked", e), j && (-1 === h ? b("#" + b.jgrid.jqID(a), "#" + b.jgrid.jqID(j)).addClass("ui-state-highlight") : b("#" + b.jgrid.jqID(a), "#" + b.jgrid.jqID(j)).removeClass("ui-state-highlight"), b("#jqg_" + b.jgrid.jqID(this.p.id) + "_" + b.jgrid.jqID(a), "#" + b.jgrid.jqID(j))[this.p.useProp ? "prop" : "attr"]("checked", e)), c && (b(this).triggerHandler("jqGridSelectRow", [f.id, e, d]), this.p.onSelectRow && this.p.onSelectRow.call(this, f.id, e, d))) : "ui-subgrid" !== f.className && (this.p.selrow !== f.id ? (b(b(this).jqGrid("getGridRowById", this.p.selrow)).removeClass("ui-state-highlight").attr({
                    "aria-selected": "false",
                    "tabindex": "-1"
                }), b(f).addClass("ui-state-highlight").attr({
                    "aria-selected": "true",
                    "tabindex": "0"
                }), j && (b("#" + b.jgrid.jqID(this.p.selrow), "#" + b.jgrid.jqID(j)).removeClass("ui-state-highlight"), b("#" + b.jgrid.jqID(a), "#" + b.jgrid.jqID(j)).addClass("ui-state-highlight")), e = !0) : e = !1, this.p.selrow = f.id, c) && (b(this).triggerHandler("jqGridSelectRow", [f.id, e, d]), this.p.onSelectRow && this.p.onSelectRow.call(this, f.id, e, d)))
            })
        },
        "resetSelection": function(a) {
            return this.each(function() {
                var c, d, e = this;
                !0 === e.p.frozenColumns && (d = e.p.id + "_frozen"), void 0 !== a ? (c = a === e.p.selrow ? e.p.selrow : a, b("#" + b.jgrid.jqID(e.p.id) + " tbody:first tr#" + b.jgrid.jqID(c)).removeClass("ui-state-highlight").attr("aria-selected", "false"), d && b("#" + b.jgrid.jqID(c), "#" + b.jgrid.jqID(d)).removeClass("ui-state-highlight"), e.p.multiselect && (b("#jqg_" + b.jgrid.jqID(e.p.id) + "_" + b.jgrid.jqID(c), "#" + b.jgrid.jqID(e.p.id))[e.p.useProp ? "prop" : "attr"]("checked", !1), d && b("#jqg_" + b.jgrid.jqID(e.p.id) + "_" + b.jgrid.jqID(c), "#" + b.jgrid.jqID(d))[e.p.useProp ? "prop" : "attr"]("checked", !1), e.setHeadCheckBox(!1)), c = null) : e.p.multiselect ? (b(e.p.selarrrow).each(function(a, c) {
                    b(b(e).jqGrid("getGridRowById", c)).removeClass("ui-state-highlight").attr("aria-selected", "false"), b("#jqg_" + b.jgrid.jqID(e.p.id) + "_" + b.jgrid.jqID(c))[e.p.useProp ? "prop" : "attr"]("checked", !1), d && (b("#" + b.jgrid.jqID(c), "#" + b.jgrid.jqID(d)).removeClass("ui-state-highlight"), b("#jqg_" + b.jgrid.jqID(e.p.id) + "_" + b.jgrid.jqID(c), "#" + b.jgrid.jqID(d))[e.p.useProp ? "prop" : "attr"]("checked", !1))
                }), e.setHeadCheckBox(!1), e.p.selarrrow = []) : e.p.selrow && (b("#" + b.jgrid.jqID(e.p.id) + " tbody:first tr#" + b.jgrid.jqID(e.p.selrow)).removeClass("ui-state-highlight").attr("aria-selected", "false"), d && b("#" + b.jgrid.jqID(e.p.selrow), "#" + b.jgrid.jqID(d)).removeClass("ui-state-highlight"), e.p.selrow = null), !0 === e.p.cellEdit && 0 <= parseInt(e.p.iCol, 10) && 0 <= parseInt(e.p.iRow, 10) && (b("td:eq(" + e.p.iCol + ")", e.rows[e.p.iRow]).removeClass("edit-cell ui-state-highlight"), b(e.rows[e.p.iRow]).removeClass("selected-row ui-state-hover")), e.p.savedRow = []
            })
        },
        "getRowData": function(a) {
            var c, d, e = {},
                f = !1,
                g = 0;
            return this.each(function() {
                var h, i, j = this;
                if (void 0 === a) f = !0, c = [], d = j.rows.length;
                else {
                    if (i = b(j).jqGrid("getGridRowById", a), !i) return e;
                    d = 2
                }
                for (; d > g;) f && (i = j.rows[g]), b(i).hasClass("jqgrow") && (b('td[role="gridcell"]', i).each(function(a) {
                    if (h = j.p.colModel[a].name, "cb" !== h && "subgrid" !== h && "rn" !== h)
                        if (!0 === j.p.treeGrid && h === j.p.ExpandColumn) e[h] = b.jgrid.htmlDecode(b("span:first", this).html());
                        else try {
                            e[h] = b.unformat.call(j, this, {
                                "rowId": i.id,
                                "colModel": j.p.colModel[a]
                            }, a)
                        } catch (c) {
                            e[h] = b.jgrid.htmlDecode(b(this).html())
                        }
                }), f && (c.push(e), e = {})), g++
            }), c || e
        },
        "delRowData": function(a) {
            var c, d, e = !1;
            return this.each(function() {
                if (c = b(this).jqGrid("getGridRowById", a), !c) return !1;
                if (b(c).remove(), this.p.records--, this.p.reccount--, this.updatepager(!0, !1), e = !0, this.p.multiselect && (d = b.inArray(a, this.p.selarrrow), -1 !== d && this.p.selarrrow.splice(d, 1)), this.p.selrow = this.p.multiselect && 0 < this.p.selarrrow.length ? this.p.selarrrow[this.p.selarrrow.length - 1] : null, "local" === this.p.datatype) {
                    var f = this.p._index[b.jgrid.stripPref(this.p.idPrefix, a)];
                    void 0 !== f && (this.p.data.splice(f, 1), this.refreshIndex())
                }
                if (!0 === this.p.altRows && e) {
                    var g = this.p.altclass;
                    b(this.rows).each(function(a) {
                        a % 2 === 1 ? b(this).addClass(g) : b(this).removeClass(g)
                    })
                }
            }), e
        },
        "setRowData": function(a, c, d) {
            var e, f, g = !0;
            return this.each(function() {
                if (!this.grid) return !1;
                var h, i, j = this,
                    k = typeof d,
                    l = {};
                if (i = b(this).jqGrid("getGridRowById", a), !i) return !1;
                if (c) try {
                    if (b(this.p.colModel).each(function(d) {
                            e = this.name;
                            var g = b.jgrid.getAccessor(c, e);
                            void 0 !== g && (l[e] = this.formatter && "string" == typeof this.formatter && "date" === this.formatter ? b.unformat.date.call(j, g, this) : g, h = j.formatter(a, g, d, c, "edit"), f = this.title ? {
                                "title": b.jgrid.stripHtml(h)
                            } : {}, !0 === j.p.treeGrid && e === j.p.ExpandColumn ? b("td[role='gridcell']:eq(" + d + ") > span:first", i).html(h).attr(f) : b("td[role='gridcell']:eq(" + d + ")", i).html(h).attr(f))
                        }), "local" === j.p.datatype) {
                        var m, n = b.jgrid.stripPref(j.p.idPrefix, a),
                            o = j.p._index[n];
                        if (j.p.treeGrid)
                            for (m in j.p.treeReader) j.p.treeReader.hasOwnProperty(m) && delete l[j.p.treeReader[m]];
                        void 0 !== o && (j.p.data[o] = b.extend(!0, j.p.data[o], l)), l = null
                    }
                } catch (p) {
                    g = !1
                }
                g && ("string" === k ? b(i).addClass(d) : "object" === k && b(i).css(d), b(j).triggerHandler("jqGridAfterGridComplete"))
            }), g
        },
        "addRowData": function(a, c, d, e) {
            d || (d = "last");
            var f, g, h, i, j, k, l, m, n, o, p, q, r, s, t = !1,
                u = "";
            return c && (b.isArray(c) ? (n = !0, d = "last", o = a) : (c = [c], n = !1), this.each(function() {
                var v = c.length;
                j = this.p.rownumbers === !0 ? 1 : 0, h = this.p.multiselect === !0 ? 1 : 0, i = this.p.subGrid === !0 ? 1 : 0, n || (void 0 !== a ? a = "" + a : (a = b.jgrid.randId(), this.p.keyIndex !== !1 && (o = this.p.colModel[this.p.keyIndex + h + i + j].name, void 0 !== c[0][o] && (a = c[0][o])))), p = this.p.altclass;
                for (var w = 0, x = "", y = {}, z = b.isFunction(this.p.afterInsertRow) ? !0 : !1; v > w;) {
                    if (q = c[w], g = [], n) {
                        try {
                            a = q[o], void 0 === a && (a = b.jgrid.randId())
                        } catch (A) {
                            a = b.jgrid.randId()
                        }
                        x = this.p.altRows === !0 && (this.rows.length - 1) % 2 === 0 ? p : ""
                    }
                    for (s = a, a = this.p.idPrefix + a, j && (u = this.formatCol(0, 1, "", null, a, !0), g[g.length] = '<td role="gridcell" class="ui-state-default jqgrid-rownum" ' + u + ">0</td>"), h && (m = '<input role="checkbox" type="checkbox" id="jqg_' + this.p.id + "_" + a + '" class="cbox"/>', u = this.formatCol(j, 1, "", null, a, !0), g[g.length] = '<td role="gridcell" ' + u + ">" + m + "</td>"), i && (g[g.length] = b(this).jqGrid("addSubGridCell", h + j, 1)), l = h + i + j; l < this.p.colModel.length; l++) r = this.p.colModel[l], f = r.name, y[f] = q[f], m = this.formatter(a, b.jgrid.getAccessor(q, f), l, q), u = this.formatCol(l, 1, m, q, a, y), g[g.length] = '<td role="gridcell" ' + u + ">" + m + "</td>";
                    if (g.unshift(this.constructTr(a, !1, x, y, q, !1)), g[g.length] = "</tr>", 0 === this.rows.length) b("table:first", this.grid.bDiv).append(g.join(""));
                    else switch (d) {
                        case "last":
                            b(this.rows[this.rows.length - 1]).after(g.join("")), k = this.rows.length - 1;
                            break;
                        case "first":
                            b(this.rows[0]).after(g.join("")), k = 1;
                            break;
                        case "after":
                            (k = b(this).jqGrid("getGridRowById", e)) && (b(this.rows[k.rowIndex + 1]).hasClass("ui-subgrid") ? b(this.rows[k.rowIndex + 1]).after(g) : b(k).after(g.join("")), k = k.rowIndex + 1);
                            break;
                        case "before":
                            (k = b(this).jqGrid("getGridRowById", e)) && (b(k).before(g.join("")), k = k.rowIndex - 1)
                    }
                    this.p.subGrid === !0 && b(this).jqGrid("addSubGrid", h + j, k), this.p.records++, this.p.reccount++, b(this).triggerHandler("jqGridAfterInsertRow", [a, q, q]), z && this.p.afterInsertRow.call(this, a, q, q), w++, "local" === this.p.datatype && (y[this.p.localReader.id] = s, this.p._index[s] = this.p.data.length, this.p.data.push(y), y = {})
                }
                this.p.altRows === !0 && !n && ("last" === d ? (this.rows.length - 1) % 2 === 1 && b(this.rows[this.rows.length - 1]).addClass(p) : b(this.rows).each(function(a) {
                    a % 2 === 1 ? b(this).addClass(p) : b(this).removeClass(p)
                })), this.updatepager(!0, !0), t = !0
            })), t
        },
        "footerData": function(a, c, d) {
            function e(a) {
                for (var b in a)
                    if (a.hasOwnProperty(b)) return !1;
                return !0
            }
            var f, g, h = !1,
                i = {};
            return void 0 === a && (a = "get"), "boolean" != typeof d && (d = !0), a = a.toLowerCase(), this.each(function() {
                var j, k = this;
                return !k.grid || !k.p.footerrow || "set" === a && e(c) ? !1 : (h = !0, void b(this.p.colModel).each(function(e) {
                    f = this.name, "set" === a ? void 0 !== c[f] && (j = d ? k.formatter("", c[f], e, c, "edit") : c[f], g = this.title ? {
                        "title": b.jgrid.stripHtml(j)
                    } : {}, b("tr.footrow td:eq(" + e + ")", k.grid.sDiv).html(j).attr(g), h = !0) : "get" === a && (i[f] = b("tr.footrow td:eq(" + e + ")", k.grid.sDiv).html())
                }))
            }), "get" === a ? i : h
        },
        "showHideCol": function(a, c) {
            return this.each(function() {
                var d, e = this,
                    f = !1,
                    g = b.jgrid.cell_width ? 0 : e.p.cellLayout;
                if (e.grid) {
                    "string" == typeof a && (a = [a]), c = "none" !== c ? "" : "none";
                    var h = "" === c ? !0 : !1,
                        i = e.p.groupHeader && ("object" == typeof e.p.groupHeader || b.isFunction(e.p.groupHeader));
                    i && b(e).jqGrid("destroyGroupHeader", !1), b(this.p.colModel).each(function(i) {
                        if (-1 !== b.inArray(this.name, a) && this.hidden === h) {
                            if (!0 === e.p.frozenColumns && !0 === this.frozen) return !0;
                            b("tr[role=rowheader]", e.grid.hDiv).each(function() {
                                b(this.cells[i]).css("display", c)
                            }), b(e.rows).each(function() {
                                b(this).hasClass("jqgroup") || b(this.cells[i]).css("display", c)
                            }), e.p.footerrow && b("tr.footrow td:eq(" + i + ")", e.grid.sDiv).css("display", c), d = parseInt(this.width, 10), e.p.tblwidth = "none" === c ? e.p.tblwidth - (d + g) : e.p.tblwidth + (d + g), this.hidden = !h, f = !0, b(e).triggerHandler("jqGridShowHideCol", [h, this.name, i])
                        }
                    }), !0 === f && (!0 === e.p.shrinkToFit && !isNaN(e.p.height) && (e.p.tblwidth += parseInt(e.p.scrollOffset, 10)), b(e).jqGrid("setGridWidth", !0 === e.p.shrinkToFit ? e.p.tblwidth : e.p.width)), i && b(e).jqGrid("setGroupHeaders", e.p.groupHeader)
                }
            })
        },
        "hideCol": function(a) {
            return this.each(function() {
                b(this).jqGrid("showHideCol", a, "none")
            })
        },
        "showCol": function(a) {
            return this.each(function() {
                b(this).jqGrid("showHideCol", a, "")
            })
        },
        "remapColumns": function(a, c, d) {
            function e(c) {
                var d;
                d = c.length ? b.makeArray(c) : b.extend({}, c), b.each(a, function(a) {
                    c[a] = d[this]
                })
            }

            function f(c, d) {
                b(">tr" + (d || ""), c).each(function() {
                    var c = this,
                        d = b.makeArray(c.cells);
                    b.each(a, function() {
                        var a = d[this];
                        a && c.appendChild(a)
                    })
                })
            }
            var g = this.get(0);
            e(g.p.colModel), e(g.p.colNames), e(g.grid.headers), f(b("thead:first", g.grid.hDiv), d && ":not(.ui-jqgrid-labels)"), c && f(b("#" + b.jgrid.jqID(g.p.id) + " tbody:first"), ".jqgfirstrow, tr.jqgrow, tr.jqfoot"), g.p.footerrow && f(b("tbody:first", g.grid.sDiv)), g.p.remapColumns && (g.p.remapColumns.length ? e(g.p.remapColumns) : g.p.remapColumns = b.makeArray(a)), g.p.lastsort = b.inArray(g.p.lastsort, a), g.p.treeGrid && (g.p.expColInd = b.inArray(g.p.expColInd, a)), b(g).triggerHandler("jqGridRemapColumns", [a, c, d])
        },
        "setGridWidth": function(a, c) {
            return this.each(function() {
                if (this.grid) {
                    var d, e, f, g, h = this,
                        i = 0,
                        j = b.jgrid.cell_width ? 0 : h.p.cellLayout,
                        k = 0,
                        l = !1,
                        m = h.p.scrollOffset,
                        n = 0;
                    if ("boolean" != typeof c && (c = h.p.shrinkToFit), !isNaN(a)) {
                        if (a = parseInt(a, 10), h.grid.width = h.p.width = a, b("#gbox_" + b.jgrid.jqID(h.p.id)).css("width", a + "px"), b("#gview_" + b.jgrid.jqID(h.p.id)).css("width", a + "px"), b(h.grid.bDiv).css("width", a + "px"), b(h.grid.hDiv).css("width", a + "px"), h.p.pager && b(h.p.pager).css("width", a + "px"), h.p.toppager && b(h.p.toppager).css("width", a + "px"), !0 === h.p.toolbar[0] && (b(h.grid.uDiv).css("width", a + "px"), "both" === h.p.toolbar[1] && b(h.grid.ubDiv).css("width", a + "px")), h.p.footerrow && b(h.grid.sDiv).css("width", a + "px"), !1 === c && !0 === h.p.forceFit && (h.p.forceFit = !1), !0 === c) {
                            if (b.each(h.p.colModel, function() {
                                    this.hidden === !1 && (d = this.widthOrg, i += d + j, this.fixed ? n += d + j : k++)
                                }), 0 === k) return;
                            h.p.tblwidth = i, f = a - j * k - n, !isNaN(h.p.height) && (b(h.grid.bDiv)[0].clientHeight < b(h.grid.bDiv)[0].scrollHeight || 1 === h.rows.length) && (l = !0, f -= m);
                            var i = 0,
                                o = 0 < h.grid.cols.length;
                            if (b.each(h.p.colModel, function(a) {
                                    this.hidden !== !1 || this.fixed || (d = this.widthOrg, d = Math.round(f * d / (h.p.tblwidth - j * k - n)), 0 > d || (this.width = d, i += d, h.grid.headers[a].width = d, h.grid.headers[a].el.style.width = d + "px", h.p.footerrow && (h.grid.footers[a].style.width = d + "px"), o && (h.grid.cols[a].style.width = d + "px"), e = a))
                                }), !e) return;
                            g = 0, l ? a - n - (i + j * k) !== m && (g = a - n - (i + j * k) - m) : 1 !== Math.abs(a - n - (i + j * k)) && (g = a - n - (i + j * k)), h.p.colModel[e].width += g, h.p.tblwidth = i + g + j * k + n, h.p.tblwidth > a ? (l = h.p.tblwidth - parseInt(a, 10), h.p.tblwidth = a, d = h.p.colModel[e].width -= l) : d = h.p.colModel[e].width, h.grid.headers[e].width = d, h.grid.headers[e].el.style.width = d + "px", o && (h.grid.cols[e].style.width = d + "px"), h.p.footerrow && (h.grid.footers[e].style.width = d + "px")
                        }
                        h.p.tblwidth && (b("table:first", h.grid.bDiv).css("width", h.p.tblwidth + "px"), b("table:first", h.grid.hDiv).css("width", h.p.tblwidth + "px"), h.grid.hDiv.scrollLeft = h.grid.bDiv.scrollLeft, h.p.footerrow && b("table:first", h.grid.sDiv).css("width", h.p.tblwidth + "px"))
                    }
                }
            })
        },
        "setGridHeight": function(a) {
            return this.each(function() {
                if (this.grid) {
                    var c = b(this.grid.bDiv);
                    c.css({
                        "height": a + (isNaN(a) ? "" : "px")
                    }), !0 === this.p.frozenColumns && b("#" + b.jgrid.jqID(this.p.id) + "_frozen").parent().height(c.height() - 16), this.p.height = a, this.p.scroll && this.grid.populateVisible()
                }
            })
        },
        "setCaption": function(a) {
            return this.each(function() {
                this.p.caption = a, b("span.ui-jqgrid-title, span.ui-jqgrid-title-rtl", this.grid.cDiv).html(a), b(this.grid.cDiv).show()
            })
        },
        "setLabel": function(a, c, d, e) {
            return this.each(function() {
                var f = -1;
                if (this.grid && void 0 !== a && (b(this.p.colModel).each(function(b) {
                        return this.name === a ? (f = b, !1) : void 0
                    }), f >= 0)) {
                    var g = b("tr.ui-jqgrid-labels th:eq(" + f + ")", this.grid.hDiv);
                    if (c) {
                        var h = b(".s-ico", g);
                        b("[id^=jqgh_]", g).empty().html(c).append(h), this.p.colNames[f] = c
                    }
                    d && ("string" == typeof d ? b(g).addClass(d) : b(g).css(d)), "object" == typeof e && b(g).attr(e)
                }
            })
        },
        "setCell": function(a, c, d, e, f, g) {
            return this.each(function() {
                var h, i, j = -1;
                if (this.grid && (isNaN(c) ? b(this.p.colModel).each(function(a) {
                        return this.name === c ? (j = a, !1) : void 0
                    }) : j = parseInt(c, 10), j >= 0 && (h = b(this).jqGrid("getGridRowById", a)))) {
                    var k = b("td:eq(" + j + ")", h);
                    ("" !== d || !0 === g) && (h = this.formatter(a, d, j, h, "edit"), i = this.p.colModel[j].title ? {
                        "title": b.jgrid.stripHtml(h)
                    } : {}, this.p.treeGrid && 0 < b(".tree-wrap", b(k)).length ? b("span", b(k)).html(h).attr(i) : b(k).html(h).attr(i), "local" === this.p.datatype && (h = this.p.colModel[j], d = h.formatter && "string" == typeof h.formatter && "date" === h.formatter ? b.unformat.date.call(this, d, h) : d, i = this.p._index[b.jgrid.stripPref(this.p.idPrefix, a)], void 0 !== i && (this.p.data[i][h.name] = d))), "string" == typeof e ? b(k).addClass(e) : e && b(k).css(e), "object" == typeof f && b(k).attr(f)
                }
            })
        },
        "getCell": function(a, c) {
            var d = !1;
            return this.each(function() {
                var e = -1;
                if (this.grid && (isNaN(c) ? b(this.p.colModel).each(function(a) {
                        return this.name === c ? (e = a, !1) : void 0
                    }) : e = parseInt(c, 10), e >= 0)) {
                    var f = b(this).jqGrid("getGridRowById", a);
                    if (f) try {
                        d = b.unformat.call(this, b("td:eq(" + e + ")", f), {
                            "rowId": f.id,
                            "colModel": this.p.colModel[e]
                        }, e)
                    } catch (g) {
                        d = b.jgrid.htmlDecode(b("td:eq(" + e + ")", f).html())
                    }
                }
            }), d
        },
        "getCol": function(a, c, d) {
            var e, f, g, h, i = [],
                j = 0,
                c = "boolean" != typeof c ? !1 : c;
            return void 0 === d && (d = !1), this.each(function() {
                var k = -1;
                if (this.grid && (isNaN(a) ? b(this.p.colModel).each(function(b) {
                        return this.name === a ? (k = b, !1) : void 0
                    }) : k = parseInt(a, 10), k >= 0)) {
                    var l = this.rows.length,
                        m = 0,
                        n = 0;
                    if (l && l > 0) {
                        for (; l > m;) {
                            if (b(this.rows[m]).hasClass("jqgrow")) {
                                try {
                                    e = b.unformat.call(this, b(this.rows[m].cells[k]), {
                                        "rowId": this.rows[m].id,
                                        "colModel": this.p.colModel[k]
                                    }, k)
                                } catch (o) {
                                    e = b.jgrid.htmlDecode(this.rows[m].cells[k].innerHTML)
                                }
                                d ? (h = parseFloat(e), isNaN(h) || (j += h, void 0 === g && (g = f = h), f = Math.min(f, h), g = Math.max(g, h), n++)) : i.push(c ? {
                                    "id": this.rows[m].id,
                                    "value": e
                                } : e)
                            }
                            m++
                        }
                        if (d) switch (d.toLowerCase()) {
                            case "sum":
                                i = j;
                                break;
                            case "avg":
                                i = j / n;
                                break;
                            case "count":
                                i = l - 1;
                                break;
                            case "min":
                                i = f;
                                break;
                            case "max":
                                i = g
                        }
                    }
                }
            }), i
        },
        "clearGridData": function(a) {
            return this.each(function() {
                if (this.grid) {
                    if ("boolean" != typeof a && (a = !1), this.p.deepempty) b("#" + b.jgrid.jqID(this.p.id) + " tbody:first tr:gt(0)").remove();
                    else {
                        var c = b("#" + b.jgrid.jqID(this.p.id) + " tbody:first tr:first")[0];
                        b("#" + b.jgrid.jqID(this.p.id) + " tbody:first").empty().append(c)
                    }
                    this.p.footerrow && a && b(".ui-jqgrid-ftable td", this.grid.sDiv).html("&#160;"), this.p.selrow = null, this.p.selarrrow = [], this.p.savedRow = [], this.p.records = 0, this.p.page = 1, this.p.lastpage = 0, this.p.reccount = 0, this.p.data = [], this.p._index = {}, this.updatepager(!0, !1)
                }
            })
        },
        "getInd": function(a, c) {
            var d, e = !1;
            return this.each(function() {
                (d = b(this).jqGrid("getGridRowById", a)) && (e = !0 === c ? d : d.rowIndex)
            }), e
        },
        "bindKeys": function(a) {
            var c = b.extend({
                "onEnter": null,
                "onSpace": null,
                "onLeftKey": null,
                "onRightKey": null,
                "scrollingRows": !0
            }, a || {});
            return this.each(function() {
                var a = this;
                b("body").is("[role]") || b("body").attr("role", "application"), a.p.scrollrows = c.scrollingRows, b(a).keydown(function(d) {
                    var e, f, g, h = b(a).find("tr[tabindex=0]")[0],
                        i = a.p.treeReader.expanded_field;
                    if (h)
                        if (g = a.p._index[b.jgrid.stripPref(a.p.idPrefix, h.id)], 37 === d.keyCode || 38 === d.keyCode || 39 === d.keyCode || 40 === d.keyCode) {
                            if (38 === d.keyCode) {
                                if (f = h.previousSibling, e = "", f)
                                    if (b(f).is(":hidden")) {
                                        for (; f;)
                                            if (f = f.previousSibling, !b(f).is(":hidden") && b(f).hasClass("jqgrow")) {
                                                e = f.id;
                                                break
                                            }
                                    } else e = f.id;
                                b(a).jqGrid("setSelection", e, !0, d), d.preventDefault()
                            }
                            if (40 === d.keyCode) {
                                if (f = h.nextSibling, e = "", f)
                                    if (b(f).is(":hidden")) {
                                        for (; f;)
                                            if (f = f.nextSibling, !b(f).is(":hidden") && b(f).hasClass("jqgrow")) {
                                                e = f.id;
                                                break
                                            }
                                    } else e = f.id;
                                b(a).jqGrid("setSelection", e, !0, d), d.preventDefault()
                            }
                            37 === d.keyCode && (a.p.treeGrid && a.p.data[g][i] && b(h).find("div.treeclick").trigger("click"), b(a).triggerHandler("jqGridKeyLeft", [a.p.selrow]), b.isFunction(c.onLeftKey) && c.onLeftKey.call(a, a.p.selrow)), 39 === d.keyCode && (a.p.treeGrid && !a.p.data[g][i] && b(h).find("div.treeclick").trigger("click"), b(a).triggerHandler("jqGridKeyRight", [a.p.selrow]), b.isFunction(c.onRightKey) && c.onRightKey.call(a, a.p.selrow))
                        } else 13 === d.keyCode ? (b(a).triggerHandler("jqGridKeyEnter", [a.p.selrow]), b.isFunction(c.onEnter) && c.onEnter.call(a, a.p.selrow)) : 32 === d.keyCode && (b(a).triggerHandler("jqGridKeySpace", [a.p.selrow]), b.isFunction(c.onSpace) && c.onSpace.call(a, a.p.selrow))
                })
            })
        },
        "unbindKeys": function() {
            return this.each(function() {
                b(this).unbind("keydown")
            })
        },
        "getLocalRow": function(a) {
            var c, d = !1;
            return this.each(function() {
                void 0 !== a && (c = this.p._index[b.jgrid.stripPref(this.p.idPrefix, a)], c >= 0 && (d = this.p.data[c]))
            }), d
        }
    })
}(jQuery),
function(a) {
    a.fmatter = {}, a.extend(a.fmatter, {
        "isBoolean": function(a) {
            return "boolean" == typeof a
        },
        "isObject": function(b) {
            return b && ("object" == typeof b || a.isFunction(b)) || !1
        },
        "isString": function(a) {
            return "string" == typeof a
        },
        "isNumber": function(a) {
            return "number" == typeof a && isFinite(a)
        },
        "isValue": function(a) {
            return this.isObject(a) || this.isString(a) || this.isNumber(a) || this.isBoolean(a)
        },
        "isEmpty": function(b) {
            return !this.isString(b) && this.isValue(b) ? !1 : this.isValue(b) ? (b = a.trim(b).replace(/\&nbsp\;/gi, "").replace(/\&#160\;/gi, ""), "" === b) : !0
        }
    }), a.fn.fmatter = function(b, c, d, e, f) {
        var g = c,
            d = a.extend({}, a.jgrid.formatter, d);
        try {
            g = a.fn.fmatter[b].call(this, c, d, e, f)
        } catch (h) {}
        return g
    }, a.fmatter.util = {
        "NumberFormat": function(b, c) {
            if (a.fmatter.isNumber(b) || (b *= 1), a.fmatter.isNumber(b)) {
                var d, e = 0 > b,
                    f = "" + b,
                    g = c.decimalSeparator || ".";
                if (a.fmatter.isNumber(c.decimalPlaces)) {
                    var h = c.decimalPlaces,
                        f = Math.pow(10, h),
                        f = "" + Math.round(b * f) / f;
                    if (d = f.lastIndexOf("."), h > 0)
                        for (0 > d ? (f += g, d = f.length - 1) : "." !== g && (f = f.replace(".", g)); f.length - 1 - d < h;) f += "0"
                }
                if (c.thousandsSeparator) {
                    h = c.thousandsSeparator, d = f.lastIndexOf(g), d = d > -1 ? d : f.length;
                    var i, g = f.substring(d),
                        j = -1;
                    for (i = d; i > 0; i--) j++, 0 === j % 3 && i !== d && (!e || i > 1) && (g = h + g), g = f.charAt(i - 1) + g;
                    f = g
                }
                return f = c.prefix ? c.prefix + f : f, f = c.suffix ? f + c.suffix : f
            }
            return b
        }
    }, a.fn.fmatter.defaultFormat = function(b, c) {
        return a.fmatter.isValue(b) && "" !== b ? b : c.defaultValue || "&#160;"
    }, a.fn.fmatter.email = function(b, c) {
        return a.fmatter.isEmpty(b) ? a.fn.fmatter.defaultFormat(b, c) : '<a href="mailto:' + b + '">' + b + "</a>"
    }, a.fn.fmatter.checkbox = function(b, c) {
        var d, e = a.extend({}, c.checkbox);
        return void 0 !== c.colModel && void 0 !== c.colModel.formatoptions && (e = a.extend({}, e, c.colModel.formatoptions)), d = !0 === e.disabled ? 'disabled="disabled"' : "", (a.fmatter.isEmpty(b) || void 0 === b) && (b = a.fn.fmatter.defaultFormat(b, e)), b = ("" + b).toLowerCase(), '<input type="checkbox" ' + (0 > b.search(/(false|f|0|no|n|off|undefined)/i) ? " checked='checked' " : "") + ' value="' + b + '" offval="no" ' + d + "/>"
    }, a.fn.fmatter.link = function(b, c) {
        var d = {
                "target": c.target
            },
            e = "";
        return void 0 !== c.colModel && void 0 !== c.colModel.formatoptions && (d = a.extend({}, d, c.colModel.formatoptions)), d.target && (e = "target=" + d.target), a.fmatter.isEmpty(b) ? a.fn.fmatter.defaultFormat(b, c) : "<a " + e + ' href="' + b + '">' + b + "</a>"
    }, a.fn.fmatter.showlink = function(b, c) {
        var d = {
                "baseLinkUrl": c.baseLinkUrl,
                "showAction": c.showAction,
                "addParam": c.addParam || "",
                "target": c.target,
                "idName": c.idName
            },
            e = "";
        return void 0 !== c.colModel && void 0 !== c.colModel.formatoptions && (d = a.extend({}, d, c.colModel.formatoptions)), d.target && (e = "target=" + d.target), d = d.baseLinkUrl + d.showAction + "?" + d.idName + "=" + c.rowId + d.addParam, a.fmatter.isString(b) || a.fmatter.isNumber(b) ? "<a " + e + ' href="' + d + '">' + b + "</a>" : a.fn.fmatter.defaultFormat(b, c)
    }, a.fn.fmatter.integer = function(b, c) {
        var d = a.extend({}, c.integer);
        return void 0 !== c.colModel && void 0 !== c.colModel.formatoptions && (d = a.extend({}, d, c.colModel.formatoptions)), a.fmatter.isEmpty(b) ? d.defaultValue : a.fmatter.util.NumberFormat(b, d)
    }, a.fn.fmatter.number = function(b, c) {
        var d = a.extend({}, c.number);
        return void 0 !== c.colModel && void 0 !== c.colModel.formatoptions && (d = a.extend({}, d, c.colModel.formatoptions)), a.fmatter.isEmpty(b) ? d.defaultValue : a.fmatter.util.NumberFormat(b, d)
    }, a.fn.fmatter.currency = function(b, c) {
        var d = a.extend({}, c.currency);
        return void 0 !== c.colModel && void 0 !== c.colModel.formatoptions && (d = a.extend({}, d, c.colModel.formatoptions)), a.fmatter.isEmpty(b) ? d.defaultValue : a.fmatter.util.NumberFormat(b, d)
    }, a.fn.fmatter.date = function(b, c, d, e) {
        return d = a.extend({}, c.date), void 0 !== c.colModel && void 0 !== c.colModel.formatoptions && (d = a.extend({}, d, c.colModel.formatoptions)), d.reformatAfterEdit || "edit" !== e ? a.fmatter.isEmpty(b) ? a.fn.fmatter.defaultFormat(b, c) : a.jgrid.parseDate(d.srcformat, b, d.newformat, d) : a.fn.fmatter.defaultFormat(b, c)
    }, a.fn.fmatter.select = function(b, c) {
        var d, e, b = "" + b,
            f = !1,
            g = [];
        if (void 0 !== c.colModel.formatoptions ? (f = c.colModel.formatoptions.value, d = void 0 === c.colModel.formatoptions.separator ? ":" : c.colModel.formatoptions.separator, e = void 0 === c.colModel.formatoptions.delimiter ? ";" : c.colModel.formatoptions.delimiter) : void 0 !== c.colModel.editoptions && (f = c.colModel.editoptions.value, d = void 0 === c.colModel.editoptions.separator ? ":" : c.colModel.editoptions.separator, e = void 0 === c.colModel.editoptions.delimiter ? ";" : c.colModel.editoptions.delimiter), f) {
            var h = !0 === c.colModel.editoptions.multiple ? !0 : !1,
                i = [];
            if (h && (i = b.split(","), i = a.map(i, function(b) {
                    return a.trim(b)
                })), a.fmatter.isString(f)) {
                var j, k = f.split(e),
                    l = 0;
                for (j = 0; j < k.length; j++)
                    if (e = k[j].split(d), 2 < e.length && (e[1] = a.map(e, function(a, b) {
                            return b > 0 ? a : void 0
                        }).join(d)), h) - 1 < a.inArray(e[0], i) && (g[l] = e[1], l++);
                    else if (a.trim(e[0]) === a.trim(b)) {
                    g[0] = e[1];
                    break
                }
            } else a.fmatter.isObject(f) && (h ? g = a.map(i, function(a) {
                return f[a]
            }) : g[0] = f[b] || "")
        }
        return b = g.join(", "), "" === b ? a.fn.fmatter.defaultFormat(b, c) : b
    }, a.fn.fmatter.rowactions = function(b) {
        var c = a(this).closest("tr.jqgrow"),
            d = c.attr("id"),
            e = a(this).closest("table.ui-jqgrid-btable").attr("id").replace(/_frozen([^_]*)$/, "$1"),
            e = a("#" + e),
            f = e[0],
            g = f.p,
            h = g.colModel[a.jgrid.getCellIndex(this)],
            i = h.frozen ? a("tr#" + d + " td:eq(" + a.jgrid.getCellIndex(this) + ") > div", e) : a(this).parent(),
            j = {
                "keys": !1,
                "onEdit": null,
                "onSuccess": null,
                "afterSave": null,
                "onError": null,
                "afterRestore": null,
                "extraparam": {},
                "url": null,
                "restoreAfterError": !0,
                "mtype": "POST",
                "delOptions": {},
                "editOptions": {}
            },
            k = function(b) {
                a.isFunction(j.afterRestore) && j.afterRestore.call(f, b), i.find("div.ui-inline-edit,div.ui-inline-del").show(), i.find("div.ui-inline-save,div.ui-inline-cancel").hide()
            };
        switch (void 0 !== h.formatoptions && (j = a.extend(j, h.formatoptions)), void 0 !== g.editOptions && (j.editOptions = g.editOptions), void 0 !== g.delOptions && (j.delOptions = g.delOptions), c.hasClass("jqgrid-new-row") && (j.extraparam[g.prmNames.oper] = g.prmNames.addoper), c = {
            "keys": j.keys,
            "oneditfunc": j.onEdit,
            "successfunc": j.onSuccess,
            "url": j.url,
            "extraparam": j.extraparam,
            "aftersavefunc": function(b, c) {
                a.isFunction(j.afterSave) && j.afterSave.call(f, b, c), i.find("div.ui-inline-edit,div.ui-inline-del").show(), i.find("div.ui-inline-save,div.ui-inline-cancel").hide()
            },
            "errorfunc": j.onError,
            "afterrestorefunc": k,
            "restoreAfterError": j.restoreAfterError,
            "mtype": j.mtype
        }, b) {
            case "edit":
                e.jqGrid("editRow", d, c), i.find("div.ui-inline-edit,div.ui-inline-del").hide(), i.find("div.ui-inline-save,div.ui-inline-cancel").show(), e.triggerHandler("jqGridAfterGridComplete");
                break;
            case "save":
                e.jqGrid("saveRow", d, c) && (i.find("div.ui-inline-edit,div.ui-inline-del").show(), i.find("div.ui-inline-save,div.ui-inline-cancel").hide(), e.triggerHandler("jqGridAfterGridComplete"));
                break;
            case "cancel":
                e.jqGrid("restoreRow", d, k), i.find("div.ui-inline-edit,div.ui-inline-del").show(), i.find("div.ui-inline-save,div.ui-inline-cancel").hide(), e.triggerHandler("jqGridAfterGridComplete");
                break;
            case "del":
                e.jqGrid("delGridRow", d, j.delOptions);
                break;
            case "formedit":
                e.jqGrid("setSelection", d), e.jqGrid("editGridRow", d, j.editOptions)
        }
    }, a.fn.fmatter.actions = function(b, c) {
        var d = {
                "keys": !1,
                "editbutton": !0,
                "delbutton": !0,
                "editformbutton": !1
            },
            e = c.rowId,
            f = "";
        return void 0 !== c.colModel.formatoptions && (d = a.extend(d, c.colModel.formatoptions)), void 0 === e || a.fmatter.isEmpty(e) ? "" : (d.editformbutton ? f += "<div title='" + a.jgrid.nav.edittitle + "' style='float:left;cursor:pointer;' class='ui-pg-div ui-inline-edit' " + ("id='jEditButton_" + e + "' onclick=jQuery.fn.fmatter.rowactions.call(this,'formedit'); onmouseover=jQuery(this).addClass('ui-state-hover'); onmouseout=jQuery(this).removeClass('ui-state-hover'); ") + "><span class='ui-icon ui-icon-pencil'></span></div>" : d.editbutton && (f += "<div title='" + a.jgrid.nav.edittitle + "' style='float:left;cursor:pointer;' class='ui-pg-div ui-inline-edit' " + ("id='jEditButton_" + e + "' onclick=jQuery.fn.fmatter.rowactions.call(this,'edit'); onmouseover=jQuery(this).addClass('ui-state-hover'); onmouseout=jQuery(this).removeClass('ui-state-hover') ") + "><span class='ui-icon ui-icon-pencil'></span></div>"), d.delbutton && (f += "<div title='" + a.jgrid.nav.deltitle + "' style='float:left;margin-left:5px;' class='ui-pg-div ui-inline-del' " + ("id='jDeleteButton_" + e + "' onclick=jQuery.fn.fmatter.rowactions.call(this,'del'); onmouseover=jQuery(this).addClass('ui-state-hover'); onmouseout=jQuery(this).removeClass('ui-state-hover'); ") + "><span class='ui-icon ui-icon-trash'></span></div>"), f += "<div title='" + a.jgrid.edit.bSubmit + "' style='float:left;display:none' class='ui-pg-div ui-inline-save' " + ("id='jSaveButton_" + e + "' onclick=jQuery.fn.fmatter.rowactions.call(this,'save'); onmouseover=jQuery(this).addClass('ui-state-hover'); onmouseout=jQuery(this).removeClass('ui-state-hover'); ") + "><span class='ui-icon ui-icon-disk'></span></div>", f += "<div title='" + a.jgrid.edit.bCancel + "' style='float:left;display:none;margin-left:5px;' class='ui-pg-div ui-inline-cancel' " + ("id='jCancelButton_" + e + "' onclick=jQuery.fn.fmatter.rowactions.call(this,'cancel'); onmouseover=jQuery(this).addClass('ui-state-hover'); onmouseout=jQuery(this).removeClass('ui-state-hover'); ") + "><span class='ui-icon ui-icon-cancel'></span></div>", "<div style='margin-left:8px;'>" + f + "</div>")
    }, a.unformat = function(b, c, d, e) {
        var f, g = c.colModel.formatter,
            h = c.colModel.formatoptions || {},
            i = /([\.\*\_\'\(\)\{\}\+\?\\])/g,
            j = c.colModel.unformat || a.fn.fmatter[g] && a.fn.fmatter[g].unformat;
        if (void 0 !== j && a.isFunction(j)) f = j.call(this, a(b).text(), c, b);
        else if (void 0 !== g && a.fmatter.isString(g)) switch (f = a.jgrid.formatter || {}, g) {
            case "integer":
                h = a.extend({}, f.integer, h), c = h.thousandsSeparator.replace(i, "\\$1"), f = a(b).text().replace(RegExp(c, "g"), "");
                break;
            case "number":
                h = a.extend({}, f.number, h), c = h.thousandsSeparator.replace(i, "\\$1"), f = a(b).text().replace(RegExp(c, "g"), "").replace(h.decimalSeparator, ".");
                break;
            case "currency":
                h = a.extend({}, f.currency, h), c = h.thousandsSeparator.replace(i, "\\$1"), c = RegExp(c, "g"), f = a(b).text(), h.prefix && h.prefix.length && (f = f.substr(h.prefix.length)), h.suffix && h.suffix.length && (f = f.substr(0, f.length - h.suffix.length)), f = f.replace(c, "").replace(h.decimalSeparator, ".");
                break;
            case "checkbox":
                h = c.colModel.editoptions ? c.colModel.editoptions.value.split(":") : ["Yes", "No"], f = a("input", b).is(":checked") ? h[0] : h[1];
                break;
            case "select":
                f = a.unformat.select(b, c, d, e);
                break;
            case "actions":
                return "";
            default:
                f = a(b).text()
        }
        return void 0 !== f ? f : !0 === e ? a(b).text() : a.jgrid.htmlDecode(a(b).html())
    }, a.unformat.select = function(b, c, d, e) {
        if (d = [], b = a(b).text(), !0 === e) return b;
        var e = a.extend({}, void 0 !== c.colModel.formatoptions ? c.colModel.formatoptions : c.colModel.editoptions),
            c = void 0 === e.separator ? ":" : e.separator,
            f = void 0 === e.delimiter ? ";" : e.delimiter;
        if (e.value) {
            var g = e.value,
                e = !0 === e.multiple ? !0 : !1,
                h = [];
            if (e && (h = b.split(","), h = a.map(h, function(b) {
                    return a.trim(b)
                })), a.fmatter.isString(g)) {
                var i, j = g.split(f),
                    k = 0;
                for (i = 0; i < j.length; i++)
                    if (f = j[i].split(c), 2 < f.length && (f[1] = a.map(f, function(a, b) {
                            return b > 0 ? a : void 0
                        }).join(c)), e) - 1 < a.inArray(f[1], h) && (d[k] = f[0], k++);
                    else if (a.trim(f[1]) === a.trim(b)) {
                    d[0] = f[0];
                    break
                }
            } else(a.fmatter.isObject(g) || a.isArray(g)) && (e || (h[0] = b), d = a.map(h, function(b) {
                var c;
                return a.each(g, function(a, d) {
                    return d === b ? (c = a, !1) : void 0
                }), void 0 !== c ? c : void 0
            }));
            return d.join(", ")
        }
        return b || ""
    }, a.unformat.date = function(b, c) {
        var d = a.jgrid.formatter.date || {};
        return void 0 !== c.formatoptions && (d = a.extend({}, d, c.formatoptions)), a.fmatter.isEmpty(b) ? a.fn.fmatter.defaultFormat(b, c) : a.jgrid.parseDate(d.newformat, b, d.srcformat, d)
    }
}(jQuery),
function(a) {
    a.jgrid.extend({
        "getColProp": function(a) {
            var b = {},
                c = this[0];
            if (!c.grid) return !1;
            var d, c = c.p.colModel;
            for (d = 0; d < c.length; d++)
                if (c[d].name === a) {
                    b = c[d];
                    break
                }
            return b
        },
        "setColProp": function(b, c) {
            return this.each(function() {
                if (this.grid && c) {
                    var d, e = this.p.colModel;
                    for (d = 0; d < e.length; d++)
                        if (e[d].name === b) {
                            a.extend(!0, this.p.colModel[d], c);
                            break
                        }
                }
            })
        },
        "sortGrid": function(a, b, c) {
            return this.each(function() {
                var d, e = -1,
                    f = !1;
                if (this.grid) {
                    for (a || (a = this.p.sortname), d = 0; d < this.p.colModel.length; d++)
                        if (this.p.colModel[d].index === a || this.p.colModel[d].name === a) {
                            e = d, !0 === this.p.frozenColumns && !0 === this.p.colModel[d].frozen && (f = this.grid.fhDiv.find("#" + this.p.id + "_" + a));
                            break
                        } - 1 !== e && (d = this.p.colModel[e].sortable, "boolean" != typeof d && (d = !0), "boolean" != typeof b && (b = !1), d && this.sortData("jqgh_" + this.p.id + "_" + a, e, b, c, f))
                }
            })
        },
        "clearBeforeUnload": function() {
            return this.each(function() {
                var b = this.grid;
                b.emptyRows.call(this, !0, !0), a(document).unbind("mouseup.jqGrid" + this.p.id), a(b.hDiv).unbind("mousemove"), a(this).unbind(), b.dragEnd = null, b.dragMove = null, b.dragStart = null, b.emptyRows = null, b.populate = null, b.populateVisible = null, b.scrollGrid = null, b.selectionPreserver = null, b.bDiv = null, b.cDiv = null, b.hDiv = null, b.cols = null;
                var c, d = b.headers.length;
                for (c = 0; d > c; c++) b.headers[c].el = null;
                this.addJSONData = this.addXmlData = this.formatter = this.constructTr = this.setHeadCheckBox = this.refreshIndex = this.updatepager = this.sortData = this.formatCol = null
            })
        },
        "GridDestroy": function() {
            return this.each(function() {
                if (this.grid) {
                    this.p.pager && a(this.p.pager).remove();
                    try {
                        a(this).jqGrid("clearBeforeUnload"), a("#gbox_" + a.jgrid.jqID(this.id)).remove()
                    } catch (b) {}
                }
            })
        },
        "GridUnload": function() {
            return this.each(function() {
                if (this.grid) {
                    var b = a(this).attr("id"),
                        c = a(this).attr("class");
                    this.p.pager && a(this.p.pager).empty().removeClass("ui-state-default ui-jqgrid-pager corner-bottom");
                    var d = document.createElement("table");
                    a(d).attr({
                        "id": b
                    }), d.className = c, b = a.jgrid.jqID(this.id), a(d).removeClass("ui-jqgrid-btable"), 1 === a(this.p.pager).parents("#gbox_" + b).length ? (a(d).insertBefore("#gbox_" + b).show(), a(this.p.pager).insertBefore("#gbox_" + b)) : a(d).insertBefore("#gbox_" + b).show(), a(this).jqGrid("clearBeforeUnload"), a("#gbox_" + b).remove()
                }
            })
        },
        "setGridState": function(b) {
            return this.each(function() {
                this.grid && ("hidden" === b ? (a(".ui-jqgrid-bdiv, .ui-jqgrid-hdiv", "#gview_" + a.jgrid.jqID(this.p.id)).slideUp("fast"), this.p.pager && a(this.p.pager).slideUp("fast"), this.p.toppager && a(this.p.toppager).slideUp("fast"), !0 === this.p.toolbar[0] && ("both" === this.p.toolbar[1] && a(this.grid.ubDiv).slideUp("fast"), a(this.grid.uDiv).slideUp("fast")), this.p.footerrow && a(".ui-jqgrid-sdiv", "#gbox_" + a.jgrid.jqID(this.p.id)).slideUp("fast"), a(".ui-jqgrid-titlebar-close span", this.grid.cDiv).removeClass("ui-icon-circle-triangle-n").addClass("ui-icon-circle-triangle-s"), this.p.gridstate = "hidden") : "visible" === b && (a(".ui-jqgrid-hdiv, .ui-jqgrid-bdiv", "#gview_" + a.jgrid.jqID(this.p.id)).slideDown("fast"), this.p.pager && a(this.p.pager).slideDown("fast"), this.p.toppager && a(this.p.toppager).slideDown("fast"), !0 === this.p.toolbar[0] && ("both" === this.p.toolbar[1] && a(this.grid.ubDiv).slideDown("fast"), a(this.grid.uDiv).slideDown("fast")), this.p.footerrow && a(".ui-jqgrid-sdiv", "#gbox_" + a.jgrid.jqID(this.p.id)).slideDown("fast"), a(".ui-jqgrid-titlebar-close span", this.grid.cDiv).removeClass("ui-icon-circle-triangle-s").addClass("ui-icon-circle-triangle-n"), this.p.gridstate = "visible"))
            })
        },
        "filterToolbar": function(b) {
            return b = a.extend({
                "autosearch": !0,
                "searchOnEnter": !0,
                "beforeSearch": null,
                "afterSearch": null,
                "beforeClear": null,
                "afterClear": null,
                "searchurl": "",
                "stringResult": !1,
                "groupOp": "AND",
                "defaultSearch": "bw",
                "searchOperators": !1,
                "operandTitle": "Click to select search operation.",
                "operands": {
                    "eq": "==",
                    "ne": "!",
                    "lt": "<",
                    "le": "<=",
                    "gt": ">",
                    "ge": ">=",
                    "bw": "^",
                    "bn": "!^",
                    "in": "=",
                    "ni": "!=",
                    "ew": "|",
                    "en": "!@",
                    "cn": "~",
                    "nc": "!~",
                    "nu": "#",
                    "nn": "!#"
                }
            }, a.jgrid.search, b || {}), this.each(function() {
                var c = this;
                if (!this.ftoolbar) {
                    var d, e = function() {
                            var d, e, f, g = {},
                                h = 0,
                                i = {};
                            a.each(c.p.colModel, function() {
                                var j = a("#gs_" + a.jgrid.jqID(this.name), !0 === this.frozen && !0 === c.p.frozenColumns ? c.grid.fhDiv : c.grid.hDiv);
                                if (e = this.index || this.name, f = b.searchOperators ? j.parent().prev().children("a").attr("soper") || b.defaultSearch : this.searchoptions && this.searchoptions.sopt ? this.searchoptions.sopt[0] : "select" === this.stype ? "eq" : b.defaultSearch, (d = "custom" === this.stype && a.isFunction(this.searchoptions.custom_value) && 0 < j.length && "SPAN" === j[0].nodeName.toUpperCase() ? this.searchoptions.custom_value.call(c, j.children(".customelement:first"), "get") : j.val()) || "nu" === f || "nn" === f) g[e] = d, i[e] = f, h++;
                                else try {
                                    delete c.p.postData[e]
                                } catch (k) {}
                            });
                            var j = h > 0 ? !0 : !1;
                            if (!0 === b.stringResult || "local" === c.p.datatype) {
                                var k = '{"groupOp":"' + b.groupOp + '","rules":[',
                                    l = 0;
                                a.each(g, function(a, b) {
                                    l > 0 && (k += ","), k += '{"field":"' + a + '",', k += '"op":"' + i[a] + '",', k += '"data":"' + (b + "").replace(/\\/g, "\\\\").replace(/\"/g, '\\"') + '"}', l++
                                }), k += "]}", a.extend(c.p.postData, {
                                    "filters": k
                                }), a.each(["searchField", "searchString", "searchOper"], function(a, b) {
                                    c.p.postData.hasOwnProperty(b) && delete c.p.postData[b]
                                })
                            } else a.extend(c.p.postData, g);
                            var m;
                            c.p.searchurl && (m = c.p.url, a(c).jqGrid("setGridParam", {
                                "url": c.p.searchurl
                            }));
                            var n = "stop" === a(c).triggerHandler("jqGridToolbarBeforeSearch") ? !0 : !1;
                            !n && a.isFunction(b.beforeSearch) && (n = b.beforeSearch.call(c)), n || a(c).jqGrid("setGridParam", {
                                "search": j
                            }).trigger("reloadGrid", [{
                                "page": 1
                            }]), m && a(c).jqGrid("setGridParam", {
                                "url": m
                            }), a(c).triggerHandler("jqGridToolbarAfterSearch"), a.isFunction(b.afterSearch) && b.afterSearch.call(c)
                        },
                        f = function(d, f, g) {
                            a("#sopt_menu").remove();
                            var h, i, f = parseInt(f, 10),
                                g = parseInt(g, 10) + 18,
                                f = '<ul id="sopt_menu" class="ui-search-menu" role="menu" tabindex="0" style="font-size:' + (a(".ui-jqgrid-view").css("font-size") || "11px") + ";left:" + f + "px;top:" + g + 'px;">',
                                g = a(d).attr("soper"),
                                j = [],
                                k = 0,
                                l = a(d).attr("colname");
                            for (h = c.p.colModel.length; h > k && c.p.colModel[k].name !== l;) k++;
                            for (k = c.p.colModel[k], l = a.extend({}, k.searchoptions), l.sopt || (l.sopt = [], l.sopt[0] = "select" === k.stype ? "eq" : b.defaultSearch), a.each(b.odata, function() {
                                    j.push(this.oper)
                                }), k = 0; k < l.sopt.length; k++) i = a.inArray(l.sopt[k], j), -1 !== i && (h = g === b.odata[i].oper ? "ui-state-highlight" : "", f += '<li class="ui-menu-item ' + h + '" role="presentation"><a class="ui-corner-all g-menu-item" tabindex="0" role="menuitem" value="' + b.odata[i].oper + '" oper="' + b.operands[b.odata[i].oper] + '"><table cellspacing="0" cellpadding="0" border="0"><tr><td width="25px">' + b.operands[b.odata[i].oper] + "</td><td>" + b.odata[i].text + "</td></tr></table></a></li>");
                            a("body").append(f + "</ul>"), a("#sopt_menu").addClass("ui-menu ui-widget ui-widget-content ui-corner-all"), a("#sopt_menu > li > a").hover(function() {
                                a(this).addClass("ui-state-hover")
                            }, function() {
                                a(this).removeClass("ui-state-hover")
                            }).click(function() {
                                var f = a(this).attr("value"),
                                    g = a(this).attr("oper");
                                a(c).triggerHandler("jqGridToolbarSelectOper", [f, g, d]), a("#sopt_menu").hide(), a(d).text(g).attr("soper", f), b.autosearch === !0 && (g = a(d).parent().next().children()[0], (a(g).val() || "nu" === f || "nn" === f) && e())
                            })
                        },
                        g = a("<tr class='ui-search-toolbar' role='rowheader'></tr>");
                    a.each(c.p.colModel, function(f) {
                        var h, i, j = this;
                        i = "";
                        var k, l = "=",
                            m = a("<th role='columnheader' class='ui-state-default ui-th-column ui-th-" + c.p.direction + "'></th>"),
                            n = a("<div style='position:relative;height:100%;padding-right:0.3em;padding-left:0.3em;'></div>"),
                            o = a("<table class='ui-search-table' cellspacing='0'><tr><td class='ui-search-oper'></td><td class='ui-search-input'></td><td class='ui-search-clear'></td></tr></table>");
                        if (!0 === this.hidden && a(m).css("display", "none"), this.search = !1 === this.search ? !1 : !0, void 0 === this.stype && (this.stype = "text"), h = a.extend({}, this.searchoptions || {}), this.search) {
                            if (b.searchOperators) {
                                for (i = h.sopt ? h.sopt[0] : "select" === j.stype ? "eq" : b.defaultSearch, k = 0; k < b.odata.length; k++)
                                    if (b.odata[k].oper === i) {
                                        l = b.operands[i] || "";
                                        break
                                    }
                                i = "<a title='" + (null != h.searchtitle ? h.searchtitle : b.operandTitle) + "' style='padding-right: 0.5em;' soper='" + i + "' class='soptclass' colname='" + this.name + "'>" + l + "</a>"
                            }
                            switch (a("td:eq(0)", o).attr("colindex", f).append(i), void 0 === h.clearSearch && (h.clearSearch = !0), h.clearSearch && a("td:eq(2)", o).append("<a title='Clear Search Value' style='padding-right: 0.3em;padding-left: 0.3em;' class='clearsearchclass'>x</a>"), this.stype) {
                                case "select":
                                    if (i = this.surl || h.dataUrl) a(n).append(o), a.ajax(a.extend({
                                        "url": i,
                                        "dataType": "html",
                                        "success": function(d) {
                                            void 0 !== h.buildSelect ? (d = h.buildSelect(d)) && a("td:eq(1)", o).append(d) : a("td:eq(1)", o).append(d), void 0 !== h.defaultValue && a("select", n).val(h.defaultValue), a("select", n).attr({
                                                "name": j.index || j.name,
                                                "id": "gs_" + j.name
                                            }), h.attr && a("select", n).attr(h.attr), a("select", n).css({
                                                "width": "100%"
                                            }), a.jgrid.bindEv.call(c, a("select", n)[0], h), b.autosearch === !0 && a("select", n).change(function() {
                                                return e(), !1
                                            }), d = null
                                        }
                                    }, a.jgrid.ajaxOptions, c.p.ajaxSelectOptions || {}));
                                    else {
                                        var p, q, r;
                                        if (j.searchoptions ? (p = void 0 === j.searchoptions.value ? "" : j.searchoptions.value, q = void 0 === j.searchoptions.separator ? ":" : j.searchoptions.separator, r = void 0 === j.searchoptions.delimiter ? ";" : j.searchoptions.delimiter) : j.editoptions && (p = void 0 === j.editoptions.value ? "" : j.editoptions.value, q = void 0 === j.editoptions.separator ? ":" : j.editoptions.separator, r = void 0 === j.editoptions.delimiter ? ";" : j.editoptions.delimiter), p) {
                                            f = document.createElement("select"), f.style.width = "100%", a(f).attr({
                                                "name": j.index || j.name,
                                                "id": "gs_" + j.name
                                            });
                                            var s;
                                            if ("string" == typeof p)
                                                for (i = p.split(r), s = 0; s < i.length; s++) p = i[s].split(q), r = document.createElement("option"), r.value = p[0], r.innerHTML = p[1], f.appendChild(r);
                                            else if ("object" == typeof p)
                                                for (s in p) p.hasOwnProperty(s) && (r = document.createElement("option"), r.value = s, r.innerHTML = p[s], f.appendChild(r));
                                            void 0 !== h.defaultValue && a(f).val(h.defaultValue), h.attr && a(f).attr(h.attr), a(n).append(o), a.jgrid.bindEv.call(c, f, h), a("td:eq(1)", o).append(f), !0 === b.autosearch && a(f).change(function() {
                                                return e(), !1
                                            })
                                        }
                                    }
                                    break;
                                case "text":
                                    q = void 0 !== h.defaultValue ? h.defaultValue : "", a("td:eq(1)", o).append("<input type='text' style='width:100%;padding:0px;' name='" + (j.index || j.name) + "' id='gs_" + j.name + "' value='" + q + "'/>"), a(n).append(o), h.attr && a("input", n).attr(h.attr), a.jgrid.bindEv.call(c, a("input", n)[0], h), !0 === b.autosearch && (b.searchOnEnter ? a("input", n).keypress(function(a) {
                                        return 13 === (a.charCode || a.keyCode || 0) ? (e(), !1) : this
                                    }) : a("input", n).keydown(function(a) {
                                        switch (a.which) {
                                            case 13:
                                                return !1;
                                            case 9:
                                            case 16:
                                            case 37:
                                            case 38:
                                            case 39:
                                            case 40:
                                            case 27:
                                                break;
                                            default:
                                                d && clearTimeout(d), d = setTimeout(function() {
                                                    e()
                                                }, 500)
                                        }
                                    }));
                                    break;
                                case "custom":
                                    a("td:eq(1)", o).append("<span style='width:95%;padding:0px;' name='" + (j.index || j.name) + "' id='gs_" + j.name + "'/>"), a(n).append(o);
                                    try {
                                        if (!a.isFunction(h.custom_element)) throw "e1";
                                        var t = h.custom_element.call(c, void 0 !== h.defaultValue ? h.defaultValue : "", h);
                                        if (!t) throw "e2";
                                        t = a(t).addClass("customelement"), a(n).find(">span").append(t)
                                    } catch (u) {
                                        "e1" === u && a.jgrid.info_dialog(a.jgrid.errors.errcap, "function 'custom_element' " + a.jgrid.edit.msg.nodefined, a.jgrid.edit.bClose), "e2" === u ? a.jgrid.info_dialog(a.jgrid.errors.errcap, "function 'custom_element' " + a.jgrid.edit.msg.novalue, a.jgrid.edit.bClose) : a.jgrid.info_dialog(a.jgrid.errors.errcap, "string" == typeof u ? u : u.message, a.jgrid.edit.bClose)
                                    }
                            }
                        }
                        a(m).append(n), a(g).append(m), b.searchOperators || a("td:eq(0)", o).hide()
                    }), a("table thead", c.grid.hDiv).append(g), b.searchOperators && (a(".soptclass", g).click(function(b) {
                        var c = a(this).offset();
                        f(this, c.left, c.top), b.stopPropagation()
                    }), a("body").on("click", function(b) {
                        "soptclass" !== b.target.className && a("#sopt_menu").hide()
                    })), a(".clearsearchclass", g).click(function() {
                        var d = a(this).parents("tr:first"),
                            f = parseInt(a("td.ui-search-oper", d).attr("colindex"), 10),
                            g = a.extend({}, c.p.colModel[f].searchoptions || {}),
                            g = g.defaultValue ? g.defaultValue : "";
                        "select" === c.p.colModel[f].stype ? g ? a("td.ui-search-input select", d).val(g) : a("td.ui-search-input select", d)[0].selectedIndex = 0 : a("td.ui-search-input input", d).val(g), b.autosearch === !0 && e()
                    }), this.ftoolbar = !0, this.triggerToolbar = e, this.clearToolbar = function(d) {
                        var e, f = {},
                            g = 0,
                            d = "boolean" != typeof d ? !0 : d;
                        a.each(c.p.colModel, function() {
                            var b, d = a("#gs_" + a.jgrid.jqID(this.name), this.frozen === !0 && c.p.frozenColumns === !0 ? c.grid.fhDiv : c.grid.hDiv);
                            switch (this.searchoptions && void 0 !== this.searchoptions.defaultValue && (b = this.searchoptions.defaultValue), e = this.index || this.name, this.stype) {
                                case "select":
                                    if (d.find("option").each(function(c) {
                                            return 0 === c && (this.selected = !0), a(this).val() === b ? (this.selected = !0, !1) : void 0
                                        }), void 0 !== b) f[e] = b, g++;
                                    else try {
                                        delete c.p.postData[e]
                                    } catch (h) {}
                                    break;
                                case "text":
                                    if (d.val(b), void 0 !== b) f[e] = b, g++;
                                    else try {
                                        delete c.p.postData[e]
                                    } catch (i) {}
                                    break;
                                case "custom":
                                    a.isFunction(this.searchoptions.custom_value) && d.length > 0 && "SPAN" === d[0].nodeName.toUpperCase() && this.searchoptions.custom_value.call(c, d.children(".customelement:first"), "set", b)
                            }
                        });
                        var h = g > 0 ? !0 : !1;
                        if (b.stringResult === !0 || "local" === c.p.datatype) {
                            var i = '{"groupOp":"' + b.groupOp + '","rules":[',
                                j = 0;
                            a.each(f, function(a, b) {
                                j > 0 && (i += ","), i += '{"field":"' + a + '",', i += '"op":"eq",', i += '"data":"' + (b + "").replace(/\\/g, "\\\\").replace(/\"/g, '\\"') + '"}', j++
                            }), i += "]}", a.extend(c.p.postData, {
                                "filters": i
                            }), a.each(["searchField", "searchString", "searchOper"], function(a, b) {
                                c.p.postData.hasOwnProperty(b) && delete c.p.postData[b]
                            })
                        } else a.extend(c.p.postData, f);
                        var k;
                        c.p.searchurl && (k = c.p.url, a(c).jqGrid("setGridParam", {
                            "url": c.p.searchurl
                        }));
                        var l = "stop" === a(c).triggerHandler("jqGridToolbarBeforeClear") ? !0 : !1;
                        !l && a.isFunction(b.beforeClear) && (l = b.beforeClear.call(c)), l || d && a(c).jqGrid("setGridParam", {
                            "search": h
                        }).trigger("reloadGrid", [{
                            "page": 1
                        }]), k && a(c).jqGrid("setGridParam", {
                            "url": k
                        }), a(c).triggerHandler("jqGridToolbarAfterClear"), a.isFunction(b.afterClear) && b.afterClear()
                    }, this.toggleToolbar = function() {
                        var b = a("tr.ui-search-toolbar", c.grid.hDiv),
                            d = c.p.frozenColumns === !0 ? a("tr.ui-search-toolbar", c.grid.fhDiv) : !1;
                        "none" === b.css("display") ? (b.show(), d && d.show()) : (b.hide(), d && d.hide())
                    }
                }
            })
        },
        "destroyFilterToolbar": function() {
            return this.each(function() {
                this.ftoolbar && (this.toggleToolbar = this.clearToolbar = this.triggerToolbar = null, this.ftoolbar = !1, a(this.grid.hDiv).find("table thead tr.ui-search-toolbar").remove())
            })
        },
        "destroyGroupHeader": function(b) {
            return void 0 === b && (b = !0), this.each(function() {
                var c, d, e, f, g, h;
                d = this.grid;
                var i = a("table.ui-jqgrid-htable thead", d.hDiv),
                    j = this.p.colModel;
                if (d) {
                    for (a(this).unbind(".setGroupHeaders"), c = a("<tr>", {
                            "role": "rowheader"
                        }).addClass("ui-jqgrid-labels"), f = d.headers, d = 0, e = f.length; e > d; d++) {
                        g = j[d].hidden ? "none" : "", g = a(f[d].el).width(f[d].width).css("display", g);
                        try {
                            g.removeAttr("rowSpan")
                        } catch (k) {
                            g.attr("rowSpan", 1)
                        }
                        c.append(g), h = g.children("span.ui-jqgrid-resize"), 0 < h.length && (h[0].style.height = ""), g.children("div")[0].style.top = ""
                    }
                    a(i).children("tr.ui-jqgrid-labels").remove(), a(i).prepend(c), !0 === b && a(this).jqGrid("setGridParam", {
                        "groupHeader": null
                    })
                }
            })
        },
        "setGroupHeaders": function(b) {
            return b = a.extend({
                "useColSpanStyle": !1,
                "groupHeaders": []
            }, b || {}), this.each(function() {
                this.p.groupHeader = b;
                var c, d, e, f, g, h, i, j = 0,
                    k = this.p.colModel,
                    l = k.length,
                    m = this.grid.headers,
                    n = a("table.ui-jqgrid-htable", this.grid.hDiv),
                    o = n.children("thead").children("tr.ui-jqgrid-labels:last").addClass("jqg-second-row-header");
                e = n.children("thead");
                var p = n.find(".jqg-first-row-header");
                void 0 === p[0] ? p = a("<tr>", {
                    "role": "row",
                    "aria-hidden": "true"
                }).addClass("jqg-first-row-header").css("height", "auto") : p.empty();
                var q, r = function(a, b) {
                    var c, d = b.length;
                    for (c = 0; d > c; c++)
                        if (b[c].startColumnName === a) return c;
                    return -1
                };
                for (a(this).prepend(e), e = a("<tr>", {
                        "role": "rowheader"
                    }).addClass("ui-jqgrid-labels jqg-third-row-header"), c = 0; l > c; c++)
                    if (g = m[c].el, h = a(g), d = k[c], f = {
                            "height": "0px",
                            "width": m[c].width + "px",
                            "display": d.hidden ? "none" : ""
                        }, a("<th>", {
                            "role": "gridcell"
                        }).css(f).addClass("ui-first-th-" + this.p.direction).appendTo(p), g.style.width = "", f = r(d.name, b.groupHeaders), f >= 0) {
                        for (f = b.groupHeaders[f], j = f.numberOfColumns, i = f.titleText, f = d = 0; j > f && l > c + f; f++) k[c + f].hidden || d++;
                        f = a("<th>").attr({
                            "role": "columnheader"
                        }).addClass("ui-state-default ui-th-column-header ui-th-" + this.p.direction).css({
                            "height": "22px",
                            "border-top": "0px none"
                        }).html(i), d > 0 && f.attr("colspan", "" + d), this.p.headertitles && f.attr("title", f.text()), 0 === d && f.hide(), h.before(f), e.append(g), j -= 1
                    } else 0 === j ? b.useColSpanStyle ? h.attr("rowspan", "2") : (a("<th>", {
                        "role": "columnheader"
                    }).addClass("ui-state-default ui-th-column-header ui-th-" + this.p.direction).css({
                        "display": d.hidden ? "none" : "",
                        "border-top": "0px none"
                    }).insertBefore(h), e.append(g)) : (e.append(g), j--);
                k = a(this).children("thead"), k.prepend(p), e.insertAfter(o), n.append(k), b.useColSpanStyle && (n.find("span.ui-jqgrid-resize").each(function() {
                    var b = a(this).parent();
                    b.is(":visible") && (this.style.cssText = "height: " + b.height() + "px !important; cursor: col-resize;")
                }), n.find("div.ui-jqgrid-sortable").each(function() {
                    var b = a(this),
                        c = b.parent();
                    c.is(":visible") && c.is(":has(span.ui-jqgrid-resize)") && b.css("top", (c.height() - b.outerHeight()) / 2 + "px")
                })), q = k.find("tr.jqg-first-row-header"), a(this).bind("jqGridResizeStop.setGroupHeaders", function(a, b, c) {
                    q.find("th").eq(c).width(b)
                })
            })
        },
        "setFrozenColumns": function() {
            return this.each(function() {
                if (this.grid) {
                    var b = this,
                        c = b.p.colModel,
                        d = 0,
                        e = c.length,
                        f = -1,
                        g = !1;
                    if (!(!0 === b.p.subGrid || !0 === b.p.treeGrid || !0 === b.p.cellEdit || b.p.sortable || b.p.scroll || b.p.grouping)) {
                        for (b.p.rownumbers && d++, b.p.multiselect && d++; e > d && !0 === c[d].frozen;) g = !0, f = d, d++;
                        if (f >= 0 && g) {
                            if (c = b.p.caption ? a(b.grid.cDiv).outerHeight() : 0, d = a(".ui-jqgrid-htable", "#gview_" + a.jgrid.jqID(b.p.id)).height(), b.p.toppager && (c += a(b.grid.topDiv).outerHeight()), !0 === b.p.toolbar[0] && "bottom" !== b.p.toolbar[1] && (c += a(b.grid.uDiv).outerHeight()), b.grid.fhDiv = a('<div style="position:absolute;left:0px;top:' + c + "px;height:" + d + 'px;" class="frozen-div ui-state-default ui-jqgrid-hdiv"></div>'), b.grid.fbDiv = a('<div style="position:absolute;left:0px;top:' + (parseInt(c, 10) + parseInt(d, 10) + 1) + 'px;overflow-y:hidden" class="frozen-bdiv ui-jqgrid-bdiv"></div>'), a("#gview_" + a.jgrid.jqID(b.p.id)).append(b.grid.fhDiv), c = a(".ui-jqgrid-htable", "#gview_" + a.jgrid.jqID(b.p.id)).clone(!0), b.p.groupHeader) {
                                a("tr.jqg-first-row-header, tr.jqg-third-row-header", c).each(function() {
                                    a("th:gt(" + f + ")", this).remove()
                                });
                                var h, i, j = -1,
                                    k = -1;
                                a("tr.jqg-second-row-header th", c).each(function() {
                                    return h = parseInt(a(this).attr("colspan"), 10), (i = parseInt(a(this).attr("rowspan"), 10)) && (j++, k++), h && (j += h, k++), j === f ? !1 : void 0
                                }), j !== f && (k = f), a("tr.jqg-second-row-header", c).each(function() {
                                    a("th:gt(" + k + ")", this).remove()
                                })
                            } else a("tr", c).each(function() {
                                a("th:gt(" + f + ")", this).remove()
                            });
                            a(c).width(1), a(b.grid.fhDiv).append(c).mousemove(function(a) {
                                return b.grid.resizing ? (b.grid.dragMove(a), !1) : void 0
                            }), a(b).bind("jqGridResizeStop.setFrozenColumns", function(c, d, e) {
                                c = a(".ui-jqgrid-htable", b.grid.fhDiv), a("th:eq(" + e + ")", c).width(d), c = a(".ui-jqgrid-btable", b.grid.fbDiv), a("tr:first td:eq(" + e + ")", c).width(d)
                            }), a(b).bind("jqGridSortCol.setFrozenColumns", function(c, d, e) {
                                c = a("tr.ui-jqgrid-labels:last th:eq(" + b.p.lastsort + ")", b.grid.fhDiv), d = a("tr.ui-jqgrid-labels:last th:eq(" + e + ")", b.grid.fhDiv), a("span.ui-grid-ico-sort", c).addClass("ui-state-disabled"), a(c).attr("aria-selected", "false"), a("span.ui-icon-" + b.p.sortorder, d).removeClass("ui-state-disabled"), a(d).attr("aria-selected", "true"), !b.p.viewsortcols[0] && b.p.lastsort !== e && (a("span.s-ico", c).hide(), a("span.s-ico", d).show())
                            }), a("#gview_" + a.jgrid.jqID(b.p.id)).append(b.grid.fbDiv), a(b.grid.bDiv).scroll(function() {
                                a(b.grid.fbDiv).scrollTop(a(this).scrollTop())
                            }), !0 === b.p.hoverrows && a("#" + a.jgrid.jqID(b.p.id)).unbind("mouseover").unbind("mouseout"), a(b).bind("jqGridAfterGridComplete.setFrozenColumns", function() {
                                a("#" + a.jgrid.jqID(b.p.id) + "_frozen").remove(), a(b.grid.fbDiv).height(a(b.grid.bDiv).height() - 16);
                                var c = a("#" + a.jgrid.jqID(b.p.id)).clone(!0);
                                a("tr[role=row]", c).each(function() {
                                    a("td[role=gridcell]:gt(" + f + ")", this).remove()
                                }), a(c).width(1).attr("id", b.p.id + "_frozen"), a(b.grid.fbDiv).append(c), !0 === b.p.hoverrows && (a("tr.jqgrow", c).hover(function() {
                                    a(this).addClass("ui-state-hover"), a("#" + a.jgrid.jqID(this.id), "#" + a.jgrid.jqID(b.p.id)).addClass("ui-state-hover")
                                }, function() {
                                    a(this).removeClass("ui-state-hover"), a("#" + a.jgrid.jqID(this.id), "#" + a.jgrid.jqID(b.p.id)).removeClass("ui-state-hover")
                                }), a("tr.jqgrow", "#" + a.jgrid.jqID(b.p.id)).hover(function() {
                                    a(this).addClass("ui-state-hover"), a("#" + a.jgrid.jqID(this.id), "#" + a.jgrid.jqID(b.p.id) + "_frozen").addClass("ui-state-hover")
                                }, function() {
                                    a(this).removeClass("ui-state-hover"), a("#" + a.jgrid.jqID(this.id), "#" + a.jgrid.jqID(b.p.id) + "_frozen").removeClass("ui-state-hover")
                                })), c = null
                            }), b.grid.hDiv.loading || a(b).triggerHandler("jqGridAfterGridComplete"), b.p.frozenColumns = !0
                        }
                    }
                }
            })
        },
        "destroyFrozenColumns": function() {
            return this.each(function() {
                if (this.grid && !0 === this.p.frozenColumns) {
                    if (a(this.grid.fhDiv).remove(), a(this.grid.fbDiv).remove(), this.grid.fhDiv = null, this.grid.fbDiv = null, a(this).unbind(".setFrozenColumns"), !0 === this.p.hoverrows) {
                        var b;
                        a("#" + a.jgrid.jqID(this.p.id)).bind("mouseover", function(c) {
                            b = a(c.target).closest("tr.jqgrow"), "ui-subgrid" !== a(b).attr("class") && a(b).addClass("ui-state-hover")
                        }).bind("mouseout", function(c) {
                            b = a(c.target).closest("tr.jqgrow"), a(b).removeClass("ui-state-hover")
                        })
                    }
                    this.p.frozenColumns = !1
                }
            })
        }
    })
}(jQuery),
function(a) {
    a.extend(a.jgrid, {
        "showModal": function(a) {
            a.w.show()
        },
        "closeModal": function(a) {
            a.w.hide().attr("aria-hidden", "true"), a.o && a.o.remove()
        },
        "hideModal": function(b, c) {
            if (c = a.extend({
                    "jqm": !0,
                    "gb": ""
                }, c || {}), c.onClose) {
                var d = c.gb && "string" == typeof c.gb && "#gbox_" === c.gb.substr(0, 6) ? c.onClose.call(a("#" + c.gb.substr(6))[0], b) : c.onClose(b);
                if ("boolean" == typeof d && !d) return
            }
            if (a.fn.jqm && !0 === c.jqm) a(b).attr("aria-hidden", "true").jqmHide();
            else {
                if ("" !== c.gb) try {
                    a(".jqgrid-overlay:first", c.gb).hide()
                } catch (e) {}
                a(b).hide().attr("aria-hidden", "true")
            }
        },
        "findPos": function(a) {
            var b = 0,
                c = 0;
            if (a.offsetParent)
                do b += a.offsetLeft, c += a.offsetTop; while (a = a.offsetParent);
            return [b, c]
        },
        "createModal": function(b, c, d, e, f, g, h) {
            var i, d = a.extend(!0, {}, a.jgrid.jqModal || {}, d),
                j = document.createElement("div"),
                k = this,
                h = a.extend({}, h || {});
            i = "rtl" === a(d.gbox).attr("dir") ? !0 : !1, j.className = "ui-widget ui-widget-content ui-corner-all ui-jqdialog", j.id = b.themodal;
            var l = document.createElement("div");
            l.className = "ui-jqdialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix", l.id = b.modalhead, a(l).append("<span class='ui-jqdialog-title'>" + d.caption + "</span>");
            var m = a("<a class='ui-jqdialog-titlebar-close ui-corner-all'></a>").hover(function() {
                m.addClass("ui-state-hover")
            }, function() {
                m.removeClass("ui-state-hover")
            }).append("<span class='ui-icon ui-icon-closethick'></span>");
            a(l).append(m), i ? (j.dir = "rtl", a(".ui-jqdialog-title", l).css("float", "right"), a(".ui-jqdialog-titlebar-close", l).css("left", "0.3em")) : (j.dir = "ltr", a(".ui-jqdialog-title", l).css("float", "left"), a(".ui-jqdialog-titlebar-close", l).css("right", "0.3em"));
            var n = document.createElement("div");
            if (a(n).addClass("ui-jqdialog-content ui-widget-content").attr("id", b.modalcontent), a(n).append(c), j.appendChild(n), a(j).prepend(l), !0 === g ? a("body").append(j) : "string" == typeof g ? a(g).append(j) : a(j).insertBefore(e), a(j).css(h), void 0 === d.jqModal && (d.jqModal = !0), c = {}, a.fn.jqm && !0 === d.jqModal ? (0 === d.left && 0 === d.top && d.overlay && (h = [], h = a.jgrid.findPos(f), d.left = h[0] + 4, d.top = h[1] + 4), c.top = d.top + "px", c.left = d.left) : (0 !== d.left || 0 !== d.top) && (c.left = d.left, c.top = d.top + "px"), a("a.ui-jqdialog-titlebar-close", l).click(function() {
                    var c = a("#" + a.jgrid.jqID(b.themodal)).data("onClose") || d.onClose,
                        e = a("#" + a.jgrid.jqID(b.themodal)).data("gbox") || d.gbox;
                    return k.hideModal("#" + a.jgrid.jqID(b.themodal), {
                        "gb": e,
                        "jqm": d.jqModal,
                        "onClose": c
                    }), !1
                }), 0 !== d.width && d.width || (d.width = 300), 0 !== d.height && d.height || (d.height = 200), d.zIndex || (e = a(e).parents("*[role=dialog]").filter(":first").css("z-index"), d.zIndex = e ? parseInt(e, 10) + 2 : 950), e = 0, i && c.left && !g && (e = a(d.gbox).width() - (isNaN(d.width) ? 0 : parseInt(d.width, 10)) - 8, c.left = parseInt(c.left, 10) + parseInt(e, 10)), c.left && (c.left += "px"), a(j).css(a.extend({
                    "width": isNaN(d.width) ? "auto" : d.width + "px",
                    "height": isNaN(d.height) ? "auto" : d.height + "px",
                    "zIndex": d.zIndex,
                    "overflow": "hidden"
                }, c)).attr({
                    "tabIndex": "-1",
                    "role": "dialog",
                    "aria-labelledby": b.modalhead,
                    "aria-hidden": "true"
                }), void 0 === d.drag && (d.drag = !0), void 0 === d.resize && (d.resize = !0), d.drag)
                if (a(l).css("cursor", "move"), a.fn.jqDrag) a(j).jqDrag(l);
                else try {
                    a(j).draggable({
                        "handle": a("#" + a.jgrid.jqID(l.id))
                    })
                } catch (o) {}
                if (d.resize)
                    if (a.fn.jqResize) a(j).append("<div class='jqResize ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se'></div>"), a("#" + a.jgrid.jqID(b.themodal)).jqResize(".jqResize", b.scrollelm ? "#" + a.jgrid.jqID(b.scrollelm) : !1);
                    else try {
                        a(j).resizable({
                            "handles": "se, sw",
                            "alsoResize": b.scrollelm ? "#" + a.jgrid.jqID(b.scrollelm) : !1
                        })
                    } catch (p) {}!0 === d.closeOnEscape && a(j).keydown(function(c) {
                        27 == c.which && (c = a("#" + a.jgrid.jqID(b.themodal)).data("onClose") || d.onClose, k.hideModal("#" + a.jgrid.jqID(b.themodal), {
                            "gb": d.gbox,
                            "jqm": d.jqModal,
                            "onClose": c
                        }))
                    })
        },
        "viewModal": function(b, c) {
            if (c = a.extend({
                    "toTop": !0,
                    "overlay": 10,
                    "modal": !1,
                    "overlayClass": "ui-widget-overlay",
                    "onShow": a.jgrid.showModal,
                    "onHide": a.jgrid.closeModal,
                    "gbox": "",
                    "jqm": !0,
                    "jqM": !0
                }, c || {}), a.fn.jqm && !0 === c.jqm) c.jqM ? a(b).attr("aria-hidden", "false").jqm(c).jqmShow() : a(b).attr("aria-hidden", "false").jqmShow();
            else {
                "" !== c.gbox && (a(".jqgrid-overlay:first", c.gbox).show(), a(b).data("gbox", c.gbox)), a(b).show().attr("aria-hidden", "false");
                try {
                    a(":input:visible", b)[0].focus()
                } catch (d) {}
            }
        },
        "info_dialog": function(b, c, d, e) {
            var f = {
                "width": 290,
                "height": "auto",
                "dataheight": "auto",
                "drag": !0,
                "resize": !1,
                "left": 250,
                "top": 170,
                "zIndex": 1e3,
                "jqModal": !0,
                "modal": !1,
                "closeOnEscape": !0,
                "align": "center",
                "buttonalign": "center",
                "buttons": []
            };
            a.extend(!0, f, a.jgrid.jqModal || {}, {
                "caption": "<b>" + b + "</b>"
            }, e || {});
            var g = f.jqModal,
                h = this;
            if (a.fn.jqm && !g && (g = !1), b = "", 0 < f.buttons.length)
                for (e = 0; e < f.buttons.length; e++) void 0 === f.buttons[e].id && (f.buttons[e].id = "info_button_" + e), b += "<a id='" + f.buttons[e].id + "' class='fm-button ui-state-default ui-corner-all'>" + f.buttons[e].text + "</a>";
            e = isNaN(f.dataheight) ? f.dataheight : f.dataheight + "px", c = "<div id='info_id'>" + ("<div id='infocnt' style='margin:0px;padding-bottom:1em;width:100%;overflow:auto;position:relative;height:" + e + ";" + ("text-align:" + f.align + ";") + "'>" + c + "</div>"), c += d ? "<div class='ui-widget-content ui-helper-clearfix' style='text-align:" + f.buttonalign + ";padding-bottom:0.8em;padding-top:0.5em;background-image: none;border-width: 1px 0 0 0;'><a id='closedialog' class='fm-button ui-state-default ui-corner-all'>" + d + "</a>" + b + "</div>" : "" !== b ? "<div class='ui-widget-content ui-helper-clearfix' style='text-align:" + f.buttonalign + ";padding-bottom:0.8em;padding-top:0.5em;background-image: none;border-width: 1px 0 0 0;'>" + b + "</div>" : "", c += "</div>";
            try {
                "false" === a("#info_dialog").attr("aria-hidden") && a.jgrid.hideModal("#info_dialog", {
                    "jqm": g
                }), a("#info_dialog").remove()
            } catch (i) {}
            a.jgrid.createModal({
                "themodal": "info_dialog",
                "modalhead": "info_head",
                "modalcontent": "info_content",
                "scrollelm": "infocnt"
            }, c, f, "", "", !0), b && a.each(f.buttons, function(b) {
                a("#" + a.jgrid.jqID(this.id), "#info_id").bind("click", function() {
                    return f.buttons[b].onClick.call(a("#info_dialog")), !1
                })
            }), a("#closedialog", "#info_id").click(function() {
                return h.hideModal("#info_dialog", {
                    "jqm": g,
                    "onClose": a("#info_dialog").data("onClose") || f.onClose,
                    "gb": a("#info_dialog").data("gbox") || f.gbox
                }), !1
            }), a(".fm-button", "#info_dialog").hover(function() {
                a(this).addClass("ui-state-hover")
            }, function() {
                a(this).removeClass("ui-state-hover")
            }), a.isFunction(f.beforeOpen) && f.beforeOpen(), a.jgrid.viewModal("#info_dialog", {
                "onHide": function(a) {
                    a.w.hide().remove(), a.o && a.o.remove()
                },
                "modal": f.modal,
                "jqm": g
            }), a.isFunction(f.afterOpen) && f.afterOpen();
            try {
                a("#info_dialog").focus()
            } catch (j) {}
        },
        "bindEv": function(b, c) {
            a.isFunction(c.dataInit) && c.dataInit.call(this, b, c), c.dataEvents && a.each(c.dataEvents, function() {
                void 0 !== this.data ? a(b).bind(this.type, this.data, this.fn) : a(b).bind(this.type, this.fn)
            })
        },
        "createEl": function(b, c, d, e, f) {
            function g(b, c, d) {
                var e = "dataInit,dataEvents,dataUrl,buildSelect,sopt,searchhidden,defaultValue,attr,custom_element,custom_value".split(",");
                void 0 !== d && a.isArray(d) && a.merge(e, d), a.each(c, function(c, d) {
                    -1 === a.inArray(c, e) && a(b).attr(c, d)
                }), c.hasOwnProperty("id") || a(b).attr("id", a.jgrid.randId())
            }
            var h = "",
                i = this;
            switch (b) {
                case "textarea":
                    h = document.createElement("textarea"), e ? c.cols || a(h).css({
                        "width": "98%"
                    }) : c.cols || (c.cols = 20), c.rows || (c.rows = 2), ("&nbsp;" === d || "&#160;" === d || 1 === d.length && 160 === d.charCodeAt(0)) && (d = ""), h.value = d, g(h, c), a(h).attr({
                        "role": "textbox",
                        "multiline": "true"
                    });
                    break;
                case "checkbox":
                    h = document.createElement("input"), h.type = "checkbox", c.value ? (b = c.value.split(":"), d === b[0] && (h.checked = !0, h.defaultChecked = !0), h.value = b[0], a(h).attr("offval", b[1])) : (b = (d + "").toLowerCase(), 0 > b.search(/(false|f|0|no|n|off|undefined)/i) && "" !== b ? (h.checked = !0, h.defaultChecked = !0, h.value = d) : h.value = "on", a(h).attr("offval", "off")), g(h, c, ["value"]), a(h).attr("role", "checkbox");
                    break;
                case "select":
                    if (h = document.createElement("select"), h.setAttribute("role", "select"), e = [], !0 === c.multiple ? (b = !0, h.multiple = "multiple", a(h).attr("aria-multiselectable", "true")) : b = !1, void 0 !== c.dataUrl) {
                        var b = c.name ? ("" + c.id).substring(0, ("" + c.id).length - ("" + c.name).length - 1) : "" + c.id,
                            j = c.postData || f.postData;
                        i.p && i.p.idPrefix && (b = a.jgrid.stripPref(i.p.idPrefix, b)), a.ajax(a.extend({
                            "url": a.isFunction(c.dataUrl) ? c.dataUrl.call(i, b, d, "" + c.name) : c.dataUrl,
                            "type": "GET",
                            "dataType": "html",
                            "data": a.isFunction(j) ? j.call(i, b, d, "" + c.name) : j,
                            "context": {
                                "elem": h,
                                "options": c,
                                "vl": d
                            },
                            "success": function(b) {
                                var c = [],
                                    d = this.elem,
                                    e = this.vl,
                                    f = a.extend({}, this.options),
                                    h = f.multiple === !0,
                                    b = a.isFunction(f.buildSelect) ? f.buildSelect.call(i, b) : b;
                                "string" == typeof b && (b = a(a.trim(b)).html()), b && (a(d).append(b), g(d, f, j ? ["postData"] : void 0), void 0 === f.size && (f.size = h ? 3 : 1), h ? (c = e.split(","), c = a.map(c, function(b) {
                                    return a.trim(b)
                                })) : c[0] = a.trim(e), setTimeout(function() {
                                    a("option", d).each(function(b) {
                                        0 === b && d.multiple && (this.selected = !1), a(this).attr("role", "option"), (a.inArray(a.trim(a(this).text()), c) > -1 || a.inArray(a.trim(a(this).val()), c) > -1) && (this.selected = "selected")
                                    })
                                }, 0))
                            }
                        }, f || {}))
                    } else if (c.value) {
                        var k;
                        void 0 === c.size && (c.size = b ? 3 : 1), b && (e = d.split(","), e = a.map(e, function(b) {
                            return a.trim(b)
                        })), "function" == typeof c.value && (c.value = c.value());
                        var l, m, n = void 0 === c.separator ? ":" : c.separator,
                            f = void 0 === c.delimiter ? ";" : c.delimiter;
                        if ("string" == typeof c.value)
                            for (l = c.value.split(f), k = 0; k < l.length; k++) m = l[k].split(n), 2 < m.length && (m[1] = a.map(m, function(a, b) {
                                return b > 0 ? a : void 0
                            }).join(n)), f = document.createElement("option"), f.setAttribute("role", "option"), f.value = m[0], f.innerHTML = m[1], h.appendChild(f), b || a.trim(m[0]) !== a.trim(d) && a.trim(m[1]) !== a.trim(d) || (f.selected = "selected"), b && (-1 < a.inArray(a.trim(m[1]), e) || -1 < a.inArray(a.trim(m[0]), e)) && (f.selected = "selected");
                        else if ("object" == typeof c.value)
                            for (k in n = c.value) n.hasOwnProperty(k) && (f = document.createElement("option"), f.setAttribute("role", "option"), f.value = k, f.innerHTML = n[k], h.appendChild(f), b || a.trim(k) !== a.trim(d) && a.trim(n[k]) !== a.trim(d) || (f.selected = "selected"), b && (-1 < a.inArray(a.trim(n[k]), e) || -1 < a.inArray(a.trim(k), e)) && (f.selected = "selected"));
                        g(h, c, ["value"])
                    }
                    break;
                case "text":
                case "password":
                case "button":
                    k = "button" === b ? "button" : "textbox", h = document.createElement("input"), h.type = b, h.value = d, g(h, c), "button" !== b && (e ? c.size || a(h).css({
                        "width": "98%"
                    }) : c.size || (c.size = 20)), a(h).attr("role", k);
                    break;
                case "image":
                case "file":
                    h = document.createElement("input"), h.type = b, g(h, c);
                    break;
                case "custom":
                    h = document.createElement("span");
                    try {
                        if (!a.isFunction(c.custom_element)) throw "e1";
                        if (!(n = c.custom_element.call(i, d, c))) throw "e2";
                        n = a(n).addClass("customelement").attr({
                            "id": c.id,
                            "name": c.name
                        }), a(h).empty().append(n)
                    } catch (o) {
                        "e1" === o && a.jgrid.info_dialog(a.jgrid.errors.errcap, "function 'custom_element' " + a.jgrid.edit.msg.nodefined, a.jgrid.edit.bClose), "e2" === o ? a.jgrid.info_dialog(a.jgrid.errors.errcap, "function 'custom_element' " + a.jgrid.edit.msg.novalue, a.jgrid.edit.bClose) : a.jgrid.info_dialog(a.jgrid.errors.errcap, "string" == typeof o ? o : o.message, a.jgrid.edit.bClose)
                    }
            }
            return h
        },
        "checkDate": function(a, b) {
            var c, d = {},
                a = a.toLowerCase();
            if (c = -1 !== a.indexOf("/") ? "/" : -1 !== a.indexOf("-") ? "-" : -1 !== a.indexOf(".") ? "." : "/", a = a.split(c), b = b.split(c), 3 !== b.length) return !1;
            c = -1;
            var e, f, g = -1,
                h = -1;
            for (f = 0; f < a.length; f++) e = isNaN(b[f]) ? 0 : parseInt(b[f], 10), d[a[f]] = e, e = a[f], -1 !== e.indexOf("y") && (c = f), -1 !== e.indexOf("m") && (h = f), -1 !== e.indexOf("d") && (g = f);
            e = "y" === a[c] || "yyyy" === a[c] ? 4 : "yy" === a[c] ? 2 : -1, f = [0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
            var i;
            return -1 === c ? !1 : (i = d[a[c]].toString(), 2 === e && 1 === i.length && (e = 1), i.length !== e || 0 === d[a[c]] && "00" !== b[c] || -1 === h ? !1 : (i = d[a[h]].toString(), 1 > i.length || 1 > d[a[h]] || 12 < d[a[h]] || -1 === g ? !1 : (i = d[a[g]].toString(), 1 > i.length || 1 > d[a[g]] || 31 < d[a[g]] || 2 === d[a[h]] && d[a[g]] > (0 !== d[a[c]] % 4 || 0 === d[a[c]] % 100 && 0 !== d[a[c]] % 400 ? 28 : 29) || d[a[g]] > f[d[a[h]]] ? !1 : !0)))
        },
        "isEmpty": function(a) {
            return a.match(/^\s+$/) || "" === a ? !0 : !1
        },
        "checkTime": function(b) {
            var c = /^(\d{1,2}):(\d{2})([apAP][Mm])?$/;
            if (!a.jgrid.isEmpty(b)) {
                if (!(b = b.match(c))) return !1;
                if (b[3]) {
                    if (1 > b[1] || 12 < b[1]) return !1
                } else if (23 < b[1]) return !1;
                if (59 < b[2]) return !1
            }
            return !0
        },
        "checkValues": function(b, c, d, e) {
            var f, g, h;
            if (h = this.p.colModel, void 0 === d)
                if ("string" == typeof c) {
                    for (d = 0, e = h.length; e > d; d++)
                        if (h[d].name === c) {
                            f = h[d].editrules, c = d, null != h[d].formoptions && (g = h[d].formoptions.label);
                            break
                        }
                } else c >= 0 && (f = h[c].editrules);
            else f = d, g = void 0 === e ? "_" : e;
            if (f) {
                if (g || (g = null != this.p.colNames ? this.p.colNames[c] : h[c].label), !0 === f.required && a.jgrid.isEmpty(b)) return [!1, g + ": " + a.jgrid.edit.msg.required, ""];
                if (d = !1 === f.required ? !1 : !0, !0 === f.number && (!1 !== d || !a.jgrid.isEmpty(b)) && isNaN(b)) return [!1, g + ": " + a.jgrid.edit.msg.number, ""];
                if (void 0 !== f.minValue && !isNaN(f.minValue) && parseFloat(b) < parseFloat(f.minValue)) return [!1, g + ": " + a.jgrid.edit.msg.minValue + " " + f.minValue, ""];
                if (void 0 !== f.maxValue && !isNaN(f.maxValue) && parseFloat(b) > parseFloat(f.maxValue)) return [!1, g + ": " + a.jgrid.edit.msg.maxValue + " " + f.maxValue, ""];
                if (!(!0 !== f.email || !1 === d && a.jgrid.isEmpty(b) || (e = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, e.test(b)))) return [!1, g + ": " + a.jgrid.edit.msg.email, ""];
                if (!(!0 !== f.integer || !1 === d && a.jgrid.isEmpty(b) || !isNaN(b) && 0 === b % 1 && -1 === b.indexOf("."))) return [!1, g + ": " + a.jgrid.edit.msg.integer, ""];
                if (!(!0 !== f.date || !1 === d && a.jgrid.isEmpty(b) || (h[c].formatoptions && h[c].formatoptions.newformat ? (h = h[c].formatoptions.newformat, a.jgrid.formatter.date.masks.hasOwnProperty(h) && (h = a.jgrid.formatter.date.masks[h])) : h = h[c].datefmt || "Y-m-d", a.jgrid.checkDate(h, b)))) return [!1, g + ": " + a.jgrid.edit.msg.date + " - " + h, ""];
                if (!(!0 !== f.time || !1 === d && a.jgrid.isEmpty(b) || a.jgrid.checkTime(b))) return [!1, g + ": " + a.jgrid.edit.msg.date + " - hh:mm (am/pm)", ""];
                if (!(!0 !== f.url || !1 === d && a.jgrid.isEmpty(b) || (e = /^(((https?)|(ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i, e.test(b)))) return [!1, g + ": " + a.jgrid.edit.msg.url, ""];
                if (!0 === f.custom && (!1 !== d || !a.jgrid.isEmpty(b))) return a.isFunction(f.custom_func) ? (b = f.custom_func.call(this, b, g, c), a.isArray(b) ? b : [!1, a.jgrid.edit.msg.customarray, ""]) : [!1, a.jgrid.edit.msg.customfcheck, ""]
            }
            return [!0, "", ""]
        }
    })
}(jQuery),
function(a) {
    var b = {};
    a.jgrid.extend({
        "searchGrid": function(b) {
            return b = a.extend(!0, {
                "recreateFilter": !1,
                "drag": !0,
                "sField": "searchField",
                "sValue": "searchString",
                "sOper": "searchOper",
                "sFilter": "filters",
                "loadDefaults": !0,
                "beforeShowSearch": null,
                "afterShowSearch": null,
                "onInitializeSearch": null,
                "afterRedraw": null,
                "afterChange": null,
                "closeAfterSearch": !1,
                "closeAfterReset": !1,
                "closeOnEscape": !1,
                "searchOnEnter": !1,
                "multipleSearch": !1,
                "multipleGroup": !1,
                "top": 0,
                "left": 0,
                "jqModal": !0,
                "modal": !1,
                "resize": !0,
                "width": 450,
                "height": "auto",
                "dataheight": "auto",
                "showQuery": !1,
                "errorcheck": !0,
                "sopt": null,
                "stringResult": void 0,
                "onClose": null,
                "onSearch": null,
                "onReset": null,
                "toTop": !0,
                "overlay": 30,
                "columns": [],
                "tmplNames": null,
                "tmplFilters": null,
                "tmplLabel": " Template: ",
                "showOnLoad": !1,
                "layer": null,
                "operands": {
                    "eq": "=",
                    "ne": "<>",
                    "lt": "<",
                    "le": "<=",
                    "gt": ">",
                    "ge": ">=",
                    "bw": "LIKE",
                    "bn": "NOT LIKE",
                    "in": "IN",
                    "ni": "NOT IN",
                    "ew": "LIKE",
                    "en": "NOT LIKE",
                    "cn": "LIKE",
                    "nc": "NOT LIKE",
                    "nu": "IS NULL",
                    "nn": "ISNOT NULL"
                }
            }, a.jgrid.search, b || {}), this.each(function() {
                function c(c) {
                    f = a(d).triggerHandler("jqGridFilterBeforeShow", [c]), void 0 === f && (f = !0), f && a.isFunction(b.beforeShowSearch) && (f = b.beforeShowSearch.call(d, c)), f && (a.jgrid.viewModal("#" + a.jgrid.jqID(h.themodal), {
                        "gbox": "#gbox_" + a.jgrid.jqID(e),
                        "jqm": b.jqModal,
                        "modal": b.modal,
                        "overlay": b.overlay,
                        "toTop": b.toTop
                    }), a(d).triggerHandler("jqGridFilterAfterShow", [c]), a.isFunction(b.afterShowSearch) && b.afterShowSearch.call(d, c))
                }
                var d = this;
                if (d.grid) {
                    var e = "fbox_" + d.p.id,
                        f = !0,
                        g = !0,
                        h = {
                            "themodal": "searchmod" + e,
                            "modalhead": "searchhd" + e,
                            "modalcontent": "searchcnt" + e,
                            "scrollelm": e
                        },
                        i = d.p.postData[b.sFilter];
                    if ("string" == typeof i && (i = a.jgrid.parse(i)), !0 === b.recreateFilter && a("#" + a.jgrid.jqID(h.themodal)).remove(), void 0 !== a("#" + a.jgrid.jqID(h.themodal))[0]) c(a("#fbox_" + a.jgrid.jqID(+d.p.id)));
                    else {
                        var j = a("<div><div id='" + e + "' class='searchFilter' style='overflow:auto'></div></div>").insertBefore("#gview_" + a.jgrid.jqID(d.p.id)),
                            k = "left",
                            l = "";
                        "rtl" === d.p.direction && (k = "right", l = " style='text-align:left'", j.attr("dir", "rtl"));
                        var m, n = a.extend([], d.p.colModel),
                            o = "<a id='" + e + "_search' class='fm-button ui-state-default ui-corner-all fm-button-icon-right ui-reset'><span class='ui-icon ui-icon-search'></span>" + b.Find + "</a>",
                            p = "<a id='" + e + "_reset' class='fm-button ui-state-default ui-corner-all fm-button-icon-left ui-search'><span class='ui-icon ui-icon-arrowreturnthick-1-w'></span>" + b.Reset + "</a>",
                            q = "",
                            r = "",
                            s = !1,
                            t = -1;
                        if (b.showQuery && (q = "<a id='" + e + "_query' class='fm-button ui-state-default ui-corner-all fm-button-icon-left'><span class='ui-icon ui-icon-comment'></span>Query</a>"), b.columns.length ? (n = b.columns, t = 0, m = n[0].index || n[0].name) : a.each(n, function(a, b) {
                                if (b.label || (b.label = d.p.colNames[a]), !s) {
                                    var c = void 0 === b.search ? !0 : b.search,
                                        e = b.hidden === !0;
                                    (b.searchoptions && b.searchoptions.searchhidden === !0 && c || c && !e) && (s = !0, m = b.index || b.name, t = a)
                                }
                            }), !i && m || !1 === b.multipleSearch) {
                            var u = "eq";
                            t >= 0 && n[t].searchoptions && n[t].searchoptions.sopt ? u = n[t].searchoptions.sopt[0] : b.sopt && b.sopt.length && (u = b.sopt[0]), i = {
                                "groupOp": "AND",
                                "rules": [{
                                    "field": m,
                                    "op": u,
                                    "data": ""
                                }]
                            }
                        }
                        s = !1, b.tmplNames && b.tmplNames.length && (s = !0, r = b.tmplLabel, r += "<select class='ui-template'>", r += "<option value='default'>Default</option>", a.each(b.tmplNames, function(a, b) {
                            r += "<option value='" + a + "'>" + b + "</option>"
                        }), r += "</select>"), k = "<table class='EditTable' style='border:0px none;margin-top:5px' id='" + e + "_2'><tbody><tr><td colspan='2'><hr class='ui-widget-content' style='margin:1px'/></td></tr><tr><td class='EditButton' style='text-align:" + k + "'>" + p + r + "</td><td class='EditButton' " + l + ">" + q + o + "</td></tr></tbody></table>", e = a.jgrid.jqID(e), a("#" + e).jqFilter({
                            "columns": n,
                            "filter": b.loadDefaults ? i : null,
                            "showQuery": b.showQuery,
                            "errorcheck": b.errorcheck,
                            "sopt": b.sopt,
                            "groupButton": b.multipleGroup,
                            "ruleButtons": b.multipleSearch,
                            "afterRedraw": b.afterRedraw,
                            "ops": b.odata,
                            "operands": b.operands,
                            "ajaxSelectOptions": d.p.ajaxSelectOptions,
                            "groupOps": b.groupOps,
                            "onChange": function() {
                                this.p.showQuery && a(".query", this).html(this.toUserFriendlyString()), a.isFunction(b.afterChange) && b.afterChange.call(d, a("#" + e), b)
                            },
                            "direction": d.p.direction,
                            "id": d.p.id
                        }), j.append(k), s && b.tmplFilters && b.tmplFilters.length && a(".ui-template", j).bind("change", function() {
                            var c = a(this).val();
                            return "default" === c ? a("#" + e).jqFilter("addFilter", i) : a("#" + e).jqFilter("addFilter", b.tmplFilters[parseInt(c, 10)]), !1
                        }), !0 === b.multipleGroup && (b.multipleSearch = !0), a(d).triggerHandler("jqGridFilterInitialize", [a("#" + e)]), a.isFunction(b.onInitializeSearch) && b.onInitializeSearch.call(d, a("#" + e)), b.gbox = "#gbox_" + e, b.layer ? a.jgrid.createModal(h, j, b, "#gview_" + a.jgrid.jqID(d.p.id), a("#gbox_" + a.jgrid.jqID(d.p.id))[0], "#" + a.jgrid.jqID(b.layer), {
                            "position": "relative"
                        }) : a.jgrid.createModal(h, j, b, "#gview_" + a.jgrid.jqID(d.p.id), a("#gbox_" + a.jgrid.jqID(d.p.id))[0]), (b.searchOnEnter || b.closeOnEscape) && a("#" + a.jgrid.jqID(h.themodal)).keydown(function(c) {
                            var d = a(c.target);
                            return !b.searchOnEnter || 13 !== c.which || d.hasClass("add-group") || d.hasClass("add-rule") || d.hasClass("delete-group") || d.hasClass("delete-rule") || d.hasClass("fm-button") && d.is("[id$=_query]") ? b.closeOnEscape && 27 === c.which ? (a("#" + a.jgrid.jqID(h.modalhead)).find(".ui-jqdialog-titlebar-close").focus().click(), !1) : void 0 : (a("#" + e + "_search").focus().click(), !1)
                        }), q && a("#" + e + "_query").bind("click", function() {
                            return a(".queryresult", j).toggle(), !1
                        }), void 0 === b.stringResult && (b.stringResult = b.multipleSearch), a("#" + e + "_search").bind("click", function() {
                            var c, f = a("#" + e),
                                i = {},
                                j = f.jqFilter("filterData");
                            if (b.errorcheck && (f[0].hideError(), b.showQuery || f.jqFilter("toSQLString"), f[0].p.error)) return f[0].showError(), !1;
                            if (b.stringResult) {
                                try {
                                    c = xmlJsonClass.toJson(j, "", "", !1)
                                } catch (k) {
                                    try {
                                        c = JSON.stringify(j)
                                    } catch (l) {}
                                }
                                "string" == typeof c && (i[b.sFilter] = c, a.each([b.sField, b.sValue, b.sOper], function() {
                                    i[this] = ""
                                }))
                            } else b.multipleSearch ? (i[b.sFilter] = j, a.each([b.sField, b.sValue, b.sOper], function() {
                                i[this] = ""
                            })) : (i[b.sField] = j.rules[0].field, i[b.sValue] = j.rules[0].data, i[b.sOper] = j.rules[0].op, i[b.sFilter] = "");
                            return d.p.search = !0, a.extend(d.p.postData, i), g = a(d).triggerHandler("jqGridFilterSearch"), void 0 === g && (g = !0), g && a.isFunction(b.onSearch) && (g = b.onSearch.call(d, d.p.filters)), g !== !1 && a(d).trigger("reloadGrid", [{
                                "page": 1
                            }]), b.closeAfterSearch && a.jgrid.hideModal("#" + a.jgrid.jqID(h.themodal), {
                                "gb": "#gbox_" + a.jgrid.jqID(d.p.id),
                                "jqm": b.jqModal,
                                "onClose": b.onClose
                            }), !1
                        }), a("#" + e + "_reset").bind("click", function() {
                            var c = {},
                                f = a("#" + e);
                            return d.p.search = !1, b.multipleSearch === !1 ? c[b.sField] = c[b.sValue] = c[b.sOper] = "" : c[b.sFilter] = "", f[0].resetFilter(), s && a(".ui-template", j).val("default"), a.extend(d.p.postData, c), g = a(d).triggerHandler("jqGridFilterReset"), void 0 === g && (g = !0), g && a.isFunction(b.onReset) && (g = b.onReset.call(d)), g !== !1 && a(d).trigger("reloadGrid", [{
                                "page": 1
                            }]), b.closeAfterReset && a.jgrid.hideModal("#" + a.jgrid.jqID(h.themodal), {
                                "gb": "#gbox_" + a.jgrid.jqID(d.p.id),
                                "jqm": b.jqModal,
                                "onClose": b.onClose
                            }), !1
                        }), c(a("#" + e)), a(".fm-button:not(.ui-state-disabled)", j).hover(function() {
                            a(this).addClass("ui-state-hover")
                        }, function() {
                            a(this).removeClass("ui-state-hover")
                        })
                    }
                }
            })
        },
        "editGridRow": function(c, d) {
            return d = a.extend(!0, {
                "top": 0,
                "left": 0,
                "width": 300,
                "datawidth": "auto",
                "height": "auto",
                "dataheight": "auto",
                "modal": !1,
                "overlay": 30,
                "drag": !0,
                "resize": !0,
                "url": null,
                "mtype": "POST",
                "clearAfterAdd": !0,
                "closeAfterEdit": !1,
                "reloadAfterSubmit": !0,
                "onInitializeForm": null,
                "beforeInitData": null,
                "beforeShowForm": null,
                "afterShowForm": null,
                "beforeSubmit": null,
                "afterSubmit": null,
                "onclickSubmit": null,
                "afterComplete": null,
                "onclickPgButtons": null,
                "afterclickPgButtons": null,
                "editData": {},
                "recreateForm": !1,
                "jqModal": !0,
                "closeOnEscape": !1,
                "addedrow": "first",
                "topinfo": "",
                "bottominfo": "",
                "saveicon": [],
                "closeicon": [],
                "savekey": [!1, 13],
                "navkeys": [!1, 38, 40],
                "checkOnSubmit": !1,
                "checkOnUpdate": !1,
                "_savedData": {},
                "processing": !1,
                "onClose": null,
                "ajaxEditOptions": {},
                "serializeEditData": null,
                "viewPagerButtons": !0,
                "overlayClass": "ui-widget-overlay"
            }, a.jgrid.edit, d || {}), b[a(this)[0].p.id] = d, this.each(function() {
                function e() {
                    return a(v + " > tbody > tr > td > .FormElement").each(function() {
                        var b = a(".customelement", this);
                        if (b.length) {
                            var c = a(b[0]).attr("name");
                            a.each(o.p.colModel, function() {
                                if (this.name === c && this.editoptions && a.isFunction(this.editoptions.custom_value)) {
                                    try {
                                        if (p[c] = this.editoptions.custom_value.call(o, a("#" + a.jgrid.jqID(c), v), "get"), void 0 === p[c]) throw "e1"
                                    } catch (b) {
                                        "e1" === b ? a.jgrid.info_dialog(a.jgrid.errors.errcap, "function 'custom_value' " + a.jgrid.edit.msg.novalue, a.jgrid.edit.bClose) : a.jgrid.info_dialog(a.jgrid.errors.errcap, b.message, a.jgrid.edit.bClose)
                                    }
                                    return !0
                                }
                            })
                        } else {
                            switch (a(this).get(0).type) {
                                case "checkbox":
                                    a(this).is(":checked") ? p[this.name] = a(this).val() : (b = a(this).attr("offval"), p[this.name] = b);
                                    break;
                                case "select-one":
                                    p[this.name] = a("option:selected", this).val();
                                    break;
                                case "select-multiple":
                                    p[this.name] = a(this).val(), p[this.name] = p[this.name] ? p[this.name].join(",") : "", a("option:selected", this).each(function(b, c) {
                                        a(c).text()
                                    });
                                    break;
                                case "password":
                                case "text":
                                case "textarea":
                                case "button":
                                    p[this.name] = a(this).val()
                            }
                            o.p.autoencode && (p[this.name] = a.jgrid.htmlEncode(p[this.name]))
                        }
                    }), !0
                }

                function f(c, d, e, f) {
                    var g, h, i, j, k, l, m, n = 0,
                        p = [],
                        q = !1,
                        r = "";
                    for (m = 1; f >= m; m++) r += "<td class='CaptionTD'>&#160;</td><td class='DataTD'>&#160;</td>";
                    return "_empty" !== c && (q = a(d).jqGrid("getInd", c)), a(d.p.colModel).each(function(m) {
                        if (g = this.name, k = (h = this.editrules && !0 === this.editrules.edithidden ? !1 : !0 === this.hidden ? !0 : !1) ? "style='display:none'" : "", "cb" !== g && "subgrid" !== g && !0 === this.editable && "rn" !== g) {
                            if (!1 === q) j = "";
                            else if (g === d.p.ExpandColumn && !0 === d.p.treeGrid) j = a("td[role='gridcell']:eq(" + m + ")", d.rows[q]).text();
                            else {
                                try {
                                    j = a.unformat.call(d, a("td[role='gridcell']:eq(" + m + ")", d.rows[q]), {
                                        "rowId": c,
                                        "colModel": this
                                    }, m)
                                } catch (s) {
                                    j = this.edittype && "textarea" === this.edittype ? a("td[role='gridcell']:eq(" + m + ")", d.rows[q]).text() : a("td[role='gridcell']:eq(" + m + ")", d.rows[q]).html()
                                }(!j || "&nbsp;" === j || "&#160;" === j || 1 === j.length && 160 === j.charCodeAt(0)) && (j = "")
                            }
                            var u = a.extend({}, this.editoptions || {}, {
                                    "id": g,
                                    "name": g
                                }),
                                v = a.extend({}, {
                                    "elmprefix": "",
                                    "elmsuffix": "",
                                    "rowabove": !1,
                                    "rowcontent": ""
                                }, this.formoptions || {}),
                                w = parseInt(v.rowpos, 10) || n + 1,
                                x = parseInt(2 * (parseInt(v.colpos, 10) || 1), 10);
                            if ("_empty" === c && u.defaultValue && (j = a.isFunction(u.defaultValue) ? u.defaultValue.call(o) : u.defaultValue), this.edittype || (this.edittype = "text"), o.p.autoencode && (j = a.jgrid.htmlDecode(j)), l = a.jgrid.createEl.call(o, this.edittype, u, j, !1, a.extend({}, a.jgrid.ajaxOptions, d.p.ajaxSelectOptions || {})), (b[o.p.id].checkOnSubmit || b[o.p.id].checkOnUpdate) && (b[o.p.id]._savedData[g] = j), a(l).addClass("FormElement"), -1 < a.inArray(this.edittype, ["text", "textarea", "password", "select"]) && a(l).addClass("ui-widget-content ui-corner-all"), i = a(e).find("tr[rowpos=" + w + "]"), v.rowabove) {
                                var y = a("<tr><td class='contentinfo' colspan='" + 2 * f + "'>" + v.rowcontent + "</td></tr>");
                                a(e).append(y), y[0].rp = w
                            }
                            0 === i.length && (i = a("<tr " + k + " rowpos='" + w + "'></tr>").addClass("FormData").attr("id", "tr_" + g), a(i).append(r), a(e).append(i), i[0].rp = w), a("td:eq(" + (x - 2) + ")", i[0]).html(void 0 === v.label ? d.p.colNames[m] : v.label), a("td:eq(" + (x - 1) + ")", i[0]).append(v.elmprefix).append(l).append(v.elmsuffix), "custom" === this.edittype && a.isFunction(u.custom_value) && u.custom_value.call(o, a("#" + g, "#" + t), "set", j), a.jgrid.bindEv.call(o, l, u), p[n] = m, n++
                        }
                    }), n > 0 && (m = a("<tr class='FormData' style='display:none'><td class='CaptionTD'></td><td colspan='" + (2 * f - 1) + "' class='DataTD'><input class='FormElement' id='id_g' type='text' name='" + d.p.id + "_id' value='" + c + "'/></td></tr>"), m[0].rp = n + 999, a(e).append(m), b[o.p.id].checkOnSubmit || b[o.p.id].checkOnUpdate) && (b[o.p.id]._savedData[d.p.id + "_id"] = c), p
                }

                function g(c, d, e) {
                    var f, g, h, i, j, k, l = 0;
                    (b[o.p.id].checkOnSubmit || b[o.p.id].checkOnUpdate) && (b[o.p.id]._savedData = {}, b[o.p.id]._savedData[d.p.id + "_id"] = c);
                    var m = d.p.colModel;
                    if ("_empty" === c) a(m).each(function() {
                        f = this.name, i = a.extend({}, this.editoptions || {}), (h = a("#" + a.jgrid.jqID(f), "#" + e)) && h.length && null !== h[0] && (j = "", "custom" === this.edittype && a.isFunction(i.custom_value) ? i.custom_value.call(o, a("#" + f, "#" + e), "set", j) : i.defaultValue ? (j = a.isFunction(i.defaultValue) ? i.defaultValue.call(o) : i.defaultValue, "checkbox" === h[0].type ? (k = j.toLowerCase(), 0 > k.search(/(false|f|0|no|n|off|undefined)/i) && "" !== k ? (h[0].checked = !0, h[0].defaultChecked = !0, h[0].value = j) : (h[0].checked = !1, h[0].defaultChecked = !1)) : h.val(j)) : "checkbox" === h[0].type ? (h[0].checked = !1, h[0].defaultChecked = !1, j = a(h).attr("offval")) : h[0].type && "select" === h[0].type.substr(0, 6) ? h[0].selectedIndex = 0 : h.val(j), (!0 === b[o.p.id].checkOnSubmit || b[o.p.id].checkOnUpdate) && (b[o.p.id]._savedData[f] = j))
                    }), a("#id_g", "#" + e).val(c);
                    else {
                        var n = a(d).jqGrid("getInd", c, !0);
                        n && (a('td[role="gridcell"]', n).each(function(h) {
                            if (f = m[h].name, "cb" !== f && "subgrid" !== f && "rn" !== f && !0 === m[h].editable) {
                                if (f === d.p.ExpandColumn && !0 === d.p.treeGrid) g = a(this).text();
                                else try {
                                    g = a.unformat.call(d, a(this), {
                                        "rowId": c,
                                        "colModel": m[h]
                                    }, h)
                                } catch (i) {
                                    g = "textarea" === m[h].edittype ? a(this).text() : a(this).html()
                                }
                                switch (o.p.autoencode && (g = a.jgrid.htmlDecode(g)), (!0 === b[o.p.id].checkOnSubmit || b[o.p.id].checkOnUpdate) && (b[o.p.id]._savedData[f] = g), f = a.jgrid.jqID(f), m[h].edittype) {
                                    case "password":
                                    case "text":
                                    case "button":
                                    case "image":
                                    case "textarea":
                                        ("&nbsp;" === g || "&#160;" === g || 1 === g.length && 160 === g.charCodeAt(0)) && (g = ""), a("#" + f, "#" + e).val(g);
                                        break;
                                    case "select":
                                        var j = g.split(","),
                                            j = a.map(j, function(b) {
                                                return a.trim(b)
                                            });
                                        a("#" + f + " option", "#" + e).each(function() {
                                            this.selected = m[h].editoptions.multiple || a.trim(g) !== a.trim(a(this).text()) && j[0] !== a.trim(a(this).text()) && j[0] !== a.trim(a(this).val()) ? m[h].editoptions.multiple && (-1 < a.inArray(a.trim(a(this).text()), j) || -1 < a.inArray(a.trim(a(this).val()), j)) ? !0 : !1 : !0
                                        });
                                        break;
                                    case "checkbox":
                                        g = "" + g, m[h].editoptions && m[h].editoptions.value ? a("#" + f, "#" + e)[o.p.useProp ? "prop" : "attr"](m[h].editoptions.value.split(":")[0] === g ? {
                                            "checked": !0,
                                            "defaultChecked": !0
                                        } : {
                                            "checked": !1,
                                            "defaultChecked": !1
                                        }) : (g = g.toLowerCase(), 0 > g.search(/(false|f|0|no|n|off|undefined)/i) && "" !== g ? (a("#" + f, "#" + e)[o.p.useProp ? "prop" : "attr"]("checked", !0), a("#" + f, "#" + e)[o.p.useProp ? "prop" : "attr"]("defaultChecked", !0)) : (a("#" + f, "#" + e)[o.p.useProp ? "prop" : "attr"]("checked", !1), a("#" + f, "#" + e)[o.p.useProp ? "prop" : "attr"]("defaultChecked", !1)));
                                        break;
                                    case "custom":
                                        try {
                                            if (!m[h].editoptions || !a.isFunction(m[h].editoptions.custom_value)) throw "e1";
                                            m[h].editoptions.custom_value.call(o, a("#" + f, "#" + e), "set", g)
                                        } catch (k) {
                                            "e1" === k ? a.jgrid.info_dialog(a.jgrid.errors.errcap, "function 'custom_value' " + a.jgrid.edit.msg.nodefined, a.jgrid.edit.bClose) : a.jgrid.info_dialog(a.jgrid.errors.errcap, k.message, a.jgrid.edit.bClose)
                                        }
                                }
                                l++
                            }
                        }), l > 0 && a("#id_g", v).val(c))
                    }
                }

                function h() {
                    a.each(o.p.colModel, function(a, b) {
                        b.editoptions && !0 === b.editoptions.NullIfEmpty && p.hasOwnProperty(b.name) && "" === p[b.name] && (p[b.name] = "null")
                    })
                }

                function i() {
                    var c, e, f, i, j, k, l = [!0, "", ""],
                        m = {},
                        n = o.p.prmNames,
                        q = a(o).triggerHandler("jqGridAddEditBeforeCheckValues", [a("#" + t), r]);
                    q && "object" == typeof q && (p = q), a.isFunction(b[o.p.id].beforeCheckValues) && (q = b[o.p.id].beforeCheckValues.call(o, p, a("#" + t), r)) && "object" == typeof q && (p = q);
                    for (i in p)
                        if (p.hasOwnProperty(i) && (l = a.jgrid.checkValues.call(o, p[i], i), !1 === l[0])) break;
                    if (h(), l[0] && (m = a(o).triggerHandler("jqGridAddEditClickSubmit", [b[o.p.id], p, r]), void 0 === m && a.isFunction(b[o.p.id].onclickSubmit) && (m = b[o.p.id].onclickSubmit.call(o, b[o.p.id], p, r) || {}), l = a(o).triggerHandler("jqGridAddEditBeforeSubmit", [p, a("#" + t), r]), void 0 === l && (l = [!0, "", ""]), l[0] && a.isFunction(b[o.p.id].beforeSubmit) && (l = b[o.p.id].beforeSubmit.call(o, p, a("#" + t), r))), l[0] && !b[o.p.id].processing) {
                        if (b[o.p.id].processing = !0, a("#sData", v + "_2").addClass("ui-state-active"), f = n.oper, e = n.id, p[f] = "_empty" === a.trim(p[o.p.id + "_id"]) ? n.addoper : n.editoper, p[f] !== n.addoper ? p[e] = p[o.p.id + "_id"] : void 0 === p[e] && (p[e] = p[o.p.id + "_id"]), delete p[o.p.id + "_id"], p = a.extend(p, b[o.p.id].editData, m), !0 === o.p.treeGrid)
                            for (k in p[f] === n.addoper && (j = a(o).jqGrid("getGridParam", "selrow"), p["adjacency" === o.p.treeGridModel ? o.p.treeReader.parent_id_field : "parent_id"] = j), o.p.treeReader) o.p.treeReader.hasOwnProperty(k) && (m = o.p.treeReader[k], p.hasOwnProperty(m) && !(p[f] === n.addoper && "parent_id_field" === k) && delete p[m]);
                        p[e] = a.jgrid.stripPref(o.p.idPrefix, p[e]), k = a.extend({
                            "url": b[o.p.id].url || a(o).jqGrid("getGridParam", "editurl"),
                            "type": b[o.p.id].mtype,
                            "data": a.isFunction(b[o.p.id].serializeEditData) ? b[o.p.id].serializeEditData.call(o, p) : p,
                            "complete": function(h, i) {
                                var k;
                                if (p[e] = o.p.idPrefix + p[e], h.status >= 300 && 304 !== h.status ? (l[0] = !1, l[1] = a(o).triggerHandler("jqGridAddEditErrorTextFormat", [h, r]), l[1] = a.isFunction(b[o.p.id].errorTextFormat) ? b[o.p.id].errorTextFormat.call(o, h, r) : i + " Status: '" + h.statusText + "'. Error code: " + h.status) : (l = a(o).triggerHandler("jqGridAddEditAfterSubmit", [h, p, r]), void 0 === l && (l = [!0, "", ""]), l[0] && a.isFunction(b[o.p.id].afterSubmit) && (l = b[o.p.id].afterSubmit.call(o, h, p, r))), l[0] === !1) a("#FormError>td", v).html(l[1]), a("#FormError", v).show();
                                else if (o.p.autoencode && a.each(p, function(b, c) {
                                        p[b] = a.jgrid.htmlDecode(c)
                                    }), p[f] === n.addoper ? (l[2] || (l[2] = a.jgrid.randId()), p[e] = l[2], b[o.p.id].reloadAfterSubmit ? a(o).trigger("reloadGrid") : o.p.treeGrid === !0 ? a(o).jqGrid("addChildNode", l[2], j, p) : a(o).jqGrid("addRowData", l[2], p, d.addedrow), b[o.p.id].closeAfterAdd ? (o.p.treeGrid !== !0 && a(o).jqGrid("setSelection", l[2]), a.jgrid.hideModal("#" + a.jgrid.jqID(w.themodal), {
                                        "gb": "#gbox_" + a.jgrid.jqID(s),
                                        "jqm": d.jqModal,
                                        "onClose": b[o.p.id].onClose
                                    })) : b[o.p.id].clearAfterAdd && g("_empty", o, t)) : (b[o.p.id].reloadAfterSubmit ? (a(o).trigger("reloadGrid"), b[o.p.id].closeAfterEdit || setTimeout(function() {
                                        a(o).jqGrid("setSelection", p[e])
                                    }, 1e3)) : o.p.treeGrid === !0 ? a(o).jqGrid("setTreeRow", p[e], p) : a(o).jqGrid("setRowData", p[e], p), b[o.p.id].closeAfterEdit && a.jgrid.hideModal("#" + a.jgrid.jqID(w.themodal), {
                                        "gb": "#gbox_" + a.jgrid.jqID(s),
                                        "jqm": d.jqModal,
                                        "onClose": b[o.p.id].onClose
                                    })), a.isFunction(b[o.p.id].afterComplete) && (c = h, setTimeout(function() {
                                        a(o).triggerHandler("jqGridAddEditAfterComplete", [c, p, a("#" + t), r]), b[o.p.id].afterComplete.call(o, c, p, a("#" + t), r), c = null
                                    }, 500)), (b[o.p.id].checkOnSubmit || b[o.p.id].checkOnUpdate) && (a("#" + t).data("disabled", !1), "_empty" !== b[o.p.id]._savedData[o.p.id + "_id"]))
                                    for (k in b[o.p.id]._savedData) b[o.p.id]._savedData.hasOwnProperty(k) && p[k] && (b[o.p.id]._savedData[k] = p[k]);
                                b[o.p.id].processing = !1, a("#sData", v + "_2").removeClass("ui-state-active");
                                try {
                                    a(":input:visible", "#" + t)[0].focus()
                                } catch (m) {}
                            }
                        }, a.jgrid.ajaxOptions, b[o.p.id].ajaxEditOptions), !k.url && !b[o.p.id].useDataProxy && (a.isFunction(o.p.dataProxy) ? b[o.p.id].useDataProxy = !0 : (l[0] = !1, l[1] += " " + a.jgrid.errors.nourl)), l[0] && (b[o.p.id].useDataProxy ? (m = o.p.dataProxy.call(o, k, "set_" + o.p.id), void 0 === m && (m = [!0, ""]), !1 === m[0] ? (l[0] = !1, l[1] = m[1] || "Error deleting the selected row!") : (k.data.oper === n.addoper && b[o.p.id].closeAfterAdd && a.jgrid.hideModal("#" + a.jgrid.jqID(w.themodal), {
                            "gb": "#gbox_" + a.jgrid.jqID(s),
                            "jqm": d.jqModal,
                            "onClose": b[o.p.id].onClose
                        }), k.data.oper === n.editoper && b[o.p.id].closeAfterEdit && a.jgrid.hideModal("#" + a.jgrid.jqID(w.themodal), {
                            "gb": "#gbox_" + a.jgrid.jqID(s),
                            "jqm": d.jqModal,
                            "onClose": b[o.p.id].onClose
                        }))) : a.ajax(k))
                    }!1 === l[0] && (a("#FormError>td", v).html(l[1]), a("#FormError", v).show())
                }

                function j(a, b) {
                    var c, d = !1;
                    for (c in a)
                        if (a.hasOwnProperty(c) && a[c] != b[c]) {
                            d = !0;
                            break
                        }
                    return d
                }

                function k() {
                    var c = !0;
                    return a("#FormError", v).hide(), b[o.p.id].checkOnUpdate && (p = {}, e(), q = j(p, b[o.p.id]._savedData)) && (a("#" + t).data("disabled", !0), a(".confirm", "#" + w.themodal).show(), c = !1), c
                }

                function l() {
                    var b;
                    if ("_empty" !== c && void 0 !== o.p.savedRow && 0 < o.p.savedRow.length && a.isFunction(a.fn.jqGrid.restoreRow))
                        for (b = 0; b < o.p.savedRow.length; b++)
                            if (o.p.savedRow[b].id == c) {
                                a(o).jqGrid("restoreRow", c);
                                break
                            }
                }

                function m(b, c) {
                    var d = c[1].length - 1;
                    0 === b ? a("#pData", v + "_2").addClass("ui-state-disabled") : void 0 !== c[1][b - 1] && a("#" + a.jgrid.jqID(c[1][b - 1])).hasClass("ui-state-disabled") ? a("#pData", v + "_2").addClass("ui-state-disabled") : a("#pData", v + "_2").removeClass("ui-state-disabled"), b === d ? a("#nData", v + "_2").addClass("ui-state-disabled") : void 0 !== c[1][b + 1] && a("#" + a.jgrid.jqID(c[1][b + 1])).hasClass("ui-state-disabled") ? a("#nData", v + "_2").addClass("ui-state-disabled") : a("#nData", v + "_2").removeClass("ui-state-disabled")
                }

                function n() {
                    var b = a(o).jqGrid("getDataIDs"),
                        c = a("#id_g", v).val();
                    return [a.inArray(c, b), b]
                }
                var o = this;
                if (o.grid && c) {
                    var p, q, r, s = o.p.id,
                        t = "FrmGrid_" + s,
                        u = "TblGrid_" + s,
                        v = "#" + a.jgrid.jqID(u),
                        w = {
                            "themodal": "editmod" + s,
                            "modalhead": "edithd" + s,
                            "modalcontent": "editcnt" + s,
                            "scrollelm": t
                        },
                        x = a.isFunction(b[o.p.id].beforeShowForm) ? b[o.p.id].beforeShowForm : !1,
                        y = a.isFunction(b[o.p.id].afterShowForm) ? b[o.p.id].afterShowForm : !1,
                        z = a.isFunction(b[o.p.id].beforeInitData) ? b[o.p.id].beforeInitData : !1,
                        A = a.isFunction(b[o.p.id].onInitializeForm) ? b[o.p.id].onInitializeForm : !1,
                        B = !0,
                        C = 1,
                        D = 0,
                        t = a.jgrid.jqID(t);
                    "new" === c ? (c = "_empty", r = "add", d.caption = b[o.p.id].addCaption) : (d.caption = b[o.p.id].editCaption, r = "edit"), !0 === d.recreateForm && void 0 !== a("#" + a.jgrid.jqID(w.themodal))[0] && a("#" + a.jgrid.jqID(w.themodal)).remove();
                    var E = !0;
                    if (d.checkOnUpdate && d.jqModal && !d.modal && (E = !1), void 0 !== a("#" + a.jgrid.jqID(w.themodal))[0]) {
                        if (B = a(o).triggerHandler("jqGridAddEditBeforeInitData", [a("#" + a.jgrid.jqID(t)), r]), void 0 === B && (B = !0), B && z && (B = z.call(o, a("#" + t), r)), !1 === B) return;
                        l(), a(".ui-jqdialog-title", "#" + a.jgrid.jqID(w.modalhead)).html(d.caption), a("#FormError", v).hide(), b[o.p.id].topinfo ? (a(".topinfo", v).html(b[o.p.id].topinfo), a(".tinfo", v).show()) : a(".tinfo", v).hide(), b[o.p.id].bottominfo ? (a(".bottominfo", v + "_2").html(b[o.p.id].bottominfo), a(".binfo", v + "_2").show()) : a(".binfo", v + "_2").hide(), g(c, o, t), "_empty" !== c && b[o.p.id].viewPagerButtons ? a("#pData, #nData", v + "_2").show() : a("#pData, #nData", v + "_2").hide(), !0 === b[o.p.id].processing && (b[o.p.id].processing = !1, a("#sData", v + "_2").removeClass("ui-state-active")), !0 === a("#" + t).data("disabled") && (a(".confirm", "#" + a.jgrid.jqID(w.themodal)).hide(), a("#" + t).data("disabled", !1)), a(o).triggerHandler("jqGridAddEditBeforeShowForm", [a("#" + t), r]), x && x.call(o, a("#" + t), r), a("#" + a.jgrid.jqID(w.themodal)).data("onClose", b[o.p.id].onClose), a.jgrid.viewModal("#" + a.jgrid.jqID(w.themodal), {
                            "gbox": "#gbox_" + a.jgrid.jqID(s),
                            "jqm": d.jqModal,
                            "jqM": !1,
                            "overlay": d.overlay,
                            "modal": d.modal,
                            "overlayClass": d.overlayClass
                        }), E || a("." + a.jgrid.jqID(d.overlayClass)).click(function() {
                            return k() ? (a.jgrid.hideModal("#" + a.jgrid.jqID(w.themodal), {
                                "gb": "#gbox_" + a.jgrid.jqID(s),
                                "jqm": d.jqModal,
                                "onClose": b[o.p.id].onClose
                            }), !1) : !1
                        }), a(o).triggerHandler("jqGridAddEditAfterShowForm", [a("#" + t), r]), y && y.call(o, a("#" + t), r)
                    } else {
                        var F = isNaN(d.dataheight) ? d.dataheight : d.dataheight + "px",
                            B = isNaN(d.datawidth) ? d.datawidth : d.datawidth + "px",
                            F = a("<form name='FormPost' id='" + t + "' class='FormGrid' onSubmit='return false;' style='width:" + B + ";overflow:auto;position:relative;height:" + F + ";'></form>").data("disabled", !1),
                            G = a("<table id='" + u + "' class='EditTable' cellspacing='0' cellpadding='0' border='0'><tbody></tbody></table>"),
                            B = a(o).triggerHandler("jqGridAddEditBeforeInitData", [a("#" + t), r]);
                        if (void 0 === B && (B = !0), B && z && (B = z.call(o, a("#" + t), r)), !1 === B) return;
                        l(), a(o.p.colModel).each(function() {
                            var a = this.formoptions;
                            C = Math.max(C, a ? a.colpos || 0 : 0), D = Math.max(D, a ? a.rowpos || 0 : 0)
                        }), a(F).append(G), z = a("<tr id='FormError' style='display:none'><td class='ui-state-error' colspan='" + 2 * C + "'></td></tr>"), z[0].rp = 0, a(G).append(z), z = a("<tr style='display:none' class='tinfo'><td class='topinfo' colspan='" + 2 * C + "'>" + b[o.p.id].topinfo + "</td></tr>"), z[0].rp = 0, a(G).append(z);
                        var B = (z = "rtl" === o.p.direction ? !0 : !1) ? "nData" : "pData",
                            H = z ? "pData" : "nData";
                        f(c, o, G, C);
                        var B = "<a id='" + B + "' class='fm-button ui-state-default ui-corner-left'><span class='ui-icon ui-icon-triangle-1-w'></span></a>",
                            H = "<a id='" + H + "' class='fm-button ui-state-default ui-corner-right'><span class='ui-icon ui-icon-triangle-1-e'></span></a>",
                            I = "<a id='sData' class='fm-button ui-state-default ui-corner-all'>" + d.bSubmit + "</a>",
                            J = "<a id='cData' class='fm-button ui-state-default ui-corner-all'>" + d.bCancel + "</a>",
                            u = "<table border='0' cellspacing='0' cellpadding='0' class='EditTable' id='" + u + "_2'><tbody><tr><td colspan='2'><hr class='ui-widget-content' style='margin:1px'/></td></tr><tr id='Act_Buttons'><td class='navButton'>" + (z ? H + B : B + H) + "</td><td class='EditButton'>" + I + J + "</td></tr>" + ("<tr style='display:none' class='binfo'><td class='bottominfo' colspan='2'>" + b[o.p.id].bottominfo + "</td></tr>"),
                            u = u + "</tbody></table>";
                        if (D > 0) {
                            var K = [];
                            a.each(a(G)[0].rows, function(a, b) {
                                K[a] = b
                            }), K.sort(function(a, b) {
                                return a.rp > b.rp ? 1 : a.rp < b.rp ? -1 : 0
                            }), a.each(K, function(b, c) {
                                a("tbody", G).append(c)
                            })
                        }
                        d.gbox = "#gbox_" + a.jgrid.jqID(s);
                        var L = !1;
                        !0 === d.closeOnEscape && (d.closeOnEscape = !1, L = !0), u = a("<div></div>").append(F).append(u), a.jgrid.createModal(w, u, d, "#gview_" + a.jgrid.jqID(o.p.id), a("#gbox_" + a.jgrid.jqID(o.p.id))[0]), z && (a("#pData, #nData", v + "_2").css("float", "right"), a(".EditButton", v + "_2").css("text-align", "left")), b[o.p.id].topinfo && a(".tinfo", v).show(), b[o.p.id].bottominfo && a(".binfo", v + "_2").show(), u = u = null, a("#" + a.jgrid.jqID(w.themodal)).keydown(function(c) {
                            var e = c.target;
                            if (a("#" + t).data("disabled") === !0) return !1;
                            if (b[o.p.id].savekey[0] === !0 && c.which === b[o.p.id].savekey[1] && "TEXTAREA" !== e.tagName) return a("#sData", v + "_2").trigger("click"), !1;
                            if (27 === c.which) return k() ? (L && a.jgrid.hideModal("#" + a.jgrid.jqID(w.themodal), {
                                "gb": d.gbox,
                                "jqm": d.jqModal,
                                "onClose": b[o.p.id].onClose
                            }), !1) : !1;
                            if (b[o.p.id].navkeys[0] === !0) {
                                if ("_empty" === a("#id_g", v).val()) return !0;
                                if (c.which === b[o.p.id].navkeys[1]) return a("#pData", v + "_2").trigger("click"), !1;
                                if (c.which === b[o.p.id].navkeys[2]) return a("#nData", v + "_2").trigger("click"), !1
                            }
                        }), d.checkOnUpdate && (a("a.ui-jqdialog-titlebar-close span", "#" + a.jgrid.jqID(w.themodal)).removeClass("jqmClose"), a("a.ui-jqdialog-titlebar-close", "#" + a.jgrid.jqID(w.themodal)).unbind("click").click(function() {
                            return k() ? (a.jgrid.hideModal("#" + a.jgrid.jqID(w.themodal), {
                                "gb": "#gbox_" + a.jgrid.jqID(s),
                                "jqm": d.jqModal,
                                "onClose": b[o.p.id].onClose
                            }), !1) : !1
                        })), d.saveicon = a.extend([!0, "left", "ui-icon-disk"], d.saveicon), d.closeicon = a.extend([!0, "left", "ui-icon-close"], d.closeicon), !0 === d.saveicon[0] && a("#sData", v + "_2").addClass("right" === d.saveicon[1] ? "fm-button-icon-right" : "fm-button-icon-left").append("<span class='ui-icon " + d.saveicon[2] + "'></span>"), !0 === d.closeicon[0] && a("#cData", v + "_2").addClass("right" === d.closeicon[1] ? "fm-button-icon-right" : "fm-button-icon-left").append("<span class='ui-icon " + d.closeicon[2] + "'></span>"), (b[o.p.id].checkOnSubmit || b[o.p.id].checkOnUpdate) && (I = "<a id='sNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>" + d.bYes + "</a>", H = "<a id='nNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>" + d.bNo + "</a>", J = "<a id='cNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>" + d.bExit + "</a>", u = d.zIndex || 999, u++, a("<div class='" + d.overlayClass + " jqgrid-overlay confirm' style='z-index:" + u + ";display:none;'>&#160;</div><div class='confirm ui-widget-content ui-jqconfirm' style='z-index:" + (u + 1) + "'>" + d.saveData + "<br/><br/>" + I + H + J + "</div>").insertAfter("#" + t), a("#sNew", "#" + a.jgrid.jqID(w.themodal)).click(function() {
                            return i(), a("#" + t).data("disabled", !1), a(".confirm", "#" + a.jgrid.jqID(w.themodal)).hide(), !1
                        }), a("#nNew", "#" + a.jgrid.jqID(w.themodal)).click(function() {
                            return a(".confirm", "#" + a.jgrid.jqID(w.themodal)).hide(), a("#" + t).data("disabled", !1), setTimeout(function() {
                                a(":input:visible", "#" + t)[0].focus()
                            }, 0), !1
                        }), a("#cNew", "#" + a.jgrid.jqID(w.themodal)).click(function() {
                            return a(".confirm", "#" + a.jgrid.jqID(w.themodal)).hide(), a("#" + t).data("disabled", !1), a.jgrid.hideModal("#" + a.jgrid.jqID(w.themodal), {
                                "gb": "#gbox_" + a.jgrid.jqID(s),
                                "jqm": d.jqModal,
                                "onClose": b[o.p.id].onClose
                            }), !1
                        })), a(o).triggerHandler("jqGridAddEditInitializeForm", [a("#" + t), r]), A && A.call(o, a("#" + t), r), "_empty" !== c && b[o.p.id].viewPagerButtons ? a("#pData,#nData", v + "_2").show() : a("#pData,#nData", v + "_2").hide(), a(o).triggerHandler("jqGridAddEditBeforeShowForm", [a("#" + t), r]), x && x.call(o, a("#" + t), r), a("#" + a.jgrid.jqID(w.themodal)).data("onClose", b[o.p.id].onClose), a.jgrid.viewModal("#" + a.jgrid.jqID(w.themodal), {
                            "gbox": "#gbox_" + a.jgrid.jqID(s),
                            "jqm": d.jqModal,
                            "overlay": d.overlay,
                            "modal": d.modal,
                            "overlayClass": d.overlayClass
                        }), E || a("." + a.jgrid.jqID(d.overlayClass)).click(function() {
                            return k() ? (a.jgrid.hideModal("#" + a.jgrid.jqID(w.themodal), {
                                "gb": "#gbox_" + a.jgrid.jqID(s),
                                "jqm": d.jqModal,
                                "onClose": b[o.p.id].onClose
                            }), !1) : !1
                        }), a(o).triggerHandler("jqGridAddEditAfterShowForm", [a("#" + t), r]), y && y.call(o, a("#" + t), r), a(".fm-button", "#" + a.jgrid.jqID(w.themodal)).hover(function() {
                            a(this).addClass("ui-state-hover")
                        }, function() {
                            a(this).removeClass("ui-state-hover")
                        }), a("#sData", v + "_2").click(function() {
                            return p = {}, a("#FormError", v).hide(), e(), "_empty" === p[o.p.id + "_id"] ? i() : d.checkOnSubmit === !0 && (q = j(p, b[o.p.id]._savedData)) ? (a("#" + t).data("disabled", !0), a(".confirm", "#" + a.jgrid.jqID(w.themodal)).show()) : i(), !1
                        }), a("#cData", v + "_2").click(function() {
                            return k() ? (a.jgrid.hideModal("#" + a.jgrid.jqID(w.themodal), {
                                "gb": "#gbox_" + a.jgrid.jqID(s),
                                "jqm": d.jqModal,
                                "onClose": b[o.p.id].onClose
                            }), !1) : !1
                        }), a("#nData", v + "_2").click(function() {
                            if (!k()) return !1;
                            a("#FormError", v).hide();
                            var b = n();
                            if (b[0] = parseInt(b[0], 10), -1 !== b[0] && b[1][b[0] + 1]) {
                                a(o).triggerHandler("jqGridAddEditClickPgButtons", ["next", a("#" + t), b[1][b[0]]]);
                                var c;
                                if (a.isFunction(d.onclickPgButtons) && (c = d.onclickPgButtons.call(o, "next", a("#" + t), b[1][b[0]]), void 0 !== c && c === !1)) return !1;
                                if (a("#" + a.jgrid.jqID(b[1][b[0] + 1])).hasClass("ui-state-disabled")) return !1;
                                g(b[1][b[0] + 1], o, t), a(o).jqGrid("setSelection", b[1][b[0] + 1]), a(o).triggerHandler("jqGridAddEditAfterClickPgButtons", ["next", a("#" + t), b[1][b[0]]]), a.isFunction(d.afterclickPgButtons) && d.afterclickPgButtons.call(o, "next", a("#" + t), b[1][b[0] + 1]), m(b[0] + 1, b)
                            }
                            return !1
                        }), a("#pData", v + "_2").click(function() {
                            if (!k()) return !1;
                            a("#FormError", v).hide();
                            var b = n();
                            if (-1 !== b[0] && b[1][b[0] - 1]) {
                                a(o).triggerHandler("jqGridAddEditClickPgButtons", ["prev", a("#" + t), b[1][b[0]]]);
                                var c;
                                if (a.isFunction(d.onclickPgButtons) && (c = d.onclickPgButtons.call(o, "prev", a("#" + t), b[1][b[0]]), void 0 !== c && c === !1)) return !1;
                                if (a("#" + a.jgrid.jqID(b[1][b[0] - 1])).hasClass("ui-state-disabled")) return !1;
                                g(b[1][b[0] - 1], o, t), a(o).jqGrid("setSelection", b[1][b[0] - 1]), a(o).triggerHandler("jqGridAddEditAfterClickPgButtons", ["prev", a("#" + t), b[1][b[0]]]), a.isFunction(d.afterclickPgButtons) && d.afterclickPgButtons.call(o, "prev", a("#" + t), b[1][b[0] - 1]), m(b[0] - 1, b)
                            }
                            return !1
                        })
                    }
                    x = n(), m(x[0], x)
                }
            })
        },
        "viewGridRow": function(c, d) {
            return d = a.extend(!0, {
                "top": 0,
                "left": 0,
                "width": 0,
                "datawidth": "auto",
                "height": "auto",
                "dataheight": "auto",
                "modal": !1,
                "overlay": 30,
                "drag": !0,
                "resize": !0,
                "jqModal": !0,
                "closeOnEscape": !1,
                "labelswidth": "30%",
                "closeicon": [],
                "navkeys": [!1, 38, 40],
                "onClose": null,
                "beforeShowForm": null,
                "beforeInitData": null,
                "viewPagerButtons": !0,
                "recreateForm": !1
            }, a.jgrid.view, d || {}), b[a(this)[0].p.id] = d, this.each(function() {
                function e() {
                    (!0 === b[j.p.id].closeOnEscape || !0 === b[j.p.id].navkeys[0]) && setTimeout(function() {
                        a(".ui-jqdialog-titlebar-close", "#" + a.jgrid.jqID(p.modalhead)).focus()
                    }, 0)
                }

                function f(b, c, e, f) {
                    var g, h, i, j, k, l, m, n, o, p = 0,
                        q = [],
                        r = !1,
                        s = "<td class='CaptionTD form-view-label ui-widget-content' width='" + d.labelswidth + "'>&#160;</td><td class='DataTD form-view-data ui-helper-reset ui-widget-content'>&#160;</td>",
                        t = "",
                        u = ["integer", "number", "currency"],
                        v = 0,
                        w = 0;
                    for (l = 1; f >= l; l++) t += 1 === l ? s : "<td class='CaptionTD form-view-label ui-widget-content'>&#160;</td><td class='DataTD form-view-data ui-widget-content'>&#160;</td>";
                    return a(c.p.colModel).each(function() {
                        h = this.editrules && !0 === this.editrules.edithidden ? !1 : !0 === this.hidden ? !0 : !1, !h && "right" === this.align && (this.formatter && -1 !== a.inArray(this.formatter, u) ? v = Math.max(v, parseInt(this.width, 10)) : w = Math.max(w, parseInt(this.width, 10)))
                    }), m = 0 !== v ? v : 0 !== w ? w : 0, r = a(c).jqGrid("getInd", b), a(c.p.colModel).each(function(b) {
                        if (g = this.name, n = !1, k = (h = this.editrules && !0 === this.editrules.edithidden ? !1 : !0 === this.hidden ? !0 : !1) ? "style='display:none'" : "", o = "boolean" != typeof this.viewable ? !0 : this.viewable, "cb" !== g && "subgrid" !== g && "rn" !== g && o) {
                            j = !1 === r ? "" : g === c.p.ExpandColumn && !0 === c.p.treeGrid ? a("td:eq(" + b + ")", c.rows[r]).text() : a("td:eq(" + b + ")", c.rows[r]).html(), n = "right" === this.align && 0 !== m ? !0 : !1;
                            var d = a.extend({}, {
                                    "rowabove": !1,
                                    "rowcontent": ""
                                }, this.formoptions || {}),
                                l = parseInt(d.rowpos, 10) || p + 1,
                                s = parseInt(2 * (parseInt(d.colpos, 10) || 1), 10);
                            if (d.rowabove) {
                                var u = a("<tr><td class='contentinfo' colspan='" + 2 * f + "'>" + d.rowcontent + "</td></tr>");
                                a(e).append(u), u[0].rp = l
                            }
                            i = a(e).find("tr[rowpos=" + l + "]"), 0 === i.length && (i = a("<tr " + k + " rowpos='" + l + "'></tr>").addClass("FormData").attr("id", "trv_" + g), a(i).append(t), a(e).append(i), i[0].rp = l), a("td:eq(" + (s - 2) + ")", i[0]).html("<b>" + (void 0 === d.label ? c.p.colNames[b] : d.label) + "</b>"), a("td:eq(" + (s - 1) + ")", i[0]).append("<span>" + j + "</span>").attr("id", "v_" + g), n && a("td:eq(" + (s - 1) + ") span", i[0]).css({
                                "text-align": "right",
                                "width": m + "px"
                            }), q[p] = b, p++
                        }
                    }), p > 0 && (b = a("<tr class='FormData' style='display:none'><td class='CaptionTD'></td><td colspan='" + (2 * f - 1) + "' class='DataTD'><input class='FormElement' id='id_g' type='text' name='id' value='" + b + "'/></td></tr>"), b[0].rp = p + 99, a(e).append(b)), q
                }

                function g(b, c) {
                    var d, e, f, g, h = 0;
                    (g = a(c).jqGrid("getInd", b, !0)) && (a("td", g).each(function(b) {
                        d = c.p.colModel[b].name, e = c.p.colModel[b].editrules && !0 === c.p.colModel[b].editrules.edithidden ? !1 : !0 === c.p.colModel[b].hidden ? !0 : !1, "cb" !== d && "subgrid" !== d && "rn" !== d && (f = d === c.p.ExpandColumn && !0 === c.p.treeGrid ? a(this).text() : a(this).html(), d = a.jgrid.jqID("v_" + d), a("#" + d + " span", "#" + m).html(f), e && a("#" + d, "#" + m).parents("tr:first").hide(), h++)
                    }), h > 0 && a("#id_g", "#" + m).val(b))
                }

                function h(b, c) {
                    var d = c[1].length - 1;
                    0 === b ? a("#pData", "#" + m + "_2").addClass("ui-state-disabled") : void 0 !== c[1][b - 1] && a("#" + a.jgrid.jqID(c[1][b - 1])).hasClass("ui-state-disabled") ? a("#pData", m + "_2").addClass("ui-state-disabled") : a("#pData", "#" + m + "_2").removeClass("ui-state-disabled"), b === d ? a("#nData", "#" + m + "_2").addClass("ui-state-disabled") : void 0 !== c[1][b + 1] && a("#" + a.jgrid.jqID(c[1][b + 1])).hasClass("ui-state-disabled") ? a("#nData", m + "_2").addClass("ui-state-disabled") : a("#nData", "#" + m + "_2").removeClass("ui-state-disabled")
                }

                function i() {
                    var b = a(j).jqGrid("getDataIDs"),
                        c = a("#id_g", "#" + m).val();
                    return [a.inArray(c, b), b]
                }
                var j = this;
                if (j.grid && c) {
                    var k = j.p.id,
                        l = "ViewGrid_" + a.jgrid.jqID(k),
                        m = "ViewTbl_" + a.jgrid.jqID(k),
                        n = "ViewGrid_" + k,
                        o = "ViewTbl_" + k,
                        p = {
                            "themodal": "viewmod" + k,
                            "modalhead": "viewhd" + k,
                            "modalcontent": "viewcnt" + k,
                            "scrollelm": l
                        },
                        q = a.isFunction(b[j.p.id].beforeInitData) ? b[j.p.id].beforeInitData : !1,
                        r = !0,
                        s = 1,
                        t = 0;
                    if (!0 === d.recreateForm && void 0 !== a("#" + a.jgrid.jqID(p.themodal))[0] && a("#" + a.jgrid.jqID(p.themodal)).remove(), void 0 !== a("#" + a.jgrid.jqID(p.themodal))[0]) {
                        if (q && (r = q.call(j, a("#" + l)), void 0 === r && (r = !0)), !1 === r) return;
                        a(".ui-jqdialog-title", "#" + a.jgrid.jqID(p.modalhead)).html(d.caption), a("#FormError", "#" + m).hide(), g(c, j), a.isFunction(b[j.p.id].beforeShowForm) && b[j.p.id].beforeShowForm.call(j, a("#" + l)), a.jgrid.viewModal("#" + a.jgrid.jqID(p.themodal), {
                            "gbox": "#gbox_" + a.jgrid.jqID(k),
                            "jqm": d.jqModal,
                            "jqM": !1,
                            "overlay": d.overlay,
                            "modal": d.modal
                        }), e()
                    } else {
                        var u = isNaN(d.dataheight) ? d.dataheight : d.dataheight + "px",
                            v = isNaN(d.datawidth) ? d.datawidth : d.datawidth + "px",
                            n = a("<form name='FormPost' id='" + n + "' class='FormGrid' style='width:" + v + ";overflow:auto;position:relative;height:" + u + ";'></form>"),
                            w = a("<table id='" + o + "' class='EditTable' cellspacing='1' cellpadding='2' border='0' style='table-layout:fixed'><tbody></tbody></table>");
                        if (q && (r = q.call(j, a("#" + l)), void 0 === r && (r = !0)), !1 === r) return;
                        if (a(j.p.colModel).each(function() {
                                var a = this.formoptions;
                                s = Math.max(s, a ? a.colpos || 0 : 0), t = Math.max(t, a ? a.rowpos || 0 : 0)
                            }), a(n).append(w), f(c, j, w, s), o = "rtl" === j.p.direction ? !0 : !1, q = "<a id='" + (o ? "nData" : "pData") + "' class='fm-button ui-state-default ui-corner-left'><span class='ui-icon ui-icon-triangle-1-w'></span></a>", r = "<a id='" + (o ? "pData" : "nData") + "' class='fm-button ui-state-default ui-corner-right'><span class='ui-icon ui-icon-triangle-1-e'></span></a>", u = "<a id='cData' class='fm-button ui-state-default ui-corner-all'>" + d.bClose + "</a>", t > 0) {
                            var x = [];
                            a.each(a(w)[0].rows, function(a, b) {
                                x[a] = b
                            }), x.sort(function(a, b) {
                                return a.rp > b.rp ? 1 : a.rp < b.rp ? -1 : 0
                            }), a.each(x, function(b, c) {
                                a("tbody", w).append(c)
                            })
                        }
                        d.gbox = "#gbox_" + a.jgrid.jqID(k), n = a("<div></div>").append(n).append("<table border='0' class='EditTable' id='" + m + "_2'><tbody><tr id='Act_Buttons'><td class='navButton' width='" + d.labelswidth + "'>" + (o ? r + q : q + r) + "</td><td class='EditButton'>" + u + "</td></tr></tbody></table>"), a.jgrid.createModal(p, n, d, "#gview_" + a.jgrid.jqID(j.p.id), a("#gview_" + a.jgrid.jqID(j.p.id))[0]), o && (a("#pData, #nData", "#" + m + "_2").css("float", "right"), a(".EditButton", "#" + m + "_2").css("text-align", "left")), d.viewPagerButtons || a("#pData, #nData", "#" + m + "_2").hide(), n = null, a("#" + p.themodal).keydown(function(c) {
                            if (27 === c.which) return b[j.p.id].closeOnEscape && a.jgrid.hideModal("#" + a.jgrid.jqID(p.themodal), {
                                "gb": d.gbox,
                                "jqm": d.jqModal,
                                "onClose": d.onClose
                            }), !1;
                            if (d.navkeys[0] === !0) {
                                if (c.which === d.navkeys[1]) return a("#pData", "#" + m + "_2").trigger("click"), !1;
                                if (c.which === d.navkeys[2]) return a("#nData", "#" + m + "_2").trigger("click"), !1
                            }
                        }), d.closeicon = a.extend([!0, "left", "ui-icon-close"], d.closeicon), !0 === d.closeicon[0] && a("#cData", "#" + m + "_2").addClass("right" === d.closeicon[1] ? "fm-button-icon-right" : "fm-button-icon-left").append("<span class='ui-icon " + d.closeicon[2] + "'></span>"), a.isFunction(d.beforeShowForm) && d.beforeShowForm.call(j, a("#" + l)), a.jgrid.viewModal("#" + a.jgrid.jqID(p.themodal), {
                            "gbox": "#gbox_" + a.jgrid.jqID(k),
                            "jqm": d.jqModal,
                            "overlay": d.overlay,
                            "modal": d.modal
                        }), a(".fm-button:not(.ui-state-disabled)", "#" + m + "_2").hover(function() {
                            a(this).addClass("ui-state-hover")
                        }, function() {
                            a(this).removeClass("ui-state-hover")
                        }), e(), a("#cData", "#" + m + "_2").click(function() {
                            return a.jgrid.hideModal("#" + a.jgrid.jqID(p.themodal), {
                                "gb": "#gbox_" + a.jgrid.jqID(k),
                                "jqm": d.jqModal,
                                "onClose": d.onClose
                            }), !1
                        }), a("#nData", "#" + m + "_2").click(function() {
                            a("#FormError", "#" + m).hide();
                            var b = i();
                            return b[0] = parseInt(b[0], 10), -1 !== b[0] && b[1][b[0] + 1] && (a.isFunction(d.onclickPgButtons) && d.onclickPgButtons.call(j, "next", a("#" + l), b[1][b[0]]), g(b[1][b[0] + 1], j), a(j).jqGrid("setSelection", b[1][b[0] + 1]), a.isFunction(d.afterclickPgButtons) && d.afterclickPgButtons.call(j, "next", a("#" + l), b[1][b[0] + 1]), h(b[0] + 1, b)), e(), !1
                        }), a("#pData", "#" + m + "_2").click(function() {
                            a("#FormError", "#" + m).hide();
                            var b = i();
                            return -1 !== b[0] && b[1][b[0] - 1] && (a.isFunction(d.onclickPgButtons) && d.onclickPgButtons.call(j, "prev", a("#" + l), b[1][b[0]]), g(b[1][b[0] - 1], j), a(j).jqGrid("setSelection", b[1][b[0] - 1]), a.isFunction(d.afterclickPgButtons) && d.afterclickPgButtons.call(j, "prev", a("#" + l), b[1][b[0] - 1]), h(b[0] - 1, b)), e(), !1
                        })
                    }
                    n = i(), h(n[0], n)
                }
            })
        },
        "delGridRow": function(c, d) {
            return d = a.extend(!0, {
                "top": 0,
                "left": 0,
                "width": 240,
                "height": "auto",
                "dataheight": "auto",
                "modal": !1,
                "overlay": 30,
                "drag": !0,
                "resize": !0,
                "url": "",
                "mtype": "POST",
                "reloadAfterSubmit": !0,
                "beforeShowForm": null,
                "beforeInitData": null,
                "afterShowForm": null,
                "beforeSubmit": null,
                "onclickSubmit": null,
                "afterSubmit": null,
                "jqModal": !0,
                "closeOnEscape": !1,
                "delData": {},
                "delicon": [],
                "cancelicon": [],
                "onClose": null,
                "ajaxDelOptions": {},
                "processing": !1,
                "serializeDelData": null,
                "useDataProxy": !1
            }, a.jgrid.del, d || {}), b[a(this)[0].p.id] = d, this.each(function() {
                var e = this;
                if (e.grid && c) {
                    var f, g, h, i, j = a.isFunction(b[e.p.id].beforeShowForm),
                        k = a.isFunction(b[e.p.id].afterShowForm),
                        l = a.isFunction(b[e.p.id].beforeInitData) ? b[e.p.id].beforeInitData : !1,
                        m = e.p.id,
                        n = {},
                        o = !0,
                        p = "DelTbl_" + a.jgrid.jqID(m),
                        q = "DelTbl_" + m,
                        r = {
                            "themodal": "delmod" + m,
                            "modalhead": "delhd" + m,
                            "modalcontent": "delcnt" + m,
                            "scrollelm": p
                        };
                    if (a.isArray(c) && (c = c.join()), void 0 !== a("#" + a.jgrid.jqID(r.themodal))[0]) {
                        if (l && (o = l.call(e, a("#" + p)), void 0 === o && (o = !0)), !1 === o) return;
                        a("#DelData>td", "#" + p).text(c), a("#DelError", "#" + p).hide(), !0 === b[e.p.id].processing && (b[e.p.id].processing = !1, a("#dData", "#" + p).removeClass("ui-state-active")), j && b[e.p.id].beforeShowForm.call(e, a("#" + p)), a.jgrid.viewModal("#" + a.jgrid.jqID(r.themodal), {
                            "gbox": "#gbox_" + a.jgrid.jqID(m),
                            "jqm": b[e.p.id].jqModal,
                            "jqM": !1,
                            "overlay": b[e.p.id].overlay,
                            "modal": b[e.p.id].modal
                        })
                    } else {
                        var s = isNaN(b[e.p.id].dataheight) ? b[e.p.id].dataheight : b[e.p.id].dataheight + "px",
                            t = isNaN(d.datawidth) ? d.datawidth : d.datawidth + "px",
                            q = "<div id='" + q + "' class='formdata' style='width:" + t + ";overflow:auto;position:relative;height:" + s + ";'><table class='DelTable'><tbody><tr id='DelError' style='display:none'><td class='ui-state-error'></td></tr>" + ("<tr id='DelData' style='display:none'><td >" + c + "</td></tr>"),
                            q = q + ('<tr><td class="delmsg" style="white-space:pre;">' + b[e.p.id].msg + "</td></tr><tr><td >&#160;</td></tr>"),
                            q = q + "</tbody></table></div>" + ("<table cellspacing='0' cellpadding='0' border='0' class='EditTable' id='" + p + "_2'><tbody><tr><td><hr class='ui-widget-content' style='margin:1px'/></td></tr><tr><td class='DelButton EditButton'>" + ("<a id='dData' class='fm-button ui-state-default ui-corner-all'>" + d.bSubmit + "</a>") + "&#160;" + ("<a id='eData' class='fm-button ui-state-default ui-corner-all'>" + d.bCancel + "</a>") + "</td></tr></tbody></table>");
                        if (d.gbox = "#gbox_" + a.jgrid.jqID(m), a.jgrid.createModal(r, q, d, "#gview_" + a.jgrid.jqID(e.p.id), a("#gview_" + a.jgrid.jqID(e.p.id))[0]), l && (o = l.call(e, a("#" + p)), void 0 === o && (o = !0)), !1 === o) return;
                        a(".fm-button", "#" + p + "_2").hover(function() {
                            a(this).addClass("ui-state-hover")
                        }, function() {
                            a(this).removeClass("ui-state-hover")
                        }), d.delicon = a.extend([!0, "left", "ui-icon-scissors"], b[e.p.id].delicon), d.cancelicon = a.extend([!0, "left", "ui-icon-cancel"], b[e.p.id].cancelicon), !0 === d.delicon[0] && a("#dData", "#" + p + "_2").addClass("right" === d.delicon[1] ? "fm-button-icon-right" : "fm-button-icon-left").append("<span class='ui-icon " + d.delicon[2] + "'></span>"), !0 === d.cancelicon[0] && a("#eData", "#" + p + "_2").addClass("right" === d.cancelicon[1] ? "fm-button-icon-right" : "fm-button-icon-left").append("<span class='ui-icon " + d.cancelicon[2] + "'></span>"), a("#dData", "#" + p + "_2").click(function() {
                            var c, j = [!0, ""],
                                k = a("#DelData>td", "#" + p).text();
                            if (n = {}, a.isFunction(b[e.p.id].onclickSubmit) && (n = b[e.p.id].onclickSubmit.call(e, b[e.p.id], k) || {}), a.isFunction(b[e.p.id].beforeSubmit) && (j = b[e.p.id].beforeSubmit.call(e, k)), j[0] && !b[e.p.id].processing) {
                                if (b[e.p.id].processing = !0, h = e.p.prmNames, f = a.extend({}, b[e.p.id].delData, n), i = h.oper, f[i] = h.deloper, g = h.id, k = ("" + k).split(","), !k.length) return !1;
                                for (c in k) k.hasOwnProperty(c) && (k[c] = a.jgrid.stripPref(e.p.idPrefix, k[c]));
                                f[g] = k.join(), a(this).addClass("ui-state-active"), c = a.extend({
                                    "url": b[e.p.id].url || a(e).jqGrid("getGridParam", "editurl"),
                                    "type": b[e.p.id].mtype,
                                    "data": a.isFunction(b[e.p.id].serializeDelData) ? b[e.p.id].serializeDelData.call(e, f) : f,
                                    "complete": function(c, g) {
                                        var h;
                                        if (c.status >= 300 && 304 !== c.status ? (j[0] = !1, j[1] = a.isFunction(b[e.p.id].errorTextFormat) ? b[e.p.id].errorTextFormat.call(e, c) : g + " Status: '" + c.statusText + "'. Error code: " + c.status) : a.isFunction(b[e.p.id].afterSubmit) && (j = b[e.p.id].afterSubmit.call(e, c, f)), j[0] === !1) a("#DelError>td", "#" + p).html(j[1]), a("#DelError", "#" + p).show();
                                        else {
                                            if (b[e.p.id].reloadAfterSubmit && "local" !== e.p.datatype) a(e).trigger("reloadGrid");
                                            else {
                                                if (e.p.treeGrid === !0) try {
                                                    a(e).jqGrid("delTreeNode", e.p.idPrefix + k[0])
                                                } catch (i) {} else
                                                    for (h = 0; h < k.length; h++) a(e).jqGrid("delRowData", e.p.idPrefix + k[h]);
                                                e.p.selrow = null, e.p.selarrrow = []
                                            }
                                            a.isFunction(b[e.p.id].afterComplete) && setTimeout(function() {
                                                b[e.p.id].afterComplete.call(e, c, k)
                                            }, 500)
                                        }
                                        b[e.p.id].processing = !1, a("#dData", "#" + p + "_2").removeClass("ui-state-active"), j[0] && a.jgrid.hideModal("#" + a.jgrid.jqID(r.themodal), {
                                            "gb": "#gbox_" + a.jgrid.jqID(m),
                                            "jqm": d.jqModal,
                                            "onClose": b[e.p.id].onClose
                                        })
                                    }
                                }, a.jgrid.ajaxOptions, b[e.p.id].ajaxDelOptions), c.url || b[e.p.id].useDataProxy || (a.isFunction(e.p.dataProxy) ? b[e.p.id].useDataProxy = !0 : (j[0] = !1, j[1] = j[1] + (" " + a.jgrid.errors.nourl))), j[0] && (b[e.p.id].useDataProxy ? (c = e.p.dataProxy.call(e, c, "del_" + e.p.id), void 0 === c && (c = [!0, ""]), c[0] === !1 ? (j[0] = !1, j[1] = c[1] || "Error deleting the selected row!") : a.jgrid.hideModal("#" + a.jgrid.jqID(r.themodal), {
                                    "gb": "#gbox_" + a.jgrid.jqID(m),
                                    "jqm": d.jqModal,
                                    "onClose": b[e.p.id].onClose
                                })) : a.ajax(c))
                            }
                            return j[0] === !1 && (a("#DelError>td", "#" + p).html(j[1]), a("#DelError", "#" + p).show()), !1
                        }), a("#eData", "#" + p + "_2").click(function() {
                            return a.jgrid.hideModal("#" + a.jgrid.jqID(r.themodal), {
                                "gb": "#gbox_" + a.jgrid.jqID(m),
                                "jqm": b[e.p.id].jqModal,
                                "onClose": b[e.p.id].onClose
                            }), !1
                        }), j && b[e.p.id].beforeShowForm.call(e, a("#" + p)), a.jgrid.viewModal("#" + a.jgrid.jqID(r.themodal), {
                            "gbox": "#gbox_" + a.jgrid.jqID(m),
                            "jqm": b[e.p.id].jqModal,
                            "overlay": b[e.p.id].overlay,
                            "modal": b[e.p.id].modal
                        })
                    }
                    k && b[e.p.id].afterShowForm.call(e, a("#" + p)), !0 === b[e.p.id].closeOnEscape && setTimeout(function() {
                        a(".ui-jqdialog-titlebar-close", "#" + a.jgrid.jqID(r.modalhead)).focus()
                    }, 0)
                }
            })
        },
        "navGrid": function(b, c, d, e, f, g, h) {
            return c = a.extend({
                "edit": !0,
                "editicon": "ui-icon-pencil",
                "add": !0,
                "addicon": "ui-icon-plus",
                "del": !0,
                "delicon": "ui-icon-trash",
                "search": !0,
                "searchicon": "ui-icon-search",
                "refresh": !0,
                "refreshicon": "ui-icon-refresh",
                "refreshstate": "firstpage",
                "view": !1,
                "viewicon": "ui-icon-document",
                "position": "left",
                "closeOnEscape": !0,
                "beforeRefresh": null,
                "afterRefresh": null,
                "cloneToTop": !1,
                "alertwidth": 200,
                "alertheight": "auto",
                "alerttop": null,
                "alertleft": null,
                "alertzIndex": null
            }, a.jgrid.nav, c || {}), this.each(function() {
                if (!this.nav) {
                    var i, j = {
                            "themodal": "alertmod_" + this.p.id,
                            "modalhead": "alerthd_" + this.p.id,
                            "modalcontent": "alertcnt_" + this.p.id
                        },
                        k = this;
                    if (k.grid && "string" == typeof b) {
                        void 0 === a("#" + j.themodal)[0] && (!c.alerttop && !c.alertleft && (void 0 !== window.innerWidth ? (c.alertleft = window.innerWidth, c.alerttop = window.innerHeight) : void 0 !== document.documentElement && void 0 !== document.documentElement.clientWidth && 0 !== document.documentElement.clientWidth ? (c.alertleft = document.documentElement.clientWidth, c.alerttop = document.documentElement.clientHeight) : (c.alertleft = 1024, c.alerttop = 768), c.alertleft = c.alertleft / 2 - parseInt(c.alertwidth, 10) / 2, c.alerttop = c.alerttop / 2 - 25), a.jgrid.createModal(j, "<div>" + c.alerttext + "</div><span tabindex='0'><span tabindex='-1' id='jqg_alrt'></span></span>", {
                            "gbox": "#gbox_" + a.jgrid.jqID(k.p.id),
                            "jqModal": !0,
                            "drag": !0,
                            "resize": !0,
                            "caption": c.alertcap,
                            "top": c.alerttop,
                            "left": c.alertleft,
                            "width": c.alertwidth,
                            "height": c.alertheight,
                            "closeOnEscape": c.closeOnEscape,
                            "zIndex": c.alertzIndex
                        }, "#gview_" + a.jgrid.jqID(k.p.id), a("#gbox_" + a.jgrid.jqID(k.p.id))[0], !0));
                        var l, m = 1,
                            n = function() {
                                a(this).hasClass("ui-state-disabled") || a(this).addClass("ui-state-hover")
                            },
                            o = function() {
                                a(this).removeClass("ui-state-hover")
                            };
                        for (c.cloneToTop && k.p.toppager && (m = 2), l = 0; m > l; l++) {
                            var p, q, r = a("<table cellspacing='0' cellpadding='0' border='0' class='ui-pg-table navtable' style='float:left;table-layout:auto;'><tbody><tr></tr></tbody></table>");
                            0 === l ? (p = b, q = k.p.id, p === k.p.toppager && (q += "_top", m = 1)) : (p = k.p.toppager, q = k.p.id + "_top"), "rtl" === k.p.direction && a(r).attr("dir", "rtl").css("float", "right"), c.add && (e = e || {}, i = a("<td class='ui-pg-button ui-corner-all'></td>"), a(i).append("<div class='ui-pg-div'><span class='ui-icon " + c.addicon + "'></span>" + c.addtext + "</div>"), a("tr", r).append(i), a(i, r).attr({
                                "title": c.addtitle || "",
                                "id": e.id || "add_" + q
                            }).click(function() {
                                return a(this).hasClass("ui-state-disabled") || (a.isFunction(c.addfunc) ? c.addfunc.call(k) : a(k).jqGrid("editGridRow", "new", e)), !1
                            }).hover(n, o), i = null), c.edit && (i = a("<td class='ui-pg-button ui-corner-all'></td>"), d = d || {}, a(i).append("<div class='ui-pg-div'><span class='ui-icon " + c.editicon + "'></span>" + c.edittext + "</div>"), a("tr", r).append(i), a(i, r).attr({
                                "title": c.edittitle || "",
                                "id": d.id || "edit_" + q
                            }).click(function() {
                                if (!a(this).hasClass("ui-state-disabled")) {
                                    var b = k.p.selrow;
                                    b ? a.isFunction(c.editfunc) ? c.editfunc.call(k, b) : a(k).jqGrid("editGridRow", b, d) : (a.jgrid.viewModal("#" + j.themodal, {
                                        "gbox": "#gbox_" + a.jgrid.jqID(k.p.id),
                                        "jqm": !0
                                    }), a("#jqg_alrt").focus())
                                }
                                return !1
                            }).hover(n, o), i = null), c.view && (i = a("<td class='ui-pg-button ui-corner-all'></td>"), h = h || {}, a(i).append("<div class='ui-pg-div'><span class='ui-icon " + c.viewicon + "'></span>" + c.viewtext + "</div>"), a("tr", r).append(i), a(i, r).attr({
                                "title": c.viewtitle || "",
                                "id": h.id || "view_" + q
                            }).click(function() {
                                if (!a(this).hasClass("ui-state-disabled")) {
                                    var b = k.p.selrow;
                                    b ? a.isFunction(c.viewfunc) ? c.viewfunc.call(k, b) : a(k).jqGrid("viewGridRow", b, h) : (a.jgrid.viewModal("#" + j.themodal, {
                                        "gbox": "#gbox_" + a.jgrid.jqID(k.p.id),
                                        "jqm": !0
                                    }), a("#jqg_alrt").focus())
                                }
                                return !1
                            }).hover(n, o), i = null), c.del && (i = a("<td class='ui-pg-button ui-corner-all'></td>"), f = f || {}, a(i).append("<div class='ui-pg-div'><span class='ui-icon " + c.delicon + "'></span>" + c.deltext + "</div>"), a("tr", r).append(i), a(i, r).attr({
                                "title": c.deltitle || "",
                                "id": f.id || "del_" + q
                            }).click(function() {
                                if (!a(this).hasClass("ui-state-disabled")) {
                                    var b;
                                    k.p.multiselect ? (b = k.p.selarrrow, 0 === b.length && (b = null)) : b = k.p.selrow, b ? a.isFunction(c.delfunc) ? c.delfunc.call(k, b) : a(k).jqGrid("delGridRow", b, f) : (a.jgrid.viewModal("#" + j.themodal, {
                                        "gbox": "#gbox_" + a.jgrid.jqID(k.p.id),
                                        "jqm": !0
                                    }), a("#jqg_alrt").focus())
                                }
                                return !1
                            }).hover(n, o), i = null), (c.add || c.edit || c.del || c.view) && a("tr", r).append("<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='ui-separator'></span></td>"), c.search && (i = a("<td class='ui-pg-button ui-corner-all'></td>"), g = g || {}, a(i).append("<div class='ui-pg-div'><span class='ui-icon " + c.searchicon + "'></span>" + c.searchtext + "</div>"), a("tr", r).append(i), a(i, r).attr({
                                "title": c.searchtitle || "",
                                "id": g.id || "search_" + q
                            }).click(function() {
                                return a(this).hasClass("ui-state-disabled") || (a.isFunction(c.searchfunc) ? c.searchfunc.call(k, g) : a(k).jqGrid("searchGrid", g)), !1
                            }).hover(n, o), g.showOnLoad && !0 === g.showOnLoad && a(i, r).click(), i = null), c.refresh && (i = a("<td class='ui-pg-button ui-corner-all'></td>"), a(i).append("<div class='ui-pg-div'><span class='ui-icon " + c.refreshicon + "'></span>" + c.refreshtext + "</div>"), a("tr", r).append(i), a(i, r).attr({
                                "title": c.refreshtitle || "",
                                "id": "refresh_" + q
                            }).click(function() {
                                if (!a(this).hasClass("ui-state-disabled")) {
                                    a.isFunction(c.beforeRefresh) && c.beforeRefresh.call(k), k.p.search = !1;
                                    try {
                                        var b = k.p.id;
                                        k.p.postData.filters = "";
                                        try {
                                            a("#fbox_" + a.jgrid.jqID(b)).jqFilter("resetFilter")
                                        } catch (d) {}
                                        a.isFunction(k.clearToolbar) && k.clearToolbar.call(k, !1)
                                    } catch (e) {}
                                    switch (c.refreshstate) {
                                        case "firstpage":
                                            a(k).trigger("reloadGrid", [{
                                                "page": 1
                                            }]);
                                            break;
                                        case "current":
                                            a(k).trigger("reloadGrid", [{
                                                "current": !0
                                            }])
                                    }
                                    a.isFunction(c.afterRefresh) && c.afterRefresh.call(k)
                                }
                                return !1
                            }).hover(n, o), i = null), i = a(".ui-jqgrid").css("font-size") || "11px", a("body").append("<div id='testpg2' class='ui-jqgrid ui-widget ui-widget-content' style='font-size:" + i + ";visibility:hidden;' ></div>"), i = a(r).clone().appendTo("#testpg2").width(), a("#testpg2").remove(), a(p + "_" + c.position, p).append(r), k.p._nvtd && (i > k.p._nvtd[0] && (a(p + "_" + c.position, p).width(i), k.p._nvtd[0] = i), k.p._nvtd[1] = i), r = i = i = null, this.nav = !0
                        }
                    }
                }
            })
        },
        "navButtonAdd": function(b, c) {
            return c = a.extend({
                "caption": "newButton",
                "title": "",
                "buttonicon": "ui-icon-newwin",
                "onClickButton": null,
                "position": "last",
                "cursor": "pointer"
            }, c || {}), this.each(function() {
                if (this.grid) {
                    "string" == typeof b && 0 !== b.indexOf("#") && (b = "#" + a.jgrid.jqID(b));
                    var d = a(".navtable", b)[0],
                        e = this;
                    if (d && (!c.id || void 0 === a("#" + a.jgrid.jqID(c.id), d)[0])) {
                        var f = a("<td></td>");
                        a(f).addClass("ui-pg-button ui-corner-all").append("NONE" === c.buttonicon.toString().toUpperCase() ? "<div class='ui-pg-div'>" + c.caption + "</div>" : "<div class='ui-pg-div'><span class='ui-icon " + c.buttonicon + "'></span>" + c.caption + "</div>"), c.id && a(f).attr("id", c.id), "first" === c.position ? 0 === d.rows[0].cells.length ? a("tr", d).append(f) : a("tr td:eq(0)", d).before(f) : a("tr", d).append(f), a(f, d).attr("title", c.title || "").click(function(b) {
                            return a(this).hasClass("ui-state-disabled") || a.isFunction(c.onClickButton) && c.onClickButton.call(e, b), !1
                        }).hover(function() {
                            a(this).hasClass("ui-state-disabled") || a(this).addClass("ui-state-hover")
                        }, function() {
                            a(this).removeClass("ui-state-hover")
                        })
                    }
                }
            })
        },
        "navSeparatorAdd": function(b, c) {
            return c = a.extend({
                "sepclass": "ui-separator",
                "sepcontent": "",
                "position": "last"
            }, c || {}), this.each(function() {
                if (this.grid) {
                    "string" == typeof b && 0 !== b.indexOf("#") && (b = "#" + a.jgrid.jqID(b));
                    var d = a(".navtable", b)[0];
                    if (d) {
                        var e = "<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='" + c.sepclass + "'></span>" + c.sepcontent + "</td>";
                        "first" === c.position ? 0 === d.rows[0].cells.length ? a("tr", d).append(e) : a("tr td:eq(0)", d).before(e) : a("tr", d).append(e)
                    }
                }
            })
        },
        "GridToForm": function(b, c) {
            return this.each(function() {
                var d, e = this;
                if (e.grid) {
                    var f = a(e).jqGrid("getRowData", b);
                    if (f)
                        for (d in f) f.hasOwnProperty(d) && (a("[name=" + a.jgrid.jqID(d) + "]", c).is("input:radio") || a("[name=" + a.jgrid.jqID(d) + "]", c).is("input:checkbox") ? a("[name=" + a.jgrid.jqID(d) + "]", c).each(function() {
                            a(this).val() == f[d] ? a(this)[e.p.useProp ? "prop" : "attr"]("checked", !0) : a(this)[e.p.useProp ? "prop" : "attr"]("checked", !1)
                        }) : a("[name=" + a.jgrid.jqID(d) + "]", c).val(f[d]))
                }
            })
        },
        "FormToGrid": function(b, c, d, e) {
            return this.each(function() {
                if (this.grid) {
                    d || (d = "set"), e || (e = "first");
                    var f = a(c).serializeArray(),
                        g = {};
                    a.each(f, function(a, b) {
                        g[b.name] = b.value
                    }), "add" === d ? a(this).jqGrid("addRowData", b, g, e) : "set" === d && a(this).jqGrid("setRowData", b, g)
                }
            })
        }
    })
}(jQuery),
function(a) {
    a.fn.jqFilter = function(b) {
        if ("string" == typeof b) {
            var c = a.fn.jqFilter[b];
            if (!c) throw "jqFilter - No such method: " + b;
            var d = a.makeArray(arguments).slice(1);
            return c.apply(this, d)
        }
        var e = a.extend(!0, {
            "filter": null,
            "columns": [],
            "onChange": null,
            "afterRedraw": null,
            "checkValues": null,
            "error": !1,
            "errmsg": "",
            "errorcheck": !0,
            "showQuery": !0,
            "sopt": null,
            "ops": [],
            "operands": null,
            "numopts": "eq,ne,lt,le,gt,ge,nu,nn,in,ni".split(","),
            "stropts": "eq,ne,bw,bn,ew,en,cn,nc,nu,nn,in,ni".split(","),
            "strarr": ["text", "string", "blob"],
            "groupOps": [{
                "op": "AND",
                "text": "AND"
            }, {
                "op": "OR",
                "text": "OR"
            }],
            "groupButton": !0,
            "ruleButtons": !0,
            "direction": "ltr"
        }, a.jgrid.filter, b || {});
        return this.each(function() {
            if (!this.filter) {
                this.p = e, (null === this.p.filter || void 0 === this.p.filter) && (this.p.filter = {
                    "groupOp": this.p.groupOps[0].op,
                    "rules": [],
                    "groups": []
                });
                var b, c, d = this.p.columns.length,
                    f = /msie/i.test(navigator.userAgent) && !window.opera;
                if (this.p.initFilter = a.extend(!0, {}, this.p.filter), d) {
                    for (b = 0; d > b; b++) c = this.p.columns[b], c.stype ? c.inputtype = c.stype : c.inputtype || (c.inputtype = "text"), c.sorttype ? c.searchtype = c.sorttype : c.searchtype || (c.searchtype = "string"), void 0 === c.hidden && (c.hidden = !1), c.label || (c.label = c.name), c.index && (c.name = c.index), c.hasOwnProperty("searchoptions") || (c.searchoptions = {}), c.hasOwnProperty("searchrules") || (c.searchrules = {});
                    this.p.showQuery && a(this).append("<table class='queryresult ui-widget ui-widget-content' style='display:block;max-width:440px;border:0px none;' dir='" + this.p.direction + "'><tbody><tr><td class='query'></td></tr></tbody></table>");
                    var g = function() {
                            return a("#" + a.jgrid.jqID(e.id))[0] || null
                        },
                        h = function(b, c) {
                            var d = [!0, ""],
                                f = g();
                            if (a.isFunction(c.searchrules)) d = c.searchrules.call(f, b, c);
                            else if (a.jgrid && a.jgrid.checkValues) try {
                                d = a.jgrid.checkValues.call(f, b, -1, c.searchrules, c.label)
                            } catch (h) {}
                            d && d.length && !1 === d[0] && (e.error = !d[0], e.errmsg = d[1])
                        };
                    this.onchange = function() {
                        return this.p.error = !1, this.p.errmsg = "", a.isFunction(this.p.onChange) ? this.p.onChange.call(this, this.p) : !1
                    }, this.reDraw = function() {
                        a("table.group:first", this).remove();
                        var b = this.createTableForGroup(e.filter, null);
                        a(this).append(b), a.isFunction(this.p.afterRedraw) && this.p.afterRedraw.call(this, this.p)
                    }, this.createTableForGroup = function(b, c) {
                        var d, f = this,
                            g = a("<table class='group ui-widget ui-widget-content' style='border:0px none;'><tbody></tbody></table>"),
                            h = "left";
                        "rtl" === this.p.direction && (h = "right", g.attr("dir", "rtl")), null === c && g.append("<tr class='error' style='display:none;'><th colspan='5' class='ui-state-error' align='" + h + "'></th></tr>");
                        var i = a("<tr></tr>");
                        if (g.append(i), h = a("<th colspan='5' align='" + h + "'></th>"), i.append(h), !0 === this.p.ruleButtons) {
                            var j = a("<select class='opsel'></select>");
                            h.append(j);
                            var k, i = "";
                            for (d = 0; d < e.groupOps.length; d++) k = b.groupOp === f.p.groupOps[d].op ? " selected='selected'" : "", i += "<option value='" + f.p.groupOps[d].op + "'" + k + ">" + f.p.groupOps[d].text + "</option>";
                            j.append(i).bind("change", function() {
                                b.groupOp = a(j).val(), f.onchange()
                            })
                        }
                        if (i = "<span></span>", this.p.groupButton && (i = a("<input type='button' value='+ {}' title='Add subgroup' class='add-group'/>"), i.bind("click", function() {
                                return void 0 === b.groups && (b.groups = []), b.groups.push({
                                    "groupOp": e.groupOps[0].op,
                                    "rules": [],
                                    "groups": []
                                }), f.reDraw(), f.onchange(), !1
                            })), h.append(i), !0 === this.p.ruleButtons) {
                            var l, i = a("<input type='button' value='+' title='Add rule' class='add-rule ui-add'/>");
                            i.bind("click", function() {
                                for (void 0 === b.rules && (b.rules = []), d = 0; d < f.p.columns.length; d++) {
                                    var c = void 0 === f.p.columns[d].search ? !0 : f.p.columns[d].search,
                                        e = f.p.columns[d].hidden === !0;
                                    if (f.p.columns[d].searchoptions.searchhidden === !0 && c || c && !e) {
                                        l = f.p.columns[d];
                                        break
                                    }
                                }
                                return c = l.searchoptions.sopt ? l.searchoptions.sopt : f.p.sopt ? f.p.sopt : -1 !== a.inArray(l.searchtype, f.p.strarr) ? f.p.stropts : f.p.numopts, b.rules.push({
                                    "field": l.name,
                                    "op": c[0],
                                    "data": ""
                                }), f.reDraw(), !1
                            }), h.append(i)
                        }
                        if (null !== c && (i = a("<input type='button' value='-' title='Delete group' class='delete-group'/>"), h.append(i), i.bind("click", function() {
                                for (d = 0; d < c.groups.length; d++)
                                    if (c.groups[d] === b) {
                                        c.groups.splice(d, 1);
                                        break
                                    }
                                return f.reDraw(), f.onchange(), !1
                            })), void 0 !== b.groups)
                            for (d = 0; d < b.groups.length; d++) h = a("<tr></tr>"), g.append(h), i = a("<td class='first'></td>"), h.append(i), i = a("<td colspan='4'></td>"), i.append(this.createTableForGroup(b.groups[d], b)), h.append(i);
                        if (void 0 === b.groupOp && (b.groupOp = f.p.groupOps[0].op), void 0 !== b.rules)
                            for (d = 0; d < b.rules.length; d++) g.append(this.createTableRowForRule(b.rules[d], b));
                        return g
                    }, this.createTableRowForRule = function(b, c) {
                        var d, h, i, j, k, l = this,
                            m = g(),
                            n = a("<tr></tr>"),
                            o = "";
                        n.append("<td class='first'></td>");
                        var p = a("<td class='columns'></td>");
                        n.append(p);
                        var q, r = a("<select></select>"),
                            s = [];
                        for (p.append(r), r.bind("change", function() {
                                for (b.field = a(r).val(), i = a(this).parents("tr:first"), d = 0; d < l.p.columns.length; d++)
                                    if (l.p.columns[d].name === b.field) {
                                        j = l.p.columns[d];
                                        break
                                    }
                                if (j) {
                                    j.searchoptions.id = a.jgrid.randId(), f && "text" === j.inputtype && !j.searchoptions.size && (j.searchoptions.size = 10);
                                    var c = a.jgrid.createEl.call(m, j.inputtype, j.searchoptions, "", !0, l.p.ajaxSelectOptions || {}, !0);
                                    a(c).addClass("input-elm"), h = j.searchoptions.sopt ? j.searchoptions.sopt : l.p.sopt ? l.p.sopt : -1 !== a.inArray(j.searchtype, l.p.strarr) ? l.p.stropts : l.p.numopts;
                                    var e = "",
                                        g = 0;
                                    for (s = [], a.each(l.p.ops, function() {
                                            s.push(this.oper)
                                        }), d = 0; d < h.length; d++) q = a.inArray(h[d], s), -1 !== q && (0 === g && (b.op = l.p.ops[q].oper), e += "<option value='" + l.p.ops[q].oper + "'>" + l.p.ops[q].text + "</option>", g++);
                                    a(".selectopts", i).empty().append(e), a(".selectopts", i)[0].selectedIndex = 0, a.jgrid.msie && 9 > a.jgrid.msiever() && (e = parseInt(a("select.selectopts", i)[0].offsetWidth, 10) + 1, a(".selectopts", i).width(e), a(".selectopts", i).css("width", "auto")), a(".data", i).empty().append(c), a.jgrid.bindEv.call(m, c, j.searchoptions), a(".input-elm", i).bind("change", function(c) {
                                        var d = a(this).hasClass("ui-autocomplete-input") ? 200 : 0;
                                        setTimeout(function() {
                                            var d = c.target;
                                            b.data = "SPAN" === d.nodeName.toUpperCase() && j.searchoptions && a.isFunction(j.searchoptions.custom_value) ? j.searchoptions.custom_value.call(m, a(d).children(".customelement:first"), "get") : d.value, l.onchange()
                                        }, d)
                                    }), setTimeout(function() {
                                        b.data = a(c).val(), l.onchange()
                                    }, 0)
                                }
                            }), d = p = 0; d < l.p.columns.length; d++) {
                            k = void 0 === l.p.columns[d].search ? !0 : l.p.columns[d].search;
                            var t = !0 === l.p.columns[d].hidden;
                            (!0 === l.p.columns[d].searchoptions.searchhidden && k || k && !t) && (k = "", b.field === l.p.columns[d].name && (k = " selected='selected'", p = d), o += "<option value='" + l.p.columns[d].name + "'" + k + ">" + l.p.columns[d].label + "</option>")
                        }
                        r.append(o), o = a("<td class='operators'></td>"), n.append(o), j = e.columns[p], j.searchoptions.id = a.jgrid.randId(), f && "text" === j.inputtype && !j.searchoptions.size && (j.searchoptions.size = 10), p = a.jgrid.createEl.call(m, j.inputtype, j.searchoptions, b.data, !0, l.p.ajaxSelectOptions || {}, !0), ("nu" === b.op || "nn" === b.op) && (a(p).attr("readonly", "true"), a(p).attr("disabled", "true"));
                        var u = a("<select class='selectopts'></select>");
                        for (o.append(u), u.bind("change", function() {
                                b.op = a(u).val(), i = a(this).parents("tr:first");
                                var c = a(".input-elm", i)[0];
                                "nu" === b.op || "nn" === b.op ? (b.data = "", "SELECT" !== c.tagName.toUpperCase() && (c.value = ""), c.setAttribute("readonly", "true"), c.setAttribute("disabled", "true")) : ("SELECT" === c.tagName.toUpperCase() && (b.data = c.value), c.removeAttribute("readonly"), c.removeAttribute("disabled")), l.onchange()
                            }), h = j.searchoptions.sopt ? j.searchoptions.sopt : l.p.sopt ? l.p.sopt : -1 !== a.inArray(j.searchtype, l.p.strarr) ? l.p.stropts : l.p.numopts, o = "", a.each(l.p.ops, function() {
                                s.push(this.oper)
                            }), d = 0; d < h.length; d++) q = a.inArray(h[d], s), -1 !== q && (k = b.op === l.p.ops[q].oper ? " selected='selected'" : "", o += "<option value='" + l.p.ops[q].oper + "'" + k + ">" + l.p.ops[q].text + "</option>");
                        return u.append(o), o = a("<td class='data'></td>"), n.append(o), o.append(p), a.jgrid.bindEv.call(m, p, j.searchoptions), a(p).addClass("input-elm").bind("change", function() {
                            b.data = "custom" === j.inputtype ? j.searchoptions.custom_value.call(m, a(this).children(".customelement:first"), "get") : a(this).val(), l.onchange()
                        }), o = a("<td></td>"), n.append(o), !0 === this.p.ruleButtons && (p = a("<input type='button' value='-' title='Delete rule' class='delete-rule ui-del'/>"), o.append(p), p.bind("click", function() {
                            for (d = 0; d < c.rules.length; d++)
                                if (c.rules[d] === b) {
                                    c.rules.splice(d, 1);
                                    break
                                }
                            return l.reDraw(), l.onchange(), !1
                        })), n
                    }, this.getStringForGroup = function(a) {
                        var b, c = "(";
                        if (void 0 !== a.groups)
                            for (b = 0; b < a.groups.length; b++) {
                                1 < c.length && (c += " " + a.groupOp + " ");
                                try {
                                    c += this.getStringForGroup(a.groups[b])
                                } catch (d) {
                                    alert(d)
                                }
                            }
                        if (void 0 !== a.rules) try {
                            for (b = 0; b < a.rules.length; b++) 1 < c.length && (c += " " + a.groupOp + " "), c += this.getStringForRule(a.rules[b])
                        } catch (e) {
                            alert(e)
                        }
                        return c += ")", "()" === c ? "" : c
                    }, this.getStringForRule = function(b) {
                        var c, d, f = "",
                            g = "";
                        for (c = 0; c < this.p.ops.length; c++)
                            if (this.p.ops[c].oper === b.op) {
                                f = this.p.operands.hasOwnProperty(b.op) ? this.p.operands[b.op] : "", g = this.p.ops[c].oper;
                                break
                            }
                        for (c = 0; c < this.p.columns.length; c++)
                            if (this.p.columns[c].name === b.field) {
                                d = this.p.columns[c];
                                break
                            }
                        return void 0 === d ? "" : (c = b.data, ("bw" === g || "bn" === g) && (c += "%"), ("ew" === g || "en" === g) && (c = "%" + c), ("cn" === g || "nc" === g) && (c = "%" + c + "%"), ("in" === g || "ni" === g) && (c = " (" + c + ")"), e.errorcheck && h(b.data, d), -1 !== a.inArray(d.searchtype, ["int", "integer", "float", "number", "currency"]) || "nn" === g || "nu" === g ? b.field + " " + f + " " + c : b.field + " " + f + ' "' + c + '"')
                    }, this.resetFilter = function() {
                        this.p.filter = a.extend(!0, {}, this.p.initFilter), this.reDraw(), this.onchange()
                    }, this.hideError = function() {
                        a("th.ui-state-error", this).html(""), a("tr.error", this).hide()
                    }, this.showError = function() {
                        a("th.ui-state-error", this).html(this.p.errmsg), a("tr.error", this).show()
                    }, this.toUserFriendlyString = function() {
                        return this.getStringForGroup(e.filter)
                    }, this.toString = function() {
                        function a(c) {
                            var d, e = "(";
                            if (void 0 !== c.groups)
                                for (d = 0; d < c.groups.length; d++) 1 < e.length && (e = "OR" === c.groupOp ? e + " || " : e + " && "), e += a(c.groups[d]);
                            if (void 0 !== c.rules)
                                for (d = 0; d < c.rules.length; d++) {
                                    1 < e.length && (e = "OR" === c.groupOp ? e + " || " : e + " && ");
                                    var f = c.rules[d];
                                    if (b.p.errorcheck) {
                                        for (var g = void 0, i = void 0, g = 0; g < b.p.columns.length; g++)
                                            if (b.p.columns[g].name === f.field) {
                                                i = b.p.columns[g];
                                                break
                                            }
                                        i && h(f.data, i)
                                    }
                                    e += f.op + "(item." + f.field + ",'" + f.data + "')"
                                }
                            return e += ")", "()" === e ? "" : e
                        }
                        var b = this;
                        return a(this.p.filter)
                    }, this.reDraw(), this.p.showQuery && this.onchange(), this.filter = !0
                }
            }
        })
    }, a.extend(a.fn.jqFilter, {
        "toSQLString": function() {
            var a = "";
            return this.each(function() {
                a = this.toUserFriendlyString()
            }), a
        },
        "filterData": function() {
            var a;
            return this.each(function() {
                a = this.p.filter
            }), a
        },
        "getParameter": function(a) {
            return void 0 !== a && this.p.hasOwnProperty(a) ? this.p[a] : this.p
        },
        "resetFilter": function() {
            return this.each(function() {
                this.resetFilter()
            })
        },
        "addFilter": function(b) {
            "string" == typeof b && (b = a.jgrid.parse(b)), this.each(function() {
                this.p.filter = b, this.reDraw(), this.onchange()
            })
        }
    })
}(jQuery),
function(a) {
    a.jgrid.inlineEdit = a.jgrid.inlineEdit || {}, a.jgrid.extend({
        "editRow": function(b, c, d, e, f, g, h, i, j) {
            var k = {},
                l = a.makeArray(arguments).slice(1);
            return "object" === a.type(l[0]) ? k = l[0] : (void 0 !== c && (k.keys = c), a.isFunction(d) && (k.oneditfunc = d), a.isFunction(e) && (k.successfunc = e), void 0 !== f && (k.url = f), void 0 !== g && (k.extraparam = g), a.isFunction(h) && (k.aftersavefunc = h), a.isFunction(i) && (k.errorfunc = i), a.isFunction(j) && (k.afterrestorefunc = j)), k = a.extend(!0, {
                "keys": !1,
                "oneditfunc": null,
                "successfunc": null,
                "url": null,
                "extraparam": {},
                "aftersavefunc": null,
                "errorfunc": null,
                "afterrestorefunc": null,
                "restoreAfterError": !0,
                "mtype": "POST"
            }, a.jgrid.inlineEdit, k), this.each(function() {
                var c, d, e, f, g, h = this,
                    i = 0,
                    j = null,
                    l = {};
                h.grid && (e = a(h).jqGrid("getInd", b, !0), !1 !== e && (g = a.isFunction(k.beforeEditRow) ? k.beforeEditRow.call(h, k, b) : void 0, void 0 === g && (g = !0), g && "0" === (a(e).attr("editable") || "0") && !a(e).hasClass("not-editable-row"))) && (f = h.p.colModel, a('td[role="gridcell"]', e).each(function(e) {
                    c = f[e].name;
                    var g = !0 === h.p.treeGrid && c === h.p.ExpandColumn;
                    if (g) d = a("span:first", this).html();
                    else try {
                        d = a.unformat.call(h, this, {
                            "rowId": b,
                            "colModel": f[e]
                        }, e)
                    } catch (k) {
                        d = f[e].edittype && "textarea" === f[e].edittype ? a(this).text() : a(this).html()
                    }
                    if ("cb" !== c && "subgrid" !== c && "rn" !== c && (h.p.autoencode && (d = a.jgrid.htmlDecode(d)), l[c] = d, !0 === f[e].editable)) {
                        null === j && (j = e), g ? a("span:first", this).html("") : a(this).html("");
                        var m = a.extend({}, f[e].editoptions || {}, {
                            "id": b + "_" + c,
                            "name": c
                        });
                        f[e].edittype || (f[e].edittype = "text"), ("&nbsp;" === d || "&#160;" === d || 1 === d.length && 160 === d.charCodeAt(0)) && (d = "");
                        var n = a.jgrid.createEl.call(h, f[e].edittype, m, d, !0, a.extend({}, a.jgrid.ajaxOptions, h.p.ajaxSelectOptions || {}));
                        a(n).addClass("editable"), g ? a("span:first", this).append(n) : a(this).append(n), a.jgrid.bindEv.call(h, n, m), "select" === f[e].edittype && void 0 !== f[e].editoptions && !0 === f[e].editoptions.multiple && void 0 === f[e].editoptions.dataUrl && a.jgrid.msie && a(n).width(a(n).width()), i++
                    }
                }), i > 0 && (l.id = b, h.p.savedRow.push(l), a(e).attr("editable", "1"), a("td:eq(" + j + ") input", e).focus(), !0 === k.keys && a(e).bind("keydown", function(c) {
                    if (27 === c.keyCode) {
                        if (a(h).jqGrid("restoreRow", b, k.afterrestorefunc), h.p._inlinenav) try {
                            a(h).jqGrid("showAddEditButtons")
                        } catch (d) {}
                        return !1
                    }
                    if (13 === c.keyCode) {
                        if ("TEXTAREA" === c.target.tagName) return !0;
                        if (a(h).jqGrid("saveRow", b, k) && h.p._inlinenav) try {
                            a(h).jqGrid("showAddEditButtons")
                        } catch (e) {}
                        return !1
                    }
                }), a(h).triggerHandler("jqGridInlineEditRow", [b, k]), a.isFunction(k.oneditfunc) && k.oneditfunc.call(h, b)))
            })
        },
        "saveRow": function(b, c, d, e, f, g, h) {
            var i = a.makeArray(arguments).slice(1),
                j = {};
            "object" === a.type(i[0]) ? j = i[0] : (a.isFunction(c) && (j.successfunc = c), void 0 !== d && (j.url = d), void 0 !== e && (j.extraparam = e), a.isFunction(f) && (j.aftersavefunc = f), a.isFunction(g) && (j.errorfunc = g), a.isFunction(h) && (j.afterrestorefunc = h));
            var k, l, m, n, j = a.extend(!0, {
                    "successfunc": null,
                    "url": null,
                    "extraparam": {},
                    "aftersavefunc": null,
                    "errorfunc": null,
                    "afterrestorefunc": null,
                    "restoreAfterError": !0,
                    "mtype": "POST"
                }, a.jgrid.inlineEdit, j),
                o = !1,
                p = this[0],
                q = {},
                r = {},
                s = {};
            if (!p.grid) return o;
            if (n = a(p).jqGrid("getInd", b, !0), !1 === n) return o;
            if (i = a.isFunction(j.beforeSaveRow) ? j.beforeSaveRow.call(p, j, b) : void 0, void 0 === i && (i = !0), i) {
                if (i = a(n).attr("editable"), j.url = j.url || p.p.editurl, "1" === i) {
                    var t;
                    if (a('td[role="gridcell"]', n).each(function(b) {
                            if (t = p.p.colModel[b], k = t.name, "cb" !== k && "subgrid" !== k && !0 === t.editable && "rn" !== k && !a(this).hasClass("not-editable-cell")) {
                                switch (t.edittype) {
                                    case "checkbox":
                                        var c = ["Yes", "No"];
                                        t.editoptions && (c = t.editoptions.value.split(":")), q[k] = a("input", this).is(":checked") ? c[0] : c[1];
                                        break;
                                    case "text":
                                    case "password":
                                    case "textarea":
                                    case "button":
                                        q[k] = a("input, textarea", this).val();
                                        break;
                                    case "select":
                                        if (t.editoptions.multiple) {
                                            var c = a("select", this),
                                                d = [];
                                            q[k] = a(c).val(), q[k] = q[k] ? q[k].join(",") : "", a("select option:selected", this).each(function(b, c) {
                                                d[b] = a(c).text()
                                            }), r[k] = d.join(",")
                                        } else q[k] = a("select option:selected", this).val(), r[k] = a("select option:selected", this).text();
                                        t.formatter && "select" === t.formatter && (r = {});
                                        break;
                                    case "custom":
                                        try {
                                            if (!t.editoptions || !a.isFunction(t.editoptions.custom_value)) throw "e1";
                                            if (q[k] = t.editoptions.custom_value.call(p, a(".customelement", this), "get"), void 0 === q[k]) throw "e2"
                                        } catch (e) {
                                            "e1" === e && a.jgrid.info_dialog(a.jgrid.errors.errcap, "function 'custom_value' " + a.jgrid.edit.msg.nodefined, a.jgrid.edit.bClose), "e2" === e ? a.jgrid.info_dialog(a.jgrid.errors.errcap, "function 'custom_value' " + a.jgrid.edit.msg.novalue, a.jgrid.edit.bClose) : a.jgrid.info_dialog(a.jgrid.errors.errcap, e.message, a.jgrid.edit.bClose)
                                        }
                                }
                                if (m = a.jgrid.checkValues.call(p, q[k], b), !1 === m[0]) return !1;
                                p.p.autoencode && (q[k] = a.jgrid.htmlEncode(q[k])), "clientArray" !== j.url && t.editoptions && !0 === t.editoptions.NullIfEmpty && "" === q[k] && (s[k] = "null")
                            }
                        }), !1 === m[0]) {
                        try {
                            var u = a(p).jqGrid("getGridRowById", b),
                                v = a.jgrid.findPos(u);
                            a.jgrid.info_dialog(a.jgrid.errors.errcap, m[1], a.jgrid.edit.bClose, {
                                "left": v[0],
                                "top": v[1] + a(u).outerHeight()
                            })
                        } catch (w) {
                            alert(m[1])
                        }
                        return o
                    }
                    if (i = p.p.prmNames, u = b, v = !1 === p.p.keyIndex ? i.id : p.p.colModel[p.p.keyIndex + (!0 === p.p.rownumbers ? 1 : 0) + (!0 === p.p.multiselect ? 1 : 0) + (!0 === p.p.subGrid ? 1 : 0)].name, q && (q[i.oper] = i.editoper, void 0 === q[v] || "" === q[v] ? q[v] = b : n.id !== p.p.idPrefix + q[v] && (i = a.jgrid.stripPref(p.p.idPrefix, b), void 0 !== p.p._index[i] && (p.p._index[q[v]] = p.p._index[i], delete p.p._index[i]), b = p.p.idPrefix + q[v], a(n).attr("id", b), p.p.selrow === u && (p.p.selrow = b), a.isArray(p.p.selarrrow) && (i = a.inArray(u, p.p.selarrrow), i >= 0 && (p.p.selarrrow[i] = b)), p.p.multiselect) && (i = "jqg_" + p.p.id + "_" + b, a("input.cbox", n).attr("id", i).attr("name", i)), void 0 === p.p.inlineData && (p.p.inlineData = {}), q = a.extend({}, q, p.p.inlineData, j.extraparam)), "clientArray" === j.url) {
                        for (q = a.extend({}, q, r), p.p.autoencode && a.each(q, function(b, c) {
                                q[b] = a.jgrid.htmlDecode(c)
                            }), i = a(p).jqGrid("setRowData", b, q), a(n).attr("editable", "0"), v = 0; v < p.p.savedRow.length; v++)
                            if ("" + p.p.savedRow[v].id == "" + u) {
                                l = v;
                                break
                            }
                        l >= 0 && p.p.savedRow.splice(l, 1), a(p).triggerHandler("jqGridInlineAfterSaveRow", [b, i, q, j]), a.isFunction(j.aftersavefunc) && j.aftersavefunc.call(p, b, i, j), o = !0, a(n).removeClass("jqgrid-new-row").unbind("keydown")
                    } else a("#lui_" + a.jgrid.jqID(p.p.id)).show(), s = a.extend({}, q, s), s[v] = a.jgrid.stripPref(p.p.idPrefix, s[v]), a.ajax(a.extend({
                        "url": j.url,
                        "data": a.isFunction(p.p.serializeRowData) ? p.p.serializeRowData.call(p, s) : s,
                        "type": j.mtype,
                        "async": !1,
                        "complete": function(c, d) {
                            if (a("#lui_" + a.jgrid.jqID(p.p.id)).hide(), "success" === d) {
                                var e, f = !0;
                                if (e = a(p).triggerHandler("jqGridInlineSuccessSaveRow", [c, b, j]), a.isArray(e) || (e = [!0, q]), e[0] && a.isFunction(j.successfunc) && (e = j.successfunc.call(p, c)), a.isArray(e) ? (f = e[0], q = e[1] || q) : f = e, !0 === f) {
                                    for (p.p.autoencode && a.each(q, function(b, c) {
                                            q[b] = a.jgrid.htmlDecode(c)
                                        }), q = a.extend({}, q, r), a(p).jqGrid("setRowData", b, q), a(n).attr("editable", "0"), f = 0; f < p.p.savedRow.length; f++)
                                        if ("" + p.p.savedRow[f].id == "" + b) {
                                            l = f;
                                            break
                                        }
                                    l >= 0 && p.p.savedRow.splice(l, 1), a(p).triggerHandler("jqGridInlineAfterSaveRow", [b, c, q, j]), a.isFunction(j.aftersavefunc) && j.aftersavefunc.call(p, b, c), o = !0, a(n).removeClass("jqgrid-new-row").unbind("keydown")
                                } else a(p).triggerHandler("jqGridInlineErrorSaveRow", [b, c, d, null, j]), a.isFunction(j.errorfunc) && j.errorfunc.call(p, b, c, d, null), !0 === j.restoreAfterError && a(p).jqGrid("restoreRow", b, j.afterrestorefunc)
                            }
                        },
                        "error": function(c, d, e) {
                            if (a("#lui_" + a.jgrid.jqID(p.p.id)).hide(), a(p).triggerHandler("jqGridInlineErrorSaveRow", [b, c, d, e, j]), a.isFunction(j.errorfunc)) j.errorfunc.call(p, b, c, d, e);
                            else {
                                c = c.responseText || c.statusText;
                                try {
                                    a.jgrid.info_dialog(a.jgrid.errors.errcap, '<div class="ui-state-error">' + c + "</div>", a.jgrid.edit.bClose, {
                                        "buttonalign": "right"
                                    })
                                } catch (f) {
                                    alert(c)
                                }
                            }!0 === j.restoreAfterError && a(p).jqGrid("restoreRow", b, j.afterrestorefunc)
                        }
                    }, a.jgrid.ajaxOptions, p.p.ajaxRowOptions || {}))
                }
                return o
            }
        },
        "restoreRow": function(b, c) {
            var d = a.makeArray(arguments).slice(1),
                e = {};
            return "object" === a.type(d[0]) ? e = d[0] : a.isFunction(c) && (e.afterrestorefunc = c), e = a.extend(!0, {}, a.jgrid.inlineEdit, e), this.each(function() {
                var c, d, f, g = this,
                    h = {};
                if (g.grid && (d = a(g).jqGrid("getInd", b, !0), d !== !1 && (f = a.isFunction(e.beforeCancelRow) ? e.beforeCancelRow.call(g, cancelPrm, sr) : void 0, void 0 === f && (f = !0), f))) {
                    for (f = 0; f < g.p.savedRow.length; f++)
                        if ("" + g.p.savedRow[f].id == "" + b) {
                            c = f;
                            break
                        }
                    if (c >= 0) {
                        if (a.isFunction(a.fn.datepicker)) try {
                            a("input.hasDatepicker", "#" + a.jgrid.jqID(d.id)).datepicker("hide")
                        } catch (i) {}
                        a.each(g.p.colModel, function() {
                            this.editable === !0 && g.p.savedRow[c].hasOwnProperty(this.name) && (h[this.name] = g.p.savedRow[c][this.name])
                        }), a(g).jqGrid("setRowData", b, h), a(d).attr("editable", "0").unbind("keydown"), g.p.savedRow.splice(c, 1), a("#" + a.jgrid.jqID(b), "#" + a.jgrid.jqID(g.p.id)).hasClass("jqgrid-new-row") && setTimeout(function() {
                            a(g).jqGrid("delRowData", b), a(g).jqGrid("showAddEditButtons")
                        }, 0)
                    }
                    a(g).triggerHandler("jqGridInlineAfterRestoreRow", [b]), a.isFunction(e.afterrestorefunc) && e.afterrestorefunc.call(g, b)
                }
            })
        },
        "addRow": function(b) {
            return b = a.extend(!0, {
                "rowID": null,
                "initdata": {},
                "position": "first",
                "useDefValues": !0,
                "useFormatter": !1,
                "addRowParams": {
                    "extraparam": {}
                }
            }, b || {}), this.each(function() {
                if (this.grid) {
                    var c = this,
                        d = a.isFunction(b.beforeAddRow) ? b.beforeAddRow.call(c, b.addRowParams) : void 0;
                    void 0 === d && (d = !0), d && (b.rowID = a.isFunction(b.rowID) ? b.rowID.call(c, b) : null != b.rowID ? b.rowID : a.jgrid.randId(), !0 === b.useDefValues && a(c.p.colModel).each(function() {
                        if (this.editoptions && this.editoptions.defaultValue) {
                            var d = this.editoptions.defaultValue,
                                d = a.isFunction(d) ? d.call(c) : d;
                            b.initdata[this.name] = d
                        }
                    }), a(c).jqGrid("addRowData", b.rowID, b.initdata, b.position), b.rowID = c.p.idPrefix + b.rowID, a("#" + a.jgrid.jqID(b.rowID), "#" + a.jgrid.jqID(c.p.id)).addClass("jqgrid-new-row"), b.useFormatter ? a("#" + a.jgrid.jqID(b.rowID) + " .ui-inline-edit", "#" + a.jgrid.jqID(c.p.id)).click() : (d = c.p.prmNames, b.addRowParams.extraparam[d.oper] = d.addoper, a(c).jqGrid("editRow", b.rowID, b.addRowParams), a(c).jqGrid("setSelection", b.rowID)))
                }
            })
        },
        "inlineNav": function(b, c) {
            return c = a.extend(!0, {
                "edit": !0,
                "editicon": "ui-icon-pencil",
                "add": !0,
                "addicon": "ui-icon-plus",
                "save": !0,
                "saveicon": "ui-icon-disk",
                "cancel": !0,
                "cancelicon": "ui-icon-cancel",
                "addParams": {
                    "addRowParams": {
                        "extraparam": {}
                    }
                },
                "editParams": {},
                "restoreAfterSelect": !0
            }, a.jgrid.nav, c || {}), this.each(function() {
                if (this.grid) {
                    var d, e = this,
                        f = a.jgrid.jqID(e.p.id);
                    if (e.p._inlinenav = !0, !0 === c.addParams.useFormatter) {
                        var g, h = e.p.colModel;
                        for (g = 0; g < h.length; g++)
                            if (h[g].formatter && "actions" === h[g].formatter) {
                                h[g].formatoptions && (h = a.extend({
                                    "keys": !1,
                                    "onEdit": null,
                                    "onSuccess": null,
                                    "afterSave": null,
                                    "onError": null,
                                    "afterRestore": null,
                                    "extraparam": {},
                                    "url": null
                                }, h[g].formatoptions), c.addParams.addRowParams = {
                                    "keys": h.keys,
                                    "oneditfunc": h.onEdit,
                                    "successfunc": h.onSuccess,
                                    "url": h.url,
                                    "extraparam": h.extraparam,
                                    "aftersavefunc": h.afterSave,
                                    "errorfunc": h.onError,
                                    "afterrestorefunc": h.afterRestore
                                });
                                break
                            }
                    }
                    c.add && a(e).jqGrid("navButtonAdd", b, {
                        "caption": c.addtext,
                        "title": c.addtitle,
                        "buttonicon": c.addicon,
                        "id": e.p.id + "_iladd",
                        "onClickButton": function() {
                            a(e).jqGrid("addRow", c.addParams), c.addParams.useFormatter || (a("#" + f + "_ilsave").removeClass("ui-state-disabled"), a("#" + f + "_ilcancel").removeClass("ui-state-disabled"), a("#" + f + "_iladd").addClass("ui-state-disabled"), a("#" + f + "_iledit").addClass("ui-state-disabled"))
                        }
                    }), c.edit && a(e).jqGrid("navButtonAdd", b, {
                        "caption": c.edittext,
                        "title": c.edittitle,
                        "buttonicon": c.editicon,
                        "id": e.p.id + "_iledit",
                        "onClickButton": function() {
                            var b = a(e).jqGrid("getGridParam", "selrow");
                            b ? (a(e).jqGrid("editRow", b, c.editParams), a("#" + f + "_ilsave").removeClass("ui-state-disabled"), a("#" + f + "_ilcancel").removeClass("ui-state-disabled"), a("#" + f + "_iladd").addClass("ui-state-disabled"), a("#" + f + "_iledit").addClass("ui-state-disabled")) : (a.jgrid.viewModal("#alertmod", {
                                "gbox": "#gbox_" + f,
                                "jqm": !0
                            }), a("#jqg_alrt").focus())
                        }
                    }), c.save && (a(e).jqGrid("navButtonAdd", b, {
                        "caption": c.savetext || "",
                        "title": c.savetitle || "Save row",
                        "buttonicon": c.saveicon,
                        "id": e.p.id + "_ilsave",
                        "onClickButton": function() {
                            var b = e.p.savedRow[0].id;
                            if (b) {
                                var d = e.p.prmNames,
                                    g = d.oper,
                                    h = {};
                                a("#" + a.jgrid.jqID(b), "#" + f).hasClass("jqgrid-new-row") ? (c.addParams.addRowParams.extraparam[g] = d.addoper, h = c.addParams.addRowParams) : (c.editParams.extraparam || (c.editParams.extraparam = {}), c.editParams.extraparam[g] = d.editoper, h = c.editParams), a(e).jqGrid("saveRow", b, h) && a(e).jqGrid("showAddEditButtons")
                            } else a.jgrid.viewModal("#alertmod", {
                                "gbox": "#gbox_" + f,
                                "jqm": !0
                            }), a("#jqg_alrt").focus()
                        }
                    }), a("#" + f + "_ilsave").addClass("ui-state-disabled")), c.cancel && (a(e).jqGrid("navButtonAdd", b, {
                        "caption": c.canceltext || "",
                        "title": c.canceltitle || "Cancel row editing",
                        "buttonicon": c.cancelicon,
                        "id": e.p.id + "_ilcancel",
                        "onClickButton": function() {
                            var b = e.p.savedRow[0].id,
                                d = {};
                            b ? (d = a("#" + a.jgrid.jqID(b), "#" + f).hasClass("jqgrid-new-row") ? c.addParams.addRowParams : c.editParams, a(e).jqGrid("restoreRow", b, d), a(e).jqGrid("showAddEditButtons")) : (a.jgrid.viewModal("#alertmod", {
                                "gbox": "#gbox_" + f,
                                "jqm": !0
                            }), a("#jqg_alrt").focus())
                        }
                    }), a("#" + f + "_ilcancel").addClass("ui-state-disabled")), !0 === c.restoreAfterSelect && (d = a.isFunction(e.p.beforeSelectRow) ? e.p.beforeSelectRow : !1, e.p.beforeSelectRow = function(b, f) {
                        var g = !0;
                        return e.p.savedRow.length > 0 && e.p._inlinenav === !0 && b !== e.p.selrow && null !== e.p.selrow && (e.p.selrow === c.addParams.rowID ? a(e).jqGrid("delRowData", e.p.selrow) : a(e).jqGrid("restoreRow", e.p.selrow, c.editParams), a(e).jqGrid("showAddEditButtons")), d && (g = d.call(e, b, f)), g
                    })
                }
            })
        },
        "showAddEditButtons": function() {
            return this.each(function() {
                if (this.grid) {
                    var b = a.jgrid.jqID(this.p.id);
                    a("#" + b + "_ilsave").addClass("ui-state-disabled"), a("#" + b + "_ilcancel").addClass("ui-state-disabled"), a("#" + b + "_iladd").removeClass("ui-state-disabled"), a("#" + b + "_iledit").removeClass("ui-state-disabled")
                }
            })
        }
    })
}(jQuery),
function(a) {
    a.jgrid.extend({
        "editCell": function(b, c, d) {
            return this.each(function() {
                var e, f, g, h, i = this;
                if (i.grid && !0 === i.p.cellEdit) {
                    if (c = parseInt(c, 10), i.p.selrow = i.rows[b].id, i.p.knv || a(i).jqGrid("GridNav"), 0 < i.p.savedRow.length) {
                        if (!0 === d && b == i.p.iRow && c == i.p.iCol) return;
                        a(i).jqGrid("saveCell", i.p.savedRow[0].id, i.p.savedRow[0].ic)
                    } else window.setTimeout(function() {
                        a("#" + a.jgrid.jqID(i.p.knv)).attr("tabindex", "-1").focus()
                    }, 0);
                    if (h = i.p.colModel[c], e = h.name, "subgrid" !== e && "cb" !== e && "rn" !== e) {
                        if (g = a("td:eq(" + c + ")", i.rows[b]), !0 !== h.editable || !0 !== d || g.hasClass("not-editable-cell")) 0 <= parseInt(i.p.iCol, 10) && 0 <= parseInt(i.p.iRow, 10) && (a("td:eq(" + i.p.iCol + ")", i.rows[i.p.iRow]).removeClass("edit-cell ui-state-highlight"), a(i.rows[i.p.iRow]).removeClass("selected-row ui-state-hover")), g.addClass("edit-cell ui-state-highlight"), a(i.rows[b]).addClass("selected-row ui-state-hover"), f = g.html().replace(/\&#160\;/gi, ""), a(i).triggerHandler("jqGridSelectCell", [i.rows[b].id, e, f, b, c]), a.isFunction(i.p.onSelectCell) && i.p.onSelectCell.call(i, i.rows[b].id, e, f, b, c);
                        else {
                            0 <= parseInt(i.p.iCol, 10) && 0 <= parseInt(i.p.iRow, 10) && (a("td:eq(" + i.p.iCol + ")", i.rows[i.p.iRow]).removeClass("edit-cell ui-state-highlight"), a(i.rows[i.p.iRow]).removeClass("selected-row ui-state-hover")), a(g).addClass("edit-cell ui-state-highlight"), a(i.rows[b]).addClass("selected-row ui-state-hover");
                            try {
                                f = a.unformat.call(i, g, {
                                    "rowId": i.rows[b].id,
                                    "colModel": h
                                }, c)
                            } catch (j) {
                                f = h.edittype && "textarea" === h.edittype ? a(g).text() : a(g).html()
                            }
                            if (i.p.autoencode && (f = a.jgrid.htmlDecode(f)), h.edittype || (h.edittype = "text"), i.p.savedRow.push({
                                    "id": b,
                                    "ic": c,
                                    "name": e,
                                    "v": f
                                }), ("&nbsp;" === f || "&#160;" === f || 1 === f.length && 160 === f.charCodeAt(0)) && (f = ""), a.isFunction(i.p.formatCell)) {
                                var k = i.p.formatCell.call(i, i.rows[b].id, e, f, b, c);
                                void 0 !== k && (f = k)
                            }
                            a(i).triggerHandler("jqGridBeforeEditCell", [i.rows[b].id, e, f, b, c]), a.isFunction(i.p.beforeEditCell) && i.p.beforeEditCell.call(i, i.rows[b].id, e, f, b, c);
                            var k = a.extend({}, h.editoptions || {}, {
                                    "id": b + "_" + e,
                                    "name": e
                                }),
                                l = a.jgrid.createEl.call(i, h.edittype, k, f, !0, a.extend({}, a.jgrid.ajaxOptions, i.p.ajaxSelectOptions || {}));
                            a(g).html("").append(l).attr("tabindex", "0"), a.jgrid.bindEv.call(i, l, k), window.setTimeout(function() {
                                a(l).focus()
                            }, 0), a("input, select, textarea", g).bind("keydown", function(d) {
                                if (27 === d.keyCode && (a("input.hasDatepicker", g).length > 0 ? a(".ui-datepicker").is(":hidden") ? a(i).jqGrid("restoreCell", b, c) : a("input.hasDatepicker", g).datepicker("hide") : a(i).jqGrid("restoreCell", b, c)), 13 === d.keyCode) return a(i).jqGrid("saveCell", b, c), !1;
                                if (9 === d.keyCode) {
                                    if (i.grid.hDiv.loading) return !1;
                                    d.shiftKey ? a(i).jqGrid("prevCell", b, c) : a(i).jqGrid("nextCell", b, c)
                                }
                                d.stopPropagation()
                            }), a(i).triggerHandler("jqGridAfterEditCell", [i.rows[b].id, e, f, b, c]), a.isFunction(i.p.afterEditCell) && i.p.afterEditCell.call(i, i.rows[b].id, e, f, b, c)
                        }
                        i.p.iCol = c, i.p.iRow = b
                    }
                }
            })
        },
        "saveCell": function(b, c) {
            return this.each(function() {
                var d, e = this;
                if (e.grid && !0 === e.p.cellEdit) {
                    if (d = 1 <= e.p.savedRow.length ? 0 : null, null !== d) {
                        var f, g, h = a("td:eq(" + c + ")", e.rows[b]),
                            i = e.p.colModel[c],
                            j = i.name,
                            k = a.jgrid.jqID(j);
                        switch (i.edittype) {
                            case "select":
                                if (i.editoptions.multiple) {
                                    var k = a("#" + b + "_" + k, e.rows[b]),
                                        l = [];
                                    (f = a(k).val()) ? f.join(","): f = "", a("option:selected", k).each(function(b, c) {
                                        l[b] = a(c).text()
                                    }), g = l.join(",")
                                } else f = a("#" + b + "_" + k + " option:selected", e.rows[b]).val(), g = a("#" + b + "_" + k + " option:selected", e.rows[b]).text();
                                i.formatter && (g = f);
                                break;
                            case "checkbox":
                                var m = ["Yes", "No"];
                                i.editoptions && (m = i.editoptions.value.split(":")), g = f = a("#" + b + "_" + k, e.rows[b]).is(":checked") ? m[0] : m[1];
                                break;
                            case "password":
                            case "text":
                            case "textarea":
                            case "button":
                                g = f = a("#" + b + "_" + k, e.rows[b]).val();
                                break;
                            case "custom":
                                try {
                                    if (!i.editoptions || !a.isFunction(i.editoptions.custom_value)) throw "e1";
                                    if (f = i.editoptions.custom_value.call(e, a(".customelement", h), "get"), void 0 === f) throw "e2";
                                    g = f
                                } catch (n) {
                                    "e1" === n && a.jgrid.info_dialog(a.jgrid.errors.errcap, "function 'custom_value' " + a.jgrid.edit.msg.nodefined, a.jgrid.edit.bClose), "e2" === n ? a.jgrid.info_dialog(a.jgrid.errors.errcap, "function 'custom_value' " + a.jgrid.edit.msg.novalue, a.jgrid.edit.bClose) : a.jgrid.info_dialog(a.jgrid.errors.errcap, n.message, a.jgrid.edit.bClose)
                                }
                        }
                        if (g !== e.p.savedRow[d].v) {
                            (d = a(e).triggerHandler("jqGridBeforeSaveCell", [e.rows[b].id, j, f, b, c])) && (g = f = d), a.isFunction(e.p.beforeSaveCell) && (d = e.p.beforeSaveCell.call(e, e.rows[b].id, j, f, b, c)) && (g = f = d);
                            var o = a.jgrid.checkValues.call(e, f, c);
                            if (!0 === o[0]) {
                                if (d = a(e).triggerHandler("jqGridBeforeSubmitCell", [e.rows[b].id, j, f, b, c]) || {}, a.isFunction(e.p.beforeSubmitCell) && ((d = e.p.beforeSubmitCell.call(e, e.rows[b].id, j, f, b, c)) || (d = {})), 0 < a("input.hasDatepicker", h).length && a("input.hasDatepicker", h).datepicker("hide"), "remote" === e.p.cellsubmit)
                                    if (e.p.cellurl) {
                                        var p = {};
                                        e.p.autoencode && (f = a.jgrid.htmlEncode(f)), p[j] = f, m = e.p.prmNames, i = m.id, k = m.oper, p[i] = a.jgrid.stripPref(e.p.idPrefix, e.rows[b].id), p[k] = m.editoper, p = a.extend(d, p), a("#lui_" + a.jgrid.jqID(e.p.id)).show(), e.grid.hDiv.loading = !0, a.ajax(a.extend({
                                            "url": e.p.cellurl,
                                            "data": a.isFunction(e.p.serializeCellData) ? e.p.serializeCellData.call(e, p) : p,
                                            "type": "POST",
                                            "complete": function(d, i) {
                                                if (a("#lui_" + e.p.id).hide(), e.grid.hDiv.loading = !1, "success" === i) {
                                                    var k = a(e).triggerHandler("jqGridAfterSubmitCell", [e, d, p.id, j, f, b, c]) || [!0, ""];
                                                    k[0] === !0 && a.isFunction(e.p.afterSubmitCell) && (k = e.p.afterSubmitCell.call(e, d, p.id, j, f, b, c)), k[0] === !0 ? (a(h).empty(), a(e).jqGrid("setCell", e.rows[b].id, c, g, !1, !1, !0), a(h).addClass("dirty-cell"), a(e.rows[b]).addClass("edited"), a(e).triggerHandler("jqGridAfterSaveCell", [e.rows[b].id, j, f, b, c]), a.isFunction(e.p.afterSaveCell) && e.p.afterSaveCell.call(e, e.rows[b].id, j, f, b, c), e.p.savedRow.splice(0, 1)) : (a.jgrid.info_dialog(a.jgrid.errors.errcap, k[1], a.jgrid.edit.bClose), a(e).jqGrid("restoreCell", b, c))
                                                }
                                            },
                                            "error": function(d, f, g) {
                                                a("#lui_" + a.jgrid.jqID(e.p.id)).hide(), e.grid.hDiv.loading = !1, a(e).triggerHandler("jqGridErrorCell", [d, f, g]), a.isFunction(e.p.errorCell) ? e.p.errorCell.call(e, d, f, g) : a.jgrid.info_dialog(a.jgrid.errors.errcap, d.status + " : " + d.statusText + "<br/>" + f, a.jgrid.edit.bClose), a(e).jqGrid("restoreCell", b, c)
                                            }
                                        }, a.jgrid.ajaxOptions, e.p.ajaxCellOptions || {}))
                                    } else try {
                                        a.jgrid.info_dialog(a.jgrid.errors.errcap, a.jgrid.errors.nourl, a.jgrid.edit.bClose), a(e).jqGrid("restoreCell", b, c)
                                    } catch (q) {}
                                    "clientArray" === e.p.cellsubmit && (a(h).empty(), a(e).jqGrid("setCell", e.rows[b].id, c, g, !1, !1, !0), a(h).addClass("dirty-cell"), a(e.rows[b]).addClass("edited"), a(e).triggerHandler("jqGridAfterSaveCell", [e.rows[b].id, j, f, b, c]), a.isFunction(e.p.afterSaveCell) && e.p.afterSaveCell.call(e, e.rows[b].id, j, f, b, c), e.p.savedRow.splice(0, 1))
                            } else try {
                                window.setTimeout(function() {
                                    a.jgrid.info_dialog(a.jgrid.errors.errcap, f + " " + o[1], a.jgrid.edit.bClose)
                                }, 100), a(e).jqGrid("restoreCell", b, c)
                            } catch (r) {}
                        } else a(e).jqGrid("restoreCell", b, c)
                    }
                    window.setTimeout(function() {
                        a("#" + a.jgrid.jqID(e.p.knv)).attr("tabindex", "-1").focus()
                    }, 0)
                }
            })
        },
        "restoreCell": function(b, c) {
            return this.each(function() {
                var d, e = this;
                if (e.grid && !0 === e.p.cellEdit) {
                    if (d = 1 <= e.p.savedRow.length ? 0 : null, null !== d) {
                        var f = a("td:eq(" + c + ")", e.rows[b]);
                        if (a.isFunction(a.fn.datepicker)) try {
                            a("input.hasDatepicker", f).datepicker("hide")
                        } catch (g) {}
                        a(f).empty().attr("tabindex", "-1"), a(e).jqGrid("setCell", e.rows[b].id, c, e.p.savedRow[d].v, !1, !1, !0), a(e).triggerHandler("jqGridAfterRestoreCell", [e.rows[b].id, e.p.savedRow[d].v, b, c]), a.isFunction(e.p.afterRestoreCell) && e.p.afterRestoreCell.call(e, e.rows[b].id, e.p.savedRow[d].v, b, c), e.p.savedRow.splice(0, 1)
                    }
                    window.setTimeout(function() {
                        a("#" + e.p.knv).attr("tabindex", "-1").focus()
                    }, 0)
                }
            })
        },
        "nextCell": function(b, c) {
            return this.each(function() {
                var d, e = !1;
                if (this.grid && !0 === this.p.cellEdit) {
                    for (d = c + 1; d < this.p.colModel.length; d++)
                        if (!0 === this.p.colModel[d].editable) {
                            e = d;
                            break
                        }!1 !== e ? a(this).jqGrid("editCell", b, e, !0) : 0 < this.p.savedRow.length && a(this).jqGrid("saveCell", b, c)
                }
            })
        },
        "prevCell": function(b, c) {
            return this.each(function() {
                var d, e = !1;
                if (this.grid && !0 === this.p.cellEdit) {
                    for (d = c - 1; d >= 0; d--)
                        if (!0 === this.p.colModel[d].editable) {
                            e = d;
                            break
                        }!1 !== e ? a(this).jqGrid("editCell", b, e, !0) : 0 < this.p.savedRow.length && a(this).jqGrid("saveCell", b, c)
                }
            })
        },
        "GridNav": function() {
            return this.each(function() {
                function b(b, c, e) {
                    if ("v" === e.substr(0, 1)) {
                        var f = a(d.grid.bDiv)[0].clientHeight,
                            g = a(d.grid.bDiv)[0].scrollTop,
                            h = d.rows[b].offsetTop + d.rows[b].clientHeight,
                            i = d.rows[b].offsetTop;
                        "vd" === e && h >= f && (a(d.grid.bDiv)[0].scrollTop = a(d.grid.bDiv)[0].scrollTop + d.rows[b].clientHeight), "vu" === e && g > i && (a(d.grid.bDiv)[0].scrollTop = a(d.grid.bDiv)[0].scrollTop - d.rows[b].clientHeight)
                    }
                    "h" === e && (e = a(d.grid.bDiv)[0].clientWidth, f = a(d.grid.bDiv)[0].scrollLeft, g = d.rows[b].cells[c].offsetLeft, d.rows[b].cells[c].offsetLeft + d.rows[b].cells[c].clientWidth >= e + parseInt(f, 10) ? a(d.grid.bDiv)[0].scrollLeft = a(d.grid.bDiv)[0].scrollLeft + d.rows[b].cells[c].clientWidth : f > g && (a(d.grid.bDiv)[0].scrollLeft = a(d.grid.bDiv)[0].scrollLeft - d.rows[b].cells[c].clientWidth))
                }

                function c(a, b) {
                    var c, e;
                    if ("lft" === b)
                        for (c = a + 1, e = a; e >= 0; e--)
                            if (!0 !== d.p.colModel[e].hidden) {
                                c = e;
                                break
                            }
                    if ("rgt" === b)
                        for (c = a - 1, e = a; e < d.p.colModel.length; e++)
                            if (!0 !== d.p.colModel[e].hidden) {
                                c = e;
                                break
                            }
                    return c
                }
                var d = this;
                if (d.grid && !0 === d.p.cellEdit) {
                    d.p.knv = d.p.id + "_kn";
                    var e, f, g = a("<div style='position:fixed;top:0px;width:1px;height:1px;' tabindex='0'><div tabindex='-1' style='width:1px;height:1px;' id='" + d.p.knv + "'></div></div>");
                    a(g).insertBefore(d.grid.cDiv), a("#" + d.p.knv).focus().keydown(function(g) {
                        switch (f = g.keyCode, "rtl" === d.p.direction && (37 === f ? f = 39 : 39 === f && (f = 37)), f) {
                            case 38:
                                0 < d.p.iRow - 1 && (b(d.p.iRow - 1, d.p.iCol, "vu"), a(d).jqGrid("editCell", d.p.iRow - 1, d.p.iCol, !1));
                                break;
                            case 40:
                                d.p.iRow + 1 <= d.rows.length - 1 && (b(d.p.iRow + 1, d.p.iCol, "vd"), a(d).jqGrid("editCell", d.p.iRow + 1, d.p.iCol, !1));
                                break;
                            case 37:
                                0 <= d.p.iCol - 1 && (e = c(d.p.iCol - 1, "lft"), b(d.p.iRow, e, "h"), a(d).jqGrid("editCell", d.p.iRow, e, !1));
                                break;
                            case 39:
                                d.p.iCol + 1 <= d.p.colModel.length - 1 && (e = c(d.p.iCol + 1, "rgt"), b(d.p.iRow, e, "h"), a(d).jqGrid("editCell", d.p.iRow, e, !1));
                                break;
                            case 13:
                                0 <= parseInt(d.p.iCol, 10) && 0 <= parseInt(d.p.iRow, 10) && a(d).jqGrid("editCell", d.p.iRow, d.p.iCol, !0);
                                break;
                            default:
                                return !0
                        }
                        return !1
                    })
                }
            })
        },
        "getChangedCells": function(b) {
            var c = [];
            return b || (b = "all"), this.each(function() {
                var d, e = this;
                e.grid && !0 === e.p.cellEdit && a(e.rows).each(function(f) {
                    var g = {};
                    a(this).hasClass("edited") && (a("td", this).each(function(c) {
                        if (d = e.p.colModel[c].name, "cb" !== d && "subgrid" !== d)
                            if ("dirty" === b) {
                                if (a(this).hasClass("dirty-cell")) try {
                                    g[d] = a.unformat.call(e, this, {
                                        "rowId": e.rows[f].id,
                                        "colModel": e.p.colModel[c]
                                    }, c)
                                } catch (h) {
                                    g[d] = a.jgrid.htmlDecode(a(this).html())
                                }
                            } else try {
                                g[d] = a.unformat.call(e, this, {
                                    "rowId": e.rows[f].id,
                                    "colModel": e.p.colModel[c]
                                }, c)
                            } catch (i) {
                                g[d] = a.jgrid.htmlDecode(a(this).html())
                            }
                    }), g.id = this.id, c.push(g))
                })
            }), c
        }
    })
}(jQuery),
function(a) {
    a.fn.jqm = function(d) {
        var f = {
            "overlay": 50,
            "closeoverlay": !0,
            "overlayClass": "jqmOverlay",
            "closeClass": "jqmClose",
            "trigger": ".jqModal",
            "ajax": e,
            "ajaxText": "",
            "target": e,
            "modal": e,
            "toTop": e,
            "onShow": e,
            "onHide": e,
            "onLoad": e
        };
        return this.each(function() {
            return this._jqm ? c[this._jqm].c = a.extend({}, c[this._jqm].c, d) : (b++, this._jqm = b, c[b] = {
                "c": a.extend(f, a.jqm.params, d),
                "a": e,
                "w": a(this).addClass("jqmID" + b),
                "s": b
            }, void(f.trigger && a(this).jqmAddTrigger(f.trigger)))
        })
    }, a.fn.jqmAddClose = function(a) {
        return i(this, a, "jqmHide")
    }, a.fn.jqmAddTrigger = function(a) {
        return i(this, a, "jqmShow")
    }, a.fn.jqmShow = function(b) {
        return this.each(function() {
            a.jqm.open(this._jqm, b)
        })
    }, a.fn.jqmHide = function(b) {
        return this.each(function() {
            a.jqm.close(this._jqm, b)
        })
    }, a.jqm = {
        "hash": {},
        "open": function(b, h) {
            var i = c[b],
                j = i.c,
                k = "." + j.closeClass,
                l = parseInt(i.w.css("z-index")),
                l = l > 0 ? l : 3e3,
                m = a("<div></div>").css({
                    "height": "100%",
                    "width": "100%",
                    "position": "fixed",
                    "left": 0,
                    "top": 0,
                    "z-index": l - 1,
                    "opacity": j.overlay / 100
                });
            return i.a ? e : (i.t = h, i.a = !0, i.w.css("z-index", l), j.modal ? (d[0] || setTimeout(function() {
                g("bind")
            }, 1), d.push(b)) : 0 < j.overlay ? j.closeoverlay && i.w.jqmAddClose(m) : m = e, i.o = m ? m.addClass(j.overlayClass).prependTo("body") : e, j.ajax ? (l = j.target || i.w, m = j.ajax, l = "string" == typeof l ? a(l, i.w) : a(l), m = "@" == m.substr(0, 1) ? a(h).attr(m.substring(1)) : m, l.html(j.ajaxText).load(m, function() {
                j.onLoad && j.onLoad.call(this, i), k && i.w.jqmAddClose(a(k, i.w)), f(i)
            })) : k && i.w.jqmAddClose(a(k, i.w)), j.toTop && i.o && i.w.before('<span id="jqmP' + i.w[0]._jqm + '"></span>').insertAfter(i.o), j.onShow ? j.onShow(i) : i.w.show(), f(i), e)
        },
        "close": function(b) {
            return b = c[b], b.a ? (b.a = e, d[0] && (d.pop(), d[0] || g("unbind")), b.c.toTop && b.o && a("#jqmP" + b.w[0]._jqm).after(b.w).remove(), b.c.onHide ? b.c.onHide(b) : (b.w.hide(), b.o && b.o.remove()), e) : e
        },
        "params": {}
    };
    var b = 0,
        c = a.jqm.hash,
        d = [],
        e = !1,
        f = function(b) {
            try {
                a(":input:visible", b.w)[0].focus()
            } catch (c) {}
        },
        g = function(b) {
            a(document)[b]("keypress", h)[b]("keydown", h)[b]("mousedown", h)
        },
        h = function(b) {
            var e = c[d[d.length - 1]],
                g = !a(b.target).parents(".jqmID" + e.s)[0];
            return g && (a(".jqmID" + e.s).each(function() {
                var c = a(this),
                    d = c.offset();
                return d.top <= b.pageY && b.pageY <= d.top + c.height() && d.left <= b.pageX && b.pageX <= d.left + c.width() ? g = !1 : void 0
            }), f(e)), !g
        },
        i = function(b, d, f) {
            return b.each(function() {
                var b = this._jqm;
                a(d).each(function() {
                    this[f] || (this[f] = [], a(this).click(function() {
                        for (var a in {
                                "jqmShow": 1,
                                "jqmHide": 1
                            })
                            for (var b in this[a]) c[this[a][b]] && c[this[a][b]].w[a](this);
                        return e
                    })), this[f].push(b)
                })
            })
        }
}(jQuery),
function(a) {
    a.fn.jqDrag = function(a) {
        return g(this, a, "d")
    }, a.fn.jqResize = function(a, b) {
        return g(this, a, "r", b)
    }, a.jqDnR = {
        "dnr": {},
        "e": 0,
        "drag": function(a) {
            return "d" == e.k ? f.css({
                "left": e.X + a.pageX - e.pX,
                "top": e.Y + a.pageY - e.pY
            }) : (f.css({
                "width": Math.max(a.pageX - e.pX + e.W, 0),
                "height": Math.max(a.pageY - e.pY + e.H, 0)
            }), c && b.css({
                "width": Math.max(a.pageX - c.pX + c.W, 0),
                "height": Math.max(a.pageY - c.pY + c.H, 0)
            })), !1
        },
        "stop": function() {
            a(document).unbind("mousemove", d.drag).unbind("mouseup", d.stop)
        }
    };
    var b, c, d = a.jqDnR,
        e = d.dnr,
        f = d.e,
        g = function(d, g, j, k) {
            return d.each(function() {
                g = g ? a(g, d) : d, g.bind("mousedown", {
                    "e": d,
                    "k": j
                }, function(d) {
                    var g = d.data,
                        j = {};
                    if (f = g.e, b = k ? a(k) : !1, "relative" != f.css("position")) try {
                        f.position(j)
                    } catch (l) {}
                    if (e = {
                            "X": j.left || h("left") || 0,
                            "Y": j.top || h("top") || 0,
                            "W": h("width") || f[0].scrollWidth || 0,
                            "H": h("height") || f[0].scrollHeight || 0,
                            "pX": d.pageX,
                            "pY": d.pageY,
                            "k": g.k
                        }, c = b && "d" != g.k ? {
                            "X": j.left || i("left") || 0,
                            "Y": j.top || i("top") || 0,
                            "W": b[0].offsetWidth || i("width") || 0,
                            "H": b[0].offsetHeight || i("height") || 0,
                            "pX": d.pageX,
                            "pY": d.pageY,
                            "k": g.k
                        } : !1, a("input.hasDatepicker", f[0])[0]) try {
                        a("input.hasDatepicker", f[0]).datepicker("hide")
                    } catch (m) {}
                    return a(document).mousemove(a.jqDnR.drag).mouseup(a.jqDnR.stop), !1
                })
            })
        },
        h = function(a) {
            return parseInt(f.css(a), 10) || !1
        },
        i = function(a) {
            return parseInt(b.css(a), 10) || !1
        }
}(jQuery),
function(a) {
    a.jgrid.extend({
        "setSubGrid": function() {
            return this.each(function() {
                var b, c;
                if (this.p.subGridOptions = a.extend({
                        "plusicon": "ui-icon-plus",
                        "minusicon": "ui-icon-minus",
                        "openicon": "ui-icon-carat-1-sw",
                        "expandOnLoad": !1,
                        "delayOnLoad": 50,
                        "selectOnExpand": !1,
                        "selectOnCollapse": !1,
                        "reloadOnExpand": !0
                    }, this.p.subGridOptions || {}), this.p.colNames.unshift(""), this.p.colModel.unshift({
                        "name": "subgrid",
                        "width": a.jgrid.cell_width ? this.p.subGridWidth + this.p.cellLayout : this.p.subGridWidth,
                        "sortable": !1,
                        "resizable": !1,
                        "hidedlg": !0,
                        "search": !1,
                        "fixed": !0
                    }), b = this.p.subGridModel, b[0])
                    for (b[0].align = a.extend([], b[0].align || []), c = 0; c < b[0].name.length; c++) b[0].align[c] = b[0].align[c] || "left"
            })
        },
        "addSubGridCell": function(a, b) {
            var c, d, e = "";
            return this.each(function() {
                e = this.formatCol(a, b), d = this.p.id, c = this.p.subGridOptions.plusicon
            }), '<td role="gridcell" aria-describedby="' + d + '_subgrid" class="ui-sgcollapsed sgcollapsed" ' + e + "><a style='cursor:pointer;'><span class='ui-icon " + c + "'></span></a></td>"
        },
        "addSubGrid": function(b, c) {
            return this.each(function() {
                var d = this;
                if (d.grid) {
                    var e, f, g, h, i, j = function(b, c, e) {
                            c = a("<td align='" + d.p.subGridModel[0].align[e] + "'></td>").html(c), a(b).append(c)
                        },
                        k = function(b, c) {
                            var e, f, g, h = a("<table cellspacing='0' cellpadding='0' border='0'><tbody></tbody></table>"),
                                i = a("<tr></tr>");
                            for (f = 0; f < d.p.subGridModel[0].name.length; f++) e = a("<th class='ui-state-default ui-th-subgrid ui-th-column ui-th-" + d.p.direction + "'></th>"), a(e).html(d.p.subGridModel[0].name[f]), a(e).width(d.p.subGridModel[0].width[f]), a(i).append(e);
                            return a(h).append(i), b && (g = d.p.xmlReader.subgrid, a(g.root + " " + g.row, b).each(function() {
                                if (i = a("<tr class='ui-widget-content ui-subtblcell'></tr>"), !0 === g.repeatitems) a(g.cell, this).each(function(b) {
                                    j(i, a(this).text() || "&#160;", b)
                                });
                                else {
                                    var b = d.p.subGridModel[0].mapping || d.p.subGridModel[0].name;
                                    if (b)
                                        for (f = 0; f < b.length; f++) j(i, a(b[f], this).text() || "&#160;", f)
                                }
                                a(h).append(i)
                            })), e = a("table:first", d.grid.bDiv).attr("id") + "_", a("#" + a.jgrid.jqID(e + c)).append(h), d.grid.hDiv.loading = !1, a("#load_" + a.jgrid.jqID(d.p.id)).hide(), !1
                        },
                        l = function(b, c) {
                            var e, f, g, h, i, k = a("<table cellspacing='0' cellpadding='0' border='0'><tbody></tbody></table>"),
                                l = a("<tr></tr>");
                            for (f = 0; f < d.p.subGridModel[0].name.length; f++) e = a("<th class='ui-state-default ui-th-subgrid ui-th-column ui-th-" + d.p.direction + "'></th>"), a(e).html(d.p.subGridModel[0].name[f]), a(e).width(d.p.subGridModel[0].width[f]), a(l).append(e);
                            if (a(k).append(l), b && (h = d.p.jsonReader.subgrid, e = a.jgrid.getAccessor(b, h.root), void 0 !== e))
                                for (f = 0; f < e.length; f++) {
                                    if (g = e[f], l = a("<tr class='ui-widget-content ui-subtblcell'></tr>"), !0 === h.repeatitems)
                                        for (h.cell && (g = g[h.cell]), i = 0; i < g.length; i++) j(l, g[i] || "&#160;", i);
                                    else {
                                        var m = d.p.subGridModel[0].mapping || d.p.subGridModel[0].name;
                                        if (m.length)
                                            for (i = 0; i < m.length; i++) j(l, g[m[i]] || "&#160;", i)
                                    }
                                    a(k).append(l)
                                }
                            return f = a("table:first", d.grid.bDiv).attr("id") + "_", a("#" + a.jgrid.jqID(f + c)).append(k), d.grid.hDiv.loading = !1, a("#load_" + a.jgrid.jqID(d.p.id)).hide(), !1
                        },
                        m = function(b) {
                            var c, e, f, g;
                            if (c = a(b).attr("id"), e = {
                                    "nd_": (new Date).getTime()
                                }, e[d.p.prmNames.subgridid] = c, !d.p.subGridModel[0]) return !1;
                            if (d.p.subGridModel[0].params)
                                for (g = 0; g < d.p.subGridModel[0].params.length; g++)
                                    for (f = 0; f < d.p.colModel.length; f++) d.p.colModel[f].name === d.p.subGridModel[0].params[g] && (e[d.p.colModel[f].name] = a("td:eq(" + f + ")", b).text().replace(/\&#160\;/gi, ""));
                            if (!d.grid.hDiv.loading) switch (d.grid.hDiv.loading = !0, a("#load_" + a.jgrid.jqID(d.p.id)).show(), d.p.subgridtype || (d.p.subgridtype = d.p.datatype), a.isFunction(d.p.subgridtype) ? d.p.subgridtype.call(d, e) : d.p.subgridtype = d.p.subgridtype.toLowerCase(), d.p.subgridtype) {
                                case "xml":
                                case "json":
                                    a.ajax(a.extend({
                                        "type": d.p.mtype,
                                        "url": d.p.subGridUrl,
                                        "dataType": d.p.subgridtype,
                                        "data": a.isFunction(d.p.serializeSubGridData) ? d.p.serializeSubGridData.call(d, e) : e,
                                        "complete": function(b) {
                                            "xml" === d.p.subgridtype ? k(b.responseXML, c) : l(a.jgrid.parse(b.responseText), c)
                                        }
                                    }, a.jgrid.ajaxOptions, d.p.ajaxSubgridOptions || {}))
                            }
                            return !1
                        },
                        n = 0;
                    a.each(d.p.colModel, function() {
                        (!0 === this.hidden || "rn" === this.name || "cb" === this.name) && n++
                    });
                    var o = d.rows.length,
                        p = 1;
                    for (void 0 !== c && c > 0 && (p = c, o = c + 1); o > p;) a(d.rows[p]).hasClass("jqgrow") && a(d.rows[p].cells[b]).bind("click", function() {
                        var c = a(this).parent("tr")[0];
                        if (i = c.nextSibling, a(this).hasClass("sgcollapsed")) {
                            if (f = d.p.id, e = c.id, d.p.subGridOptions.reloadOnExpand === !0 || d.p.subGridOptions.reloadOnExpand === !1 && !a(i).hasClass("ui-subgrid")) {
                                if (g = b >= 1 ? "<td colspan='" + b + "'>&#160;</td>" : "", h = a(d).triggerHandler("jqGridSubGridBeforeExpand", [f + "_" + e, e]), (h = h === !1 || "stop" === h ? !1 : !0) && a.isFunction(d.p.subGridBeforeExpand) && (h = d.p.subGridBeforeExpand.call(d, f + "_" + e, e)), h === !1) return !1;
                                a(c).after("<tr role='row' class='ui-subgrid'>" + g + "<td class='ui-widget-content subgrid-cell'><span class='ui-icon " + d.p.subGridOptions.openicon + "'></span></td><td colspan='" + parseInt(d.p.colNames.length - 1 - n, 10) + "' class='ui-widget-content subgrid-data'><div id=" + f + "_" + e + " class='tablediv'></div></td></tr>"), a(d).triggerHandler("jqGridSubGridRowExpanded", [f + "_" + e, e]), a.isFunction(d.p.subGridRowExpanded) ? d.p.subGridRowExpanded.call(d, f + "_" + e, e) : m(c)
                            } else a(i).show();
                            a(this).html("<a style='cursor:pointer;'><span class='ui-icon " + d.p.subGridOptions.minusicon + "'></span></a>").removeClass("sgcollapsed").addClass("sgexpanded"), d.p.subGridOptions.selectOnExpand && a(d).jqGrid("setSelection", e)
                        } else if (a(this).hasClass("sgexpanded")) {
                            if (h = a(d).triggerHandler("jqGridSubGridRowColapsed", [f + "_" + e, e]), h = h === !1 || "stop" === h ? !1 : !0, e = c.id, h && a.isFunction(d.p.subGridRowColapsed) && (h = d.p.subGridRowColapsed.call(d, f + "_" + e, e)), h === !1) return !1;
                            d.p.subGridOptions.reloadOnExpand === !0 ? a(i).remove(".ui-subgrid") : a(i).hasClass("ui-subgrid") && a(i).hide(), a(this).html("<a style='cursor:pointer;'><span class='ui-icon " + d.p.subGridOptions.plusicon + "'></span></a>").removeClass("sgexpanded").addClass("sgcollapsed"), d.p.subGridOptions.selectOnCollapse && a(d).jqGrid("setSelection", e)
                        }
                        return !1
                    }), p++;
                    !0 === d.p.subGridOptions.expandOnLoad && a(d.rows).filter(".jqgrow").each(function(b, c) {
                        a(c.cells[0]).click()
                    }), d.subGridXml = function(a, b) {
                        k(a, b)
                    }, d.subGridJson = function(a, b) {
                        l(a, b)
                    }
                }
            })
        },
        "expandSubGridRow": function(b) {
            return this.each(function() {
                if ((this.grid || b) && !0 === this.p.subGrid) {
                    var c = a(this).jqGrid("getInd", b, !0);
                    c && (c = a("td.sgcollapsed", c)[0]) && a(c).trigger("click")
                }
            })
        },
        "collapseSubGridRow": function(b) {
            return this.each(function() {
                if ((this.grid || b) && !0 === this.p.subGrid) {
                    var c = a(this).jqGrid("getInd", b, !0);
                    c && (c = a("td.sgexpanded", c)[0]) && a(c).trigger("click")
                }
            })
        },
        "toggleSubGridRow": function(b) {
            return this.each(function() {
                if ((this.grid || b) && !0 === this.p.subGrid) {
                    var c = a(this).jqGrid("getInd", b, !0);
                    if (c) {
                        var d = a("td.sgcollapsed", c)[0];
                        d ? a(d).trigger("click") : (d = a("td.sgexpanded", c)[0]) && a(d).trigger("click")
                    }
                }
            })
        }
    })
}(jQuery),
function(a) {
    a.extend(a.jgrid, {
        "template": function(b) {
            var c, d = a.makeArray(arguments).slice(1),
                e = d.length;
            return null == b && (b = ""), b.replace(/\{([\w\-]+)(?:\:([\w\.]*)(?:\((.*?)?\))?)?\}/g, function(b, f) {
                if (!isNaN(parseInt(f, 10))) return d[parseInt(f, 10)];
                for (c = 0; e > c; c++)
                    if (a.isArray(d[c]))
                        for (var g = d[c], h = g.length; h--;)
                            if (f === g[h].nm) return g[h].v
            })
        }
    }), a.jgrid.extend({
        "groupingSetup": function() {
            return this.each(function() {
                var b, c, d = this.p.colModel,
                    e = this.p.groupingView;
                if (null === e || "object" != typeof e && !a.isFunction(e)) this.p.grouping = !1;
                else if (e.groupField.length) {
                    for (void 0 === e.visibiltyOnNextGrouping && (e.visibiltyOnNextGrouping = []), e.lastvalues = [], e.groups = [], e.counters = [], b = 0; b < e.groupField.length; b++) e.groupOrder[b] || (e.groupOrder[b] = "asc"), e.groupText[b] || (e.groupText[b] = "{0}"), "boolean" != typeof e.groupColumnShow[b] && (e.groupColumnShow[b] = !0), "boolean" != typeof e.groupSummary[b] && (e.groupSummary[b] = !1), !0 === e.groupColumnShow[b] ? (e.visibiltyOnNextGrouping[b] = !0, a(this).jqGrid("showCol", e.groupField[b])) : (e.visibiltyOnNextGrouping[b] = a("#" + a.jgrid.jqID(this.p.id + "_" + e.groupField[b])).is(":visible"), a(this).jqGrid("hideCol", e.groupField[b]));
                    for (e.summary = [], b = 0, c = d.length; c > b; b++) d[b].summaryType && e.summary.push(d[b].summaryDivider ? {
                        "nm": d[b].name,
                        "st": d[b].summaryType,
                        "v": "",
                        "sd": d[b].summaryDivider,
                        "vd": "",
                        "sr": d[b].summaryRound,
                        "srt": d[b].summaryRoundType || "round"
                    } : {
                        "nm": d[b].name,
                        "st": d[b].summaryType,
                        "v": "",
                        "sr": d[b].summaryRound,
                        "srt": d[b].summaryRoundType || "round"
                    })
                } else this.p.grouping = !1
            })
        },
        "groupingPrepare": function(b, c, d, e) {
            return this.each(function() {
                var f, g, h, i, j = this.p.groupingView,
                    k = this,
                    l = j.groupField.length,
                    m = 0;
                for (f = 0; l > f; f++) g = j.groupField[f], i = j.displayField[f], h = d[g], i = null == i ? null : d[i], null == i && (i = h), void 0 !== h && (0 === e ? (j.groups.push({
                    "idx": f,
                    "dataIndex": g,
                    "value": h,
                    "displayValue": i,
                    "startRow": e,
                    "cnt": 1,
                    "summary": []
                }), j.lastvalues[f] = h, j.counters[f] = {
                    "cnt": 1,
                    "pos": j.groups.length - 1,
                    "summary": a.extend(!0, [], j.summary)
                }) : "object" == typeof h || (a.isArray(j.isInTheSameGroup) && a.isFunction(j.isInTheSameGroup[f]) ? j.isInTheSameGroup[f].call(k, j.lastvalues[f], h, f, j) : j.lastvalues[f] === h) ? 1 === m ? (j.groups.push({
                    "idx": f,
                    "dataIndex": g,
                    "value": h,
                    "displayValue": i,
                    "startRow": e,
                    "cnt": 1,
                    "summary": []
                }), j.lastvalues[f] = h, j.counters[f] = {
                    "cnt": 1,
                    "pos": j.groups.length - 1,
                    "summary": a.extend(!0, [], j.summary)
                }) : (j.counters[f].cnt += 1, j.groups[j.counters[f].pos].cnt = j.counters[f].cnt) : (j.groups.push({
                    "idx": f,
                    "dataIndex": g,
                    "value": h,
                    "displayValue": i,
                    "startRow": e,
                    "cnt": 1,
                    "summary": []
                }), j.lastvalues[f] = h, m = 1, j.counters[f] = {
                    "cnt": 1,
                    "pos": j.groups.length - 1,
                    "summary": a.extend(!0, [], j.summary)
                }), a.each(j.counters[f].summary, function() {
                    a.isFunction(this.st) ? this.v = this.st.call(k, this.v, this.nm, d) : (this.v = a(k).jqGrid("groupingCalculations.handler", this.st, this.v, this.nm, this.sr, this.srt, d), "avg" === this.st.toLowerCase() && this.sd && (this.vd = a(k).jqGrid("groupingCalculations.handler", this.st, this.vd, this.sd, this.sr, this.srt, d)))
                }), j.groups[j.counters[f].pos].summary = j.counters[f].summary);
                c.push(b)
            }), c
        },
        "groupingToggle": function(b) {
            return this.each(function() {
                var c = this.p.groupingView,
                    d = b.split("_"),
                    e = parseInt(d[d.length - 2], 10);
                d.splice(d.length - 2, 2);
                var f, g = d.join("_"),
                    d = c.minusicon,
                    h = c.plusicon,
                    i = a("#" + a.jgrid.jqID(b)),
                    i = i.length ? i[0].nextSibling : null,
                    j = a("#" + a.jgrid.jqID(b) + " span.tree-wrap-" + this.p.direction),
                    k = function(b) {
                        return b = a.map(b.split(" "), function(a) {
                            return a.substring(0, g.length + 1) === g + "_" ? parseInt(a.substring(g.length + 1), 10) : void 0
                        }), 0 < b.length ? b[0] : void 0
                    },
                    l = !1;
                if (j.hasClass(d)) {
                    if (c.showSummaryOnHide) {
                        if (i)
                            for (; i && !(a(i).hasClass("jqfoot") && parseInt(a(i).attr("jqfootlevel"), 10) <= e);) a(i).hide(), i = i.nextSibling
                    } else if (i)
                        for (; i && (c = k(i.className), !(void 0 !== c && e >= c));) a(i).hide(), i = i.nextSibling;
                    j.removeClass(d).addClass(h), l = !0
                } else {
                    if (i)
                        for (f = void 0; i;) {
                            if (c = k(i.className), void 0 === f && (f = void 0 === c), void 0 !== c) {
                                if (e >= c) break;
                                c === e + 1 && a(i).show().find(">td>span.tree-wrap-" + this.p.direction).removeClass(d).addClass(h)
                            } else f && a(i).show();
                            i = i.nextSibling
                        }
                    j.removeClass(h).addClass(d)
                }
                a(this).triggerHandler("jqGridGroupingClickGroup", [b, l]), a.isFunction(this.p.onClickGroup) && this.p.onClickGroup.call(this, b, l)
            }), !1
        },
        "groupingRender": function(b, c) {
            return this.each(function() {
                function d(a, b, c) {
                    var d = !1;
                    if (0 === b) d = c[a];
                    else {
                        var e = c[a].idx;
                        if (0 === e) d = c[a];
                        else
                            for (; a >= 0; a--)
                                if (c[a].idx === e - b) {
                                    d = c[a];
                                    break
                                }
                    }
                    return d
                }
                var e, f, g, h = this,
                    i = h.p.groupingView,
                    j = "",
                    k = "",
                    l = i.groupCollapse ? i.plusicon : i.minusicon,
                    m = [],
                    n = i.groupField.length,
                    l = l + (" tree-wrap-" + h.p.direction);
                a.each(h.p.colModel, function(a, b) {
                    var c;
                    for (c = 0; n > c; c++)
                        if (i.groupField[c] === b.name) {
                            m[c] = a;
                            break
                        }
                });
                var o = 0,
                    p = a.makeArray(i.groupSummary);
                p.reverse(), a.each(i.groups, function(q, r) {
                    o++, f = h.p.id + "ghead_" + r.idx, e = f + "_" + q, k = "<span style='cursor:pointer;' class='ui-icon " + l + "' onclick=\"jQuery('#" + a.jgrid.jqID(h.p.id) + "').jqGrid('groupingToggle','" + e + "');return false;\"></span>";
                    try {
                        a.isArray(i.formatDisplayField) && a.isFunction(i.formatDisplayField[r.idx]) ? (r.displayValue = i.formatDisplayField[r.idx].call(h, r.displayValue, r.value, h.p.colModel[m[r.idx]], r.idx, i), g = r.displayValue) : g = h.formatter(e, r.displayValue, m[r.idx], r.value)
                    } catch (s) {
                        g = r.displayValue
                    }
                    if (j += '<tr id="' + e + '"' + (i.groupCollapse && 0 < r.idx ? ' style="display:none;" ' : " ") + 'role="row" class= "ui-widget-content jqgroup ui-row-' + h.p.direction + " " + f + '"><td style="padding-left:' + 12 * r.idx + 'px;" colspan="' + c + '">' + k + a.jgrid.template(i.groupText[r.idx], g, r.cnt, r.summary) + "</td></tr>", n - 1 === r.idx) {
                        var t, u, v = i.groups[q + 1],
                            w = void 0 !== v ? i.groups[q + 1].startRow : b.length;
                        for (u = r.startRow; w > u; u++) j += b[u].join("");
                        var x;
                        if (void 0 !== v) {
                            for (x = 0; x < i.groupField.length && v.dataIndex !== i.groupField[x]; x++);
                            o = i.groupField.length - x
                        }
                        for (v = 0; o > v; v++)
                            if (p[v]) {
                                u = "", i.groupCollapse && !i.showSummaryOnHide && (u = ' style="display:none;"'), j += "<tr" + u + ' jqfootlevel="' + (r.idx - v) + '" role="row" class="ui-widget-content jqfoot ui-row-' + h.p.direction + '">', u = d(q, v, i.groups);
                                var y, z = h.p.colModel,
                                    A = u.cnt;
                                for (t = 0; c > t; t++) {
                                    var B = "<td " + h.formatCol(t, 1, "") + ">&#160;</td>",
                                        C = "{0}";
                                    a.each(u.summary, function() {
                                        if (this.nm === z[t].name) {
                                            z[t].summaryTpl && (C = z[t].summaryTpl), "string" == typeof this.st && "avg" === this.st.toLowerCase() && (this.sd && this.vd ? this.v /= this.vd : this.v && A > 0 && (this.v /= A));
                                            try {
                                                y = h.formatter("", this.v, t, this)
                                            } catch (b) {
                                                y = this.v
                                            }
                                            return B = "<td " + h.formatCol(t, 1, "") + ">" + a.jgrid.format(C, y) + "</td>", !1
                                        }
                                    }), j += B
                                }
                                j += "</tr>"
                            }
                        o = x
                    }
                }), a("#" + a.jgrid.jqID(h.p.id) + " tbody:first").append(j), j = null
            })
        },
        "groupingGroupBy": function(b, c) {
            return this.each(function() {
                "string" == typeof b && (b = [b]);
                var d = this.p.groupingView;
                this.p.grouping = !0, void 0 === d.visibiltyOnNextGrouping && (d.visibiltyOnNextGrouping = []);
                var e;
                for (e = 0; e < d.groupField.length; e++) !d.groupColumnShow[e] && d.visibiltyOnNextGrouping[e] && a(this).jqGrid("showCol", d.groupField[e]);
                for (e = 0; e < b.length; e++) d.visibiltyOnNextGrouping[e] = a("#" + a.jgrid.jqID(this.p.id) + "_" + a.jgrid.jqID(b[e])).is(":visible");
                this.p.groupingView = a.extend(this.p.groupingView, c || {}), d.groupField = b, a(this).trigger("reloadGrid")
            })
        },
        "groupingRemove": function(b) {
            return this.each(function() {
                if (void 0 === b && (b = !0), this.p.grouping = !1, !0 === b) {
                    var c, d = this.p.groupingView;
                    for (c = 0; c < d.groupField.length; c++) !d.groupColumnShow[c] && d.visibiltyOnNextGrouping[c] && a(this).jqGrid("showCol", d.groupField);
                    a("tr.jqgroup, tr.jqfoot", "#" + a.jgrid.jqID(this.p.id) + " tbody:first").remove(), a("tr.jqgrow:hidden", "#" + a.jgrid.jqID(this.p.id) + " tbody:first").show()
                } else a(this).trigger("reloadGrid")
            })
        },
        "groupingCalculations": {
            "handler": function(a, b, c, d, e, f) {
                var g = {
                    "sum": function() {
                        return parseFloat(b || 0) + parseFloat(f[c] || 0)
                    },
                    "min": function() {
                        return "" === b ? parseFloat(f[c] || 0) : Math.min(parseFloat(b), parseFloat(f[c] || 0))
                    },
                    "max": function() {
                        return "" === b ? parseFloat(f[c] || 0) : Math.max(parseFloat(b), parseFloat(f[c] || 0))
                    },
                    "count": function() {
                        return "" === b && (b = 0), f.hasOwnProperty(c) ? b + 1 : 0
                    },
                    "avg": function() {
                        return g.sum()
                    }
                };
                if (!g[a]) throw "jqGrid Grouping No such method: " + a;
                return a = g[a](), null != d && ("fixed" === e ? a = a.toFixed(d) : (d = Math.pow(10, d), a = Math.round(a * d) / d)), a
            }
        }
    })
}(jQuery),
function(a) {
    a.jgrid.extend({
        "setTreeNode": function(b, c) {
            return this.each(function() {
                var d = this;
                if (d.grid && d.p.treeGrid)
                    for (var e, f, g, h, i = d.p.expColInd, j = d.p.treeReader.expanded_field, k = d.p.treeReader.leaf_field, l = d.p.treeReader.level_field, m = d.p.treeReader.icon_field, n = d.p.treeReader.loaded; c > b;) h = a.jgrid.stripPref(d.p.idPrefix, d.rows[b].id), h = d.p.data[d.p._index[h]], "nested" === d.p.treeGridModel && !h[k] && (e = parseInt(h[d.p.treeReader.left_field], 10), f = parseInt(h[d.p.treeReader.right_field], 10), h[k] = f === e + 1 ? "true" : "false", d.rows[b].cells[d.p._treeleafpos].innerHTML = h[k]), e = parseInt(h[l], 10), 0 === d.p.tree_root_level ? (g = e + 1, f = e) : (g = e, f = e - 1), g = "<div class='tree-wrap tree-wrap-" + d.p.direction + "' style='width:" + 18 * g + "px;'>", g += "<div style='" + ("rtl" === d.p.direction ? "right:" : "left:") + 18 * f + "px;' class='ui-icon ", void 0 !== h[n] && (h[n] = "true" === h[n] || !0 === h[n] ? !0 : !1), "true" === h[k] || !0 === h[k] ? (g += (void 0 !== h[m] && "" !== h[m] ? h[m] : d.p.treeIcons.leaf) + " tree-leaf treeclick", h[k] = !0, f = "leaf") : (h[k] = !1, f = ""), h[j] = ("true" === h[j] || !0 === h[j] ? !0 : !1) && (h[n] || void 0 === h[n]), g = !1 === h[j] ? g + (!0 === h[k] ? "'" : d.p.treeIcons.plus + " tree-plus treeclick'") : g + (!0 === h[k] ? "'" : d.p.treeIcons.minus + " tree-minus treeclick'"), g += "></div></div>", a(d.rows[b].cells[i]).wrapInner("<span class='cell-wrapper" + f + "'></span>").prepend(g), e !== parseInt(d.p.tree_root_level, 10) && ((h = (h = a(d).jqGrid("getNodeParent", h)) && h.hasOwnProperty(j) ? h[j] : !0) || a(d.rows[b]).css("display", "none")), a(d.rows[b].cells[i]).find("div.treeclick").bind("click", function(b) {
                        return b = a.jgrid.stripPref(d.p.idPrefix, a(b.target || b.srcElement, d.rows).closest("tr.jqgrow")[0].id), b = d.p._index[b], d.p.data[b][k] || (d.p.data[b][j] ? (a(d).jqGrid("collapseRow", d.p.data[b]), a(d).jqGrid("collapseNode", d.p.data[b])) : (a(d).jqGrid("expandRow", d.p.data[b]), a(d).jqGrid("expandNode", d.p.data[b]))), !1
                    }), !0 === d.p.ExpandColClick && a(d.rows[b].cells[i]).find("span.cell-wrapper").css("cursor", "pointer").bind("click", function(b) {
                        var b = a.jgrid.stripPref(d.p.idPrefix, a(b.target || b.srcElement, d.rows).closest("tr.jqgrow")[0].id),
                            c = d.p._index[b];
                        return d.p.data[c][k] || (d.p.data[c][j] ? (a(d).jqGrid("collapseRow", d.p.data[c]), a(d).jqGrid("collapseNode", d.p.data[c])) : (a(d).jqGrid("expandRow", d.p.data[c]), a(d).jqGrid("expandNode", d.p.data[c]))), a(d).jqGrid("setSelection", b), !1
                    }), b++
            })
        },
        "setTreeGrid": function() {
            return this.each(function() {
                var b, c, d, e = this,
                    f = 0,
                    g = !1,
                    h = [];
                if (e.p.treeGrid) {
                    e.p.treedatatype || a.extend(e.p, {
                        "treedatatype": e.p.datatype
                    }), e.p.subGrid = !1, e.p.altRows = !1, e.p.pgbuttons = !1, e.p.pginput = !1, e.p.gridview = !0, null === e.p.rowTotal && (e.p.rowNum = 1e4), e.p.multiselect = !1, e.p.rowList = [], e.p.expColInd = 0, e.p.treeIcons = a.extend({
                        "plus": "ui-icon-triangle-1-" + ("rtl" === e.p.direction ? "w" : "e"),
                        "minus": "ui-icon-triangle-1-s",
                        "leaf": "ui-icon-radio-off"
                    }, e.p.treeIcons || {}), "nested" === e.p.treeGridModel ? e.p.treeReader = a.extend({
                        "level_field": "level",
                        "left_field": "lft",
                        "right_field": "rgt",
                        "leaf_field": "isLeaf",
                        "expanded_field": "expanded",
                        "loaded": "loaded",
                        "icon_field": "icon"
                    }, e.p.treeReader) : "adjacency" === e.p.treeGridModel && (e.p.treeReader = a.extend({
                        "level_field": "level",
                        "parent_id_field": "parent",
                        "leaf_field": "isLeaf",
                        "expanded_field": "expanded",
                        "loaded": "loaded",
                        "icon_field": "icon"
                    }, e.p.treeReader));
                    for (c in e.p.colModel)
                        if (e.p.colModel.hasOwnProperty(c))
                            for (d in b = e.p.colModel[c].name, b === e.p.ExpandColumn && !g && (g = !0, e.p.expColInd = f), f++, e.p.treeReader) e.p.treeReader.hasOwnProperty(d) && e.p.treeReader[d] === b && h.push(b);
                    a.each(e.p.treeReader, function(b, c) {
                        c && -1 === a.inArray(c, h) && ("leaf_field" === b && (e.p._treeleafpos = f), f++, e.p.colNames.push(c), e.p.colModel.push({
                            "name": c,
                            "width": 1,
                            "hidden": !0,
                            "sortable": !1,
                            "resizable": !1,
                            "hidedlg": !0,
                            "editable": !0,
                            "search": !1
                        }))
                    })
                }
            })
        },
        "expandRow": function(b) {
            this.each(function() {
                var c = this;
                if (c.grid && c.p.treeGrid) {
                    var d = a(c).jqGrid("getNodeChildren", b),
                        e = c.p.treeReader.expanded_field;
                    a(d).each(function() {
                        var b = c.p.idPrefix + a.jgrid.getAccessor(this, c.p.localReader.id);
                        a(a(c).jqGrid("getGridRowById", b)).css("display", ""), this[e] && a(c).jqGrid("expandRow", this)
                    })
                }
            })
        },
        "collapseRow": function(b) {
            this.each(function() {
                var c = this;
                if (c.grid && c.p.treeGrid) {
                    var d = a(c).jqGrid("getNodeChildren", b),
                        e = c.p.treeReader.expanded_field;
                    a(d).each(function() {
                        var b = c.p.idPrefix + a.jgrid.getAccessor(this, c.p.localReader.id);
                        a(a(c).jqGrid("getGridRowById", b)).css("display", "none"), this[e] && a(c).jqGrid("collapseRow", this)
                    })
                }
            })
        },
        "getRootNodes": function() {
            var b = [];
            return this.each(function() {
                var c = this;
                if (c.grid && c.p.treeGrid) switch (c.p.treeGridModel) {
                    case "nested":
                        var d = c.p.treeReader.level_field;
                        a(c.p.data).each(function() {
                            parseInt(this[d], 10) === parseInt(c.p.tree_root_level, 10) && b.push(this)
                        });
                        break;
                    case "adjacency":
                        var e = c.p.treeReader.parent_id_field;
                        a(c.p.data).each(function() {
                            (null === this[e] || "null" === ("" + this[e]).toLowerCase()) && b.push(this)
                        })
                }
            }), b
        },
        "getNodeDepth": function(b) {
            var c = null;
            return this.each(function() {
                if (this.grid && this.p.treeGrid) switch (this.p.treeGridModel) {
                    case "nested":
                        c = parseInt(b[this.p.treeReader.level_field], 10) - parseInt(this.p.tree_root_level, 10);
                        break;
                    case "adjacency":
                        c = a(this).jqGrid("getNodeAncestors", b).length
                }
            }), c
        },
        "getNodeParent": function(b) {
            var c = null;
            return this.each(function() {
                var d = this;
                if (d.grid && d.p.treeGrid) switch (d.p.treeGridModel) {
                    case "nested":
                        var e = d.p.treeReader.left_field,
                            f = d.p.treeReader.right_field,
                            g = d.p.treeReader.level_field,
                            h = parseInt(b[e], 10),
                            i = parseInt(b[f], 10),
                            j = parseInt(b[g], 10);
                        a(this.p.data).each(function() {
                            return parseInt(this[g], 10) === j - 1 && parseInt(this[e], 10) < h && parseInt(this[f], 10) > i ? (c = this, !1) : void 0
                        });
                        break;
                    case "adjacency":
                        var k = d.p.treeReader.parent_id_field,
                            l = d.p.localReader.id;
                        a(this.p.data).each(function() {
                            return this[l] === a.jgrid.stripPref(d.p.idPrefix, b[k]) ? (c = this, !1) : void 0
                        })
                }
            }), c
        },
        "getNodeChildren": function(b) {
            var c = [];
            return this.each(function() {
                var d = this;
                if (d.grid && d.p.treeGrid) switch (d.p.treeGridModel) {
                    case "nested":
                        var e = d.p.treeReader.left_field,
                            f = d.p.treeReader.right_field,
                            g = d.p.treeReader.level_field,
                            h = parseInt(b[e], 10),
                            i = parseInt(b[f], 10),
                            j = parseInt(b[g], 10);
                        a(this.p.data).each(function() {
                            parseInt(this[g], 10) === j + 1 && parseInt(this[e], 10) > h && parseInt(this[f], 10) < i && c.push(this)
                        });
                        break;
                    case "adjacency":
                        var k = d.p.treeReader.parent_id_field,
                            l = d.p.localReader.id;
                        a(this.p.data).each(function() {
                            this[k] == a.jgrid.stripPref(d.p.idPrefix, b[l]) && c.push(this)
                        })
                }
            }), c
        },
        "getFullTreeNode": function(b) {
            var c = [];
            return this.each(function() {
                var d, e = this;
                if (e.grid && e.p.treeGrid) switch (e.p.treeGridModel) {
                    case "nested":
                        var f = e.p.treeReader.left_field,
                            g = e.p.treeReader.right_field,
                            h = e.p.treeReader.level_field,
                            i = parseInt(b[f], 10),
                            j = parseInt(b[g], 10),
                            k = parseInt(b[h], 10);
                        a(this.p.data).each(function() {
                            parseInt(this[h], 10) >= k && parseInt(this[f], 10) >= i && parseInt(this[f], 10) <= j && c.push(this)
                        });
                        break;
                    case "adjacency":
                        if (b) {
                            c.push(b);
                            var l = e.p.treeReader.parent_id_field,
                                m = e.p.localReader.id;
                            a(this.p.data).each(function(b) {
                                for (d = c.length, b = 0; d > b; b++)
                                    if (a.jgrid.stripPref(e.p.idPrefix, c[b][m]) === this[l]) {
                                        c.push(this);
                                        break
                                    }
                            })
                        }
                }
            }), c
        },
        "getNodeAncestors": function(b) {
            var c = [];
            return this.each(function() {
                if (this.grid && this.p.treeGrid)
                    for (var d = a(this).jqGrid("getNodeParent", b); d;) c.push(d), d = a(this).jqGrid("getNodeParent", d)
            }), c
        },
        "isVisibleNode": function(b) {
            var c = !0;
            return this.each(function() {
                if (this.grid && this.p.treeGrid) {
                    var d = a(this).jqGrid("getNodeAncestors", b),
                        e = this.p.treeReader.expanded_field;
                    a(d).each(function() {
                        return c = c && this[e], c ? void 0 : !1
                    })
                }
            }), c
        },
        "isNodeLoaded": function(b) {
            var c;
            return this.each(function() {
                if (this.grid && this.p.treeGrid) {
                    var d = this.p.treeReader.leaf_field,
                        e = this.p.treeReader.loaded;
                    c = void 0 !== b ? void 0 !== b[e] ? b[e] : b[d] || 0 < a(this).jqGrid("getNodeChildren", b).length ? !0 : !1 : !1
                }
            }), c
        },
        "expandNode": function(b) {
            return this.each(function() {
                if (this.grid && this.p.treeGrid) {
                    var c = this.p.treeReader.expanded_field,
                        d = this.p.treeReader.parent_id_field,
                        e = this.p.treeReader.loaded,
                        f = this.p.treeReader.level_field,
                        g = this.p.treeReader.left_field,
                        h = this.p.treeReader.right_field;
                    if (!b[c]) {
                        var i = a.jgrid.getAccessor(b, this.p.localReader.id),
                            j = a("#" + this.p.idPrefix + a.jgrid.jqID(i), this.grid.bDiv)[0],
                            k = this.p._index[i];
                        a(this).jqGrid("isNodeLoaded", this.p.data[k]) ? (b[c] = !0, a("div.treeclick", j).removeClass(this.p.treeIcons.plus + " tree-plus").addClass(this.p.treeIcons.minus + " tree-minus")) : this.grid.hDiv.loading || (b[c] = !0, a("div.treeclick", j).removeClass(this.p.treeIcons.plus + " tree-plus").addClass(this.p.treeIcons.minus + " tree-minus"), this.p.treeANode = j.rowIndex, this.p.datatype = this.p.treedatatype, "nested" === this.p.treeGridModel ? a(this).jqGrid("setGridParam", {
                            "postData": {
                                "nodeid": i,
                                "n_left": b[g],
                                "n_right": b[h],
                                "n_level": b[f]
                            }
                        }) : a(this).jqGrid("setGridParam", {
                            "postData": {
                                "nodeid": i,
                                "parentid": b[d],
                                "n_level": b[f]
                            }
                        }), a(this).trigger("reloadGrid"), b[e] = !0, "nested" === this.p.treeGridModel ? a(this).jqGrid("setGridParam", {
                            "postData": {
                                "nodeid": "",
                                "n_left": "",
                                "n_right": "",
                                "n_level": ""
                            }
                        }) : a(this).jqGrid("setGridParam", {
                            "postData": {
                                "nodeid": "",
                                "parentid": "",
                                "n_level": ""
                            }
                        }))
                    }
                }
            })
        },
        "collapseNode": function(b) {
            return this.each(function() {
                if (this.grid && this.p.treeGrid) {
                    var c = this.p.treeReader.expanded_field;
                    b[c] && (b[c] = !1, c = a.jgrid.getAccessor(b, this.p.localReader.id), c = a("#" + this.p.idPrefix + a.jgrid.jqID(c), this.grid.bDiv)[0], a("div.treeclick", c).removeClass(this.p.treeIcons.minus + " tree-minus").addClass(this.p.treeIcons.plus + " tree-plus"))
                }
            })
        },
        "SortTree": function(b, c, d, e) {
            return this.each(function() {
                if (this.grid && this.p.treeGrid) {
                    var f, g, h, i, j = [],
                        k = this;
                    for (f = a(this).jqGrid("getRootNodes"), f = a.jgrid.from(f), f.orderBy(b, c, d, e), i = f.select(), f = 0, g = i.length; g > f; f++) h = i[f], j.push(h), a(this).jqGrid("collectChildrenSortTree", j, h, b, c, d, e);
                    a.each(j, function(b) {
                        var c = a.jgrid.getAccessor(this, k.p.localReader.id);
                        a("#" + a.jgrid.jqID(k.p.id) + " tbody tr:eq(" + b + ")").after(a("tr#" + a.jgrid.jqID(c), k.grid.bDiv))
                    }), j = i = f = null
                }
            })
        },
        "collectChildrenSortTree": function(b, c, d, e, f, g) {
            return this.each(function() {
                if (this.grid && this.p.treeGrid) {
                    var h, i, j, k;
                    for (h = a(this).jqGrid("getNodeChildren", c), h = a.jgrid.from(h), h.orderBy(d, e, f, g), k = h.select(), h = 0, i = k.length; i > h; h++) j = k[h], b.push(j), a(this).jqGrid("collectChildrenSortTree", b, j, d, e, f, g)
                }
            })
        },
        "setTreeRow": function(b, c) {
            var d = !1;
            return this.each(function() {
                this.grid && this.p.treeGrid && (d = a(this).jqGrid("setRowData", b, c))
            }), d
        },
        "delTreeNode": function(b) {
            return this.each(function() {
                var c, d, e, f, g = this.p.localReader.id,
                    h = this.p.treeReader.left_field,
                    i = this.p.treeReader.right_field;
                if (this.grid && this.p.treeGrid && (c = this.p._index[b], void 0 !== c)) {
                    d = parseInt(this.p.data[c][i], 10), e = d - parseInt(this.p.data[c][h], 10) + 1;
                    var j = a(this).jqGrid("getFullTreeNode", this.p.data[c]);
                    if (0 < j.length)
                        for (c = 0; c < j.length; c++) a(this).jqGrid("delRowData", j[c][g]);
                    if ("nested" === this.p.treeGridModel) {
                        if (g = a.jgrid.from(this.p.data).greater(h, d, {
                                "stype": "integer"
                            }).select(), g.length)
                            for (f in g) g.hasOwnProperty(f) && (g[f][h] = parseInt(g[f][h], 10) - e);
                        if (g = a.jgrid.from(this.p.data).greater(i, d, {
                                "stype": "integer"
                            }).select(), g.length)
                            for (f in g) g.hasOwnProperty(f) && (g[f][i] = parseInt(g[f][i], 10) - e)
                    }
                }
            })
        },
        "addChildNode": function(b, c, d, e) {
            var f = this[0];
            if (d) {
                var g, h, i, j, k, l = f.p.treeReader.expanded_field,
                    m = f.p.treeReader.leaf_field,
                    n = f.p.treeReader.level_field,
                    o = f.p.treeReader.parent_id_field,
                    p = f.p.treeReader.left_field,
                    q = f.p.treeReader.right_field,
                    r = f.p.treeReader.loaded;
                g = 0;
                var s, t = c;
                if (void 0 === e && (e = !1), void 0 === b || null === b) {
                    if (k = f.p.data.length - 1, k >= 0)
                        for (; k >= 0;) g = Math.max(g, parseInt(f.p.data[k][f.p.localReader.id], 10)), k--;
                    b = g + 1
                }
                var u = a(f).jqGrid("getInd", c);
                if (s = !1, void 0 === c || null === c || "" === c ? (t = c = null, g = "last", j = f.p.tree_root_level, k = f.p.data.length + 1) : (g = "after", h = f.p._index[c], i = f.p.data[h], c = i[f.p.localReader.id], j = parseInt(i[n], 10) + 1, k = a(f).jqGrid("getFullTreeNode", i), k.length ? (t = k = k[k.length - 1][f.p.localReader.id], k = a(f).jqGrid("getInd", t) + 1) : k = a(f).jqGrid("getInd", c) + 1, i[m] && (s = !0, i[l] = !0, a(f.rows[u]).find("span.cell-wrapperleaf").removeClass("cell-wrapperleaf").addClass("cell-wrapper").end().find("div.tree-leaf").removeClass(f.p.treeIcons.leaf + " tree-leaf").addClass(f.p.treeIcons.minus + " tree-minus"), f.p.data[h][m] = !1, i[r] = !0)), h = k + 1, void 0 === d[l] && (d[l] = !1), void 0 === d[r] && (d[r] = !1), d[n] = j, void 0 === d[m] && (d[m] = !0), "adjacency" === f.p.treeGridModel && (d[o] = c), "nested" === f.p.treeGridModel) {
                    var v;
                    if (null !== c) {
                        if (m = parseInt(i[q], 10), n = a.jgrid.from(f.p.data), n = n.greaterOrEquals(q, m, {
                                "stype": "integer"
                            }), n = n.select(), n.length)
                            for (v in n) n.hasOwnProperty(v) && (n[v][p] = n[v][p] > m ? parseInt(n[v][p], 10) + 2 : n[v][p], n[v][q] = n[v][q] >= m ? parseInt(n[v][q], 10) + 2 : n[v][q]);
                        d[p] = m, d[q] = m + 1
                    } else {
                        if (m = parseInt(a(f).jqGrid("getCol", q, !1, "max"), 10), n = a.jgrid.from(f.p.data).greater(p, m, {
                                "stype": "integer"
                            }).select(), n.length)
                            for (v in n) n.hasOwnProperty(v) && (n[v][p] = parseInt(n[v][p], 10) + 2);
                        if (n = a.jgrid.from(f.p.data).greater(q, m, {
                                "stype": "integer"
                            }).select(), n.length)
                            for (v in n) n.hasOwnProperty(v) && (n[v][q] = parseInt(n[v][q], 10) + 2);
                        d[p] = m + 1, d[q] = m + 2
                    }
                }(null === c || a(f).jqGrid("isNodeLoaded", i) || s) && (a(f).jqGrid("addRowData", b, d, g, t), a(f).jqGrid("setTreeNode", k, h)), i && !i[l] && e && a(f.rows[u]).find("div.treeclick").click()
            }
        }
    })
}(jQuery),
function(a) {
    a.jgrid.extend({
        "jqGridImport": function(b) {
            return b = a.extend({
                "imptype": "xml",
                "impstring": "",
                "impurl": "",
                "mtype": "GET",
                "impData": {},
                "xmlGrid": {
                    "config": "roots>grid",
                    "data": "roots>rows"
                },
                "jsonGrid": {
                    "config": "grid",
                    "data": "data"
                },
                "ajaxOptions": {}
            }, b || {}), this.each(function() {
                var c = this,
                    d = function(b, d) {
                        var e, f, g = a(d.xmlGrid.config, b)[0],
                            h = a(d.xmlGrid.data, b)[0];
                        if (xmlJsonClass.xml2json && a.jgrid.parse) {
                            g = xmlJsonClass.xml2json(g, " "), g = a.jgrid.parse(g);
                            for (f in g) g.hasOwnProperty(f) && (e = g[f]);
                            h ? (h = g.grid.datatype, g.grid.datatype = "xmlstring", g.grid.datastr = b, a(c).jqGrid(e).jqGrid("setGridParam", {
                                "datatype": h
                            })) : a(c).jqGrid(e)
                        } else alert("xml2json or parse are not present")
                    },
                    e = function(b, d) {
                        if (b && "string" == typeof b) {
                            var e = !1;
                            a.jgrid.useJSON && (a.jgrid.useJSON = !1, e = !0);
                            var f = a.jgrid.parse(b);
                            if (e && (a.jgrid.useJSON = !0), e = f[d.jsonGrid.config], f = f[d.jsonGrid.data]) {
                                var g = e.datatype;
                                e.datatype = "jsonstring", e.datastr = f, a(c).jqGrid(e).jqGrid("setGridParam", {
                                    "datatype": g
                                })
                            } else a(c).jqGrid(e)
                        }
                    };
                switch (b.imptype) {
                    case "xml":
                        a.ajax(a.extend({
                            "url": b.impurl,
                            "type": b.mtype,
                            "data": b.impData,
                            "dataType": "xml",
                            "complete": function(e, f) {
                                "success" === f && (d(e.responseXML, b), a(c).triggerHandler("jqGridImportComplete", [e, b]), a.isFunction(b.importComplete) && b.importComplete(e))
                            }
                        }, b.ajaxOptions));
                        break;
                    case "xmlstring":
                        if (b.impstring && "string" == typeof b.impstring) {
                            var f = a.parseXML(b.impstring);
                            f && (d(f, b), a(c).triggerHandler("jqGridImportComplete", [f, b]), a.isFunction(b.importComplete) && b.importComplete(f), b.impstring = null), f = null
                        }
                        break;
                    case "json":
                        a.ajax(a.extend({
                            "url": b.impurl,
                            "type": b.mtype,
                            "data": b.impData,
                            "dataType": "json",
                            "complete": function(d) {
                                try {
                                    e(d.responseText, b), a(c).triggerHandler("jqGridImportComplete", [d, b]), a.isFunction(b.importComplete) && b.importComplete(d)
                                } catch (f) {}
                            }
                        }, b.ajaxOptions));
                        break;
                    case "jsonstring":
                        b.impstring && "string" == typeof b.impstring && (e(b.impstring, b), a(c).triggerHandler("jqGridImportComplete", [b.impstring, b]), a.isFunction(b.importComplete) && b.importComplete(b.impstring), b.impstring = null)
                }
            })
        },
        "jqGridExport": function(b) {
            var b = a.extend({
                    "exptype": "xmlstring",
                    "root": "grid",
                    "ident": "	"
                }, b || {}),
                c = null;
            return this.each(function() {
                if (this.grid) {
                    var d, e = a.extend(!0, {}, a(this).jqGrid("getGridParam"));
                    if (e.rownumbers && (e.colNames.splice(0, 1), e.colModel.splice(0, 1)), e.multiselect && (e.colNames.splice(0, 1), e.colModel.splice(0, 1)), e.subGrid && (e.colNames.splice(0, 1), e.colModel.splice(0, 1)), e.knv = null, e.treeGrid)
                        for (d in e.treeReader) e.treeReader.hasOwnProperty(d) && (e.colNames.splice(e.colNames.length - 1), e.colModel.splice(e.colModel.length - 1));
                    switch (b.exptype) {
                        case "xmlstring":
                            c = "<" + b.root + ">" + xmlJsonClass.json2xml(e, b.ident) + "</" + b.root + ">";
                            break;
                        case "jsonstring":
                            c = "{" + xmlJsonClass.toJson(e, b.root, b.ident, !1) + "}", void 0 !== e.postData.filters && (c = c.replace(/filters":"/, 'filters":'), c = c.replace(/}]}"/, "}]}"))
                    }
                }
            }), c
        },
        "excelExport": function(b) {
            return b = a.extend({
                "exptype": "remote",
                "url": null,
                "oper": "oper",
                "tag": "excel",
                "exportOptions": {}
            }, b || {}), this.each(function() {
                if (this.grid) {
                    var c;
                    "remote" === b.exptype && (c = a.extend({}, this.p.postData), c[b.oper] = b.tag, c = jQuery.param(c), c = -1 !== b.url.indexOf("?") ? b.url + "&" + c : b.url + "?" + c, window.location = c)
                }
            })
        }
    })
}(jQuery);
var xmlJsonClass = {
    "xml2json": function(a, b) {
        9 === a.nodeType && (a = a.documentElement);
        var c = this.toJson(this.toObj(this.removeWhite(a)), a.nodeName, "	");
        return "{\n" + b + (b ? c.replace(/\t/g, b) : c.replace(/\t|\n/g, "")) + "\n}"
    },
    "json2xml": function(a, b) {
        var c, d = function(a, b, c) {
                var e, f, g = "";
                if (a instanceof Array)
                    if (0 === a.length) g += c + "<" + b + ">__EMPTY_ARRAY_</" + b + ">\n";
                    else
                        for (e = 0, f = a.length; f > e; e += 1) var h = c + d(a[e], b, c + "	") + "\n",
                            g = g + h;
                else if ("object" == typeof a) {
                    e = !1, g += c + "<" + b;
                    for (f in a) a.hasOwnProperty(f) && ("@" === f.charAt(0) ? g += " " + f.substr(1) + '="' + a[f].toString() + '"' : e = !0);
                    if (g += e ? ">" : "/>", e) {
                        for (f in a) a.hasOwnProperty(f) && ("#text" === f ? g += a[f] : "#cdata" === f ? g += "<![CDATA[" + a[f] + "]]>" : "@" !== f.charAt(0) && (g += d(a[f], f, c + "	")));
                        g += ("\n" === g.charAt(g.length - 1) ? c : "") + "</" + b + ">"
                    }
                } else "function" == typeof a ? g += c + "<" + b + "><![CDATA[" + a + "]]></" + b + ">" : (void 0 === a && (a = ""), g = '""' === a.toString() || 0 === a.toString().length ? g + (c + "<" + b + ">__EMPTY_STRING_</" + b + ">") : g + (c + "<" + b + ">" + a.toString() + "</" + b + ">"));
                return g
            },
            e = "";
        for (c in a) a.hasOwnProperty(c) && (e += d(a[c], c, ""));
        return b ? e.replace(/\t/g, b) : e.replace(/\t|\n/g, "")
    },
    "toObj": function(a) {
        var b = {},
            c = /function/i;
        if (1 === a.nodeType) {
            if (a.attributes.length) {
                var d;
                for (d = 0; d < a.attributes.length; d += 1) b["@" + a.attributes[d].nodeName] = (a.attributes[d].nodeValue || "").toString()
            }
            if (a.firstChild) {
                var e, f = d = 0,
                    g = !1;
                for (e = a.firstChild; e; e = e.nextSibling) 1 === e.nodeType ? g = !0 : 3 === e.nodeType && e.nodeValue.match(/[^ \f\n\r\t\v]/) ? d += 1 : 4 === e.nodeType && (f += 1);
                if (g)
                    if (2 > d && 2 > f)
                        for (this.removeWhite(a), e = a.firstChild; e; e = e.nextSibling) 3 === e.nodeType ? b["#text"] = this.escape(e.nodeValue) : 4 === e.nodeType ? c.test(e.nodeValue) ? b[e.nodeName] = [b[e.nodeName], e.nodeValue] : b["#cdata"] = this.escape(e.nodeValue) : b[e.nodeName] ? b[e.nodeName] instanceof Array ? b[e.nodeName][b[e.nodeName].length] = this.toObj(e) : b[e.nodeName] = [b[e.nodeName], this.toObj(e)] : b[e.nodeName] = this.toObj(e);
                    else a.attributes.length ? b["#text"] = this.escape(this.innerXml(a)) : b = this.escape(this.innerXml(a));
                else if (d) a.attributes.length ? b["#text"] = this.escape(this.innerXml(a)) : (b = this.escape(this.innerXml(a)), "__EMPTY_ARRAY_" === b ? b = "[]" : "__EMPTY_STRING_" === b && (b = ""));
                else if (f)
                    if (f > 1) b = this.escape(this.innerXml(a));
                    else
                        for (e = a.firstChild; e; e = e.nextSibling) {
                            if (c.test(a.firstChild.nodeValue)) {
                                b = a.firstChild.nodeValue;
                                break
                            }
                            b["#cdata"] = this.escape(e.nodeValue)
                        }
            }!a.attributes.length && !a.firstChild && (b = null)
        } else 9 === a.nodeType ? b = this.toObj(a.documentElement) : alert("unhandled node type: " + a.nodeType);
        return b
    },
    "toJson": function(a, b, c, d) {
        void 0 === d && (d = !0);
        var e = b ? '"' + b + '"' : "",
            f = "	",
            g = "\n";
        if (d || (g = f = ""), "[]" === a) e += b ? ":[]" : "[]";
        else if (a instanceof Array) {
            var h, i, j = [];
            for (i = 0, h = a.length; h > i; i += 1) j[i] = this.toJson(a[i], "", c + f, d);
            e += (b ? ":[" : "[") + (1 < j.length ? g + c + f + j.join("," + g + c + f) + g + c : j.join("")) + "]"
        } else if (null === a) e += (b && ":") + "null";
        else if ("object" == typeof a) {
            h = [];
            for (i in a) a.hasOwnProperty(i) && (h[h.length] = this.toJson(a[i], i, c + f, d));
            e += (b ? ":{" : "{") + (1 < h.length ? g + c + f + h.join("," + g + c + f) + g + c : h.join("")) + "}"
        } else e = "string" == typeof a ? e + ((b && ":") + '"' + a.replace(/\\/g, "\\\\").replace(/\"/g, '\\"') + '"') : e + ((b && ":") + a.toString());
        return e
    },
    "innerXml": function(a) {
        var b = "";
        if ("innerHTML" in a) b = a.innerHTML;
        else
            for (var c = function(a) {
                    var b, d = "";
                    if (1 === a.nodeType) {
                        for (d += "<" + a.nodeName, b = 0; b < a.attributes.length; b += 1) d += " " + a.attributes[b].nodeName + '="' + (a.attributes[b].nodeValue || "").toString() + '"';
                        if (a.firstChild) {
                            for (d += ">", b = a.firstChild; b; b = b.nextSibling) d += c(b);
                            d += "</" + a.nodeName + ">"
                        } else d += "/>"
                    } else 3 === a.nodeType ? d += a.nodeValue : 4 === a.nodeType && (d += "<![CDATA[" + a.nodeValue + "]]>");
                    return d
                }, a = a.firstChild; a; a = a.nextSibling) b += c(a);
        return b
    },
    "escape": function(a) {
        return a.replace(/[\\]/g, "\\\\").replace(/[\"]/g, '\\"').replace(/[\n]/g, "\\n").replace(/[\r]/g, "\\r")
    },
    "removeWhite": function(a) {
        a.normalize();
        var b;
        for (b = a.firstChild; b;)
            if (3 === b.nodeType)
                if (b.nodeValue.match(/[^ \f\n\r\t\v]/)) b = b.nextSibling;
                else {
                    var c = b.nextSibling;
                    a.removeChild(b), b = c
                } else 1 === b.nodeType && this.removeWhite(b), b = b.nextSibling;
        return a
    }
};
! function(b) {
    if (b.jgrid.msie && 8 === b.jgrid.msiever() && (b.expr[":"].hidden = function(a) {
            return 0 === a.offsetWidth || 0 === a.offsetHeight || "none" === a.style.display
        }), b.jgrid._multiselect = !1, b.ui && b.ui.multiselect) {
        if (b.ui.multiselect.prototype._setSelected) {
            var o = b.ui.multiselect.prototype._setSelected;
            b.ui.multiselect.prototype._setSelected = function(a, c) {
                var d = o.call(this, a, c);
                if (c && this.selectedList) {
                    var e = this.element;
                    this.selectedList.find("li").each(function() {
                        b(this).data("optionLink") && b(this).data("optionLink").remove().appendTo(e)
                    })
                }
                return d
            }
        }
        b.ui.multiselect.prototype.destroy && (b.ui.multiselect.prototype.destroy = function() {
            this.element.show(), this.container.remove(), void 0 === b.Widget ? b.widget.prototype.destroy.apply(this, arguments) : b.Widget.prototype.destroy.apply(this, arguments)
        }), b.jgrid._multiselect = !0
    }
    b.jgrid.extend({
        "sortableColumns": function(a) {
            return this.each(function() {
                function c() {
                    d.p.disableClick = !0
                }
                var d = this,
                    e = b.jgrid.jqID(d.p.id),
                    e = {
                        "tolerance": "pointer",
                        "axis": "x",
                        "scrollSensitivity": "1",
                        "items": ">th:not(:has(#jqgh_" + e + "_cb,#jqgh_" + e + "_rn,#jqgh_" + e + "_subgrid),:hidden)",
                        "placeholder": {
                            "element": function(a) {
                                return b(document.createElement(a[0].nodeName)).addClass(a[0].className + " ui-sortable-placeholder ui-state-highlight").removeClass("ui-sortable-helper")[0]
                            },
                            "update": function(a, b) {
                                b.height(a.currentItem.innerHeight() - parseInt(a.currentItem.css("paddingTop") || 0, 10) - parseInt(a.currentItem.css("paddingBottom") || 0, 10)), b.width(a.currentItem.innerWidth() - parseInt(a.currentItem.css("paddingLeft") || 0, 10) - parseInt(a.currentItem.css("paddingRight") || 0, 10))
                            }
                        },
                        "update": function(a, c) {
                            var e = b(c.item).parent(),
                                e = b(">th", e),
                                f = {},
                                g = d.p.id + "_";
                            b.each(d.p.colModel, function(a) {
                                f[this.name] = a
                            });
                            var h = [];
                            e.each(function() {
                                var a = b(">div", this).get(0).id.replace(/^jqgh_/, "").replace(g, "");
                                f.hasOwnProperty(a) && h.push(f[a])
                            }), b(d).jqGrid("remapColumns", h, !0, !0), b.isFunction(d.p.sortable.update) && d.p.sortable.update(h), setTimeout(function() {
                                d.p.disableClick = !1
                            }, 50)
                        }
                    };
                if (d.p.sortable.options ? b.extend(e, d.p.sortable.options) : b.isFunction(d.p.sortable) && (d.p.sortable = {
                        "update": d.p.sortable
                    }), e.start) {
                    var f = e.start;
                    e.start = function(a, b) {
                        c(), f.call(this, a, b)
                    }
                } else e.start = c;
                d.p.sortable.exclude && (e.items = e.items + (":not(" + d.p.sortable.exclude + ")")), a.sortable(e).data("sortable").floating = !0
            })
        },
        "columnChooser": function(a) {
            function c(a, c) {
                a && ("string" == typeof a ? b.fn[a] && b.fn[a].apply(c, b.makeArray(arguments).slice(2)) : b.isFunction(a) && a.apply(c, b.makeArray(arguments).slice(2)))
            }
            var d = this;
            if (!b("#colchooser_" + b.jgrid.jqID(d[0].p.id)).length) {
                var e = b('<div id="colchooser_' + d[0].p.id + '" style="position:relative;overflow:hidden"><div><select multiple="multiple"></select></div></div>'),
                    f = b("select", e),
                    a = b.extend({
                        "width": 420,
                        "height": 240,
                        "classname": null,
                        "done": function(a) {
                            a && d.jqGrid("remapColumns", a, !0)
                        },
                        "msel": "multiselect",
                        "dlog": "dialog",
                        "dialog_opts": {
                            "minWidth": 470
                        },
                        "dlog_opts": function(a) {
                            var c = {};
                            return c[a.bSubmit] = function() {
                                a.apply_perm(), a.cleanup(!1)
                            }, c[a.bCancel] = function() {
                                a.cleanup(!0)
                            }, b.extend(!0, {
                                "buttons": c,
                                "close": function() {
                                    a.cleanup(!0)
                                },
                                "modal": a.modal || !1,
                                "resizable": a.resizable || !0,
                                "width": a.width + 20
                            }, a.dialog_opts || {})
                        },
                        "apply_perm": function() {
                            b("option", f).each(function() {
                                this.selected ? d.jqGrid("showCol", g[this.value].name) : d.jqGrid("hideCol", g[this.value].name)
                            });
                            var c = [];
                            b("option:selected", f).each(function() {
                                c.push(parseInt(this.value, 10))
                            }), b.each(c, function() {
                                delete i[g[parseInt(this, 10)].name]
                            }), b.each(i, function() {
                                var a = parseInt(this, 10),
                                    b = c,
                                    d = a;
                                if (d >= 0) {
                                    var e = b.slice(),
                                        f = e.splice(d, Math.max(b.length - d, d));
                                    d > b.length && (d = b.length), e[d] = a, c = e.concat(f)
                                } else c = void 0
                            }), a.done && a.done.call(d, c)
                        },
                        "cleanup": function(b) {
                            c(a.dlog, e, "destroy"), c(a.msel, f, "destroy"), e.remove(), b && a.done && a.done.call(d)
                        },
                        "msel_opts": {}
                    }, b.jgrid.col, a || {});
                if (b.ui && b.ui.multiselect && "multiselect" === a.msel) {
                    if (!b.jgrid._multiselect) return void alert("Multiselect plugin loaded after jqGrid. Please load the plugin before the jqGrid!");
                    a.msel_opts = b.extend(b.ui.multiselect.defaults, a.msel_opts)
                }
                a.caption && e.attr("title", a.caption), a.classname && (e.addClass(a.classname), f.addClass(a.classname)), a.width && (b(">div", e).css({
                    "width": a.width,
                    "margin": "0 auto"
                }), f.css("width", a.width)), a.height && (b(">div", e).css("height", a.height), f.css("height", a.height - 10));
                var g = d.jqGrid("getGridParam", "colModel"),
                    h = d.jqGrid("getGridParam", "colNames"),
                    i = {},
                    j = [];
                f.empty(), b.each(g, function(a) {
                    i[this.name] = a, this.hidedlg ? this.hidden || j.push(a) : f.append("<option value='" + a + "' " + (this.hidden ? "" : "selected='selected'") + ">" + b.jgrid.stripHtml(h[a]) + "</option>")
                });
                var k = b.isFunction(a.dlog_opts) ? a.dlog_opts.call(d, a) : a.dlog_opts;
                c(a.dlog, e, k), k = b.isFunction(a.msel_opts) ? a.msel_opts.call(d, a) : a.msel_opts, c(a.msel, f, k)
            }
        },
        "sortableRows": function(a) {
            return this.each(function() {
                var c = this;
                c.grid && !c.p.treeGrid && b.fn.sortable && (a = b.extend({
                    "cursor": "move",
                    "axis": "y",
                    "items": ".jqgrow"
                }, a || {}), a.start && b.isFunction(a.start) ? (a._start_ = a.start, delete a.start) : a._start_ = !1, a.update && b.isFunction(a.update) ? (a._update_ = a.update, delete a.update) : a._update_ = !1, a.start = function(d, e) {
                    if (b(e.item).css("border-width", "0px"), b("td", e.item).each(function(a) {
                            this.style.width = c.grid.cols[a].style.width
                        }), c.p.subGrid) {
                        var f = b(e.item).attr("id");
                        try {
                            b(c).jqGrid("collapseSubGridRow", f)
                        } catch (g) {}
                    }
                    a._start_ && a._start_.apply(this, [d, e])
                }, a.update = function(d, e) {
                    b(e.item).css("border-width", ""), c.p.rownumbers === !0 && b("td.jqgrid-rownum", c.rows).each(function(a) {
                        b(this).html(a + 1 + (parseInt(c.p.page, 10) - 1) * parseInt(c.p.rowNum, 10))
                    }), a._update_ && a._update_.apply(this, [d, e])
                }, b("tbody:first", c).sortable(a), b("tbody:first", c).disableSelection())
            })
        },
        "gridDnD": function(a) {
            return this.each(function() {
                function c() {
                    var a = b.data(f, "dnd");
                    b("tr.jqgrow:not(.ui-draggable)", f).draggable(b.isFunction(a.drag) ? a.drag.call(b(f), a) : a.drag)
                }
                var d, e, f = this;
                if (f.grid && !f.p.treeGrid && b.fn.draggable && b.fn.droppable)
                    if (void 0 === b("#jqgrid_dnd")[0] && b("body").append("<table id='jqgrid_dnd' class='ui-jqgrid-dnd'></table>"), "string" == typeof a && "updateDnD" === a && f.p.jqgdnd === !0) c();
                    else if (a = b.extend({
                        "drag": function(a) {
                            return b.extend({
                                "start": function(c, d) {
                                    var e;
                                    if (f.p.subGrid) {
                                        e = b(d.helper).attr("id");
                                        try {
                                            b(f).jqGrid("collapseSubGridRow", e)
                                        } catch (g) {}
                                    }
                                    for (e = 0; e < b.data(f, "dnd").connectWith.length; e++) 0 === b(b.data(f, "dnd").connectWith[e]).jqGrid("getGridParam", "reccount") && b(b.data(f, "dnd").connectWith[e]).jqGrid("addRowData", "jqg_empty_row", {});
                                    d.helper.addClass("ui-state-highlight"), b("td", d.helper).each(function(a) {
                                        this.style.width = f.grid.headers[a].width + "px"
                                    }), a.onstart && b.isFunction(a.onstart) && a.onstart.call(b(f), c, d)
                                },
                                "stop": function(c, d) {
                                    var e;
                                    for (d.helper.dropped && !a.dragcopy && (e = b(d.helper).attr("id"), void 0 === e && (e = b(this).attr("id")), b(f).jqGrid("delRowData", e)), e = 0; e < b.data(f, "dnd").connectWith.length; e++) b(b.data(f, "dnd").connectWith[e]).jqGrid("delRowData", "jqg_empty_row");
                                    a.onstop && b.isFunction(a.onstop) && a.onstop.call(b(f), c, d)
                                }
                            }, a.drag_opts || {})
                        },
                        "drop": function(a) {
                            return b.extend({
                                "accept": function(a) {
                                    return b(a).hasClass("jqgrow") ? (a = b(a).closest("table.ui-jqgrid-btable"), a.length > 0 && void 0 !== b.data(a[0], "dnd") ? (a = b.data(a[0], "dnd").connectWith, -1 !== b.inArray("#" + b.jgrid.jqID(this.id), a) ? !0 : !1) : !1) : a
                                },
                                "drop": function(c, d) {
                                    if (b(d.draggable).hasClass("jqgrow")) {
                                        var e = b(d.draggable).attr("id"),
                                            e = d.draggable.parent().parent().jqGrid("getRowData", e);
                                        if (!a.dropbyname) {
                                            var g, h, i = 0,
                                                j = {},
                                                k = b("#" + b.jgrid.jqID(this.id)).jqGrid("getGridParam", "colModel");
                                            try {
                                                for (h in e) e.hasOwnProperty(h) && (g = k[i].name, "cb" === g || "rn" === g || "subgrid" === g || e.hasOwnProperty(h) && k[i] && (j[g] = e[h]), i++);
                                                e = j
                                            } catch (l) {}
                                        }
                                        if (d.helper.dropped = !0, a.beforedrop && b.isFunction(a.beforedrop) && (g = a.beforedrop.call(this, c, d, e, b("#" + b.jgrid.jqID(f.p.id)), b(this)), void 0 !== g && null !== g && "object" == typeof g && (e = g)), d.helper.dropped) {
                                            var m;
                                            a.autoid && (b.isFunction(a.autoid) ? m = a.autoid.call(this, e) : (m = Math.ceil(1e3 * Math.random()), m = a.autoidprefix + m)), b("#" + b.jgrid.jqID(this.id)).jqGrid("addRowData", m, e, a.droppos)
                                        }
                                        a.ondrop && b.isFunction(a.ondrop) && a.ondrop.call(this, c, d, e)
                                    }
                                }
                            }, a.drop_opts || {})
                        },
                        "onstart": null,
                        "onstop": null,
                        "beforedrop": null,
                        "ondrop": null,
                        "drop_opts": {
                            "activeClass": "ui-state-active",
                            "hoverClass": "ui-state-hover"
                        },
                        "drag_opts": {
                            "revert": "invalid",
                            "helper": "clone",
                            "cursor": "move",
                            "appendTo": "#jqgrid_dnd",
                            "zIndex": 5e3
                        },
                        "dragcopy": !1,
                        "dropbyname": !1,
                        "droppos": "first",
                        "autoid": !0,
                        "autoidprefix": "dnd_"
                    }, a || {}), a.connectWith)
                    for (a.connectWith = a.connectWith.split(","), a.connectWith = b.map(a.connectWith, function(a) {
                            return b.trim(a)
                        }), b.data(f, "dnd", a), 0 !== f.p.reccount && !f.p.jqgdnd && c(), f.p.jqgdnd = !0, d = 0; d < a.connectWith.length; d++) e = a.connectWith[d], b(e).droppable(b.isFunction(a.drop) ? a.drop.call(b(f), a) : a.drop)
            })
        },
        "gridResize": function(a) {
            return this.each(function() {
                var d = this,
                    c = b.jgrid.jqID(d.p.id);
                d.grid && b.fn.resizable && (a = b.extend({}, a || {}), a.alsoResize ? (a._alsoResize_ = a.alsoResize, delete a.alsoResize) : a._alsoResize_ = !1, a.stop && b.isFunction(a.stop) ? (a._stop_ = a.stop, delete a.stop) : a._stop_ = !1, a.stop = function(e, f) {
                    b(d).jqGrid("setGridParam", {
                        "height": b("#gview_" + c + " .ui-jqgrid-bdiv").height()
                    }), b(d).jqGrid("setGridWidth", f.size.width, a.shrinkToFit), a._stop_ && a._stop_.call(d, e, f)
                }, a.alsoResize = a._alsoResize_ ? eval("(" + ("{'#gview_" + c + " .ui-jqgrid-bdiv':true,'" + a._alsoResize_ + "':true}") + ")") : b(".ui-jqgrid-bdiv", "#gview_" + c), delete a._alsoResize_, b("#gbox_" + c).resizable(a))
            })
        }
    })
}(jQuery);