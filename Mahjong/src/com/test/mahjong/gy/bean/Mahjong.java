package com.test.mahjong.gy.bean;

public class Mahjong implements Comparable<Mahjong>{
	private Type type;
	private Value value;
	
//小对象，忽略重用
//	private static Map<Type,Map<Value,Mahjong>> mahjongs;
//	
//	
//	static{
//		mahjongs = new HashMap<Mahjong.Type, Map<Value,Mahjong>>(3);
//		for(Type type : Type.values()){
//			Map<Value,Mahjong> map = new HashMap<Mahjong.Value, Mahjong>();
//			for(Value value : Value.values()){
//				map.put(value, new Mahjong(type,value));
//			}
//			mahjongs.put(type, map);
//		}
//	}
//	
//	public static Mahjong getInstance(Type type,Value value){
//		return mahjongs.get(type).get(value);
//	}
	
	
	public Mahjong(Type type,Value value){
		this.type = type;
		this.value = value;
	}
	
	/**
	 * 获取这张牌的下一个（用于坎牌的判断）,当本张值为9时，返回null
	 * @return
	 */
	public Mahjong nextInstance(){
		if(value.getValue() == 9){
			return null;
		}
		return new Mahjong(type,Value.getValueById(value.getValue()+1));
	}
	
	/**
	 * 获取这张牌的下一个（用于坎牌的判断）,当本张值为1时，返回null
	 * @return
	 */
	public Mahjong lastInstance(){
		if(value.getValue() == 1){
			return null;
		}
		return new Mahjong(type,Value.getValueById(value.getValue()-1));
	}
	
	
	public Type getType(){
		return type;
	}
	
	public Value getValue(){
		return value;
	}
	
	public String toString(){
		return "[" + value.getValue() + type.getType() + "]";
	}
	
	@Override
	public int compareTo(Mahjong o) {
		if(type == o.getType()){
			return value.getValue() - o.getValue().getValue();
		}else{
			return type.getType().compareTo(o.getType().getType());
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Mahjong)){
			return false;
		}
		Mahjong mahjong = (Mahjong) obj;		
		return this.type == mahjong.getType() && this.value == mahjong.getValue();
	}
	
	@Override
	public int hashCode() {		
		return type.getFlag() + value.getValue();
	}
	
	public static enum Type{
		w(0,0,"万"),b(1,10,"饼"),t(2,20,"条");
		int id;
		int flag;
		String text;
		public String getType(){
			return text;
		}
		
		public int getId() {
			return id;
		}
		
		public int getFlag() {
			return flag;
		}
		
		Type(int id,int flag,String text){
			this.id = id;
			this.flag = flag;
			this.text = text;			
		}
	}
	
	public static enum Value{
		v1(1),v2(2),v3(3),v4(4),v5(5),v6(6),v7(7),v8(8),v9(9);
		int v;
		public int getValue(){
			return v;
		}
		Value(int v){
			this.v = v;
		}
		
		static Value getValueById(int id){
			for(Value v : Value.values()){
				if(v.v == id){
					return v;
				}
			}
			return null;
		}
	}
}
