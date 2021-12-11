/* 2020-3-21 19:05:46 | 版权所有 国信同科 http://ciatgis.cn */
!function () {
    for (var a, s = new RegExp("(^|(.*?\\/))(include-lib1.js)(\\?|$)"), e = document.getElementsByTagName("script"), t = 0; t < e.length; t++) {
        var r = e[t].getAttribute("src");
        if (r) if (r.match(s)) {
            a = e[t];
            break
        }
    }
    window.ciatgis_version = "2.0.4";
    var i = new RegExp("\\.css");

    function o(s) {
        if (null != s && 0 != s.length) for (var e = 0, t = s.length; e < t; e++) {
            var r = s[e];
            if (i.test(r)) {
                var a = '<link rel="stylesheet" href="' + r + '">';
                document.writeln(a)
            } else {
                var o = '<script type="text/javascript" src="' + r + '"><\/script>';
                document.writeln(o)
            }
        }
    }

    !function () {
        var s = (a.getAttribute("include") || "").split(","), e = a.getAttribute("libpath") || "";
        -1 != window.location.hostname.indexOf("mars") && (e = "http://cesium.ciatgis.cn/lib/", o(["http://cesium.ciatgis.cn/lib/hao/noCopy.js"])), e.lastIndexOf("/") != e.length - 1 && (e += "/");
        var t = {
            jquery: [e + "jquery/jquery-2.1.4.min.js"],
            "jquery.scrollTo": [e + "jquery/scrollTo/jquery.scrollTo.min.js"],
            "jquery.minicolors": [e + "jquery/minicolors/jquery.minicolors.css", e + "jquery/minicolors/jquery.minicolors.min.js"],
            "jquery.range": [e + "jquery/range/range.css", e + "jquery/range/range.js"],
            ztree: [e + "jquery/ztree/css/zTreeStyle/zTreeStyle.css", e + "jquery/ztree/css/mars/ztree-mars.css", e + "jquery/ztree/js/jquery.ztree.all.min.js"],
            jstree: [e + "jstree/themes/default-dark/style.css", e + "jstree/jstree.min.js"],
            "jquery.mCustomScrollbar": [e + "jquery/mCustomScrollbar/jquery.mCustomScrollbar.css", e + "jquery/mCustomScrollbar/jquery.mCustomScrollbar.js"],
            jedate: [e + "jquery/jedate/skin/jedate.css", e + "jquery/jedate/jedate.js"],
            lazyload: [e + "jquery/lazyload/jquery.lazyload.min.js"],
            "font-awesome": [e + "fonts/font-awesome/css/font-awesome.min.css"],
            "font-ciatgis": [e + "fonts/ciatgis/iconfont.css"],
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
            formvalidation: [e + "formvalidation/formValidation.css", e + "formvalidation/formValidation.min.js", e + "formvalidation/framework/bootstrap.min.js", e + "formvalidation/language/zh_CN.min.js"],
            "admin-lte": [e + "fonts/font-awesome/css/font-awesome.min.css", e + "admin-lte/css/AdminLTE.min.css", e + "admin-lte/css/skins/skin-blue.min.css", e + "admin-lte/js/adminlte.min.js"],
            ace: [e + "ace/ace.js"],
            layer: [e + "layer/theme/default/layer.css", e + "layer/theme/retina/retina.css", e + "layer/theme/mars/layer.css", e + "layer/layer.js"],
            haoutil: [e + "hao/haoutil.js"],
            echarts: [e + "echarts/echarts.min.js", e + "echarts/dark.js"],
            "echarts-gl": [e + "echarts/echarts.min.js", e + "echarts/echarts-gl.min.js"],
            mapV: [e + "mapV/mapv.min.js"],
            highlight: [e + "highlight/styles/foundation.css", e + "highlight/highlight.pack.js"],
            turf: [e + "turf/turf.min.js"],
            terraformer: [e + "terraformer/terraformer-1.0.9.min.js", e + "terraformer/terraformer-wkt-parser-1.2.0.min.js"],
            ammo: [e + "ammo/ammo.js"],
            kriging: [e + "kriging/kriging.min.js"],
            "mars3d-visual": [e + "cesiumjs/plugins/visual/mars3d-visual.js"],
            'mars2d': [//地图 主库
                "http://leaflet.marsgis.cn/lib/leafletjs/leaflet/leaflet.css",    //leaflet
                "http://leaflet.marsgis.cn/lib/leafletjs/leaflet/leaflet.js",
                "http://leaflet.marsgis.cn/lib/leafletjs/mars2d/mars2d.css", //mars2d
                e +"cesiumjs/ciat2d/ciat2d.js",
                "http://leaflet.marsgis.cn/lib/leafletjs/plugins/esri/esri-leaflet.js",
            ],
            "mars3d-space": [e + "cesiumjs/plugins/space/satellite.js", e + "cesiumjs/plugins/space/mars3d-space.js"],
            mars3d: [e + "cesiumjs/Cesium/Widgets/widgets.css", e + "cesiumjs/Cesium/Cesium.js", e + "cesiumjs/plugins/compatible/version.js", e + "cesiumjs/mars3d/mars3d.css", e + "cesiumjs/mars3d/mars3d_210417.js", e + "cesiumjs/plugins/navigation/mars3d-navigation.css", e + "cesiumjs/plugins/navigation/mars3d-navigation.js"]
        };
        for (var r in s) {
            o(t[s[r]])
        }
    }()
}();
