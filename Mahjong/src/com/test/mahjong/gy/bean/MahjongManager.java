package com.test.mahjong.gy.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MahjongManager {
	//һ���齫������
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
	 * ��ʼ��
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
	 * ϴ��
	 */
	public synchronized void shuffle(){
		Collections.shuffle(mahjongs);
		index = -1;
		lastIndex = MAHJONG_COUNT -1;
	}
	
	/**
	 * ��ʣ�¶�����
	 * @return
	 */
	public int remainder(){
		return lastIndex - index + 1;
	}
	
	/**
	 * ˳��һ����
	 * @return
	 */
	public synchronized Mahjong next(){
		index++; 
		checkIndexOutOfBounds();
		return mahjongs.get(index);
	}
	
	/**
	 * ����һ����
	 * @return
	 */
	public synchronized Mahjong last(){
		lastIndex--;
		checkIndexOutOfBounds();
		return mahjongs.get(lastIndex);
	}
	/**
	 * ���ȡ���Ƿ�Խ��
	 */
	private void checkIndexOutOfBounds(){
		if(index >= lastIndex){
			throw new IndexOutOfBoundsException("����");
		}
	}
	
	
	//-------------------------------- ����Ϊ��������
	
	//��ȡһ������������
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
	
	//��ȡ�������
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
