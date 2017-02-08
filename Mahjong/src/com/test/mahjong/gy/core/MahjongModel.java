package com.test.mahjong.gy.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.mahjong.gy.bean.Mahjong;
import com.test.mahjong.gy.bean.ModelType;

public class MahjongModel {
	//һ����
	private List<Mahjong> mahjongs;
	//��
	private List<Model> jiang;
	//��
	private List<Model> kan;
	//����
	private List<Model> ke;
	//ÿ���齫�ĸ���
	private Map<Mahjong, Integer> mahjong_count;
	
	/**
	 * �������һ���ƣ����Բ鿴�Ƿ����
	 * @param mahjongs
	 */
	public MahjongModel(List<Mahjong> mahjongs){
		this.mahjongs = new ArrayList<Mahjong>(mahjongs);
		init();
	}
	
	/**
	 * �������һ���ƣ����Բ鿴�Ƿ����
	 * @param mahjongs
	 */
	public MahjongModel(List<Mahjong> mahjongs,Mahjong mahjong){
		this.mahjongs = new ArrayList<Mahjong>(mahjongs);
		this.mahjongs.add(mahjong);
		init();
	}
	
	private void init(){
		//����
		Collections.sort(mahjongs);
		//�������7�ԣ�С�߶�ʱ
		jiang = new ArrayList<Model>(7);
		//�������4����ƽ��
		kan = new ArrayList<Model>(4);
		//�̣����4��
		ke = new ArrayList<Model>(4);
		
		mahjong_count = new HashMap<Mahjong, Integer>();
		for(int i = 0;i < mahjongs.size();){
			Mahjong mahjong = mahjongs.get(i);
			int count = 1;
			while(true){
				if(i+count < mahjongs.size() && mahjong.equals(mahjongs.get(i+count))){
					count++;
				}else{
					mahjong_count.put(mahjong, count);
					break;
				}
			}
			i += count;
		}
	}
	
	
	public static void main(String[] args) {
		List<Mahjong> mahjongs = new ArrayList<Mahjong>();
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v1));
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v1));
		
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v1));
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v2));
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v3));
		
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v3));
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v3));
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v3));
		
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v4));
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v5));
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v6));
		
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v7));
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v8));
		
		
		mahjongs.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v9));
		
		MahjongModel mm = new MahjongModel(mahjongs);
		
		
	}
	
	
	/**
	 * ��ʼ����
	 */
	public void startParse(final OnParse parse){
		
		if(parse == null){
			throw new NullPointerException("��������Ϊ��");
		}
		new Thread(){
			public void run() {
				
				
			};
		}.start();
	}
	
	
	
	/**
	 * ��������û��һ��
	 * @return
	 */
	private boolean hasJiang(){
		for(Map.Entry<Mahjong, Integer> entry : mahjong_count.entrySet()){
			if(entry.getValue() > 2){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ��������
	 * @author Administrator
	 *
	 */
	public interface OnParse{
		void parse(ModelType mt);
	}	
	
	/**
	 * ���һ�������Ƶ���ɽṹ
	 * @author Administrator
	 *
	 */
	private class Model{
		int size;
		List<Mahjong> mahjongs;
		
		public Model(int size){
			mahjongs = new ArrayList<Mahjong>(size);
		}
		
		public void put(Mahjong mahjong){
			if(mahjongs.size() >= size){
				throw new IndexOutOfBoundsException("�±�Խ��");
			}
			mahjongs.add(mahjong);
		}
		
		public Mahjong get(int index){
			if(index < 0 || index >= size){
				throw new IndexOutOfBoundsException("�±�Խ��");
			}
			return mahjongs.get(index);
		}
		
	}  
	
	/**
	 * һ�ԣ�����
	 * @author Administrator
	 *
	 */
	private class Twain extends Model{
		public Twain(Mahjong m0,Mahjong m1){
			super(2);
			if(!m0.equals(m1)){
				throw new IllegalStateException("һ�Խ�������������ͬ����");
			}
			put(m0);
			put(m1);
		}
	}
	
	/**
	 * һ�����У�һ���ƣ�
	 * @author Administrator
	 *
	 */
	private class Sequence extends Model{
		public Sequence(Mahjong m0,Mahjong m1,Mahjong m2){
			super(3);			
			List<Mahjong> ms = new ArrayList<Mahjong>(3);
			ms.add(m0);
			ms.add(m1);
			ms.add(m2);
			init(ms);
		}
		
		public Sequence(Mahjong[] ms){
			super(3);
			if(ms == null || ms.length != 3){
				throw new IllegalStateException("һ���Ʊ�����3��");
			}
			List<Mahjong> m = new ArrayList<Mahjong>(3);
			m.add(ms[0]);
			m.add(ms[1]);
			m.add(ms[2]);
			init(m);
		}
		
		public Sequence(List<Mahjong> ms){
			super(3);
			init(ms);
		}
		
		private void init(List<Mahjong> ms){
			if(ms == null || ms.size() != 3){
				throw new IllegalStateException("һ���Ʊ�����3��");
			}
			if(!(ms.get(0).getType() == ms.get(1).getType() && ms.get(1).getType() == ms.get(2).getType())){
				throw new IllegalStateException("һ���Ʊ�����ͬ��������");
			}
			Collections.sort(ms);
			if(!(ms.get(2).getValue().getValue()-ms.get(1).getValue().getValue() == 1 &&
					ms.get(1).getValue().getValue()-ms.get(0).getValue().getValue() == 1)){
				throw new IllegalStateException("һ���Ʊ�����������");
			}
			put(ms.get(0));
			put(ms.get(1));
			put(ms.get(2));
		}
	}
	/**
	 * ����
	 * @author Administrator
	 *
	 */
	private class Three extends Model{
		public Three(Mahjong m0,Mahjong m1,Mahjong m2){
			super(3);
			init(m0,m1,m2);
		}
		private void init(Mahjong m0,Mahjong m1,Mahjong m2){
			if(!(m0.equals(m1)&&m1.equals(m2))){
				throw new IllegalStateException("һ���̱�����3����ͬ����");
			}
			put(m0);
			put(m1);
			put(m2);
		}
	}
}
