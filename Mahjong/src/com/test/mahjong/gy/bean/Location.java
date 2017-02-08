package com.test.mahjong.gy.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import com.test.mahjong.gy.core.CheckHu;
import com.test.mahjong.gy.core.CheckHuUtils;


/**
 * ����
 * @author Administrator
 *
 */
public class Location {
	/**
	 * ���λ��
	 */
	private Direction direction;
	/**
	 * ���ϻ�ʣ�µ���
	 */
	private List<Mahjong> mahjongs;
	/**
	 * ���˵���
	 */
	private List<Mahjong> peng;
	/**
	 * ���˵���
	 */
	private List<Gang> gangs;
	/**
	 * ��ǰ������
	 */
	private Mahjong currentMahjong;
	
	/**
	 * �Ѿ����ȥ����
	 */
	private List<Mahjong> playedMahjongs;
	
	private Scanner scanner;
	/**
	 * �ոո���һ����
	 */
	private boolean gang;
	/**
	 * ���һ���ƣ��ж�������������������Ϊtrue��ʱ����ƣ���Ϊ���
	 */
	private boolean firstPlay;
	/**
	 * �Ƿ�����
	 */
	private boolean tianTing;
	
	//����
	private int score;
	
	public Location(Direction direction,Scanner scanner){
		this.direction = direction;
		this.scanner = scanner;
		this.gangs = new ArrayList<Gang>();
		this.playedMahjongs = new ArrayList<Mahjong>(); 
	}
	
	/**
	 * ���֣���ʼ��������
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
	 * �Ѿ��������
	 * @param mahjong
	 */
	public void addPlayedMahjongs(Mahjong mahjong){
		playedMahjongs.add(mahjong);
	}
	
	/**
	 * ����Ѵ��ȥ ����
	 */
	public void clearPlayedMahjongs(){
		playedMahjongs.containsAll(playedMahjongs);
	}
	
	/**
	 * �ǲ�������
	 * @return
	 */
	public boolean isTianTing(){
		return tianTing;
	}
	
	/**
	 * �Ƿ�ոܹ�
	 * @return
	 */
	public boolean isGang(){
		return gang;
	}
	
	/**
	 * ȡ���ոܹ��ı��
	 */
	public void cancelGang(){
		this.gang = false;
	}
	
	/**
	 * ��ȡλ��
	 * @return
	 */
	public Direction getDirection(){
		return direction;
	}
	
	/**
	 * ��ȡʣ�µ�����
	 * @return
	 */
	public List<Mahjong> getMahjongs(){
		return mahjongs;
	}
	
	/**
	 * ��ȡ��������
	 * @return
	 */
	public List<Mahjong> getPeng(){
		return peng;
	}
	
	
	/**
	 * ��ȡ���иܵ��� �����ܲ���ʾ��������ҿ�����Ϸ����ʱ����ʾ��
	 * @return
	 */
	private List<Gang> getGang(){
		return gangs;
	}

	/**
	 * ��ʼ����
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
	 * ��һ����,���ؿ��Ժ����߸ܵ���ͼ�������û������ó���
	 * @param mahjong
	 */
	public synchronized List<Intention> distribute(Action action){
		System.out.println("��ǰ��� -> " + direction.name + " �����ƣ�" + action.getMahjong());
//		printMahjongs();
		currentMahjong = action.getMahjong();
		return getIntention(action);
	}
	
	/**
	 * �������Ʋ���������
	 * @param mahjong
	 */
	private synchronized void putInMahjong(){
		mahjongs.add(currentMahjong);
		currentMahjong = null;
		Collections.sort(mahjongs);
	}
	
