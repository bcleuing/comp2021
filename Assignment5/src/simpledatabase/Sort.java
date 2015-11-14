package simpledatabase;
import java.util.ArrayList;
import java.util.Collections;
@SuppressWarnings("unchecked")

public class Sort extends Operator{
	
	private ArrayList newAttributeList;
	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;
	
	private int countReturned = 0;
	
	public Sort(Operator child, String orderPredicate){
		this.child = child;
		this.orderPredicate = orderPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();
		
		Tuple fetchedTuple = child.next();
		
		int index = 0;
		// This loop is to find the location of the orderPredicate in the attribute list
		for (; index < fetchedTuple.getAttributeList().size(); index++)
			if (fetchedTuple.getAttributeName(index).equals(orderPredicate)) break;
		
		ArrayList<Tuple> tempTuples = new ArrayList<Tuple>();
		
		while (fetchedTuple != null) {
			tempTuples.add(fetchedTuple);
			newAttributeList.add(fetchedTuple.getAttributeValue(index));
			fetchedTuple = child.next();
		}

		Collections.sort(newAttributeList);
		
		
		for (int i = 0; i < newAttributeList.size(); i++) {
			for (int j = 0; j < tempTuples.size(); j++) {
				if (newAttributeList.get(i).equals(tempTuples.get(j).getAttributeValue(index))) {
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