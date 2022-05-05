package library.controller;

import java.util.Scanner;
import java.util.List;
import library.model.Admin;
import library.view.AdminView;
import java.sql.SQLException;

public class AdminController {
	private Admin model;
	private AdminView view;
	private Scanner in = new Scanner(System.in);
	
	public AdminController(Admin model, AdminView view) {
		this.model = model;
		this.view = view;
	}
	
	public void login() throws SQLException{
		if (model.login()) {
			adminOptions();
		}
	}
	
	public void adminOptions() throws SQLException{
		while(true) {
			view.showAdminOptions();
			System.out.println("Enter your choice");
			int choice = Integer.parseInt(in.nextLine());
			switch(choice) {
				case 1:
					model.addLibrarian();
					break;
				case 2:
					List<List<String>> librarians = model.getLibrarians();
					view.showLibrarians(librarians);
					break;
				case 3:
					model.deleteLibrarian();
					break;
				case 4:
					model.resetPassword();
					break;	
			}
			if (choice == 5) {
				break;
			}
		}
	}
	
	
}
