/*! SmartAdmin - v1.5 - 2014-09-27 */
(function() {
    var a;
    a = jQuery, a.fn.extend({
        "rowslide": function(b) {
            var c, d, e;
            return c = this, d = this.find("td"), $row_id = c.attr("id"), e = this.getTallestTd(d), c.animate({
                "opacity": 0
            }, 80, function() {
                var f, g;
                return d.each(function() {
                    return this !== e ? (a(this).empty(), a(this).css("padding", "0")) : void 0
                }), f = a(e), g = a("<div/>"), g.css(f.css("padding")), f.css("padding", "0"), f.wrapInner(g), f.children("div").animate({
                    "height": "hide"
                }, 100, "swing", function() {
                    return c.remove(), b ? b() : void 0
                })
            })
        },
        "getTallestTd": function(b) {
            var c, d;
            return d = -1, c = 0, b.each(function(b, e) {
                return a(e).height() > c ? (d = b, c = a(e).height()) : void 0
            }), b.get(d)
        }
    })
}).call(this);