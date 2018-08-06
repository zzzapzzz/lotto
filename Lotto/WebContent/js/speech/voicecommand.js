/*! SmartAdmin - v1.5 - 2014-09-27 */
if (SpeechRecognition = root.SpeechRecognition || root.webkitSpeechRecognition || root.mozSpeechRecognition || root.msSpeechRecognition || root.oSpeechRecognition, SpeechRecognition && voice_command) {
    $.root_.on("click", '[data-action="voiceCommand"]', function(a) {
        $.root_.hasClass("voice-command-active") ? $.speechApp.stop() : ($.speechApp.start(), $("#speech-btn .popover").fadeIn(350)), a.preventDefault()
    }), $(document).mouseup(function(a) {
        $("#speech-btn .popover").is(a.target) || 0 !== $("#speech-btn .popover").has(a.target).length || $("#speech-btn .popover").fadeOut(250)
    });
    var modal = $('<div class="modal fade" id="voiceModal" tabindex="-1" role="dialog" aria-labelledby="remoteModalLabel" aria-hidden="true"><div class="modal-dialog"><div class="modal-content"></div></div></div>');
    modal.appendTo("body"), debugState && root.console.log("This browser supports Voice Command"), $.speechApp = function(a) {
        return a.start = function() {
            smartSpeechRecognition.addCommands(commands), smartSpeechRecognition ? (smartSpeechRecognition.start(), $.root_.addClass("voice-command-active"), $.speechApp.playON(), voice_localStorage && localStorage.setItem("sm-setautovoice", "true")) : alert("speech plugin not loaded")
        }, a.stop = function() {
            smartSpeechRecognition && (smartSpeechRecognition.abort(), $.root_.removeClass("voice-command-active"), $.speechApp.playOFF(), voice_localStorage && localStorage.setItem("sm-setautovoice", "false"), $("#speech-btn .popover").is(":visible") && $("#speech-btn .popover").fadeOut(250))
        }, a.playON = function() {
            var a = document.createElement("audio");
            navigator.userAgent.match("Firefox/") ? a.setAttribute("src", $.sound_path + "voice_on.ogg") : a.setAttribute("src", $.sound_path + "voice_on.mp3"), a.addEventListener("load", function() {
                a.play()
            }, !0), $.sound_on && (a.pause(), a.play())
        }, a.playOFF = function() {
            var a = document.createElement("audio");
            navigator.userAgent.match("Firefox/") ? a.setAttribute("src", $.sound_path + "voice_off.ogg") : a.setAttribute("src", $.sound_path + "voice_off.mp3"), $.get(), a.addEventListener("load", function() {
                a.play()
            }, !0), $.sound_on && (a.pause(), a.play())
        }, a.playConfirmation = function() {
            var a = document.createElement("audio");
            navigator.userAgent.match("Firefox/") ? a.setAttribute("src", $.sound_path + "voice_alert.ogg") : a.setAttribute("src", $.sound_path + "voice_alert.mp3"), $.get(), a.addEventListener("load", function() {
                a.play()
            }, !0), $.sound_on && (a.pause(), a.play())
        }, a
    }({})
} else $("#speech-btn").addClass("display-none");
(function(a) {
    "use strict";
    if (!SpeechRecognition) return root.smartSpeechRecognition = null, a;
    var b, c, d = [],
        e = {
            "start": [],
            "error": [],
            "end": [],
            "result": [],
            "resultMatch": [],
            "resultNoMatch": [],
            "errorNetwork": [],
            "errorPermissionBlocked": [],
            "errorPermissionDenied": []
        },
        f = 0,
        g = /\s*\((.*?)\)\s*/g,
        h = /(\(\?:[^)]+\))\?/g,
        i = /(\(\?)?:\w+/g,
        j = /\*\w+/g,
        k = /[\-{}\[\]+?.,\\\^$|#]/g,
        l = function(a) {
            return a = a.replace(k, "\\$&").replace(g, "(?:$1)?").replace(i, function(a, b) {
                return b ? a : "([^\\s]+)"
            }).replace(j, "(.*?)").replace(h, "\\s*$1?\\s*"), new RegExp("^" + a + "$", "i")
        },
        m = function(a) {
            a.forEach(function(a) {
                a.callback.apply(a.context)
            })
        },
        n = function() {
            o() || root.smartSpeechRecognition.init({}, !1)
        },
        o = function() {
            return b !== a
        };
    root.smartSpeechRecognition = {
        "init": function(g, h) {
            h = h === a ? !0 : !!h, b && b.abort && b.abort(), b = new SpeechRecognition, b.maxAlternatives = 5, b.continuous = !0, b.lang = voice_command_lang || "en-US", b.onstart = function() {
                m(e.start), debugState && (root.console.log("%c \u2714 SUCCESS: User allowed access the microphone service to start ", debugStyle_success), root.console.log("Language setting is set to: " + b.lang, debugStyle)), $.root_.removeClass("service-not-allowed"), $.root_.addClass("service-allowed")
            }, b.onerror = function(a) {
                switch (m(e.error), a.error) {
                    case "network":
                        m(e.errorNetwork);
                        break;
                    case "not-allowed":
                    case "service-not-allowed":
                        c = !1, $.root_.removeClass("service-allowed"), $.root_.addClass("service-not-allowed"), debugState && root.console.log("%c WARNING: Microphone was not detected (either user denied access or it is not installed properly) ", debugStyle_warning), m((new Date).getTime() - f < 200 ? e.errorPermissionBlocked : e.errorPermissionDenied)
                }
            }, b.onend = function() {
                if (m(e.end), c) {
                    var a = (new Date).getTime() - f;
                    1e3 > a ? setTimeout(root.smartSpeechRecognition.start, 1e3 - a) : root.smartSpeechRecognition.start()
                }
            }, b.onresult = function(a) {
                m(e.result);
                for (var b, c = a.results[a.resultIndex], f = 0; f < c.length; f++) {
                    b = c[f].transcript.trim(), debugState && root.console.log("Speech recognized: %c" + b, debugStyle);
                    for (var g = 0, h = d.length; h > g; g++) {
                        var i = d[g].command.exec(b);
                        if (i) {
                            var j = i.slice(1);
                            debugState && (root.console.log("command matched: %c" + d[g].originalPhrase, debugStyle), j.length && root.console.log("with parameters", j)), d[g].callback.apply(this, j), m(e.resultMatch);
                            var k = ["sound on", "mute", "stop"];
                            return k.indexOf(d[g].originalPhrase) < 0 && ($.smallBox({
                                "title": d[g].originalPhrase,
                                "content": "loading...",
                                "color": "#333",
                                "sound_file": "voice_alert",
                                "timeout": 2e3
                            }), $("#speech-btn .popover").is(":visible") && $("#speech-btn .popover").fadeOut(250)), !0
                        }
                    }
                }
                return m(e.resultNoMatch), $.smallBox({
                    "title": 'Error: <strong> " ' + b + ' " </strong> no match found!',
                    "content": "Please speak clearly into the microphone",
                    "color": "#a90329",
                    "timeout": 5e3,
                    "icon": "fa fa-microphone"
                }), $("#speech-btn .popover").is(":visible") && $("#speech-btn .popover").fadeOut(250), !1
            }, h && (d = []), g.length && this.addCommands(g)
        },
        "start": function(d) {
            n(), d = d || {}, c = d.autoRestart !== a ? !!d.autoRestart : !0, f = (new Date).getTime(), b.start()
        },
        "abort": function() {
            c = !1, o && b.abort()
        },
        "debug": function(a) {
            debugState = arguments.length > 0 ? !!a : !0
        },
        "setLanguage": function(a) {
            n(), b.lang = a
        },
        "addCommands": function(a) {
            var b, c;
            n();
            for (var e in a)
                if (a.hasOwnProperty(e)) {
                    if (b = root[a[e]] || a[e], "function" != typeof b) continue;
                    c = l(e), d.push({
                        "command": c,
                        "callback": b,
                        "originalPhrase": e
                    })
                }
            debugState && root.console.log("Commands successfully loaded: %c" + d.length, debugStyle)
        },
        "removeCommands": function(b) {
            return b === a ? void(d = []) : (b = Array.isArray(b) ? b : [b], void(d = d.filter(function(a) {
                for (var c = 0; c < b.length; c++)
                    if (b[c] === a.originalPhrase) return !1;
                return !0
            })))
        },
        "addCallback": function(b, c, d) {
            if (e[b] !== a) {
                var f = root[c] || c;
                "function" == typeof f && e[b].push({
                    "callback": f,
                    "context": d || this
                })
            }
        }
    }
}).call(this);
var autoStart = function() {
    smartSpeechRecognition.addCommands(commands), smartSpeechRecognition ? (smartSpeechRecognition.start(), $.root_.addClass("voice-command-active"), voice_localStorage && localStorage.setItem("sm-setautovoice", "true")) : alert("speech plugin not loaded")
};
SpeechRecognition && voice_command && "true" == localStorage.getItem("sm-setautovoice") && autoStart(), SpeechRecognition && voice_command_auto && voice_command && autoStart();