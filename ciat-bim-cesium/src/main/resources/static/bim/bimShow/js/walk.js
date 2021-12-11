'use script' //开发环境建议开启严格模式

var map
var roamLine

function initMap(options) {
    //合并属性参数，可覆盖config.json中的对应配置
    var mapOptions = mars3d.Util.merge(options, {
        control: {
            animation: true, //是否创建动画小器件，左下角仪表
            timeline: true, //是否显示时间线控件
        },
    })

    //创建三维地球场景
    map = new mars3d.Map('mars3dContainer', mapOptions)

    //创建矢量数据图层
    var graphicLayer = new mars3d.layer.GraphicLayer()
    map.addLayer(graphicLayer)

    //该数据可以从 基础项目 飞行漫游功能界面操作后单个路线的 保存JSON
    var flydata = {
        name: '步行路线',
        speed: 40,
        positions: [
            [117.220356, 31.833959, 43.67],
            [117.220361, 31.835111, 44.36],
            [117.213242, 31.835863, 42.31],
            [117.211926, 31.835738, 42.14],
            [117.183103, 31.833906, 47.17],
            [117.183136, 31.833586, 47.39],
            [117.183968, 31.833637, 47.05],
            [117.184038, 31.833134, 47.39],
            [117.184364, 31.833142, 47.26],
            [117.184519, 31.833375, 47.04],
        ],
        model: {
            show: true,
            url: '//data.mars3d.cn/gltf/mars/man/walk.gltf',
            scale: 5,
            minimumPixelSize: 50,
        },
        shadow: [
            {
                type: 'circle',
                radius: 10,
                color: '#ffff00',
                opacity: 0.3,
                materialType: mars3d.MaterialType.CircleWave,
                speed: 10,
                count: 3,
                gradient: 0.1,
                show: true,
            },
        ],
        camera: {
            type: 'gs',
            radius: 300,
        },
        clockRange: Cesium.ClockRange.CLAMPED, //CLAMPED到达终点后停止
        pauseTime: 0.5,
    }

    roamLine = new mars3d.graphic.RoamLine(flydata)
    graphicLayer.addGraphic(roamLine)

    roamLine.on(mars3d.EventType.endItem, function (event) {
        console.log('已漫游过点：' + event.index, event)
    })
    roamLine.on(mars3d.EventType.end, function (event) {
        console.log('漫游结束', event)
    })

    roamLine.on(mars3d.EventType.change, (event) => {
        //面板显示相关信息
        showRealTimeInfo(event)
    })

    roamLine.bindPopup(
        function (event) {
            var params = event?.target?.info
            if (!params) {
                return '即将开始漫游'
            }
            let roamLine = event.target

            var html =
                '<div style="width:200px;">' +
                '总距离：' +
                haoutil.str.formatLength(roamLine.alllen) +
                '<br/>' +
                '总时间：' +
                haoutil.str.formatTime(roamLine.alltimes / map.clock.multiplier) +
                '<br/>' +
                '开始时间：' +
                Cesium.JulianDate.toDate(roamLine.startTime).format('yyyy-M-d HH:mm:ss') +
                '<br/>' +
                '剩余时间：' +
                haoutil.str.formatTime((roamLine.alltimes - params.time) / map.clock.multiplier) +
                '<br/>' +
                '剩余距离：' +
                haoutil.str.formatLength(roamLine.alllen - params.len) +
                ' <br/>' +
                '</div>'
            return html
        },
        {
            anchor: [0, -20], //左右、上下的偏移像素值。
            timeRender: true, //实时更新html
        }
    )

    //不贴地时，直接开始
    startFly()

    //贴地时，异步计算完成后开始
    // roamLine.clampToGround(function (e) {//异步计算完成贴地后再启动
    //     //贴地后的路线值为flyLine.positions
    //     startFly()
    // });
}

function startFly() {
    //启动漫游
    roamLine.start()

    //显示popup
    roamLine.openPopup()

    //显示基本信息，名称、总长、总时间
    $('#td_alltimes').html(haoutil.str.formatTime(roamLine.alltimes))
    $('#td_alllength').html(haoutil.str.formatLength(roamLine.alllen))
}

//显示实时坐标和时间
function showRealTimeInfo(params) {
    if (params == null) {
        return
    }

    var val = Math.ceil((params.time * 100) / roamLine.alltimes)
    if (val < 1) {
        val = 1
    }
    if (val > 100) {
        val = 100
    }

    $('.progress-bar')
        .css('width', val + '%')
        .attr('aria-valuenow', val)
        .html(val + '%')

    $('#td_jd').html(params.lng)
    $('#td_wd').html(params.lat)
    $('#td_gd').html(haoutil.str.formatLength(params.alt))

    $('#td_times').html(haoutil.str.formatTime(params.time))
    $('#td_length').html(haoutil.str.formatLength(params.len))

    if (params.hbgd) {
        $('#td_dmhb').html(haoutil.str.formatLength(params.hbgd))
    } else {
        $('#td_dmhb').html('未知')
    }

    if (params.ldgd) {
        $('#td_ldgd').html(haoutil.str.formatLength(params.ldgd))
    } else {
        $('#td_ldgd').html('未知')
    }
}
