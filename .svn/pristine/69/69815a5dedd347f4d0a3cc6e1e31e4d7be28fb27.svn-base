package com.hengzhiyi.it.pic.test.junit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hengzhiyi.it.pic.test.services.DataUpgradeService;

/**
 * 生成压缩图片的升级程序，使用junit实现
 * 
 * @author liutianlong
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/test_applicationContext.xml")
@Transactional(transactionManager="txManager_test",rollbackFor=Throwable.class)
@Rollback
public class SmallImageUpgradeRunner
{

	@Autowired
	private DataUpgradeService dataUpgradeService;
	
	/**
	 * 升级
	 */
	@Test
	@Transactional(rollbackFor=Exception.class)
	@Rollback(false)
	public void upgrade()
	{
		System.out.println("-----------开始升级数据-----------");
		long t1 = System.currentTimeMillis();
		// TODO 注意，在执行前，请确认classpath下的test_image.properties,test_jdbc_properties文件的配置是否是想要的
		try
		{
			dataUpgradeService.upgrade();
		}catch(Exception e)
		{
			Assert.fail(e.getMessage());
			throw new RuntimeException(e);
		}
		System.out.println("-----------结束升级数据-----------");
		long t2 = System.currentTimeMillis();
		System.out.println("总计耗时：" + (t2 - t1) + "ms");
	}

}
