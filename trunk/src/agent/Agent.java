package agent;

public abstract class Agent {

	/**
	 * strategy_type
	 * 策越的类型：
	 * true：非混合型
	 * false: 混合型
	 */
	private  boolean 	strategy_type 	= true;
	/**
	 * action
	 * 代理的决策动作：
	 * 1：看涨
	 * 0：无动作
	 * -1：看跌
	 */
	protected int    	action 		= 0;//
	/**
	 * gain
	 * 代理的获利
	 * 为负值时表示获利为负数
	 */
	protected double 	gain 			= 0;//
	protected int[][]	determining;//
	private double 	 	score 			= 0;

	public void setGain(double gain) {
		
		this.gain = gain;
	}

	public double getGain() {
		
		return gain;
	}

	public void setDetermin(int[][] det) {
		
		this.determining = det;

	}

	public int[][] getDetermin() {

		return determining;
	}

	public boolean agentAct(int historyChoise) {
		return false;
	}
	
	public int getAction() {
		
		return action;
	}

	/**
	 * 
	 * receive feedback message such as maxprice,maxgain,minprice,mingain,avgprice,avgain
	 * 
	 */

	public boolean feedback(int thisTurnPrice) {

		return true;
	}
	
	public double caculateScore() {
		return score;
	}

	public double getScore() {
		// TODO Auto-generated method stub
		return 0;
	}

}
