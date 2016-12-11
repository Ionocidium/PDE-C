package model;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

import service.ClientService;
import service.FileManipulation;

/**
 * Handles the encapsulation and representation of <code>Activity</code> model.
 * 
 * @author In Yong S. Lee
 */
public class Activity 
{

	private int activityID;
	private String activityName;
	private File activityFile;
	private Timestamp activityTimeStamp;
	private Timestamp activityDeadline;
	private String activityFilename;
	
	/**
	 * Creates a blank representation for <code>Activity</code> model.
	 */
	public Activity() 
	{
		
	}

	/**
	 * Creates a representation that contains the information for <code>Activity</code> model.
	 * @param activityID Activity ID Number 
	 * @param activityName Activity Name 
	 * @param activityFile Activity File Object Representation
	 * @param activityTimeStamp Activity Uploaded by the professor 
	 * @param activityDeadline Activity Deadline 
	 * @param activityFilename Activity File Name (Absolute Path)
	 */
	public Activity(int activityID, String activityName, File activityFile, Timestamp activityTimeStamp,
			Timestamp activityDeadline, String activityFilename) 
	{
		this.activityID = activityID;
		this.activityName = activityName;
		this.activityFile = activityFile;
		this.activityTimeStamp = activityTimeStamp;
		this.activityDeadline = activityDeadline;
		this.activityFilename = activityFilename;
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
	 * Gets the <code>activityName</code> property.
	 * @return the <code>activityName</code>
	 */
	public String getActivityName() 
	{
		return activityName;
	}

	/**
	 * Sets the <code>activityName</code> to its preferred value.
	 * @param activityName the <code>activityName</code> to set
	 */
	public void setActivityName(String activityName) 
	{
		this.activityName = activityName;
	}

	/**
	 * Gets the <code>activityFile</code> property.
	 * @return the <code>activityFile</code>
	 */
	public File getActivityFile()
	{
		return activityFile;
	}

	/**
	 * Sets the <code>activityFile</code> to its preferred value.
	 * @param activityFile the <code>activityFile</code> to set
	 */
	public void setActivityFile(File activityFile) 
	{
		this.activityFile = activityFile;
	}

	/**
	 * Gets the <code>activityTimeStamp</code> property.
	 * @return the <code>activityTimeStamp</code>
	 */
	public Timestamp getActivityTimeStamp() 
	{
		return activityTimeStamp;
	}

	/**
	 * Sets the <code>activityTimeStamp</code> to its preferred value.
	 * @param activityTimeStamp the <code>activityTimeStamp</code> to set
	 */
	public void setActivityTimeStamp(Timestamp activityTimeStamp) 
	{
		this.activityTimeStamp = activityTimeStamp;
	}

	/**
	 * Gets the <code>activityDeadline</code> property.
	 * @return the <code>activityDeadline</code>
	 */
	public Timestamp getActivityDeadline() 
	{
		return activityDeadline;
	}

	/**
	 * Sets the <code>activityDeadline</code> to its preferred value.
	 * @param activityDeadline the <code>activityDeadline</code> to set
	 */
	public void setActivityDeadline(Timestamp activityDeadline) 
	{
		this.activityDeadline = activityDeadline;
	}

	/**
	 * Gets the <code>activityFilename</code> property.
	 * @return the <code>activityFilename</code>
	 */
	public String getActivityFilename() {
		return activityFilename;
	}

	/**
	 * Sets the <code>activityFilename</code> to its preferred value.
	 * @param activityFilename the <code>activityFilename</code> to set
	 */
	public void setActivityFilename(String activityFilename) 
	{
		this.activityFilename = activityFilename;
	}

	/**
	 * Sends the <code>Activity</code> model representation to the server-side.
	 * @throws IOException if the data cannot be written to bytes
	 */
	public void sendData() throws IOException
	{
	  FileManipulation fm = new FileManipulation();
	  String toBeSent = "activity," + this.activityID + "," + this.activityName + "," + fm.convertToBinary(this.activityFile) + "," + this.activityTimeStamp + "," + this.activityDeadline + "," + this.activityFilename;
	  
	  ClientService client = ClientService.getClientService();
	  client.sendDataToServer(toBeSent);
	}
}
