
!function (o) {
    var C = {};
    C.Constants = {
        TWO_PI: 2 * Math.PI,
        HALF_PI: Math.PI / 2,
        FITTING_COUNT: 100,
        ZERO_TOLERANCE: 1e-4
    }, C.PlotUtils = {}, C.PlotUtils.distance = function (t, i) {
        return Math.sqrt(Math.pow(t[0] - i[0], 2) + Math.pow(t[1] - i[1], 2))
    }, C.PlotUtils.wholeDistance = function (t) {
        for (var i = 0, o = 0; o < t.length - 1; o++) i += C.PlotUtils.distance(t[o], t[o + 1]);
        return i
    }, C.PlotUtils.getBaseLength = function (t) {
        return Math.pow(C.PlotUtils.wholeDistance(t), .99)
    }, C.PlotUtils.mid = function (t, i) {
        return [(t[0] + i[0]) / 2, (t[1] + i[1]) / 2]
    }, C.PlotUtils.getCircleCenterOfThreePoints = function (t, i, o) {
        var n = [(t[0] + i[0]) / 2, (t[1] + i[1]) / 2], s = [n[0] - t[1] + i[1], n[1] + t[0] - i[0]],
            l = [(t[0] + o[0]) / 2, (t[1] + o[1]) / 2], e = [l[0] - t[1] + o[1], l[1] + t[0] - o[0]];
        return C.PlotUtils.getIntersectPoint(n, s, l, e)
    }, C.PlotUtils.getIntersectPoint = function (t, i, o, n) {
        if (t[1] == i[1]) {
            var s = (n[0] - o[0]) / (n[1] - o[1]), l = s * (t[1] - o[1]) + o[0], e = t[1];
            return [l, e]
        }
        if (o[1] != n[1]) return r = (i[0] - t[0]) / (i[1] - t[1]), s = (n[0] - o[0]) / (n[1] - o[1]), [l = r * (e = (r * t[1] - t[0] - s * o[1] + o[0]) / (r - s)) - r * t[1] + t[0], e];
        var r = (i[0] - t[0]) / (i[1] - t[1]);
        return [l = r * (o[1] - t[1]) + t[0], e = o[1]]
    }, C.PlotUtils.getAzimuth = function (t, i) {
        var o, n = Math.asin(Math.abs(i[1] - t[1]) / C.PlotUtils.distance(t, i));
        return i[1] >= t[1] && i[0] >= t[0] ? o = n + Math.PI : i[1] >= t[1] && i[0] < t[0] ? o = C.Constants.TWO_PI - n : i[1] < t[1] && i[0] < t[0] ? o = n : i[1] < t[1] && i[0] >= t[0] && (o = Math.PI - n), o
    }, C.PlotUtils.getAngleOfThreePoints = function (t, i, o) {
        var n = C.PlotUtils.getAzimuth(i, t) - C.PlotUtils.getAzimuth(i, o);
        return n < 0 ? n + C.Constants.TWO_PI : n
    }, C.PlotUtils.isClockWise = function (t, i, o) {
        return (o[1] - t[1]) * (i[0] - t[0]) > (i[1] - t[1]) * (o[0] - t[0])
    }, C.PlotUtils.getPointOnLine = function (t, i, o) {
        return [i[0] + t * (o[0] - i[0]), i[1] + t * (o[1] - i[1])]
    }, C.PlotUtils.getCubicValue = function (t, i, o, n, s) {
        var l = 1 - (t = Math.max(Math.min(t, 1), 0)), e = t * t, r = e * t, a = l * l, h = a * l;
        return [h * i[0] + 3 * a * t * o[0] + 3 * l * e * n[0] + r * s[0], h * i[1] + 3 * a * t * o[1] + 3 * l * e * n[1] + r * s[1]]
    }, C.PlotUtils.getThirdPoint = function (t, i, o, n, s) {
        var l = C.PlotUtils.getAzimuth(t, i), e = s ? l + o : l - o, r = n * Math.cos(e), a = n * Math.sin(e);
        return [i[0] + r, i[1] + a]
    }, C.PlotUtils.getArcPoints = function (t, i, o, n) {
        var s, l, e = [], r = n - o;
        r = r < 0 ? r + C.Constants.TWO_PI : r;
        for (var a = 0; a <= C.Constants.FITTING_COUNT; a++) {
            var h = o + r * a / C.Constants.FITTING_COUNT;
            s = t[0] + i * Math.cos(h), l = t[1] + i * Math.sin(h), e.push([s, l])
        }
        return e
    }, C.PlotUtils.getBisectorNormals = function (t, i, o, n) {
        var s = C.PlotUtils.getNormal(i, o, n), l = Math.sqrt(s[0] * s[0] + s[1] * s[1]), e = s[0] / l, r = s[1] / l,
            a = C.PlotUtils.distance(i, o), h = C.PlotUtils.distance(o, n);
        if (l > C.Constants.ZERO_TOLERANCE) if (C.PlotUtils.isClockWise(i, o, n)) {
            var P = t * a, c = o[0] - P * r, g = o[1] + P * e, u = [c, g];
            P = t * h;
            var d = [c = o[0] + P * r, g = o[1] - P * e]
        } else P = t * a, u = [c = o[0] + P * r, g = o[1] - P * e], P = t * h, d = [c = o[0] - P * r, g = o[1] + P * e]; else u = [c = o[0] + t * (i[0] - o[0]), g = o[1] + t * (i[1] - o[1])], d = [c = o[0] + t * (n[0] - o[0]), g = o[1] + t * (n[1] - o[1])];
        return [u, d]
    }, C.PlotUtils.getNormal = function (t, i, o) {
        var n = t[0] - i[0], s = t[1] - i[1], l = Math.sqrt(n * n + s * s);
        n /= l, s /= l;
        var e = o[0] - i[0], r = o[1] - i[1], a = Math.sqrt(e * e + r * r);
        return [n + (e /= a), s + (r /= a)]
    }, C.PlotUtils.getCurvePoints = function (t, i) {
        for (var o = [C.PlotUtils.getLeftMostControlPoint(i)], n = 0; n < i.length - 2; n++) {
            var s = i[n], l = i[n + 1], e = i[n + 2], r = C.PlotUtils.getBisectorNormals(t, s, l, e);
            o = o.concat(r)
        }
        var a = C.PlotUtils.getRightMostControlPoint(i);
        o.push(a);
        var h = [];
        for (n = 0; n < i.length - 1; n++) {
            s = i[n], l = i[n + 1], h.push(s);
            for (t = 0; t < C.Constants.FITTING_COUNT; t++) {
                var P = C.PlotUtils.getCubicValue(t / C.Constants.FITTING_COUNT, s, o[2 * n], o[2 * n + 1], l);
                h.push(P)
            }
            h.push(l)
        }
        return h
    }, C.PlotUtils.getLeftMostControlPoint = function (i) {
        var o = i[0], n = i[1], s = i[2], l = C.PlotUtils.getBisectorNormals(0, o, n, s)[0],
            e = C.PlotUtils.getNormal(o, n, s);
        if (Math.sqrt(e[0] * e[0] + e[1] * e[1]) > C.Constants.ZERO_TOLERANCE) var r = C.PlotUtils.mid(o, n),
            a = o[0] - r[0], h = o[1] - r[1], P = 2 / C.PlotUtils.distance(o, n), c = -P * h, g = P * a,
            u = c * c - g * g, d = 2 * c * g, U = g * g - c * c, F = l[0] - r[0], f = l[1] - r[1],
            m = r[0] + u * F + d * f,
            p = r[1] + d * F + U * f; else m = o[0] + t * (n[0] - o[0]), p = o[1] + t * (n[1] - o[1]);
        return [m, p]
    }, C.PlotUtils.getRightMostControlPoint = function (i) {
        var o = i.length, n = i[o - 3], s = i[o - 2], l = i[o - 1], e = C.PlotUtils.getBisectorNormals(0, n, s, l)[1],
            r = C.PlotUtils.getNormal(n, s, l);
        if (Math.sqrt(r[0] * r[0] + r[1] * r[1]) > C.Constants.ZERO_TOLERANCE) var a = C.PlotUtils.mid(s, l),
            h = l[0] - a[0], P = l[1] - a[1], c = 2 / C.PlotUtils.distance(s, l), g = -c * P, u = c * h,
            d = g * g - u * u, U = 2 * g * u, F = u * u - g * g, f = e[0] - a[0], m = e[1] - a[1],
            p = a[0] + d * f + U * m,
            T = a[1] + U * f + F * m; else p = l[0] + t * (s[0] - l[0]), T = l[1] + t * (s[1] - l[1]);
        return [p, T]
    }, C.PlotUtils.getBezierPoints = function (t) {
        if (t.length <= 2) return t;
        for (var i = [], o = t.length - 1, n = 0; n <= 1; n += .01) {
            for (var s = y = 0, l = 0; l <= o; l++) {
                var e = C.PlotUtils.getBinomialFactor(o, l), r = Math.pow(n, l), a = Math.pow(1 - n, o - l);
                s += e * r * a * t[l][0], y += e * r * a * t[l][1]
            }
            i.push([s, y])
        }
        return i.push(t[o]), i
    }, C.PlotUtils.getBinomialFactor = function (t, i) {
        return C.PlotUtils.getFactorial(t) / (C.PlotUtils.getFactorial(i) * C.PlotUtils.getFactorial(t - i))
    }, C.PlotUtils.getFactorial = function (t) {
        if (t <= 1) return 1;
        if (2 == t) return 2;
        if (3 == t) return 6;
        if (4 == t) return 24;
        if (5 == t) return 120;
        for (var i = 1, o = 1; o <= t; o++) i *= o;
        return i
    }, C.PlotUtils.getQBSplinePoints = function (t) {
        if (t.length <= 2) return t;
        var i = [], o = t.length - 2 - 1;
        i.push(t[0]);
        for (var n = 0; n <= o; n++) for (var s = 0; s <= 1; s += .05) {
            for (var l = y = 0, e = 0; e <= 2; e++) {
                var r = C.PlotUtils.getQuadricBSplineFactor(e, s);
                l += r * t[n + e][0], y += r * t[n + e][1]
            }
            i.push([l, y])
        }
        return i.push(t[t.length - 1]), i
    }, C.PlotUtils.getQuadricBSplineFactor = function (t, i) {
        return 0 == t ? Math.pow(i - 1, 2) / 2 : 1 == t ? (-2 * Math.pow(i, 2) + 2 * i + 1) / 2 : 2 == t ? Math.pow(i, 2) / 2 : 0
    };
    var f = "doublearrow", m = .25, T = .3, A = .85, v = .15, I = {
        headHeightFactor: .18,
        headWidthFactor: .3,
        neckHeightFactor: .85,
        neckWidthFactor: .15,
        tailWidthFactor: .1,
        headTailFactor: .8,
        swallowTailFactor: 1
    }, M = {
        tailWidthFactor: .15,
        neckWidthFactor: .2,
        headWidthFactor: .25,
        headAngle: Math.PI / 8.5,
        neckAngle: Math.PI / 13
    }, w = {algorithm: {}};
    w.algorithm.doubleArrow = function (t) {
        this.connPoint = null;
        var i = {controlPoint: this.tempPoint4 = null, polygonalPoint: null};
        if (!((r = (this.points = t).length) < 2)) {
            if (2 == r) return t;
            var o, n, s = this.points[0], l = this.points[1], e = this.points[2], r = t.length;
            this.tempPoint4 = 3 == r ? w.algorithm.getTempPoint4(s, l, e) : this.points[3], this.connPoint = 3 == r || 4 == r ? C.PlotUtils.mid(s, l) : this.points[4], n = C.PlotUtils.isClockWise(s, l, e) ? (o = w.algorithm.getArrowPoints(s, this.connPoint, this.tempPoint4, !1), w.algorithm.getArrowPoints(this.connPoint, l, e, !0)) : (o = w.algorithm.getArrowPoints(l, this.connPoint, e, !1), w.algorithm.getArrowPoints(this.connPoint, s, this.tempPoint4, !0));
            var a = o.length, h = (a - 5) / 2, P = o.slice(0, h), c = o.slice(h, 5 + h), g = o.slice(5 + h, a),
                u = n.slice(0, h), d = n.slice(h, 5 + h), U = n.slice(5 + h, a);
            u = C.PlotUtils.getBezierPoints(u);
            var F = C.PlotUtils.getBezierPoints(U.concat(P.slice(1)));
            g = C.PlotUtils.getBezierPoints(g);
            var f = u.concat(d, F, c, g), m = w.algorithm.array2Dto1D(f);
            i.controlPoint = [s, l, e, this.tempPoint4, this.connPoint], i.polygonalPoint = Cesium.Cartesian3.fromDegreesArray(m)
        }
        return i
    }, w.algorithm.getTempPoint4 = function (t, i, o) {
        var n, s, l, e = C.PlotUtils.mid(t, i), r = C.PlotUtils.distance(e, o),
            a = C.PlotUtils.getAngleOfThreePoints(t, e, o);
        return a < C.Constants.HALF_PI ? (n = r * Math.sin(a), s = r * Math.cos(a), l = C.PlotUtils.getThirdPoint(t, e, C.Constants.HALF_PI, n, !1), C.PlotUtils.getThirdPoint(e, l, C.Constants.HALF_PI, s, !0)) : a >= C.Constants.HALF_PI && a < Math.PI ? (n = r * Math.sin(Math.PI - a), s = r * Math.cos(Math.PI - a), l = C.PlotUtils.getThirdPoint(t, e, C.Constants.HALF_PI, n, !1), C.PlotUtils.getThirdPoint(e, l, C.Constants.HALF_PI, s, !1)) : a >= Math.PI && a < 1.5 * Math.PI ? (n = r * Math.sin(a - Math.PI), s = r * Math.cos(a - Math.PI), l = C.PlotUtils.getThirdPoint(t, e, C.Constants.HALF_PI, n, !0), C.PlotUtils.getThirdPoint(e, l, C.Constants.HALF_PI, s, !0)) : (n = r * Math.sin(2 * Math.PI - a), s = r * Math.cos(2 * Math.PI - a), l = C.PlotUtils.getThirdPoint(t, e, C.Constants.HALF_PI, n, !0), C.PlotUtils.getThirdPoint(e, l, C.Constants.HALF_PI, s, !1))
    }, w.algorithm.threeArrow = function (t) {
        this.connPoint = null, this.tempPoint4 = null;
        var i = {controlPoint: this.tempPoint5 = null, polygonalPoint: null};
        if (2 <= (o = (this.points = t).length)) {
            if (2 == o) return t;
            var o, n, s, l = this.points[0], e = this.points[1], r = this.points[2];
            3 == (o = t.length) ? (this.tempPoint4 = w.algorithm.getTempPoint4(l, e, r), this.tempPoint5 = C.PlotUtils.mid(r, this.tempPoint4)) : (this.tempPoint4 = this.points[3], this.tempPoint5 = this.points[4]), this.connPoint = o < 6 ? C.PlotUtils.mid(l, e) : this.points[5], s = C.PlotUtils.isClockWise(l, e, r) ? (n = w.algorithm.getArrowPoints(l, this.connPoint, this.tempPoint4, !1), w.algorithm.getArrowPoints(this.connPoint, e, r, !0)) : (n = w.algorithm.getArrowPoints(e, this.connPoint, r, !1), w.algorithm.getArrowPoints(this.connPoint, l, this.tempPoint4, !0));
            var a = n.length, h = (a - 5) / 2, P = n.slice(0, h), c = n.slice(h, 5 + h), g = n.slice(5 + h, a),
                u = s.slice(0, h), d = s.slice(h, 5 + h), U = s.slice(5 + h, a);
            u = C.PlotUtils.getBezierPoints(u);
            var F = C.PlotUtils.getBezierPoints(U.concat(P.slice(1)));
            g = C.PlotUtils.getBezierPoints(g);
            var f = u.concat(d, F, c, g), m = w.algorithm.array2Dto1D(f);
            i.controlPoint = [l, e, r, this.tempPoint4, this.tempPoint5, this.connPoint], i.polygonalPoint = Cesium.Cartesian3.fromDegreesArray(m)
        }
        return i
    }, w.algorithm.array2Dto1D = function (t) {
        var i = [];
        return t.forEach(function (t) {
            i.push(t[0]), i.push(t[1])
        }), i
    }, w.algorithm.getArrowPoints = function (t, i, o, n) {
        this.type = f, this.headHeightFactor = m, this.headWidthFactor = T, this.neckHeightFactor = A, this.neckWidthFactor = v;
        var s = C.PlotUtils.mid(t, i), l = C.PlotUtils.distance(s, o),
            e = C.PlotUtils.getThirdPoint(o, s, 0, .3 * l, !0), r = C.PlotUtils.getThirdPoint(o, s, 0, .5 * l, !0),
            a = [s, e = C.PlotUtils.getThirdPoint(s, e, C.Constants.HALF_PI, l / 5, n), r = C.PlotUtils.getThirdPoint(s, r, C.Constants.HALF_PI, l / 4, n), o],
            h = w.algorithm.getArrowHeadPoints(a, this.headHeightFactor, this.headWidthFactor, this.neckHeightFactor, this.neckWidthFactor),
            P = h[0], c = h[4], g = C.PlotUtils.distance(t, i) / C.PlotUtils.getBaseLength(a) / 2,
            u = w.algorithm.getArrowBodyPoints(a, P, c, g), d = u.length, U = u.slice(0, d / 2), F = u.slice(d / 2, d);
        return U.push(P), F.push(c), (U = U.reverse()).push(i), (F = F.reverse()).push(t), U.reverse().concat(h, F)
    }, w.algorithm.getArrowHeadPoints = function (t, i, o) {
        this.type = f, this.headHeightFactor = m, this.headWidthFactor = T, this.neckHeightFactor = A, this.neckWidthFactor = v;
        var n = C.PlotUtils.getBaseLength(t) * this.headHeightFactor, s = t[t.length - 1],
            l = (C.PlotUtils.distance(i, o), n * this.headWidthFactor), e = n * this.neckWidthFactor,
            r = n * this.neckHeightFactor, a = C.PlotUtils.getThirdPoint(t[t.length - 2], s, 0, n, !0),
            h = C.PlotUtils.getThirdPoint(t[t.length - 2], s, 0, r, !0),
            P = C.PlotUtils.getThirdPoint(s, a, C.Constants.HALF_PI, l, !1),
            c = C.PlotUtils.getThirdPoint(s, a, C.Constants.HALF_PI, l, !0);
        return [C.PlotUtils.getThirdPoint(s, h, C.Constants.HALF_PI, e, !1), P, s, c, C.PlotUtils.getThirdPoint(s, h, C.Constants.HALF_PI, e, !0)]
    }, w.algorithm.getArrowBodyPoints = function (t, i, o, n) {
        for (var s = C.PlotUtils.wholeDistance(t), l = C.PlotUtils.getBaseLength(t) * n, e = (l - C.PlotUtils.distance(i, o)) / 2, r = 0, a = [], h = [], P = 1; P < t.length - 1; P++) {
            var c = C.PlotUtils.getAngleOfThreePoints(t[P - 1], t[P], t[P + 1]) / 2,
                g = (l / 2 - (r += C.PlotUtils.distance(t[P - 1], t[P])) / s * e) / Math.sin(c),
                u = C.PlotUtils.getThirdPoint(t[P - 1], t[P], Math.PI - c, g, !0),
                d = C.PlotUtils.getThirdPoint(t[P - 1], t[P], c, g, !1);
            a.push(u), h.push(d)
        }
        return a.concat(h)
    }, w.algorithm.tailedAttackArrow = function (t) {
        t = w.algorithm.dereplication(t), this.tailWidthFactor = I.tailWidthFactor, this.swallowTailFactor = I.swallowTailFactor, this.swallowTailPnt = I.swallowTailPnt;
        var i = {controlPoint: null, polygonalPoint: null};
        if (!((d = (i.controlPoint = t).length) < 2)) {
            if (2 == t.length) return i.polygonalPoint = t, i;
            var o = t, n = o[0], s = o[1];
            C.PlotUtils.isClockWise(o[0], o[1], o[2]) && (n = o[1], s = o[0]);
            var l = [C.PlotUtils.mid(n, s)].concat(o.slice(2)), e = w.algorithm.getAttackArrowHeadPoints(l, n, s, I),
                r = e[0], a = e[4], h = C.PlotUtils.distance(n, s), P = C.PlotUtils.getBaseLength(l),
                c = P * this.tailWidthFactor * this.swallowTailFactor;
            this.swallowTailPnt = C.PlotUtils.getThirdPoint(l[1], l[0], 0, c, !0);
            var g = h / P, u = w.algorithm.getAttackArrowBodyPoints(l, r, a, g), d = u.length,
                U = [n].concat(u.slice(0, d / 2));
            U.push(r);
            var F, f = [s].concat(u.slice(d / 2, d));
            f.push(a), U = C.PlotUtils.getQBSplinePoints(U), f = C.PlotUtils.getQBSplinePoints(f), F = w.algorithm.array2Dto1D(U.concat(e, f.reverse(), [this.swallowTailPnt, U[0]])), i.polygonalPoint = Cesium.Cartesian3.fromDegreesArray(F)
        }
        return i
    }, w.algorithm.getAttackArrowHeadPoints = function (t, i, o, n) {
        this.headHeightFactor = n.headHeightFactor, this.headTailFactor = n.headTailFactor, this.headWidthFactor = n.headWidthFactor, this.neckWidthFactor = n.neckWidthFactor, this.neckHeightFactor = n.neckHeightFactor;
        var s = C.PlotUtils.getBaseLength(t), l = s * this.headHeightFactor, e = t[t.length - 1];
        s = C.PlotUtils.distance(e, t[t.length - 2]);
        var r = C.PlotUtils.distance(i, o);
        l > r * this.headTailFactor && (l = r * this.headTailFactor);
        var a = l * this.headWidthFactor, h = l * this.neckWidthFactor, P = (l = s < l ? s : l) * this.neckHeightFactor,
            c = C.PlotUtils.getThirdPoint(t[t.length - 2], e, 0, l, !0),
            g = C.PlotUtils.getThirdPoint(t[t.length - 2], e, 0, P, !0),
            u = C.PlotUtils.getThirdPoint(e, c, C.Constants.HALF_PI, a, !1),
            d = C.PlotUtils.getThirdPoint(e, c, C.Constants.HALF_PI, a, !0);
        return [C.PlotUtils.getThirdPoint(e, g, C.Constants.HALF_PI, h, !1), u, e, d, C.PlotUtils.getThirdPoint(e, g, C.Constants.HALF_PI, h, !0)]
    }, w.algorithm.getAttackArrowBodyPoints = function (t, i, o, n) {
        for (var s = C.PlotUtils.wholeDistance(t), l = C.PlotUtils.getBaseLength(t) * n, e = (l - C.PlotUtils.distance(i, o)) / 2, r = 0, a = [], h = [], P = 1; P < t.length - 1; P++) {
            var c = C.PlotUtils.getAngleOfThreePoints(t[P - 1], t[P], t[P + 1]) / 2,
                g = (l / 2 - (r += C.PlotUtils.distance(t[P - 1], t[P])) / s * e) / Math.sin(c),
                u = C.PlotUtils.getThirdPoint(t[P - 1], t[P], Math.PI - c, g, !0),
                d = C.PlotUtils.getThirdPoint(t[P - 1], t[P], c, g, !1);
            a.push(u), h.push(d)
        }
        return a.concat(h)
    }, w.algorithm.dereplication = function (t) {
        var i = t[t.length - 1], o = !1, n = [];
        return n = t.filter(function (t) {
            if (t[0] != i[0] && t[1] != i[1]) return t;
            o = !0
        }), o && n.push(i), n
    }, w.algorithm.fineArrow = function (t, o) {
        if (!(t.length < 2 || o.length < 2)) {
            var P = 3 == t.length ? t[2] : 0, U = 3 == o.length ? o[2] : 0, F = M.tailWidthFactor,
                f = M.neckWidthFactor, m = M.headWidthFactor, T = M.headAngle, A = M.neckAngle, v = [];
            return v[0] = t, v[1] = o, e = v[0], r = v[1], n = C.PlotUtils.getBaseLength(v), g = n * F, i = n * f, s = n * m, a = C.PlotUtils.getThirdPoint(r, e, C.Constants.HALF_PI, g, !0), l = C.PlotUtils.getThirdPoint(r, e, C.Constants.HALF_PI, g, !1), u = C.PlotUtils.getThirdPoint(e, r, T, s, !1), c = C.PlotUtils.getThirdPoint(e, r, T, s, !0), p = C.PlotUtils.getThirdPoint(e, r, A, i, !1), h = C.PlotUtils.getThirdPoint(e, r, A, i, !0), d = [], d.push(a[0], a[1], P, p[0], p[1], U, u[0], u[1], U, r[0], r[1], U, c[0], c[1], U, h[0], h[1], U, l[0], l[1], P, e[0], e[1], P), Cesium.Cartesian3.fromDegreesArrayHeights(d)
        }
    }, o.plotUtil = w.algorithm
}(window);