/** 回显剩余字符 */
;
$(function() {
	$("input,textarea").each(
			function(i, o) {
				if ($(this).attr("maxlength")) 
			{
					bindEvent(this);
				}
			});

	function bindEvent(o) 
	{
		$(o).focus(function() {
			showLaveCharMessage(o);
		}).keyup(function() {
			showLaveCharMessage(o);
		}).blur(function() {
			removeLaveCharMessage(o);
		});
		
//		$(o).click(function() {
//			showLaveCharMessage(o);
//		});	
	}

	function showLaveCharMessage(o) 
	{
		removeLaveCharMessage(o);
		createMessageTips(o);
	}
	
	function createMessageTips(o)
	{
		var backgroundColor = '#6caef5',textColor='#FFF';
		var tip = $("<div></div>").addClass("lavelength_msg_tips").css({'width':'auto','display':'inline'});

		//创建tip内容
		var maxLen = parseInt($(o).attr("maxlength"));
		var currLen = parseInt($(o).val().length);
		var laveLen = maxLen - currLen;
		var tipContent = $("<div>").addClass("lavelength_msg_tips_content").css({
			'min-width':'20px',
			'width':'auto',
			'padding':'1px 12px',
			'margin':'0px',
			'font-size':'10px',
			'display':'inline'
		});
		tipContent.append("剩余字数:").append($("<strong></strong>").css({
			'font-family':"'微软-雅黑',arial,georgia,verdana,helvetica,sans-serif",
//			'font-family':"georgia",
			'font-size': '12px',
			'padding': '0px',
			'margin':'0px 0px 0px 2px',
			'font-style':'italic',
			'font-weight':'bold'
		}).text(laveLen));
		
		tip.append(tipContent).append($("<i></i>"));
//		$(o).after(tip);
		$('body').append(tip);
		
		// 位置调整
		Postion.auto(o,tip);
	}
	
	function parsePix(pix)
	{
		if(!pix || pix == '' || pix == null)
		{
			return pix;
		}	
		try
		{
			if(pix.indexOf('px'))
			{
				return parseFloat(pix.substring(0,pix.indexOf('px')));
			}else
			{
				return pix;
			}	
		}catch (e) {
			return pix;
		}
	}
	
	function removeLaveCharMessage(o) 
	{
//		$(o).siblings("div.lavelength_msg_tips").remove();
		$('body').children("div.lavelength_msg_tips").remove();
	}
	
	/**
	 * 位置调整
	 */
	window.Postion = {
		options:{
			backgroundColor:'#6caef5',
			textColor:'#FFF',
//			spacing:10,//good
			spacing:12
		},	
		defaultTipsCss:{
			'position':'absolute',
			'z-index':'99999',
			'color':'#FFF',
			'background':'#6caef5',
			'min-width':'50px',
			'border-radius':'5px',
			'border':'none',
			'margin':'0px',
			'padding':'0px',
			'line-height':'130%',
			'text-align':'center'
		},	
		defaultArrowCss:{
			'width':'0',
			'height':'0',
			'position':'absolute',
//			'border-width':'6',//good
			'border-width':'8',
			'border-color':'transparent',
			'border-style':'dashed'
//			'display':'inline-block'
		},
		// 自动调整	
		auto:function(t,tip)
		{
			var opts = this.calc(t,tip);
			this.resize(t,tip,opts);
		},
		// 计算位置
		calc:function(t,tip)
		{
			var win=$(window),to = $(t).offset();
			var opts = {};
			// 自动位置调整优先级:右>左>上>下
			// 计算右边
			if(win.width() - (to.left + $(t).outerWidth() + $(tip).outerWidth() + this.options.spacing) > 0)
			{
				opts.tipCss = {
						'left' : to.left + $(t).outerWidth() + this.options.spacing,
						'top' :  to.top
				};
				opts.arrowCss = {
						'border-bottom-color':this.options.backgroundColor,
						'border-bottom-style':'solid',
						'left':(0 -  this.defaultArrowCss['border-width'] + 1),
//						'top':this.defaultArrowCss['border-width']/2 //在border-width=6时最好
						'top':$(tip).outerHeight()/2-this.defaultArrowCss['border-width'] - 3//border-witdh=8时最好
					};
			}
			// 计算左边
			else if(to.left - (win.scrollLeft() + $(tip).outerWidth() + this.options.spacing) > 0)
			{
				opts.tipCss = {
						'left' :  to.left - ($(tip).width() + this.options.spacing),
						'top' :  to.top
				};
				opts.arrowCss = {
						'border-bottom-color':this.options.backgroundColor,
						'border-bottom-style':'solid',
						'left':$(tip).outerWidth() - this.defaultArrowCss['border-width'] - 1, 
//						'top':this.defaultArrowCss['border-width']/2  //在border-width=6时最好
						'top':$(tip).outerHeight()/2-this.defaultArrowCss['border-width'] - 3//border-witdh=8时最好
					};
			}
			// 计算上边
			else if(to.top - (win.scrollTop() + $(tip).outerHeight() + this.options.spacing) > 0)
			{
				opts.tipCss = {
						'left' : to.left + $(t).outerWidth()/2 - $(tip).outerWidth()/2,
						'top' :  to.top - $(tip).outerHeight() - this.options.spacing
				};
				opts.arrowCss =  {
						'border-top-color':this.options.backgroundColor,
						'border-top-style':'solid',
						'left':$(tip).outerWidth()/2 - this.defaultArrowCss['border-width']/2,
//						'top':$(tip).outerHeight() + 1 //造border-width=6时最好
						'top':$(tip).outerHeight()     // 在border-width=8时最好
					};
			}
			// 否则是下边
			else
			{
				opts.tipCss = {
						'left' :  to.left + $(t).outerWidth()/2 - $(tip).outerWidth()/2,
						'top' :  to.top + $(t).outerHeight() + this.options.spacing
				};
				opts.arrowCss = {
						'border-bottom-color':this.options.backgroundColor,
						'border-bottom-style':'solid',
						'left':$(tip).outerWidth() / 2 - this.defaultArrowCss['border-width']/2,
//						'top':(0-$(tip).outerHeight()/2 - 1)//在border-width=6时最好
						'top':(0-$(tip).outerHeight()/2 - this.defaultArrowCss['border-width']/2)//在border-width=8时最好
					};
			}	
			return opts;
		},
		// 调整
		resize:function(t,tip,opts)
		{
			$(tip).css($.extend({},this.defaultTipsCss,opts.tipCss));
			$(tip).find("i").css($.extend({},this.defaultArrowCss,opts.arrowCss));
		}
			
	};

});
