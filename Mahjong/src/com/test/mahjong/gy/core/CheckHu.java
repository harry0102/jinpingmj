package com.test.mahjong.gy.core;

import com.test.mahjong.gy.bean.ModelType;
/**
 * ���Ƽ��
 * �㷨��Դ��http://blog.csdn.net/caoxiongjun/article/details/1184784(�Լ��Ľ��������齫���Ʒ�ʽ�뷬������)
 * @author Administrator
 *
 */
public class CheckHu {
		
	// �Ƿ��Ќ�
	private boolean jiang;

	// �Ƿ�ͬ�� -- Ϊtrue��Ϊ��һɫ
	private boolean tongHua;

	// �Ƿ���С�߶�
	private boolean xiaoQiDui;

	// �Ƿ���˳�� -- �����ж�С�߶Ժʹ����
	private boolean shunZi;
	
	//�Ƿ��а��� --�����ܳ��߶�
	private boolean anKe;
	
	// �Ƿ��ܳ����߶�
	private boolean canLong;
	//����ƥ��С�߶�
	private boolean tryQiDui = true;
	//ƥ��Ķ�������
	private int dui;
	// �ٶ�
//	private int velocity;

		
	/**
	 * ��������ɫ�Ƿ񱻼����ˣ����û�м�����Ȼû�кͣ� �ݹ��㷨
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
			//С�߶��ж�
			if(tryQiDui && type[i] != 0 && type[i] %2 == 0){
				type[i] -= 2;
				xiaoQiDui = true;
				dui ++;
				//����ݹ��������û�м��꣬��ӻ�ȥ
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
			// �����õ�i����Ϊ��
			if (!jiang && type[i] >= 2) {
				// �����û�н�����ѽ�����ȥ
				type[i] -= 2;
				jiang = true;
				// ���˽�����ȥ����������͵����ܲ��ܼ��꣬������ܣ�������Ʋ���Ϊ�����ٽ�֮�ӻ�ȥ
				if (check(type) != 0) {
					jiang = false;
					type[i] += 2;
				}
			}
			
			// ����(������˳��֮ǰ�жϣ�����Ϊ�����ܱ�˳����ɸ��߷��������ͣ�Ҫ���������)
			if (type[i] >= 3) {
				type[i] -= 3;
				anKe = true;
				// ����ݹ��������û�м��꣬��ӻ�ȥ
				if (check(type) != 0) {
					type[i] += 3;
					anKe = false;
				}
			}

			// �����õ�i����Ϊ˳�ӵĵ�һ��,
			if (i < 7 && type[i] != 0 && type[i + 1] != 0 && type[i + 2] != 0) {
				type[i]--;
				type[i + 1]--;
				type[i + 2]--;
				shunZi = true;
				// ����ݹ��������û�м��꣬��ӻ�ȥ
				if (check(type) != 0) {
					type[i]++;
					type[i + 1]++;
					type[i + 2]++;
					shunZi = false;
				}
			}
						
		}
		// ����жϼ�û����
		try {
			return getCount(type);
		} catch (Exception e) {
			// ������쳣�����ܷ���0�����Է��ط�0���κ���������һ�㲻�����쳣������
			return 1;
		}

	}

	/**
	 * �������ã���ʼ����
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
	 * ��ȡĳ����ɫ���Ƶ�������˳������Ƶ�������
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 *             ����Ƶ���������4������С��0���ᱨ�쳣
	 */
	private int getCount(int[] type) throws Exception {
		int count = 0;
		for (int i : type) {
			if (i < 0 || i > 4) {
				throw new IndexOutOfBoundsException("�Ƶ�������������");
			}
			count += i;
		}
		return count;
	}
	
