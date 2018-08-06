/*! SmartAdmin - v1.5 - 2014-09-27 */ ! function(a) {
    "undefined" == typeof a.fn.each2 && a.extend(a.fn, {
        "each2": function(b) {
            for (var c = a([0]), d = -1, e = this.length; ++d < e && (c.context = c[0] = this[d]) && b.call(c[0], d, c) !== !1;);
            return this
        }
    })
}(jQuery),
function(a, b) {
    "use strict";

    function c(b) {
        var c = a(document.createTextNode(""));
        b.before(c), c.before(b), c.remove()
    }

    function d(a) {
        function b(a) {
            return O[a] || a
        }
        return a.replace(/[^\u0000-\u007E]/g, b)
    }

    function e(a, b) {
        for (var c = 0, d = b.length; d > c; c += 1)
            if (g(a, b[c])) return c;
        return -1
    }

    function f() {
        var b = a(N);
        b.appendTo("body");
        var c = {
            "width": b.width() - b[0].clientWidth,
            "height": b.height() - b[0].clientHeight
        };
        return b.remove(), c
    }

    function g(a, c) {
        return a === c ? !0 : a === b || c === b ? !1 : null === a || null === c ? !1 : a.constructor === String ? a + "" == c + "" : c.constructor === String ? c + "" == a + "" : !1
    }

    function h(b, c) {
        var d, e, f;
        if (null === b || b.length < 1) return [];
        for (d = b.split(c), e = 0, f = d.length; f > e; e += 1) d[e] = a.trim(d[e]);
        return d
    }

    function i(a) {
        return a.outerWidth(!1) - a.width()
    }

    function j(c) {
        var d = "keyup-change-value";
        c.on("keydown", function() {
            a.data(c, d) === b && a.data(c, d, c.val())
        }), c.on("keyup", function() {
            var e = a.data(c, d);
            e !== b && c.val() !== e && (a.removeData(c, d), c.trigger("keyup-change"))
        })
    }

    function k(c) {
        c.on("mousemove", function(c) {
            var d = M;
            (d === b || d.x !== c.pageX || d.y !== c.pageY) && a(c.target).trigger("mousemove-filtered", c)
        })
    }

    function l(a, c, d) {
        d = d || b;
        var e;
        return function() {
            var b = arguments;
            window.clearTimeout(e), e = window.setTimeout(function() {
                c.apply(d, b)
            }, a)
        }
    }

    function m(a, b) {
        var c = l(a, function(a) {
            b.trigger("scroll-debounced", a)
        });
        b.on("scroll", function(a) {
            e(a.target, b.get()) >= 0 && c(a)
        })
    }

    function n(a) {
        a[0] !== document.activeElement && window.setTimeout(function() {
            var b, c = a[0],
                d = a.val().length;
            a.focus();
            var e = c.offsetWidth > 0 || c.offsetHeight > 0;
            e && c === document.activeElement && (c.setSelectionRange ? c.setSelectionRange(d, d) : c.createTextRange && (b = c.createTextRange(), b.collapse(!1), b.select()))
        }, 0)
    }

    function o(b) {
        b = a(b)[0];
        var c = 0,
            d = 0;
        if ("selectionStart" in b) c = b.selectionStart, d = b.selectionEnd - c;
        else if ("selection" in document) {
            b.focus();
            var e = document.selection.createRange();
            d = document.selection.createRange().text.length, e.moveStart("character", -b.value.length), c = e.text.length - d
        }
        return {
            "offset": c,
            "length": d
        }
    }

    function p(a) {
        a.preventDefault(), a.stopPropagation()
    }

    function q(a) {
        a.preventDefault(), a.stopImmediatePropagation()
    }

    function r(b) {
        if (!J) {
            var c = b[0].currentStyle || window.getComputedStyle(b[0], null);
            J = a(document.createElement("div")).css({
                "position": "absolute",
                "left": "-10000px",
                "top": "-10000px",
                "display": "none",
                "fontSize": c.fontSize,
                "fontFamily": c.fontFamily,
                "fontStyle": c.fontStyle,
                "fontWeight": c.fontWeight,
                "letterSpacing": c.letterSpacing,
                "textTransform": c.textTransform,
                "whiteSpace": "nowrap"
            }), J.attr("class", "select2-sizer"), a("body").append(J)
        }
        return J.text(b.val()), J.width()
    }

    function s(b, c, d) {
        var e, f, g = [];
        e = a.trim(b.attr("class")), e && (e = "" + e, a(e.split(/\s+/)).each2(function() {
            0 === this.indexOf("select2-") && g.push(this)
        })), e = a.trim(c.attr("class")), e && (e = "" + e, a(e.split(/\s+/)).each2(function() {
            0 !== this.indexOf("select2-") && (f = d(this), f && g.push(f))
        })), b.attr("class", g.join(" "))
    }

    function t(a, b, c, e) {
        var f = d(a.toUpperCase()).indexOf(d(b.toUpperCase())),
            g = b.length;
        return 0 > f ? void c.push(e(a)) : (c.push(e(a.substring(0, f))), c.push("<span class='select2-match'>"), c.push(e(a.substring(f, f + g))), c.push("</span>"), void c.push(e(a.substring(f + g, a.length))))
    }

    function u(a) {
        var b = {
            "\\": "&#92;",
            "&": "&amp;",
            "<": "&lt;",
            ">": "&gt;",
            '"': "&quot;",
            "'": "&#39;",
            "/": "&#47;"
        };
        return String(a).replace(/[&<>"'\/\\]/g, function(a) {
            return b[a]
        })
    }

    function v(c) {
        var d, e = null,
            f = c.quietMillis || 100,
            g = c.url,
            h = this;
        return function(i) {
            window.clearTimeout(d), d = window.setTimeout(function() {
                var d = c.data,
                    f = g,
                    j = c.transport || a.fn.select2.ajaxDefaults.transport,
                    k = {
                        "type": c.type || "GET",
                        "cache": c.cache || !1,
                        "jsonpCallback": c.jsonpCallback || b,
                        "dataType": c.dataType || "json"
                    },
                    l = a.extend({}, a.fn.select2.ajaxDefaults.params, k);
                d = d ? d.call(h, i.term, i.page, i.context) : null, f = "function" == typeof f ? f.call(h, i.term, i.page, i.context) : f, e && "function" == typeof e.abort && e.abort(), c.params && (a.isFunction(c.params) ? a.extend(l, c.params.call(h)) : a.extend(l, c.params)), a.extend(l, {
                    "url": f,
                    "dataType": c.dataType,
                    "data": d,
                    "success": function(a) {
                        var b = c.results(a, i.page, i);
                        i.callback(b)
                    },
                    "error": function(a, b, c) {
                        var d = {
                            "hasError": !0,
                            "jqXHR": a,
                            "textStatus": b,
                            "errorThrown": c
                        };
                        i.callback(d)
                    }
                }), e = j.call(h, l)
            }, f)
        }
    }

    function w(b) {
        var c, d, e = b,
            f = function(a) {
                return "" + a.text
            };
        a.isArray(e) && (d = e, e = {
            "results": d
        }), a.isFunction(e) === !1 && (d = e, e = function() {
            return d
        });
        var g = e();
        return g.text && (f = g.text, a.isFunction(f) || (c = g.text, f = function(a) {
                return a[c]
            })),
            function(b) {
                var c, d = b.term,
                    g = {
                        "results": []
                    };
                return "" === d ? void b.callback(e()) : (c = function(e, g) {
                    var h, i;
                    if (e = e[0], e.children) {
                        h = {};
                        for (i in e) e.hasOwnProperty(i) && (h[i] = e[i]);
                        h.children = [], a(e.children).each2(function(a, b) {
                            c(b, h.children)
                        }), (h.children.length || b.matcher(d, f(h), e)) && g.push(h)
                    } else b.matcher(d, f(e), e) && g.push(e)
                }, a(e().results).each2(function(a, b) {
                    c(b, g.results)
                }), void b.callback(g))
            }
    }

    function x(c) {
        var d = a.isFunction(c);
        return function(e) {
            var f = e.term,
                g = {
                    "results": []
                },
                h = d ? c(e) : c;
            a.isArray(h) && (a(h).each(function() {
                var a = this.text !== b,
                    c = a ? this.text : this;
                ("" === f || e.matcher(f, c)) && g.results.push(a ? this : {
                    "id": this,
                    "text": this
                })
            }), e.callback(g))
        }
    }

    function y(b, c) {
        if (a.isFunction(b)) return !0;
        if (!b) return !1;
        if ("string" == typeof b) return !0;
        throw new Error(c + " must be a string, function, or falsy value")
    }

    function z(b, c) {
        if (a.isFunction(b)) {
            var d = Array.prototype.slice.call(arguments, 2);
            return b.apply(c, d)
        }
        return b
    }

    function A(b) {
        var c = 0;
        return a.each(b, function(a, b) {
            b.children ? c += A(b.children) : c++
        }), c
    }

    function B(a, c, d, e) {
        var f, h, i, j, k, l = a,
            m = !1;
        if (!e.createSearchChoice || !e.tokenSeparators || e.tokenSeparators.length < 1) return b;
        for (;;) {
            for (h = -1, i = 0, j = e.tokenSeparators.length; j > i && (k = e.tokenSeparators[i], h = a.indexOf(k), !(h >= 0)); i++);
            if (0 > h) break;
            if (f = a.substring(0, h), a = a.substring(h + k.length), f.length > 0 && (f = e.createSearchChoice.call(this, f, c), f !== b && null !== f && e.id(f) !== b && null !== e.id(f))) {
                for (m = !1, i = 0, j = c.length; j > i; i++)
                    if (g(e.id(f), e.id(c[i]))) {
                        m = !0;
                        break
                    }
                m || d(f)
            }
        }
        return l !== a ? a : void 0
    }

    function C() {
        var b = this;
        a.each(arguments, function(a, c) {
            b[c].remove(), b[c] = null
        })
    }

    function D(b, c) {
        var d = function() {};
        return d.prototype = new b, d.prototype.constructor = d, d.prototype.parent = b.prototype, d.prototype = a.extend(d.prototype, c), d
    }
    if (window.Select2 === b) {
        var E, F, G, H, I, J, K, L, M = {
                "x": 0,
                "y": 0
            },
            E = {
                "TAB": 9,
                "ENTER": 13,
                "ESC": 27,
                "SPACE": 32,
                "LEFT": 37,
                "UP": 38,
                "RIGHT": 39,
                "DOWN": 40,
                "SHIFT": 16,
                "CTRL": 17,
                "ALT": 18,
                "PAGE_UP": 33,
                "PAGE_DOWN": 34,
                "HOME": 36,
                "END": 35,
                "BACKSPACE": 8,
                "DELETE": 46,
                "isArrow": function(a) {
                    switch (a = a.which ? a.which : a) {
                        case E.LEFT:
                        case E.RIGHT:
                        case E.UP:
                        case E.DOWN:
                            return !0
                    }
                    return !1
                },
                "isControl": function(a) {
                    var b = a.which;
                    switch (b) {
                        case E.SHIFT:
                        case E.CTRL:
                        case E.ALT:
                            return !0
                    }
                    return a.metaKey ? !0 : !1
                },
                "isFunctionKey": function(a) {
                    return a = a.which ? a.which : a, a >= 112 && 123 >= a
                }
            },
            N = "<div class='select2-measure-scrollbar'></div>",
            O = {
                "\u24b6": "A",
                "\uff21": "A",
                "\xc0": "A",
                "\xc1": "A",
                "\xc2": "A",
                "\u1ea6": "A",
                "\u1ea4": "A",
                "\u1eaa": "A",
                "\u1ea8": "A",
                "\xc3": "A",
                "\u0100": "A",
                "\u0102": "A",
                "\u1eb0": "A",
                "\u1eae": "A",
                "\u1eb4": "A",
                "\u1eb2": "A",
                "\u0226": "A",
                "\u01e0": "A",
                "\xc4": "A",
                "\u01de": "A",
                "\u1ea2": "A",
                "\xc5": "A",
                "\u01fa": "A",
                "\u01cd": "A",
                "\u0200": "A",
                "\u0202": "A",
                "\u1ea0": "A",
                "\u1eac": "A",
                "\u1eb6": "A",
                "\u1e00": "A",
                "\u0104": "A",
                "\u023a": "A",
                "\u2c6f": "A",
                "\ua732": "AA",
                "\xc6": "AE",
                "\u01fc": "AE",
                "\u01e2": "AE",
                "\ua734": "AO",
                "\ua736": "AU",
                "\ua738": "AV",
                "\ua73a": "AV",
                "\ua73c": "AY",
                "\u24b7": "B",
                "\uff22": "B",
                "\u1e02": "B",
                "\u1e04": "B",
                "\u1e06": "B",
                "\u0243": "B",
                "\u0182": "B",
                "\u0181": "B",
                "\u24b8": "C",
                "\uff23": "C",
                "\u0106": "C",
                "\u0108": "C",
                "\u010a": "C",
                "\u010c": "C",
                "\xc7": "C",
                "\u1e08": "C",
                "\u0187": "C",
                "\u023b": "C",
                "\ua73e": "C",
                "\u24b9": "D",
                "\uff24": "D",
                "\u1e0a": "D",
                "\u010e": "D",
                "\u1e0c": "D",
                "\u1e10": "D",
                "\u1e12": "D",
                "\u1e0e": "D",
                "\u0110": "D",
                "\u018b": "D",
                "\u018a": "D",
                "\u0189": "D",
                "\ua779": "D",
                "\u01f1": "DZ",
                "\u01c4": "DZ",
                "\u01f2": "Dz",
                "\u01c5": "Dz",
                "\u24ba": "E",
                "\uff25": "E",
                "\xc8": "E",
                "\xc9": "E",
                "\xca": "E",
                "\u1ec0": "E",
                "\u1ebe": "E",
                "\u1ec4": "E",
                "\u1ec2": "E",
                "\u1ebc": "E",
                "\u0112": "E",
                "\u1e14": "E",
                "\u1e16": "E",
                "\u0114": "E",
                "\u0116": "E",
                "\xcb": "E",
                "\u1eba": "E",
                "\u011a": "E",
                "\u0204": "E",
                "\u0206": "E",
                "\u1eb8": "E",
                "\u1ec6": "E",
                "\u0228": "E",
                "\u1e1c": "E",
                "\u0118": "E",
                "\u1e18": "E",
                "\u1e1a": "E",
                "\u0190": "E",
                "\u018e": "E",
                "\u24bb": "F",
                "\uff26": "F",
                "\u1e1e": "F",
                "\u0191": "F",
                "\ua77b": "F",
                "\u24bc": "G",
                "\uff27": "G",
                "\u01f4": "G",
                "\u011c": "G",
                "\u1e20": "G",
                "\u011e": "G",
                "\u0120": "G",
                "\u01e6": "G",
                "\u0122": "G",
                "\u01e4": "G",
                "\u0193": "G",
                "\ua7a0": "G",
                "\ua77d": "G",
                "\ua77e": "G",
                "\u24bd": "H",
                "\uff28": "H",
                "\u0124": "H",
                "\u1e22": "H",
                "\u1e26": "H",
                "\u021e": "H",
                "\u1e24": "H",
                "\u1e28": "H",
                "\u1e2a": "H",
                "\u0126": "H",
                "\u2c67": "H",
                "\u2c75": "H",
                "\ua78d": "H",
                "\u24be": "I",
                "\uff29": "I",
                "\xcc": "I",
                "\xcd": "I",
                "\xce": "I",
                "\u0128": "I",
                "\u012a": "I",
                "\u012c": "I",
                "\u0130": "I",
                "\xcf": "I",
                "\u1e2e": "I",
                "\u1ec8": "I",
                "\u01cf": "I",
                "\u0208": "I",
                "\u020a": "I",
                "\u1eca": "I",
                "\u012e": "I",
                "\u1e2c": "I",
                "\u0197": "I",
                "\u24bf": "J",
                "\uff2a": "J",
                "\u0134": "J",
                "\u0248": "J",
                "\u24c0": "K",
                "\uff2b": "K",
                "\u1e30": "K",
                "\u01e8": "K",
                "\u1e32": "K",
                "\u0136": "K",
                "\u1e34": "K",
                "\u0198": "K",
                "\u2c69": "K",
                "\ua740": "K",
                "\ua742": "K",
                "\ua744": "K",
                "\ua7a2": "K",
                "\u24c1": "L",
                "\uff2c": "L",
                "\u013f": "L",
                "\u0139": "L",
                "\u013d": "L",
                "\u1e36": "L",
                "\u1e38": "L",
                "\u013b": "L",
                "\u1e3c": "L",
                "\u1e3a": "L",
                "\u0141": "L",
                "\u023d": "L",
                "\u2c62": "L",
                "\u2c60": "L",
                "\ua748": "L",
                "\ua746": "L",
                "\ua780": "L",
                "\u01c7": "LJ",
                "\u01c8": "Lj",
                "\u24c2": "M",
                "\uff2d": "M",
                "\u1e3e": "M",
                "\u1e40": "M",
                "\u1e42": "M",
                "\u2c6e": "M",
                "\u019c": "M",
                "\u24c3": "N",
                "\uff2e": "N",
                "\u01f8": "N",
                "\u0143": "N",
                "\xd1": "N",
                "\u1e44": "N",
                "\u0147": "N",
                "\u1e46": "N",
                "\u0145": "N",
                "\u1e4a": "N",
                "\u1e48": "N",
                "\u0220": "N",
                "\u019d": "N",
                "\ua790": "N",
                "\ua7a4": "N",
                "\u01ca": "NJ",
                "\u01cb": "Nj",
                "\u24c4": "O",
                "\uff2f": "O",
                "\xd2": "O",
                "\xd3": "O",
                "\xd4": "O",
                "\u1ed2": "O",
                "\u1ed0": "O",
                "\u1ed6": "O",
                "\u1ed4": "O",
                "\xd5": "O",
                "\u1e4c": "O",
                "\u022c": "O",
                "\u1e4e": "O",
                "\u014c": "O",
                "\u1e50": "O",
                "\u1e52": "O",
                "\u014e": "O",
                "\u022e": "O",
                "\u0230": "O",
                "\xd6": "O",
                "\u022a": "O",
                "\u1ece": "O",
                "\u0150": "O",
                "\u01d1": "O",
                "\u020c": "O",
                "\u020e": "O",
                "\u01a0": "O",
                "\u1edc": "O",
                "\u1eda": "O",
                "\u1ee0": "O",
                "\u1ede": "O",
                "\u1ee2": "O",
                "\u1ecc": "O",
                "\u1ed8": "O",
                "\u01ea": "O",
                "\u01ec": "O",
                "\xd8": "O",
                "\u01fe": "O",
                "\u0186": "O",
                "\u019f": "O",
                "\ua74a": "O",
                "\ua74c": "O",
                "\u01a2": "OI",
                "\ua74e": "OO",
                "\u0222": "OU",
                "\u24c5": "P",
                "\uff30": "P",
                "\u1e54": "P",
                "\u1e56": "P",
                "\u01a4": "P",
                "\u2c63": "P",
                "\ua750": "P",
                "\ua752": "P",
                "\ua754": "P",
                "\u24c6": "Q",
                "\uff31": "Q",
                "\ua756": "Q",
                "\ua758": "Q",
                "\u024a": "Q",
                "\u24c7": "R",
                "\uff32": "R",
                "\u0154": "R",
                "\u1e58": "R",
                "\u0158": "R",
                "\u0210": "R",
                "\u0212": "R",
                "\u1e5a": "R",
                "\u1e5c": "R",
                "\u0156": "R",
                "\u1e5e": "R",
                "\u024c": "R",
                "\u2c64": "R",
                "\ua75a": "R",
                "\ua7a6": "R",
                "\ua782": "R",
                "\u24c8": "S",
                "\uff33": "S",
                "\u1e9e": "S",
                "\u015a": "S",
                "\u1e64": "S",
                "\u015c": "S",
                "\u1e60": "S",
                "\u0160": "S",
                "\u1e66": "S",
                "\u1e62": "S",
                "\u1e68": "S",
                "\u0218": "S",
                "\u015e": "S",
                "\u2c7e": "S",
                "\ua7a8": "S",
                "\ua784": "S",
                "\u24c9": "T",
                "\uff34": "T",
                "\u1e6a": "T",
                "\u0164": "T",
                "\u1e6c": "T",
                "\u021a": "T",
                "\u0162": "T",
                "\u1e70": "T",
                "\u1e6e": "T",
                "\u0166": "T",
                "\u01ac": "T",
                "\u01ae": "T",
                "\u023e": "T",
                "\ua786": "T",
                "\ua728": "TZ",
                "\u24ca": "U",
                "\uff35": "U",
                "\xd9": "U",
                "\xda": "U",
                "\xdb": "U",
                "\u0168": "U",
                "\u1e78": "U",
                "\u016a": "U",
                "\u1e7a": "U",
                "\u016c": "U",
                "\xdc": "U",
                "\u01db": "U",
                "\u01d7": "U",
                "\u01d5": "U",
                "\u01d9": "U",
                "\u1ee6": "U",
                "\u016e": "U",
                "\u0170": "U",
                "\u01d3": "U",
                "\u0214": "U",
                "\u0216": "U",
                "\u01af": "U",
                "\u1eea": "U",
                "\u1ee8": "U",
                "\u1eee": "U",
                "\u1eec": "U",
                "\u1ef0": "U",
                "\u1ee4": "U",
                "\u1e72": "U",
                "\u0172": "U",
                "\u1e76": "U",
                "\u1e74": "U",
                "\u0244": "U",
                "\u24cb": "V",
                "\uff36": "V",
                "\u1e7c": "V",
                "\u1e7e": "V",
                "\u01b2": "V",
                "\ua75e": "V",
                "\u0245": "V",
                "\ua760": "VY",
                "\u24cc": "W",
                "\uff37": "W",
                "\u1e80": "W",
                "\u1e82": "W",
                "\u0174": "W",
                "\u1e86": "W",
                "\u1e84": "W",
                "\u1e88": "W",
                "\u2c72": "W",
                "\u24cd": "X",
                "\uff38": "X",
                "\u1e8a": "X",
                "\u1e8c": "X",
                "\u24ce": "Y",
                "\uff39": "Y",
                "\u1ef2": "Y",
                "\xdd": "Y",
                "\u0176": "Y",
                "\u1ef8": "Y",
                "\u0232": "Y",
                "\u1e8e": "Y",
                "\u0178": "Y",
                "\u1ef6": "Y",
                "\u1ef4": "Y",
                "\u01b3": "Y",
                "\u024e": "Y",
                "\u1efe": "Y",
                "\u24cf": "Z",
                "\uff3a": "Z",
                "\u0179": "Z",
                "\u1e90": "Z",
                "\u017b": "Z",
                "\u017d": "Z",
                "\u1e92": "Z",
                "\u1e94": "Z",
                "\u01b5": "Z",
                "\u0224": "Z",
                "\u2c7f": "Z",
                "\u2c6b": "Z",
                "\ua762": "Z",
                "\u24d0": "a",
                "\uff41": "a",
                "\u1e9a": "a",
                "\xe0": "a",
                "\xe1": "a",
                "\xe2": "a",
                "\u1ea7": "a",
                "\u1ea5": "a",
                "\u1eab": "a",
                "\u1ea9": "a",
                "\xe3": "a",
                "\u0101": "a",
                "\u0103": "a",
                "\u1eb1": "a",
                "\u1eaf": "a",
                "\u1eb5": "a",
                "\u1eb3": "a",
                "\u0227": "a",
                "\u01e1": "a",
                "\xe4": "a",
                "\u01df": "a",
                "\u1ea3": "a",
                "\xe5": "a",
                "\u01fb": "a",
                "\u01ce": "a",
                "\u0201": "a",
                "\u0203": "a",
                "\u1ea1": "a",
                "\u1ead": "a",
                "\u1eb7": "a",
                "\u1e01": "a",
                "\u0105": "a",
                "\u2c65": "a",
                "\u0250": "a",
                "\ua733": "aa",
                "\xe6": "ae",
                "\u01fd": "ae",
                "\u01e3": "ae",
                "\ua735": "ao",
                "\ua737": "au",
                "\ua739": "av",
                "\ua73b": "av",
                "\ua73d": "ay",
                "\u24d1": "b",
                "\uff42": "b",
                "\u1e03": "b",
                "\u1e05": "b",
                "\u1e07": "b",
                "\u0180": "b",
                "\u0183": "b",
                "\u0253": "b",
                "\u24d2": "c",
                "\uff43": "c",
                "\u0107": "c",
                "\u0109": "c",
                "\u010b": "c",
                "\u010d": "c",
                "\xe7": "c",
                "\u1e09": "c",
                "\u0188": "c",
                "\u023c": "c",
                "\ua73f": "c",
                "\u2184": "c",
                "\u24d3": "d",
                "\uff44": "d",
                "\u1e0b": "d",
                "\u010f": "d",
                "\u1e0d": "d",
                "\u1e11": "d",
                "\u1e13": "d",
                "\u1e0f": "d",
                "\u0111": "d",
                "\u018c": "d",
                "\u0256": "d",
                "\u0257": "d",
                "\ua77a": "d",
                "\u01f3": "dz",
                "\u01c6": "dz",
                "\u24d4": "e",
                "\uff45": "e",
                "\xe8": "e",
                "\xe9": "e",
                "\xea": "e",
                "\u1ec1": "e",
                "\u1ebf": "e",
                "\u1ec5": "e",
                "\u1ec3": "e",
                "\u1ebd": "e",
                "\u0113": "e",
                "\u1e15": "e",
                "\u1e17": "e",
                "\u0115": "e",
                "\u0117": "e",
                "\xeb": "e",
                "\u1ebb": "e",
                "\u011b": "e",
                "\u0205": "e",
                "\u0207": "e",
                "\u1eb9": "e",
                "\u1ec7": "e",
                "\u0229": "e",
                "\u1e1d": "e",
                "\u0119": "e",
                "\u1e19": "e",
                "\u1e1b": "e",
                "\u0247": "e",
                "\u025b": "e",
                "\u01dd": "e",
                "\u24d5": "f",
                "\uff46": "f",
                "\u1e1f": "f",
                "\u0192": "f",
                "\ua77c": "f",
                "\u24d6": "g",
                "\uff47": "g",
                "\u01f5": "g",
                "\u011d": "g",
                "\u1e21": "g",
                "\u011f": "g",
                "\u0121": "g",
                "\u01e7": "g",
                "\u0123": "g",
                "\u01e5": "g",
                "\u0260": "g",
                "\ua7a1": "g",
                "\u1d79": "g",
                "\ua77f": "g",
                "\u24d7": "h",
                "\uff48": "h",
                "\u0125": "h",
                "\u1e23": "h",
                "\u1e27": "h",
                "\u021f": "h",
                "\u1e25": "h",
                "\u1e29": "h",
                "\u1e2b": "h",
                "\u1e96": "h",
                "\u0127": "h",
                "\u2c68": "h",
                "\u2c76": "h",
                "\u0265": "h",
                "\u0195": "hv",
                "\u24d8": "i",
                "\uff49": "i",
                "\xec": "i",
                "\xed": "i",
                "\xee": "i",
                "\u0129": "i",
                "\u012b": "i",
                "\u012d": "i",
                "\xef": "i",
                "\u1e2f": "i",
                "\u1ec9": "i",
                "\u01d0": "i",
                "\u0209": "i",
                "\u020b": "i",
                "\u1ecb": "i",
                "\u012f": "i",
                "\u1e2d": "i",
                "\u0268": "i",
                "\u0131": "i",
                "\u24d9": "j",
                "\uff4a": "j",
                "\u0135": "j",
                "\u01f0": "j",
                "\u0249": "j",
                "\u24da": "k",
                "\uff4b": "k",
                "\u1e31": "k",
                "\u01e9": "k",
                "\u1e33": "k",
                "\u0137": "k",
                "\u1e35": "k",
                "\u0199": "k",
                "\u2c6a": "k",
                "\ua741": "k",
                "\ua743": "k",
                "\ua745": "k",
                "\ua7a3": "k",
                "\u24db": "l",
                "\uff4c": "l",
                "\u0140": "l",
                "\u013a": "l",
                "\u013e": "l",
                "\u1e37": "l",
                "\u1e39": "l",
                "\u013c": "l",
                "\u1e3d": "l",
                "\u1e3b": "l",
                "\u017f": "l",
                "\u0142": "l",
                "\u019a": "l",
                "\u026b": "l",
                "\u2c61": "l",
                "\ua749": "l",
                "\ua781": "l",
                "\ua747": "l",
                "\u01c9": "lj",
                "\u24dc": "m",
                "\uff4d": "m",
                "\u1e3f": "m",
                "\u1e41": "m",
                "\u1e43": "m",
                "\u0271": "m",
                "\u026f": "m",
                "\u24dd": "n",
                "\uff4e": "n",
                "\u01f9": "n",
                "\u0144": "n",
                "\xf1": "n",
                "\u1e45": "n",
                "\u0148": "n",
                "\u1e47": "n",
                "\u0146": "n",
                "\u1e4b": "n",
                "\u1e49": "n",
                "\u019e": "n",
                "\u0272": "n",
                "\u0149": "n",
                "\ua791": "n",
                "\ua7a5": "n",
                "\u01cc": "nj",
                "\u24de": "o",
                "\uff4f": "o",
                "\xf2": "o",
                "\xf3": "o",
                "\xf4": "o",
                "\u1ed3": "o",
                "\u1ed1": "o",
                "\u1ed7": "o",
                "\u1ed5": "o",
                "\xf5": "o",
                "\u1e4d": "o",
                "\u022d": "o",
                "\u1e4f": "o",
                "\u014d": "o",
                "\u1e51": "o",
                "\u1e53": "o",
                "\u014f": "o",
                "\u022f": "o",
                "\u0231": "o",
                "\xf6": "o",
                "\u022b": "o",
                "\u1ecf": "o",
                "\u0151": "o",
                "\u01d2": "o",
                "\u020d": "o",
                "\u020f": "o",
                "\u01a1": "o",
                "\u1edd": "o",
                "\u1edb": "o",
                "\u1ee1": "o",
                "\u1edf": "o",
                "\u1ee3": "o",
                "\u1ecd": "o",
                "\u1ed9": "o",
                "\u01eb": "o",
                "\u01ed": "o",
                "\xf8": "o",
                "\u01ff": "o",
                "\u0254": "o",
                "\ua74b": "o",
                "\ua74d": "o",
                "\u0275": "o",
                "\u01a3": "oi",
                "\u0223": "ou",
                "\ua74f": "oo",
                "\u24df": "p",
                "\uff50": "p",
                "\u1e55": "p",
                "\u1e57": "p",
                "\u01a5": "p",
                "\u1d7d": "p",
                "\ua751": "p",
                "\ua753": "p",
                "\ua755": "p",
                "\u24e0": "q",
                "\uff51": "q",
                "\u024b": "q",
                "\ua757": "q",
                "\ua759": "q",
                "\u24e1": "r",
                "\uff52": "r",
                "\u0155": "r",
                "\u1e59": "r",
                "\u0159": "r",
                "\u0211": "r",
                "\u0213": "r",
                "\u1e5b": "r",
                "\u1e5d": "r",
                "\u0157": "r",
                "\u1e5f": "r",
                "\u024d": "r",
                "\u027d": "r",
                "\ua75b": "r",
                "\ua7a7": "r",
                "\ua783": "r",
                "\u24e2": "s",
                "\uff53": "s",
                "\xdf": "s",
                "\u015b": "s",
                "\u1e65": "s",
                "\u015d": "s",
                "\u1e61": "s",
                "\u0161": "s",
                "\u1e67": "s",
                "\u1e63": "s",
                "\u1e69": "s",
                "\u0219": "s",
                "\u015f": "s",
                "\u023f": "s",
                "\ua7a9": "s",
                "\ua785": "s",
                "\u1e9b": "s",
                "\u24e3": "t",
                "\uff54": "t",
                "\u1e6b": "t",
                "\u1e97": "t",
                "\u0165": "t",
                "\u1e6d": "t",
                "\u021b": "t",
                "\u0163": "t",
                "\u1e71": "t",
                "\u1e6f": "t",
                "\u0167": "t",
                "\u01ad": "t",
                "\u0288": "t",
                "\u2c66": "t",
                "\ua787": "t",
                "\ua729": "tz",
                "\u24e4": "u",
                "\uff55": "u",
                "\xf9": "u",
                "\xfa": "u",
                "\xfb": "u",
                "\u0169": "u",
                "\u1e79": "u",
                "\u016b": "u",
                "\u1e7b": "u",
                "\u016d": "u",
                "\xfc": "u",
                "\u01dc": "u",
                "\u01d8": "u",
                "\u01d6": "u",
                "\u01da": "u",
                "\u1ee7": "u",
                "\u016f": "u",
                "\u0171": "u",
                "\u01d4": "u",
                "\u0215": "u",
                "\u0217": "u",
                "\u01b0": "u",
                "\u1eeb": "u",
                "\u1ee9": "u",
                "\u1eef": "u",
                "\u1eed": "u",
                "\u1ef1": "u",
                "\u1ee5": "u",
                "\u1e73": "u",
                "\u0173": "u",
                "\u1e77": "u",
                "\u1e75": "u",
                "\u0289": "u",
                "\u24e5": "v",
                "\uff56": "v",
                "\u1e7d": "v",
                "\u1e7f": "v",
                "\u028b": "v",
                "\ua75f": "v",
                "\u028c": "v",
                "\ua761": "vy",
                "\u24e6": "w",
                "\uff57": "w",
                "\u1e81": "w",
                "\u1e83": "w",
                "\u0175": "w",
                "\u1e87": "w",
                "\u1e85": "w",
                "\u1e98": "w",
                "\u1e89": "w",
                "\u2c73": "w",
                "\u24e7": "x",
                "\uff58": "x",
                "\u1e8b": "x",
                "\u1e8d": "x",
                "\u24e8": "y",
                "\uff59": "y",
                "\u1ef3": "y",
                "\xfd": "y",
                "\u0177": "y",
                "\u1ef9": "y",
                "\u0233": "y",
                "\u1e8f": "y",
                "\xff": "y",
                "\u1ef7": "y",
                "\u1e99": "y",
                "\u1ef5": "y",
                "\u01b4": "y",
                "\u024f": "y",
                "\u1eff": "y",
                "\u24e9": "z",
                "\uff5a": "z",
                "\u017a": "z",
                "\u1e91": "z",
                "\u017c": "z",
                "\u017e": "z",
                "\u1e93": "z",
                "\u1e95": "z",
                "\u01b6": "z",
                "\u0225": "z",
                "\u0240": "z",
                "\u2c6c": "z",
                "\ua763": "z",
                "\u0386": "\u0391",
                "\u0388": "\u0395",
                "\u0389": "\u0397",
                "\u038a": "\u0399",
                "\u03aa": "\u0399",
                "\u038c": "\u039f",
                "\u038e": "\u03a5",
                "\u03ab": "\u03a5",
                "\u038f": "\u03a9",
                "\u03ac": "\u03b1",
                "\u03ad": "\u03b5",
                "\u03ae": "\u03b7",
                "\u03af": "\u03b9",
                "\u03ca": "\u03b9",
                "\u0390": "\u03b9",
                "\u03cc": "\u03bf",
                "\u03cd": "\u03c5",
                "\u03cb": "\u03c5",
                "\u03b0": "\u03c5",
                "\u03c9": "\u03c9",
                "\u03c2": "\u03c3"
            };
        K = a(document), I = function() {
            var a = 1;
            return function() {
                return a++
            }
        }(), F = D(Object, {
            "bind": function(a) {
                var b = this;
                return function() {
                    a.apply(b, arguments)
                }
            },
            "init": function(c) {
                var d, e, g = ".select2-results";
                this.opts = c = this.prepareOpts(c), this.id = c.id, c.element.data("select2") !== b && null !== c.element.data("select2") && c.element.data("select2").destroy(), this.container = this.createContainer(), this.liveRegion = a("<span>", {
                    "role": "status",
                    "aria-live": "polite"
                }).addClass("select2-hidden-accessible").appendTo(document.body), this.containerId = "s2id_" + (c.element.attr("id") || "autogen" + I()), this.containerEventName = this.containerId.replace(/([.])/g, "_").replace(/([;&,\-\.\+\*\~':"\!\^#$%@\[\]\(\)=>\|])/g, "\\$1"), this.container.attr("id", this.containerId), this.container.attr("title", c.element.attr("title")), this.body = a("body"), s(this.container, this.opts.element, this.opts.adaptContainerCssClass), this.container.attr("style", c.element.attr("style")), this.container.css(z(c.containerCss, this.opts.element)), this.container.addClass(z(c.containerCssClass, this.opts.element)), this.elementTabIndex = this.opts.element.attr("tabindex"), this.opts.element.data("select2", this).attr("tabindex", "-1").before(this.container).on("click.select2", p), this.container.data("select2", this), this.dropdown = this.container.find(".select2-drop"), s(this.dropdown, this.opts.element, this.opts.adaptDropdownCssClass), this.dropdown.addClass(z(c.dropdownCssClass, this.opts.element)), this.dropdown.data("select2", this), this.dropdown.on("click", p), this.results = d = this.container.find(g), this.search = e = this.container.find("input.select2-input"), this.queryCount = 0, this.resultsPage = 0, this.context = null, this.initContainer(), this.container.on("click", p), k(this.results), this.dropdown.on("mousemove-filtered", g, this.bind(this.highlightUnderEvent)), this.dropdown.on("touchstart touchmove touchend", g, this.bind(function(a) {
                    this._touchEvent = !0, this.highlightUnderEvent(a)
                })), this.dropdown.on("touchmove", g, this.bind(this.touchMoved)), this.dropdown.on("touchstart touchend", g, this.bind(this.clearTouchMoved)), this.dropdown.on("click", this.bind(function() {
                    this._touchEvent && (this._touchEvent = !1, this.selectHighlighted())
                })), m(80, this.results), this.dropdown.on("scroll-debounced", g, this.bind(this.loadMoreIfNeeded)), a(this.container).on("change", ".select2-input", function(a) {
                    a.stopPropagation()
                }), a(this.dropdown).on("change", ".select2-input", function(a) {
                    a.stopPropagation()
                }), a.fn.mousewheel && d.mousewheel(function(a, b, c, e) {
                    var f = d.scrollTop();
                    e > 0 && 0 >= f - e ? (d.scrollTop(0), p(a)) : 0 > e && d.get(0).scrollHeight - d.scrollTop() + e <= d.height() && (d.scrollTop(d.get(0).scrollHeight - d.height()), p(a))
                }), j(e), e.on("keyup-change input paste", this.bind(this.updateResults)), e.on("focus", function() {
                    e.addClass("select2-focused")
                }), e.on("blur", function() {
                    e.removeClass("select2-focused")
                }), this.dropdown.on("mouseup", g, this.bind(function(b) {
                    a(b.target).closest(".select2-result-selectable").length > 0 && (this.highlightUnderEvent(b), this.selectHighlighted(b))
                })), this.dropdown.on("click mouseup mousedown touchstart touchend focusin", function(a) {
                    a.stopPropagation()
                }), this.nextSearchTerm = b, a.isFunction(this.opts.initSelection) && (this.initSelection(), this.monitorSource()), null !== c.maximumInputLength && this.search.attr("maxlength", c.maximumInputLength);
                var h = c.element.prop("disabled");
                h === b && (h = !1), this.enable(!h);
                var i = c.element.prop("readonly");
                i === b && (i = !1), this.readonly(i), L = L || f(), this.autofocus = c.element.prop("autofocus"), c.element.prop("autofocus", !1), this.autofocus && this.focus(), this.search.attr("placeholder", c.searchInputPlaceholder)
            },
            "destroy": function() {
                var a = this.opts.element,
                    c = a.data("select2"),
                    d = this;
                this.close(), a.length && a[0].detachEvent && a.each(function() {
                    this.detachEvent("onpropertychange", d._sync)
                }), this.propertyObserver && (this.propertyObserver.disconnect(), this.propertyObserver = null), this._sync = null, c !== b && (c.container.remove(), c.liveRegion.remove(), c.dropdown.remove(), a.removeClass("select2-offscreen").removeData("select2").off(".select2").prop("autofocus", this.autofocus || !1), this.elementTabIndex ? a.attr({
                    "tabindex": this.elementTabIndex
                }) : a.removeAttr("tabindex"), a.show()), C.call(this, "container", "liveRegion", "dropdown", "results", "search")
            },
            "optionToData": function(a) {
                return a.is("option") ? {
                    "id": a.prop("value"),
                    "text": a.text(),
                    "element": a.get(),
                    "css": a.attr("class"),
                    "disabled": a.prop("disabled"),
                    "locked": g(a.attr("locked"), "locked") || g(a.data("locked"), !0)
                } : a.is("optgroup") ? {
                    "text": a.attr("label"),
                    "children": [],
                    "element": a.get(),
                    "css": a.attr("class")
                } : void 0
            },
            "prepareOpts": function(c) {
                var d, e, f, i, j = this;
                if (d = c.element, "select" === d.get(0).tagName.toLowerCase() && (this.select = e = c.element), e && a.each(["id", "multiple", "ajax", "query", "createSearchChoice", "initSelection", "data", "tags"], function() {
                        if (this in c) throw new Error("Option '" + this + "' is not allowed for Select2 when attached to a <select> element.")
                    }), c = a.extend({}, {
                        "populateResults": function(d, e, f) {
                            var g, h = this.opts.id,
                                i = this.liveRegion;
                            (g = function(d, e, k) {
                                var l, m, n, o, p, q, r, s, t, u;
                                d = c.sortResults(d, e, f);
                                var v = [];
                                for (l = 0, m = d.length; m > l; l += 1) n = d[l], p = n.disabled === !0, o = !p && h(n) !== b, q = n.children && n.children.length > 0, r = a("<li></li>"), r.addClass("select2-results-dept-" + k), r.addClass("select2-result"), r.addClass(o ? "select2-result-selectable" : "select2-result-unselectable"), p && r.addClass("select2-disabled"), q && r.addClass("select2-result-with-children"), r.addClass(j.opts.formatResultCssClass(n)), r.attr("role", "presentation"), s = a(document.createElement("div")), s.addClass("select2-result-label"), s.attr("id", "select2-result-label-" + I()), s.attr("role", "option"), u = c.formatResult(n, s, f, j.opts.escapeMarkup), u !== b && (s.html(u), r.append(s)), q && (t = a("<ul></ul>"), t.addClass("select2-result-sub"), g(n.children, t, k + 1), r.append(t)), r.data("select2-data", n), v.push(r[0]);
                                e.append(v), i.text(c.formatMatches(d.length))
                            })(e, d, 0)
                        }
                    }, a.fn.select2.defaults, c), "function" != typeof c.id && (f = c.id, c.id = function(a) {
                        return a[f]
                    }), a.isArray(c.element.data("select2Tags"))) {
                    if ("tags" in c) throw "tags specified as both an attribute 'data-select2-tags' and in options of Select2 " + c.element.attr("id");
                    c.tags = c.element.data("select2Tags")
                }
                if (e ? (c.query = this.bind(function(a) {
                        var c, e, f, g = {
                                "results": [],
                                "more": !1
                            },
                            h = a.term;
                        f = function(b, c) {
                            var d;
                            b.is("option") ? a.matcher(h, b.text(), b) && c.push(j.optionToData(b)) : b.is("optgroup") && (d = j.optionToData(b), b.children().each2(function(a, b) {
                                f(b, d.children)
                            }), d.children.length > 0 && c.push(d))
                        }, c = d.children(), this.getPlaceholder() !== b && c.length > 0 && (e = this.getPlaceholderOption(), e && (c = c.not(e))), c.each2(function(a, b) {
                            f(b, g.results)
                        }), a.callback(g)
                    }), c.id = function(a) {
                        return a.id
                    }) : "query" in c || ("ajax" in c ? (i = c.element.data("ajax-url"), i && i.length > 0 && (c.ajax.url = i), c.query = v.call(c.element, c.ajax)) : "data" in c ? c.query = w(c.data) : "tags" in c && (c.query = x(c.tags), c.createSearchChoice === b && (c.createSearchChoice = function(b) {
                        return {
                            "id": a.trim(b),
                            "text": a.trim(b)
                        }
                    }), c.initSelection === b && (c.initSelection = function(b, d) {
                        var e = [];
                        a(h(b.val(), c.separator)).each(function() {
                            var b = {
                                    "id": this,
                                    "text": this
                                },
                                d = c.tags;
                            a.isFunction(d) && (d = d()), a(d).each(function() {
                                return g(this.id, b.id) ? (b = this, !1) : void 0
                            }), e.push(b)
                        }), d(e)
                    }))), "function" != typeof c.query) throw "query function not defined for Select2 " + c.element.attr("id");
                if ("top" === c.createSearchChoicePosition) c.createSearchChoicePosition = function(a, b) {
                    a.unshift(b)
                };
                else if ("bottom" === c.createSearchChoicePosition) c.createSearchChoicePosition = function(a, b) {
                    a.push(b)
                };
                else if ("function" != typeof c.createSearchChoicePosition) throw "invalid createSearchChoicePosition option must be 'top', 'bottom' or a custom function";
                return c
            },
            "monitorSource": function() {
                var c, d = this.opts.element,
                    e = this;
                d.on("change.select2", this.bind(function() {
                    this.opts.element.data("select2-change-triggered") !== !0 && this.initSelection()
                })), this._sync = this.bind(function() {
                    var a = d.prop("disabled");
                    a === b && (a = !1), this.enable(!a);
                    var c = d.prop("readonly");
                    c === b && (c = !1), this.readonly(c), s(this.container, this.opts.element, this.opts.adaptContainerCssClass), this.container.addClass(z(this.opts.containerCssClass, this.opts.element)), s(this.dropdown, this.opts.element, this.opts.adaptDropdownCssClass), this.dropdown.addClass(z(this.opts.dropdownCssClass, this.opts.element))
                }), d.length && d[0].attachEvent && d.each(function() {
                    this.attachEvent("onpropertychange", e._sync)
                }), c = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver, c !== b && (this.propertyObserver && (delete this.propertyObserver, this.propertyObserver = null), this.propertyObserver = new c(function(b) {
                    a.each(b, e._sync)
                }), this.propertyObserver.observe(d.get(0), {
                    "attributes": !0,
                    "subtree": !1
                }))
            },
            "triggerSelect": function(b) {
                var c = a.Event("select2-selecting", {
                    "val": this.id(b),
                    "object": b,
                    "choice": b
                });
                return this.opts.element.trigger(c), !c.isDefaultPrevented()
            },
            "triggerChange": function(b) {
                b = b || {}, b = a.extend({}, b, {
                    "type": "change",
                    "val": this.val()
                }), this.opts.element.data("select2-change-triggered", !0), this.opts.element.trigger(b), this.opts.element.data("select2-change-triggered", !1), this.opts.element.click(), this.opts.blurOnChange && this.opts.element.blur()
            },
            "isInterfaceEnabled": function() {
                return this.enabledInterface === !0
            },
            "enableInterface": function() {
                var a = this._enabled && !this._readonly,
                    b = !a;
                return a === this.enabledInterface ? !1 : (this.container.toggleClass("select2-container-disabled", b), this.close(), this.enabledInterface = a, !0)
            },
            "enable": function(a) {
                a === b && (a = !0), this._enabled !== a && (this._enabled = a, this.opts.element.prop("disabled", !a), this.enableInterface())
            },
            "disable": function() {
                this.enable(!1)
            },
            "readonly": function(a) {
                a === b && (a = !1), this._readonly !== a && (this._readonly = a, this.opts.element.prop("readonly", a), this.enableInterface())
            },
            "opened": function() {
                return this.container ? this.container.hasClass("select2-dropdown-open") : !1
            },
            "positionDropdown": function() {
                var b, c, d, e, f, g = this.dropdown,
                    h = this.container.offset(),
                    i = this.container.outerHeight(!1),
                    j = this.container.outerWidth(!1),
                    k = g.outerHeight(!1),
                    l = a(window),
                    m = l.width(),
                    n = l.height(),
                    o = l.scrollLeft() + m,
                    p = l.scrollTop() + n,
                    q = h.top + i,
                    r = h.left,
                    s = p >= q + k,
                    t = h.top - k >= l.scrollTop(),
                    u = g.outerWidth(!1),
                    v = o >= r + u,
                    w = g.hasClass("select2-drop-above");
                w ? (c = !0, !t && s && (d = !0, c = !1)) : (c = !1, !s && t && (d = !0, c = !0)), d && (g.hide(), h = this.container.offset(), i = this.container.outerHeight(!1), j = this.container.outerWidth(!1), k = g.outerHeight(!1), o = l.scrollLeft() + m, p = l.scrollTop() + n, q = h.top + i, r = h.left, u = g.outerWidth(!1), v = o >= r + u, g.show(), this.focusSearch()), this.opts.dropdownAutoWidth ? (f = a(".select2-results", g)[0], g.addClass("select2-drop-auto-width"), g.css("width", ""), u = g.outerWidth(!1) + (f.scrollHeight === f.clientHeight ? 0 : L.width), u > j ? j = u : u = j, k = g.outerHeight(!1), v = o >= r + u) : this.container.removeClass("select2-drop-auto-width"), "static" !== this.body.css("position") && (b = this.body.offset(), q -= b.top, r -= b.left), v || (r = h.left + this.container.outerWidth(!1) - u), e = {
                    "left": r,
                    "width": j
                }, c ? (e.top = h.top - k, e.bottom = "auto", this.container.addClass("select2-drop-above"), g.addClass("select2-drop-above")) : (e.top = q, e.bottom = "auto", this.container.removeClass("select2-drop-above"), g.removeClass("select2-drop-above")), e = a.extend(e, z(this.opts.dropdownCss, this.opts.element)), g.css(e)
            },
            "shouldOpen": function() {
                var b;
                return this.opened() ? !1 : this._enabled === !1 || this._readonly === !0 ? !1 : (b = a.Event("select2-opening"), this.opts.element.trigger(b), !b.isDefaultPrevented())
            },
            "clearDropdownAlignmentPreference": function() {
                this.container.removeClass("select2-drop-above"), this.dropdown.removeClass("select2-drop-above")
            },
            "open": function() {
                return this.shouldOpen() ? (this.opening(), K.on("mousemove.select2Event", function(a) {
                    M.x = a.pageX, M.y = a.pageY
                }), !0) : !1
            },
            "opening": function() {
                var b, d = this.containerEventName,
                    e = "scroll." + d,
                    f = "resize." + d,
                    g = "orientationchange." + d;
                this.container.addClass("select2-dropdown-open").addClass("select2-container-active"), this.clearDropdownAlignmentPreference(), this.dropdown[0] !== this.body.children().last()[0] && this.dropdown.detach().appendTo(this.body), b = a("#select2-drop-mask"), 0 == b.length && (b = a(document.createElement("div")), b.attr("id", "select2-drop-mask").attr("class", "select2-drop-mask"), b.hide(), b.appendTo(this.body), b.on("mousedown touchstart click", function(d) {
                    c(b);
                    var e, f = a("#select2-drop");
                    f.length > 0 && (e = f.data("select2"), e.opts.selectOnBlur && e.selectHighlighted({
                        "noFocus": !0
                    }), e.close(), d.preventDefault(), d.stopPropagation())
                })), this.dropdown.prev()[0] !== b[0] && this.dropdown.before(b), a("#select2-drop").removeAttr("id"), this.dropdown.attr("id", "select2-drop"), b.show(), this.positionDropdown(), this.dropdown.show(), this.positionDropdown(), this.dropdown.addClass("select2-drop-active");
                var h = this;
                this.container.parents().add(window).each(function() {
                    a(this).on(f + " " + e + " " + g, function() {
                        h.opened() && h.positionDropdown()
                    })
                })
            },
            "close": function() {
                if (this.opened()) {
                    var b = this.containerEventName,
                        c = "scroll." + b,
                        d = "resize." + b,
                        e = "orientationchange." + b;
                    this.container.parents().add(window).each(function() {
                        a(this).off(c).off(d).off(e)
                    }), this.clearDropdownAlignmentPreference(), a("#select2-drop-mask").hide(), this.dropdown.removeAttr("id"), this.dropdown.hide(), this.container.removeClass("select2-dropdown-open").removeClass("select2-container-active"), this.results.empty(), K.off("mousemove.select2Event"), this.clearSearch(), this.search.removeClass("select2-active"), this.opts.element.trigger(a.Event("select2-close"))
                }
            },
            "externalSearch": function(a) {
                this.open(), this.search.val(a), this.updateResults(!1)
            },
            "clearSearch": function() {},
            "getMaximumSelectionSize": function() {
                return z(this.opts.maximumSelectionSize, this.opts.element)
            },
            "ensureHighlightVisible": function() {
                var b, c, d, e, f, g, h, i, j = this.results;
                if (c = this.highlight(), !(0 > c)) {
                    if (0 == c) return void j.scrollTop(0);
                    b = this.findHighlightableChoices().find(".select2-result-label"), d = a(b[c]), i = (d.offset() || {}).top || 0, e = i + d.outerHeight(!0), c === b.length - 1 && (h = j.find("li.select2-more-results"), h.length > 0 && (e = h.offset().top + h.outerHeight(!0))), f = j.offset().top + j.outerHeight(!0), e > f && j.scrollTop(j.scrollTop() + (e - f)), g = i - j.offset().top, 0 > g && "none" != d.css("display") && j.scrollTop(j.scrollTop() + g)
                }
            },
            "findHighlightableChoices": function() {
                return this.results.find(".select2-result-selectable:not(.select2-disabled):not(.select2-selected)")
            },
            "moveHighlight": function(b) {
                for (var c = this.findHighlightableChoices(), d = this.highlight(); d > -1 && d < c.length;) {
                    d += b;
                    var e = a(c[d]);
                    if (e.hasClass("select2-result-selectable") && !e.hasClass("select2-disabled") && !e.hasClass("select2-selected")) {
                        this.highlight(d);
                        break
                    }
                }
            },
            "highlight": function(b) {
                var c, d, f = this.findHighlightableChoices();
                return 0 === arguments.length ? e(f.filter(".select2-highlighted")[0], f.get()) : (b >= f.length && (b = f.length - 1), 0 > b && (b = 0), this.removeHighlight(), c = a(f[b]), c.addClass("select2-highlighted"), this.search.attr("aria-activedescendant", c.find(".select2-result-label").attr("id")), this.ensureHighlightVisible(), this.liveRegion.text(c.text()), d = c.data("select2-data"), void(d && this.opts.element.trigger({
                    "type": "select2-highlight",
                    "val": this.id(d),
                    "choice": d
                })))
            },
            "removeHighlight": function() {
                this.results.find(".select2-highlighted").removeClass("select2-highlighted")
            },
            "touchMoved": function() {
                this._touchMoved = !0
            },
            "clearTouchMoved": function() {
                this._touchMoved = !1
            },
            "countSelectableResults": function() {
                return this.findHighlightableChoices().length
            },
            "highlightUnderEvent": function(b) {
                var c = a(b.target).closest(".select2-result-selectable");
                if (c.length > 0 && !c.is(".select2-highlighted")) {
                    var d = this.findHighlightableChoices();
                    this.highlight(d.index(c))
                } else 0 == c.length && this.removeHighlight()
            },
            "loadMoreIfNeeded": function() {
                var a, b = this.results,
                    c = b.find("li.select2-more-results"),
                    d = this.resultsPage + 1,
                    e = this,
                    f = this.search.val(),
                    g = this.context;
                0 !== c.length && (a = c.offset().top - b.offset().top - b.height(), a <= this.opts.loadMorePadding && (c.addClass("select2-active"), this.opts.query({
                    "element": this.opts.element,
                    "term": f,
                    "page": d,
                    "context": g,
                    "matcher": this.opts.matcher,
                    "callback": this.bind(function(a) {
                        e.opened() && (e.opts.populateResults.call(this, b, a.results, {
                            "term": f,
                            "page": d,
                            "context": g
                        }), e.postprocessResults(a, !1, !1), a.more === !0 ? (c.detach().appendTo(b).text(z(e.opts.formatLoadMore, e.opts.element, d + 1)), window.setTimeout(function() {
                            e.loadMoreIfNeeded()
                        }, 10)) : c.remove(), e.positionDropdown(), e.resultsPage = d, e.context = a.context, this.opts.element.trigger({
                            "type": "select2-loaded",
                            "items": a
                        }))
                    })
                })))
            },
            "tokenize": function() {},
            "updateResults": function(c) {
                function d() {
                    j.removeClass("select2-active"), m.positionDropdown(), m.liveRegion.text(k.find(".select2-no-results,.select2-selection-limit,.select2-searching").length ? k.text() : m.opts.formatMatches(k.find(".select2-result-selectable").length))
                }

                function e(a) {
                    k.html(a), d()
                }
                var f, h, i, j = this.search,
                    k = this.results,
                    l = this.opts,
                    m = this,
                    n = j.val(),
                    o = a.data(this.container, "select2-last-term");
                if ((c === !0 || !o || !g(n, o)) && (a.data(this.container, "select2-last-term", n), c === !0 || this.showSearchInput !== !1 && this.opened())) {
                    i = ++this.queryCount;
                    var p = this.getMaximumSelectionSize();
                    if (p >= 1 && (f = this.data(), a.isArray(f) && f.length >= p && y(l.formatSelectionTooBig, "formatSelectionTooBig"))) return void e("<li class='select2-selection-limit'>" + z(l.formatSelectionTooBig, l.element, p) + "</li>");
                    if (j.val().length < l.minimumInputLength) return e(y(l.formatInputTooShort, "formatInputTooShort") ? "<li class='select2-no-results'>" + z(l.formatInputTooShort, l.element, j.val(), l.minimumInputLength) + "</li>" : ""), void(c && this.showSearch && this.showSearch(!0));
                    if (l.maximumInputLength && j.val().length > l.maximumInputLength) return void e(y(l.formatInputTooLong, "formatInputTooLong") ? "<li class='select2-no-results'>" + z(l.formatInputTooLong, l.element, j.val(), l.maximumInputLength) + "</li>" : "");
                    l.formatSearching && 0 === this.findHighlightableChoices().length && e("<li class='select2-searching'>" + z(l.formatSearching, l.element) + "</li>"), j.addClass("select2-active"), this.removeHighlight(), h = this.tokenize(), h != b && null != h && j.val(h), this.resultsPage = 1, l.query({
                        "element": l.element,
                        "term": j.val(),
                        "page": this.resultsPage,
                        "context": null,
                        "matcher": l.matcher,
                        "callback": this.bind(function(f) {
                            var h;
                            if (i == this.queryCount) {
                                if (!this.opened()) return void this.search.removeClass("select2-active");
                                if (f.hasError !== b && y(l.formatAjaxError, "formatAjaxError")) return void e("<li class='select2-ajax-error'>" + z(l.formatAjaxError, l.element, f.jqXHR, f.textStatus, f.errorThrown) + "</li>");
                                if (this.context = f.context === b ? null : f.context, this.opts.createSearchChoice && "" !== j.val() && (h = this.opts.createSearchChoice.call(m, j.val(), f.results), h !== b && null !== h && m.id(h) !== b && null !== m.id(h) && 0 === a(f.results).filter(function() {
                                        return g(m.id(this), m.id(h))
                                    }).length && this.opts.createSearchChoicePosition(f.results, h)), 0 === f.results.length && y(l.formatNoMatches, "formatNoMatches")) return void e("<li class='select2-no-results'>" + z(l.formatNoMatches, l.element, j.val()) + "</li>");
                                k.empty(), m.opts.populateResults.call(this, k, f.results, {
                                    "term": j.val(),
                                    "page": this.resultsPage,
                                    "context": null
                                }), f.more === !0 && y(l.formatLoadMore, "formatLoadMore") && (k.append("<li class='select2-more-results'>" + l.escapeMarkup(z(l.formatLoadMore, l.element, this.resultsPage)) + "</li>"), window.setTimeout(function() {
                                    m.loadMoreIfNeeded()
                                }, 10)), this.postprocessResults(f, c), d(), this.opts.element.trigger({
                                    "type": "select2-loaded",
                                    "items": f
                                })
                            }
                        })
                    })
                }
            },
            "cancel": function() {
                this.close()
            },
            "blur": function() {
                this.opts.selectOnBlur && this.selectHighlighted({
                    "noFocus": !0
                }), this.close(), this.container.removeClass("select2-container-active"), this.search[0] === document.activeElement && this.search.blur(), this.clearSearch(), this.selection.find(".select2-search-choice-focus").removeClass("select2-search-choice-focus")
            },
            "focusSearch": function() {
                n(this.search)
            },
            "selectHighlighted": function(a) {
                if (this._touchMoved) return void this.clearTouchMoved();
                var b = this.highlight(),
                    c = this.results.find(".select2-highlighted"),
                    d = c.closest(".select2-result").data("select2-data");
                d ? (this.highlight(b), this.onSelect(d, a)) : a && a.noFocus && this.close()
            },
            "getPlaceholder": function() {
                var a;
                return this.opts.element.attr("placeholder") || this.opts.element.attr("data-placeholder") || this.opts.element.data("placeholder") || this.opts.placeholder || ((a = this.getPlaceholderOption()) !== b ? a.text() : b)
            },
            "getPlaceholderOption": function() {
                if (this.select) {
                    var c = this.select.children("option").first();
                    if (this.opts.placeholderOption !== b) return "first" === this.opts.placeholderOption && c || "function" == typeof this.opts.placeholderOption && this.opts.placeholderOption(this.select);
                    if ("" === a.trim(c.text()) && "" === c.val()) return c
                }
            },
            "initContainerWidth": function() {
                function c() {
                    var c, d, e, f, g, h;
                    if ("off" === this.opts.width) return null;
                    if ("element" === this.opts.width) return 0 === this.opts.element.outerWidth(!1) ? "auto" : this.opts.element.outerWidth(!1) + "px";
                    if ("copy" === this.opts.width || "resolve" === this.opts.width) {
                        if (c = this.opts.element.attr("style"), c !== b)
                            for (d = c.split(";"), f = 0, g = d.length; g > f; f += 1)
                                if (h = d[f].replace(/\s/g, ""), e = h.match(/^width:(([-+]?([0-9]*\.)?[0-9]+)(px|em|ex|%|in|cm|mm|pt|pc))/i), null !== e && e.length >= 1) return e[1];
                        return "resolve" === this.opts.width ? (c = this.opts.element.css("width"), c.indexOf("%") > 0 ? c : 0 === this.opts.element.outerWidth(!1) ? "auto" : this.opts.element.outerWidth(!1) + "px") : null
                    }
                    return a.isFunction(this.opts.width) ? this.opts.width() : this.opts.width
                }
                var d = c.call(this);
                null !== d && this.container.css("width", d)
            }
        }), G = D(F, {
            "createContainer": function() {
                var b = a(document.createElement("div")).attr({
                    "class": "select2-container"
                }).html(["<a href='javascript:void(0)' class='select2-choice' tabindex='-1'>", "   <span class='select2-chosen'>&#160;</span><abbr class='select2-search-choice-close'></abbr>", "   <span class='select2-arrow' role='presentation'><b role='presentation'></b></span>", "</a>", "<label for='' class='select2-offscreen'></label>", "<input class='select2-focusser select2-offscreen' type='text' aria-haspopup='true' role='button' />", "<div class='select2-drop select2-display-none'>", "   <div class='select2-search'>", "       <label for='' class='select2-offscreen'></label>", "       <input type='text' autocomplete='off' autocorrect='off' autocapitalize='off' spellcheck='false' class='select2-input' role='combobox' aria-expanded='true'", "       aria-autocomplete='list' />", "   </div>", "   <ul class='select2-results' role='listbox'>", "   </ul>", "</div>"].join(""));
                return b
            },
            "enableInterface": function() {
                this.parent.enableInterface.apply(this, arguments) && this.focusser.prop("disabled", !this.isInterfaceEnabled())
            },
            "opening": function() {
                var c, d, e;
                this.opts.minimumResultsForSearch >= 0 && this.showSearch(!0), this.parent.opening.apply(this, arguments), this.showSearchInput !== !1 && this.search.val(this.focusser.val()), this.opts.shouldFocusInput(this) && (this.search.focus(), c = this.search.get(0), c.createTextRange ? (d = c.createTextRange(), d.collapse(!1), d.select()) : c.setSelectionRange && (e = this.search.val().length, c.setSelectionRange(e, e))), "" === this.search.val() && this.nextSearchTerm != b && (this.search.val(this.nextSearchTerm), this.search.select()), this.focusser.prop("disabled", !0).val(""), this.updateResults(!0), this.opts.element.trigger(a.Event("select2-open"))
            },
            "close": function() {
                this.opened() && (this.parent.close.apply(this, arguments), this.focusser.prop("disabled", !1), this.opts.shouldFocusInput(this) && this.focusser.focus())
            },
            "focus": function() {
                this.opened() ? this.close() : (this.focusser.prop("disabled", !1), this.opts.shouldFocusInput(this) && this.focusser.focus())
            },
            "isFocused": function() {
                return this.container.hasClass("select2-container-active")
            },
            "cancel": function() {
                this.parent.cancel.apply(this, arguments), this.focusser.prop("disabled", !1), this.opts.shouldFocusInput(this) && this.focusser.focus()
            },
            "destroy": function() {
                a("label[for='" + this.focusser.attr("id") + "']").attr("for", this.opts.element.attr("id")), this.parent.destroy.apply(this, arguments), C.call(this, "selection", "focusser")
            },
            "initContainer": function() {
                var b, d, e = this.container,
                    f = this.dropdown,
                    g = I();
                this.showSearch(this.opts.minimumResultsForSearch < 0 ? !1 : !0), this.selection = b = e.find(".select2-choice"), this.focusser = e.find(".select2-focusser"), b.find(".select2-chosen").attr("id", "select2-chosen-" + g), this.focusser.attr("aria-labelledby", "select2-chosen-" + g), this.results.attr("id", "select2-results-" + g), this.search.attr("aria-owns", "select2-results-" + g), this.focusser.attr("id", "s2id_autogen" + g), d = a("label[for='" + this.opts.element.attr("id") + "']"), this.focusser.prev().text(d.text()).attr("for", this.focusser.attr("id"));
                var h = this.opts.element.attr("title");
                this.opts.element.attr("title", h || d.text()), this.focusser.attr("tabindex", this.elementTabIndex), this.search.attr("id", this.focusser.attr("id") + "_search"), this.search.prev().text(a("label[for='" + this.focusser.attr("id") + "']").text()).attr("for", this.search.attr("id")), this.search.on("keydown", this.bind(function(a) {
                    if (this.isInterfaceEnabled() && 229 != a.keyCode) {
                        if (a.which === E.PAGE_UP || a.which === E.PAGE_DOWN) return void p(a);
                        switch (a.which) {
                            case E.UP:
                            case E.DOWN:
                                return this.moveHighlight(a.which === E.UP ? -1 : 1), void p(a);
                            case E.ENTER:
                                return this.selectHighlighted(), void p(a);
                            case E.TAB:
                                return void this.selectHighlighted({
                                    "noFocus": !0
                                });
                            case E.ESC:
                                return this.cancel(a), void p(a)
                        }
                    }
                })), this.search.on("blur", this.bind(function() {
                    document.activeElement === this.body.get(0) && window.setTimeout(this.bind(function() {
                        this.opened() && this.search.focus()
                    }), 0)
                })), this.focusser.on("keydown", this.bind(function(a) {
                    if (this.isInterfaceEnabled() && a.which !== E.TAB && !E.isControl(a) && !E.isFunctionKey(a) && a.which !== E.ESC) {
                        if (this.opts.openOnEnter === !1 && a.which === E.ENTER) return void p(a);
                        if (a.which == E.DOWN || a.which == E.UP || a.which == E.ENTER && this.opts.openOnEnter) {
                            if (a.altKey || a.ctrlKey || a.shiftKey || a.metaKey) return;
                            return this.open(), void p(a)
                        }
                        return a.which == E.DELETE || a.which == E.BACKSPACE ? (this.opts.allowClear && this.clear(), void p(a)) : void 0
                    }
                })), j(this.focusser), this.focusser.on("keyup-change input", this.bind(function(a) {
                    if (this.opts.minimumResultsForSearch >= 0) {
                        if (a.stopPropagation(), this.opened()) return;
                        this.open()
                    }
                })), b.on("mousedown touchstart", "abbr", this.bind(function(a) {
                    this.isInterfaceEnabled() && (this.clear(), q(a), this.close(), this.selection.focus())
                })), b.on("mousedown touchstart", this.bind(function(d) {
                    c(b), this.container.hasClass("select2-container-active") || this.opts.element.trigger(a.Event("select2-focus")), this.opened() ? this.close() : this.isInterfaceEnabled() && this.open(), p(d)
                })), f.on("mousedown touchstart", this.bind(function() {
                    this.opts.shouldFocusInput(this) && this.search.focus()
                })), b.on("focus", this.bind(function(a) {
                    p(a)
                })), this.focusser.on("focus", this.bind(function() {
                    this.container.hasClass("select2-container-active") || this.opts.element.trigger(a.Event("select2-focus")), this.container.addClass("select2-container-active")
                })).on("blur", this.bind(function() {
                    this.opened() || (this.container.removeClass("select2-container-active"), this.opts.element.trigger(a.Event("select2-blur")))
                })), this.search.on("focus", this.bind(function() {
                    this.container.hasClass("select2-container-active") || this.opts.element.trigger(a.Event("select2-focus")), this.container.addClass("select2-container-active")
                })), this.initContainerWidth(), this.opts.element.addClass("select2-offscreen"), this.setPlaceholder()
            },
            "clear": function(b) {
                var c = this.selection.data("select2-data");
                if (c) {
                    var d = a.Event("select2-clearing");
                    if (this.opts.element.trigger(d), d.isDefaultPrevented()) return;
                    var e = this.getPlaceholderOption();
                    this.opts.element.val(e ? e.val() : ""), this.selection.find(".select2-chosen").empty(), this.selection.removeData("select2-data"), this.setPlaceholder(), b !== !1 && (this.opts.element.trigger({
                        "type": "select2-removed",
                        "val": this.id(c),
                        "choice": c
                    }), this.triggerChange({
                        "removed": c
                    }))
                }
            },
            "initSelection": function() {
                if (this.isPlaceholderOptionSelected()) this.updateSelection(null), this.close(), this.setPlaceholder();
                else {
                    var a = this;
                    this.opts.initSelection.call(null, this.opts.element, function(c) {
                        c !== b && null !== c && (a.updateSelection(c), a.close(), a.setPlaceholder(), a.nextSearchTerm = a.opts.nextSearchTerm(c, a.search.val()))
                    })
                }
            },
            "isPlaceholderOptionSelected": function() {
                var a;
                return this.getPlaceholder() === b ? !1 : (a = this.getPlaceholderOption()) !== b && a.prop("selected") || "" === this.opts.element.val() || this.opts.element.val() === b || null === this.opts.element.val()
            },
            "prepareOpts": function() {
                var b = this.parent.prepareOpts.apply(this, arguments),
                    c = this;
                return "select" === b.element.get(0).tagName.toLowerCase() ? b.initSelection = function(a, b) {
                    var d = a.find("option").filter(function() {
                        return this.selected && !this.disabled
                    });
                    b(c.optionToData(d))
                } : "data" in b && (b.initSelection = b.initSelection || function(c, d) {
                    var e = c.val(),
                        f = null;
                    b.query({
                        "matcher": function(a, c, d) {
                            var h = g(e, b.id(d));
                            return h && (f = d), h
                        },
                        "callback": a.isFunction(d) ? function() {
                            d(f)
                        } : a.noop
                    })
                }), b
            },
            "getPlaceholder": function() {
                return this.select && this.getPlaceholderOption() === b ? b : this.parent.getPlaceholder.apply(this, arguments)
            },
            "setPlaceholder": function() {
                var a = this.getPlaceholder();
                if (this.isPlaceholderOptionSelected() && a !== b) {
                    if (this.select && this.getPlaceholderOption() === b) return;
                    this.selection.find(".select2-chosen").html(this.opts.escapeMarkup(a)), this.selection.addClass("select2-default"), this.container.removeClass("select2-allowclear")
                }
            },
            "postprocessResults": function(a, b, c) {
                var d = 0,
                    e = this;
                if (this.findHighlightableChoices().each2(function(a, b) {
                        return g(e.id(b.data("select2-data")), e.opts.element.val()) ? (d = a, !1) : void 0
                    }), c !== !1 && this.highlight(b === !0 && d >= 0 ? d : 0), b === !0) {
                    var f = this.opts.minimumResultsForSearch;
                    f >= 0 && this.showSearch(A(a.results) >= f)
                }
            },
            "showSearch": function(b) {
                this.showSearchInput !== b && (this.showSearchInput = b, this.dropdown.find(".select2-search").toggleClass("select2-search-hidden", !b), this.dropdown.find(".select2-search").toggleClass("select2-offscreen", !b), a(this.dropdown, this.container).toggleClass("select2-with-searchbox", b))
            },
            "onSelect": function(a, b) {
                if (this.triggerSelect(a)) {
                    var c = this.opts.element.val(),
                        d = this.data();
                    this.opts.element.val(this.id(a)), this.updateSelection(a), this.opts.element.trigger({
                        "type": "select2-selected",
                        "val": this.id(a),
                        "choice": a
                    }), this.nextSearchTerm = this.opts.nextSearchTerm(a, this.search.val()), this.close(), b && b.noFocus || !this.opts.shouldFocusInput(this) || this.focusser.focus(), g(c, this.id(a)) || this.triggerChange({
                        "added": a,
                        "removed": d
                    })
                }
            },
            "updateSelection": function(a) {
                var c, d, e = this.selection.find(".select2-chosen");
                this.selection.data("select2-data", a), e.empty(), null !== a && (c = this.opts.formatSelection(a, e, this.opts.escapeMarkup)), c !== b && e.append(c), d = this.opts.formatSelectionCssClass(a, e), d !== b && e.addClass(d), this.selection.removeClass("select2-default"), this.opts.allowClear && this.getPlaceholder() !== b && this.container.addClass("select2-allowclear")
            },
            "val": function() {
                var a, c = !1,
                    d = null,
                    e = this,
                    f = this.data();
                if (0 === arguments.length) return this.opts.element.val();
                if (a = arguments[0], arguments.length > 1 && (c = arguments[1]), this.select) this.select.val(a).find("option").filter(function() {
                    return this.selected
                }).each2(function(a, b) {
                    return d = e.optionToData(b), !1
                }), this.updateSelection(d), this.setPlaceholder(), c && this.triggerChange({
                    "added": d,
                    "removed": f
                });
                else {
                    if (!a && 0 !== a) return void this.clear(c);
                    if (this.opts.initSelection === b) throw new Error("cannot call val() if initSelection() is not defined");
                    this.opts.element.val(a), this.opts.initSelection(this.opts.element, function(a) {
                        e.opts.element.val(a ? e.id(a) : ""), e.updateSelection(a), e.setPlaceholder(), c && e.triggerChange({
                            "added": a,
                            "removed": f
                        })
                    })
                }
            },
            "clearSearch": function() {
                this.search.val(""), this.focusser.val("")
            },
            "data": function(a) {
                var c, d = !1;
                return 0 === arguments.length ? (c = this.selection.data("select2-data"), c == b && (c = null), c) : (arguments.length > 1 && (d = arguments[1]), void(a ? (c = this.data(), this.opts.element.val(a ? this.id(a) : ""), this.updateSelection(a), d && this.triggerChange({
                    "added": a,
                    "removed": c
                })) : this.clear(d)))
            }
        }), H = D(F, {
            "createContainer": function() {
                var b = a(document.createElement("div")).attr({
                    "class": "select2-container select2-container-multi"
                }).html(["<ul class='select2-choices'>", "  <li class='select2-search-field'>", "    <label for='' class='select2-offscreen'></label>", "    <input type='text' autocomplete='off' autocorrect='off' autocapitalize='off' spellcheck='false' class='select2-input'>", "  </li>", "</ul>", "<div class='select2-drop select2-drop-multi select2-display-none'>", "   <ul class='select2-results'>", "   </ul>", "</div>"].join(""));
                return b
            },
            "prepareOpts": function() {
                var b = this.parent.prepareOpts.apply(this, arguments),
                    c = this;
                return "select" === b.element.get(0).tagName.toLowerCase() ? b.initSelection = function(a, b) {
                    var d = [];
                    a.find("option").filter(function() {
                        return this.selected && !this.disabled
                    }).each2(function(a, b) {
                        d.push(c.optionToData(b))
                    }), b(d)
                } : "data" in b && (b.initSelection = b.initSelection || function(c, d) {
                    var e = h(c.val(), b.separator),
                        f = [];
                    b.query({
                        "matcher": function(c, d, h) {
                            var i = a.grep(e, function(a) {
                                return g(a, b.id(h))
                            }).length;
                            return i && f.push(h), i
                        },
                        "callback": a.isFunction(d) ? function() {
                            for (var a = [], c = 0; c < e.length; c++)
                                for (var h = e[c], i = 0; i < f.length; i++) {
                                    var j = f[i];
                                    if (g(h, b.id(j))) {
                                        a.push(j), f.splice(i, 1);
                                        break
                                    }
                                }
                            d(a)
                        } : a.noop
                    })
                }), b
            },
            "selectChoice": function(a) {
                var b = this.container.find(".select2-search-choice-focus");
                b.length && a && a[0] == b[0] || (b.length && this.opts.element.trigger("choice-deselected", b), b.removeClass("select2-search-choice-focus"), a && a.length && (this.close(), a.addClass("select2-search-choice-focus"), this.opts.element.trigger("choice-selected", a)))
            },
            "destroy": function() {
                a("label[for='" + this.search.attr("id") + "']").attr("for", this.opts.element.attr("id")), this.parent.destroy.apply(this, arguments), C.call(this, "searchContainer", "selection")
            },
            "initContainer": function() {
                var b, c = ".select2-choices";
                this.searchContainer = this.container.find(".select2-search-field"), this.selection = b = this.container.find(c);
                var d = this;
                this.selection.on("click", ".select2-search-choice:not(.select2-locked)", function() {
                    d.search[0].focus(), d.selectChoice(a(this))
                }), this.search.attr("id", "s2id_autogen" + I()), this.search.prev().text(a("label[for='" + this.opts.element.attr("id") + "']").text()).attr("for", this.search.attr("id")), this.search.on("input paste", this.bind(function() {
                    this.search.attr("placeholder") && 0 == this.search.val().length || this.isInterfaceEnabled() && (this.opened() || this.open())
                })), this.search.attr("tabindex", this.elementTabIndex), this.keydowns = 0, this.search.on("keydown", this.bind(function(a) {
                    if (this.isInterfaceEnabled()) {
                        ++this.keydowns;
                        var c = b.find(".select2-search-choice-focus"),
                            d = c.prev(".select2-search-choice:not(.select2-locked)"),
                            e = c.next(".select2-search-choice:not(.select2-locked)"),
                            f = o(this.search);
                        if (c.length && (a.which == E.LEFT || a.which == E.RIGHT || a.which == E.BACKSPACE || a.which == E.DELETE || a.which == E.ENTER)) {
                            var g = c;
                            return a.which == E.LEFT && d.length ? g = d : a.which == E.RIGHT ? g = e.length ? e : null : a.which === E.BACKSPACE ? this.unselect(c.first()) && (this.search.width(10), g = d.length ? d : e) : a.which == E.DELETE ? this.unselect(c.first()) && (this.search.width(10), g = e.length ? e : null) : a.which == E.ENTER && (g = null), this.selectChoice(g), p(a), void(g && g.length || this.open())
                        }
                        if ((a.which === E.BACKSPACE && 1 == this.keydowns || a.which == E.LEFT) && 0 == f.offset && !f.length) return this.selectChoice(b.find(".select2-search-choice:not(.select2-locked)").last()), void p(a);
                        if (this.selectChoice(null), this.opened()) switch (a.which) {
                            case E.UP:
                            case E.DOWN:
                                return this.moveHighlight(a.which === E.UP ? -1 : 1), void p(a);
                            case E.ENTER:
                                return this.selectHighlighted(), void p(a);
                            case E.TAB:
                                return this.selectHighlighted({
                                    "noFocus": !0
                                }), void this.close();
                            case E.ESC:
                                return this.cancel(a), void p(a)
                        }
                        if (a.which !== E.TAB && !E.isControl(a) && !E.isFunctionKey(a) && a.which !== E.BACKSPACE && a.which !== E.ESC) {
                            if (a.which === E.ENTER) {
                                if (this.opts.openOnEnter === !1) return;
                                if (a.altKey || a.ctrlKey || a.shiftKey || a.metaKey) return
                            }
                            this.open(), (a.which === E.PAGE_UP || a.which === E.PAGE_DOWN) && p(a), a.which === E.ENTER && p(a)
                        }
                    }
                })), this.search.on("keyup", this.bind(function() {
                    this.keydowns = 0, this.resizeSearch()
                })), this.search.on("blur", this.bind(function(b) {
                    this.container.removeClass("select2-container-active"), this.search.removeClass("select2-focused"), this.selectChoice(null), this.opened() || this.clearSearch(), b.stopImmediatePropagation(), this.opts.element.trigger(a.Event("select2-blur"))
                })), this.container.on("click", c, this.bind(function(b) {
                    this.isInterfaceEnabled() && (a(b.target).closest(".select2-search-choice").length > 0 || (this.selectChoice(null), this.clearPlaceholder(), this.container.hasClass("select2-container-active") || this.opts.element.trigger(a.Event("select2-focus")), this.open(), this.focusSearch(), b.preventDefault()))
                })), this.container.on("focus", c, this.bind(function() {
                    this.isInterfaceEnabled() && (this.container.hasClass("select2-container-active") || this.opts.element.trigger(a.Event("select2-focus")), this.container.addClass("select2-container-active"), this.dropdown.addClass("select2-drop-active"), this.clearPlaceholder())
                })), this.initContainerWidth(), this.opts.element.addClass("select2-offscreen"), this.clearSearch()
            },
            "enableInterface": function() {
                this.parent.enableInterface.apply(this, arguments) && this.search.prop("disabled", !this.isInterfaceEnabled())
            },
            "initSelection": function() {
                if ("" === this.opts.element.val() && "" === this.opts.element.text() && (this.updateSelection([]), this.close(), this.clearSearch()), this.select || "" !== this.opts.element.val()) {
                    var a = this;
                    this.opts.initSelection.call(null, this.opts.element, function(c) {
                        c !== b && null !== c && (a.updateSelection(c), a.close(), a.clearSearch())
                    })
                }
            },
            "clearSearch": function() {
                var a = this.getPlaceholder(),
                    c = this.getMaxSearchWidth();
                a !== b && 0 === this.getVal().length && this.search.hasClass("select2-focused") === !1 ? (this.search.val(a).addClass("select2-default"), this.search.width(c > 0 ? c : this.container.css("width"))) : this.search.val("").width(10)
            },
            "clearPlaceholder": function() {
                this.search.hasClass("select2-default") && this.search.val("").removeClass("select2-default")
            },
            "opening": function() {
                this.clearPlaceholder(), this.resizeSearch(), this.parent.opening.apply(this, arguments), this.focusSearch(), "" === this.search.val() && this.nextSearchTerm != b && (this.search.val(this.nextSearchTerm), this.search.select()), this.updateResults(!0), this.opts.shouldFocusInput(this) && this.search.focus(), this.opts.element.trigger(a.Event("select2-open"))
            },
            "close": function() {
                this.opened() && this.parent.close.apply(this, arguments)
            },
            "focus": function() {
                this.close(), this.search.focus()
            },
            "isFocused": function() {
                return this.search.hasClass("select2-focused")
            },
            "updateSelection": function(b) {
                var c = [],
                    d = [],
                    f = this;
                a(b).each(function() {
                    e(f.id(this), c) < 0 && (c.push(f.id(this)), d.push(this))
                }), b = d, this.selection.find(".select2-search-choice").remove(), a(b).each(function() {
                    f.addSelectedChoice(this)
                }), f.postprocessResults()
            },
            "tokenize": function() {
                var a = this.search.val();
                a = this.opts.tokenizer.call(this, a, this.data(), this.bind(this.onSelect), this.opts), null != a && a != b && (this.search.val(a), a.length > 0 && this.open())
            },
            "onSelect": function(a, c) {
                this.triggerSelect(a) && "" !== a.text && (this.addSelectedChoice(a), this.opts.element.trigger({
                    "type": "selected",
                    "val": this.id(a),
                    "choice": a
                }), this.nextSearchTerm = this.opts.nextSearchTerm(a, this.search.val()), this.clearSearch(), this.updateResults(), (this.select || !this.opts.closeOnSelect) && this.postprocessResults(a, !1, this.opts.closeOnSelect === !0), this.opts.closeOnSelect ? (this.close(), this.search.width(10)) : this.countSelectableResults() > 0 ? (this.search.width(10), this.resizeSearch(), this.getMaximumSelectionSize() > 0 && this.val().length >= this.getMaximumSelectionSize() ? this.updateResults(!0) : this.nextSearchTerm != b && (this.search.val(this.nextSearchTerm), this.updateResults(), this.search.select()), this.positionDropdown()) : (this.close(), this.search.width(10)), this.triggerChange({
                    "added": a
                }), c && c.noFocus || this.focusSearch())
            },
            "cancel": function() {
                this.close(), this.focusSearch()
            },
            "addSelectedChoice": function(c) {
                var d, e, f = !c.locked,
                    g = a("<li class='select2-search-choice'>    <div></div>    <a href='#' class='select2-search-choice-close' tabindex='-1'></a></li>"),
                    h = a("<li class='select2-search-choice select2-locked'><div></div></li>"),
                    i = f ? g : h,
                    j = this.id(c),
                    k = this.getVal();
                d = this.opts.formatSelection(c, i.find("div"), this.opts.escapeMarkup), d != b && i.find("div").replaceWith("<div>" + d + "</div>"), e = this.opts.formatSelectionCssClass(c, i.find("div")), e != b && i.addClass(e), f && i.find(".select2-search-choice-close").on("mousedown", p).on("click dblclick", this.bind(function(b) {
                    this.isInterfaceEnabled() && (this.unselect(a(b.target)), this.selection.find(".select2-search-choice-focus").removeClass("select2-search-choice-focus"), p(b), this.close(), this.focusSearch())
                })).on("focus", this.bind(function() {
                    this.isInterfaceEnabled() && (this.container.addClass("select2-container-active"), this.dropdown.addClass("select2-drop-active"))
                })), i.data("select2-data", c), i.insertBefore(this.searchContainer), k.push(j), this.setVal(k)
            },
            "unselect": function(b) {
                var c, d, f = this.getVal();
                if (b = b.closest(".select2-search-choice"), 0 === b.length) throw "Invalid argument: " + b + ". Must be .select2-search-choice";
                if (c = b.data("select2-data")) {
                    var g = a.Event("select2-removing");
                    if (g.val = this.id(c), g.choice = c, this.opts.element.trigger(g), g.isDefaultPrevented()) return !1;
                    for (;
                        (d = e(this.id(c), f)) >= 0;) f.splice(d, 1), this.setVal(f), this.select && this.postprocessResults();
                    return b.remove(), this.opts.element.trigger({
                        "type": "select2-removed",
                        "val": this.id(c),
                        "choice": c
                    }), this.triggerChange({
                        "removed": c
                    }), !0
                }
            },
            "postprocessResults": function(a, b, c) {
                var d = this.getVal(),
                    f = this.results.find(".select2-result"),
                    g = this.results.find(".select2-result-with-children"),
                    h = this;
                f.each2(function(a, b) {
                    var c = h.id(b.data("select2-data"));
                    e(c, d) >= 0 && (b.addClass("select2-selected"), b.find(".select2-result-selectable").addClass("select2-selected"))
                }), g.each2(function(a, b) {
                    b.is(".select2-result-selectable") || 0 !== b.find(".select2-result-selectable:not(.select2-selected)").length || b.addClass("select2-selected")
                }), -1 == this.highlight() && c !== !1 && h.highlight(0), !this.opts.createSearchChoice && !f.filter(".select2-result:not(.select2-selected)").length > 0 && (!a || a && !a.more && 0 === this.results.find(".select2-no-results").length) && y(h.opts.formatNoMatches, "formatNoMatches") && this.results.append("<li class='select2-no-results'>" + z(h.opts.formatNoMatches, h.opts.element, h.search.val()) + "</li>")
            },
            "getMaxSearchWidth": function() {
                return this.selection.width() - i(this.search)
            },
            "resizeSearch": function() {
                var a, b, c, d, e, f = i(this.search);
                a = r(this.search) + 10, b = this.search.offset().left, c = this.selection.width(), d = this.selection.offset().left, e = c - (b - d) - f, a > e && (e = c - f), 40 > e && (e = c - f), 0 >= e && (e = a), this.search.width(Math.floor(e))
            },
            "getVal": function() {
                var a;
                return this.select ? (a = this.select.val(), null === a ? [] : a) : (a = this.opts.element.val(), h(a, this.opts.separator))
            },
            "setVal": function(b) {
                var c;
                this.select ? this.select.val(b) : (c = [], a(b).each(function() {
                    e(this, c) < 0 && c.push(this)
                }), this.opts.element.val(0 === c.length ? "" : c.join(this.opts.separator)))
            },
            "buildChangeDetails": function(a, b) {
                for (var b = b.slice(0), a = a.slice(0), c = 0; c < b.length; c++)
                    for (var d = 0; d < a.length; d++) g(this.opts.id(b[c]), this.opts.id(a[d])) && (b.splice(c, 1), c > 0 && c--, a.splice(d, 1), d--);
                return {
                    "added": b,
                    "removed": a
                }
            },
            "val": function(c, d) {
                var e, f = this;
                if (0 === arguments.length) return this.getVal();
                if (e = this.data(), e.length || (e = []), !c && 0 !== c) return this.opts.element.val(""), this.updateSelection([]), this.clearSearch(), void(d && this.triggerChange({
                    "added": this.data(),
                    "removed": e
                }));
                if (this.setVal(c), this.select) this.opts.initSelection(this.select, this.bind(this.updateSelection)), d && this.triggerChange(this.buildChangeDetails(e, this.data()));
                else {
                    if (this.opts.initSelection === b) throw new Error("val() cannot be called if initSelection() is not defined");
                    this.opts.initSelection(this.opts.element, function(b) {
                        var c = a.map(b, f.id);
                        f.setVal(c), f.updateSelection(b), f.clearSearch(), d && f.triggerChange(f.buildChangeDetails(e, f.data()))
                    })
                }
                this.clearSearch()
            },
            "onSortStart": function() {
                if (this.select) throw new Error("Sorting of elements is not supported when attached to <select>. Attach to <input type='hidden'/> instead.");
                this.search.width(0), this.searchContainer.hide()
            },
            "onSortEnd": function() {
                var b = [],
                    c = this;
                this.searchContainer.show(), this.searchContainer.appendTo(this.searchContainer.parent()), this.resizeSearch(), this.selection.find(".select2-search-choice").each(function() {
                    b.push(c.opts.id(a(this).data("select2-data")))
                }), this.setVal(b), this.triggerChange()
            },
            "data": function(b, c) {
                var d, e, f = this;
                return 0 === arguments.length ? this.selection.children(".select2-search-choice").map(function() {
                    return a(this).data("select2-data")
                }).get() : (e = this.data(), b || (b = []), d = a.map(b, function(a) {
                    return f.opts.id(a)
                }), this.setVal(d), this.updateSelection(b), this.clearSearch(), c && this.triggerChange(this.buildChangeDetails(e, this.data())), void 0)
            }
        }), a.fn.select2 = function() {
            var c, d, f, g, h, i = Array.prototype.slice.call(arguments, 0),
                j = ["val", "destroy", "opened", "open", "close", "focus", "isFocused", "container", "dropdown", "onSortStart", "onSortEnd", "enable", "disable", "readonly", "positionDropdown", "data", "search"],
                k = ["opened", "isFocused", "container", "dropdown"],
                l = ["val", "data"],
                m = {
                    "search": "externalSearch"
                };
            return this.each(function() {
                if (0 === i.length || "object" == typeof i[0]) c = 0 === i.length ? {} : a.extend({}, i[0]), c.element = a(this), "select" === c.element.get(0).tagName.toLowerCase() ? h = c.element.prop("multiple") : (h = c.multiple || !1, "tags" in c && (c.multiple = h = !0)), d = h ? new window.Select2["class"].multi : new window.Select2["class"].single, d.init(c);
                else {
                    if ("string" != typeof i[0]) throw "Invalid arguments to select2 plugin: " + i;
                    if (e(i[0], j) < 0) throw "Unknown method: " + i[0];
                    if (g = b, d = a(this).data("select2"), d === b) return;
                    if (f = i[0], "container" === f ? g = d.container : "dropdown" === f ? g = d.dropdown : (m[f] && (f = m[f]), g = d[f].apply(d, i.slice(1))), e(i[0], k) >= 0 || e(i[0], l) >= 0 && 1 == i.length) return !1
                }
            }), g === b ? this : g
        }, a.fn.select2.defaults = {
            "width": "copy",
            "loadMorePadding": 0,
            "closeOnSelect": !0,
            "openOnEnter": !0,
            "containerCss": {},
            "dropdownCss": {},
            "containerCssClass": "",
            "dropdownCssClass": "",
            "formatResult": function(a, b, c, d) {
                var e = [];
                return t(a.text, c.term, e, d), e.join("")
            },
            "formatSelection": function(a, c, d) {
                return a ? d(a.text) : b
            },
            "sortResults": function(a) {
                return a
            },
            "formatResultCssClass": function(a) {
                return a.css
            },
            "formatSelectionCssClass": function() {
                return b
            },
            "minimumResultsForSearch": 0,
            "minimumInputLength": 0,
            "maximumInputLength": null,
            "maximumSelectionSize": 0,
            "id": function(a) {
                return a == b ? null : a.id
            },
            "matcher": function(a, b) {
                return d("" + b).toUpperCase().indexOf(d("" + a).toUpperCase()) >= 0
            },
            "separator": ",",
            "tokenSeparators": [],
            "tokenizer": B,
            "escapeMarkup": u,
            "blurOnChange": !1,
            "selectOnBlur": !1,
            "adaptContainerCssClass": function(a) {
                return a
            },
            "adaptDropdownCssClass": function() {
                return null
            },
            "nextSearchTerm": function() {
                return b
            },
            "searchInputPlaceholder": "",
            "createSearchChoicePosition": "top",
            "shouldFocusInput": function(a) {
                var b = "ontouchstart" in window || navigator.msMaxTouchPoints > 0;
                return b && a.opts.minimumResultsForSearch < 0 ? !1 : !0
            }
        }, a.fn.select2.locales = [], a.fn.select2.locales.en = {
            "formatMatches": function(a) {
                return 1 === a ? "One result is available, press enter to select it." : a + " results are available, use up and down arrow keys to navigate."
            },
            "formatNoMatches": function() {
                return "No matches found"
            },
            "formatAjaxError": function() {
                return "Loading failed"
            },
            "formatInputTooShort": function(a, b) {
                var c = b - a.length;
                return "Please enter " + c + " or more character" + (1 == c ? "" : "s")
            },
            "formatInputTooLong": function(a, b) {
                var c = a.length - b;
                return "Please delete " + c + " character" + (1 == c ? "" : "s")
            },
            "formatSelectionTooBig": function(a) {
                return "You can only select " + a + " item" + (1 == a ? "" : "s")
            },
            "formatLoadMore": function() {
                return "Loading more results\u2026"
            },
            "formatSearching": function() {
                return "Searching\u2026"
            }
        }, a.extend(a.fn.select2.defaults, a.fn.select2.locales.en), a.fn.select2.ajaxDefaults = {
            "transport": a.ajax,
            "params": {
                "type": "GET",
                "cache": !1,
                "dataType": "json"
            }
        }, window.Select2 = {
            "query": {
                "ajax": v,
                "local": w,
                "tags": x
            },
            "util": {
                "debounce": l,
                "markMatch": t,
                "escapeMarkup": u,
                "stripDiacritics": d
            },
            "class": {
                "abstract": F,
                "single": G,
                "multi": H
            }
        }
    }
}(jQuery);