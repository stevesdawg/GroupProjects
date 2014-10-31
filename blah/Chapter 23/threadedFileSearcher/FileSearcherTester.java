package threadedFileSearcher;

public class FileSearcherTester {

	public static void main(String[] args) {
		
		String query = args[0];
		for(int i = 1; i < args.length; i++)
		{
			(new Thread(new FileSearcher(args[i], query))).start();
		}

	}

}
