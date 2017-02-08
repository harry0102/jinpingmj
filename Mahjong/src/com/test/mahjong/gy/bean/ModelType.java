package com.test.mahjong.gy.bean;
/**
 * 胡牌类型
 * @author Administrator
 *
 */
public enum ModelType {
	// 类别（序号，番数，描述）
	/**
	 * 没听牌或没胡牌
	 */
	NONE(0,0,"未听/胡牌"),
	/**
	 * 平胡
	 */
	PING(1,1,"平胡"),
	/**
	 * 大对子
	 */
	DA_DUI(2,3,"大对子"),
	/**
	 * 小七对
	 */
	XIAO_QI_DUI(3,3,"小七对"),
	/**
	 * 龙七对
	 */
	LONG_QI_DUI(4,4,"龙七对"),
	/**
	 * 清一色
	 */
	QING_YI_SE(5,4,"清一色"),
	/**
	 * 清大对
	 */
	QING_DUI(6,4*3,"清大对"),
	/**
	 * 清七对
	 */
	QING_QI_DUI(7,4*3,"清七对"),
	/**
	 * 青龙背
	 */
	QING_LONG(8,4*4,"青龙背"),
	/**
	 * 天听
	 */
	TIAN_TING(9,4,"天听"),
	/**
	 * 地胡（闲家摸第一张牌胡牌）
	 */
	DI_HU(10,8,"地胡"),
	/**
	 * 天胡（庄家摸第一张牌胡牌）
	 */
	TIAN_HU(11,8,"天胡")
	;
	
	int id;
	int multiple;
	String text;
	
	ModelType(int id,int multiple,String text){
		this.id = id;
		this.multiple = multiple;
		this.text = text;
	}
	
	public int getId(){
		return id;
	}
	
	public int getMultiple() {
		return multiple;
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	public static ModelType getModelTypeById(int id){
		for(ModelType mt : ModelType.values()){
			if(mt.id == id){
				return mt;
			}
		}
		return null;
	}
}
