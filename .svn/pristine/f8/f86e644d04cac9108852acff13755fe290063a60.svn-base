<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/sys/sys.css?appV=${appV}">
<%-- <div class="upload-container">
	<div style="width: 600px;height:400px;margin:100px auto; center;background:">
		<img alt="" src="${contextPath }/images/building.jpg"/>
	</div>
</div> --%>
<div class="pic-child-container">
	<div class="pic-child-content">
		<%-- <div>
			<img alt="" src="${contextPath}/images/building.jpg"/>
		</div> --%>
		<div class="content-head user-head">
			<div class="content-title search-description">
				系统管理
			</div>
		</div>
		<div class="content-body sys-body">
			<div class="sys-panel">
				<div class="col col-sm-6">
					<div class="col-title"><h4 class="text-center">用户管理</h4></div>
					<div class="col-body">
						<div class="bar-container">
							<div class="toolbar">
								<a id="btn_add" class="btn pic-btn pic-btn-small btn_add" biz-type="user"><i class="icon icon-plus"></i>新增</a>
								<a id="btn_del" class="btn pic-btn pic-btn-small btn_del" biz-type="user"><i class="icon icon-remove"></i>删除</a>
								<div class="checkbox">
									<input id="userType" name="userType" type="checkbox"/><label for="userType" class="checkbox-inline">子管理员</label>
									<input id="whiteListType" name="whiteListType" type="checkbox"/><label for="whiteListType" class="checkbox-inline">白名单</label>
								</div>
							</div>
							<div class="input-group searc-box">
								<input id="search_cdt" type="text" class="form-control from-small search_cdt" placeholder="输入条件搜索" biz-type="user"/>
								<span class="input-group-btn">
									<button id="btn_search" type="button" class="btn pic-btn pic-btn-small btn_search" biz-type="user">搜索</button>
								</span>
							</div>
						</div> 
						<div class="table_wrapper user_table_wrapper">
                           	<table id="user_table_list"></table>
                           	<div id="user_table_pager"></div>
                          </div>
					</div>
				</div>
				<div class="col col-sm-6">
					<div class="col-title"><h4 class="text-center">账号管理</h4></div>
					<div class="col-body">
						<div class="bar-container">
							<div class="toolbar">
								<a id="btn_add" class="btn pic-btn pic-btn-small btn_add" biz-type="account"><i class="icon icon-plus"></i>新增</a>
								<a id="btn_del" class="btn pic-btn pic-btn-small btn_del" biz-type="account"><i class="icon icon-remove"></i>删除</a>
							</div>
							<div class="input-group searc-box">
								<input id="search_cdt" type="text" class="form-control from-small search_cdt" placeholder="输入条件搜索" biz-type="account"/>
								<span class="input-group-btn">
									<button id="btn_search" type="button" class="btn pic-btn pic-btn-small btn_search" biz-type="account">搜索</button>
								</span>
							</div>
						</div>
						<div class="table_wrapper account_table_wrapper">
                           	<table id="account_table_list"></table>
                           	<div id="account_table_pager"></div>
                          </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>	


<!-- 账号管理对话框 -->
<div class="modal inmodal fade" id="account_edit_modal" tabindex="-1" role="dialog"  aria-hidden="true" >
	<div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                	<span class="close-btn" aria-hidden="true">&times;</span>
                	<span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">新建账号</h4>
            </div>
            <div class="modal-body">
                <form id="account_edit_form" class="form-horizontal">
                	<input id="id" name="id" type="hidden"/>
                	<div class="form-group">
                		<label class="col-sm-3 control-label">账号名称</label>
                		<div class="col-sm-9">
                			<input id="name" name="name" type="text" class="form-control input-sm" aria-required="true">
                		</div>
                	</div>
                	<div class="form-group">
                		<label class="col-sm-3 control-label">所属平台</label>
                		<div class="col-sm-9">
                			<select id="platform" name="platform" type="text" class="form-control input-sm" aria-required="true">
                				<option/>
                				<option value="EBAY">EBAY</option>
                				<option value="AMAZON">AMAZON</option>
                				<option value="WISH">WISH</option>
                				<option value="ALIEXPRESS">ALIEXPRESS</option>
                			</select>
                		</div>
                	</div>
                </form>
            </div>
            <div class="modal-footer">
            	<span style="float: left;display:none;color: #d45252"><span class="bold">提示：</span><span id="error_msg"></span></span>
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
<!-- 用户账号绑定对话框 -->
<div class="modal inmodal fade" id="user_account_bind_modal" tabindex="-1" role="dialog"  aria-hidden="true" >
	<div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                	<span class="close-btn" aria-hidden="true">&times;</span>
                	<span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">绑定账号</h4>
            </div>
            <div class="modal-body">
                <div id="content-wrapper" class="form-horizontal">
                </div>
            </div>
            <div class="modal-footer">
            	<span style="float: left;display:none;color: #d45252"><span class="bold">提示：</span><span id="error_msg"></span></span>
                <a id="btn_sumit_modal" class="btn pic-btn">
					<i class="fa fa-close"></i>确定
				</a>
                <a id="btn_close_modal" class="btn pic-btn" data-dismiss="modal">
					<i class="fa fa-close"></i>取消
				</a>
            </div>
        </div>
    </div>
</div>
<!-- 引入自身JS-->
<script type="text/javascript" src="${contextPath}/js/sys/sys.js?appV=${appV}"></script>



























