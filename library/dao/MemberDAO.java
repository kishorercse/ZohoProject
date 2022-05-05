package library.dao;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import library.model.Book;

public class MemberDAO {
	
	private Connection conn;
	
	public MemberDAO() throws SQLException{
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","BeConfident@21");
	}
	
	public String verifyMember(String id, String password) throws SQLException {
		String query = "SELECT * FROM user WHERE id=? AND password=?";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setString(1, id);
		preparedStatement.setString(2, password);
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next()) {
			return rs.getString("name");
		}
		return null;
	}
	
	public boolean insertMember(String id, String name, String password, String mail) throws SQLException {
		try {
			String query = "INSERT INTO user(id,email_id,name,password) VALUES (?,?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, mail);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, password);
			int n = preparedStatement.executeUpdate();
			System.out.println(n + " Member added");
			if (n > 0) {
				return true;
			}else {
				return false;
			}
		}
		catch(SQLIntegrityConstraintViolationException e) {
			System.out.println("E-mail already exists");
			return false;
		}
	}
	
	public int bookAvailability(String name) throws SQLException {
		String query = "SELECT * FROM book WHERE name=? AND available IS TRUE";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setString(1, name);
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next()) {
			return rs.getInt("id");
		}
		return -1;
	}
	
	public int issuedBookCount(String id) throws SQLException {
		String query = "SELECT COUNT(*) FROM issued_books WHERE member_id=?";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setString(1, id);
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}
	
	public List<Book> getBooks(String id) throws SQLException {
		String query = "SELECT b.id, b.name, b.author, b.price FROM issued_books ib, book b WHERE ib.member_id=? AND ib.book_id=b.id";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setString(1, id);
		List<Book> books = new ArrayList<>();
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			Book book = new Book();
			book.setId(rs.getInt(1));
			book.setName(rs.getString(2));
			book.setAuthor(rs.getString(3));
			book.setPrice(rs.getInt(4));
			books.add(book);
		}
		return books;
	}
	
	public List<Book> getBooksOnDue(String id) throws SQLException {
		String query = "SELECT b.id, b.name, b.author, b.price, ib.issue_date, ib.due_date, DATEDIFF(CURDATE(), ib.due_date) as date_diff FROM issued_books ib, book b WHERE ib.member_id=? AND ib.due_date < CURDATE() AND ib.book_id=b.id";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setString(1, id);
		List<Book> books = new ArrayList<>();
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			Book book = new Book();
			book.setId(rs.getInt(1));
			book.setName(rs.getString(2));
			book.setAuthor(rs.getString(3));
			book.setPrice(rs.getInt(4));
			book.setIssueDate(rs.getDate(5));
			book.setDueDate(rs.getDate(6));
			int dateDiff = rs.getInt(7);
			if (dateDiff < 0) {
				dateDiff=0;
			}
			book.setBillAmount(2 * dateDiff);
			books.add(book);
		}
		return books;
	}
	
	public int getBill(int bookId, String memberId) throws SQLException {
		String query = "SELECT DATEDIFF(CURDATE(),due_date) FROM issued_books WHERE book_id=? AND member_id=?";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setInt(1, bookId);
		preparedStatement.setString(2, memberId);
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next()) {
			int days = rs.getInt(1);
			if (days <= 0) {
				return 0;
			}
			return 2 * days;
		}else {
			return -1;
		}
	}
	
	public boolean issueBook(int bookId, String memberId) throws SQLException {
		conn.setAutoCommit(false);
		String query = "INSERT INTO issued_books(book_id, member_id, issue_date, due_date) values(?,?,CURDATE(), CURDATE()+5)";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setInt(1, bookId);
		preparedStatement.setString(2, memberId);
		if (preparedStatement.executeUpdate() == 1) {
			query = "UPDATE book set available = false WHERE id=?";
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, bookId);
			if (preparedStatement.executeUpdate() == 1) {
				conn.commit();
				conn.setAutoCommit(true);
				return true;
			}else {
				conn.rollback();
				conn.setAutoCommit(true);
				return false;
			}
		}else {
			conn.rollback();
			conn.setAutoCommit(true);
			return false;
		}
	}
	
	public boolean returnBook(int bookId, String memberId) throws SQLException {
		conn.setAutoCommit(false);
		String query = "INSERT INTO bill (book_id, member_id, issue_date, due_date, amount) (SELECT book_id, member_id, issue_date, due_date, IF(CURDATE() > due_date, 2 * DATEDIFF(CURDATE(),due_date),0) FROM issued_books WHERE book_id=? AND member_id=?)";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setInt(1, bookId);
		preparedStatement.setString(2, memberId);
		if (preparedStatement.executeUpdate() == 1) {
			query = "UPDATE book set available = true WHERE id=?";
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, bookId);
			if (preparedStatement.executeUpdate() == 1) {
				query = "DELETE FROM issued_books WHERE book_id=? AND member_id=?";
				preparedStatement = conn.prepareStatement(query);
				preparedStatement.setInt(1, bookId);
				preparedStatement.setString(2, memberId);
				if (preparedStatement.executeUpdate() == 1) {
					conn.commit();
					conn.setAutoCommit(true);
					return true;
				}
				else {
					conn.rollback();
					conn.setAutoCommit(true);
					return false;
				}
			}else {
				conn.rollback();
				conn.setAutoCommit(true);
				return false;
			}
		}else {
			conn.rollback();
			conn.setAutoCommit(true);
			return false;
		}
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
	
	public void deleteMember(String id) throws SQLException {
		String query = "DELETE FROM user WHERE id=?";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setString(1, id);
		if (preparedStatement.executeUpdate()==1) {
			System.out.println("Account deleted successfully");
		}else {
			System.out.println("Account not deleted");
		}
	}
}
