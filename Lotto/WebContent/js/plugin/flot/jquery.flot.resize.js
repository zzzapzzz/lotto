/*! SmartAdmin - v1.5 - 2014-09-27 */ ! function(a, b, c) {
    "$:nomunge";

    function d(c) {
        h === !0 && (h = c || 1);
        for (var i = f.length - 1; i >= 0; i--) {
            var m = a(f[i]);
            if (m[0] == b || m.is(":visible")) {
                var n = m.width(),
                    o = m.height(),
                    p = m.data(k);
                !p || n === p.w && o === p.h || (m.trigger(j, [p.w = n, p.h = o]), h = c || !0)
            } else p = m.data(k), p.w = 0, p.h = 0
        }
        null !== e && (h && (null == c || 1e3 > c - h) ? e = b.requestAnimationFrame(d) : (e = setTimeout(d, g[l]), h = !1))
    }
    var e, f = [],
        g = a.resize = a.extend(a.resize, {}),
        h = !1,
        i = "setTimeout",
        j = "resize",
        k = j + "-special-event",
        l = "pendingDelay",
        m = "activeDelay",
        n = "throttleWindow";
    g[l] = 200, g[m] = 20, g[n] = !0, a.event.special[j] = {
        "setup": function() {
            if (!g[n] && this[i]) return !1;
            var b = a(this);
            f.push(this), b.data(k, {
                "w": b.width(),
                "h": b.height()
            }), 1 === f.length && (e = c, d())
        },
        "teardown": function() {
            if (!g[n] && this[i]) return !1;
            for (var b = a(this), c = f.length - 1; c >= 0; c--)
                if (f[c] == this) {
                    f.splice(c, 1);
                    break
                }
            b.removeData(k), f.length || (h ? cancelAnimationFrame(e) : clearTimeout(e), e = null)
        },
        "add": function(b) {
            function d(b, d, f) {
                var g = a(this),
                    h = g.data(k) || {};
                h.w = d !== c ? d : g.width(), h.h = f !== c ? f : g.height(), e.apply(this, arguments)
            }
            if (!g[n] && this[i]) return !1;
            var e;
            return a.isFunction(b) ? (e = b, d) : (e = b.handler, void(b.handler = d))
        }
    }, b.requestAnimationFrame || (b.requestAnimationFrame = function() {
        return b.webkitRequestAnimationFrame || b.mozRequestAnimationFrame || b.oRequestAnimationFrame || b.msRequestAnimationFrame || function(a) {
            return b.setTimeout(function() {
                a((new Date).getTime())
            }, g[m])
        }
    }()), b.cancelAnimationFrame || (b.cancelAnimationFrame = function() {
        return b.webkitCancelRequestAnimationFrame || b.mozCancelRequestAnimationFrame || b.oCancelRequestAnimationFrame || b.msCancelRequestAnimationFrame || clearTimeout
    }())
}(jQuery, this),
function(a) {
    function b(a) {
        function b() {
            var b = a.getPlaceholder();
            0 != b.width() && 0 != b.height() && (a.resize(), a.setupGrid(), a.draw())
        }

        function c(a) {
            a.getPlaceholder().resize(b)
        }

        function d(a) {
            a.getPlaceholder().unbind("resize", b)
        }
        a.hooks.bindEvents.push(c), a.hooks.shutdown.push(d)
    }
    var c = {};
    a.plot.plugins.push({
        "init": b,
        "options": c,
        "name": "resize",
        "version": "1.0"
    })
}(jQuery);