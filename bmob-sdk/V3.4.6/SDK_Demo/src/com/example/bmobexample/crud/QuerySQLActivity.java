package com.example.bmobexample.crud;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.StatisticQueryListener;

import com.example.bmobexample.BaseActivity;
import com.example.bmobexample.R;
import com.example.bmobexample.bean.GameScore;
import com.example.bmobexample.bean.MyUser;

/**  
 *   
 * @class  QuerySQLActivity  
 * @author smile   
 * @date   2015-5-8 ����4:55:10  
 *   
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("SimpleDateFormat")
public class QuerySQLActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mListview = (ListView) findViewById(R.id.listview);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item,R.id.tv_item, getResources().getStringArray(R.array.bmob_sql_list));
		mListview.setAdapter(mAdapter);
		mListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				testBmob(position + 1);
			}
		});
		//�����Ҫ�������ݵĻ�������ʹ��QueryStatisticActivity�������createGameScores����������
	}
	
	private void testBmob(int pos) {
		switch (pos) {
		case 1:
			querySql();
			break;
		case 2:
			queryCountSql();
			break;
		case 3:
			queryStatisticSql();
			break;
		case 4:
			queryStatmentSql();
			break;
		}
	}
	
	private void querySql(){
		//------------��ѯ��������-------------------------------------------
//		String sql = "select * from GameScore";//��ѯ���е���Ϸ�÷ּ�¼
		
		//------------�Ƚϲ�ѯ����ȡ����ȡ����ڡ�С�ڡ����ڵ��ڡ�С�ڵ���...-------------
//		String sql = "select * from GameScore where playScore<10";//��ѯ��ҵ÷���10���µ���Ϣ
//		String sql = "select * from GameScore where game='�����ܿ�'";//��ѯ����������ܿ������Ϸ����Ϸ��¼
//		String sql = "select * from GameScore where game!='�����ܿ�'";//��ѯ�������ܿ������Ϸ�������Ϸ��¼
		
		//------------ֵ�Ƿ���ڲ�ѯ------------------------------------------
//		String sql = "select * from GameScore where gps is not exists";//��ѯGameScore����û�е���λ�õ�������Ϣ(ֵ�Ƿ���ڵĽ綨������û��Ϊ����ֶ����ó�ʼֵ�����ַ���������ֵ)
		
		//------------ģ����ѯ------------------------------------------------
//		String sql = "select * from GameScore where game like ����%";//��ѯGameScore������Ϸ���Ե�����ͷ����Ϣ
//		String sql = "select * from GameScore where game not like ����%";//��ѯGameScore������Ϸ�����Ե�����ͷ����Ϣ
//		String sql = "select * from GameScore where game regexp ����.*";//��ѯGameScore������Ϸ���Ե�����ͷ����Ϣ
		
		//------------�����ѯ------------------------------------------------
		//��ѯ����Ӿ���õ��ˣ�ע��hobby��Person���б�ʾ���õ�һ���������͵��ֶΣ�
//		String sql = "select * from Person where hobby ='��Ӿ'";//
		//��ѯ�Ȱ�����ӾҲ���ÿ������
//		String sql = "select * from Person where hobby all ('��Ӿ','����')";//
//		new BmobQuery<Person>().doSQLQuery(this, sql, new SQLQueryListener<Person>(){
//			
//			@Override
//			public void done(BmobQueryResult<Person> result, BmobException e) {
//				// TODO Auto-generated method stub
//				if(e ==null){
//					List<Person> list = (List<Person>) result.getResults();
//					if(list!=null && list.size()>0){
//						for(int i=0;i<list.size();i++){
//							Person p = list.get(i);
//							Log.i("smile", ""+p.getName()+"-"+p.getAddress()+"-"+p.getAge());
//						}
//					}else{
//						Log.i("smile", "��ѯ�ɹ��������ݷ���");
//					}
//				}else{
//					Log.i("smile", "�����룺"+e.getErrorCode()+"������������"+e.getMessage());
//				}
//			}
//		});
		
		//���ú�����
		//------------Date��ѯ------------------------------------------------
//		String dateString = "2015-05-12";  
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date date  = null;
//		try {
//			date = sdf.parse(dateString);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
//		String sql = "select * from GameScore where createdAt < date('"+new BmobDate(date).getDate()+"')";//��ѯ2015��5��12��֮ǰ������
		//�����sql��ͬ�������д��
//		String sql = "select * from GameScore where createdAt < {'__type': 'Date','iso': '"+new BmobDate(date).getDate()+"'}";//��ѯ2015��5��12��֮ǰ������
		
		//------------pointer��ѯ------------------------------------------------
		//��ѯ��ǰ�û�����Ϸ��¼
//		MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
//		//��һ��д����
//		String sql = "select * from GameScore where player = pointer('_User', "+"'"+user.getObjectId()+"')";
//		//�ڶ���д����
//		String d = "{'__type':'Pointer','className':'_User','objectId':'"+user.getObjectId()+"'}";
//		String sql = "select * from GameScore where player = "+d;
		
		//------------����λ�ò�ѯ-----------------------------------------------------
		//1����ѯָ������λ��
		//��һ��д��
//		String sql = "select * from GameScore where gps = geopoint(112.934755,24.52065)";//��ѯ��ָ������λ�õ���Ϸ��¼
		//�ڶ���д��
//		String sql = "select * from GameScore where gps = {'__type':'GeoPoint','latitude':24.52065,'longitude':112.934755}";
		
		//2����ѯָ������λ�ø�������Ϣ�����������������ˣ�
//		String sql = "select * from GameScore where gps near geopoint(112.934755,24.52065)";//��ѯ��ָ������λ�ø�������Ϸ��¼���ɽ���Զ�����У�
//		String sql = "select * from GameScore where gps near [112.934755,24.52065]";//��ѯ��ָ������λ�ø�������Ϸ��¼
//		
//		//3��Ϊ���������޶������������
//		String sql = "select * from GameScore where gps near [112.934755,24.52065] max 1 km";//��ѯ��ָ������λ�ø���1km���ڵ���Ϸ��¼
//		
//		//4����ѯ���η�Χ��
//		String sql = "select * from GameScore where gps within [102.934755,24.52065] and [112.934755,24.52065]";
		
		//------------include��ѯ��------------------------------------------------
		//�������뽫��Ϸ��ҵ���ϢҲ��ѯ����
//		String sql = "select include player,* from GameScore limit 5";
		
		//------------�Ӳ�ѯ--------------------------------------ʹ��in�����Ӳ�ѯ
		//1��in��������Ǹ��б������ѯ��Ϸ��Ϊ�����ܿᡢ�����ͻ����Ϸ��¼��
//		String sql = "select * from GameScore where game in ('�����ܿ�','�����ͻ')";
		//2��in����Ҳ�����Ǹ��Ӳ�ѯ
		//����Ҫ��ѯ��Ϸ�÷ִ���10���û�����,������Ҫusername��ֵ����Ҫ���Ӳ�ѯ�����������У��������GameScore�����½���һ��name�ֶ�����ʾ��ҵ�����
//		String sql="select * from _User where username in (select name from GameScore where playScore>10)";
		//��ѯ�������20����ҵ���Ϸ��Ϣ
//		String sql="select * from GameScore where name in (select username from _User where age>20)";
//		���� Ҫ��ѯ����ͼƬ��΢���������б�
		
		//------------��ϵ��ѯ------------------------------------------------------------
		//��ѯĳ��΢��������������Ϣ-����ֻ�Ǿٸ����ӣ�����ɲ鿴�ù�����CommentListActivity��
		//Weibo�����и�Relation���͵��ֶν�comment���洢������΢�����е�������Ϣ������Բ�ѯ����Щ������Ϣ����Ϊ���Ƕ���������ͬһ��΢��
//		String sql="select include author,* from Comment where related comment to pointer('Weibo','15e67b68ce')";
		
		//------------���ϲ�ѯ------------------------------------------------------------
		//1���������ѯ����ѯ��Ϸ�÷���10-15֮�������
//		String sql ="select * from GameScore where playScore>10 and playScore<=15";
		//2�����ϻ��ѯ���ټӸ���������Ϸ�÷�Ϊ5
//		String sql ="select * from GameScore where playScore>10 and playScore<=15 or playScore=5";
		//�ȼ�����������
//		String sql ="select * from GameScore where (playScore>10 and playScore<=15) or playScore=5";
		
		//------------��ҳ��ѯ------------------------------------------------------------
//		String sql = "select * from GameScore limit 10,10";//����ӵ� 10 ����ʼ������ 10 ��GameScore����
		
		//------------�������------------------------------------------------------------
		String sql = "select * from GameScore order by playScore,signScore desc";//���ݵ÷֣�playScore���������򣬵�playScore��ͬ������ٸ���signScore��������
		//------------�����ѯ------------------------------------------------------------
		BmobQuery<GameScore> query = new BmobQuery<GameScore>();
		query.setSQL(sql);
		boolean isCache = query.hasCachedResult(this,GameScore.class);
		toast("�Ƿ��л��棺"+isCache);
		if(isCache){
			query.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);	// �ȴӻ���ȡ���ݣ����û�еĻ����ٴ�����ȡ��
		}else{
			query.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);	// ���û�л���Ļ������ȴ�������ȡ
		}
		//ִ��SQL��ѯ����
		query.doSQLQuery(this, sql, new SQLQueryListener<GameScore>(){
			
			@Override
			public void done(BmobQueryResult<GameScore> result, BmobException e) {
				// TODO Auto-generated method stub
				if(e ==null){
					List<GameScore> list = (List<GameScore>) result.getResults();
					if(list!=null && list.size()>0){
						for(int i=0;i<list.size();i++){
							GameScore p = list.get(i);
							Log.i("smile", ""+p.getPlayer()+"-"+p.getGame()+"-"+p.getPlayScore()+"-"+p.getSignScore());
						}
						toast("��ѯ�ɹ���"+list.size());
					}else{
						toast("��ѯ�ɹ��������ݷ���");
					}
				}else{
					toast("�����룺"+e.getErrorCode()+"������������"+e.getMessage());
				}
			}
		});
		
	}
	
	private void queryCountSql(){
//		String sql = "select count(*) from GameScore";//��ѯGameScore���е��ܼ�¼��
		String sql = "select count(objectId) from GameScore";//��ѯGameScore���е��ܼ�¼��
//		String sql = "select count(*),* from GameScore";//��ѯGameScore�����ܼ�¼�����������м�¼��Ϣ
//		String sql = "select count(*),game from GameScore";//��ѯGameScore���е�����������ÿ����¼����Ϸ�����������ض��ֶΣ�
//		String sql = "select count(*) from GameScore where playScore>10 and where playScore<20";//��ѯ�ܵļ�¼�������ص÷���10-20֮�����Ϣ
		new BmobQuery<GameScore>().doSQLQuery(this, sql, new SQLQueryListener<GameScore>(){
			
			@Override
			public void done(BmobQueryResult<GameScore> result, BmobException e) {
				// TODO Auto-generated method stub
				if(e ==null){
					int count = result.getCount();
					Log.i("smile", "������"+count);
					List<GameScore> list = (List<GameScore>) result.getResults();
					if(list.size()>0){
						for(int i=0;i<list.size();i++){
							GameScore p = list.get(i);
							Log.i("smile", ""+p.getPlayer()+"-"+p.getGame()+"-"+p.getPlayScore()+"-"+p.getSignScore());
						}
					}else{
						Log.i("smile", "��ѯ�ɹ���������");
					}
				}else{
					Log.i("smile", "�����룺"+e.getErrorCode()+"������������"+e.getMessage());
				}
			}
		});
	}
	
	
	/**ͳ�Ʋ�ѯ
	 * @method queryStatisticql 
	 * @param     
	 * @return void  
	 * @exception   
	 */
	private void queryStatisticSql(){
		String bql = "select sum(playScore) from GameScore group by name order by -createdAt";//���,������������з��鲢����ʱ�併������
		new BmobQuery<GameScore>().doStatisticQuery(this, bql,new StatisticQueryListener(){
			
			@Override
			public void done(Object result, BmobException e) {
				// TODO Auto-generated method stub
				if(e ==null){
					JSONArray ary = (JSONArray) result;
					if(ary!=null){//
						try {
							JSONObject obj = ary.getJSONObject(0);
							int sum = obj.getInt("_sumPlayScore");
							String name = obj.getString("name");
							showToast("��Ϸ�ܵ÷֣�" + sum+",name:"+name);
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
					}else{
						showToast("��ѯ�ɹ���������");
					}
				}else{
					Log.i("smile", "�����룺"+e.getErrorCode()+"������������"+e.getMessage());
				}
			}
		});
	}
	
	/**ռλ����ѯ
	 * @method queryStatmentSql 
	 * @param     
	 * @return void  
	 * @exception   
	 */
	private void queryStatmentSql(){
		MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
		//��ѯ��ǰ�û���2015��5��12��֮ǰ�����ض�����λ�ø�������Ϸ��¼
		String sql = "select * from GameScore where createdAt > date(?) and player = pointer(?,?) and gps near geopoint(?,?)";
		BmobQuery<GameScore> query = new BmobQuery<GameScore>();
		query.setSQL(sql);
		query.setPreparedParams(new Object[]{"2015-05-12 00:00:00","_User",user.getObjectId(),112.934755,24.52065});
		boolean isCache = query.hasCachedResult(this,GameScore.class);
		Log.i("life", "isCache = "+isCache);
		if(isCache){
			query.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);	// �ȴӻ���ȡ���ݣ����û�еĻ����ٴ�����ȡ��
		}else{
			query.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);	// ���û�л���Ļ������ȴ�������ȡ
		}
		query.doSQLQuery(this,new SQLQueryListener<GameScore>(){
			
			@Override
			public void done(BmobQueryResult<GameScore> result, BmobException e) {
				// TODO Auto-generated method stub
				if(e ==null){
					List<GameScore> list = (List<GameScore>) result.getResults();
					if(list!=null && list.size()>0){
						for(int i=0;i<list.size();i++){
							GameScore p = list.get(i);
							Log.i("smile", ""+p.getPlayer()+"-"+p.getGame()+"-"+p.getPlayScore()+"-"+p.getSignScore());
						}
					}else{
						Log.i("smile", "��ѯ�ɹ��������ݷ���");
					}
				}else{
					Log.i("smile", "�����룺"+e.getErrorCode()+"������������"+e.getMessage());
				}
			}
		});
		//��ͬ������Ĳ�ѯ����
