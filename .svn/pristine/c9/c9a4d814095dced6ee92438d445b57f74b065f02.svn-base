/**
 * 顶层JS
 */
top.SYS = window.SYS = self.SYS = SYS = {};

SYS._items = new Array();
SYS.ready = function(fn)
{
	if(typeof fn == 'function')
	{
		SYS._ready(fn);
	}else if(typeof fn == 'object' )
	{
		if(fn.constructor == Array)
		{
			for(var i=0;i<fn.length;i++)
			{
				SYS._ready(fn[i]);
			}
		}
	}
};
SYS._ready = function(fn)
{
	if(typeof fn == 'function')
	{
		SYS._items.push(fn);
	}
};
SYS._launch = function()
{
	if(SYS._items && SYS._items.length > 0)
	{
		for(var i=0;i<SYS._items.length;i++)
		{
			SYS._items[i].call();
		}
	}
};

document.onreadystatechange = function(){
	if(document.readyState == 'complete')
	{
		SYS._launch();
	}
}



