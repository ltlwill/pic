package com.hengzhiyi.it.pic.test.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hengzhiyi.it.pic.test.dao.DataUpgradeDao;
import com.hengzhiyi.it.pic.tools.ImageCompressHelper;
import com.hengzhiyi.it.pic.tools.ListHelper;
import com.hengzhiyi.it.pic.vo.ImageVO;

@Service
public class DataUpgradeServiceImpl implements DataUpgradeService
{

	@Autowired
	private DataUpgradeDao dataUpgradeDao;

	@Value("${virtualDir}")
	private String virtualDir;

	@Value("${defaultCompressWidth}")
	private Integer defaultCompressWidth;

	@Value("${defaultCompressHeight}")
	private Integer defaultCompressHeight;

	@Value("${compressFolder}")
	private String compressFolder;

	/**
	 * 升级数据，将没有压缩图片的老数据生成相遇的压缩图片
	 */
	@Override
	public void upgrade() throws Exception
	{
		// 获取还没有压缩图片的数据
		List<ImageVO> vos = dataUpgradeDao.getNoSmallImageData();
		if (vos == null || vos.isEmpty())
		{
			return;
		}
		// 单线程
		for (ImageVO vo : vos)
		{
			// 创建压缩图片
			createSmallImage(vo);
		}
		// 多线程执行
//		excute(vos);
		
		// 分割数据，以免语句过长执行出错，默认500条执行一次
		List<List<ImageVO>> splitList = ListHelper.split(vos,500);
		for(List<ImageVO> list : splitList)
		{
			// 批量更新新信息到数据库
			dataUpgradeDao.batchUpgrade(list);
		}	
	}
	
	private void createSmallImages(List<ImageVO> list) throws Exception
	{
		for(ImageVO vo : list)
		{
			createSmallImage(vo);
		}	
	}

	private void createSmallImage(ImageVO vo) throws Exception
	{
		File srcFile = new File(vo.getAbsolutePath());
		String baseDir = vo.getAbsolutePath().substring(0,
				vo.getAbsolutePath().lastIndexOf(vo.getRelativePath()) - 1);

		// 压缩图片
		String[] arr = ImageCompressHelper.compress(srcFile, baseDir,
				compressFolder, defaultCompressWidth, defaultCompressHeight);

		vo.setSmallAbsolutePath(arr[0]);
		vo.setSmallRelativePath(arr[1]);
	}
	
	@SuppressWarnings("unused")
	private void excute(final List<ImageVO> vos) throws Exception
	{
		// 获取计算机处理器数量，开启的线程数量等于处理器数量*2
		int threadSize = Runtime.getRuntime().availableProcessors() * 2;
		int splitSize = vos.size() / threadSize;
		List<List<ImageVO>> splitList = ListHelper.split(vos,splitSize);
		
		ExecutorService service = Executors.newFixedThreadPool(threadSize);
		// 创建多线程任务
		List<Callable<List<ImageVO>>> callables = createTask(splitList);
		
		// 执行任务
		service.invokeAll(callables);
	}
	
	private List<Callable<List<ImageVO>>> createTask(List<List<ImageVO>> splitList)
	{
		List<Callable<List<ImageVO>>> callables = new ArrayList<Callable<List<ImageVO>>>(splitList.size());
		for(final List<ImageVO> list : splitList )
		{
			callables.add(new Callable<List<ImageVO>>()
			{
				@Override
				public List<ImageVO> call() throws Exception
				{
					// 生成压缩图片
					createSmallImages(list);
					return list;
				}
			});
		}	
		return callables;
	}
}
