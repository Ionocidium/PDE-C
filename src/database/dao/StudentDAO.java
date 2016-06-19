package database.dao;

import database.objects.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAO extends DAO{
	
	public void addStudent (Student smdl) throws SQLException{
        int studentID = smdl.getStudentID();
        String studentPassword = smdl.getStudentPassword();
        String studentFirstName = smdl.getStudentFirstName();
        String studentLastName = smdl.getStudentLastName();
        String studentSection = smdl.getStudentSection();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into Student (StudentID, StudentPasssword, StudentFirstName, StudentLastName, StudentSection) values(?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, studentID);
        preparedStatement.setString(2, studentPassword);
        preparedStatement.setString(3, studentFirstName);
        preparedStatement.setString(4, studentLastName);
        preparedStatement.setString(5, studentSection);
        update(preparedStatement);
        close(preparedStatement, connection);
    }
	
	public void changePassword (int studentID, String newpwd) throws SQLException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update Student set StudentPassword = ? where StudentID = ?");
        preparedStatement.setString(1, newpwd);
        preparedStatement.setInt(2, studentID);
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
    
    public Student getStudent (int idNumber) throws SQLException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from Student where StudentID = ?");
        preparedStatement.setInt(1, idNumber);
        ResultSet resultSet = query(preparedStatement);
        Student smdl = new Student();
        while (resultSet.next()) {
            int studentID = resultSet.getInt("StudentID");
            String studentPassword = resultSet.getString("StudentPassword");
            String studentFirstName = resultSet.getString("StudentFirstName");
            String studentLastName = resultSet.getString("StudentLastName");
            String studentSection = resultSet.getString("StudentSection");
            smdl.setStudentID(studentID);
            smdl.setStudentPassword(studentPassword);
            smdl.setStudentFirstName(studentFirstName);
            smdl.setStudentLastName(studentLastName);
            smdl.setStudentSection(studentSection);
        }
        close(preparedStatement, connection);
        return smdl;
    }
    
    public ArrayList<Student> getStudents () throws SQLException{
        ArrayList<Student> students = new ArrayList<Student>();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from Student order by StudentID");
        ResultSet resultSet = query(preparedStatement);
        while (resultSet.next()) {
        	Student smdl = new Student();
            int studentID = resultSet.getInt("IDnumber");
            String studentPassword = resultSet.getString("StudentPassword");
            String studentFirstName = resultSet.getString("StudentFirstName");
            String studentLastName = resultSet.getString("StudentLastName");
            String studentSection = resultSet.getString("StudentSection");
            smdl.setStudentID(studentID);
            smdl.setStudentPassword(studentPassword);
            smdl.setStudentFirstName(studentFirstName);
            smdl.setStudentLastName(studentLastName);
            smdl.setStudentSection(studentSection);
            students.add(smdl);
        }
        close(preparedStatement, connection);
        return students;
    }

}
