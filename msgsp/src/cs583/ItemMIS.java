package cs583;

public class ItemMIS implements Comparable<ItemMIS>{
	
	Integer item;
	Double mis;

	public ItemMIS() { }
		
	public ItemMIS(Integer item, Double mis) {
		this.item = item;
		this.mis = mis;
	}

	@Override
	public int compareTo(ItemMIS itemMis) {
		final int BEFORE = -1;
	    final int AFTER = 1;
	    
	    if (this.mis > itemMis.mis) {
	    	return AFTER;
	    }
	    else if (this.mis == itemMis.mis) {
	    	if (this.item > itemMis.item)
	    		return AFTER;
	    	else
	    		return BEFORE;
	    }
	    else {
	    	return BEFORE;
	    }
	}
}
