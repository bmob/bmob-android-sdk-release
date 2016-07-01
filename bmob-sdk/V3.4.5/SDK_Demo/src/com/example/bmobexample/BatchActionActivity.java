package com.example.bmobexample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.example.bmobexample.bean.BankCard;
import com.example.bmobexample.bean.MyUser;
import com.example.bmobexample.bean.Person;

/**
  * @ClassName: BatchActionActivity
  * @Description: ��������
  * @author smile
  * @date 2014-12-9 ����11:59:55
  */
public class BatchActionActivity extends BaseActivity {
	
	ListView mListview;
	BaseAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mListview = (ListView) findViewById(R.id.listview);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.tv_item, getResources().getStringArray(
						R.array.batch_action_list));
		mListview.setAdapter(mAdapter);
		mListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				testBatch(position + 1);
			}
		});
	}
	
	private void testBatch(int pos){
		switch (pos) {
			case 1:
				// �������
				batchInsert();
				break;
			case 2:
				// ��������
				batchUpdate();
				break;
			case 3:
				// ����ɾ��
				batchDelete();
				break;
			default:
				break;
		}
	}
	
	/**
	 * �������
	 */
	private void batchInsert(){
		List<BmobObject> persons = new ArrayList<BmobObject>();
		for (int i = 0; i < 3; i++) {
			Person person = new Person();
			person.setName("���� "+i);
			person.setAddress("�Ϻ�����·"+i+"��");
			person.setGpsAdd(new BmobGeoPoint(112.934755, 24.52065));
			person.setUploadTime(new BmobDate(new Date()));
			List<String> hobbys = new ArrayList<String>();
			hobbys.add("�Ķ�");
			hobbys.add("����");
			hobbys.add("����");
			person.setHobby(hobbys);
			person.setBankCard(new BankCard("�й�����", "176672673687545097"+i));
			//��������Ӵ�Poniter���͵����ݣ�����
			person.setAuthor(BmobUser.getCurrentUser(this, MyUser.class));
			persons.add(person);
		}
		
		new BmobObject().insertBatch(this, persons, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("������ӳɹ�");
			}
			
			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("�������ʧ��:"+msg);
			}
		});
	}
	
	/**
	 * ��������
	 */
	private void batchUpdate(){
		List<BmobObject> persons = new ArrayList<BmobObject>();
		Person p1 = new Person();
		p1.setObjectId("71ff16bdf9");
		p1.setAge(105);
		Person p2 = new Person();
		p2.setObjectId("599d76109b");
		p2.setAge(106);
		p2.setGender(false);
		Person p3 = new Person();
		p3.setObjectId("97cefecc04");
		p3.setAge(107);
		
		persons.add(p1);
		persons.add(p2);
		persons.add(p3);
		
		new BmobObject().updateBatch(this, persons, new UpdateListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("�������³ɹ�");
			}
			
			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("��������ʧ��:"+msg);
			}
		});
	}
	
	/**
	 * ����ɾ��
	 */
	private void batchDelete(){
		List<BmobObject> persons = new ArrayList<BmobObject>();
		Person p1 = new Person();
		p1.setObjectId("766c9556cf");
		Person p2 = new Person();
		p2.setObjectId("3cfc36d32a");
		Person p3 = new Person();
		p3.setObjectId("e003224592");
		
		persons.add(p1);
		persons.add(p2);
		persons.add(p3);
		
		new BmobObject().deleteBatch(this, persons, new DeleteListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("����ɾ���ɹ�");
			}
			
			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("����ɾ��ʧ��:"+msg);
			}
		});
	}
	
}
