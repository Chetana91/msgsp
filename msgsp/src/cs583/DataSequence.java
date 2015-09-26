package cs583;

import java.util.ArrayList;
import java.util.HashSet;

public class DataSequence {

	public ArrayList<ItemSet> sequence;
	public int count; // count the occurrence of the Data sequence in the database

	DataSequence() {
		sequence = new ArrayList<ItemSet>();
		count = 0;
	}
	
	DataSequence(int item) {
		ItemSet itemSet = new ItemSet();
		itemSet.items.add(item);
		sequence = new ArrayList<ItemSet>();
		sequence.add(itemSet);
	}
	
	DataSequence(ArrayList<ItemSet> sequence) {
		this.sequence.addAll(sequence);
	}

	public void printSequence() {
		System.out.print("<");
		for (ItemSet itemSet : sequence) {
			itemSet.printItemSet();
		}
		System.out.print(">");
	}

	public HashSet<Integer> getUniqueItems() {
		HashSet<Integer> uniqueItems = new HashSet<Integer>();
		for (ItemSet itemSet : sequence) {
			uniqueItems.addAll(itemSet.items);
		}
		return uniqueItems;
	}
	
	public int getFirstItem(int i) {
		return sequence.get(i).items.get(0);
	}
	
	public int getLastItem(int i) {
		int size = sequence.get(i).items.size();
		return sequence.get(i).items.get(size-1);
	}
	
	public boolean isItemMisSmallest(int i, int smallestItem) {
		ArrayList<Integer> items = sequence.get(i).items;
		double smallestMIS = MsGsp.MISMap.get(smallestItem);
		for (int item: items) {
			// if for any item, smallestMIS is greater than that item, break and return false.
			if (smallestMIS > MsGsp.MISMap.get(item)) {
				return false;
			}
		}
		return true;
	}
	
	public DataSequence dropItemPositionNewSeq (int position) {
		DataSequence dataSequence = new DataSequence();
		//dataSequence.
		return dataSequence;
	}

}
