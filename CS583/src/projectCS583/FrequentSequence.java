package projectCS583;
import java.util.HashSet;



/*
 * This is the class used to implement L (initial pass set), F(k), and C(k) from GSP algorithm
*/
public class FrequentSequence {
	public HashSet<DataSequence> sequences = new HashSet<DataSequence>();
	FrequentSequence(){
		sequences=new HashSet<DataSequence>();
	}
	
	FrequentSequence(HashSet<DataSequence> sequence) {
		sequences.addAll(sequence);
	}

	public void addSequence(DataSequence transaction) {
		sequences.add(transaction);
	}
	
	public void addFrequentSequence(FrequentSequence fs) {
		sequences.addAll(fs.sequences);
	}

}
