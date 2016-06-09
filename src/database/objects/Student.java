package database.objects;

public class Student 
{

	private int studentID;
	private String studentPassword;
	private String studentFirstName;
	private String studentLastName;
	private String studentSection;
	
	public Student() 
	{
		
	}

	public Student(int studentID, String studentPassword, String studentFirstName, String studentLastName,
			String studentSection) 
	{
		this.studentID = studentID;
		this.studentPassword = studentPassword;
		this.studentFirstName = studentFirstName;
		this.studentLastName = studentLastName;
		this.studentSection = studentSection;
	}
	
	public int getStudentID() 
	{
		return studentID;
	}
	
	public void setStudentID(int studentID) 
	{
		this.studentID = studentID;
	}
	
	public String getStudentPassword() 
	{
		return studentPassword;
	}
	
	public void setStudentPassword(String studentPassword) 
	{
		this.studentPassword = studentPassword;
	}
	
	public String getStudentFirstName() {
		return studentFirstName;
	}
	
	public void setStudentFirstName(String studentFirstName) 
	{
		this.studentFirstName = studentFirstName;
	}
	
	public String getStudentLastName() {
		return studentLastName;
	}
	
	public void setStudentLastName(String studentLastName) 
	{
		this.studentLastName = studentLastName;
	}
	
	public String getStudentSection() 
	{
		return studentSection;
	}
	
	public void setStudentSection(String studentSection) 
	{
		this.studentSection = studentSection;
	}
	
}
