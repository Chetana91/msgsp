package projectCS583;
import java.util.*;

/* This is the class used to abstract Intermediate Data Sequences in a  transaction.
 * Every DataSequence will contains a list of itemsets that form a transaction.
 */
public class DataSequence {
	public ArrayList<ItemList> sequence; 
	public int count;  // count the occurrence of the Data sequence in the IntermidiateTransaction
	DataSequence()
	{	sequence= new ArrayList<ItemList>();
		count=0;
	}
	/*
	 * This function returns all the items contained in this IntermidiateTransaction
	 */
	public ArrayList<Integer> getAllItems()
	{	ArrayList<Integer> allItems=  new ArrayList<Integer>();
		for(ItemList i: sequence)
			allItems.addAll(i.items);
		return allItems;
	}
	
	public HashSet<Integer> getUniqueItems() {
		HashSet<Integer> result = new HashSet<Integer>();
		for (ItemList i : sequence) {
			result.addAll(i.items);
		}
		return result;
	}
	/*
	 * This function returns the id of the first item in the IntermidiateTransaction
	 * getFirstItemInSequence
	 */
	public Integer getFirstItemInSequence() {
		return sequence.get(0).items.get(0);
	}
	/*
	 * This function returns the id of the last item in the IntermidiateTransaction
	 * getLastItemInSequence
	 */
	public Integer getLastItemInSequence() {
		ArrayList<Integer> items = sequence.get(sequence.size()-1).items;
		return items.get(items.size()-1);
	}
	/*
	 * test whether the item with id of itemId has the lowest MIS in current
	 * IntermidiateTransaction.
	 * When flag=0, itemId is the first item,
	 * flag =1, itemId is the last item.
	 */
	public boolean isSmallest(Integer itemId, int flag) {
		boolean result = true;
		ArrayList<Integer> items = this.getAllItems();
		if (flag == 0)
			items.remove(0);
		else if (flag == 1)
			items.remove(items.size()-1);
		for (Integer id : items) {
			if (msgsp.MS.get(id).doubleValue() <= msgsp.MS.get(itemId).doubleValue()) {
				result = false;
				break;
			}
		}
		return result;
	}
	/*
	 * This method takes out the index1th item of current IntermidiateTransaction object
	 * and the index2th item of transaction, and compares the generated sub-sequences 
	 * to test whether they are the same.
	 */
	public boolean specialEqualTo(DataSequence tran, int index1, int index2) {
		//TODO Analyse
		boolean result = false;
		int s1 = this.sequence.size();
		int s2 = tran.sequence.size();
		DataSequence  current = new DataSequence();
		DataSequence   compare = new DataSequence();
		current = this.copy();
		compare = tran.copy();
		int i=0, j=0, index;
		for (index=0; index<s1; index++) {
			ItemList is = current.sequence.get(index);
			j = i;
			i += is.items.size();
			if (i > index1)
				break;
		}
		current.sequence.get(index).items.remove(index1 - j);
		if (current.sequence.get(index).items.size() == 0)
			current.sequence.remove(index);
		i = 0;
		j = 0;
		for (index=0; index<s2; index++) {
			ItemList is = compare.sequence.get(index);
			j = i;
			i += is.items.size();
			if (i > index2)
				break;
		}
		compare.sequence.get(index).items.remove(index2 - j);
		if (compare.sequence.get(index).items.size() == 0)
			compare.sequence.remove(index);
		int s12 = current.sequence.size();
		int s22 = compare.sequence.size();
		int l12 = current.getAllItems().size();
		int l22 = compare.getAllItems().size();
		if (current.presentIn(compare) && s12 == s22 && l12 == l22)
			result = true;
		return result;
	}
	/*
	 * This method is used to copy an Intermidiate Data Sequence
	 * TODO -analyse
	 */
	public DataSequence copy() {
		DataSequence tran = new DataSequence();
		for (int i=0; i<this.sequence.size(); i++) {
			ItemList is = new ItemList();
			is.items.addAll(this.sequence.get(i).items);
			tran.sequence.add(is);
		}
		return tran;
	}

	/*
	 * This method is used to check if this IntermidiateTransaction is contained in the IntermidiateTransaction indicated by an para.
	 */
	public boolean presentIn(DataSequence tran){
		//TODO loops,condition ,variables refactor..change
		boolean result = true;
		int m = this.sequence.size();
		int n = tran.sequence.size();
		int i=0, j=0;
		for(i=0; i<m; i++) {
			ItemList is = this.sequence.get(i);
			do {
				if (m-i > n-j) {	//The number of rest elements in current sequence
									//is greater than the number of rest elements in tran
					result = false;
					break;
				}
				if (is.isSubsetOf(tran.sequence.get(j))) {
					///count++;
					//find an element in tran which is the super set of current element in current sequence
					j++;
					break;
				}
				j++;
			} while(j<n);
			if (result == false) {
				break;
			}
			if (i==m-1 && j==n) {
				result = is.isSubsetOf(tran.sequence.get(j-1));
			}
		}
		return result;
	}
	/*
	 * This method is used to return the item that has the lowest MIS value in the DataSequence
	 */
	public int minMISItem(){
		ArrayList<Integer> items = this.getAllItems();
	    int item=0;
	    for(int i=1;i<items.size();i++){
	    	if(msgsp.MS.get(items.get(i))<msgsp.MS.get(items.get(item)))
	    		item=i;
	    }
	    return item;
	}
	/*
	 *  This method is used to create and return a Data Sequence  which is an 
	 *  reverse of this Data Sequence (also have to reverse the element/item set) 
	 * 
	 */
	public DataSequence reverseSequence(){
		DataSequence rev=new DataSequence();
		ItemList revIS=new ItemList();
		int i,j;
		for(i=this.sequence.size()-1;i>=0;i--){
			ItemList is=this.sequence.get(i);
			for(j=is.items.size()-1;j>=0;j--)
				revIS.items.add(new Integer(is.items.get(j).intValue()));
			rev.sequence.add(revIS);
			revIS = new ItemList();
		}
		return rev;
	}
	/*
	 * This method is used to print a Data Sequence in a line
		 */
	
	public void displaySequence(){
		int i;
		System.out.print("Pattern: <"); 
		for(ItemList is: sequence){
			System.out.print("{");
			for(i=0;i<is.items.size()-1;i++){  // print an element except the last item
				System.out.print(is.items.get(i)+",");
			}
			System.out.print(is.items.get(i)+"}");
			
			//print the last item in an element
		}
		System.out.println(">  ");
	}
	
	public boolean equals(Object tr) {
		//TODO analyse conditional construct and change
		DataSequence trans = (DataSequence) tr;
		boolean result = false;
		if (this.presentIn(trans) && this.sequence.size() == trans.sequence.size() && this.getAllItems().size() == trans.getAllItems().size())
			result = true;
		return result;
	}
	
	public int hashCode() {
		//TODO analyse conditional construct and change
		//TODO try Iterator instead of FOR construct
		int result = 0;
		for (Integer it : this.getAllItems())
			result += it.intValue();
		return result;
	}
}
