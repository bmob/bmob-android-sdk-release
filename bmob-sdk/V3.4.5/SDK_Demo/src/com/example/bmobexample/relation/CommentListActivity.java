package com.example.bmobexample.relation;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.example.bmobexample.BaseActivity;
import com.example.bmobexample.R;
import com.example.bmobexample.bean.MyUser;

public class CommentListActivity extends BaseActivity {
	
	ListView listView;
	EditText et_content;
	Button btn_publish;
	
	static List<Comment> comments = new ArrayList<Comment>();
	MyAdapter adapter;
	Post weibo = new Post();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		setTitle("�����б�");
		
		weibo.setObjectId(getIntent().getStringExtra("objectId"));
		
		adapter = new MyAdapter(this);
		et_content = (EditText) findViewById(R.id.et_content);
		btn_publish = (Button) findViewById(R.id.btn_publish);
		listView = (ListView) findViewById(R.id.listview);
		listView.setAdapter(adapter);
		
		btn_publish.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				publishComment(et_content.getText().toString());
			}
		});
		findComments();
	}
	
	private void findComments(){
		BmobQuery<Comment> query = new BmobQuery<Comment>();
		// pointer����
		query.addWhereEqualTo("post",new BmobPointer(weibo));		
		query.include("user,post.author");
		query.findObjects(this, new FindListener<Comment>() {
			
			@Override
			public void onSuccess(List<Comment> object) {
				// TODO Auto-generated method stub
				comments = object;
				adapter.notifyDataSetChanged();
				et_content.setText("");
			}
			
			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
				toast("��ѯʧ��:"+msg);
			}
		});
		
//		//Weibo�����и�Relation���͵��ֶν�comment���洢������΢�����е�������Ϣ������Բ�ѯ����Щ������Ϣ����Ϊ���Ƕ���������ͬһ��΢��
//		String sql="select include user,* from Comment where related comment to pointer('Weibo', "+"'"+weibo.getObjectId()+"')";
//		new BmobQuery<Comment>().doSQLQuery(this, sql, new SQLQueryListener<Comment>(){
//			
//			@Override
//			public void done(BmobQueryResult<Comment> result, BmobException e) {
//				// TODO Auto-generated method stub
//				if(e ==null){
//					List<Comment> list = (List<Comment>) result.getResults();
//					if(list!=null && list.size()>0){
//						comments = list;
//						adapter.notifyDataSetChanged();
//						et_content.setText("");
//					}else{
//						Log.i("smile", "��ѯ�ɹ��������ݷ���");
//					}
//				}else{
//					Log.i("smile", "�����룺"+e.getErrorCode()+"������������"+e.getMessage());
//				}
//			}
//		});
	}
	
	private void publishComment(String content){
		MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
		if(user == null){
			toast("��������ǰ���ȵ�½");
			return;
		}else if(TextUtils.isEmpty(content)){
			toast("�������۲���Ϊ��");
			return;
		}
		
		final Comment comment = new Comment();
		comment.setContent(content);
		comment.setPost(weibo);
		comment.setUser(user);
		comment.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				findComments();
				et_content.setText("");
				toast("���۳ɹ�");
			}
			
			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("����ʧ��");
			}
		});
	}
	
	private static class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		
		public MyAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		static class ViewHolder {
			TextView tv_content;
			TextView tv_author;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return comments.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item_weibo, null);

				holder = new ViewHolder();
				holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
				holder.tv_author = (TextView) convertView.findViewById(R.id.tv_author);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			final Comment comment = comments.get(position);
			
			if(comment.getUser() != null){
				holder.tv_author.setText("�����ˣ�"+comment.getUser().getUsername());
			}
			
			final String str = comment.getContent();

			holder.tv_content.setText(str);

			return convertView;
		}
	}

}
