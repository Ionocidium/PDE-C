package database.objects;

import java.io.File;
import java.sql.Timestamp;

public class Deliverable 
{

	private int deliverableID;
	private int studentID;
	private int activityID;
	private File deliverableSourceCode;
	private Timestamp dateSubmitted;
	private String deliverableFileName;
	private float grade;
	
	public Deliverable() 
	{
		
	}

	public Deliverable(int deliverableID, int studentID, int activityID, File deliverableSourceCode,
			Timestamp dateSubmitted, String deliverableFileName, float grade) 
	{
		this.deliverableID = deliverableID;
		this.studentID = studentID;
		this.activityID = activityID;
		this.deliverableSourceCode = deliverableSourceCode;
		this.dateSubmitted = dateSubmitted;
		this.deliverableFileName = deliverableFileName;
		this.grade = grade;
	}
	
	public int getDeliverableID() 
	{
		return deliverableID;
	}
	
	public void setDeliverableID(int deliverableID) 
	{
		this.deliverableID = deliverableID;
	}
	
	public int getStudentID() 
	{
		return studentID;
	}
	
	public void setStudentID(int studentID) 
	{
		this.studentID = studentID;
	}
	
	public int getActivityID() 
	{
		return activityID;
	}
	
	public void setActivityID(int activityID) 
	{
		this.activityID = activityID;
	}
	
	public File getDeliverableSourceCode() 
	{
		return deliverableSourceCode;
	}
	
	public void setDeliverableSourceCode(File deliverableSourceCode) 
	{
		this.deliverableSourceCode = deliverableSourceCode;
	}
	
	public Timestamp getDateSubmitted() 
	{
		return dateSubmitted;
	}
	
	public void setDateSubmitted(Timestamp dateSubmitted) 
	{
		this.dateSubmitted = dateSubmitted;
	}
	
	public String getDeliverableFileName() 
	{
		return deliverableFileName;
	}
	
	public void setDeliverableFileName(String deliverableFileName) 
	{
		this.deliverableFileName = deliverableFileName;
	}
	
	public float getGrade() 
	{
		return grade;
	}
	
	public void setGrade(float grade) 
	{
		this.grade = grade;
	}
	
}
