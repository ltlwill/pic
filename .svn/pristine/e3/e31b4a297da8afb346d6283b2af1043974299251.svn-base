  
/************************************自定义启动器**************************************/
	var Launcher = $L = {};
	$L._items = new Array();
	$L.ready = function(fn)
	{
		if(typeof fn == 'function')
		{
			$L._ready(fn);
		}else if(typeof fn == 'object' )
		{
			if(fn.constructor == Array)
			{
				for(var i=0;i<fn.length;i++)
				{
					$L._ready(fn[i]);
				}
			}
		}
	};
	$L._ready = function(fn)
	{
		if(typeof fn == 'function')
		{
			$L._items.push(fn);
		}
	};
	$L._launch = function()
	{
		if($L._items)
		{
			for(var i=0;i<$L._items.length;i++)
			{
				$L._items[i].call();
			}
		}
	};

	// 文档完成后执行
	;$(function(){
		$L._launch();
	});
/*************************************内容区域高度自适应***************************************/		
//	;$(function(){
//		
//		$(window).resize(autoResize);
//		
//		function autoResize()
//		{
//			window.console.log("resize window....");
////			debugger;
//			var doc,header,con,footer,docH,headerH,conH,footerH;
//			doc = $(document);
//			header = $(".pic-header");
//			var htop = $(document.body).offset().top;
//			con = $(".pic-container");
//			footer = $(".pic-footer");
//			docH = doc.height() || document.documentElement.clientHeight;
//			headerH = header.height();
//			footerH = footer.height();
//			conH = (docH - headerH - footerH - 10);
//			con.height(conH);
//		};
//		//运行高度调整 
//		autoResize();
//	});
	
/*************************************登录用户信息***************************************/	
	;(function(w){
		// 系统用户
		w.loginUser = loginUser =  {};
		SYS ? (SYS.loginUser = loginUser) : '';
		if(loginUserStr == null && loginUserStr == '')
		{
			return;
		}	
		var arr = loginUserStr.split(";");
		for(var i in arr)
		{
			var col = arr[i];
			var kv = col.split("=");
			
			loginUser[kv[0]] = (kv[1] == '' || kv[1] == null || kv[1] == 'null') ? '' : kv[1];
		}
		// 系统设置
//		w.sysSettings = sysSettings =  sysSettingsStr ? eval('(' + sysSettingsStr + ')') : [];
		w.sysSettings = sysSettings =  JSON.parse(sysSettingsStr || '[]');
	})(window);
	
/*************************************AJAX全局设置*****************************************/	
//	$.ajaxSetup({
//		
//	});
	
/*************************************slimScroll****************************************/
	 //固定菜单栏
    ;$(function () {
        $('.menu-container').slimScroll({
            height: '100%',
            distance: '0px',
            color: '#708090',
            railOpacity: 0.8,
            alwaysVisible: false
        });
    });
    
    ;(function(w){
    	w['userLocalStoreName'] = '_picUserConfig';
    	w['picLocaStorageMgr'] = {};
    	w['picLocaStorageMgr'].getUserConfig = function()
		{
			var jsonStr = window.localStorage 
				? window.localStorage.getItem(window['userLocalStoreName']) : null;
			return jsonStr ? JSON.parse(jsonStr) : {};
		};
		w['picLocaStorageMgr'].setUserConfig = function(conf)
		{
			if(!window.localStorage)
			{
				return false;
			}	
			window.localStorage.setItem(window['userLocalStoreName'],conf ? JSON.stringify(conf) : '{}');
		};
    })(window);
	
/****************************iCheck相关区域*******************************************/
	// iCheck使用的样式参数
	var iCheckDefaultOpts  = 
	{
//		checkboxClass: 'icheckbox_square-green',  // checkbox样式
//		radioClass: 'iradio_square-green',        // radio样式
		checkboxClass: 'icheckbox_square-blue',  // checkbox样式
		radioClass: 'iradio_square-blue',        // radio样式
//		checkboxClass: 'icheckbox_flat-blue',  // checkbox样式
//		radioClass: 'iradio_flat-blue',        // radio样式
	}; 
	// 自定义初始化iCheck插件方法
	$.fn.iCheck2 = function(opts)
	{
		if(typeof opts == 'string')
		{
			return this.iCheck(opts);
		}else
		{	
			opts = $.extend({},iCheckDefaultOpts,opts ? opts : {});
			return this.iCheck(opts).iCheckDefaultEvents();
		}
	};
	// iCheck绑定事件函数
	$.fn.iCheckDefaultEvents = function()
	{
		// 当选中时
		this.on("ifChecked",function(){
//        	$(this).attr("checked",true).val(true);
        	$(this).attr("checked",true).val($(this).getICheck2Value().checkedVal);
		});	
		// 当取消选中时
    	this.on("ifUnchecked",function(){
//        	$(this).attr("checked",false).val(false);	
        	$(this).attr("checked",false).val($(this).getICheck2Value().uncheckedVal);	
    	});	
    	!function(e){
    		(e && ($(e).attr('checked')) == 'true' || $(e).attr('checked') == 'checked') 
    			? $(e).val($(e).getICheck2Value().checkedVal) : $(e).val($(e).getICheck2Value().unCheckedVal);
    	}(this);
		return this;
	};
	$.fn.getICheck2Value = function()
	{
		var checkedVal = $.trim($(this).attr('checked-value') || undefined),
			unCheckedVal = $.trim($(this).attr('unchecked-value') || undefined);
		return {checkedVal: checkedVal || true,uncheckedVal:unCheckedVal || false};
	};
