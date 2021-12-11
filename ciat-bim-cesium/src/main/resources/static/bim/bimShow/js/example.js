var exConfig, openTimer, containExamples = !1, thumbLocation = getThumbLocation();

function initPage() {
    var _0x34cf99 = $('ul#sidebar-menu'), _0x1efdd3 = $('#charts-list');
    exConfig.forEach(function (_0x2bfe1b, _0xf4fd67) {
        var _0x208ada = 0x0;
        _0x2bfe1b.children && _0x2bfe1b.children.forEach(function (_0x1b7b20, _0x2df189) {
            _0x1b7b20.count = _0x1b7b20.children ? _0x1b7b20.children['length'] : 0x0, _0x208ada += _0x1b7b20.count;
        }), _0x2bfe1b.count = _0x208ada, _0x34cf99.append(createSideBarMenuItem(_0x2bfe1b, containExamples)), _0x1efdd3.append(createGalleryItem(_0x2bfe1b));

    }), resizeCharts(), initSelect(), sidebarScrollFix();
}

function initSelect() {
    -0x1 === window['location'].hash.indexOf('#') || scroll();
}

function createGalleryItem(_0xd18e56) {
    if (_0xd18e56) {
        var _0x23e480 = $(('<li\x20class=\x27category\x27\x20id=\x27' + _0xd18e56['id']) + '\'></li>');
        return _0xd18e56.name && createGalleryItemTitle(_0xd18e56)['appendTo'](_0x23e480),
        _0xd18e56.children && createSubGalleryItem(_0xd18e56.children).appendTo(_0x23e480), _0x23e480;
    }
}

function createSubGalleryItem(_0x4ef165) {
    var _0x34913c = $('<div class=\'category-content\'></div>');
    return _0x4ef165.forEach(function (_0x491166, _0x13c348) {
        var _0x465766 = $(('<div\x20class=\x27box\x20box-default\x20color-palette-box\x27\x20id=\x27' + _0x4ef165['id']) + '\'></div>');
        createSubGalleryItemTitle(_0x491166).appendTo(_0x465766),
        _0x491166['children'] && createGalleryCharts(_0x491166.children).appendTo(_0x465766), _0x465766.appendTo(_0x34913c);

    }), _0x34913c;
}

function createGalleryItemTitle(_0x91dc21) {
    return $(((('<h3 class=\'category-title\' id=\'' + _0x91dc21['id'] + '\'><i class=\'fa ') +
        _0x91dc21['icon']) + '\'></i>&nbsp;&nbsp;' + _0x91dc21.name) + '\x20(' + _0x91dc21.count + ')</h3>');
}

function createSubGalleryItemTitle(_0x616e52) {
    var _0x2aa9d6 = _0x616e52.details ? '<div class=\'box-title-details\'>说明：'.concat(_0x616e52.details, '</div>') : '';
    return $('<div class=\'box-header\'><h3 class=\'box-title\' id=\'' + _0x616e52['id'] + '\x27>&nbsp;&nbsp;&nbsp;&nbsp;' + _0x616e52['name'] + '\x20(' + _0x616e52.count + ')</h4></h3>' + _0x2aa9d6 + '</div>');
}

function createGalleryCharts(_0x3d8646) {
    for (var _0x12a145 = $('<div class=\'box-body\'></div>'), _0x4591b7 = _0x3d8646 && _0x3d8646['length'] ? _0x3d8646.length : 0x0, _0x54efb2 = 0x0; _0x54efb2 < _0x4591b7; _0x54efb2++) {
        var _0x52c291 = createGalleryChart(_0x3d8646[_0x54efb2]);
        _0x52c291 && _0x52c291.appendTo(_0x12a145);
    }
    return _0x12a145;
}

