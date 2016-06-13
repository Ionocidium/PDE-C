package database.dao;

import database.objects.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAO extends DAO{
	
	public void addStudent (Student smdl) throws SQLException{
        int IDNumber = smdl.getStudentID();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into Student (StudentID) values(?)");
        preparedStatement.setInt(1, IDNumber);
        update(preparedStatement);
        close(preparedStatement, connection);
    }
    
    public void deleteStudent (int studentID) throws SQLException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("delete from Student where StudentID = ?");
        preparedStatement.setInt(1, studentID);
        update(preparedStatement);
        close(preparedStatement, connection);
    }
    
    public Student getStudent (int studentID) throws SQLException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from Student where StudentID = ?");
        preparedStatement.setInt(1, studentID);
        ResultSet resultSet = query(preparedStatement);
        Student bmdl = new Student();
        while (resultSet.next()) {
            int IDNumber = resultSet.getInt("StudentID");
            bmdl.setStudentID(IDNumber);
        }
        close(preparedStatement, connection);
        return bmdl;
    }
    
    public ArrayList<Student> getStudents () throws SQLException{
        ArrayList<Student> activities = new ArrayList<Student>();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from Student order by StudentID");
        ResultSet resultSet = query(preparedStatement);
        while (resultSet.next()) {
        	Student smdl = new Student();
            int IDNumber = resultSet.getInt("IDnumber");
            smdl.setStudentID(IDNumber);
            activities.add(smdl);
        }
        close(preparedStatement, connection);
        return activities;
    }

}
