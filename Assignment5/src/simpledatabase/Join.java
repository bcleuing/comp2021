package simpledatabase;
import java.util.ArrayList;

public class Join extends Operator{

	private ArrayList<Attribute> newAttributeList;
	private String joinPredicate;
	ArrayList<Tuple> tuples1;
	
	private int leftLocation = 0;
	private int rightLocation = 0;
	
	private int countReturned = 0;
	
	private boolean existCommonAttr = false;
	
	//Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuples1 = new ArrayList<Tuple>();
		
		ArrayList<Tuple> leftTuples = new ArrayList<Tuple>();
		ArrayList<Tuple> rightTuples = new ArrayList<Tuple>();		
		
		Tuple leftChildTuple = leftChild.next();
		Tuple rightChildTuple = rightChild.next();
		
		while (leftChildTuple != null) {
			leftTuples.add(leftChildTuple);
			leftChildTuple = leftChild.next();
		}

		while (rightChildTuple != null) {
			rightTuples.add(rightChildTuple);
			rightChildTuple = rightChild.next();
		}
		
		leftChildTuple = leftTuples.get(0);
		rightChildTuple = rightTuples.get(0);
		// This loops is used to find the locations of the common attributes of the two tables
		for (; leftLocation < leftChildTuple.getAttributeList().size(); leftLocation++) {
			rightLocation = 0;
			for (int count = 0; count < rightChildTuple.getAttributeList().size(); count++) {
				if (leftChildTuple.getAttributeName(leftLocation).equals(rightChildTuple.getAttributeName(rightLocation))) {
					existCommonAttr = true;
					break;
				}
				rightLocation++;
			}
			if (existCommonAttr) break;
		}
		
		if (existCommonAttr) {
			// This loops is used to find the tuples with common attribute value and join them and append them to an arraylist of tuples
			for (int i = 0; i < rightTuples.size(); i++) {
				for (int j = 0; j < leftTuples.size(); j++) {
					if ((rightTuples.get(i).getAttributeValue(rightLocation)).equals(leftTuples.get(j).getAttributeValue(leftLocation))) {
						ArrayList<Attribute> rightAttrList = rightTuples.get(i).getAttributeList();
						ArrayList<Attribute> leftAttrList = leftTuples.get(j).getAttributeList();
												
						for (int k = 0; k < leftAttrList.size(); k++)
							if (k != leftLocation) newAttributeList.add(leftAttrList.get(k));
							
						for (int l = 0; l < rightAttrList.size(); l++)
							newAttributeList.add(rightAttrList.get(l));
						
						ArrayList<Attribute> returnAttrList = new ArrayList<Attribute>();
						
						for (int m = 0; m < newAttributeList.size(); m++)
							returnAttrList.add(newAttributeList.get(m));
						
						tuples1.add(new Tuple(returnAttrList));	
						
						newAttributeList.clear();
					}
				}
			}
		}
	
	}

	
	/**
     * It is used to return a new tuple which is already joined by the common attribute
     * @return the new joined tuple
     */
	//The record after join with two tables
	@Override
	public Tuple next(){
		if (!existCommonAttr) return null;
		
		if (countReturned < tuples1.size())
			return tuples1.get(countReturned++);

		return null;
	}
	
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		if(joinPredicate.isEmpty())
			return child.getAttributeList();
		else
			return(newAttributeList);
	}

}