function _typeof(x) {
    return (_typeof = 'function' == typeof Symbol && 'symbol' == typeof Symbol.iterator ? function (x) {
        return typeof x
    } : function (x) {
        return x && 'function' == typeof Symbol && x.constructor === Symbol && x !== Symbol.prototype ? 'symbol' : typeof x
    })(x)
}

!function (x, e) {
    'object' == ('undefined' == typeof exports ? 'undefined' : _typeof(exports)) && 'object' == ('undefined' == typeof module ? 'undefined' : _typeof(module)) ? module.exports = e(require('leaflet')) : 'function' == typeof define && define.amd ? define('mars2d', '.leaflet', e) : 'object' == ('undefined' == typeof exports ? 'undefined' : _typeof(exports)) ? exports.mars2d = e(require('leaflet')) : x.mars2d = e(x.L)
}(window, function (ncdf0000) {
    return H = [function (x, e) {
        x.exports = ncdf0000
    }, function (x, e, a) {
        'use strict';
        a.d(e, 'a', function () {
            return F
        });
        var t, _, s, d, i, f, n, h, r, c, o, l, u, b, m, p, y, v, Z, M, V, G, W, X, g, R, w, Y, N, e = a(4), k = a.n(e),
            F = (h = (n = []).concat, r = n.filter, c = n.slice, o = window.document, l = {}, u = {}, b = {
                'column-count': 1,
                columns: 1,
                'font-weight': 1,
                'line-height': 1,
                opacity: 1,
                'z-index': 1,
                zoom: 1
            }, m = /^\s*<(\w+|!)[^>]*>/, p = /^<(\w+)\s*\/?>(?:<\/\1>|)$/, y = /<(?!area|br|col|embed|hr|img|input|link|meta|param)(([\w:]+)[^>]*)\/>/gi, v = /^(?:body|html)$/i, Z = /([A-Z])/g, M = ['val', 'css', 'html', 'text', 'data', 'width', 'height', 'offset'], a = o.createElement('table'), e = o.createElement('tr'), V = {
                tr: o.createElement('tbody'),
                tbody: a,
                thead: a,
                tfoot: a,
                td: e,
                th: e,
                '*': o.createElement('div')
            }, G = /complete|loaded|interactive/, W = /^[\w-]*$/, g = (X = {}).toString, R = {}, w = o.createElement('div'), Y = {
                tabindex: 'tabIndex',
                readonly: 'readOnly',
                for: 'htmlFor',
                class: 'className',
                maxlength: 'maxLength',
                cellspacing: 'cellSpacing',
                cellpadding: 'cellPadding',
                rowspan: 'rowSpan',
                colspan: 'colSpan',
                usemap: 'useMap',
                frameborder: 'frameBorder',
                contenteditable: 'contentEditable'
            }, N = Array.isArray || function (x) {
                return x instanceof Array
            }, R.matches = function (x, e) {
                if (!e || !x || 1 !== x.nodeType) return !1;
                var a = x.matches || x.webkitMatchesSelector || x.mozMatchesSelector || x.oMatchesSelector || x.matchesSelector;
                if (a) return a.call(x, e);
                var d = x.parentNode, a = !d;
                return a && (d = w).appendChild(x), e = ~R.qsa(d, e).indexOf(x), a && w.removeChild(x), e
            }, i = function (x) {
                return x.replace(/-+(.)?/g, function (x, e) {
                    return e ? e.toUpperCase() : ''
                })
            }, f = function (a) {
                return r.call(a, function (x, e) {
                    return a.indexOf(x) == e
                })
            }, R.fragment = function (x, e, a) {
                var d, f, t;
                return (d = p.test(x) ? s(o.createElement(RegExp.$1)) : d) || (x.replace && (x = x.replace(y, '<$1></$2>')), void 0 === e && (e = m.test(x) && RegExp.$1), (t = V[e = !(e in V) ? '*' : e]).innerHTML = '' + x, d = s.each(c.call(t.childNodes), function () {
                    t.removeChild(this)
                })), L(a) && (f = s(d), s.each(a, function (x, e) {
                    -1 < M.indexOf(x) ? f[x](e) : f.attr(x, e)
                })), d
            }, R.Z = function (x, e) {
                return new H(x, e)
            }, R.isZ = function (x) {
                return x instanceof R.Z
            }, R.init = function (x, e) {
                var a, d;
                if (!x) return R.Z();
                if ('string' == typeof x) if ('<' == (x = x.trim())[0] && m.test(x)) a = R.fragment(x, RegExp.$1, e), x = null; else {
                    if (void 0 !== e) return s(e).find(x);
                    a = R.qsa(o, x)
                } else {
                    if (J(x)) return s(o).ready(x);
                    if (R.isZ(x)) return x;
                    if (N(x)) d = x, a = r.call(d, function (x) {
                        return null != x
                    }); else if (Q(x)) x = (a = 0 < x.length ? x : [x], null); else if (m.test(x)) a = R.fragment(x.trim(), RegExp.$1, e), x = null; else {
                        if (void 0 !== e) return s(e).find(x);
                        a = R.qsa(o, x)
                    }
                }
                return R.Z(a, x)
            }, (s = function (x, e) {
                return R.init(x, e)
            }).extend = function (e) {
                var a, x = c.call(arguments, 1);
                return 'boolean' == _typeof(e) && (a = e, e = x.shift()), x.forEach(function (x) {
                    !function x(e, a, d) {
                        for (_ in a) d && (L(a[_]) || N(a[_])) ? (L(a[_]) && !L(e[_]) && (e[_] = {}), N(a[_]) && !N(e[_]) && (e[_] = []), x(e[_], a[_], d)) : void 0 !== a[_] && (e[_] = a[_])
                    }(e, x, a)
                }), e
            }, R.qsa = function (x, e) {
                var a, d = '#' == e[0], f = !d && '.' == e[0], t = d || f ? e.slice(1) : e, _ = W.test(t);
                return x.getElementById && _ && d ? (a = x.getElementById(t)) ? [a] : [] : 1 !== x.nodeType && 9 !== x.nodeType && 11 !== x.nodeType ? [] : c.call(_ && !d && x.getElementsByClassName ? f ? x.getElementsByClassName(t) : x.getElementsByTagName(e) : x.querySelectorAll(e))
            }, s.contains = o.documentElement.contains ? function (x, e) {
                return x !== e && x.contains(e)
            } : function (x, e) {
                for (; e = e && e.parentNode;) if (e === x) return !0;
                return !1
            }, s.type = z, s.isFunction = J, s.isWindow = T, s.isArray = N, s.isPlainObject = L, s.isEmptyObject = function (x) {
                for (var e in x) return !1;
                return !0
            }, s.isNumeric = function (x) {
                var e = Number(x), a = k()(x);
                return null != x && 'boolean' != a && ('string' != a || x.length) && !isNaN(e) && isFinite(e) || !1
            }, s.inArray = function (x, e, a) {
                return n.indexOf.call(e, x, a)
            }, s.camelCase = i, s.trim = function (x) {
                return null == x ? '' : String.prototype.trim.call(x)
            }, s.uuid = 0, s.support = {}, s.expr = {}, s.noop = function () {
            }, s.map = function (x, e) {
                var a, d, f, t, _ = [];
                if (I(x)) for (d = 0; d < x.length; d++) null != (a = e(x[d], d)) && _.push(a); else for (f in x) null != (a = e(x[f], f)) && _.push(a);
                return 0 < (t = _).length ? s.fn.concat.apply([], t) : t
            }, s.each = function (x, e) {
                var a, d;
                if (I(x)) {
                    for (a = 0; a < x.length; a++) if (!1 === e.call(x[a], a, x[a])) return x
                } else for (d in x) if (!1 === e.call(x[d], d, x[d])) return x;
                return x
            }, s.grep = function (x, e) {
                return r.call(x, e)
            }, window.JSON && (s.parseJSON = JSON.parse), s.each('Boolean Number String Function Array Date RegExp Object Error'.split(' '), function (x, e) {
                X['[object ' + e + ']'] = e.toLowerCase()
            }), s.fn = {
                constructor: R.Z,
                length: 0,
                forEach: n.forEach,
                reduce: n.reduce,
                push: n.push,
                sort: n.sort,
                splice: n.splice,
                indexOf: n.indexOf,
                concat: function () {
                    for (var x, e = [], a = 0; a < arguments.length; a++) x = arguments[a], e[a] = R.isZ(x) ? x.toArray() : x;
                    return h.apply(R.isZ(this) ? this.toArray() : this, e)
                },
                map: function (a) {
                    return s(s.map(this, function (x, e) {
                        return a.call(x, e, x)
                    }))
                },
                slice: function () {
                    return s(c.apply(this, arguments))
                },
                ready: function (x) {
                    return G.test(o.readyState) && o.body ? x(s) : o.addEventListener('DOMContentLoaded', function () {
                        x(s)
                    }, !1), this
                },
                get: function (x) {
                    return void 0 === x ? c.call(this) : this[0 <= x ? x : x + this.length]
                },
                toArray: function () {
                    return this.get()
                },
                size: function () {
                    return this.length
                },
                remove: function () {
                    return this.each(function () {
                        null != this.parentNode && this.parentNode.removeChild(this)
                    })
                },
                each: function (a) {
                    return n.every.call(this, function (x, e) {
                        return !1 !== a.call(x, e, x)
                    }), this
                },
                filter: function (e) {
                    return J(e) ? this.not(this.not(e)) : s(r.call(this, function (x) {
                        return R.matches(x, e)
                    }))
                },
                add: function (x, e) {
                    return s(f(this.concat(s(x, e))))
                },
                is: function (x) {
                    return 0 < this.length && R.matches(this[0], x)
                },
                not: function (e) {
                    var a, d = [];
                    return J(e) && void 0 !== e.call ? this.each(function (x) {
                        e.call(this, x) || d.push(this)
                    }) : (a = 'string' == _typeof(e) ? this.filter(e) : I(e) && J(e.item) ? c.call(e) : s(e), this.forEach(function (x) {
                        a.indexOf(x) < 0 && d.push(x)
                    })), s(d)
                },
                has: function (x) {
                    return this.filter(function () {
                        return Q(x) ? s.contains(this, x) : s(this).find(x).size()
                    })
                },
                eq: function (x) {
                    return -1 === x ? this.slice(x) : this.slice(x, +x + 1)
                },
                first: function () {
                    var x = this[0];
                    return x && !Q(x) ? x : s(x)
                },
                last: function () {
                    var x = this[this.length - 1];
                    return x && !Q(x) ? x : s(x)
                },
                find: function (x) {
                    var a = this;
                    return x ? 'object' == k()(x) ? s(x).filter(function () {
                        var e = this;
                        return n.some.call(a, function (x) {
                            return s.contains(x, e)
                        })
                    }) : 1 == this.length ? s(R.qsa(this[0], x)) : this.map(function () {
                        return R.qsa(this, x)
                    }) : s()
                },
                closest: function (a, d) {
                    var f = [], t = 'object' == k()(a) && s(a);
                    return this.each(function (x, e) {
                        for (; e && !(t ? 0 <= t.indexOf(e) : R.matches(e, a));) e = e !== d && !U(e) && e.parentNode;
                        e && f.indexOf(e) < 0 && f.push(e)
                    }), s(f)
                },
                parents: function (x) {
                    for (var e = [], a = this; 0 < a.length;) a = s.map(a, function (x) {
                        if ((x = x.parentNode) && !U(x) && e.indexOf(x) < 0) return e.push(x), x
                    });
                    return C(e, x)
                },
                parent: function (x) {
                    return C(f(this.pluck('parentNode')), x)
                },
                children: function (x) {
                    return C(this.map(function () {
                        return B(this)
                    }), x)
                },
                contents: function () {
                    return this.map(function () {
                        return this.contentDocument || c.call(this.childNodes)
                    })
                },
                siblings: function (x) {
                    return C(this.map(function (x, e) {
                        return r.call(B(e.parentNode), function (x) {
                            return x !== e
                        })
                    }), x)
                },
                empty: function () {
                    return this.each(function () {
                        this.innerHTML = ''
                    })
                },
                pluck: function (e) {
                    return s.map(this, function (x) {
                        return x[e]
                    })
                },
                show: function () {
                    return this.each(function () {
                        var x, e, a;
                        'none' == this.style.display && (this.style.display = ''), 'none' == getComputedStyle(this, '').getPropertyValue('display') && (this.style.display = (x = this.nodeName, l[x] || (e = o.createElement(x), o.body.appendChild(e), a = getComputedStyle(e, '').getPropertyValue('display'), e.parentNode.removeChild(e), 'none' == a && (a = 'block'), l[x] = a), l[x]))
                    })
                },
                replaceWith: function (x) {
                    return this.before(x).remove()
                },
                wrap: function (e) {
                    var a, d, f = J(e);
                    return this[0] && !f && (a = s(e).get(0), d = a.parentNode || 1 < this.length), this.each(function (x) {
                        s(this).wrapAll(f ? e.call(this, x) : d ? a.cloneNode(!0) : a)
                    })
                },
                wrapAll: function (x) {
                    if (this[0]) {
                        var e;
                        for (s(this[0]).before(x = s(x)); (e = x.children()).length;) x = e.first();
                        s(x).append(this)
                    }
                    return this
                },
                wrapInner: function (d) {
                    var f = J(d);
                    return this.each(function (x) {
                        var e = s(this), a = e.contents(), x = f ? d.call(this, x) : d;
                        a.length ? a.wrapAll(x) : e.append(x)
                    })
                },
                unwrap: function () {
                    return this.parent().each(function () {
                        s(this).replaceWith(s(this).children())
                    }), this
                },
                clone: function () {
                    return this.map(function () {
                        return this.cloneNode(!0)
                    })
                },
                hide: function () {
                    return this.css('display', 'none')
                },
                toggle: function (e) {
                    return this.each(function () {
                        var x = s(this);
                        (void 0 === e ? 'none' == x.css('display') : e) ? x.show() : x.hide()
                    })
                },
                prev: function (x) {
                    return s(this.pluck('previousElementSibling')).filter(x || '*')
                },
                next: function (x) {
                    return s(this.pluck('nextElementSibling')).filter(x || '*')
                },
                html: function (a) {
                    return 0 in arguments ? this.each(function (x) {
                        var e = this.innerHTML;
                        s(this).empty().append(j(this, a, x, e))
                    }) : 0 in this ? this[0].innerHTML : null
                },
                text: function (e) {
                    return 0 in arguments ? this.each(function (x) {
                        x = j(this, e, x, this.textContent);
                        this.textContent = null == x ? '' : '' + x
                    }) : 0 in this ? this.pluck('textContent').join('') : null
                },
                attr: function (e, a) {
                    var x;
                    return 'string' != _typeof(e) || 1 in arguments ? this.each(function (x) {
                        if (1 === this.nodeType) if (Q(e)) for (_ in e) O(this, _, e[_]); else O(this, e, j(this, a, x, this.getAttribute(e)))
                    }) : 0 in this && 1 == this[0].nodeType && null != (x = this[0].getAttribute(e)) ? x : void 0
                },
                removeAttr: function (x) {
                    return this.each(function () {
                        1 === this.nodeType && x.split(' ').forEach(function (x) {
                            O(this, x)
                        }, this)
                    })
                },
                prop: function (e, a) {
                    return e = Y[e] || e, 1 in arguments ? this.each(function (x) {
                        this[e] = j(this, a, x, this[e])
                    }) : this[0] && this[0][e]
                },
                removeProp: function (x) {
                    return x = Y[x] || x, this.each(function () {
                        delete this[x]
                    })
                },
                data: function (x, e) {
                    x = 'data-' + x.replace(Z, '-$1').toLowerCase(), x = 1 in arguments ? this.attr(x, e) : this.attr(x);
                    return null !== x ? P(x) : void 0
                },
                val: function (e) {
                    return 0 in arguments ? (null == e && (e = ''), this.each(function (x) {
                        this.value = j(this, e, x, this.value)
                    })) : this[0] && (this[0].multiple ? s(this[0]).find('option').filter(function () {
                        return this.selected
                    }).pluck('value') : this[0].value)
                },
                offset: function (d) {
                    if (d) return this.each(function (x) {
                        var e = s(this), a = j(this, d, x, e.offset()), x = e.offsetParent().offset(),
                            x = {top: a.top - x.top, left: a.left - x.left};
                        'static' == e.css('position') && (x.position = 'relative'), e.css(x)
                    });
                    if (!this.length) return null;
                    if (o.documentElement !== this[0] && !s.contains(o.documentElement, this[0])) return {
                        top: 0,
                        left: 0
                    };
                    var x = this[0].getBoundingClientRect();
                    return x.width = this[0].offsetWidth, x.height = this[0].offsetHeight, {
                        left: x.left + window.pageXOffset,
                        top: x.top + window.pageYOffset,
                        width: Math.round(x.width),
                        height: Math.round(x.height)
                    }
                },
                css: function (x, e) {
                    if (arguments.length < 2) {
                        var a = this[0];
                        if ('string' == typeof x) return a ? a.style[i(x)] || getComputedStyle(a, '').getPropertyValue(x) : void 0;
                        if (N(x)) {
                            if (!a) return;
                            var d = {}, f = getComputedStyle(a, '');
                            return s.each(x, function (x, e) {
                                d[e] = a.style[i(e)] || f.getPropertyValue(e)
                            }), d
                        }
                    }
                    var t = '';
                    if ('string' == z(x)) e || 0 === e ? t = A(x) + ':' + S(x, e) : this.each(function () {
                        this.style.removeProperty(A(x))
                    }); else for (_ in x) x[_] || 0 === x[_] ? t += A(_) + ':' + S(_, x[_]) + ';' : this.each(function () {
                        this.style.removeProperty(A(_))
                    });
                    return this.each(function () {
                        this.style && (this.style.cssText += ';' + t)
                    })
                },
                index: function (x) {
                    return x ? this.indexOf(s(x)[0]) : this.parent().children().indexOf(this[0])
                },
                hasClass: function (x) {
                    return !!x && n.some.call(this, function (x) {
                        return this.test(D(x))
                    }, E(x))
                },
                addClass: function (a) {
                    return a ? this.each(function (x) {
                        var e;
                        'className' in this && (d = [], e = D(this), j(this, a, x, e).split(/\s+/g).forEach(function (x) {
                            s(this).hasClass(x) || d.push(x)
                        }, this), d.length && D(this, e + (e ? ' ' : '') + d.join(' ')))
                    }) : this
                },
                removeClass: function (e) {
                    return this.each(function (x) {
                        if ('className' in this) {
                            if (void 0 === e) return D(this, '');
                            d = D(this), j(this, e, x, d).split(/\s+/g).forEach(function (x) {
                                d = d.replace(E(x), ' ')
                            }), D(this, d.trim())
                        }
                    })
                },
                toggleClass: function (a, d) {
                    return a ? this.each(function (x) {
                        var e = s(this);
                        j(this, a, x, D(this)).split(/\s+/g).forEach(function (x) {
                            (void 0 === d ? !e.hasClass(x) : d) ? e.addClass(x) : e.removeClass(x)
                        })
                    }) : this
                },
                scrollTop: function (x) {
                    if (this.length) {
                        var e = 'scrollTop' in this[0];
                        return void 0 === x ? e ? this[0].scrollTop : this[0].pageYOffset : this.each(e ? function () {
                            this.scrollTop = x
                        } : function () {
                            this.scrollTo(this.scrollX, x)
                        })
                    }
                },
                scrollLeft: function (x) {
                    if (this.length) {
                        var e = 'scrollLeft' in this[0];
                        return void 0 === x ? e ? this[0].scrollLeft : this[0].pageXOffset : this.each(e ? function () {
                            this.scrollLeft = x
                        } : function () {
                            this.scrollTo(x, this.scrollY)
                        })
                    }
                },
                position: function () {
                    if (this.length) {
                        var x = this[0], e = this.offsetParent(), a = this.offset(),
                            d = v.test(e[0].nodeName) ? {top: 0, left: 0} : e.offset();
                        return a.top -= parseFloat(s(x).css('margin-top')) || 0, a.left -= parseFloat(s(x).css('margin-left')) || 0, d.top += parseFloat(s(e[0]).css('border-top-width')) || 0, d.left += parseFloat(s(e[0]).css('border-left-width')) || 0, {
                            top: a.top - d.top,
                            left: a.left - d.left
                        }
                    }
                },
                offsetParent: function () {
                    return this.map(function () {
                        for (var x = this.offsetParent || o.body; x && !v.test(x.nodeName) && 'static' == s(x).css('position');) x = x.offsetParent;
                        return x
                    })
                }
            }, s.fn.detach = s.fn.remove, ['width', 'height'].forEach(function (d) {
                var f = d.replace(/./, function (x) {
                    return x[0].toUpperCase()
                });
                s.fn[d] = function (e) {
                    var x, a = this[0];
                    return void 0 === e ? T(a) ? a['inner' + f] : U(a) ? a.documentElement['scroll' + f] : (x = this.offset()) && x[d] : this.each(function (x) {
                        (a = s(this)).css(d, j(this, e, x, a[d]()))
                    })
                }
            }), ['after', 'prepend', 'before', 'append'].forEach(function (e, _) {
                var i = _ % 2;
                s.fn[e] = function () {
                    var a, d, f = s.map(arguments, function (x) {
                        var e = [];
                        return 'array' == (a = z(x)) ? (x.forEach(function (x) {
                            return void 0 !== x.nodeType ? e.push(x) : s.zepto.isZ(x) ? e = e.concat(x.get()) : void (e = e.concat(R.fragment(x)))
                        }), e) : 'object' == a || null == x ? x : R.fragment(x)
                    }), t = 1 < this.length;
                    return f.length < 1 ? this : this.each(function (x, e) {
                        d = i ? e : e.parentNode, e = 0 == _ ? e.nextSibling : 1 == _ ? e.firstChild : 2 == _ ? e : null;
                        var a = s.contains(o.documentElement, d);
                        f.forEach(function (x) {
                            if (t) x = x.cloneNode(!0); else if (!d) return s(x).remove();
                            d.insertBefore(x, e), a && function x(e, a) {
                                a(e);
                                for (var d = 0, f = e.childNodes.length; d < f; d++) x(e.childNodes[d], a)
                            }(x, function (x) {
                                var e;
                                null == x.nodeName || 'SCRIPT' !== x.nodeName.toUpperCase() || x.type && 'text/javascript' !== x.type || x.src || (e = x.ownerDocument ? x.ownerDocument.defaultView : window).eval.call(e, x.innerHTML)
                            })
                        })
                    })
                }, s.fn[i ? e + 'To' : 'insert' + (_ ? 'Before' : 'After')] = function (x) {
                    return s(x)[e](this), this
                }
            }), R.Z.prototype = H.prototype = s.fn, R.uniq = f, R.deserializeValue = P, s.zepto = R, s);

        function z(x) {
            return null == x ? String(x) : X[g.call(x)] || 'object'
        }

        function J(x) {
            return 'function' == z(x)
        }

        function T(x) {
            return null != x && x == x.window
        }

        function U(x) {
            return null != x && x.nodeType == x.DOCUMENT_NODE
        }

        function Q(x) {
            return 'object' == z(x)
        }

        function L(x) {
            return Q(x) && !T(x) && Object.getPrototypeOf(x) == Object.prototype
        }

        function I(x) {
            var e = !!x && 'length' in x && x.length, a = s.type(x);
            return 'function' != a && !T(x) && ('array' == a || 0 === e || 'number' == typeof e && 0 < e && e - 1 in x)
        }

        function A(x) {
            return x.replace(/::/g, '/').replace(/([A-Z]+)([A-Z][a-z])/g, '$1_$2').replace(/([a-z\d])([A-Z])/g, '$1_$2').replace(/_/g, '-').toLowerCase()
        }

        function E(x) {
            return x in u ? u[x] : u[x] = new RegExp('(^|\\s)' + x + '(\s|$)')
        }

        function S(x, e) {
            return 'number' != typeof e || b[A(x)] ? e : e + 'px'
        }

        function B(x) {
            return 'children' in x ? c.call(x.children) : s.map(x.childNodes, function (x) {
                if (1 == x.nodeType) return x
            })
        }

        function H(x, e) {
            for (var a = x ? x.length : 0, d = 0; d < a; d++) this[d] = x[d];
            this.length = a, this.selector = e || ''
        }

        function C(x, e) {
            return null == e ? s(x) : s(x).filter(e)
        }

        function j(x, e, a, d) {
            return J(e) ? e.call(x, a, d) : e
        }

        function O(x, e, a) {
            null == a ? x.removeAttribute(e) : x.setAttribute(e, a)
        }

        function D(x, e) {
            var a = x.className || '', d = a && void 0 !== a.baseVal;
            if (void 0 === e) return d ? a.baseVal : a;
            d ? a.baseVal = e : x.className = e
        }

        function P(e) {
            try {
                return e && ('true' == e || 'false' != e && ('null' == e ? null : +e + '' == e ? +e : /^[\[\{]/.test(e) ? s.parseJSON(e) : e))
            } catch (x) {
                return e
            }
        }

        !function (h) {
            function n(x) {
                return 'string' == _typeof(x)
            }

            var e = 1, r = Array.prototype.slice, c = h.isFunction, o = {}, t = {}, a = 'onfocusin' in window,
                d = {focus: 'focusin', blur: 'focusout'}, l = {mouseenter: 'mouseover', mouseleave: 'mouseout'};

            function u(x) {
                return x._zid || (x._zid = e++)
            }

            function _(x, e, a, d) {
                var f, t;
                return (e = b(e)).ns && (t = e.ns, f = new RegExp('(?:^| )' + t.replace(' ', ' .* ?') + '(?: |$)')), (o[u(x)] || []).filter(function (x) {
                    return x && (!e.e || x.e == e.e) && (!e.ns || f.test(x.ns)) && (!a || u(x.fn) === u(a)) && (!d || x.sel == d)
                })
            }

            function b(x) {
                x = ('' + x).split('.');
                return {e: x[0], ns: x.slice(1).sort().join(' ')}
            }

            function m(x, e) {
                return x.del && !a && x.e in d || !!e
            }

            function p(x) {
                return l[x] || a && d[x] || x
            }

            function y(d, x, f, t, _, i, s) {
                var e = u(d), n = o[e] || (o[e] = []);
                x.split(/\s/).forEach(function (x) {
                    if ('ready' == x) return h(document).ready(f);
                    var e = b(x);
                    e.fn = f, e.sel = _, e.e in l && (f = function (x) {
                        x = x.relatedTarget;
                        if (!x || x !== this && !h.contains(this, x)) return e.fn.apply(this, arguments)
                    });
                    var a = (e.del = i) || f;
                    e.proxy = function (x) {
                        if (!(x = M(x)).isImmediatePropagationStopped()) {
                            x.data = t;
                            var e = a.apply(d, null == x._args ? [x] : [x].concat(x._args));
                            return !1 === e && (x.preventDefault(), x.stopPropagation()), e
                        }
                    }, e.i = n.length, n.push(e), 'addEventListener' in d && d.addEventListener(p(e.e), e.proxy, m(e, s))
                })
            }

            function v(e, x, a, d, f) {
                var t = u(e);
                (x || '').split(/\s/).forEach(function (x) {
                    _(e, x, a, d).forEach(function (x) {
                        delete o[t][x.i], 'removeEventListener' in e && e.removeEventListener(p(x.e), x.proxy, m(x, f))
                    })
                })
            }

            t.click = t.mousedown = t.mouseup = t.mousemove = 'MouseEvents', h.event = {
                add: y,
                remove: v
            }, h.proxy = function (x, e) {
                var a = 2 in arguments && r.call(arguments, 2);
                if (c(x)) {
                    var d = function () {
                        return x.apply(e, a ? a.concat(r.call(arguments)) : arguments)
                    };
                    return d._zid = u(x), d
                }
                if (n(e)) return a ? (a.unshift(x[e], x), h.proxy.apply(null, a)) : h.proxy(x[e], x);
                throw new TypeError('expected function')
            }, h.fn.bind = function (x, e, a) {
                return this.on(x, e, a)
            }, h.fn.unbind = function (x, e) {
                return this.off(x, e)
            }, h.fn.one = function (x, e, a, d) {
                return this.on(x, e, a, d, 1)
            };
            var i = function () {
                return !0
            }, Z = function () {
                return !1
            }, f = /^([A-Z]|returnValue$|layer[XY]$|webkitMovement[XY]$)/, x = {
                preventDefault: 'isDefaultPrevented',
                stopImmediatePropagation: 'isImmediatePropagationStopped',
                stopPropagation: 'isPropagationStopped'
            };

            function M(d, f) {
                return !f && d.isDefaultPrevented || (f = f || d, h.each(x, function (x, e) {
                    var a = f[x];
                    d[x] = function () {
                        return this[e] = i, a && a.apply(f, arguments)
                    }, d[e] = Z
                }), d.timeStamp || (d.timeStamp = Date.now()), (void 0 !== f.defaultPrevented ? f.defaultPrevented : 'returnValue' in f ? !1 === f.returnValue : f.getPreventDefault && f.getPreventDefault()) && (d.isDefaultPrevented = i)), d
            }

            function V(x) {
                var e, a = {originalEvent: x};
                for (e in x) f.test(e) || void 0 === x[e] || (a[e] = x[e]);
                return M(a, x)
            }

            h.fn.delegate = function (x, e, a) {
                return this.on(e, x, a)
            }, h.fn.undelegate = function (x, e, a) {
                return this.off(e, x, a)
            }, h.fn.live = function (x, e) {
                return h(document.body).delegate(this.selector, x, e), this
            }, h.fn.die = function (x, e) {
                return h(document.body).undelegate(this.selector, x, e), this
            }, h.fn.on = function (e, d, f, t, _) {
                var i, s, a = this;
                return e && !n(e) ? (h.each(e, function (x, e) {
                    a.on(x, d, f, e, _)
                }), a) : (n(d) || c(t) || !1 === t || (t = f, f = d, d = void 0), void 0 !== t && !1 !== f || (t = f, f = void 0), !1 === t && (t = Z), a.each(function (x, a) {
                    _ && (i = function (x) {
                        return v(a, x.type, t), t.apply(this, arguments)
                    }), d && (s = function (x) {
                        var e = h(x.target).closest(d, a).get(0);
                        if (e && e !== a) return x = h.extend(V(x), {
                            currentTarget: e,
                            liveFired: a
                        }), (i || t).apply(e, [x].concat(r.call(arguments, 1)))
                    }), y(a, e, t, f, d, s || i)
                }))
            }, h.fn.off = function (x, a, e) {
                var d = this;
                return x && !n(x) ? (h.each(x, function (x, e) {
                    d.off(x, a, e)
                }), d) : (n(a) || c(e) || !1 === e || (e = a, a = void 0), !1 === e && (e = Z), d.each(function () {
                    v(this, x, e, a)
                }))
            }, h.fn.trigger = function (x, e) {
                return (x = n(x) || h.isPlainObject(x) ? h.Event(x) : M(x))._args = e, this.each(function () {
                    x.type in d && 'function' == _typeof(this[x.type]) ? this[x.type]() : 'dispatchEvent' in this ? this.dispatchEvent(x) : h(this).triggerHandler(x, e)
                })
            }, h.fn.triggerHandler = function (a, d) {
                var f, t;
                return this.each(function (x, e) {
                    (f = V(n(a) ? h.Event(a) : a))._args = d, f.target = e, h.each(_(e, a.type || a), function (x, e) {
                        if (t = e.proxy(f), f.isImmediatePropagationStopped()) return !1
                    })
                }), t
            }, 'focusin focusout focus blur load resize scroll unload click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave change select keydown keypress keyup error'.split(' ').forEach(function (e) {
                h.fn[e] = function (x) {
                    return 0 in arguments ? this.bind(e, x) : this.trigger(e)
                }
            }), h.Event = function (x, e) {
                n(x) || (x = (e = x).type);
                var a = document.createEvent(t[x] || 'Events'), d = !0;
                if (e) for (var f in e) 'bubbles' == f ? d = !!e[f] : a[f] = e[f];
                return a.initEvent(x, d, !0), M(a)
            }
        }(F), function (c) {
            var o, l, h = +new Date, u = window.document, i = /<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi,
                b = /^(?:text|application)\/javascript/i, m = /^(?:text|application)\/xml/i, p = /^\s*$/,
                y = u.createElement('a');

            function v(x, e, a, d) {
                if (x.global) return function (x, e, a) {
                    e = c.Event(e);
                    return c(x).trigger(e, a), !e.isDefaultPrevented()
                }(e || u, a, d)
            }

            function Z(x, e) {
                var a = e.context;
                if (!1 === e.beforeSend.call(a, x, e) || !1 === v(e, a, 'ajaxBeforeSend', [x, e])) return !1;
                v(e, a, 'ajaxSend', [x, e])
            }

            function M(x, e, a, d) {
                var f = a.context;
                a.success.call(f, x, 'success', e), d && d.resolveWith(f, [x, 'success', e]), v(a, f, 'ajaxSuccess', [e, a, x]), _('success', e, a)
            }

            function V(x, e, a, d, f) {
                var t = d.context;
                d.error.call(t, a, e, x), f && f.rejectWith(t, [a, e, x]), v(d, t, 'ajaxError', [a, d, x || e]), _(e, a, d)
            }

            function _(x, e, a) {
                var d = a.context;
                a.complete.call(d, e, x), v(a, d, 'ajaxComplete', [e, a]), (a = a).global && !--c.active && v(a, null, 'ajaxStop')
            }

            function G() {
            }

            function W(x, e) {
                return '' == e ? x : (x + '&' + e).replace(/[&?]{1,2}/, '?')
            }

            function s(x, e, a, d) {
                return c.isFunction(e) && (d = a, a = e, e = void 0), c.isFunction(a) || (d = a, a = void 0), {
                    url: x,
                    data: e,
                    success: a,
                    dataType: d
                }
            }

            y.href = window.location.href, c.active = 0, c.ajaxJSONP = function (a, d) {
                if (!('type' in a)) return c.ajax(a);

                function x(x) {
                    c(i).triggerHandler('error', x || 'abort')
                }

                var f, t, e = a.jsonpCallback, _ = (c.isFunction(e) ? e() : e) || 'Zepto' + h++,
                    i = u.createElement('script'), s = window[_], n = {abort: x};
                return d && d.promise(n), c(i).on('load error', function (x, e) {
                    clearTimeout(t), c(i).off().remove(), 'error' != x.type && f ? M(f[0], n, a, d) : V(null, e || 'error', n, a, d), window[_] = s, f && c.isFunction(s) && s(f[0]), s = f = void 0
                }), !1 === Z(n, a) ? x('abort') : (window[_] = function () {
                    f = arguments
                }, i.src = a.url.replace(/\?(.+)=\?/, '?$1=' + _), u.head.appendChild(i), 0 < a.timeout && (t = setTimeout(function () {
                    x('timeout')
                }, a.timeout))), n
            }, c.ajaxSettings = {
                type: 'GET',
                beforeSend: G,
                success: G,
                error: G,
                complete: G,
                context: null,
                global: !0,
                xhr: function () {
                    return new window.XMLHttpRequest
                },
                accepts: {
                    script: 'text/javascript, application/javascript, application/x-javascript',
                    json: 'application/json',
                    xml: 'application/xml, text/xml',
                    html: 'text/html',
                    text: 'text/plain'
                },
                crossDomain: !1,
                timeout: 0,
                processData: !0,
                cache: !0,
                dataFilter: G
            }, c.ajax = function (x) {
                var e, d = c.extend({}, x || {}), f = c.Deferred && c.Deferred();
                for (o in c.ajaxSettings) void 0 === d[o] && (d[o] = c.ajaxSettings[o]);
                (e = d).global && 0 == c.active++ && v(e, null, 'ajaxStart'), d.crossDomain || ((a = u.createElement('a')).href = d.url, d.crossDomain = y.protocol + '//' + y.host != a.protocol + '//' + a.host), d.url || (d.url = window.location.toString()), -1 < (a = d.url.indexOf('#')) && (d.url = d.url.slice(0, a)), (a = d).processData && a.data && 'string' != c.type(a.data) && (a.data = c.param(a.data, a.traditional)), !a.data || a.type && 'GET' != a.type.toUpperCase() && 'jsonp' != a.dataType || (a.url = W(a.url, a.data), a.data = void 0);
                var t = d.dataType, a = /\?.+=\?/.test(d.url);
                if (a && (t = 'jsonp'), !1 !== d.cache && (x && !0 === x.cache || 'script' != t && 'jsonp' != t) || (d.url = W(d.url, '_=' + Date.now())), 'jsonp' == t) return a || (d.url = W(d.url, d.jsonp ? d.jsonp + '=?' : !1 === d.jsonp ? '' : 'callback=?')), c.ajaxJSONP(d, f);

                function _(x, e) {
                    s[x.toLowerCase()] = [x, e]
                }

                var i, a = d.accepts[t], s = {},
                    n = /^([\w-]+:)\/\//.test(d.url) ? RegExp.$1 : window.location.protocol, h = d.xhr(),
                    r = h.setRequestHeader;
                if (f && f.promise(h), d.crossDomain || _('X-Requested-With', 'XMLHttpRequest'), _('Accept', a || '*/*'), (a = d.mimeType || a) && (-1 < a.indexOf(',') && (a = a.split(',', 2)[0]), h.overrideMimeType && h.overrideMimeType(a)), (d.contentType || !1 !== d.contentType && d.data && 'GET' != d.type.toUpperCase()) && _('Content-Type', d.contentType || 'application/x-www-form-urlencoded'), d.headers) for (l in d.headers) _(l, d.headers[l]);
                if (h.setRequestHeader = _, !(h.onreadystatechange = function () {
                    if (4 == h.readyState) {
                        h.onreadystatechange = G, clearTimeout(i);
                        var x, e = !1;
                        if (200 <= h.status && h.status < 300 || 304 == h.status || 0 == h.status && 'file:' == n) {
                            if (t = t || ((a = (a = d.mimeType || h.getResponseHeader('content-type')) && a.split(';', 2)[0]) && ('text/html' == a ? 'html' : 'application/json' == a ? 'json' : b.test(a) ? 'script' : m.test(a) && 'xml') || 'text'), 'arraybuffer' == h.responseType || 'blob' == h.responseType) x = h.response; else {
                                x = h.responseText;
                                try {
                                    x = function (x, e, a) {
                                        if (a.dataFilter == G) return x;
                                        var d = a.context;
                                        return a.dataFilter.call(d, x, e)
                                    }(x, t, d), 'script' == t ? (0, eval)(x) : 'xml' == t ? x = h.responseXML : 'json' == t && (x = p.test(x) ? null : c.parseJSON(x))
                                } catch (x) {
                                    e = x
                                }
                                if (e) return V(e, 'parsererror', h, d, f)
                            }
                            M(x, h, d, f)
                        } else V(h.statusText || null, h.status ? 'error' : 'abort', h, d, f)
                    }
                    var a
                }) === Z(h, d)) return h.abort(), V(null, 'abort', h, d, f), h;
                a = !('async' in d) || d.async;
                if (h.open(d.type, d.url, a, d.username, d.password), d.xhrFields) for (l in d.xhrFields) h[l] = d.xhrFields[l];
                for (l in s) r.apply(h, s[l]);
                return 0 < d.timeout && (i = setTimeout(function () {
                    h.onreadystatechange = G, h.abort(), V(null, 'timeout', h, d, f)
                }, d.timeout)), h.send(d.data ? d.data : null), h
            }, c.get = function () {
                return c.ajax(s.apply(null, arguments))
            }, c.post = function () {
                var x = s.apply(null, arguments);
                return x.type = 'POST', c.ajax(x)
            }, c.getJSON = function () {
                var x = s.apply(null, arguments);
                return x.dataType = 'json', c.ajax(x)
            }, c.fn.load = function (x, e, a) {
                if (!this.length) return this;
                var d, f = this, t = x.split(/\s/), a = s(x, e, a), _ = a.success;
                return 1 < t.length && (a.url = t[0], d = t[1]), a.success = function (x) {
                    f.html(d ? c('<div>').html(x.replace(i, '')).find(d) : x), _ && _.apply(f, arguments)
                }, c.ajax(a), this
            };
            var d = encodeURIComponent;
            c.param = function (x, e) {
                var a = [];
                return a.add = function (x, e) {
                    null == (e = c.isFunction(e) ? e() : e) && (e = ''), this.push(d(x) + '=' + d(e))
                }, function a(d, x, f, t) {
                    var _, i = c.isArray(x), s = c.isPlainObject(x);
                    c.each(x, function (x, e) {
                        _ = c.type(e), t && (x = f ? t : t + '[' + (s || 'object' == _ || 'array' == _ ? x : '') + ']'), !t && i ? d.add(e.name, e.value) : 'array' == _ || !f && 'object' == _ ? a(d, e, f, x) : d.add(x, e)
                    })
                }(a, x, e), a.join('&').replace(/%20/g, '+')
            }
        }(F), (t = F).fn.serializeArray = function () {
            var a, d, f = [];
            return this[0] && t.each(this[0].elements, function (x, e) {
                d = e.type, (a = e.name) && 'fieldset' != e.nodeName.toLowerCase() && !e.disabled && 'submit' != d && 'reset' != d && 'button' != d && 'file' != d && ('radio' != d && 'checkbox' != d || e.checked) && function x(e) {
                    return e.forEach ? e.forEach(x) : void f.push({name: a, value: e})
                }(t(e).val())
            }), f
        }, t.fn.serialize = function () {
            var e = [];
            return this.serializeArray().forEach(function (x) {
                e.push(encodeURIComponent(x.name) + '=' + encodeURIComponent(x.value))
            }), e.join('&')
        }, t.fn.submit = function (x) {
            return 0 in arguments ? this.bind('submit', x) : this.length && (x = t.Event('submit'), this.eq(0).trigger(x), x.isDefaultPrevented() || this.get(0).submit()), this
        }, function () {
            try {
                getComputedStyle(void 0)
            } catch (x) {
                var a = getComputedStyle;
                window.getComputedStyle = function (x, e) {
                    try {
                        return a(x, e)
                    } catch (x) {
                        return null
                    }
                }
            }
        }(), window.jQuery && (F = window.jQuery)
    }, function (x, e, a) {
        'use strict';
        a.r(e), a.d(e, 'isNumber', function () {
            return d
        }), a.d(e, 'isString', function () {
            return f
        }), a.d(e, 'isArray', function () {
            return t
        }), a.d(e, 'alert', function () {
            return i
        }), a.d(e, 'msg', function () {
            return n
        }), a.d(e, 'getRequest', function () {
            return h
        }), a.d(e, 'getRequestByName', function () {
            return r
        }), a.d(e, 'randomLatLng', function () {
            return o
        }), a.d(e, 'random', function () {
            return l
        }), a.d(e, 'clone', function () {
            return u
        }), a.d(e, 'template', function () {
            return b
        }), a.d(e, 'formatDegree', function () {
            return m
        });
        var e = a(4), s = a.n(e), e = a(0), _ = a.n(e);

        function d(x) {
            return 'number' == typeof x && x.constructor == Number
        }

        function f(x) {
            return 'string' == _typeof(x) && x.constructor == String
        }

        var t = Array.isArray || function (x) {
            return '[object Array]' === Object.prototype.toString.call(x)
        };

        function i(x, e) {
            window.haoutil && window.haoutil.alert ? window.haoutil.alert(x, e) : window.layer ? window.layer.alert(x, {
                title: e || '提示',
                skin: 'layui-layer-lan layer-mars-dialog',
                closeBtn: 0,
                anim: 0
            }) : window.alert(x)
        }

        function n(x) {
            window.haoutil && window.haoutil.msg ? window.haoutil.msg(x) : window.toastr ? window.toastr.info(x) : window.layer ? window.layer.msg(x) : window.alert(x)
        }

        function h() {
            var x = location.search, e = new Object;
            if (-1 != x.indexOf('?')) for (var a = x.substr(1).split('&'), d = 0; d < a.length; d++) e[a[d].split('=')[0]] = decodeURI(a[d].split('=')[1]);
            return e
        }

        function r(x) {
            x = new RegExp('(^|&)' + x + '=([^&]*)(&|$)', 'i'), x = window.location.search.substr(1).match(x);
            return null != x ? decodeURI(x[2]) : null
        }

        var c = _.a.geoJSON({
            type: 'Feature', geometry: {
                type: 'Polygon',
                coordinates: [[[114.02709960937501, 22.59372606392931], [113.59313964843751, 23.03929774776974], [113.48327636718751, 22.253512814974744], [110.24230957031251, 21.401933838235188], [107.88574218750001, 22.522705703482472], [105.27099609375, 23.664650731631625], [104.0625, 22.998851594142923], [99.93164062500001, 22.471954507739227], [98.85498046875001, 25.025884063244828], [99.84375, 29.065772888415406], [92.57080078125001, 28.65203063036226], [85.53955078125001, 29.219302076779456], [80.61767578125001, 31.690781806136822], [79.69482421875, 34.903952965590065], [75.49804687500001, 37.26530995561875], [74.95971679687501, 39.45316112807394], [81.82617187500001, 42.74701217318067], [81.5185546875, 44.25306865928177], [84.04541015625001, 46.042735653846506], [87.72583007812501, 48.026672195436014], [90.5712890625, 45.10454630976873], [96.45996093750001, 42.32606244456202], [105.71044921875001, 41.19518982948959], [112.0166015625, 42.84375132629023], [117.02636718750001, 45.73685954736049], [120.60791015625, 46.55886030311719], [117.83935546875001, 48.922499263758255], [122.65136718750001, 53.10721669189343], [127.13378906250001, 49.53946900793534], [130.97900390625003, 47.368594345213374], [134.45068359375003, 48.019324184801185], [131.11083984375003, 45.521743896993634], [130.51757812500003, 43.197167282501276], [126.46362304687501, 41.89409955811395], [121.94824218750001, 39.26628442213066], [122.54150390625001, 40.55554790286314], [121.70654296875001, 41.19518982948959], [119.46533203125001, 39.985538414809746], [118.64135742187501, 39.206718844918505], [117.52075195312501, 39.342794408952386], [117.43286132812501, 38.41055825094609], [118.56445312500001, 37.59682400108367], [119.080810546875, 36.96744946416934], [120.81665039062501, 37.326488613342086], [122.40966796875001, 37.22158045838649], [120.05859375000001, 36.35052700542766], [118.88305664062501, 35.10193405724608], [120.47607421875001, 33.422272258866045], [120.66284179687501, 31.784216884487385], [121.81640625000001, 30.996445897426373], [120.36621093750001, 30.477082932837682], [121.28906250000001, 29.19053283229458], [120.30029296875001, 27.31321389856826], [119.102783203125, 26.52956523826758], [118.45458984375001, 24.9163314045991], [116.71875000000001, 23.805449612314625], [115.04882812500001, 22.84707068783908], [114.02709960937501, 22.59372606392931]]]
            }
        }).getLayers()[0];

        function o(x) {
            var e = (x = null == x ? c : x).getBounds(), a = e.getSouthWest(), e = e.getNorthEast(),
                d = l(1e6 * a.lng, 1e6 * e.lng) / 1e6, f = l(1e6 * a.lat, 1e6 * e.lat) / 1e6;
            try {
                var t = window.turf.point([d, f]);
                if (!window.turf.inside(t, x.toGeoJSON())) return o(x)
            } catch (x) {
            }
            return _.a.latLng(f, d)
        }

        function l(x, e) {
            return Math.floor(Math.random() * (e - x + 1) + x)
        }

        function u(x, e) {
            if (null == e && (e = 5), null == x || 'object' != s()(x)) return x;
            if (x instanceof Date) {
                var a = new Date;
                return a.setTime(x.getTime()), a
            }
            if (Array.isArray(x) && 0 <= e) {
                for (var d = [], f = 0, t = x.length; f < t; ++f) d[f] = u(x[f], e - 1);
                return d
            }
            if ('object' === s()(x) && 0 <= e) {
                var _, i = {};
                for (_ in x) 'function' != _typeof(_) && '_layer' != _ && '_layers' != _ && '_parent' != _ && x.hasOwnProperty(_) && (i[_] = u(x[_], e - 1));
                return i
            }
            return x
        }

        function b(x, e) {
            if (null == x) return x;
            for (var a in e) {
                var d = e[a];
                null != d && 'Null' != d && 'Unknown' != d || (d = ''), x = x.replace(new RegExp('{' + a + '}', 'gm'), d)
            }
            return x
        }

        function m(x) {
            x = Math.abs(x);
            var e = Math.floor(x);
            return e + '° ' + Math.floor(60 * (x - e)) + '\'  ' + Math.round(3600 * (x - e) % 60) + '\''
        }
    }, function (x, e, a) {
        'use strict';
        a.d(e, 'a', function () {
            return t
        }), a.d(e, 'b', function () {
            return _
        });
        var e = a(0), d = a.n(e), f = (a(13), a(7)),
            a = new d.a.Proj.CRS('EPSG:4490', '+proj=longlat +ellps=GRS80 +no_defs', {
                resolutions: [1.40625, .703125, .3515625, .17578125, .087890625, .0439453125, .02197265625, .010986328125, .0054931640625, .00274658203125, .001373291015625, .0006866455206208891, .00034332276031044456, .00017166138015522228, 8583069007761114e-20, 4291534503880557e-20, 21457672519402785e-21, 10728836259701392e-21, 5364418129850696e-21, 2682209064925349e-21, 13411045324626745e-22, 6.705522537e-7],
                origin: [-180, 90],
                bounds: d.a.bounds([-180, -90], [180, 90])
            }), t = d.a.TileLayer.extend({
                layername: 'vec', initialize: function (x) {
                    x = x || {}, this.layername = x.layer || this.layername, this._key = x.key || f.tiandituArr, d.a.setOptions(this, x)
                }, getTileUrl: function (x) {
                    var e = x.y, a = x.x, d = x.z;
                    this.options.zOffset && (d += Number(this.options.zOffset));
                    var f = 'http://t' + parseInt(7 * Math.random()) + '.tianditu.gov.cn/' + this.layername + '_c/wmts',
                        x = i(this._key);
                    return f + '?Service=WMTS&Request=GetTile&Version=1.0.0&Style=Default&Format=tiles&serviceMode=KVP&layer=' + this.layername + '&TileMatrixSet=c&TileMatrix=' + d + '&TileRow=' + e + '&TileCol=' + a + '&tk=' + x
                }
            }), _ = d.a.TileLayer.extend({
                layername: 'vec', initialize: function (x) {
                    x = x || {}, this.layername = x.layer || this.layername, this._key = x.key || '.87949882c75775b5069a0076357b7530', d.a.setOptions(this, x)
                }, getTileUrl: function (x) {
                    var e = x.y, a = x.x, d = x.z,
                        f = 'http://t' + parseInt(7 * Math.random()) + '.tianditu.gov.cn/' + this.layername + '_w/wmts',
                        x = i(this._key);
                    return f + '?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=' + this.layername + '&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&TILECOL=' + a + '&TILEROW=' + e + '&TILEMATRIX=' + d + '&tk=' + x
                }
            });

        function i(x) {
            return x[Math.floor(Math.random() * x.length + 1) - 1]
        }

        d.a.CRS.EPSG4490 = a, d.a.TileLayer.TDT = t, d.a.tileLayer.tdt = function (x) {
            return new t(x)
        }, d.a.TileLayer.TDTMercator = _, d.a.tileLayer.tdtMercator = function (x) {
            return new _(x)
        }
    }, function (e, x) {
        function a(x) {
            return 'function' == ('undefined' == typeof Symbol ? 'undefined' : _typeof(Symbol)) && 'symbol' == _typeof(Symbol.iterator) ? (e.exports = a = function (x) {
                return _typeof(x)
            }, e.exports.default = e.exports) : (e.exports = a = function (x) {
                return x && 'function' == typeof Symbol && x.constructor === Symbol && x !== Symbol.prototype ? 'symbol' : _typeof(x)
            }, e.exports.default = e.exports), e.exports.__esModule = !0, a(x)
        }

        e.exports = a, e.exports.default = e.exports, e.exports.__esModule = !0
    }, function (g4hA0000, eFaJ0030, iawz0211) {
        'use strict';
        iawz0211.r(eFaJ0030), iawz0211.d(eFaJ0030, 'createLayer', function () {
            return hqSj0000
        }), iawz0211.d(eFaJ0030, 'cloneLayer', function () {
            return frqK0110
        }), iawz0211.d(eFaJ0030, 'getDefaultBaseLayers', function () {
            return havY0210
        }), iawz0211.d(eFaJ0030, 'isInPoly', function () {
            return iPOm0000
        }), iawz0211.d(eFaJ0030, 'getLatlngs', function () {
            return jc7q0100
        }), iawz0211.d(eFaJ0030, 'getPopup', function () {
            return eb7b0300
        }), iawz0211.d(eFaJ0030, 'getPopupByConfig', function () {
            return gayl0000
        }), iawz0211.d(eFaJ0030, 'getMarkerOptionsByConfig', function () {
            return keuE0200
        }), iawz0211.d(eFaJ0030, 'getPolyOptionsByConfig', function () {
            return ie540300
        }), iawz0211.d(eFaJ0030, 'bindClickFeature', function () {
            return fvqq0100
        });
        var okcT0100 = iawz0211(4), iciA0110 = iawz0211.n(okcT0100), jcrl0100 = iawz0211(14),
            guec0022 = iawz0211.n(jcrl0100), e9ho0001 = iawz0211(0), iwxl0111 = iawz0211.n(e9ho0001),
            _0x3d4c5 = iawz0211(1), ljbc0022 = iawz0211(6), nzWI0000 = iawz0211(15), ifEu0101 = iawz0211(3),
            ezFa0102 = iawz0211(9), oe4N0100 = iawz0211(8), ignL0200 = iawz0211(10), i5HD0000 = iawz0211(11),
            jlvQ0010 = iawz0211(2), gjY40100;

        function eTcf0020(e, x) {
            var a, d = Object.keys(e);
            return Object.getOwnPropertySymbols && (a = Object.getOwnPropertySymbols(e), x && (a = a.filter(function (x) {
                return Object.getOwnPropertyDescriptor(e, x).enumerable
            })), d.push.apply(d, a)), d
        }

        function jzVf0000(e) {
            for (var x = 1; x < arguments.length; x++) {
                var a = null != arguments[x] ? arguments[x] : {};
                x % 2 ? eTcf0020(Object(a), !0).forEach(function (x) {
                    guec0022()(e, x, a[x])
                }) : Object.getOwnPropertyDescriptors ? Object.defineProperties(e, Object.getOwnPropertyDescriptors(a)) : eTcf0020(Object(a)).forEach(function (x) {
                    Object.defineProperty(e, x, Object.getOwnPropertyDescriptor(a, x))
                })
            }
            return e
        }

        function hqSj0000(d, x, e) {
            var f, a, t;
            switch (d.url && (x && (d.url = d.url.replace('$serverURL$', x)), d.url = d.url.replace('$hostname$', location.hostname).replace('$host$', location.host)), d.crs && (d._crs = d.crs, d.crs = null), d.type) {
                case 'group':
                    if (d.layers && 0 < d.layers.length) {
                        for (var _ = [], i = 0; i < d.layers.length; i++) {
                            var s = d.layers[i];
                            !s.crs && d._crs && (s.crs = d._crs);
                            var n = hqSj0000(s, x, e);
                            null != n && (s.hasOwnProperty('visible') && !s.visible || _.push(n))
                        }
                        f = iwxl0111.a.layerGroup(_)
                    }
                    break;
                case 'image':
                    f = iwxl0111.a.imageOverlay(d.url, d.bounds || [[0, 0], [1e3, 1e3]]);
                    break;
                case'tile':
                    f = iwxl0111.a.tileLayer(d.url, d);
                    break;
                case 'wms':
                    f = iwxl0111.a.tileLayer.wms(d.url, d);
                    break;
                case'wmts':
                    f = new oe4N0100.a(d.url, d);
                    break;
                case 'arcgis_cache':
                    f = new ljbc0022.a(d.url, d);
                    break;
                case 'arcgis_cache_compact':
                    f = new nzWI0000.a(d.url, d);
                    break;
                case 'arcgis_tile':
                    null == iwxl0111.a.esri && console.error('请引入esri-leaflet插件'), f = iwxl0111.a.esri.tiledMapLayer(d);
                    break;
                case 'arcgis_image':
                    null == iwxl0111.a.esri && console.error('请引入esri-leaflet插件'), f = iwxl0111.a.esri.imageMapLayer(d);
                    break;
                case 'arcgis_dynamic':
                    null == iwxl0111.a.esri && console.error('请引入esri-leaflet插件');
                    var h, r = {};
                    for (h in d) 'popup' != h && 'columns' != h && 'symbol' != h && (r[h] = d[h]);
                    f = iwxl0111.a.esri.dynamicMapLayer(r), (d.popup || d.columns) && (d.popup && 'string' == typeof d.popup && d.popup.endsWith('.html') && _0x3d4c5.a.ajax({
                        url: d.popup,
                        type: 'GET',
                        dataType: 'html',
                        success: function (x) {
                            d.popup = x
                        }
                    }), f.bindPopup(function (x, e, a) {
                        if (!f.hasOwnProperty('isPopup') || f.isPopup) {
                            if (null != x && 0 < x.code) return jlvQ0010.msg(x.message), !1;
                            if (0 == e.features.length) return !1;
                            gjY40100 && (x = iwxl0111.a.geoJSON(e).getLayers(), gjY40100('arcgis_dynamic', x[x.length - 1]));
                            e = e.features[e.features.length - 1].properties;
                            return gayl0000(d, e)
                        }
                    }, {maxWidth: 600}));
                    break;
                case 'arcgis_feature':
                    null == iwxl0111.a.esri && console.error('请引入esri-leaflet插件'), f = iwxl0111.a.esri.featureLayer(fKyK0010(d)), (d.popup || d.columns) && (d.popup && 'string' == _typeof(d.popup) && d.popup.endsWith('.html') && _0x3d4c5.a.ajax({
                        url: d.popup,
                        type: 'GET',
                        dataType: 'html',
                        success: function (x) {
                            d.popup = x
                        }
                    }), f.bindPopup(function (x) {
                        if (!f.hasOwnProperty('isPopup') || f.isPopup) {
                            gjY40100 && gjY40100('arcgis_feature', x);
                            x = x.feature.properties;
                            return gayl0000(d, x)
                        }
                    }, {maxWidth: 600})), d.tooltip && f.bindTooltip(function (x) {
                        x = x.feature.properties;
                        return lMgx0021(d, x)
                    }, {className: 'leafletlayer-tooltip', direction: 'top'});
                    break;
                case'geojson':
                    f = iwxl0111.a.geoJSON(null, fKyK0010(d)), (d.popup || d.columns) && (d.popup && 'string' == _typeof(d.popup) && d.popup.endsWith('.html') && _0x3d4c5.a.ajax({
                        url: d.popup,
                        type: 'GET',
                        dataType: 'html',
                        success: function (x) {
                            d.popup = x
                        }
                    }), f.bindPopup(function (x) {
                        if (!f.hasOwnProperty('isPopup') || f.isPopup) {
                            gjY40100 && gjY40100('arcgis_feature', x);
                            x = x.feature.properties;
                            return gayl0000(d, x)
                        }
                    }, {maxWidth: 600})), d.tooltip && f.bindTooltip(function (x) {
                        x = x.feature.properties;
                        return lMgx0021(d, x)
                    }, {
                        className: 'leafletlayer-tooltip',
                        direction: 'top'
                    }), d.data ? f.addData(d.data) : d.url && (f.reload = function () {
                        _0x3d4c5.a.ajax({
                            url: this.options.url,
                            data: this.options.filter || {},
                            type: this.options.ajaxType || 'get',
                            dataType: 'json',
                            success: function (x) {
                                f.clearLayers(), f.addData(x)
                            },
                            error: function (x, e, a) {
                                console.log('Json文件加载失败！', d)
                            }
                        })
                    }, f.reload());
                    break;
                case'www_tdt':
                    var c = jlvQ0010.clone(d);
                    switch (c.subdomains = '01234567', d._crs) {
                        default:
                            switch (d.layer) {
                                case'vec_d':
                                    c.layer = 'vec', f = new ifEu0101.b(c);
                                    break;
                                case 'vec_z':
                                    c.layer = 'cva', f = new ifEu0101.b(c);
                                    break;
                                case'img_d':
                                    c.layer = 'img', f = new ifEu0101.b(c);
                                    break;
                                case 'img_z':
                                    c.layer = 'cia', f = new ifEu0101.b(c);
                                    break;
                                case 'ter_d':
                                    c.layer = 'ter', f = new ifEu0101.b(c);
                                    break;
                                case 'ter_z':
                                    c.layer = 'cta', f = new ifEu0101.b(c);
                                    break;
                                case 'vec':
                                default:
                                    f = iwxl0111.a.layerGroup([hqSj0000(_0x3d4c5.a.extend(c, {
                                        name: '底图',
                                        layer: 'vec_d',
                                        crs: d._crs
                                    })), hqSj0000(_0x3d4c5.a.extend(c, {name: '注记', layer: 'vec_z', crs: d._crs}))]);
                                    break;
                                case 'img':
                                    f = iwxl0111.a.layerGroup([hqSj0000(_0x3d4c5.a.extend(c, {
                                        name: '底图',
                                        layer: 'img_d',
                                        crs: d._crs
                                    })), hqSj0000(_0x3d4c5.a.extend(c, {name: '注记', layer: 'img_z', crs: d._crs}))]);
                                    break;
                                case'ter':
                                    f = iwxl0111.a.layerGroup([hqSj0000(_0x3d4c5.a.extend(c, {
                                        name: '底图',
                                        layer: 'ter_d',
                                        crs: d._crs
                                    })), hqSj0000(_0x3d4c5.a.extend(c, {name: '注记', layer: 'ter_z', crs: d._crs}))])
                            }
                            break;
                        case'4326':
                        case'EPSG4326':
                            switch (c.zOffset = 1, d.layer) {
                                case'vec_d':
                                    c.layer = 'vec', f = new ifEu0101.a(c);
                                    break;
                                case 'vec_z':
                                    c.layer = 'cva', f = new ifEu0101.a(c);
                                    break;
                                case'img_d':
                                    c.layer = 'img', f = new ifEu0101.a(c);
                                    break;
                                case'img_z':
                                    c.layer = 'cia', f = new ifEu0101.a(c);
                                    break;
                                case 'ter_d':
                                    c.layer = 'ter', f = new ifEu0101.a(c);
                                    break;
                                case 'ter_z':
                                    c.layer = 'cta', f = new ifEu0101.a(c);
                                    break;
                                case 'vec':
                                default:
                                    f = iwxl0111.a.layerGroup([hqSj0000(_0x3d4c5.a.extend(c, {
                                        name: '底图',
                                        layer: 'vec_d',
                                        crs: d._crs
                                    })), hqSj0000(_0x3d4c5.a.extend(c, {name: '注记', layer: 'vec_z', crs: d._crs}))]);
                                    break;
                                case'img':
                                    f = iwxl0111.a.layerGroup([hqSj0000(_0x3d4c5.a.extend(c, {
                                        name: '底图',
                                        layer: 'img_d',
                                        crs: d._crs
                                    })), hqSj0000(_0x3d4c5.a.extend(c, {name: '注记', layer: 'img_z', crs: d._crs}))]);
                                    break;
                                case'ter':
                                    f = iwxl0111.a.layerGroup([hqSj0000(_0x3d4c5.a.extend(c, {
                                        name: '底图',
                                        layer: 'ter_d',
                                        crs: d._crs
                                    })), hqSj0000(_0x3d4c5.a.extend(c, {name: '注记', layer: 'ter_z', crs: d._crs}))])
                            }
                            break;
                        case '4490':
                        case 'EPSG4490':
                            switch (d.layer) {
                                case'vec_d':
                                    c.layer = 'vec', f = new ifEu0101.a(c);
                                    break;
                                case 'vec_z':
                                    c.layer = 'cva', f = new ifEu0101.a(c);
                                    break;
                                case 'img_d':
                                    c.layer = 'img', f = new ifEu0101.a(c);
                                    break;
                                case 'img_z':
                                    c.layer = 'cia', f = new ifEu0101.a(c);
                                    break;
                                case 'ter_d':
                                    c.layer = 'ter', f = new ifEu0101.a(c);
                                    break;
                                case 'ter_z':
                                    c.layer = 'cta', f = new ifEu0101.a(c);
                                    break;
                                case 'vec':
                                default:
                                    f = iwxl0111.a.layerGroup([hqSj0000(_0x3d4c5.a.extend(c, {
                                        name: '底图',
                                        layer: 'vec_d',
                                        crs: d._crs
                                    })), hqSj0000(_0x3d4c5.a.extend(c, {name: '注记', layer: 'vec_z', crs: d._crs}))]);
                                    break;
                                case 'img':
                                    f = iwxl0111.a.layerGroup([hqSj0000(_0x3d4c5.a.extend(c, {
                                        name: '底图',
                                        layer: 'img_d',
                                        crs: d._crs
                                    })), hqSj0000(_0x3d4c5.a.extend(c, {name: '注记', layer: 'img_z', crs: d._crs}))]);
                                    break;
                                case'ter':
                                    f = iwxl0111.a.layerGroup([hqSj0000(_0x3d4c5.a.extend(c, {
                                        name: '底图',
                                        layer: 'ter_d',
                                        crs: d._crs
                                    })), hqSj0000(_0x3d4c5.a.extend(c, {name: '注记', layer: 'ter_z', crs: d._crs}))])
                            }
                    }
                    break;
                case 'www_gaode':
                    for (var o in a = {subdomains: '1234'}, d) 'layer' != o && (a[o] = d[o]);
                    switch (d.layer) {
                        case 'vec':
                        default:
                            f = iwxl0111.a.tileLayer('http://' + (d.bigfont ? 'wprd' : 'webrd') + '0{s}.is.autonavi.com/appmaptile?lang=zh_cn&size=1&scale=1&style=8&x={x}&y={y}&z={z}', a);
                            break;
                        case 'img_d':
                            f = iwxl0111.a.tileLayer('http://webst0{s}.is.autonavi.com/appmaptile?style=6&x={x}&y={y}&z={z}', a);
                            break;
                        case 'img_z':
                            f = iwxl0111.a.tileLayer('http://webst0{s}.is.autonavi.com/appmaptile?x={x}&y={y}&z={z}&lang=zh_cn&size=1&scale=1&style=8', a);
                            break;
                        case'time':
                            f = iwxl0111.a.tileLayer('http://tm.amap.com/trafficengine/mapabc/traffictile?v=1.0&;t=1&x={x}&y={y}&z={z}&&t=' + (new Date).getTime(), a);
                            break;
                        case'img':
                            f = iwxl0111.a.layerGroup([hqSj0000(jzVf0000(jzVf0000({}, d), {}, {
                                name: '底图',
                                layer: 'img_d'
                            })), hqSj0000(jzVf0000(jzVf0000({}, d), {}, {name: '注记', layer: 'img_z'}))])
                    }
                    break;
                case 'www_google':
                    if ('4326' == d.crs || 'wgs84' == d.crs) {
                        d.layer, 'img_d';
                        f = iwxl0111.a.tileLayer('http://mt1.google.cn/vt/lyrs=s&x={x}&y={y}&z={z}')
                    } else {
                        for (var l in a = {subdomains: '123'}, d) 'layer' != l && (a[l] = d[l]);
                        switch (d.layer) {
                            case 'vec':
                            default:
                                f = iwxl0111.a.tileLayer('http://mt{s}.google.cn/vt/lyrs=m@207000000&hl=zh-CN&gl=CN&src=app&x={x}&y={y}&z={z}&s=Galile', a);
                                break;
                            case'img_d':
                                f = iwxl0111.a.tileLayer('http://mt{s}.google.cn/vt/lyrs=s&hl=zh-CN&gl=CN&x={x}&y={y}&z={z}&s=Gali', a);
                                break;
                            case 'img_z':
                                f = iwxl0111.a.tileLayer('http://mt{s}.google.cn/vt/imgtp=png32&lyrs=h@207000000&hl=zh-CN&gl=cn&x={x}&y={y}&z={z}&s=Galil', a);
                                break;
                            case 'img':
                                f = iwxl0111.a.layerGroup([hqSj0000(jzVf0000(jzVf0000({}, d), {}, {
                                    name: '底图',
                                    layer: 'img_d'
                                })), hqSj0000(jzVf0000(jzVf0000({}, d), {}, {name: '注记', layer: 'img_z'}))]);
                                break;
                            case 'ter':
                                f = iwxl0111.a.tileLayer('http://mt{s}.google.cn/vt/lyrs=t@131,r@227000000&hl=zh-CN&gl=cn&x={x}&y={y}&z={z}&s=Galile', a)
                        }
                    }
                    break;
                case 'www_osm':
                    for (var u in a = {subdomains: 'abc'}, d) a[u] = d[u];
                    f = iwxl0111.a.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', a);
                    break;
                case 'www_baidu':
                    f = Object(ezFa0102.a)(d);
                    break;
                case 'custom_jww':
                    f = new ignL0200.a({
                        interval: 10,
                        zoomIntervals: [{start: 0, end: 2, interval: 60}, {start: 3, end: 4, interval: 30}, {
                            start: 5,
                            end: 6,
                            interval: 10
                        }, {start: 7, end: 8, interval: 5}, {start: 9, end: 10, interval: 1}, {
                            start: 11,
                            end: 11,
                            interval: .5
                        }, {start: 12, end: 12, interval: .2}, {start: 13, end: 20, interval: .1}],
                        showOriginLabel: !0,
                        redraw: 'move'
                    });
                    break;
                case'custom_lighting':
                    f = new i5HD0000.a
            }
            return !e || (t = e(d, f)) && (f = t), null == f ? 'group' != d.type && console.log('配置中的图层未处理', d) : (f.config = d, f.name = d.name, d._layer = f), d._crs && (d.crs = d._crs, delete d._crs), f
        }

        function gayl0000(x, e) {
            var a = x.popupNameField ? e[x.popupNameField] : x.name;
            return x.popup ? eb7b0300(x.popup, e, a) : !!x.columns && eb7b0300(x.columns, e, a)
        }

        function lMgx0021(x, e) {
            var a = x.popupNameField ? e[x.popupNameField] : x.name;
            return !!x.tooltip && eb7b0300(x.tooltip, e, a)
        }

        function eb7b0300(ezdd0100, hxwe0113, gbsv0201) {
            if (gbsv0201 = gbsv0201 || '', Array.isArray(ezdd0100)) {
                for (var ittj0000 = 0, fQvb0003 = '<div class="mars-popup-titile">' + gbsv0201 + '</div><div class="mars-popup-content" >', qxJl0000 = 0; qxJl0000 < ezdd0100.length; qxJl0000++) {
                    var eKqc0000 = ezdd0100[qxJl0000];
                    if ('details' != eKqc0000.type) {
                        var ftDY0100 = _0x3d4c5.a.trim(hxwe0113[eKqc0000.field]);
                        if (eKqc0000.hasOwnProperty('show') && eKqc0000.show || null != ftDY0100 && '' != ftDY0100 && 'Null' != ftDY0100 && 'Unknown' != ftDY0100 && '0' != ftDY0100 && 0 != ftDY0100.length) {
                            if (eKqc0000.format) try {
                                ftDY0100 = eval(eKqc0000.format + '(\'' + ftDY0100 + '\')')
                            } catch (gvbo0031) {
                                console.log('getPopupByConfig error:' + eKqc0000.format)
                            }
                            eKqc0000.unit && (ftDY0100 += eKqc0000.unit), fQvb0003 += '<div><label>' + eKqc0000.name + '</label>' + (ftDY0100 || '') + '</div>', ittj0000++
                        }
                    } else {
                        var hclw0101 = _0x3d4c5.a.trim(hxwe0113[eKqc0000.field || 'OBJECTID']);
                        null != hclw0101 && '' != hclw0101 && 'Null' != hclw0101 && 'Unknown' != hclw0101 && (fQvb0003 += '<div style="text-align: center;padding: 10px 0;"><button type="button" onclick="' + eKqc0000.calback + '(\'' + hclw0101 + ');" " class="btn btn-info  btn-sm">' + (eKqc0000.name || '查看详情') + '</button></div>')
                    }
                }
                return fQvb0003 += '</div>', 0 != ittj0000 && fQvb0003
            }
            if ('object' !== iciA0110()(ezdd0100)) {
                if ('function' == _typeof(ezdd0100) && ezdd0100.constructor == Function) return ezdd0100(hxwe0113);
                if ('all' != ezdd0100) return jlvQ0010.template(ezdd0100, hxwe0113);
                var hive0103 = 0,
                    jxRd0000 = '<div class="mars-popup-titile">' + gbsv0201 + '</div><div class="mars-popup-content" >',
                    iQwD0000, gnzj0001;
                for (iQwD0000 in hxwe0113) {
                    'Shape' != iQwD0000 && 'FID' != iQwD0000 && 'OBJECTID' != iQwD0000 && (gnzj0001 = _0x3d4c5.a.trim(hxwe0113[iQwD0000]), null != gnzj0001 && '' != gnzj0001 && 'Null' != gnzj0001 && 'Unknown' != gnzj0001 && '0' != gnzj0001 && 0 != gnzj0001.length && (jxRd0000 += '<div><label>' + iQwD0000 + '</label>' + gnzj0001 + '</div>', hive0103++))
                }
                return jxRd0000 += '</div>', 0 != hive0103 && jxRd0000
            }
            switch (ezdd0100.type) {
                case 'iframe':
                    var izcZ0000 = jlvQ0010.template(ezdd0100.url, hxwe0113);
                    return '<iframe id=\'ifarm\' src=\'' + izcZ0000 + '"  style="width:' + (ezdd0100.width || '300') + 'px;height:' + (ezdd0100.height || '300') + 'px;overflow:hidden;margin:0;" scrolling="no" frameborder="0" ></iframe>';
                case 'javascript':
                    return eval(ezdd0100.calback + '(' + JSON.stringify(hxwe0113) + ')')
            }
            return !1
        }

        function fKyK0010(a) {
            var x, e = {};
            for (x in a) 'popup' != x && 'columns' != x && 'symbol' != x && (e[x] = a[x]);
            return a.symbol && a.symbol.iconOptions && (e.pointToLayer = function (x, e) {
                x = x.properties, x = keuE0200(a, x);
                return iwxl0111.a.marker(e, x)
            }), a.symbol && a.symbol.styleOptions && (e.style = function (x) {
                x = x.properties;
                return ie540300(a, x)
            }), e.coordsToLatLng = function (x) {
                if (window.map && window.map.convert2map) {
                    var e = window.map.convert2map({lat: x[1], lng: x[0]});
                    return new iwxl0111.a.LatLng(e[0], e[1], x[2])
                }
                return new iwxl0111.a.LatLng(x[1], x[0], x[2])
            }, e
        }

        function keuE0200(x, e, a) {
            var d, f = x.symbol, t = _0x3d4c5.a.extend({}, x.symbol.iconOptions);
            return f.iconField && (d = e[f.iconField], null != (d = x.symbol.iconFieldOptions[d]) && (t = _0x3d4c5.a.extend({}, t), t = _0x3d4c5.a.extend(t, d))), a && t.iconUrlForSelect && (t.iconUrl = t.iconUrlForSelect), t.hasOwnProperty('iconUrl') ? f.icon = iwxl0111.a.icon(t) : t.hasOwnProperty('iconFont') ? (d = 20, t.hasOwnProperty('iconSize') && (d = t.iconSize[0]), a = t.color || '#000000', t.className = '', t.html = '<i class="' + t.iconFont + '\' style=\'color:' + a + ';font-size:' + d + 'px;"></i> ', f.icon = iwxl0111.a.divIcon(t)) : (t = '<div class="centerat_animation" style="color:' + (t.color || '#0f89f5') + ';"><p></p></div>', f.nameField && (t += ' <div class="layer_divicon_name" style="top: 2px;left: 25px;" >' + e[f.nameField] + '</div>'), f.icon = iwxl0111.a.divIcon({
                className: '',
                iconSize: [10, 10],
                iconAnchor: [5, 5],
                popupAnchor: [5, -5],
                tooltipAnchor: [5, -5],
                html: t
            })), f
        }

        function ie540300(x, e) {
            var a = x.symbol, d = x.symbol.styleOptions;
            return a.styleField && (a = e[a.styleField], null != (a = x.symbol.styleFieldOptions[a]) && (d = jlvQ0010.clone(d), d = _0x3d4c5.a.extend(d, a))), d
        }

        function frqK0110(x) {
            var e = oxkT0010(x.options);
            if (x instanceof iwxl0111.a.SVG) return iwxl0111.a.svg(e);
            if (x instanceof iwxl0111.a.Canvas) return iwxl0111.a.canvas(e);
            if (x.config && x.config.type) {
                var a, d = {};
                for (a in x.config) '_layer' != a && (d[a] = x.config[a]);
                return hqSj0000(d)
            }
            if ('arcgis_cache' == e.type || x instanceof iwxl0111.a.TileLayer.ArcGISTile) return iwxl0111.a.tileLayer.arcGISTile(e.url, e);
            if ('wmts' == e.type || x instanceof iwxl0111.a.TileLayer.WMTS) return iwxl0111.a.tileLayer.wmts(x._url, e);
            if ('wms' == e.type || x instanceof iwxl0111.a.TileLayer.WMTS) return iwxl0111.a.tileLayer.wmts(x._url, e);
            if (x instanceof iwxl0111.a.TileLayer) return iwxl0111.a.tileLayer(x._url, e);
            if (x instanceof iwxl0111.a.ImageOverlay) return iwxl0111.a.imageOverlay(x._url, x._bounds, e);
            if (x instanceof iwxl0111.a.Marker) return iwxl0111.a.marker(x.getLatLng(), e);
            if (x instanceof iwxl0111.a.Circle) return iwxl0111.a.circle(x.getLatLng(), x.getRadius(), e);
            if (x instanceof iwxl0111.a.CircleMarker) return iwxl0111.a.circleMarker(x.getLatLng(), e);
            if (x instanceof iwxl0111.a.Rectangle) return iwxl0111.a.rectangle(x.getBounds(), e);
            if (x instanceof iwxl0111.a.Polygon) return iwxl0111.a.polygon(x.getLatLngs(), e);
            if (x instanceof iwxl0111.a.Polyline) return iwxl0111.a.polyline(x.getLatLngs(), e);
            if (x instanceof iwxl0111.a.GeoJSON) return iwxl0111.a.geoJson(x.toGeoJSON(), e);
            if (x instanceof iwxl0111.a.LayerGroup) return iwxl0111.a.layerGroup(iwoe0010(x));
            if (x instanceof iwxl0111.a.FeatureGroup) return iwxl0111.a.FeatureGroup(iwoe0010(x));
            throw 'Unknown layer, cannot clone this layer. Leaflet-version: ' + iwxl0111.a.version
        }

        function oxkT0010(x) {
            var e, a = {};
            for (e in x) {
                var d = x[e];
                d && d.clone ? a[e] = d.clone() : d instanceof iwxl0111.a.Layer ? a[e] = frqK0110(d) : a[e] = d
            }
            return a
        }

        function iwoe0010(x) {
            var e = [];
            return x.eachLayer(function (x) {
                e.push(frqK0110(x))
            }), e
        }

        function jc7q0100(x) {
            if (x instanceof iwxl0111.a.Marker || x instanceof iwxl0111.a.Circle || x instanceof iwxl0111.a.CircleMarker) return [x.getLatLng()];
            if (x instanceof iwxl0111.a.Rectangle) {
                var e = x.getBounds();
                return [e.getNorthWest(), e.getSouthEast()]
            }
            if (x instanceof iwxl0111.a.Polygon) {
                e = x.getLatLngs();
                return 0 == e.length ? e : e[0]
            }
            return x instanceof iwxl0111.a.Polyline ? x.getLatLngs() : []
        }

        function havY0210() {
            return [{
                name: '高德地图',
                type: 'www_gaode',
                layer: 'vec',
                icon: 'bingmap.png',
                visible: !0,
                crs: 'gcj'
            }, {name: '高德卫星', type: 'www_gaode', layer: 'img', icon: 'bingimage.png', crs: 'gcj'}, {
                name: '谷歌地图',
                type: 'www_google',
                layer: 'vec',
                icon: 'googlemap.png',
                crs: 'gcj'
            }, {name: '谷歌卫星', type: 'www_google', layer: 'img', icon: 'googleimage.png', crs: 'gcj'}, {
                name: '天地图',
                type: 'www_tdt',
                layer: 'vec',
                icon: 'tianditumap.png',
                crs: 'EPSG3857'
            }, {name: '天地图卫星', type: 'www_tdt', layer: 'img', icon: 'tiandituimage.png', crs: 'EPSG3857'}]
        }

        function iPOm0000(x, e) {
            return x instanceof iwxl0111.a.Circle ? iAbd0003(x, e) : x instanceof iwxl0111.a.Rectangle ? jiew0020(x, e) : x instanceof iwxl0111.a.Polygon ? jiFQ0000(x, e) : void 0
        }

        function iAbd0003(x, e) {
            var a = x.getRadius(), x = x.getLatLng();
            return Math.abs(x.distanceTo(e)) <= a
        }

        function jiew0020(x, e) {
            return x.getBounds().contains(e)
        }

        function nFlf0010(x, e, a) {
            for (var d = !1, f = 0, t = a.length - 1; f < a.length; t = f++) {
                var _ = a[f].lat, i = a[f].lng, s = a[t].lat, n = a[t].lng;
                e < i != e < n && x < (s - _) * (e - i) / (n - i) + _ && (d = !d)
            }
            return d
        }

        function jiFQ0000(x, e) {
            for (var a = x.getLatLngs(), d = !1, f = 0; f < a.length; f++) nFlf0010(e.lat, e.lng, a[f]) && (d = !0);
            return d
        }

        function fvqq0100(x) {
            gjY40100 = x
        }
    }, function (x, e, a) {
        'use strict';
        a.d(e, 'a', function () {
            return d
        });
        var e = a(0), i = a.n(e);

        function f(x, e) {
            return x.slice(-e.length) == e
        }

        var d = i.a.TileLayer.extend({
            initialize: function (x, e) {
                var a, d;
                null == e && (e = {}), f(x, '.png') || f(x, '.jpg') || (e.hasOwnProperty('isUpper') && (a = e.isUpper), d = e.imgType || '.jpg', x = a ? x + '/_alllayers/L{arc_Z}/R{arc_Y}/C{arc_X}' + d : x + '/_alllayers/L{arc_z}/R{arc_y}/C{arc_x}' + d), this._url = x, i.a.setOptions(this, e)
            }, onAdd: function (x) {
                i.a.TileLayer.prototype.onAdd.call(this, x)
            }, getTileUrl: function (x) {
                var e = x.z;
                this.options.zOffset && (e += Number(this.options.zOffset));
                var a = this.padLeft0(e.toString(), 2), d = this.padLeft0(x.x.toString(16), 8),
                    f = this.padLeft0(x.y.toString(16), 8), t = a.toUpperCase(), _ = d.toUpperCase(),
                    e = f.toUpperCase(), t = {
                        r: i.a.Browser.retina ? '@2x' : '',
                        s: this._getSubdomain(x),
                        arc_x: d,
                        arc_y: f,
                        arc_z: a,
                        arc_X: _,
                        arc_Y: e,
                        arc_Z: t
                    };
                return i.a.Util.template(this._url, i.a.extend(t, this.options))
            }, padLeft0: function (x, e) {
                for (var a = (x = String(x)).length; a < e;) x = '0' + x, a++;
                return x
            }
        });
        i.a.TileLayer.ArcGISTile = d, i.a.tileLayer.arcGISTile = function (x, e) {
            return new d(x, e)
        }
    }, function (x, e, a) {
        'use strict';
        a.r(e), a.d(e, 'tiandituArr', function () {
            return d
        }), a.d(e, 'tianditu', function () {
            return f
        }), a.d(e, 'gaodeArr', function () {
            return t
        }), a.d(e, 'gaode', function () {
            return _
        }), a.d(e, 'baiduArr', function () {
            return i
        }), a.d(e, 'baidu', function () {
            return s
        });
        var d = ['9ae78c51a0a28f06444d541148496e36', '2a0e637a8772d92b123ee8866dee4a82'], f = d[0],
            t = ['ae29a37307840c7ae4a785ac905927e0', '888a52a74c55ca47abe6c55ab3661d11', '0bc2903efcb3b67ebf1452d2f664a238', '0df8f6f984adc49fca5b7b1108664da2', '72f75689dff38a781055e68843474751'],
            _ = t[0],
            i = ['c3qarrKcqnB9HbCOPfKOHgneH6AGXCVU', '6g6evLsHT4M0DVZnRXRpXDDq1t95ESrg', '4j0HA8IeuvAPCl62ni8xCZkBhc2YGr67', 'F4CZ3cvHf8vbL8rkuTNtx8w2eflpdzj5', 'qObioeG8HeeQVrOVAGScPVhDzlmv6rL9'],
            s = i[0]
    }, function (x, e, a) {
        'use strict';
        a.d(e, 'a', function () {
            return f
        });
        var e = a(0), d = a.n(e), f = d.a.TileLayer.extend({
            options: {
                version: '1.0.0',
                style: 'default',
                tilematrixSet: '',
                format: 'image/png',
                tileSize: 256,
                layer: ''
            }, initialize: function (x, e) {
                this._url = x, d.a.setOptions(this, e)
            }, getParamString: function (x, e, a) {
                var d, f = [];
                for (d in x) f.push((a ? d.toUpperCase() : d) + '=' + x[d]);
                return (e && -1 !== e.indexOf('?') ? '&' : '?') + f.join('&')
            }, getTileUrl: function (x) {
                var e = this._getZoomForUrl();
                this.options.zOffset && (e += this.options.zOffset);
                var a = this.options.matrixIds ? this.options.matrixIds[e] : this.options.tilematrixBefore ? this.options.tilematrixBefore + e : e,
                    e = d.a.Util.template(this._url, {s: this._getSubdomain(x)}), x = {
                        service: 'WMTS',
                        request: 'GetTile',
                        version: this.options.version,
                        style: this.options.style,
                        tilematrixSet: this.options.tilematrixSet,
                        format: this.options.format,
                        width: this.options.tileSize,
                        height: this.options.tileSize,
                        layer: this.options.layer,
                        tilematrix: a,
                        tilerow: x.y,
                        tilecol: x.x
                    };
                return e + this.getParamString(x, e)
            }
        });
        d.a.TileLayer.WMTS = f, d.a.tileLayer.wmts = function (x, e) {
            return new f(x, e)
        }
    }, function (x, e, a) {
        'use strict';
        a.d(e, 'a', function () {
            return f
        });
        var e = a(0), d = a.n(e),
            a = (a(13), new d.a.Proj.CRS('EPSG:900913', '+proj=merc +a=6378206 +b=6356584.314245179 +lat_ts=0.0 +lon_0=0.0 +x_0=0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +wktext  +no_defs', {
                resolutions: function () {
                    var x = [];
                    x[0] = Math.pow(2, 18);
                    for (var e = 1; e < 19; e++) x[e] = Math.pow(2, 18 - e);
                    return x
                }(), origin: [0, 0], bounds: d.a.bounds([20037508.342789244, 0], [0, 20037508.342789244])
            })), f = function (x) {
                var e;
                switch ((x = x || {}).layer) {
                    case 'vec':
                    default:
                        e = d.a.tileLayer('https://maponline{s}.bdimg.com/tile/?qt=vtile&x={x}&y={y}&z={z}&styles=' + (x.bigfont ? 'ph' : 'pl') + '&scaler=2&udt=&from=jsapi2_0', {
                            name: x.name,
                            subdomains: '012',
                            tms: !0
                        });
                        break;
                    case 'img_d':
                        e = d.a.tileLayer('http://shangetu{s}.map.bdimg.com/it/u=x={x};y={y};z={z};v=009;type=sate&fm=46', {
                            name: x.name,
                            subdomains: '0123456789',
                            tms: !0
                        });
                        break;
                    case 'img_z':
                        e = d.a.tileLayer('http://online{s}.map.bdimg.com/tile/?qt=tile&x={x}&y={y}&z={z}&styles=' + (x.bigfont ? 'sh' : 'sl') + '&v=020', {
                            name: x.name,
                            subdomains: '0123456789',
                            tms: !0
                        });
                        break;
                    case 'custom':
                        x.customid = x.customid || 'midnight', e = d.a.tileLayer('http://api{s}.map.bdimg.com/customimage/tile?&x={x}&y={y}&z={z}&scale=1&customid=' + x.customid, {
                            name: x.name,
                            subdomains: '012',
                            tms: !0
                        });
                        break;
                    case 'time':
                        e = d.a.tileLayer('http://its.map.baidu.com:8002/traffic/TrafficTileService?x={x}&y={y}&level={z}&time=' + (new Date).getTime() + '&label=web2D&v=017', {
                            name: x.name,
                            subdomains: '0123456789',
                            tms: !0
                        });
                        break;
                    case 'img':
                        e = d.a.layerGroup([d.a.tileLayer.baidu({
                            name: '底图',
                            layer: 'img_d',
                            bigfont: x.bigfont
                        }), d.a.tileLayer.baidu({name: '注记', layer: 'img_z', bigfont: x.bigfont})])
                }
                return e
            };
        d.a.CRS.Baidu = a, d.a.tileLayer.baidu = f
    }, function (x, e, a) {
        'use strict';
        a.d(e, 'a', function () {
            return f
        }), a(24);
        var e = a(0), d = a.n(e), f = d.a.LayerGroup.extend({
            options: {interval: 20, showOriginLabel: !0, redraw: 'move', hidden: !1, zoomIntervals: []},
            lineStyle: {stroke: !0, color: '#111', opacity: .6, weight: 1, interactive: !1, clickable: !1},
            labelStyle: {opacity: 1},
            initialize: function (x) {
                d.a.LayerGroup.prototype.initialize.call(this), d.a.Util.setOptions(this, x)
            },
            onAdd: function (x) {
                this._map = x;
                var e = this.redraw();
                this._map.on('viewreset ' + this.options.redraw, e.redraw, e), this.eachLayer(x.addLayer, x)
            },
            onRemove: function (x) {
                x.off('viewreset ' + this.options.redraw, this.map), this.eachLayer(this.removeLayer, this)
            },
            hide: function () {
                this.options.hidden = !0, this.redraw()
            },
            show: function () {
                this.options.hidden = !1, this.redraw()
            },
            setOpacity: function (x) {
                this.lineStyle.opacity = x, this.labelStyle.opacity = x
            },
            redraw: function () {
                if (this._bounds = this._map.getBounds().pad(.5), this.clearLayers(), !this.options.hidden) {
                    for (var x = this._map.getZoom(), e = 0; e < this.options.zoomIntervals.length; e++) if (x >= this.options.zoomIntervals[e].start && x <= this.options.zoomIntervals[e].end) {
                        this.options.interval = this.options.zoomIntervals[e].interval;
                        break
                    }
                    this.constructLines(this.getMins(), this.getLineCounts()), this.options.showOriginLabel && this.addLayer(this.addOriginLabel())
                }
                return this
            },
            getLineCounts: function () {
                return {
                    x: Math.ceil((this._bounds.getEast() - this._bounds.getWest()) / this.options.interval),
                    y: Math.ceil((this._bounds.getNorth() - this._bounds.getSouth()) / this.options.interval)
                }
            },
            getMins: function () {
                var x = this.options.interval;
                return {x: Math.floor(this._bounds.getWest() / x) * x, y: Math.floor(this._bounds.getSouth() / x) * x}
            },
            constructLines: function (x, e) {
                for (var a = new Array(e.x + e.y), d = new Array(e.x + e.y), f = 0; f <= e.x; f++) {
                    var t = x.x + f * this.options.interval;
                    a[f] = this.buildXLine(t), d[f] = this.buildLabel('gridlabel-horiz', t)
                }
                for (var _ = 0; _ <= e.y; _++) {
                    var i = x.y + _ * this.options.interval;
                    a[_ + f] = this.buildYLine(i), d[_ + f] = this.buildLabel('gridlabel-vert', i)
                }
                a.forEach(this.addLayer, this), d.forEach(this.addLayer, this)
            },
            buildXLine: function (x) {
                var e = new d.a.LatLng(this._bounds.getSouth(), x), x = new d.a.LatLng(this._bounds.getNorth(), x);
                return new d.a.Polyline([e, x], this.lineStyle)
            },
            buildYLine: function (x) {
                var e = new d.a.LatLng(x, this._bounds.getWest()), x = new d.a.LatLng(x, this._bounds.getEast());
                return new d.a.Polyline([e, x], this.lineStyle)
            },
            buildLabel: function (x, e) {
                var a = this._map.getBounds().pad(-.003),
                    a = 'gridlabel-horiz' == x ? new d.a.LatLng(a.getNorth(), e) : new d.a.LatLng(e, a.getWest()),
                    e = e.toFixed(1).replace('.0', '');
                return d.a.marker(a, {
                    interactive: !1,
                    clickable: !1,
                    opacity: this.labelStyle.opacity,
                    icon: d.a.divIcon({
                        iconSize: [0, 0],
                        className: 'leaflet-grid-label',
                        html: '<div class="' + x + '\'>' + e + '</div>'
                    })
                })
            },
            addOriginLabel: function () {
                return d.a.marker([0, 0], {
                    interactive: !1,
                    clickable: !1,
                    opacity: this.labelStyle.opacity,
                    icon: d.a.divIcon({
                        iconSize: [0, 0],
                        className: 'leaflet-grid-label',
                        html: '<div class="gridlabel-horiz">(0,0)</div>'
                    })
                })
            }
        });
        d.a.SimpleGraticule = f, d.a.simpleGraticule = function (x) {
            return new f(x)
        }
    }, function (x, e, a) {
        'use strict';
        a.d(e, 'a', function () {
            return f
        });
        var e = a(0), d = a.n(e);

        function s(x) {
            return x / 864e5 + 2440587.5
        }

        var f = d.a.Polygon.extend({
            options: {color: '#00', opacity: .5, fillColor: '#00', fillOpacity: .5, resolution: 2},
            initialize: function (x) {
                this.version = '0.1.0', this._R2D = 180 / Math.PI, this._D2R = Math.PI / 180, d.a.Util.setOptions(this, x);
                x = this._compute(this.options.time || null);
                this.setLatLngs(x)
            },
            setTime: function (x) {
                this.options.time = x;
                x = this._compute(x || null);
                this.setLatLngs(x)
            },
            _sunEclipticPosition: function (x) {
                var e = x - 2451545, x = 280.46 + .9856474 * e, e = 357.528 + .9856003 * e;
                return e %= 360, {
                    lambda: (x %= 360) + 1.915 * Math.sin(e * this._D2R) + .02 * Math.sin(2 * e * this._D2R),
                    R: 1.00014 - .01671 * Math.cos(e * this._D2R) - .0014 * Math.cos(2 * e * this._D2R)
                }
            },
            _eclipticObliquity: function (x) {
                x = (x - 2451545) / 36525;
                return 23.43929111 - x * (46.836769 / 3600 - x * (1831e-7 / 3600 + x * (5.565e-7 - x * (1.6e-10 - 4.34e-8 * x / 3600))))
            },
            _sunEquatorialPosition: function (x, e) {
                var a = Math.atan(Math.cos(e * this._D2R) * Math.tan(x * this._D2R)) * this._R2D,
                    e = Math.asin(Math.sin(e * this._D2R) * Math.sin(x * this._D2R)) * this._R2D;
                return {alpha: a += 90 * Math.floor(x / 90) - 90 * Math.floor(a / 90), delta: e}
            },
            _hourAngle: function (x, e, a) {
                return 15 * (a + x / 15) - e.alpha
            },
            _latitude: function (x, e) {
                return Math.atan(-Math.cos(x * this._D2R) / Math.tan(e.delta * this._D2R)) * this._R2D
            },
            _compute: function (x) {
                for (var e, a, x = s(t = null == x ? new Date : new Date(x)), d = (18.697374558 + 24.06570982441908 * (s(t) - 2451545)) % 24, f = [], t = this._sunEclipticPosition(x), x = this._eclipticObliquity(x), _ = this._sunEquatorialPosition(t.lambda, x), i = 0; i <= 720 * this.options.resolution; i++) e = i / this.options.resolution - 360, a = this._hourAngle(e, _, d), a = this._latitude(a, _), f[i + 1] = [a, e];
                return _.delta < 0 ? (f[0] = [90, -360], f[f.length] = [90, 360]) : (f[0] = [-90, -360], f[f.length] = [-90, 360]), f
            }
        });
        d.a.Terminator = f, d.a.terminator = function (x) {
            return new f(x)
        }
    }, function (fZhq0001, ggqu0010, lWvn0011) {
        'use strict';
        lWvn0011.d(ggqu0010, 'b', function () {
            return pHtq0001
        }), lWvn0011.d(ggqu0010, 'a', function () {
            return icTE0300
        });
        var pHtq0001 = '2.3.1', icTE0300 = '2021-3-31 21:02:44';
    }, function (x, e, a) {
        'use strict';
        var d = a(0), i = a.n(d), V = 484813681109536e-20, b = Math.PI / 2, m = .017453292519943295,
            o = 57.29577951308232, p = Math.PI / 4, y = 2 * Math.PI, h = 3.14159265359, s = {
                greenwich: 0,
                lisbon: -9.131906111111,
                paris: 2.337229166667,
                bogota: -74.080916666667,
                madrid: -3.687938888889,
                rome: 12.452333333333,
                bern: 7.439583333333,
                jakarta: 106.807719444444,
                ferro: -17.666666666667,
                brussels: 4.367975,
                stockholm: 18.058277777778,
                athens: 23.7163375,
                oslo: 10.722916666667
            }, n = {ft: {to_meter: .3048}, 'us-ft': {to_meter: 1200 / 3937}}, _ = /[\s_\-\/\(\)]/g;

        function r(x, e) {
            if (x[e]) return x[e];
            for (var a, d = Object.keys(x), f = e.toLowerCase().replace(_, ''), t = -1; ++t < d.length;) if ((a = d[t]).toLowerCase().replace(_, '') === f) return x[a]
        }

        var f = function (x) {
            var e, a, d, f = {}, t = x.split('+').map(function (x) {
                return x.trim()
            }).filter(function (x) {
                return x
            }).reduce(function (x, e) {
                e = e.split('=');
                return e.push(!0), x[e[0].toLowerCase()] = e[1], x
            }, {}), _ = {
                proj: 'projName', datum: 'datumCode', rf: function (x) {
                    f.rf = parseFloat(x)
                }, lat_0: function (x) {
                    f.lat0 = x * m
                }, lat_1: function (x) {
                    f.lat1 = x * m
                }, lat_2: function (x) {
                    f.lat2 = x * m
                }, lat_ts: function (x) {
                    f.lat_ts = x * m
                }, lon_0: function (x) {
                    f.long0 = x * m
                }, lon_1: function (x) {
                    f.long1 = x * m
                }, lon_2: function (x) {
                    f.long2 = x * m
                }, alpha: function (x) {
                    f.alpha = parseFloat(x) * m
                }, gamma: function (x) {
                    f.rectified_grid_angle = parseFloat(x)
                }, lonc: function (x) {
                    f.longc = x * m
                }, x_0: function (x) {
                    f.x0 = parseFloat(x)
                }, y_0: function (x) {
                    f.y0 = parseFloat(x)
                }, k_0: function (x) {
                    f.k0 = parseFloat(x)
                }, k: function (x) {
                    f.k0 = parseFloat(x)
                }, a: function (x) {
                    f.a = parseFloat(x)
                }, b: function (x) {
                    f.b = parseFloat(x)
                }, r_a: function () {
                    f.R_A = !0
                }, zone: function (x) {
                    f.zone = parseInt(x, 10)
                }, south: function () {
                    f.utmSouth = !0
                }, towgs84: function (x) {
                    f.datum_params = x.split(',').map(function (x) {
                        return parseFloat(x)
                    })
                }, to_meter: function (x) {
                    f.to_meter = parseFloat(x)
                }, units: function (x) {
                    f.units = x;
                    x = r(n, x);
                    x && (f.to_meter = x.to_meter)
                }, from_greenwich: function (x) {
                    f.from_greenwich = x * m
                }, pm: function (x) {
                    var e = r(s, x);
                    f.from_greenwich = (e || parseFloat(x)) * m
                }, nadgrids: function (x) {
                    '@null' === x ? f.datumCode = 'none' : f.nadgrids = x
                }, axis: function (x) {
                    3 === x.length && -1 !== 'ewnsud'.indexOf(x.substr(0, 1)) && -1 !== 'ewnsud'.indexOf(x.substr(1, 1)) && -1 !== 'ewnsud'.indexOf(x.substr(2, 1)) && (f.axis = x)
                }, approx: function () {
                    f.approx = !0
                }
            };
            for (e in t) a = t[e], e in _ ? 'function' == typeof (d = _[e]) ? d(a) : f[d] = a : f[e] = a;
            return 'string' == _typeof(f.datumCode) && 'WGS84' !== f.datumCode && (f.datumCode = f.datumCode.toLowerCase()), f
        }, t = /\s/, c = /[A-Za-z]/, l = /[A-Za-z84]/, u = /[,\]]/, v = /[\d\.E\-\+]/;

        function Z(x) {
            if ('string' != typeof x) throw new Error('not a string');
            this.text = x.trim(), this.level = 0, this.place = 0, this.root = null, this.stack = [], this.currentObject = null, this.state = 1
        }

        function M(x, e, a) {
            Array.isArray(e) && (a.unshift(e), e = null);
            var d = e ? {} : x, d = a.reduce(function (x, e) {
                return G(e, x), x
            }, d);
            e && (x[e] = d)
        }

        function G(x, e) {
            if (Array.isArray(x)) {
                var a, d = x.shift();
                if ('PARAMETER' === d && (d = x.shift()), 1 === x.length) return Array.isArray(x[0]) ? (e[d] = {}, void G(x[0], e[d])) : void (e[d] = x[0]);
                if (x.length) if ('TOWGS84' !== d) {
                    if ('AXIS' === d) return d in e || (e[d] = []), void e[d].push(x);
                    switch (Array.isArray(d) || (e[d] = {}), d) {
                        case 'UNIT':
                        case 'PRIMEM':
                        case 'VERT_DATUM':
                            return e[d] = {
                                name: x[0].toLowerCase(),
                                convert: x[1]
                            }, void (3 === x.length && G(x[2], e[d]));
                        case'SPHEROID':
                        case 'ELLIPSOID':
                            return e[d] = {name: x[0], a: x[1], rf: x[2]}, void (4 === x.length && G(x[3], e[d]));
                        case 'PROJECTEDCRS':
                        case'PROJCRS':
                        case'GEOGCS':
                        case 'GEOCCS':
                        case 'PROJCS':
                        case 'LOCAL_CS':
                        case'GEODCRS':
                        case'GEODETICCRS':
                        case'GEODETICDATUM':
                        case 'EDATUM':
                        case 'ENGINEERINGDATUM':
                        case 'VERT_CS':
                        case 'VERTCRS':
                        case 'VERTICALCRS':
                        case 'COMPD_CS':
                        case 'COMPOUNDCRS':
                        case'ENGINEERINGCRS':
                        case 'ENGCRS':
                        case 'FITTED_CS':
                        case 'LOCAL_DATUM':
                        case 'DATUM':
                            return x[0] = ['name', x[0]], void M(e, d, x);
                        default:
                            for (a = -1; ++a < x.length;) if (!Array.isArray(x[a])) return G(x, e[d]);
                            return M(e, d, x)
                    }
                } else e[d] = x; else e[d] = !0
            } else e[x] = !0
        }

        function W(x) {
            return .017453292519943295 * x
        }

        Z.prototype.readCharicter = function () {
            var x = this.text[this.place++];
            if (4 !== this.state) for (; t.test(x);) {
                if (this.place >= this.text.length) return;
                x = this.text[this.place++]
            }
            switch (this.state) {
                case 1:
                    return this.neutral(x);
                case 2:
                    return this.keyword(x);
                case 4:
                    return this.quoted(x);
                case 5:
                    return this.afterquote(x);
                case 3:
                    return this.number(x);
                case-1:
                    return
            }
        }, Z.prototype.afterquote = function (x) {
            if ('\'' === x) return this.word += '\'', void (this.state = 4);
            if (u.test(x)) return this.word = this.word.trim(), void this.afterItem(x);
            throw new Error('havn\'t handled \'' + x + '\' in afterquote yet, index ' + this.place)
        }, Z.prototype.afterItem = function (x) {
            return ',' === x ? (null !== this.word && this.currentObject.push(this.word), this.word = null, void (this.state = 1)) : ']' === x ? (this.level--, null !== this.word && (this.currentObject.push(this.word), this.word = null), this.state = 1, this.currentObject = this.stack.pop(), void (this.currentObject || (this.state = -1))) : void 0
        }, Z.prototype.number = function (x) {
            if (!v.test(x)) {
                if (u.test(x)) return this.word = parseFloat(this.word), void this.afterItem(x);
                throw new Error('havn\'t handled \'' + x + '" in number yet, index ' + this.place)
            }
            this.word += x
        }, Z.prototype.quoted = function (x) {
            '\'' !== x ? this.word += x : this.state = 5
        }, Z.prototype.keyword = function (x) {
            if (l.test(x)) this.word += x; else {
                if ('[' === x) {
                    var e = [];
                    return e.push(this.word), this.level++, null === this.root ? this.root = e : this.currentObject.push(e), this.stack.push(this.currentObject), this.currentObject = e, void (this.state = 1)
                }
                if (!u.test(x)) throw new Error('havn\'t handled \'' + x + '" in keyword yet, index ' + this.place);
                this.afterItem(x)
            }
        }, Z.prototype.neutral = function (x) {
            if (c.test(x)) return this.word = x, void (this.state = 2);
            if ('\'' === x) return this.word = '', void (this.state = 4);
            if (v.test(x)) return this.word = x, void (this.state = 3);
            if (!u.test(x)) throw new Error('havnt handled "' + x + '\' in neutral yet, index ' + this.place);
            this.afterItem(x)
        }, Z.prototype.output = function () {
            for (; this.place < this.text.length;) this.readCharicter();
            if (-1 === this.state) return this.root;
            throw new Error('unable to parse string "' + this.text + '". State is ' + this.state)
        };
        var X = function (x) {
            var e = new Z(x).output(), a = e.shift(), x = e.shift();
            e.unshift(['name', x]), e.unshift(['type', a]);
            a = {};
            return G(e, a), function (f) {
                if ('GEOGCS' === f.type ? f.projName = 'longlat' : 'LOCAL_CS' === f.type ? (f.projName = 'identity', f.local = !0) : 'object' == _typeof(f.PROJECTION) ? f.projName = Object.keys(f.PROJECTION)[0] : f.projName = f.PROJECTION, f.AXIS) {
                    for (var x = '', e = 0, a = f.AXIS.length; e < a; ++e) {
                        var d = f.AXIS[e][0].toLowerCase();
                        -1 !== d.indexOf('north') ? x += 'n' : -1 !== d.indexOf('south') ? x += 's' : -1 !== d.indexOf('east') ? x += 'e' : -1 !== d.indexOf('west') && (x += 'w')
                    }
                    2 === x.length && (x += 'u'), 3 === x.length && (f.axis = x)
                }
                f.UNIT && (f.units = f.UNIT.name.toLowerCase(), 'metre' === f.units && (f.units = 'meter'), f.UNIT.convert && ('GEOGCS' === f.type ? f.DATUM && f.DATUM.SPHEROID && (f.to_meter = f.UNIT.convert * f.DATUM.SPHEROID.a) : f.to_meter = f.UNIT.convert));
                var t = f.GEOGCS;

                function _(x) {
                    return x * (f.to_meter || 1)
                }

                (t = 'GEOGCS' === f.type ? f : t) && (t.DATUM ? f.datumCode = t.DATUM.name.toLowerCase() : f.datumCode = t.name.toLowerCase(), 'd_' === f.datumCode.slice(0, 2) && (f.datumCode = f.datumCode.slice(2)), 'new_zealand_geodetic_datum_1949' !== f.datumCode && 'new_zealand_1949' !== f.datumCode || (f.datumCode = 'nzgd49'), 'wgs_1984' !== f.datumCode && 'world_geodetic_system_1984' !== f.datumCode || ('Mercator_Auxiliary_Sphere' === f.PROJECTION && (f.sphere = !0), f.datumCode = 'wgs84'), '_ferro' === f.datumCode.slice(-6) && (f.datumCode = f.datumCode.slice(0, -6)), '_jakarta' === f.datumCode.slice(-8) && (f.datumCode = f.datumCode.slice(0, -8)), ~f.datumCode.indexOf('belge') && (f.datumCode = 'rnb72'), t.DATUM && t.DATUM.SPHEROID && (f.ellps = t.DATUM.SPHEROID.name.replace('_19', '').replace(/[Cc]larke\_18/, 'clrk'), 'international' === f.ellps.toLowerCase().slice(0, 13) && (f.ellps = 'intl'), f.a = t.DATUM.SPHEROID.a, f.rf = parseFloat(t.DATUM.SPHEROID.rf, 10)), t.DATUM && t.DATUM.TOWGS84 && (f.datum_params = t.DATUM.TOWGS84), ~f.datumCode.indexOf('osgb_1936') && (f.datumCode = 'osgb36'), ~f.datumCode.indexOf('osni_1952') && (f.datumCode = 'osni52'), (~f.datumCode.indexOf('tm65') || ~f.datumCode.indexOf('geodetic_datum_of_1965')) && (f.datumCode = 'ire65'), 'ch1903+' === f.datumCode && (f.datumCode = 'ch1903'), ~f.datumCode.indexOf('israel') && (f.datumCode = 'isr93')), f.b && !isFinite(f.b) && (f.b = f.a), [['standard_parallel_1', 'Standard_Parallel_1'], ['standard_parallel_2', 'Standard_Parallel_2'], ['false_easting', 'False_Easting'], ['false_northing', 'False_Northing'], ['central_meridian', 'Central_Meridian'], ['latitude_of_origin', 'Latitude_Of_Origin'], ['latitude_of_origin', 'Central_Parallel'], ['scale_factor', 'Scale_Factor'], ['k0', 'scale_factor'], ['latitude_of_center', 'Latitude_Of_Center'], ['latitude_of_center', 'Latitude_of_center'], ['lat0', 'latitude_of_center', W], ['longitude_of_center', 'Longitude_Of_Center'], ['longitude_of_center', 'Longitude_of_center'], ['longc', 'longitude_of_center', W], ['x0', 'false_easting', _], ['y0', 'false_northing', _], ['long0', 'central_meridian', W], ['lat0', 'latitude_of_origin', W], ['lat0', 'standard_parallel_1', W], ['lat1', 'standard_parallel_1', W], ['lat2', 'standard_parallel_2', W], ['azimuth', 'Azimuth'], ['alpha', 'azimuth', W], ['srsCode', 'name']].forEach(function (x) {
                    var e, a = f, d = (e = x)[0], x = e[1];
                    !(d in a) && x in a && (a[d] = a[x], 3 === e.length && (a[d] = e[2](a[d])))
                }), f.long0 || !f.longc || 'Albers_Conic_Equal_Area' !== f.projName && 'Lambert_Azimuthal_Equal_Area' !== f.projName || (f.long0 = f.longc), f.lat_ts || !f.lat1 || 'Stereographic_South_Pole' !== f.projName && 'Polar Stereographic (variant B)' !== f.projName || (f.lat0 = W(0 < f.lat1 ? 90 : -90), f.lat_ts = f.lat1)
            }(a), a
        };

        function g(x) {
            var e = this;
            if (2 === arguments.length) {
                var a = arguments[1];
                'string' == _typeof(a) ? '+' === a.charAt(0) ? g[x] = f(arguments[1]) : g[x] = X(arguments[1]) : g[x] = a
            } else if (1 === arguments.length) {
                if (Array.isArray(x)) return x.map(function (x) {
                    Array.isArray(x) ? g.apply(e, x) : g(x)
                });
                if ('string' == _typeof(x)) {
                    if (x in g) return g[x]
                } else 'EPSG' in x ? g['EPSG:' + x.EPSG] = x : 'ESRI' in x ? g['ESRI:' + x.ESRI] = x : 'IAU2000' in x ? g['IAU2000:' + x.IAU2000] = x : console.log(x)
            }
        }

        (Me = g)('EPSG:4326', '+title=WGS 84 (long/lat) +proj=longlat +ellps=WGS84 +datum=WGS84 +units=degrees'), Me('EPSG:4269', '+title=NAD83 (long/lat) +proj=longlat +a=6378137.0 +b=6356752.31414036 +ellps=GRS80 +datum=NAD83 +units=degrees'), Me('EPSG:3857', '+title=WGS 84 / Pseudo-Mercator +proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +no_defs'), Me.WGS84 = Me['EPSG:4326'],
            Me['EPSG:3785'] = Me['EPSG:3857'],
            Me.GOOGLE = Me['EPSG:3857'],
            Me['EPSG:900913'] = Me['EPSG:3857'],
            Me['EPSG:102113'] = Me['EPSG:3857'];

        function R(x, e, a) {
            return e *= x, a / Math.sqrt(1 - e * e)
        }

        function w(x) {
            return x < 0 ? -1 : 1
        }

        function Y(x, e, a) {
            return a *= x, x *= .5, a = Math.pow((1 - a) / (1 + a), x), Math.tan(.5 * (b - e)) / a
        }

        function N(x, e) {
            for (var a, d = .5 * x, f = b - 2 * Math.atan(e), t = 0; t <= 15; t++) if (a = x * Math.sin(f), f += a = b - 2 * Math.atan(e * Math.pow((1 - a) / (1 + a), d)) - f, Math.abs(a) <= 1e-10) return f;
            return -9999
        }

        var k = g,
            F = ['PROJECTEDCRS', 'PROJCRS', 'GEOGCS', 'GEOCCS', 'PROJCS', 'LOCAL_CS', 'GEODCRS', 'GEODETICCRS', 'GEODETICDATUM', 'ENGCRS', 'ENGINEERINGCRS'],
            z = ['3857', '900913', '3785', '102113'], J = function (x) {
                if (d = x, 'string' != _typeof(d)) return x;
                var e;
                if (x in k) return k[x];
                if (e = x, F.some(function (x) {
                    return -1 < e.indexOf(x)
                })) {
                    var a = X(x);
                    if (function () {
                        var x = r(a, 'authority');
                        if (x) {
                            x = r(x, 'epsg');
                            return x && -1 < z.indexOf(x)
                        }
                    }()) return k['EPSG:3857'];
                    var d = function () {
                        var x = r(a, 'extension');
                        if (x) return r(x, 'proj4')
                    }();
                    return d ? f(d) : a
                }
                return '+' === x[0] ? f(x) : void 0
            }, T = function (x, e) {
                var a, d;
                if (x = x || {}, !e) return x;
                for (d in e) void 0 !== (a = e[d]) && (x[d] = a);
                return x
            }, U = function (x) {
                return Math.abs(x) <= h ? x : x - w(x) * y
            };

        function Q(x) {
            return x
        }

        var L = [{
            init: function () {
                var x = this.b / this.a;
                this.es = 1 - x * x, 'x0' in this || (this.x0 = 0), 'y0' in this || (this.y0 = 0), this.e = Math.sqrt(this.es), this.lat_ts ? this.sphere ? this.k0 = Math.cos(this.lat_ts) : this.k0 = R(this.e, Math.sin(this.lat_ts), Math.cos(this.lat_ts)) : this.k0 || (this.k ? this.k0 = this.k : this.k0 = 1)
            },
            forward: function (x) {
                var e, a, d = x.x, f = x.y;
                return 90 < f * o && f * o < -90 && 180 < d * o && d * o < -180 || Math.abs(Math.abs(f) - b) <= 1e-10 ? null : (a = this.sphere ? (e = this.x0 + this.a * this.k0 * U(d - this.long0), this.y0 + this.a * this.k0 * Math.log(Math.tan(p + .5 * f))) : (a = Math.sin(f), a = Y(this.e, f, a), e = this.x0 + this.a * this.k0 * U(d - this.long0), this.y0 - this.a * this.k0 * Math.log(a)), x.x = e, x.y = a, x)
            },
            inverse: function (x) {
                var e, a = x.x - this.x0, d = x.y - this.y0;
                if (this.sphere) e = b - 2 * Math.atan(Math.exp(-d / (this.a * this.k0))); else {
                    d = Math.exp(-d / (this.a * this.k0));
                    if (-9999 === (e = N(this.e, d))) return null
                }
                return a = U(this.long0 + a / (this.a * this.k0)), x.x = a, x.y = e, x
            },
            names: ['Mercator', 'Popular Visualisation Pseudo Mercator', 'Mercator_1SP', 'Mercator_Auxiliary_Sphere', 'merc']
        }, {
            init: function () {
            }, forward: Q, inverse: Q, names: ['longlat', 'identity']
        }], I = {}, A = [];

        function E(x, e) {
            var a = A.length;
            return x.names ? ((A[a] = x).names.forEach(function (x) {
                I[x.toLowerCase()] = a
            }), this) : (console.log(e), !0)
        }

        var S = {
            start: function () {
                L.forEach(E)
            }, add: E, get: function (x) {
                if (!x) return !1;
                x = x.toLowerCase();
                return void 0 !== I[x] && A[I[x]] ? A[I[x]] : void 0
            }
        }, B = {
            MERIT: {a: 6378137, rf: 298.257, ellipseName: 'MERIT 1983'},
            SGS85: {a: 6378136, rf: 298.257, ellipseName: 'Soviet Geodetic System 85'},
            GRS80: {a: 6378137, rf: 298.257222101, ellipseName: 'GRS 1980(IUGG, 1980)'},
            IAU76: {a: 6378140, rf: 298.257, ellipseName: 'IAU 1976'},
            airy: {a: 6377563.396, b: 6356256.91, ellipseName: 'Airy 1830'},
            APL4: {a: 6378137, rf: 298.25, ellipseName: 'Appl. Physics. 1965'},
            NWL9D: {a: 6378145, rf: 298.25, ellipseName: 'Naval Weapons Lab., 1965'},
            mod_airy: {a: 6377340.189, b: 6356034.446, ellipseName: 'Modified Airy'},
            andrae: {a: 6377104.43, rf: 300, ellipseName: 'Andrae 1876 (Den., Iclnd.)'},
            aust_SA: {a: 6378160, rf: 298.25, ellipseName: 'Australian Natl & S. Amer. 1969'},
            GRS67: {a: 6378160, rf: 298.247167427, ellipseName: 'GRS 67(IUGG 1967)'},
            bessel: {a: 6377397.155, rf: 299.1528128, ellipseName: 'Bessel 1841'},
            bess_nam: {a: 6377483.865, rf: 299.1528128, ellipseName: 'Bessel 1841 (Namibia)'},
            clrk66: {a: 6378206.4, b: 6356583.8, ellipseName: 'Clarke 1866'},
            clrk80: {a: 6378249.145, rf: 293.4663, ellipseName: 'Clarke 1880 mod.'},
            clrk58: {a: 6378293.645208759, rf: 294.2606763692654, ellipseName: 'Clarke 1858'},
            CPM: {a: 6375738.7, rf: 334.29, ellipseName: 'Comm. des Poids et Mesures 1799'},
            delmbr: {a: 6376428, rf: 311.5, ellipseName: 'Delambre 1810 (Belgium)'},
            engelis: {a: 6378136.05, rf: 298.2566, ellipseName: 'Engelis 1985'},
            evrst30: {a: 6377276.345, rf: 300.8017, ellipseName: 'Everest 1830'},
            evrst48: {a: 6377304.063, rf: 300.8017, ellipseName: 'Everest 1948'},
            evrst56: {a: 6377301.243, rf: 300.8017, ellipseName: 'Everest 1956'},
            evrst69: {a: 6377295.664, rf: 300.8017, ellipseName: 'Everest 1969'},
            evrstSS: {a: 6377298.556, rf: 300.8017, ellipseName: 'Everest (Sabah & Sarawak)'},
            fschr60: {a: 6378166, rf: 298.3, ellipseName: 'Fischer (Mercury Datum) 1960'},
            fschr60m: {a: 6378155, rf: 298.3, ellipseName: 'Fischer 1960'},
            fschr68: {a: 6378150, rf: 298.3, ellipseName: 'Fischer 1968'},
            helmert: {a: 6378200, rf: 298.3, ellipseName: 'Helmert 1906'},
            hough: {a: 6378270, rf: 297, ellipseName: 'Hough'},
            intl: {a: 6378388, rf: 297, ellipseName: 'International 1909 (Hayford)'},
            kaula: {a: 6378163, rf: 298.24, ellipseName: 'Kaula 1961'},
            lerch: {a: 6378139, rf: 298.257, ellipseName: 'Lerch 1979'},
            mprts: {a: 6397300, rf: 191, ellipseName: 'Maupertius 1738'},
            new_intl: {a: 6378157.5, b: 6356772.2, ellipseName: 'New International 1967'},
            plessis: {a: 6376523, rf: 6355863, ellipseName: 'Plessis 1817 (France)'},
            krass: {a: 6378245, rf: 298.3, ellipseName: 'Krassovsky, 1942'},
            SEasia: {a: 6378155, b: 6356773.3205, ellipseName: 'Southeast Asia'},
            walbeck: {a: 6376896, b: 6355834.8467, ellipseName: 'Walbeck'},
            WGS60: {a: 6378165, rf: 298.3, ellipseName: 'WGS 60'},
            WGS66: {a: 6378145, rf: 298.25, ellipseName: 'WGS 66'},
            WGS7: {a: 6378135, rf: 298.26, ellipseName: 'WGS 72'}
        }, H = B.WGS84 = {a: 6378137, rf: 298.257223563, ellipseName: 'WGS 84'};
        B.sphere = {a: 6370997, b: 6370997, ellipseName: 'Normal Sphere (r=6370997)'};
        var C = {};
        C.wgs84 = {
            towgs84: '0,0,0',
            ellipse: 'WGS84',
            datumName: 'WGS84'
        }, C.ch1903 = {
            towgs84: '674.374,15.056,405.346',
            ellipse: 'bessel',
            datumName: 'swiss'
        }, C.ggrs87 = {
            towgs84: '-199.87,74.79,246.62',
            ellipse: 'GRS80',
            datumName: 'Greek_Geodetic_Reference_System_1987'
        }, C.nad83 = {
            towgs84: '0,0,0',
            ellipse: 'GRS80',
            datumName: 'North_American_Datum_1983'
        }, C.nad27 = {
            nadgrids: '@conus,@alaska,@ntv2_0.gsb,@ntv1_can.dat',
            ellipse: 'clrk66',
            datumName: 'North_American_Datum_1927'
        }, C.potsdam = {
            towgs84: '606.0,23.0,413.0',
            ellipse: 'bessel',
            datumName: 'Potsdam Rauenberg 1950 DHDN'
        }, C.carthage = {
            towgs84: '-263.0,6.0,431.0',
            ellipse: 'clark80',
            datumName: 'Carthage 1934 Tunisia'
        }, C.hermannskogel = {
            towgs84: '653.0,-212.0,449.0',
            ellipse: 'bessel',
            datumName: 'Hermannskogel'
        }, C.osni52 = {
            towgs84: '482.530,-130.596,564.557,-1.042,-0.214,-0.631,8.15',
            ellipse: 'airy',
            datumName: 'Irish National'
        }, C.ire65 = {
            towgs84: '482.530,-130.596,564.557,-1.042,-0.214,-0.631,8.15',
            ellipse: 'mod_airy',
            datumName: 'Ireland 1965'
        }, C.rassadiran = {
            towgs84: '-133.63,-157.5,-158.62',
            ellipse: 'intl',
            datumName: 'Rassadiran'
        }, C.nzgd49 = {
            towgs84: '59.47,-5.04,187.44,0.47,-0.1,1.024,-4.5993',
            ellipse: 'intl',
            datumName: 'New Zealand Geodetic Datum 1949'
        }, C.osgb36 = {
            towgs84: '446.448,-125.157,542.060,0.1502,0.2470,0.8421,-20.4894',
            ellipse: 'airy',
            datumName: 'Airy 1830'
        }, C.s_jtsk = {
            towgs84: '589,76,480',
            ellipse: 'bessel',
            datumName: 'S-JTSK (Ferro)'
        }, C.beduaram = {
            towgs84: '-106,-87,188',
            ellipse: 'clrk80',
            datumName: 'Beduaram'
        }, C.gunung_segara = {
            towgs84: '-403,684,41',
            ellipse: 'bessel',
            datumName: 'Gunung Segara Jakarta'
        }, C.rnb72 = {
            towgs84: '106.869,-52.2978,103.724,-0.33657,0.456955,-1.84218,1',
            ellipse: 'intl',
            datumName: 'Reseau National Belge 1972'
        };
        var j = function (x, e, a, d, f, t, _) {
            var i = {};
            return i.datum_type = void 0 === x || 'none' === x ? 5 : 4, e && (i.datum_params = e.map(parseFloat), 0 === i.datum_params[0] && 0 === i.datum_params[1] && 0 === i.datum_params[2] || (i.datum_type = 1), 3 < i.datum_params.length && (0 === i.datum_params[3] && 0 === i.datum_params[4] && 0 === i.datum_params[5] && 0 === i.datum_params[6] || (i.datum_type = 2, i.datum_params[3] *= V, i.datum_params[4] *= V, i.datum_params[5] *= V, i.datum_params[6] = i.datum_params[6] / 1e6 + 1))), _ && (i.datum_type = 3, i.grids = _), i.a = a, i.b = d, i.es = f, i.ep2 = t, i
        }, O = {};

        function D(x) {
            if (0 === x.length) return null;
            var e = '@' === x[0];
            return e && (x = x.slice(1)), 'null' === x ? {
                name: 'null',
                mandatory: !e,
                grid: null,
                isNull: !0
            } : {name: x, mandatory: !e, grid: O[x] || null, isNull: !1}
        }

        function P(x) {
            return x / 3600 * Math.PI / 180
        }

        function q(x, e, a) {
            return String.fromCharCode.apply(null, new Uint8Array(x.buffer.slice(e, a)))
        }

        function K(x, e) {
            if (!(this instanceof K)) return new K(x);
            e = e || function (x) {
                if (x) throw x
            };
            var a, d, f, t, _, i, s, n = J(x);
            'object' == _typeof(n) && (a = K.projections.get(n.projName)) ? (!n.datumCode || 'none' === n.datumCode || (d = r(C, n.datumCode)) && (n.datum_params = n.datum_params || (d.towgs84 ? d.towgs84.split(',') : null), n.ellps = d.ellipse, n.datumName = d.datumName ? d.datumName : n.datumCode), n.k0 = n.k0 || 1, n.axis = n.axis || 'enu', n.ellps = n.ellps || 'wgs84', n.lat1 = n.lat1 || n.lat0, s = ((f = (i = (d = (f = n.a, t = n.b, _ = n.rf, i = n.ellps, s = n.sphere, f || (f = (i = (i = r(B, i)) || H).a, t = i.b, _ = i.rf), _ && !t && (t = (1 - 1 / _) * f), (0 === _ || Math.abs(f - t) < 1e-10) && (s = !0, t = f), {
                a: f,
                b: t,
                rf: _,
                sphere: s
            })).a) * i) - (_ = (t = d.b) * t)) / f, t = 0, n.R_A ? (f = (i *= 1 - s * (.16666666666666666 + s * (.04722222222222222 + .022156084656084655 * s))) * i, s = 0) : t = Math.sqrt(s), f = {
                es: s,
                e: t,
                ep2: (f - _) / _
            }, _ = void 0 === (_ = n.nadgrids) ? null : _.split(',').map(D), _ = n.datum || j(n.datumCode, n.datum_params, d.a, d.b, f.es, f.ep2, _), T(this, n), T(this, a), this.a = d.a, this.b = d.b, this.rf = d.rf, this.sphere = d.sphere, this.es = f.es, this.e = f.e, this.ep2 = f.ep2, this.datum = _, this.init(), e(null, this)) : e(x)
        }

        K.projections = S, K.projections.start();
        var $ = K;

        function xx(x, e, a) {
            var d, f = x.x, t = x.y, _ = x.z || 0;
            if (t < -b && -1.001 * b < t) t = -b; else if (b < t && t < 1.001 * b) t = b; else {
                if (t < -b) return {x: -1 / 0, y: -1 / 0, z: x.z};
                if (b < t) return {x: 1 / 0, y: 1 / 0, z: x.z}
            }
            return f > Math.PI && (f -= 2 * Math.PI), d = Math.sin(t), x = Math.cos(t), t = d * d, {
                x: ((t = a / Math.sqrt(1 - e * t)) + _) * x * Math.cos(f),
                y: (t + _) * x * Math.sin(f),
                z: (t * (1 - e) + _) * d
            }
        }

        function ex(x, e, a, d) {
            var f, t, _, i, s, n, h, r, c, o, l, u = x.x, b = x.y, m = x.z || 0, p = Math.sqrt(u * u + b * b),
                y = Math.sqrt(u * u + b * b + m * m);
            if (p / a < 1e-12) {
                if (o = 0, y / a < 1e-12) return l = -d, {x: x.x, y: x.y, z: x.z}
            } else o = Math.atan2(b, u);
            for (f = m / y, i = (t = p / y) * (1 - e) * (_ = 1 / Math.sqrt(1 - e * (2 - e) * t * t)), s = f * _, c = 0; c++, r = e * (r = a / Math.sqrt(1 - e * s * s)) / (r + (l = p * i + m * s - r * (1 - e * s * s))), r = (h = f * (_ = 1 / Math.sqrt(1 - r * (2 - r) * t * t))) * i - (n = t * (1 - r) * _) * s, i = n, s = h, 1e-24 < r * r && c < 30;) ;
            return {x: o, y: Math.atan(h / Math.abs(n)), z: l}
        }

        function ax(x) {
            return 1 === x || 2 === x
        }

        var dx = function (x, e, a) {
            if (i = e, (_ = x).datum_type === i.datum_type && !(_.a !== i.a || 5e-11 < Math.abs(_.es - i.es)) && (1 === _.datum_type ? _.datum_params[0] === i.datum_params[0] && _.datum_params[1] === i.datum_params[1] && _.datum_params[2] === i.datum_params[2] : 2 !== _.datum_type || _.datum_params[0] === i.datum_params[0] && _.datum_params[1] === i.datum_params[1] && _.datum_params[2] === i.datum_params[2] && _.datum_params[3] === i.datum_params[3] && _.datum_params[4] === i.datum_params[4] && _.datum_params[5] === i.datum_params[5] && _.datum_params[6] === i.datum_params[6])) return a;
            if (5 === x.datum_type || 5 === e.datum_type) return a;
            var d = x.a, f = x.es;
            if (3 === x.datum_type) {
                if (0 !== fx(x, !1, a)) return;
                d = 6378137, f = .0066943799901413165
            }
            var t = e.a, _ = e.b, i = e.es;
            return 3 === e.datum_type && (t = 6378137, _ = 6356752.314, i = .0066943799901413165), f !== i || d !== t || ax(x.datum_type) || ax(e.datum_type) ? (a = xx(a, f, d), ax(x.datum_type) && (a = function (x, e, a) {
                if (1 === e) return {x: x.x + a[0], y: x.y + a[1], z: x.z + a[2]};
                if (2 === e) {
                    var d = a[0], f = a[1], t = a[2], _ = a[3], i = a[4], e = a[5], a = a[6];
                    return {
                        x: a * (x.x - e * x.y + i * x.z) + d,
                        y: a * (e * x.x + x.y - _ * x.z) + f,
                        z: a * (-i * x.x + _ * x.y + x.z) + t
                    }
                }
            }(a, x.datum_type, x.datum_params)), a = ex(a = ax(e.datum_type) ? function (x, e, a) {
                if (1 === e) return {x: x.x - a[0], y: x.y - a[1], z: x.z - a[2]};
                if (2 === e) {
                    var d = a[0], f = a[1], t = a[2], _ = a[3], i = a[4], e = a[5], a = a[6], d = (x.x - d) / a,
                        f = (x.y - f) / a, a = (x.z - t) / a;
                    return {x: d + e * f - i * a, y: -e * d + f + _ * a, z: i * d - _ * f + a}
                }
            }(a, e.datum_type, e.datum_params) : a, i, t, _), 3 !== e.datum_type || 0 === fx(e, !0, a) ? a : void 0) : a
        };

        function fx(x, e, a) {
            if (null === x.grids || 0 === x.grids.length) return console.log('Grid shift grids not found'), -1;
            for (var d = {x: -a.x, y: a.y}, f = {
                x: Number.NaN,
                y: Number.NaN
            }, t = [], _ = 0; _ < x.grids.length; _++) {
                var i = x.grids[_];
                if (t.push(i.name), i.isNull) {
                    f = d;
                    break
                }
                if (i.mandatory, null !== i.grid) {
                    var s = i.grid.subgrids[0], n = (Math.abs(s.del[1]) + Math.abs(s.del[0])) / 1e4, h = s.ll[0] - n,
                        r = s.ll[1] - n, c = s.ll[0] + (s.lim[0] - 1) * s.del[0] + n,
                        n = s.ll[1] + (s.lim[1] - 1) * s.del[1] + n;
                    if (!(d.y < r || d.x < h || n < d.y || c < d.x || (f = function (x, e, a) {
                        var d = {x: Number.NaN, y: Number.NaN};
                        if (isNaN(x.x)) return d;
                        var f = {x: x.x, y: x.y};
                        f.x -= a.ll[0], f.y -= a.ll[1], f.x = U(f.x - Math.PI) + Math.PI;
                        var t = tx(f, a);
                        if (e) {
                            if (isNaN(t.x)) return d;
                            t.x = f.x - t.x, t.y = f.y - t.y;
                            var _, i = 9;
                            do {
                                if (_ = tx(t, a), isNaN(_.x)) {
                                    console.log('Inverse grid shift iteration failed, presumably at grid edge.  Using first approximation.');
                                    break
                                }
                            } while (_ = {
                                x: f.x - (_.x + t.x),
                                y: f.y - (_.y + t.y)
                            }, t.x += _.x, t.y += _.y, i-- && 1e-12 < Math.abs(_.x) && 1e-12 < Math.abs(_.y));
                            if (i < 0) return console.log('Inverse grid shift iterator failed to converge.'), d;
                            d.x = U(t.x + a.ll[0]), d.y = t.y + a.ll[1]
                        } else isNaN(t.x) || (d.x = x.x + t.x, d.y = x.y + t.y);
                        return d
                    }(d, e, s), isNaN(f.x)))) break
                } else if (i.mandatory) return console.log('Unable to find mandatory grid ' + i.name + '\''), -1
            }
            return isNaN(f.x) ? (console.log('Failed to find a grid shift table for location \'' + -d.x * o + ' ' + d.y * o + ' tried: ' + t + '\''), -1) : (a.x = -f.x, a.y = f.y, 0)
        }

        function tx(x, e) {
            var a = {x: x.x / e.del[0], y: x.y / e.del[1]}, d = Math.floor(a.x), f = Math.floor(a.y), t = a.x - +d,
                _ = a.y - +f, i = {x: Number.NaN, y: Number.NaN};
            if (d < 0 || d >= e.lim[0]) return i;
            if (f < 0 || f >= e.lim[1]) return i;
            var s = f * e.lim[0] + d, n = e.cvs[s][0], h = e.cvs[s][1];
            s++;
            var r = e.cvs[s][0], c = e.cvs[s][1];
            s += e.lim[0];
            var o = e.cvs[s][0], x = e.cvs[s][1];
            s--;
            a = e.cvs[s][0], f = e.cvs[s][1], d = t * _, e = t * (1 - _), s = (1 - t) * (1 - _), _ *= 1 - t;
            return i.x = s * n + e * r + _ * a + d * o, i.y = s * h + e * c + _ * f + d * x, i
        }

        var _x = function (x, e, a) {
            for (var d, f, t = a.x, _ = a.y, i = a.z || 0, s = {}, n = 0; n < 3; n++) if (!e || 2 !== n || void 0 !== a.z) switch (f = 0 === n ? (d = t, -1 !== 'ew'.indexOf(x.axis[n]) ? 'x' : 'y') : 1 === n ? (d = _, -1 !== 'ns'.indexOf(x.axis[n]) ? 'y' : 'x') : (d = i, 'z'), x.axis[n]) {
                case'e':
                    s[f] = d;
                    break;
                case'w':
                    s[f] = -d;
                    break;
                case'n':
                    s[f] = d;
                    break;
                case's':
                    s[f] = -d;
                    break;
                case'u':
                    void 0 !== a[f] && (s.z = d);
                    break;
                case'd':
                    void 0 !== a[f] && (s.z = -d);
                    break;
                default:
                    return null
            }
            return s
        }, ix = function (x) {
            var e = {x: x[0], y: x[1]};
            return 2 < x.length && (e.z = x[2]), 3 < x.length && (e.m = x[3]), e
        };

        function sx(x) {
            if ('function' == _typeof(Number.isFinite)) {
                if (Number.isFinite(x)) return;
                throw new TypeError('coordinates must be finite numbers')
            }
            if ('number' != _typeof(x) || x != x || !isFinite(x)) throw new TypeError('coordinates must be finite numbers')
        }

        function nx(x, e, a) {
            var d, f;
            if (Array.isArray(a) && (a = ix(a)), sx((f = a).x), sx(f.y), x.datum && e.datum && (f = e, (1 === (d = x).datum.datum_type || 2 === d.datum.datum_type) && 'WGS84' !== f.datumCode || (1 === f.datum.datum_type || 2 === f.datum.datum_type) && 'WGS84' !== d.datumCode) && (a = nx(x, d = new $('WGS84'), a), x = d), 'enu' !== x.axis && (a = _x(x, !1, a)), 'longlat' === x.projName) a = {
                x: a.x * m,
                y: a.y * m,
                z: a.z || 0
            }; else if (x.to_meter && (a = {
                x: a.x * x.to_meter,
                y: a.y * x.to_meter,
                z: a.z || 0
            }), !(a = x.inverse(a))) return;
            if (x.from_greenwich && (a.x += x.from_greenwich), a = dx(x.datum, e.datum, a)) return e.from_greenwich && (a = {
                x: a.x - e.from_greenwich,
                y: a.y,
                z: a.z || 0
            }), 'longlat' === e.projName ? a = {
                x: a.x * o,
                y: a.y * o,
                z: a.z || 0
            } : (a = e.forward(a), e.to_meter && (a = {
                x: a.x / e.to_meter,
                y: a.y / e.to_meter,
                z: a.z || 0
            })), 'enu' !== e.axis ? _x(e, !0, a) : a
        }

        var hx = $('WGS84');

        function rx(e, a, d) {
            var f, x;
            return Array.isArray(d) ? (x = nx(e, a, d) || {
                x: NaN,
                y: NaN
            }, 2 < d.length ? void 0 !== e.name && 'geocent' === e.name || void 0 !== a.name && 'geocent' === a.name ? 'number' == _typeof(x.z) ? [x.x, x.y, x.z].concat(d.splice(3)) : [x.x, x.y, d[2]].concat(d.splice(3)) : [x.x, x.y].concat(d.splice(2)) : [x.x, x.y]) : (f = nx(e, a, d), 2 === (x = Object.keys(d)).length || x.forEach(function (x) {
                if (void 0 !== e.name && 'geocent' === e.name || void 0 !== a.name && 'geocent' === a.name) {
                    if ('x' === x || 'y' === x || 'z' === x) return
                } else if ('x' === x || 'y' === x) return;
                f[x] = d[x]
            }), f)
        }

        function cx(x) {
            return x instanceof $ ? x : x.oProj ? x.oProj : $(x)
        }

        function ox(e, a, x) {
            e = cx(e);
            var d = !1;
            return void 0 === a ? (a = e, e = hx, d = !0) : void 0 === a.x && !Array.isArray(a) || (x = a, a = e, e = hx, d = !0), a = cx(a), x ? rx(e, a, x) : (x = {
                forward: function (x) {
                    return rx(e, a, x)
                }, inverse: function (x) {
                    return rx(a, e, x)
                }
            }, d && (x.oProj = a), x)
        }

        var lx = 73, ux = 79, bx = {
            forward: mx, inverse: function (x) {
                x = Zx(Vx(x.toUpperCase()));
                return x.lat && x.lon ? [x.lon, x.lat, x.lon, x.lat] : [x.left, x.bottom, x.right, x.top]
            }, toPoint: px
        };

        function mx(x, e) {
            return e = e || 5, a = function (x) {
                var e = x.lat, a = x.lon, d = yx(e), f = yx(a), t = Math.floor((a + 180) / 6) + 1;
                180 === a && (t = 60), 56 <= e && e < 64 && 3 <= a && a < 12 && (t = 32), 72 <= e && e < 84 && (0 <= a && a < 9 ? t = 31 : 9 <= a && a < 21 ? t = 33 : 21 <= a && a < 33 ? t = 35 : 33 <= a && a < 42 && (t = 37));
                var _ = yx(6 * (t - 1) - 180 + 3), i = 6378137 / Math.sqrt(1 - .00669438 * Math.sin(d) * Math.sin(d)),
                    x = Math.tan(d) * Math.tan(d), a = .006739496752268451 * Math.cos(d) * Math.cos(d),
                    _ = .9996 * i * ((f = Math.cos(d) * (f - _)) + (1 - x + a) * f * f * f / 6 + (5 - 18 * x + x * x + 72 * a - .39089081163157013) * f * f * f * f * f / 120) + 5e5,
                    f = .9996 * (6378137 * (.9983242984503243 * d - .002514607064228144 * Math.sin(2 * d) + 2639046602129982e-21 * Math.sin(4 * d) - 3.418046101696858e-9 * Math.sin(6 * d)) + i * Math.tan(d) * (f * f / 2 + (5 - x + 9 * a + 4 * a * a) * f * f * f * f / 24 + (61 - 58 * x + x * x + 600 * a - 2.2240339282485886) * f * f * f * f * f * f / 720));
                return e < 0 && (f += 1e7), {
                    northing: Math.round(f),
                    easting: Math.round(_),
                    zoneNumber: t,
                    zoneLetter: (t = 'Z', (e = e) <= 84 && 72 <= e ? t = 'X' : e < 72 && 64 <= e ? t = 'W' : e < 64 && 56 <= e ? t = 'V' : e < 56 && 48 <= e ? t = 'U' : e < 48 && 40 <= e ? t = 'T' : e < 40 && 32 <= e ? t = 'S' : e < 32 && 24 <= e ? t = 'R' : e < 24 && 16 <= e ? t = 'Q' : e < 16 && 8 <= e ? t = 'P' : e < 8 && 0 <= e ? t = 'N' : e < 0 && -8 <= e ? t = 'M' : e < -8 && -16 <= e ? t = 'L' : e < -16 && -24 <= e ? t = 'K' : e < -24 && -32 <= e ? t = 'J' : e < -32 && -40 <= e ? t = 'H' : e < -40 && -48 <= e ? t = 'G' : e < -48 && -56 <= e ? t = 'F' : e < -56 && -64 <= e ? t = 'E' : e < -64 && -72 <= e ? t = 'D' : e < -72 && -80 <= e && (t = 'C'), t)
                }
            }({
                lat: x[1],
                lon: x[0]
            }), d = e, _ = '00000' + a.easting, i = '00000' + a.northing, a.zoneNumber + a.zoneLetter + (f = a.easting, t = a.northing, x = Mx(a.zoneNumber), e = Math.floor(f / 1e5), a = Math.floor(t / 1e5) % 20, f = x - 1, t = 'AJSAJS'.charCodeAt(f), x = 'AFAFAF'.charCodeAt(f), f = !1, 90 < (e = t + e - 1) && (e = e - 90 + 65 - 1, f = !0), (e === lx || t < lx && lx < e || (lx < e || t < lx) && f) && e++, (e === ux || t < ux && ux < e || (ux < e || t < ux) && f) && ++e === lx && e++, 90 < e && (e = e - 90 + 65 - 1), f = 86 < (a = x + a) && (a = a - 86 + 65 - 1, !0), (a === lx || x < lx && lx < a || (lx < a || x < lx) && f) && a++, (a === ux || x < ux && ux < a || (ux < a || x < ux) && f) && ++a === lx && a++, 86 < a && (a = a - 86 + 65 - 1), String.fromCharCode(e) + String.fromCharCode(a)) + _.substr(_.length - 5, d) + i.substr(i.length - 5, d);
            var a, d, f, t, _, i
        }

        function px(x) {
            x = Zx(Vx(x.toUpperCase()));
            return x.lat && x.lon ? [x.lon, x.lat] : [(x.left + x.right) / 2, (x.top + x.bottom) / 2]
        }

        function yx(x) {
            return x * (Math.PI / 180)
        }

        function vx(x) {
            return x / Math.PI * 180
        }

        function Zx(x) {
            var e = x.northing, a = x.easting, d = x.zoneLetter, f = x.zoneNumber;
            if (f < 0 || 60 < f) return null;
            var t = (1 - Math.sqrt(.99330562)) / (1 + Math.sqrt(.99330562)), _ = a - 5e5, i = e;
            d < 'N' && (i -= 1e7);
            a = 6 * (f - 1) - 180 + 3, d = (e = i / .9996 / 6367449.145945056) + (3 * t / 2 - 27 * t * t * t / 32) * Math.sin(2 * e) + (21 * t * t / 16 - 55 * t * t * t * t / 32) * Math.sin(4 * e) + 151 * t * t * t / 96 * Math.sin(6 * e), f = 6378137 / Math.sqrt(1 - .00669438 * Math.sin(d) * Math.sin(d)), i = Math.tan(d) * Math.tan(d), t = .006739496752268451 * Math.cos(d) * Math.cos(d), e = 6335439.32722994 / Math.pow(1 - .00669438 * Math.sin(d) * Math.sin(d), 1.5), _ /= .9996 * f, e = vx(e = d - f * Math.tan(d) / e * (_ * _ / 2 - (5 + 3 * i + 10 * t - 4 * t * t - .06065547077041606) * _ * _ * _ * _ / 24 + (61 + 90 * i + 298 * t + 45 * i * i - 1.6983531815716497 - 3 * t * t) * _ * _ * _ * _ * _ * _ / 720)), d = a + vx(d = (_ - (1 + 2 * i + t) * _ * _ * _ / 6 + (5 - 2 * t + 28 * i - 3 * t * t + .05391597401814761 + 24 * i * i) * _ * _ * _ * _ * _ / 120) / Math.cos(d));
            return x.accuracy ? {
                top: (x = Zx({
                    northing: x.northing + x.accuracy,
                    easting: x.easting + x.accuracy,
                    zoneLetter: x.zoneLetter,
                    zoneNumber: x.zoneNumber
                })).lat, right: x.lon, bottom: e, left: d
            } : {lat: e, lon: d}
        }

        function Mx(x) {
            x %= 6;
            return x = 0 === x ? 6 : x
        }

        function Vx(x) {
            if (x && 0 === x.length) throw 'MGRSPoint coverting from nothing';
            for (var e, a = x.length, d = null, f = '', t = 0; !/[A-Z]/.test(e = x.charAt(t));) {
                if (2 <= t) throw'MGRSPoint bad conversion from: ' + x;
                f += e, t++
            }
            var _ = parseInt(f, 10);
            if (0 === t || a < t + 3) throw 'MGRSPoint bad conversion from: ' + x;
            var i = x.charAt(t++);
            if (i <= 'A' || 'B' === i || 'Y' === i || 'Z' <= i || 'I' === i || 'O' === i) throw 'MGRSPoint zone letter ' + i + ' not handled: ' + x;
            for (var d = x.substring(t, t += 2), s = Mx(_), n = function (x, e) {
                for (var a = 'AJSAJS'.charCodeAt(e - 1), d = 1e5, f = !1; a !== x.charCodeAt(0);) {
                    if (++a === lx && a++, a === ux && a++, 90 < a) {
                        if (f) throw 'Bad character: ' + x;
                        a = 65, f = !0
                    }
                    d += 1e5
                }
                return d
            }(d.charAt(0), s), h = function (x, e) {
                if ('V' < x) throw 'MGRSPoint given invalid Northing ' + x;
                for (var a = 'AFAFAF'.charCodeAt(e - 1), d = 0, f = !1; a !== x.charCodeAt(0);) {
                    if (++a === lx && a++, a === ux && a++, 86 < a) {
                        if (f) throw 'Bad character: ' + x;
                        a = 65, f = !0
                    }
                    d += 1e5
                }
                return d
            }(d.charAt(1), s); h < function (x) {
                var e;
                switch (x) {
                    case'C':
                        e = 11e5;
                        break;
                    case'D':
                        e = 2e6;
                        break;
                    case'E':
                        e = 28e5;
                        break;
                    case'F':
                        e = 37e5;
                        break;
                    case'G':
                        e = 46e5;
                        break;
                    case'H':
                        e = 55e5;
                        break;
                    case'J':
                        e = 64e5;
                        break;
                    case'K':
                        e = 73e5;
                        break;
                    case'L':
                        e = 82e5;
                        break;
                    case'M':
                        e = 91e5;
                        break;
                    case'N':
                        e = 0;
                        break;
                    case'P':
                        e = 8e5;
                        break;
                    case'Q':
                        e = 17e5;
                        break;
                    case'R':
                        e = 26e5;
                        break;
                    case'S':
                        e = 35e5;
                        break;
                    case'T':
                        e = 44e5;
                        break;
                    case'U':
                        e = 53e5;
                        break;
                    case'V':
                        e = 62e5;
                        break;
                    case'W':
                        e = 7e6;
                        break;
                    case'X':
                        e = 79e5;
                        break;
                    default:
                        e = -1
                }
                if (0 <= e) return e;
                throw 'Invalid zone letter: ' + x
            }(i);) h += 2e6;
            var r = a - t;
            if (r % 2 != 0) throw'MGRSPoint has to have an even number \nof digits after the zone letter and two 100km letters - front \nhalf for easting meters, second half for \nnorthing meters' + x;
            var c, d = r / 2, s = 0, a = 0;
            return 0 < d && (c = 1e5 / Math.pow(10, d), r = x.substring(t, t + d), s = parseFloat(r) * c, d = x.substring(t + d), a = parseFloat(d) * c), {
                easting: s + n,
                northing: a + h,
                zoneLetter: i,
                zoneNumber: _,
                accuracy: c
            }
        }

        function Gx(x, e, a) {
            if (!(this instanceof Gx)) return new Gx(x, e, a);
            var d;
            Array.isArray(x) ? (this.x = x[0], this.y = x[1], this.z = x[2] || 0) : 'object' == _typeof(x) ? (this.x = x.x, this.y = x.y, this.z = x.z || 0) : 'string' == _typeof(x) && void 0 === e ? (d = x.split(','), this.x = parseFloat(d[0], 10), this.y = parseFloat(d[1], 10), this.z = parseFloat(d[2], 10) || 0) : (this.x = x, this.y = e, this.z = a || 0), console.warn('proj4.Point will be removed in version 3, use proj4.toPoint')
        }

        Gx.fromMGRS = function (x) {
            return new Gx(px(x))
        }, Gx.prototype.toMGRS = function (x) {
            return mx([this.x, this.y], x)
        };

        function Wx(x) {
            var e = [];
            e[0] = 1 - x * (.25 + x * (.046875 + x * (.01953125 + x * Bx))), e[1] = x * (.75 - x * (.046875 + x * (.01953125 + x * Bx)));
            var a = x * x;
            return e[2] = a * (.46875 - x * (.013020833333333334 + .007120768229166667 * x)), a *= x, e[3] = a * (.3645833333333333 - .005696614583333333 * x), e[4] = a * x * .3076171875, e
        }

        function Xx(x, e, a, d) {
            return a *= e, e *= e, d[0] * x - a * (d[1] + e * (d[2] + e * (d[3] + e * d[4])))
        }

        function gx(x, e, a) {
            for (var d = 1 / (1 - e), f = x, t = 20; t; --t) {
                var _ = Math.sin(f), i = 1 - e * _ * _;
                if (f -= i = (Xx(f, _, Math.cos(f), a) - x) * (i * Math.sqrt(i)) * d, Math.abs(i) < 1e-10) return f
            }
            return f
        }

        function Rx(x) {
            return ((x = Math.exp(x)) - 1 / x) / 2
        }

        function wx(x, e) {
            x = Math.abs(x), e = Math.abs(e);
            var a = Math.max(x, e), e = Math.min(x, e) / (a || 1);
            return a * Math.sqrt(1 + Math.pow(e, 2))
        }

        function Yx(x, e) {
            for (var a, d = 2 * Math.cos(2 * e), f = x.length - 1, t = x[f], _ = 0; 0 <= --f;) a = d * t - _ + x[f], _ = t, t = a;
            return e + a * Math.sin(2 * e)
        }

        function Nx(x, e, a) {
            for (var d, f, t = Math.sin(e), _ = Math.cos(e), e = Rx(a), a = function (x) {
                x = Math.exp(x);
                return (x + 1 / x) / 2
            }(a), i = 2 * _ * a, s = -2 * t * e, n = x.length - 1, h = x[n], r = 0, c = 0, o = 0; 0 <= --n;) d = c, f = r, h = i * (c = h) - d - s * (r = o) + x[n], o = s * c - f + i * r;
            return [(i = t * a) * h - (s = _ * e) * o, i * o + s * h]
        }

        function kx(x, e) {
            return Math.pow((1 - x) / (1 + x), e)
        }

        function Fx(x, e, a, d, f) {
            return x * f - e * Math.sin(2 * f) + a * Math.sin(4 * f) - d * Math.sin(6 * f)
        }

        function zx(x) {
            return 1 - .25 * x * (1 + x / 16 * (3 + 1.25 * x))
        }

        function Jx(x) {
            return .375 * x * (1 + .25 * x * (1 + .46875 * x))
        }

        function Tx(x) {
            return .05859375 * x * x * (1 + .75 * x)
        }

        function Ux(x) {
            return x * x * x * (35 / 3072)
        }

        function Qx(x, e, a) {
            return a *= e, x / Math.sqrt(1 - a * a)
        }

        function Lx(x) {
            return Math.abs(x) < b ? x : x - w(x) * Math.PI
        }

        function Ix(x, e, a, d, f) {
            for (var t, _ = x / e, i = 0; i < 15; i++) if (_ += t = (x - (e * _ - a * Math.sin(2 * _) + d * Math.sin(4 * _) - f * Math.sin(6 * _))) / (e - 2 * a * Math.cos(2 * _) + 4 * d * Math.cos(4 * _) - 6 * f * Math.cos(6 * _)), Math.abs(t) <= 1e-10) return _;
            return NaN
        }

        function Ax(x, e) {
            var a;
            return 1e-7 < x ? (1 - x * x) * (e / (1 - (a = x * e) * a) - .5 / x * Math.log((1 - a) / (1 + a))) : 2 * e
        }

        function Ex(x) {
            return 1 < Math.abs(x) && (x = 1 < x ? 1 : -1), Math.asin(x)
        }

        var Sx = Gx, Bx = .01068115234375, Hx = {
            init: function () {
                this.x0 = void 0 !== this.x0 ? this.x0 : 0, this.y0 = void 0 !== this.y0 ? this.y0 : 0, this.long0 = void 0 !== this.long0 ? this.long0 : 0, this.lat0 = void 0 !== this.lat0 ? this.lat0 : 0, this.es && (this.en = Wx(this.es), this.ml0 = Xx(this.lat0, Math.sin(this.lat0), Math.cos(this.lat0), this.en))
            }, forward: function (x) {
                var e = x.x, a = x.y, d = U(e - this.long0), f = Math.sin(a), t = Math.cos(a);
                if (this.es) {
                    var _ = t * d, i = Math.pow(_, 2), s = this.ep2 * Math.pow(t, 2), n = Math.pow(s, 2),
                        e = 1e-10 < Math.abs(t) ? Math.tan(a) : 0, h = Math.pow(e, 2), r = Math.pow(h, 2),
                        c = 1 - this.es * Math.pow(f, 2);
                    _ /= Math.sqrt(c);
                    e = Xx(a, f, t, this.en), c = this.a * (this.k0 * _ * (1 + i / 6 * (1 - h + s + i / 20 * (5 - 18 * h + r + 14 * s - 58 * h * s + i / 42 * (61 + 179 * r - r * h - 479 * h))))) + this.x0, r = this.a * (this.k0 * (e - this.ml0 + f * d * _ / 2 * (1 + i / 12 * (5 - h + 9 * s + 4 * n + i / 30 * (61 + r - 58 * h + 270 * s - 330 * h * s + i / 56 * (1385 + 543 * r - r * h - 3111 * h)))))) + this.y0
                } else {
                    h = t * Math.sin(d);
                    if (Math.abs(Math.abs(h) - 1) < 1e-10) return 93;
                    if (c = .5 * this.a * this.k0 * Math.log((1 + h) / (1 - h)) + this.x0, r = t * Math.cos(d) / Math.sqrt(1 - Math.pow(h, 2)), 1 <= (h = Math.abs(r))) {
                        if (1e-10 < h - 1) return 93;
                        r = 0
                    } else r = Math.acos(r);
                    a < 0 && (r = -r), r = this.a * this.k0 * (r - this.lat0) + this.y0
                }
                return x.x = c, x.y = r, x
            }, inverse: function (x) {
                var e, a, d, f, t, _, i, s, n, h, r = (x.x - this.x0) * (1 / this.a),
                    c = (x.y - this.y0) * (1 / this.a);
                return h = this.es ? (_ = this.ml0 + c / this.k0, e = gx(_, this.es, this.en), Math.abs(e) < b ? (i = Math.sin(e), a = Math.cos(e), s = 1e-10 < Math.abs(a) ? Math.tan(e) : 0, d = this.ep2 * Math.pow(a, 2), n = Math.pow(d, 2), f = Math.pow(s, 2), t = Math.pow(f, 2), _ = 1 - this.es * Math.pow(i, 2), i = r * Math.sqrt(_) / this.k0, n = e - (_ *= s) * (s = Math.pow(i, 2)) / (1 - this.es) * .5 * (1 - s / 12 * (5 + 3 * f - 9 * d * f + d - 4 * n - s / 30 * (61 + 90 * f - 252 * d * f + 45 * t + 46 * d - s / 56 * (1385 + 3633 * f + 4095 * t + 1574 * t * f)))), U(this.long0 + i * (1 - s / 6 * (1 + 2 * f + d - s / 20 * (5 + 28 * f + 24 * t + 8 * d * f + 6 * d - s / 42 * (61 + 662 * f + 1320 * t + 720 * t * f)))) / a)) : (n = b * w(c), 0)) : (r = .5 * ((h = Math.exp(r / this.k0)) - 1 / h), h = this.lat0 + c / this.k0, h = Math.cos(h), _ = Math.sqrt((1 - Math.pow(h, 2)) / (1 + Math.pow(r, 2))), n = Math.asin(_), c < 0 && (n = -n), 0 == r && 0 === h ? 0 : U(Math.atan2(r, h) + this.long0)), x.x = h, x.y = n, x
            }, names: ['Fast_Transverse_Mercator', 'Fast Transverse Mercator']
        }, Cx = {
            init: function () {
                if (!this.approx && (isNaN(this.es) || this.es <= 0)) throw new Error('Incorrect elliptical usage. Try using the +approx option in the proj string, or PROJECTION["Fast_Transverse_Mercator"] in the WKT.');
                this.approx && (Hx.init.apply(this), this.forward = Hx.forward, this.inverse = Hx.inverse), this.x0 = void 0 !== this.x0 ? this.x0 : 0, this.y0 = void 0 !== this.y0 ? this.y0 : 0, this.long0 = void 0 !== this.long0 ? this.long0 : 0, this.lat0 = void 0 !== this.lat0 ? this.lat0 : 0, this.cgb = [], this.cbg = [], this.utg = [], this.gtu = [];
                var x = this.es / (1 + Math.sqrt(1 - this.es)), e = x / (2 - x), x = e;
                this.cgb[0] = e * (2 + e * (-2 / 3 + e * (e * (116 / 45 + e * (26 / 45 + -2854 / 675 * e)) - 2))), this.cbg[0] = e * (e * (2 / 3 + e * (4 / 3 + e * (-82 / 45 + e * (32 / 45 + 4642 / 4725 * e)))) - 2), x *= e, this.cgb[1] = x * (7 / 3 + e * (e * (-227 / 45 + e * (2704 / 315 + 2323 / 945 * e)) - 1.6)), this.cbg[1] = x * (5 / 3 + e * (-16 / 15 + e * (-13 / 9 + e * (904 / 315 + -1522 / 945 * e)))), x *= e, this.cgb[2] = x * (56 / 15 + e * (-136 / 35 + e * (-1262 / 105 + 73814 / 2835 * e))), this.cbg[2] = x * (-26 / 15 + e * (34 / 21 + e * (1.6 + -12686 / 2835 * e))), x *= e, this.cgb[3] = x * (4279 / 630 + e * (-332 / 35 + -399572 / 14175 * e)), this.cbg[3] = x * (1237 / 630 + e * (-24832 / 14175 * e - 2.4)), x *= e, this.cgb[4] = x * (4174 / 315 + -144838 / 6237 * e), this.cbg[4] = x * (-734 / 315 + 109598 / 31185 * e), x *= e, this.cgb[5] = 601676 / 22275 * x, this.cbg[5] = 444337 / 155925 * x, x = Math.pow(e, 2), this.Qn = this.k0 / (1 + e) * (1 + x * (.25 + x * (1 / 64 + x / 256))), this.utg[0] = e * (e * (2 / 3 + e * (-37 / 96 + e * (1 / 360 + e * (81 / 512 + -96199 / 604800 * e)))) - .5), this.gtu[0] = e * (.5 + e * (-2 / 3 + e * (5 / 16 + e * (41 / 180 + e * (-127 / 288 + 7891 / 37800 * e))))), this.utg[1] = x * (-1 / 48 + e * (-1 / 15 + e * (437 / 1440 + e * (-46 / 105 + 1118711 / 3870720 * e)))), this.gtu[1] = x * (13 / 48 + e * (e * (557 / 1440 + e * (281 / 630 + -1983433 / 1935360 * e)) - .6)), x *= e, this.utg[2] = x * (-17 / 480 + e * (37 / 840 + e * (209 / 4480 + -5569 / 90720 * e))), this.gtu[2] = x * (61 / 240 + e * (-103 / 140 + e * (15061 / 26880 + 167603 / 181440 * e))), x *= e, this.utg[3] = x * (-4397 / 161280 + e * (11 / 504 + 830251 / 7257600 * e)), this.gtu[3] = x * (49561 / 161280 + e * (-179 / 168 + 6601661 / 7257600 * e)), x *= e, this.utg[4] = x * (-4583 / 161280 + 108847 / 3991680 * e), this.gtu[4] = x * (34729 / 80640 + -3418889 / 1995840 * e), x *= e, this.utg[5] = -.03233083094085698 * x, this.gtu[5] = .6650675310896665 * x;
                x = Yx(this.cbg, this.lat0);
                this.Zb = -this.Qn * (x + function (x, e) {
                    for (var a, d = 2 * Math.cos(e), f = x.length - 1, t = x[f], _ = 0; 0 <= --f;) a = d * t - _ + x[f], _ = t, t = a;
                    return Math.sin(e) * a
                }(this.gtu, 2 * x))
            },
            forward: function (x) {
                var e, a = U(x.x - this.long0), d = x.y, d = Yx(this.cbg, d), f = Math.sin(d), t = Math.cos(d),
                    _ = Math.sin(a), i = Math.cos(a);
                d = Math.atan2(f, i * t), a = Math.atan2(_ * t, wx(f, t * i)), e = Math.tan(a), _ = Math.abs(e), f = _ * (1 + _ / (wx(1, _) + 1)), _ = 0 == (i = (t = 1 + f) - 1) ? f : f * Math.log(t) / i;
                var s, a = e < 0 ? -_ : _, _ = Nx(this.gtu, 2 * d, 2 * a);
                return d += _[0], a += _[1], d = Math.abs(a) <= 2.623395162778 ? (s = this.a * (this.Qn * a) + this.x0, this.a * (this.Qn * d + this.Zb) + this.y0) : s = 1 / 0, x.x = s, x.y = d, x
            },
            inverse: function (x) {
                var e, a, d, f, t = (x.x - this.x0) * (1 / this.a), _ = (x.y - this.y0) * (1 / this.a);
                return _ = (_ - this.Zb) / this.Qn, t /= this.Qn, _ = Math.abs(t) <= 2.623395162778 ? (_ += (f = Nx(this.utg, 2 * _, 2 * t))[0], t += f[1], t = Math.atan(Rx(t)), e = Math.sin(_), a = Math.cos(_), d = Math.sin(t), f = Math.cos(t), _ = Math.atan2(e * f, wx(d, f * a)), t = Math.atan2(d, f * a), a = U(t + this.long0), Yx(this.cgb, _)) : a = 1 / 0, x.x = a, x.y = _, x
            },
            names: ['Extended_Transverse_Mercator', 'Extended Transverse Mercator', 'etmerc', 'Transverse_Mercator', 'Transverse Mercator', 'tmerc']
        }, jx = {
            init: function () {
                var x = function (x, e) {
                    if (void 0 === x) {
                        if ((x = Math.floor(30 * (U(e) + Math.PI) / Math.PI) + 1) < 0) return 0;
                        if (60 < x) return 60
                    }
                    return x
                }(this.zone, this.long0);
                if (void 0 === x) throw new Error('unknown utm zone');
                this.lat0 = 0, this.long0 = (6 * Math.abs(x) - 183) * m, this.x0 = 5e5, this.y0 = this.utmSouth ? 1e7 : 0, this.k0 = .9996, Cx.init.apply(this), this.forward = Cx.forward, this.inverse = Cx.inverse
            }, names: ['Universal Transverse Mercator System', 'utm'], dependsOn: 'etmerc'
        }, Ox = {
            init: function () {
                var x = Math.sin(this.lat0), e = Math.cos(this.lat0);
                e *= e, this.rc = Math.sqrt(1 - this.es) / (1 - this.es * x * x), this.C = Math.sqrt(1 + this.es * e * e / (1 - this.es)), this.phic0 = Math.asin(x / this.C), this.ratexp = .5 * this.C * this.e, this.K = Math.tan(.5 * this.phic0 + p) / (Math.pow(Math.tan(.5 * this.lat0 + p), this.C) * kx(this.e * x, this.ratexp))
            }, forward: function (x) {
                var e = x.x, a = x.y;
                return x.y = 2 * Math.atan(this.K * Math.pow(Math.tan(.5 * a + p), this.C) * kx(this.e * Math.sin(a), this.ratexp)) - b, x.x = this.C * e, x
            }, inverse: function (x) {
                for (var e = x.x / this.C, a = x.y, d = Math.pow(Math.tan(.5 * a + p) / this.K, 1 / this.C), f = 20; 0 < f && (a = 2 * Math.atan(d * kx(this.e * Math.sin(x.y), -.5 * this.e)) - b, !(Math.abs(a - x.y) < 1e-14)); --f) x.y = a;
                return f ? (x.x = e, x.y = a, x) : null
            }, names: '.gauss'
        }, Dx = {
            init: function () {
                Ox.init.apply(this), this.rc && (this.sinc0 = Math.sin(this.phic0), this.cosc0 = Math.cos(this.phic0), this.R2 = 2 * this.rc, this.title || (this.title = 'Oblique Stereographic Alternative'))
            },
            forward: function (x) {
                var e, a, d, f;
                return x.x = U(x.x - this.long0), Ox.forward.apply(this, [x]), e = Math.sin(x.y), a = Math.cos(x.y), d = Math.cos(x.x), f = this.k0 * this.R2 / (1 + this.sinc0 * e + this.cosc0 * a * d), x.x = f * a * Math.sin(x.x), x.y = f * (this.cosc0 * e - this.sinc0 * a * d), x.x = this.a * x.x + this.x0, x.y = this.a * x.y + this.y0, x
            },
            inverse: function (x) {
                var e, a, d, f;
                return x.x = (x.x - this.x0) / this.a, x.y = (x.y - this.y0) / this.a, x.x /= this.k0, x.y /= this.k0, f = (e = Math.sqrt(x.x * x.x + x.y * x.y)) ? (d = 2 * Math.atan2(e, this.R2), f = Math.sin(d), a = Math.cos(d), d = Math.asin(a * this.sinc0 + x.y * f * this.cosc0 / e), Math.atan2(x.x * f, e * this.cosc0 * a - x.y * this.sinc0 * f)) : (d = this.phic0, 0), x.x = f, x.y = d, Ox.inverse.apply(this, [x]), x.x = U(x.x + this.long0), x
            },
            names: ['Stereographic_North_Pole', 'Oblique_Stereographic', 'Polar_Stereographic', 'sterea', 'Oblique Stereographic Alternative', 'Double_Stereographic']
        }, Px = {
            init: function () {
                this.coslat0 = Math.cos(this.lat0), this.sinlat0 = Math.sin(this.lat0), this.sphere ? 1 === this.k0 && !isNaN(this.lat_ts) && Math.abs(this.coslat0) <= 1e-10 && (this.k0 = .5 * (1 + w(this.lat0) * Math.sin(this.lat_ts))) : (Math.abs(this.coslat0) <= 1e-10 && (0 < this.lat0 ? this.con = 1 : this.con = -1), this.cons = Math.sqrt(Math.pow(1 + this.e, 1 + this.e) * Math.pow(1 - this.e, 1 - this.e)), 1 === this.k0 && !isNaN(this.lat_ts) && Math.abs(this.coslat0) <= 1e-10 && (this.k0 = .5 * this.cons * R(this.e, Math.sin(this.lat_ts), Math.cos(this.lat_ts)) / Y(this.e, this.con * this.lat_ts, this.con * Math.sin(this.lat_ts))), this.ms1 = R(this.e, this.sinlat0, this.coslat0), this.X0 = 2 * Math.atan(this.ssfn_(this.lat0, this.sinlat0, this.e)) - b, this.cosX0 = Math.cos(this.X0), this.sinX0 = Math.sin(this.X0))
            },
            forward: function (x) {
                var e, a, d = x.x, f = x.y, t = Math.sin(f), _ = Math.cos(f), i = U(d - this.long0);
                return Math.abs(Math.abs(d - this.long0) - Math.PI) <= 1e-10 && Math.abs(f + this.lat0) <= 1e-10 ? (x.x = NaN, x.y = NaN) : this.sphere ? (e = 2 * this.k0 / (1 + this.sinlat0 * t + this.coslat0 * _ * Math.cos(i)), x.x = this.a * e * _ * Math.sin(i) + this.x0, x.y = this.a * e * (this.coslat0 * t - this.sinlat0 * _ * Math.cos(i)) + this.y0) : (a = 2 * Math.atan(this.ssfn_(f, t, this.e)) - b, _ = Math.cos(a), a = Math.sin(a), Math.abs(this.coslat0) <= 1e-10 ? (t = Y(this.e, f * this.con, this.con * t), t = 2 * this.a * this.k0 * t / this.cons, x.x = this.x0 + t * Math.sin(d - this.long0), x.y = this.y0 - this.con * t * Math.cos(d - this.long0)) : (Math.abs(this.sinlat0) < 1e-10 ? (e = 2 * this.a * this.k0 / (1 + _ * Math.cos(i)), x.y = e * a) : (e = 2 * this.a * this.k0 * this.ms1 / (this.cosX0 * (1 + this.sinX0 * a + this.cosX0 * _ * Math.cos(i))), x.y = e * (this.cosX0 * a - this.sinX0 * _ * Math.cos(i)) + this.y0), x.x = e * _ * Math.sin(i) + this.x0)), x
            },
            inverse: function (x) {
                var e, a;
                x.x -= this.x0, x.y -= this.y0;
                var d = Math.sqrt(x.x * x.x + x.y * x.y);
                if (this.sphere) {
                    var f = 2 * Math.atan(d / (2 * this.a * this.k0)), t = this.long0, _ = this.lat0;
                    return d <= 1e-10 || (_ = Math.asin(Math.cos(f) * this.sinlat0 + x.y * Math.sin(f) * this.coslat0 / d), t = Math.abs(this.coslat0) < 1e-10 ? 0 < this.lat0 ? U(this.long0 + Math.atan2(x.x, -1 * x.y)) : U(this.long0 + Math.atan2(x.x, x.y)) : U(this.long0 + Math.atan2(x.x * Math.sin(f), d * this.coslat0 * Math.cos(f) - x.y * this.sinlat0 * Math.sin(f)))), x.x = t, x.y = _, x
                }
                if (Math.abs(this.coslat0) <= 1e-10) {
                    if (d <= 1e-10) return _ = this.lat0, t = this.long0, x.x = t, x.y = _, x;
                    x.x *= this.con, x.y *= this.con, e = d * this.cons / (2 * this.a * this.k0), _ = this.con * N(this.e, e), t = this.con * U(this.con * this.long0 + Math.atan2(x.x, -1 * x.y))
                } else e = 2 * Math.atan(d * this.cosX0 / (2 * this.a * this.k0 * this.ms1)), t = this.long0, d <= 1e-10 ? a = this.X0 : (a = Math.asin(Math.cos(e) * this.sinX0 + x.y * Math.sin(e) * this.cosX0 / d), t = U(this.long0 + Math.atan2(x.x * Math.sin(e), d * this.cosX0 * Math.cos(e) - x.y * this.sinX0 * Math.sin(e)))), _ = -1 * N(this.e, Math.tan(.5 * (b + a)));
                return x.x = t, x.y = _, x
            },
            names: ['stere', 'Stereographic_South_Pole', 'Polar Stereographic (variant B)'],
            ssfn_: function (x, e, a) {
                return e *= a, Math.tan(.5 * (b + x)) * Math.pow((1 - e) / (1 + e), .5 * a)
            }
        }, qx = {
            init: function () {
                var x = this.lat0;
                this.lambda0 = this.long0;
                var e = Math.sin(x), a = this.a, d = 1 / this.rf, f = 2 * d - Math.pow(d, 2), d = this.e = Math.sqrt(f);
                this.R = this.k0 * a * Math.sqrt(1 - f) / (1 - f * Math.pow(e, 2)), this.alpha = Math.sqrt(1 + f / (1 - f) * Math.pow(Math.cos(x), 4)), this.b0 = Math.asin(e / this.alpha);
                f = Math.log(Math.tan(Math.PI / 4 + this.b0 / 2)), x = Math.log(Math.tan(Math.PI / 4 + x / 2)), e = Math.log((1 + d * e) / (1 - d * e));
                this.K = f - this.alpha * x + this.alpha * d / 2 * e
            }, forward: function (x) {
                var e = Math.log(Math.tan(Math.PI / 4 - x.y / 2)),
                    a = this.e / 2 * Math.log((1 + this.e * Math.sin(x.y)) / (1 - this.e * Math.sin(x.y))),
                    d = -this.alpha * (e + a) + this.K, e = 2 * (Math.atan(Math.exp(d)) - Math.PI / 4),
                    a = this.alpha * (x.x - this.lambda0),
                    d = Math.atan(Math.sin(a) / (Math.sin(this.b0) * Math.tan(e) + Math.cos(this.b0) * Math.cos(a))),
                    a = Math.asin(Math.cos(this.b0) * Math.sin(e) - Math.sin(this.b0) * Math.cos(e) * Math.cos(a));
                return x.y = this.R / 2 * Math.log((1 + Math.sin(a)) / (1 - Math.sin(a))) + this.y0, x.x = this.R * d + this.x0, x
            }, inverse: function (x) {
                for (var e, a = x.x - this.x0, d = x.y - this.y0, a = a / this.R, d = 2 * (Math.atan(Math.exp(d / this.R)) - Math.PI / 4), f = Math.asin(Math.cos(this.b0) * Math.sin(d) + Math.sin(this.b0) * Math.cos(d) * Math.cos(a)), d = Math.atan(Math.sin(a) / (Math.cos(this.b0) * Math.cos(a) - Math.sin(this.b0) * Math.tan(d))), d = this.lambda0 + d / this.alpha, t = f, _ = -1e3, i = 0; 1e-7 < Math.abs(t - _);) {
                    if (20 < ++i) return;
                    e = 1 / this.alpha * (Math.log(Math.tan(Math.PI / 4 + f / 2)) - this.K) + this.e * Math.log(Math.tan(Math.PI / 4 + Math.asin(this.e * Math.sin(t)) / 2)), _ = t, t = 2 * Math.atan(Math.exp(e)) - Math.PI / 2
                }
                return x.x = d, x.y = t, x
            }, names: ['.somerc']
        }, Kx = {
            init: function () {
                var x, e, a, d, f, t = 0, _ = 0, i = 0, s = 0, n = 0, h = 0, r = 0;
                this.no_off = (f = 'object' == _typeof(this.PROJECTION) ? Object.keys(this.PROJECTION)[0] : this.PROJECTION, 'no_uoff' in this || 'no_off' in this || -1 !== ['Hotine_Oblique_Mercator', 'Hotine_Oblique_Mercator_Azimuth_Natural_Origin'].indexOf(f)), this.no_rot = 'no_rot' in this;
                var c = !1;
                'alpha' in this && (c = !0);
                var o = !1;
                if ('rectified_grid_angle' in this && (o = !0), c && (r = this.alpha), o && (t = this.rectified_grid_angle * m), c || o) _ = this.longc; else if (i = this.long1, n = this.lat1, s = this.long2, h = this.lat2, Math.abs(n - h) <= 1e-7 || (x = Math.abs(n)) <= 1e-7 || Math.abs(x - b) <= 1e-7 || Math.abs(Math.abs(this.lat0) - b) <= 1e-7 || Math.abs(Math.abs(h) - b) <= 1e-7) throw new Error;
                var l = 1 - this.es, u = Math.sqrt(l);
                1e-10 < Math.abs(this.lat0) ? (f = Math.sin(this.lat0), a = Math.cos(this.lat0), x = 1 - this.es * f * f, this.B = a * a, this.B = Math.sqrt(1 + this.es * this.B * this.B / l), this.A = this.B * this.k0 * u / x, (a = (e = this.B * u / (a * Math.sqrt(x))) * e - 1) <= 0 ? a = 0 : (a = Math.sqrt(a), this.lat0 < 0 && (a = -a)), this.E = a += e, this.E *= Math.pow(Y(this.e, this.lat0, f), this.B)) : (this.B = 1 / u, this.A = this.k0, this.E = e = a = 1), c || o ? (c ? (d = Math.asin(Math.sin(r) / e), o || (t = r)) : (d = t, r = Math.asin(e * Math.sin(d))), this.lam0 = _ - Math.asin(.5 * (a - 1 / a) * Math.tan(d)) / this.B) : (o = Math.pow(Y(this.e, n, Math.sin(n)), this.B), _ = Math.pow(Y(this.e, h, Math.sin(h)), this.B), a = this.E / o, n = (_ - o) / (_ + o), h = ((h = this.E * this.E) - _ * o) / (h + _ * o), (x = i - s) < -Math.pi ? s -= y : x > Math.pi && (s += y), this.lam0 = U(.5 * (i + s) - Math.atan(h * Math.tan(.5 * this.B * (i - s)) / n) / this.B), d = Math.atan(2 * Math.sin(this.B * U(i - this.lam0)) / (a - 1 / a)), t = r = Math.asin(e * Math.sin(d))), this.singam = Math.sin(d), this.cosgam = Math.cos(d), this.sinrot = Math.sin(t), this.cosrot = Math.cos(t), this.rB = 1 / this.B, this.ArB = this.A * this.rB, this.BrA = 1 / this.ArB, this.A, this.B, this.no_off ? this.u_0 = 0 : (this.u_0 = Math.abs(this.ArB * Math.atan(Math.sqrt(e * e - 1) / Math.cos(r))), this.lat0 < 0 && (this.u_0 = -this.u_0)), a = .5 * d, this.v_pole_n = this.ArB * Math.log(Math.tan(p - a)), this.v_pole_s = this.ArB * Math.log(Math.tan(p + a))
            },
            forward: function (x) {
                var e, a, d, f, t = {};
                if (x.x = x.x - this.lam0, 1e-10 < Math.abs(Math.abs(x.y) - b)) {
                    if (e = .5 * ((a = this.E / Math.pow(Y(this.e, x.y, Math.sin(x.y)), this.B)) - (d = 1 / a)), f = .5 * (a + d), a = Math.sin(this.B * x.x), f = (e * this.singam - a * this.cosgam) / f, Math.abs(Math.abs(f) - 1) < 1e-10) throw new Error;
                    f = .5 * this.ArB * Math.log((1 - f) / (1 + f)), d = Math.cos(this.B * x.x), d = Math.abs(d) < 1e-7 ? this.A * x.x : this.ArB * Math.atan2(e * this.cosgam + a * this.singam, d)
                } else f = 0 < x.y ? this.v_pole_n : this.v_pole_s, d = this.ArB * x.y;
                return this.no_rot ? (t.x = d, t.y = f) : (d -= this.u_0, t.x = f * this.cosrot + d * this.sinrot, t.y = d * this.cosrot - f * this.sinrot), t.x = this.a * t.x + this.x0, t.y = this.a * t.y + this.y0, t
            },
            inverse: function (x) {
                var e, a, d, f = {};
                if (x.x = (x.x - this.x0) * (1 / this.a), x.y = (x.y - this.y0) * (1 / this.a), e = this.no_rot ? (d = x.y, x.x) : (d = x.x * this.cosrot - x.y * this.sinrot, x.y * this.cosrot + x.x * this.sinrot + this.u_0), x = .5 * ((a = Math.exp(-this.BrA * d)) - 1 / a), d = .5 * (a + 1 / a), d = ((a = Math.sin(this.BrA * e)) * this.cosgam + x * this.singam) / d, Math.abs(Math.abs(d) - 1) < 1e-10) f.x = 0, f.y = d < 0 ? -b : b; else {
                    if (f.y = this.E / Math.sqrt((1 + d) / (1 - d)), f.y = N(this.e, Math.pow(f.y, 1 / this.B)), f.y === 1 / 0) throw new Error;
                    f.x = -this.rB * Math.atan2(x * this.cosgam - a * this.singam, Math.cos(this.BrA * e))
                }
                return f.x += this.lam0, f
            },
            names: ['Hotine_Oblique_Mercator', 'Hotine Oblique Mercator', 'Hotine_Oblique_Mercator_Azimuth_Natural_Origin', 'Hotine_Oblique_Mercator_Two_Point_Natural_Origin', 'Hotine_Oblique_Mercator_Azimuth_Center', 'Oblique_Mercator', 'omerc']
        }, $x = {
            init: function () {
                var x, e, a, d, f, t;
                this.lat2 || (this.lat2 = this.lat1), this.k0 || (this.k0 = 1), this.x0 = this.x0 || 0, this.y0 = this.y0 || 0, Math.abs(this.lat1 + this.lat2) < 1e-10 || (f = this.b / this.a, this.e = Math.sqrt(1 - f * f), x = Math.sin(this.lat1), d = Math.cos(this.lat1), e = R(this.e, x, d), a = Y(this.e, this.lat1, x), t = Math.sin(this.lat2), f = Math.cos(this.lat2), d = R(this.e, t, f), f = Y(this.e, this.lat2, t), t = Y(this.e, this.lat0, Math.sin(this.lat0)), 1e-10 < Math.abs(this.lat1 - this.lat2) ? this.ns = Math.log(e / d) / Math.log(a / f) : this.ns = x, isNaN(this.ns) && (this.ns = x), this.f0 = e / (this.ns * Math.pow(a, this.ns)), this.rh = this.a * this.f0 * Math.pow(t, this.ns), this.title || (this.title = 'Lambert Conformal Conic'))
            },
            forward: function (x) {
                var e = x.x, a = x.y;
                Math.abs(2 * Math.abs(a) - Math.PI) <= 1e-10 && (a = w(a) * (b - 2e-10));
                var d, f = Math.abs(Math.abs(a) - b);
                if (1e-10 < f) d = Y(this.e, a, Math.sin(a)), d = this.a * this.f0 * Math.pow(d, this.ns); else {
                    if (a * this.ns <= 0) return null;
                    d = 0
                }
                e = this.ns * U(e - this.long0);
                return x.x = this.k0 * (d * Math.sin(e)) + this.x0, x.y = this.k0 * (this.rh - d * Math.cos(e)) + this.y0, x
            },
            inverse: function (x) {
                var e, a, d = (x.x - this.x0) / this.k0, f = this.rh - (x.y - this.y0) / this.k0,
                    t = 0 < this.ns ? (e = Math.sqrt(d * d + f * f), 1) : (e = -Math.sqrt(d * d + f * f), -1), _ = 0;
                if (0 !== e && (_ = Math.atan2(t * d, t * f)), 0 !== e || 0 < this.ns) {
                    if (t = 1 / this.ns, a = Math.pow(e / (this.a * this.f0), t), -9999 === (a = N(this.e, a))) return null
                } else a = -b;
                return _ = U(_ / this.ns + this.long0), x.x = _, x.y = a, x
            },
            names: ['Lambert Tangential Conformal Conic Projection', 'Lambert_Conformal_Conic', 'Lambert_Conformal_Conic_1SP', 'Lambert_Conformal_Conic_2SP', 'lcc']
        }, xe = {
            init: function () {
                this.a = 6377397.155, this.es = .006674372230614, this.e = Math.sqrt(this.es), this.lat0 || (this.lat0 = .863937979737193), this.long0 || (this.long0 = .4334234309119251), this.k0 || (this.k0 = .9999), this.s45 = .785398163397448, this.s90 = 2 * this.s45, this.fi0 = this.lat0, this.e2 = this.es, this.e = Math.sqrt(this.e2), this.alfa = Math.sqrt(1 + this.e2 * Math.pow(Math.cos(this.fi0), 4) / (1 - this.e2)), this.uq = 1.04216856380474, this.u0 = Math.asin(Math.sin(this.fi0) / this.alfa), this.g = Math.pow((1 + this.e * Math.sin(this.fi0)) / (1 - this.e * Math.sin(this.fi0)), this.alfa * this.e / 2), this.k = Math.tan(this.u0 / 2 + this.s45) / Math.pow(Math.tan(this.fi0 / 2 + this.s45), this.alfa) * this.g, this.k1 = this.k0, this.n0 = this.a * Math.sqrt(1 - this.e2) / (1 - this.e2 * Math.pow(Math.sin(this.fi0), 2)), this.s0 = 1.37008346281555, this.n = Math.sin(this.s0), this.ro0 = this.k1 * this.n0 / Math.tan(this.s0), this.ad = this.s90 - this.uq
            }, forward: function (x) {
                var e = x.x, a = x.y, d = U(e - this.long0),
                    e = Math.pow((1 + this.e * Math.sin(a)) / (1 - this.e * Math.sin(a)), this.alfa * this.e / 2),
                    a = 2 * (Math.atan(this.k * Math.pow(Math.tan(a / 2 + this.s45), this.alfa) / e) - this.s45),
                    e = -d * this.alfa,
                    d = Math.asin(Math.cos(this.ad) * Math.sin(a) + Math.sin(this.ad) * Math.cos(a) * Math.cos(e)),
                    e = Math.asin(Math.cos(a) * Math.sin(e) / Math.cos(d)), e = this.n * e,
                    d = this.ro0 * Math.pow(Math.tan(this.s0 / 2 + this.s45), this.n) / Math.pow(Math.tan(d / 2 + this.s45), this.n);
                return x.y = d * Math.cos(e), x.x = d * Math.sin(e), this.czech || (x.y *= -1, x.x *= -1), x
            }, inverse: function (x) {
                var e, a, d = x.x;
                x.x = x.y, x.y = d, this.czech || (x.y *= -1, x.x *= -1), a = Math.sqrt(x.x * x.x + x.y * x.y), d = Math.atan2(x.y, x.x) / Math.sin(this.s0), a = 2 * (Math.atan(Math.pow(this.ro0 / a, 1 / this.n) * Math.tan(this.s0 / 2 + this.s45)) - this.s45), e = Math.asin(Math.cos(this.ad) * Math.sin(a) - Math.sin(this.ad) * Math.cos(a) * Math.cos(d)), d = Math.asin(Math.cos(a) * Math.sin(d) / Math.cos(e)), x.x = this.long0 - d / this.alfa;
                for (var f = e, t = 0, _ = 0; x.y = 2 * (Math.atan(Math.pow(this.k, -1 / this.alfa) * Math.pow(Math.tan(e / 2 + this.s45), 1 / this.alfa) * Math.pow((1 + this.e * Math.sin(f)) / (1 - this.e * Math.sin(f)), this.e / 2)) - this.s45), Math.abs(f - x.y) < 1e-10 && (t = 1), f = x.y, _ += 1, 0 === t && _ < 15;) ;
                return 15 <= _ ? null : x
            }, names: ['Krovak', 'krovak']
        }, ee = {
            init: function () {
                this.sphere || (this.e0 = zx(this.es), this.e1 = Jx(this.es), this.e2 = Tx(this.es), this.e3 = Ux(this.es), this.ml0 = this.a * Fx(this.e0, this.e1, this.e2, this.e3, this.lat0))
            }, forward: function (x) {
                var e, a, d, f, t, _, i = x.x, s = x.y, i = U(i - this.long0);
                return _ = this.sphere ? (t = this.a * Math.asin(Math.cos(s) * Math.sin(i)), this.a * (Math.atan2(Math.tan(s), Math.cos(i)) - this.lat0)) : (e = Math.sin(s), a = Math.cos(s), d = Qx(this.a, this.e, e), f = Math.tan(s) * Math.tan(s), t = d * (i = i * Math.cos(s)) * (1 - (_ = i * i) * f * (1 / 6 - (8 - f + 8 * (i = this.es * a * a / (1 - this.es))) * _ / 120)), this.a * Fx(this.e0, this.e1, this.e2, this.e3, s) - this.ml0 + d * e / a * _ * (.5 + (5 - f + 6 * i) * _ / 24)), x.x = t + this.x0, x.y = _ + this.y0, x
            }, inverse: function (x) {
                x.x -= this.x0, x.y -= this.y0;
                var e = x.x / this.a, a = x.y / this.a;
                if (this.sphere) var d = a + this.lat0, f = Math.asin(Math.sin(d) * Math.cos(e)),
                    t = Math.atan2(Math.tan(e), Math.cos(d)); else {
                    var _ = this.ml0 / this.a + a, i = Ix(_, this.e0, this.e1, this.e2, this.e3);
                    if (Math.abs(Math.abs(i) - b) <= 1e-10) return x.x = this.long0, x.y = b, a < 0 && (x.y *= -1), x;
                    var s = Qx(this.a, this.e, Math.sin(i)), d = s * s * s / this.a / this.a * (1 - this.es),
                        _ = Math.pow(Math.tan(i), 2), a = e * this.a / s, e = a * a;
                    f = i - s * Math.tan(i) / d * a * a * (.5 - (1 + 3 * _) * a * a / 24), t = a * (1 - e * (_ / 3 + (1 + 3 * _) * _ * e / 15)) / Math.cos(i)
                }
                return x.x = U(t + this.long0), x.y = Lx(f), x
            }, names: ['Cassini', 'Cassini_Soldner', 'cass']
        }, ae = {
            init: function () {
                var x, e, a, d = Math.abs(this.lat0);
                if (Math.abs(d - b) < 1e-10 ? this.mode = this.lat0 < 0 ? this.S_POLE : this.N_POLE : Math.abs(d) < 1e-10 ? this.mode = this.EQUIT : this.mode = this.OBLIQ, 0 < this.es) switch (this.qp = Ax(this.e, 1), this.mmf = .5 / (1 - this.es), this.apa = (e = this.es, a = [], a[0] = .3333333333333333 * e, d = e * e, a[0] += .17222222222222222 * d, a[1] = .06388888888888888 * d, d *= e, a[0] += .10257936507936508 * d, a[1] += .0664021164021164 * d, a[2] = .016415012942191543 * d, a), this.mode) {
                    case this.N_POLE:
                    case this.S_POLE:
                        this.dd = 1;
                        break;
                    case this.EQUIT:
                        this.rq = Math.sqrt(.5 * this.qp), this.dd = 1 / this.rq, this.xmf = 1, this.ymf = .5 * this.qp;
                        break;
                    case this.OBLIQ:
                        this.rq = Math.sqrt(.5 * this.qp), x = Math.sin(this.lat0), this.sinb1 = Ax(this.e, x) / this.qp, this.cosb1 = Math.sqrt(1 - this.sinb1 * this.sinb1), this.dd = Math.cos(this.lat0) / (Math.sqrt(1 - this.es * x * x) * this.rq * this.cosb1), this.ymf = (this.xmf = this.rq) / this.dd, this.xmf *= this.dd
                } else this.mode === this.OBLIQ && (this.sinph0 = Math.sin(this.lat0), this.cosph0 = Math.cos(this.lat0))
            },
            forward: function (x) {
                var e, a, d, f, t, _, i, s, n, h, r = x.x, c = x.y, r = U(r - this.long0);
                if (this.sphere) {
                    if (t = Math.sin(c), h = Math.cos(c), d = Math.cos(r), this.mode === this.OBLIQ || this.mode === this.EQUIT) {
                        if ((a = this.mode === this.EQUIT ? 1 + h * d : 1 + this.sinph0 * t + this.cosph0 * h * d) <= 1e-10) return null;
                        e = (a = Math.sqrt(2 / a)) * h * Math.sin(r), a *= this.mode === this.EQUIT ? t : this.cosph0 * t - this.sinph0 * h * d
                    } else if (this.mode === this.N_POLE || this.mode === this.S_POLE) {
                        if (this.mode === this.N_POLE && (d = -d), Math.abs(c + this.lat0) < 1e-10) return null;
                        a = p - .5 * c, e = (a = 2 * (this.mode === this.S_POLE ? Math.cos(a) : Math.sin(a))) * Math.sin(r), a *= d
                    }
                } else {
                    switch (n = s = i = 0, d = Math.cos(r), f = Math.sin(r), t = Math.sin(c), _ = Ax(this.e, t), this.mode !== this.OBLIQ && this.mode !== this.EQUIT || (i = _ / this.qp, s = Math.sqrt(1 - i * i)), this.mode) {
                        case this.OBLIQ:
                            n = 1 + this.sinb1 * i + this.cosb1 * s * d;
                            break;
                        case this.EQUIT:
                            n = 1 + s * d;
                            break;
                        case this.N_POLE:
                            n = b + c, _ = this.qp - _;
                            break;
                        case this.S_POLE:
                            n = c - b, _ = this.qp + _
                    }
                    if (Math.abs(n) < 1e-10) return null;
                    switch (this.mode) {
                        case this.OBLIQ:
                        case this.EQUIT:
                            n = Math.sqrt(2 / n), a = this.mode === this.OBLIQ ? this.ymf * n * (this.cosb1 * i - this.sinb1 * s * d) : (n = Math.sqrt(2 / (1 + s * d))) * i * this.ymf, e = this.xmf * n * s * f;
                            break;
                        case this.N_POLE:
                        case this.S_POLE:
                            0 <= _ ? (e = (n = Math.sqrt(_)) * f, a = d * (this.mode === this.S_POLE ? n : -n)) : e = a = 0
                    }
                }
                return x.x = this.a * e + this.x0, x.y = this.a * a + this.y0, x
            },
            inverse: function (x) {
                x.x -= this.x0, x.y -= this.y0;
                var e, a, d, f, t, _, i, s = x.x / this.a, n = x.y / this.a;
                if (this.sphere) {
                    var h, r = 0, c = 0;
                    if (1 < (a = .5 * (h = Math.sqrt(s * s + n * n)))) return null;
                    switch (a = 2 * Math.asin(a), this.mode !== this.OBLIQ && this.mode !== this.EQUIT || (c = Math.sin(a), r = Math.cos(a)), this.mode) {
                        case this.EQUIT:
                            a = Math.abs(h) <= 1e-10 ? 0 : Math.asin(n * c / h), s *= c, n = r * h;
                            break;
                        case this.OBLIQ:
                            a = Math.abs(h) <= 1e-10 ? this.lat0 : Math.asin(r * this.sinph0 + n * c * this.cosph0 / h), s *= c * this.cosph0, n = (r - Math.sin(a) * this.sinph0) * h;
                            break;
                        case this.N_POLE:
                            n = -n, a = b - a;
                            break;
                        case this.S_POLE:
                            a -= b
                    }
                    e = 0 !== n || this.mode !== this.EQUIT && this.mode !== this.OBLIQ ? Math.atan2(s, n) : 0
                } else {
                    if (i = 0, this.mode === this.OBLIQ || this.mode === this.EQUIT) {
                        if (s /= this.dd, n *= this.dd, (t = Math.sqrt(s * s + n * n)) < 1e-10) return x.x = this.long0, x.y = this.lat0, x;
                        f = 2 * Math.asin(.5 * t / this.rq), d = Math.cos(f), s *= f = Math.sin(f), n = this.mode === this.OBLIQ ? (i = d * this.sinb1 + n * f * this.cosb1 / t, _ = this.qp * i, t * this.cosb1 * d - n * this.sinb1 * f) : (i = n * f / t, _ = this.qp * i, t * d)
                    } else if (this.mode === this.N_POLE || this.mode === this.S_POLE) {
                        if (!(_ = s * s + (n = this.mode === this.N_POLE ? -n : n) * n)) return x.x = this.long0, x.y = this.lat0, x;
                        i = 1 - _ / this.qp, this.mode === this.S_POLE && (i = -i)
                    }
                    e = Math.atan2(s, n), i = (_ = Math.asin(i)) + _, a = _ + (_ = this.apa)[0] * Math.sin(i) + _[1] * Math.sin(i + i) + _[2] * Math.sin(i + i + i)
                }
                return x.x = U(this.long0 + e), x.y = a, x
            },
            names: ['Lambert Azimuthal Equal Area', 'Lambert_Azimuthal_Equal_Area', 'laea'],
            S_POLE: 1,
            N_POLE: 2,
            EQUIT: 3,
            OBLIQ: 4
        }, de = {
            init: function () {
                Math.abs(this.lat1 + this.lat2) < 1e-10 || (this.temp = this.b / this.a, this.es = 1 - Math.pow(this.temp, 2), this.e3 = Math.sqrt(this.es), this.sin_po = Math.sin(this.lat1), this.cos_po = Math.cos(this.lat1), this.t1 = this.sin_po, this.con = this.sin_po, this.ms1 = R(this.e3, this.sin_po, this.cos_po), this.qs1 = Ax(this.e3, this.sin_po, this.cos_po), this.sin_po = Math.sin(this.lat2), this.cos_po = Math.cos(this.lat2), this.t2 = this.sin_po, this.ms2 = R(this.e3, this.sin_po, this.cos_po), this.qs2 = Ax(this.e3, this.sin_po, this.cos_po), this.sin_po = Math.sin(this.lat0), this.cos_po = Math.cos(this.lat0), this.t3 = this.sin_po, this.qs0 = Ax(this.e3, this.sin_po, this.cos_po), 1e-10 < Math.abs(this.lat1 - this.lat2) ? this.ns0 = (this.ms1 * this.ms1 - this.ms2 * this.ms2) / (this.qs2 - this.qs1) : this.ns0 = this.con, this.c = this.ms1 * this.ms1 + this.ns0 * this.qs1, this.rh = this.a * Math.sqrt(this.c - this.ns0 * this.qs0) / this.ns0)
            }, forward: function (x) {
                var e = x.x, a = x.y;
                this.sin_phi = Math.sin(a), this.cos_phi = Math.cos(a);
                var d = Ax(this.e3, this.sin_phi, this.cos_phi),
                    a = this.a * Math.sqrt(this.c - this.ns0 * d) / this.ns0, d = this.ns0 * U(e - this.long0),
                    e = a * Math.sin(d) + this.x0, d = this.rh - a * Math.cos(d) + this.y0;
                return x.x = e, x.y = d, x
            }, inverse: function (x) {
                var e, a, d, f;
                return x.x -= this.x0, x.y = this.rh - x.y + this.y0, a = 0 <= this.ns0 ? (e = Math.sqrt(x.x * x.x + x.y * x.y), 1) : (e = -Math.sqrt(x.x * x.x + x.y * x.y), -1), (d = 0) !== e && (d = Math.atan2(a * x.x, a * x.y)), a = e * this.ns0 / this.a, f = this.sphere ? Math.asin((this.c - a * a) / (2 * this.ns0)) : (f = (this.c - a * a) / this.ns0, this.phi1z(this.e3, f)), d = U(d / this.ns0 + this.long0), x.x = d, x.y = f, x
            }, names: ['Albers_Conic_Equal_Area', 'Albers', 'aea'], phi1z: function (x, e) {
                var a, d, f, t = Ex(.5 * e);
                if (x < 1e-10) return t;
                for (var _ = x * x, i = 1; i <= 25; i++) if (t += f = .5 * (d = 1 - (f = x * (a = Math.sin(t))) * f) * d / Math.cos(t) * (e / (1 - _) - a / d + .5 / x * Math.log((1 - f) / (1 + f))), Math.abs(f) <= 1e-7) return t;
                return null
            }
        }, fe = {
            init: function () {
                this.sin_p14 = Math.sin(this.lat0), this.cos_p14 = Math.cos(this.lat0), this.infinity_dist = 1e3 * this.a, this.rc = 1
            }, forward: function (x) {
                var e, a = x.x, d = x.y, f = U(a - this.long0), t = Math.sin(d), _ = Math.cos(d), a = Math.cos(f),
                    a = 0 < (d = this.sin_p14 * t + this.cos_p14 * _ * a) || Math.abs(d) <= 1e-10 ? (e = this.x0 + +this.a * _ * Math.sin(f) / d, this.y0 + +this.a * (this.cos_p14 * t - this.sin_p14 * _ * a) / d) : (e = this.x0 + this.infinity_dist * _ * Math.sin(f), this.y0 + this.infinity_dist * (this.cos_p14 * t - this.sin_p14 * _ * a));
                return x.x = e, x.y = a, x
            }, inverse: function (x) {
                var e, a, d, f;
                return x.x = (x.x - this.x0) / this.a, x.y = (x.y - this.y0) / this.a, x.x /= this.k0, x.y /= this.k0, d = (e = Math.sqrt(x.x * x.x + x.y * x.y)) ? (f = Math.atan2(e, this.rc), d = Math.sin(f), a = Math.cos(f), f = Ex(a * this.sin_p14 + x.y * d * this.cos_p14 / e), d = Math.atan2(x.x * d, e * this.cos_p14 * a - x.y * this.sin_p14 * d), U(this.long0 + d)) : (f = this.phic0, 0), x.x = d, x.y = f, x
            }, names: ['gnom']
        }, te = {
            init: function () {
                this.sphere || (this.k0 = R(this.e, Math.sin(this.lat_ts), Math.cos(this.lat_ts)))
            }, forward: function (x) {
                var e, a, d = x.x, f = x.y, d = U(d - this.long0);
                return a = this.sphere ? (e = this.x0 + this.a * d * Math.cos(this.lat_ts), this.y0 + this.a * Math.sin(f) / Math.cos(this.lat_ts)) : (a = Ax(this.e, Math.sin(f)), e = this.x0 + this.a * this.k0 * d, this.y0 + this.a * a * .5 / this.k0), x.x = e, x.y = a, x
            }, inverse: function (x) {
                var e, a;
                return x.x -= this.x0, x.y -= this.y0, this.sphere ? (e = U(this.long0 + x.x / this.a / Math.cos(this.lat_ts)), a = Math.asin(x.y / this.a * Math.cos(this.lat_ts))) : (a = function (x, e) {
                    var a = 1 - (1 - x * x) / (2 * x) * Math.log((1 - x) / (1 + x));
                    if (Math.abs(Math.abs(e) - a) < 1e-6) return e < 0 ? -1 * b : b;
                    for (var d, f, t, _ = Math.asin(.5 * e), i = 0; i < 30; i++) if (d = Math.sin(_), f = Math.cos(_), t = x * d, _ += t = Math.pow(1 - t * t, 2) / (2 * f) * (e / (1 - x * x) - d / (1 - t * t) + .5 / x * Math.log((1 - t) / (1 + t))), Math.abs(t) <= 1e-10) return _;
                    return NaN
                }(this.e, 2 * x.y * this.k0 / this.a), e = U(this.long0 + x.x / (this.a * this.k0))), x.x = e, x.y = a, x
            }, names: ['cea']
        }, _e = {
            init: function () {
                this.x0 = this.x0 || 0, this.y0 = this.y0 || 0, this.lat0 = this.lat0 || 0, this.long0 = this.long0 || 0, this.lat_ts = this.lat_ts || 0, this.title = this.title || 'Equidistant Cylindrical (Plate Carre)', this.rc = Math.cos(this.lat_ts)
            }, forward: function (x) {
                var e = x.x, a = x.y, e = U(e - this.long0), a = Lx(a - this.lat0);
                return x.x = this.x0 + this.a * e * this.rc, x.y = this.y0 + this.a * a, x
            }, inverse: function (x) {
                var e = x.x, a = x.y;
                return x.x = U(this.long0 + (e - this.x0) / (this.a * this.rc)), x.y = Lx(this.lat0 + (a - this.y0) / this.a), x
            }, names: ['Equirectangular', 'Equidistant_Cylindrical', 'eqc']
        }, ie = {
            init: function () {
                this.temp = this.b / this.a, this.es = 1 - Math.pow(this.temp, 2), this.e = Math.sqrt(this.es), this.e0 = zx(this.es), this.e1 = Jx(this.es), this.e2 = Tx(this.es), this.e3 = Ux(this.es), this.ml0 = this.a * Fx(this.e0, this.e1, this.e2, this.e3, this.lat0)
            }, forward: function (x) {
                var e, a = x.x, d = x.y, f = U(a - this.long0), a = f * Math.sin(d);
                return a = this.sphere ? Math.abs(d) <= 1e-10 ? (e = this.a * f, -1 * this.a * this.lat0) : (e = this.a * Math.sin(a) / Math.tan(d), this.a * (Lx(d - this.lat0) + (1 - Math.cos(a)) / Math.tan(d))) : Math.abs(d) <= 1e-10 ? (e = this.a * f, -1 * this.ml0) : (e = (f = Qx(this.a, this.e, Math.sin(d)) / Math.tan(d)) * Math.sin(a), this.a * Fx(this.e0, this.e1, this.e2, this.e3, d) - this.ml0 + f * (1 - Math.cos(a))), x.x = e + this.x0, x.y = a + this.y0, x
            }, inverse: function (x) {
                var e, a, d, f, t, _, i = x.x - this.x0, s = x.y - this.y0;
                if (this.sphere) if (Math.abs(s + this.a * this.lat0) <= 1e-10) e = U(i / this.a + this.long0), a = 0; else {
                    for (var n, h = this.lat0 + s / this.a, r = i * i / this.a / this.a + h * h, c = h, o = 20; o; --o) if (c += d = -1 * (h * (c * (n = Math.tan(c)) + 1) - c - .5 * (c * c + r) * n) / ((c - h) / n - 1), Math.abs(d) <= 1e-10) {
                        a = c;
                        break
                    }
                    e = U(this.long0 + Math.asin(i * Math.tan(c) / this.a) / Math.sin(a))
                } else if (Math.abs(s + this.ml0) <= 1e-10) a = 0, e = U(this.long0 + i / this.a); else {
                    for (h = (this.ml0 + s) / this.a, r = i * i / this.a / this.a + h * h, c = h, o = 20; o; --o) if (_ = this.e * Math.sin(c), f = Math.sqrt(1 - _ * _) * Math.tan(c), t = this.a * Fx(this.e0, this.e1, this.e2, this.e3, c), _ = this.e0 - 2 * this.e1 * Math.cos(2 * c) + 4 * this.e2 * Math.cos(4 * c) - 6 * this.e3 * Math.cos(6 * c), c -= d = (h * (f * (t = t / this.a) + 1) - t - .5 * f * (t * t + r)) / (this.es * Math.sin(2 * c) * (t * t + r - 2 * h * t) / (4 * f) + (h - t) * (f * _ - 2 / Math.sin(2 * c)) - _), Math.abs(d) <= 1e-10) {
                        a = c;
                        break
                    }
                    f = Math.sqrt(1 - this.es * Math.pow(Math.sin(a), 2)) * Math.tan(a), e = U(this.long0 + Math.asin(i * f / this.a) / Math.sin(a))
                }
                return x.x = e, x.y = a, x
            }, names: ['Polyconic', 'poly']
        }, se = {
            init: function () {
                this.A = [], this.A[1] = .6399175073, this.A[2] = -.1358797613, this.A[3] = .063294409, this.A[4] = -.02526853, this.A[5] = .0117879, this.A[6] = -.0055161, this.A[7] = .0026906, this.A[8] = -.001333, this.A[9] = 67e-5, this.A[10] = -34e-5, this.B_re = [], this.B_im = [], this.B_re[1] = .7557853228, this.B_im[1] = 0, this.B_re[2] = .249204646, this.B_im[2] = .003371507, this.B_re[3] = -.001541739, this.B_im[3] = .04105856, this.B_re[4] = -.10162907, this.B_im[4] = .01727609, this.B_re[5] = -.26623489, this.B_im[5] = -.36249218, this.B_re[6] = -.6870983, this.B_im[6] = -1.1651967, this.C_re = [], this.C_im = [], this.C_re[1] = 1.3231270439, this.C_im[1] = 0, this.C_re[2] = -.577245789, this.C_im[2] = -.007809598, this.C_re[3] = .508307513, this.C_im[3] = -.112208952, this.C_re[4] = -.15094762, this.C_im[4] = .18200602, this.C_re[5] = 1.01418179, this.C_im[5] = 1.64497696, this.C_re[6] = 1.9660549, this.C_im[6] = 2.5127645, this.D = [], this.D[1] = 1.5627014243, this.D[2] = .5185406398, this.D[3] = -.03333098, this.D[4] = -.1052906, this.D[5] = -.0368594, this.D[6] = .007317, this.D[7] = .0122, this.D[8] = .00394, this.D[9] = -.0013
            }, forward: function (x) {
                for (var e = x.x, a = x.y - this.lat0, e = e - this.long0, d = a / V * 1e-5, e = e, f = 1, t = 0, _ = 1; _ <= 10; _++) f *= d, t += this.A[_] * f;
                var i, s = t, n = e, h = 1, r = 0, c = 0, o = 0;
                for (_ = 1; _ <= 6; _++) i = r * s + h * n, h = h * s - r * n, r = i, c = c + this.B_re[_] * h - this.B_im[_] * r, o = o + this.B_im[_] * h + this.B_re[_] * r;
                return x.x = o * this.a + this.x0, x.y = c * this.a + this.y0, x
            }, inverse: function (x) {
                var e, a = x.x, d = x.y, a = a - this.x0, f = (d - this.y0) / this.a, t = a / this.a, _ = 1, i = 0,
                    s = 0, n = 0;
                for (b = 1; b <= 6; b++) e = i * f + _ * t, _ = _ * f - i * t, i = e, s = s + this.C_re[b] * _ - this.C_im[b] * i, n = n + this.C_im[b] * _ + this.C_re[b] * i;
                for (var h = 0; h < this.iterations; h++) {
                    for (var r, c = s, o = n, l = f, u = t, b = 2; b <= 6; b++) r = o * s + c * n, c = c * s - o * n, o = r, l += (b - 1) * (this.B_re[b] * c - this.B_im[b] * o), u += (b - 1) * (this.B_im[b] * c + this.B_re[b] * o);
                    var c = 1, o = 0, m = this.B_re[1], p = this.B_im[1];
                    for (b = 2; b <= 6; b++) r = o * s + c * n, c = c * s - o * n, o = r, m += b * (this.B_re[b] * c - this.B_im[b] * o), p += b * (this.B_im[b] * c + this.B_re[b] * o);
                    var y = m * m + p * p, s = (l * m + u * p) / y, n = (u * m - l * p) / y
                }
                var v = s, d = n, Z = 1, M = 0;
                for (b = 1; b <= 9; b++) Z *= v, M += this.D[b] * Z;
                a = this.lat0 + M * V * 1e5, d = this.long0 + d;
                return x.x = d, x.y = a, x
            }, names: ['New_Zealand_Map_Grid', 'nzmg']
        }, ne = {
            init: function () {
            }, forward: function (x) {
                var e = x.x, a = x.y, e = U(e - this.long0), e = this.x0 + this.a * e,
                    a = this.y0 + this.a * Math.log(Math.tan(Math.PI / 4 + a / 2.5)) * 1.25;
                return x.x = e, x.y = a, x
            }, inverse: function (x) {
                x.x -= this.x0, x.y -= this.y0;
                var e = U(this.long0 + x.x / this.a), a = 2.5 * (Math.atan(Math.exp(.8 * x.y / this.a)) - Math.PI / 4);
                return x.x = e, x.y = a, x
            }, names: ['Miller_Cylindrical', 'mill']
        }, he = {
            init: function () {
                this.sphere ? (this.n = 1, this.m = 0, this.es = 0, this.C_y = Math.sqrt((this.m + 1) / this.n), this.C_x = this.C_y / (this.m + 1)) : this.en = Wx(this.es)
            }, forward: function (x) {
                var e = x.x, a = x.y, e = U(e - this.long0);
                if (this.sphere) {
                    if (this.m) for (var d = this.n * Math.sin(a), f = 20; f; --f) {
                        var t = (this.m * a + Math.sin(a) - d) / (this.m + Math.cos(a));
                        if (a -= t, Math.abs(t) < 1e-10) break
                    } else a = 1 !== this.n ? Math.asin(this.n * Math.sin(a)) : a;
                    n = this.a * this.C_x * e * (this.m + Math.cos(a)), s = this.a * this.C_y * a
                } else var _ = Math.sin(a), i = Math.cos(a), s = this.a * Xx(a, _, i, this.en),
                    n = this.a * e * i / Math.sqrt(1 - this.es * _ * _);
                return x.x = n, x.y = s, x
            }, inverse: function (x) {
                var e, a, d, f;
                return x.x -= this.x0, d = x.x / this.a, x.y -= this.y0, e = x.y / this.a, this.sphere ? (e /= this.C_y, d /= this.C_x * (this.m + Math.cos(e)), this.m ? e = Ex((this.m * e + Math.sin(e)) / this.n) : 1 !== this.n && (e = Ex(Math.sin(e) / this.n)), d = U(d + this.long0), e = Lx(e)) : (e = gx(x.y / this.a, this.es, this.en), (f = Math.abs(e)) < b ? (f = Math.sin(e), a = this.long0 + x.x * Math.sqrt(1 - this.es * f * f) / (this.a * Math.cos(e)), d = U(a)) : f - 1e-10 < b && (d = this.long0)), x.x = d, x.y = e, x
            }, names: ['Sinusoidal', 'sinu']
        }, re = {
            init: function () {
            }, forward: function (x) {
                for (var e = x.x, a = x.y, e = U(e - this.long0), d = a, f = Math.PI * Math.sin(a); ;) {
                    var t = -(d + Math.sin(d) - f) / (1 + Math.cos(d));
                    if (d += t, Math.abs(t) < 1e-10) break
                }
                d /= 2, Math.PI / 2 - Math.abs(a) < 1e-10 && (e = 0);
                a = .900316316158 * this.a * e * Math.cos(d) + this.x0, e = 1.4142135623731 * this.a * Math.sin(d) + this.y0;
                return x.x = a, x.y = e, x
            }, inverse: function (x) {
                x.x -= this.x0, x.y -= this.y0, d = x.y / (1.4142135623731 * this.a), .999999999999 < Math.abs(d) && (d = .999999999999);
                var e = Math.asin(d), a = U(this.long0 + x.x / (.900316316158 * this.a * Math.cos(e)));
                (a = a < -Math.PI ? -Math.PI : a) > Math.PI && (a = Math.PI), d = (2 * e + Math.sin(2 * e)) / Math.PI, 1 < Math.abs(d) && (d = 1);
                var d = Math.asin(d);
                return x.x = a, x.y = d, x
            }, names: ['Mollweide', 'moll']
        }, ce = {
            init: function () {
                Math.abs(this.lat1 + this.lat2) < 1e-10 || (this.lat2 = this.lat2 || this.lat1, this.temp = this.b / this.a, this.es = 1 - Math.pow(this.temp, 2), this.e = Math.sqrt(this.es), this.e0 = zx(this.es), this.e1 = Jx(this.es), this.e2 = Tx(this.es), this.e3 = Ux(this.es), this.sinphi = Math.sin(this.lat1), this.cosphi = Math.cos(this.lat1), this.ms1 = R(this.e, this.sinphi, this.cosphi), this.ml1 = Fx(this.e0, this.e1, this.e2, this.e3, this.lat1), Math.abs(this.lat1 - this.lat2) < 1e-10 ? this.ns = this.sinphi : (this.sinphi = Math.sin(this.lat2), this.cosphi = Math.cos(this.lat2), this.ms2 = R(this.e, this.sinphi, this.cosphi), this.ml2 = Fx(this.e0, this.e1, this.e2, this.e3, this.lat2), this.ns = (this.ms1 - this.ms2) / (this.ml2 - this.ml1)), this.g = this.ml1 + this.ms1 / this.ns, this.ml0 = Fx(this.e0, this.e1, this.e2, this.e3, this.lat0), this.rh = this.a * (this.g - this.ml0))
            }, forward: function (x) {
                var e = x.x, a = x.y;
                a = this.sphere ? this.a * (this.g - a) : (d = Fx(this.e0, this.e1, this.e2, this.e3, a), this.a * (this.g - d));
                var d = this.ns * U(e - this.long0), e = this.x0 + a * Math.sin(d),
                    d = this.y0 + this.rh - a * Math.cos(d);
                return x.x = e, x.y = d, x
            }, inverse: function (x) {
                x.x -= this.x0, x.y = this.rh - x.y + this.y0;
                var e = 0 <= this.ns ? (d = Math.sqrt(x.x * x.x + x.y * x.y), 1) : (d = -Math.sqrt(x.x * x.x + x.y * x.y), -1),
                    a = 0;
                if (0 !== d && (a = Math.atan2(e * x.x, e * x.y)), this.sphere) return t = U(this.long0 + a / this.ns), f = Lx(this.g - d / this.a), x.x = t, x.y = f, x;
                var d = this.g - d / this.a, f = Ix(d, this.e0, this.e1, this.e2, this.e3),
                    t = U(this.long0 + a / this.ns);
                return x.x = t, x.y = f, x
            }, names: ['Equidistant_Conic', 'eqdc']
        }, oe = {
            init: function () {
                this.R = this.a
            }, forward: function (x) {
                var e = x.x, a = x.y, d = U(e - this.long0);
                Math.abs(a) <= 1e-10 && (n = this.x0 + this.R * d, h = this.y0);
                var f = Ex(2 * Math.abs(a / Math.PI));
                (Math.abs(d) <= 1e-10 || Math.abs(Math.abs(a) - b) <= 1e-10) && (n = this.x0, h = 0 <= a ? this.y0 + Math.PI * this.R * Math.tan(.5 * f) : this.y0 + Math.PI * this.R * -Math.tan(.5 * f));
                var t = .5 * Math.abs(Math.PI / d - d / Math.PI), _ = t * t, i = Math.sin(f), s = Math.cos(f),
                    e = s / (i + s - 1), f = e * e, s = e * (2 / i - 1), i = s * s,
                    f = Math.PI * this.R * (t * (e - i) + Math.sqrt(_ * (e - i) * (e - i) - (i + _) * (f - i))) / (i + _);
                d < 0 && (f = -f);
                var n = this.x0 + f, e = _ + e,
                    f = Math.PI * this.R * (s * e - t * Math.sqrt((i + _) * (1 + _) - e * e)) / (i + _),
                    h = 0 <= a ? this.y0 + f : this.y0 - f;
                return x.x = n, x.y = h, x
            }, inverse: function (x) {
                var e, a, d, f, t, _, i;
                return x.x -= this.x0, x.y -= this.y0, i = Math.PI * this.R, d = (e = x.x / i) * e + (a = x.y / i) * a, i = 3 * (a * a / (t = -2 * (_ = -Math.abs(a) * (1 + d)) + 1 + 2 * a * a + d * d) + (2 * (f = _ - 2 * a * a + e * e) * f * f / t / t / t - 9 * _ * f / t / t) / 27) / (_ = (_ - f * f / 3 / t) / t) / (_ = 2 * Math.sqrt(-_ / 3)), 1 < Math.abs(i) && (i = 0 <= i ? 1 : -1), i = Math.acos(i) / 3, t = 0 <= x.y ? (-_ * Math.cos(i + Math.PI / 3) - f / 3 / t) * Math.PI : -(-_ * Math.cos(i + Math.PI / 3) - f / 3 / t) * Math.PI, e = Math.abs(e) < 1e-10 ? this.long0 : U(this.long0 + Math.PI * (d - 1 + Math.sqrt(1 + 2 * (e * e - a * a) + d * d)) / 2 / e), x.x = e, x.y = t, x
            }, names: ['Van_der_Grinten_I', 'VanDerGrinten', 'vandg']
        }, le = {
            init: function () {
                this.sin_p12 = Math.sin(this.lat0), this.cos_p12 = Math.cos(this.lat0)
            }, forward: function (x) {
                var e, a, d, f, t, _, i = x.x, s = x.y, n = Math.sin(x.y), h = Math.cos(x.y), r = U(i - this.long0);
                return this.sphere ? Math.abs(this.sin_p12 - 1) <= 1e-10 ? (x.x = this.x0 + this.a * (b - s) * Math.sin(r), x.y = this.y0 - this.a * (b - s) * Math.cos(r)) : Math.abs(this.sin_p12 + 1) <= 1e-10 ? (x.x = this.x0 + this.a * (b + s) * Math.sin(r), x.y = this.y0 + this.a * (b + s) * Math.cos(r)) : (t = this.sin_p12 * n + this.cos_p12 * h * Math.cos(r), f = (d = Math.acos(t)) ? d / Math.sin(d) : 1, x.x = this.x0 + this.a * f * h * Math.sin(r), x.y = this.y0 + this.a * f * (this.cos_p12 * n - this.sin_p12 * h * Math.cos(r))) : (e = zx(this.es), i = Jx(this.es), t = Tx(this.es), f = Ux(this.es), Math.abs(this.sin_p12 - 1) <= 1e-10 ? (a = this.a * Fx(e, i, t, f, b), _ = this.a * Fx(e, i, t, f, s), x.x = this.x0 + (a - _) * Math.sin(r), x.y = this.y0 - (a - _) * Math.cos(r)) : Math.abs(this.sin_p12 + 1) <= 1e-10 ? (a = this.a * Fx(e, i, t, f, b), _ = this.a * Fx(e, i, t, f, s), x.x = this.x0 + (a + _) * Math.sin(r), x.y = this.y0 + (a + _) * Math.cos(r)) : (s = n / h, a = Qx(this.a, this.e, this.sin_p12), _ = Qx(this.a, this.e, n), n = Math.atan((1 - this.es) * s + this.es * a * this.sin_p12 / (_ * h)), _ = 0 === (s = Math.atan2(Math.sin(r), this.cos_p12 * Math.tan(n) - this.sin_p12 * Math.cos(r))) ? Math.asin(this.cos_p12 * Math.sin(n) - this.sin_p12 * Math.cos(n)) : Math.abs(Math.abs(s) - Math.PI) <= 1e-10 ? -Math.asin(this.cos_p12 * Math.sin(n) - this.sin_p12 * Math.cos(n)) : Math.asin(Math.sin(r) * Math.cos(n) / Math.sin(s)), h = this.e * this.sin_p12 / Math.sqrt(1 - this.es), d = a * _ * (1 - (r = _ * _) * (a = (n = this.e * this.cos_p12 * Math.cos(s) / Math.sqrt(1 - this.es)) * n) * (1 - a) / 6 + (r = r * _) / 8 * (n = h * n) * (1 - 2 * a) + (r = r * _) / 120 * (a * (4 - 7 * a) - 3 * h * h * (1 - 7 * a)) - r * _ / 48 * n), x.x = this.x0 + d * Math.sin(s), x.y = this.y0 + d * Math.cos(s))), x
            }, inverse: function (x) {
                var e, a, d, f, t, _, i, s;
                return x.x -= this.x0, x.y -= this.y0, this.sphere ? (e = Math.sqrt(x.x * x.x + x.y * x.y)) > 2 * b * this.a ? void 0 : (i = e / this.a, s = Math.sin(i), _ = Math.cos(i), a = this.long0, Math.abs(e) <= 1e-10 ? d = this.lat0 : (d = Ex(_ * this.sin_p12 + x.y * s * this.cos_p12 / e), t = Math.abs(this.lat0) - b, a = Math.abs(t) <= 1e-10 ? 0 <= this.lat0 ? U(this.long0 + Math.atan2(x.x, -x.y)) : U(this.long0 - Math.atan2(-x.x, x.y)) : U(this.long0 + Math.atan2(x.x * s, e * this.cos_p12 * _ - x.y * this.sin_p12 * s))), x.x = a, x.y = d, x) : (i = zx(this.es), t = Jx(this.es), _ = Tx(this.es), s = Ux(this.es), Math.abs(this.sin_p12 - 1) <= 1e-10 ? (f = this.a * Fx(i, t, _, s, b), e = Math.sqrt(x.x * x.x + x.y * x.y), d = Ix((f - e) / this.a, i, t, _, s), a = U(this.long0 + Math.atan2(x.x, -1 * x.y))) : Math.abs(this.sin_p12 + 1) <= 1e-10 ? (f = this.a * Fx(i, t, _, s, b), e = Math.sqrt(x.x * x.x + x.y * x.y), d = Ix((e - f) / this.a, i, t, _, s), a = U(this.long0 + Math.atan2(x.x, x.y))) : (e = Math.sqrt(x.x * x.x + x.y * x.y), f = Math.atan2(x.x, x.y), i = Qx(this.a, this.e, this.sin_p12), t = Math.cos(f), s = -(_ = this.e * this.cos_p12 * t) * _ / (1 - this.es), _ = 3 * this.es * (1 - s) * this.sin_p12 * this.cos_p12 * t / (1 - this.es), i = 1 - s * (s = (i = e / i) - s * (1 + s) * Math.pow(i, 3) / 6 - _ * (1 + 3 * s) * Math.pow(i, 4) / 24) * s / 2 - i * s * s * s / 6, t = Math.asin(this.sin_p12 * Math.cos(s) + this.cos_p12 * Math.sin(s) * t), a = U(this.long0 + Math.asin(Math.sin(f) * Math.sin(s) / Math.cos(t))), s = Math.sin(t), d = Math.atan2((s - this.es * i * this.sin_p12) * Math.tan(t), s * (1 - this.es))), x.x = a, x.y = d, x)
            }, names: ['Azimuthal_Equidistant', 'aeqd']
        }, ue = {
            init: function () {
                this.sin_p14 = Math.sin(this.lat0), this.cos_p14 = Math.cos(this.lat0)
            }, forward: function (x) {
                var e, a, d = x.x, f = x.y, t = U(d - this.long0), _ = Math.sin(f), i = Math.cos(f), d = Math.cos(t);
                return (0 < (f = this.sin_p14 * _ + this.cos_p14 * i * d) || Math.abs(f) <= 1e-10) && (e = +this.a * i * Math.sin(t), a = this.y0 + +this.a * (this.cos_p14 * _ - this.sin_p14 * i * d)), x.x = e, x.y = a, x
            }, inverse: function (x) {
                var e, a, d, f, t, _;
                return x.x -= this.x0, x.y -= this.y0, e = Math.sqrt(x.x * x.x + x.y * x.y), f = Ex(e / this.a), a = Math.sin(f), d = Math.cos(f), t = this.long0, Math.abs(e) <= 1e-10 ? _ = this.lat0 : (_ = Ex(d * this.sin_p14 + x.y * a * this.cos_p14 / e), f = Math.abs(this.lat0) - b, t = Math.abs(f) <= 1e-10 ? 0 <= this.lat0 ? U(this.long0 + Math.atan2(x.x, -x.y)) : U(this.long0 - Math.atan2(-x.x, x.y)) : U(this.long0 + Math.atan2(x.x * a, e * this.cos_p14 * d - x.y * this.sin_p14 * a))), x.x = t, x.y = _, x
            }, names: ['ortho']
        };

        function be(x, e, a, d) {
            var f;
            return x < 1e-10 ? (d.value = 1, f = 0) : (f = Math.atan2(e, a), Math.abs(f) <= p ? d.value = 1 : p < f && f <= b + p ? (d.value = 2, f -= b) : b + p < f || f <= -(b + p) ? (d.value = 3, f = 0 <= f ? f - h : f + h) : (d.value = 4, f += b)), f
        }

        function me(x, e) {
            e = x + e;
            return e < -h ? e += y : +h < e && (e -= y), e
        }

        function pe(x, e) {
            return x[0] + e * (x[1] + e * (x[2] + e * x[3]))
        }

        var a = {
                init: function () {
                    this.x0 = this.x0 || 0, this.y0 = this.y0 || 0, this.lat0 = this.lat0 || 0, this.long0 = this.long0 || 0, this.lat_ts = this.lat_ts || 0, this.title = this.title || 'Quadrilateralized Spherical Cube', this.lat0 >= b - p / 2 ? this.face = 5 : this.lat0 <= -(b - p / 2) ? this.face = 6 : Math.abs(this.long0) <= p ? this.face = 1 : Math.abs(this.long0) <= b + p ? this.face = 0 < this.long0 ? 2 : 4 : this.face = 3, 0 !== this.es && (this.one_minus_f = 1 - (this.a - this.b) / this.a, this.one_minus_f_squared = this.one_minus_f * this.one_minus_f)
                }, forward: function (x) {
                    var e, a, d, f, t, _, i, s = {x: 0, y: 0}, n = {value: 0};
                    return x.x -= this.long0, i = 0 !== this.es ? Math.atan(this.one_minus_f_squared * Math.tan(x.y)) : x.y, e = x.x, 5 === this.face ? (a = b - i, d = p <= e && e <= b + p ? (n.value = 1, e - b) : b + p < e || e <= -(b + p) ? (n.value = 2, 0 < e ? e - h : e + h) : -(b + p) < e && e <= -p ? (n.value = 3, e + b) : (n.value = 4, e)) : 6 === this.face ? (a = b + i, d = p <= e && e <= b + p ? (n.value = 1, b - e) : e < p && -p <= e ? (n.value = 2, -e) : e < -p && -(b + p) <= e ? (n.value = 3, -e - b) : (n.value = 4, 0 < e ? h - e : -e - h)) : (2 === this.face ? e = me(e, +b) : 3 === this.face ? e = me(e, +h) : 4 === this.face && (e = me(e, -b)), t = Math.sin(i), _ = Math.cos(i), i = Math.sin(e), f = _ * Math.cos(e), i = _ * i, t = t, 1 === this.face ? d = be(a = Math.acos(f), t, i, n) : 2 === this.face ? d = be(a = Math.acos(i), t, -f, n) : 3 === this.face ? d = be(a = Math.acos(-f), t, -i, n) : 4 === this.face ? d = be(a = Math.acos(-i), t, f, n) : (a = d = 0, n.value = 1)), f = Math.atan(12 / h * (d + Math.acos(Math.sin(d) * Math.cos(p)) - b)), d = Math.sqrt((1 - Math.cos(a)) / (Math.cos(f) * Math.cos(f)) / (1 - Math.cos(Math.atan(1 / Math.cos(d))))), 2 === n.value ? f += b : 3 === n.value ? f += h : 4 === n.value && (f += 1.5 * h), s.x = d * Math.cos(f), s.y = d * Math.sin(f), s.x = s.x * this.a + this.x0, s.y = s.y * this.a + this.y0, x.x = s.x, x.y = s.y, x
                }, inverse: function (x) {
                    var e, a, d, f, t, _, i, s = {lam: 0, phi: 0}, n = {value: 0};
                    return x.x = (x.x - this.x0) / this.a, x.y = (x.y - this.y0) / this.a, a = Math.atan(Math.sqrt(x.x * x.x + x.y * x.y)), e = Math.atan2(x.y, x.x), 0 <= x.x && x.x >= Math.abs(x.y) ? n.value = 1 : 0 <= x.y && x.y >= Math.abs(x.x) ? (n.value = 2, e -= b) : x.x < 0 && -x.x >= Math.abs(x.y) ? (n.value = 3, e = e < 0 ? e + h : e - h) : (n.value = 4, e += b), _ = h / 12 * Math.tan(e), t = Math.sin(_) / (Math.cos(_) - 1 / Math.sqrt(2)), t = Math.atan(t), (a = 1 - (e = Math.cos(e)) * e * (a = Math.tan(a)) * a * (1 - Math.cos(Math.atan(1 / Math.cos(t))))) < -1 ? a = -1 : 1 < a && (a = 1), 5 === this.face ? (d = Math.acos(a), s.phi = b - d, 1 === n.value ? s.lam = t + b : 2 === n.value ? s.lam = t < 0 ? t + h : t - h : 3 === n.value ? s.lam = t - b : s.lam = t) : 6 === this.face ? (d = Math.acos(a), s.phi = d - b, 1 === n.value ? s.lam = b - t : 2 === n.value ? s.lam = -t : 3 === n.value ? s.lam = -t - b : s.lam = t < 0 ? -t - h : h - t) : (_ = (f = a) * f, i = 1 <= (_ += (t = 1 <= _ ? 0 : Math.sqrt(1 - _) * Math.sin(t)) * t) ? 0 : Math.sqrt(1 - _), 2 === n.value ? (_ = i, i = -t, t = _) : 3 === n.value ? (i = -i, t = -t) : 4 === n.value && (_ = i, i = t, t = -_), 2 === this.face ? (_ = f, f = -i, i = _) : 3 === this.face ? (f = -f, i = -i) : 4 === this.face && (_ = f, f = i, i = -_), s.phi = Math.acos(-t) - b, s.lam = Math.atan2(i, f), 2 === this.face ? s.lam = me(s.lam, -b) : 3 === this.face ? s.lam = me(s.lam, -h) : 4 === this.face && (s.lam = me(s.lam, +b))), 0 !== this.es && (i = s.phi < 0 ? 1 : 0, f = Math.tan(s.phi), f = this.b / Math.sqrt(f * f + this.one_minus_f_squared), s.phi = Math.atan(Math.sqrt(this.a * this.a - f * f) / (this.one_minus_f * f)), i && (s.phi = -s.phi)), s.lam += this.long0, x.x = s.lam, x.y = s.phi, x
                }, names: ['Quadrilateralized Spherical Cube', 'Quadrilateralized_Spherical_Cube', 'qsc']
            },
            ye = [[1, 22199e-21, -715515e-10, 31103e-10], [.9986, -482243e-9, -24897e-9, -13309e-10], [.9954, -83103e-8, -448605e-10, -9.86701e-7], [.99, -.00135364, -59661e-9, 36777e-10], [.9822, -.00167442, -449547e-11, -572411e-11], [.973, -.00214868, -903571e-10, 1.8736e-8], [.96, -.00305085, -900761e-10, 164917e-11], [.9427, -.00382792, -653386e-10, -26154e-10], [.9216, -.00467746, -10457e-8, 481243e-11], [.8962, -.00536223, -323831e-10, -543432e-11], [.8679, -.00609363, -113898e-9, 332484e-11], [.835, -.00698325, -640253e-10, 9.34959e-7], [.7986, -.00755338, -500009e-10, 9.35324e-7], [.7597, -.00798324, -35971e-9, -227626e-11], [.7186, -.00851367, -701149e-10, -86303e-10], [.6732, -.00986209, -199569e-9, 191974e-10], [.6213, -.010418, 883923e-10, 624051e-11], [.5722, -.00906601, 182e-6, 624051e-11], [.5322, -.00677797, 275608e-9, 624051e-11]],
            ve = [[-520417e-23, .0124, 121431e-23, -845284e-16], [.062, .0124, -1.26793e-9, 4.22642e-10], [.124, .0124, 5.07171e-9, -1.60604e-9], [.186, .0123999, -1.90189e-8, 6.00152e-9], [.248, .0124002, 7.10039e-8, -2.24e-8], [.31, .0123992, -2.64997e-7, 8.35986e-8], [.372, .0124029, 9.88983e-7, -3.11994e-7], [.434, .0123893, -369093e-11, -4.35621e-7], [.4958, .0123198, -102252e-10, -3.45523e-7], [.5571, .0121916, -154081e-10, -5.82288e-7], [.6176, .0119938, -241424e-10, -5.25327e-7], [.6769, .011713, -320223e-10, -5.16405e-7], [.7346, .0113541, -397684e-10, -6.09052e-7], [.7903, .0109107, -489042e-10, -104739e-11], [.8435, .0103431, -64615e-9, -1.40374e-9], [.8936, .00969686, -64636e-9, -8547e-9], [.9394, .00840947, -192841e-9, -42106e-10], [.9761, .00616527, -256e-6, -42106e-10], [1, .00328947, -319159e-9, -42106e-10]],
            Ze = o / 5, d = {
                init: function () {
                    this.x0 = this.x0 || 0, this.y0 = this.y0 || 0, this.long0 = this.long0 || 0, this.es = 0, this.title = this.title || 'Robinson'
                }, forward: function (x) {
                    var e = U(x.x - this.long0), a = Math.abs(x.y), d = Math.floor(a * Ze);
                    d < 0 ? d = 0 : 18 <= d && (d = 17);
                    a = {x: pe(ye[d], a = o * (a - .08726646259971647 * d)) * e, y: pe(ve[d], a)};
                    return x.y < 0 && (a.y = -a.y), a.x = a.x * this.a * .8487 + this.x0, a.y = a.y * this.a * 1.3523 + this.y0, a
                }, inverse: function (x) {
                    var d = {x: (x.x - this.x0) / (.8487 * this.a), y: Math.abs(x.y - this.y0) / (1.3523 * this.a)};
                    if (1 <= d.y) d.x /= ye[18][0], d.y = x.y < 0 ? -b : b; else {
                        var e = Math.floor(18 * d.y);
                        for (e < 0 ? e = 0 : 18 <= e && (e = 17); ;) if (ve[e][0] > d.y) --e; else {
                            if (!(ve[e + 1][0] <= d.y)) break;
                            ++e
                        }
                        var f = ve[e], t = 5 * (d.y - f[0]) / (ve[e + 1][0] - f[0]), t = function (x) {
                            for (var e = t; x; --x) {
                                var a = (pe(f, a = e) - d.y) / (f[1] + a * (2 * f[2] + 3 * a * f[3]));
                                if (e -= a, Math.abs(a) < 1e-10) break
                            }
                            return e
                        }(100);
                        d.x /= pe(ye[e], t), d.y = (5 * e + t) * m, x.y < 0 && (d.y = -d.y)
                    }
                    return d.x = U(d.x + this.long0), d
                }, names: ['Robinson', 'robin']
            }, Me = {
                init: function () {
                    this.name = 'geocent'
                }, forward: function (x) {
                    return xx(x, this.es, this.a)
                }, inverse: function (x) {
                    return ex(x, this.es, this.a, this.b)
                }, names: ['Geocentric', 'geocentric', 'geocent', 'Geocent']
            }, Ve = {
                h: {def: 1e5, num: !0},
                azi: {def: 0, num: !0, degrees: !0},
                tilt: {def: 0, num: !0, degrees: !0},
                long0: {def: 0, num: !0},
                lat0: {def: 0, num: !0}
            }, S = {
                init: function () {
                    if (Object.keys(Ve).forEach(function (x) {
                        if (void 0 === this[x]) this[x] = Ve[x].def; else {
                            if (Ve[x].num && isNaN(this[x])) throw new Error('Invalid parameter value, must be numeric ' + x + ' = ' + this[x]);
                            Ve[x].num && (this[x] = parseFloat(this[x]))
                        }
                        Ve[x].degrees && (this[x] = this[x] * m)
                    }.bind(this)), Math.abs(Math.abs(this.lat0) - b) < 1e-10 ? this.mode = this.lat0 < 0 ? 1 : 0 : Math.abs(this.lat0) < 1e-10 ? this.mode = 2 : (this.mode = 3, this.sinph0 = Math.sin(this.lat0), this.cosph0 = Math.cos(this.lat0)), this.pn1 = this.h / this.a, this.pn1 <= 0 || 1e10 < this.pn1) throw new Error('Invalid height');
                    this.p = 1 + this.pn1, this.rp = 1 / this.p, this.h1 = 1 / this.pn1, this.pfact = (this.p + 1) * this.h1, this.es = 0;
                    var x = this.tilt, e = this.azi;
                    this.cg = Math.cos(e), this.sg = Math.sin(e), this.cw = Math.cos(x), this.sw = Math.sin(x)
                }, forward: function (x) {
                    x.x -= this.long0;
                    var e, a, d, f, t = Math.sin(x.y), _ = Math.cos(x.y), i = Math.cos(x.x);
                    switch (this.mode) {
                        case 3:
                            a = this.sinph0 * t + this.cosph0 * _ * i;
                            break;
                        case 2:
                            a = _ * i;
                            break;
                        case 1:
                            a = -t;
                            break;
                        case 0:
                            a = t
                    }
                    switch (e = (a = this.pn1 / (this.p - a)) * _ * Math.sin(x.x), this.mode) {
                        case 3:
                            a *= this.cosph0 * t - this.sinph0 * _ * i;
                            break;
                        case 2:
                            a *= t;
                            break;
                        case 0:
                            a *= -_ * i;
                            break;
                        case 1:
                            a *= _ * i
                    }
                    return f = 1 / ((d = a * this.cg + e * this.sg) * this.sw * this.h1 + this.cw), e = (e * this.cg - a * this.sg) * this.cw * f, a = d * f, x.x = e * this.a, x.y = a * this.a, x
                }, inverse: function (x) {
                    x.x /= this.a, x.y /= this.a;
                    var e = {x: x.x, y: x.y}, a = 1 / (this.pn1 - x.y * this.sw), d = this.pn1 * x.x * a,
                        a = this.pn1 * x.y * this.cw * a;
                    x.x = d * this.cg + a * this.sg, x.y = a * this.cg - d * this.sg;
                    var f = wx(x.x, x.y);
                    if (Math.abs(f) < 1e-10) e.x = 0, e.y = x.y; else {
                        var t, _ = 1 - f * f * this.pfact;
                        switch (_ = (this.p - Math.sqrt(_)) / (this.pn1 / f + f / this.pn1), t = Math.sqrt(1 - _ * _), this.mode) {
                            case 3:
                                e.y = Math.asin(t * this.sinph0 + x.y * _ * this.cosph0 / f), x.y = (t - this.sinph0 * Math.sin(e.y)) * f, x.x *= _ * this.cosph0;
                                break;
                            case 2:
                                e.y = Math.asin(x.y * _ / f), x.y = t * f, x.x *= _;
                                break;
                            case 0:
                                e.y = Math.asin(t), x.y = -x.y;
                                break;
                            case 1:
                                e.y = -Math.asin(t)
                        }
                        e.x = Math.atan2(x.x, x.y)
                    }
                    return x.x = e.x + this.long0, x.y = e.y, x
                }, names: ['Tilted_Perspective', 'tpers']
            };
        ox.defaultDatum = 'WGS84', ox.Proj = $, ox.WGS84 = new ox.Proj('WGS84'), ox.Point = Sx, ox.toPoint = ix, ox.defs = k, ox.nadgrid = function (x, e) {
            var a, d = new DataView(e),
                f = 11 !== (a = d).getInt32(8, !1) && (11 !== a.getInt32(8, !0) && console.warn('Failed to detect nadgrid endian-ness, defaulting to little-endian'), !0),
                e = (e = f, {
                    nFields: (a = d).getInt32(8, e),
                    nSubgridFields: a.getInt32(24, e),
                    nSubgrids: a.getInt32(40, e),
                    shiftType: q(a, 56, 64).trim(),
                    fromSemiMajorAxis: a.getFloat64(120, e),
                    fromSemiMinorAxis: a.getFloat64(136, e),
                    toSemiMajorAxis: a.getFloat64(152, e),
                    toSemiMinorAxis: a.getFloat64(168, e)
                });
            1 < e.nSubgrids && console.log('Only single NTv2 subgrids are currently supported, subsequent sub grids are ignored');
            f = {
                header: e, subgrids: function (x, e, a) {
                    for (var d = [], f = 0; f < e.nSubgrids; f++) {
                        var t = (s = a, {
                                name: q(_ = x, (i = 176) + 8, i + 16).trim(),
                                parent: q(_, i + 24, i + 24 + 8).trim(),
                                lowerLatitude: _.getFloat64(i + 72, s),
                                upperLatitude: _.getFloat64(i + 88, s),
                                lowerLongitude: _.getFloat64(i + 104, s),
                                upperLongitude: _.getFloat64(i + 120, s),
                                latitudeInterval: _.getFloat64(i + 136, s),
                                longitudeInterval: _.getFloat64(i + 152, s),
                                gridNodeCount: _.getInt32(i + 168, s)
                            }), _ = function (x, e, a, d) {
                                for (var f = e + 176, t = [], _ = 0; _ < a.gridNodeCount; _++) {
                                    var i = {
                                        latitudeShift: x.getFloat32(f + 16 * _, d),
                                        longitudeShift: x.getFloat32(f + 16 * _ + 4, d),
                                        latitudeAccuracy: x.getFloat32(f + 16 * _ + 8, d),
                                        longitudeAccuracy: x.getFloat32(f + 16 * _ + 12, d)
                                    };
                                    t.push(i)
                                }
                                return t
                            }(x, 176, t, a),
                            i = Math.round(1 + (t.upperLongitude - t.lowerLongitude) / t.longitudeInterval),
                            s = Math.round(1 + (t.upperLatitude - t.lowerLatitude) / t.latitudeInterval);
                        d.push({
                            ll: [P(t.lowerLongitude), P(t.lowerLatitude)],
                            del: [P(t.longitudeInterval), P(t.latitudeInterval)],
                            lim: [i, s],
                            count: t.gridNodeCount,
                            cvs: _.map(function (x) {
                                return [P(x.longitudeShift), P(x.latitudeShift)]
                            })
                        })
                    }
                    return d
                }(d, e, f)
            };
            return O[x] = f
        }, ox.transform = nx, ox.mgrs = bx, ox.version = '__VERSION__', (bx = ox).Proj.projections.add(Hx), bx.Proj.projections.add(Cx), bx.Proj.projections.add(jx), bx.Proj.projections.add(Dx), bx.Proj.projections.add(Px), bx.Proj.projections.add(qx), bx.Proj.projections.add(Kx), bx.Proj.projections.add($x), bx.Proj.projections.add(xe), bx.Proj.projections.add(ee), bx.Proj.projections.add(ae), bx.Proj.projections.add(de), bx.Proj.projections.add(fe), bx.Proj.projections.add(te), bx.Proj.projections.add(_e), bx.Proj.projections.add(ie), bx.Proj.projections.add(se), bx.Proj.projections.add(ne), bx.Proj.projections.add(he), bx.Proj.projections.add(re), bx.Proj.projections.add(ce), bx.Proj.projections.add(oe), bx.Proj.projections.add(le), bx.Proj.projections.add(ue), bx.Proj.projections.add(a), bx.Proj.projections.add(d), bx.Proj.projections.add(Me), bx.Proj.projections.add(S);
        var Ge = ox;
        window.proj4 = Ge, i.a.Proj = {}, i.a.Proj._isProj4Obj = function (x) {
            return void 0 !== x.inverse && void 0 !== x.forward
        }, i.a.Proj.Projection = i.a.Class.extend({
            initialize: function (x, e, a) {
                var d = i.a.Proj._isProj4Obj(x);
                this._proj = d ? x : this._projFromCodeDef(x, e), this.bounds = d ? e : a
            }, project: function (x) {
                x = this._proj.forward([x.lng, x.lat]);
                return new i.a.Point(x[0], x[1])
            }, unproject: function (x, e) {
                x = this._proj.inverse([x.x, x.y]);
                return new i.a.LatLng(x[1], x[0], e)
            }, _projFromCodeDef: function (x, e) {
                if (e) Ge.defs(x, e); else if (void 0 === Ge.defs[x]) {
                    e = x.split(':');
                    if (3 < e.length && (x = e[e.length - 3] + ':' + e[e.length - 1]), void 0 === Ge.defs[x]) throw'No projection definition for code ' + x
                }
                return Ge(x)
            }
        }), i.a.Proj.CRS = i.a.Class.extend({
            includes: i.a.CRS,
            options: {transformation: new i.a.Transformation(1, 0, -1, 0)},
            initialize: function (x, e, a) {
                var d, f, t;
                if (i.a.Proj._isProj4Obj(x) ? (d = (f = x).srsCode, t = e || {}, this.projection = new i.a.Proj.Projection(f, i.a.bounds(t.bounds))) : (d = x, e = e, t = a || {}, this.projection = new i.a.Proj.Projection(d, e, i.a.bounds(t.bounds))), i.a.Util.setOptions(this, t), this.code = d, this.transformation = this.options.transformation, this.options.origin && (this.transformation = new i.a.Transformation(1, -this.options.origin[0], -1, this.options.origin[1])), this.options.scales) this._scales = this.options.scales; else if (this.options.resolutions) {
                    this._scales = [];
                    for (var _ = this.options.resolutions.length - 1; 0 <= _; _--) this.options.resolutions[_] && (this._scales[_] = 1 / this.options.resolutions[_])
                }
                this.infinite = !i.a.bounds(this.options.bounds)
            },
            scale: function (x) {
                var e, a = Math.floor(x);
                return x === a ? this._scales[x] : (e = this._scales[a]) + (this._scales[a + 1] - e) * (x - a)
            },
            zoom: function (x) {
                var e, a = this._closestElement(this._scales, x), d = this._scales.indexOf(a);
                return x === a ? d : void 0 === a ? -1 / 0 : (e = d + 1, void 0 === (e = this._scales[e]) ? 1 / 0 : (x - a) / (e - a) + d)
            },
            distance: i.a.CRS.Earth.distance,
            R: i.a.CRS.Earth.R,
            _closestElement: function (x, e) {
                for (var a, d = x.length; d--;) x[d] <= e && (void 0 === a || a < x[d]) && (a = x[d]);
                return a
            }
        }), i.a.Proj.GeoJSON = i.a.GeoJSON.extend({
            initialize: function (x, e) {
                this._callLevel = 0, i.a.GeoJSON.prototype.initialize.call(this, x, e)
            }, addData: function (x) {
                var e;
                x && (x.crs && 'name' === x.crs.type ? e = new i.a.Proj.CRS(x.crs.properties.name) : x.crs && x.crs.type && (e = new i.a.Proj.CRS(x.crs.type + ':' + x.crs.properties.code)), void 0 !== e && (this.options.coordsToLatLng = function (x) {
                    x = i.a.point(x[0], x[1]);
                    return e.projection.unproject(x)
                })), this._callLevel++;
                try {
                    i.a.GeoJSON.prototype.addData.call(this, x)
                } finally {
                    this._callLevel--, 0 === this._callLevel && delete this.options.coordsToLatLng
                }
            }
        }), i.a.Proj.geoJson = function (x, e) {
            return new i.a.Proj.GeoJSON(x, e)
        }, i.a.Proj.ImageOverlay = i.a.ImageOverlay.extend({
            initialize: function (x, e, a) {
                i.a.ImageOverlay.prototype.initialize.call(this, x, null, a), this._projectedBounds = e
            }, _animateZoom: function (x) {
                var e = this._map.getZoomScale(x.zoom),
                    a = i.a.point(this._projectedBounds.min.x, this._projectedBounds.max.y),
                    x = this._projectedToNewLayerPoint(a, x.zoom, x.center);
                i.a.DomUtil.setTransform(this._image, x, e)
            }, _reset: function () {
                var x = this._map.getZoom(), e = this._map.getPixelOrigin(),
                    x = i.a.bounds(this._transform(this._projectedBounds.min, x)._subtract(e), this._transform(this._projectedBounds.max, x)._subtract(e)),
                    e = x.getSize();
                i.a.DomUtil.setPosition(this._image, x.min), this._image.style.width = e.x + 'px', this._image.style.height = e.y + 'px'
            }, _projectedToNewLayerPoint: function (x, e, a) {
                var d = this._map.getSize()._divideBy(2),
                    d = this._map.project(a, e)._subtract(d)._round().add(this._map._getMapPanePos());
                return this._transform(x, e)._subtract(d)
            }, _transform: function (x, e) {
                var a = this._map.options.crs, d = a.transformation, e = a.scale(e);
                return d.transform(x, e)
            }
        }), i.a.Proj.imageOverlay = function (x, e, a) {
            return new i.a.Proj.ImageOverlay(x, e, a)
        }
    }, function (x, e) {
        x.exports = function (x, e, a) {
            return e in x ? Object.defineProperty(x, e, {
                value: a,
                enumerable: !0,
                configurable: !0,
                writable: !0
            }) : x[e] = a, x
        }, x.exports.default = x.exports, x.exports.__esModule = !0
    }, function (x, e, a) {
        'use strict';
        a.d(e, 'a', function () {
            return f
        });
        var e = a(0), d = a.n(e), f = d.a.TileLayer.extend({
            isUpper: !0, _url: '', initialize: function (x, e) {
                (e = null == e ? {} : e).hasOwnProperty('isUpper') && (this.isUpper = e.isUpper), this._url = x, d.a.setOptions(this, e)
            }, onAdd: function (x) {
                d.a.TileLayer.prototype.onAdd.call(this, x)
            }, createTile: function (x, e) {
                var a = document.createElement('img');
                return d.a.DomEvent.on(a, 'load', d.a.Util.bind(this._tileOnLoad, this, e, a)), d.a.DomEvent.on(a, 'error', d.a.Util.bind(this._tileOnError, this, e, a)), !this.options.crossOrigin && '' !== this.options.crossOrigin || (a.crossOrigin = !0 === this.options.crossOrigin ? '' : this.options.crossOrigin), a.alt = '', a.setAttribute('role', 'presentation'), this.getMapPictureBinary(x, function (x) {
                    a.src = x
                }), a
            }, getMapPictureBinary: function (x, _) {
                var e = x.z, i = x.x, s = x.y;
                s < 0 && (s = -s);
                var a = e.toString().length, n = e;
                if (a < 2) for (var d = 0; d < 2 - a; d++) n = '0' + n;
                var n = 'L' + n, h = 128 * parseInt(s / 128), x = h.toString(16), f = x.length, t = x;
                if (f < 4) for (var r = 0; r < 4 - f; r++) t = '0' + t;
                var t = 'R' + t, c = 128 * parseInt(i / 128), e = c.toString(16), o = e, l = e.length;
                if (l < 4) for (var u = 0; u < 4 - l; u++) o = '0' + o;
                o = 'C' + o, this.isUpper && (n = n.toUpperCase(), t = t.toUpperCase(), o = o.toUpperCase());
                var x = this._url + '/' + n + '/' + t + o + '.bundlx',
                    b = this._url + '/' + n + '/' + t + o + '.bundle', m = this, e = new XMLHttpRequest;
                e.open('GET', x, !0), e.responseType = 'blob', e.onload = function (x) {
                    var e = this.response, t = new FileReader;
                    t.addEventListener('loadend', function () {
                        for (var x = new Uint8Array(t.result), e = i - c, a = s - h, d = new Uint8Array(new ArrayBuffer(5)), f = 0; f < 5; f++) d[f] = x[16 + 5 * (128 * e + a) + f];
                        m.getPictureBuffer(d, i, s, b, n, _)
                    }), t.readAsArrayBuffer(e)
                }, e.send()
            }, getPictureBuffer: function (n, x, e, a, d, h) {
                e < 0 && (e = -e);
                e = new XMLHttpRequest;
                e.open('GET', a, !0), e.responseType = 'blob', e.onload = function (x) {
                    var e = this.response, s = new FileReader;
                    s.addEventListener('loadend', function () {
                        for (var x = new Uint8Array(s.result), e = parseInt(n[0]) + 256 * parseInt(n[1]) + 65536 * parseInt(n[2]) + 16777216 * parseInt(n[3]) + 4294967296 * parseInt(n[4]), a = [], d = 0; d < 4; d++) a.push(x[e + d]);
                        for (var f = 2 * (a[0] + 256 * a[1] + 65536 * a[2] + 16777216 * a[3]), t = new Uint8Array(new ArrayBuffer(f)), _ = 0; _ < f; _++) t[_] = x[e + 4 + _];
                        var i = new Blob([t], {type: 'image/png'});
                        return h(window.URL.createObjectURL(i)), t
                    }), s.readAsArrayBuffer(e)
                }, e.send()
            }
        });
        d.a.TileLayer.ArcGISCompactTile = f, d.a.tileLayer.arcGISCompactTile = function (x, e) {
            return new f(x, e)
        }
    }, function (x, e, a) {
    }, function (x, e, a) {
    }, function (x, e, a) {
    }, function (x, e, a) {
    }, function (x, e, a) {
        !function (x) {
            'use strict';
            var e = L.MarkerClusterGroup = L.FeatureGroup.extend({
                options: {
                    maxClusterRadius: 80,
                    iconCreateFunction: null,
                    clusterPane: L.Marker.prototype.options.pane,
                    spiderfyOnMaxZoom: !0,
                    showCoverageOnHover: !0,
                    zoomToBoundsOnClick: !0,
                    singleMarkerMode: !1,
                    disableClusteringAtZoom: null,
                    removeOutsideVisibleBounds: !0,
                    animate: !0,
                    animateAddingMarkers: !1,
                    spiderfyDistanceMultiplier: 1,
                    spiderLegPolylineOptions: {weight: 1.5, color: '#222', opacity: .5},
                    chunkedLoading: !1,
                    chunkInterval: 200,
                    chunkDelay: 50,
                    chunkProgress: null,
                    polygonOptions: {}
                }, initialize: function (x) {
                    L.Util.setOptions(this, x), this.options.iconCreateFunction || (this.options.iconCreateFunction = this._defaultIconCreateFunction), this._featureGroup = L.featureGroup(), this._featureGroup.addEventParent(this), this._nonPointGroup = L.featureGroup(), this._nonPointGroup.addEventParent(this), this._inZoomAnimation = 0, this._needsClustering = [], this._needsRemoving = [], this._currentShownBounds = null, this._queue = [], this._childMarkerEventHandlers = {
                        dragstart: this._childMarkerDragStart,
                        move: this._childMarkerMoved,
                        dragend: this._childMarkerDragEnd
                    };
                    x = L.DomUtil.TRANSITION && this.options.animate;
                    L.extend(this, x ? this._withAnimation : this._noAnimation), this._markerCluster = x ? L.MarkerCluster : L.MarkerClusterNonAnimated
                }, addLayer: function (x) {
                    if (x instanceof L.LayerGroup) return this.addLayers([x]);
                    if (!x.getLatLng) return this._nonPointGroup.addLayer(x), this.fire('layeradd', {layer: x}), this;
                    if (!this._map) return this._needsClustering.push(x), this.fire('layeradd', {layer: x}), this;
                    if (this.hasLayer(x)) return this;
                    this._unspiderfy && this._unspiderfy(), this._addLayer(x, this._maxZoom), this.fire('layeradd', {layer: x}), this._topClusterLevel._recalculateBounds(), this._refreshClustersIcons();
                    var e = x, a = this._zoom;
                    if (x.__parent) for (; e.__parent._zoom >= a;) e = e.__parent;
                    return this._currentShownBounds.contains(e.getLatLng()) && (this.options.animateAddingMarkers ? this._animationAddLayer(x, e) : this._animationAddLayerNonAnimated(x, e)), this
                }, removeLayer: function (x) {
                    return x instanceof L.LayerGroup ? this.removeLayers([x]) : (x.getLatLng ? this._map ? x.__parent && (this._unspiderfy && (this._unspiderfy(), this._unspiderfyLayer(x)), this._removeLayer(x, !0), this.fire('layerremove', {layer: x}), this._topClusterLevel._recalculateBounds(), this._refreshClustersIcons(), x.off(this._childMarkerEventHandlers, this), this._featureGroup.hasLayer(x) && (this._featureGroup.removeLayer(x), x.clusterShow && x.clusterShow())) : (!this._arraySplice(this._needsClustering, x) && this.hasLayer(x) && this._needsRemoving.push({
                        layer: x,
                        latlng: x._latlng
                    }), this.fire('layerremove', {layer: x})) : (this._nonPointGroup.removeLayer(x), this.fire('layerremove', {layer: x})), this)
                }, addLayers: function (a, d) {
                    if (!L.Util.isArray(a)) return this.addLayer(a);
                    var f, t = this._featureGroup, _ = this._nonPointGroup, i = this.options.chunkedLoading,
                        s = this.options.chunkInterval, n = this.options.chunkProgress, h = a.length, r = 0, c = !0;
                    if (this._map) {
                        var o = (new Date).getTime(), l = L.bind(function () {
                            for (var x, e = (new Date).getTime(); r < h && !(i && r % 200 == 0 && (new Date).getTime() - e > s); r++) (f = a[r]) instanceof L.LayerGroup ? (c && (a = a.slice(), c = !1), this._extractNonGroupLayers(f, a), h = a.length) : f.getLatLng ? !this.hasLayer(f) && (this._addLayer(f, this._maxZoom), d || this.fire('layeradd', {layer: f}), f.__parent && 2 === f.__parent.getChildCount()) && (x = (x = f.__parent.getAllChildMarkers())[0] === f ? x[1] : x[0], t.removeLayer(x)) : (_.addLayer(f), d || this.fire('layeradd', {layer: f}));
                            n && n(r, h, (new Date).getTime() - o), r === h ? (this._topClusterLevel._recalculateBounds(), this._refreshClustersIcons(), this._topClusterLevel._recursivelyAddChildrenToMap(null, this._zoom, this._currentShownBounds)) : setTimeout(l, this.options.chunkDelay)
                        }, this);
                        l()
                    } else for (var x = this._needsClustering; r < h; r++) (f = a[r]) instanceof L.LayerGroup ? (c && (a = a.slice(), c = !1), this._extractNonGroupLayers(f, a), h = a.length) : f.getLatLng ? this.hasLayer(f) || x.push(f) : _.addLayer(f);
                    return this
                }, removeLayers: function (x) {
                    var e, a = x.length, d = this._featureGroup, f = this._nonPointGroup, t = !0;
                    if (!this._map) {
                        for (s = 0; s < a; s++) (e = x[s]) instanceof L.LayerGroup ? (t && (x = x.slice(), t = !1), this._extractNonGroupLayers(e, x), a = x.length) : (this._arraySplice(this._needsClustering, e), f.removeLayer(e), this.hasLayer(e) && this._needsRemoving.push({
                            layer: e,
                            latlng: e._latlng
                        }), this.fire('layerremove', {layer: e}));
                        return this
                    }
                    if (this._unspiderfy) {
                        this._unspiderfy();
                        for (var _ = x.slice(), i = a, s = 0; s < i; s++) (e = _[s]) instanceof L.LayerGroup ? (this._extractNonGroupLayers(e, _), i = _.length) : this._unspiderfyLayer(e)
                    }
                    for (s = 0; s < a; s++) (e = x[s]) instanceof L.LayerGroup ? (t && (x = x.slice(), t = !1), this._extractNonGroupLayers(e, x), a = x.length) : e.__parent ? (this._removeLayer(e, !0, !0), this.fire('layerremove', {layer: e}), d.hasLayer(e) && (d.removeLayer(e), e.clusterShow && e.clusterShow())) : (f.removeLayer(e), this.fire('layerremove', {layer: e}));
                    return this._topClusterLevel._recalculateBounds(), this._refreshClustersIcons(), this._topClusterLevel._recursivelyAddChildrenToMap(null, this._zoom, this._currentShownBounds), this
                }, clearLayers: function () {
                    return this._map || (this._needsClustering = [], this._needsRemoving = [], delete this._gridClusters, delete this._gridUnclustered), this._noanimationUnspiderfy && this._noanimationUnspiderfy(), this._featureGroup.clearLayers(), this._nonPointGroup.clearLayers(), this.eachLayer(function (x) {
                        x.off(this._childMarkerEventHandlers, this), delete x.__parent
                    }, this), this._map && this._generateInitialClusters(), this
                }, getBounds: function () {
                    var x = new L.LatLngBounds;
                    this._topClusterLevel && x.extend(this._topClusterLevel._bounds);
                    for (var e = this._needsClustering.length - 1; 0 <= e; e--) x.extend(this._needsClustering[e].getLatLng());
                    return x.extend(this._nonPointGroup.getBounds()), x
                }, eachLayer: function (x, e) {
                    var a, d, f, t = this._needsClustering.slice(), _ = this._needsRemoving;
                    for (this._topClusterLevel && this._topClusterLevel.getAllChildMarkers(t), d = t.length - 1; 0 <= d; d--) {
                        for (a = !0, f = _.length - 1; 0 <= f; f--) if (_[f].layer === t[d]) {
                            a = !1;
                            break
                        }
                        a && x.call(e, t[d])
                    }
                    this._nonPointGroup.eachLayer(x, e)
                }, getLayers: function () {
                    var e = [];
                    return this.eachLayer(function (x) {
                        e.push(x)
                    }), e
                }, getLayer: function (e) {
                    var a = null;
                    return e = parseInt(e, 10), this.eachLayer(function (x) {
                        L.stamp(x) === e && (a = x)
                    }), a
                }, hasLayer: function (x) {
                    if (!x) return !1;
                    for (var e = this._needsClustering, a = e.length - 1; 0 <= a; a--) if (e[a] === x) return !0;
                    for (a = (e = this._needsRemoving).length - 1; 0 <= a; a--) if (e[a].layer === x) return !1;
                    return !(!x.__parent || x.__parent._group !== this) || this._nonPointGroup.hasLayer(x)
                }, zoomToShowLayer: function (x, e) {
                    'function' != _typeof(e) && (e = function () {
                    });

                    function a() {
                        !x._icon && !x.__parent._icon || this._inZoomAnimation || (this._map.off('moveend', a, this), this.off('animationend', a, this), x._icon ? e() : x.__parent._icon && (this.once('spiderfied', e, this), x.__parent.spiderfy()))
                    }

                    x._icon && this._map.getBounds().contains(x.getLatLng()) ? e() : x.__parent._zoom < Math.round(this._map._zoom) ? (this._map.on('moveend', a, this), this._map.panTo(x.getLatLng())) : (this._map.on('moveend', a, this), this.on('animationend', a, this), x.__parent.zoomToBounds())
                }, onAdd: function (x) {
                    var e, a, d;
                    if (this._map = x, !isFinite(this._map.getMaxZoom())) throw 'Map has no maxZoom specified';
                    for (this._featureGroup.addTo(x), this._nonPointGroup.addTo(x), this._gridClusters || this._generateInitialClusters(), this._maxLat = x.options.crs.projection.MAX_LATITUDE, e = 0, a = this._needsRemoving.length; e < a; e++) (d = this._needsRemoving[e]).newlatlng = d.layer._latlng, d.layer._latlng = d.latlng;
                    for (e = 0, a = this._needsRemoving.length; e < a; e++) d = this._needsRemoving[e], this._removeLayer(d.layer, !0), d.layer._latlng = d.newlatlng;
                    this._needsRemoving = [], this._zoom = Math.round(this._map._zoom), this._currentShownBounds = this._getExpandedVisibleBounds(), this._map.on('zoomend', this._zoomEnd, this), this._map.on('moveend', this._moveEnd, this), this._spiderfierOnAdd && this._spiderfierOnAdd(), this._bindEvents(), a = this._needsClustering, this._needsClustering = [], this.addLayers(a, !0)
                }, onRemove: function (x) {
                    x.off('zoomend', this._zoomEnd, this), x.off('moveend', this._moveEnd, this), this._unbindEvents(), this._map._mapPane.className = this._map._mapPane.className.replace(' leaflet-cluster-anim', ''), this._spiderfierOnRemove && this._spiderfierOnRemove(), delete this._maxLat, this._hideCoverage(), this._featureGroup.remove(), this._nonPointGroup.remove(), this._featureGroup.clearLayers(), this._map = null
                }, getVisibleParent: function (x) {
                    for (var e = x; e && !e._icon;) e = e.__parent;
                    return e || null
                }, _arraySplice: function (x, e) {
                    for (var a = x.length - 1; 0 <= a; a--) if (x[a] === e) return x.splice(a, 1), !0
                }, _removeFromGridUnclustered: function (x, e) {
                    for (var a = this._map, d = this._gridUnclustered, f = Math.floor(this._map.getMinZoom()); f <= e && d[e].removeObject(x, a.project(x.getLatLng(), e)); e--) ;
                }, _childMarkerDragStart: function (x) {
                    x.target.__dragStart = x.target._latlng
                }, _childMarkerMoved: function (x) {
                    var e;
                    this._ignoreMove || x.target.__dragStart || (e = x.target._popup && x.target._popup.isOpen(), this._moveChild(x.target, x.oldLatLng, x.latlng), e && x.target.openPopup())
                }, _moveChild: function (x, e, a) {
                    x._latlng = e, this.removeLayer(x), x._latlng = a, this.addLayer(x)
                }, _childMarkerDragEnd: function (x) {
                    var e = x.target.__dragStart;
                    delete x.target.__dragStart, e && this._moveChild(x.target, e, x.target._latlng)
                }, _removeLayer: function (x, e, a) {
                    var d = this._gridClusters, f = this._gridUnclustered, t = this._featureGroup, _ = this._map,
                        i = Math.floor(this._map.getMinZoom());
                    e && this._removeFromGridUnclustered(x, this._maxZoom);
                    var s, n = x.__parent, h = n._markers;
                    for (this._arraySplice(h, x); n && (n._childCount--, n._boundsNeedUpdate = !0, !(n._zoom < i));) e && n._childCount <= 1 ? (s = n._markers[0] === x ? n._markers[1] : n._markers[0], d[n._zoom].removeObject(n, _.project(n._cLatLng, n._zoom)), f[n._zoom].addObject(s, _.project(s.getLatLng(), n._zoom)), this._arraySplice(n.__parent._childClusters, n), n.__parent._markers.push(s), s.__parent = n.__parent, n._icon && (t.removeLayer(n), a || t.addLayer(s))) : n._iconNeedsUpdate = !0, n = n.__parent;
                    delete x.__parent
                }, _isOrIsParent: function (x, e) {
                    for (; e;) {
                        if (x === e) return !0;
                        e = e.parentNode
                    }
                    return !1
                }, fire: function (x, e, a) {
                    if (e && e.layer instanceof L.MarkerCluster) {
                        if (e.originalEvent && this._isOrIsParent(e.layer._icon, e.originalEvent.relatedTarget)) return;
                        x = 'cluster' + x
                    }
                    L.FeatureGroup.prototype.fire.call(this, x, e, a)
                }, listens: function (x, e) {
                    return L.FeatureGroup.prototype.listens.call(this, x, e) || L.FeatureGroup.prototype.listens.call(this, 'cluster' + x, e)
                }, _defaultIconCreateFunction: function (x) {
                    var e = x.getChildCount(), x = ' marker-cluster-';
                    return x += a0_0x1dfe(e < 10 ? '0x61f' : e < 100 ? '0x103' : '0x926'), new L.DivIcon({
                        html: '<div><span>' + e + '</span></div>',
                        className: 'marker-cluster' + x,
                        iconSize: new L.Point(40, 40)
                    })
                }, _bindEvents: function () {
                    var x = this._map, e = this.options.spiderfyOnMaxZoom, a = this.options.showCoverageOnHover,
                        d = this.options.zoomToBoundsOnClick;
                    (e || d) && this.on('clusterclick', this._zoomOrSpiderfy, this), a && (this.on('clustermouseover', this._showCoverage, this), this.on('clustermouseout', this._hideCoverage, this), x.on('zoomend', this._hideCoverage, this))
                }, _zoomOrSpiderfy: function (x) {
                    for (var e = x.layer, a = e; 1 === a._childClusters.length;) a = a._childClusters[0];
                    a._zoom === this._maxZoom && a._childCount === e._childCount && this.options.spiderfyOnMaxZoom ? e.spiderfy() : this.options.zoomToBoundsOnClick && e.zoomToBounds(), x.originalEvent && 13 === x.originalEvent.keyCode && this._map._container.focus()
                }, _showCoverage: function (x) {
                    var e = this._map;
                    this._inZoomAnimation || (this._shownPolygon && e.removeLayer(this._shownPolygon), 2 < x.layer.getChildCount() && x.layer !== this._spiderfied && (this._shownPolygon = new L.Polygon(x.layer.getConvexHull(), this.options.polygonOptions), e.addLayer(this._shownPolygon)))
                }, _hideCoverage: function () {
                    this._shownPolygon && (this._map.removeLayer(this._shownPolygon), this._shownPolygon = null)
                }, _unbindEvents: function () {
                    var x = this.options.spiderfyOnMaxZoom, e = this.options.showCoverageOnHover,
                        a = this.options.zoomToBoundsOnClick, d = this._map;
                    (x || a) && this.off('clusterclick', this._zoomOrSpiderfy, this), e && (this.off('clustermouseover', this._showCoverage, this), this.off('clustermouseout', this._hideCoverage, this), d.off('zoomend', this._hideCoverage, this))
                }, _zoomEnd: function () {
                    this._map && (this._mergeSplitClusters(), this._zoom = Math.round(this._map._zoom), this._currentShownBounds = this._getExpandedVisibleBounds())
                }, _moveEnd: function () {
                    var x;
                    this._inZoomAnimation || (x = this._getExpandedVisibleBounds(), this._topClusterLevel._recursivelyRemoveChildrenFromMap(this._currentShownBounds, Math.floor(this._map.getMinZoom()), this._zoom, x), this._topClusterLevel._recursivelyAddChildrenToMap(null, Math.round(this._map._zoom), x), this._currentShownBounds = x)
                }, _generateInitialClusters: function () {
                    var x = Math.ceil(this._map.getMaxZoom()), e = Math.floor(this._map.getMinZoom()),
                        a = this.options.maxClusterRadius, d = 'function' != typeof a ? function () {
                            return a
                        } : a;
                    null !== this.options.disableClusteringAtZoom && (x = this.options.disableClusteringAtZoom - 1), this._maxZoom = x, this._gridClusters = {}, this._gridUnclustered = {};
                    for (var f = x; e <= f; f--) this._gridClusters[f] = new L.DistanceGrid(d(f)), this._gridUnclustered[f] = new L.DistanceGrid(d(f));
                    this._topClusterLevel = new this._markerCluster(this, e - 1)
                }, _addLayer: function (x, e) {
                    var a = this._gridClusters, d = this._gridUnclustered, f = Math.floor(this._map.getMinZoom());
                    for (this.options.singleMarkerMode && this._overrideMarkerIcon(x), x.on(this._childMarkerEventHandlers, this); f <= e; e--) {
                        var t = this._map.project(x.getLatLng(), e), _ = a[e].getNearObject(t);
                        if (_) return _._addChild(x), void (x.__parent = _);
                        if (_ = d[e].getNearObject(t)) {
                            var i = _.__parent;
                            i && this._removeLayer(_, !1);
                            var s = new this._markerCluster(this, e, _, x);
                            a[e].addObject(s, this._map.project(s._cLatLng, e)), _.__parent = s;
                            for (var n = x.__parent = s, h = e - 1; h > i._zoom; h--) n = new this._markerCluster(this, h, n), a[h].addObject(n, this._map.project(_.getLatLng(), h));
                            return i._addChild(n), void this._removeFromGridUnclustered(_, e)
                        }
                        d[e].addObject(x, t)
                    }
                    this._topClusterLevel._addChild(x), x.__parent = this._topClusterLevel
                }, _refreshClustersIcons: function () {
                    this._featureGroup.eachLayer(function (x) {
                        x instanceof L.MarkerCluster && x._iconNeedsUpdate && x._updateIcon()
                    })
                }, _enqueue: function (x) {
                    this._queue.push(x), this._queueTimeout || (this._queueTimeout = setTimeout(L.bind(this._processQueue, this), 300))
                }, _processQueue: function () {
                    for (var x = 0; x < this._queue.length; x++) this._queue[x].call(this);
                    this._queue.length = 0, clearTimeout(this._queueTimeout), this._queueTimeout = null
                }, _mergeSplitClusters: function () {
                    var x = Math.round(this._map._zoom);
                    this._processQueue(), this._zoom < x && this._currentShownBounds.intersects(this._getExpandedVisibleBounds()) ? (this._animationStart(), this._topClusterLevel._recursivelyRemoveChildrenFromMap(this._currentShownBounds, Math.floor(this._map.getMinZoom()), this._zoom, this._getExpandedVisibleBounds()), this._animationZoomIn(this._zoom, x)) : this._zoom > x ? (this._animationStart(), this._animationZoomOut(this._zoom, x)) : this._moveEnd()
                }, _getExpandedVisibleBounds: function () {
                    return this.options.removeOutsideVisibleBounds ? L.Browser.mobile ? this._checkBoundsMaxLat(this._map.getBounds()) : this._checkBoundsMaxLat(this._map.getBounds().pad(1)) : this._mapBoundsInfinite
                }, _checkBoundsMaxLat: function (x) {
                    var e = this._maxLat;
                    return void 0 !== e && (x.getNorth() >= e && (x._northEast.lat = 1 / 0), x.getSouth() <= -e && (x._southWest.lat = -1 / 0)), x
                }, _animationAddLayerNonAnimated: function (x, e) {
                    e === x ? this._featureGroup.addLayer(x) : 2 === e._childCount ? (e._addToMap(), x = e.getAllChildMarkers(), this._featureGroup.removeLayer(x[0]), this._featureGroup.removeLayer(x[1])) : e._updateIcon()
                }, _extractNonGroupLayers: function (x, e) {
                    var a, d = x.getLayers(), f = 0;
                    for (e = e || []; f < d.length; f++) (a = d[f]) instanceof L.LayerGroup ? this._extractNonGroupLayers(a, e) : e.push(a);
                    return e
                }, _overrideMarkerIcon: function (x) {
                    return x.options.icon = this.options.iconCreateFunction({
                        getChildCount: function () {
                            return 1
                        }, getAllChildMarkers: function () {
                            return [x]
                        }
                    })
                }
            });
            L.MarkerClusterGroup.include({_mapBoundsInfinite: new L.LatLngBounds(new L.LatLng(-1 / 0, -1 / 0), new L.LatLng(1 / 0, 1 / 0))}), L.MarkerClusterGroup.include({
                _noAnimation: {
                    _animationStart: function () {
                    }, _animationZoomIn: function (x, e) {
                        this._topClusterLevel._recursivelyRemoveChildrenFromMap(this._currentShownBounds, Math.floor(this._map.getMinZoom()), x), this._topClusterLevel._recursivelyAddChildrenToMap(null, e, this._getExpandedVisibleBounds()), this.fire('animationend')
                    }, _animationZoomOut: function (x, e) {
                        this._topClusterLevel._recursivelyRemoveChildrenFromMap(this._currentShownBounds, Math.floor(this._map.getMinZoom()), x), this._topClusterLevel._recursivelyAddChildrenToMap(null, e, this._getExpandedVisibleBounds()), this.fire('animationend')
                    }, _animationAddLayer: function (x, e) {
                        this._animationAddLayerNonAnimated(x, e)
                    }
                }, _withAnimation: {
                    _animationStart: function () {
                        this._map._mapPane.className += ' leaflet-cluster-anim', this._inZoomAnimation++
                    }, _animationZoomIn: function (f, t) {
                        var _, i = this._getExpandedVisibleBounds(), s = this._featureGroup,
                            x = Math.floor(this._map.getMinZoom());
                        this._ignoreMove = !0, this._topClusterLevel._recursively(i, f, x, function (x) {
                            var e, a = x._latlng, d = x._markers;
                            for (i.contains(a) || (a = null), x._isSingleParent() && f + 1 === t ? (s.removeLayer(x), x._recursivelyAddChildrenToMap(null, t, i)) : (x.clusterHide(), x._recursivelyAddChildrenToMap(a, t, i)), _ = d.length - 1; 0 <= _; _--) e = d[_], i.contains(e._latlng) || s.removeLayer(e)
                        }), this._forceLayout(), this._topClusterLevel._recursivelyBecomeVisible(i, t), s.eachLayer(function (x) {
                            x instanceof L.MarkerCluster || !x._icon || x.clusterShow()
                        }), this._topClusterLevel._recursively(i, f, t, function (x) {
                            x._recursivelyRestoreChildPositions(t)
                        }), this._ignoreMove = !1, this._enqueue(function () {
                            this._topClusterLevel._recursively(i, f, x, function (x) {
                                s.removeLayer(x), x.clusterShow()
                            }), this._animationEnd()
                        })
                    }, _animationZoomOut: function (x, e) {
                        this._animationZoomOutSingle(this._topClusterLevel, x - 1, e), this._topClusterLevel._recursivelyAddChildrenToMap(null, e, this._getExpandedVisibleBounds()), this._topClusterLevel._recursivelyRemoveChildrenFromMap(this._currentShownBounds, Math.floor(this._map.getMinZoom()), x, this._getExpandedVisibleBounds())
                    }, _animationAddLayer: function (x, e) {
                        var a = this, d = this._featureGroup;
                        d.addLayer(x), e !== x && (2 < e._childCount ? (e._updateIcon(), this._forceLayout(), this._animationStart(), x._setPos(this._map.latLngToLayerPoint(e.getLatLng())), x.clusterHide(), this._enqueue(function () {
                            d.removeLayer(x), x.clusterShow(), a._animationEnd()
                        })) : (this._forceLayout(), a._animationStart(), a._animationZoomOutSingle(e, this._map.getMaxZoom(), this._zoom)))
                    }
                }, _animationZoomOutSingle: function (e, a, d) {
                    var f = this._getExpandedVisibleBounds(), t = Math.floor(this._map.getMinZoom());
                    e._recursivelyAnimateChildrenInAndAddSelfToMap(f, t, a + 1, d);
                    var _ = this;
                    this._forceLayout(), e._recursivelyBecomeVisible(f, d), this._enqueue(function () {
                        var x;
                        1 === e._childCount ? (x = e._markers[0], this._ignoreMove = !0, x.setLatLng(x.getLatLng()), this._ignoreMove = !1, x.clusterShow && x.clusterShow()) : e._recursively(f, d, t, function (x) {
                            x._recursivelyRemoveChildrenFromMap(f, t, a + 1)
                        }), _._animationEnd()
                    })
                }, _animationEnd: function () {
                    this._map && (this._map._mapPane.className = this._map._mapPane.className.replace(' leaflet-cluster-anim', '')), this._inZoomAnimation--, this.fire('animationend')
                }, _forceLayout: function () {
                    L.Util.falseFn(document.body.offsetWidth)
                }
            }), L.markerClusterGroup = function (x) {
                return new L.MarkerClusterGroup(x)
            };
            var a = L.MarkerCluster = L.Marker.extend({
                options: L.Icon.prototype.options, initialize: function (x, e, a, d) {
                    L.Marker.prototype.initialize.call(this, a ? a._cLatLng || a.getLatLng() : new L.LatLng(0, 0), {
                        icon: this,
                        pane: x.options.clusterPane
                    }), this._group = x, this._zoom = e, this._markers = [], this._childClusters = [], this._childCount = 0, this._iconNeedsUpdate = !0, this._boundsNeedUpdate = !0, this._bounds = new L.LatLngBounds, a && this._addChild(a), d && this._addChild(d)
                }, getAllChildMarkers: function (x, e) {
                    x = x || [];
                    for (var a = this._childClusters.length - 1; 0 <= a; a--) this._childClusters[a].getAllChildMarkers(x);
                    for (var d = this._markers.length - 1; 0 <= d; d--) e && this._markers[d].__dragStart || x.push(this._markers[d]);
                    return x
                }, getChildCount: function () {
                    return this._childCount
                }, zoomToBounds: function (x) {
                    for (var e = this._childClusters.slice(), a = this._group._map, d = a.getBoundsZoom(this._bounds), f = this._zoom + 1, a = a.getZoom(); 0 < e.length && f < d;) {
                        f++;
                        for (var t = [], _ = 0; _ < e.length; _++) t = t.concat(e[_]._childClusters);
                        e = t
                    }
                    f < d ? this._group._map.setView(this._latlng, f) : d <= a ? this._group._map.setView(this._latlng, a + 1) : this._group._map.fitBounds(this._bounds, x)
                }, getBounds: function () {
                    var x = new L.LatLngBounds;
                    return x.extend(this._bounds), x
                }, _updateIcon: function () {
                    this._iconNeedsUpdate = !0, this._icon && this.setIcon(this)
                }, createIcon: function () {
                    return this._iconNeedsUpdate && (this._iconObj = this._group.options.iconCreateFunction(this), this._iconNeedsUpdate = !1), this._iconObj.createIcon()
                }, createShadow: function () {
                    return this._iconObj.createShadow()
                }, _addChild: function (x, e) {
                    this._iconNeedsUpdate = !0, this._boundsNeedUpdate = !0, this._setClusterCenter(x), x instanceof L.MarkerCluster ? (e || (this._childClusters.push(x), x.__parent = this), this._childCount += x._childCount) : (e || this._markers.push(x), this._childCount++), this.__parent && this.__parent._addChild(x, !0)
                }, _setClusterCenter: function (x) {
                    this._cLatLng || (this._cLatLng = x._cLatLng || x._latlng)
                }, _resetBounds: function () {
                    var x = this._bounds;
                    x._southWest && (x._southWest.lat = 1 / 0, x._southWest.lng = 1 / 0), x._northEast && (x._northEast.lat = -1 / 0, x._northEast.lng = -1 / 0)
                }, _recalculateBounds: function () {
                    var x, e, a, d = this._markers, f = this._childClusters, t = 0, _ = 0, i = this._childCount;
                    if (0 !== i) {
                        for (this._resetBounds(), x = 0; x < d.length; x++) e = d[x]._latlng, this._bounds.extend(e), t += e.lat, _ += e.lng;
                        for (x = 0; x < f.length; x++) (a = f[x])._boundsNeedUpdate && a._recalculateBounds(), this._bounds.extend(a._bounds), e = a._wLatLng, a = a._childCount, t += e.lat * a, _ += e.lng * a;
                        this._latlng = this._wLatLng = new L.LatLng(t / i, _ / i), this._boundsNeedUpdate = !1
                    }
                }, _addToMap: function (x) {
                    x && (this._backupLatlng = this._latlng, this.setLatLng(x)), this._group._featureGroup.addLayer(this)
                }, _recursivelyAnimateChildrenIn: function (x, f, e) {
                    this._recursively(x, this._group._map.getMinZoom(), e - 1, function (x) {
                        for (var e, a = x._markers, d = a.length - 1; 0 <= d; d--) (e = a[d])._icon && (e._setPos(f), e.clusterHide())
                    }, function (x) {
                        for (var e, a = x._childClusters, d = a.length - 1; 0 <= d; d--) (e = a[d])._icon && (e._setPos(f), e.clusterHide())
                    })
                }, _recursivelyAnimateChildrenInAndAddSelfToMap: function (e, a, d, f) {
                    this._recursively(e, f, a, function (x) {
                        x._recursivelyAnimateChildrenIn(e, x._group._map.latLngToLayerPoint(x.getLatLng()).round(), d), x._isSingleParent() && d - 1 === f ? (x.clusterShow(), x._recursivelyRemoveChildrenFromMap(e, a, d)) : x.clusterHide(), x._addToMap()
                    })
                }, _recursivelyBecomeVisible: function (x, e) {
                    this._recursively(x, this._group._map.getMinZoom(), e, null, function (x) {
                        x.clusterShow()
                    })
                }, _recursivelyAddChildrenToMap: function (d, f, t) {
                    this._recursively(t, this._group._map.getMinZoom() - 1, f, function (x) {
                        if (f !== x._zoom) for (var e = x._markers.length - 1; 0 <= e; e--) {
                            var a = x._markers[e];
                            t.contains(a._latlng) && (d && (a._backupLatlng = a.getLatLng(), a.setLatLng(d), a.clusterHide && a.clusterHide()), x._group._featureGroup.addLayer(a))
                        }
                    }, function (x) {
                        x._addToMap(d)
                    })
                }, _recursivelyRestoreChildPositions: function (x) {
                    for (var e = this._markers.length - 1; 0 <= e; e--) {
                        var a = this._markers[e];
                        a._backupLatlng && (a.setLatLng(a._backupLatlng), delete a._backupLatlng)
                    }
                    if (x - 1 === this._zoom) for (var d = this._childClusters.length - 1; 0 <= d; d--) this._childClusters[d]._restorePosition(); else for (var f = this._childClusters.length - 1; 0 <= f; f--) this._childClusters[f]._recursivelyRestoreChildPositions(x)
                }, _restorePosition: function () {
                    this._backupLatlng && (this.setLatLng(this._backupLatlng), delete this._backupLatlng)
                }, _recursivelyRemoveChildrenFromMap: function (x, e, a, d) {
                    var f, t;
                    this._recursively(x, e - 1, a - 1, function (x) {
                        for (t = x._markers.length - 1; 0 <= t; t--) f = x._markers[t], d && d.contains(f._latlng) || (x._group._featureGroup.removeLayer(f), f.clusterShow && f.clusterShow())
                    }, function (x) {
                        for (t = x._childClusters.length - 1; 0 <= t; t--) f = x._childClusters[t], d && d.contains(f._latlng) || (x._group._featureGroup.removeLayer(f), f.clusterShow && f.clusterShow())
                    })
                }, _recursively: function (x, e, a, d, f) {
                    var t, _, i = this._childClusters, s = this._zoom;
                    if (e <= s && (d && d(this), f && s === a && f(this)), s < e || s < a) for (t = i.length - 1; 0 <= t; t--) (_ = i[t])._boundsNeedUpdate && _._recalculateBounds(), x.intersects(_._bounds) && _._recursively(x, e, a, d, f)
                }, _isSingleParent: function () {
                    return 0 < this._childClusters.length && this._childClusters[0]._childCount === this._childCount
                }
            });
            L.Marker.include({
                clusterHide: function () {
                    var x = this.options.opacity;
                    return this.setOpacity(0), this.options.opacity = x, this
                }, clusterShow: function () {
                    return this.setOpacity(this.options.opacity)
                }
            }), L.DistanceGrid = function (x) {
                this._cellSize = x, this._sqCellSize = x * x, this._grid = {}, this._objectPoint = {}
            }, L.DistanceGrid.prototype = {
                addObject: function (x, e) {
                    var a = this._getCoord(e.x), d = this._getCoord(e.y), f = this._grid, d = f[d] = f[d] || {},
                        d = d[a] = d[a] || [], a = L.Util.stamp(x);
                    this._objectPoint[a] = e, d.push(x)
                }, updateObject: function (x, e) {
                    this.removeObject(x), this.addObject(x, e)
                }, removeObject: function (x, e) {
                    var a, d, f = this._getCoord(e.x), t = this._getCoord(e.y), e = this._grid, _ = e[t] = e[t] || {},
                        i = _[f] = _[f] || [];
                    for (delete this._objectPoint[L.Util.stamp(x)], a = 0, d = i.length; a < d; a++) if (i[a] === x) return i.splice(a, 1), 1 === d && delete _[f], !0
                }, eachObject: function (x, e) {
                    var a, d, f, t, _, i, s = this._grid;
                    for (a in s) for (d in _ = s[a]) for (f = 0, t = (i = _[d]).length; f < t; f++) x.call(e, i[f]) && (f--, t--)
                }, getNearObject: function (x) {
                    for (var e, a, d, f, t, _, i, s = this._getCoord(x.x), n = this._getCoord(x.y), h = this._objectPoint, r = this._sqCellSize, c = null, o = n - 1; o <= n + 1; o++) if (d = this._grid[o]) for (e = s - 1; e <= s + 1; e++) if (f = d[e]) for (a = 0, t = f.length; a < t; a++) _ = f[a], ((i = this._sqDist(h[L.Util.stamp(_)], x)) < r || i <= r && null === c) && (r = i, c = _);
                    return c
                }, _getCoord: function (x) {
                    var e = Math.floor(x / this._cellSize);
                    return isFinite(e) ? e : x
                }, _sqDist: function (x, e) {
                    var a = e.x - x.x, x = e.y - x.y;
                    return a * a + x * x
                }
            }, L.QuickHull = {
                getDistant: function (x, e) {
                    var a = e[1].lat - e[0].lat;
                    return (e[0].lng - e[1].lng) * (x.lat - e[0].lat) + a * (x.lng - e[0].lng)
                }, findMostDistantPointFromBaseLine: function (x, e) {
                    for (var a, d, f = 0, t = null, _ = [], i = e.length - 1; 0 <= i; i--) a = e[i], 0 < (d = this.getDistant(a, x)) && (_.push(a), f < d && (f = d, t = a));
                    return {maxPoint: t, newPoints: _}
                }, buildConvexHull: function (x, e) {
                    var a = [], e = this.findMostDistantPointFromBaseLine(x, e);
                    return e.maxPoint ? (a = a.concat(this.buildConvexHull([x[0], e.maxPoint], e.newPoints))).concat(this.buildConvexHull([e.maxPoint, x[1]], e.newPoints)) : [x[0]]
                }, getConvexHull: function (x) {
                    for (var e = !1, a = !1, d = !1, f = !1, t = null, _ = null, i = null, s = null, n = null, h = null, r = x.length - 1; 0 <= r; r--) {
                        var c = x[r];
                        (!1 === e || c.lat > e) && (e = (t = c).lat), (!1 === a || c.lat < a) && (a = (_ = c).lat), (!1 === d || c.lng > d) && (d = (i = c).lng), (!1 === f || c.lng < f) && (f = (s = c).lng)
                    }
                    return n = a !== e ? (h = _, t) : (h = s, i), [].concat(this.buildConvexHull([h, n], x), this.buildConvexHull([n, h], x))
                }
            }, L.MarkerCluster.include({
                getConvexHull: function () {
                    for (var x, e = this.getAllChildMarkers(), a = [], d = e.length - 1; 0 <= d; d--) x = e[d].getLatLng(), a.push(x);
                    return L.QuickHull.getConvexHull(a)
                }
            }), L.MarkerCluster.include({
                _2PI: 2 * Math.PI,
                _circleFootSeparation: 25,
                _circleStartAngle: 0,
                _spiralFootSeparation: 28,
                _spiralLengthStart: 11,
                _spiralLengthFactor: 5,
                _circleSpiralSwitchover: 9,
                spiderfy: function () {
                    var x, e;
                    this._group._spiderfied === this || this._group._inZoomAnimation || (x = this.getAllChildMarkers(null, !0), e = this._group._map.latLngToLayerPoint(this._latlng), this._group._unspiderfy(), this._group._spiderfied = this, e = x.length >= this._circleSpiralSwitchover ? this._generatePointsSpiral(x.length, e) : (e.y += 10, this._generatePointsCircle(x.length, e)), this._animationSpiderfy(x, e))
                },
                unspiderfy: function (x) {
                    this._group._inZoomAnimation || (this._animationUnspiderfy(x), this._group._spiderfied = null)
                },
                _generatePointsCircle: function (x, e) {
                    var a, d,
                        f = this._group.options.spiderfyDistanceMultiplier * this._circleFootSeparation * (2 + x) / this._2PI,
                        t = this._2PI / x, _ = [], f = Math.max(f, 35);
                    for (_.length = x, a = 0; a < x; a++) d = this._circleStartAngle + a * t, _[a] = new L.Point(e.x + f * Math.cos(d), e.y + f * Math.sin(d))._round();
                    return _
                },
                _generatePointsSpiral: function (x, e) {
                    for (var a = this._group.options.spiderfyDistanceMultiplier, d = a * this._spiralLengthStart, f = a * this._spiralFootSeparation, t = a * this._spiralLengthFactor * this._2PI, _ = 0, i = [], s = i.length = x; 0 <= s; s--) s < x && (i[s] = new L.Point(e.x + d * Math.cos(_), e.y + d * Math.sin(_))._round()), d += t / (_ += f / d + 5e-4 * s);
                    return i
                },
                _noanimationUnspiderfy: function () {
                    var x, e, a = this._group, d = a._map, f = a._featureGroup, t = this.getAllChildMarkers(null, !0);
                    for (a._ignoreMove = !0, this.setOpacity(1), e = t.length - 1; 0 <= e; e--) x = t[e], f.removeLayer(x), x._preSpiderfyLatlng && (x.setLatLng(x._preSpiderfyLatlng), delete x._preSpiderfyLatlng), x.setZIndexOffset && x.setZIndexOffset(0), x._spiderLeg && (d.removeLayer(x._spiderLeg), delete x._spiderLeg);
                    a.fire('unspiderfied', {cluster: this, markers: t}), a._ignoreMove = !1, a._spiderfied = null
                }
            }), L.MarkerClusterNonAnimated = L.MarkerCluster.extend({
                _animationSpiderfy: function (x, e) {
                    var a, d, f, t, _ = this._group, i = _._map, s = _._featureGroup,
                        n = this._group.options.spiderLegPolylineOptions;
                    for (_._ignoreMove = !0, a = 0; a < x.length; a++) t = i.layerPointToLatLng(e[a]), d = x[a], f = new L.Polyline([this._latlng, t], n), i.addLayer(f), d._spiderLeg = f, d._preSpiderfyLatlng = d._latlng, d.setLatLng(t), d.setZIndexOffset && d.setZIndexOffset(1e6), s.addLayer(d);
                    this.setOpacity(.3), _._ignoreMove = !1, _.fire('spiderfied', {cluster: this, markers: x})
                }, _animationUnspiderfy: function () {
                    this._noanimationUnspiderfy()
                }
            }), L.MarkerCluster.include({
                _animationSpiderfy: function (x, e) {
                    var a, d, f, t, _, i, s = this, n = this._group, h = n._map, r = n._featureGroup, c = this._latlng,
                        o = h.latLngToLayerPoint(c), l = L.Path.SVG,
                        u = L.extend({}, this._group.options.spiderLegPolylineOptions), b = u.opacity;
                    for (void 0 === b && (b = L.MarkerClusterGroup.prototype.options.spiderLegPolylineOptions.opacity), l ? (u.opacity = 0, u.className = (u.className || '') + ' leaflet-cluster-spider-leg') : u.opacity = b, n._ignoreMove = !0, a = 0; a < x.length; a++) d = x[a], i = h.layerPointToLatLng(e[a]), f = new L.Polyline([c, i], u), h.addLayer(f), d._spiderLeg = f, l && (_ = (t = f._path).getTotalLength() + .1, t.style.strokeDasharray = _, t.style.strokeDashoffset = _), d.setZIndexOffset && d.setZIndexOffset(1e6), d.clusterHide && d.clusterHide(), r.addLayer(d), d._setPos && d._setPos(o);
                    for (n._forceLayout(), n._animationStart(), a = x.length - 1; 0 <= a; a--) i = h.layerPointToLatLng(e[a]), (d = x[a])._preSpiderfyLatlng = d._latlng, d.setLatLng(i), d.clusterShow && d.clusterShow(), l && ((t = (f = d._spiderLeg)._path).style.strokeDashoffset = 0, f.setStyle({opacity: b}));
                    this.setOpacity(.3), n._ignoreMove = !1, setTimeout(function () {
                        n._animationEnd(), n.fire('spiderfied', {cluster: s, markers: x})
                    }, 200)
                }, _animationUnspiderfy: function (x) {
                    var e, a, d, f, t, _ = this, i = this._group, s = i._map, n = i._featureGroup,
                        h = x ? s._latLngToNewLayerPoint(this._latlng, x.zoom, x.center) : s.latLngToLayerPoint(this._latlng),
                        r = this.getAllChildMarkers(null, !0), c = L.Path.SVG;
                    for (i._ignoreMove = !0, i._animationStart(), this.setOpacity(1), a = r.length - 1; 0 <= a; a--) (e = r[a])._preSpiderfyLatlng && (e.closePopup(), e.setLatLng(e._preSpiderfyLatlng), delete e._preSpiderfyLatlng, t = !0, e._setPos && (e._setPos(h), t = !1), e.clusterHide && (e.clusterHide(), t = !1), t && n.removeLayer(e), c && (t = (f = (d = e._spiderLeg)._path).getTotalLength() + .1, f.style.strokeDashoffset = t, d.setStyle({opacity: 0})));
                    i._ignoreMove = !1, setTimeout(function () {
                        var x = 0;
                        for (a = r.length - 1; 0 <= a; a--) (e = r[a])._spiderLeg && x++;
                        for (a = r.length - 1; 0 <= a; a--) (e = r[a])._spiderLeg && (e.clusterShow && e.clusterShow(), e.setZIndexOffset && e.setZIndexOffset(0), 1 < x && n.removeLayer(e), s.removeLayer(e._spiderLeg), delete e._spiderLeg);
                        i._animationEnd(), i.fire('unspiderfied', {cluster: _, markers: r})
                    }, 200)
                }
            }), L.MarkerClusterGroup.include({
                _spiderfied: null, unspiderfy: function () {
                    this._unspiderfy.apply(this, arguments)
                }, _spiderfierOnAdd: function () {
                    this._map.on('click', this._unspiderfyWrapper, this), this._map.options.zoomAnimation && this._map.on('zoomstart', this._unspiderfyZoomStart, this), this._map.on('zoomend', this._noanimationUnspiderfy, this), L.Browser.touch || this._map.getRenderer(this)
                }, _spiderfierOnRemove: function () {
                    this._map.off('click', this._unspiderfyWrapper, this), this._map.off('zoomstart', this._unspiderfyZoomStart, this), this._map.off('zoomanim', this._unspiderfyZoomAnim, this), this._map.off('zoomend', this._noanimationUnspiderfy, this), this._noanimationUnspiderfy()
                }, _unspiderfyZoomStart: function () {
                    this._map && this._map.on('zoomanim', this._unspiderfyZoomAnim, this)
                }, _unspiderfyZoomAnim: function (x) {
                    L.DomUtil.hasClass(this._map._mapPane, 'leaflet-touching') || (this._map.off('zoomanim', this._unspiderfyZoomAnim, this), this._unspiderfy(x))
                }, _unspiderfyWrapper: function () {
                    this._unspiderfy()
                }, _unspiderfy: function (x) {
                    this._spiderfied && this._spiderfied.unspiderfy(x)
                }, _noanimationUnspiderfy: function () {
                    this._spiderfied && this._spiderfied._noanimationUnspiderfy()
                }, _unspiderfyLayer: function (x) {
                    x._spiderLeg && (this._featureGroup.removeLayer(x), x.clusterShow && x.clusterShow(), x.setZIndexOffset && x.setZIndexOffset(0), this._map.removeLayer(x._spiderLeg), delete x._spiderLeg)
                }
            }), L.MarkerClusterGroup.include({
                refreshClusters: function (x) {
                    return x ? x instanceof L.MarkerClusterGroup ? x = x._topClusterLevel.getAllChildMarkers() : x instanceof L.LayerGroup ? x = x._layers : x instanceof L.MarkerCluster ? x = x.getAllChildMarkers() : x instanceof L.Marker && (x = [x]) : x = this._topClusterLevel.getAllChildMarkers(), this._flagParentsIconsNeedUpdate(x), this._refreshClustersIcons(), this.options.singleMarkerMode && this._refreshSingleMarkerModeMarkers(x), this
                }, _flagParentsIconsNeedUpdate: function (x) {
                    var e, a;
                    for (e in x) for (a = x[e].__parent; a;) a._iconNeedsUpdate = !0, a = a.__parent
                }, _refreshSingleMarkerModeMarkers: function (x) {
                    var e, a;
                    for (e in x) a = x[e], this.hasLayer(a) && a.setIcon(this._overrideMarkerIcon(a))
                }
            }), L.Marker.include({
                refreshIconOptions: function (x, e) {
                    var a = this.options.icon;
                    return L.setOptions(a, x), this.setIcon(a), e && this.__parent && this.__parent._group.refreshClusters(this), this
                }
            }), x.MarkerClusterGroup = e, x.MarkerCluster = a
        }(e)
    }, function (x, e, a) {
    }, function (x, e, a) {
    }, function (x, e, a) {
    }, function (x, e, a) {
    }, function (x, e, a) {
        'use strict';
        a.r(e), a.d(e, 'mars', function () {
            return Fx
        }), a.d(e, 'widget', function () {
            return zx
        });
        var d = {};
        a.r(d), a.d(d, 'bd2gcj', function () {
            return o
        }), a.d(d, 'gcj2bd', function () {
            return l
        }), a.d(d, 'wgs2gcj', function () {
            return u
        }), a.d(d, 'gcj2wgs', function () {
            return b
        }), a.d(d, 'bd2wgs', function () {
            return m
        }), a.d(d, 'wgs2bd', function () {
            return p
        }), a.d(d, 'jwd2mct', function () {
            return y
        }), a.d(d, 'mct2jwd', function () {
            return v
        });
        var f = {};
        a.r(f), a.d(f, 'init', function () {
            return rx
        }), a.d(f, 'getDefWindowOptions', function () {
            return cx
        }), a.d(f, 'activate', function () {
            return lx
        }), a.d(f, 'getWidget', function () {
            return ux
        }), a.d(f, 'getClass', function () {
            return bx
        }), a.d(f, 'isActivate', function () {
            return mx
        }), a.d(f, 'disable', function () {
            return px
        }), a.d(f, 'disableAll', function () {
            return yx
        }), a.d(f, 'disableGroup', function () {
            return vx
        }), a.d(f, 'eachWidget', function () {
            return Zx
        }), a.d(f, 'bindClass', function () {
            return Xx
        }), a.d(f, 'removeDebugeBar', function () {
            return gx
        }), a.d(f, 'getCacheVersion', function () {
            return Rx
        }), a.d(f, 'getBasePath', function () {
            return wx
        }), a.d(f, 'destroy', function () {
            return Yx
        }), a(16), a(17);
        var t = a(0), D = a.n(t), _ = 52.35987755982988, i = 3.141592653589793, s = 6378245, n = .006693421622965943;

        function h(x, e) {
            var a = 2 * x - 100 + 3 * e + .2 * e * e + .1 * x * e + .2 * Math.sqrt(Math.abs(x));
            return a += 2 * (20 * Math.sin(6 * x * i) + 20 * Math.sin(2 * x * i)) / 3, (a += 2 * (20 * Math.sin(e * i) + 40 * Math.sin(e / 3 * i)) / 3) + 2 * (160 * Math.sin(e / 12 * i) + 320 * Math.sin(e * i / 30)) / 3
        }

        function r(x, e) {
            e = 300 + x + 2 * e + .1 * x * x + .1 * x * e + .1 * Math.sqrt(Math.abs(x));
            return e += 2 * (20 * Math.sin(6 * x * i) + 20 * Math.sin(2 * x * i)) / 3, (e += 2 * (20 * Math.sin(x * i) + 40 * Math.sin(x / 3 * i)) / 3) + 2 * (150 * Math.sin(x / 12 * i) + 300 * Math.sin(x / 30 * i)) / 3
        }

        function c(x, e) {
            return x < 72.004 || 137.8347 < x || e < .8293 || 55.8271 < e
        }

        function o(x) {
            var e = 52.35987755982988, a = Number(x[0]) - .0065, d = Number(x[1]) - .006,
                x = Math.sqrt(a * a + d * d) - 2e-5 * Math.sin(d * e), a = Math.atan2(d, a) - 3e-6 * Math.cos(a * e),
                e = x * Math.cos(a), a = x * Math.sin(a);
            return [e = Number(e.toFixed(6)), a = Number(a.toFixed(6))]
        }

        function l(x) {
            var e = Number(x[0]), a = Number(x[1]), x = Math.sqrt(e * e + a * a) + 2e-5 * Math.sin(a * _),
                a = Math.atan2(a, e) + 3e-6 * Math.cos(e * _), e = x * Math.cos(a) + .0065, a = x * Math.sin(a) + .006;
            return [e = Number(e.toFixed(6)), a = Number(a.toFixed(6))]
        }

        function u(x) {
            var e = Number(x[0]), a = Number(x[1]);
            if (c(e, a)) return [e, a];
            var d = h(e - 105, a - 35), f = r(e - 105, a - 35), t = a / 180 * i, _ = Math.sin(t), _ = 1 - n * _ * _,
                x = Math.sqrt(_), d = a + (d = 180 * d / (s * (1 - n) / (_ * x) * i)),
                f = e + (f = 180 * f / (s / x * Math.cos(t) * i));
            return [f = Number(f.toFixed(6)), d = Number(d.toFixed(6))]
        }

        function b(x) {
            var e = Number(x[0]), a = Number(x[1]);
            if (c(e, a)) return [e, a];
            var d = h(e - 105, a - 35), f = r(e - 105, a - 35), t = a / 180 * i, _ = Math.sin(t), _ = 1 - n * _ * _,
                x = Math.sqrt(_), d = 180 * d / (s * (1 - n) / (_ * x) * i),
                f = 2 * e - (e + (f = 180 * f / (s / x * Math.cos(t) * i))), d = 2 * a - (a + d);
            return [f = Number(f.toFixed(6)), d = Number(d.toFixed(6))]
        }

        function m(x) {
            return b(o(x))
        }

        function p(x) {
            return l(u(x))
        }

        function y(x) {
            var e = Number(x[0]), x = Number(x[1]), e = 20037508.34 * e / 180,
                x = 20037508.34 * (x = Math.log(Math.tan((90 + x) * i / 360)) / (i / 180)) / 180;
            return [e = Number(e.toFixed(2)), x = Number(x.toFixed(2))]
        }

        function v(x) {
            var e = Number(x[0]) / 20037508.34 * 180, x = Number(x[1]) / 20037508.34 * 180,
                x = 180 / i * (2 * Math.atan(Math.exp(x * i / 180)) - i / 2);
            return [e = Number(e.toFixed(6)), x = Number(x.toFixed(6))]
        }

        var P = a(1), q = a(2);
        D.a.Map.include({
            setConvertType: function (x) {
                this.options.pointconvertType = x
            }, getConvertType: function () {
                return this.options.pointconvertType
            }, hasConvert: function () {
                var x = this.options.pointconvertType;
                return 'gcj' == x || 'bd' == x
            }, convert2map: function (x) {
                var e, a;
                return x = x.lng && x.lat ? (e = x.lng, x.lat) : (e = x[1], x[0]), 'gcj' == this.options.pointconvertType ? (e = (a = u([e, x]))[0], x = a[1]) : 'bd' == this.options.pointconvertType && (e = (a = p([e, x]))[0], x = a[1]), [x, e]
            }, convert2wgs: function (x) {
                var e, a;
                return x = x.lng && x.lat ? (e = x.lng, x.lat) : (e = x[1], x[0]), 'gcj' == this.options.pointconvertType ? (e = (a = b([e, x]))[0], x = a[1]) : 'bd' == this.options.pointconvertType && (e = (a = m([e, x]))[0], x = a[1]), [x, e]
            }, centerAt: function (x, e) {
                var a, d = this.getZoom();
                return this.gisdata && this.gisdata.config && this.gisdata.config.centerAutoLevel && (d < (a = this.gisdata.config.centerAutoLevel) && this.getMaxZoom() >= a && (d = a)), this.stop(), this.setView(x, d, e), this
            }, centerAtByWgs: function (x, e) {
                return x = this.convert2map(x), this.centerAt(x, e), this
            }, centerAtLayer: function (x) {
                return x instanceof D.a.Marker || x instanceof D.a.CircleMarker ? this.centerAt(x.getLatLng()) : x instanceof D.a.Circle || x instanceof D.a.Rectangle || x instanceof D.a.Polygon || x instanceof D.a.Polyline || x instanceof D.a.LayerGroup || x instanceof D.a.FeatureGroup ? this.fitBounds(x.getBounds()) : x.getLatLng ? this.centerAt(x.getLatLng()) : x.getBounds && this.fitBounds(x.getBounds()), this
            }, goHomeExtent: function () {
                if (null != this.gisdata && null != this.gisdata.config && null != this.gisdata.config.center) return this.setView(this.gisdata.config.center, this.gisdata.config.zoom), this
            }, changeBaseMap: function (x) {
                if (null != this.gisdata && null != this.gisdata.config && null != this.gisdata.config.basemaps) {
                    for (var e, a, d = 0; d < this.gisdata.config.basemaps.length; d++) {
                        var f = this.gisdata.config.basemaps[d];
                        null != f.name && '' != f.name && null != f._layer && (null == a && (a = f._layer), x != d && f.name != x || (e = f._layer), this.hasLayer(f._layer) && this.removeLayer(f._layer))
                    }
                    return null != e ? (Object(P.a)('.leaflet-container').css({background: e.config.background || '#fff'}), this.addLayer(e)) : null != a && (Object(P.a)('.leaflet-container').css({background: a.config.background || '#fff'}), this.addLayer(a)), this
                }
            }, getLayer: function (x, e) {
                e = this.getLayers(x, e);
                return 0 == e.length ? null : e[0]
            }, getLayers: function (x, e) {
                var a = [];
                null == e && (e = 'name');
                var d = this.gisdata.config.basemaps;
                if (d && 0 < d.length) for (var f = 0; f < d.length; f++) {
                    var t = d[f];
                    null != t && t[e] == x && a.push(t._layer)
                }
                if ((d = this.gisdata.config.operationallayers) && 0 < d.length) for (var _ = 0; _ < d.length; _++) {
                    var i = d[_];
                    null != i && i[e] == x && a.push(i._layer)
                }
                return a
            }, getConfig: function () {
                return q.clone(this.gisdata.config)
            }
        });
        var Z = D.a.Marker.prototype._initIcon, M = D.a.Marker.prototype._setPos,
            V = 'msTransform' === D.a.DomUtil.TRANSFORM;
        D.a.Marker.addInitHook(function () {
            var x = (x = this.options.icon && this.options.icon.options && this.options.icon.options.iconAnchor) && x[0] + 'px ' + x[1] + 'px';
            this.options.rotationOrigin = this.options.rotationOrigin || x || 'center bottom', this.options.rotationAngle = this.options.rotationAngle || 0, this.on('drag', function (x) {
                x.target._applyRotation()
            })
        }), D.a.Marker.include({
            _initIcon: function () {
                Z.call(this)
            }, _setPos: function (x) {
                M.call(this, x), this._applyRotation()
            }, _applyRotation: function () {
                this.options.rotationAngle && (this._icon.style[D.a.DomUtil.TRANSFORM + 'Origin'] = this.options.rotationOrigin, V ? this._icon.style[D.a.DomUtil.TRANSFORM] = 'rotate(' + this.options.rotationAngle + 'deg)' : this._icon.style[D.a.DomUtil.TRANSFORM] += ' rotateZ(' + this.options.rotationAngle + 'deg)')
            }, setRotationAngle: function (x) {
                return this.options.rotationAngle = x, this.update(), this
            }, setRotationOrigin: function (x) {
                return this.options.rotationOrigin = x, this.update(), this
            }
        }), D.a.TileLayer.prototype.getTileUrl = function (x) {
            var e = {
                r: D.a.Browser.retina ? '@2x' : '',
                s: this._getSubdomain(x),
                x: x.x,
                y: x.y,
                z: this._getZoomForUrl()
            };
            return this._map && !this._map.options.crs.infinite && (x = this._globalTileRange.max.y - x.y, this.options.tms && (e.y = x), e['-y'] = x), this.options.xOffset && (e.x = e.x + Number(this.options.xOffset)), this.options.yOffset && (e.y = e.y + Number(this.options.yOffset)), this.options.zOffset && (e.z = e.z + Number(this.options.zOffset)), D.a.Util.template(this._url || '', D.a.extend(e, this.options))
        }, D.a.GridLayer.include({
            transformCenter: function (x) {
                if (null == x || !this.options) return x;
                var e = this._map.options.pointconvertType || this._map.options.coordType || 'wgs',
                    a = this.options.coordType || e;
                if (e == a) return x;
                a = e + '2' + a;
                if (!d[a]) return x;
                x = d[a]([x.lng, x.lat]);
                return {lng: x[0], lat: x[1]}
            }, _setZoomTransform: function (x, e, a) {
                var d = this.transformCenter(e), e = this._map.getZoomScale(a, x.zoom),
                    a = x.origin.multiplyBy(e).subtract(this._map._getNewPixelOrigin(d, a)).round();
                D.a.Browser.any3d ? D.a.DomUtil.setTransform(x.el, a, e) : D.a.DomUtil.setPosition(x.el, a)
            }, _getTiledPixelBounds: function (x) {
                var e = this.transformCenter(x), a = this._map,
                    x = a._animatingZoom ? Math.max(a._animateToZoom, a.getZoom()) : a.getZoom(),
                    x = a.getZoomScale(x, this._tileZoom), e = a.project(e, this._tileZoom).floor(),
                    x = a.getSize().divideBy(2 * x);
                return new D.a.Bounds(e.subtract(x), e.add(x))
            }
        }), a(18), a(19), a(20), a(21), D.a.Map.mergeOptions({contextmenuItems: []}), D.a.Map.ContextMenu = D.a.Handler.extend({
            _touchstart: D.a.Browser.msPointer ? 'MSPointerDown' : D.a.Browser.pointer ? 'pointerdown' : 'touchstart',
            statics: {BASE_CLS: 'leaflet-contextmenu'},
            initialize: function (x) {
                D.a.Handler.prototype.initialize.call(this, x), this._items = [], this._visible = !1;
                var e = this._container = D.a.DomUtil.create('div', D.a.Map.ContextMenu.BASE_CLS, x._container);
                e.style.zIndex = 1e4, e.style.position = 'absolute', x.options.contextmenuWidth && (e.style.width = x.options.contextmenuWidth + 'px'), this._createItems(), D.a.DomEvent.on(e, 'click', D.a.DomEvent.stop).on(e, 'mousedown', D.a.DomEvent.stop).on(e, 'dblclick', D.a.DomEvent.stop).on(e, 'contextmenu', D.a.DomEvent.stop)
            },
            addHooks: function () {
                var x = this._map.getContainer();
                D.a.DomEvent.on(x, 'mouseleave', this._hide, this).on(document, 'keydown', this._onKeyDown, this), D.a.Browser.touch && D.a.DomEvent.on(document, this._touchstart, this._hide, this), this._map.on({
                    contextmenu: this._show,
                    mousedown: this._hide,
                    movestart: this._hide,
                    zoomstart: this._hide
                }, this)
            },
            removeHooks: function () {
                var x = this._map.getContainer();
                D.a.DomEvent.off(x, 'mouseleave', this._hide, this).off(document, 'keydown', this._onKeyDown, this), D.a.Browser.touch && D.a.DomEvent.off(document, this._touchstart, this._hide, this), this._map.off({
                    contextmenu: this._show,
                    mousedown: this._hide,
                    movestart: this._hide,
                    zoomstart: this._hide
                }, this)
            },
            showAt: function (x, e) {
                x instanceof D.a.LatLng && (x = this._map.latLngToContainerPoint(x)), this._showAtPoint(x, e)
            },
            hide: function () {
                this._hide()
            },
            addItem: function (x) {
                return this.insertItem(x)
            },
            insertItem: function (x, e) {
                e = void 0 !== e ? e : this._items.length;
                x = this._createItem(this._container, x, e);
                return this._items.push(x), this._sizeChanged = !0, this._map.fire('contextmenu.additem', {
                    contextmenu: this,
                    el: x.el,
                    index: e
                }), x.el
            },
            removeItem: function (x) {
                var e = this._container;
                return (x = !isNaN(x) ? e.children[x] : x) ? (this._removeItem(D.a.Util.stamp(x)), this._sizeChanged = !0, this._map.fire('contextmenu.removeitem', {
                    contextmenu: this,
                    el: x
                }), x) : null
            },
            removeAllItems: function () {
                for (var x, e = this._container.children; e.length;) x = e[0], this._removeItem(D.a.Util.stamp(x));
                return e
            },
            hideAllItems: function () {
                for (var x = 0, e = this._items.length; x < e; x++) this._items[x].el.style.display = 'none'
            },
            showAllItems: function () {
                for (var x = 0, e = this._items.length; x < e; x++) this._items[x].el.style.display = ''
            },
            setDisabled: function (x, e) {
                var a = this._container, d = D.a.Map.ContextMenu.BASE_CLS + '-item';
                (x = !isNaN(x) ? a.children[x] : x) && D.a.DomUtil.hasClass(x, d) && (e ? (D.a.DomUtil.addClass(x, d + '-disabled'), this._map.fire('contextmenu.disableitem', {
                    contextmenu: this,
                    el: x
                })) : (D.a.DomUtil.removeClass(x, d + '-disabled'), this._map.fire('contextmenu.enableitem', {
                    contextmenu: this,
                    el: x
                })))
            },
            isVisible: function () {
                return this._visible
            },
            _createItems: function () {
                for (var x = this._map.options.contextmenuItems, e = 0, a = x.length; e < a; e++) this._items.push(this._createItem(this._container, x[e]))
            },
            _createItem: function (x, e, a) {
                if (e.separator || '-' === e) return this._createSeparator(x, a);
                var d = D.a.Map.ContextMenu.BASE_CLS + '-item', f = e.disabled ? d + ' ' + d + '-disabled' : d,
                    t = this._insertElementAt('a', f, x, a),
                    d = this._createEventHandler(t, e.callback, e.context, e.hideOnSelect), f = this._getIcon(e),
                    x = this._getIconCls(e), a = '';
                return f ? a = '<img class=\'' + D.a.Map.ContextMenu.BASE_CLS + '-icon" src="' + f + '"/>' : x && (a = '<span class="' + D.a.Map.ContextMenu.BASE_CLS + '-icon ' + x + '\'></span>'), t.innerHTML = a + e.text, t.href = '#', D.a.DomEvent.on(t, 'mouseover', this._onItemMouseOver, this).on(t, 'mouseout', this._onItemMouseOut, this).on(t, 'mousedown', D.a.DomEvent.stopPropagation).on(t, 'click', d), D.a.Browser.touch && D.a.DomEvent.on(t, this._touchstart, D.a.DomEvent.stopPropagation), D.a.Browser.pointer || D.a.DomEvent.on(t, 'click', this._onItemMouseOut, this), {
                    id: D.a.Util.stamp(t),
                    el: t,
                    callback: d
                }
            },
            _removeItem: function (x) {
                for (var e, a, d, f = 0, t = this._items.length; f < t; f++) if ((e = this._items[f]).id === x) return a = e.el, (d = e.callback) && (D.a.DomEvent.off(a, 'mouseover', this._onItemMouseOver, this).off(a, 'mouseover', this._onItemMouseOut, this).off(a, 'mousedown', D.a.DomEvent.stopPropagation).off(a, 'click', d), D.a.Browser.touch && D.a.DomEvent.off(a, this._touchstart, D.a.DomEvent.stopPropagation), D.a.Browser.pointer || D.a.DomEvent.on(a, 'click', this._onItemMouseOut, this)), this._container.removeChild(a), this._items.splice(f, 1), e;
                return null
            },
            _createSeparator: function (x, e) {
                e = this._insertElementAt('div', D.a.Map.ContextMenu.BASE_CLS + '-separator', x, e);
                return {id: D.a.Util.stamp(e), el: e}
            },
            _createEventHandler: function (e, a, d, f) {
                var t = this, _ = this._map, i = D.a.Map.ContextMenu.BASE_CLS + '-item-disabled';
                return f = void 0 === f || f, function (x) {
                    D.a.DomUtil.hasClass(e, i) || (f && t._hide(), a && a.call(d || _, t._showLocation), t._map.fire('contextmenu.select', {
                        contextmenu: t,
                        el: e
                    }))
                }
            },
            _insertElementAt: function (x, e, a, d) {
                var f, x = document.createElement(x);
                return x.className = e, (f = void 0 !== d ? a.children[d] : f) ? a.insertBefore(x, f) : a.appendChild(x), x
            },
            _show: function (x) {
                this._showAtPoint(x.containerPoint, x)
            },
            _showAtPoint: function (x, e) {
                var a, d, f;
                this._items.length && (a = (f = this._map).containerPointToLayerPoint(x), d = f.layerPointToLatLng(a), f = D.a.extend(e || {}, {contextmenu: this}), this._showLocation = {
                    latlng: d,
                    layerPoint: a,
                    containerPoint: x
                }, e && e.relatedTarget && (this._showLocation.relatedTarget = e.relatedTarget), this._setPosition(x), this._visible || (this._container.style.display = 'block', this._visible = !0), this._map.fire('contextmenu.show', f))
            },
            _hide: function () {
                this._visible && (this._visible = !1, this._container.style.display = 'none', this._map.fire('contextmenu.hide', {contextmenu: this}))
            },
            _getIcon: function (x) {
                return D.a.Browser.retina && x.retinaIcon || x.icon
            },
            _getIconCls: function (x) {
                return D.a.Browser.retina && x.retinaIconCls || x.iconCls
            },
            _setPosition: function (x) {
                var e, a = this._map.getSize(), d = this._container, f = this._getElementSize(d);
                this._map.options.contextmenuAnchor && (e = D.a.point(this._map.options.contextmenuAnchor), x = x.add(e)), (d._leaflet_pos = x).x + f.x > a.x ? (d.style.left = 'auto', d.style.right = Math.min(Math.max(a.x - x.x, 0), a.x - f.x - 1) + 'px') : (d.style.left = Math.max(x.x, 0) + 'px', d.style.right = 'auto'), x.y + f.y > a.y ? (d.style.top = 'auto', d.style.bottom = Math.min(Math.max(a.y - x.y, 0), a.y - f.y - 1) + 'px') : (d.style.top = Math.max(x.y, 0) + 'px', d.style.bottom = 'auto')
            },
            _getElementSize: function (x) {
                var e = this._size, a = x.style.display;
                return e && !this._sizeChanged || (e = {}, x.style.left = '-999999px', x.style.right = 'auto', x.style.display = 'block', e.x = x.offsetWidth, e.y = x.offsetHeight, x.style.left = 'auto', x.style.display = a, this._sizeChanged = !1), e
            },
            _onKeyDown: function (x) {
                27 === x.keyCode && this._hide()
            },
            _onItemMouseOver: function (x) {
                D.a.DomUtil.addClass(x.target || x.srcElement, 'over')
            },
            _onItemMouseOut: function (x) {
                D.a.DomUtil.removeClass(x.target || x.srcElement, 'over')
            }
        }), D.a.Map.addInitHook('addHandler', 'contextmenu', D.a.Map.ContextMenu), D.a.Mixin.ContextMenu = {
            bindContextMenu: function (x) {
                return D.a.setOptions(this, x), this._initContextMenu(), this
            }, unbindContextMenu: function () {
                return this.off('contextmenu', this._showContextMenu, this), this
            }, addContextMenuItem: function (x) {
                this.options.contextmenuItems.push(x)
            }, removeContextMenuItemWithIndex: function (x) {
                for (var e = [], a = 0; a < this.options.contextmenuItems.length; a++) this.options.contextmenuItems[a].index == x && e.push(a);
                for (var d = e.pop(); void 0 !== d;) this.options.contextmenuItems.splice(d, 1), d = e.pop()
            }, replaceContextMenuItem: function (x) {
                this.removeContextMenuItemWithIndex(x.index), this.addContextMenuItem(x)
            }, _initContextMenu: function () {
                this._items = [], this.on('contextmenu', this._showContextMenu, this)
            }, _showContextMenu: function (x) {
                var e, a, d, f, t, _ = this;
                if (this._map.contextmenu) {
                    for (a = D.a.extend({relatedTarget: this}, x), d = this._map.mouseEventToContainerPoint(x.originalEvent), this.options.contextmenuInheritItems || this._map.contextmenu.hideAllItems(), f = 0, t = this.options.contextmenuItems.length; f < t; f++) e = this.options.contextmenuItems[f], this._items.push(this._map.contextmenu.insertItem(e, e.index));
                    this._map.once('contextmenu.hide', this._hideContextMenu, this), setTimeout(function () {
                        _._map.contextmenu.showAt(d, a)
                    }, 50)
                }
            }, _hideContextMenu: function () {
                for (var x = 0, e = this._items.length; x < e; x++) this._map.contextmenu.removeItem(this._items[x]);
                this._items.length = 0, this.options.contextmenuInheritItems || this._map.contextmenu.showAllItems()
            }
        };
        for (var G, W = [D.a.Marker, D.a.Path], X = {
            contextmenu: !1,
            contextmenuItems: [],
            contextmenuInheritItems: !0
        }, g = 0, R = W.length; g < R; g++) (G = W[g]).prototype.options ? G.mergeOptions(X) : G.prototype.options = X, G.addInitHook(function () {
            this.options.contextmenu && this._initContextMenu()
        }), G.include(D.a.Mixin.ContextMenu);
        var w, Y = a(4), K = a.n(Y);
        a(22), D.a.drawVersion = '1.0.4', D.a.Draw = {}, D.a.drawLocal = {
            draw: {
                toolbar: {
                    actions: {title: 'Cancel drawing', text: 'Cancel'},
                    finish: {title: 'Finish drawing', text: 'Finish'},
                    undo: {title: 'Delete last point drawn', text: 'Delete last point'},
                    buttons: {
                        polyline: 'Draw a polyline',
                        polygon: 'Draw a polygon',
                        rectangle: 'Draw a rectangle',
                        circle: 'Draw a circle',
                        marker: 'Draw a marker',
                        circlemarker: 'Draw a circlemarker'
                    }
                },
                handlers: {
                    circle: {tooltip: {start: 'Click and drag to draw circle.'}, radius: 'Radius'},
                    circlemarker: {tooltip: {start: 'Click map to place circle marker.'}},
                    marker: {tooltip: {start: 'Click map to place marker.'}},
                    polygon: {
                        tooltip: {
                            start: 'Click to start drawing shape.',
                            cont: 'Click to continue drawing shape.',
                            end: 'Click first point to close this shape.'
                        }
                    },
                    polyline: {
                        error: '<strong>Error:</strong> shape edges cannot cross!',
                        tooltip: {
                            start: 'Click to start drawing line.',
                            cont: 'Click to continue drawing line.',
                            end: 'Click last point to finish line.'
                        }
                    },
                    rectangle: {tooltip: {start: 'Click and drag to draw rectangle.'}},
                    simpleshape: {tooltip: {end: 'Release mouse to finish drawing.'}}
                }
            },
            edit: {
                toolbar: {
                    actions: {
                        save: {title: 'Save changes', text: 'Save'},
                        cancel: {title: 'Cancel editing, discards all changes', text: 'Cancel'},
                        clearAll: {title: 'Clear all layers', text: 'Clear All'}
                    },
                    buttons: {
                        edit: 'Edit layers',
                        editDisabled: 'No layers to edit',
                        remove: 'Delete layers',
                        removeDisabled: 'No layers to delete'
                    }
                },
                handlers: {
                    edit: {
                        tooltip: {
                            text: 'Drag handles or markers to edit features.',
                            subtext: 'Click cancel to undo changes.'
                        }
                    }, remove: {tooltip: {text: 'Click on a feature to remove.'}}
                }
            }
        }, D.a.Draw.Event = {}, D.a.Draw.Event.CREATED = 'draw:created', D.a.Draw.Event.EDITED = 'draw:edited', D.a.Draw.Event.DELETED = 'draw:deleted', D.a.Draw.Event.DRAWSTART = 'draw:drawstart', D.a.Draw.Event.DRAWSTOP = 'draw:drawstop', D.a.Draw.Event.DRAWVERTEX = 'draw:drawvertex', D.a.Draw.Event.EDITSTART = 'draw:editstart', D.a.Draw.Event.EDITMOVE = 'draw:editmove', D.a.Draw.Event.EDITRESIZE = 'draw:editresize', D.a.Draw.Event.EDITVERTEX = 'draw:editvertex', D.a.Draw.Event.EDITSTOP = 'draw:editstop', D.a.Draw.Event.DELETESTART = 'draw:deletestart', D.a.Draw.Event.DELETESTOP = 'draw:deletestop', D.a.Draw.Event.TOOLBAROPENED = 'draw:toolbaropened', D.a.Draw.Event.TOOLBARCLOSED = 'draw:toolbarclosed', D.a.Draw.Event.MARKERCONTEXT = 'draw:markercontext', D.a.Draw = D.a.Draw || {}, D.a.Draw.Feature = D.a.Handler.extend({
            initialize: function (x, e) {
                this._map = x, this._container = x._container, this._overlayPane = x._panes.overlayPane, this._popupPane = x._panes.popupPane, e && e.shapeOptions && (e.shapeOptions = D.a.Util.extend({}, this.options.shapeOptions, e.shapeOptions)), D.a.setOptions(this, e);
                e = D.a.version.split('.');
                1 === parseInt(e[0], 10) && 2 <= parseInt(e[1], 10) ? D.a.Draw.Feature.include(D.a.Evented.prototype) : D.a.Draw.Feature.include(D.a.Mixin.Events)
            }, enable: function () {
                this._enabled || (D.a.Handler.prototype.enable.call(this), this.fire('enabled', {handler: this.type}), this._map.fire(D.a.Draw.Event.DRAWSTART, {layerType: this.type}))
            }, disable: function () {
                this._enabled && (D.a.Handler.prototype.disable.call(this), this._map.fire(D.a.Draw.Event.DRAWSTOP, {layerType: this.type}), this.fire('disabled', {handler: this.type}))
            }, addHooks: function () {
                var x = this._map;
                x && (D.a.DomUtil.disableTextSelection(), x.getContainer().focus(), this._tooltip = new D.a.Draw.Tooltip(this._map), D.a.DomEvent.on(this._container, 'keyup', this._cancelDrawing, this))
            }, removeHooks: function () {
                this._map && (D.a.DomUtil.enableTextSelection(), this._tooltip.dispose(), this._tooltip = null, D.a.DomEvent.off(this._container, 'keyup', this._cancelDrawing, this))
            }, setOptions: function (x) {
                D.a.setOptions(this, x)
            }, _fireCreatedEvent: function (x) {
                this._map.fire(D.a.Draw.Event.CREATED, {layer: x, layerType: this.type})
            }, _cancelDrawing: function (x) {
                27 === x.keyCode && (this._map.fire('draw:canceled', {layerType: this.type}), this.disable())
            }
        }), D.a.Draw.Polyline = D.a.Draw.Feature.extend({
            statics: {TYPE: 'polyline'},
            Poly: D.a.Polyline,
            options: {
                allowIntersection: !0,
                repeatMode: !1,
                drawError: {color: '#b00b00', timeout: 2500},
                icon: new D.a.DivIcon({
                    iconSize: new D.a.Point(8, 8),
                    className: 'leaflet-div-icon leaflet-editing-icon'
                }),
                touchIcon: new D.a.DivIcon({
                    iconSize: new D.a.Point(15, 15),
                    className: 'leaflet-div-icon leaflet-editing-icon leaflet-touch-icon'
                }),
                guidelineDistance: 20,
                maxGuideLineLength: 4e3,
                shapeOptions: {stroke: !0, color: '#3388ff', weight: 4, opacity: .5, fill: !1, clickable: !0},
                metric: !0,
                feet: !0,
                nautic: !1,
                showLength: !0,
                zIndexOffset: 2e3,
                factor: 1,
                maxPoints: 0
            },
            initialize: function (x, e) {
                D.a.Browser.touch && (this.options.icon = this.options.touchIcon), this.options.drawError.message = D.a.drawLocal.draw.handlers.polyline.error, e && e.drawError && (e.drawError = D.a.Util.extend({}, this.options.drawError, e.drawError)), this.type = D.a.Draw.Polyline.TYPE, D.a.Draw.Feature.prototype.initialize.call(this, x, e)
            },
            addHooks: function () {
                D.a.Draw.Feature.prototype.addHooks.call(this), this._map && (this._markers = [], this._markerGroup = new D.a.LayerGroup, this._map.addLayer(this._markerGroup), this._poly = new D.a.Polyline([], this.options.shapeOptions), this._tooltip.updateContent(this._getTooltipText()), this._mouseMarker || (this._mouseMarker = D.a.marker(this._map.getCenter(), {
                    icon: D.a.divIcon({
                        className: 'leaflet-mouse-marker',
                        iconAnchor: [20, 20],
                        iconSize: [40, 40]
                    }), opacity: 0, zIndexOffset: this.options.zIndexOffset
                })), this._mouseMarker.on('mouseout', this._onMouseOut, this).on('mousemove', this._onMouseMove, this).on('mousedown', this._onMouseDown, this).on('mouseup', this._onMouseUp, this).addTo(this._map), this._map.on('mouseup', this._onMouseUp, this).on('mousemove', this._onMouseMove, this).on('zoomlevelschange', this._onZoomEnd, this).on('touchstart', this._onTouch, this).on('zoomend', this._onZoomEnd, this).on('dblclick', this._onDblclickHndler, this))
            },
            removeHooks: function () {
                D.a.Draw.Feature.prototype.removeHooks.call(this), this._clearHideErrorTimeout(), this._cleanUpShape(), this._map.removeLayer(this._markerGroup), delete this._markerGroup, delete this._markers, this._map.removeLayer(this._poly), delete this._poly, this._mouseMarker.off('mousedown', this._onMouseDown, this).off('mouseout', this._onMouseOut, this).off('mouseup', this._onMouseUp, this).off('mousemove', this._onMouseMove, this), this._map.removeLayer(this._mouseMarker), delete this._mouseMarker, this._clearGuides(), this._map.off('mouseup', this._onMouseUp, this).off('mousemove', this._onMouseMove, this).off('zoomlevelschange', this._onZoomEnd, this).off('zoomend', this._onZoomEnd, this).off('touchstart', this._onTouch, this).off('click', this._onTouch, this).off('dblclick', this._onDblclickHndler, this)
            },
            _onDblclickHndler: function (x) {
                D.a.DomEvent.stopPropagation(x), this._finishShape()
            },
            deleteLastVertex: function () {
                var x, e, a, d;
                this._markers.length <= 1 || (x = this._markers.pop(), d = (a = (e = this._poly).getLatLngs()).splice(-1, 1)[0], this._poly.setLatLngs(a), this._markerGroup.removeLayer(x), e.getLatLngs().length < 2 && this._map.removeLayer(e), this._vertexChanged(d, !1))
            },
            addVertex: function (x) {
                2 <= this._markers.length && !this.options.allowIntersection && this._poly.newLatLngIntersects(x) ? this._showErrorTooltip() : (this._errorShown && this._hideErrorTooltip(), this._markers.push(this._createMarker(x)), this._poly.addLatLng(x), 2 === this._poly.getLatLngs().length && this._map.addLayer(this._poly), this._vertexChanged(x, !0))
            },
            completeShape: function () {
                this._markers.length <= 1 || !this._shapeIsValid() || (this._fireCreatedEvent(), this.disable(), this.options.repeatMode && this.enable())
            },
            _finishShape: function (x) {
                var e = this._poly._defaultShape ? this._poly._defaultShape() : this._poly.getLatLngs(),
                    e = this._poly.newLatLngIntersects(e[e.length - 1]);
                !this.options.allowIntersection && e || !this._shapeIsValid() ? this._showErrorTooltip() : (this._fireCreatedEvent(), this.disable(), this.options.repeatMode && this.enable())
            },
            _shapeIsValid: function () {
                return !0
            },
            _onZoomEnd: function () {
                null !== this._markers && this._updateGuide()
            },
            _onMouseMove: function (x) {
                var e = this._map.mouseEventToLayerPoint(x.originalEvent), a = this._map.layerPointToLatLng(e);
                this._currentLatLng = a, this._updateTooltip(a), this._updateGuide(e), this._mouseMarker.setLatLng(a), D.a.DomEvent.preventDefault(x.originalEvent)
            },
            _vertexChanged: function (x, e) {
                this._map.fire(D.a.Draw.Event.DRAWVERTEX, {layers: this._markerGroup}), this._updateFinishHandler(), this._updateRunningMeasure(x, e), this._clearGuides(), this._updateTooltip()
            },
            _onMouseDown: function (x) {
                var e;
                this._clickHandled || this._touchHandled || this._disableMarkers || (this._onMouseMove(x), this._clickHandled = !0, this._disableNewMarkers(), x = (e = x.originalEvent).clientX, e = e.clientY, this._startPoint.call(this, x, e))
            },
            _startPoint: function (x, e) {
                this._mouseDownOrigin = D.a.point(x, e)
            },
            _onMouseUp: function (x) {
                var e = x.originalEvent, a = e.clientX, e = e.clientY;
                this._endPoint.call(this, a, e, x), this._clickHandled = null
            },
            _endPoint: function (x, e, a) {
                this._mouseDownOrigin && (x = D.a.point(x, e).distanceTo(this._mouseDownOrigin), e = this._calculateFinishDistance(a.latlng), 1 < this.options.maxPoints && this.options.maxPoints == this._markers.length + 1 ? (this.addVertex(a.latlng), this._finishShape()) : e < 10 && D.a.Browser.touch ? this._finishShape() : Math.abs(x) < 9 * (window.devicePixelRatio || 1) && this.addVertex(a.latlng), this._enableNewMarkers()), this._mouseDownOrigin = null
            },
            _onTouch: function (x) {
                var e, a = x.originalEvent;
                !a.touches || !a.touches[0] || this._clickHandled || this._touchHandled || this._disableMarkers || (e = a.touches[0].clientX, a = a.touches[0].clientY, this._disableNewMarkers(), this._touchHandled = !0, this._startPoint.call(this, e, a), this._endPoint.call(this, e, a, x), this._touchHandled = null), this._clickHandled = null
            },
            _onMouseOut: function () {
                this._tooltip && this._tooltip._onMouseOut.call(this._tooltip)
            },
            _calculateFinishDistance: function (x) {
                if (0 < this._markers.length) {
                    if (this.type === D.a.Draw.Polyline.TYPE) a = this._markers[this._markers.length - 1]; else {
                        if (this.type !== D.a.Draw.Polygon.TYPE) return 1 / 0;
                        a = this._markers[0]
                    }
                    var e = this._map.latLngToContainerPoint(a.getLatLng()),
                        a = new D.a.Marker(x, {icon: this.options.icon, zIndexOffset: 2 * this.options.zIndexOffset}),
                        a = this._map.latLngToContainerPoint(a.getLatLng()), a = e.distanceTo(a)
                } else a = 1 / 0;
                return a
            },
            _updateFinishHandler: function () {
                var x = this._markers.length;
                1 < x && this._markers[x - 1].on('click', this._finishShape, this), 2 < x && this._markers[x - 2].off('click', this._finishShape, this)
            },
            _createMarker: function (x) {
                x = new D.a.Marker(x, {icon: this.options.icon, zIndexOffset: 2 * this.options.zIndexOffset});
                return this._markerGroup.addLayer(x), x
            },
            _updateGuide: function (x) {
                var e = this._markers ? this._markers.length : 0;
                0 < e && (x = x || this._map.latLngToLayerPoint(this._currentLatLng), this._clearGuides(), this._drawGuide(this._map.latLngToLayerPoint(this._markers[e - 1].getLatLng()), x))
            },
            _updateTooltip: function (x) {
                var e = this._getTooltipText();
                x && this._tooltip.updatePosition(x), this._errorShown || this._tooltip.updateContent(e)
            },
            _drawGuide: function (x, e) {
                var a, d, f = Math.floor(Math.sqrt(Math.pow(e.x - x.x, 2) + Math.pow(e.y - x.y, 2))),
                    t = this.options.guidelineDistance, _ = this.options.maxGuideLineLength, i = _ < f ? f - _ : t;
                for (this._guidesContainer || (this._guidesContainer = D.a.DomUtil.create('div', 'leaflet-draw-guides', this._overlayPane)); i < f; i += this.options.guidelineDistance) d = i / f, a = {
                    x: Math.floor(x.x * (1 - d) + d * e.x),
                    y: Math.floor(x.y * (1 - d) + d * e.y)
                }, (d = D.a.DomUtil.create('div', 'leaflet-draw-guide-dash', this._guidesContainer)).style.backgroundColor = this._errorShown ? this.options.drawError.color : this.options.shapeOptions.color, D.a.DomUtil.setPosition(d, a)
            },
            _updateGuideColor: function (x) {
                if (this._guidesContainer) for (var e = 0, a = this._guidesContainer.childNodes.length; e < a; e++) this._guidesContainer.childNodes[e].style.backgroundColor = x
            },
            _clearGuides: function () {
                if (this._guidesContainer) for (; this._guidesContainer.firstChild;) this._guidesContainer.removeChild(this._guidesContainer.firstChild)
            },
            _getTooltipText: function () {
                var x = this.options.showLength,
                    e = 0 === this._markers.length ? {text: D.a.drawLocal.draw.handlers.polyline.tooltip.start} : (e = x ? this._getMeasurementString() : '', 1 === this._markers.length ? {
                        text: D.a.drawLocal.draw.handlers.polyline.tooltip.cont,
                        subtext: e
                    } : {text: D.a.drawLocal.draw.handlers.polyline.tooltip.end, subtext: e});
                return e
            },
            _updateRunningMeasure: function (x, e) {
                var a = this._markers.length;
                1 === this._markers.length ? this._measurementRunningTotal = 0 : (a = a - (e ? 2 : 1), a = D.a.GeometryUtil.isVersion07x() ? x.distanceTo(this._markers[a].getLatLng()) * (this.options.factor || 1) : this._map.distance(x, this._markers[a].getLatLng()) * (this.options.factor || 1), this._measurementRunningTotal += a * (e ? 1 : -1))
            },
            _getMeasurementString: function () {
                var x = this._currentLatLng, e = this._markers[this._markers.length - 1].getLatLng(),
                    e = D.a.GeometryUtil.isVersion07x() ? e && x && x.distanceTo ? this._measurementRunningTotal + x.distanceTo(e) * (this.options.factor || 1) : this._measurementRunningTotal || 0 : e && x ? this._measurementRunningTotal + this._map.distance(x, e) * (this.options.factor || 1) : this._measurementRunningTotal || 0;
                return D.a.GeometryUtil.readableDistance(e, this.options.metric, this.options.feet, this.options.nautic, this.options.precision)
            },
            _showErrorTooltip: function () {
                this._errorShown = !0, this._tooltip.showAsError().updateContent({text: this.options.drawError.message}), this._updateGuideColor(this.options.drawError.color), this._poly.setStyle({color: this.options.drawError.color}), this._clearHideErrorTimeout(), this._hideErrorTimeout = setTimeout(D.a.Util.bind(this._hideErrorTooltip, this), this.options.drawError.timeout)
            },
            _hideErrorTooltip: function () {
                this._errorShown = !1, this._clearHideErrorTimeout(), this._tooltip.removeError().updateContent(this._getTooltipText()), this._updateGuideColor(this.options.shapeOptions.color), this._poly.setStyle({color: this.options.shapeOptions.color})
            },
            _clearHideErrorTimeout: function () {
                this._hideErrorTimeout && (clearTimeout(this._hideErrorTimeout), this._hideErrorTimeout = null)
            },
            _disableNewMarkers: function () {
                this._disableMarkers = !0
            },
            _enableNewMarkers: function () {
                setTimeout(function () {
                    this._disableMarkers = !1
                }.bind(this), 50)
            },
            _cleanUpShape: function () {
                1 < this._markers.length && this._markers[this._markers.length - 1].off('click', this._finishShape, this)
            },
            _fireCreatedEvent: function () {
                var x = new this.Poly(this._poly.getLatLngs(), this.options.shapeOptions);
                D.a.Draw.Feature.prototype._fireCreatedEvent.call(this, x)
            }
        }), D.a.Draw.Polygon = D.a.Draw.Polyline.extend({
            statics: {TYPE: 'polygon'},
            Poly: D.a.Polygon,
            options: {
                showArea: !1,
                showLength: !1,
                shapeOptions: {
                    stroke: !0,
                    color: '#3388ff',
                    weight: 4,
                    opacity: .5,
                    fill: !0,
                    fillColor: null,
                    fillOpacity: .2,
                    clickable: !0
                },
                metric: !0,
                feet: !0,
                nautic: !1,
                precision: {}
            },
            initialize: function (x, e) {
                D.a.Draw.Polyline.prototype.initialize.call(this, x, e), this.type = D.a.Draw.Polygon.TYPE
            },
            _updateFinishHandler: function () {
                var x = this._markers.length;
                1 === x && this._markers[0].on('click', this._finishShape, this), 2 < x && (this._markers[x - 1].on('dblclick', this._finishShape, this), 3 < x && this._markers[x - 2].off('dblclick', this._finishShape, this))
            },
            _getTooltipText: function () {
                var x, e;
                return 0 === this._markers.length ? x = D.a.drawLocal.draw.handlers.polygon.tooltip.start : e = (x = this._markers.length < 3 ? D.a.drawLocal.draw.handlers.polygon.tooltip.cont : D.a.drawLocal.draw.handlers.polygon.tooltip.end, this._getMeasurementString()), {
                    text: x,
                    subtext: e
                }
            },
            _getMeasurementString: function () {
                var x = this._area, e = '';
                return x || this.options.showLength ? (this.options.showLength && (e = D.a.Draw.Polyline.prototype._getMeasurementString.call(this)), x && (e += '<br>' + D.a.GeometryUtil.readableArea(x, this.options.metric, this.options.precision)), e) : null
            },
            _shapeIsValid: function () {
                return 3 <= this._markers.length
            },
            _vertexChanged: function (x, e) {
                var a;
                !this.options.allowIntersection && this.options.showArea && (a = this._poly.getLatLngs(), this._area = D.a.GeometryUtil.geodesicArea(a)), D.a.Draw.Polyline.prototype._vertexChanged.call(this, x, e)
            },
            _cleanUpShape: function () {
                var x = this._markers.length;
                0 < x && (this._markers[0].off('click', this._finishShape, this), 2 < x && this._markers[x - 1].off('dblclick', this._finishShape, this))
            }
        }), D.a.SimpleShape = {}, D.a.Draw.SimpleShape = D.a.Draw.Feature.extend({
            options: {repeatMode: !1}, initialize: function (x, e) {
                this._endLabelText = D.a.drawLocal.draw.handlers.simpleshape.tooltip.end, D.a.Draw.Feature.prototype.initialize.call(this, x, e)
            }, addHooks: function () {
                D.a.Draw.Feature.prototype.addHooks.call(this), this._map && (this._mapDraggable = this._map.dragging.enabled(), this._mapDraggable && this._map.dragging.disable(), this._container.style.cursor = 'crosshair', this._tooltip.updateContent({text: this._initialLabelText}), this._map.on('mousedown', this._onMouseDown, this).on('mousemove', this._onMouseMove, this).on('touchstart', this._onMouseDown, this).on('touchmove', this._onMouseMove, this), document.addEventListener('touchstart', D.a.DomEvent.preventDefault, {passive: !1}))
            }, removeHooks: function () {
                D.a.Draw.Feature.prototype.removeHooks.call(this), this._map && (this._mapDraggable && this._map.dragging.enable(), this._container.style.cursor = '', this._map.off('mousedown', this._onMouseDown, this).off('mousemove', this._onMouseMove, this).off('touchstart', this._onMouseDown, this).off('touchmove', this._onMouseMove, this), D.a.DomEvent.off(document, 'mouseup', this._onMouseUp, this), D.a.DomEvent.off(document, 'touchend', this._onMouseUp, this), document.removeEventListener('touchstart', D.a.DomEvent.preventDefault), this._shape && (this._map.removeLayer(this._shape), delete this._shape)), this._isDrawing = !1
            }, _getTooltipText: function () {
                return {text: this._endLabelText}
            }, _onMouseDown: function (x) {
                this._isDrawing = !0, this._startLatLng = x.latlng, D.a.DomEvent.on(document, 'mouseup', this._onMouseUp, this).on(document, 'touchend', this._onMouseUp, this).preventDefault(x.originalEvent)
            }, _onMouseMove: function (x) {
                x = x.latlng;
                this._tooltip.updatePosition(x), this._isDrawing && (this._tooltip.updateContent(this._getTooltipText()), this._drawShape(x))
            }, _onMouseUp: function () {
                this._shape && this._fireCreatedEvent(), this.disable(), this.options.repeatMode && this.enable()
            }
        }), D.a.Draw.Rectangle = D.a.Draw.SimpleShape.extend({
            statics: {TYPE: 'rectangle'},
            options: {
                shapeOptions: {
                    stroke: !0,
                    color: '#3388ff',
                    weight: 4,
                    opacity: .5,
                    fill: !0,
                    fillColor: null,
                    fillOpacity: .2,
                    clickable: !0
                }, showArea: !0, metric: !0
            },
            initialize: function (x, e) {
                this.type = D.a.Draw.Rectangle.TYPE, this._initialLabelText = D.a.drawLocal.draw.handlers.rectangle.tooltip.start, D.a.Draw.SimpleShape.prototype.initialize.call(this, x, e)
            },
            disable: function () {
                this._enabled && (this._isCurrentlyTwoClickDrawing = !1, D.a.Draw.SimpleShape.prototype.disable.call(this))
            },
            _onMouseUp: function (x) {
                this._shape || this._isCurrentlyTwoClickDrawing ? this._isCurrentlyTwoClickDrawing && !function (x, e) {
                    for (; (x = x.parentElement) && !x.classList.contains(e);) ;
                    return x
                }(x.target, 'leaflet-pane') || D.a.Draw.SimpleShape.prototype._onMouseUp.call(this) : this._isCurrentlyTwoClickDrawing = !0
            },
            _drawShape: function (x) {
                this._shape ? this._shape.setBounds(new D.a.LatLngBounds(this._startLatLng, x)) : (this._shape = new D.a.Rectangle(new D.a.LatLngBounds(this._startLatLng, x), this.options.shapeOptions), this._map.addLayer(this._shape))
            },
            _fireCreatedEvent: function () {
                var x = new D.a.Rectangle(this._shape.getBounds(), this.options.shapeOptions);
                D.a.Draw.SimpleShape.prototype._fireCreatedEvent.call(this, x)
            },
            _getTooltipText: function () {
                var x, e = D.a.Draw.SimpleShape.prototype._getTooltipText.call(this), a = this._shape,
                    d = this.options.showArea;
                return a && (x = this._shape._defaultShape ? this._shape._defaultShape() : this._shape.getLatLngs(), x = D.a.GeometryUtil.geodesicArea(x), x = d ? D.a.GeometryUtil.readableArea(x, this.options.metric) : ''), {
                    text: e.text,
                    subtext: x
                }
            }
        }), D.a.Draw.Marker = D.a.Draw.Feature.extend({
            statics: {TYPE: 'marker'},
            options: {icon: new D.a.Icon.Default, repeatMode: !1, zIndexOffset: 2e3},
            initialize: function (x, e) {
                this.type = D.a.Draw.Marker.TYPE, this._initialLabelText = D.a.drawLocal.draw.handlers.marker.tooltip.start, D.a.Draw.Feature.prototype.initialize.call(this, x, e)
            },
            addHooks: function () {
                D.a.Draw.Feature.prototype.addHooks.call(this), this._map && (this._tooltip.updateContent({text: this._initialLabelText}), this._mouseMarker || (this._mouseMarker = D.a.marker(this._map.getCenter(), {
                    icon: D.a.divIcon({
                        className: 'leaflet-mouse-marker',
                        iconAnchor: [20, 20],
                        iconSize: [40, 40]
                    }), opacity: 0, zIndexOffset: this.options.zIndexOffset
                })), this._mouseMarker.on('click', this._onClick, this).addTo(this._map), this._map.on('mousemove', this._onMouseMove, this), this._map.on('click', this._onTouch, this))
            },
            removeHooks: function () {
                D.a.Draw.Feature.prototype.removeHooks.call(this), this._map && (this._map.off('click', this._onClick, this).off('click', this._onTouch, this), this._marker && (this._marker.off('click', this._onClick, this), this._map.removeLayer(this._marker), delete this._marker), this._mouseMarker.off('click', this._onClick, this), this._map.removeLayer(this._mouseMarker), delete this._mouseMarker, this._map.off('mousemove', this._onMouseMove, this))
            },
            _onMouseMove: function (x) {
                x = x.latlng;
                this._tooltip.updatePosition(x), this._mouseMarker.setLatLng(x), this._marker ? (x = this._mouseMarker.getLatLng(), this._marker.setLatLng(x)) : (this._marker = this._createMarker(x), this._marker.on('click', this._onClick, this), this._map.on('click', this._onClick, this).addLayer(this._marker))
            },
            _createMarker: function (x) {
                return new D.a.Marker(x, {icon: this.options.icon, zIndexOffset: this.options.zIndexOffset})
            },
            _onClick: function () {
                this._fireCreatedEvent(), this.disable(), this.options.repeatMode && this.enable()
            },
            _onTouch: function (x) {
                this._onMouseMove(x), this._onClick()
            },
            _fireCreatedEvent: function () {
                var x = new D.a.Marker.Touch(this._marker.getLatLng(), {icon: this.options.icon});
                D.a.Draw.Feature.prototype._fireCreatedEvent.call(this, x)
            }
        }), D.a.Draw.CircleMarker = D.a.Draw.Marker.extend({
            statics: {TYPE: 'circlemarker'},
            options: {
                stroke: !0,
                color: '#3388ff',
                weight: 4,
                opacity: .5,
                fill: !0,
                fillColor: null,
                fillOpacity: .2,
                clickable: !0,
                zIndexOffset: 2e3
            },
            initialize: function (x, e) {
                this.type = D.a.Draw.CircleMarker.TYPE, this._initialLabelText = D.a.drawLocal.draw.handlers.circlemarker.tooltip.start, D.a.Draw.Feature.prototype.initialize.call(this, x, e)
            },
            _fireCreatedEvent: function () {
                var x = new D.a.CircleMarker(this._marker.getLatLng(), this.options);
                D.a.Draw.Feature.prototype._fireCreatedEvent.call(this, x)
            },
            _createMarker: function (x) {
                return new D.a.CircleMarker(x, this.options)
            }
        }), D.a.Draw.Circle = D.a.Draw.SimpleShape.extend({
            statics: {TYPE: 'circle'},
            options: {
                shapeOptions: {
                    stroke: !0,
                    color: '#3388ff',
                    weight: 4,
                    opacity: .5,
                    fill: !0,
                    fillColor: null,
                    fillOpacity: .2,
                    clickable: !0
                }, showRadius: !0, metric: !0, feet: !0, nautic: !1
            },
            initialize: function (x, e) {
                this.type = D.a.Draw.Circle.TYPE, this._initialLabelText = D.a.drawLocal.draw.handlers.circle.tooltip.start, D.a.Draw.SimpleShape.prototype.initialize.call(this, x, e)
            },
            _drawShape: function (x) {
                x = D.a.GeometryUtil.isVersion07x() ? this._startLatLng.distanceTo(x) : this._map.distance(this._startLatLng, x);
                this._shape ? this._shape.setRadius(x) : (this._shape = new D.a.Circle(this._startLatLng, x, this.options.shapeOptions), this._map.addLayer(this._shape))
            },
            _fireCreatedEvent: function () {
                var x = new D.a.Circle(this._startLatLng, this._shape.getRadius(), this.options.shapeOptions);
                D.a.Draw.SimpleShape.prototype._fireCreatedEvent.call(this, x)
            },
            _onMouseMove: function (x) {
                var e = x.latlng, a = this.options.showRadius, d = this.options.metric;
                this._tooltip.updatePosition(e), this._isDrawing && (this._drawShape(e), x = this._shape.getRadius().toFixed(1), e = '', a && (e = D.a.drawLocal.draw.handlers.circle.radius + ': ' + D.a.GeometryUtil.readableDistance(x, d, this.options.feet, this.options.nautic)), this._tooltip.updateContent({
                    text: this._endLabelText,
                    subtext: e
                }))
            }
        }), D.a.Edit = D.a.Edit || {}, D.a.Edit.Marker = D.a.Handler.extend({
            initialize: function (x, e) {
                this._marker = x, D.a.setOptions(this, e)
            }, addHooks: function () {
                var x = this._marker;
                x.dragging && x.dragging.enable(), x.on('dragend', this._onDragEnd, x), this._toggleMarkerHighlight()
            }, removeHooks: function () {
                var x = this._marker;
                x.dragging && x.dragging.disable(), x.off('dragend', this._onDragEnd, x), this._toggleMarkerHighlight()
            }, _onDragEnd: function (x) {
                x = x.target;
                x.edited = !0, this._map.fire(D.a.Draw.Event.EDITMOVE, {layer: x})
            }, _toggleMarkerHighlight: function () {
                var x = this._marker._icon;
                x && (x.style.display = 'none', D.a.DomUtil.hasClass(x, 'leaflet-edit-marker-selected') ? (D.a.DomUtil.removeClass(x, 'leaflet-edit-marker-selected'), this._offsetMarker(x, -4)) : (D.a.DomUtil.addClass(x, 'leaflet-edit-marker-selected'), this._offsetMarker(x, 4)), x.style.display = '')
            }, _offsetMarker: function (x, e) {
                var a = parseInt(x.style.marginTop, 10) - e, e = parseInt(x.style.marginLeft, 10) - e;
                x.style.marginTop = a + 'px', x.style.marginLeft = e + 'px'
            }
        }), D.a.Marker.addInitHook(function () {
            D.a.Edit.Marker && (this.editing = new D.a.Edit.Marker(this), this.options.editable && this.editing.enable())
        }), D.a.Edit = D.a.Edit || {}, D.a.Edit.Poly = D.a.Handler.extend({
            initialize: function (x) {
                this.latlngs = [x._latlngs], x._holes && (this.latlngs = this.latlngs.concat(x._holes)), this._poly = x, this._poly.on('revert-edited', this._updateLatLngs, this)
            }, _defaultShape: function () {
                return D.a.LineUtil.isFlat(this._poly._latlngs) ? this._poly._latlngs : this._poly._latlngs[0]
            }, _eachVertexHandler: function (x) {
                for (var e = 0; e < this._verticesHandlers.length; e++) x(this._verticesHandlers[e])
            }, addHooks: function () {
                this._initHandlers(), this._eachVertexHandler(function (x) {
                    x.addHooks()
                })
            }, removeHooks: function () {
                this._eachVertexHandler(function (x) {
                    x.removeHooks()
                })
            }, updateMarkers: function () {
                this._eachVertexHandler(function (x) {
                    x.updateMarkers()
                })
            }, _initHandlers: function () {
                this._verticesHandlers = [];
                for (var x = 0; x < this.latlngs.length; x++) this._verticesHandlers.push(new D.a.Edit.PolyVerticesEdit(this._poly, this.latlngs[x], this._poly.options.poly))
            }, _updateLatLngs: function (x) {
                this.latlngs = [x.layer._latlngs], x.layer._holes && (this.latlngs = this.latlngs.concat(x.layer._holes))
            }
        }), D.a.Edit.PolyVerticesEdit = D.a.Handler.extend({
            options: {
                icon: new D.a.DivIcon({
                    iconSize: new D.a.Point(8, 8),
                    className: 'leaflet-div-icon leaflet-editing-icon'
                }),
                touchIcon: new D.a.DivIcon({
                    iconSize: new D.a.Point(15, 15),
                    className: 'leaflet-div-icon leaflet-editing-icon leaflet-touch-icon'
                }),
                drawError: {color: '#b00b00', timeout: 1e3}
            }, initialize: function (x, e, a) {
                D.a.Browser.touch && (this.options.icon = this.options.touchIcon), this._poly = x, a && a.drawError && (a.drawError = D.a.Util.extend({}, this.options.drawError, a.drawError)), this._latlngs = e, D.a.setOptions(this, a)
            }, _defaultShape: function () {
                return D.a.LineUtil.isFlat(this._latlngs) ? this._latlngs : this._latlngs[0]
            }, addHooks: function () {
                var x = this._poly, e = x._path;
                null == x.options.editing && x.editing && (x.options.editing = x.editing), x instanceof D.a.Polygon || (x.options.fill = !1, x.options.editing && (x.options.editing.fill = !1)), e && x.options.editing && x.options.editing.className && (x.options.original.className && x.options.original.className.split(' ').forEach(function (x) {
                    D.a.DomUtil.removeClass(e, x)
                }), x.options.editing.className.split(' ').forEach(function (x) {
                    D.a.DomUtil.addClass(e, x)
                })), x.setStyle(x.options.editing || {}), this._poly._map && (this._map = this._poly._map, this._markerGroup || this._initMarkers(), this._poly._map.addLayer(this._markerGroup))
            }, removeHooks: function () {
                var x = this._poly, e = x._path;
                e && x.options.editing && x.options.editing.className && (x.options.editing.className.split(' ').forEach(function (x) {
                    D.a.DomUtil.removeClass(e, x)
                }), x.options.original.className && x.options.original.className.split(' ').forEach(function (x) {
                    D.a.DomUtil.addClass(e, x)
                })), x.setStyle(x.options.original || {}), x._map && (x._map.removeLayer(this._markerGroup), delete this._markerGroup, delete this._markers)
            }, updateMarkers: function () {
                this._markerGroup.clearLayers(), this._initMarkers()
            }, _initMarkers: function () {
                this._markerGroup || (this._markerGroup = new D.a.LayerGroup), this._markers = [];
                for (var x, e, a, d, f = this._defaultShape(), t = 0, _ = f.length; t < _; t++) (e = this._createMarker(f[t], t)).on('click', this._onMarkerClick, this), e.on('contextmenu', this._onContextMenu, this), this._markers.push(e);
                for (t = 0, x = _ - 1; t < _; x = t++) (0 !== t || D.a.Polygon && this._poly instanceof D.a.Polygon) && (a = this._markers[x], d = this._markers[t], this._createMiddleMarker(a, d), this._updatePrevNext(a, d))
            }, _createMarker: function (x, e) {
                var a = new D.a.Marker.Touch(x, {draggable: !0, icon: this.options.icon});
                return a._origLatLng = x, a._index = e, a.on('dragstart', this._onMarkerDragStart, this).on('drag', this._onMarkerDrag, this).on('dragend', this._fireEdit, this).on('touchmove', this._onTouchMove, this).on('touchend', this._fireEdit, this).on('MSPointerMove', this._onTouchMove, this).on('MSPointerUp', this._fireEdit, this), this._markerGroup.addLayer(a), a
            }, _onMarkerDragStart: function () {
                this._poly.fire('editstart')
            }, _spliceLatLngs: function () {
                var x = this._defaultShape(), e = [].splice.apply(x, arguments);
                return this._poly._convertLatLngs(x, !0), this._poly.redraw(), e
            }, _removeMarker: function (x) {
                var e = x._index;
                this._markerGroup.removeLayer(x), this._markers.splice(e, 1), this._spliceLatLngs(e, 1), this._updateIndexes(e, -1), x.off('dragstart', this._onMarkerDragStart, this).off('drag', this._onMarkerDrag, this).off('dragend', this._fireEdit, this).off('touchmove', this._onMarkerDrag, this).off('touchend', this._fireEdit, this).off('click', this._onMarkerClick, this).off('MSPointerMove', this._onTouchMove, this).off('MSPointerUp', this._fireEdit, this)
            }, _fireEdit: function () {
                this._poly.edited = !0, this._poly.fire('edit'), this._poly._map.fire(D.a.Draw.Event.EDITVERTEX, {
                    layers: this._markerGroup,
                    poly: this._poly
                })
            }, _onMarkerDrag: function (x) {
                var e, a, d = x.target, f = this._poly, x = D.a.LatLngUtil.cloneLatLng(d._origLatLng);
                D.a.extend(d._origLatLng, d._latlng), f.options.poly && (e = f._map._editTooltip, !f.options.poly.allowIntersection && f.intersects() && (D.a.extend(d._origLatLng, x), d.setLatLng(x), a = f.options.color, f.setStyle({color: this.options.drawError.color}), e && e.updateContent({text: D.a.drawLocal.draw.handlers.polyline.error}), setTimeout(function () {
                    f.setStyle({color: a}), e && e.updateContent({
                        text: D.a.drawLocal.edit.handlers.edit.tooltip.text,
                        subtext: D.a.drawLocal.edit.handlers.edit.tooltip.subtext
                    })
                }, 1e3))), d._middleLeft && d._middleLeft.setLatLng(this._getMiddleLatLng(d._prev, d)), d._middleRight && d._middleRight.setLatLng(this._getMiddleLatLng(d, d._next)), this._poly._bounds._southWest = D.a.latLng(1 / 0, 1 / 0), this._poly._bounds._northEast = D.a.latLng(-1 / 0, -1 / 0);
                d = this._poly.getLatLngs();
                this._poly._convertLatLngs(d, !0), this._poly.redraw(), this._poly.fire('editdrag'), this._map && this._map.fire('draw:editing', {layer: this._poly})
            }, _onMarkerClick: function (x) {
                var e = D.a.Polygon && this._poly instanceof D.a.Polygon ? 4 : 3, x = x.target;
                this._defaultShape().length < e || (this._removeMarker(x), this._updatePrevNext(x._prev, x._next), x._middleLeft && this._markerGroup.removeLayer(x._middleLeft), x._middleRight && this._markerGroup.removeLayer(x._middleRight), x._prev && x._next ? this._createMiddleMarker(x._prev, x._next) : x._prev ? x._next || (x._prev._middleRight = null) : x._next._middleLeft = null, this._fireEdit())
            }, _onContextMenu: function (x) {
                x = x.target;
                this._poly, this._poly._map.fire(D.a.Draw.Event.MARKERCONTEXT, {
                    marker: x,
                    layers: this._markerGroup,
                    poly: this._poly
                }), D.a.DomEvent.stopPropagation
            }, _onTouchMove: function (x) {
                var e = this._map.mouseEventToLayerPoint(x.originalEvent.touches[0]),
                    e = this._map.layerPointToLatLng(e), x = x.target;
                D.a.extend(x._origLatLng, e), x._middleLeft && x._middleLeft.setLatLng(this._getMiddleLatLng(x._prev, x)), x._middleRight && x._middleRight.setLatLng(this._getMiddleLatLng(x, x._next)), this._poly.redraw(), this.updateMarkers()
            }, _updateIndexes: function (e, a) {
                this._markerGroup.eachLayer(function (x) {
                    x._index > e && (x._index += a)
                })
            }, _createMiddleMarker: function (e, a) {
                var d, f, x, t = this._getMiddleLatLng(e, a), _ = this._createMarker(t);
                _.setOpacity(.6), e._middleRight = a._middleLeft = _, f = function () {
                    _.off('touchmove', f, this);
                    var x = a._index;
                    _._index = x, _.off('click', d, this).on('click', this._onMarkerClick, this), t.lat = _.getLatLng().lat, t.lng = _.getLatLng().lng, this._spliceLatLngs(x, 0, t), this._markers.splice(x, 0, _), _.setOpacity(1), this._updateIndexes(x, 1), a._index++, this._updatePrevNext(e, _), this._updatePrevNext(_, a), this._poly.fire('editstart')
                }, x = function () {
                    _.off('dragstart', f, this), _.off('dragend', x, this), _.off('touchmove', f, this), this._createMiddleMarker(e, _), this._createMiddleMarker(_, a)
                }, d = function () {
                    f.call(this), x.call(this), this._fireEdit()
                }, _.on('click', d, this).on('dragstart', f, this).on('dragend', x, this).on('touchmove', f, this), this._markerGroup.addLayer(_)
            }, _updatePrevNext: function (x, e) {
                x && (x._next = e), e && (e._prev = x)
            }, _getMiddleLatLng: function (x, e) {
                var a = this._poly._map, x = a.project(x.getLatLng()), e = a.project(e.getLatLng());
                return a.unproject(x._add(e)._divideBy(2))
            }
        }), D.a.Polyline.addInitHook(function () {
            this.editing || (D.a.Edit.Poly && (this.editing = new D.a.Edit.Poly(this), this.options.editable && this.editing.enable()), this.on('add', function () {
                this.editing && this.editing.enabled() && this.editing.addHooks()
            }), this.on('remove', function () {
                this.editing && this.editing.enabled() && this.editing.removeHooks()
            }))
        }), D.a.Edit = D.a.Edit || {}, D.a.Edit.SimpleShape = D.a.Handler.extend({
            options: {
                moveIcon: new D.a.DivIcon({
                    iconSize: new D.a.Point(8, 8),
                    className: 'leaflet-div-icon leaflet-editing-icon leaflet-edit-move'
                }),
                resizeIcon: new D.a.DivIcon({
                    iconSize: new D.a.Point(8, 8),
                    className: 'leaflet-div-icon leaflet-editing-icon leaflet-edit-resize'
                }),
                touchMoveIcon: new D.a.DivIcon({
                    iconSize: new D.a.Point(15, 15),
                    className: 'leaflet-div-icon leaflet-editing-icon leaflet-edit-move leaflet-touch-icon'
                }),
                touchResizeIcon: new D.a.DivIcon({
                    iconSize: new D.a.Point(15, 15),
                    className: 'leaflet-div-icon leaflet-editing-icon leaflet-edit-resize leaflet-touch-icon'
                })
            }, initialize: function (x, e) {
                D.a.Browser.touch && (this.options.moveIcon = this.options.touchMoveIcon, this.options.resizeIcon = this.options.touchResizeIcon), this._shape = x, D.a.Util.setOptions(this, e)
            }, addHooks: function () {
                var x = this._shape;
                this._shape._map && (this._map = this._shape._map, x.options.editing && x.setStyle(x.options.editing), x._map && (this._map = x._map, this._markerGroup || this._initMarkers(), this._map.addLayer(this._markerGroup)))
            }, removeHooks: function () {
                var x = this._shape;
                if (x.options.original && x.setStyle(x.options.original), x._map) {
                    this._unbindMarker(this._moveMarker);
                    for (var e = 0, a = this._resizeMarkers.length; e < a; e++) this._unbindMarker(this._resizeMarkers[e]);
                    this._resizeMarkers = null, this._map.removeLayer(this._markerGroup), delete this._markerGroup
                }
                this._map = null
            }, updateMarkers: function () {
                this._markerGroup.clearLayers(), this._initMarkers()
            }, _initMarkers: function () {
                this._markerGroup || (this._markerGroup = new D.a.LayerGroup), this._createMoveMarker(), this._createResizeMarker()
            }, _createMoveMarker: function () {
            }, _createResizeMarker: function () {
            }, _createMarker: function (x, e) {
                e = new D.a.Marker.Touch(x, {draggable: !0, icon: e, zIndexOffset: 10});
                return this._bindMarker(e), this._markerGroup.addLayer(e), e
            }, _bindMarker: function (x) {
                x.on('dragstart', this._onMarkerDragStart, this).on('drag', this._onMarkerDrag, this).on('dragend', this._onMarkerDragEnd, this).on('touchstart', this._onTouchStart, this).on('touchmove', this._onTouchMove, this).on('MSPointerMove', this._onTouchMove, this).on('touchend', this._onTouchEnd, this).on('MSPointerUp', this._onTouchEnd, this)
            }, _unbindMarker: function (x) {
                x.off('dragstart', this._onMarkerDragStart, this).off('drag', this._onMarkerDrag, this).off('dragend', this._onMarkerDragEnd, this).off('touchstart', this._onTouchStart, this).off('touchmove', this._onTouchMove, this).off('MSPointerMove', this._onTouchMove, this).off('touchend', this._onTouchEnd, this).off('MSPointerUp', this._onTouchEnd, this)
            }, _onMarkerDragStart: function (x) {
                x.target.setOpacity(0), this._shape.fire('editstart')
            }, _fireEdit: function () {
                this._shape.edited = !0, this._shape.fire('edit')
            }, _onMarkerDrag: function (x) {
                var e = x.target, x = e.getLatLng();
                e === this._moveMarker ? this._move(x) : this._resize(x), this._shape.redraw(), this._shape.fire('editdrag')
            }, _onMarkerDragEnd: function (x) {
                x.target.setOpacity(1), this._fireEdit()
            }, _onTouchStart: function (x) {
                var e, a;
                D.a.Edit.SimpleShape.prototype._onMarkerDragStart.call(this, x), 'function' == _typeof(this._getCorners) && (e = this._getCorners(), x = (a = x.target)._cornerIndex, a.setOpacity(0), this._oppositeCorner = e[(x + 2) % 4], this._toggleCornerMarkers(0, x)), this._shape.fire('editstart')
            }, _onTouchMove: function (x) {
                var e = this._map.mouseEventToLayerPoint(x.originalEvent.touches[0]),
                    e = this._map.layerPointToLatLng(e);
                return x.target === this._moveMarker ? this._move(e) : this._resize(e), this._shape.redraw(), !1
            }, _onTouchEnd: function (x) {
                x.target.setOpacity(1), this.updateMarkers(), this._fireEdit()
            }, _move: function () {
            }, _resize: function () {
            }
        }), D.a.Edit = D.a.Edit || {}, D.a.Edit.Rectangle = D.a.Edit.SimpleShape.extend({
            _createMoveMarker: function () {
                var x = this._shape.getBounds().getCenter();
                this._moveMarker = this._createMarker(x, this.options.moveIcon)
            }, _createResizeMarker: function () {
                var x = this._getCorners();
                this._resizeMarkers = [];
                for (var e = 0, a = x.length; e < a; e++) this._resizeMarkers.push(this._createMarker(x[e], this.options.resizeIcon)), this._resizeMarkers[e]._cornerIndex = e
            }, _onMarkerDragStart: function (x) {
                D.a.Edit.SimpleShape.prototype._onMarkerDragStart.call(this, x);
                var e = this._getCorners(), x = x.target._cornerIndex;
                this._oppositeCorner = e[(x + 2) % 4], this._toggleCornerMarkers(0, x)
            }, _onMarkerDragEnd: function (x) {
                var e, a = x.target;
                a === this._moveMarker && (e = this._shape.getBounds().getCenter(), a.setLatLng(e)), this._toggleCornerMarkers(1), this._repositionCornerMarkers(), D.a.Edit.SimpleShape.prototype._onMarkerDragEnd.call(this, x)
            }, _move: function (x) {
                for (var e, a = this._shape._defaultShape ? this._shape._defaultShape() : this._shape.getLatLngs(), d = this._shape.getBounds().getCenter(), f = [], t = 0, _ = a.length; t < _; t++) e = [a[t].lat - d.lat, a[t].lng - d.lng], f.push([x.lat + e[0], x.lng + e[1]]);
                this._shape.setLatLngs(f), this._repositionCornerMarkers(), this._map.fire(D.a.Draw.Event.EDITMOVE, {layer: this._shape})
            }, _resize: function (x) {
                this._shape.setBounds(D.a.latLngBounds(x, this._oppositeCorner)), x = this._shape.getBounds(), this._moveMarker.setLatLng(x.getCenter()), this._map.fire(D.a.Draw.Event.EDITRESIZE, {layer: this._shape})
            }, _getCorners: function () {
                var x = this._shape.getBounds();
                return [x.getNorthWest(), x.getNorthEast(), x.getSouthEast(), x.getSouthWest()]
            }, _toggleCornerMarkers: function (x) {
                for (var e = 0, a = this._resizeMarkers.length; e < a; e++) this._resizeMarkers[e].setOpacity(x)
            }, _repositionCornerMarkers: function () {
                for (var x = this._getCorners(), e = 0, a = this._resizeMarkers.length; e < a; e++) this._resizeMarkers[e].setLatLng(x[e])
            }
        }), D.a.Rectangle.addInitHook(function () {
            D.a.Edit.Rectangle && (this.editing = new D.a.Edit.Rectangle(this), this.options.editable && this.editing.enable())
        }), D.a.Edit = D.a.Edit || {}, D.a.Edit.CircleMarker = D.a.Edit.SimpleShape.extend({
            _createMoveMarker: function () {
                var x = this._shape.getLatLng();
                this._moveMarker = this._createMarker(x, this.options.moveIcon)
            }, _createResizeMarker: function () {
                this._resizeMarkers = []
            }, _move: function (x) {
                var e;
                this._resizeMarkers.length && (e = this._getResizeMarkerPoint(x), this._resizeMarkers[0].setLatLng(e)), this._shape.setLatLng(x), this._map.fire(D.a.Draw.Event.EDITMOVE, {layer: this._shape})
            }
        }), D.a.CircleMarker.addInitHook(function () {
            D.a.Edit.CircleMarker && (this.editing = new D.a.Edit.CircleMarker(this), this.options.editable && this.editing.enable()), this.on('add', function () {
                this.editing && this.editing.enabled() && this.editing.addHooks()
            }), this.on('remove', function () {
                this.editing && this.editing.enabled() && this.editing.removeHooks()
            })
        }), D.a.Edit = D.a.Edit || {}, D.a.Edit.Circle = D.a.Edit.CircleMarker.extend({
            _createResizeMarker: function () {
                var x = this._shape.getLatLng(), x = this._getResizeMarkerPoint(x);
                this._resizeMarkers = [], this._resizeMarkers.push(this._createMarker(x, this.options.resizeIcon))
            }, _getResizeMarkerPoint: function (x) {
                var e = this._shape._radius * Math.cos(Math.PI / 4), x = this._map.project(x);
                return this._map.unproject([x.x + e, x.y - e])
            }, _resize: function (x) {
                var e = this._moveMarker.getLatLng(),
                    x = D.a.GeometryUtil.isVersion07x() ? e.distanceTo(x) : this._map.distance(e, x);
                this._shape.setRadius(x), this._map.editTooltip && this._map._editTooltip.updateContent({
                    text: D.a.drawLocal.edit.handlers.edit.tooltip.subtext + '<br />' + D.a.drawLocal.edit.handlers.edit.tooltip.text,
                    subtext: D.a.drawLocal.draw.handlers.circle.radius + ': ' + D.a.GeometryUtil.readableDistance(x, !0, this.options.feet, this.options.nautic)
                }), this._shape.setRadius(x), this._map.fire(D.a.Draw.Event.EDITRESIZE, {layer: this._shape})
            }
        }), D.a.Circle.addInitHook(function () {
            D.a.Edit.Circle && (this.editing = new D.a.Edit.Circle(this), this.options.editable && this.editing.enable())
        }), D.a.Map.mergeOptions({touchExtend: !0}), D.a.Map.TouchExtend = D.a.Handler.extend({
            initialize: function (x) {
                this._map = x, this._container = x._container, this._pane = x._panes.overlayPane
            }, addHooks: function () {
                D.a.DomEvent.on(this._container, 'touchstart', this._onTouchStart, this), D.a.DomEvent.on(this._container, 'touchend', this._onTouchEnd, this), D.a.DomEvent.on(this._container, 'touchmove', this._onTouchMove, this), this._detectIE() ? (D.a.DomEvent.on(this._container, 'MSPointerDown', this._onTouchStart, this), D.a.DomEvent.on(this._container, 'MSPointerUp', this._onTouchEnd, this), D.a.DomEvent.on(this._container, 'MSPointerMove', this._onTouchMove, this), D.a.DomEvent.on(this._container, 'MSPointerCancel', this._onTouchCancel, this)) : (D.a.DomEvent.on(this._container, 'touchcancel', this._onTouchCancel, this), D.a.DomEvent.on(this._container, 'touchleave', this._onTouchLeave, this))
            }, removeHooks: function () {
                D.a.DomEvent.off(this._container, 'touchstart', this._onTouchStart, this), D.a.DomEvent.off(this._container, 'touchend', this._onTouchEnd, this), D.a.DomEvent.off(this._container, 'touchmove', this._onTouchMove, this), this._detectIE() ? (D.a.DomEvent.off(this._container, 'MSPointerDown', this._onTouchStart, this), D.a.DomEvent.off(this._container, 'MSPointerUp', this._onTouchEnd, this), D.a.DomEvent.off(this._container, 'MSPointerMove', this._onTouchMove, this), D.a.DomEvent.off(this._container, 'MSPointerCancel', this._onTouchCancel, this)) : (D.a.DomEvent.off(this._container, 'touchcancel', this._onTouchCancel, this), D.a.DomEvent.off(this._container, 'touchleave', this._onTouchLeave, this))
            }, _touchEvent: function (x, e) {
                var a = {};
                if (void 0 !== x.touches) {
                    if (!x.touches.length) return;
                    a = x.touches[0]
                } else {
                    if ('touch' !== x.pointerType) return;
                    if (a = x, !this._filterClick(x)) return
                }
                var d = this._map.mouseEventToContainerPoint(a), f = this._map.mouseEventToLayerPoint(a),
                    t = this._map.layerPointToLatLng(f);
                this._map.fire(e, {
                    latlng: t,
                    layerPoint: f,
                    containerPoint: d,
                    pageX: a.pageX,
                    pageY: a.pageY,
                    originalEvent: x
                })
            }, _filterClick: function (x) {
                var e = x.timeStamp || x.originalEvent.timeStamp,
                    a = D.a.DomEvent._lastClick && e - D.a.DomEvent._lastClick;
                return a && 100 < a && a < 500 || x.target._simulatedClick && !x._simulated ? (D.a.DomEvent.stop(x), !1) : (D.a.DomEvent._lastClick = e, !0)
            }, _onTouchStart: function (x) {
                this._map._loaded && this._touchEvent(x, 'touchstart')
            }, _onTouchEnd: function (x) {
                this._map._loaded && this._touchEvent(x, 'touchend')
            }, _onTouchCancel: function (x) {
                var e;
                this._map._loaded && (e = 'touchcancel', this._detectIE() && (e = 'pointercancel'), this._touchEvent(x, e))
            }, _onTouchLeave: function (x) {
                this._map._loaded && this._touchEvent(x, 'touchleave')
            }, _onTouchMove: function (x) {
                this._map._loaded && this._touchEvent(x, 'touchmove')
            }, _detectIE: function () {
                var x = window.navigator.userAgent, e = x.indexOf('MSIE ');
                if (0 < e) return parseInt(x.substring(e + 5, x.indexOf('.', e)), 10);
                if (0 < x.indexOf('Trident/')) {
                    var a = x.indexOf('rv:');
                    return parseInt(x.substring(a + 3, x.indexOf('.', a)), 10)
                }
                a = x.indexOf('Edge/');
                return 0 < a && parseInt(x.substring(a + 5, x.indexOf('.', a)), 10)
            }
        }), D.a.Map.addInitHook('addHandler', 'touchExtend', D.a.Map.TouchExtend), D.a.Marker.Touch = D.a.Marker.extend({
            _initInteraction: function () {
                return this.addInteractiveTarget ? D.a.Marker.prototype._initInteraction.apply(this) : this._initInteractionLegacy()
            }, _initInteractionLegacy: function () {
                if (this.options.clickable) {
                    var x = this._icon,
                        e = ['dblclick', 'mousedown', 'mouseover', 'mouseout', 'contextmenu', 'touchstart', 'touchend', 'touchmove'];
                    this._detectIE ? e.concat(['MSPointerDown', 'MSPointerUp', 'MSPointerMove', 'MSPointerCancel']) : e.concat('.touchcancel'
                ),
                    D.a.DomUtil.addClass(x, 'leaflet-clickable'), D.a.DomEvent.on(x, 'click', this._onMouseClick, this), D.a.DomEvent.on(x, 'keypress', this._onKeyPress, this);
                    for (var a = 0; a < e.length; a++) D.a.DomEvent.on(x, e[a], this._fireMouseEvent, this);
                    D.a.Handler.MarkerDrag && (this.dragging = new D.a.Handler.MarkerDrag(this), this.options.draggable && this.dragging.enable())
                }
            }, _detectIE: function () {
                var x = window.navigator.userAgent, e = x.indexOf('MSIE ');
                if (0 < e) return parseInt(x.substring(e + 5, x.indexOf('.', e)), 10);
                if (0 < x.indexOf('Trident/')) {
                    var a = x.indexOf('rv:');
                    return parseInt(x.substring(a + 3, x.indexOf('.', a)), 10)
                }
                a = x.indexOf('Edge/');
                return 0 < a && parseInt(x.substring(a + 5, x.indexOf('.', a)), 10)
            }
        }), D.a.LatLngUtil = {
            cloneLatLngs: function (x) {
                for (var e = [], a = 0, d = x.length; a < d; a++) Array.isArray(x[a]) ? e.push(D.a.LatLngUtil.cloneLatLngs(x[a])) : e.push(this.cloneLatLng(x[a]));
                return e
            }, cloneLatLng: function (x) {
                return D.a.latLng(x.lat, x.lng)
            }
        }, w = {
            km: 2,
            ha: 2,
            m: 0,
            mi: 2,
            ac: 2,
            yd: 0,
            ft: 0,
            nm: 2
        }, D.a.GeometryUtil = D.a.extend(D.a.GeometryUtil || {}, {
            geodesicArea: function (x) {
                var e, a, d = x.length, f = 0, t = Math.PI / 180;
                if (2 < d) {
                    for (var _ = 0; _ < d; _++) e = x[_], f += ((a = x[(_ + 1) % d]).lng - e.lng) * t * (2 + Math.sin(e.lat * t) + Math.sin(a.lat * t));
                    f = 6378137 * f * 6378137 / 2
                }
                return Math.abs(f)
            }, formattedNumber: function (x, e) {
                var a = parseFloat(x).toFixed(e), d = D.a.drawLocal.format && D.a.drawLocal.format.numeric,
                    x = d && d.delimiters, e = x && x.thousands, d = x && x.decimal;
                return (e || d) && (x = a.split('.'), a = e ? x[0].replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1' + e) : x[0], d = d || '.', 1 < x.length && (a = a + d + x[1])), a
            }, readableArea: function (x, e, a) {
                var d, f;
                return a = D.a.Util.extend({}, w, a), e ? (d = ['ha', 'm'], 'string' === (f = K()(e)) ? d = [e] : 'boolean' !== f && (d = e), 1e6 <= x && -1 !== d.indexOf('km') ? D.a.GeometryUtil.formattedNumber(1e-6 * x, a.km) + ' km²' : 1e4 <= x && -1 !== d.indexOf('ha') ? D.a.GeometryUtil.formattedNumber(1e-4 * x, a.ha) + ' ha' : D.a.GeometryUtil.formattedNumber(x, a.m) + ' m²') : 3097600 <= (x /= .836127) ? D.a.GeometryUtil.formattedNumber(x / 3097600, a.mi) + ' mi²' : 4840 <= x ? D.a.GeometryUtil.formattedNumber(x / 4840, a.ac) + ' acres' : D.a.GeometryUtil.formattedNumber(x, a.yd) + ' yd²'
            }, readableDistance: function (x, e, a, d, f) {
                var t;
                switch (f = D.a.Util.extend({}, w, f), e ? 'string' == _typeof(e) ? e : 'metric' : a ? 'feet' : d ? 'nauticalMile' : 'yards') {
                    case 'metric':
                        t = 1e3 < x ? D.a.GeometryUtil.formattedNumber(x / 1e3, f.km) + ' km' : D.a.GeometryUtil.formattedNumber(x, f.m) + ' m';
                        break;
                    case 'feet':
                        x *= 3.28083, t = D.a.GeometryUtil.formattedNumber(x, f.ft) + ' ft';
                        break;
                    case 'nauticalMile':
                        x *= .53996, t = D.a.GeometryUtil.formattedNumber(x / 1e3, f.nm) + ' nm';
                        break;
                    case'yards':
                    default:
                        t = 1760 < (x *= 1.09361) ? D.a.GeometryUtil.formattedNumber(x / 1760, f.mi) + ' miles' : D.a.GeometryUtil.formattedNumber(x, f.yd) + ' yd'
                }
                return t
            }, isVersion07x: function () {
                var x = D.a.version.split('.');
                return 0 === parseInt(x[0], 10) && 7 === parseInt(x[1], 10)
            }
        }), D.a.Util.extend(D.a.LineUtil, {
            segmentsIntersect: function (x, e, a, d) {
                return this._checkCounterclockwise(x, a, d) !== this._checkCounterclockwise(e, a, d) && this._checkCounterclockwise(x, e, a) !== this._checkCounterclockwise(x, e, d)
            }, _checkCounterclockwise: function (x, e, a) {
                return (a.y - x.y) * (e.x - x.x) > (e.y - x.y) * (a.x - x.x)
            }
        }), D.a.Polyline.include({
            intersects: function () {
                var x, e, a, d = this._getProjectedPoints(), f = d ? d.length : 0;
                if (this._tooFewPointsForIntersection()) return !1;
                for (x = f - 1; 3 <= x; x--) if (e = d[x - 1], a = d[x], this._lineSegmentsIntersectsRange(e, a, x - 2)) return !0;
                return !1
            }, newLatLngIntersects: function (x, e) {
                return !!this._map && this.newPointIntersects(this._map.latLngToLayerPoint(x), e)
            }, newPointIntersects: function (x, e) {
                var a = this._getProjectedPoints(), d = a ? a.length : 0, a = a ? a[d - 1] : null, d = d - 2;
                return !this._tooFewPointsForIntersection(1) && this._lineSegmentsIntersectsRange(a, x, d, e ? 1 : 0)
            }, _tooFewPointsForIntersection: function (x) {
                var e = this._getProjectedPoints(), a = e ? e.length : 0;
                return !e || (a += x || 0) <= 3
            }, _lineSegmentsIntersectsRange: function (x, e, a, d) {
                var f, t, _ = this._getProjectedPoints();
                d = d || 0;
                for (var i = a; d < i; i--) if (f = _[i - 1], t = _[i], D.a.LineUtil.segmentsIntersect(x, e, f, t)) return !0;
                return !1
            }, _getProjectedPoints: function () {
                if (!this._defaultShape) return this._originalPoints;
                for (var x = [], e = this._defaultShape(), a = 0; a < e.length; a++) x.push(this._map.latLngToLayerPoint(e[a]));
                return x
            }
        }), D.a.Polygon.include({
            intersects: function () {
                var x, e, a = this._getProjectedPoints();
                return !this._tooFewPointsForIntersection() && (!!D.a.Polyline.prototype.intersects.call(this) || (e = a.length, x = a[0], a = a[e - 1], e = e - 2, this._lineSegmentsIntersectsRange(a, x, e, 1)))
            }
        }), D.a.Control.Draw = D.a.Control.extend({
            options: {position: 'topleft', draw: {}, edit: !1}, initialize: function (x) {
                if (D.a.version < '0.7') throw new Error('Leaflet.draw 0.2.3+ requires Leaflet 0.7.0+. Download latest from https://github.com/Leaflet/Leaflet/');
                var e;
                D.a.Control.prototype.initialize.call(this, x), this._toolbars = {}, D.a.DrawToolbar && this.options.draw && (e = new D.a.DrawToolbar(this.options.draw), this._toolbars[D.a.DrawToolbar.TYPE] = e, this._toolbars[D.a.DrawToolbar.TYPE].on('enable', this._toolbarEnabled, this)), D.a.EditToolbar && this.options.edit && (e = new D.a.EditToolbar(this.options.edit), this._toolbars[D.a.EditToolbar.TYPE] = e, this._toolbars[D.a.EditToolbar.TYPE].on('enable', this._toolbarEnabled, this)), D.a.toolbar = this
            }, onAdd: function (x) {
                var e, a, d = D.a.DomUtil.create('div', 'leaflet-draw'), f = !1;
                for (a in this._toolbars) this._toolbars.hasOwnProperty(a) && (e = this._toolbars[a].addToolbar(x)) && (f || (D.a.DomUtil.hasClass(e, 'leaflet-draw-toolbar-top') || D.a.DomUtil.addClass(e.childNodes[0], 'leaflet-draw-toolbar-top'), f = !0), d.appendChild(e));
                return d
            }, onRemove: function () {
                for (var x in this._toolbars) this._toolbars.hasOwnProperty(x) && this._toolbars[x].removeToolbar()
            }, setDrawingOptions: function (x) {
                for (var e in this._toolbars) this._toolbars[e] instanceof D.a.DrawToolbar && this._toolbars[e].setOptions(x)
            }, _toolbarEnabled: function (x) {
                var e, a = x.target;
                for (e in this._toolbars) this._toolbars[e] !== a && this._toolbars[e].disable()
            }
        }), D.a.Map.mergeOptions({drawControlTooltips: !0, drawControl: !1}), D.a.Map.addInitHook(function () {
            this.options.drawControl && (this.drawControl = new D.a.Control.Draw, this.addControl(this.drawControl))
        }), D.a.Toolbar = D.a.Class.extend({
            initialize: function (x) {
                D.a.setOptions(this, x), this._modes = {}, this._actionButtons = [], this._activeMode = null;
                x = D.a.version.split('.');
                1 === parseInt(x[0], 10) && 2 <= parseInt(x[1], 10) ? D.a.Toolbar.include(D.a.Evented.prototype) : D.a.Toolbar.include(D.a.Mixin.Events)
            }, enabled: function () {
                return null !== this._activeMode
            }, disable: function () {
                this.enabled() && this._activeMode.handler.disable()
            }, addToolbar: function (x) {
                var e, a = D.a.DomUtil.create('div', 'leaflet-draw-section'), d = 0, f = this._toolbarClass || '',
                    t = this.getModeHandlers(x);
                for (this._toolbarContainer = D.a.DomUtil.create('div', 'leaflet-draw-toolbar leaflet-bar'), this._map = x, e = 0; e < t.length; e++) t[e].enabled && this._initModeHandler(t[e].handler, this._toolbarContainer, d++, f, t[e].title);
                if (d) return this._lastButtonIndex = --d, this._actionsContainer = D.a.DomUtil.create('ul', 'leaflet-draw-actions'), a.appendChild(this._toolbarContainer), a.appendChild(this._actionsContainer), a
            }, removeToolbar: function () {
                for (var x in this._modes) this._modes.hasOwnProperty(x) && (this._disposeButton(this._modes[x].button, this._modes[x].handler.enable, this._modes[x].handler), this._modes[x].handler.disable(), this._modes[x].handler.off('enabled', this._handlerActivated, this).off('disabled', this._handlerDeactivated, this));
                this._modes = {};
                for (var e = 0, a = this._actionButtons.length; e < a; e++) this._disposeButton(this._actionButtons[e].button, this._actionButtons[e].callback, this);
                this._actionButtons = [], this._actionsContainer = null
            }, _initModeHandler: function (x, e, a, d, f) {
                var t = x.type;
                this._modes[t] = {}, this._modes[t].handler = x, this._modes[t].button = this._createButton({
                    type: t,
                    title: f,
                    className: d + '-' + t,
                    container: e,
                    callback: this._modes[t].handler.enable,
                    context: this._modes[t].handler
                }), this._modes[t].buttonIndex = a, this._modes[t].handler.on('enabled', this._handlerActivated, this).on('disabled', this._handlerDeactivated, this)
            }, _detectIOS: function () {
                return /iPad|iPhone|iPod/.test(navigator.userAgent) && !window.MSStream
            }, _createButton: function (x) {
                var e = D.a.DomUtil.create('a', x.className || '', x.container),
                    a = D.a.DomUtil.create('span', 'sr-only', x.container);
                e.href = '#', e.appendChild(a), x.title && (e.title = x.title, a.innerHTML = x.title), x.text && (e.innerHTML = x.text, a.innerHTML = x.text);
                a = this._detectIOS() ? 'touchstart' : 'click';
                return D.a.DomEvent.on(e, 'click', D.a.DomEvent.stopPropagation).on(e, 'mousedown', D.a.DomEvent.stopPropagation).on(e, 'dblclick', D.a.DomEvent.stopPropagation).on(e, 'touchstart', D.a.DomEvent.stopPropagation).on(e, 'click', D.a.DomEvent.preventDefault).on(e, a, x.callback, x.context), e
            }, _disposeButton: function (x, e) {
                var a = this._detectIOS() ? 'touchstart' : 'click';
                D.a.DomEvent.off(x, 'click', D.a.DomEvent.stopPropagation).off(x, 'mousedown', D.a.DomEvent.stopPropagation).off(x, 'dblclick', D.a.DomEvent.stopPropagation).off(x, 'touchstart', D.a.DomEvent.stopPropagation).off(x, 'click', D.a.DomEvent.preventDefault).off(x, a, e)
            }, _handlerActivated: function (x) {
                this.disable(), this._activeMode = this._modes[x.handler], D.a.DomUtil.addClass(this._activeMode.button, 'leaflet-draw-toolbar-button-enabled'), this._showActionsToolbar(), this.fire('enable')
            }, _handlerDeactivated: function () {
                this._hideActionsToolbar(), D.a.DomUtil.removeClass(this._activeMode.button, 'leaflet-draw-toolbar-button-enabled'), this._activeMode = null, this.fire('disable')
            }, _createActions: function (x) {
                for (var e, a = this._actionsContainer, d = this.getActions(x), f = d.length, t = 0, _ = this._actionButtons.length; t < _; t++) this._disposeButton(this._actionButtons[t].button, this._actionButtons[t].callback);
                for (this._actionButtons = []; a.firstChild;) a.removeChild(a.firstChild);
                for (var i = 0; i < f; i++) 'enabled' in d[i] && !d[i].enabled || (e = D.a.DomUtil.create('li', '', a), e = this._createButton({
                    title: d[i].title,
                    text: d[i].text,
                    container: e,
                    callback: d[i].callback,
                    context: d[i].context
                }), this._actionButtons.push({button: e, callback: d[i].callback}))
            }, _showActionsToolbar: function () {
                var x = this._activeMode.buttonIndex, e = this._lastButtonIndex,
                    a = this._activeMode.button.offsetTop - 1;
                this._createActions(this._activeMode.handler), this._actionsContainer.style.top = a + 'px', 0 === x && (D.a.DomUtil.addClass(this._toolbarContainer, 'leaflet-draw-toolbar-notop'), D.a.DomUtil.addClass(this._actionsContainer, 'leaflet-draw-actions-top')), x === e && (D.a.DomUtil.addClass(this._toolbarContainer, 'leaflet-draw-toolbar-nobottom'), D.a.DomUtil.addClass(this._actionsContainer, 'leaflet-draw-actions-bottom')), this._actionsContainer.style.display = 'block', this._map.fire(D.a.Draw.Event.TOOLBAROPENED)
            }, _hideActionsToolbar: function () {
                this._actionsContainer.style.display = 'none', D.a.DomUtil.removeClass(this._toolbarContainer, 'leaflet-draw-toolbar-notop'), D.a.DomUtil.removeClass(this._toolbarContainer, 'leaflet-draw-toolbar-nobottom'), D.a.DomUtil.removeClass(this._actionsContainer, 'leaflet-draw-actions-top'), D.a.DomUtil.removeClass(this._actionsContainer, 'leaflet-draw-actions-bottom'), this._map.fire(D.a.Draw.Event.TOOLBARCLOSED)
            }
        }), D.a.Draw = D.a.Draw || {}, D.a.Draw.Tooltip = D.a.Class.extend({
            initialize: function (x) {
                this._map = x, this._popupPane = x._panes.popupPane, this._visible = !1, this._container = x.options.drawControlTooltips ? D.a.DomUtil.create('div', 'leaflet-draw-tooltip', this._popupPane) : null, this._singleLineLabel = !1, this._map.on('mouseout', this._onMouseOut, this)
            }, dispose: function () {
                this._map.off('mouseout', this._onMouseOut, this), this._container && (this._popupPane.removeChild(this._container), this._container = null)
            }, updateContent: function (x) {
                return this._container && (x.subtext = x.subtext || '', 0 !== x.subtext.length || this._singleLineLabel ? 0 < x.subtext.length && this._singleLineLabel && (D.a.DomUtil.removeClass(this._container, 'leaflet-draw-tooltip-single'), this._singleLineLabel = !1) : (D.a.DomUtil.addClass(this._container, 'leaflet-draw-tooltip-single'), this._singleLineLabel = !0), this._container.innerHTML = (0 < x.subtext.length ? '<span class="leaflet-draw-tooltip-subtext">' + x.subtext + '</span><br />' : '') + '<span>' + x.text + '</span>', x.text || x.subtext ? (this._visible = !0, this._container.style.visibility = 'inherit') : (this._visible = !1, this._container.style.visibility = 'hidden')), this
            }, updatePosition: function (x) {
                var e = this._map.latLngToLayerPoint(x), x = this._container;
                return this._container && (this._visible && (x.style.visibility = 'inherit'), D.a.DomUtil.setPosition(x, e)), this
            }, showAsError: function () {
                return this._container && D.a.DomUtil.addClass(this._container, 'leaflet-error-draw-tooltip'), this
            }, removeError: function () {
                return this._container && D.a.DomUtil.removeClass(this._container, 'leaflet-error-draw-tooltip'), this
            }, _onMouseOut: function () {
                this._container && (this._container.style.visibility = 'hidden')
            }
        }), D.a.DrawToolbar = D.a.Toolbar.extend({
            statics: {TYPE: 'draw'},
            options: {polyline: {}, polygon: {}, rectangle: {}, circle: {}, marker: {}, circlemarker: {}},
            initialize: function (x) {
                for (var e in this.options) this.options.hasOwnProperty(e) && x[e] && (x[e] = D.a.extend({}, this.options[e], x[e]));
                this._toolbarClass = 'leaflet-draw-draw', D.a.Toolbar.prototype.initialize.call(this, x)
            },
            getModeHandlers: function (x) {
                return [{
                    enabled: this.options.polyline,
                    handler: new D.a.Draw.Polyline(x, this.options.polyline),
                    title: D.a.drawLocal.draw.toolbar.buttons.polyline
                }, {
                    enabled: this.options.polygon,
                    handler: new D.a.Draw.Polygon(x, this.options.polygon),
                    title: D.a.drawLocal.draw.toolbar.buttons.polygon
                }, {
                    enabled: this.options.rectangle,
                    handler: new D.a.Draw.Rectangle(x, this.options.rectangle),
                    title: D.a.drawLocal.draw.toolbar.buttons.rectangle
                }, {
                    enabled: this.options.circle,
                    handler: new D.a.Draw.Circle(x, this.options.circle),
                    title: D.a.drawLocal.draw.toolbar.buttons.circle
                }, {
                    enabled: this.options.marker,
                    handler: new D.a.Draw.Marker(x, this.options.marker),
                    title: D.a.drawLocal.draw.toolbar.buttons.marker
                }, {
                    enabled: this.options.circlemarker,
                    handler: new D.a.Draw.CircleMarker(x, this.options.circlemarker),
                    title: D.a.drawLocal.draw.toolbar.buttons.circlemarker
                }]
            },
            getActions: function (x) {
                return [{
                    enabled: x.completeShape,
                    title: D.a.drawLocal.draw.toolbar.finish.title,
                    text: D.a.drawLocal.draw.toolbar.finish.text,
                    callback: x.completeShape,
                    context: x
                }, {
                    enabled: x.deleteLastVertex,
                    title: D.a.drawLocal.draw.toolbar.undo.title,
                    text: D.a.drawLocal.draw.toolbar.undo.text,
                    callback: x.deleteLastVertex,
                    context: x
                }, {
                    title: D.a.drawLocal.draw.toolbar.actions.title,
                    text: D.a.drawLocal.draw.toolbar.actions.text,
                    callback: this.disable,
                    context: this
                }]
            },
            setOptions: function (x) {
                for (var e in D.a.setOptions(this, x), this._modes) this._modes.hasOwnProperty(e) && x.hasOwnProperty(e) && this._modes[e].handler.setOptions(x[e])
            }
        }), D.a.EditToolbar = D.a.Toolbar.extend({
            statics: {TYPE: 'edit'},
            options: {
                edit: {
                    selectedPathOptions: {
                        dashArray: '10, 10',
                        fill: !0,
                        fillColor: '#fe57a1',
                        fillOpacity: .1,
                        maintainColor: !1
                    }
                }, remove: {}, poly: null, featureGroup: null
            },
            initialize: function (x) {
                x.edit && (void 0 === x.edit.selectedPathOptions && (x.edit.selectedPathOptions = this.options.edit.selectedPathOptions), x.edit.selectedPathOptions = D.a.extend({}, this.options.edit.selectedPathOptions, x.edit.selectedPathOptions)), x.remove && (x.remove = D.a.extend({}, this.options.remove, x.remove)), x.poly && (x.poly = D.a.extend({}, this.options.poly, x.poly)), this._toolbarClass = 'leaflet-draw-edit', D.a.Toolbar.prototype.initialize.call(this, x), this._selectedFeatureCount = 0
            },
            getModeHandlers: function (x) {
                var e = this.options.featureGroup;
                return [{
                    enabled: this.options.edit,
                    handler: new D.a.EditToolbar.Edit(x, {
                        featureGroup: e,
                        selectedPathOptions: this.options.edit.selectedPathOptions,
                        poly: this.options.poly
                    }),
                    title: D.a.drawLocal.edit.toolbar.buttons.edit
                }, {
                    enabled: this.options.remove,
                    handler: new D.a.EditToolbar.Delete(x, {featureGroup: e}),
                    title: D.a.drawLocal.edit.toolbar.buttons.remove
                }]
            },
            getActions: function (x) {
                var e = [{
                    title: D.a.drawLocal.edit.toolbar.actions.save.title,
                    text: D.a.drawLocal.edit.toolbar.actions.save.text,
                    callback: this._save,
                    context: this
                }, {
                    title: D.a.drawLocal.edit.toolbar.actions.cancel.title,
                    text: D.a.drawLocal.edit.toolbar.actions.cancel.text,
                    callback: this.disable,
                    context: this
                }];
                return x.removeAllLayers && e.push({
                    title: D.a.drawLocal.edit.toolbar.actions.clearAll.title,
                    text: D.a.drawLocal.edit.toolbar.actions.clearAll.text,
                    callback: this._clearAllLayers,
                    context: this
                }), e
            },
            addToolbar: function (x) {
                x = D.a.Toolbar.prototype.addToolbar.call(this, x);
                return this._checkDisabled(), this.options.featureGroup.on('layeradd layerremove', this._checkDisabled, this), x
            },
            removeToolbar: function () {
                this.options.featureGroup.off('layeradd layerremove', this._checkDisabled, this), D.a.Toolbar.prototype.removeToolbar.call(this)
            },
            disable: function () {
                this.enabled() && (this._activeMode.handler.revertLayers(), D.a.Toolbar.prototype.disable.call(this))
            },
            _save: function () {
                this._activeMode.handler.save(), this._activeMode && this._activeMode.handler.disable()
            },
            _clearAllLayers: function () {
                this._activeMode.handler.removeAllLayers(), this._activeMode && this._activeMode.handler.disable()
            },
            _checkDisabled: function () {
                var x, e = 0 !== this.options.featureGroup.getLayers().length;
                this.options.edit && (x = this._modes[D.a.EditToolbar.Edit.TYPE].button, e ? D.a.DomUtil.removeClass(x, 'leaflet-disabled') : D.a.DomUtil.addClass(x, 'leaflet-disabled'), x.setAttribute('title', e ? D.a.drawLocal.edit.toolbar.buttons.edit : D.a.drawLocal.edit.toolbar.buttons.editDisabled)), this.options.remove && (x = this._modes[D.a.EditToolbar.Delete.TYPE].button, e ? D.a.DomUtil.removeClass(x, 'leaflet-disabled') : D.a.DomUtil.addClass(x, 'leaflet-disabled'), x.setAttribute('title', e ? D.a.drawLocal.edit.toolbar.buttons.remove : D.a.drawLocal.edit.toolbar.buttons.removeDisabled))
            }
        }), D.a.EditToolbar.Edit = D.a.Handler.extend({
            statics: {TYPE: 'edit'}, initialize: function (x, e) {
                if (D.a.Handler.prototype.initialize.call(this, x), D.a.setOptions(this, e), this._featureGroup = e.featureGroup, !(this._featureGroup instanceof D.a.FeatureGroup)) throw new Error('options.featureGroup must be a L.FeatureGroup');
                this._uneditedLayerProps = {}, this.type = D.a.EditToolbar.Edit.TYPE;
                e = D.a.version.split('.');
                1 === parseInt(e[0], 10) && 2 <= parseInt(e[1], 10) ? D.a.EditToolbar.Edit.include(D.a.Evented.prototype) : D.a.EditToolbar.Edit.include(D.a.Mixin.Events)
            }, enable: function () {
                !this._enabled && this._hasAvailableLayers() && (this.fire('enabled', {handler: this.type}), this._map.fire(D.a.Draw.Event.EDITSTART, {handler: this.type}), D.a.Handler.prototype.enable.call(this), this._featureGroup.on('layeradd', this._enableLayerEdit, this).on('layerremove', this._disableLayerEdit, this))
            }, disable: function () {
                this._enabled && (this._featureGroup.off('layeradd', this._enableLayerEdit, this).off('layerremove', this._disableLayerEdit, this), D.a.Handler.prototype.disable.call(this), this._map.fire(D.a.Draw.Event.EDITSTOP, {handler: this.type}), this.fire('disabled', {handler: this.type}))
            }, addHooks: function () {
                var x = this._map;
                x && (x.getContainer().focus(), this._featureGroup.eachLayer(this._enableLayerEdit, this), this._tooltip = new D.a.Draw.Tooltip(this._map), this._tooltip.updateContent({
                    text: D.a.drawLocal.edit.handlers.edit.tooltip.text,
                    subtext: D.a.drawLocal.edit.handlers.edit.tooltip.subtext
                }), x._editTooltip = this._tooltip, this._updateTooltip(), this._map.on('mousemove', this._onMouseMove, this).on('touchmove', this._onMouseMove, this).on('MSPointerMove', this._onMouseMove, this).on(D.a.Draw.Event.EDITVERTEX, this._updateTooltip, this))
            }, removeHooks: function () {
                this._map && (this._featureGroup.eachLayer(this._disableLayerEdit, this), this._uneditedLayerProps = {}, this._tooltip.dispose(), this._tooltip = null, this._map.off('mousemove', this._onMouseMove, this).off('touchmove', this._onMouseMove, this).off('MSPointerMove', this._onMouseMove, this).off(D.a.Draw.Event.EDITVERTEX, this._updateTooltip, this))
            }, revertLayers: function () {
                this._featureGroup.eachLayer(function (x) {
                    this._revertLayer(x)
                }, this)
            }, save: function () {
                var e = new D.a.LayerGroup;
                this._featureGroup.eachLayer(function (x) {
                    x.edited && (e.addLayer(x), x.edited = !1)
                }), this._map.fire(D.a.Draw.Event.EDITED, {layers: e})
            }, _backupLayer: function (x) {
                var e = D.a.Util.stamp(x);
                this._uneditedLayerProps[e] || (x instanceof D.a.Polyline || x instanceof D.a.Polygon || x instanceof D.a.Rectangle ? this._uneditedLayerProps[e] = {latlngs: D.a.LatLngUtil.cloneLatLngs(x.getLatLngs())} : x instanceof D.a.Circle ? this._uneditedLayerProps[e] = {
                    latlng: D.a.LatLngUtil.cloneLatLng(x.getLatLng()),
                    radius: x.getRadius()
                } : (x instanceof D.a.Marker || x instanceof D.a.CircleMarker) && (this._uneditedLayerProps[e] = {latlng: D.a.LatLngUtil.cloneLatLng(x.getLatLng())}))
            }, _getTooltipText: function () {
                return {
                    text: D.a.drawLocal.edit.handlers.edit.tooltip.text,
                    subtext: D.a.drawLocal.edit.handlers.edit.tooltip.subtext
                }
            }, _updateTooltip: function () {
                this._tooltip.updateContent(this._getTooltipText())
            }, _revertLayer: function (x) {
                var e = D.a.Util.stamp(x);
                x.edited = !1, this._uneditedLayerProps.hasOwnProperty(e) && (x instanceof D.a.Polyline || x instanceof D.a.Polygon || x instanceof D.a.Rectangle ? x.setLatLngs(this._uneditedLayerProps[e].latlngs) : x instanceof D.a.Circle ? (x.setLatLng(this._uneditedLayerProps[e].latlng), x.setRadius(this._uneditedLayerProps[e].radius)) : (x instanceof D.a.Marker || x instanceof D.a.CircleMarker) && x.setLatLng(this._uneditedLayerProps[e].latlng), x.fire('revert-edited', {layer: x}))
            }, _enableLayerEdit: function (x) {
                var e, x = x.layer || x.target || x;
                this._backupLayer(x), this.options.poly && (e = D.a.Util.extend({}, this.options.poly), x.options.poly = e), this.options.selectedPathOptions && ((e = D.a.Util.extend({}, this.options.selectedPathOptions)).maintainColor && (e.color = x.options.color, e.fillColor = x.options.fillColor), x.options.original = D.a.extend({}, x.options), x.options.editing = e), x instanceof D.a.Marker ? (x.editing && x.editing.enable(), x.dragging.enable(), x.on('dragend', this._onMarkerDragEnd).on('touchmove', this._onTouchMove, this).on('MSPointerMove', this._onTouchMove, this).on('touchend', this._onMarkerDragEnd, this).on('MSPointerUp', this._onMarkerDragEnd, this)) : x.editing.enable()
            }, _disableLayerEdit: function (x) {
                x = x.layer || x.target || x;
                x.edited = !1, x.editing && x.editing.disable(), delete x.options.editing, delete x.options.original, this._selectedPathOptions && (x instanceof D.a.Marker ? this._toggleMarkerHighlight(x) : (x.setStyle(x.options.previousOptions || {}), delete x.options.previousOptions)), x instanceof D.a.Marker ? (x.dragging.disable(), x.off('dragend', this._onMarkerDragEnd, this).off('touchmove', this._onTouchMove, this).off('MSPointerMove', this._onTouchMove, this).off('touchend', this._onMarkerDragEnd, this).off('MSPointerUp', this._onMarkerDragEnd, this)) : x.editing.disable()
            }, _onMouseMove: function (x) {
                this._tooltip.updatePosition(x.latlng)
            }, _onMarkerDragEnd: function (x) {
                x = x.target;
                x.edited = !0, this._map.fire(D.a.Draw.Event.EDITMOVE, {layer: x})
            }, _onTouchMove: function (x) {
                var e = x.originalEvent.changedTouches[0], e = this._map.mouseEventToLayerPoint(e),
                    e = this._map.layerPointToLatLng(e);
                x.target.setLatLng(e)
            }, _hasAvailableLayers: function () {
                return 0 !== this._featureGroup.getLayers().length
            }
        }), D.a.EditToolbar.Delete = D.a.Handler.extend({
            statics: {TYPE: 'remove'}, initialize: function (x, e) {
                if (D.a.Handler.prototype.initialize.call(this, x), D.a.Util.setOptions(this, e), this._deletableLayers = this.options.featureGroup, !(this._deletableLayers instanceof D.a.FeatureGroup)) throw new Error('options.featureGroup must be a L.FeatureGroup');
                this.type = D.a.EditToolbar.Delete.TYPE;
                e = D.a.version.split('.');
                1 === parseInt(e[0], 10) && 2 <= parseInt(e[1], 10) ? D.a.EditToolbar.Delete.include(D.a.Evented.prototype) : D.a.EditToolbar.Delete.include(D.a.Mixin.Events)
            }, enable: function () {
                !this._enabled && this._hasAvailableLayers() && (this.fire('enabled', {handler: this.type}), this._map.fire(D.a.Draw.Event.DELETESTART, {handler: this.type}), D.a.Handler.prototype.enable.call(this), this._deletableLayers.on('layeradd', this._enableLayerDelete, this).on('layerremove', this._disableLayerDelete, this))
            }, disable: function () {
                this._enabled && (this._deletableLayers.off('layeradd', this._enableLayerDelete, this).off('layerremove', this._disableLayerDelete, this), D.a.Handler.prototype.disable.call(this), this._map.fire(D.a.Draw.Event.DELETESTOP, {handler: this.type}), this.fire('disabled', {handler: this.type}))
            }, addHooks: function () {
                var x = this._map;
                x && (x.getContainer().focus(), this._deletableLayers.eachLayer(this._enableLayerDelete, this), this._deletedLayers = new D.a.LayerGroup, this._tooltip = new D.a.Draw.Tooltip(this._map), this._tooltip.updateContent({text: D.a.drawLocal.edit.handlers.remove.tooltip.text}), this._map.on('mousemove', this._onMouseMove, this))
            }, removeHooks: function () {
                this._map && (this._deletableLayers.eachLayer(this._disableLayerDelete, this), this._deletedLayers = null, this._tooltip.dispose(), this._tooltip = null, this._map.off('mousemove', this._onMouseMove, this))
            }, revertLayers: function () {
                this._deletedLayers.eachLayer(function (x) {
                    this._deletableLayers.addLayer(x), x.fire('revert-deleted', {layer: x})
                }, this)
            }, save: function () {
                this._map.fire(D.a.Draw.Event.DELETED, {layers: this._deletedLayers})
            }, removeAllLayers: function () {
                this._deletableLayers.eachLayer(function (x) {
                    this._removeLayer({layer: x})
                }, this), this.save()
            }, _enableLayerDelete: function (x) {
                (x.layer || x.target || x).on('click', this._removeLayer, this)
            }, _disableLayerDelete: function (x) {
                x = x.layer || x.target || x;
                x.off('click', this._removeLayer, this), this._deletedLayers.removeLayer(x)
            }, _removeLayer: function (x) {
                x = x.layer || x.target || x;
                this._deletableLayers.removeLayer(x), this._deletedLayers.addLayer(x), x.fire('deleted')
            }, _onMouseMove: function (x) {
                this._tooltip.updatePosition(x.latlng)
            }, _hasAvailableLayers: function () {
                return 0 !== this._deletableLayers.getLayers().length
            }
        }), D.a.drawLocal.draw.toolbar.actions = {
            title: '取消绘制',
            text: '取消'
        }, D.a.drawLocal.draw.toolbar.finish = {
            title: '完成绘制',
            text: '完成'
        }, D.a.drawLocal.draw.toolbar.undo = {
            title: '删除最后一个绘制的点',
            text: '删除最后的点'
        }, D.a.drawLocal.draw.toolbar.buttons = {
            polyline: '绘制折线',
            polygon: '绘制多边形',
            rectangle: '绘制矩形',
            circle: '绘制圆',
            marker: '标点'
        }, D.a.drawLocal.edit.toolbar.actions.save = {
            title: '保存修改.',
            text: '保存'
        }, D.a.drawLocal.edit.toolbar.actions.cancel = {
            title: '取消编辑，丢弃所有的修改',
            text: '取消'
        }, D.a.drawLocal.edit.toolbar.actions.clearAll = {
            title: '刪除所有要素',
            text: '刪除所有'
        }, D.a.drawLocal.edit.toolbar.buttons = {
            edit: '编辑要素',
            editDisabled: '没有需要编辑的要素',
            remove: '删除要素',
            removeDisabled: '没有需要删除的要素'
        }, D.a.drawLocal.draw.handlers.circle = {
            tooltip: {start: '单击并拖动到绘制圆'},
            radius: '半径'
        }, D.a.drawLocal.draw.handlers.marker = {tooltip: {start: '单击地图标记点'}}, D.a.drawLocal.draw.handlers.polygon = {
            tooltip: {
                start: '单击开始绘制形状',
                cont: '单击继续绘制形状',
                end: '单击继续绘制，双击完成绘制'
            }
        }, D.a.drawLocal.draw.handlers.polyline = {
            error: '<strong>错误:</strong> 面积边缘不可交叉!',
            tooltip: {start: '单击开始画线', cont: '单击继续画线', end: '单击继续画线,双击完成绘制'}
        }, D.a.drawLocal.draw.handlers.rectangle = {tooltip: {start: '单击并拖动绘制矩形'}}, D.a.drawLocal.draw.handlers.simpleshape = {tooltip: {end: '释放鼠标完成绘图'}}, D.a.drawLocal.edit.handlers.edit = {
            tooltip: {
                text: '拖动标记或白色小框进行编辑修改',
                subtext: ''
            }
        }, D.a.drawLocal.edit.handlers.remove = {tooltip: {text: '单击目标进行删除'}}, D.a.Edit.Marker.prototype._toggleMarkerHighlight = function () {
        }, D.a.Draw.Text = D.a.Draw.Marker.extend({
            statics: {TYPE: 'text'},
            options: {repeatMode: !1},
            initialize: function (x, e) {
                (e = e || {}).icon = this.getIcon(), this.type = D.a.Draw.Text.TYPE, D.a.Draw.Feature.prototype.initialize.call(this, x, e)
            },
            updateIcon: function (x) {
                this.options.icon = this.getIcon(x)
            },
            defaultIconAttr: {
                text: '文字',
                color: '#0081c2',
                font_size: 25,
                font_family: '黑体',
                font_style: 'normal',
                font_weight: 'normal',
                background: !1,
                border: !1
            },
            getIcon: function (x) {
                (x = x || this.defaultIconAttr).text = x.text || this.defaultIconAttr.text;
                var e = this.getTextSize(x), a = this.getTextCSS(x);
                return D.a.divIcon({
                    className: 'leaflet-text-marker',
                    iconSize: [e.width, e.height],
                    iconAnchor: [e.width / 2, e.height / 2],
                    html: '<div style="' + a + '\'>' + x.text + '</div>'
                })
            },
            getTextSize: function (x) {
                for (var e = x.text.split('<br />'), a = -1, d = 0; d < e.length; d++) {
                    var f = e[d].replace(/[\u0391-\uFFE5]/g, 'aa').length;
                    a < f && (a = f)
                }
                return {
                    width: a * (x.font_size || this.defaultIconAttr.font_size) / 2 + 10,
                    height: e.length * (x.font_size || this.defaultIconAttr.font_size) * 1.5 + 10
                }
            },
            getTextCSS: function (x) {
                var e = 'display:table; padding:5px; color:' + (x.color || this.defaultIconAttr.color) + ';    font-size:' + (x.font_size || this.defaultIconAttr.font_size) + 'px;  font-family:' + (x.font_family || this.defaultIconAttr.font_family) + ';   font-style:' + (x.font_style || this.defaultIconAttr.font_style) + ';    font-weight:' + (x.font_weight || this.defaultIconAttr.font_weight) + ';';
                return x.background && (e += 'background-color: ' + x.background_color + ';  '), x.border && (e += 'border: ' + x.border_width + 'px  ' + x.border_style + ' ' + x.border_color + ' ;  '), e
            },
            _createMarker: function (x) {
                return new D.a.Marker(x, {
                    draggable: !0,
                    icon: this.options.icon,
                    zIndexOffset: this.options.zIndexOffset
                })
            },
            _onMouseMove: function (x) {
                x = x.latlng;
                this._mouseMarker && this._mouseMarker.setLatLng(x), this._marker ? (this._mouseMarker && (x = this._mouseMarker.getLatLng()), this._marker.setLatLng(x)) : (this._marker = this._createMarker(x), this._marker.on('click', this._onClick, this), this._map.on('click', this._onClick, this).addLayer(this._marker))
            }
        }), D.a.Draw.FontMarker = D.a.Draw.Marker.extend({
            statics: {TYPE: 'font-marker'}, initialize: function (x, e) {
                (e = e || {}).icon = this.getIcon(), this.type = D.a.Draw.FontMarker.TYPE, D.a.Draw.Feature.prototype.initialize.call(this, x, e)
            }, updateIcon: function (x) {
                this.options.icon = this.getIcon(x)
            }, defaultIconAttr: {size: 50, color: '#0081c2', iconClass: 'fa fa-crosshairs'}, getIcon: function (x) {
                return (x = x || this.defaultIconAttr).size = x.size || this.defaultIconAttr.size, x.color = x.color || this.defaultIconAttr.color, x.iconClass = x.iconClass || this.defaultIconAttr.iconClass, D.a.divIcon({
                    className: '',
                    html: '<i class="' + x.iconClass + '" style="color:' + x.color + ';font-size:' + x.size + 'px;"></i> ',
                    iconSize: [x.size + 2, x.size + 2]
                })
            }
        }), D.a.ImageOverlay = D.a.ImageOverlay.extend({
            getLatLngs: function () {
                return this._boundsToLatLngs(this.getBounds())
            }, _boundsToLatLngs: function (x) {
                return [(x = D.a.latLngBounds(x)).getSouthWest(), x.getNorthWest(), x.getNorthEast(), x.getSouthEast()]
            }, toGeoJSON: function () {
                var x = this.getLatLngs(), e = !D.a.LineUtil.isFlat(x), a = e && !D.a.LineUtil.isFlat(x[0]),
                    x = D.a.json.latLngsToCoords(x, a ? 2 : e ? 1 : 0, !0);
                return e || (x = [x]), D.a.json.getFeature(this, {type: (a ? 'Multi' : '') + 'Polygon', coordinates: x})
            }
        }), D.a.Draw.Image = D.a.Draw.Rectangle.extend({
            statics: {TYPE: 'image'},
            _defstyle: {weight: 1, fill: !0, fillOpacity: .1, color: '#ffffff', showArea: !1, clickable: !0},
            options: {iconUrl: '', opacity: 1, metric: !0},
            initialize: function (x, e) {
                this.type = D.a.Draw.Image.TYPE, this._initialLabelText = D.a.drawLocal.draw.handlers.rectangle.tooltip.start, D.a.Draw.SimpleShape.prototype.initialize.call(this, x, e)
            },
            _drawShape: function (x) {
                this._shape ? this._shape.setBounds(new D.a.LatLngBounds(this._startLatLng, x)) : (this._shape = new D.a.ImageOverlay(this.options.iconUrl, new D.a.LatLngBounds(this._startLatLng, x), {opacity: this.options.opacity}), this._map.addLayer(this._shape))
            },
            _fireCreatedEvent: function () {
                var x = new D.a.Rectangle(this._shape.getBounds(), this._defstyle),
                    e = new D.a.ImageOverlay(this.options.iconUrl, this._shape.getBounds(), {opacity: this.options.opacity});
                x._imageOverlay = e, D.a.Draw.SimpleShape.prototype._fireCreatedEvent.call(this, x)
            }
        }), D.a.Draw.Polylinefree = D.a.Draw.SimpleShape.extend({
            statics: {TYPE: 'polylinefree'},
            options: {
                allowIntersection: !0,
                shapeOptions: {stroke: !0, color: '#f06eaa', weight: 4, opacity: .5, fill: !1, clickable: !0},
                nautic: !1
            },
            initialize: function (x, e) {
                e && e.drawError && (e.drawError = D.a.Util.extend({}, this.options.drawError, e.drawError)), this.type = D.a.Draw.Polylinefree.TYPE, D.a.Draw.Feature.prototype.initialize.call(this, x, e)
            },
            _onMouseMove: function (x) {
                this._isDrawing && this._drawShape(x.latlng)
            },
            _drawShape: function (x) {
                this._shape ? this._shape.addLatLng(x) : (this._shape = new D.a.Polyline([], this.options.shapeOptions), this._map.addLayer(this._shape))
            },
            _fireCreatedEvent: function () {
                var x = new D.a.Polyline(this._shape.getLatLngs(), this.options.shapeOptions);
                D.a.Draw.Feature.prototype._fireCreatedEvent.call(this, x)
            }
        }), a(23), D.a.Control.ToolBar = D.a.Control.extend({
            options: {position: 'topleft'},
            _createButton: function (x, e, a, d) {
                a = D.a.DomUtil.create('a', e, a);
                return a.href = '#', a.title = x, D.a.DomEvent.on(a, 'mousedown dblclick', D.a.DomEvent.stopPropagation).on(a, 'click', D.a.DomEvent.stop).on(a, 'click', d, this).on(a, 'click', this._refocusOnMap, this), a
            },
            onAdd: function (x) {
                this.options.center || (this.options.center = x.getCenter()), this.options.zoom || (this.options.zoom = x.getZoom());
                var e = 'leaflet-control-toolbar', a = D.a.DomUtil.create('div', e + '  leaflet-bar leaflet-control');
                this.options.item = this.options.item || ['home', 'location', 'fullscreen'];
                for (var d = 0; d < this.options.item.length; d++) switch (this.options.item[d]) {
                    case'home':
                        this._createButton('回到默认视图区域', e + '-home', a, D.a.Control.ToolBar.goHome || this._btnclick_goHome);
                        break;
                    case 'location':
                        this.btnLocation = this._createButton('定位至当前所在位置', e + '-locate', a, D.a.Control.ToolBar.goLocate || this._btnclick_goLocate), this._map.on('locationfound', this.onLocationFound, this), this._map.on('locationerror', this.onLocationError, this);
                        break;
                    case'fullscreen':
                        this.btnFullscreen = this._createButton('进入全屏', e + '-fullscreen', a, D.a.Control.ToolBar.fullscreen || this._btnclick_fullscreen), this._map.on('fullscreenchange', this._toggle_fullscreen_Title, this);
                        break;
                    case'clear':
                        this._createButton('清除所有操作', e + '-clear', a, D.a.Control.ToolBar.clear || this._btnclick_clear)
                }
                return a
            },
            _btnclick_goHome: function (x) {
                if (this.options.bbox) try {
                    this._map.fitBounds(this.options.bbox)
                } catch (x) {
                    this._map.setView(this.options.center, this.options.zoom)
                }
                this._map.setView(this.options.center, this.options.zoom)
            },
            _btnclick_clear: function (x) {
                null != window.clearALL && window.clearALL()
            },
            btnLocation: null,
            _location_mark: null,
            _location_circle: null,
            _iconLocationLoading: 'leaflet-control-toolbar-locate-loading',
            _iconLocation: 'leaflet-control-toolbar-locate',
            _btnclick_goLocate: function (x) {
                D.a.DomUtil.removeClasses(this.btnLocation, this._iconLocation), D.a.DomUtil.addClasses(this.btnLocation, this._iconLocationLoading), this._map.locate({setView: !0})
            },
            onLocationFound: function (x) {
                var e = this;
                D.a.DomUtil.removeClasses(this.btnLocation, this._iconLocationLoading), D.a.DomUtil.addClasses(this.btnLocation, this._iconLocation);
                var a = x.accuracy / 2;
                1e3 < a && (a = 1e3);
                var d = this._map.convert2map(x.latlng);
                setTimeout(function () {
                    e._map.setView(d, 16)
                }, 500), this.options.noLocPoint || (null != this._location_mark && this._location_mark.remove(), null != this._location_circle && this._location_circle.remove(), this._location_mark = D.a.marker(d, {
                    icon: D.a.divIcon({
                        className: '',
                        iconSize: [10, 10],
                        iconAnchor: [5, 5],
                        popupAnchor: [5, -5],
                        tooltipAnchor: [5, -5],
                        html: '<div class="centerat_animation" style="color:#0f89f5;"><p></p></div>'
                    })
                }).addTo(this._map), this._location_circle = D.a.circle(d, {
                    radius: a,
                    stroke: !1,
                    fillOpacity: .2
                }).addTo(this._map), setTimeout(function () {
                    e._location_mark.remove(), e._location_circle.remove(), e._location_mark = null, e._location_circle = null
                }, 5e3))
            },
            onLocationError: function (x) {
                D.a.DomUtil.removeClasses(this.btnLocation, this._iconLocationLoading), D.a.DomUtil.addClasses(this.btnLocation, this._iconLocation), this.options.noLocPoint || Object(q.msg)('获取地理位置失败：' + (x.message || '').replace('Geolocation error: ') + '(code:' + x.code + ')')
            },
            btnFullscreen: null,
            _btnclick_fullscreen: function (x) {
                D.a.DomEvent.stopPropagation(x), D.a.DomEvent.preventDefault(x), this._map.toggleFullscreen(this.options)
            },
            _toggle_fullscreen_Title: function () {
                this.btnFullscreen.title = this._map.isFullscreen() ? '退出全屏' : '进入全屏'
            },
            onRemove: function (x) {
                this._map.off('fullscreenchange', this._toggle_fullscreen_Title, this), this._map.off('locationfound', this.onLocationFound, this), this._map.off('locationerror', this.onLocationError, this)
            }
        }), D.a.control.toolbar = function (x) {
            return new D.a.Control.ToolBar(x)
        };

        function N(e, a, x) {
            (x = x.split(' ')).forEach(function (x) {
                D.a.DomUtil[e].call(this, a, x)
            })
        }

        D.a.DomUtil.addClasses = function (x, e) {
            N('addClass', x, e)
        }, D.a.DomUtil.removeClasses = function (x, e) {
            N('removeClass', x, e)
        }, D.a.Map.include({
            isFullscreen: function () {
                return this._isFullscreen
            }, toggleFullscreen: function () {
                var x = document.documentElement;
                this._isFullscreen ? (document.exitFullscreen ? document.exitFullscreen() : document.mozCancelFullScreen ? document.mozCancelFullScreen() : document.webkitCancelFullScreen ? document.webkitCancelFullScreen() : document.msExitFullscreen && document.msExitFullscreen(), this._isFullscreen = !1) : (x.requestFullscreen ? x.requestFullscreen() : x.mozRequestFullScreen ? x.mozRequestFullScreen() : x.webkitRequestFullscreen ? x.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT) : x.msRequestFullscreen && x.msRequestFullscreen(), this._isFullscreen = !0)
            }
        });
        var k = D.a.Layer.extend({
            options: {cellSize: 512, updateInterval: 150}, initialize: function (x) {
                x = D.a.setOptions(this, x), this._zooming = !1
            }, onAdd: function (x) {
                this._map = x, this._update = D.a.Util.throttle(this._update, this.options.updateInterval, this), this._reset(), this._update()
            }, onRemove: function (x) {
                this._map.removeEventListener(this.getEvents(), this), this._removeCells()
            }, getEvents: function () {
                return {moveend: this._update, zoomstart: this._zoomstart, zoomend: this._reset}
            }, addTo: function (x) {
                return x.addLayer(this), this
            }, removeFrom: function (x) {
                return x.removeLayer(this), this
            }, _zoomstart: function () {
                this._zooming = !0
            }, _reset: function () {
                this._removeCells(), this._cells = {}, this._activeCells = {}, this._cellsToLoad = 0, this._cellsTotal = 0, this._cellNumBounds = this._getCellNumBounds(), this._resetWrap(), this._zooming = !1
            }, _resetWrap: function () {
                var x, e = this._map, a = e.options.crs;
                a.infinite || (x = this._getCellSize(), a.wrapLng && (this._wrapLng = [Math.floor(e.project([0, a.wrapLng[0]]).x / x), Math.ceil(e.project([0, a.wrapLng[1]]).x / x)]), a.wrapLat && (this._wrapLat = [Math.floor(e.project([a.wrapLat[0], 0]).y / x), Math.ceil(e.project([a.wrapLat[1], 0]).y / x)]))
            }, _getCellSize: function () {
                return this.options.cellSize
            }, _update: function () {
                var x, e;
                this._map && (x = this._map.getPixelBounds(), e = this._getCellSize(), e = D.a.bounds(x.min.divideBy(e).floor(), x.max.divideBy(e).floor()), this._removeOtherCells(e), this._addCells(e), this.fire('cellsupdated'))
            }, _addCells: function (x) {
                for (var e, a, d = [], f = x.getCenter(), t = this._map.getZoom(), _ = x.min.y; _ <= x.max.y; _++) for (e = x.min.x; e <= x.max.x; e++) (a = D.a.point(e, _)).z = t, this._isValidCell(a) && d.push(a);
                var i = d.length;
                if (0 !== i) for (this._cellsToLoad += i, this._cellsTotal += i, d.sort(function (x, e) {
                    return x.distanceTo(f) - e.distanceTo(f)
                }), e = 0; e < i; e++) this._addCell(d[e])
            }, _isValidCell: function (x) {
                var e = this._map.options.crs;
                if (!e.infinite) {
                    var a = this._cellNumBounds;
                    if (!e.wrapLng && (x.x < a.min.x || x.x > a.max.x) || !e.wrapLat && (x.y < a.min.y || x.y > a.max.y)) return !1
                }
                if (!this.options.bounds) return !0;
                x = this._cellCoordsToBounds(x);
                return D.a.latLngBounds(this.options.bounds).intersects(x)
            }, _cellCoordsToBounds: function (x) {
                var e = this._map, a = this.options.cellSize, d = x.multiplyBy(a), a = d.add([a, a]),
                    d = e.wrapLatLng(e.unproject(d, x.z)), x = e.wrapLatLng(e.unproject(a, x.z));
                return D.a.latLngBounds(d, x)
            }, _cellCoordsToKey: function (x) {
                return x.x + ':' + x.y
            }, _keyToCellCoords: function (x) {
                var e = x.split(':'), x = parseInt(e[0], 10), e = parseInt(e[1], 10);
                return D.a.point(x, e)
            }, _removeOtherCells: function (x) {
                for (var e in this._cells) x.contains(this._keyToCellCoords(e)) || this._removeCell(e)
            }, _removeCell: function (x) {
                var e = this._activeCells[x];
                e && (delete this._activeCells[x], this.cellLeave && this.cellLeave(e.bounds, e.coords), this.fire('cellleave', {
                    bounds: e.bounds,
                    coords: e.coords
                }))
            }, _removeCells: function () {
                for (var x in this._cells) {
                    var e = this._cells[x].bounds, x = this._cells[x].coords;
                    this.cellLeave && this.cellLeave(e, x), this.fire('cellleave', {bounds: e, coords: x})
                }
            }, _addCell: function (x) {
                this._wrapCoords(x);
                var e = this._cellCoordsToKey(x), a = this._cells[e];
                a && !this._activeCells[e] && (this.cellEnter && this.cellEnter(a.bounds, x), this.fire('cellenter', {
                    bounds: a.bounds,
                    coords: x
                }), this._activeCells[e] = a), a || (a = {
                    coords: x,
                    bounds: this._cellCoordsToBounds(x)
                }, this._cells[e] = a, this._activeCells[e] = a, this.createCell && this.createCell(a.bounds, x), this.fire('cellcreate', {
                    bounds: a.bounds,
                    coords: x
                }))
            }, _wrapCoords: function (x) {
                x.x = this._wrapLng ? D.a.Util.wrapNum(x.x, this._wrapLng) : x.x, x.y = this._wrapLat ? D.a.Util.wrapNum(x.y, this._wrapLat) : x.y
            }, _getCellNumBounds: function () {
                var x = this._map.getPixelWorldBounds(), e = this._getCellSize();
                return x ? D.a.bounds(x.min.divideBy(e).floor(), x.max.divideBy(e).ceil().subtract([1, 1])) : null
            }
        });

        function F(x) {
            if (!(this instanceof F)) return new F(x);
            this._canvas = x = 'string' == _typeof(x) ? document.getElementById(x) : x, this._ctx = x.getContext('2d'), this._width = x.width, this._height = x.height, this._max = 1, this._data = []
        }

        D.a.VirtualGrid = k, D.a.virtualGrid = function (x) {
            return new k(x)
        }, F.prototype = {
            defaultRadius: 25,
            defaultGradient: {.4: 'blue', .6: 'cyan', .7: 'lime', .8: 'yellow', 1: 'red'},
            data: function (x) {
                return this._data = x, this
            },
            max: function (x) {
                return this._max = x, this
            },
            add: function (x) {
                return this._data.push(x), this
            },
            clear: function () {
                return this._data = [], this
            },
            radius: function (x, e) {
                e = void 0 === e ? 15 : e;
                var a = this._circle = this._createCanvas(), d = a.getContext('2d'), f = this._r = x + e;
                return a.width = a.height = 2 * f, d.shadowOffsetX = d.shadowOffsetY = 2 * f, d.shadowBlur = e, d.shadowColor = 'black', d.beginPath(), d.arc(-f, -f, x, 0, 2 * Math.PI, !0), d.closePath(), d.fill(), this
            },
            resize: function () {
                this._width = this._canvas.width, this._height = this._canvas.height
            },
            gradient: function (x) {
                var e, a = this._createCanvas(), d = a.getContext('2d'), f = d.createLinearGradient(0, 0, 0, 256);
                for (e in a.width = 1, a.height = 256, x) f.addColorStop(+e, x[e]);
                return d.fillStyle = f, d.fillRect(0, 0, 1, 256), this._grad = d.getImageData(0, 0, 1, 256).data, this
            },
            draw: function (x) {
                this._circle || this.radius(this.defaultRadius), this._grad || this.gradient(this.defaultGradient);
                var e = this._ctx;
                e.clearRect(0, 0, this._width, this._height);
                for (var a, d = 0, f = this._data.length; d < f; d++) a = this._data[d], e.globalAlpha = Math.min(Math.max(a[2] / this._max, void 0 === x ? .05 : x), 1), e.drawImage(this._circle, a[0] - this._r, a[1] - this._r);
                var t = e.getImageData(0, 0, this._width, this._height);
                return this._colorize(t.data, this._grad), e.putImageData(t, 0, 0), this
            },
            _colorize: function (x, e) {
                for (var a, d = 0, f = x.length; d < f; d += 4) (a = 4 * x[d + 3]) && (x[d] = e[a], x[d + 1] = e[1 + a], x[d + 2] = e[2 + a])
            },
            _createCanvas: function () {
                return 'undefined' != ('undefined' == typeof document ? 'undefined' : _typeof(document)) ? document.createElement('canvas') : new this._canvas.constructor
            }
        };
        var z = (D.a.Layer ? D.a.Layer : D.a.Class).extend({
            initialize: function (x, e) {
                this._latlngs = x, D.a.setOptions(this, e)
            }, setLatLngs: function (x) {
                return this._latlngs = x, this.redraw()
            }, addLatLng: function (x) {
                return this._latlngs.push(x), this.redraw()
            }, setOptions: function (x) {
                return D.a.setOptions(this, x), this._heat && this._updateOptions(), this.redraw()
            }, redraw: function () {
                return this._heat && !this._frame && this._map && !this._map._animating && (this._frame = D.a.Util.requestAnimFrame(this._redraw, this)), this
            }, onAdd: function (x) {
                this._map = x, this._canvas || this._initCanvas(), (this.options.pane ? this.getPane() : x._panes.overlayPane).appendChild(this._canvas), x.on('moveend', this._reset, this), x.options.zoomAnimation && D.a.Browser.any3d && x.on('zoomanim', this._animateZoom, this), this._reset()
            }, onRemove: function (x) {
                (this.options.pane ? this.getPane() : x.getPanes().overlayPane).removeChild(this._canvas), x.off('moveend', this._reset, this), x.options.zoomAnimation && x.off('zoomanim', this._animateZoom, this)
            }, addTo: function (x) {
                return x.addLayer(this), this
            }, _initCanvas: function () {
                var x = this._canvas = D.a.DomUtil.create('canvas', 'leaflet-heatmap-layer leaflet-layer'),
                    e = D.a.DomUtil.testProp(['transformOrigin', 'WebkitTransformOrigin', 'msTransformOrigin']);
                x.style[e] = '50% 50%';
                e = this._map.getSize();
                x.width = e.x, x.height = e.y;
                e = this._map.options.zoomAnimation && D.a.Browser.any3d;
                D.a.DomUtil.addClass(x, 'leaflet-zoom-' + (e ? 'animated' : 'hide')), this._heat = F(x), this._updateOptions()
            }, _updateOptions: function () {
                this._heat.radius(this.options.radius || this._heat.defaultRadius, this.options.blur), this.options.gradient && this._heat.gradient(this.options.gradient), this.options.max && this._heat.max(this.options.max)
            }, _reset: function () {
                var x = this._map.containerPointToLayerPoint([0, 0]);
                D.a.DomUtil.setPosition(this._canvas, x);
                x = this._map.getSize();
                this._heat._width !== x.x && (this._canvas.width = this._heat._width = x.x), this._heat._height !== x.y && (this._canvas.height = this._heat._height = x.y), this._redraw()
            }, _redraw: function () {
                if (this._map) {
                    for (var x, e, a, d, f, t, _, i = [], s = this._heat._r, n = this._map.getSize(), h = new D.a.Bounds(D.a.point([-s, -s]), n.add([s, s])), r = void 0 === this.options.max ? 1 : this.options.max, n = void 0 === this.options.maxZoom ? this._map.getMaxZoom() : this.options.maxZoom, c = 1 / Math.pow(2, Math.max(0, Math.min(n - this._map.getZoom(), 12))), o = s / 2, l = [], s = this._map._getMapPanePos(), u = s.x % o, b = s.y % o, m = 0, p = this._latlngs.length; m < p; m++) x = this._map.latLngToContainerPoint(this._latlngs[m]), h.contains(x) && (a = Math.floor((x.x - u) / o) + 2, d = Math.floor((x.y - b) / o) + 2, _ = (void 0 !== this._latlngs[m].alt ? this._latlngs[m].alt : void 0 !== this._latlngs[m][2] ? +this._latlngs[m][2] : 1) * c, l[d] = l[d] || [], (e = l[d][a]) ? (e[0] = (e[0] * e[2] + x.x * _) / (e[2] + _), e[1] = (e[1] * e[2] + x.y * _) / (e[2] + _), e[2] += _) : l[d][a] = [x.x, x.y, _]);
                    for (m = 0, p = l.length; m < p; m++) if (l[m]) for (f = 0, t = l[m].length; f < t; f++) (e = l[m][f]) && i.push([Math.round(e[0]), Math.round(e[1]), Math.min(e[2], r)]);
                    this._heat.data(i).draw(this.options.minOpacity), this._frame = null
                }
            }, _animateZoom: function (x) {
                var e = this._map.getZoomScale(x.zoom),
                    x = this._map._getCenterOffset(x.center)._multiplyBy(-e).subtract(this._map._getMapPanePos());
                D.a.DomUtil.setTransform ? D.a.DomUtil.setTransform(this._canvas, x, e) : this._canvas.style[D.a.DomUtil.TRANSFORM] = D.a.DomUtil.getTranslateString(x) + ' scale(' + e + ')'
            }
        });
        D.a.HeatLayer = z, D.a.heatLayer = function (x, e) {
            return new z(x, e)
        };
        var J = a(6), T = a(3), U = a(8), Q = a(9);
        D.a.CRS.PGIS = D.a.Util.extend({}, D.a.CRS.Earth, {
            code: 'PGIS',
            projection: D.a.Projection.LonLat,
            transformation: new D.a.Transformation(1 / 512, .5, -1 / 512, .5)
        });
        var L = a(10), I = a(11), $ = a(5);

        function A(x, e, a) {
            if (null != e) {
                e.centerAutoLevel = e.centerAutoLevel || 15, t = e.extent ? (f = (e.extent.xmin + e.extent.xmax) / 2, [(e.extent.ymin + e.extent.ymax) / 2, f]) : e.center && e.center.x ? [e.center.y, e.center.x] : e.center, e.center = t;
                var d = function (x, e) {
                    var d, a, f, t = e.id, _ = {
                        zoomControl: !1,
                        attributionControl: !1,
                        contextmenu: !0,
                        contextmenuWidth: 140,
                        contextmenuItems: [{
                            text: '移动到此处', iconCls: 'fa fa-hand-o-right', callback: function (x) {
                                d.panTo(x.latlng)
                            }
                        }, {
                            text: '显示此处经纬度', iconCls: 'fa fa-map-marker', callback: function (x) {
                                var e = '层级：' + d.getZoom();
                                e += '<br/>经度：' + x.latlng.lng.toFixed(6) + ' 纬度：' + x.latlng.lat.toFixed(6), d.hasConvert() && (x = d.convert2wgs(x.latlng), e += '<br/>经度：' + x[1].toFixed(6) + ' 纬度：' + x[0].toFixed(6) + ' (WGS84)'), q.alert(e)
                            }
                        }, '-', {
                            text: '放大', iconCls: 'fa fa-search-plus', callback: function (x) {
                                d.zoomIn()
                            }
                        }, {
                            text: '缩小', iconCls: 'fa fa-search-minus', callback: function (x) {
                                d.zoomOut()
                            }
                        }]
                    };
                    for (a in x) _[a] = x[a];
                    for (f in e) 'id' !== f && 'success' !== f && (_[f] = e[f]);
                    var i = q.getRequest();
                    _.crs = _.crs || 'EPSG3857';
                    var s, n, h, r, c = _.basemaps;
                    if (c) {
                        for (var o = 0; o < c.length; o++) {
                            var l = c[o];
                            null == l.crs && null != _.crs && (l.crs = _.crs), l.hasOwnProperty('visible') && l.visible && (s = l)
                        }
                        !s && 0 < c.length && ((s = c[0]).visible = !0)
                    }
                    if (i && i.baselayer) for (var u = 0; u < c.length; u++) {
                        var b = c[u];
                        if (b.name == i.baselayer) {
                            s && (s.visible = !1), (s = b).visible = !0;
                            break
                        }
                    }
                    if (s && s.crs != _.crs && (_.crs = s.crs), x.crs = _.crs, 'string' == typeof _.crs) switch ((_.crs || '').toString().toUpperCase()) {
                        default:
                            n = D.a.CRS.EPSG3857;
                            break;
                        case 'IMAGE':
                        case 'SIMPLE':
                            n = D.a.CRS.Simple;
                            break;
                        case'4326':
                        case 'EPSG:4326':
                        case 'EPSG4326':
                            n = D.a.CRS.EPSG4326;
                            break;
                        case'3395':
                        case'EPSG:3395':
                        case 'EPSG3395':
                            n = D.a.CRS.EPSG3395;
                            break;
                        case '4490':
                        case 'EPSG:4490':
                        case 'EPSG4490':
                            n = D.a.CRS.EPSG4490;
                            break;
                        case 'BD09':
                        case'BAIDU':
                            h = 'bd', n = D.a.CRS.Baidu;
                            break;
                        case 'GCJ':
                        case 'GCJ02':
                            h = 'gcj', n = D.a.CRS.EPSG3857;
                            break;
                        case 'PGIS':
                            n = D.a.CRS.PGIS
                    } else if ('object' == K()(_.crs)) {
                        var m, p = {};
                        for (m in _.crs) 'code' != m && 'proj' != m && ('bounds' != m ? p[m] = _.crs[m] : p.bounds = D.a.bounds(_.crs.bounds));
                        n = new D.a.Proj.CRS(_.crs.code, _.crs.proj, p)
                    }
                    _.crs = n, _.pointconvertType = h, d = D.a.map(t, _), i && i.center && -1 != i.center.indexOf(',') && (2 == (S = i.center.split(',')).length ? (r = d.convert2map([S[0], S[1]]), d.panTo(r)) : 3 == S.length && (r = d.convert2map([S[0], S[1]]), d.setView(r, S[2])));
                    var y = [], v = {};
                    if (c = _.basemaps) for (var Z = 0; Z < c.length; Z++) {
                        var M = c[Z], V = $.createLayer(M, _.serverURL, _.layerToMap);
                        if (null != V) if (M.hasOwnProperty('visible') && M.visible && (V.addTo(d), M.maxBounds && d.setMaxBounds(M.maxBounds), M.background && Object(P.a)('.leaflet-container').css({background: M.background})), v[M.name] = V, y.push(M), 'group' == M.type && M.layers) for (var G = 0; G < M.layers.length; G++) y.push(M.layers[G]); else if (M._layer instanceof D.a.LayerGroup || M._layer instanceof D.a.FeatureGroup) for (var W = M._layer.getLayers(), X = 0; X < W.length; X++) {
                            var g = W[X], g = {name: g.options.name, _layer: g};
                            y.push(g)
                        }
                    }
                    var R = {};
                    if (c = _.operationallayers) for (var w = 0; w < c.length; w++) {
                        var Y = c[w], N = $.createLayer(Y, _.serverURL, _.layerToMap);
                        if (null != N) if (Y.hasOwnProperty('visible') && Y.visible && N.addTo(d), R[Y.name] = N, y.push(Y), 'group' == Y.type && Y.layers) for (var k = 0; k < Y.layers.length; k++) y.push(Y.layers[k]); else if (Y._layer instanceof D.a.LayerGroup || Y._layer instanceof D.a.FeatureGroup) for (var F = Y._layer.getLayers(), z = 0; z < F.length; z++) {
                            var J = F[z], J = {name: J.options.name, _layer: J};
                            y.push(J)
                        }
                    }
                    for (var T = 0, U = y.length; T < U; T++) {
                        var Q = y[T], L = Number(Q.order);
                        isNaN(L) && (L = T - U), null != Q._layer && Q._layer.setZIndex && Q._layer.setZIndex(L)
                    }
                    var I, A, E, S, B = {}, H = _.controls;
                    if (H) for (var C = 0; C < H.length; C++) {
                        var j = H[C];
                        if (!j.hasOwnProperty('visible') || j.visible) {
                            var O = void 0;
                            switch (j.type) {
                                case 'layers':
                                    O = D.a.control.layers(v, R, {position: j.position || 'topright'});
                                    break;
                                case'zoom':
                                    O = D.a.control.zoom({position: j.position || 'bottomright'});
                                    break;
                                case 'scale':
                                    O = D.a.control.scale({metric: !0, imperial: !1});
                                    break;
                                case 'tool':
                                    j.position = j.position || 'bottomright', O = D.a.control.toolbar(j).addTo(d);
                                    break;
                                case'location':
                                    xx(d, j)
                            }
                            null != O && (d.addControl(O), B[j.type] = O)
                        }
                    }
                    return _.center && d.setView(_.center, _.zoom), _.extent && d.fitBounds([[_.extent.ymin, _.extent.xmin], [_.extent.ymax, _.extent.xmax]]), _.clickHighlight && (E = function () {
                        var x;
                        I && ('arcgis_dynamic' == A ? d.removeLayer(I) : ((x = I.options).weight = x._weight_old, x.color = x._color_old, I.setStyle(x)), I = null)
                    }, d.on('click', E), $.bindClickFeature(function (x, e) {
                        var a;
                        E(), e.setStyle && ('arcgis_dynamic' == x ? (e.setStyle({
                            color: '#3388ff',
                            weight: 3
                        }), d.addLayer(e)) : (null == (a = e.options)._color_old && (a._color_old = a.color), null == a._weight_old && (a._weight_old = a.weight), a.color = '#3388ff', a.weight = 3, e.setStyle(a)), I = e, A = x)
                    })), S = d, Object(P.a)('#' + S._container.id).prepend('<div style="position: absolute; z-index: 999; right: 30px;bottom: 2px; "></div>'), {
                        map: d,
                        baselayers: v,
                        overlayers: R,
                        controls: B
                    }
                }(e, x), f = d.map;
                e.zoom || (e.zoom = f.getZoom());
                var t = {};
                return t.config = e, t.baselayers = d.baselayers, t.overlayers = d.overlayers, t.controls = d.controls, f.gisdata = t, x.success && x.success(f, t, a), f
            }
            console.log('配置信息不能为空！')
        }

        function xx(t, _) {
            function e(x) {
                var e = {z: t.getZoom()};
                switch (_.crs) {
                    default:
                        a = _.hasOwnProperty('toFixed') ? _.toFixed : 6, e.x = x.lng.toFixed(a), e.y = x.lat.toFixed(a);
                        break;
                    case 'degree':
                        e.x = q.formatDegree(x.lng), e.y = q.formatDegree(x.lat);
                        break;
                    case 'project':
                        var a = _.hasOwnProperty('toFixed') ? _.toFixed : 0, d = t.project(x, t.getZoom());
                        e.x = d.x.toFixed(a), e.y = d.y.toFixed(a);
                        break;
                    case 'wgs':
                        a = _.hasOwnProperty('toFixed') ? _.toFixed : 6, x = t.convert2wgs(x), e.x = x[1].toFixed(a), e.y = x[0].toFixed(a);
                        break;
                    case 'wgs-degree':
                        x = t.convert2wgs(x), e.x = q.formatDegree(x[1]), e.y = q.formatDegree(x[0])
                }
                var f = q.template(_.format, e);
                Object(P.a)('#location').html(f)
            }

            function a() {
                Object(P.a)('#location').css({left: Object(P.a)('.leaflet-control-scale-line').width() + 40 + 'px'})
            }

            var d;
            document.getElementById('location') || Object(P.a)('#' + t._container.id).prepend('<div id="location" class="location-bar no-print no-print-view"></div>'), (_ = _ || {}).format = _.format || '<div>经度:{x}</div><div>纬度:{y}</div>', t.on('mousemove', function (x) {
                d = x.latlng, e(x.latlng)
            }), t.on('zoomend', function (x) {
                a(), e(d = d || t.getCenter())
            }), a()
        }

        var E = D.a.Class.extend({
                map: null, layerDraw: null, options: null, control: {}, initialize: function (x) {
                    this.options = x || {}, this.map = this.options.map, this.options.layer ? this.layerDraw = this.options.layer : this.layerDraw = D.a.featureGroup().addTo(this.map, {editing: !0}), this.options.style = this.options.style || {
                        color: '#0000ff',
                        weight: 2
                    };
                    x = this.options.style;
                    this.control.marker = new D.a.Draw.Marker(this.map, {icon: new D.a.Icon.Default}), this.control.text = new D.a.Draw.Text(this.map), this.control.font_marker = new D.a.Draw.FontMarker(this.map), this.control.polyline = new D.a.Draw.Polyline(this.map, {shapeOptions: x}), this.control.polylinefree = new D.a.Draw.Polylinefree(this.map, {shapeOptions: x}), this.control.rectangle = new D.a.Draw.Rectangle(this.map, {shapeOptions: x}), this.control.circle = new D.a.Draw.Circle(this.map, {shapeOptions: x}), this.control.polygon = new D.a.Draw.Polygon(this.map, {
                        allowIntersection: !0,
                        showArea: !0,
                        metric: ['km', 'm'],
                        shapeOptions: x
                    }), this.control.image = new D.a.Draw.Image(this.map), this.options.hasOwnProperty('onEvnet') && !this.options.onEvnet || this.onEvnet()
                }, onEvnet: function () {
                    return this.layerDraw.on('click', this._layerDraw_clickHndler, this), this.map.on('click', this._map_clickHndler, this), this.map.on('dblclick', this._map_dblclickHndler, this), this.map.on('draw:created', this._map_draw_createdHndler, this), this.options.onChange && 'function' == _typeof(this.options.onChange) && (this.map.on('draw:editvertex', this._map_draw_changeHandler, this), this.map.on('draw:editmove', this._map_draw_changeHandler, this), this.map.on('draw:editresize', this._map_draw_changeHandler, this)), this
                }, offEvent: function () {
                    return this.layerDraw.off('click', this._layerDraw_clickHndler, this), this.map.off('click', this._map_clickHndler, this), this.map.off('dblclick', this._map_dblclickHndler, this), this.map.off('draw:created', this._map_draw_createdHndler, this), this.options.onChange && 'function' == _typeof(this.options.onChange) && (this.map.off('draw:editvertex', this._map_draw_changeHandler, this), this.map.off('draw:editmove', this._map_draw_changeHandler, this), this.map.off('draw:editresize', this._map_draw_changeHandler, this)), this
                }, destroy: function (x) {
                    return this.stopDraw(), x || this.clearDraw(), this.offEvent(), this
                }, _drawtype: null, _defval: null, startDraw: function (x, e) {
                    if (null == this.control[x]) throw'不能进行type为【' + x + '】的绘制，无该类型！';
                    if ((e = e || this.configDefval[x]).type = e.type || x, this._drawtype = x, this._defval = e, this.options.isOnly && this.clearDraw(), this.stopDraw(), e) switch (x) {
                        default:
                            this.control[x].setOptions({shapeOptions: e.style});
                            break;
                        case'image':
                            this.control[x].setOptions(e.style);
                            break;
                        case 'marker':
                            var a = e.style.hasOwnProperty('iconUrl') && null != e.style.iconUrl && '' != e.style.iconUrl ? (e.style.iconSize0 && e.style.iconSize1 && (e.style.iconSize = [e.style.iconSize0, e.style.iconSize1]), e.style.iconAnchor0 && e.style.iconAnchor1 && (e.style.iconAnchor = [e.style.iconAnchor0, e.style.iconAnchor1]), D.a.icon(e.style)) : new D.a.Icon.Default;
                            this.control[x].setOptions({icon: a});
                            break;
                        case 'text':
                        case 'font-marker':
                            this.control[x].updateIcon(e.style)
                    }
                    this.control[x].enable(), this._isDrawing = !0
                }, stopDraw: function () {
                    for (var x in this.control) this.control[x].disable();
                    return this._isDrawing = !1, this.stopEditing(), this
                }, clearDraw: function () {
                    this.stopEditing(), this.layerDraw.clearLayers()
                }, _hasEdit: !0, hasEdit: function (x) {
                    (this._hasEdit = x) || this.stopEditing()
                }, currEditFeature: null, startEditing: function (x) {
                    null != x && this._hasEdit && (x.editing && x.editing.enable(), this.currEditFeature = x, this.options.hasOwnProperty('onStartEditing') && this.options.onStartEditing && this.options.onStartEditing(this.currEditFeature))
                }, stopEditing: function () {
                    this.currEditFeature && this.currEditFeature.editing && this.currEditFeature.editing.disable && (this.currEditFeature.editing.disable(), this.options.hasOwnProperty('onStopEditing') && this.options.onStopEditing && this.options.onStopEditing(this.currEditFeature)), this.currEditFeature = null
                }, updateProperties: function (x, e) {
                    if (null != (x = x || this.currEditFeature)) {
                        var a = x.properties;
                        switch (e ? x.properties = e : e = a, e.type) {
                            case 'marker':
                                var d = e.style.hasOwnProperty('iconUrl') && null != e.style.iconUrl && '' != e.style.iconUrl ? (d = {iconUrl: e.style.iconUrl}, e.style.iconSize0 && e.style.iconSize1 ? d.iconSize = [e.style.iconSize0, e.style.iconSize1] : e.style.iconSize && (d.iconSize = e.style.iconSize), e.style.iconAnchor0 && e.style.iconAnchor1 ? d.iconAnchor = [e.style.iconAnchor0, e.style.iconAnchor1] : e.style.iconAnchor ? d.iconAnchor = e.style.iconAnchor : d.iconSize && 2 <= d.iconSize.length && (e.style.iconAnchor0 = d.iconSize[0] / 2, e.style.iconAnchor1 = d.iconSize[1] / 2, d.iconAnchor = [e.style.iconAnchor0, e.style.iconAnchor1]), D.a.icon(d)) : new D.a.Icon.Default;
                                x.setIcon(d), x.setOpacity(e.style.opacity);
                                break;
                            case'text':
                            case'font-marker':
                                x.setOpacity(e.style.opacity);
                                var f = this.control[e.type].getIcon(e.style);
                                x.setIcon(f);
                                break;
                            case 'polyline':
                            case'polylinefree':
                            case'polygon':
                            case 'rectangle':
                                x.setStyle(e.style);
                                break;
                            case'image':
                                null == x._imageOverlay && (f = new D.a.ImageOverlay(e.style.iconUrl, x.getBounds(), {opacity: e.style.opacity}), x._imageOverlay = f, x._imageOverlay._nosave = !0, this.layerDraw.addLayer(x._imageOverlay)), x._imageOverlay.setOpacity(e.style.opacity);
                                break;
                            case 'circle':
                                e.isSemicircle || (e.startAngle = null, e.stopAngle = null), x.setRadius(e.style.radius), x.setStyle(e.style), x.redraw()
                        }
                        return x
                    }
                }, deleteFeature: function (x) {
                    this.stopEditing(), this.layerDraw.removeLayer(x)
                }, _map_clickHndler: function (x) {
                    this.stopEditing()
                }, _map_dblclickHndler: function (x) {
                    for (var e in D.a.DomEvent.stopPropagation(x), this.control) this.control[e]._enabled && this.control[e]._finishShape && this.control[e]._finishShape()
                }, _layerDraw_clickHndler: function (x) {
                    x.layer != this.currEditFeature && this.stopEditing(), this.startEditing(x.layer), D.a.DomEvent.stopPropagation(x)
                }, _map_draw_createdHndler: function (x) {
                    var e;
                    this._isDrawing && ((e = x.layer).properties = this._defval, this._updateFeatureForEdit(e), this.layerDraw.addLayer(e), this.options.hasDel && this.bindDeleteContextmenu(e), this.stopEditing(), D.a.Browser.ie || this.startEditing(e), this.options.onCreate && 'function' == _typeof(this.options.onCreate) && this.options.onCreate(x), this._isDrawing = !1)
                }, _map_draw_changeHandler: function (x) {
                    var e;
                    this.currEditFeature && (e = this.currEditFeature, x.layer = e, this._updateFeatureForEdit(e), this.options.onChange(x))
                }, _updateFeatureForEdit: function (x) {
                    if (null != x.properties && null != x.properties.type) switch (x.properties.type) {
                        case 'circle':
                            x.properties.style.radius = parseInt(x.getRadius());
                            break;
                        case'image':
                            this.layerDraw.addLayer(x._imageOverlay), x._imageOverlay._nosave = !0, x._imageOverlay.setBounds(x.getBounds());
                            break;
                        case 'polylinefree':
                    }
                }, hasDraw: function () {
                    return null != this.layerDraw && 0 < this.layerDraw.getLayers().length
                }, getLayer: function () {
                    return this.layerDraw
                }, getFeatures: function () {
                    if (this.hasDraw()) {
                        var x = this.layerDraw.getLayers();
                        return this.options.isOnly ? x[0] : x
                    }
                    return null
                }, bindDeleteContextmenu: function (x) {
                    var e = this;
                    x.bindContextMenu && x.bindContextMenu({
                        contextmenu: !0,
                        contextmenuInheritItems: !1,
                        contextmenuItems: [{
                            text: '删除', iconCls: 'fa fa-trash', context: x, callback: function (x) {
                                e.deleteFeature(this)
                            }
                        }]
                    })
                }, toJson: function (x) {
                    var e = this.layerDraw.getLayers();
                    if (0 == e.length) return x ? {} : '';
                    for (var a = [], d = 0; d < e.length; d++) {
                        var f = e[d];
                        if (!f._nosave) {
                            var t = f.toGeoJSON();
                            t.properties = {type: f.properties.type};
                            var _, i, s = this.configDefval[f.properties.type];
                            for (_ in f.properties.style) {
                                var n = f.properties.style[_];
                                null != n && '' != n && s.style[_] != n && (null == t.properties.style && (t.properties.style = {}), t.properties.style[_] = n)
                            }
                            for (i in f.properties.attr) {
                                var h = f.properties.attr[i];
                                null != h && '' != h && s.attr[i] != h && (null == t.properties.attr && (t.properties.attr = {}), t.properties.attr[i] = h)
                            }
                            a.push(t)
                        }
                    }
                    var r = {type: 'FeatureCollection', features: a};
                    return x ? r : JSON.stringify(r)
                }, jsonToLayer: function (x, e, a) {
                    this.stopDraw(), this.options.isOnly && this.clearDraw();
                    var d = x;
                    try {
                        'string' == _typeof(x) && x.constructor == String && (d = JSON.parse(x))
                    } catch (x) {
                        return void Object(q.alert)(x.name + ': ' + x.message + ' \n请确认json文件格式正确!!!')
                    }
                    var f = D.a.geoJSON(d), t = f.getLayers();
                    if (0 == t.length) return 0;
                    e && this.layerDraw.clearLayers();
                    for (var _ = 0; _ < t.length; _++) {
                        var i = t[_];
                        i.feature.properties = i.feature.properties || {};
                        var s = i.feature.properties;
                        if (null == s.style && (s.style = {}), null == s.attr && (s.attr = {}), null == s.type) switch (i.feature.geometry.type) {
                            default:
                            case'Point':
                            case'MultiPoint':
                                s.type = 'marker';
                                break;
                            case 'LineString':
                            case'MultiLineString':
                                s.type = 'polyline';
                                break;
                            case 'Polygon':
                            case 'MultiPolygon':
                                s.type = 'polygon'
                        }
                        var n, h, r = this.configDefval[s.type];
                        for (n in r.style) null == s.style[n] && (s.style[n] = r.style[n]);
                        for (h in r.attr) null == s.attr[h] && (s.attr[h] = r.attr[h]);
                        switch (s.type) {
                            case'circle':
                                i = D.a.circle(i.getLatLng(), {radius: s.style.radius});
                                break;
                            case 'rectangle':
                                i = D.a.rectangle(i.getBounds());
                                break;
                            case 'image':
                                i = D.a.rectangle(i.getBounds(), this.control.image._defstyle)
                        }
                        i.properties = s, i.feature = null, f.removeLayer(i), this.layerDraw.addLayer(i), this.updateProperties(i), t[_] = i
                    }
                    return a && this.map.fitBounds(this.layerDraw.getBounds()), t
                }, configDefval: {
                    text: {
                        type: 'text',
                        name: '文字',
                        style: {
                            text: '文字',
                            opacity: 1,
                            color: '#0081c2',
                            font_size: 30,
                            font_family: '黑体',
                            font_style: 'normal',
                            font_weight: 'normal',
                            background: !1,
                            background_color: '#ccc',
                            border: !1,
                            border_color: '#5928de',
                            border_width: 3,
                            border_style: 'solid'
                        },
                        attr: {name: '', remark: ''}
                    },
                    'font-marker': {
                        type: 'font-marker',
                        name: '字体图标点',
                        style: {opacity: 1, size: 50, color: '#000000', iconClass: 'fa fa-crosshairs'},
                        attr: {name: '', remark: ''}
                    },
                    marker: {
                        type: 'marker',
                        name: '点标记',
                        style: {opacity: 1, iconUrl: '', iconSize0: 30, iconSize1: 30},
                        attr: {name: '', remark: ''}
                    },
                    polyline: {
                        type: 'polyline',
                        name: '线',
                        style: {color: '#3388ff', weight: 3, opacity: 1, dashArray: ''},
                        attr: {name: '', remark: ''}
                    },
                    polylinefree: {
                        type: 'polylinefree',
                        name: '自由线',
                        style: {color: '#3388ff', weight: 3, opacity: 1, dashArray: ''},
                        attr: {name: '', remark: ''}
                    },
                    polygon: {
                        type: 'polygon',
                        name: '面',
                        style: {
                            stroke: !0,
                            color: '#3388ff',
                            weight: 2,
                            opacity: 1,
                            dashArray: '',
                            fill: !0,
                            fillColor: '#3388ff',
                            fillOpacity: .3
                        },
                        attr: {name: '', remark: ''}
                    },
                    rectangle: {
                        type: 'rectangle',
                        name: '矩形',
                        style: {
                            stroke: !0,
                            color: '#3388ff',
                            weight: 2,
                            opacity: 1,
                            dashArray: '',
                            fill: !0,
                            fillColor: '#3388ff',
                            fillOpacity: .3
                        },
                        attr: {name: '', remark: ''}
                    },
                    circle: {
                        type: 'circle',
                        name: '圆',
                        style: {
                            radius: 0,
                            stroke: !0,
                            color: '#3388ff',
                            weight: 2,
                            opacity: 1,
                            dashArray: '',
                            fill: !0,
                            fillColor: '#3388ff',
                            fillOpacity: .3,
                            isSemicircle: !1,
                            startAngle: 0,
                            stopAngle: 0
                        },
                        attr: {name: '', remark: ''}
                    },
                    image: {type: 'image', name: '图片', style: {iconUrl: '', opacity: 1}, attr: {name: '', remark: ''}}
                }
            }), S = D.a.Class.extend({
                map: null,
                layerBase: null,
                layerPoint: null,
                layerText: null,
                textShowZoom: -1,
                minZoom: -1,
                maxZoom: 99,
                options: null,
                initialize: function (e) {
                    this.options = e || {}, this.map = e.map, this.minZoom = e.minZoom || -1, this.maxZoom = e.maxZoom || 99, this.textShowZoom = e.textShowZoom || 15, this._visible = !e.hasOwnProperty('visible') || e.visible, this.layerBase = D.a.layerGroup([]), this._visible && this.map.addLayer(this.layerBase), this.layerText = D.a.featureGroup([]).addTo(this.layerBase), this.options.isCluster ? this.layerPoint = D.a.markerClusterGroup(D.a.Util.extend({
                        chunkedLoading: !0,
                        showCoverageOnHover: !1,
                        disableClusteringAtZoom: this.textShowZoom,
                        spiderfyOnMaxZoom: !1,
                        zoomToBoundsOnClick: !0
                    }, e)).addTo(this.layerBase) : this.layerPoint = D.a.featureGroup([]).addTo(this.layerBase), this.map.on('zoomend', this.map_zoomendHandler, this), this.map_zoomendHandler(), this.options.click && (this.layerText.on('click', function (x) {
                        e.click(x.layer.attribute, x.layer)
                    }, this), this.layerPoint.on('click', function (x) {
                        e.click(x.layer.attribute, x.layer)
                    }, this)), this.options.dblclick && (this.layerText.on('dblclick', function (x) {
                        e.dblclick(x.layer.attribute, x.layer)
                    }, this), this.layerPoint.on('dblclick', function (x) {
                        e.dblclick(x.layer.attribute, x.layer)
                    }, this))
                },
                getLayer: function () {
                    return this.layerBase
                },
                map_zoomendHandler: function (x) {
                    var e;
                    this._visible && (e = this.map.getZoom(), this.minZoom <= e && e <= this.maxZoom ? (this.map.addLayer(this.layerBase), this.updatePointSize(e), e >= this.textShowZoom ? this.layerBase.addLayer(this.layerText) : this.layerBase.removeLayer(this.layerText)) : this.map.removeLayer(this.layerBase))
                },
                updatePointSize: function (x) {
                    if (this.options.isAutoZoomSize) {
                        var e = this.map.getZoomScale(x, 16) * Math.pow(.55, x - 16);
                        1 < e && (e = 1);
                        for (var a = this.layerPoint.getLayers(), d = 0; d < a.length; d++) {
                            var f = a[d], t = f.options.icon.options;
                            t.iconSize && (t.iconSizeDef || (t.iconSizeDef = [t.iconSize[0], t.iconSize[1]]), t.iconSize[0] = t.iconSizeDef[0] * e, t.iconSize[1] = t.iconSizeDef[1] * e), t.iconAnchor && (t.iconAnchorDef || (t.iconAnchorDef = [t.iconAnchor[0], t.iconAnchor[1]]), t.iconAnchor[0] = t.iconAnchorDef[0] * e, t.iconAnchor[1] = t.iconAnchorDef[1] * e), f.options.icon instanceof D.a.DivIcon ? f.setIcon(D.a.divIcon(t)) : f.options.icon instanceof D.a.Icon && f.setIcon(D.a.icon(t))
                        }
                    }
                },
                _visible: !0,
                visible: function (x) {
                    (this._visible = x) ? (this.map.addLayer(this.layerBase), this.map_zoomendHandler()) : this.map.removeLayer(this.layerBase)
                },
                clear: function () {
                    this.layerPoint.clearLayers(), this.layerText.clearLayers()
                },
                _isBindPopup: !1,
                _isBindTooltip: !1,
                arrData: null,
                showData: function (x, e) {
                    if (this.arrData = x, this.clear(), null != x && 0 != x.length) {
                        (e = e || {}).name = e.name || {};
                        for (var a = e.name.jd || 'jd', d = e.name.wd || 'wd', f = e.name.mc || 'mc', t = 0; t < x.length; t++) {
                            var _ = x[t], i = _[a], s = _[d], n = _[f];
                            if (isNaN(i) || isNaN(s) || 0 == i || 0 == s) return;
                            var h = 'function' == _typeof(e.icon) ? e.icon(_) : e.icon, r = D.a.marker([s, i], {icon: h});
                            e.bindPopup && (this._isBindPopup = !0, o = e.bindPopup(_), r.bindPopup(o)), e.bindTooltip && (this._isBindTooltip = !0, l = e.bindTooltip(_), r.bindTooltip(l)), r.attribute = _, this.layerPoint.addLayer(r), _._marker_point = r;
                            var c = e.fontsize || 13, o = n.replace(/[\u0391-\uFFE5]/g, 'aa').length * c / 2, l = 5;
                            h.options.iconSize && 1 < h.options.iconSize.length && (l += h.options.iconSize[1] || 10, h.options.iconAnchor && 1 < h.options.iconAnchor.length && (l -= h.options.iconAnchor[1] || 0)), (r = D.a.marker([s, i], {
                                icon: D.a.divIcon({
                                    iconSize: [o, c],
                                    iconAnchor: [o / 2, -l],
                                    className: 'leaflet-text-marker',
                                    html: n
                                })
                            })).attribute = _, this.layerText.addLayer(r), _._marker_text = r
                        }
                        return e.hasOwnProperty('isCenter') || (e.isCenter = !0), e.isCenter && this.map.fitBounds(this.layerPoint.getBounds()), this.updatePointSize(this.map.getZoom()), this.layerPoint.getLayers()
                    }
                },
                getData: function () {
                    return this.arrData
                },
                getItemById: function (x, e) {
                    e = e || 'id';
                    for (var a = this.arrData, d = 0; d < a.length; d++) {
                        var f = a[d];
                        if (f[e] == x) return f
                    }
                    return null
                },
                getMarkerById: function (x, e) {
                    e = e || 'id';
                    for (var a = this.layerPoint.getLayers(), d = 0; d < a.length; d++) {
                        var f = a[d];
                        if (f.attribute[e] == x) return f
                    }
                    return null
                },
                lastCenter: null,
                lastCenterAnimation: null,
                centerAt: function (x, e, a) {
                    this.clearCenter();
                    x = this.getMarkerById(x, e);
                    if (null == x) return !1;
                    var d, e = x.getLatLng();
                    return this.map.centerAt(e), this._isBindTooltip && x.openTooltip(e), this._isBindPopup && x.openPopup(e), this.lastCenter = x, a && (e = D.a.marker(e, {
                        icon: D.a.divIcon({
                            className: '',
                            iconSize: [10, 10],
                            iconAnchor: [5, 5],
                            popupAnchor: [5, -5],
                            tooltipAnchor: [5, -5],
                            html: "<div class='centerat_animation' style='color:#0f89f5;'><p></p></div>"
                        })
                    }), this.map.addLayer(e), this.lastCenterAnimation = e, d = this, setTimeout(function () {
                        null != d.lastCenterAnimation && (d.lastCenterAnimation.remove(), d.lastCenterAnimation = null)
                    }, 6e3)), !0
                },
                clearCenter: function () {
                    null != this.lastCenter && (this._isBindTooltip && this.lastCenter.closeTooltip(), this._isBindPopup && this.lastCenter.closePopup(), this.lastCenter = null), null != this.lastCenterAnimation && (this.lastCenterAnimation.remove(), this.lastCenterAnimation = null)
                }
            }), B = D.a.Class.extend({
                map: null, initialize: function (x) {
                    this.options = x || {}, this.map = this.options.map, this.options.hasOwnProperty('hasDel') || (this.options.hasDel = !0), this._create(), this.options.hasOwnProperty('isactivate') || this.activate()
                }, layerDraw: null, layerResult: null, polylineControl: null, polygonControl: null, _create: function () {
                    this.layerDraw = D.a.featureGroup().addTo(this.map), this.layerResult = D.a.featureGroup().addTo(this.map);
                    var x = {color: '#0000ff', weight: 2};
                    this.polylineControl = new D.a.Draw.Polyline(this.map, {shapeOptions: x}), this.polygonControl = new D.a.Draw.Polygon(this.map, {
                        allowIntersection: !1,
                        showArea: !0,
                        metric: ['km', 'm'],
                        shapeOptions: x
                    })
                }, isActivate: !1, activate: function () {
                    this.isActivate || (this.isActivate = !0, this.map.addLayer(this.layerResult), this.map.addLayer(this.layerDraw), this.layerDraw.on('click', this._layerDraw_clickHndler, this), this.map.on('click', this._map_clickHndler, this), this.map.on('dblclick', this._map_dblclickHndler, this), this.map.on('draw:created', this._map_draw_createdHndler, this), this.map.on('draw:drawvertex', this._map_draw_drawvertexHandler, this), this.map.on('draw:drawing', this._map_draw_drawing, this), this.map.on('draw:editvertex', this._map_draw_changeHandler, this), this.map.on('draw:editmove', this._map_draw_changeHandler, this), this.map.on('draw:editresize', this._map_draw_changeHandler, this))
                }, disable: function () {
                    this.isActivate = !1, this.clear(), this.map.removeLayer(this.layerDraw), this.map.removeLayer(this.layerResult), this.layerDraw.off('click', this._layerDraw_clickHndler, this), this.map.off('click', this._map_clickHndler, this), this.map.off('dblclick', this._map_dblclickHndler, this), this.map.off('draw:created', this._map_draw_createdHndler, this), this.map.off('draw:drawvertex', this._map_draw_drawvertexHandler, this), this.map.off('draw:drawing', this._map_draw_drawing, this), this.map.off('draw:editvertex', this._map_draw_changeHandler, this), this.map.off('draw:editmove', this._map_draw_changeHandler, this), this.map.off('draw:editresize', this._map_draw_changeHandler, this)
                }, currEditFeature: null, startEditing: function (x) {
                    null != x && (x.editing.enable(), this.currEditFeature = x, this._isDrawing = !0)
                }, stopEditing: function () {
                    this.currEditFeature && this.currEditFeature.editing && this.currEditFeature.editing.disable && this.currEditFeature.editing.disable(), this.currEditFeature = null
                }, _map_clickHndler: function (x) {
                    this.stopEditing()
                }, _layerDraw_clickHndler: function (x) {
                    x.layer != this.currEditFeature && this.stopEditing(), this.startEditing(x.layer), D.a.DomEvent.stopPropagation(x)
                }, _map_draw_changeHandler: function (x) {
                    var e = this.currEditFeature;
                    if (e) if ('polyline' == e.type) {
                        if (e.tipMarker) for (var a = 0; a < e.tipMarker.length; a++) e.tipMarker[a].remove();
                        e.tipMarker = [];
                        for (var d = e.getLatLngs(), f = 0; f < d.length; f++) {
                            var t = this._showLengthTipMarker(d.slice(0, f + 1));
                            f == d.length - 1 && (t._isend = !0, t._icon.innerHTML = '总长：' + t._icon.innerHTML), e.tipMarker.push(t)
                        }
                        this._showResultLength(d)
                    } else {
                        e.tipMarker && e.tipMarker.remove();
                        var _ = e.getLatLngs()[0];
                        this._showResultArea(_), this._showAreaTipMarker(e)
                    }
                }, clear: function () {
                    return this._stopDraw(), this._last_length_val = 0, this._last_area_val = 0, this._length_tipmarker = [], this.layerDraw.clearLayers(), this.layerResult.clearLayers(), this
                }, _stopDraw: function () {
                    this._length_tipmarker = [], this.polylineControl.disable(), this.polygonControl.disable(), this._isDrawing = !1
                }, _drawType: '', _drawParams: null, measureLength: function (x) {
                    return this._drawType = 'length', this._drawParams = x, this._stopDraw(), this.polylineControl.enable(), this._isDrawing = !0, this
                }, measureArea: function (x) {
                    return this._drawType = 'area', this._drawParams = x, this._stopDraw(), this.polygonControl.enable(), this._isDrawing = !0, this
                }, _map_draw_drawvertexHandler: function (x) {
                    if (this._isDrawing) {
                        var e = x.layers.getLayers();
                        if ('length' == this._drawType) {
                            for (var a = [], d = 0; d < e.length; d++) {
                                var f = e[d], t = f.getLatLng();
                                a.push(t), f.tipMarker || (t = this._showLengthTipMarker(a), this._length_tipmarker.push(t), f.tipMarker = t)
                            }
                            this._showResultLength(a)
                        } else if ('area' == this._drawType) {
                            for (var _ = [], i = 0; i < e.length; i++) {
                                var s = e[i].getLatLng();
                                _.push(s)
                            }
                            this._showResultArea(_)
                        }
                    }
                }, _map_draw_createdHndler: function (x) {
                    if (this._isDrawing) {
                        var e = x.layer;
                        if (e.type = x.layerType, e._isMeasure = !0, this.layerDraw.addLayer(e), this.options.hasDel && this.bindDeleteContextmenu(e), 'polyline' == x.layerType) {
                            if (0 == this._length_tipmarker.length) return;
                            var a = e.getLatLngs();
                            this._showResultLength(a);
                            a = this._length_tipmarker[this._length_tipmarker.length - 1];
                            a._isend = !0, a._icon.innerHTML = '总长：' + a._icon.innerHTML, e.tipMarker = this._length_tipmarker
                        } else 'polygon' == x.layerType && (x = e.getLatLngs()[0], this._showResultArea(x), this._showAreaTipMarker(e));
                        this._drawParams && this._drawParams.drawend && this._drawParams.drawend()
                    }
                }, _map_dblclickHndler: function (x) {
                    D.a.DomEvent.stopPropagation(x), this.polygonControl._enabled && this.polygonControl._finishShape && this.polygonControl._finishShape()
                }, bindDeleteContextmenu: function (x) {
                    var e = this;
                    x.bindContextMenu && x.bindContextMenu({
                        contextmenu: !0,
                        contextmenuInheritItems: !1,
                        contextmenuItems: [{
                            text: '删除', iconCls: 'fa fa-trash', context: x, callback: function (x) {
                                e.deleteFeature(this)
                            }
                        }]
                    })
                }, deleteFeature: function (x) {
                    if (this.stopEditing(), 'polyline' == x.type) {
                        if (x.tipMarker) {
                            for (var e = 0; e < x.tipMarker.length; e++) x.tipMarker[e].remove();
                            x.tipMarker = []
                        }
                    } else x.tipMarker && x.tipMarker.remove();
                    this.layerDraw.removeLayer(x)
                }, _map_draw_drawing: function (x) {
                    var e = x.layer, x = x.latlng;
                    e instanceof D.a.Polyline && ((x = e.getLatLngs().concat([x])).length < 2 || ('length' == this._drawType ? this._showResultLength(x) : this._showResultArea(x)))
                }, _formatLength: function (x, e) {
                    var a = '';
                    switch (null == e && (e = this._drawParams && this._drawParams.unit ? 'function' == _typeof(this._drawParams.unit) ? this._drawParams.unit() : this._drawParams.unit : x < 1e3 ? 'm' : 'km'), e = 'auto' == e ? x < 1e3 ? 'm' : 'km' : e) {
                        default:
                        case'm':
                            a = x.toFixed(0) + ' 米';
                            break;
                        case'km':
                            a = (.001 * x).toFixed(2) + ' 公里';
                            break;
                        case 'mile':
                            a = (54e-5 * x).toFixed(2) + ' 海里';
                            break;
                        case 'zhang':
                            a = (.3 * x).toFixed(2) + ' 丈'
                    }
                    return a
                }, _length_tipmarker: [], _showLengthTipMarker: function (x) {
                    var e, a;
                    1 == x.length ? d = '起点' : (e = D.a.mars.measure.length(x), d = this._formatLength(e), 2 < x.length && (a = D.a.mars.measure.length([x[x.length - 1], x[x.length - 2]]), d += '<br>(+' + this._formatLength(a) + ')'));
                    var d = D.a.marker(x[x.length - 1], {
                        icon: D.a.divIcon({
                            className: 'leaflet-measuretool-result',
                            html: d,
                            iconSize: [null, null],
                            iconAnchor: [-20, 12]
                        })
                    }).addTo(this.layerResult);
                    return d._length = e, d._length2D = a, d
                }, updateLengthUnit: function (x) {
                    for (var e = this.layerDraw.getLayers(), a = 0; a < e.length; a++) {
                        var d = e[a];
                        if ('polyline' == d.type) for (var f = 0; f < d.tipMarker.length; f++) {
                            var t, _ = d.tipMarker[f];
                            null != _._length && (t = this._formatLength(_._length, x), _._length2D && (t += '<br>(+' + this._formatLength(_._length2D, x) + ')'), _._isend ? _._icon.innerHTML = '总长：' + t : _._icon.innerHTML = t)
                        }
                    }
                }, _last_length_val: 0, _showResultLength: function (x) {
                    this._last_length_val = D.a.mars.measure.length(x);
                    x = this._formatLength(this._last_length_val);
                    this._drawParams && this._drawParams.showResult && 'function' == typeof this._drawParams.showResult && this._drawParams.showResult(x, this._last_length_val)
                }, _formatArea: function (x, e) {
                    var a = '';
                    switch (null == e && (e = this._drawParams && this._drawParams.unit ? 'function' == _typeof(this._drawParams.unit) ? this._drawParams.unit() : this._drawParams.unit : x < 1e6 ? 'm' : 'km'), e = 'auto' == e ? x < 1e6 ? 'm' : 'km' : e) {
                        default:
                        case'm':
                            a = x.toFixed(0) + ' 平方米';
                            break;
                        case'km':
                            a = (x / 1e6).toFixed(2) + ' 平方公里';
                            break;
                        case'mu':
                            a = (.0015 * x).toFixed(2) + ' 亩';
                            break;
                        case'ha':
                            a = (1e-4 * x).toFixed(2) + ' 公顷'
                    }
                    return a
                }, _showAreaTipMarker: function (x) {
                    var e = x.getCenter(), a = this._formatArea(this._last_area_val), a = D.a.marker(e, {
                        icon: D.a.divIcon({
                            className: 'leaflet-measuretool-result',
                            html: a,
                            iconSize: [null, 25],
                            iconAnchor: [10 * a.length / 2, 12]
                        })
                    }).addTo(this.layerResult);
                    return a._area = this._last_area_val, x.tipMarker = a
                }, updateAreaUnit: function (x) {
                    for (var e = this.layerDraw.getLayers(), a = 0; a < e.length; a++) {
                        var d, f = e[a];
                        'polygon' == f.type && (d = f.tipMarker, f = this._formatArea(d._area, x), d._icon.innerHTML = f)
                    }
                }, _last_area_val: 0, _showResultArea: function (x) {
                    this._last_area_val = D.a.mars.measure.area(x);
                    x = this._formatArea(this._last_area_val);
                    this._drawParams && this._drawParams.showResult && 'function' == typeof this._drawParams.showResult && this._drawParams.showResult(x, this._last_area_val)
                }
            }), e = {
                length: function (x) {
                    if (0 === (x = 'function' == _typeof(x.getLatLngs) ? x.getLatLngs() : x).length) return 0;
                    for (var e = 0, a = 0, d = x.length - 1; a < d; a++) e += x[a].distanceTo(x[a + 1]);
                    return e
                }, lengthstr: function (x) {
                    return this.lengthFormat(this.length(x))
                }, lengthFormat: function (x) {
                    return 1e3 < x ? (x / 1e3).toFixed(2) + '公里' : x.toFixed(0) + '米'
                }, geodesicArea: function (x) {
                    return this.area(x)
                }, area: function (x) {
                    var e = (x = 'function' == _typeof(x.getLatLngs) ? x.getLatLngs() : x).length;
                    if (null == (e = 1 == e ? (x = x[0]).length : e) || e < 3) return 0;
                    for (var a = 0, d = x[e - 1].lng, f = x[e - 1].lat, t = 0; t < e; t++) {
                        var _ = x[t].lng, i = x[t].lat;
                        a += this.toRadians(_ - d) * (2 + Math.sin(this.toRadians(f)) + Math.sin(this.toRadians(i))), d = _, f = i
                    }
                    return a = 6378137 * a * 6378137 / 2, Math.abs(a)
                }, toRadians: function (x) {
                    return x * Math.PI / 180
                }, areastr: function (x) {
                    return this.areaFormat(this.area(x))
                }, areaFormat: function (x) {
                    return 1e6 <= x ? (x / 1e6).toFixed(2) + '平方公里' : x.toFixed(0) + '平方米'
                }, getAngle: function (x, e) {
                    if (!x || !e) return 0;
                    var a = e.x - x.x, e = e.y - x.y;
                    if (0 == a) {
                        if (0 == e) return 0;
                        if (0 < e) return 90;
                        if (e < 0) return 270
                    }
                    x = Math.atan(e / a) / Math.PI * 180;
                    return a <= 0 && (x += 180), 0 < a && e < 0 && (x += 360), Number(x.toFixed(0))
                }
            }, t = a(7), H = new RegExp('\\.css'), C = document.head || document.getElementsByTagName('head')[0],
            j = +navigator.userAgent.replace(/.*(?:AppleWebKit|AndroidWebKit)\/?(\d+).*/i, '$1') < 536;

        function O(x) {
            return 'complete' === x.readyState || 'loaded' === x.readyState
        }

        function ex(e, x, a) {
            var d = 'onload' in e;

            function f() {
                e.onload = e.onreadystatechange = null, e = null, x()
            }

            'css' !== a || !j && d ? d ? (e.onload = f, e.onerror = function (x) {
                e.onerror = null, 'css' == a ? console.error('该css文件不存在：' + e.href, x) : console.error('该js文件不存在：' + e.src, x), f()
            }) : e.onreadystatechange = function () {
                O(e) && f()
            } : setTimeout(function () {
                !function x(e, a) {
                    var d;
                    e.sheet && (d = !0), setTimeout(function () {
                        d ? a() : x(e, a)
                    }, 20)
                }(e, x)
            }, 1)
        }

        function ax(x, e, a) {
            function d() {
                a && a()
            }

            if (0 !== (x = Array.prototype.slice.call(x || [])).length) for (var f = 0, t = x.length; f < t; f++) !function (e, a, x, d) {
                function f() {
                    var x = a.indexOf(e);
                    -1 < x && a.splice(x, 1), 0 === a.length && d()
                }

                var t, _, i;
                e ? H.test(e) ? (t = e, _ = f, (i = document.createElement('link')).rel = 'stylesheet', ex(i, _, 'css'), i.async = !0, i.href = t, C.appendChild(i)) : (_ = e, t = x, i = f, (x = document.createElement('script')).charset = 'utf-8', ex(x, i, 'js'), x.async = !t.sync, x.src = _, C.appendChild(x)) : setTimeout(function () {
                    f()
                })
            }(x[f], x, e, d); else d()
        }

        var dx, fx, tx, _x, ix, sx = function (x, e) {
            var a, d, f;
            a = document, d = function () {
                ax(x, {}, e)
            }, O(a) ? d() : (f = !1, window.addEventListener('load', function () {
                f || (d(), f = !0)
            }), setTimeout(function () {
                f || (d(), f = !0)
            }, 1500))
        }, nx = '', hx = [];

        function rx(x, e, a) {
            _x = x, nx = a || '', hx = [], dx = (e = e || {}).defaultOptions || {
                windowOptions: {
                    position: 'rt',
                    maxmin: !1,
                    resize: !0
                }, autoDisable: !0, disableOther: !0
            }, 'time' == (fx = e.version) && (fx = (new Date).getTime());
            var d = e.widgetsAtStart;
            if (d && 0 < d.length) for (var f = 0; f < d.length; f++) {
                var t = d[f];
                t.hasOwnProperty('uri') && '' != t.uri ? t.hasOwnProperty('visible') && !t.visible || (t.autoDisable = !1, t.openAtStart = !0, t._nodebug = !0, ox(t), t._firstConfigBak = Object(q.clone)(t), hx.push(t)) : console.log('widget未配置uri', t)
            }
            if ((tx = e.debugger) && (Object(P.a)('body').append('<div id="widget-testbar" class="widgetbar animation-slide-bottom no-print-view" >      <div style="height: 30px; line-height:30px;"><b style="color: #4db3ff;">widget测试栏</b>&nbsp;&nbsp;<button  id="widget-testbar-remove"  type="button" class="btn btn-link btn-xs">关闭</button> </div>     <button id="widget-testbar-disableAll" type="button" class="btn btn-info" ><i class="fa fa-globe"></i>漫游</button></div>'), Object(P.a)('#widget-testbar-remove').click(function (x) {
                gx()
            }), Object(P.a)('#widget-testbar-disableAll').click(function (x) {
                yx()
            })), (d = e.widgets) && 0 < d.length) {
                for (var _ = 0; _ < d.length; _++) {
                    var i, s = d[_];
                    if ('group' == s.type) {
                        for (var n = ' <div class="btn-group dropup">  <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="fa fa-align-justify"></i>' + s.name + ' <span class="caret"></span></button> <ul class="dropdown-menu">', h = 0; h < s.children.length; h++) {
                            var r = s.children[h];
                            r.hasOwnProperty('uri') && '' != r.uri ? (n += ' <li data-widget="' + r.uri + "' class='widget-btn' ><a href='#'><i class='fa fa-star'></i>" + r.name + '</a></li>', ox(r), r._firstConfigBak = Object(q.clone)(r), hx.push(r)) : console.log('widget未配置uri', r)
                        }
                        n += '</ul></div>', tx && !s._nodebug && Object(P.a)('#widget-testbar').append(n)
                    } else s.hasOwnProperty('uri') && '' != s.uri ? (tx && !s._nodebug && (i = '<button type="button" class="btn btn-primary widget-btn" data-widget="' + s.uri + '"  > <i class="fa fa-globe"></i>' + s.name + ' </button>', Object(P.a)('#widget-testbar').append(i)), ox(s), s._firstConfigBak = Object(q.clone)(s), hx.push(s)) : console.log('widget未配置uri', s)
                }
                tx && Object(P.a)('#widget-testbar .widget-btn').each(function () {
                    Object(P.a)(this).click(function (x) {
                        var e = Object(P.a)(this).attr('data-widget');
                        null != e && '' != e && (mx(e) ? px : lx)(e)
                    })
                })
            }
            for (var c, o = 0; o < hx.length; o++) {
                var l = hx[o];
                (l.openAtStart || l.createAtStart) && Gx.push(l)
            }
            Object(P.a)(window).resize(function () {
                for (var x = 0; x < hx.length; x++) {
                    var e = hx[x];
                    e._class && e._class.indexResize()
                }
            }), tx && (c = -1 === (c = window.location.toString()).indexOf('#') ? '' : (c = c.split('#')) && 0 < c.length ? c[1] : void 0) && lx(c), Wx()
        }

        function cx() {
            return Object(q.clone)(dx.windowOptions)
        }

        function ox(x) {
            if (dx) for (var e in dx) 'windowOptions' == e || x.hasOwnProperty(e) || (x[e] = dx[e]);
            var a, d;
            x.path = (d = (a = nx + x.uri).lastIndexOf('/'), a.substring(0, d + 1)), x.name = x.name || x.label
        }

        function lx(x, e) {
            var a;
            null == _x && x.map && rx(x.map, {}, x.basePath), 'string' == typeof x ? (x = {uri: x}, null != e && (x.disableOther = !e)) : null == x.uri && console.error('activate激活widget时需要uri参数！', x);
            for (var d, f = 0; f < hx.length; f++) {
                var t = hx[f];
                if (x.uri == t.uri || t.id && x.uri == t.id) {
                    if ((a = t).isloading) return a;
                    for (var _ in x) 'uri' != _ && (a[_] = x[_]);
                    break
                }
            }
            if (null == a && (ox(x), (a = x)._firstConfigBak || (x._firstConfigBak = Object(q.clone)(x)), hx.push(x)), tx && (console.log('开始激活widget：' + a.uri), window.location.hash = '#' + a.uri), a.hasOwnProperty('disableOhter') && !a.hasOwnProperty('disableOther') && (a.disableOther = a.disableOhter), a.disableOther ? yx(a.uri, a.group) : vx(a.group, a.uri), a._class) a._class.isActivate ? a._class.update ? a._class.update() : (d = a, clearInterval(ix), d._class.disableBase(), ix = setInterval(function () {
                d._class.isActivate || (d._class.activateBase(), clearInterval(ix))
            }, 200)) : a._class.activateBase(); else {
                for (var i = 0; i < Gx.length; i++) if (Gx[i].uri == a.uri) return Gx[i];
                Gx.push(a), 1 == Gx.length && Wx()
            }
            return a
        }

        function ux(x) {
            for (var e = 0; e < hx.length; e++) {
                var a = hx[e];
                if (x == a.uri || x == a.id) return a
            }
        }

        function bx(x) {
            x = ux(x);
            return x ? x._class : null
        }

        function mx(x) {
            x = bx(x);
            return null != x && x.isActivate
        }

        function px(x) {
            if (null == x) return !1;
            'object' === ('undefined' == ('undefined' == typeof id ? 'undefined' : _typeof(id)) ? 'undefined' : K()(id)) && (x = x.uri);
            for (var e = 0; e < hx.length; e++) {
                var a = hx[e];
                if (a._class && (x == a.uri || x == a.id)) return a._class.disableBase(), !0
            }
            return !1
        }

        function yx(x, e) {
            for (var a = 0; a < hx.length; a++) {
                var d = hx[a];
                (e && d.group == e || !0 === x || d.autoDisable) && (!x || x != d.uri && x != d.id) && d._class && d._class.disableBase()
            }
        }

        function vx(x, e) {
            if (null != x) for (var a = 0; a < hx.length; a++) {
                var d = hx[a];
                d.group == x && (!e || e != d.uri && e != d.id) && d._class && d._class.disableBase()
            }
        }

        function Zx(x) {
            for (var e = 0; e < hx.length; e++) x(hx[e])
        }

        var Mx, Vx, Gx = [];

        function Wx() {
            var x;
            0 != Gx.length && (Vx ? setTimeout(Wx, 500) : (Vx = !0, (Mx = Gx[0]).isloading = !0, x = Mx.uri, fx && (-1 == x.indexOf('?') ? x += '?time=' + fx : x += '&time=' + fx), window.NProgress && window.NProgress.start(), tx && console.log('开始加载js：' + nx + x), sx([nx + x], function () {
                Vx = !1, Mx.isloading = !1, window.NProgress && window.NProgress.done(!0), Gx.shift(), Wx()
            })))
        }

        function Xx(x) {
            if (null != Mx) return Mx.isloading = !1, Mx._class = new x(Mx, _x), Mx._class.activateBase(), Mx._class;
            for (var e = function () {
                for (var x, e = document.scripts, a = e.length - 1; 0 <= a; a--) if (null != (x = e[a].src) && '' != x && -1 != x.indexOf('widgets')) return x;
                return ''
            }(), a = 0; a < hx.length; a++) {
                var d = hx[a];
                if (e.endsWith(d.uri)) return d.isloading = !1, d._class = new x(d, _x), d._class.activateBase(), d._class
            }
        }

        function gx() {
            Object(P.a)('#widget-testbar').remove()
        }

        function Rx() {
            return fx
        }

        function wx() {
            return nx
        }

        function Yx() {
            for (var x = 0; x < hx.length; x++) {
                var e = hx[x];
                e._class && (e._class.disableBase(), e._class.destro && e._class.destroy(), delete e._class)
            }
            _x = null
        }

        var Nx = window.layer, kx = [], Y = D.a.Class.extend({
            map: null, options: {}, config: {}, path: '', isActivate: !1, isCreate: !1, initialize: function (x, e) {
                this.map = e, this.config = x, this.path = x.path || '', this.init()
            }, addCacheVersion: function (x) {
                if (null == x) return x;
                var e = fx;
                return e && (-1 == x.indexOf('?') ? x += '?time=' + e : -1 == x.indexOf('time=' + e) && (x += '&time=' + e)), x
            }, activateBase: function () {
                var x = this;
                if (!this.isActivate) {
                    if (this.beforeActivate(), this.isActivate = !0, !this.isCreate) {
                        if (this.options.resources && 0 < this.options.resources.length) {
                            for (var e = [], a = 0; a < this.options.resources.length; a++) {
                                var d = this.options.resources[a], d = this._getUrl(d);
                                -1 == kx.indexOf(d) && e.push(d)
                            }
                            return kx = kx.concat(e), void sx(e, function () {
                                if (!x.create(function () {
                                    x._createWidgetView(), x.isCreate = !0
                                })) {
                                    if (x.config.createAtStart) return x.config.createAtStart = !1, x.isActivate = !1, void (x.isCreate = !0);
                                    x._createWidgetView(), x.isCreate = !0
                                }
                            })
                        }
                        if (this.create(function () {
                            x._createWidgetView(), this.isCreate = !0
                        })) return;
                        if (x.config.createAtStart) return x.config.createAtStart = !1, x.isActivate = !1, void (x.isCreate = !0);
                        this.isCreate = !0
                    }
                    return this._createWidgetView(), this
                }
                this.changeWidgetView(function (x) {
                    x._dom && (Object(P.a)('.layui-layer').each(function () {
                        Object(P.a)(this).css('z-index', 19891e3)
                    }), Object(P.a)(x._dom).css('z-index', 19891014))
                })
            }, _createWidgetView: function () {
                var x = this.options.view;
                if (null == x) this._startActivate(); else if (Array.isArray(x)) {
                    this._viewcreate_allcount = x.length;
                    for (var e = this._viewcreate_okcount = 0; e < x.length; e++) this.createItemView(x[e])
                } else this._viewcreate_allcount = 1, this._viewcreate_okcount = 0, this.createItemView(x)
            }, changeWidgetView: function (x) {
                var e = this.options.view;
                if (null == e) return !1;
                if (Array.isArray(e)) {
                    for (var a = !1, d = 0; d < e.length; d++) a = a || x(e[d]);
                    return a
                }
                return x(e)
            }, createItemView: function (e) {
                var a = this;
                switch (e.type) {
                    case 'window':
                        this._openWindow(e);
                        break;
                    case 'divwindow':
                        this._openDivWindow(e);
                        break;
                    case 'append':
                        var x = this._getUrl(e.url);
                        a.getHtml(x, function (x) {
                            a._appendView(e, x)
                        });
                        break;
                    case'custom':
                        e.open(this._getUrl(e.url), function (x) {
                            a.winCreateOK(e, x), a._viewcreate_okcount++, a._viewcreate_okcount >= a._viewcreate_allcount && a._startActivate(x)
                        }, this)
                }
            }, _viewcreate_allcount: 0, _viewcreate_okcount: 0, _openWindow: function (d) {
                var f = this, t = this._getUrl(d.url), x = {
                    type: 2, content: [t, 'no'], success: function (x, e) {
                        var a;
                        f.isActivate ? (d._layerIdx != e && (Nx.close(d._layerIdx), d._layerIdx = e), d._layerOpening = !1, d._dom = x, a = window[x.find('iframe')[0].name], f.config.css && Object(P.a)('#layui-layer' + d._layerIdx).css(f.config.css), f.config.hasOwnProperty('visible') && !f.config.visible && Object(P.a)(x).hide(), Nx.setTop(x), f.winCreateOK(d, a), f._viewcreate_okcount++, f._viewcreate_okcount >= f._viewcreate_allcount && f._startActivate(x), a && a.initWidgetView ? a.initWidgetView(f) : console.error(t + '页面没有定义function initWidgetView(widget)方法，无法初始化widget页面!')) : Nx.close(e)
                    }
                };
                d._layerIdx, d._layerOpening = !0, d._layerIdx = Nx.open(this._getWinOpt(d, x))
            }, _openDivWindow: function (a) {
                var d = this, x = this._getUrl(a.url);
                this.getHtml(x, function (x) {
                    x = {
                        type: 1, content: x, success: function (x, e) {
                            d.isActivate ? (a._layerIdx != e && (Nx.close(a._layerIdx), a._layerIdx = e), a._layerOpening = !1, a._dom = x, d.config.hasOwnProperty('visible') && !d.config.visible && Object(P.a)(x).hide(), Nx.setTop(x), d.winCreateOK(a, x), d._viewcreate_okcount++, d._viewcreate_okcount >= d._viewcreate_allcount && d._startActivate(x)) : Nx.close(e)
                        }
                    };
                    a._layerOpening = !0, a._layerIdx = Nx.open(d._getWinOpt(a, x))
                })
            }, _getUrl: function (x) {
                return (x = this.addCacheVersion(x)).startsWith('/') || x.startsWith('.') || x.startsWith('http') ? x : this.path + x
            }, _getWinOpt: function (x, e) {
                var a = cx(), d = P.a.extend(a, x.windowOptions), d = P.a.extend(d, this.config.windowOptions);
                x.windowOptions = d;
                var f = this, t = this._getWinSize(d), a = !1;
                d.noTitle || (a = this.config.name || ' ', this.config.icon && (a = '<i class=\'' + this.config.icon + '" ></i>&nbsp;' + a));
                t = {
                    title: a,
                    area: t.area,
                    offset: t.offset,
                    shade: 0,
                    maxmin: !1,
                    zIndex: Nx.zIndex,
                    beforeEnd: function () {
                        f.beforeDisable()
                    },
                    end: function () {
                        x._layerIdx = -1, x._dom = null, f.disableBase(!0)
                    },
                    full: function (x) {
                        f.winFull(x)
                    },
                    min: function (x) {
                        f.winMin(x)
                    },
                    restore: function (x) {
                        f.winRestore(x)
                    }
                }, d = P.a.extend(t, d);
                return P.a.extend(d, e || {})
            }, _getWinSize: function (x) {
                var e, a, d, f = this.bfb2Number(x.width, document.documentElement.clientWidth, x),
                    t = this.bfb2Number(x.height, document.documentElement.clientHeight, x), _ = '', i = x.position;
                return i && ('string' == _typeof(i) ? _ = i : 'object' == K()(i) && (i.hasOwnProperty('top') && null != i.top && (e = this.bfb2Number(i.top, document.documentElement.clientHeight, x)), i.hasOwnProperty('bottom') && null != i.bottom && (x._hasresize = !0, d = this.bfb2Number(i.bottom, document.documentElement.clientHeight, x), null != e ? t = document.documentElement.clientHeight - e - d : e = document.documentElement.clientHeight - t - d), i.hasOwnProperty('left') && null != i.left && (a = this.bfb2Number(i.left, document.documentElement.clientWidth, x)), i.hasOwnProperty('right') && null != i.right && (x._hasresize = !0, i = this.bfb2Number(i.right, document.documentElement.clientWidth, x), null != a ? f = document.documentElement.clientWidth - a - i : a = document.documentElement.clientWidth - f - i), _ = [(e = null == e ? (document.documentElement.clientHeight - t) / 2 : e) + 'px', (a = null == a ? (document.documentElement.clientWidth - f) / 2 : a) + 'px'])), x.hasOwnProperty('minHeight') && t < x.minHeight && (x._hasresize = !0, t = x.minHeight), x.hasOwnProperty('maxHeight') && t > x.maxHeight && (x._hasresize = !0, t = x.maxHeight), x.hasOwnProperty('minHeight') && f < x.minWidth && (x._hasresize = !0, f = x.minWidth), x.hasOwnProperty('maxWidth') && f > x.maxWidth && (x._hasresize = !0, f = x.maxWidth), {
                    area: f && t ? [f + 'px', t + 'px'] : f + 'px',
                    offset: _
                }
            }, bfb2Number: function (x, e, a) {
                return 'string' == _typeof(x) && -1 != x.indexOf('%') ? (a._hasresize = !0, e * Number(x.replace('%', '')) / 100) : x
            }, _appendView: function (x, e) {
                x._dom = Object(P.a)(e).appendTo(x.parent || 'body'), this.config.css && Object(P.a)(x._dom).css(this.config.css), this.winCreateOK(x, e), this._viewcreate_okcount++, this._viewcreate_okcount >= this._viewcreate_allcount && this._startActivate(e)
            }, disableBase: function (x) {
                this.isActivate && (x || this.beforeDisable(), this.changeWidgetView(function (x) {
                    return null != x._layerIdx && -1 != x._layerIdx ? (Nx.close(x._layerIdx), x._layerOpening || (x._layerIdx = -1), !0) : ('append' == x.type && x._dom && (x._dom.remove(), x._dom = null), 'custom' == x.type && x.close && x.close(), !1)
                }) || (this.disable(), this.isActivate = !1, this.config.autoReset && this.resetConfig()))
            }, resetConfig: function () {
                if (this.config._firstConfigBak) {
                    var x, e = this.config._firstConfigBak;
                    for (x in e) 'uri' != x && (this.config[x] = e[x])
                }
            }, setViewVisible: function (e) {
                this.changeWidgetView(function (x) {
                    null != x._layerIdx && -1 != x._layerIdx ? e ? Object(P.a)('#layui-layer' + x._layerIdx).show() : Object(P.a)('#layui-layer' + x._layerIdx).hide() : 'append' == x.type && x._dom && (e ? Object(P.a)(x._dom).show() : Object(P.a)(x._dom).hide())
                })
            }, setViewCss: function (e) {
                this.changeWidgetView(function (x) {
                    null != x._layerIdx && -1 != x._layerIdx ? Object(P.a)('#layui-layer' + x._layerIdx).css(e) : 'append' == x.type && x._dom && Object(P.a)(x._dom).css(e)
                })
            }, indexResize: function () {
                var d;
                this.isActivate && (d = this).changeWidgetView(function (x) {
                    var e, a;
                    null != x._layerIdx && -1 != x._layerIdx && null != x.windowOptions && x.windowOptions._hasresize && (e = d._getWinSize(x.windowOptions), a = {}, Array.isArray(e.area) && (e.area[0] && (a.width = e.area[0]), e.area[1] && (a.height = e.area[1])), Array.isArray(e.offset) && (e.offset[1] && (a.top = e.offset[0]), e.offset[1] && (a.left = e.offset[1])), Object(P.a)(x._dom).attr('myTopLeft', !0), Nx.style(x._layerIdx, a), 'divwindow' == x.type && Nx.iframeAuto(x._layerIdx))
                })
            }, _startActivate: function (x) {
                this.activate(x), this.config.success && this.config.success(this), this.isActivate || this.disableBase()
            }, init: function () {
            }, create: function (x) {
            }, beforeActivate: function () {
            }, activate: function (x) {
            }, beforeDisable: function () {
            }, disable: function () {
            }, winCreateOK: function (x, e) {
            }, winFull: function () {
            }, winMin: function () {
            }, winRestore: function () {
            }, getHtml: function (x, e) {
                P.a.ajax({
                    url: x, type: 'GET', dataType: 'html', timeout: 0, success: function (x) {
                        e(x)
                    }
                })
            }
        }), a = a(12), Fx = {name: 'Mars2D地图平台软件', author: '', website: 'http://mars2d.cn'};
        Fx.version = a.b, Fx.update = a.a, Fx.token = t, Fx.createMap = function (d) {
            return d.url ? (P.a.ajax({
                type: 'get', dataType: 'json', url: d.url, success: function (x) {
                    var e = x.map;
                    x.serverURL && (e.serverURL = x.serverURL), d.serverURL && (e.serverURL = d.serverURL), A(d, e, x)
                }, error: function (x, e, a) {
                    console.log('Json文件加载失败！', d), q.alert('Json文件' + d.url + '加载失败！')
                }
            }), null) : (d.serverURL && d.data && (d.data.serverURL = d.serverURL), A(d, d.data))
        }, Fx.layer = $, Fx.control = {}, Fx.control.bindLocation = xx, Fx.pointconvert = d, Fx.util = q, Fx.measure = e, Fx.layer.baiduTileLayer = Q.a, Fx.Marker = S, Fx.Draw = E, Fx.MeasureTool = B, Fx.Layer = {}, Fx.Layer.VirtualGrid = k, Fx.Layer.HeatLayer = z, Fx.Layer.ArcGISTile = J.a, Fx.Layer.TiandituLayer = T.a, Fx.Layer.TiandituMercatorLayer = T.b, Fx.Layer.WMTS = U.a, Fx.Layer.SimpleGraticule = L.a, Fx.Layer.Terminator = I.a;
        var zx = f;
        zx.BaseWidget = Y, D.a.mars = Fx, D.a.widget = zx
    }], I = {}, J.m = H, J.c = I, J.d = function (x, e, a) {
        J.o(x, e) || Object.defineProperty(x, e, {enumerable: !0, get: a})
    }, J.r = function (x) {
        'undefined' != typeof Symbol && Symbol.toStringTag && Object.defineProperty(x, Symbol.toStringTag, {value: 'Module'}), Object.defineProperty(x, '__esModule', {value: !0})
    }, J.t = function (e, x) {
        if (1 & x && (e = J(e)), 8 & x) return e;
        if (4 & x && 'object' == _typeof(e) && e && e.__esModule) return e;
        var a = Object.create(null);
        if (J.r(a), Object.defineProperty(a, 'default', {
            enumerable: !0,
            value: e
        }), 2 & x && 'string' != _typeof(e)) for (var d in e) J.d(a, d, function (x) {
            return e[x]
        }.bind(null, d));
        return a
    }, J.n = function (x) {
        var e = x && x.__esModule ? function () {
            return x.default
        } : function () {
            return x
        };
        return J.d(e, 'a', e), e
    }, J.o = function (x, e) {
        return Object.prototype.hasOwnProperty.call(x, e)
    }, J.p = '', J(J.s = 25);

    function J(x) {
        if (I[x]) return I[x].exports;
        var e = I[x] = {i: x, l: !1, exports: {}};
        return H[x].call(e.exports, e, e.exports, J), e.l = !0, e.exports
    }

    var H, I
});
