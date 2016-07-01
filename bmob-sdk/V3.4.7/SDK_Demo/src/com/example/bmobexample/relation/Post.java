package com.example.bmobexample.relation;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

import com.example.bmobexample.bean.MyUser;
/**
 * 
 * @ClassName: ����
 * @Description: ����ʵ��
 * @author smile
 * @date 2014��4��17�� ����11:10:44
 *
 */
public class Post extends BmobObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**  
	 *  ���ӱ���
	 */  
	private String title;
	
	/**  
	 *  ��������
	 */  
	private String content;
	
	/**  
	 *  ΢��������
	 */
	private MyUser author;
	/**  
	 *  ΢��ͼƬ
	 */
	private BmobFile image;
	
	/**  
	 *  һ�Զ��ϵ�����ڴ洢ϲ�������ӵ������û�
	 */  
	private BmobRelation likes;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public BmobRelation getLikes() {
		return likes;
	}
	public void setLikes(BmobRelation likes) {
		this.likes = likes;
	}
	public BmobFile getImage() {
		return image;
	}
	public void setImage(BmobFile image) {
		this.image = image;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public MyUser getAuthor() {
		return author;
	}
	public void setAuthor(MyUser author) {
		this.author = author;
	}
	
//	/**
//	 * ΢�������ۣ�һ��΢���Ƕ�Ӧ�������۵ģ�������һ�Զ�����Σ���ʹ��BmobRelation����
//	 */
//	private BmobRelation comment;
	
//	public BmobRelation getComment() {
//		return comment;
//	}
//	public void setComment(BmobRelation comment) {
//		this.comment = comment;
//	}
}
