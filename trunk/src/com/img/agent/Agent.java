package com.img.agent;

import com.img.game.Strategy;

public class Agent extends Strategy {

	private int t; // ����
	private int k; /* ͷ�磬ͷ���ṩ��һ���򵥵�ģ����ʵ�г������޲Ƹ���Ӱ�� */
	private int MaxK; // ���ͷ�磬���ͷ��������ͷ���������ã���ͷ��ľ���ֵ�������ͷ�������ʱ��ͷ��ľ���ֵ�������ӣ�ģ�͵�һ������ǲƸ����пɼ��ԣ������ǿɳ��ԡ�
	private double cash; // �ֽ���
	private double wealth; // ��ʵ�Ƹ�

	private int strategy[];// ��Խ

	private int a; // ������trading decision.
	private int s; // ��Խ����
	private int BUY; // +1
	private int SELL; // -1
	private int HOLD; // 0

	public Agent() {

	}

	/**
	 * agent��ÿ������������
	 */
	public void strategyMakeDecision(int state) {

	}

	/**
	 * ��strategy��ѡ�����Ų���
	 */
	public void chooseStrategy() {

	}

	/**
	 * ���Ը�������ͷ�磬�Ƹ�
	 */
	public void strategyUpdateInfo(int tranctionDiff) {

	}

	/**
	 * ����ͷ�磬�Ƹ�
	 */
	public void updateInfo(int tranctionDiff) {

	}

	/**
	 * agent�����ף�����������������
	 */
	public void doTransaction(int tranctionDiff) {

	}

	/**
	 * ����Tʱ�̽��׼۸�
	 */
	public void calTransctionPriceT() {

	}

	/**
	 * ������ɺ󣬸���ʱ���ɨβ����
	 */
	public void finishTrans() {

	}

	/**
	 * ��ͳ�ƣ��緵��best worst, mid agent��
	 */
	public void getAnalysis() {

	}

	/**
	 * ���ؾ���
	 */
	public int getDecision() {
		return 0;
	}

	/**
	 * ��������Ƹ�����
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
