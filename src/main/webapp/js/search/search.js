/**
 * 图片搜索功能管理JS
 */
var SearchMgr = {
	config:
	{
		searchUrl: "/search/doSearch",   // 搜索请求的路径
		downloadUrl: "/search/download", // 单个下载URL
		batchDownloadUrl: "/search/batchDownload", // 批量下载URL
		deleteUrl: "/search/deleteImages", // 删除图片URL
		delBtnPermission:false, // 删除图片的权限
	},
	// 初始化
	init:function()
	{
		SearchMgr.config.delBtnPermission = $("#del_img_permission").val();
		// 绑定事件
		SearchMgr.bindEvent();
		//初始化图片表格
		SearchMgr.initImageGrid();
	},
	// 初始化图片表格
	initImageGrid:function()
	{
		var gridOptions = 
		{
//			height:'650px',
			method:'POST',
			url: contextPath + SearchMgr.config.searchUrl,
			pageSize: window['picLocaStorageMgr'].getUserConfig().imgGridNum || 36,  // 每页显示图片数量
			searchBtn:'#img_search_area div',//查询按钮选择器
			enterKey:"#keyWord",//enter键触发选择器
			onSearch:SearchMgr.doSearch, //绑定触发查询时的处理事件,
			onBeforeSearch: SearchMgr.onBeforeSearch,
			onAfterShow:SearchMgr.afterShow,
			gridToolbar:[
			     {iconCls:'icon-check icon-large check',text:'全选',handler:SearchMgr.selectBtnProcess},       
			     {iconCls:'icon-download icon-large',text:'下载全部',handler:function(){
			    	 SearchMgr.downloadImage.call(this,'all');
			     }},    
			     {iconCls:'icon-download icon-large',text:'下载选中',handler:function(){
			    	 SearchMgr.downloadImage.call(this,'selected');
			     }},   
			     {iconCls:'icon-trash icon-large',text:'删除选中',handler:function(){
			    	 SearchMgr.deleteImage.call(this,'selected');
			     },checkPermission:SearchMgr.checkDelBtnPermission},
            ],
			imageToolbar:[
		         {iconCls:'icon-zoom-in icon-2x left',text:'预览图片',handler:SearchMgr.viewOriginalImage},
		         {iconCls:'icon-trash icon-2x mg-r-20',text:'删除图片',handler:function(index,row){
		        	 SearchMgr.deleteImage.call(this,'single',index,row);
		         },checkPermission:SearchMgr.checkDelBtnPermission},
		         {iconCls:'icon-copy icon-2x scale-7',text:'复制地址'},
		         {iconCls:'icon-download icon-2x right',text:'下载图片',handler:function(){
		        	 SearchMgr.downloadImage.call(this,'single');
		         }},
			]
		};
		// 初始化图片表格面板
		SearchMgr.imageGrid = IMG.imageGrid("#img_search_grid",gridOptions);
		// 页面初始化后自动查询图片数据
		SearchMgr.doSearch();
	},
	// 绑定事件
	bindEvent:function()
	{
//		$("#img_search_area div").bind("click",this.doSearch);//绑定搜索事件
	},
	// 搜索
	doSearch:function(o,opts)
	{
		// 请求数据,加载表格
		SearchMgr.imageGrid.load({queryParams:{'keyWord':$.trim($("#keyWord").val()),'userId':window.loginUser.userId}});
		
	},
	// 查看原图
	viewOriginalImage:function()
	{
		// 如果有相册实例
		if(!SearchMgr.myphoto)
		{
			var img = $(this).parent().siblings("img");
			// 浏览器打开
			window.open($(img).attr("original-url"),$(img).attr("name")); 
		}else
		{
			// 相册打开
			SearchMgr.myphoto.open($('#img_search_grid').find('.image-list').children('li').index($(this).parent().parent()));
		}	
	},
	// 选择按钮事件处理
	selectBtnProcess:function()
	{
		//切换样式
		if($(this).children("i").hasClass("check"))
		{
			// 全选
			SearchMgr.imageGrid.selectAll();
			$(this).html($(this).html().replace(/全选/gi,"取消"));
			$(this).children("i").removeClass("check").addClass("uncheck");
		}else
		{
			// 取消全选
			SearchMgr.imageGrid.unSelectAll();
			$(this).html($(this).html().replace(/取消/gi,"全选"));
			$(this).children("i").removeClass("uncheck").addClass("check");
		}
	},
	// 下载图片
	downloadImage:function(type){
		var url;
		switch(type)
		{
			case 'single' :
				var img = $(this).parent().siblings("img");
				url = contextPath + SearchMgr.config.downloadUrl + "?path=" +  $(img).attr("name");
				break;
			case 'all' :
				url = SearchMgr.generateBatchUrl(SearchMgr.imageGrid.getDatas());
				break;
			case 'selected' :
				url = SearchMgr.generateBatchUrl(SearchMgr.imageGrid.getSelectedRows());
				break;
		};
		if(!url || url == '' || url == null)
		{
			return;
		}
		SearchMgr.doDownload(url);	
	},
	// 删除图片
	deleteImage:function(type,index,row)
	{
		var ids = [];
		var indexArr = [];
		switch(type)
		{
			case 'single':
				ids.push(row.id);
				indexArr.push(index);
				break;
			case 'selected':
				var rows = SearchMgr.imageGrid.getSelectedRows();
				for(var i=0;i<rows.length;i++)
				{
					ids.push(rows[i].id);
					// 获取对应的位置索引
					indexArr.push(SearchMgr.imageGrid.getItemIndexById(rows[i].id));
				}	
				break;
		};
		if(!ids || !ids.length)
		{
			return false;
		}	
		layer.confirm('确定要删除吗？', {
			icon:3,
		    btn: ['是','否'] //按钮
		}, function(){
			// 请求删除
			$.ajax({
				url:contextPath + SearchMgr.config.deleteUrl,
				type:'POST',
				data:{ids:ids.join(",")},
				async:false,//同步
				success:function(res)
				{
					if(res == 'ok')
					{	
						// 删除页面元素
						SearchMgr.imageGrid.deleteItems(indexArr);
						layer.msg("删除成功！",{icon:1});
					}else
					{
						layer.msg("删除失败！",{icon:2});
					}	
				},
				error:function(res)
				{
					layer.msg('出错了！', {icon: 5});
				}
			});
		}, function(){
		    return true;
		});
	},
	// 生成批量文件下载的URL
	generateBatchUrl:function(rows)
	{
		if(!rows || !rows.length)
		{
			return null;
		}	
		// 如果只有一条数据则请求单文件下载（不会打包），
		if(rows.length == 1)
		{
			return contextPath + SearchMgr.config.downloadUrl + "?path=" + rows[0].name;
		}
		var ids = [];
		for(var i=0;i<rows.length;i++)
		{
			ids.push(rows[i].id);
		}
		return contextPath + SearchMgr.config.batchDownloadUrl + "?ids=" + ids.join(",");
	},
	doDownload:function(url)
	{
		if(!SearchMgr.download_image_iframe)
		{
			SearchMgr.download_image_iframe = document.createElement("iframe");
			SearchMgr.download_image_iframe.style.display = 'none';
			document.body.appendChild(SearchMgr.download_image_iframe);
		}	
		SearchMgr.download_image_iframe.src = url;
		
	},
	checkDelBtnPermission:function()
	{
		return (SearchMgr.config.delBtnPermission == '' ||  SearchMgr.config.delBtnPermission == null) ? false : true;
	},
	afterShow: function(datas)
	{
		var me = SearchMgr;
		// 1.鼠标移入事件显示大图
		var conf = window['picLocaStorageMgr'].getUserConfig();
		if(conf['mousehoverShowBigImg'] == 'true' || conf['mousehoverShowBigImg'])
		{
			$('#img_search_grid').find('.image-item img').bind('mouseover mouseout',function(evt){
				me.showBigImage($(this),evt || window.event);
			});
		}
		// 2.复制图片地址
		var btns = $('#img_search_grid').find('.image-toolbar').children('.icon-copy');
		var clip = new ZeroClipboard( btns );
		clip.on("beforecopy", function(evt){ 
			evt = evt || window.event;
			var $target = $(evt.target),
				data = '';
			if($target.attr('id') == 'btn_photo_copy_addr')
			{
				data = $target.parent().siblings('.content').children('img').attr('src');
			}else
			{
				data = $(evt.target).parent().siblings('img').attr('original-url');
			}	
			clip.setData('text/plain',data);
		});
		clip.on("aftercopy", function(){ 
			layer.msg('已复制到剪贴板',{icon:1,shift:0});
		});
		clip.on("error", function(){ 
			layer.msg('复制失败',{icon:2,shift:0});
		});
		// 3.相册功能，不需要时，只需注释掉下面代码即可，其他不用改
		var $imgList = $('#img_search_grid').find('.image-list');
		me.myphoto = $imgList.myPhoto({
			urlAttrName:'original-url',
			btns:[
					{id:'btn_photo_copy_addr',iconCls:'icon icon-copy icon-2x scale-7',alt:'复制地址'},
					{iconCls:'icon icon-download-alt icon-2x',alt:'下载图片',hanlder:function($img,imgIndex){
						me.downloadImage.call($imgList.children('li').eq(imgIndex)
							.children('.image-toolbar').children('.icon-download'),'single');
					}},
				]
		});
		clip.clip($('#btn_photo_copy_addr'));
	},
	showBigImage: function($ele,evt)
	{
		var $body = $('body'),$div = $body.find('.preview-big-img-container');
		if(!$div.length)
		{
			$div = $('<div/>').addClass('preview-big-img-container').append($('<img>')).appendTo($body);
		}
		if(evt.type == 'mouseout')
		{
			$div.hide();
			return false;
		}	
		var $img = $div.find('img'),
			$li = $ele.parent('li'),
			margin = 5,
			maxW = 500,
			maxH = 500
			bodyW = $body.width(),
			bodyH = $body.height(),
			style = {},
			pRightCls = 'position-right',
			pLeftCls = 'position-left',
			aBottomCls = 'align-bottom',
			aTopCls = 'align-top';
		$img.attr('src',$ele.attr('original-url'))
//			.css({'width':$ele[0].naturalWidth || $ele.width(),'height':$ele[0].naturalHeight || $ele.height(),'max-width':maxW,'max-height':maxH});
		.css({'max-width':maxW,'max-height':maxH});
		var off = $li.offset();
		// 计算左右位置
		if(bodyW - (off.left + $li.outerWidth() + margin) > maxW)
		{
			style.left = off.left + $li.outerWidth() + margin;     // 右侧显示
			$div.removeClass(pLeftCls).addClass(pRightCls);
		}else
		{
			style.left = (off.left - $div.outerWidth() - margin);  // 左侧显示	 
			$div.removeClass(pRightCls).addClass(pLeftCls);
		}	
		// 计算上下位置
		if((bodyH - (off.top + $li.outerHeight()) > maxH) || off.top < $li.outerHeight())
		{
			style.top = off.top;   // 顶部对齐
			$div.removeClass(aBottomCls).addClass(aTopCls);
		}else
		{
			style.top = off.top - ($div.outerHeight() - $li.outerHeight());  // 底部对齐
			$div.removeClass(aTopCls).addClass(aBottomCls);
		}	
		$div.css(style).show();
	},
	onBeforeSearch: function()
	{
		 $('body').find('.preview-big-img-container img').attr('src','');
	},
};

// 准备启动
SYS.ready(SearchMgr.init);























