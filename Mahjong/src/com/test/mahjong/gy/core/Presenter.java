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
	//�������
	private int player;
	//��
	private MahjongManager mahjongManager;
	//���
	private Location[] locations;
	//ׯ��
	private int zhuang;
	//��һ�����Ƶ���ң���¼�����Ҫ����һ�����Ƶ���ҵĸܵı��ȥ����
	private int previousPlay;
	//��ǰ�������
	private int currentPlay;
	
	private Scanner scanner;
	//�Ƿ��Ǵ����ڣ�����Ǵ����� winner ��Ϊ�����ߣ�����Ϊ�����ߣ�
	private boolean chuanXin;
	//��һ��ׯ��
	private int nextZhuang;
	
	public Presenter(Room room,Scanner scanner){
		player = room.getConfig().getWanFa().getVal();
		if(player < MIN_LOCATION || player > MAX_LOCATION){
			throw new IndexOutOfBoundsException("��ұ���Ҫ2~4����֮��");
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
	 * ���ݱ�Ż�ȡ���λ����Ϣ
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
	 * ��ʼ����������
	 */
	private void initGameAttr(){
		//�����Ƶ��ϼ���Ϊû��
		previousPlay = -1;
		//��ʼ�������˵��������
		initLocation();
		//��� �����ߵı��Ϊ-1���ʾ�տ��ֻ��߻�ׯ��ׯ��Ϊ0�Ż��߻���ԭ����ׯ�Ҳ���
		if(nextZhuang != -1){
			zhuang = nextZhuang;
		}
		//��һ�����Ƶ�����ׯ��
		currentPlay = zhuang;
		//��ʼ��������
		nextZhuang = -1;
		//ϴ��
		mahjongManager.shuffle();
		
	}
	
	/**
	 * ��ʼ�������˵����ԣ�����Ϸ����λ���·��÷����������û�����ǣ�
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
		//���ƣ�����ʾ������ҵ���
		for(int i = 0; i < player;i++){
			//����������
			List<Mahjong> mahjongs = mahjongManager.getTianTing();
			locations[i].setMahjongs(mahjongs);
			locations[i].printMahjongs();
			//��������
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
			//Ҫ���ƻ�Ҫ����ʱȥ���ϼҵĸܵı��
			if(previousPlay != -1){
				//ȥ�����Ƶ��ϼҵĸܵı��
				locations[previousPlay].cancelGang();
			}
			
			if(actionType == true){
				//Ҫ������				
				if(mahjongManager.remainder() < 1){
					//û����
					break;
				}
				//��ʼ����
				
				Mahjong mahjong;
				//�Ƿ��Ǹܺ�����
				if(locations[currentPlay].isGang()){
					mahjong = mahjongManager.last();
				}else{
					mahjong = mahjongManager.next();
				}
				//���Ƶ��¼�
				Action mopaiAction = new Action(locations[currentPlay].getDirection(),mahjong,Intention.MOPAI,locations[currentPlay].isGang());
				
				List<Intention> intentions = locations[currentPlay].distribute(mopaiAction);
				if(intentions!=null){
					//���¼����Ա��Լ���Ӧ(������)
					Intention.printIntentions(locations[currentPlay], intentions);
					Intention intention = Intention.getSelectedIntention(scanner, intentions);					
					if(intention != Intention.NONE){
						Action action = new Action(locations[currentPlay].getDirection(), mahjong, intention, locations[currentPlay].isGang());
						//��Ҳ���ѡ���������ͼҪ����
						boolean isFinish = locations[currentPlay].handlerFromMineIntention(intention, action);
						if(isFinish){
							nextZhuang = currentPlay;							
							//�Ѻ��ƣ����ֽ�����ȡ����
							break;
						}
						continue;
					}
				}
				actionType = false;
				continue;
			}else{
				//����
				Action chuPaiAction = locations[currentPlay].play();
				//�Ƿ�����Ҫ�������
				boolean hasHu;
				//TODO �ж�һ�£�����ǣ����ж����ļ����ˣ��ֱ������ѡ�����ѡ����ز�ִ��������߼����������������߼�
				
				for(int i = 0; i < player;i++){
					if(currentPlay == i){
						continue;
					}
					List<Intention> intentions = locations[i].getAction(chuPaiAction);
					if(intentions != null){
						locations[i].printMahjongs();
						//���¼����Ա����������Ӧ
						Intention.printIntentions(locations[i], intentions);
						Intention intention = Intention.getSelectedIntention(scanner, intentions);
						if(intention != Intention.NONE){
							Action action = new Action(locations[i].getDirection(), chuPaiAction.getMahjong(), intention, locations[currentPlay].isGang());
							//��Ҳ���ѡ���������ͼҪ����
							boolean isFinish = locations[i].handlerFromOtherIntention(intention, action);
							if(isFinish){
								//�Ѻ��ƣ����ֽ�����ȡ����
								jiesuan = true;
								break;
							}else{
								//��һ���¼�Ϊ������
								actionType = false;
								previousPlay = currentPlay;
								currentPlay = i;
								skip = true;
								break;
							}							
						}						
					}
				}
				//�Ƿ���ת����һ�����ƻ���Ƶ��¼�
				if(skip){
					continue;
				}
				//�Ƿ�Ҫ������
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
	 * �Ƿ���ת����һ�����ƻ���Ƶ��¼�,ÿ�ο�ʼ��Ҫ�����ֵ����
	 */
	private boolean skip;
	//�Ƿ�Ҫ������
	private boolean jiesuan;
	/**
	 * �����ƻ���Ҫ����,trueΪ���ƣ�falseΪ����
	 */
	private boolean actionType;
	
	/**
	 * ����
	 */
	private void jiesuan(){
		
	}
}
