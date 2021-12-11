
!function () {
    var r = "arrow", t = mars3d.DrawEdit.PolygonEx.extend({
        getShowPositions: function (r) {
            var t = r[0], a = r[1], n = mars3d.draw.util.cartesian2lonlat(t), i = mars3d.draw.util.cartesian2lonlat(a);
            return plotUtil.fineArrow(n, i)
        }
    }), a = mars3d.Draw.PolygonEx.extend({
        type: r,
        _minPointNum: 2,
        _maxPointNum: 2,
        editClass: t,
        getShowPositions: function (r) {
            var t = r[0], a = r[1], n = mars3d.draw.util.cartesian2lonlat(t), i = mars3d.draw.util.cartesian2lonlat(a);
            return plotUtil.fineArrow(n, i)
        }
    });
    mars3d.draw.register(r, a)
}(window);