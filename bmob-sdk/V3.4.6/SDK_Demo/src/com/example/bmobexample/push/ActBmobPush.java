package com.example.bmobexample.push;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cn.bmob.push.BmobPush;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.example.bmobexample.BaseActivity;
import com.example.bmobexample.R;

public class ActBmobPush extends BaseActivity {

	BmobPushManager<BmobInstallation> bmobPush;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_push);
		//����debug����󣬿�֪��push�����Ƿ���������������
		BmobPush.setDebugMode(true);
		//�������ͷ���
		BmobPush.startWork(this);	
		bmobPush = new BmobPushManager<BmobInstallation>(this);
		
		mListview = (ListView) findViewById(R.id.listview);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.tv_item, getResources().getStringArray(
						R.array.bmob_push_list));
		mListview.setAdapter(mAdapter);
		mListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				testBmob(position + 1);
			}
		});
		
		BmobInstallation.getCurrentInstallation(this).save();
		
	}
	
	/**�����Զ����BmobInstallation�ֶ�  
	 * @method updateBmobInstallation    
	 * @return void  
	 * @exception   
	 */
	public void updateBmobInstallation(){
		BmobQuery<MyBmobInstallation> query = new BmobQuery<MyBmobInstallation>();
		query.addWhereEqualTo("installationId", BmobInstallation.getInstallationId(this));
		query.findObjects(this, new FindListener<MyBmobInstallation>() {
			
			@Override
			public void onSuccess(List<MyBmobInstallation> object) {
				// TODO Auto-generated method stub
				if(object.size() > 0){
					MyBmobInstallation mbi = object.get(0);
					mbi.setUid("uid");
					mbi.update(ActBmobPush.this,new UpdateListener() {
						
						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							Log.i("bmob", "���³ɹ�");
						}
						
						@Override
						public void onFailure(int code, String msg) {
							// TODO Auto-generated method stub
							Log.i("bmob", "����ʧ�ܣ�"+msg);
						}
					});
				}else{
				}
			}
			
			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	private void testBmob(int pos) {
		switch (pos) {
			case 1:
				BmobInstallation installation = BmobInstallation.getCurrentInstallation(this);
				installation.subscribe("aaa");
				installation.subscribe("bbb");
				installation.save();
				break;
			case 2:
				BmobInstallation installation2 = BmobInstallation.getCurrentInstallation(this);
				installation2.unsubscribe("bbb");
				installation2.save();
				break;
			case 3:
				// �������ն�����
				pushMessage("���Ǹ������ն����͵�һ����Ϣ");
				break;
			case 4:
				// ��ĳ��Android�ն�����
				pushAndroidMessage("���Ǹ�ָ��Android�ն����͵�һ����Ϣ", "E54053E1D3A74C86B61809AA8D1AEF46");
				break;
			case 5:
				// ��ĳ��IOS�ն�����
				pushIOSMessage("���Ǹ�ָ��IOS�ն����͵�һ����Ϣ", "e2d4869619f61e0266561ce956e5d3cda153fef844242c6bf3f2c52d48fe98d4");
				break;
			case 6:
				// ��ĳĳ��������
				pushChannelMessage("���Ǹ�ָ���������͵�һ����Ϣ", "aaa");
				break;
			case 7:
				// ������Ծ�û�������Ϣ
				pushToInactive("������Ծ�û����͵���Ϣ");
				break;
			case 8:
				pushToAndroid("��Androidƽ̨���͵���Ϣ");
				break;
			case 9:
				pushToIOS("��IOSƽ̨���͵���Ϣ");
				break;
			case 10:
				pushToGeoPoint("���ݵ�����Ϣλ�����͵���Ϣ");
				break;
		}
	}
	
	/**
	 * ��������������Ϣ
	 */
	private void pushMessage(String message){
//		bmobPush.pushMessage(message);
		bmobPush.pushMessageAll(message);
	}
	
	/**
	 * ��ָ��Android�û�������Ϣ
	 * @param message
	 * @param installId
	 */
	private void pushAndroidMessage(String message, String installId){
//		bmobPush.pushMessage(message, installId);
		BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
		query.addWhereEqualTo("installationId", installId);
		bmobPush.setQuery(query);
		bmobPush.pushMessage(message);
	}
	
	/**
	 * ��ָ��IOS�û�����
	 * @param message
	 * @param deviceToken
	 */
	private void pushIOSMessage(String message, String deviceToken){
		BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
		query.addWhereEqualTo("deviceToken", deviceToken);
		bmobPush.setQuery(query);
		bmobPush.pushMessage(message);
	}
	
	/**
	 * ��ָ������������Ϣ
	 * @param message
	 * @param channel
	 */
	private void pushChannelMessage(String message, String channel){
		BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
		List<String> channels = new ArrayList<String>();
		channels.add(channel);
		query.addWhereContainedIn("channels", channels);
		bmobPush.setQuery(query);
		bmobPush.pushMessage(message);
	}
	
	/**
	 * ������Ծ�û�������Ϣ
	 * @param message 
	 */
	private void pushToInactive(String message){
		BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
		query.addWhereLessThan("updatedAt", new BmobDate(new Date()));
		bmobPush.setQuery(query);
		bmobPush.pushMessage(message);
	}
	
	/**
	 * ��androidƽ̨�ն�����
	 * @param message
	 */
	private void pushToAndroid(String message){
		BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
		query.addWhereEqualTo("deviceType", "android");
		bmobPush.setQuery(query);
		bmobPush.pushMessage(message);
	}
	
	/**
	 * ��iosƽ̨�ն�����
	 * @param message
	 */
	private void pushToIOS(String message){
		BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
		query.addWhereEqualTo("deviceType", "ios");
		bmobPush.setQuery(query);
		bmobPush.pushMessage(message);
	}
	
	/**
	 * ���ݵ�����Ϣλ��������
	 * @param message
	 */
	private void pushToGeoPoint(String message){
		BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
		query.addWhereWithinRadians("location", new BmobGeoPoint(112.934755, 24.52065), 1.0);
		bmobPush.setQuery(query);
		bmobPush.pushMessage(message);
	}
	
}
