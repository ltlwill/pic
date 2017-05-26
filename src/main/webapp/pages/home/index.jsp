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
		<title>恒之易-图片管理系统</title>
		
		<!-- 动态引入 -->
		<jsp:include page="/pages/common/common_css.jsp"/>
		
	</head>
	<body>
		<!-- 头部区 begin -->
		<%-- <jsp:include page="/pages/common/header.jsp"/> --%>
		<%@include file="/pages/common/header.jsp" %>
		<!-- 头部区 end -->
		
		<!-- 内容区 begin -->
		<div class="container pic-container">
			<div class="pic-content">
				<!-- 左侧菜单区域begin -->
				<div class="pic-area-col area-left">
					<%-- <jsp:include page="/pages/common/menu.jsp"/> --%>
					<%@include file="/pages/common/menu.jsp" %>
				</div>
				<!-- 左侧菜单区 end -->
				
				<!-- 右侧内容区 begin -->
				<div class="pic-area-col area-right">
					<!-- jsp:includ和c:import引入的页面中c:if标签不生效,会解析成dom节点 -->
					<%-- <jsp:include page="${target_page_name == null ? defaultPage : target_page_name}"/> --%>
					<%-- <c:import url="${target_page_name }"></c:import> --%>
					
					<!-- < % @include 中的file又不能用el表达式（如：${target_page_name}）动态指定，只好使用choose来一个个列举 -->
					<c:choose>
						<c:when test="${active_menu_id == 'img-home'}">
							<%@include file="/pages/home/home.jsp" %>
						</c:when>
						<c:when test="${active_menu_id == 'img-search'}">
							<%@include file="/pages/search/search.jsp" %>
						</c:when>
						<c:when test="${active_menu_id == 'img-upload'}">
							<%@include file="/pages/upload/upload.jsp" %>
						</c:when>
						<c:when test="${active_menu_id == 'img-setting'}">
							<%@include file="/pages/setting/setting.jsp" %>
						</c:when>
						<c:when test="${active_menu_id == 'img-sys'}">
							<%@include file="/pages/sys/sys.jsp" %>
						</c:when>
						<c:otherwise>
							<%@include file="/pages/common/404.jsp" %>
						</c:otherwise>
					</c:choose>
				</div>
				<!-- 右侧内容区 end -->
			</div>
		</div>
		<!-- 内容区 end -->
		
		<!-- 账号管理对话框 -->
		<div class="modal inmodal fade" id="user_edit_modal" tabindex="-1" role="dialog"  aria-hidden="true" >
			<div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal">
		                	<span class="close-btn" aria-hidden="true">&times;</span>
		                	<span class="sr-only">Close</span>
		                </button>
		                <h4 class="modal-title">新建用户</h4>
		            </div>
		            <div class="modal-body">
		                <form id="user_edit_form" class="form-horizontal">
		                	<input id="userId" name="userId" type="hidden"/>
		                	<div class="form-group">
		                		<label class="col-sm-3 control-label">姓名</label>
		                		<div class="col-sm-9">
		                			<input id="nickName" name="nickName" type="text" class="form-control input-sm" aria-required="true">
		                		</div>
		                	</div>
		                	<div class="form-group">
		                		<label class="col-sm-3 control-label">用户名</label>
		                		<div class="col-sm-9">
		                			<input id="userName" name="userName" type="text" class="form-control input-sm" aria-required="true">
		                		</div>
		                	</div>
		                	<div class="form-group">
		                		<label class="col-sm-3 control-label">密码</label>
		                		<div class="col-sm-9">
		                			<input id="password" name="password" type="password" class="form-control input-sm" aria-required="true">
		                		</div>
		                	</div>
		                	<div class="form-group">
		                		<label class="col-sm-3 control-label">确认密码</label>
		                		<div class="col-sm-9">
		                			<input id="password_confirm" name="password_confirm" type="password" class="form-control input-sm" aria-required="true">
		                		</div>
		                	</div>
		                	<div class="form-group">
						    	<label class="col-sm-3 control-label">状态</label>
							    <div class="col-sm-9">
							      	<div class="switch switch-smal">
									    <input type="checkbox" id="status" name="status" value="1" disabled="${isAdmin ? true : false }" data-on-color="info" data-off-color="default" data-on-text="启用" data-off-text="禁用"/>
									</div>
							    </div>
						  	</div>
						  	<c:if test="${isAdmin }">
							  	<div class="form-group">
								    <label class="col-sm-3 control-label">是否是子管理员</label>
								    <div class="col-sm-9">
								      	<div class="switch switch-smal">
										    <input type="checkbox" id="userType" name="userType" value="2" data-on-color="info" data-off-color="default" data-on-text="是" data-off-text="否"/>
										</div>
								    </div>
							  	</div>
							  	<div class="form-group">
								    <label class="col-sm-3 control-label">加入白名单</label>
								    <div class="col-sm-9">
								      	<div class="switch switch-smal">
										    <input type="checkbox" id="whiteListType" name="whiteListType" value="0" data-on-color="info" data-off-color="default" data-on-text="是" data-off-text="否"/>
										</div>
								    </div>
							  	</div>
						  	</c:if>
		                </form>
		            </div>
		            <div class="modal-footer">
		            	<span style="float: left;color: #d45252"><span class="bold">提示：</span><span id="error_msg"></span></span>
		                <a id="btn_sumit_modal" class="btn pic-btn">
							<i class="fa fa-close"></i>保存
						</a>
		                <a id="btn_close_modal" class="btn pic-btn" data-dismiss="modal">
							<i class="fa fa-close"></i>取消
						</a>
		            </div>
		        </div>
		    </div>
		</div>
		<!-- 系统设置对话框 -->
		<div class="modal inmodal fade" id="system_setting_modal" tabindex="-1" role="dialog"  aria-hidden="true" >
			<div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal">
		                	<span class="close-btn" aria-hidden="true">&times;</span>
		                	<span class="sr-only">Close</span>
		                </button>
		                <h4 class="modal-title">系统设置</h4>
		            </div>
		            <div class="modal-body">
		                <form id="system_setting_form" class="form-horizontal sys-setting-form">
		                	<div class="form-group">
		                		<label class="col-sm-12 control-label">设置允许用户登录时间段</label>
		                		<div class="clockpicker-group">
		                			<div class="input-group clockpicker" data-autoclose="true">
			                            <input type="text" name="startTime" class="form-control" value="">
			                            <span class="input-group-addon">
			                                    <span class="icon icon-clock-o"></span>
			                            </span>
			                        </div>
			                        <span class="split">至</span>
			                        <div class="input-group clockpicker" data-autoclose="true">
			                            <input type="text" name="endTime" class="form-control" value="">
			                            <span class="input-group-addon">
			                                    <span class="icon icon-clock-o"></span>
			                            </span>
			                        </div>
		                		</div>
		                		<div class="clockpicker-group">
		                			<div class="input-group clockpicker" data-autoclose="true">
			                            <input type="text" name="startTime" class="form-control" value="">
			                            <span class="input-group-addon">
			                                    <span class="icon icon-clock-o"></span>
			                            </span>
			                        </div>
			                        <span class="split">至</span>
			                        <div class="input-group clockpicker" data-autoclose="true">
			                            <input type="text" name="endTime" class="form-control" value="">
			                            <span class="input-group-addon">
			                                    <span class="icon icon-clock-o"></span>
			                            </span>
			                        </div>
		                		</div>
		                		<div class="clockpicker-group">
		                			<div class="input-group clockpicker" data-autoclose="true">
			                            <input type="text" name="startTime" class="form-control" value="">
			                            <span class="input-group-addon">
			                                    <span class="icon icon-clock-o"></span>
			                            </span>
			                        </div>
			                        <span class="split">至</span>
			                        <div class="input-group clockpicker" data-autoclose="true">
			                            <input type="text" name="endTime" class="form-control" value="">
			                            <span class="input-group-addon">
			                                    <span class="icon icon-clock-o"></span>
			                            </span>
			                        </div>
		                		</div>
		                	</div>
		                	<div class="form-group">
		                		<label class="col-sm-12 control-label">设置本周工作日</label>
		                		<div class="col-sm-12 work-day-container">
		                			<div class="item">
		                				<input type="checkbox" id="work_data_1"/>
		                				<label for="work_data_1">星期一</label>
		                			</div>
		                			<div class="item">
		                				<input type="checkbox" id="work_data_2"/>
		                				<label for="work_data_2">星期二</label>
		                			</div>
		                		</div>
		                	</div>	
		                </form>
		            </div>
		            <div class="modal-footer">
		            	<span style="float: left;color: #d45252;display: none"><span class="bold">提示：</span><span id="error_msg"></span></span>
		                <a id="btn_sumit_modal" class="btn pic-btn">
							<i class="fa fa-close"></i>保存
						</a>
		                <a id="btn_close_modal" class="btn pic-btn" data-dismiss="modal">
							<i class="fa fa-close"></i>取消
						</a>
		            </div>
		        </div>
		    </div>
		</div>
		
		<!-- 底部区 begin -->
		<jsp:include page="/pages/common/footer.jsp"/>
		<!-- 底部区 end -->
		
		<!-- 公共JS begin -->
		<jsp:include page="/pages/common/common_js.jsp"/>
		<!-- 公共JS end -->
	</body>
</html>	