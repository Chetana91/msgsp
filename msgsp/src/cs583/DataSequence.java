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

}
