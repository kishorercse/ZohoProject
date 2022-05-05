package library.main;

import java.util.Scanner;
import library.model.*;
import library.view.*;
import library.controller.*;
import java.sql.SQLException;

public class Library {
	public static void main(String args[]) throws SQLException{
		Scanner in = new Scanner(System.in);
		while(true) {
			System.out.println("1.Admin Login");
			System.out.println("2.Librarian Login");
			System.out.println("3.Member Login");
			System.out.println("4.Member Signup");
			System.out.println("5.Exit");
			System.out.println("Enter your choice");
			int choice=Integer.parseInt(in.nextLine());
	
			switch(choice) {
				case 1:
					AdminController adminController = new AdminController(new Admin(), new AdminView());
					adminController.login();
					break;
				case 2:
					LibrarianController libController = new LibrarianController(new Librarian(), new LibrarianView());
					libController.login();
					break;
				case 3:
					MemberController memberController = new MemberController(new Member(), new MemberView());
					memberController.login();
					break;
				case 4:
					MemberController memberController2 = new MemberController(new Member(), new MemberView());
					memberController2.signup();
					break;
			}
			if (choice == 5) {
				System.out.println("Session has ended");
				break;
			}
		}
		in.close();
	}
}
