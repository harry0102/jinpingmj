package com.test.mahjong.gy.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MahjongManager {
	//一副麻将的总数
	private static final int MAHJONG_COUNT = 9*3*4;
	private List<Mahjong> mahjongs;
	private int index;
	private int lastIndex;
	
	public MahjongManager(){
		initMahjongs();
		index = -1;
		lastIndex = MAHJONG_COUNT -1;
	}
	
	/**
	 * 初始化
	 */
	private void initMahjongs(){
		mahjongs = new ArrayList<Mahjong>(MAHJONG_COUNT);
		for(int i = 0; i < 4; i++){
			for(Mahjong.Type type : Mahjong.Type.values()){
				for(Mahjong.Value value : Mahjong.Value.values()){
					mahjongs.add(new Mahjong(type, value));
				}
			}
		}
	}
	
	/**
	 * 洗牌
	 */
	public synchronized void shuffle(){
		Collections.shuffle(mahjongs);
		index = -1;
		lastIndex = MAHJONG_COUNT -1;
	}
	
	/**
	 * 还剩下多少牌
	 * @return
	 */
	public int remainder(){
		return lastIndex - index + 1;
	}
	
	/**
	 * 顺摸一张牌
	 * @return
	 */
	public synchronized Mahjong next(){
		index++; 
		checkIndexOutOfBounds();
		return mahjongs.get(index);
	}
	
	/**
	 * 逆摸一张牌
	 * @return
	 */
	public synchronized Mahjong last(){
		lastIndex--;
		checkIndexOutOfBounds();
		return mahjongs.get(lastIndex);
	}
	/**
	 * 检查取牌是否越界
	 */
	private void checkIndexOutOfBounds(){
		if(index >= lastIndex){
			throw new IndexOutOfBoundsException("过界");
		}
	}
	
	
	//-------------------------------- 以下为辅助代码
	
	//获取一副天听的手牌
	public List<Mahjong> getTianTing(){
		List<Mahjong> mahjongs = new ArrayList<Mahjong>(MAHJONG_COUNT);
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v1));
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v1));
		
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v2));
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v3));
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v4));
		
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v2));
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v3));
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v4));
		
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v5));
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v6));
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v7));
		
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v5));
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v6));
		
		return mahjongs;
	}
	
	//获取天胡的牌
	public Mahjong getTianHu(){
		return new Mahjong(Mahjong.Type.w, Mahjong.Value.v7);
	}
	
	public void print(){
		for(int i = 0; i < mahjongs.size();i++){
			if(i!=0 && i%9 ==0){
				System.out.println();
			}
			System.out.print(mahjongs.get(i));
		}
	}
}
