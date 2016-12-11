package model;

import java.io.IOException;

import service.ClientService;

/**
 * Handles the encapsulation and representation of <code>Student</code> model.
 * 
 * @author In Yong S. Lee
 */
public class Student 
{

	private int studentID;
	private String studentPassword;
	private String studentFirstName;
	private String studentLastName;
	private String studentSection;

	/**
	 * Creates a blank representation for <code>Student</code> model.
	 */
	public Student() 
	{
		
	}

	/**
	 * Creates a representation that contains the information for <code>Student</code> model.
	 * @param studentID Student ID Number 
	 * @param studentPassword Student Password 
	 * @param studentFirstName Student First Name
	 * @param studentLastName Student Last Name 
	 * @param studentSection Student Section 
	 */
	public Student(int studentID, String studentPassword, String studentFirstName, String studentLastName,
			String studentSection) 
	{
		this.studentID = studentID;
		this.studentPassword = studentPassword;
		this.studentFirstName = studentFirstName;
		this.studentLastName = studentLastName;
		this.studentSection = studentSection;
	}

	/**
	 * Gets the <code>studentID</code> property.
	 * @return the <code>studentID</code>
	 */
	public int getStudentID() 
	{
		return studentID;
	}

	/**
	 * Sets the <code>studentID</code> to its preferred value.
	 * @param studentID the <code>studentID</code> to set
	 */
	public void setStudentID(int studentID) 
	{
		this.studentID = studentID;
	}

	/**
	 * Gets the <code>studentPassword</code> property.
	 * @return the <code>studentPassword</code>
	 */
	public String getStudentPassword() 
	{
		return studentPassword;
	}

	/**
	 * Sets the <code>studentPassword</code> to its preferred value.
	 * @param studentPassword the <code>studentPassword</code> to set
	 */
	public void setStudentPassword(String studentPassword) 
	{
		this.studentPassword = studentPassword;
	}

	/**
	 * Gets the <code>studentFirstName</code> property.
	 * @return the <code>studentFirstName</code>
	 */
	public String getStudentFirstName() {
		return studentFirstName;
	}

	/**
	 * Sets the <code>studentFirstName</code> to its preferred value.
	 * @param studentFirstName the <code>studentFirstName</code> to set
	 */
	public void setStudentFirstName(String studentFirstName) 
	{
		this.studentFirstName = studentFirstName;
	}

	/**
	 * Gets the <code>studentLastName</code> property.
	 * @return the <code>studentLastName</code>
	 */
	public String getStudentLastName() {
		return studentLastName;
	}

	/**
	 * Sets the <code>studentLastName</code> to its preferred value.
	 * @param studentLastName the <code>studentLastName</code> to set
	 */
	public void setStudentLastName(String studentLastName) 
	{
		this.studentLastName = studentLastName;
	}

	/**
	 * Gets the <code>studentSection</code> property.
	 * @return the <code>studentSection</code>
	 */
	public String getStudentSection() 
	{
		return studentSection;
	}

	/**
	 * Sets the <code>studentSection</code> to its preferred value.
	 * @param studentSection the <code>studentSection</code> to set
	 */
	public void setStudentSection(String studentSection) 
	{
		this.studentSection = studentSection;
	}

	/**
	 * Sends the <code>Student</code> model representation to the server-side.
	 * @throws IOException if the data cannot be written to bytes
	 */
	public void sendData() throws IOException
	{
	  String toBeSent = "student," + this.studentID + "," + this.studentPassword + "," + this.studentFirstName + "," + this.studentLastName + "," + this.studentSection;
	  ClientService client = ClientService.getClientService();
	  client.sendDataToServer(toBeSent);
	}
}
