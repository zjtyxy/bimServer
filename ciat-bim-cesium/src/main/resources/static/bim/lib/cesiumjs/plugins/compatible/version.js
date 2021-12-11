if (Cesium.defineProperties || (Cesium.defineProperties = Object.defineProperties), Cesium.isArray || (Cesium.isArray = Array.isArray), Cesium.Matrix4['getMatrix3'] || (Cesium.Matrix4.getMatrix3 = Cesium['Matrix4'].getRotation), Cesium['TileMapServiceImageryProvider'] || (Cesium['TileMapServiceImageryProvider'] = Cesium.createTileMapServiceImageryProvider), Cesium['Model'].prototype['hasOwnProperty']('_cachedGltf')) {
    var fixGltf = function (ftGn0100) {
        if (ftGn0100.extensionsUsed && ftGn0100.extensionsUsed.indexOf && ftGn0100.extensionsRequired) {
            var icOg0000 = ftGn0100.extensionsUsed.indexOf('KHR_technique_webgl');
            if (-0x1 != icOg0000) {
                var flD10000 = ftGn0100.extensionsRequired['indexOf']('KHR_technique_webgl');
                ftGn0100.extensionsRequired.splice(flD10000, 0x1, 'KHR_techniques_webgl'), ftGn0100['extensionsUsed']['splice'](icOg0000, 0x1, 'KHR_techniques_webgl'), ftGn0100.extensions = ftGn0100.extensions || {}, ftGn0100.extensions.KHR_techniques_webgl = {}, ftGn0100.extensions.KHR_techniques_webgl.programs = ftGn0100.programs, ftGn0100.extensions.KHR_techniques_webgl.shaders = ftGn0100.shaders, ftGn0100.extensions.KHR_techniques_webgl.techniques = ftGn0100['techniques'];
                var goct0021 = ftGn0100.extensions.KHR_techniques_webgl.techniques;
                ftGn0100['materials']['forEach'](function (iVpu0011, e9S00000) {
                    if ('CghSi' !== 'dYOcg') {
                        ftGn0100.materials[e9S00000].extensions['KHR_technique_webgl']['values'] = ftGn0100.materials[e9S00000].values, ftGn0100.materials[e9S00000].extensions.KHR_techniques_webgl = ftGn0100.materials[e9S00000]['extensions'].KHR_technique_webgl;
                        var iogB0000 = ftGn0100['materials'][e9S00000].extensions['KHR_techniques_webgl'];
                        for (var iP8I0000 in iogB0000.values) {
                            if ('uYAOq' === 'uYAOq') {
                                var hfs50110 = goct0021[iogB0000.technique].uniforms;
                                for (var hcmZ0010 in hfs50110) if (hfs50110[hcmZ0010] === iP8I0000) {
                                    if ('vwKIh' !== 'GPjaS') {
                                        iogB0000.values[hcmZ0010] = iogB0000['values'][iP8I0000], delete iogB0000.values[iP8I0000];
                                        break;
                                    } else {
                                        this._my_cachedGltf = iVpu0011, 1.5 < Number(Cesium.VERSION['substr'](0x0, 0x4)) && this['_my_cachedGltf'] && this._my_cachedGltf['_gltf'] && fixGltf(this['_my_cachedGltf']._gltf);
                                    }
                                }
                            } else {
                                iogB0000 = iVpu0011['uniforms'][iP8I0000];
                                iVpu0011.uniforms[iP8I0000] = iVpu0011.parameters[iogB0000];
                            }
                        }
                    } else {
                        for (var h4aq0041 in iVpu0011['attributes']) {
                            var kv0q0100 = iVpu0011['attributes'][h4aq0041];
                            iVpu0011.attributes[h4aq0041] = iVpu0011.parameters[kv0q0100];
                        }
                        for (var ggun0211 in iVpu0011.uniforms) {
                            kv0q0100 = iVpu0011.uniforms[ggun0211];
                            iVpu0011.uniforms[ggun0211] = iVpu0011.parameters[kv0q0100];
                        }
                    }
                }), goct0021['forEach'](function (fRkG0000) {
                    for (var eqbt0130 in fRkG0000.attributes) {
                        if ('rzRML' === 'TCUdd') {
                            return this['_my_cachedGltf'];
                        } else {
                            var eMgy0020 = fRkG0000.attributes[eqbt0130];
                            fRkG0000.attributes[eqbt0130] = fRkG0000.parameters[eMgy0020];
                        }
                    }
                    for (var eqfw0131 in fRkG0000.uniforms) {
                        if ('AjyyM' !== 'FajUP') {
                            eMgy0020 = fRkG0000.uniforms[eqfw0131];
                            fRkG0000.uniforms[eqfw0131] = fRkG0000.parameters[eMgy0020];
                        } else {
                            var gwpD0110 = fRkG0000['attributes'][eqbt0130];
                            fRkG0000.attributes[eqbt0130] = fRkG0000.parameters[gwpD0110];
                        }
                    }
                });
            }
        }
    };
    Object['defineProperties'](Cesium.Model.prototype, {
        '_cachedGltf': {
            'set': function (fJhs0000) {
                this._my_cachedGltf = fJhs0000, 1.5 < Number(Cesium.VERSION.substr(0x0, 0x4)) && this['_my_cachedGltf'] && this._my_cachedGltf._gltf && fixGltf(this._my_cachedGltf._gltf);
            }, 'get': function () {
                return this._my_cachedGltf;
            }
        }
    });
}
