/*! SmartAdmin - v1.5 - 2014-09-27 */
var toMarkdown = function(a) {
    function b(a, b) {
        var c = "void" === b.type ? "<" + b.tag + "\\b([^>]*)\\/?>" : "<" + b.tag + "\\b([^>]*)>([\\s\\S]*?)<\\/" + b.tag + ">",
            d = new RegExp(c, "gi"),
            e = "";
        return e = "string" == typeof b.replacement ? a.replace(d, b.replacement) : a.replace(d, function(a, c, d, e) {
            return b.replacement.call(this, a, c, d, e)
        })
    }

    function c(a) {
        return new RegExp(a + "\\s*=\\s*[\"']?([^\"']*)[\"']?", "i")
    }

    function d(a) {
        return a = a.replace(/<(ul|ol)\b[^>]*>([\s\S]*?)<\/\1>/gi, function(a, b, c) {
            var d = c.split("</li>");
            for (d.splice(d.length - 1, 1), h = 0, i = d.length; i > h; h++)
                if (d[h]) {
                    var e = "ol" === b ? h + 1 + ".  " : "*   ";
                    d[h] = d[h].replace(/\s*<li[^>]*>([\s\S]*)/i, function(a, b) {
                        return b = b.replace(/^\s+/, ""), b = b.replace(/\n\n/g, "\n\n    "), b = b.replace(/\n([ ]*)+(\*|\d+\.) /g, "\n$1    $2 "), e + b
                    })
                }
            return d.join("\n")
        }), "\n\n" + a.replace(/[ \t]+\n|\s+$/g, "")
    }

    function e(a) {
        return a = a.replace(/<blockquote\b[^>]*>([\s\S]*?)<\/blockquote>/gi, function(a, b) {
            return b = b.replace(/^\s+|\s+$/g, ""), b = f(b), b = b.replace(/^/gm, "> "), b = b.replace(/^(>([ \t]{2,}>)+)/gm, "> >")
        })
    }

    function f(a) {
        return a = a.replace(/^[\t\r\n]+|[\t\r\n]+$/g, ""), a = a.replace(/\n\s+\n/g, "\n\n"), a = a.replace(/\n{3,}/g, "\n\n")
    }
    for (var g = [{
            "patterns": "p",
            "replacement": function(a, b, c) {
                return c ? "\n\n" + c + "\n" : ""
            }
        }, {
            "patterns": "br",
            "type": "void",
            "replacement": "\n"
        }, {
            "patterns": "h([1-6])",
            "replacement": function(a, b, c, d) {
                for (var e = "", f = 0; b > f; f++) e += "#";
                return "\n\n" + e + " " + d + "\n"
            }
        }, {
            "patterns": "hr",
            "type": "void",
            "replacement": "\n\n* * *\n"
        }, {
            "patterns": "a",
            "replacement": function(a, b, d) {
                var e = b.match(c("href")),
                    f = b.match(c("title"));
                return e ? "[" + d + "](" + e[1] + (f && f[1] ? ' "' + f[1] + '"' : "") + ")" : a
            }
        }, {
            "patterns": ["b", "strong"],
            "replacement": function(a, b, c) {
                return c ? "**" + c + "**" : ""
            }
        }, {
            "patterns": ["i", "em"],
            "replacement": function(a, b, c) {
                return c ? "_" + c + "_" : ""
            }
        }, {
            "patterns": "code",
            "replacement": function(a, b, c) {
                return c ? "`" + c + "`" : ""
            }
        }, {
            "patterns": "img",
            "type": "void",
            "replacement": function(a, b) {
                var d = b.match(c("src")),
                    e = b.match(c("alt")),
                    f = b.match(c("title"));
                return "![" + (e && e[1] ? e[1] : "") + "](" + d[1] + (f && f[1] ? ' "' + f[1] + '"' : "") + ")"
            }
        }], h = 0, i = g.length; i > h; h++)
        if ("string" == typeof g[h].patterns) a = b(a, {
            "tag": g[h].patterns,
            "replacement": g[h].replacement,
            "type": g[h].type
        });
        else
            for (var j = 0, k = g[h].patterns.length; k > j; j++) a = b(a, {
                "tag": g[h].patterns[j],
                "replacement": g[h].replacement,
                "type": g[h].type
            });
    a = a.replace(/<pre\b[^>]*>`([\s\S]*)`<\/pre>/gi, function(a, b) {
        return b = b.replace(/^\t+/g, "  "), b = b.replace(/\n/g, "\n    "), "\n\n    " + b + "\n"
    }), a = a.replace(/^(\s{0,3}\d+)\. /g, "$1\\. ");
    for (var l = /<(ul|ol)\b[^>]*>(?:(?!<ul|<ol)[\s\S])*?<\/\1>/gi; a.match(l);) a = a.replace(l, function(a) {
        return d(a)
    });
    for (var m = /<blockquote\b[^>]*>((?:(?!<blockquote)[\s\S])*?)<\/blockquote>/gi; a.match(m);) a = a.replace(m, function(a) {
        return e(a)
    });
    return f(a)
};
"object" == typeof exports && (exports.toMarkdown = toMarkdown);