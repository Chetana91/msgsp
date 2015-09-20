package projectCS583;
import java.util.HashSet;



/*
 * This is the class used to implement L (initial pass set), F(k), and C(k) from GSP algorithm
*/
public class FrequentSequencePattern {
	public HashSet<DataSequence> sequences = new HashSet<DataSequence>();
	FrequentSequencePattern(){
		sequences=new HashSet<DataSequence>();
	}
	
	FrequentSequencePattern(HashSet<DataSequence> sequence) {
		sequences.addAll(sequence);
	}
	
	public void addIntermidiateTransaction(DataSequence transaction) {
		sequences.add(transaction);
	}
	
	public void addFrequentSequencePattern(FrequentSequencePattern fsp) {
		sequences.addAll(fsp.sequences);
	}

}
