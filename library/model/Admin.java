package library.model;
import java.util.Scanner;
import java.util.List;
import java.sql.SQLException;
import library.dao.AdminDAO;

public class Admin extends User implements AdminInt{
	private Scanner in;
	private AdminDAO dao;
	
	public Admin() throws SQLException {
		dao = new AdminDAO();
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
		setId("adm#"+getMail());
		if (!dao.verifyAdmin(getId(), getPassword())) {
			System.out.println("INCORRECT EMAIL OR PASSWORD\nTRY AGAIN");
			return login();
		}
		return true;
	}
	
	@Override
	public List<List<String>> getLibrarians() throws SQLException {
		return dao.getLibrarians();
	}
	
	@Override
	public void addLibrarian() throws SQLException {
		System.out.print("Username : ");
		String name = in.nextLine();
		System.out.print("Email id : ");
		String mail = in.nextLine();
		while(!validateMail(mail)) {
			System.out.println("INVALID Email\nENTER a valid email");
			mail = in.nextLine();
		}
		String id = "lib#"+mail;
		System.out.print("Password : ");
		String password = in.nextLine();
		while(!validatePassword(password)) {
			System.out.println("INVALID PASSWORD\nENTER NEW PASSWORD");
			password = in.nextLine();
		}
		dao.insertLibrarian(id, mail, name, password);
	}
	
	@Override
	public void deleteLibrarian() throws SQLException {
		System.out.print("Enter Email ID of librarian to be deleted: ");
		dao.deleteLibrarian("lib#"+in.nextLine());
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
