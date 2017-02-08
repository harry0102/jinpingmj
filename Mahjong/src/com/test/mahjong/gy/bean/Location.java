package com.test.mahjong.gy.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import com.test.mahjong.gy.core.CheckHu;
import com.test.mahjong.gy.core.CheckHuUtils;


/**
 * 方向
 * @author Administrator
 *
 */
public class Location {
	/**
	 * 玩家位置
	 */
	private Direction direction;
	/**
	 * 手上还剩下的牌
	 */
	private List<Mahjong> mahjongs;
	/**
	 * 碰了的牌
	 */
	private List<Mahjong> peng;
	/**
	 * 杠了的牌
	 */
	private List<Gang> gangs;
	/**
	 * 当前摸的牌
	 */
	private Mahjong currentMahjong;
	
	/**
	 * 已经打出去的牌
	 */
	private List<Mahjong> playedMahjongs;
	
	private Scanner scanner;
	/**
	 * 刚刚杠了一手牌
	 */
	private boolean gang;
	/**
	 * 打第一张牌（判断天听），并：如果这个为true的时候胡牌，则为天胡
	 */
	private boolean firstPlay;
	/**
	 * 是否天听
	 */
	private boolean tianTing;
	
	//分数
	private int score;
	
	public Location(Direction direction,Scanner scanner){
		this.direction = direction;
		this.scanner = scanner;
		this.gangs = new ArrayList<Gang>();
		this.playedMahjongs = new ArrayList<Mahjong>(); 
	}
	
	/**
	 * 开局，初始化各属性
	 */
	public void init(){
		gang = false;
		currentMahjong = null;
		peng = null;
		gangs.clear();;
		mahjongs = null;
		firstPlay = true;
		tianTing = false;
		clearPlayedMahjongs();
	}
	
	/**
	 * 已经打过的牌
	 * @param mahjong
	 */
	public void addPlayedMahjongs(Mahjong mahjong){
		playedMahjongs.add(mahjong);
	}
	
	/**
	 * 清除已打出去 的牌
	 */
	public void clearPlayedMahjongs(){
		playedMahjongs.containsAll(playedMahjongs);
	}
	
	/**
	 * 是不是天听
	 * @return
	 */
	public boolean isTianTing(){
		return tianTing;
	}
	
	/**
	 * 是否刚杠过
	 * @return
	 */
	public boolean isGang(){
		return gang;
	}
	
	/**
	 * 取消刚杠过的标记
	 */
	public void cancelGang(){
		this.gang = false;
	}
	
	/**
	 * 获取位置
	 * @return
	 */
	public Direction getDirection(){
		return direction;
	}
	
	/**
	 * 获取剩下的手牌
	 * @return
	 */
	public List<Mahjong> getMahjongs(){
		return mahjongs;
	}
	
	/**
	 * 获取已碰的牌
	 * @return
	 */
	public List<Mahjong> getPeng(){
		return peng;
	}
	
	
	/**
	 * 获取所有杠的牌 （暗杠不显示给其他玩家看，游戏结束时才显示）
	 * @return
	 */
	private List<Gang> getGang(){
		return gangs;
	}

	/**
	 * 初始发牌
	 * @param mahjongs
	 */
	public synchronized void setMahjongs(List<Mahjong> mahjongs){
		if(this.mahjongs == null){
			this.mahjongs = mahjongs;
		}else{
			this.mahjongs.containsAll(mahjongs);
			this.mahjongs.addAll(mahjongs);
		}
		sort();
	}
	
	
	/**
	 * 摸一张牌,返回可以胡或者杠的意图，如果都没有则调用出牌
	 * @param mahjong
	 */
	public synchronized List<Intention> distribute(Action action){
		System.out.println("当前玩家 -> " + direction.name + " 摸的牌：" + action.getMahjong());
//		printMahjongs();
		currentMahjong = action.getMahjong();
		return getIntention(action);
	}
	
	/**
	 * 将摸的牌插入手牌中
	 * @param mahjong
	 */
	private synchronized void putInMahjong(){
		mahjongs.add(currentMahjong);
		currentMahjong = null;
		Collections.sort(mahjongs);
	}
	
