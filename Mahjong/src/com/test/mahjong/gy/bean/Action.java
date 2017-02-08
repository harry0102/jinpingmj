package com.test.mahjong.gy.bean;

/**
 * 事件
 * @author Administrator
 *
 */
public class Action {
	//该事件相关的牌
	private Mahjong mahjong;
	//事件描述(已成事实的意图)
	private Intention intention;
	//谁是责任者
	private Direction direction;
	//是否杠后出的牌（可以热炮）
	private boolean repao;
	
	public Action(Direction direction,Mahjong mahjong,Intention intention,boolean repao){
		this.direction = direction;
		this.mahjong = mahjong;
		this.intention = intention;
		this.repao = repao;
	}
	
	public Mahjong getMahjong(){
		return mahjong;
	}
	
	public Intention getIntention(){
		return intention;
	}
	
	public Direction getDirection(){
		return direction;
	}
}
