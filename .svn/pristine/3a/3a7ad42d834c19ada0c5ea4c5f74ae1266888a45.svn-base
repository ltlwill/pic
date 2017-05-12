-- 删除图片数据库中重复的图片名称的数据，只保留其中一条
delete from pic_images b
 where b.name in (select a.name
                    from pic_images a
                   group by a.name
                  having count(a.name) > 1)
   and b.id not in (select max(c.id)
                      from pic_images c
                     group by c.name
                    having count(c.name) > 1);