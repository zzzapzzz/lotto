/*! SmartAdmin - v1.5 - 2014-09-27 */
var old = $.fn.wizard,
    Wizard = function(a, b) {
        var c;
        this.$element = $(a), this.options = $.extend({}, $.fn.wizard.defaults, b), this.options.disablePreviousStep = "previous" === this.$element.data().restrict ? !0 : !1, this.currentStep = this.options.selectedItem.step, this.numSteps = this.$element.find(".steps li").length, this.$prevBtn = this.$element.find("button.btn-prev"), this.$nextBtn = this.$element.find("button.btn-next"), c = this.$nextBtn.children().detach(), this.nextText = $.trim(this.$nextBtn.text()), this.$nextBtn.append(c), this.$prevBtn.on("click", $.proxy(this.previous, this)), this.$nextBtn.on("click", $.proxy(this.next, this)), this.$element.on("click", "li.complete", $.proxy(this.stepclicked, this)), this.currentStep > 1 && this.selectedItem(this.options.selectedItem), this.options.disablePreviousStep && (this.$prevBtn.attr("disabled", !0), this.$element.find(".steps").addClass("previous-disabled"))
    };
Wizard.prototype = {
    "constructor": Wizard,
    "setState": function() {
        var a = this.currentStep > 1,
            b = 1 === this.currentStep,
            c = this.currentStep === this.numSteps;
        this.options.disablePreviousStep || this.$prevBtn.attr("disabled", b === !0 || a === !1);
        var d = this.$nextBtn.data();
        if (d && d.last && (this.lastText = d.last, "undefined" != typeof this.lastText)) {
            var e = c !== !0 ? this.nextText : this.lastText,
                f = this.$nextBtn.children().detach();
            this.$nextBtn.text(e).append(f)
        }
        var g = this.$element.find(".steps li");
        g.removeClass("active").removeClass("complete"), g.find("span.badge").removeClass("badge-info").removeClass("badge-success");
        var h = ".steps li:lt(" + (this.currentStep - 1) + ")",
            i = this.$element.find(h);
        i.addClass("complete"), i.find("span.badge").addClass("badge-success");
        var j = ".steps li:eq(" + (this.currentStep - 1) + ")",
            k = this.$element.find(j);
        k.addClass("active"), k.find("span.badge").addClass("badge-info");
        var l = k.data().target;
        this.$element.next(".step-content").find(".step-pane").removeClass("active"), $(l).addClass("active"), this.$element.find(".steps").first().attr("style", "margin-left: 0");
        var m = 0;
        this.$element.find(".steps > li").each(function() {
            m += $(this).outerWidth()
        });
        var n = 0;
        if (n = this.$element.find(".actions").length ? this.$element.width() - this.$element.find(".actions").first().outerWidth() : this.$element.width(), m > n) {
            var o = m - n;
            this.$element.find(".steps").first().attr("style", "margin-left: -" + o + "px"), this.$element.find("li.active").first().position().left < 200 && (o += this.$element.find("li.active").first().position().left - 200, 1 > o ? this.$element.find(".steps").first().attr("style", "margin-left: 0") : this.$element.find(".steps").first().attr("style", "margin-left: -" + o + "px"))
        }
        this.$element.trigger("changed")
    },
    "stepclicked": function(a) {
        var b = $(a.currentTarget),
            c = this.$element.find(".steps li").index(b),
            d = !0;
        if (this.options.disablePreviousStep && c < this.currentStep && (d = !1), d) {
            var e = $.Event("stepclick");
            if (this.$element.trigger(e, {
                    "step": c + 1
                }), e.isDefaultPrevented()) return;
            this.currentStep = c + 1, this.setState()
        }
    },
    "previous": function() {
        var a = this.currentStep > 1;
        if (this.options.disablePreviousStep && (a = !1), a) {
            var b = $.Event("change");
            if (this.$element.trigger(b, {
                    "step": this.currentStep,
                    "direction": "previous"
                }), b.isDefaultPrevented()) return;
            this.currentStep -= 1, this.setState()
        }
    },
    "next": function() {
        var a = this.currentStep + 1 <= this.numSteps,
            b = this.currentStep === this.numSteps;
        if (a) {
            var c = $.Event("change");
            if (this.$element.trigger(c, {
                    "step": this.currentStep,
                    "direction": "next"
                }), c.isDefaultPrevented()) return;
            this.currentStep += 1, this.setState()
        } else b && this.$element.trigger("finished")
    },
    "selectedItem": function(a) {
        var b, c;
        return a ? (c = a.step || -1, c >= 1 && c <= this.numSteps && (this.currentStep = c, this.setState()), b = this) : b = {
            "step": this.currentStep
        }, b
    }
}, $.fn.wizard = function(a) {
    var b, c = Array.prototype.slice.call(arguments, 1),
        d = this.each(function() {
            var d = $(this),
                e = d.data("wizard"),
                f = "object" == typeof a && a;
            e || d.data("wizard", e = new Wizard(this, f)), "string" == typeof a && (b = e[a].apply(e, c))
        });
    return void 0 === b ? d : b
}, $.fn.wizard.defaults = {
    "selectedItem": {
        "step": 1
    }
}, $.fn.wizard.Constructor = Wizard, $.fn.wizard.noConflict = function() {
    return $.fn.wizard = old, this
}, $(function() {
    $("body").on("mouseover.wizard.data-api", ".wizard", function() {
        var a = $(this);
        a.data("wizard") || a.wizard(a.data())
    })
});