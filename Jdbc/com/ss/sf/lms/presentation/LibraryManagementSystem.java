/**
 * 
 */
package com.ss.sf.lms.presentation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.ss.sf.lms.service.AdministratorService;
import com.ss.sf.lms.service.LibrarianService;
import com.ss.sf.lms.service.BorrowerService;

/**
 * This is the presentation Unit.
 * Interact with the User through the Console with menu prompts.
 *
 */
public class LibraryManagementSystem {

	/**
	 * @param args
	 * Runs start() method that gives user type options, also catches errors thrown up from DAOs.
	 */
	public static void main(String[] args) {
		
		try {
			mainMenu();
		}catch (IOException io) {
			System.out.println("IOException occured.");
			io.printStackTrace();
		} catch (ClassNotFoundException cnf) {
			System.out.println("ClassNotFoundException occured.");
			cnf.printStackTrace();
		} catch (SQLException sq) {
			System.out.println("SQLException occured.");
			sq.printStackTrace();
		}

	}
	/*
	 * Prompt user for a choice. instantiates Service to show menus on console.
	 */
	
	public static void mainMenu() throws ClassNotFoundException, IOException, SQLException  {
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Welcome to the SS Library Management System. Which category of user are you?");
			System.out.println("1) Librarian");
			System.out.println("2) Borrower");
			System.out.println("3) Administrator");
			Integer c = sc.nextInt();
			switch(c) {
			case 1:
				LibrarianService librarian = new LibrarianService();
				librarian.libMenuOne();
				break;
			case 2:
				BorrowerService borrower = new BorrowerService();
				borrower.validate();
				break;
			case 3:
				AdministratorService admin = new AdministratorService();
				admin.adminMenu();
				break;
			
				
			}
		}
		
	}

}
