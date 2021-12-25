/*!
 * BimAngle 3D Tiles Helper Lib v0.3.4
 *
 * Copyright 2018-2020 BimAngle
 * All rights reserved.
 */

"use strict";

function loadTileset(map, name, url, position, count) {
    var configs ={
        name: name,
        url: url,
        "maximumScreenSpaceError": 8,
        "maximumMemoryUsage": 1024,
        "show": true,
        showClickFeature: true,
        "luminanceAtZenith": 0.3,
        "scale": 1,
        "highlight": {
            "type": 'click',
        },
        popup: 'all',
        flyTo: true
    }
    try {
        var  ps = JSON.parse(position);
        configs.position= ps;
    } catch(err) {

    }
    var tiles3dLayer = new mars3d.layer.TilesetLayer(configs)
    map.addLayer(tiles3dLayer)
    attachTileset(map.viewer, tiles3dLayer.tileset, count);
}

function attachTileset(viewer, tileset, count) {

    var picking = true;
    var dbIdToFeatures = {};
    var hiddenDbIds = [];

    var selected = [];
    var selectedDbId = -1;
    var highlighted = [];
    var highlightedDbId = -1;

    var expro = [];

    let leftDown = false;
    let middleDown = false;
    let rightDown = false;
    let pinchStart = false;
    var tilesetUrl = tileset.url || tileset.resource.url;
    var lastIndex = tilesetUrl.lastIndexOf('/');
    var basePath = lastIndex === -1 ? "." : tilesetUrl.substr(0, lastIndex);
    $.ajaxSettings.async = false
    for (var i = 0; i < count; i++) {
        $.getJSON(`${basePath}/info/${parseInt(i)}.json`, function (json) {
            expro.push(json.data)
        });
    }
    $.ajaxSettings.async = true

    function getFeatureDbId(feature) {
        if (Cesium.defined(feature) && Cesium.defined(feature.getProperty)) {
            return parseInt(feature.getProperty('DbId'), 10);
        }
        return -1;
    }

    function setHighlighted(dbId) {

        if (dbId === highlightedDbId) return;

        clearHighlighted();
        highlightedDbId = dbId;

        if (highlightedDbId === selectedDbId || highlightedDbId < 0) {
            return;
        }

        const targetColor = Cesium.Color.YELLOW;
        const features = dbIdToFeatures[dbId];
        for (let feature of features) {
            highlighted.push({
                feature: feature,
                originalColor: Cesium.Color.clone(feature.color)
            });
            feature.color = Cesium.Color.clone(targetColor, feature.color);
        }
    }

    function setSelected(dbId, feature) {

        if (dbId === selectedDbId) return;

        if (dbId === highlightedDbId) {
            clearHighlighted();
        }

        clearSelected();
        selectedDbId = dbId;

        if (selectedDbId < 0) {
            return;
        }

        console.log(`Selected dbId: ${dbId}`);

        selectedEntity.name = `Load info for node ${dbId}`;
        selectedEntity.description = 'Loading <div class="cesium-infoBox-loading"></div>';
        viewer.selectedEntity = selectedEntity;


        function showProps(node) {
            selectedEntity.name = node.name || "<null>";

            let strings = [];
            strings.push('<table class="cesium-infoBox-defaultTable"><tbody>');

            for (let category of node.categories) {
                const props = category.props;
                const propCount = category.count;
                let haveTitle = false;
                for (let i = 0; i < propCount; i++) {
                    if (props.flags[i]) continue;

                    if (!haveTitle) {
                        haveTitle = true;
                        strings.push(`<tr><th colspan=2>${category.name}</th></tr>`);
                    }

                    let value = props.values[i];
                    switch (props.types[i]) {
                        case 'boolean':
                            value = value ? 'Yes' : 'No';
                            break;
                        case 'double':
                            value = props.units[i] ? `${value.toFixed(3)} ${props.units[i]}` : `${value.toFixed(3)}`;
                            break;
                        default:
                            value = value + '';
                            break;
                    }

                    strings.push(`<tr><th>${props.names[i]}</th><td>${value}</td></tr>`);
                }
            }

            strings.push('</tbody></table>');

            selectedEntity.description = strings.join('');
        }

        var propsData = feature && feature.getProperty("Props");
        if (propsData) {
            //加载嵌入的属性数据
            showProps(propsData);
        } else {
            //从json中加载属性数据
            fetch(`${basePath}/info/${parseInt(dbId / 100)}.json`).then(function (response) {
                return response.json();
            }).then(function (json) {
                if (selectedDbId !== dbId) return;

                const node = json.data[dbId + ''];

                showProps(node);
            }).catch(function (err) {
                if (selectedDbId !== dbId) return;

                selectedEntity.description = err;
            });
        }

        const targetColor = Cesium.Color.LIME;
        const features = dbIdToFeatures[dbId];
        for (let feature of features) {
            selected.push({
                feature: feature,
                originalColor: Cesium.Color.clone(feature.color)
            });
            feature.color = Cesium.Color.clone(targetColor, feature.color);
        }
    }

    function clearHighlighted() {
        if (highlightedDbId < 0) return;

        if (highlighted.length > 0) {
            for (let item of highlighted) {
                item.feature.color = item.originalColor;
            }
            highlighted = [];
        }

        highlightedDbId = -1;
    }

    function clearSelected() {
        if (selected.length > 0) {
            for (let item of selected) {
                item.feature.color = item.originalColor;
            }
            selected = [];
        }

        selectedDbId = -1;

        if (viewer.selectedEntity === selectedEntity) {
            viewer.selectedEntity = null;
        }
    }

    function unloadFeature(feature) {
        const dbId = getFeatureDbId(feature);
        const features = dbIdToFeatures[dbId];
        features.splice(features.findIndex(item => item.feature === feature), 1);

        if (dbId === selectedDbId) {
            selected.splice(selected.findIndex(item => item.feature === feature), 1);
        }

        if (dbId === highlighted) {
            highlighted.splice(highlighted.findIndex(item => item.feature === feature), 1);
        }
    }

    function loadFeature(feature) {
        const dbId = getFeatureDbId(feature);
        let features = dbIdToFeatures[dbId];
        var propsData = feature && feature.getProperty("Props");
        if (propsData) {

        } else {
            var dsa = expro[parseInt(dbId / 100)][dbId];
            if (dsa.categories) {

                for (var j = 0; j < dsa.categories.length; j++) {
                    if (dsa.categories[j].name == "约束" || dsa.categories[j].name == "尺寸标注" || dsa.categories[j].name == "数据" || dsa.categories[j].name == "其他") {
                        var names = dsa.categories[j].props.names;
                        var valus = dsa.categories[j].props.values;
                        for (var ii = 0; ii < names.length; ii++)
                            feature.setProperty(names[ii], valus[ii]);
                    }


                }


            }


        }


        if (!Cesium.defined(features)) {
            dbIdToFeatures[dbId] = features = [];

        }
        features.push(feature);


        if (hiddenDbIds.indexOf(dbId) > -1) {
            feature.show = false;
        }
    }

    function processContentFeatures(content, callback) {
        const featuresLength = content.featuresLength;
        for (let i = 0; i < featuresLength; ++i) {
            const feature = content.getFeature(i);
            callback(feature);
        }
    }

    function processTileFeatures(tile, callback) {
        const content = tile.content;
        const innerContents = content.innerContents;
        if (Cesium.defined(innerContents)) {
            const length = innerContents.length;
            for (let i = 0; i < length; ++i) {
                processContentFeatures(innerContents[i], callback);
            }
        } else {
            processContentFeatures(content, callback);
        }
    }

    tileset.tileLoad.addEventListener(function (tile) {
        processTileFeatures(tile, loadFeature);
    });

    tileset.tileUnload.addEventListener(function (tile) {
        processTileFeatures(tile, unloadFeature);
    });

}

