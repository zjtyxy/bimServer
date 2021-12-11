/* 2019-3-18 15:39:27 | 版权所有 广西中遥 http://91daocao.com   */
!function () {
    for (var o, s = new RegExp("(^|(.*?\\/))(include-lib.js)(\\?|$)"), e = document.getElementsByTagName("script"), t = 0; t < e.length; t++) {
        var r = e[t].getAttribute("src");
        if (r) if (r.match(s)) {
            o = e[t];
            break
        }
    }
    var  m = new RegExp("\\.css");

    function n(s) {
        if (null != s && 0 != s.length) for (var e = 0, t = s.length; e < t; e++) {
           var r = s[e];
            if (m.test(r)) {
                var a = '<link rel="stylesheet" href="' + r + '">';
                document.writeln(a)
            } else {
                var o = '<script type="text/javascript" src="' + r + '"><\/script>';
                document.writeln(o)
            }
        }
    }

    !function () {
        var s = (o.getAttribute("include") || "").split(","), e = o.getAttribute("libpath") || "";
        e.lastIndexOf("/") != e.length - 1 && (e += "/");
        var t = {
            jquery: [e + "jquery/jquery-2.1.4.min.js",e+"base64/base64.js"],
            "jquery.scrollTo": [e + "jquery/scrollTo/jquery.scrollTo.min.js"],
            "jquery.minicolors": [e + "jquery/minicolors/jquery.minicolors.css", e + "jquery/minicolors/jquery.minicolors.min.js"],
            "jquery.range": [e + "jquery/range/range.css", e + "jquery/range/range.js"],
            ztree: [e + "jquery/ztree/css/zTreeStyle/zTreeStyle.css", e + "jquery/ztree/css/mars/ztree-mars.css", e + "jquery/ztree/js/jquery.ztree.all.min.js"],
            jstree: [e + "jstree/themes/default-dark/style.css", e + "jstree/jstree.min.js"],
            "jquery.mCustomScrollbar": [e + "jquery/mCustomScrollbar/jquery.mCustomScrollbar.css", e + "jquery/mCustomScrollbar/jquery.mCustomScrollbar.js"],
            jedate: [e + "jquery/jedate/skin/jedate.css", e + "jquery/jedate/jedate.js"],
            lazyload: [e + "jquery/lazyload/jquery.lazyload.min.js"],
            "font-awesome": [e + "fonts/font-awesome/css/font-awesome.min.css"],
            "font-marsgis":[e+"fonts/ciatgis/iconfont.css"],
            "web-icons": [e + "fonts/web-icons/web-icons.css"],
            animate: [e + "animate/animate.css"],
            admui: [e + "admui/css/index.css", e + "admui/js/global/core.js", e + "admui/js/global/configs/site-configs.js", e + "admui/js/global/components.js"],
            "admui-frame": [e + "admui/css/site.css", e + "admui/js/app.js"],
            bootstrap: [e + "bootstrap/bootstrap.css", e + "bootstrap/bootstrap.min.js"],
            "bootstrap-table": [e + "bootstrap/bootstrap-table/bootstrap-table.css", e + "bootstrap/bootstrap-table/bootstrap-table.min.js", e + "bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"],
            "bootstrap-select": [e + "bootstrap/bootstrap-select/bootstrap-select.css", e + "bootstrap/bootstrap-select/bootstrap-select.min.js"],
            "bootstrap-checkbox": [e + "bootstrap/bootstrap-checkbox/awesome-bootstrap-checkbox.css"],
            nprogress: [e + "nprogress/nprogress.css", e + "nprogress/nprogress.min.js"],
            toastr: [e + "toastr/toastr.css", e + "toastr/toastr.js"],
            formvalidation: [e + "formValidation/formValidation.css", e + "formValidation/formValidation.min.js", e + "formValidation/framework/bootstrap.min.js", e + "formValidation/language/zh_CN.min.js"],
            "admin-lte": [e + "fonts/font-awesome/css/font-awesome.min.css", e + "admin-lte/css/AdminLTE.min.css", e + "admin-lte/css/skins/skin-blue.min.css", e + "admin-lte/js/adminlte.min.js"],
            ace: [e + "ace/ace.js"],
            layer: [e + "layer/theme/default/layer.css", e + "layer/theme/retina/retina.css", e + "layer/theme/mars/layer.css", e + "layer/layer.js"],
          //  mylayer: [ e + "layer/layer.js"],
            haoutil: [e + "hao/haoutil.js"],
            echarts: [e + "echarts/echarts.min.js", e + "echarts/dark.js"],
            "echarts-gl": [e + "echarts/echarts.min.js", e + "echarts/echarts-gl.min.js"],
            mapV: [e + "mapV/mapv.min.js"],
            highlight: [e + "highlight/styles/foundation.css", e + "highlight/highlight.pack.js"],
            turf: [e + "turf/turf.min.js"],
            terraformer: [e + "terraformer/terraformer-1.0.9.min.js", e + "terraformer/terraformer-wkt-parser-1.2.0.min.js"],
            ammo: [e + "ammo/ammo.js"],
            quill: [e + "quill/highlight.min.js",e + "quill/katex.min.js",e + "quill/katex.min.css",e + "quill/quill.snow.css",e + "quill/quill.min.js"],
            h5player: [e + "h5player/h5splayer.js",e + "h5player/h5splayerhelper.js",e + "h5player/platform.js",e + "h5player/css/h5splayer.css"],
            "mars3d-visual": [e + "cesiumjs/plugins/visual/mars3d-visual.js"],
            "mars3d-space": [ e + "cesiumjs/plugins/space/mars3d-space.js"],
            'three': [
                e + "three/three.js"
            ],
            'mars2d': [//地图 主库
                "http://mars2d.cn/lib/leafletjs/leaflet/leaflet.css",    //leaflet
                "http://mars2d.cn/lib/leafletjs/leaflet/leaflet.js",
                "http://mars2d.cn/lib/leafletjs/mars2d/mars2d.css", //mars2d
                "http://mars2d.cn/lib/leafletjs/mars2d/mars2d.js",
                "http://mars2d.cn/lib/leafletjs/plugins/esri/esri-leaflet.js",
            ],
            'cesium-tdt': [//天地图三维
                e + "cesiumjs/plugins/tdt/mars3d-tdt.js",
            ],
            'mars3d-echarts': [
                //echarts支持插件
                e + "echarts/echarts.min.js",
                e + "echarts/echarts-gl/echarts-gl.min.js",
                e + 'cesiumjs/plugins/echarts/mars3d-echarts.js',
            ],
            'mars3d-mapv': [
                //mapv支持插件
                e + 'mapV/mapv.min.js',
                e + 'cesiumjs/plugins/mapv/mars3d-mapv.js',
            ],
            'mars3d-heatmap': [
                //heatmap热力图支持插件
                e + 'cesiumjs/plugins/heatmap/heatmap.min.js',
                e + 'cesiumjs/plugins/heatmap/mars3d-heatmap.js',
            ],
            "mars3d-wind": [//风场图层插件
                e + "cesiumjs/plugins/wind/netcdfjs.js", //m10_windLayer解析nc
                e + "cesiumjs/plugins/wind/mars3d-wind.js"
            ],
            'mars3d-widget': [
                //项目widget模块插件
                e + 'cesiumjs/plugins/widget/mars3d-widget.css',
                e + 'cesiumjs/plugins/widget/mars3d-widget.js',
            ],
            "mars3d": [e + "cesiumjs/Cesium/Widgets/widgets.css", e + "cesiumjs/Cesium/Cesium.js", e + "cesiumjs/plugins/compatible/version.js", e + "cesiumjs/mars3d/mars3d.css", e + "cesiumjs/mars3d/newsss.js","config/marsUrl.js"],
           // "mars3d": [e + "cesiumjs/Cesium/Widgets/widgets.css", e + "cesiumjs/Cesium/Cesium.js", e + "cesiumjs/plugins/compatible/version.js", e + "cesiumjs/mars3d/mars3d.css", e + "cesiumjs/mars3d/mars3d_210417.js", e + "cesiumjs/plugins/navigation/mars3d-navigation.css", e + "cesiumjs/plugins/navigation/mars3d-navigation.js", e + "cesiumjs/ext/czml.js","config/marsUrl.js"],
            "cesium-mars": [e + "cesiumjs/Cesium/Widgets/widgets.css", e + "cesiumjs/Cesium/Cesium.js", e + "cesiumjs/plugins/compatible/version.js", e + "cesiumjs/mars3d/mars3d.css", e + "cesiumjs/mars3d/mars3d_11.js", e + "cesiumjs/plugins/navigation/mars3d-navigation.css",e + "cesiumjs/ext/czml.js",e + "cesiumjs/plugins/navigation/mars3d-navigation.js"]
        };
        for (var a in  (n(["lib/hao/noCopy.js"])), s) n(t[s[a]])
    }()
}();


