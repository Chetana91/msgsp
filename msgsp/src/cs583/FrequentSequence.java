package cs583;

import java.util.LinkedHashSet;

public class FrequentSequence {
	
	public LinkedHashSet<DataSequence> sequence= new LinkedHashSet<DataSequence>();
	
	public FrequentSequence () {
		// TODO Auto-generated constructor stub
		//default constructor to initialize to an empty frequentsequence
		sequence= new LinkedHashSet<DataSequence>();
	}
	
	public FrequentSequence (LinkedHashSet<DataSequence> paramsequence) {
		sequence.addAll(paramsequence);
	}
	
	public void addIntermediateSequence (DataSequence seq) {  //this function is used to append a sequence to the existing result sequence when forming candidates
		sequence.add(seq);
	}
	
	public void addFrequentSequence(FrequentSequence fs) {  //this sequence adds all the sequences of a frequent sequence,formed in previous step
		sequence.addAll(fs.sequence);
	}
	
	public void printFrequentSequence(char type, int k) { // here type denotes "F" or "C"; K denotes level
		System.out.println("Printing "+type+"-"+k);
		for(DataSequence seq : sequence) {
			seq.printSequence();
		}
	}
	
	public boolean isEmpty() {
		if (sequence.size() == 0 || sequence == null) {
			return true;
		}
		
		else
			return false;
	}
}
