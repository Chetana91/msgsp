package cs583;

import java.util.ArrayList;
import java.util.HashSet;

public class CandidateGeneration {
	
	
	/**
	 * @return
	 */
	public FrequentSequence frequent1ItemsetGen (ArrayList<Integer> L) {
		FrequentSequence f1 = new FrequentSequence();
		
		for (Integer item : L) {
			if ( (MsGsp.ItemCountMap.get(item)*1.0/MsGsp.N) >= MsGsp.MISMap.get(item)) {
				DataSequence dataSequence = new DataSequence(item);
				f1.addIntermediateSequence(dataSequence);
			}
		}
		return null;
	}
	
	public FrequentSequence level2CandidateGen(ArrayList<Integer> L)
	{ //function takes argument L and returns a superset of the set of all frequent 2 itemsets
	   FrequentSequence C2= new FrequentSequence();
	   for(Integer i: L)
	   { 
		   
	   }
		
		return C2;
	}

}
