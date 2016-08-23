package com.wlh.wpd.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 */
public class FileUtil
{
    /**
     * 读取文件内容
     * @param file
     * @param defaultEnc
     * @return List<String>
     */
    public static List<String> getFileContent(File file, String defaultEnc)
    {
		// 文件内容
		List<String> list = new ArrayList<String>();
		FileInputStream in = null;
		BufferedReader br = null;

		if (null != file) {
			try {
				// 读文件
				in = new FileInputStream(file);
				// 指定编码格式读取
				br = new BufferedReader(new UnicodeReader(in, defaultEnc));
				// 读文件
				String line = br.readLine();
				while (null != line) {
					list.add(line);
					line = br.readLine();
				}
			} catch (IOException e) {
				if (null != br) {
					try {
						br.close();
					} catch (IOException e1) {

					}
				}
			}
		}

		return list;
    }

    /**
     * 读取文件内容
     * @param file
     * @return List<String>
     */
    public static List<String> getFileContent(File file)
    {
		// 文件内容
		List<String> list = new ArrayList<String>();

		FileInputStream in = null;
		BufferedReader br = null;

		if (null != file) {
			try {
				// 读文件
				in = new FileInputStream(file);
				// 指定读取文件时以JVM指定的编码格式读取
				br = new BufferedReader(new UnicodeReader(in, Charset.defaultCharset().name()));
				// 读文件
				String line = br.readLine();
				while (null != line) {
					list.add(line);
					line = br.readLine();
				}
			} catch (IOException e) {
				if (null != br) {
					try {
						br.close();
					} catch (IOException e1) {

					}
				}
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return list;
    }
    
    /**
	 * 读取文件内容
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	public static String readFileData(File file) throws Exception {
		StringBuilder sb = new StringBuilder();
		FileInputStream in = null;
		BufferedReader br = null;
		try {
			in = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(in));
			String data = null;
			while ((data = br.readLine()) != null) {
				sb.append(data);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

}
