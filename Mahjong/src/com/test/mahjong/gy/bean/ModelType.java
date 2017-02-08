package com.test.mahjong.gy.bean;
/**
 * ��������
 * @author Administrator
 *
 */
public enum ModelType {
	// �����ţ�������������
	/**
	 * û���ƻ�û����
	 */
	NONE(0,0,"δ��/����"),
	/**
	 * ƽ��
	 */
	PING(1,1,"ƽ��"),
	/**
	 * �����
	 */
	DA_DUI(2,3,"�����"),
	/**
	 * С�߶�
	 */
	XIAO_QI_DUI(3,3,"С�߶�"),
	/**
	 * ���߶�
	 */
	LONG_QI_DUI(4,4,"���߶�"),
	/**
	 * ��һɫ
	 */
	QING_YI_SE(5,4,"��һɫ"),
	/**
	 * ����
	 */
	QING_DUI(6,4*3,"����"),
	/**
	 * ���߶�
	 */
	QING_QI_DUI(7,4*3,"���߶�"),
	/**
	 * ������
	 */
	QING_LONG(8,4*4,"������"),
	/**
	 * ����
	 */
	TIAN_TING(9,4,"����"),
	/**
	 * �غ����м�����һ���ƺ��ƣ�
	 */
	DI_HU(10,8,"�غ�"),
	/**
	 * �����ׯ������һ���ƺ��ƣ�
	 */
	TIAN_HU(11,8,"���")
	;
	
	int id;
	int multiple;
	String text;
	
	ModelType(int id,int multiple,String text){
		this.id = id;
		this.multiple = multiple;
		this.text = text;
	}
	
	public int getId(){
		return id;
	}
	
	public int getMultiple() {
		return multiple;
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	public static ModelType getModelTypeById(int id){
		for(ModelType mt : ModelType.values()){
			if(mt.id == id){
				return mt;
			}
		}
		return null;
	}
}