	/**
	 * 出牌
	 * @return
	 */
	public Action play(){
		printMahjongs();
		System.out.println( "玩家 ->" + direction.getName() +  "请选择你要出的牌：");
		int index = -1;
		Mahjong remove;
		while(true){
			try{
				index = scanner.nextInt();		
				if(index < 0){
					throw new IndexOutOfBoundsException("下标越界");
				}
				if(currentMahjong == null){
					//非摸牌出牌
					if(index >= mahjongs.size()){
						throw new IndexOutOfBoundsException("下标越界");
					}
					
					//打出去的是手上的牌
					remove = mahjongs.remove(index);
					
				}else{
					//是摸牌再出牌
					if(index > mahjongs.size()){
						throw new IndexOutOfBoundsException("下标越界");
					}
					
					if(index == mahjongs.size()){
						//打出去的是当前摸的牌								
						remove = currentMahjong;
						currentMahjong = null;
					}else{
						//打出去的是手上的牌
						remove = mahjongs.remove(index);
						putInMahjong();
					}
				}
				
				break;
			}catch(Exception e){
				System.out.println("输入错误，请重新输入：");
			}
		}
		if(firstPlay){
			//打出第一张牌的时候，判断是否听牌，听牌则给用户是否报天听
			//（报了天听，不能再换牌哦，只能等胡，不需要有通行证也可以接炮）
			// TODO
			ModelType modelType = CheckHuUtils.checkTing(peng, Gang.getGangMahjong(gangs), mahjongs, false);
			if(modelType != ModelType.NONE){
				//给出是否要报天听的选项
				List<Intention> intentions = Intention.makeIntentions(Intention.TING);
				Intention.printIntentions(this, intentions);
				Intention intention = Intention.getSelectedIntention(scanner, intentions);
				if(intention != Intention.NONE){
					//选择了天听
					Action action = new Action(direction,remove,Intention.TING,false/** 这里前面不可能有杠，所有直接false */);
					handlerFromMineIntention(intention, action);
				}
			}
		}
		firstPlay = false;
		System.out.println("玩家 ->" + direction.getName() + "出牌： " + remove);
//		printMahjongs();
		return new Action(direction, remove, Intention.CHUPAI,gang);
	}
	
	/**
	 * 传入一个事件，判断自己手上的牌是否能对该事件作出反应,
	 * 如果不能则返回null，否则返回可以做出的反应集
	 * @param mahjong
	 * @return
	 */
	public List<Intention> getAction(Action action){		
		return getIntention(action);
	}
	
	/**
	 * 处理来自自己摸牌时(含摸牌了打出去一张牌后判断是否天听)的意图
	 * @param intention
	 * @param mahjong
	 * @return 返回是否本局结束（是否胡牌）
	 */
	public synchronized boolean handlerFromMineIntention(Intention intention,Action action){
		try{
			Mahjong mahjong = action.getMahjong();
			if(intention == Intention.HU){
				return true;
			}else if(intention == Intention.TING){
				//报天听
				tianTing = true;
				return false;
			}else{
				firstPlay = false;
				//剩下的就是杠了			
				if(intention == Intention.GANG){
					//暗杠
					gangs.add(new Gang(direction,Gang.TYPE_AN,mahjong));
				}else{
					//转弯杠
					gangs.add(new Gang(direction,Gang.TYPE_ZHUANG_WAN,mahjong));
					if(peng != null){
						peng.remove(mahjong);
					}else{
						throw new IllegalStateException("错误，没有碰就不会有转弯杠");
					}
				}
				while(mahjongs.contains(mahjong)){
					mahjongs.remove(mahjong);
				}
				printMahjongs();
				return false;
			}
		}finally{
			printMahjongs();
		}
		
	}
	
