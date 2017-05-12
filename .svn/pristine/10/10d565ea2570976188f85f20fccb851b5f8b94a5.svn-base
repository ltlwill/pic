-- add by ltl 2016-12-8 pic系统增加用户绑定账号业务的数据升级
-- 创建连接到portal（刊登系统）的正式数据库的dblink
create public database link PORTAL_DB_LINK
  connect to EBAY_HZY
  using '(DESCRIPTION =
    (ADDRESS = (PROTOCOL = TCP)(HOST = 192.168.2.109)(PORT = 1521))
    (CONNECT_DATA =
      (SERVER = DEDICATED)
      (SERVICE_NAME = orcl)
    )
  )';

-- 将portal系统的账号信息全部导入到pic系统中
insert into pic_account
  (id, name, platform, user_id, user_name, create_time)
  select pic_account_seq.nextval,
         a.ebayname,
         a.type,
         (select u.id from pic_user u where u.name = 'admin'),
         (select u.name from pic_user u where u.name = 'admin'),
         sysdate
    from ebay_user@portal_db_link a;  