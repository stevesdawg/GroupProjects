package randomAccess;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;


public class StudentData {
	
	private RandomAccessFile file;
	
	private static final int AGESIZE = 4;
	private static final int GPASIZE = 8;
	private static final int IDSIZE = 4;
	private static final int FIRSTNAMESIZE = 20;
	private static final int LASTNAMESIZE = 20;
	private static final int RECORDSIZE = AGESIZE + GPASIZE + FIRSTNAMESIZE + LASTNAMESIZE + IDSIZE;
	
	public StudentData(String fileName) throws FileNotFoundException
	{
		file = new RandomAccessFile(fileName, "rw");
	}
	
	public int size() throws IOException
	{
		return (int) (file.length() / RECORDSIZE);
	}
	
	public void writeStudent(int age, double gpa, int id, String firstName, String lastName) throws IOException
	{
		file.seek(file.length());
		file.writeInt(age);
		file.writeDouble(gpa);
		file.writeInt(id);
		for(int i = 0; i < 10; i++)
		{
			if(i < firstName.length() - 1)
				file.writeChar(firstName.charAt(i));
			else
				file.writeChars(" ");
		}
		for(int i = 0; i < 10; i++)
		{
			if (i < lastName.length() - 1)
				file.writeChar(lastName.charAt(i));
			else
				file.writeChars(" ");
		}
	}
	
	public void writeStudent(Student studentToWrite) throws IOException
	{
		this.writeStudent(studentToWrite.getAge(), studentToWrite.getGpa(), studentToWrite.getId(),
				studentToWrite.getFirstName(), studentToWrite.getLastName());
	}
	
	public Student readStudent(int whichStudent) throws IOException
	{
		
		if((whichStudent - 1) * RECORDSIZE >= file.length())
			throw new IllegalArgumentException();
		
		file.seek((whichStudent - 1) * RECORDSIZE);
		int age = file.readInt();
		double gpa = file.readDouble();
		int id = file.readInt();
		char[] first = new char[10];
		for(int i = 0; i < 10; i++)
		{
			first[i] = file.readChar();
		}
		char[] last = new char[10];
		for(int i = 0; i < 10; i++)
		{
			last[i] = file.readChar();
		}
		
		String firstName = new String(first);
		String lastName = new String(last);
		firstName = firstName.trim();
		lastName = lastName.trim();
		
		return new Student(age, gpa, id, firstName, lastName);
	}
	
	public int find(int idnumber) throws IOException
	{
		for (int i = 0; i < this.size(); i++)
		{
			file.seek(i * RECORDSIZE + 12);
			if(file.readInt() == idnumber)
				return i;
		}
		
		return -1;
	}
	
	public void edit(int idNumberToFindBy, int age, double gpa, String firstName, String lastName) throws IOException
	{
		int whichStudent = this.find(idNumberToFindBy);
		if(whichStudent < 0)
			throw new IllegalArgumentException();
		
		file.seek(whichStudent * RECORDSIZE);
		this.writeStudent(age, gpa, idNumberToFindBy, firstName, lastName);
	}
	
	public void edit(int idNumber, Student studentToReplaceOldWith) throws IOException
	{
		int whichStudent = this.find(idNumber);
		if(whichStudent < 0)
			throw new IllegalArgumentException();
		
		file.seek(whichStudent * RECORDSIZE);
		this.writeStudent(studentToReplaceOldWith);
	}
	
	public void close() throws IOException
	{
		file.close();
	}

}
