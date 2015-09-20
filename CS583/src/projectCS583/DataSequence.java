package projectCS583;
import java.util.*;

/* This is the class used to abstract Intermediate Data Sequences in a  transaction.
 * Every DataSequence will contains a list of itemsets that form a transaction.
 */
public class DataSequence {
	public ArrayList<ItemSet> transactions;
	public int count;  // count the occurrence of the Data sequence in the IntermidiateTransaction
	DataSequence()
	{
		transactions= new ArrayList<ItemSet>();
		count=0;

	}
	/*
	 * This function returns all the items contained in this IntermidiateTransaction
		 */
	public ArrayList<Integer> getAllItems()
	{
		ArrayList<Integer> allItems=  new ArrayList<Integer>();
		
		for(ItemSet i: transactions)
			allItems.addAll(i.list);
		
		return allItems;
	}
	
	public HashSet<Integer> getUniqueItems() {
		HashSet<Integer> result = new HashSet<Integer>();
		for (ItemSet i : transactions) {
			result.addAll(i.list);
		}
		return result;
	}
	/*
	 * This function returns the id of the first item in the IntermidiateTransaction
	 * getFirstItemInSequence
	 */
	public Integer getFirstItemInSequence() {
		return transactions.get(0).list.get(0);
	}
	/*
	 * This function returns the id of the last item in the IntermidiateTransaction
	 * getLastItemInSequence
	 */
	public Integer getLastItemInSequence() {
		ArrayList<Integer> items = transactions.get(transactions.size()-1).list;
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
		boolean result = false;
		int s1 = this.transactions.size();
		int s2 = tran.transactions.size();
		DataSequence  current = new DataSequence();
		DataSequence   compare = new DataSequence();
		current = this.copy();
		compare = tran.copy();
		int i=0, j=0, index;
		for (index=0; index<s1; index++) {
			ItemSet is = current.transactions.get(index);
			j = i;
			i += is.list.size();
			if (i > index1)
				break;
		}
		current.transactions.get(index).list.remove(index1 - j);
		if (current.transactions.get(index).list.size() == 0)
			current.transactions.remove(index);
		i = 0;
		j = 0;
		for (index=0; index<s2; index++) {
			ItemSet is = compare.transactions.get(index);
			j = i;
			i += is.list.size();
			if (i > index2)
				break;
		}
		compare.transactions.get(index).list.remove(index2 - j);
		if (compare.transactions.get(index).list.size() == 0)
			compare.transactions.remove(index);
		int s12 = current.transactions.size();
		int s22 = compare.transactions.size();
		int l12 = current.getAllItems().size();
		int l22 = compare.getAllItems().size();
		if (current.presentIn(compare) && s12 == s22 && l12 == l22)
			result = true;
		return result;
	}
	/*
	 * This method is used to copy an Intermidiate Data Sequence
	 */
	public DataSequence copy() {
		DataSequence tran = new DataSequence();
		for (int i=0; i<this.transactions.size(); i++) {
			ItemSet is = new ItemSet();
			is.list.addAll(this.transactions.get(i).list);
			tran.transactions.add(is);
		}
		return tran;
	}

	/*
	 * This method is used to check if this IntermidiateTransaction is contained in the IntermidiateTransaction indicated by an para.
	 */
	public boolean presentIn(DataSequence tran){
		boolean result = true;
		int m = this.transactions.size();
		int n = tran.transactions.size();
		int i=0, j=0;
		for(i=0; i<m; i++) {
			ItemSet is = this.transactions.get(i);
			do {
				if (m-i > n-j) {	//The number of rest elements in current sequence
									//is greater than the number of rest elements in tran
					result = false;
					break;
				}
				if (is.isSubsetOf(tran.transactions.get(j))) {
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
				result = is.isSubsetOf(tran.transactions.get(j-1));
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
	 */
	public DataSequence reverse(){
		DataSequence rev=new DataSequence();
		ItemSet revIS=new ItemSet();
		int i,j;
		for(i=this.transactions.size()-1;i>=0;i--){
			ItemSet is=this.transactions.get(i);
			for(j=is.list.size()-1;j>=0;j--)
				revIS.list.add(new Integer(is.list.get(j).intValue()));
			rev.transactions.add(revIS);
			revIS = new ItemSet();
		}
		return rev;
	}
	/*
	 * This method is used to print a Data Sequence in a line
	 */
	
	public void print(){
		int i;
		System.out.print("Pattern: <"); 
		for(ItemSet is: transactions){
			System.out.print("{");
			for(i=0;i<is.list.size()-1;i++){  // print an element except the last item
				System.out.print(is.list.get(i)+",");
			}
			System.out.print(is.list.get(i)+"}");
			
			//print the last item in an element
		}
		System.out.println(">  ");
	}
	
	public boolean equals(Object tr) {
		DataSequence trans = (DataSequence) tr;
		boolean result = false;
		if (this.presentIn(trans) && this.transactions.size() == trans.transactions.size() && this.getAllItems().size() == trans.getAllItems().size())
			result = true;
		return result;
	}
	
	public int hashCode() {
		int result = 0;
		for (Integer it : this.getAllItems())
			result += it.intValue();
		return result;
	}
}
