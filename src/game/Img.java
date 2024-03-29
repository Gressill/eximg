package game;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.FileWriter;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.XMLWriter;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Parser;

import server.DatabaseOperation;
import server.SocketServer;
import util.Constant;
import game.Game;
import agents.Agent;
import agents.MGAgent;
import agents.MGHuman;

public class Img {

    private Game game;

    private Agent[] agents;

    private MGHuman mghuman;

    //private int[] initCurrentChoise;

    private double currentPrice;

    private static ArrayList<Double> hisPriceList = new ArrayList<Double>();

    private double[] agentScoreInfo;

    private GameList gameList = GameList.getInstance();

    public Img() {
	// TODO Auto-generated constructor stub
    }

    public Img(int m, int s, int n) {
	this.mghuman = new MGHuman(m, s, n);
	gameList.addHumenAgent(this.mghuman);
    }

    public double getCurrentPrice() {
	return this.currentPrice;
    }

    public List getPrice() {
	return hisPriceList;
    }

    public void init() {

    }

    public void init(int m, int s, int n) {
	// check game list
	this.mghuman = new MGHuman(m, s, n);
	gameList.addHumenAgent(this.mghuman);
	// change the agentNumber to configured value
	agents = new Agent[n + 1]; // number of
	agents[n] = this.mghuman;
	Constant.MEMORYSIZE = m;
	// computer-controlled
	// agents PLUS human

	// 初始化agent
	for (int i = 0; i < (agents.length - 1); i++) {
	    agents[i] = new MGAgent(m, s,n);
	}

	// game plays once to initialise
	// game = new Game(agents, 0, 200 * (1 << 3));
	game = new Game(agents);
    }

    public void playGame() {
	// load price xml documents
	// hisPriceList = loadHistory(hisPriceList);
	game.playGame();
	this.currentPrice = game.getCurrentPrice();
	this.agentScoreInfo = game.getAgentScore();
	// price.set(price.size()+1, price.get(price.size())+currentPrice);
    }

    /**
     * load history from database
     * 
     * @param indexNum
     *            : number of price,default is 60
     * @return history price list
     */
    public ArrayList<Double> getHistoryPrice(int indexNum) {
	String sqlString = "select price from price_info order by price_id desc limit "
		+ indexNum;
	ResultSet res;
	DatabaseOperation databaseOperation = new DatabaseOperation();
	if (databaseOperation.OpenConnection()) {
	    hisPriceList.clear();
	    res = databaseOperation.ExecuteQuery(sqlString);
	    // 处理结果集
	    try {
		while (res.next()) {
		    double tempPrice = res.getDouble("price");
		    hisPriceList.add(tempPrice);
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    } finally {
		databaseOperation.CloseConnection();
	    }
	}
	return hisPriceList;
    }

    /**
     * loading history price,while price is empty,load history
     * 
     * @param price
     *            : price list
     * @return priceList: price list
     */
    private ArrayList<Double> loadHistoryXml(ArrayList<Double> price) {

	// check file exist
	// File historyFile = new File(Constant.HISTORY_PRICE_FILE);
	// if (historyFile.canRead())
	ArrayList<Double> priceList = price;

	if (priceList.isEmpty()) {
	    long lasting = System.currentTimeMillis();
	    try {
		File historyFile = new File(Constant.HISTORY_PRICE_FILE);
		SAXReader reader = new SAXReader();
		Document doc = reader.read(historyFile);
		Element root = doc.getRootElement();
		// get price
		// List nodes = root.elements("price");
		// System.out.println("size = "+nodes.size());
		if (priceList.size() > 50) {
		    priceList = (ArrayList<Double>) root.elements("price")
			    .subList((priceList.size() - 50), priceList.size());
		}

		for (Iterator it = priceList.iterator(); it.hasNext();) {
		    Element priceElm = (Element) it.next();
		    System.out.println("priceElm.getData()"
			    + priceElm.getData());
		}

	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    // System.out.println("运行时间：" + (System.currentTimeMillis() -
	    // lasting) + " 毫秒");
	}
	return priceList;
    }

    public void writeAgentFile(int best, double avg, int worse) {
	/**
	 * 产生 一个document对象AGENT_INFO_FILE
	 */
	try {
	    File historyFile = new File(Constant.AGENT_INFO_FILE);
	    SAXReader reader = new SAXReader();
	    Document doc = reader.read(historyFile);
	    Element root = doc.getRootElement();

	    root.element("best").setText(String.valueOf(best));
	    root.element("avg").setText(String.valueOf(avg));
	    root.element("worse").setText(String.valueOf(worse));
	    // System.out.println(asaString);
	} catch (Exception e) {
	    // TODO: handle exception
	}
    }

    public void writePriceFile(int thisTurnPrice) {
	/**
	 * 产生 一个document对象AGENT_PRICE_FILE
	 */
	try {
	    File historyFile = new File(Constant.HISTORY_PRICE_FILE);
	    SAXReader reader = new SAXReader();
	    Document doc = reader.read(historyFile);
	    Element root = doc.getRootElement();
	    root.addElement("price").setText(String.valueOf(thisTurnPrice));
	    XMLWriter writer = new XMLWriter(new FileWriter(new File(
		    Constant.HISTORY_PRICE_FILE)));
	    writer.write(doc);
	    writer.close();
	} catch (Exception e) {
	    // TODO: handle exception
	}
    }

    private double caculateVolatility(List price) {
	double lastDTurnA = 0;
	double sqrtLastDTurnA = 0;
	double volatility = 0;
	List tempPriceList = null;
	if (price.size() >= Constant.dVolatility) {
	    tempPriceList = price.subList(price.size() - 5, price.size());
	}
	if (price.size() == Constant.dVolatility) {
	    for (int j = 0; j < tempPriceList.size(); j++) {
		lastDTurnA = lastDTurnA
			+ Integer.parseInt(tempPriceList.get(j).toString());
		sqrtLastDTurnA = sqrtLastDTurnA
			+ Double.parseDouble(tempPriceList.get(j).toString())
			* Double.parseDouble(tempPriceList.get(j).toString());
	    }
	}
	// caculateVolatility
	volatility = (sqrtLastDTurnA / Constant.dVolatility)
		- (lastDTurnA / Constant.dVolatility)
		* (lastDTurnA / Constant.dVolatility);
	return volatility;
    }

    public double[] getAgentScoreInfo() {
	return agentScoreInfo;
    }

    public MGHuman getHumanAgent() {
	return this.mghuman;
    }

    public Game getGame() {
	return this.game;
    }

}
