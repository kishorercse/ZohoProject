package library.model;

import java.sql.SQLException;
import java.util.List;

public interface MemberInt {
	
	boolean login() throws SQLException;
	boolean signup() throws SQLException;
	void requestBook() throws SQLException;
	List<Book> getBooks(String id) throws SQLException;
	List<Book> getBooksOnDue(String id) throws SQLException;
	void returnBook() throws SQLException;
	void deleteAccount() throws SQLException;
	void resetPassword() throws SQLException;
	
}