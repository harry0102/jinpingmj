package com.test.mahjong.gy.core;

import java.util.Scanner;

public abstract class Room {
	/**
	 * ��Ϸ������
	 */
	private Presenter presenter;
	
	/**
	 * ��������
	 */
	private Config config;
	
	public Room(Config config){
		this.config = config;
		Scanner scanner = new Scanner(System.in);
		presenter = new Presenter(this,scanner);
	}
	
	public void startGame(){
		presenter.startGame();
	}
	
	/**
	 * ��ȡ��������
	 * @return
	 */
	public Config getConfig(){
		return config;
	}
	
	/**
	 * ���ٷ���
	 */
	public abstract void destroy();
	
	/**
	 * �������ò���
	 * @author Administrator
	 *
	 */
	public static class Config{
		/**
		 * �Ƿ����ü������ȥ��Ҳ�㼦
		 */
		private boolean manTangJi;
		/**
		 * �ڹǼ�����Ͳ�Ƿ�Ϊ��
		 */
		private boolean wuGuJi;
		/**
		 * ҡ�ڼ����������Ƶ���һ������һ����Ϊ�������Ƿ��Ƽ���Ϊ���Ƽ������Ƽ����������Ƶ���һ����Ϊ������
		 */
		private boolean yaoBaiJi;
		/**
		 * �𼦣�����ʱ����������Ϊ��������������ϵ��ۼ�Ϊ�𼦣�����������ü�����ȥ��Ҳ�㣬������ȥ�Ĳ��㣩����һ��������
		 */
		private boolean jinJi;
		
		/**
		 * ��������������ʹ�õķ�������
		 */
		private int fangKa;
		/**
		 * �淨��4��3��2��
		 */
		private WanFa wanFa;
		
		/**
		 * Ĭ��Ϊ�����淨
		 * @param fangka
		 */
		public Config(int fangka){
			this.fangKa = fangka;
			manTangJi = false;
			wuGuJi = false;
			yaoBaiJi = false;
			jinJi = true;
		}
		
		/**
		 * �Ƿ�֧�����ü�
		 * @return
		 */
		public boolean isManTangJi() {
			return manTangJi;
		}

		/**
		 * �����Ƿ����ü�
		 * @param manTangJi
		 */
		public void setManTangJi(boolean manTangJi) {
			this.manTangJi = manTangJi;
		}
		
		/**
		 * �Ƿ�֧���ڹǼ�
		 * @return
		 */
		public boolean isWuGuJi() {
			return wuGuJi;
		}
		/**
		 * �����Ƿ�֧���ڹǼ�
		 * @param wuGuJi
		 */
		public void setWuGuJi(boolean wuGuJi) {
			this.wuGuJi = wuGuJi;
		}
		
		/**
		 * �Ƿ�֧��ҡ�ڼ�
		 * @return
		 */
		public boolean isYaoBaiJi() {
			return yaoBaiJi;
		}
		
		/**
		 * �����Ƿ�֧��ҡ�ڼ�
		 * @param yaoBaiJi
		 */
		public void setYaoBaiJi(boolean yaoBaiJi) {
			this.yaoBaiJi = yaoBaiJi;
		}
		
		/**
		 * �Ƿ�֧�ֽ�
		 * @return
		 */
		public boolean isJinJi() {
			return jinJi;
		}
		
		/**
		 * �����Ƿ�֧�ֽ�
		 * @param jinJi
		 */
		public void setJinJi(boolean jinJi) {
			this.jinJi = jinJi;
		}
		
		/**
		 * ��ȡ����ʹ�õķ������ڵ�һ�ֽ����ʱ��۳�����һ��δ�����ͽ�ɢ�򲻿۳�����
		 * @return
		 */
		public int getFangKa() {
			return fangKa;
		}
		
		/**
		 * ��ȡ��Ϸ����������=ʹ�õķ�������*3+ʹ�õķ���������
		 * @return
		 */
		public int getJushu(){
			return fangKa * 3 + fangKa;
		}
		
		/**
		 * ������Ϸ�淨
		 * @param wanfa
		 */
		public void setWanFa(int wanfa){
			WanFa wf = WanFa.getWanFaByVal(wanfa);
			if(wf == null){
				throw new IllegalStateException("Ŀǰû�� " + wanfa + "��һ��������淨��Ŀǰ֧�ֵ��淨�У����˾֣������գ�������");
			}
			setWanFa(wf);
		}
		
		/**
		 * ������Ϸ�淨
		 * @param wanfa
		 */
		public void setWanFa(WanFa wanfa){
			this.wanFa = wanfa;
		}
		
		/**
		 * ��ȡ�淨
		 * @return
		 */
		public WanFa getWanFa(){
			return wanFa;
		}
		
		
		/**
		 * �淨ö��:��������
		 * @author Administrator
		 *
		 */
		public static enum WanFa{
			/**
			 * ���˾�
			 */
			SI(4),
			/**
			 * ������
			 */
			SAN(3),
			/**
			 * ������
			 */
			ER(2);
			private int val;
			
			/**
			 * ��ȡ�������
			 * @return
			 */
			public int getVal(){
				return val;
			}
			
			WanFa(int val){
				this.val = val;
			}
			
			public static final WanFa getWanFaByVal(int val){
				for(WanFa wf : WanFa.values()){
					if(wf.getVal() == val){
						return wf;
					}
				}
				return null;
			}
			
		}
	}
}
