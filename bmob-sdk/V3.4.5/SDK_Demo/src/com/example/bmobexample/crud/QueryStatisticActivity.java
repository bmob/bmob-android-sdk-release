package com.example.bmobexample.crud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.FindStatisticsListener;
import cn.bmob.v3.listener.SaveListener;

import com.example.bmobexample.BaseActivity;
import com.example.bmobexample.R;
import com.example.bmobexample.bean.GameScore;
import com.example.bmobexample.bean.MyUser;

/**
 * ��ѯͳ������
 * @class QueryStatisticActivity
 * @author smile
 * @date 2015-4-23 ����11:43:32
 * 
 */
public class QueryStatisticActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mListview = (ListView) findViewById(R.id.listview);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.tv_item, getResources().getStringArray(
						R.array.bmob_statistic_list));
		mListview.setAdapter(mAdapter);
		mListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				testBmob(position + 1);
			}
		});
//		 createGameScores();
	}

	public void createGameScores() {
		List<BmobObject> scores = new ArrayList<BmobObject>();
		//��Ӳ�������
		for (int i = 0; i < 5; i++) {
			GameScore score = new GameScore();
			score.setGps(new BmobGeoPoint(112.934755,24.52065));
			MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
			score.setName(user.getUsername());
			score.setPlayer(user);
			score.setGame("�����ܿ�");
			score.setPlayScore(5 + i);
			score.setSignScore(i);
			scores.add(score);
		}
		//
		for (int i = 0; i < 5; i++) {
			GameScore score = new GameScore();
			score.setGps(new BmobGeoPoint(111.934755,25.52065));
			MyUser user = BmobUser.getCurrentUser(this, MyUser.class);//Ϊ�˲���pointer���ͣ������Ҫ�û���½
			score.setName(user.getUsername());
			score.setPlayer(user);
			score.setGame("�����ͻ");
			score.setPlayScore(10 + i);
			score.setSignScore(5+i);
			scores.add(score);
		}
		new BmobObject().insertBatch(this, scores, new SaveListener() {
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				toast("��������������ӳɹ�");
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("���������������ʧ��:" + msg);
			}
		});
	}

	private void testBmob(int pos) {
		switch (pos) {
		case 1:// ���㵥���е��ܺ�
			querySum();
			break;
		case 2:// ��������ܺ�
			querySumByGroup();
			break;
		case 3:// ������鲢�������е��ܺ�
			querySumsByGroups();
			break;
		case 4:// ��������ܺͲ������������������
			querySumByHaving();
			break;
		case 5:// ��������ܺͲ�����ÿ������ļ�¼��
			querySumByGroupCount();
			break;
		case 6:// ��ȡ���ظ�����ֵ
			queryScores();
			break;
		case 7:// ������ƽ��ֵ�������Сֵ��
			queryOthers();
			break;
		}
	}

	/**
	 * ��ѯ��Ϸ�ܷ�
	 * 
	 * @method querySum
	 * @params
	 * @return void
	 * @exception
	 */
	private void querySum() {
		BmobQuery<GameScore> query = new BmobQuery<GameScore>();
		query.sum(new String[] { "playScore" });
		query.findStatistics(this, GameScore.class,new FindStatisticsListener() {

					@Override
					public void onSuccess(Object object) {
						// TODO Auto-generated method stub
						JSONArray ary = (JSONArray) object;
						if(ary!=null){//
							try {
								JSONObject obj = ary.getJSONObject(0);
								int sum = obj.getInt("_sumPlayScore");
								showToast("��Ϸ�ܵ÷֣�" + sum);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}else{
							showToast("��ѯ�ɹ���������");
						}
					}

					@Override
					public void onFailure(int code, String msg) {
						// TODO Auto-generated method stub
						showToast("��ѯ����code =" + ",msg = " + msg);
					}
				});

	}

	/**
	 * ��������ܺ�
	 * 
	 * @method queryGroupSum
	 * @return void
	 * @exception
	 */
	private void querySumByGroup() {
		BmobQuery<GameScore> query = new BmobQuery<GameScore>();
		query.sum(new String[] { "playScore" });//����÷��ܺ�
		query.groupby(new String[] { "createdAt" });//����ʱ����з���
		query.order("-createdAt");// ��������
		query.setLimit(1000);
		query.findStatistics(this, GameScore.class,new FindStatisticsListener() {

					@Override
					public void onSuccess(Object object) {
						// TODO Auto-generated method stub
						JSONArray ary = (JSONArray) object;
						if(ary!=null){//
							int length = ary.length();
							try {
								for (int i = 0; i < length; i++) {
									JSONObject obj = ary.getJSONObject(i);
									int playscore = obj.getInt("_sumPlayScore");
									String createDate = obj.getString("createdAt");
									showToast("��Ϸ�ܵ÷֣�" + playscore + ",ʱ�䣺"
											+ createDate);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							showToast("��ѯ�ɹ���������");
						}
					}

					@Override
					public void onFailure(int code, String msg) {
						// TODO Auto-generated method stub
						showToast("��ѯ����code =" + ",msg = " + msg);
					}
				});
	}

	/**
	 * ������鲢�������ܺ�
	 * @method queryGroupSum
	 * @return void
	 * @exception
	 */
	private void querySumsByGroups() {
		BmobQuery<GameScore> query = new BmobQuery<GameScore>();
		query.sum(new String[] { "playScore", "signScore" });//�����е��ܺ�
		query.groupby(new String[] { "createdAt", "game" });//����ʱ�����Ϸ���з���
		query.order("-createdAt");//��������
		query.setLimit(100);
		query.findStatistics(this, GameScore.class,new FindStatisticsListener() {

					@Override
					public void onSuccess(Object object) {
						// TODO Auto-generated method stub
						JSONArray ary = (JSONArray) object;
						if(ary!=null){
							int length = ary.length();
							try {
								for (int i = 0; i < length; i++) {
									JSONObject obj = ary.getJSONObject(i);
									int playscore = obj.getInt("_sumPlayScore");
									int signscore = obj.getInt("_sumSignScore");
									String createDate = obj.getString("createdAt");
									String game = obj.getString("game");
									showToast("��Ϸ�ܵ÷֣�" + playscore + ",ǩ���÷֣�"
											+ signscore + ",ʱ��:" + createDate+",game:"+game);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							showToast("��ѯ�ɹ���������");
						}
					}

					@Override
					public void onFailure(int code, String msg) {
						// TODO Auto-generated method stub
						showToast("��ѯ����code =" + ",msg = " + msg);
					}
				});
	}

	/**
	 * ��������ܺͲ�ֻ�������������Ĳ���ֵ
	 * @method queryGroupSum
	 * @return void
	 * @exception
	 */
	private void querySumByHaving() {
		BmobQuery<GameScore> query = new BmobQuery<GameScore>();
		query.sum(new String[] {"playScore"});//�����ܵ÷���
		query.groupby(new String[] {"game"});//��������������Ϸ�����з���
		query.order("-createdAt");// ��������
		HashMap<String, Object> map = new HashMap<String, Object>();
		JSONObject js = new JSONObject();
		try {
			js.put("$gt", 150);
		} catch (JSONException e1) {
		}
		map.put("_sumPlayScore", js);//�����������ܵ÷�������150,ֻ�ܹ��ˣ�sum�ȵĲ�ѯ������
		query.having(map);
		query.addWhereGreaterThan("playScore", 10);//����where��ѯ�������÷�������10��
		query.setLimit(100);
		query.findStatistics(this, GameScore.class,new FindStatisticsListener() {

					@Override
					public void onSuccess(Object object) {
						// TODO Auto-generated method stub
						JSONArray ary = (JSONArray) object;
						if(ary!=null){
							int length = ary.length();
							try {
								for (int i = 0; i < length; i++) {
									JSONObject obj = ary.getJSONObject(i);
									int playscore = obj.getInt("_sumPlayScore");//����������key��ʲô�����ص������о���ʲô
									String game = obj.getString("game");
									showToast("��Ϸ�÷֣�" + playscore + ",��Ϸ�� = "+ game);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							showToast("��ѯ�ɹ���������");
						}
					}

					@Override
					public void onFailure(int code, String msg) {
						// TODO Auto-generated method stub
						showToast("��ѯ����code =" + ",msg = " + msg);
					}
				});
	}

	/**
	 * ��������ܺͲ�����ÿ������ļ�¼��
	 * @method queryGroupSum
	 * @return void
	 * @exception
	 */
	private void querySumByGroupCount() {
		// ��ѯ����ʱ�䰴��ͳ��������ҵĵ÷ֺ�ÿһ���ж�������ҵĵ÷ּ�¼������ʱ�併��:
		BmobQuery<GameScore> query = new BmobQuery<GameScore>();
		query.sum(new String[] { "playScore" });// ͳ���ܵ÷�
		query.groupby(new String[] { "createdAt" });// ����ʱ�����
		query.order("-createdAt");// ��������
		query.setHasGroupCount(true);// ͳ��ÿһ���ж�����ҵĵ÷ּ�¼��Ĭ�ϲ����ط������
		query.findStatistics(this, GameScore.class,new FindStatisticsListener() {

			@Override
			public void onSuccess(Object object) {
				// TODO Auto-generated method stub
				JSONArray ary = (JSONArray) object;
				if (ary!=null) {
					int length = ary.length();
					try {
						for (int i = 0; i < length; i++) {
							JSONObject obj = ary.getJSONObject(i);
							int playscore = obj.getInt("_sumPlayScore");
							String createDate = obj.getString("createdAt");
							int count = obj.getInt("_count");
							showToast("��Ϸ�ܵ÷֣�" + playscore + ",�ܹ�ͳ����"
									+ count + "����¼,ͳ��ʱ�� = "+ createDate);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					showToast("��ѯ�ɹ���������");
				}
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				showToast("��ѯ����code =" + ",msg = " + msg);
			}
		});
	}
	
	/**��ѯ���еĵ÷�  
	 * @method queryScore 
	 * @params    
	 * @return void  
	 * @exception   
	 */
	private void queryScores(){
		BmobQuery<GameScore> query = new BmobQuery<GameScore>();
		query.groupby(new String[]{"playScore"});
		query.order("-createdAt");
		query.findStatistics(this, GameScore.class, new FindStatisticsListener() {
			
			@Override
			public void onSuccess(Object result) {
				// TODO Auto-generated method stub
				JSONArray ary = (JSONArray) result;
				if (ary!=null) {
					int length = ary.length();
					try {
						for (int i = 0; i < length; i++) {
							JSONObject obj = ary.getJSONObject(i);
							String score = obj.getString("playScore");
							showLog("��Ϸ������" + score);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					showToast("��ѯ�ɹ���������");
				}
			}
			
			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				showToast("��ѯ����code =" + ",msg = " + msg);
			}
		});
	}
	
	/**��ѯ����
	 * @method queryOthers 
	 * @params    
	 * @return void  
	 * @exception   
	 */
	private void queryOthers(){
		BmobQuery<GameScore> query = new BmobQuery<GameScore>();
//		query.average(new String[]{"playScore"});//��ѯĳ�е�ƽ��ֵ
		query.min(new String[]{"playScore"});//��ѯ��Сֵ
//		query.max(new String[]{"playScore"});//��ѯ���ֵ
		query.groupby(new String[]{"createdAt"});
		query.findStatistics(this, GameScore.class, new FindStatisticsListener() {
			
			@Override
			public void onSuccess(Object result) {
				// TODO Auto-generated method stub
				JSONArray ary = (JSONArray) result;
				if (ary!=null) {
					try {
						JSONObject obj = ary.getJSONObject(0);
//						int playscore = obj.getInt("_avgPlayScore");
						int minscore = obj.getInt("_minPlayScore");
//						int maxscore = obj.getInt("_maxPlayScore");
						String createDate = obj.getString("createdAt");
						showToast("minscore = " + minscore+ ",ͳ��ʱ�� = "+ createDate);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					showToast("��ѯ�ɹ���������");
				}
			}
			
			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				showToast("��ѯ����code =" + ",msg = " + msg);
			}
		});
	}

}
