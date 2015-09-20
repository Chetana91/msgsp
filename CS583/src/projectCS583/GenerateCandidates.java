package projectCS583;
import java.util.ArrayList;




public class GenerateCandidates {

	public FrequentSequencePattern candidateGen(FrequentSequencePattern F) {
		FrequentSequencePattern C = new FrequentSequencePattern();
		for (DataSequence tr1 : F.sequences){
			for (DataSequence tr2 : F.sequences) {
				DataSequence s1 = tr1.copy();
				DataSequence s2 = tr2.copy();
				int condition = checkCondition(s1, s2);
				if (condition != 0) {
					C.addFrequentSequencePattern(joinDataSequence(s1, s2, condition));
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
	private boolean isFrequent(DataSequence t, FrequentSequencePattern fk_1){
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
	 * To-do joinSequences~joinDataSequences 
	 */
	private FrequentSequencePattern joinDataSequence(DataSequence tran, DataSequence tr, int i) {
		FrequentSequencePattern fs = new FrequentSequencePattern();
		DataSequence candidate = new DataSequence();
		switch(i) {
		case 1:
			DataSequence trans = tran.copy();
				if (tr.transactions.get(tr.transactions.size()-1).list.size() == 1) {
					candidate = new DataSequence();
					candidate.transactions.addAll(trans.transactions);
					candidate.transactions.add(tr.transactions.get(tr.transactions.size()-1));
					fs.addIntermidiateTransaction(candidate);
					if (trans.transactions.size()==2 && trans.getAllItems().size()==2 && trans.getLastItemInSequence().toString().compareTo(tr.getLastItemInSequence().toString()) < 0) {
						candidate = new DataSequence();
						candidate.transactions.addAll(trans.copy().transactions);
						candidate.transactions.get(candidate.transactions.size()-1).list.add(tr.copy().getLastItemInSequence());
						fs.addIntermidiateTransaction(candidate);
					}
				}
				else if (trans.getAllItems().size() > 2 ||(trans.transactions.size()==1 && trans.getAllItems().size()==2 && trans.getLastItemInSequence().toString().compareTo(tr.getLastItemInSequence().toString()) < 0)) {
					candidate = new DataSequence();
					candidate.transactions.addAll(trans.transactions);
					candidate.transactions.get(candidate.transactions.size()-1).list.add(tr.getLastItemInSequence());
					fs.addIntermidiateTransaction(candidate);
				}
			
			break;
		case 2:
				trans = tran.copy();
				if (tr.reverse().transactions.get(tr.reverse().transactions.size()-1).list.size() == 1) {
					candidate = new DataSequence();
					candidate.transactions.addAll(trans.reverse().transactions);
					candidate.transactions.add(tr.reverse().transactions.get(tr.reverse().transactions.size()-1));
					fs.addIntermidiateTransaction(candidate.reverse());
					if (trans.reverse().transactions.size()==2 && trans.reverse().getAllItems().size()==2 && trans.reverse().getLastItemInSequence().toString().compareTo(tr.reverse().getLastItemInSequence().toString()) < 0) {
						candidate = new DataSequence();
						candidate.transactions.addAll(trans.copy().reverse().transactions);
						candidate.transactions.get(candidate.transactions.size()-1).list.add(tr.copy().reverse().getLastItemInSequence());
						fs.addIntermidiateTransaction(candidate.reverse());
					}
				}
				else if (trans.reverse().getAllItems().size() > 2 ||(trans.reverse().transactions.size()==1 && trans.reverse().getAllItems().size()==2 && trans.reverse().getLastItemInSequence().toString().compareTo(tr.reverse().getLastItemInSequence().toString()) < 0)) {
					candidate = new DataSequence();
					candidate.transactions.addAll(trans.reverse().transactions);
					candidate.transactions.get(candidate.transactions.size()-1).list.add(tr.reverse().getLastItemInSequence());
					fs.addIntermidiateTransaction(candidate.reverse());
				}
			break;
		case 3:
				trans = tran.copy();
				if (tr.transactions.get(tr.transactions.size()-1).list.size() == 1) {
					candidate = new DataSequence();
					candidate.transactions.addAll(trans.transactions);
					candidate.transactions.add(tr.transactions.get(tr.transactions.size()-1));
					fs.addIntermidiateTransaction(candidate);
				}
				else {
					candidate = new DataSequence();
					candidate.transactions.addAll(trans.transactions);
					candidate.transactions.get(candidate.transactions.size()-1).list.add(tr.getLastItemInSequence());
					fs.addIntermidiateTransaction(candidate);
				}
			
			break;
		}
		return fs;
	}
	/*
	 * The prune step in MScandidate-gen-SPM function
	 * prune a Candidate Sequence if any of its (k-1) subsequences are infrequent(without minimum support)
	 */
	private FrequentSequencePattern pruneSequence(FrequentSequencePattern fs, FrequentSequencePattern fk_1) {
		Integer minItem; // Item that has the minimum MIS in a sequence
		FrequentSequencePattern fsPruned=new FrequentSequencePattern(); // Frequent sequence set after prune step
		for(DataSequence t: fs.sequences){
			minItem=new Integer(t.minMISItem());
			boolean frequent=true; // indicator of if t is frequent or not
			for(int i=0;i<t.transactions.size();i++){ // walk through the itemsets of a sequence
				if(t.transactions.get(i).list.contains(minItem)){ // if the itemset contains the item with min MIS
					DataSequence copy=t.copy();
					for(int k=0;k<t.transactions.size();k++){
						if(!frequent) 
							break;
						for(Integer item: t.transactions.get(k).list){
							if(!(k==i&&item==minItem)){ //except the minItem
								copy.transactions.get(k).list.remove(item); // generate a k-1 subsequence 
								if(!isFrequent(copy, fk_1)){//if this subsequence is not frequent, then the sequence is not frequent either.
									frequent=false;
									break;
								}
							}
						}
					}
					if(!frequent)// if a sequence is already infrequent the no need to continue check the remaining itemsets
						break;
				}
			}
			if(frequent) //if this sequence is frequent, add to the result
				fsPruned.sequences.add(t);
		}
		return fsPruned;
	}
}
