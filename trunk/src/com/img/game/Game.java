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
	private Agent[] agents;
	private int state; // ----------tʱ���г�״̬��one of the
	// 2m strings of the m most
	// recent outcomes of the sign
	// of price changes.

	private ArrayList<Double> priceSeries; // -----��ʷ�۸����У����ȥ100�ּ۸�
	private int[] currentChoise;// ���ÿ��agent��ѡ�������
	private double currentPrice = 0;
	//private ArrayList<Agent> agents; // agents
	private int[] historyChoise;

	private int m; // ���䳤��
	private int s; // ��Խ����
	private int n; // agents����

	private int lastBuyNum;
	private int lastSellNum;
	private int lastholdNum;

	private int priceLength; // 100
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

		// loadHistory();
		t++;
		this.lastBuyNum = 0;
		this.lastSellNum = 0;
		this.lastholdNum = 0;
		for (int i = 0; i < (agents.length - 1); i++) {
			// System.out.println(historyChoise[i]);
			agents[i].agentAct(historyChoise[i]);// ������ʷ�������������Ҳ����action��ֵ��Ϊ-1����1
			// System.out.println("this turn historyChoise["+i+"]
			// is:"+historyChoise[i]);
			currentChoise[i] = (int) agents[i].getDecision();
			if (currentChoise[i] == -1) {
				lastBuyNum++;
			} else if (currentChoise[i] == 1) {
				lastSellNum++;
			} else if(currentChoise[i] == 0){
				lastholdNum++;
			}else {
				System.out.println("currentChoise[i] is:" + currentChoise[i]);
			}
			// System.out.println("current"+i+"Choise"+currentChoise[i]);
		}
		for (int i = 0; i < (agents.length); i++) {
			// agent[i].feedback(historyChoise[i],caculateThisTurnPrice(currentChoise),
			// i);
			agents[i].feedback(calTransctionPriceT());
			// agents[i].feedback(caculateThisTurnPrice(currentChoise),i);
			updateHistory(historyChoise, currentChoise, i);
		}
		// �õ����ֵļ۸� feedback to client
		currentPrice = calTransctionPriceT(currentChoise)
				+ agents[agents.length - 1].getDecision();
		this.updateAgentScore(agents);
		// System.out.println("currentPrice"+currentPrice);
		//System.out.println("currentPrice" + currentPrice);
	}

	/**
	 * �����ݿ�����۸�����realmarket_price_series
	 */
	public void loadPrice() {
		// TODO Auto-generated method stub
		getHistoryPrice(50);
	}

	/**
	 * load history from database
	 * 
	 * @param indexNum:
	 *            number of price,default is 60
	 * @return history price list
	 */
	public ArrayList<Double> getHistoryPrice(int indexNum) {
		String sqlString = "select price from price_info order by price_id desc limit "
				+ indexNum;
		ResultSet res;
		DatabaseOperation databaseOperation = new DatabaseOperation();
		if (databaseOperation.OpenConnection()) {
			priceSeries.clear();
			res = databaseOperation.ExecuteQuery(sqlString);
			// ��������
			try {
				while (res.next()) {
					double tempPrice = res.getDouble("price");
					priceSeries.add(tempPrice);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				databaseOperation.CloseConnection();
			}
		}
		return priceSeries;
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
		return lastholdNum;
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

	public ArrayList<Double> getPriceSeries() {
		return priceSeries;
	}

	public void setPrice_series(ArrayList<Double> price_series) {
		this.priceSeries = price_series;
	}

//	public ArrayList<Agent> getAgents() {
//		return agents;
//	}
//
//	public void setAgents(ArrayList<Agent> agents) {
//		this.agents = agents;
//	}

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

	public int getPriceLength() {
		return priceLength;
	}

	public void setPriceLength(int price_length) {
		priceLength = price_length;
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
