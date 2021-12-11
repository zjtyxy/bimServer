'use script' //开发环境建议开启严格模式


var map;
var tiles3dLayer;

function initMap(options) {
    //合并属性参数，可覆盖config.json中的对应配置
    var mapOptions = mars3d.Util.merge(options, {
        scene: {
            center: {"lat":31.972623,"lng":118.702054,"alt":305,"heading":348,"pitch":-25},
        },
    })
    //创建三维地球场景
    map = new mars3d.Map('mars3dContainer', mapOptions)
    //118.700724, 纬度:31.97989
    var center =  {lng: 118.699194, lat: 31.978852, alt: 0.6 }
    addBuild( modelurl,center,"南京首创")
    //创建DIV数据图层
    var graphicLayer = new mars3d.layer.DivLayer()
    map.addLayer(graphicLayer)
    //添加数据
    addGraphic(graphicLayer, [118.699537, 31.978644, 10.5],'http://localhost/data/video/shigong1.mp4')
    addGraphic(graphicLayer, [118.702686, 31.978999, 6.4],'http://localhost/data/video/shigong2.mp4')
    addGraphic(graphicLayer, [118.700841, 31.979906, 6.8],'http://localhost/data/video/shigong3.mp4')
    addGraphic(graphicLayer, [118.702036, 31.980237, 6.2],'http://localhost/data/video/shigong4.mp4')
}

function addGraphic(graphicLayer, position,url) {
    var graphicImg = new mars3d.graphic.DivGraphic({
        position: position,
        style: {
            html: ` <div class="mars3d-camera-content">
                      <img class="mars3d-camera-img" src="img/marker/camera.svg" >
                    </div>
                    <div class="mars3d-camera-line" ></div>
                    <div class="mars3d-camera-point"></div>
                  `,
            offsetX: -16,
            distanceDisplayCondition: new Cesium.DistanceDisplayCondition(0, 100000),
        },
        popup: `<video src='`+url+`' controls autoplay style="width: 600px;" ></video>`,
        popupOptions: {
            offsetY: -170, //显示Popup的偏移值，是DivGraphic本身的像素高度值
            template: `<div class="marsBlackPanel animation-spaceInDown">
                        <div class="marsBlackPanel-text">{content}</div>
                        <span class="mars3d-popup-close-button closeButton" >×</span>
                      </div>`,
            horizontalOrigin: Cesium.HorizontalOrigin.LEFT,
            verticalOrigin: Cesium.VerticalOrigin.CENTER,
        },
    });
    graphicLayer.addGraphic(graphicImg);
}
function showCengByStyle(ceng) {
    var con =  [
    ];
    for(var i=1;i<=ceng;i++)
    {
        if(i<10)
        con.push( ["${标高} ==='F0" + i + "' || ${底部约束} ==='F0" + i + "'", 'rgb(255, 255, 255)'])
        else
            con.push( ["${标高} ==='F" + i + "' || ${底部约束} ==='F" + i + "'", 'rgb(255, 255, 255)'])
    }
    con.push(['true', 'rgba(255, 255, 255,0)'])
    tiles3dLayer.style = new Cesium.Cesium3DTileStyle({
        color: {
            conditions: con,
        },
    })
}
function addBuild(url,postion1,name1) {
    var tiles3dLaye = new mars3d.layer.TilesetLayer({
        name:name1,
        url,
        position: postion1,
        maximumScreenSpaceError: 1,
        maximumMemoryUsage: 1024,
        showClickFeature: true,
        popup: 'all',
    })
    //"lat":31.974737,"lng":118.70385
    map.addLayer(tiles3dLaye)
    attachTileset(map.viewer, tiles3dLaye.tileset);
    tiles3dLayer = tiles3dLaye;
    $('#btnShowAll').click(function () {
        tiles3dLayer.style = undefined
    })
    $('#btnShowD1').click(function () {
        showCengByStyle(1)
    })
    $('#btnShowF1').click(function () {
        showCengByStyle(3)
    })

    $('#btnShowF2').click(function () {
        showCengByStyle(5)
    })
    $('#btnShowF3').click(function () {
        showCengByStyle(7)
    })

    $('#btnShowF4').click(function () {

        showCengByStyle(10)
    })

    $('#btnShowF5').click(function () {
        showCengByStyle(15)
    })
}
