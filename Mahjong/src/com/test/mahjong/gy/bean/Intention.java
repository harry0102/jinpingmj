package com.test.mahjong.gy.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * ��ͼ
 * @author Administrator
 *
 */
public enum Intention {
	/**
	 * ��
	 */
	NONE(0,"��"),
	/**
	 * ���ƣ�����Ҫ��ʾ�û�ѡ���ѡ��
	 */
	MOPAI(1,""),
	/**
	 * ���ƣ�����Ҫ��ʾ�û�ѡ���ѡ��
	 */
	CHUPAI(2,""),
	/**
	 * ����
	 */
	TING(3,"��"),
	/**
	 * ��
	 */
	PENG(4,"��"),
	/**
	 * ��
	 */
	GANG(5,"��"),
	/**
	 * ��
	 */
	HU(6,"��");
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
	 * ��ӡ���û�ѡ�����ͼѡ��
	 * @param location
	 * @param intentions
	 */
	public static void printIntentions(Location location, List<Intention> intentions){
		if(intentions == null || intentions.size() == 0){
			return;
		}
		String name = location.getDirection().getName();
		StringBuffer sb = new StringBuffer("��� -> "+name+" ��������ͼ��Ӧ�����֣�");
		for(Intention intention : intentions){
			sb.append(intention.getValue()).append("-").append(intention.getText()).append(" ");
		}
		System.out.println(sb.toString());
	}
	/**
	 * ��ȡ�û�ѡ�����ͼ
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
					throw new IllegalStateException("û�����ѡ��");
				}
				break;
			}catch(Exception e){
				System.out.println("����������������룺");
			}
		}
		return intention;
	}
	
}
