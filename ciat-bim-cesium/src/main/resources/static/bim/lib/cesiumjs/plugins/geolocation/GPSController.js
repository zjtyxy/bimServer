!function () {
    var haay0221, eakd0003;
    function s3md0000(jPff0001, jzas0040) {
        this['viewer'] = jPff0001, this['okCalback'] = jzas0040, this._liveTrackingActivated = !0x1, this._timer = void 0x0, this._timerMiliseconds = 0x15e, this._savedAlpha = void 0x0, this._firstActivated = !0x1, this['_watchPos'] = !0x1, this._touchHoldDuration = 0x1f4, this._longPress = !0x1, this['createGPSButton']();
    }

    function lvlc0111() {
        eakd0003 && (eakd0003['destroy'](), eakd0003 = null);
    }

    document.write('<script type="text/javascript" src="http://api.map.baidu.com/getscript?v=2.0&ak=5BdfbH8G0acGrG8bvauqkc6RClTe64fz&services="></script>'),
        Object.defineProperties(s3md0000['prototype'], {
        'liveTrackingActivated': {
            'get': function () {
                return this._liveTrackingActivated;
            }, 'set': function (mmbx0121) {
                this['_liveTrackingActivated'] = mmbx0121;
            }
        }, 'timer': {
            'get': function () {
                return this._timer;
            }, 'set': function (gyef0130) {
                    this._timer = gyef0130;
            }
        }, 'timerMiliseconds': {
            'get': function () {
                return this._timerMiliseconds;
            }, 'set': function (gf4c0100) {
                this._timerMiliseconds = gf4c0100;
            }
        }, 'savedAlpha': {
            'get': function () {

                return this._savedAlpha;

            }, 'set': function (leYe0100) {

                this['_savedAlpha'] = leYe0100;

            }
        }, 'firstActivated': {
            'get': function () {
                return this['_firstActivated'];
            }, 'set': function (hWxs0010) {
                this._firstActivated = hWxs0010;
            }
        }, 'watchPos': {
            'get': function () {
                return this['_watchPos'];
            }, 'set': function (fcTd0000) {
                this._watchPos = fcTd0000;
            }
        }, 'touchHoldDuration': {
            'get': function () {
                return this._touchHoldDuration;
            }, 'set': function (fa4V0400) {
                this['_touchHoldDuration'] = fa4V0400;
            }
        }, 'longPress': {
            'get': function () {
                return this._longPress;
            }, 'set': function (m7kF0010) {
                this._longPress = m7kF0010;
            }
        }, 'isMobile': {
            'get': function () {
                return this._isMobile;
            }, 'set': function (h0ce0002) {
                this._isMobile = h0ce0002;
            }
        }
    }), s3md0000.prototype.createGPSButton = function () {

        var f6Ie0001 = this, iqcs0111 = document.createElement('div');
        iqcs0111['id'] = 'gpsButton', iqcs0111.className = 'cesium-button cesium-toolbar-button tracking-deactivated', iqcs0111['title'] = '查看GPS位置(单击：一次，双击：实时)';
        var geZr0301 = document.getElementsByClassName('cesium-viewer-toolbar')[0x0],
            eHfz0030 = geZr0301.getElementsByClassName('cesium-sceneModePicker-wrapper cesium-toolbar-button')[0x0];
        geZr0301['appendChild'](iqcs0111, eHfz0030);
        var eqqz0100 = 0x0, iyvY0000 = 0x0;
        f6Ie0001._isMobile ? (iqcs0111['addEventListener']('touchstart', function (hSue0011) {
            eqqz0100 = Date['now']();
        }, !0x1), iqcs0111['addEventListener']('touchend', function (gvak0140) {

            iyvY0000 = Date.now() - eqqz0100, f6Ie0001._longPress = iyvY0000 >= f6Ie0001._touchHoldDuration;

        }, !0x1)) : (iqcs0111.addEventListener('mousedown', function (eznk0110) {
            eqqz0100 = Date['now']();
        }, !0x1), window.addEventListener('mouseup', function (iimu0101) {
            iyvY0000 = Date.now() - eqqz0100, f6Ie0001['_longPress'] = iyvY0000 >= f6Ie0001._touchHoldDuration;
        }, !0x1)), iqcs0111['onclick'] = function () {
            if (f6Ie0001._liveTrackingActivated && (f6Ie0001._liveTrackingActivated = !0x1, f6Ie0001.stopTracking()), f6Ie0001._longPress) {
                nG7d0000 = function () {
                    f6Ie0001['viewer'].camera.flyTo({
                        'destination': Cesium.Cartesian3['fromRadians'](f6Ie0001.viewer.camera.positionCartographic.longitude, f6Ie0001.viewer.camera.positionCartographic['latitude'], 0xfa),
                        'orientation': {
                            'heading': f6Ie0001.viewer['camera'].heading,
                            'pitch': Cesium.Math.toRadians(-0x4b),
                            'roll': 0x0
                        }
                    });
                }, f6Ie0001['_firstActivated'] = !0x1, f6Ie0001.viewer.camera['cancelFlight'](), nG7d0000();
            } else {
                var qq2d0103 = document['getElementById']('gpsButton');
                null == qq2d0103.getAttribute('data-double-click') ? (qq2d0103['setAttribute']('data-double-click', 0x1), setTimeout(function () {

                    0x1 == qq2d0103.getAttribute('data-double-click') && f6Ie0001.handleClick(), qq2d0103.removeAttribute('data-double-click');

                }, 0x1f4)) : null == qq2d0103['getAttribute']('data-triple-click') ? (qq2d0103.setAttribute('data-triple-click', 0x1), setTimeout(function () {
                    0x1 == qq2d0103.getAttribute('data-triple-click') && f6Ie0001['handleDclick'](), qq2d0103.removeAttribute('data-double-click'), qq2d0103.removeAttribute('data-triple-click');
                }, 0x1f4)) : (qq2d0103.removeAttribute('data-double-click'), qq2d0103.removeAttribute('data-triple-click'), f6Ie0001.handleTclick());
            }
            var nG7d0000;
        };

    }, s3md0000.prototype.handleClick = function () {
        var hDGc0002 = this;
        if (hDGc0002['_liveTrackingActivated']) hDGc0002._liveTrackingActivated = !0x1, hDGc0002['stopTracking'](); else {
            var k8oa0002 = document.getElementById('gpsButton');
            k8oa0002['classList'].remove('tracking-ori-activated'), k8oa0002.classList.remove('tracking-pos-ori-activated'), k8oa0002.classList.add('tracking-deactivated'), hDGc0002['startTracking']();
        }
    }, s3md0000.prototype.handleDclick = function () {
        var hnde0000 = this;
        if (hnde0000._liveTrackingActivated) hnde0000._liveTrackingActivated = !0x1, hnde0000['stopTracking'](); else {
            hnde0000._liveTrackingActivated = !0x0, hnde0000._watchPos = !0x1;
            var iPtF0000 = document.getElementById('gpsButton');
            iPtF0000.classList.remove('tracking-deactivated'), iPtF0000.classList.remove('tracking-ori-deactivated'), iPtF0000.classList['add']('tracking-ori-activated'), hnde0000.startTracking();
        }

    }, s3md0000.prototype.handleTclick = function () {
        var h80A0000 = this;
        if (h80A0000._liveTrackingActivated) h80A0000._liveTrackingActivated = !0x1, h80A0000.stopTracking(); else {
            h80A0000._liveTrackingActivated = !0x0, h80A0000._watchPos = !0x0;
            var etwh0101 = document.getElementById('gpsButton');
            etwh0101.classList['remove']('tracking-deactivated'), etwh0101.classList.remove('tracking-pos-ori-deactivated'), etwh0101['classList'].add('tracking-pos-ori-activated'), h80A0000.startTracking();
        }
    }, s3md0000.prototype.startTracking = function () {

        var jjnW0010 = this;
        (haay0221 = haay0221 || new BMap[('Geolocation')]())['getCurrentPosition'](function (hPx90000) {
            if (this.getStatus() == BMAP_STATUS_SUCCESS) {

                var g1Fp0001 = mars3d.pointconvert['bd2wgs']([hPx90000.point['lng'], hPx90000.point.lat]);
                jjnW0010.flyToLocation({'x': g1Fp0001[0x0], 'y': g1Fp0001[0x1]});

            } else haoutil.msg(this['getStatus'](), '定位失败');
        }, {'enableHighAccuracy': !0x0});

    }, s3md0000.prototype.flyToLocation = function (lKGQ0000) {

        var fxAo0100 = this;
        this.okCalback && this.okCalback(lKGQ0000), lvlc0111(), eakd0003 = new mars3d['DivPoint'](this.viewer, {
            'html': '<div\x20class=\x22mars3d-animation-point\x22\x20style=\x22color:#00ff00;\x22><p></p></div>',
            'position': Cesium['Cartesian3'].fromDegrees(lKGQ0000['x'], lKGQ0000['y']),
            'tooltip': '我的位置：' + lKGQ0000['x'] + ',' + lKGQ0000['y']
        }), setTimeout(function () {
            lvlc0111();
        }, 0x4e20), this.viewer['camera'].flyTo({
            'destination': Cesium.Cartesian3.fromDegrees(lKGQ0000['x'], lKGQ0000['y'], 0xfa0),
            'orientation': {'heading': 0x0, 'pitch': Cesium['Math'].toRadians(-0x5a), 'roll': 0x0},
            'complete': function () {
                fxAo0100._firstActivated = !0x0, fxAo0100._liveTrackingActivated && (fxAo0100._longPress || (fxAo0100._timer = setTimeout(function () {
                    fxAo0100._watchPos ? fxAo0100.startTracking() : showPosition(lKGQ0000);
                }, fxAo0100._timerMiliseconds)));
            }
        });

    }, s3md0000.prototype.stopTracking = function () {
        this._watchPos = !0x1;
        var eakA0000 = document.getElementById('gpsButton');
        eakA0000.classList['remove']('tracking-activated'), eakA0000.classList['add']('tracking-deactivated'), clearTimeout(this._timer), lvlc0111();
    }, mars3d.GPSController = s3md0000;
}();
