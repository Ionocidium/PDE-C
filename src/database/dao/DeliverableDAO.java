package database.dao;

import database.objects.Deliverable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class DeliverableDAO extends DAO{
	
	public void addDeliverable (Deliverable dmdl) throws SQLException, FileNotFoundException{
        int deliverableID = dmdl.getDeliverableID();
    	int studentID = dmdl.getStudentID();
    	int activityID = dmdl.getActivityID();
    	File deliverableSourceCode = dmdl.getDeliverableSourceCode();
    	Timestamp dateSubmitted = dmdl.getDateSubmitted();
    	String deliverableSourceCodeFileName = dmdl.getDeliverableSourceCodeFileName();
    	float grade = dmdl.getGrade();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into Deliverable (DeliverableID, StudentID, ActivityID, DeliverableSourceCode, DateSubmitted, DeliverableSourceCodeFileName, Grade) values(?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, deliverableID);
        preparedStatement.setInt(2, studentID);
        preparedStatement.setInt(3, activityID);
        preparedStatement.setBlob(4, new FileInputStream(deliverableSourceCode));
        preparedStatement.setTimestamp(5, dateSubmitted);
        preparedStatement.setString(6, deliverableSourceCodeFileName);
        preparedStatement.setFloat(7, grade);
        update(preparedStatement);
        close(preparedStatement, connection);
    }
	
	public void changeGrade (int studentID, int activityID, Float newGrade) throws SQLException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update Deliverable set Grade = ? where StudentID = ? AND ActivityID = ?");
        preparedStatement.setFloat(1, newGrade);
        preparedStatement.setInt(2, studentID);
        preparedStatement.setInt(3, activityID);
        update(preparedStatement);
        close(preparedStatement, connection);
    }
	
	public void uploadNewSubmission (int studentID, int activityID, File newFile) throws SQLException, FileNotFoundException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update Deliverable set DeliverableSourceCode = ?, DeliverableSourceCodeFileName = ?, DateSubmitted = ? where StudentID = ? AND ActivityID = ?");
        preparedStatement.setBlob(1, new FileInputStream(newFile));
        preparedStatement.setString(2, newFile.getName());
        preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setInt(4, studentID);
        preparedStatement.setInt(5, activityID);
        update(preparedStatement);
        close(preparedStatement, connection);
    }
    
    public void deleteDeliverable (int deliverableID) throws SQLException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("delete from Deliverable where DeliverableID = ?");
        preparedStatement.setInt(1, deliverableID);
        update(preparedStatement);
        close(preparedStatement, connection);
    }
    
    public Deliverable getDeliverable (int idNumber) throws SQLException, IOException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from Deliverable where DeliverableID = ?");
        preparedStatement.setInt(1, idNumber);
        ResultSet resultSet = query(preparedStatement);
        Deliverable dmdl = new Deliverable();
        while (resultSet.next()) {
            int deliverableID = resultSet.getInt("DeliverableID");
        	int studentID = resultSet.getInt("StudentID");
        	int activityID = resultSet.getInt("ActivityID");
        	String deliverableSourceCodeFileName = resultSet.getString("DeliverableSourceCodeFileName");
        	Blob b = resultSet.getBlob("DeliverableSourceCode");
        	InputStream binaryStream = b.getBinaryStream(0, b.length());
        	byte[] buffer = new byte[binaryStream.available()];
            binaryStream.read(buffer);
        	File deliverableSourceCode = new File(deliverableSourceCodeFileName);
        	OutputStream outStream = new FileOutputStream(deliverableSourceCode);
        	outStream.write(buffer);
        	outStream.close();
        	Timestamp dateSubmitted = resultSet.getTimestamp("DateSubmitted");
        	float grade = resultSet.getFloat("Grade");
            dmdl.setDeliverableID(deliverableID);
            dmdl.setStudentID(studentID);
            dmdl.setActivityID(activityID);
            dmdl.setDeliverableSourceCode(deliverableSourceCode);
            dmdl.setDateSubmitted(dateSubmitted);
            dmdl.setDeliverableSourceCodeFileName(deliverableSourceCodeFileName);
            dmdl.setGrade(grade);
        }
        close(preparedStatement, connection);
        return dmdl;
    }
    
    public ArrayList<Deliverable> getDeliverables () throws SQLException, IOException{
        ArrayList<Deliverable> activities = new ArrayList<Deliverable>();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from Deliverable order by DeliverableID");
        ResultSet resultSet = query(preparedStatement);
        while (resultSet.next()) {
        	Deliverable dmdl = new Deliverable();
        	int deliverableID = resultSet.getInt("DeliverableID");
        	int studentID = resultSet.getInt("StudentID");
        	int activityID = resultSet.getInt("ActivityID");
        	String deliverableSourceCodeFileName = resultSet.getString("DeliverableSourceCodeFileName");
        	Blob b = resultSet.getBlob("DeliverableSourceCode");
        	InputStream binaryStream = b.getBinaryStream(0, b.length());
        	byte[] buffer = new byte[binaryStream.available()];
            binaryStream.read(buffer);
        	File deliverableSourceCode = new File(deliverableSourceCodeFileName);
        	OutputStream outStream = new FileOutputStream(deliverableSourceCode);
        	outStream.write(buffer);
        	outStream.close();
        	Timestamp dateSubmitted = resultSet.getTimestamp("DateSubmitted");
        	float grade = resultSet.getFloat("Grade");
            dmdl.setDeliverableID(deliverableID);
            dmdl.setStudentID(studentID);
            dmdl.setActivityID(activityID);
            dmdl.setDeliverableSourceCode(deliverableSourceCode);
            dmdl.setDateSubmitted(dateSubmitted);
            dmdl.setDeliverableSourceCodeFileName(deliverableSourceCodeFileName);
            dmdl.setGrade(grade);
            dmdl.setDeliverableID(deliverableID);
            activities.add(dmdl);
        }
        close(preparedStatement, connection);
        return activities;
    }

}
