<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${contextPath }/css/home/home.css?appV=${appV}">

<div class="pic-child-container">
	<div class="pic-child-content">
		<%-- <div>
			<img alt="" src="${contextPath}/images/building.jpg"/>
		</div> --%>
		<div id="content-container" class="row" style="">
			
		</div>
		
		<%-- <div class="row" style="display: none">
			<div class="col col-sm-4">
				<div class="img-wrapper">
					<img src="${contextPath }/images/android_logo.jpg"/>
					<p class="platform">
						易图Android版
					</p>
					<p>
						版本：V 1.0(Beta版)
					</p>
					<p>
						系统要求：Android&nbsp;4.4及以上
					</p>
					<p class="placeholder">
						&nbsp;
					</p>
					<p class="download">
						<a href="">立即下载</a>
					</p>
				</div>
			</div>
			<div class="col col-sm-4">
				<div class="img-wrapper barcode">
					<img src="${contextPath }/images/app_barcode.png"/>
					<p>
						扫码下载
					</p>
				</div>
			</div>
			<div class="col col-sm-4">
				<div class="img-wrapper">
					<img src="${contextPath }/images/ios_logo.jpg"/>
					<p class="platform">
						易图iOS版
					</p>
					<p>
						版本：V 1.0(Beta版)
					</p>
					<p>
						系统要求：iOS&nbsp;7.0及以上<span class="important">(需越狱,越狱有风险，需谨慎)</span>
					</p>
					<p class="download">
						<a href="">立即下载</a>
					</p>
				</div>
			</div>
		</div> --%>
	</div>
</div>	
<script id="template_content" type="text/html">
			<div class="col col-sm-4">
				<div class="img-wrapper">
					<img src="{{contextPath}}{{android.logoPath}}"/>
					<p class="platform">
						{{android.name}}
					</p>
					<p>
						{{android.versionDesc}}
					</p>
					<p>
						{{android.platformDesc}}
					</p>
					<p class="placeholder">
						&nbsp;
					</p>
					<p class="download">
						<a href="{{android.webUrl}}">立即下载</a>
					</p>
				</div>
			</div>
			<div class="col col-sm-4">
				<div class="img-wrapper barcode">
					<div class="barcode-content"></div>
					<p>扫码下载</p>
				</div>
			</div>
			<div class="col col-sm-4">
				<div class="img-wrapper">
					<img src="{{contextPath}}{{ios.logoPath}}"/>
					<p class="platform">
						{{ios.name}}
					</p>
					<p>
						{{ios.versionDesc}}
					</p>
					<p>
						{{ios.platformDesc}}<span class="important">{{ios.warning}}</span>
					</p>
					<p class="download">
						<a href="{{ios.webUrl}}">立即下载</a>
					</p>
				</div>
			</div>
</script>
<!-- 引入自身JS-->
<script type="text/javascript" src="${contextPath }/js/plugins/arttemplate/arttemplate.js?appV=${appV}"></script>
<script type="text/javascript" src="${contextPath }/js/plugins/qrcode/qrcode.min.js?appV=${appV}"></script>
<script type="text/javascript" src="${contextPath }/js/home/home.js?appV=${appV}"></script>


























