package agents;

import game.Game;

import java.lang.Math;
import util.Constant;

public class MGAgent extends Agent {

    private int historySize; // Size of history space =
    // (actionChooseNumber^memorySize) where agent
    // looks default as 27
    // at last M time steps

    private int strategySize; // Number of strategies default as 2

    private int[][] strategiesArray; // The strategies: an
    // (strategiesNum*historySize) array
    
    private int[][] virtualPosition; // The strategies: an
    // (strategiesNum*historySize) array

    private double[] virtualScores; // The strategies' virtual scores

    private double[] virtualWealth;

    private int[] actionArray;

    private static int historyState;

    private int realPosition;

    private int determining; // Array determining active strategy

    private int mgAgentScore = 0;

    public MGAgent(int memorySize, int strategySize) {

	historySize = 1 << memorySize; // Size of history space
	this.strategySize = strategySize;
	realPosition = 0;

	virtualPosition = new int[strategySize][historySize];
	strategiesArray = new int[strategySize][historySize]; // The
	// strategies:
	// an
	// (strategiesNum*historySize)
	// array
	virtualScores = new double[strategySize];
	virtualWealth = new double[strategySize];
	//position = new int[Constant.k];
	determining = (int) (strategySize * Math.random());

	this.InitStrategy();
    }

    /**
     * init strategies
     * 
     * @return strategies array
     */
    public int[][] InitStrategy() {

	double randomNumber = 0;
	for (int i = 0; i < strategySize; i++) {
	    // virtualScores[i] = 0;
	    for (int j = 0; j < historySize; j++) {
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
	return strategiesArray;
    }

    /**
     * 根据历史来决定这轮的选择
     * 
     * @param historyChoise
     *            : historyChoise.
     * @see agent.Agent#act(int)
     * @param action
     *            : this turn agent's choice should be 0 or 1;
     */
    public boolean agentAct(int historyChoise,int agentNum) {
	// 所有的策略根据局势作出选择，不过虚分值计算改变了，不是每次去加减多少分，而是直接用头寸来计算
	int tempPosition = 0;
	for (int i = 0; i < strategySize; i++) {
	    tempPosition = virtualPosition[agentNum][i] + strategiesArray[i][historyChoise];
	    if (Math.abs(tempPosition) <= Constant.k) {
		virtualPosition[agentNum][i] = tempPosition;
		//position[i] = tempPosition;
	    }
	}
	//
	historyState = historyChoise;
	action = strategiesArray[determining][historyChoise];
	// System.out.println("strategiesArray[1][" + historyChoise + "]");

	return true;
    }

    /**
     * update virtualscores and decide determining
     * 
     * @see agent.Agent#feedback(double, int)
     * @param historyChoise
     *            : History choice array
     * @param thisTurnPrice
     *            : this turn price
     * @param num
     *            :just for test,meanness
     */

    // public boolean feedback(int historyChoise, int thisTurnPrice,int num) {
    public boolean feedback(int thisTurnPrice, int j,int t) {
	// update virtuakScore and agents score
	if (thisTurnPrice * action < 0) {
	    // 虚分值
	    virtualScores[determining]++;
	    // 实际分数
	    mgAgentScore = mgAgentScore + Math.abs(thisTurnPrice);
	} else if (thisTurnPrice * action > 0) {
	    virtualScores[determining]--;
	    mgAgentScore = mgAgentScore - Math.abs(thisTurnPrice);

	}
	// // 根据历史虚分值选择策略
	for (int i = 0; i < strategySize; ++i) {
	    if (virtualScores[i] > virtualScores[determining]) {
		determining = i;
	    }
	    // System.out.println("agent["+num+"]v[" + i + "]=" +
	    // virtualScores[i]);
	}
	return true;
    }

    public boolean feedback(int realTransPrice,int agentNum) {
	// update position and agents score
	if (action < 0) {
	    // 实际分数
	    realPosition --;
	    //mgAgentScore = mgAgentScore + Math.abs(thisTurnPrice);
	} else if (action >0) {
	    realPosition ++;
	} else if (action == 0) {
	} else {
	    System.err.println("File:mgagent.java,L148 thisTurnPrice is:"
		    + realTransPrice);
	}
	mgAgentScore = realPosition*realTransPrice;
//	for (int i = 0; i < virtualWealth.length; i++) {
//	    if (thisTurnPrice * strategiesArray[i][historyState] < 0) {
//		// 实际分数
//		//virtualWealth[i] = virtualWealth[i] + Math.abs(thisTurnPrice);
//		//virtualPosition[AgentNum][i] = 
//		System.err.println("1 work is ");
//	    } else if (thisTurnPrice == 0 && strategiesArray[i][historyState] == 0) {
//		System.err.println("2 work");
//		//virtualWealth[i] = virtualWealth[i] + Math.abs(thisTurnPrice);
//	    } else if (thisTurnPrice * strategiesArray[i][historyState] >= 0) {
//		System.err.println("3 work");
//		//virtualWealth[i] = virtualWealth[i] - Math.abs(thisTurnPrice);
//	    } else {
//		System.err.println("File:mgagent.java,L160 thisTurnPrice is:"
//			+ thisTurnPrice);
//	    }
//	}
	// 根据历史虚分值选择策略
	for (int i = 0; i < strategySize; ++i) {
	    if ((virtualPosition[agentNum][i] - virtualPosition[agentNum][determining])*realTransPrice>0) {
		determining = i;
	    }
	    // System.out.println("agent["+num+"]v[" + i + "]=" +
	    // virtualScores[i]);
	    System.out.println("agent["+agentNum+"]v[" + i + "]=" +
		    virtualWealth[i]+"choose is :"+determining);
	}
	return true;
    }

    public double getScore() {
	return mgAgentScore;
    }

    public int getAction() {
	return this.action;
    }
}
