package com.test.mahjong.gy.core;

import java.util.Scanner;

public abstract class Room {
	/**
	 * 游戏主持人
	 */
	private Presenter presenter;
	
	/**
	 * 房间配置
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
	 * 获取房间配置
	 * @return
	 */
	public Config getConfig(){
		return config;
	}
	
	/**
	 * 销毁房间
	 */
	public abstract void destroy();
	
	/**
	 * 房间配置参数
	 * @author Administrator
	 *
	 */
	public static class Config{
		/**
		 * 是否满堂鸡：打出去的也算鸡
		 */
		private boolean manTangJi;
		/**
		 * 乌骨鸡：八筒是否为鸡
		 */
		private boolean wuGuJi;
		/**
		 * 摇摆鸡：所翻的牌的上一个和下一个都为鸡，不是翻牌鸡则为翻牌鸡（翻牌鸡：所翻的牌的下一个牌为鸡）。
		 */
		private boolean yaoBaiJi;
		/**
		 * 金鸡：翻鸡时，所翻的牌为九条，则玩家手上的幺鸡为金鸡（如果设置满堂鸡则打出去的也算，否则打出去的不算），金鸡一个算三个
		 */
		private boolean jinJi;
		
		/**
		 * 房卡数：开房间使用的房卡数量
		 */
		private int fangKa;
		/**
		 * 玩法：4、3、2人
		 */
		private WanFa wanFa;
		
		/**
		 * 默认为锦屏玩法
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
		 * 是否支持满堂鸡
		 * @return
		 */
		public boolean isManTangJi() {
			return manTangJi;
		}

		/**
		 * 设置是否满堂鸡
		 * @param manTangJi
		 */
		public void setManTangJi(boolean manTangJi) {
			this.manTangJi = manTangJi;
		}
		
		/**
		 * 是否支持乌骨鸡
		 * @return
		 */
		public boolean isWuGuJi() {
			return wuGuJi;
		}
		/**
		 * 设置是否支持乌骨鸡
		 * @param wuGuJi
		 */
		public void setWuGuJi(boolean wuGuJi) {
			this.wuGuJi = wuGuJi;
		}
		
		/**
		 * 是否支持摇摆鸡
		 * @return
		 */
		public boolean isYaoBaiJi() {
			return yaoBaiJi;
		}
		
		/**
		 * 设置是否支持摇摆鸡
		 * @param yaoBaiJi
		 */
		public void setYaoBaiJi(boolean yaoBaiJi) {
			this.yaoBaiJi = yaoBaiJi;
		}
		
		/**
		 * 是否支持金鸡
		 * @return
		 */
		public boolean isJinJi() {
			return jinJi;
		}
		
		/**
		 * 设置是否支持金鸡
		 * @param jinJi
		 */
		public void setJinJi(boolean jinJi) {
			this.jinJi = jinJi;
		}
		
		/**
		 * 获取开放使用的房卡，在第一局结算的时候扣除，第一局未结束就解散则不扣除房卡
		 * @return
		 */
		public int getFangKa() {
			return fangKa;
		}
		
		/**
		 * 获取游戏局数（局数=使用的房卡数量*3+使用的房卡数量）
		 * @return
		 */
		public int getJushu(){
			return fangKa * 3 + fangKa;
		}
		
		/**
		 * 设置游戏玩法
		 * @param wanfa
		 */
		public void setWanFa(int wanfa){
			WanFa wf = WanFa.getWanFaByVal(wanfa);
			if(wf == null){
				throw new IllegalStateException("目前没有 " + wanfa + "人一起的这种玩法，目前支持的玩法有：四人局，三丁拐，二丁拐");
			}
			setWanFa(wf);
		}
		
		/**
		 * 设置游戏玩法
		 * @param wanfa
		 */
		public void setWanFa(WanFa wanfa){
			this.wanFa = wanfa;
		}
		
		/**
		 * 获取玩法
		 * @return
		 */
		public WanFa getWanFa(){
			return wanFa;
		}
		
		
		/**
		 * 玩法枚举:几个人玩
		 * @author Administrator
		 *
		 */
		public static enum WanFa{
			/**
			 * 四人局
			 */
			SI(4),
			/**
			 * 三丁拐
			 */
			SAN(3),
			/**
			 * 二丁拐
			 */
			ER(2);
			private int val;
			
			/**
			 * 获取玩家数量
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
