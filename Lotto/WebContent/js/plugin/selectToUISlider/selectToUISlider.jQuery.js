/*! SmartAdmin - v1.5 - 2014-09-27 */
jQuery.fn.selectToUISlider = function(a) {
    function b(a) {
        return a.constructor == Array
    }

    function c(a) {
        return "text" == f.tooltipSrc ? h[a].text : h[a].value
    }

    function d(a) {
        return (a / (h.length - 1) * 100).toFixed(2) + "%"
    }
    var e = jQuery(this),
        f = jQuery.extend({
            "labels": 3,
            "tooltip": !0,
            "tooltipSrc": "text",
            "labelSrc": "value",
            "sliderOptions": null
        }, a),
        g = function() {
            var a = [];
            return e.each(function() {
                a.push("handle_" + jQuery(this).attr("id"))
            }), a
        }(),
        h = function() {
            var a = [];
            return e.eq(0).find("option").each(function() {
                a.push({
                    "value": jQuery(this).attr("value"),
                    "text": jQuery(this).text()
                })
            }), a
        }(),
        i = function() {
            if (e.eq(0).find("optgroup").size() > 0) {
                var a = [];
                return e.eq(0).find("optgroup").each(function(b) {
                    a[b] = {}, a[b].label = jQuery(this).attr("label"), a[b].options = [], jQuery(this).find("option").each(function() {
                        a[b].options.push({
                            "text": jQuery(this).text(),
                            "value": jQuery(this).attr("value")
                        })
                    })
                }), a
            }
            return null
        }(),
        j = {
            "step": 1,
            "min": 0,
            "orientation": "horizontal",
            "max": h.length - 1,
            "range": e.length > 1,
            "slide": function(a, b) {
                var d = jQuery(b.handle),
                    e = c(b.value);
                d.attr("aria-valuetext", e).attr("aria-valuenow", b.value).find(".ui-slider-tooltip .ttContent").text(e);
                var f = jQuery("#" + d.attr("id").split("handle_")[1]);
                f.find("option").eq(b.value).attr("selected", "selected")
            },
            "values": function() {
                var a = [];
                return e.each(function() {
                    a.push(jQuery(this).get(0).selectedIndex)
                }), a
            }()
        };
    f.sliderOptions = a ? jQuery.extend(j, a.sliderOptions) : j, e.bind("change keyup click", function() {
        var a = jQuery(this).get(0).selectedIndex,
            b = jQuery("#handle_" + jQuery(this).attr("id")),
            c = b.data("handleNum");
        b.parents(".ui-slider:eq(0)").slider("values", c, a)
    });
    var k = jQuery("<div></div>");
    if (e.each(function(a) {
            var b = "",
                d = jQuery("label[for=" + jQuery(this).attr("id") + "]"),
                e = d.size() > 0 ? "Slider control for " + d.text() : "",
                h = d.attr("id") || d.attr("id", "label_" + g[a]).attr("id");
            0 == f.tooltip && (b = ' style="display: none;"'), jQuery('<a href="#" tabindex="0" id="' + g[a] + '" class="ui-slider-handle" role="slider" aria-labelledby="' + h + '" aria-valuemin="' + f.sliderOptions.min + '" aria-valuemax="' + f.sliderOptions.max + '" aria-valuenow="' + f.sliderOptions.values[a] + '" aria-valuetext="' + c(f.sliderOptions.values[a]) + '" ><span class="screenReaderContext">' + e + '</span><span class="ui-slider-tooltip ui-widget-content ui-corner-all"' + b + '><span class="ttContent"></span><span class="ui-tooltip-pointer-down ui-widget-content"><span class="ui-tooltip-pointer-down-inner"></span></span></span></a>').data("handleNum", a).appendTo(k)
        }), i) {
        var l = 0,
            m = k.append('<dl class="ui-slider-scale ui-helper-reset" role="presentation"></dl>').find(".ui-slider-scale:eq(0)");
        jQuery(i).each(function(a) {
            m.append('<dt style="width: ' + (100 / i.length).toFixed(2) + "%; left:" + (a / (i.length - 1) * 100).toFixed(2) + '%"><span>' + this.label + "</span></dt>");
            var b = this.options;
            jQuery(this.options).each(function(a) {
                var c = l == h.length - 1 || 0 == l ? 'style="display: none;"' : "",
                    e = "text" == f.labelSrc ? b[a].text : b[a].value;
                m.append('<dd style="left:' + d(l) + '"><span class="ui-slider-label">' + e + '</span><span class="ui-slider-tic ui-widget-content"' + c + "></span></dd>"), l++
            })
        })
    } else {
        var m = k.append('<ol class="ui-slider-scale ui-helper-reset" role="presentation"></ol>').find(".ui-slider-scale:eq(0)");
        jQuery(h).each(function(a) {
            var b = a == h.length - 1 || 0 == a ? 'style="display: none;"' : "",
                c = "text" == f.labelSrc ? this.text : this.value;
            m.append('<li style="left:' + d(a) + '"><span class="ui-slider-label">' + c + '</span><span class="ui-slider-tic ui-widget-content"' + b + "></span></li>")
        })
    }
    f.labels > 1 && k.find(".ui-slider-scale li:last span.ui-slider-label, .ui-slider-scale dd:last span.ui-slider-label").addClass("ui-slider-label-show");
    for (var n = Math.max(1, Math.round(h.length / f.labels)), o = 0; o < h.length; o += n) h.length - o > n && k.find(".ui-slider-scale li:eq(" + o + ") span.ui-slider-label, .ui-slider-scale dd:eq(" + o + ") span.ui-slider-label").addClass("ui-slider-label-show");
    k.find(".ui-slider-scale dt").each(function(a) {
        jQuery(this).css({
            "left": (100 / i.length * a).toFixed(2) + "%"
        })
    }), k.insertAfter(jQuery(this).eq(this.length - 1)).slider(f.sliderOptions).attr("role", "application").find(".ui-slider-label").each(function() {
        jQuery(this).css("marginLeft", -jQuery(this).width() / 2)
    }), k.find(".ui-tooltip-pointer-down-inner").each(function() {
        var a = jQuery(".ui-tooltip-pointer-down-inner").css("borderTopWidth"),
            b = jQuery(this).parents(".ui-slider-tooltip").css("backgroundColor");
        jQuery(this).css("border-top", a + " solid " + b)
    });
    var p = k.slider("values");
    return b(p) ? jQuery(p).each(function(a) {
        k.find(".ui-slider-tooltip .ttContent").eq(a).text(c(this))
    }) : k.find(".ui-slider-tooltip .ttContent").eq(0).text(c(p)), this
};