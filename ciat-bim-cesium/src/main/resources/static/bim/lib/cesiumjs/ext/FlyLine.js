FlyLine = function (fgl10010) {
    function ikfq0121() {
        return hjUa0103(this, ikfq0121), fkqg0102(this, (ikfq0121.__proto__ || Object.getPrototypeOf(ikfq0121)).apply(this, arguments));
    }

    return function (gbJE0200, igqd0102) {
        if ('function' != typeof igqd0102 && null !== igqd0102) throw new TypeError('Super expression must either be null or a function, not ' + typeof igqd0102);
        gbJE0200['prototype'] = Object.create(igqd0102 && igqd0102.prototype, {
            'constructor': {
                'value': gbJE0200,
                'enumerable': !0x1,
                'writable': !0x0,
                'configurable': !0x0
            }
        }), igqd0102 && (Object.setPrototypeOf ? Object.setPrototypeOf(gbJE0200, igqd0102) : gbJE0200.__proto__ = igqd0102);
    }(ikfq0121, fgl10010), hakg0400(ikfq0121, [{
        'key': 'init', 'value': function () {
            this._isStart = !0x1, this.options.points && this.createPath(this.options.points, this.options);
        }
    }, {
        'key': 'createPath', 'value': function (gcab0333, suxn0100) {
            if (!gcab0333 || gcab0333['length'] < 0x2) eh0u0100.warn('路线无坐标数据，无法漫游！', gcab0333); else {
                this.points = gcab0333, suxn0100 = suxn0100 || {};
                var iocp0010, i4aB0000, hglX0000 = iIla0014['defaultValue'](suxn0100.offsetHeight, 0x0);
                iocp0010 = suxn0100.startTime ? iIla0014['JulianDate'].fromDate(new Date(suxn0100['startTime'])) : this['viewer'].clock.currentTime;
                var emlC0110 = suxn0100['speed'], icLd0000 = !(0x0, hcgz0320.isNumber)(emlC0110);
                if (0x2 == gcab0333.length) {
                    var haXm0201 = [(gcab0333[0x0][0x0] + gcab0333[0x1][0x0]) / 0x2, (gcab0333[0x0][0x1] + gcab0333[0x1][0x1]) / 0x2, gcab0333[0x0][0x2]];
                    gcab0333.splice(0x1, 0x0, haXm0201), emlC0110 && icLd0000 && emlC0110.splice(0x1, 0x0, emlC0110[0x0]);
                }
                var ruyt0100 = new iIla0014[('SampledPositionProperty')]();
                this['positions'] = [], this['times'] = [];
                for (var efc10130, efht0111 = [], gfXb0103 = 0x0, _0xecb6f = 0x0, eqgc0002 = {}, giqK0110 = {}, fWae0021 = 0x0, inB30000 = gcab0333['length']; fWae0021 < inB30000; fWae0021++) {
                    var fscd0012, eTXd0003 = gcab0333[fWae0021],
                        fG5b0002 = iIla0014['Cartesian3'].fromDegrees(eTXd0003[0x0], eTXd0003[0x1], (eTXd0003[0x2] || 0x0) + hglX0000);
                    if (fG5b0002.lonlat = eTXd0003, 0x0 == fWae0021) fscd0012 = iIla0014.JulianDate.addSeconds(iocp0010, gfXb0103, new iIla0014[('JulianDate')]()), fG5b0002.time = fscd0012, fG5b0002['second'] = gfXb0103, ruyt0100.addSample(fscd0012, fG5b0002); else {
                        var iFcw0000 = icLd0000 ? emlC0110 ? emlC0110[fWae0021 - 0x1] : 0x64 : emlC0110 || 0x64;
                        efht0111.push(iFcw0000), iFcw0000 /= 3.6;
                        var iR600000 = iIla0014.Cartesian3.distance(fG5b0002, efc10130),
                            g0fp0010 = iR600000 / iFcw0000;
                        g0fp0010 < 0.01 && (g0fp0010 = 0.01), gfXb0103 += g0fp0010, _0xecb6f += iR600000, fscd0012 = iIla0014.JulianDate.addSeconds(iocp0010, gfXb0103, new iIla0014['JulianDate']()), fG5b0002['time'] = fscd0012, fG5b0002.second = gfXb0103, ruyt0100['addSample'](fscd0012, fG5b0002), suxn0100.pauseTime && ('function' == typeof suxn0100.pauseTime ? gfXb0103 += suxn0100.pauseTime(fWae0021, fG5b0002) : gfXb0103 += suxn0100.pauseTime, fscd0012 = iIla0014.JulianDate.addSeconds(iocp0010, gfXb0103, new iIla0014[('JulianDate')]()), ruyt0100['addSample'](fscd0012, (0x0, hDte0010.getOnLinePointByLen)(efc10130, fG5b0002, 0.01, !0x0)));
                    }
                    efc10130 = fG5b0002, this['positions']['push'](fG5b0002), this['times'].push(fscd0012), eqgc0002[fWae0021] = _0xecb6f, giqK0110[fWae0021] = gfXb0103;
                }
                this.arrSpeed = efht0111, this.lastItem = {
                    'position': this.positions[this.positions.length - 0x1],
                    'time': this.times[this['times'].length - 0x1]
                }, i4aB0000 = iIla0014.JulianDate.addSeconds(iocp0010, gfXb0103, new iIla0014['JulianDate']()), this.alltimes = gfXb0103, this.alllen = _0xecb6f, this['stepLen'] = eqgc0002, this.stepTime = giqK0110, this.startTime = iocp0010, this.stopTime = i4aB0000, this.property = ruyt0100, this.velocityOrientation = new iIla0014['VelocityOrientationProperty'](this.property), suxn0100.interpolation && this.property.setInterpolationOptions({
                    'interpolationDegree': suxn0100.interpolationDegree || 0x2,
                    'interpolationAlgorithm': iIla0014.LagrangePolynomialApproximation
                });
            }
        }
    }, {
        'key': 'start', 'value': function (h0Xh0001) {
            iIla0014.defined(this['positions']) && 0x0 != this.positions['length'] ? (this._isStart && this.stop(), this['_isStart'] = !0x0, this['_createEntity'](), iIla0014.defined(this.options.multiplier) && (this['_bak_multiplier'] = this.viewer.clock.multiplier, this['viewer'].clock.multiplier = this['options'].multiplier), this.viewer.clock['shouldAnimate'] = !0x0, this.viewer.clock.currentTime = this['startTime'].clone(), (this.options.clockRange || this.options.clockLoop) && (this._bak_clockRange = this.viewer.clock['clockRange'], this._bak_startTime = this.viewer.clock.startTime, this._bak_stopTime = this.viewer.clock.stopTime, this.viewer['clock'].clockRange = iIla0014.defaultValue(this.options.clockRange, iIla0014.ClockRange.LOOP_STOP), this['viewer'].clock['startTime'] = this['startTime']['clone'](), this.viewer.clock.stopTime = this.stopTime.clone()), this['viewer'].timeline && this['viewer'].timeline.zoomTo(this['startTime'], this['stopTime']), this.options.shadow && this.options.shadow['length'] > 0x0 && this._addArrShading(), this._flyok_point_index = 0x0, this.fire(iebn0301.eventType['endItem'], {
                'index': this['_flyok_point_index'],
                'counts': this.positions.length
            }), this.fire(iebn0301.eventType['start']), this.viewer.scene['preRender'].addEventListener(this['preRender_eventHandler'], this)) : eh0u0100['warn']('没有坐标数据，飞行路线启动失败', this['positions']);
        }
    }, {
        'key': 'stop', 'value': function () {
            if (this.viewer.trackedEntity = void 0x0, this.viewer.scene.preRender['removeEventListener'](this.preRender_eventHandler, this), this.entity && (this.viewer['entities'].remove(this.entity), delete this.entity), this.arrShowingEntity) {
                for (var gZwl0001 = 0x0, jdmy0001 = this.arrShowingEntity.length; gZwl0001 < jdmy0001; gZwl0001++) this.viewer.entities['remove'](this['arrShowingEntity'][gZwl0001]);
                delete this['arrShowingEntity'];
            }
            this._bak_startTime && (this.viewer['clock'].startTime = this['_bak_startTime'], delete this['_bak_startTime']), this._bak_stopTime && (this.viewer['clock'].stopTime = this['_bak_stopTime'], delete this._bak_stopTime), this._bak_multiplier && (this.viewer.clock.multiplier = this._bak_multiplier, delete this['_bak_multiplier']), this['_bak_clockRange'] && (this.viewer.clock.clockRange = this['_bak_clockRange'], delete this['_bak_clockRange']), this._flyok_point_index = 0x0, this._isStart = !0x1, this['fire'](iebn0301.eventType['end']);
        }
    }, {
        'key': 'preRender_eventHandler', 'value': function (gawh0301) {
            if (this._isStart && null != this.entity) {
                if (this.viewer.clock['shouldAnimate'] && iIla0014.JulianDate['greaterThanOrEquals'](this.viewer.clock.currentTime, this['stopTime'])) return this._flyok_point_index = this.positions['length'] - 0x1, this['_onStepTempBS'] || (this.fire(iebn0301.eventType.endItem, {
                    'index': this['_flyok_point_index'],
                    'counts': this.positions.length
                }), this.fire(iebn0301.eventType.end), this._onStepTempBS = !0x0), void ((this['options']['autoStop'] || this.viewer.clock.clockRange == iIla0014.ClockRange.UNBOUNDED) && this['stop']());
                var hovI0010 = this['position'];
                if (iIla0014.defined(hovI0010)) {
                    switch (this.options.camera.type) {
                        default:
                            null != this.viewer.trackedEntity && (this.viewer.trackedEntity = void 0x0, this.flyTo(this.options.camera));
                            break;
                        case 'gs':
                            this.viewer.trackedEntity != this['entity'] && (this.viewer.trackedEntity = this.entity, this['flyTo'](this.options.camera));
                            break;
                        case 'dy':
                            this.viewer.trackedEntity != this.entity && (this.viewer.trackedEntity = this.entity);
                            var hgVg0000 = this.getModelMatrix(), eFLb0002 = this.options.camera['followedX'],
                                hJye0010 = this.options.camera['followedZ'];
                            this['viewer'].scene.camera.lookAtTransform(hgVg0000, new iIla0014[('Cartesian3')](-eFLb0002, 0x0, hJye0010));
                            break;
                        case 'sd':
                            this.viewer['trackedEntity'] != this.entity && (this.viewer.trackedEntity = this.entity), this.viewer.scene.camera.lookAtTransform(this.getModelMatrix(), new iIla0014['Cartesian3'](-0x1, 0x0, this.options.camera.followedZ));
                    }
                    this['viewer'].clock.shouldAnimate && this.realTime(hovI0010);
                }
            }
        }
    }, {
        'key': 'realTime', 'value': function (fzkl0101) {
            var hgcr0011 = iIla0014['JulianDate']['secondsDifference'](this['viewer']['clock'].currentTime, this['startTime']),
                graf0133 = (0x0, hMSi0000.formatPosition)(fzkl0101), hoWS0100 = this.getCurrIndex(),
                eue20120 = this.positions.length, jw6q0001 = this.stepLen[hoWS0100],
                pvEk0001 = this.positions[hoWS0100];
            iIla0014['defined'](pvEk0001) && (jw6q0001 += iIla0014['Cartesian3'].distance(fzkl0101, pvEk0001)), jw6q0001 >= this.alllen && (hoWS0100 = eue20120 - 0x1, jw6q0001 = this['alllen']), hoWS0100 != this._flyok_point_index && this.fire(iebn0301['eventType'].endItem, {
                'index': hoWS0100,
                'counts': eue20120
            }), this._flyok_point_index = hoWS0100, this.timeinfo = {
                'time': hgcr0011,
                'len': jw6q0001,
                'x': graf0133['x'],
                'y': graf0133['y'],
                'z': graf0133['z']
            }, this.options.shadow && this.options['shadow']['length'] > 0x0 && this['_updateArrShading'](fzkl0101);
            var gglk0211 = iIla0014.Cartographic['fromCartesian'](fzkl0101),
                hRjh0000 = this.viewer['scene'].globe.getHeight(gglk0211);
            if (null != hRjh0000 && hRjh0000 > 0x0 && (this.timeinfo['hbgd'] = hRjh0000, this.timeinfo.ldgd = graf0133['z'] - hRjh0000), this.options.showGroundHeight) {
                var jxcs0021 = this;
                (0x0, thdK0000['computeSurfaceLine'])({
                    'viewer': jxcs0021.viewer,
                    'positions': [fzkl0101, fzkl0101],
                    'callback': function (fF0W0000, jq5X0000) {
                        if (null != fF0W0000 && 0x0 != fF0W0000['length'] && !jq5X0000) {
                            var eUuk0010 = (0x0, hMSi0000['formatPosition'])(fF0W0000[0x0])['z'],
                                rpwF0010 = graf0133['z'] - eUuk0010;
                            if (jxcs0021.timeinfo.hbgd = eUuk0010, jxcs0021['timeinfo'].ldgd = rpwF0010, jxcs0021['entity'].label) {
                                var f3cX0000 = (0x0, hcgz0320['formatLength'])(jxcs0021.timeinfo['z']),
                                    goad0101 = (0x0, hcgz0320['formatLength'])(jxcs0021.timeinfo.ldgd);
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
            (0x0, thdK0000.computeStepSurfaceLine)({
                'viewer': this.viewer,
                'positions': this.positions,
                'has3dtiles': ipzq0110.has3dtiles,
                'splitNum': ipzq0110.splitNum,
                'offset': ipzq0110['offset'],
                'endItem': function (hGge0001, gpbb0033, hoen0020) {
                    var ggEO0000 = papf0003[hoen0020];
                    if (gpbb0033) g7u90000.push(hlak0020[hoen0020]), gzLd0103.push(ggEO0000); else for (var eaUY0000 = 0x0; eaUY0000 < hGge0001.length; eaUY0000++) {
                        var ifjL0000 = hGge0001[eaUY0000],
                            fcve0303 = iIla0014['Cartographic'].fromCartesian(ifjL0000);
                        g7u90000.push([iIla0014.Math.toDegrees(fcve0303.longitude), iIla0014['Math'].toDegrees(fcve0303.latitude), fcve0303.height]), gzLd0103.push(ggEO0000);
                    }
                    for (var gbmO0210 = hlak0020[hoen0020][0x2] || 0x0, gwm30100 = ((hlak0020[hoen0020 + 0x1][0x2] || 0x0) - gbmO0210) / hGge0001['length'], rgT70100 = 0x0; rgT70100 < hGge0001.length; rgT70100++) {
                        0x0 != rgT70100 && (mpzu0001 += iIla0014['Cartesian3'].distance(hGge0001[rgT70100], hGge0001[rgT70100 - 0x1])), gsze0111.push(Number(mpzu0001.toFixed(0x1)));
                        var eJkc0013 = (0x0, hMSi0000.formatPosition)(hGge0001[rgT70100]);
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
            (0x0, thdK0000.computeStepSurfaceLine)({
                'viewer': this.viewer,
                'positions': this.positions,
                'has3dtiles': iabE0330.has3dtiles,
                'splitNum': iabE0330.splitNum,
                'offset': iabE0330.offset,
                'endItem': function (_0x5ace2, jdbs0030, f31J0000) {
                    for (var itWa0102 = gcsk0301[f31J0000][0x2] || 0x0, hxQr0100 = ((gcsk0301[f31J0000 + 0x1][0x2] || 0x0) - itWa0102) / _0x5ace2.length, h1im0010 = 0x0; h1im0010 < _0x5ace2['length']; h1im0010++) {
                        0x0 != h1im0010 && (iNx70010 += iIla0014.Cartesian3.distance(_0x5ace2[h1im0010], _0x5ace2[h1im0010 - 0x1])), h6St0000.push(Number(iNx70010['toFixed'](0x1)));
                        var edXM0200 = (0x0, hMSi0000.formatPosition)(_0x5ace2[h1im0010]);
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
}
