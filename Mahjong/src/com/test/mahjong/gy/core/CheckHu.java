package com.test.mahjong.gy.core;

import com.test.mahjong.gy.bean.ModelType;
/**
 * 胡牌检查
 * 算法来源：http://blog.csdn.net/caoxiongjun/article/details/1184784(自己改进：贵州麻将胡牌方式与番数计算)
 * @author Administrator
 *
 */
public class CheckHu {
		
	// 是否有將
	private boolean jiang;

	// 是否同花 -- 为true则为清一色
	private boolean tongHua;

	// 是否是小七对
	private boolean xiaoQiDui;

	// 是否有顺子 -- 用来判断小七对和大对子
	private boolean shunZi;
	
	//是否有暗刻 --有则不能成七对
	private boolean anKe;
	
	// 是否能成龙七对
	private boolean canLong;
	//尝试匹配小七对
	private boolean tryQiDui = true;
	//匹配的对子数量
	private int dui;
	// 速度
//	private int velocity;

		
	/**
	 * 检查这个花色是否被减完了（如果没有减完自然没有和） 递归算法
	 * 
	 * @param type
	 * @return
	 * @throws Exception 
	 */
	private int check(int[] type) throws Exception {
		for (int i = 0; i < type.length; i++) {
//			velocity++;
			if(tryQiDui && type[i] %2 == 1){
				tryQiDui = false;
			}
			//小七对判断
			if(tryQiDui && type[i] != 0 && type[i] %2 == 0){
				type[i] -= 2;
				xiaoQiDui = true;
				dui ++;
				//如果递归回来依旧没有减完，则加回去
				if(check(type) != 0){
					type[i] += 2;
					xiaoQiDui = false;
					tryQiDui = false;
//					i = -1;
				}else{
					if(dui == 7){
						tryQiDui = false;
					}					
					return 0;
				}
			}
			if(tryQiDui){
				continue;
			}
			// 尝试用第i张牌为将
			if (!jiang && type[i] >= 2) {
				// 如果还没有将，则把将减出去
				type[i] -= 2;
				jiang = true;
				// 有了将，再去计算这个类型的牌能不能减完，如果不能，则这个牌不能为将，再将之加回去
				if (check(type) != 0) {
					jiang = false;
					type[i] += 2;
				}
			}
			
			// 暗刻(暗刻在顺子之前判断，是因为暗刻能比顺子组成更高番数的牌型，要优先算大牌)
			if (type[i] >= 3) {
				type[i] -= 3;
				anKe = true;
				// 如果递归回来依旧没有减完，则加回去
				if (check(type) != 0) {
					type[i] += 3;
					anKe = false;
				}
			}

			// 尝试用第i张牌为顺子的第一个,
			if (i < 7 && type[i] != 0 && type[i + 1] != 0 && type[i + 2] != 0) {
				type[i]--;
				type[i + 1]--;
				type[i + 2]--;
				shunZi = true;
				// 如果递归回来依旧没有减完，则加回去
				if (check(type) != 0) {
					type[i]++;
					type[i + 1]++;
					type[i + 2]++;
					shunZi = false;
				}
			}
						
		}
		// 最后判断减没减完
		try {
			return getCount(type);
		} catch (Exception e) {
			// 如果出异常，不能返回0，可以返回非0的任何数（这里一般不会有异常的啦）
			return 1;
		}

	}

	/**
	 * 属性重置（初始化）
	 */
	private void reset(){
		jiang = false;
		tongHua = false;
		xiaoQiDui = false;
		shunZi = false;
		anKe = false;
		canLong = false;
		tryQiDui = true;
		dui = 0;
	}
	
	/**
	 * 获取某个花色的牌的数量（顺带检查牌的数量）
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 *             如果牌的数量大于4个或者小于0个会报异常
	 */
	private int getCount(int[] type) throws Exception {
		int count = 0;
		for (int i : type) {
			if (i < 0 || i > 4) {
				throw new IndexOutOfBoundsException("牌的数量超过界限");
			}
			count += i;
		}
		return count;
	}
	
