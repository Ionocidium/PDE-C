package model;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import service.ClientService;
import service.FileManipulation;

/**
 * Handles the encapsulation and representation of <code>Deliverable</code> model.
 * 
 * @author In Yong S. Lee
 */
public class Deliverable 
{

	private int deliverableID;
	private int studentID;
	private int activityID;
	private File deliverableSourceCode;
	private Timestamp dateSubmitted;
	private String deliverableSourceCodeFileName;
	private float grade;

	/**
	 * Creates a representation that contains the information for <code>Deliverable</code> model.
	 * @param deliverableID Deliverable ID
	 * @param studentID Student ID
	 * @param activityID Activity ID
	 * @param deliverableSourceCode Deliverable Source Code Object Representation
	 * @param dateSubmitted Date Submitted by the <code>Student</code>
	 * @param deliverableSourceCodeFileName Deliverable Source Code (Absolute Path)
	 * @param grade The corresponding grade of the <code>Student</code>
	 */
	public Deliverable(int deliverableID, int studentID, int activityID, File deliverableSourceCode,
			Timestamp dateSubmitted, String deliverableSourceCodeFileName, float grade) 
	{
		this.deliverableID = deliverableID;
		this.studentID = studentID;
		this.activityID = activityID;
		this.deliverableSourceCode = deliverableSourceCode;
		this.dateSubmitted = dateSubmitted;
		this.deliverableSourceCodeFileName = deliverableSourceCodeFileName;
		this.grade = grade;
	}

	/**
	 * Gets the <code>deliverableID</code> property.
	 * @return the <code>deliverableID</code>
	 */
	public int getDeliverableID() 
	{
		return deliverableID;
	}

	/**
	 * Sets the <code>deliverableID</code> to its preferred value.
	 * @param deliverableID the <code>deliverableID</code> to set
	 */
	public void setDeliverableID(int deliverableID) 
	{
		this.deliverableID = deliverableID;
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
	 * Gets the <code>activityID</code> property.
	 * @return the <code>activityID</code>
	 */
	public int getActivityID() 
	{
		return activityID;
	}

	/**
	 * Sets the <code>activityID</code> to its preferred value.
	 * @param activityID the <code>activityID</code> to set
	 */
	public void setActivityID(int activityID) 
	{
		this.activityID = activityID;
	}

	/**
	 * Gets the <code>deliverableSourceCode</code> property.
	 * @return the <code>deliverableSourceCode</code>
	 */
	public File getDeliverableSourceCode() 
	{
		return deliverableSourceCode;
	}

	/**
	 * Sets the <code>deliverableSourceCode</code> to its preferred value.
	 * @param deliverableSourceCode the <code>deliverableSourceCode</code> to set
	 */
	public void setDeliverableSourceCode(File deliverableSourceCode) 
	{
		this.deliverableSourceCode = deliverableSourceCode;
	}

	/**
	 * Gets the <code>dateSubmitted</code> property.
	 * @return the <code>dateSubmitted</code>
	 */
	public Timestamp getDateSubmitted() 
	{
		return dateSubmitted;
	}

	/**
	 * Sets the <code>dateSubmitted</code> to its preferred value.
	 * @param dateSubmitted the <code>dateSubmitted</code> to set
	 */
	public void setDateSubmitted(Timestamp dateSubmitted) 
	{
		this.dateSubmitted = dateSubmitted;
	}

	/**
	 * Gets the <code>deliverableSourceCodeFileName</code> property.
	 * @return the <code>deliverableSourceCodeFileName</code>
	 */
	public String getDeliverableSourceCodeFileName() 
	{
		return deliverableSourceCodeFileName;
	}

	/**
	 * Sets the <code>deliverableSourceCodeFileName</code> to its preferred value.
	 * @param deliverableSourceCodeFileName the <code>deliverableSourceCodeFileName</code> to set
	 */
	public void setDeliverableSourceCodeFileName(String deliverableSourceCodeFileName) 
	{
		this.deliverableSourceCodeFileName = deliverableSourceCodeFileName;
	}

	/**
	 * Gets the <code>grade</code> property.
	 * @return the <code>grade</code>
	 */
	public float getGrade() 
	{
		return grade;
	}

	/**
	 * Sets the <code>grade</code> to its preferred value.
	 * @param grade the <code>grade</code> to set
	 */
	public void setGrade(float grade) 
	{
		this.grade = grade;
	}

	/**
	 * Sends the <code>Deliverable</code> model representation to the server-side.
	 * @throws IOException if the data cannot be written to bytes
	 */
	public void sendData() throws IOException
	{
	  FileManipulation file = new FileManipulation();
	  String toBeSent = "deliverable," + this.deliverableID + "," + this.studentID + "," + this.activityID + "," + file.convertToBinary(this.deliverableSourceCode) + "," + this.dateSubmitted + "," +
		  				this.deliverableSourceCodeFileName + "," + this.grade;
	  
	  ClientService client = ClientService.getClientService();
	  client.sendDataToServer(toBeSent);
	}
	
}
