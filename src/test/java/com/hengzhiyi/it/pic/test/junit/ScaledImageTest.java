package com.hengzhiyi.it.pic.test.junit;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.Test;

public class ScaledImageTest
{

	@Test
	public void scaled()
	{
		String basePath = "D:\\test\\scaled images";
		File srcFile = new File(basePath + File.separator + "3.jpg");
		String fileName = srcFile.getName(), name = null, suffix = null;
		name = fileName.substring(0, fileName.lastIndexOf("."));
		suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		File destFile = new File(basePath + File.separator + name + "_small."
				+ suffix);

		int width = 140, height = width;
		try
		{
			BufferedImage srcImage = ImageIO.read(srcFile);
			Image scaleImage = srcImage.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);
			BufferedImage bScaledImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			bScaledImage.getGraphics().drawImage(scaleImage, 0, 0, null);

			ImageIO.write(bScaledImage, suffix.toUpperCase(), destFile);
		} catch (Exception e)
		{
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

	}
}
