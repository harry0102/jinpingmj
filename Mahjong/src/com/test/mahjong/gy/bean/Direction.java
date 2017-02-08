package com.test.mahjong.gy.bean;

/**
 * 方位：东西南北
 * @author Administrator
 *
 */
public enum Direction{
	e("东"),w("西"),s("南"),n("北");
	String name;
	
	Direction(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
