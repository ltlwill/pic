package com.hengzhiyi.it.pic.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengzhiyi.it.pic.dao.IImgSearchDao;
import com.hengzhiyi.it.pic.dao.IUserAccountDao;
import com.hengzhiyi.it.pic.vo.ImageVO;
import com.hengzhiyi.it.pic.vo.PagedVO;
import com.hengzhiyi.it.pic.vo.User;

/**
 * 图片搜索服务实现类
 * 
 * @author liutianlong
 *
 */
@Service
public class ImgSearchService extends SysAbstractService implements
		IImgSearchService
{

	@Autowired
	private IImgSearchDao imgSearchDao;

	@Autowired
	private IUserService userService;

	@Autowired
	private IUserAccountDao userAccountDao;

	@Override
	public PagedVO<List<ImageVO>> findImages(HttpServletRequest request,
			PagedVO<?> params) throws Exception
	{
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		// 获取当前用户所具有的账号
		User user = userService.getUserById(params.getUserId());
		paramsMap.put("pagedVo", params);
		paramsMap.put("user", user);

		// 获取当前页数据
		List<ImageVO> vos = imgSearchDao.findImages(paramsMap);
		// 获取总记录数
		int total = imgSearchDao.findImagesCount(paramsMap);
		if (vos != null && !vos.isEmpty())
		{
			// 设置公共属性
			setCommonProperties(request, vos);
		}
		PagedVO<List<ImageVO>> result = new PagedVO<List<ImageVO>>();
		// 设置数据
		result.setRows(vos);
		result.setCurrPage(params.getCurrPage());
		result.setPageSize(params.getPageSize());
		result.setTotal(total);
		return result;
	}

	@Override
	public List<ImageVO> findImagesByIds(List<String> ids) throws Exception
	{
		return imgSearchDao.findImagesByIds(ids);
	}

	@Override
	public List<File> getImageFilesByIds(String ids) throws Exception
	{
		if (StringUtils.isBlank(ids))
		{
			return null;
		}
		String[] idsArr = ids.split(",");
		List<String> idList = new ArrayList<String>();
		// 将ID串转换为集合
		for (String id : idsArr)
		{
			idList.add(id);
		}
		// 查询数据库
		List<ImageVO> imgVos = findImagesByIds(idList);
		if (imgVos == null || imgVos.isEmpty())
		{
			return null;
		}
		// 根据路径生产文件对象
		List<File> files = new ArrayList<File>();
		for (ImageVO vo : imgVos)
		{
			files.add(new File(this.getVirtualDir() + File.separator
					+ vo.getRelativePath()));
		}
		return files;
	}

	@Override
	public File getImageByRelativePath(String path) throws Exception
	{
		if (StringUtils.isEmpty(path))
		{
			throw new RuntimeException(
					"Download image error,the file path is null.");
		}
		return new File(this.getVirtualDir() + File.separator + path);
	}

	/**
	 * 创建URL
	 * 
	 * @param relativePath
	 * @return
	 */
	private String createUrl(HttpServletRequest request, String relativePath)
	{
		// 自动加上协议“//”
		// return "//" + getRequestHost(request) + this.getVirtualDirContext()
		// + "/" + relativePath;
		// 获取完整协议
		return getRequestHost(request) + this.getVirtualDirContext() + "/"
				+ relativePath;
	}

	@Override
	public void deleteImagesByIds(String ids) throws Exception
	{
		if (StringUtils.isEmpty(ids))
		{
			throw new RuntimeException(
					"Delete images error,cause the parameter is null.");
		}
		List<String> idList = Arrays.asList(ids.split(","));
		// 获取所有要删除的图片的信息
		List<ImageVO> vos = imgSearchDao.findImagesByIds(idList);
		// 如果在数据库中不存在，则不执行后面的操作
		if (vos == null || vos.isEmpty())
		{
			return;
		}
		// 从数据库删除
		imgSearchDao.deleteImagesByIds(idList);
		// 从磁盘上删除
		deleteImageFiles(vos);
	}

	/**
	 * 从磁盘中删除图片文件
	 * 
	 * @param vos
	 * @throws Exception
	 */
	private void deleteImageFiles(List<ImageVO> vos) throws Exception
	{
		for (ImageVO vo : vos)
		{
			// 删除原始图片
			FileUtils.forceDelete(new File(getVirtualDir() + File.separator
					+ vo.getRelativePath()));
			// 删除压缩图片
			FileUtils.forceDelete(new File(getVirtualDir() + File.separator
					+ vo.getSmallRelativePath()));
		}
	}

	@Override
	public List<String> getImagesUrlBySku(HttpServletRequest request, String sku)
			throws Exception
	{
		if (StringUtils.isBlank(sku))
		{
			return null;
		}
		List<ImageVO> images = imgSearchDao.getImagesBySku(sku.trim());
		if (images == null || images.isEmpty())
		{
			return null;
		}
		List<String> urls = new ArrayList<String>();
		for (ImageVO vo : images)
		{
			urls.add(createUrl(request, vo.getRelativePath()));
		}
		return urls;
	}

	@Override
	public List<ImageVO> getImagesBySku(HttpServletRequest request, String sku)
			throws Exception
	{
		if (StringUtils.isBlank(sku))
		{
			return null;
		}
		List<ImageVO> images = imgSearchDao.getImagesBySku(sku.trim());
		if (images == null || images.isEmpty())
		{
			return null;
		}
		// 设置公共属性
		setCommonProperties(request, images);
		return images;
	}

	private void setCommonProperties(HttpServletRequest request,
			List<ImageVO> vos)
	{
		if (vos == null || vos.isEmpty())
		{
			return;
		}
		for (ImageVO vo : vos)
		{
			// 大图片地址
			vo.setUrl(createUrl(request, vo.getRelativePath()));
			// 小图片地址
			vo.setSmallUrl(createUrl(request, vo.getSmallRelativePath()));
			// 图片上下文
			vo.setContextPath(this.getVirtualDirContext());
		}
	}
}
