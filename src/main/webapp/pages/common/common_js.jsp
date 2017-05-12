<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 应用上下文 -->
<script type="text/javascript">
	var contextPath = window.contextPath = self.contextPath
		= top.contextPath = '${contextPath}' || '<%=request.getContextPath()%>'|| '${pageContext.request.contextPath}';
	var loginUserStr = '${login_user}',sysSettingsStr = '${sysSettingsStr}';
</script>

<!-- jquery JS -->
<script type="text/javascript" src="${contextPath}/js/jquery/jquery-1.11.3.min.js?appV=${appV}"></script>
<!--  bootstrap JS -->
<script type="text/javascript" src="${contextPath}/plugins/bootstrap/js/bootstrap.min.js?appV=${appV}"></script>
<!--  webuploader JS -->
<script type="text/javascript" src="${contextPath}/plugins/webuploader/js/webuploader.min.js?appV=${appV}"></script>
<!-- layer JS -->
<script type="text/javascript" src="${contextPath}/plugins/layer/layer.js?appV=${appV}"></script>
<script type="text/javascript" src="${contextPath}/plugins/layer/extend/layer.ext.js?appV=${appV}"></script>
<script type="text/javascript" src="${contextPath}/plugins/layer/laydate/laydate.js?appV=${appV}"></script>
<!-- clockpicker -->
<script type="text/javascript" src="${contextPath}/plugins/clockpicker/clockpicker.js?appV=${appV}"></script>
<script type="text/javascript" src="${contextPath}/plugins/switch-master/js/bootstrap-switch.js?appV=${appV}"></script>
<%-- <script type="text/javascript" src="${contextPath}/plugins/switch-master/js/bootstrapSwitch.js?appV=${appV}"></script> --%>
<!-- slimScroll JS -->
<script type="text/javascript" src="${contextPath}/plugins/slimscroll/jquery.slimscroll.min.js?appV=${appV}"></script>
<!-- ZeroClipbord -->
<script type="text/javascript" src="${contextPath}/plugins/ZeroClipboard/ZeroClipboard.min.js?appV=${appV}"></script>
<script type="text/javascript" src="${contextPath }/js/plugins/validate/jquery.validate.min.js?appV=${appV}"></script>
<!-- iCheck -->
<script type="text/javascript" src="${contextPath }/js/plugins/iCheck/icheck.min.js?appV=${appV}"></script>
<script type="text/javascript" src="${contextPath }/js/plugins/jqgrid/i18n/grid.locale-cn.js?appV=${appV}"></script>
<script type="text/javascript" src="${contextPath }/js/plugins/jqgrid/jquery.jqGrid.min.js?appV=${appV}"></script>
<script type="text/javascript" src="${contextPath}/plugins/myphoto/js/myphoto.js?appV=${appV}"></script>
<!-- ztree JS -->
<script type="text/javascript" src="${contextPath }/js/plugins/ztree/js/jquery.ztree.all.min.js?appV=${appV}"></script>
<!-- ztree select下拉树  自定义 JS -->
<script type="text/javascript" src="${contextPath }/js/common/jquery.ztree.select.js?appV=${appV}"></script>

<!-- JSON2 JS -->
<script type="text/javascript" src="${contextPath}/js/common/json2.js?appV=${appV}"></script>
<script type="text/javascript" src="${contextPath}/js/common/common.js?appV=${appV}"></script>
<script type="text/javascript" src="${contextPath}/js/common/itips.js?appV=${appV}"></script>
<script type="text/javascript" src="${contextPath}/js/common/menu.js?appV=${appV}"></script>
<script type="text/javascript" src="${contextPath}/js/common/image.grid.js?appV=${appV}"></script>
<script type="text/javascript" src="${contextPath}/js/common/mygrid.js?appV=${appV}"></script>
<script type="text/javascript" src="${contextPath}/js/common/string.js?appV=${appV}"></script>
<script type="text/javascript" src="${contextPath}/js/common/loading.js?appV=${appV}"></script>
<script type="text/javascript" src="${contextPath}/js/common/userMgr.js?appV=${appV}"></script>
<script type="text/javascript">
	/* window.console.log("contextPath:" + contextPath); */
</script>
