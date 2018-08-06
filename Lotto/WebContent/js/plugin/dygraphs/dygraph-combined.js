/*! SmartAdmin - v1.5 - 2014-09-27 */
function RGBColorParser(a) {
    this.ok = !1, "#" == a.charAt(0) && (a = a.substr(1, 6)), a = a.replace(/ /g, ""), a = a.toLowerCase();
    var b = {
        "aliceblue": "f0f8ff",
        "antiquewhite": "faebd7",
        "aqua": "00ffff",
        "aquamarine": "7fffd4",
        "azure": "f0ffff",
        "beige": "f5f5dc",
        "bisque": "ffe4c4",
        "black": "000000",
        "blanchedalmond": "ffebcd",
        "blue": "0000ff",
        "blueviolet": "8a2be2",
        "brown": "a52a2a",
        "burlywood": "deb887",
        "cadetblue": "5f9ea0",
        "chartreuse": "7fff00",
        "chocolate": "d2691e",
        "coral": "ff7f50",
        "cornflowerblue": "6495ed",
        "cornsilk": "fff8dc",
        "crimson": "dc143c",
        "cyan": "00ffff",
        "darkblue": "00008b",
        "darkcyan": "008b8b",
        "darkgoldenrod": "b8860b",
        "darkgray": "a9a9a9",
        "darkgreen": "006400",
        "darkkhaki": "bdb76b",
        "darkmagenta": "8b008b",
        "darkolivegreen": "556b2f",
        "darkorange": "ff8c00",
        "darkorchid": "9932cc",
        "darkred": "8b0000",
        "darksalmon": "e9967a",
        "darkseagreen": "8fbc8f",
        "darkslateblue": "483d8b",
        "darkslategray": "2f4f4f",
        "darkturquoise": "00ced1",
        "darkviolet": "9400d3",
        "deeppink": "ff1493",
        "deepskyblue": "00bfff",
        "dimgray": "696969",
        "dodgerblue": "1e90ff",
        "feldspar": "d19275",
        "firebrick": "b22222",
        "floralwhite": "fffaf0",
        "forestgreen": "228b22",
        "fuchsia": "ff00ff",
        "gainsboro": "dcdcdc",
        "ghostwhite": "f8f8ff",
        "gold": "ffd700",
        "goldenrod": "daa520",
        "gray": "808080",
        "green": "008000",
        "greenyellow": "adff2f",
        "honeydew": "f0fff0",
        "hotpink": "ff69b4",
        "indianred": "cd5c5c",
        "indigo": "4b0082",
        "ivory": "fffff0",
        "khaki": "f0e68c",
        "lavender": "e6e6fa",
        "lavenderblush": "fff0f5",
        "lawngreen": "7cfc00",
        "lemonchiffon": "fffacd",
        "lightblue": "add8e6",
        "lightcoral": "f08080",
        "lightcyan": "e0ffff",
        "lightgoldenrodyellow": "fafad2",
        "lightgrey": "d3d3d3",
        "lightgreen": "90ee90",
        "lightpink": "ffb6c1",
        "lightsalmon": "ffa07a",
        "lightseagreen": "20b2aa",
        "lightskyblue": "87cefa",
        "lightslateblue": "8470ff",
        "lightslategray": "778899",
        "lightsteelblue": "b0c4de",
        "lightyellow": "ffffe0",
        "lime": "00ff00",
        "limegreen": "32cd32",
        "linen": "faf0e6",
        "magenta": "ff00ff",
        "maroon": "800000",
        "mediumaquamarine": "66cdaa",
        "mediumblue": "0000cd",
        "mediumorchid": "ba55d3",
        "mediumpurple": "9370d8",
        "mediumseagreen": "3cb371",
        "mediumslateblue": "7b68ee",
        "mediumspringgreen": "00fa9a",
        "mediumturquoise": "48d1cc",
        "mediumvioletred": "c71585",
        "midnightblue": "191970",
        "mintcream": "f5fffa",
        "mistyrose": "ffe4e1",
        "moccasin": "ffe4b5",
        "navajowhite": "ffdead",
        "navy": "000080",
        "oldlace": "fdf5e6",
        "olive": "808000",
        "olivedrab": "6b8e23",
        "orange": "ffa500",
        "orangered": "ff4500",
        "orchid": "da70d6",
        "palegoldenrod": "eee8aa",
        "palegreen": "98fb98",
        "paleturquoise": "afeeee",
        "palevioletred": "d87093",
        "papayawhip": "ffefd5",
        "peachpuff": "ffdab9",
        "peru": "cd853f",
        "pink": "ffc0cb",
        "plum": "dda0dd",
        "powderblue": "b0e0e6",
        "purple": "800080",
        "red": "ff0000",
        "rosybrown": "bc8f8f",
        "royalblue": "4169e1",
        "saddlebrown": "8b4513",
        "salmon": "fa8072",
        "sandybrown": "f4a460",
        "seagreen": "2e8b57",
        "seashell": "fff5ee",
        "sienna": "a0522d",
        "silver": "c0c0c0",
        "skyblue": "87ceeb",
        "slateblue": "6a5acd",
        "slategray": "708090",
        "snow": "fffafa",
        "springgreen": "00ff7f",
        "steelblue": "4682b4",
        "tan": "d2b48c",
        "teal": "008080",
        "thistle": "d8bfd8",
        "tomato": "ff6347",
        "turquoise": "40e0d0",
        "violet": "ee82ee",
        "violetred": "d02090",
        "wheat": "f5deb3",
        "white": "ffffff",
        "whitesmoke": "f5f5f5",
        "yellow": "ffff00",
        "yellowgreen": "9acd32"
    };
    for (var c in b) a == c && (a = b[c]);
    for (var d = [{
            "re": /^rgb\((\d{1,3}),\s*(\d{1,3}),\s*(\d{1,3})\)$/,
            "example": ["rgb(123, 234, 45)", "rgb(255,234,245)"],
            "process": function(a) {
                return [parseInt(a[1]), parseInt(a[2]), parseInt(a[3])]
            }
        }, {
            "re": /^(\w{2})(\w{2})(\w{2})$/,
            "example": ["#00ff00", "336699"],
            "process": function(a) {
                return [parseInt(a[1], 16), parseInt(a[2], 16), parseInt(a[3], 16)]
            }
        }, {
            "re": /^(\w{1})(\w{1})(\w{1})$/,
            "example": ["#fb0", "f0f"],
            "process": function(a) {
                return [parseInt(a[1] + a[1], 16), parseInt(a[2] + a[2], 16), parseInt(a[3] + a[3], 16)]
            }
        }], e = 0; e < d.length; e++) {
        var f = d[e].re,
            g = d[e].process,
            h = f.exec(a);
        if (h) {
            var i = g(h);
            this.r = i[0], this.g = i[1], this.b = i[2], this.ok = !0
        }
    }
    this.r = this.r < 0 || isNaN(this.r) ? 0 : this.r > 255 ? 255 : this.r, this.g = this.g < 0 || isNaN(this.g) ? 0 : this.g > 255 ? 255 : this.g, this.b = this.b < 0 || isNaN(this.b) ? 0 : this.b > 255 ? 255 : this.b, this.toRGB = function() {
        return "rgb(" + this.r + ", " + this.g + ", " + this.b + ")"
    }, this.toHex = function() {
        var a = this.r.toString(16),
            b = this.g.toString(16),
            c = this.b.toString(16);
        return 1 == a.length && (a = "0" + a), 1 == b.length && (b = "0" + b), 1 == c.length && (c = "0" + c), "#" + a + b + c
    }
}

