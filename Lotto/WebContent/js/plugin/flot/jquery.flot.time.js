/*! SmartAdmin - v1.5 - 2014-09-27 */ ! function(a) {
    function b(a, b) {
        return b * Math.floor(a / b)
    }

    function c(a, b, c, d) {
        if ("function" == typeof a.strftime) return a.strftime(b);
        var e = function(a, b) {
                return a = "" + a, b = "" + (null == b ? "0" : b), 1 == a.length ? b + a : a
            },
            f = [],
            g = !1,
            h = a.getHours(),
            i = 12 > h;
        null == c && (c = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]), null == d && (d = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"]);
        var j;
        j = h > 12 ? h - 12 : 0 == h ? 12 : h;
        for (var k = 0; k < b.length; ++k) {
            var l = b.charAt(k);
            if (g) {
                switch (l) {
                    case "a":
                        l = "" + d[a.getDay()];
                        break;
                    case "b":
                        l = "" + c[a.getMonth()];
                        break;
                    case "d":
                        l = e(a.getDate());
                        break;
                    case "e":
                        l = e(a.getDate(), " ");
                        break;
                    case "h":
                    case "H":
                        l = e(h);
                        break;
                    case "I":
                        l = e(j);
                        break;
                    case "l":
                        l = e(j, " ");
                        break;
                    case "m":
                        l = e(a.getMonth() + 1);
                        break;
                    case "M":
                        l = e(a.getMinutes());
                        break;
                    case "q":
                        l = "" + (Math.floor(a.getMonth() / 3) + 1);
                        break;
                    case "S":
                        l = e(a.getSeconds());
                        break;
                    case "y":
                        l = e(a.getFullYear() % 100);
                        break;
                    case "Y":
                        l = "" + a.getFullYear();
                        break;
                    case "p":
                        l = i ? "am" : "pm";
                        break;
                    case "P":
                        l = i ? "AM" : "PM";
                        break;
                    case "w":
                        l = "" + a.getDay()
                }
                f.push(l), g = !1
            } else "%" == l ? g = !0 : f.push(l)
        }
        return f.join("")
    }

    function d(a) {
        function b(a, b, c, d) {
            a[b] = function() {
                return c[d].apply(c, arguments)
            }
        }
        var c = {
            "date": a
        };
        void 0 != a.strftime && b(c, "strftime", a, "strftime"), b(c, "getTime", a, "getTime"), b(c, "setTime", a, "setTime");
        for (var d = ["Date", "Day", "FullYear", "Hours", "Milliseconds", "Minutes", "Month", "Seconds"], e = 0; e < d.length; e++) b(c, "get" + d[e], a, "getUTC" + d[e]), b(c, "set" + d[e], a, "setUTC" + d[e]);
        return c
    }

    function e(a, b) {
        if ("browser" == b.timezone) return new Date(a);
        if (b.timezone && "utc" != b.timezone) {
            if ("undefined" != typeof timezoneJS && "undefined" != typeof timezoneJS.Date) {
                var c = new timezoneJS.Date;
                return c.setTimezone(b.timezone), c.setTime(a), c
            }
            return d(new Date(a))
        }
        return d(new Date(a))
    }

    function f(d) {
        d.hooks.processOptions.push(function(d) {
            a.each(d.getAxes(), function(a, d) {
                var f = d.options;
                "time" == f.mode && (d.tickGenerator = function(a) {
                    var c = [],
                        d = e(a.min, f),
                        g = 0,
                        i = f.tickSize && "quarter" === f.tickSize[1] || f.minTickSize && "quarter" === f.minTickSize[1] ? k : j;
                    null != f.minTickSize && (g = "number" == typeof f.tickSize ? f.tickSize : f.minTickSize[0] * h[f.minTickSize[1]]);
                    for (var l = 0; l < i.length - 1 && !(a.delta < (i[l][0] * h[i[l][1]] + i[l + 1][0] * h[i[l + 1][1]]) / 2 && i[l][0] * h[i[l][1]] >= g); ++l);
                    var m = i[l][0],
                        n = i[l][1];
                    if ("year" == n) {
                        if (null != f.minTickSize && "year" == f.minTickSize[1]) m = Math.floor(f.minTickSize[0]);
                        else {
                            var o = Math.pow(10, Math.floor(Math.log(a.delta / h.year) / Math.LN10)),
                                p = a.delta / h.year / o;
                            m = 1.5 > p ? 1 : 3 > p ? 2 : 7.5 > p ? 5 : 10, m *= o
                        }
                        1 > m && (m = 1)
                    }
                    a.tickSize = f.tickSize || [m, n];
                    var q = a.tickSize[0];
                    n = a.tickSize[1];
                    var r = q * h[n];
                    "second" == n ? d.setSeconds(b(d.getSeconds(), q)) : "minute" == n ? d.setMinutes(b(d.getMinutes(), q)) : "hour" == n ? d.setHours(b(d.getHours(), q)) : "month" == n ? d.setMonth(b(d.getMonth(), q)) : "quarter" == n ? d.setMonth(3 * b(d.getMonth() / 3, q)) : "year" == n && d.setFullYear(b(d.getFullYear(), q)), d.setMilliseconds(0), r >= h.minute && d.setSeconds(0), r >= h.hour && d.setMinutes(0), r >= h.day && d.setHours(0), r >= 4 * h.day && d.setDate(1), r >= 2 * h.month && d.setMonth(b(d.getMonth(), 3)), r >= 2 * h.quarter && d.setMonth(b(d.getMonth(), 6)), r >= h.year && d.setMonth(0);
                    var s, t = 0,
                        u = Number.NaN;
                    do
                        if (s = u, u = d.getTime(), c.push(u), "month" == n || "quarter" == n)
                            if (1 > q) {
                                d.setDate(1);
                                var v = d.getTime();
                                d.setMonth(d.getMonth() + ("quarter" == n ? 3 : 1));
                                var w = d.getTime();
                                d.setTime(u + t * h.hour + (w - v) * q), t = d.getHours(), d.setHours(0)
                            } else d.setMonth(d.getMonth() + q * ("quarter" == n ? 3 : 1));
                    else "year" == n ? d.setFullYear(d.getFullYear() + q) : d.setTime(u + r); while (u < a.max && u != s);
                    return c
                }, d.tickFormatter = function(a, b) {
                    var d = e(a, b.options);
                    if (null != f.timeformat) return c(d, f.timeformat, f.monthNames, f.dayNames);
                    var g, i = b.options.tickSize && "quarter" == b.options.tickSize[1] || b.options.minTickSize && "quarter" == b.options.minTickSize[1],
                        j = b.tickSize[0] * h[b.tickSize[1]],
                        k = b.max - b.min,
                        l = f.twelveHourClock ? " %p" : "",
                        m = f.twelveHourClock ? "%I" : "%H";
                    g = j < h.minute ? m + ":%M:%S" + l : j < h.day ? k < 2 * h.day ? m + ":%M" + l : "%b %d " + m + ":%M" + l : j < h.month ? "%b %d" : i && j < h.quarter || !i && j < h.year ? k < h.year ? "%b" : "%b %Y" : i && j < h.year ? k < h.year ? "Q%q" : "Q%q %Y" : "%Y";
                    var n = c(d, g, f.monthNames, f.dayNames);
                    return n
                })
            })
        })
    }
    var g = {
            "xaxis": {
                "timezone": null,
                "timeformat": null,
                "twelveHourClock": !1,
                "monthNames": null
            }
        },
        h = {
            "second": 1e3,
            "minute": 6e4,
            "hour": 36e5,
            "day": 864e5,
            "month": 2592e6,
            "quarter": 7776e6,
            "year": 525949.2 * 60 * 1e3
        },
        i = [
            [1, "second"],
            [2, "second"],
            [5, "second"],
            [10, "second"],
            [30, "second"],
            [1, "minute"],
            [2, "minute"],
            [5, "minute"],
            [10, "minute"],
            [30, "minute"],
            [1, "hour"],
            [2, "hour"],
            [4, "hour"],
            [8, "hour"],
            [12, "hour"],
            [1, "day"],
            [2, "day"],
            [3, "day"],
            [.25, "month"],
            [.5, "month"],
            [1, "month"],
            [2, "month"]
        ],
        j = i.concat([
            [3, "month"],
            [6, "month"],
            [1, "year"]
        ]),
        k = i.concat([
            [1, "quarter"],
            [2, "quarter"],
            [1, "year"]
        ]);
    a.plot.plugins.push({
        "init": f,
        "options": g,
        "name": "time",
        "version": "1.0"
    }), a.plot.formatDate = c, a.plot.dateGenerator = e
}(jQuery);