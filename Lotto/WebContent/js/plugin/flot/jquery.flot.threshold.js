/*! SmartAdmin - v1.5 - 2014-09-27 */ ! function(a) {
    function b(b) {
        function c(b, c, d, e, f) {
            var g, h, i, j, k, l = d.pointsize,
                m = a.extend({}, c);
            m.datapoints = {
                "points": [],
                "pointsize": l,
                "format": d.format
            }, m.label = null, m.color = f, m.threshold = null, m.originSeries = c, m.data = [];
            var n, o = d.points,
                p = c.lines.show,
                q = [],
                r = [];
            for (g = 0; g < o.length; g += l) {
                if (h = o[g], i = o[g + 1], k = j, j = e > i ? q : r, p && k != j && null != h && g > 0 && null != o[g - l]) {
                    var s = h + (e - i) * (h - o[g - l]) / (i - o[g - l + 1]);
                    for (k.push(s), k.push(e), n = 2; l > n; ++n) k.push(o[g + n]);
                    for (j.push(null), j.push(null), n = 2; l > n; ++n) j.push(o[g + n]);
                    for (j.push(s), j.push(e), n = 2; l > n; ++n) j.push(o[g + n])
                }
                for (j.push(h), j.push(i), n = 2; l > n; ++n) j.push(o[g + n])
            }
            if (d.points = r, m.datapoints.points = q, m.datapoints.points.length > 0) {
                var t = a.inArray(c, b.getData());
                b.getData().splice(t + 1, 0, m)
            }
        }

        function d(b, d, e) {
            d.threshold && (d.threshold instanceof Array ? (d.threshold.sort(function(a, b) {
                return a.below - b.below
            }), a(d.threshold).each(function(a, f) {
                c(b, d, e, f.below, f.color)
            })) : c(b, d, e, d.threshold.below, d.threshold.color))
        }
        b.hooks.processDatapoints.push(d)
    }
    var c = {
        "series": {
            "threshold": null
        }
    };
    a.plot.plugins.push({
        "init": b,
        "options": c,
        "name": "threshold",
        "version": "1.2"
    })
}(jQuery);