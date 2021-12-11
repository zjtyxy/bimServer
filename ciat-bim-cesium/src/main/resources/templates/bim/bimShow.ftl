<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0,minimum-scale=1.0,maximum-scale=1.0" />
    <meta name="apple-touch-fullscreen" content="yes" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no" />
    <meta name="x5-fullscreen" content="true" />
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />

    <script
      type="text/javascript"
      src="../lib/include-lib.js"
      libpath="../lib/"
      include="jquery,font-awesome,bootstrap,layer,haoutil,turf,mars3d"
    ></script>
    <script src="js/bimangle-latest.js"></script>
    <link href="css/style.css" rel="stylesheet" />
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
    <div id="mars3dContainer" class="mars3d-container"></div>
    <div class="infoview" style="top: 30%; width: 100px; text-align: center">
      <button type="button" id="btnShowAll" class="btn btn-primary active">整体</button>
      <button type="button" id="btnShowF5" class="btn btn-primary">15层</button>
      <button type="button" id="btnShowF4" class="btn btn-primary">10层</button>
      <button type="button" id="btnShowF3" class="btn btn-primary">7层</button>
      <button type="button" id="btnShowF2" class="btn btn-primary">5层</button>
      <button type="button" id="btnShowF1" class="btn btn-primary">3层</button>
      <button type="button" id="btnShowD1" class="btn btn-primary">1层</button>
    </div>

    <script >
       var modelurl =  '${bimModel.url}';

    </script>
    <script src="./js/common.js"></script>
    <script src="js/f40.js"></script>
    <script src="js/bimangle-latest.js"></script>
  </body>
</html>
