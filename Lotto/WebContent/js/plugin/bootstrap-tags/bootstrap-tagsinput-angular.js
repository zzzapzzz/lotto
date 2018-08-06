/*! SmartAdmin - v1.5 - 2014-09-27 */
angular.module("bootstrap-tagsinput", []).directive("bootstrapTagsinput", [function() {
    function a(a, b) {
        return b ? angular.isFunction(a.$parent[b]) ? a.$parent[b] : function(a) {
            return a[b]
        } : void 0
    }
    return {
        "restrict": "EA",
        "scope": {
            "model": "=ngModel"
        },
        "template": "<select multiple></select>",
        "replace": !1,
        "link": function(b, c, d) {
            $(function() {
                angular.isArray(b.model) || (b.model = []);
                var e = $("select", c);
                e.tagsinput({
                    "typeahead": {
                        "source": angular.isFunction(b.$parent[d.typeaheadSource]) ? b.$parent[d.typeaheadSource] : null
                    },
                    "itemValue": a(b, d.itemvalue),
                    "itemText": a(b, d.itemtext),
                    "tagClass": angular.isFunction(b.$parent[d.tagclass]) ? b.$parent[d.tagclass] : function() {
                        return d.tagclass
                    }
                });
                for (var f = 0; f < b.model.length; f++) e.tagsinput("add", b.model[f]);
                e.on("itemAdded", function(a) {
                    -1 === b.model.indexOf(a.item) && b.model.push(a.item)
                }), e.on("itemRemoved", function(a) {
                    var c = b.model.indexOf(a.item); - 1 !== c && b.model.splice(c, 1)
                });
                var g = b.model.slice();
                b.$watch("model", function() {
                    var a, c = b.model.filter(function(a) {
                            return -1 === g.indexOf(a)
                        }),
                        d = g.filter(function(a) {
                            return -1 === b.model.indexOf(a)
                        });
                    for (g = b.model.slice(), a = 0; a < d.length; a++) e.tagsinput("remove", d[a]);
                    for (e.tagsinput("refresh"), a = 0; a < c.length; a++) e.tagsinput("add", c[a])
                }, !0)
            })
        }
    }
}]);