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
					
				}
				else
				{
					
				}
				
			}
		}
		catch(IOException e)
		{ e.printStackTrace();
			
		}
		
		
		return mis;
	}
	
	
} //end of class InputReader