function createGalleryChart(_0x1c72ce) {
    var _0x45b8ae = window.examplePath || 'example/',
        _0xaf18b2 = window.widgetPath || '//mars3d.cn/project/zhts/map.html',
        _0xc156b1 = (_0x45b8ae + 'editor.html'),
        _0x1092cf = _0x1c72ce.name,
        _0x3fdab2 = fileName2Id(_0x1c72ce['fileName']),
        _0x3599a0 = ((window.exampleIconPath || '../data/exampleIcon/') + _0x1c72ce.thumbnail || ''),
        _0x200ffe = !1;
    if (_0x1c72ce['params']) {
        if (_0xc156b1 += '?' + window.autoShowCode ? 'code=true&' : '' + _0x1c72ce.params, (-1 != _0x1c72ce['params']['indexOf']('widget='))) {
            if (!window.showWidget) return !1;
            _0x200ffe = !0x0, _0xc156b1 = (_0xaf18b2 + '?onlyStart=true&name=' + _0x1092cf + '&' + _0x1c72ce.params);
        }
    } else _0xc156b1 += '?' + (window.autoShowCode ? 'code=true&' : '');
    _0x3fdab2 && (_0xc156b1 = (_0xc156b1 + '#') + _0x3fdab2);
    var _0x5b7a54 = (_0x1092cf + '\x20v' + _0x1c72ce.version || ''),
        _0xaf18b2 = $('<div class=\'col-xlg-2 col-lg-3 col-md-4 col-sm-6 col-xs-12\'><a target=\'_blank\'href=\'' + _0x45b8ae + _0x3fdab2 + '.html\x27></a></div>'),
        _0x45b8ae = $('<div class="chart"></div>'),
        _0x3fdab2 = $('<a class=\'chart-link\' target=\'_blank\' href=\'' + _0xc156b1 + '\x27></a>'),
        _0xc156b1 = $('<h5 class=\'chart-title\'  title=\'' + _0x5b7a54 + '\' >' + _0x1092cf + '</h5>'),
        _0x3599a0 = $('<img class=\'chart-area\' src=\'' + _0x3599a0 + '\' style=\'display: inline\'>');
    return _0x1c72ce['plugins'] && (_0x5b7a54 += ('\n该功能属于独立' + _0x1c72ce.plugins + '插件功能，在额外的js中。'),
        _0xc156b1 = $('<h5 class=\'chart-title\' title=\'' + _0x5b7a54 + '\x27\x20\x20>' + _0x1092cf, '<span\x20style=\x27color:rgba(0,\x20147,\x20255,\x200.7)\x27>[' + _0x1c72ce.plugins + '插件]</span></h5>'),
    _0x200ffe && (_0x5b7a54 += '\x0a该功能属于项目内功能，此处仅做演示，具体交付依赖是否选择对应项目。',
        _0xc156b1 = $(('<h5\x20class=\x27chart-title\x27\x20title=\x27' + _0x5b7a54), '\' >' + _0x1092cf) + '<span style=\'color:rgba(0, 147, 255, 0.7)\'>[项目widget]</span></h5>'))
        , _0xaf18b2.attr('title', _0x5b7a54), _0xc156b1.appendTo(_0x3fdab2), _0x3599a0['appendTo'](_0x3fdab2), _0x3fdab2['appendTo'](_0x45b8ae), _0x45b8ae.appendTo(_0xaf18b2), _0xaf18b2;
}

function imgerrorfun() {
    var _0x47c7b0 = event.srcElement;
    _0x47c7b0.src = 'img/mapicon.jpg', _0x47c7b0.onerror = null;
}

function openExampleView(_0x536d80, _0xf38ce1) {
    var _0x249c2f = (document.documentElement.clientWidth - 0xe6) + 'px',
        _0x2303dc = (document.documentElement.clientHeight - 0x3c) + 'px',
        _0x536d80 = layer.open({
            'type': 0x2,
            'title': _0xf38ce1,
            'fix': !0x0,
            'maxmin': !0x0,
            'shadeClose': !0x0,
            'offset': ['60px', '230px'],
            'area': [_0x249c2f, _0x2303dc],
            'content': _0x536d80,
            'skin': 'layer-mars-dialog animation-scale-up',
            'success': function (_0x47b4eb) {
            }
        });
    $((('#layui-layer' + _0x536d80) + ' .layui-layer-title')).css({
        'background': 'rgba(30, 36, 50, 1)',
        'border-color': 'rgba(32, 160, 255, 1)'
    });
}

function getThumbLocation() {
    var _0x475bcf = window.location.toString();
    return _0x475bcf.substr(0x0, _0x475bcf.lastIndexOf('/'));
}

function resizeCharts() {
    var _0x111b2d = $('#charts-list\x20.chart\x20.chart-area');
    _0x111b2d[0] && _0x111b2d[0].offsetWidth ? _0x111b2d.height((0.8 * _0x111b2d[0].offsetWidth)) : _0x111b2d.height(0xd0), window.onresize = function () {
        resizeCharts();
    };
}

