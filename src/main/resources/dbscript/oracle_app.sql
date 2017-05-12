------------------------------- 创建图片存储表--------------------------------
create table pic_images(
       id number not null,
       name varchar2(500),
       url varchar(1000),
       extension varchar(50),
       absolute_path varchar2(1000),
       relative_path varchar2(1000),
       upload_date date,
       user_id varchar(100),
       user_name varchar(100),
       remark varchar2(2000),
       SMALL_ABSOLUTE_PATH VARCHAR2(500),
  		SMALL_RELATIVE_PATH VARCHAR2(200)
);
-- 设置主键
alter table pic_images add constraint PIC_IMAGES_PK primary key (id);
comment on table pic_images is '图片存储表';
comment on column pic_images.name is '图片名';
comment on column pic_images.url is '生成的图片http路径';
comment on column pic_images.extension is '图片扩展名';
comment on column pic_images.absolute_path is '图片存储的绝对路径';
comment on column pic_images.relative_path is '图片存储的相对路径';
comment on column pic_images.upload_date is '图片上传时间';
comment on column pic_images.user_id is '上传图片的用户ID';
comment on column pic_images.user_name is '上传图片的用户名称';
comment on column pic_images.remark is '备注';
comment on column PIC_IMAGES.SMALL_ABSOLUTE_PATH
  is '压缩版图片的绝对路径';
comment on column PIC_IMAGES.SMALL_RELATIVE_PATH
  is '压缩版图片的相对路径';
-- 创建序列
create sequence pic_images_seq;


------------------------------ 创建上传日志  ------------------------------
create table pic_upload_log(
       id number not null,
       operator_id varchar2(100),
       operator_name varchar2(100),
       file_name varchar2(500),
       absolute_path varchar2(1000),
       relative_path varchar2(1000),
       upload_date date
);
-- 设置主键
alter table pic_upload_log add constraint PIC_UPLOAD_LOG_PK primary key (id);

comment on table pic_upload_log is '图片上传日志表';
comment on column pic_upload_log.operator_id is '上传人ID';
comment on column pic_upload_log.operator_name is '上传人名称';
comment on column pic_upload_log.file_name is '文件名称';
comment on column pic_upload_log.absolute_path is '文件绝对路径';
comment on column pic_upload_log.relative_path is '文件相对路径';
comment on column pic_upload_log.upload_date is '文件上传时间';
-- 创建日志表序列
create sequence pic_upload_log_seq;

-------------------------------- 创建系统用户表 ---------------------------------------
-- 创建PIC系统用户表
create table pic_user(
       id number not null,
       name varchar2(100) unique not null,
       password varchar2(100) not null,
       age number(3),
       address varchar2(1000),
       phone varchar2(100),
       email varchar2(100)     
);
-- 创建主键
alter table pic_user add constraint PIC_USER_PK primary key (id);
-- 增加注释
comment on table pic_user is 'PIC系统用户表';
comment on column pic_user.name is '用户名';
comment on column pic_user.password is '用户密码';
comment on column pic_user.age is '年龄';
comment on column pic_user.address is '地址';
comment on column pic_user.phone is '联系电话';
comment on column pic_user.email is '电子邮箱';
-- 创建用户表序列
create sequence pic_user_seq;
-- 手动插入数据
insert into pic_user
values
  (pic_user_seq.nextval, 'hzy', 'hzy1234', 0, null, null, null);
  
insert into pic_user
values
  (pic_user_seq.nextval, 'admin', 'admin1234', 0, null, null, null);

commit;


-- 修改用户表  ltl 2016-10-18
alter table pic_user add nick_name varchar2(50);
alter table pic_user add CREATE_TIME date;
alter table pic_user add STATUS number;
alter table pic_user add pwd_status number(2) default 0;

comment on column PIC_USER.NICK_NAME
  is '昵称';
comment on column PIC_USER.CREATE_TIME
  is '创建时间';
comment on column PIC_USER.STATUS
  is '状态(0:禁用,1:启用)';
comment on column pic_user.pwd_status is '密码状态（0：初始化，1：非初始化）';

-- 将老用户都设置为启用状态  
update pic_user a set a.status = 1;

-- add by ltl 2016-10-26
alter table pic_user add user_type number(2);
comment on column pic_user.user_type is '用户类型(0：管理员，1：子管理员：2:普通人员：空：普通人员)';

-- 将admin用户类型修改为0（管理员）
update pic_user a set a.user_type = 0 where a.name = 'admin';


