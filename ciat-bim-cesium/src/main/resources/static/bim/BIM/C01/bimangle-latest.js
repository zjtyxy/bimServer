
/*!
 * BimAngle 3D Tiles Helper Lib v0.3.4
 * 
 * Copyright 2018-2020 BimAngle
 * All rights reserved.
 */

"use strict";

function loadTileset(viewer, url, longitude, latitude, height, rotation) {

	var modelMatrix = Cesium.Transforms.eastNorthUpToFixedFrame(Cesium.Cartesian3.fromDegrees(longitude, latitude, height));
    Cesium.Matrix4.multiplyByMatrix3(modelMatrix, Cesium.Matrix3.fromRotationZ(Cesium.Math.toRadians(rotation)), modelMatrix);

    /*
	var tileset = viewer.scene.primitives.add(new Cesium.Cesium3DTileset({
		url: url,
		modelMatrix: modelMatrix,
		luminanceAtZenith: 0.2,
		lightColor: new Cesium.Cartesian3(0.3, 0.3, 0.3),
		// maximumScreenSpaceError: 8, 
		// maximumMemoryUsage: 1024,
    }));
    */

    var tileset = viewer.scene.primitives.add(new Cesium.Cesium3DTileset({
        url: url,
        //modelMatrix: modelMatrix,
        luminanceAtZenith: 0.2,
        lightColor: new Cesium.Cartesian3(0.3, 0.3, 0.3)
        // maximumScreenSpaceError: 8, 
        // maximumMemoryUsage: 1024,
    }));

    tileset.readyPromise.then(function(tileset) {
        tileset.root.transform = modelMatrix;
    });
	
	return tileset;
}

