/**
 * 弹出标绘
 */
mars3d.widget.bindClass(mars3d.widget.BaseWidget.extend({
    'options': {
        'resources': ['../../../lib/dom2img/dom-to-image.js'],
        'view': {
            'type': 'window',
            'url': 'view.html',
            'windowOptions': {'width': 0xfa, 'position': {'top': 0x32, 'right': 0x5, 'bottom': 0x5}}
        }
    }, drawControl: null, getServerURL: function () {
        return this.viewer.mars.config.serverURL;
    }, 'create': function () {
        var evrs0100 = this;
        this.drawControl = new mars3d[('Draw')](this.viewer, {
            'hasEdit': !0x1,
            'nameTooltip': !0x0
        }), this['drawControl']['on'](mars3d.draw.event.DrawCreated, function (peyc0201) {
            evrs0100['startEditing'](peyc0201.entity);
        }), this['drawControl']['on'](mars3d.draw.event.EditStart, function (eM4D0000) {
            evrs0100.startEditing(eM4D0000.entity);
        }), this.drawControl['on'](mars3d.draw.event['EditMovePoint'], function (gRDj0001) {
            evrs0100.startEditing(gRDj0001.entity);
        }), this.drawControl['on'](mars3d.draw.event['EditRemovePoint'], function (iLkQ0000) {
            evrs0100['startEditing'](iLkQ0000.entity);
        }), this.drawControl.on(mars3d.draw.event.EditStop, function (eF2Q0000) {
            evrs0100.stopEditing(eF2Q0000.entity), evrs0100.sendSaveEntity(eF2Q0000.entity), evrs0100.showTable();
        }), this.drawControl['on'](mars3d.draw.event['Delete'], function (ma4i0200) {
            evrs0100.sendDeleteEntity(ma4i0200.entity), evrs0100.showTable();
        }), window.bindToLayerControl && (this['layerWork'] = bindToLayerControl({
            'pid': 0x0, 'name': '标绘', 'visible': !0x0, 'onAdd': function () {
                evrs0100['drawControl'].setVisible(!0x0);
            }, 'onRemove': function () {
                evrs0100['drawControl']['setVisible'](!0x1);
            }, 'onCenterAt': function (fkat0001) {
                var hiry0010 = evrs0100.drawControl.getEntitys();
                evrs0100.viewer['flyTo'](hiry0010, {'duration': fkat0001});
            }, 'compare': {
                'onCreate': function (sgxy0010) {
                    this.drawControlEx = new mars3d[('Draw')](sgxy0010, {
                        'hasEdit': !0x1,
                        'nameTooltip': !0x0
                    });
                }, onAdd: function () {
                    this['drawControlEx']['loadJson'](evrs0100.getGeoJson()), this.drawControlEx.setVisible(!0x0);
                }, onRemove: function () {
                    this.drawControlEx.setVisible(!0x1);

                }
            }
        })), this.sendGetList();
    }, 'viewWindow': null, 'winCreateOK': function (eZzl0001, fPog0001) {
        this.viewWindow = fPog0001;
    }, 'activate': function () {
        this.drawControl['hasEdit'](!0x0);
    }, 'disable': function () {
        this.stopEditing(), this.viewWindow = null, this['drawControl'].stopDraw(), this['drawControl'].hasEdit(!0x1);
    }, 'getDefStyle': function (fuas0100) {
        return mars3d['draw'].util['getDefStyle'](fuas0100);
    }, 'startDraw': function (hPYz0000) {
        var iwcG0030 = {'IbpBR': 'model', 'onljD': '模型根据其大小需要一点加载时间，请稍等片刻。'};
        hPYz0000 && iwcG0030['IbpBR'] === hPYz0000.type && haoutil.msg(iwcG0030['onljD']), this.drawControl.startDraw(hPYz0000);
    }, 'endDraw': function () {
        this.drawControl.endDraw();
    }, 'startEditingById': function (gjt30000) {
        var fgRv0000 = {
            'MOCbY': function (eLL60000, enoo0101) {
                return eLL60000 != enoo0101;
            }
        };
        var hcUq0100 = this['drawControl'].getEntityById(gjt30000);
        (null != hcUq0100) && (this['viewer'].mars['flyTo'](hcUq0100), hcUq0100.startEditing());
    }, 'startEditing': function (e29s0001) {

        var kzmi0011 = this;
        if (null != this['viewWindow']) {
            var fgna0010 = this.drawControl['getCoordinates'](e29s0001);
            clearTimeout(this.timeTik);
            var jyNw0001 = mars3d.widget.getClass('widgets/plotAttr/widget.js');
            jyNw0001 && jyNw0001.isActivate ? jyNw0001.startEditing(e29s0001, fgna0010) : mars3d.widget['activate']({
                'uri': 'widgets/plotAttr/widget.js',
                'entity': e29s0001,
                'lonlats': fgna0010,
                'deleteEntity': function (iifj0001) {
                    kzmi0011['deleteCurrentEntity'](iifj0001);
                },
                'updateAttr': function (g8VX0000) {
                    kzmi0011.updateAttr2map(g8VX0000);
                },
                'updateGeo': function (ftmf0002) {
                    kzmi0011.updateGeo2map(ftmf0002);
                },
                'centerAt': function (ijWr0100) {
                    kzmi0011.centerCurrentEntity(ijWr0100);
                }
            });
        }
    }, stopEditing: function () {
        this.timeTik = setTimeout(function () {
            mars3d['widget'].disable('widgets/plotAttr/widget.js');
        }, 0xc8);
    }, updateAttr2map: function (hJu00000) {
        this.drawControl.updateAttribute(hJu00000);
    }, updateGeo2map: function (hy0t0101) {
        if (this.drawControl['setPositions'](hy0t0101), hy0t0101['length'] <= 0x3) {
            var pars0201 = this.drawControl['getCurrentEntity']();
            this.centerCurrentEntity(pars0201);
        }
        return hy0t0101;
    }, centerCurrentEntity: function (iGFf0003) {
        this.viewer.mars['flyTo'](iGFf0003, {'scale': 0.5, 'radius': 0x3e8});
    }, getGeoJson: function () {
        return this.drawControl.toGeoJSON();
    }, getCurrentEntityGeoJson: function () {
        var iLcS0030 = this.drawControl['getCurrentEntity']();
        if (null != iLcS0030) return this['drawControl'].toGeoJSON(iLcS0030);
    }, jsonToLayer: function (icA10200, h24E0000, ggz30010) {
        if (null != icA10200) return this['showTable'](icA10200), this['drawControl'].loadJson(icA10200, {
            'clear': h24E0000,
            'flyTo': ggz30010
        });
    }, deleteAll: function () {
        this.drawControl.deleteAll(), this.sendDeleteAll(), this['showTable']();
    }, deleteEntity: function (fcar0230) {
        var iyac0143 = this['drawControl'].getEntityById(fcar0230);
        (null != iyac0143) && this.drawControl.deleteEntity(iyac0143);
    }, isOnDraw: !0x1, 'deleteCurrentEntity': function (fPRw0000) {
        null != (fPRw0000 = fPRw0000 || this.drawControl['getCurrentEntity']()) && this.drawControl.deleteEntity(fPRw0000);
    }, hasEdit: function (fg5g0200) {
        this.drawControl['hasEdit'](fg5g0200);
    }, query: function (h6yz0000, ioAP0100) {
        for (var eacU0310 = this['drawControl']['getEntitys'](), pgv80210 = [], eKFg0000 = 0x0, e9rS0000 = 0x0; (e9rS0000 < eacU0310.length); e9rS0000++) {

            var fodQ0130, iRlq0011 = eacU0310[e9rS0000];
            if (('label' === iRlq0011.attribute.type) ? fodQ0130 = iRlq0011.attribute.style.text : iRlq0011.attribute.attr && (fodQ0130 = iRlq0011['attribute'].attr.name),
            null != fodQ0130 && (-0x1 != fodQ0130.indexOf(h6yz0000)) && (pgv80210.push({
                'name': fodQ0130,
                'type': '标绘 - ' + iRlq0011['attribute']['name'],
                '_datatype': 'plot',
                '_entity': iRlq0011
            }), ioAP0100 && (ioAP0100 < ++eKFg0000))) break;

        }
        return pgv80210;
    }, last_window_param: null, 'showEditAttrWindow': function (gdju0210) {
        this.last_window_param = gdju0210;
    }, saveWindowEdit: function (fOCx0000) {
        this.viewWindow['plotEdit'].updateAttr(this.last_window_param.parname, this.last_window_param.attrName, fOCx0000), layer['close'](layer['index']);
    }, showTable: function (etd60130) {
        var iQ2d0000 = [];
        if ((etd60130 = etd60130 || this.getGeoJson()) && etd60130.features) for (var hMee0002 = 0x0, eFPx0000 = etd60130.features.length; (hMee0002 < eFPx0000); hMee0002++) {
            var eio70010 = etd60130.features[hMee0002];
            iQ2d0000.push({
                'index': hMee0002,
                'name': eio70010.properties['attr'].name || eio70010.properties['name'],
                'type': eio70010.properties.type
            });
        }
        this['arrList'] = iQ2d0000, this.viewWindow && this.viewWindow.tableWork.loadData(iQ2d0000);
    }, showTableItem: function (hpfp0131) {
        var hbqm0311 = this.drawControl.getEntitys()[hpfp0131.index];
        this.viewer.mars.flyTo(hbqm0311);
    }, delTableItem: function (iJXf0003) {
        var juGh0000 = this.drawControl.getEntitys()[iJXf0003.index];
        this.drawControl.deleteEntity(juGh0000);
    }, 'storageName': 'marsgis_plot', 'sendGetList': function () {
        if(this.config.plotData!='')
        this.jsonToLayer(this.config.plotData, !0x0, !0x0);
    }, sendSaveEntity: function (jdX70000) {
        if (null != this.viewWindow) if (this.isOnDraw) this.isOnDraw = !0x1;
        window.plotData = JSON.stringify(this.getGeoJson());
       // haoutil.storage.add(this['storageName'], eyfc0103);
    }, sendDeleteEntity: function (ecfk0331) {
        var hVrf0003 = JSON.stringify(this.getGeoJson());
        haoutil['storage'].add(this.storageName, hVrf0003);

    }, sendDeleteAll: function () {
        haoutil.storage.del(this.storageName);
    }
}));
