package cs583;

import java.util.ArrayList;

public class CandidateGeneration {

	/**
	 * @param L
	 * @return
	 */
	public FrequentSequence frequent1ItemsetGen (ArrayList<Integer> L) {
		FrequentSequence f1 = new FrequentSequence();
		System.out.println("Finding F1");
		for (Integer item : L) {
			if ((MsGsp.ItemCountMap.get(item) * 1.0 / MsGsp.N) >= MsGsp.MISMap.get(item)) {
				System.out.println(item);
				DataSequence dataSequence = new DataSequence(item);
				f1.addIntermediateSequence(dataSequence);
			}
		}
		System.out.println("f1 size: "+f1.sequence.size());
		f1.printFrequentSequence('F', 1);
		return f1;
	}

	public FrequentSequence level2CandidateGen(ArrayList<Integer> L) {
		// function takes argument L and returns a superset of the set of all frequent 2 itemsets
		FrequentSequence C2 = new FrequentSequence();
		for (Integer i : L) {

		}

		return C2;
	}

}
