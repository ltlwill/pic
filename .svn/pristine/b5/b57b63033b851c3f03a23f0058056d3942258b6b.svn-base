
/**
 * 头部管理
 */
var HeadMgr = {
	options: 
	{
		logoutUrl: '/logout',
		settingUrl: '/sys/setting',
	},	
	init:function()
	{
		HeadMgr.initEles();
		HeadMgr.bindEvent();
	},
	initEles: function()
	{
		this.$container = $('.header-container');
		this.$userDropdown = this.$container.find('#user_dropdown');
		this.$userSubMenu = this.$container.find('#user_sub_menu');
		this.$editUserBtn = this.$userSubMenu.find('#btn_edit_user');
		this.$sysSettingBtn = this.$container.find('#btn_sys_setting');
		
		this.$settingModal = $('#system_setting_modal');
		this.$saveSettingBtn = this.$settingModal.find('#btn_sumit_modal');
		this.$settingForm = this.$settingModal.find('#system_setting_form');
		this.$errorMsg = this.$settingModal.find('#error_msg');
		this.$clockpickers = $('.clockpicker').clockpicker(); // 时间选择插件
	},
	bindEvent:function()
	{
		var me = this;
		$("#logout").bind("click",function(){
//			window.location.replace(contextPath + HeadMgr.logoutUrl);
			me.doLoginOut();
		});
		this.$userDropdown.bind('click',function(){
			if(me.$userSubMenu.hasClass('isShow'))
			{
				me.$userSubMenu.removeClass('isShow');
				me.$userSubMenu.hide();
			}else
			{
				me.$userSubMenu.addClass('isShow');
				me.$userSubMenu.show();
			}
		});
		// 修改资料
		this.$editUserBtn.bind('click',function(){
			me.doEditUserInfo();
		});
		// 系统设置
		this.$sysSettingBtn.bind('click',function(){
			me.openSysSettingModal();
		});
		this.$saveSettingBtn.bind('click',function(){
			me.doSysSetting();
		});
		this.$settingModal.on('shown.bs.modal',function(){
			me.initSettingForm();
		});
		$('body').bind('click',function(evt){
			if(!(evt.target.id == me.$userDropdown.attr('id') 
					|| (evt.target.parentElement && evt.target.parentElement.id == me.$userDropdown.attr('id'))	
					|| (evt.target.parentElement.parentElement && evt.target.parentElement.parentElement.id ==  me.$userDropdown.attr('id'))
					|| evt.target.id == me.$userSubMenu.attr('id')
					|| (evt.target.parentElement && evt.target.parentElement.id == me.$userSubMenu.attr('id'))
					|| (evt.target.parentElement.parentElement && evt.target.parentElement.parentElement.id ==  me.$userSubMenu.attr('id'))))
			{
				me.$userSubMenu.removeClass('isShow');
				me.$userSubMenu.hide();
			}	
		});
	},
	doLoginOut: function()
	{
		window.location.replace(contextPath + HeadMgr.options.logoutUrl);
	},
	initSettingForm: function()
	{
		this.validator = this.$settingForm.validate({
			rules:{
				'startTime': 'required',
				'endTime': 'required'
			}
		});
		this.validator.resetForm();
		this.$errorMsg.html('').parent().hide();
		// 初始化数据
		var settingData = this.getSettingData();
		if(settingData && settingData.length)
		{
			this.$settingForm.find('.clockpicker-group').each(function(i,o){
				if(settingData[i])
				{
					$(this).find('input').each(function(){
						$(this).val(settingData[i][$(this).attr('name')] || '');
					});
				}
			});
		}
	},
	getSettingData: function()
	{
		return window.sysSettings || [];
	},
	doEditUserInfo: function()
	{
		var user = window.loginUser;
		SysUserMgr.openUserEditModal('edit',user.userId,{'disStatus':true});
	},
	openSysSettingModal: function()
	{
		this.$settingModal.modal({}).modal('show');
	},
	doSysSetting: function()
	{
//		if(!this.$settingForm.valid())
//		{
//			return false;
//		}
		var me = this,params = me.getSettingParams();
		$.ajax({
			url: contextPath + me.options.settingUrl,
			data: {'settingJson':JSON.stringify(params)},
			async: false,
			type: 'POST',
			dataType: 'json',
			success: function(result){
				window.sysSettings = result;
				me.$settingModal.modal('hide');
				layer.msg("保存成功",{icon:1,shift:0,offset:200});
			},
			error: function(){
				me.$errorMsg.html('保存失败').parent().show();
			}
		});
	},
	getSettingParams: function()
	{
		var data = [],row;
		this.$settingForm.find('.clockpicker-group').each(function(){
			row = {};
			$(this).find('input').each(function(){
				if($(this).attr('name')){
					row[$(this).attr('name')] = $(this).val() || '';
				}
			});
			data.push(row);
		});
		return data;
	},
};
// 启动
SYS.ready(HeadMgr.init);



















