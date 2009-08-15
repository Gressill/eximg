package com.img.game;

import java.sql.ResultSet;
import java.util.ArrayList;

import server.DatabaseOperation;

import com.img.agent.Agent;

public class Game {

	private int t;// ----------轮数
	private double Pr; // ----------第t轮真实市场价格
	private double PrNext; // ----------第t+1轮的真实市场价格
	private int A; // ----------第t轮做决策时的决策积累量，extended demand
	private double Pt; // ----------第t轮交易价格 the tranction price
	private double PtNext; // ----------第t+1轮的交易价格
	private Agent[] agents;
	private int state; // ----------t时刻市场状态，one of the
	// 2m strings of the m most
	// recent outcomes of the sign
	// of price changes.

	private ArrayList<Double> priceSeries; // -----历史价格序列，如过去100轮价格
	private int[] currentChoise;// 存放每个agent的选择的数组
	private double currentPrice = 0;
	//private ArrayList<Agent> agents; // agents
	private int[] historyChoise;

	private int m; // 记忆长度
	private int s; // 策越个数
	private int n; // agents个数

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
	 * 当收到客服端发来的请求就Play game。
	 */
	public void Play() {

		// loadHistory();
		t++;
		this.lastBuyNum = 0;
		this.lastSellNum = 0;
		this.lastholdNum = 0;
		for (int i = 0; i < (agents.length - 1); i++) {
			// System.out.println(historyChoise[i]);
			agents[i].agentAct(historyChoise[i]);// 根据历史来决定买和卖，也就是action的值，为-1或者1
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
		// 得到该轮的价格 feedback to client
		currentPrice = calTransctionPriceT(currentChoise)
				+ agents[agents.length - 1].getDecision();
		this.updateAgentScore(agents);
		// System.out.println("currentPrice"+currentPrice);
		//System.out.println("currentPrice" + currentPrice);
	}

	/**
	 * 从数据库载入价格序列realmarket_price_series
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
			// 处理结果集
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
	 * 写入数据序列realmarket_price_series到数据库
	 */
	public void savePrice() {
		// TODO Auto-generated method stub

	}

	/**
	 * 根据记忆长度m获取市场状态state
	 * 
	 * @return
	 */
	public int getState() {
		// TODO Auto-generated method stub
		return state;
	}

	/**
	 * Game的agent,agent的strategy做决策(计算agent.a, strategy.v_a)
	 */
	public void makeDecision() {
		// TODO Auto-generated method stub

	}

	/**
	 * 计算策越积累量A=sum(agent.a)
	 */
	public void calA() {
		// TODO Auto-generated method stub

	}

	/**
	 * 计算T+1轮真实市场价格
	 */
	public void calRealmarketPriceT1() {
		// TODO Auto-generated method stub

	}

	/**
	 * 计算T时刻交易价格
	 */
	public void calTransctionPriceT() {
		// TODO Auto-generated method stub

	}

	/**
	 * 计算交易价格差 tranction_diff =
	 * tranction_price_series[T]-tranction_price_series[T-1].
	 */
	public void calTransactionDiff() {
		// TODO Auto-generated method stub

	}

	/**
	 * Game的agent,agent的strategy根据T和T-1时刻交易价格完成头寸，财富的更新计算
	 */
	public void doTransaction(int tranctionDiff) {
		// TODO Auto-generated method stub

	}

	/**
	 * 做交易完成后的工作，更新时间t至T+1
	 */
	public void finishTrans() {
		// TODO Auto-generated method stub

	}

	/**
	 * 向客户端输出交易完成后各种信息：真人的财富，agent, strategy的信息等
	 */
	public void explore() {
		// TODO Auto-generated method stub

	}

	/**
	 * 返回真实财富序列
	 */
	public void getRealmarketPriceSeries() {
		// TODO Auto-generated method stub

	}

	/**
	 * 返回买的人数
	 */
	public int getBuyNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 返回卖的人数
	 */
	public int getSellNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 返回不做操作人数
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
