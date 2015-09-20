package projectCS583;
import java.util.*;

/*
 * This is the class used to abstract ItemSet.
 * Every set will contains several items indicated by their itemIDs.
 */

public class ItemSet {
	//using an ArrayList of integers to store items
public ArrayList<Integer> list;

public ItemSet()
{
	list=new ArrayList<Integer>();
}
/*
 * This is to test whether current itemSet is a subset of the ItemSet, given in the parameter of the function
 */
public boolean isSubsetOf(ItemSet itemset)
{
	return itemset.list.containsAll(this.list);
}
	
}
