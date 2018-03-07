import java.io.*;
import java.util.ArrayList;

public class Matching
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		HashTable<String, KeyItem> table = new HashTable<>();

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;
				
				String str = input.substring(2);
				
				switch (input.charAt(0)) 
				{
					case '<':
						table.clear();
						table = new HashTable<>();
						table = readFile(table, str);
						break;
					case '@':
						printSlot(table, str);
						break;
					case '?':
						patternMatching(table, str);
						break;
					default : 
						throw new IOException("Wrong command");
				}
			}
			catch (IOException e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static HashTable<String, KeyItem> readFile(HashTable<String, KeyItem> table, String fileName)
	{
		String line;
		int lineNo = 0;
		
		try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	++lineNo;
                table = hashing(table, line, lineNo);
                
            }   

            // close file
            bufferedReader.close();  
            
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                  
        }
		
        return table;
	} // end readFile
	
	// insert all the substrings each with 6 characters into hash table
	private static HashTable<String, KeyItem> hashing(HashTable<String, KeyItem> table, String line, int lineNo)
	{
		int index = 0;
		String subStr;
		
		while(index <= line.length()-6) {
			subStr = line.substring(index, index+6);
			KeyItem location = new KeyItem(lineNo, index+1);
			table.insert(subStr, location);
			index++;
		}
		
		return table;
	} // end hashing
	
	private static void printSlot(HashTable<String, KeyItem> table, String index)
	{ // print all the values in the 'index'th slot
		int i = Integer.parseInt(index);
		table.retieveSlot(i);
	} // end printSlot 
	
	private static void patternMatching(HashTable<String, KeyItem> table, String pattern)
	{
		int i = 0;
		String subPattern;
		ArrayList<KeyItem> matchedBefore, matched = new ArrayList<>();
		boolean noneMatched = false;
		
		while(i <= pattern.length()-6 && noneMatched == false) {
			matchedBefore = matched;
			subPattern = pattern.substring(i, i+6);
			matched = table.retrieveItem(table.hashIndex(subPattern), subPattern);
			if(matched.size() == 0) {
				noneMatched = true;
				System.out.println("(0, 0)");
				return;
			}
			else {
				matched = compare(matchedBefore, matched, 6);
				i += 6;
			}
		}
		if(i != pattern.length()) {
			int index = pattern.length() - i;
			matchedBefore = matched;
			subPattern = pattern.substring(index, index + 6);
			matched = table.retrieveItem(table.hashIndex(subPattern), subPattern);
			matched = compare(matchedBefore, matched, index);
		}
		
		for(i=0; i<matched.size()-1; i++) {
			System.out.print("(" + matched.get(i).getLineNo() + ", " + matched.get(i).getIndex() + ") ");
		}
		System.out.println("(" + matched.get(i).getLineNo() + ", " + matched.get(i).getIndex() + ")");
	
	} // end patternMatching
	
	private static ArrayList<KeyItem> compare(ArrayList<KeyItem> before, ArrayList<KeyItem> after, int index)
	{
		ArrayList<KeyItem> matched = new ArrayList<>();
		
		if(before.size() == 0) {
			return after;
		}
		
		for(int i = 0; i < before.size(); i++) {
			int j = 0;
			while(j < after.size() && after.get(j).getLineNo() <= before.get(i).getLineNo()) {
				if(before.get(i).getLineNo() == after.get(j).getLineNo()) {
					if(before.get(i).getIndex() == after.get(j).getIndex() - index) {
						matched.add(before.get(i));
						break;
					}
				}
				j++;
			}	
		}
		
		return matched;
	} // end compare
}

