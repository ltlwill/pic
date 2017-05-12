<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/setting/setting.css?appV=${appV}">
<div class="pic-child-container">
	<div class="pic-child-content">
		<div class="content-head user-head">
			<div class="content-title search-description">
				用户设置
			</div>
		</div>
		<div class="content-body setting-body">
			<div class="setting-panel">
				<div class="col col-sm-6">
					<div class="col-title"><h4 class="text-center">个性化设置</h4></div>
					<div class="col-body">
						<form id="setting_form" class="form-horizontal">
							<div class="form-group">
						    	<label class="col-sm-4 control-label">图片列表显示大图</label>
							    <div class="col-sm-8">
							      	<div class="switch">
									    <input type="checkbox" name="listShowBigImg" checked data-on-color="info" data-off-color="default"/>
									</div>
							    </div>
						  	</div>
							<div class="form-group">
						    	<label class="col-sm-4 control-label">鼠标移入图片显示大图</label>
							    <div class="col-sm-8">
							      	<div class="switch">
									    <input type="checkbox" name="mousehoverShowBigImg" checked data-on-color="info" data-off-color="default"/>
									</div>
							    </div>
						  	</div>
							<div class="form-group">
						    	<label class="col-sm-4 control-label">图片列表每页显示图片数</label>
							    <div class="col-sm-8">
								    <input type="number" name="imgGridNum" min="1" max="100" value="36" class="number-input"/>
							    </div>
						  	</div>
						  	
						  	<button id="form_submit" type="submit" class="submit" style="display: none"></button>
						</form>
					</div>
				</div>
			</div>
		</div>		
	</div>
</div>
<!-- 引入自身JS-->
<script type="text/javascript" src="${contextPath}/js/setting/setting.js?appV=${appV}"></script>



























