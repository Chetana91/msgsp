package cs583;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class MsGsp {

	// SDC - Support Difference Count
	public static double SDC = -1;

	// Total Data Sequences
	public static int N;

	// MIS values for each item
	public HashMap<Integer, Double> MISMap = null;

	// items sorted as per MIS values
	public LinkedList<Integer> M;
	
	// support count for each item
	public HashMap<Integer, Integer> ItemCountMap;

	// list of all data sequences
	public ArrayList<DataSequence> S = null;

	public static void main(String[] args) {

		MsGsp msGsp = new MsGsp();
		FileHandler fileHandler = new FileHandler();
		msGsp.MISMap = fileHandler.readMIS("para.txt");
		System.out.println("Control returns back");
		msGsp.printMIS();
		if (SDC == -1) {
			System.out.println("Some error in parsing SDC in para.txt");
			return;
		}
		else
			System.out.println("SDC: " + SDC);
		msGsp.S = fileHandler.readData("data.txt");
		msGsp.printS();
		N = msGsp.S.size();	//no of transactions
		msGsp.returnSortedM();
		msGsp.printM();
		
		System.out.println("\nEnd");
	}

	public void printMIS() {
		if (MISMap == null) {
			System.out.println("Some error in parsing MIS values in para.txt");
			return;
		}
		Iterator<Entry<Integer, Double>> mapIterator = MISMap.entrySet()
				.iterator();
		while (mapIterator.hasNext()) {
			Map.Entry<Integer, Double> pair = (Map.Entry<Integer, Double>) mapIterator
					.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
		}
	}

	public void printS() {
		if (S == null) {
			System.out.println("Some error in parsing data sequences values in data.txt");
			return;
		}
		for (DataSequence dataSequence : S) {
			dataSequence.printSequence();
			System.out.println("\n");
		}
	}
	
	public void printM() {
		for(Integer item : M) {
			System.out.print(item + ", ");
		}
	}

	public void returnSortedM() {
		//assuming list is already sorted!
		M = new LinkedList<Integer>();
		for (Integer item : MISMap.keySet()) {
			M.add(item);
		}
	}
	
	public ArrayList<Integer> initialPass () {
		ArrayList<Integer> L = new ArrayList<Integer>(); 
		ItemCountMap = new HashMap<Integer, Integer>();
		
		// adding all items; initializing with count = 0
		for (Integer item : MISMap.keySet()) {
			ItemCountMap.put(item, 0);
		}
		
		//for()
		
		return L;
	}

}



/*
 for (Integer item : MISMap.keySet()) {
			if (M.size() == 0) {
				M.add(item);
				printM();
			}
			else {
				int position = 0;
				for (int counter=0; counter < M.size(); counter++) {
					System.out.println(MISMap.get(M.get(counter)));
					System.out.println(MISMap.get(item));
					while (MISMap.get(item) > MISMap.get(M.get(counter))) {
						position++;
					}
					if (MISMap.get(item) == MISMap.get(M.get(counter))) {
						if(item>M.get(counter)) { //next sorting based on lexicographic order
							position++;
						}
					}
				}
				System.out.println("position of "+item +" is "+ position);
				M.add(position, item);
				printM();
			}
		}
 * */

/*
 else {
				int position = 0;
				for (Integer m : M) {
					if (MISMap.get(item) > MISMap.get(m)) {
						position++;
					}
					else if (MISMap.get(item) == MISMap.get(m)) {
						if(item>m) { //next sorting based on lexicographic order
							position++;
						}
					}
				}
				System.out.println("position of "+item +" is "+ position);
				M.add(position, item);
				printM();
			}
 * */