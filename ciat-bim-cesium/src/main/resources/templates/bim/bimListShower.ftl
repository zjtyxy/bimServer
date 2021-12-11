<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,user-scalable=0,minimum-scale=1.0,maximum-scale=1.0"/>
    <meta name="author" content=" http://mars3d.cn ">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta name="x5-fullscreen" content="true">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
    <!-- 标题及搜索关键字 -->


    <!--第三方lib-->
    <script type="text/javascript" src="../lib/include-lib.js" libpath="../lib/"
            include="jquery,jquery.range,font-awesome,bootstrap,bootstrap-checkbox,layer,haoutil,turf,mars3d"></script>

    <link href="css/style.css" rel="stylesheet"/>
    <style>
        .mars3d-camera-content {
            /* 防止下面的line一直随img动画 */
            height: 30px;
        }

        .mars3d-camera-img {
            width: 30px;
            height: 30px;
            animation: cameraMove 1s linear infinite alternate;
            -webkit-animation: cameraMove 1s linear infinite alternate;
        }

        @keyframes cameraMove {
            from {
                margin-top: 20px;
            }
            to {
                margin-top: 0px;
            }
        }

        @-webkit-keyframes cameraMove {
            from {
                margin-top: 20px;
            }
            to {
                margin-top: 0px;
            }
        }

        .mars3d-camera-line {
            height: 150px;
            width: 5px;
            margin-top: 20px;
            border-left: 3px dashed #5b8fee;
            margin-left: calc(50% - 1px);
        }

        .mars3d-camera-point {
            border-radius: 50%;
            width: 8px;
            height: 8px;
            margin-left: calc(50% - 3px);
            background-color: #5b8fee;
        }
    </style>
</head>

<body class="dark">
<!--加载前进行操作提示，优化用户体验-->
<div id="mask" class="signmask" onclick="removeMask()"></div>

<div id="mars3dContainer" class="mars3d-container"></div>


<!-- 面板 -->
<div class="infoview">
    <table class="mars-table">
        <tr class="undergroundAttr">
            <td>地下模式：</td>
            <td>
                <div class="checkbox checkbox-primary checkbox-inline">
                    <input id="chkUnderground" class="styled" type="checkbox">
                    <label for="chkUnderground">
                        开启/关闭
                    </label>
                </div>
            </td>
            <td>地表透明度：</td>
            <td colspan="2">
                <input id="alpha" type="range" min="0.0" max="1.0" step="0.1" value='0.5'>
            </td>
        </tr>

        <tr class="undergroundAttr">
            <td>地下开挖：</td>
            <td>
                <div class="checkbox checkbox-primary checkbox-inline">
                    <input id="chkClippingPlanes" class="styled" type="checkbox" checked>
                    <label for="chkClippingPlanes">
                        是否挖地
                    </label>
                </div>
            </td>
            <td>开挖深度：</td>
            <td>
                <input id="txtHeight" type="number" value="30" min="-500" step="1" max="999" class="form-control"
                       style="width: 100px;"/>（米）

            </td>
            <td>
                <input type="button" class="btn btn-primary" value="矩形" id="btnDrawExtent"/>
                <input type="button" class="btn btn-primary" value="多边形" id="btnDraw"/>
                <input type="button" class="btn btn-primary" value="清除" id="clearWJ"/>
            </td>
        </tr>


    </table>

</div>


