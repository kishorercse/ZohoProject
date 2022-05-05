package library.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import library.model.Book;
import library.model.Member;
import library.view.MemberView;

public class MemberController {
	private Member model;
	private MemberView view;
	private Scanner in = new Scanner(System.in);
	
	public MemberController(Member model, MemberView view) {
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
	
	
	public void login() throws SQLException {
		if (model.login()) {
			memberOptions();
		}
	}
	
	public void signup() throws SQLException {
		if (model.signup()) {
			memberOptions();
		}
	}
	
	public void memberOptions() throws SQLException {
		while(true) {
			view.showMemberOptions();
			int choice = Integer.parseInt(in.nextLine());
			switch(choice) {
				case 1:
					model.requestBook();
					break;
				case 2:
					model.returnBook();
					break;
				case 3:
					List<Book> books = model.getBooks(getId());
					view.showBooks(books);
					break;
				case 4:
					List<Book> booksOnDue = model.getBooksOnDue(getId());
					view.showBooksOnDue(booksOnDue);
					break;
				case 5:
					view.showMemberDetails(getName(), getMail());
					break;
				case 6:
					model.resetPassword();
					break;
				case 7:
					model.deleteAccount();
					break;	
			}
			if (choice == 7 || choice == 8) {
				break;
			}
		}
	}
}
