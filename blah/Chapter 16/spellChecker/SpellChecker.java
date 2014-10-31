package spellChecker;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class SpellChecker {
	
	private String dictPath;
	private String specialCharacters;
	private ArrayList<String> dictionaryWords;
	private final static String specialChars = "?!%$#.,';][}{)(\"/\\";
	
	public SpellChecker(String dictPath, String specialChars) throws FileNotFoundException
	{
		this.dictPath = dictPath;
		this.specialCharacters = specialChars;
		this.dictionaryWords = getDictionaryWords(dictPath);
	}

	public static void main(String[] args)
	{
		SpellChecker sc = null;
		try
		{
			sc = new SpellChecker("./dictionary/american-english", specialChars);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Dictionary file Not Found");
		}
		
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter text file path: ");
		String path = keyboard.next();
		while(!path.startsWith("qui"))
		{
			try
			{
				System.out.println(sc.checkSpellingUsingPath(path)+"\n");
			}
			catch (FileNotFoundException e)
			{
				System.out.println("Specified Text File Not Found");
			}
			finally
			{
				System.out.println("Enter textfile path or type quit to exit");
				path = keyboard.next();
			}
		}
	}
	
	public ArrayList<String> checkSpellingUsingPath(String textFilePath) throws FileNotFoundException
	{
		ArrayList<String> allWords = new ArrayList<String>();
		ArrayList<String> wrongWords = new ArrayList<String>();
		
		FileReader textFileReader = new FileReader(textFilePath);
		Scanner textScanner = new Scanner(textFileReader);
		
		while(textScanner.hasNext())
		{
			String word = textScanner.next();
			if (specialCharacters.indexOf(word.charAt(word.length() - 1)) >= 0)
				word = word.substring(0, word.length() - 1);
			if(specialCharacters.indexOf(word.charAt(0)) >= 0)
				word = word.substring(1, word.length());
			
			allWords.add(word);
		}
		
		if(this.dictionaryWords.size() == 0)
			this.dictionaryWords = getDictionaryWords(this.dictPath);
		
		for(String word : allWords)
		{
			if(Collections.binarySearch(dictionaryWords, word) < 0)
				wrongWords.add(word);
		}
		
		textScanner.close();
		return wrongWords;
	}
	
	public static ArrayList<String> getDictionaryWords(String dictPath) throws FileNotFoundException
	{
		ArrayList<String> dictionaryWords = new ArrayList<String>();
		FileReader dictionaryReader = new FileReader(dictPath);
		Scanner dictionaryScanner = new Scanner(dictionaryReader);
		
		while(dictionaryScanner.hasNext())
		{
			dictionaryWords.add(dictionaryScanner.next());
		}
		
		dictionaryScanner.close();
		return dictionaryWords;
	}
	
	public void setDictionaryPath(String dictPath)
	{
		this.dictPath = dictPath;
	}

}
