package com.test.mahjong.gy.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 杠
 * @author Administrator
 *
 */
public class Gang {
	/**
	 * 暗杠
	 */
	public static final int TYPE_AN = 0;
	/**
	 * 明杠
	 */
	public static final int TYPE_MING = 1;
	/**
	 * 转弯杠（碰后再摸到一张）
	 */
	public static final int TYPE_ZHUANG_WAN = 2;
	
	private int type;
	private Direction direction;
	private Mahjong mahjong;
	private int multiple;
	
	public Gang(Direction direction,int type,Mahjong mahjong){
		if(type != TYPE_AN && type != TYPE_MING && type != TYPE_ZHUANG_WAN){
			throw new IllegalStateException("杠的类型只有：0-暗杠，1-明杠，2-转弯杠");
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
	 * 获取杠的结算倍数（记得庄家还要再双倍哦）
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
	 * 获取所有杠的牌
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
