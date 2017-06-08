package com.qhyc.fund.uitl;


import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

public class HttpUtils {
	
	private static HttpClient client;
	
	private static HttpClient sslClient;
	
	static{
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();		
		 
		cm.setMaxTotal(500);
		cm.setDefaultMaxPerRoute(500);
		
		client = HttpClients.custom().setConnectionManager(cm).build();
	}
	
	static{
		

		TrustStrategy trust = new TrustStrategy() {
			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				// TODO Auto-generated method stub
				return true;
			}
		};
		
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, trust).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
			        .<ConnectionSocketFactory> create().register("https", sslsf)
			        .build();
			PoolingHttpClientConnectionManager sslcm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			sslcm.setMaxTotal(500);
			sslcm.setDefaultMaxPerRoute(500);
			sslClient = HttpClients.custom().setConnectionManager(sslcm).build();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static HttpClient getHttpClient(String url){
		if(url.startsWith("https:")){
			return sslClient;
		}else{
			return client;
		}
	}
	
	private static HttpClient getHttpClient(String url,int connTimeout,int soTimeout){
		if(url.startsWith("https:")){
			return sslClient;
		}else{
			return client;
		}
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String getUrl(String url) throws Exception{
		return getUrl(url,null);
	}
	
	/**
	 * 
	 * @param url
	 * @param header
	 * @return
	 * @throws Exception
	 */
	public static String getUrl(String url,Map<String,String> header) throws Exception{
		HttpGet get = new HttpGet(url);
		if(header!=null){
			Set<String> keySet = header.keySet();
			for(String key:keySet){
				get.setHeader(key, header.get(key));
			}
		}
		
		HttpResponse res = getHttpClient(url).execute(get);
		String result = IOUtils.toString(res.getEntity().getContent());
		get.releaseConnection();
		return result;
	}
	
	
	/**
	 * 
	 * @param url
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static String postUrl(String url,String body) throws Exception{
		return postUrl(url,body,null);
	}
	
	/**
	 * 
	 * @param url
	 * @param body
	 * @param header
	 * @return
	 * @throws Exception
	 */
	public static String postUrl(String url,String body,Map<String,String> header) throws Exception{
		HttpPost post = new HttpPost(url);
		if(header!=null){
			Set<String> keySet = header.keySet();
			for(String key:keySet){
				post.setHeader(key, header.get(key));
			}
		}
		StringEntity stringEntity = new StringEntity(body,"UTF-8"); 
		post.setEntity(stringEntity);
		HttpResponse res = getHttpClient(url).execute(post);
		String result = IOUtils.toString(res.getEntity().getContent());
		post.releaseConnection();
		return result;
	}
	
	/**
	 * 
	 * @param url
	 * @param body
	 * @param header
	 * @return
	 * @throws Exception
	 */
	public static String postUrl(String url,String body,Map<String,String> header,int connTimeout,int soTimeout) throws Exception{
		HttpPost post = new HttpPost(url);
		if(header!=null){
			Set<String> keySet = header.keySet();
			for(String key:keySet){
				post.setHeader(key, header.get(key));
			}
		}
		StringEntity stringEntity = new StringEntity(body,"UTF-8"); 
		post.setEntity(stringEntity);
		RequestConfig config = RequestConfig.custom().setSocketTimeout(soTimeout).setConnectTimeout(connTimeout).build();
		post.setConfig(config);
		HttpResponse res = getHttpClient(url).execute(post);
		String result = IOUtils.toString(res.getEntity().getContent());
		post.releaseConnection();
		return result;
	}
	
	/**
	 * 
	 * @param url
	 * @param params
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String postUrl(String url,Map<String,String> params,String charset) throws Exception{
		return postUrl(url,params,charset,null);
	}
	
	public static String postUrl(String url,Map<String,String> params,String charset,int connTimeout,int soTimeout) throws Exception{
		return postUrl(url,params,charset,null,connTimeout,soTimeout);
	}
	
	/**
	 * 
	 * @param url
	 * @param params
	 * @param charset
	 * @param header
	 * @return
	 * @throws Exception
	 */
	public static String postUrl(String url,Map<String,String> params,String charset,Map<String,String> header,int connTimeout,int soTimeout) throws Exception{
		HttpPost post = new HttpPost(url);
		if(header!=null){
			Set<String> keySet = header.keySet();
			for(String key:keySet){
				post.setHeader(key, header.get(key));
			}
		}
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Set<String> keySet = params.keySet();
		for(String key:keySet){
			nvps.add(new BasicNameValuePair(key,params.get(key)));
		}
		post.setEntity(new UrlEncodedFormEntity(nvps,charset));
		RequestConfig config = RequestConfig.custom().setSocketTimeout(soTimeout).setConnectTimeout(connTimeout).build();
		post.setConfig(config);
		HttpResponse res = getHttpClient(url).execute(post);
		String result = IOUtils.toString(res.getEntity().getContent(),charset);
		post.releaseConnection();
		return result;
	}
	
	/**
	 * 
	 * @param url
	 * @param params
	 * @param charset
	 * @param header
	 * @return
	 * @throws Exception
	 */
	public static String postUrl(String url,Map<String,String> params,String charset,Map<String,String> header) throws Exception{
		HttpPost post = new HttpPost(url);
		if(header!=null){
			Set<String> keySet = header.keySet();
			for(String key:keySet){
				post.setHeader(key, header.get(key));
			}
		}
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Set<String> keySet = params.keySet();
		for(String key:keySet){
			nvps.add(new BasicNameValuePair(key,params.get(key)));
		}
		post.setEntity(new UrlEncodedFormEntity(nvps,charset));
		HttpResponse res = getHttpClient(url).execute(post);
		String result = IOUtils.toString(res.getEntity().getContent(),charset);
		post.releaseConnection();
		return result;
	}
	
	public static String postUrl(String url,Map<String,String> params) throws Exception{
		return postUrl(url,params,"UTF-8",null);
	}
	
	/**
	 * 
	 * @param url
	 * @param params
	 * @param header
	 * @return
	 * @throws Exception
	 */
	public static String postUrl(String url,Map<String,String> params,Map<String,String> header) throws Exception{
		HttpPost post = new HttpPost(url);
		if(header!=null){
			Set<String> keySet = header.keySet();
			for(String key:keySet){
				post.setHeader(key, header.get(key));
			}
		}
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Set<String> keySet = params.keySet();
		for(String key:keySet){
			nvps.add(new BasicNameValuePair(key,params.get(key)));
		}
		post.setEntity(new UrlEncodedFormEntity(nvps));
		HttpResponse res = getHttpClient(url).execute(post);
		String result = IOUtils.toString(res.getEntity().getContent());
		post.abort();
		post.releaseConnection();
		return result;
	}
	
	/**
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String postUrl(String url,List<NameValuePair> params) throws Exception{
		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(params));
		HttpResponse res = getHttpClient(url).execute(post);
		String result = IOUtils.toString(res.getEntity().getContent());
		post.releaseConnection();
		return result;
	}
	
	/**
	 * 
	 * @param url
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String putUrl(String url,String charset) throws Exception{
		HttpPut put = new HttpPut(url);
		HttpResponse res = getHttpClient(url).execute(put);
		String result = IOUtils.toString(res.getEntity().getContent(),charset);
		put.releaseConnection();
		return result;
	}
	
	public static void main(String a[]){
		
	}
	
}
