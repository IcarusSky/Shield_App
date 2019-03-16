/*
 Highcharts JS v6.0.7 (2018-02-16)
 Exporting module

 (c) 2010-2017 Torstein Honsi

 License: www.highcharts.com/license
*/
(function (k) {
    "object" === typeof module && module.exports ? module.exports = k : k(Highcharts)
})(function (k) {
    (function (f) {
        var k = f.defaultOptions, A = f.doc, B = f.Chart, x = f.addEvent, H = f.removeEvent, E = f.fireEvent,
            p = f.createElement, C = f.discardElement, v = f.css, n = f.merge, q = f.pick, h = f.each, F = f.objectEach,
            t = f.extend, I = f.isTouchDevice, D = f.win, G = D.navigator.userAgent, J = f.Renderer.prototype.symbols;
        /Edge\/|Trident\/|MSIE /.test(G);
        /firefox/i.test(G);
        t(k.lang, {
            printChart: "Print chart",
            downloadPNG: "Download PNG image",
            downloadJPEG: "Download JPEG image",
            downloadPDF: "Download PDF document",
            downloadSVG: "Download SVG vector image",
            contextButtonTitle: "Chart context menu"
        });
        k.navigation = {
            buttonOptions: {
                theme: {},
                symbolSize: 14,
                symbolX: 12.5,
                symbolY: 10.5,
                align: "right",
                buttonSpacing: 3,
                height: 22,
                verticalAlign: "top",
                width: 24
            }
        };
        n(!0, k.navigation, {
            menuStyle: {border: "1px solid #999999", background: "#ffffff", padding: "5px 0"},
            menuItemStyle: {
                padding: "0.5em 1em",
                background: "none",
                color: "#333333",
                fontSize: I ? "14px" : "11px",
                transition: "background 250ms, color 250ms"
            },
            menuItemHoverStyle: {
                background: "#335cad",
                color: "#ffffff"
            },
            buttonOptions: {
                symbolFill: "#666666",
                symbolStroke: "#666666",
                symbolStrokeWidth: 3,
                theme: {fill: "#ffffff", stroke: "none", padding: 5}
            }
        });
        k.exporting = {
            type: "image/png",
            url: "https://export.highcharts.com/",
            printMaxWidth: 780,
            scale: 2,
            buttons: {
                contextButton: {
                    className: "highcharts-contextbutton",
                    menuClassName: "highcharts-contextmenu",
                    symbol: "menu",
                    _titleKey: "contextButtonTitle",
                    menuItems: "printChart separator downloadPNG downloadJPEG downloadPDF downloadSVG".split(" ")
                }
            },
            menuItemDefinitions: {
                printChart: {
                    textKey: "printChart",
                    onclick: function () {
                        this.print()
                    }
                }, separator: {separator: !0}, downloadPNG: {
                    textKey: "downloadPNG", onclick: function () {
                        this.exportChart()
                    }
                }, downloadJPEG: {
                    textKey: "downloadJPEG", onclick: function () {
                        this.exportChart({type: "image/jpeg"})
                    }
                }, downloadPDF: {
                    textKey: "downloadPDF", onclick: function () {
                        this.exportChart({type: "application/pdf"})
                    }
                }, downloadSVG: {
                    textKey: "downloadSVG", onclick: function () {
                        this.exportChart({type: "image/svg+xml"})
                    }
                }
            }
        };
        f.post = function (a, b, e) {
            var c = p("form", n({method: "post", action: a, enctype: "multipart/form-data"},
                e), {display: "none"}, A.body);
            F(b, function (a, b) {
                p("input", {type: "hidden", name: b, value: a}, null, c)
            });
            c.submit();
            C(c)
        };
        t(B.prototype, {
            sanitizeSVG: function (a, b) {
                if (b && b.exporting && b.exporting.allowHTML) {
                    var e = a.match(/<\/svg>(.*?$)/);
                    e && e[1] && (e = '\x3cforeignObject x\x3d"0" y\x3d"0" width\x3d"' + b.chart.width + '" height\x3d"' + b.chart.height + '"\x3e\x3cbody xmlns\x3d"http://www.w3.org/1999/xhtml"\x3e' + e[1] + "\x3c/body\x3e\x3c/foreignObject\x3e", a = a.replace("\x3c/svg\x3e", e + "\x3c/svg\x3e"))
                }
                a = a.replace(/zIndex="[^"]+"/g,
                    "").replace(/isShadow="[^"]+"/g, "").replace(/symbolName="[^"]+"/g, "").replace(/jQuery[0-9]+="[^"]+"/g, "").replace(/url\(("|&quot;)(\S+)("|&quot;)\)/g, "url($2)").replace(/url\([^#]+#/g, "url(#").replace(/<svg /, '\x3csvg xmlns:xlink\x3d"http://www.w3.org/1999/xlink" ').replace(/ (|NS[0-9]+\:)href=/g, " xlink:href\x3d").replace(/\n/, " ").replace(/<\/svg>.*?$/, "\x3c/svg\x3e").replace(/(fill|stroke)="rgba\(([ 0-9]+,[ 0-9]+,[ 0-9]+),([ 0-9\.]+)\)"/g, '$1\x3d"rgb($2)" $1-opacity\x3d"$3"').replace(/&nbsp;/g,
                    "\u00a0").replace(/&shy;/g, "\u00ad");
                this.ieSanitizeSVG && (a = this.ieSanitizeSVG(a));
                return a
            }, getChartHTML: function () {
                return this.container.innerHTML
            }, getSVG: function (a) {
                var b, e, c, w, m, g = n(this.options, a);
                e = p("div", null, {
                    position: "absolute",
                    top: "-9999em",
                    width: this.chartWidth + "px",
                    height: this.chartHeight + "px"
                }, A.body);
                c = this.renderTo.style.width;
                m = this.renderTo.style.height;
                c = g.exporting.sourceWidth || g.chart.width || /px$/.test(c) && parseInt(c, 10) || 600;
                m = g.exporting.sourceHeight || g.chart.height || /px$/.test(m) &&
                    parseInt(m, 10) || 400;
                t(g.chart, {animation: !1, renderTo: e, forExport: !0, renderer: "SVGRenderer", width: c, height: m});
                g.exporting.enabled = !1;
                delete g.data;
                g.series = [];
                h(this.series, function (a) {
                    w = n(a.userOptions, {
                        animation: !1,
                        enableMouseTracking: !1,
                        showCheckbox: !1,
                        visible: a.visible
                    });
                    w.isInternal || g.series.push(w)
                });
                h(this.axes, function (a) {
                    a.userOptions.internalKey || (a.userOptions.internalKey = f.uniqueKey())
                });
                b = new f.Chart(g, this.callback);
                a && h(["xAxis", "yAxis", "series"], function (c) {
                    var d = {};
                    a[c] && (d[c] = a[c],
                        b.update(d))
                });
                h(this.axes, function (a) {
                    var c = f.find(b.axes, function (b) {
                        return b.options.internalKey === a.userOptions.internalKey
                    }), d = a.getExtremes(), e = d.userMin, d = d.userMax;
                    !c || void 0 === e && void 0 === d || c.setExtremes(e, d, !0, !1)
                });
                c = b.getChartHTML();
                c = this.sanitizeSVG(c, g);
                g = null;
                b.destroy();
                C(e);
                return c
            }, getSVGForExport: function (a, b) {
                var e = this.options.exporting;
                return this.getSVG(n({chart: {borderRadius: 0}}, e.chartOptions, b, {
                    exporting: {
                        sourceWidth: a && a.sourceWidth || e.sourceWidth, sourceHeight: a && a.sourceHeight ||
                        e.sourceHeight
                    }
                }))
            }, exportChart: function (a, b) {
                b = this.getSVGForExport(a, b);
                a = n(this.options.exporting, a);
                f.post(a.url, {
                    filename: a.filename || "chart",
                    type: a.type,
                    width: a.width || 0,
                    scale: a.scale,
                    svg: b
                }, a.formAttributes)
            }, print: function () {
                var a = this, b = a.container, e = [], c = b.parentNode, f = A.body, m = f.childNodes,
                    g = a.options.exporting.printMaxWidth, d, u;
                if (!a.isPrinting) {
                    a.isPrinting = !0;
                    a.pointer.reset(null, 0);
                    E(a, "beforePrint");
                    if (u = g && a.chartWidth > g) d = [a.options.chart.width, void 0, !1], a.setSize(g, void 0, !1);
                    h(m, function (a, b) {
                        1 === a.nodeType && (e[b] = a.style.display, a.style.display = "none")
                    });
                    f.appendChild(b);
                    D.focus();
                    D.print();
                    setTimeout(function () {
                        c.appendChild(b);
                        h(m, function (a, b) {
                            1 === a.nodeType && (a.style.display = e[b])
                        });
                        a.isPrinting = !1;
                        u && a.setSize.apply(a, d);
                        E(a, "afterPrint")
                    }, 1E3)
                }
            }, contextMenu: function (a, b, e, c, w, m, g) {
                var d = this, u = d.options.navigation, k = d.chartWidth, r = d.chartHeight, n = "cache-" + a, l = d[n],
                    y = Math.max(w, m), z, q;
                l || (d[n] = l = p("div", {className: a}, {position: "absolute", zIndex: 1E3, padding: y + "px"},
                    d.container), z = p("div", {className: "highcharts-menu"}, null, l), v(z, t({
                    MozBoxShadow: "3px 3px 10px #888",
                    WebkitBoxShadow: "3px 3px 10px #888",
                    boxShadow: "3px 3px 10px #888"
                }, u.menuStyle)), q = function () {
                    v(l, {display: "none"});
                    g && g.setState(0);
                    d.openMenu = !1
                }, d.exportEvents.push(x(l, "mouseleave", function () {
                    l.hideTimer = setTimeout(q, 500)
                }), x(l, "mouseenter", function () {
                    clearTimeout(l.hideTimer)
                }), x(A, "mouseup", function (b) {
                    d.pointer.inClass(b.target, a) || q()
                })), h(b, function (a) {
                    "string" === typeof a && (a = d.options.exporting.menuItemDefinitions[a]);
                    if (f.isObject(a, !0)) {
                        var b;
                        a.separator ? b = p("hr", null, null, z) : (b = p("div", {
                            className: "highcharts-menu-item",
                            onclick: function (b) {
                                b && b.stopPropagation();
                                q();
                                a.onclick && a.onclick.apply(d, arguments)
                            },
                            innerHTML: a.text || d.options.lang[a.textKey]
                        }, null, z), b.onmouseover = function () {
                            v(this, u.menuItemHoverStyle)
                        }, b.onmouseout = function () {
                            v(this, u.menuItemStyle)
                        }, v(b, t({cursor: "pointer"}, u.menuItemStyle)));
                        d.exportDivElements.push(b)
                    }
                }), d.exportDivElements.push(z, l), d.exportMenuWidth = l.offsetWidth, d.exportMenuHeight =
                    l.offsetHeight);
                b = {display: "block"};
                e + d.exportMenuWidth > k ? b.right = k - e - w - y + "px" : b.left = e - y + "px";
                c + m + d.exportMenuHeight > r && "top" !== g.alignOptions.verticalAlign ? b.bottom = r - c - y + "px" : b.top = c + m - y + "px";
                v(l, b);
                d.openMenu = !0
            }, addButton: function (a) {
                var b = this, e = b.renderer, c = n(b.options.navigation.buttonOptions, a), f = c.onclick,
                    m = c.menuItems, g, d, k = c.symbolSize || 12;
                b.btnCount || (b.btnCount = 0);
                b.exportDivElements || (b.exportDivElements = [], b.exportSVGElements = []);
                if (!1 !== c.enabled) {
                    var h = c.theme, r = h.states, p = r && r.hover,
                        r = r && r.select, l;
                    delete h.states;
                    f ? l = function (a) {
                        a.stopPropagation();
                        f.call(b, a)
                    } : m && (l = function () {
                        b.contextMenu(d.menuClassName, m, d.translateX, d.translateY, d.width, d.height, d);
                        d.setState(2)
                    });
                    c.text && c.symbol ? h.paddingLeft = q(h.paddingLeft, 25) : c.text || t(h, {
                        width: c.width,
                        height: c.height,
                        padding: 0
                    });
                    d = e.button(c.text, 0, 0, l, h, p, r).addClass(a.className).attr({
                        "stroke-linecap": "round",
                        title: q(b.options.lang[c._titleKey], ""),
                        zIndex: 3
                    });
                    d.menuClassName = a.menuClassName || "highcharts-menu-" + b.btnCount++;
                    c.symbol &&
                    (g = e.symbol(c.symbol, c.symbolX - k / 2, c.symbolY - k / 2, k, k).addClass("highcharts-button-symbol").attr({zIndex: 1}).add(d), g.attr({
                        stroke: c.symbolStroke,
                        fill: c.symbolFill,
                        "stroke-width": c.symbolStrokeWidth || 1
                    }));
                    d.add().align(t(c, {width: d.width, x: q(c.x, b.buttonOffset)}), !0, "spacingBox");
                    b.buttonOffset += (d.width + c.buttonSpacing) * ("right" === c.align ? -1 : 1);
                    b.exportSVGElements.push(d, g)
                }
            }, destroyExport: function (a) {
                var b = a ? a.target : this;
                a = b.exportSVGElements;
                var e = b.exportDivElements, c = b.exportEvents, f;
                a && (h(a,
                    function (a, c) {
                        a && (a.onclick = a.ontouchstart = null, f = "cache-" + a.menuClassName, b[f] && delete b[f], b.exportSVGElements[c] = a.destroy())
                    }), a.length = 0);
                e && (h(e, function (a, c) {
                    clearTimeout(a.hideTimer);
                    H(a, "mouseleave");
                    b.exportDivElements[c] = a.onmouseout = a.onmouseover = a.ontouchstart = a.onclick = null;
                    C(a)
                }), e.length = 0);
                c && (h(c, function (a) {
                    a()
                }), c.length = 0)
            }
        });
        J.menu = function (a, b, e, c) {
            return ["M", a, b + 2.5, "L", a + e, b + 2.5, "M", a, b + c / 2 + .5, "L", a + e, b + c / 2 + .5, "M", a, b + c - 1.5, "L", a + e, b + c - 1.5]
        };
        B.prototype.renderExporting = function () {
            var a =
                this, b = a.options.exporting, e = b.buttons, c = a.isDirtyExporting || !a.exportSVGElements;
            a.buttonOffset = 0;
            a.isDirtyExporting && a.destroyExport();
            c && !1 !== b.enabled && (a.exportEvents = [], F(e, function (b) {
                a.addButton(b)
            }), a.isDirtyExporting = !1);
            x(a, "destroy", a.destroyExport)
        };
        B.prototype.callbacks.push(function (a) {
            a.renderExporting();
            x(a, "redraw", a.renderExporting);
            h(["exporting", "navigation"], function (b) {
                a[b] = {
                    update: function (e, c) {
                        a.isDirtyExporting = !0;
                        n(!0, a.options[b], e);
                        q(c, !0) && a.redraw()
                    }
                }
            })
        })
    })(k)
});