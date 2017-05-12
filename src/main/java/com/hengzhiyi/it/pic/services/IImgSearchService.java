package com.hengzhiyi.it.pic.services;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hengzhiyi.it.pic.vo.ImageVO;
import com.hengzhiyi.it.pic.vo.PagedVO;

/**
 * 图片搜索服务接口
 * 
 * @author liutianlong
 *
 */
public interface IImgSearchService
{

	/**
	 * 根据关键字查找图片
	 * 
	 * @param request
	 * @param keWord
	 * @return
	 * @throws Exception
	 */
	PagedVO<List<ImageVO>> findImages(HttpServletRequest request, PagedVO<?> params)
			throws Exception;

	/**
	 * 根据图片ID获取图片
	 * 
	 * @return
	 * @throws Exception
	 */
	List<ImageVO> findImagesByIds(List<String> ids) throws Exception;

	/**
	 * 根据图片ID获取图片文件
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	List<File> getImageFilesByIds(String ids) throws Exception;

	/**
	 * 根据文件的相对路径获取图片
	 * 
	 * @param path
	 * @return
	 */
	File getImageByRelativePath(String path) throws Exception;

	/**
	 * 根据ID批量删除图片
	 * 
	 * @param ids
	 * @throws Exception
	 */
	void deleteImagesByIds(String ids) throws Exception;

	/**
	 * 根据SKU获取相关图片连接
	 * 
	 * @param sku
	 * @return
	 */
	List<String> getImagesUrlBySku(HttpServletRequest request, String sku)
			throws Exception;

	/**
	 * 根据SKU获取图片所有信息
	 * 
	 * @param request
	 * @param sku 
	 * @return
	 * @throws Exception
	 */
	List<ImageVO> getImagesBySku(HttpServletRequest request, String sku)
			throws Exception;

}
