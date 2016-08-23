package com.wlh.wpd.util;

import java.util.ArrayList;
import java.util.List;

/**
 * XMl工具类
 */
public class XmlTool {
	/**
	 * 整理XML的字符串列表
	 * 
	 * @param xmlLines
	 * @return List<String>
	 */
	public static List<String> trimXml(List<String> lines) {
		try {
			// 首先拼接为一个字符串
			String oneLine = appendToOneLine(lines);

			// 然后截取
			return oneLineToList(oneLine);
		} catch (Exception e) {
			return lines;
		}
	}

	/**
	 * 按标签获取值
	 * 
	 * @return value
	 */
	public static String getFirstByTagName(final List<String> lines, String tagName) {
		if (null != lines) {
			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);
				if (null != line && (line.startsWith("<" + tagName + " ") || line.startsWith("<" + tagName + ">"))) {
					// 获取开始Index和结束Index
					int beginIndex = line.indexOf(">") + 1;
					int endIndex = line.indexOf("</");
					if (beginIndex >= 0 && endIndex <= line.length() && beginIndex <= endIndex) {
						return line.substring(beginIndex, endIndex);
					}
				}
			}
		}

		return "";
	}

	/**
	 * 按标签获取所有值
	 * 
	 * @return value
	 */
	public static List<String> getAllByTagName(final List<String> lines, String tagName) {
		List<String> list = new ArrayList<String>();
		if (null != lines) {
			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);
				if (null != line && (line.startsWith("<" + tagName + " ") || line.startsWith("<" + tagName + ">"))) {
					// 获取开始Index和结束Index
					int beginIndex = line.indexOf(">") + 1;
					int endIndex = line.indexOf("</");
					if (beginIndex >= 0 && endIndex <= line.length() && beginIndex <= endIndex) {
						list.add(line.substring(beginIndex, endIndex));
					}
				}
			}
		}

		return list;
	}

	/**
	 * 按标签及关键字获取单个值
	 * 
	 * @return value
	 */
	public static String getFirstByTagNameAndKey(final List<String> xml, final String tagName, final String key) {
		if (xml != null) {
			for (int i = 0; i < xml.size(); i++) {
				String line = xml.get(i);
				if (null != line && (line.startsWith("<" + tagName + " ") || line.startsWith("<" + tagName + ">"))
						&& line.contains(key)) {
					return getByKeyName(line, key);
				}
			}
		}

		return "";
	}

	/**
	 * 按key获取值
	 * 
	 * @return value
	 */
	public static String getByKeyName(final String line, final String key) {
		if (null != line) {
			int start = -1;
			int end = -1;

			// 查找key的位置
			int index = line.indexOf(key);

			if (index >= 0) {
				for (int i = index; i < line.length(); i++) {
					if (line.charAt(i) == '\'' || line.charAt(i) == '\"') {
						if (start == -1) {
							start = i;
						} else if (end == -1) {
							end = i;
							break;
						}
					}
				}

				if (start >= 0 && end >= 0) {
					return line.substring(start + 1, end);
				}
			}

		}

		return "";
	}

	/**
	 * 获取标签的值
	 * 
	 * @return value
	 */
	public static String getTagValue(final String line) {
		if (null != line) {
			// 获取开始Index和结束Index
			int beginIndex = line.indexOf(">") + 1;
			int endIndex = line.indexOf("</");
			if (beginIndex >= 0 && endIndex <= line.length() && beginIndex <= endIndex) {
				return line.substring(beginIndex, endIndex);
			}
		}

		return "";
	}

	/**
	 * 判断此行的标签名称是否为预期的名称
	 * 
	 * @param line
	 * @param tagName
	 * @return boolean
	 */
	public static boolean checkTagName(final String line, final String tagName) {
		if (null != line && (line.startsWith("<" + tagName + " ") || line.startsWith("<" + tagName + ">"))) {
			return true;
		}

		return false;
	}

	/**
	 * 判断此行的标签名称是否为预期的名称
	 * 
	 * @param line
	 * @param tagNames
	 * @return 参数说明
	 * @return boolean 返回类型说明
	 */
	public static boolean checkTagName(final String line, final List<String> tagNames) {
		if (null != line && null != tagNames) {
			for (int i = 0; i < tagNames.size(); i++) {
				String tagName = tagNames.get(i);
				if (null != tagName && (line.startsWith("<" + tagName + " ") || line.startsWith("<" + tagName + ">"))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 将多行记录变为一行
	 * 
	 * @param lines
	 * @return String 返回类型说明
	 */
	private static String appendToOneLine(List<String> lines) {
		// 创建StringBuilder
		StringBuilder builder = new StringBuilder();

		// 注释标识
		boolean notesFlag = false;

		if (null != lines) {
			for (int i = 0; i < lines.size(); i++) {
				if (null != lines.get(i) && !"".equals(lines.get(i).trim())) {
					// 过滤前后空格
					String trimedLine = lines.get(i).trim();

					if (!notesFlag) {
						// 单行注释
						if (trimedLine.startsWith("<!--") && trimedLine.endsWith("-->")) {
							continue;
						}

						// 多行注释（开始行）
						if (trimedLine.startsWith("<!--")) {
							// 设置下一行标识
							notesFlag = true;
							continue;
						}

						// 不以<开始，则加空格
						if (!trimedLine.startsWith("<")) {
							builder.append(" ");
						}
						builder.append(trimedLine);
					} else {
						// 多行注释（结束行）
						if (trimedLine.endsWith("-->")) {
							// 设置下一行标识
							notesFlag = false;
						}

						continue;
					}
				}
			}
		}

		return builder.toString();
	}

	/**
	 * 将单行分解为多行
	 * 
	 * @param line
	 * @return List<String>
	 */
	private static List<String> oneLineToList(String line) {
		// xml行列表
		List<String> lines = new ArrayList<String>();

		if (null == line) {
			return lines;
		}

		// 构造新的List
		String[] lineArr = line.replaceAll(">\\s{0,}<", ">######<").split("######");
		if (null == lineArr) {
			return lines;
		}

		for (int i = 0; i < lineArr.length; i++) {
			if (null != lineArr[i]) {
				if (lineArr[i].contains("[CDATA[") && lines.size() > 0 && i + 1 < lineArr[i].length()) {// 去掉<![CDATA[]]>
					String tmp = lines.get(lines.size() - 1) + lineArr[i].substring(9, lineArr[i].length() - 3)
							+ lineArr[i + 1];
					lines.set(lines.size() - 1, tmp);

					i++;
				} else {
					lines.add(lineArr[i]);
				}
			}
		}

		return lines;
	}

}
