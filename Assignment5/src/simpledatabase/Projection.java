package simpledatabase;
import java.util.ArrayList;

public class Projection extends Operator{
	
	ArrayList<Attribute> newAttributeList;
	private String attributePredicate;

	public Projection(Operator child, String attributePredicate){
		
		this.attributePredicate = attributePredicate;
		this.child = child;
		newAttributeList = new ArrayList<Attribute>();

	}
	
	
	/**
     * Return the data of the selected attribute as tuple format
     * @return tuple
     */
	@Override
	public Tuple next(){
		newAttributeList.clear();
		Tuple fetchedTuple = child.next();
		Tuple returnTuple = null;
		
		if (fetchedTuple != null) {
			for (int i = 0; i < fetchedTuple.getAttributeList().size(); i++) {
				if (fetchedTuple.getAttributeName(i).equals(attributePredicate)){
					
					newAttributeList.add(fetchedTuple.getAttributeList().get(i));
					
					returnTuple = new Tuple(newAttributeList);
				}
			}
			return returnTuple;
		}
		
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