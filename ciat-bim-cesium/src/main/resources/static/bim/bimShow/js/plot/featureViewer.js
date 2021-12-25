/* 2019-3-21 09:33:54 | 版权所有 广西中遥 http://91daocao.com   */
var featureViewer = {
    colorHighlight: Cesium.Color.YELLOW, colorSelected: Cesium.Color.LIME, getBimAttr: function (e) {
        if (!e.tileset.properties || !e.tileset.properties.length) return !1;
        var t;
        if (e.hasProperty("file")) for (var r = e.getProperty("file"), i = 0; i < e.tileset.properties.length; i++) {
            var o = e.tileset.properties[i];
            o.file == r && (t = o.params)
        } else t = e.tileset.properties[0].params;
        if (!t) return !1;
        var n = [];

        function a(e, t) {
            null != t && "" != t && 0 != t && n.push({name: e, value: t})
        }

        e.hasProperty("id") && a("ID", e.getProperty("id")), e.hasProperty("name") && a("名称", e.getProperty("name")), e.hasProperty("LevelName") && a("楼层", e.getProperty("LevelName")), e.hasProperty("CategoryName") && a("分类", e.getProperty("CategoryName")), e.hasProperty("FamilyName") && a("族", e.getProperty("FamilyName")), e.hasProperty("FamilySymbolName") && a("族类型", e.getProperty("FamilySymbolName")), e.hasProperty("file") && a("文件名", e.getProperty("file"));
        for (var s, l = {}, p = e._content.batchTable.getPropertyNames(e._batchId), c = 0; c < p.length; c++) {
            var u = p[c];
            if ("_properties" == u) {
                var f = e.getProperty(u);
                try {
                    for (var m = JSON.parse(f), g = 0; g < m.length; g++) {
                        var y = m[g];
                        (E = l[y.name]) || (E = []);
                        for (var d = 0; d < y.properties.length; d++) {
                            var v = y.properties[d];
                            if (v.value) {
                                var h = {name: v.name, value: v.value};
                                E.push(h)
                            }
                        }
                        l[y.name] = E
                    }
                } catch (e) {
                    console.log("parse _properties failed:" + e)
                }
            } else {
                var C, P = u, _ = e.getProperty(u);
                for (d = 0; d < t.length; d++) {
                    var S = t[d];
                    if (P == S.name) {
                        C = S;
                        break
                    }
                }
                if (!C) continue;
                (E = l[C.group]) || (E = []);
                var b = _;
                if ("YesNo" == C.type && (b = 1 == _ ? "是" : "否"), "Length" == C.type && (b = (.3048 * _).toFixed(2) + "m"), "Area" == C.type && (b = (.3048 * _ * .3048).toFixed(2) + "㎡"), "Volume" == C.type && (b = (.3048 * _ * .3048 * .3048).toFixed(2) + "m³"), !b) continue;
                h = {name: C.name, value: b};
                E.push(h), l[C.group] = E
            }
        }
        for (group in l) {
            n.push({
                type: "group",
                name: (s = group, "PG_IDENTITY_DATA" == s ? "标识数据" : "PG_GEOMETRY" == s ? "尺寸标注" : "PG_PHASING" == s ? "阶段化" : "PG_CONSTRAINTS" == s ? "约束" : "INVALID" == s ? "其他" : "PG_MATERIALS" == s ? "材质和装饰" : "PG_CONSTRUCTION" == s ? "构造" : s)
            });
            var E = l[group];
            for (i = 0; i < E.length; i++) n = n.concat(E[i])
        }
        return n
    }, install: function (u) {
        this.viewer = u;
        var i = document.createElement("div");
        u.container.appendChild(i), i.style.display = "none", i.style.position = "absolute", i.style.bottom = "0", i.style.left = "0", i.style.color = "#ffffff", i.style["pointer-events"] = "none", i.style.padding = "4px", i.style.backgroundColor = "black", this.nameOverlay = i;
        var f = {feature: void 0, originalColor: new Cesium.Color},
            m = {feature: void 0, originalColor: new Cesium.Color}, g = new Cesium.Entity, y = this;
        this.restoreHighlight = function () {
            if (Cesium.defined(m.feature)) {
                try {
                    m.feature.color = m.originalColor
                } catch (e) {
                }
                m.feature = void 0
            }
        }, this.onMouseMove = function (e) {
            y.restoreHighlight();
            var t = u.scene.pick(e.endPosition);
            if (Cesium.defined(t)) if (Cesium.defined(t.getProperty)) {
                var r = t.getProperty("name");
                Cesium.defined(r) || (r = t.getProperty("id")), Cesium.defined(r) || (r = t.getProperty("ID")), r && "" != r ? (i.style.display = "block", i.style.bottom = u.canvas.clientHeight - e.endPosition.y + "px", i.style.left = e.endPosition.x + "px", i.textContent = r, t !== f.feature && (m.feature = t, Cesium.Color.clone(t.color, m.originalColor), t.color = y.colorHighlight)) : i.style.display = "none"
            } else i.style.display = "none"; else i.style.display = "none"
        }, this.onLeftClick = function (e) {
            if (Cesium.defined(f.feature)) {
                try {
                    f.feature.color = f.originalColor
                } catch (e) {
                }
                f.feature = void 0
            }
            var t = u.scene.pick(e.position);
            if (Cesium.defined(t) || !y.orginClickHandler) {
                if (f.feature !== t && (y.showTilesParts(t), Cesium.defined(t.getProperty))) {
                    (f.feature = t) === m.feature ? (Cesium.Color.clone(m.originalColor, f.originalColor), m.feature = void 0) : Cesium.Color.clone(t.color, f.originalColor), t.color = y.colorSelected;
                    var r = t.getProperty("name");
                    g.name = r, g.description = 'Loading <div class="cesium-infoBox-loading"></div>', u.selectedEntity = g;
                    var i = y.getBimAttr(t);
                    if (!i || 0 == i.length) {
                        i = [];
                        for (var o = t._content.batchTable.getPropertyNames(t._batchId), n = 0; n < o.length; n++) {
                            var a = o[n];
                            if (t.hasProperty(a)) {
                                var s = t.getProperty(a);
                                null != s && "" != s && i.push({name: a, value: s})
                            }
                        }
                    }
                    if (i && 0 != i.length) {
                        var l = '<table class="cesium-infoBox-defaultTable"><tbody>';
                        for (n = 0; n < i.length; n++) {
                            var p = i[n];
                            "group" == p.type ? l += '<tr><th colspan="2">' + p.name + "</th></tr>" : p.value && (l += "<tr><th>" + p.name + "</th><td>" + p.value + "</td></tr>")
                        }
                        if (l += "</tbody></table>", u.infoBox) g.description = l; else {
                            var c = mars3d.point.getCurrentMousePosition(u.scene, e.position);
                            setTimeout(function () {
                                u.mars.popup.show({id: "bim", popup: {html: l, anchor: [0, -15]}}, c)
                            }, 200)
                        }
                    }
                }
            } else y.orginClickHandler(e)
        };
        var e = new Cesium.ScreenSpaceEventHandler(u.scene.canvas);
        e.setInputAction(this.onMouseMove, Cesium.ScreenSpaceEventType.MOUSE_MOVE), e.setInputAction(this.onLeftClick, Cesium.ScreenSpaceEventType.LEFT_CLICK), this.handler = e
    }, setMouseOver: function (e) {
        e ? this.handler.setInputAction(this.onMouseMove, Cesium.ScreenSpaceEventType.MOUSE_MOVE) : (this.restoreHighlight(), this.nameOverlay.style.display = "none", this.handler.removeInputAction(Cesium.ScreenSpaceEventType.MOUSE_MOVE))
    }, showTilesParts: function (e) {
        if (e && Cesium.defined(e.primitive) && e.primitive._config && e.primitive._config.scenetree) {
            var t = "widgets/tilesParts/widget.js";
            if (mars3d.widget.isActivate(t)) if (mars3d.widget.getClass(t).config.layerWork == e.primitive._config._layer) return;
            mars3d.widget.activate({
                name: e.primitive._config.name + " 构件",
                uri: t,
                layerWork: e.primitive._config._layer,
                scenetree: e.primitive._config.scenetree,
                disableOhter: !1
            })
        }
    }
};