package library.view;
import java.util.List;
import library.model.Book;

public class LibrarianView {
	
	public void showLibrarianOptions() {
		System.out.println("1.Add book");
		System.out.println("2.View all books");
		System.out.println("3.View available books");
		System.out.println("4.View issued books");
		System.out.println("5.View books on due");
		System.out.println("6.View books of a member");
		System.out.println("7.View bills");
		System.out.println("8.Delete member");
		System.out.println("9.View Profile");
		System.out.println("10.Reset Password");
		System.out.println("11.Delete Account");
		System.out.println("12.Logout");
		System.out.println("Enter your choice");
	}
	
	public void showBooks(List<Book> books) {
		System.out.println("ID\tNAME\tAUTHOR\tPRICE\tSTATUS");
		for(Book book:books) {
			System.out.printf("%d %s %s %s %s\n", book.getId(), book.getName(), book.getAuthor(), book.getPrice(), book.getStatus());
		}
	}
	
	public void showAvailableBooks(List<Book> books) {
		System.out.println("ID\tNAME\tAUTHOR\tPRICE");
		for(Book book:books) {
			System.out.printf("%d %s %s %s\n", book.getId(), book.getName(), book.getAuthor(), book.getPrice());
		}
	}
	
	public void showIssuedBooks(List<Book> books) {
		System.out.println("MEMBER NAME\tID\tBOOK NAME\tAUTHOR\tPRICE\tISSUE_DATE\tDUE_DATE");
		for(Book book:books) {
			System.out.printf("%s %d %s %s %s %s %s\n",book.getMemberName(), book.getId(), book.getName(), book.getAuthor(), book.getPrice(), book.getIssueDate(), book.getDueDate());
		}
	}
	
	public void showIssuedBooks(List<Book> books, boolean onDue) {        // Run time polymorphism
		System.out.println("MEMBER NAME\tID\tBOOK NAME\tAUTHOR\tPRICE\tISSUE DATE\tDUE DATE\tBILL AMOUNT");
		for(Book book:books) {
			System.out.printf("%s %d %s %s %s %s %s\n",book.getMemberName(), book.getId(), book.getName(), book.getAuthor(), book.getPrice(), book.getIssueDate(), book.getDueDate(), book.getBillAmount());
		}
	}
	
	public void showMemberBooks(List<Book> books) {
		System.out.println("ID\tBOOK NAME\tAUTHOR\tPRICE\tISSUE DATE\tDUE DATE");
		for(Book book:books) {
			System.out.printf("%d %s %s %s %s %s\n", book.getId(), book.getName(), book.getAuthor(), book.getPrice(), book.getIssueDate(), book.getDueDate(), book.getBillAmount());
		}
	}
	public void showLibrarianDetails(String name, String mail) {
		System.out.println("Name: " +name);
		System.out.println("Mail: "+mail);
	}
	
	public void showBills(List<List<Object>> bills ) {
		System.out.println("ID\tBOOK ID\tMEMBER_ID\tISSUE DATE\tDUE DATE\tAMOUNT");
		for(List<Object> bill:bills) {
			System.out.printf("%d   %d   %s %s %s %d\n",bill.get(0),bill.get(1),bill.get(2),bill.get(3),bill.get(4),bill.get(5));
		}
	}
}