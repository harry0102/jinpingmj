package com.test.mahjong.gy.core;

import java.util.List;

import com.test.mahjong.gy.bean.Mahjong;
import com.test.mahjong.gy.bean.ModelType;

public class CheckHuUtils {
	private static int[][] ARRAY = initArray();

	/**
	 * ����Ƿ�����
	 * 
	 * @param peng
	 *            ��
	 * @param gang
	 *            ��
	 * @param mahjongs
	 *            ����
	 * @param judgment
	 *            �о�(Ϊ���Ч�ʶ��ӵĲ���)��true��Ϊ�ж�������������ͣ����к������ȡ������󣩣�false��ֻ���ж��Ƿ�����
	 * @return
	 */
	public static ModelType checkTing(List<Mahjong> peng, List<Mahjong> gang, List<Mahjong> mahjongs, boolean judgment) {
		int[][] array = listToArray(mahjongs);

		int p = peng == null ? 0 : peng.size();
		int g = gang == null ? 0 : peng.size();

		int[][][] pg = new int[p + g][][];
		if (p != 0)
			for (int i = 0; i < p; i++) {
				pg[i] = new int[peng.get(i).getType().getId()][];
			}
		if (g != 0)
			for (int i = p; i < p + g; i++) {
				pg[i] = new int[peng.get(i - p).getType().getId()][];
			}
		
		return new CheckHu().checkTing(pg, array[0], array[1], array[2], judgment);
	}

	/**
	 * ����Ƿ����
	 * 
	 * @param peng
	 *            ��
	 * @param gang
	 *            ��
	 * @param mahjongs
	 *            ����
	 * @param mahjong
	 *            ��������˳����ƣ����һ�ţ�
	 * @return ���غ��Ƶ�����
	 */
	public static ModelType checkHu(List<Mahjong> peng, List<Mahjong> gang, List<Mahjong> mahjongs, Mahjong mahjong) {
		int[][] array = listToArray(mahjongs);

		int p = peng == null ? 0 : peng.size();
		int g = gang == null ? 0 : peng.size();

		int[][][] pg = new int[p + g][][];
		if (p != 0)
			for (int i = 0; i < p; i++) {
				pg[i] = new int[peng.get(i).getType().getId()][];
			}
		if (g != 0)
			for (int i = p; i < p + g; i++) {
				pg[i] = new int[peng.get(i - p).getType().getId()][];
			}
		ModelType result = new CheckHu().checkHu(pg, array[0], array[1], array[2], mahjong.getType().getId(),
				mahjong.getValue().getValue());
		return result;
	}

	/**
	 * ������ת��Ϊ����
	 * 
	 * @param mahjongs
	 * @return
	 */
	private static int[][] listToArray(List<Mahjong> mahjongs) {
		int[][] array = ARRAY;
		for (Mahjong mahjong : mahjongs) {
			array[mahjong.getType().getId()][mahjong.getValue().getValue() - 1]++;
		}
		return array;
	}

	/**
	 * ��ʼ������
	 * 
	 * @return
	 */
	private static int[][] initArray() {
		int[][] array = new int[3][9];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				array[i][j] = 0;
			}
		}
		return array;
	}

//	public static void main(String[] args) {
//		List<Mahjong> peng = new ArrayList<Mahjong>();
//		peng.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v3));
//
//		List<Mahjong> gang = new ArrayList<Mahjong>();
//		gang.add(new Mahjong(Mahjong.Type.w, Mahjong.Value.v3));
//
//		List<Mahjong> mahjongs1 = new ArrayList<Mahjong>();
//		mahjongs1.add(new Mahjong(Mahjong.Type.b, Mahjong.Value.v1));
//		mahjongs1.add(new Mahjong(Mahjong.Type.b, Mahjong.Value.v1));
//		mahjongs1.add(new Mahjong(Mahjong.Type.b, Mahjong.Value.v1));
//
//		mahjongs1.add(new Mahjong(Mahjong.Type.b, Mahjong.Value.v2));
//		mahjongs1.add(new Mahjong(Mahjong.Type.b, Mahjong.Value.v2));
//		mahjongs1.add(new Mahjong(Mahjong.Type.b, Mahjong.Value.v2));
//
//		mahjongs1.add(new Mahjong(Mahjong.Type.b, Mahjong.Value.v3));
//		mahjongs1.add(new Mahjong(Mahjong.Type.b, Mahjong.Value.v3));
//		mahjongs1.add(new Mahjong(Mahjong.Type.b, Mahjong.Value.v3));
//
//		mahjongs1.add(new Mahjong(Mahjong.Type.t, Mahjong.Value.v1));
//
////		Mahjong mahjong1 = new Mahjong(Mahjong.Type.t, Mahjong.Value.v1);
//
//		long beginTime = System.currentTimeMillis();
//		for (int i = 0; i < 1; i++) {
////			ModelType modelType1 = CheckHuUtils.checkHu(null, null, mahjongs1, mahjong1);
//			ModelType modelType1 = CheckHuUtils.checkTing(peng, gang, mahjongs1, true);
//			System.out.println(modelType1.getText());
//		}
//		long endTime = System.currentTimeMillis();
//		System.out.println("��ʱ��" + (endTime - beginTime));
//	}
}
