var _0x57ff4e = function (_0x300e74) {
    _0x51dbd2()(_0x4b5fde, _0x300e74);
    var _0x2fb75f = _0x3ef988(_0x4b5fde);

    function _0x4b5fde() {
        return _0x2ede7c()(this, _0x4b5fde), _0x2fb75f['apply'](this, arguments);
    }

    return _0x4530b7()(_0x4b5fde, [{
        'key': 'currIndex', 'get': function () {
            return this['_flyok_point_index'];
        }
    }, {
        'key': 'isStart', 'get': function () {
            return this['_isStart'];
        }
    }, {
        'key': 'info', 'get': function () {
            return this['_timeinfo'];
        }
    }, {
        'key': '_mountedHook', 'value': function () {
            this['_isStart'] = !1, this.options.positions ? this.createPath(this.options.positions,
                this.options) : this.options.points && this.createPath(this.options.points, this.options);
        }
    }, {
        'key': '_removedHook', 'value': function () {
            this.stop(), _0x3a396d()(_0x1a7031()(_0x4b5fde['prototype']), '_removedHook', this).call(this);
        }
    }, {
        'key': 'createPath', 'value': function (_0xd99c9d) {
            var _0x57e770 = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : {};
            if (!_0xd99c9d || _0xd99c9d.length < 2) Object(_0x3d0eae.logError)('路线无坐标数据，无法漫游！', _0xd99c9d); else {
                this.points = _0x401d8f.toPoints(_0xd99c9d);
                var _0x34607a, _0x450723, _0x465f92 = _0x324641.defaultValue(_0x57e770['offsetHeight'], 0);
                if (_0x57e770.startTime) _0x34607a = Object(_0xbd445a['isString'])(_0x57e770.startTime) ? -1 == _0x57e770.startTime['indexOf']('Z') ? _0x324641.JulianDate.fromDate(new Date(_0x57e770.startTime)) : _0x324641.JulianDate['fromIso8601'](_0x57e770.startTime) : _0x57e770.startTime;
                else {
                    if (_0x57e770.timeField) {
                        var _0x58d24b = this.points[0][_0x57e770.timeField];
                        _0x34607a = _0x324641.JulianDate.fromDate(new Date(_0x58d24b));
                    } else _0x34607a = this['_map'].clock.currentTime;
                }
                var _0x17c8f7 = _0x57e770.speed, _0x27c67c = !Object(_0xbd445a['isNumber'])(_0x17c8f7);
                if (0x2 == this.points.length) {
                    var _0x38e785 = this.points[0], _0x36b172 = this.points[1],
                        _0x23e4f6 = new _0x5e7fa7['a']((_0x38e785['lng'] + _0x36b172['lng']) / 2, (_0x38e785['lat'] + _0x36b172['lat']) /2,
                            (_0x38e785['alt'] + _0x36b172['alt']) / 2);
                    this.points.splice(1, 0, _0x23e4f6), _0x17c8f7 && _0x27c67c && _0x17c8f7.splice(1, 0, _0x17c8f7[0]);
                }
                var _0x152058 = new _0x324641[('SampledPositionProperty')]();
                _0x152058['forwardExtrapolationType'] = _0x324641['ExtrapolationType']['HOLD'], this.positions = [], this['times'] = [];
                for (var _0x105066, _0x44cfb8, _0x48a205 = 0x64, _0x6212e3 = [], _0x1798a3 = 0, _0xf88642 = 0, _0x60fb2c = {}, _0x5bf8f0 = {}, _0x6f9fa9 = 0, _0x5cd9f4 = this.points.length; _0x6f9fa9 < _0x5cd9f4; _0x6f9fa9++) {
                    var _0xbff3eb = this.points[_0x6f9fa9],
                        _0x46f76c = _0x324641.Cartesian3['fromDegrees'](_0xbff3eb['lng'], _0xbff3eb['lat'], _0xbff3eb['alt'] + _0x465f92);
                    if (_0x46f76c['point'] = _0xbff3eb, 0 == _0x6f9fa9) _0x44cfb8 = _0x34607a['clone'](), _0x46f76c['time'] = _0x44cfb8,
                        _0x46f76c['second'] = _0x1798a3, _0x152058['addSample'](_0x44cfb8, _0x46f76c); else {
                        var _0x16ad52 = _0x324641.Cartesian3['distance'](_0x46f76c, _0x105066);
                        if (0 == _0x16ad52) continue;
                        var _0x9f010f = void 0;
                        if (_0x57e770.timeField) {
                            var _0x294923 = _0xbff3eb[_0x57e770['timeField']],
                                _0x3ae25c = _0x324641.JulianDate.fromDate(new Date(_0x294923));
                            _0x9f010f = _0x324641.JulianDate['secondsDifference'](_0x3ae25c, _0x44cfb8), _0x44cfb8 = _0x3ae25c,
                                _0x6212e3.push(3.6 * _0x16ad52 / _0x9f010f), _0x1798a3 += _0x9f010f;
                        } else {
                            var _0x2ceac2 = _0x27c67c ? _0x17c8f7 ? _0x17c8f7[_0x6f9fa9 - 1] : _0x48a205 : _0x17c8f7 || _0x48a205;
                            _0x6212e3.push(_0x2ceac2), (_0x9f010f = _0x16ad52 / (_0x2ceac2 /= 3.6)) < 0.01 && (_0x9f010f = 0.01),
                                _0x1798a3 += _0x9f010f, _0x44cfb8 = _0x324641.JulianDate['addSeconds'](_0x34607a, _0x1798a3, new _0x324641[('JulianDate')]());
                        }
                        _0xf88642 += _0x16ad52, _0x46f76c['time'] = _0x44cfb8, _0x46f76c['second'] = _0x1798a3, _0x152058['addSample'](_0x44cfb8, _0x46f76c), _0x57e770['pauseTime'] && ('function' == typeof _0x57e770['pauseTime'] ? _0x1798a3 += _0x57e770['pauseTime'](_0x6f9fa9, _0x46f76c) : _0x1798a3 += _0x57e770['pauseTime'], _0x44cfb8 = _0x324641.JulianDate['addSeconds'](_0x34607a, _0x1798a3, new _0x324641[('JulianDate')]()), _0x152058['addSample'](_0x44cfb8, Object(_0x3800e9['getOnLinePointByLen'])(_0x105066, _0x46f76c, 0.01, !0x0)));
                    }
                    _0x105066 = _0x46f76c, this.positions.push(_0x46f76c), this['times'].push(_0x44cfb8), _0x60fb2c[_0x6f9fa9] = _0xf88642, _0x5bf8f0[_0x6f9fa9] = _0x1798a3;
                }
                this['arrSpeed'] = _0x6212e3, this['lastItem'] = {
                    'position': this.positions[this.positions.length - 1],
                    'time': this['times'][this['times'].length - 1]
                }, _0x450723 = _0x324641.JulianDate['addSeconds'](_0x34607a, _0x1798a3, new _0x324641.JulianDate()), this['alltimes'] = _0x1798a3, this['alllen'] = _0xf88642, this['stepLen'] = _0x60fb2c, this['stepTime'] = _0x5bf8f0, this.startTime = _0x34607a, this['stopTime'] = _0x450723, this['property'] = _0x152058, this['velocityOrientation'] = new _0x324641[('VelocityOrientationProperty')](this['property']), _0x57e770['interpolation'] && this['property']['setInterpolationOptions']({
                    'interpolationDegree': _0x57e770['interpolationDegree'] || 2,
                    'interpolationAlgorithm': _0x324641['LagrangePolynomialApproximation']
                }), _0x57e770['showStop'] && this['_createStaticModel']();
            }
        }
    }, {
        'key': 'start', 'value': function () {
            var _0x1df6a3, _0x46c4d4 = this;
            _0x324641['defined'](this.positions) && 0 != this.positions.length ? this['_map'] && (this['_isStart'] && this['stop'](), this['_isStart'] = !0x0, this['_createEntity'](), null !== (_0x1df6a3 = this.options['model']) && void 0x0 !== _0x1df6a3 && _0x1df6a3['show'] && _0x324641['ExpandByMars'] ? (this['_map']['clock']['shouldAnimate'] = !0x1, this['_map']['clock']['currentTime'] = this.startTime['clone'](), this['_entity']['readyPromise'] = function (_0x47a5b3, _0x4004b1) {
                _0x46c4d4['_start']();
            }) : this['_start']()) : Object(_0x3d0eae.logError)('没有坐标数据，飞行路线启动失败', this.positions);
        }
    }, {
        'key': '_start', 'value': function () {
            _0x324641['defined'](this.options['multiplier']) && (this['_bak_multiplier'] = this['_map']['clock']['multiplier'], this['_map']['clock']['multiplier'] = this.options['multiplier']), this['_map']['clock']['shouldAnimate'] = !0x0, this['_map']['clock']['currentTime'] = this.startTime['clone'](), (this.options['clockRange'] || this.options['clockLoop']) && (this['_bak_clockRange'] = this['_map']['clock']['clockRange'], this['_bak_startTime'] = this['_map']['clock'].startTime, this['_bak_stopTime'] = this['_map']['clock']['stopTime'], this['_map']['clock']['clockRange'] = _0x324641['defaultValue'](this.options['clockRange'], _0x324641['ClockRange']['LOOP_STOP']), this['_map']['clock'].startTime = this.startTime['clone'](), this['_map']['clock']['stopTime'] = this['stopTime']['clone']()), this['_map']['viewer']['timeline'] && this['_map']['viewer']['timeline']['zoomTo'](this.startTime, this['stopTime']), this.options['shadow'] && this.options['shadow'].length > 0x0 && this['_addArrShading'](), this['_flyok_point_index'] = 0x0, this['fire'](_0x128102['endItem'], {
                'index': this['_flyok_point_index'],
                'counts': this.positions.length
            }), this['fire'](_0x128102['start']), this['_map']['on'](_0x128102['preRender'], this['_onPreRenderHandler'], this);
        }
    }, {
        'key': 'stop', 'value': function () {
            if (this['_isStart'] && this['_map']) {
                if (this['_map'].trackedEntity = void 0, this['_map']['off'](_0x128102['preRender'], this['_onPreRenderHandler'], this), this['_entity'] && (this['dataSource']['entities']['remove'](this['_entity']), delete this['_entity']), this['_arrShowingEntity']) {
                    for (var _0x5ec82b = 0, _0x30b7e8 = this['_arrShowingEntity'].length; _0x5ec82b < _0x30b7e8; _0x5ec82b++) this['dataSource']['entities']['remove'](this['_arrShowingEntity'][_0x5ec82b]);
                    delete this['_arrShowingEntity'];
                }
                this['_bak_startTime'] && (this['_map']['clock'].startTime = this['_bak_startTime'], delete this['_bak_startTime']), this['_bak_stopTime'] && (this['_map']['clock']['stopTime'] = this['_bak_stopTime'], delete this['_bak_stopTime']), this['_bak_multiplier'] && (this['_map']['clock']['multiplier'] = this['_bak_multiplier'], delete this['_bak_multiplier']), this['_bak_clockRange'] && (this['_map']['clock']['clockRange'] = this['_bak_clockRange'], delete this['_bak_clockRange']), this['_flyok_point_index'] = 0x0, this.options['showStop'] && this['_createStaticModel'](), this['_isStart'] = !0x1, this['fire'](_0x128102['end']);
            }
        }
    }, {
        'key': '_onPreRenderHandler', 'value': function (_0x2b3ef6) {
            if (this['_isStart'] && null != this['_entity'] && this['_map']) {
                if (this['_map']['clock']['shouldAnimate'] && _0x324641.JulianDate['greaterThanOrEquals'](this['_map']['clock']['currentTime'],
                    this['stopTime'])) return this['_flyok_point_index'] = this.positions.length - 1,
                this['_onStepTempBS'] || (this['fire'](_0x128102['endItem'], {
                    'index': this['_flyok_point_index'],
                    'counts': this.positions.length
                }), this['fire'](_0x128102['end']), this['_onStepTempBS'] = !0),
                    void ((this.options['autoStop'] || this['_map']['clock']['clockRange'] == _0x324641['ClockRange']['UNBOUNDED']) && this['stop']());
                var _0x1e1fef = this['position'];
                if (_0x324641['defined'](_0x1e1fef)) {
                    if (this['_hasCache']) {
                        var _0x42cfdb = ''['concat'](_0x1e1fef['x'], '-')['concat'](_0x1e1fef['y'], '-')['concat'](_0x1e1fef['z']);
                        if (_0x42cfdb == this['_position_cache']) return;
                        this['_position_cache'] = _0x42cfdb;
                    }
                    switch (this.options.camera['type']) {
                        default:
                            this['_map'].trackedEntity == this['_entity'] && (this['_map'].trackedEntity = void 0);
                            break;
                        case'gs':
                            this['_map'].trackedEntity != this['_entity'] && (this['_map'].trackedEntity = this['_entity'],
                                this['flyToPoint'](_0x30f6e6(_0x30f6e6({}, this.options.camera), {}, {'duration': 0})));
                            break;
                        case'dy':
                            this['_map'].trackedEntity != this['_entity'] && (this['_map'].trackedEntity = this['_entity']);
                            var _0x546100, _0xdaefd1 = this.options.camera['followedX'],
                                _0x5a8b9a = this.options.camera['followedZ'];
                            0 === _0xdaefd1 && 0 === _0x5a8b9a && (_0xdaefd1 = 0.1), this.options.camera['offsetX'] && ((_0x546100 = _0x546100 || {})['x'] = this.options.camera['offsetX']), this.options.camera['offsetY'] && ((_0x546100 = _0x546100 || {})['y'] = this.options.camera['offsetY']), this.options.camera['offsetZ'] && ((_0x546100 = _0x546100 || {})['z'] = this.options.camera['offsetZ']), this['_map']['scene'].camera['lookAtTransform'](this['computeModelMatrix'](_0x546100), new _0x324641[('Cartesian3')](-_0xdaefd1, 0x0, _0x5a8b9a));
                            break;
                        case'sd':
                            this['_map'].trackedEntity != this['_entity'] && (this['_map'].trackedEntity = this['_entity']), this['_map']['scene'].camera['lookAtTransform'](this['computeModelMatrix'](), new _0x324641[('Cartesian3')](-0x1, 0x0, this.options.camera['followedZ']));
                    }
                    this['_map']['clock']['shouldAnimate'] && this['_updateRealTimePosition'](_0x1e1fef);
                }
            }
        }
    }, {
        'key': '_updateRealTimePosition', 'value': function (_0x52b216) {
            var _0x3aa183 = this;
            if (this['_map'] && this['_isStart']) {
                var _0x391f3e = _0x324641.JulianDate['secondsDifference'](this['_map']['clock']['currentTime'], this.startTime),
                    _0x2caeea = _0x5e7fa7['a']['fromCartesian'](_0x52b216), _0x15afa2 = this['getCurrIndex'](),
                    _0x19de39 = this.positions.length, _0x1a380a = this['stepLen'][_0x15afa2],
                    _0x5d9378 = this.positions[_0x15afa2];
                _0x324641['defined'](_0x5d9378) && (_0x1a380a += _0x324641.Cartesian3['distance'](_0x52b216, _0x5d9378)),
                _0x1a380a >= this['alllen'] && (_0x15afa2 = _0x19de39 - 1, _0x1a380a = this['alllen']),
                _0x15afa2 != this['_flyok_point_index'] && this['fire'](_0x128102['endItem'], {
                    'index': _0x15afa2,
                    'counts': _0x19de39
                }), this['_flyok_point_index'] = _0x15afa2, this['_timeinfo'] = {
                    'time': _0x391f3e,
                    'len': _0x1a380a,
                    'lng': _0x2caeea['lng'],
                    'lat': _0x2caeea['lat'],
                    'alt': _0x2caeea['alt']
                };
                var _0xba1338 = _0x324641['Cartographic']['fromCartesian'](_0x52b216),
                    _0x5d02f8 = this['_map']['scene']['globe']['getHeight'](_0xba1338);
                null != _0x5d02f8 && _0x5d02f8 > 0 && (_0x52b216['hbgd'] = _0x5d02f8,
                    this['_timeinfo']['hbgd'] = _0x5d02f8, this['_timeinfo']['ldgd'] = _0x2caeea['alt'] - _0x5d02f8), this.options['showGroundHeight'] && _0x3d1793({
                    'map': this['_map'],
                    'positions': [_0x52b216, _0x52b216],
                    'callback': function (_0x395121, _0x4e845d) {
                        if (null != _0x395121 && 0 != _0x395121.length && !_0x4e845d) {
                            var _0x37300b, _0x56c9df = _0x5e7fa7['a']['fromCartesian'](_0x395121[0])['alt'],
                                _0x30af2b = _0x2caeea['alt'] - _0x56c9df;
                            if (_0x3aa183['_timeinfo']) {
                                if (_0x3aa183['_timeinfo']['hbgd'] = _0x56c9df, _0x3aa183['_timeinfo']['ldgd'] = _0x30af2b,
                                null !== (_0x37300b = _0x3aa183['_entity']) && void 0 !== _0x37300b && _0x37300b['label']) {
                                    var _0xfcc662 = _0x396d76(_0x3aa183['_timeinfo']['alt']),
                                        _0x55d8c9 = _0x396d76(_0x3aa183['_timeinfo']['ldgd']);
                                    _0x3aa183['_entity']['label']['text'] = _0x3aa183['name'] + '\n漫游高程：' + _0xfcc662 + '\n离地距离：' + _0x55d8c9;
                                }
                            }
                        }
                    }
                }), this.options['shadow'] && this.options['shadow'].length > 0 && this['_updateArrShading'](_0x52b216),
                    this['fire'](_0x128102['change'], _0x30f6e6(_0x30f6e6({}, this['_timeinfo']), {}, {
                    'time': _0x391f3e,
                    'position': _0x52b216,
                    'index': this['_flyok_point_index']
                }));
            }
        }
    }, {
        'key': 'clampToGround', 'value': function (_0x2836f9) {
            var _0xb793b5 = this, _0x197864 = this.points,
                _0x5314a2 = this.arrSpeed || this.options.speed, _0x5cc2ae = [], _0x49b8c2 = [],
                _0x28b923 = 0, _0x383961 = [], _0x11f1fe = [], _0x314aef = [], _0x263d0e = [];
            _0x5e385b({
                'map': this['_map'],
                'positions': this.positions,
                'splitNum': this.options['splitNum'],
                'minDistance': this.options['minDistance'],
                'has3dtiles': this.options['clampToTileset'] || this.options['has3dtiles'],
                'objectsToExclude': this['objectsToExclude'],
                'offset': this.options['offset'],
                'endItem': function (_0x38ce72, _0x4bdbff, _0x3105c3) {
                    var _0x15319b, _0x5e2bd2;
                    if (_0xb793b5['_map']) {
                        var _0x48aab2 = _0x5314a2[_0x3105c3];
                        if (_0x4bdbff) _0x5cc2ae.push(_0x197864[_0x3105c3]['toArray']()), _0x49b8c2.push(_0x48aab2);
                        else for (var _0x53d377 = 0; _0x53d377 < _0x38ce72.length; _0x53d377++) {
                            var _0x3ea7f7 = _0x38ce72[_0x53d377],
                                _0x5cbc84 = _0x324641['Cartographic']['fromCartesian'](_0x3ea7f7);
                            _0x5cc2ae.push([_0x324641['Math']['toDegrees'](_0x5cbc84['longitude']),
                                _0x324641['Math']['toDegrees'](_0x5cbc84['latitude']), _0x5cbc84['height']]), _0x49b8c2.push(_0x48aab2);
                        }
                        for (var _0x5ba4cf = (null === (_0x15319b = _0x197864[_0x3105c3]) || void 0 === _0x15319b ? void 0 : _0x15319b['alt']) || 0,
                                 _0x2e30d0 = (((null === (_0x5e2bd2 = _0x197864[_0x3105c3 + 1]) || void 0 === _0x5e2bd2 ? void 0 : _0x5e2bd2['alt']) || 0) - _0x5ba4cf) / _0x38ce72.length,
                                 _0x1d0407 = 0; _0x1d0407 < _0x38ce72.length; _0x1d0407++) {
                            0 != _0x1d0407 && (_0x28b923 += _0x324641.Cartesian3['distance'](_0x38ce72[_0x1d0407], _0x38ce72[_0x1d0407 - 1])),
                                _0x383961.push(Number(_0x28b923['toFixed'](1)));
                            var _0x38c525 = _0x5e7fa7['a']['fromCartesian'](_0x38ce72[_0x1d0407]);
                            _0x263d0e.push(_0x38c525);
                            var _0x59ab36 = _0x4bdbff ? 0 : _0x38c525['alt'];
                            _0x11f1fe.push(_0x59ab36);
                            var _0x3a22b4 = Number((_0x5ba4cf + _0x2e30d0 * _0x1d0407)['toFixed'](1));
                            _0x314aef.push(_0x3a22b4);
                        }
                    }
                },
                'end': function () {
                    _0xb793b5['_map'] && (_0xb793b5['terrainHeight'] = {
                        'arrLength': _0x383961,
                        'arrFxgd': _0x314aef,
                        'arrHbgd': _0x11f1fe,
                        'arrPoint': _0x263d0e
                    }, _0xb793b5.createPath(_0x5cc2ae, _0x30f6e6(_0x30f6e6({}, _0xb793b5.options), {}, {'speed': _0x49b8c2})), _0x2836f9 && _0x2836f9({
                        'lonlats': _0x5cc2ae,
                        'speed': _0x49b8c2
                    }));
                }
            });
        }
    }, {
        'key': 'getTerrainHeight', 'value': function (_0x462b9d) {
            var _0x4e137f = this;
            if (this['terrainHeight']) return _0x462b9d(this['terrainHeight']), this['terrainHeight'];
            var _0x48c3b1 = this.points, _0x2b91b8 = 0, _0x1d70e4 = [], _0x53242 = [], _0x5aa151 = [],
                _0x402400 = [];
            _0x5e385b({
                'map': this['_map'],
                'positions': this.positions,
                'splitNum': this.options['splitNum'],
                'offset': this.options['offset'],
                'has3dtiles': this.options['clampToTileset'] || this.options['has3dtiles'],
                'objectsToExclude': this['objectsToExclude'],
                'endItem': function (_0x11b31f, _0x304d9f, _0x4d57e2) {
                    for (var _0x2c302d = _0x48c3b1[_0x4d57e2]['alt'], _0x4878a0 = (_0x48c3b1[_0x4d57e2 + 1]['alt'] - _0x2c302d) / _0x11b31f.length,
                             _0x33ea60 = 0; _0x33ea60 < _0x11b31f.length; _0x33ea60++) {
                        0x0 != _0x33ea60 && (_0x2b91b8 += _0x324641.Cartesian3['distance'](_0x11b31f[_0x33ea60], _0x11b31f[_0x33ea60 - 1])),
                            _0x1d70e4.push(Number(_0x2b91b8['toFixed'](1)));
                        var _0x400119 = _0x5e7fa7['a']['fromCartesian'](_0x11b31f[_0x33ea60]);
                        _0x402400.push(_0x400119);
                        var _0xab4e6b = _0x304d9f ? 0 : _0x400119['alt'];
                        _0x53242.push(_0xab4e6b);
                        var _0x496219 = Number((_0x2c302d + _0x4878a0 * _0x33ea60)['toFixed'](1));
                        _0x5aa151.push(_0x496219);
                    }
                },
                'end': function () {
                    _0x4e137f['terrainHeight'] = {
                        'arrLength': _0x1d70e4,
                        'arrFxgd': _0x5aa151,
                        'arrHbgd': _0x53242,
                        'arrPoint': _0x402400
                    }, _0x462b9d(_0x4e137f['terrainHeight']);
                }
            });
        }
    }]), _0x4b5fde;
}(_0x42bfad);