	/**
	 * ������͸ܵĻ�ɫ�Ƿ�һ�£���� tongHua  �ֶΣ��ж��Ƿ���һɫ
	 * @param pengOrGang
	 * @return ����ͬ��ɫ�����ͣ���-1����ͬ����,-2���������޸ܣ�
	 */
	private int alike(int[][][] pengOrGang){
		//int   []     []       []     pengOrGang ��ֵΪ3��������4���Ǹ�
		//     �ڼ���    ʲô��������        ʲô��
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
	 * ����Ƿ����ƣ��÷������ڵ�һ���ƴ��ȥʱ���ж��Ƿ���������Ҳ���ڽ���ʱ�ж�������������ͣ�
	 * @param pengOrGang �����
	 * @param wan
	 *            �����ƣ�����0
	 * @param tiao
	 *            �����ƣ�����1
	 * @param tong
	 *            Ͳ���ƣ�����2
	 * @param judgment �о�(Ϊ���Ч�ʶ��ӵĲ���)��true��Ϊ�ж�������������ͣ����к������ȡ������󣩣�false��ֻ���ж��Ƿ�����
	 * @return
	 */
	public ModelType checkTing(int[][][] pengOrGang, int[] wan, int[] tiao, int[] tong,boolean judgment){
		ModelType mt = ModelType.NONE;
		for(int type = 0; type < 3; type++){
			for(int value = 1; value <= 9;value++){
				tryQiDui = true;
				ModelType tmp = checkHu(pengOrGang, wan, tiao, tong,type,value);
				if(tmp != ModelType.NONE){
					//���Խ��������ռ������ﲻ��Ҫ
					mt = mt.getId() < tmp.getId() ? tmp : mt;
					if(!judgment){
						return mt; //�����ϼ������������Ч��
					}
					
				}
			}
		}
		return mt;
	}
	
	
	/**
	 * ����Ƿ���ƣ����һ���Ʋ�Ϊ���������������ж��Ƿ����߶ԣ�
	 * 
	 * @param wan
	 *            �����ƣ�����0
	 * @param tiao
	 *            �����ƣ�����1
	 * @param tong
	 *            Ͳ���ƣ�����2
	 * @param type
	 *            ���һ���Ƶ�����[0-2]
	 * @param index
	 *            ���һ���Ƶ�ֵ[1-9]
	 * @return
	 */
	public ModelType checkHu(int[][][] pengOrGang, int[] awan, int[] atiao, int[] atong, int type, int index) {
		try{
			reset();
			if (awan == null || atiao == null || atong == null) {
				return ModelType.NONE;
			}
			// ���ÿ�������Ƿ���9��û���Ƶ�ҲҪ��0��ʾ
			if (awan.length != 9 || atiao.length != 9 || atong.length != 9) {
				return ModelType.NONE;
			}
			// ������һ����
			if (type < 0 || type > 3 || index < 1 || index > 9) {
				return ModelType.NONE;
			}
			
			//��������
			int[] wan = new int[awan.length];
			int[] tiao = new int[atiao.length];
			int[] tong = new int[atong.length];
			System.arraycopy(awan, 0, wan, 0, awan.length);
			System.arraycopy(atiao, 0, tiao, 0, atiao.length);
			System.arraycopy(atong, 0, tong, 0, atong.length);
			
			// �����һ��������
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

			// ����Ƶ������Ƿ��� 3n+2(n[0,4])
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
				// ���������쳣��GG,�Ƶ��������ԣ�û��Ҫ�����ж���ȥ��
				System.err.println("����������Ϊ0���»�4������");
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
				// �Ƶ��������ԣ����ܺ���
				System.err.println("���ܺ��ƣ��Ƶ�����Ϊ��" + count);
				return ModelType.NONE;
			}
						
			// �Ƿ����
			if ((wanCount == 0 || check(wan) == 0) && (tiaoCount == 0 || check(tiao) == 0)
					&& (tongCount == 0 || check(tong) == 0)) {
				//���͸ܵ������Ϣ�� -2(û�����͸�),-1(���͸ܷ�ͬ��),0(���͸ܶ���������),1(���͸ܶ���������),2(���͸ܶ���Ͳ����)
				int alike = alike(pengOrGang);
				ModelType result = ModelType.PING;// ƽ��
				if (!shunZi) {
					// û��˳�ӣ�����Ϊ����ӻ���С�߶ԣ������ݶ�Ϊ����ӣ�С�߶��������ж�
					result = ModelType.DA_DUI;
				}
				if (!canLong) {
					//С�߶Ե�������û��˳�ӣ�û�а��̣�û������û�и�
					if (alike == -2 && xiaoQiDui && !shunZi && !anKe) {
						// ��С�߶�
						result = ModelType.XIAO_QI_DUI;
					}
				} else {
					if (alike == -2 && xiaoQiDui && !shunZi && !anKe) {
						// �����߶�
						result = ModelType.LONG_QI_DUI;
					}
				}
				
				//�ж��ܷ�Ϊ��һɫ
				if (count == wanCount || count == tiaoCount || count == tongCount) {
					// ��ɫ��(��Ϊ��һɫ)
					
					if(alike == -2){
						//û���͸�
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
						// ����
						result = ModelType.QING_DUI;
					} else if (result == ModelType.XIAO_QI_DUI) {
						// ���߶�
						result = ModelType.QING_QI_DUI;
					} else if (result == ModelType.LONG_QI_DUI) {
						// ������
						result = ModelType.QING_LONG;
					} else {
						// ��һɫ
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
//		System.out.println("�����ʱ��" + (endTime - beginTime) + ",����=" + hu);
//	}

}
