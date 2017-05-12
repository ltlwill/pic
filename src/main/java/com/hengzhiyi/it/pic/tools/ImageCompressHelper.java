package com.hengzhiyi.it.pic.tools;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 图片压缩工具类
 * 
 * @author liutianlong
 *
 */
public class ImageCompressHelper
{

	/**
	 * 
	 * @param srcFile
	 *            源图片
	 * @param baseDir
	 *            根目录
	 * @param compressDir
	 *            压缩图片存放目录
	 * @param destWidth
	 *            目标宽度
	 * @param destHeight
	 *            目标高度
	 * @return 数组{0:绝对路劲，1:相对路径}
	 */
	public static String[] compress(File srcFile, String baseDir, String compressDir,
			int destWidth, int destHeight) throws IOException
	{
		String fileName = srcFile.getName(), name = null, suffix = null, relativePath = null;
		name = fileName.substring(0, fileName.lastIndexOf("."));
		suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		// 相对路径
		relativePath = compressDir + File.separator + name + "_small." + suffix;
		// 压缩版文件
		File compressFile = new File(baseDir + File.separator + relativePath);
		// 如果父路径不存在,则创建父路径
		if (!compressFile.getParentFile().exists())
		{
			compressFile.getParentFile().mkdirs();
		}

		// 开始创建压缩版的图片
		BufferedImage srcImage = ImageIO.read(srcFile);
		Image scaleImage = srcImage.getScaledInstance(destWidth, destHeight,
				Image.SCALE_SMOOTH);
		BufferedImage bScaledImage = new BufferedImage(destWidth, destHeight,
				BufferedImage.TYPE_INT_RGB);
		bScaledImage.getGraphics().drawImage(scaleImage, 0, 0, null);
		ImageIO.write(bScaledImage, suffix.toUpperCase(), compressFile);
		
		return new String[] { compressFile.getAbsolutePath(), relativePath };
	}

}
