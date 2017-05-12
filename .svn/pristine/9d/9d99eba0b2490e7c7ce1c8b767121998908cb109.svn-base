<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	    <meta name="viewport" content="width=device-width,initial-scale=1.0"> 
		<meta name="author" content="test">
		<meta name="description" content="test">
		<meta name="copyright" content="test">
		
		<title>test page</title>
		
		<!-- 动态引入 -->
		<jsp:include page="/pages/common/common_css.jsp"/>

	</head>
	<body>
		<!-- 公共js资源(动态引入) -->
		<jsp:include page="/pages/common/common_js.jsp"/>
		
		<script type="text/javascript">
			$(function(){
				
				var o = {};
				o["document.body.clientWidth"] = document.body.clientWidth; //网页可见区域宽(body)
				o["document.body.clientHeight"] = document.body.clientHeight; //网页可见区域高(body)
				o["document.body.offsetWidth"] = document.body.offsetWidth; //网页可见区域宽(body)，包括border、margin等
				o["document.body.offsetHeight"] = document.body.offsetHeight; //网页可见区域高(body)，包括border、margin等
				o["document.body.scrollWidth"] = document.body.scrollWidth; //网页可见区域宽(body)，包括border、margin等
				o["document.body.scrollHeight"] = document.body.scrollHeight; //网页正文全文宽，包括有滚动条时的未见区域
				o["document.body.scrollTop"] = document.body.scrollTop; //网页正文全文高，包括有滚动条时的未见区域
				o["document.body.scrollLeft"] = document.body.scrollLeft;  //网页被卷去的Left(滚动条)
				o["window.screenTop"] = window.screenTop;   //浏览器距离Top
				o["window.screenLeft"] = window.screenLeft;  //浏览器距离Left
				o["window.screen.height"] = window.screen.height; //屏幕分辨率的高
				o["window.screen.width"] = window.screen.width; //屏幕分辨率的宽
				o["window.screen.availHeight"] = window.screen.availWidth;  //屏幕可用工作区的高
				o["window.screen.availWidth"] = window.screen.availWidth; //屏幕可用工作区的宽
				
				window.console.log(JSON.stringify(o));
				
			});
		
		</script>
	</body>
</html>