package library.model;

import java.sql.SQLException;
import java.util.List;

public interface AdminInt {
	
	boolean login() throws SQLException;
	List<List<String>> getLibrarians() throws SQLException;
	void addLibrarian() throws SQLException;
	void deleteLibrarian() throws SQLException;
	void resetPassword() throws SQLException;
	
}
