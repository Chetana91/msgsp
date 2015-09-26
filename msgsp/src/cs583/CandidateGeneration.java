package cs583;

import java.util.ArrayList;
import java.math.*;


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
	
	public FrequentSequence level2CandidateGen(ArrayList<Integer> L)
	{ //function takes argument L and returns a superset of the set of all frequent 2 itemsets
	   FrequentSequence C2= new FrequentSequence();
	   for(Integer i: L)
	   {  if(MsGsp.ItemCountMap.get(L.get(i))*1.0/ MsGsp.N >= MsGsp.MISMap.get(L.get(i)))
	   			{
		   			for(int h=i; h < L.size();h++){
		   				if(MsGsp.ItemCountMap.get(L.get(h))*1.0 / MsGsp.N >= MsGsp.MISMap.get(L.get(i)))
		   				{
		   					if (Math.abs(MsGsp.ItemCountMap.get(L.get(i)).intValue() - MsGsp.ItemCountMap.get(L.get(h)).intValue()) <= MsGsp.SDC * MsGsp.N)
		   					{
		   						ItemSet itemset = new ItemSet();
		   						int a= L.get(i);
		   						int b=L.get(h);
		   						
		   						//adding sequence<{a},{b}>
		   						ItemSet firstset= new ItemSet();
		   						firstset.items.add(a);
		   						ItemSet secondset= new ItemSet();
		   						secondset.items.add(b);
		   						DataSequence tempsequence1= new DataSequence();
		   						tempsequence1.sequence.add(firstset);
		   						tempsequence1.sequence.add(secondset);
		   						C2.addIntermediateSequence(tempsequence1); //tempsequence1 is <{a},{b}>
		   						
		   						
		   						
		   						if(a<=b)
		   						{
		   							itemset.items.add(a);
		   							itemset.items.add(b);
		   						}
		   						else
		   						{
		   							itemset.items.add(b);
		   							itemset.items.add(a);
		   						}
		   						if(a!=b)
		   						{
		   							DataSequence tempsequence2= new DataSequence();
		   							tempsequence2.sequence.add(itemset); //adds tempsequence2 <{a,b}> 
		   							C2.addIntermediateSequence(tempsequence2);
		   						}
		   						
		   						
		   						
		   						
		   					}
		   				}
		   			}
		   				   			
		   	}
		   			
	   }
		return C2;
	}
	
	public FrequentSequence MSCandidateGen_SPM (FrequentSequence fk_1) {
		return null;
	}

}
