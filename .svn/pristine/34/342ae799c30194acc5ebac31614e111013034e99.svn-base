/**
 * 菜单管理(立即执行)
 */
;(function($,w){
	var aid = $("#active_menu_id").val();
	var menuId = "menu_nav";
	var aObj;
	initStyle();
	bindEvent();
	
	// 初始化样式
	function initStyle()
	{
		if( aid == null || aid == 'null' || aid == '')
		{
			aObj = $("#" + menuId + " li:first-child").find("a:first-child");
		}else
		{
			aObj = $("#" + aid);
		}	
		aObj?aObj.addClass("bar-active"):null;
	};
	// 事件绑定
	function bindEvent()
	{
		$("#" + menuId + " li").find("a").each(function(i,o){
			$(this).bind("click",function(){
				var $ele = $(this);
				// 如果点击的菜单不是上传猜的那 且 上传功能是打开的 且 还有为完成的上传任务则提示
				if($(this).attr('id') != 'img-upload' 
					&& $('#img-upload').hasClass('bar-active') 
					&& UploadMgr.hasNotDoneTask)
				{
					layer.confirm('当前页面还有未完成的任务，确定离开此页面吗？',{btn: ['残忍离开','再待会']},
					function(index){
						layer.close(index);
						doPageForword($ele);
					},function(index){
						layer.close(index);
					});
					return false;
				}else
				{
					doPageForword($(this));
				}	
			});
		});
	};
	function doPageForword($ele)
	{
		$("#" + menuId + " li").find("a.bar-active").removeClass("bar-active");
		$ele.addClass("bar-active");
		window.location.replace($ele.attr('href'));
	}
	
})(jQuery,window);
