package com.hengzhiyi.it.pic.dao;

import org.springframework.stereotype.Repository;

import com.hengzhiyi.it.pic.vo.ImageVO;
import com.hengzhiyi.it.pic.vo.UploadLogVO;

/**
 * 图片上传DAO接口
 * 
 * @author liutianlong
 *
 */
@Repository
public interface IImgUploadDao
{
	/**
	 * 保存图片
	 * 
	 * @param vo
	 */
	void saveImage(ImageVO vo);

	/**
	 * 保存上传日志
	 * 
	 * @param log
	 */
	void saveUploadLog(UploadLogVO log);

}
