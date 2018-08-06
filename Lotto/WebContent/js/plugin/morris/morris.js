/*! SmartAdmin - v1.5 - 2014-09-27 */
(function() {
    var a, b, c, d, e = [].slice,
        f = {}.hasOwnProperty,
        g = function(a, b) {
            function c() {
                this.constructor = a
            }
            for (var d in b) f.call(b, d) && (a[d] = b[d]);
            return c.prototype = b.prototype, a.prototype = new c, a.__super__ = b.prototype, a
        },
        h = function(a, b) {
            return function() {
                return a.apply(b, arguments)
            }
        },
        i = [].indexOf || function(a) {
            for (var b = 0, c = this.length; c > b; b++)
                if (b in this && this[b] === a) return b;
            return -1
        };
    b = window.Morris = {}, a = jQuery, b.EventEmitter = function() {
        function a() {}
        return a.prototype.on = function(a, b) {
            return null == this.handlers && (this.handlers = {}), null == this.handlers[a] && (this.handlers[a] = []), this.handlers[a].push(b)
        }, a.prototype.fire = function() {
            var a, b, c, d, f, g, h;
            if (c = arguments[0], a = 2 <= arguments.length ? e.call(arguments, 1) : [], null != this.handlers && null != this.handlers[c]) {
                for (g = this.handlers[c], h = [], d = 0, f = g.length; f > d; d++) b = g[d], h.push(b.apply(null, a));
                return h
            }
        }, a
    }(), b.commas = function(a) {
        var b, c, d, e;
        return null != a ? (d = 0 > a ? "-" : "", b = Math.abs(a), c = Math.floor(b).toFixed(0), d += c.replace(/(?=(?:\d{3})+$)(?!^)/g, ","), e = b.toString(), e.length > c.length && (d += e.slice(c.length)), d) : "-"
    }, b.pad2 = function(a) {
        return (10 > a ? "0" : "") + a
    }, b.Grid = function(c) {
        function d(b) {
            var c = this;
            if (this.el = a("string" == typeof b.element ? document.getElementById(b.element) : b.element), null == this.el || 0 === this.el.length) throw new Error("Graph container element not found");
            "static" === this.el.css("position") && this.el.css("position", "relative"), this.options = a.extend({}, this.gridDefaults, this.defaults || {}, b), "string" == typeof this.options.units && (this.options.postUnits = b.units), this.raphael = new Raphael(this.el[0]), this.elementWidth = null, this.elementHeight = null, this.dirty = !1, this.init && this.init(), this.setData(this.options.data), this.el.bind("mousemove", function(a) {
                var b;
                return b = c.el.offset(), c.fire("hovermove", a.pageX - b.left, a.pageY - b.top)
            }), this.el.bind("mouseout", function() {
                return c.fire("hoverout")
            }), this.el.bind("touchstart touchmove touchend", function(a) {
                var b, d;
                return d = a.originalEvent.touches[0] || a.originalEvent.changedTouches[0], b = c.el.offset(), c.fire("hover", d.pageX - b.left, d.pageY - b.top), d
            }), this.postInit && this.postInit()
        }
        return g(d, c), d.prototype.gridDefaults = {
            "dateFormat": null,
            "axes": !0,
            "grid": !0,
            "gridLineColor": "#aaa",
            "gridStrokeWidth": .5,
            "gridTextColor": "#888",
            "gridTextSize": 12,
            "hideHover": !1,
            "yLabelFormat": null,
            "numLines": 5,
            "padding": 25,
            "parseTime": !0,
            "postUnits": "",
            "preUnits": "",
            "ymax": "auto",
            "ymin": "auto 0",
            "goals": [],
            "goalStrokeWidth": 1,
            "goalLineColors": ["#666633", "#999966", "#cc6666", "#663333"],
            "events": [],
            "eventStrokeWidth": 1,
            "eventLineColors": ["#005a04", "#ccffbb", "#3a5f0b", "#005502"]
        }, d.prototype.setData = function(a, c) {
            var d, e, f, g, h, i, j, k, l, m, n, o;
            return null == c && (c = !0), null == a || 0 === a.length ? (this.data = [], this.raphael.clear(), void(null != this.hover && this.hover.hide())) : (m = this.cumulative ? 0 : null, n = this.cumulative ? 0 : null, this.options.goals.length > 0 && (h = Math.min.apply(null, this.options.goals), g = Math.max.apply(null, this.options.goals), n = null != n ? Math.min(n, h) : h, m = null != m ? Math.max(m, g) : g), this.data = function() {
                var c, d, g;
                for (g = [], f = c = 0, d = a.length; d > c; f = ++c) j = a[f], i = {}, i.label = j[this.options.xkey], this.options.parseTime ? (i.x = b.parseDate(i.label), this.options.dateFormat ? i.label = this.options.dateFormat(i.x) : "number" == typeof i.label && (i.label = new Date(i.label).toString())) : i.x = f, k = 0, i.y = function() {
                    var a, b, c, d;
                    for (c = this.options.ykeys, d = [], e = a = 0, b = c.length; b > a; e = ++a) l = c[e], o = j[l], "string" == typeof o && (o = parseFloat(o)), null != o && "number" != typeof o && (o = null), null != o && (this.cumulative ? k += o : null != m ? (m = Math.max(o, m), n = Math.min(o, n)) : m = n = o), this.cumulative && null != k && (m = Math.max(k, m), n = Math.min(k, n)), d.push(o);
                    return d
                }.call(this), g.push(i);
                return g
            }.call(this), this.options.parseTime && (this.data = this.data.sort(function(a, b) {
                return (a.x > b.x) - (b.x > a.x)
            })), this.xmin = this.data[0].x, this.xmax = this.data[this.data.length - 1].x, this.events = [], this.options.parseTime && this.options.events.length > 0 && (this.events = function() {
                var a, c, e, f;
                for (e = this.options.events, f = [], a = 0, c = e.length; c > a; a++) d = e[a], f.push(b.parseDate(d));
                return f
            }.call(this), this.xmax = Math.max(this.xmax, Math.max.apply(null, this.events)), this.xmin = Math.min(this.xmin, Math.min.apply(null, this.events))), this.xmin === this.xmax && (this.xmin -= 1, this.xmax += 1), this.ymin = this.yboundary("min", n), this.ymax = this.yboundary("max", m), this.ymin === this.ymax && (n && (this.ymin -= 1), this.ymax += 1), this.yInterval = (this.ymax - this.ymin) / (this.options.numLines - 1), this.precision = this.yInterval > 0 && this.yInterval < 1 ? -Math.floor(Math.log(this.yInterval) / Math.log(10)) : 0, this.dirty = !0, c ? this.redraw() : void 0)
        }, d.prototype.yboundary = function(a, b) {
            var c, d;
            return c = this.options["y" + a], "string" == typeof c ? "auto" === c.slice(0, 4) ? c.length > 5 ? (d = parseInt(c.slice(5), 10), null == b ? d : Math[a](b, d)) : null != b ? b : 0 : parseInt(c, 10) : c
        }, d.prototype._calc = function() {
            var a, b, c;
            return c = this.el.width(), a = this.el.height(), (this.elementWidth !== c || this.elementHeight !== a || this.dirty) && (this.elementWidth = c, this.elementHeight = a, this.dirty = !1, this.left = this.options.padding, this.right = this.elementWidth - this.options.padding, this.top = this.options.padding, this.bottom = this.elementHeight - this.options.padding, this.options.axes && (b = Math.max(this.measureText(this.yAxisFormat(this.ymin), this.options.gridTextSize).width, this.measureText(this.yAxisFormat(this.ymax), this.options.gridTextSize).width), this.left += b, this.bottom -= 1.5 * this.options.gridTextSize), this.width = this.right - this.left, this.height = this.bottom - this.top, this.dx = this.width / (this.xmax - this.xmin), this.dy = this.height / (this.ymax - this.ymin), this.calc) ? this.calc() : void 0
        }, d.prototype.transY = function(a) {
            return this.bottom - (a - this.ymin) * this.dy
        }, d.prototype.transX = function(a) {
            return 1 === this.data.length ? (this.left + this.right) / 2 : this.left + (a - this.xmin) * this.dx
        }, d.prototype.redraw = function() {
            return this.raphael.clear(), this._calc(), this.drawGrid(), this.drawGoals(), this.drawEvents(), this.draw ? this.draw() : void 0
        }, d.prototype.measureText = function(a, b) {
            var c, d;
            return null == b && (b = 12), d = this.raphael.text(100, 100, a).attr("font-size", b), c = d.getBBox(), d.remove(), c
        }, d.prototype.yAxisFormat = function(a) {
            return this.yLabelFormat(a)
        }, d.prototype.yLabelFormat = function(a) {
            return "function" == typeof this.options.yLabelFormat ? this.options.yLabelFormat(a) : "" + this.options.preUnits + b.commas(a) + this.options.postUnits
        }, d.prototype.updateHover = function(a, b) {
            var c, d;
            return c = this.hitTest(a, b), null != c ? (d = this.hover).update.apply(d, c) : void 0
        }, d.prototype.drawGrid = function() {
            var a, b, c, d, e, f, g, h;
            if (this.options.grid !== !1 || this.options.axes !== !1) {
                for (a = this.ymin, b = this.ymax, h = [], c = f = a, g = this.yInterval; b >= a ? b >= f : f >= b; c = f += g) d = parseFloat(c.toFixed(this.precision)), e = this.transY(d), this.options.axes && this.drawYAxisLabel(this.left - this.options.padding / 2, e, this.yAxisFormat(d)), h.push(this.options.grid ? this.drawGridLine("M" + this.left + "," + e + "H" + (this.left + this.width)) : void 0);
                return h
            }
        }, d.prototype.drawGoals = function() {
            var a, b, c, d, e, f, g;
            for (f = this.options.goals, g = [], c = d = 0, e = f.length; e > d; c = ++d) b = f[c], a = this.options.goalLineColors[c % this.options.goalLineColors.length], g.push(this.drawGoal(b, a));
            return g
        }, d.prototype.drawEvents = function() {
            var a, b, c, d, e, f, g;
            for (f = this.events, g = [], c = d = 0, e = f.length; e > d; c = ++d) b = f[c], a = this.options.eventLineColors[c % this.options.eventLineColors.length], g.push(this.drawEvent(b, a));
            return g
        }, d.prototype.drawGoal = function(a, b) {
            return this.raphael.path("M" + this.left + "," + this.transY(a) + "H" + this.right).attr("stroke", b).attr("stroke-width", this.options.goalStrokeWidth)
        }, d.prototype.drawEvent = function(a, b) {
            return this.raphael.path("M" + this.transX(a) + "," + this.bottom + "V" + this.top).attr("stroke", b).attr("stroke-width", this.options.eventStrokeWidth)
        }, d.prototype.drawYAxisLabel = function(a, b, c) {
            return this.raphael.text(a, b, c).attr("font-size", this.options.gridTextSize).attr("fill", this.options.gridTextColor).attr("text-anchor", "end")
        }, d.prototype.drawGridLine = function(a) {
            return this.raphael.path(a).attr("stroke", this.options.gridLineColor).attr("stroke-width", this.options.gridStrokeWidth)
        }, d
    }(b.EventEmitter), b.parseDate = function(a) {
        var b, c, d, e, f, g, h, i, j, k, l;
        return "number" == typeof a ? a : (c = a.match(/^(\d+) Q(\d)$/), e = a.match(/^(\d+)-(\d+)$/), f = a.match(/^(\d+)-(\d+)-(\d+)$/), h = a.match(/^(\d+) W(\d+)$/), i = a.match(/^(\d+)-(\d+)-(\d+)[ T](\d+):(\d+)(Z|([+-])(\d\d):?(\d\d))?$/), j = a.match(/^(\d+)-(\d+)-(\d+)[ T](\d+):(\d+):(\d+(\.\d+)?)(Z|([+-])(\d\d):?(\d\d))?$/), c ? new Date(parseInt(c[1], 10), 3 * parseInt(c[2], 10) - 1, 1).getTime() : e ? new Date(parseInt(e[1], 10), parseInt(e[2], 10) - 1, 1).getTime() : f ? new Date(parseInt(f[1], 10), parseInt(f[2], 10) - 1, parseInt(f[3], 10)).getTime() : h ? (k = new Date(parseInt(h[1], 10), 0, 1), 4 !== k.getDay() && k.setMonth(0, 1 + (4 - k.getDay() + 7) % 7), k.getTime() + 6048e5 * parseInt(h[2], 10)) : i ? i[6] ? (g = 0, "Z" !== i[6] && (g = 60 * parseInt(i[8], 10) + parseInt(i[9], 10), "+" === i[7] && (g = 0 - g)), Date.UTC(parseInt(i[1], 10), parseInt(i[2], 10) - 1, parseInt(i[3], 10), parseInt(i[4], 10), parseInt(i[5], 10) + g)) : new Date(parseInt(i[1], 10), parseInt(i[2], 10) - 1, parseInt(i[3], 10), parseInt(i[4], 10), parseInt(i[5], 10)).getTime() : j ? (l = parseFloat(j[6]), b = Math.floor(l), d = Math.round(1e3 * (l - b)), j[8] ? (g = 0, "Z" !== j[8] && (g = 60 * parseInt(j[10], 10) + parseInt(j[11], 10), "+" === j[9] && (g = 0 - g)), Date.UTC(parseInt(j[1], 10), parseInt(j[2], 10) - 1, parseInt(j[3], 10), parseInt(j[4], 10), parseInt(j[5], 10) + g, b, d)) : new Date(parseInt(j[1], 10), parseInt(j[2], 10) - 1, parseInt(j[3], 10), parseInt(j[4], 10), parseInt(j[5], 10), b, d).getTime()) : new Date(parseInt(a, 10), 0, 1).getTime())
    }, b.Hover = function() {
        function c(c) {
            null == c && (c = {}), this.options = a.extend({}, b.Hover.defaults, c), this.el = a("<div class='" + this.options["class"] + "'></div>"), this.el.hide(), this.options.parent.append(this.el)
        }
        return c.defaults = {
            "class": "morris-hover morris-default-style"
        }, c.prototype.update = function(a, b, c) {
            return this.html(a), this.show(), this.moveTo(b, c)
        }, c.prototype.html = function(a) {
            return this.el.html(a)
        }, c.prototype.moveTo = function(a, b) {
            var c, d, e, f, g, h;
            return g = this.options.parent.innerWidth(), f = this.options.parent.innerHeight(), d = this.el.outerWidth(), c = this.el.outerHeight(), e = Math.min(Math.max(0, a - d / 2), g - d), null != b ? (h = b - c - 10, 0 > h && (h = b + 10, h + c > f && (h = f / 2 - c / 2))) : h = f / 2 - c / 2, this.el.css({
                "left": e + "px",
                "top": h + "px"
            })
        }, c.prototype.show = function() {
            return this.el.show()
        }, c.prototype.hide = function() {
            return this.el.hide()
        }, c
    }(), b.Line = function(a) {
        function c(a) {
            return this.hilight = h(this.hilight, this), this.onHoverOut = h(this.onHoverOut, this), this.onHoverMove = h(this.onHoverMove, this), this instanceof b.Line ? void c.__super__.constructor.call(this, a) : new b.Line(a)
        }
        return g(c, a), c.prototype.init = function() {
            return this.pointGrow = Raphael.animation({
                "r": this.options.pointSize + 3
            }, 25, "linear"), this.pointShrink = Raphael.animation({
                "r": this.options.pointSize
            }, 25, "linear"), "always" !== this.options.hideHover ? (this.hover = new b.Hover({
                "parent": this.el
            }), this.on("hovermove", this.onHoverMove), this.on("hoverout", this.onHoverOut)) : void 0
        }, c.prototype.defaults = {
            "lineWidth": 3,
            "pointSize": 4,
            "lineColors": ["#57889c", "#71843f", "#92a2a8", "#afd8f8", "#edc240", "#cb4b4b", "#9440ed"],
            "pointWidths": [1],
            "pointStrokeColors": ["#ffffff"],
            "pointFillColors": [],
            "smooth": !0,
            "xLabels": "auto",
            "xLabelFormat": null,
            "xLabelMargin": 50,
            "continuousLine": !0,
            "hideHover": !1
        }, c.prototype.calc = function() {
            return this.calcPoints(), this.generatePaths()
        }, c.prototype.calcPoints = function() {
            var a, b, c, d, e, f;
            for (e = this.data, f = [], c = 0, d = e.length; d > c; c++) a = e[c], a._x = this.transX(a.x), a._y = function() {
                var c, d, e, f;
                for (e = a.y, f = [], c = 0, d = e.length; d > c; c++) b = e[c], f.push(null != b ? this.transY(b) : b);
                return f
            }.call(this), f.push(a._ymax = Math.min.apply(null, [this.bottom].concat(function() {
                var c, d, e, f;
                for (e = a._y, f = [], c = 0, d = e.length; d > c; c++) b = e[c], null != b && f.push(b);
                return f
            }())));
            return f
        }, c.prototype.hitTest = function(a) {
            var b, c, d, e, f;
            if (0 === this.data.length) return null;
            for (f = this.data.slice(1), b = d = 0, e = f.length; e > d && (c = f[b], !(a < (c._x + this.data[b]._x) / 2)); b = ++d);
            return b
        }, c.prototype.onHoverMove = function(a, b) {
            var c;
            return c = this.hitTest(a, b), this.displayHoverForRow(c)
        }, c.prototype.onHoverOut = function() {
            return "auto" === this.options.hideHover ? this.displayHoverForRow(null) : void 0
        }, c.prototype.displayHoverForRow = function(a) {
            var b;
            return null != a ? ((b = this.hover).update.apply(b, this.hoverContentForRow(a)), this.hilight(a)) : (this.hover.hide(), this.hilight())
        }, c.prototype.hoverContentForRow = function(a) {
            var b, c, d, e, f, g, h;
            if (d = this.data[a], "function" == typeof this.options.hoverCallback) b = this.options.hoverCallback(a, this.options);
            else
                for (b = "<div class='morris-hover-row-label'>" + d.label + "</div>", h = d.y, c = f = 0, g = h.length; g > f; c = ++f) e = h[c], b += "<div class='morris-hover-point' style='color: " + this.colorFor(d, c, "label") + "'>\n  " + this.options.labels[c] + ":\n  " + this.yLabelFormat(e) + "\n</div>";
            return [b, d._x, d._ymax]
        }, c.prototype.generatePaths = function() {
            var a, c, d, e, f;
            return this.paths = function() {
                var g, h, j, k;
                for (k = [], d = g = 0, h = this.options.ykeys.length; h >= 0 ? h > g : g > h; d = h >= 0 ? ++g : --g) f = this.options.smooth === !0 || (j = this.options.ykeys[d], i.call(this.options.smooth, j) >= 0), c = function() {
                    var a, b, c, f;
                    for (c = this.data, f = [], a = 0, b = c.length; b > a; a++) e = c[a], void 0 !== e._y[d] && f.push({
                        "x": e._x,
                        "y": e._y[d]
                    });
                    return f
                }.call(this), this.options.continuousLine && (c = function() {
                    var b, d, e;
                    for (e = [], b = 0, d = c.length; d > b; b++) a = c[b], null !== a.y && e.push(a);
                    return e
                }()), k.push(c.length > 1 ? b.Line.createPath(c, f, this.bottom) : null);
                return k
            }.call(this)
        }, c.prototype.draw = function() {
            return this.options.axes && this.drawXAxis(), this.drawSeries(), this.options.hideHover === !1 ? this.displayHoverForRow(this.data.length - 1) : void 0
        }, c.prototype.drawXAxis = function() {
            var a, c, d, e, f, g, h, i, j, k = this;
            for (g = this.bottom + 1.25 * this.options.gridTextSize, e = null, a = function(a, b) {
                    var c, d;
                    return c = k.drawXAxisLabel(k.transX(b), g, a), d = c.getBBox(), (null == e || e >= d.x + d.width) && d.x >= 0 && d.x + d.width < k.el.width() ? e = d.x - k.options.xLabelMargin : c.remove()
                }, d = this.options.parseTime ? 1 === this.data.length && "auto" === this.options.xLabels ? [
                    [this.data[0].label, this.data[0].x]
                ] : b.labelSeries(this.xmin, this.xmax, this.width, this.options.xLabels, this.options.xLabelFormat) : function() {
                    var a, b, c, d;
                    for (c = this.data, d = [], a = 0, b = c.length; b > a; a++) f = c[a], d.push([f.label, f.x]);
                    return d
                }.call(this), d.reverse(), j = [], h = 0, i = d.length; i > h; h++) c = d[h], j.push(a(c[0], c[1]));
            return j
        }, c.prototype.drawSeries = function() {
            var a, b, c, d, e, f, g, h, i;
            for (b = e = g = this.options.ykeys.length - 1; 0 >= g ? 0 >= e : e >= 0; b = 0 >= g ? ++e : --e) c = this.paths[b], null !== c && this.drawLinePath(c, this.colorFor(d, b, "line"));
            for (this.seriesPoints = function() {
                    var a, c, d;
                    for (d = [], b = a = 0, c = this.options.ykeys.length; c >= 0 ? c > a : a > c; b = c >= 0 ? ++a : --a) d.push([]);
                    return d
                }.call(this), i = [], b = f = h = this.options.ykeys.length - 1; 0 >= h ? 0 >= f : f >= 0; b = 0 >= h ? ++f : --f) i.push(function() {
                var c, e, f, g;
                for (f = this.data, g = [], c = 0, e = f.length; e > c; c++) d = f[c], a = null != d._y[b] ? this.drawLinePoint(d._x, d._y[b], this.options.pointSize, this.colorFor(d, b, "point"), b) : null, g.push(this.seriesPoints[b].push(a));
                return g
            }.call(this));
            return i
        }, c.createPath = function(a, c, d) {
            var e, f, g, h, i, j, k, l, m, n, o, p, q, r;
            for (k = "", c && (g = b.Line.gradients(a)), l = {
                    "y": null
                }, h = q = 0, r = a.length; r > q; h = ++q) e = a[h], null != e.y && (null != l.y ? c ? (f = g[h], j = g[h - 1], i = (e.x - l.x) / 4, m = l.x + i, o = Math.min(d, l.y + i * j), n = e.x - i, p = Math.min(d, e.y - i * f), k += "C" + m + "," + o + "," + n + "," + p + "," + e.x + "," + e.y) : k += "L" + e.x + "," + e.y : c && null == g[h] || (k += "M" + e.x + "," + e.y)), l = e;
            return k
        }, c.gradients = function(a) {
            var b, c, d, e, f, g, h, i;
            for (c = function(a, b) {
                    return (a.y - b.y) / (a.x - b.x)
                }, i = [], d = g = 0, h = a.length; h > g; d = ++g) b = a[d], null != b.y ? (e = a[d + 1] || {
                "y": null
            }, f = a[d - 1] || {
                "y": null
            }, i.push(null != f.y && null != e.y ? c(f, e) : null != f.y ? c(f, b) : null != e.y ? c(b, e) : null)) : i.push(null);
            return i
        }, c.prototype.hilight = function(a) {
            var b, c, d, e, f;
            if (null !== this.prevHilight && this.prevHilight !== a)
                for (b = c = 0, e = this.seriesPoints.length - 1; e >= 0 ? e >= c : c >= e; b = e >= 0 ? ++c : --c) this.seriesPoints[b][this.prevHilight] && this.seriesPoints[b][this.prevHilight].animate(this.pointShrink);
            if (null !== a && this.prevHilight !== a)
                for (b = d = 0, f = this.seriesPoints.length - 1; f >= 0 ? f >= d : d >= f; b = f >= 0 ? ++d : --d) this.seriesPoints[b][a] && this.seriesPoints[b][a].animate(this.pointGrow);
            return this.prevHilight = a
        }, c.prototype.colorFor = function(a, b, c) {
            return "function" == typeof this.options.lineColors ? this.options.lineColors.call(this, a, b, c) : "point" === c ? this.options.pointFillColors[b % this.options.pointFillColors.length] || this.options.lineColors[b % this.options.lineColors.length] : this.options.lineColors[b % this.options.lineColors.length]
        }, c.prototype.drawXAxisLabel = function(a, b, c) {
            return this.raphael.text(a, b, c).attr("font-size", this.options.gridTextSize).attr("fill", this.options.gridTextColor)
        }, c.prototype.drawLinePath = function(a, b) {
            return this.raphael.path(a).attr("stroke", b).attr("stroke-width", this.options.lineWidth)
        }, c.prototype.drawLinePoint = function(a, b, c, d, e) {
            return this.raphael.circle(a, b, c).attr("fill", d).attr("stroke-width", this.strokeWidthForSeries(e)).attr("stroke", this.strokeForSeries(e))
        }, c.prototype.strokeWidthForSeries = function(a) {
            return this.options.pointWidths[a % this.options.pointWidths.length]
        }, c.prototype.strokeForSeries = function(a) {
            return this.options.pointStrokeColors[a % this.options.pointStrokeColors.length]
        }, c
    }(b.Grid), b.labelSeries = function(c, d, e, f, g) {
        var h, i, j, k, l, m, n, o, p, q, r;
        if (j = 200 * (d - c) / e, i = new Date(c), n = b.LABEL_SPECS[f], void 0 === n)
            for (r = b.AUTO_LABEL_ORDER, p = 0, q = r.length; q > p; p++)
                if (k = r[p], m = b.LABEL_SPECS[k], j >= m.span) {
                    n = m;
                    break
                }
        for (void 0 === n && (n = b.LABEL_SPECS.second), g && (n = a.extend({}, n, {
                "fmt": g
            })), h = n.start(i), l = [];
            (o = h.getTime()) <= d;) o >= c && l.push([n.fmt(h), o]), n.incr(h);
        return l
    }, c = function(a) {
        return {
            "span": 60 * a * 1e3,
            "start": function(a) {
                return new Date(a.getFullYear(), a.getMonth(), a.getDate(), a.getHours())
            },
            "fmt": function(a) {
                return "" + b.pad2(a.getHours()) + ":" + b.pad2(a.getMinutes())
            },
            "incr": function(b) {
                return b.setMinutes(b.getMinutes() + a)
            }
        }
    }, d = function(a) {
        return {
            "span": 1e3 * a,
            "start": function(a) {
                return new Date(a.getFullYear(), a.getMonth(), a.getDate(), a.getHours(), a.getMinutes())
            },
            "fmt": function(a) {
                return "" + b.pad2(a.getHours()) + ":" + b.pad2(a.getMinutes()) + ":" + b.pad2(a.getSeconds())
            },
            "incr": function(b) {
                return b.setSeconds(b.getSeconds() + a)
            }
        }
    }, b.LABEL_SPECS = {
        "decade": {
            "span": 1728e8,
            "start": function(a) {
                return new Date(a.getFullYear() - a.getFullYear() % 10, 0, 1)
            },
            "fmt": function(a) {
                return "" + a.getFullYear()
            },
            "incr": function(a) {
                return a.setFullYear(a.getFullYear() + 10)
            }
        },
        "year": {
            "span": 1728e7,
            "start": function(a) {
                return new Date(a.getFullYear(), 0, 1)
            },
            "fmt": function(a) {
                return "" + a.getFullYear()
            },
            "incr": function(a) {
                return a.setFullYear(a.getFullYear() + 1)
            }
        },
        "month": {
            "span": 24192e5,
            "start": function(a) {
                return new Date(a.getFullYear(), a.getMonth(), 1)
            },
            "fmt": function(a) {
                return "" + a.getFullYear() + "-" + b.pad2(a.getMonth() + 1)
            },
            "incr": function(a) {
                return a.setMonth(a.getMonth() + 1)
            }
        },
        "day": {
            "span": 864e5,
            "start": function(a) {
                return new Date(a.getFullYear(), a.getMonth(), a.getDate())
            },
            "fmt": function(a) {
                return "" + a.getFullYear() + "-" + b.pad2(a.getMonth() + 1) + "-" + b.pad2(a.getDate())
            },
            "incr": function(a) {
                return a.setDate(a.getDate() + 1)
            }
        },
        "hour": c(60),
        "30min": c(30),
        "15min": c(15),
        "10min": c(10),
        "5min": c(5),
        "minute": c(1),
        "30sec": d(30),
        "15sec": d(15),
        "10sec": d(10),
        "5sec": d(5),
        "second": d(1)
    }, b.AUTO_LABEL_ORDER = ["decade", "year", "month", "day", "hour", "30min", "15min", "10min", "5min", "minute", "30sec", "15sec", "10sec", "5sec", "second"], b.Area = function(a) {
        function c(a) {
            return this instanceof b.Area ? (this.cumulative = !0, void c.__super__.constructor.call(this, a)) : new b.Area(a)
        }
        return g(c, a), c.prototype.calcPoints = function() {
            var a, b, c, d, e, f, g;
            for (f = this.data, g = [], d = 0, e = f.length; e > d; d++) a = f[d], a._x = this.transX(a.x), b = 0, a._y = function() {
                var d, e, f, g;
                for (f = a.y, g = [], d = 0, e = f.length; e > d; d++) c = f[d], b += c || 0, g.push(this.transY(b));
                return g
            }.call(this), g.push(a._ymax = a._y[a._y.length - 1]);
            return g
        }, c.prototype.drawSeries = function() {
            var a, b, d, e;
            for (a = d = e = this.options.ykeys.length - 1; 0 >= e ? 0 >= d : d >= 0; a = 0 >= e ? ++d : --d) b = this.paths[a], null !== b && (b += "L" + this.transX(this.xmax) + "," + this.bottom + "L" + this.transX(this.xmin) + "," + this.bottom + "Z", this.drawFilledPath(b, this.fillForSeries(a)));
            return c.__super__.drawSeries.call(this)
        }, c.prototype.fillForSeries = function(a) {
            var b;
            return b = Raphael.rgb2hsl(this.colorFor(this.data[a], a, "line")), Raphael.hsl(b.h, Math.min(255, .75 * b.s), Math.min(255, 1.25 * b.l))
        }, c.prototype.drawFilledPath = function(a, b) {
            return this.raphael.path(a).attr("fill", b).attr("stroke-width", 0)
        }, c
    }(b.Line), b.Bar = function(c) {
        function d(c) {
            return this.onHoverOut = h(this.onHoverOut, this), this.onHoverMove = h(this.onHoverMove, this), this instanceof b.Bar ? void d.__super__.constructor.call(this, a.extend({}, c, {
                "parseTime": !1
            })) : new b.Bar(c)
        }
        return g(d, c), d.prototype.init = function() {
            return this.cumulative = this.options.stacked, "always" !== this.options.hideHover ? (this.hover = new b.Hover({
                "parent": this.el
            }), this.on("hovermove", this.onHoverMove), this.on("hoverout", this.onHoverOut)) : void 0
        }, d.prototype.defaults = {
            "barSizeRatio": .75,
            "barGap": 3,
            "barColors": ["#57889c", "#71843f", "#92a2a8", "#afd8f8", "#edc240", "#cb4b4b", "#9440ed"],
            "xLabelMargin": 50
        }, d.prototype.calc = function() {
            var a;
            return this.calcBars(), this.options.hideHover === !1 ? (a = this.hover).update.apply(a, this.hoverContentForRow(this.data.length - 1)) : void 0
        }, d.prototype.calcBars = function() {
            var a, b, c, d, e, f, g;
            for (f = this.data, g = [], a = d = 0, e = f.length; e > d; a = ++d) b = f[a], b._x = this.left + this.width * (a + .5) / this.data.length, g.push(b._y = function() {
                var a, d, e, f;
                for (e = b.y, f = [], a = 0, d = e.length; d > a; a++) c = e[a], f.push(null != c ? this.transY(c) : null);
                return f
            }.call(this));
            return g
        }, d.prototype.draw = function() {
            return this.options.axes && this.drawXAxis(), this.drawSeries()
        }, d.prototype.drawXAxis = function() {
            var a, b, c, d, e, f, g, h, i;
            for (f = this.bottom + 1.25 * this.options.gridTextSize, d = null, i = [], a = g = 0, h = this.data.length; h >= 0 ? h > g : g > h; a = h >= 0 ? ++g : --g) e = this.data[this.data.length - 1 - a], b = this.drawXAxisLabel(e._x, f, e.label), c = b.getBBox(), i.push((null == d || d >= c.x + c.width) && c.x >= 0 && c.x + c.width < this.el.width() ? d = c.x - this.options.xLabelMargin : b.remove());
            return i
        }, d.prototype.drawSeries = function() {
            var a, b, c, d, e, f, g, h, i, j, k, l, m, n;
            return c = this.width / this.options.data.length, h = null != this.options.stacked ? 1 : this.options.ykeys.length, a = (c * this.options.barSizeRatio - this.options.barGap * (h - 1)) / h, g = c * (1 - this.options.barSizeRatio) / 2, n = this.ymin <= 0 && this.ymax >= 0 ? this.transY(0) : null, this.bars = function() {
                var h, o, p, q;
                for (p = this.data, q = [], d = h = 0, o = p.length; o > h; d = ++h) i = p[d], e = 0, q.push(function() {
                    var h, o, p, q;
                    for (p = i._y, q = [], j = h = 0, o = p.length; o > h; j = ++h) m = p[j], null !== m ? (n ? (l = Math.min(m, n), b = Math.max(m, n)) : (l = m, b = this.bottom), f = this.left + d * c + g, this.options.stacked || (f += j * (a + this.options.barGap)), k = b - l, this.options.stacked && (l -= e), this.drawBar(f, l, a, k, this.colorFor(i, j, "bar")), q.push(e += k)) : q.push(null);
                    return q
                }.call(this));
                return q
            }.call(this)
        }, d.prototype.colorFor = function(a, b, c) {
            var d, e;
            return "function" == typeof this.options.barColors ? (d = {
                "x": a.x,
                "y": a.y[b],
                "label": a.label
            }, e = {
                "index": b,
                "key": this.options.ykeys[b],
                "label": this.options.labels[b]
            }, this.options.barColors.call(this, d, e, c)) : this.options.barColors[b % this.options.barColors.length]
        }, d.prototype.hitTest = function(a) {
            return 0 === this.data.length ? null : (a = Math.max(Math.min(a, this.right), this.left), Math.min(this.data.length - 1, Math.floor((a - this.left) / (this.width / this.data.length))))
        }, d.prototype.onHoverMove = function(a, b) {
            var c, d;
            return c = this.hitTest(a, b), (d = this.hover).update.apply(d, this.hoverContentForRow(c))
        }, d.prototype.onHoverOut = function() {
            return "auto" === this.options.hideHover ? this.hover.hide() : void 0
        }, d.prototype.hoverContentForRow = function(a) {
            var b, c, d, e, f, g, h, i;
            if ("function" == typeof this.options.hoverCallback) b = this.options.hoverCallback(a, this.options);
            else
                for (d = this.data[a], b = "<div class='morris-hover-row-label'>" + d.label + "</div>", i = d.y, c = g = 0, h = i.length; h > g; c = ++g) f = i[c], b += "<div class='morris-hover-point' style='color: " + this.colorFor(d, c, "label") + "'>\n  " + this.options.labels[c] + ":\n  " + this.yLabelFormat(f) + "\n</div>";
            return e = this.left + (a + .5) * this.width / this.data.length, [b, e]
        }, d.prototype.drawXAxisLabel = function(a, b, c) {
            var d;
            return d = this.raphael.text(a, b, c).attr("font-size", this.options.gridTextSize).attr("fill", this.options.gridTextColor)
        }, d.prototype.drawBar = function(a, b, c, d, e) {
            return this.raphael.rect(a, b, c, d).attr("fill", e).attr("stroke-width", 0)
        }, d
    }(b.Grid), b.Donut = function() {
        function c(c) {
            if (this.select = h(this.select, this), !(this instanceof b.Donut)) return new b.Donut(c);
            if (this.el = a("string" == typeof c.element ? document.getElementById(c.element) : c.element), this.options = a.extend({}, this.defaults, c), null === this.el || 0 === this.el.length) throw new Error("Graph placeholder not found.");
            void 0 !== c.data && 0 !== c.data.length && (this.data = c.data, this.redraw())
        }
        return c.prototype.defaults = {
            "colors": ["#57889c", "#3980B5", "#679DC6", "#95BBD7", "#B0CCE1", "#095791", "#095085", "#083E67", "#052C48", "#042135"],
            "backgroundColor": "#FFFFFF",
            "labelColor": "#000000",
            "formatter": b.commas
        }, c.prototype.redraw = function() {
            var a, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x;
            for (this.el.empty(), this.raphael = new Raphael(this.el[0]), c = this.el.width() / 2, d = this.el.height() / 2, m = (Math.min(c, d) - 10) / 3, l = 0, u = this.data, o = 0, r = u.length; r > o; o++) n = u[o], l += n.value;
            for (i = 5 / (2 * m), a = 1.9999 * Math.PI - i * this.data.length, g = 0, f = 0, this.segments = [], v = this.data, p = 0, s = v.length; s > p; p++) e = v[p], j = g + i + a * (e.value / l), k = new b.DonutSegment(c, d, 2 * m, m, g, j, this.options.colors[f % this.options.colors.length], this.options.backgroundColor, e, this.raphael), k.render(), this.segments.push(k), k.on("hover", this.select), g = j, f += 1;
            for (this.text1 = this.drawEmptyDonutLabel(c, d - 10, this.options.labelColor, 15, 800), this.text2 = this.drawEmptyDonutLabel(c, d + 10, this.options.labelColor, 14), h = Math.max.apply(null, function() {
                    var a, b, c, d;
                    for (c = this.data, d = [], a = 0, b = c.length; b > a; a++) e = c[a], d.push(e.value);
                    return d
                }.call(this)), f = 0, w = this.data, x = [], q = 0, t = w.length; t > q; q++) {
                if (e = w[q], e.value === h) {
                    this.select(f);
                    break
                }
                x.push(f += 1)
            }
            return x
        }, c.prototype.select = function(a) {
            var b, c, d, e, f;
            for (f = this.segments, d = 0, e = f.length; e > d; d++) b = f[d], b.deselect();
            return c = "number" == typeof a ? this.segments[a] : a, c.select(), this.setLabels(c.data.label, this.options.formatter(c.data.value, c.data))
        }, c.prototype.setLabels = function(a, b) {
            var c, d, e, f, g, h, i, j;
            return c = 2 * (Math.min(this.el.width() / 2, this.el.height() / 2) - 10) / 3, f = 1.8 * c, e = c / 2, d = c / 3, this.text1.attr({
                "text": a,
                "transform": ""
            }), g = this.text1.getBBox(), h = Math.min(f / g.width, e / g.height), this.text1.attr({
                "transform": "S" + h + "," + h + "," + (g.x + g.width / 2) + "," + (g.y + g.height)
            }), this.text2.attr({
                "text": b,
                "transform": ""
            }), i = this.text2.getBBox(), j = Math.min(f / i.width, d / i.height), this.text2.attr({
                "transform": "S" + j + "," + j + "," + (i.x + i.width / 2) + "," + i.y
            })
        }, c.prototype.drawEmptyDonutLabel = function(a, b, c, d, e) {
            var f;
            return f = this.raphael.text(a, b, "").attr("font-size", d).attr("fill", c), null != e && f.attr("font-weight", e), f
        }, c
    }(), b.DonutSegment = function(a) {
        function b(a, b, c, d, e, f, g, i, j, k) {
            this.cx = a, this.cy = b, this.inner = c, this.outer = d, this.color = g, this.backgroundColor = i, this.data = j, this.raphael = k, this.deselect = h(this.deselect, this), this.select = h(this.select, this), this.sin_p0 = Math.sin(e), this.cos_p0 = Math.cos(e), this.sin_p1 = Math.sin(f), this.cos_p1 = Math.cos(f), this.is_long = f - e > Math.PI ? 1 : 0, this.path = this.calcSegment(this.inner + 3, this.inner + this.outer - 5), this.selectedPath = this.calcSegment(this.inner + 3, this.inner + this.outer), this.hilight = this.calcArc(this.inner)
        }
        return g(b, a), b.prototype.calcArcPoints = function(a) {
            return [this.cx + a * this.sin_p0, this.cy + a * this.cos_p0, this.cx + a * this.sin_p1, this.cy + a * this.cos_p1]
        }, b.prototype.calcSegment = function(a, b) {
            var c, d, e, f, g, h, i, j, k, l;
            return k = this.calcArcPoints(a), c = k[0], e = k[1], d = k[2], f = k[3], l = this.calcArcPoints(b), g = l[0], i = l[1], h = l[2], j = l[3], "M" + c + "," + e + ("A" + a + "," + a + ",0," + this.is_long + ",0," + d + "," + f) + ("L" + h + "," + j) + ("A" + b + "," + b + ",0," + this.is_long + ",1," + g + "," + i) + "Z"
        }, b.prototype.calcArc = function(a) {
            var b, c, d, e, f;
            return f = this.calcArcPoints(a), b = f[0], d = f[1], c = f[2], e = f[3], "M" + b + "," + d + ("A" + a + "," + a + ",0," + this.is_long + ",0," + c + "," + e)
        }, b.prototype.render = function() {
            var a = this;
            return this.arc = this.drawDonutArc(this.hilight, this.color), this.seg = this.drawDonutSegment(this.path, this.color, this.backgroundColor, function() {
                return a.fire("hover", a)
            })
        }, b.prototype.drawDonutArc = function(a, b) {
            return this.raphael.path(a).attr({
                "stroke": b,
                "stroke-width": 2,
                "opacity": 0
            })
        }, b.prototype.drawDonutSegment = function(a, b, c, d) {
            return this.raphael.path(a).attr({
                "fill": b,
                "stroke": c,
                "stroke-width": 3
            }).hover(d)
        }, b.prototype.select = function() {
            return this.selected ? void 0 : (this.seg.animate({
                "path": this.selectedPath
            }, 150, "<>"), this.arc.animate({
                "opacity": 1
            }, 150, "<>"), this.selected = !0)
        }, b.prototype.deselect = function() {
            return this.selected ? (this.seg.animate({
                "path": this.path
            }, 150, "<>"), this.arc.animate({
                "opacity": 0
            }, 150, "<>"), this.selected = !1) : void 0
        }, b
    }(b.EventEmitter)
}).call(this);