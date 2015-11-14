package simpledatabase;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Table extends Operator{
	private BufferedReader br = null;
	private boolean getAttribute = false;
	private Tuple tuple;
	
	private String attributeLine;
	private String dataTypeLine;
	private String tupleLine;
	
	private ArrayList<String> tupleLines = new ArrayList<String>();
	private int count = 0;
	
	public Table(String from){
		this.from = from;
		
		//Create buffer reader
		try{
			br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/datafile/"+from+".csv")));
			attributeLine = br.readLine();
			dataTypeLine = br.readLine();
			tupleLine = br.readLine();
			while (tupleLine != null) {
				tupleLines.add(tupleLine);
				tupleLine = br.readLine();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	
	/**
     * Create a new tuple and return the tuple to its parent.
     * Set the attribute list if you have not prepare the attribute list
     * @return the tuple
     */
	@Override
	public Tuple next(){
		if (count == tupleLines.size()) return null;
		tuple = new Tuple(attributeLine, dataTypeLine, tupleLines.get(count++));
		tuple.setAttributeName();
		tuple.setAttributeType();
		tuple.setAttributeValue();
		return tuple;
	}
	

	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return tuple.getAttributeList();
	}
	
}