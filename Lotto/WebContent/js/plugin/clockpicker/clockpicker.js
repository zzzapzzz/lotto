/*! SmartAdmin - v1.5 - 2014-09-27 */ ! function() {
    function a(a) {
        return document.createElementNS(i, a)
    }

    function b(a) {
        return (10 > a ? "0" : "") + a
    }

    function c(a) {
        var b = ++q + "";
        return a ? a + b : b
    }

    function d(d, g) {
        function i(a, b) {
            var c = l.offset(),
                d = /^touch/.test(a.type),
                f = c.left + r,
                i = c.top + r,
                k = (d ? a.originalEvent.touches[0] : a).pageX - f,
                m = (d ? a.originalEvent.touches[0] : a).pageY - i,
                p = Math.sqrt(k * k + m * m),
                q = !1;
            if (!b || !(s - u > p || p > s + u)) {
                a.preventDefault();
                var t = setTimeout(function() {
                    e.addClass("clockpicker-moving")
                }, 200);
                j && l.append(B.canvas), B.setHand(k, m, !b, !0), h.off(n).on(n, function(a) {
                    a.preventDefault();
                    var b = /^touch/.test(a.type),
                        c = (b ? a.originalEvent.touches[0] : a).pageX - f,
                        d = (b ? a.originalEvent.touches[0] : a).pageY - i;
                    (q || c !== k || d !== m) && (q = !0, B.setHand(c, d, !1, !0))
                }), h.off(o).one(o, function(a) {
                    a.preventDefault();
                    var c = /^touch/.test(a.type),
                        d = (c ? a.originalEvent.changedTouches[0] : a).pageX - f,
                        j = (c ? a.originalEvent.changedTouches[0] : a).pageY - i;
                    (b || q) && d === k && j === m && B.setHand(d, j), "hours" === B.currentView ? B.toggleView("minutes", w / 2) : g.autoclose && (B.minutesView.addClass("clockpicker-dial-out"), setTimeout(function() {
                        B.done()
                    }, w / 2)), l.prepend(I), clearTimeout(t), e.removeClass("clockpicker-moving"), h.off(n)
                })
            }
        }
        var k = f(x),
            l = k.find(".clockpicker-plate"),
            p = k.find(".clockpicker-hours"),
            q = k.find(".clockpicker-minutes"),
            y = "INPUT" === d.prop("tagName"),
            z = y ? d : d.find("input"),
            A = d.find(".input-group-addon"),
            B = this;
        this.id = c("cp"), this.element = d, this.options = g, this.isAppended = !1, this.isShown = !1, this.currentView = "hours", this.isInput = y, this.input = z, this.addon = A, this.popover = k, this.plate = l, this.hoursView = p, this.minutesView = q, this.spanHours = k.find(".clockpicker-span-hours"), this.spanMinutes = k.find(".clockpicker-span-minutes"), g.autoclose || f('<button type="button" class="btn btn-sm btn-default btn-block clockpicker-button">' + g.donetext + "</button>").click(f.proxy(this.done, this)).appendTo(k), "top" !== g.placement && "bottom" !== g.placement || "top" !== g.align && "bottom" !== g.align || (g.align = "left"), "left" !== g.placement && "right" !== g.placement || "left" !== g.align && "right" !== g.align || (g.align = "top"), k.addClass(g.placement), k.addClass("clockpicker-align-" + g.align), this.spanHours.click(f.proxy(this.toggleView, this, "hours")), this.spanMinutes.click(f.proxy(this.toggleView, this, "minutes")), z.on("focus.clockpicker click.clockpicker", f.proxy(this.show, this)), A.on("click.clockpicker", f.proxy(this.toggle, this));
        var C, D, E, F = f('<div class="clockpicker-tick"></div>');
        for (C = 0; 24 > C; C += 1) {
            D = F.clone(), E = C / 6 * Math.PI;
            var G = C > 0 && 13 > C,
                H = G ? t : s;
            D.css({
                "left": r + Math.sin(E) * H - u,
                "top": r - Math.cos(E) * H - u
            }), G && D.css("font-size", "120%"), D.html(0 === C ? "00" : C), p.append(D), D.on(m, i)
        }
        for (C = 0; 60 > C; C += 5) D = F.clone(), E = C / 30 * Math.PI, D.css({
            "left": r + Math.sin(E) * s - u,
            "top": r - Math.cos(E) * s - u
        }), D.css("font-size", "120%"), D.html(b(C)), q.append(D), D.on(m, i);
        if (l.on(m, function(a) {
                0 === f(a.target).closest(".clockpicker-tick").length && i(a, !0)
            }), j) {
            var I = k.find(".clockpicker-canvas"),
                J = a("svg");
            J.setAttribute("class", "clockpicker-svg"), J.setAttribute("width", v), J.setAttribute("height", v);
            var K = a("g");
            K.setAttribute("transform", "translate(" + r + "," + r + ")");
            var L = a("circle");
            L.setAttribute("class", "clockpicker-canvas-bearing"), L.setAttribute("cx", 0), L.setAttribute("cy", 0), L.setAttribute("r", 2);
            var M = a("line");
            M.setAttribute("x1", 0), M.setAttribute("y1", 0);
            var N = a("circle");
            N.setAttribute("class", "clockpicker-canvas-bg"), N.setAttribute("r", u);
            var O = a("circle");
            O.setAttribute("class", "clockpicker-canvas-fg"), O.setAttribute("r", 3.5), K.appendChild(M), K.appendChild(N), K.appendChild(O), K.appendChild(L), J.appendChild(K), I.append(J), this.hand = M, this.bg = N, this.fg = O, this.bearing = L, this.g = K, this.canvas = I
        }
    }
    var e, f = window.jQuery,
        g = f(window),
        h = f(document),
        i = "http://www.w3.org/2000/svg",
        j = "SVGAngle" in window && function() {
            var a, b = document.createElement("div");
            return b.innerHTML = "<svg/>", a = (b.firstChild && b.firstChild.namespaceURI) == i, b.innerHTML = "", a
        }(),
        k = function() {
            var a = document.createElement("div").style;
            return "transition" in a || "WebkitTransition" in a || "MozTransition" in a || "msTransition" in a || "OTransition" in a
        }(),
        l = "ontouchstart" in window,
        m = "mousedown" + (l ? " touchstart" : ""),
        n = "mousemove.clockpicker" + (l ? " touchmove.clockpicker" : ""),
        o = "mouseup.clockpicker" + (l ? " touchend.clockpicker" : ""),
        p = navigator.vibrate ? "vibrate" : navigator.webkitVibrate ? "webkitVibrate" : null,
        q = 0,
        r = 100,
        s = 80,
        t = 54,
        u = 13,
        v = 2 * r,
        w = k ? 350 : 1,
        x = ['<div class="popover clockpicker-popover">', '<div class="arrow"></div>', '<div class="popover-title">', '<span class="clockpicker-span-hours text-primary"></span>', " : ", '<span class="clockpicker-span-minutes"></span>', "</div>", '<div class="popover-content">', '<div class="clockpicker-plate">', '<div class="clockpicker-canvas"></div>', '<div class="clockpicker-dial clockpicker-hours"></div>', '<div class="clockpicker-dial clockpicker-minutes clockpicker-dial-out"></div>', "</div>", "</div>", "</div>"].join("");
    d.DEFAULTS = {
        "default": "",
        "fromnow": 0,
        "placement": "bottom",
        "align": "left",
        "donetext": "\u5b8c\u6210",
        "autoclose": !1,
        "vibrate": !0
    }, d.prototype.toggle = function() {
        this[this.isShown ? "hide" : "show"]()
    }, d.prototype.locate = function() {
        var a = this.element,
            b = this.popover,
            c = a.offset(),
            d = a.outerWidth(),
            e = a.outerHeight(),
            f = this.options.placement,
            g = this.options.align,
            h = {};
        switch (b.show(), f) {
            case "bottom":
                h.top = c.top + e;
                break;
            case "right":
                h.left = c.left + d;
                break;
            case "top":
                h.top = c.top - b.outerHeight();
                break;
            case "left":
                h.left = c.left - b.outerWidth()
        }
        switch (g) {
            case "left":
                h.left = c.left;
                break;
            case "right":
                h.left = c.left + d - b.outerWidth();
                break;
            case "top":
                h.top = c.top;
                break;
            case "bottom":
                h.top = c.top + e - b.outerHeight()
        }
        b.css(h)
    }, d.prototype.show = function() {
        if (!this.isShown) {
            var a = this;
            this.isAppended || (e = f(document.body).append(this.popover), g.on("resize.clockpicker" + this.id, function() {
                a.isShown && a.locate()
            }), this.isAppended = !0);
            var c = ((this.input.prop("value") || this.options["default"] || "") + "").split(":");
            if ("now" === c[0]) {
                var d = new Date(+new Date + this.options.fromnow);
                c = [d.getHours(), d.getMinutes()]
            }
            this.hours = +c[0] || 0, this.minutes = +c[1] || 0, this.spanHours.html(b(this.hours)), this.spanMinutes.html(b(this.minutes)), this.toggleView("hours"), this.locate(), this.isShown = !0, h.on("click.clockpicker." + this.id + " focusin.clockpicker." + this.id, function(b) {
                var c = f(b.target);
                0 === c.closest(a.popover).length && 0 === c.closest(a.addon).length && 0 === c.closest(a.input).length && a.hide()
            }), h.on("keyup.clockpicker." + this.id, function(b) {
                27 === b.keyCode && a.hide()
            })
        }
    }, d.prototype.hide = function() {
        this.isShown = !1, h.off("click.clockpicker." + this.id + " focusin.clockpicker." + this.id), h.off("keyup.clockpicker." + this.id), this.popover.hide()
    }, d.prototype.toggleView = function(a, b) {
        var c = "hours" === a,
            d = c ? this.hoursView : this.minutesView,
            e = c ? this.minutesView : this.hoursView;
        this.currentView = a, this.spanHours.toggleClass("text-primary", c), this.spanMinutes.toggleClass("text-primary", !c), e.addClass("clockpicker-dial-out"), d.css("visibility", "visible").removeClass("clockpicker-dial-out"), this.resetClock(b), clearTimeout(this.toggleViewTimer), this.toggleViewTimer = setTimeout(function() {
            e.css("visibility", "hidden")
        }, w)
    }, d.prototype.resetClock = function(a) {
        var b = this.currentView,
            c = this[b],
            d = "hours" === b,
            e = Math.PI / (d ? 6 : 30),
            f = c * e,
            g = d && c > 0 && 13 > c ? t : s,
            h = Math.sin(f) * g,
            i = -Math.cos(f) * g,
            k = this;
        j && a ? (k.canvas.addClass("clockpicker-canvas-out"), setTimeout(function() {
            k.canvas.removeClass("clockpicker-canvas-out"), k.setHand(h, i)
        }, a)) : this.setHand(h, i)
    }, d.prototype.setHand = function(a, c, d, e) {
        var g, h = Math.atan2(a, -c),
            i = "hours" === this.currentView,
            k = Math.PI / (i || d ? 6 : 30),
            l = Math.sqrt(a * a + c * c),
            m = (this.options, i && (s + t) / 2 > l),
            n = m ? t : s;
        if (0 > h && (h = 2 * Math.PI + h), g = Math.round(h / k), h = g * k, i ? (12 === g && (g = 0), g = m ? 0 === g ? 12 : g : 0 === g ? 0 : g + 12) : (d && (g *= 5), 60 === g && (g = 0)), this[this.currentView] !== g && p && this.options.vibrate && (this.vibrateTimer || (navigator[p](10), this.vibrateTimer = setTimeout(f.proxy(function() {
                this.vibrateTimer = null
            }, this), 100))), this[this.currentView] = g, this[i ? "spanHours" : "spanMinutes"].html(b(g)), !j) return void this[i ? "hoursView" : "minutesView"].find(".clockpicker-tick").each(function() {
            var a = f(this);
            a.toggleClass("active", g === +a.html())
        });
        e || !i && g % 5 ? (this.g.insertBefore(this.hand, this.bearing), this.g.insertBefore(this.bg, this.fg), this.bg.setAttribute("class", "clockpicker-canvas-bg clockpicker-canvas-bg-trans")) : (this.g.insertBefore(this.hand, this.bg), this.g.insertBefore(this.fg, this.bg), this.bg.setAttribute("class", "clockpicker-canvas-bg"));
        var o = Math.sin(h) * n,
            q = -Math.cos(h) * n;
        this.hand.setAttribute("x2", o), this.hand.setAttribute("y2", q), this.bg.setAttribute("cx", o), this.bg.setAttribute("cy", q), this.fg.setAttribute("cx", o), this.fg.setAttribute("cy", q)
    }, d.prototype.done = function() {
        this.hide();
        var a = this.input.prop("value"),
            c = b(this.hours) + ":" + b(this.minutes);
        this.input.prop("value", c), c !== a && (this.input.triggerHandler("change"), this.isInput || this.element.trigger("change"))
    }, d.prototype.remove = function() {
        this.element.removeData("clockpicker"), this.input.off("focus.clockpicker click.clockpicker"), this.addon.off("click.clockpicker"), this.isShown && this.hide(), this.isAppended && (g.off("resize.clockpicker" + this.id), this.popover.remove())
    }, f.fn.clockpicker = function(a) {
        var b = Array.prototype.slice.call(arguments, 1);
        return this.each(function() {
            var c = f(this),
                e = c.data("clockpicker");
            if (e) "function" == typeof e[a] && e[a].apply(e, b);
            else {
                var g = f.extend({}, d.DEFAULTS, c.data(), "object" == typeof a && a);
                c.data("clockpicker", new d(c, g))
            }
        })
    }
}();