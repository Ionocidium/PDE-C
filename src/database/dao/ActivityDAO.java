package database.dao;

import database.objects.Activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;

public class ActivityDAO extends DAO{
	
	public void addActivity (Activity amdl) throws SQLException, FileNotFoundException{
        int activityID = amdl.getActivityID();
        String activityName = amdl.getActivityName();
    	File activityFile = amdl.getActivityFile();
    	Timestamp activityTimeStamp = amdl.getActivityTimeStamp();
    	Date activityDeadline = amdl.getActivityDeadline();
    	String activityFilename = amdl.getActivityFilename();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into Activity (ActivityID, ActivityName, ActivityFile, ActivityTimestamp, ActivityDeadline, ActivityFilename) values(?)");
        preparedStatement.setInt(1, activityID);
        preparedStatement.setString(2, activityName);
        preparedStatement.setBlob(3, new FileInputStream(activityFile));
        preparedStatement.setTimestamp(4, activityTimeStamp);
        preparedStatement.setDate(5, activityDeadline);
        preparedStatement.setString(6, activityFilename);
        update(preparedStatement);
        close(preparedStatement, connection);
    }
    
    public void deleteActivity (int activityID) throws SQLException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("delete from Activity where ActivityID = ?");
        preparedStatement.setInt(1, activityID);
        update(preparedStatement);
        close(preparedStatement, connection);
    }
    
    public void changeDate (int activityID, Date newDate) throws SQLException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update Activity set ActivityDeadline = ?, ActivityTimestamp = ? where ActivityID = ?");
        preparedStatement.setDate(1, newDate);
        preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setInt(3, activityID);
        update(preparedStatement);
        close(preparedStatement, connection);
    }
    
    public void changeName (int activityID, String newName) throws SQLException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update Activity set ActivityName = ?, ActivityTimestamp = ? where ActivityID = ?");
        preparedStatement.setString(1, newName);
        preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setInt(3, activityID);
        update(preparedStatement);
        close(preparedStatement, connection);
    }
    
    public void updateFile (int activityID, File newFile) throws SQLException, FileNotFoundException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update Activity set ActivityFile = ?, ActivityFileName = ?, ActivityTimestamp = ? where ActivityID = ?");
        preparedStatement.setBlob(1, new FileInputStream(newFile));
        preparedStatement.setString(2, newFile.getName());
        preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setInt(4, activityID);
        update(preparedStatement);
        close(preparedStatement, connection);
    }
    
    public Activity getActivity (int idNumber) throws SQLException, IOException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from Activity where ActivityID = ?");
        preparedStatement.setInt(1, idNumber);
        ResultSet resultSet = query(preparedStatement);
        Activity amdl = new Activity();
        while (resultSet.next()) {
            int activityID = resultSet.getInt("ActivityID");
            String activityName = resultSet.getString("ActivityName");
            String activityFilename = resultSet.getString("ActivityFilename");
        	Blob b = resultSet.getBlob("ActivityFile");
        	InputStream binaryStream = b.getBinaryStream(0, b.length());
        	byte[] buffer = new byte[binaryStream.available()];
            binaryStream.read(buffer);
        	File activityFile = new File(activityFilename);
            Timestamp activityTimeStamp = resultSet.getTimestamp("ActivityTimeStamp");
            Date activityDeadline = resultSet.getDate("ActivityDeadline");
            amdl.setActivityID(activityID);
            amdl.setActivityName(activityName);
            amdl.setActivityFile(activityFile);
            amdl.setActivityTimeStamp(activityTimeStamp);
            amdl.setActivityDeadline(activityDeadline);
            amdl.setActivityFilename(activityFilename);
        }
        close(preparedStatement, connection);
        return amdl;
    }
    
    public ArrayList<Activity> getActivities () throws SQLException, IOException{
        ArrayList<Activity> activities = new ArrayList<Activity>();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from Activity order by ActivityID");
        ResultSet resultSet = query(preparedStatement);
        while (resultSet.next()) {
        	Activity amdl = new Activity();
            int activityID = resultSet.getInt("ActivityID");
            String activityName = resultSet.getString("ActivityName");
            String activityFilename = resultSet.getString("ActivityFilename");
        	Blob b = resultSet.getBlob("ActivityFile");
        	InputStream binaryStream = b.getBinaryStream(0, b.length());
        	byte[] buffer = new byte[binaryStream.available()];
            binaryStream.read(buffer);
        	File activityFile = new File(activityFilename);
            Timestamp activityTimeStamp = resultSet.getTimestamp("ActivityTimeStamp");
            Date activityDeadline = resultSet.getDate("ActivityDeadline");
            amdl.setActivityID(activityID);
            amdl.setActivityName(activityName);
            amdl.setActivityFile(activityFile);
            amdl.setActivityTimeStamp(activityTimeStamp);
            amdl.setActivityDeadline(activityDeadline);
            amdl.setActivityFilename(activityFilename);
            activities.add(amdl);
        }
        close(preparedStatement, connection);
        return activities;
    }
    
}
