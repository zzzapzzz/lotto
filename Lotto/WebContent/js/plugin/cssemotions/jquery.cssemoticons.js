/*! SmartAdmin - v1.4.2 - 2014-08-21 */

!function(a) {
    a.fn.emoticonize=function(b) {
        for(var c=a.extend( {}
        , a.fn.emoticonize.defaults, b), d=[")", "(", "*", "[", "]", "{", "}", "|", "^", "<", ">", "\\", "?", "+", "=", "."], e=[":-)", ":o)", ":c)", ":^)", ":-D", ":-(", ":-9", ";-)", ":-P", ":-p", ":-\xde", ":-b", ":-O", ":-/", ":-X", ":-#", ":'(", "B-)", "8-)", ";*(", ":-*", ":-\\", "?-)", ": )", ": ]", "= ]", "= )", "8 )", ": }", ": D", "8 D", "X D", "x D", "= D", ": (", ": [", ": {", "= (", "; )", "; ]", "; D", ": P", ": p", "= P", "= p", ": b", ": \xde", ": O", "8 O", ": /", "= /", ": S", ": #", ": X", "B )", ": |", ": \\", "= \\", ": *", ": &gt;", ": &lt;"], f=[":)", ":]", "=]", "=)", "8)", ":}", ":D", ":(", ":[", ":{", "=(", ";)", ";]", ";D", ":P", ":p", "=P", "=p", ":b", ":\xde", ":O", ":/", "=/", ":S", ":#", ":X", "B)", ":|", ":\\", "=\\", ":*", ":&gt;", ":&lt;"], g= {
            "&gt;:)": {
                "cssClass": "red-emoticon small-emoticon spaced-emoticon"
            }
            , "&gt;;)": {
                "cssClass": "red-emoticon small-emoticon spaced-emoticon"
            }
            , "&gt;:(": {
                "cssClass": "red-emoticon small-emoticon spaced-emoticon"
            }
            , "&gt;: )": {
                "cssClass": "red-emoticon small-emoticon"
            }
            , "&gt;; )": {
                "cssClass": "red-emoticon small-emoticon"
            }
            , "&gt;: (": {
                "cssClass": "red-emoticon small-emoticon"
            }
            , ";(": {
                "cssClass": "red-emoticon spaced-emoticon"
            }
            , "&lt;3": {
                "cssClass": "pink-emoticon counter-rotated"
            }
            , "O_O": {
                "cssClass": "no-rotate"
            }
            , "o_o": {
                "cssClass": "no-rotate"
            }
            , "0_o": {
                "cssClass": "no-rotate"
            }
            , "O_o": {
                "cssClass": "no-rotate"
            }
            , "T_T": {
                "cssClass": "no-rotate"
            }
            , "^_^": {
                "cssClass": "no-rotate"
            }
            , "O:)": {
                "cssClass": "small-emoticon spaced-emoticon"
            }
            , "O: )": {
                "cssClass": "small-emoticon"
            }
            , "8D": {
                "cssClass": "small-emoticon spaced-emoticon"
            }
            , "XD": {
                "cssClass": "small-emoticon spaced-emoticon"
            }
            , "xD": {
                "cssClass": "small-emoticon spaced-emoticon"
            }
            , "=D": {
                "cssClass": "small-emoticon spaced-emoticon"
            }
            , "8O": {
                "cssClass": "small-emoticon spaced-emoticon"
            }
            , "[+=..]": {
                "cssClass": "no-rotate nintendo-controller"
            }
        }
        , h=new RegExp("(\\"+d.join("|\\")+")", "g"), i="(^|[\\s\\0])", j=e.length-1;
        j>=0;
        --j)e[j]=e[j].replace(h, "\\$1"),
        e[j]=new RegExp(i+"("+e[j]+")", "g");
        for(var j=f.length-1;
        j>=0;
        --j)f[j]=f[j].replace(h, "\\$1"),
        f[j]=new RegExp(i+"("+f[j]+")", "g");
        for(var k in g)g[k].regexp=k.replace(h, "\\$1"),
        g[k].regexp=new RegExp(i+"("+g[k].regexp+")", "g");
        var l="span.css-emoticon";
        c.exclude&&(l+=","+c.exclude);
        var m=l.split(",");
        return this.not(l).each(function() {
            var b=a(this), d="css-emoticon";
            c.animate&&(d+=" un-transformed-emoticon animated-emoticon");
            for(var h in g)specialCssClass=d+" "+g[h].cssClass, b.html(b.html().replace(g[h].regexp, "$1<span class='"+specialCssClass+"'>$2</span>"));
            a(e).each(function() {
                b.html(b.html().replace(this, "$1<span class='"+d+"'>$2</span>"))
            }
            ), a(f).each(function() {
                b.html(b.html().replace(this, "$1<span class='"+d+" spaced-emoticon'>$2</span>"))
            }
            ), a.each(m, function(c, d) {
                b.find(a.trim(d)+" span.css-emoticon").each(function() {
                    a(this).replaceWith(a(this).text())
                }
                )
            }
            ), c.animate&&setTimeout(function() {
                a(".un-transformed-emoticon").removeClass("un-transformed-emoticon")
            }
            , c.delay)
        }
        )
    }
    ,
    a.fn.unemoticonize=function(b) {
        var c=a.extend( {}
        , a.fn.emoticonize.defaults, b);
        return this.each(function() {
            var b=a(this);
            b.find("span.css-emoticon").each(function() {
                var b=a(this);
                c.animate?(b.addClass("un-transformed-emoticon"), setTimeout(function() {
                    b.replaceWith(b.text())
                }
                , c.delay)):b.replaceWith(b.text())
            }
            )
        }
        )
    }
    ,
    a.fn.emoticonize.defaults= {
        "animate": !0, "delay": 500, "exclude": "pre,code,.no-emoticons"
    }
}

(jQuery);