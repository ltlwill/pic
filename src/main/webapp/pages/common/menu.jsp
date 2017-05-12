<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="menu-container">
	<!-- <div class="menu-content"> -->
		<ul id="menu_nav" class="pic-nav menu-nav">
			<li>
				<a id="img-home" href="${contextPath}/home">
					<i class="img-icon-home"></i>
					<span>主界面</span>
				</a>
			</li>
			<li>
				<a id="img-search" href="${contextPath}/search/toSearch">
					<i class="img-icon-search"></i>
					<span>图片查询</span>
				</a>
			</li>
			<li>
				<a id="img-upload" href="${contextPath}/upload/toUpload" valide-task="true">
					<i class="img-icon-upload"></i>
					<span>图片上传</span>
				</a>
			</li>
			<li>
				<a id="img-setting" href="${contextPath}/user/setting">
					<i class="img-icon-setting"></i>
					<span>用户设置</span>
				</a>
			</li>
			<c:if test="${isAdmin }">
			<li>
				<a id="img-sys" href="${contextPath}/sys/manager">
					<i class="img-icon-manager"></i>
					<span>系统管理</span>
				</a>
			</li>
			</c:if>
		</ul>
	<!-- </div> -->
</div>




























