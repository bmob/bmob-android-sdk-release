package com.example.bmobexample.acl;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cn.bmob.v3.BmobACL;
import cn.bmob.v3.BmobRole;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.example.bmobexample.BaseActivity;
import com.example.bmobexample.R;
import com.example.bmobexample.bean.Person;

/**  
 * ACLȨ�޿��ƺͽ�ɫ����
 * @class  ACLActivity  
 * @author smile   
 * @date   2015-4-13 ����11:43:55  
 * ע�����尸���ɲ鿴BmobACLDemo
 */
public class ACLActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mListview = (ListView) findViewById(R.id.listview);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.tv_item, getResources().getStringArray(
						R.array.bmob_acl_list));
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
			// ��������ʱ���ACL
			createACLData();
			break;
		case 2:
			// ������ɫ
			createRole();
			break;
		case 3:
			// ���½�ɫ
			updateRole();
			break;
		case 4:
			// ���½�ɫ(ɾ����ɫ�е��û�)
			removeRole();
			break;
		default:
			break;
		}
	}
	
	/**
	 * ��������ʱ���ACL
	 */
	private void createACLData(){
		Person person = new Person();
		person.setName("ְԱ");
		person.setAddress("������");
		//���ACLȨ�޿���
		BmobACL aCL = new BmobACL();
		aCL.setPublicReadAccess(true);//���������˿ɶ���Ȩ��
		aCL.setPublicWriteAccess(true);//���������˿�д��Ȩ��
		aCL.setWriteAccess(BmobUser.getCurrentUser(this), true);//���õ�ǰ�û���д��Ȩ��
//		aCL.setReadAccess("�û���objectId", false);//ָ���ض��û����ɶ�
//		aCL.setWriteAccess("�û���objectId", true);//ָ���ض��û���д
		BmobRole hr = new BmobRole("hr");
		aCL.setRoleReadAccess(hr, true);//ָ��hr����˿ɶ�
		aCL.setRoleWriteAccess("hr", true);//ָ��hr����˿�д
		person.setACL(aCL);
		person.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("�����ɹ�");
			}
			
			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("����ʧ�ܣ�"+msg);
			}
		});
	}
	
	/**������ɫ
	 * @method createRole 
	 * @params    
	 * @return void  
	 * @exception   
	 */
	private void createRole(){
		BmobRole hr_role = new BmobRole("hr");//Ϊ��ǰ�û������ɫ
		hr_role.getUsers().add(BmobUser.getCurrentUser(this));
		hr_role.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("�����ɹ�");
			}
			
			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("����ʧ�ܣ�"+msg);
			}
		});
	}
	
	/**
	 * ���½�ɫ
	 */
	private void updateRole() {
		BmobRole role = new BmobRole("hr");
		role.setObjectId("6f35f87f3a");
		role.getUsers().add(BmobUser.getCurrentUser(this));
		role.update(this, new UpdateListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("���³ɹ�");
			}
			
			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("����ʧ�ܣ�"+msg);
			}
		});
		
	}
	
	/**ɾ����ɫ
	 * @method removeRole 
	 * @params    
	 * @return void  
	 * @exception   
	 */
	private void removeRole(){
		BmobRole role = new BmobRole("hr");
		role.setObjectId("6f35f87f3a");
		role.getUsers().remove(BmobUser.getCurrentUser(this));
		role.update(this, new UpdateListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("���³ɹ�");
			}
			
			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("����ʧ�ܣ�"+msg);
			}
		});
	}
	
	
}
