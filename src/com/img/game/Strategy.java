package com.img.game;

import util.Constant;

public class Strategy {
	private int t = 0; // ---------轮数
	private int m = 0; // ---------记忆长度
	private int s = 0; // ---------strategyNum
	private int outcome[] = null; // ---策越决定，length = 2^m, value = -1,0, or +1.
	private int v_a = 0; // ---------策略的虚拟决策
	private int v_k = 0; // ---------策略的虚拟头寸
	private int maxK = 5; // ---------最大头寸
	private int vWealth = 0; // ---------策略的虚拟财富
	private int historySize = 0;
	private int strategiesArray[][] = null;

	/**
	 * 构造函数
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
	 * 初始化策略
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
	 * strategy做出决策
	 * @param state
	 */
	public int makeVirtualDecision(int state) {
		return 1;
	}

	/**
	 * 更新头寸，财富等
	 * @param tranction_diff
	 */
	public void updateVirtualInfo(int tranctionDiff) {
	}

	/**
	 * 交易完成后，更新时间等扫尾工作
	 */
	public void finishTrans() {
	}

	/**
	 * 返回虚拟决策
	 */
	public void getVirtualDecision() {
	}

	/**
	 * 返回虚拟财富序列
	 */
	public void getVirtualWealth() {
	}
}
