package cs583;

import java.util.HashSet;

public class FrequentSequence {
	public HashSet<DataSequence> sequence= new HashSet<DataSequence>();
	
	public FrequentSequence () {
		// TODO Auto-generated constructor stub
		//default constructor to initialize to an empty frequentsequence
		sequence= new HashSet<DataSequence>();
	}
	
	public FrequentSequence (HashSet<DataSequence> paramsequence) {
		sequence.addAll(paramsequence);
	}
	
	public void addIntermediateSequence (DataSequence seq) {  //this function is used to append a sequence to the existing result sequence when forming candidates
		sequence.add(seq);
	}
	
	public void addFrequentSequence(FrequentSequence fs) {  //this sequence adds all the sequences of a frequent sequence,formed in previous step
		sequence.addAll(fs.sequence);
	}
	
	public void printFrequentSequence(int k, char type) { // here K denotes level; char type denotes "F" or "C"
		System.out.println("Printing "+type+"-"+k);
		for(DataSequence seq : sequence) {
			seq.printSequence();
		}
	}
}
