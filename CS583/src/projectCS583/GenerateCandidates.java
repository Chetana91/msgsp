package projectCS583;
import java.util.ArrayList;
//TODO analysis for all functions
public class GenerateCandidates {

	public FrequentSequence candidateGen(FrequentSequence F) {
		FrequentSequence C = new FrequentSequence();
		for (DataSequence tr1 : F.sequences){
			for (DataSequence tr2 : F.sequences) {
				DataSequence s1 = tr1.copy();
				DataSequence s2 = tr2.copy();
				int condition = checkCondition(s1, s2);
				if (condition != 0) {
					C.addFrequentSequence(joinDataSequence(s1, s2, condition));
				}
			}
		}
		return pruneSequence(C,F); 
	}
	private int checkCondition(DataSequence s1, DataSequence s2) {
		int result = 0;
		int i = partition(s1);
		if(testPair(s1, s2, i))
			result = i;
		else if(testPair(s1, s2, 3))
			result = 3;
		return result;
	}
	/*
	 * This method partitions the k-1 length frequent sequence F into three
	 * subset s1, s2, and s3.
	 * s1 is those whose first item has the minimum MIS.
	 * s2 is those whose last item has the minimum MIS.
	 * s3 is the rest.
	 */
	private int partition(DataSequence tran) {
		int result = 0;
			Integer firstItem = tran.getFirstItemInSequence();
			Integer lastItem = tran.getLastItemInSequence();
			if (tran.isSmallest(firstItem, 0))
				result = 1;
			else if (tran.isSmallest(lastItem, 1))
				result = 2;
		return result;
	}
	/*
	 * This method is used to determine if a k-1 length subsequence is frequent
	 */
	private boolean isFrequent(DataSequence t, FrequentSequence fk_1){
		boolean	frequent=false; 
		for(DataSequence freq: fk_1.sequences)
			if(t.presentIn(freq)){
				frequent=true; //there is one sequence in F(k-1) includes the subsequence
				break;
			}
		return frequent;
	}
	/*
	 * This method finds the corresponding join sequences for s1, s2, and s3.
	 * Parameter tran is the IntermidiateTransaction in s1, s2, or s3, which will be found 
	 * a pair for.
	 * Parameter i indicates which of the three, s1, s2, and s3, does transaction belong to. 
	 */
	private boolean testPair(DataSequence tran, DataSequence tr, int i) {
		boolean result = false;
		switch (i) {
		case 1:
			if (tran.specialEqualTo(tr, 1, tr.getAllItems().size()-1) &&
					msgsp.MS.get(tran.getFirstItemInSequence()).doubleValue() < msgsp.MS.get(tr.getLastItemInSequence()).doubleValue() &&
					Math.abs(msgsp.SUP.get(tran.getAllItems().get(1)).intValue() - msgsp.SUP.get(tr.getLastItemInSequence()).intValue()) <= msgsp.SDC*msgsp.N)
				result = true;
			break;
		case 2:
			if (tran.specialEqualTo(tr, tran.getAllItems().size()-2, 0) &&
					msgsp.MS.get(tr.getFirstItemInSequence()).doubleValue() > msgsp.MS.get(tran.getLastItemInSequence()).doubleValue() &&
					Math.abs(msgsp.SUP.get(tran.getAllItems().get(tran.getAllItems().size()-2)).intValue() - msgsp.SUP.get(tr.getFirstItemInSequence()).intValue()) <= msgsp.SDC*msgsp.N)
				result = true;
			break;
		case 3:
			if (tran.specialEqualTo(tr, 0, tr.getAllItems().size()-1) &&
					Math.abs(msgsp.SUP.get(tran.getFirstItemInSequence()).intValue() - msgsp.SUP.get(tr.getLastItemInSequence()).intValue()) <= msgsp.SDC*msgsp.N)
				result = true;
			break;
		}
		return result;
	}
	/*
	 * This method joins s1, s2, or s3 with their corresponding pairs to 
	 * form the k-length frequent sequence candidates.
	 * Parameter i indicates for which of the three, s1, s2, and s3, are we
	 * going to join their pairs.
	 * Parameter fs is s1, s2, or s3.
	 * 
	 */
	private FrequentSequence joinDataSequence(DataSequence tran, DataSequence tr, int i) {
		FrequentSequence fs = new FrequentSequence();
		DataSequence candidate = new DataSequence();
		switch(i) {
		case 1:
			DataSequence trans = tran.copy();
				if (tr.sequence.get(tr.sequence.size()-1).items.size() == 1) {
					candidate = new DataSequence();
					candidate.sequence.addAll(trans.sequence);
					candidate.sequence.add(tr.sequence.get(tr.sequence.size()-1));
					fs.addSequence(candidate);
					if (trans.sequence.size()==2 && trans.getAllItems().size()==2 && trans.getLastItemInSequence().toString().compareTo(tr.getLastItemInSequence().toString()) < 0) {
						candidate = new DataSequence();
						candidate.sequence.addAll(trans.copy().sequence);
						candidate.sequence.get(candidate.sequence.size()-1).items.add(tr.copy().getLastItemInSequence());
						fs.addSequence(candidate);
					}
				}
				else if (trans.getAllItems().size() > 2 ||(trans.sequence.size()==1 && trans.getAllItems().size()==2 && trans.getLastItemInSequence().toString().compareTo(tr.getLastItemInSequence().toString()) < 0)) {
					candidate = new DataSequence();
					candidate.sequence.addAll(trans.sequence);
					candidate.sequence.get(candidate.sequence.size()-1).items.add(tr.getLastItemInSequence());
					fs.addSequence(candidate);
				}
			
			break;
		case 2:
				trans = tran.copy();
				if (tr.reverseSequence().sequence.get(tr.reverseSequence().sequence.size()-1).items.size() == 1) {
					candidate = new DataSequence();
					candidate.sequence.addAll(trans.reverseSequence().sequence);
					candidate.sequence.add(tr.reverseSequence().sequence.get(tr.reverseSequence().sequence.size()-1));
					fs.addSequence(candidate.reverseSequence());
					if (trans.reverseSequence().sequence.size()==2 && trans.reverseSequence().getAllItems().size()==2 && trans.reverseSequence().getLastItemInSequence().toString().compareTo(tr.reverseSequence().getLastItemInSequence().toString()) < 0) {
						candidate = new DataSequence();
						candidate.sequence.addAll(trans.copy().reverseSequence().sequence);
						candidate.sequence.get(candidate.sequence.size()-1).items.add(tr.copy().reverseSequence().getLastItemInSequence());
						fs.addSequence(candidate.reverseSequence());
					}
				}
				else if (trans.reverseSequence().getAllItems().size() > 2 ||(trans.reverseSequence().sequence.size()==1 && trans.reverseSequence().getAllItems().size()==2 && trans.reverseSequence().getLastItemInSequence().toString().compareTo(tr.reverseSequence().getLastItemInSequence().toString()) < 0)) {
					candidate = new DataSequence();
					candidate.sequence.addAll(trans.reverseSequence().sequence);
					candidate.sequence.get(candidate.sequence.size()-1).items.add(tr.reverseSequence().getLastItemInSequence());
					fs.addSequence(candidate.reverseSequence());
				}
			break;
		case 3:
				trans = tran.copy();
				if (tr.sequence.get(tr.sequence.size()-1).items.size() == 1) {
					candidate = new DataSequence();
					candidate.sequence.addAll(trans.sequence);
					candidate.sequence.add(tr.sequence.get(tr.sequence.size()-1));
					fs.addSequence(candidate);
				}
				else {
					candidate = new DataSequence();
					candidate.sequence.addAll(trans.sequence);
					candidate.sequence.get(candidate.sequence.size()-1).items.add(tr.getLastItemInSequence());
					fs.addSequence(candidate);
				}
			
			break;
		}
		return fs;
	}
	/*
	 * The prune step in MScandidate-gen-SPM function
	 * prune a Candidate Sequence if any of its (k-1) subsequences are infrequent(without minimum support)
	 */
	private FrequentSequence pruneSequence(FrequentSequence fs, FrequentSequence fk_1) {
		Integer minItem; // Item that has the minimum MIS in a sequence
		FrequentSequence fsPruned=new FrequentSequence(); // Frequent sequence set after prune step
		for(DataSequence t: fs.sequences){
			minItem=new Integer(t.minMISItem());
			boolean frequent=true; // indicator of if t is frequent or not
			for(int i=0;i<t.sequence.size();i++){ // walk through the s of a sequence
				if(t.sequence.get(i).items.contains(minItem)){ // if the  contains the item with min MIS
					DataSequence copy=t.copy();
					for(int k=0;k<t.sequence.size();k++){
						if(!frequent) 
							break;
						for(Integer item: t.sequence.get(k).items){
							if(!(k==i&&item==minItem)){ //except the minItem
								copy.sequence.get(k).items.remove(item); // generate a k-1 subsequence 
								if(!isFrequent(copy, fk_1)){//if this subsequence is not frequent, then the sequence is not frequent either.
									frequent=false;
									break;
								}
							}
						}
					}
					if(!frequent)// if a sequence is already infrequent the no need to continue check the remaining s
						break;
				}
			}
			if(frequent) //if this sequence is frequent, add to the result
				fsPruned.sequences.add(t);
		}
		return fsPruned;
	}
}
