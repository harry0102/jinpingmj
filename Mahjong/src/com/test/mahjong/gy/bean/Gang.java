package com.test.mahjong.gy.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * ��
 * @author Administrator
 *
 */
public class Gang {
	/**
	 * ����
	 */
	public static final int TYPE_AN = 0;
	/**
	 * ����
	 */
	public static final int TYPE_MING = 1;
	/**
	 * ת��ܣ�����������һ�ţ�
	 */
	public static final int TYPE_ZHUANG_WAN = 2;
	
	private int type;
	private Direction direction;
	private Mahjong mahjong;
	private int multiple;
	
	public Gang(Direction direction,int type,Mahjong mahjong){
		if(type != TYPE_AN && type != TYPE_MING && type != TYPE_ZHUANG_WAN){
			throw new IllegalStateException("�ܵ�����ֻ�У�0-���ܣ�1-���ܣ�2-ת���");
		}
		this.type = type;
		this.direction = direction;
		this.mahjong = mahjong;
	}

	public static int getTypeAn() {
		return TYPE_AN;
	}

	public static int getTypeMing() {
		return TYPE_MING;
	}

	public static int getTypeZhuangWan() {
		return TYPE_ZHUANG_WAN;
	}

	public int getType() {
		return type;
	}

	public Direction getDirection() {
		return direction;
	}

	public Mahjong getMahjong() {
		return mahjong;
	}
	/**
	 * ��ȡ�ܵĽ��㱶�����ǵ�ׯ�һ�Ҫ��˫��Ŷ��
	 * @return
	 */
	public int getMultiple() {
		if(type == TYPE_AN){
			multiple = 2;
		}else{
			multiple = 1;
		}		
		return multiple;
	}
	/**
	 * ��ȡ���иܵ���
	 * @param gangs
	 * @return
	 */
	public static List<Mahjong> getGangMahjong(List<Gang> gangs){
		if(gangs == null || gangs.size() == 0){
			return null;
		}
		List<Mahjong> mahjongs = new ArrayList<Mahjong>();
		for(Gang gang : gangs){
			mahjongs.add(gang.getMahjong());
		}
		return mahjongs;
	}
}
