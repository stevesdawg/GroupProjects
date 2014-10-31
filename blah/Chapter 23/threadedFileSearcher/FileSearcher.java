package threadedFileSearcher;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class FileSearcher implements Runnable {
	
	private String path;
	private String query;
	
	public FileSearcher(String path, String query)
	{
		this.path = path;
		this.query = query;
	}
	
	public void run()
	{
		try {
			System.out.println(search());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String search() throws FileNotFoundException
	{
		FileReader reader = new FileReader(path);
		Scanner textFile = new Scanner(reader);
		int i = 1;
		String result = "";
		
		while (textFile.hasNext())
		{
			String line = textFile.nextLine();
			String[] words = line.split(" ");
			for(String word : words)
			{
				if(query.equals(word))
				{
					result += ("Line " + i + " in File: " + path + ". " + line + "\n");
				}
			}
		}
		
		textFile.close();
		return result;
	}
	
}
