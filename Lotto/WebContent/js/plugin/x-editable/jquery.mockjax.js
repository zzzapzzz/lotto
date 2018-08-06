/*!
 * MockJax - jQuery Plugin to Mock Ajax requests
 *
 * Version:  1.5.3
 * Released:
 * Home:   http://github.com/appendto/jquery-mockjax
 * Author:   Jonathan Sharp (http://jdsharp.com)
 * License:  MIT,GPL
 *
 * Copyright (c) 2011 appendTo LLC.
 * Dual licensed under the MIT or GPL licenses.
 * http://appendto.com/open-source-licenses
 */
(function(e) {
    function o(t) {
        if (window.DOMParser == undefined && window.ActiveXObject) {
            DOMParser = function() {};
            DOMParser.prototype.parseFromString = function(e) {
                var t = new ActiveXObject("Microsoft.XMLDOM");
                t.async = "false";
                t.loadXML(e);
                return t
            }
        }
        try {
            var n = (new DOMParser).parseFromString(t, "text/xml");
            if (!e.isXMLDoc(n)) throw "Unable to parse XML";
            var r = e("parsererror", n);
            if (r.length == 1) throw "Error: " + e(n).text();
            return n
        } catch (i) {
            var s = i.name == undefined ? i : i.name + ": " + i.message;
            e(document).trigger("xmlParseError", [s]);
            return undefined
        }
    }

    function u(t, n, r) {
        (t.context ? e(t.context) : e.event).trigger(n, r)
    }

    function a(t, n) {
        var r = !0;
        if (typeof n == "string") return e.isFunction(t.test) ? t.test(n) : t == n;
        e.each(t, function(i) {
            if (n[i] === undefined) {
                r = !1;
                return r
            }
            typeof n[i] == "object" && n[i] !== null ? r = r && a(t[i], n[i]) : t[i] && e.isFunction(t[i].test) ? r = r && t[i].test(n[i]) : r = r && t[i] == n[i]
        });
        return r
    }

    function f(t, n) {
        return t[n] === e.mockjaxSettings[n]
    }

    function l(t, n) {
        if (e.isFunction(t)) return t(n);
        if (e.isFunction(t.url.test)) {
            if (!t.url.test(n.url)) return null
        } else {
            var r = t.url.indexOf("*");
            if (t.url !== n.url && r === -1 || !(new RegExp(t.url.replace(/[-[\]{}()+?.,\\^$|#\s]/g, "\\$&").replace(/\*/g, ".+"))).test(n.url)) return null
        }
        return t.data && n.data && !a(t.data, n.data) ? null : t && t.type && t.type.toLowerCase() != n.type.toLowerCase() ? null : t
    }

    function c(n, r, i) {
        var s = function(t) {
            return function() {
                return function() {
                    var t;
                    this.status = n.status;
                    this.statusText = n.statusText;
                    this.readyState = 4;
                    e.isFunction(n.response) && n.response(i);
                    if (r.dataType == "json" && typeof n.responseText == "object") this.responseText = JSON.stringify(n.responseText);
                    else if (r.dataType == "xml")
                        if (typeof n.responseXML == "string") {
                            this.responseXML = o(n.responseXML);
                            this.responseText = n.responseXML
                        } else this.responseXML = n.responseXML;
                    else this.responseText = n.responseText;
                    if (typeof n.status == "number" || typeof n.status == "string") this.status = n.status;
                    typeof n.statusText == "string" && (this.statusText = n.statusText);
                    t = this.onreadystatechange || this.onload;
                    if (e.isFunction(t)) {
                        n.isTimeout && (this.status = -1);
                        t.call(this, n.isTimeout ? "timeout" : undefined)
                    } else n.isTimeout && (this.status = -1)
                }.apply(t)
            }
        }(this);
        n.proxy ? t({
            global: !1,
            url: n.proxy,
            type: n.proxyType,
            data: n.data,
            dataType: r.dataType === "script" ? "text/plain" : r.dataType,
            complete: function(e) {
                n.responseXML = e.responseXML;
                n.responseText = e.responseText;
                f(n, "status") && (n.status = e.status);
                f(n, "statusText") && (n.statusText = e.statusText);
                this.responseTimer = setTimeout(s, n.responseTime || 0)
            }
        }) : r.async === !1 ? s() : this.responseTimer = setTimeout(s, n.responseTime || 50)
    }

    function h(t, n, r, i) {
        t = e.extend(!0, {}, e.mockjaxSettings, t);
        typeof t.headers == "undefined" && (t.headers = {});
        t.contentType && (t.headers["content-type"] = t.contentType);
        return {
            status: t.status,
            statusText: t.statusText,
            readyState: 1,
            open: function() {},
            send: function() {
                i.fired = !0;
                c.call(this, t, n, r)
            },
            abort: function() {
                clearTimeout(this.responseTimer)
            },
            setRequestHeader: function(e, n) {
                t.headers[e] = n
            },
            getResponseHeader: function(e) {
                if (t.headers && t.headers[e]) return t.headers[e];
                if (e.toLowerCase() == "last-modified") return t.lastModified || (new Date).toString();
                if (e.toLowerCase() == "etag") return t.etag || "";
                if (e.toLowerCase() == "content-type") return t.contentType || "text/plain"
            },
            getAllResponseHeaders: function() {
                var n = "";
                e.each(t.headers, function(e, t) {
                    n += e + ": " + t + "\n"
                });
                return n
            }
        }
    }

    function p(e, t, n) {
        d(e);
        e.dataType = "json";
        if (e.data && i.test(e.data) || i.test(e.url)) {
            m(e, t, n);
            var r = /^(\w+:)?\/\/([^\/?#]+)/,
                s = r.exec(e.url),
                o = s && (s[1] && s[1] !== location.protocol || s[2] !== location.host);
            e.dataType = "script";
            if (e.type.toUpperCase() === "GET" && o) {
                var u = v(e, t, n);
                return u ? u : !0
            }
        }
        return null
    }

    function d(e) {
        if (e.type.toUpperCase() === "GET") i.test(e.url) || (e.url += (/\?/.test(e.url) ? "&" : "?") + (e.jsonp || "callback") + "=?");
        else if (!e.data || !i.test(e.data)) e.data = (e.data ? e.data + "&" : "") + (e.jsonp || "callback") + "=?"
    }

    function v(t, n, r) {
        var i = r && r.context || t,
            s = null;
        n.response && e.isFunction(n.response) ? n.response(r) : typeof n.responseText == "object" ? e.globalEval("(" + JSON.stringify(n.responseText) + ")") : e.globalEval("(" + n.responseText + ")");
        g(t, i, n);
        y(t, i, n);
        if (e.Deferred) {
            s = new e.Deferred;
            typeof n.responseText == "object" ? s.resolveWith(i, [n.responseText]) : s.resolveWith(i, [e.parseJSON(n.responseText)])
        }
        return s
    }

    function m(e, t, n) {
        var r = n && n.context || e,
            o = e.jsonpCallback || "jsonp" + s++;
        e.data && (e.data = (e.data + "").replace(i, "=" + o + "$1"));
        e.url = e.url.replace(i, "=" + o + "$1");
        window[o] = window[o] || function(n) {
            data = n;
            g(e, r, t);
            y(e, r, t);
            window[o] = undefined;
            try {
                delete window[o]
            } catch (i) {}
            head && head.removeChild(script)
        }
    }

    function g(e, t, n) {
        e.success && e.success.call(t, n.responseText || "", status, {});
        e.global && u(e, "ajaxSuccess", [{}, e])
    }

    function y(t, n) {
        t.complete && t.complete.call(n, {}, status);
        t.global && u("ajaxComplete", [{}, t]);
        t.global && !--e.active && e.event.trigger("ajaxStop")
    }

    function b(i, s) {
        var o, u, a;
        if (typeof i == "object") {
            s = i;
            i = undefined
        } else s.url = i;
        u = e.extend(!0, {}, e.ajaxSettings, s);
        for (var f = 0; f < n.length; f++) {
            if (!n[f]) continue;
            a = l(n[f], u);
            if (!a) continue;
            r.push(u);
            e.mockjaxSettings.log(a, u);
            if (u.dataType === "jsonp")
                if (o = p(u, a, s)) return o;
            a.cache = u.cache;
            a.timeout = u.timeout;
            a.global = u.global;
            w(a, s);
            (function(n, r, i, s) {
                o = t.call(e, e.extend(!0, {}, i, {
                    xhr: function() {
                        return h(n, r, i, s)
                    }
                }))
            })(a, u, s, n[f]);
            return o
        }
        if (e.mockjaxSettings.throwUnmocked === !0) throw "AJAX not mocked: " + s.url;
        return t.apply(e, [s])
    }

    function w(e, t) {
        if (!(e.url instanceof RegExp)) return;
        if (!e.hasOwnProperty("urlParams")) return;
        var n = e.url.exec(t.url);
        if (n.length === 1) return;
        n.shift();
        var r = 0,
            i = n.length,
            s = e.urlParams.length,
            o = Math.min(i, s),
            u = {};
        for (r; r < o; r++) {
            var a = e.urlParams[r];
            u[a] = n[r]
        }
        t.urlParams = u
    }
    var t = e.ajax,
        n = [],
        r = [],
        i = /=\?(&|$)/,
        s = (new Date).getTime();
    e.extend({
        ajax: b
    });
    e.mockjaxSettings = {
        log: function(t, n) {
            if (t.logging === !1 || typeof t.logging == "undefined" && e.mockjaxSettings.logging === !1) return;
            if (window.console && console.log) {
                var r = "MOCK " + n.type.toUpperCase() + ": " + n.url,
                    i = e.extend({}, n);
                if (typeof console.log == "function") console.log(r, i);
                else try {
                    console.log(r + " " + JSON.stringify(i))
                } catch (s) {
                    console.log(r)
                }
            }
        },
        logging: !0,
        status: 200,
        statusText: "OK",
        responseTime: 500,
        isTimeout: !1,
        throwUnmocked: !1,
        contentType: "text/plain",
        response: "",
        responseText: "",
        responseXML: "",
        proxy: "",
        proxyType: "GET",
        lastModified: null,
        etag: "",
        headers: {
            etag: "IJF@H#@923uf8023hFO@I#H#",
            "content-type": "text/plain"
        }
    };
    e.mockjax = function(e) {
        var t = n.length;
        n[t] = e;
        return t
    };
    e.mockjaxClear = function(e) {
        arguments.length == 1 ? n[e] = null : n = [];
        r = []
    };
    e.mockjax.handler = function(e) {
        if (arguments.length == 1) return n[e]
    };
    e.mockjax.mockedAjaxCalls = function() {
        return r
    }
})(jQuery);