/*! SmartAdmin - v1.5 - 2014-09-27 */ ! function(a) {
    a.Jcrop = function(b, c) {
        function d(a) {
            return Math.round(a) + "px"
        }

        function e(a) {
            return J.baseClass + "-" + a
        }

        function f() {
            return a.fx.step.hasOwnProperty("backgroundColor")
        }

        function g(b) {
            var c = a(b).offset();
            return [c.left, c.top]
        }

        function h(a) {
            return [a.pageX - I[0], a.pageY - I[1]]
        }

        function i(b) {
            "object" != typeof b && (b = {}), J = a.extend(J, b), a.each(["onChange", "onSelect", "onRelease", "onDblClick"], function(a, b) {
                "function" != typeof J[b] && (J[b] = function() {})
            })
        }

        function j(a, b, c) {
            if (I = g(R), ob.setCursor("move" === a ? a : a + "-resize"), "move" === a) return ob.activateHandlers(l(b), q, c);
            var d = lb.getFixed(),
                e = m(a),
                f = lb.getCorner(m(e));
            lb.setPressed(lb.getCorner(e)), lb.setCurrent(f), ob.activateHandlers(k(a, d), q, c)
        }

        function k(a, b) {
            return function(c) {
                if (J.aspectRatio) switch (a) {
                    case "e":
                        c[1] = b.y + 1;
                        break;
                    case "w":
                        c[1] = b.y + 1;
                        break;
                    case "n":
                        c[0] = b.x + 1;
                        break;
                    case "s":
                        c[0] = b.x + 1
                } else switch (a) {
                    case "e":
                        c[1] = b.y2;
                        break;
                    case "w":
                        c[1] = b.y2;
                        break;
                    case "n":
                        c[0] = b.x2;
                        break;
                    case "s":
                        c[0] = b.x2
                }
                lb.setCurrent(c), nb.update()
            }
        }

        function l(a) {
            var b = a;
            return pb.watchKeys(),
                function(a) {
                    lb.moveOffset([a[0] - b[0], a[1] - b[1]]), b = a, nb.update()
                }
        }

        function m(a) {
            switch (a) {
                case "n":
                    return "sw";
                case "s":
                    return "nw";
                case "e":
                    return "nw";
                case "w":
                    return "ne";
                case "ne":
                    return "sw";
                case "nw":
                    return "se";
                case "se":
                    return "nw";
                case "sw":
                    return "ne"
            }
        }

        function n(a) {
            return function(b) {
                return J.disabled ? !1 : "move" !== a || J.allowMove ? (I = g(R), db = !0, j(a, h(b)), b.stopPropagation(), b.preventDefault(), !1) : !1
            }
        }

        function o(a, b, c) {
            var d = a.width(),
                e = a.height();
            d > b && b > 0 && (d = b, e = b / a.width() * a.height()), e > c && c > 0 && (e = c, d = c / a.height() * a.width()), bb = a.width() / d, cb = a.height() / e, a.width(d).height(e)
        }

        function p(a) {
            return {
                "x": a.x * bb,
                "y": a.y * cb,
                "x2": a.x2 * bb,
                "y2": a.y2 * cb,
                "w": a.w * bb,
                "h": a.h * cb
            }
        }

        function q() {
            var a = lb.getFixed();
            a.w > J.minSelect[0] && a.h > J.minSelect[1] ? (nb.enableHandles(), nb.done()) : nb.release(), ob.setCursor(J.allowSelect ? "crosshair" : "default")
        }

        function r(a) {
            if (J.disabled) return !1;
            if (!J.allowSelect) return !1;
            db = !0, I = g(R), nb.disableHandles(), ob.setCursor("crosshair");
            var b = h(a);
            return lb.setPressed(b), nb.update(), ob.activateHandlers(s, q, "touch" === a.type.substring(0, 5)), pb.watchKeys(), a.stopPropagation(), a.preventDefault(), !1
        }

        function s(a) {
            lb.setCurrent(a), nb.update()
        }

        function t() {
            var b = a("<div></div>").addClass(e("tracker"));
            return L && b.css({
                "opacity": 0,
                "backgroundColor": "white"
            }), b
        }

        function u(a) {
            U.removeClass().addClass(e("holder")).addClass(a)
        }

        function v(a, b) {
            function c() {
                window.setTimeout(s, l)
            }
            var d = a[0] / bb,
                e = a[1] / cb,
                f = a[2] / bb,
                g = a[3] / cb;
            if (!eb) {
                var h = lb.flipCoords(d, e, f, g),
                    i = lb.getFixed(),
                    j = [i.x, i.y, i.x2, i.y2],
                    k = j,
                    l = J.animationDelay,
                    m = h[0] - j[0],
                    n = h[1] - j[1],
                    o = h[2] - j[2],
                    p = h[3] - j[3],
                    q = 0,
                    r = J.swingSpeed;
                d = k[0], e = k[1], f = k[2], g = k[3], nb.animMode(!0);
                var s = function() {
                    return function() {
                        q += (100 - q) / r, k[0] = Math.round(d + q / 100 * m), k[1] = Math.round(e + q / 100 * n), k[2] = Math.round(f + q / 100 * o), k[3] = Math.round(g + q / 100 * p), q >= 99.8 && (q = 100), 100 > q ? (x(k), c()) : (nb.done(), nb.animMode(!1), "function" == typeof b && b.call(qb))
                    }
                }();
                c()
            }
        }

        function w(a) {
            x([a[0] / bb, a[1] / cb, a[2] / bb, a[3] / cb]), J.onSelect.call(qb, p(lb.getFixed())), nb.enableHandles()
        }

        function x(a) {
            lb.setPressed([a[0], a[1]]), lb.setCurrent([a[2], a[3]]), nb.update()
        }

        function y() {
            return p(lb.getFixed())
        }

        function z() {
            return lb.getFixed()
        }

        function A(a) {
            i(a), H()
        }

        function B() {
            J.disabled = !0, nb.disableHandles(), nb.setCursor("default"), ob.setCursor("default")
        }

        function C() {
            J.disabled = !1, H()
        }

        function D() {
            nb.done(), ob.activateHandlers(null, null)
        }

        function E() {
            U.remove(), O.show(), O.css("visibility", "visible"), a(b).removeData("Jcrop")
        }

        function F(a, b) {
            nb.release(), B();
            var c = new Image;
            c.onload = function() {
                var d = c.width,
                    e = c.height,
                    f = J.boxWidth,
                    g = J.boxHeight;
                R.width(d).height(e), R.attr("src", a), V.attr("src", a), o(R, f, g), S = R.width(), T = R.height(), V.width(S).height(T), hb.width(S + 2 * gb).height(T + 2 * gb), U.width(S).height(T), mb.resize(S, T), C(), "function" == typeof b && b.call(qb)
            }, c.src = a
        }

        function G(a, b, c) {
            var d = b || J.bgColor;
            J.bgFade && f() && J.fadeTime && !c ? a.animate({
                "backgroundColor": d
            }, {
                "queue": !1,
                "duration": J.fadeTime
            }) : a.css("backgroundColor", d)
        }

        function H(a) {
            J.allowResize ? a ? nb.enableOnly() : nb.enableHandles() : nb.disableHandles(), ob.setCursor(J.allowSelect ? "crosshair" : "default"), nb.setCursor(J.allowMove ? "move" : "default"), J.hasOwnProperty("trueSize") && (bb = J.trueSize[0] / S, cb = J.trueSize[1] / T), J.hasOwnProperty("setSelect") && (w(J.setSelect), nb.done(), delete J.setSelect), mb.refresh(), J.bgColor != ib && (G(J.shade ? mb.getShades() : U, J.shade ? J.shadeColor || J.bgColor : J.bgColor), ib = J.bgColor), jb != J.bgOpacity && (jb = J.bgOpacity, J.shade ? mb.refresh() : nb.setBgOpacity(jb)), Z = J.maxSize[0] || 0, $ = J.maxSize[1] || 0, _ = J.minSize[0] || 0, ab = J.minSize[1] || 0, J.hasOwnProperty("outerImage") && (R.attr("src", J.outerImage), delete J.outerImage), nb.refresh()
        }
        var I, J = a.extend({}, a.Jcrop.defaults),
            K = navigator.userAgent.toLowerCase(),
            L = /msie/.test(K),
            M = /msie [1-6]\./.test(K);
        "object" != typeof b && (b = a(b)[0]), "object" != typeof c && (c = {}), i(c);
        var N = {
                "border": "none",
                "visibility": "visible",
                "margin": 0,
                "padding": 0,
                "position": "absolute",
                "top": 0,
                "left": 0
            },
            O = a(b),
            P = !0;
        if ("IMG" == b.tagName) {
            if (0 != O[0].width && 0 != O[0].height) O.width(O[0].width), O.height(O[0].height);
            else {
                var Q = new Image;
                Q.src = O[0].src, O.width(Q.width), O.height(Q.height)
            }
            var R = O.clone().removeAttr("id").css(N).show();
            R.width(O.width()), R.height(O.height()), O.after(R).hide()
        } else R = O.css(N).show(), P = !1, null === J.shade && (J.shade = !0);
        o(R, J.boxWidth, J.boxHeight);
        var S = R.width(),
            T = R.height(),
            U = a("<div />").width(S).height(T).addClass(e("holder")).css({
                "position": "relative",
                "backgroundColor": J.bgColor
            }).insertAfter(O).append(R);
        J.addClass && U.addClass(J.addClass);
        var V = a("<div />"),
            W = a("<div />").width("100%").height("100%").css({
                "zIndex": 310,
                "position": "absolute",
                "overflow": "hidden"
            }),
            X = a("<div />").width("100%").height("100%").css("zIndex", 320),
            Y = a("<div />").css({
                "position": "absolute",
                "zIndex": 600
            }).dblclick(function() {
                var a = lb.getFixed();
                J.onDblClick.call(qb, a)
            }).insertBefore(R).append(W, X);
        P && (V = a("<img />").attr("src", R.attr("src")).css(N).width(S).height(T), W.append(V)), M && Y.css({
            "overflowY": "hidden"
        });
        var Z, $, _, ab, bb, cb, db, eb, fb, gb = J.boundary,
            hb = t().width(S + 2 * gb).height(T + 2 * gb).css({
                "position": "absolute",
                "top": d(-gb),
                "left": d(-gb),
                "zIndex": 290
            }).mousedown(r),
            ib = J.bgColor,
            jb = J.bgOpacity;
        I = g(R);
        var kb = function() {
                function a() {
                    var a, b = {},
                        c = ["touchstart", "touchmove", "touchend"],
                        d = document.createElement("div");
                    try {
                        for (a = 0; a < c.length; a++) {
                            var e = c[a];
                            e = "on" + e;
                            var f = e in d;
                            f || (d.setAttribute(e, "return;"), f = "function" == typeof d[e]), b[c[a]] = f
                        }
                        return b.touchstart && b.touchend && b.touchmove
                    } catch (g) {
                        return !1
                    }
                }

                function b() {
                    return J.touchSupport === !0 || J.touchSupport === !1 ? J.touchSupport : a()
                }
                return {
                    "createDragger": function(a) {
                        return function(b) {
                            return J.disabled ? !1 : "move" !== a || J.allowMove ? (I = g(R), db = !0, j(a, h(kb.cfilter(b)), !0), b.stopPropagation(), b.preventDefault(), !1) : !1
                        }
                    },
                    "newSelection": function(a) {
                        return r(kb.cfilter(a))
                    },
                    "cfilter": function(a) {
                        return a.pageX = a.originalEvent.changedTouches[0].pageX, a.pageY = a.originalEvent.changedTouches[0].pageY, a
                    },
                    "isSupported": a,
                    "support": b()
                }
            }(),
            lb = function() {
                function a(a) {
                    a = g(a), o = m = a[0], p = n = a[1]
                }

                function b(a) {
                    a = g(a), k = a[0] - o, l = a[1] - p, o = a[0], p = a[1]
                }

                function c() {
                    return [k, l]
                }

                function d(a) {
                    var b = a[0],
                        c = a[1];
                    0 > m + b && (b -= b + m), 0 > n + c && (c -= c + n), p + c > T && (c += T - (p + c)), o + b > S && (b += S - (o + b)), m += b, o += b, n += c, p += c
                }

                function e(a) {
                    var b = f();
                    switch (a) {
                        case "ne":
                            return [b.x2, b.y];
                        case "nw":
                            return [b.x, b.y];
                        case "se":
                            return [b.x2, b.y2];
                        case "sw":
                            return [b.x, b.y2]
                    }
                }

                function f() {
                    if (!J.aspectRatio) return i();
                    var a, b, c, d, e = J.aspectRatio,
                        f = J.minSize[0] / bb,
                        g = J.maxSize[0] / bb,
                        k = J.maxSize[1] / cb,
                        l = o - m,
                        q = p - n,
                        r = Math.abs(l),
                        s = Math.abs(q),
                        t = r / s;
                    return 0 === g && (g = 10 * S), 0 === k && (k = 10 * T), e > t ? (b = p, c = s * e, a = 0 > l ? m - c : c + m, 0 > a ? (a = 0, d = Math.abs((a - m) / e), b = 0 > q ? n - d : d + n) : a > S && (a = S, d = Math.abs((a - m) / e), b = 0 > q ? n - d : d + n)) : (a = o, d = r / e, b = 0 > q ? n - d : n + d, 0 > b ? (b = 0, c = Math.abs((b - n) * e), a = 0 > l ? m - c : c + m) : b > T && (b = T, c = Math.abs(b - n) * e, a = 0 > l ? m - c : c + m)), a > m ? (f > a - m ? a = m + f : a - m > g && (a = m + g), b = b > n ? n + (a - m) / e : n - (a - m) / e) : m > a && (f > m - a ? a = m - f : m - a > g && (a = m - g), b = b > n ? n + (m - a) / e : n - (m - a) / e), 0 > a ? (m -= a, a = 0) : a > S && (m -= a - S, a = S), 0 > b ? (n -= b, b = 0) : b > T && (n -= b - T, b = T), j(h(m, n, a, b))
                }

                function g(a) {
                    return a[0] < 0 && (a[0] = 0), a[1] < 0 && (a[1] = 0), a[0] > S && (a[0] = S), a[1] > T && (a[1] = T), [Math.round(a[0]), Math.round(a[1])]
                }

                function h(a, b, c, d) {
                    var e = a,
                        f = c,
                        g = b,
                        h = d;
                    return a > c && (e = c, f = a), b > d && (g = d, h = b), [e, g, f, h]
                }

                function i() {
                    var a, b = o - m,
                        c = p - n;
                    return Z && Math.abs(b) > Z && (o = b > 0 ? m + Z : m - Z), $ && Math.abs(c) > $ && (p = c > 0 ? n + $ : n - $), ab / cb && Math.abs(c) < ab / cb && (p = c > 0 ? n + ab / cb : n - ab / cb), _ / bb && Math.abs(b) < _ / bb && (o = b > 0 ? m + _ / bb : m - _ / bb), 0 > m && (o -= m, m -= m), 0 > n && (p -= n, n -= n), 0 > o && (m -= o, o -= o), 0 > p && (n -= p, p -= p), o > S && (a = o - S, m -= a, o -= a), p > T && (a = p - T, n -= a, p -= a), m > S && (a = m - T, p -= a, n -= a), n > T && (a = n - T, p -= a, n -= a), j(h(m, n, o, p))
                }

                function j(a) {
                    return {
                        "x": a[0],
                        "y": a[1],
                        "x2": a[2],
                        "y2": a[3],
                        "w": a[2] - a[0],
                        "h": a[3] - a[1]
                    }
                }
                var k, l, m = 0,
                    n = 0,
                    o = 0,
                    p = 0;
                return {
                    "flipCoords": h,
                    "setPressed": a,
                    "setCurrent": b,
                    "getOffset": c,
                    "moveOffset": d,
                    "getCorner": e,
                    "getFixed": f
                }
            }(),
            mb = function() {
                function b(a, b) {
                    o.left.css({
                        "height": d(b)
                    }), o.right.css({
                        "height": d(b)
                    })
                }

                function c() {
                    return e(lb.getFixed())
                }

                function e(a) {
                    o.top.css({
                        "left": d(a.x),
                        "width": d(a.w),
                        "height": d(a.y)
                    }), o.bottom.css({
                        "top": d(a.y2),
                        "left": d(a.x),
                        "width": d(a.w),
                        "height": d(T - a.y2)
                    }), o.right.css({
                        "left": d(a.x2),
                        "width": d(S - a.x2)
                    }), o.left.css({
                        "width": d(a.x)
                    })
                }

                function f() {
                    return a("<div />").css({
                        "position": "absolute",
                        "backgroundColor": J.shadeColor || J.bgColor
                    }).appendTo(n)
                }

                function g() {
                    m || (m = !0, n.insertBefore(R), c(), nb.setBgOpacity(1, 0, 1), V.hide(), h(J.shadeColor || J.bgColor, 1), nb.isAwake() ? j(J.bgOpacity, 1) : j(1, 1))
                }

                function h(a, b) {
                    G(l(), a, b)
                }

                function i() {
                    m && (n.remove(), V.show(), m = !1, nb.isAwake() ? nb.setBgOpacity(J.bgOpacity, 1, 1) : (nb.setBgOpacity(1, 1, 1), nb.disableHandles()), G(U, 0, 1))
                }

                function j(a, b) {
                    m && (J.bgFade && !b ? n.animate({
                        "opacity": 1 - a
                    }, {
                        "queue": !1,
                        "duration": J.fadeTime
                    }) : n.css({
                        "opacity": 1 - a
                    }))
                }

                function k() {
                    J.shade ? g() : i(), nb.isAwake() && j(J.bgOpacity)
                }

                function l() {
                    return n.children()
                }
                var m = !1,
                    n = a("<div />").css({
                        "position": "absolute",
                        "zIndex": 240,
                        "opacity": 0
                    }),
                    o = {
                        "top": f(),
                        "left": f().height(T),
                        "right": f().height(T),
                        "bottom": f()
                    };
                return {
                    "update": c,
                    "updateRaw": e,
                    "getShades": l,
                    "setBgColor": h,
                    "enable": g,
                    "disable": i,
                    "resize": b,
                    "refresh": k,
                    "opacity": j
                }
            }(),
            nb = function() {
                function b(b) {
                    var c = a("<div />").css({
                        "position": "absolute",
                        "opacity": J.borderOpacity
                    }).addClass(e(b));
                    return W.append(c), c
                }

                function c(b, c) {
                    var d = a("<div />").mousedown(n(b)).css({
                        "cursor": b + "-resize",
                        "position": "absolute",
                        "zIndex": c
                    }).addClass("ord-" + b);
                    return kb.support && d.bind("touchstart.jcrop", kb.createDragger(b)), X.append(d), d
                }

                function f(a) {
                    var b = J.handleSize,
                        d = c(a, B++).css({
                            "opacity": J.handleOpacity
                        }).addClass(e("handle"));
                    return b && d.width(b).height(b), d
                }

                function g(a) {
                    return c(a, B++).addClass("jcrop-dragbar")
                }

                function h(a) {
                    var b;
                    for (b = 0; b < a.length; b++) E[a[b]] = g(a[b])
                }

                function i(a) {
                    var c, d;
                    for (d = 0; d < a.length; d++) {
                        switch (a[d]) {
                            case "n":
                                c = "hline";
                                break;
                            case "s":
                                c = "hline bottom";
                                break;
                            case "e":
                                c = "vline right";
                                break;
                            case "w":
                                c = "vline"
                        }
                        C[a[d]] = b(c)
                    }
                }

                function j(a) {
                    var b;
                    for (b = 0; b < a.length; b++) D[a[b]] = f(a[b])
                }

                function k(a, b) {
                    J.shade || V.css({
                        "top": d(-b),
                        "left": d(-a)
                    }), Y.css({
                        "top": d(b),
                        "left": d(a)
                    })
                }

                function l(a, b) {
                    Y.width(Math.round(a)).height(Math.round(b))
                }

                function m() {
                    var a = lb.getFixed();
                    lb.setPressed([a.x, a.y]), lb.setCurrent([a.x2, a.y2]), o()
                }

                function o(a) {
                    return A ? q(a) : void 0
                }

                function q(a) {
                    var b = lb.getFixed();
                    l(b.w, b.h), k(b.x, b.y), J.shade && mb.updateRaw(b), A || s(), a ? J.onSelect.call(qb, p(b)) : J.onChange.call(qb, p(b))
                }

                function r(a, b, c) {
                    (A || b) && (J.bgFade && !c ? R.animate({
                        "opacity": a
                    }, {
                        "queue": !1,
                        "duration": J.fadeTime
                    }) : R.css("opacity", a))
                }

                function s() {
                    Y.show(), J.shade ? mb.opacity(jb) : r(jb, !0), A = !0
                }

                function u() {
                    x(), Y.hide(), J.shade ? mb.opacity(1) : r(1), A = !1, J.onRelease.call(qb)
                }

                function v() {
                    F && X.show()
                }

                function w() {
                    return F = !0, J.allowResize ? (X.show(), !0) : void 0
                }

                function x() {
                    F = !1, X.hide()
                }

                function y(a) {
                    a ? (eb = !0, x()) : (eb = !1, w())
                }

                function z() {
                    y(!1), m()
                }
                var A, B = 370,
                    C = {},
                    D = {},
                    E = {},
                    F = !1;
                J.dragEdges && a.isArray(J.createDragbars) && h(J.createDragbars), a.isArray(J.createHandles) && j(J.createHandles), J.drawBorders && a.isArray(J.createBorders) && i(J.createBorders), a(document).bind("touchstart.jcrop-ios", function(b) {
                    a(b.currentTarget).hasClass("jcrop-tracker") && b.stopPropagation()
                });
                var G = t().mousedown(n("move")).css({
                    "cursor": "move",
                    "position": "absolute",
                    "zIndex": 360
                });
                return kb.support && G.bind("touchstart.jcrop", kb.createDragger("move")), W.append(G), x(), {
                    "updateVisible": o,
                    "update": q,
                    "release": u,
                    "refresh": m,
                    "isAwake": function() {
                        return A
                    },
                    "setCursor": function(a) {
                        G.css("cursor", a)
                    },
                    "enableHandles": w,
                    "enableOnly": function() {
                        F = !0
                    },
                    "showHandles": v,
                    "disableHandles": x,
                    "animMode": y,
                    "setBgOpacity": r,
                    "done": z
                }
            }(),
            ob = function() {
                function b(b) {
                    hb.css({
                        "zIndex": 450
                    }), b ? a(document).bind("touchmove.jcrop", g).bind("touchend.jcrop", i) : m && a(document).bind("mousemove.jcrop", d).bind("mouseup.jcrop", e)
                }

                function c() {
                    hb.css({
                        "zIndex": 290
                    }), a(document).unbind(".jcrop")
                }

                function d(a) {
                    return k(h(a)), !1
                }

                function e(a) {
                    return a.preventDefault(), a.stopPropagation(), db && (db = !1, l(h(a)), nb.isAwake() && J.onSelect.call(qb, p(lb.getFixed())), c(), k = function() {}, l = function() {}), !1
                }

                function f(a, c, d) {
                    return db = !0, k = a, l = c, b(d), !1
                }

                function g(a) {
                    return k(h(kb.cfilter(a))), !1
                }

                function i(a) {
                    return e(kb.cfilter(a))
                }

                function j(a) {
                    hb.css("cursor", a)
                }
                var k = function() {},
                    l = function() {},
                    m = J.trackDocument;
                return m || hb.mousemove(d).mouseup(e).mouseout(e), R.before(hb), {
                    "activateHandlers": f,
                    "setCursor": j
                }
            }(),
            pb = function() {
                function b() {
                    J.keySupport && (f.show(), f.focus())
                }

                function c() {
                    f.hide()
                }

                function d(a, b, c) {
                    J.allowMove && (lb.moveOffset([b, c]), nb.updateVisible(!0)), a.preventDefault(), a.stopPropagation()
                }

                function e(a) {
                    if (a.ctrlKey || a.metaKey) return !0;
                    fb = a.shiftKey ? !0 : !1;
                    var b = fb ? 10 : 1;
                    switch (a.keyCode) {
                        case 37:
                            d(a, -b, 0);
                            break;
                        case 39:
                            d(a, b, 0);
                            break;
                        case 38:
                            d(a, 0, -b);
                            break;
                        case 40:
                            d(a, 0, b);
                            break;
                        case 27:
                            J.allowSelect && nb.release();
                            break;
                        case 9:
                            return !0
                    }
                    return !1
                }
                var f = a('<input type="radio" />').css({
                        "position": "fixed",
                        "left": "-120px",
                        "width": "12px"
                    }).addClass("jcrop-keymgr"),
                    g = a("<div />").css({
                        "position": "absolute",
                        "overflow": "hidden"
                    }).append(f);
                return J.keySupport && (f.keydown(e).blur(c), M || !J.fixedSupport ? (f.css({
                    "position": "absolute",
                    "left": "-20px"
                }), g.append(f).insertBefore(R)) : f.insertBefore(R)), {
                    "watchKeys": b
                }
            }();
        kb.support && hb.bind("touchstart.jcrop", kb.newSelection), X.hide(), H(!0);
        var qb = {
            "setImage": F,
            "animateTo": v,
            "setSelect": w,
            "setOptions": A,
            "tellSelect": y,
            "tellScaled": z,
            "setClass": u,
            "disable": B,
            "enable": C,
            "cancel": D,
            "release": nb.release,
            "destroy": E,
            "focus": pb.watchKeys,
            "getBounds": function() {
                return [S * bb, T * cb]
            },
            "getWidgetSize": function() {
                return [S, T]
            },
            "getScaleFactor": function() {
                return [bb, cb]
            },
            "getOptions": function() {
                return J
            },
            "ui": {
                "holder": U,
                "selection": Y
            }
        };
        return L && U.bind("selectstart", function() {
            return !1
        }), O.data("Jcrop", qb), qb
    }, a.fn.Jcrop = function(b, c) {
        var d;
        return this.each(function() {
            if (a(this).data("Jcrop")) {
                if ("api" === b) return a(this).data("Jcrop");
                a(this).data("Jcrop").setOptions(b)
            } else "IMG" == this.tagName ? a.Jcrop.Loader(this, function() {
                a(this).css({
                    "display": "block",
                    "visibility": "hidden"
                }), d = a.Jcrop(this, b), a.isFunction(c) && c.call(d)
            }) : (a(this).css({
                "display": "block",
                "visibility": "hidden"
            }), d = a.Jcrop(this, b), a.isFunction(c) && c.call(d))
        }), this
    }, a.Jcrop.Loader = function(b, c, d) {
        function e() {
            g.complete ? (f.unbind(".jcloader"), a.isFunction(c) && c.call(g)) : window.setTimeout(e, 50)
        }
        var f = a(b),
            g = f[0];
        f.bind("load.jcloader", e).bind("error.jcloader", function() {
            f.unbind(".jcloader"), a.isFunction(d) && d.call(g)
        }), g.complete && a.isFunction(c) && (f.unbind(".jcloader"), c.call(g))
    }, a.Jcrop.defaults = {
        "allowSelect": !0,
        "allowMove": !0,
        "allowResize": !0,
        "trackDocument": !0,
        "baseClass": "jcrop",
        "addClass": null,
        "bgColor": "black",
        "bgOpacity": .6,
        "bgFade": !1,
        "borderOpacity": .4,
        "handleOpacity": .5,
        "handleSize": null,
        "aspectRatio": 0,
        "keySupport": !0,
        "createHandles": ["n", "s", "e", "w", "nw", "ne", "se", "sw"],
        "createDragbars": ["n", "s", "e", "w"],
        "createBorders": ["n", "s", "e", "w"],
        "drawBorders": !0,
        "dragEdges": !0,
        "fixedSupport": !0,
        "touchSupport": null,
        "shade": null,
        "boxWidth": 0,
        "boxHeight": 0,
        "boundary": 2,
        "fadeTime": 400,
        "animationDelay": 20,
        "swingSpeed": 3,
        "minSelect": [0, 0],
        "maxSize": [0, 0],
        "minSize": [0, 0],
        "onChange": function() {},
        "onSelect": function() {},
        "onDblClick": function() {},
        "onRelease": function() {}
    }
}(jQuery);