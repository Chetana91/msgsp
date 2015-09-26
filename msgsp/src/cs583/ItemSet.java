package cs583;

import java.util.ArrayList;

/**
 * @author vedantam
 *
 */
public class ItemSet {
	
	public ArrayList<Integer> items;

	/**
	 * Default constructor
	 */
	public ItemSet() {
		items = new ArrayList<Integer>();
	}
	
	public ItemSet(int length) {
		items = new ArrayList<Integer>(length);
	}
	
	/**
	 * @param subItemSet
	 * @return
	 */
	public boolean isContained(ArrayList<Integer> subItemSet) {
		return items.containsAll(subItemSet);
	}
	
	public void printItemSet() {
		System.out.print("{ ");
		int counter = 0;
		while( counter< items.size()-1) {
			System.out.print(items.get(counter)+", ");
			counter++;
		}
		System.out.print(items.get(counter));
		System.out.print(" }");
	}

}
