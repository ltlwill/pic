/****************************zTree 相关区域*******************************************/
;(function($){
	function zTree(ele,nodes,setting,type)
	{
		this.$ele = ele;
		this.nodes = nodes;
		this.setting = $.extend(true,{},this.setting,setting);
		this.type = type;
		this._init();
		return this;
	};
	var pt = zTree.prototype;
	pt.setting = 
	{
		check: {
			enable: true,
//				nocheckInherit: true,
		},
		view: {
			dblClickExpand: false,
			showLine: true,
			selectedMulti: false,
			showIcon: false, // 是否显示节点图标
		},	
		data: {
			simpleData: {
				enable:true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: ""
			}
		},
		width: 'auto',
		height: '250px',
		minWidth:'200px',
		minHeight: '250px',
		onOpen: function(target){},                                // 打开选择框时
		onClose: function(target){},                               // 关闭选择框时 
		onSelected: function(treeId,treeNode,target,ids,names){},  // 选择节点后回调
		onClear: function(atreeEle,target){},                      // 当清空时触发的函数
		autoClose: true,                                           // 选择节点后自动关闭下拉数据 
	};
	pt.config = 
	{
		zTreeClass: 'ztree',
		openClass: 'ztree-open',
		arrowClass: 'ztree-arrow',
		arrowOpenClass: 'ztree-arrow-open',
		clearClass: 'ztree-clear',
		types:
		{
			normal: 'normal',
			select: 'select',
		},
		divStyles:
		{
			'display':'none',
			'position': 'absolute',
			'border': '1px solid #00A2D4',
			'background-color': '#FFF',
			/*'border-top': 'none',*/
			'z-index': '99',
		},	
		ulStyles:
		{
			'max-height':'100%',
			'margin-top': '0px',
			'width': '100%',
			'overflow': 'auto',
		},	
	};
	pt._init = function()
	{
		this.$ele.data('This',this);
		this.$ele.attr('id') ? null : this.$ele.attr('id',new Date().getTime());
		this._setDefaultEvent();
//			this.$ele.addClass(this.config.zTreeClass);
		// 下拉型的树,需要创建额外的辅助元素
		if(this.type == this.config.types.select)
		{
			this._createSelectEles();
		}
		var $tree = this._getTreeEle();
		$tree.addClass(this.config.zTreeClass);
		$.fn.zTree.init($tree,this.setting,this.nodes);
	};
	pt._createSelectEles = function()
	{
		var $div,$ul,$wrapper,$arrow,$clear,offset = this.$ele.offset();
		$div = $('<div/>').css(this.config.divStyles).attr('id','container_' + this.$ele.attr('id'))
			.css({'width':this.setting.width,'height':this.setting.height,'min-width':this.setting.minWidth || '','min-height':this.setting.minHeight || ''})
			.appendTo($('body'));
		$ul = $('<ul/>').css(this.config.ulStyles).appendTo($div)
			.attr('id','select_' + this.$ele.attr('id'));
		
		$arrow = $('<span/>').addClass(this.config.arrowClass)
			.attr('id','arrow_' + this.$ele.attr('id'))
//			.css({position:'absolute','z-index': '99','top': '0px','right':'0px'})
			.append($('<b>'));
		$wrapper = $('<div/>').css({position:'relative'});
		this.$ele.wrap($wrapper).after($arrow);
		
		$clear = $('<span>×</span>').addClass(this.config.clearClass)
			.attr('id','clear_' + this.$ele.attr('id'));
		$arrow.after($clear);
		
		this.$ele.data('SelectContainer',$div);
		this.$ele.data('Arrow',$arrow);
		this.$ele.data('Clear',$clear);
		// 绑定事件
		this._bindEvent();
	},
	pt._setDefaultEvent = function()
	{
		var me = this;
		this.setting.callback = this.setting.callback || {};
		$.extend(true,this.setting.callback,
		{
			onClick: function(e, treeId, treeNode)
			{
				me._onClick.call(me,e, treeId, treeNode);
			},
			onCheck: function(e, treeId, treeNode)
			{
				me._onCheck.call(me,e, treeId, treeNode);
			},
		});
	};
	pt._onClick = function(e, treeId, treeNode)
	{
		var zTree = this.getZTreeObj();
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		this.setting.onClick ? this.setting.onClick.call(this,e, treeId, treeNode) : null;
		return false;
	},
	pt._onCheck = function(e, treeId, treeNode)
	{
		var zTree = this.getZTreeObj(),
		nodes = zTree.getCheckedNodes(true),
		ids = '',names = '';
		for (var i=0, l=nodes.length; i<l; i++) {
			names += nodes[i]['name'] + ',';
			ids += nodes[i]['id'] + ',';
		}
		names = (names.length > 0) ? names.substring(0,names.length-1) : '';
		ids = (ids.length > 0) ? ids.substring(0, ids.length-1) : '';
		// 对外回调
		if(this.setting.onSelected)
		{
			this.setting.onSelected.call(this,treeId,treeNode,this.$ele,ids,names);
		}	
		// 选择后自动关闭
		if(this.setting.autoClose)
		{
			this._hideSelectMenu();
		}	
	};
	pt._bindEvent = function()
	{
		var me = this,
			$container = me.$ele.data('SelectContainer'),
			$select = $container.children('ul');
		this.$ele.bind('click',function(){
			$(this).data('isOpen') ? me._hideSelectMenu() : me._showSelectMenu();
		});
		this.$ele.data('Arrow').bind('click',function(evt){
			$(this).data('isOpen') ? me._hideSelectMenu() : me._showSelectMenu();
		});
		this.$ele.data('Clear').bind('click',function(){
			// 取消所有的勾选
			var nodes = me.getZTreeObj().getCheckedNodes(true);
			if(nodes && nodes.length){
				for(var i=0;i<nodes.length;i++){
					 me.getZTreeObj().checkNode(nodes[i], false, true)
				}
			}
			me.$ele.val('').trigger('change');
			if(me.setting.onClear){
				me.setting.onClear.call(me,me.$ele,this);
			}	
		});
		$('body').bind("mousedown",function(evt){
			if (!(evt.target.id == $container.attr('id') || evt.target.id == me.$ele.attr('id') 
				|| evt.target.id == me.$ele.data('Arrow').attr('id')
				|| evt.target.id == me.$ele.data('Clear').attr('id')
				|| $(evt.target).parents('#' + $container.attr('id')).length > 0)) {
				me._hideSelectMenu();
			}
		});
	};
	pt._showSelectMenu = function()
	{
		var offset = this.$ele.offset(),
			$container = this.$ele.data('SelectContainer');
		$container.css({left:offset.left + "px",top:(offset.top 
			+ this.$ele.outerHeight() - 1) + "px",width:(this.$ele.outerWidth()) + 'px'})
			.show();
		this.$ele.addClass(this.config.openClass).data('isOpen',true);
		this.$ele.data('Arrow').addClass(this.config.arrowOpenClass).data('isOpen',true);
		if(this.setting.onOpen){
			this.setting.onOpen(this,this.$ele);
		}
	};
	pt._hideSelectMenu = function(){
		this.$ele.data('SelectContainer').hide();
		this.$ele.removeClass(this.config.openClass).data('isOpen',false);
		this.$ele.data('Arrow').removeClass(this.config.arrowOpenClass).data('isOpen',false);
		if(this.setting.onClose){
			this.setting.onClose(this,this.$ele);
		}
	};
	pt._getTreeId = function()
	{
		return (this.type == this.config.types.select) ? 
			this.$ele.data('SelectContainer').children('ul').attr('id') : this.$ele.attr('id');
	};
	pt._getTreeEle = function()
	{
		return (this.type == this.config.types.select) ? 
			this.$ele.data('SelectContainer').children('ul') : this.$ele;
	};
	pt.getZTreeObj = function()
	{
		return $.fn.zTree.getZTreeObj(this._getTreeId());
	};
	pt.destory = function()
	{
		var obj = this.getZTreeObj();
		obj ? obj.destory() : null;
		return this;
	};
	pt._invoke = function($ele,params,args,type)
	{
		if(params && typeof args == 'string' 
			&& params.indexOf('_') != 0
			&& typeof pt[params] == 'function')
		{
			// 如果没有创建过
			var THIS = $ele.data("This");
			if(!THIS)
			{
				return;
			}	
			return pt[params].apply(THIS,Array.prototype.slice.call(args, 1));
		}else if(args && args.length)
		{
			var nodes = args[0] ? args[0] : [],
				setting = args[1] ? args[1] : {};
			return new zTree($ele,nodes,setting,type);
		}else
		{
			$.error("zTree not support the arguments:" + params);
		}
	};
	pt._newInstance = function($ele,params,args,type)
	{
		var obj = {},ins;
		$ele.each(function(){
			 ins = pt._invoke($(this),params,args,type);
			 obj[String[this.id] || String(this.name)] = ins;
		});
		return ($ele.length > 1) ? obj : ins;
	};
	
	// 封装ztree树渲染方式
	$.fn.ztree = function(args)
	{
		return pt._newInstance(this,args,arguments);
	};
	// 封装ztree下拉选择渲染方式
	$.fn.ztreeSelect = function(args)
	{
		return pt._newInstance(this,args,arguments,'select');
	};
})(jQuery);