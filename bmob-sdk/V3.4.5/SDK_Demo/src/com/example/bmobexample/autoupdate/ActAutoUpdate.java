package com.example.bmobexample.autoupdate;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobDialogButtonListener;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

import com.example.bmobexample.BaseActivity;
import com.example.bmobexample.R;

public class ActAutoUpdate extends BaseActivity {
	
	String [] arr = {"�Զ�����","�ֶ�����","��Ĭ���ظ���","ɾ���ļ�"};
	
	UpdateResponse ur;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		������Ҫ�����Զ����¹���֮ǰ�Ƚ��г�ʼ���������
//		�˷����ʺϿ����ߵ����Զ����¹���ʱʹ�ã�һ��AppVersion���ں�̨�����ɹ����������λ�ɾ���˷����������������ɶ��м�¼��
		BmobUpdateAgent.initAppVersion(this);
				
		mListview = (ListView) findViewById(R.id.listview);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.tv_item, arr);
		mListview.setAdapter(mAdapter);
		mListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				testAutoUpdate(position + 1);
			}
		});
		//�������·�ʽ����apk��target_size��С��
		Log.i("smile", "Ӧ�õ�target_size�Ĵ�С = "+new File("sdcard/BmobExample.apk").length());
		//�����ڷ�wifi�����¼��Ӧ�ø���
		BmobUpdateAgent.setUpdateOnlyWifi(false);
		//���¼�����
		BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
			
			@Override
			public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
				//V3.4.4�汾��ʼ�����Ӱ汾���´�����ʾ����ͨ���˷�����ȡ��������Ϣ
				BmobException e = updateInfo.getException();
				if(e!=null){
					Toast.makeText(ActAutoUpdate.this, "�����·��أ�"+e.getMessage()+"("+e.getErrorCode()+")", Toast.LENGTH_SHORT).show();
				}else{
					ur = updateInfo;
				}
				//����������V3.4.4֮ǰ�汾
//				if (updateStatus == UpdateStatus.Yes) {
//					ur = updateInfo;
//				}else if(updateStatus == UpdateStatus.No){
//					Toast.makeText(ActAutoUpdate.this, "�汾�޸���", Toast.LENGTH_SHORT).show();
//				}else if(updateStatus==UpdateStatus.EmptyField){//����ʾֻ�����ѿ����߹�ע��Щ��������Գɹ���������û���ʾ
//					Toast.makeText(ActAutoUpdate.this, "������AppVersion��ı����1��target_size���ļ���С���Ƿ���д��2��path����android_url���߱�������һ�", Toast.LENGTH_SHORT).show();
//				}else if(updateStatus==UpdateStatus.IGNORED){
//					Toast.makeText(ActAutoUpdate.this, "�ð汾�ѱ����Ը���", Toast.LENGTH_SHORT).show();
//				}else if(updateStatus==UpdateStatus.ErrorSizeFormat){
//					Toast.makeText(ActAutoUpdate.this, "����target_size��д�ĸ�ʽ����ʹ��file.length()������ȡapk��С��", Toast.LENGTH_SHORT).show();
//				}else if(updateStatus==UpdateStatus.TimeOut){
//					Toast.makeText(ActAutoUpdate.this, "��ѯ������ѯ��ʱ", Toast.LENGTH_SHORT).show();
//				}
			}
		});
		//���öԶԻ���ť�ĵ���¼��ļ���
		BmobUpdateAgent.setDialogListener(new BmobDialogButtonListener() {
			
			@Override
			public void onClick(int status) {
				// TODO Auto-generated method stub
				switch (status) {
		        case UpdateStatus.Update:
		            Toast.makeText(ActAutoUpdate.this, "������������°�ť" , Toast.LENGTH_SHORT).show();
		            break;
		        case UpdateStatus.NotNow:
		        	Toast.makeText(ActAutoUpdate.this, "������Ժ���˵��ť" , Toast.LENGTH_SHORT).show();
		        	break;
		        case UpdateStatus.Close://ֻ����ǿ�Ƹ���״̬�²Ż��ڸ��¶Ի�������Ϸ�����close��ť,����û���������������¡���ť����ʱ�򿪷��߿���Щ����������ֱ���˳�Ӧ�õ�
		            Toast.makeText(ActAutoUpdate.this, "����˶Ի���رհ�ť" , Toast.LENGTH_SHORT).show();
		            break;
		        }
			}
		});
		
	}
	
	private void testAutoUpdate(int pos){
		switch (pos) {
		case 1://�Զ�����
			BmobUpdateAgent.update(this);
			break;
		case 2://�ֶ�����
			BmobUpdateAgent.forceUpdate(this);
			break;
		case 3://��Ĭ����
			BmobUpdateAgent.silentUpdate(this);
			break;
		case 4:
			if(ur != null){
				File file = new File(Environment.getExternalStorageDirectory(), ur.path_md5 + ".apk");
				if (file != null && file.exists()) {
					if (file.delete()) {
						Toast.makeText(ActAutoUpdate.this, "ɾ�����",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(ActAutoUpdate.this, "ɾ��ʧ��",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(ActAutoUpdate.this, "ɾ�����", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(ActAutoUpdate.this, "ɾ��ʧ��", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}
	
}
