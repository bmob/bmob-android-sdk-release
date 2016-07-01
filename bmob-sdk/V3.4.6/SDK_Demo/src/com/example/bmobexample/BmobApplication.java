package com.example.bmobexample;

import android.app.Application;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

public class BmobApplication extends Application {
	/**
	 * SDK��ʼ��Ҳ���Էŵ�Application��
	 */
	public static String APPID = "";
	
	@Override
	public void onCreate() {
		super.onCreate();
		//����BmobConfig
		BmobConfig config =new BmobConfig.Builder()
		//����ʱʱ�䣨��λΪ�룩��Ĭ��15s
		.setConnectTimeout(30)
		//�ļ���Ƭ�ϴ�ʱÿƬ�Ĵ�С����λ�ֽڣ���Ĭ��512*1024
		.setBlockSize(500*1024)
		.build();
		Bmob.getInstance().initConfig(config);
		//Bmob��ʼ��
		Bmob.initialize(this, APPID);
	}
	
}
