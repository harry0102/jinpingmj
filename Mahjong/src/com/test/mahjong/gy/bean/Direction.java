package com.test.mahjong.gy.bean;

/**
 * ��λ�������ϱ�
 * @author Administrator
 *
 */
public enum Direction{
	e("��"),w("��"),s("��"),n("��");
	String name;
	
	Direction(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
