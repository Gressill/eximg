package agent;

public abstract class Agent {

	/**
	 * strategy_type
	 * ��Խ�����ͣ�
	 * true���ǻ����
	 * false: �����
	 */
	private  boolean 	strategy_type 	= true;
	/**
	 * action
	 * ����ľ��߶�����
	 * 1������
	 * 0���޶���
	 * -1������
	 */
	protected int    	action 		= 0;//
	/**
	 * gain
	 * ����Ļ���
	 * Ϊ��ֵʱ��ʾ����Ϊ����
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
