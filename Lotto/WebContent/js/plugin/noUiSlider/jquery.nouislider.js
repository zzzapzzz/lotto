/*! SmartAdmin - v1.5 - 2014-09-27 */ ! function(a, b) {
    a.fn.noUiSlider = function(c) {
        function d(a) {
            a.stopPropagation()
        }

        function e(b, c, d) {
            a.each(b, function(a, b) {
                "function" == typeof b && b.call(c, d)
            })
        }

        function f(a) {
            return a.data.base.data("target").is('[class*="noUi-state-"], [disabled]')
        }

        function g(a, c) {
            c && a.preventDefault();
            var d, e, f = a,
                g = 0 === a.type.indexOf("touch"),
                h = 0 === a.type.indexOf("mouse"),
                i = 0 === a.type.indexOf("MSPointer");
            return a = a.originalEvent, g && (d = a.changedTouches[0].pageX, e = a.changedTouches[0].pageY), h && (window.pageXOffset === b && (window.pageXOffset = document.documentElement.scrollLeft, window.pageYOffset = document.documentElement.scrollTop), d = a.clientX + window.pageXOffset, e = a.clientY + window.pageYOffset), i && (d = a.pageX, e = a.pageY), {
                "pass": f.data,
                "e": a,
                "x": d,
                "y": e,
                "t": [g, h, i]
            }
        }

        function h(a) {
            return parseFloat(this.style[a])
        }

        function i(b, c) {
            function d(a) {
                return !isNaN(a) && isFinite(a)
            }

            function e(b) {
                return b instanceof a || "string" == typeof b || b === !1
            }
            var f = {
                    "handles": {
                        "r": !0,
                        "t": function(a, b) {
                            return b = parseInt(b, 10), 1 === b || 2 === b
                        }
                    },
                    "range": {
                        "r": !0,
                        "t": function(a, b, c) {
                            return 2 !== b.length ? !1 : (b = [parseFloat(b[0]), parseFloat(b[1])], d(b[0]) && d(b[1]) ? "range" === c && b[0] === b[1] ? !1 : (a[c] = b, !0) : !1)
                        }
                    },
                    "start": {
                        "r": !0,
                        "t": function(b, c, e) {
                            return 1 === b.handles ? (a.isArray(c) && (c = c[0]), c = parseFloat(c), b.start = [c], d(c)) : this.parent.range.t(b, c, e)
                        }
                    },
                    "connect": {
                        "t": function(a, b) {
                            return b === !0 || b === !1 || "lower" === b && 1 === a.handles || "upper" === b && 1 === a.handles
                        }
                    },
                    "orientation": {
                        "t": function(a, b) {
                            return "horizontal" === b || "vertical" === b
                        }
                    },
                    "margin": {
                        "r": !0,
                        "t": function(a, b, c) {
                            return b = parseFloat(b), a[c] = b, d(b)
                        }
                    },
                    "serialization": {
                        "r": !0,
                        "t": function(b, c) {
                            if (c.resolution) switch (c.resolution) {
                                case 1:
                                case .1:
                                case .01:
                                case .001:
                                case 1e-4:
                                case 1e-5:
                                    break;
                                default:
                                    return !1
                            } else b.serialization.resolution = .01;
                            return c.to ? 1 === b.handles ? (a.isArray(c.to) || (c.to = [c.to]), b.serialization.to = c.to, e(c.to[0])) : 2 === c.to.length && e(c.to[0]) && e(c.to[1]) : !1
                        }
                    },
                    "slide": {
                        "t": function(a, b) {
                            return "function" == typeof b
                        }
                    },
                    "step": {
                        "t": function(a, b, c) {
                            return this.parent.margin.t(a, b, c)
                        }
                    },
                    "init": function() {
                        var b = this;
                        return a.each(b, function(a, c) {
                            c.parent = b
                        }), delete this.init, this
                    }
                },
                g = f.init();
            a.each(g, function(d, e) {
                return e.r && !b[d] && 0 !== b[d] || (b[d] || 0 === b[d]) && !e.t(b, b[d], d) ? (console && console.log && console.log("Slider:			", c, "\nOption:			", d, "\nValue:			", b[d]), a.error("Error on noUiSlider initialisation."), !1) : void 0
            })
        }

        function j(a, b) {
            return Math.round(a / b) * b
        }

        function k(a, b, c) {
            var d, e = a.data("nui").options,
                f = a.data("nui").base.data(x[12]),
                g = a.data("nui").style,
                h = a.data("nui").decimals;
            return b === a[0].getPercentage(g) ? !1 : (b = 0 > b ? 0 : b > 100 ? 100 : b, e.step && !c && (b = j(b, z.from(e.range, e.step))), b === a[0].getPercentage(g) ? !1 : a.siblings("." + x[1]).length && !c && f && (a.data("nui").number ? (d = f[0][0].getPercentage(g) + e.margin, b = d > b ? d : b) : (d = f[1][0].getPercentage(g) - e.margin, b = b > d ? d : b), b === a[0].getPercentage(g)) ? !1 : (0 === a.data("nui").number && b > 95 ? a.addClass(x[14]) : a.removeClass(x[14]), a.css(g, b + "%"), a.data("store").val(z.is(e.range, b).toFixed(h)), !0))
        }

        function l(c, e) {
            var f = c.data("nui").number;
            return e.to[f] instanceof a ? e.to[f].on("change" + t + " blur" + t, function() {
                var b = [null, null];
                b[f] = a(this).val(), c.data("nui").target.val(b, !0)
            }) : "string" == typeof e.to[f] ? a('<input type="hidden" class="' + x[3] + '" name="' + e.to[f] + '">').appendTo(c).change(d) : e.to[f] === !1 ? {
                "val": function(a) {
                    return a === b ? this.handleElement.data("nui-val") : void this.handleElement.data("nui-val", a)
                },
                "hasClass": function() {
                    return !1
                },
                "handleElement": c
            } : void 0
        }

        function m(a) {
            if (a = g(a, !0)) {
                var b = a.pass.base,
                    c = b.data("style"),
                    d = a.x - a.pass.startEvent.x,
                    f = "left" === c ? b.width() : b.height();
                "top" === c && (d = a.y - a.pass.startEvent.y), d = a.pass.position + 100 * d / f, k(a.pass.handle, d), e([a.pass.base.data("options").slide], a.pass.base.data("target"))
            }
        }

        function n(b) {
            f(b) || (b.data.handle.children().removeClass(x[4]), u.off(v.move), u.off(v.end), a("body").off(t), b.data.base.data("target").change())
        }

        function o(b) {
            if (!f(b) && (b = g(b))) {
                var c = b.pass.handle,
                    d = c[0].getPercentage(c.data("nui").style);
                c.children().addClass("noUi-active"), u.on(v.move, {
                    "startEvent": b,
                    "position": d,
                    "base": b.pass.base,
                    "handle": c
                }, m), u.on(v.end, {
                    "base": b.pass.base,
                    "handle": c
                }, n), a("body").on("selectstart" + t, function() {
                    return !1
                })
            }
        }

        function p(a) {
            n({
                "data": {
                    "base": a.data.base,
                    "handle": a.data.handle
                }
            }), a.stopPropagation()
        }

        function q(a) {
            if (!f(a) && !a.data.base.find("." + x[4]).length && (a = g(a))) {
                var b, c, d, h = a.pass.base,
                    i = a.pass.handles,
                    j = h.data("style"),
                    l = a["left" === j ? "x" : "y"],
                    m = "left" === j ? h.width() : h.height(),
                    n = {
                        "x": a.t[2] ? window.pageXOffset : 0
                    },
                    o = {
                        "handles": [],
                        "base": {
                            "left": h.offset().left - n.x,
                            "top": h.offset().top
                        }
                    };
                for (b = 0; b < i.length; b++) o.handles.push({
                    "left": i[b].offset().left - n.x,
                    "top": i[b].offset().top
                });
                d = 1 === i.length ? 0 : (o.handles[0][j] + o.handles[1][j]) / 2, c = 1 === i.length || d > l ? i[0] : i[1], h.addClass(x[5]), setTimeout(function() {
                    h.removeClass(x[5])
                }, 300), k(c, 100 * (l - o.base[j]) / m), e([c.data("nui").options.slide], h.data("target")), h.data("target").change()
            }
        }

        function r() {
            return this.each(function(b, d) {
                d = a(d), d.addClass(x[6]);
                var e, f, g, j, m = a("<div/>").appendTo(d),
                    n = [],
                    r = {
                        "base": y.base,
                        "origin": [y.origin.concat([x[1] + x[7]]), y.origin.concat([x[1] + x[8]])],
                        "handle": [y.handle.concat([x[2] + x[7]]), y.handle.concat([x[2] + x[8]])]
                    };
                for (c = a.extend({
                        "handles": 2,
                        "margin": 0,
                        "orientation": "horizontal"
                    }, c) || {}, c.serialization || (c.serialization = {
                        "to": [!1, !1],
                        "resolution": .01
                    }), i(c, d), c.S = c.serialization, c.connect && (r.origin[0].push(x[9]), "lower" === c.connect ? (r.base.push(x[9], x[9] + x[7]), r.origin[0].push(x[13])) : r.base.push(x[9] + x[8])), f = "vertical" === c.orientation ? "top" : "left", g = c.S.resolution.toString().split("."), g = "1" === g[0] ? 0 : g[1].length, r.base.push("vertical" === c.orientation ? x[10] : x[11]), m.addClass(r.base.join(" ")).data("target", d), e = 0; e < c.handles; e++) j = a("<div><div/></i>").appendTo(m), j.addClass(r.origin[e].join(" ")), j.children().addClass(r.handle[e].join(" ")), j.children().on(v.start, {
                    "base": m,
                    "handle": j
                }, o).on(v.end, {
                    "base": m,
                    "handle": j
                }, p), j.data("nui", {
                    "target": d,
                    "decimals": g,
                    "options": c,
                    "base": m,
                    "style": f,
                    "number": e
                }).data("store", l(j, c.S)), j[0].getPercentage = h, n.push(j), k(j, z.to(c.range, c.start[e]));
                m.data({
                    "options": c,
                    "handles": n,
                    "style": f
                }), d.data({
                    "base": m,
                    "handles": n
                }), m.on(v.end, {
                    "base": m,
                    "handles": n
                }, q)
            })
        }

        function s(c, d) {
            if (c !== b) return a.isArray(c) || (c = [c]), this.each(function() {
                a.each(a(this).data(x[12]), function(a, b) {
                    if (null !== c[a]) {
                        var e, f, g = b.data("nui").options.range,
                            h = z.to(g, parseFloat(c[a])),
                            i = k(b, h, d === !0 ? !1 : !0);
                        i || (e = b.data("store").val(), f = z.is(g, b[0].getPercentage(b.data("nui").style)).toFixed(b.data("nui").decimals), e !== f && b.data("store").val(f))
                    }
                })
            });
            var e = [];
            return a.each(a(this).data(x[12]), function(a, b) {
                e.push(b.data("store").val())
            }), 1 === e.length ? e[0] : e
        }
        var t = ".nui",
            u = a(document),
            v = {
                "start": "mousedown" + t + " touchstart" + t,
                "move": "mousemove" + t + " touchmove" + t,
                "end": "mouseup" + t + " touchend" + t
            },
            w = a.fn.val,
            x = ["noUi-base", "noUi-origin", "noUi-handle", "noUi-input", "noUi-active", "noUi-state-tap", "noUi-target", "-lower", "-upper", "noUi-connect", "noUi-vertical", "noUi-horizontal", "handles", "noUi-background", "noUi-z-index"],
            y = {
                "base": [x[0], x[13]],
                "origin": [x[1]],
                "handle": [x[2]]
            },
            z = {
                "to": function(a, b) {
                    return b = a[0] < 0 ? b + Math.abs(a[0]) : b - a[0], 100 * b / this.len(a)
                },
                "from": function(a, b) {
                    return 100 * b / this.len(a)
                },
                "is": function(a, b) {
                    return b * this.len(a) / 100 + a[0]
                },
                "len": function(a) {
                    return a[0] > a[1] ? a[0] - a[1] : a[1] - a[0]
                }
            };
        return window.navigator.msPointerEnabled && (v = {
            "start": "MSPointerDown" + t,
            "move": "MSPointerMove" + t,
            "end": "MSPointerUp" + t
        }), a.fn.val = function() {
            return this.hasClass(x[6]) ? s.apply(this, arguments) : w.apply(this, arguments)
        }, r.apply(this, arguments)
    }
}(jQuery);