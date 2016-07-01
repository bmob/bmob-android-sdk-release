package com.example.bmobexample.push;

import com.example.bmobexample.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import cn.bmob.push.PushConstants;

public class MyMessageReceiver extends BroadcastReceiver {

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// ��ȡ������Ϣ
		String message = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
		Log.i("BmobClient", "�յ���������Ϣ��"+message);
		Toast.makeText(context, ""+message, Toast.LENGTH_LONG).show();
		// ����֪ͨ
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		Notification n = new Notification();  
        n.icon = R.drawable.ic_launcher;  
        n.tickerText = "BmobExample�յ���Ϣ����";  
        n.when = System.currentTimeMillis();  
        //n.flags=Notification.FLAG_ONGOING_EVENT;  
        Intent i = new Intent();  
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);  
        n.setLatestEventInfo(context, "��Ϣ", message, pi);  
        n.defaults |= Notification.DEFAULT_SOUND;
        n.flags = Notification.FLAG_AUTO_CANCEL;
        nm.notify(1, n);
	}

}