/*******************************jqGrid 全局设置区域***************************************/
	$.extend(true,$.jgrid.defaults,{
		styleUI : 'Bootstrap',        // 默认样式
		height: 'auto',               // 高度自增长
	    autowidth: true,              // 自动调整宽度
	    shrinkToFit: true,            //
		
		emptyrecords : '没有数据',       // 结果数据为空时显示的内容
		datatype: 'json',             // 默认数据格式
		viewrecords : true,           // 显示总记录数
		loadtext : "加载中...",
//		loadui : 'disable',           // 禁用jqGrid默认的加载提示
//		beforeRequest : function()
//		{
//			_jqgrid_load_msg_index = layer.msg("加载中...",{icon:16,shade:0.001,time:0});  // 自定义表格加载提示信息
//		},
//		loadComplete : function()
//		{
//			layer.close(_jqgrid_load_msg_index);                                        // 关闭表格加载提示信息
//		}, 
		loadError: function(xhr,status,error)
	    {
	    	layer.alert('请求数据失败！',{icon:2,offset:100});
	    },
		// 请求服务器时的请求参数名称定义
		prmNames : 
		{
			page : "currPage",           // 请求的当前页数,默认:page
			rows : "pageSize"            // 每页多少条记录,默认:rows  
		},
		// 服务器返回的JSON格式定义
		jsonReader : 
		{
			root : "rows",               // 后台回传的实际数据字段名,默认:rows
			page : "currPage",           // 当前页数字段名,默认:page
			total : "pages",             // 总页数字段名,默认:total
			records : "total"            // 总记录数字段名,默认:records
		},
	});	
	/************************************对象辅助**************************************/
	var DataUtil = 
	{
		// 表单序列化(序列化为对象)
		formSerializeObj: function($form)
		{
			var formData = $form.serializeArray();
			if(!formData || !formData.length)
			{
				return formData;
			}	
			var obj = {};
			$.each(formData,function(){
				obj[this.name] = obj[this.name] ? ',' + this.value : this.value;
			});
			return obj;
		},
		formSerializeStr: function($form)
		{
			return JSON.stringify(this.formSerializeObj($form));
		}
	};
	
	/************************************自定义下载器***********************************/
	;(function(win,$){
		var options = 
		{
			url: '',        // 下载路径	
			data: '',  	    // 请求参数
			type: 'GET',    // 请求方式 (默认GET方式)
		};
		var _downloader = win.Downloader = 
		{
			download: function(opts)
			{
				opts = $.extend(true,{},options,opts);
				if(opts.type.toUpperCase() == 'POST')
				{
					this._doPostDownload(opts);
				}else
				{
					this._doGetDownload(opts);
				}	
			},	
			_doPostDownload: function(opts)
			{
				if(!this.$postForm || !this.$postForm.length)
				{
					this._createPostForm(opts);
				}	
				// 请求参数
				this._createPostParams(opts.data);
				// 请求路径
				this.$postForm.attr('action',opts.url);
				// 提交表单
				this.$postForm.submit();
			},
			_createPostForm: function()
			{
				var targetId = 'post_download_target_' + new Date().getTime();
				this.$postForm = $('<form/>')
					.attr('id','post_download_form')
					.attr('method','POST')
					.attr('target',targetId)
					.css({'visibility':'hidden','position':'absolute','left':'-99999px','top':'-99999px'})
					.appendTo($('body'));
				$('<iframe/>').attr('id',targetId)
					.attr('name',targetId)
					.css({'visibility':'hidden','position':'absolute','left':'-99999px','top':'-99999px'})
					.appendTo($('body'));
			},
			_createPostParams: function(data)
			{
				this.$postForm.empty();
				if(!data || typeof data != 'object')
				{
					return false;
				}	
				for(name in data)
				{
					if(!name || name == '' || typeof name == 'undefined')
					{
						continue;
					}	
					$('<input/>').attr('type','hidden')
						.attr('name',name)
						.val(data[name])
						.appendTo(this.$postForm);
				}
			},
			_doGetDownload: function(opts)
			{
				if(!this.download_iframe_ele)
				{
					this.download_iframe_ele = document.createElement('iframe');
					this.download_iframe_ele.style.display = 'none';
					document.body.appendChild(this.download_iframe_ele);
				}
				var params = [];
				if(opts.data && typeof opts.data == 'object')
				{
					for(var name in opts.data)
					{
						params.push(name + '=' + opts.data[name]);
					}	
				}	
				params = encodeURI(params.join('&'));
				this.download_iframe_ele.src = opts.url + '?' + params;
			},
		};
		
	})(window,jQuery);
	
	
	
	
	
	
	
	
	