-- 2016-12-2 by ltl APP的token表
-- 创建token表
create table pic_app_token(
    id number not null,
    token varchar2(200),
    user_id varchar2(50),
    device_id varchar2(100),
    status number(2),
    login_time date
);
-- 创建序列
create sequence pic_app_token_seq;
-- 设置主键
alter table pic_app_token add constraint pic_app_token_pk primary key(id);
comment on table pic_app_token
  is 'APP用户token表';
-- Add comments to the columns 
comment on column pic_app_token.token
  is 'token';
comment on column pic_app_token.user_id
  is '用户ID';
comment on column pic_app_token.device_id
  is '设备ID（移动设置的唯一标识UUID）';  
comment on column pic_app_token.status
  is 'token状态（0：失效，1:有效）';
comment on column pic_app_token.login_time
  is '登录时间';    


-- 增加账号管理 by ltl 2016-12-7
-- 创建账号表
create table pic_account(
    id number not null,
    name varchar2(100),
    platform varchar2(100),
    user_id varchar2(50),
    user_name varchar2(100),
    create_time date
);
-- 创建序列
create sequence pic_account_seq;
-- 创建主键
alter table pic_account add constraint pic_account_pk primary key (id);
-- 添加注释
comment on table pic_account is '账号表';
comment on column pic_account.name is '账号名称';
comment on column pic_account.platform is '所属平台';
comment on column pic_account.user_id is '创建人ID';
comment on column pic_account.user_name is '创建人名称';
comment on column pic_account.create_time is '创建时间';

-- 创建用户与账号的关系表 by ltl 2016-12-8
create table pic_user_account( 
    user_id varchar2(50),
    account_id varchar2(50)    
);
comment on table pic_user_account is '用户与账号关系表';
comment on column pic_user_account.user_id is '用户ID';
comment on column pic_user_account.account_id is '账号ID';

-- 修改图片表，增加账号ID字段
alter table pic_images add account_id varchar2(50);
comment on column pic_images.account_id is '绑定账号ID';


-- 增加用户反馈表 by ltl 2016-12-15 begin
-- 创建APP 问题反馈表
-- 创建APP 问题反馈表
create table pic_app_feedback(
   question varchar2(1000),
   contact_type varchar2(10),
   contact varchar2(50),
   score varchar2(10),
   platform varchar2(20),
   os_version varchar2(20),
   app_id varchar2(100),
   app_version varchar2(50),
   imei varchar2(200),
   model varchar(200),
   plus_version varchar2(20),
   net varchar2(100),
   user_id varchar2(50),
   user_name varchar2(100),
   create_time date
);
comment on table pic_app_feedback is 'APP用户反馈表';
comment on column pic_app_feedback.question is '反馈的问题';
comment on column pic_app_feedback.contact_type is ' 联系方式（1：微信，2：QQ，3：邮箱）';
comment on column pic_app_feedback.contact is ' 联系方式内容';
comment on column pic_app_feedback.score is ' 应用评分（1到5分）';
comment on column pic_app_feedback.platform is '操作系统（android，ios等）';
comment on column pic_app_feedback.os_version is '操作系统版本号';
comment on column pic_app_feedback.app_id is '应用唯一标示';
comment on column pic_app_feedback.app_version is '应用的版本号';
comment on column pic_app_feedback.imei is '设备标识';
comment on column pic_app_feedback.model is '设置型号';
comment on column pic_app_feedback.plus_version is 'plus基座的版本号';
comment on column pic_app_feedback.net is ' 网络类型';
comment on column pic_app_feedback.user_id is '用户ID';
comment on column pic_app_feedback.user_name is '用户名称';
comment on column pic_app_feedback.create_time is '反馈时间';
-- 增加用户反馈表 by ltl 2016-12-15 end

---------------------------------------------------- by ltl 增加登录白名单字段 2017-5-8 begin
alter table pic_user add white_list_type number(2);
comment on column pic_user.white_list_type is '是否白名单（0：否,1：是）';
-- 升级老数据
update pic_user a set a.white_list_type = 0 where a.name <> 'admin';

-- 新增系统设置表
create table pic_sys_settings(
   content clob,
   update_time date   
);
comment on table pic_sys_settings is '系统设置表';
comment on column pic_sys_settings.content is '设置内容';
comment on column pic_sys_settings.update_time is '更新时间';
----------------------------------------------------  by ltl 增加登录白名单字段 2017-5-8 end







