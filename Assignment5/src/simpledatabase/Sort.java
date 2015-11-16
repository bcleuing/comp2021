package simpledatabase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
@SuppressWarnings("unchecked")

public class Sort extends Operator{
	
	private ArrayList<Attribute> newAttributeList;
	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;
	
	private int countReturned = 0;
	
	public Sort(Operator child, String orderPredicate){
		this.child = child;
		this.orderPredicate = orderPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();
		
		ArrayList sortedList = new ArrayList();
		Tuple fetchedTuple = child.next();
		
		int index = 0;
		// This loop is to find the location of the orderPredicate in the attribute list
		for (; index < fetchedTuple.getAttributeList().size(); index++)
			if (fetchedTuple.getAttributeName(index).equals(orderPredicate)) break;
		
		ArrayList<Tuple> tempTuples = new ArrayList<Tuple>();
		
		while (fetchedTuple != null) {
			tempTuples.add(fetchedTuple);
			newAttributeList.add(fetchedTuple.getAttributeList().get(index));
			fetchedTuple = child.next();
		}
		
		for (int a = 0; a < newAttributeList.size(); a++)
			sortedList.add(newAttributeList.get(a).getAttributeValue());
		
		Collections.sort(sortedList);
		
		
		for (int i = 0; i < sortedList.size(); i++) {
			for (int j = 0; j < tempTuples.size(); j++) {
				if (sortedList.get(i).equals(tempTuples.get(j).getAttributeValue(index))) {
					tuplesResult.add(tempTuples.get(j));
					tempTuples.remove(j);
				}
			}
		}
		
	}
	
	
	/**
     * The function is used to return the sorted tuple
     * @return tuple
     */
	@Override
	public Tuple next(){
		if (countReturned < tuplesResult.size())
			return tuplesResult.get(countReturned++);
		
		return null;		
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}