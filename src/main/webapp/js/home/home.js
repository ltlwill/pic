/**
 * 主页管理JS
 */
var HomeMgr = {
	options:
	{
//		initDataUrl: '/resources/app/update.json',
		initDataUrl: '/appspace/update.json',
	},	
	// 初始化
	init:function()
	{
		HomeMgr.initEles();
		HomeMgr.initElesData();
	},
	initEles: function()
	{
		this.$contentContainer = $('#content-container');
		this.$templateContent = $('#template_content');
	},
	initElesData: function()
	{
		this.initData = {};
		this.requestInitDatas();
	},
	requestInitDatas: function()
	{
		var me = this;
		$.ajax({
//			url: contextPath + this.options.initDataUrl + '?_r' + (new Date().getTime()),
			url: this.options.initDataUrl + '?_r' + (new Date().getTime()),
			dataType: 'JSON',
			contentType: 'application/json;charset=UTF-8',
			success: function(result){
				result = result || {};
				result.contextPath = contextPath;
				me.initData = result;
				me.initTemplateData(result);
			},
			error: function(th,ex){
				var i = 0;
			},
		});
	},
	initTemplateData: function(data)
	{
		var html = template(this.$templateContent.attr('id'), data);
    	this.$contentContainer.html(html);
    	this.generateQRcode();
	},
	// 生成二维码
	generateQRcode: function()
	{
		this.qrcode = new QRCode(this.$contentContainer.find('.barcode .barcode-content')[0], {
			width : 200,
			height : 200,
		});
		var url = this.initData.qrcodeUrl || '';
		if(url && url.indexOf('?') > -1)
		{
			url += '&contextPath=' + contextPath;
		}else{
			url += '?contextPath=' + contextPath;
		}
		url = location.protocol + '//' + location.host + contextPath + url,
		this.qrcode.makeCode(url);
	},
};

// 准备启动
SYS.ready(HomeMgr.init);























