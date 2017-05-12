/**
 * 图片表格控件
 */
var IMG = {};
;(function(w,$){
	/**
	 * 默认参数配置
	 */
	IMG.defaultOpts = 
	{
		/**配置区*/	
		width:'',
		height:'',
		url:'',
		queryParams:{},
		method:'GET',//默认请求方式
		searchBtn:'',//搜索按钮选择器
		enterKey:'',//enter触发选择器
		pageSize:30, // 默认每页30条
		pageList:[10,20,50],
		showHeader:true,
		showFooter:true,
		idField:'id',//ID字段（唯一标示）
		/**事件区*/
		onSearch:function(){},     //当搜索时触发
		onBeforeSearch: function(){},// 当查询数据之前
//		loadSuccess:function(){},  //加载数据完成后
		loaded: function(){},      //当数据加载完成后
		loadError:function(){},    //加载数据失败后
		onBeforeLoad:function(){}, //加载数据之前
		onAfterShow: function(){}, // 数据显示之后
		onItemClick:function(index,row){},  // 当前每条数据被点击时,参数:index:索引位置,row:当前行数据
		/**工具栏区*/
		gridToolbar:[],  //表格级别工具栏, {iconCls:''--按钮图标,addCls:''--增加额外的class,plain:true,text:''--按钮内容,handler:function(){},--处理函数,checkPermission:function(){}--权限检查}
		imageToolbar:[]   //图片级别工具栏,{iconCls:''--图标的class,plain:true,text:''--标题内容,handler:function(){}--处理函数,checkPermission:function(){}--权限检查}
	};
	/**
	 * 表格渲染
	 */
	IMG.imageGrid = function(gridId,opts)
	{
		if(typeof opts != 'object')
		{
			opts = {};
		}
		var container = this.container = $(gridId);
		// 继承自定义的参数
		var _opts = this._opts = $.extend(true,{},this.defaultOpts,opts);
		// 初始化表格
		_initImageGrid(container,_opts);
		// 绑定事件
		_bindEvent(container,_opts);
		return this;
	};
	/**
	 * 加载数据
	 */
	IMG.load = function(opts)
	{
		opts = (typeof opts != 'object') ? {} : opts;
		// 继承参数
		$.extend(true,this._opts,opts);
		// 执行请求后台
		_doLoad.call(this);
		return this;
	};
	IMG.reload = function()
	{
		_doLoad.call(this);
		return this;
	};
	/**
	 * 获取所有数据
	 */
	IMG.getDatas = function()
	{
		return this._rows ? this._rows : [];
	};
	/**
	 * 获取选中的数据
	 */
	IMG.getSelectedRows = function()
	{
		return _getSelectedRows.call(this);
	};
	/**
	 * 选中所有
	 */
	IMG.selectAll = function(){
		_selectAll.call(this);
	};
	/**
	 * 取消所有选中
	 */
	IMG.unSelectAll = function()
	{
		_unSelectAll.call(this);
	};
	/**
	 * 删除行,参数:行索引
	 */
	IMG.deleteItem = function(index)
	{
		return _deleteItem.call(this,index);
	};
	/**
	 * 删除多行，参数：多行在所在索引
	 */
	IMG.deleteItems = function(indexArr)
	{
		return _deleteItems.call(this,indexArr);
	};
	/**
	 * 根据ID字段值获取条目在页面上的位置
	 */
	IMG.getItemIndexById = function(id)
	{
		return _getItemIndexById.call(this,id);
	};
	
	/**
	 * 查询
	 */
	function _doLoad(_params)
	{
		var _self = this;
		var opts = _self._opts;
		if(!opts || opts.url == null || opts.url == '')
		{
			w.console.log("请求出错,url=" + (opts ? opts.url : ''));
			return false;
		}	
		opts.loadSuccess = _loadSuccess;
		opts.loadError = _loadError;
		// 参数覆盖
		var params = $.extend({_r:Math.random(),currPage:1,pageSize:opts.pageSize},opts.queryParams,_params ? _params : {});
		var loadingIndex;
		$.ajax({
			url:opts.url,
			type:opts.method,
			data:params,
			dataType:opts.dataType,
			success:function(data)
			{
				if(opts.loaded && typeof opts.loaded == 'funciton')
				{
					opts.loaded.call(_self,data);
				}	
				opts.loadSuccess.call(_self,data);
				// 数据显示之后
				if(opts.onAfterShow && typeof opts.onAfterShow == 'function')
				{
					opts.onAfterShow.call(_self,data);
				}	
			},
			error:function(data)
			{
				opts.loadError.call(_self,data);
			},
			beforeSend:function()
			{
				// 显示加载中
				loadingIndex = Loading.loading({shade:0.03,container:_self.container});
				if(opts.onBeforeSearch && typeof opts.onBeforeSearch == 'function')
				{
					opts.onBeforeSearch.call(_self);
				}	
			},
			complete:function()
			{
				// 关闭加载中提示
				Loading.close(loadingIndex);
			}
		});
	};
	/**
	 * 加载成功
	 */
	function _loadSuccess(datas)
	{
		w.console.log("加载数据成功...");
		var _self = this;
		_self._rows = [];//每次查询后先重置数据
		var bodyDiv = _self.container.children(".image-grid-body");
		var dataDiv = $(bodyDiv).children(".image-grid-data");
		// 将所有图片的src属性置为空，很重要，如果不置为空，上一页的img还在加载时，会影响下一页的图片的显示速度，亲测，优点类似于阻塞队列的意思
		dataDiv.find('img').each(function(){
			$(this).attr('src','');
		});
		dataDiv.empty();
		
		if(datas != null && datas.resStatus != '200')
		{
			loadError.call(_self,datas);
		}else if( datas == null || datas.rows == null || datas.rows.length == 0)
		{
			dataDiv.append("<h3>没有查询到相关数据</h3>")
		}else
		{
			// 显示数据
			_showImageList.call(_self,dataDiv,datas);
		}	
		// 显示分业
		_showPagination.call(_self,datas);
	};
	/**
	 * 加载失败
	 */
	function _loadError(datas)
	{
		w.console.log("加载数据失败...");
		this._rows = [];//重置当前数据
		var bodyDiv = this.container.find(".image-grid-body");
		$(bodyDiv).find(".image-grid-data").empty().append("<h3>查询失败，请联系管理员</h3>");
	};
	/**
	 * 显示图片
	 */
	function _showImageList(dataDiv,datas)
	{
		var _self = this;
		var opts = _self._opts;
		var btns = opts.imageToolbar;
		dataDiv.empty();
		var ul,li,title,img,tool,selIcon,img2;
		ul = $("<ul></ul>").addClass("image-list");
		// 添加到body中的data区中
		dataDiv.append(ul);
		var imgObj = {};
		datas = (datas && datas.rows) ? datas.rows : [];
		// 缓存当前数据
		_self._rows = datas;
		// 创建条目
		for(var i=0;i<datas.length;i++)
		{
			imgObj = datas[i];
			// 创建li标签,设置ID
			li = $("<li></li>").addClass("image-item")
//				.attr("id","image-item-" + datas[i][opts.idField]).attr("index",i);
			.attr("id","image-item-" + i).attr("index",i).attr("idvalue", datas[i][opts.idField]);
				
			// 图片标题div标签
			title = $("<div></div>").addClass("image-title").html(imgObj.name).attr("title",imgObj.name);
			// 创建图片img标签,增加随机数，防止浏览器缓存相同名称的图片，导致同一个名称的图片后，显示的还是老图片的问题
//			img = $("<img/>").attr("src",imgObj.url).attr("name",imgObj.name);
			// 为了解决有的浏览器不识别带"\"的URL，二导致图片加载失败的问题，则需要将路径中的"\"字符替换为"/"字符
			img = $("<img/>").attr('original-url',imgObj.url)
//				.attr("src",imgObj.smallUrl.replace(/\\/gi,'/') + '?_r=' + (Math.random() + '_' + i)) // 显示小图
//				.attr("src",imgObj.url.replace(/\\/gi,'/') + '?_r=' + (Math.random() + '_' + i))      // 显示大图
				.attr("src",_getShowImageUrl(imgObj) + '?_r=' + (Math.random() + '_' + i))      // 显示大图
				.attr("name",imgObj.name)
				.attr('draggable',true);
			// 为了解决用户习惯上用拖拽的方式或者鼠标右键的方式下载图片时下载的时小图而不是大图的问题（会引起图片加载很慢）
//			img2 = $('<img/>').attr('src',imgObj.url)
//				.addClass('original-img')
//				.attr('draggable',true);
			// 绑定点击事件
			img.bind('click',function(){
				_onImgOrIconClick.call(_self,$(this));
			});
//			img2.bind('click',function(){
//				_onImgOrIconClick.call(_self,$(this));
//			});
			// 创建工具栏div标签
			tool = $("<div></div>").addClass("image-toolbar");
			// 创建工具条
			if(btns && btns.length > 0)
			{
				var obj,btn;
				for(var j=0;j<btns.length;j++)
				{
					obj = btns[j];
					if(!obj.checkPermission || obj.checkPermission == null || obj.checkPermission == ''  || obj.checkPermission())
					{
						tool.append($("<i></i>").addClass(obj.iconCls).attr("title",obj.text).attr("index",j).bind("click",obj.handler ? function(){
							_imageBtnsProcess.call(this,_self,$(this).attr("index"));
						} : function(){}));
					}	
				}	
			}
			// 创建选中时的图标
//			selIcon = $("<i></i>").addClass("selected-icon icon-check icon-2x");
			selIcon = $("<i></i>").addClass("selected-icon icon-check");
			// 绑定点击事件(解决已显示选中图标后点击选中图标位置区域内时不能取消)
			selIcon.bind('click',function(){
				_onImgOrIconClick.call(_self,$(this));
			});
			// 添加标题
			li.append(title);
			// 添加图片
			li.append(img);
			li.append(img2);
			// 添加工具栏
			li.append(tool);
			// 添加选中图标
			li.append(selIcon);
			// 将li添加到ul中
			ul.append(li);
		}	
	};
	
	function _getShowImageUrl(imgRow)
	{
		if(!imgRow)
		{
			return '';
		}	
		var conf = window['picLocaStorageMgr'].getUserConfig();
		return (conf['listShowBigImg'] == 'true' || conf['listShowBigImg']) 
			? imgRow.url.replace(/\\/gi,'/') : imgRow.smallUrl.replace(/\\/gi,'/');
	}
	
	function _onImgOrIconClick($ele)
	{
		var me = this;
		_itemClickProcess.call(me,$ele.parent().attr("id"),$ele.parent().attr("index"));
	};
	/**
	 * 图片按钮点击出处理
	 */
	function _imageBtnsProcess(o,index)
	{
		var opts = o._opts;
		var li = $(this).parent().parent();
		// li的位置
		var _index = li.attr("index");
		var idvalue = li.attr("idvalue");
		// 调用工具中绑定的处理事件,参数1:行数据所在位置，参数2:行数据
		opts.imageToolbar[index].handler.call(this,_index,_getRowDataById.call(o,idvalue));
	};
	/**
	 * 显示分业信息
	 */
	function _showPagination(datas)
	{
		datas = datas ? datas : {};
		var _self = this;
		var opts = _self._opts;
		var pageCon = _self.container.find(".image-grid-footer").find(".image-grid-pagination");
		var _curr = parseInt(datas.currPage ? datas.currPage : 1);
		var _pages = parseInt(datas.pages ? datas.pages : 0);
		var _total = parseInt(datas.total ? datas.total : 0);
		// 显示分业务信息
		laypage({
		    cont: pageCon, //容器。值支持id名、原生dom对象，jquery对象,
		    skip: true, //是否开启跳页
		    skin: '#add8e6',
		    groups: 3, //连续显示分页数
		    curr:_curr, // 设置当前页
			pages:_pages, //设置总页数
		    jump:function(obj,first){
//		    	debugger;
		    	var _params = $.extend({},{'currPage':obj.curr});
		    	if(first)
		    	{
		    		return;
		    	}	
		    	_doLoad.call(_self,_params);
		    }
		});
		// 显示当前记录和总页数
		var pagDesc = _self.container.find(".image-grid-footer").find(".image-grid-pag-desc").empty();
		if(_total == 0 || _pages == 0)
		{
			return;
		}
		var begin,end;
		// 计算显示开始位置
		begin = ((_pages == 0 || _total == 0 ) ? 0 : (_curr - 1) * opts.pageSize + 1);
		// 计算显示的结束位置
		end = ((_curr == _pages || _pages == 0 || _total == 0) ? ((_curr - 1) * opts.pageSize + datas.rows.length) : (_curr * opts.pageSize));
		pagDesc.empty().append("显示<font style='font-weight:bold'>" + begin
					+ "</font>到<font style='font-weight:bold'>" +  end
					+ "</font>,共<font style='font-weight:bold'>" + _total + "</font>记录"
					+ ",共<font style='font-weight:bold'>"+_pages+"</font>页");
	};
	/**
	 * 初始化图片表格
	 */
	function _initImageGrid(container,opts)
	{
		var isCreated = container.hasClass("image-grid");
		// 创建grid容器
		container.addClass("image-grid");
		// 创建头部
		_createGridHeader(container,opts,isCreated);
		// 创建标表体
		_createGridBody(container,opts,isCreated);
		// 创建底部
		_createGridFooter(container,opts,isCreated);
		// 设置表格样式
		_setGridCss(container,opts);
	};
	/**
	 * 创建表格头部
	 */
	function _createGridHeader(container,opts,isCreated)
	{
		var header; 
		if(isCreated)
		{
			header = container.children(".image-grid-header");
		}else
		{	
			header = $("<div></div>").addClass("image-grid-header");
			if(opts.gridToolbar && opts.gridToolbar.length > 0)
			{
				var tool,a,icon;
				for(var i=0;i<opts.gridToolbar.length;i++)
				{
					tool = opts.gridToolbar[i];
					if(!tool.checkPermission || tool.checkPermission == null || tool.checkPermission == ''  || tool.checkPermission())
					{
						a = $("<a><i></i>"+(tool.text ? tool.text : '')+"</a>")
							.addClass("btn pic-btn pic-btn-small")    // 固定class
							.addClass(tool.addCls ? tool.addCls : '') // 额外class
							.css({"margin":"3px auto 3px 10px","float":"left"})               // 固定css
							.bind("click",tool.handler ? tool.handler : function(){}); // 绑定事件
							
						a.find("i").addClass(tool.iconCls ? tool.iconCls : '');
						// 添加工具栏到表头
						a.appendTo(header);
					}
				}	
			}	
		}
		opts.showHeader ? header.css({"dislay":"block"}) : header.css({"display":"none"});
		// 添加到容器
		isCreated ? null : container.append(header) ;
	};
	/**
	 * 创建表格体
	 */
	function _createGridBody(container,opts,isCreated)
	{
		var body,body_data;
		if(isCreated)
		{
			body = container.children(".image-grid-body");
			body_data = body.children(".image-grid-data");
		}else
		{
			body = $("<div></div>").addClass("image-grid-body");
			body_data = $("<div></div>").addClass("image-grid-data").appendTo(body);
		}	
		
//		body_data.css({"height":opts.height ? opts.height : "auto"});
		// 添加到容器
		isCreated ? null : container.append(body);
	};
	/**
	 * 创建表格底部
	 */
	function _createGridFooter(container,opts,isCreated)
	{
		var footer = isCreated ? container.children(".image-grid-footer") 
				: $("<div></div>").addClass("image-grid-footer");
		isCreated ? null : $("<div></div>").addClass("image-grid-pagination").appendTo(footer);
		isCreated ? null : $("<div></div>").addClass("image-grid-pag-desc").appendTo(footer);
		
		opts.showFooter ? footer.css({"display":"block"}) : footer.css({"display":"none"});
		// 添加到容器
		isCreated ? null : container.append(footer);
	};
	/**
	 * 设置表格的样式
	 */
	function _setGridCss(container,opts)
	{
		var $gridHead,$gridBody,$gridFooter;
		$gridHead = container.children('.image-grid-header');
		$gridBody = container.children('.image-grid-body');
		$gridFooter = container.children('.image-grid-footer');
		// 设置样式
		var bodyW,bodyH = 0;
		if(opts.width)
		{
			bodyW = opts.width;
		}	
		if(!opts.height)
		{	
			if(opts.showHeader)
			{
				bodyH += $gridHead.outerHeight();
			}
			if(opts.showFooter)
			{
				bodyH += $gridFooter.outerHeight();
			}	
			bodyH += 'px';
		}else
		{
			bodyH = opts.height;
		}	
		$gridBody.css({"height":'calc(100% - ' + bodyH + ')','width':bodyW});
	};
	
	/**
	 * 每条数据被点击时的处理
	 */
	function _itemClickProcess(id,index)
	{
		var _self = this;
		var item = _self.container.find(".image-grid-data").find("#" + id);
		item.toggleClass("item-selected");
		if(_self._opts.onItemClick)
		{
			_self._opts.onItemClick(index,_self._rows[index]);
		}	
	}
	/**
	 * 获取选中的数据
	 */
	function _getSelectedRows()
	{
		var _self = this;
		var rows = [];
		_self.container.find(".image-grid-data").find(".image-list li.item-selected").each(function(i,o){
			var idvalue = $(this).attr("idvalue");
			// 根据ID字段值获取对应行数据
			rows.push(_getRowDataById.call(_self,idvalue));
		});
		return rows;
	};
	/**
	 * 选中所有
	 */
	function _selectAll()
	{
		this.container.find(".image-grid-data")
			.find(".image-list li.image-item").addClass("item-selected");
	};
	/**
	 * 取消所有选中 
	 */
	function _unSelectAll()
	{
		this.container.find(".image-grid-data")
		.find(".image-list li.image-item").removeClass("item-selected");
	};
	/**
	 * 删除指定的行,参数:行索引
	 */
	function _deleteItem(index)
	{
		if(index == null || $.trim(index) == '')
		{
			return false;
		}	
		var _self = this;
		// 移除页面元素 idvalue
		var li = _self.container.find(".image-grid-data")
			.find(".image-list li[id='image-item-" + index + "']");
		if(!li.length)
		{
			return false;
		}
		var idvalue = li.attr("idvalue");
		// 移除页面元素
		li.hide('normal',function(){
			$(this).remove();
		});
		for(var i=0;i<_self._rows.length;i++)
		{
			// 如果当前行数据中的唯一字段(ID)的值与li标签上的idvalue属性的值一直，则将此条数据删除
			if(_self._rows[i][_self._opts.idField] == idvalue)
			{
				_self._rows.splice(i,1);//移除数据
				break;
			}	
		}	
		return true;
	};
	/**
	 * 删除多行
	 * @param indexArr 索引数组
	 * @returns {Boolean}
	 */
	function _deleteItems(indexArr)
	{
		if(!indexArr || !indexArr.length)
		{
			return false;
		}	
		for(var i=0;i<indexArr.length;i++)
		{
			_deleteItem.call(this,indexArr[i]);
		}	
	};
	/**
	 * 根据ID字段获取条目在页面上的位置
	 */
	function _getItemIndexById(id){
		return this.container.find(".image-grid-data")
			.find(".image-list li[idvalue='" + id + "']").attr("index");
	};
	
	/**
	 * 根据id字段值获取对应行数据
	 */
	function _getRowDataById(id)
	{
		for(var i=0;i<this._rows.length;i++)
		{
			if(this._rows[i][this._opts.idField] == id)
			{
				return this._rows[i];
			}	
		}	
	};
	/**
	 * 绑定事件
	 */
	function _bindEvent(container,opts)
	{
		if(opts && opts.enterKey && opts.enterKey != '')
		{
			if(typeof opts.enterKey == 'string')
			{
				opts.enterKey = $(opts.enterKey);
			}	
			// 绑定enter按钮
			$(opts.enterKey).bind("keyup",function(e){
				e = (e ? e : w.event);
				if(e.keyCode == '13' || e.which == '13')
				{
					opts.onSearch.call(this,container,opts);
				}		
			});
		}	
		if(opts.searchBtn && opts.searchBtn != '')
		{
			$(opts.searchBtn).bind("click",function(){
				opts.onSearch.call(this,container,opts);
			});
		}
	};
})(window,jQuery);