function printStackTrace(a) {
    a = a || {
        "guess": !0
    };
    var b = a.e || null,
        c = !!a.guess,
        d = new printStackTrace.implementation,
        e = d.run(b);
    return c ? d.guessAnonymousFunctions(e) : e
}
Date.ext = {}, Date.ext.util = {}, Date.ext.util.xPad = function(a, b, c) {
    for ("undefined" == typeof c && (c = 10); parseInt(a, 10) < c && c > 1; c /= 10) a = b.toString() + a;
    return a.toString()
}, Date.prototype.locale = "en-GB", document.getElementsByTagName("html") && document.getElementsByTagName("html")[0].lang && (Date.prototype.locale = document.getElementsByTagName("html")[0].lang), Date.ext.locales = {}, Date.ext.locales.en = {
    "a": ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
    "A": ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
    "b": ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
    "B": ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
    "c": "%a %d %b %Y %T %Z",
    "p": ["AM", "PM"],
    "P": ["am", "pm"],
    "x": "%d/%m/%y",
    "X": "%T"
}, Date.ext.locales["en-US"] = Date.ext.locales.en, Date.ext.locales["en-US"].c = "%a %d %b %Y %r %Z", Date.ext.locales["en-US"].x = "%D", Date.ext.locales["en-US"].X = "%r", Date.ext.locales["en-GB"] = Date.ext.locales.en, Date.ext.locales["en-AU"] = Date.ext.locales["en-GB"], Date.ext.formats = {
    "a": function(a) {
        return Date.ext.locales[a.locale].a[a.getDay()]
    },
    "A": function(a) {
        return Date.ext.locales[a.locale].A[a.getDay()]
    },
    "b": function(a) {
        return Date.ext.locales[a.locale].b[a.getMonth()]
    },
    "B": function(a) {
        return Date.ext.locales[a.locale].B[a.getMonth()]
    },
    "c": "toLocaleString",
    "C": function(a) {
        return Date.ext.util.xPad(parseInt(a.getFullYear() / 100, 10), 0)
    },
    "d": ["getDate", "0"],
    "e": ["getDate", " "],
    "g": function(a) {
        return Date.ext.util.xPad(parseInt(Date.ext.util.G(a) / 100, 10), 0)
    },
    "G": function(a) {
        var b = a.getFullYear(),
            c = parseInt(Date.ext.formats.V(a), 10),
            d = parseInt(Date.ext.formats.W(a), 10);
        return d > c ? b++ : 0 === d && c >= 52 && b--, b
    },
    "H": ["getHours", "0"],
    "I": function(a) {
        var b = a.getHours() % 12;
        return Date.ext.util.xPad(0 === b ? 12 : b, 0)
    },
    "j": function(a) {
        var b = a - new Date("" + a.getFullYear() + "/1/1 GMT");
        b += 6e4 * a.getTimezoneOffset();
        var c = parseInt(b / 6e4 / 60 / 24, 10) + 1;
        return Date.ext.util.xPad(c, 0, 100)
    },
    "m": function(a) {
        return Date.ext.util.xPad(a.getMonth() + 1, 0)
    },
    "M": ["getMinutes", "0"],
    "p": function(a) {
        return Date.ext.locales[a.locale].p[a.getHours() >= 12 ? 1 : 0]
    },
    "P": function(a) {
        return Date.ext.locales[a.locale].P[a.getHours() >= 12 ? 1 : 0]
    },
    "S": ["getSeconds", "0"],
    "u": function(a) {
        var b = a.getDay();
        return 0 === b ? 7 : b
    },
    "U": function(a) {
        var b = parseInt(Date.ext.formats.j(a), 10),
            c = 6 - a.getDay(),
            d = parseInt((b + c) / 7, 10);
        return Date.ext.util.xPad(d, 0)
    },
    "V": function(a) {
        var b = parseInt(Date.ext.formats.W(a), 10),
            c = new Date("" + a.getFullYear() + "/1/1").getDay(),
            d = b + (c > 4 || 1 >= c ? 0 : 1);
        return 53 == d && new Date("" + a.getFullYear() + "/12/31").getDay() < 4 ? d = 1 : 0 === d && (d = Date.ext.formats.V(new Date("" + (a.getFullYear() - 1) + "/12/31"))), Date.ext.util.xPad(d, 0)
    },
    "w": "getDay",
    "W": function(a) {
        var b = parseInt(Date.ext.formats.j(a), 10),
            c = 7 - Date.ext.formats.u(a),
            d = parseInt((b + c) / 7, 10);
        return Date.ext.util.xPad(d, 0, 10)
    },
    "y": function(a) {
        return Date.ext.util.xPad(a.getFullYear() % 100, 0)
    },
    "Y": "getFullYear",
    "z": function(a) {
        var b = a.getTimezoneOffset(),
            c = Date.ext.util.xPad(parseInt(Math.abs(b / 60), 10), 0),
            d = Date.ext.util.xPad(b % 60, 0);
        return (b > 0 ? "-" : "+") + c + d
    },
    "Z": function(a) {
        return a.toString().replace(/^.*\(([^)]+)\)$/, "$1")
    },
    "%": function() {
        return "%"
    }
}, Date.ext.aggregates = {
    "c": "locale",
    "D": "%m/%d/%y",
    "h": "%b",
    "n": "\n",
    "r": "%I:%M:%S %p",
    "R": "%H:%M",
    "t": "	",
    "T": "%H:%M:%S",
    "x": "locale",
    "X": "locale"
}, Date.ext.aggregates.z = Date.ext.formats.z(new Date), Date.ext.aggregates.Z = Date.ext.formats.Z(new Date), Date.ext.unsupported = {}, Date.prototype.strftime = function(a) {
    this.locale in Date.ext.locales || (this.locale = this.locale.replace(/-[a-zA-Z]+$/, "") in Date.ext.locales ? this.locale.replace(/-[a-zA-Z]+$/, "") : "en-GB");
    for (var b = this; a.match(/%[cDhnrRtTxXzZ]/);) a = a.replace(/%([cDhnrRtTxXzZ])/g, function(a, c) {
        var d = Date.ext.aggregates[c];
        return "locale" == d ? Date.ext.locales[b.locale][c] : d
    });
    var c = a.replace(/%([aAbBCdegGHIjmMpPSuUVwWyY%])/g, function(a, c) {
        var d = Date.ext.formats[c];
        return "string" == typeof d ? b[d]() : "function" == typeof d ? d.call(b, b) : "object" == typeof d && "string" == typeof d[0] ? Date.ext.util.xPad(b[d[0]](), d[1]) : c
    });
    return b = null, c
}, printStackTrace.implementation = function() {}, printStackTrace.implementation.prototype = {
    "run": function(a, b) {
        return a = a || this.createException(), b = b || this.mode(a), "other" === b ? this.other(arguments.callee) : this[b](a)
    },
    "createException": function() {
        try {
            this.undef()
        } catch (a) {
            return a
        }
    },
    "mode": function(a) {
        return a.arguments && a.stack ? "chrome" : "string" == typeof a.message && "undefined" != typeof window && window.opera ? a.stacktrace ? a.message.indexOf("\n") > -1 && a.message.split("\n").length > a.stacktrace.split("\n").length ? "opera9" : a.stack ? a.stacktrace.indexOf("called from line") < 0 ? "opera10b" : "opera11" : "opera10a" : "opera9" : a.stack ? "firefox" : "other"
    },
    "instrumentFunction": function(a, b, c) {
        a = a || window;
        var d = a[b];
        a[b] = function() {
            return c.call(this, printStackTrace().slice(4)), a[b]._instrumented.apply(this, arguments)
        }, a[b]._instrumented = d
    },
    "deinstrumentFunction": function(a, b) {
        a[b].constructor === Function && a[b]._instrumented && a[b]._instrumented.constructor === Function && (a[b] = a[b]._instrumented)
    },
    "chrome": function(a) {
        var b = (a.stack + "\n").replace(/^\S[^\(]+?[\n$]/gm, "").replace(/^\s+at\s+/gm, "").replace(/^([^\(]+?)([\n$])/gm, "{anonymous}()@$1$2").replace(/^Object.<anonymous>\s*\(([^\)]+)\)/gm, "{anonymous}()@$1").split("\n");
        return b.pop(), b
    },
    "firefox": function(a) {
        return a.stack.replace(/(?:\n@:0)?\s+$/m, "").replace(/^\(/gm, "{anonymous}(").split("\n")
    },
    "opera11": function(a) {
        for (var b = "{anonymous}", c = /^.*line (\d+), column (\d+)(?: in (.+))? in (\S+):$/, d = a.stacktrace.split("\n"), e = [], f = 0, g = d.length; g > f; f += 2) {
            var h = c.exec(d[f]);
            if (h) {
                var i = h[4] + ":" + h[1] + ":" + h[2],
                    j = h[3] || "global code";
                j = j.replace(/<anonymous function: (\S+)>/, "$1").replace(/<anonymous function>/, b), e.push(j + "@" + i + " -- " + d[f + 1].replace(/^\s+/, ""))
            }
        }
        return e
    },
    "opera10b": function(a) {
        for (var b = /^(.*)@(.+):(\d+)$/, c = a.stacktrace.split("\n"), d = [], e = 0, f = c.length; f > e; e++) {
            var g = b.exec(c[e]);
            if (g) {
                var h = g[1] ? g[1] + "()" : "global code";
                d.push(h + "@" + g[2] + ":" + g[3])
            }
        }
        return d
    },
    "opera10a": function(a) {
        for (var b = "{anonymous}", c = /Line (\d+).*script (?:in )?(\S+)(?:: In function (\S+))?$/i, d = a.stacktrace.split("\n"), e = [], f = 0, g = d.length; g > f; f += 2) {
            var h = c.exec(d[f]);
            if (h) {
                var i = h[3] || b;
                e.push(i + "()@" + h[2] + ":" + h[1] + " -- " + d[f + 1].replace(/^\s+/, ""))
            }
        }
        return e
    },
    "opera9": function(a) {
        for (var b = "{anonymous}", c = /Line (\d+).*script (?:in )?(\S+)/i, d = a.message.split("\n"), e = [], f = 2, g = d.length; g > f; f += 2) {
            var h = c.exec(d[f]);
            h && e.push(b + "()@" + h[2] + ":" + h[1] + " -- " + d[f + 1].replace(/^\s+/, ""))
        }
        return e
    },
    "other": function(a) {
        for (var b, c, d = "{anonymous}", e = /function\s*([\w\-$]+)?\s*\(/i, f = [], g = 10; a && f.length < g;) b = e.test(a.toString()) ? RegExp.$1 || d : d, c = Array.prototype.slice.call(a.arguments || []), f[f.length] = b + "(" + this.stringifyArguments(c) + ")", a = a.caller;
        return f
    },
    "stringifyArguments": function(a) {
        for (var b = [], c = Array.prototype.slice, d = 0; d < a.length; ++d) {
            var e = a[d];
            void 0 === e ? b[d] = "undefined" : null === e ? b[d] = "null" : e.constructor && (e.constructor === Array ? b[d] = e.length < 3 ? "[" + this.stringifyArguments(e) + "]" : "[" + this.stringifyArguments(c.call(e, 0, 1)) + "..." + this.stringifyArguments(c.call(e, -1)) + "]" : e.constructor === Object ? b[d] = "#object" : e.constructor === Function ? b[d] = "#function" : e.constructor === String ? b[d] = '"' + e + '"' : e.constructor === Number && (b[d] = e))
        }
        return b.join(",")
    },
    "sourceCache": {},
    "ajax": function(a) {
        var b = this.createXMLHTTPObject();
        if (b) try {
            return b.open("GET", a, !1), b.send(null), b.responseText
        } catch (c) {}
        return ""
    },
    "createXMLHTTPObject": function() {
        for (var a, b = [function() {
                return new XMLHttpRequest
            }, function() {
                return new ActiveXObject("Msxml2.XMLHTTP")
            }, function() {
                return new ActiveXObject("Msxml3.XMLHTTP")
            }, function() {
                return new ActiveXObject("Microsoft.XMLHTTP")
            }], c = 0; c < b.length; c++) try {
            return a = b[c](), this.createXMLHTTPObject = b[c], a
        } catch (d) {}
    },
    "isSameDomain": function(a) {
        return -1 !== a.indexOf(location.hostname)
    },
    "getSource": function(a) {
        return a in this.sourceCache || (this.sourceCache[a] = this.ajax(a).split("\n")), this.sourceCache[a]
    },
    "guessAnonymousFunctions": function(a) {
        for (var b = 0; b < a.length; ++b) {
            var c = /\{anonymous\}\(.*\)@(.*)/,
                d = /^(.*?)(?::(\d+))(?::(\d+))?(?: -- .+)?$/,
                e = a[b],
                f = c.exec(e);
            if (f) {
                var g = d.exec(f[1]),
                    h = g[1],
                    i = g[2],
                    j = g[3] || 0;
                if (h && this.isSameDomain(h) && i) {
                    var k = this.guessAnonymousFunction(h, i, j);
                    a[b] = e.replace("{anonymous}", k)
                }
            }
        }
        return a
    },
    "guessAnonymousFunction": function(a, b) {
        var c;
        try {
            c = this.findFunctionName(this.getSource(a), b)
        } catch (d) {
            c = "getSource failed with url: " + a + ", exception: " + d.toString()
        }
        return c
    },
    "findFunctionName": function(a, b) {
        for (var c, d, e, f = /function\s+([^(]*?)\s*\(([^)]*)\)/, g = /['"]?([0-9A-Za-z_]+)['"]?\s*[:=]\s*function\b/, h = /['"]?([0-9A-Za-z_]+)['"]?\s*[:=]\s*(?:eval|new Function)\b/, i = "", j = Math.min(b, 20), k = 0; j > k; ++k)
            if (c = a[b - k - 1], e = c.indexOf("//"), e >= 0 && (c = c.substr(0, e)), c) {
                if (i = c + i, d = g.exec(i), d && d[1]) return d[1];
                if (d = f.exec(i), d && d[1]) return d[1];
                if (d = h.exec(i), d && d[1]) return d[1]
            }
        return "(?)"
    }
}, CanvasRenderingContext2D.prototype.installPattern = function(a) {
    if ("undefined" != typeof this.isPatternInstalled) throw "Must un-install old line pattern before installing a new one.";
    this.isPatternInstalled = !0;
    var b = [0, 0],
        c = [],
        d = this.beginPath,
        e = this.lineTo,
        f = this.moveTo,
        g = this.stroke;
    this.uninstallPattern = function() {
        this.beginPath = d, this.lineTo = e, this.moveTo = f, this.stroke = g, this.uninstallPattern = void 0, this.isPatternInstalled = void 0
    }, this.beginPath = function() {
        c = [], d.call(this)
    }, this.moveTo = function(a, b) {
        c.push([
            [a, b]
        ]), f.call(this, a, b)
    }, this.lineTo = function(a, b) {
        var d = c[c.length - 1];
        d.push([a, b])
    }, this.stroke = function() {
        if (0 === c.length) return void g.call(this);
        for (var d = 0; d < c.length; d++)
            for (var h = c[d], i = h[0][0], j = h[0][1], k = 1; k < h.length; k++) {
                var l = h[k][0],
                    m = h[k][1];
                this.save();
                var n = l - i,
                    o = m - j,
                    p = Math.sqrt(n * n + o * o),
                    q = Math.atan2(o, n);
                this.translate(i, j), f.call(this, 0, 0), this.rotate(q);
                for (var r = b[0], s = 0; p > s;) {
                    var t = a[r];
                    s += b[1] ? b[1] : t, s > p ? (b = [r, s - p], s = p) : b = [(r + 1) % a.length, 0], r % 2 === 0 ? e.call(this, s, 0) : f.call(this, s, 0), r = (r + 1) % a.length
                }
                this.restore(), i = l, j = m
            }
        g.call(this), c = []
    }
}, CanvasRenderingContext2D.prototype.uninstallPattern = function() {
    throw "Must install a line pattern before uninstalling it."
};
var DygraphOptions = function() {
        var a = function(a) {
            this.dygraph_ = a, this.yAxes_ = [], this.xAxis_ = {}, this.series_ = {}, this.global_ = this.dygraph_.attrs_, this.user_ = this.dygraph_.user_attrs_ || {}, this.labels_ = [], this.highlightSeries_ = this.get("highlightSeriesOpts") || {}, this.reparseSeries()
        };
        return a.AXIS_STRING_MAPPINGS_ = {
            "y": 0,
            "Y": 0,
            "y1": 0,
            "Y1": 0,
            "y2": 1,
            "Y2": 1
        }, a.axisToIndex_ = function(b) {
            if ("string" == typeof b) {
                if (a.AXIS_STRING_MAPPINGS_.hasOwnProperty(b)) return a.AXIS_STRING_MAPPINGS_[b];
                throw "Unknown axis : " + b
            }
            if ("number" == typeof b) {
                if (0 === b || 1 === b) return b;
                throw "Dygraphs only supports two y-axes, indexed from 0-1."
            }
            if (b) throw "Unknown axis : " + b;
            return 0
        }, a.prototype.reparseSeries = function() {
            var b = this.get("labels");
            if (b) {
                this.labels_ = b.slice(1), this.yAxes_ = [{
                    "series": [],
                    "options": {}
                }], this.xAxis_ = {
                    "options": {}
                }, this.series_ = {};
                var c = !this.user_.series;
                if (c) {
                    for (var d = 0, e = 0; e < this.labels_.length; e++) {
                        var f = this.labels_[e],
                            g = this.user_[f] || {},
                            h = 0,
                            i = g.axis;
                        "object" == typeof i && (h = ++d, this.yAxes_[h] = {
                            "series": [f],
                            "options": i
                        }), i || this.yAxes_[0].series.push(f), this.series_[f] = {
                            "idx": e,
                            "yAxis": h,
                            "options": g
                        }
                    }
                    for (var e = 0; e < this.labels_.length; e++) {
                        var f = this.labels_[e],
                            g = this.series_[f].options,
                            i = g.axis;
                        if ("string" == typeof i) {
                            if (!this.series_.hasOwnProperty(i)) return void Dygraph.error("Series " + f + " wants to share a y-axis with series " + i + ", which does not define its own axis.");
                            var h = this.series_[i].yAxis;
                            this.series_[f].yAxis = h, this.yAxes_[h].series.push(f)
                        }
                    }
                } else
                    for (var e = 0; e < this.labels_.length; e++) {
                        var f = this.labels_[e],
                            g = this.user_.series[f] || {},
                            h = a.axisToIndex_(g.axis);
                        this.series_[f] = {
                            "idx": e,
                            "yAxis": h,
                            "options": g
                        }, this.yAxes_[h] ? this.yAxes_[h].series.push(f) : this.yAxes_[h] = {
                            "series": [f],
                            "options": {}
                        }
                    }
                var j = this.user_.axes || {};
                Dygraph.update(this.yAxes_[0].options, j.y || {}), this.yAxes_.length > 1 && Dygraph.update(this.yAxes_[1].options, j.y2 || {}), Dygraph.update(this.xAxis_.options, j.x || {})
            }
        }, a.prototype.get = function(a) {
            var b = this.getGlobalUser_(a);
            return null !== b ? b : this.getGlobalDefault_(a)
        }, a.prototype.getGlobalUser_ = function(a) {
            return this.user_.hasOwnProperty(a) ? this.user_[a] : null
        }, a.prototype.getGlobalDefault_ = function(a) {
            return this.global_.hasOwnProperty(a) ? this.global_[a] : Dygraph.DEFAULT_ATTRS.hasOwnProperty(a) ? Dygraph.DEFAULT_ATTRS[a] : null
        }, a.prototype.getForAxis = function(a, b) {
            var c, d;
            if ("number" == typeof b) c = b, d = 0 === c ? "y" : "y2";
            else {
                if ("y1" == b && (b = "y"), "y" == b) c = 0;
                else if ("y2" == b) c = 1;
                else {
                    if ("x" != b) throw "Unknown axis " + b;
                    c = -1
                }
                d = b
            }
            var e = -1 == c ? this.xAxis_ : this.yAxes_[c];
            if (e) {
                var f = e.options;
                if (f.hasOwnProperty(a)) return f[a]
            }
            var g = this.getGlobalUser_(a);
            if (null !== g) return g;
            var h = Dygraph.DEFAULT_ATTRS.axes[d];
            return h.hasOwnProperty(a) ? h[a] : this.getGlobalDefault_(a)
        }, a.prototype.getForSeries = function(a, b) {
            if (b === this.dygraph_.getHighlightSeries() && this.highlightSeries_.hasOwnProperty(a)) return this.highlightSeries_[a];
            if (!this.series_.hasOwnProperty(b)) throw "Unknown series: " + b;
            var c = this.series_[b],
                d = c.options;
            return d.hasOwnProperty(a) ? d[a] : this.getForAxis(a, c.yAxis)
        }, a.prototype.numAxes = function() {
            return this.yAxes_.length
        }, a.prototype.axisForSeries = function(a) {
            return this.series_[a].yAxis
        }, a.prototype.axisOptions = function(a) {
            return this.yAxes_[a].options
        }, a.prototype.seriesForAxis = function(a) {
            return this.yAxes_[a].series
        }, a.prototype.seriesNames = function() {
            return this.labels_
        }, a
    }(),
    DygraphLayout = function(a) {
        this.dygraph_ = a, this.points = [], this.setNames = [], this.annotations = [], this.yAxes_ = null, this.xTicks_ = null, this.yTicks_ = null
    };
DygraphLayout.prototype.attr_ = function(a) {
    return this.dygraph_.attr_(a)
}, DygraphLayout.prototype.addDataset = function(a, b) {
    this.points.push(b), this.setNames.push(a)
}, DygraphLayout.prototype.getPlotArea = function() {
    return this.area_
}, DygraphLayout.prototype.computePlotArea = function() {
    var a = {
        "x": 0,
        "y": 0
    };
    a.w = this.dygraph_.width_ - a.x - this.attr_("rightGap"), a.h = this.dygraph_.height_;
    var b = {
        "chart_div": this.dygraph_.graphDiv,
        "reserveSpaceLeft": function(b) {
            var c = {
                "x": a.x,
                "y": a.y,
                "w": b,
                "h": a.h
            };
            return a.x += b, a.w -= b, c
        },
        "reserveSpaceRight": function(b) {
            var c = {
                "x": a.x + a.w - b,
                "y": a.y,
                "w": b,
                "h": a.h
            };
            return a.w -= b, c
        },
        "reserveSpaceTop": function(b) {
            var c = {
                "x": a.x,
                "y": a.y,
                "w": a.w,
                "h": b
            };
            return a.y += b, a.h -= b, c
        },
        "reserveSpaceBottom": function(b) {
            var c = {
                "x": a.x,
                "y": a.y + a.h - b,
                "w": a.w,
                "h": b
            };
            return a.h -= b, c
        },
        "chartRect": function() {
            return {
                "x": a.x,
                "y": a.y,
                "w": a.w,
                "h": a.h
            }
        }
    };
    this.dygraph_.cascadeEvents_("layout", b), this.area_ = a
}, DygraphLayout.prototype.setAnnotations = function(a) {
    this.annotations = [];
    for (var b = this.attr_("xValueParser") || function(a) {
            return a
        }, c = 0; c < a.length; c++) {
        var d = {};
        if (!a[c].xval && void 0 === a[c].x) return void this.dygraph_.error("Annotations must have an 'x' property");
        if (a[c].icon && (!a[c].hasOwnProperty("width") || !a[c].hasOwnProperty("height"))) return void this.dygraph_.error("Must set width and height when setting annotation.icon property");
        Dygraph.update(d, a[c]), d.xval || (d.xval = b(d.x)), this.annotations.push(d)
    }
}, DygraphLayout.prototype.setXTicks = function(a) {
    this.xTicks_ = a
}, DygraphLayout.prototype.setYAxes = function(a) {
    this.yAxes_ = a
}, DygraphLayout.prototype.evaluate = function() {
    this._evaluateLimits(), this._evaluateLineCharts(), this._evaluateLineTicks(), this._evaluateAnnotations()
}, DygraphLayout.prototype._evaluateLimits = function() {
    var a = this.dygraph_.xAxisRange();
    this.minxval = a[0], this.maxxval = a[1];
    var b = a[1] - a[0];
    this.xscale = 0 !== b ? 1 / b : 1;
    for (var c = 0; c < this.yAxes_.length; c++) {
        var d = this.yAxes_[c];
        d.minyval = d.computedValueRange[0], d.maxyval = d.computedValueRange[1], d.yrange = d.maxyval - d.minyval, d.yscale = 0 !== d.yrange ? 1 / d.yrange : 1, d.g.attr_("logscale") && (d.ylogrange = Dygraph.log10(d.maxyval) - Dygraph.log10(d.minyval), d.ylogscale = 0 !== d.ylogrange ? 1 / d.ylogrange : 1, (!isFinite(d.ylogrange) || isNaN(d.ylogrange)) && d.g.error("axis " + c + " of graph at " + d.g + " can't be displayed in log scale for range [" + d.minyval + " - " + d.maxyval + "]"))
    }
}, DygraphLayout._calcYNormal = function(a, b, c) {
    return c ? 1 - (Dygraph.log10(b) - Dygraph.log10(a.minyval)) * a.ylogscale : 1 - (b - a.minyval) * a.yscale
}, DygraphLayout.prototype._evaluateLineCharts = function() {
    for (var a = this.attr_("connectSeparatedPoints"), b = this.attr_("stackedGraph"), c = this.attr_("errorBars") || this.attr_("customBars"), d = 0; d < this.points.length; d++)
        for (var e = this.points[d], f = this.setNames[d], g = this.dygraph_.axisPropertiesForSeries(f), h = this.dygraph_.attributes_.getForSeries("logscale", f), i = 0; i < e.length; i++) {
            var j = e[i];
            j.x = (j.xval - this.minxval) * this.xscale;
            var k = j.yval;
            b && (j.y_stacked = DygraphLayout._calcYNormal(g, j.yval_stacked, h), null === k || isNaN(k) || (k = j.yval_stacked)), null === k && (k = 0 / 0, a || (j.yval = 0 / 0)), j.y = DygraphLayout._calcYNormal(g, k, h), c && (j.y_top = DygraphLayout._calcYNormal(g, k - j.yval_minus, h), j.y_bottom = DygraphLayout._calcYNormal(g, k + j.yval_plus, h))
        }
}, DygraphLayout.parseFloat_ = function(a) {
    return null === a ? 0 / 0 : a
}, DygraphLayout.prototype._evaluateLineTicks = function() {
    var a, b, c, d;
    for (this.xticks = [], a = 0; a < this.xTicks_.length; a++) b = this.xTicks_[a], c = b.label, d = this.xscale * (b.v - this.minxval), d >= 0 && 1 >= d && this.xticks.push([d, c]);
    for (this.yticks = [], a = 0; a < this.yAxes_.length; a++)
        for (var e = this.yAxes_[a], f = 0; f < e.ticks.length; f++) b = e.ticks[f], c = b.label, d = this.dygraph_.toPercentYCoord(b.v, a), d >= 0 && 1 >= d && this.yticks.push([a, d, c])
}, DygraphLayout.prototype._evaluateAnnotations = function() {
    var a, b = {};
    for (a = 0; a < this.annotations.length; a++) {
        var c = this.annotations[a];
        b[c.xval + "," + c.series] = c
    }
    if (this.annotated_points = [], this.annotations && this.annotations.length)
        for (var d = 0; d < this.points.length; d++) {
            var e = this.points[d];
            for (a = 0; a < e.length; a++) {
                var f = e[a],
                    g = f.xval + "," + f.name;
                g in b && (f.annotation = b[g], this.annotated_points.push(f))
            }
        }
}, DygraphLayout.prototype.removeAllDatasets = function() {
    delete this.points, delete this.setNames, delete this.setPointsLengths, delete this.setPointsOffsets, this.points = [], this.setNames = [], this.setPointsLengths = [], this.setPointsOffsets = []
};
var DygraphCanvasRenderer = function(a, b, c, d) {
    if (this.dygraph_ = a, this.layout = d, this.element = b, this.elementContext = c, this.container = this.element.parentNode, this.height = this.element.height, this.width = this.element.width, !this.isIE && !DygraphCanvasRenderer.isSupported(this.element)) throw "Canvas is not supported.";
    if (this.area = d.getPlotArea(), this.container.style.position = "relative", this.container.style.width = this.width + "px", this.dygraph_.isUsingExcanvas_) this._createIEClipArea();
    else if (!Dygraph.isAndroid()) {
        var e = this.dygraph_.canvas_ctx_;
        e.beginPath(), e.rect(this.area.x, this.area.y, this.area.w, this.area.h), e.clip(), e = this.dygraph_.hidden_ctx_, e.beginPath(), e.rect(this.area.x, this.area.y, this.area.w, this.area.h), e.clip()
    }
};
DygraphCanvasRenderer.prototype.attr_ = function(a, b) {
    return this.dygraph_.attr_(a, b)
}, DygraphCanvasRenderer.prototype.clear = function() {
    var a;
    if (this.isIE) try {
        this.clearDelay && (this.clearDelay.cancel(), this.clearDelay = null), a = this.elementContext
    } catch (b) {
        return
    }
    a = this.elementContext, a.clearRect(0, 0, this.width, this.height)
}, DygraphCanvasRenderer.isSupported = function(a) {
    var b = null;
    try {
        b = "undefined" == typeof a || null === a ? document.createElement("canvas") : a, b.getContext("2d")
    } catch (c) {
        var d = navigator.appVersion.match(/MSIE (\d\.\d)/),
            e = -1 != navigator.userAgent.toLowerCase().indexOf("opera");
        return !d || d[1] < 6 || e ? !1 : !0
    }
    return !0
}, DygraphCanvasRenderer.prototype.render = function() {
    this._updatePoints(), this._renderLineChart()
}, DygraphCanvasRenderer.prototype._createIEClipArea = function() {
    function a(a) {
        if (0 !== a.w && 0 !== a.h) {
            var d = document.createElement("div");
            d.className = b, d.style.backgroundColor = e, d.style.position = "absolute", d.style.left = a.x + "px", d.style.top = a.y + "px", d.style.width = a.w + "px", d.style.height = a.h + "px", c.appendChild(d)
        }
    }
    for (var b = "dygraph-clip-div", c = this.dygraph_.graphDiv, d = c.childNodes.length - 1; d >= 0; d--) c.childNodes[d].className == b && c.removeChild(c.childNodes[d]);
    for (var e = document.bgColor, f = this.dygraph_.graphDiv; f != document;) {
        var g = f.currentStyle.backgroundColor;
        if (g && "transparent" != g) {
            e = g;
            break
        }
        f = f.parentNode
    }
    var h = this.area;
    a({
        "x": 0,
        "y": 0,
        "w": h.x,
        "h": this.height
    }), a({
        "x": h.x,
        "y": 0,
        "w": this.width - h.x,
        "h": h.y
    }), a({
        "x": h.x + h.w,
        "y": 0,
        "w": this.width - h.x - h.w,
        "h": this.height
    }), a({
        "x": h.x,
        "y": h.y + h.h,
        "w": this.width - h.x,
        "h": this.height - h.h - h.y
    })
}, DygraphCanvasRenderer._getIteratorPredicate = function(a) {
    return a ? DygraphCanvasRenderer._predicateThatSkipsEmptyPoints : null
}, DygraphCanvasRenderer._predicateThatSkipsEmptyPoints = function(a, b) {
    return null !== a[b].yval
}, DygraphCanvasRenderer._drawStyledLine = function(a, b, c, d, e, f, g) {
    var h = a.dygraph,
        i = h.getOption("stepPlot", a.setName);
    Dygraph.isArrayLike(d) || (d = null);
    var j = h.getOption("drawGapEdgePoints", a.setName),
        k = a.points,
        l = Dygraph.createIterator(k, 0, k.length, DygraphCanvasRenderer._getIteratorPredicate(h.getOption("connectSeparatedPoints"))),
        m = d && d.length >= 2,
        n = a.drawingContext;
    n.save(), m && n.installPattern(d);
    var o = DygraphCanvasRenderer._drawSeries(a, l, c, g, e, j, i, b);
    DygraphCanvasRenderer._drawPointsOnLine(a, o, f, b, g), m && n.uninstallPattern(), n.restore()
}, DygraphCanvasRenderer._drawSeries = function(a, b, c, d, e, f, g, h) {
    var i, j, k = null,
        l = null,
        m = null,
        n = [],
        o = !0,
        p = a.drawingContext;
    p.beginPath(), p.strokeStyle = h, p.lineWidth = c;
    for (var q = b.array_, r = b.end_, s = b.predicate_, t = b.start_; r > t; t++) {
        if (j = q[t], s) {
            for (; r > t && !s(q, t);) t++;
            if (t == r) break;
            j = q[t]
        }
        if (null === j.canvasy || j.canvasy != j.canvasy) g && null !== k && (p.moveTo(k, l), p.lineTo(j.canvasx, l)), k = l = null;
        else {
            if (i = !1, f || !k) {
                b.nextIdx_ = t, b.next(), m = b.hasNext ? b.peek.canvasy : null;
                var u = null === m || m != m;
                i = !k && u, f && (!o && !k || b.hasNext && u) && (i = !0)
            }
            null !== k ? c && (g && (p.moveTo(k, l), p.lineTo(j.canvasx, l)), p.lineTo(j.canvasx, j.canvasy)) : p.moveTo(j.canvasx, j.canvasy), (e || i) && n.push([j.canvasx, j.canvasy, j.idx]), k = j.canvasx, l = j.canvasy
        }
        o = !1
    }
    return p.stroke(), n
}, DygraphCanvasRenderer._drawPointsOnLine = function(a, b, c, d, e) {
    for (var f = a.drawingContext, g = 0; g < b.length; g++) {
        var h = b[g];
        f.save(), c(a.dygraph, a.setName, f, h[0], h[1], d, e, h[2]), f.restore()
    }
}, DygraphCanvasRenderer.prototype._updatePoints = function() {
    for (var a = this.layout.points, b = a.length; b--;)
        for (var c = a[b], d = c.length; d--;) {
            var e = c[d];
            e.canvasx = this.area.w * e.x + this.area.x, e.canvasy = this.area.h * e.y + this.area.y
        }
}, DygraphCanvasRenderer.prototype._renderLineChart = function(a, b) {
    var c, d, e = b || this.elementContext,
        f = this.layout.points,
        g = this.layout.setNames;
    this.colors = this.dygraph_.colorsMap_;
    var h = this.attr_("plotter"),
        i = h;
    Dygraph.isArrayLike(i) || (i = [i]);
    var j = {};
    for (c = 0; c < g.length; c++) {
        d = g[c];
        var k = this.attr_("plotter", d);
        k != h && (j[d] = k)
    }
    for (c = 0; c < i.length; c++)
        for (var l = i[c], m = c == i.length - 1, n = 0; n < f.length; n++)
            if (d = g[n], !a || d == a) {
                var o = f[n],
                    p = l;
                if (d in j) {
                    if (!m) continue;
                    p = j[d]
                }
                var q = this.colors[d],
                    r = this.dygraph_.getOption("strokeWidth", d);
                e.save(), e.strokeStyle = q, e.lineWidth = r, p({
                    "points": o,
                    "setName": d,
                    "drawingContext": e,
                    "color": q,
                    "strokeWidth": r,
                    "dygraph": this.dygraph_,
                    "axis": this.dygraph_.axisPropertiesForSeries(d),
                    "plotArea": this.area,
                    "seriesIndex": n,
                    "seriesCount": f.length,
                    "singleSeriesName": a,
                    "allSeriesPoints": f
                }), e.restore()
            }
}, DygraphCanvasRenderer._Plotters = {
    "linePlotter": function(a) {
        DygraphCanvasRenderer._linePlotter(a)
    },
    "fillPlotter": function(a) {
        DygraphCanvasRenderer._fillPlotter(a)
    },
    "errorPlotter": function(a) {
        DygraphCanvasRenderer._errorPlotter(a)
    }
}, DygraphCanvasRenderer._linePlotter = function(a) {
    var b = a.dygraph,
        c = a.setName,
        d = a.strokeWidth,
        e = b.getOption("strokeBorderWidth", c),
        f = b.getOption("drawPointCallback", c) || Dygraph.Circles.DEFAULT,
        g = b.getOption("strokePattern", c),
        h = b.getOption("drawPoints", c),
        i = b.getOption("pointSize", c);
    e && d && DygraphCanvasRenderer._drawStyledLine(a, b.getOption("strokeBorderColor", c), d + 2 * e, g, h, f, i), DygraphCanvasRenderer._drawStyledLine(a, a.color, d, g, h, f, i)
}, DygraphCanvasRenderer._errorPlotter = function(a) {
    var b = a.dygraph,
        c = a.setName,
        d = b.getOption("errorBars") || b.getOption("customBars");
    if (d) {
        var e = b.getOption("fillGraph", c);
        e && b.warn("Can't use fillGraph option with error bars");
        var f, g = a.drawingContext,
            h = a.color,
            i = b.getOption("fillAlpha", c),
            j = b.getOption("stepPlot", c),
            k = a.points,
            l = Dygraph.createIterator(k, 0, k.length, DygraphCanvasRenderer._getIteratorPredicate(b.getOption("connectSeparatedPoints"))),
            m = 0 / 0,
            n = 0 / 0,
            o = [-1, -1],
            p = new RGBColorParser(h),
            q = "rgba(" + p.r + "," + p.g + "," + p.b + "," + i + ")";
        g.fillStyle = q, g.beginPath();
        for (var r = function(a) {
                return null === a || void 0 === a || isNaN(a)
            }; l.hasNext;) {
            var s = l.next();
            !j && r(s.y) || j && !isNaN(n) && r(n) ? m = 0 / 0 : (j ? (f = [s.y_bottom, s.y_top], n = s.y) : f = [s.y_bottom, s.y_top], f[0] = a.plotArea.h * f[0] + a.plotArea.y, f[1] = a.plotArea.h * f[1] + a.plotArea.y, isNaN(m) || (j ? (g.moveTo(m, o[0]), g.lineTo(s.canvasx, o[0]), g.lineTo(s.canvasx, o[1])) : (g.moveTo(m, o[0]), g.lineTo(s.canvasx, f[0]), g.lineTo(s.canvasx, f[1])), g.lineTo(m, o[1]), g.closePath()), o = f, m = s.canvasx)
        }
        g.fill()
    }
}, DygraphCanvasRenderer._fillPlotter = function(a) {
    if (!a.singleSeriesName && 0 === a.seriesIndex) {
        for (var b = a.dygraph, c = b.getLabels().slice(1), d = c.length; d >= 0; d--) b.visibility()[d] || c.splice(d, 1);
        var e = function() {
            for (var a = 0; a < c.length; a++)
                if (b.getOption("fillGraph", c[a])) return !0;
            return !1
        }();
        if (e)
            for (var f, g, h = a.drawingContext, i = a.plotArea, j = a.allSeriesPoints, k = j.length, l = b.getOption("fillAlpha"), m = b.getOption("stackedGraph"), n = b.getColors(), o = {}, p = k - 1; p >= 0; p--) {
                var q = c[p];
                if (b.getOption("fillGraph", q)) {
                    var r = b.getOption("stepPlot", q),
                        s = n[p],
                        t = b.axisPropertiesForSeries(q),
                        u = 1 + t.minyval * t.yscale;
                    0 > u ? u = 0 : u > 1 && (u = 1), u = i.h * u + i.y;
                    var v, w = j[p],
                        x = Dygraph.createIterator(w, 0, w.length, DygraphCanvasRenderer._getIteratorPredicate(b.getOption("connectSeparatedPoints"))),
                        y = 0 / 0,
                        z = [-1, -1],
                        A = new RGBColorParser(s),
                        B = "rgba(" + A.r + "," + A.g + "," + A.b + "," + l + ")";
                    h.fillStyle = B, h.beginPath();
                    for (var C, D = !0; x.hasNext;) {
                        var E = x.next();
                        if (Dygraph.isOK(E.y)) {
                            if (m) {
                                if (!D && C == E.xval) continue;
                                D = !1, C = E.xval, f = o[E.canvasx];
                                var F;
                                F = void 0 === f ? u : g ? f[0] : f, v = [E.canvasy, F], o[E.canvasx] = r ? -1 === z[0] ? [E.canvasy, u] : [E.canvasy, z[0]] : E.canvasy
                            } else v = [E.canvasy, u];
                            isNaN(y) || (h.moveTo(y, z[0]), r ? h.lineTo(E.canvasx, z[0]) : h.lineTo(E.canvasx, v[0]), g && f ? h.lineTo(E.canvasx, f[1]) : h.lineTo(E.canvasx, v[1]), h.lineTo(y, z[1]), h.closePath()), z = v, y = E.canvasx
                        } else y = 0 / 0, null === E.y_stacked || isNaN(E.y_stacked) || (o[E.canvasx] = i.h * E.y_stacked + i.y)
                    }
                    g = r, h.fill()
                }
            }
    }
};
var Dygraph = function(a, b, c, d) {
    this.is_initial_draw_ = !0, this.readyFns_ = [], void 0 !== d ? (this.warn("Using deprecated four-argument dygraph constructor"), this.__old_init__(a, b, c, d)) : this.__init__(a, b, c)
};
Dygraph.NAME = "Dygraph", Dygraph.VERSION = "1.0.1", Dygraph.__repr__ = function() {
    return "[" + this.NAME + " " + this.VERSION + "]"
}, Dygraph.toString = function() {
    return this.__repr__()
}, Dygraph.DEFAULT_ROLL_PERIOD = 1, Dygraph.DEFAULT_WIDTH = 480, Dygraph.DEFAULT_HEIGHT = 320, Dygraph.ANIMATION_STEPS = 12, Dygraph.ANIMATION_DURATION = 200, Dygraph.KMB_LABELS = ["K", "M", "B", "T", "Q"], Dygraph.KMG2_BIG_LABELS = ["k", "M", "G", "T", "P", "E", "Z", "Y"], Dygraph.KMG2_SMALL_LABELS = ["m", "u", "n", "p", "f", "a", "z", "y"], Dygraph.numberValueFormatter = function(a, b) {
    var c = b("sigFigs");
    if (null !== c) return Dygraph.floatFormat(a, c);
    var d, e = b("digitsAfterDecimal"),
        f = b("maxNumberWidth"),
        g = b("labelsKMB"),
        h = b("labelsKMG2");
    if (d = 0 !== a && (Math.abs(a) >= Math.pow(10, f) || Math.abs(a) < Math.pow(10, -e)) ? a.toExponential(e) : "" + Dygraph.round_(a, e), g || h) {
        var i, j = [],
            k = [];
        g && (i = 1e3, j = Dygraph.KMB_LABELS), h && (g && Dygraph.warn("Setting both labelsKMB and labelsKMG2. Pick one!"), i = 1024, j = Dygraph.KMG2_BIG_LABELS, k = Dygraph.KMG2_SMALL_LABELS);
        for (var l = Math.abs(a), m = Dygraph.pow(i, j.length), n = j.length - 1; n >= 0; n--, m /= i)
            if (l >= m) {
                d = Dygraph.round_(a / m, e) + j[n];
                break
            }
        if (h) {
            var o = String(a.toExponential()).split("e-");
            2 === o.length && o[1] >= 3 && o[1] <= 24 && (d = o[1] % 3 > 0 ? Dygraph.round_(o[0] / Dygraph.pow(10, o[1] % 3), e) : Number(o[0]).toFixed(2), d += k[Math.floor(o[1] / 3) - 1])
        }
    }
    return d
}, Dygraph.numberAxisLabelFormatter = function(a, b, c, d) {
    return Dygraph.numberValueFormatter(a, c, d)
}, Dygraph.dateString_ = function(a) {
    var b = Dygraph.zeropad,
        c = new Date(a),
        d = "" + c.getFullYear(),
        e = b(c.getMonth() + 1),
        f = b(c.getDate()),
        g = "",
        h = 3600 * c.getHours() + 60 * c.getMinutes() + c.getSeconds();
    return h && (g = " " + Dygraph.hmsString_(a)), d + "/" + e + "/" + f + g
}, Dygraph.dateAxisFormatter = function(a, b) {
    if (b >= Dygraph.DECADAL) return a.strftime("%Y");
    if (b >= Dygraph.MONTHLY) return a.strftime("%b %y");
    var c = 3600 * a.getHours() + 60 * a.getMinutes() + a.getSeconds() + a.getMilliseconds();
    return 0 === c || b >= Dygraph.DAILY ? new Date(a.getTime() + 36e5).strftime("%d%b") : Dygraph.hmsString_(a.getTime())
}, Dygraph.Plotters = DygraphCanvasRenderer._Plotters, Dygraph.DEFAULT_ATTRS = {
    "highlightCircleSize": 3,
    "highlightSeriesOpts": null,
    "highlightSeriesBackgroundAlpha": .5,
    "labelsDivWidth": 250,
    "labelsDivStyles": {},
    "labelsSeparateLines": !1,
    "labelsShowZeroValues": !0,
    "labelsKMB": !1,
    "labelsKMG2": !1,
    "showLabelsOnHighlight": !0,
    "digitsAfterDecimal": 2,
    "maxNumberWidth": 6,
    "sigFigs": null,
    "strokeWidth": 1,
    "strokeBorderWidth": 0,
    "strokeBorderColor": "white",
    "axisTickSize": 3,
    "axisLabelFontSize": 14,
    "xAxisLabelWidth": 50,
    "yAxisLabelWidth": 50,
    "rightGap": 5,
    "showRoller": !1,
    "xValueParser": Dygraph.dateParser,
    "delimiter": ",",
    "sigma": 2,
    "errorBars": !1,
    "fractions": !1,
    "wilsonInterval": !0,
    "customBars": !1,
    "fillGraph": !1,
    "fillAlpha": .15,
    "connectSeparatedPoints": !1,
    "stackedGraph": !1,
    "stackedGraphNaNFill": "all",
    "hideOverlayOnMouseOut": !0,
    "legend": "onmouseover",
    "stepPlot": !1,
    "avoidMinZero": !1,
    "xRangePad": 0,
    "yRangePad": null,
    "drawAxesAtZero": !1,
    "titleHeight": 28,
    "xLabelHeight": 18,
    "yLabelWidth": 18,
    "drawXAxis": !0,
    "drawYAxis": !0,
    "axisLineColor": "black",
    "axisLineWidth": .3,
    "gridLineWidth": .3,
    "axisLabelColor": "black",
    "axisLabelFont": "Arial",
    "axisLabelWidth": 50,
    "drawYGrid": !0,
    "drawXGrid": !0,
    "gridLineColor": "rgb(128,128,128)",
    "interactionModel": null,
    "animatedZooms": !1,
    "showRangeSelector": !1,
    "rangeSelectorHeight": 40,
    "rangeSelectorPlotStrokeColor": "#808FAB",
    "rangeSelectorPlotFillColor": "#A7B1C4",
    "plotter": [Dygraph.Plotters.fillPlotter, Dygraph.Plotters.errorPlotter, Dygraph.Plotters.linePlotter],
    "plugins": [],
    "axes": {
        "x": {
            "pixelsPerLabel": 60,
            "axisLabelFormatter": Dygraph.dateAxisFormatter,
            "valueFormatter": Dygraph.dateString_,
            "drawGrid": !0,
            "independentTicks": !0,
            "ticker": null
        },
        "y": {
            "pixelsPerLabel": 30,
            "valueFormatter": Dygraph.numberValueFormatter,
            "axisLabelFormatter": Dygraph.numberAxisLabelFormatter,
            "drawGrid": !0,
            "independentTicks": !0,
            "ticker": null
        },
        "y2": {
            "pixelsPerLabel": 30,
            "valueFormatter": Dygraph.numberValueFormatter,
            "axisLabelFormatter": Dygraph.numberAxisLabelFormatter,
            "drawGrid": !1,
            "independentTicks": !1,
            "ticker": null
        }
    }
}, Dygraph.HORIZONTAL = 1, Dygraph.VERTICAL = 2, Dygraph.PLUGINS = [], Dygraph.addedAnnotationCSS = !1, Dygraph.prototype.__old_init__ = function(a, b, c, d) {
    if (null !== c) {
        for (var e = ["Date"], f = 0; f < c.length; f++) e.push(c[f]);
        Dygraph.update(d, {
            "labels": e
        })
    }
    this.__init__(a, b, d)
}, Dygraph.prototype.__init__ = function(a, b, c) {
    if (/MSIE/.test(navigator.userAgent) && !window.opera && "undefined" != typeof G_vmlCanvasManager && "complete" != document.readyState) {
        var d = this;
        return void setTimeout(function() {
            d.__init__(a, b, c)
        }, 100)
    }
    if ((null === c || void 0 === c) && (c = {}), c = Dygraph.mapLegacyOptions_(c), "string" == typeof a && (a = document.getElementById(a)), !a) return void Dygraph.error("Constructing dygraph with a non-existent div!");
    this.isUsingExcanvas_ = "undefined" != typeof G_vmlCanvasManager, this.maindiv_ = a, this.file_ = b, this.rollPeriod_ = c.rollPeriod || Dygraph.DEFAULT_ROLL_PERIOD, this.previousVerticalX_ = -1, this.fractions_ = c.fractions || !1, this.dateWindow_ = c.dateWindow || null, this.annotations_ = [], this.zoomed_x_ = !1, this.zoomed_y_ = !1, a.innerHTML = "", "" === a.style.width && c.width && (a.style.width = c.width + "px"), "" === a.style.height && c.height && (a.style.height = c.height + "px"), "" === a.style.height && 0 === a.clientHeight && (a.style.height = Dygraph.DEFAULT_HEIGHT + "px", "" === a.style.width && (a.style.width = Dygraph.DEFAULT_WIDTH + "px")), this.width_ = a.clientWidth || c.width || 0, this.height_ = a.clientHeight || c.height || 0, c.stackedGraph && (c.fillGraph = !0), this.user_attrs_ = {}, Dygraph.update(this.user_attrs_, c), this.attrs_ = {}, Dygraph.updateDeep(this.attrs_, Dygraph.DEFAULT_ATTRS), this.boundaryIds_ = [], this.setIndexByName_ = {}, this.datasetIndex_ = [], this.registeredEvents_ = [], this.eventListeners_ = {}, this.attributes_ = new DygraphOptions(this), this.createInterface_(), this.plugins_ = [];
    for (var e = Dygraph.PLUGINS.concat(this.getOption("plugins")), f = 0; f < e.length; f++) {
        var g = e[f],
            h = new g,
            i = {
                "plugin": h,
                "events": {},
                "options": {},
                "pluginOptions": {}
            },
            j = h.activate(this);
        for (var k in j) i.events[k] = j[k];
        this.plugins_.push(i)
    }
    for (var f = 0; f < this.plugins_.length; f++) {
        var l = this.plugins_[f];
        for (var k in l.events)
            if (l.events.hasOwnProperty(k)) {
                var m = l.events[k],
                    n = [l.plugin, m];
                k in this.eventListeners_ ? this.eventListeners_[k].push(n) : this.eventListeners_[k] = [n]
            }
    }
    this.createDragInterface_(), this.start_()
}, Dygraph.prototype.cascadeEvents_ = function(a, b) {
    if (!(a in this.eventListeners_)) return !0;
    var c = {
        "dygraph": this,
        "cancelable": !1,
        "defaultPrevented": !1,
        "preventDefault": function() {
            if (!c.cancelable) throw "Cannot call preventDefault on non-cancelable event.";
            c.defaultPrevented = !0
        },
        "propagationStopped": !1,
        "stopPropagation": function() {
            c.propagationStopped = !0
        }
    };
    Dygraph.update(c, b);
    var d = this.eventListeners_[a];
    if (d)
        for (var e = d.length - 1; e >= 0; e--) {
            var f = d[e][0],
                g = d[e][1];
            if (g.call(f, c), c.propagationStopped) break
        }
    return c.defaultPrevented
}, Dygraph.prototype.isZoomed = function(a) {
    if (null === a || void 0 === a) return this.zoomed_x_ || this.zoomed_y_;
    if ("x" === a) return this.zoomed_x_;
    if ("y" === a) return this.zoomed_y_;
    throw "axis parameter is [" + a + "] must be null, 'x' or 'y'."
}, Dygraph.prototype.toString = function() {
    var a = this.maindiv_,
        b = a && a.id ? a.id : a;
    return "[Dygraph " + b + "]"
}, Dygraph.prototype.attr_ = function(a, b) {
    return b ? this.attributes_.getForSeries(a, b) : this.attributes_.get(a)
}, Dygraph.prototype.getOption = function(a, b) {
    return this.attr_(a, b)
}, Dygraph.prototype.getOptionForAxis = function(a, b) {
    return this.attributes_.getForAxis(a, b)
}, Dygraph.prototype.optionsViewForAxis_ = function(a) {
    var b = this;
    return function(c) {
        var d = b.user_attrs_.axes;
        return d && d[a] && d[a].hasOwnProperty(c) ? d[a][c] : "undefined" != typeof b.user_attrs_[c] ? b.user_attrs_[c] : (d = b.attrs_.axes, d && d[a] && d[a].hasOwnProperty(c) ? d[a][c] : "y" == a && b.axes_[0].hasOwnProperty(c) ? b.axes_[0][c] : "y2" == a && b.axes_[1].hasOwnProperty(c) ? b.axes_[1][c] : b.attr_(c))
    }
}, Dygraph.prototype.rollPeriod = function() {
    return this.rollPeriod_
}, Dygraph.prototype.xAxisRange = function() {
    return this.dateWindow_ ? this.dateWindow_ : this.xAxisExtremes()
}, Dygraph.prototype.xAxisExtremes = function() {
    var a = this.attr_("xRangePad") / this.plotter_.area.w;
    if (0 === this.numRows()) return [0 - a, 1 + a];
    var b = this.rawData_[0][0],
        c = this.rawData_[this.rawData_.length - 1][0];
    if (a) {
        var d = c - b;
        b -= d * a, c += d * a
    }
    return [b, c]
}, Dygraph.prototype.yAxisRange = function(a) {
    if ("undefined" == typeof a && (a = 0), 0 > a || a >= this.axes_.length) return null;
    var b = this.axes_[a];
    return [b.computedValueRange[0], b.computedValueRange[1]]
}, Dygraph.prototype.yAxisRanges = function() {
    for (var a = [], b = 0; b < this.axes_.length; b++) a.push(this.yAxisRange(b));
    return a
}, Dygraph.prototype.toDomCoords = function(a, b, c) {
    return [this.toDomXCoord(a), this.toDomYCoord(b, c)]
}, Dygraph.prototype.toDomXCoord = function(a) {
    if (null === a) return null;
    var b = this.plotter_.area,
        c = this.xAxisRange();
    return b.x + (a - c[0]) / (c[1] - c[0]) * b.w
}, Dygraph.prototype.toDomYCoord = function(a, b) {
    var c = this.toPercentYCoord(a, b);
    if (null === c) return null;
    var d = this.plotter_.area;
    return d.y + c * d.h
}, Dygraph.prototype.toDataCoords = function(a, b, c) {
    return [this.toDataXCoord(a), this.toDataYCoord(b, c)]
}, Dygraph.prototype.toDataXCoord = function(a) {
    if (null === a) return null;
    var b = this.plotter_.area,
        c = this.xAxisRange();
    return c[0] + (a - b.x) / b.w * (c[1] - c[0])
}, Dygraph.prototype.toDataYCoord = function(a, b) {
    if (null === a) return null;
    var c = this.plotter_.area,
        d = this.yAxisRange(b);
    if ("undefined" == typeof b && (b = 0), this.attributes_.getForAxis("logscale", b)) {
        var e = (a - c.y) / c.h,
            f = Dygraph.log10(d[1]),
            g = f - e * (f - Dygraph.log10(d[0])),
            h = Math.pow(Dygraph.LOG_SCALE, g);
        return h
    }
    return d[0] + (c.y + c.h - a) / c.h * (d[1] - d[0])
}, Dygraph.prototype.toPercentYCoord = function(a, b) {
    if (null === a) return null;
    "undefined" == typeof b && (b = 0);
    var c, d = this.yAxisRange(b),
        e = this.attributes_.getForAxis("logscale", b);
    if (e) {
        var f = Dygraph.log10(d[1]);
        c = (f - Dygraph.log10(a)) / (f - Dygraph.log10(d[0]))
    } else c = (d[1] - a) / (d[1] - d[0]);
    return c
}, Dygraph.prototype.toPercentXCoord = function(a) {
    if (null === a) return null;
    var b = this.xAxisRange();
    return (a - b[0]) / (b[1] - b[0])
}, Dygraph.prototype.numColumns = function() {
    return this.rawData_ ? this.rawData_[0] ? this.rawData_[0].length : this.attr_("labels").length : 0
}, Dygraph.prototype.numRows = function() {
    return this.rawData_ ? this.rawData_.length : 0
}, Dygraph.prototype.getValue = function(a, b) {
    return 0 > a || a > this.rawData_.length ? null : 0 > b || b > this.rawData_[a].length ? null : this.rawData_[a][b]
}, Dygraph.prototype.createInterface_ = function() {
    var a = this.maindiv_;
    this.graphDiv = document.createElement("div"), this.graphDiv.style.textAlign = "left", a.appendChild(this.graphDiv), this.canvas_ = Dygraph.createCanvas(), this.canvas_.style.position = "absolute", this.hidden_ = this.createPlotKitCanvas_(this.canvas_), this.resizeElements_(), this.canvas_ctx_ = Dygraph.getContext(this.canvas_), this.hidden_ctx_ = Dygraph.getContext(this.hidden_), this.graphDiv.appendChild(this.hidden_), this.graphDiv.appendChild(this.canvas_), this.mouseEventElement_ = this.createMouseEventElement_(), this.layout_ = new DygraphLayout(this);
    var b = this;
    this.mouseMoveHandler_ = function(a) {
        b.mouseMove_(a)
    }, this.mouseOutHandler_ = function(a) {
        var c = a.target || a.fromElement,
            d = a.relatedTarget || a.toElement;
        Dygraph.isNodeContainedBy(c, b.graphDiv) && !Dygraph.isNodeContainedBy(d, b.graphDiv) && b.mouseOut_(a)
    }, this.addAndTrackEvent(window, "mouseout", this.mouseOutHandler_), this.addAndTrackEvent(this.mouseEventElement_, "mousemove", this.mouseMoveHandler_), this.resizeHandler_ || (this.resizeHandler_ = function() {
        b.resize()
    }, this.addAndTrackEvent(window, "resize", this.resizeHandler_))
}, Dygraph.prototype.resizeElements_ = function() {
    this.graphDiv.style.width = this.width_ + "px", this.graphDiv.style.height = this.height_ + "px", this.canvas_.width = this.width_, this.canvas_.height = this.height_, this.canvas_.style.width = this.width_ + "px", this.canvas_.style.height = this.height_ + "px", this.hidden_.width = this.width_, this.hidden_.height = this.height_, this.hidden_.style.width = this.width_ + "px", this.hidden_.style.height = this.height_ + "px"
}, Dygraph.prototype.destroy = function() {
    this.canvas_ctx_.restore(), this.hidden_ctx_.restore();
    var a = function(b) {
        for (; b.hasChildNodes();) a(b.firstChild), b.removeChild(b.firstChild)
    };
    this.removeTrackedEvents_(), Dygraph.removeEvent(window, "mouseout", this.mouseOutHandler_), Dygraph.removeEvent(this.mouseEventElement_, "mousemove", this.mouseMoveHandler_), Dygraph.removeEvent(window, "resize", this.resizeHandler_), this.resizeHandler_ = null, a(this.maindiv_);
    var b = function(a) {
        for (var b in a) "object" == typeof a[b] && (a[b] = null)
    };
    b(this.layout_), b(this.plotter_), b(this)
}, Dygraph.prototype.createPlotKitCanvas_ = function(a) {
    var b = Dygraph.createCanvas();
    return b.style.position = "absolute", b.style.top = a.style.top, b.style.left = a.style.left, b.width = this.width_, b.height = this.height_, b.style.width = this.width_ + "px", b.style.height = this.height_ + "px", b
}, Dygraph.prototype.createMouseEventElement_ = function() {
    if (this.isUsingExcanvas_) {
        var a = document.createElement("div");
        return a.style.position = "absolute", a.style.backgroundColor = "white", a.style.filter = "alpha(opacity=0)", a.style.width = this.width_ + "px", a.style.height = this.height_ + "px", this.graphDiv.appendChild(a), a
    }
    return this.canvas_
}, Dygraph.prototype.setColors_ = function() {
    var a = this.getLabels(),
        b = a.length - 1;
    this.colors_ = [], this.colorsMap_ = {};
    var c, d = this.attr_("colors");
    if (d) {
        for (c = 0; b > c; c++)
            if (this.visibility()[c]) {
                var e = d[c % d.length];
                this.colors_.push(e), this.colorsMap_[a[1 + c]] = e
            }
    } else {
        var f = this.attr_("colorSaturation") || 1,
            g = this.attr_("colorValue") || .5,
            h = Math.ceil(b / 2);
        for (c = 1; b >= c; c++)
            if (this.visibility()[c - 1]) {
                var i = c % 2 ? Math.ceil(c / 2) : h + c / 2,
                    j = 1 * i / (1 + b),
                    e = Dygraph.hsvToRGB(j, f, g);
                this.colors_.push(e), this.colorsMap_[a[c]] = e
            }
    }
}, Dygraph.prototype.getColors = function() {
    return this.colors_
}, Dygraph.prototype.getPropertiesForSeries = function(a) {
    for (var b = -1, c = this.getLabels(), d = 1; d < c.length; d++)
        if (c[d] == a) {
            b = d;
            break
        }
    return -1 == b ? null : {
        "name": a,
        "column": b,
        "visible": this.visibility()[b - 1],
        "color": this.colorsMap_[a],
        "axis": 1 + this.attributes_.axisForSeries(a)
    }
}, Dygraph.prototype.createRollInterface_ = function() {
    this.roller_ || (this.roller_ = document.createElement("input"), this.roller_.type = "text", this.roller_.style.display = "none", this.graphDiv.appendChild(this.roller_));
    var a = this.attr_("showRoller") ? "block" : "none",
        b = this.plotter_.area,
        c = {
            "position": "absolute",
            "zIndex": 10,
            "top": b.y + b.h - 25 + "px",
            "left": b.x + 1 + "px",
            "display": a
        };
    this.roller_.size = "2", this.roller_.value = this.rollPeriod_;
    for (var d in c) c.hasOwnProperty(d) && (this.roller_.style[d] = c[d]);
    var e = this;
    this.roller_.onchange = function() {
        e.adjustRoll(e.roller_.value)
    }
}, Dygraph.prototype.dragGetX_ = function(a, b) {
    return Dygraph.pageX(a) - b.px
}, Dygraph.prototype.dragGetY_ = function(a, b) {
    return Dygraph.pageY(a) - b.py
}, Dygraph.prototype.createDragInterface_ = function() {
    var a = {
            "isZooming": !1,
            "isPanning": !1,
            "is2DPan": !1,
            "dragStartX": null,
            "dragStartY": null,
            "dragEndX": null,
            "dragEndY": null,
            "dragDirection": null,
            "prevEndX": null,
            "prevEndY": null,
            "prevDragDirection": null,
            "cancelNextDblclick": !1,
            "initialLeftmostDate": null,
            "xUnitsPerPixel": null,
            "dateRange": null,
            "px": 0,
            "py": 0,
            "boundedDates": null,
            "boundedValues": null,
            "tarp": new Dygraph.IFrameTarp,
            "initializeMouseDown": function(a, b, c) {
                a.preventDefault ? a.preventDefault() : (a.returnValue = !1, a.cancelBubble = !0), c.px = Dygraph.findPosX(b.canvas_), c.py = Dygraph.findPosY(b.canvas_), c.dragStartX = b.dragGetX_(a, c), c.dragStartY = b.dragGetY_(a, c), c.cancelNextDblclick = !1, c.tarp.cover()
            }
        },
        b = this.attr_("interactionModel"),
        c = this,
        d = function(b) {
            return function(d) {
                b(d, c, a)
            }
        };
    for (var e in b) b.hasOwnProperty(e) && this.addAndTrackEvent(this.mouseEventElement_, e, d(b[e]));
    var f = function() {
        if ((a.isZooming || a.isPanning) && (a.isZooming = !1, a.dragStartX = null, a.dragStartY = null), a.isPanning) {
            a.isPanning = !1, a.draggingDate = null, a.dateRange = null;
            for (var b = 0; b < c.axes_.length; b++) delete c.axes_[b].draggingValue, delete c.axes_[b].dragValueRange
        }
        a.tarp.uncover()
    };
    this.addAndTrackEvent(document, "mouseup", f)
}, Dygraph.prototype.drawZoomRect_ = function(a, b, c, d, e, f, g, h) {
    var i = this.canvas_ctx_;
    f == Dygraph.HORIZONTAL ? i.clearRect(Math.min(b, g), this.layout_.getPlotArea().y, Math.abs(b - g), this.layout_.getPlotArea().h) : f == Dygraph.VERTICAL && i.clearRect(this.layout_.getPlotArea().x, Math.min(d, h), this.layout_.getPlotArea().w, Math.abs(d - h)), a == Dygraph.HORIZONTAL ? c && b && (i.fillStyle = "rgba(128,128,128,0.33)", i.fillRect(Math.min(b, c), this.layout_.getPlotArea().y, Math.abs(c - b), this.layout_.getPlotArea().h)) : a == Dygraph.VERTICAL && e && d && (i.fillStyle = "rgba(128,128,128,0.33)", i.fillRect(this.layout_.getPlotArea().x, Math.min(d, e), this.layout_.getPlotArea().w, Math.abs(e - d))), this.isUsingExcanvas_ && (this.currentZoomRectArgs_ = [a, b, c, d, e, 0, 0, 0])
}, Dygraph.prototype.clearZoomRect_ = function() {
    this.currentZoomRectArgs_ = null, this.canvas_ctx_.clearRect(0, 0, this.canvas_.width, this.canvas_.height)
}, Dygraph.prototype.doZoomX_ = function(a, b) {
    this.currentZoomRectArgs_ = null;
    var c = this.toDataXCoord(a),
        d = this.toDataXCoord(b);
    this.doZoomXDates_(c, d)
}, Dygraph.zoomAnimationFunction = function(a, b) {
    var c = 1.5;
    return (1 - Math.pow(c, -a)) / (1 - Math.pow(c, -b))
}, Dygraph.prototype.doZoomXDates_ = function(a, b) {
    var c = this.xAxisRange(),
        d = [a, b];
    this.zoomed_x_ = !0;
    var e = this;
    this.doAnimatedZoom(c, d, null, null, function() {
        e.attr_("zoomCallback") && e.attr_("zoomCallback")(a, b, e.yAxisRanges())
    })
}, Dygraph.prototype.doZoomY_ = function(a, b) {
    this.currentZoomRectArgs_ = null;
    for (var c = this.yAxisRanges(), d = [], e = 0; e < this.axes_.length; e++) {
        var f = this.toDataYCoord(a, e),
            g = this.toDataYCoord(b, e);
        d.push([g, f])
    }
    this.zoomed_y_ = !0;
    var h = this;
    this.doAnimatedZoom(null, null, c, d, function() {
        if (h.attr_("zoomCallback")) {
            var a = h.xAxisRange();
            h.attr_("zoomCallback")(a[0], a[1], h.yAxisRanges())
        }
    })
}, Dygraph.prototype.resetZoom = function() {
    var a = !1,
        b = !1,
        c = !1;
    null !== this.dateWindow_ && (a = !0, b = !0);
    for (var d = 0; d < this.axes_.length; d++) "undefined" != typeof this.axes_[d].valueWindow && null !== this.axes_[d].valueWindow && (a = !0, c = !0);
    if (this.clearSelection(), a) {
        this.zoomed_x_ = !1, this.zoomed_y_ = !1;
        var e = this.rawData_[0][0],
            f = this.rawData_[this.rawData_.length - 1][0];
        if (!this.attr_("animatedZooms")) {
            for (this.dateWindow_ = null, d = 0; d < this.axes_.length; d++) null !== this.axes_[d].valueWindow && delete this.axes_[d].valueWindow;
            return this.drawGraph_(), void(this.attr_("zoomCallback") && this.attr_("zoomCallback")(e, f, this.yAxisRanges()))
        }
        var g = null,
            h = null,
            i = null,
            j = null;
        if (b && (g = this.xAxisRange(), h = [e, f]), c) {
            i = this.yAxisRanges();
            var k = this.gatherDatasets_(this.rolledSeries_, null),
                l = k.extremes;
            for (this.computeYAxisRanges_(l), j = [], d = 0; d < this.axes_.length; d++) {
                var m = this.axes_[d];
                j.push(null !== m.valueRange && void 0 !== m.valueRange ? m.valueRange : m.extremeRange)
            }
        }
        var n = this;
        this.doAnimatedZoom(g, h, i, j, function() {
            n.dateWindow_ = null;
            for (var a = 0; a < n.axes_.length; a++) null !== n.axes_[a].valueWindow && delete n.axes_[a].valueWindow;
            n.attr_("zoomCallback") && n.attr_("zoomCallback")(e, f, n.yAxisRanges())
        })
    }
}, Dygraph.prototype.doAnimatedZoom = function(a, b, c, d, e) {
    var f, g, h = this.attr_("animatedZooms") ? Dygraph.ANIMATION_STEPS : 1,
        i = [],
        j = [];
    if (null !== a && null !== b)
        for (f = 1; h >= f; f++) g = Dygraph.zoomAnimationFunction(f, h), i[f - 1] = [a[0] * (1 - g) + g * b[0], a[1] * (1 - g) + g * b[1]];
    if (null !== c && null !== d)
        for (f = 1; h >= f; f++) {
            g = Dygraph.zoomAnimationFunction(f, h);
            for (var k = [], l = 0; l < this.axes_.length; l++) k.push([c[l][0] * (1 - g) + g * d[l][0], c[l][1] * (1 - g) + g * d[l][1]]);
            j[f - 1] = k
        }
    var m = this;
    Dygraph.repeatAndCleanup(function(a) {
        if (j.length)
            for (var b = 0; b < m.axes_.length; b++) {
                var c = j[a][b];
                m.axes_[b].valueWindow = [c[0], c[1]]
            }
        i.length && (m.dateWindow_ = i[a]), m.drawGraph_()
    }, h, Dygraph.ANIMATION_DURATION / h, e)
}, Dygraph.prototype.getArea = function() {
    return this.plotter_.area
}, Dygraph.prototype.eventToDomCoords = function(a) {
    if (a.offsetX && a.offsetY) return [a.offsetX, a.offsetY];
    var b = Dygraph.pageX(a) - Dygraph.findPosX(this.mouseEventElement_),
        c = Dygraph.pageY(a) - Dygraph.findPosY(this.mouseEventElement_);
    return [b, c]
}, Dygraph.prototype.findClosestRow = function(a) {
    for (var b = 1 / 0, c = -1, d = this.layout_.points, e = 0; e < d.length; e++)
        for (var f = d[e], g = f.length, h = 0; g > h; h++) {
            var i = f[h];
            if (Dygraph.isValidPoint(i, !0)) {
                var j = Math.abs(i.canvasx - a);
                b > j && (b = j, c = i.idx)
            }
        }
    return c
}, Dygraph.prototype.findClosestPoint = function(a, b) {
    for (var c, d, e, f, g, h, i, j = 1 / 0, k = this.layout_.points.length - 1; k >= 0; --k)
        for (var l = this.layout_.points[k], m = 0; m < l.length; ++m) f = l[m], Dygraph.isValidPoint(f) && (d = f.canvasx - a, e = f.canvasy - b, c = d * d + e * e, j > c && (j = c, g = f, h = k, i = f.idx));
    var n = this.layout_.setNames[h];
    return {
        "row": i,
        "seriesName": n,
        "point": g
    }
}, Dygraph.prototype.findStackedPoint = function(a, b) {
    for (var c, d, e = this.findClosestRow(a), f = 0; f < this.layout_.points.length; ++f) {
        var g = this.getLeftBoundary_(f),
            h = e - g,
            i = this.layout_.points[f];
        if (!(h >= i.length)) {
            var j = i[h];
            if (Dygraph.isValidPoint(j)) {
                var k = j.canvasy;
                if (a > j.canvasx && h + 1 < i.length) {
                    var l = i[h + 1];
                    if (Dygraph.isValidPoint(l)) {
                        var m = l.canvasx - j.canvasx;
                        if (m > 0) {
                            var n = (a - j.canvasx) / m;
                            k += n * (l.canvasy - j.canvasy)
                        }
                    }
                } else if (a < j.canvasx && h > 0) {
                    var o = i[h - 1];
                    if (Dygraph.isValidPoint(o)) {
                        var m = j.canvasx - o.canvasx;
                        if (m > 0) {
                            var n = (j.canvasx - a) / m;
                            k += n * (o.canvasy - j.canvasy)
                        }
                    }
                }(0 === f || b > k) && (c = j, d = f)
            }
        }
    }
    var p = this.layout_.setNames[d];
    return {
        "row": e,
        "seriesName": p,
        "point": c
    }
}, Dygraph.prototype.mouseMove_ = function(a) {
    var b = this.layout_.points;
    if (void 0 !== b && null !== b) {
        var c = this.eventToDomCoords(a),
            d = c[0],
            e = c[1],
            f = this.attr_("highlightSeriesOpts"),
            g = !1;
        if (f && !this.isSeriesLocked()) {
            var h;
            h = this.attr_("stackedGraph") ? this.findStackedPoint(d, e) : this.findClosestPoint(d, e), g = this.setSelection(h.row, h.seriesName)
        } else {
            var i = this.findClosestRow(d);
            g = this.setSelection(i)
        }
        var j = this.attr_("highlightCallback");
        j && g && j(a, this.lastx_, this.selPoints_, this.lastRow_, this.highlightSet_)
    }
}, Dygraph.prototype.getLeftBoundary_ = function(a) {
    if (this.boundaryIds_[a]) return this.boundaryIds_[a][0];
    for (var b = 0; b < this.boundaryIds_.length; b++)
        if (void 0 !== this.boundaryIds_[b]) return this.boundaryIds_[b][0];
    return 0
}, Dygraph.prototype.animateSelection_ = function(a) {
    var b = 10,
        c = 30;
    void 0 === this.fadeLevel && (this.fadeLevel = 0), void 0 === this.animateId && (this.animateId = 0);
    var d = this.fadeLevel,
        e = 0 > a ? d : b - d;
    if (0 >= e) return void(this.fadeLevel && this.updateSelection_(1));
    var f = ++this.animateId,
        g = this;
    Dygraph.repeatAndCleanup(function() {
        g.animateId == f && (g.fadeLevel += a, 0 === g.fadeLevel ? g.clearSelection() : g.updateSelection_(g.fadeLevel / b))
    }, e, c, function() {})
}, Dygraph.prototype.updateSelection_ = function(a) {
    this.cascadeEvents_("select", {
        "selectedX": this.lastx_,
        "selectedPoints": this.selPoints_
    });
    var b, c = this.canvas_ctx_;
    if (this.attr_("highlightSeriesOpts")) {
        c.clearRect(0, 0, this.width_, this.height_);
        var d = 1 - this.attr_("highlightSeriesBackgroundAlpha");
        if (d) {
            var e = !0;
            if (e) {
                if (void 0 === a) return void this.animateSelection_(1);
                d *= a
            }
            c.fillStyle = "rgba(255,255,255," + d + ")", c.fillRect(0, 0, this.width_, this.height_)
        }
        this.plotter_._renderLineChart(this.highlightSet_, c)
    } else if (this.previousVerticalX_ >= 0) {
        var f = 0,
            g = this.attr_("labels");
        for (b = 1; b < g.length; b++) {
            var h = this.attr_("highlightCircleSize", g[b]);
            h > f && (f = h)
        }
        var i = this.previousVerticalX_;
        c.clearRect(i - f - 1, 0, 2 * f + 2, this.height_)
    }
    if (this.isUsingExcanvas_ && this.currentZoomRectArgs_ && Dygraph.prototype.drawZoomRect_.apply(this, this.currentZoomRectArgs_), this.selPoints_.length > 0) {
        var j = this.selPoints_[0].canvasx;
        for (c.save(), b = 0; b < this.selPoints_.length; b++) {
            var k = this.selPoints_[b];
            if (Dygraph.isOK(k.canvasy)) {
                var l = this.attr_("highlightCircleSize", k.name),
                    m = this.attr_("drawHighlightPointCallback", k.name),
                    n = this.plotter_.colors[k.name];
                m || (m = Dygraph.Circles.DEFAULT), c.lineWidth = this.attr_("strokeWidth", k.name), c.strokeStyle = n, c.fillStyle = n, m(this.g, k.name, c, j, k.canvasy, n, l, k.idx)
            }
        }
        c.restore(), this.previousVerticalX_ = j
    }
}, Dygraph.prototype.setSelection = function(a, b, c) {
    this.selPoints_ = [];
    var d = !1;
    if (a !== !1 && a >= 0) {
        a != this.lastRow_ && (d = !0), this.lastRow_ = a;
        for (var e = 0; e < this.layout_.points.length; ++e) {
            var f = this.layout_.points[e],
                g = a - this.getLeftBoundary_(e);
            if (g < f.length) {
                var h = f[g];
                null !== h.yval && this.selPoints_.push(h)
            }
        }
    } else this.lastRow_ >= 0 && (d = !0), this.lastRow_ = -1;
    return this.lastx_ = this.selPoints_.length ? this.selPoints_[0].xval : -1, void 0 !== b && (this.highlightSet_ !== b && (d = !0), this.highlightSet_ = b), void 0 !== c && (this.lockedSet_ = c), d && this.updateSelection_(void 0), d
}, Dygraph.prototype.mouseOut_ = function(a) {
    this.attr_("unhighlightCallback") && this.attr_("unhighlightCallback")(a), this.attr_("hideOverlayOnMouseOut") && !this.lockedSet_ && this.clearSelection()
}, Dygraph.prototype.clearSelection = function() {
    return this.cascadeEvents_("deselect", {}), this.lockedSet_ = !1, this.fadeLevel ? void this.animateSelection_(-1) : (this.canvas_ctx_.clearRect(0, 0, this.width_, this.height_), this.fadeLevel = 0, this.selPoints_ = [], this.lastx_ = -1, this.lastRow_ = -1, void(this.highlightSet_ = null))
}, Dygraph.prototype.getSelection = function() {
    if (!this.selPoints_ || this.selPoints_.length < 1) return -1;
    for (var a = 0; a < this.layout_.points.length; a++)
        for (var b = this.layout_.points[a], c = 0; c < b.length; c++)
            if (b[c].x == this.selPoints_[0].x) return b[c].idx;
    return -1
}, Dygraph.prototype.getHighlightSeries = function() {
    return this.highlightSet_
}, Dygraph.prototype.isSeriesLocked = function() {
    return this.lockedSet_
}, Dygraph.prototype.loadedEvent_ = function(a) {
    this.rawData_ = this.parseCSV_(a), this.predraw_()
}, Dygraph.prototype.addXTicks_ = function() {
    var a;
    a = this.dateWindow_ ? [this.dateWindow_[0], this.dateWindow_[1]] : this.xAxisExtremes();
    var b = this.optionsViewForAxis_("x"),
        c = b("ticker")(a[0], a[1], this.width_, b, this);
    this.layout_.setXTicks(c)
}, Dygraph.prototype.extremeValues_ = function(a) {
    var b, c, d = null,
        e = null,
        f = this.attr_("errorBars") || this.attr_("customBars");
    if (f) {
        for (b = 0; b < a.length; b++)
            if (c = a[b][1][0], null !== c && !isNaN(c)) {
                var g = c - a[b][1][1],
                    h = c + a[b][1][2];
                g > c && (g = c), c > h && (h = c), (null === e || h > e) && (e = h), (null === d || d > g) && (d = g)
            }
    } else
        for (b = 0; b < a.length; b++) c = a[b][1], null === c || isNaN(c) || ((null === e || c > e) && (e = c), (null === d || d > c) && (d = c));
    return [d, e]
}, Dygraph.prototype.predraw_ = function() {
    var a = new Date;
    this.layout_.computePlotArea(), this.computeYAxes_(), this.plotter_ && (this.cascadeEvents_("clearChart"), this.plotter_.clear()), this.is_initial_draw_ || (this.canvas_ctx_.restore(), this.hidden_ctx_.restore()), this.canvas_ctx_.save(), this.hidden_ctx_.save(), this.plotter_ = new DygraphCanvasRenderer(this, this.hidden_, this.hidden_ctx_, this.layout_), this.createRollInterface_(), this.cascadeEvents_("predraw"), this.rolledSeries_ = [null];
    for (var b = 1; b < this.numColumns(); b++) {
        var c = this.attr_("logscale"),
            d = this.extractSeries_(this.rawData_, b, c);
        d = this.rollingAverage(d, this.rollPeriod_), this.rolledSeries_.push(d)
    }
    this.drawGraph_();
    var e = new Date;
    this.drawingTimeMs_ = e - a
}, Dygraph.PointType = void 0, Dygraph.seriesToPoints_ = function(a, b, c, d) {
    for (var e = [], f = 0; f < a.length; ++f) {
        var g = a[f],
            h = b ? g[1][0] : g[1],
            i = null === h ? null : DygraphLayout.parseFloat_(h),
            j = {
                "x": 0 / 0,
                "y": 0 / 0,
                "xval": DygraphLayout.parseFloat_(g[0]),
                "yval": i,
                "name": c,
                "idx": f + d
            };
        b && (j.y_top = 0 / 0, j.y_bottom = 0 / 0, j.yval_minus = DygraphLayout.parseFloat_(g[1][1]), j.yval_plus = DygraphLayout.parseFloat_(g[1][2])), e.push(j)
    }
    return e
}, Dygraph.stackPoints_ = function(a, b, c, d) {
    for (var e = null, f = null, g = null, h = -1, i = function(b) {
            if (!(h >= b))
                for (var c = b; c < a.length; ++c)
                    if (g = null, !isNaN(a[c].yval) && null !== a[c].yval) {
                        h = c, g = a[c];
                        break
                    }
        }, j = 0; j < a.length; ++j) {
        var k = a[j],
            l = k.xval;
        void 0 === b[l] && (b[l] = 0);
        var m = k.yval;
        isNaN(m) || null === m ? (i(j), m = f && g && "none" != d ? f.yval + (g.yval - f.yval) * ((l - f.xval) / (g.xval - f.xval)) : f && "all" == d ? f.yval : g && "all" == d ? g.yval : 0) : f = k;
        var n = b[l];
        e != l && (n += m, b[l] = n), e = l, k.yval_stacked = n, n > c[1] && (c[1] = n), n < c[0] && (c[0] = n)
    }
}, Dygraph.prototype.gatherDatasets_ = function(a, b) {
    var c, d, e, f = [],
        g = [],
        h = [],
        i = {},
        j = this.attr_("errorBars"),
        k = this.attr_("customBars"),
        l = j || k,
        m = function(a) {
            return l ? k ? null === a[1][1] : j ? null === a[1][0] : !1 : null === a[1]
        },
        n = a.length - 1;
    for (c = n; c >= 1; c--)
        if (this.visibility()[c - 1]) {
            if (b) {
                e = a[c];
                var o = b[0],
                    p = b[1],
                    q = null,
                    r = null;
                for (d = 0; d < e.length; d++) e[d][0] >= o && null === q && (q = d), e[d][0] <= p && (r = d);
                null === q && (q = 0);
                for (var s = q, t = !0; t && s > 0;) s--, t = m(e[s]);
                null === r && (r = e.length - 1);
                var u = r;
                for (t = !0; t && u < e.length - 1;) u++, t = m(e[u]);
                s !== q && (q = s), u !== r && (r = u), f[c - 1] = [q, r], e = e.slice(q, r + 1)
            } else e = a[c], f[c - 1] = [0, e.length - 1];
            var v = this.attr_("labels")[c],
                w = this.extremeValues_(e),
                x = Dygraph.seriesToPoints_(e, l, v, f[c - 1][0]);
            this.attr_("stackedGraph") && Dygraph.stackPoints_(x, h, w, this.attr_("stackedGraphNaNFill")), i[v] = w, g[c] = x
        }
    return {
        "points": g,
        "extremes": i,
        "boundaryIds": f
    }
}, Dygraph.prototype.drawGraph_ = function() {
    var a = new Date,
        b = this.is_initial_draw_;
    this.is_initial_draw_ = !1, this.layout_.removeAllDatasets(), this.setColors_(), this.attrs_.pointSize = .5 * this.attr_("highlightCircleSize");
    var c = this.gatherDatasets_(this.rolledSeries_, this.dateWindow_),
        d = c.points,
        e = c.extremes;
    this.boundaryIds_ = c.boundaryIds, this.setIndexByName_ = {};
    var f = this.attr_("labels");
    f.length > 0 && (this.setIndexByName_[f[0]] = 0);
    for (var g = 0, h = 1; h < d.length; h++) this.setIndexByName_[f[h]] = h, this.visibility()[h - 1] && (this.layout_.addDataset(f[h], d[h]), this.datasetIndex_[h] = g++);
    this.computeYAxisRanges_(e), this.layout_.setYAxes(this.axes_), this.addXTicks_();
    var i = this.zoomed_x_;
    if (this.zoomed_x_ = i, this.layout_.evaluate(), this.renderGraph_(b), this.attr_("timingName")) {
        var j = new Date;
        Dygraph.info(this.attr_("timingName") + " - drawGraph: " + (j - a) + "ms")
    }
}, Dygraph.prototype.renderGraph_ = function(a) {
    this.cascadeEvents_("clearChart"), this.plotter_.clear(), this.attr_("underlayCallback") && this.attr_("underlayCallback")(this.hidden_ctx_, this.layout_.getPlotArea(), this, this);
    var b = {
        "canvas": this.hidden_,
        "drawingContext": this.hidden_ctx_
    };
    if (this.cascadeEvents_("willDrawChart", b), this.plotter_.render(), this.cascadeEvents_("didDrawChart", b), this.lastRow_ = -1, this.canvas_.getContext("2d").clearRect(0, 0, this.canvas_.width, this.canvas_.height), null !== this.attr_("drawCallback") && this.attr_("drawCallback")(this, a), a)
        for (this.readyFired_ = !0; this.readyFns_.length > 0;) {
            var c = this.readyFns_.pop();
            c(this)
        }
}, Dygraph.prototype.computeYAxes_ = function() {
    var a, b, c, d, e;
    if (void 0 !== this.axes_ && this.user_attrs_.hasOwnProperty("valueRange") === !1)
        for (a = [], c = 0; c < this.axes_.length; c++) a.push(this.axes_[c].valueWindow);
    for (this.axes_ = [], b = 0; b < this.attributes_.numAxes(); b++) d = {
        "g": this
    }, Dygraph.update(d, this.attributes_.axisOptions(b)), this.axes_[b] = d;
    if (e = this.attr_("valueRange"), e && (this.axes_[0].valueRange = e), void 0 !== a) {
        var f = Math.min(a.length, this.axes_.length);
        for (c = 0; f > c; c++) this.axes_[c].valueWindow = a[c]
    }
    for (b = 0; b < this.axes_.length; b++)
        if (0 === b) d = this.optionsViewForAxis_("y" + (b ? "2" : "")), e = d("valueRange"), e && (this.axes_[b].valueRange = e);
        else {
            var g = this.user_attrs_.axes;
            g && g.y2 && (e = g.y2.valueRange, e && (this.axes_[b].valueRange = e))
        }
}, Dygraph.prototype.numAxes = function() {
    return this.attributes_.numAxes()
}, Dygraph.prototype.axisPropertiesForSeries = function(a) {
    return this.axes_[this.attributes_.axisForSeries(a)]
}, Dygraph.prototype.computeYAxisRanges_ = function(a) {
    for (var b, c, d, e, f, g = function(a) {
            return isNaN(parseFloat(a))
        }, h = this.attributes_.numAxes(), i = 0; h > i; i++) {
        var j = this.axes_[i],
            k = this.attributes_.getForAxis("logscale", i),
            l = this.attributes_.getForAxis("includeZero", i),
            m = this.attributes_.getForAxis("independentTicks", i);
        if (d = this.attributes_.seriesForAxis(i), b = !0, e = .1, null !== this.attr_("yRangePad") && (b = !1, e = this.attr_("yRangePad") / this.plotter_.area.h), 0 === d.length) j.extremeRange = [0, 1];
        else {
            for (var n, o, p = 1 / 0, q = -1 / 0, r = 0; r < d.length; r++) a.hasOwnProperty(d[r]) && (n = a[d[r]][0], null !== n && (p = Math.min(n, p)), o = a[d[r]][1], null !== o && (q = Math.max(o, q)));
            l && !k && (p > 0 && (p = 0), 0 > q && (q = 0)), 1 / 0 == p && (p = 0), q == -1 / 0 && (q = 1), c = q - p, 0 === c && (0 !== q ? c = Math.abs(q) : (q = 1, c = 1));
            var s, t;
            if (k)
                if (b) s = q + e * c, t = p;
                else {
                    var u = Math.exp(Math.log(c) * e);
                    s = q * u, t = p / u
                } else s = q + e * c, t = p - e * c, b && !this.attr_("avoidMinZero") && (0 > t && p >= 0 && (t = 0), s > 0 && 0 >= q && (s = 0));
            j.extremeRange = [t, s]
        }
        if (j.valueWindow) j.computedValueRange = [j.valueWindow[0], j.valueWindow[1]];
        else if (j.valueRange) {
            var v = g(j.valueRange[0]) ? j.extremeRange[0] : j.valueRange[0],
                w = g(j.valueRange[1]) ? j.extremeRange[1] : j.valueRange[1];
            if (!b)
                if (j.logscale) {
                    var u = Math.exp(Math.log(c) * e);
                    v *= u, w /= u
                } else c = w - v, v -= c * e, w += c * e;
            j.computedValueRange = [v, w]
        } else j.computedValueRange = j.extremeRange;
        if (m) {
            j.independentTicks = m;
            var x = this.optionsViewForAxis_("y" + (i ? "2" : "")),
                y = x("ticker");
            j.ticks = y(j.computedValueRange[0], j.computedValueRange[1], this.height_, x, this), f || (f = j)
        }
    }
    if (void 0 === f) throw 'Configuration Error: At least one axis has to have the "independentTicks" option activated.';
    for (var i = 0; h > i; i++) {
        var j = this.axes_[i];
        if (!j.independentTicks) {
            for (var x = this.optionsViewForAxis_("y" + (i ? "2" : "")), y = x("ticker"), z = f.ticks, A = f.computedValueRange[1] - f.computedValueRange[0], B = j.computedValueRange[1] - j.computedValueRange[0], C = [], D = 0; D < z.length; D++) {
                var E = (z[D].v - f.computedValueRange[0]) / A,
                    F = j.computedValueRange[0] + E * B;
                C.push(F)
            }
            j.ticks = y(j.computedValueRange[0], j.computedValueRange[1], this.height_, x, this, C)
        }
    }
}, Dygraph.prototype.extractSeries_ = function(a, b, c) {
    for (var d = [], e = this.attr_("errorBars"), f = this.attr_("customBars"), g = 0; g < a.length; g++) {
        var h = a[g][0],
            i = a[g][b];
        if (c)
            if (e || f) {
                for (var j = 0; j < i.length; j++)
                    if (i[j] <= 0) {
                        i = null;
                        break
                    }
            } else 0 >= i && (i = null);
        d.push(null !== i ? [h, i] : [h, e ? [null, null] : f ? [null, null, null] : i])
    }
    return d
}, Dygraph.prototype.rollingAverage = function(a, b) {
    b = Math.min(b, a.length);
    var c, d, e, f, g, h, i, j, k = [],
        l = this.attr_("sigma");
    if (this.fractions_) {
        var m = 0,
            n = 0,
            o = 100;
        for (e = 0; e < a.length; e++) {
            m += a[e][1][0], n += a[e][1][1], e - b >= 0 && (m -= a[e - b][1][0], n -= a[e - b][1][1]);
            var p = a[e][0],
                q = n ? m / n : 0;
            if (this.attr_("errorBars"))
                if (this.attr_("wilsonInterval"))
                    if (n) {
                        var r = 0 > q ? 0 : q,
                            s = n,
                            t = l * Math.sqrt(r * (1 - r) / s + l * l / (4 * s * s)),
                            u = 1 + l * l / n;
                        c = (r + l * l / (2 * n) - t) / u, d = (r + l * l / (2 * n) + t) / u, k[e] = [p, [r * o, (r - c) * o, (d - r) * o]]
                    } else k[e] = [p, [0, 0, 0]];
            else j = n ? l * Math.sqrt(q * (1 - q) / n) : 1, k[e] = [p, [o * q, o * j, o * j]];
            else k[e] = [p, o * q]
        }
    } else if (this.attr_("customBars")) {
        c = 0;
        var v = 0;
        d = 0;
        var w = 0;
        for (e = 0; e < a.length; e++) {
            var x = a[e][1];
            if (g = x[1], k[e] = [a[e][0],
                    [g, g - x[0], x[2] - g]
                ], null === g || isNaN(g) || (c += x[0], v += g, d += x[2], w += 1), e - b >= 0) {
                var y = a[e - b];
                null === y[1][1] || isNaN(y[1][1]) || (c -= y[1][0], v -= y[1][1], d -= y[1][2], w -= 1)
            }
            k[e] = w ? [a[e][0],
                [1 * v / w, 1 * (v - c) / w, 1 * (d - v) / w]
            ] : [a[e][0],
                [null, null, null]
            ]
        }
    } else if (this.attr_("errorBars"))
        for (e = 0; e < a.length; e++) {
            h = 0;
            var z = 0;
            for (i = 0, f = Math.max(0, e - b + 1); e + 1 > f; f++) g = a[f][1][0], null === g || isNaN(g) || (i++, h += a[f][1][0], z += Math.pow(a[f][1][1], 2));
            if (i) j = Math.sqrt(z) / i, k[e] = [a[e][0],
                [h / i, l * j, l * j]
            ];
            else {
                var A = 1 == b ? a[e][1][0] : null;
                k[e] = [a[e][0],
                    [A, A, A]
                ]
            }
        } else {
            if (1 == b) return a;
            for (e = 0; e < a.length; e++) {
                for (h = 0, i = 0, f = Math.max(0, e - b + 1); e + 1 > f; f++) g = a[f][1], null === g || isNaN(g) || (i++, h += a[f][1]);
                k[e] = i ? [a[e][0], h / i] : [a[e][0], null]
            }
        }
    return k
}, Dygraph.prototype.detectTypeFromString_ = function(a) {
    var b = !1,
        c = a.indexOf("-");
    c > 0 && "e" != a[c - 1] && "E" != a[c - 1] || a.indexOf("/") >= 0 || isNaN(parseFloat(a)) ? b = !0 : 8 == a.length && a > "19700101" && "20371231" > a && (b = !0), this.setXAxisOptions_(b)
}, Dygraph.prototype.setXAxisOptions_ = function(a) {
    a ? (this.attrs_.xValueParser = Dygraph.dateParser, this.attrs_.axes.x.valueFormatter = Dygraph.dateString_, this.attrs_.axes.x.ticker = Dygraph.dateTicker, this.attrs_.axes.x.axisLabelFormatter = Dygraph.dateAxisFormatter) : (this.attrs_.xValueParser = function(a) {
        return parseFloat(a)
    }, this.attrs_.axes.x.valueFormatter = function(a) {
        return a
    }, this.attrs_.axes.x.ticker = Dygraph.numericLinearTicks, this.attrs_.axes.x.axisLabelFormatter = this.attrs_.axes.x.valueFormatter)
}, Dygraph.prototype.parseFloat_ = function(a, b, c) {
    var d = parseFloat(a);
    if (!isNaN(d)) return d;
    if (/^ *$/.test(a)) return null;
    if (/^ *nan *$/i.test(a)) return 0 / 0;
    var e = "Unable to parse '" + a + "' as a number";
    return null !== c && null !== b && (e += " on line " + (1 + b) + " ('" + c + "') of CSV."), this.error(e), null
}, Dygraph.prototype.parseCSV_ = function(a) {
    var b, c, d = [],
        e = Dygraph.detectLineDelimiter(a),
        f = a.split(e || "\n"),
        g = this.attr_("delimiter"); - 1 == f[0].indexOf(g) && f[0].indexOf("	") >= 0 && (g = "	");
    var h = 0;
    "labels" in this.user_attrs_ || (h = 1, this.attrs_.labels = f[0].split(g), this.attributes_.reparseSeries());
    for (var i, j = 0, k = !1, l = this.attr_("labels").length, m = !1, n = h; n < f.length; n++) {
        var o = f[n];
        if (j = n, 0 !== o.length && "#" != o[0]) {
            var p = o.split(g);
            if (!(p.length < 2)) {
                var q = [];
                if (k || (this.detectTypeFromString_(p[0]), i = this.attr_("xValueParser"), k = !0), q[0] = i(p[0], this), this.fractions_)
                    for (c = 1; c < p.length; c++) b = p[c].split("/"), 2 != b.length ? (this.error('Expected fractional "num/den" values in CSV data but found a value \'' + p[c] + "' on line " + (1 + n) + " ('" + o + "') which is not of this form."), q[c] = [0, 0]) : q[c] = [this.parseFloat_(b[0], n, o), this.parseFloat_(b[1], n, o)];
                else if (this.attr_("errorBars"))
                    for (p.length % 2 != 1 && this.error("Expected alternating (value, stdev.) pairs in CSV data but line " + (1 + n) + " has an odd number of values (" + (p.length - 1) + "): '" + o + "'"), c = 1; c < p.length; c += 2) q[(c + 1) / 2] = [this.parseFloat_(p[c], n, o), this.parseFloat_(p[c + 1], n, o)];
                else if (this.attr_("customBars"))
                    for (c = 1; c < p.length; c++) {
                        var r = p[c];
                        /^ *$/.test(r) ? q[c] = [null, null, null] : (b = r.split(";"), 3 == b.length ? q[c] = [this.parseFloat_(b[0], n, o), this.parseFloat_(b[1], n, o), this.parseFloat_(b[2], n, o)] : this.warn('When using customBars, values must be either blank or "low;center;high" tuples (got "' + r + '" on line ' + (1 + n)))
                    } else
                        for (c = 1; c < p.length; c++) q[c] = this.parseFloat_(p[c], n, o);
                if (d.length > 0 && q[0] < d[d.length - 1][0] && (m = !0), q.length != l && this.error("Number of columns in line " + n + " (" + q.length + ") does not agree with number of labels (" + l + ") " + o), 0 === n && this.attr_("labels")) {
                    var s = !0;
                    for (c = 0; s && c < q.length; c++) q[c] && (s = !1);
                    if (s) {
                        this.warn("The dygraphs 'labels' option is set, but the first row of CSV data ('" + o + "') appears to also contain labels. Will drop the CSV labels and use the option labels.");
                        continue
                    }
                }
                d.push(q)
            }
        }
    }
    return m && (this.warn("CSV is out of order; order it correctly to speed loading."), d.sort(function(a, b) {
        return a[0] - b[0]
    })), d
}, Dygraph.prototype.parseArray_ = function(a) {
    if (0 === a.length) return this.error("Can't plot empty data set"), null;
    if (0 === a[0].length) return this.error("Data set cannot contain an empty row"), null;
    var b;
    if (null === this.attr_("labels")) {
        for (this.warn("Using default labels. Set labels explicitly via 'labels' in the options parameter"), this.attrs_.labels = ["X"], b = 1; b < a[0].length; b++) this.attrs_.labels.push("Y" + b);
        this.attributes_.reparseSeries()
    } else {
        var c = this.attr_("labels");
        if (c.length != a[0].length) return this.error("Mismatch between number of labels (" + c + ") and number of columns in array (" + a[0].length + ")"), null
    }
    if (Dygraph.isDateLike(a[0][0])) {
        this.attrs_.axes.x.valueFormatter = Dygraph.dateString_, this.attrs_.axes.x.ticker = Dygraph.dateTicker, this.attrs_.axes.x.axisLabelFormatter = Dygraph.dateAxisFormatter;
        var d = Dygraph.clone(a);
        for (b = 0; b < a.length; b++) {
            if (0 === d[b].length) return this.error("Row " + (1 + b) + " of data is empty"), null;
            if (null === d[b][0] || "function" != typeof d[b][0].getTime || isNaN(d[b][0].getTime())) return this.error("x value in row " + (1 + b) + " is not a Date"), null;
            d[b][0] = d[b][0].getTime()
        }
        return d
    }
    return this.attrs_.axes.x.valueFormatter = function(a) {
        return a
    }, this.attrs_.axes.x.ticker = Dygraph.numericLinearTicks, this.attrs_.axes.x.axisLabelFormatter = Dygraph.numberAxisLabelFormatter, a
}, Dygraph.prototype.parseDataTable_ = function(a) {
    var b = function(a) {
            var b = String.fromCharCode(65 + a % 26);
            for (a = Math.floor(a / 26); a > 0;) b = String.fromCharCode(65 + (a - 1) % 26) + b.toLowerCase(), a = Math.floor((a - 1) / 26);
            return b
        },
        c = a.getNumberOfColumns(),
        d = a.getNumberOfRows(),
        e = a.getColumnType(0);
    if ("date" == e || "datetime" == e) this.attrs_.xValueParser = Dygraph.dateParser, this.attrs_.axes.x.valueFormatter = Dygraph.dateString_, this.attrs_.axes.x.ticker = Dygraph.dateTicker, this.attrs_.axes.x.axisLabelFormatter = Dygraph.dateAxisFormatter;
    else {
        if ("number" != e) return this.error("only 'date', 'datetime' and 'number' types are supported for column 1 of DataTable input (Got '" + e + "')"), null;
        this.attrs_.xValueParser = function(a) {
            return parseFloat(a)
        }, this.attrs_.axes.x.valueFormatter = function(a) {
            return a
        }, this.attrs_.axes.x.ticker = Dygraph.numericLinearTicks, this.attrs_.axes.x.axisLabelFormatter = this.attrs_.axes.x.valueFormatter
    }
    var f, g, h = [],
        i = {},
        j = !1;
    for (f = 1; c > f; f++) {
        var k = a.getColumnType(f);
        if ("number" == k) h.push(f);
        else if ("string" == k && this.attr_("displayAnnotations")) {
            var l = h[h.length - 1];
            i.hasOwnProperty(l) ? i[l].push(f) : i[l] = [f], j = !0
        } else this.error("Only 'number' is supported as a dependent type with Gviz. 'string' is only supported if displayAnnotations is true")
    }
    var m = [a.getColumnLabel(0)];
    for (f = 0; f < h.length; f++) m.push(a.getColumnLabel(h[f])), this.attr_("errorBars") && (f += 1);
    this.attrs_.labels = m, c = m.length;
    var n = [],
        o = !1,
        p = [];
    for (f = 0; d > f; f++) {
        var q = [];
        if ("undefined" != typeof a.getValue(f, 0) && null !== a.getValue(f, 0)) {
            if (q.push("date" == e || "datetime" == e ? a.getValue(f, 0).getTime() : a.getValue(f, 0)), this.attr_("errorBars"))
                for (g = 0; c - 1 > g; g++) q.push([a.getValue(f, 1 + 2 * g), a.getValue(f, 2 + 2 * g)]);
            else {
                for (g = 0; g < h.length; g++) {
                    var r = h[g];
                    if (q.push(a.getValue(f, r)), j && i.hasOwnProperty(r) && null !== a.getValue(f, i[r][0])) {
                        var s = {};
                        s.series = a.getColumnLabel(r), s.xval = q[0], s.shortText = b(p.length), s.text = "";
                        for (var t = 0; t < i[r].length; t++) t && (s.text += "\n"), s.text += a.getValue(f, i[r][t]);
                        p.push(s)
                    }
                }
                for (g = 0; g < q.length; g++) isFinite(q[g]) || (q[g] = null)
            }
            n.length > 0 && q[0] < n[n.length - 1][0] && (o = !0), n.push(q)
        } else this.warn("Ignoring row " + f + " of DataTable because of undefined or null first column.")
    }
    o && (this.warn("DataTable is out of order; order it correctly to speed loading."), n.sort(function(a, b) {
        return a[0] - b[0]
    })), this.rawData_ = n, p.length > 0 && this.setAnnotations(p, !0), this.attributes_.reparseSeries()
}, Dygraph.prototype.start_ = function() {
    var a = this.file_;
    if ("function" == typeof a && (a = a()), Dygraph.isArrayLike(a)) this.rawData_ = this.parseArray_(a), this.predraw_();
    else if ("object" == typeof a && "function" == typeof a.getColumnRange) this.parseDataTable_(a), this.predraw_();
    else if ("string" == typeof a) {
        var b = Dygraph.detectLineDelimiter(a);
        if (b) this.loadedEvent_(a);
        else {
            var c;
            c = window.XMLHttpRequest ? new XMLHttpRequest : new ActiveXObject("Microsoft.XMLHTTP");
            var d = this;
            c.onreadystatechange = function() {
                4 == c.readyState && (200 === c.status || 0 === c.status) && d.loadedEvent_(c.responseText)
            }, c.open("GET", a, !0), c.send(null)
        }
    } else this.error("Unknown data format: " + typeof a)
}, Dygraph.prototype.updateOptions = function(a, b) {
    "undefined" == typeof b && (b = !1);
    var c = a.file,
        d = Dygraph.mapLegacyOptions_(a);
    "rollPeriod" in d && (this.rollPeriod_ = d.rollPeriod), "dateWindow" in d && (this.dateWindow_ = d.dateWindow, "isZoomedIgnoreProgrammaticZoom" in d || (this.zoomed_x_ = null !== d.dateWindow)), "valueRange" in d && !("isZoomedIgnoreProgrammaticZoom" in d) && (this.zoomed_y_ = null !== d.valueRange);
    var e = Dygraph.isPixelChangingOptionList(this.attr_("labels"), d);
    Dygraph.updateDeep(this.user_attrs_, d), this.attributes_.reparseSeries(), c ? (this.file_ = c, b || this.start_()) : b || (e ? this.predraw_() : this.renderGraph_(!1))
}, Dygraph.mapLegacyOptions_ = function(a) {
    var b = {};
    for (var c in a) "file" != c && a.hasOwnProperty(c) && (b[c] = a[c]);
    var d = function(a, c, d) {
            b.axes || (b.axes = {}), b.axes[a] || (b.axes[a] = {}), b.axes[a][c] = d
        },
        e = function(c, e, f) {
            "undefined" != typeof a[c] && (Dygraph.warn("Option " + c + " is deprecated. Use the " + f + " option for the " + e + " axis instead. (e.g. { axes : { " + e + " : { " + f + " : ... } } } (see http://dygraphs.com/per-axis.html for more information."), d(e, f, a[c]), delete b[c])
        };
    return e("xValueFormatter", "x", "valueFormatter"), e("pixelsPerXLabel", "x", "pixelsPerLabel"), e("xAxisLabelFormatter", "x", "axisLabelFormatter"), e("xTicker", "x", "ticker"), e("yValueFormatter", "y", "valueFormatter"), e("pixelsPerYLabel", "y", "pixelsPerLabel"), e("yAxisLabelFormatter", "y", "axisLabelFormatter"), e("yTicker", "y", "ticker"), b
}, Dygraph.prototype.resize = function(a, b) {
    if (!this.resize_lock) {
        this.resize_lock = !0, null === a != (null === b) && (this.warn("Dygraph.resize() should be called with zero parameters or two non-NULL parameters. Pretending it was zero."), a = b = null);
        var c = this.width_,
            d = this.height_;
        a ? (this.maindiv_.style.width = a + "px", this.maindiv_.style.height = b + "px", this.width_ = a, this.height_ = b) : (this.width_ = this.maindiv_.clientWidth, this.height_ = this.maindiv_.clientHeight), (c != this.width_ || d != this.height_) && (this.resizeElements_(), this.predraw_()), this.resize_lock = !1
    }
}, Dygraph.prototype.adjustRoll = function(a) {
    this.rollPeriod_ = a, this.predraw_()
}, Dygraph.prototype.visibility = function() {
    for (this.attr_("visibility") || (this.attrs_.visibility = []); this.attr_("visibility").length < this.numColumns() - 1;) this.attrs_.visibility.push(!0);
    return this.attr_("visibility")
}, Dygraph.prototype.setVisibility = function(a, b) {
    var c = this.visibility();
    0 > a || a >= c.length ? this.warn("invalid series number in setVisibility: " + a) : (c[a] = b, this.predraw_())
}, Dygraph.prototype.size = function() {
    return {
        "width": this.width_,
        "height": this.height_
    }
}, Dygraph.prototype.setAnnotations = function(a, b) {
    return Dygraph.addAnnotationRule(), this.annotations_ = a, this.layout_ ? (this.layout_.setAnnotations(this.annotations_), void(b || this.predraw_())) : void this.warn("Tried to setAnnotations before dygraph was ready. Try setting them in a ready() block. See dygraphs.com/tests/annotation.html")
}, Dygraph.prototype.annotations = function() {
    return this.annotations_
}, Dygraph.prototype.getLabels = function() {
    var a = this.attr_("labels");
    return a ? a.slice() : null
}, Dygraph.prototype.indexFromSetName = function(a) {
    return this.setIndexByName_[a]
}, Dygraph.prototype.ready = function(a) {
    this.is_initial_draw_ ? this.readyFns_.push(a) : a(this)
}, Dygraph.addAnnotationRule = function() {
    if (!Dygraph.addedAnnotationCSS) {
        var a = "border: 1px solid black; background-color: white; text-align: center;",
            b = document.createElement("style");
        b.type = "text/css", document.getElementsByTagName("head")[0].appendChild(b);
        for (var c = 0; c < document.styleSheets.length; c++)
            if (!document.styleSheets[c].disabled) {
                var d = document.styleSheets[c];
                try {
                    if (d.insertRule) {
                        var e = d.cssRules ? d.cssRules.length : 0;
                        d.insertRule(".dygraphDefaultAnnotation { " + a + " }", e)
                    } else d.addRule && d.addRule(".dygraphDefaultAnnotation", a);
                    return void(Dygraph.addedAnnotationCSS = !0)
                } catch (f) {}
            }
        this.warn("Unable to add default annotation CSS rule; display may be off.")
    }
};
var DateGraph = Dygraph;
Dygraph.LOG_SCALE = 10, Dygraph.LN_TEN = Math.log(Dygraph.LOG_SCALE), Dygraph.log10 = function(a) {
    return Math.log(a) / Dygraph.LN_TEN
}, Dygraph.DEBUG = 1, Dygraph.INFO = 2, Dygraph.WARNING = 3, Dygraph.ERROR = 3, Dygraph.LOG_STACK_TRACES = !1, Dygraph.DOTTED_LINE = [2, 2], Dygraph.DASHED_LINE = [7, 3], Dygraph.DOT_DASH_LINE = [7, 2, 2, 2], Dygraph.log = function(a, b) {
    var c;
    if ("undefined" != typeof printStackTrace) try {
        for (c = printStackTrace({
                "guess": !1
            }); - 1 != c[0].indexOf("stacktrace");) c.splice(0, 1);
        c.splice(0, 2);
        for (var d = 0; d < c.length; d++) c[d] = c[d].replace(/\([^)]*\/(.*)\)/, "@$1").replace(/\@.*\/([^\/]*)/, "@$1").replace("[object Object].", "");
        var e = c.splice(0, 1)[0];
        b += " (" + e.replace(/^.*@ ?/, "") + ")"
    } catch (f) {}
    if ("undefined" != typeof window.console) {
        var g = window.console,
            h = function(a, b, c) {
                b && "function" == typeof b ? b.call(a, c) : a.log(c)
            };
        switch (a) {
            case Dygraph.DEBUG:
                h(g, g.debug, "dygraphs: " + b);
                break;
            case Dygraph.INFO:
                h(g, g.info, "dygraphs: " + b);
                break;
            case Dygraph.WARNING:
                h(g, g.warn, "dygraphs: " + b);
                break;
            case Dygraph.ERROR:
                h(g, g.error, "dygraphs: " + b)
        }
    }
    Dygraph.LOG_STACK_TRACES && window.console.log(c.join("\n"))
}, Dygraph.info = function(a) {
    Dygraph.log(Dygraph.INFO, a)
}, Dygraph.prototype.info = Dygraph.info, Dygraph.warn = function(a) {
    Dygraph.log(Dygraph.WARNING, a)
}, Dygraph.prototype.warn = Dygraph.warn, Dygraph.error = function(a) {
    Dygraph.log(Dygraph.ERROR, a)
}, Dygraph.prototype.error = Dygraph.error, Dygraph.getContext = function(a) {
    return a.getContext("2d")
}, Dygraph.addEvent = function(a, b, c) {
    a.addEventListener ? a.addEventListener(b, c, !1) : (a[b + c] = function() {
        c(window.event)
    }, a.attachEvent("on" + b, a[b + c]))
}, Dygraph.prototype.addAndTrackEvent = function(a, b, c) {
    Dygraph.addEvent(a, b, c), this.registeredEvents_.push({
        "elem": a,
        "type": b,
        "fn": c
    })
}, Dygraph.removeEvent = function(a, b, c) {
    if (a.removeEventListener) a.removeEventListener(b, c, !1);
    else {
        try {
            a.detachEvent("on" + b, a[b + c])
        } catch (d) {}
        a[b + c] = null
    }
}, Dygraph.prototype.removeTrackedEvents_ = function() {
    if (this.registeredEvents_)
        for (var a = 0; a < this.registeredEvents_.length; a++) {
            var b = this.registeredEvents_[a];
            Dygraph.removeEvent(b.elem, b.type, b.fn)
        }
    this.registeredEvents_ = []
}, Dygraph.cancelEvent = function(a) {
    return a = a ? a : window.event, a.stopPropagation && a.stopPropagation(), a.preventDefault && a.preventDefault(), a.cancelBubble = !0, a.cancel = !0, a.returnValue = !1, !1
}, Dygraph.hsvToRGB = function(a, b, c) {
    var d, e, f;
    if (0 === b) d = c, e = c, f = c;
    else {
        var g = Math.floor(6 * a),
            h = 6 * a - g,
            i = c * (1 - b),
            j = c * (1 - b * h),
            k = c * (1 - b * (1 - h));
        switch (g) {
            case 1:
                d = j, e = c, f = i;
                break;
            case 2:
                d = i, e = c, f = k;
                break;
            case 3:
                d = i, e = j, f = c;
                break;
            case 4:
                d = k, e = i, f = c;
                break;
            case 5:
                d = c, e = i, f = j;
                break;
            case 6:
            case 0:
                d = c, e = k, f = i
        }
    }
    return d = Math.floor(255 * d + .5), e = Math.floor(255 * e + .5), f = Math.floor(255 * f + .5), "rgb(" + d + "," + e + "," + f + ")"
}, Dygraph.findPosX = function(a) {
    var b = 0;
    if (a.offsetParent)
        for (var c = a;;) {
            var d = "0";
            if (window.getComputedStyle && (d = window.getComputedStyle(c, null).borderLeft || "0"), b += parseInt(d, 10), b += c.offsetLeft, !c.offsetParent) break;
            c = c.offsetParent
        } else a.x && (b += a.x);
    for (; a && a != document.body;) b -= a.scrollLeft, a = a.parentNode;
    return b
}, Dygraph.findPosY = function(a) {
    var b = 0;
    if (a.offsetParent)
        for (var c = a;;) {
            var d = "0";
            if (window.getComputedStyle && (d = window.getComputedStyle(c, null).borderTop || "0"), b += parseInt(d, 10), b += c.offsetTop, !c.offsetParent) break;
            c = c.offsetParent
        } else a.y && (b += a.y);
    for (; a && a != document.body;) b -= a.scrollTop, a = a.parentNode;
    return b
}, Dygraph.pageX = function(a) {
    if (a.pageX) return !a.pageX || a.pageX < 0 ? 0 : a.pageX;
    var b = document.documentElement,
        c = document.body;
    return a.clientX + (b.scrollLeft || c.scrollLeft) - (b.clientLeft || 0)
}, Dygraph.pageY = function(a) {
    if (a.pageY) return !a.pageY || a.pageY < 0 ? 0 : a.pageY;
    var b = document.documentElement,
        c = document.body;
    return a.clientY + (b.scrollTop || c.scrollTop) - (b.clientTop || 0)
}, Dygraph.isOK = function(a) {
    return !!a && !isNaN(a)
}, Dygraph.isValidPoint = function(a, b) {
    return a ? null === a.yval ? !1 : null === a.x || void 0 === a.x ? !1 : null === a.y || void 0 === a.y ? !1 : isNaN(a.x) || !b && isNaN(a.y) ? !1 : !0 : !1
}, Dygraph.floatFormat = function(a, b) {
    var c = Math.min(Math.max(1, b || 2), 21);
    return Math.abs(a) < .001 && 0 !== a ? a.toExponential(c - 1) : a.toPrecision(c)
}, Dygraph.zeropad = function(a) {
    return 10 > a ? "0" + a : "" + a
}, Dygraph.hmsString_ = function(a) {
    var b = Dygraph.zeropad,
        c = new Date(a);
    return c.getSeconds() ? b(c.getHours()) + ":" + b(c.getMinutes()) + ":" + b(c.getSeconds()) : b(c.getHours()) + ":" + b(c.getMinutes())
}, Dygraph.round_ = function(a, b) {
    var c = Math.pow(10, b);
    return Math.round(a * c) / c
}, Dygraph.binarySearch = function(a, b, c, d, e) {
    if ((null === d || void 0 === d || null === e || void 0 === e) && (d = 0, e = b.length - 1), d > e) return -1;
    (null === c || void 0 === c) && (c = 0);
    var f, g = function(a) {
            return a >= 0 && a < b.length
        },
        h = parseInt((d + e) / 2, 10),
        i = b[h];
    return i == a ? h : i > a ? c > 0 && (f = h - 1, g(f) && b[f] < a) ? h : Dygraph.binarySearch(a, b, c, d, h - 1) : a > i ? 0 > c && (f = h + 1, g(f) && b[f] > a) ? h : Dygraph.binarySearch(a, b, c, h + 1, e) : -1
}, Dygraph.dateParser = function(a) {
    var b, c;
    if ((-1 == a.search("-") || -1 != a.search("T") || -1 != a.search("Z")) && (c = Dygraph.dateStrToMillis(a), c && !isNaN(c))) return c;
    if (-1 != a.search("-")) {
        for (b = a.replace("-", "/", "g"); - 1 != b.search("-");) b = b.replace("-", "/");
        c = Dygraph.dateStrToMillis(b)
    } else 8 == a.length ? (b = a.substr(0, 4) + "/" + a.substr(4, 2) + "/" + a.substr(6, 2), c = Dygraph.dateStrToMillis(b)) : c = Dygraph.dateStrToMillis(a);
    return (!c || isNaN(c)) && Dygraph.error("Couldn't parse " + a + " as a date"), c
}, Dygraph.dateStrToMillis = function(a) {
    return new Date(a).getTime()
}, Dygraph.update = function(a, b) {
    if ("undefined" != typeof b && null !== b)
        for (var c in b) b.hasOwnProperty(c) && (a[c] = b[c]);
    return a
}, Dygraph.updateDeep = function(a, b) {
    function c(a) {
        return "object" == typeof Node ? a instanceof Node : "object" == typeof a && "number" == typeof a.nodeType && "string" == typeof a.nodeName
    }
    if ("undefined" != typeof b && null !== b)
        for (var d in b) b.hasOwnProperty(d) && (null === b[d] ? a[d] = null : Dygraph.isArrayLike(b[d]) ? a[d] = b[d].slice() : c(b[d]) ? a[d] = b[d] : "object" == typeof b[d] ? (("object" != typeof a[d] || null === a[d]) && (a[d] = {}), Dygraph.updateDeep(a[d], b[d])) : a[d] = b[d]);
    return a
}, Dygraph.isArrayLike = function(a) {
    var b = typeof a;
    return "object" != b && ("function" != b || "function" != typeof a.item) || null === a || "number" != typeof a.length || 3 === a.nodeType ? !1 : !0
}, Dygraph.isDateLike = function(a) {
    return "object" != typeof a || null === a || "function" != typeof a.getTime ? !1 : !0
}, Dygraph.clone = function(a) {
    for (var b = [], c = 0; c < a.length; c++) b.push(Dygraph.isArrayLike(a[c]) ? Dygraph.clone(a[c]) : a[c]);
    return b
}, Dygraph.createCanvas = function() {
    var a = document.createElement("canvas"),
        b = /MSIE/.test(navigator.userAgent) && !window.opera;
    return b && "undefined" != typeof G_vmlCanvasManager && (a = G_vmlCanvasManager.initElement(a)), a
}, Dygraph.isAndroid = function() {
    return /Android/.test(navigator.userAgent)
}, Dygraph.Iterator = function(a, b, c, d) {
    b = b || 0, c = c || a.length, this.hasNext = !0, this.peek = null, this.start_ = b, this.array_ = a, this.predicate_ = d, this.end_ = Math.min(a.length, b + c), this.nextIdx_ = b - 1, this.next()
}, Dygraph.Iterator.prototype.next = function() {
    if (!this.hasNext) return null;
    for (var a = this.peek, b = this.nextIdx_ + 1, c = !1; b < this.end_;) {
        if (!this.predicate_ || this.predicate_(this.array_, b)) {
            this.peek = this.array_[b], c = !0;
            break
        }
        b++
    }
    return this.nextIdx_ = b, c || (this.hasNext = !1, this.peek = null), a
}, Dygraph.createIterator = function(a, b, c, d) {
    return new Dygraph.Iterator(a, b, c, d)
}, Dygraph.requestAnimFrame = function() {
    return window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame || function(a) {
        window.setTimeout(a, 1e3 / 60)
    }
}(), Dygraph.repeatAndCleanup = function(a, b, c, d) {
    var e, f = 0,
        g = (new Date).getTime();
    if (a(f), 1 == b) return void d();
    var h = b - 1;
    ! function i() {
        f >= b || Dygraph.requestAnimFrame.call(window, function() {
            var b = (new Date).getTime(),
                j = b - g;
            e = f, f = Math.floor(j / c);
            var k = f - e,
                l = f + k > h;
            l || f >= h ? (a(h), d()) : (0 !== k && a(f), i())
        })
    }()
}, Dygraph.isPixelChangingOptionList = function(a, b) {
    var c = {
            "annotationClickHandler": !0,
            "annotationDblClickHandler": !0,
            "annotationMouseOutHandler": !0,
            "annotationMouseOverHandler": !0,
            "axisLabelColor": !0,
            "axisLineColor": !0,
            "axisLineWidth": !0,
            "clickCallback": !0,
            "digitsAfterDecimal": !0,
            "drawCallback": !0,
            "drawHighlightPointCallback": !0,
            "drawPoints": !0,
            "drawPointCallback": !0,
            "drawXGrid": !0,
            "drawYGrid": !0,
            "fillAlpha": !0,
            "gridLineColor": !0,
            "gridLineWidth": !0,
            "hideOverlayOnMouseOut": !0,
            "highlightCallback": !0,
            "highlightCircleSize": !0,
            "interactionModel": !0,
            "isZoomedIgnoreProgrammaticZoom": !0,
            "labelsDiv": !0,
            "labelsDivStyles": !0,
            "labelsDivWidth": !0,
            "labelsKMB": !0,
            "labelsKMG2": !0,
            "labelsSeparateLines": !0,
            "labelsShowZeroValues": !0,
            "legend": !0,
            "maxNumberWidth": !0,
            "panEdgeFraction": !0,
            "pixelsPerYLabel": !0,
            "pointClickCallback": !0,
            "pointSize": !0,
            "rangeSelectorPlotFillColor": !0,
            "rangeSelectorPlotStrokeColor": !0,
            "showLabelsOnHighlight": !0,
            "showRoller": !0,
            "sigFigs": !0,
            "strokeWidth": !0,
            "underlayCallback": !0,
            "unhighlightCallback": !0,
            "xAxisLabelFormatter": !0,
            "xTicker": !0,
            "xValueFormatter": !0,
            "yAxisLabelFormatter": !0,
            "yValueFormatter": !0,
            "zoomCallback": !0
        },
        d = !1,
        e = {};
    if (a)
        for (var f = 1; f < a.length; f++) e[a[f]] = !0;
    for (var g in b) {
        if (d) break;
        if (b.hasOwnProperty(g))
            if (e[g])
                for (var h in b[g]) {
                    if (d) break;
                    b[g].hasOwnProperty(h) && !c[h] && (d = !0)
                } else c[g] || (d = !0)
    }
    return d
}, Dygraph.compareArrays = function(a, b) {
    if (!Dygraph.isArrayLike(a) || !Dygraph.isArrayLike(b)) return !1;
    if (a.length !== b.length) return !1;
    for (var c = 0; c < a.length; c++)
        if (a[c] !== b[c]) return !1;
    return !0
}, Dygraph.regularShape_ = function(a, b, c, d, e, f, g) {
    f = f || 0, g = g || 2 * Math.PI / b, a.beginPath();
    var h = f,
        i = h,
        j = function() {
            var a = d + Math.sin(i) * c,
                b = e + -Math.cos(i) * c;
            return [a, b]
        },
        k = j(),
        l = k[0],
        m = k[1];
    a.moveTo(l, m);
    for (var n = 0; b > n; n++) {
        i = n == b - 1 ? h : i + g;
        var o = j();
        a.lineTo(o[0], o[1])
    }
    a.fill(), a.stroke()
}, Dygraph.shapeFunction_ = function(a, b, c) {
    return function(d, e, f, g, h, i, j) {
        f.strokeStyle = i, f.fillStyle = "white", Dygraph.regularShape_(f, a, j, g, h, b, c)
    }
}, Dygraph.Circles = {
    "DEFAULT": function(a, b, c, d, e, f, g) {
        c.beginPath(), c.fillStyle = f, c.arc(d, e, g, 0, 2 * Math.PI, !1), c.fill()
    },
    "TRIANGLE": Dygraph.shapeFunction_(3),
    "SQUARE": Dygraph.shapeFunction_(4, Math.PI / 4),
    "DIAMOND": Dygraph.shapeFunction_(4),
    "PENTAGON": Dygraph.shapeFunction_(5),
    "HEXAGON": Dygraph.shapeFunction_(6),
    "CIRCLE": function(a, b, c, d, e, f, g) {
        c.beginPath(), c.strokeStyle = f, c.fillStyle = "white", c.arc(d, e, g, 0, 2 * Math.PI, !1), c.fill(), c.stroke()
    },
    "STAR": Dygraph.shapeFunction_(5, 0, 4 * Math.PI / 5),
    "PLUS": function(a, b, c, d, e, f, g) {
        c.strokeStyle = f, c.beginPath(), c.moveTo(d + g, e), c.lineTo(d - g, e), c.closePath(), c.stroke(), c.beginPath(), c.moveTo(d, e + g), c.lineTo(d, e - g), c.closePath(), c.stroke()
    },
    "EX": function(a, b, c, d, e, f, g) {
        c.strokeStyle = f, c.beginPath(), c.moveTo(d + g, e + g), c.lineTo(d - g, e - g), c.closePath(), c.stroke(), c.beginPath(), c.moveTo(d + g, e - g), c.lineTo(d - g, e + g), c.closePath(), c.stroke()
    }
}, Dygraph.IFrameTarp = function() {
    this.tarps = []
}, Dygraph.IFrameTarp.prototype.cover = function() {
    for (var a = document.getElementsByTagName("iframe"), b = 0; b < a.length; b++) {
        var c = a[b],
            d = Dygraph.findPosX(c),
            e = Dygraph.findPosY(c),
            f = c.offsetWidth,
            g = c.offsetHeight,
            h = document.createElement("div");
        h.style.position = "absolute", h.style.left = d + "px", h.style.top = e + "px", h.style.width = f + "px", h.style.height = g + "px", h.style.zIndex = 999, document.body.appendChild(h), this.tarps.push(h)
    }
}, Dygraph.IFrameTarp.prototype.uncover = function() {
    for (var a = 0; a < this.tarps.length; a++) this.tarps[a].parentNode.removeChild(this.tarps[a]);
    this.tarps = []
}, Dygraph.detectLineDelimiter = function(a) {
    for (var b = 0; b < a.length; b++) {
        var c = a.charAt(b);
        if ("\r" === c) return b + 1 < a.length && "\n" === a.charAt(b + 1) ? "\r\n" : c;
        if ("\n" === c) return b + 1 < a.length && "\r" === a.charAt(b + 1) ? "\n\r" : c
    }
    return null
}, Dygraph.isNodeContainedBy = function(a, b) {
    if (null === b || null === a) return !1;
    for (var c = a; c && c !== b;) c = c.parentNode;
    return c === b
}, Dygraph.pow = function(a, b) {
    return 0 > b ? 1 / Math.pow(a, -b) : Math.pow(a, b)
}, Dygraph.dateSetters = {
    "ms": Date.prototype.setMilliseconds,
    "s": Date.prototype.setSeconds,
    "m": Date.prototype.setMinutes,
    "h": Date.prototype.setHours
}, Dygraph.setDateSameTZ = function(a, b) {
    var c = a.getTimezoneOffset();
    for (var d in b)
        if (b.hasOwnProperty(d)) {
            var e = Dygraph.dateSetters[d];
            if (!e) throw "Invalid setter: " + d;
            e.call(a, b[d]), a.getTimezoneOffset() != c && a.setTime(a.getTime() + 60 * (c - a.getTimezoneOffset()) * 1e3)
        }
}, Dygraph.GVizChart = function(a) {
    this.container = a
}, Dygraph.GVizChart.prototype.draw = function(a, b) {
    this.container.innerHTML = "", "undefined" != typeof this.date_graph && this.date_graph.destroy(), this.date_graph = new Dygraph(this.container, a, b)
}, Dygraph.GVizChart.prototype.setSelection = function(a) {
    var b = !1;
    a.length && (b = a[0].row), this.date_graph.setSelection(b)
}, Dygraph.GVizChart.prototype.getSelection = function() {
    var a = [],
        b = this.date_graph.getSelection();
    if (0 > b) return a;
    for (var c = this.date_graph.layout_.points, d = 0; d < c.length; ++d) a.push({
        "row": b,
        "column": d + 1
    });
    return a
}, Dygraph.Interaction = {}, Dygraph.Interaction.startPan = function(a, b, c) {
    var d, e;
    c.isPanning = !0;
    var f = b.xAxisRange();
    if (c.dateRange = f[1] - f[0], c.initialLeftmostDate = f[0], c.xUnitsPerPixel = c.dateRange / (b.plotter_.area.w - 1), b.attr_("panEdgeFraction")) {
        var g = b.width_ * b.attr_("panEdgeFraction"),
            h = b.xAxisExtremes(),
            i = b.toDomXCoord(h[0]) - g,
            j = b.toDomXCoord(h[1]) + g,
            k = b.toDataXCoord(i),
            l = b.toDataXCoord(j);
        c.boundedDates = [k, l];
        var m = [],
            n = b.height_ * b.attr_("panEdgeFraction");
        for (d = 0; d < b.axes_.length; d++) {
            e = b.axes_[d];
            var o = e.extremeRange,
                p = b.toDomYCoord(o[0], d) + n,
                q = b.toDomYCoord(o[1], d) - n,
                r = b.toDataYCoord(p, d),
                s = b.toDataYCoord(q, d);
            m[d] = [r, s]
        }
        c.boundedValues = m
    }
    for (c.is2DPan = !1, c.axes = [], d = 0; d < b.axes_.length; d++) {
        e = b.axes_[d];
        var t = {},
            u = b.yAxisRange(d),
            v = b.attributes_.getForAxis("logscale", d);
        v ? (t.initialTopValue = Dygraph.log10(u[1]), t.dragValueRange = Dygraph.log10(u[1]) - Dygraph.log10(u[0])) : (t.initialTopValue = u[1], t.dragValueRange = u[1] - u[0]), t.unitsPerPixel = t.dragValueRange / (b.plotter_.area.h - 1), c.axes.push(t), (e.valueWindow || e.valueRange) && (c.is2DPan = !0)
    }
}, Dygraph.Interaction.movePan = function(a, b, c) {
    c.dragEndX = b.dragGetX_(a, c), c.dragEndY = b.dragGetY_(a, c);
    var d = c.initialLeftmostDate - (c.dragEndX - c.dragStartX) * c.xUnitsPerPixel;
    c.boundedDates && (d = Math.max(d, c.boundedDates[0]));
    var e = d + c.dateRange;
    if (c.boundedDates && e > c.boundedDates[1] && (d -= e - c.boundedDates[1], e = d + c.dateRange), b.dateWindow_ = [d, e], c.is2DPan)
        for (var f = c.dragEndY - c.dragStartY, g = 0; g < b.axes_.length; g++) {
            var h = b.axes_[g],
                i = c.axes[g],
                j = f * i.unitsPerPixel,
                k = c.boundedValues ? c.boundedValues[g] : null,
                l = i.initialTopValue + j;
            k && (l = Math.min(l, k[1]));
            var m = l - i.dragValueRange;
            k && m < k[0] && (l -= m - k[0], m = l - i.dragValueRange);
            var n = b.attributes_.getForAxis("logscale", g);
            h.valueWindow = n ? [Math.pow(Dygraph.LOG_SCALE, m), Math.pow(Dygraph.LOG_SCALE, l)] : [m, l]
        }
    b.drawGraph_(!1)
}, Dygraph.Interaction.endPan = function(a, b, c) {
    c.dragEndX = b.dragGetX_(a, c), c.dragEndY = b.dragGetY_(a, c);
    var d = Math.abs(c.dragEndX - c.dragStartX),
        e = Math.abs(c.dragEndY - c.dragStartY);
    2 > d && 2 > e && void 0 !== b.lastx_ && -1 != b.lastx_ && Dygraph.Interaction.treatMouseOpAsClick(b, a, c), c.isPanning = !1, c.is2DPan = !1, c.initialLeftmostDate = null, c.dateRange = null, c.valueRange = null, c.boundedDates = null, c.boundedValues = null, c.axes = null
}, Dygraph.Interaction.startZoom = function(a, b, c) {
    c.isZooming = !0, c.zoomMoved = !1
}, Dygraph.Interaction.moveZoom = function(a, b, c) {
    c.zoomMoved = !0, c.dragEndX = b.dragGetX_(a, c), c.dragEndY = b.dragGetY_(a, c);
    var d = Math.abs(c.dragStartX - c.dragEndX),
        e = Math.abs(c.dragStartY - c.dragEndY);
    c.dragDirection = e / 2 > d ? Dygraph.VERTICAL : Dygraph.HORIZONTAL, b.drawZoomRect_(c.dragDirection, c.dragStartX, c.dragEndX, c.dragStartY, c.dragEndY, c.prevDragDirection, c.prevEndX, c.prevEndY), c.prevEndX = c.dragEndX, c.prevEndY = c.dragEndY, c.prevDragDirection = c.dragDirection
}, Dygraph.Interaction.treatMouseOpAsClick = function(a, b, c) {
    var d = a.attr_("clickCallback"),
        e = a.attr_("pointClickCallback"),
        f = null;
    if (e) {
        for (var g = -1, h = Number.MAX_VALUE, i = 0; i < a.selPoints_.length; i++) {
            var j = a.selPoints_[i],
                k = Math.pow(j.canvasx - c.dragEndX, 2) + Math.pow(j.canvasy - c.dragEndY, 2);
            !isNaN(k) && (-1 == g || h > k) && (h = k, g = i)
        }
        var l = a.attr_("highlightCircleSize") + 2;
        l * l >= h && (f = a.selPoints_[g])
    }
    f && e(b, f), d && d(b, a.lastx_, a.selPoints_)
}, Dygraph.Interaction.endZoom = function(a, b, c) {
    c.isZooming = !1, c.dragEndX = b.dragGetX_(a, c), c.dragEndY = b.dragGetY_(a, c);
    var d = Math.abs(c.dragEndX - c.dragStartX),
        e = Math.abs(c.dragEndY - c.dragStartY);
    2 > d && 2 > e && void 0 !== b.lastx_ && -1 != b.lastx_ && Dygraph.Interaction.treatMouseOpAsClick(b, a, c);
    var f = b.getArea();
    if (d >= 10 && c.dragDirection == Dygraph.HORIZONTAL) {
        var g = Math.min(c.dragStartX, c.dragEndX),
            h = Math.max(c.dragStartX, c.dragEndX);
        g = Math.max(g, f.x), h = Math.min(h, f.x + f.w), h > g && b.doZoomX_(g, h), c.cancelNextDblclick = !0
    } else if (e >= 10 && c.dragDirection == Dygraph.VERTICAL) {
        var i = Math.min(c.dragStartY, c.dragEndY),
            j = Math.max(c.dragStartY, c.dragEndY);
        i = Math.max(i, f.y), j = Math.min(j, f.y + f.h), j > i && b.doZoomY_(i, j), c.cancelNextDblclick = !0
    } else c.zoomMoved && b.clearZoomRect_();
    c.dragStartX = null, c.dragStartY = null
}, Dygraph.Interaction.startTouch = function(a, b, c) {
    a.preventDefault(), a.touches.length > 1 && (c.startTimeForDoubleTapMs = null);
    for (var d = [], e = 0; e < a.touches.length; e++) {
        var f = a.touches[e];
        d.push({
            "pageX": f.pageX,
            "pageY": f.pageY,
            "dataX": b.toDataXCoord(f.pageX),
            "dataY": b.toDataYCoord(f.pageY)
        })
    }
    if (c.initialTouches = d, 1 == d.length) c.initialPinchCenter = d[0], c.touchDirections = {
        "x": !0,
        "y": !0
    };
    else if (d.length >= 2) {
        c.initialPinchCenter = {
            "pageX": .5 * (d[0].pageX + d[1].pageX),
            "pageY": .5 * (d[0].pageY + d[1].pageY),
            "dataX": .5 * (d[0].dataX + d[1].dataX),
            "dataY": .5 * (d[0].dataY + d[1].dataY)
        };
        var g = 180 / Math.PI * Math.atan2(c.initialPinchCenter.pageY - d[0].pageY, d[0].pageX - c.initialPinchCenter.pageX);
        g = Math.abs(g), g > 90 && (g = 90 - g), c.touchDirections = {
            "x": 67.5 > g,
            "y": g > 22.5
        }
    }
    c.initialRange = {
        "x": b.xAxisRange(),
        "y": b.yAxisRange()
    }
}, Dygraph.Interaction.moveTouch = function(a, b, c) {
    c.startTimeForDoubleTapMs = null;
    var d, e = [];
    for (d = 0; d < a.touches.length; d++) {
        var f = a.touches[d];
        e.push({
            "pageX": f.pageX,
            "pageY": f.pageY
        })
    }
    var g, h = c.initialTouches,
        i = c.initialPinchCenter;
    g = 1 == e.length ? e[0] : {
        "pageX": .5 * (e[0].pageX + e[1].pageX),
        "pageY": .5 * (e[0].pageY + e[1].pageY)
    };
    var j = {
            "pageX": g.pageX - i.pageX,
            "pageY": g.pageY - i.pageY
        },
        k = c.initialRange.x[1] - c.initialRange.x[0],
        l = c.initialRange.y[0] - c.initialRange.y[1];
    j.dataX = j.pageX / b.plotter_.area.w * k, j.dataY = j.pageY / b.plotter_.area.h * l;
    var m, n;
    if (1 == e.length) m = 1, n = 1;
    else if (e.length >= 2) {
        var o = h[1].pageX - i.pageX;
        m = (e[1].pageX - g.pageX) / o;
        var p = h[1].pageY - i.pageY;
        n = (e[1].pageY - g.pageY) / p
    }
    m = Math.min(8, Math.max(.125, m)), n = Math.min(8, Math.max(.125, n));
    var q = !1;
    if (c.touchDirections.x && (b.dateWindow_ = [i.dataX - j.dataX + (c.initialRange.x[0] - i.dataX) / m, i.dataX - j.dataX + (c.initialRange.x[1] - i.dataX) / m], q = !0), c.touchDirections.y)
        for (d = 0; 1 > d; d++) {
            var r = b.axes_[d],
                s = b.attributes_.getForAxis("logscale", d);
            s || (r.valueWindow = [i.dataY - j.dataY + (c.initialRange.y[0] - i.dataY) / n, i.dataY - j.dataY + (c.initialRange.y[1] - i.dataY) / n], q = !0)
        }
    if (b.drawGraph_(!1), q && e.length > 1 && b.attr_("zoomCallback")) {
        var t = b.xAxisRange();
        b.attr_("zoomCallback")(t[0], t[1], b.yAxisRanges())
    }
}, Dygraph.Interaction.endTouch = function(a, b, c) {
    if (0 !== a.touches.length) Dygraph.Interaction.startTouch(a, b, c);
    else if (1 == a.changedTouches.length) {
        var d = (new Date).getTime(),
            e = a.changedTouches[0];
        c.startTimeForDoubleTapMs && d - c.startTimeForDoubleTapMs < 500 && c.doubleTapX && Math.abs(c.doubleTapX - e.screenX) < 50 && c.doubleTapY && Math.abs(c.doubleTapY - e.screenY) < 50 ? b.resetZoom() : (c.startTimeForDoubleTapMs = d, c.doubleTapX = e.screenX, c.doubleTapY = e.screenY)
    }
}, Dygraph.Interaction.defaultModel = {
    "mousedown": function(a, b, c) {
        a.button && 2 == a.button || (c.initializeMouseDown(a, b, c), a.altKey || a.shiftKey ? Dygraph.startPan(a, b, c) : Dygraph.startZoom(a, b, c))
    },
    "mousemove": function(a, b, c) {
        c.isZooming ? Dygraph.moveZoom(a, b, c) : c.isPanning && Dygraph.movePan(a, b, c)
    },
    "mouseup": function(a, b, c) {
        c.isZooming ? Dygraph.endZoom(a, b, c) : c.isPanning && Dygraph.endPan(a, b, c)
    },
    "touchstart": function(a, b, c) {
        Dygraph.Interaction.startTouch(a, b, c)
    },
    "touchmove": function(a, b, c) {
        Dygraph.Interaction.moveTouch(a, b, c)
    },
    "touchend": function(a, b, c) {
        Dygraph.Interaction.endTouch(a, b, c)
    },
    "mouseout": function(a, b, c) {
        c.isZooming && (c.dragEndX = null, c.dragEndY = null, b.clearZoomRect_())
    },
    "dblclick": function(a, b, c) {
        return c.cancelNextDblclick ? void(c.cancelNextDblclick = !1) : void(a.altKey || a.shiftKey || b.resetZoom())
    }
}, Dygraph.DEFAULT_ATTRS.interactionModel = Dygraph.Interaction.defaultModel, Dygraph.defaultInteractionModel = Dygraph.Interaction.defaultModel, Dygraph.endZoom = Dygraph.Interaction.endZoom, Dygraph.moveZoom = Dygraph.Interaction.moveZoom, Dygraph.startZoom = Dygraph.Interaction.startZoom, Dygraph.endPan = Dygraph.Interaction.endPan, Dygraph.movePan = Dygraph.Interaction.movePan, Dygraph.startPan = Dygraph.Interaction.startPan, Dygraph.Interaction.nonInteractiveModel_ = {
    "mousedown": function(a, b, c) {
        c.initializeMouseDown(a, b, c)
    },
    "mouseup": function(a, b, c) {
        c.dragEndX = b.dragGetX_(a, c), c.dragEndY = b.dragGetY_(a, c);
        var d = Math.abs(c.dragEndX - c.dragStartX),
            e = Math.abs(c.dragEndY - c.dragStartY);
        2 > d && 2 > e && void 0 !== b.lastx_ && -1 != b.lastx_ && Dygraph.Interaction.treatMouseOpAsClick(b, a, c)
    }
}, Dygraph.Interaction.dragIsPanInteractionModel = {
    "mousedown": function(a, b, c) {
        c.initializeMouseDown(a, b, c), Dygraph.startPan(a, b, c)
    },
    "mousemove": function(a, b, c) {
        c.isPanning && Dygraph.movePan(a, b, c)
    },
    "mouseup": function(a, b, c) {
        c.isPanning && Dygraph.endPan(a, b, c)
    }
}, Dygraph.TickList = void 0, Dygraph.Ticker = void 0, Dygraph.numericLinearTicks = function(a, b, c, d, e, f) {
    var g = function(a) {
        return "logscale" === a ? !1 : d(a)
    };
    return Dygraph.numericTicks(a, b, c, g, e, f)
}, Dygraph.numericTicks = function(a, b, c, d, e, f) {
    var g, h, i, j, k = d("pixelsPerLabel"),
        l = [];
    if (f)
        for (g = 0; g < f.length; g++) l.push({
            "v": f[g]
        });
    else {
        if (d("logscale")) {
            j = Math.floor(c / k);
            var m = Dygraph.binarySearch(a, Dygraph.PREFERRED_LOG_TICK_VALUES, 1),
                n = Dygraph.binarySearch(b, Dygraph.PREFERRED_LOG_TICK_VALUES, -1); - 1 == m && (m = 0), -1 == n && (n = Dygraph.PREFERRED_LOG_TICK_VALUES.length - 1);
            var o = null;
            if (n - m >= j / 4) {
                for (var p = n; p >= m; p--) {
                    var q = Dygraph.PREFERRED_LOG_TICK_VALUES[p],
                        r = Math.log(q / a) / Math.log(b / a) * c,
                        s = {
                            "v": q
                        };
                    null === o ? o = {
                        "tickValue": q,
                        "pixel_coord": r
                    } : Math.abs(r - o.pixel_coord) >= k ? o = {
                        "tickValue": q,
                        "pixel_coord": r
                    } : s.label = "", l.push(s)
                }
                l.reverse()
            }
        }
        if (0 === l.length) {
            var t, u, v = d("labelsKMG2");
            v ? (t = [1, 2, 4, 8, 16, 32, 64, 128, 256], u = 16) : (t = [1, 2, 5, 10, 20, 50, 100], u = 10);
            var w, x, y, z, A = Math.ceil(c / k),
                B = Math.abs(b - a) / A,
                C = Math.floor(Math.log(B) / Math.log(u)),
                D = Math.pow(u, C);
            for (h = 0; h < t.length && (w = D * t[h], x = Math.floor(a / w) * w, y = Math.ceil(b / w) * w, j = Math.abs(y - x) / w, z = c / j, !(z > k)); h++);
            for (x > y && (w *= -1), g = 0; j > g; g++) i = x + g * w, l.push({
                "v": i
            })
        }
    }
    var E = d("axisLabelFormatter");
    for (g = 0; g < l.length; g++) void 0 === l[g].label && (l[g].label = E(l[g].v, 0, d, e));
    return l
}, Dygraph.dateTicker = function(a, b, c, d, e) {
    var f = Dygraph.pickDateTickGranularity(a, b, c, d);
    return f >= 0 ? Dygraph.getDateAxis(a, b, f, d, e) : []
}, Dygraph.SECONDLY = 0, Dygraph.TWO_SECONDLY = 1, Dygraph.FIVE_SECONDLY = 2, Dygraph.TEN_SECONDLY = 3, Dygraph.THIRTY_SECONDLY = 4, Dygraph.MINUTELY = 5, Dygraph.TWO_MINUTELY = 6, Dygraph.FIVE_MINUTELY = 7, Dygraph.TEN_MINUTELY = 8, Dygraph.THIRTY_MINUTELY = 9, Dygraph.HOURLY = 10, Dygraph.TWO_HOURLY = 11, Dygraph.SIX_HOURLY = 12, Dygraph.DAILY = 13, Dygraph.WEEKLY = 14, Dygraph.MONTHLY = 15, Dygraph.QUARTERLY = 16, Dygraph.BIANNUAL = 17, Dygraph.ANNUAL = 18, Dygraph.DECADAL = 19, Dygraph.CENTENNIAL = 20, Dygraph.NUM_GRANULARITIES = 21, Dygraph.SHORT_SPACINGS = [], Dygraph.SHORT_SPACINGS[Dygraph.SECONDLY] = 1e3, Dygraph.SHORT_SPACINGS[Dygraph.TWO_SECONDLY] = 2e3, Dygraph.SHORT_SPACINGS[Dygraph.FIVE_SECONDLY] = 5e3, Dygraph.SHORT_SPACINGS[Dygraph.TEN_SECONDLY] = 1e4, Dygraph.SHORT_SPACINGS[Dygraph.THIRTY_SECONDLY] = 3e4, Dygraph.SHORT_SPACINGS[Dygraph.MINUTELY] = 6e4, Dygraph.SHORT_SPACINGS[Dygraph.TWO_MINUTELY] = 12e4, Dygraph.SHORT_SPACINGS[Dygraph.FIVE_MINUTELY] = 3e5, Dygraph.SHORT_SPACINGS[Dygraph.TEN_MINUTELY] = 6e5, Dygraph.SHORT_SPACINGS[Dygraph.THIRTY_MINUTELY] = 18e5, Dygraph.SHORT_SPACINGS[Dygraph.HOURLY] = 36e5, Dygraph.SHORT_SPACINGS[Dygraph.TWO_HOURLY] = 72e5, Dygraph.SHORT_SPACINGS[Dygraph.SIX_HOURLY] = 216e5, Dygraph.SHORT_SPACINGS[Dygraph.DAILY] = 864e5, Dygraph.SHORT_SPACINGS[Dygraph.WEEKLY] = 6048e5, Dygraph.LONG_TICK_PLACEMENTS = [], Dygraph.LONG_TICK_PLACEMENTS[Dygraph.MONTHLY] = {
    "months": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11],
    "year_mod": 1
}, Dygraph.LONG_TICK_PLACEMENTS[Dygraph.QUARTERLY] = {
    "months": [0, 3, 6, 9],
    "year_mod": 1
}, Dygraph.LONG_TICK_PLACEMENTS[Dygraph.BIANNUAL] = {
    "months": [0, 6],
    "year_mod": 1
}, Dygraph.LONG_TICK_PLACEMENTS[Dygraph.ANNUAL] = {
    "months": [0],
    "year_mod": 1
}, Dygraph.LONG_TICK_PLACEMENTS[Dygraph.DECADAL] = {
    "months": [0],
    "year_mod": 10
}, Dygraph.LONG_TICK_PLACEMENTS[Dygraph.CENTENNIAL] = {
    "months": [0],
    "year_mod": 100
}, Dygraph.PREFERRED_LOG_TICK_VALUES = function() {
    for (var a = [], b = -39; 39 >= b; b++)
        for (var c = Math.pow(10, b), d = 1; 9 >= d; d++) {
            var e = c * d;
            a.push(e)
        }
    return a
}(), Dygraph.pickDateTickGranularity = function(a, b, c, d) {
    for (var e = d("pixelsPerLabel"), f = 0; f < Dygraph.NUM_GRANULARITIES; f++) {
        var g = Dygraph.numDateTicks(a, b, f);
        if (c / g >= e) return f
    }
    return -1
}, Dygraph.numDateTicks = function(a, b, c) {
    if (c < Dygraph.MONTHLY) {
        var d = Dygraph.SHORT_SPACINGS[c];
        return Math.floor(.5 + 1 * (b - a) / d)
    }
    var e = Dygraph.LONG_TICK_PLACEMENTS[c],
        f = 31557807360,
        g = 1 * (b - a) / f;
    return Math.floor(.5 + 1 * g * e.months.length / e.year_mod)
}, Dygraph.getDateAxis = function(a, b, c, d, e) {
    var f, g = d("axisLabelFormatter"),
        h = [];
    if (c < Dygraph.MONTHLY) {
        var i = Dygraph.SHORT_SPACINGS[c],
            j = i / 1e3,
            k = new Date(a);
        Dygraph.setDateSameTZ(k, {
            "ms": 0
        });
        var l;
        60 >= j ? (l = k.getSeconds(), Dygraph.setDateSameTZ(k, {
            "s": l - l % j
        })) : (Dygraph.setDateSameTZ(k, {
            "s": 0
        }), j /= 60, 60 >= j ? (l = k.getMinutes(), Dygraph.setDateSameTZ(k, {
            "m": l - l % j
        })) : (Dygraph.setDateSameTZ(k, {
            "m": 0
        }), j /= 60, 24 >= j ? (l = k.getHours(), k.setHours(l - l % j)) : (k.setHours(0), j /= 24, 7 == j && k.setDate(k.getDate() - k.getDay())))), a = k.getTime();
        var m = new Date(a).getTimezoneOffset(),
            n = i >= Dygraph.SHORT_SPACINGS[Dygraph.TWO_HOURLY];
        for (f = a; b >= f; f += i) {
            if (k = new Date(f), n && k.getTimezoneOffset() != m) {
                var o = k.getTimezoneOffset() - m;
                f += 60 * o * 1e3, k = new Date(f), m = k.getTimezoneOffset(), new Date(f + i).getTimezoneOffset() != m && (f += i, k = new Date(f), m = k.getTimezoneOffset())
            }
            h.push({
                "v": f,
                "label": g(k, c, d, e)
            })
        }
    } else {
        var p, q = 1;
        c < Dygraph.NUM_GRANULARITIES ? (p = Dygraph.LONG_TICK_PLACEMENTS[c].months, q = Dygraph.LONG_TICK_PLACEMENTS[c].year_mod) : Dygraph.warn("Span of dates is too long");
        for (var r = new Date(a).getFullYear(), s = new Date(b).getFullYear(), t = Dygraph.zeropad, u = r; s >= u; u++)
            if (u % q === 0)
                for (var v = 0; v < p.length; v++) {
                    var w = u + "/" + t(1 + p[v]) + "/01";
                    f = Dygraph.dateStrToMillis(w), a > f || f > b || h.push({
                        "v": f,
                        "label": g(new Date(f), c, d, e)
                    })
                }
    }
    return h
}, Dygraph && Dygraph.DEFAULT_ATTRS && Dygraph.DEFAULT_ATTRS.axes && Dygraph.DEFAULT_ATTRS.axes.x && Dygraph.DEFAULT_ATTRS.axes.y && Dygraph.DEFAULT_ATTRS.axes.y2 && (Dygraph.DEFAULT_ATTRS.axes.x.ticker = Dygraph.dateTicker, Dygraph.DEFAULT_ATTRS.axes.y.ticker = Dygraph.numericTicks, Dygraph.DEFAULT_ATTRS.axes.y2.ticker = Dygraph.numericTicks), Dygraph.Plugins = {}, Dygraph.Plugins.Annotations = function() {
    var a = function() {
        this.annotations_ = []
    };
    return a.prototype.toString = function() {
        return "Annotations Plugin"
    }, a.prototype.activate = function() {
        return {
            "clearChart": this.clearChart,
            "didDrawChart": this.didDrawChart
        }
    }, a.prototype.detachLabels = function() {
        for (var a = 0; a < this.annotations_.length; a++) {
            var b = this.annotations_[a];
            b.parentNode && b.parentNode.removeChild(b), this.annotations_[a] = null
        }
        this.annotations_ = []
    }, a.prototype.clearChart = function() {
        this.detachLabels()
    }, a.prototype.didDrawChart = function(a) {
        var b = a.dygraph,
            c = b.layout_.annotated_points;
        if (c && 0 !== c.length)
            for (var d = a.canvas.parentNode, e = {
                    "position": "absolute",
                    "fontSize": b.getOption("axisLabelFontSize") + "px",
                    "zIndex": 10,
                    "overflow": "hidden"
                }, f = function(a, c, d) {
                    return function(e) {
                        var f = d.annotation;
                        f.hasOwnProperty(a) ? f[a](f, d, b, e) : b.getOption(c) && b.getOption(c)(f, d, b, e)
                    }
                }, g = a.dygraph.plotter_.area, h = {}, i = 0; i < c.length; i++) {
                var j = c[i];
                if (!(j.canvasx < g.x || j.canvasx > g.x + g.w || j.canvasy < g.y || j.canvasy > g.y + g.h)) {
                    var k = j.annotation,
                        l = 6;
                    k.hasOwnProperty("tickHeight") && (l = k.tickHeight);
                    var m = document.createElement("div");
                    for (var n in e) e.hasOwnProperty(n) && (m.style[n] = e[n]);
                    k.hasOwnProperty("icon") || (m.className = "dygraphDefaultAnnotation"), k.hasOwnProperty("cssClass") && (m.className += " " + k.cssClass);
                    var o = k.hasOwnProperty("width") ? k.width : 16,
                        p = k.hasOwnProperty("height") ? k.height : 16;
                    if (k.hasOwnProperty("icon")) {
                        var q = document.createElement("img");
                        q.src = k.icon, q.width = o, q.height = p, m.appendChild(q)
                    } else j.annotation.hasOwnProperty("shortText") && m.appendChild(document.createTextNode(j.annotation.shortText));
                    var r = j.canvasx - o / 2;
                    m.style.left = r + "px";
                    var s = 0;
                    if (k.attachAtBottom) {
                        var t = g.y + g.h - p - l;
                        h[r] ? t -= h[r] : h[r] = 0, h[r] += l + p, s = t
                    } else s = j.canvasy - p - l;
                    m.style.top = s + "px", m.style.width = o + "px", m.style.height = p + "px", m.title = j.annotation.text, m.style.color = b.colorsMap_[j.name], m.style.borderColor = b.colorsMap_[j.name], k.div = m, b.addAndTrackEvent(m, "click", f("clickHandler", "annotationClickHandler", j, this)), b.addAndTrackEvent(m, "mouseover", f("mouseOverHandler", "annotationMouseOverHandler", j, this)), b.addAndTrackEvent(m, "mouseout", f("mouseOutHandler", "annotationMouseOutHandler", j, this)), b.addAndTrackEvent(m, "dblclick", f("dblClickHandler", "annotationDblClickHandler", j, this)), d.appendChild(m), this.annotations_.push(m);
                    var u = a.drawingContext;
                    if (u.save(), u.strokeStyle = b.colorsMap_[j.name], u.beginPath(), k.attachAtBottom) {
                        var t = s + p;
                        u.moveTo(j.canvasx, t), u.lineTo(j.canvasx, t + l)
                    } else u.moveTo(j.canvasx, j.canvasy), u.lineTo(j.canvasx, j.canvasy - 2 - l);
                    u.closePath(), u.stroke(), u.restore()
                }
            }
    }, a.prototype.destroy = function() {
        this.detachLabels()
    }, a
}(), Dygraph.Plugins.Axes = function() {
    var a = function() {
        this.xlabels_ = [], this.ylabels_ = []
    };
    return a.prototype.toString = function() {
        return "Axes Plugin"
    }, a.prototype.activate = function() {
        return {
            "layout": this.layout,
            "clearChart": this.clearChart,
            "willDrawChart": this.willDrawChart
        }
    }, a.prototype.layout = function(a) {
        var b = a.dygraph;
        if (b.getOption("drawYAxis")) {
            var c = b.getOption("yAxisLabelWidth") + 2 * b.getOption("axisTickSize");
            a.reserveSpaceLeft(c)
        }
        if (b.getOption("drawXAxis")) {
            var d;
            d = b.getOption("xAxisHeight") ? b.getOption("xAxisHeight") : b.getOptionForAxis("axisLabelFontSize", "x") + 2 * b.getOption("axisTickSize"), a.reserveSpaceBottom(d)
        }
        if (2 == b.numAxes()) {
            if (b.getOption("drawYAxis")) {
                var c = b.getOption("yAxisLabelWidth") + 2 * b.getOption("axisTickSize");
                a.reserveSpaceRight(c)
            }
        } else b.numAxes() > 2 && b.error("Only two y-axes are supported at this time. (Trying to use " + b.numAxes() + ")")
    }, a.prototype.detachLabels = function() {
        function a(a) {
            for (var b = 0; b < a.length; b++) {
                var c = a[b];
                c.parentNode && c.parentNode.removeChild(c)
            }
        }
        a(this.xlabels_), a(this.ylabels_), this.xlabels_ = [], this.ylabels_ = []
    }, a.prototype.clearChart = function() {
        this.detachLabels()
    }, a.prototype.willDrawChart = function(a) {
        function b(a) {
            return Math.round(a) + .5
        }

        function c(a) {
            return Math.round(a) - .5
        }
        var d = a.dygraph;
        if (d.getOption("drawXAxis") || d.getOption("drawYAxis")) {
            var e, f, g, h, i, j = a.drawingContext,
                k = a.canvas.parentNode,
                l = a.canvas.width,
                m = a.canvas.height,
                n = function(a) {
                    return {
                        "position": "absolute",
                        "fontSize": d.getOptionForAxis("axisLabelFontSize", a) + "px",
                        "zIndex": 10,
                        "color": d.getOptionForAxis("axisLabelColor", a),
                        "width": d.getOption("axisLabelWidth") + "px",
                        "lineHeight": "normal",
                        "overflow": "hidden"
                    }
                },
                o = {
                    "x": n("x"),
                    "y": n("y"),
                    "y2": n("y2")
                },
                p = function(a, b, c) {
                    var d = document.createElement("div"),
                        e = o["y2" == c ? "y2" : b];
                    for (var f in e) e.hasOwnProperty(f) && (d.style[f] = e[f]);
                    var g = document.createElement("div");
                    return g.className = "dygraph-axis-label dygraph-axis-label-" + b + (c ? " dygraph-axis-label-" + c : ""), g.innerHTML = a, d.appendChild(g), d
                };
            j.save();
            var q = d.layout_,
                r = a.dygraph.plotter_.area;
            if (d.getOption("drawYAxis")) {
                if (q.yticks && q.yticks.length > 0) {
                    var s = d.numAxes();
                    for (i = 0; i < q.yticks.length; i++) {
                        if (h = q.yticks[i], "function" == typeof h) return;
                        f = r.x;
                        var t = 1,
                            u = "y1";
                        1 == h[0] && (f = r.x + r.w, t = -1, u = "y2");
                        var v = d.getOptionForAxis("axisLabelFontSize", u);
                        g = r.y + h[1] * r.h, e = p(h[2], "y", 2 == s ? u : null);
                        var w = g - v / 2;
                        0 > w && (w = 0), w + v + 3 > m ? e.style.bottom = "0px" : e.style.top = w + "px", 0 === h[0] ? (e.style.left = r.x - d.getOption("yAxisLabelWidth") - d.getOption("axisTickSize") + "px", e.style.textAlign = "right") : 1 == h[0] && (e.style.left = r.x + r.w + d.getOption("axisTickSize") + "px", e.style.textAlign = "left"), e.style.width = d.getOption("yAxisLabelWidth") + "px", k.appendChild(e), this.ylabels_.push(e)
                    }
                    var x = this.ylabels_[0],
                        v = d.getOptionForAxis("axisLabelFontSize", "y"),
                        y = parseInt(x.style.top, 10) + v;
                    y > m - v && (x.style.top = parseInt(x.style.top, 10) - v / 2 + "px")
                }
                var z;
                if (d.getOption("drawAxesAtZero")) {
                    var A = d.toPercentXCoord(0);
                    (A > 1 || 0 > A || isNaN(A)) && (A = 0), z = b(r.x + A * r.w)
                } else z = b(r.x);
                j.strokeStyle = d.getOptionForAxis("axisLineColor", "y"), j.lineWidth = d.getOptionForAxis("axisLineWidth", "y"), j.beginPath(), j.moveTo(z, c(r.y)), j.lineTo(z, c(r.y + r.h)), j.closePath(), j.stroke(), 2 == d.numAxes() && (j.strokeStyle = d.getOptionForAxis("axisLineColor", "y2"), j.lineWidth = d.getOptionForAxis("axisLineWidth", "y2"), j.beginPath(), j.moveTo(c(r.x + r.w), c(r.y)), j.lineTo(c(r.x + r.w), c(r.y + r.h)), j.closePath(), j.stroke())
            }
            if (d.getOption("drawXAxis")) {
                if (q.xticks)
                    for (i = 0; i < q.xticks.length; i++) {
                        h = q.xticks[i], f = r.x + h[0] * r.w, g = r.y + r.h, e = p(h[1], "x"), e.style.textAlign = "center", e.style.top = g + d.getOption("axisTickSize") + "px";
                        var B = f - d.getOption("axisLabelWidth") / 2;
                        B + d.getOption("axisLabelWidth") > l && (B = l - d.getOption("xAxisLabelWidth"), e.style.textAlign = "right"), 0 > B && (B = 0, e.style.textAlign = "left"), e.style.left = B + "px", e.style.width = d.getOption("xAxisLabelWidth") + "px", k.appendChild(e), this.xlabels_.push(e)
                    }
                j.strokeStyle = d.getOptionForAxis("axisLineColor", "x"), j.lineWidth = d.getOptionForAxis("axisLineWidth", "x"), j.beginPath();
                var C;
                if (d.getOption("drawAxesAtZero")) {
                    var A = d.toPercentYCoord(0, 0);
                    (A > 1 || 0 > A) && (A = 1), C = c(r.y + A * r.h)
                } else C = c(r.y + r.h);
                j.moveTo(b(r.x), C), j.lineTo(b(r.x + r.w), C), j.closePath(), j.stroke()
            }
            j.restore()
        }
    }, a
}(), Dygraph.Plugins.ChartLabels = function() {
    var a = function() {
        this.title_div_ = null, this.xlabel_div_ = null, this.ylabel_div_ = null, this.y2label_div_ = null
    };
    a.prototype.toString = function() {
        return "ChartLabels Plugin"
    }, a.prototype.activate = function() {
        return {
            "layout": this.layout,
            "didDrawChart": this.didDrawChart
        }
    };
    var b = function(a) {
        var b = document.createElement("div");
        return b.style.position = "absolute", b.style.left = a.x + "px", b.style.top = a.y + "px", b.style.width = a.w + "px", b.style.height = a.h + "px", b
    };
    a.prototype.detachLabels_ = function() {
        for (var a = [this.title_div_, this.xlabel_div_, this.ylabel_div_, this.y2label_div_], b = 0; b < a.length; b++) {
            var c = a[b];
            c && c.parentNode && c.parentNode.removeChild(c)
        }
        this.title_div_ = null, this.xlabel_div_ = null, this.ylabel_div_ = null, this.y2label_div_ = null
    };
    var c = function(a, b, c, d, e) {
        var f = document.createElement("div");
        f.style.position = "absolute", f.style.left = 1 == c ? "0px" : b.x + "px", f.style.top = b.y + "px", f.style.width = b.w + "px", f.style.height = b.h + "px", f.style.fontSize = a.getOption("yLabelWidth") - 2 + "px";
        var g = document.createElement("div");
        g.style.position = "absolute", g.style.width = b.h + "px", g.style.height = b.w + "px", g.style.top = b.h / 2 - b.w / 2 + "px", g.style.left = b.w / 2 - b.h / 2 + "px", g.style.textAlign = "center";
        var h = "rotate(" + (1 == c ? "-" : "") + "90deg)";
        g.style.transform = h, g.style.WebkitTransform = h, g.style.MozTransform = h, g.style.OTransform = h, g.style.msTransform = h, "undefined" != typeof document.documentMode && document.documentMode < 9 && (g.style.filter = "progid:DXImageTransform.Microsoft.BasicImage(rotation=" + (1 == c ? "3" : "1") + ")", g.style.left = "0px", g.style.top = "0px");
        var i = document.createElement("div");
        return i.className = d, i.innerHTML = e, g.appendChild(i), f.appendChild(g), f
    };
    return a.prototype.layout = function(a) {
        this.detachLabels_();
        var d = a.dygraph,
            e = a.chart_div;
        if (d.getOption("title")) {
            var f = a.reserveSpaceTop(d.getOption("titleHeight"));
            this.title_div_ = b(f), this.title_div_.style.textAlign = "center", this.title_div_.style.fontSize = d.getOption("titleHeight") - 8 + "px", this.title_div_.style.fontWeight = "bold", this.title_div_.style.zIndex = 10;
            var g = document.createElement("div");
            g.className = "dygraph-label dygraph-title", g.innerHTML = d.getOption("title"), this.title_div_.appendChild(g), e.appendChild(this.title_div_)
        }
        if (d.getOption("xlabel")) {
            var h = a.reserveSpaceBottom(d.getOption("xLabelHeight"));
            this.xlabel_div_ = b(h), this.xlabel_div_.style.textAlign = "center", this.xlabel_div_.style.fontSize = d.getOption("xLabelHeight") - 2 + "px";
            var g = document.createElement("div");
            g.className = "dygraph-label dygraph-xlabel", g.innerHTML = d.getOption("xlabel"), this.xlabel_div_.appendChild(g), e.appendChild(this.xlabel_div_)
        }
        if (d.getOption("ylabel")) {
            var i = a.reserveSpaceLeft(0);
            this.ylabel_div_ = c(d, i, 1, "dygraph-label dygraph-ylabel", d.getOption("ylabel")), e.appendChild(this.ylabel_div_)
        }
        if (d.getOption("y2label") && 2 == d.numAxes()) {
            var j = a.reserveSpaceRight(0);
            this.y2label_div_ = c(d, j, 2, "dygraph-label dygraph-y2label", d.getOption("y2label")), e.appendChild(this.y2label_div_)
        }
    }, a.prototype.didDrawChart = function(a) {
        var b = a.dygraph;
        this.title_div_ && (this.title_div_.children[0].innerHTML = b.getOption("title")), this.xlabel_div_ && (this.xlabel_div_.children[0].innerHTML = b.getOption("xlabel")), this.ylabel_div_ && (this.ylabel_div_.children[0].children[0].innerHTML = b.getOption("ylabel")), this.y2label_div_ && (this.y2label_div_.children[0].children[0].innerHTML = b.getOption("y2label"))
    }, a.prototype.clearChart = function() {}, a.prototype.destroy = function() {
        this.detachLabels_()
    }, a
}(), Dygraph.Plugins.Grid = function() {
    var a = function() {};
    return a.prototype.toString = function() {
        return "Gridline Plugin"
    }, a.prototype.activate = function() {
        return {
            "willDrawChart": this.willDrawChart
        }
    }, a.prototype.willDrawChart = function(a) {
        function b(a) {
            return Math.round(a) + .5
        }

        function c(a) {
            return Math.round(a) - .5
        }
        var d, e, f, g, h = a.dygraph,
            i = a.drawingContext,
            j = h.layout_,
            k = a.dygraph.plotter_.area;
        if (h.getOption("drawYGrid")) {
            for (var l = ["y", "y2"], m = [], n = [], o = [], p = [], q = [], f = 0; f < l.length; f++) o[f] = h.getOptionForAxis("drawGrid", l[f]), o[f] && (m[f] = h.getOptionForAxis("gridLineColor", l[f]), n[f] = h.getOptionForAxis("gridLineWidth", l[f]), q[f] = h.getOptionForAxis("gridLinePattern", l[f]), p[f] = q[f] && q[f].length >= 2);
            for (g = j.yticks, i.save(), f = 0; f < g.length; f++) {
                var r = g[f][0];
                o[r] && (p[r] && i.installPattern(q[r]), i.strokeStyle = m[r], i.lineWidth = n[r], d = b(k.x), e = c(k.y + g[f][1] * k.h), i.beginPath(), i.moveTo(d, e), i.lineTo(d + k.w, e), i.closePath(), i.stroke(), p[r] && i.uninstallPattern())
            }
            i.restore()
        }
        if (h.getOption("drawXGrid") && h.getOptionForAxis("drawGrid", "x")) {
            g = j.xticks, i.save();
            var q = h.getOptionForAxis("gridLinePattern", "x"),
                p = q && q.length >= 2;
            for (p && i.installPattern(q), i.strokeStyle = h.getOptionForAxis("gridLineColor", "x"), i.lineWidth = h.getOptionForAxis("gridLineWidth", "x"), f = 0; f < g.length; f++) d = b(k.x + g[f][0] * k.w), e = c(k.y + k.h), i.beginPath(), i.moveTo(d, e), i.lineTo(d, k.y), i.closePath(), i.stroke();
            p && i.uninstallPattern(), i.restore()
        }
    }, a.prototype.destroy = function() {}, a
}(), Dygraph.Plugins.Legend = function() {
    var a = function() {
        this.legend_div_ = null, this.is_generated_div_ = !1
    };
    a.prototype.toString = function() {
        return "Legend Plugin"
    };
    var b, c;
    a.prototype.activate = function(a) {
        var b, c = a.getOption("labelsDivWidth"),
            d = a.getOption("labelsDiv");
        if (d && null !== d) b = "string" == typeof d || d instanceof String ? document.getElementById(d) : d;
        else {
            var e = {
                "position": "absolute",
                "fontSize": "14px",
                "zIndex": 10,
                "width": c + "px",
                "top": "0px",
                "left": a.size().width - c - 2 + "px",
                "background": "white",
                "lineHeight": "normal",
                "textAlign": "left",
                "overflow": "hidden"
            };
            Dygraph.update(e, a.getOption("labelsDivStyles")), b = document.createElement("div"), b.className = "dygraph-legend";
            for (var f in e)
                if (e.hasOwnProperty(f)) try {
                    b.style[f] = e[f]
                } catch (g) {
                    this.warn("You are using unsupported css properties for your browser in labelsDivStyles")
                }
                a.graphDiv.appendChild(b), this.is_generated_div_ = !0
        }
        return this.legend_div_ = b, this.one_em_width_ = 10, {
            "select": this.select,
            "deselect": this.deselect,
            "predraw": this.predraw,
            "didDrawChart": this.didDrawChart
        }
    };
    var d = function(a) {
        var b = document.createElement("span");
        b.setAttribute("style", "margin: 0; padding: 0 0 0 1em; border: 0;"), a.appendChild(b);
        var c = b.offsetWidth;
        return a.removeChild(b), c
    };
    return a.prototype.select = function(a) {
        var c = a.selectedX,
            d = a.selectedPoints,
            e = b(a.dygraph, c, d, this.one_em_width_);
        this.legend_div_.innerHTML = e
    }, a.prototype.deselect = function(a) {
        var c = d(this.legend_div_);
        this.one_em_width_ = c;
        var e = b(a.dygraph, void 0, void 0, c);
        this.legend_div_.innerHTML = e
    }, a.prototype.didDrawChart = function(a) {
        this.deselect(a)
    }, a.prototype.predraw = function(a) {
        if (this.is_generated_div_) {
            a.dygraph.graphDiv.appendChild(this.legend_div_);
            var b = a.dygraph.plotter_.area,
                c = a.dygraph.getOption("labelsDivWidth");
            this.legend_div_.style.left = b.x + b.w - c - 1 + "px", this.legend_div_.style.top = b.y + "px", this.legend_div_.style.width = c + "px"
        }
    }, a.prototype.destroy = function() {
        this.legend_div_ = null
    }, b = function(a, b, d, e) {
        if (a.getOption("showLabelsOnHighlight") !== !0) return "";
        var f, g, h, i, j, k = a.getLabels();
        if ("undefined" == typeof b) {
            if ("always" != a.getOption("legend")) return "";
            for (g = a.getOption("labelsSeparateLines"), f = "", h = 1; h < k.length; h++) {
                var l = a.getPropertiesForSeries(k[h]);
                l.visible && ("" !== f && (f += g ? "<br/>" : " "), j = a.getOption("strokePattern", k[h]), i = c(j, l.color, e), f += "<span style='font-weight: bold; color: " + l.color + ";'>" + i + " " + k[h] + "</span>")
            }
            return f
        }
        var m = a.optionsViewForAxis_("x"),
            n = m("valueFormatter");
        f = n(b, m, k[0], a), "" !== f && (f += ":");
        var o = [],
            p = a.numAxes();
        for (h = 0; p > h; h++) o[h] = a.optionsViewForAxis_("y" + (h ? 1 + h : ""));
        var q = a.getOption("labelsShowZeroValues");
        g = a.getOption("labelsSeparateLines");
        var r = a.getHighlightSeries();
        for (h = 0; h < d.length; h++) {
            var s = d[h];
            if ((0 !== s.yval || q) && Dygraph.isOK(s.canvasy)) {
                g && (f += "<br/>");
                var l = a.getPropertiesForSeries(s.name),
                    t = o[l.axis - 1],
                    u = t("valueFormatter"),
                    v = u(s.yval, t, s.name, a),
                    w = s.name == r ? " class='highlight'" : "";
                f += "<span" + w + "> <b><span style='color: " + l.color + ";'>" + s.name + "</span></b>:&nbsp;" + v + "</span>"
            }
        }
        return f
    }, c = function(a, b, c) {
        var d = /MSIE/.test(navigator.userAgent) && !window.opera;
        if (d) return "&mdash;";
        if (!a || a.length <= 1) return '<div style="display: inline-block; position: relative; bottom: .5ex; padding-left: 1em; height: 1px; border-bottom: 2px solid ' + b + ';"></div>';
        var e, f, g, h, i, j = 0,
            k = 0,
            l = [];
        for (e = 0; e <= a.length; e++) j += a[e % a.length];
        if (i = Math.floor(c / (j - a[0])), i > 1) {
            for (e = 0; e < a.length; e++) l[e] = a[e] / c;
            k = l.length
        } else {
            for (i = 1, e = 0; e < a.length; e++) l[e] = a[e] / j;
            k = l.length + 1
        }
        var m = "";
        for (f = 0; i > f; f++)
            for (e = 0; k > e; e += 2) g = l[e % l.length], h = e < a.length ? l[(e + 1) % l.length] : 0, m += '<div style="display: inline-block; position: relative; bottom: .5ex; margin-right: ' + h + "em; padding-left: " + g + "em; height: 1px; border-bottom: 2px solid " + b + ';"></div>';
        return m
    }, a
}(), Dygraph.Plugins.RangeSelector = function() {
    var a = function() {
        this.isIE_ = /MSIE/.test(navigator.userAgent) && !window.opera, this.hasTouchInterface_ = "undefined" != typeof TouchEvent, this.isMobileDevice_ = /mobile|android/gi.test(navigator.appVersion), this.interfaceCreated_ = !1
    };
    return a.prototype.toString = function() {
        return "RangeSelector Plugin"
    }, a.prototype.activate = function(a) {
        return this.dygraph_ = a, this.isUsingExcanvas_ = a.isUsingExcanvas_, this.getOption_("showRangeSelector") && this.createInterface_(), {
            "layout": this.reserveSpace_,
            "predraw": this.renderStaticLayer_,
            "didDrawChart": this.renderInteractiveLayer_
        }
    }, a.prototype.destroy = function() {
        this.bgcanvas_ = null, this.fgcanvas_ = null, this.leftZoomHandle_ = null, this.rightZoomHandle_ = null, this.iePanOverlay_ = null
    }, a.prototype.getOption_ = function(a) {
        return this.dygraph_.getOption(a)
    }, a.prototype.setDefaultOption_ = function(a, b) {
        return this.dygraph_.attrs_[a] = b
    }, a.prototype.createInterface_ = function() {
        this.createCanvases_(), this.isUsingExcanvas_ && this.createIEPanOverlay_(), this.createZoomHandles_(), this.initInteraction_(), this.getOption_("animatedZooms") && (this.dygraph_.warn("Animated zooms and range selector are not compatible; disabling animatedZooms."), this.dygraph_.updateOptions({
            "animatedZooms": !1
        }, !0)), this.interfaceCreated_ = !0, this.addToGraph_()
    }, a.prototype.addToGraph_ = function() {
        var a = this.graphDiv_ = this.dygraph_.graphDiv;
        a.appendChild(this.bgcanvas_), a.appendChild(this.fgcanvas_), a.appendChild(this.leftZoomHandle_), a.appendChild(this.rightZoomHandle_)
    }, a.prototype.removeFromGraph_ = function() {
        var a = this.graphDiv_;
        a.removeChild(this.bgcanvas_), a.removeChild(this.fgcanvas_), a.removeChild(this.leftZoomHandle_), a.removeChild(this.rightZoomHandle_), this.graphDiv_ = null
    }, a.prototype.reserveSpace_ = function(a) {
        this.getOption_("showRangeSelector") && a.reserveSpaceBottom(this.getOption_("rangeSelectorHeight") + 4)
    }, a.prototype.renderStaticLayer_ = function() {
        this.updateVisibility_() && (this.resize_(), this.drawStaticLayer_())
    }, a.prototype.renderInteractiveLayer_ = function() {
        this.updateVisibility_() && !this.isChangingRange_ && (this.placeZoomHandles_(), this.drawInteractiveLayer_())
    }, a.prototype.updateVisibility_ = function() {
        var a = this.getOption_("showRangeSelector");
        if (a) this.interfaceCreated_ ? this.graphDiv_ && this.graphDiv_.parentNode || this.addToGraph_() : this.createInterface_();
        else if (this.graphDiv_) {
            this.removeFromGraph_();
            var b = this.dygraph_;
            setTimeout(function() {
                b.width_ = 0, b.resize()
            }, 1)
        }
        return a
    }, a.prototype.resize_ = function() {
        function a(a, b) {
            a.style.top = b.y + "px", a.style.left = b.x + "px", a.width = b.w, a.height = b.h, a.style.width = a.width + "px", a.style.height = a.height + "px"
        }
        var b = this.dygraph_.layout_.getPlotArea(),
            c = 0;
        this.getOption_("drawXAxis") && (c = this.getOption_("xAxisHeight") || this.getOption_("axisLabelFontSize") + 2 * this.getOption_("axisTickSize")), this.canvasRect_ = {
            "x": b.x,
            "y": b.y + b.h + c + 4,
            "w": b.w,
            "h": this.getOption_("rangeSelectorHeight")
        }, a(this.bgcanvas_, this.canvasRect_), a(this.fgcanvas_, this.canvasRect_)
    }, a.prototype.createCanvases_ = function() {
        this.bgcanvas_ = Dygraph.createCanvas(), this.bgcanvas_.className = "dygraph-rangesel-bgcanvas", this.bgcanvas_.style.position = "absolute", this.bgcanvas_.style.zIndex = 9, this.bgcanvas_ctx_ = Dygraph.getContext(this.bgcanvas_), this.fgcanvas_ = Dygraph.createCanvas(), this.fgcanvas_.className = "dygraph-rangesel-fgcanvas", this.fgcanvas_.style.position = "absolute", this.fgcanvas_.style.zIndex = 9, this.fgcanvas_.style.cursor = "default", this.fgcanvas_ctx_ = Dygraph.getContext(this.fgcanvas_)
    }, a.prototype.createIEPanOverlay_ = function() {
        this.iePanOverlay_ = document.createElement("div"), this.iePanOverlay_.style.position = "absolute", this.iePanOverlay_.style.backgroundColor = "white", this.iePanOverlay_.style.filter = "alpha(opacity=0)", this.iePanOverlay_.style.display = "none", this.iePanOverlay_.style.cursor = "move", this.fgcanvas_.appendChild(this.iePanOverlay_)
    }, a.prototype.createZoomHandles_ = function() {
        var a = new Image;
        a.className = "dygraph-rangesel-zoomhandle", a.style.position = "absolute", a.style.zIndex = 10, a.style.visibility = "hidden", a.style.cursor = "col-resize", /MSIE 7/.test(navigator.userAgent) ? (a.width = 7, a.height = 14, a.style.backgroundColor = "white", a.style.border = "1px solid #333333") : (a.width = 9, a.height = 16, a.src = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAQCAYAAADESFVDAAAAAXNSR0IArs4c6QAAAAZiS0dEANAAzwDP4Z7KegAAAAlwSFlzAAAOxAAADsQBlSsOGwAAAAd0SU1FB9sHGw0cMqdt1UwAAAAZdEVYdENvbW1lbnQAQ3JlYXRlZCB3aXRoIEdJTVBXgQ4XAAAAaElEQVQoz+3SsRFAQBCF4Z9WJM8KCDVwownl6YXsTmCUsyKGkZzcl7zkz3YLkypgAnreFmDEpHkIwVOMfpdi9CEEN2nGpFdwD03yEqDtOgCaun7sqSTDH32I1pQA2Pb9sZecAxc5r3IAb21d6878xsAAAAAASUVORK5CYII="), this.isMobileDevice_ && (a.width *= 2, a.height *= 2), this.leftZoomHandle_ = a, this.rightZoomHandle_ = a.cloneNode(!1)
    }, a.prototype.initInteraction_ = function() {
        var a, b, c, d, e, f, g, h, i, j, k, l, m, n, o = this,
            p = this.isIE_ ? document : window,
            q = 0,
            r = null,
            s = !1,
            t = !1,
            u = !this.isMobileDevice_ && !this.isUsingExcanvas_,
            v = new Dygraph.IFrameTarp;
        a = function(a) {
            var b = o.dygraph_.xAxisExtremes(),
                c = (b[1] - b[0]) / o.canvasRect_.w,
                d = b[0] + (a.leftHandlePos - o.canvasRect_.x) * c,
                e = b[0] + (a.rightHandlePos - o.canvasRect_.x) * c;
            return [d, e]
        }, b = function(a) {
            return Dygraph.cancelEvent(a), s = !0, q = a.clientX, r = a.target ? a.target : a.srcElement, ("mousedown" === a.type || "dragstart" === a.type) && (Dygraph.addEvent(p, "mousemove", c), Dygraph.addEvent(p, "mouseup", d)), o.fgcanvas_.style.cursor = "col-resize", v.cover(), !0
        }, c = function(a) {
            if (!s) return !1;
            Dygraph.cancelEvent(a);
            var b = a.clientX - q;
            if (Math.abs(b) < 4) return !0;
            q = a.clientX;
            var c, d = o.getZoomHandleStatus_();
            r == o.leftZoomHandle_ ? (c = d.leftHandlePos + b, c = Math.min(c, d.rightHandlePos - r.width - 3), c = Math.max(c, o.canvasRect_.x)) : (c = d.rightHandlePos + b, c = Math.min(c, o.canvasRect_.x + o.canvasRect_.w), c = Math.max(c, d.leftHandlePos + r.width + 3));
            var f = r.width / 2;
            return r.style.left = c - f + "px", o.drawInteractiveLayer_(), u && e(), !0
        }, d = function() {
            return s ? (s = !1, v.uncover(), Dygraph.removeEvent(p, "mousemove", c), Dygraph.removeEvent(p, "mouseup", d), o.fgcanvas_.style.cursor = "default", u || e(), !0) : !1
        }, e = function() {
            try {
                var b = o.getZoomHandleStatus_();
                if (o.isChangingRange_ = !0, b.isZoomed) {
                    var c = a(b);
                    o.dygraph_.doZoomXDates_(c[0], c[1])
                } else o.dygraph_.resetZoom()
            } finally {
                o.isChangingRange_ = !1
            }
        }, f = function(a) {
            if (o.isUsingExcanvas_) return a.srcElement == o.iePanOverlay_;
            var b = o.leftZoomHandle_.getBoundingClientRect(),
                c = b.left + b.width / 2;
            b = o.rightZoomHandle_.getBoundingClientRect();
            var d = b.left + b.width / 2;
            return a.clientX > c && a.clientX < d
        }, g = function(a) {
            return !t && f(a) && o.getZoomHandleStatus_().isZoomed ? (Dygraph.cancelEvent(a), t = !0, q = a.clientX, "mousedown" === a.type && (Dygraph.addEvent(p, "mousemove", h), Dygraph.addEvent(p, "mouseup", i)), !0) : !1
        }, h = function(a) {
            if (!t) return !1;
            Dygraph.cancelEvent(a);
            var b = a.clientX - q;
            if (Math.abs(b) < 4) return !0;
            q = a.clientX;
            var c = o.getZoomHandleStatus_(),
                d = c.leftHandlePos,
                e = c.rightHandlePos,
                f = e - d;
            d + b <= o.canvasRect_.x ? (d = o.canvasRect_.x, e = d + f) : e + b >= o.canvasRect_.x + o.canvasRect_.w ? (e = o.canvasRect_.x + o.canvasRect_.w, d = e - f) : (d += b, e += b);
            var g = o.leftZoomHandle_.width / 2;
            return o.leftZoomHandle_.style.left = d - g + "px", o.rightZoomHandle_.style.left = e - g + "px", o.drawInteractiveLayer_(), u && j(), !0
        }, i = function() {
            return t ? (t = !1, Dygraph.removeEvent(p, "mousemove", h), Dygraph.removeEvent(p, "mouseup", i), u || j(), !0) : !1
        }, j = function() {
            try {
                o.isChangingRange_ = !0, o.dygraph_.dateWindow_ = a(o.getZoomHandleStatus_()), o.dygraph_.drawGraph_(!1)
            } finally {
                o.isChangingRange_ = !1
            }
        }, k = function(a) {
            if (!s && !t) {
                var b = f(a) ? "move" : "default";
                b != o.fgcanvas_.style.cursor && (o.fgcanvas_.style.cursor = b)
            }
        }, l = function(a) {
            "touchstart" == a.type && 1 == a.targetTouches.length ? b(a.targetTouches[0]) && Dygraph.cancelEvent(a) : "touchmove" == a.type && 1 == a.targetTouches.length ? c(a.targetTouches[0]) && Dygraph.cancelEvent(a) : d(a)
        }, m = function(a) {
            "touchstart" == a.type && 1 == a.targetTouches.length ? g(a.targetTouches[0]) && Dygraph.cancelEvent(a) : "touchmove" == a.type && 1 == a.targetTouches.length ? h(a.targetTouches[0]) && Dygraph.cancelEvent(a) : i(a)
        }, n = function(a, b) {
            for (var c = ["touchstart", "touchend", "touchmove", "touchcancel"], d = 0; d < c.length; d++) o.dygraph_.addAndTrackEvent(a, c[d], b)
        }, this.setDefaultOption_("interactionModel", Dygraph.Interaction.dragIsPanInteractionModel), this.setDefaultOption_("panEdgeFraction", 1e-4);
        var w = window.opera ? "mousedown" : "dragstart";
        this.dygraph_.addAndTrackEvent(this.leftZoomHandle_, w, b), this.dygraph_.addAndTrackEvent(this.rightZoomHandle_, w, b), this.isUsingExcanvas_ ? this.dygraph_.addAndTrackEvent(this.iePanOverlay_, "mousedown", g) : (this.dygraph_.addAndTrackEvent(this.fgcanvas_, "mousedown", g), this.dygraph_.addAndTrackEvent(this.fgcanvas_, "mousemove", k)), this.hasTouchInterface_ && (n(this.leftZoomHandle_, l), n(this.rightZoomHandle_, l), n(this.fgcanvas_, m))
    }, a.prototype.drawStaticLayer_ = function() {
        var a = this.bgcanvas_ctx_;
        a.clearRect(0, 0, this.canvasRect_.w, this.canvasRect_.h);
        try {
            this.drawMiniPlot_()
        } catch (b) {
            Dygraph.warn(b)
        }
        var c = .5;
        this.bgcanvas_ctx_.lineWidth = 1, a.strokeStyle = "gray", a.beginPath(), a.moveTo(c, c), a.lineTo(c, this.canvasRect_.h - c), a.lineTo(this.canvasRect_.w - c, this.canvasRect_.h - c), a.lineTo(this.canvasRect_.w - c, c), a.stroke()
    }, a.prototype.drawMiniPlot_ = function() {
        var a = this.getOption_("rangeSelectorPlotFillColor"),
            b = this.getOption_("rangeSelectorPlotStrokeColor");
        if (a || b) {
            var c = this.getOption_("stepPlot"),
                d = this.computeCombinedSeriesAndLimits_(),
                e = d.yMax - d.yMin,
                f = this.bgcanvas_ctx_,
                g = .5,
                h = this.dygraph_.xAxisExtremes(),
                i = Math.max(h[1] - h[0], 1e-30),
                j = (this.canvasRect_.w - g) / i,
                k = (this.canvasRect_.h - g) / e,
                l = this.canvasRect_.w - g,
                m = this.canvasRect_.h - g,
                n = null,
                o = null;
            f.beginPath(), f.moveTo(g, m);
            for (var p = 0; p < d.data.length; p++) {
                var q = d.data[p],
                    r = null !== q[0] ? (q[0] - h[0]) * j : 0 / 0,
                    s = null !== q[1] ? m - (q[1] - d.yMin) * k : 0 / 0;
                isFinite(r) && isFinite(s) ? (null === n ? f.lineTo(r, m) : c && f.lineTo(r, o), f.lineTo(r, s), n = r, o = s) : (null !== n && (c ? (f.lineTo(r, o), f.lineTo(r, m)) : f.lineTo(n, m)), n = o = null)
            }
            if (f.lineTo(l, m), f.closePath(), a) {
                var t = this.bgcanvas_ctx_.createLinearGradient(0, 0, 0, m);
                t.addColorStop(0, "white"), t.addColorStop(1, a), this.bgcanvas_ctx_.fillStyle = t, f.fill()
            }
            b && (this.bgcanvas_ctx_.strokeStyle = b, this.bgcanvas_ctx_.lineWidth = 1.5, f.stroke())
        }
    }, a.prototype.computeCombinedSeriesAndLimits_ = function() {
        var a, b, c, d, e, f, g, h, i = this.dygraph_.rawData_,
            j = this.getOption_("logscale"),
            k = [];
        for (d = 0; d < i.length; d++)
            if (i[d].length > 1 && null !== i[d][1]) {
                if (c = "number" != typeof i[d][1])
                    for (a = [], b = [], f = 0; f < i[d][1].length; f++) a.push(0), b.push(0);
                break
            }
        for (d = 0; d < i.length; d++) {
            var l = i[d];
            if (g = l[0], c)
                for (f = 0; f < a.length; f++) a[f] = b[f] = 0;
            else a = b = 0;
            for (e = 1; e < l.length; e++)
                if (this.dygraph_.visibility()[e - 1]) {
                    var m;
                    if (c)
                        for (f = 0; f < a.length; f++) m = l[e][f], null === m || isNaN(m) || (a[f] += m, b[f]++);
                    else {
                        if (m = l[e], null === m || isNaN(m)) continue;
                        a += m, b++
                    }
                }
            if (c) {
                for (f = 0; f < a.length; f++) a[f] /= b[f];
                h = a.slice(0)
            } else h = a / b;
            k.push([g, h])
        }
        if (k = this.dygraph_.rollingAverage(k, this.dygraph_.rollPeriod_), "number" != typeof k[0][1])
            for (d = 0; d < k.length; d++) h = k[d][1], k[d][1] = h[0];
        var n = Number.MAX_VALUE,
            o = -Number.MAX_VALUE;
        for (d = 0; d < k.length; d++) h = k[d][1], null !== h && isFinite(h) && (!j || h > 0) && (n = Math.min(n, h), o = Math.max(o, h));
        var p = .25;
        if (j)
            for (o = Dygraph.log10(o), o += o * p, n = Dygraph.log10(n), d = 0; d < k.length; d++) k[d][1] = Dygraph.log10(k[d][1]);
        else {
            var q, r = o - n;
            q = r <= Number.MIN_VALUE ? o * p : r * p, o += q, n -= q
        }
        return {
            "data": k,
            "yMin": n,
            "yMax": o
        }
    }, a.prototype.placeZoomHandles_ = function() {
        var a = this.dygraph_.xAxisExtremes(),
            b = this.dygraph_.xAxisRange(),
            c = a[1] - a[0],
            d = Math.max(0, (b[0] - a[0]) / c),
            e = Math.max(0, (a[1] - b[1]) / c),
            f = this.canvasRect_.x + this.canvasRect_.w * d,
            g = this.canvasRect_.x + this.canvasRect_.w * (1 - e),
            h = Math.max(this.canvasRect_.y, this.canvasRect_.y + (this.canvasRect_.h - this.leftZoomHandle_.height) / 2),
            i = this.leftZoomHandle_.width / 2;
        this.leftZoomHandle_.style.left = f - i + "px", this.leftZoomHandle_.style.top = h + "px", this.rightZoomHandle_.style.left = g - i + "px", this.rightZoomHandle_.style.top = this.leftZoomHandle_.style.top, this.leftZoomHandle_.style.visibility = "visible", this.rightZoomHandle_.style.visibility = "visible"
    }, a.prototype.drawInteractiveLayer_ = function() {
        var a = this.fgcanvas_ctx_;
        a.clearRect(0, 0, this.canvasRect_.w, this.canvasRect_.h);
        var b = 1,
            c = this.canvasRect_.w - b,
            d = this.canvasRect_.h - b,
            e = this.getZoomHandleStatus_();
        if (a.strokeStyle = "black", e.isZoomed) {
            var f = Math.max(b, e.leftHandlePos - this.canvasRect_.x),
                g = Math.min(c, e.rightHandlePos - this.canvasRect_.x);
            a.fillStyle = "rgba(240, 240, 240, 0.6)", a.fillRect(0, 0, f, this.canvasRect_.h), a.fillRect(g, 0, this.canvasRect_.w - g, this.canvasRect_.h), a.beginPath(), a.moveTo(b, b), a.lineTo(f, b), a.lineTo(f, d), a.lineTo(g, d), a.lineTo(g, b), a.lineTo(c, b), a.stroke(), this.isUsingExcanvas_ && (this.iePanOverlay_.style.width = g - f + "px", this.iePanOverlay_.style.left = f + "px", this.iePanOverlay_.style.height = d + "px", this.iePanOverlay_.style.display = "inline")
        } else a.beginPath(), a.moveTo(b, b), a.lineTo(b, d), a.lineTo(c, d), a.lineTo(c, b), a.stroke(), this.iePanOverlay_ && (this.iePanOverlay_.style.display = "none")
    }, a.prototype.getZoomHandleStatus_ = function() {
        var a = this.leftZoomHandle_.width / 2,
            b = parseFloat(this.leftZoomHandle_.style.left) + a,
            c = parseFloat(this.rightZoomHandle_.style.left) + a;
        return {
            "leftHandlePos": b,
            "rightHandlePos": c,
            "isZoomed": b - 1 > this.canvasRect_.x || c + 1 < this.canvasRect_.x + this.canvasRect_.w
        }
    }, a
}(), Dygraph.PLUGINS.push(Dygraph.Plugins.Legend, Dygraph.Plugins.Axes, Dygraph.Plugins.RangeSelector, Dygraph.Plugins.ChartLabels, Dygraph.Plugins.Annotations, Dygraph.Plugins.Grid);