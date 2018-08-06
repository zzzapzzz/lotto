/*! SmartAdmin - v1.5 - 2014-09-27 */
var chatboxManager = function() {
    var a = function(a) {
            $.extend(chatbox_config, a)
        },
        b = function() {},
        c = function() {
            return (chatbox_config.width + chatbox_config.gap) * showList.length
        },
        d = function(a) {
            var b = showList.indexOf(a);
            if (-1 != b) {
                showList.splice(b, 1), diff = chatbox_config.width + chatbox_config.gap;
                for (var c = b; c < showList.length; c++) offset = $("#" + showList[c]).chatbox("option", "offset"), $("#" + showList[c]).chatbox("option", "offset", offset - diff)
            } else alert("NOTE: Id missing from array: " + a)
        },
        e = function(a, b) {
            var e = showList.indexOf(a),
                g = boxList.indexOf(a);
            if (-1 != e);
            else if (-1 != g) {
                $("#" + a).chatbox("option", "offset", c());
                var h = $("#" + a).chatbox("option", "boxManager");
                h.toggleBox(), showList.push(a)
            } else {
                var i = document.createElement("div");
                i.setAttribute("id", a), $(i).chatbox({
                    "id": a,
                    "user": b,
                    "title": '<i title="' + b.status + '"></i>' + b.first_name + " " + b.last_name,
                    "hidden": !1,
                    "offset": c(),
                    "width": chatbox_config.width,
                    "status": b.status,
                    "alertmsg": b.alertmsg,
                    "alertshow": b.alertshow,
                    "messageSent": f,
                    "boxClosed": d
                }), boxList.push(a), showList.push(a), nameList.push(b.first_name)
            }
        },
        f = function(a, b, c) {
            $("#chatlog").doesExist() && $("#chatlog").append("You said to <b>" + b.first_name + " " + b.last_name + ":</b> " + c + "<br/>").effect("highlight", {}, 500), $("#" + a).chatbox("option", "boxManager").addMsg("Me", c)
        };
    return {
        "init": a,
        "addBox": e,
        "delBox": b,
        "dispatch": f
    }
}();
$("a[data-chat-id]:not(.offline)").click(function(a) {
    var b = $(this),
        c = b.attr("data-chat-id"),
        d = b.attr("data-chat-fname"),
        e = b.attr("data-chat-lname"),
        f = b.attr("data-chat-status") || "online",
        g = b.attr("data-chat-alertmsg"),
        h = b.attr("data-chat-alertshow") || !1;
    chatboxManager.addBox(c, {
        "title": "username" + c,
        "first_name": d,
        "last_name": e,
        "status": f,
        "alertmsg": g,
        "alertshow": h
    }), a.preventDefault()
});