package com.wlh.wpd.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Http工具类
 * 
 */
public class HttpTool {
	private static final Logger LOG = LogManager.getLogger(HttpTool.class);

	/**
	 * <发送信息> <功能详细描述>
	 * 
	 * @param url
	 * @param info
	 * @throws Exception
	 * @return String
	 */
	public static String sendXmlInfo(String url, String info) throws Exception {
		// 返回的消息
		StringBuilder builder = new StringBuilder();
		// 构造post
		HttpPost post = new HttpPost(url);
		InputStream inStream = new ByteArrayInputStream(info.getBytes("UTF-8"));
		InputStreamEntity inEntity = new InputStreamEntity(inStream, -1L);
		inEntity.setContentType("text/xml; charset=UTF-8");
		inEntity.setChunked(true);
		post.setEntity(inEntity);
		// 构造client
		CloseableHttpClient client = HttpClients.createDefault();
			HttpResponse response = client.execute(post);
		HttpEntity resEntity = response.getEntity();
		if (resEntity != null) {
			InputStream instream = resEntity.getContent();
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(instream, "UTF-8"));
				String line = reader.readLine();
				while (null != line) {
					builder.append(line.trim());
					line = reader.readLine();
				}
			} catch (IOException e) {
				throw e;
			} catch (RuntimeException e) {
				post.abort();
				throw e;
			} finally {
				instream.close();
				client.close();
			}
		}
		return builder.toString();
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @param params
	 * @param timeout 设置请求超时值，单位秒
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> params, int timeout) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// URL
		HttpPost httpPost = new HttpPost(url);
		// time out
		RequestConfig config = RequestConfig.custom().setSocketTimeout(timeout * 1000).build();
		httpPost.setConfig(config);
		// 参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}
		// 编码
		LOG.info("set utf-8 form entity to httppost");
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		// 发送POST
		LOG.info("execute post...");
		String body = null;
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpPost);
			
			// 处理结果
			LOG.info("get response from http server..");
			if (null != response) {
				HttpEntity entity = response.getEntity();
				body = EntityUtils.toString(entity);
			}
		} catch (ClientProtocolException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally{
			if(response!=null){
				response.close();
			}
			if(httpclient!=null){
				httpclient.close();
			}
		}
		return body;
	}
}
