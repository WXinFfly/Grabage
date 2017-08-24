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
	 * HttpURLConnection��ʽ��get����
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String HttpURLConnectionrequestByGet(String path)throws Exception{
		String data=null;
		//����ַת��ΪURL���͵ĵ�ַ
		URL url=new URL(path);
		//����һ������
		HttpURLConnection connection =(HttpURLConnection)url.openConnection();
		//�������ӳ�ʱ
		connection.setConnectTimeout(5 * 1000);
		//��������ʽ
		connection.setRequestMethod("POST");
		//�������
		connection.setDoInput(true);
		//������
		connection.connect();
		//�Ƿ����ӳɹ�
		if(connection.getResponseCode()==connection.HTTP_OK){
			//��ȡ����
			data=readStream(connection.getInputStream());
		}
		return data;
	}

	/**
	 * ����HttpClient����
	 * @return
	 */
	private static HttpClient createHttpClient(){
		HttpParams httpParams = new BasicHttpParams();
		//�������ӳ�ʱ
		HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
		//��������ʱ
		HttpConnectionParams.setSoTimeout(httpParams, 15000);
		HttpConnectionParams.setTcpNoDelay(httpParams, true);
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);
		//��������
		HttpProtocolParams.setUseExpectContinue(httpParams, true);
		HttpClient mHttpClient=new DefaultHttpClient(httpParams);
		return mHttpClient;
	}
	public static String HttpClientByGet(String url) throws ClientProtocolException, IOException{
		String data=null;
		//�������ӵ�ַ
		HttpGet mHttpGet=new HttpGet(url);
		//���ҳü
		mHttpGet.addHeader("Connection", "Keep-Alive");
		//��������
		HttpClient httpClient = createHttpClient();
		//ִ������
		HttpResponse httpResponse = httpClient.execute(mHttpGet);
		//ͨ��HttpResponse��ȡ��Ӧ����
		HttpEntity httpEntity = httpResponse.getEntity();
		//��ȡ��Ӧ��
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if(null!=httpEntity){
			//��ȡ������
			InputStream inputStream = httpEntity.getContent();
			data=readStream(inputStream);
		}
		return data;
	}
	/**
	 * ��������ת�����ַ���
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
