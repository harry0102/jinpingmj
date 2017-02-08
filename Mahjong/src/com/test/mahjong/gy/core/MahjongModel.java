package com.test.mahjong.gy.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.mahjong.gy.bean.Mahjong;
import com.test.mahjong.gy.bean.ModelType;

public class MahjongModel {
	//一手牌
	private List<Mahjong> mahjongs;
	//将
	private List<Model> jiang;
	//坎
	private List<Model> kan;
	//暗刻
	private List<Model> ke;
	//每个麻将的个数
	private Map<Mahjong, Integer> mahjong_count;
	
	/**
	 * 传入玩家一副牌，可以查看是否胡牌
	 * @param mahjongs
	 */
	public MahjongModel(List<Mahjong> mahjongs){
		this.mahjongs = new ArrayList<Mahjong>(mahjongs);
		init();
	}
	
	/**
	 * 传入玩家一副牌，可以查看是否胡牌
	 * @param mahjongs
	 */
	public MahjongModel(List<Mahjong> mahjongs,Mahjong mahjong){
		this.mahjongs = new ArrayList<Mahjong>(mahjongs);
		this.mahjongs.add(mahjong);
		init();
	}
	
	private void init(){
		//排序
		Collections.sort(mahjongs);
		//将：最多7对，小七对时
		jiang = new ArrayList<Model>(7);
		//坎：最多4坎，平胡
		kan = new ArrayList<Model>(4);
		//刻：最多4刻
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
	 * 开始解析
	 */
	public void startParse(final OnParse parse){
		
		if(parse == null){
			throw new NullPointerException("监听不能为空");
		}
		new Thread(){
			public void run() {
				
				
			};
		}.start();
	}
	
	
	
	/**
	 * 整副牌有没有一对
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
	 * 分析监听
	 * @author Administrator
	 *
	 */
	public interface OnParse{
		void parse(ModelType mt);
	}	
	
	/**
	 * 玩家一副成型牌的组成结构
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
				throw new IndexOutOfBoundsException("下标越界");
			}
			mahjongs.add(mahjong);
		}
		
		public Mahjong get(int index){
			if(index < 0 || index >= size){
				throw new IndexOutOfBoundsException("下标越界");
			}
			return mahjongs.get(index);
		}
		
	}  
	
	/**
	 * 一对（将）
	 * @author Administrator
	 *
	 */
	private class Twain extends Model{
		public Twain(Mahjong m0,Mahjong m1){
			super(2);
			if(!m0.equals(m1)){
				throw new IllegalStateException("一对将必须是两个相同的牌");
			}
			put(m0);
			put(m1);
		}
	}
	
	/**
	 * 一个序列（一坎牌）
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
				throw new IllegalStateException("一坎牌必须是3个");
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
				throw new IllegalStateException("一坎牌必须是3个");
			}
			if(!(ms.get(0).getType() == ms.get(1).getType() && ms.get(1).getType() == ms.get(2).getType())){
				throw new IllegalStateException("一坎牌必须是同样的类型");
			}
			Collections.sort(ms);
			if(!(ms.get(2).getValue().getValue()-ms.get(1).getValue().getValue() == 1 &&
					ms.get(1).getValue().getValue()-ms.get(0).getValue().getValue() == 1)){
				throw new IllegalStateException("一坎牌必须是连续的");
			}
			put(ms.get(0));
			put(ms.get(1));
			put(ms.get(2));
		}
	}
	/**
	 * 暗刻
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
				throw new IllegalStateException("一暗刻必须是3个相同的牌");
			}
			put(m0);
			put(m1);
			put(m2);
		}
	}
}
