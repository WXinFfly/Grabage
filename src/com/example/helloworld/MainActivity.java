package com.example.helloworld;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button btn;
	private TextView tv_text;

	public Handler hd=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				String data = msg.obj.toString();
				tv_text.setText(data);
				break;

			default:
				break;
			}
		};
		};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn = (Button) findViewById(R.id.bt_qingqiu);
		tv_text = (TextView) findViewById(R.id.tv_text);
		btn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
						new Thread(new Runnable() {
							
							public void run() {
								try {
									String data = RequestUtils.HttpClientByGet("http://www.baidu.com/");
									Message msg=new Message();
									if(data==null){
										data="«Î«Û ß∞‹";
									}
									msg.obj=data;
									msg.what=1;
									hd.sendMessage(msg);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}).start();
					
				}
		});
		
	}

}
