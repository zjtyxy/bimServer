!function (iGmt0011, irCd0102) {
    'object' == typeof exports && 'object' == typeof module ? module.exports = irCd0102(require('cesium/Cesium'), require('@turf/turf')) : 'function' == typeof define && define.amd ? define(['cesium/Cesium', '@turf/turf']
        , irCd0102) : 'object' == typeof exports ? exports.ciat3d = irCd0102(require('cesium/Cesium'), require('@turf/turf')) : iGmt0011.ciat3d = irCd0102(iGmt0011.Cesium, iGmt0011.turf);
}(window, function (_0x3d7e17, _0x178393) {
    return function (giJn0001) {
        var j33s0001 = {};

        function hdvZ0010(index) {
            if (j33s0001[index]) return j33s0001[index].exports;
            var jxga0023 = j33s0001[index] = {'i': index, 'l': !1, 'exports': {}};
            return giJn0001[index].call(jxga0023.exports, jxga0023, jxga0023.exports, hdvZ0010), jxga0023['l'] = !0, jxga0023.exports;
        }

        return hdvZ0010(hdvZ0010['s'] = 2);
        //hdvZ0010['m'] = giJn0001
        //     , hdvZ0010['c'] = j33s0001
        //     , hdvZ0010['d'] = function (fMfe0022, idb80330, fWUg0002) {
        //     hdvZ0010['o'](fMfe0022, idb80330) || Object.defineProperty(fMfe0022, idb80330, {
        //         'enumerable': !0x0,
        //         'get': fWUg0002
        //     });
        // }
        //     , hdvZ0010['r'] = function (jpn80000) {
        //     'undefined' != typeof Symbol && Symbol.toStringTag && Object.defineProperty(jpn80000, Symbol['toStringTag'], {'value': 'Module'}), Object.defineProperty(jpn80000, '__esModule', {'value': !0x0});
        // }
        //     , hdvZ0010['t'] = function (hmzp0100, eYaa0024) {
        //     if (0x1 & eYaa0024 && (hmzp0100 = hdvZ0010(hmzp0100)), 0x8 & eYaa0024) return hmzp0100;
        //     if (0x4 & eYaa0024 && 'object' == typeof hmzp0100 && hmzp0100 && hmzp0100.__esModule) return hmzp0100;
        //     var egdc0113 = Object.create(null);
        //     if (hdvZ0010['r'](egdc0113), Object.defineProperty(egdc0113, 'default', {
        //         'enumerable': !0x0,
        //         'value': hmzp0100
        //     }), 0x2 & eYaa0024 && 'string' != typeof hmzp0100) for (var nxdU0110 in hmzp0100) hdvZ0010['d'](egdc0113, nxdU0110, function (fdfs0310) {
        //         return hmzp0100[fdfs0310];
        //     }.bind(null, nxdU0110));
        //     return egdc0113;
        // }
        //     , hdvZ0010['n'] = function (f4cu0001) {
        //     var ggTx0100 = f4cu0001 && f4cu0001['__esModule'] ? function () {
        //         return f4cu0001['default'];
        //     } : function () {
        //         return f4cu0001;
        //     };
        //     return hdvZ0010['d'](ggTx0100, 'a', ggTx0100), ggTx0100;
        // }
        //     , hdvZ0010['o'] = function (ex7Q0100, fxhM0110) {
        //     return Object.prototype.hasOwnProperty.call(ex7Q0100, fxhM0110);
        // }
        //     , hdvZ0010['p'] = '',

    }([
        function (edzF0210, hyNp0001) {
            //cesium
            edzF0210['exports'] = _0x3d7e17;
        }
        , function (fCfq0010, fbtj0300, hceQ0020) {
            'use strict';
            Object.defineProperty(fbtj0300, '__esModule', {'value': !0x0});
            fbtj0300.version = '2.2.1', fbtj0300.update = '2020-11-27 11:46:26';
        }
        , function (h7jc0011, gIJw0000, hgLx0100) {
            'use strict';
            // hgLx0100(0x5c), hgLx0100(0x5d);
            var hNfY0030 = ixy60100(hgLx0100(0));
            var eRsj0000 = ixy60100(hgLx0100(1));

            // hgLx0100(0x5e), hgLx0100(0x5f);
            // var hD3c0002 = hgLx0100(0x60)
            // , quKe0000 = hgLx0100(0x49), hdwu0010 = hgLx0100(0x3),
            //     hCnX0000 = ixy60100(hgLx0100(0x2f)), _0x44a78 = hgLx0100(0x8a), ilsh0101 = hgLx0100(0x8c),
            //     _0x4da79 = hgLx0100(0x8d), isI40000 = ixy60100(hgLx0100(0x18)), eEMc0003 = hgLx0100(0x8f),
            //     fzUd0002 = hgLx0100(0x90), gb0f0001 = hgLx0100(0x91), enV80000 = hgLx0100(0x4d), fb6r0200 = hgLx0100(0x24),
            //     iE3p0000 = hgLx0100(0x4e), p8ya0000 = hgLx0100(0x31), i4fR0000 = hgLx0100(0x4f), hrU60100 = hgLx0100(0x25),
            //     gKcJ0010 = hgLx0100(0x50), hzq40110 = hgLx0100(0x51), edPe0300 = hgLx0100(0x52), gokb0013 = hgLx0100(0x53),
            //     fveM0030 = hgLx0100(0x92), gzVa0103 = hgLx0100(0x94), irUp0000 = hgLx0100(0x95), ihxU0100 = hgLx0100(0x96),
            //     fu1p0000 = hgLx0100(0x97), i5FR0000 = hgLx0100(0x99), fmrx0011 = hgLx0100(0x9a), jzhg0011 = hgLx0100(0x3b),
            //     fedd0130 = hgLx0100(0x9b), eAfh0010 = hgLx0100(0x57), frv60110 = hgLx0100(0x9d), ivYa0104 = hgLx0100(0xa0),
            //     ilnn0101 = hgLx0100(0xa1), icb50220 = hgLx0100(0xa2), iagb0203 = hgLx0100(0xa3), hzkY0000 = hgLx0100(0xa4),
            //     _0x457ce = hgLx0100(0x4a), iwTw0000 = hgLx0100(0xa5), fjHe0002 = hgLx0100(0xa6), hSsi0010 = hgLx0100(0x6),
            //     eC2u0000 = ixy60100(hgLx0100(0x16)), shIC0000 = hgLx0100(0x8), ebke0201 = ixy60100(hgLx0100(0xf)),
            //     maec0202 = hgLx0100(0x1a), iOZb0003 = hgLx0100(0x42), g5Qf0002 = hgLx0100(0x3e), hzVe0102 = hgLx0100(0x3c),
            //     hi5m0001 = hgLx0100(0x45), hfgC0220 = hgLx0100(0x3a), isgv0001 = hgLx0100(0x1c), fsDn0000 = hgLx0100(0xb),
            //     hQev0000 = hgLx0100(0x1b), iq7z0101 = hgLx0100(0x3d), fEmi0010 = hgLx0100(0x41), fBIb0002 = hgLx0100(0x46),
            //     fzbm0121 = hgLx0100(0x48), i7qe0002 = hgLx0100(0x47), hznk0000 = hgLx0100(0x9);
            // hgLx0100(0xa7), hgLx0100(0xa9), hgLx0100(0xab), hgLx0100(0xad), hgLx0100(0xaf), hgLx0100(0xb1), hgLx0100(0xb3),
            //     hgLx0100(0xb5), hgLx0100(0xb7), hgLx0100(0xb9), hgLx0100(0xbb), hgLx0100(0xbc), hgLx0100(0xbd), hgLx0100(0xbe)
            //     , hgLx0100(0xbf), hgLx0100(0xc0);
            // var ew5f0003 = hgLx0100(0xc1), fkef0021 = hgLx0100(0x35), ifed0210 = hgLx0100(0x27), hbOp0001 = hgLx0100(0xc3),
            //     fdMA0300 = hgLx0100(0xc4), fcOy0100 = hgLx0100(0xc5), hyki0001 = hgLx0100(0x5a), g7Te0001 = hgLx0100(0x56),
            //     h8si0000 = hgLx0100(0xca), ghDd0001 = hgLx0100(0x59), pciK0000 = hgLx0100(0xcb),
            //     gdme0011 = ixy60100(hgLx0100(0x39)), ewtc0103 = hgLx0100(0xcf), eyVx0101 = hgLx0100(0xd0),
            //     edn90310 = hgLx0100(0xd3), hzg80020 = hgLx0100(0xd5), flha0002 = hgLx0100(0xda), e8sd0000 = hgLx0100(0xdd),
            //     f1eO0020 = hgLx0100(0xe2), eWwP0010 = hgLx0100(0xe4), hEg40010 = hgLx0100(0xe6),
            //     fqld0011 = inau0030(hgLx0100(0xe8)), hGga0013 = inau0030(hgLx0100(0xe9)), iauV0210 = hgLx0100(0xea),
            //     iihm0001 = ixy60100(hgLx0100(0xe)), ggeo0111 = ixy60100(hgLx0100(0xeb)), habC0200 = ixy60100(hgLx0100(0x2)),
            //     hda10130 = ixy60100(hgLx0100(0xd)), n1ru0011 = ixy60100(hgLx0100(0x17)), hjxT0000 = ixy60100(hgLx0100(0x4)),
            //     hxVc0101 = ixy60100(hgLx0100(0x1)), hpPQ0100 = hgLx0100(0x1e), pbdy0020 = hgLx0100(0x4b),
            //     gmeb0113 = ixy60100(hgLx0100(0x11)), jpif0013 = ixy60100(hgLx0100(0x1f)),
            //     ifsN0300 = ixy60100(hgLx0100(0x5)), fhan0100 = hgLx0100(0xec), fLLU0000 = hgLx0100(0xee);

            function inau0030(ihK80100) {
                return ihK80100 && ihK80100['__esModule'] ? ihK80100 : {'default': ihK80100};
            }

            function ixy60100(izom0010) {
                if (izom0010 && izom0010.__esModule) return izom0010;
                var kBze0000 = {};
                if (null != izom0010) for (var gev10110 in izom0010) Object.prototype.hasOwnProperty.call(izom0010, gev10110) && (kBze0000[gev10110] = izom0010[gev10110]);
                return kBze0000.default = izom0010, kBze0000;
            }

            gIJw0000.Cesium = hNfY0030
                , gIJw0000.name = '三维地球框架'
                , gIJw0000.website = 'http://cesium.ciat.cn'
                , gIJw0000.author = '泥团'
                , gIJw0000.version = eRsj0000.version
                , gIJw0000.FlyLine = hgLx0100(5).FlyLine
                , gIJw0000.update = eRsj0000.update;
            //         , gIJw0000.Cesium3DTilesetEx = hD3c0002.Cesium3DTilesetEx
            //         , gIJw0000.ViewerEx = quKe0000.ViewerEx
            //         , gIJw0000.MarsClass = hdwu0010.MarsClass
            //         , gIJw0000.event = hdwu0010.eventType
            //         , gIJw0000.widget = hCnX0000
            //         , gIJw0000.widget.ES5BaseWidget = _0x44a78.ES5BaseWidget
            //         , gIJw0000.widget.BaseWidget = ilsh0101.BaseWidget
            //         , gIJw0000.widget.BaseWidget.extend = function (iLjp0000) {
            //     return _0x44a78.ES5BaseWidget.extend(iLjp0000);
            // }
            //         , gIJw0000.createMap = _0x4da79['createMap']
            //         , gIJw0000.layer = isI40000
            //         , gIJw0000['analysi'] = {}
            //         , gIJw0000['analysi'].FloodByEntity = eEMc0003['FloodByEntity']
            //         , gIJw0000.analysi.FloodByTerrain = fzUd0002.FloodByTerrain
            //         , gIJw0000.analysi.Measure = gb0f0001.Measure
            //         , gIJw0000.analysi.MeasureAngle = enV80000.MeasureAngle
            //         , gIJw0000.analysi.MeasureArea = fb6r0200.MeasureArea
            //         , gIJw0000['analysi'].MeasureAreaSurface = iE3p0000.MeasureAreaSurface
            //         , gIJw0000['analysi'].MeasureHeight = p8ya0000['MeasureHeight']
            //         , gIJw0000['analysi'].MeasureHeightTriangle = i4fR0000.MeasureHeightTriangle
            //         , gIJw0000.analysi.MeasureLength = hrU60100['MeasureLength']
            //         , gIJw0000.analysi.MeasureLengthSection = gKcJ0010.MeasureLengthSection
            //         , gIJw0000.analysi.MeasureLengthSurface = hzq40110.MeasureLengthSurface
            //         , gIJw0000.analysi['MeasurePoint'] = edPe0300.MeasurePoint
            //         , gIJw0000.analysi.MeasureVolume = gokb0013['MeasureVolume']
            //         , gIJw0000.analysi['Skyline'] = fveM0030['Skyline']
            //         , gIJw0000.analysi.TerrainClip = gzVa0103.TerrainClip
            //         , gIJw0000.analysi.TerrainClipPlan = irUp0000['TerrainClipPlan']
            //         , gIJw0000.analysi.Underground = ihxU0100.Underground
            //         , gIJw0000.analysi['ViewShed3D'] = fu1p0000.ViewShed3D
            //         , gIJw0000.analysi.Sightline = i5FR0000.Sightline
            //         , gIJw0000.analysi.ContourLine = fmrx0011.ContourLine
            //         , gIJw0000.analysi.Slope = jzhg0011.Slope
            //         , gIJw0000['tiles'] = {}
            //         , gIJw0000.tiles.MixedOcclusion = fedd0130.MixedOcclusion
            //         , gIJw0000['tiles'].TilesClipPlan = eAfh0010.TilesClipPlan
            //         , gIJw0000.tiles['TilesClip'] = frv60110.TilesClip
            //         , gIJw0000.tiles.TilesFlat = ivYa0104['TilesFlat']
            //         , gIJw0000.tiles.TilesFlood = ilnn0101.TilesFlood
            //         , gIJw0000.GltfClipPlan = icb50220.GltfClipPlan

            //         , gIJw0000.DynamicFlyLine = hzkY0000['DynamicFlyLine']
            //         , gIJw0000.KeyboardType = _0x457ce.KeyboardType
            //         , gIJw0000.FirstPersonRoam = iwTw0000['FirstPersonRoam']
            //         , gIJw0000.StreetCameraController = fjHe0002.StreetCameraController
            //         , gIJw0000.Draw = hSsi0010.Draw
            //         , gIJw0000.draw = {}
            //         , gIJw0000.draw.register = hSsi0010['register']
            //         , gIJw0000.draw.attr = eC2u0000
            //         , gIJw0000.draw.tooltip = shIC0000.message, gIJw0000['draw'].dragger = ebke0201, gIJw0000.DrawEdit = {}
            //         , gIJw0000.DrawEdit.Base = maec0202.EditBase, gIJw0000['DrawEdit'].Circle = iOZb0003['EditCircle']
            //         , gIJw0000.DrawEdit['Corridor'] = g5Qf0002.EditCorridor, gIJw0000['DrawEdit'].Curve = hzVe0102.EditCurve
            //         , gIJw0000.DrawEdit.Ellipsoid = hi5m0001.EditEllipsoid, gIJw0000.DrawEdit.Point = hfgC0220.EditPoint
            //         , gIJw0000.DrawEdit.Polygon = isgv0001.EditPolygon, gIJw0000['DrawEdit']['PolygonEx'] = fsDn0000['EditPolygonEx']
            //         , gIJw0000['DrawEdit'].Polyline = hQev0000.EditPolyline, gIJw0000.DrawEdit.PolylineVolume = iq7z0101.EditPolylineVolume
            //         , gIJw0000['DrawEdit'].Rectangle = fEmi0010.EditRectangle, gIJw0000.DrawEdit.Wall = fBIb0002.EditWall
            //         , gIJw0000.DrawEdit.Box = fzbm0121.EditBox, gIJw0000.DrawEdit.Plane = i7qe0002['EditPlane']
            //         , gIJw0000.draw['plotUtil'] = hznk0000.plotUtil, gIJw0000.material = {}
            //         , gIJw0000.material.CircleScanMaterial = ew5f0003.CircleScanMaterial, gIJw0000['material'].CircleScanMaterialProperty = ew5f0003.CircleScanMaterialProperty
            //         , gIJw0000.material.CircleWaveMaterial = fkef0021['CircleWaveMaterial'], gIJw0000['material'].CircleWaveMaterialProperty = fkef0021.CircleWaveMaterialProperty
            //         , gIJw0000.material.LineFlowMaterial = ifed0210.LineFlowMaterial, gIJw0000.material.LineFlowMaterialProperty = ifed0210['LineFlowMaterialProperty']
            //         , gIJw0000['material'].TextMaterialProperty = hbOp0001.TextMaterialProperty, gIJw0000.material.TextMaterial = fdMA0300['TextMaterial']
            //         , gIJw0000.material.CylinderWaveMaterial = fcOy0100.CylinderWaveMaterial, gIJw0000['RectangularSensorPrimitive'] = hyki0001['RectangularSensorPrimitive']
            //         , gIJw0000.RectangularSensorGraphics = g7Te0001.RectangularSensorGraphics, gIJw0000.RectangularSensorVisualizer = h8si0000.RectangularSensorVisualizer
            //         , gIJw0000.DivPoint = ghDd0001.DivPoint, gIJw0000.DynamicRiver = pciK0000.DynamicRiver
            //         , gIJw0000.water = gdme0011, gIJw0000.ParticleSystemEx = ewtc0103.ParticleSystemEx
            //         , gIJw0000.FlatBillboard = eyVx0101.FlatBillboard, gIJw0000.FlatImage = edn90310.FlatImage
            //         , gIJw0000.ConeGlow = hzg80020['ConeGlow'], gIJw0000['DiffuseWallGlow'] = flha0002.DiffuseWallGlow
            //         , gIJw0000.ScrollWallGlow = e8sd0000.ScrollWallGlow, gIJw0000.scene = {}
            //         , gIJw0000.scene['FogEffect'] = f1eO0020.FogEffect, gIJw0000.scene.InvertedScene = eWwP0010.InvertedScene
            //         , gIJw0000.scene.SnowCover = hEg40010.SnowCover, gIJw0000.shader = {}, gIJw0000.shader['rain'] = fqld0011['default']
            //         , gIJw0000['shader'].snow = hGga0013.default
            //     , gIJw0000['ZoomNavigation'] = iauV0210['ZoomNavigation']
            //     , gIJw0000.matrix = iihm0001
            //     , gIJw0000.model = ggeo0111, gIJw0000.point = habC0200, gIJw0000.polygon = hda10130, gIJw0000['polyline'] = n1ru0011, gIJw0000.pointconvert = hjxT0000
            //     , gIJw0000.util = hxVc0101, gIJw0000.util.config2Entity = hpPQ0100.config2Entity, gIJw0000.util.getDefaultContextMenu = pbdy0020.getDefaultContextMenu
            //     , gIJw0000.measure = gmeb0113, gIJw0000.tileset = jpif0013, gIJw0000['log'] = ifsN0300, gIJw0000.video = {}, gIJw0000.video.Video3D = fhan0100.Video3D
            //     , gIJw0000.video.Video2D = fLLU0000.Video2D;
        }
        , function (ihsV0110, grRI0000, etyn0010) {
            'use strict';
            Object['defineProperty'](grRI0000, '__esModule', {'value': !0x0});
            var egmn0210 = 'function' == typeof Symbol && 'symbol' == typeof Symbol.iterator ? function (gblj0001) {
                return typeof gblj0001;
            } : function (icee0133) {
                return icee0133 && 'function' == typeof Symbol && icee0133.constructor === Symbol && icee0133 !== Symbol['prototype'] ? 'symbol' : typeof icee0133;
            }, jhbh0030 = function () {
                function iXHI0000(eAdh0021, juva0112) {
                    for (var eimf0001 = 0x0; eimf0001 < juva0112.length; eimf0001++) {
                        var gvfp0021 = juva0112[eimf0001];
                        gvfp0021.enumerable = gvfp0021.enumerable || !0x1, gvfp0021['configurable'] = !0x0, 'value' in gvfp0021 && (gvfp0021.writable = !0x0), Object.defineProperty(eAdh0021, gvfp0021['key'], gvfp0021);
                    }
                }

                return function (gd6s0200, jydX0010, ftlR0100) {
                    return jydX0010 && iXHI0000(gd6s0200.prototype, jydX0010), ftlR0100 && iXHI0000(gd6s0200, ftlR0100), gd6s0200;
                };
            }();
            grRI0000.MarsClass = function () {
                function ejLz0000(heZJ0300) {
                    !function (eycb0130, gxTu0000) {
                        if (!(eycb0130 instanceof gxTu0000)) throw new TypeError('Cannot call a class as a function');
                    }(this, ejLz0000), this.addEventListener = this['on'], this.removeEventListener = this.clearAllEventListeners = this.off, this['addOneTimeEventListener'] = this['once'], this['fireEvent'] = this.fire, this.hasEventListeners = this.listens;
                }

                return jhbh0030(ejLz0000, [{
                    'key': 'destroy', 'value': function () {
                        for (var eaip0010 in this) delete this[eaip0010];
                    }
                }, {
                    'key': 'hasEvent', 'value': function (gypM0110) {
                        return !!this._events && !!this._events[gypM0110];
                    }
                }, {
                    'key': 'on', 'value': function (faca0234, _0x14dd1, pi6k0001) {
                        if (ntgd0001(faca0234)) for (var ilva0112 = 0x0, erFh0001 = faca0234['length']; ilva0112 < erFh0001; ilva0112++) this._on(faca0234[ilva0112], _0x14dd1, pi6k0001); else if (sFsq0011(faca0234)) for (var hrIw0101 in faca0234) this['_on'](hrIw0101, faca0234[hrIw0101], _0x14dd1); else for (var jccf0101 = 0x0, eB0y0000 = (faca0234 = iwIh0001(faca0234)).length; jccf0101 < eB0y0000; jccf0101++) this._on(faca0234[jccf0101], _0x14dd1, pi6k0001);
                        return this;
                    }
                }, {
                    'key': 'off', 'value': function (ggBf0200, eDef0032, eLqp0011) {
                        if (ggBf0200) if (ntgd0001(ggBf0200)) for (var i68U0000 = 0x0, gya30030 = ggBf0200['length']; i68U0000 < gya30030; i68U0000++) this._off(ggBf0200[i68U0000], eDef0032, eLqp0011); else if (sFsq0011(ggBf0200)) for (var e2fv0030 in ggBf0200) this._off(e2fv0030, ggBf0200[e2fv0030], eDef0032); else for (var iEah0021 = 0x0, fvda0122 = (ggBf0200 = iwIh0001(ggBf0200)).length; iEah0021 < fvda0122; iEah0021++) this['_off'](ggBf0200[iEah0021], eDef0032, eLqp0011); else delete this._events;
                        return this;
                    }
                }, {
                    'key': '_on', 'value': function (hzSe0001, fnjJ0010, hpAq0100) {
                        this._events = this._events || {};
                        var hUxv0011 = this._events[hzSe0001];
                        hUxv0011 || (hUxv0011 = [], this['_events'][hzSe0001] = hUxv0011), hpAq0100 === this && (hpAq0100 = void 0x0);
                        for (var h4cg0030 = {
                            'fn': fnjJ0010,
                            'ctx': hpAq0100
                        }, gnuE0000 = hUxv0011, jd1f0000 = 0x0, hHK10000 = gnuE0000.length; jd1f0000 < hHK10000; jd1f0000++) if (gnuE0000[jd1f0000]['fn'] === fnjJ0010 && gnuE0000[jd1f0000]['ctx'] === hpAq0100) return;
                        gnuE0000.push(h4cg0030);
                    }
                }, {
                    'key': '_off', 'value': function (iBf50020, huYD0000, enbp0021) {
                        var izgw0101, eTJe0002, f0fL0010;
                        if (this['_events'] && (izgw0101 = this._events[iBf50020])) if (huYD0000) {
                            if (enbp0021 === this && (enbp0021 = void 0x0), izgw0101) for (eTJe0002 = 0x0, f0fL0010 = izgw0101['length']; eTJe0002 < f0fL0010; eTJe0002++) {
                                var edyC0300 = izgw0101[eTJe0002];
                                if (edyC0300.ctx === enbp0021 && edyC0300['fn'] === huYD0000) return edyC0300['fn'] = _0xb7f27, this._firingCount && (this['_events'][iBf50020] = izgw0101 = izgw0101.slice()), void izgw0101.splice(eTJe0002, 0x1);
                            }
                        } else {
                            for (eTJe0002 = 0x0, f0fL0010 = izgw0101.length; eTJe0002 < f0fL0010; eTJe0002++) izgw0101[eTJe0002]['fn'] = _0xb7f27;
                            delete this['_events'][iBf50020];
                        }
                    }
                }, {
                    'key': 'fire', 'value': function (feBf0302, fbsW0000, m4vQ0010) {
                        if (!this.listens(feBf0302, m4vQ0010)) return this;
                        var eclv0210 = iegb0020({}, fbsW0000, {
                            'type': feBf0302,
                            'target': this,
                            'sourceTarget': fbsW0000 && fbsW0000.sourceTarget || this
                        });
                        if (this._events) {
                            var iQag0020 = this['_events'][feBf0302];
                            if (iQag0020) {
                                this['_firingCount'] = this['_firingCount'] + 0x1 || 0x1;
                                for (var qtBT0100 = 0x0, iOdW0010 = iQag0020.length; qtBT0100 < iOdW0010; qtBT0100++) {
                                    var mmcu0000 = iQag0020[qtBT0100];
                                    mmcu0000['fn'].call(mmcu0000.ctx || this, eclv0210);
                                }
                                this['_firingCount']--;
                            }
                        }
                        return m4vQ0010 && this._propagateEvent(eclv0210), this;
                    }
                }, {
                    'key': 'listens', 'value': function (gQiE0010, hIwa0004) {
                        var isar0141 = this._events && this['_events'][gQiE0010];
                        if (isar0141 && isar0141.length) return !0x0;
                        if (hIwa0004) for (var hiYQ0000 in this._eventParents) if (this['_eventParents'][hiYQ0000].listens(gQiE0010, hIwa0004)) return !0x0;
                        return !0x1;
                    }
                }, {
                    'key': 'once', 'value': function (iwxB0110, fNkv0010, hxKl0100) {
                        if (sFsq0011(iwxB0110)) {
                            for (var h3Xj0000 in iwxB0110) this['once'](h3Xj0000, iwxB0110[h3Xj0000], fNkv0010);
                            return this;
                        }
                        var eddb0313 = function (izE30000, iMlb0002) {
                            var gegY0200 = Array.prototype['slice'];
                            if (izE30000.bind) return izE30000.bind['apply'](izE30000, gegY0200.call(arguments, 0x1));
                            var geGk0201 = gegY0200.call(arguments, 0x2);
                            return function () {
                                return izE30000['apply'](iMlb0002, geGk0201.length ? geGk0201['concat'](gegY0200.call(arguments)) : arguments);
                            };
                        }(function () {
                            this.off(iwxB0110, fNkv0010, hxKl0100)['off'](iwxB0110, eddb0313, hxKl0100);
                        }, this);
                        return this['on'](iwxB0110, fNkv0010, hxKl0100)['on'](iwxB0110, eddb0313, hxKl0100);
                    }
                }, {
                    'key': 'addEventParent', 'value': function (fi0l0101) {
                        return this._eventParents = this._eventParents || {}, this._eventParents[iZf40030(fi0l0101)] = fi0l0101, this;
                    }
                }, {
                    'key': 'removeEventParent', 'value': function (ipav0020) {
                        return this._eventParents && delete this._eventParents[iZf40030(ipav0020)], this;
                    }
                }, {
                    'key': '_propagateEvent', 'value': function (hLlg0012) {
                        for (var exkq0100 in this._eventParents) this._eventParents[exkq0100].fire(hLlg0012.type, iegb0020({
                            'layer': hLlg0012.target,
                            'propagatedFrom': hLlg0012['target']
                        }, hLlg0012), !0x0);
                    }
                }]), ejLz0000;
            }(), grRI0000.eventType = {
                'add': 'add',
                'remove': 'remove',
                'delete': 'delete',
                'update': 'update',
                'start': 'start',
                'change': 'change',
                'endItem': 'endItem',
                'end': 'end',
                'stop': 'stop',
                'loadBefore': 'loadBefore',
                'load': 'load',
                'error': 'error',
                'click': 'click',
                'clickMap': 'clickMap',
                'mouseMove': 'mouseMove',
                'mouseOver': 'mouseOver',
                'mouseOut': 'mouseOut',
                'rightClick': 'rightClick',
                'dblClick': 'dblClick',
                'drawStart': 'draw-start',
                'drawMouseMove': 'draw-mouse-move',
                'drawAddPoint': 'draw-add-point',
                'drawRemovePoint': 'draw-remove-lastpoint',
                'drawCreated': 'draw-created',
                'editStart': 'edit-start',
                'editMouseDown': 'edit-mouse-movestart',
                'editMouseMove': 'edit-mouse-move',
                'editMovePoint': 'edit-move-point',
                'editRemovePoint': 'edit-remove-point',
                'editStyle': 'edit-style',
                'editStop': 'edit-stop',
                'initialTilesLoaded': 'initialTilesLoaded',
                'allTilesLoaded': 'allTilesLoaded',
                'loadTileStart': 'loadTileStart',
                'loadTileEnd': 'loadTileEnd',
                'loadTileError': 'loadTileError',
                'beforeCreate': 'beforeCreate',
                'created': 'created',
                'beforeActivate': 'beforeActivate',
                'activated': 'activated',
                'openView': 'openView',
                'beforeDisable': 'beforeDisable',
                'disabled': 'disabled'
            };

            function iegb0020(fooq0101) {
                var feEd0002, f5qT0010, jdSm0001, ggC60000;
                for (f5qT0010 = 0x1, jdSm0001 = arguments.length; f5qT0010 < jdSm0001; f5qT0010++) for (feEd0002 in ggC60000 = arguments[f5qT0010]) fooq0101[feEd0002] = ggC60000[feEd0002];
                return fooq0101;
            }

            function iwIh0001(fjgB0100) {
                return fjgB0100 ? function (ekcr0030) {
                    return ekcr0030.trim ? ekcr0030.trim() : ekcr0030.replace(/^\s+|\s+$/g, '');
                }(fjgB0100).split(/\s+/) : (console.error('传入了空event事件名称，请检查代码'), fjgB0100);
            }

            function _0xb7f27() {
                return !0x1;
            }

            var evde0031 = 0x0;

            function iZf40030(fMBq0001) {
                return fMBq0001._mars3d_id = fMBq0001._mars3d_id || ++evde0031, fMBq0001._mars3d_id;
            }

            function sFsq0011(gbeh0020) {
                return 'object' == (void 0x0 === gbeh0020 ? 'undefined' : egmn0210(gbeh0020)) && gbeh0020.constructor == Object;
            }

            var ntgd0001 = Array.isArray || function (gnV30000) {
                return '[object Array]' === Object.prototype.toString['call'](gnV30000);
            };
        }
        , function (eGew0001, eafE0030, ivRm0000) {
            'use strict';
            Object['defineProperty'](eafE0030, '__esModule', {'value': !0x0}), eafE0030.Video2D = void 0x0;
            var fguy0210 = function () {
                function hbd40200(gT430000, ftMf0003) {
                    for (var gugh0100 = 0x0; gugh0100 < ftMf0003.length; gugh0100++) {
                        var gDxh0011 = ftMf0003[gugh0100];
                        gDxh0011.enumerable = gDxh0011.enumerable || !0x1, gDxh0011.configurable = !0x0, 'value' in gDxh0011 && (gDxh0011.writable = !0x0), Object.defineProperty(gT430000, gDxh0011.key, gDxh0011);
                    }
                }

                return function (oHtj0010, h4eW0030, iofc0022) {
                    return h4eW0030 && hbd40200(oHtj0010['prototype'], h4eW0030), iofc0022 && hbd40200(oHtj0010, iofc0022), oHtj0010;
                };
            }(), gaev0401 = fwbz0020(ivRm0000(0x0)), gjBf0100 = ivRm0000(0x3);
            fwbz0020(ivRm0000(0x5)), ivRm0000(0x1);

            function fwbz0020(iScl0001) {
                if (iScl0001 && iScl0001['__esModule']) return iScl0001;
                var fy8l0100 = {};
                if (null != iScl0001) for (var e0qN0000 in iScl0001) Object.prototype.hasOwnProperty['call'](iScl0001, e0qN0000) && (fy8l0100[e0qN0000] = iScl0001[e0qN0000]);
                return fy8l0100.default = iScl0001, fy8l0100;
            }

            var e7fW0020 = 'Z', hJgb0002 = '-Z', hPcT0000 = 'Y', icuT0110 = '-Y', feTs0001 = 'X', h5ga0010 = '-X';
            (eafE0030.Video2D = function (ifee0111) {
                function iixC0000(imji0101, gIB00000, ix2i0001) {
                    !function (ffde0120, iaKq0000) {
                        if (!(ffde0120 instanceof iaKq0000)) throw new TypeError('Cannot call a class as a function');
                    }(this, iixC0000);
                    var rUjx0010 = function (gkxL0000, tffW0210) {
                        if (!gkxL0000) throw new ReferenceError('this\x20hasn\x27t\x20been\x20initialised\x20-\x20super()\x20hasn\x27t\x20been\x20called');
                        return !tffW0210 || 'object' != typeof tffW0210 && 'function' != typeof tffW0210 ? gkxL0000 : tffW0210;
                    }(this, (iixC0000.__proto__ || Object['getPrototypeOf'](iixC0000)).call(this, gIB00000));
                    return ix2i0001 && (ix2i0001.dom = gIB00000, gIB00000 = ix2i0001), gaev0401.defined(gIB00000.frustumShow) && (gIB00000.showFrustum = gIB00000['frustumShow']), rUjx0010.frustumShow = rUjx0010.showFrustum, rUjx0010.viewer = imji0101,
                        rUjx0010.options = gIB00000, rUjx0010._play = !0x0, gIB00000.aspectRatio ? rUjx0010._aspectRatio = gIB00000.aspectRatio : rUjx0010._aspectRatio = rUjx0010.viewer.scene.context.drawingBufferWidth / rUjx0010['viewer']['scene'].context.drawingBufferHeight, rUjx0010['_fov'] = gaev0401.defaultValue(gIB00000.fov, rUjx0010.viewer.scene.camera.frustum.fov), rUjx0010['_dis'] = gaev0401.defaultValue(gIB00000['dis'], 0xa), rUjx0010._stRotation = gaev0401.defaultValue(gIB00000.stRotation, 0x0), rUjx0010._rotateCam = gaev0401.defaultValue(gIB00000.rotateCam, 0.05), rUjx0010._frustumShow = gaev0401['defaultValue'](gIB00000.showFrustum, !0x0), rUjx0010._camera = gIB00000.camera
                        , gIB00000['dom'] && (gIB00000.dom instanceof Object && gIB00000['dom'].length ? rUjx0010.dom = gIB00000.dom[0x0] : rUjx0010.dom = gIB00000.dom), gIB00000.click && rUjx0010['on'](gjBf0100.eventType['click'], gIB00000.click), rUjx0010.init(), rUjx0010;
                }

                return function (f63a0000, e58j0000) {
                    if ('function' != typeof e58j0000 && null !== e58j0000) throw new TypeError('Super\x20expression\x20must\x20either\x20be\x20null\x20or\x20a\x20function,\x20not\x20' + typeof e58j0000);
                    f63a0000.prototype = Object.create(e58j0000 && e58j0000['prototype'], {
                        'constructor': {
                            'value': f63a0000,
                            'enumerable': !0x1,
                            'writable': !0x0,
                            'configurable': !0x0
                        }
                    }), e58j0000 && (Object.setPrototypeOf ? Object.setPrototypeOf(f63a0000, e58j0000) : f63a0000.__proto__ = e58j0000);
                }(iixC0000, ifee0111), fguy0210(iixC0000, [{
                    'key': 'init', 'value': function () {
                        this.recordObj = this['record'](), this.rectPos = this.computedPos(this.dis, this['fov'], this['aspectRatio'], this.recordObj);
                        var pofk0120 = this.getOrientation(this.recordObj),
                            fhcm0001 = this.createFrustum(this.fov, this.aspectRatio, this.dis),
                            fe6k0200 = this.createFrustumGeo(fhcm0001, pofk0120, this.recordObj.position);
                        this.frustumPri = this['createFrustumPri'](fe6k0200), this.addToScene();
                    }
                }, {
                    'key': 'reset', 'value': function () {
                        this.viewer.scene['primitives'].remove(this.frustumPri), this['viewer'].entities.remove(this.entity), this.rectPos = this.computedPos(this.dis, this.fov, this['aspectRatio'], this.recordObj);
                        var jhwv0001 = this.getOrientation(this.recordObj),
                            gpKT0000 = this.createFrustum(this.fov, this['aspectRatio'], this.dis),
                            fTLX0000 = this.createFrustumGeo(gpKT0000, jhwv0001, this.recordObj.position);
                        this.frustumPri = this.createFrustumPri(fTLX0000), this['addToScene']();
                    }
                }, {
                    'key': 'record', 'value': function () {
                        var rfef0020 = {}, p1fg0002 = this['_camera'] || this['viewer'].scene.camera;
                        return rfef0020.direction = gaev0401.clone(p1fg0002.direction), rfef0020['up'] = gaev0401.clone(p1fg0002['up']), rfef0020.right = gaev0401.clone(p1fg0002.right), rfef0020['position'] = gaev0401.clone(p1fg0002.position), rfef0020;
                    }
                }, {
                    'key': 'addToScene', 'value': function () {
                        this.viewer.scene.primitives.add(this.frustumPri), this.entity = this.viewer['entities'].add({
                            'polygon': {
                                'hierarchy': this.rectPos,
                                'perPositionHeight': !0x0,
                                'material': this.dom || this.options.material,
                                'stRotation': this.stRotation
                            },
                            'data': this.options,
                            'eventTarget': this,
                            'popup': this.options.popup,
                            'tooltip': this.options['tooltip']
                        });
                    }
                }, {
                    'key': 'computedPos', 'value': function (gjDs0101, g2Kp0000, f6Uu0000, glnt0001) {
                        var epqI0010 = glnt0001.position, hxgq0001 = glnt0001['direction'], iGu10000 = glnt0001.right,
                            eeVz0301 = glnt0001['up'], sNDG0000 = new gaev0401[('Ray')](epqI0010, hxgq0001),
                            eaad0020 = gaev0401['Ray']['getPoint'](sNDG0000, gjDs0101, new gaev0401[('Cartesian3')]()),
                            ihpj0001 = g2Kp0000 / 0x2, iDkw0000 = gjDs0101 * Math['tan'](ihpj0001),
                            i1Ic0000 = iDkw0000 / f6Uu0000,
                            kfge0021 = Math['sqrt'](iDkw0000 * iDkw0000 + i1Ic0000 * i1Ic0000),
                            odRT0200 = new gaev0401['Cartesian3'](), iplv0101 = new gaev0401['Ray'](eaad0020, iGu10000),
                            fycy0030 = gaev0401.Ray.getPoint(iplv0101, iDkw0000, new gaev0401[('Cartesian3')]()),
                            i4mn0000 = new gaev0401[('Ray')](fycy0030, eeVz0301);
                        gaev0401.Ray['getPoint'](i4mn0000, i1Ic0000, odRT0200);
                        var fBcf0023 = new gaev0401['Cartesian3'](),
                            iAuM0010 = gaev0401['Cartesian3'].negate(eeVz0301, new gaev0401[('Cartesian3')]()),
                            ijyt0101 = new gaev0401[('Ray')](fycy0030, iAuM0010);
                        gaev0401.Ray['getPoint'](ijyt0101, i1Ic0000, fBcf0023);
                        var imvc0010 = new gaev0401[('Cartesian3')](),
                            itWr0101 = gaev0401.Cartesian3.normalize(gaev0401.Cartesian3.subtract(eaad0020, odRT0200, new gaev0401['Cartesian3']()), new gaev0401[('Cartesian3')]()),
                            iBNc0001 = new gaev0401[('Ray')](eaad0020, itWr0101);
                        gaev0401['Ray'].getPoint(iBNc0001, kfge0021, imvc0010);
                        var jg2E0100 = new gaev0401[('Cartesian3')](),
                            iy1b0100 = gaev0401.Cartesian3.normalize(gaev0401.Cartesian3.subtract(eaad0020, fBcf0023, new gaev0401[('Cartesian3')]()), new gaev0401[('Cartesian3')]()),
                            gazi0311 = new gaev0401[('Ray')](eaad0020, iy1b0100);
                        return gaev0401['Ray']['getPoint'](gazi0311, kfge0021, jg2E0100), this.options['reverse'] ? [imvc0010, jg2E0100, odRT0200, fBcf0023].reverse() : [imvc0010, jg2E0100, odRT0200, fBcf0023];
                    }
                }, {
                    'key': 'createFrustum', 'value': function (hG3l0000, ggws0000, pami0400) {
                        return new gaev0401[('PerspectiveFrustum')]({
                            'fov': hG3l0000,
                            'aspectRatio': ggws0000,
                            'near': 0.1,
                            'far': pami0400
                        });
                    }
                }, {
                    'key': 'getOrientation', 'value': function (fbEO0200) {
                        if (fbEO0200) {
                            var qBlz0000 = fbEO0200['direction'], f8uk0000 = fbEO0200['up'], fjhi0010 = fbEO0200.right,
                                exbt0101 = new gaev0401[('Cartesian3')](), eufA0000 = new gaev0401[('Matrix3')](),
                                gfyR0310 = new gaev0401[('Quaternion')]();
                            fjhi0010 = gaev0401.Cartesian3.negate(fjhi0010, exbt0101);
                            var gTM80000 = eufA0000;
                            return gaev0401.Matrix3['setColumn'](gTM80000, 0x0, fjhi0010, gTM80000), gaev0401['Matrix3'].setColumn(gTM80000, 0x1, f8uk0000, gTM80000), gaev0401.Matrix3.setColumn(gTM80000, 0x2, qBlz0000, gTM80000), gaev0401.Quaternion.fromRotationMatrix(gTM80000, gfyR0310);
                        }
                    }
                }, {
                    'key': 'createFrustumGeo', 'value': function (iia80000, fCgg0020, i6b40020) {
                        return new gaev0401[('FrustumOutlineGeometry')]({
                            'frustum': iia80000,
                            'orientation': fCgg0020,
                            'origin': i6b40020
                        });
                    }
                }, {
                    'key': 'createFrustumPri', 'value': function (eaQG0200) {
                        return new gaev0401['Primitive']({
                            'geometryInstances': new gaev0401[('GeometryInstance')]({
                                'geometry': eaQG0200,
                                'attributes': {'color': gaev0401['ColorGeometryInstanceAttribute'].fromColor(gaev0401['Color'].AZURE)}
                            }),
                            'appearance': new gaev0401[('PerInstanceColorAppearance')]({'flat': !0x0}),
                            'show': this.showFrustum
                        });
                    }
                }, {
                    'key': 'locate', 'value': function () {
                        this.viewer['camera']['direction'] = gaev0401['clone'](this.recordObj.direction), this.viewer.camera['right'] = gaev0401.clone(this.recordObj['right']), this.viewer.camera['up'] = gaev0401['clone'](this.recordObj['up']), this['viewer'].camera.position = gaev0401.clone(this.recordObj.position);
                    }
                }, {
                    'key': 'rotateCamera', 'value': function (hpe60130, ii8I0000) {
                        var itIS0000 = gaev0401['defaultValue'](ii8I0000, this._rotateCam);
                        switch (hpe60130) {
                            case e7fW0020:
                                break;
                            case hJgb0002:
                                itIS0000 *= -0x1;
                                break;
                            case hPcT0000:
                                break;
                            case icuT0110:
                                itIS0000 *= -0x1;
                                break;
                            case feTs0001:
                                break;
                            case h5ga0010:
                                itIS0000 *= -0x1;
                        }
                        var fLBi0000 = this._computedNewViewDir(hpe60130, itIS0000);
                        this.recordObj.direction = fLBi0000.direction, this.recordObj['up'] = fLBi0000['up'], this.recordObj.right = fLBi0000.right, this['reset']();
                    }
                }, {
                    'key': '_computedNewViewDir', 'value': function (gofs0110, jvas0040) {
                        jvas0040 = gaev0401.Math['toRadians'](jvas0040);
                        var eHbr0021 = this['recordObj'], eMK30000 = gaev0401.clone(eHbr0021.direction),
                            iGj50000 = gaev0401['clone'](eHbr0021.right), saze0413 = gaev0401['clone'](eHbr0021['up']),
                            f00b0002 = new gaev0401[('Matrix3')]();
                        switch (gofs0110) {
                            case e7fW0020:
                            case hJgb0002:
                                gaev0401.Matrix3.fromRotationZ(jvas0040, f00b0002);
                                break;
                            case hPcT0000:
                            case icuT0110:
                                gaev0401['Matrix3']['fromRotationY'](jvas0040, f00b0002);
                                break;
                            case feTs0001:
                            case h5ga0010:
                                gaev0401['Matrix3'].fromRotationX(jvas0040, f00b0002);
                        }
                        var gdao0130 = gaev0401.Transforms['eastNorthUpToFixedFrame'](eHbr0021.position),
                            gcsU0210 = gaev0401.Matrix4.inverse(gdao0130, new gaev0401[('Matrix4')]()),
                            fnte0000 = gaev0401.Matrix4['multiplyByPointAsVector'](gcsU0210, eMK30000, new gaev0401[('Cartesian3')]()),
                            fdqr0311 = gaev0401.Matrix3.multiplyByVector(f00b0002, fnte0000, new gaev0401['Cartesian3']()),
                            edZZ0300 = gaev0401.Matrix4['multiplyByPointAsVector'](gdao0130, fdqr0311, new gaev0401['Cartesian3']()),
                            fu1h0100 = gaev0401.Matrix4.multiplyByPointAsVector(gcsU0210, iGj50000, new gaev0401[('Cartesian3')]()),
                            hlon0010 = gaev0401.Matrix3['multiplyByVector'](f00b0002, fu1h0100, new gaev0401[('Cartesian3')]()),
                            hs0s0000 = gaev0401.Matrix4.multiplyByPointAsVector(gdao0130, hlon0010, new gaev0401['Cartesian3']()),
                            hde90230 = gaev0401.Matrix4.multiplyByPointAsVector(gcsU0210, saze0413, new gaev0401[('Cartesian3')]()),
                            lMoa0004 = gaev0401.Matrix3.multiplyByVector(f00b0002, hde90230, new gaev0401[('Cartesian3')]());
                        return {
                            'direction': edZZ0300,
                            'right': hs0s0000,
                            'up': gaev0401.Matrix4.multiplyByPointAsVector(gdao0130, lMoa0004, new gaev0401[('Cartesian3')]())
                        };
                    }
                }, {
                    'key': 'destroy', 'value': function () {
                        for (var ixeY0020 in (this.viewer.scene.primitives.remove(this.frustumPri), this['viewer']['entities'].remove(this.entity), this)) delete this[ixeY0020];
                    }
                }, {
                    'key': 'play', 'get': function () {
                        return this['_play'];
                    }, 'set': function (go8g0002) {
                        this._play = go8g0002, this.dom && (this._play ? this['dom'].play() : this.dom['pause']());
                    }
                }, {
                    'key': 'aspectRatio', 'get': function () {
                        return this._aspectRatio;
                    }, 'set': function (hyfc0001) {
                        !(hyfc0001 = Number(hyfc0001)) || hyfc0001 < 0x0 || (hyfc0001 < 0x1 && (hyfc0001 = 0x1), this['_aspectRatio'] = hyfc0001, this.reset());
                    }
                }, {
                    'key': 'fov', 'get': function () {
                        return this._fov;
                    }, 'set': function (_0xbcf16) {
                        !(_0xbcf16 = Number(_0xbcf16)) || _0xbcf16 < 0x0 || (this._fov = _0xbcf16, this.reset());
                    }
                }, {
                    'key': 'dis', 'get': function () {
                        return this._dis;
                    }, 'set': function (gQNh0000) {
                        !(gQNh0000 = Number(gQNh0000)) || gQNh0000 < 0x0 || (this._dis = gQNh0000, this.reset());
                    }
                }, {
                    'key': 'stRotation', 'get': function () {
                        return this._stRotation;
                    }, 'set': function (eLIp0000) {
                        !(eLIp0000 = Number(eLIp0000)) || eLIp0000 < 0x0 || (this['_stRotation'] = eLIp0000, this.entity.polygon.stRotation = eLIp0000);
                    }
                }, {
                    'key': 'showFrustum', 'get': function () {
                        return this._frustumShow;
                    }, 'set': function (eSqg0002) {
                        this._frustumShow = eSqg0002, this.frustumPri.show = eSqg0002;
                    }
                }, {
                    'key': 'params', 'get': function () {
                        return {
                            'fov': this.fov,
                            'dis': this['dis'],
                            'stRotation': this.stRotation,
                            'showFrustum': this.showFrustum,
                            'aspectRatio': this.aspectRatio,
                            'camera': {
                                'position': this.recordObj.position,
                                'direction': this.recordObj.direction,
                                'up': this.recordObj['up'],
                                'right': this.recordObj.right
                            }
                        };
                    }
                }]), iixC0000;
            }(gjBf0100['MarsClass'])).event = {
                'click': gjBf0100.event['click'],
                'mouseOver': gjBf0100.event.mouseOver,
                'mouseOut': gjBf0100.event.mouseOut
            };
        }
        , function (hNvE0010, fmsw0101, gJuP0010) {
            'use strict';
            Object['defineProperty'](fmsw0101, '__esModule', {'value': !0}), fmsw0101.FlyLine = void 0;
            var pNOl0001 = Object.assign || function (gjSf0103) {
                for (var i = 1; i < arguments.length; i++) {
                    var fpcE0020 = arguments[i];
                    for (var ifIJ0000 in fpcE0020) Object.prototype.hasOwnProperty.call(fpcE0020, ifIJ0000) && (gjSf0103[ifIJ0000] = fpcE0020[ifIJ0000]);
                }
                return gjSf0103;
            }, hakg0400 = function () {
                function e9u20000(i2zo0010, gfeO0030) {
                    for (var feMs0001 = 0; feMs0001 < gfeO0030.length; feMs0001++) {
                        var hBSJ0000 = gfeO0030[feMs0001];
                        hBSJ0000.enumerable = hBSJ0000.enumerable || !1, hBSJ0000.configurable = !0,
                        'value' in hBSJ0000 && (hBSJ0000.writable = !0), Object.defineProperty(i2zo0010, hBSJ0000.key, hBSJ0000);
                    }
                }

                return function (prDr0101, fgxq0000, flca0030) {
                    return fgxq0000 && e9u20000(prDr0101.prototype, fgxq0000), flca0030 && e9u20000(prDr0101, flca0030), prDr0101;
                };
            }(), hfql0311 = gJuP0010(11);

            function fx5c0001(i2wy0001) {
                if (i2wy0001 && i2wy0001.__esModule) return i2wy0001;
                var gG6h0000 = {};
                if (null != i2wy0001) for (var gnwf0001 in i2wy0001) Object['prototype']['hasOwnProperty']['call'](i2wy0001, gnwf0001) && (gG6h0000[gnwf0001] = i2wy0001[gnwf0001]);
                return gG6h0000.default = i2wy0001, gG6h0000;
            }

            function hjUa0103(itTq0101, izjf0112) {
                if (!(itTq0101 instanceof izjf0112)) throw new TypeError('Cannot call a class as a function');
            }

            function fkqg0102(ggvf0101, i5lw0001) {
                if (!ggvf0101) throw new ReferenceError('this hasnt been initialised - super() hasnt been called');
                return !i5lw0001 || 'object' != typeof i5lw0001 && 'function' != typeof i5lw0001 ? ggvf0101 : i5lw0001;
            }

            (fmsw0101.FlyLine = function (fgl10010) {
                function ikfq0121() {
                    return hjUa0103(this, ikfq0121), fkqg0102(this, (ikfq0121.__proto__ || Object.getPrototypeOf(ikfq0121)).apply(this, arguments));
                }

                return function (gbJE0200, igqd0102) {
                    if ('function' != typeof igqd0102 && null !== igqd0102) throw new TypeError('Super expression must either be null or a function, not ' + typeof igqd0102);
                    gbJE0200['prototype'] = Object.create(igqd0102 && igqd0102.prototype, {
                        'constructor': {
                            'value': gbJE0200,
                            'enumerable': !1,
                            'writable': !0,
                            'configurable': !0
                        }
                    }), igqd0102 && (Object.setPrototypeOf ? Object.setPrototypeOf(gbJE0200, igqd0102) : gbJE0200.__proto__ = igqd0102);
                }(ikfq0121, fgl10010), hakg0400(ikfq0121, [{
                    'key': 'init', 'value': function () {
                        this._isStart = !1, this.options.points && this.createPath(this.options.points, this.options);
                    }
                }, {
                    'key': 'createPath', 'value': function (pathPoints, attr) {
                        if (!pathPoints || pathPoints.length < 2) mars3d.log.warn('路线无坐标数据，无法漫游！', pathPoints);
                        else {
                            this.points = pathPoints, attr = attr || {};
                            var iocp0010, i4aB0000, hglX0000 = Cesium.defaultValue(attr.offsetHeight, 0);
                            iocp0010 = attr.startTime ? Cesium.JulianDate.fromDate(new Date(attr.startTime)) : this.viewer.clock.currentTime;
                            var icLd0000 = !(0, mars3d.util.isNumber)(attr.speed);
                            if (2 == pathPoints.length) {
                                var haXm0201 = [(pathPoints[0][0] + pathPoints[1][0]) / 2, (pathPoints[0][1] + pathPoints[1][1]) / 2, pathPoints[0][2]];
                                pathPoints.splice(1, 0, haXm0201), attr.speed && icLd0000 && attr.speed.splice(1, 0, attr.speed[0]);
                            }
                            var ruyt0100 = new Cesium.SampledPositionProperty();
                            this.positions = [], this.times = [];
                            var efc10130, efht0111 = [], gfXb0103 = 0, _0xecb6f = 0, eqgc0002 = {}, giqK0110 = {};
                            for (var i = 0; i < pathPoints.length; i++) {
                                var fscd0012, eTXd0003 = pathPoints[i],
                                    fG5b0002 = Cesium.Cartesian3.fromDegrees(eTXd0003[0], eTXd0003[1], (eTXd0003[2] || 0) + hglX0000);
                                if (fG5b0002.lonlat = eTXd0003, 0 == i) fscd0012 = Cesium.JulianDate.addSeconds(iocp0010, gfXb0103, new Cesium.JulianDate())
                                    , fG5b0002.time = fscd0012, fG5b0002['second'] = gfXb0103, ruyt0100.addSample(fscd0012, fG5b0002);
                                else {
                                    var iFcw0000 = icLd0000 ? attr.speed ? attr.speed[i - 1] : 0x64 : attr.speed || 0x64;
                                    efht0111.push(iFcw0000), iFcw0000 /= 3.6;
                                    var iR600000 = Cesium.Cartesian3.distance(fG5b0002, efc10130),
                                        g0fp0010 = iR600000 / iFcw0000;
                                    g0fp0010 < 0.01 && (g0fp0010 = 0.01), gfXb0103 += g0fp0010, _0xecb6f += iR600000,
                                        fscd0012 = Cesium.JulianDate.addSeconds(iocp0010, gfXb0103, new Cesium.JulianDate()), fG5b0002['time'] = fscd0012,
                                        fG5b0002.second = gfXb0103, ruyt0100['addSample'](fscd0012, fG5b0002),
                                    attr.pauseTime && ('function' == typeof attr.pauseTime ? gfXb0103 += attr.pauseTime(i, fG5b0002) : gfXb0103 += attr.pauseTime,
                                        fscd0012 = Cesium.JulianDate.addSeconds(iocp0010, gfXb0103, new Cesium.JulianDate())
                                        , ruyt0100['addSample'](fscd0012, (0, mars3d.matrix.getOnLinePointByLen)(efc10130, fG5b0002, 0.01, !0)));
                                }
                                efc10130 = fG5b0002, this.positions.push(fG5b0002),
                                    this.times.push(fscd0012), eqgc0002[i] = _0xecb6f, giqK0110[i] = gfXb0103;
                            }
                            this.arrSpeed = efht0111, this.lastItem = {
                                'position': this.positions[this.positions.length - 1],
                                'time': this.times[this['times'].length - 1]
                            }, i4aB0000 = Cesium.JulianDate.addSeconds(iocp0010, gfXb0103, new Cesium['JulianDate']()), this.alltimes = gfXb0103, this.alllen = _0xecb6f, this['stepLen'] = eqgc0002, this.stepTime = giqK0110, this.startTime = iocp0010, this.stopTime = i4aB0000, this.property = ruyt0100, this.velocityOrientation = new Cesium['VelocityOrientationProperty'](this.property),
                            attr.interpolation && this.property.setInterpolationOptions({
                                'interpolationDegree': attr.interpolationDegree || 2,
                                'interpolationAlgorithm': Cesium.LagrangePolynomialApproximation
                            });
                        }
                    }
                }, {
                    'key': 'start', 'value': function (h0Xh0001) {
                        Cesium.defined(this['positions']) && 0x0 != this.positions['length'] ? (this._isStart && this.stop(), this['_isStart'] = !0x0, this['_createEntity']()
                            , Cesium.defined(this.options.multiplier) && (this['_bak_multiplier'] = this.viewer.clock.multiplier, this['viewer'].clock.multiplier = this.options.multiplier)
                            , this.viewer.clock['shouldAnimate'] = !0x0, this.viewer.clock.currentTime = this['startTime'].clone()
                            , (this.options.clockRange || this.options.clockLoop) && (this._bak_clockRange = this.viewer.clock['clockRange']
                            , this._bak_startTime = this.viewer.clock.startTime, this._bak_stopTime = this.viewer.clock.stopTime
                            , this.viewer['clock'].clockRange = Cesium.defaultValue(this.options.clockRange, Cesium.ClockRange.LOOP_STOP)
                            , this['viewer'].clock['startTime'] = this['startTime']['clone'](), this.viewer.clock.stopTime = this.stopTime.clone())
                            , this['viewer'].timeline && this['viewer'].timeline.zoomTo(this['startTime'], this['stopTime'])
                            , this.options.shadow && this.options.shadow['length'] > 0x0 && this._addArrShading()
                            , this._flyok_point_index = 0x0, this.fire(mars3d.event['endItem'], {
                            'index': this['_flyok_point_index'],
                            'counts': this.positions.length
                        }), this.fire(mars3d.event['start']), this.viewer.scene['preRender'].addEventListener(this['preRender_eventHandler'], this)) : mars3d.log['warn']('没有坐标数据，飞行路线启动失败', this['positions']);
                    }
                }, {
                    'key': 'stop', 'value': function () {
                        if (this.viewer.trackedEntity = void 0x0, this.viewer.scene.preRender['removeEventListener'](this.preRender_eventHandler, this), this.entity && (this.viewer['entities'].remove(this.entity), delete this.entity), this.arrShowingEntity) {
                            for (var gZwl0001 = 0x0, jdmy0001 = this.arrShowingEntity.length; gZwl0001 < jdmy0001; gZwl0001++) this.viewer.entities['remove'](this['arrShowingEntity'][gZwl0001]);
                            delete this['arrShowingEntity'];
                        }
                        this._bak_startTime && (this.viewer['clock'].startTime = this['_bak_startTime'], delete this['_bak_startTime']), this._bak_stopTime && (this.viewer['clock'].stopTime = this['_bak_stopTime'], delete this._bak_stopTime), this._bak_multiplier && (this.viewer.clock.multiplier = this._bak_multiplier, delete this['_bak_multiplier']), this['_bak_clockRange']
                        && (this.viewer.clock.clockRange = this['_bak_clockRange'], delete this['_bak_clockRange']), this._flyok_point_index = 0x0, this._isStart = !0x1, this['fire'](mars3d.event['end']);
                    }
                }, {
                    'key': 'preRender_eventHandler', 'value': function (gawh0301) {
                        if (this._isStart && null != this.entity) {
                            if (this.viewer.clock['shouldAnimate'] && Cesium.JulianDate['greaterThanOrEquals'](this.viewer.clock.currentTime, this['stopTime'])) return this._flyok_point_index = this.positions['length'] - 0x1, this['_onStepTempBS'] || (this.fire(mars3d.eventType.endItem, {
                                'index': this['_flyok_point_index'],
                                'counts': this.positions.length
                            }), this.fire(mars3d.event.end), this._onStepTempBS = !0x0), void ((this['options']['autoStop'] || this.viewer.clock.clockRange == Cesium.ClockRange.UNBOUNDED) && this['stop']());
                            var hovI0010 = this['position'];
                            if (Cesium.defined(hovI0010)) {
                                switch (this.options.camera.type) {
                                    default:
                                        null != this.viewer.trackedEntity && (this.viewer.trackedEntity = void 0x0, this.flyTo(this.options.camera));
                                        break;
                                    case 'gs':
                                        this.viewer.trackedEntity != this['entity'] && (this.viewer.trackedEntity = this.entity, this['flyTo'](this.options.camera));
                                        break;
                                    case 'dy':
                                        this.viewer.trackedEntity != this.entity && (this.viewer.trackedEntity = this.entity);
                                        var hgVg0000 = this.getModelMatrix(),
                                            eFLb0002 = this.options.camera['followedX'],
                                            hJye0010 = this.options.camera['followedZ'];
                                        this['viewer'].scene.camera.lookAtTransform(hgVg0000, new Cesium[('Cartesian3')](-eFLb0002, 0x0, hJye0010));
                                        break;
                                    case 'sd':
                                        this.viewer['trackedEntity'] != this.entity && (this.viewer.trackedEntity = this.entity), this.viewer.scene.camera.lookAtTransform(this.getModelMatrix(), new Cesium['Cartesian3'](-0x1, 0x0, this.options.camera.followedZ));
                                }
                                this['viewer'].clock.shouldAnimate && this.realTime(hovI0010);
                            }
                        }
                    }
                }, {
                    'key': 'realTime', 'value': function (fzkl0101) {
                        var hgcr0011 = Cesium['JulianDate']['secondsDifference'](this['viewer']['clock'].currentTime, this['startTime']),
                            graf0133 = (0x0, mars3d.point.formatPosition)(fzkl0101), hoWS0100 = this.getCurrIndex(),
                            eue20120 = this.positions.length, jw6q0001 = this.stepLen[hoWS0100],
                            pvEk0001 = this.positions[hoWS0100];
                        Cesium['defined'](pvEk0001) && (jw6q0001 += Cesium['Cartesian3'].distance(fzkl0101, pvEk0001)), jw6q0001 >= this.alllen && (hoWS0100 = eue20120 - 0x1
                            , jw6q0001 = this['alllen']), hoWS0100 != this._flyok_point_index && this.fire(mars3d['event'].endItem, {
                            'index': hoWS0100,
                            'counts': eue20120
                        }), this._flyok_point_index = hoWS0100, this.timeinfo = {
                            'time': hgcr0011,
                            'len': jw6q0001,
                            'x': graf0133['x'],
                            'y': graf0133['y'],
                            'z': graf0133['z']
                        }, this.options.shadow && this.options['shadow']['length'] > 0x0 && this['_updateArrShading'](fzkl0101);
                        var gglk0211 = Cesium.Cartographic['fromCartesian'](fzkl0101),
                            hRjh0000 = this.viewer['scene'].globe.getHeight(gglk0211);
                        if (null != hRjh0000 && hRjh0000 > 0x0 && (this.timeinfo['hbgd'] = hRjh0000, this.timeinfo.ldgd = graf0133['z'] - hRjh0000), this.options.showGroundHeight) {
                            var jxcs0021 = this;
                            (0x0, mars3d.polyline['computeSurfaceLine'])({
                                'viewer': jxcs0021.viewer,
                                'positions': [fzkl0101, fzkl0101],
                                'callback': function (fF0W0000, jq5X0000) {
                                    if (null != fF0W0000 && 0x0 != fF0W0000['length'] && !jq5X0000) {
                                        var eUuk0010 = (0x0, mars3d.point['formatPosition'])(fF0W0000[0x0])['z'],
                                            rpwF0010 = graf0133['z'] - eUuk0010;
                                        if (jxcs0021.timeinfo.hbgd = eUuk0010, jxcs0021['timeinfo'].ldgd = rpwF0010, jxcs0021['entity'].label) {
                                            var f3cX0000 = (0x0, mars3d.util['formatLength'])(jxcs0021.timeinfo['z']),
                                                goad0101 = (0x0, mars3d.util['formatLength'])(jxcs0021.timeinfo.ldgd);
                                            jxcs0021.entity.label.text = jxcs0021.name + '\n漫游高程：' + f3cX0000 + '\n离地距离：' + goad0101;
                                        }
                                    }
                                }
                            });
                        }
                    }
                }, {
                    'key': 'clampToGround', 'value': function (psfs0100, ipzq0110) {
                        ipzq0110 = ipzq0110 || {};
                        var hlak0020 = this.points, papf0003 = this.arrSpeed || this.options.speed, g7u90000 = [],
                            gzLd0103 = [], mpzu0001 = 0x0, gsze0111 = [], jfbc0020 = [], igff0022 = [], l6pi0011 = [],
                            eXKH0000 = this;
                        (0x0, mars3d.polyline.computeStepSurfaceLine)({
                            'viewer': this.viewer,
                            'positions': this.positions,
                            'has3dtiles': ipzq0110.has3dtiles,
                            'splitNum': ipzq0110.splitNum,
                            'offset': ipzq0110['offset'],
                            'endItem': function (hGge0001, gpbb0033, hoen0020) {
                                var ggEO0000 = papf0003[hoen0020];
                                if (gpbb0033) g7u90000.push(hlak0020[hoen0020]), gzLd0103.push(ggEO0000); else for (var eaUY0000 = 0x0; eaUY0000 < hGge0001.length; eaUY0000++) {
                                    var ifjL0000 = hGge0001[eaUY0000],
                                        fcve0303 = Cesium['Cartographic'].fromCartesian(ifjL0000);
                                    g7u90000.push([Cesium.Math.toDegrees(fcve0303.longitude), Cesium['Math'].toDegrees(fcve0303.latitude), fcve0303.height]), gzLd0103.push(ggEO0000);
                                }
                                for (var gbmO0210 = hlak0020[hoen0020][0x2] || 0x0, gwm30100 = ((hlak0020[hoen0020 + 0x1][0x2] || 0x0) - gbmO0210) / hGge0001['length'], rgT70100 = 0x0; rgT70100 < hGge0001.length; rgT70100++) {
                                    0x0 != rgT70100 && (mpzu0001 += Cesium['Cartesian3'].distance(hGge0001[rgT70100], hGge0001[rgT70100 - 0x1])), gsze0111.push(Number(mpzu0001.toFixed(0x1)));
                                    var eJkc0013 = (0x0, mars3d.point.formatPosition)(hGge0001[rgT70100]);
                                    l6pi0011.push(eJkc0013);
                                    var _0x45e71 = gpbb0033 ? 0x0 : eJkc0013['z'];
                                    jfbc0020.push(_0x45e71);
                                    var h8Pd0002 = Number((gbmO0210 + gwm30100 * rgT70100).toFixed(0x1));
                                    igff0022.push(h8Pd0002);
                                }
                            },
                            'end': function () {
                                eXKH0000['terrainHeight'] = {
                                    'arrLength': gsze0111,
                                    'arrFxgd': igff0022,
                                    'arrHbgd': jfbc0020,
                                    'arrPoint': l6pi0011
                                }, eXKH0000.createPath(g7u90000, pNOl0001({}, eXKH0000['options'], {'speed': gzLd0103})), psfs0100 && psfs0100({
                                    'lonlats': g7u90000,
                                    'speed': gzLd0103
                                });
                            }
                        });
                    }
                }, {
                    'key': 'getTerrainHeight', 'value': function (irIB0100, iabE0330) {
                        if (this.terrainHeight) return irIB0100(this.terrainHeight), this.terrainHeight;
                        iabE0330 = iabE0330 || {};
                        var gcsk0301 = this['points'], iNx70010 = 0x0, h6St0000 = [], gItY0010 = [], fibp0031 = [],
                            hif70110 = [], ff2W0000 = this;
                        (0x0, mars3d.polyline.computeStepSurfaceLine)({
                            'viewer': this.viewer,
                            'positions': this.positions,
                            'has3dtiles': iabE0330.has3dtiles,
                            'splitNum': iabE0330.splitNum,
                            'offset': iabE0330.offset,
                            'endItem': function (_0x5ace2, jdbs0030, f31J0000) {
                                for (var itWa0102 = gcsk0301[f31J0000][0x2] || 0x0, hxQr0100 = ((gcsk0301[f31J0000 + 0x1][0x2] || 0x0) - itWa0102) / _0x5ace2.length, h1im0010 = 0x0; h1im0010 < _0x5ace2['length']; h1im0010++) {
                                    0x0 != h1im0010 && (iNx70010 += Cesium.Cartesian3.distance(_0x5ace2[h1im0010], _0x5ace2[h1im0010 - 0x1])), h6St0000.push(Number(iNx70010['toFixed'](0x1)));
                                    var edXM0200 = (0x0, mars3d.point.formatPosition)(_0x5ace2[h1im0010]);
                                    hif70110['push'](edXM0200);
                                    var iwep0001 = jdbs0030 ? 0x0 : edXM0200['z'];
                                    gItY0010.push(iwep0001);
                                    var gdVk0001 = Number((itWa0102 + hxQr0100 * h1im0010).toFixed(0x1));
                                    fibp0031.push(gdVk0001);
                                }
                            },
                            'end': function () {
                                ff2W0000.terrainHeight = {
                                    'arrLength': h6St0000,
                                    'arrFxgd': fibp0031,
                                    'arrHbgd': gItY0010,
                                    'arrPoint': hif70110
                                }, irIB0100(ff2W0000.terrainHeight);
                            }
                        });
                    }
                }, {
                    'key': 'toJSON', 'value': function () {
                        return this.options;
                    }
                }, {
                    'key': 'destroy', 'value': function () {
                        this['stop'](), function g2iq0011(elUO0100, hUkt0010, gnUj0001) {
                            null === elUO0100 && (elUO0100 = Function['prototype']);
                            var iqo30000 = Object.getOwnPropertyDescriptor(elUO0100, hUkt0010);
                            if (void 0x0 === iqo30000) {
                                var h1Zv0000 = Object.getPrototypeOf(elUO0100);
                                return null === h1Zv0000 ? void 0x0 : g2iq0011(h1Zv0000, hUkt0010, gnUj0001);
                            }
                            if ('value' in iqo30000) return iqo30000.value;
                            var hqgc0000 = iqo30000.get;
                            return void 0x0 !== hqgc0000 ? hqgc0000['call'](gnUj0001) : void 0x0;
                        }(ikfq0121.prototype.__proto__ || Object.getPrototypeOf(ikfq0121.prototype), 'destroy', this).call(this);
                    }
                }, {
                    'key': 'info', 'get': function () {
                        return this.timeinfo;
                    }
                }]), ikfq0121;
            }(hfql0311.BaseFlyLine)).event = {
                'start': mars3d.event.start,
                'endItem': mars3d.event.endItem,
                'end': mars3d.event.end
            };
        },
        , function (hKrz0000, fqDb0102) {
            hKrz0000.exports = 'attribute\x20vec4\x20position;\x0d\x0aattribute\x20vec3\x20normal;\x0d\x0a\x0d\x0avarying\x20vec3\x20v_position;\x0d\x0avarying\x20vec3\x20v_positionWC;\x0d\x0avarying\x20vec3\x20v_positionEC;\x0d\x0avarying\x20vec3\x20v_normalEC;\x0d\x0a\x0d\x0avoid\x20main()\x0d\x0a{\x0d\x0a\x20\x20\x20\x20gl_Position\x20=\x20czm_modelViewProjection\x20*\x20position;\x0d\x0a\x20\x20\x20\x20v_position\x20=\x20vec3(position);\x0d\x0a\x20\x20\x20\x20v_positionWC\x20=\x20(czm_model\x20*\x20position).xyz;\x0d\x0a\x20\x20\x20\x20v_positionEC\x20=\x20(czm_modelView\x20*\x20position).xyz;\x0d\x0a\x20\x20\x20\x20v_normalEC\x20=\x20czm_normal\x20*\x20normal;\x0d\x0a}';
        }
        , function (ijBl0000, hqew0030) {
            ijBl0000.exports = '#ifdef\x20GL_OES_standard_derivatives\x0d\x0a\x20\x20\x20\x20#extension\x20GL_OES_standard_derivatives\x20:\x20enable\x0d\x0a#endif\x0d\x0a\x0d\x0auniform\x20bool\x20u_showIntersection;\x0d\x0auniform\x20bool\x20u_showThroughEllipsoid;\x0d\x0a\x0d\x0auniform\x20float\x20u_radius;\x0d\x0auniform\x20float\x20u_xHalfAngle;\x0d\x0auniform\x20float\x20u_yHalfAngle;\x0d\x0auniform\x20float\x20u_normalDirection;\x0d\x0auniform\x20float\x20u_type;\x0d\x0a\x0d\x0avarying\x20vec3\x20v_position;\x0d\x0avarying\x20vec3\x20v_positionWC;\x0d\x0avarying\x20vec3\x20v_positionEC;\x0d\x0avarying\x20vec3\x20v_normalEC;\x0d\x0a\x0d\x0avec4\x20getColor(float\x20sensorRadius,\x20vec3\x20pointEC)\x0d\x0a{\x0d\x0a\x20\x20\x20\x20czm_materialInput\x20materialInput;\x0d\x0a\x0d\x0a\x20\x20\x20\x20vec3\x20pointMC\x20=\x20(czm_inverseModelView\x20*\x20vec4(pointEC,\x201.0)).xyz;\x0d\x0a\x20\x20\x20\x20materialInput.st\x20=\x20sensor2dTextureCoordinates(sensorRadius,\x20pointMC);\x0d\x0a\x20\x20\x20\x20materialInput.str\x20=\x20pointMC\x20/\x20sensorRadius;\x0d\x0a\x0d\x0a\x20\x20\x20\x20vec3\x20positionToEyeEC\x20=\x20-v_positionEC;\x0d\x0a\x20\x20\x20\x20materialInput.positionToEyeEC\x20=\x20positionToEyeEC;\x0d\x0a\x0d\x0a\x20\x20\x20\x20vec3\x20normalEC\x20=\x20normalize(v_normalEC);\x0d\x0a\x20\x20\x20\x20materialInput.normalEC\x20=\x20u_normalDirection\x20*\x20normalEC;\x0d\x0a\x0d\x0a\x20\x20\x20\x20czm_material\x20material\x20=\x20czm_getMaterial(materialInput);\x0d\x0a\x20\x20\x20\x20//\x20czm_lightDirectionEC在cesium1.66开始加入的\x0d\x0a\x20\x20\x20\x20return\x20mix(czm_phong(normalize(positionToEyeEC),\x20material,\x20czm_lightDirectionEC),\x20vec4(material.diffuse,\x20material.alpha),\x200.4);\x0d\x0a\x0d\x0a}\x0d\x0a\x0d\x0abool\x20isOnBoundary(float\x20value,\x20float\x20epsilon)\x0d\x0a{\x0d\x0a\x20\x20\x20\x20float\x20width\x20=\x20getIntersectionWidth();\x0d\x0a\x20\x20\x20\x20float\x20tolerance\x20=\x20width\x20*\x20epsilon;\x0d\x0a\x0d\x0a#ifdef\x20GL_OES_standard_derivatives\x0d\x0a\x20\x20\x20\x20float\x20delta\x20=\x20max(abs(dFdx(value)),\x20abs(dFdy(value)));\x0d\x0a\x20\x20\x20\x20float\x20pixels\x20=\x20width\x20*\x20delta;\x0d\x0a\x20\x20\x20\x20float\x20temp\x20=\x20abs(value);\x0d\x0a\x20\x20\x20\x20//\x20There\x20are\x20a\x20couple\x20things\x20going\x20on\x20here.\x0d\x0a\x20\x20\x20\x20//\x20First\x20we\x20test\x20the\x20value\x20at\x20the\x20current\x20fragment\x20to\x20see\x20if\x20it\x20is\x20within\x20the\x20tolerance.\x0d\x0a\x20\x20\x20\x20//\x20We\x20also\x20want\x20to\x20check\x20if\x20the\x20value\x20of\x20an\x20adjacent\x20pixel\x20is\x20within\x20the\x20tolerance,\x0d\x0a\x20\x20\x20\x20//\x20but\x20we\x20don\x27t\x20want\x20to\x20admit\x20points\x20that\x20are\x20obviously\x20not\x20on\x20the\x20surface.\x0d\x0a\x20\x20\x20\x20//\x20For\x20example,\x20if\x20we\x20are\x20looking\x20for\x20\x22value\x22\x20to\x20be\x20close\x20to\x200,\x20but\x20value\x20is\x201\x20and\x20the\x20adjacent\x20value\x20is\x202,\x0d\x0a\x20\x20\x20\x20//\x20then\x20the\x20delta\x20would\x20be\x201\x20and\x20\x22temp\x20-\x20delta\x22\x20would\x20be\x20\x221\x20-\x201\x22\x20which\x20is\x20zero\x20even\x20though\x20neither\x20of\x0d\x0a\x20\x20\x20\x20//\x20the\x20points\x20is\x20close\x20to\x20zero.\x0d\x0a\x20\x20\x20\x20return\x20temp\x20<\x20tolerance\x20&&\x20temp\x20<\x20pixels\x20||\x20(delta\x20<\x2010.0\x20*\x20tolerance\x20&&\x20temp\x20-\x20delta\x20<\x20tolerance\x20&&\x20temp\x20<\x20pixels);\x0d\x0a#else\x0d\x0a\x20\x20\x20\x20return\x20abs(value)\x20<\x20tolerance;\x0d\x0a#endif\x0d\x0a}\x0d\x0a\x0d\x0avec4\x20shade(bool\x20isOnBoundary)\x0d\x0a{\x0d\x0a\x20\x20\x20\x20if\x20(u_showIntersection\x20&&\x20isOnBoundary)\x0d\x0a\x20\x20\x20\x20{\x0d\x0a\x20\x20\x20\x20\x20\x20\x20\x20return\x20getIntersectionColor();\x0d\x0a\x20\x20\x20\x20}\x0d\x0a\x20\x20\x20\x20if(u_type\x20==\x201.0){\x0d\x0a\x20\x20\x20\x20\x20\x20\x20\x20return\x20getLineColor();\x0d\x0a\x20\x20\x20\x20}\x0d\x0a\x20\x20\x20\x20return\x20getColor(u_radius,\x20v_positionEC);\x0d\x0a}\x0d\x0a\x0d\x0afloat\x20ellipsoidSurfaceFunction(vec3\x20point)\x0d\x0a{\x0d\x0a\x20\x20\x20\x20vec3\x20scaled\x20=\x20czm_ellipsoidInverseRadii\x20*\x20point;\x0d\x0a\x20\x20\x20\x20return\x20dot(scaled,\x20scaled)\x20-\x201.0;\x0d\x0a}\x0d\x0a\x0d\x0avoid\x20main()\x0d\x0a{\x0d\x0a\x20\x20\x20\x20vec3\x20sensorVertexWC\x20=\x20czm_model[3].xyz;\x20\x20\x20\x20\x20\x20//\x20(0.0,\x200.0,\x200.0)\x20in\x20model\x20coordinates\x0d\x0a\x20\x20\x20\x20vec3\x20sensorVertexEC\x20=\x20czm_modelView[3].xyz;\x20\x20//\x20(0.0,\x200.0,\x200.0)\x20in\x20model\x20coordinates\x0d\x0a\x0d\x0a\x20\x20\x20\x20//vec3\x20pixDir\x20=\x20normalize(v_position);\x0d\x0a\x20\x20\x20\x20float\x20positionX\x20=\x20v_position.x;\x0d\x0a\x20\x20\x20\x20float\x20positionY\x20=\x20v_position.y;\x0d\x0a\x20\x20\x20\x20float\x20positionZ\x20=\x20v_position.z;\x0d\x0a\x0d\x0a\x20\x20\x20\x20vec3\x20zDir\x20=\x20vec3(0.0,\x200.0,\x201.0);\x0d\x0a\x20\x20\x20\x20vec3\x20lineX\x20=\x20vec3(positionX,\x200\x20,positionZ);\x0d\x0a\x20\x20\x20\x20vec3\x20lineY\x20=\x20vec3(0,\x20positionY,\x20positionZ);\x0d\x0a\x20\x20\x20\x20float\x20resX\x20=\x20dot(normalize(lineX),\x20zDir);\x0d\x0a\x20\x20\x20\x20if(resX\x20<\x20cos(u_xHalfAngle)-0.00001){\x0d\x0a\x20\x20\x20\x20\x20\x20\x20\x20discard;\x0d\x0a\x20\x20\x20\x20}\x0d\x0a\x20\x20\x20\x20float\x20resY\x20=\x20dot(normalize(lineY),\x20zDir);\x0d\x0a\x20\x20\x20\x20if(resY\x20<\x20cos(u_yHalfAngle)-0.00001){\x0d\x0a\x20\x20\x20\x20\x20\x20\x20\x20discard;\x0d\x0a\x20\x20\x20\x20}\x0d\x0a\x0d\x0a\x0d\x0a\x20\x20\x20\x20float\x20ellipsoidValue\x20=\x20ellipsoidSurfaceFunction(v_positionWC);\x0d\x0a\x0d\x0a\x20\x20\x20\x20//\x20Occluded\x20by\x20the\x20ellipsoid?\x0d\x0a\x09if\x20(!u_showThroughEllipsoid)\x0d\x0a\x09{\x0d\x0a\x09\x20\x20\x20\x20//\x20Discard\x20if\x20in\x20the\x20ellipsoid\x0d\x0a\x09\x20\x20\x20\x20//\x20PERFORMANCE_IDEA:\x20A\x20coarse\x20check\x20for\x20ellipsoid\x20intersection\x20could\x20be\x20done\x20on\x20the\x20CPU\x20first.\x0d\x0a\x09\x20\x20\x20\x20if\x20(ellipsoidValue\x20<\x200.0)\x0d\x0a\x09\x20\x20\x20\x20{\x0d\x0a\x20\x20\x20\x20\x20\x20\x20\x20\x20\x20\x20\x20discard;\x0d\x0a\x09\x20\x20\x20\x20}\x0d\x0a\x0d\x0a\x09\x20\x20\x20\x20//\x20Discard\x20if\x20in\x20the\x20sensor\x27s\x20shadow\x0d\x0a\x09\x20\x20\x20\x20if\x20(inSensorShadow(sensorVertexWC,\x20v_positionWC))\x0d\x0a\x09\x20\x20\x20\x20{\x0d\x0a\x09\x20\x20\x20\x20\x20\x20\x20\x20discard;\x0d\x0a\x09\x20\x20\x20\x20}\x0d\x0a\x20\x20\x20\x20}\x0d\x0a\x0d\x0a\x20\x20\x20\x20//\x20Notes:\x20Each\x20surface\x20functions\x20should\x20have\x20an\x20associated\x20tolerance\x20based\x20on\x20the\x20floating\x20point\x20error.\x0d\x0a\x20\x20\x20\x20bool\x20isOnEllipsoid\x20=\x20isOnBoundary(ellipsoidValue,\x20czm_epsilon3);\x0d\x0a\x20\x20\x20\x20//isOnEllipsoid\x20=\x20false;\x0d\x0a\x20\x20\x20\x20//if((resX\x20>=\x200.8\x20&&\x20resX\x20<=\x200.81)||(resY\x20>=\x200.8\x20&&\x20resY\x20<=\x200.81)){\x0d\x0a\x20\x20\x20\x20/*if(false){\x0d\x0a\x20\x20\x20\x20\x20\x20\x20\x20gl_FragColor\x20=\x20vec4(1.0,0.0,0.0,1.0);\x0d\x0a\x20\x20\x20\x20}else{\x0d\x0a\x20\x20\x20\x20\x20\x20\x20\x20gl_FragColor\x20=\x20shade(isOnEllipsoid);\x0d\x0a\x20\x20\x20\x20}\x0d\x0a*/\x0d\x0a\x20\x20\x20\x20gl_FragColor\x20=\x20shade(isOnEllipsoid);\x0d\x0a\x0d\x0a}';
        }//200
        , function (nPWe0003, ftMz0101) {
            nPWe0003.exports = 'uniform\x20vec4\x20u_intersectionColor;\x0auniform\x20float\x20u_intersectionWidth;\x0auniform\x20vec4\x20u_lineColor;\x0a\x0abool\x20inSensorShadow(vec3\x20coneVertexWC,\x20vec3\x20pointWC)\x0a{\x0a\x20\x20\x20\x20//\x20Diagonal\x20matrix\x20from\x20the\x20unscaled\x20ellipsoid\x20space\x20to\x20the\x20scaled\x20space.\x20\x20\x20\x20\x0a\x20\x20\x20\x20vec3\x20D\x20=\x20czm_ellipsoidInverseRadii;\x0a\x0a\x20\x20\x20\x20//\x20Sensor\x20vertex\x20in\x20the\x20scaled\x20ellipsoid\x20space\x0a\x20\x20\x20\x20vec3\x20q\x20=\x20D\x20*\x20coneVertexWC;\x0a\x20\x20\x20\x20float\x20qMagnitudeSquared\x20=\x20dot(q,\x20q);\x0a\x20\x20\x20\x20float\x20test\x20=\x20qMagnitudeSquared\x20-\x201.0;\x0a\x20\x20\x20\x20\x0a\x20\x20\x20\x20//\x20Sensor\x20vertex\x20to\x20fragment\x20vector\x20in\x20the\x20ellipsoid\x27s\x20scaled\x20space\x0a\x20\x20\x20\x20vec3\x20temp\x20=\x20D\x20*\x20pointWC\x20-\x20q;\x0a\x20\x20\x20\x20float\x20d\x20=\x20dot(temp,\x20q);\x0a\x20\x20\x20\x20\x0a\x20\x20\x20\x20//\x20Behind\x20silhouette\x20plane\x20and\x20inside\x20silhouette\x20cone\x0a\x20\x20\x20\x20return\x20(d\x20<\x20-test)\x20&&\x20(d\x20/\x20length(temp)\x20<\x20-sqrt(test));\x0a}\x0a\x0a///////////////////////////////////////////////////////////////////////////////\x0a\x0avec4\x20getLineColor()\x0a{\x0a\x20\x20\x20\x20return\x20u_lineColor;\x0a}\x0a\x0avec4\x20getIntersectionColor()\x0a{\x0a\x20\x20\x20\x20return\x20u_intersectionColor;\x0a}\x0a\x0afloat\x20getIntersectionWidth()\x0a{\x0a\x20\x20\x20\x20return\x20u_intersectionWidth;\x0a}\x0a\x0avec2\x20sensor2dTextureCoordinates(float\x20sensorRadius,\x20vec3\x20pointMC)\x0a{\x0a\x20\x20\x20\x20//\x20(s,\x20t)\x20both\x20in\x20the\x20range\x20[0,\x201]\x0a\x20\x20\x20\x20float\x20t\x20=\x20pointMC.z\x20/\x20sensorRadius;\x0a\x20\x20\x20\x20float\x20s\x20=\x201.0\x20+\x20(atan(pointMC.y,\x20pointMC.x)\x20/\x20czm_twoPi);\x0a\x20\x20\x20\x20s\x20=\x20s\x20-\x20floor(s);\x0a\x20\x20\x20\x20\x0a\x20\x20\x20\x20return\x20vec2(s,\x20t);\x0a}\x0a';
        }
        , function (huXf0002, o4v30000) {
            huXf0002.exports = '#ifdef GL_OES_standard_derivatives \n    #extension GL_OES_standard_derivatives : enable \n#endif \n \nuniform bool u_showIntersection; \nuniform bool u_showThroughEllipsoid; \n \nuniform float u_radius; \nuniform float u_xHalfAngle; \nuniform float u_yHalfAngle; \nuniform float u_normalDirection; \nuniform vec4 u_color; \n \nvarying vec3 v_position; \nvarying vec3 v_positionWC; \nvarying vec3 v_positionEC; \nvarying vec3 v_normalEC; \n \nvec4 getColor(float sensorRadius, vec3 pointEC) \n{ \n    czm_materialInput materialInput; \n \n    vec3 pointMC = (czm_inverseModelView * vec4(pointEC, 1.0)).xyz; \n    materialInput.st = sensor2dTextureCoordinates(sensorRadius, pointMC); \n    materialInput.str = pointMC / sensorRadius; \n \n    vec3 positionToEyeEC = -v_positionEC; \n    materialInput.positionToEyeEC = positionToEyeEC; \n \n    vec3 normalEC = normalize(v_normalEC); \n    materialInput.normalEC = u_normalDirection * normalEC; \n \n    czm_material material = czm_getMaterial(materialInput); \n \n    material.diffuse = u_color.rgb; \n    material.alpha = u_color.a; \n    // czm_lightDirectionEC在cesium1.66开始加入的 \n    return mix(czm_phong(normalize(positionToEyeEC), material, czm_lightDirectionEC), vec4(material.diffuse, material.alpha), 0.4); \n \n} \n \nbool isOnBoundary(float value, float epsilon) \n{ \n    float width = getIntersectionWidth(); \n    float tolerance = width * epsilon; \n \n#ifdef GL_OES_standard_derivatives \n    float delta = max(abs(dFdx(value)), abs(dFdy(value))); \n    float pixels = width * delta; \n    float temp = abs(value); \n    // There are a couple things going on here. \n    // First we test the value at the current fragment to see if it is within the tolerance. \n    // We also want to check if the value of an adjacent pixel is within the tolerance, \n    // but we dont want to admit points that are obviously not on the surface. \n    // For example, if we are looking for "value" to be close to 0, but value is 1 and the adjacent value is 2, \n    // then the delta would be 1 and "temp - delta" would be "1 - 1" which is zero even though neither of \n    // the points is close to zero. \n    return temp < tolerance && temp < pixels || (delta < 10.0 * tolerance && temp - delta < tolerance && temp < pixels); \n#else \n    return abs(value) < tolerance; \n#endif \n} \n \nvec4 shade(bool isOnBoundary) \n{ \n    if (u_showIntersection && isOnBoundary) \n    { \n        return getIntersectionColor(); \n    } \n    return getColor(u_radius, v_positionEC); \n} \n \nfloat ellipsoidSurfaceFunction(vec3 point) \n{ \n    vec3 scaled = czm_ellipsoidInverseRadii * point; \n    return dot(scaled, scaled) - 1.0; \n} \n \nvoid main() \n{ \n    vec3 sensorVertexWC = czm_model[3].xyz;      // (0.0, 0.0, 0.0) in model coordinates \n    vec3 sensorVertexEC = czm_modelView[3].xyz;  // (0.0, 0.0, 0.0) in model coordinates \n \n    //vec3 pixDir = normalize(v_position); \n    float positionX = v_position.x; \n    float positionY = v_position.y; \n    float positionZ = v_position.z; \n \n    vec3 zDir = vec3(0.0, 0.0, 1.0); \n    vec3 lineX = vec3(positionX, 0 ,positionZ); \n    vec3 lineY = vec3(0, positionY, positionZ); \n    float resX = dot(normalize(lineX), zDir); \n    if(resX < cos(u_xHalfAngle) - 0.0001){ \n        discard; \n    } \n    float resY = dot(normalize(lineY), zDir); \n    if(resY < cos(u_yHalfAngle)- 0.0001){ \n        discard; \n    } \n \n \n    float ellipsoidValue = ellipsoidSurfaceFunction(v_positionWC); \n \n    // Occluded by the ellipsoid? \n	if (!u_showThroughEllipsoid) \n	{ \n	    // Discard if in the ellipsoid \n	    // PERFORMANCE_IDEA: A coarse check for ellipsoid intersection could be done on the CPU first. \n	    if (ellipsoidValue < 0.0) \n	    { \n            discard; \n	    } \n \n	    // Discard if in the sensors shadow \n	    if (inSensorShadow(sensorVertexWC, v_positionWC)) \n	    { \n	        discard; \n	    } \n    } \n \n    // Notes: Each surface functions should have an associated tolerance based on the floating point error. \n    bool isOnEllipsoid = isOnBoundary(ellipsoidValue, czm_epsilon3); \n    gl_FragColor = shade(isOnEllipsoid); \n \n}';
        }
        , function (geDV0000, fyDo0100, ev2b0100) {
            'use strict';
            Object.defineProperty(fyDo0100, '__esModule', {'value': !0})
                , fyDo0100.BaseFlyLine = void 0;
            var iqbp0021 = Object.assign || function (hLou0000) {
                for (var i = 0x1; i < arguments.length; i++) {
                    var iuch0021 = arguments[i];
                    for (var ivga0123 in iuch0021) Object['prototype'].hasOwnProperty.call(iuch0021, ivga0123) && (hLou0000[ivga0123] = iuch0021[ivga0123]);
                }
                return hLou0000;
            }, ffJG0100 = 'function' == typeof Symbol && 'symbol' == typeof Symbol.iterator ? function (egfY0110) {
                return typeof egfY0110;
            } : function (eqhp0100) {
                return eqhp0100 && 'function' == typeof Symbol && eqhp0100.constructor === Symbol && eqhp0100 !== Symbol.prototype ? 'symbol' : typeof eqhp0100;
            }, hJsx0010 = function () {
                function gheo0110(gb5f0200, fyre0000) {
                    for (var i = 0; i < fyre0000.length; i++) {
                        var iX9w0001 = fyre0000[i];
                        iX9w0001.enumerable = iX9w0001['enumerable'] || !1
                            , iX9w0001.configurable = !0
                            , 'value' in iX9w0001 && (iX9w0001.writable = !0)
                            , Object.defineProperty(gb5f0200, iX9w0001.key, iX9w0001);
                    }
                }

                return function (g6eN0000, iJLx0000, ehUv0101) {
                    return iJLx0000 && gheo0110(g6eN0000.prototype, iJLx0000), ehUv0101 && gheo0110(g6eN0000, ehUv0101), g6eN0000;
                };
            }();

            function fWkx0000(fcVy0301) {
                if (fcVy0301 && fcVy0301.__esModule) return fcVy0301;
                var gAtp0000 = {};
                if (null != fcVy0301) for (var pVtv0011 in fcVy0301) Object['prototype'].hasOwnProperty['call'](fcVy0301, pVtv0011) && (gAtp0000[pVtv0011] = fcVy0301[pVtv0011]);
                return gAtp0000.default = fcVy0301, gAtp0000;
            }

            fyDo0100.BaseFlyLine = function (superClass) {
                function eO9e0003(mfcf0023, fggg0002) {
                    !function (t8ix0000, evla0000) {
                        if (!(t8ix0000 instanceof evla0000)) throw new TypeError('Cannot call a class as a function');
                    }(this, eO9e0003);
                    var _0xd99dc = function (gfsi0301, i0oh0001) {
                        if (!gfsi0301) throw new ReferenceError('this hasnt been initialised - super() hasnt been called');
                        return !i0oh0001 || 'object' != typeof i0oh0001 && 'function' != typeof i0oh0001 ? gfsi0301 : i0oh0001;
                    }(this, (eO9e0003['__proto__'] || Object.getPrototypeOf(eO9e0003)).call(this, fggg0002));
                    if (fggg0002['onStep']) {
                        var jrps0011 = fggg0002.onStep;
                        delete fggg0002.onStep, _0xd99dc['on'](mars3d.eventType.endItem, function (iD8z0000) {
                            jrps0011(iD8z0000.index, iD8z0000.counts);
                        });
                    }
                    return (0x0, mars3d.util['isObject'])(fggg0002.shadow) && fggg0002.shadow.show && (fggg0002.shadow = [fggg0002.shadow]),
                        _0xd99dc.toGeoJSON = _0xd99dc.toJSON,
                        _0xd99dc.viewer = mfcf0023, _0xd99dc.options = fggg0002,
                        _0xd99dc['_mergeDefVal'](), _0xd99dc['id'] = fggg0002['id'] || 0x0,
                        _0xd99dc['name'] = fggg0002.name || '', _0xd99dc._popup = fggg0002.popup,
                        _0xd99dc._tooltip = fggg0002.tooltip, _0xd99dc['_fixedFrameTransform'] = Cesium['defaultValue'](fggg0002.fixedFrameTransform,
                        Cesium.Transforms.eastNorthUpToFixedFrame), _0xd99dc.positions = [],
                        _0xd99dc.times = [], _0xd99dc.init(), _0xd99dc;
                }

                return function (gLvy0010, izfs0101) {
                    if ('function' != typeof izfs0101 && null !== izfs0101) throw new TypeError('Super expression must either be null or a function, not ' + typeof izfs0101);
                    gLvy0010['prototype'] = Object.create(izfs0101 && izfs0101.prototype, {
                        'constructor': {
                            'value': gLvy0010,
                            'enumerable': !1,
                            'writable': !0,
                            'configurable': !0
                        }
                    }), izfs0101 && (Object.setPrototypeOf ? Object.setPrototypeOf(gLvy0010, izfs0101) : gLvy0010.__proto__ = izfs0101);
                }(eO9e0003, superClass), hJsx0010(eO9e0003, [{
                    'key': '_mergeDefVal', 'value': function () {
                        for (var hKih0010 in this['defConfig']) {
                            var iC060000 = this.defConfig[hKih0010];
                            if (this.options.hasOwnProperty(hKih0010) && 'object' === ffJG0100(this.options[hKih0010])) for (var ixmk0011 in iC060000) this.options[hKih0010].hasOwnProperty(ixmk0011) || (this.options[hKih0010][ixmk0011] = iC060000[ixmk0011]); else Cesium['defined'](this.options[hKih0010]) || (this.options[hKih0010] = iC060000);
                        }
                    }
                }, {
                    'key': 'init', 'value': function () {
                    }
                }, {
                    'key': '_createEntity', 'value': function () {
                        var gC7n0001 = this, iEey0010 = {
                            'name': this.name,
                            'position': new Cesium[('CallbackProperty')](function (gNa50020) {
                                return gC7n0001.position;
                            }, !1),
                            'orientation': this['velocityOrientation'],
                            'point': {
                                'show': !(this.options.model && this.options.model['show']),
                                'color': Cesium['Color'].fromCssColorString('#ffffff').withAlpha(0.01),
                                'pixelSize': 1
                            }
                        };
                        if (this.options.label && this.options.label.show && (this['options'].label['text'] = this.options.label.text || this.name,
                            iEey0010.label = mars3d.draw.attr.label.style2Entity(this.options.label, null, this)),
                        this['options'].billboard && this.options['billboard']['show'] && (iEey0010.billboard = mars3d.draw.attr.billboard.style2Entity(this['options'].billboard)),
                        this.options.point && this.options.point.show && (iEey0010.point = mars3d.draw.attr.point.style2Entity(this['options'].point)),
                        this['options'].model && this.options['model'].show && (iEey0010.model = mars3d.draw.attr.model.style2Entity(this['options'].model)),
                        this.options.path && this.options.path.show) {
                            var nibJ0030 = mars3d.draw.attr.polyline.style2Entity(this.options.path);
                            nibJ0030.isAll || (nibJ0030.leadTime = 0, nibJ0030.trailTime = 0xa * this.alltimes),
                                iEey0010.path = nibJ0030, iEey0010.position = this.property;
                        }
                        this['options']['circle'] && this.options.circle.show && (iEey0010['ellipse'] = mars3d.draw.attr['circle'].style2Entity(this.options.circle)), this.entity = this.viewer.entities['add'](iEey0010), this['entity'].eventTarget = this, this.popup && (this.entity.popup = this['popup']), this.tooltip && (this.entity['tooltip'] = this['tooltip']);
                    }
                }, {
                    'key': 'updateConfig', 'value': function (echW0100) {
                        return this.updateStyle(echW0100);
                    }
                }, {
                    'key': 'updateStyle', 'value': function (hfwl0101) {
                        if (this.options) for (var r9Eh0000 in hfwl0101) if ('object' === ffJG0100(hfwl0101[r9Eh0000]) && this.options[r9Eh0000])
                            for (var gjes0101 in hfwl0101[r9Eh0000]) this['options'][r9Eh0000][gjes0101] = hfwl0101[r9Eh0000][gjes0101];
                            else this.options[r9Eh0000] = hfwl0101[r9Eh0000];
                    }
                }, {
                    'key': 'updateAngle', 'value': function (fuRh0000, g1eH0020) {
                        if (fuRh0000) this.entity['orientation'] = this.velocityOrientation, this._heading = null, this._pitch = null, this._roll = null; else {
                            g1eH0020 = g1eH0020 || {};
                            var eyZT0100 = this.position, _0x524a2 = this.orientation;
                            if (!eyZT0100 || !_0x524a2) return null;
                            var hz4k0001 = (0x0, mars3d.matrix.getHeadingPitchRollByOrientation)(eyZT0100, _0x524a2, this.viewer.scene.globe.ellipsoid, this._fixedFrameTransform)['heading'],
                                hiqG0000 = Cesium.Math.toRadians(Number(g1eH0020.pitch || 0x0)),
                                gt2J0000 = Cesium.Math.toRadians(Number(g1eH0020.roll || 0x0));
                            this._heading = hz4k0001, this['_pitch'] = hiqG0000, this['_roll'] = gt2J0000, this.entity.orientation = Cesium['Transforms'].headingPitchRollQuaternion(eyZT0100, new Cesium['HeadingPitchRoll'](hz4k0001, hiqG0000, gt2J0000), this.viewer['scene']['globe'].ellipsoid, this._fixedFrameTransform);
                        }
                    }
                }, {
                    'key': 'getCurrIndex', 'value': function () {
                        var fkJg0101 = this['times']['length'] - 0x1;
                        if (fkJg0101 < 0x0) return -0x1;
                        Cesium.JulianDate.compare(this['viewer']['clock'].currentTime, this.times[0x0]) <= 0x0 && (this['_flyok_point_index'] = 0x0), (this._flyok_point_index < 0x0 || this._flyok_point_index >= fkJg0101) && (this['_flyok_point_index'] = 0x0);
                        for (var ifLh0100 = this._flyok_point_index; ifLh0100 <= fkJg0101; ifLh0100++) {
                            var fcsp0101 = this.times[ifLh0100];
                            if (Cesium.JulianDate['compare'](this.viewer.clock.currentTime, fcsp0101) <= 0x0) return ifLh0100 - 0x1;
                        }
                        for (var jxe10030 = 0x0; jxe10030 <= fkJg0101; jxe10030++) {
                            var ieuc0302 = this.times[jxe10030];
                            if (Cesium['JulianDate'].compare(this.viewer.clock.currentTime, ieuc0302) <= 0x0) return jxe10030 - 0x1;
                        }
                        return fkJg0101;
                    }
                }, {
                    'key': 'getModelMatrix', 'value': function () {
                        var jgqO0000 = new Cesium[('Matrix4')](), fZbx0030 = new Cesium[('Matrix3')](),
                            fanF0210 = this['position'];
                        if (Cesium.defined(fanF0210)) {
                            var ffam0131 = this['orientation'];
                            return Cesium.defined(ffam0131) ? Cesium.Matrix4.fromRotationTranslation(Cesium.Matrix3.fromQuaternion(ffam0131, fZbx0030), fanF0210, jgqO0000) : this._fixedFrameTransform(fanF0210, void 0x0, jgqO0000);
                        }
                    }
                }, {
                    'key': '_addArrShading', 'value': function () {
                        this.arrShowingEntity = [];
                        for (var f8nL0000 = 0x0, sFe30010 = this['options'].shadow['length']; f8nL0000 < sFe30010; f8nL0000++) {
                            var iANj0000 = this.options.shadow[f8nL0000];
                            iANj0000['show'] && this['addShading'](iANj0000);
                        }
                    }
                }, {
                    'key': '_updateArrShading', 'value': function (g8n00000) {
                        for (var e4T20000 = 0x0, tgXS0000 = this.options.shadow['length']; e4T20000 < tgXS0000; e4T20000++) {
                            var eiWe0102 = this.options['shadow'][e4T20000];
                            if (eiWe0102.show) {
                                var fesd0003 = void 0x0;
                                switch (eiWe0102.type) {
                                    case 'wall':
                                        (fesd0003 = this.positions.slice(0x0, this['_flyok_point_index'] + 0x1)).push(g8n00000), this.updateWallShading(fesd0003);
                                        break;
                                    case 'polyline':
                                        (fesd0003 = this.positions.slice(0x0, this._flyok_point_index + 0x1)).push(g8n00000), eiWe0102.maxDistance ? this._passed_positions = (0x0, mars3d.point.sliceByMaxDistance)(fesd0003, eiWe0102.maxDistance) : this._passed_positions = fesd0003;
                                        break;
                                    case 'polyline-going':
                                        fesd0003 = [g8n00000].concat(this.positions['slice'](this['_flyok_point_index'] + 0x1)), this._going_positions = fesd0003;
                                }
                            }
                        }
                    }
                }, {
                    'key': 'addShading', 'value': function (iyWk0100) {
                        var flMr0100;
                        switch (iyWk0100.type) {
                            case 'wall':
                                flMr0100 = this.addWallShading(iyWk0100);
                                break;
                            case 'cylinder':
                                flMr0100 = this.addCylinderShading(iyWk0100);
                                break;
                            case 'circle':
                                flMr0100 = this.addCircleShading(iyWk0100);
                                break;
                            case 'polyline':
                            case 'polyline-going':
                                flMr0100 = this.addPolylineShading(iyWk0100);
                                break;
                            default:
                                mars3d.log.warn('存在未标识type的无效shadow配置', iyWk0100);
                        }
                        flMr0100 && (flMr0100['data'] = iyWk0100, this.arrShowingEntity['push'](flMr0100));
                    }
                }, {
                    'key': 'removeShading', 'value': function (js8u0000) {
                        if (null == js8u0000) {
                            if (0x0 == this.arrShowingEntity.length) return;
                            var iNDj0000 = this.arrShowingEntity.length - 0x1;
                            this.viewer.entities.remove(this.arrShowingEntity[iNDj0000]), this['arrShowingEntity'].splice(iNDj0000, 0x1);
                        } else {
                            if ((0x0, mars3d.util.isString)(js8u0000)) {
                                for (var fqJB0000 = 0x0, eeti0210 = this['arrShowingEntity'].length; fqJB0000 < eeti0210; fqJB0000++) if (this.arrShowingEntity[fqJB0000].data.type == js8u0000) {
                                    this.viewer.entities['remove'](this['arrShowingEntity'][fqJB0000]), this.arrShowingEntity.splice(fqJB0000, 0x1);
                                    break;
                                }
                                return;
                            }
                            if (this.viewer.entities.remove(js8u0000), this.arrShowingEntity) for (var e2eg0011 = 0x0, iqdp0100 = this.arrShowingEntity.length; e2eg0011 < iqdp0100; e2eg0011++) if (this.arrShowingEntity[e2eg0011] == js8u0000) {
                                this.arrShowingEntity.splice(e2eg0011, 0x1);
                                break;
                            }
                        }
                    }
                }, {
                    'key': 'addWallShading', 'value': function (ghI60100) {
                        this._wall_positions = [], this['_wall_minimumHeights'] = [], this['_wall_maximumHeights'] = [], ghI60100 = iqbp0021({
                            'color': '#00ff00',
                            'outline': !0x1,
                            'opacity': 0.3
                        }, ghI60100);
                        var ffow0211 = this, eDaa0030 = mars3d.draw.attr.wall.style2Entity(ghI60100);
                        return eDaa0030['minimumHeights'] = new Cesium[('CallbackProperty')](function (igdb0103) {
                            return ffow0211._wall_minimumHeights;
                        }, !0x1), eDaa0030.maximumHeights = new Cesium[('CallbackProperty')](function (fyt70010) {
                            return ffow0211._wall_maximumHeights;
                        }, !0x1), eDaa0030['positions'] = new Cesium['CallbackProperty'](function (fxlq0001) {
                            return ffow0211['_wall_positions'];
                        }, !0x1), this.viewer.entities.add({'wall': eDaa0030});
                    }
                }, {
                    'key': 'updateWallShading', 'value': function (hfMt0000) {
                        for (var eznf0102 = [], euoj0100 = [], k1uw0001 = [], g1li0011 = 0x0; g1li0011 < hfMt0000.length; g1li0011++) {
                            var ewof0010 = hfMt0000[g1li0011].clone();
                            if (ewof0010) {
                                eznf0102.push(ewof0010);
                                var eEg50010 = Cesium.Cartographic.fromCartesian(ewof0010);
                                euoj0100.push(0x0), k1uw0001.push(eEg50010['height']);
                            }
                        }
                        this._wall_positions = eznf0102, this._wall_minimumHeights = euoj0100, this['_wall_maximumHeights'] = k1uw0001;
                    }
                }, {
                    'key': 'addCylinderShading', 'value': function (fkcV0030) {
                        var gYsi0010 = 0x64, fMmf0000 = 0x64, kjah0100 = this;
                        fkcV0030 = iqbp0021({'color': '#00ff00', 'outline': !0x1, 'opacity': 0.3}, fkcV0030);
                        var g9n40000 = mars3d.draw.attr.wall.style2Entity(fkcV0030);
                        return g9n40000['length'] = new Cesium[('CallbackProperty')](function (hUwr0010) {
                            return fMmf0000;
                        }, !0x1), g9n40000.topRadius = 0x0, g9n40000.bottomRadius = new Cesium[('CallbackProperty')](function (ePxf0013) {
                            return gYsi0010;
                        }, !0x1), g9n40000.numberOfVerticalLines = 0x0, this['viewer'].entities.add({
                            'position': new Cesium['CallbackProperty'](function (hp8j0100) {
                                var iVPi0000 = kjah0100.position;
                                if (!iVPi0000) return null;
                                var iNja0010 = Cesium.Cartographic.fromCartesian(iVPi0000),
                                    f6cW0010 = Cesium.Cartesian3['fromRadians'](iNja0010.longitude, iNja0010.latitude, iNja0010.height / 0x2);
                                return fMmf0000 = iNja0010['height'], gYsi0010 = 0.3 * fMmf0000, f6cW0010;
                            }, !0x1), 'cylinder': g9n40000
                        });
                    }
                }, {
                    'key': 'addCircleShading', 'value': function (icdv0021) {
                        var gthw0010 = mars3d.draw.attr.circle.style2Entity(icdv0021);
                        return this.viewer['entities'].add({'position': this.property, 'ellipse': gthw0010});
                    }
                }, {
                    'key': 'addPolylineShading', 'value': function (gpk00110) {
                        var fs3f0000 = this, ignz0210 = mars3d.draw.attr.polyline['style2Entity'](gpk00110);
                        return ignz0210['positions'] = new Cesium['CallbackProperty'](function (gaeg0002) {
                            return 'polyline-going' == gpk00110.type ? fs3f0000._going_positions : fs3f0000._passed_positions;
                        }, !1), this['viewer'].entities.add({'polyline': ignz0210});
                    }
                }, {
                    'key': 'centerAt', 'value': function (l8kk0011) {
                        l8kk0011 = l8kk0011 || {};
                        var f3m30000 = (0, mars3d.point.getRectangle)(this.positions);
                        return this.viewer.camera.flyTo({
                            'duration': Cesium.defaultValue(l8kk0011.duration, 0),
                            'destination': f3m30000
                        }), f3m30000;
                    }
                }, {
                    'key': 'flyTo', 'value': function (sqPR0000) {
                        var ejb50020 = this;
                        sqPR0000 = sqPR0000 || {};
                        var fnJq0000 = this.viewer, iywd0001 = this['position'];
                        iywd0001 && (this.viewer.scene.mode == Cesium.SceneMode.SCENE3D ? (this.viewer.clock['shouldAnimate'] = !1
                            , setTimeout(function () {
                            var hj9o0001 = Cesium.Math.toDegrees(ejb50020.hdr.heading) + Cesium.defaultValue(sqPR0000['heading'], 0);
                            fnJq0000.mars.centerPoint(iywd0001, {
                                'radius': Cesium.defaultValue(sqPR0000.radius, Cesium['defaultValue'](sqPR0000.distance, 0x1f4)),
                                'heading': hj9o0001,
                                'pitch': Cesium.defaultValue(sqPR0000.pitch, -0x32),
                                'duration': 0.1,
                                'complete': function () {
                                    fnJq0000.clock.shouldAnimate = !0;
                                }
                            });
                        }, 0x1f4)) : this['entity'] && this['viewer'].flyTo(this['entity']));
                    }
                }, {
                    'key': 'pause', 'value': function () {
                        this.viewer.clock.shouldAnimate = false;
                    }
                }, {
                    'key': 'proceed', 'value': function () {
                        this.viewer.clock.shouldAnimate = true;
                    }
                }, {
                    'key': 'toCZML', 'value': function () {
                        var ploypoints = [];
                        for (var statrTime = this.times[0].toString()
                                 , endTime = this.times[this.times.length - 1].toString()
                                 , jllf0101 = [], i = 0; i < this.positions.length; i++) {
                            var hiec0012 = (0, mars3d.point.formatPosition)(this.positions[i]),
                                gkXz0001 = 0 == i ? 0 : Cesium.JulianDate.secondsDifference(this.times[i], this.times[i - 1]);
                            if (i > 0) gkXz0001 += jllf0101[(i - 1) * 4];
                            jllf0101.push(gkXz0001)
                                , jllf0101.push(hiec0012['x']),
                                ploypoints.push(hiec0012.x)
                                , jllf0101.push(hiec0012['y']),
                                ploypoints.push(hiec0012.y)
                                , jllf0101['push'](hiec0012['z']),
                                ploypoints.push(hiec0012.z);
                        }

                        var pathCzml = {
                            'id': this.name,
                            'description': this.options.remark,
                            'availability': statrTime + '/' + endTime,
                            'orientation': {'velocityReference': '#position'},
                            'position': {'epoch': statrTime, 'cartographicDegrees': jllf0101}
                        };
                        var modelCzml = {
                            'id': this.name + 'model',
                            'description': this.options.remark,
                            'availability': endTime + '/2218-07-12T14:00:00Z'
                        };
                        this.options.interpolation && (pathCzml.position.interpolationAlgorithm = 'LAGRANGE'
                            , pathCzml['position'].interpolationDegree = this.options['interpolationDegree'] || 0x2)
                            , this.options.label.show && (pathCzml.label = {
                            'show': !0,
                            'outlineWidth': 2,
                            'text': this['name'],
                            'font': '12pt 微软雅黑 Console',
                            heightReference: "CLAMP_TO_GROUND",
                            'outlineColor': {'rgba': [0, 0, 0, 0xff]},
                            'horizontalOrigin': 'LEFT',
                            'fillColor': {'rgba': [0xd5, 0xff, 0x0, 0xff]}
                        }), this.options.path.show && (
                            // pathCzml.path = {
                            //     'show': !0,
                            //     'material': {'solidColor': {'color': {'rgba': [0xff, 0, 0, 0xff]}}},
                            //     'width': 3,
                            //     heightReference: "CLAMP_TO_GROUND",
                            //     'resolution': 1,
                            //     'leadTime': 0,
                            //     'trailTime': this.alltimes
                            // },

                            pathCzml.polyline = {
                                show: !0,
                                clampToGround: true,
                                width: this.options.path.width,
                                positions: {'cartographicDegrees': ploypoints},
                                material: {solidColor: {"color": {"rgba": new Cesium.Color.fromCssColorString(this.options.path.color || '#00ff00').withAlpha(this.options.path.opacity || 0.6).toBytes()}}}
                            }
                        ), this.options.model.show && (pathCzml.model = this.options.model);
                        modelCzml.model = this.options.model;
                        modelCzml.position = {'cartographicDegrees': [ploypoints[ploypoints.length - 3], ploypoints[ploypoints.length - 2], ploypoints[ploypoints.length - 1]]}
                        return [pathCzml, modelCzml];
                    }
                }, {
                    'key': 'destroy', 'value': function () {
                        if (this.viewer['trackedEntity'] == this.entity && (this.viewer.trackedEntity = void 0x0), this.entity && (this.viewer['entities'].remove(this['entity']), delete this.entity), this['arrShowingEntity']) {
                            for (var hhTf0101 = 0x0, fcQj0301 = this.arrShowingEntity.length; hhTf0101 < fcQj0301; hhTf0101++) this.viewer['entities']['remove'](this.arrShowingEntity[hhTf0101]);
                            delete this.arrShowingEntity;
                        }
                        this.stop(), function gfpA0210(js6k0001, pqca0014, gKhJ0010) {
                            null === js6k0001 && (js6k0001 = Function['prototype']);
                            var ebZS0000 = Object.getOwnPropertyDescriptor(js6k0001, pqca0014);
                            if (void 0x0 === ebZS0000) {
                                var ieis0211 = Object.getPrototypeOf(js6k0001);
                                return null === ieis0211 ? void 0x0 : gfpA0210(ieis0211, pqca0014, gKhJ0010);
                            }
                            if ('value' in ebZS0000) return ebZS0000.value;
                            var jxeS0030 = ebZS0000.get;
                            return void 0x0 !== jxeS0030 ? jxeS0030['call'](gKhJ0010) : void 0x0;
                        }(eO9e0003.prototype.__proto__ || Object.getPrototypeOf(eO9e0003['prototype']), 'destroy', this).call(this);
                    }
                }, {
                    'key': 'data', 'get': function () {
                        return this.options;
                    }, 'set': function (fhh30100) {
                        for (var joUq0001 in fhh30100) this.options[joUq0001] = fhh30100[joUq0001];
                    }
                }, {
                    'key': 'defConfig', 'get': function () {
                        return {
                            'model': {'show': !0x1, 'scale': 0x1, 'minimumPixelSize': 0x32},
                            'label': {
                                'show': !0x1,
                                'color': '#ffffff',
                                'opacity': 0x1,
                                'font_family': '楷体',
                                'font_size': 0x14,
                                'border': !0x0,
                                'border_color': '#000000',
                                'border_width': 0x3,
                                'background': !0x1,
                                'hasPixelOffset': !0x0,
                                'pixelOffsetX': 0x1e,
                                'pixelOffsetY': -0x1e,
                                'scaleByDistance': !0x0,
                                'scaleByDistance_far': 0x989680,
                                'scaleByDistance_farValue': 0.4,
                                'scaleByDistance_near': 0x1388,
                                'scaleByDistance_nearValue': 0x1
                            },
                            'path': {
                                'show': !0x1,
                                'lineType': 'solid',
                                'color': '#3388ff',
                                'opacity': 0.5,
                                'width': 0x1,
                                'outline': !0x1,
                                'outlineColor': '#ffffff',
                                'outlineWidth': 0x2
                            },
                            'camera': {'type': '', 'followedX': 0x32, 'followedZ': 0xa},
                            'showGroundHeight': !0x1
                        };
                    }
                }, {
                    'key': 'popup', 'get': function () {
                        return this['_popup'];
                    }, 'set': function (elob0002) {
                        this._popup = elob0002, this.entity && (this['entity'].popup = elob0002);
                    }
                }, {
                    'key': 'tooltip', 'get': function () {
                        return this._tooltip;
                    }, 'set': function (gd8e0002) {
                        this._tooltip = gd8e0002, this.entity && (this.entity.tooltip = gd8e0002);
                    }
                }, {
                    'key': 'indexForFlyOK', 'get': function () {
                        return this._flyok_point_index;
                    }
                }, {
                    'key': 'position', 'get': function () {
                        var jMvc0011 = Cesium.Property['getValueOrUndefined'](this['property'], this['viewer'].clock['currentTime'], new Cesium[('Cartesian3')]());
                        return !jMvc0011 && this['_lastItem'] && (jMvc0011 = this['_lastItem'].position), jMvc0011;
                    }
                }, {
                    'key': 'lastItem', 'get': function () {
                        return this['_lastItem'];
                    }, 'set': function (fDaV0030) {
                        this._lastItem = fDaV0030;
                    }
                }, {
                    'key': 'orientation', 'get': function () {
                        return Cesium.Property['getValueOrUndefined'](this.velocityOrientation, this.viewer['clock'].currentTime, new Cesium[('Quaternion')]());
                    }
                }, {
                    'key': 'hdr', 'get': function () {
                        var kOfh0030 = this.position, lqeq0101 = this.orientation;
                        return kOfh0030 && lqeq0101 ? (0x0, mars3d.matrix['getHeadingPitchRollByOrientation'])(kOfh0030, lqeq0101, this['viewer'].scene.globe.ellipsoid, this._fixedFrameTransform) : null;
                    }
                }, {
                    'key': 'matrix', 'get': function () {
                        return this.getModelMatrix();
                    }
                }, {
                    'key': 'heading', 'get': function () {
                        if (!Cesium.defined(this._heading)) {
                            var huj90100 = this.hdr;
                            return huj90100 ? huj90100.heading : null;
                        }
                        return this._heading;
                    }
                }, {
                    'key': 'pitch', 'get': function () {
                        if (!Cesium.defined(this._pitch)) {
                            var gPOx0000 = this['hdr'];
                            return gPOx0000 ? gPOx0000['pitch'] : null;
                        }
                        return this._pitch;
                    }, 'set': function (hLth0010) {
                        this._pitch = hLth0010, this.updateAngle(!0x1, {'pitch': this._pitch, 'roll': this['_roll']});
                    }
                }, {
                    'key': 'roll', 'get': function () {
                        if (!Cesium['defined'](this._roll)) {
                            var g4Qt0000 = this['hdr'];
                            return g4Qt0000 ? g4Qt0000.roll : null;
                        }
                        return this['_roll'];
                    }, 'set': function (ifxd0012) {
                        this._roll = ifxd0012, this.updateAngle(!0x1, {'pitch': this._pitch, 'roll': this._roll});
                    }
                }, {
                    'key': 'groundPosition', 'get': function () {
                        return (0x0, mars3d.matrix.getRayEarthPositionByMatrix)(this.matrix, !0x0, this.viewer.scene['globe'].ellipsoid);
                    }
                }]), eO9e0003;
            }(mars3d.MarsClass);
        }]);
});
