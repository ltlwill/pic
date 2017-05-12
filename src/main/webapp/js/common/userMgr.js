/**
 * 系统用户管理JS
 */
var SysUserMgr = 
{
	init: function()
	{
		SysUserMgr.initEles();
		SysUserMgr.initElesData();
		SysUserMgr.bindEvent();
	},	
	initEles: function()
	{
		// 账号新建、编辑区域元素
		this.$modal = $('#user_edit_modal');
		this.$userForm = this.$modal.find('#user_edit_form');
		this.$userFormSave = this.$modal.find('#btn_sumit_modal');
		this.$userId = this.$userForm.find('#userId');
		this.$errorMsg = this.$modal.find('#error_msg');
		this.$isAdmin = $('#is_admin');
		// 系统设置区域
		this.$settingModal = $('#system_setting_modal');
		// 新增一个密码验证规则
		$.validator.addMethod("userPwdValidator", function(value) {
			var rule = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/;
		    return rule.test(value);
		}, '请输入长度为6至20个字符的英文字母和数字组合的密码');
		// 密码是初始化状态时，初次登录需要修改初始密码
		if(window.loginUser && window.loginUser.pwdStatus == '0')
		{
			this.showUpdatePwdModal();
		}	
	},
	initElesData: function()
	{
	},
	bindEvent: function()
	{
		var me = this;
		this.$userFormSave.bind('click',function(){
			me.doEditUser();
		});
	},
	showUpdatePwdModal: function()
	{
		var opts = {'modalOpts':{'backdrop':false,'keyboard':false,'show':false},'disStatus':true
				,'firstModifyPwd':true,'onSaveSuccess':function(){
					layer.alert('密码修改成功，请重新登陆后再使用！',{icon:1,offset:200,yes:function(){
						HeadMgr.doLoginOut();
					}});
				},'onInitDataSuccess':function(){
					this.$userForm.find('#password').val('');
					this.$userForm.find('#password_confirm').val('');
				}};
		this.openUserEditModal('edit',window.loginUser.userId,opts);
	},
	openUserEditModal: function(type,userId,_opts)
	{
		var opts = $.extend(true,{'modalOpts':{'backdrop': false,'keyboard':true,'show':true},
			'onSaveSuccess':'','onInitDataSuccess':'','disStatus':false,'firstModifyPwd':false},_opts);
		this.onSaveSuccess = opts.onSaveSuccess;
		this.disStatus = opts.disStatus;
		this.onInitDataSuccess = opts.onInitDataSuccess;
		this.$modal.modal(opts.modalOpts).modal('show');
		// 是否是第一次初始化修改密码
		if(opts.firstModifyPwd)
		{
			this.$modal.find('.close').hide();
			this.$modal.find('#btn_close_modal').hide();
			this.$errorMsg.html('首次登录，请修改初始密码'); 
			this.$errorMsg.parent().show();
		}else
		{
			this.$modal.find('.close').show();
			this.$modal.find('#btn_close_modal').show();
			this.$errorMsg.parent().hide();
		}
		this.initUserEditForm(type,userId);
	},
	initUserEditForm: function(type,userId)
	{
		this.validator = this.$userForm.validate({
			rules:{
				'nickName': 'required',
				'userName': 'required',
				'password': 'userPwdValidator',
				'password_confirm':{
					required: true,
					equalTo: "#password"
				}
			},
			messages:{
				'nickName': '请输入昵称',
				'userName': '请输入账号',
				'password': {
					required: '请输入有效的密码',
					rangelength: '长度必须在4至20个字符之间'
				},
				'password_confirm':{
					required: '请再次输入密码',
					equalTo: '两次输入的密码不一致'
				}	
			}
		});
		this.validator.resetForm();
		
		var user = {'status':'1'};
		// 编辑账号时，
		if(type == 'edit')
		{
			this.$userId.val(userId);
			this.$userForm.find("#userName").attr('disabled',true);
			this.$modal.find('.modal-title').html('修改用户');
			user = this.getUserById(userId);
		}else
		{
			this.$userForm.find("#userName").attr('disabled',false);
			this.$modal.find('.modal-title').html('新建用户');
		}	
		if(this.disStatus)
		{
			this.$userForm.find('#nickName').attr('disabled',true);
			this.$userForm.find('#status').attr('disabled',true);
			// 如果是admin自己编辑自己,则不需要显示用户类型、白名单类型
			if(String(this.$isAdmin.val()) == 'true')
			{
				this.$userForm.find('#userType').closest('.form-group').hide();
				this.$userForm.find('#whiteListType').closest('.form-group').hide();
			}else
			{
				this.$userForm.find('#userType').closest('.form-group').show();
				this.$userForm.find('#whiteListType').closest('.form-group').show();
			}	
		}else
		{
			this.$userForm.find('#nickName').removeAttr('disabled');
			this.$userForm.find('#status').removeAttr('disabled');
			this.$userForm.find('#userType').closest('.form-group').show();
		}
		this.initEditUserFromData(user,type);
	},
	initEditUserFromData: function(user,type)
	{
		var me = this;
		me.$userForm.find('input').each(function(i,o){
			var val = (this.name == 'password_confirm') ? user['password'] : user[this.name];
			this.value = val || '';
			if(this.type == 'checkbox' && this.name == 'status')
			{
				me.createBootstrapSwitch($(this),type == 'edit' ? String(user[this.name]) == '1' ? true : false : true);
			}else if(this.type == 'checkbox' && this.name == 'userType')
			{
				me.createBootstrapSwitch($(this),type == 'edit' ? String(user[this.name]) == '1' ? true : false : false);
			}else if(this.type == 'checkbox' && this.name == 'whiteListType')
			{
				me.createBootstrapSwitch($(this),type == 'edit' ? String(user[this.name]) == '1' ? true : false : false);
			}	
		});
		if(typeof me.onInitDataSuccess == 'function')
		{
			me.onInitDataSuccess();
		}
	},
	createBootstrapSwitch: function($ele,state)
	{
		$ele.bootstrapSwitch('destroy').bootstrapSwitch({
			'state': state || false,
			'onSwitchChange': function(evt,state){
				if(this.name == 'status')
				{
					this.value = 'true' == String(state) ? '1' : '0';
				}else if(this.name == 'userType')
				{
					this.value = 'true' == String(state) ? '1' : '2';
				}else if(this.name == 'whiteListType')
				{
					this.value = 'true' == String(state) ? '1' : '0';
				}else
				{
					this.value = state;
				}	
			}
		});
	},
	getUserById: function(userId)
	{
		var me = this,
			user = {};
		$.ajax({
			url: contextPath + '/sys/getUserById',
			data: {'id':userId},
			async: false,
			success: function(result){
				user = result || {};
			},
			error: function()
			{
				layer.alert('出错了！',{icon:2,offset:200});
			},
		});
		return user;
	},
	doEditUser: function()
	{
		var me= this;
		var valid = this.$userForm.valid();
		if(!valid)
		{
			return false;
		}
		$.ajax({
			url: contextPath + '/sys/editUser',
			type: 'POST',
			data: $.extend(DataUtil.formSerializeObj(me.$userForm),
					{'status':me.$userForm.find('#status').val(),'nickName':me.$userForm.find('#nickName').val(),
					'userType':me.$userForm.find('#userType').val() || '2','whiteListType': me.$userForm.find('#whiteListType').val() || '0'}),
			async: false,
			success: function(result){
				me.$modal.modal('hide');
				layer.msg("保存成功",{icon:1,shift:0,offset:200});
//				me.doReload();
				if(typeof me.onSaveSuccess == 'function')
				{
					me.onSaveSuccess();
				}	
			},
			error: function(xhr)
			{
				var msg = '未知错误';
				// 账号已存在
				if(xhr.responseText == '2001')
				{
					msg = '账号已存在';
				}
				me.$errorMsg.html('保存失败，' + msg); 
				me.$errorMsg.parent().show();
			},
		});
	},
};

SYS.ready(SysUserMgr.init);