/*! SmartAdmin - v1.5 - 2014-09-27 */ ! function(a) {
    "use strict";
    var b = function(a) {
        this.init("typeaheadjs", a, b.defaults)
    };
    a.fn.editableutils.inherit(b, a.fn.editabletypes.text), a.extend(b.prototype, {
        "render": function() {
            this.renderClear(), this.setClass(), this.setAttr("placeholder"), this.$input.typeahead(this.options.typeahead), "bs3" === a.fn.editableform.engine && (this.$input.hasClass("input-sm") && this.$input.siblings("input.tt-hint").addClass("input-sm"), this.$input.hasClass("input-lg") && this.$input.siblings("input.tt-hint").addClass("input-lg"))
        }
    }), b.defaults = a.extend({}, a.fn.editabletypes.list.defaults, {
        "tpl": '<input type="text">',
        "typeahead": null,
        "clear": !0
    }), a.fn.editabletypes.typeaheadjs = b
}(window.jQuery);