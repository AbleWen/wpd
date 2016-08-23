package com.wlh.wpd.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static boolean contains(String s, String text, String delimiter) {
		if ((s == null) || (text == null) || (delimiter == null)) {
			return false;
		}

		if (!s.endsWith(delimiter)) {
			s += delimiter;
		}

		int pos = s.indexOf(delimiter + text + delimiter);

		if (pos == -1) {
			if (s.startsWith(text + delimiter)) {
				return true;
			}

			return false;
		}

		return true;
	}

	public static int count(String s, String text) {
		if ((s == null) || (text == null)) {
			return 0;
		}

		int count = 0;

		int pos = s.indexOf(text);

		while (pos != -1) {
			pos = s.indexOf(text, pos + text.length());
			count++;
		}

		return count;
	}

	public static String merge(String array[], String delimiter) {
		if (array == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < array.length; i++) {
			sb.append(array[i].trim());

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static String read(ClassLoader classLoader, String name) throws IOException {

		return read(classLoader.getResourceAsStream(name));
	}

	public static String read(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		StringBuffer sb = new StringBuffer();
		String line = null;

		while ((line = br.readLine()) != null) {
			sb.append(line).append('\n');
		}

		br.close();

		return sb.toString().trim();
	}

	public static String reverse(String s) {
		if (s == null) {
			return null;
		}

		char[] c = s.toCharArray();
		char[] reverse = new char[c.length];

		for (int i = 0; i < c.length; i++) {
			reverse[i] = c[c.length - i - 1];
		}

		return new String(reverse);
	}

	public static String shorten(String s) {
		return shorten(s, 20);
	}

	public static String shorten(String s, int length) {
		return shorten(s, length, "..");
	}

	public static String shorten(String s, String suffix) {
		return shorten(s, 20, suffix);
	}

	public static String shorten(String s, int length, String suffix) {
		if (s == null || suffix == null) {
			return null;
		}

		if (s.length() > length) {
			s = s.substring(0, length) + suffix;
		}

		return s;
	}

	public static String[] split(String s, String delimiter) {
		if (s == null || delimiter == null) {
			return new String[0];
		}

		if (!s.endsWith(delimiter)) {
			s += delimiter;
		}

		s = s.trim();

		if (s.equals(delimiter)) {
			return new String[0];
		}

		List<String> nodeValues = new ArrayList<String>();

		if (delimiter.equals("\n") || delimiter.equals("\r")) {
			try {
				BufferedReader br = new BufferedReader(new StringReader(s));

				String line = null;

				while ((line = br.readLine()) != null) {
					nodeValues.add(line);
				}

				br.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} else {
			int offset = 0;
			int pos = s.indexOf(delimiter, offset);

			while (pos != -1) {
				nodeValues.add(s.substring(offset, pos));

				offset = pos + delimiter.length();
				pos = s.indexOf(delimiter, offset);
			}
		}

		return (String[]) nodeValues.toArray(new String[0]);
	}

	public static boolean[] split(String s, String delimiter, boolean x) {
		String[] array = split(s, delimiter);
		boolean[] newArray = new boolean[array.length];

		for (int i = 0; i < array.length; i++) {
			boolean value = x;

			try {
				value = Boolean.valueOf(array[i]).booleanValue();
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static double[] split(String s, String delimiter, double x) {
		String[] array = split(s, delimiter);
		double[] newArray = new double[array.length];

		for (int i = 0; i < array.length; i++) {
			double value = x;

			try {
				value = Double.parseDouble(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static float[] split(String s, String delimiter, float x) {
		String[] array = split(s, delimiter);
		float[] newArray = new float[array.length];

		for (int i = 0; i < array.length; i++) {
			float value = x;

			try {
				value = Float.parseFloat(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static int[] split(String s, String delimiter, int x) {
		String[] array = split(s, delimiter);
		int[] newArray = new int[array.length];

		for (int i = 0; i < array.length; i++) {
			int value = x;

			try {
				value = Integer.parseInt(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static long[] split(String s, String delimiter, long x) {
		String[] array = split(s, delimiter);
		long[] newArray = new long[array.length];

		for (int i = 0; i < array.length; i++) {
			long value = x;

			try {
				value = Long.parseLong(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static short[] split(String s, String delimiter, short x) {
		String[] array = split(s, delimiter);
		short[] newArray = new short[array.length];

		for (int i = 0; i < array.length; i++) {
			short value = x;

			try {
				value = Short.parseShort(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static final String stackTrace(Throwable t) {
		String s = null;

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			t.printStackTrace(new PrintWriter(baos, true));
			s = baos.toString();
		} catch (Exception e) {
		}

		return s;
	}

	public static boolean startsWith(String s, String begin) {
		if ((s == null) || (begin == null)) {
			return false;
		}

		if (begin.length() > s.length()) {
			return false;
		}

		String temp = s.substring(0, begin.length());

		if (temp.equalsIgnoreCase(begin)) {
			return true;
		} else {
			return false;
		}
	}

	public static String wrap(String text) {
		return wrap(text, 80);
	}

	public static String wrap(String text, int width) {
		if (text == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		try {
			BufferedReader br = new BufferedReader(new StringReader(text));

			String s = "";

			while ((s = br.readLine()) != null) {
				if (s.length() == 0) {
					sb.append("\n");
				} else {
					while (true) {
						int pos = s.lastIndexOf(' ', width);

						if ((pos == -1) && (s.length() > width)) {
							sb.append(s.substring(0, width));
							sb.append("\n");

							s = s.substring(width, s.length()).trim();
						} else if ((pos != -1) && (s.length() > width)) {
							sb.append(s.substring(0, pos));
							sb.append("\n");

							s = s.substring(pos, s.length()).trim();
						} else {
							sb.append(s);
							sb.append("\n");

							break;
						}
					}
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return sb.toString();
	}

	public static String getPassword(int length, String key) {

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; i++) {
			sb.append(key.charAt((int) (Math.random() * key.length())));
		}

		return sb.toString();
	}

	public static String getPassword(int length) {
		String key = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		return getPassword(length, key);
	}

	/**
	 * Encode a string using algorithm specified in web.xml and return the
	 * resulting encrypted password. If exception, the plain credentials string
	 * is returned
	 * 
	 * @param password
	 *            Password or other credentials to use in authenticating this
	 *            username
	 * @param algorithm
	 *            Algorithm used to do the digest
	 * 
	 * @return encypted password based on the algorithm.
	 */
	public static String encodePassword(String password, String algorithm) {
		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;

		try {
			// first create an instance, given the provider
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			System.err.print("Exception: " + e);

			return password;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < encodedPassword.length; i++) {
			if (((int) encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}

			buf.append(Long.toString((int) encodedPassword[i] & 0xff, 16));
		}

		return buf.toString();
	}

	public static String encodeString(String str) {
		return Base64.encodeBytes((str.getBytes())).trim();
	}

	public static String decodeString(String str) {
		try {
			return new String(Base64.decode(str));
		} catch (IOException io) {
			throw new RuntimeException(io.getMessage(), io.getCause());
		}
	}

	public static StringBuffer appendCond(StringBuffer cdtn, String exp) {
		if (cdtn.length() != 0) {
			cdtn.append("AND");
		}
		cdtn.append(" " + exp + " ");

		return cdtn;
	}

	public static boolean isEmpty(String str) {
		return !org.apache.commons.lang.StringUtils.isNotEmpty(str);
	}

	public static boolean isNotEmpty(String str) {
		return org.apache.commons.lang.StringUtils.isNotEmpty(str);
	}

	public static final String replace(String line, String oldString, String newString) {
		if (line == null)
			return null;
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char line2[] = line.toCharArray();
			char newString2[] = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j;
			for (j = i; (i = line.indexOf(oldString, i)) > 0; j = i) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
			}

			buf.append(line2, j, line2.length - j);
			return buf.toString();
		} else {
			return line;
		}
	}

	public static final String trimNull(String str) {
		str = str != null ? str : "";
		return str;
	}

	public static final String trimNullAndSpace(String str) {
		if (str == null) {
			return "";
		} else {
			String tempStr = str.trim();
			return tempStr;
		}
	}

	public static final String trimForHtml(String str) {
		if (str == null || str.trim().equals(""))
			str = "&nbsp;";
		return str;
	}

	public static final String trimForHtml(Object obj) {
		return trimForHtml(objToString(obj));
	}

	public static final String objToString(Object obj) {
		return obj != null ? obj.toString() : "";
	}

	public static String filterNewline(String input) {
		StringBuffer filtered = new StringBuffer(input.length());
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c == '\r' && input.charAt(i + 1) == '\n') {
				filtered.append("&#13");
				i++;
				continue;
			}
			if (c == '"')
				filtered.append("&quot;");
			else
				filtered.append(c);
		}

		return filtered.toString();
	}

	public static String filterForHtml(String input) {
		StringBuffer filtered = new StringBuffer(input.length());
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c == ' ') {
				filtered.append("&nbsp;");
				continue;
			}
			if (c == '<') {
				filtered.append("&lt;");
				continue;
			}
			if (c == '>') {
				filtered.append("&gt;");
				continue;
			}
			if (c == '"') {
				filtered.append("&quot;");
				continue;
			}
			if (c == '\'') {
				filtered.append("&acute;");
				continue;
			}
			if (c == '&') {
				filtered.append("&amp;");
				continue;
			}
			if (c == '\n')
				filtered.append("<br/>");
			else
				filtered.append(c);
		}

		return filtered.toString();
	}

	public static long ip2long(String ip) {
		long ipNumber = 0;
		String[] ips = ip.split("[.]");
		for (int i = 0; i < 4; ++i) {
			ipNumber = ipNumber << 8 | Integer.parseInt(ips[i]);
		}
		return ipNumber;
	}

	@SuppressWarnings("unchecked")
	public static boolean isEmptyCollection(Collection c) {
		if (c == null || c.isEmpty()) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static boolean isNotEmptyCollection(Collection c) {
		return !isEmptyCollection(c);
	}

	public static boolean isNullObj(Object obj) {
		return obj == null;
	}

	public static boolean isNotNullObj(Object obj) {
		return obj != null;
	}

	/**
	 * 根据把数组转成字符串
	 * 
	 * @param s
	 * @param regex
	 * @return
	 */
	public static String arrayToString(Object[] array) {
		if (array == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (Object obj : array) {
			sb.append(obj == null ? "" : obj.toString());
			sb.append(",");
		}
		if (sb.indexOf(",") > -1) {
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 根据把数组转成字符串
	 * 
	 * @param s
	 * @param regex
	 * @return
	 */
	public static String arrayToString(long[] array) {
		if (array == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (long obj : array) {
			sb.append(obj);
			sb.append(",");
		}
		if (sb.indexOf(",") > -1) {
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 特殊字符验证 false 包含特殊字符
	 * 
	 * @return
	 */
	public static boolean checkSpecialCharacter(String str) {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);

		Matcher m = p.matcher(str);
		if (m.find()) {
			return false;
		}
		return true;
	}

	/**
	 * 中文字符验证 false 包含中文字符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkChineseCharacter(String str) {
		String regEx = "[\u4e00-\u9fa5]";
		Pattern p = Pattern.compile(regEx);

		Matcher m = p.matcher(str);
		if (m.find()) {
			return false;
		}
		return true;
	}

	/**
	 * 只包含英文验证 false 只包含英文
	 * 
	 * @param bitRate
	 * @return
	 */
	public static boolean checkOnlyEnglishLetters(String str) {
		String regEx = "^[a-zA-Z][a-zA-Z]*$";
		Pattern p = Pattern.compile(regEx);

		Matcher m = p.matcher(str);
		if (m.find()) {
			return false;
		}
		return true;
	}
}
