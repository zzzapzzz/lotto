/*! SmartAdmin - v1.5 - 2014-09-27 */ ! function(a) {
    "use strict";

    function b(b, c) {
        this.options = this.mergeOptions(c), this.$select = a(b), this.originalOptions = this.$select.clone()[0].options, this.query = "", this.searchTimeout = null, this.options.multiple = "multiple" == this.$select.attr("multiple"), this.options.onChange = a.proxy(this.options.onChange, this), this.buildContainer(), this.buildButton(), this.buildSelectAll(), this.buildDropdown(), this.buildDropdownOptions(), this.buildFilter(), this.updateButtonText(), this.$select.hide().after(this.$container)
    }
    "undefined" != typeof ko && ko.bindingHandlers && !ko.bindingHandlers.multiselect && (ko.bindingHandlers.multiselect = {
        "init": function() {},
        "update": function(b, c, d) {
            var e = a(b).data("multiselect");
            e ? d().options && d().options().length !== e.originalOptions.length && (e.updateOriginalOptions(), a(b).multiselect("rebuild")) : a(b).multiselect(ko.utils.unwrapObservable(c()))
        }
    }), b.prototype = {
        "defaults": {
            "buttonText": function(b) {
                if (0 == b.length) return this.nonSelectedText + ' <b class="caret"></b>';
                if (b.length > 3) return b.length + " " + this.nSelectedText + ' <b class="caret"></b>';
                var c = "";
                return b.each(function() {
                    var b = void 0 !== a(this).attr("label") ? a(this).attr("label") : a(this).html();
                    c += b + ", "
                }), c.substr(0, c.length - 2) + ' <b class="caret"></b>'
            },
            "buttonTitle": function(b) {
                if (0 == b.length) return this.nonSelectedText;
                var c = "";
                return b.each(function() {
                    c += a(this).text() + ", "
                }), c.substr(0, c.length - 2)
            },
            "onChange": function() {},
            "buttonClass": "btn",
            "dropRight": !1,
            "selectedClass": "active",
            "buttonWidth": "auto",
            "buttonContainer": '<div class="btn-group" />',
            "maxHeight": !1,
            "includeSelectAllOption": !1,
            "selectAllText": " Select all",
            "selectAllValue": "multiselect-all",
            "enableFiltering": !1,
            "enableCaseInsensitiveFiltering": !1,
            "filterPlaceholder": "Search",
            "filterBehavior": "text",
            "preventInputChangeEvent": !1,
            "nonSelectedText": "None selected",
            "nSelectedText": "selected"
        },
        "templates": {
            "button": '<button type="button" class="multiselect dropdown-toggle" data-toggle="dropdown"></button>',
            "ul": '<ul class="multiselect-container dropdown-menu"></ul>',
            "filter": '<div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span><input class="form-control multiselect-search" type="text"></div>',
            "li": '<li><a href="javascript:void(0);"><label></label></a></li>',
            "liGroup": '<li><label class="multiselect-group"></label></li>'
        },
        "constructor": b,
        "buildContainer": function() {
            this.$container = a(this.options.buttonContainer)
        },
        "buildButton": function() {
            this.$button = a(this.templates.button).addClass(this.options.buttonClass), this.$select.prop("disabled") ? this.disable() : this.enable(), this.options.buttonWidth && this.$button.css({
                "width": this.options.buttonWidth
            });
            var b = this.$select.attr("tabindex");
            b && this.$button.attr("tabindex", b), this.$container.prepend(this.$button)
        },
        "buildDropdown": function() {
            this.$ul = a(this.templates.ul), this.options.dropRight && this.$ul.addClass("pull-right"), this.options.maxHeight && this.$ul.css({
                "max-height": this.options.maxHeight + "px",
                "overflow-y": "auto",
                "overflow-x": "hidden"
            }), this.$container.append(this.$ul)
        },
        "buildDropdownOptions": function() {
            this.$select.children().each(a.proxy(function(b, c) {
                var d = a(c).prop("tagName").toLowerCase();
                "optgroup" == d ? this.createOptgroup(c) : "option" == d && this.createOptionValue(c)
            }, this)), a("li input", this.$ul).on("change", a.proxy(function(b) {
                var c = a(b.target).prop("checked") || !1,
                    d = a(b.target).val() == this.options.selectAllValue;
                this.options.selectedClass && (c ? a(b.target).parents("li").addClass(this.options.selectedClass) : a(b.target).parents("li").removeClass(this.options.selectedClass));
                var e = a(b.target).val(),
                    f = this.getOptionByValue(e),
                    g = a("option", this.$select).not(f),
                    h = a("input", this.$container).not(a(b.target));
                return d && h.filter(function() {
                    return a(this).is(":checked") != c
                }).trigger("click"), c ? (f.prop("selected", !0), this.options.multiple ? f.prop("selected", !0) : (this.options.selectedClass && a(h).parents("li").removeClass(this.options.selectedClass), a(h).prop("checked", !1), g.prop("selected", !1), this.$button.click()), "active" == this.options.selectedClass && g.parents("a").css("outline", "")) : f.prop("selected", !1), this.updateButtonText(), this.$select.change(), this.options.onChange(f, c), this.options.preventInputChangeEvent ? !1 : void 0
            }, this)), a("li a", this.$ul).on("touchstart click", function(b) {
                b.stopPropagation(), a(b.target).blur()
            }), this.$container.on("keydown", a.proxy(function(b) {
                if (!a('input[type="text"]', this.$container).is(":focus"))
                    if (9 != b.keyCode && 27 != b.keyCode || !this.$container.hasClass("open")) {
                        var c = a(this.$container).find("li:not(.divider):visible a");
                        if (!c.length) return;
                        var d = c.index(c.filter(":focus"));
                        38 == b.keyCode && d > 0 ? d-- : 40 == b.keyCode && d < c.length - 1 ? d++ : ~d || (d = 0);
                        var e = c.eq(d);
                        if (e.focus(), 32 == b.keyCode || 13 == b.keyCode) {
                            var f = e.find("input");
                            f.prop("checked", !f.prop("checked")), f.change()
                        }
                        b.stopPropagation(), b.preventDefault()
                    } else this.$button.click()
            }, this))
        },
        "createOptionValue": function(b) {
            a(b).is(":selected") && a(b).prop("selected", !0);
            var c = a(b).attr("label") || a(b).html(),
                d = a(b).val(),
                e = this.options.multiple ? "checkbox" : "radio",
                f = a(this.templates.li);
            a("label", f).addClass(e), a("label", f).append('<input type="' + e + '" />');
            var g = a(b).prop("selected") || !1,
                h = a("input", f);
            h.val(d), d == this.options.selectAllValue && h.parent().parent().addClass("multiselect-all"), a("label", f).append(" " + c), this.$ul.append(f), a(b).is(":disabled") && h.attr("disabled", "disabled").prop("disabled", !0).parents("li").addClass("disabled"), h.prop("checked", g), g && this.options.selectedClass && h.parents("li").addClass(this.options.selectedClass)
        },
        "createOptgroup": function(b) {
            var c = a(b).prop("label"),
                d = a(this.templates.liGroup);
            a("label", d).text(c), this.$ul.append(d), a("option", b).each(a.proxy(function(a, b) {
                this.createOptionValue(b)
            }, this))
        },
        "buildSelectAll": function() {
            var a = this.$select[0][0] ? this.$select[0][0].value == this.options.selectAllValue : !1;
            this.options.includeSelectAllOption && this.options.multiple && !a && this.$select.prepend('<option value="' + this.options.selectAllValue + '">' + this.options.selectAllText + "</option>")
        },
        "buildFilter": function() {
            if (this.options.enableFiltering || this.options.enableCaseInsensitiveFiltering) {
                var b = Math.max(this.options.enableFiltering, this.options.enableCaseInsensitiveFiltering);
                this.$select.find("option").length >= b && (this.$filter = a(this.templates.filter), a("input", this.$filter).attr("placeholder", this.options.filterPlaceholder), this.$ul.prepend(this.$filter), this.$filter.val(this.query).on("click", function(a) {
                    a.stopPropagation()
                }).on("keydown", a.proxy(function(b) {
                    clearTimeout(this.searchTimeout), this.searchTimeout = this.asyncFunction(a.proxy(function() {
                        this.query != b.target.value && (this.query = b.target.value, a.each(a("li", this.$ul), a.proxy(function(b, c) {
                            var d = a("input", c).val();
                            if (d != this.options.selectAllValue) {
                                var e = a("label", c).text(),
                                    d = a("input", c).val();
                                if (d && e && d != this.options.selectAllValue) {
                                    var f = !1,
                                        g = "";
                                    ("text" == this.options.filterBehavior || "both" == this.options.filterBehavior) && (g = e), ("value" == this.options.filterBehavior || "both" == this.options.filterBehavior) && (g = d), this.options.enableCaseInsensitiveFiltering && g.toLowerCase().indexOf(this.query.toLowerCase()) > -1 ? f = !0 : g.indexOf(this.query) > -1 && (f = !0), f ? a(c).show() : a(c).hide()
                                }
                            }
                        }, this)))
                    }, this), 300, this)
                }, this)))
            }
        },
        "destroy": function() {
            this.$container.remove(), this.$select.show()
        },
        "refresh": function() {
            a("option", this.$select).each(a.proxy(function(b, c) {
                var d = a("li input", this.$ul).filter(function() {
                    return a(this).val() == a(c).val()
                });
                a(c).is(":selected") ? (d.prop("checked", !0), this.options.selectedClass && d.parents("li").addClass(this.options.selectedClass)) : (d.prop("checked", !1), this.options.selectedClass && d.parents("li").removeClass(this.options.selectedClass)), a(c).is(":disabled") ? d.attr("disabled", "disabled").prop("disabled", !0).parents("li").addClass("disabled") : d.prop("disabled", !1).parents("li").removeClass("disabled")
            }, this)), this.updateButtonText()
        },
        "select": function(b) {
            b && !a.isArray(b) && (b = [b]);
            for (var c = 0; c < b.length; c++) {
                var d = b[c],
                    e = this.getOptionByValue(d),
                    f = this.getInputByValue(d);
                this.options.selectedClass && f.parents("li").addClass(this.options.selectedClass), f.prop("checked", !0), e.prop("selected", !0), this.options.onChange(e, !0)
            }
            this.updateButtonText()
        },
        "deselect": function(b) {
            b && !a.isArray(b) && (b = [b]);
            for (var c = 0; c < b.length; c++) {
                var d = b[c],
                    e = this.getOptionByValue(d),
                    f = this.getInputByValue(d);
                this.options.selectedClass && f.parents("li").removeClass(this.options.selectedClass), f.prop("checked", !1), e.prop("selected", !1), this.options.onChange(e, !1)
            }
            this.updateButtonText()
        },
        "rebuild": function() {
            this.$ul.html(""), a('option[value="' + this.options.selectAllValue + '"]', this.$select).remove(), this.options.multiple = "multiple" == this.$select.attr("multiple"), this.buildSelectAll(), this.buildDropdownOptions(), this.updateButtonText(), this.buildFilter()
        },
        "dataprovider": function(a) {
            var b = "";
            a.forEach(function(a) {
                b += '<option value="' + a.value + '">' + a.label + "</option>"
            }), this.$select.html(b), this.rebuild()
        },
        "enable": function() {
            this.$select.prop("disabled", !1), this.$button.prop("disabled", !1).removeClass("disabled")
        },
        "disable": function() {
            this.$select.prop("disabled", !0), this.$button.prop("disabled", !0).addClass("disabled")
        },
        "setOptions": function(a) {
            this.options = this.mergeOptions(a)
        },
        "mergeOptions": function(b) {
            return a.extend({}, this.defaults, b)
        },
        "updateButtonText": function() {
            var b = this.getSelected();
            a("button", this.$container).html(this.options.buttonText(b, this.$select)), a("button", this.$container).attr("title", this.options.buttonTitle(b, this.$select))
        },
        "getSelected": function() {
            return a('option[value!="' + this.options.selectAllValue + '"]:selected', this.$select).filter(function() {
                return a(this).prop("selected")
            })
        },
        "getOptionByValue": function(b) {
            return a("option", this.$select).filter(function() {
                return a(this).val() == b
            })
        },
        "getInputByValue": function(b) {
            return a("li input", this.$ul).filter(function() {
                return a(this).val() == b
            })
        },
        "updateOriginalOptions": function() {
            this.originalOptions = this.$select.clone()[0].options
        },
        "asyncFunction": function(a, b, c) {
            var d = Array.prototype.slice.call(arguments, 3);
            return setTimeout(function() {
                a.apply(c || window, d)
            }, b)
        }
    }, a.fn.multiselect = function(c, d) {
        return this.each(function() {
            var e = a(this).data("multiselect"),
                f = "object" == typeof c && c;
            e || a(this).data("multiselect", e = new b(this, f)), "string" == typeof c && e[c](d)
        })
    }, a.fn.multiselect.Constructor = b, a(function() {
        a("select[data-role=multiselect]").multiselect()
    })
}(window.jQuery);