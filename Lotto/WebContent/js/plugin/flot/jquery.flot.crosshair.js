/*! SmartAdmin - v1.5 - 2014-09-27 */ ! function(a) {
    function b(a) {
        function b() {
            d.locked || -1 != d.x && (d.x = -1, a.triggerRedrawOverlay())
        }

        function c(b) {
            if (!d.locked) {
                if (a.getSelection && a.getSelection()) return void(d.x = -1);
                var c = a.offset();
                d.x = Math.max(0, Math.min(b.pageX - c.left, a.width())), d.y = Math.max(0, Math.min(b.pageY - c.top, a.height())), a.triggerRedrawOverlay()
            }
        }
        var d = {
            "x": -1,
            "y": -1,
            "locked": !1
        };
        a.setCrosshair = function(b) {
            if (b) {
                var c = a.p2c(b);
                d.x = Math.max(0, Math.min(c.left, a.width())), d.y = Math.max(0, Math.min(c.top, a.height()))
            } else d.x = -1;
            a.triggerRedrawOverlay()
        }, a.clearCrosshair = a.setCrosshair, a.lockCrosshair = function(b) {
            b && a.setCrosshair(b), d.locked = !0
        }, a.unlockCrosshair = function() {
            d.locked = !1
        }, a.hooks.bindEvents.push(function(a, d) {
            a.getOptions().crosshair.mode && (d.mouseout(b), d.mousemove(c))
        }), a.hooks.drawOverlay.push(function(a, b) {
            var c = a.getOptions().crosshair;
            if (c.mode) {
                var e = a.getPlotOffset();
                if (b.save(), b.translate(e.left, e.top), -1 != d.x) {
                    var f = a.getOptions().crosshair.lineWidth % 2 ? .5 : 0;
                    if (b.strokeStyle = c.color, b.lineWidth = c.lineWidth, b.lineJoin = "round", b.beginPath(), -1 != c.mode.indexOf("x")) {
                        var g = Math.floor(d.x) + f;
                        b.moveTo(g, 0), b.lineTo(g, a.height())
                    }
                    if (-1 != c.mode.indexOf("y")) {
                        var h = Math.floor(d.y) + f;
                        b.moveTo(0, h), b.lineTo(a.width(), h)
                    }
                    b.stroke()
                }
                b.restore()
            }
        }), a.hooks.shutdown.push(function(a, d) {
            d.unbind("mouseout", b), d.unbind("mousemove", c)
        })
    }
    var c = {
        "crosshair": {
            "mode": null,
            "color": "rgba(170, 0, 0, 0.80)",
            "lineWidth": 1
        }
    };
    a.plot.plugins.push({
        "init": b,
        "options": c,
        "name": "crosshair",
        "version": "1.0"
    })
}(jQuery);