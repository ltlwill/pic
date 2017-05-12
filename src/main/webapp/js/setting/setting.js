/**
 * 用户管理JS
 */
var SettingMgr = 
{
	init: function()
	{
		SettingMgr.initEles();
		SettingMgr.initElesData();
		SettingMgr.bindEvent();
	},
	initEles: function()
	{
		var me = this;
		// 个性化设置区域元素
		this.$settingForm = $('#setting_form');
		this.$switch = me.$settingForm.find('input[type="checkbox"]');
		this.$imgNum = me.$settingForm.find('input[type="number"]');
		var conf = window['picLocaStorageMgr'].getUserConfig();
		this.$switch.each(function(){
			$(this).bootstrapSwitch({
				'state': (typeof conf[this.name] == 'undefined' || conf[this.name] == null) ? false : conf[this.name],
				'onSwitchChange': function(event, state){
					me.onSwitchChange(this,event,state);
				}
			});
		});
		this.$imgNum.bind('input',function(evt){
			if(this.value > 100 || this.value < 1 || !this.value)
			{
				me.$settingForm.find('button[type="submit"]').click();
				return false;
			}	
			me.onSwitchChange(this,evt,this.value);
		}).val(window['picLocaStorageMgr'].getUserConfig().imgGridNum || 36);
	},
	initElesData: function()
	{
	},
	bindEvent: function()
	{
	},
	onSwitchChange: function(ele,event, state)
	{
		if(!window.localStorage)
		{
			console.log('此浏览器不支持本地存储！');
			return false;
		}
		var key = ele.name,
			val = state,
			storeName = 'picUserConfig';
		var conf = window['picLocaStorageMgr'].getUserConfig();
		// 重置键值对
		conf[key] = val;
		// 重新存储
		window['picLocaStorageMgr'].setUserConfig(conf);
	},
};

//准备启动
SYS.ready(SettingMgr.init);