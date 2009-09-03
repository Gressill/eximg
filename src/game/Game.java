package game;

import java.text.DecimalFormat;

import util.Constant;
import agents.Agent;
import agents.Strategy;

public class Game implements Strategy {

	private int turns = 0; // turns

	// history space
	private int P = 8;// 1 << 16;

	private int[] historyChoise;

	private int[] currentChoise;// 存放每个agent的选择的数组

	private double currentPrice = 0;

	private Agent[] agents;

	private double[] agentScoreInfo = new double[3];

	private int lastBuyNum = 0;

	private int lastSellNum = 0;

	private int lastHoldNum = 0;

	private int tempLastHoldNum = 0;

	private int tempLastBuyNum = 0;

	private int tempLastSellNum = 0;

	private int upOrDown = 0;

	private int humanAction = 0;

	private static int historyChoiseStatic;

	private static double lastExpectPrice;

	//
	public Game(Agent[] agents) {
		this.agents = agents; // agents
		this.P = 1 << Constant.MEMORYSIZE;
		lastExpectPrice = Constant.FIRSTPRICE;
		currentChoise = new int[agents.length];
		historyChoise = new int[agents.length];
		historyChoiseStatic = (int) (P * Math.random());
		for (int i = 0; i < (agents.length - 1); i++) {
			historyChoise[i] = (int) (P * Math.random());// random number
			// between 0 to p:8
		}

	}

	public void playGame() {

		// turns = 0;
		// loadHistory();
		turns++;
		this.lastBuyNum = 0;
		this.lastSellNum = 0;
		this.lastHoldNum = 0;
		this.tempLastBuyNum = 0;
		this.tempLastHoldNum = 0;
		this.tempLastSellNum = 0;
		this.upOrDown = 0;
		this.humanAction = agents[agents.length - 1].getAction();
		for (int i = 0; i < (agents.length - 1); i++) {
			agents[i].agentAct(historyChoiseStatic, i);//根据历史来决定买和卖，也就是action的值，
			// 为-1
			// 或者1
			currentChoise[i] = (int) agents[i].getAction();
			if (currentChoise[i] == -1) {
				lastBuyNum++;
			} else if (currentChoise[i] == 1) {
				lastSellNum++;
			} else if (currentChoise[i] == 0) {
				lastHoldNum++;
			} else {
				System.err.println("currentChoise[" + i + "] is:"
						+ currentChoise[i]);
			}
			// System.out.println("current"+i+"Choise"+currentChoise[i]);
		}
		if (humanAction == -1) {
			tempLastBuyNum = lastBuyNum + 1;
			tempLastSellNum = lastSellNum;
			tempLastHoldNum = lastHoldNum;
		} else if (humanAction == 1) {
			tempLastBuyNum = lastBuyNum;
			tempLastSellNum = lastSellNum + 1;
			tempLastHoldNum = lastHoldNum;
		} else if (humanAction == 0) {
			tempLastBuyNum = lastBuyNum;
			tempLastSellNum = lastSellNum;
			tempLastHoldNum = lastHoldNum + 1;
		} else {
			System.err.println("humanAction is:" + humanAction
					+ "this is file game.java L104.");
		}
		if (tempLastBuyNum > tempLastSellNum) {
			this.upOrDown = -1;
		} else if (tempLastBuyNum < tempLastSellNum) {
			this.upOrDown = 1;
		} else if (tempLastBuyNum == tempLastSellNum) {
			this.upOrDown = humanAction;
		} else {
			System.err.println("this error is file game.java L113!");
			System.err.println("tempLastBuyNum is:" + tempLastBuyNum);
			System.err.println("tempLastSellNum is:" + tempLastSellNum);
			System.err.println("tempLastHoldNum is:" + tempLastHoldNum);
		}
		// 得到该轮的价格 feedback to client
		currentPrice += caculatePrice(currentChoise) + humanAction;
		currentPrice = caculateTransPrice(currentPrice);
		for (int i = 0; i < (agents.length); i++) {
			// agents[i].feedback(caculateThisTurnPrice(currentChoise));
			agents[i].feedback(currentPrice, i);
			// updateHistory(historyChoise, currentChoise, i);
		}
		updateHistory(upOrDown);
		// System.out.println(agents[agents.length-1].getClass());
		this.updateAgentScore(agents);
		System.out.println("currentPrice" + currentPrice);

	}

	/**
	 * 计算实际交易的价格
	 * 
	 * @param currentChoise
	 *            ：储存该轮的所有agent的决定（买或者卖,1/-1）
	 * @return currentPrice：每一轮的价格
	 */
	private double caculateTransPrice(Double thisTurnPrice) {
		double tempTransPrice = 0;
		tempTransPrice = (1 - Constant.BETA) * lastExpectPrice + Constant.BETA
				* thisTurnPrice;
		lastExpectPrice = thisTurnPrice;
		return tempTransPrice;
	}

	/**
	 * 计算该轮期望交易的价格
	 * 
	 *            ：储存该轮的所有agent的决定（买或者卖,1/-1）
	 * @return currentPrice：每一轮的价格
	 */
	private double caculatePrice(int[] currentChoise) {

		double tempCurrentPrice = 0;
		for (int i = 0; i < currentChoise.length; i++) {
			tempCurrentPrice += currentChoise[i];
		}
		tempCurrentPrice = Math.signum(tempCurrentPrice)
				* Math.pow(Math.abs(tempCurrentPrice), Constant.ALPHA);
		return tempCurrentPrice;
	}

