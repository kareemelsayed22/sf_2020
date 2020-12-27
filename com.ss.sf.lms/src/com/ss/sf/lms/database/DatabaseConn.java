/**
 * 
 */
package com.ss.sf.lms.database;
import com.ss.sf.lms.dao.AdministratorDao;
import com.ss.sf.lms.dao.BorrowerDao;
import com.ss.sf.lms.dao.LibrarianDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author kareemelsayed
 * This class sets up the connection & displays first menu 
 */
public class DatabaseConn {
	private static Connection connection;
	private static Scanner scan;
	
	//public static void DatabaseConn() {
	public DatabaseConn() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "password");
			scan = new Scanner(System.in);
			mainMenu();
			scan.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
   //}
	//First menu to be displayed to the console
		public static void mainMenu() {
			System.out.println("*******************************************************");
			System.out.println("Welcome to the Library Management System. ");
			System.out.println("Which category of a user are you? ");
			System.out.println("1) Librarian");
			System.out.println("2) Administrator");
			System.out.println("3) Borrower");
			System.out.println("4) Exit this program");
			//scan object to read the user input 
			if (scan.hasNextInt()) {
				int answer = scan.nextInt();
				if (answer == 1) {
					new LibrarianDao(connection);
				}
				else if (answer == 2) {
					new AdministratorDao(connection);
				}
				else if (answer == 3) {
					new BorrowerDao(connection);
				}
				else if (answer == 4) {
					scan.close();
					return;
				}
				else {
					System.out.println("Invalid Input. Please try again.");
					mainMenu();
				}
			}
			else {
				System.out.println("Invalid Input. Please try again.");
				mainMenu();
			}
		}

}
