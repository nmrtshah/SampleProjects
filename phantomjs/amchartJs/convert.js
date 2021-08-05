(function () {
   var mapCLArguments,
            render,
            args,
            pick,
            system = require('system'),
            fs = require('fs'),
            serverMode = false;
    pick = function () {
        var args = arguments, i, arg, length = args.length;
        for (i = 0; i < length; i += 1) {
            arg = args[i];
            if (arg !== undefined && arg !== null && arg !== 'null' && arg != '0') {
                return arg;
            }
        }
    };
    mapCLArguments = function () {
        var map = {},
                i,
                key;

        for (i = 0; i < system.args.length; i += 1) {
            if (system.args[i].charAt(0) === '-') {
                key = system.args[i].substr(1, i.length);
                if (key === 'infile' || key === 'callback' || key === 'dataoptions' || key === 'globaloptions' || key === 'customcode') {
                    try {
                        map[key] = fs.read(system.args[i + 1]).replace(/^\s+/, '');
                    } catch (e) {
                        console.log('Error: cannot find file, ' + system.args[i + 1]);
                        phantom.exit();
                    }
                } else {
                    map[key] = system.args[i + 1];
                }
            }
        }
        return map;
    };

    render = function (params, exitCallback) {

        var page = require('webpage').create(),
                scaleAndClipPage,
                createChart,
                input,
                constr,
                callback,
                width,
                output,
                outType,
                convert,
                exit;
        window.optionsParsed = false;
        window.callbackParsed = false;

        page.onConsoleMessage = function (msg) {
            console.log(msg);
        };

        page.onAlert = function (msg) {
            console.log(msg);
        };

        /* scale and clip the page */
        scaleAndClipPage = function (svg) {
            /*	param: svg: The scg configuration object
             */

            var zoom = 1,
                    pageWidth = pick(params.width, svg.width),
                    clipwidth,
                    clipheight;

            if (parseInt(pageWidth, 10) == pageWidth) {
                zoom = pageWidth / svg.width;
            }

            /* set this line when scale factor has a higher precedence
             scale has precedence : page.zoomFactor = params.scale  ? zoom * params.scale : zoom;*/

            /* params.width has a higher precedence over scaling, to not break backover compatibility */
            page.zoomFactor = params.scale && params.width == undefined ? zoom * params.scale : zoom;

            clipwidth = svg.width * page.zoomFactor;
            clipheight = svg.height * page.zoomFactor;

            // define the clip-rectangle 
            page.clipRect = {
                top: 0,
                left: 0,
                width: clipwidth,
                height: clipheight
            };
        };

        exit = function (result) {
            if (serverMode) {
                //Calling page.close(), may stop the increasing heap allocation
                page.close();
            }
            exitCallback(result);
        };

        convert = function (svg) {
            scaleAndClipPage(svg);
            page.render(output);
            exit("success");
        };

        createChart = function (input, globalOptionsArg, dataOptionsArg, customCodeArg,callback) {

            $(document.body).css('backgroundColor', 'white');

            function loadScript(varStr, codeStr) {
                var $script = $('<script>').attr('type', 'text/javascript');
                $script.html('var ' + varStr + ' = ' + codeStr);
                document.getElementsByTagName("head")[0].appendChild($script[0]);
            }

            if (input !== 'undefined') {
                loadScript('options', input);
            }
            if (callback !== 'undefined') {
                loadScript('cb', callback);
            }
            if (globalOptionsArg !== 'undefined') {
                loadScript('globalOptions', globalOptionsArg);
            }

            if (dataOptionsArg !== 'undefined') {
                loadScript('dataOptions', dataOptionsArg);
            }

            if (customCodeArg !== 'undefined') {
                loadScript('customCode', customCodeArg);
            }
            dataOptionsArg = jQuery.parseJSON(dataOptionsArg);


            var divEle = document.createElement("div");
            divEle.style.width = dataOptionsArg.width;
            divEle.style.height = dataOptionsArg.height;
            divEle.id = "chartdiv";
            document.body.appendChild(divEle);
            var chart, chartSvg;
            var x = dataOptionsArg.functionName;
            var fn = window[x];

            chart = fn();
            chart.dataProvider = jQuery.parseJSON(input);
            chart.startDuration = 0;

            chart.addListener("rendered", function (e)
            {
                var interval = setInterval(function () {
                    if (window.fabric) {
                        clearTimeout(interval);
                        chart.export.capture({}, function () {
                            var cfg = this.deepMerge({
                                format: "png",
                                quality: 1,
                                multiplier: 1
                            }, {});
                            this.toSVG(cfg, function (base64) {
                                chartSvg = base64;
                            });
                        });
                    }
                }, 100);
            });
            chart.write("chartdiv");
            return {
                html: chartSvg,
                width: divEle.style.width,
                height: divEle.style.Height,
                imgUrls: ''
            };
        };
        
        
 
            input = params.infile;
            output = params.outfile;

            if (output !== undefined) {
                outType = pick(output.split('.').pop(), 'png');
            } else {
                outType = pick(params.type, 'png');
            }

            constr = pick(params.constr, 'Chart');
            callback = params.callback;
            width = params.width;
            page.open('about:blank', function (status) {
                var svg,
                        globalOptions = params.globaloptions,
                        dataOptions = params.dataoptions,
                        customCode = 'function customCode(options) {\n' + params.customcode + '}\n',
                        jsfile;
                    if (page.injectJs("jquery.1.9.1.min.js"))
                    {
                        if (page.injectJs(params.jsMainFile))
                        {
                            var configfiles = params.importsFiles.split(",");
                            for (jsfile in configfiles) {
                                if (configfiles.hasOwnProperty(jsfile)) {
                                    page.injectJs(configfiles[jsfile]);
                                }
                            }
                        }
                        dataOptions = '{"height": "' + params.height + 'px","width": "' + params.width + '","functionName":"' + params.functionName + '"}';
                        svg = page.evaluate(createChart, input, globalOptions, dataOptions, customCode,callback);
                        convert(svg);
                    }
            });
    };
    args = mapCLArguments();
    render(args, function (msg) {
        console.log(msg);
        phantom.exit();
    });
}());
