package library.view;

import java.util.List;

public class AdminView {
	
	public void showAdminOptions() {
		System.out.println("1.Add librarian");
		System.out.println("2.Show librarians");
		System.out.println("3.Delete librarians");
		System.out.println("4.Reset password");
		System.out.println("5.Logout");
	}
	
	public void showLibrarians(List<List<String>> librarians) {
		System.out.println("NAME\tMAIL");
		for(List<String> librarian:librarians) {
			System.out.println(librarian.get(0)+" "+librarian.get(1));
		}
	}
}
