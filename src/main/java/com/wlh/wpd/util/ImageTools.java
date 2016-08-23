package com.wlh.wpd.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 通过java获取图片的宽和高
 */
public class ImageTools {

	/**
	 * 获取图片的size [宽，高]
	 * 
	 * @param imageData
	 * @return
	 */
	public static int[] getMemeryImageSize(byte[] imageData) {
		if (imageData == null) {
			return new int[] { 0, 0 };
		}

		InputStream is = new ByteArrayInputStream(imageData);
		try {
			BufferedImage src = javax.imageio.ImageIO.read(is);
			return new int[] { src.getWidth(), src.getHeight() };
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return new int[] { 0, 0 };
	}
}
