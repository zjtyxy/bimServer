'use script' //开发环境建议开启严格模式
var map

function initMap(options) {
    //合并属性参数，可覆盖config.json中的对应配置
    var mapOptions = mars3d.Util.merge(options, {
        scene: {
            center: { lat: 31.832696, lng: 117.221018, alt: 183, heading: 355, pitch: -48 },
        },
    })

    //创建三维地球场景
    map = new mars3d.Map('mars3dContainer', mapOptions)

    let tiles3dLayer = new mars3d.layer.TilesetLayer({
        url: '../data/huxing/max-fcfh/tileset.json',
        maximumScreenSpaceError: 1,
    })
    map.addLayer(tiles3dLayer)

    //单击事件
    tiles3dLayer.on(mars3d.EventType.click, function (event) {
        console.log('单击了3dtiles图层', event)

        // tiles3dLayer.tileset._selectedTiles[0].transform = Cesium.Matrix4.fromTranslation(new Cesium.Cartesian3(100, 0, 0))
        // Cesium.Matrix4.multiply(temp1.transform, Cesium.Matrix4.fromTranslation(new Cesium.Cartesian3(100, 0, 0)), temp1.transform)

        let attr = event.graphic?.attr
        if (attr) {
            tiles3dLayer.style = new Cesium.Cesium3DTileStyle({
                color: {
                    conditions: [
                        ["${name} ==='" + attr.name + "'", 'rgb(255, 255, 255)'],
                        ['true', 'rgba(255, 255, 255,0.03)'],
                    ],
                },
            })
        }
    })

    map.on(mars3d.EventType.clickMap, function (event) {
        tiles3dLayer.style = undefined
    })
}
