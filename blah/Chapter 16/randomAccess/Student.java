package randomAccess;

public class Student {
	
	private int age;
	private double gpa;
	private int idNumber;
	private String firstName;
	private String lastName;
	
	public Student(int age, double gpa, int idNumber, String first, String last)
	{
		this.age = age;
		this.gpa = gpa;
		this.idNumber = idNumber;
		this.firstName = first;
		this.lastName = last;
	}
	
	public String toString()
	{
		return "IDNumber: " + idNumber + "\nNAME: " + firstName + " " + lastName + "\nAGE: " + age + "\nGPA: " + gpa;
	}
	
	public double getGpa()
	{
		return gpa;
	}
	
	public int getAge()
	{
		return age;
	}
	
	public int getId()
	{
		return idNumber;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}

}
