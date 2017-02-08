package com.test.mahjong.gy.core;

import java.util.List;

/**
 * 听牌检查
 * @author Administrator
 *
 */
public class CheckTing {
	//是否需要查出所有听的牌
	private boolean findAll;
	
	//定缺（是否已确定所听的牌）
	private boolean dingque;
	//假设所听的牌的牌型
	private int type;
	//假设所听的牌的牌值
	private int value;
	
	private void check(int typeTmp,int[] array){
		
	}
	
	
	
	/**
	 * 是否听牌
	 * @param wan
	 * @param tiao
	 * @param tong
	 * @param returnListenerAll 是否返回所有正在听的牌
	 * @return
	 */
	public List<Integer> check(int[] wan, int[] tiao, int[] tong,boolean returnListenerAll){
		findAll = returnListenerAll;
		
		
		return null;
	}
	
	
}
