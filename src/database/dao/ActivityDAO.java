package database.dao;

import database.objects.Activity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ActivityDAO extends DAO{
	
	public void addActivity (Activity amdl) throws SQLException{
        int IDNumber = amdl.getActivityID();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into Activity (ActivityID) values(?)");
        preparedStatement.setInt(1, IDNumber);
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
    
    public Activity getActivity (int activityID) throws SQLException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from Activity where ActivityID = ?");
        preparedStatement.setInt(1, activityID);
        ResultSet resultSet = query(preparedStatement);
        Activity bmdl = new Activity();
        while (resultSet.next()) {
            int IDNumber = resultSet.getInt("ActivityID");
            bmdl.setActivityID(IDNumber);
        }
        close(preparedStatement, connection);
        return bmdl;
    }
    
    public ArrayList<Activity> getActivities () throws SQLException{
        ArrayList<Activity> activities = new ArrayList<Activity>();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from Activity order by ActivityID");
        ResultSet resultSet = query(preparedStatement);
        while (resultSet.next()) {
        	Activity amdl = new Activity();
            int IDNumber = resultSet.getInt("IDnumber");
            amdl.setActivityID(IDNumber);
            activities.add(amdl);
        }
        close(preparedStatement, connection);
        return activities;
    }
    
}
