/*! SmartAdmin - v1.5 - 2014-09-27 */ ! function(a) {
    function b(a) {
        function b(a, b) {
            for (var c = null, d = 0; d < b.length && a != b[d]; ++d) b[d].stack == a.stack && (c = b[d]);
            return c
        }

        function c(a, c, d) {
            if (null != c.stack && c.stack !== !1) {
                var e = b(c, a.getData());
                if (e) {
                    for (var f, g, h, i, j, k, l, m, n = d.pointsize, o = d.points, p = e.datapoints.pointsize, q = e.datapoints.points, r = [], s = c.lines.show, t = c.bars.horizontal, u = n > 2 && (t ? d.format[2].x : d.format[2].y), v = s && c.lines.steps, w = !0, x = t ? 1 : 0, y = t ? 0 : 1, z = 0, A = 0;;) {
                        if (z >= o.length) break;
                        if (l = r.length, null == o[z]) {
                            for (m = 0; n > m; ++m) r.push(o[z + m]);
                            z += n
                        } else if (A >= q.length) {
                            if (!s)
                                for (m = 0; n > m; ++m) r.push(o[z + m]);
                            z += n
                        } else if (null == q[A]) {
                            for (m = 0; n > m; ++m) r.push(null);
                            w = !0, A += p
                        } else {
                            if (f = o[z + x], g = o[z + y], i = q[A + x], j = q[A + y], k = 0, f == i) {
                                for (m = 0; n > m; ++m) r.push(o[z + m]);
                                r[l + y] += j, k = j, z += n, A += p
                            } else if (f > i) {
                                if (s && z > 0 && null != o[z - n]) {
                                    for (h = g + (o[z - n + y] - g) * (i - f) / (o[z - n + x] - f), r.push(i), r.push(h + j), m = 2; n > m; ++m) r.push(o[z + m]);
                                    k = j
                                }
                                A += p
                            } else {
                                if (w && s) {
                                    z += n;
                                    continue
                                }
                                for (m = 0; n > m; ++m) r.push(o[z + m]);
                                s && A > 0 && null != q[A - p] && (k = j + (q[A - p + y] - j) * (f - i) / (q[A - p + x] - i)), r[l + y] += k, z += n
                            }
                            w = !1, l != r.length && u && (r[l + 2] += k)
                        }
                        if (v && l != r.length && l > 0 && null != r[l] && r[l] != r[l - n] && r[l + 1] != r[l - n + 1]) {
                            for (m = 0; n > m; ++m) r[l + n + m] = r[l + m];
                            r[l + 1] = r[l - n + 1]
                        }
                    }
                    d.points = r
                }
            }
        }
        a.hooks.processDatapoints.push(c)
    }
    var c = {
        "series": {
            "stack": null
        }
    };
    a.plot.plugins.push({
        "init": b,
        "options": c,
        "name": "stack",
        "version": "1.2"
    })
}(jQuery);