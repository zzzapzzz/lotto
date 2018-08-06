/*! SmartAdmin - v1.5 - 2014-09-27 */ ! function(a) {
    "use strict";
    var b = function(b, c) {
        this.$ns = "bootstrap-markdown", this.$element = a(b), this.$editable = {
            "el": null,
            "type": null,
            "attrKeys": [],
            "attrValues": [],
            "content": null
        }, this.$options = a.extend(!0, {}, a.fn.markdown.defaults, c), this.$oldContent = null, this.$isPreview = !1, this.$editor = null, this.$textarea = null, this.$handler = [], this.$callback = [], this.$nextTab = [], this.showEditor()
    };
    b.prototype = {
        "constructor": b,
        "__alterButtons": function(b, c) {
            var d = this.$handler,
                e = "all" == b,
                f = this;
            a.each(d, function(a, d) {
                var g = !0;
                g = e ? !1 : d.indexOf(b) < 0, 0 == g && c(f.$editor.find('button[data-handler="' + d + '"]'))
            })
        },
        "__buildButtons": function(b, c) {
            var d, e = this.$ns,
                f = this.$handler,
                g = this.$callback;
            for (d = 0; d < b.length; d++) {
                var h, i = b[d];
                for (h = 0; h < i.length; h++) {
                    var j, k = i[h].data,
                        l = a("<div/>", {
                            "class": "btn-group"
                        });
                    for (j = 0; j < k.length; j++) {
                        var m = k[j],
                            n = "",
                            o = e + "-" + m.name,
                            p = m.btnText ? m.btnText : "",
                            q = m.btnClass ? m.btnClass : "btn",
                            r = m.tabIndex ? m.tabIndex : "-1";
                        1 == m.toggle && (n = ' data-toggle="button"'), l.append('<button class="' + q + ' btn-default btn-sm" title="' + m.title + '" tabindex="' + r + '" data-provider="' + e + '" data-handler="' + o + '"' + n + '><span class="' + m.icon + '"></span> ' + p + "</button>"), f.push(o), g.push(m.callback)
                    }
                    c.append(l)
                }
            }
            return c
        },
        "__setListener": function() {
            var b = "undefined" != typeof this.$textarea.attr("rows"),
                c = this.$textarea.val().split("\n").length > 5 ? this.$textarea.val().split("\n").length : "5",
                d = b ? this.$textarea.attr("rows") : c;
            this.$textarea.attr("rows", d), this.$textarea.css("resize", "none"), this.$textarea.on("focus", a.proxy(this.focus, this)).on("keypress", a.proxy(this.keypress, this)).on("keyup", a.proxy(this.keyup, this)), this.eventSupported("keydown") && this.$textarea.on("keydown", a.proxy(this.keydown, this)), this.$textarea.data("markdown", this)
        },
        "__handle": function(b) {
            var c = a(b.currentTarget),
                d = this.$handler,
                e = this.$callback,
                f = c.attr("data-handler"),
                g = d.indexOf(f),
                h = e[g];
            a(b.currentTarget).focus(), h(this), f.indexOf("cmdSave") < 0 && this.$textarea.focus(), b.preventDefault()
        },
        "showEditor": function() {
            var b, c = this,
                d = this.$ns,
                e = this.$element,
                f = (e.css("height"), e.css("width"), this.$editable),
                g = this.$handler,
                h = this.$callback,
                i = this.$options,
                j = a("<div/>", {
                    "class": "md-editor",
                    "click": function() {
                        c.focus()
                    }
                });
            if (null == this.$editor) {
                var k = a("<div/>", {
                    "class": "md-header btn-toolbar"
                });
                if (i.buttons.length > 0 && (k = this.__buildButtons(i.buttons, k)), i.additionalButtons.length > 0 && (k = this.__buildButtons(i.additionalButtons, k)), j.append(k), e.is("textarea")) e.before(j), b = e, b.addClass("md-input"), j.append(b);
                else {
                    var l = "function" == typeof toMarkdown ? toMarkdown(e.html()) : e.html(),
                        m = a.trim(l);
                    b = a("<textarea/>", {
                        "class": "md-input",
                        "val": m
                    }), j.append(b), f.el = e, f.type = e.prop("tagName").toLowerCase(), f.content = e.html(), a(e[0].attributes).each(function() {
                        f.attrKeys.push(this.nodeName), f.attrValues.push(this.nodeValue)
                    }), e.replaceWith(j)
                }
                if (i.savable) {
                    var n = a("<div/>", {
                            "class": "md-footer"
                        }),
                        o = "cmdSave";
                    g.push(o), h.push(i.onSave), n.append('<button class="btn btn-success" data-provider="' + d + '" data-handler="' + o + '"><i class="fa fa-check"></i> Save</button>'), j.append(n)
                }
                a.each(["height", "width"], function(a, b) {
                    "inherit" != i[b] && (jQuery.isNumeric(i[b]) ? j.css(b, i[b] + "px") : j.addClass(i[b]))
                }), this.$editor = j, this.$textarea = b, this.$editable = f, this.$oldContent = this.getContent(), this.__setListener(), this.$editor.attr("id", (new Date).getTime()), this.$editor.on("click", '[data-provider="bootstrap-markdown"]', a.proxy(this.__handle, this))
            } else this.$editor.show();
            return i.autofocus && (this.$textarea.focus(), this.$editor.addClass("active")), i.onShow(this), this
        },
        "showPreview": function() {
            var b, c = this.$options,
                d = c.onPreview(this),
                e = this.$textarea,
                f = e.next(),
                g = a("<div/>", {
                    "class": "md-preview",
                    "data-provider": "markdown-preview"
                });
            return this.$isPreview = !0, this.disableButtons("all").enableButtons("cmdPreview"), b = "string" == typeof d ? d : "object" == typeof markdown ? markdown.toHTML(e.val()) : e.val(), g.html(b), f && "md-footer" == f.attr("class") ? g.insertBefore(f) : e.parent().append(g), e.hide(), g.data("markdown", this), this
        },
        "hidePreview": function() {
            this.$isPreview = !1;
            var a = this.$editor.find('div[data-provider="markdown-preview"]');
            return a.remove(), this.enableButtons("all"), this.$textarea.show(), this.__setListener(), this
        },
        "isDirty": function() {
            return this.$oldContent != this.getContent()
        },
        "getContent": function() {
            return this.$textarea.val()
        },
        "setContent": function(a) {
            return this.$textarea.val(a), this
        },
        "findSelection": function(a) {
            var b, c = this.getContent();
            if (b = c.indexOf(a), b >= 0 && a.length > 0) {
                var d, e = this.getSelection();
                return this.setSelection(b, b + a.length), d = this.getSelection(), this.setSelection(e.start, e.end), d
            }
            return null
        },
        "getSelection": function() {
            var a = this.$textarea[0];
            return ("selectionStart" in a && function() {
                var b = a.selectionEnd - a.selectionStart;
                return {
                    "start": a.selectionStart,
                    "end": a.selectionEnd,
                    "length": b,
                    "text": a.value.substr(a.selectionStart, b)
                }
            } || function() {
                return null
            })()
        },
        "setSelection": function(a, b) {
            var c = this.$textarea[0];
            return ("selectionStart" in c && function() {
                c.selectionStart = a, c.selectionEnd = b
            } || function() {
                return null
            })()
        },
        "replaceSelection": function(a) {
            var b = this.$textarea[0];
            return ("selectionStart" in b && function() {
                return b.value = b.value.substr(0, b.selectionStart) + a + b.value.substr(b.selectionEnd, b.value.length), b.selectionStart = b.value.length, this
            } || function() {
                return b.value += a, jQuery(b)
            })()
        },
        "getNextTab": function() {
            if (0 == this.$nextTab.length) return null;
            var a, b = this.$nextTab.shift();
            return "function" == typeof b ? a = b() : "object" == typeof b && b.length > 0 && (a = b), a
        },
        "setNextTab": function(a, b) {
            if ("string" == typeof a) {
                var c = this;
                this.$nextTab.push(function() {
                    return c.findSelection(a)
                })
            } else if ("numeric" == typeof a && "numeric" == typeof b) {
                var d = this.getSelection();
                this.setSelection(a, b), this.$nextTab.push(this.getSelection()), this.setSelection(d.start, d.end)
            }
        },
        "enableButtons": function(a) {
            var b = function(a) {
                a.removeAttr("disabled")
            };
            return this.__alterButtons(a, b), this
        },
        "disableButtons": function(a) {
            var b = function(a) {
                a.attr("disabled", "disabled")
            };
            return this.__alterButtons(a, b), this
        },
        "eventSupported": function(a) {
            var b = a in this.$element;
            return b || (this.$element.setAttribute(a, "return;"), b = "function" == typeof this.$element[a]), b
        },
        "keydown": function(b) {
            this.suppressKeyPressRepeat = ~a.inArray(b.keyCode, [40, 38, 9, 13, 27]), this.keyup(b)
        },
        "keypress": function(a) {
            this.suppressKeyPressRepeat || this.keyup(a)
        },
        "keyup": function(a) {
            var b = !1;
            switch (a.keyCode) {
                case 40:
                case 38:
                case 16:
                case 17:
                case 18:
                    break;
                case 9:
                    var c;
                    if (c = this.getNextTab(), null != c) {
                        var d = this;
                        setTimeout(function() {
                            d.setSelection(c.start, c.end)
                        }, 500), b = !0
                    } else {
                        var e = this.getSelection();
                        e.start == e.end && e.end == this.getContent().length ? b = !1 : (this.setSelection(this.getContent().length, this.getContent().length), b = !0)
                    }
                    break;
                case 13:
                case 27:
                    b = !1;
                    break;
                default:
                    b = !1
            }
            b && (a.stopPropagation(), a.preventDefault())
        },
        "focus": function() {
            var b = this.$options,
                c = (b.hideable, this.$editor);
            return c.addClass("active"), a(document).find(".md-editor").each(function() {
                if (a(this).attr("id") != c.attr("id")) {
                    var b;
                    b = a(this).find("textarea").data("markdown"), null == b && (b = a(this).find('div[data-provider="markdown-preview"]').data("markdown")), b && b.blur()
                }
            }), this
        },
        "blur": function() {
            var b = this.$options,
                c = b.hideable,
                d = this.$editor,
                e = this.$editable;
            if (d.hasClass("active") || 0 == this.$element.parent().length) {
                if (d.removeClass("active"), c)
                    if (null != e.el) {
                        var f = a("<" + e.type + "/>"),
                            g = this.getContent(),
                            h = "object" == typeof markdown ? markdown.toHTML(g) : g;
                        a(e.attrKeys).each(function(a) {
                            f.attr(e.attrKeys[a], e.attrValues[a])
                        }), f.html(h), d.replaceWith(f)
                    } else d.hide();
                b.onBlur(this)
            }
            return this
        }
    };
    var c = a.fn.markdown;
    a.fn.markdown = function(c) {
        return this.each(function() {
            var d = a(this),
                e = d.data("markdown"),
                f = "object" == typeof c && c;
            e || d.data("markdown", e = new b(this, f))
        })
    }, a.fn.markdown.defaults = {
        "autofocus": !1,
        "hideable": !1,
        "savable": !1,
        "width": "inherit",
        "height": "inherit",
        "buttons": [
            [{
                "name": "groupFont",
                "data": [{
                    "name": "cmdBold",
                    "title": "Bold",
                    "icon": "glyphicon glyphicon-bold",
                    "callback": function(a) {
                        var b, c, d = a.getSelection(),
                            e = a.getContent();
                        b = 0 == d.length ? "strong text" : d.text, "**" == e.substr(d.start - 2, 2) && "**" == e.substr(d.end, 2) ? (a.setSelection(d.start - 2, d.end + 2), a.replaceSelection(b), c = d.start - 2) : (a.replaceSelection("**" + b + "**"), c = d.start + 2), a.setSelection(c, c + b.length)
                    }
                }, {
                    "name": "cmdItalic",
                    "title": "Italic",
                    "icon": "glyphicon glyphicon-italic",
                    "callback": function(a) {
                        var b, c, d = a.getSelection(),
                            e = a.getContent();
                        b = 0 == d.length ? "emphasized text" : d.text, "*" == e.substr(d.start - 1, 1) && "*" == e.substr(d.end, 1) ? (a.setSelection(d.start - 1, d.end + 1), a.replaceSelection(b), c = d.start - 1) : (a.replaceSelection("*" + b + "*"), c = d.start + 1), a.setSelection(c, c + b.length)
                    }
                }, {
                    "name": "cmdHeading",
                    "title": "Heading",
                    "icon": "glyphicon glyphicon-font",
                    "callback": function(a) {
                        var b, c, d, e, f = a.getSelection(),
                            g = a.getContent();
                        b = 0 == f.length ? "heading text" : f.text, d = 4, "### " == g.substr(f.start - d, d) || (d = 3, "###" == g.substr(f.start - d, d)) ? (a.setSelection(f.start - d, f.end), a.replaceSelection(b), c = f.start - d) : (e = g.substr(f.start - 1, 1), e && "\n" != e ? (a.replaceSelection("\n\n### " + b + "\n"), c = f.start + 6) : (a.replaceSelection("### " + b + "\n"), c = f.start + 4)), a.setSelection(c, c + b.length)
                    }
                }]
            }, {
                "name": "groupLink",
                "data": [{
                    "name": "cmdUrl",
                    "title": "URL/Link",
                    "icon": "glyphicon glyphicon-globe",
                    "callback": function(a) {
                        {
                            var b, c, d, e = a.getSelection();
                            a.getContent()
                        }
                        b = 0 == e.length ? "enter link description here" : e.text, d = prompt("Insert Hyperlink", "http://"), null != d && (a.replaceSelection("[" + b + "](" + d + ")"), c = e.start + 1, a.setSelection(c, c + b.length))
                    }
                }, {
                    "name": "cmdImage",
                    "title": "Image",
                    "icon": "glyphicon glyphicon-picture",
                    "callback": function(a) {
                        {
                            var b, c, d, e = a.getSelection();
                            a.getContent()
                        }
                        b = 0 == e.length ? "enter image description here" : e.text, d = prompt("Insert Image Hyperlink", "http://"), null != d && (a.replaceSelection("![" + b + "](" + d + ' "enter image title here")'), c = e.start + 2, a.setNextTab("enter image title here"), a.setSelection(c, c + b.length))
                    }
                }]
            }, {
                "name": "groupMisc",
                "data": [{
                    "name": "cmdList",
                    "title": "List",
                    "icon": "glyphicon glyphicon-list",
                    "callback": function(b) {
                        {
                            var c, d, e = b.getSelection();
                            b.getContent()
                        }
                        if (0 == e.length) c = "list text here", b.replaceSelection("- " + c), d = e.start + 2;
                        else if (e.text.indexOf("\n") < 0) c = e.text, b.replaceSelection("- " + c), d = e.start + 2;
                        else {
                            var f = [];
                            f = e.text.split("\n"), c = f[0], a.each(f, function(a, b) {
                                f[a] = "- " + b
                            }), b.replaceSelection("\n\n" + f.join("\n")), d = e.start + 4
                        }
                        b.setSelection(d, d + c.length)
                    }
                }]
            }, {
                "name": "groupUtil",
                "data": [{
                    "name": "cmdPreview",
                    "toggle": !0,
                    "title": "Preview",
                    "btnText": "Preview",
                    "btnClass": "btn btn-primary btn-sm",
                    "icon": "glyphicon glyphicon-search",
                    "callback": function(a) {
                        var b = a.$isPreview;
                        0 == b ? a.showPreview() : a.hidePreview()
                    }
                }]
            }]
        ],
        "additionalButtons": [],
        "onShow": function() {},
        "onPreview": function() {},
        "onSave": function() {},
        "onBlur": function() {}
    }, a.fn.markdown.Constructor = b, a.fn.markdown.noConflict = function() {
        return a.fn.markdown = c, this
    };
    var d = function(a) {
            var b = a;
            return b.data("markdown") ? void b.data("markdown").showEditor() : void b.markdown(b.data())
        },
        e = function(b) {
            var c, d = !1,
                e = a(b.currentTarget);
            "focusin" != b.type && "click" != b.type || 1 != e.length || "object" != typeof e[0] || (c = e[0].activeElement, a(c).data("markdown") || ("undefined" == typeof a(c).parent().parent().parent().attr("class") || a(c).parent().parent().parent().attr("class").indexOf("md-editor") < 0 ? ("undefined" == typeof a(c).parent().parent().attr("class") || a(c).parent().parent().attr("class").indexOf("md-editor") < 0) && (d = !0) : d = !1), d && a(document).find(".md-editor").each(function() {
                var b = a(c).parent();
                if (a(this).attr("id") != b.attr("id")) {
                    var d;
                    d = a(this).find("textarea").data("markdown"), null == d && (d = a(this).find('div[data-provider="markdown-preview"]').data("markdown")), d && d.blur()
                }
            }), b.stopPropagation())
        };
    a(document).on("click.markdown.data-api", '[data-provide="markdown-editable"]', function(b) {
        d(a(this)), b.preventDefault()
    }).on("click", function(a) {
        e(a)
    }).on("focusin", function(a) {
        e(a)
    }).ready(function() {
        a('textarea[data-provide="markdown"]').each(function() {
            d(a(this))
        })
    })
}(window.jQuery);