/*! SmartAdmin - v1.5 - 2014-09-27 */ ! function(a) {
    "use strict";
    var b = function(c, d) {
        this.$element = a(c), this.options = a.extend({}, b.defaults, d)
    };
    b.defaults = {
        "transition_delay": 300,
        "refresh_speed": 50,
        "display_text": "none",
        "use_percentage": !0,
        "percent_format": function(a) {
            return a + "%"
        },
        "amount_format": function(a, b) {
            return a + " / " + b
        },
        "update": a.noop,
        "done": a.noop,
        "fail": a.noop
    }, b.prototype.transition = function() {
        var c = this.$element,
            d = c.parent(),
            e = this.$back_text,
            f = this.$front_text,
            g = this.options,
            h = c.attr("aria-valuetransitiongoal"),
            i = c.attr("aria-valuemin") || 0,
            j = c.attr("aria-valuemax") || 100,
            k = d.hasClass("vertical"),
            l = g.update && "function" == typeof g.update ? g.update : b.defaults.update,
            m = g.done && "function" == typeof g.done ? g.done : b.defaults.done,
            n = g.fail && "function" == typeof g.fail ? g.fail : b.defaults.fail;
        if (!h) return void n("aria-valuetransitiongoal not set");
        var o = Math.round(100 * (h - i) / (j - i));
        if ("center" === g.display_text && !e && !f) {
            this.$back_text = e = a("<span>").addClass("progressbar-back-text").prependTo(d), this.$front_text = f = a("<span>").addClass("progressbar-front-text").prependTo(c);
            var p;
            k ? (p = d.css("height"), e.css({
                "height": p,
                "line-height": p
            }), f.css({
                "height": p,
                "line-height": p
            }), a(window).resize(function() {
                p = d.css("height"), e.css({
                    "height": p,
                    "line-height": p
                }), f.css({
                    "height": p,
                    "line-height": p
                })
            })) : (p = d.css("width"), f.css({
                "width": p
            }), a(window).resize(function() {
                p = d.css("width"), f.css({
                    "width": p
                })
            }))
        }
        setTimeout(function() {
            var a, b, n, p, q;
            k ? c.css("height", o + "%") : c.css("width", o + "%");
            var r = setInterval(function() {
                k ? (n = c.height(), p = d.height()) : (n = c.width(), p = d.width()), a = Math.round(100 * n / p), b = Math.round(n / p * (j - i)), a >= o && (a = o, b = h, m(), clearInterval(r)), "none" !== g.display_text && (q = g.use_percentage ? g.percent_format(a) : g.amount_format(b, j), "fill" === g.display_text ? c.text(q) : "center" === g.display_text && (e.text(q), f.text(q))), c.attr("aria-valuenow", b), l(a)
            }, g.refresh_speed)
        }, g.transition_delay)
    };
    var c = a.fn.progressbar;
    a.fn.progressbar = function(c) {
        return this.each(function() {
            var d = a(this),
                e = d.data("bs.progressbar"),
                f = "object" == typeof c && c;
            e || d.data("bs.progressbar", e = new b(this, f)), e.transition()
        })
    }, a.fn.progressbar.Constructor = b, a.fn.progressbar.noConflict = function() {
        return a.fn.progressbar = c, this
    }
}(window.jQuery);