package library.dao;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class AdminDAO {
	private Connection conn;
	
	public AdminDAO() throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","BeConfident@21");
	}
	
	public void insertLibrarian(String id, String mail, String name, String password) throws SQLException {
		try {
			String query = "INSERT INTO user(id,email_id,name,password) VALUES (?,?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, mail);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, password);
			int n = preparedStatement.executeUpdate();
			System.out.println(n + " librarian added");
		}
		catch(SQLIntegrityConstraintViolationException e) {
			System.out.println("E-mail already exists");
		}
	}
	
	public List<List<String>> getLibrarians() throws SQLException{
		List<List<String>> librarians = new ArrayList<>(); 
		String query = "SELECT name, email_id FROM user where SUBSTR(id,1,3)='lib'";
		Statement statement = conn.createStatement();
		ResultSet res = statement.executeQuery(query);
		while (res.next()) {
			List<String> librarian = new ArrayList<>();
			librarian.add(res.getString("name"));
			librarian.add(res.getString("email_id"));
			librarians.add(librarian);
		}
		return librarians;
	}
	
	public void deleteLibrarian(String id) throws SQLException {
		String query = "DELETE FROM user where id=?";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setString(1, id);
		int n = preparedStatement.executeUpdate();
		System.out.println(n + " librarian deleted");	
	}
	
	public boolean verifyAdmin(String id, String password) throws SQLException{
		String query = "SELECT * FROM user WHERE id=? AND password=?";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setString(1, id);
		preparedStatement.setString(2, password);
		ResultSet rs = preparedStatement.executeQuery();
		return rs.next();
	}
	
	public boolean resetPassword(String id, String newPassword) throws SQLException {
		String query = "UPDATE user SET password=? WHERE id=?";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setString(1, newPassword);
		preparedStatement.setString(2, id);
		if (preparedStatement.executeUpdate()==1) {
			System.out.println("Password reset successfully");
			return true;
		}else {
			System.out.println("Password not reset");
			return false;
		}
	}
	
}