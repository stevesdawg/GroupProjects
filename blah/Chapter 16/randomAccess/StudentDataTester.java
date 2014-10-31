package randomAccess;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class StudentDataTester {

	public static void main(String[] args) {
		
		StudentData sd = null;
		
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Choose a data file to read and write from: ");
		String filePath = keyboard.next();
		boolean exit = false;
		boolean filePathIncorrect = true;
		while(filePathIncorrect)
		{
			try
			{
				sd = new StudentData(filePath);
				filePathIncorrect = false;
			}
			catch (FileNotFoundException e)
			{
				System.out.println("File not found. Enter a new path: ");
				filePath = keyboard.next();
				filePathIncorrect = true;
			}
		}
		
		while(!exit)
		{	
			System.out.println("Write, read, find, edit, or exit");
			String action = keyboard.next();
			if(action.toLowerCase().equals("write"))
			{
				System.out.println("Enter age: ");
				int age = keyboard.nextInt();
				System.out.println("Enter gpa: ");
				double gpa = keyboard.nextDouble();
				System.out.println("Enter id number: ");
				int id = keyboard.nextInt();
				System.out.println("Enter first 10 characters of first name: ");
				String first = keyboard.next();
				System.out.println("Enter first 10 characters of last name: ");
				String last = keyboard.next();
				
				try {
					sd.writeStudent(age, gpa, id, first, last);
					System.out.println("Written to file.");
				} catch (IOException e) {
					System.out.println("Error Writing to File");
				}
			}
			
			else if(action.toLowerCase().equals("read"))
			{
				System.out.println("Which number student to read: ");
				int whichStudent = keyboard.nextInt();
				boolean argumentBad = true;
				while(argumentBad)
				{
					try
					{
						System.out.println("Student: " + sd.readStudent(whichStudent));
						argumentBad = false;
					}
					catch(IllegalArgumentException e)
					{
						System.out.println("Specified number exceeds number saved on file");
						System.out.println("New number: ");
						whichStudent = keyboard.nextInt();
						argumentBad = true;
					}
					catch(IOException e)
					{
						System.out.println("Error reading file.");
					}
				}
			}
			
			else if(action.toLowerCase().equals("find"))
			{
				System.out.println("Enter the id number of the student you wish to find: ");
				int id = keyboard.nextInt();
				try
				{
					System.out.println("The student number with this id number: " + sd.find(id));
				}
				catch (IOException e)
				{
					System.out.println("Error reading file");
				}
			}
			
			else if (action.toLowerCase().equals("edit"))
			{
				System.out.println("Enter id Number of student you wish to edit: ");
				int id = keyboard.nextInt();
				boolean argumentBad = true;
				try {
					argumentBad = sd.find(id) < 0;
				} catch (IOException e1) {
					System.out.println("Error reading file.");
				}
				while(argumentBad)
				{
					System.out.println("ID Number not found. Enter a new one.");
					id = keyboard.nextInt();
					try {
						argumentBad = sd.find(id) < 0;
					} catch (IOException e) {
						System.out.println("Error reading file.");
					}
				}
				
				System.out.println("Enter new age: ");
				int age = keyboard.nextInt();
				System.out.println("Enter new gpa: ");
				double gpa = keyboard.nextDouble();
				System.out.println("Enter new First Name: ");
				String first = keyboard.next();
				System.out.println("Enter new Last Name: ");
				String last = keyboard.next();
				
				try {
					sd.edit(id, age, gpa, first, last);
				} catch (IOException e) {
					System.out.println("Error reading file.");
				}
			}
			
			else if(action.toLowerCase().equals("exit"))
			{
				System.out.println("Exiting!");
				exit = true;
				try
				{
					sd.close();
				}
				catch (IOException e)
				{
					System.out.println("File did not close properly.");
				}
			}
			
		}
		
		keyboard.close();
	}

}