	/**
	 * strategiesArray = new int[strategiesNum][historySize];<br>
	 * then this we may base on virtualScores to determin param "strategiesNum",<br>
	 * and we use historyChoise to determing param "historySize".<br>
	 * for example:<br>
	 * in t turns,strategy one's virtualScores is highter,so MGagent action with
	 * strategiesArray[1][historyChoise[t]]
	 * 
	 * @param historyChoise
	 *            : History choice array,we add that <br>
	 *            for example:<br>
	 *            1 0 1 (historial fluctuation)<br>
	 *            -----><br>
	 *            1*2^2 + 0*2^1 + 1*2^0 (strategy's choose index)<br>
	 * 
	 * @param currentChoise
	 *            : this turns agents choice.
	 */

	private void updateHistory(int upOrDown) {

		// System.out.println("agent["+i+"]Choise = "+historyChoise[i]);
		int tempCurrentChoise = 0;
		if (upOrDown == -1) {
			tempCurrentChoise = 0;
		} else if (upOrDown == 1) {
			tempCurrentChoise = 1;
		}
		System.err.println("history is :" + historyChoiseStatic);
		System.err.println("tempCurrentChoise is :" + tempCurrentChoise);
		historyChoiseStatic = ((2 * historyChoiseStatic) + tempCurrentChoise)
				% P;
		System.err.println("now is :" + historyChoiseStatic);
	}

	private void updateHistoryForHold(int historyChoise[], int currentChoise[],
			int i) {

		// System.out.println("agent["+i+"]Choise = "+historyChoise[i]);
		int tempCurrentChoise = 1;
		if (currentChoise[i] == -1) {
			tempCurrentChoise = 0;
		} else if (currentChoise[i] == 0) {
			tempCurrentChoise = 1;
		} else if (currentChoise[i] == 1) {
			tempCurrentChoise = 2;
		}
		historyChoise[i] = ((3 * historyChoise[i]) + tempCurrentChoise) % P;
	}

//	private void caculateVolatility() {
//	}

	/**
	 * this to return best,worse,average score of agents
	 * 
	 * @return an array A ,A[0] = worse score, A[1] = average score, A[2] = best
	 *         score
	 */
	public double[] getAgentScore() {

		DecimalFormat daDecimalFormat = new DecimalFormat("########.00");
		// 四舍五入
		agentScoreInfo[0] = Double.parseDouble(daDecimalFormat
				.format(agentScoreInfo[0]));
		agentScoreInfo[1] = Double.parseDouble(daDecimalFormat
				.format(agentScoreInfo[1]));
		agentScoreInfo[2] = Double.parseDouble(daDecimalFormat
				.format(agentScoreInfo[2]));
		return agentScoreInfo;
	}

	public double getHumanScoreInfo() {
		return agents[agents.length].getScore();
	}

	/**
	 * update agents score,best,worse,average score of agents
	 * 
	 * @param agents
	 *            : all agents
	 */
	public void updateAgentScore(Agent[] agents) {

		// double avgAgentScore = 0;
		// double bestAgentScore = 0;
		// double worseAgentScore = 0;
		// System.out.println("agents is:"+agents.length);
		// avgAgentScore = agents[22].getScore();
		// System.out.println("agents score is:" + agents[500].getScore());
		for (int i = 0, j = (agents.length - 1); i < j; i++) {
			if (agents[i].getScore() < this.agentScoreInfo[0]) {

				this.agentScoreInfo[0] = agents[i].getScore();
			}

			if (agents[i].getScore() > this.agentScoreInfo[2]) {

				this.agentScoreInfo[2] = agents[i].getScore();
			}

			this.agentScoreInfo[1] = this.agentScoreInfo[1]
					+ agents[i].getScore();
		}
		this.agentScoreInfo[1] = this.agentScoreInfo[1] / (agents.length - 1);
		// Arrays.sort(agents);
		// this.agentScoreInfo[0] = worseAgentScore;
		// this.agentScoreInfo[1] = avgAgentScore;
		// this.agentScoreInfo[2] = bestAgentScore;
		// System.out.println("agent:--best is:"+this.agentScoreInfo[2]+"avg
		// is:"+this.agentScoreInfo[1]+"worse is:"+this.agentScoreInfo[0]);
	}

	/**
	 * Initialize the agent strategy
	 */
	public Strategy initStrategy() {
		// TODO Auto-generated method stub
		return null;
	}

	public int[] getCurrentChoise() {
		return currentChoise;
	}

	public double getCurrentPrice() {
		return this.currentPrice;
	}

	public int getLastBuyNum() {
		// System.out.println(lastBuyNum);
		return lastBuyNum;
	}

	public int getLastSellNum() {
		// System.out.println(lastSellNum);
		return lastSellNum;
	}

	public static void setLastExpectPrice(double lastExpectPrice) {
	    Game.lastExpectPrice = lastExpectPrice;
	}

	public static double getLastExpectPrice() {
	    return lastExpectPrice;
	}

}