function attachTileset(viewer, tileset){
	
	var picking = true;
	var dbIdToFeatures = {};
	var hiddenDbIds = [];

	var selected = [];
	var selectedDbId = -1;
	var highlighted = [];
	var highlightedDbId = -1;

	let leftDown = false;
	let middleDown = false;
	let rightDown = false;
	let pinchStart = false;

	var selectedEntity = new Cesium.Entity();
	
	tileset.colorBlendMode = Cesium.Cesium3DTileColorBlendMode.REPLACE;
		
	var handler = new Cesium.ScreenSpaceEventHandler(viewer.scene.canvas);
	handler.setInputAction(function onMouseMove(movement) {
		if (!picking) return;
		if (leftDown || middleDown || rightDown || pinchStart) return;

		const pickedFeature = viewer.scene.pick(movement.endPosition);
		if (Cesium.defined(pickedFeature) &&
			pickedFeature instanceof Cesium.Cesium3DTileFeature &&
			pickedFeature.tileset === tileset) {
			const dbId = getFeatureDbId(pickedFeature);
			setHighlighted(dbId);
		} else {
			clearHighlighted();
		}

	}, Cesium.ScreenSpaceEventType.MOUSE_MOVE);

	handler.setInputAction(function onLeftClick(movement) {
		if (!picking) return;

		const pickedFeature = viewer.scene.pick(movement.position);
		if (Cesium.defined(pickedFeature) &&
			pickedFeature instanceof Cesium.Cesium3DTileFeature &&
			pickedFeature.tileset === tileset) {
			const dbId = getFeatureDbId(pickedFeature);
			if (dbId === selectedDbId) {
				clearSelected();
			} else {
                setSelected(dbId, pickedFeature);
			}
		} else {
			clearSelected();
		}
	}, Cesium.ScreenSpaceEventType.LEFT_CLICK);

	handler.setInputAction(function onLeftClick(movement) {
		if (!picking) return;

		const pickedFeature = viewer.scene.pick(movement.position);
		if (Cesium.defined(pickedFeature) &&
			pickedFeature instanceof Cesium.Cesium3DTileFeature &&
			pickedFeature.tileset === tileset) {

			var minX = pickedFeature.getProperty('MinX');
			var minY = pickedFeature.getProperty('MinY');
			var minZ = pickedFeature.getProperty('MinZ');
			var maxX = pickedFeature.getProperty('MaxX');
			var maxY = pickedFeature.getProperty('MaxY');
			var maxZ = pickedFeature.getProperty('MaxZ');

			var sphere = Cesium.BoundingSphere.transform(
				Cesium.BoundingSphere.fromCornerPoints(new Cesium.Cartesian3(minX, minY, minZ), new Cesium.Cartesian3(maxX, maxY, maxZ)), 
				tileset.root.computedTransform,  //tileset.modelMatrix, 
				new Cesium.BoundingSphere()
			);
			var camera = viewer.scene.camera;
			var offset = new Cesium.HeadingPitchRange(camera.heading, camera.pitch, 0);
			camera.flyToBoundingSphere(sphere, {offset: offset});
		} else {
			clearSelected();
		}
	}, Cesium.ScreenSpaceEventType.RIGHT_CLICK);
	
	handler.setInputAction(function() {
		leftDown = true;
	}, Cesium.ScreenSpaceEventType.LEFT_DOWN);

	handler.setInputAction(function() {
		leftDown = false;
	}, Cesium.ScreenSpaceEventType.LEFT_UP);

	handler.setInputAction(function() {
		middleDown = true;
	}, Cesium.ScreenSpaceEventType.MIDDLE_DOWN);

	handler.setInputAction(function() {
		middleDown = false;
	}, Cesium.ScreenSpaceEventType.MIDDLE_UP);

	handler.setInputAction(function() {
		rightDown = true;
	}, Cesium.ScreenSpaceEventType.RIGHT_DOWN);

	handler.setInputAction(function() {
		rightDown = false;
	}, Cesium.ScreenSpaceEventType.RIGHT_UP);

	handler.setInputAction(function() {
		pinchStart = true;
	}, Cesium.ScreenSpaceEventType.PINCH_START);

	handler.setInputAction(function() {
		pinchStart = false;
	}, Cesium.ScreenSpaceEventType.PINCH_END);

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

		var tilesetUrl = tileset.url || tileset.resource.url;
		var lastIndex = tilesetUrl.lastIndexOf('/');
		var basePath = lastIndex === -1 ? "." : tilesetUrl.substr(0, lastIndex);

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
		
		if(viewer.selectedEntity === selectedEntity){
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

	tileset.tileLoad.addEventListener(function(tile) {
		processTileFeatures(tile, loadFeature);
	});

	tileset.tileUnload.addEventListener(function(tile) {
		processTileFeatures(tile, unloadFeature);
	});	
	
}

//把模型作为一个整体，不再需要查看或选择单个构件
function attachTilesetX(viewer, tileset, modelTitle, modelInfo){
	
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
	
	handler.setInputAction(function() {
		leftDown = true;
	}, Cesium.ScreenSpaceEventType.LEFT_DOWN);

	handler.setInputAction(function() {
		leftDown = false;
	}, Cesium.ScreenSpaceEventType.LEFT_UP);

	handler.setInputAction(function() {
		middleDown = true;
	}, Cesium.ScreenSpaceEventType.MIDDLE_DOWN);

	handler.setInputAction(function() {
		middleDown = false;
	}, Cesium.ScreenSpaceEventType.MIDDLE_UP);

	handler.setInputAction(function() {
		rightDown = true;
	}, Cesium.ScreenSpaceEventType.RIGHT_DOWN);

	handler.setInputAction(function() {
		rightDown = false;
	}, Cesium.ScreenSpaceEventType.RIGHT_UP);

	handler.setInputAction(function() {
		pinchStart = true;
	}, Cesium.ScreenSpaceEventType.PINCH_START);

	handler.setInputAction(function() {
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

	function restoreTilesColor(){
		for (let tile of tiles) {
			if(tile.originalColor){
				tile.color = tile.originalColor;
			}
		}
	}

	function setTilesColor(color){
		for (let tile of tiles) {
			tile.originalColor = Cesium.Color.clone(tile.color);
			tile.color = color;
		}
	}

	function clearHighlighted() {
		if (highlighted && selected == false){
			restoreTilesColor();
		}
		highlighted = false;
	}

	function clearSelected() {
		if (selected) {
			restoreTilesColor();
			selected = false;
		}
		
		if(viewer.selectedEntity === selectedEntity){
			viewer.selectedEntity = null;
		}
	}
	
	tileset.tileLoad.addEventListener(function(tile) {
		tiles.push(tile);
	});

	tileset.tileUnload.addEventListener(function(tile) {
		tiles.splice(tiles.findIndex(item => item === tile), 1);
	});	
}