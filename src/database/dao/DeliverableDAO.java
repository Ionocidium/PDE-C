package database.dao;

import database.objects.Deliverable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliverableDAO extends DAO{
	
	public void addDeliverable (Deliverable dmdl) throws SQLException{
        int IDNumber = dmdl.getDeliverableID();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into Deliverable (DeliverableID) values(?)");
        preparedStatement.setInt(1, IDNumber);
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
    
    public Deliverable getDeliverable (int deliverableID) throws SQLException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from Deliverable where DeliverableID = ?");
        preparedStatement.setInt(1, deliverableID);
        ResultSet resultSet = query(preparedStatement);
        Deliverable bmdl = new Deliverable();
        while (resultSet.next()) {
            int IDNumber = resultSet.getInt("DeliverableID");
            bmdl.setDeliverableID(IDNumber);
        }
        close(preparedStatement, connection);
        return bmdl;
    }
    
    public ArrayList<Deliverable> getDeliverables () throws SQLException{
        ArrayList<Deliverable> activities = new ArrayList<Deliverable>();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from Deliverable order by DeliverableID");
        ResultSet resultSet = query(preparedStatement);
        while (resultSet.next()) {
        	Deliverable dmdl = new Deliverable();
            int IDNumber = resultSet.getInt("IDnumber");
            dmdl.setDeliverableID(IDNumber);
            activities.add(dmdl);
        }
        close(preparedStatement, connection);
        return activities;
    }

}
