package com.example.helloworld;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
public class RequestUtils {
	/**
	 * HttpURLConnection方式的get请求
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String HttpURLConnectionrequestByGet(String path)throws Exception{
		String data=null;
		//将地址转换为URL类型的地址
		URL url=new URL(path);
		//创建一个链接
		HttpURLConnection connection =(HttpURLConnection)url.openConnection();
		//设置链接超时
		connection.setConnectTimeout(5 * 1000);
		//设置请求方式
		connection.setRequestMethod("POST");
		//设置输出
		connection.setDoInput(true);
		//打开链接
		connection.connect();
		//是否链接成功
		if(connection.getResponseCode()==connection.HTTP_OK){
			//获取数据
			data=readStream(connection.getInputStream());
		}
		return data;
	}

	/**
	 * 设置HttpClient参数
	 * @return
	 */
	private static HttpClient createHttpClient(){
		HttpParams httpParams = new BasicHttpParams();
		//设置链接超时
		HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
		//设置请求超时
		HttpConnectionParams.setSoTimeout(httpParams, 15000);
		HttpConnectionParams.setTcpNoDelay(httpParams, true);
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);
		//持续握手
		HttpProtocolParams.setUseExpectContinue(httpParams, true);
		HttpClient mHttpClient=new DefaultHttpClient(httpParams);
		return mHttpClient;
	}
	public static String HttpClientByGet(String url) throws ClientProtocolException, IOException{
		String data=null;
		//创建链接地址
		HttpGet mHttpGet=new HttpGet(url);
		//添加页眉
		mHttpGet.addHeader("Connection", "Keep-Alive");
		//创建链接
		HttpClient httpClient = createHttpClient();
		//执行请求
		HttpResponse httpResponse = httpClient.execute(mHttpGet);
		//通过HttpResponse获取响应数据
		HttpEntity httpEntity = httpResponse.getEntity();
		//获取响应码
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if(null!=httpEntity){
			//获取输入流
			InputStream inputStream = httpEntity.getContent();
			data=readStream(inputStream);
		}
		return data;
	}
	/**
	 * 将输入流转换成字符串
	 * @param inputStream
	 * @return
	 */
	private static String readStream(InputStream inputStream) {
		String data=null;
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		int i;
		try {
			while((i=inputStream.read())!=-1){
				baos.write(i);
			}
			data=baos.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			baos.close();
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
}
