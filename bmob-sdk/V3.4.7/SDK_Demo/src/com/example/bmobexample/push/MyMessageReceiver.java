package com.example.bmobexample.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import cn.bmob.push.PushConstants;
import cn.bmob.v3.helper.NotificationCompat;

import com.example.bmobexample.R;

public class MyMessageReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String message = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
		Log.i("BmobClient", "�յ���������Ϣ��"+message);
		Toast.makeText(context.getApplicationContext(), ""+message, Toast.LENGTH_LONG).show();
		//ʹ��cn.bmob.v3.helper���µ�NotificationCompat������֪ͨ����Ҳ����ʹ��support_v4�����NotificationCompat��
		Intent i = new Intent();
		i.setClass(context, ActBmobPush.class);
		i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);
		NotificationCompat.Builder mBuilder =new NotificationCompat.Builder(context)
        .setTicker("BmobExample�յ���Ϣ����")
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle("��Ϣ")
        .setContentText(message)
        .setAutoCancel(true)
        .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
        .setContentIntent(pi);
		// ����֪ͨ
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification n = mBuilder.build();  
        nm.notify(0, n);
	}

}
