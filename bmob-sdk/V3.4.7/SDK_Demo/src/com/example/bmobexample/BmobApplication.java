package com.example.bmobexample;

import android.app.Application;
import cn.bmob.v3.Bmob;

public class BmobApplication extends Application {
	/**
	 * SDK��ʼ��Ҳ���Էŵ�Application��
	 */
	public static String APPID = "";
	
	@Override
	public void onCreate() {
		super.onCreate();
		//�ṩ�������ַ�ʽ���г�ʼ��������
//		//��һ������BmobConfig��������������ʱʱ�䡢�ļ���Ƭ�ϴ�ʱÿƬ�Ĵ�С���ļ��Ĺ���ʱ��(��λΪ��)
//		BmobConfig config =new BmobConfig.Builder(this)
//		//����appkey
//		.setApplicationId(APPID)
//		//����ʱʱ�䣨��λΪ�룩��Ĭ��15s
//		.setConnectTimeout(30)
//		//�ļ���Ƭ�ϴ�ʱÿƬ�Ĵ�С����λ�ֽڣ���Ĭ��512*1024
//		.setUploadBlockSize(1024*1024)
//		//�ļ��Ĺ���ʱ��(��λΪ��)��Ĭ��1800s
//		.setFileExpiration(5500)
//		.build();
//		Bmob.initialize(config);
		//�ڶ���Ĭ�ϳ�ʼ��
		Bmob.initialize(this, APPID);
	}
	
}
