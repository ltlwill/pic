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
		<title>用户登录</title>
		
		<!-- 动态引入 -->
		<jsp:include page="/pages/common/common_css.jsp"/>
		
		<!-- 其他css引入 -->
		<link rel="stylesheet" type="text/css" href="css/login/login.css?appV=${appV}">
	</head>
	<body>
		<!-- 内容区域 begin -->
		<div class="container">
			<!-- 登录区域 begin -->
			<div class="login-content">
				<div class="login-area">
					<!-- 头像区域 begin -->
					<div class="user-head"></div>
					<!-- 头像区域 end -->
					<div class="login-title">
						用户登录
					</div>
					<div class="login-body">
						<div class="login-form">
							<form id="user_form" method="POST" action="">
								<div class="form-row">
									<span class="wrap-icon wrap-icon-user">
										<i class="icon-user"></i>
									</span>
									<input id="name" name="name" type="text" placeholder="请输入用户名" tabindex="1"/>
								</div>
								<div class="form-row">
									<span class="wrap-icon">
										<i class="icon-key"></i>
									</span>
									<input id="password" name="password" type="password" placeholder="请输入密码" tabindex="2"/>
								</div>
							
								<div class="form-row btn-row">
									<a id="form_submit" class="sumbit-btn">登录</a>
								</div>
							</form>
						</div>
					</div>
					<div class="notice none">
						<p>注：非工作日、非工作时间段不允许登录</p>
						<p>允许登录时间段：<p>
						<div class="notice-detail">
							<!-- <p>8:30 至 12:00</p>
							<p>8:30 至 12:00</p>
							<p>8:30 至 12:00</p> -->
						</div>
					</div>
				</div>
			</div>
			<!-- 登录区域 end -->
		</div>
		<!-- 内容区域 end -->
		
		<!-- 公共js资源(动态引入) -->
		<jsp:include page="/pages/common/common_js.jsp"/>
		<!-- 其他js资源 -->
		<script type="text/javascript" src="js/login/login.js?appV=${appV}"></script>
	</body>
</html>