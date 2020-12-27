/**
 * 
 */
package com.ss.sf.lms.presentation;



import com.ss.sf.lms.database.*;
/**
 * @author kareemelsayed
 * This is the presentation unit. Gets connection started with database server.
 */
public class LibraryManagement {

	private static DatabaseConn dc;

	/**
	 * @param args
	 * Variable objects to register and create the connection using DriverManager
	 */
	public static void main(String args[]) {
		setDc(new DatabaseConn());
	}

	public static DatabaseConn getDc() {
		return dc;
	}

	public static void setDc(DatabaseConn dc) {
		LibraryManagement.dc = dc;
	}
	
	
}
