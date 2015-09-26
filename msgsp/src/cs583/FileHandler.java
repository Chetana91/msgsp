package cs583;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/*
 Assumption:
 1. MIS and SDC values lie between 0 and 1
 2. No value is missing - checked
 3. The file contains values if the form "MIS(<Integer>) = <Double>"
 */

public class FileHandler {

	public HashMap<Integer, Double> readMIS(String file) {
		File source = new File(file);
		HashMap<Integer, Double> MISMap = new HashMap<Integer, Double>();
		try {
			Scanner scanner = new Scanner(source);
			Pattern p = Pattern.compile("MIS\\((\\d+)\\)\\s*=\\s*(.+)");
			Pattern p2 = Pattern.compile("SDC\\s*=\\s*(.+)");
			while (true) {
				if (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					Matcher m = p.matcher(line);
					if (m.find()) {
						int item = Integer.parseInt(m.group(1));
						double mis = Double.parseDouble(m.group(2));
						MISMap.put(item, mis);
					} else {
						Matcher m2 = p2.matcher(line);
						if (m2.find()) { // Case for SDC
							MsGsp.SDC = Double.parseDouble(m2.group(1));
							break;
						} else {
							break;
						}
					}
				}
			}
			scanner.close();
		} catch (FileNotFoundException | PatternSyntaxException e) {
			e.printStackTrace();
			return null;
		}
		return MISMap;
	}

	public ArrayList<DataSequence> readData(String file) {
		// (?:\{(\s*\d+\s*(,\s*\d+\s*)*)(?=\}))+
		File source = new File(file);
		System.out.println("Reading data");
		ArrayList<DataSequence> data = new ArrayList<DataSequence>();
		try {
			Scanner scanner = new Scanner(source);
			//(?:\{([\d\s,]+)(?=\}))+
			//(?:\\{(\\d+(,\\s*\\d+)*)(?=\\}))+
			//Pattern p = Pattern.compile("\\{(\\s*\\d+\\s*(,\\s*\\d+\\s*)*)(?=\\})");
			Pattern p = Pattern.compile("\\{(\\d+(,\\s\\d+)*)(?=\\})");
			while (scanner.hasNextLine()) {
				DataSequence dataSequence = new DataSequence();
				String line = scanner.nextLine();
				Matcher m = p.matcher(line);
				//System.out.println("here");
				while (m.find()) {
					//System.out.println("Match found");
					//System.out.println("line: **"+line+"** count: "+m.groupCount());
					String seq = m.group(1);
					//System.out.println("**"+seq+"**");
					
					String itemStringArray[] = seq.split(",");
					ItemSet itemSet = new ItemSet(itemStringArray.length);
					for(String itemString: itemStringArray) {
						//System.out.println("***"+itemString+"**");
						itemSet.items.add(Integer.parseInt(itemString.trim()));
					}
					dataSequence.sequence.add(itemSet);
				}
				if(dataSequence.sequence.size() == 0)
					return null;
				data.add(dataSequence);
			}
			scanner.close();
		} catch (FileNotFoundException | PatternSyntaxException | NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
		System.out.println("Returning "+data.size());
		return data;
	}

}




/*


try {
Scanner scanner = new Scanner(source);
//(?:\{([\d\s,]+)(?=\}))+
//(?:\\{(\\d+(,\\s*\\d+)*)(?=\\}))+
Pattern p = Pattern.compile("(?:\\{([\\d\\s*,]+)(?=\\}))+");
while (scanner.hasNextLine()) {
	String line = scanner.nextLine();
	Matcher m = p.matcher(line);
	System.out.println("here");
	if (m.find(0)) {
		System.out.println("Match found");
		DataSequence dataSequence = new DataSequence();
		System.out.println("line: **"+line+"** count: "+m.groupCount());
		for( int i=1; i<= m.groupCount(); i++ ) {
			String seq = m.group();
			System.out.println("**"+seq+"**");
			String itemStringArray[] = seq.split(",");
			ItemSet itemSet = new ItemSet(itemStringArray.length);
			for(String itemString: itemStringArray) {
				System.out.println("["+itemString+"]");
				itemSet.items.add(Integer.parseInt(itemString.trim()));
			}
			dataSequence.sequence.add(itemSet);
		}
	} else {
		//System.out.println("Match not found");
		break;
	}
}
scanner.close();
}


*/