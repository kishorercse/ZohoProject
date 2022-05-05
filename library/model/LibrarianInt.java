package library.model;

import java.sql.SQLException;
import java.util.List;

public interface LibrarianInt {
	
	boolean login() throws SQLException;
	void addBook() throws SQLException;
	List<Book> getAllBooks() throws SQLException;
	List<Book> getAvailableBooks() throws SQLException;
	List<Book> getIssuedBooks() throws SQLException;
	List<Book> getBooksOnDue() throws SQLException;
	List<Book> getMemberBooks() throws SQLException;
	List<List<Object>> getBills() throws SQLException;
	void deleteAccount() throws SQLException;
	void deleteMember() throws SQLException;
	void resetPassword() throws SQLException;
	
}