//		new BmobQuery<GameScore>().doSQLQuery(this, sql,new SQLQueryListener<GameScore>(){
//			
//			@Override
//			public void done(BmobQueryResult<GameScore> result, BmobException e) {
//				// TODO Auto-generated method stub
//				if(e ==null){
//					List<GameScore> list = (List<GameScore>) result.getResults();
//					if(list!=null && list.size()>0){
//						for(int i=0;i<list.size();i++){
//							GameScore p = list.get(i);
//							Log.i("smile", ""+p.getPlayer()+"-"+p.getGame()+"-"+p.getPlayScore()+"-"+p.getSignScore());
//						}
//					}else{
//						Log.i("smile", "��ѯ�ɹ��������ݷ���");
//					}
//				}else{
//					Log.i("smile", "�����룺"+e.getErrorCode()+"������������"+e.getMessage());
//				}
//			}
//		},"2015-05-12 00:00:00","_User",user.getObjectId(),112.934755,24.52065);
		
		//�й�ͳ�Ʋ�ѯ��ռλ������
//		String bql = "select sum(playScore),count(*) from GameScore group by game having _sumPlayScore>200";//������Ϸ���з��鲢��ȡ�ܷ������10��ͳ����Ϣ
//		new BmobQuery<GameScore>().doStatisticQuery(this, bql,new StatisticQueryListener(){
//			
//			@Override
//			public void done(Object result, BmobException e) {
//				// TODO Auto-generated method stub
//				if(e ==null){
//					JSONArray ary = (JSONArray) result;
//					if(ary!=null){//
//						try {
//							JSONObject obj = ary.getJSONObject(0);
//							int sum = obj.getInt("_sumPlayScore");
//							showToast("��Ϸ�ܵ÷֣�" + sum);
//						} catch (JSONException e1) {
//							e1.printStackTrace();
//						}
//					}else{
//						showToast("��ѯ�ɹ���������");
//					}
//				}else{
//					Log.i("smile", "�����룺"+e.getErrorCode()+"������������"+e.getMessage());
//				}
//			}
//		});
	}
	
}
