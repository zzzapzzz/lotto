/*! SmartAdmin - v1.5 - 2014-09-27 */ ! function(a) {
    function b(e) {
        var j, k = this,
            l = e.data || {};
        if (l.elem) k = e.dragTarget = l.elem, e.dragProxy = i.proxy || k, e.cursorOffsetX = l.pageX - l.left, e.cursorOffsetY = l.pageY - l.top, e.offsetX = e.pageX - e.cursorOffsetX, e.offsetY = e.pageY - e.cursorOffsetY;
        else if (i.dragging || l.which > 0 && e.which != l.which || a(e.target).is(l.not)) return;
        switch (e.type) {
            case "mousedown":
                return a.extend(l, a(k).offset(), {
                    "elem": k,
                    "target": e.target,
                    "pageX": e.pageX,
                    "pageY": e.pageY
                }), g.add(document, "mousemove mouseup", b, l), f(k, !1), i.dragging = null, !1;
            case !i.dragging && "mousemove":
                if (d(e.pageX - l.pageX) + d(e.pageY - l.pageY) < l.distance) break;
                e.target = l.target, j = c(e, "dragstart", k), j !== !1 && (i.dragging = k, i.proxy = e.dragProxy = a(j || k)[0]);
            case "mousemove":
                if (i.dragging) {
                    if (j = c(e, "drag", k), h.drop && (h.drop.allowed = j !== !1, h.drop.handler(e)), j !== !1) break;
                    e.type = "mouseup"
                }
            case "mouseup":
                g.remove(document, "mousemove mouseup", b), i.dragging && (h.drop && h.drop.handler(e), c(e, "dragend", k)), f(k, !0), i.dragging = i.proxy = l.elem = !1
        }
        return !0
    }

    function c(b, c, d) {
        b.type = c;
        var e = a.event.dispatch.call(d, b);
        return e === !1 ? !1 : e || b.result
    }

    function d(a) {
        return Math.pow(a, 2)
    }

    function e() {
        return i.dragging === !1
    }

    function f(a, b) {
        a && (a.unselectable = b ? "off" : "on", a.onselectstart = function() {
            return b
        }, a.style && (a.style.MozUserSelect = b ? "" : "none"))
    }
    a.fn.drag = function(a, b, c) {
        return b && this.bind("dragstart", a), c && this.bind("dragend", c), a ? this.bind("drag", b ? b : a) : this.trigger("drag")
    };
    var g = a.event,
        h = g.special,
        i = h.drag = {
            "not": ":input",
            "distance": 0,
            "which": 1,
            "dragging": !1,
            "setup": function(c) {
                c = a.extend({
                    "distance": i.distance,
                    "which": i.which,
                    "not": i.not
                }, c || {}), c.distance = d(c.distance), g.add(this, "mousedown", b, c), this.attachEvent && this.attachEvent("ondragstart", e)
            },
            "teardown": function() {
                g.remove(this, "mousedown", b), this === i.dragging && (i.dragging = i.proxy = !1), f(this, !0), this.detachEvent && this.detachEvent("ondragstart", e)
            }
        };
    h.dragstart = h.dragend = {
        "setup": function() {},
        "teardown": function() {}
    }
}(jQuery),
function(a) {
    function b(b) {
        var c = b || window.event,
            d = [].slice.call(arguments, 1),
            e = 0,
            f = 0,
            g = 0,
            b = a.event.fix(c);
        return b.type = "mousewheel", c.wheelDelta && (e = c.wheelDelta / 120), c.detail && (e = -c.detail / 3), g = e, void 0 !== c.axis && c.axis === c.HORIZONTAL_AXIS && (g = 0, f = -1 * e), void 0 !== c.wheelDeltaY && (g = c.wheelDeltaY / 120), void 0 !== c.wheelDeltaX && (f = -1 * c.wheelDeltaX / 120), d.unshift(b, e, f, g), (a.event.dispatch || a.event.handle).apply(this, d)
    }
    var c = ["DOMMouseScroll", "mousewheel"];
    if (a.event.fixHooks)
        for (var d = c.length; d;) a.event.fixHooks[c[--d]] = a.event.mouseHooks;
    a.event.special.mousewheel = {
        "setup": function() {
            if (this.addEventListener)
                for (var a = c.length; a;) this.addEventListener(c[--a], b, !1);
            else this.onmousewheel = b
        },
        "teardown": function() {
            if (this.removeEventListener)
                for (var a = c.length; a;) this.removeEventListener(c[--a], b, !1);
            else this.onmousewheel = null
        }
    }, a.fn.extend({
        "mousewheel": function(a) {
            return a ? this.bind("mousewheel", a) : this.trigger("mousewheel")
        },
        "unmousewheel": function(a) {
            return this.unbind("mousewheel", a)
        }
    })
}(jQuery),
function(a) {
    function b(b) {
        function c(a, c) {
            var d = b.offset();
            d.left = a.pageX - d.left, d.top = a.pageY - d.top, c ? b.zoomOut({
                "center": d
            }) : b.zoom({
                "center": d
            })
        }

        function d(a, b) {
            return a.preventDefault(), c(a, 0 > b), !1
        }

        function e(a) {
            if (1 != a.which) return !1;
            var c = b.getPlaceholder().css("cursor");
            c && (j = c), b.getPlaceholder().css("cursor", b.getOptions().pan.cursor), k = a.pageX, l = a.pageY
        }

        function f(a) {
            var c = b.getOptions().pan.frameRate;
            !m && c && (m = setTimeout(function() {
                b.pan({
                    "left": k - a.pageX,
                    "top": l - a.pageY
                }), k = a.pageX, l = a.pageY, m = null
            }, 1 / c * 1e3))
        }

        function g(a) {
            m && (clearTimeout(m), m = null), b.getPlaceholder().css("cursor", j), b.pan({
                "left": k - a.pageX,
                "top": l - a.pageY
            })
        }

        function h(a, b) {
            var h = a.getOptions();
            h.zoom.interactive && (b[h.zoom.trigger](c), b.mousewheel(d)), h.pan.interactive && (b.bind("dragstart", {
                "distance": 10
            }, e), b.bind("drag", f), b.bind("dragend", g))
        }

        function i(a, b) {
            b.unbind(a.getOptions().zoom.trigger, c), b.unbind("mousewheel", d), b.unbind("dragstart", e), b.unbind("drag", f), b.unbind("dragend", g), m && clearTimeout(m)
        }
        var j = "default",
            k = 0,
            l = 0,
            m = null;
        b.zoomOut = function(a) {
            a || (a = {}), a.amount || (a.amount = b.getOptions().zoom.amount), a.amount = 1 / a.amount, b.zoom(a)
        }, b.zoom = function(c) {
            c || (c = {});
            var d = c.center,
                e = c.amount || b.getOptions().zoom.amount,
                f = b.width(),
                g = b.height();
            d || (d = {
                "left": f / 2,
                "top": g / 2
            });
            var h = d.left / f,
                i = d.top / g,
                j = {
                    "x": {
                        "min": d.left - h * f / e,
                        "max": d.left + (1 - h) * f / e
                    },
                    "y": {
                        "min": d.top - i * g / e,
                        "max": d.top + (1 - i) * g / e
                    }
                };
            a.each(b.getAxes(), function(a, b) {
                var c = b.options,
                    d = j[b.direction].min,
                    f = j[b.direction].max,
                    g = c.zoomRange,
                    h = c.panRange;
                if (g !== !1) {
                    if (d = b.c2p(d), f = b.c2p(f), d > f) {
                        var i = d;
                        d = f, f = i
                    }
                    h && (null != h[0] && d < h[0] && (d = h[0]), null != h[1] && f > h[1] && (f = h[1]));
                    var k = f - d;
                    g && (null != g[0] && k < g[0] && e > 1 || null != g[1] && k > g[1] && 1 > e) || (c.min = d, c.max = f)
                }
            }), b.setupGrid(), b.draw(), c.preventEvent || b.getPlaceholder().trigger("plotzoom", [b, c])
        }, b.pan = function(c) {
            var d = {
                "x": +c.left,
                "y": +c.top
            };
            isNaN(d.x) && (d.x = 0), isNaN(d.y) && (d.y = 0), a.each(b.getAxes(), function(a, b) {
                var c, e, f = b.options,
                    g = d[b.direction];
                c = b.c2p(b.p2c(b.min) + g), e = b.c2p(b.p2c(b.max) + g);
                var h = f.panRange;
                h !== !1 && (h && (null != h[0] && h[0] > c && (g = h[0] - c, c += g, e += g), null != h[1] && h[1] < e && (g = h[1] - e, c += g, e += g)), f.min = c, f.max = e)
            }), b.setupGrid(), b.draw(), c.preventEvent || b.getPlaceholder().trigger("plotpan", [b, c])
        }, b.hooks.bindEvents.push(h), b.hooks.shutdown.push(i)
    }
    var c = {
        "xaxis": {
            "zoomRange": null,
            "panRange": null
        },
        "zoom": {
            "interactive": !1,
            "trigger": "dblclick",
            "amount": 1.5
        },
        "pan": {
            "interactive": !1,
            "cursor": "move",
            "frameRate": 20
        }
    };
    a.plot.plugins.push({
        "init": b,
        "options": c,
        "name": "navigate",
        "version": "1.3"
    })
}(jQuery);