	/**
	 * ����
	 * @return
	 */
	public Action play(){
		printMahjongs();
		System.out.println( "��� ->" + direction.getName() +  "��ѡ����Ҫ�����ƣ�");
		int index = -1;
		Mahjong remove;
		while(true){
			try{
				index = scanner.nextInt();		
				if(index < 0){
					throw new IndexOutOfBoundsException("�±�Խ��");
				}
				if(currentMahjong == null){
					//�����Ƴ���
					if(index >= mahjongs.size()){
						throw new IndexOutOfBoundsException("�±�Խ��");
					}
					
					//���ȥ�������ϵ���
					remove = mahjongs.remove(index);
					
				}else{
					//�������ٳ���
					if(index > mahjongs.size()){
						throw new IndexOutOfBoundsException("�±�Խ��");
					}
					
					if(index == mahjongs.size()){
						//���ȥ���ǵ�ǰ������								
						remove = currentMahjong;
						currentMahjong = null;
					}else{
						//���ȥ�������ϵ���
						remove = mahjongs.remove(index);
						putInMahjong();
					}
				}
				
				break;
			}catch(Exception e){
				System.out.println("����������������룺");
			}
		}
		if(firstPlay){
			//�����һ���Ƶ�ʱ���ж��Ƿ����ƣ���������û��Ƿ�����
			//�����������������ٻ���Ŷ��ֻ�ܵȺ�������Ҫ��ͨ��֤Ҳ���Խ��ڣ�
			// TODO
			ModelType modelType = CheckHuUtils.checkTing(peng, Gang.getGangMahjong(gangs), mahjongs, false);
			if(modelType != ModelType.NONE){
				//�����Ƿ�Ҫ��������ѡ��
				List<Intention> intentions = Intention.makeIntentions(Intention.TING);
				Intention.printIntentions(this, intentions);
				Intention intention = Intention.getSelectedIntention(scanner, intentions);
				if(intention != Intention.NONE){
					//ѡ��������
					Action action = new Action(direction,remove,Intention.TING,false/** ����ǰ�治�����иܣ�����ֱ��false */);
					handlerFromMineIntention(intention, action);
				}
			}
		}
		firstPlay = false;
		System.out.println("��� ->" + direction.getName() + "���ƣ� " + remove);
//		printMahjongs();
		return new Action(direction, remove, Intention.CHUPAI,gang);
	}
	
	/**
	 * ����һ���¼����ж��Լ����ϵ����Ƿ��ܶԸ��¼�������Ӧ,
	 * ��������򷵻�null�����򷵻ؿ��������ķ�Ӧ��
	 * @param mahjong
	 * @return
	 */
	public List<Intention> getAction(Action action){		
		return getIntention(action);
	}
	
	/**
	 * ���������Լ�����ʱ(�������˴��ȥһ���ƺ��ж��Ƿ�����)����ͼ
	 * @param intention
	 * @param mahjong
	 * @return �����Ƿ񱾾ֽ������Ƿ���ƣ�
	 */
	public synchronized boolean handlerFromMineIntention(Intention intention,Action action){
		try{
			Mahjong mahjong = action.getMahjong();
			if(intention == Intention.HU){
				return true;
			}else if(intention == Intention.TING){
				//������
				tianTing = true;
				return false;
			}else{
				firstPlay = false;
				//ʣ�µľ��Ǹ���			
				if(intention == Intention.GANG){
					//����
					gangs.add(new Gang(direction,Gang.TYPE_AN,mahjong));
				}else{
					//ת���
					gangs.add(new Gang(direction,Gang.TYPE_ZHUANG_WAN,mahjong));
					if(peng != null){
						peng.remove(mahjong);
					}else{
						throw new IllegalStateException("����û�����Ͳ�����ת���");
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
	 * �������Ա��˳���ʱ����ͼ
	 * @param intention
	 * @param mahjong
	 * @return �����Ƿ񱾾ֽ��� ���Ƿ���ƣ�
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
				//ʣ�µľ�������
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
	 * �����ϵ�������
	 */
	private void sort(){
		if(mahjongs != null){
			Collections.sort(mahjongs);
		}
	}
	
	/**
	 * �ж�һ�����Ƿ����������ȵ�
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
		
		//TODO �ж��Ƿ�ɺ�
		
		return intentions.size() == 1 ? null : intentions;
	}
	
	
	/**
	 * �м����Ƹ���ǰ�¼���ص�����һ����
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
	 * �Ƿ������
	 * @return
	 */
	private boolean canPeng(Action action){
		if(actionForMine(action)){
			return false;
		}		
		//�ǳ��Ƶ��¼���������
		if(action.getIntention() == Intention.CHUPAI){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * �Ƿ���Ը�
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
	 * �Ƿ���԰���
	 * @return
	 */
	private boolean anGang(Action action){
		if(!actionForMine(action)){
			return false;
		}
		//�������¼����ܰ���
		if(action.getIntention() == Intention.MOPAI){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * �Ƿ�ת���
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
	 * �Ƿ��������
	 * @return
	 */
	public boolean mingGang(Action action){
		if(actionForMine(action)){
			return false;
		}
		//�ǳ��Ƶ��¼���������
		if(action.getIntention() == Intention.CHUPAI){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * ����¼����Լ�������
	 * @return
	 */
	private boolean actionForMine(Action action){
		return direction == action.getDirection();
	}
	
	
	/**
	 * ��ӡ�Լ�����
	 */
	public void printMahjongs(){
		StringBuffer sb = new StringBuffer("��� ->" + direction.getName() + " ���ƣ�\n{");
		
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
