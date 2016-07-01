package com.example.bmobexample.relation;

import com.example.bmobexample.bean.MyUser;

import cn.bmob.v3.BmobObject;

/**
 * 
 * @ClassName: Comment
 * @Description: ����ʵ��
 * @author smile
 * @date 2014��4��17�� ����11:29:41
 *
 */
public class Comment extends BmobObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**  
	 * ��������  
	 */  
	private String content;
	
	/**  
	 * ���۵��û�
	 */  
	private MyUser user;
	
	/**  
	 *  �����۵�����
	 */  
	private Post post; //һ������ֻ������һ��΢��
	
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public MyUser getUser() {
		return user;
	}
	public void setUser(MyUser user) {
		this.user = user;
	}

}