//把模型作为一个整体，不再需要查看或选择单个构件
function attachTilesetX(viewer, tileset, modelTitle, modelInfo) {

    var picking = true;

    var selected = false;
    var highlighted = false;

    let leftDown = false;
    let middleDown = false;
    let rightDown = false;
    let pinchStart = false;

    let tiles = [];

    var selectedEntity = new Cesium.Entity();

    tileset.colorBlendMode = Cesium.Cesium3DTileColorBlendMode.REPLACE;

    var handler = new Cesium.ScreenSpaceEventHandler(viewer.scene.canvas);
    handler.setInputAction(function onMouseMove(movement) {
        if (!picking) return;
        if (leftDown || middleDown || rightDown || pinchStart) return;

        const pick = viewer.scene.pick(movement.endPosition);
        if (Cesium.defined(pick) && pick.primitive === tileset) {
            setHighlighted();
        } else {
            clearHighlighted();
        }

    }, Cesium.ScreenSpaceEventType.MOUSE_MOVE);

    handler.setInputAction(function onLeftClick(movement) {
        if (!picking) return;

        const pick = viewer.scene.pick(movement.position);
        if (Cesium.defined(pick) && pick.primitive === tileset) {
            if (selected) {
                clearSelected();
            } else {
                setSelected();
            }
        } else {
            clearSelected();
        }
    }, Cesium.ScreenSpaceEventType.LEFT_CLICK);

    handler.setInputAction(function onLeftClick(movement) {
        if (!picking) return;

        const pick = viewer.scene.pick(movement.position);
        if (Cesium.defined(pick) && pick.primitive === tileset) {
            var camera = viewer.scene.camera;
            var offset = new Cesium.HeadingPitchRange(camera.heading, camera.pitch, 0);
            viewer.flyTo(tileset, {offset: offset});
        } else {
            clearSelected();
        }
    }, Cesium.ScreenSpaceEventType.RIGHT_CLICK);

    handler.setInputAction(function () {
        leftDown = true;
    }, Cesium.ScreenSpaceEventType.LEFT_DOWN);

    handler.setInputAction(function () {
        leftDown = false;
    }, Cesium.ScreenSpaceEventType.LEFT_UP);

    handler.setInputAction(function () {
        middleDown = true;
    }, Cesium.ScreenSpaceEventType.MIDDLE_DOWN);

    handler.setInputAction(function () {
        middleDown = false;
    }, Cesium.ScreenSpaceEventType.MIDDLE_UP);

    handler.setInputAction(function () {
        rightDown = true;
    }, Cesium.ScreenSpaceEventType.RIGHT_DOWN);

    handler.setInputAction(function () {
        rightDown = false;
    }, Cesium.ScreenSpaceEventType.RIGHT_UP);

    handler.setInputAction(function () {
        pinchStart = true;
    }, Cesium.ScreenSpaceEventType.PINCH_START);

    handler.setInputAction(function () {
        pinchStart = false;
    }, Cesium.ScreenSpaceEventType.PINCH_END);

    function setHighlighted() {

        if (highlighted) return;

        clearHighlighted();
        highlighted = true;

        if (highlighted === selected) {
            return;
        }

        setTilesColor(Cesium.Color.YELLOW);
    }

    function setSelected(dbId) {

        if (selected) return;

        if (highlighted) {
            clearHighlighted();
        }

        clearSelected();
        selected = true;

        selectedEntity.name = modelTitle;
        selectedEntity.description = modelInfo; //'Loading <div class="cesium-infoBox-loading"></div>';
        viewer.selectedEntity = selectedEntity;

        setTilesColor(Cesium.Color.LIME);
    }

    function restoreTilesColor() {
        for (let tile of tiles) {
            if (tile.originalColor) {
                tile.color = tile.originalColor;
            }
        }
    }

    function setTilesColor(color) {
        for (let tile of tiles) {
            tile.originalColor = Cesium.Color.clone(tile.color);
            tile.color = color;
        }
    }

    function clearHighlighted() {
        if (highlighted && selected == false) {
            restoreTilesColor();
        }
        highlighted = false;
    }

    function clearSelected() {
        if (selected) {
            restoreTilesColor();
            selected = false;
        }

        if (viewer.selectedEntity === selectedEntity) {
            viewer.selectedEntity = null;
        }
    }

    tileset.tileLoad.addEventListener(function (tile) {
        tiles.push(tile);
    });

    tileset.tileUnload.addEventListener(function (tile) {
        tiles.splice(tiles.findIndex(item => item === tile), 1);
    });
}
