package com.test.mahjong.gy.bean;

/**
 * �¼�
 * @author Administrator
 *
 */
public class Action {
	//���¼���ص���
	private Mahjong mahjong;
	//�¼�����(�ѳ���ʵ����ͼ)
	private Intention intention;
	//˭��������
	private Direction direction;
	//�Ƿ�ܺ�����ƣ��������ڣ�
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