	/**
	 * 检查碰和杠的花色是否一致，结合 tongHua  字段，判断是否清一色
	 * @param pengOrGang
	 * @return 返回同花色的类型，或-1（非同花）,-2（无碰、无杠）
	 */
	private int alike(int[][][] pengOrGang){
		//int   []     []       []     pengOrGang 的值为3就是碰，4就是杠
		//     第几组    什么类型牌型        什么牌
		if(pengOrGang == null || pengOrGang.length == 0){
			return -2;
		}
		if(pengOrGang.length == 1){
			return pengOrGang[0].length;
		}
		boolean flag = true;
		for(int i = 1; i < pengOrGang.length; i++){
			flag &= pengOrGang[i-1].length == pengOrGang[i].length;
			if(!flag){
				return -1;
			}
		}		
		return pengOrGang[0].length;
	}
	
	
	/**
	 * 检查是否听牌（该方法用于第一张牌打出去时，判断是否是天听，也可于结算时判断玩家所做的牌型）
	 * @param pengOrGang 碰或杠
	 * @param wan
	 *            万字牌，类型0
	 * @param tiao
	 *            条字牌，类型1
	 * @param tong
	 *            筒字牌，类型2
	 * @param judgment 判决(为提高效率而加的参数)，true则为判断玩家所做的牌型（所有胡牌情况取番数最大），false则只需判断是否听牌
	 * @return
	 */
	public ModelType checkTing(int[][][] pengOrGang, int[] wan, int[] tiao, int[] tong,boolean judgment){
		ModelType mt = ModelType.NONE;
		for(int type = 0; type < 3; type++){
			for(int value = 1; value <= 9;value++){
				tryQiDui = true;
				ModelType tmp = checkHu(pengOrGang, wan, tiao, tong,type,value);
				if(tmp != ModelType.NONE){
					//可以将听的牌收集，这里不需要
					mt = mt.getId() < tmp.getId() ? tmp : mt;
					if(!judgment){
						return mt; //理论上家上这句能增加效率
					}
					
				}
			}
		}
		return mt;
	}
	
	
	/**
	 * 检查是否胡牌（最后一个牌拆为两个参数是用于判断是否龙七对）
	 * 
	 * @param wan
	 *            万字牌，类型0
	 * @param tiao
	 *            条字牌，类型1
	 * @param tong
	 *            筒字牌，类型2
	 * @param type
	 *            最后一张牌的类型[0-2]
	 * @param index
	 *            最后一张牌的值[1-9]
	 * @return
	 */
	public ModelType checkHu(int[][][] pengOrGang, int[] awan, int[] atiao, int[] atong, int type, int index) {
		try{
			reset();
			if (awan == null || atiao == null || atong == null) {
				return ModelType.NONE;
			}
			// 检查每个类型是否都是9，没有牌的也要用0表示
			if (awan.length != 9 || atiao.length != 9 || atong.length != 9) {
				return ModelType.NONE;
			}
			// 检查最后一张牌
			if (type < 0 || type > 3 || index < 1 || index > 9) {
				return ModelType.NONE;
			}
			
			//制作副本
			int[] wan = new int[awan.length];
			int[] tiao = new int[atiao.length];
			int[] tong = new int[atong.length];
			System.arraycopy(awan, 0, wan, 0, awan.length);
			System.arraycopy(atiao, 0, tiao, 0, atiao.length);
			System.arraycopy(atong, 0, tong, 0, atong.length);
			
			// 将最后一张牌入列
			if (type == 0) {
				wan[index - 1]++;
				canLong = wan[index - 1] == 4;
			} else if (type == 1) {
				tiao[index - 1]++;
				canLong = tiao[index - 1] == 4;
			} else {
				tong[index - 1]++;
				canLong = tong[index - 1] == 4;
			}

			// 检查牌的数量是否达标 3n+2(n[0,4])
			int count = 0;
			int wanCount;
			int tiaoCount;
			int tongCount;
			try {
				wanCount = getCount(wan);
				tiaoCount = getCount(tiao);
				tongCount = getCount(tong);
				count = wanCount + tiaoCount + tongCount;
			} catch (Exception e) {
				// 如果这里出异常，GG,牌的数量不对，没必要继续判断下去了
				System.err.println("单张牌数量为0以下或4以上了");
				return ModelType.NONE;
			}
			boolean countFlag = false;
			for (int n = 0; n <= 4; n++) {
				if (3 * n + 2 == count) {
					countFlag = true;
					break;
				}
			}
			if (!countFlag) {
				// 牌的数量不对，不能胡牌
				System.err.println("不能胡牌，牌的数量为：" + count);
				return ModelType.NONE;
			}
						
			// 是否胡牌
			if ((wanCount == 0 || check(wan) == 0) && (tiaoCount == 0 || check(tiao) == 0)
					&& (tongCount == 0 || check(tong) == 0)) {
				//碰和杠的相关信息： -2(没有碰和杠),-1(碰和杠非同花),0(碰和杠都是万字牌),1(碰和杠都是条字牌),2(碰和杠都是筒字牌)
				int alike = alike(pengOrGang);
				ModelType result = ModelType.PING;// 平胡
				if (!shunZi) {
					// 没有顺子，可能为大对子或者小七对，这里暂定为大对子，小七对在下面判断
					result = ModelType.DA_DUI;
				}
				if (!canLong) {
					//小七对的条件：没有顺子，没有暗刻，没有碰，没有杠
					if (alike == -2 && xiaoQiDui && !shunZi && !anKe) {
						// 是小七对
						result = ModelType.XIAO_QI_DUI;
					}
				} else {
					if (alike == -2 && xiaoQiDui && !shunZi && !anKe) {
						// 是龙七对
						result = ModelType.LONG_QI_DUI;
					}
				}
				
				//判断能否为清一色
				if (count == wanCount || count == tiaoCount || count == tongCount) {
					// 混色牌(不为清一色)
					
					if(alike == -2){
						//没碰和杠
						tongHua = true;
					}else if(alike == -1){
						tongHua = false;
					}else if(alike == 0){
						tongHua = count == wanCount;
					}else if(alike == 1){
						tongHua = count == tiaoCount;
					}else if(alike == 2){
						tongHua = count == tongCount;
					}
				}
				
				if (!tongHua) {
					return result;
				} else {
					if (result == ModelType.DA_DUI) {
						// 清大对
						result = ModelType.QING_DUI;
					} else if (result == ModelType.XIAO_QI_DUI) {
						// 清七对
						result = ModelType.QING_QI_DUI;
					} else if (result == ModelType.LONG_QI_DUI) {
						// 青龙背
						result = ModelType.QING_LONG;
					} else {
						// 清一色
						result = ModelType.QING_YI_SE;
					}
				}
				return result;
			}
			return ModelType.NONE;
		}catch(Exception e){
			return ModelType.NONE;
		}
	}

//	public static void main(String[] args) {		
//		CheckHu ch = new CheckHu();
//		int[] w = { 3, 0, 2, 2, 2, 0, 0, 0, 0 };		
//		int[] ti = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
//		int[] to = { 0, 1, 0, 3, 0, 0, 0, 0, 0 };
//		long beginTime = System.currentTimeMillis();
//		ModelType hu = ch.checkTing(null,w, ti, to,true);
//		long endTime = System.currentTimeMillis();
//		System.out.println("计算耗时：" + (endTime - beginTime) + ",牌型=" + hu);
//	}

}
