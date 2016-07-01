package com.example.bmobexample.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.example.bmobexample.BaseActivity;
import com.example.bmobexample.R;
import com.smile.filechoose.api.ChooserType;
import com.smile.filechoose.api.ChosenFile;
import com.smile.filechoose.api.FileChooserListener;
import com.smile.filechoose.api.FileChooserManager;

/** �ļ��ϴ�+������������
  * @ClassName: BmobFileActivity
  * @Description: TODO
  * @author smile
  * @date 2014-5-22 ����7:58:58
  * 
  * �ļ��ϴ���������������ʽ������һ�ļ��ϴ����ļ������ϴ���
  * 
  * 1�����뵥�����ݣ�������ֻ��һ��BmobFile�У�
  * 2�����������������-��ÿ�����ݶ�����һ��BmobFile��
  * 3�����뵥�����ݣ����BmobFile�У�
  * 4�����������������-��ÿ�����ݶ����ڶ��BmobFile��
  * 
  */
@SuppressLint("SdCardPath")
public class BmobFileActivity extends BaseActivity implements OnClickListener, FileChooserListener{

	Button tv_one_one;
	Button tv_one_many;
	Button tv_many_one;
	Button tv_many_many;
	Button btn_delete;

	private FileChooserManager fm;
	
	TextView tv_path;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file);
		initViews();
		initListeners();
	}

	public void initViews() {
		// TODO Auto-generated method stub
		tv_one_one = (Button) findViewById(R.id.tv_one_one);
		tv_one_many = (Button) findViewById(R.id.tv_one_many);
		tv_many_one = (Button) findViewById(R.id.tv_many_one);
		tv_many_many = (Button) findViewById(R.id.tv_many_many);
		btn_delete = (Button) findViewById(R.id.btn_delete);
		tv_path = (TextView)findViewById(R.id.tv_path);
	}

	public void initListeners() {
		// TODO Auto-generated method stub
		tv_one_one.setOnClickListener(this);
		tv_one_many.setOnClickListener(this);
		tv_many_one.setOnClickListener(this);
		tv_many_many.setOnClickListener(this);
		btn_delete.setOnClickListener(this);
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
	 
	 
	@Override
	public void onError(String arg0) {
		// TODO Auto-generated method stub
		showToast(arg0);
	}

	ChosenFile choosedFile;
	
	@Override
	public void onFileChosen(final ChosenFile file) {
		// TODO Auto-generated method stub
		choosedFile = file;
		runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	showFileDetails(file);
            	File mp3 = new File(choosedFile.getFilePath());
            	uploadMovoieFile(mp3);
            }
        });
	}
		
	private void showFileDetails(ChosenFile file) {
        StringBuffer text = new StringBuffer();
        text.append("File name: " + file.getFileName() + "\n");
        text.append("File path: " + file.getFilePath() + "\n");
        text.append("File size: " + file.getFileSize() );
        tv_path.setText(text.toString());
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
	 
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_one_one://���뵥�����ݣ�һ��BmobFile�У�
			insertDataWithOne();
			break;
		case R.id.tv_one_many://���뵥�����ݣ����BmobFile�У�
			insertDataWithMany();
			break;
		case R.id.tv_many_one://���������������-��ÿ�����ݶ�����һ��BmobFile��
			insertBatchDatasWithOne();
			break;
		case R.id.tv_many_many://���������������-��ÿ�����ݶ����ڶ��BmobFile��
			insertBatchDatasWithMany();
			break;
		case R.id.btn_delete://
			deleteFile();
			break;
		}
	}
	
	private void deleteFile(){
		BmobFile file = new BmobFile();
		file.setUrl(url);
		file.delete(this, new DeleteListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				showToast("�ļ�ɾ���ɹ�");
			}
			
			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				showToast("�ļ�ɾ��ʧ�ܣ�"+code+",msg = "+msg);
			}
		});
	}
	
	//======================����BmobFile��=======================================
	
	/** ���뵥�����ݣ�����BmobFile�У�
	  * ���磺���뵥����Ӱ
	  * @return void
	  * @throws
	  */
	private void insertDataWithOne(){
		if(choosedFile ==null){
			showToast("����ѡ���ļ�");
			pickFile();
			return;
		}
	}
	
	private static String url="";
	
	ProgressDialog dialog =null;
	
	/** �ϴ�ָ��·���µĵ�Ӱ�ļ�
	  * @Description: TODO
	  * @param @param type
	  * @param @param i
	  * @param @param file 
	  * @return void
	  * @throws
	  */
	private void uploadMovoieFile(File file) {
		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);                 
		dialog.setTitle("�ϴ���...");
		dialog.setIndeterminate(false);               
		dialog.setCancelable(true);       
		dialog.setCanceledOnTouchOutside(false);  
		dialog.show();
		final BmobFile bmobFile = new BmobFile(file);
		bmobFile.upload(this, new UploadFileListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				dialog.dismiss();
				url = bmobFile.getUrl();
				showToast("�ļ��ϴ��ɹ�");
				Log.i("life", "��Ӱ�ļ��ϴ��ɹ������ص�����--"+bmobFile.getFileUrl(BmobFileActivity.this)+"���ļ���="+bmobFile.getFilename());
				insertObject(new Movie("���⣺����֮��",bmobFile));
			}

			@Override
			public void onProgress(Integer arg0) {
				// TODO Auto-generated method stub
				Log.i("life", "uploadMovoieFile-->onProgress:"+arg0);
				dialog.setProgress(arg0);
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				showToast("-->uploadMovoieFile-->onFailure:" + arg0+",msg = "+arg1);
			}

		});

	}
	
	//============================================
	/**
	 * ע�����µĲ����ļ�·�������������ã��������������У��������滻��sd���ڲ��ļ�·��
	 */
	List<BmobObject> movies = new ArrayList<BmobObject>();
	String filePath_mp3 = "sdcard/png0.png";
	String filePath_lrc = "sdcard/test1.jpg";
	
	/**
	  * �˷�����������������������ÿ������ֻ��һ��BmobFile�ֶ�
	  * ���磺�����ϴ���ӰMovies
	  * @Title: insertBatchDatasWithOne
	  * @throws
	  */
	public void insertBatchDatasWithOne(){
		String[] filePaths = new String[2];
		filePaths[0] = filePath_mp3;
		filePaths[1] = filePath_lrc;
		//�����ϴ��ǻ������ϴ��ļ���������ļ�
		Bmob.uploadBatch(this, filePaths, new UploadBatchListener() {
			
			@Override
			public void onSuccess(List<BmobFile> files,List<String> urls) {
				// TODO Auto-generated method stub
				Log.i("life","insertBatchDatasWithOne -onSuccess :"+urls.size()+"-----"+files+"----"+urls);
				if(urls.size()==1){//�����һ���ļ��ϴ����
					Movie movie =new Movie("��������1",files.get(0));
					movies.add(movie);
				}else if(urls.size()==2){//�ڶ����ļ��ϴ��ɹ�
					Movie movie1 =new Movie("��������2",files.get(1));
					movies.add(movie1);
					insertBatch(movies);
				}
			}
			
			@Override
			public void onError(int statuscode, String errormsg) {
				// TODO Auto-generated method stub
				showToast("�����룺"+statuscode +",����������"+errormsg);
			}

			@Override
			public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
				// TODO Auto-generated method stub
				Log.i("life","insertBatchDatasWithOne -onProgress :"+curIndex+"---"+curPercent+"---"+total+"----"+totalPercent);
				
			}
		});
		
	
	}
	

	/** ��������
	  * insertObject
	  * @return void
	  * @throws
	  */
	private void insertObject(final BmobObject obj){
		obj.save(BmobFileActivity.this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				showToast("-->�������ݳɹ���" + obj.getObjectId());
				
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				showToast("-->��������ʧ�ܣ�" + arg0+",msg = "+arg1);
			}
		});
	}
	
	//======================���BmobFile��=======================================
	
	List<BmobObject> songs = new ArrayList<BmobObject>();
	
	/**
	 * ���뵥�����ݣ����BmobFile��--������Ϊ����
	 * �����ϴ�MP3�ļ��͸��lrc�ļ���һ��Song������
	 */
	private void insertDataWithMany() {
		String[] filePaths = new String[2];
		filePaths[0] = filePath_mp3;
		filePaths[1] = filePath_lrc;
		Bmob.uploadBatch(this, filePaths, new UploadBatchListener() {
			
			@Override
			public void onSuccess(List<BmobFile> files,List<String> urls) {
				// TODO Auto-generated method stub
				Log.i("life","insertDataWithMany -onSuccess :"+urls.size()+"-----"+files+"----"+urls);
				if(urls.size()==2){//���ȫ���ϴ��꣬����¸�����¼
					Song song =new Song("����0","��������0",files.get(0),files.get(1));
					insertObject(song);
				}else{
					//�п����ϴ����������м���ܻ����δ�ϴ��ɹ����������������д���
				}
			}
			@Override
			public void onError(int statuscode, String errormsg) {
				// TODO Auto-generated method stub
				showToast("������"+statuscode +",����������"+errormsg);
			}
			@Override
			public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
				// TODO Auto-generated method stub
				Log.i("life","insertDataWithMany -onProgress :"+curIndex+"---"+curPercent+"---"+total+"----"+totalPercent);
			}
		});
		
	}

	/**
	  * �˷�����������������������ÿ�����ݶ��ж��BmobFile�ֶΣ�
	  * ���磺�����ϴ�����songs
	  * @Title: insertBatchDatasWithOne
	  * @throws
	  */
	private void insertBatchDatasWithMany() {
		File ff = new File("/mnt/sdcard/testbmob/");
		File[] fs = ff.listFiles();
		String[] filePaths = new String[fs.length];
		if(fs!=null && fs.length>0){
			final int len = fs.length;
			for(int i=0;i<len;i++){
				filePaths[i] = fs[i].getAbsolutePath();
			}
			Bmob.uploadBatch(this, filePaths, new UploadBatchListener() {
				
				@Override
				public void onSuccess(List<BmobFile> files,List<String> urls) {
					// TODO Auto-generated method stub
					Log.i("life","insertBatchDatasWithMany -onSuccess :"+urls.size()+"-----"+files+"----"+urls);
					if(urls.size()==len){//���ȫ���ϴ��꣬����������
						Log.i("life","====insertBatch=====");
						//��Ϊ�ҵ��ļ�������������ͼƬÿ�������뵽һ��������
						Song song =new Song("����0","��������0",files.get(0),files.get(1));
						songs.add(song);
//						Song song1 =new Song("����1","��������1",files.get(2),files.get(3));
//						songs.add(song1);
						//�����������
						insertBatch(songs);
					}else{
						//�п����ϴ����������м���ܻ����δ�ϴ��ɹ����������������д���
					}
				}
				
				@Override
				public void onError(int statuscode, String errormsg) {
					// TODO Auto-generated method stub
					showToast("������"+statuscode +",����������"+errormsg);
				}

				@Override
				public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
					// TODO Auto-generated method stub
					Log.i("life","insertBatchDatasWithMany -onProgress :"+curIndex+"---"+curPercent+"---"+total+"----"+totalPercent);
				}
			});
		}
	}
	
	/** �����������
	  * insertBatch
	  * @return void
	  * @throws
	  */
	public void insertBatch(List<BmobObject> files){
		new BmobObject().insertBatch(BmobFileActivity.this, files, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				showToast("---->�������³ɹ�");
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				showToast("---->��������ʧ��"+arg0);
				
			}
		});
	}

}
