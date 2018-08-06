/*! SmartAdmin - v1.5 - 2014-09-27 */
if ($(".tree > ul") && !mytreebranch) {
    var mytreebranch = $(".tree").find("li:has(ul)").addClass("parent_li").attr("role", "treeitem").find(" > span").attr("title", "Collapse this branch");
    $(".tree > ul").attr("role", "tree").find("ul").attr("role", "group"), mytreebranch.on("click", function(a) {
        var b = $(this).parent("li.parent_li").find(" > ul > li");
        b.is(":visible") ? (b.hide("fast"), $(this).attr("title", "Expand this branch").find(" > i").addClass("icon-plus-sign").removeClass("icon-minus-sign")) : (b.show("fast"), $(this).attr("title", "Collapse this branch").find(" > i").addClass("icon-minus-sign").removeClass("icon-plus-sign")), a.stopPropagation()
    })
}