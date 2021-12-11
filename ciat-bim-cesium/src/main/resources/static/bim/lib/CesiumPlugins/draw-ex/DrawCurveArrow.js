/* 2019-9-2 09:29:12 | 版权所有 国信科技 http://marsgis.cn */
!function () {
    var t = "curveArrow", r = mars3d.DrawEdit.PolygonEx.extend({
        _hasMidPoint: !0, bindDraggers: function () {
            mars3d.DrawEdit.Polygon.prototype.bindDraggers.call(this), this.draggers[1].show = !1, this.draggers[this.draggers.length - 1].show = !1
        }, getShowPositions: function (t) {
            var r = mars3d.draw.util.cartesians2lonlats(t);
            return plotUtil.tailedAttackArrow(r).polygonalPoint
        }
    }), o = mars3d.Draw.PolygonEx.extend({
        type: t,
        _minPointNum: 3,
        _maxPointNum: 9999,
        editClass: r,
        getShowPositions: function (t) {
            var r = mars3d.draw.util.cartesians2lonlats(t);
            return plotUtil.tailedAttackArrow(r).polygonalPoint
        }
    });
    mars3d.draw.register(t, o)
}(window);