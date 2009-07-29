package com.img.game;

import util.Constant;

public class Strategy {
	private int t = 0; // ---------����
	private int m = 0; // ---------���䳤��
	private int s = 0; // ---------strategyNum
	private int outcome[] = null; // ---��Խ������length = 2^m, value = -1,0, or +1.
	private int v_a = 0; // ---------���Ե��������
	private int v_k = 0; // ---------���Ե�����ͷ��
	private int maxK = 5; // ---------���ͷ��
	private int vWealth = 0; // ---------���Ե�����Ƹ�
	private int historySize = 0;
	private int strategiesArray[][] = null;

	/**
	 * ���캯��
	 * @param strategyNum: the number of agent strategy
	 */
	public Strategy(int strategyNum) {
		historySize = 1 << m; // Size of history space
		s = strategyNum;
		strategiesArray = new int[s][historySize]; // The
		initStrategy();
	}
	
	public Strategy() {
	}

	/**
	 * ��ʼ������
	 */
	private void initStrategy() {
		double randomNumber = 0;
		for (int i = 0; i < s; i++) {
			// virtualScores[i] = 0;
			for (int j = 0; j < m; j++) {
				randomNumber = Math.random();
				// this is how the game works,use random num to simulate the
				// people's select & init strategies
				if (randomNumber <= 0.33) {
					strategiesArray[i][j] = Constant.SELL_CHOISE;
				} else if (randomNumber <= 0.66) {
					strategiesArray[i][j] = Constant.HOLD_CHOISE;
				} else {
					strategiesArray[i][j] = Constant.BUY_CHOISE;
				}
			}
		}
		//return strategiesArray;
	}

	/**
	 * strategy��������
	 * @param state
	 */
	public int makeVirtualDecision(int state) {
		return 1;
	}

	/**
	 * ����ͷ�磬�Ƹ���
	 * @param tranction_diff
	 */
	public void updateVirtualInfo(int tranctionDiff) {
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
