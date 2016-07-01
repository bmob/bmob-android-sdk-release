package com.example.bmobexample.crud;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.example.bmobexample.BaseActivity;
import com.example.bmobexample.R;
import com.example.bmobexample.bean.BankCard;
import com.example.bmobexample.bean.Person;

/**��ɾ�Ĳ�
 * @author Administrator
 *
 */
public class CRUDActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListview = (ListView) findViewById(R.id.listview);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.tv_item, getResources().getStringArray(
						R.array.bmob_crud_list));
		mListview.setAdapter(mAdapter);
		mListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				testBmob(position + 1);
			}
		});

	}
	
	private void testBmob(int pos) {
		switch (pos) {
		case 1:
			testinsertObject();
			break;
		case 2:
			testUpdateObjet();
			break;
		case 3:
			testDeleteObject();
			break;
		case 4:
			startActivity(new Intent(this, QueryActivity.class));
			break;
		}
	}
	
	public static String objectId="";
	/**
	 * �������
	 */
	private void testinsertObject() {
		final Person p2 = new Person();
		p2.setName("lucky");
		p2.setAddress("�����к�����");
		p2.setAge(25);
		//���Object����
		p2.setBankCard(new BankCard("����", "111"));
		//���Object���͵�����
		List<BankCard> cards =new ArrayList<BankCard>();
		for(int i=0;i<2;i++){
			cards.add(new BankCard("����", "111"+i));
		}
		p2.addAll("cards", cards);
		//���String���͵�����
		p2.addAll("hobby", Arrays.asList("��Ӿ", "����"));    // һ����Ӷ��ֵ��hobby�ֶ���
//		p2.add("cards",new BankCard("����", "111"));//һ����ӵ���ֵ
		p2.setGpsAdd(new BmobGeoPoint(112.934755, 24.52065));
		p2.setUploadTime(new BmobDate(new Date()));
		p2.save(this, new SaveListener() {

			@Override
			public void onSuccess() {
				objectId = p2.getObjectId();
				toast("�������ݳɹ���" + p2.getObjectId());
				Log.d("bmob", "objectId = " + p2.getObjectId());
				Log.d("bmob", "name =" + p2.getName());
				Log.d("bmob", "age =" + p2.getAge());
				Log.d("bmob", "address =" + p2.getAddress());
				Log.d("bmob", "gender =" + p2.isGender());
				Log.d("bmob", "createAt = " + p2.getCreatedAt());
			}

			@Override
			public void onFailure(int code, String msg) {
				toast("��������ʧ�ܣ�" + msg+"("+code+")");
			}
		});
	}

	/**
	 * ���¶���
	 */
	private void testUpdateObjet() {
		final Person p2 = new Person();
		//���������е�ĳ��λ�õĶ���ֵ
		p2.setValue("cards.0", new BankCard("cards.0", "cards.0��ֵ"));
		//���¶���������ָ�������ָ���ֶε�ֵ
//		p2.setValue("cards.0.bankName", "���п�");
//		p2.setValue("cards.0.cardNumber", "����");
//		p2.setValue("cards.1.bankName", "���п�");
		//����BmobObject��ֵ
//		p2.setValue("author", BmobUser.getCurrentUser(this, MyUser.class));
		//����Object���͵�����
//		List<BankCard> cards =new ArrayList<BankCard>();
//		for(int i=0;i<2;i++){
//			cards.add(new BankCard("�н��"+i, "111"+i));
//		}
//		p2.setValue("cards",cards);
		//����Object����
		p2.setValue("bankCard",new BankCard("bankCard", "bankCard��ֵ"));
		//����Object�����ֵ
//		p2.setValue("bankCard.bankName","����");
		//����Integer����
//		p2.setValue("age",11);
//		p2.setValue("gender", true);
		p2.update(this, objectId, new UpdateListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("���³ɹ���" + p2.getUpdatedAt());
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("����ʧ�ܣ�" + msg);
			}
		});

	}

	/**
	 * ɾ������
	 */
	private void testDeleteObject() {
		Person p2 = new Person();
		p2.removeAll("cards", Arrays.asList(new BankCard("����", "111")));
		p2.setObjectId(objectId);
		p2.update(this, new UpdateListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("ɾ���ɹ�");
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("ɾ��ʧ�ܣ�" + msg);
			}
		});
	}
	
	
}
