package com.example.bmobexample.newfile;

import java.util.Arrays;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.BTPFileResponse;
import com.bmob.BmobPro;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.DeleteFileListener;
import com.bmob.btp.callback.DownloadListener;
import com.bmob.btp.callback.GetAccessUrlListener;
import com.bmob.btp.callback.ThumbnailListener;
import com.bmob.btp.callback.UploadBatchListener;
import com.bmob.btp.callback.UploadListener;
import com.example.bmobexample.BaseActivity;
import com.example.bmobexample.R;
import com.example.bmobexample.bean.Person;
import com.smile.filechoose.api.ChooserType;
import com.smile.filechoose.api.ChosenFile;
import com.smile.filechoose.api.FileChooserListener;
import com.smile.filechoose.api.FileChooserManager;

/**
 * @ClassName: NewBmobFileActivity
 * @Description: TODO
 * @author smile
 * @date 2014-10-24 ����2:33:40
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class NewBmobFileActivity extends BaseActivity implements FileChooserListener{

	protected ListView mListview;
	protected BaseAdapter mAdapter;
	private FileChooserManager fm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_file);
		initListView();
	}

	private void initListView(){
		mListview = (ListView) findViewById(R.id.list);
		mAdapter = new ArrayAdapter<String>(this, R.layout.item_list,R.id.tv_item, getResources().getStringArray(
						R.array.bmob_newfile_arrays));
		mListview.setAdapter(mAdapter);
		mListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				testBmobPro(position + 1);
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
            	upload();
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
	
	private void testBmobPro(int pos) {
		switch (pos) {
		case 1:
			showToast("����ѡ���ļ�");
			pickFile();
			break;
		case 2:
			uploadBatchFile();
			break;
		case 3:
			download();
			break;
		case 4:
			clearCache();
			showToast("��������ɹ�");
			break;
		case 5:
			getCurrentCache();
			break;
		case 6:
			showToast("�ļ����ص�ַ��"+BmobPro.getInstance(this).getCacheDownloadDir());
			break;
		case 7:
			requestThumbnailTask();
			break;
		case 8:
			startActivity(new Intent(this, LocalThumbnailActivity.class));
			break;
		case 9: //��ȡ�ļ��Ŀɷ��ʵ�ַ
			getAccessURL();
			break;
		case 10://ɾ���ļ�
			deleteFile();
			break;
		default:
			break;
		}
	}

	String downLoadUrl = "";
	/**
	 * @Description:��һ�ļ��ϴ�
	 * @param  
	 * @return void
	 * @throws
	 */
	ProgressDialog dialog =null;

	private void upload(){
		dialog = new ProgressDialog(NewBmobFileActivity.this);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);                 
		dialog.setTitle("�ϴ���...");
		dialog.setIndeterminate(false);               
		dialog.setCancelable(true);       
		dialog.setCanceledOnTouchOutside(false);  
		dialog.show();
		BTPFileResponse response = BmobProFile.getInstance(NewBmobFileActivity.this).upload(choosedFile.getFilePath(), new UploadListener() {

			@Override
			public void onSuccess(String fileName,String url,BmobFile file) {
				// TODO Auto-generated method stub
				Log.i("smile", "�°��ļ������fileName = "+fileName+",�°��ļ������url ="+url);
				if(file!=null){
					Log.i("smile", "���ݾɰ��ļ������Դ�ļ��� = "+file.getFilename()+",�ļ���ַurl = "+file.getUrl());
				}
				downloadName = fileName;
				dialog.dismiss();
				//����BmobFile����
				Person person = new Person();
				person.setPic(file);
				person.setName("smile");
				person.save(NewBmobFileActivity.this,new SaveListener() {
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						Log.i("smile", "Person���󱣴�ɹ�");
					}
					
					@Override
					public void onFailure(int code, String msg) {
						// TODO Auto-generated method stub
						Log.i("smile", "Person���󱣴�ʧ�ܣ�"+code+"-"+msg);
					}
				});
				showToast("�ļ����ϴ��ɹ���"+fileName);
			}

			@Override
			public void onProgress(int ratio) {
				// TODO Auto-generated method stub
				Log.i("smile","MainActivity -onProgress :"+ratio);
				dialog.setProgress(ratio);
			}

			@Override
			public void onError(int statuscode, String errormsg) {
				// TODO Auto-generated method stub
				//showLog("MainActivity -onError :"+statuscode +"--"+errormsg);
				dialog.dismiss();
				showToast("�ϴ�����"+errormsg);
			}
		});

		showLog("upload�������ص�code = "+response.getStatusCode());
	}

	/**
	 * @Title: updateBatchFile
	 * @Description: �ļ������ϴ�
	 * @param  
	 * @return void
	 * @throws
	 */
	private void uploadBatchFile(){
		dialog = new ProgressDialog(NewBmobFileActivity.this);
		String[] files = new String[]{"sdcard/png0.png","sdcard/png1.png"};
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);                 
		dialog.setTitle("�����ϴ���...");
		dialog.setIndeterminate(false);               
		dialog.setCancelable(true);       
		dialog.setCanceledOnTouchOutside(false);  
		dialog.setMax(files.length);
		dialog.show();
		BmobProFile.getInstance(NewBmobFileActivity.this).uploadBatch(files, new UploadBatchListener() {

			@Override
			public void onSuccess(boolean isFinish,String[] fileNames,String[] urls,BmobFile[] files) {
				// TODO Auto-generated method stub
				if(isFinish){
					dialog.dismiss();
				}
				showToast(""+Arrays.asList(fileNames)+""+Arrays.asList(files));
				showLog("NewBmobFileActivity -onSuccess :"+isFinish+"-----"+Arrays.asList(fileNames)+"----"+Arrays.asList(urls)+"----"+Arrays.asList(files));
			}

			@Override
			public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
				// TODO Auto-generated method stub
				dialog.setProgress(curIndex);
				showLog("NewBmobFileActivity -onProgress :"+curIndex+"---"+curPercent+"---"+total+"----"+totalPercent);
			}

			@Override
			public void onError(int statuscode, String errormsg) {
				// TODO Auto-generated method stub
				showLog("NewBmobFileActivity -onError :"+statuscode+"--"+errormsg);
				dialog.dismiss();
				showToast("�����ϴ�����"+errormsg);
			}
		});
	}

	private static String downloadName= "";

	/**
	 * @Description: �ļ�����
	 * @param  
	 * @return void
	 * @throws
	 */
	private void download(){
		if(downloadName.equals("")){
			showLog("��ָ�������ļ���");
			return;
		}
		dialog = new ProgressDialog(NewBmobFileActivity.this);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);                 
		dialog.setTitle("������...");
		dialog.setIndeterminate(false);               
		dialog.setCancelable(true);       
		dialog.setCanceledOnTouchOutside(false);  
		dialog.show();
		BmobProFile.getInstance(NewBmobFileActivity.this).download(downloadName, new DownloadListener() {

			@Override
			public void onSuccess(String fullPath) {
				// TODO Auto-generated method stub
				showLog("MainActivity -download-->onSuccess :"+fullPath);
				dialog.dismiss();
				showToast("���سɹ���"+fullPath);
			}

			@Override
			public void onProgress(String localPath, int percent) {
				// TODO Auto-generated method stub
				showLog("MainActivity -download-->onProgress :"+percent);
				dialog.setProgress(percent);
			}

			@Override
			public void onError(int statuscode, String errormsg) {
				// TODO Auto-generated method stub
				showLog("MainActivity -download-->onError :"+statuscode +"--"+errormsg);
				dialog.dismiss();
				showToast("���س���"+errormsg);
			}
		});
	}

	/**
	 * @Description: �ύ�����������������ͼ������
	 * @param  
	 * @return void
	 * @throws
	 */
	private void requestThumbnailTask(){
		BmobProFile.getInstance(NewBmobFileActivity.this).submitThumnailTask(downloadName, 1, new ThumbnailListener() {

			@Override
			public void onSuccess(String thumbnailName,String thumbnailUrl) {
				// TODO Auto-generated method stub
				//������ͼ���ƺ͵�ַ����һ���Ǽ�ʱ���صģ���Ϊ�첽����
				showToast("�ύ�����������������ͼ������ɹ���:"+thumbnailName+"-->"+thumbnailUrl);
				showLog("MainActivity -onSuccess :"+thumbnailName+"-->"+thumbnailUrl);
			}

			@Override
			public void onError(int statuscode, String errormsg) {
				// TODO Auto-generated method stub
				showToast("�ύ�����������������ͼ������ʧ�ܣ�:"+statuscode+"---"+errormsg);
				showLog("MainActivity -onError :"+statuscode+"---"+errormsg);
			}
		});
	}
	
	private void getAccessURL(){
		BmobProFile.getInstance(this).getAccessURL(downloadName, new GetAccessUrlListener() {
			
			@Override
			public void onError(int errorcode, String errormsg) {
				// TODO Auto-generated method stub
				showToast("��ȡ�ļ��ķ�������ַʧ�ܣ�"+errormsg+"("+errorcode+")");
			}
			
			@Override
			public void onSuccess(BmobFile file) {
				// TODO Auto-generated method stub
				Log.i("smile", "Դ�ļ�����"+file.getFilename()+"���ɷ��ʵĵ�ַ��"+file.getUrl());
				showToast("��ȡ�ļ��ķ�������ַ�ɹ���Դ�ļ�����"+file.getFilename()+"��group="+file.getGroup()+"��fileUrl = "+file.getUrl());
			}
		});
	}
	
	private void deleteFile(){
		BmobProFile.getInstance(this).deleteFile(downloadName, new DeleteFileListener() {
			
			@Override
			public void onError(int errorcode, String errormsg) {
				// TODO Auto-generated method stub
				showToast("ɾ���ļ�ʧ�ܣ�"+errormsg+"("+errorcode+")");
			}
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				showToast("ɾ���ļ��ɹ�");
			}
		});
	}
	
	/**
	 * @Title: clearCache
	 * @Description: �������
	 * @param  
	 * @return void
	 * @throws
	 */
	public void clearCache(){
		BmobPro.getInstance(NewBmobFileActivity.this).clearCache();
	}

	/**
	 * ��ȡ��ǰ�����С
	 */
	public void getCurrentCache(){
		String cacheSize = String.valueOf(BmobPro.getInstance(NewBmobFileActivity.this).getCacheFileSize());
		String formatSize = BmobPro.getInstance(NewBmobFileActivity.this).getCacheFormatSize();
		showToast("�ѻ����С��"+cacheSize+"----->"+formatSize);
	}

}
