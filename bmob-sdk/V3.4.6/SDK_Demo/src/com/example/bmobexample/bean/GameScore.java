package com.example.bmobexample.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**  
 * ��Ϸ�÷�
 * @class  GameScore  
 * @author smile   
 * @date   2015-4-23 ����11:56:20  
 *   
 */
public class GameScore extends BmobObject {
	/**  
	 *  
	 */  
	private static final long serialVersionUID = 1L;
	
	public GameScore(){
		super();
	}
	
	public GameScore(String tableName){
		super(tableName);
	} 
	
	/**  
	 * ���
	 */  
	private MyUser player;
	
	/**  
	 * ����ǳ�--��ӦUser����û���
	 */  
	private String name;
	/**  
	 * ��Ϸ�÷�  
	 */  
	private Integer playScore;
	
	/**  
	 * ǩ���÷�
	 */  
	private Integer signScore;
	
	/**  
	 * ��Ϸ������������Ϸ��  
	 */  
	private String game;
	
	private BmobGeoPoint gps;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BmobGeoPoint getGps() {
		return gps;
	}

	public void setGps(BmobGeoPoint gps) {
		this.gps = gps;
	}

	public MyUser getPlayer() {
		return player;
	}

	public void setPlayer(MyUser player) {
		this.player = player;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public Integer getPlayScore() {
		return playScore;
	}
	
	public void setPlayScore(Integer playScore) {
		this.playScore = playScore;
	}
	
	public Integer getSignScore() {
		return signScore;
	}
	
	public void setSignScore(Integer signScore) {
		this.signScore = signScore;
	}
	
}
