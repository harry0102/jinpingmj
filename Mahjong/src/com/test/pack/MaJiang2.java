package com.test.pack;

public class MaJiang2 {
	private boolean jiang = false;// �Ƿ��ҵ���

	public MaJiang2() {
	}

	// ��ɫ���ж������ɫ�Ƿ񱻼����ˣ����û�м�����Ȼû�кͣ�

	private int huase(int[] hua) {
		if (hua.length == 7) {
			// �ж��֣��ԱȽ����⣬û��˳
			for (int i = 0; i < hua.length; i++) {
				if (hua[i] == 3 || hua[i] == 4) {
					hua[i] = 0;
					huase(hua);
				}
				// ��������������϶��ǽ�
				if (hua[i] == 2 && !jiang) {
					hua[i] = 0;
					jiang = true;
					huase(hua);
				}
			}
		} else {
			for (int i = 0; i < hua.length; i++) {
				// ���û�н����Ȱѽ�����ȥ
				if (!jiang && hua[i] >= 2) {
					hua[i] = hua[i] - 2;
					jiang = true;
					int fanhui = huase(hua);
					// ����ݹ��������û�м��꣬��ѽ��ӻ�ȥ
					if (fanhui != 0) {
						hua[i] = hua[i] + 2;
						jiang = false;
					}
				}
				// ��
				if (hua[i] != 0 && i < 7 && hua[i + 1] != 0 && hua[i + 2] != 0) {
					hua[i]--;
					hua[i + 1]--;
					hua[i + 2]--;
					huase(hua);
					int fanhui = huase(hua);
					// ����ݹ��������û�м��꣬��ȥ�ļӻ�ȥ
					if (fanhui != 0) {
						hua[i]++;
						hua[i + 1]++;
						hua[i + 2]++;
					}
				}
				if (hua[i] == 3 || hua[i] == 4) {
					int temp = hua[i];
					hua[i] = 0;
					huase(hua);
					int fanhui = huase(hua);
					// ����ݹ��������û�м��꣬��ȥ�ļӻ�ȥ
					if (fanhui != 0) {
						hua[i]++;
						hua[i] = temp;
					}
				}
			}
		}
		int re = 0;
		// ����жϼ�û����
		for (int i = 0; i < hua.length; i++) {
			re = re + hua[i];
		}
		return re;
	}

	public boolean Hu(int[] aWan, int[] aTong, int[] aTiao, int[] aZi)// aTiao
	{

		if (huase(aZi) == 0 && huase(aWan) == 0 && huase(aTong) == 0 && huase(aTiao) == 0 && jiang) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		MaJiang2 mj = new MaJiang2();
		int[] w = { 2, 2, 0, 2, 0, 2, 2, 2, 2 };
		int[] to = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int[] ti = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int[] z = { 0, 0, 0, 0, 0, 0, 0 };
		long beginTime = System.currentTimeMillis();
		boolean hu = mj.Hu(w, to, ti, z);
		long endTime = System.currentTimeMillis();
		System.out.println("��ʱ��" + (endTime - beginTime) + "hu=" + hu);
	}
}