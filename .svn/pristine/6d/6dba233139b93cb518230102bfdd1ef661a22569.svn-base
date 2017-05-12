/**
 * 图片上传管理JS
 */
var UploadMgr = 
{
	// 初始化
	init:function()
	{
		UploadMgr.initEles();
		UploadMgr.initWebUploader();
	},
	initEles: function()
	{
		this.$bindAccountInp = $('#account_bind_inp');
		// 是否有还未完成的任务
		this.hasNotDoneTask = false;
		// 初始化ztree
		this.initZtree();
	},
	// 初始化上传工具
	initWebUploader:function()
	{
		var me = this;
		// 存放预览图的容器
		$list = $("#fileList");
	    // 优化retina, 在retina下这个值是2
        ratio = window.devicePixelRatio || 1;
        // 缩略图大小
        thumbnailWidth = 100 * ratio;
        thumbnailHeight = 100 * ratio;
        // 文件总数
        filesCount = 0;
        // 文件总大小
        filesSize = 0;
        // 记录服务器一存在的文件数量
        serverExistsFileSize = 0;
        // 清除所有文件按钮
        delAllBtn = $("#del_files_btn");
        // 上传按钮
        uploadBtn = $("#upload_files_btn");
        // 进度条
        processBar = $("#process_status");
        // 描述区域
        desDiv = $("#upload_description");
        // 文件大小描述区
        fileSizeDesc = $("#file_status");
        // 文件大小缓存，用于记录当前文件的大小，因为在文件上传后文件的大小会发生变化，导致重新计算总文件大小时，如：出现0个文件时总大小不等于0等情况，所以先记录未上传时文件的大小
        _fileSizeCache = {};
        // 文件上传控件对象
        uploader = null;
        
		// 上传工具参数	
		var options = {
			dnd:"#file_upload_panel",	
			auto:false,  //选择完文件后，是否自动上传
			swf:contextPath + "/plugins/webuploader/Uploader.swf", // swf文件路径
			server:contextPath + "/upload/uploadImage", //接收文件的服务器
			formData:$.extend({_r:Math.random()},SYS.loginUser),//请求参数
			pick:'#filePicker', //选择文件的按钮，内部根据当前运行是创建，可能是input元素，也可能是flash.
			// 只允许选择图片文件。
		    accept: {
		        title: 'Images',
		        extensions: 'gif,jpg,jpeg,bmp,png',
//		        mimeTypes: 'image/*',  // 这种方式在某些版本的浏览器(如：chrome53)上打开文件选择对话框很慢
		        mimeTypes: 'image/jpg,image/jpeg,image/png,image/gif,image/bmp',
		    },
		    disableGlobalDnd: true,
		    compress: false, // 上传时不压缩图片
	        /*chunked: true,*/
	        fileNumLimit: 100,//总文件数不超过100个
	        fileSizeLimit: 100 * 1024 * 1024, // 总文件大小不超过100 M
	        fileSingleSizeLimit: 1 * 1024 * 1024 // 单个文件大小不超过1 M
		};
		// 创建容器
		uploader = WebUploader.create(options);
	    // 添加“添加文件”的按钮，
	    uploader.addButton({
	        id: '#filePicker2',
	        label: '点击选择图片'
	    });
	    // 初始化
	    initUploadConatiner.call();
	    
		// 监听有文件添加进来时
		uploader.on("fileQueued",function(file){
			var $li =  $(
		            '<div id="' + file.id + '" class="file-item thumbnail upload-item">' +
	                	'<img>' +
	                	'<div class="info" title="' + file.name + '">' + file.name + '</div>' +
	                	'<i class="icon-trash icon-2x del-item-btn" title="删除"></i>' + 
	                '</div>'
	        	);
			var $img = $li.find("img");
			// 绑定删除文件事件
			$li.find("i").bind("click",function(){
				// 删除文件
				delSingleFile(file);
			});
			// $list为容器jQuery实例
		    $list.append( $li );
		    
		    // 创建缩略图
		    // 如果为非图片文件，可以不用调用此方法。
		    // thumbnailWidth x thumbnailHeight 为 100 x 100
		    uploader.makeThumb( file, function( error, src ) {
		        if ( error ) {
		            $img.replaceWith('<span>不能预览</span>');
		            return;
		        }

		        $img.attr( 'src', src );
		    }, thumbnailWidth, thumbnailHeight );
		});
		uploader.on("uploadBeforeSend",function(o,params,headers){
			headers['X-Requested-With'] = 'XMLHttpRequest';
			// 将createTime置为空''，解决后端接收createTime类型转换出错的问题
			params['createTime'] = '';
			params['accountId'] = me.$bindAccountInp.attr('data-id') || '';
		});
		 // 文件上传过程中创建进度条实时显示。
	    uploader.on( 'uploadProgress', function( file, percentage ) {
	        var $li = $( '#'+file.id ),
	            $percent = $li.find('.progress span');

	        // 避免重复创建
	        if ( !$percent.length ) {
	            $percent = $('<p class="progress"><span></span></p>')
	                    .appendTo( $li )
	                    .find('span');
	        }

	        $percent.css( 'width', percentage * 100 + '%' );
	    });

	    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
	    uploader.on( 'uploadSuccess', function( file,response ) {
	    	// 文件已存在
	    	if(response == '1001')
    		{
	    		// 记录上传时，服务器已存在的文件数量
	    		serverExistsFileSize = serverExistsFileSize + 1 ;
	    		
	    		// 文件上传失败处理
		    	uploadErrorProcess(file,response);
    		}else
	    	{	
	    		$( '#'+file.id ).addClass('upload-state-done');
	    	}
	    });

	    // 文件上传失败，现实上传出错。
	    uploader.on( 'uploadError', function( file,reason) {
	    	// 文件上传失败处理
	    	uploadErrorProcess(file);
	    });

	    // 完成上传完了，成功或者失败，先删除进度条。
	    uploader.on( 'uploadComplete', function( file ) {
	        $( '#'+file.id ).find('.progress').remove();
	        // 更新进度条状态
	        updateProcessBarStatus('block');
	    });
	    // 当文件被加入队列后
	    uploader.on('fileQueued',function(file){
	    	window.console.log('queue file,file name:' + file.name);
	    	// 缓存文件大小
	    	cacheFileSize(file);
	    });
	    // 当文件被移除队列后触发
	    uploader.on('fileDequeued',function(file){
	    	window.console.log("dequeued file,fileId: "+file.id+";file name:" + file.name);
	    	// 从缓存中移除
	    	removeFileSizeCache(file);
	    });
	    // 当一批文件被加入队列后(单个文件也会触发)
	    uploader.on('filesQueued',function(files){
	    	window.console.log('一批文件加入  ,文件个数:' + files.length);
	    	// 更新状态
	    	updateStatus(files,'queued');
	    });
	    // 当开始航船流程时触发
	    uploader.on("startUpload",function(){
	    	me.hasNotDoneTask = true;
	    	buttonsEventHandle('unbind');
	    });
	    // 当所有文件上传结束时触发
	    uploader.on("uploadFinished",function(){
	    	me.hasNotDoneTask = false;
	    	buttonsEventHandle('bind');
	    });
	    // 文件上传失败处理
	    function uploadErrorProcess(file,errorCode)
	    {
	    	var $li = $( '#'+file.id ),
            $error = $li.find('div.error');

	        // 避免重复创建
	        if ( !$error.length ) {
	            $error = $('<div class="error"></div>').appendTo( $li );
	        }
	        var text = (errorCode == '1001') ? "失败(已存在)" : "上传失败";
	        $error.html( text );
	    };
	    
	    // 初始化容器
	    function initUploadConatiner()
	    {
	    	delAllBtn.css({"display":"none"});
	    	uploadBtn.css({"display":"none"});
	    	buttonsEventHandle('bind');
	    	setDescriptionContent();
	    };
	    // 按钮事件处理
	    function buttonsEventHandle(type)
	    {
	    	if( type == 'bind' )
	    	{
	    		delAllBtn.bind("click",delAllFiles).removeClass("disable").addClass("custom-toolbar");
	    		uploadBtn.bind("click",uploadAllFiles).removeClass("disable").addClass("custom-toolbar");
	    	}else if( type == 'unbind')	
    		{
	    		delAllBtn.unbind("click").removeClass("custom-toolbar").addClass("disable");
	    		uploadBtn.unbind("click").removeClass("custom-toolbar").addClass("disable");
    		}
	    };
	    
	    // 删除所有文件
	    function delAllFiles()
	    {
	    	var files = uploader.getFiles();//
	    	if(files != null && files.length > 0)
    		{
	    		for(var i in files)
    			{
	    			uploader.removeFile(files[i],true);//从队列中删除
    			}
	    		uploader.reset();// 重置
	    		// 删除页面中的元素
	    		$list.empty();
	    		// 更新状态
	    		updateStatus();
	    		// 重置文件大小缓存
	    		resetFileSizeCache();
    		}
	    	
	    };
	    // 删除单个文件
	    function delSingleFile(file)
	    {
	    	window.console.log("Before delete,files length=" + uploader.getFiles().length );
	    	window.console.log("Delete file,file id:" + file.id );
	    	// 从队列中删除文件
	    	uploader.removeFile(file,true);
	    	window.console.log("After delete,files length=" + uploader.getFiles().length );
	    	// 从页面元素中删除
	    	$list.find('#' + file.id).remove();
	    	// 更新状态
	    	updateStatus([file],'dequeued');
	    };
	    // 上传所有图片
	    function uploadAllFiles()
	    {
	    	window.console.log("上传图片....");
	    	uploader.upload();
	    };
	    // 更改状态
	    function updateStatus(files,type)
	    {
	    	if(  files == null )
	    	{
	    		files = [];
	    	}else if(files != null && typeof files != 'object')
    		{
	    		files = [files];
    		}
	    	// 设置文件信息
	    	setFileInfo(files,type);
	    	// 设置元素状态
	    	setElementsStatus(files);
	    };
	    // 设置元素状态
	    function setElementsStatus(files,type)
	    {
	    	var addBtn = $("#filePicker");
	    	if(filesCount > 0 && filesSize > 0 )
    		{
	    		desDiv.css({"display":"none"});
	    		addBtn.find(".webuploader-pick").html("继续添加");
	    		uploadBtn.css({"display":"block"});
	    		delAllBtn.css({"display":"block"})
	    		fileSizeDesc.css({"display":"block"});
    		}else
    		{
    			desDiv.css({"display":"block"});
    			addBtn.find(".webuploader-pick").html("选择图片");
    			uploadBtn.css({"display":"none"});
    			delAllBtn.css({"display":"none"})
    			fileSizeDesc.css({"display":"none"});
    		}	
    		// 更新进度条状态
	        updateProcessBarStatus('none');
	    };
	    // 设置文件信息
	    function setFileInfo(files,type)
	    {
	    	calcFileInfo(files,type);
	    	fileSizeDesc.css({"display":"block"})
	    		.html("当前:共<span style='font-weight: bold'>" + 
	    		filesCount + "</span>张图片,共<span style='font-weight: bold'>" + WebUploader.formatSize(filesSize)) + "</span>";
	    };
	    // 计算文件信息
	    function calcFileInfo(files,type)
	    {
	    	switch(type)
	    	{
	    		case 'queued':
	    			filesCount += files.length;
	    			for(var i=0;i<files.length;i++)
	        		{
	    	    		filesSize += files[i].size;
	        		}
	    			break;
	    		case 'dequeued' :
	    			filesCount -= files.length;
	    			for(var i=0;i<files.length;i++)
	        		{
	    				var _key = files[i].id + files[i].name;
	    				var _size = _fileSizeCache[_key];
	    				// 删除文件时计算文件总大小用当前缓存的数据
	    	    		filesSize -= _size;
	        		}
	    			break;
	    		default : 
	    			filesCount = 0;
	    			filesSize = 0;
	    			break;
	    	};
	    	filesCount = (filesCount < 0) ? 0 : filesCount;
	    	filesSize = (filesSize < 0) ? 0 : filesSize;
	    };
	    // 设置描述信息内容
	    function setDescriptionContent()
	    {
	    	var p = desDiv.find("p");
	    	if(!p.length)
    		{
		    	var _num = options.fileNumLimit ? options.fileNumLimit : false;
		    	var _size = options.fileSizeLimit ? WebUploader.formatSize(options.fileSizeLimit) : false;
		    	var _ssize = options.fileSingleSizeLimit ? WebUploader.formatSize(options.fileSingleSizeLimit) : false;
		    	var desc = '<p>或将图片拖到这里';
		    	if(_num)
	    		{
		    		desc += '，最多可添加' + _num + '张';
	    		}
		    	if(_size){
		    		desc += '，总图片大小不超过' + (Math.round(_size.indexOf('M') > -1 ? _size.substring(0,_size.indexOf('M')) : _size)) + 'M';
		    	}
		    	if(_ssize)
	    		{
		    		desc += '，单个图片大小不超过' + ('1024.00K' != _ssize ? (Math.round(_ssize.indexOf('M') > -1 ? _ssize.substring(0,_ssize.indexOf('M')) : _ssize)) + 'M' : '1M');
	    		}
		    	desc += '</p>';
		    	desDiv.append(desc);
    		}
	    };
	    
	    // 更新进度条状态
	    function updateProcessBarStatus(display)
	    {
    		processBar.empty().css({"display":display});
    		if(display == 'none')
    		{
    			processBar.empty();
    			return ;
    		}	
    		// 获取上传状态
    		var status = uploader.getStats();
    		// 成功文件总数
    		var successNum = (status.successNum > 0) 
    			? (status.successNum - serverExistsFileSize) : status.successNum;
    		// 失败文件总数(包含服务器中一存在的文件数)
    		var failNum = status.uploadFailNum + serverExistsFileSize;
    		
    		var sSpanMsg = '已上传' + (successNum + failNum)
				+ '张，其中成功' + successNum + ',失败' + failNum + '';
    		var process = (successNum + failNum)/filesCount * 100;
    		if(process % 100 == 0)
    		{
    			process = new Number(process).toFixed() + '%';
    		}else
			{
        		process = new Number(process).toFixed(1) + '%';
			}
    		var content = '<span>' + sSpanMsg + '</span>' 
    			+ '<div class="process-panel"> '
    			+ '<span>' + process + '</span>'
    			+ '<div style="width:'+process+'"></div>'
    			+' </div>';
    		processBar.empty().append(content);
	    };
	    
	    function cacheFileSize(file)
	    {
	    	if(file == null || typeof file == 'undefined')
	    	{
	    		return;
	    	}	
	    	var key = file.id + file.name;
	    	_fileSizeCache = _fileSizeCache ? _fileSizeCache : {};
	    	_fileSizeCache[key] = file.size;
	    };
	    function removeFileSizeCache(file)
	    {
	    	var key = file.id + file.name;
	    	if(key in _fileSizeCache)
    		{
	    		delete key;
    		}
	    };
	    function resetFileSizeCache()
	    {
	    	_fileSizeCache = {};
	    };
	    
	},
	initZtree: function()
	{
		var me = this;
		me.requestZtreeData();
	},
	requestZtreeData: function(){
		var me = this;
		$.ajax({
			url: contextPath + '/sys/getBindAccountsByList',
			success: function(data){
				data = data || [];
				me.requestZtreeDataFlag = true;
				me.createAccountTree(data);
			},
			error: function(){
				me.requestZtreeDataFlag = false;
			}
		});
	},
	createAccountTree: function(zNodes){
		var me = this;
		if(zNodes && zNodes.length)
		{
			for(var i=0;i<zNodes.length;i++)
			{
				zNodes[i].open = true;
				zNodes[i].nocheck = true;
			}	
		}	
		var opts = 
		{
			check: {
				chkStyle: "radio",
				radioType: "all"
			},
			onOpen: function(){
				if(!me.requestZtreeDataFlag){
					me.requestZtreeData();
				}
			},
			onSelected: function(treeId,treeNode,target,ids,names)
			{
				$(target).val(names).attr('data-id',ids).trigger('change');
			},
			onClear: function(target,ele)
			{
				$(target).attr('data-id','');
			},
			autoClose: false
		};
		this.$bindAccountInp.ztreeSelect(zNodes,opts);
	}
};
// 准备启动
SYS.ready(UploadMgr.init);


















