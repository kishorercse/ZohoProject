package library.model;
import java.util.Scanner;
import java.sql.SQLException;
import java.util.*;
import library.dao.LibrarianDAO;


public class Librarian extends User implements LibrarianInt {
	private Scanner in;
	private LibrarianDAO dao;
	
	public Librarian() throws SQLException {
		dao = new LibrarianDAO();
		in = new Scanner(System.in);
	}
	
	@Override
	public boolean login() throws SQLException {
		System.out.print("Email : ");
		setMail(in.nextLine());
		if (!validateMail(getMail())) {
			System.out.println("INVALID EMAIL\nTRY AGAIN");
			return login();
		}
		
		System.out.print("Password : ");
		String password = in.nextLine();
		while (!validatePassword(password)) {
			System.out.println("INVALID PASSWORD\nTRY AGAIN\nEnter password:");
			password = in.nextLine();
		}
		setPassword(password);
		
		setId("lib#"+getMail());
		String name = dao.verifyLibrarian(getId(), getPassword());
		if (name == null){
			System.out.println("INCORRECT EMAIL OR PASSWORD\nTRY AGAIN");
			return login();
		}
		setName(name);
		return true;
	}
	
	@Override
	public void addBook() throws SQLException {
		Book book = new Book();
		System.out.print("Name: ");
		book.setName(in.nextLine());
		System.out.print("Author: ");
		book.setAuthor(in.nextLine());
		System.out.print("Price: ");
		book.setPrice(Integer.parseInt(in.nextLine()));
		dao.addBooks(book);
	}
	
	@Override
	public List<Book> getAllBooks() throws SQLException {
		return dao.getAllBooks();
	}
	
	@Override
	public List<Book> getAvailableBooks() throws SQLException {
		return dao.getAvailableBooks();
	}
	
	@Override
	public List<Book> getIssuedBooks() throws SQLException {
		return dao.getIssuedBooks();
	}
	
	@Override
	public List<Book> getBooksOnDue() throws SQLException {
		return dao.getBooksOnDue();
	}
	
	@Override
	public List<Book> getMemberBooks() throws SQLException {
		System.out.println("Enter a valid member email id:");
		String mail = in.nextLine();
		if (validateMail(mail)) {
			return dao.getMemberBooks("mem#"+mail);
		}else {
			return getMemberBooks();
		}
	}
	
	@Override
	public List<List<Object>> getBills() throws SQLException {
		return dao.getBills();
	}
	
	@Override
	public void deleteAccount() throws SQLException {
		dao.deleteUser(getId());
	}
	
	@Override
	public void deleteMember() throws SQLException {
		System.out.print("Enter email id of member to be deleted: ");
		String memberMail = in.nextLine();
		dao.deleteUser("mem#"+memberMail);
	}
	
	@Override
	public void resetPassword() throws SQLException {
		System.out.print("New Password: ");
		String newPassword = in.nextLine();
		while (!validatePassword(newPassword)) {
			System.out.println("ENTER A VALID PASSWORD");
			newPassword = in.nextLine();
		}
		if (dao.resetPassword(getId(), newPassword)) {
			setPassword(newPassword);
		}
	}
		
}