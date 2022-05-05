package library.dao;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import library.model.Book;

public class LibrarianDAO {
	private Connection conn;
	
	public LibrarianDAO() throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","BeConfident@21");
	}
	
	public String verifyLibrarian(String id, String password) throws SQLException {
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
	
	public void addBooks(Book book) throws SQLException {
		String query = "INSERT INTO book(name, author, price, available) VALUES (?, ?, ?, true)";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setString(1, book.getName());
		preparedStatement.setString(2, book.getAuthor());
		preparedStatement.setInt(3, book.getPrice());
		if (preparedStatement.executeUpdate() == 1) {
			System.out.println("1 Book Added");
		}
	}
	
	public List<Book> getAllBooks() throws SQLException {
		String query = "SELECT * FROM book";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(query);
		List<Book> books = new ArrayList<>();
		while(rs.next())
		{
			Book book = new Book();
			book.setId(rs.getInt("id"));
			book.setName(rs.getString("name"));
			book.setAuthor(rs.getString("author"));
			book.setPrice(rs.getInt("price"));
			book.setStatus(rs.getBoolean("available"));
			books.add(book);
		}
		return books;
	}
	
	public List<Book> getAvailableBooks() throws SQLException {
		String query = "SELECT * FROM book WHERE available IS TRUE";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(query);
		List<Book> books = new ArrayList<>();
		while(rs.next())
		{
			Book book = new Book();
			book.setId(rs.getInt("id"));
			book.setName(rs.getString("name"));
			book.setAuthor(rs.getString("author"));
			book.setPrice(rs.getInt("price"));
			books.add(book);
		}
		return books;
	}
	
	public List<Book> getIssuedBooks() throws SQLException {
		String query = "SELECT u.name as user, b.id, b.name, b.author, b.price, ib.issue_date, ib.due_date FROM book b, issued_books ib, user u WHERE b.id=ib.book_id AND ib.member_id=u.id";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(query);
		List<Book> books = new ArrayList<>();
		while(rs.next())
		{
			Book book = new Book();
			book.setMemberName("user");
			book.setId(rs.getInt("id"));
			book.setName(rs.getString("name"));
			book.setAuthor(rs.getString("author"));
			book.setPrice(rs.getInt("price"));
			book.setIssueDate(rs.getDate("issue_date"));
			book.setDueDate(rs.getDate("due_date"));
			books.add(book);
		}
		return books;
	}
	
	public List<List<Object>> getBills() throws SQLException {
		List<List<Object>> bills = new ArrayList<>();
		String query = "SELECT * from bill";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(query);
		while(rs.next()) {
			List<Object> bill = new ArrayList<>();
			bill.add(rs.getInt("id"));
			bill.add(rs.getInt("book_id"));
			bill.add(rs.getString("member_id"));
			bill.add(rs.getDate("issue_date"));
			bill.add(rs.getDate("due_date"));
			bill.add(rs.getInt("amount"));
			bills.add(bill);
		}
		return bills;
	}
	
	public List<Book> getBooksOnDue() throws SQLException {
		String query = "SELECT u.name as user, b.id, b.name, b.author, b.price, ib.issue_date, ib.due_date, DATEDIFF(CURDATE(), due_date) as date_diff FROM book b, issued_books ib, user u WHERE ib.due_date>ib.issue_date AND b.id=ib.book_id AND ib.member_id=u.id";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(query);
		List<Book> books = new ArrayList<>();
		while(rs.next()) {
			Book book = new Book();
			book.setMemberName(rs.getString("user"));
			book.setId(rs.getInt("id"));
			book.setName(rs.getString("name"));
			book.setAuthor(rs.getString("author"));
			book.setPrice(rs.getInt("price"));
			book.setIssueDate(rs.getDate("issue_date"));
			book.setDueDate(rs.getDate("due_date"));
			int dateDiff = rs.getInt("date_diff");
			if (dateDiff < 0) {
				dateDiff=0;
			}
			book.setBillAmount(2 * dateDiff);
			books.add(book);
		}
		return books;
	}
	
	public List<Book> getMemberBooks(String id) throws SQLException {
		String query = "SELECT b.id, b.name, b.author, b.price, ib.issue_date, ib.due_date FROM book b, issued_books ib, user u WHERE u.id=? AND ib.due_date>ib.issue_date AND b.id=ib.book_id AND ib.member_id=u.id";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setString(1, id);
		ResultSet rs = preparedStatement.executeQuery();
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setId(rs.getInt(1));
			book.setName(rs.getString(2));
			book.setAuthor(rs.getString(3));
			book.setPrice(rs.getInt(4));
			book.setIssueDate(rs.getDate(5));
			book.setDueDate(rs.getDate(6));
			books.add(book);
		}
		return books;
	}
	
	
	public void deleteUser(String id) throws SQLException {
		String query = "DELETE FROM user WHERE id=?";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setString(1, id);
		if (preparedStatement.executeUpdate()==1) {
			System.out.println("Account deleted successfully");
		}else {
			System.out.println("Account not deleted");
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
	
}
