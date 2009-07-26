package com.img.game;

public class Strategy {
	int t = 0; // ---------轮数
	int m = 0; // ---------记忆长度
	int outcome[] = null; // ---策越决定，length = 2^m, value = -1,0, or +1.
	int v_a = 0; // ---------策略的虚拟决策
	int v_k = 0; // ---------策略的虚拟头寸
	int Max_k = 5; // ---------最大头寸
	int v_wealth = 0; // ---------策略的虚拟财富

	/**
	 * 初始化
	 */
	public Strategy() {
	}

	/**
	 * strategy做出决策
	 * @param state
	 */
	public void make_virtual_decision(int state) {
	} 

	/**
	 * 更新头寸，财富等
	 * @param tranction_diff
	 */
	public void updateVirtualInfo(int tranction_diff) {
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
