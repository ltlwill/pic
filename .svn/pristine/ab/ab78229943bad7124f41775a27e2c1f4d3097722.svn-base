
/**
 * 系统登录管理JS
 */
var LoginMgr = 
{
		options:
		{
			loginUrl:contextPath + "/userLogin?",
			homeUrl:contextPath + "/home"
		},
		// 初始化
		init:function()
		{
			LoginMgr.initEles();
			LoginMgr.initElesData();
			LoginMgr.bindEvent();
		},
		initEles: function()
		{
			this.$loginArea = $('.login-area');
			this.$notice = this.$loginArea.find('.notice');
			this.$noticeDetail = this.$notice.find('.notice-detail');
		},
		initElesData: function()
		{
			var settings = window.sysSettings || [];
			if(settings && settings.length)
			{
				var flag = false;
				for(var i in settings)
				{
					if(!settings[i].startTime && !settings[i].endTime)
					{
						continue;
					}
					flag = true;
					$('<p>').html((settings[i].startTime || '') +' 至  ' + (settings[i].endTime || '')).appendTo(this.$noticeDetail);
				}	
				flag ? this.$notice.show() : null;
			}	
//			this.$loginArea.slideDown('slow');
		},
		// 绑定事件
		bindEvent:function()
		{
			var _self = this;
			$("#form_submit").bind("click",this.login);
			$("#user_form input").focus(function(){
				$(this).removeClass("invalid");
			});
			// 绑定enter事件
			$(document).bind("keyup",function(e){
				e = (e ? e : window.event);
				if(e.keyCode == '13' || e.which == '13')
				{
					_self.login();
				}
			});
		},
		// 登录
		login:function()
		{
			var url,nameObj,pwdObj;
			url = LoginMgr.options.loginUrl;
			
			nameObj = $("#user_form input[id='name']");
			pwdObj = $("#user_form input[id='password']");
			// 如果验证不通过
			if(!LoginMgr.validate(new Array(nameObj,pwdObj)))
			{
				return false;
			}
			// 验证通过	
			$.ajax({
				url:url,
				type:'POST',
				data:{userName:nameObj.val(),password:pwdObj.val(),_r:Math.random()},
				/*dataType:"JSON",*/
				success:function(res)
				{
					LoginMgr.loginSuccess(res);
				},
				error:function(res)
				{
					LoginMgr.loginError();
				}
			});
			
		},
		// 登录无错误
		loginSuccess:function(res)
		{
			switch(res)
			{
				case 'ok' :
					LoginMgr.loginMsgProcess("hide");
					$("#form_submit").css({"padding":"5px 90px"}).html("正在登录");
					window.location.href= LoginMgr.options.homeUrl;
					break;
				case 'fail' :
					LoginMgr.loginMsgProcess("show","用户名或密码错误");
					break;
				case 'error' :
					LoginMgr.loginMsgProcess("show","出错啦，请联系管理员");
					break;
				case 'non_working_time':
					LoginMgr.loginMsgProcess("show","非工作时间，不允许登录");
					break;
				default:
					break;
			}
		},
		// 登录出错
		loginError:function()
		{
			LoginMgr.loginMsgProcess("show","出错啦，请联系管理员");
		},
		// 验证
		validate:function(objs)
		{
			if(objs == null || objs.length <= 0)
			{
				return false;
			}
			var flag = true;
			for(var i=0;i<objs.length;i++)
			{
				var v = $.trim($(objs[i]).val());
				if( v == null || v == '')
				{
					flag = false;
					$(objs[i]).addClass("invalid");
				}else
				{
					$(objs[i]).removeClass("invalid");
				}
			}	
			return flag;
		},
		loginMsgProcess:function(type,msg)
		{
			var loginForm = $(".login-form");
			var msgSpan = $(".login-form").children(".login-fail");
			if(!msgSpan.length)
			{
				msgSpan = $("<span class='login-fail'></span>").appendTo($(".login-form"));
			}
			switch(type)
			{
				case 'show':
					msgSpan.empty().html(msg);
					break;
				case 'hide':
					msgSpan.remove();
					break;
			}
		}
};

$L.ready(LoginMgr.init);






