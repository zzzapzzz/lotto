/*! SmartAdmin - v1.5 - 2014-09-27 */ ! function(a) {
    function b(b, f) {
        var h = f.Canvas;
        null == c && (d = h.prototype.getTextInfo, e = h.prototype.addText, c = h.prototype.render), h.prototype.render = function() {
            if (!b.getOptions().canvas) return c.call(this);
            var a = this.context,
                d = this._textCache;
            a.save(), a.textBaseline = "middle";
            for (var e in d)
                if (g.call(d, e)) {
                    var f = d[e];
                    for (var h in f)
                        if (g.call(f, h)) {
                            var i = f[h],
                                j = !0;
                            for (var k in i)
                                if (g.call(i, k)) {
                                    var l = i[k],
                                        m = l.positions,
                                        n = l.lines;
                                    j && (a.fillStyle = l.font.color, a.font = l.font.definition, j = !1);
                                    for (var o, p = 0; o = m[p]; p++)
                                        if (o.active)
                                            for (var q, r = 0; q = o.lines[r]; r++) a.fillText(n[r].text, q[0], q[1]);
                                        else m.splice(p--, 1);
                                    0 == m.length && delete i[k]
                                }
                        }
                }
            a.restore()
        }, h.prototype.getTextInfo = function(c, e, f, g, h) {
            if (!b.getOptions().canvas) return d.call(this, c, e, f, g, h);
            var i, j, k, l;
            if (e = "" + e, i = "object" == typeof f ? f.style + " " + f.variant + " " + f.weight + " " + f.size + "px " + f.family : f, j = this._textCache[c], null == j && (j = this._textCache[c] = {}), k = j[i], null == k && (k = j[i] = {}), l = k[e], null == l) {
                var m = this.context;
                if ("object" != typeof f) {
                    var n = a("<div>&nbsp;</div>").css("position", "absolute").addClass("string" == typeof f ? f : null).appendTo(this.getTextLayer(c));
                    f = {
                        "lineHeight": n.height(),
                        "style": n.css("font-style"),
                        "variant": n.css("font-variant"),
                        "weight": n.css("font-weight"),
                        "family": n.css("font-family"),
                        "color": n.css("color")
                    }, f.size = n.css("line-height", 1).height(), n.remove()
                }
                i = f.style + " " + f.variant + " " + f.weight + " " + f.size + "px " + f.family, l = k[e] = {
                    "width": 0,
                    "height": 0,
                    "positions": [],
                    "lines": [],
                    "font": {
                        "definition": i,
                        "color": f.color
                    }
                }, m.save(), m.font = i;
                for (var o = (e + "").replace(/<br ?\/?>|\r\n|\r/g, "\n").split("\n"), p = 0; p < o.length; ++p) {
                    var q = o[p],
                        r = m.measureText(q);
                    l.width = Math.max(r.width, l.width), l.height += f.lineHeight, l.lines.push({
                        "text": q,
                        "width": r.width,
                        "height": f.lineHeight
                    })
                }
                m.restore()
            }
            return l
        }, h.prototype.addText = function(a, c, d, f, g, h, i, j, k) {
            if (!b.getOptions().canvas) return e.call(this, a, c, d, f, g, h, i, j, k);
            var l = this.getTextInfo(a, f, g, h, i),
                m = l.positions,
                n = l.lines;
            d += l.height / n.length / 2, d = Math.round("middle" == k ? d - l.height / 2 : "bottom" == k ? d - l.height : d), window.opera && window.opera.version().split(".")[0] < 12 && (d -= 2);
            for (var o, p = 0; o = m[p]; p++)
                if (o.x == c && o.y == d) return void(o.active = !0);
            o = {
                "active": !0,
                "lines": [],
                "x": c,
                "y": d
            }, m.push(o);
            for (var q, p = 0; q = n[p]; p++) o.lines.push("center" == j ? [Math.round(c - q.width / 2), d] : "right" == j ? [Math.round(c - q.width), d] : [Math.round(c), d]), d += q.height
        }
    }
    var c, d, e, f = {
            "canvas": !0
        },
        g = Object.prototype.hasOwnProperty;
    a.plot.plugins.push({
        "init": b,
        "options": f,
        "name": "canvas",
        "version": "1.0"
    })
}(jQuery);