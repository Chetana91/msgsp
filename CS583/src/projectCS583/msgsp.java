package projectCS583;

import java.util.ArrayList;
import java.util.HashMap;
public class msgsp {
	//this is main file
	
	public static HashMap<Integer,Float> MS;  
	public ArrayList<DataSequence> S; 
	/*
	 * @Func: Constructor for MSGSP class
	 * @Param: data file name and MIS file name
	 */
	msgsp(String paraFileName, String dataFileName){
		MS=InputReader.readMISvalues(paraFileName);
		S=InputReader.readDataItems(dataFileName);
	}
	public static int N;
	/*
	 * SDC is support distance constraint
	 */
	public static double SDC;
	/*
	 * SUP stores the support count for each item using a HashMap
	 */
	public static HashMap<Integer, Integer> SUP = new HashMap<Integer, Integer>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