<script src="./js/common.js"></script>
<script src="js/bimangle-latest.js"></script>
<script type="text/javascript">
    'use script' //开发环境建议开启严格模式
    function addGraphic(graphicLayer, position, url) {
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
            popup: `<video src='` + url + `' controls autoplay style="width: 600px;" ></video>`,
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

    var map

    function initMap(options) {
        //合并属性参数，可覆盖config.json中的对应配置
        var mapOptions = mars3d.Util.merge(options, {
            scene: {
                center: {"lat": 31.97989, "lng": 118.700724, "alt": 305, "heading": 348, "pitch": -25},
                baseColor: 'rgba(0,0,0.0,0.5)',
                globe: {
                    depthTestAgainstTerrain: true,
                },
                highDynamicRange: true,
            },
        })

        var bimlist ={};
        //创建三维地球场景
        map = new mars3d.Map('mars3dContainer', mapOptions)
        <#list bimModelList as item>
        loadTileset(map, '${item.name}', '${item.url}','${item.position}', ${item.infoCount});
        </#list>

        // var graphicLayer1 = new mars3d.layer.DivLayer();
        // map.addLayer(graphicLayer1)
        // addGraphic(graphicLayer1, [106.264355, 30.003115, 192.7], 'http://localhost/data/video/shigong1.mp4')
        // addGraphic(graphicLayer1, [106.264442, 30.00344, 199.3], 'http://localhost/data/video/shigong2.mp4')
        // addGraphic(graphicLayer1, [106.264765, 30.00276, 186.4], 'http://localhost/data/video/shigong3.mp4')
        // addGraphic(graphicLayer1, [106.264902, 30.002514, 186.4], 'http://localhost/data/video/shigong4.mp4')

        //地下模式
        var underground = new mars3d.thing.Underground({
            alpha: Number($('#alpha').val()),
            enabled: true,
        })
        map.addThing(underground)

        $('#chkUnderground').change(function () {
            var val = $(this).is(':checked')

            underground.enabled = val
        })

        $('#alpha').range({
            onChange: function (value, bfb) {
                underground.alpha = value
            },
        })

        // 挖地区域
        var terrainClip = new mars3d.thing.TerrainClip({
            positions: [
                [117.096176, 31.851189, 42.56],
                [117.097776, 31.851189, 42.56],
                [117.097776, 31.853494, 42.56],
                [117.096176, 31.853494, 42.56],
            ],
            diffHeight: Number($('#txtHeight').val()), //高度
            image: './img/textures/excavate_side_min.jpg',
            imageBottom: './img/textures/excavate_bottom_min.jpg',
            splitNum: 80, //井边界插值数
        })
        map.addThing(terrainClip)

        var terrainPlanClip = new mars3d.thing.TerrainPlanClip({
            positions: [
                [117.096176, 31.851189, 42.56],
                [117.097776, 31.851189, 42.56],
                [117.097776, 31.853494, 42.56],
                [117.096176, 31.853494, 42.56],
            ],
        })
        map.addThing(terrainPlanClip)

        $('#chkTestTerrain').change(function () {
            var val = $(this).is(':checked')
            map.scene.globe.depthTestAgainstTerrain = val
        })

        $('#chkClippingPlanes').change(function () {
            var val = $(this).is(':checked')
            terrainClip.enabled = val
            terrainPlanClip.enabled = val
        })

        $('#txtHeight').change(function () {
            var num = Number($(this).val())
            terrainClip.height = num
        })

        $('#clearWJ').click(function () {
            terrainClip.clear() //清除挖地区域
            terrainPlanClip.clear()
        })

        $('#btnDraw').click(function () {
            terrainClip.clear() //清除挖地区域
            terrainPlanClip.clear()

            map.graphicLayer.startDraw({
                type: 'polygon',
                style: {
                    color: '#007be6',
                    opacity: 0.5,
                    clampToGround: true,
                },
                success: function (graphic) {
                    //绘制成功后回调
                    var positions = graphic.positionsShow
                    map.graphicLayer.clear()

                    console.log(JSON.stringify(mars3d.PointTrans.cartesians2lonlats(positions))) //打印下边界

                    //挖地区域
                    terrainClip.positions = positions
                    terrainPlanClip.positions = positions
                },
            })
        })

        $('#btnDrawExtent').click(function () {
            terrainClip.clear() //清除挖地区域
            terrainPlanClip.clear()

            map.graphicLayer.startDraw({
                type: 'rectangle',
                style: {
                    color: '#007be6',
                    opacity: 0.8,
                    outline: false,
                },
                success: function (graphic) {
                    //绘制成功后回调
                    var positions = graphic.getOutlinePositions(false)
                    map.graphicLayer.clear()

                    console.log(JSON.stringify(mars3d.PointTrans.cartesians2lonlats(positions))) //打印下边界

                    //挖地区域
                    terrainClip.positions = positions
                    terrainPlanClip.positions = positions
                },
            })
        })

        $('#btnClearClip').click(function () {
            tilesetPlanClip.clear()
        })

        //滑动条
        $('#rangeDistance').range({
            onChange: function (value, bfb) {
                tilesetPlanClip.distance = value
            },
        })

        $('#btnClipTop').click(function () {
            tilesetPlanClip.type = mars3d.thing.TilesetPlanClip.Type.ZR
        })
        $('#btnClipBottom').click(function () {
            tilesetPlanClip.type = mars3d.thing.TilesetPlanClip.Type.Z
        })

        $('#btnClipPoly').click(function () {
            tilesetPlanClip.clear()

            map.graphicLayer.startDraw({
                type: 'polygon',
                style: {
                    color: '#007be6',
                    opacity: 0.5,
                },
                success: function (graphic) {
                    //绘制成功后回调
                    var positions = graphic.positionsShow
                    map.graphicLayer.clear()

                    console.log(JSON.stringify(mars3d.PointTrans.cartesians2lonlats(positions))) //打印下边界

                    tilesetPlanClip.positions = positions
                },
            })
        })

        $('#btnClipPoly2').click(function () {
            tilesetPlanClip.clear()

            map.graphicLayer.startDraw({
                type: 'polygon',
                style: {
                    color: '#007be6',
                    opacity: 0.5,
                    clampToGround: true,
                },
                success: function (graphic) {
                    //绘制成功后回调
                    var positions = graphic.positionsShow
                    map.graphicLayer.clear()

                    tilesetPlanClip.clipOutSide = true
                    tilesetPlanClip.positions = positions
                },
            })
        })

        $('#btnClipLine').click(function () {
            tilesetPlanClip.clear()

            map.graphicLayer.startDraw({
                type: 'polyline',
                maxPointNum: 2,
                style: {
                    color: '#007be6',
                    opacity: 0.8,
                    outline: false,
                },
                success: function (graphic) {
                    //绘制成功后回调
                    var positions = graphic.positionsShow
                    map.graphicLayer.clear()

                    tilesetPlanClip.positions = positions
                },
            })
        })
    }
</script>


</body>

</html>
