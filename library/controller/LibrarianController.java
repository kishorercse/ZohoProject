package library.controller;

import java.util.Scanner;
import java.util.List;
import library.model.Librarian;
import library.view.LibrarianView;
import library.model.Book;
import java.sql.SQLException;

public class LibrarianController {
	private Librarian model;
	private LibrarianView view;
	private Scanner in = new Scanner(System.in);
	
	public LibrarianController(Librarian model, LibrarianView view) {
		this.model=model;
		this.view=view;
	}
	
	public String getName() {
		return model.getName();
	}
	
	public String getId() {
		return model.getId();
	}
	
	public String getMail() {
		return model.getMail();
	}
	
	public void login() throws SQLException{
		if (model.login()) {
			librarianOptions();
		}
	}
	
	public void librarianOptions() throws SQLException {
		while (true) {
			view.showLibrarianOptions();
			int choice = Integer.parseInt(in.nextLine());
			switch(choice) {
				case 1:
					model.addBook();
					break;
				case 2:
					List<Book> books = model.getAllBooks();
					view.showBooks(books);
					break;
				case 3:
					List<Book> availableBooks = model.getAvailableBooks();
					view.showAvailableBooks(availableBooks);
					break;
				case 4:
					List<Book> issuedBooks = model.getIssuedBooks();
					view.showIssuedBooks(issuedBooks);
					break;
				case 5:
					List<Book> booksOnDue = model.getBooksOnDue();
					view.showIssuedBooks(booksOnDue, true);
					break;
				case 6:
					List<Book> memberBooks = model.getMemberBooks();
					view.showMemberBooks(memberBooks);
					break;
				case 7:
					List<List<Object>> bills = model.getBills();
					view.showBills(bills);
					break;
				case 8:
					model.deleteMember();
				case 9:
					view.showLibrarianDetails(getName(), getMail());
					break;
				case 10:
					model.resetPassword();
					break;
				case 11:
					model.deleteAccount();
					break;		
			}
			if (choice == 11 || choice == 12) {
				break;
			}
		}
	}
	
}