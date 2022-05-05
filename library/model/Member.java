package library.model;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.List;
import library.dao.MemberDAO;

public class Member extends User implements MemberInt{
	private Scanner in;
	private MemberDAO dao;
	private int MAXBOOKISSUED = 3;
	
	public Member() throws SQLException {
		dao = new MemberDAO();
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
		
		setId("mem#"+getMail());
		String name = dao.verifyMember(getId(), getPassword());
		if (name == null){
			System.out.println("INCORRECT EMAIL OR PASSWORD\nTRY AGAIN");
			return login();
		}
		setName(name);
		return true;
	}
	
	@Override
	public boolean signup() throws SQLException {
		System.out.print("Username : ");
		setName(in.nextLine());
		System.out.print("Email id: ");
		String mail = in.nextLine();
		while(!validateMail(mail)) {
			System.out.println("INVALID Email\nENTER a valid email");
			mail = in.nextLine();
		}
		setMail(mail);
		setId("mem#"+getMail());
		System.out.print("Password : ");
		String password = in.nextLine();
		while(!validatePassword(password)) {
			System.out.println("INVALID PASSWORD\nENTER NEW PASSWORD");
			password = in.nextLine();
		}
		setPassword(password);
		if (dao.insertMember(getId(), getName(), getPassword(), getMail())) {
			return true;
		}
		return false;
	}
	
	@Override
	public void requestBook() throws SQLException {
		System.out.print("Enter book name: ");
		String name = in.nextLine();
		int bookId = dao.bookAvailability(name);
		if (bookId != -1) {
			if (dao.issuedBookCount(getId()) < MAXBOOKISSUED) {
				if (dao.issueBook(bookId, getId())) {
					System.out.println("Book issued");
				}else {
					System.out.println("Book not issued");
				}
			}else {
				System.out.println("Max issued book count reached");
			}
		}else {
			System.out.println("BOOK NOT AVAILABLE");
		}
	}
	
	@Override
	public List<Book> getBooks(String id) throws SQLException {
		return dao.getBooks(id);
	}
	
	@Override
	public List<Book> getBooksOnDue(String id) throws SQLException {
		return dao.getBooksOnDue(id);
	}
	
	@Override
	public void returnBook() throws SQLException {
		System.out.print("Enter id of book to be returned :");
		int bookId = in.nextInt();
		int bill = dao.getBill(bookId, getId());
		if (bill == -1) {
			System.out.println("INVALID BOOK ID");
		}
		else if (bill == 0) {
			if (dao.returnBook(bookId, getId())) {
				System.out.println("Book returned successfully");
			}
			else {
				System.out.println("Book not returned");
			}
		}else {
			System.out.println("1.Pay Bill Rs."+ bill);
			if (in.nextInt() == 1 && dao.returnBook(bookId, getId())) {
				System.out.println("Bill paid");
				System.out.println("Book returned successfully");
			}
			else {
				System.out.println("Book not returned");
			}
		}
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
	
	@Override
	public void deleteAccount() throws SQLException {
		dao.deleteMember(getId());
	}
	
	
}
