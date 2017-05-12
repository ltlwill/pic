/**
 * 字符串工具JS
 */

var StringHelper = {};
// 判斷空
StringHelper.isEmpty = function(s)
{
	if( s == null || s == '' || s == 'null')
	{
		return true;
	}	
	return false;
};
// 判斷非空
StringHelper.isNotEmpty = function(s)
{
	return !this.isEmpty(s);
};

