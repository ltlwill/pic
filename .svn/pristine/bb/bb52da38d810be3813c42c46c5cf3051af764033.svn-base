<%@page import="com.hengzhiyi.it.pic.vo.User"%>
<%@page import="com.hengzhiyi.it.pic.common.Constants"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 隐藏域 -->
<input type="hidden" id="active_menu_id" value="${active_menu_id }"/>
<input type="hidden" id="is_admin" value="${isAdmin }"/>
<div class="pic-header">
	<div class="header-container">
		<div class="user-info">
			<ul class="pic-nav">
				<li>
					当前用户:
					<a id="user_dropdown" class="dropdown">
						<span>${login_user.userName}</span>
						<span><i class="icon icon-caret-down"></i></span>
					</a>
					<ul id="user_sub_menu" class="sub-menu">
						<li>
							<a id="btn_edit_user"><i class="icon icon-edit"></i>修改资料</a>
						</li>
						<c:if test="${isAdmin }">
							<li>
								<a id="btn_sys_setting"><i class="icon icon-cog"></i>系统设置</a>
							</li>
						</c:if>
					</ul>
				</li>
				<li>
					<%-- <a href="${contextPath}/logout" target="_self">退出登录</a> --%>
					<a id="logout">退出登录</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="pic-segmentation"></div>
</div>	
<!-- clockpicker -->
<!-- 自身js引入 -->
<script type="text/javascript" src="${contextPath}/js/common/header.js?appV=${appV}"></script>
