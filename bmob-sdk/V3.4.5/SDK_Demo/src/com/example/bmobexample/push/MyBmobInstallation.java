package com.example.bmobexample.push;

import android.content.Context;
import cn.bmob.v3.BmobInstallation;

public class MyBmobInstallation extends BmobInstallation {

	/**  
	 *  
	 */  
	private static final long serialVersionUID = 1L;

	/**  
	 * �û�id-�������Խ��豸���û�֮����а�
	 */  
	private String uid;
	
	public MyBmobInstallation(Context context) {
		super(context);
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
}
