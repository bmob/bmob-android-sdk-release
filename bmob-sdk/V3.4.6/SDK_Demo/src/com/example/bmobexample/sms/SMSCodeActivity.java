package com.example.bmobexample.sms;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.datatype.BmobSmsState;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QuerySMSStateListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

import com.example.bmobexample.BaseActivity;
import com.example.bmobexample.R;

/**  
 * ������֤��
 * @class  SMSCodeActivity  
 * @author smile   
 * @date   2015-6-4 ����9:48:50  
 *   
 */
@SuppressLint("SimpleDateFormat")
public class SMSCodeActivity extends BaseActivity {
	
	EditText et_number,et_code;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_code);
		et_number = (EditText) findViewById(R.id.et_number);
		et_code = (EditText) findViewById(R.id.et_code);
		
		mListview = (ListView) findViewById(R.id.listview);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item,R.id.tv_item, getResources().getStringArray(R.array.bmob_code_list));
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
			requestSmsCode();
			break;
		case 2:
			verifySmsCode();
			break;
		case 3:
			querySmsState();
			break;
		case 4:
			requestSms();
			break;
		}
	}
	
	
	/** �Զ��巢�Ͷ�������
	 * @method requestSmsCode    
	 * @return void  
	 * @exception   
	 */
	private void requestSms(){
		String number = et_number.getText().toString();
		if(!TextUtils.isEmpty(number)){
			SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sendTime = format.format(new Date());
			BmobSMS.requestSMS(this, number, "������֤��Ϊ123456���뼰ʱ��֤��",sendTime,new RequestSMSCodeListener() {
				
				@Override
				public void done(Integer smsId,BmobException ex) {
					// TODO Auto-generated method stub
					if(ex==null){//��֤�뷢�ͳɹ�
						toast("��֤�뷢�ͳɹ�������id��"+smsId);//���ڲ�ѯ���ζ��ŷ�������
					}else{
						toast("errorCode = "+ex.getErrorCode()+",errorMsg = "+ex.getLocalizedMessage());
					}
				}
			});
		}else{
			toast("�������ֻ�����");
		}
	}
	
	/** ���������֤�� 
	 * @method requestSmsCode    
	 * @return void  
	 * @exception   
	 */
	private void requestSmsCode(){
		String number = et_number.getText().toString();
		if(!TextUtils.isEmpty(number)){
			BmobSMS.requestSMSCode(this, number, "ע��ģ��",new RequestSMSCodeListener() {
				
				@Override
				public void done(Integer smsId,BmobException ex) {
					// TODO Auto-generated method stub
					if(ex==null){//��֤�뷢�ͳɹ�
						toast("��֤�뷢�ͳɹ�������id��"+smsId);//���ڲ�ѯ���ζ��ŷ�������
					}else{
						toast("errorCode = "+ex.getErrorCode()+",errorMsg = "+ex.getLocalizedMessage());
					}
				}
			});
		}else{
			toast("�������ֻ�����");
		}
	}
	
	/** ��֤������֤�� 
	 * @method requestSmsCode    
	 * @return void  
	 * @exception   
	 */
	private void verifySmsCode(){
		String number = et_number.getText().toString();
		String code = et_code.getText().toString();
		if(!TextUtils.isEmpty(number)&&!TextUtils.isEmpty(code)){
			BmobSMS.verifySmsCode(SMSCodeActivity.this,number,code, new VerifySMSCodeListener() {
				
				@Override
				public void done(BmobException ex) {
					// TODO Auto-generated method stub
					if(ex==null){//������֤������֤�ɹ�
						toast("��֤ͨ��");
					}else{
						toast("��֤ʧ�ܣ�code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
					}
				}
			});
		}else{
			toast("�������ֻ��ź���֤��");
		}
	}
	
	
	/** ��ѯ����״̬ 
	 * @method querySmsState    
	 * @return void  
	 * @exception   
	 */
	private void querySmsState(){
		BmobSMS.querySmsState(this, 3199438, new QuerySMSStateListener() {
			
			@Override
			public void done(BmobSmsState state, BmobException ex) {
				// TODO Auto-generated method stub
				if(ex==null){
					toast("����״̬��"+state.getSmsState()+",��֤״̬��"+state.getVerifyState());
				}else{
					toast("errorCode = "+ex.getErrorCode()+",errorMsg = "+ex.getLocalizedMessage());
				}
			}
		});
	}
}
