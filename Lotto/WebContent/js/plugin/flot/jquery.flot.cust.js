/*! SmartAdmin - v1.5 - 2014-09-27 */ ! function(a) {
    a.color = {}, a.color.make = function(b, c, d, e) {
        var f = {};
        return f.r = b || 0, f.g = c || 0, f.b = d || 0, f.a = null != e ? e : 1, f.add = function(a, b) {
            for (var c = 0; c < a.length; ++c) f[a.charAt(c)] += b;
            return f.normalize()
        }, f.scale = function(a, b) {
            for (var c = 0; c < a.length; ++c) f[a.charAt(c)] *= b;
            return f.normalize()
        }, f.toString = function() {
            return f.a >= 1 ? "rgb(" + [f.r, f.g, f.b].join(",") + ")" : "rgba(" + [f.r, f.g, f.b, f.a].join(",") + ")"
        }, f.normalize = function() {
            function a(a, b, c) {
                return a > b ? a : b > c ? c : b
            }
            return f.r = a(0, parseInt(f.r), 255), f.g = a(0, parseInt(f.g), 255), f.b = a(0, parseInt(f.b), 255), f.a = a(0, f.a, 1), f
        }, f.clone = function() {
            return a.color.make(f.r, f.b, f.g, f.a)
        }, f.normalize()
    }, a.color.extract = function(b, c) {
        var d;
        do {
            if (d = b.css(c).toLowerCase(), "" != d && "transparent" != d) break;
            b = b.parent()
        } while (b.length && !a.nodeName(b.get(0), "body"));
        return "rgba(0, 0, 0, 0)" == d && (d = "transparent"), a.color.parse(d)
    }, a.color.parse = function(c) {
        var d, e = a.color.make;
        if (d = /rgb\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*\)/.exec(c)) return e(parseInt(d[1], 10), parseInt(d[2], 10), parseInt(d[3], 10));
        if (d = /rgba\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]+(?:\.[0-9]+)?)\s*\)/.exec(c)) return e(parseInt(d[1], 10), parseInt(d[2], 10), parseInt(d[3], 10), parseFloat(d[4]));
        if (d = /rgb\(\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\%\s*\)/.exec(c)) return e(2.55 * parseFloat(d[1]), 2.55 * parseFloat(d[2]), 2.55 * parseFloat(d[3]));
        if (d = /rgba\(\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\s*\)/.exec(c)) return e(2.55 * parseFloat(d[1]), 2.55 * parseFloat(d[2]), 2.55 * parseFloat(d[3]), parseFloat(d[4]));
        if (d = /#([a-fA-F0-9]{2})([a-fA-F0-9]{2})([a-fA-F0-9]{2})/.exec(c)) return e(parseInt(d[1], 16), parseInt(d[2], 16), parseInt(d[3], 16));
        if (d = /#([a-fA-F0-9])([a-fA-F0-9])([a-fA-F0-9])/.exec(c)) return e(parseInt(d[1] + d[1], 16), parseInt(d[2] + d[2], 16), parseInt(d[3] + d[3], 16));
        var f = a.trim(c).toLowerCase();
        return "transparent" == f ? e(255, 255, 255, 0) : (d = b[f] || [0, 0, 0], e(d[0], d[1], d[2]))
    };
    var b = {
        "aqua": [0, 255, 255],
        "azure": [240, 255, 255],
        "beige": [245, 245, 220],
        "black": [0, 0, 0],
        "blue": [0, 0, 255],
        "brown": [165, 42, 42],
        "cyan": [0, 255, 255],
        "darkblue": [0, 0, 139],
        "darkcyan": [0, 139, 139],
        "darkgrey": [169, 169, 169],
        "darkgreen": [0, 100, 0],
        "darkkhaki": [189, 183, 107],
        "darkmagenta": [139, 0, 139],
        "darkolivegreen": [85, 107, 47],
        "darkorange": [255, 140, 0],
        "darkorchid": [153, 50, 204],
        "darkred": [139, 0, 0],
        "darksalmon": [233, 150, 122],
        "darkviolet": [148, 0, 211],
        "fuchsia": [255, 0, 255],
        "gold": [255, 215, 0],
        "green": [0, 128, 0],
        "indigo": [75, 0, 130],
        "khaki": [240, 230, 140],
        "lightblue": [173, 216, 230],
        "lightcyan": [224, 255, 255],
        "lightgreen": [144, 238, 144],
        "lightgrey": [211, 211, 211],
        "lightpink": [255, 182, 193],
        "lightyellow": [255, 255, 224],
        "lime": [0, 255, 0],
        "magenta": [255, 0, 255],
        "maroon": [128, 0, 0],
        "navy": [0, 0, 128],
        "olive": [128, 128, 0],
        "orange": [255, 165, 0],
        "pink": [255, 192, 203],
        "purple": [128, 0, 128],
        "violet": [128, 0, 128],
        "red": [255, 0, 0],
        "silver": [192, 192, 192],
        "white": [255, 255, 255],
        "yellow": [255, 255, 0]
    }
}(jQuery),
function(a) {
    function b(b, c) {
        var d = c.children("." + b)[0];
        if (null == d && (d = document.createElement("canvas"), d.className = b, a(d).css({
                "direction": "ltr",
                "position": "absolute",
                "left": 0,
                "top": 0
            }).appendTo(c), !d.getContext)) {
            if (!window.G_vmlCanvasManager) throw new Error("Canvas is not available. If you're using IE with a fall-back such as Excanvas, then there's either a mistake in your conditional include, or the page has no DOCTYPE and is rendering in Quirks Mode.");
            d = window.G_vmlCanvasManager.initElement(d)
        }
        this.element = d;
        var e = this.context = d.getContext("2d"),
            f = window.devicePixelRatio || 1,
            g = e.webkitBackingStorePixelRatio || e.mozBackingStorePixelRatio || e.msBackingStorePixelRatio || e.oBackingStorePixelRatio || e.backingStorePixelRatio || 1;
        this.pixelRatio = f / g, this.resize(c.width(), c.height()), this.textContainer = null, this.text = {}, this._textCache = {}
    }

    function c(c, e, f, g) {
        function h(a, b) {
            b = [qb].concat(b);
            for (var c = 0; c < a.length; ++c) a[c].apply(this, b)
        }

        function i() {
            for (var c = {
                    "Canvas": b
                }, d = 0; d < g.length; ++d) {
                var e = g[d];
                e.init(qb, c), e.options && a.extend(!0, eb, e.options)
            }
        }

        function j(b) {
            a.extend(!0, eb, b), b && b.colors && (eb.colors = b.colors), null == eb.xaxis.color && (eb.xaxis.color = a.color.parse(eb.grid.color).scale("a", .22).toString()), null == eb.yaxis.color && (eb.yaxis.color = a.color.parse(eb.grid.color).scale("a", .22).toString()), null == eb.xaxis.tickColor && (eb.xaxis.tickColor = eb.grid.tickColor || eb.xaxis.color), null == eb.yaxis.tickColor && (eb.yaxis.tickColor = eb.grid.tickColor || eb.yaxis.color), null == eb.grid.borderColor && (eb.grid.borderColor = eb.grid.color), null == eb.grid.tickColor && (eb.grid.tickColor = a.color.parse(eb.grid.color).scale("a", .22).toString());
            var d, e, f, g = c.css("font-size"),
                i = g ? +g.replace("px", "") : 13,
                j = {
                    "style": c.css("font-style"),
                    "size": Math.round(.8 * i),
                    "variant": c.css("font-variant"),
                    "weight": c.css("font-weight"),
                    "family": c.css("font-family")
                };
            for (f = eb.xaxes.length || 1, d = 0; f > d; ++d) e = eb.xaxes[d], e && !e.tickColor && (e.tickColor = e.color), e = a.extend(!0, {}, eb.xaxis, e), eb.xaxes[d] = e, e.font && (e.font = a.extend({}, j, e.font), e.font.color || (e.font.color = e.color), e.font.lineHeight || (e.font.lineHeight = Math.round(1.15 * e.font.size)));
            for (f = eb.yaxes.length || 1, d = 0; f > d; ++d) e = eb.yaxes[d], e && !e.tickColor && (e.tickColor = e.color), e = a.extend(!0, {}, eb.yaxis, e), eb.yaxes[d] = e, e.font && (e.font = a.extend({}, j, e.font), e.font.color || (e.font.color = e.color), e.font.lineHeight || (e.font.lineHeight = Math.round(1.15 * e.font.size)));
            for (eb.xaxis.noTicks && null == eb.xaxis.ticks && (eb.xaxis.ticks = eb.xaxis.noTicks), eb.yaxis.noTicks && null == eb.yaxis.ticks && (eb.yaxis.ticks = eb.yaxis.noTicks), eb.x2axis && (eb.xaxes[1] = a.extend(!0, {}, eb.xaxis, eb.x2axis), eb.xaxes[1].position = "top", null == eb.x2axis.min && (eb.xaxes[1].min = null), null == eb.x2axis.max && (eb.xaxes[1].max = null)), eb.y2axis && (eb.yaxes[1] = a.extend(!0, {}, eb.yaxis, eb.y2axis), eb.yaxes[1].position = "right", null == eb.y2axis.min && (eb.yaxes[1].min = null), null == eb.y2axis.max && (eb.yaxes[1].max = null)), eb.grid.coloredAreas && (eb.grid.markings = eb.grid.coloredAreas), eb.grid.coloredAreasColor && (eb.grid.markingsColor = eb.grid.coloredAreasColor), eb.lines && a.extend(!0, eb.series.lines, eb.lines), eb.points && a.extend(!0, eb.series.points, eb.points), eb.bars && a.extend(!0, eb.series.bars, eb.bars), null != eb.shadowSize && (eb.series.shadowSize = eb.shadowSize), null != eb.highlightColor && (eb.series.highlightColor = eb.highlightColor), d = 0; d < eb.xaxes.length; ++d) q(kb, d + 1).options = eb.xaxes[d];
            for (d = 0; d < eb.yaxes.length; ++d) q(lb, d + 1).options = eb.yaxes[d];
            for (var k in pb) eb.hooks[k] && eb.hooks[k].length && (pb[k] = pb[k].concat(eb.hooks[k]));
            h(pb.processOptions, [eb])
        }

        function k(a) {
            db = l(a), r(), s()
        }

        function l(b) {
            for (var c = [], d = 0; d < b.length; ++d) {
                var e = a.extend(!0, {}, eb.series);
                null != b[d].data ? (e.data = b[d].data, delete b[d].data, a.extend(!0, e, b[d]), b[d].data = e.data) : e.data = b[d], c.push(e)
            }
            return c
        }

        function m(a, b) {
            var c = a[b + "axis"];
            return "object" == typeof c && (c = c.n), "number" != typeof c && (c = 1), c
        }

        function n() {
            return a.grep(kb.concat(lb), function(a) {
                return a
            })
        }

        function o(a) {
            var b, c, d = {};
            for (b = 0; b < kb.length; ++b) c = kb[b], c && c.used && (d["x" + c.n] = c.c2p(a.left));
            for (b = 0; b < lb.length; ++b) c = lb[b], c && c.used && (d["y" + c.n] = c.c2p(a.top));
            return void 0 !== d.x1 && (d.x = d.x1), void 0 !== d.y1 && (d.y = d.y1), d
        }

        function p(a) {
            var b, c, d, e = {};
            for (b = 0; b < kb.length; ++b)
                if (c = kb[b], c && c.used && (d = "x" + c.n, null == a[d] && 1 == c.n && (d = "x"), null != a[d])) {
                    e.left = c.p2c(a[d]);
                    break
                }
            for (b = 0; b < lb.length; ++b)
                if (c = lb[b], c && c.used && (d = "y" + c.n, null == a[d] && 1 == c.n && (d = "y"), null != a[d])) {
                    e.top = c.p2c(a[d]);
                    break
                }
            return e
        }

        function q(b, c) {
            return b[c - 1] || (b[c - 1] = {
                "n": c,
                "direction": b == kb ? "x" : "y",
                "options": a.extend(!0, {}, b == kb ? eb.xaxis : eb.yaxis)
            }), b[c - 1]
        }

        function r() {
            var b, c = db.length,
                d = -1;
            for (b = 0; b < db.length; ++b) {
                var e = db[b].color;
                null != e && (c--, "number" == typeof e && e > d && (d = e))
            }
            d >= c && (c = d + 1);
            var f, g = [],
                h = eb.colors,
                i = h.length,
                j = 0;
            for (b = 0; c > b; b++) f = a.color.parse(h[b % i] || "#666"), b % i == 0 && b && (j = j >= 0 ? .5 > j ? -j - .2 : 0 : -j), g[b] = f.scale("rgb", 1 + j);
            var k, l = 0;
            for (b = 0; b < db.length; ++b) {
                if (k = db[b], null == k.color ? (k.color = g[l].toString(), ++l) : "number" == typeof k.color && (k.color = g[k.color].toString()), null == k.lines.show) {
                    var n, o = !0;
                    for (n in k)
                        if (k[n] && k[n].show) {
                            o = !1;
                            break
                        }
                    o && (k.lines.show = !0)
                }
                null == k.lines.zero && (k.lines.zero = !!k.lines.fill), k.xaxis = q(kb, m(k, "x")), k.yaxis = q(lb, m(k, "y"))
            }
        }

        function s() {
            function b(a, b, c) {
                b < a.datamin && b != -s && (a.datamin = b), c > a.datamax && c != s && (a.datamax = c)
            }
            var c, d, e, f, g, i, j, k, l, m, o, p, q = Number.POSITIVE_INFINITY,
                r = Number.NEGATIVE_INFINITY,
                s = Number.MAX_VALUE;
            for (a.each(n(), function(a, b) {
                    b.datamin = q, b.datamax = r, b.used = !1
                }), c = 0; c < db.length; ++c) g = db[c], g.datapoints = {
                "points": []
            }, h(pb.processRawData, [g, g.data, g.datapoints]);
            for (c = 0; c < db.length; ++c) {
                if (g = db[c], o = g.data, p = g.datapoints.format, !p) {
                    if (p = [], p.push({
                            "x": !0,
                            "number": !0,
                            "required": !0
                        }), p.push({
                            "y": !0,
                            "number": !0,
                            "required": !0
                        }), g.bars.show || g.lines.show && g.lines.fill) {
                        var t = !!(g.bars.show && g.bars.zero || g.lines.show && g.lines.zero);
                        p.push({
                            "y": !0,
                            "number": !0,
                            "required": !1,
                            "defaultValue": 0,
                            "autoscale": t
                        }), g.bars.horizontal && (delete p[p.length - 1].y, p[p.length - 1].x = !0)
                    }
                    g.datapoints.format = p
                }
                if (null == g.datapoints.pointsize) {
                    g.datapoints.pointsize = p.length, j = g.datapoints.pointsize, i = g.datapoints.points;
                    var u = g.lines.show && g.lines.steps;
                    for (g.xaxis.used = g.yaxis.used = !0, d = e = 0; d < o.length; ++d, e += j) {
                        m = o[d];
                        var v = null == m;
                        if (!v)
                            for (f = 0; j > f; ++f) k = m[f], l = p[f], l && (l.number && null != k && (k = +k, isNaN(k) ? k = null : 1 / 0 == k ? k = s : k == -1 / 0 && (k = -s)), null == k && (l.required && (v = !0), null != l.defaultValue && (k = l.defaultValue))), i[e + f] = k;
                        if (v)
                            for (f = 0; j > f; ++f) k = i[e + f], null != k && (l = p[f], l.autoscale !== !1 && (l.x && b(g.xaxis, k, k), l.y && b(g.yaxis, k, k))), i[e + f] = null;
                        else if (u && e > 0 && null != i[e - j] && i[e - j] != i[e] && i[e - j + 1] != i[e + 1]) {
                            for (f = 0; j > f; ++f) i[e + j + f] = i[e + f];
                            i[e + 1] = i[e - j + 1], e += j
                        }
                    }
                }
            }
            for (c = 0; c < db.length; ++c) g = db[c], h(pb.processDatapoints, [g, g.datapoints]);
            for (c = 0; c < db.length; ++c) {
                g = db[c], i = g.datapoints.points, j = g.datapoints.pointsize, p = g.datapoints.format;
                var w = q,
                    x = q,
                    y = r,
                    z = r;
                for (d = 0; d < i.length; d += j)
                    if (null != i[d])
                        for (f = 0; j > f; ++f) k = i[d + f], l = p[f], l && l.autoscale !== !1 && k != s && k != -s && (l.x && (w > k && (w = k), k > y && (y = k)), l.y && (x > k && (x = k), k > z && (z = k)));
                if (g.bars.show) {
                    var A;
                    switch (g.bars.align) {
                        case "left":
                            A = 0;
                            break;
                        case "right":
                            A = -g.bars.barWidth;
                            break;
                        default:
                            A = -g.bars.barWidth / 2
                    }
                    g.bars.horizontal ? (x += A, z += A + g.bars.barWidth) : (w += A, y += A + g.bars.barWidth)
                }
                b(g.xaxis, w, y), b(g.yaxis, x, z)
            }
            a.each(n(), function(a, b) {
                b.datamin == q && (b.datamin = null), b.datamax == r && (b.datamax = null)
            })
        }

        function t() {
            c.css("padding", 0).children().filter(function() {
                return !a(this).hasClass("flot-overlay") && !a(this).hasClass("flot-base")
            }).remove(), "static" == c.css("position") && c.css("position", "relative"), fb = new b("flot-base", c), gb = new b("flot-overlay", c), ib = fb.context, jb = gb.context, hb = a(gb.element).unbind();
            var d = c.data("plot");
            d && (d.shutdown(), gb.clear()), c.data("plot", qb)
        }

        function u() {
            eb.grid.hoverable && (hb.mousemove(T), hb.bind("mouseleave", U)), eb.grid.clickable && hb.click(V), h(pb.bindEvents, [hb])
        }

        function v() {
            sb && clearTimeout(sb), hb.unbind("mousemove", T), hb.unbind("mouseleave", U), hb.unbind("click", V), h(pb.shutdown, [hb])
        }

        function w(a) {
            function b(a) {
                return a
            }
            var c, d, e = a.options.transform || b,
                f = a.options.inverseTransform;
            "x" == a.direction ? (c = a.scale = nb / Math.abs(e(a.max) - e(a.min)), d = Math.min(e(a.max), e(a.min))) : (c = a.scale = ob / Math.abs(e(a.max) - e(a.min)), c = -c, d = Math.max(e(a.max), e(a.min))), a.p2c = e == b ? function(a) {
                return (a - d) * c
            } : function(a) {
                return (e(a) - d) * c
            }, a.c2p = f ? function(a) {
                return f(d + a / c)
            } : function(a) {
                return d + a / c
            }
        }

        function x(a) {
            for (var b = a.options, c = a.ticks || [], d = b.labelWidth || 0, e = b.labelHeight || 0, f = d || ("x" == a.direction ? Math.floor(fb.width / (c.length || 1)) : null), g = a.direction + "Axis " + a.direction + a.n + "Axis", h = "flot-" + a.direction + "-axis flot-" + a.direction + a.n + "-axis " + g, i = b.font || "flot-tick-label tickLabel", j = 0; j < c.length; ++j) {
                var k = c[j];
                if (k.label) {
                    var l = fb.getTextInfo(h, k.label, i, null, f);
                    d = Math.max(d, l.width), e = Math.max(e, l.height)
                }
            }
            a.labelWidth = b.labelWidth || d, a.labelHeight = b.labelHeight || e
        }

        function y(b) {
            var c = b.labelWidth,
                d = b.labelHeight,
                e = b.options.position,
                f = "x" === b.direction,
                g = b.options.tickLength,
                h = eb.grid.axisMargin,
                i = eb.grid.labelMargin,
                j = !0,
                k = !0,
                l = !0,
                m = !1;
            a.each(f ? kb : lb, function(a, c) {
                c && (c.show || c.reserveSpace) && (c === b ? m = !0 : c.options.position === e && (m ? k = !1 : j = !1), m || (l = !1))
            }), k && (h = 0), null == g && (g = l ? "full" : 5), isNaN(+g) || (i += +g), f ? (d += i, "bottom" == e ? (mb.bottom += d + h, b.box = {
                "top": fb.height - mb.bottom,
                "height": d
            }) : (b.box = {
                "top": mb.top + h,
                "height": d
            }, mb.top += d + h)) : (c += i, "left" == e ? (b.box = {
                "left": mb.left + h,
                "width": c
            }, mb.left += c + h) : (mb.right += c + h, b.box = {
                "left": fb.width - mb.right,
                "width": c
            })), b.position = e, b.tickLength = g, b.box.padding = i, b.innermost = j
        }

        function z(a) {
            "x" == a.direction ? (a.box.left = mb.left - a.labelWidth / 2, a.box.width = fb.width - mb.left - mb.right + a.labelWidth) : (a.box.top = mb.top - a.labelHeight / 2, a.box.height = fb.height - mb.bottom - mb.top + a.labelHeight)
        }

        function A() {
            var b, c = eb.grid.minBorderMargin;
            if (null == c)
                for (c = 0, b = 0; b < db.length; ++b) c = Math.max(c, 2 * (db[b].points.radius + db[b].points.lineWidth / 2));
            var d = {
                "left": c,
                "right": c,
                "top": c,
                "bottom": c
            };
            a.each(n(), function(a, b) {
                b.reserveSpace && b.ticks && b.ticks.length && ("x" === b.direction ? (d.left = Math.max(d.left, b.labelWidth / 2), d.right = Math.max(d.right, b.labelWidth / 2)) : (d.bottom = Math.max(d.bottom, b.labelHeight / 2), d.top = Math.max(d.top, b.labelHeight / 2)))
            }), mb.left = Math.ceil(Math.max(d.left, mb.left)), mb.right = Math.ceil(Math.max(d.right, mb.right)), mb.top = Math.ceil(Math.max(d.top, mb.top)), mb.bottom = Math.ceil(Math.max(d.bottom, mb.bottom))
        }

        function B() {
            var b, c = n(),
                d = eb.grid.show;
            for (var e in mb) {
                var f = eb.grid.margin || 0;
                mb[e] = "number" == typeof f ? f : f[e] || 0
            }
            h(pb.processOffset, [mb]);
            for (var e in mb) mb[e] += "object" == typeof eb.grid.borderWidth ? d ? eb.grid.borderWidth[e] : 0 : d ? eb.grid.borderWidth : 0;
            if (a.each(c, function(a, b) {
                    var c = b.options;
                    b.show = null == c.show ? b.used : c.show, b.reserveSpace = null == c.reserveSpace ? b.show : c.reserveSpace, C(b)
                }), d) {
                var g = a.grep(c, function(a) {
                    return a.show || a.reserveSpace
                });
                for (a.each(g, function(a, b) {
                        D(b), E(b), F(b, b.ticks), x(b)
                    }), b = g.length - 1; b >= 0; --b) y(g[b]);
                A(), a.each(g, function(a, b) {
                    z(b)
                })
            }
            nb = fb.width - mb.left - mb.right, ob = fb.height - mb.bottom - mb.top, a.each(c, function(a, b) {
                w(b)
            }), d && K(), R()
        }

        function C(a) {
            var b = a.options,
                c = +(null != b.min ? b.min : a.datamin),
                d = +(null != b.max ? b.max : a.datamax),
                e = d - c;
            if (0 == e) {
                var f = 0 == d ? 1 : .01;
                null == b.min && (c -= f), (null == b.max || null != b.min) && (d += f)
            } else {
                var g = b.autoscaleMargin;
                null != g && (null == b.min && (c -= e * g, 0 > c && null != a.datamin && a.datamin >= 0 && (c = 0)), null == b.max && (d += e * g, d > 0 && null != a.datamax && a.datamax <= 0 && (d = 0)))
            }
            a.min = c, a.max = d
        }

        function D(b) {
            var c, e = b.options;
            c = "number" == typeof e.ticks && e.ticks > 0 ? e.ticks : .3 * Math.sqrt("x" == b.direction ? fb.width : fb.height);
            var f = (b.max - b.min) / c,
                g = -Math.floor(Math.log(f) / Math.LN10),
                h = e.tickDecimals;
            null != h && g > h && (g = h);
            var i, j = Math.pow(10, -g),
                k = f / j;
            if (1.5 > k ? i = 1 : 3 > k ? (i = 2, k > 2.25 && (null == h || h >= g + 1) && (i = 2.5, ++g)) : i = 7.5 > k ? 5 : 10, i *= j, null != e.minTickSize && i < e.minTickSize && (i = e.minTickSize), b.delta = f, b.tickDecimals = Math.max(0, null != h ? h : g), b.tickSize = e.tickSize || i, "time" == e.mode && !b.tickGenerator) throw new Error("Time mode requires the flot.time plugin.");
            if (b.tickGenerator || (b.tickGenerator = function(a) {
                    var b, c = [],
                        e = d(a.min, a.tickSize),
                        f = 0,
                        g = Number.NaN;
                    do b = g, g = e + f * a.tickSize, c.push(g), ++f; while (g < a.max && g != b);
                    return c
                }, b.tickFormatter = function(a, b) {
                    var c = b.tickDecimals ? Math.pow(10, b.tickDecimals) : 1,
                        d = "" + Math.round(a * c) / c;
                    if (null != b.tickDecimals) {
                        var e = d.indexOf("."),
                            f = -1 == e ? 0 : d.length - e - 1;
                        if (f < b.tickDecimals) return (f ? d : d + ".") + ("" + c).substr(1, b.tickDecimals - f)
                    }
                    return d
                }), a.isFunction(e.tickFormatter) && (b.tickFormatter = function(a, b) {
                    return "" + e.tickFormatter(a, b)
                }), null != e.alignTicksWithAxis) {
                var l = ("x" == b.direction ? kb : lb)[e.alignTicksWithAxis - 1];
                if (l && l.used && l != b) {
                    var m = b.tickGenerator(b);
                    if (m.length > 0 && (null == e.min && (b.min = Math.min(b.min, m[0])), null == e.max && m.length > 1 && (b.max = Math.max(b.max, m[m.length - 1]))), b.tickGenerator = function(a) {
                            var b, c, d = [];
                            for (c = 0; c < l.ticks.length; ++c) b = (l.ticks[c].v - l.min) / (l.max - l.min), b = a.min + b * (a.max - a.min), d.push(b);
                            return d
                        }, !b.mode && null == e.tickDecimals) {
                        var n = Math.max(0, -Math.floor(Math.log(b.delta) / Math.LN10) + 1),
                            o = b.tickGenerator(b);
                        o.length > 1 && /\..*0$/.test((o[1] - o[0]).toFixed(n)) || (b.tickDecimals = n)
                    }
                }
            }
        }

        function E(b) {
            var c = b.options.ticks,
                d = [];
            null == c || "number" == typeof c && c > 0 ? d = b.tickGenerator(b) : c && (d = a.isFunction(c) ? c(b) : c);
            var e, f;
            for (b.ticks = [], e = 0; e < d.length; ++e) {
                var g = null,
                    h = d[e];
                "object" == typeof h ? (f = +h[0], h.length > 1 && (g = h[1])) : f = +h, null == g && (g = b.tickFormatter(f, b)), isNaN(f) || b.ticks.push({
                    "v": f,
                    "label": g
                })
            }
        }

        function F(a, b) {
            a.options.autoscaleMargin && b.length > 0 && (null == a.options.min && (a.min = Math.min(a.min, b[0].v)), null == a.options.max && b.length > 1 && (a.max = Math.max(a.max, b[b.length - 1].v)))
        }

        function G() {
            fb.clear(), h(pb.drawBackground, [ib]);
            var a = eb.grid;
            a.show && a.backgroundColor && I(), a.show && !a.aboveData && J();
            for (var b = 0; b < db.length; ++b) h(pb.drawSeries, [ib, db[b]]), L(db[b]);
            h(pb.draw, [ib]), a.show && a.aboveData && J(), fb.render(), X()
        }

        function H(a, b) {
            for (var c, d, e, f, g = n(), h = 0; h < g.length; ++h)
                if (c = g[h], c.direction == b && (f = b + c.n + "axis", a[f] || 1 != c.n || (f = b + "axis"), a[f])) {
                    d = a[f].from, e = a[f].to;
                    break
                }
            if (a[f] || (c = "x" == b ? kb[0] : lb[0], d = a[b + "1"], e = a[b + "2"]), null != d && null != e && d > e) {
                var i = d;
                d = e, e = i
            }
            return {
                "from": d,
                "to": e,
                "axis": c
            }
        }

        function I() {
            ib.save(), ib.translate(mb.left, mb.top), ib.fillStyle = cb(eb.grid.backgroundColor, ob, 0, "rgba(255, 255, 255, 0)"), ib.fillRect(0, 0, nb, ob), ib.restore()
        }

        function J() {
            var b, c, d, e;
            ib.save(), ib.translate(mb.left, mb.top);
            var f = eb.grid.markings;
            if (f)
                for (a.isFunction(f) && (c = qb.getAxes(), c.xmin = c.xaxis.min, c.xmax = c.xaxis.max, c.ymin = c.yaxis.min, c.ymax = c.yaxis.max, f = f(c)), b = 0; b < f.length; ++b) {
                    var g = f[b],
                        h = H(g, "x"),
                        i = H(g, "y");
                    if (null == h.from && (h.from = h.axis.min), null == h.to && (h.to = h.axis.max), null == i.from && (i.from = i.axis.min), null == i.to && (i.to = i.axis.max), !(h.to < h.axis.min || h.from > h.axis.max || i.to < i.axis.min || i.from > i.axis.max)) {
                        h.from = Math.max(h.from, h.axis.min), h.to = Math.min(h.to, h.axis.max), i.from = Math.max(i.from, i.axis.min), i.to = Math.min(i.to, i.axis.max);
                        var j = h.from === h.to,
                            k = i.from === i.to;
                        if (!j || !k)
                            if (h.from = Math.floor(h.axis.p2c(h.from)), h.to = Math.floor(h.axis.p2c(h.to)), i.from = Math.floor(i.axis.p2c(i.from)), i.to = Math.floor(i.axis.p2c(i.to)), j || k) {
                                var l = g.lineWidth || eb.grid.markingsLineWidth,
                                    m = l % 2 ? .5 : 0;
                                ib.beginPath(), ib.strokeStyle = g.color || eb.grid.markingsColor, ib.lineWidth = l, j ? (ib.moveTo(h.to + m, i.from), ib.lineTo(h.to + m, i.to)) : (ib.moveTo(h.from, i.to + m), ib.lineTo(h.to, i.to + m)), ib.stroke()
                            } else ib.fillStyle = g.color || eb.grid.markingsColor, ib.fillRect(h.from, i.to, h.to - h.from, i.from - i.to)
                    }
                }
            c = n(), d = eb.grid.borderWidth;
            for (var o = 0; o < c.length; ++o) {
                var p, q, r, s, t = c[o],
                    u = t.box,
                    v = t.tickLength;
                if (t.show && 0 != t.ticks.length) {
                    for (ib.lineWidth = 1, "x" == t.direction ? (p = 0, q = "full" == v ? "top" == t.position ? 0 : ob : u.top - mb.top + ("top" == t.position ? u.height : 0)) : (q = 0, p = "full" == v ? "left" == t.position ? 0 : nb : u.left - mb.left + ("left" == t.position ? u.width : 0)), t.innermost || (ib.strokeStyle = t.options.color, ib.beginPath(), r = s = 0, "x" == t.direction ? r = nb + 1 : s = ob + 1, 1 == ib.lineWidth && ("x" == t.direction ? q = Math.floor(q) + .5 : p = Math.floor(p) + .5), ib.moveTo(p, q), ib.lineTo(p + r, q + s), ib.stroke()), ib.strokeStyle = t.options.tickColor, ib.beginPath(), b = 0; b < t.ticks.length; ++b) {
                        var w = t.ticks[b].v;
                        r = s = 0, isNaN(w) || w < t.min || w > t.max || "full" == v && ("object" == typeof d && d[t.position] > 0 || d > 0) && (w == t.min || w == t.max) || ("x" == t.direction ? (p = t.p2c(w), s = "full" == v ? -ob : v, "top" == t.position && (s = -s)) : (q = t.p2c(w), r = "full" == v ? -nb : v, "left" == t.position && (r = -r)), 1 == ib.lineWidth && ("x" == t.direction ? p = Math.floor(p) + .5 : q = Math.floor(q) + .5), ib.moveTo(p, q), ib.lineTo(p + r, q + s))
                    }
                    ib.stroke()
                }
            }
            d && (e = eb.grid.borderColor, "object" == typeof d || "object" == typeof e ? ("object" != typeof d && (d = {
                "top": d,
                "right": d,
                "bottom": d,
                "left": d
            }), "object" != typeof e && (e = {
                "top": e,
                "right": e,
                "bottom": e,
                "left": e
            }), d.top > 0 && (ib.strokeStyle = e.top, ib.lineWidth = d.top, ib.beginPath(), ib.moveTo(0 - d.left, 0 - d.top / 2), ib.lineTo(nb, 0 - d.top / 2), ib.stroke()), d.right > 0 && (ib.strokeStyle = e.right, ib.lineWidth = d.right, ib.beginPath(), ib.moveTo(nb + d.right / 2, 0 - d.top), ib.lineTo(nb + d.right / 2, ob), ib.stroke()), d.bottom > 0 && (ib.strokeStyle = e.bottom, ib.lineWidth = d.bottom, ib.beginPath(), ib.moveTo(nb + d.right, ob + d.bottom / 2), ib.lineTo(0, ob + d.bottom / 2), ib.stroke()), d.left > 0 && (ib.strokeStyle = e.left, ib.lineWidth = d.left, ib.beginPath(), ib.moveTo(0 - d.left / 2, ob + d.bottom), ib.lineTo(0 - d.left / 2, 0), ib.stroke())) : (ib.lineWidth = d, ib.strokeStyle = eb.grid.borderColor, ib.strokeRect(-d / 2, -d / 2, nb + d, ob + d))), ib.restore()
        }

        function K() {
            a.each(n(), function(a, b) {
                var c, d, e, f, g, h = b.box,
                    i = b.direction + "Axis " + b.direction + b.n + "Axis",
                    j = "flot-" + b.direction + "-axis flot-" + b.direction + b.n + "-axis " + i,
                    k = b.options.font || "flot-tick-label tickLabel";
                if (fb.removeText(j), b.show && 0 != b.ticks.length)
                    for (var l = 0; l < b.ticks.length; ++l) c = b.ticks[l], !c.label || c.v < b.min || c.v > b.max || ("x" == b.direction ? (f = "center", d = mb.left + b.p2c(c.v), "bottom" == b.position ? e = h.top + h.padding : (e = h.top + h.height - h.padding, g = "bottom")) : (g = "middle", e = mb.top + b.p2c(c.v), "left" == b.position ? (d = h.left + h.width - h.padding, f = "right") : d = h.left + h.padding), fb.addText(j, d, e, c.label, k, null, null, f, g))
            })
        }

        function L(a) {
            a.lines.show && M(a), a.bars.show && P(a), a.points.show && N(a)
        }

        function M(a) {
            function b(a, b, c, d, e) {
                var f = a.points,
                    g = a.pointsize,
                    h = null,
                    i = null;
                ib.beginPath();
                for (var j = g; j < f.length; j += g) {
                    var k = f[j - g],
                        l = f[j - g + 1],
                        m = f[j],
                        n = f[j + 1];
                    if (null != k && null != m) {
                        if (n >= l && l < e.min) {
                            if (n < e.min) continue;
                            k = (e.min - l) / (n - l) * (m - k) + k, l = e.min
                        } else if (l >= n && n < e.min) {
                            if (l < e.min) continue;
                            m = (e.min - l) / (n - l) * (m - k) + k, n = e.min
                        }
                        if (l >= n && l > e.max) {
                            if (n > e.max) continue;
                            k = (e.max - l) / (n - l) * (m - k) + k, l = e.max
                        } else if (n >= l && n > e.max) {
                            if (l > e.max) continue;
                            m = (e.max - l) / (n - l) * (m - k) + k, n = e.max
                        }
                        if (m >= k && k < d.min) {
                            if (m < d.min) continue;
                            l = (d.min - k) / (m - k) * (n - l) + l, k = d.min
                        } else if (k >= m && m < d.min) {
                            if (k < d.min) continue;
                            n = (d.min - k) / (m - k) * (n - l) + l, m = d.min
                        }
                        if (k >= m && k > d.max) {
                            if (m > d.max) continue;
                            l = (d.max - k) / (m - k) * (n - l) + l, k = d.max
                        } else if (m >= k && m > d.max) {
                            if (k > d.max) continue;
                            n = (d.max - k) / (m - k) * (n - l) + l, m = d.max
                        }(k != h || l != i) && ib.moveTo(d.p2c(k) + b, e.p2c(l) + c), h = m, i = n, ib.lineTo(d.p2c(m) + b, e.p2c(n) + c)
                    }
                }
                ib.stroke()
            }

            function c(a, b, c) {
                for (var d = a.points, e = a.pointsize, f = Math.min(Math.max(0, c.min), c.max), g = 0, h = !1, i = 1, j = 0, k = 0;;) {
                    if (e > 0 && g > d.length + e) break;
                    g += e;
                    var l = d[g - e],
                        m = d[g - e + i],
                        n = d[g],
                        o = d[g + i];
                    if (h) {
                        if (e > 0 && null != l && null == n) {
                            k = g, e = -e, i = 2;
                            continue
                        }
                        if (0 > e && g == j + e) {
                            ib.fill(), h = !1, e = -e, i = 1, g = j = k + e;
                            continue
                        }
                    }
                    if (null != l && null != n) {
                        if (n >= l && l < b.min) {
                            if (n < b.min) continue;
                            m = (b.min - l) / (n - l) * (o - m) + m, l = b.min
                        } else if (l >= n && n < b.min) {
                            if (l < b.min) continue;
                            o = (b.min - l) / (n - l) * (o - m) + m, n = b.min
                        }
                        if (l >= n && l > b.max) {
                            if (n > b.max) continue;
                            m = (b.max - l) / (n - l) * (o - m) + m, l = b.max
                        } else if (n >= l && n > b.max) {
                            if (l > b.max) continue;
                            o = (b.max - l) / (n - l) * (o - m) + m, n = b.max
                        }
                        if (h || (ib.beginPath(), ib.moveTo(b.p2c(l), c.p2c(f)), h = !0), m >= c.max && o >= c.max) ib.lineTo(b.p2c(l), c.p2c(c.max)), ib.lineTo(b.p2c(n), c.p2c(c.max));
                        else if (m <= c.min && o <= c.min) ib.lineTo(b.p2c(l), c.p2c(c.min)), ib.lineTo(b.p2c(n), c.p2c(c.min));
                        else {
                            var p = l,
                                q = n;
                            o >= m && m < c.min && o >= c.min ? (l = (c.min - m) / (o - m) * (n - l) + l, m = c.min) : m >= o && o < c.min && m >= c.min && (n = (c.min - m) / (o - m) * (n - l) + l, o = c.min), m >= o && m > c.max && o <= c.max ? (l = (c.max - m) / (o - m) * (n - l) + l, m = c.max) : o >= m && o > c.max && m <= c.max && (n = (c.max - m) / (o - m) * (n - l) + l, o = c.max), l != p && ib.lineTo(b.p2c(p), c.p2c(m)), ib.lineTo(b.p2c(l), c.p2c(m)), ib.lineTo(b.p2c(n), c.p2c(o)), n != q && (ib.lineTo(b.p2c(n), c.p2c(o)), ib.lineTo(b.p2c(q), c.p2c(o)))
                        }
                    }
                }
            }
            ib.save(), ib.translate(mb.left, mb.top), ib.lineJoin = "round";
            var d = a.lines.lineWidth,
                e = a.shadowSize;
            if (d > 0 && e > 0) {
                ib.lineWidth = e, ib.strokeStyle = "rgba(0,0,0,0.1)";
                var f = Math.PI / 18;
                b(a.datapoints, Math.sin(f) * (d / 2 + e / 2), Math.cos(f) * (d / 2 + e / 2), a.xaxis, a.yaxis), ib.lineWidth = e / 2, b(a.datapoints, Math.sin(f) * (d / 2 + e / 4), Math.cos(f) * (d / 2 + e / 4), a.xaxis, a.yaxis)
            }
            ib.lineWidth = d, ib.strokeStyle = a.color;
            var g = Q(a.lines, a.color, 0, ob);
            g && (ib.fillStyle = g, c(a.datapoints, a.xaxis, a.yaxis)), d > 0 && b(a.datapoints, 0, 0, a.xaxis, a.yaxis), ib.restore()
        }

        function N(a) {
            function b(a, b, c, d, e, f, g, h) {
                for (var i = a.points, j = a.pointsize, k = 0; k < i.length; k += j) {
                    var l = i[k],
                        m = i[k + 1];
                    null == l || l < f.min || l > f.max || m < g.min || m > g.max || (ib.beginPath(), l = f.p2c(l), m = g.p2c(m) + d, "circle" == h ? ib.arc(l, m, b, 0, e ? Math.PI : 2 * Math.PI, !1) : h(ib, l, m, b, e), ib.closePath(), c && (ib.fillStyle = c, ib.fill()), ib.stroke())
                }
            }
            ib.save(), ib.translate(mb.left, mb.top);
            var c = a.points.lineWidth,
                d = a.shadowSize,
                e = a.points.radius,
                f = a.points.symbol;
            if (0 == c && (c = 1e-4), c > 0 && d > 0) {
                var g = d / 2;
                ib.lineWidth = g, ib.strokeStyle = "rgba(0,0,0,0.1)", b(a.datapoints, e, null, g + g / 2, !0, a.xaxis, a.yaxis, f), ib.strokeStyle = "rgba(0,0,0,0.2)", b(a.datapoints, e, null, g / 2, !0, a.xaxis, a.yaxis, f)
            }
            ib.lineWidth = c, ib.strokeStyle = a.color, b(a.datapoints, e, Q(a.points, a.color), 0, !1, a.xaxis, a.yaxis, f), ib.restore()
        }

        function O(a, b, c, d, e, f, g, h, i, j, k) {
            var l, m, n, o, p, q, r, s, t;
            j ? (s = q = r = !0, p = !1, l = c, m = a, o = b + d, n = b + e, l > m && (t = m, m = l, l = t, p = !0, q = !1)) : (p = q = r = !0, s = !1, l = a + d, m = a + e, n = c, o = b, n > o && (t = o, o = n, n = t, s = !0, r = !1)), m < g.min || l > g.max || o < h.min || n > h.max || (l < g.min && (l = g.min, p = !1), m > g.max && (m = g.max, q = !1), n < h.min && (n = h.min, s = !1), o > h.max && (o = h.max, r = !1), l = g.p2c(l), n = h.p2c(n), m = g.p2c(m), o = h.p2c(o), f && (i.fillStyle = f(n, o), i.fillRect(l, o, m - l, n - o)), k > 0 && (p || q || r || s) && (i.beginPath(), i.moveTo(l, n), p ? i.lineTo(l, o) : i.moveTo(l, o), r ? i.lineTo(m, o) : i.moveTo(m, o), q ? i.lineTo(m, n) : i.moveTo(m, n), s ? i.lineTo(l, n) : i.moveTo(l, n), i.stroke()))
        }

        function P(a) {
            function b(b, c, d, e, f, g) {
                for (var h = b.points, i = b.pointsize, j = 0; j < h.length; j += i) null != h[j] && O(h[j], h[j + 1], h[j + 2], c, d, e, f, g, ib, a.bars.horizontal, a.bars.lineWidth)
            }
            ib.save(), ib.translate(mb.left, mb.top), ib.lineWidth = a.bars.lineWidth, ib.strokeStyle = a.color;
            var c;
            switch (a.bars.align) {
                case "left":
                    c = 0;
                    break;
                case "right":
                    c = -a.bars.barWidth;
                    break;
                default:
                    c = -a.bars.barWidth / 2
            }
            var d = a.bars.fill ? function(b, c) {
                return Q(a.bars, a.color, b, c)
            } : null;
            b(a.datapoints, c, c + a.bars.barWidth, d, a.xaxis, a.yaxis), ib.restore()
        }

        function Q(b, c, d, e) {
            var f = b.fill;
            if (!f) return null;
            if (b.fillColor) return cb(b.fillColor, d, e, c);
            var g = a.color.parse(c);
            return g.a = "number" == typeof f ? f : .4, g.normalize(), g.toString()
        }

        function R() {
            if (null != eb.legend.container ? a(eb.legend.container).html("") : c.find(".legend").remove(), eb.legend.show) {
                for (var b, d, e = [], f = [], g = !1, h = eb.legend.labelFormatter, i = 0; i < db.length; ++i) b = db[i], b.label && (d = h ? h(b.label, b) : b.label, d && f.push({
                    "label": d,
                    "color": b.color
                }));
                if (eb.legend.sorted)
                    if (a.isFunction(eb.legend.sorted)) f.sort(eb.legend.sorted);
                    else if ("reverse" == eb.legend.sorted) f.reverse();
                else {
                    var j = "descending" != eb.legend.sorted;
                    f.sort(function(a, b) {
                        return a.label == b.label ? 0 : a.label < b.label != j ? 1 : -1
                    })
                }
                for (var i = 0; i < f.length; ++i) {
                    var k = f[i];
                    i % eb.legend.noColumns == 0 && (g && e.push("</tr>"), e.push("<tr>"), g = !0), e.push('<td class="legendColorBox"><div style="' + eb.legend.labelBoxBorderColor + '"><div style="border:2px solid ' + k.color + ';overflow:hidden"></div></div></td><td class="legendLabel"><span>' + k.label + "</span></td>")
                }
                if (g && e.push("</tr>"), 0 != e.length) {
                    var l = '<table style="font-size:smaller;color:' + eb.grid.color + '">' + e.join("") + "</table>";
                    if (null != eb.legend.container) a(eb.legend.container).html(l);
                    else {
                        var m = "",
                            n = eb.legend.position,
                            o = eb.legend.margin;
                        null == o[0] && (o = [o, o]), "n" == n.charAt(0) ? m += "top:" + (o[1] + mb.top) + "px;" : "s" == n.charAt(0) && (m += "bottom:" + (o[1] + mb.bottom) + "px;"), "e" == n.charAt(1) ? m += "right:" + (o[0] + mb.right) + "px;" : "w" == n.charAt(1) && (m += "left:" + (o[0] + mb.left) + "px;");
                        var p = a('<div class="legend">' + l.replace('style="', 'style="position:absolute;' + m + ";") + "</div>").appendTo(c);
                        if (0 != eb.legend.backgroundOpacity) {
                            var q = eb.legend.backgroundColor;
                            null == q && (q = eb.grid.backgroundColor, q = q && "string" == typeof q ? a.color.parse(q) : a.color.extract(p, "background-color"), q.a = 1, q = q.toString());
                            var r = p.children();
                            a('<div style="position:absolute;width:' + r.width() + "px;height:" + r.height() + "px;" + m + "background-color:" + q + ';"> </div>').prependTo(p).css("opacity", eb.legend.backgroundOpacity)
                        }
                    }
                }
            }
        }

        function S(a, b, c) {
            var d, e, f, g = eb.grid.mouseActiveRadius,
                h = g * g + 1,
                i = null;
            for (d = db.length - 1; d >= 0; --d)
                if (c(db[d])) {
                    var j = db[d],
                        k = j.xaxis,
                        l = j.yaxis,
                        m = j.datapoints.points,
                        n = k.c2p(a),
                        o = l.c2p(b),
                        p = g / k.scale,
                        q = g / l.scale;
                    if (f = j.datapoints.pointsize, k.options.inverseTransform && (p = Number.MAX_VALUE), l.options.inverseTransform && (q = Number.MAX_VALUE), j.lines.show || j.points.show)
                        for (e = 0; e < m.length; e += f) {
                            var r = m[e],
                                s = m[e + 1];
                            if (null != r && !(r - n > p || -p > r - n || s - o > q || -q > s - o)) {
                                var t = Math.abs(k.p2c(r) - a),
                                    u = Math.abs(l.p2c(s) - b),
                                    v = t * t + u * u;
                                h > v && (h = v, i = [d, e / f])
                            }
                        }
                    if (j.bars.show && !i) {
                        var w, x;
                        switch (j.bars.align) {
                            case "left":
                                w = 0;
                                break;
                            case "right":
                                w = -j.bars.barWidth;
                                break;
                            default:
                                w = -j.bars.barWidth / 2
                        }
                        for (x = w + j.bars.barWidth, e = 0; e < m.length; e += f) {
                            var r = m[e],
                                s = m[e + 1],
                                y = m[e + 2];
                            null != r && (db[d].bars.horizontal ? n <= Math.max(y, r) && n >= Math.min(y, r) && o >= s + w && s + x >= o : n >= r + w && r + x >= n && o >= Math.min(y, s) && o <= Math.max(y, s)) && (i = [d, e / f])
                        }
                    }
                }
            return i ? (d = i[0], e = i[1], f = db[d].datapoints.pointsize, {
                "datapoint": db[d].datapoints.points.slice(e * f, (e + 1) * f),
                "dataIndex": e,
                "series": db[d],
                "seriesIndex": d
            }) : null
        }

        function T(a) {
            eb.grid.hoverable && W("plothover", a, function(a) {
                return 0 != a.hoverable
            })
        }

        function U(a) {
            eb.grid.hoverable && W("plothover", a, function() {
                return !1
            })
        }

        function V(a) {
            W("plotclick", a, function(a) {
                return 0 != a.clickable
            })
        }

        function W(a, b, d) {
            var e = hb.offset(),
                f = b.pageX - e.left - mb.left,
                g = b.pageY - e.top - mb.top,
                h = o({
                    "left": f,
                    "top": g
                });
            h.pageX = b.pageX, h.pageY = b.pageY;
            var i = S(f, g, d);
            if (i && (i.pageX = parseInt(i.series.xaxis.p2c(i.datapoint[0]) + e.left + mb.left, 10), i.pageY = parseInt(i.series.yaxis.p2c(i.datapoint[1]) + e.top + mb.top, 10)), eb.grid.autoHighlight) {
                for (var j = 0; j < rb.length; ++j) {
                    var k = rb[j];
                    k.auto != a || i && k.series == i.series && k.point[0] == i.datapoint[0] && k.point[1] == i.datapoint[1] || $(k.series, k.point)
                }
                i && Z(i.series, i.datapoint, a)
            }
            c.trigger(a, [h, i])
        }

        function X() {
            var a = eb.interaction.redrawOverlayInterval;
            return -1 == a ? void Y() : void(sb || (sb = setTimeout(Y, a)))
        }

        function Y() {
            sb = null, jb.save(), gb.clear(), jb.translate(mb.left, mb.top);
            var a, b;
            for (a = 0; a < rb.length; ++a) b = rb[a], b.series.bars.show ? bb(b.series, b.point) : ab(b.series, b.point);
            jb.restore(), h(pb.drawOverlay, [jb])
        }

        function Z(a, b, c) {
            if ("number" == typeof a && (a = db[a]), "number" == typeof b) {
                var d = a.datapoints.pointsize;
                b = a.datapoints.points.slice(d * b, d * (b + 1))
            }
            var e = _(a, b); - 1 == e ? (rb.push({
                "series": a,
                "point": b,
                "auto": c
            }), X()) : c || (rb[e].auto = !1)
        }

        function $(a, b) {
            if (null == a && null == b) return rb = [], void X();
            if ("number" == typeof a && (a = db[a]), "number" == typeof b) {
                var c = a.datapoints.pointsize;
                b = a.datapoints.points.slice(c * b, c * (b + 1))
            }
            var d = _(a, b); - 1 != d && (rb.splice(d, 1), X())
        }

        function _(a, b) {
            for (var c = 0; c < rb.length; ++c) {
                var d = rb[c];
                if (d.series == a && d.point[0] == b[0] && d.point[1] == b[1]) return c
            }
            return -1
        }

        function ab(b, c) {
            var d = c[0],
                e = c[1],
                f = b.xaxis,
                g = b.yaxis,
                h = "string" == typeof b.highlightColor ? b.highlightColor : a.color.parse(b.color).scale("a", .5).toString();
            if (!(d < f.min || d > f.max || e < g.min || e > g.max)) {
                var i = b.points.radius + b.points.lineWidth / 2;
                jb.lineWidth = i, jb.strokeStyle = h;
                var j = 1.5 * i;
                d = f.p2c(d), e = g.p2c(e), jb.beginPath(), "circle" == b.points.symbol ? jb.arc(d, e, j, 0, 2 * Math.PI, !1) : b.points.symbol(jb, d, e, j, !1), jb.closePath(), jb.stroke()
            }
        }

        function bb(b, c) {
            var d, e = "string" == typeof b.highlightColor ? b.highlightColor : a.color.parse(b.color).scale("a", .5).toString(),
                f = e;
            switch (b.bars.align) {
                case "left":
                    d = 0;
                    break;
                case "right":
                    d = -b.bars.barWidth;
                    break;
                default:
                    d = -b.bars.barWidth / 2
            }
            jb.lineWidth = b.bars.lineWidth, jb.strokeStyle = e, O(c[0], c[1], c[2] || 0, d, d + b.bars.barWidth, function() {
                return f
            }, b.xaxis, b.yaxis, jb, b.bars.horizontal, b.bars.lineWidth)
        }

        function cb(b, c, d, e) {
            if ("string" == typeof b) return b;
            for (var f = ib.createLinearGradient(0, d, 0, c), g = 0, h = b.colors.length; h > g; ++g) {
                var i = b.colors[g];
                if ("string" != typeof i) {
                    var j = a.color.parse(e);
                    null != i.brightness && (j = j.scale("rgb", i.brightness)), null != i.opacity && (j.a *= i.opacity), i = j.toString()
                }
                f.addColorStop(g / (h - 1), i)
            }
            return f
        }
        var db = [],
            eb = {
                "colors": a.flot_colors || ["#931313", "#638167", "#65596B", "#60747C", "#B09B5B"],
                "legend": {
                    "show": !0,
                    "noColumns": a.flot_noColumns || 0,
                    "labelFormatter": null,
                    "labelBoxBorderColor": "",
                    "container": null,
                    "position": "ne",
                    "margin": a.flot_margin || [-5, -32],
                    "backgroundColor": a.flot_backgroundColor || "",
                    "backgroundOpacity": a.flot_backgroundOpacity || 1,
                    "sorted": null
                },
                "xaxis": {
                    "show": null,
                    "position": "bottom",
                    "mode": null,
                    "font": null,
                    "color": null,
                    "tickColor": null,
                    "transform": null,
                    "inverseTransform": null,
                    "min": null,
                    "max": null,
                    "autoscaleMargin": null,
                    "ticks": null,
                    "tickFormatter": null,
                    "labelWidth": null,
                    "labelHeight": null,
                    "reserveSpace": null,
                    "tickLength": null,
                    "alignTicksWithAxis": null,
                    "tickDecimals": null,
                    "tickSize": null,
                    "minTickSize": null
                },
                "yaxis": {
                    "autoscaleMargin": .02,
                    "position": "left"
                },
                "xaxes": [],
                "yaxes": [],
                "series": {
                    "points": {
                        "show": !1,
                        "radius": 3,
                        "lineWidth": 2,
                        "fill": !0,
                        "fillColor": "#ffffff",
                        "symbol": "circle"
                    },
                    "lines": {
                        "lineWidth": 2,
                        "fill": !1,
                        "fillColor": null,
                        "steps": !1
                    },
                    "bars": {
                        "show": !1,
                        "lineWidth": a.flot_bars_lineWidth || 1,
                        "barWidth": 1,
                        "fill": !0,
                        "fillColor": a.flot_bars_fillColor || {
                            "colors": [{
                                "opacity": .7
                            }, {
                                "opacity": 1
                            }]
                        },
                        "align": "left",
                        "horizontal": !1,
                        "zero": !0
                    },
                    "shadowSize": a.flot_shadowSize || 0,
                    "highlightColor": null
                },
                "grid": {
                    "show": !0,
                    "aboveData": !1,
                    "color": a.flot_grid_color || "#545454",
                    "backgroundColor": null,
                    "borderColor": a.flot_grid_borderColor || "#efefef",
                    "tickColor": a.flot_grid_tickColor || "rgba(0,0,0,0.06)",
                    "margin": 0,
                    "labelMargin": a.flot_grid_labelMargin || 10,
                    "axisMargin": 8,
                    "borderWidth": a.flot_grid_borderWidth || 0,
                    "minBorderMargin": a.flot_grid_minBorderMargin || 10,
                    "markings": null,
                    "markingsColor": "#f4f4f4",
                    "markingsLineWidth": 2,
                    "clickable": !1,
                    "hoverable": !1,
                    "autoHighlight": !0,
                    "mouseActiveRadius": a.flot_grid_mouseActiveRadius || 5
                },
                "interaction": {
                    "redrawOverlayInterval": 1e3 / 60
                },
                "hooks": {}
            },
            fb = null,
            gb = null,
            hb = null,
            ib = null,
            jb = null,
            kb = [],
            lb = [],
            mb = {
                "left": 0,
                "right": 0,
                "top": 0,
                "bottom": 0
            },
            nb = 0,
            ob = 0,
            pb = {
                "processOptions": [],
                "processRawData": [],
                "processDatapoints": [],
                "processOffset": [],
                "drawBackground": [],
                "drawSeries": [],
                "draw": [],
                "bindEvents": [],
                "drawOverlay": [],
                "shutdown": []
            },
            qb = this;
        qb.setData = k, qb.setupGrid = B, qb.draw = G, qb.getPlaceholder = function() {
            return c
        }, qb.getCanvas = function() {
            return fb.element
        }, qb.getPlotOffset = function() {
            return mb
        }, qb.width = function() {
            return nb
        }, qb.height = function() {
            return ob
        }, qb.offset = function() {
            var a = hb.offset();
            return a.left += mb.left, a.top += mb.top, a
        }, qb.getData = function() {
            return db
        }, qb.getAxes = function() {
            var b = {};
            return a.each(kb.concat(lb), function(a, c) {
                c && (b[c.direction + (1 != c.n ? c.n : "") + "axis"] = c)
            }), b
        }, qb.getXAxes = function() {
            return kb
        }, qb.getYAxes = function() {
            return lb
        }, qb.c2p = o, qb.p2c = p, qb.getOptions = function() {
            return eb
        }, qb.highlight = Z, qb.unhighlight = $, qb.triggerRedrawOverlay = X, qb.pointOffset = function(a) {
            return {
                "left": parseInt(kb[m(a, "x") - 1].p2c(+a.x) + mb.left, 10),
                "top": parseInt(lb[m(a, "y") - 1].p2c(+a.y) + mb.top, 10)
            }
        }, qb.shutdown = v, qb.destroy = function() {
            v(), c.removeData("plot").empty(), db = [], eb = null, fb = null, gb = null, hb = null, ib = null, jb = null, kb = [], lb = [], pb = null, rb = [], qb = null
        }, qb.resize = function() {
            var a = c.width(),
                b = c.height();
            fb.resize(a, b), gb.resize(a, b)
        }, qb.hooks = pb, i(qb), j(f), t(), k(e), B(), G(), u();
        var rb = [],
            sb = null
    }

    function d(a, b) {
        return b * Math.floor(a / b)
    }
    var e = Object.prototype.hasOwnProperty;
    a.fn.detach || (a.fn.detach = function() {
        return this.each(function() {
            this.parentNode && this.parentNode.removeChild(this)
        })
    }), b.prototype.resize = function(a, b) {
        if (0 >= a || 0 >= b) throw new Error("Invalid dimensions for plot, width = " + a + ", height = " + b);
        var c = this.element,
            d = this.context,
            e = this.pixelRatio;
        this.width != a && (c.width = a * e, c.style.width = a + "px", this.width = a), this.height != b && (c.height = b * e, c.style.height = b + "px", this.height = b), d.restore(), d.save(), d.scale(e, e)
    }, b.prototype.clear = function() {
        this.context.clearRect(0, 0, this.width, this.height)
    }, b.prototype.render = function() {
        var a = this._textCache;
        for (var b in a)
            if (e.call(a, b)) {
                var c = this.getTextLayer(b),
                    d = a[b];
                c.hide();
                for (var f in d)
                    if (e.call(d, f)) {
                        var g = d[f];
                        for (var h in g)
                            if (e.call(g, h)) {
                                for (var i, j = g[h].positions, k = 0; i = j[k]; k++) i.active ? i.rendered || (c.append(i.element), i.rendered = !0) : (j.splice(k--, 1), i.rendered && i.element.detach());
                                0 == j.length && delete g[h]
                            }
                    }
                c.show()
            }
    }, b.prototype.getTextLayer = function(b) {
        var c = this.text[b];
        return null == c && (null == this.textContainer && (this.textContainer = a("<div class='flot-text'></div>").css({
            "position": "absolute",
            "top": 0,
            "left": 0,
            "bottom": 0,
            "right": 0,
            "font-size": "smaller",
            "color": "#545454"
        }).insertAfter(this.element)), c = this.text[b] = a("<div></div>").addClass(b).css({
            "position": "absolute",
            "top": 0,
            "left": 0,
            "bottom": 0,
            "right": 0
        }).appendTo(this.textContainer)), c
    }, b.prototype.getTextInfo = function(b, c, d, e, f) {
        var g, h, i, j;
        if (c = "" + c, g = "object" == typeof d ? d.style + " " + d.variant + " " + d.weight + " " + d.size + "px/" + d.lineHeight + "px " + d.family : d, h = this._textCache[b], null == h && (h = this._textCache[b] = {}), i = h[g], null == i && (i = h[g] = {}), j = i[c], null == j) {
            var k = a("<div></div>").html(c).css({
                "position": "absolute",
                "max-width": f,
                "top": -9999
            }).appendTo(this.getTextLayer(b));
            "object" == typeof d ? k.css({
                "font": g,
                "color": d.color
            }) : "string" == typeof d && k.addClass(d), j = i[c] = {
                "width": k.outerWidth(!0),
                "height": k.outerHeight(!0),
                "element": k,
                "positions": []
            }, k.detach()
        }
        return j
    }, b.prototype.addText = function(a, b, c, d, e, f, g, h, i) {
        var j = this.getTextInfo(a, d, e, f, g),
            k = j.positions;
        "center" == h ? b -= j.width / 2 : "right" == h && (b -= j.width), "middle" == i ? c -= j.height / 2 : "bottom" == i && (c -= j.height);
        for (var l, m = 0; l = k[m]; m++)
            if (l.x == b && l.y == c) return void(l.active = !0);
        l = {
            "active": !0,
            "rendered": !1,
            "element": k.length ? j.element.clone() : j.element,
            "x": b,
            "y": c
        }, k.push(l), l.element.css({
            "top": Math.round(c),
            "left": Math.round(b),
            "text-align": h
        })
    }, b.prototype.removeText = function(a, b, c, d, f, g) {
        if (null == d) {
            var h = this._textCache[a];
            if (null != h)
                for (var i in h)
                    if (e.call(h, i)) {
                        var j = h[i];
                        for (var k in j)
                            if (e.call(j, k))
                                for (var l, m = j[k].positions, n = 0; l = m[n]; n++) l.active = !1
                    }
        } else
            for (var l, m = this.getTextInfo(a, d, f, g).positions, n = 0; l = m[n]; n++) l.x == b && l.y == c && (l.active = !1)
    }, a.plot = function(b, d, e) {
        var f = new c(a(b), d, e, a.plot.plugins);
        return f
    }, a.plot.version = "0.8.3", a.plot.plugins = [], a.fn.plot = function(b, c) {
        return this.each(function() {
            a.plot(this, b, c)
        })
    }
}(jQuery);