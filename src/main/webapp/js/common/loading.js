/**
 * 加载中提示工具
 */
;(function(w,$){
	
	var o = {
		index:0,
		loading:function(opts)
		{
			return o.open(opts);
		},
		open:function(opts)
		{
			var ff = new $f(opts);
			return ff.index;
		},
		close:function(index)
		{
			$("body").find("#ui-loading-shade" + index).remove();
			$("body").find("#ui-loading-area" + index).remove();
		},
		closeAll:function()
		{
			$("body").find("div[id^='ui-loading-shade']").remove();
			$("body").find("div[id^='ui-loading-area']").remove();
		}
	};
	
	function $f(opts)
	{
		var _s = this;
		_s.index = ++ o.index;
		_s.options = $.extend(_s.options,opts ? opts : {});
		_s.create();
	};
	$f.pt = $f.prototype;
	$f.pt.options = 
	{
		shade:0.1,//阴影模糊值
		container:'',//容器,如果不提供则默认为document
		zIndex:199999,//层叠值
		background:'#000'//默认阴影颜色
	};
	$f.pt.items = {
		'shade':'<div class="ui-loading-shade" style="position:absolute;top:0px;left:0px;"></div>',
		'icon':'<div class="ui-loading-area"><div class="ui-loading-content"></div></div>'
	};
	$f.pt.create = function()
	{
		var _s = this;
		var opts = _s.options;
		var whObj = _s.calcContainer();
		// 遮罩层
		var shade = $(_s.items.shade);
		// 图标层
		var icon = $(_s.items.icon);
		shade.css({'z-index':opts.zIndex + _s.index,
				'background-color':opts.background,
				'opacity':opts.shade,
				'filter':'alpha(opacity='+(100 * opts.shade)+')',
				'width':whObj.width,
				'height':whObj.height
				});
		shade.attr("id",'ui-loading-shade' + _s.index).attr("times",_s.index);
		icon.css({
			'position':'absolute',
			'z-index':opts.zIndex + (_s.index + 1),
			'left':'50%',
			'top':'40%'
		});
		icon.attr("id",'ui-loading-area' + _s.index).attr("times",_s.index);
		icon.find(".ui-loading-content").css({
			'background':'url(' + contextPath + '/images/loading-0.gif) no-repeat scroll',
			'width':'60px',
			'height':'24px'
		});
		// 
		whObj.container.append(shade);
		whObj.container.append(icon);
	};
	$f.pt.calcContainer = function()
	{
		var _s = this;
		var opts = _s.options;
		var width,height,container;
		if(opts.container != '' && opts.container != null)
		{
			container = $(opts.container);
			width = $(opts.container).width();
			height = $(opts.container).height();
		}else
		{
			container = $('body');
			width = $('body').width();
			height = $('body').height();
		}
		return {'width':width,'height':height,'container':container};
	};
	Loading = o;
})(window,jQuery);





