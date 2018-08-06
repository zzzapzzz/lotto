/*! SmartAdmin - v1.5 - 2014-09-27 */ ! function(a) {
    function b(a, b) {
        var c = {
                "square": function(a, b, c, d) {
                    var e = d * Math.sqrt(Math.PI) / 2;
                    a.rect(b - e, c - e, e + e, e + e)
                },
                "diamond": function(a, b, c, d) {
                    var e = d * Math.sqrt(Math.PI / 2);
                    a.moveTo(b - e, c), a.lineTo(b, c - e), a.lineTo(b + e, c), a.lineTo(b, c + e), a.lineTo(b - e, c)
                },
                "triangle": function(a, b, c, d, e) {
                    var f = d * Math.sqrt(2 * Math.PI / Math.sin(Math.PI / 3)),
                        g = f * Math.sin(Math.PI / 3);
                    a.moveTo(b - f / 2, c + g / 2), a.lineTo(b + f / 2, c + g / 2), e || (a.lineTo(b, c - g / 2), a.lineTo(b - f / 2, c + g / 2))
                },
                "cross": function(a, b, c, d) {
                    var e = d * Math.sqrt(Math.PI) / 2;
                    a.moveTo(b - e, c - e), a.lineTo(b + e, c + e), a.moveTo(b - e, c + e), a.lineTo(b + e, c - e)
                }
            },
            d = b.points.symbol;
        c[d] && (b.points.symbol = c[d])
    }

    function c(a) {
        a.hooks.processDatapoints.push(b)
    }
    a.plot.plugins.push({
        "init": c,
        "name": "symbols",
        "version": "1.0"
    })
}(jQuery);