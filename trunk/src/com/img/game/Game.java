package com.img.game;

import java.sql.ResultSet;
import java.util.ArrayList;

import server.DatabaseOperation;

import com.img.agent.Agent;

public class Game {

	private int t;// ----------����
	private double Pr; // ----------��t����ʵ�г��۸�
	private double PrNext; // ----------��t+1�ֵ���ʵ�г��۸�
	private int A; // ----------��t��������ʱ�ľ��߻�������extended demand
	private double Pt; // ----------��t�ֽ��׼۸� the tranction price
	private double PtNext; // ----------��t+1�ֵĽ��׼۸�
	private int state; // ----------tʱ���г�״̬��one of the
	// 2m strings of the m most
	// recent outcomes of the sign
	// of price changes.

	private ArrayList<Double> price_series; // -----��ʷ�۸����У����ȥ100�ּ۸�

	private ArrayList<Agent> agents; // agents

	private int m; // ���䳤��
	private int s; // ��Խ����
	private int n; // agents����

	private int PRICE_LENGTH; // 100
	private double GAMA; // ----------the sensitivity of price increment.
	private double BETA; // ----------the market impact factor.

	public Game() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param memory
	 *            number
	 * @param agent
	 *            number
	 * @param strategy
	 *            number
	 */
	public Game(int memory, int strategy, int agent) {
		m = memory;
		s = strategy;
		n = agent;
		
		loadPrice();
	}

	/**
	 * ���յ��ͷ��˷����������Play game��
	 */
	public void Play() {

	}

	/**
	 * �����ݿ�����۸�����realmarket_price_series
	 */
	public void loadPrice() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * load history from database
	 * @param indexNum: number of price,default is 60
	 * @return history price list
	 */
	public ArrayList<Double> getHistoryPrice(int indexNum) {
		String sqlString = "select price from price_info order by price_id desc limit "+indexNum;
		ResultSet res;
		DatabaseOperation databaseOperation = new DatabaseOperation();
		if(databaseOperation.OpenConnection())
		{
			price_series.clear();
			res = databaseOperation.ExecuteQuery(sqlString);
			//��������
			try {
				while (res.next()) {
					double tempPrice = res.getDouble("price");
					price_series.add(tempPrice);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				databaseOperation.CloseConnection();
			}
		}
		return price_series;
	}

	/**
	 * д����������realmarket_price_series�����ݿ�
	 */
	public void savePrice() {
		// TODO Auto-generated method stub

	}

	/**
	 * ���ݼ��䳤��m��ȡ�г�״̬state
	 * 
	 * @return
	 */
	public int getState() {
		// TODO Auto-generated method stub
		return state;
	}

	/**
	 * Game��agent,agent��strategy������(����agent.a, strategy.v_a)
	 */
	public void makeDecision() {
		// TODO Auto-generated method stub

	}

	/**
	 * �����Խ������A=sum(agent.a)
	 */
	public void calA() {
		// TODO Auto-generated method stub

	}

	/**
	 * ����T+1����ʵ�г��۸�
	 */
	public void calRealmarketPriceT1() {
		// TODO Auto-generated method stub

	}

	/**
	 * ����Tʱ�̽��׼۸�
	 */
	public void calTransctionPriceT() {
		// TODO Auto-generated method stub

	}

	/**
	 * ���㽻�׼۸�� tranction_diff =
	 * tranction_price_series[T]-tranction_price_series[T-1].
	 */
	public void calTransactionDiff() {
		// TODO Auto-generated method stub

	}

	/**
	 * Game��agent,agent��strategy����T��T-1ʱ�̽��׼۸����ͷ�磬�Ƹ��ĸ��¼���
	 */
	public void doTransaction(int tranctionDiff) {
		// TODO Auto-generated method stub

	}

	/**
	 * ��������ɺ�Ĺ���������ʱ��t��T+1
	 */
	public void finishTrans() {
		// TODO Auto-generated method stub

	}

	/**
	 * ��ͻ������������ɺ������Ϣ�����˵ĲƸ���agent, strategy����Ϣ��
	 */
	public void explore() {
		// TODO Auto-generated method stub

	}

	/**
	 * ������ʵ�Ƹ�����
	 */
	public void getRealmarketPriceSeries() {
		// TODO Auto-generated method stub

	}

	/**
	 * �����������
	 */
	public int getBuyNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * ������������
	 */
	public int getSellNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * ���ز�����������
	 */
	public int getKeepNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public double getPr() {
		return Pr;
	}

	public void setPr(double pr) {
		Pr = pr;
	}

	public double getPrNext() {
		return PrNext;
	}

	public void setPrNext(double PrNext) {
		PrNext = PrNext;
	}

	public int getA() {
		return A;
	}

	public void setA(int a) {
		A = a;
	}

	public double getPt() {
		return Pt;
	}

	public void setPt(double pt) {
		Pt = pt;
	}

	public double getPtNext() {
		return PtNext;
	}

	public void setPtNext(double ptNext) {
		PtNext = ptNext;
	}

	public void setState(int state) {
		this.state = state;
	}

	public ArrayList<Double> getPrice_series() {
		return price_series;
	}

	public void setPrice_series(ArrayList<Double> price_series) {
		this.price_series = price_series;
	}

	public ArrayList<Agent> getAgents() {
		return agents;
	}

	public void setAgents(ArrayList<Agent> agents) {
		this.agents = agents;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public int getS() {
		return s;
	}

	public void setS(int s) {
		this.s = s;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getPRICE_LENGTH() {
		return PRICE_LENGTH;
	}

	public void setPRICE_LENGTH(int price_length) {
		PRICE_LENGTH = price_length;
	}

	public double getGAMA() {
		return GAMA;
	}

	public void setGAMA(double gama) {
		GAMA = gama;
	}

	public double getBETA() {
		return BETA;
	}

	public void setBETA(double beta) {
		BETA = beta;
	}

}
