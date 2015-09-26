package projectCS583;
import java.util.*;
import java.io.*;


/*
 * Class to read MIS support values from input files
 */
public class InputReader {

	public static HashMap <Integer,Float> readMISvalues(String filename){
		
		HashMap<Integer,Float> mis = new HashMap<Integer,Float>();
		try
		{  
		File inputfile =  new File(filename);
			Scanner scanner= new Scanner(inputfile);
			while(true)
			{
				String record= scanner.nextLine(); //reading each line from parameters.txt into record,one line at a time
				if(scanner.hasNextLine())
				{
					// extract the itemID
					Integer itemID=Integer.valueOf(record.substring(record.indexOf('(')+1, record.indexOf(')')));
					// extract the minimum item support
					Float itemMIS=Float.valueOf(record.substring(record.indexOf('=')+2)); // There must be a space after '=' to make this line work
					mis.put(itemID,itemMIS);
				}
				else
				{msgsp.SDC=Float.valueOf(record.substring(record.indexOf('=')+2));
				break;
				
				}
				
			}//end of while
		
		}//end of try
		catch(IOException e)
		{ e.printStackTrace();
		  return null;
		}
		return mis;
	}//end of readMISValues
	public static ArrayList<DataSequence> readDataItems(String filename){
		ArrayList<DataSequence> trans=new ArrayList<DataSequence>();
		try{
			File file = new File(filename);
			BufferedReader f=new BufferedReader(new FileReader(file));
			
			String record=f.readLine();
			while(record != null){
				DataSequence transaction=new DataSequence(); // create a new transaction 
				//index for next open parenthesis in current line
				int idxOpenParen=record.indexOf('{'); 
				//index for next closed parenthesis in current line
				int idxClosedParen=record.indexOf('}');
				
				while(idxOpenParen<record.length()-1){
					ItemList is=new ItemList(); // create an new SetofItems for this transaction 
					//index for next comma between current open parentheses
					int idxComma=record.indexOf(',',idxOpenParen); 
					//index indicating the starting position of next number within current parentheses
					int idxNumStart=idxOpenParen+1;
					//index indicating the ending position of next number within current parentheses. The character on this position is either a '}' or ','.
					int idxNumEnd=min(idxClosedParen,idxComma);
										
					while(idxNumEnd<=idxClosedParen){
						is.items.add(Integer.valueOf(record.substring(idxNumStart,idxNumEnd).trim())); // add an item to the SetofItems
						if(idxNumEnd==idxClosedParen) //reach the end of the SetofItems
							break;
						// locate beginning and ending positions for next item in the current SetofItems
						idxNumStart=idxComma+1;
						idxComma=record.indexOf(',',idxNumStart); 
						idxNumEnd=min(idxClosedParen,idxComma);
					}
															
					// locate beginning and ending positions for next SetofItems
					idxOpenParen=idxClosedParen+1;
					idxClosedParen=record.indexOf('}',idxOpenParen);
					transaction.sequence.add(is); // add an SetofItems to the current transaction
				}
				
				trans.add(transaction);  // add an transaction 
				record=f.readLine();
			}
			return trans;
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	public static int min(int idxClosedParen, int idxComma){
		if(idxComma<0)
			return idxClosedParen;
		else
			return idxClosedParen<idxComma?idxClosedParen:idxComma;
	}
	
} //end of class InputReader