	/**
	 * 处理来自别人出牌时的意图
	 * @param intention
	 * @param mahjong
	 * @return 返回是否本局结束 （是否胡牌）
	 */
	public synchronized boolean handlerFromOtherIntention(Intention intention,Action action){
		try{
			Mahjong mahjong = action.getMahjong();
			if(intention == Intention.HU){
				return true;
			}else if(intention == Intention.GANG){
				firstPlay = false;
				gangs.add(new Gang(action.getDirection(),Gang.TYPE_MING,action.getMahjong()));
				while(mahjongs.contains(mahjong)){
					mahjongs.remove(mahjong);
				}
				sort();
				return false;
			}else/* if(intention == Intention.PENG)*/{
				firstPlay = false;
				//剩下的就是碰了
				if(peng == null){
					peng = new ArrayList<Mahjong>();
				}
				peng.add(mahjong);
				int i = 0;
				while(mahjongs.contains(mahjong)){
					mahjongs.remove(mahjong);
					i++;
					if(i == 2){
						break;
					}
				}
				sort();
				return false;
			}
		}finally{
			printMahjongs();
		}
	}
	
	/**
	 * 对手上的牌排序
	 */
	private void sort(){
		if(mahjongs != null){
			Collections.sort(mahjongs);
		}
	}
	
	/**
	 * 判断一副牌是否能碰、胡等等
	 * @param mahjongs
	 * @param action
	 * @return
	 */
	public synchronized List<Intention> getIntention(Action action){	
		List<Intention> intentions = new ArrayList<Intention>();
		intentions.add(Intention.NONE);
		
		List<Mahjong> count = Count(action);
		if(count.size() >= 2){
			if(canPeng(action)){
				intentions.add(Intention.PENG);
			}
			if(count.size() == 3){
				Intention canGang = canGang(action);
				if(canGang != Intention.NONE){
					intentions.add(canGang);
				}
			}
		}
		
		//TODO 判断是否可胡
		
		return intentions.size() == 1 ? null : intentions;
	}
	
	
	/**
	 * 有几张牌跟当前事件相关的牌是一样的
	 * @return
	 */
	private List<Mahjong> Count(Action action){
		List<Mahjong> count = new ArrayList<Mahjong>();
		for(Mahjong mahjong: mahjongs){
			if(mahjong.equals(action.getMahjong())){
				count.add(mahjong);
				if(count.size() == 3){
					break;
				}
			}			
		}
		return count;
	}
	
	/**
	 * 是否可以碰
	 * @return
	 */
	private boolean canPeng(Action action){
		if(actionForMine(action)){
			return false;
		}		
		//非出牌的事件，不能碰
		if(action.getIntention() == Intention.CHUPAI){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 是否可以杠
	 * @return
	 */
	private Intention canGang(Action action){
		if(anGang(action) || mingGang(action) || zhuanWanGang(action)){
			return Intention.GANG;
		}else{
			return Intention.NONE;
		}
	}
	
	/**
	 * 是否可以暗杠
	 * @return
	 */
	private boolean anGang(Action action){
		if(!actionForMine(action)){
			return false;
		}
		//非摸牌事件不能暗杠
		if(action.getIntention() == Intention.MOPAI){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 是否转弯杠
	 * @return
	 */
	private boolean zhuanWanGang(Action action){
		if(!actionForMine(action)){
			return false;
		}
		if(peng == null || peng.size() == 0){
			return false;
		}
		return peng.contains(action.getMahjong());
	}
	
	/**
	 * 是否可以明杠
	 * @return
	 */
	public boolean mingGang(Action action){
		if(actionForMine(action)){
			return false;
		}
		//非出牌的事件，不能碰
		if(action.getIntention() == Intention.CHUPAI){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 这个事件是自己发出的
	 * @return
	 */
	private boolean actionForMine(Action action){
		return direction == action.getDirection();
	}
	
	
	/**
	 * 打印自己的牌
	 */
	public void printMahjongs(){
		StringBuffer sb = new StringBuffer("玩家 ->" + direction.getName() + " 的牌：\n{");
		
		if(peng != null && peng.size() > 0){
			for(Mahjong mahjong : peng)
				sb.append(mahjong).append(mahjong).append(mahjong).append("-");
		}
		if(gangs != null && gangs.size() > 0){
			for(Gang gang : gangs){
				Mahjong mahjong = gang.getMahjong();
				sb.append(mahjong).append(mahjong).append(mahjong).append(mahjong).append("-");
			}
		}
		sb.append("} ");
		for(Mahjong mahjong : mahjongs){
			sb.append(mahjong);
		}
		System.out.println(sb.toString());
	}
	
	
	
	
	
}
