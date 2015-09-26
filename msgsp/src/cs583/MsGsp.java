package cs583;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
	public static HashMap<Integer, Double> MISMap = null;

	// items sorted as per MIS values
	public LinkedList<Integer> M;
	
	// support count for each item
	public static HashMap<Integer, Integer> ItemCountMap;

	// list of all data sequences
	public ArrayList<DataSequence> S = null;

	public static void main(String[] args) {

		MsGsp msGsp = new MsGsp();
		FileHandler fileHandler = new FileHandler();
		MISMap = fileHandler.readMIS("para.txt");
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
		System.out.println("size:: "+msGsp.S.size());
		N = msGsp.S.size();	//no of transactions
		
		// MS - GSP
		// 1. according to MIS(i)’s stored in MS
		msGsp.returnSortedM();
		msGsp.printCollection(msGsp.M);
		
		// 2. make the first pass over S
		
		ArrayList<Integer> L = msGsp.initialPass();
		msGsp.printCollection(L);
		
		CandidateGeneration candidateGeneration = new CandidateGeneration();
		
		FrequentSequence fk_1;							// (k-1)th frequent sequence
		FrequentSequence fk = new FrequentSequence();	// kth frequent sequence
		FrequentSequence ck = new FrequentSequence();	// kth candidate sequence
		
		
		// 3. Generate F1
		fk_1 = candidateGeneration.frequent1ItemsetGen(L);
		fk_1.printFrequentSequence('F', 1);
		
		int k=2;
		
		for (; !fk_1.isEmpty() ; k++) {
			if (k==2) {
				ck = null;// candidateGeneration.level2CandidateGen(L);
			}
			else {
				ck = candidateGeneration.MSCandidateGen_SPM(fk_1);
			}
			
			for (DataSequence sequence: msGsp.S) {
				
			}
			
			fk_1 = fk;	// copy fk to fk_1
			fk = ck;	// copy ck to fk		
		}
		
		System.out.println("\nFinal Frequent Sets: ");
		
		
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
	
	public void printCollection(Collection<Integer> collection) {
		System.out.println("Printing "+ collection.getClass().getName());
		for(Integer item : collection) {
			System.out.print(item + ", ");
		}
		System.out.println("");
	}

	public void returnSortedM() {
		//assuming list is already sorted!
		M = new LinkedList<Integer>();
		for (Integer item : MISMap.keySet()) {
			M.add(item);
		}
	}
	
	public ArrayList<Integer> initialPass () {
		
		// 1. get support count of each item
		
		ArrayList<Integer> L = new ArrayList<Integer>(); 
		ItemCountMap = new HashMap<Integer, Integer>();
		
		// adding all items; initializing with count = 0
		
		for (Integer item : MISMap.keySet()) {
			ItemCountMap.put(item, 0);
		}
		
		// finding item counts
		
		for (DataSequence sequence : S) {
			HashSet<Integer> uniqueItems = sequence.getUniqueItems();
			System.out.println("");
			sequence.printSequence();
			for(Integer item: uniqueItems) {
				int count = ItemCountMap.get(item);
				ItemCountMap.put(item, ++count);
			}
		}
		
		// printing ItemCountMap
		Iterator<Entry<Integer, Integer>> mapIterator = ItemCountMap.entrySet()
				.iterator();
		while (mapIterator.hasNext()) {
			Map.Entry<Integer, Integer> pair = (Map.Entry<Integer, Integer>) mapIterator
					.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
		}
		
		// 2. find first item i which meets ItemCount(i)/N >= MIS(i)  
		boolean flag = false;
		int itemI = -1;
		
		Iterator<Integer> itemSet = M.iterator();
		
		while (itemSet.hasNext()) {
			int item = itemSet.next();
			System.out.println( item+"||| "+ ItemCountMap.get(item)*1.0/N + " ||| "+ MISMap.get(item));
			
			if( (ItemCountMap.get(item)*1.0/N) >= MISMap.get(item)) {
				itemI = item;
				flag = true;
				break;
			}
		}

		System.out.println("item i: "+itemI );
		
		if (!flag) {
			System.out.println("No such item found where: ItemCount(i)/N >= MIS(i). Returning null.");
			return null;
		}
		
		L.add(itemI);
		double misI = MISMap.get(itemI);
		
		while (itemSet.hasNext()) { // iterating through subsequent items
			int item = itemSet.next();
			if ((ItemCountMap.get(item)*1.0/N) >= misI) {
				L.add(item);
			}
		}
		
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