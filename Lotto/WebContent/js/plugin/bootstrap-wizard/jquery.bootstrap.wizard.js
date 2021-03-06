/*! SmartAdmin - v1.5 - 2014-09-27 */ ! function(a) {
    var b = function(b, c) {
        var b = a(b),
            d = this,
            e = 'li:has([data-toggle="tab"])',
            f = a.extend({}, a.fn.bootstrapWizard.defaults, c),
            g = null,
            h = null;
        this.rebindClick = function(a, b) {
            a.unbind("click", b).bind("click", b)
        }, this.fixNavigationButtons = function() {
            return g.length || (h.find("a:first").tab("show"), g = h.find(e + ":first")), a(f.previousSelector, b).toggleClass("disabled", d.firstIndex() >= d.currentIndex()), a(f.nextSelector, b).toggleClass("disabled", d.currentIndex() >= d.navigationLength()), d.rebindClick(a(f.nextSelector, b), d.next), d.rebindClick(a(f.previousSelector, b), d.previous), d.rebindClick(a(f.lastSelector, b), d.last), d.rebindClick(a(f.firstSelector, b), d.first), f.onTabShow && "function" == typeof f.onTabShow && f.onTabShow(g, h, d.currentIndex()) === !1 ? !1 : void 0
        }, this.next = function() {
            return b.hasClass("last") ? !1 : f.onNext && "function" == typeof f.onNext && f.onNext(g, h, d.nextIndex()) === !1 ? !1 : ($index = d.nextIndex(), void($index > d.navigationLength() || h.find(e + ":eq(" + $index + ") a").tab("show")))
        }, this.previous = function() {
            return b.hasClass("first") ? !1 : f.onPrevious && "function" == typeof f.onPrevious && f.onPrevious(g, h, d.previousIndex()) === !1 ? !1 : ($index = d.previousIndex(), void(0 > $index || h.find(e + ":eq(" + $index + ") a").tab("show")))
        }, this.first = function() {
            return f.onFirst && "function" == typeof f.onFirst && f.onFirst(g, h, d.firstIndex()) === !1 ? !1 : b.hasClass("disabled") ? !1 : void h.find(e + ":eq(0) a").tab("show")
        }, this.last = function() {
            return f.onLast && "function" == typeof f.onLast && f.onLast(g, h, d.lastIndex()) === !1 ? !1 : b.hasClass("disabled") ? !1 : void h.find(e + ":eq(" + d.navigationLength() + ") a").tab("show")
        }, this.currentIndex = function() {
            return h.find(e).index(g)
        }, this.firstIndex = function() {
            return 0
        }, this.lastIndex = function() {
            return d.navigationLength()
        }, this.getIndex = function(a) {
            return h.find(e).index(a)
        }, this.nextIndex = function() {
            return h.find(e).index(g) + 1
        }, this.previousIndex = function() {
            return h.find(e).index(g) - 1
        }, this.navigationLength = function() {
            return h.find(e).length - 1
        }, this.activeTab = function() {
            return g
        }, this.nextTab = function() {
            return h.find(e + ":eq(" + (d.currentIndex() + 1) + ")").length ? h.find(e + ":eq(" + (d.currentIndex() + 1) + ")") : null
        }, this.previousTab = function() {
            return d.currentIndex() <= 0 ? null : h.find(e + ":eq(" + parseInt(d.currentIndex() - 1) + ")")
        }, this.show = function(a) {
            return b.find(e + ":eq(" + a + ") a").tab("show")
        }, this.disable = function(a) {
            h.find(e + ":eq(" + a + ")").addClass("disabled")
        }, this.enable = function(a) {
            h.find(e + ":eq(" + a + ")").removeClass("disabled")
        }, this.hide = function(a) {
            h.find(e + ":eq(" + a + ")").hide()
        }, this.display = function(a) {
            h.find(e + ":eq(" + a + ")").show()
        }, this.remove = function(b) {
            var c = b[0],
                d = "undefined" != typeof b[1] ? b[1] : !1,
                f = h.find(e + ":eq(" + c + ")");
            if (d) {
                var g = f.find("a").attr("href");
                a(g).remove()
            }
            f.remove()
        }, h = b.find("ul:first", b), g = h.find(e + ".active", b), h.hasClass(f.tabClass) || h.addClass(f.tabClass), f.onInit && "function" == typeof f.onInit && f.onInit(g, h, 0), f.onShow && "function" == typeof f.onShow && f.onShow(g, h, d.nextIndex()), d.fixNavigationButtons(), a('a[data-toggle="tab"]', h).on("click", function(b) {
            var c = h.find(e).index(a(b.currentTarget).parent(e));
            return f.onTabClick && "function" == typeof f.onTabClick && f.onTabClick(g, h, d.currentIndex(), c) === !1 ? !1 : void 0
        }), a('a[data-toggle="tab"]', h).on("shown shown.bs.tab", function(b) {
            $element = a(b.target).parent();
            var c = h.find(e).index($element);
            return $element.hasClass("disabled") ? !1 : f.onTabChange && "function" == typeof f.onTabChange && f.onTabChange(g, h, d.currentIndex(), c) === !1 ? !1 : (g = $element, void d.fixNavigationButtons())
        })
    };
    a.fn.bootstrapWizard = function(c) {
        if ("string" == typeof c) {
            var d = Array.prototype.slice.call(arguments, 1);
            return 1 === d.length && d.toString(), this.data("bootstrapWizard")[c](d)
        }
        return this.each(function() {
            var d = a(this);
            if (!d.data("bootstrapWizard")) {
                var e = new b(d, c);
                d.data("bootstrapWizard", e)
            }
        })
    }, a.fn.bootstrapWizard.defaults = {
        "tabClass": "nav nav-pills",
        "nextSelector": ".wizard li.next",
        "previousSelector": ".wizard li.previous",
        "firstSelector": ".wizard li.first",
        "lastSelector": ".wizard li.last",
        "onShow": null,
        "onInit": null,
        "onNext": null,
        "onPrevious": null,
        "onLast": null,
        "onFirst": null,
        "onTabChange": null,
        "onTabClick": null,
        "onTabShow": null
    }
}(jQuery);