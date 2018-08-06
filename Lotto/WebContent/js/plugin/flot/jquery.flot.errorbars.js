/*! SmartAdmin - v1.5 - 2014-09-27 */ ! function(a) {
    function b(a, b, c, d) {
        if (b.points.errorbars) {
            var e = [{
                    "x": !0,
                    "number": !0,
                    "required": !0
                }, {
                    "y": !0,
                    "number": !0,
                    "required": !0
                }],
                f = b.points.errorbars;
            ("x" == f || "xy" == f) && (b.points.xerr.asymmetric ? (e.push({
                "x": !0,
                "number": !0,
                "required": !0
            }), e.push({
                "x": !0,
                "number": !0,
                "required": !0
            })) : e.push({
                "x": !0,
                "number": !0,
                "required": !0
            })), ("y" == f || "xy" == f) && (b.points.yerr.asymmetric ? (e.push({
                "y": !0,
                "number": !0,
                "required": !0
            }), e.push({
                "y": !0,
                "number": !0,
                "required": !0
            })) : e.push({
                "y": !0,
                "number": !0,
                "required": !0
            })), d.format = e
        }
    }

    function c(a, b) {
        var c = a.datapoints.points,
            d = null,
            e = null,
            f = null,
            g = null,
            h = a.points.xerr,
            i = a.points.yerr,
            j = a.points.errorbars;
        "x" == j || "xy" == j ? h.asymmetric ? (d = c[b + 2], e = c[b + 3], "xy" == j && (i.asymmetric ? (f = c[b + 4], g = c[b + 5]) : f = c[b + 4])) : (d = c[b + 2], "xy" == j && (i.asymmetric ? (f = c[b + 3], g = c[b + 4]) : f = c[b + 3])) : "y" == j && (i.asymmetric ? (f = c[b + 2], g = c[b + 3]) : f = c[b + 2]), null == e && (e = d), null == g && (g = f);
        var k = [d, e, f, g];
        return h.show || (k[0] = null, k[1] = null), i.show || (k[2] = null, k[3] = null), k
    }

    function d(a, b, d) {
        var f = d.datapoints.points,
            g = d.datapoints.pointsize,
            h = [d.xaxis, d.yaxis],
            i = d.points.radius,
            j = [d.points.xerr, d.points.yerr],
            k = !1;
        if (h[0].p2c(h[0].max) < h[0].p2c(h[0].min)) {
            k = !0;
            var l = j[0].lowerCap;
            j[0].lowerCap = j[0].upperCap, j[0].upperCap = l
        }
        var m = !1;
        if (h[1].p2c(h[1].min) < h[1].p2c(h[1].max)) {
            m = !0;
            var l = j[1].lowerCap;
            j[1].lowerCap = j[1].upperCap, j[1].upperCap = l
        }
        for (var n = 0; n < d.datapoints.points.length; n += g)
            for (var o = c(d, n), p = 0; p < j.length; p++) {
                var q = [h[p].min, h[p].max];
                if (o[p * j.length]) {
                    var r = f[n],
                        s = f[n + 1],
                        t = [r, s][p] + o[p * j.length + 1],
                        u = [r, s][p] - o[p * j.length];
                    if ("x" == j[p].err && (s > h[1].max || s < h[1].min || t < h[0].min || u > h[0].max)) continue;
                    if ("y" == j[p].err && (r > h[0].max || r < h[0].min || t < h[1].min || u > h[1].max)) continue;
                    var v = !0,
                        w = !0;
                    if (t > q[1] && (v = !1, t = q[1]), u < q[0] && (w = !1, u = q[0]), "x" == j[p].err && k || "y" == j[p].err && m) {
                        var l = u;
                        u = t, t = l, l = w, w = v, v = l, l = q[0], q[0] = q[1], q[1] = l
                    }
                    r = h[0].p2c(r), s = h[1].p2c(s), t = h[p].p2c(t), u = h[p].p2c(u), q[0] = h[p].p2c(q[0]), q[1] = h[p].p2c(q[1]);
                    var x = j[p].lineWidth ? j[p].lineWidth : d.points.lineWidth,
                        y = null != d.points.shadowSize ? d.points.shadowSize : d.shadowSize;
                    if (x > 0 && y > 0) {
                        var z = y / 2;
                        b.lineWidth = z, b.strokeStyle = "rgba(0,0,0,0.1)", e(b, j[p], r, s, t, u, v, w, i, z + z / 2, q), b.strokeStyle = "rgba(0,0,0,0.2)", e(b, j[p], r, s, t, u, v, w, i, z / 2, q)
                    }
                    b.strokeStyle = j[p].color ? j[p].color : d.color, b.lineWidth = x, e(b, j[p], r, s, t, u, v, w, i, 0, q)
                }
            }
    }

    function e(b, c, d, e, g, h, i, j, k, l, m) {
        e += l, g += l, h += l, "x" == c.err ? (g > d + k ? f(b, [
            [g, e],
            [Math.max(d + k, m[0]), e]
        ]) : i = !1, d - k > h ? f(b, [
            [Math.min(d - k, m[1]), e],
            [h, e]
        ]) : j = !1) : (e - k > g ? f(b, [
            [d, g],
            [d, Math.min(e - k, m[0])]
        ]) : i = !1, h > e + k ? f(b, [
            [d, Math.max(e + k, m[1])],
            [d, h]
        ]) : j = !1), k = null != c.radius ? c.radius : k, i && ("-" == c.upperCap ? "x" == c.err ? f(b, [
            [g, e - k],
            [g, e + k]
        ]) : f(b, [
            [d - k, g],
            [d + k, g]
        ]) : a.isFunction(c.upperCap) && ("x" == c.err ? c.upperCap(b, g, e, k) : c.upperCap(b, d, g, k))), j && ("-" == c.lowerCap ? "x" == c.err ? f(b, [
            [h, e - k],
            [h, e + k]
        ]) : f(b, [
            [d - k, h],
            [d + k, h]
        ]) : a.isFunction(c.lowerCap) && ("x" == c.err ? c.lowerCap(b, h, e, k) : c.lowerCap(b, d, h, k)))
    }

    function f(a, b) {
        a.beginPath(), a.moveTo(b[0][0], b[0][1]);
        for (var c = 1; c < b.length; c++) a.lineTo(b[c][0], b[c][1]);
        a.stroke()
    }

    function g(b, c) {
        var e = b.getPlotOffset();
        c.save(), c.translate(e.left, e.top), a.each(b.getData(), function(a, e) {
            e.points.errorbars && (e.points.xerr.show || e.points.yerr.show) && d(b, c, e)
        }), c.restore()
    }

    function h(a) {
        a.hooks.processRawData.push(b), a.hooks.draw.push(g)
    }
    var i = {
        "series": {
            "points": {
                "errorbars": null,
                "xerr": {
                    "err": "x",
                    "show": null,
                    "asymmetric": null,
                    "upperCap": null,
                    "lowerCap": null,
                    "color": null,
                    "radius": null
                },
                "yerr": {
                    "err": "y",
                    "show": null,
                    "asymmetric": null,
                    "upperCap": null,
                    "lowerCap": null,
                    "color": null,
                    "radius": null
                }
            }
        }
    };
    a.plot.plugins.push({
        "init": h,
        "options": i,
        "name": "errorbars",
        "version": "1.0"
    })
}(jQuery);