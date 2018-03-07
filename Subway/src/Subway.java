import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Subway 
{
	public static void main(String args[])
	{
		File file = new File(args[0]);
		Graph graph = readFile(file);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while (true)
		{
			try
			{
				
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;
				
				String[] command = input.split("\\s");
				
				Dijkstra path = new Dijkstra(graph);
				path.findShortestPath(command[0], command[1]);
				
			}
			catch (IOException e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}
	
	private static Graph readFile(File file)
	{ // read file and insert all the substrings into the hash table
		String line;
		Graph graph = new Graph();
			
		try {
            // FileReader reads text files in the default encoding.
            InputStream fileReader = new FileInputStream(file);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileReader, "UTF-8"));
            
            while((line = bufferedReader.readLine()).length() != 0) {
            	String[] lineStr = line.split("\\s");
            	Node newNode = new Node(lineStr[0], lineStr[1], lineStr[2]);
            	graph.addNode(newNode);        
            }
                                
            while((line = bufferedReader.readLine()) != null) {
            	String[] lineStr = line.split("\\s");
            	graph.addEdge(lineStr[0], lineStr[1], Integer.parseInt(lineStr[2]));
            }

            // close file
            bufferedReader.close();  
            
            graph.sort();
            
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + file + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + file + "'");                  
        }

        return graph;
	} // end readFile
	

	
}
