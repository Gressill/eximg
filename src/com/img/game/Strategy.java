package com.img.game;

public class Strategy {
	int t = 0; // ---------����
	int m = 0; // ---------���䳤��
	int outcome[] = null; // ---��Խ������length = 2^m, value = -1,0, or +1.
	int v_a = 0; // ---------���Ե��������
	int v_k = 0; // ---------���Ե�����ͷ��
	int Max_k = 5; // ---------���ͷ��
	int v_wealth = 0; // ---------���Ե�����Ƹ�

	/**
	 * ��ʼ��
	 */
	public Strategy() {
	}

	/**
	 * strategy��������
	 * @param state
	 */
	public void make_virtual_decision(int state) {
	} 

	/**
	 * ����ͷ�磬�Ƹ���
	 * @param tranction_diff
	 */
	public void updateVirtualInfo(int tranction_diff) {
	}

	/**
	 * ������ɺ󣬸���ʱ���ɨβ����
	 */
	public void finishTrans() {
	}

	/**
	 * �����������
	 */
	public void getVirtualDecision() {
	} 

	/**
	 * ��������Ƹ�����
	 */
	public void getVirtualWealth() {
	}
}
