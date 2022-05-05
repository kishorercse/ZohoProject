package library.view;

import java.util.List;
import library.model.Book;

public class MemberView {
	
	public void showMemberOptions() {
		System.out.println("1.Request book");
		System.out.println("2.Return book");
		System.out.println("3.View books");
		System.out.println("4.View books on due");
		System.out.println("5.View Profile");
		System.out.println("6.Reset Password");
		System.out.println("7.Delete Account");
		System.out.println("8.Logout");
		System.out.println("Enter your choice");
	}
	
	public void showMemberDetails(String name, String mail) {
		System.out.println("Name: " +name);
		System.out.println("Mail: "+mail);
	}
	
	public void showBooks(List<Book> books) {
		System.out.println("ID\tNAME\tAUTHOR\tPRICE");
		for(Book b: books) {
			System.out.printf("%d %s %s %d\n",b.getId(),b.getName(),b.getAuthor(),b.getPrice());
		}
	}
	
	public void showBooksOnDue(List<Book> books) {
		System.out.println("ID\tNAME\tAUTHOR\tPRICE\tISSUE DATE\tDUE DATE\tBILL AMOUNT");
		for(Book book:books) {
			System.out.printf("%d %s %s %s %s %s %d\n", book.getId(), book.getName(), book.getAuthor(), book.getPrice(), book.getIssueDate(), book.getDueDate(), book.getBillAmount());
		}
	}
}
