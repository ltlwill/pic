/**
 * 用户管理JS
 */
var SysMgr = 
{
	init: function()
	{
		SysMgr.initEles();
		SysMgr.initElesData();
		SysMgr.bindEvent();
	},
	initEles: function()
	{
		var me = this;
		this.ATTR_BIZ_TYPE = 'biz-type';
		this.TYPE_USER = 'user';
		this.TYPE_ACCOUNT = 'account';
		this.$panel = $('.sys-panel');
		// 账号表格区域元素
		this.$userTableWrapper = $('.user_table_wrapper');
		this.$userTable = $('#user_table_list');
		this.$userTablePager = $('#user_table_pager');
		this.$accountTableWrapper = $('.account_table_wrapper');
		this.$accountTable = $('#account_table_list');
		this.$accountTablePager = $('#account_table_pager');
		
		this.$searchCdt = this.$panel.find('.search_cdt');
		this.$searchBtn = this.$panel.find('.btn_search');
		this.$checkboxCdt = this.$panel.find('.checkbox');
		
		this.$addBtn = this.$panel.find('.btn_add');
		this.$delBtn = this.$panel.find('.btn_del');
		// 账号对话框区域
		this.$modal = $('#account_edit_modal');
		this.$form = this.$modal.find('#account_edit_form');
		this.$okBtn = this.$modal.find('#btn_sumit_modal');
		this.$errorMsg = this.$modal.find('#error_msg');
		this.validator = this.$form.validate({
			rules:{'name': 'required','platform': 'required'},
			messages:{'name': '请输入账号名称','platform': '请选择所属平台'}
		});
		// 绑定账号对话框
		this.$bindModal = $('#user_account_bind_modal');
		this.$bindModalCon = this.$bindModal.find('#content-wrapper');
		this.$bindSubmit = this.$bindModal.find('#btn_sumit_modal');
		this.$bindErrorMsg = this.$bindModal.find('#error_msg');
		// 初始化用户表格
		this.initUserGrid();
		// 初始化账号表格
		this.intiAccountGrid();
	},
	initElesData: function()
	{
	},
	bindEvent: function()
	{
		var me = this;
		this.$addBtn.bind('click',function(evt){
			if($(this).attr(me.ATTR_BIZ_TYPE) == me.TYPE_USER)
			{
				// 打开编辑用户对话框
				me.openUserEditModal('create');
			}else
			{
				// 打开创建账号的对话框
				me.openAccountEditModal('create');
			}
		});
		this.$delBtn.bind('click',function(){
			var type = $(this).attr(me.ATTR_BIZ_TYPE);
			var rowIds = me.getTable(type).jqGrid('getGridParam', 'selarrrow');
			if(!rowIds || !rowIds.length)
			{
				layer.msg("请选择要删除的" + (type == me.TYPE_USER ? '用户' : '账号'),{icon:0,shift:6,offset:200});
				return false;
			}
			layer.confirm("确定要删除吗？",{icon:3},function(index){
				layer.close(index);
				var ids = me.getSelectIds(rowIds,type);
				me.doDel(ids,type);
			});
		});
		this.$searchBtn.bind('click',function(){
			me.doSearch($(this).attr(me.ATTR_BIZ_TYPE));
		});
		this.$searchCdt.bind('keyup',function(evt){
			evt = evt || window.event;
			if(evt.keyCode == '13' || evt.which == '13')
			{
				me.doSearch($(this).attr(me.ATTR_BIZ_TYPE));
			}
		});
		this.$checkboxCdt.find('input').bind('change',function(evt){
			me.doSearch(me.TYPE_USER);
		});
		this.$okBtn.bind('click',function(){
			me.doAccountEdit();
		});
		this.$bindSubmit.bind('click',function(){
			me.doBindAccounts();
		});
		// 窗口改变事件
		$(window).bind('resize', function () {
            var width = me.$userTableWrapper.width();
            me.$userTable.setGridWidth(width);
            me.$userTable.setGridHeight(me.getTableHeight(me.TYPE_USER));
            
            width = me.$accountTableWrapper.width();
            me.$accountTable.setGridWidth(width);
            me.$accountTable.setGridHeight(me.getTableHeight(me.TYPE_ACCOUNT));
        }).trigger('resize');
	},
	doSearch: function(type)
	{
		this.getTable(type).setGridParam({page:1}).trigger("reloadGrid");
	},
	doReload: function(type)
	{
		this.getTable(type).trigger("reloadGrid");
	},
	doDel: function(ids,type)
	{
		var me = this,
			url = contextPath + '/sys' + (type == me.TYPE_USER ? '/delUserByIds' : '/delAccountByIds' );
		$.ajax({
			url: url,
			data: {'ids': ids},
			success: function(result)
			{
				layer.msg("删除成功",{icon:1,shift:0,offset:200});
				me.doReload(type);
			},
			error: function()
			{
				layer.alert('删除失败',{icon:2});
			}
		});
	},
	getSelectIds: function(rowIds,type)
	{
		var me = this,
		row = {},
		ids = [],
		field = type == me.TYPE_USER ? 'userId' : 'id';
		for(var index in rowIds)
		{
			row = me.getTable(type).getRowData(rowIds[index]);
			(row && row[field]) ? ids.push(row[field]) : null;
		}	
		return ids;
	},
	getRequestParams: function(type)
	{
		var $userType = this.$checkboxCdt.children('input[name="userType"]')[0],
			$whiteListType = this.$checkboxCdt.children('input[name="whiteListType"]')[0];
		return {'keyWord': $.trim(this.$searchCdt.filter('[' + this.ATTR_BIZ_TYPE + '="' + type + '"]').val()),
				'userType':$userType.checked ? '1' : '',
				'whiteListType': $whiteListType.checked ? '1' : ''};
	},
	initUserGrid: function()
	{
		var me = this,
			opts = {
			url: contextPath + '/sys/getUserList',
			colNames:['ID','姓名','用户名','状态','操作'],
			colModel:[
	        	{name:'userId',index:'userId',hidden:true,key:true},
	        	{name:'nickName',index:'nickName',formatter:function(value,opts,row){
	        		if(row.userType == '1' || row.whiteListType == '1') // 子管理员或白名单
	        		{
	        			var str = '<div title=""><span title="' + value + '" style="margin-right:5px">' + value;
	        			if(row.userType == '1')
	        			{
	        				str += '</span><span title="子管理员" style="font-size: xx-small;background-color: #00b7ee;color:#fff;padding:1px;border-radius:50%;cursor:default;margin-right: 5px;">管</span>';
	        			}
	        			if(row.whiteListType == '1')
	        			{
	        				str+= '</span><span title="白名单" style="font-size: xx-small;background-color: #00b7ee;color:#fff;padding:1px;border-radius:50%;cursor:default;margin-right: 5px;">白</span>'
	        			}	
//	        			return '<div title=""><span title="' + value + '" style="margin-right:5px">' + value 
//	        				+ '</span><span title="子管理员" style="font-size: xx-small;background-color: #00b7ee;color:#fff;padding:1px;border-radius:50%;cursor:default">管</span><div>';
	        			return str;
	        		}
	        		return value;
	        	}},
	        	{name:'userName',index:'userName'},
	        	{name:'status',index:'status',formatter:function(value,opts,row){
	        		if(row.status == '1')
	        		{
	        			return '<span style="color:#00b7ee">启用</span>';
	        		}	
	        		return '<span style="color:#d45252">禁用</span>';
	        	}},
	        	{name:'',width:'161px',fixed:true,sortable:false,formatter:function(value,opts,row){
	        		return '<a class="btn pic-btn pic-btn-small mg-r-5" onclick="SysMgr.openUserEditModal(\'edit\',\'' + row.userId + '\')"><i class="icon icon-edit"></i>修改</a>' + 
	        			'<a class="btn pic-btn pic-btn-small mg-r-5" onclick="SysMgr.openSelectAccountModal(\'' + row.userId + '\')"><i class="icon  icon-wrench"></i>绑定账号</a>';
	        	}},
	        ],
	        height: me.getTableHeight(me.TYPE_USER),
	        rownumbers:true,              // 是否显示总记录数
		    multiselect:true ,            // 是否多选
	        rowNum : 20, 
	        rowList : [ 10, 20, 30 ], 
	        pager : '#' + me.$userTablePager.attr('id'),
	        beforeRequest: function()
	        {
	        	me.$userTable.setGridParam($.extend({},{'postData': me.getRequestParams(me.TYPE_USER)}));
	        }
		};
		this.$userTable.jqGrid(opts);
	},
	intiAccountGrid: function()
	{
		var me = this,
		opts = {
			url: contextPath + '/sys/getAccountList',
			colNames:['ID','账号名称','所属平台','操作'],
			colModel:[
	        	{name:'id',index:'id',hidden:true,key:true},
	        	{name:'name',index:'name'},
	        	{name:'platform',index:'platform'},
	        	{name:'',width:'75px',fixed:true,sortable:false,formatter:function(value,opts,row){
	        		return '<a class="btn pic-btn pic-btn-small" onclick="SysMgr.openAccountEditModal(\'edit\',\'' + row.id + '\')"><i class="icon icon-edit"></i>修改</a>';
	        	}},
	        ],
	        height: me.getTableHeight(me.TYPE_ACCOUNT),
	        rownumbers:true,              // 是否显示总记录数
		    multiselect:true ,            // 是否多选
	        rowNum : 20, 
	        rowList : [ 10, 20, 30 ], 
	        pager : '#' + me.$accountTablePager.attr('id'),
	        beforeRequest: function()
	        {
	        	me.$accountTable.setGridParam($.extend({},{'postData': me.getRequestParams(me.TYPE_ACCOUNT)}));
	        }
		};
		 this.$accountTable.jqGrid(opts);
	},
	openUserEditModal: function(type,userId)
	{
		SysUserMgr.openUserEditModal(type,userId,{'onSaveSuccess':this.onUserSaveSuccess});
	},
	openAccountEditModal: function(openType,id)
	{
		this.$modal.modal({'backdrop':false,'keyboard':true,'show':false}).modal('show');
		this.validator.resetForm();
		this.$errorMsg.parent().hide();
		this.$modal.find('.modal-title').html(openType == 'edit' ? '修改账号' : '新建账号');
		var me = this;
		if(openType == 'edit')
		{
			// 初始化数据
			$.ajax({
				url: contextPath + '/sys/getAccountById',
				data: {'id': id},
				success: function(result){
					result = result || {};
					me.$form.find('input,select').each(function(){
						if(this.name)
						{
							this.value = result[this.name];
						}	
					});
				},
				error: function()
				{
					me.$errorMsg.html('获取账号信息出错啦').parent().show();
				},
			});
		}else
		{
			this.$form.find('input,select').val('');
		}
	},
	doAccountEdit: function()
	{
		if(!this.$form.valid())
		{
			return false;
		}
		var me = this,
			url = contextPath + '/sys/editAccount',
			params = $.extend({},DataUtil.formSerializeObj(me.$form),{'userId':loginUser.userId,'userName':loginUser.userName});
		$.ajax({
			url: url,
			data: params,
			type: 'POST',
			success: function(result){
				me.$modal.modal('hide');
				layer.msg("保存成功",{icon:1,shift:0,offset:200});
				SysMgr.doReload(SysMgr.TYPE_ACCOUNT);
			},
			error: function(xhr){
				var msg = '未知错误';
				if(xhr && xhr.responseText == '2001')
				{
					msg = '账号已存在';
				}
				me.$errorMsg.html('保存失败，' + msg).parent().show(); 
			}
		});
	},
	onUserSaveSuccess: function()
	{
		SysMgr.doReload(SysMgr.TYPE_USER);
	},
	getTableHeight: function(type)
	{
		return (type == this.TYPE_USER ? this.$userTableWrapper.height() : this.$accountTableWrapper.height()) - 40*2 - 5;
	},
	getTable: function(type)
	{
		return (type == this.TYPE_USER) ? this.$userTable : this.$accountTable;
	},
	openSelectAccountModal: function(userId)
	{
		this.$bindModal.modal({'backdrop':false,'keyboard':true,'show':false}).modal('show');
		this.$bindErrorMsg.parent().hide();
		this.currentUserId = userId;
		var me = this;
		$.ajax({
			url: contextPath + '/sys/getBindAccountsInfo',
			data: {'userId':userId},
			success: function(data){
				data = data || [];
				me.createICheckBox(data);
			},
			error:function(){
			},
		});
		
		// 测试
//		var rows = [
//			{name: 'EBAY',children:[{'id':'1','name':'dsds'},{'id':'2','name':'dsddddds'}]},
//			{name: 'AMAZON',children:[{'id':'13','name':'dsds'}]},
//		];
//		this.createICheckBox(rows);
	},
	doBindAccounts: function()
	{
		var accountIds = this.getICheckBoxCheckedData();
//		if(!accountIds || !accountIds.length)
//		{
//			this.$bindErrorMsg.html('请选择要绑定的账号').parent().show();
//			return false;
//		}
		var me = this;
		$.ajax({
			url: contextPath + '/sys/doBindAccounts',
			data: {'userId':this.currentUserId,'accountIdsStr':accountIds.join(',')},
			success: function(){
				layer.msg("绑定成功",{icon:1,shift:0,offset:200});
				me.$bindModal.modal('hide');
			},
			error: function(){
				me.$bindErrorMsg.html('绑定失败，未知错误').parent().show();
			}
		});
	},
	getICheckBoxCheckedData: function()
	{
		var ids = [];
		this.$bindModalCon.find('.content-item').find('.item-body').find('input:checked').each(function(){
			if(this.id)
			{
				ids.push(this.id);
			}	
		});
		return ids;
	},
	createICheckBox: function(rows)
	{
//		{name:'EBAY',children:[{id:'1',name:'sasaa'}]}
		rows = rows || [];
		var row,children,$item,$ihead,$ibody;
		this.$bindModalCon.empty();
		for(var i=0;i<rows.length;i++)
		{
			row = rows[i];
			$item = $('<div>').addClass('content-item');
			$ihead = $('<div>').addClass('form-group item-head').append($('<label>').addClass('checkbox-inline')
					.append($('<input>').addClass('i-checks').attr("type","checkbox"))).append($('<span>').addClass('bold').html(row.name));
			$ibody = $('<div>').addClass('form-group item-body');
			children = row.children || [];
			for(var j=0;j<children.length;j++)
			{
				var $label = $('<label>').addClass('checkbox-inline');
				var $input = $('<input>').addClass('i-checks')
					.attr('id',children[j].id).attr('name',children[j].name)
					.attr("type","checkbox");
				if(String(children[j].checked) == 'true')
				{
					$input.attr('checked','checked');
				}	
				$label.append($input).append(children[j].name).appendTo($ibody);
			}	
			$item.append($ihead).append($ibody).appendTo(this.$bindModalCon);
			$item.find(".i-checks").iCheck2();
			// 事件处理
			$ihead.find('.i-checks').on('ifChecked ifUnchecked',function(evt){
				var $ichecks = $(this).closest('.item-head').siblings('.item-body').find('.i-checks');
				if(evt.type == 'ifChecked')
				{
					$ichecks.iCheck('check');
				}else
				{
					$ichecks.iCheck('uncheck');
				}	
			});
		}	
	},
};

//准备启动
SYS.ready(SysMgr.init);