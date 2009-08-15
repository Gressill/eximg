package com.img.agent;

import com.img.game.Strategy;

public class Agent extends Strategy {

	private int t; // 轮数
	private int k; /* 头寸，头寸提供了一个简单的模拟真实市场中有限财富的影响 */
	private int MaxK; // 最大头寸，最大头寸起限制头寸过大的作用，当头寸的绝对值到达最大头寸的限制时，头寸的绝对值不再增加，模型的一个结果是财富具有可加性，而不是可乘性。
	private double cash; // 现金量
	private double wealth; // 真实财富

	private int strategy[];// 策越

	private int a; // 决定，trading decision.
	private int s; // 策越个数
	private int BUY; // +1
	private int SELL; // -1
	private int HOLD; // 0

	public Agent() {

	}

	/**
	 * agent的每个策略做决策
	 */
	public void strategyMakeDecision(int state) {

	}

	/**
	 * 从strategy中选择最优策略
	 */
	public void chooseStrategy() {

	}

	/**
	 * 策略更虚拟新头寸，财富
	 */
	public void strategyUpdateInfo(int tranctionDiff) {

	}

	/**
	 * 更新头寸，财富
	 */
	public void updateInfo(int tranctionDiff) {

	}

	/**
	 * agent做交易，调用以上两个函数
	 */
	public void doTransaction(int tranctionDiff) {

	}

	/**
	 * 计算T时刻交易价格
	 */
	public void calTransctionPriceT() {

	}

	/**
	 * 交易完成后，更新时间等扫尾工作
	 */
	public void finishTrans() {

	}

	/**
	 * 做统计，如返回best worst, mid agent等
	 */
	public void getAnalysis() {

	}

	/**
	 * 返回决策
	 */
	public int getDecision() {
		return 0;
	}

	/**
	 * 返回虚拟财富序列
	 */
	public double getWealth() {
		return wealth;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public int getMaxK() {
		return MaxK;
	}

	public void setMaxK(int maxK) {
		MaxK = maxK;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public void setWealth(double wealth) {
		this.wealth = wealth;
	}

	public int[] getStrategy() {
		return strategy;
	}

	public void setStrategy(int[] strategy) {
		this.strategy = strategy;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getS() {
		return s;
	}

	public void setS(int s) {
		this.s = s;
	}
}
