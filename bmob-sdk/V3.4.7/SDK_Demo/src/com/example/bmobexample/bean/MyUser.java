package com.example.bmobexample.bean;

import java.util.List;

import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser {
	
	/**  
	 *  
	 */  
	
	private static final long serialVersionUID = 1L;
	private Integer age;
	private Integer num;
	private Boolean sex;
	
	private List<String> hobby;		// ��Ӧ�����Array���ͣ�String���͵ļ���
	private List<BankCard> cards;	// ��Ӧ�����Array����:Object���͵ļ���
	
	private BankCard mainCard;      //����
	private Person banker;          //���й�����Ա
	
	public Boolean getSex() {
		return sex;
	}
	public void setSex(Boolean sex) {
		this.sex = sex;
	}
	public List<String> getHobby() {
		return hobby;
	}
	public void setHobby(List<String> hobby) {
		this.hobby = hobby;
	}
	public List<BankCard> getCards() {
		return cards;
	}
	public void setCards(List<BankCard> cards) {
		this.cards = cards;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	public BankCard getMainCard() {
		return mainCard;
	}
	public void setMainCard(BankCard mainCard) {
		this.mainCard = mainCard;
	}
	public Person getBanker() {
		return banker;
	}
	public void setBanker(Person banker) {
		this.banker = banker;
	}
	
}
