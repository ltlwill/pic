
--oracle管理员账号登录(服务器地址:112.74.193.52,端口:1521,全局数据库名:orcl)，创建pic系统数据库用户:pic,密码:****
create user pic identified by ****;
-- 一次性授权更多的授权给pic用户
grant connect,resource to pic;
-- 授权目录权限
grant create any directory to pic;
-- 删除目录权限
grant drop any directory to pic;

-- 创建pic系统测试用户
create user pic_test identified by test1234;
-- 一次性授权更多的授权给pic_test用户
grant connect,resource to pic;





