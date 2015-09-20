package projectCS583;
import java.util.*;

/*
 * This is the class used to abstract ItemSet.
 * Every set will contains several items indicated by their itemIDs.
 */

public class ItemList {
	//using an ArrayList of integers to store items
	
	
public ArrayList<Integer> items;

public ItemList()
{
	items=new ArrayList<Integer>();
}
/*
 * This is to test whether current itemSet is a subset of the ItemSet, given in the parameter of the function
 */
public boolean isSubsetOf(ItemList itemlist)
{
	return itemlist.items.containsAll(this.items);
}
	
}
