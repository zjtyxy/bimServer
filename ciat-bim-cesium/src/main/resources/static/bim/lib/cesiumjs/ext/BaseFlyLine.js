fyDo0100.BaseFlyLine = function (iFCe0001) {
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
            delete fggg0002.onStep, _0xd99dc['on'](eQ9g0000.eventType.endItem, function (iD8z0000) {
                jrps0011(iD8z0000.index, iD8z0000.counts);
            });
        }
        return (0x0, fvew0130['isObject'])(fggg0002.shadow) && fggg0002.shadow.show && (fggg0002.shadow = [fggg0002.shadow])
            , _0xd99dc.toGeoJSON = _0xd99dc.toJSON,
              _0xd99dc.viewer = mfcf0023, _0xd99dc.options = fggg0002
            , _0xd99dc['_mergeDefVal'](), _0xd99dc['id'] = fggg0002['id'] || 0x0, _0xd99dc['name'] = fggg0002.name || '', _0xd99dc._popup = fggg0002.popup
            , _0xd99dc._tooltip = fggg0002.tooltip, _0xd99dc['_fixedFrameTransform'] = fc5n0300['defaultValue'](fggg0002.fixedFrameTransform, fc5n0300.Transforms.eastNorthUpToFixedFrame), _0xd99dc.positions = []
            , _0xd99dc.times = [], _0xd99dc.init(), _0xd99dc;
    }

    return function (gLvy0010, izfs0101) {
        if ('function' != typeof izfs0101 && null !== izfs0101) throw new TypeError('Super expression must either be null or a function, not ' + typeof izfs0101);
        gLvy0010['prototype'] = Object.create(izfs0101 && izfs0101.prototype, {
            'constructor': {
                'value': gLvy0010,
                'enumerable': !0x1,
                'writable': !0x0,
                'configurable': !0x0
            }
        }), izfs0101 && (Object.setPrototypeOf ? Object.setPrototypeOf(gLvy0010, izfs0101) : gLvy0010.__proto__ = izfs0101);
    }(eO9e0003, iFCe0001), hJsx0010(eO9e0003, [{
        'key': '_mergeDefVal', 'value': function () {
            for (var hKih0010 in this['defConfig']) {
                var iC060000 = this.defConfig[hKih0010];
                if (this.options.hasOwnProperty(hKih0010) && 'object' === ffJG0100(this.options[hKih0010])) for (var ixmk0011 in iC060000) this.options[hKih0010].hasOwnProperty(ixmk0011) || (this.options[hKih0010][ixmk0011] = iC060000[ixmk0011]); else fc5n0300['defined'](this.options[hKih0010]) || (this.options[hKih0010] = iC060000);
            }
        }
    }, {
        'key': 'init', 'value': function () {
        }
    }, {
        'key': '_createEntity', 'value': function () {
            var gC7n0001 = this, iEey0010 = {
                'name': this.name,
                'position': new fc5n0300[('CallbackProperty')](function (gNa50020) {
                    return gC7n0001.position;
                }, !0x1),
                'orientation': this['velocityOrientation'],
                'point': {
                    'show': !(this.options.model && this.options.model['show']),
                    'color': fc5n0300['Color'].fromCssColorString('#ffffff').withAlpha(0.01),
                    'pixelSize': 0x1
                }
            };
            if (this.options.label && this.options.label.show && (this['options'].label['text'] = this.options.label.text || this.name, iEey0010.label = idWf0203.label.style2Entity(this.options.label, null, this)), this['options'].billboard && this.options['billboard']['show'] && (iEey0010.billboard = idWf0203.billboard.style2Entity(this['options'].billboard)), this.options.point && this.options.point.show && (iEey0010.point = idWf0203.point.style2Entity(this['options'].point)), this['options'].model && this.options['model'].show && (iEey0010.model = idWf0203.model.style2Entity(this['options'].model)), this.options.path && this.options.path.show) {
                var nibJ0030 = idWf0203.polyline['style2Entity'](this.options['path']);
                nibJ0030.isAll || (nibJ0030['leadTime'] = 0x0, nibJ0030['trailTime'] = 0xa * this.alltimes), iEey0010.path = nibJ0030, iEey0010['position'] = this.property;
            }
            this['options']['circle'] && this.options.circle.show && (iEey0010['ellipse'] = idWf0203['circle'].style2Entity(this.options.circle)), this.entity = this.viewer.entities['add'](iEey0010), this['entity'].eventTarget = this, this.popup && (this.entity.popup = this['popup']), this.tooltip && (this.entity['tooltip'] = this['tooltip']);
        }
    }, {
        'key': 'updateConfig', 'value': function (echW0100) {
            return this.updateStyle(echW0100);
        }
    }, {
        'key': 'updateStyle', 'value': function (hfwl0101) {
            if (this.options) for (var r9Eh0000 in hfwl0101) if ('object' === ffJG0100(hfwl0101[r9Eh0000]) && this.options[r9Eh0000]) for (var gjes0101 in hfwl0101[r9Eh0000]) this['options'][r9Eh0000][gjes0101] = hfwl0101[r9Eh0000][gjes0101]; else this.options[r9Eh0000] = hfwl0101[r9Eh0000];
        }
    }, {
        'key': 'updateAngle', 'value': function (fuRh0000, g1eH0020) {
            if (fuRh0000) this.entity['orientation'] = this.velocityOrientation, this._heading = null, this._pitch = null, this._roll = null; else {
                g1eH0020 = g1eH0020 || {};
                var eyZT0100 = this.position, _0x524a2 = this.orientation;
                if (!eyZT0100 || !_0x524a2) return null;
                var hz4k0001 = (0x0, jNax0020.getHeadingPitchRollByOrientation)(eyZT0100, _0x524a2, this.viewer.scene.globe.ellipsoid, this._fixedFrameTransform)['heading'],
                    hiqG0000 = fc5n0300.Math.toRadians(Number(g1eH0020.pitch || 0x0)),
                    gt2J0000 = fc5n0300.Math.toRadians(Number(g1eH0020.roll || 0x0));
                this._heading = hz4k0001, this['_pitch'] = hiqG0000, this['_roll'] = gt2J0000, this.entity.orientation = fc5n0300['Transforms'].headingPitchRollQuaternion(eyZT0100, new fc5n0300['HeadingPitchRoll'](hz4k0001, hiqG0000, gt2J0000), this.viewer['scene']['globe'].ellipsoid, this._fixedFrameTransform);
            }
        }
    }, {
        'key': 'getCurrIndex', 'value': function () {
            var fkJg0101 = this.times.length - 1;
            if (fkJg0101 < 0) return -1;
            fc5n0300.JulianDate.compare(this['viewer']['clock'].currentTime, this.times[0x0]) <= 0x0 && (this['_flyok_point_index'] = 0x0), (this._flyok_point_index < 0x0 || this._flyok_point_index >= fkJg0101) && (this['_flyok_point_index'] = 0x0);
            for (var ifLh0100 = this._flyok_point_index; ifLh0100 <= fkJg0101; ifLh0100++) {
                var fcsp0101 = this.times[ifLh0100];
                if (fc5n0300.JulianDate['compare'](this.viewer.clock.currentTime, fcsp0101) <= 0x0) return ifLh0100 - 0x1;
            }
            for (var jxe10030 = 0x0; jxe10030 <= fkJg0101; jxe10030++) {
                var ieuc0302 = this.times[jxe10030];
                if (fc5n0300['JulianDate'].compare(this.viewer.clock.currentTime, ieuc0302) <= 0x0) return jxe10030 - 0x1;
            }
            return fkJg0101;
        }
    }, {
        'key': 'getModelMatrix', 'value': function () {
            var jgqO0000 = new fc5n0300[('Matrix4')](), fZbx0030 = new fc5n0300[('Matrix3')](),
                fanF0210 = this['position'];
            if (fc5n0300.defined(fanF0210)) {
                var ffam0131 = this['orientation'];
                return fc5n0300.defined(ffam0131) ? fc5n0300.Matrix4.fromRotationTranslation(fc5n0300.Matrix3.fromQuaternion(ffam0131, fZbx0030), fanF0210, jgqO0000) : this._fixedFrameTransform(fanF0210, void 0x0, jgqO0000);
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
                            (fesd0003 = this.positions.slice(0x0, this._flyok_point_index + 0x1)).push(g8n00000), eiWe0102.maxDistance ? this._passed_positions = (0x0, ioOp0101.sliceByMaxDistance)(fesd0003, eiWe0102.maxDistance) : this._passed_positions = fesd0003;
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
            switch (iyWk0100['type']) {
                case 'wall':
                    flMr0100 = this['addWallShading'](iyWk0100);
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
                    es8j0001.warn('存在未标识type的无效shadow配置', iyWk0100);
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
                if ((0x0, fvew0130.isString)(js8u0000)) {
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
            var ffow0211 = this, eDaa0030 = idWf0203.wall.style2Entity(ghI60100);
            return eDaa0030['minimumHeights'] = new fc5n0300[('CallbackProperty')](function (igdb0103) {
                return ffow0211._wall_minimumHeights;
            }, !0x1), eDaa0030.maximumHeights = new fc5n0300[('CallbackProperty')](function (fyt70010) {
                return ffow0211._wall_maximumHeights;
            }, !0x1), eDaa0030['positions'] = new fc5n0300['CallbackProperty'](function (fxlq0001) {
                return ffow0211['_wall_positions'];
            }, !0x1), this.viewer.entities.add({'wall': eDaa0030});
        }
    }, {
        'key': 'updateWallShading', 'value': function (hfMt0000) {
            for (var eznf0102 = [], euoj0100 = [], k1uw0001 = [], g1li0011 = 0x0; g1li0011 < hfMt0000.length; g1li0011++) {
                var ewof0010 = hfMt0000[g1li0011].clone();
                if (ewof0010) {
                    eznf0102.push(ewof0010);
                    var eEg50010 = fc5n0300.Cartographic.fromCartesian(ewof0010);
                    euoj0100.push(0x0), k1uw0001.push(eEg50010['height']);
                }
            }
            this._wall_positions = eznf0102, this._wall_minimumHeights = euoj0100, this['_wall_maximumHeights'] = k1uw0001;
        }
    }, {
        'key': 'addCylinderShading', 'value': function (fkcV0030) {
            var gYsi0010 = 0x64, fMmf0000 = 0x64, kjah0100 = this;
            fkcV0030 = iqbp0021({'color': '#00ff00', 'outline': !0x1, 'opacity': 0.3}, fkcV0030);
            var g9n40000 = idWf0203.wall.style2Entity(fkcV0030);
            return g9n40000['length'] = new fc5n0300[('CallbackProperty')](function (hUwr0010) {
                return fMmf0000;
            }, !0x1), g9n40000.topRadius = 0x0, g9n40000.bottomRadius = new fc5n0300[('CallbackProperty')](function (ePxf0013) {
                return gYsi0010;
            }, !0x1), g9n40000.numberOfVerticalLines = 0x0, this['viewer'].entities.add({
                'position': new fc5n0300['CallbackProperty'](function (hp8j0100) {
                    var iVPi0000 = kjah0100.position;
                    if (!iVPi0000) return null;
                    var iNja0010 = fc5n0300.Cartographic.fromCartesian(iVPi0000),
                        f6cW0010 = fc5n0300.Cartesian3['fromRadians'](iNja0010.longitude, iNja0010.latitude, iNja0010.height / 0x2);
                    return fMmf0000 = iNja0010['height'], gYsi0010 = 0.3 * fMmf0000, f6cW0010;
                }, !0x1), 'cylinder': g9n40000
            });
        }
    }, {
        'key': 'addCircleShading', 'value': function (icdv0021) {
            var gthw0010 = idWf0203.circle.style2Entity(icdv0021);
            return this.viewer['entities'].add({'position': this.property, 'ellipse': gthw0010});
        }
    }, {
        'key': 'addPolylineShading', 'value': function (gpk00110) {
            var fs3f0000 = this, ignz0210 = idWf0203.polyline['style2Entity'](gpk00110);
            return ignz0210['positions'] = new fc5n0300['CallbackProperty'](function (gaeg0002) {
                return 'polyline-going' == gpk00110.type ? fs3f0000._going_positions : fs3f0000._passed_positions;
            }, !0x1), this['viewer'].entities.add({'polyline': ignz0210});
        }
    }, {
        'key': 'centerAt', 'value': function (l8kk0011) {
            l8kk0011 = l8kk0011 || {};
            var f3m30000 = (0x0, ioOp0101.getRectangle)(this.positions);
            return this.viewer.camera.flyTo({
                'duration': fc5n0300.defaultValue(l8kk0011['duration'], 0x0),
                'destination': f3m30000
            }), f3m30000;
        }
    }, {
        'key': 'flyTo', 'value': function (sqPR0000) {
            var ejb50020 = this;
            sqPR0000 = sqPR0000 || {};
            var fnJq0000 = this['viewer'], iywd0001 = this['position'];
            iywd0001 && (this.viewer.scene.mode == fc5n0300.SceneMode.SCENE3D ? (this.viewer.clock['shouldAnimate'] = !0x1, setTimeout(function () {
                var hj9o0001 = fc5n0300.Math.toDegrees(ejb50020.hdr.heading) + fc5n0300['defaultValue'](sqPR0000['heading'], 0x0);
                fnJq0000.mars.centerPoint(iywd0001, {
                    'radius': fc5n0300.defaultValue(sqPR0000.radius, fc5n0300['defaultValue'](sqPR0000.distance, 0x1f4)),
                    'heading': hj9o0001,
                    'pitch': fc5n0300.defaultValue(sqPR0000.pitch, -0x32),
                    'duration': 0.1,
                    'complete': function () {
                        fnJq0000.clock.shouldAnimate = !0x0;
                    }
                });
            }, 0x1f4)) : this['entity'] && this['viewer'].flyTo(this['entity']));
        }
    }, {
        'key': 'pause', 'value': function () {
            this['viewer'].clock.shouldAnimate = !0x1;
        }
    }, {
        'key': 'proceed', 'value': function () {
            this['viewer'].clock.shouldAnimate = !0x0;
        }
    }, {
        'key': 'toCZML', 'value': function () {
            for (var ekzR0000 = this.times[0].toString(),
                     fcdO0320 = this.times[this.times.length - 1].toString(),
                     jllf0101 = [], eau40310 = 0, ikoq0010 = this.positions.length; eau40310 < ikoq0010; eau40310++) {
                var hiec0012 = (0, ioOp0101.formatPosition)(this.positions[eau40310]),
                    gkXz0001 = 0 == eau40310 ? 0 : fc5n0300.JulianDate.secondsDifference(this.times[eau40310], this.times[eau40310 - 1]);
                if(eau40310 > 0) gkXz0001 += jllf0101[(eau40310 - 1)*4];
                jllf0101['push'](gkXz0001), jllf0101.push(hiec0012['x']), jllf0101.push(hiec0012['y']), jllf0101['push'](hiec0012['z']);
            }
            var eQZc0003 = {
                'id': this.name,
                'description': this.options.remark,
                'availability': ekzR0000 + '/' + fcdO0320,
                'orientation': {'velocityReference': '#position'},
                'position': {'epoch': ekzR0000, 'cartographicDegrees': jllf0101}
            };
            return this.options.interpolation && (eQZc0003.position.interpolationAlgorithm = 'LAGRANGE', eQZc0003['position'].interpolationDegree = this.options['interpolationDegree'] || 0x2)
                , this.options.label['show'] && (eQZc0003.label = {
                'show': !0,
                'outlineWidth': 2,
                'text': this['name'],
                'font': '12pt 微软雅黑 Console',
                'outlineColor': {'rgba': [0, 0, 0, 0xff]},
                'horizontalOrigin': 'LEFT',
                'fillColor': {'rgba': [0xd5, 0xff, 0, 0xff]}
            }), this.options.path.show && (eQZc0003['path'] = {
                'show': !0,
                'material': {'solidColor': {'color': {'rgba': [0xff, 0x0, 0x0, 0xff]}}},
                'width': 5,
                'resolution': 1,
                'leadTime': 0,
                'trailTime': this.alltimes
            }), this.options.model.show && (eQZc0003.model = this.options.model), [{
                'version': '1.0',
                'id': 'document',
                'clock': {'interval': ekzR0000 + '/' + fcdO0320, 'currentTime': ekzR0000, 'multiplier': 0x1}
            }, eQZc0003];
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
                'model': {'show': !1, 'scale': 1, 'minimumPixelSize': 0x32},
                'label': {
                    'show': !1,
                    'color': '#ffffff',
                    'opacity': 1,
                    'font_family': '楷体',
                    'font_size': 0x14,
                    'border': !0,
                    'border_color': '#000000',
                    'border_width': 3,
                    'background': !1,
                    'hasPixelOffset': !0,
                    'pixelOffsetX': 0x1e,
                    'pixelOffsetY': -0x1e,
                    'scaleByDistance': !0,
                    'scaleByDistance_far': 0x989680,
                    'scaleByDistance_farValue': 0.4,
                    'scaleByDistance_near': 0x1388,
                    'scaleByDistance_nearValue': 1
                },
                'path': {
                    'show': !1,
                    'lineType': 'solid',
                    'color': '#3388ff',
                    'opacity': 0.5,
                    'width': 1,
                    'outline': !1,
                    'outlineColor': '#ffffff',
                    'outlineWidth': 2
                },
                'camera': {'type': '', 'followedX': 0x32, 'followedZ': 0xa},
                'showGroundHeight': !1
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
            var jMvc0011 = fc5n0300.Property['getValueOrUndefined'](this['property'], this['viewer'].clock['currentTime'], new fc5n0300[('Cartesian3')]());
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
            return fc5n0300.Property['getValueOrUndefined'](this.velocityOrientation, this.viewer['clock'].currentTime, new fc5n0300[('Quaternion')]());
        }
    }, {
        'key': 'hdr', 'get': function () {
            var kOfh0030 = this.position, lqeq0101 = this.orientation;
            return kOfh0030 && lqeq0101 ? (0x0, jNax0020['getHeadingPitchRollByOrientation'])(kOfh0030, lqeq0101, this['viewer'].scene.globe.ellipsoid, this._fixedFrameTransform) : null;
        }
    }, {
        'key': 'matrix', 'get': function () {
            return this.getModelMatrix();
        }
    }, {
        'key': 'heading', 'get': function () {
            if (!fc5n0300.defined(this._heading)) {
                var huj90100 = this.hdr;
                return huj90100 ? huj90100.heading : null;
            }
            return this._heading;
        }
    }, {
        'key': 'pitch', 'get': function () {
            if (!fc5n0300.defined(this._pitch)) {
                var gPOx0000 = this['hdr'];
                return gPOx0000 ? gPOx0000['pitch'] : null;
            }
            return this._pitch;
        }, 'set': function (hLth0010) {
            this._pitch = hLth0010, this.updateAngle(!0x1, {'pitch': this._pitch, 'roll': this['_roll']});
        }
    }, {
        'key': 'roll', 'get': function () {
            if (!fc5n0300['defined'](this._roll)) {
                var g4Qt0000 = this['hdr'];
                return g4Qt0000 ? g4Qt0000.roll : null;
            }
            return this['_roll'];
        }, 'set': function (ifxd0012) {
            this._roll = ifxd0012, this.updateAngle(!0x1, {'pitch': this._pitch, 'roll': this._roll});
        }
    }, {
        'key': 'groundPosition', 'get': function () {
            return (0x0, jNax0020.getRayEarthPositionByMatrix)(this.matrix, !0x0, this.viewer.scene['globe'].ellipsoid);
        }
    }]), eO9e0003;
}(eQ9g0000.MarsClass);
