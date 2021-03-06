function initmsg(){
    window.addEventListener('message', function(event) {
        deleteAll();
        var data = event.data;
        if(data.type=="point"){
            document.getElementById("polylineDiv2").style.display="none";
            document.getElementById("polygonDiv2").style.display="none";
            if(data.data.lon!=null && data.data.lat!=null){
                var corstr="{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{\"type\":\"billboard\",\"style\":{\"image\":\"img/marker/mark1.png\"}},\"geometry\":{\"type\":\"Point\",\"coordinates\":[";
                corstr+=data.data.lon+","+data.data.lat+",0]}}]}";
                window.drawjson = corstr;
                window.name = data.data.name;
                loadOldFeatures();
            }
        }
    }, false);

    window.parent.postMessage({
        type: "create"
    }, '*')
}

function removeMask(){ $("#mask").remove() }
var viewer, drawControl;
var drawLineColor = "#55ff33";//默认值荧光绿
var objectType;
var coorType = "";     //点 线 面
function initMap() {
    var o = haoutil.system.getRequestByName("config", "config/config.json");
    mars3d.createMap({
        id: "cesiumContainer", url: o, success: function (o, t, e) {
            viewer = o, setTimeout(removeMask, 3e3),initWork()
        }
    });
}
function loadOldFeatures() {
    if(window.drawjson)
        jsonToLayer(JSON.parse(window.drawjson), !0, !0)
}
function initWork() {
    var t = viewer.scene.globe;
    $("#chkHasTerrain").change(function () {
        var o = $(this).is(":checked");
        viewer.mars.updateTerrainProvider(o)
    }),
        $("#chkTestTerrain").change(function () {
            var o = $(this).is(":checked");
            (t.depthTestAgainstTerrain = o) && toastr.info("深度监测打开后，您将无法看到地下或被地形遮挡的对象。")
        }),
        drawControl = new mars3d.Draw(viewer, {}),

        //if(window.drawjson){
        //drawControl.jsonToEntity(JSON.parse(window.drawjson), !0, !0),
        //}

        drawControl.on(mars3d.draw.event.DrawStart, function (o) {
            console.log("开始绘制")
        }),

        drawControl.on(mars3d.draw.event.DrawAddPoint, function (o) {
            console.log("绘制过程中增加了点")
        }),
        drawControl.on(mars3d.draw.event.DrawRemovePoint, function (o) {
            console.log("绘制过程中删除了点")
        }),
        drawControl.on(mars3d.draw.event.DrawCreated, function (o) {
            console.log("创建完成");
        }),
        drawControl.on(mars3d.draw.event.EditStart, function (o) { console.log("开始编辑") }),
        drawControl.on(mars3d.draw.event.EditMovePoint, function (o) { console.log("编辑修改了点") }),
        drawControl.on(mars3d.draw.event.EditRemovePoint, function (o) { console.log("编辑删除了点") }),
        drawControl.on(mars3d.draw.event.EditStop, function (o) {
            var i = o.entity;
            var n=this;
            n.stopEditing(i);
            console.log("停止编辑");
        }),
        $("#btnSave").click(function () {
            var o = toJson();
            // null != o ? (haoutil.file.downloadFile("我的标注.json", JSON.stringify(o)), document.cookie = "coorValues=" + JSON.stringify(o) + ";path=/") : (layer.msg("当前没有标注任何数据，无需保存！"), document.cookie = "coorValues=;path=/")
            window.drawjson =  JSON.stringify(o);
            console.info("window.drawjson:"+JSON.stringify(o));

            var coorArr=(o.features)[o.features.length-1].geometry.coordinates;
            //window.parent['vueDefinedMyProp'](coorArr[0],coorArr[1]);
            window.parent.postMessage({
                type:"point",
                data:{lon: coorArr[0],lat: coorArr[1]}
            }, '*')
        }),
        $("#input_plot_file").change(function (o) {
            var t = this.files[0], e = t.name; if ("json" != e.substring(e.lastIndexOf(".") + 1, e.length).toLowerCase()) return layer.msg("文件类型不合法,请选择json格式标注文件！"),
                void clearSelectFile(); if (window.FileReader) {
                var r = new FileReader; r.readAsText(t, "UTF-8"), r.onloadend = function (o) {
                    jsonToLayer(this.result), clearSelectFile()
                }
            }
        }),
        $("#btnImpFile").click(function () { $("#input_plot_file").click() }),
        //loadOldFeatures()
        onLoad()
}
function stopEditing(){
    this.viewWindow && this.viewWindow.plotEdit.stopEditing()
}
function clearSelectFile() {
    window.addEventListener ? document.getElementById("input_plot_file").value = "" : document.getElementById("input_plot_file").outerHTML += ""
}
function toJson() {
    return drawControl.toGeoJSON()
}
function jsonToLayer(o) {
    var coorArr=(o.features)[o.features.length-1].geometry.coordinates;
    var e = drawControl.jsonToEntity(o, !0, !0);
    for (var a in e) {
        var t = e[a],
            i = window.name;
        t.attribute && t.attribute.attr && t.attribute.attr.name && (i = t.attribute.attr.name),
            bindTooltip(t, i,coorArr[0],coorArr[1])
    }
    return e;//drawControl.jsonToEntity(o, !0, !0)
}
function deleteAll() {
    drawControl.clearDraw()
}
function drawPoint() {
    drawControl.startDraw({ type: "point", style: { pixelSize: 12, color: "#3388ff" } })
}
function drawMarker() {
    drawControl.startDraw({ type: "billboard", style: { image: "img/marker/mark1.png" } })
}
// function drawLabel() {
//     drawControl.startDraw({ type: "label", style: { text: "国信同科三维地球", color: "#0081c2", font_size: 50, border: !0, border_color: "#ffffff", border_width: 2 } })
// }
function drawPolyline(o) {
    drawControl.startDraw({
        type: "polyline",
        style: {
            color: drawLineColor,
            width: 3,
            clampToGround: o
        }
    })
}
function drawPolygon(o) {
    drawControl.startDraw({ type: "polygon", style: { color: drawLineColor, opacity: .5, clampToGround: o } })
}
function drawCurve(o) {
    drawControl.startDraw({ type: "curve", style: { color: drawLineColor, width: 3, clampToGround: o } })
}
function drawEllipse(o) {
    drawControl.startDraw({ type: "circle", style: { color: drawLineColor, opacity: .6, semiMinorAxis: 100, semiMajorAxis: 100, clampToGround: o } })
}
function drawRectangle(o) {
    drawControl.startDraw({ type: "rectangle", style: { color: drawLineColor, opacity: .6, clampToGround: o } })
}
$(document).ready(function () {
    mars3d.util.webglreport() || (alert("系统检测到您使用的浏览器不支持WebGL功能"), layer.open({ type: 1, title: "当前浏览器不支持WebGL功能", closeBtn: 0, shadeClose: !1, resize: !1, area: ["600px", "200px"], content: '<div style="margin: 20px;"><h3>系统检测到您使用的浏览器不支持WebGL功能！</h3>  <p>1、请您检查浏览器版本，安装使用最新版chrome、火狐或IE11以上浏览器！</p> <p>2、WebGL支持取决于GPU支持，请保证客户端电脑已安装显卡驱动程序！</p></div>' })), initMap()
})

function bindTooltip(t, e,lon,lat) {
    var a = '<table style="width:280px;">' +
        '<tr><th scope="col" colspan="4"  style="text-align:center;font-size:15px;">' + e + '</th></tr>' +
        '<tr><td >经度：</td><td >'+lon+'</td></tr>' +
        '<tr><td >纬度：</td><td >'+lat+'</td></tr>' +
        // '<tr><td colspan="4" style="text-align:right;"><a href="javascript:showXQ(\'' + e + "')\">更多</a></td></tr>" +
        "</table>";
    t.tooltip = {
        html: a,
        anchor: [0, -12]
    }
}
