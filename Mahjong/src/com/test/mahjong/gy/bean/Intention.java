package com.test.mahjong.gy.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 意图
 * @author Administrator
 *
 */
public enum Intention {
	/**
	 * 过
	 */
	NONE(0,"过"),
	/**
	 * 摸牌，不需要提示用户选择的选项
	 */
	MOPAI(1,""),
	/**
	 * 出牌，不需要提示用户选择的选项
	 */
	CHUPAI(2,""),
	/**
	 * 天听
	 */
	TING(3,"听"),
	/**
	 * 碰
	 */
	PENG(4,"碰"),
	/**
	 * 杠
	 */
	GANG(5,"杠"),
	/**
	 * 胡
	 */
	HU(6,"胡");
	Intention(int v,String text){
		this.v = v;
		this.text = text;
	}
	
	int v;
	String text;
	
	public int getValue(){
		return v;
	}
	
	public String getText(){
		return text;
	}
	
	public static Intention getIntentionById(int id){
		for(Intention i : Intention.values()){
			if(i.getValue() == id){
				return i;
			}
		}
		
		return null;
	}
	
	public static List<Intention> makeIntentions(Intention... intentions){
		if(intentions == null || intentions.length == 0){
			return null;
		}
		List<Intention> list = new ArrayList<Intention>();
		for(Intention Intention: intentions){
			list.add(Intention);
		}
		list.add(Intention.NONE);
		return list;
	}
	/**
	 * 打印给用户选择的意图选项
	 * @param location
	 * @param intentions
	 */
	public static void printIntentions(Location location, List<Intention> intentions){
		if(intentions == null || intentions.size() == 0){
			return;
		}
		String name = location.getDirection().getName();
		StringBuffer sb = new StringBuffer("玩家 -> "+name+" 请输入意图对应的数字：");
		for(Intention intention : intentions){
			sb.append(intention.getValue()).append("-").append(intention.getText()).append(" ");
		}
		System.out.println(sb.toString());
	}
	/**
	 * 获取用户选择的意图
	 * @param scanner
	 * @param intentions
	 * @return
	 */
	public static Intention getSelectedIntention(Scanner scanner,List<Intention> intentions){
		Intention intention = null;
		while(true){
			try{
				int in = scanner.nextInt();
				intention = Intention.getIntentionById(in);
				if(!intentions.contains(intention)){
					throw new IllegalStateException("没有这个选项");
				}
				break;
			}catch(Exception e){
				System.out.println("输入错误，请重新输入：");
			}
		}
		return intention;
	}
	
}
