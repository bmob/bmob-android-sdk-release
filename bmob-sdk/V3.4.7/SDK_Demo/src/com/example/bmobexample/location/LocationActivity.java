package com.example.bmobexample.location;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.FindListener;

import com.example.bmobexample.BaseActivity;
import com.example.bmobexample.R;
import com.example.bmobexample.bean.Person;

/**  
 *   ����λ��  
 * @class  LocationActivity  
 * @author smile   
 * @date   2015-4-13 ����11:31:47  
 *   
 */
public class LocationActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mListview = (ListView) findViewById(R.id.listview);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.tv_item, getResources().getStringArray(
						R.array.bmob_location_list));
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
			queryNear();
			break;
		case 2:
			queryKiloMeters();
			break;
		case 3:
			queryBox();
			break;
		}
	}
	
	/**��ѯ��ӽ�ĳ��������û�
	 * @method queryNear 
	 * @params    
	 * @return void  
	 * @exception   
	 */
	private void queryNear(){
		BmobQuery<Person> query =new BmobQuery<Person>();
		BmobGeoPoint location = new BmobGeoPoint(112.934755,24.52065);
		query.addWhereNear("gpsAdd", location);
		query.findObjects(this, new FindListener<Person>() {

			@Override
			public void onSuccess(List<Person> object) {
				// TODO Auto-generated method stub
				toast("��ѯ�ɹ�����" + object.size() + "�����ݡ�");
			}

			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
				toast("��ѯʧ�ܣ�" + code);
			}
		});
	}
	
	/** ��ѯָ�����뷶Χ�ڵ��û�
	 * @method queryKiloMeters 
	 * @params    
	 * @return void  
	 * @exception   
	 */
	private void queryKiloMeters(){
		BmobQuery<Person> query =new BmobQuery<Person>();
		BmobGeoPoint southwestOfSF = new BmobGeoPoint(112.934755,24.52065);
		//��ѯָ������ָ���뾶�ڵ��û�
		query.addWhereWithinRadians("gpsAdd", southwestOfSF, 10.0);
		//��ѯָ������ָ�����ﷶΧ�ڵ��û�
//		query.addWhereWithinKilometers("gpsAdd", southwestOfSF, 10);
		//��ѯָ������ָ��Ӣ�ﷶΧ�ڵ��û�
		query.addWhereWithinMiles("gpsAdd", southwestOfSF, 10.0);
		query.findObjects(this, new FindListener<Person>() {

			@Override
			public void onSuccess(List<Person> object) {
				// TODO Auto-generated method stub
				toast("��ѯ�ɹ�����" + object.size() + "�����ݡ�");
			}

			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
				toast("��ѯʧ�ܣ�" + code);
			}
		});
	}
	
	/**  ��ѯ���η�Χ�ڵ��û�
	 * @method queryBox 
	 * @params    
	 * @return void  
	 * @exception   
	 */
	private void queryBox(){
		BmobQuery<Person> query =new BmobQuery<Person>();
		BmobGeoPoint southwestOfSF = new BmobGeoPoint(112.934755,24.52065);
		BmobGeoPoint northeastOfSF = new BmobGeoPoint(116.627623, 40.143687);
		query.addWhereWithinGeoBox("gpsAdd", southwestOfSF, northeastOfSF);
		query.findObjects(this, new FindListener<Person>() {

			@Override
			public void onSuccess(List<Person> object) {
				// TODO Auto-generated method stub
				toast("��ѯ�ɹ�����" + object.size() + "�����ݡ�");
			}

			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
				toast("��ѯʧ�ܣ�" + code);
			}
		});
	}
	
}
