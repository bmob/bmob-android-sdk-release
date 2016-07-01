package com.example.bmobexample.newfile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.LocalThumbnailListener;
import com.example.bmobexample.BaseActivity;
import com.example.bmobexample.R;
import com.smile.filechoose.api.ChooserType;
import com.smile.filechoose.api.ChosenFile;
import com.smile.filechoose.api.FileChooserListener;
import com.smile.filechoose.api.FileChooserManager;

/**
 * ������������ͼ
 */
public class LocalThumbnailActivity extends BaseActivity implements FileChooserListener{

	EditText edit_ratio,edit_width,edit_height,edit_quality;
	Button btn_thumbnail,btn_thumbnail1,btn_thumbnail2;
	private FileChooserManager fm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local_thumbnail);
		btn_thumbnail = (Button)findViewById(R.id.btn_thumbnail);
		btn_thumbnail1 = (Button)findViewById(R.id.btn_thumbnail1);
		btn_thumbnail2 = (Button)findViewById(R.id.btn_thumbnail2);

		edit_ratio = (EditText)findViewById(R.id.edit_ratio);
		edit_width = (EditText)findViewById(R.id.edit_width);
		edit_height = (EditText)findViewById(R.id.edit_height);
		edit_quality = (EditText)findViewById(R.id.edit_quality);
		btn_thumbnail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(choosedFile ==null){
					showToast("����ѡ���ļ�");
					pickFile();
				}else{
					getLocalThumbnailById();
				}
			}
		});

		btn_thumbnail1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getLocalThumbnail();
			}
		});
		btn_thumbnail2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getLocalThumbnailByQuality();
			}
		});
		
	}
	
	ChosenFile choosedFile;
	
	@Override
	public void onFileChosen(final ChosenFile file) {
		// TODO Auto-generated method stub
		choosedFile = file;
		runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	getLocalThumbnailById();
            }
        });
	}
	
	@Override
	public void onError(String arg0) {
		// TODO Auto-generated method stub
		showToast(arg0);
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ChooserType.REQUEST_PICK_FILE && resultCode == RESULT_OK) {
            if (fm == null) {
                fm = new FileChooserManager(this);
                fm.setFileChooserListener(this);
            }
            Log.i(TAG, "Probable file size: " + fm.queryProbableFileSize(data.getData(), this));
            fm.submit(requestCode, data);
        }
    }
	
	public void pickFile() {
        fm = new FileChooserManager(this);
        fm.setFileChooserListener(this);
        try {
            fm.choose();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
	private void getLocalThumbnailById(){
		final String id = edit_ratio.getText().toString();
		if(TextUtils.isEmpty(id)||TextUtils.isEmpty(choosedFile.getFilePath())){
			showToast("��ѡ��ͼƬ");
			return;
		}
		int modeId =Integer.parseInt(id);
		BmobProFile.getInstance(LocalThumbnailActivity.this).getLocalThumbnail(choosedFile.getFilePath(), modeId, new LocalThumbnailListener() {

			@Override
			public void onError(int statuscode, String errormsg) {
				// TODO Auto-generated method stub
				showToast("��������ͼ����ʧ�� :"+statuscode+","+errormsg);
			}

			@Override
			public void onSuccess(String thumbnailPath) {
				// TODO Auto-generated method stub
				showToast("��������ͼ�����ɹ�  :"+thumbnailPath);
			}
		});
	}
	
	private void getLocalThumbnail(){
		String id = edit_ratio.getText().toString();
		String w = edit_width.getText().toString();
		String h = edit_height.getText().toString();
		if(TextUtils.isEmpty(id)||TextUtils.isEmpty(choosedFile.getFilePath())){
			showToast("��ѡ��ͼƬ");
			return;
		}
		if(TextUtils.isEmpty(w)){
			return;
		}
		if(TextUtils.isEmpty(h)){
			return;
		}
		int modeId =Integer.parseInt(id);
		int width =Integer.parseInt(w);
		int height =Integer.parseInt(h);
		BmobProFile.getInstance(LocalThumbnailActivity.this).getLocalThumbnail(choosedFile.getFilePath(), modeId, width, height, new LocalThumbnailListener() {

			@Override
			public void onError(int statuscode, String errormsg) {
				// TODO Auto-generated method stub
				showToast("��������ͼ����ʧ�� :"+statuscode+","+errormsg);
			}

			@Override
			public void onSuccess(String thumbnailPath) {
				// TODO Auto-generated method stub
				showToast("��������ͼ�����ɹ�  :"+thumbnailPath);
			}
		});
	}
	
	private void getLocalThumbnailByQuality(){
		String id = edit_ratio.getText().toString();
		String w = edit_width.getText().toString();
		String h = edit_height.getText().toString();
		String q = edit_quality.getText().toString();
		if(TextUtils.isEmpty(id)||TextUtils.isEmpty(choosedFile.getFilePath())){
			showToast("��ѡ��ͼƬ");
			return;
		}
		if(TextUtils.isEmpty(w)){
			return;
		}
		if(TextUtils.isEmpty(h)){
			return;
		}
		if(TextUtils.isEmpty(q)){
			return;
		}
		int modeId =Integer.parseInt(id);
		int width =Integer.parseInt(w);
		int height =Integer.parseInt(h);
		//��Ϊ������ͼƬѹ��������0-100֮�䣬���㿪����
		int quality =Integer.parseInt(q);
		
		BmobProFile.getInstance(LocalThumbnailActivity.this).getLocalThumbnail(choosedFile.getFilePath(), modeId, width, height, quality,new LocalThumbnailListener() {
			
			@Override
			public void onError(int statuscode, String errormsg) {
				// TODO Auto-generated method stub
				showToast("��������ͼ����ʧ�� :"+statuscode+","+errormsg);
			}
			
			@Override
			public void onSuccess(String thumbnailPath) {
				// TODO Auto-generated method stub
				showToast("��������ͼ�����ɹ�  :"+thumbnailPath);
			}
		});
	}
	

}