function scroll() {
}

function bindEvents() {
    $('ul#sidebar-menu>li.treeview>ul>li').parent('ul').siblings('a').click(function (_0xc3b07d) {
        $(this).siblings('ul')['is'](':visible') && $(this)['siblings']('ul').children('li').hasClass('active') && _0xc3b07d.stopPropagation(),
            window.location = _0xc3b07d.currentTarget.href;
    }), window.addEventListener('hashchange', function () {
        scroll();
    });
}

$(document).ready(function () {
    bindEvents();
    setTimeout(function () {
        $('img.chart-thumb').lazyload();
    }, 0x3e8);
    fetch(window['exampleConfig'] || 'data/example.json').then(function (_0x1daaed) {
        if (_0x1daaed['ok']) return _0x1daaed.json();
        var _0x5e75ed = new Error(_0x1daaed.statusText);
        throw _0x5e75ed.response = _0x1daaed, _0x5e75ed;
    }).then(function (_0x1afe66) {
        (exConfig = _0x1afe66.result).forEach(function (_0x58b3dc, _0x34b18a) {
            _0x58b3dc['id'] = ('ex_' + _0x34b18a), _0x58b3dc.children && _0x58b3dc.children.forEach(function (_0x53815f, _0x9a8f14) {
                _0x53815f['id'] = (_0x58b3dc['id'] + '_' + _0x9a8f14), _0x53815f.children && _0x53815f.children.forEach(function (_0xcddc65, _0x286f1c) {
                    _0xcddc65['id'] = _0xcddc65['id'] + '_' + _0x286f1c;
                });
            });
        }), haoutil.storage.add('exConfig', JSON.stringify(exConfig)), initPage();
    }).catch(function (_0x375bcd) {
        console.log('加载JSON出错', _0x375bcd), haoutil.alert(null == _0x375bcd ? void 0x0 : _0x375bcd.message, '出错了');
    });
});
var animationSpeed = 0x1f4;

function getVerDiff(_0x2bae7c) {
    var _0x306073 = 0, _0x499257 = '序号,分类,子分类,功能名称,版本\x0a';
    return exConfig['forEach'](function (_0x2c794f, _0x3d3617) {
        _0x2c794f.children && _0x2c794f.children.forEach(function (_0x664258, _0x204878) {
            _0x664258.children && _0x664258.children.forEach(function (_0x4aa04b, _0x3d05cd) {
                (!_0x2bae7c || (_0x4aa04b.version > _0x2bae7c)) && (_0x499257 += ''['concat'](++_0x306073, ',')['concat'](_0x2c794f.name, ',')['concat'](_0x664258.name, ',').concat(_0x4aa04b['name'], ',').concat(_0x4aa04b['version'], '\x0a'));
            });
        });

    }), _0x499257;
}

function getAllName() {
    var _0x505b52 = 'Mars3D功能清单：';
    return exConfig.forEach(function (_0x16136c, _0x3183b5) {
        _0x16136c.children && (_0x505b52 += '\x0a\x0a'.concat('1.').concat((_0x3183b5 + 1), '\x20\x20').concat(_0x16136c.name), _0x16136c.children.forEach(function (_0x287b97, _0x38066a) {
            _0x287b97.children && (_0x505b52 += '\x0a'.concat('1.')['concat']((_0x3183b5 + 1), '.')['concat'](_0x38066a + 0x1, '\x20\x20').concat(_0x287b97.name, '\x0a'), _0x287b97.children.forEach(function (_0x507fe9, _0x94bc93) {
                _0x505b52 += ((0 === _0x94bc93) ? '\x09' : ',').concat(_0x507fe9.name);
            }), _0x505b52 += '\x0a');
        }));
    }), _0x505b52;
}

$(window)['on']('scroll', function () {
    var _0x4c1a58;
    $('ul.sidebar-menu>li').hasClass('active') && (_0x4c1a58 = $('ul.sidebar-menu>li')['parent']('ul'), openTimer && clearTimeout(openTimer),
        openTimer = setTimeout(function () {
            _0x4c1a58.children('li.active')['children']('ul').slideDown(animationSpeed, function () {
                _0x4c1a58.children('li.active').children('ul').css('display', 'block');
            });
        }, 0x64)), $('ul.sidebar-menu>li').not('li.active').children('ul').css('display', 'none');
});
