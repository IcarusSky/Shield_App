/*
 Highcharts JS v6.0.7 (2018-02-16)

 (c) 2009-2016 Torstein Honsi

 License: www.highcharts.com/license
*/
(function (S, K) {
    "object" === typeof module && module.exports ? module.exports = S.document ? K(S) : K : S.Highcharts = K(S)
})("undefined" !== typeof window ? window : this, function (S) {
    var K = function () {
        var a = "undefined" === typeof S ? window : S, B = a.document, H = a.navigator && a.navigator.userAgent || "",
            E = B && B.createElementNS && !!B.createElementNS("http://www.w3.org/2000/svg", "svg").createSVGRect,
            q = /(edge|msie|trident)/i.test(H) && !a.opera, f = -1 !== H.indexOf("Firefox"),
            l = -1 !== H.indexOf("Chrome"), t = f && 4 > parseInt(H.split("Firefox/")[1],
            10);
        return a.Highcharts ? a.Highcharts.error(16, !0) : {
            product: "Highcharts",
            version: "6.0.7",
            deg2rad: 2 * Math.PI / 360,
            doc: B,
            hasBidiBug: t,
            hasTouch: B && void 0 !== B.documentElement.ontouchstart,
            isMS: q,
            isWebKit: -1 !== H.indexOf("AppleWebKit"),
            isFirefox: f,
            isChrome: l,
            isSafari: !l && -1 !== H.indexOf("Safari"),
            isTouchDevice: /(Mobile|Android|Windows Phone)/.test(H),
            SVG_NS: "http://www.w3.org/2000/svg",
            chartCount: 0,
            seriesTypes: {},
            symbolSizes: {},
            svg: E,
            win: a,
            marginNames: ["plotTop", "marginRight", "marginBottom", "plotLeft"],
            noop: function () {
            },
            charts: []
        }
    }();
    (function (a) {
        a.timers = [];
        var B = a.charts, H = a.doc, E = a.win;
        a.error = function (q, f) {
            q = a.isNumber(q) ? "Highcharts error #" + q + ": www.highcharts.com/errors/" + q : q;
            if (f) throw Error(q);
            E.console && console.log(q)
        };
        a.Fx = function (a, f, l) {
            this.options = f;
            this.elem = a;
            this.prop = l
        };
        a.Fx.prototype = {
            dSetter: function () {
                var a = this.paths[0], f = this.paths[1], l = [], t = this.now, n = a.length, v;
                if (1 === t) l = this.toD; else if (n === f.length && 1 > t) for (; n--;) v = parseFloat(a[n]), l[n] = isNaN(v) ? f[n] : t * parseFloat(f[n] - v) + v; else l = f;
                this.elem.attr("d",
                    l, null, !0)
            }, update: function () {
                var a = this.elem, f = this.prop, l = this.now, t = this.options.step;
                if (this[f + "Setter"]) this[f + "Setter"](); else a.attr ? a.element && a.attr(f, l, null, !0) : a.style[f] = l + this.unit;
                t && t.call(a, l, this)
            }, run: function (q, f, l) {
                var t = this, n = t.options, v = function (a) {
                    return v.stopped ? !1 : t.step(a)
                }, u = E.requestAnimationFrame || function (a) {
                    setTimeout(a, 13)
                }, c = function () {
                    for (var b = 0; b < a.timers.length; b++) a.timers[b]() || a.timers.splice(b--, 1);
                    a.timers.length && u(c)
                };
                q === f ? (delete n.curAnim[this.prop],
                n.complete && 0 === a.keys(n.curAnim).length && n.complete.call(this.elem)) : (this.startTime = +new Date, this.start = q, this.end = f, this.unit = l, this.now = this.start, this.pos = 0, v.elem = this.elem, v.prop = this.prop, v() && 1 === a.timers.push(v) && u(c))
            }, step: function (q) {
                var f = +new Date, l, t = this.options, n = this.elem, v = t.complete, u = t.duration, c = t.curAnim;
                n.attr && !n.element ? q = !1 : q || f >= u + this.startTime ? (this.now = this.end, this.pos = 1, this.update(), l = c[this.prop] = !0, a.objectEach(c, function (a) {
                    !0 !== a && (l = !1)
                }), l && v && v.call(n), q =
                    !1) : (this.pos = t.easing((f - this.startTime) / u), this.now = this.start + (this.end - this.start) * this.pos, this.update(), q = !0);
                return q
            }, initPath: function (q, f, l) {
                function t(a) {
                    var c, h;
                    for (e = a.length; e--;) c = "M" === a[e] || "L" === a[e], h = /[a-zA-Z]/.test(a[e + 3]), c && h && a.splice(e + 1, 0, a[e + 1], a[e + 2], a[e + 1], a[e + 2])
                }

                function n(a, c) {
                    for (; a.length < h;) {
                        a[0] = c[h - a.length];
                        var b = a.slice(0, d);
                        [].splice.apply(a, [0, 0].concat(b));
                        p && (b = a.slice(a.length - d), [].splice.apply(a, [a.length, 0].concat(b)), e--)
                    }
                    a[0] = "M"
                }

                function v(a, c) {
                    for (var b =
                        (h - a.length) / d; 0 < b && b--;) k = a.slice().splice(a.length / r - d, d * r), k[0] = c[h - d - b * d], m && (k[d - 6] = k[d - 2], k[d - 5] = k[d - 1]), [].splice.apply(a, [a.length / r, 0].concat(k)), p && b--
                }

                f = f || "";
                var u, c = q.startX, b = q.endX, m = -1 < f.indexOf("C"), d = m ? 7 : 3, h, k, e;
                f = f.split(" ");
                l = l.slice();
                var p = q.isArea, r = p ? 2 : 1, I;
                m && (t(f), t(l));
                if (c && b) {
                    for (e = 0; e < c.length; e++) if (c[e] === b[0]) {
                        u = e;
                        break
                    } else if (c[0] === b[b.length - c.length + e]) {
                        u = e;
                        I = !0;
                        break
                    }
                    void 0 === u && (f = [])
                }
                f.length && a.isNumber(u) && (h = l.length + u * r * d, I ? (n(f, l), v(l, f)) : (n(l, f), v(f,
                    l)));
                return [f, l]
            }
        };
        a.Fx.prototype.fillSetter = a.Fx.prototype.strokeSetter = function () {
            this.elem.attr(this.prop, a.color(this.start).tweenTo(a.color(this.end), this.pos), null, !0)
        };
        a.merge = function () {
            var q, f = arguments, l, t = {}, n = function (l, q) {
                "object" !== typeof l && (l = {});
                a.objectEach(q, function (c, b) {
                    !a.isObject(c, !0) || a.isClass(c) || a.isDOMElement(c) ? l[b] = q[b] : l[b] = n(l[b] || {}, c)
                });
                return l
            };
            !0 === f[0] && (t = f[1], f = Array.prototype.slice.call(f, 2));
            l = f.length;
            for (q = 0; q < l; q++) t = n(t, f[q]);
            return t
        };
        a.pInt = function (a,
                           f) {
            return parseInt(a, f || 10)
        };
        a.isString = function (a) {
            return "string" === typeof a
        };
        a.isArray = function (a) {
            a = Object.prototype.toString.call(a);
            return "[object Array]" === a || "[object Array Iterator]" === a
        };
        a.isObject = function (q, f) {
            return !!q && "object" === typeof q && (!f || !a.isArray(q))
        };
        a.isDOMElement = function (q) {
            return a.isObject(q) && "number" === typeof q.nodeType
        };
        a.isClass = function (q) {
            var f = q && q.constructor;
            return !(!a.isObject(q, !0) || a.isDOMElement(q) || !f || !f.name || "Object" === f.name)
        };
        a.isNumber = function (a) {
            return "number" ===
                typeof a && !isNaN(a) && Infinity > a && -Infinity < a
        };
        a.erase = function (a, f) {
            for (var l = a.length; l--;) if (a[l] === f) {
                a.splice(l, 1);
                break
            }
        };
        a.defined = function (a) {
            return void 0 !== a && null !== a
        };
        a.attr = function (q, f, l) {
            var t;
            a.isString(f) ? a.defined(l) ? q.setAttribute(f, l) : q && q.getAttribute && (t = q.getAttribute(f)) : a.defined(f) && a.isObject(f) && a.objectEach(f, function (a, l) {
                q.setAttribute(l, a)
            });
            return t
        };
        a.splat = function (q) {
            return a.isArray(q) ? q : [q]
        };
        a.syncTimeout = function (a, f, l) {
            if (f) return setTimeout(a, f, l);
            a.call(0,
                l)
        };
        a.extend = function (a, f) {
            var l;
            a || (a = {});
            for (l in f) a[l] = f[l];
            return a
        };
        a.pick = function () {
            var a = arguments, f, l, t = a.length;
            for (f = 0; f < t; f++) if (l = a[f], void 0 !== l && null !== l) return l
        };
        a.css = function (q, f) {
            a.isMS && !a.svg && f && void 0 !== f.opacity && (f.filter = "alpha(opacity\x3d" + 100 * f.opacity + ")");
            a.extend(q.style, f)
        };
        a.createElement = function (q, f, l, t, n) {
            q = H.createElement(q);
            var v = a.css;
            f && a.extend(q, f);
            n && v(q, {padding: 0, border: "none", margin: 0});
            l && v(q, l);
            t && t.appendChild(q);
            return q
        };
        a.extendClass = function (q,
                                  f) {
            var l = function () {
            };
            l.prototype = new q;
            a.extend(l.prototype, f);
            return l
        };
        a.pad = function (a, f, l) {
            return Array((f || 2) + 1 - String(a).length).join(l || 0) + a
        };
        a.relativeLength = function (a, f, l) {
            return /%$/.test(a) ? f * parseFloat(a) / 100 + (l || 0) : parseFloat(a)
        };
        a.wrap = function (a, f, l) {
            var t = a[f];
            a[f] = function () {
                var a = Array.prototype.slice.call(arguments), v = arguments, u = this;
                u.proceed = function () {
                    t.apply(u, arguments.length ? arguments : v)
                };
                a.unshift(t);
                a = l.apply(this, a);
                u.proceed = null;
                return a
            }
        };
        a.formatSingle = function (q,
                                   f, l) {
            var t = /\.([0-9])/, n = a.defaultOptions.lang;
            /f$/.test(q) ? (l = (l = q.match(t)) ? l[1] : -1, null !== f && (f = a.numberFormat(f, l, n.decimalPoint, -1 < q.indexOf(",") ? n.thousandsSep : ""))) : f = (l || a.time).dateFormat(q, f);
            return f
        };
        a.format = function (q, f, l) {
            for (var t = "{", n = !1, v, u, c, b, m = [], d; q;) {
                t = q.indexOf(t);
                if (-1 === t) break;
                v = q.slice(0, t);
                if (n) {
                    v = v.split(":");
                    u = v.shift().split(".");
                    b = u.length;
                    d = f;
                    for (c = 0; c < b; c++) d && (d = d[u[c]]);
                    v.length && (d = a.formatSingle(v.join(":"), d, l));
                    m.push(d)
                } else m.push(v);
                q = q.slice(t + 1);
                t = (n =
                    !n) ? "}" : "{"
            }
            m.push(q);
            return m.join("")
        };
        a.getMagnitude = function (a) {
            return Math.pow(10, Math.floor(Math.log(a) / Math.LN10))
        };
        a.normalizeTickInterval = function (q, f, l, t, n) {
            var v, u = q;
            l = a.pick(l, 1);
            v = q / l;
            f || (f = n ? [1, 1.2, 1.5, 2, 2.5, 3, 4, 5, 6, 8, 10] : [1, 2, 2.5, 5, 10], !1 === t && (1 === l ? f = a.grep(f, function (a) {
                return 0 === a % 1
            }) : .1 >= l && (f = [1 / l])));
            for (t = 0; t < f.length && !(u = f[t], n && u * l >= q || !n && v <= (f[t] + (f[t + 1] || f[t])) / 2); t++) ;
            return u = a.correctFloat(u * l, -Math.round(Math.log(.001) / Math.LN10))
        };
        a.stableSort = function (a, f) {
            var l =
                a.length, t, n;
            for (n = 0; n < l; n++) a[n].safeI = n;
            a.sort(function (a, l) {
                t = f(a, l);
                return 0 === t ? a.safeI - l.safeI : t
            });
            for (n = 0; n < l; n++) delete a[n].safeI
        };
        a.arrayMin = function (a) {
            for (var f = a.length, l = a[0]; f--;) a[f] < l && (l = a[f]);
            return l
        };
        a.arrayMax = function (a) {
            for (var f = a.length, l = a[0]; f--;) a[f] > l && (l = a[f]);
            return l
        };
        a.destroyObjectProperties = function (q, f) {
            a.objectEach(q, function (a, t) {
                a && a !== f && a.destroy && a.destroy();
                delete q[t]
            })
        };
        a.discardElement = function (q) {
            var f = a.garbageBin;
            f || (f = a.createElement("div"));
            q && f.appendChild(q);
            f.innerHTML = ""
        };
        a.correctFloat = function (a, f) {
            return parseFloat(a.toPrecision(f || 14))
        };
        a.setAnimation = function (q, f) {
            f.renderer.globalAnimation = a.pick(q, f.options.chart.animation, !0)
        };
        a.animObject = function (q) {
            return a.isObject(q) ? a.merge(q) : {duration: q ? 500 : 0}
        };
        a.timeUnits = {
            millisecond: 1,
            second: 1E3,
            minute: 6E4,
            hour: 36E5,
            day: 864E5,
            week: 6048E5,
            month: 24192E5,
            year: 314496E5
        };
        a.numberFormat = function (q, f, l, t) {
            q = +q || 0;
            f = +f;
            var n = a.defaultOptions.lang, v = (q.toString().split(".")[1] || "").split("e")[0].length, u,
                c, b = q.toString().split("e");
            -1 === f ? f = Math.min(v, 20) : a.isNumber(f) ? f && b[1] && 0 > b[1] && (u = f + +b[1], 0 <= u ? (b[0] = (+b[0]).toExponential(u).split("e")[0], f = u) : (b[0] = b[0].split(".")[0] || 0, q = 20 > f ? (b[0] * Math.pow(10, b[1])).toFixed(f) : 0, b[1] = 0)) : f = 2;
            c = (Math.abs(b[1] ? b[0] : q) + Math.pow(10, -Math.max(f, v) - 1)).toFixed(f);
            v = String(a.pInt(c));
            u = 3 < v.length ? v.length % 3 : 0;
            l = a.pick(l, n.decimalPoint);
            t = a.pick(t, n.thousandsSep);
            q = (0 > q ? "-" : "") + (u ? v.substr(0, u) + t : "");
            q += v.substr(u).replace(/(\d{3})(?=\d)/g, "$1" + t);
            f && (q += l + c.slice(-f));
            b[1] && 0 !== +q && (q += "e" + b[1]);
            return q
        };
        Math.easeInOutSine = function (a) {
            return -.5 * (Math.cos(Math.PI * a) - 1)
        };
        a.getStyle = function (q, f, l) {
            if ("width" === f) return Math.min(q.offsetWidth, q.scrollWidth) - a.getStyle(q, "padding-left") - a.getStyle(q, "padding-right");
            if ("height" === f) return Math.min(q.offsetHeight, q.scrollHeight) - a.getStyle(q, "padding-top") - a.getStyle(q, "padding-bottom");
            E.getComputedStyle || a.error(27, !0);
            if (q = E.getComputedStyle(q, void 0)) q = q.getPropertyValue(f), a.pick(l, "opacity" !== f) && (q = a.pInt(q));
            return q
        };
        a.inArray = function (q, f) {
            return (a.indexOfPolyfill || Array.prototype.indexOf).call(f, q)
        };
        a.grep = function (q, f) {
            return (a.filterPolyfill || Array.prototype.filter).call(q, f)
        };
        a.find = Array.prototype.find ? function (a, f) {
            return a.find(f)
        } : function (a, f) {
            var l, t = a.length;
            for (l = 0; l < t; l++) if (f(a[l], l)) return a[l]
        };
        a.map = function (a, f) {
            for (var l = [], t = 0, n = a.length; t < n; t++) l[t] = f.call(a[t], a[t], t, a);
            return l
        };
        a.keys = function (q) {
            return (a.keysPolyfill || Object.keys).call(void 0, q)
        };
        a.reduce = function (q, f, l) {
            return (a.reducePolyfill ||
                Array.prototype.reduce).call(q, f, l)
        };
        a.offset = function (a) {
            var f = H.documentElement;
            a = a.parentElement ? a.getBoundingClientRect() : {top: 0, left: 0};
            return {
                top: a.top + (E.pageYOffset || f.scrollTop) - (f.clientTop || 0),
                left: a.left + (E.pageXOffset || f.scrollLeft) - (f.clientLeft || 0)
            }
        };
        a.stop = function (q, f) {
            for (var l = a.timers.length; l--;) a.timers[l].elem !== q || f && f !== a.timers[l].prop || (a.timers[l].stopped = !0)
        };
        a.each = function (q, f, l) {
            return (a.forEachPolyfill || Array.prototype.forEach).call(q, f, l)
        };
        a.objectEach = function (a,
                                 f, l) {
            for (var t in a) a.hasOwnProperty(t) && f.call(l, a[t], t, a)
        };
        a.isPrototype = function (q) {
            return q === a.Axis.prototype || q === a.Chart.prototype || q === a.Point.prototype || q === a.Series.prototype || q === a.Tick.prototype
        };
        a.addEvent = function (q, f, l) {
            var t, n = q.addEventListener || a.addEventListenerPolyfill;
            t = a.isPrototype(q) ? "protoEvents" : "hcEvents";
            t = q[t] = q[t] || {};
            n && n.call(q, f, l, !1);
            t[f] || (t[f] = []);
            t[f].push(l);
            return function () {
                a.removeEvent(q, f, l)
            }
        };
        a.removeEvent = function (q, f, l) {
            function t(c, b) {
                var m = q.removeEventListener ||
                    a.removeEventListenerPolyfill;
                m && m.call(q, c, b, !1)
            }

            function n(c) {
                var b, m;
                q.nodeName && (f ? (b = {}, b[f] = !0) : b = c, a.objectEach(b, function (a, h) {
                    if (c[h]) for (m = c[h].length; m--;) t(h, c[h][m])
                }))
            }

            var v, u;
            a.each(["protoEvents", "hcEvents"], function (c) {
                var b = q[c];
                b && (f ? (v = b[f] || [], l ? (u = a.inArray(l, v), -1 < u && (v.splice(u, 1), b[f] = v), t(f, l)) : (n(b), b[f] = [])) : (n(b), q[c] = {}))
            })
        };
        a.fireEvent = function (q, f, l, t) {
            var n, v, u, c, b;
            l = l || {};
            H.createEvent && (q.dispatchEvent || q.fireEvent) ? (n = H.createEvent("Events"), n.initEvent(f, !0, !0),
                a.extend(n, l), q.dispatchEvent ? q.dispatchEvent(n) : q.fireEvent(f, n)) : a.each(["protoEvents", "hcEvents"], function (m) {
                if (q[m]) for (v = q[m][f] || [], u = v.length, l.target || a.extend(l, {
                    preventDefault: function () {
                        l.defaultPrevented = !0
                    }, target: q, type: f
                }), c = 0; c < u; c++) (b = v[c]) && !1 === b.call(q, l) && l.preventDefault()
            });
            t && !l.defaultPrevented && t(l)
        };
        a.animate = function (q, f, l) {
            var t, n = "", v, u, c;
            a.isObject(l) || (c = arguments, l = {duration: c[2], easing: c[3], complete: c[4]});
            a.isNumber(l.duration) || (l.duration = 400);
            l.easing = "function" ===
            typeof l.easing ? l.easing : Math[l.easing] || Math.easeInOutSine;
            l.curAnim = a.merge(f);
            a.objectEach(f, function (c, m) {
                a.stop(q, m);
                u = new a.Fx(q, l, m);
                v = null;
                "d" === m ? (u.paths = u.initPath(q, q.d, f.d), u.toD = f.d, t = 0, v = 1) : q.attr ? t = q.attr(m) : (t = parseFloat(a.getStyle(q, m)) || 0, "opacity" !== m && (n = "px"));
                v || (v = c);
                v && v.match && v.match("px") && (v = v.replace(/px/g, ""));
                u.run(t, v, n)
            })
        };
        a.seriesType = function (q, f, l, t, n) {
            var v = a.getOptions(), u = a.seriesTypes;
            v.plotOptions[q] = a.merge(v.plotOptions[f], l);
            u[q] = a.extendClass(u[f] || function () {
            },
                t);
            u[q].prototype.type = q;
            n && (u[q].prototype.pointClass = a.extendClass(a.Point, n));
            return u[q]
        };
        a.uniqueKey = function () {
            var a = Math.random().toString(36).substring(2, 9), f = 0;
            return function () {
                return "highcharts-" + a + "-" + f++
            }
        }();
        E.jQuery && (E.jQuery.fn.highcharts = function () {
            var q = [].slice.call(arguments);
            if (this[0]) return q[0] ? (new (a[a.isString(q[0]) ? q.shift() : "Chart"])(this[0], q[0], q[1]), this) : B[a.attr(this[0], "data-highcharts-chart")]
        })
    })(K);
    (function (a) {
        var B = a.each, H = a.isNumber, E = a.map, q = a.merge, f = a.pInt;
        a.Color = function (l) {
            if (!(this instanceof a.Color)) return new a.Color(l);
            this.init(l)
        };
        a.Color.prototype = {
            parsers: [{
                regex: /rgba\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]?(?:\.[0-9]+)?)\s*\)/,
                parse: function (a) {
                    return [f(a[1]), f(a[2]), f(a[3]), parseFloat(a[4], 10)]
                }
            }, {
                regex: /rgb\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*\)/, parse: function (a) {
                    return [f(a[1]), f(a[2]), f(a[3]), 1]
                }
            }], names: {none: "rgba(255,255,255,0)", white: "#ffffff", black: "#000000"}, init: function (l) {
                var f,
                    n, v, u;
                if ((this.input = l = this.names[l && l.toLowerCase ? l.toLowerCase() : ""] || l) && l.stops) this.stops = E(l.stops, function (c) {
                    return new a.Color(c[1])
                }); else if (l && l.charAt && "#" === l.charAt() && (f = l.length, l = parseInt(l.substr(1), 16), 7 === f ? n = [(l & 16711680) >> 16, (l & 65280) >> 8, l & 255, 1] : 4 === f && (n = [(l & 3840) >> 4 | (l & 3840) >> 8, (l & 240) >> 4 | l & 240, (l & 15) << 4 | l & 15, 1])), !n) for (v = this.parsers.length; v-- && !n;) u = this.parsers[v], (f = u.regex.exec(l)) && (n = u.parse(f));
                this.rgba = n || []
            }, get: function (a) {
                var l = this.input, n = this.rgba, v;
                this.stops ?
                    (v = q(l), v.stops = [].concat(v.stops), B(this.stops, function (n, c) {
                        v.stops[c] = [v.stops[c][0], n.get(a)]
                    })) : v = n && H(n[0]) ? "rgb" === a || !a && 1 === n[3] ? "rgb(" + n[0] + "," + n[1] + "," + n[2] + ")" : "a" === a ? n[3] : "rgba(" + n.join(",") + ")" : l;
                return v
            }, brighten: function (a) {
                var l, n = this.rgba;
                if (this.stops) B(this.stops, function (n) {
                    n.brighten(a)
                }); else if (H(a) && 0 !== a) for (l = 0; 3 > l; l++) n[l] += f(255 * a), 0 > n[l] && (n[l] = 0), 255 < n[l] && (n[l] = 255);
                return this
            }, setOpacity: function (a) {
                this.rgba[3] = a;
                return this
            }, tweenTo: function (a, f) {
                var n = this.rgba,
                    l = a.rgba;
                l.length && n && n.length ? (a = 1 !== l[3] || 1 !== n[3], f = (a ? "rgba(" : "rgb(") + Math.round(l[0] + (n[0] - l[0]) * (1 - f)) + "," + Math.round(l[1] + (n[1] - l[1]) * (1 - f)) + "," + Math.round(l[2] + (n[2] - l[2]) * (1 - f)) + (a ? "," + (l[3] + (n[3] - l[3]) * (1 - f)) : "") + ")") : f = a.input || "none";
                return f
            }
        };
        a.color = function (l) {
            return new a.Color(l)
        }
    })(K);
    (function (a) {
        var B, H, E = a.addEvent, q = a.animate, f = a.attr, l = a.charts, t = a.color, n = a.css, v = a.createElement,
            u = a.defined, c = a.deg2rad, b = a.destroyObjectProperties, m = a.doc, d = a.each, h = a.extend,
            k = a.erase, e = a.grep,
            p = a.hasTouch, r = a.inArray, I = a.isArray, z = a.isFirefox, M = a.isMS, D = a.isObject, C = a.isString,
            x = a.isWebKit, F = a.merge, A = a.noop, J = a.objectEach, G = a.pick, g = a.pInt, w = a.removeEvent,
            L = a.stop, P = a.svg, N = a.SVG_NS, O = a.symbolSizes, R = a.win;
        B = a.SVGElement = function () {
            return this
        };
        h(B.prototype, {
            opacity: 1,
            SVG_NS: N,
            textProps: "direction fontSize fontWeight fontFamily fontStyle color lineHeight width textAlign textDecoration textOverflow textOutline".split(" "),
            init: function (a, g) {
                this.element = "span" === g ? v(g) : m.createElementNS(this.SVG_NS,
                    g);
                this.renderer = a
            },
            animate: function (y, g, c) {
                g = a.animObject(G(g, this.renderer.globalAnimation, !0));
                0 !== g.duration ? (c && (g.complete = c), q(this, y, g)) : (this.attr(y, null, c), g.step && g.step.call(this));
                return this
            },
            colorGradient: function (y, g, c) {
                var w = this.renderer, h, b, e, k, p, Q, x, N, m, A, r = [], P;
                y.radialGradient ? b = "radialGradient" : y.linearGradient && (b = "linearGradient");
                b && (e = y[b], p = w.gradients, x = y.stops, A = c.radialReference, I(e) && (y[b] = e = {
                    x1: e[0],
                    y1: e[1],
                    x2: e[2],
                    y2: e[3],
                    gradientUnits: "userSpaceOnUse"
                }), "radialGradient" ===
                b && A && !u(e.gradientUnits) && (k = e, e = F(e, w.getRadialAttr(A, k), {gradientUnits: "userSpaceOnUse"})), J(e, function (a, y) {
                    "id" !== y && r.push(y, a)
                }), J(x, function (a) {
                    r.push(a)
                }), r = r.join(","), p[r] ? A = p[r].attr("id") : (e.id = A = a.uniqueKey(), p[r] = Q = w.createElement(b).attr(e).add(w.defs), Q.radAttr = k, Q.stops = [], d(x, function (y) {
                    0 === y[1].indexOf("rgba") ? (h = a.color(y[1]), N = h.get("rgb"), m = h.get("a")) : (N = y[1], m = 1);
                    y = w.createElement("stop").attr({offset: y[0], "stop-color": N, "stop-opacity": m}).add(Q);
                    Q.stops.push(y)
                })), P = "url(" +
                    w.url + "#" + A + ")", c.setAttribute(g, P), c.gradient = r, y.toString = function () {
                    return P
                })
            },
            applyTextOutline: function (y) {
                var g = this.element, c, w, h, e, b;
                -1 !== y.indexOf("contrast") && (y = y.replace(/contrast/g, this.renderer.getContrast(g.style.fill)));
                y = y.split(" ");
                w = y[y.length - 1];
                if ((h = y[0]) && "none" !== h && a.svg) {
                    this.fakeTS = !0;
                    y = [].slice.call(g.getElementsByTagName("tspan"));
                    this.ySetter = this.xSetter;
                    h = h.replace(/(^[\d\.]+)(.*?)$/g, function (a, y, g) {
                        return 2 * y + g
                    });
                    for (b = y.length; b--;) c = y[b], "highcharts-text-outline" ===
                    c.getAttribute("class") && k(y, g.removeChild(c));
                    e = g.firstChild;
                    d(y, function (a, y) {
                        0 === y && (a.setAttribute("x", g.getAttribute("x")), y = g.getAttribute("y"), a.setAttribute("y", y || 0), null === y && g.setAttribute("y", 0));
                        a = a.cloneNode(1);
                        f(a, {
                            "class": "highcharts-text-outline",
                            fill: w,
                            stroke: w,
                            "stroke-width": h,
                            "stroke-linejoin": "round"
                        });
                        g.insertBefore(a, e)
                    })
                }
            },
            attr: function (a, g, c, w) {
                var y, h = this.element, b, e = this, d, k;
                "string" === typeof a && void 0 !== g && (y = a, a = {}, a[y] = g);
                "string" === typeof a ? e = (this[a + "Getter"] || this._defaultGetter).call(this,
                    a, h) : (J(a, function (y, g) {
                    d = !1;
                    w || L(this, g);
                    this.symbolName && /^(x|y|width|height|r|start|end|innerR|anchorX|anchorY)$/.test(g) && (b || (this.symbolAttr(a), b = !0), d = !0);
                    !this.rotation || "x" !== g && "y" !== g || (this.doTransform = !0);
                    d || (k = this[g + "Setter"] || this._defaultSetter, k.call(this, y, g, h), this.shadows && /^(width|height|visibility|x|y|d|transform|cx|cy|r)$/.test(g) && this.updateShadows(g, y, k))
                }, this), this.afterSetters());
                c && c.call(this);
                return e
            },
            afterSetters: function () {
                this.doTransform && (this.updateTransform(),
                    this.doTransform = !1)
            },
            updateShadows: function (a, g, c) {
                for (var y = this.shadows, w = y.length; w--;) c.call(y[w], "height" === a ? Math.max(g - (y[w].cutHeight || 0), 0) : "d" === a ? this.d : g, a, y[w])
            },
            addClass: function (a, g) {
                var y = this.attr("class") || "";
                -1 === y.indexOf(a) && (g || (a = (y + (y ? " " : "") + a).replace("  ", " ")), this.attr("class", a));
                return this
            },
            hasClass: function (a) {
                return -1 !== r(a, (this.attr("class") || "").split(" "))
            },
            removeClass: function (a) {
                return this.attr("class", (this.attr("class") || "").replace(a, ""))
            },
            symbolAttr: function (a) {
                var y =
                    this;
                d("x y r start end width height innerR anchorX anchorY".split(" "), function (g) {
                    y[g] = G(a[g], y[g])
                });
                y.attr({d: y.renderer.symbols[y.symbolName](y.x, y.y, y.width, y.height, y)})
            },
            clip: function (a) {
                return this.attr("clip-path", a ? "url(" + this.renderer.url + "#" + a.id + ")" : "none")
            },
            crisp: function (a, g) {
                var y;
                g = g || a.strokeWidth || 0;
                y = Math.round(g) % 2 / 2;
                a.x = Math.floor(a.x || this.x || 0) + y;
                a.y = Math.floor(a.y || this.y || 0) + y;
                a.width = Math.floor((a.width || this.width || 0) - 2 * y);
                a.height = Math.floor((a.height || this.height || 0) -
                    2 * y);
                u(a.strokeWidth) && (a.strokeWidth = g);
                return a
            },
            css: function (a) {
                var y = this.styles, c = {}, w = this.element, b, e = "", d, k = !y,
                    p = ["textOutline", "textOverflow", "width"];
                a && a.color && (a.fill = a.color);
                y && J(a, function (a, g) {
                    a !== y[g] && (c[g] = a, k = !0)
                });
                k && (y && (a = h(y, c)), b = this.textWidth = a && a.width && "auto" !== a.width && "text" === w.nodeName.toLowerCase() && g(a.width), this.styles = a, b && !P && this.renderer.forExport && delete a.width, w.namespaceURI === this.SVG_NS ? (d = function (a, y) {
                    return "-" + y.toLowerCase()
                }, J(a, function (a, y) {
                    -1 ===
                    r(y, p) && (e += y.replace(/([A-Z])/g, d) + ":" + a + ";")
                }), e && f(w, "style", e)) : n(w, a), this.added && ("text" === this.element.nodeName && this.renderer.buildText(this), a && a.textOutline && this.applyTextOutline(a.textOutline)));
                return this
            },
            strokeWidth: function () {
                return this["stroke-width"] || 0
            },
            on: function (a, g) {
                var y = this, c = y.element;
                p && "click" === a ? (c.ontouchstart = function (a) {
                    y.touchEventFired = Date.now();
                    a.preventDefault();
                    g.call(c, a)
                }, c.onclick = function (a) {
                    (-1 === R.navigator.userAgent.indexOf("Android") || 1100 < Date.now() -
                        (y.touchEventFired || 0)) && g.call(c, a)
                }) : c["on" + a] = g;
                return this
            },
            setRadialReference: function (a) {
                var y = this.renderer.gradients[this.element.gradient];
                this.element.radialReference = a;
                y && y.radAttr && y.animate(this.renderer.getRadialAttr(a, y.radAttr));
                return this
            },
            translate: function (a, g) {
                return this.attr({translateX: a, translateY: g})
            },
            invert: function (a) {
                this.inverted = a;
                this.updateTransform();
                return this
            },
            updateTransform: function () {
                var a = this.translateX || 0, g = this.translateY || 0, c = this.scaleX, w = this.scaleY,
                    h = this.inverted, e = this.rotation, b = this.matrix, d = this.element;
                h && (a += this.width, g += this.height);
                a = ["translate(" + a + "," + g + ")"];
                u(b) && a.push("matrix(" + b.join(",") + ")");
                h ? a.push("rotate(90) scale(-1,1)") : e && a.push("rotate(" + e + " " + G(this.rotationOriginX, d.getAttribute("x"), 0) + " " + G(this.rotationOriginY, d.getAttribute("y") || 0) + ")");
                (u(c) || u(w)) && a.push("scale(" + G(c, 1) + " " + G(w, 1) + ")");
                a.length && d.setAttribute("transform", a.join(" "))
            },
            toFront: function () {
                var a = this.element;
                a.parentNode.appendChild(a);
                return this
            },
            align: function (a, g, c) {
                var y, w, h, e, d = {};
                w = this.renderer;
                h = w.alignedObjects;
                var b, p;
                if (a) {
                    if (this.alignOptions = a, this.alignByTranslate = g, !c || C(c)) this.alignTo = y = c || "renderer", k(h, this), h.push(this), c = null
                } else a = this.alignOptions, g = this.alignByTranslate, y = this.alignTo;
                c = G(c, w[y], w);
                y = a.align;
                w = a.verticalAlign;
                h = (c.x || 0) + (a.x || 0);
                e = (c.y || 0) + (a.y || 0);
                "right" === y ? b = 1 : "center" === y && (b = 2);
                b && (h += (c.width - (a.width || 0)) / b);
                d[g ? "translateX" : "x"] = Math.round(h);
                "bottom" === w ? p = 1 : "middle" === w && (p = 2);
                p && (e += (c.height -
                    (a.height || 0)) / p);
                d[g ? "translateY" : "y"] = Math.round(e);
                this[this.placed ? "animate" : "attr"](d);
                this.placed = !0;
                this.alignAttr = d;
                return this
            },
            getBBox: function (a, g) {
                var y, w = this.renderer, b, e = this.element, k = this.styles, p, x = this.textStr, N, m = w.cache,
                    A = w.cacheKeys, F;
                g = G(g, this.rotation);
                b = g * c;
                p = k && k.fontSize;
                u(x) && (F = x.toString(), -1 === F.indexOf("\x3c") && (F = F.replace(/[0-9]/g, "0")), F += ["", g || 0, p, k && k.width, k && k.textOverflow].join());
                F && !a && (y = m[F]);
                if (!y) {
                    if (e.namespaceURI === this.SVG_NS || w.forExport) {
                        try {
                            (N =
                                this.fakeTS && function (a) {
                                    d(e.querySelectorAll(".highcharts-text-outline"), function (y) {
                                        y.style.display = a
                                    })
                                }) && N("none"), y = e.getBBox ? h({}, e.getBBox()) : {
                                width: e.offsetWidth,
                                height: e.offsetHeight
                            }, N && N("")
                        } catch (W) {
                        }
                        if (!y || 0 > y.width) y = {width: 0, height: 0}
                    } else y = this.htmlGetBBox();
                    w.isSVG && (a = y.width, w = y.height, k && "11px" === k.fontSize && 17 === Math.round(w) && (y.height = w = 14), g && (y.width = Math.abs(w * Math.sin(b)) + Math.abs(a * Math.cos(b)), y.height = Math.abs(w * Math.cos(b)) + Math.abs(a * Math.sin(b))));
                    if (F && 0 < y.height) {
                        for (; 250 <
                               A.length;) delete m[A.shift()];
                        m[F] || A.push(F);
                        m[F] = y
                    }
                }
                return y
            },
            show: function (a) {
                return this.attr({visibility: a ? "inherit" : "visible"})
            },
            hide: function () {
                return this.attr({visibility: "hidden"})
            },
            fadeOut: function (a) {
                var y = this;
                y.animate({opacity: 0}, {
                    duration: a || 150, complete: function () {
                        y.attr({y: -9999})
                    }
                })
            },
            add: function (a) {
                var y = this.renderer, g = this.element, c;
                a && (this.parentGroup = a);
                this.parentInverted = a && a.inverted;
                void 0 !== this.textStr && y.buildText(this);
                this.added = !0;
                if (!a || a.handleZ || this.zIndex) c =
                    this.zIndexSetter();
                c || (a ? a.element : y.box).appendChild(g);
                if (this.onAdd) this.onAdd();
                return this
            },
            safeRemoveChild: function (a) {
                var y = a.parentNode;
                y && y.removeChild(a)
            },
            destroy: function () {
                var a = this, g = a.element || {}, c = a.renderer.isSVG && "SPAN" === g.nodeName && a.parentGroup,
                    w = g.ownerSVGElement, h = a.clipPath;
                g.onclick = g.onmouseout = g.onmouseover = g.onmousemove = g.point = null;
                L(a);
                h && w && (d(w.querySelectorAll("[clip-path],[CLIP-PATH]"), function (a) {
                    var g = a.getAttribute("clip-path"), y = h.element.id;
                    (-1 < g.indexOf("(#" +
                        y + ")") || -1 < g.indexOf('("#' + y + '")')) && a.removeAttribute("clip-path")
                }), a.clipPath = h.destroy());
                if (a.stops) {
                    for (w = 0; w < a.stops.length; w++) a.stops[w] = a.stops[w].destroy();
                    a.stops = null
                }
                a.safeRemoveChild(g);
                for (a.destroyShadows(); c && c.div && 0 === c.div.childNodes.length;) g = c.parentGroup, a.safeRemoveChild(c.div), delete c.div, c = g;
                a.alignTo && k(a.renderer.alignedObjects, a);
                J(a, function (g, y) {
                    delete a[y]
                });
                return null
            },
            shadow: function (a, g, c) {
                var y = [], w, h, e = this.element, b, d, k, p;
                if (!a) this.destroyShadows(); else if (!this.shadows) {
                    d =
                        G(a.width, 3);
                    k = (a.opacity || .15) / d;
                    p = this.parentInverted ? "(-1,-1)" : "(" + G(a.offsetX, 1) + ", " + G(a.offsetY, 1) + ")";
                    for (w = 1; w <= d; w++) h = e.cloneNode(0), b = 2 * d + 1 - 2 * w, f(h, {
                        isShadow: "true",
                        stroke: a.color || "#000000",
                        "stroke-opacity": k * w,
                        "stroke-width": b,
                        transform: "translate" + p,
                        fill: "none"
                    }), c && (f(h, "height", Math.max(f(h, "height") - b, 0)), h.cutHeight = b), g ? g.element.appendChild(h) : e.parentNode && e.parentNode.insertBefore(h, e), y.push(h);
                    this.shadows = y
                }
                return this
            },
            destroyShadows: function () {
                d(this.shadows || [], function (a) {
                        this.safeRemoveChild(a)
                    },
                    this);
                this.shadows = void 0
            },
            xGetter: function (a) {
                "circle" === this.element.nodeName && ("x" === a ? a = "cx" : "y" === a && (a = "cy"));
                return this._defaultGetter(a)
            },
            _defaultGetter: function (a) {
                a = G(this[a + "Value"], this[a], this.element ? this.element.getAttribute(a) : null, 0);
                /^[\-0-9\.]+$/.test(a) && (a = parseFloat(a));
                return a
            },
            dSetter: function (a, g, c) {
                a && a.join && (a = a.join(" "));
                /(NaN| {2}|^$)/.test(a) && (a = "M 0 0");
                this[g] !== a && (c.setAttribute(g, a), this[g] = a)
            },
            dashstyleSetter: function (a) {
                var y, c = this["stroke-width"];
                "inherit" ===
                c && (c = 1);
                if (a = a && a.toLowerCase()) {
                    a = a.replace("shortdashdotdot", "3,1,1,1,1,1,").replace("shortdashdot", "3,1,1,1").replace("shortdot", "1,1,").replace("shortdash", "3,1,").replace("longdash", "8,3,").replace(/dot/g, "1,3,").replace("dash", "4,3,").replace(/,$/, "").split(",");
                    for (y = a.length; y--;) a[y] = g(a[y]) * c;
                    a = a.join(",").replace(/NaN/g, "none");
                    this.element.setAttribute("stroke-dasharray", a)
                }
            },
            alignSetter: function (a) {
                this.alignValue = a;
                this.element.setAttribute("text-anchor", {
                    left: "start", center: "middle",
                    right: "end"
                }[a])
            },
            opacitySetter: function (a, g, c) {
                this[g] = a;
                c.setAttribute(g, a)
            },
            titleSetter: function (a) {
                var g = this.element.getElementsByTagName("title")[0];
                g || (g = m.createElementNS(this.SVG_NS, "title"), this.element.appendChild(g));
                g.firstChild && g.removeChild(g.firstChild);
                g.appendChild(m.createTextNode(String(G(a), "").replace(/<[^>]*>/g, "").replace(/&lt;/g, "\x3c").replace(/&gt;/g, "\x3e")))
            },
            textSetter: function (a) {
                a !== this.textStr && (delete this.bBox, this.textStr = a, this.added && this.renderer.buildText(this))
            },
            fillSetter: function (a, g, c) {
                "string" === typeof a ? c.setAttribute(g, a) : a && this.colorGradient(a, g, c)
            },
            visibilitySetter: function (a, g, c) {
                "inherit" === a ? c.removeAttribute(g) : this[g] !== a && c.setAttribute(g, a);
                this[g] = a
            },
            zIndexSetter: function (a, c) {
                var w = this.renderer, y = this.parentGroup, h = (y || w).element || w.box, e, b = this.element, d, k,
                    w = h === w.box;
                e = this.added;
                var p;
                u(a) && (b.zIndex = a, a = +a, this[c] === a && (e = !1), this[c] = a);
                if (e) {
                    (a = this.zIndex) && y && (y.handleZ = !0);
                    c = h.childNodes;
                    for (p = c.length - 1; 0 <= p && !d; p--) if (y = c[p],
                            e = y.zIndex, k = !u(e), y !== b) if (0 > a && k && !w && !p) h.insertBefore(b, c[p]), d = !0; else if (g(e) <= a || k && (!u(a) || 0 <= a)) h.insertBefore(b, c[p + 1] || null), d = !0;
                    d || (h.insertBefore(b, c[w ? 3 : 0] || null), d = !0)
                }
                return d
            },
            _defaultSetter: function (a, g, c) {
                c.setAttribute(g, a)
            }
        });
        B.prototype.yGetter = B.prototype.xGetter;
        B.prototype.translateXSetter = B.prototype.translateYSetter = B.prototype.rotationSetter = B.prototype.verticalAlignSetter = B.prototype.rotationOriginXSetter = B.prototype.rotationOriginYSetter = B.prototype.scaleXSetter = B.prototype.scaleYSetter =
            B.prototype.matrixSetter = function (a, g) {
                this[g] = a;
                this.doTransform = !0
            };
        B.prototype["stroke-widthSetter"] = B.prototype.strokeSetter = function (a, g, c) {
            this[g] = a;
            this.stroke && this["stroke-width"] ? (B.prototype.fillSetter.call(this, this.stroke, "stroke", c), c.setAttribute("stroke-width", this["stroke-width"]), this.hasStroke = !0) : "stroke-width" === g && 0 === a && this.hasStroke && (c.removeAttribute("stroke"), this.hasStroke = !1)
        };
        H = a.SVGRenderer = function () {
            this.init.apply(this, arguments)
        };
        h(H.prototype, {
            Element: B, SVG_NS: N,
            init: function (a, g, c, w, h, e) {
                var y;
                w = this.createElement("svg").attr({version: "1.1", "class": "highcharts-root"}).css(this.getStyle(w));
                y = w.element;
                a.appendChild(y);
                f(a, "dir", "ltr");
                -1 === a.innerHTML.indexOf("xmlns") && f(y, "xmlns", this.SVG_NS);
                this.isSVG = !0;
                this.box = y;
                this.boxWrapper = w;
                this.alignedObjects = [];
                this.url = (z || x) && m.getElementsByTagName("base").length ? R.location.href.replace(/#.*?$/, "").replace(/<[^>]*>/g, "").replace(/([\('\)])/g, "\\$1").replace(/ /g, "%20") : "";
                this.createElement("desc").add().element.appendChild(m.createTextNode("Created with Highcharts 6.0.7"));
                this.defs = this.createElement("defs").add();
                this.allowHTML = e;
                this.forExport = h;
                this.gradients = {};
                this.cache = {};
                this.cacheKeys = [];
                this.imgCount = 0;
                this.setSize(g, c, !1);
                var b;
                z && a.getBoundingClientRect && (g = function () {
                    n(a, {left: 0, top: 0});
                    b = a.getBoundingClientRect();
                    n(a, {left: Math.ceil(b.left) - b.left + "px", top: Math.ceil(b.top) - b.top + "px"})
                }, g(), this.unSubPixelFix = E(R, "resize", g))
            }, getStyle: function (a) {
                return this.style = h({
                        fontFamily: '"Lucida Grande", "Lucida Sans Unicode", Arial, Helvetica, sans-serif',
                        fontSize: "12px"
                    },
                    a)
            }, setStyle: function (a) {
                this.boxWrapper.css(this.getStyle(a))
            }, isHidden: function () {
                return !this.boxWrapper.getBBox().width
            }, destroy: function () {
                var a = this.defs;
                this.box = null;
                this.boxWrapper = this.boxWrapper.destroy();
                b(this.gradients || {});
                this.gradients = null;
                a && (this.defs = a.destroy());
                this.unSubPixelFix && this.unSubPixelFix();
                return this.alignedObjects = null
            }, createElement: function (a) {
                var g = new this.Element;
                g.init(this, a);
                return g
            }, draw: A, getRadialAttr: function (a, g) {
                return {
                    cx: a[0] - a[2] / 2 + g.cx * a[2], cy: a[1] -
                    a[2] / 2 + g.cy * a[2], r: g.r * a[2]
                }
            }, getSpanWidth: function (a) {
                return a.getBBox(!0).width
            }, applyEllipsis: function (a, g, c, w) {
                var h = a.rotation, y = c, b, e = 0, d = c.length, k = function (a) {
                    g.removeChild(g.firstChild);
                    a && g.appendChild(m.createTextNode(a))
                }, p;
                a.rotation = 0;
                y = this.getSpanWidth(a, g);
                if (p = y > w) {
                    for (; e <= d;) b = Math.ceil((e + d) / 2), y = c.substring(0, b) + "\u2026", k(y), y = this.getSpanWidth(a, g), e === d ? e = d + 1 : y > w ? d = b - 1 : e = b;
                    0 === d && k("")
                }
                a.rotation = h;
                return p
            }, escapes: {
                "\x26": "\x26amp;", "\x3c": "\x26lt;", "\x3e": "\x26gt;", "'": "\x26#39;",
                '"': "\x26quot;"
            }, buildText: function (a) {
                var c = a.element, w = this, h = w.forExport, b = G(a.textStr, "").toString(),
                    y = -1 !== b.indexOf("\x3c"), k = c.childNodes, p, x, A, F, z = f(c, "x"), L = a.styles,
                    O = a.textWidth, l = L && L.lineHeight, D = L && L.textOutline,
                    C = L && "ellipsis" === L.textOverflow, v = L && "nowrap" === L.whiteSpace, u = L && L.fontSize, M,
                    t, I = k.length, L = O && !a.added && this.box, R = function (a) {
                        var h;
                        h = /(px|em)$/.test(a && a.style.fontSize) ? a.style.fontSize : u || w.style.fontSize || 12;
                        return l ? g(l) : w.fontMetrics(h, a.getAttribute("style") ? a : c).h
                    },
                    q = function (a, g) {
                        J(w.escapes, function (c, w) {
                            g && -1 !== r(c, g) || (a = a.toString().replace(new RegExp(c, "g"), w))
                        });
                        return a
                    };
                M = [b, C, v, l, D, u, O].join();
                if (M !== a.textCache) {
                    for (a.textCache = M; I--;) c.removeChild(k[I]);
                    y || D || C || O || -1 !== b.indexOf(" ") ? (p = /<.*class="([^"]+)".*>/, x = /<.*style="([^"]+)".*>/, A = /<.*href="([^"]+)".*>/, L && L.appendChild(c), b = y ? b.replace(/<(b|strong)>/g, '\x3cspan style\x3d"font-weight:bold"\x3e').replace(/<(i|em)>/g, '\x3cspan style\x3d"font-style:italic"\x3e').replace(/<a/g, "\x3cspan").replace(/<\/(b|strong|i|em|a)>/g,
                        "\x3c/span\x3e").split(/<br.*?>/g) : [b], b = e(b, function (a) {
                        return "" !== a
                    }), d(b, function (g, b) {
                        var e, y = 0;
                        g = g.replace(/^\s+|\s+$/g, "").replace(/<span/g, "|||\x3cspan").replace(/<\/span>/g, "\x3c/span\x3e|||");
                        e = g.split("|||");
                        d(e, function (g) {
                            if ("" !== g || 1 === e.length) {
                                var d = {}, k = m.createElementNS(w.SVG_NS, "tspan"), r, L;
                                p.test(g) && (r = g.match(p)[1], f(k, "class", r));
                                x.test(g) && (L = g.match(x)[1].replace(/(;| |^)color([ :])/, "$1fill$2"), f(k, "style", L));
                                A.test(g) && !h && (f(k, "onclick", 'location.href\x3d"' + g.match(A)[1] +
                                    '"'), f(k, "class", "highcharts-anchor"), n(k, {cursor: "pointer"}));
                                g = q(g.replace(/<[a-zA-Z\/](.|\n)*?>/g, "") || " ");
                                if (" " !== g) {
                                    k.appendChild(m.createTextNode(g));
                                    y ? d.dx = 0 : b && null !== z && (d.x = z);
                                    f(k, d);
                                    c.appendChild(k);
                                    !y && t && (!P && h && n(k, {display: "block"}), f(k, "dy", R(k)));
                                    if (O) {
                                        d = g.replace(/([^\^])-/g, "$1- ").split(" ");
                                        r = 1 < e.length || b || 1 < d.length && !v;
                                        var l = [], D, J = R(k), G = a.rotation;
                                        for (C && (F = w.applyEllipsis(a, k, g, O)); !C && r && (d.length || l.length);) a.rotation = 0, D = w.getSpanWidth(a, k), g = D > O, void 0 === F && (F = g),
                                            g && 1 !== d.length ? (k.removeChild(k.firstChild), l.unshift(d.pop())) : (d = l, l = [], d.length && !v && (k = m.createElementNS(N, "tspan"), f(k, {
                                                dy: J,
                                                x: z
                                            }), L && f(k, "style", L), c.appendChild(k)), D > O && (O = D)), d.length && k.appendChild(m.createTextNode(d.join(" ").replace(/- /g, "-")));
                                        a.rotation = G
                                    }
                                    y++
                                }
                            }
                        });
                        t = t || c.childNodes.length
                    }), F && a.attr("title", q(a.textStr, ["\x26lt;", "\x26gt;"])), L && L.removeChild(c), D && a.applyTextOutline && a.applyTextOutline(D)) : c.appendChild(m.createTextNode(q(b)))
                }
            }, getContrast: function (a) {
                a = t(a).rgba;
                return 510 < a[0] + a[1] + a[2] ? "#000000" : "#FFFFFF"
            }, button: function (a, g, c, w, b, d, e, k, p) {
                var y = this.label(a, g, c, p, null, null, null, null, "button"), x = 0;
                y.attr(F({padding: 8, r: 2}, b));
                var N, m, A, r;
                b = F({
                    fill: "#f7f7f7",
                    stroke: "#cccccc",
                    "stroke-width": 1,
                    style: {color: "#333333", cursor: "pointer", fontWeight: "normal"}
                }, b);
                N = b.style;
                delete b.style;
                d = F(b, {fill: "#e6e6e6"}, d);
                m = d.style;
                delete d.style;
                e = F(b, {fill: "#e6ebf5", style: {color: "#000000", fontWeight: "bold"}}, e);
                A = e.style;
                delete e.style;
                k = F(b, {style: {color: "#cccccc"}},
                    k);
                r = k.style;
                delete k.style;
                E(y.element, M ? "mouseover" : "mouseenter", function () {
                    3 !== x && y.setState(1)
                });
                E(y.element, M ? "mouseout" : "mouseleave", function () {
                    3 !== x && y.setState(x)
                });
                y.setState = function (a) {
                    1 !== a && (y.state = x = a);
                    y.removeClass(/highcharts-button-(normal|hover|pressed|disabled)/).addClass("highcharts-button-" + ["normal", "hover", "pressed", "disabled"][a || 0]);
                    y.attr([b, d, e, k][a || 0]).css([N, m, A, r][a || 0])
                };
                y.attr(b).css(h({cursor: "default"}, N));
                return y.on("click", function (a) {
                    3 !== x && w.call(y, a)
                })
            }, crispLine: function (a,
                                    g) {
                a[1] === a[4] && (a[1] = a[4] = Math.round(a[1]) - g % 2 / 2);
                a[2] === a[5] && (a[2] = a[5] = Math.round(a[2]) + g % 2 / 2);
                return a
            }, path: function (a) {
                var g = {fill: "none"};
                I(a) ? g.d = a : D(a) && h(g, a);
                return this.createElement("path").attr(g)
            }, circle: function (a, g, c) {
                a = D(a) ? a : {x: a, y: g, r: c};
                g = this.createElement("circle");
                g.xSetter = g.ySetter = function (a, g, c) {
                    c.setAttribute("c" + g, a)
                };
                return g.attr(a)
            }, arc: function (a, g, c, w, b, h) {
                D(a) ? (w = a, g = w.y, c = w.r, a = w.x) : w = {innerR: w, start: b, end: h};
                a = this.symbol("arc", a, g, c, c, w);
                a.r = c;
                return a
            }, rect: function (a,
                               g, c, w, b, h) {
                b = D(a) ? a.r : b;
                var d = this.createElement("rect");
                a = D(a) ? a : void 0 === a ? {} : {x: a, y: g, width: Math.max(c, 0), height: Math.max(w, 0)};
                void 0 !== h && (a.strokeWidth = h, a = d.crisp(a));
                a.fill = "none";
                b && (a.r = b);
                d.rSetter = function (a, g, c) {
                    f(c, {rx: a, ry: a})
                };
                return d.attr(a)
            }, setSize: function (a, g, c) {
                var w = this.alignedObjects, b = w.length;
                this.width = a;
                this.height = g;
                for (this.boxWrapper.animate({width: a, height: g}, {
                    step: function () {
                        this.attr({viewBox: "0 0 " + this.attr("width") + " " + this.attr("height")})
                    }, duration: G(c, !0) ?
                        void 0 : 0
                }); b--;) w[b].align()
            }, g: function (a) {
                var g = this.createElement("g");
                return a ? g.attr({"class": "highcharts-" + a}) : g
            }, image: function (a, g, c, w, b) {
                var d = {preserveAspectRatio: "none"};
                1 < arguments.length && h(d, {x: g, y: c, width: w, height: b});
                d = this.createElement("image").attr(d);
                d.element.setAttributeNS ? d.element.setAttributeNS("http://www.w3.org/1999/xlink", "href", a) : d.element.setAttribute("hc-svg-href", a);
                return d
            }, symbol: function (a, g, c, w, b, e) {
                var k = this, p, y = /^url\((.*?)\)$/, x = y.test(a), N = !x && (this.symbols[a] ?
                    a : "circle"), A = N && this.symbols[N],
                    F = u(g) && A && A.call(this.symbols, Math.round(g), Math.round(c), w, b, e), r, L;
                A ? (p = this.path(F), p.attr("fill", "none"), h(p, {
                    symbolName: N,
                    x: g,
                    y: c,
                    width: w,
                    height: b
                }), e && h(p, e)) : x && (r = a.match(y)[1], p = this.image(r), p.imgwidth = G(O[r] && O[r].width, e && e.width), p.imgheight = G(O[r] && O[r].height, e && e.height), L = function () {
                    p.attr({width: p.width, height: p.height})
                }, d(["width", "height"], function (a) {
                    p[a + "Setter"] = function (a, g) {
                        var c = {}, w = this["img" + g], b = "width" === g ? "translateX" : "translateY";
                        this[g] = a;
                        u(w) && (this.element && this.element.setAttribute(g, w), this.alignByTranslate || (c[b] = ((this[g] || 0) - w) / 2, this.attr(c)))
                    }
                }), u(g) && p.attr({
                    x: g,
                    y: c
                }), p.isImg = !0, u(p.imgwidth) && u(p.imgheight) ? L() : (p.attr({width: 0, height: 0}), v("img", {
                    onload: function () {
                        var a = l[k.chartIndex];
                        0 === this.width && (n(this, {position: "absolute", top: "-999em"}), m.body.appendChild(this));
                        O[r] = {width: this.width, height: this.height};
                        p.imgwidth = this.width;
                        p.imgheight = this.height;
                        p.element && L();
                        this.parentNode && this.parentNode.removeChild(this);
                        k.imgCount--;
                        if (!k.imgCount && a && a.onload) a.onload()
                    }, src: r
                }), this.imgCount++));
                return p
            }, symbols: {
                circle: function (a, g, c, w) {
                    return this.arc(a + c / 2, g + w / 2, c / 2, w / 2, {start: 0, end: 2 * Math.PI, open: !1})
                }, square: function (a, g, c, w) {
                    return ["M", a, g, "L", a + c, g, a + c, g + w, a, g + w, "Z"]
                }, triangle: function (a, g, c, w) {
                    return ["M", a + c / 2, g, "L", a + c, g + w, a, g + w, "Z"]
                }, "triangle-down": function (a, g, c, w) {
                    return ["M", a, g, "L", a + c, g, a + c / 2, g + w, "Z"]
                }, diamond: function (a, g, c, w) {
                    return ["M", a + c / 2, g, "L", a + c, g + w / 2, a + c / 2, g + w, a, g + w / 2, "Z"]
                }, arc: function (a,
                                  g, c, w, b) {
                    var h = b.start, d = b.r || c, e = b.r || w || c, k = b.end - .001;
                    c = b.innerR;
                    w = G(b.open, .001 > Math.abs(b.end - b.start - 2 * Math.PI));
                    var p = Math.cos(h), y = Math.sin(h), x = Math.cos(k), k = Math.sin(k);
                    b = .001 > b.end - h - Math.PI ? 0 : 1;
                    d = ["M", a + d * p, g + e * y, "A", d, e, 0, b, 1, a + d * x, g + e * k];
                    u(c) && d.push(w ? "M" : "L", a + c * x, g + c * k, "A", c, c, 0, b, 0, a + c * p, g + c * y);
                    d.push(w ? "" : "Z");
                    return d
                }, callout: function (a, g, c, w, b) {
                    var h = Math.min(b && b.r || 0, c, w), d = h + 6, e = b && b.anchorX;
                    b = b && b.anchorY;
                    var k;
                    k = ["M", a + h, g, "L", a + c - h, g, "C", a + c, g, a + c, g, a + c, g + h, "L", a + c, g + w -
                    h, "C", a + c, g + w, a + c, g + w, a + c - h, g + w, "L", a + h, g + w, "C", a, g + w, a, g + w, a, g + w - h, "L", a, g + h, "C", a, g, a, g, a + h, g];
                    e && e > c ? b > g + d && b < g + w - d ? k.splice(13, 3, "L", a + c, b - 6, a + c + 6, b, a + c, b + 6, a + c, g + w - h) : k.splice(13, 3, "L", a + c, w / 2, e, b, a + c, w / 2, a + c, g + w - h) : e && 0 > e ? b > g + d && b < g + w - d ? k.splice(33, 3, "L", a, b + 6, a - 6, b, a, b - 6, a, g + h) : k.splice(33, 3, "L", a, w / 2, e, b, a, w / 2, a, g + h) : b && b > w && e > a + d && e < a + c - d ? k.splice(23, 3, "L", e + 6, g + w, e, g + w + 6, e - 6, g + w, a + h, g + w) : b && 0 > b && e > a + d && e < a + c - d && k.splice(3, 3, "L", e - 6, g, e, g - 6, e + 6, g, c - h, g);
                    return k
                }
            }, clipRect: function (g, c, w,
                                   b) {
                var h = a.uniqueKey(), d = this.createElement("clipPath").attr({id: h}).add(this.defs);
                g = this.rect(g, c, w, b, 0).add(d);
                g.id = h;
                g.clipPath = d;
                g.count = 0;
                return g
            }, text: function (a, g, c, w) {
                var b = {};
                if (w && (this.allowHTML || !this.forExport)) return this.html(a, g, c);
                b.x = Math.round(g || 0);
                c && (b.y = Math.round(c));
                if (a || 0 === a) b.text = a;
                a = this.createElement("text").attr(b);
                w || (a.xSetter = function (a, g, c) {
                    var w = c.getElementsByTagName("tspan"), b, h = c.getAttribute(g), d;
                    for (d = 0; d < w.length; d++) b = w[d], b.getAttribute(g) === h && b.setAttribute(g,
                        a);
                    c.setAttribute(g, a)
                });
                return a
            }, fontMetrics: function (a, c) {
                a = a || c && c.style && c.style.fontSize || this.style && this.style.fontSize;
                a = /px/.test(a) ? g(a) : /em/.test(a) ? parseFloat(a) * (c ? this.fontMetrics(null, c.parentNode).f : 16) : 12;
                c = 24 > a ? a + 3 : Math.round(1.2 * a);
                return {h: c, b: Math.round(.8 * c), f: a}
            }, rotCorr: function (a, g, w) {
                var b = a;
                g && w && (b = Math.max(b * Math.cos(g * c), 4));
                return {x: -a / 3 * Math.sin(g * c), y: b}
            }, label: function (g, c, b, e, k, p, x, N, m) {
                var A = this, r = A.g("button" !== m && "label"), y = r.text = A.text("", 0, 0, x).attr({zIndex: 1}),
                    L, P, z = 0, O = 3, l = 0, n, D, f, J, C, G = {}, v, M, t = /^url\((.*?)\)$/.test(e), I = t, R, q,
                    Q, T;
                m && r.addClass("highcharts-" + m);
                I = t;
                R = function () {
                    return (v || 0) % 2 / 2
                };
                q = function () {
                    var a = y.element.style, g = {};
                    P = (void 0 === n || void 0 === D || C) && u(y.textStr) && y.getBBox();
                    r.width = (n || P.width || 0) + 2 * O + l;
                    r.height = (D || P.height || 0) + 2 * O;
                    M = O + A.fontMetrics(a && a.fontSize, y).b;
                    I && (L || (r.box = L = A.symbols[e] || t ? A.symbol(e) : A.rect(), L.addClass(("button" === m ? "" : "highcharts-label-box") + (m ? " highcharts-" + m + "-box" : "")), L.add(r), a = R(), g.x = a, g.y = (N ? -M :
                        0) + a), g.width = Math.round(r.width), g.height = Math.round(r.height), L.attr(h(g, G)), G = {})
                };
                Q = function () {
                    var a = l + O, g;
                    g = N ? 0 : M;
                    u(n) && P && ("center" === C || "right" === C) && (a += {center: .5, right: 1}[C] * (n - P.width));
                    if (a !== y.x || g !== y.y) y.attr("x", a), void 0 !== g && y.attr("y", g);
                    y.x = a;
                    y.y = g
                };
                T = function (a, g) {
                    L ? L.attr(a, g) : G[a] = g
                };
                r.onAdd = function () {
                    y.add(r);
                    r.attr({text: g || 0 === g ? g : "", x: c, y: b});
                    L && u(k) && r.attr({anchorX: k, anchorY: p})
                };
                r.widthSetter = function (g) {
                    n = a.isNumber(g) ? g : null
                };
                r.heightSetter = function (a) {
                    D = a
                };
                r["text-alignSetter"] =
                    function (a) {
                        C = a
                    };
                r.paddingSetter = function (a) {
                    u(a) && a !== O && (O = r.padding = a, Q())
                };
                r.paddingLeftSetter = function (a) {
                    u(a) && a !== l && (l = a, Q())
                };
                r.alignSetter = function (a) {
                    a = {left: 0, center: .5, right: 1}[a];
                    a !== z && (z = a, P && r.attr({x: f}))
                };
                r.textSetter = function (a) {
                    void 0 !== a && y.textSetter(a);
                    q();
                    Q()
                };
                r["stroke-widthSetter"] = function (a, g) {
                    a && (I = !0);
                    v = this["stroke-width"] = a;
                    T(g, a)
                };
                r.strokeSetter = r.fillSetter = r.rSetter = function (a, g) {
                    "r" !== g && ("fill" === g && a && (I = !0), r[g] = a);
                    T(g, a)
                };
                r.anchorXSetter = function (a, g) {
                    k = r.anchorX =
                        a;
                    T(g, Math.round(a) - R() - f)
                };
                r.anchorYSetter = function (a, g) {
                    p = r.anchorY = a;
                    T(g, a - J)
                };
                r.xSetter = function (a) {
                    r.x = a;
                    z && (a -= z * ((n || P.width) + 2 * O));
                    f = Math.round(a);
                    r.attr("translateX", f)
                };
                r.ySetter = function (a) {
                    J = r.y = Math.round(a);
                    r.attr("translateY", J)
                };
                var U = r.css;
                return h(r, {
                    css: function (a) {
                        if (a) {
                            var g = {};
                            a = F(a);
                            d(r.textProps, function (c) {
                                void 0 !== a[c] && (g[c] = a[c], delete a[c])
                            });
                            y.css(g)
                        }
                        return U.call(r, a)
                    }, getBBox: function () {
                        return {width: P.width + 2 * O, height: P.height + 2 * O, x: P.x - O, y: P.y - O}
                    }, shadow: function (a) {
                        a &&
                        (q(), L && L.shadow(a));
                        return r
                    }, destroy: function () {
                        w(r.element, "mouseenter");
                        w(r.element, "mouseleave");
                        y && (y = y.destroy());
                        L && (L = L.destroy());
                        B.prototype.destroy.call(r);
                        r = A = q = Q = T = null
                    }
                })
            }
        });
        a.Renderer = H
    })(K);
    (function (a) {
        var B = a.attr, H = a.createElement, E = a.css, q = a.defined, f = a.each, l = a.extend, t = a.isFirefox,
            n = a.isMS, v = a.isWebKit, u = a.pick, c = a.pInt, b = a.SVGRenderer, m = a.win, d = a.wrap;
        l(a.SVGElement.prototype, {
            htmlCss: function (a) {
                var c = this.element;
                if (c = a && "SPAN" === c.tagName && a.width) delete a.width, this.textWidth =
                    c, this.updateTransform();
                a && "ellipsis" === a.textOverflow && (a.whiteSpace = "nowrap", a.overflow = "hidden");
                this.styles = l(this.styles, a);
                E(this.element, a);
                return this
            }, htmlGetBBox: function () {
                var a = this.element;
                return {x: a.offsetLeft, y: a.offsetTop, width: a.offsetWidth, height: a.offsetHeight}
            }, htmlUpdateTransform: function () {
                if (this.added) {
                    var a = this.renderer, b = this.element, d = this.translateX || 0, p = this.translateY || 0,
                        r = this.x || 0, m = this.y || 0, z = this.textAlign || "left",
                        l = {left: 0, center: .5, right: 1}[z], n = this.styles,
                        C = n && n.whiteSpace;
                    E(b, {marginLeft: d, marginTop: p});
                    this.shadows && f(this.shadows, function (a) {
                        E(a, {marginLeft: d + 1, marginTop: p + 1})
                    });
                    this.inverted && f(b.childNodes, function (c) {
                        a.invertChild(c, b)
                    });
                    if ("SPAN" === b.tagName) {
                        var n = this.rotation, x = this.textWidth && c(this.textWidth),
                            F = [n, z, b.innerHTML, this.textWidth, this.textAlign].join(), A;
                        (A = x !== this.oldTextWidth) && !(A = x > this.oldTextWidth) && ((A = this.textPxLength) || (E(b, {
                            width: "",
                            whiteSpace: C || "nowrap"
                        }), A = b.offsetWidth), A = A > x);
                        A && /[ \-]/.test(b.textContent ||
                            b.innerText) && (E(b, {
                            width: x + "px",
                            display: "block",
                            whiteSpace: C || "normal"
                        }), this.oldTextWidth = x);
                        F !== this.cTT && (C = a.fontMetrics(b.style.fontSize).b, q(n) && n !== (this.oldRotation || 0) && this.setSpanRotation(n, l, C), this.getSpanCorrection(this.textPxLength || b.offsetWidth, C, l, n, z));
                        E(b, {left: r + (this.xCorr || 0) + "px", top: m + (this.yCorr || 0) + "px"});
                        this.cTT = F;
                        this.oldRotation = n
                    }
                } else this.alignOnAdd = !0
            }, setSpanRotation: function (a, c, b) {
                var d = {}, e = this.renderer.getTransformKey();
                d[e] = d.transform = "rotate(" + a + "deg)";
                d[e + (t ? "Origin" : "-origin")] = d.transformOrigin = 100 * c + "% " + b + "px";
                E(this.element, d)
            }, getSpanCorrection: function (a, c, b) {
                this.xCorr = -a * b;
                this.yCorr = -c
            }
        });
        l(b.prototype, {
            getTransformKey: function () {
                return n && !/Edge/.test(m.navigator.userAgent) ? "-ms-transform" : v ? "-webkit-transform" : t ? "MozTransform" : m.opera ? "-o-transform" : ""
            }, html: function (a, c, b) {
                var e = this.createElement("span"), h = e.element, k = e.renderer, m = k.isSVG, n = function (a, c) {
                    f(["opacity", "visibility"], function (b) {
                        d(a, b + "Setter", function (a, b, e, d) {
                            a.call(this,
                                b, e, d);
                            c[e] = b
                        })
                    })
                };
                e.textSetter = function (a) {
                    a !== h.innerHTML && delete this.bBox;
                    this.textStr = a;
                    h.innerHTML = u(a, "");
                    e.doTransform = !0
                };
                m && n(e, e.element.style);
                e.xSetter = e.ySetter = e.alignSetter = e.rotationSetter = function (a, c) {
                    "align" === c && (c = "textAlign");
                    e[c] = a;
                    e.doTransform = !0
                };
                e.afterSetters = function () {
                    this.doTransform && (this.htmlUpdateTransform(), this.doTransform = !1)
                };
                e.attr({text: a, x: Math.round(c), y: Math.round(b)}).css({
                    fontFamily: this.style.fontFamily,
                    fontSize: this.style.fontSize,
                    position: "absolute"
                });
                h.style.whiteSpace = "nowrap";
                e.css = e.htmlCss;
                m && (e.add = function (a) {
                    var c, b = k.box.parentNode, d = [];
                    if (this.parentGroup = a) {
                        if (c = a.div, !c) {
                            for (; a;) d.push(a), a = a.parentGroup;
                            f(d.reverse(), function (a) {
                                function h(g, c) {
                                    a[c] = g;
                                    "translateX" === c ? k.left = g + "px" : k.top = g + "px";
                                    a.doTransform = !0
                                }

                                var k, g = B(a.element, "class");
                                g && (g = {className: g});
                                c = a.div = a.div || H("div", g, {
                                        position: "absolute",
                                        left: (a.translateX || 0) + "px",
                                        top: (a.translateY || 0) + "px",
                                        display: a.display,
                                        opacity: a.opacity,
                                        pointerEvents: a.styles && a.styles.pointerEvents
                                    },
                                    c || b);
                                k = c.style;
                                l(a, {
                                    classSetter: function (a) {
                                        return function (g) {
                                            this.element.setAttribute("class", g);
                                            a.className = g
                                        }
                                    }(c), on: function () {
                                        d[0].div && e.on.apply({element: d[0].div}, arguments);
                                        return a
                                    }, translateXSetter: h, translateYSetter: h
                                });
                                n(a, k)
                            })
                        }
                    } else c = b;
                    c.appendChild(h);
                    e.added = !0;
                    e.alignOnAdd && e.htmlUpdateTransform();
                    return e
                });
                return e
            }
        })
    })(K);
    (function (a) {
        var B = a.defined, H = a.each, E = a.extend, q = a.merge, f = a.pick, l = a.timeUnits, t = a.win;
        a.Time = function (a) {
            this.update(a, !1)
        };
        a.Time.prototype = {
            defaultOptions: {},
            update: function (n) {
                var l = f(n && n.useUTC, !0), u = this;
                this.options = n = q(!0, this.options || {}, n);
                this.Date = n.Date || t.Date;
                this.timezoneOffset = (this.useUTC = l) && n.timezoneOffset;
                this.getTimezoneOffset = this.timezoneOffsetFunction();
                (this.variableTimezone = !(l && !n.getTimezoneOffset && !n.timezone)) || this.timezoneOffset ? (this.get = function (a, b) {
                    var c = b.getTime(), d = c - u.getTimezoneOffset(b);
                    b.setTime(d);
                    a = b["getUTC" + a]();
                    b.setTime(c);
                    return a
                }, this.set = function (c, b, m) {
                    var d;
                    if (-1 !== a.inArray(c, ["Milliseconds", "Seconds",
                            "Minutes"])) b["set" + c](m); else d = u.getTimezoneOffset(b), d = b.getTime() - d, b.setTime(d), b["setUTC" + c](m), c = u.getTimezoneOffset(b), d = b.getTime() + c, b.setTime(d)
                }) : l ? (this.get = function (a, b) {
                    return b["getUTC" + a]()
                }, this.set = function (a, b, m) {
                    return b["setUTC" + a](m)
                }) : (this.get = function (a, b) {
                    return b["get" + a]()
                }, this.set = function (a, b, m) {
                    return b["set" + a](m)
                })
            }, makeTime: function (l, v, u, c, b, m) {
                var d, h, k;
                this.useUTC ? (d = this.Date.UTC.apply(0, arguments), h = this.getTimezoneOffset(d), d += h, k = this.getTimezoneOffset(d),
                    h !== k ? d += k - h : h - 36E5 !== this.getTimezoneOffset(d - 36E5) || a.isSafari || (d -= 36E5)) : d = (new this.Date(l, v, f(u, 1), f(c, 0), f(b, 0), f(m, 0))).getTime();
                return d
            }, timezoneOffsetFunction: function () {
                var l = this, f = this.options, u = t.moment;
                if (!this.useUTC) return function (a) {
                    return 6E4 * (new Date(a)).getTimezoneOffset()
                };
                if (f.timezone) {
                    if (u) return function (a) {
                        return 6E4 * -u.tz(a, f.timezone).utcOffset()
                    };
                    a.error(25)
                }
                return this.useUTC && f.getTimezoneOffset ? function (a) {
                    return 6E4 * f.getTimezoneOffset(a)
                } : function () {
                    return 6E4 *
                        (l.timezoneOffset || 0)
                }
            }, dateFormat: function (l, f, u) {
                if (!a.defined(f) || isNaN(f)) return a.defaultOptions.lang.invalidDate || "";
                l = a.pick(l, "%Y-%m-%d %H:%M:%S");
                var c = this, b = new this.Date(f), m = this.get("Hours", b), d = this.get("Day", b),
                    h = this.get("Date", b), k = this.get("Month", b), e = this.get("FullYear", b),
                    p = a.defaultOptions.lang, r = p.weekdays, n = p.shortWeekdays, z = a.pad, b = a.extend({
                        a: n ? n[d] : r[d].substr(0, 3),
                        A: r[d],
                        d: z(h),
                        e: z(h, 2, " "),
                        w: d,
                        b: p.shortMonths[k],
                        B: p.months[k],
                        m: z(k + 1),
                        y: e.toString().substr(2, 2),
                        Y: e,
                        H: z(m),
                        k: m,
                        I: z(m % 12 || 12),
                        l: m % 12 || 12,
                        M: z(c.get("Minutes", b)),
                        p: 12 > m ? "AM" : "PM",
                        P: 12 > m ? "am" : "pm",
                        S: z(b.getSeconds()),
                        L: z(Math.round(f % 1E3), 3)
                    }, a.dateFormats);
                a.objectEach(b, function (a, b) {
                    for (; -1 !== l.indexOf("%" + b);) l = l.replace("%" + b, "function" === typeof a ? a.call(c, f) : a)
                });
                return u ? l.substr(0, 1).toUpperCase() + l.substr(1) : l
            }, getTimeTicks: function (a, v, u, c) {
                var b = this, m = [], d = {}, h, k = new b.Date(v), e = a.unitRange, p = a.count || 1, r;
                if (B(v)) {
                    b.set("Milliseconds", k, e >= l.second ? 0 : p * Math.floor(b.get("Milliseconds", k) / p));
                    e >=
                    l.second && b.set("Seconds", k, e >= l.minute ? 0 : p * Math.floor(b.get("Seconds", k) / p));
                    e >= l.minute && b.set("Minutes", k, e >= l.hour ? 0 : p * Math.floor(b.get("Minutes", k) / p));
                    e >= l.hour && b.set("Hours", k, e >= l.day ? 0 : p * Math.floor(b.get("Hours", k) / p));
                    e >= l.day && b.set("Date", k, e >= l.month ? 1 : p * Math.floor(b.get("Date", k) / p));
                    e >= l.month && (b.set("Month", k, e >= l.year ? 0 : p * Math.floor(b.get("Month", k) / p)), h = b.get("FullYear", k));
                    e >= l.year && b.set("FullYear", k, h - h % p);
                    e === l.week && b.set("Date", k, b.get("Date", k) - b.get("Day", k) + f(c, 1));
                    h = b.get("FullYear", k);
                    c = b.get("Month", k);
                    var n = b.get("Date", k), z = b.get("Hours", k);
                    v = k.getTime();
                    b.variableTimezone && (r = u - v > 4 * l.month || b.getTimezoneOffset(v) !== b.getTimezoneOffset(u));
                    k = k.getTime();
                    for (v = 1; k < u;) m.push(k), k = e === l.year ? b.makeTime(h + v * p, 0) : e === l.month ? b.makeTime(h, c + v * p) : !r || e !== l.day && e !== l.week ? r && e === l.hour && 1 < p ? b.makeTime(h, c, n, z + v * p) : k + e * p : b.makeTime(h, c, n + v * p * (e === l.day ? 1 : 7)), v++;
                    m.push(k);
                    e <= l.hour && 1E4 > m.length && H(m, function (a) {
                        0 === a % 18E5 && "000000000" === b.dateFormat("%H%M%S%L",
                            a) && (d[a] = "day")
                    })
                }
                m.info = E(a, {higherRanks: d, totalRange: e * p});
                return m
            }
        }
    })(K);
    (function (a) {
        var B = a.color, H = a.merge;
        a.defaultOptions = {
            colors: "#7cb5ec #434348 #90ed7d #f7a35c #8085e9 #f15c80 #e4d354 #2b908f #f45b5b #91e8e1".split(" "),
            symbols: ["circle", "diamond", "square", "triangle", "triangle-down"],
            lang: {
                loading: "Loading...",
                months: "January February March April May June July August September October November December".split(" "),
                shortMonths: "Jan Feb Mar Apr May Jun Jul Aug Sep Oct Nov Dec".split(" "),
                weekdays: "Sunday Monday Tuesday Wednesday Thursday Friday Saturday".split(" "),
                decimalPoint: ".",
                numericSymbols: "kMGTPE".split(""),
                resetZoom: "Reset zoom",
                resetZoomTitle: "Reset zoom level 1:1",
                thousandsSep: " "
            },
            global: {},
            time: a.Time.prototype.defaultOptions,
            chart: {
                borderRadius: 0,
                defaultSeriesType: "line",
                ignoreHiddenSeries: !0,
                spacing: [10, 10, 15, 10],
                resetZoomButton: {theme: {zIndex: 6}, position: {align: "right", x: -10, y: 10}},
                width: null,
                height: null,
                borderColor: "#335cad",
                backgroundColor: "#ffffff",
                plotBorderColor: "#cccccc"
            },
            title: {text: "Chart title", align: "center", margin: 15, widthAdjust: -44},
            subtitle: {text: "", align: "center", widthAdjust: -44},
            plotOptions: {},
            labels: {style: {position: "absolute", color: "#333333"}},
            legend: {
                enabled: !0,
                align: "center",
                layout: "horizontal",
                labelFormatter: function () {
                    return this.name
                },
                borderColor: "#999999",
                borderRadius: 0,
                navigation: {activeColor: "#003399", inactiveColor: "#cccccc"},
                itemStyle: {color: "#333333", fontSize: "12px", fontWeight: "bold", textOverflow: "ellipsis"},
                itemHoverStyle: {color: "#000000"},
                itemHiddenStyle: {color: "#cccccc"},
                shadow: !1,
                itemCheckboxStyle: {position: "absolute", width: "13px", height: "13px"},
                squareSymbol: !0,
                symbolPadding: 5,
                verticalAlign: "bottom",
                x: 0,
                y: 0,
                title: {style: {fontWeight: "bold"}}
            },
            loading: {
                labelStyle: {fontWeight: "bold", position: "relative", top: "45%"},
                style: {position: "absolute", backgroundColor: "#ffffff", opacity: .5, textAlign: "center"}
            },
            tooltip: {
                enabled: !0,
                animation: a.svg,
                borderRadius: 3,
                dateTimeLabelFormats: {
                    millisecond: "%A, %b %e, %H:%M:%S.%L",
                    second: "%A, %b %e, %H:%M:%S",
                    minute: "%A, %b %e, %H:%M",
                    hour: "%A, %b %e, %H:%M",
                    day: "%A, %b %e, %Y",
                    week: "Week from %A, %b %e, %Y",
                    month: "%B %Y",
                    year: "%Y"
                },
                footerFormat: "",
                padding: 8,
                snap: a.isTouchDevice ? 25 : 10,
                backgroundColor: B("#f7f7f7").setOpacity(.85).get(),
                borderWidth: 1,
                headerFormat: '\x3cspan style\x3d"font-size: 10px"\x3e{point.key}\x3c/span\x3e\x3cbr/\x3e',
                pointFormat: '\x3cspan style\x3d"color:{point.color}"\x3e\u25cf\x3c/span\x3e {series.name}: \x3cb\x3e{point.y}\x3c/b\x3e\x3cbr/\x3e',
                shadow: !0,
                style: {
                    color: "#333333", cursor: "default", fontSize: "12px", pointerEvents: "none",
                    whiteSpace: "nowrap"
                }
            },
        };
        a.setOptions = function (B) {
            a.defaultOptions = H(!0, a.defaultOptions, B);
            a.time.update(H(a.defaultOptions.global, a.defaultOptions.time), !1);
            return a.defaultOptions
        };
        a.getOptions = function () {
            return a.defaultOptions
        };
        a.defaultPlotOptions = a.defaultOptions.plotOptions;
        a.time = new a.Time(H(a.defaultOptions.global,
            a.defaultOptions.time));
        a.dateFormat = function (B, q, f) {
            return a.time.dateFormat(B, q, f)
        }
    })(K);
    (function (a) {
        var B = a.correctFloat, H = a.defined, E = a.destroyObjectProperties, q = a.isNumber, f = a.merge, l = a.pick,
            t = a.deg2rad;
        a.Tick = function (a, l, f, c) {
            this.axis = a;
            this.pos = l;
            this.type = f || "";
            this.isNewLabel = this.isNew = !0;
            f || c || this.addLabel()
        };
        a.Tick.prototype = {
            addLabel: function () {
                var a = this.axis, v = a.options, u = a.chart, c = a.categories, b = a.names, m = this.pos,
                    d = v.labels, h = a.tickPositions, k = m === h[0], e = m === h[h.length - 1], b = c ?
                    l(c[m], b[m], m) : m, c = this.label, h = h.info, p;
                a.isDatetimeAxis && h && (p = v.dateTimeLabelFormats[h.higherRanks[m] || h.unitName]);
                this.isFirst = k;
                this.isLast = e;
                v = a.labelFormatter.call({
                    axis: a,
                    chart: u,
                    isFirst: k,
                    isLast: e,
                    dateTimeLabelFormat: p,
                    value: a.isLog ? B(a.lin2log(b)) : b,
                    pos: m
                });
                if (H(c)) c && c.attr({text: v}); else {
                    if (this.label = c = H(v) && d.enabled ? u.renderer.text(v, 0, 0, d.useHTML).css(f(d.style)).add(a.labelGroup) : null) c.textPxLength = c.getBBox().width;
                    this.rotation = 0
                }
            }, getLabelSize: function () {
                return this.label ? this.label.getBBox()[this.axis.horiz ?
                    "height" : "width"] : 0
            }, handleOverflow: function (a) {
                var f = this.axis, n = f.options.labels, c = a.x, b = f.chart.chartWidth, m = f.chart.spacing,
                    d = l(f.labelLeft, Math.min(f.pos, m[3])),
                    m = l(f.labelRight, Math.max(f.isRadial ? 0 : f.pos + f.len, b - m[1])), h = this.label,
                    k = this.rotation, e = {left: 0, center: .5, right: 1}[f.labelAlign || h.attr("align")],
                    p = h.getBBox().width, r = f.getSlotWidth(), I = r, z = 1, M, D = {};
                if (k || !1 === n.overflow) 0 > k && c - e * p < d ? M = Math.round(c / Math.cos(k * t) - d) : 0 < k && c + e * p > m && (M = Math.round((b - c) / Math.cos(k * t))); else if (b = c + (1 -
                        e) * p, c - e * p < d ? I = a.x + I * (1 - e) - d : b > m && (I = m - a.x + I * e, z = -1), I = Math.min(r, I), I < r && "center" === f.labelAlign && (a.x += z * (r - I - e * (r - Math.min(p, I)))), p > I || f.autoRotation && (h.styles || {}).width) M = I;
                M && (D.width = M, (n.style || {}).textOverflow || (D.textOverflow = "ellipsis"), h.css(D))
            }, getPosition: function (l, f, u, c) {
                var b = this.axis, m = b.chart, d = c && m.oldChartHeight || m.chartHeight;
                return {
                    x: l ? a.correctFloat(b.translate(f + u, null, null, c) + b.transB) : b.left + b.offset + (b.opposite ? (c && m.oldChartWidth || m.chartWidth) - b.right - b.left : 0),
                    y: l ?
                        d - b.bottom + b.offset - (b.opposite ? b.height : 0) : a.correctFloat(d - b.translate(f + u, null, null, c) - b.transB)
                }
            }, getLabelPosition: function (a, l, f, c, b, m, d, h) {
                var k = this.axis, e = k.transA, p = k.reversed, r = k.staggerLines, n = k.tickRotCorr || {x: 0, y: 0},
                    z = b.y, u = c || k.reserveSpaceDefault ? 0 : -k.labelOffset * ("center" === k.labelAlign ? .5 : 1);
                H(z) || (z = 0 === k.side ? f.rotation ? -8 : -f.getBBox().height : 2 === k.side ? n.y + 8 : Math.cos(f.rotation * t) * (n.y - f.getBBox(!1, 0).height / 2));
                a = a + b.x + u + n.x - (m && c ? m * e * (p ? -1 : 1) : 0);
                l = l + z - (m && !c ? m * e * (p ? 1 : -1) : 0);
                r && (f = d / (h || 1) % r, k.opposite && (f = r - f - 1), l += k.labelOffset / r * f);
                return {x: a, y: Math.round(l)}
            }, getMarkPath: function (a, l, f, c, b, m) {
                return m.crispLine(["M", a, l, "L", a + (b ? 0 : -f), l + (b ? f : 0)], c)
            }, renderGridLine: function (a, l, f) {
                var c = this.axis, b = c.options, m = this.gridLine, d = {}, h = this.pos, k = this.type,
                    e = c.tickmarkOffset, p = c.chart.renderer, r = k ? k + "Grid" : "grid", n = b[r + "LineWidth"],
                    z = b[r + "LineColor"], b = b[r + "LineDashStyle"];
                m || (d.stroke = z, d["stroke-width"] = n, b && (d.dashstyle = b), k || (d.zIndex = 1), a && (d.opacity = 0), this.gridLine =
                    m = p.path().attr(d).addClass("highcharts-" + (k ? k + "-" : "") + "grid-line").add(c.gridGroup));
                if (!a && m && (a = c.getPlotLinePath(h + e, m.strokeWidth() * f, a, !0))) m[this.isNew ? "attr" : "animate"]({
                    d: a,
                    opacity: l
                })
            }, renderMark: function (a, f, u) {
                var c = this.axis, b = c.options, m = c.chart.renderer, d = this.type, h = d ? d + "Tick" : "tick",
                    k = c.tickSize(h), e = this.mark, p = !e, r = a.x;
                a = a.y;
                var n = l(b[h + "Width"], !d && c.isXAxis ? 1 : 0), b = b[h + "Color"];
                k && (c.opposite && (k[0] = -k[0]), p && (this.mark = e = m.path().addClass("highcharts-" + (d ? d + "-" : "") + "tick").add(c.axisGroup),
                    e.attr({
                        stroke: b,
                        "stroke-width": n
                    })), e[p ? "attr" : "animate"]({
                    d: this.getMarkPath(r, a, k[0], e.strokeWidth() * u, c.horiz, m),
                    opacity: f
                }))
            }, renderLabel: function (a, f, u, c) {
                var b = this.axis, m = b.horiz, d = b.options, h = this.label, k = d.labels, e = k.step,
                    b = b.tickmarkOffset, p = !0, r = a.x;
                a = a.y;
                h && q(r) && (h.xy = a = this.getLabelPosition(r, a, h, m, k, b, c, e), this.isFirst && !this.isLast && !l(d.showFirstLabel, 1) || this.isLast && !this.isFirst && !l(d.showLastLabel, 1) ? p = !1 : !m || k.step || k.rotation || f || 0 === u || this.handleOverflow(a), e && c % e && (p = !1),
                    p && q(a.y) ? (a.opacity = u, h[this.isNewLabel ? "attr" : "animate"](a), this.isNewLabel = !1) : (h.attr("y", -9999), this.isNewLabel = !0))
            }, render: function (f, t, u) {
                var c = this.axis, b = c.horiz, m = this.getPosition(b, this.pos, c.tickmarkOffset, t), d = m.x,
                    h = m.y, c = b && d === c.pos + c.len || !b && h === c.pos ? -1 : 1;
                u = l(u, 1);
                this.isActive = !0;
                this.renderGridLine(t, u, c);
                this.renderMark(m, u, c);
                this.renderLabel(m, t, u, f);
                this.isNew = !1;
                a.fireEvent(this, "afterRender")
            }, destroy: function () {
                E(this, this.axis)
            }
        }
    })(K);
    var V = function (a) {
        var B = a.addEvent,
            H = a.animObject, E = a.arrayMax, q = a.arrayMin, f = a.color, l = a.correctFloat, t = a.defaultOptions,
            n = a.defined, v = a.deg2rad, u = a.destroyObjectProperties, c = a.each, b = a.extend, m = a.fireEvent,
            d = a.format, h = a.getMagnitude, k = a.grep, e = a.inArray, p = a.isArray, r = a.isNumber, I = a.isString,
            z = a.merge, M = a.normalizeTickInterval, D = a.objectEach, C = a.pick, x = a.removeEvent, F = a.splat,
            A = a.syncTimeout, J = a.Tick, G = function () {
                this.init.apply(this, arguments)
            };
        a.extend(G.prototype, {
            defaultOptions: {
                dateTimeLabelFormats: {
                    millisecond: "%H:%M:%S.%L",
                    second: "%H:%M:%S",
                    minute: "%H:%M",
                    hour: "%H:%M",
                    day: "%e. %b",
                    week: "%e. %b",
                    month: "%b '%y",
                    year: "%Y"
                },
                endOnTick: !1,
                labels: {enabled: !0, style: {color: "#666666", cursor: "default", fontSize: "11px"}, x: 0},
                maxPadding: .01,
                minorTickLength: 2,
                minorTickPosition: "outside",
                minPadding: .01,
                startOfWeek: 1,
                startOnTick: !1,
                tickLength: 10,
                tickmarkPlacement: "between",
                tickPixelInterval: 100,
                tickPosition: "outside",
                title: {align: "middle", style: {color: "#666666"}},
                type: "linear",
                minorGridLineColor: "#f2f2f2",
                minorGridLineWidth: 1,
                minorTickColor: "#999999",
                lineColor: "#ccd6eb",
                lineWidth: 1,
                gridLineColor: "#e6e6e6",
                tickColor: "#ccd6eb"
            },
            defaultYAxisOptions: {
                endOnTick: !0,
                tickPixelInterval: 72,
                showLastLabel: !0,
                labels: {x: -8},
                maxPadding: .05,
                minPadding: .05,
                startOnTick: !0,
                title: {rotation: 270, text: "Values"},
                stackLabels: {
                    allowOverlap: !1, enabled: !1, formatter: function () {
                        return a.numberFormat(this.total, -1)
                    }, style: {fontSize: "11px", fontWeight: "bold", color: "#000000", textOutline: "1px contrast"}
                },
                gridLineWidth: 1,
                lineWidth: 0
            },
            defaultLeftAxisOptions: {labels: {x: -15}, title: {rotation: 270}},
            defaultRightAxisOptions: {labels: {x: 15}, title: {rotation: 90}},
            defaultBottomAxisOptions: {labels: {autoRotation: [-45], x: 0}, title: {rotation: 0}},
            defaultTopAxisOptions: {labels: {autoRotation: [-45], x: 0}, title: {rotation: 0}},
            init: function (a, c) {
                var g = c.isX, b = this;
                b.chart = a;
                b.horiz = a.inverted && !b.isZAxis ? !g : g;
                b.isXAxis = g;
                b.coll = b.coll || (g ? "xAxis" : "yAxis");
                b.opposite = c.opposite;
                b.side = c.side || (b.horiz ? b.opposite ? 0 : 2 : b.opposite ? 1 : 3);
                b.setOptions(c);
                var w = this.options, d = w.type;
                b.labelFormatter = w.labels.formatter ||
                    b.defaultLabelFormatter;
                b.userOptions = c;
                b.minPixelPadding = 0;
                b.reversed = w.reversed;
                b.visible = !1 !== w.visible;
                b.zoomEnabled = !1 !== w.zoomEnabled;
                b.hasNames = "category" === d || !0 === w.categories;
                b.categories = w.categories || b.hasNames;
                b.names || (b.names = [], b.names.keys = {});
                b.plotLinesAndBandsGroups = {};
                b.isLog = "logarithmic" === d;
                b.isDatetimeAxis = "datetime" === d;
                b.positiveValuesOnly = b.isLog && !b.allowNegativeLog;
                b.isLinked = n(w.linkedTo);
                b.ticks = {};
                b.labelEdge = [];
                b.minorTicks = {};
                b.plotLinesAndBands = [];
                b.alternateBands =
                    {};
                b.len = 0;
                b.minRange = b.userMinRange = w.minRange || w.maxZoom;
                b.range = w.range;
                b.offset = w.offset || 0;
                b.stacks = {};
                b.oldStacks = {};
                b.stacksTouched = 0;
                b.max = null;
                b.min = null;
                b.crosshair = C(w.crosshair, F(a.options.tooltip.crosshairs)[g ? 0 : 1], !1);
                c = b.options.events;
                -1 === e(b, a.axes) && (g ? a.axes.splice(a.xAxis.length, 0, b) : a.axes.push(b), a[b.coll].push(b));
                b.series = b.series || [];
                a.inverted && !b.isZAxis && g && void 0 === b.reversed && (b.reversed = !0);
                D(c, function (a, g) {
                    B(b, g, a)
                });
                b.lin2log = w.linearToLogConverter || b.lin2log;
                b.isLog &&
                (b.val2lin = b.log2lin, b.lin2val = b.lin2log)
            },
            setOptions: function (a) {
                this.options = z(this.defaultOptions, "yAxis" === this.coll && this.defaultYAxisOptions, [this.defaultTopAxisOptions, this.defaultRightAxisOptions, this.defaultBottomAxisOptions, this.defaultLeftAxisOptions][this.side], z(t[this.coll], a))
            },
            defaultLabelFormatter: function () {
                var g = this.axis, c = this.value, b = g.chart.time, e = g.categories, h = this.dateTimeLabelFormat,
                    k = t.lang, p = k.numericSymbols, k = k.numericSymbolMagnitude || 1E3, x = p && p.length, r,
                    m = g.options.labels.format,
                    g = g.isLog ? Math.abs(c) : g.tickInterval;
                if (m) r = d(m, this, b); else if (e) r = c; else if (h) r = b.dateFormat(h, c); else if (x && 1E3 <= g) for (; x-- && void 0 === r;) b = Math.pow(k, x + 1), g >= b && 0 === 10 * c % b && null !== p[x] && 0 !== c && (r = a.numberFormat(c / b, -1) + p[x]);
                void 0 === r && (r = 1E4 <= Math.abs(c) ? a.numberFormat(c, -1) : a.numberFormat(c, -1, void 0, ""));
                return r
            },
            getSeriesExtremes: function () {
                var a = this, b = a.chart;
                a.hasVisibleSeries = !1;
                a.dataMin = a.dataMax = a.threshold = null;
                a.softThreshold = !a.isXAxis;
                a.buildStacks && a.buildStacks();
                c(a.series,
                    function (g) {
                        if (g.visible || !b.options.chart.ignoreHiddenSeries) {
                            var c = g.options, w = c.threshold, d;
                            a.hasVisibleSeries = !0;
                            a.positiveValuesOnly && 0 >= w && (w = null);
                            if (a.isXAxis) c = g.xData, c.length && (g = q(c), d = E(c), r(g) || g instanceof Date || (c = k(c, r), g = q(c), d = E(c)), c.length && (a.dataMin = Math.min(C(a.dataMin, c[0], g), g), a.dataMax = Math.max(C(a.dataMax, c[0], d), d))); else if (g.getExtremes(), d = g.dataMax, g = g.dataMin, n(g) && n(d) && (a.dataMin = Math.min(C(a.dataMin, g), g), a.dataMax = Math.max(C(a.dataMax, d), d)), n(w) && (a.threshold =
                                    w), !c.softThreshold || a.positiveValuesOnly) a.softThreshold = !1
                        }
                    })
            },
            translate: function (a, c, b, d, e, h) {
                var g = this.linkedParent || this, w = 1, k = 0, p = d ? g.oldTransA : g.transA;
                d = d ? g.oldMin : g.min;
                var x = g.minPixelPadding;
                e = (g.isOrdinal || g.isBroken || g.isLog && e) && g.lin2val;
                p || (p = g.transA);
                b && (w *= -1, k = g.len);
                g.reversed && (w *= -1, k -= w * (g.sector || g.len));
                c ? (a = (a * w + k - x) / p + d, e && (a = g.lin2val(a))) : (e && (a = g.val2lin(a)), a = r(d) ? w * (a - d) * p + k + w * x + (r(h) ? p * h : 0) : void 0);
                return a
            },
            toPixels: function (a, c) {
                return this.translate(a, !1, !this.horiz,
                    null, !0) + (c ? 0 : this.pos)
            },
            toValue: function (a, c) {
                return this.translate(a - (c ? 0 : this.pos), !0, !this.horiz, null, !0)
            },
            getPlotLinePath: function (a, c, b, d, e) {
                var g = this.chart, w = this.left, h = this.top, k, p, x = b && g.oldChartHeight || g.chartHeight,
                    m = b && g.oldChartWidth || g.chartWidth, N;
                k = this.transB;
                var A = function (a, g, c) {
                    if (a < g || a > c) d ? a = Math.min(Math.max(g, a), c) : N = !0;
                    return a
                };
                e = C(e, this.translate(a, null, null, b));
                e = Math.min(Math.max(-1E5, e), 1E5);
                a = b = Math.round(e + k);
                k = p = Math.round(x - e - k);
                r(e) ? this.horiz ? (k = h, p = x - this.bottom,
                    a = b = A(a, w, w + this.width)) : (a = w, b = m - this.right, k = p = A(k, h, h + this.height)) : (N = !0, d = !1);
                return N && !d ? null : g.renderer.crispLine(["M", a, k, "L", b, p], c || 1)
            },
            getLinearTickPositions: function (a, c, b) {
                var g, w = l(Math.floor(c / a) * a);
                b = l(Math.ceil(b / a) * a);
                var d = [], e;
                l(w + a) === w && (e = 20);
                if (this.single) return [c];
                for (c = w; c <= b;) {
                    d.push(c);
                    c = l(c + a, e);
                    if (c === g) break;
                    g = c
                }
                return d
            },
            getMinorTickInterval: function () {
                var a = this.options;
                return !0 === a.minorTicks ? C(a.minorTickInterval, "auto") : !1 === a.minorTicks ? null : a.minorTickInterval
            },
            getMinorTickPositions: function () {
                var a = this, b = a.options, d = a.tickPositions, e = a.minorTickInterval, h = [],
                    k = a.pointRangePadding || 0, p = a.min - k, k = a.max + k, x = k - p;
                if (x && x / e < a.len / 3) if (a.isLog) c(this.paddedTicks, function (g, c, b) {
                    c && h.push.apply(h, a.getLogTickPositions(e, b[c - 1], b[c], !0))
                }); else if (a.isDatetimeAxis && "auto" === this.getMinorTickInterval()) h = h.concat(a.getTimeTicks(a.normalizeTimeTickInterval(e), p, k, b.startOfWeek)); else for (b = p + (d[0] - p) % e; b <= k && b !== h[0]; b += e) h.push(b);
                0 !== h.length && a.trimTicks(h);
                return h
            },
            adjustForMinRange: function () {
                var a = this.options, b = this.min, d = this.max, e, h, k, p, x, r, m, A;
                this.isXAxis && void 0 === this.minRange && !this.isLog && (n(a.min) || n(a.max) ? this.minRange = null : (c(this.series, function (a) {
                    r = a.xData;
                    for (p = m = a.xIncrement ? 1 : r.length - 1; 0 < p; p--) if (x = r[p] - r[p - 1], void 0 === k || x < k) k = x
                }), this.minRange = Math.min(5 * k, this.dataMax - this.dataMin)));
                d - b < this.minRange && (h = this.dataMax - this.dataMin >= this.minRange, A = this.minRange, e = (A - d + b) / 2, e = [b - e, C(a.min, b - e)], h && (e[2] = this.isLog ? this.log2lin(this.dataMin) :
                    this.dataMin), b = E(e), d = [b + A, C(a.max, b + A)], h && (d[2] = this.isLog ? this.log2lin(this.dataMax) : this.dataMax), d = q(d), d - b < A && (e[0] = d - A, e[1] = C(a.min, d - A), b = E(e)));
                this.min = b;
                this.max = d
            },
            getClosest: function () {
                var a;
                this.categories ? a = 1 : c(this.series, function (g) {
                    var c = g.closestPointRange, b = g.visible || !g.chart.options.chart.ignoreHiddenSeries;
                    !g.noSharedTooltip && n(c) && b && (a = n(a) ? Math.min(a, c) : c)
                });
                return a
            },
            nameToX: function (a) {
                var g = p(this.categories), c = g ? this.categories : this.names, b = a.options.x, d;
                a.series.requireSorting =
                    !1;
                n(b) || (b = !1 === this.options.uniqueNames ? a.series.autoIncrement() : g ? e(a.name, c) : C(c.keys[a.name], -1));
                -1 === b ? g || (d = c.length) : d = b;
                void 0 !== d && (this.names[d] = a.name, this.names.keys[a.name] = d);
                return d
            },
            updateNames: function () {
                var g = this, b = this.names;
                0 < b.length && (c(a.keys(b.keys), function (a) {
                    delete b.keys[a]
                }), b.length = 0, this.minRange = this.userMinRange, c(this.series || [], function (a) {
                    a.xIncrement = null;
                    if (!a.points || a.isDirtyData) a.processData(), a.generatePoints();
                    c(a.points, function (c, b) {
                        var d;
                        c.options &&
                        (d = g.nameToX(c), void 0 !== d && d !== c.x && (c.x = d, a.xData[b] = d))
                    })
                }))
            },
            setAxisTranslation: function (a) {
                var g = this, b = g.max - g.min, d = g.axisPointRange || 0, e, h = 0, k = 0, p = g.linkedParent,
                    x = !!g.categories, r = g.transA, m = g.isXAxis;
                if (m || x || d) e = g.getClosest(), p ? (h = p.minPointOffset, k = p.pointRangePadding) : c(g.series, function (a) {
                    var c = x ? 1 : m ? C(a.options.pointRange, e, 0) : g.axisPointRange || 0;
                    a = a.options.pointPlacement;
                    d = Math.max(d, c);
                    g.single || (h = Math.max(h, I(a) ? 0 : c / 2), k = Math.max(k, "on" === a ? 0 : c))
                }), p = g.ordinalSlope && e ? g.ordinalSlope /
                    e : 1, g.minPointOffset = h *= p, g.pointRangePadding = k *= p, g.pointRange = Math.min(d, b), m && (g.closestPointRange = e);
                a && (g.oldTransA = r);
                g.translationSlope = g.transA = r = g.options.staticScale || g.len / (b + k || 1);
                g.transB = g.horiz ? g.left : g.bottom;
                g.minPixelPadding = r * h
            },
            minFromRange: function () {
                return this.max - this.range
            },
            setTickInterval: function (g) {
                var b = this, d = b.chart, e = b.options, k = b.isLog, p = b.log2lin, x = b.isDatetimeAxis,
                    A = b.isXAxis, F = b.isLinked, f = e.maxPadding, z = e.minPadding, D = e.tickInterval,
                    J = e.tickPixelInterval, G = b.categories,
                    u = b.threshold, t = b.softThreshold, v, q, I, B;
                x || G || F || this.getTickAmount();
                I = C(b.userMin, e.min);
                B = C(b.userMax, e.max);
                F ? (b.linkedParent = d[b.coll][e.linkedTo], d = b.linkedParent.getExtremes(), b.min = C(d.min, d.dataMin), b.max = C(d.max, d.dataMax), e.type !== b.linkedParent.options.type && a.error(11, 1)) : (!t && n(u) && (b.dataMin >= u ? (v = u, z = 0) : b.dataMax <= u && (q = u, f = 0)), b.min = C(I, v, b.dataMin), b.max = C(B, q, b.dataMax));
                k && (b.positiveValuesOnly && !g && 0 >= Math.min(b.min, C(b.dataMin, b.min)) && a.error(10, 1), b.min = l(p(b.min), 15), b.max =
                    l(p(b.max), 15));
                b.range && n(b.max) && (b.userMin = b.min = I = Math.max(b.dataMin, b.minFromRange()), b.userMax = B = b.max, b.range = null);
                m(b, "foundExtremes");
                b.beforePadding && b.beforePadding();
                b.adjustForMinRange();
                !(G || b.axisPointRange || b.usePercentage || F) && n(b.min) && n(b.max) && (p = b.max - b.min) && (!n(I) && z && (b.min -= p * z), !n(B) && f && (b.max += p * f));
                r(e.softMin) && !r(b.userMin) && (b.min = Math.min(b.min, e.softMin));
                r(e.softMax) && !r(b.userMax) && (b.max = Math.max(b.max, e.softMax));
                r(e.floor) && (b.min = Math.max(b.min, e.floor));
                r(e.ceiling) &&
                (b.max = Math.min(b.max, e.ceiling));
                t && n(b.dataMin) && (u = u || 0, !n(I) && b.min < u && b.dataMin >= u ? b.min = u : !n(B) && b.max > u && b.dataMax <= u && (b.max = u));
                b.tickInterval = b.min === b.max || void 0 === b.min || void 0 === b.max ? 1 : F && !D && J === b.linkedParent.options.tickPixelInterval ? D = b.linkedParent.tickInterval : C(D, this.tickAmount ? (b.max - b.min) / Math.max(this.tickAmount - 1, 1) : void 0, G ? 1 : (b.max - b.min) * J / Math.max(b.len, J));
                A && !g && c(b.series, function (a) {
                    a.processData(b.min !== b.oldMin || b.max !== b.oldMax)
                });
                b.setAxisTranslation(!0);
                b.beforeSetTickPositions &&
                b.beforeSetTickPositions();
                b.postProcessTickInterval && (b.tickInterval = b.postProcessTickInterval(b.tickInterval));
                b.pointRange && !D && (b.tickInterval = Math.max(b.pointRange, b.tickInterval));
                g = C(e.minTickInterval, b.isDatetimeAxis && b.closestPointRange);
                !D && b.tickInterval < g && (b.tickInterval = g);
                x || k || D || (b.tickInterval = M(b.tickInterval, null, h(b.tickInterval), C(e.allowDecimals, !(.5 < b.tickInterval && 5 > b.tickInterval && 1E3 < b.max && 9999 > b.max)), !!this.tickAmount));
                this.tickAmount || (b.tickInterval = b.unsquish());
                this.setTickPositions()
            },
            setTickPositions: function () {
                var a = this.options, b, c = a.tickPositions;
                b = this.getMinorTickInterval();
                var d = a.tickPositioner, e = a.startOnTick, h = a.endOnTick;
                this.tickmarkOffset = this.categories && "between" === a.tickmarkPlacement && 1 === this.tickInterval ? .5 : 0;
                this.minorTickInterval = "auto" === b && this.tickInterval ? this.tickInterval / 5 : b;
                this.single = this.min === this.max && n(this.min) && !this.tickAmount && (parseInt(this.min, 10) === this.min || !1 !== a.allowDecimals);
                this.tickPositions = b = c && c.slice();
                !b && (b = this.isDatetimeAxis ? this.getTimeTicks(this.normalizeTimeTickInterval(this.tickInterval, a.units), this.min, this.max, a.startOfWeek, this.ordinalPositions, this.closestPointRange, !0) : this.isLog ? this.getLogTickPositions(this.tickInterval, this.min, this.max) : this.getLinearTickPositions(this.tickInterval, this.min, this.max), b.length > this.len && (b = [b[0], b.pop()], b[0] === b[1] && (b.length = 1)), this.tickPositions = b, d && (d = d.apply(this, [this.min, this.max]))) && (this.tickPositions = b = d);
                this.paddedTicks = b.slice(0);
                this.trimTicks(b, e, h);
                this.isLinked || (this.single && 2 > b.length && (this.min -= .5, this.max += .5), c || d || this.adjustTickAmount())
            },
            trimTicks: function (a, b, c) {
                var g = a[0], d = a[a.length - 1], e = this.minPointOffset || 0;
                if (!this.isLinked) {
                    if (b && -Infinity !== g) this.min = g; else for (; this.min - e > a[0];) a.shift();
                    if (c) this.max = d; else for (; this.max + e < a[a.length - 1];) a.pop();
                    0 === a.length && n(g) && !this.options.tickPositions && a.push((d + g) / 2)
                }
            },
            alignToOthers: function () {
                var a = {}, b, d = this.options;
                !1 === this.chart.options.chart.alignTicks ||
                !1 === d.alignTicks || this.isLog || c(this.chart[this.coll], function (g) {
                    var c = g.options, c = [g.horiz ? c.left : c.top, c.width, c.height, c.pane].join();
                    g.series.length && (a[c] ? b = !0 : a[c] = 1)
                });
                return b
            },
            getTickAmount: function () {
                var a = this.options, b = a.tickAmount, c = a.tickPixelInterval;
                !n(a.tickInterval) && this.len < c && !this.isRadial && !this.isLog && a.startOnTick && a.endOnTick && (b = 2);
                !b && this.alignToOthers() && (b = Math.ceil(this.len / c) + 1);
                4 > b && (this.finalTickAmt = b, b = 5);
                this.tickAmount = b
            },
            adjustTickAmount: function () {
                var a =
                        this.tickInterval, b = this.tickPositions, c = this.tickAmount, d = this.finalTickAmt,
                    e = b && b.length, h = C(this.threshold, this.softThreshold ? 0 : null);
                if (this.hasData()) {
                    if (e < c) {
                        for (; b.length < c;) b.length % 2 || this.min === h ? b.push(l(b[b.length - 1] + a)) : b.unshift(l(b[0] - a));
                        this.transA *= (e - 1) / (c - 1);
                        this.min = b[0];
                        this.max = b[b.length - 1]
                    } else e > c && (this.tickInterval *= 2, this.setTickPositions());
                    if (n(d)) {
                        for (a = c = b.length; a--;) (3 === d && 1 === a % 2 || 2 >= d && 0 < a && a < c - 1) && b.splice(a, 1);
                        this.finalTickAmt = void 0
                    }
                }
            },
            setScale: function () {
                var a,
                    b;
                this.oldMin = this.min;
                this.oldMax = this.max;
                this.oldAxisLength = this.len;
                this.setAxisSize();
                b = this.len !== this.oldAxisLength;
                c(this.series, function (b) {
                    if (b.isDirtyData || b.isDirty || b.xAxis.isDirty) a = !0
                });
                b || a || this.isLinked || this.forceRedraw || this.userMin !== this.oldUserMin || this.userMax !== this.oldUserMax || this.alignToOthers() ? (this.resetStacks && this.resetStacks(), this.forceRedraw = !1, this.getSeriesExtremes(), this.setTickInterval(), this.oldUserMin = this.userMin, this.oldUserMax = this.userMax, this.isDirty ||
                (this.isDirty = b || this.min !== this.oldMin || this.max !== this.oldMax)) : this.cleanStacks && this.cleanStacks();
                m(this, "afterSetScale")
            },
            setExtremes: function (a, d, e, h, k) {
                var g = this, p = g.chart;
                e = C(e, !0);
                c(g.series, function (a) {
                    delete a.kdTree
                });
                k = b(k, {min: a, max: d});
                m(g, "setExtremes", k, function () {
                    g.userMin = a;
                    g.userMax = d;
                    g.eventArgs = k;
                    e && p.redraw(h)
                })
            },
            zoom: function (a, b) {
                var g = this.dataMin, c = this.dataMax, d = this.options, e = Math.min(g, C(d.min, g)),
                    d = Math.max(c, C(d.max, c));
                if (a !== this.min || b !== this.max) this.allowZoomOutside ||
                (n(g) && (a < e && (a = e), a > d && (a = d)), n(c) && (b < e && (b = e), b > d && (b = d))), this.displayBtn = void 0 !== a || void 0 !== b, this.setExtremes(a, b, !1, void 0, {trigger: "zoom"});
                return !0
            },
            setAxisSize: function () {
                var b = this.chart, c = this.options, d = c.offsets || [0, 0, 0, 0], e = this.horiz,
                    h = this.width = Math.round(a.relativeLength(C(c.width, b.plotWidth - d[3] + d[1]), b.plotWidth)),
                    k = this.height = Math.round(a.relativeLength(C(c.height, b.plotHeight - d[0] + d[2]), b.plotHeight)),
                    p = this.top = Math.round(a.relativeLength(C(c.top, b.plotTop + d[0]), b.plotHeight,
                        b.plotTop)),
                    c = this.left = Math.round(a.relativeLength(C(c.left, b.plotLeft + d[3]), b.plotWidth, b.plotLeft));
                this.bottom = b.chartHeight - k - p;
                this.right = b.chartWidth - h - c;
                this.len = Math.max(e ? h : k, 0);
                this.pos = e ? c : p
            },
            getExtremes: function () {
                var a = this.isLog, b = this.lin2log;
                return {
                    min: a ? l(b(this.min)) : this.min,
                    max: a ? l(b(this.max)) : this.max,
                    dataMin: this.dataMin,
                    dataMax: this.dataMax,
                    userMin: this.userMin,
                    userMax: this.userMax
                }
            },
            getThreshold: function (a) {
                var b = this.isLog, c = this.lin2log, g = b ? c(this.min) : this.min, b = b ? c(this.max) :
                    this.max;
                null === a ? a = g : g > a ? a = g : b < a && (a = b);
                return this.translate(a, 0, 1, 0, 1)
            },
            autoLabelAlign: function (a) {
                a = (C(a, 0) - 90 * this.side + 720) % 360;
                return 15 < a && 165 > a ? "right" : 195 < a && 345 > a ? "left" : "center"
            },
            tickSize: function (a) {
                var b = this.options, c = b[a + "Length"], g = C(b[a + "Width"], "tick" === a && this.isXAxis ? 1 : 0);
                if (g && c) return "inside" === b[a + "Position"] && (c = -c), [c, g]
            },
            labelMetrics: function () {
                var a = this.tickPositions && this.tickPositions[0] || 0;
                return this.chart.renderer.fontMetrics(this.options.labels.style && this.options.labels.style.fontSize,
                    this.ticks[a] && this.ticks[a].label)
            },
            unsquish: function () {
                var a = this.options.labels, b = this.horiz, d = this.tickInterval, e = d,
                    h = this.len / (((this.categories ? 1 : 0) + this.max - this.min) / d), k, p = a.rotation,
                    x = this.labelMetrics(), r, m = Number.MAX_VALUE, A, F = function (a) {
                        a /= h || 1;
                        a = 1 < a ? Math.ceil(a) : 1;
                        return a * d
                    };
                b ? (A = !a.staggerLines && !a.step && (n(p) ? [p] : h < C(a.autoRotationLimit, 80) && a.autoRotation)) && c(A, function (a) {
                    var b;
                    if (a === p || a && -90 <= a && 90 >= a) r = F(Math.abs(x.h / Math.sin(v * a))), b = r + Math.abs(a / 360), b < m && (m = b, k = a, e = r)
                }) :
                    a.step || (e = F(x.h));
                this.autoRotation = A;
                this.labelRotation = C(k, p);
                return e
            },
            getSlotWidth: function () {
                var a = this.chart, b = this.horiz, c = this.options.labels,
                    d = Math.max(this.tickPositions.length - (this.categories ? 0 : 1), 1), e = a.margin[3];
                return b && 2 > (c.step || 0) && !c.rotation && (this.staggerLines || 1) * this.len / d || !b && (c.style && parseInt(c.style.width, 10) || e && e - a.spacing[3] || .33 * a.chartWidth)
            },
            renderUnsquish: function () {
                var a = this.chart, b = a.renderer, d = this.tickPositions, e = this.ticks, h = this.options.labels,
                    k = this.horiz,
                    p = this.getSlotWidth(), x = Math.max(1, Math.round(p - 2 * (h.padding || 5))), r = {},
                    m = this.labelMetrics(), A = h.style && h.style.textOverflow, F, l, f = 0, z;
                I(h.rotation) || (r.rotation = h.rotation || 0);
                c(d, function (a) {
                    (a = e[a]) && a.label && a.label.textPxLength > f && (f = a.label.textPxLength)
                });
                this.maxLabelLength = f;
                if (this.autoRotation) f > x && f > m.h ? r.rotation = this.labelRotation : this.labelRotation = 0; else if (p && (F = x, !A)) for (l = "clip", x = d.length; !k && x--;) if (z = d[x], z = e[z].label) z.styles && "ellipsis" === z.styles.textOverflow ? z.css({textOverflow: "clip"}) :
                    z.textPxLength > p && z.css({width: p + "px"}), z.getBBox().height > this.len / d.length - (m.h - m.f) && (z.specificTextOverflow = "ellipsis");
                r.rotation && (F = f > .5 * a.chartHeight ? .33 * a.chartHeight : a.chartHeight, A || (l = "ellipsis"));
                if (this.labelAlign = h.align || this.autoLabelAlign(this.labelRotation)) r.align = this.labelAlign;
                c(d, function (a) {
                    var b = (a = e[a]) && a.label;
                    b && (b.attr(r), !F || h.style && h.style.width || !(F < b.textPxLength || "SPAN" === b.element.tagName) || b.css({
                        width: F,
                        textOverflow: b.specificTextOverflow || l
                    }), delete b.specificTextOverflow,
                        a.rotation = r.rotation)
                });
                this.tickRotCorr = b.rotCorr(m.b, this.labelRotation || 0, 0 !== this.side)
            },
            hasData: function () {
                return this.hasVisibleSeries || n(this.min) && n(this.max) && this.tickPositions && 0 < this.tickPositions.length
            },
            addTitle: function (a) {
                var b = this.chart.renderer, c = this.horiz, g = this.opposite, d = this.options.title, e;
                this.axisTitle || ((e = d.textAlign) || (e = (c ? {
                    low: "left",
                    middle: "center",
                    high: "right"
                } : {
                    low: g ? "right" : "left",
                    middle: "center",
                    high: g ? "left" : "right"
                })[d.align]), this.axisTitle = b.text(d.text, 0,
                    0, d.useHTML).attr({
                    zIndex: 7,
                    rotation: d.rotation || 0,
                    align: e
                }).addClass("highcharts-axis-title").css(z(d.style)).add(this.axisGroup), this.axisTitle.isNew = !0);
                d.style.width || this.isRadial || this.axisTitle.css({width: this.len});
                this.axisTitle[a ? "show" : "hide"](!0)
            },
            generateTick: function (a) {
                var b = this.ticks;
                b[a] ? b[a].addLabel() : b[a] = new J(this, a)
            },
            getOffset: function () {
                var a = this, b = a.chart, d = b.renderer, e = a.options, h = a.tickPositions, k = a.ticks, p = a.horiz,
                    x = a.side, r = b.inverted && !a.isZAxis ? [1, 0, 3, 2][x] : x, m, A,
                    F = 0, l, f = 0, z = e.title, J = e.labels, G = 0, u = b.axisOffset, b = b.clipOffset,
                    t = [-1, 1, 1, -1][x], v = e.className, M = a.axisParent, q = this.tickSize("tick");
                m = a.hasData();
                a.showAxis = A = m || C(e.showEmpty, !0);
                a.staggerLines = a.horiz && J.staggerLines;
                a.axisGroup || (a.gridGroup = d.g("grid").attr({zIndex: e.gridZIndex || 1}).addClass("highcharts-" + this.coll.toLowerCase() + "-grid " + (v || "")).add(M), a.axisGroup = d.g("axis").attr({zIndex: e.zIndex || 2}).addClass("highcharts-" + this.coll.toLowerCase() + " " + (v || "")).add(M), a.labelGroup = d.g("axis-labels").attr({
                    zIndex: J.zIndex ||
                    7
                }).addClass("highcharts-" + a.coll.toLowerCase() + "-labels " + (v || "")).add(M));
                m || a.isLinked ? (c(h, function (b, c) {
                    a.generateTick(b, c)
                }), a.renderUnsquish(), a.reserveSpaceDefault = 0 === x || 2 === x || {
                    1: "left",
                    3: "right"
                }[x] === a.labelAlign, C(J.reserveSpace, "center" === a.labelAlign ? !0 : null, a.reserveSpaceDefault) && c(h, function (a) {
                    G = Math.max(k[a].getLabelSize(), G)
                }), a.staggerLines && (G *= a.staggerLines), a.labelOffset = G * (a.opposite ? -1 : 1)) : D(k, function (a, b) {
                    a.destroy();
                    delete k[b]
                });
                z && z.text && !1 !== z.enabled && (a.addTitle(A),
                A && !1 !== z.reserveSpace && (a.titleOffset = F = a.axisTitle.getBBox()[p ? "height" : "width"], l = z.offset, f = n(l) ? 0 : C(z.margin, p ? 5 : 10)));
                a.renderLine();
                a.offset = t * C(e.offset, u[x]);
                a.tickRotCorr = a.tickRotCorr || {x: 0, y: 0};
                d = 0 === x ? -a.labelMetrics().h : 2 === x ? a.tickRotCorr.y : 0;
                f = Math.abs(G) + f;
                G && (f = f - d + t * (p ? C(J.y, a.tickRotCorr.y + 8 * t) : J.x));
                a.axisTitleMargin = C(l, f);
                u[x] = Math.max(u[x], a.axisTitleMargin + F + t * a.offset, f, m && h.length && q ? q[0] + t * a.offset : 0);
                e = e.offset ? 0 : 2 * Math.floor(a.axisLine.strokeWidth() / 2);
                b[r] = Math.max(b[r],
                    e)
            },
            getLinePath: function (a) {
                var b = this.chart, c = this.opposite, g = this.offset, d = this.horiz,
                    e = this.left + (c ? this.width : 0) + g,
                    g = b.chartHeight - this.bottom - (c ? this.height : 0) + g;
                c && (a *= -1);
                return b.renderer.crispLine(["M", d ? this.left : e, d ? g : this.top, "L", d ? b.chartWidth - this.right : e, d ? g : b.chartHeight - this.bottom], a)
            },
            renderLine: function () {
                this.axisLine || (this.axisLine = this.chart.renderer.path().addClass("highcharts-axis-line").add(this.axisGroup), this.axisLine.attr({
                    stroke: this.options.lineColor, "stroke-width": this.options.lineWidth,
                    zIndex: 7
                }))
            },
            getTitlePosition: function () {
                var a = this.horiz, b = this.left, c = this.top, d = this.len, e = this.options.title, h = a ? b : c,
                    k = this.opposite, p = this.offset, x = e.x || 0, r = e.y || 0, m = this.axisTitle,
                    A = this.chart.renderer.fontMetrics(e.style && e.style.fontSize, m),
                    m = Math.max(m.getBBox(null, 0).height - A.h - 1, 0),
                    d = {low: h + (a ? 0 : d), middle: h + d / 2, high: h + (a ? d : 0)}[e.align],
                    b = (a ? c + this.height : b) + (a ? 1 : -1) * (k ? -1 : 1) * this.axisTitleMargin + [-m, m, A.f, -m][this.side];
                return {
                    x: a ? d + x : b + (k ? this.width : 0) + p + x, y: a ? b + r - (k ? this.height : 0) + p :
                        d + r
                }
            },
            renderMinorTick: function (a) {
                var b = this.chart.hasRendered && r(this.oldMin), c = this.minorTicks;
                c[a] || (c[a] = new J(this, a, "minor"));
                b && c[a].isNew && c[a].render(null, !0);
                c[a].render(null, !1, 1)
            },
            renderTick: function (a, b) {
                var c = this.isLinked, g = this.ticks, d = this.chart.hasRendered && r(this.oldMin);
                if (!c || a >= this.min && a <= this.max) g[a] || (g[a] = new J(this, a)), d && g[a].isNew && g[a].render(b, !0, .1), g[a].render(b)
            },
            render: function () {
                var b = this, d = b.chart, e = b.options, h = b.isLog, k = b.lin2log, p = b.isLinked,
                    x = b.tickPositions,
                    m = b.axisTitle, F = b.ticks, l = b.minorTicks, f = b.alternateBands, z = e.stackLabels,
                    G = e.alternateGridColor, n = b.tickmarkOffset, u = b.axisLine, C = b.showAxis,
                    t = H(d.renderer.globalAnimation), v, M;
                b.labelEdge.length = 0;
                b.overlap = !1;
                c([F, l, f], function (a) {
                    D(a, function (a) {
                        a.isActive = !1
                    })
                });
                if (b.hasData() || p) b.minorTickInterval && !b.categories && c(b.getMinorTickPositions(), function (a) {
                    b.renderMinorTick(a)
                }), x.length && (c(x, function (a, c) {
                    b.renderTick(a, c)
                }), n && (0 === b.min || b.single) && (F[-1] || (F[-1] = new J(b, -1, null, !0)), F[-1].render(-1))),
                G && c(x, function (c, e) {
                    M = void 0 !== x[e + 1] ? x[e + 1] + n : b.max - n;
                    0 === e % 2 && c < b.max && M <= b.max + (d.polar ? -n : n) && (f[c] || (f[c] = new a.PlotLineOrBand(b)), v = c + n, f[c].options = {
                        from: h ? k(v) : v,
                        to: h ? k(M) : M,
                        color: G
                    }, f[c].render(), f[c].isActive = !0)
                }), b._addedPlotLB || (c((e.plotLines || []).concat(e.plotBands || []), function (a) {
                    b.addPlotBandOrLine(a)
                }), b._addedPlotLB = !0);
                c([F, l, f], function (a) {
                    var b, c = [], e = t.duration;
                    D(a, function (a, b) {
                        a.isActive || (a.render(b, !1, 0), a.isActive = !1, c.push(b))
                    });
                    A(function () {
                        for (b = c.length; b--;) a[c[b]] &&
                        !a[c[b]].isActive && (a[c[b]].destroy(), delete a[c[b]])
                    }, a !== f && d.hasRendered && e ? e : 0)
                });
                u && (u[u.isPlaced ? "animate" : "attr"]({d: this.getLinePath(u.strokeWidth())}), u.isPlaced = !0, u[C ? "show" : "hide"](!0));
                m && C && (e = b.getTitlePosition(), r(e.y) ? (m[m.isNew ? "attr" : "animate"](e), m.isNew = !1) : (m.attr("y", -9999), m.isNew = !0));
                z && z.enabled && b.renderStackTotals();
                b.isDirty = !1
            },
            redraw: function () {
                this.visible && (this.render(), c(this.plotLinesAndBands, function (a) {
                    a.render()
                }));
                c(this.series, function (a) {
                    a.isDirty = !0
                })
            },
            keepProps: "extKey hcEvents names series userMax userMin".split(" "),
            destroy: function (a) {
                var b = this, d = b.stacks, g = b.plotLinesAndBands, h;
                a || x(b);
                D(d, function (a, b) {
                    u(a);
                    d[b] = null
                });
                c([b.ticks, b.minorTicks, b.alternateBands], function (a) {
                    u(a)
                });
                if (g) for (a = g.length; a--;) g[a].destroy();
                c("stackTotalGroup axisLine axisTitle axisGroup gridGroup labelGroup cross".split(" "), function (a) {
                    b[a] && (b[a] = b[a].destroy())
                });
                for (h in b.plotLinesAndBandsGroups) b.plotLinesAndBandsGroups[h] = b.plotLinesAndBandsGroups[h].destroy();
                D(b, function (a, c) {
                    -1 === e(c, b.keepProps) && delete b[c]
                })
            },
            drawCrosshair: function (a, b) {
                var c, d = this.crosshair, e = C(d.snap, !0), g, h = this.cross;
                a || (a = this.cross && this.cross.e);
                this.crosshair && !1 !== (n(b) || !e) ? (e ? n(b) && (g = this.isXAxis ? b.plotX : this.len - b.plotY) : g = a && (this.horiz ? a.chartX - this.pos : this.len - a.chartY + this.pos), n(g) && (c = this.getPlotLinePath(b && (this.isXAxis ? b.x : C(b.stackY, b.y)), null, null, null, g) || null), n(c) ? (b = this.categories && !this.isRadial, h || (this.cross = h = this.chart.renderer.path().addClass("highcharts-crosshair highcharts-crosshair-" +
                    (b ? "category " : "thin ") + d.className).attr({zIndex: C(d.zIndex, 2)}).add(), h.attr({
                    stroke: d.color || (b ? f("#ccd6eb").setOpacity(.25).get() : "#cccccc"),
                    "stroke-width": C(d.width, 1)
                }).css({"pointer-events": "none"}), d.dashStyle && h.attr({dashstyle: d.dashStyle})), h.show().attr({d: c}), b && !d.width && h.attr({"stroke-width": this.transA}), this.cross.e = a) : this.hideCrosshair()) : this.hideCrosshair()
            },
            hideCrosshair: function () {
                this.cross && this.cross.hide()
            }
        });
        return a.Axis = G
    }(K);
    (function (a) {
        var B = a.Axis, H = a.getMagnitude,
            E = a.normalizeTickInterval, q = a.timeUnits;
        B.prototype.getTimeTicks = function () {
            return this.chart.time.getTimeTicks.apply(this.chart.time, arguments)
        };
        B.prototype.normalizeTimeTickInterval = function (a, l) {
            var f = l || [["millisecond", [1, 2, 5, 10, 20, 25, 50, 100, 200, 500]], ["second", [1, 2, 5, 10, 15, 30]], ["minute", [1, 2, 5, 10, 15, 30]], ["hour", [1, 2, 3, 4, 6, 8, 12]], ["day", [1, 2]], ["week", [1, 2]], ["month", [1, 2, 3, 4, 6]], ["year", null]];
            l = f[f.length - 1];
            var n = q[l[0]], v = l[1], u;
            for (u = 0; u < f.length && !(l = f[u], n = q[l[0]], v = l[1], f[u + 1] && a <= (n *
                v[v.length - 1] + q[f[u + 1][0]]) / 2); u++) ;
            n === q.year && a < 5 * n && (v = [1, 2, 5]);
            a = E(a / n, v, "year" === l[0] ? Math.max(H(a / n), 1) : 1);
            return {unitRange: n, count: a, unitName: l[0]}
        }
    })(K);
    (function (a) {
        var B = a.Axis, H = a.getMagnitude, E = a.map, q = a.normalizeTickInterval, f = a.pick;
        B.prototype.getLogTickPositions = function (a, t, n, v) {
            var l = this.options, c = this.len, b = this.lin2log, m = this.log2lin, d = [];
            v || (this._minorAutoInterval = null);
            if (.5 <= a) a = Math.round(a), d = this.getLinearTickPositions(a, t, n); else if (.08 <= a) for (var c = Math.floor(t), h, k,
                                                                                                                  e, p, r, l = .3 < a ? [1, 2, 4] : .15 < a ? [1, 2, 4, 6, 8] : [1, 2, 3, 4, 5, 6, 7, 8, 9]; c < n + 1 && !r; c++) for (k = l.length, h = 0; h < k && !r; h++) e = m(b(c) * l[h]), e > t && (!v || p <= n) && void 0 !== p && d.push(p), p > n && (r = !0), p = e; else t = b(t), n = b(n), a = v ? this.getMinorTickInterval() : l.tickInterval, a = f("auto" === a ? null : a, this._minorAutoInterval, l.tickPixelInterval / (v ? 5 : 1) * (n - t) / ((v ? c / this.tickPositions.length : c) || 1)), a = q(a, null, H(a)), d = E(this.getLinearTickPositions(a, t, n), m), v || (this._minorAutoInterval = a / 5);
            v || (this.tickInterval = a);
            return d
        };
        B.prototype.log2lin =
            function (a) {
                return Math.log(a) / Math.LN10
            };
        B.prototype.lin2log = function (a) {
            return Math.pow(10, a)
        }
    })(K);
    (function (a, B) {
        var H = a.arrayMax, E = a.arrayMin, q = a.defined, f = a.destroyObjectProperties, l = a.each, t = a.erase,
            n = a.merge, v = a.pick;
        a.PlotLineOrBand = function (a, c) {
            this.axis = a;
            c && (this.options = c, this.id = c.id)
        };
        a.PlotLineOrBand.prototype = {
            render: function () {
                var f = this, c = f.axis, b = c.horiz, m = f.options, d = m.label, h = f.label, k = m.to, e = m.from,
                    p = m.value, r = q(e) && q(k), l = q(p), z = f.svgElem, t = !z, D = [], C = m.color, x = v(m.zIndex,
                    0), F = m.events, D = {"class": "highcharts-plot-" + (r ? "band " : "line ") + (m.className || "")},
                    A = {}, J = c.chart.renderer, G = r ? "bands" : "lines", g = c.log2lin;
                c.isLog && (e = g(e), k = g(k), p = g(p));
                l ? (D = {
                    stroke: C,
                    "stroke-width": m.width
                }, m.dashStyle && (D.dashstyle = m.dashStyle)) : r && (C && (D.fill = C), m.borderWidth && (D.stroke = m.borderColor, D["stroke-width"] = m.borderWidth));
                A.zIndex = x;
                G += "-" + x;
                (C = c.plotLinesAndBandsGroups[G]) || (c.plotLinesAndBandsGroups[G] = C = J.g("plot-" + G).attr(A).add());
                t && (f.svgElem = z = J.path().attr(D).add(C));
                if (l) D =
                    c.getPlotLinePath(p, z.strokeWidth()); else if (r) D = c.getPlotBandPath(e, k, m); else return;
                t && D && D.length ? (z.attr({d: D}), F && a.objectEach(F, function (a, b) {
                    z.on(b, function (a) {
                        F[b].apply(f, [a])
                    })
                })) : z && (D ? (z.show(), z.animate({d: D})) : (z.hide(), h && (f.label = h = h.destroy())));
                d && q(d.text) && D && D.length && 0 < c.width && 0 < c.height && !D.flat ? (d = n({
                    align: b && r && "center",
                    x: b ? !r && 4 : 10,
                    verticalAlign: !b && r && "middle",
                    y: b ? r ? 16 : 10 : r ? 6 : -4,
                    rotation: b && !r && 90
                }, d), this.renderLabel(d, D, r, x)) : h && h.hide();
                return f
            }, renderLabel: function (a,
                                      c, b, m) {
                var d = this.label, h = this.axis.chart.renderer;
                d || (d = {
                    align: a.textAlign || a.align,
                    rotation: a.rotation,
                    "class": "highcharts-plot-" + (b ? "band" : "line") + "-label " + (a.className || "")
                }, d.zIndex = m, this.label = d = h.text(a.text, 0, 0, a.useHTML).attr(d).add(), d.css(a.style));
                m = c.xBounds || [c[1], c[4], b ? c[6] : c[1]];
                c = c.yBounds || [c[2], c[5], b ? c[7] : c[2]];
                b = E(m);
                h = E(c);
                d.align(a, !1, {x: b, y: h, width: H(m) - b, height: H(c) - h});
                d.show()
            }, destroy: function () {
                t(this.axis.plotLinesAndBands, this);
                delete this.axis;
                f(this)
            }
        };
        a.extend(B.prototype,
            {
                getPlotBandPath: function (a, c) {
                    var b = this.getPlotLinePath(c, null, null, !0), m = this.getPlotLinePath(a, null, null, !0),
                        d = [], h = this.horiz, k = 1, e;
                    a = a < this.min && c < this.min || a > this.max && c > this.max;
                    if (m && b) for (a && (e = m.toString() === b.toString(), k = 0), a = 0; a < m.length; a += 6) h && b[a + 1] === m[a + 1] ? (b[a + 1] += k, b[a + 4] += k) : h || b[a + 2] !== m[a + 2] || (b[a + 2] += k, b[a + 5] += k), d.push("M", m[a + 1], m[a + 2], "L", m[a + 4], m[a + 5], b[a + 4], b[a + 5], b[a + 1], b[a + 2], "z"), d.flat = e;
                    return d
                }, addPlotBand: function (a) {
                return this.addPlotBandOrLine(a, "plotBands")
            },
                addPlotLine: function (a) {
                    return this.addPlotBandOrLine(a, "plotLines")
                }, addPlotBandOrLine: function (f, c) {
                var b = (new a.PlotLineOrBand(this, f)).render(), m = this.userOptions;
                b && (c && (m[c] = m[c] || [], m[c].push(f)), this.plotLinesAndBands.push(b));
                return b
            }, removePlotBandOrLine: function (a) {
                for (var c = this.plotLinesAndBands, b = this.options, m = this.userOptions, d = c.length; d--;) c[d].id === a && c[d].destroy();
                l([b.plotLines || [], m.plotLines || [], b.plotBands || [], m.plotBands || []], function (b) {
                    for (d = b.length; d--;) b[d].id === a &&
                    t(b, b[d])
                })
            }, removePlotBand: function (a) {
                this.removePlotBandOrLine(a)
            }, removePlotLine: function (a) {
                this.removePlotBandOrLine(a)
            }
            })
    })(K, V);
    (function (a) {
        var B = a.each, H = a.extend, E = a.format, q = a.isNumber, f = a.map, l = a.merge, t = a.pick, n = a.splat,
            v = a.syncTimeout, u = a.timeUnits;
        a.Tooltip = function () {
            this.init.apply(this, arguments)
        };
        a.Tooltip.prototype = {
            init: function (a, b) {
                this.chart = a;
                this.options = b;
                this.crosshairs = [];
                this.now = {x: 0, y: 0};
                this.isHidden = !0;
                this.split = b.split && !a.inverted;
                this.shared = b.shared || this.split
            },
            cleanSplit: function (a) {
                B(this.chart.series, function (b) {
                    var c = b && b.tt;
                    c && (!c.isActive || a ? b.tt = c.destroy() : c.isActive = !1)
                })
            }, getLabel: function () {
                var a = this.chart.renderer, b = this.options;
                this.label || (this.split ? this.label = a.g("tooltip") : (this.label = a.label("", 0, 0, b.shape || "callout", null, null, b.useHTML, null, "tooltip").attr({
                    padding: b.padding,
                    r: b.borderRadius
                }), this.label.attr({
                    fill: b.backgroundColor,
                    "stroke-width": b.borderWidth
                }).css(b.style).shadow(b.shadow)), this.label.attr({zIndex: 8}).add());
                return this.label
            },
            update: function (a) {
                this.destroy();
                l(!0, this.chart.options.tooltip.userOptions, a);
                this.init(this.chart, l(!0, this.options, a))
            }, destroy: function () {
                this.label && (this.label = this.label.destroy());
                this.split && this.tt && (this.cleanSplit(this.chart, !0), this.tt = this.tt.destroy());
                clearTimeout(this.hideTimer);
                clearTimeout(this.tooltipTimeout)
            }, move: function (a, b, m, d) {
                var c = this, k = c.now,
                    e = !1 !== c.options.animation && !c.isHidden && (1 < Math.abs(a - k.x) || 1 < Math.abs(b - k.y)),
                    p = c.followPointer || 1 < c.len;
                H(k, {
                    x: e ? (2 * k.x + a) /
                        3 : a,
                    y: e ? (k.y + b) / 2 : b,
                    anchorX: p ? void 0 : e ? (2 * k.anchorX + m) / 3 : m,
                    anchorY: p ? void 0 : e ? (k.anchorY + d) / 2 : d
                });
                c.getLabel().attr(k);
                e && (clearTimeout(this.tooltipTimeout), this.tooltipTimeout = setTimeout(function () {
                    c && c.move(a, b, m, d)
                }, 32))
            }, hide: function (a) {
                var b = this;
                clearTimeout(this.hideTimer);
                a = t(a, this.options.hideDelay, 500);
                this.isHidden || (this.hideTimer = v(function () {
                    b.getLabel()[a ? "fadeOut" : "hide"]();
                    b.isHidden = !0
                }, a))
            }, getAnchor: function (a, b) {
                var c, d = this.chart, h = d.inverted, k = d.plotTop, e = d.plotLeft, p = 0, r =
                    0, l, z;
                a = n(a);
                c = a[0].tooltipPos;
                this.followPointer && b && (void 0 === b.chartX && (b = d.pointer.normalize(b)), c = [b.chartX - d.plotLeft, b.chartY - k]);
                c || (B(a, function (a) {
                    l = a.series.yAxis;
                    z = a.series.xAxis;
                    p += a.plotX + (!h && z ? z.left - e : 0);
                    r += (a.plotLow ? (a.plotLow + a.plotHigh) / 2 : a.plotY) + (!h && l ? l.top - k : 0)
                }), p /= a.length, r /= a.length, c = [h ? d.plotWidth - r : p, this.shared && !h && 1 < a.length && b ? b.chartY - k : h ? d.plotHeight - p : r]);
                return f(c, Math.round)
            }, getPosition: function (a, b, m) {
                var c = this.chart, h = this.distance, k = {}, e = c.inverted &&
                    m.h || 0, p, r = ["y", c.chartHeight, b, m.plotY + c.plotTop, c.plotTop, c.plotTop + c.plotHeight],
                    f = ["x", c.chartWidth, a, m.plotX + c.plotLeft, c.plotLeft, c.plotLeft + c.plotWidth],
                    l = !this.followPointer && t(m.ttBelow, !c.inverted === !!m.negative),
                    n = function (a, b, c, d, g, p) {
                        var x = c < d - h, r = d + h + c < b, m = d - h - c;
                        d += h;
                        if (l && r) k[a] = d; else if (!l && x) k[a] = m; else if (x) k[a] = Math.min(p - c, 0 > m - e ? m : m - e); else if (r) k[a] = Math.max(g, d + e + c > b ? d : d + e); else return !1
                    }, D = function (a, b, c, d) {
                        var e;
                        d < h || d > b - h ? e = !1 : k[a] = d < c / 2 ? 1 : d > b - c / 2 ? b - c - 2 : d - c / 2;
                        return e
                    }, C =
                        function (a) {
                            var b = r;
                            r = f;
                            f = b;
                            p = a
                        }, x = function () {
                        !1 !== n.apply(0, r) ? !1 !== D.apply(0, f) || p || (C(!0), x()) : p ? k.x = k.y = 0 : (C(!0), x())
                    };
                (c.inverted || 1 < this.len) && C();
                x();
                return k
            }, defaultFormatter: function (a) {
                var b = this.points || n(this), c;
                c = [a.tooltipFooterHeaderFormatter(b[0])];
                c = c.concat(a.bodyFormatter(b));
                c.push(a.tooltipFooterHeaderFormatter(b[0], !0));
                return c
            }, refresh: function (a, b) {
                var c, d = this.options, h, k = a, e, p = {}, r = [];
                c = d.formatter || this.defaultFormatter;
                var p = this.shared, f;
                d.enabled && (clearTimeout(this.hideTimer),
                    this.followPointer = n(k)[0].series.tooltipOptions.followPointer, e = this.getAnchor(k, b), b = e[0], h = e[1], !p || k.series && k.series.noSharedTooltip ? p = k.getLabelConfig() : (B(k, function (a) {
                    a.setState("hover");
                    r.push(a.getLabelConfig())
                }), p = {
                    x: k[0].category,
                    y: k[0].y
                }, p.points = r, k = k[0]), this.len = r.length, p = c.call(p, this), f = k.series, this.distance = t(f.tooltipOptions.distance, 16), !1 === p ? this.hide() : (c = this.getLabel(), this.isHidden && c.attr({opacity: 1}).show(), this.split ? this.renderSplit(p, n(a)) : (d.style.width || c.css({width: this.chart.spacingBox.width}),
                    c.attr({text: p && p.join ? p.join("") : p}), c.removeClass(/highcharts-color-[\d]+/g).addClass("highcharts-color-" + t(k.colorIndex, f.colorIndex)), c.attr({stroke: d.borderColor || k.color || f.color || "#666666"}), this.updatePosition({
                    plotX: b,
                    plotY: h,
                    negative: k.negative,
                    ttBelow: k.ttBelow,
                    h: e[2] || 0
                })), this.isHidden = !1))
            }, renderSplit: function (c, b) {
                var m = this, d = [], h = this.chart, k = h.renderer, e = !0, p = this.options, r = 0,
                    f = this.getLabel();
                a.isString(c) && (c = [!1, c]);
                B(c.slice(0, b.length + 1), function (a, c) {
                    if (!1 !== a) {
                        c = b[c - 1] ||
                            {isHeader: !0, plotX: b[0].plotX};
                        var l = c.series || m, z = l.tt, x = c.series || {},
                            F = "highcharts-color-" + t(c.colorIndex, x.colorIndex, "none");
                        z || (l.tt = z = k.label(null, null, null, "callout", null, null, p.useHTML).addClass("highcharts-tooltip-box " + F).attr({
                            padding: p.padding,
                            r: p.borderRadius,
                            fill: p.backgroundColor,
                            stroke: p.borderColor || c.color || x.color || "#333333",
                            "stroke-width": p.borderWidth
                        }).add(f));
                        z.isActive = !0;
                        z.attr({text: a});
                        z.css(p.style).shadow(p.shadow);
                        a = z.getBBox();
                        x = a.width + z.strokeWidth();
                        c.isHeader ? (r =
                            a.height, x = Math.max(0, Math.min(c.plotX + h.plotLeft - x / 2, h.chartWidth - x))) : x = c.plotX + h.plotLeft - t(p.distance, 16) - x;
                        0 > x && (e = !1);
                        a = (c.series && c.series.yAxis && c.series.yAxis.pos) + (c.plotY || 0);
                        a -= h.plotTop;
                        d.push({
                            target: c.isHeader ? h.plotHeight + r : a,
                            rank: c.isHeader ? 1 : 0,
                            size: l.tt.getBBox().height + 1,
                            point: c,
                            x: x,
                            tt: z
                        })
                    }
                });
                this.cleanSplit();
                a.distribute(d, h.plotHeight + r);
                B(d, function (a) {
                    var b = a.point, c = b.series;
                    a.tt.attr({
                        visibility: void 0 === a.pos ? "hidden" : "inherit",
                        x: e || b.isHeader ? a.x : b.plotX + h.plotLeft + t(p.distance,
                            16),
                        y: a.pos + h.plotTop,
                        anchorX: b.isHeader ? b.plotX + h.plotLeft : b.plotX + c.xAxis.pos,
                        anchorY: b.isHeader ? a.pos + h.plotTop - 15 : b.plotY + c.yAxis.pos
                    })
                })
            }, updatePosition: function (a) {
                var b = this.chart, c = this.getLabel(),
                    c = (this.options.positioner || this.getPosition).call(this, c.width, c.height, a);
                this.move(Math.round(c.x), Math.round(c.y || 0), a.plotX + b.plotLeft, a.plotY + b.plotTop)
            }, getDateFormat: function (a, b, m, d) {
                var c = this.chart.time, k = c.dateFormat("%m-%d %H:%M:%S.%L", b), e, p, r = {
                    millisecond: 15, second: 12, minute: 9, hour: 6,
                    day: 3
                }, f = "millisecond";
                for (p in u) {
                    if (a === u.week && +c.dateFormat("%w", b) === m && "00:00:00.000" === k.substr(6)) {
                        p = "week";
                        break
                    }
                    if (u[p] > a) {
                        p = f;
                        break
                    }
                    if (r[p] && k.substr(r[p]) !== "01-01 00:00:00.000".substr(r[p])) break;
                    "week" !== p && (f = p)
                }
                p && (e = d[p]);
                return e
            }, getXDateFormat: function (a, b, m) {
                b = b.dateTimeLabelFormats;
                var c = m && m.closestPointRange;
                return (c ? this.getDateFormat(c, a.x, m.options.startOfWeek, b) : b.day) || b.year
            }, tooltipFooterHeaderFormatter: function (a, b) {
                b = b ? "footer" : "header";
                var c = a.series, d = c.tooltipOptions,
                    h = d.xDateFormat, k = c.xAxis, e = k && "datetime" === k.options.type && q(a.key),
                    p = d[b + "Format"];
                e && !h && (h = this.getXDateFormat(a, d, k));
                e && h && B(a.point && a.point.tooltipDateKeys || ["key"], function (a) {
                    p = p.replace("{point." + a + "}", "{point." + a + ":" + h + "}")
                });
                return E(p, {point: a, series: c}, this.chart.time)
            }, bodyFormatter: function (a) {
                return f(a, function (a) {
                    var b = a.series.tooltipOptions;
                    return (b[(a.point.formatPrefix || "point") + "Formatter"] || a.point.tooltipFormatter).call(a.point, b[(a.point.formatPrefix || "point") + "Format"])
                })
            }
        }
    })(K);
    (function (a) {
        var B = a.addEvent, H = a.attr, E = a.charts, q = a.color, f = a.css, l = a.defined, t = a.each, n = a.extend,
            v = a.find, u = a.fireEvent, c = a.isNumber, b = a.isObject, m = a.offset, d = a.pick, h = a.splat,
            k = a.Tooltip;
        a.Pointer = function (a, b) {
            this.init(a, b)
        };
        a.Pointer.prototype = {
            init: function (a, b) {
                this.options = b;
                this.chart = a;
                this.runChartClick = b.chart.events && !!b.chart.events.click;
                this.pinchDown = [];
                this.lastValidTouch = {};
                k && (a.tooltip = new k(a, b.tooltip), this.followTouchMove = d(b.tooltip.followTouchMove, !0));
                this.setDOMEvents()
            },
            zoomOption: function (a) {
                var b = this.chart, c = b.options.chart, e = c.zoomType || "", b = b.inverted;
                /touch/.test(a.type) && (e = d(c.pinchType, e));
                this.zoomX = a = /x/.test(e);
                this.zoomY = e = /y/.test(e);
                this.zoomHor = a && !b || e && b;
                this.zoomVert = e && !b || a && b;
                this.hasZoom = a || e
            }, normalize: function (a, b) {
                var c;
                c = a.touches ? a.touches.length ? a.touches.item(0) : a.changedTouches[0] : a;
                b || (this.chartPosition = b = m(this.chart.container));
                return n(a, {chartX: Math.round(c.pageX - b.left), chartY: Math.round(c.pageY - b.top)})
            }, getCoordinates: function (a) {
                var b =
                    {xAxis: [], yAxis: []};
                t(this.chart.axes, function (c) {
                    b[c.isXAxis ? "xAxis" : "yAxis"].push({axis: c, value: c.toValue(a[c.horiz ? "chartX" : "chartY"])})
                });
                return b
            }, findNearestKDPoint: function (a, c, d) {
                var e;
                t(a, function (a) {
                    var h = !(a.noSharedTooltip && c) && 0 > a.options.findNearestPointBy.indexOf("y");
                    a = a.searchPoint(d, h);
                    if ((h = b(a, !0)) && !(h = !b(e, !0))) var h = e.distX - a.distX, k = e.dist - a.dist,
                        p = (a.series.group && a.series.group.zIndex) - (e.series.group && e.series.group.zIndex),
                        h = 0 < (0 !== h && c ? h : 0 !== k ? k : 0 !== p ? p : e.series.index >
                        a.series.index ? -1 : 1);
                    h && (e = a)
                });
                return e
            }, getPointFromEvent: function (a) {
                a = a.target;
                for (var b; a && !b;) b = a.point, a = a.parentNode;
                return b
            }, getChartCoordinatesFromPoint: function (a, b) {
                var c = a.series, e = c.xAxis, c = c.yAxis, h = d(a.clientX, a.plotX);
                if (e && c) return b ? {
                    chartX: e.len + e.pos - h,
                    chartY: c.len + c.pos - a.plotY
                } : {chartX: h + e.pos, chartY: a.plotY + c.pos}
            }, getHoverData: function (c, h, k, f, m, l, n) {
                var e, x = [], p = n && n.isBoosting;
                f = !(!f || !c);
                n = h && !h.stickyTracking ? [h] : a.grep(k, function (a) {
                    return a.visible && !(!m && a.directTouch) &&
                        d(a.options.enableMouseTracking, !0) && a.stickyTracking
                });
                h = (e = f ? c : this.findNearestKDPoint(n, m, l)) && e.series;
                e && (m && !h.noSharedTooltip ? (n = a.grep(k, function (a) {
                    return a.visible && !(!m && a.directTouch) && d(a.options.enableMouseTracking, !0) && !a.noSharedTooltip
                }), t(n, function (a) {
                    var c = v(a.points, function (a) {
                        return a.x === e.x && !a.isNull
                    });
                    b(c) && (p && (c = a.getPoint(c)), x.push(c))
                })) : x.push(e));
                return {hoverPoint: e, hoverSeries: h, hoverPoints: x}
            }, runPointActions: function (b, c) {
                var e = this.chart, h = e.tooltip && e.tooltip.options.enabled ?
                    e.tooltip : void 0, k = h ? h.shared : !1, p = c || e.hoverPoint,
                    f = p && p.series || e.hoverSeries,
                    f = this.getHoverData(p, f, e.series, !!c || f && f.directTouch && this.isDirectTouch, k, b, {isBoosting: e.isBoosting}),
                    m, p = f.hoverPoint;
                m = f.hoverPoints;
                c = (f = f.hoverSeries) && f.tooltipOptions.followPointer;
                k = k && f && !f.noSharedTooltip;
                if (p && (p !== e.hoverPoint || h && h.isHidden)) {
                    t(e.hoverPoints || [], function (b) {
                        -1 === a.inArray(b, m) && b.setState()
                    });
                    t(m || [], function (a) {
                        a.setState("hover")
                    });
                    if (e.hoverSeries !== f) f.onMouseOver();
                    e.hoverPoint && e.hoverPoint.firePointEvent("mouseOut");
                    if (!p.series) return;
                    p.firePointEvent("mouseOver");
                    e.hoverPoints = m;
                    e.hoverPoint = p;
                    h && h.refresh(k ? m : p, b)
                } else c && h && !h.isHidden && (p = h.getAnchor([{}], b), h.updatePosition({
                    plotX: p[0],
                    plotY: p[1]
                }));
                this.unDocMouseMove || (this.unDocMouseMove = B(e.container.ownerDocument, "mousemove", function (b) {
                    var c = E[a.hoverChartIndex];
                    if (c) c.pointer.onDocumentMouseMove(b)
                }));
                t(e.axes, function (c) {
                    var e = d(c.crosshair.snap, !0), h = e ? a.find(m, function (a) {
                        return a.series[c.coll] === c
                    }) : void 0;
                    h || !e ? c.drawCrosshair(b, h) : c.hideCrosshair()
                })
            },
            reset: function (a, b) {
                var c = this.chart, d = c.hoverSeries, e = c.hoverPoint, k = c.hoverPoints, p = c.tooltip,
                    f = p && p.shared ? k : e;
                a && f && t(h(f), function (b) {
                    b.series.isCartesian && void 0 === b.plotX && (a = !1)
                });
                if (a) p && f && (p.refresh(f), e && (e.setState(e.state, !0), t(c.axes, function (a) {
                    a.crosshair && a.drawCrosshair(null, e)
                }))); else {
                    if (e) e.onMouseOut();
                    k && t(k, function (a) {
                        a.setState()
                    });
                    if (d) d.onMouseOut();
                    p && p.hide(b);
                    this.unDocMouseMove && (this.unDocMouseMove = this.unDocMouseMove());
                    t(c.axes, function (a) {
                        a.hideCrosshair()
                    });
                    this.hoverX =
                        c.hoverPoints = c.hoverPoint = null
                }
            }, scaleGroups: function (a, b) {
                var c = this.chart, d;
                t(c.series, function (e) {
                    d = a || e.getPlotBox();
                    e.xAxis && e.xAxis.zoomEnabled && e.group && (e.group.attr(d), e.markerGroup && (e.markerGroup.attr(d), e.markerGroup.clip(b ? c.clipRect : null)), e.dataLabelsGroup && e.dataLabelsGroup.attr(d))
                });
                c.clipRect.attr(b || c.clipBox)
            }, dragStart: function (a) {
                var b = this.chart;
                b.mouseIsDown = a.type;
                b.cancelClick = !1;
                b.mouseDownX = this.mouseDownX = a.chartX;
                b.mouseDownY = this.mouseDownY = a.chartY
            }, drag: function (a) {
                var b =
                        this.chart, c = b.options.chart, d = a.chartX, e = a.chartY, h = this.zoomHor, k = this.zoomVert,
                    f = b.plotLeft, x = b.plotTop, m = b.plotWidth, A = b.plotHeight, l, n = this.selectionMarker,
                    g = this.mouseDownX, w = this.mouseDownY, t = c.panKey && a[c.panKey + "Key"];
                n && n.touch || (d < f ? d = f : d > f + m && (d = f + m), e < x ? e = x : e > x + A && (e = x + A), this.hasDragged = Math.sqrt(Math.pow(g - d, 2) + Math.pow(w - e, 2)), 10 < this.hasDragged && (l = b.isInsidePlot(g - f, w - x), b.hasCartesianSeries && (this.zoomX || this.zoomY) && l && !t && !n && (this.selectionMarker = n = b.renderer.rect(f, x, h ? 1 : m,
                    k ? 1 : A, 0).attr({
                    fill: c.selectionMarkerFill || q("#335cad").setOpacity(.25).get(),
                    "class": "highcharts-selection-marker",
                    zIndex: 7
                }).add()), n && h && (d -= g, n.attr({
                    width: Math.abs(d),
                    x: (0 < d ? 0 : d) + g
                })), n && k && (d = e - w, n.attr({
                    height: Math.abs(d),
                    y: (0 < d ? 0 : d) + w
                })), l && !n && c.panning && b.pan(a, c.panning)))
            }, drop: function (a) {
                var b = this, d = this.chart, e = this.hasPinched;
                if (this.selectionMarker) {
                    var h = {originalEvent: a, xAxis: [], yAxis: []}, k = this.selectionMarker,
                        m = k.attr ? k.attr("x") : k.x, C = k.attr ? k.attr("y") : k.y, x = k.attr ? k.attr("width") :
                        k.width, F = k.attr ? k.attr("height") : k.height, A;
                    if (this.hasDragged || e) t(d.axes, function (c) {
                        if (c.zoomEnabled && l(c.min) && (e || b[{xAxis: "zoomX", yAxis: "zoomY"}[c.coll]])) {
                            var d = c.horiz, g = "touchend" === a.type ? c.minPixelPadding : 0,
                                k = c.toValue((d ? m : C) + g), d = c.toValue((d ? m + x : C + F) - g);
                            h[c.coll].push({axis: c, min: Math.min(k, d), max: Math.max(k, d)});
                            A = !0
                        }
                    }), A && u(d, "selection", h, function (a) {
                        d.zoom(n(a, e ? {animation: !1} : null))
                    });
                    c(d.index) && (this.selectionMarker = this.selectionMarker.destroy());
                    e && this.scaleGroups()
                }
                d && c(d.index) &&
                (f(d.container, {cursor: d._cursor}), d.cancelClick = 10 < this.hasDragged, d.mouseIsDown = this.hasDragged = this.hasPinched = !1, this.pinchDown = [])
            }, onContainerMouseDown: function (a) {
                a = this.normalize(a);
                2 !== a.button && (this.zoomOption(a), a.preventDefault && a.preventDefault(), this.dragStart(a))
            }, onDocumentMouseUp: function (b) {
                E[a.hoverChartIndex] && E[a.hoverChartIndex].pointer.drop(b)
            }, onDocumentMouseMove: function (a) {
                var b = this.chart, c = this.chartPosition;
                a = this.normalize(a, c);
                !c || this.inClass(a.target, "highcharts-tracker") ||
                b.isInsidePlot(a.chartX - b.plotLeft, a.chartY - b.plotTop) || this.reset()
            }, onContainerMouseLeave: function (b) {
                var c = E[a.hoverChartIndex];
                c && (b.relatedTarget || b.toElement) && (c.pointer.reset(), c.pointer.chartPosition = null)
            }, onContainerMouseMove: function (b) {
                var c = this.chart;
                l(a.hoverChartIndex) && E[a.hoverChartIndex] && E[a.hoverChartIndex].mouseIsDown || (a.hoverChartIndex = c.index);
                b = this.normalize(b);
                b.returnValue = !1;
                "mousedown" === c.mouseIsDown && this.drag(b);
                !this.inClass(b.target, "highcharts-tracker") && !c.isInsidePlot(b.chartX -
                    c.plotLeft, b.chartY - c.plotTop) || c.openMenu || this.runPointActions(b)
            }, inClass: function (a, b) {
                for (var c; a;) {
                    if (c = H(a, "class")) {
                        if (-1 !== c.indexOf(b)) return !0;
                        if (-1 !== c.indexOf("highcharts-container")) return !1
                    }
                    a = a.parentNode
                }
            }, onTrackerMouseOut: function (a) {
                var b = this.chart.hoverSeries;
                a = a.relatedTarget || a.toElement;
                this.isDirectTouch = !1;
                if (!(!b || !a || b.stickyTracking || this.inClass(a, "highcharts-tooltip") || this.inClass(a, "highcharts-series-" + b.index) && this.inClass(a, "highcharts-tracker"))) b.onMouseOut()
            },
            onContainerClick: function (a) {
                var b = this.chart, c = b.hoverPoint, d = b.plotLeft, e = b.plotTop;
                a = this.normalize(a);
                b.cancelClick || (c && this.inClass(a.target, "highcharts-tracker") ? (u(c.series, "click", n(a, {point: c})), b.hoverPoint && c.firePointEvent("click", a)) : (n(a, this.getCoordinates(a)), b.isInsidePlot(a.chartX - d, a.chartY - e) && u(b, "click", a)))
            }, setDOMEvents: function () {
                var b = this, c = b.chart.container, d = c.ownerDocument;
                c.onmousedown = function (a) {
                    b.onContainerMouseDown(a)
                };
                c.onmousemove = function (a) {
                    b.onContainerMouseMove(a)
                };
                c.onclick = function (a) {
                    b.onContainerClick(a)
                };
                this.unbindContainerMouseLeave = B(c, "mouseleave", b.onContainerMouseLeave);
                a.unbindDocumentMouseUp || (a.unbindDocumentMouseUp = B(d, "mouseup", b.onDocumentMouseUp));
                a.hasTouch && (c.ontouchstart = function (a) {
                    b.onContainerTouchStart(a)
                }, c.ontouchmove = function (a) {
                    b.onContainerTouchMove(a)
                }, a.unbindDocumentTouchEnd || (a.unbindDocumentTouchEnd = B(d, "touchend", b.onDocumentTouchEnd)))
            }, destroy: function () {
                var b = this;
                b.unDocMouseMove && b.unDocMouseMove();
                this.unbindContainerMouseLeave();
                a.chartCount || (a.unbindDocumentMouseUp && (a.unbindDocumentMouseUp = a.unbindDocumentMouseUp()), a.unbindDocumentTouchEnd && (a.unbindDocumentTouchEnd = a.unbindDocumentTouchEnd()));
                clearInterval(b.tooltipTimeout);
                a.objectEach(b, function (a, c) {
                    b[c] = null
                })
            }
        }
    })(K);
    (function (a) {
        var B = a.charts, H = a.each, E = a.extend, q = a.map, f = a.noop, l = a.pick;
        E(a.Pointer.prototype, {
            pinchTranslate: function (a, f, l, q, c, b) {
                this.zoomHor && this.pinchTranslateDirection(!0, a, f, l, q, c, b);
                this.zoomVert && this.pinchTranslateDirection(!1, a, f, l, q,
                    c, b)
            }, pinchTranslateDirection: function (a, f, l, q, c, b, m, d) {
                var h = this.chart, k = a ? "x" : "y", e = a ? "X" : "Y", p = "chart" + e, r = a ? "width" : "height",
                    n = h["plot" + (a ? "Left" : "Top")], z, t, D = d || 1, C = h.inverted, x = h.bounds[a ? "h" : "v"],
                    F = 1 === f.length, A = f[0][p], J = l[0][p], G = !F && f[1][p], g = !F && l[1][p], w;
                l = function () {
                    !F && 20 < Math.abs(A - G) && (D = d || Math.abs(J - g) / Math.abs(A - G));
                    t = (n - J) / D + A;
                    z = h["plot" + (a ? "Width" : "Height")] / D
                };
                l();
                f = t;
                f < x.min ? (f = x.min, w = !0) : f + z > x.max && (f = x.max - z, w = !0);
                w ? (J -= .8 * (J - m[k][0]), F || (g -= .8 * (g - m[k][1])), l()) : m[k] =
                    [J, g];
                C || (b[k] = t - n, b[r] = z);
                b = C ? 1 / D : D;
                c[r] = z;
                c[k] = f;
                q[C ? a ? "scaleY" : "scaleX" : "scale" + e] = D;
                q["translate" + e] = b * n + (J - b * A)
            }, pinch: function (a) {
                var n = this, t = n.chart, u = n.pinchDown, c = a.touches, b = c.length, m = n.lastValidTouch,
                    d = n.hasZoom, h = n.selectionMarker, k = {},
                    e = 1 === b && (n.inClass(a.target, "highcharts-tracker") && t.runTrackerClick || n.runChartClick),
                    p = {};
                1 < b && (n.initiated = !0);
                d && n.initiated && !e && a.preventDefault();
                q(c, function (a) {
                    return n.normalize(a)
                });
                "touchstart" === a.type ? (H(c, function (a, b) {
                    u[b] = {
                        chartX: a.chartX,
                        chartY: a.chartY
                    }
                }), m.x = [u[0].chartX, u[1] && u[1].chartX], m.y = [u[0].chartY, u[1] && u[1].chartY], H(t.axes, function (a) {
                    if (a.zoomEnabled) {
                        var b = t.bounds[a.horiz ? "h" : "v"], c = a.minPixelPadding,
                            d = a.toPixels(l(a.options.min, a.dataMin)), e = a.toPixels(l(a.options.max, a.dataMax)),
                            h = Math.max(d, e);
                        b.min = Math.min(a.pos, Math.min(d, e) - c);
                        b.max = Math.max(a.pos + a.len, h + c)
                    }
                }), n.res = !0) : n.followTouchMove && 1 === b ? this.runPointActions(n.normalize(a)) : u.length && (h || (n.selectionMarker = h = E({
                    destroy: f,
                    touch: !0
                }, t.plotBox)), n.pinchTranslate(u,
                    c, k, h, p, m), n.hasPinched = d, n.scaleGroups(k, p), n.res && (n.res = !1, this.reset(!1, 0)))
            }, touch: function (f, n) {
                var t = this.chart, q, c;
                if (t.index !== a.hoverChartIndex) this.onContainerMouseLeave({relatedTarget: !0});
                a.hoverChartIndex = t.index;
                1 === f.touches.length ? (f = this.normalize(f), (c = t.isInsidePlot(f.chartX - t.plotLeft, f.chartY - t.plotTop)) && !t.openMenu ? (n && this.runPointActions(f), "touchmove" === f.type && (n = this.pinchDown, q = n[0] ? 4 <= Math.sqrt(Math.pow(n[0].chartX - f.chartX, 2) + Math.pow(n[0].chartY - f.chartY, 2)) : !1), l(q,
                    !0) && this.pinch(f)) : n && this.reset()) : 2 === f.touches.length && this.pinch(f)
            }, onContainerTouchStart: function (a) {
                this.zoomOption(a);
                this.touch(a, !0)
            }, onContainerTouchMove: function (a) {
                this.touch(a)
            }, onDocumentTouchEnd: function (f) {
                B[a.hoverChartIndex] && B[a.hoverChartIndex].pointer.drop(f)
            }
        })
    })(K);
    (function (a) {
        var B = a.addEvent, H = a.charts, E = a.css, q = a.doc, f = a.extend, l = a.noop, t = a.Pointer,
            n = a.removeEvent, v = a.win, u = a.wrap;
        if (!a.hasTouch && (v.PointerEvent || v.MSPointerEvent)) {
            var c = {}, b = !!v.PointerEvent, m = function () {
                var b =
                    [];
                b.item = function (a) {
                    return this[a]
                };
                a.objectEach(c, function (a) {
                    b.push({pageX: a.pageX, pageY: a.pageY, target: a.target})
                });
                return b
            }, d = function (b, c, d, f) {
                "touch" !== b.pointerType && b.pointerType !== b.MSPOINTER_TYPE_TOUCH || !H[a.hoverChartIndex] || (f(b), f = H[a.hoverChartIndex].pointer, f[c]({
                    type: d,
                    target: b.currentTarget,
                    preventDefault: l,
                    touches: m()
                }))
            };
            f(t.prototype, {
                onContainerPointerDown: function (a) {
                    d(a, "onContainerTouchStart", "touchstart", function (a) {
                        c[a.pointerId] = {pageX: a.pageX, pageY: a.pageY, target: a.currentTarget}
                    })
                },
                onContainerPointerMove: function (a) {
                    d(a, "onContainerTouchMove", "touchmove", function (a) {
                        c[a.pointerId] = {pageX: a.pageX, pageY: a.pageY};
                        c[a.pointerId].target || (c[a.pointerId].target = a.currentTarget)
                    })
                }, onDocumentPointerUp: function (a) {
                    d(a, "onDocumentTouchEnd", "touchend", function (a) {
                        delete c[a.pointerId]
                    })
                }, batchMSEvents: function (a) {
                    a(this.chart.container, b ? "pointerdown" : "MSPointerDown", this.onContainerPointerDown);
                    a(this.chart.container, b ? "pointermove" : "MSPointerMove", this.onContainerPointerMove);
                    a(q, b ?
                        "pointerup" : "MSPointerUp", this.onDocumentPointerUp)
                }
            });
            u(t.prototype, "init", function (a, b, c) {
                a.call(this, b, c);
                this.hasZoom && E(b.container, {"-ms-touch-action": "none", "touch-action": "none"})
            });
            u(t.prototype, "setDOMEvents", function (a) {
                a.apply(this);
                (this.hasZoom || this.followTouchMove) && this.batchMSEvents(B)
            });
            u(t.prototype, "destroy", function (a) {
                this.batchMSEvents(n);
                a.call(this)
            })
        }
    })(K);
    (function (a) {
        var B = a.addEvent, H = a.css, E = a.discardElement, q = a.defined, f = a.each, l = a.isFirefox,
            t = a.marginNames, n = a.merge,
            v = a.pick, u = a.setAnimation, c = a.stableSort, b = a.win, m = a.wrap;
        a.Legend = function (a, b) {
            this.init(a, b)
        };
        a.Legend.prototype = {
            init: function (a, b) {
                this.chart = a;
                this.setOptions(b);
                b.enabled && (this.render(), B(this.chart, "endResize", function () {
                    this.legend.positionCheckboxes()
                }))
            }, setOptions: function (a) {
                var b = v(a.padding, 8);
                this.options = a;
                this.itemStyle = a.itemStyle;
                this.itemHiddenStyle = n(this.itemStyle, a.itemHiddenStyle);
                this.itemMarginTop = a.itemMarginTop || 0;
                this.padding = b;
                this.initialItemY = b - 5;
                this.itemHeight =
                    this.maxItemWidth = 0;
                this.symbolWidth = v(a.symbolWidth, 16);
                this.pages = []
            }, update: function (a, b) {
                var c = this.chart;
                this.setOptions(n(!0, this.options, a));
                this.destroy();
                c.isDirtyLegend = c.isDirtyBox = !0;
                v(b, !0) && c.redraw()
            }, colorizeItem: function (a, b) {
                a.legendGroup[b ? "removeClass" : "addClass"]("highcharts-legend-item-hidden");
                var c = this.options, d = a.legendItem, h = a.legendLine, f = a.legendSymbol,
                    m = this.itemHiddenStyle.color, c = b ? c.itemStyle.color : m, l = b ? a.color || m : m,
                    n = a.options && a.options.marker, D = {fill: l};
                d && d.css({
                    fill: c,
                    color: c
                });
                h && h.attr({stroke: l});
                f && (n && f.isMarker && (D = a.pointAttribs(), b || (D.stroke = D.fill = m)), f.attr(D))
            }, positionItem: function (a) {
                var b = this.options, c = b.symbolPadding, b = !b.rtl, d = a._legendItemPos, f = d[0], d = d[1],
                    m = a.checkbox;
                (a = a.legendGroup) && a.element && a.translate(b ? f : this.legendWidth - f - 2 * c - 4, d);
                m && (m.x = f, m.y = d)
            }, destroyItem: function (a) {
                var b = a.checkbox;
                f(["legendItem", "legendLine", "legendSymbol", "legendGroup"], function (b) {
                    a[b] && (a[b] = a[b].destroy())
                });
                b && E(a.checkbox)
            }, destroy: function () {
                function a(a) {
                    this[a] &&
                    (this[a] = this[a].destroy())
                }

                f(this.getAllItems(), function (b) {
                    f(["legendItem", "legendGroup"], a, b)
                });
                f("clipRect up down pager nav box title group".split(" "), a, this);
                this.display = null
            }, positionCheckboxes: function () {
                var a = this.group && this.group.alignAttr, b, c = this.clipHeight || this.legendHeight,
                    e = this.titleHeight;
                a && (b = a.translateY, f(this.allItems, function (d) {
                    var h = d.checkbox, k;
                    h && (k = b + e + h.y + (this.scrollOffset || 0) + 3, H(h, {
                        left: a.translateX + d.checkboxOffset + h.x - 20 + "px",
                        top: k + "px",
                        display: k > b - 6 && k < b + c - 6 ?
                            "" : "none"
                    }))
                }, this))
            }, renderTitle: function () {
                var a = this.options, b = this.padding, c = a.title, e = 0;
                c.text && (this.title || (this.title = this.chart.renderer.label(c.text, b - 3, b - 4, null, null, null, a.useHTML, null, "legend-title").attr({zIndex: 1}).css(c.style).add(this.group)), a = this.title.getBBox(), e = a.height, this.offsetWidth = a.width, this.contentGroup.attr({translateY: e}));
                this.titleHeight = e
            }, setText: function (b) {
                var c = this.options;
                b.legendItem.attr({text: c.labelFormat ? a.format(c.labelFormat, b, this.chart.time) : c.labelFormatter.call(b)})
            },
            renderItem: function (a) {
                var b = this.chart, c = b.renderer, d = this.options, f = "horizontal" === d.layout,
                    m = this.symbolWidth, l = d.symbolPadding, z = this.itemStyle, q = this.itemHiddenStyle,
                    D = this.padding, C = f ? v(d.itemDistance, 20) : 0, x = !d.rtl, F = d.width,
                    A = d.itemMarginBottom || 0, J = this.itemMarginTop, G = a.legendItem, g = !a.series,
                    w = !g && a.series.drawLegendSymbol ? a.series : a, t = w.options,
                    u = this.createCheckboxForItem && t && t.showCheckbox, t = m + l + C + (u ? 20 : 0), N = d.useHTML,
                    O = a.options.className;
                G || (a.legendGroup = c.g("legend-item").addClass("highcharts-" +
                    w.type + "-series highcharts-color-" + a.colorIndex + (O ? " " + O : "") + (g ? " highcharts-series-" + a.index : "")).attr({zIndex: 1}).add(this.scrollGroup), a.legendItem = G = c.text("", x ? m + l : -l, this.baseline || 0, N).css(n(a.visible ? z : q)).attr({
                    align: x ? "left" : "right",
                    zIndex: 2
                }).add(a.legendGroup), this.baseline || (m = z.fontSize, this.fontMetrics = c.fontMetrics(m, G), this.baseline = this.fontMetrics.f + 3 + J, G.attr("y", this.baseline)), this.symbolHeight = d.symbolHeight || this.fontMetrics.f, w.drawLegendSymbol(this, a), this.setItemEvents &&
                this.setItemEvents(a, G, N), u && this.createCheckboxForItem(a));
                this.colorizeItem(a, a.visible);
                z.width || G.css({width: (d.itemWidth || d.width || b.spacingBox.width) - t});
                this.setText(a);
                c = G.getBBox();
                z = a.checkboxOffset = d.itemWidth || a.legendItemWidth || c.width + t;
                this.itemHeight = c = Math.round(a.legendItemHeight || c.height || this.symbolHeight);
                f && this.itemX - D + z > (F || b.spacingBox.width - 2 * D - d.x) && (this.itemX = D, this.itemY += J + this.lastLineHeight + A, this.lastLineHeight = 0);
                this.maxItemWidth = Math.max(this.maxItemWidth, z);
                this.lastItemY = J + this.itemY + A;
                this.lastLineHeight = Math.max(c, this.lastLineHeight);
                a._legendItemPos = [this.itemX, this.itemY];
                f ? this.itemX += z : (this.itemY += J + c + A, this.lastLineHeight = c);
                this.offsetWidth = F || Math.max((f ? this.itemX - D - (a.checkbox ? 0 : C) : z) + D, this.offsetWidth)
            }, getAllItems: function () {
                var a = [];
                f(this.chart.series, function (b) {
                    var c = b && b.options;
                    b && v(c.showInLegend, q(c.linkedTo) ? !1 : void 0, !0) && (a = a.concat(b.legendItems || ("point" === c.legendType ? b.data : b)))
                });
                return a
            }, getAlignment: function () {
                var a =
                    this.options;
                return a.floating ? "" : a.align.charAt(0) + a.verticalAlign.charAt(0) + a.layout.charAt(0)
            }, adjustMargins: function (a, b) {
                var c = this.chart, d = this.options, h = this.getAlignment();
                h && f([/(lth|ct|rth)/, /(rtv|rm|rbv)/, /(rbh|cb|lbh)/, /(lbv|lm|ltv)/], function (e, k) {
                    e.test(h) && !q(a[k]) && (c[t[k]] = Math.max(c[t[k]], c.legend[(k + 1) % 2 ? "legendHeight" : "legendWidth"] + [1, -1, -1, 1][k] * d[k % 2 ? "x" : "y"] + v(d.margin, 12) + b[k] + (0 === k ? c.titleOffset + c.options.title.margin : 0)))
                })
            }, render: function () {
                var a = this, b = a.chart, k = b.renderer,
                    e = a.group, m, l, t, z, q = a.box, D = a.options, C = a.padding;
                a.itemX = C;
                a.itemY = a.initialItemY;
                a.offsetWidth = 0;
                a.lastItemY = 0;
                e || (a.group = e = k.g("legend").attr({zIndex: 7}).add(), a.contentGroup = k.g().attr({zIndex: 1}).add(e), a.scrollGroup = k.g().add(a.contentGroup));
                a.renderTitle();
                m = a.getAllItems();
                c(m, function (a, b) {
                    return (a.options && a.options.legendIndex || 0) - (b.options && b.options.legendIndex || 0)
                });
                D.reversed && m.reverse();
                a.allItems = m;
                a.display = l = !!m.length;
                a.lastLineHeight = 0;
                f(m, function (b) {
                    a.renderItem(b)
                });
                t =
                    (D.width || a.offsetWidth) + C;
                z = a.lastItemY + a.lastLineHeight + a.titleHeight;
                z = a.handleOverflow(z);
                z += C;
                q || (a.box = q = k.rect().addClass("highcharts-legend-box").attr({r: D.borderRadius}).add(e), q.isNew = !0);
                q.attr({
                    stroke: D.borderColor,
                    "stroke-width": D.borderWidth || 0,
                    fill: D.backgroundColor || "none"
                }).shadow(D.shadow);
                0 < t && 0 < z && (q[q.isNew ? "attr" : "animate"](q.crisp.call({}, {
                    x: 0,
                    y: 0,
                    width: t,
                    height: z
                }, q.strokeWidth())), q.isNew = !1);
                q[l ? "show" : "hide"]();
                a.legendWidth = t;
                a.legendHeight = z;
                f(m, function (b) {
                    a.positionItem(b)
                });
                l && (k = b.spacingBox, /(lth|ct|rth)/.test(a.getAlignment()) && (k = n(k, {y: k.y + b.titleOffset + b.options.title.margin})), e.align(n(D, {
                    width: t,
                    height: z
                }), !0, k));
                b.isResizing || this.positionCheckboxes()
            }, handleOverflow: function (a) {
                var b = this, c = this.chart, d = c.renderer, m = this.options, l = m.y, n = this.padding,
                    c = c.spacingBox.height + ("top" === m.verticalAlign ? -l : l) - n, l = m.maxHeight, z,
                    q = this.clipRect, D = m.navigation, t = v(D.animation, !0), x = D.arrowSize || 12, F = this.nav,
                    A = this.pages, J, G = this.allItems, g = function (a) {
                        "number" === typeof a ?
                            q.attr({height: a}) : q && (b.clipRect = q.destroy(), b.contentGroup.clip());
                        b.contentGroup.div && (b.contentGroup.div.style.clip = a ? "rect(" + n + "px,9999px," + (n + a) + "px,0)" : "auto")
                    };
                "horizontal" !== m.layout || "middle" === m.verticalAlign || m.floating || (c /= 2);
                l && (c = Math.min(c, l));
                A.length = 0;
                a > c && !1 !== D.enabled ? (this.clipHeight = z = Math.max(c - 20 - this.titleHeight - n, 0), this.currentPage = v(this.currentPage, 1), this.fullHeight = a, f(G, function (a, b) {
                    var c = a._legendItemPos[1], d = Math.round(a.legendItem.getBBox().height), g = A.length;
                    if (!g || c - A[g - 1] > z && (J || c) !== A[g - 1]) A.push(J || c), g++;
                    a.pageIx = g - 1;
                    J && (G[b - 1].pageIx = g - 1);
                    b === G.length - 1 && c + d - A[g - 1] > z && (A.push(c), a.pageIx = g);
                    c !== J && (J = c)
                }), q || (q = b.clipRect = d.clipRect(0, n, 9999, 0), b.contentGroup.clip(q)), g(z), F || (this.nav = F = d.g().attr({zIndex: 1}).add(this.group), this.up = d.symbol("triangle", 0, 0, x, x).on("click", function () {
                    b.scroll(-1, t)
                }).add(F), this.pager = d.text("", 15, 10).addClass("highcharts-legend-navigation").css(D.style).add(F), this.down = d.symbol("triangle-down", 0, 0, x, x).on("click",
                    function () {
                        b.scroll(1, t)
                    }).add(F)), b.scroll(0), a = c) : F && (g(), this.nav = F.destroy(), this.scrollGroup.attr({translateY: 1}), this.clipHeight = 0);
                return a
            }, scroll: function (a, b) {
                var c = this.pages, d = c.length;
                a = this.currentPage + a;
                var h = this.clipHeight, f = this.options.navigation, m = this.pager, l = this.padding;
                a > d && (a = d);
                0 < a && (void 0 !== b && u(b, this.chart), this.nav.attr({
                    translateX: l,
                    translateY: h + this.padding + 7 + this.titleHeight,
                    visibility: "visible"
                }), this.up.attr({"class": 1 === a ? "highcharts-legend-nav-inactive" : "highcharts-legend-nav-active"}),
                    m.attr({text: a + "/" + d}), this.down.attr({
                    x: 18 + this.pager.getBBox().width,
                    "class": a === d ? "highcharts-legend-nav-inactive" : "highcharts-legend-nav-active"
                }), this.up.attr({fill: 1 === a ? f.inactiveColor : f.activeColor}).css({cursor: 1 === a ? "default" : "pointer"}), this.down.attr({fill: a === d ? f.inactiveColor : f.activeColor}).css({cursor: a === d ? "default" : "pointer"}), this.scrollOffset = -c[a - 1] + this.initialItemY, this.scrollGroup.animate({translateY: this.scrollOffset}), this.currentPage = a, this.positionCheckboxes())
            }
        };
        a.LegendSymbolMixin =
            {
                drawRectangle: function (a, b) {
                    var c = a.symbolHeight, d = a.options.squareSymbol;
                    b.legendSymbol = this.chart.renderer.rect(d ? (a.symbolWidth - c) / 2 : 0, a.baseline - c + 1, d ? c : a.symbolWidth, c, v(a.options.symbolRadius, c / 2)).addClass("highcharts-point").attr({zIndex: 3}).add(b.legendGroup)
                }, drawLineMarker: function (a) {
                var b = this.options, c = b.marker, d = a.symbolWidth, f = a.symbolHeight, m = f / 2,
                    l = this.chart.renderer, z = this.legendGroup;
                a = a.baseline - Math.round(.3 * a.fontMetrics.b);
                var q;
                q = {"stroke-width": b.lineWidth || 0};
                b.dashStyle &&
                (q.dashstyle = b.dashStyle);
                this.legendLine = l.path(["M", 0, a, "L", d, a]).addClass("highcharts-graph").attr(q).add(z);
                c && !1 !== c.enabled && (b = Math.min(v(c.radius, m), m), 0 === this.symbol.indexOf("url") && (c = n(c, {
                    width: f,
                    height: f
                }), b = 0), this.legendSymbol = c = l.symbol(this.symbol, d / 2 - b, a - b, 2 * b, 2 * b, c).addClass("highcharts-point").add(z), c.isMarker = !0)
            }
            };
        (/Trident\/7\.0/.test(b.navigator.userAgent) || l) && m(a.Legend.prototype, "positionItem", function (a, b) {
            var c = this, d = function () {
                b._legendItemPos && a.call(c, b)
            };
            d();
            setTimeout(d)
        })
    })(K);
    (function (a) {
        var B = a.addEvent, H = a.animate, E = a.animObject, q = a.attr, f = a.doc, l = a.Axis, t = a.createElement,
            n = a.defaultOptions, v = a.discardElement, u = a.charts, c = a.css, b = a.defined, m = a.each,
            d = a.extend, h = a.find, k = a.fireEvent, e = a.grep, p = a.isNumber, r = a.isObject, I = a.isString,
            z = a.Legend, M = a.marginNames, D = a.merge, C = a.objectEach, x = a.Pointer, F = a.pick, A = a.pInt,
            J = a.removeEvent, G = a.seriesTypes, g = a.splat, w = a.syncTimeout, L = a.win, P = a.Chart = function () {
                this.getArgs.apply(this, arguments)
            };
        a.chart = function (a, b, c) {
            return new P(a,
                b, c)
        };
        d(P.prototype, {
            callbacks: [], getArgs: function () {
                var a = [].slice.call(arguments);
                if (I(a[0]) || a[0].nodeName) this.renderTo = a.shift();
                this.init(a[0], a[1])
            }, init: function (b, c) {
                var g, d, e = b.series, h = b.plotOptions || {};
                b.series = null;
                g = D(n, b);
                for (d in g.plotOptions) g.plotOptions[d].tooltip = h[d] && D(h[d].tooltip) || void 0;
                g.tooltip.userOptions = b.chart && b.chart.forExport && b.tooltip.userOptions || b.tooltip;
                g.series = b.series = e;
                this.userOptions = b;
                d = g.chart;
                e = d.events;
                this.margin = [];
                this.spacing = [];
                this.bounds =
                    {h: {}, v: {}};
                this.labelCollectors = [];
                this.callback = c;
                this.isResizing = 0;
                this.options = g;
                this.axes = [];
                this.series = [];
                this.time = b.time && a.keys(b.time).length ? new a.Time(b.time) : a.time;
                this.hasCartesianSeries = d.showAxes;
                var k = this;
                k.index = u.length;
                u.push(k);
                a.chartCount++;
                e && C(e, function (a, b) {
                    B(k, b, a)
                });
                k.xAxis = [];
                k.yAxis = [];
                k.pointCount = k.colorCounter = k.symbolCounter = 0;
                k.firstRender()
            }, initSeries: function (b) {
                var c = this.options.chart;
                (c = G[b.type || c.type || c.defaultSeriesType]) || a.error(17, !0);
                c = new c;
                c.init(this,
                    b);
                return c
            }, orderSeries: function (a) {
                var b = this.series;
                for (a = a || 0; a < b.length; a++) b[a] && (b[a].index = a, b[a].name = b[a].getName())
            }, isInsidePlot: function (a, b, c) {
                var g = c ? b : a;
                a = c ? a : b;
                return 0 <= g && g <= this.plotWidth && 0 <= a && a <= this.plotHeight
            }, redraw: function (b) {
                var c = this.axes, g = this.series, e = this.pointer, h = this.legend, f = this.isDirtyLegend, x, l,
                    p = this.hasCartesianSeries, A = this.isDirtyBox, F, n = this.renderer, w = n.isHidden(), r = [];
                this.setResponsive && this.setResponsive(!1);
                a.setAnimation(b, this);
                w && this.temporaryDisplay();
                this.layOutTitles();
                for (b = g.length; b--;) if (F = g[b], F.options.stacking && (x = !0, F.isDirty)) {
                    l = !0;
                    break
                }
                if (l) for (b = g.length; b--;) F = g[b], F.options.stacking && (F.isDirty = !0);
                m(g, function (a) {
                    a.isDirty && "point" === a.options.legendType && (a.updateTotals && a.updateTotals(), f = !0);
                    a.isDirtyData && k(a, "updatedData")
                });
                f && h.options.enabled && (h.render(), this.isDirtyLegend = !1);
                x && this.getStacks();
                p && m(c, function (a) {
                    a.updateNames();
                    a.setScale()
                });
                this.getMargins();
                p && (m(c, function (a) {
                    a.isDirty && (A = !0)
                }), m(c, function (a) {
                    var b =
                        a.min + "," + a.max;
                    a.extKey !== b && (a.extKey = b, r.push(function () {
                        k(a, "afterSetExtremes", d(a.eventArgs, a.getExtremes()));
                        delete a.eventArgs
                    }));
                    (A || x) && a.redraw()
                }));
                A && this.drawChartBox();
                k(this, "predraw");
                m(g, function (a) {
                    (A || a.isDirty) && a.visible && a.redraw();
                    a.isDirtyData = !1
                });
                e && e.reset(!0);
                n.draw();
                k(this, "redraw");
                k(this, "render");
                w && this.temporaryDisplay(!0);
                m(r, function (a) {
                    a.call()
                })
            }, get: function (a) {
                function b(b) {
                    return b.id === a || b.options && b.options.id === a
                }

                var c, g = this.series, d;
                c = h(this.axes, b) ||
                    h(this.series, b);
                for (d = 0; !c && d < g.length; d++) c = h(g[d].points || [], b);
                return c
            }, getAxes: function () {
                var a = this, b = this.options, c = b.xAxis = g(b.xAxis || {}), b = b.yAxis = g(b.yAxis || {});
                k(this, "beforeGetAxes");
                m(c, function (a, b) {
                    a.index = b;
                    a.isX = !0
                });
                m(b, function (a, b) {
                    a.index = b
                });
                c = c.concat(b);
                m(c, function (b) {
                    new l(a, b)
                })
            }, getSelectedPoints: function () {
                var a = [];
                m(this.series, function (b) {
                    a = a.concat(e(b.data || [], function (a) {
                        return a.selected
                    }))
                });
                return a
            }, getSelectedSeries: function () {
                return e(this.series, function (a) {
                    return a.selected
                })
            },
            setTitle: function (a, b, c) {
                var g = this, d = g.options, e;
                e = d.title = D({style: {color: "#333333", fontSize: d.isStock ? "16px" : "18px"}}, d.title, a);
                d = d.subtitle = D({style: {color: "#666666"}}, d.subtitle, b);
                m([["title", a, e], ["subtitle", b, d]], function (a, b) {
                    var c = a[0], d = g[c], e = a[1];
                    a = a[2];
                    d && e && (g[c] = d = d.destroy());
                    a && !d && (g[c] = g.renderer.text(a.text, 0, 0, a.useHTML).attr({
                        align: a.align,
                        "class": "highcharts-" + c,
                        zIndex: a.zIndex || 4
                    }).add(), g[c].update = function (a) {
                        g.setTitle(!b && a, b && a)
                    }, g[c].css(a.style))
                });
                g.layOutTitles(c)
            },
            layOutTitles: function (a) {
                var b = 0, c, g = this.renderer, e = this.spacingBox;
                m(["title", "subtitle"], function (a) {
                    var c = this[a], h = this.options[a];
                    a = "title" === a ? -3 : h.verticalAlign ? 0 : b + 2;
                    var k;
                    c && (k = h.style.fontSize, k = g.fontMetrics(k, c).b, c.css({width: (h.width || e.width + h.widthAdjust) + "px"}).align(d({y: a + k}, h), !1, "spacingBox"), h.floating || h.verticalAlign || (b = Math.ceil(b + c.getBBox(h.useHTML).height)))
                }, this);
                c = this.titleOffset !== b;
                this.titleOffset = b;
                !this.isDirtyBox && c && (this.isDirtyBox = c, this.hasRendered && F(a,
                    !0) && this.isDirtyBox && this.redraw())
            }, getChartSize: function () {
                var c = this.options.chart, g = c.width, c = c.height, d = this.renderTo;
                b(g) || (this.containerWidth = a.getStyle(d, "width"));
                b(c) || (this.containerHeight = a.getStyle(d, "height"));
                this.chartWidth = Math.max(0, g || this.containerWidth || 600);
                this.chartHeight = Math.max(0, a.relativeLength(c, this.chartWidth) || (1 < this.containerHeight ? this.containerHeight : 400))
            }, temporaryDisplay: function (b) {
                var c = this.renderTo;
                if (b) for (; c && c.style;) c.hcOrigStyle && (a.css(c, c.hcOrigStyle),
                    delete c.hcOrigStyle), c.hcOrigDetached && (f.body.removeChild(c), c.hcOrigDetached = !1), c = c.parentNode; else for (; c && c.style;) {
                    f.body.contains(c) || c.parentNode || (c.hcOrigDetached = !0, f.body.appendChild(c));
                    if ("none" === a.getStyle(c, "display", !1) || c.hcOricDetached) c.hcOrigStyle = {
                        display: c.style.display,
                        height: c.style.height,
                        overflow: c.style.overflow
                    }, b = {
                        display: "block",
                        overflow: "hidden"
                    }, c !== this.renderTo && (b.height = 0), a.css(c, b), c.offsetWidth || c.style.setProperty("display", "block", "important");
                    c = c.parentNode;
                    if (c === f.body) break
                }
            }, setClassName: function (a) {
                this.container.className = "highcharts-container " + (a || "")
            }, getContainer: function () {
                var b, c = this.options, g = c.chart, e, h;
                b = this.renderTo;
                var k = a.uniqueKey(), x;
                b || (this.renderTo = b = g.renderTo);
                I(b) && (this.renderTo = b = f.getElementById(b));
                b || a.error(13, !0);
                e = A(q(b, "data-highcharts-chart"));
                p(e) && u[e] && u[e].hasRendered && u[e].destroy();
                q(b, "data-highcharts-chart", this.index);
                b.innerHTML = "";
                g.skipClone || b.offsetWidth || this.temporaryDisplay();
                this.getChartSize();
                e = this.chartWidth;
                h = this.chartHeight;
                x = d({
                    position: "relative",
                    overflow: "hidden",
                    width: e + "px",
                    height: h + "px",
                    textAlign: "left",
                    lineHeight: "normal",
                    zIndex: 0,
                    "-webkit-tap-highlight-color": "rgba(0,0,0,0)"
                }, g.style);
                this.container = b = t("div", {id: k}, x, b);
                this._cursor = b.style.cursor;
                this.renderer = new (a[g.renderer] || a.Renderer)(b, e, h, null, g.forExport, c.exporting && c.exporting.allowHTML);
                this.setClassName(g.className);
                this.renderer.setStyle(g.style);
                this.renderer.chartIndex = this.index
            }, getMargins: function (a) {
                var c =
                    this.spacing, g = this.margin, d = this.titleOffset;
                this.resetMargins();
                d && !b(g[0]) && (this.plotTop = Math.max(this.plotTop, d + this.options.title.margin + c[0]));
                this.legend && this.legend.display && this.legend.adjustMargins(g, c);
                this.extraMargin && (this[this.extraMargin.type] = (this[this.extraMargin.type] || 0) + this.extraMargin.value);
                this.adjustPlotArea && this.adjustPlotArea();
                a || this.getAxisMargins()
            }, getAxisMargins: function () {
                var a = this, c = a.axisOffset = [0, 0, 0, 0], g = a.margin;
                a.hasCartesianSeries && m(a.axes, function (a) {
                    a.visible &&
                    a.getOffset()
                });
                m(M, function (d, e) {
                    b(g[e]) || (a[d] += c[e])
                });
                a.setChartSize()
            }, reflow: function (c) {
                var g = this, d = g.options.chart, e = g.renderTo, h = b(d.width) && b(d.height),
                    k = d.width || a.getStyle(e, "width"), d = d.height || a.getStyle(e, "height"),
                    e = c ? c.target : L;
                if (!h && !g.isPrinting && k && d && (e === L || e === f)) {
                    if (k !== g.containerWidth || d !== g.containerHeight) clearTimeout(g.reflowTimeout), g.reflowTimeout = w(function () {
                        g.container && g.setSize(void 0, void 0, !1)
                    }, c ? 100 : 0);
                    g.containerWidth = k;
                    g.containerHeight = d
                }
            }, initReflow: function () {
                var a =
                    this, b;
                b = B(L, "resize", function (b) {
                    a.reflow(b)
                });
                B(a, "destroy", b)
            }, setSize: function (b, g, d) {
                var e = this, h = e.renderer;
                e.isResizing += 1;
                a.setAnimation(d, e);
                e.oldChartHeight = e.chartHeight;
                e.oldChartWidth = e.chartWidth;
                void 0 !== b && (e.options.chart.width = b);
                void 0 !== g && (e.options.chart.height = g);
                e.getChartSize();
                b = h.globalAnimation;
                (b ? H : c)(e.container, {width: e.chartWidth + "px", height: e.chartHeight + "px"}, b);
                e.setChartSize(!0);
                h.setSize(e.chartWidth, e.chartHeight, d);
                m(e.axes, function (a) {
                    a.isDirty = !0;
                    a.setScale()
                });
                e.isDirtyLegend = !0;
                e.isDirtyBox = !0;
                e.layOutTitles();
                e.getMargins();
                e.redraw(d);
                e.oldChartHeight = null;
                k(e, "resize");
                w(function () {
                    e && k(e, "endResize", null, function () {
                        --e.isResizing
                    })
                }, E(b).duration)
            }, setChartSize: function (a) {
                var b = this.inverted, c = this.renderer, g = this.chartWidth, d = this.chartHeight,
                    e = this.options.chart, h = this.spacing, k = this.clipOffset, f, x, l, p;
                this.plotLeft = f = Math.round(this.plotLeft);
                this.plotTop = x = Math.round(this.plotTop);
                this.plotWidth = l = Math.max(0, Math.round(g - f - this.marginRight));
                this.plotHeight = p = Math.max(0, Math.round(d - x - this.marginBottom));
                this.plotSizeX = b ? p : l;
                this.plotSizeY = b ? l : p;
                this.plotBorderWidth = e.plotBorderWidth || 0;
                this.spacingBox = c.spacingBox = {x: h[3], y: h[0], width: g - h[3] - h[1], height: d - h[0] - h[2]};
                this.plotBox = c.plotBox = {x: f, y: x, width: l, height: p};
                g = 2 * Math.floor(this.plotBorderWidth / 2);
                b = Math.ceil(Math.max(g, k[3]) / 2);
                c = Math.ceil(Math.max(g, k[0]) / 2);
                this.clipBox = {
                    x: b,
                    y: c,
                    width: Math.floor(this.plotSizeX - Math.max(g, k[1]) / 2 - b),
                    height: Math.max(0, Math.floor(this.plotSizeY -
                        Math.max(g, k[2]) / 2 - c))
                };
                a || m(this.axes, function (a) {
                    a.setAxisSize();
                    a.setAxisTranslation()
                })
            }, resetMargins: function () {
                var a = this, b = a.options.chart;
                m(["margin", "spacing"], function (c) {
                    var g = b[c], d = r(g) ? g : [g, g, g, g];
                    m(["Top", "Right", "Bottom", "Left"], function (g, e) {
                        a[c][e] = F(b[c + g], d[e])
                    })
                });
                m(M, function (b, c) {
                    a[b] = F(a.margin[c], a.spacing[c])
                });
                a.axisOffset = [0, 0, 0, 0];
                a.clipOffset = [0, 0, 0, 0]
            }, drawChartBox: function () {
                var a = this.options.chart, b = this.renderer, c = this.chartWidth, g = this.chartHeight,
                    d = this.chartBackground,
                    e = this.plotBackground, h = this.plotBorder, f, x = this.plotBGImage, m = a.backgroundColor,
                    l = a.plotBackgroundColor, p = a.plotBackgroundImage, A, F = this.plotLeft, n = this.plotTop,
                    w = this.plotWidth, r = this.plotHeight, z = this.plotBox, G = this.clipRect, J = this.clipBox,
                    q = "animate";
                d || (this.chartBackground = d = b.rect().addClass("highcharts-background").add(), q = "attr");
                f = a.borderWidth || 0;
                A = f + (a.shadow ? 8 : 0);
                m = {fill: m || "none"};
                if (f || d["stroke-width"]) m.stroke = a.borderColor, m["stroke-width"] = f;
                d.attr(m).shadow(a.shadow);
                d[q]({
                    x: A /
                    2, y: A / 2, width: c - A - f % 2, height: g - A - f % 2, r: a.borderRadius
                });
                q = "animate";
                e || (q = "attr", this.plotBackground = e = b.rect().addClass("highcharts-plot-background").add());
                e[q](z);
                e.attr({fill: l || "none"}).shadow(a.plotShadow);
                p && (x ? x.animate(z) : this.plotBGImage = b.image(p, F, n, w, r).add());
                G ? G.animate({width: J.width, height: J.height}) : this.clipRect = b.clipRect(J);
                q = "animate";
                h || (q = "attr", this.plotBorder = h = b.rect().addClass("highcharts-plot-border").attr({zIndex: 1}).add());
                h.attr({
                    stroke: a.plotBorderColor, "stroke-width": a.plotBorderWidth ||
                    0, fill: "none"
                });
                h[q](h.crisp({x: F, y: n, width: w, height: r}, -h.strokeWidth()));
                this.isDirtyBox = !1;
                k(this, "afterDrawChartBox")
            }, propFromSeries: function () {
                var a = this, b = a.options.chart, c, g = a.options.series, d, e;
                m(["inverted", "angular", "polar"], function (h) {
                    c = G[b.type || b.defaultSeriesType];
                    e = b[h] || c && c.prototype[h];
                    for (d = g && g.length; !e && d--;) (c = G[g[d].type]) && c.prototype[h] && (e = !0);
                    a[h] = e
                })
            }, linkSeries: function () {
                var a = this, b = a.series;
                m(b, function (a) {
                    a.linkedSeries.length = 0
                });
                m(b, function (b) {
                    var c = b.options.linkedTo;
                    I(c) && (c = ":previous" === c ? a.series[b.index - 1] : a.get(c)) && c.linkedParent !== b && (c.linkedSeries.push(b), b.linkedParent = c, b.visible = F(b.options.visible, c.options.visible, b.visible))
                })
            }, renderSeries: function () {
                m(this.series, function (a) {
                    a.translate();
                    a.render()
                })
            }, renderLabels: function () {
                var a = this, b = a.options.labels;
                b.items && m(b.items, function (c) {
                    var g = d(b.style, c.style), e = A(g.left) + a.plotLeft, h = A(g.top) + a.plotTop + 12;
                    delete g.left;
                    delete g.top;
                    a.renderer.text(c.html, e, h).attr({zIndex: 2}).css(g).add()
                })
            },
            render: function () {
                var a = this.axes, b = this.renderer, c = this.options, g, d, e;
                this.setTitle();
                this.legend = new z(this, c.legend);
                this.getStacks && this.getStacks();
                this.getMargins(!0);
                this.setChartSize();
                c = this.plotWidth;
                g = this.plotHeight = Math.max(this.plotHeight - 21, 0);
                m(a, function (a) {
                    a.setScale()
                });
                this.getAxisMargins();
                d = 1.1 < c / this.plotWidth;
                e = 1.05 < g / this.plotHeight;
                if (d || e) m(a, function (a) {
                    (a.horiz && d || !a.horiz && e) && a.setTickInterval(!0)
                }), this.getMargins();
                this.drawChartBox();
                this.hasCartesianSeries && m(a,
                    function (a) {
                        a.visible && a.render()
                    });
                this.seriesGroup || (this.seriesGroup = b.g("series-group").attr({zIndex: 3}).add());
                this.renderSeries();
                this.renderLabels();
                this.addCredits();
                this.setResponsive && this.setResponsive();
                this.hasRendered = !0
            }, addCredits: function (a) {
                var b = this;
                a = D(!0, this.options.credits, a);
                a.enabled && !this.credits && (this.credits = this.renderer.text(a.text + (this.mapCredits || ""), 0, 0).addClass("highcharts-credits").on("click", function () {
                    a.href && (L.location.href = a.href)
                }).attr({
                    align: a.position.align,
                    zIndex: 8
                }).css(a.style).add().align(a.position), this.credits.update = function (a) {
                    b.credits = b.credits.destroy();
                    b.addCredits(a)
                })
            }, destroy: function () {
                var b = this, c = b.axes, g = b.series, d = b.container, e, h = d && d.parentNode;
                k(b, "destroy");
                b.renderer.forExport ? a.erase(u, b) : u[b.index] = void 0;
                a.chartCount--;
                b.renderTo.removeAttribute("data-highcharts-chart");
                J(b);
                for (e = c.length; e--;) c[e] = c[e].destroy();
                this.scroller && this.scroller.destroy && this.scroller.destroy();
                for (e = g.length; e--;) g[e] = g[e].destroy();
                m("title subtitle chartBackground plotBackground plotBGImage plotBorder seriesGroup clipRect credits pointer rangeSelector legend resetZoomButton tooltip renderer".split(" "),
                    function (a) {
                        var c = b[a];
                        c && c.destroy && (b[a] = c.destroy())
                    });
                d && (d.innerHTML = "", J(d), h && v(d));
                C(b, function (a, c) {
                    delete b[c]
                })
            }, firstRender: function () {
                var a = this, b = a.options;
                if (!a.isReadyToRender || a.isReadyToRender()) {
                    a.getContainer();
                    k(a, "init");
                    a.resetMargins();
                    a.setChartSize();
                    a.propFromSeries();
                    a.getAxes();
                    m(b.series || [], function (b) {
                        a.initSeries(b)
                    });
                    a.linkSeries();
                    k(a, "beforeRender");
                    x && (a.pointer = new x(a, b));
                    a.render();
                    if (!a.renderer.imgCount && a.onload) a.onload();
                    a.temporaryDisplay(!0)
                }
            }, onload: function () {
                m([this.callback].concat(this.callbacks),
                    function (a) {
                        a && void 0 !== this.index && a.apply(this, [this])
                    }, this);
                k(this, "load");
                k(this, "render");
                b(this.index) && !1 !== this.options.chart.reflow && this.initReflow();
                this.onload = null
            }
        })
    })(K);
    (function (a) {
        var B, H = a.each, E = a.extend, q = a.erase, f = a.fireEvent, l = a.format, t = a.isArray, n = a.isNumber,
            v = a.pick, u = a.removeEvent;
        a.Point = B = function () {
        };
        a.Point.prototype = {
            init: function (a, b, m) {
                this.series = a;
                this.color = a.color;
                this.applyOptions(b, m);
                a.options.colorByPoint ? (b = a.options.colors || a.chart.options.colors, this.color =
                    this.color || b[a.colorCounter], b = b.length, m = a.colorCounter, a.colorCounter++, a.colorCounter === b && (a.colorCounter = 0)) : m = a.colorIndex;
                this.colorIndex = v(this.colorIndex, m);
                a.chart.pointCount++;
                f(this, "afterInit");
                return this
            }, applyOptions: function (a, b) {
                var c = this.series, d = c.options.pointValKey || c.pointValKey;
                a = B.prototype.optionsToObject.call(this, a);
                E(this, a);
                this.options = this.options ? E(this.options, a) : a;
                a.group && delete this.group;
                d && (this.y = this[d]);
                this.isNull = v(this.isValid && !this.isValid(), null ===
                    this.x || !n(this.y, !0));
                this.selected && (this.state = "select");
                "name" in this && void 0 === b && c.xAxis && c.xAxis.hasNames && (this.x = c.xAxis.nameToX(this));
                void 0 === this.x && c && (this.x = void 0 === b ? c.autoIncrement(this) : b);
                return this
            }, optionsToObject: function (a) {
                var b = {}, c = this.series, d = c.options.keys, h = d || c.pointArrayMap || ["y"], k = h.length, e = 0,
                    f = 0;
                if (n(a) || null === a) b[h[0]] = a; else if (t(a)) for (!d && a.length > k && (c = typeof a[0], "string" === c ? b.name = a[0] : "number" === c && (b.x = a[0]), e++); f < k;) d && void 0 === a[e] || (b[h[f]] = a[e]),
                    e++, f++; else "object" === typeof a && (b = a, a.dataLabels && (c._hasPointLabels = !0), a.marker && (c._hasPointMarkers = !0));
                return b
            }, getClassName: function () {
                return "highcharts-point" + (this.selected ? " highcharts-point-select" : "") + (this.negative ? " highcharts-negative" : "") + (this.isNull ? " highcharts-null-point" : "") + (void 0 !== this.colorIndex ? " highcharts-color-" + this.colorIndex : "") + (this.options.className ? " " + this.options.className : "") + (this.zone && this.zone.className ? " " + this.zone.className.replace("highcharts-negative",
                    "") : "")
            }, getZone: function () {
                var a = this.series, b = a.zones, a = a.zoneAxis || "y", f = 0, d;
                for (d = b[f]; this[a] >= d.value;) d = b[++f];
                d && d.color && !this.options.color && (this.color = d.color);
                return d
            }, destroy: function () {
                var a = this.series.chart, b = a.hoverPoints, f;
                a.pointCount--;
                b && (this.setState(), q(b, this), b.length || (a.hoverPoints = null));
                if (this === a.hoverPoint) this.onMouseOut();
                if (this.graphic || this.dataLabel) u(this), this.destroyElements();
                this.legendItem && a.legend.destroyItem(this);
                for (f in this) this[f] = null
            }, destroyElements: function () {
                for (var a =
                    ["graphic", "dataLabel", "dataLabelUpper", "connector", "shadowGroup"], b, f = 6; f--;) b = a[f], this[b] && (this[b] = this[b].destroy())
            }, getLabelConfig: function () {
                return {
                    x: this.category,
                    y: this.y,
                    color: this.color,
                    colorIndex: this.colorIndex,
                    key: this.name || this.category,
                    series: this.series,
                    point: this,
                    percentage: this.percentage,
                    total: this.total || this.stackTotal
                }
            }, tooltipFormatter: function (a) {
                var b = this.series, c = b.tooltipOptions, d = v(c.valueDecimals, ""), h = c.valuePrefix || "",
                    k = c.valueSuffix || "";
                H(b.pointArrayMap || ["y"],
                    function (b) {
                        b = "{point." + b;
                        if (h || k) a = a.replace(b + "}", h + b + "}" + k);
                        a = a.replace(b + "}", b + ":,." + d + "f}")
                    });
                return l(a, {point: this, series: this.series}, b.chart.time)
            }, firePointEvent: function (a, b, m) {
                var c = this, h = this.series.options;
                (h.point.events[a] || c.options && c.options.events && c.options.events[a]) && this.importEvents();
                "click" === a && h.allowPointSelect && (m = function (a) {
                    c.select && c.select(null, a.ctrlKey || a.metaKey || a.shiftKey)
                });
                f(this, a, b, m)
            }, visible: !0
        }
    })(K);
    (function (a) {
        var B = a.addEvent, H = a.animObject, E = a.arrayMax,
            q = a.arrayMin, f = a.correctFloat, l = a.defaultOptions, t = a.defaultPlotOptions, n = a.defined,
            v = a.each, u = a.erase, c = a.extend, b = a.fireEvent, m = a.grep, d = a.isArray, h = a.isNumber,
            k = a.isString, e = a.merge, p = a.objectEach, r = a.pick, I = a.removeEvent, z = a.splat, M = a.SVGElement,
            D = a.syncTimeout, C = a.win;
        a.Series = a.seriesType("line", null, {
            lineWidth: 2, allowPointSelect: !1, showCheckbox: !1, animation: {duration: 1E3}, events: {}, marker: {
                lineWidth: 0, lineColor: "#ffffff", enabledThreshold: 2, radius: 4, states: {
                    normal: {animation: !0}, hover: {
                        animation: {duration: 50},
                        enabled: !0, radiusPlus: 2, lineWidthPlus: 1
                    }, select: {fillColor: "#cccccc", lineColor: "#000000", lineWidth: 2}
                }
            }, point: {events: {}}, dataLabels: {
                align: "center",
                formatter: function () {
                    return null === this.y ? "" : a.numberFormat(this.y, -1)
                },
                style: {fontSize: "11px", fontWeight: "bold", color: "contrast", textOutline: "1px contrast"},
                verticalAlign: "bottom",
                x: 0,
                y: 0,
                padding: 5
            }, cropThreshold: 300, pointRange: 0, softThreshold: !0, states: {
                normal: {animation: !0},
                hover: {animation: {duration: 50}, lineWidthPlus: 1, marker: {}, halo: {size: 10, opacity: .25}},
                select: {marker: {}}
            }, stickyTracking: !0, turboThreshold: 1E3, findNearestPointBy: "x"
        }, {
            isCartesian: !0,
            pointClass: a.Point,
            sorted: !0,
            requireSorting: !0,
            directTouch: !1,
            axisTypes: ["xAxis", "yAxis"],
            colorCounter: 0,
            parallelArrays: ["x", "y"],
            coll: "series",
            init: function (a, b) {
                var d = this, e, h = a.series, g;
                d.chart = a;
                d.options = b = d.setOptions(b);
                d.linkedSeries = [];
                d.bindAxes();
                c(d, {name: b.name, state: "", visible: !1 !== b.visible, selected: !0 === b.selected});
                e = b.events;
                p(e, function (a, b) {
                    B(d, b, a)
                });
                if (e && e.click || b.point && b.point.events &&
                    b.point.events.click || b.allowPointSelect) a.runTrackerClick = !0;
                d.getColor();
                d.getSymbol();
                v(d.parallelArrays, function (a) {
                    d[a + "Data"] = []
                });
                d.setData(b.data, !1);
                d.isCartesian && (a.hasCartesianSeries = !0);
                h.length && (g = h[h.length - 1]);
                d._i = r(g && g._i, -1) + 1;
                a.orderSeries(this.insert(h))
            },
            insert: function (a) {
                var b = this.options.index, c;
                if (h(b)) {
                    for (c = a.length; c--;) if (b >= r(a[c].options.index, a[c]._i)) {
                        a.splice(c + 1, 0, this);
                        break
                    }
                    -1 === c && a.unshift(this);
                    c += 1
                } else a.push(this);
                return r(c, a.length - 1)
            },
            bindAxes: function () {
                var b =
                    this, c = b.options, d = b.chart, e;
                v(b.axisTypes || [], function (h) {
                    v(d[h], function (a) {
                        e = a.options;
                        if (c[h] === e.index || void 0 !== c[h] && c[h] === e.id || void 0 === c[h] && 0 === e.index) b.insert(a.series), b[h] = a, a.isDirty = !0
                    });
                    b[h] || b.optionalAxis === h || a.error(18, !0)
                })
            },
            updateParallelArrays: function (a, b) {
                var c = a.series, d = arguments, e = h(b) ? function (g) {
                    var d = "y" === g && c.toYData ? c.toYData(a) : a[g];
                    c[g + "Data"][b] = d
                } : function (a) {
                    Array.prototype[b].apply(c[a + "Data"], Array.prototype.slice.call(d, 2))
                };
                v(c.parallelArrays, e)
            },
            autoIncrement: function () {
                var a =
                        this.options, b = this.xIncrement, c, d = a.pointIntervalUnit, e = this.chart.time,
                    b = r(b, a.pointStart, 0);
                this.pointInterval = c = r(this.pointInterval, a.pointInterval, 1);
                d && (a = new e.Date(b), "day" === d ? e.set("Date", a, e.get("Date", a) + c) : "month" === d ? e.set("Month", a, e.get("Month", a) + c) : "year" === d && e.set("FullYear", a, e.get("FullYear", a) + c), c = a.getTime() - b);
                this.xIncrement = b + c;
                return b
            },
            setOptions: function (a) {
                var b = this.chart, c = b.options, d = c.plotOptions, h = (b.userOptions || {}).plotOptions || {},
                    g = d[this.type];
                this.userOptions =
                    a;
                b = e(g, d.series, a);
                this.tooltipOptions = e(l.tooltip, l.plotOptions.series && l.plotOptions.series.tooltip, l.plotOptions[this.type].tooltip, c.tooltip.userOptions, d.series && d.series.tooltip, d[this.type].tooltip, a.tooltip);
                this.stickyTracking = r(a.stickyTracking, h[this.type] && h[this.type].stickyTracking, h.series && h.series.stickyTracking, this.tooltipOptions.shared && !this.noSharedTooltip ? !0 : b.stickyTracking);
                null === g.marker && delete b.marker;
                this.zoneAxis = b.zoneAxis;
                a = this.zones = (b.zones || []).slice();
                !b.negativeColor &&
                !b.negativeFillColor || b.zones || a.push({
                    value: b[this.zoneAxis + "Threshold"] || b.threshold || 0,
                    className: "highcharts-negative",
                    color: b.negativeColor,
                    fillColor: b.negativeFillColor
                });
                a.length && n(a[a.length - 1].value) && a.push({color: this.color, fillColor: this.fillColor});
                return b
            },
            getName: function () {
                return this.name || "Series " + (this.index + 1)
            },
            getCyclic: function (a, b, c) {
                var d, e = this.chart, g = this.userOptions, h = a + "Index", k = a + "Counter",
                    f = c ? c.length : r(e.options.chart[a + "Count"], e[a + "Count"]);
                b || (d = r(g[h], g["_" + h]),
                n(d) || (e.series.length || (e[k] = 0), g["_" + h] = d = e[k] % f, e[k] += 1), c && (b = c[d]));
                void 0 !== d && (this[h] = d);
                this[a] = b
            },
            getColor: function () {
                this.options.colorByPoint ? this.options.color = null : this.getCyclic("color", this.options.color || t[this.type].color, this.chart.options.colors)
            },
            getSymbol: function () {
                this.getCyclic("symbol", this.options.marker.symbol, this.chart.options.symbols)
            },
            drawLegendSymbol: a.LegendSymbolMixin.drawLineMarker,
            setData: function (b, c, e, f) {
                var m = this, g = m.points, l = g && g.length || 0, x, p = m.options, F =
                        m.chart, n = null, A = m.xAxis, z = p.turboThreshold, q = this.xData, D = this.yData,
                    t = (x = m.pointArrayMap) && x.length;
                b = b || [];
                x = b.length;
                c = r(c, !0);
                if (!1 !== f && x && l === x && !m.cropped && !m.hasGroupedData && m.visible) v(b, function (a, b) {
                    g[b].update && a !== p.data[b] && g[b].update(a, !1, null, !1)
                }); else {
                    m.xIncrement = null;
                    m.colorCounter = 0;
                    v(this.parallelArrays, function (a) {
                        m[a + "Data"].length = 0
                    });
                    if (z && x > z) {
                        for (e = 0; null === n && e < x;) n = b[e], e++;
                        if (h(n)) for (e = 0; e < x; e++) q[e] = this.autoIncrement(), D[e] = b[e]; else if (d(n)) if (t) for (e = 0; e < x; e++) n =
                            b[e], q[e] = n[0], D[e] = n.slice(1, t + 1); else for (e = 0; e < x; e++) n = b[e], q[e] = n[0], D[e] = n[1]; else a.error(12)
                    } else for (e = 0; e < x; e++) void 0 !== b[e] && (n = {series: m}, m.pointClass.prototype.applyOptions.apply(n, [b[e]]), m.updateParallelArrays(n, e));
                    D && k(D[0]) && a.error(14, !0);
                    m.data = [];
                    m.options.data = m.userOptions.data = b;
                    for (e = l; e--;) g[e] && g[e].destroy && g[e].destroy();
                    A && (A.minRange = A.userMinRange);
                    m.isDirty = F.isDirtyBox = !0;
                    m.isDirtyData = !!g;
                    e = !1
                }
                "point" === p.legendType && (this.processData(), this.generatePoints());
                c &&
                F.redraw(e)
            },
            processData: function (b) {
                var c = this.xData, d = this.yData, e = c.length, h;
                h = 0;
                var g, k, f = this.xAxis, m, l = this.options;
                m = l.cropThreshold;
                var x = this.getExtremesFromAll || l.getExtremesFromAll, p = this.isCartesian, l = f && f.val2lin,
                    n = f && f.isLog, r = this.requireSorting, z, q;
                if (p && !this.isDirty && !f.isDirty && !this.yAxis.isDirty && !b) return !1;
                f && (b = f.getExtremes(), z = b.min, q = b.max);
                if (p && this.sorted && !x && (!m || e > m || this.forceCrop)) if (c[e - 1] < z || c[0] > q) c = [], d = []; else if (c[0] < z || c[e - 1] > q) h = this.cropData(this.xData,
                    this.yData, z, q), c = h.xData, d = h.yData, h = h.start, g = !0;
                for (m = c.length || 1; --m;) e = n ? l(c[m]) - l(c[m - 1]) : c[m] - c[m - 1], 0 < e && (void 0 === k || e < k) ? k = e : 0 > e && r && (a.error(15), r = !1);
                this.cropped = g;
                this.cropStart = h;
                this.processedXData = c;
                this.processedYData = d;
                this.closestPointRange = k
            },
            cropData: function (a, b, c, d) {
                var e = a.length, g = 0, h = e, k = r(this.cropShoulder, 1), f;
                for (f = 0; f < e; f++) if (a[f] >= c) {
                    g = Math.max(0, f - k);
                    break
                }
                for (c = f; c < e; c++) if (a[c] > d) {
                    h = c + k;
                    break
                }
                return {xData: a.slice(g, h), yData: b.slice(g, h), start: g, end: h}
            },
            generatePoints: function () {
                var a =
                        this.options, b = a.data, c = this.data, d, e = this.processedXData, g = this.processedYData,
                    h = this.pointClass, k = e.length, f = this.cropStart || 0, m, l = this.hasGroupedData, a = a.keys,
                    p, n = [], r;
                c || l || (c = [], c.length = b.length, c = this.data = c);
                a && l && (this.options.keys = !1);
                for (r = 0; r < k; r++) m = f + r, l ? (p = (new h).init(this, [e[r]].concat(z(g[r]))), p.dataGroup = this.groupMap[r]) : (p = c[m]) || void 0 === b[m] || (c[m] = p = (new h).init(this, b[m], e[r])), p && (p.index = m, n[r] = p);
                this.options.keys = a;
                if (c && (k !== (d = c.length) || l)) for (r = 0; r < d; r++) r !== f || l ||
                (r += k), c[r] && (c[r].destroyElements(), c[r].plotX = void 0);
                this.data = c;
                this.points = n
            },
            getExtremes: function (a) {
                var b = this.yAxis, c = this.processedXData, e, k = [], g = 0;
                e = this.xAxis.getExtremes();
                var f = e.min, m = e.max, l, p, x, n;
                a = a || this.stackedYData || this.processedYData || [];
                e = a.length;
                for (n = 0; n < e; n++) if (p = c[n], x = a[n], l = (h(x, !0) || d(x)) && (!b.positiveValuesOnly || x.length || 0 < x), p = this.getExtremesFromAll || this.options.getExtremesFromAll || this.cropped || (c[n + 1] || p) >= f && (c[n - 1] || p) <= m, l && p) if (l = x.length) for (; l--;) "number" ===
                typeof x[l] && (k[g++] = x[l]); else k[g++] = x;
                this.dataMin = q(k);
                this.dataMax = E(k)
            },
            translate: function () {
                this.processedXData || this.processData();
                this.generatePoints();
                var a = this.options, c = a.stacking, e = this.xAxis, d = e.categories, k = this.yAxis, g = this.points,
                    m = g.length, l = !!this.modifyValue, p = a.pointPlacement, z = "between" === p || h(p),
                    q = a.threshold, D = a.startFromThreshold ? q : 0, t, C, v, u, M = Number.MAX_VALUE;
                "between" === p && (p = .5);
                h(p) && (p *= r(a.pointRange || e.pointRange));
                for (a = 0; a < m; a++) {
                    var I = g[a], B = I.x, E = I.y;
                    C = I.low;
                    var H =
                        c && k.stacks[(this.negStacks && E < (D ? 0 : q) ? "-" : "") + this.stackKey], K;
                    k.positiveValuesOnly && null !== E && 0 >= E && (I.isNull = !0);
                    I.plotX = t = f(Math.min(Math.max(-1E5, e.translate(B, 0, 0, 0, 1, p, "flags" === this.type)), 1E5));
                    c && this.visible && !I.isNull && H && H[B] && (u = this.getStackIndicator(u, B, this.index), K = H[B], E = K.points[u.key], C = E[0], E = E[1], C === D && u.key === H[B].base && (C = r(q, k.min)), k.positiveValuesOnly && 0 >= C && (C = null), I.total = I.stackTotal = K.total, I.percentage = K.total && I.y / K.total * 100, I.stackY = E, K.setOffset(this.pointXOffset ||
                        0, this.barW || 0));
                    I.yBottom = n(C) ? Math.min(Math.max(-1E5, k.translate(C, 0, 1, 0, 1)), 1E5) : null;
                    l && (E = this.modifyValue(E, I));
                    I.plotY = C = "number" === typeof E && Infinity !== E ? Math.min(Math.max(-1E5, k.translate(E, 0, 1, 0, 1)), 1E5) : void 0;
                    I.isInside = void 0 !== C && 0 <= C && C <= k.len && 0 <= t && t <= e.len;
                    I.clientX = z ? f(e.translate(B, 0, 0, 0, 1, p)) : t;
                    I.negative = I.y < (q || 0);
                    I.category = d && void 0 !== d[I.x] ? d[I.x] : I.x;
                    I.isNull || (void 0 !== v && (M = Math.min(M, Math.abs(t - v))), v = t);
                    I.zone = this.zones.length && I.getZone()
                }
                this.closestPointRangePx =
                    M;
                b(this, "afterTranslate")
            },
            getValidPoints: function (a, b) {
                var c = this.chart;
                return m(a || this.points || [], function (a) {
                    return b && !c.isInsidePlot(a.plotX, a.plotY, c.inverted) ? !1 : !a.isNull
                })
            },
            setClip: function (a) {
                var b = this.chart, c = this.options, e = b.renderer, d = b.inverted, g = this.clipBox,
                    h = g || b.clipBox,
                    k = this.sharedClipKey || ["_sharedClip", a && a.duration, a && a.easing, h.height, c.xAxis, c.yAxis].join(),
                    f = b[k], m = b[k + "m"];
                f || (a && (h.width = 0, d && (h.x = b.plotSizeX), b[k + "m"] = m = e.clipRect(d ? b.plotSizeX + 99 : -99, d ? -b.plotLeft :
                    -b.plotTop, 99, d ? b.chartWidth : b.chartHeight)), b[k] = f = e.clipRect(h), f.count = {length: 0});
                a && !f.count[this.index] && (f.count[this.index] = !0, f.count.length += 1);
                !1 !== c.clip && (this.group.clip(a || g ? f : b.clipRect), this.markerGroup.clip(m), this.sharedClipKey = k);
                a || (f.count[this.index] && (delete f.count[this.index], --f.count.length), 0 === f.count.length && k && b[k] && (g || (b[k] = b[k].destroy()), b[k + "m"] && (b[k + "m"] = b[k + "m"].destroy())))
            },
            animate: function (a) {
                var b = this.chart, c = H(this.options.animation), e;
                a ? this.setClip(c) :
                    (e = this.sharedClipKey, (a = b[e]) && a.animate({
                        width: b.plotSizeX,
                        x: 0
                    }, c), b[e + "m"] && b[e + "m"].animate({width: b.plotSizeX + 99, x: 0}, c), this.animate = null)
            },
            afterAnimate: function () {
                this.setClip();
                b(this, "afterAnimate");
                this.finishedAnimating = !0
            },
            drawPoints: function () {
                var a = this.points, b = this.chart, c, e, d, g, h = this.options.marker, k, f, m,
                    l = this[this.specialGroup] || this.markerGroup, p,
                    n = r(h.enabled, this.xAxis.isRadial ? !0 : null, this.closestPointRangePx >= h.enabledThreshold * h.radius);
                if (!1 !== h.enabled || this._hasPointMarkers) for (c =
                                                                        0; c < a.length; c++) e = a[c], g = e.graphic, k = e.marker || {}, f = !!e.marker, d = n && void 0 === k.enabled || k.enabled, m = e.isInside, d && !e.isNull ? (d = r(k.symbol, this.symbol), p = this.markerAttribs(e, e.selected && "select"), g ? g[m ? "show" : "hide"](!0).animate(p) : m && (0 < p.width || e.hasImage) && (e.graphic = g = b.renderer.symbol(d, p.x, p.y, p.width, p.height, f ? k : h).add(l)), g && g.attr(this.pointAttribs(e, e.selected && "select")), g && g.addClass(e.getClassName(), !0)) : g && (e.graphic = g.destroy())
            },
            markerAttribs: function (a, b) {
                var c = this.options.marker,
                    e = a.marker || {}, d = e.symbol || c.symbol, g = r(e.radius, c.radius);
                b && (c = c.states[b], b = e.states && e.states[b], g = r(b && b.radius, c && c.radius, g + (c && c.radiusPlus || 0)));
                a.hasImage = d && 0 === d.indexOf("url");
                a.hasImage && (g = 0);
                a = {x: Math.floor(a.plotX) - g, y: a.plotY - g};
                g && (a.width = a.height = 2 * g);
                return a
            },
            pointAttribs: function (a, b) {
                var c = this.options.marker, e = a && a.options, d = e && e.marker || {}, g = this.color,
                    h = e && e.color, k = a && a.color, e = r(d.lineWidth, c.lineWidth);
                a = a && a.zone && a.zone.color;
                g = h || a || k || g;
                a = d.fillColor || c.fillColor ||
                    g;
                g = d.lineColor || c.lineColor || g;
                b && (c = c.states[b], b = d.states && d.states[b] || {}, e = r(b.lineWidth, c.lineWidth, e + r(b.lineWidthPlus, c.lineWidthPlus, 0)), a = b.fillColor || c.fillColor || a, g = b.lineColor || c.lineColor || g);
                return {stroke: g, "stroke-width": e, fill: a}
            },
            destroy: function () {
                var a = this, c = a.chart, e = /AppleWebKit\/533/.test(C.navigator.userAgent), d, h, g = a.data || [],
                    k, f;
                b(a, "destroy");
                I(a);
                v(a.axisTypes || [], function (b) {
                    (f = a[b]) && f.series && (u(f.series, a), f.isDirty = f.forceRedraw = !0)
                });
                a.legendItem && a.chart.legend.destroyItem(a);
                for (h = g.length; h--;) (k = g[h]) && k.destroy && k.destroy();
                a.points = null;
                clearTimeout(a.animationTimeout);
                p(a, function (a, b) {
                    a instanceof M && !a.survive && (d = e && "group" === b ? "hide" : "destroy", a[d]())
                });
                c.hoverSeries === a && (c.hoverSeries = null);
                u(c.series, a);
                c.orderSeries();
                p(a, function (b, c) {
                    delete a[c]
                })
            },
            getGraphPath: function (a, b, c) {
                var e = this, d = e.options, g = d.step, h, k = [], f = [], m;
                a = a || e.points;
                (h = a.reversed) && a.reverse();
                (g = {right: 1, center: 2}[g] || g && 3) && h && (g = 4 - g);
                !d.connectNulls || b || c || (a = this.getValidPoints(a));
                v(a, function (h, l) {
                    var p = h.plotX, x = h.plotY, r = a[l - 1];
                    (h.leftCliff || r && r.rightCliff) && !c && (m = !0);
                    h.isNull && !n(b) && 0 < l ? m = !d.connectNulls : h.isNull && !b ? m = !0 : (0 === l || m ? l = ["M", h.plotX, h.plotY] : e.getPointSpline ? l = e.getPointSpline(a, h, l) : g ? (l = 1 === g ? ["L", r.plotX, x] : 2 === g ? ["L", (r.plotX + p) / 2, r.plotY, "L", (r.plotX + p) / 2, x] : ["L", p, r.plotY], l.push("L", p, x)) : l = ["L", p, x], f.push(h.x), g && f.push(h.x), k.push.apply(k, l), m = !1)
                });
                k.xMap = f;
                return e.graphPath = k
            },
            drawGraph: function () {
                var a = this, b = this.options, c = (this.gappedPath ||
                    this.getGraphPath).call(this),
                    e = [["graph", "highcharts-graph", b.lineColor || this.color, b.dashStyle]];
                v(this.zones, function (c, d) {
                    e.push(["zone-graph-" + d, "highcharts-graph highcharts-zone-graph-" + d + " " + (c.className || ""), c.color || a.color, c.dashStyle || b.dashStyle])
                });
                v(e, function (e, d) {
                    var g = e[0], h = a[g];
                    h ? (h.endX = a.preventGraphAnimation ? null : c.xMap, h.animate({d: c})) : c.length && (a[g] = a.chart.renderer.path(c).addClass(e[1]).attr({zIndex: 1}).add(a.group), h = {
                        stroke: e[2], "stroke-width": b.lineWidth, fill: a.fillGraph &&
                        a.color || "none"
                    }, e[3] ? h.dashstyle = e[3] : "square" !== b.linecap && (h["stroke-linecap"] = h["stroke-linejoin"] = "round"), h = a[g].attr(h).shadow(2 > d && b.shadow));
                    h && (h.startX = c.xMap, h.isArea = c.isArea)
                })
            },
            applyZones: function () {
                var a = this, b = this.chart, c = b.renderer, e = this.zones, d, g, h = this.clips || [], k,
                    f = this.graph, m = this.area, l = Math.max(b.chartWidth, b.chartHeight),
                    p = this[(this.zoneAxis || "y") + "Axis"], n, z, q = b.inverted, D, t, C, u, M = !1;
                e.length && (f || m) && p && void 0 !== p.min && (z = p.reversed, D = p.horiz, f && f.hide(), m && m.hide(),
                    n = p.getExtremes(), v(e, function (e, x) {
                    d = z ? D ? b.plotWidth : 0 : D ? 0 : p.toPixels(n.min);
                    d = Math.min(Math.max(r(g, d), 0), l);
                    g = Math.min(Math.max(Math.round(p.toPixels(r(e.value, n.max), !0)), 0), l);
                    M && (d = g = p.toPixels(n.max));
                    t = Math.abs(d - g);
                    C = Math.min(d, g);
                    u = Math.max(d, g);
                    p.isXAxis ? (k = {
                        x: q ? u : C,
                        y: 0,
                        width: t,
                        height: l
                    }, D || (k.x = b.plotHeight - k.x)) : (k = {
                        x: 0,
                        y: q ? u : C,
                        width: l,
                        height: t
                    }, D && (k.y = b.plotWidth - k.y));
                    q && c.isVML && (k = p.isXAxis ? {x: 0, y: z ? C : u, height: k.width, width: b.chartWidth} : {
                        x: k.y - b.plotLeft - b.spacingBox.x, y: 0, width: k.height,
                        height: b.chartHeight
                    });
                    h[x] ? h[x].animate(k) : (h[x] = c.clipRect(k), f && a["zone-graph-" + x].clip(h[x]), m && a["zone-area-" + x].clip(h[x]));
                    M = e.value > n.max
                }), this.clips = h)
            },
            invertGroups: function (a) {
                function b() {
                    v(["group", "markerGroup"], function (b) {
                        c[b] && (e.renderer.isVML && c[b].attr({
                            width: c.yAxis.len,
                            height: c.xAxis.len
                        }), c[b].width = c.yAxis.len, c[b].height = c.xAxis.len, c[b].invert(a))
                    })
                }

                var c = this, e = c.chart, d;
                c.xAxis && (d = B(e, "resize", b), B(c, "destroy", d), b(a), c.invertGroups = b)
            },
            plotGroup: function (a, b, c, e, d) {
                var g =
                    this[a], h = !g;
                h && (this[a] = g = this.chart.renderer.g().attr({zIndex: e || .1}).add(d));
                g.addClass("highcharts-" + b + " highcharts-series-" + this.index + " highcharts-" + this.type + "-series " + (n(this.colorIndex) ? "highcharts-color-" + this.colorIndex + " " : "") + (this.options.className || "") + (g.hasClass("highcharts-tracker") ? " highcharts-tracker" : ""), !0);
                g.attr({visibility: c})[h ? "attr" : "animate"](this.getPlotBox());
                return g
            },
            getPlotBox: function () {
                var a = this.chart, b = this.xAxis, c = this.yAxis;
                a.inverted && (b = c, c = this.xAxis);
                return {translateX: b ? b.left : a.plotLeft, translateY: c ? c.top : a.plotTop, scaleX: 1, scaleY: 1}
            },
            render: function () {
                var a = this, c = a.chart, e, d = a.options,
                    h = !!a.animate && c.renderer.isSVG && H(d.animation).duration,
                    g = a.visible ? "inherit" : "hidden", k = d.zIndex, f = a.hasRendered, m = c.seriesGroup,
                    l = c.inverted;
                e = a.plotGroup("group", "series", g, k, m);
                a.markerGroup = a.plotGroup("markerGroup", "markers", g, k, m);
                h && a.animate(!0);
                e.inverted = a.isCartesian ? l : !1;
                a.drawGraph && (a.drawGraph(), a.applyZones());
                a.drawDataLabels && a.drawDataLabels();
                a.visible && a.drawPoints();
                a.drawTracker && !1 !== a.options.enableMouseTracking && a.drawTracker();
                a.invertGroups(l);
                !1 === d.clip || a.sharedClipKey || f || e.clip(c.clipRect);
                h && a.animate();
                f || (a.animationTimeout = D(function () {
                    a.afterAnimate()
                }, h));
                a.isDirty = !1;
                a.hasRendered = !0;
                b(a, "afterRender")
            },
            redraw: function () {
                var a = this.chart, b = this.isDirty || this.isDirtyData, c = this.group, e = this.xAxis,
                    d = this.yAxis;
                c && (a.inverted && c.attr({width: a.plotWidth, height: a.plotHeight}), c.animate({
                    translateX: r(e && e.left, a.plotLeft),
                    translateY: r(d && d.top, a.plotTop)
                }));
                this.translate();
                this.render();
                b && delete this.kdTree
            },
            kdAxisArray: ["clientX", "plotY"],
            searchPoint: function (a, b) {
                var c = this.xAxis, e = this.yAxis, d = this.chart.inverted;
                return this.searchKDTree({
                    clientX: d ? c.len - a.chartY + c.pos : a.chartX - c.pos,
                    plotY: d ? e.len - a.chartX + e.pos : a.chartY - e.pos
                }, b)
            },
            buildKDTree: function () {
                function a(c, e, d) {
                    var g, h;
                    if (h = c && c.length) return g = b.kdAxisArray[e % d], c.sort(function (a, b) {
                        return a[g] - b[g]
                    }), h = Math.floor(h / 2), {
                        point: c[h], left: a(c.slice(0,
                            h), e + 1, d), right: a(c.slice(h + 1), e + 1, d)
                    }
                }

                this.buildingKdTree = !0;
                var b = this, c = -1 < b.options.findNearestPointBy.indexOf("y") ? 2 : 1;
                delete b.kdTree;
                D(function () {
                    b.kdTree = a(b.getValidPoints(null, !b.directTouch), c, c);
                    b.buildingKdTree = !1
                }, b.options.kdNow ? 0 : 1)
            },
            searchKDTree: function (a, b) {
                function c(a, b, k, f) {
                    var m = b.point, l = e.kdAxisArray[k % f], p, r, z = m;
                    r = n(a[d]) && n(m[d]) ? Math.pow(a[d] - m[d], 2) : null;
                    p = n(a[g]) && n(m[g]) ? Math.pow(a[g] - m[g], 2) : null;
                    p = (r || 0) + (p || 0);
                    m.dist = n(p) ? Math.sqrt(p) : Number.MAX_VALUE;
                    m.distX = n(r) ?
                        Math.sqrt(r) : Number.MAX_VALUE;
                    l = a[l] - m[l];
                    p = 0 > l ? "left" : "right";
                    r = 0 > l ? "right" : "left";
                    b[p] && (p = c(a, b[p], k + 1, f), z = p[h] < z[h] ? p : m);
                    b[r] && Math.sqrt(l * l) < z[h] && (a = c(a, b[r], k + 1, f), z = a[h] < z[h] ? a : z);
                    return z
                }

                var e = this, d = this.kdAxisArray[0], g = this.kdAxisArray[1], h = b ? "distX" : "dist";
                b = -1 < e.options.findNearestPointBy.indexOf("y") ? 2 : 1;
                this.kdTree || this.buildingKdTree || this.buildKDTree();
                if (this.kdTree) return c(a, this.kdTree, b, b)
            }
        })
    })(K);
    (function (a) {
        var B = a.Axis, H = a.Chart, E = a.correctFloat, q = a.defined, f = a.destroyObjectProperties,
            l = a.each, t = a.format, n = a.objectEach, v = a.pick, u = a.Series;
        a.StackItem = function (a, b, f, d, h) {
            var c = a.chart.inverted;
            this.axis = a;
            this.isNegative = f;
            this.options = b;
            this.x = d;
            this.total = null;
            this.points = {};
            this.stack = h;
            this.rightCliff = this.leftCliff = 0;
            this.alignOptions = {
                align: b.align || (c ? f ? "left" : "right" : "center"),
                verticalAlign: b.verticalAlign || (c ? "middle" : f ? "bottom" : "top"),
                y: v(b.y, c ? 4 : f ? 14 : -6),
                x: v(b.x, c ? f ? -6 : 6 : 0)
            };
            this.textAlign = b.textAlign || (c ? f ? "right" : "left" : "center")
        };
        a.StackItem.prototype = {
            destroy: function () {
                f(this,
                    this.axis)
            }, render: function (a) {
                var b = this.axis.chart, c = this.options, d = c.format,
                    d = d ? t(d, this, b.time) : c.formatter.call(this);
                this.label ? this.label.attr({
                    text: d,
                    visibility: "hidden"
                }) : this.label = b.renderer.text(d, null, null, c.useHTML).css(c.style).attr({
                    align: this.textAlign,
                    rotation: c.rotation,
                    visibility: "hidden"
                }).add(a)
            }, setOffset: function (a, b) {
                var c = this.axis, d = c.chart, h = c.translate(c.usePercentage ? 100 : this.total, 0, 0, 0, 1),
                    c = c.translate(0), c = Math.abs(h - c);
                a = d.xAxis[0].translate(this.x) + a;
                h = this.getStackBox(d,
                    this, a, h, b, c);
                if (b = this.label) b.align(this.alignOptions, null, h), h = b.alignAttr, b[!1 === this.options.crop || d.isInsidePlot(h.x, h.y) ? "show" : "hide"](!0)
            }, getStackBox: function (a, b, f, d, h, k) {
                var c = b.axis.reversed, m = a.inverted;
                a = a.plotHeight;
                b = b.isNegative && !c || !b.isNegative && c;
                return {
                    x: m ? b ? d : d - k : f,
                    y: m ? a - f - h : b ? a - d - k : a - d,
                    width: m ? k : h,
                    height: m ? h : k
                }
            }
        };
        H.prototype.getStacks = function () {
            var a = this;
            l(a.yAxis, function (a) {
                a.stacks && a.hasVisibleSeries && (a.oldStacks = a.stacks)
            });
            l(a.series, function (b) {
                !b.options.stacking ||
                !0 !== b.visible && !1 !== a.options.chart.ignoreHiddenSeries || (b.stackKey = b.type + v(b.options.stack, ""))
            })
        };
        B.prototype.buildStacks = function () {
            var a = this.series, b = v(this.options.reversedStacks, !0), f = a.length, d;
            if (!this.isXAxis) {
                this.usePercentage = !1;
                for (d = f; d--;) a[b ? d : f - d - 1].setStackedPoints();
                for (d = 0; d < f; d++) a[d].modifyStacks()
            }
        };
        B.prototype.renderStackTotals = function () {
            var a = this.chart, b = a.renderer, f = this.stacks, d = this.stackTotalGroup;
            d || (this.stackTotalGroup = d = b.g("stack-labels").attr({
                visibility: "visible",
                zIndex: 6
            }).add());
            d.translate(a.plotLeft, a.plotTop);
            n(f, function (a) {
                n(a, function (a) {
                    a.render(d)
                })
            })
        };
        B.prototype.resetStacks = function () {
            var a = this, b = a.stacks;
            a.isXAxis || n(b, function (b) {
                n(b, function (c, h) {
                    c.touched < a.stacksTouched ? (c.destroy(), delete b[h]) : (c.total = null, c.cumulative = null)
                })
            })
        };
        B.prototype.cleanStacks = function () {
            var a;
            this.isXAxis || (this.oldStacks && (a = this.stacks = this.oldStacks), n(a, function (a) {
                n(a, function (a) {
                    a.cumulative = a.total
                })
            }))
        };
        u.prototype.setStackedPoints = function () {
            if (this.options.stacking &&
                (!0 === this.visible || !1 === this.chart.options.chart.ignoreHiddenSeries)) {
                var c = this.processedXData, b = this.processedYData, f = [], d = b.length, h = this.options,
                    k = h.threshold, e = v(h.startFromThreshold && k, 0), l = h.stack, h = h.stacking,
                    n = this.stackKey, t = "-" + n, z = this.negStacks, u = this.yAxis, D = u.stacks, C = u.oldStacks,
                    x, F, A, J, G, g, w;
                u.stacksTouched += 1;
                for (G = 0; G < d; G++) g = c[G], w = b[G], x = this.getStackIndicator(x, g, this.index), J = x.key, A = (F = z && w < (e ? 0 : k)) ? t : n, D[A] || (D[A] = {}), D[A][g] || (C[A] && C[A][g] ? (D[A][g] = C[A][g], D[A][g].total =
                    null) : D[A][g] = new a.StackItem(u, u.options.stackLabels, F, g, l)), A = D[A][g], null !== w ? (A.points[J] = A.points[this.index] = [v(A.cumulative, e)], q(A.cumulative) || (A.base = J), A.touched = u.stacksTouched, 0 < x.index && !1 === this.singleStacks && (A.points[J][0] = A.points[this.index + "," + g + ",0"][0])) : A.points[J] = A.points[this.index] = null, "percent" === h ? (F = F ? n : t, z && D[F] && D[F][g] ? (F = D[F][g], A.total = F.total = Math.max(F.total, A.total) + Math.abs(w) || 0) : A.total = E(A.total + (Math.abs(w) || 0))) : A.total = E(A.total + (w || 0)), A.cumulative = v(A.cumulative,
                    e) + (w || 0), null !== w && (A.points[J].push(A.cumulative), f[G] = A.cumulative);
                "percent" === h && (u.usePercentage = !0);
                this.stackedYData = f;
                u.oldStacks = {}
            }
        };
        u.prototype.modifyStacks = function () {
            var a = this, b = a.stackKey, f = a.yAxis.stacks, d = a.processedXData, h, k = a.options.stacking;
            a[k + "Stacker"] && l([b, "-" + b], function (b) {
                for (var c = d.length, e, l; c--;) if (e = d[c], h = a.getStackIndicator(h, e, a.index, b), l = (e = f[b] && f[b][e]) && e.points[h.key]) a[k + "Stacker"](l, e, c)
            })
        };
        u.prototype.percentStacker = function (a, b, f) {
            b = b.total ? 100 / b.total :
                0;
            a[0] = E(a[0] * b);
            a[1] = E(a[1] * b);
            this.stackedYData[f] = a[1]
        };
        u.prototype.getStackIndicator = function (a, b, f, d) {
            !q(a) || a.x !== b || d && a.key !== d ? a = {x: b, index: 0, key: d} : a.index++;
            a.key = [f, b, a.index].join();
            return a
        }
    })(K);
    (function (a) {
        var B = a.addEvent, H = a.animate, E = a.Axis, q = a.createElement, f = a.css, l = a.defined, t = a.each,
            n = a.erase, v = a.extend, u = a.fireEvent, c = a.inArray, b = a.isNumber, m = a.isObject, d = a.isArray,
            h = a.merge, k = a.objectEach, e = a.pick, p = a.Point, r = a.Series, I = a.seriesTypes, z = a.setAnimation,
            M = a.splat;
        v(a.Chart.prototype,
            {
                addSeries: function (a, b, c) {
                    var d, h = this;
                    a && (b = e(b, !0), u(h, "addSeries", {options: a}, function () {
                        d = h.initSeries(a);
                        h.isDirtyLegend = !0;
                        h.linkSeries();
                        b && h.redraw(c)
                    }));
                    return d
                },
                addAxis: function (a, b, c, d) {
                    var k = b ? "xAxis" : "yAxis", f = this.options;
                    a = h(a, {index: this[k].length, isX: b});
                    b = new E(this, a);
                    f[k] = M(f[k] || {});
                    f[k].push(a);
                    e(c, !0) && this.redraw(d);
                    return b
                },
                showLoading: function (a) {
                    var b = this, c = b.options, e = b.loadingDiv, d = c.loading, h = function () {
                        e && f(e, {
                            left: b.plotLeft + "px", top: b.plotTop + "px", width: b.plotWidth +
                            "px", height: b.plotHeight + "px"
                        })
                    };
                    e || (b.loadingDiv = e = q("div", {className: "highcharts-loading highcharts-loading-hidden"}, null, b.container), b.loadingSpan = q("span", {className: "highcharts-loading-inner"}, null, e), B(b, "redraw", h));
                    e.className = "highcharts-loading";
                    b.loadingSpan.innerHTML = a || c.lang.loading;
                    f(e, v(d.style, {zIndex: 10}));
                    f(b.loadingSpan, d.labelStyle);
                    b.loadingShown || (f(e, {
                        opacity: 0,
                        display: ""
                    }), H(e, {opacity: d.style.opacity || .5}, {duration: d.showDuration || 0}));
                    b.loadingShown = !0;
                    h()
                },
                hideLoading: function () {
                    var a =
                        this.options, b = this.loadingDiv;
                    b && (b.className = "highcharts-loading highcharts-loading-hidden", H(b, {opacity: 0}, {
                        duration: a.loading.hideDuration || 100,
                        complete: function () {
                            f(b, {display: "none"})
                        }
                    }));
                    this.loadingShown = !1
                },
                propsRequireDirtyBox: "backgroundColor borderColor borderWidth margin marginTop marginRight marginBottom marginLeft spacing spacingTop spacingRight spacingBottom spacingLeft borderRadius plotBackgroundColor plotBackgroundImage plotBorderColor plotBorderWidth plotShadow shadow".split(" "),
                propsRequireUpdateSeries: "chart.inverted chart.polar chart.ignoreHiddenSeries chart.type colors plotOptions time tooltip".split(" "),
                update: function (a, d, f) {
                    var p = this, m = {credits: "addCredits", title: "setTitle", subtitle: "setSubtitle"}, n = a.chart,
                        r, g, z = [];
                    if (n) {
                        h(!0, p.options.chart, n);
                        "className" in n && p.setClassName(n.className);
                        if ("inverted" in n || "polar" in n) p.propFromSeries(), r = !0;
                        "alignTicks" in n && (r = !0);
                        k(n, function (a, b) {
                            -1 !== c("chart." + b, p.propsRequireUpdateSeries) && (g = !0);
                            -1 !== c(b, p.propsRequireDirtyBox) &&
                            (p.isDirtyBox = !0)
                        });
                        "style" in n && p.renderer.setStyle(n.style)
                    }
                    a.colors && (this.options.colors = a.colors);
                    a.plotOptions && h(!0, this.options.plotOptions, a.plotOptions);
                    k(a, function (a, b) {
                        if (p[b] && "function" === typeof p[b].update) p[b].update(a, !1); else if ("function" === typeof p[m[b]]) p[m[b]](a);
                        "chart" !== b && -1 !== c(b, p.propsRequireUpdateSeries) && (g = !0)
                    });
                    t("xAxis yAxis zAxis series colorAxis pane".split(" "), function (b) {
                        a[b] && (t(M(a[b]), function (a, c) {
                            (c = l(a.id) && p.get(a.id) || p[b][c]) && c.coll === b && (c.update(a,
                                !1), f && (c.touched = !0));
                            if (!c && f) if ("series" === b) p.addSeries(a, !1).touched = !0; else if ("xAxis" === b || "yAxis" === b) p.addAxis(a, "xAxis" === b, !1).touched = !0
                        }), f && t(p[b], function (a) {
                            a.touched ? delete a.touched : z.push(a)
                        }))
                    });
                    t(z, function (a) {
                        a.remove(!1)
                    });
                    r && t(p.axes, function (a) {
                        a.update({}, !1)
                    });
                    g && t(p.series, function (a) {
                        a.update({}, !1)
                    });
                    a.loading && h(!0, p.options.loading, a.loading);
                    r = n && n.width;
                    n = n && n.height;
                    b(r) && r !== p.chartWidth || b(n) && n !== p.chartHeight ? p.setSize(r, n) : e(d, !0) && p.redraw()
                },
                setSubtitle: function (a) {
                    this.setTitle(void 0,
                        a)
                }
            });
        v(p.prototype, {
            update: function (a, b, c, d) {
                function h() {
                    k.applyOptions(a);
                    null === k.y && g && (k.graphic = g.destroy());
                    m(a, !0) && (g && g.element && a && a.marker && void 0 !== a.marker.symbol && (k.graphic = g.destroy()), a && a.dataLabels && k.dataLabel && (k.dataLabel = k.dataLabel.destroy()), k.connector && (k.connector = k.connector.destroy()));
                    p = k.index;
                    f.updateParallelArrays(k, p);
                    n.data[p] = m(n.data[p], !0) || m(a, !0) ? k.options : a;
                    f.isDirty = f.isDirtyData = !0;
                    !f.fixedBox && f.hasCartesianSeries && (l.isDirtyBox = !0);
                    "point" === n.legendType &&
                    (l.isDirtyLegend = !0);
                    b && l.redraw(c)
                }

                var k = this, f = k.series, g = k.graphic, p, l = f.chart, n = f.options;
                b = e(b, !0);
                !1 === d ? h() : k.firePointEvent("update", {options: a}, h)
            }, remove: function (a, b) {
                this.series.removePoint(c(this, this.series.data), a, b)
            }
        });
        v(r.prototype, {
            addPoint: function (a, b, c, d) {
                var h = this.options, k = this.data, f = this.chart, g = this.xAxis, g = g && g.hasNames && g.names,
                    p = h.data, l, m, n = this.xData, r, z;
                b = e(b, !0);
                l = {series: this};
                this.pointClass.prototype.applyOptions.apply(l, [a]);
                z = l.x;
                r = n.length;
                if (this.requireSorting &&
                    z < n[r - 1]) for (m = !0; r && n[r - 1] > z;) r--;
                this.updateParallelArrays(l, "splice", r, 0, 0);
                this.updateParallelArrays(l, r);
                g && l.name && (g[z] = l.name);
                p.splice(r, 0, a);
                m && (this.data.splice(r, 0, null), this.processData());
                "point" === h.legendType && this.generatePoints();
                c && (k[0] && k[0].remove ? k[0].remove(!1) : (k.shift(), this.updateParallelArrays(l, "shift"), p.shift()));
                this.isDirtyData = this.isDirty = !0;
                b && f.redraw(d)
            }, removePoint: function (a, b, c) {
                var d = this, h = d.data, k = h[a], f = d.points, g = d.chart, p = function () {
                    f && f.length === h.length &&
                    f.splice(a, 1);
                    h.splice(a, 1);
                    d.options.data.splice(a, 1);
                    d.updateParallelArrays(k || {series: d}, "splice", a, 1);
                    k && k.destroy();
                    d.isDirty = !0;
                    d.isDirtyData = !0;
                    b && g.redraw()
                };
                z(c, g);
                b = e(b, !0);
                k ? k.firePointEvent("remove", null, p) : p()
            }, remove: function (a, b, c) {
                function d() {
                    h.destroy();
                    k.isDirtyLegend = k.isDirtyBox = !0;
                    k.linkSeries();
                    e(a, !0) && k.redraw(b)
                }

                var h = this, k = h.chart;
                !1 !== c ? u(h, "remove", null, d) : d()
            }, update: function (a, b) {
                var c = this, d = c.chart, k = c.userOptions, f = c.oldType || c.type,
                    p = a.type || k.type || d.options.chart.type,
                    g = I[f].prototype, l, m = ["group", "markerGroup", "dataLabelsGroup"],
                    n = ["navigatorSeries", "baseSeries"], r = c.finishedAnimating && {animation: !1};
                if (Object.keys && "data" === Object.keys(a).toString()) return this.setData(a.data, b);
                n = m.concat(n);
                t(n, function (a) {
                    n[a] = c[a];
                    delete c[a]
                });
                a = h(k, r, {index: c.index, pointStart: c.xData[0]}, {data: c.options.data}, a);
                c.remove(!1, null, !1);
                for (l in g) c[l] = void 0;
                v(c, I[p || f].prototype);
                t(n, function (a) {
                    c[a] = n[a]
                });
                c.init(d, a);
                a.zIndex !== k.zIndex && t(m, function (b) {
                    c[b] && c[b].attr({zIndex: a.zIndex})
                });
                c.oldType = f;
                d.linkSeries();
                e(b, !0) && d.redraw(!1)
            }
        });
        v(E.prototype, {
            update: function (a, b) {
                var c = this.chart;
                a = c.options[this.coll][this.options.index] = h(this.userOptions, a);
                this.destroy(!0);
                this.init(c, v(a, {events: void 0}));
                c.isDirtyBox = !0;
                e(b, !0) && c.redraw()
            }, remove: function (a) {
                for (var b = this.chart, c = this.coll, h = this.series, k = h.length; k--;) h[k] && h[k].remove(!1);
                n(b.axes, this);
                n(b[c], this);
                d(b.options[c]) ? b.options[c].splice(this.options.index, 1) : delete b.options[c];
                t(b[c], function (a, b) {
                    a.options.index =
                        b
                });
                this.destroy();
                b.isDirtyBox = !0;
                e(a, !0) && b.redraw()
            }, setTitle: function (a, b) {
                this.update({title: a}, b)
            }, setCategories: function (a, b) {
                this.update({categories: a}, b)
            }
        })
    })(K);
    (function (a) {
        var B = a.color, H = a.each, E = a.map, q = a.pick, f = a.Series, l = a.seriesType;
        l("area", "line", {softThreshold: !1, threshold: 0}, {
            singleStacks: !1, getStackPoints: function (f) {
                var l = [], t = [], u = this.xAxis, c = this.yAxis, b = c.stacks[this.stackKey], m = {}, d = this.index,
                    h = c.series, k = h.length, e, p = q(c.options.reversedStacks, !0) ? 1 : -1, r;
                f = f || this.points;
                if (this.options.stacking) {
                    for (r = 0; r < f.length; r++) f[r].leftNull = f[r].rightNull = null, m[f[r].x] = f[r];
                    a.objectEach(b, function (a, b) {
                        null !== a.total && t.push(b)
                    });
                    t.sort(function (a, b) {
                        return a - b
                    });
                    e = E(h, function () {
                        return this.visible
                    });
                    H(t, function (a, h) {
                        var f = 0, n, z;
                        if (m[a] && !m[a].isNull) l.push(m[a]), H([-1, 1], function (c) {
                            var f = 1 === c ? "rightNull" : "leftNull", l = 0, q = b[t[h + c]];
                            if (q) for (r = d; 0 <= r && r < k;) n = q.points[r], n || (r === d ? m[a][f] = !0 : e[r] && (z = b[a].points[r]) && (l -= z[1] - z[0])), r += p;
                            m[a][1 === c ? "rightCliff" : "leftCliff"] =
                                l
                        }); else {
                            for (r = d; 0 <= r && r < k;) {
                                if (n = b[a].points[r]) {
                                    f = n[1];
                                    break
                                }
                                r += p
                            }
                            f = c.translate(f, 0, 1, 0, 1);
                            l.push({isNull: !0, plotX: u.translate(a, 0, 0, 0, 1), x: a, plotY: f, yBottom: f})
                        }
                    })
                }
                return l
            }, getGraphPath: function (a) {
                var l = f.prototype.getGraphPath, t = this.options, u = t.stacking, c = this.yAxis, b, m, d = [],
                    h = [], k = this.index, e, p = c.stacks[this.stackKey], r = t.threshold,
                    I = c.getThreshold(t.threshold), z, t = t.connectNulls || "percent" === u, M = function (b, f, l) {
                        var m = a[b];
                        b = u && p[m.x].points[k];
                        var n = m[l + "Null"] || 0;
                        l = m[l + "Cliff"] || 0;
                        var z,
                            q, m = !0;
                        l || n ? (z = (n ? b[0] : b[1]) + l, q = b[0] + l, m = !!n) : !u && a[f] && a[f].isNull && (z = q = r);
                        void 0 !== z && (h.push({
                            plotX: e,
                            plotY: null === z ? I : c.getThreshold(z),
                            isNull: m,
                            isCliff: !0
                        }), d.push({plotX: e, plotY: null === q ? I : c.getThreshold(q), doCurve: !1}))
                    };
                a = a || this.points;
                u && (a = this.getStackPoints(a));
                for (b = 0; b < a.length; b++) if (m = a[b].isNull, e = q(a[b].rectPlotX, a[b].plotX), z = q(a[b].yBottom, I), !m || t) t || M(b, b - 1, "left"), m && !u && t || (h.push(a[b]), d.push({
                    x: b,
                    plotX: e,
                    plotY: z
                })), t || M(b, b + 1, "right");
                b = l.call(this, h, !0, !0);
                d.reversed =
                    !0;
                m = l.call(this, d, !0, !0);
                m.length && (m[0] = "L");
                m = b.concat(m);
                l = l.call(this, h, !1, t);
                m.xMap = b.xMap;
                this.areaPath = m;
                return l
            }, drawGraph: function () {
                this.areaPath = [];
                f.prototype.drawGraph.apply(this);
                var a = this, l = this.areaPath, v = this.options,
                    u = [["area", "highcharts-area", this.color, v.fillColor]];
                H(this.zones, function (c, b) {
                    u.push(["zone-area-" + b, "highcharts-area highcharts-zone-area-" + b + " " + c.className, c.color || a.color, c.fillColor || v.fillColor])
                });
                H(u, function (c) {
                    var b = c[0], f = a[b];
                    f ? (f.endX = a.preventGraphAnimation ?
                        null : l.xMap, f.animate({d: l})) : (f = a[b] = a.chart.renderer.path(l).addClass(c[1]).attr({
                        fill: q(c[3], B(c[2]).setOpacity(q(v.fillOpacity, .75)).get()),
                        zIndex: 0
                    }).add(a.group), f.isArea = !0);
                    f.startX = l.xMap;
                    f.shiftUnit = v.step ? 2 : 1
                })
            }, drawLegendSymbol: a.LegendSymbolMixin.drawRectangle
        })
    })(K);
    (function (a) {
        var B = a.pick;
        a = a.seriesType;
        a("spline", "line", {}, {
            getPointSpline: function (a, E, q) {
                var f = E.plotX, l = E.plotY, t = a[q - 1];
                q = a[q + 1];
                var n, v, u, c;
                if (t && !t.isNull && !1 !== t.doCurve && !E.isCliff && q && !q.isNull && !1 !== q.doCurve &&
                    !E.isCliff) {
                    a = t.plotY;
                    u = q.plotX;
                    q = q.plotY;
                    var b = 0;
                    n = (1.5 * f + t.plotX) / 2.5;
                    v = (1.5 * l + a) / 2.5;
                    u = (1.5 * f + u) / 2.5;
                    c = (1.5 * l + q) / 2.5;
                    u !== n && (b = (c - v) * (u - f) / (u - n) + l - c);
                    v += b;
                    c += b;
                    v > a && v > l ? (v = Math.max(a, l), c = 2 * l - v) : v < a && v < l && (v = Math.min(a, l), c = 2 * l - v);
                    c > q && c > l ? (c = Math.max(q, l), v = 2 * l - c) : c < q && c < l && (c = Math.min(q, l), v = 2 * l - c);
                    E.rightContX = u;
                    E.rightContY = c
                }
                E = ["C", B(t.rightContX, t.plotX), B(t.rightContY, t.plotY), B(n, f), B(v, l), f, l];
                t.rightContX = t.rightContY = null;
                return E
            }
        })
    })(K);
    (function (a) {
        var B = a.seriesTypes.area.prototype,
            H = a.seriesType;
        H("areaspline", "spline", a.defaultPlotOptions.area, {
            getStackPoints: B.getStackPoints,
            getGraphPath: B.getGraphPath,
            drawGraph: B.drawGraph,
            drawLegendSymbol: a.LegendSymbolMixin.drawRectangle
        })
    })(K);
    (function (a) {
        var B = a.animObject, H = a.color, E = a.each, q = a.extend, f = a.isNumber, l = a.merge, t = a.pick,
            n = a.Series, v = a.seriesType, u = a.svg;
        v("column", "line", {
            borderRadius: 0,
            crisp: !0,
            groupPadding: .2,
            marker: null,
            pointPadding: .1,
            minPointLength: 0,
            cropThreshold: 50,
            pointRange: null,
            states: {
                hover: {halo: !1, brightness: .1},
                select: {color: "#cccccc", borderColor: "#000000"}
            },
            dataLabels: {align: null, verticalAlign: null, y: null},
            softThreshold: !1,
            startFromThreshold: !0,
            stickyTracking: !1,
            tooltip: {distance: 6},
            threshold: 0,
            borderColor: "#ffffff"
        }, {
            cropShoulder: 0,
            directTouch: !0,
            trackerGroups: ["group", "dataLabelsGroup"],
            negStacks: !0,
            init: function () {
                n.prototype.init.apply(this, arguments);
                var a = this, b = a.chart;
                b.hasRendered && E(b.series, function (b) {
                    b.type === a.type && (b.isDirty = !0)
                })
            },
            getColumnMetrics: function () {
                var a = this, b = a.options, f = a.xAxis,
                    d = a.yAxis, h = f.reversed, k, e = {}, l = 0;
                !1 === b.grouping ? l = 1 : E(a.chart.series, function (b) {
                    var c = b.options, h = b.yAxis, f;
                    b.type !== a.type || !b.visible && a.chart.options.chart.ignoreHiddenSeries || d.len !== h.len || d.pos !== h.pos || (c.stacking ? (k = b.stackKey, void 0 === e[k] && (e[k] = l++), f = e[k]) : !1 !== c.grouping && (f = l++), b.columnIndex = f)
                });
                var n = Math.min(Math.abs(f.transA) * (f.ordinalSlope || b.pointRange || f.closestPointRange || f.tickInterval || 1), f.len),
                    q = n * b.groupPadding, z = (n - 2 * q) / (l || 1),
                    b = Math.min(b.maxPointWidth || f.len, t(b.pointWidth,
                        z * (1 - 2 * b.pointPadding)));
                a.columnMetrics = {
                    width: b,
                    offset: (z - b) / 2 + (q + ((a.columnIndex || 0) + (h ? 1 : 0)) * z - n / 2) * (h ? -1 : 1)
                };
                return a.columnMetrics
            },
            crispCol: function (a, b, f, d) {
                var c = this.chart, k = this.borderWidth, e = -(k % 2 ? .5 : 0), k = k % 2 ? .5 : 1;
                c.inverted && c.renderer.isVML && (k += 1);
                this.options.crisp && (f = Math.round(a + f) + e, a = Math.round(a) + e, f -= a);
                d = Math.round(b + d) + k;
                e = .5 >= Math.abs(b) && .5 < d;
                b = Math.round(b) + k;
                d -= b;
                e && d && (--b, d += 1);
                return {x: a, y: b, width: f, height: d}
            },
            translate: function () {
                var a = this, b = a.chart, f = a.options, d =
                        a.dense = 2 > a.closestPointRange * a.xAxis.transA, d = a.borderWidth = t(f.borderWidth, d ? 0 : 1),
                    h = a.yAxis, k = f.threshold, e = a.translatedThreshold = h.getThreshold(k),
                    l = t(f.minPointLength, 5), r = a.getColumnMetrics(), q = r.width,
                    z = a.barW = Math.max(q, 1 + 2 * d), u = a.pointXOffset = r.offset;
                b.inverted && (e -= .5);
                f.pointPadding && (z = Math.ceil(z));
                n.prototype.translate.apply(a);
                E(a.points, function (c) {
                    var d = t(c.yBottom, e), f = 999 + Math.abs(d), f = Math.min(Math.max(-f, c.plotY), h.len + f),
                        p = c.plotX + u, m = z, n = Math.min(f, d), r, g = Math.max(f, d) - n;
                    l &&
                    Math.abs(g) < l && (g = l, r = !h.reversed && !c.negative || h.reversed && c.negative, c.y === k && a.dataMax <= k && h.min < k && (r = !r), n = Math.abs(n - e) > l ? d - l : e - (r ? l : 0));
                    c.barX = p;
                    c.pointWidth = q;
                    c.tooltipPos = b.inverted ? [h.len + h.pos - b.plotLeft - f, a.xAxis.len - p - m / 2, g] : [p + m / 2, f + h.pos - b.plotTop, g];
                    c.shapeType = "rect";
                    c.shapeArgs = a.crispCol.apply(a, c.isNull ? [p, e, m, 0] : [p, n, m, g])
                })
            },
            getSymbol: a.noop,
            drawLegendSymbol: a.LegendSymbolMixin.drawRectangle,
            drawGraph: function () {
                this.group[this.dense ? "addClass" : "removeClass"]("highcharts-dense-data")
            },
            pointAttribs: function (a, b) {
                var c = this.options, d, h = this.pointAttrToOptions || {};
                d = h.stroke || "borderColor";
                var f = h["stroke-width"] || "borderWidth", e = a && a.color || this.color,
                    p = a && a[d] || c[d] || this.color || e, n = a && a[f] || c[f] || this[f] || 0, h = c.dashStyle;
                a && this.zones.length && (e = a.getZone(), e = a.options.color || e && e.color || this.color);
                b && (a = l(c.states[b], a.options.states && a.options.states[b] || {}), b = a.brightness, e = a.color || void 0 !== b && H(e).brighten(a.brightness).get() || e, p = a[d] || p, n = a[f] || n, h = a.dashStyle || h);
                d = {
                    fill: e,
                    stroke: p, "stroke-width": n
                };
                h && (d.dashstyle = h);
                return d
            },
            drawPoints: function () {
                var a = this, b = this.chart, m = a.options, d = b.renderer, h = m.animationLimit || 250, k;
                E(a.points, function (c) {
                    var e = c.graphic;
                    if (f(c.plotY) && null !== c.y) {
                        k = c.shapeArgs;
                        if (e) e[b.pointCount < h ? "animate" : "attr"](l(k)); else c.graphic = e = d[c.shapeType](k).add(c.group || a.group);
                        m.borderRadius && e.attr({r: m.borderRadius});
                        e.attr(a.pointAttribs(c, c.selected && "select")).shadow(m.shadow, null, m.stacking && !m.borderRadius);
                        e.addClass(c.getClassName(),
                            !0)
                    } else e && (c.graphic = e.destroy())
                })
            },
            animate: function (a) {
                var b = this, c = this.yAxis, d = b.options, h = this.chart.inverted, f = {},
                    e = h ? "translateX" : "translateY", l;
                u && (a ? (f.scaleY = .001, a = Math.min(c.pos + c.len, Math.max(c.pos, c.toPixels(d.threshold))), h ? f.translateX = a - c.len : f.translateY = a, b.group.attr(f)) : (l = b.group.attr(e), b.group.animate({scaleY: 1}, q(B(b.options.animation), {
                    step: function (a, d) {
                        f[e] = l + d.pos * (c.pos - l);
                        b.group.attr(f)
                    }
                })), b.animate = null))
            },
            remove: function () {
                var a = this, b = a.chart;
                b.hasRendered && E(b.series,
                    function (b) {
                        b.type === a.type && (b.isDirty = !0)
                    });
                n.prototype.remove.apply(a, arguments)
            }
        })
    })(K);
    (function (a) {
        a = a.seriesType;
        a("bar", "column", null, {inverted: !0})
    })(K);
    (function (a) {
        var B = a.Series;
        a = a.seriesType;
        a("scatter", "line", {
                lineWidth: 0,
                findNearestPointBy: "xy",
                marker: {enabled: !0},
                tooltip: {
                    headerFormat: '\x3cspan style\x3d"color:{point.color}"\x3e\u25cf\x3c/span\x3e \x3cspan style\x3d"font-size: 0.85em"\x3e {series.name}\x3c/span\x3e\x3cbr/\x3e',
                    pointFormat: "x: \x3cb\x3e{point.x}\x3c/b\x3e\x3cbr/\x3ey: \x3cb\x3e{point.y}\x3c/b\x3e\x3cbr/\x3e"
                }
            },
            {
                sorted: !1,
                requireSorting: !1,
                noSharedTooltip: !0,
                trackerGroups: ["group", "markerGroup", "dataLabelsGroup"],
                takeOrdinalPosition: !1,
                drawGraph: function () {
                    this.options.lineWidth && B.prototype.drawGraph.call(this)
                }
            })
    })(K);
    (function (a) {
        var B = a.deg2rad, H = a.isNumber, E = a.pick, q = a.relativeLength;
        a.CenteredSeriesMixin = {
            getCenter: function () {
                var a = this.options, l = this.chart, t = 2 * (a.slicedOffset || 0), n = l.plotWidth - 2 * t,
                    l = l.plotHeight - 2 * t, v = a.center,
                    v = [E(v[0], "50%"), E(v[1], "50%"), a.size || "100%", a.innerSize || 0], u = Math.min(n,
                    l), c, b;
                for (c = 0; 4 > c; ++c) b = v[c], a = 2 > c || 2 === c && /%$/.test(b), v[c] = q(b, [n, l, u, v[2]][c]) + (a ? t : 0);
                v[3] > v[2] && (v[3] = v[2]);
                return v
            }, getStartAndEndRadians: function (a, l) {
                a = H(a) ? a : 0;
                l = H(l) && l > a && 360 > l - a ? l : a + 360;
                return {start: B * (a + -90), end: B * (l + -90)}
            }
        }
    })(K);
    (function (a) {
        var B = a.addEvent, H = a.CenteredSeriesMixin, E = a.defined, q = a.each, f = a.extend,
            l = H.getStartAndEndRadians, t = a.inArray, n = a.noop, v = a.pick, u = a.Point, c = a.Series,
            b = a.seriesType, m = a.setAnimation;
        b("pie", "line", {
            center: [null, null],
            clip: !1,
            colorByPoint: !0,
            dataLabels: {
                distance: 30,
                enabled: !0, formatter: function () {
                    return this.point.isNull ? void 0 : this.point.name
                }, x: 0
            },
            ignoreHiddenPoint: !0,
            legendType: "point",
            marker: null,
            size: null,
            showInLegend: !1,
            slicedOffset: 10,
            stickyTracking: !1,
            tooltip: {followPointer: !0},
            borderColor: "#ffffff",
            borderWidth: 1,
            states: {hover: {brightness: .1}}
        }, {
            isCartesian: !1,
            requireSorting: !1,
            directTouch: !0,
            noSharedTooltip: !0,
            trackerGroups: ["group", "dataLabelsGroup"],
            axisTypes: [],
            pointAttribs: a.seriesTypes.column.prototype.pointAttribs,
            animate: function (a) {
                var b = this,
                    c = b.points, d = b.startAngleRad;
                a || (q(c, function (a) {
                    var c = a.graphic, e = a.shapeArgs;
                    c && (c.attr({r: a.startR || b.center[3] / 2, start: d, end: d}), c.animate({
                        r: e.r,
                        start: e.start,
                        end: e.end
                    }, b.options.animation))
                }), b.animate = null)
            },
            updateTotals: function () {
                var a, b = 0, c = this.points, e = c.length, f, l = this.options.ignoreHiddenPoint;
                for (a = 0; a < e; a++) f = c[a], b += l && !f.visible ? 0 : f.isNull ? 0 : f.y;
                this.total = b;
                for (a = 0; a < e; a++) f = c[a], f.percentage = 0 < b && (f.visible || !l) ? f.y / b * 100 : 0, f.total = b
            },
            generatePoints: function () {
                c.prototype.generatePoints.call(this);
                this.updateTotals()
            },
            translate: function (a) {
                this.generatePoints();
                var b = 0, c = this.options, e = c.slicedOffset, d = e + (c.borderWidth || 0), f, m, n,
                    q = l(c.startAngle, c.endAngle), t = this.startAngleRad = q.start,
                    q = (this.endAngleRad = q.end) - t, u = this.points, x, F = c.dataLabels.distance,
                    c = c.ignoreHiddenPoint, A, B = u.length, G;
                a || (this.center = a = this.getCenter());
                this.getX = function (b, c, e) {
                    n = Math.asin(Math.min((b - a[1]) / (a[2] / 2 + e.labelDistance), 1));
                    return a[0] + (c ? -1 : 1) * Math.cos(n) * (a[2] / 2 + e.labelDistance)
                };
                for (A = 0; A < B; A++) {
                    G = u[A];
                    G.labelDistance = v(G.options.dataLabels && G.options.dataLabels.distance, F);
                    this.maxLabelDistance = Math.max(this.maxLabelDistance || 0, G.labelDistance);
                    f = t + b * q;
                    if (!c || G.visible) b += G.percentage / 100;
                    m = t + b * q;
                    G.shapeType = "arc";
                    G.shapeArgs = {
                        x: a[0],
                        y: a[1],
                        r: a[2] / 2,
                        innerR: a[3] / 2,
                        start: Math.round(1E3 * f) / 1E3,
                        end: Math.round(1E3 * m) / 1E3
                    };
                    n = (m + f) / 2;
                    n > 1.5 * Math.PI ? n -= 2 * Math.PI : n < -Math.PI / 2 && (n += 2 * Math.PI);
                    G.slicedTranslation = {
                        translateX: Math.round(Math.cos(n) * e),
                        translateY: Math.round(Math.sin(n) * e)
                    };
                    m = Math.cos(n) * a[2] /
                        2;
                    x = Math.sin(n) * a[2] / 2;
                    G.tooltipPos = [a[0] + .7 * m, a[1] + .7 * x];
                    G.half = n < -Math.PI / 2 || n > Math.PI / 2 ? 1 : 0;
                    G.angle = n;
                    f = Math.min(d, G.labelDistance / 5);
                    G.labelPos = [a[0] + m + Math.cos(n) * G.labelDistance, a[1] + x + Math.sin(n) * G.labelDistance, a[0] + m + Math.cos(n) * f, a[1] + x + Math.sin(n) * f, a[0] + m, a[1] + x, 0 > G.labelDistance ? "center" : G.half ? "right" : "left", n]
                }
            },
            drawGraph: null,
            drawPoints: function () {
                var a = this, b = a.chart.renderer, c, e, l, m, n = a.options.shadow;
                n && !a.shadowGroup && (a.shadowGroup = b.g("shadow").add(a.group));
                q(a.points, function (d) {
                    e =
                        d.graphic;
                    if (d.isNull) e && (d.graphic = e.destroy()); else {
                        m = d.shapeArgs;
                        c = d.getTranslate();
                        var h = d.shadowGroup;
                        n && !h && (h = d.shadowGroup = b.g("shadow").add(a.shadowGroup));
                        h && h.attr(c);
                        l = a.pointAttribs(d, d.selected && "select");
                        e ? e.setRadialReference(a.center).attr(l).animate(f(m, c)) : (d.graphic = e = b[d.shapeType](m).setRadialReference(a.center).attr(c).add(a.group), d.visible || e.attr({visibility: "hidden"}), e.attr(l).attr({"stroke-linejoin": "round"}).shadow(n, h));
                        e.addClass(d.getClassName())
                    }
                })
            },
            searchPoint: n,
            sortByAngle: function (a, b) {
                a.sort(function (a, c) {
                    return void 0 !== a.angle && (c.angle - a.angle) * b
                })
            },
            drawLegendSymbol: a.LegendSymbolMixin.drawRectangle,
            getCenter: H.getCenter,
            getSymbol: n
        }, {
            init: function () {
                u.prototype.init.apply(this, arguments);
                var a = this, b;
                a.name = v(a.name, "Slice");
                b = function (b) {
                    a.slice("select" === b.type)
                };
                B(a, "select", b);
                B(a, "unselect", b);
                return a
            }, isValid: function () {
                return a.isNumber(this.y, !0) && 0 <= this.y
            }, setVisible: function (a, b) {
                var c = this, e = c.series, d = e.chart, h = e.options.ignoreHiddenPoint;
                b = v(b, h);
                a !== c.visible && (c.visible = c.options.visible = a = void 0 === a ? !c.visible : a, e.options.data[t(c, e.data)] = c.options, q(["graphic", "dataLabel", "connector", "shadowGroup"], function (b) {
                    if (c[b]) c[b][a ? "show" : "hide"](!0)
                }), c.legendItem && d.legend.colorizeItem(c, a), a || "hover" !== c.state || c.setState(""), h && (e.isDirty = !0), b && d.redraw())
            }, slice: function (a, b, c) {
                var e = this.series;
                m(c, e.chart);
                v(b, !0);
                this.sliced = this.options.sliced = E(a) ? a : !this.sliced;
                e.options.data[t(this, e.data)] = this.options;
                this.graphic.animate(this.getTranslate());
                this.shadowGroup && this.shadowGroup.animate(this.getTranslate())
            }, getTranslate: function () {
                return this.sliced ? this.slicedTranslation : {translateX: 0, translateY: 0}
            }, haloPath: function (a) {
                var b = this.shapeArgs;
                return this.sliced || !this.visible ? [] : this.series.chart.renderer.symbols.arc(b.x, b.y, b.r + a, b.r + a, {
                    innerR: this.shapeArgs.r - 1,
                    start: b.start,
                    end: b.end
                })
            }
        })
    })(K);
    (function (a) {
        var B = a.addEvent, H = a.arrayMax, E = a.defined, q = a.each, f = a.extend, l = a.format, t = a.map,
            n = a.merge, v = a.noop, u = a.pick, c = a.relativeLength, b =
                a.Series, m = a.seriesTypes, d = a.stableSort;
        a.distribute = function (a, b) {
            function c(a, b) {
                return a.target - b.target
            }

            var f, h = !0, k = a, l = [], m;
            m = 0;
            for (f = a.length; f--;) m += a[f].size;
            if (m > b) {
                d(a, function (a, b) {
                    return (b.rank || 0) - (a.rank || 0)
                });
                for (m = f = 0; m <= b;) m += a[f].size, f++;
                l = a.splice(f - 1, a.length)
            }
            d(a, c);
            for (a = t(a, function (a) {
                return {size: a.size, targets: [a.target], align: u(a.align, .5)}
            }); h;) {
                for (f = a.length; f--;) h = a[f], m = (Math.min.apply(0, h.targets) + Math.max.apply(0, h.targets)) / 2, h.pos = Math.min(Math.max(0, m - h.size *
                    h.align), b - h.size);
                f = a.length;
                for (h = !1; f--;) 0 < f && a[f - 1].pos + a[f - 1].size > a[f].pos && (a[f - 1].size += a[f].size, a[f - 1].targets = a[f - 1].targets.concat(a[f].targets), a[f - 1].align = .5, a[f - 1].pos + a[f - 1].size > b && (a[f - 1].pos = b - a[f - 1].size), a.splice(f, 1), h = !0)
            }
            f = 0;
            q(a, function (a) {
                var b = 0;
                q(a.targets, function () {
                    k[f].pos = a.pos + b;
                    b += k[f].size;
                    f++
                })
            });
            k.push.apply(k, l);
            d(k, c)
        };
        b.prototype.drawDataLabels = function () {
            function b(a, b) {
                var c = b.filter;
                return c ? (b = c.operator, a = a[c.property], c = c.value, "\x3e" === b && a > c || "\x3c" ===
                b && a < c || "\x3e\x3d" === b && a >= c || "\x3c\x3d" === b && a <= c || "\x3d\x3d" === b && a == c || "\x3d\x3d\x3d" === b && a === c ? !0 : !1) : !0
            }

            var c = this, e = c.chart, d = c.options, f = d.dataLabels, m = c.points, z, t, v = c.hasRendered || 0, C,
                x, F = u(f.defer, !!d.animation), A = e.renderer;
            if (f.enabled || c._hasPointLabels) c.dlProcessOptions && c.dlProcessOptions(f), x = c.plotGroup("dataLabelsGroup", "data-labels", F && !v ? "hidden" : "visible", f.zIndex || 6), F && (x.attr({opacity: +v}), v || B(c, "afterAnimate", function () {
                c.visible && x.show(!0);
                x[d.animation ? "animate" : "attr"]({opacity: 1},
                    {duration: 200})
            })), t = f, q(m, function (h) {
                var k, g = h.dataLabel, m, p, q = h.connector, r = !g, v;
                z = h.dlOptions || h.options && h.options.dataLabels;
                (k = u(z && z.enabled, t.enabled) && !h.isNull) && (k = !0 === b(h, z || f));
                k && (f = n(t, z), m = h.getLabelConfig(), v = f[h.formatPrefix + "Format"] || f.format, C = E(v) ? l(v, m, e.time) : (f[h.formatPrefix + "Formatter"] || f.formatter).call(m, f), v = f.style, m = f.rotation, v.color = u(f.color, v.color, c.color, "#000000"), "contrast" === v.color && (h.contrastColor = A.getContrast(h.color || c.color), v.color = f.inside || 0 > u(h.labelDistance,
                    f.distance) || d.stacking ? h.contrastColor : "#000000"), d.cursor && (v.cursor = d.cursor), p = {
                    fill: f.backgroundColor,
                    stroke: f.borderColor,
                    "stroke-width": f.borderWidth,
                    r: f.borderRadius || 0,
                    rotation: m,
                    padding: f.padding,
                    zIndex: 1
                }, a.objectEach(p, function (a, b) {
                    void 0 === a && delete p[b]
                }));
                !g || k && E(C) ? k && E(C) && (g ? p.text = C : (g = h.dataLabel = m ? A.text(C, 0, -9999).addClass("highcharts-data-label") : A.label(C, 0, -9999, f.shape, null, null, f.useHTML, null, "data-label"), g.addClass(" highcharts-data-label-color-" + h.colorIndex + " " + (f.className ||
                    "") + (f.useHTML ? "highcharts-tracker" : ""))), g.attr(p), g.css(v).shadow(f.shadow), g.added || g.add(x), c.alignDataLabel(h, g, f, null, r)) : (h.dataLabel = g = g.destroy(), q && (h.connector = q.destroy()))
            });
            a.fireEvent(this, "afterDrawDataLabels")
        };
        b.prototype.alignDataLabel = function (a, b, c, d, l) {
            var e = this.chart, h = e.inverted, k = u(a.dlBox && a.dlBox.centerX, a.plotX, -9999),
                m = u(a.plotY, -9999), n = b.getBBox(), p, q = c.rotation, r = c.align,
                t = this.visible && (a.series.forceDL || e.isInsidePlot(k, Math.round(m), h) || d && e.isInsidePlot(k, h ? d.x +
                    1 : d.y + d.height - 1, h)), v = "justify" === u(c.overflow, "justify");
            if (t && (p = c.style.fontSize, p = e.renderer.fontMetrics(p, b).b, d = f({
                    x: h ? this.yAxis.len - m : k,
                    y: Math.round(h ? this.xAxis.len - k : m),
                    width: 0,
                    height: 0
                }, d), f(c, {
                    width: n.width,
                    height: n.height
                }), q ? (v = !1, k = e.renderer.rotCorr(p, q), k = {
                    x: d.x + c.x + d.width / 2 + k.x,
                    y: d.y + c.y + {top: 0, middle: .5, bottom: 1}[c.verticalAlign] * d.height
                }, b[l ? "attr" : "animate"](k).attr({align: r}), m = (q + 720) % 360, m = 180 < m && 360 > m, "left" === r ? k.y -= m ? n.height : 0 : "center" === r ? (k.x -= n.width / 2, k.y -= n.height /
                    2) : "right" === r && (k.x -= n.width, k.y -= m ? 0 : n.height)) : (b.align(c, null, d), k = b.alignAttr), v ? a.isLabelJustified = this.justifyDataLabel(b, c, k, n, d, l) : u(c.crop, !0) && (t = e.isInsidePlot(k.x, k.y) && e.isInsidePlot(k.x + n.width, k.y + n.height)), c.shape && !q)) b[l ? "attr" : "animate"]({
                anchorX: h ? e.plotWidth - a.plotY : a.plotX,
                anchorY: h ? e.plotHeight - a.plotX : a.plotY
            });
            t || (b.attr({y: -9999}), b.placed = !1)
        };
        b.prototype.justifyDataLabel = function (a, b, c, d, f, l) {
            var e = this.chart, h = b.align, k = b.verticalAlign, m, n, p = a.box ? 0 : a.padding || 0;
            m = c.x +
                p;
            0 > m && ("right" === h ? b.align = "left" : b.x = -m, n = !0);
            m = c.x + d.width - p;
            m > e.plotWidth && ("left" === h ? b.align = "right" : b.x = e.plotWidth - m, n = !0);
            m = c.y + p;
            0 > m && ("bottom" === k ? b.verticalAlign = "top" : b.y = -m, n = !0);
            m = c.y + d.height - p;
            m > e.plotHeight && ("top" === k ? b.verticalAlign = "bottom" : b.y = e.plotHeight - m, n = !0);
            n && (a.placed = !l, a.align(b, null, f));
            return n
        };
        m.pie && (m.pie.prototype.drawDataLabels = function () {
            var c = this, d = c.data, e, f = c.chart, l = c.options.dataLabels, m = u(l.connectorPadding, 10),
                n = u(l.connectorWidth, 1), t = f.plotWidth,
                v = f.plotHeight, C, x = c.center, F = x[2] / 2, A = x[1], B, G, g, w, L = [[], []], P, N, O, K,
                y = [0, 0, 0, 0];
            c.visible && (l.enabled || c._hasPointLabels) && (q(d, function (a) {
                a.dataLabel && a.visible && a.dataLabel.shortened && (a.dataLabel.attr({width: "auto"}).css({
                    width: "auto",
                    textOverflow: "clip"
                }), a.dataLabel.shortened = !1)
            }), b.prototype.drawDataLabels.apply(c), q(d, function (a) {
                a.dataLabel && a.visible && (L[a.half].push(a), a.dataLabel._pos = null)
            }), q(L, function (b, d) {
                var h, k, n = b.length, p = [], r;
                if (n) for (c.sortByAngle(b, d - .5), 0 < c.maxLabelDistance &&
                (h = Math.max(0, A - F - c.maxLabelDistance), k = Math.min(A + F + c.maxLabelDistance, f.plotHeight), q(b, function (a) {
                    0 < a.labelDistance && a.dataLabel && (a.top = Math.max(0, A - F - a.labelDistance), a.bottom = Math.min(A + F + a.labelDistance, f.plotHeight), r = a.dataLabel.getBBox().height || 21, a.positionsIndex = p.push({
                        target: a.labelPos[1] - a.top + r / 2,
                        size: r,
                        rank: a.y
                    }) - 1)
                }), a.distribute(p, k + r - h)), K = 0; K < n; K++) e = b[K], k = e.positionsIndex, g = e.labelPos, B = e.dataLabel, O = !1 === e.visible ? "hidden" : "inherit", N = h = g[1], p && E(p[k]) && (void 0 === p[k].pos ?
                    O = "hidden" : (w = p[k].size, N = e.top + p[k].pos)), delete e.positionIndex, P = l.justify ? x[0] + (d ? -1 : 1) * (F + e.labelDistance) : c.getX(N < e.top + 2 || N > e.bottom - 2 ? h : N, d, e), B._attr = {
                    visibility: O,
                    align: g[6]
                }, B._pos = {
                    x: P + l.x + ({left: m, right: -m}[g[6]] || 0),
                    y: N + l.y - 10
                }, g.x = P, g.y = N, u(l.crop, !0) && (G = B.getBBox().width, h = null, P - G < m ? (h = Math.round(G - P + m), y[3] = Math.max(h, y[3])) : P + G > t - m && (h = Math.round(P + G - t + m), y[1] = Math.max(h, y[1])), 0 > N - w / 2 ? y[0] = Math.max(Math.round(-N + w / 2), y[0]) : N + w / 2 > v && (y[2] = Math.max(Math.round(N + w / 2 - v), y[2])), B.sideOverflow =
                    h)
            }), 0 === H(y) || this.verifyDataLabelOverflow(y)) && (this.placeDataLabels(), n && q(this.points, function (a) {
                var b;
                C = a.connector;
                if ((B = a.dataLabel) && B._pos && a.visible && 0 < a.labelDistance) {
                    O = B._attr.visibility;
                    if (b = !C) a.connector = C = f.renderer.path().addClass("highcharts-data-label-connector  highcharts-color-" + a.colorIndex).add(c.dataLabelsGroup), C.attr({
                        "stroke-width": n,
                        stroke: l.connectorColor || a.color || "#666666"
                    });
                    C[b ? "attr" : "animate"]({d: c.connectorPath(a.labelPos)});
                    C.attr("visibility", O)
                } else C && (a.connector =
                    C.destroy())
            }))
        }, m.pie.prototype.connectorPath = function (a) {
            var b = a.x, c = a.y;
            return u(this.options.dataLabels.softConnector, !0) ? ["M", b + ("left" === a[6] ? 5 : -5), c, "C", b, c, 2 * a[2] - a[4], 2 * a[3] - a[5], a[2], a[3], "L", a[4], a[5]] : ["M", b + ("left" === a[6] ? 5 : -5), c, "L", a[2], a[3], "L", a[4], a[5]]
        }, m.pie.prototype.placeDataLabels = function () {
            q(this.points, function (a) {
                var b = a.dataLabel;
                b && a.visible && ((a = b._pos) ? (b.sideOverflow && (b._attr.width = b.getBBox().width - b.sideOverflow, b.css({
                    width: b._attr.width + "px",
                    textOverflow: "ellipsis"
                }),
                    b.shortened = !0), b.attr(b._attr), b[b.moved ? "animate" : "attr"](a), b.moved = !0) : b && b.attr({y: -9999}))
            }, this)
        }, m.pie.prototype.alignDataLabel = v, m.pie.prototype.verifyDataLabelOverflow = function (a) {
            var b = this.center, e = this.options, d = e.center, f = e.minSize || 80, h, l = null !== e.size;
            l || (null !== d[0] ? h = Math.max(b[2] - Math.max(a[1], a[3]), f) : (h = Math.max(b[2] - a[1] - a[3], f), b[0] += (a[3] - a[1]) / 2), null !== d[1] ? h = Math.max(Math.min(h, b[2] - Math.max(a[0], a[2])), f) : (h = Math.max(Math.min(h, b[2] - a[0] - a[2]), f), b[1] += (a[0] - a[2]) / 2), h <
            b[2] ? (b[2] = h, b[3] = Math.min(c(e.innerSize || 0, h), h), this.translate(b), this.drawDataLabels && this.drawDataLabels()) : l = !0);
            return l
        });
        m.column && (m.column.prototype.alignDataLabel = function (a, c, d, f, l) {
            var e = this.chart.inverted, h = a.series, k = a.dlBox || a.shapeArgs,
                m = u(a.below, a.plotY > u(this.translatedThreshold, h.yAxis.len)),
                p = u(d.inside, !!this.options.stacking);
            k && (f = n(k), 0 > f.y && (f.height += f.y, f.y = 0), k = f.y + f.height - h.yAxis.len, 0 < k && (f.height -= k), e && (f = {
                x: h.yAxis.len - f.y - f.height, y: h.xAxis.len - f.x - f.width, width: f.height,
                height: f.width
            }), p || (e ? (f.x += m ? 0 : f.width, f.width = 0) : (f.y += m ? f.height : 0, f.height = 0)));
            d.align = u(d.align, !e || p ? "center" : m ? "right" : "left");
            d.verticalAlign = u(d.verticalAlign, e || p ? "middle" : m ? "top" : "bottom");
            b.prototype.alignDataLabel.call(this, a, c, d, f, l);
            a.isLabelJustified && a.contrastColor && a.dataLabel.css({color: a.contrastColor})
        })
    })(K);
    (function (a) {
        var B = a.Chart, H = a.each, E = a.objectEach, q = a.pick;
        a = a.addEvent;
        a(B.prototype, "render", function () {
            var a = [];
            H(this.labelCollectors || [], function (f) {
                a = a.concat(f())
            });
            H(this.yAxis || [], function (f) {
                f.options.stackLabels && !f.options.stackLabels.allowOverlap && E(f.stacks, function (f) {
                    E(f, function (f) {
                        a.push(f.label)
                    })
                })
            });
            H(this.series || [], function (f) {
                var l = f.options.dataLabels, n = f.dataLabelCollections || ["dataLabel"];
                (l.enabled || f._hasPointLabels) && !l.allowOverlap && f.visible && H(n, function (l) {
                    H(f.points, function (f) {
                        f[l] && (f[l].labelrank = q(f.labelrank, f.shapeArgs && f.shapeArgs.height), a.push(f[l]))
                    })
                })
            });
            this.hideOverlappingLabels(a)
        });
        B.prototype.hideOverlappingLabels =
            function (a) {
                var f = a.length, q, n, v, u, c, b, m, d, h, k = function (a, b, c, d, f, h, k, l) {
                    return !(f > a + c || f + k < a || h > b + d || h + l < b)
                };
                for (n = 0; n < f; n++) if (q = a[n]) q.oldOpacity = q.opacity, q.newOpacity = 1, q.width || (v = q.getBBox(), q.width = v.width, q.height = v.height);
                a.sort(function (a, b) {
                    return (b.labelrank || 0) - (a.labelrank || 0)
                });
                for (n = 0; n < f; n++) for (v = a[n], q = n + 1; q < f; ++q) if (u = a[q], v && u && v !== u && v.placed && u.placed && 0 !== v.newOpacity && 0 !== u.newOpacity && (c = v.alignAttr, b = u.alignAttr, m = v.parentGroup, d = u.parentGroup, h = 2 * (v.box ? 0 : v.padding ||
                        0), c = k(c.x + m.translateX, c.y + m.translateY, v.width - h, v.height - h, b.x + d.translateX, b.y + d.translateY, u.width - h, u.height - h))) (v.labelrank < u.labelrank ? v : u).newOpacity = 0;
                H(a, function (a) {
                    var b, c;
                    a && (c = a.newOpacity, a.oldOpacity !== c && a.placed && (c ? a.show(!0) : b = function () {
                        a.hide()
                    }, a.alignAttr.opacity = c, a[a.isOld ? "animate" : "attr"](a.alignAttr, null, b)), a.isOld = !0)
                })
            }
    })(K);
    (function (a) {
        var B = a.addEvent, H = a.Chart, E = a.createElement, q = a.css, f = a.defaultOptions, l = a.defaultPlotOptions,
            t = a.each, n = a.extend, v = a.fireEvent,
            u = a.hasTouch, c = a.inArray, b = a.isObject, m = a.Legend, d = a.merge, h = a.pick, k = a.Point,
            e = a.Series, p = a.seriesTypes, r = a.svg, I;
        I = a.TrackerMixin = {
            drawTrackerPoint: function () {
                var a = this, b = a.chart.pointer, c = function (a) {
                    var c = b.getPointFromEvent(a);
                    void 0 !== c && (b.isDirectTouch = !0, c.onMouseOver(a))
                };
                t(a.points, function (a) {
                    a.graphic && (a.graphic.element.point = a);
                    a.dataLabel && (a.dataLabel.div ? a.dataLabel.div.point = a : a.dataLabel.element.point = a)
                });
                a._hasTracking || (t(a.trackerGroups, function (d) {
                    if (a[d]) {
                        a[d].addClass("highcharts-tracker").on("mouseover",
                            c).on("mouseout", function (a) {
                            b.onTrackerMouseOut(a)
                        });
                        if (u) a[d].on("touchstart", c);
                        a.options.cursor && a[d].css(q).css({cursor: a.options.cursor})
                    }
                }), a._hasTracking = !0);
                v(this, "afterDrawTracker")
            }, drawTrackerGraph: function () {
                var a = this, b = a.options, c = b.trackByArea, d = [].concat(c ? a.areaPath : a.graphPath),
                    e = d.length, f = a.chart, h = f.pointer, k = f.renderer, l = f.options.tooltip.snap, g = a.tracker,
                    m, n = function () {
                        if (f.hoverSeries !== a) a.onMouseOver()
                    }, p = "rgba(192,192,192," + (r ? .0001 : .002) + ")";
                if (e && !c) for (m = e + 1; m--;) "M" ===
                d[m] && d.splice(m + 1, 0, d[m + 1] - l, d[m + 2], "L"), (m && "M" === d[m] || m === e) && d.splice(m, 0, "L", d[m - 2] + l, d[m - 1]);
                g ? g.attr({d: d}) : a.graph && (a.tracker = k.path(d).attr({
                    "stroke-linejoin": "round",
                    visibility: a.visible ? "visible" : "hidden",
                    stroke: p,
                    fill: c ? p : "none",
                    "stroke-width": a.graph.strokeWidth() + (c ? 0 : 2 * l),
                    zIndex: 2
                }).add(a.group), t([a.tracker, a.markerGroup], function (a) {
                    a.addClass("highcharts-tracker").on("mouseover", n).on("mouseout", function (a) {
                        h.onTrackerMouseOut(a)
                    });
                    b.cursor && a.css({cursor: b.cursor});
                    if (u) a.on("touchstart",
                        n)
                }));
                v(this, "afterDrawTracker")
            }
        };
        p.column && (p.column.prototype.drawTracker = I.drawTrackerPoint);
        p.pie && (p.pie.prototype.drawTracker = I.drawTrackerPoint);
        p.scatter && (p.scatter.prototype.drawTracker = I.drawTrackerPoint);
        n(m.prototype, {
            setItemEvents: function (a, b, c) {
                var e = this, f = e.chart.renderer.boxWrapper,
                    h = "highcharts-legend-" + (a instanceof k ? "point" : "series") + "-active";
                (c ? b : a.legendGroup).on("mouseover", function () {
                    a.setState("hover");
                    f.addClass(h);
                    b.css(e.options.itemHoverStyle)
                }).on("mouseout", function () {
                    b.css(d(a.visible ?
                        e.itemStyle : e.itemHiddenStyle));
                    f.removeClass(h);
                    a.setState()
                }).on("click", function (b) {
                    var c = function () {
                        a.setVisible && a.setVisible()
                    };
                    f.removeClass(h);
                    b = {browserEvent: b};
                    a.firePointEvent ? a.firePointEvent("legendItemClick", b, c) : v(a, "legendItemClick", b, c)
                })
            }, createCheckboxForItem: function (a) {
                a.checkbox = E("input", {
                    type: "checkbox",
                    checked: a.selected,
                    defaultChecked: a.selected
                }, this.options.itemCheckboxStyle, this.chart.container);
                B(a.checkbox, "click", function (b) {
                    v(a.series || a, "checkboxClick", {
                        checked: b.target.checked,
                        item: a
                    }, function () {
                        a.select()
                    })
                })
            }
        });
        f.legend.itemStyle.cursor = "pointer";
        n(H.prototype, {
            showResetZoom: function () {
                function a() {
                    b.zoomOut()
                }

                var b = this, c = f.lang, d = b.options.chart.resetZoomButton, e = d.theme, h = e.states,
                    k = "chart" === d.relativeTo ? null : "plotBox";
                v(this, "beforeShowResetZoom", null, function () {
                    b.resetZoomButton = b.renderer.button(c.resetZoom, null, null, a, e, h && h.hover).attr({
                        align: d.position.align,
                        title: c.resetZoomTitle
                    }).addClass("highcharts-reset-zoom").add().align(d.position, !1, k)
                })
            }, zoomOut: function () {
                var a =
                    this;
                v(a, "selection", {resetSelection: !0}, function () {
                    a.zoom()
                })
            }, zoom: function (a) {
                var c, d = this.pointer, e = !1, f;
                !a || a.resetSelection ? (t(this.axes, function (a) {
                    c = a.zoom()
                }), d.initiated = !1) : t(a.xAxis.concat(a.yAxis), function (a) {
                    var b = a.axis;
                    d[b.isXAxis ? "zoomX" : "zoomY"] && (c = b.zoom(a.min, a.max), b.displayBtn && (e = !0))
                });
                f = this.resetZoomButton;
                e && !f ? this.showResetZoom() : !e && b(f) && (this.resetZoomButton = f.destroy());
                c && this.redraw(h(this.options.chart.animation, a && a.animation, 100 > this.pointCount))
            }, pan: function (a,
                              b) {
                var c = this, d = c.hoverPoints, e;
                d && t(d, function (a) {
                    a.setState()
                });
                t("xy" === b ? [1, 0] : [1], function (b) {
                    b = c[b ? "xAxis" : "yAxis"][0];
                    var d = b.horiz, f = a[d ? "chartX" : "chartY"], d = d ? "mouseDownX" : "mouseDownY", h = c[d],
                        g = (b.pointRange || 0) / 2, k = b.getExtremes(), l = b.toValue(h - f, !0) + g,
                        m = b.toValue(h + b.len - f, !0) - g, n = m < l, h = n ? m : l, l = n ? l : m,
                        m = Math.min(k.dataMin, g ? k.min : b.toValue(b.toPixels(k.min) - b.minPixelPadding)),
                        g = Math.max(k.dataMax, g ? k.max : b.toValue(b.toPixels(k.max) + b.minPixelPadding)),
                        n = m - h;
                    0 < n && (l += n, h = m);
                    n = l - g;
                    0 < n && (l =
                        g, h -= n);
                    b.series.length && h !== k.min && l !== k.max && (b.setExtremes(h, l, !1, !1, {trigger: "pan"}), e = !0);
                    c[d] = f
                });
                e && c.redraw(!1);
                q(c.container, {cursor: "move"})
            }
        });
        n(k.prototype, {
            select: function (a, b) {
                var d = this, e = d.series, f = e.chart;
                a = h(a, !d.selected);
                d.firePointEvent(a ? "select" : "unselect", {accumulate: b}, function () {
                    d.selected = d.options.selected = a;
                    e.options.data[c(d, e.data)] = d.options;
                    d.setState(a && "select");
                    b || t(f.getSelectedPoints(), function (a) {
                        a.selected && a !== d && (a.selected = a.options.selected = !1, e.options.data[c(a,
                            e.data)] = a.options, a.setState(""), a.firePointEvent("unselect"))
                    })
                })
            }, onMouseOver: function (a) {
                var b = this.series.chart, c = b.pointer;
                a = a ? c.normalize(a) : c.getChartCoordinatesFromPoint(this, b.inverted);
                c.runPointActions(a, this)
            }, onMouseOut: function () {
                var a = this.series.chart;
                this.firePointEvent("mouseOut");
                t(a.hoverPoints || [], function (a) {
                    a.setState()
                });
                a.hoverPoints = a.hoverPoint = null
            }, importEvents: function () {
                if (!this.hasImportedEvents) {
                    var b = this, c = d(b.series.options.point, b.options).events;
                    b.events = c;
                    a.objectEach(c,
                        function (a, c) {
                            B(b, c, a)
                        });
                    this.hasImportedEvents = !0
                }
            }, setState: function (a, b) {
                var c = Math.floor(this.plotX), d = this.plotY, e = this.series,
                    f = e.options.states[a || "normal"] || {}, k = l[e.type].marker && e.options.marker,
                    m = k && !1 === k.enabled, p = k && k.states && k.states[a || "normal"] || {}, g = !1 === p.enabled,
                    q = e.stateMarkerGraphic, r = this.marker || {}, t = e.chart, u = e.halo, z,
                    B = k && e.markerAttribs;
                a = a || "";
                if (!(a === this.state && !b || this.selected && "select" !== a || !1 === f.enabled || a && (g || m && !1 === p.enabled) || a && r.states && r.states[a] && !1 ===
                        r.states[a].enabled)) {
                    B && (z = e.markerAttribs(this, a));
                    if (this.graphic) this.state && this.graphic.removeClass("highcharts-point-" + this.state), a && this.graphic.addClass("highcharts-point-" + a), this.graphic.animate(e.pointAttribs(this, a), h(t.options.chart.animation, f.animation)), z && this.graphic.animate(z, h(t.options.chart.animation, p.animation, k.animation)), q && q.hide(); else {
                        if (a && p) {
                            k = r.symbol || e.symbol;
                            q && q.currentSymbol !== k && (q = q.destroy());
                            if (q) q[b ? "animate" : "attr"]({x: z.x, y: z.y}); else k && (e.stateMarkerGraphic =
                                q = t.renderer.symbol(k, z.x, z.y, z.width, z.height).add(e.markerGroup), q.currentSymbol = k);
                            q && q.attr(e.pointAttribs(this, a))
                        }
                        q && (q[a && t.isInsidePlot(c, d, t.inverted) ? "show" : "hide"](), q.element.point = this)
                    }
                    (c = f.halo) && c.size ? (u || (e.halo = u = t.renderer.path().add((this.graphic || q).parentGroup)), u.show()[b ? "animate" : "attr"]({d: this.haloPath(c.size)}), u.attr({"class": "highcharts-halo highcharts-color-" + h(this.colorIndex, e.colorIndex)}), u.point = this, u.attr(n({
                        fill: this.color || e.color, "fill-opacity": c.opacity,
                        zIndex: -1
                    }, c.attributes))) : u && u.point && u.point.haloPath && u.animate({d: u.point.haloPath(0)}, null, u.hide);
                    this.state = a;
                    v(this, "afterSetState")
                }
            }, haloPath: function (a) {
                return this.series.chart.renderer.symbols.circle(Math.floor(this.plotX) - a, this.plotY - a, 2 * a, 2 * a)
            }
        });
        n(e.prototype, {
            onMouseOver: function () {
                var a = this.chart, b = a.hoverSeries;
                if (b && b !== this) b.onMouseOut();
                this.options.events.mouseOver && v(this, "mouseOver");
                this.setState("hover");
                a.hoverSeries = this
            }, onMouseOut: function () {
                var a = this.options, b =
                    this.chart, c = b.tooltip, d = b.hoverPoint;
                b.hoverSeries = null;
                if (d) d.onMouseOut();
                this && a.events.mouseOut && v(this, "mouseOut");
                !c || this.stickyTracking || c.shared && !this.noSharedTooltip || c.hide();
                this.setState()
            }, setState: function (a) {
                var b = this, c = b.options, d = b.graph, e = c.states, f = c.lineWidth, c = 0;
                a = a || "";
                if (b.state !== a && (t([b.group, b.markerGroup, b.dataLabelsGroup], function (c) {
                        c && (b.state && c.removeClass("highcharts-series-" + b.state), a && c.addClass("highcharts-series-" + a))
                    }), b.state = a, !e[a] || !1 !== e[a].enabled) &&
                    (a && (f = e[a].lineWidth || f + (e[a].lineWidthPlus || 0)), d && !d.dashstyle)) for (f = {"stroke-width": f}, d.animate(f, h(e[a || "normal"] && e[a || "normal"].animation, b.chart.options.chart.animation)); b["zone-graph-" + c];) b["zone-graph-" + c].attr(f), c += 1
            }, setVisible: function (a, b) {
                var c = this, d = c.chart, e = c.legendItem, f, h = d.options.chart.ignoreHiddenSeries, k = c.visible;
                f = (c.visible = a = c.options.visible = c.userOptions.visible = void 0 === a ? !k : a) ? "show" : "hide";
                t(["group", "dataLabelsGroup", "markerGroup", "tracker", "tt"], function (a) {
                    if (c[a]) c[a][f]()
                });
                if (d.hoverSeries === c || (d.hoverPoint && d.hoverPoint.series) === c) c.onMouseOut();
                e && d.legend.colorizeItem(c, a);
                c.isDirty = !0;
                c.options.stacking && t(d.series, function (a) {
                    a.options.stacking && a.visible && (a.isDirty = !0)
                });
                t(c.linkedSeries, function (b) {
                    b.setVisible(a, !1)
                });
                h && (d.isDirtyBox = !0);
                !1 !== b && d.redraw();
                v(c, f)
            }, show: function () {
                this.setVisible(!0)
            }, hide: function () {
                this.setVisible(!1)
            }, select: function (a) {
                this.selected = a = void 0 === a ? !this.selected : a;
                this.checkbox && (this.checkbox.checked = a);
                v(this, a ? "select" :
                    "unselect")
            }, drawTracker: I.drawTrackerGraph
        })
    })(K);
    (function (a) {
        var B = a.Chart, H = a.each, E = a.inArray, q = a.isArray, f = a.isObject, l = a.pick, t = a.splat;
        B.prototype.setResponsive = function (f) {
            var l = this.options.responsive, n = [], c = this.currentResponsive;
            l && l.rules && H(l.rules, function (b) {
                void 0 === b._id && (b._id = a.uniqueKey());
                this.matchResponsiveRule(b, n, f)
            }, this);
            var b = a.merge.apply(0, a.map(n, function (b) {
                return a.find(l.rules, function (a) {
                    return a._id === b
                }).chartOptions
            })), n = n.toString() || void 0;
            n !== (c && c.ruleIds) &&
            (c && this.update(c.undoOptions, f), n ? (this.currentResponsive = {
                ruleIds: n,
                mergedOptions: b,
                undoOptions: this.currentOptions(b)
            }, this.update(b, f)) : this.currentResponsive = void 0)
        };
        B.prototype.matchResponsiveRule = function (a, f) {
            var n = a.condition;
            (n.callback || function () {
                return this.chartWidth <= l(n.maxWidth, Number.MAX_VALUE) && this.chartHeight <= l(n.maxHeight, Number.MAX_VALUE) && this.chartWidth >= l(n.minWidth, 0) && this.chartHeight >= l(n.minHeight, 0)
            }).call(this) && f.push(a._id)
        };
        B.prototype.currentOptions = function (l) {
            function n(c,
                       b, l, d) {
                var h;
                a.objectEach(c, function (a, c) {
                    if (!d && -1 < E(c, ["series", "xAxis", "yAxis"])) for (a = t(a), l[c] = [], h = 0; h < a.length; h++) b[c][h] && (l[c][h] = {}, n(a[h], b[c][h], l[c][h], d + 1)); else f(a) ? (l[c] = q(a) ? [] : {}, n(a, b[c] || {}, l[c], d + 1)) : l[c] = b[c] || null
                })
            }

            var u = {};
            n(l, this.options, u, 0);
            return u
        }
    })(K);
    return K
});