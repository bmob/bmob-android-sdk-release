package com.example.bmobexample.user;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.EmailVerifyListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.ResetPasswordByCodeListener;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.example.bmobexample.BaseActivity;
import com.example.bmobexample.R;
import com.example.bmobexample.bean.BankCard;
import com.example.bmobexample.bean.MyUser;

/**�û�����
 * @author Administrator
 *
 */
public class UserActivity extends BaseActivity {
	
	EditText et_number,et_code;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_code);
		et_number = (EditText) findViewById(R.id.et_number);
		et_code = (EditText) findViewById(R.id.et_code);
		
		mListview = (ListView) findViewById(R.id.listview);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.tv_item, getResources().getStringArray(R.array.bmob_user_list));
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
			testSignUp();
			break;
		case 2:
			testLogin();
			break;
		case 3:
			testGetCurrentUser();
			break;
		case 4:
			testLogOut();
			break;
		case 5:
			updateUser();
			break;
		case 6:
			checkPassword();
			break;
		case 7:
			testResetPasswrod();
			break;
		case 8:
			emailVerify();
			break;
		case 9:
			testFindBmobUser();
			break;
		case 10://ͨ������������½
			loginByEmailPwd();
			break;
		case 11://ͨ���ֻ�����������½
			loginByPhonePwd();
			break;
		case 12://ͨ���ֻ�����Ͷ�����֤���½
			loginByPhoneCode();
			break;
		case 13://һ��ע���¼
			signOrLogin();
			break;
		case 14://ͨ���ֻ������ֵ����
			resetPasswordBySMS();
			break;
		case 15://���ݾ���������������޸ĵ�ǰ�û�����
			updateCurrentUserPwd();
			break;
		}
	}
	
	/**
	 * ע���û�
	 */
	@SuppressLint("UseValueOf")
	private void testSignUp() {
		final MyUser myUser = new MyUser();
		myUser.setUsername("smile");
		myUser.setPassword("123456");
		myUser.setAge(18);
		myUser.signUp(this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("ע��ɹ�:" + myUser.getUsername() + "-"
						+ myUser.getObjectId() + "-" + myUser.getCreatedAt()
						+ "-" + myUser.getSessionToken()+",�Ƿ���֤��"+myUser.getEmailVerified());
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("ע��ʧ��:" + msg);
			}
		});
	}

	/**
	 * ��½�û�
	 */
	private void testLogin() {
		final BmobUser bu2 = new BmobUser();
		bu2.setUsername("smile");
		bu2.setPassword("123456");
		bu2.login(this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast(bu2.getUsername() + "��½�ɹ�");
				testGetCurrentUser();
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("��½ʧ��:" + code+","+msg);
			}
		});
	}

	/**
	 * ��ȡ�����û�
	 */
	private void testGetCurrentUser() {
//		MyUser myUser = BmobUser.getCurrentUser(this, MyUser.class);
//		if (myUser != null) {
//			Log.i("life","�����û���Ϣ:objectId = " + myUser.getObjectId() + ",name = " + myUser.getUsername()
//					+ ",age = "+ myUser.getAge());
//		} else {
//			toast("�����û�Ϊnull,���¼��");
//		}
		//V3.4.5�汾������getObjectByKey������ȡ�����û�������ĳһ�е�ֵ
		String username = (String) BmobUser.getObjectByKey(this, "username");
		Integer age = (Integer) BmobUser.getObjectByKey(this, "age");
		Boolean sex = (Boolean) BmobUser.getObjectByKey(this, "sex");
		JSONArray hobby= (JSONArray) BmobUser.getObjectByKey(this, "hobby");
		JSONArray cards= (JSONArray) BmobUser.getObjectByKey(this, "cards");
		JSONObject banker= (JSONObject) BmobUser.getObjectByKey(this, "banker");
		JSONObject mainCard= (JSONObject) BmobUser.getObjectByKey(this, "mainCard");
		Log.i("bmob", "username��"+username+",\nage��"+age+",\nsex��"+ sex);
		Log.i("bmob", "hobby:"+(hobby!=null?hobby.toString():"Ϊnull")+"\ncards:"+(cards!=null ?cards.toString():"Ϊnull"));
		Log.i("bmob", "banker:"+(banker!=null?banker.toString():"Ϊnull")+"\nmainCard:"+(mainCard!=null ?mainCard.toString():"Ϊnull"));
	}

	/**
	 * ��������û�
	 */
	private void testLogOut() {
		BmobUser.logOut(this);
	}

	/**
	 * �����û�������ͬ�����±��ص��û���Ϣ
	 */
	private void updateUser() {
		final MyUser bmobUser = BmobUser.getCurrentUser(this, MyUser.class);
		if (bmobUser != null) {
			MyUser newUser = new MyUser();
			//-----------------------��ͨsetter����-------------------------------
			//number����
//			newUser.setAge(25);
			newUser.setSex(true);
			//object����
			newUser.setMainCard(new BankCard("����", "10086"));
			//BmobObject����
//			Person person =new Person();
//			person.setObjectId("6abd1c6dc5");
//			newUser.setBanker(person);
			//---------------------�������(add��addAll��addUnique��addAllUnique)---------------------------------------
			//���Object���͵�����,Object�������addAllUnique��addUnique�����󱾵��û���Ϣδ֧��ȥ��
			List<BankCard> cards =new ArrayList<BankCard>();
			cards.add(new BankCard("����", "111"));
			newUser.addAll("cards", cards);
			//���String���͵�����--String����֧��ȥ��
//			newUser.addAllUnique("hobby", Arrays.asList("��Ӿ"));
			//----------------------��������---------------------------------------
//			newUser.increment("num",-2);
//			//----------------------setValue��ʽ�����û���Ϣ�������ȱ�֤���µ��д��ڣ�����ᱨinternal error��----------------------------
//			//����number
//			newUser.setValue("age",25);
//			//��������Object
//			newUser.setValue("banker",person);
//			//����String����
//			newUser.setValue("hobby",Arrays.asList("����","��Ӿ"));
////			//����ĳ��Object��ֵ
//			newUser.setValue("mainCard.cardNumber","10011");
//			//����������ĳ��Object
//			newUser.setValue("cards.0", new BankCard("����", "10086"));
			//����������ĳ��Object��ĳ���ֶε�ֵ
//			newUser.setValue("cards.0.bankName", "����");
			newUser.update(this,bmobUser.getObjectId(),new UpdateListener() {

				@Override
				public void onSuccess() {
					//���ص��û���Ϣ���Ѹ��³ɹ������ڴ˵���getCurrentUser��������ȡ���µ��û���Ϣ
					testGetCurrentUser();
				}

				@Override
				public void onFailure(int code, String msg) {
					toast("�����û���Ϣʧ��:" + msg);
				}
			});
		} else {
			toast("�����û�Ϊnull,���¼��");
		}
	}

	/**
	 * ��֤�������Ƿ���ȷ
	 * @param
	 * @return void
	 */
	private void checkPassword() {
		BmobQuery<MyUser> query = new BmobQuery<MyUser>();
		final MyUser bmobUser = BmobUser.getCurrentUser(this, MyUser.class);
		// ����㴫����������ȷ�ģ���ôarg0.size()�Ĵ�С��1������ʹ���������ľ���������ȷ�ģ�������ʧ�ܵ�
		query.addWhereEqualTo("password", "123456");
		query.addWhereEqualTo("username", bmobUser.getUsername());
		query.findObjects(this, new FindListener<MyUser>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(List<MyUser> arg0) {
				// TODO Auto-generated method stub
				toast("��ѯ����ɹ�:" + arg0.size());
			}
		});
	}

	/**
	 * ��������
	 */
	private void testResetPasswrod() {
		final String email = "123456789@qq.com";
		BmobUser.resetPasswordByEmail(this, email, new ResetPasswordByEmailListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("������������ɹ����뵽" + email + "��������������ò���");
			}

			@Override
			public void onFailure(int code, String e) {
				// TODO Auto-generated method stub
				toast("��������ʧ��:" + e);
			}
		});
	}

	/**
	 * ��ѯ�û�
	 */
	private void testFindBmobUser() {
		BmobQuery<MyUser> query = new BmobQuery<MyUser>();
		query.addWhereEqualTo("username", "lucky");
		query.findObjects(this, new FindListener<MyUser>() {

			@Override
			public void onSuccess(List<MyUser> object) {
				// TODO Auto-generated method stub
				toast("��ѯ�û��ɹ���" + object.size());

			}

			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
				toast("��ѯ�û�ʧ�ܣ�" + msg);
			}
		});
	}

	/**
	 * ��֤�ʼ�
	 */
	private void emailVerify() {
		final String email = "75727433@qq.com";
		BmobUser.requestEmailVerify(this, email, new EmailVerifyListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("������֤�ʼ��ɹ����뵽" + email + "�����н��м����˻���");
			}

			@Override
			public void onFailure(int code, String e) {
				// TODO Auto-generated method stub
				toast("������֤�ʼ�ʧ��:" + e);
			}
		});
	}
	
	private void loginByEmailPwd(){
		BmobUser.loginByAccount(this, "123456@163.com", "123456", new LogInListener<MyUser>() {
			
			@Override
			public void done(MyUser user, BmobException e) {
				// TODO Auto-generated method stub
				if(user!=null){
					Log.i("smile", ""+user.getUsername()+"-"+user.getAge()+"-"+user.getObjectId()+"-"+user.getEmail());
				}
			}
		});
	}
	
	private void loginByPhonePwd(){
		String number = et_number.getText().toString();
		BmobUser.loginByAccount(this, number, "123456", new LogInListener<MyUser>() {
			
			@Override
			public void done(MyUser user, BmobException e) {
				// TODO Auto-generated method stub
				if(user!=null){
					toast("��¼�ɹ�");
					Log.i("smile", ""+user.getUsername()+"-"+user.getAge()+"-"+user.getObjectId()+"-"+user.getEmail());
				}else{
					toast("�����룺"+e.getErrorCode()+",����ԭ��"+e.getLocalizedMessage());
				}
			}
		});
	}
	
	private void loginByPhoneCode(){
		//1������������֤��ӿ�
//		BmobSMS.requestSMSCode(this, "�ֻ�����", "ģ������",new RequestSMSCodeListener() {
//			
//			@Override
//			public void done(Integer smsId,BmobException ex) {
//				// TODO Auto-generated method stub
//				if(ex==null){//��֤�뷢�ͳɹ�
//					Log.i("smile", "����id��"+smsId);
//				}
//			}
//		});
		String number = et_number.getText().toString();
		String code = et_code.getText().toString();
		//2��ʹ����֤����е�½
		BmobUser.loginBySMSCode(this, number, code, new LogInListener<MyUser>() {
			
			@Override
			public void done(MyUser user, BmobException e) {
				// TODO Auto-generated method stub
				if(user!=null){
					toast("��¼�ɹ�");
					Log.i("smile", ""+user.getUsername()+"-"+user.getAge()+"-"+user.getObjectId()+"-"+user.getEmail());
				}else{
					toast("�����룺"+e.getErrorCode()+",����ԭ��"+e.getLocalizedMessage());
				}
			}
		});
	}
	/** һ��ע���¼ 
	 * @method signOrLogin    
	 * @return void  
	 * @exception   
	 */
	private void signOrLogin(){
		//1������������֤��ӿ�
//		BmobSMS.requestSMSCode(this, "18312662735", "ģ������",new RequestSMSCodeListener() {
//			
//			@Override
//			public void done(Integer smsId,BmobException ex) {
//				// TODO Auto-generated method stub
//				if(ex==null){//��֤�뷢�ͳɹ�
//					Log.i("smile", "����id��"+smsId);
//				}
//			}
//
//		});
		String number = et_number.getText().toString();
		String code = et_code.getText().toString();
		//2��ʹ���ֻ��źͶ�����֤�����һ��ע���¼,�ⲽ�����ַ�ʽ����ѡ��
//		//��һ�֣�
//		BmobUser.signOrLoginByMobilePhone(this, number, code,new LogInListener<MyUser>() {
//			
//			@Override
//			public void done(MyUser user, BmobException e) {
//				// TODO Auto-generated method stub
//				if(user!=null){
//					toast("��¼�ɹ�");
//					Log.i("smile", ""+user.getUsername()+"-"+user.getAge()+"-"+user.getObjectId()+"-"+user.getEmail());
//				}else{
//					toast("�����룺"+e.getErrorCode()+",����ԭ��"+e.getLocalizedMessage());
//				}
//			}
//		});
		//�ڶ��֣����ַ�ʽ�Ƚ���������ע����¼��ͬʱ���ñ������ֶ�ֵ
		final MyUser user = new MyUser();
		user.setPassword("123456");
		user.setMobilePhoneNumber(number);
		user.signOrLogin(this, code, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("��¼�ɹ�");
				Log.i("smile", ""+BmobUser.getCurrentUser(UserActivity.this,MyUser.class).getUsername()+"-"+user.getAge()+"-"+user.getObjectId()+"-"+user.getEmail());
			}
			
			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("�����룺"+code+",����ԭ��"+msg);
			}
		});
	}
	
	/** ͨ��������֤���������û����� 
	 * @method requestSmsCode    
	 * @return void  
	 * ע�������������ȵ���������֤��Ľӿڻ�ȡ������֤�룬�����ö�����֤����������ӿ������ø��ֻ��Ŷ�Ӧ���û�������
	 */
	private void resetPasswordBySMS(){
		//1�����������֤��
//		BmobSMS.requestSMSCode(this, "�ֻ�����", "ģ������",new RequestSMSCodeListener() {
//		
//			@Override
//			public void done(String smsId,BmobException ex) {
//				// TODO Auto-generated method stub
//				if(ex==null){//��֤�뷢�ͳɹ�
//					Log.i("smile", "����id��"+smsId);
//				}
//			}
//		});
		String code = et_code.getText().toString();
		//2�����õ��ǰ��˸��ֻ��ŵ��˻�������
		BmobUser.resetPasswordBySMSCode(this, code,"1234567", new ResetPasswordByCodeListener() {
			
			@Override
			public void done(BmobException e) {
				// TODO Auto-generated method stub
				if(e==null){
					toast("�������óɹ�");
				}else{
					toast("�����룺"+e.getErrorCode()+",����ԭ��"+e.getLocalizedMessage());
				}
			}
		});
	}
	
	/**�޸ĵ�ǰ�û����� 
	 * @return void  
	 * @exception   
	 */
	private void updateCurrentUserPwd(){
		BmobUser.updateCurrentUserPassword(this, "������", "������", new UpdateListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("�����޸ĳɹ�����������������е�¼");
			}
			
			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("�����޸�ʧ�ܣ�"+msg+"("+code+")");
			}
		});
	}
	
}
