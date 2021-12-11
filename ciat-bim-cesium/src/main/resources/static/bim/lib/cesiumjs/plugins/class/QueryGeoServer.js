function _typeof(ixMx0100) {
    return (_typeof = 'function' == typeof Symbol && 'symbol' == typeof Symbol.iterator ? function (efqc0312) {
        return typeof efqc0312;
    } : function (hT6D0000) {
        return hT6D0000 && 'function' == typeof Symbol && hT6D0000.constructor === Symbol && hT6D0000 !== Symbol.prototype ? 'symbol' : typeof hT6D0000;
    })(ixMx0100);
}

function _classCallCheck(ihwa0104, kIJB0000) {
    if (!(ihwa0104 instanceof kIJB0000)) throw new TypeError('Cannot\x20call\x20a\x20class\x20as\x20a\x20function');
}

function _defineProperties(hXlc0001, fl4u0100) {
    for (var gccB0300 = 0x0; gccB0300 < fl4u0100.length; gccB0300++) {
        var hGGP0000 = fl4u0100[gccB0300];
        hGGP0000.enumerable = hGGP0000['enumerable'] || !0x1, hGGP0000['configurable'] = !0x0, 'value' in hGGP0000 && (hGGP0000.writable = !0x0), Object.defineProperty(hXlc0001, hGGP0000.key, hGGP0000);
    }
}

function _createClass(i0x20010, hgsa0203, iSXa0003) {
    return hgsa0203 && _defineProperties(i0x20010.prototype, hgsa0203), iSXa0003 && _defineProperties(i0x20010, iSXa0003), i0x20010;
}

var QueryGeoServer = function () {
    'use strict';

    function haaw0221(edcj0300) {
        _classCallCheck(this, haaw0221), this.options = edcj0300, this.name = Cesium.defaultValue(edcj0300.name, '');
    }

    return _createClass(haaw0221, [{
        'key': 'query', 'value': function (h1dS0010) {
            var ftQc0101 = '<Filter xmlns="http://www.opengis.net/ogc" xmlns:gml="http://www.opengis.net/gml"><And>';
            if ((this.lastQueryOpts = h1dS0010).text && (ftQc0101 += ' <PropertyIsLike wildCard="*" singleChar="#" escapeChar="!"> \n   <PropertyName>'.concat(h1dS0010.column, '</PropertyName>\n   <Literal>*').concat(h1dS0010['text'], '*</Literal>\n    </PropertyIsLike>')), h1dS0010.fwEntity) {

                var nnQB0100, iBy60010 = h1dS0010.fwEntity;
                iBy60010.polygon ? (nnQB0100 = mars3d.draw.attr.polygon.getCoordinates(iBy60010)).push(nnQB0100[0x0]) : iBy60010.rectangle ? nnQB0100 = mars3d.draw.attr.rectangle.getOutlineCoordinates(iBy60010) : iBy60010.ellipse && (nnQB0100 = mars3d.draw.attr.ellipse.getOutlineCoordinates(iBy60010));
                for (var qIBj0001 = '', hhu70000 = 0x0; hhu70000 < nnQB0100.length; hhu70000++) qIBj0001 += nnQB0100[hhu70000][0x0] + ',' + nnQB0100[hhu70000][0x1] + '\x20';
                ftQc0101 += '<Intersects>\n <PropertyName>the_geom</PropertyName>\n   <gml:Polygon>\n   <gml:outerBoundaryIs>\n   <gml:LinearRing>\n   <gml:coordinates>'
                    .concat(qIBj0001, '</gml:coordinates>\n    </gml:LinearRing>\n    </gml:outerBoundaryIs>\n  </gml:Polygon>\n  </Intersects>');
            }
            ftQc0101 += '</And></Filter>';
            var fhq60010 = this.options.parameters;
            fhq60010.filter = ftQc0101;
            var tMDx0001 = this;
            $.ajax({
                'url': this.options.url,
                'type': 'get',
                'data': fhq60010,
                'success': function (qNft0031) {
                    tMDx0001.processFeatureCollection(qNft0031, h1dS0010);
                },
                'error': function (hhte0000) {
                    if ('akCGW' !== 'akCGW') {
                        if (!(hhte0000 instanceof ia6G0300)) throw new TypeError('Cannot call a class as a function');
                    } else {
                        var ia6G0300 = '请求出错(' + hhte0000.status + ')：' + hhte0000.statusText;
                        console.log(ia6G0300), h1dS0010['error'] && h1dS0010.error(hhte0000, ia6G0300);
                    }
                }
            });
        }
    }, {
        'key': 'processFeatureCollection', 'value': function (em6w0000, gaPx0200) {

            var eOVb0000 = this;
            if (null == em6w0000 || null == em6w0000 || null == em6w0000.features || 0x0 == em6w0000.features.length) gaPx0200.success && gaPx0200.success({
                'list': null,
                'dataSource': null,
                'count': 0x0
            }); else {
                for (var e7he0002 = [], g1EK0000 = 0x0; g1EK0000 < em6w0000.features.length; g1EK0000++) {
                    var jiia0100 = em6w0000.features[g1EK0000];
                    null != jiia0100 && null != jiia0100.geometry && null != jiia0100.geometry.coordinates && 0x0 != jiia0100.geometry.coordinates.length && e7he0002.push(jiia0100);
                }
                em6w0000.features = e7he0002, Cesium.GeoJsonDataSource.load(em6w0000, {'clampToGround': !0x0}).then(function (iBSe0002) {
                        for (var ies10300 = [], km4x0001 = iBSe0002.entities.values, gxxm0100 = 0x0, fwbf0132 = km4x0001.length; gxxm0100 < fwbf0132; gxxm0100++) {
                            var jgkg0000 = km4x0001[gxxm0100],
                                glYI0100 = mars3d.util.getAttrVal(jgkg0000.properties);
                            glYI0100.name = glYI0100[gaPx0200['column']], glYI0100._entity = jgkg0000, ies10300.push(glYI0100), eOVb0000.options.popup && (jgkg0000.popup = {
                                'html': function (ivpm0100) {
                                    var gbcv0330 = ivpm0100.properties;
                                    return mars3d.util.getPopup(eOVb0000.options.popup, gbcv0330, eOVb0000.name);

                                }, 'anchor': [0x0, -0xf]
                            });
                            var iCOf0002 = eOVb0000.options.label;
                            iCOf0002 && (iCOf0002.text = glYI0100.name, iCOf0002.clampToGround = Cesium.defaultValue(iCOf0002.clampToGround, !0x0), iCOf0002.pixelOffset = Cesium.defaultValue(iCOf0002.pixelOffset, [0x0, -0x32]), iCOf0002 = mars3d.draw.util.getDefStyle('label', iCOf0002), jgkg0000['label'] = mars3d.draw.attr['label'].style2Entity(iCOf0002));
                        }
                        gaPx0200.success && gaPx0200.success({
                            'list': ies10300,
                            'dataSource': iBSe0002,
                            'count': ies10300.length
                        });

                })['otherwise'](function (f2CW0000) {
                    gaPx0200.error && gaPx0200.error(f2CW0000, f2CW0000.message);
                });
            }


        }
    }]), haaw0221;
}();

function isObject(iAoo0000) {
    return 'object' == _typeof(iAoo0000) && iAoo0000.constructor == Object;
}
