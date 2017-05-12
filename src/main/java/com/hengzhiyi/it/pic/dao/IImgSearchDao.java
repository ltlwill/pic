package com.hengzhiyi.it.pic.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hengzhiyi.it.pic.vo.ImageVO;

/**
 * 图片搜索DAO接口
 * 
 * @author liutianlong
 *
 */
@Repository
public interface IImgSearchDao
{
	/**
	 * 查找图片
	 * 
	 * @param params
	 * @return
	 */
	List<ImageVO> findImages(Map<String, Object> params);

	/**
	 * 获取图片总数
	 * 
	 * @param params
	 * @return
	 */
	int findImagesCount(Map<String, Object> params);

	/**
	 * 根据图片ID获取图片
	 * 
	 * @param ids
	 * @return
	 */
	List<ImageVO> findImagesByIds(List<String> ids);

	/**
	 * 根据参数删除图片
	 * 
	 * @param ids
	 */
	void deleteImagesByIds(List<String> ids);

	/**
	 * 根据SKU获取相关的图片信息
	 * 
	 * @param sku
	 * @return
	 */
	List<ImageVO> getImagesBySku(String sku);

}
