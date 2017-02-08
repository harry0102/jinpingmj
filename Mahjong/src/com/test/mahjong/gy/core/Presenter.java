package com.test.mahjong.gy.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.test.mahjong.gy.bean.Action;
import com.test.mahjong.gy.bean.Direction;
import com.test.mahjong.gy.bean.Intention;
import com.test.mahjong.gy.bean.Location;
import com.test.mahjong.gy.bean.Mahjong;
import com.test.mahjong.gy.bean.MahjongManager;

public class Presenter {
	private static final int MIN_LOCATION = 2;
	private static final int MAX_LOCATION = 4;
	//玩家数量
	private int player;
	//牌
	private MahjongManager mahjongManager;
	//玩家
	private Location[] locations;
	//庄家
	private int zhuang;
	//上一个出牌的玩家（记录这个是要将上一个出牌的玩家的杠的标记去掉）
	private int previousPlay;
	//当前出牌玩家
	private int currentPlay;
	
	private Scanner scanner;
	//是否是穿心炮（如果是穿心炮 winner 则为放炮者，否则为胡牌者）
	private boolean chuanXin;
	//下一个庄家
	private int nextZhuang;
	
	public Presenter(Room room,Scanner scanner){
		player = room.getConfig().getWanFa().getVal();
		if(player < MIN_LOCATION || player > MAX_LOCATION){
			throw new IndexOutOfBoundsException("玩家必须要2~4个人之间");
		}
		this.scanner = scanner;
		mahjongManager = new MahjongManager();
		locations = new Location[player];
		for(int i = 0;i < player;i++){
			locations[i] = getLocation(i); 
		}
		zhuang = 0;
	} 
	
	/**
	 * 根据编号获取玩家位置信息
	 * @param index
	 * @return
	 */
	private Location getLocation(int index){
		Location location = null;
		if(index == 0){
			location = new Location(Direction.e,scanner);
		}else if(index == 1){
			location = new Location(Direction.s,scanner);
		}else if(index == 2){
			location = new Location(Direction.w,scanner);
		}else{
			location = new Location(Direction.n,scanner);
		}
		return location;
	}
	
	/**
	 * 初始化开局属性
	 */
	private void initGameAttr(){
		//将出牌的上家设为没有
		previousPlay = -1;
		//初始化所有人的相关属性
		initLocation();
		//如果 胡牌者的标记为-1则表示刚开局或者黄庄，庄家为0号或者还是原来的庄家不变
		if(nextZhuang != -1){
			zhuang = nextZhuang;
		}
		//第一个出牌的人是庄家
		currentPlay = zhuang;
		//初始化胡牌者
		nextZhuang = -1;
		//洗牌
		mahjongManager.shuffle();
		
	}
	
	/**
	 * 初始化所有人的属性（在游戏桌的位置下放置分数，不在用户那里记）
	 * @param first
	 */
	private void initLocation(){
		for(int i = 0; i < player; i++){
			if(locations[i] != null){
				locations[i].init();
			}
		}
	}
	
	public void startGame(){		
		initGameAttr();
		//发牌，并显示所有玩家的牌
		for(int i = 0; i < player;i++){
			//发天听的牌
			List<Mahjong> mahjongs = mahjongManager.getTianTing();
			locations[i].setMahjongs(mahjongs);
			locations[i].printMahjongs();
			//正常发牌
//			List<Mahjong> mahjongs = new ArrayList<Mahjong>(14);
//			for(int j = 0; j < 13; j++){
//				mahjongs.add(mahjongManager.next());
//			}
//			locations[i].setMahjongs(mahjongs);
//			locations[i].printMahjongs();
		}	
		
		
		actionType = true;		
		while(true){
			//
			skip = false;
			//要出牌或要摸牌时去掉上家的杠的标记
			if(previousPlay != -1){
				//去掉出牌的上家的杠的标记
				locations[previousPlay].cancelGang();
			}
			
			if(actionType == true){
				//要摸牌了				
				if(mahjongManager.remainder() < 1){
					//没牌了
					break;
				}
				//开始摸牌
				
				Mahjong mahjong;
				//是否是杠后摸牌
				if(locations[currentPlay].isGang()){
					mahjong = mahjongManager.last();
				}else{
					mahjong = mahjongManager.next();
				}
				//摸牌的事件
				Action mopaiAction = new Action(locations[currentPlay].getDirection(),mahjong,Intention.MOPAI,locations[currentPlay].isGang());
				
				List<Intention> intentions = locations[currentPlay].distribute(mopaiAction);
				if(intentions!=null){
					//该事件可以被自己响应(胡、杠)
					Intention.printIntentions(locations[currentPlay], intentions);
					Intention intention = Intention.getSelectedIntention(scanner, intentions);					
					if(intention != Intention.NONE){
						Action action = new Action(locations[currentPlay].getDirection(), mahjong, intention, locations[currentPlay].isGang());
						//玩家不是选择过，有意图要处理
						boolean isFinish = locations[currentPlay].handlerFromMineIntention(intention, action);
						if(isFinish){
							nextZhuang = currentPlay;							
							//已胡牌，本局结束，取结算
							break;
						}
						continue;
					}
				}
				actionType = false;
				continue;
			}else{
				//出牌
				Action chuPaiAction = locations[currentPlay].play();
				//是否有人要胡这个牌
				boolean hasHu;
				//TODO 判断一下，如果是，则判断是哪几个人，分别给他们选择胡的选项，并必不执行下面的逻辑，否则完成下面的逻辑
				
				for(int i = 0; i < player;i++){
					if(currentPlay == i){
						continue;
					}
					List<Intention> intentions = locations[i].getAction(chuPaiAction);
					if(intentions != null){
						locations[i].printMahjongs();
						//该事件可以被其他玩家响应
						Intention.printIntentions(locations[i], intentions);
						Intention intention = Intention.getSelectedIntention(scanner, intentions);
						if(intention != Intention.NONE){
							Action action = new Action(locations[i].getDirection(), chuPaiAction.getMahjong(), intention, locations[currentPlay].isGang());
							//玩家不是选择过，有意图要处理
							boolean isFinish = locations[i].handlerFromOtherIntention(intention, action);
							if(isFinish){
								//已胡牌，本局结束，取结算
								jiesuan = true;
								break;
							}else{
								//下一个事件为：出牌
								actionType = false;
								previousPlay = currentPlay;
								currentPlay = i;
								skip = true;
								break;
							}							
						}						
					}
				}
				//是否跳转到下一个摸牌或出牌的事件
				if(skip){
					continue;
				}
				//是否要结算了
				if(jiesuan){
					break;
				}
				previousPlay = currentPlay;
				currentPlay = ++currentPlay == player ? 0 : currentPlay;
				actionType = true;
			}
			
		}		
		jiesuan();
	}	
	/**
	 * 是否跳转到下一个摸牌或出牌的事件,每次开始都要将这个值重置
	 */
	private boolean skip;
	//是否要结算了
	private boolean jiesuan;
	/**
	 * 是摸牌还是要出牌,true为摸牌，false为出牌
	 */
	private boolean actionType;
	
	/**
	 * 结算
	 */
	private void jiesuan(){
		
	}
}
