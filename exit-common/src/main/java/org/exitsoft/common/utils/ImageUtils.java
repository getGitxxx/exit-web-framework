package org.exitsoft.common.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * 图片工具类
 * 
 * @author vincent
 *
 */
public class ImageUtils {
	
	/**
	 * 缩放图片
	 * 
	 * @param input
	 *            图片流
	 * @param targetPath
	 *            保存文件路径
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 */
	public static void scale(InputStream input, File saveFile, int width, int height) {
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(input);// 读入文件

			Image image = bufferedImage.getScaledInstance(width, height,
					Image.SCALE_DEFAULT);

			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB); // 缩放图像
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			if (!saveFile.exists()) {
				saveFile.mkdirs();
			}
			ImageIO.write(tag, "JPEG", saveFile);// 输出到bos
			tag.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
