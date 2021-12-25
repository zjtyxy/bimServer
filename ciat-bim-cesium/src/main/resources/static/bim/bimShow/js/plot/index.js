var viewer, request, lastWidgetItem,plotData;

function removeMask() {
    ($('#mask')).remove();
}
function initmsg(){
    var that = this;
    window.addEventListener('message', function(event) {
        var data = event.data;
         if(data.type=="plotData"){
             this.plotData = data.data
             that.activateWidget({
                 "name": "标绘",
                 "uri": "widgets/popupPlot/widget.js",
                 "plotData":this.plotData
             });
         }
         else   if(data.type=="saveAndClose"){
             window.parent.postMessage({
                 type:"save",
                 password:data.password,
                 data: this.plotData
             }, '*')
         }
        // if(data.type=="point"){
        //     document.getElementById("polylineDiv2").style.display="none";
        //     document.getElementById("polygonDiv2").style.display="none";
        //     if(data.data.lon!=null && data.data.lat!=null){
        //         var corstr="{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{\"type\":\"billboard\",\"style\":{\"image\":\"img/marker/mark1.png\"}},\"geometry\":{\"type\":\"Point\",\"coordinates\":[";
        //         corstr+=data.data.lon+","+data.data.lat+",0]}}]}";
        //         window.drawjson = corstr;
        //         window.name = data.data.name;
        //         loadOldFeatures();
        //     }
        // }
    }, false);

    window.parent.postMessage({
        type: "create"
    }, '*')
}
var map;
function initMap(options) {
    var mapOptions = mars3d.Util.merge(options, {
        scene: {
            center: { lng: 102.5, lat: 35.13135, alt: 14307887.9, heading: 360, pitch: -90 },
        },
    })
    map = new mars3d.Map('mars3dContainer', mapOptions)
}

function initWidget(qofj0120) {
    haoutil.loading.show(),
        $.ajax({
            'type': 'get',
            'dataType': 'json',
            'url': 'config/plot/widget.json',
            'timeout': 0x0,
            'success': function (result) {
                haoutil.loading.hide(), haoutil.isutil.isNotNull(request.widget) && (request.onlyStart && (result.widgetsAtStart = []), result.widgetsAtStart['push']({
                    'uri': request.widget,
                    'name': request.name || '',
                    'windowOptions': {'closeBtn': !request.onlyStart},
                    'request': request
                })),
                    mars3d.widget.init(qofj0120, result), lastWidgetItem && (activateWidget(lastWidgetItem), lastWidgetItem = null);
            },
            'error': function () {
                haoutil.loading['hide'](), haoutil.alert('config/widget.json文件加载失败！');
            }
        });
}

function initUI() {
    haoutil['oneMsg']('首次访问系统无缓存会略慢，请耐心等待！', 'load3d_tip'), $('.widget-btn')['click'](function () {
        var i7Fd0001 = $(this).attr('data-widget');
        haoutil.isutil.isNull(i7Fd0001) || (mars3d.widget.isActivate(i7Fd0001) ? mars3d['widget'].disable(i7Fd0001) : mars3d.widget.activate(i7Fd0001));

    })
}

function initWork(jx8m0001) {
    haoutil.oneMsg('如果未出现地球，是因为地形加载失败，请刷新重新加载！', 'terrain_tip'),
        jx8m0001.screenSpaceEventHandler.removeInputAction(Cesium['ScreenSpaceEventType']['LEFT_DOUBLE_CLICK']),
        jx8m0001.screenSpaceEventHandler['removeInputAction'](Cesium.ScreenSpaceEventType['LEFT_CLICK']),
        jx8m0001['scene'].screenSpaceCameraController._zoomFactor = 1.5, haoutil.system.isPCBroswer() || (jx8m0001.targetFrameRate = 0x14,
        jx8m0001.requestRenderMode = !0x0, jx8m0001.scene.fog.enable = !0x1, jx8m0001.scene.skyAtmosphere.show = !0x1,
        jx8m0001['scene'].fxaa = !0x1), (0x0 <= window.navigator.userAgent['toLowerCase']().indexOf('msie')) && (jx8m0001.targetFrameRate = 0x14, jx8m0001.requestRenderMode = !0x0),
    jx8m0001.sceneModePicker && (jx8m0001.sceneModePicker.viewModel.duration = 0x0), bindShowTilesParts()

}

function bindShowTilesParts() {
    new Cesium[('ScreenSpaceEventHandler')](viewer.scene.canvas).setInputAction(function (eZgl0021) {
        var e1Xv0001 = viewer.scene.pick(eZgl0021.position);
        if (e1Xv0001 && Cesium.defined(e1Xv0001.primitive) && e1Xv0001.primitive['_config'] && e1Xv0001.primitive._config['scenetree']) {
            var i8nb0012 = 'widgets/tilesParts/widget.js';
            if (mars3d.widget['isActivate'](i8nb0012)) if (mars3d.widget.getClass(i8nb0012).config.layerCfg == e1Xv0001.primitive._config) return;
            mars3d.widget.activate({
                'name': e1Xv0001.primitive._config.name + '\x20构件',
                'uri': i8nb0012,
                'layerCfg': e1Xv0001.primitive._config,
                'disableOther': !0x1
            });
        }
    }, Cesium.ScreenSpaceEventType.LEFT_CLICK);
}

function bindToLayerControl(g3tr0000) {
    var enfR0120 = mars3d['widget'].getClass('widgets/manageLayers/widget.js');
    return enfR0120 && enfR0120.addOverlay(g3tr0000), viewer['mars'].addOperationalLayer(g3tr0000);
}

function unbindLayerControl(fJdf0023) {
    viewer.mars.removeOperationalLayer(fJdf0023.config['id']);
    var _0xa0ecc = mars3d.widget.getClass('widgets/manageLayers/widget.js');
    _0xa0ecc && _0xa0ecc.removeLayer(fJdf0023['config']['name']);
}

function activateWidget(jfne0001) {
    viewer ? mars3d['widget'].activate(jfne0001) : lastWidgetItem = jfne0001;
}

function disableWidget(pneE0120) {
    mars3d.widget.disable(pneE0120);
}

function activateFunByMenu(g71H0000) {
    eval(g71H0000);
}

function showMapLayer(hHud0000) {
    mars3d.widget.disableAll(), $('#menu1Child').hide();
    var hZJC0000 = viewer.mars.getLayer(hHud0000, 'id');
    hZJC0000 && hZJC0000['setVisible'](!hZJC0000['_visible']);
}

function goHome() {
    mars3d.widget['disableAll'](), viewer.mars.centerAt();
}

$(document).ready(function () {
    initMap();
});
