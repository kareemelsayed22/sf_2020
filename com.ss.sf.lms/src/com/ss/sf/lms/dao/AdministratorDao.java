/**
 * 
 */
package com.ss.sf.lms.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import com.ss.sf.lms.database.*;
/**
 * @author kareemelsayed
 * Prompt. CRUD using DAO with the mySql database.
 * Connection with database is successful. 
 * Build a connection. RUN any query on database tables.
 * 
 * Administrator has the most power in this system. 
 * Gets to perform multiple operations. 
 * Each CRUD Operation should be treated as a State object. Value at compile time 
 * Wont make a difference. Use complex queries in order to consistent results in all tables.
 * Begin with this. This class makes service smooth. 
 *  
 * 1. GET prompt -> 2. Access prompt data with dynamic Objects -> 3. perform SQL Queries on all objects.
 * Try to format data from string to integer? 
 * No need to get pubPhoneNumber or address.
 * 
 * 
 * Table => Object
 * Keys. PK => Pointer. Cell => Table Cell
 * ROW, COLUMN => Object
 * Node of a LinkedList as an Object: modifier -> class -> Node
 * Query is an Object
 * Write Query then Use it to work with all tables in the database
 * NO GENRE table???? CREATE genre_table????????
 * ResultSet of a query is an Object
 * Transactions? Display to user with bookReviewTransaction();
 * Number Format is unchecked exception gets thrown when Unable to format
 * data. 
 */
public class AdministratorDao {
	private Connection connection;
	private static PreparedStatement pst;
	private static ResultSet resultSet;
	private static State state;
	private static Scanner scan;
	
	//Keep the scanner open to read user input 
	//Initiate a connection at anyTime
	public AdministratorDao(Connection connection) {
		this.connection = connection;
		scan = new Scanner(System.in);
		adminMenu();
		scan.close();
	}
	
	//CRUD logic is changing behavior
	//Change each state performing a new CRUD Operation
	//Value stays fixed
	private enum State {
		ADD, UPDATE, DELETE;
	}
	//Display adminMenu
	private void adminMenu() {
		System.out.println("*******************************************************");
		System.out.println("Administrator Menu");
		System.out.println("1) Add/Update/Delete Books");
		System.out.println("2) Add/Update/Delete Library Branches");
		System.out.println("3) Add/Update/Delete Borrowers ");
		System.out.println("4) Update publishers");
		System.out.println("5) Over-ride Due Date for a Book Loan");
		System.out.println("6) Return to previous page.");
		String answer = scan.nextLine();
		if (answer.equals("1")) {
			bookManagement();
		}
		else if (answer.equals("2")) {
			libraryManagement();
		}
		else if (answer.equals("3")) {
			borrowerManagement();
		}
		else if (answer.equals("4")) {
			publisherManagement();
		}
		else if (answer.equals("5")) {
			bookLoansManagement();
		}
		else if (answer.equals("6")) {
			DatabaseConn.mainMenu();
		}
		else {
			System.out.println("Invalid Input. Please try again.");
			adminMenu();
		}
	}
	//bookManager menu
	private void bookManagement() {
		System.out.println("*******************************************************");
		System.out.println("");
		System.out.println("1) Add a book");
		System.out.println("2) Update a book");
		System.out.println("3) Delete a book");
		System.out.println("4) Return to previous page.");
		String answer = scan.nextLine();
		if (answer.equals("1")) {
			state = State.ADD;
			addBookPrompt();
		}
		else if (answer.equals("2")) {
			state = State.UPDATE;
			updateDeleteBookPrompt();
		}
		else if (answer.equals("3")) {
			state = State.DELETE;
			updateDeleteBookPrompt();
		}
		else if (answer.equals("4")) {
			adminMenu();
		}
		else {
			System.out.println("Invalid Input. Please try again.");
			bookManagement();
		}		
	}
	 //CRUD prompts on Objects --------------------------
	//Execute a query to show all books in the database then prompt to update or delete
	//prompt when updating and deleting a book. Object which has a State?
	private void updateDeleteBookPrompt() {
		System.out.println("*******************************************************");
		if (state == State.UPDATE) {
			System.out.println("Choose the book to update. ");
		}
		else {
			System.out.println("Choose the book to delete. ");
		}
		try {
			List<String> bookArray = new LinkedList<String>();
			String selectQuery = "SELECT * FROM tbl_book";
			pst = connection.prepareStatement(selectQuery);
			resultSet = pst.executeQuery();
			int i = 1;
			while (resultSet.next()) {
				System.out.println(i + ") " + resultSet.getString("title"));
				bookArray.add(resultSet.getString("title"));
				i++;
			}
			System.out.println(i + ") Return to previous page.");
			String line = scan.nextLine();
			if (isInteger(line)) {
				int answer = Integer.parseInt(line);
				if (answer > 0 && answer < i) {
					String title = bookArray.get(answer - 1);
					int bookId = 0;
					String selectBookIdQuery = "SELECT * FROM tbl_book WHERE title=?";
					pst = connection.prepareStatement(selectBookIdQuery);
					pst.setString(1, title);
					resultSet = pst.executeQuery();
					if (resultSet.next()) {
						bookId = resultSet.getInt("bookId");
					}
					if (state == State.UPDATE) {
						updatePrompt(bookId, title);
					}
					else {
						deletePrompt(bookId, title);
					}
				}
				else if (answer == i) {
					bookManagement();
				}
				else {
					System.out.println("Invalid Input. Please try again.");
					updateDeleteBookPrompt();
				}
			}
			else {
				System.out.println("Invalid Input. Please try again.");
				updateDeleteBookPrompt();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//getAuthorName from the tbl_book having bookId and title.
	//getGenre? no genre_tbl in the schema. Do it just in case?
	//updates table columns while removing the old ones. Display an update prompt?
	private void updatePrompt(int bookId, String title) {
		System.out.println("*******************************************************");
		String authorName = getAuthorName(bookId);
		String pubName = getPubName(bookId);
		System.out.println("Choose the information would you like to update.");
		System.out.println("1) Book title: " + title);
		System.out.println("2) Author: " + authorName);
		System.out.println("3) Publisher: " + pubName);
		System.out.println("4) Return to previous page.");
		String line = scan.nextLine();
		if (isInteger(line)) {
			int answer = Integer.parseInt(line);
			if (answer == 1) {
				updateBookTitle(bookId);
			}
			else if (answer == 2) {
				updateBookAuthor(bookId);
				deleteAuthor(authorName);
			}
			else if (answer == 3) {
				updateBookPublisher(bookId);
				deletePub(pubName);
			}

			else if (answer == 4) {
				updateDeleteBookPrompt();
			}
			else {
				System.out.println("Invalid Input. Please try again.");
				updatePrompt(bookId, title);
			}
		}
		else {
			System.out.println("Invalid Input. Please try again.");
			updatePrompt(bookId, title);
		}
	}
	
	//Start CRUD Operations  with DAO 
	//updates bookTitle with bookId. Checks new input. update follows constraints?
	private void updateBookTitle(int bookId) {
		System.out.println("Enter the new title.");
		String bookName = scan.nextLine();
		if (bookName.length() > 45) {
			System.out.println("Invalid input: Cannot exceed 45 characters.");
			updateBookTitle(bookId);
		}
		if (bookName.length() == 0) {
			System.out.println("Invalid input: Cannot be empty.");
			updateBookTitle(bookId);
		}
		else {
			if (getBookId(bookName) == 0) {
				updateTitle(bookId, bookName);
				updatePrompt(bookId, getBookTitle(bookId));
			}
			else {
				System.out.println("Invalid input: Duplicated title.");
				updateBookTitle(bookId);
			}
		}
	}
	
	//Every time use method create a new author element in LinkedList as a String Object
	//update bookAuthor with bookId
	private void updateBookAuthor(int bookId) {
		System.out.println("*******************************************************");
		System.out.println("Choose a new author.");
		
		String authorName = listAuthors();
		//updateAuthorName then prompt?
		updateAuthorName(bookId, authorName);
		updatePrompt(bookId, getBookTitle(bookId));
	}
	
	//listAuthors as a String Object
	//Query all authors from table_author & store this info in a LinkedList
	//prepareStmt for security
	//listAuthors, gets authorName, addAuthor(authorName)
	private String listAuthors() {
		String selectQuery = "SELECT * FROM tbl_author";
		List<String> authorList = new LinkedList<String>();
		int i = 1;
		try {
			pst = connection.prepareStatement(selectQuery);
			resultSet = pst.executeQuery();
			while (resultSet.next()) {
				authorList.add(resultSet.getString("authorName"));
				System.out.println(i + ") " + resultSet.getString("authorName")); 
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(i + ") Add a new author.");
		String line = scan.nextLine();
		//check if isInteger(userInput)
		//hit two birds with oneStone?
		if (isInteger(line)) {
			int answer = Integer.parseInt(line);
			//answer == pointer of to a cell in the List
			//checks iff answer is positive or less than i = 1
			//userInput is data. Add more info recursively 
			//head -> null or head.next -> head.next.next
			if (answer > 0 && answer < i) {
				return authorList.get(answer - 1);
			}
			else if (answer == i) {
				System.out.println("Enter the new author name.");
				String authorName = scan.nextLine();
				addAuthor(authorName);
				return authorName;
			}
			else {
				System.out.println("Invalid Input. Please try again.");
				return listAuthors();
			}
		}
		else {
			System.out.println("Invalid Input. Please try again.");
			return listAuthors();
		}
	}
	//Get all authors from tableAuthor join the result of the query with tble_book_authots on PK
	//Take authorName and delete it from table author. Remove using an alias table? MySQL supports that ON??
	private void deleteAuthor(String authorName) {
		String selectQuery = "SELECT * FROM tbl_author JOIN tbl_book_authors ON tbl_author.authorId=tbl_book_authors.authorId WHERE authorName=?";
		try {
			pst = connection.prepareStatement(selectQuery);
			pst.setString(1, authorName);
			resultSet = pst.executeQuery();
			if (!resultSet.next()) {
				String deleteQuery = "DELETE FROM tbl_author WHERE authorName=?";
				pst = connection.prepareStatement(deleteQuery);
				pst.setString(1, authorName);
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//make a pubName variable to get the listPublishers()?
	//Choose a new publisher with a new pubId getPubId(pubName)
	//updatePrompt(bookId) get from? 
	//updatePub()? - a void function that takes pubId which it gets from?
	//updatePrompt(bookId, getsBookTitle(bookId)?
	//Create a new pub with a new name. Get a pubId, updatePub on bookId and new pubName
	private void updateBookPublisher(int bookId) {
		System.out.println("*******************************************************");
		System.out.println("Choose a new publisher.");
		String pubName = listPublishers();
		int pubId = getPubId(pubName);
		updatePub(pubId, bookId);
		updatePrompt(bookId, getBookTitle(bookId));
	}
	//listPublishers as a String Object
	private String listPublishers() {
		String selectQuery = "SELECT * FROM tbl_publisher";
		int i = 1;
		List<String> pubArray = new LinkedList<String>();
		try {
			pst = connection.prepareStatement(selectQuery);
			resultSet = pst.executeQuery();
			while (resultSet.next()) {
				pubArray.add(resultSet.getString("publisherName"));
				System.out.println(i + ") " + resultSet.getString("publisherName"));
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (state == State.UPDATE) {
			System.out.println(i + ") Add a new publisher.");
		}
		//COMMENT FOR ^^^^^^^^^^^^^^GET METHOD^^^^^^^^^^^ 
        //Get userInput isInteger(userInput) -> parseInt(UInput) -> CellPointer -> NULL
		String line = scan.nextLine();
		if (isInteger(line)) {
			int answer = Integer.parseInt(line);
			
			
			if (answer > 0 && answer < i) {
				return pubArray.get(answer - 1);
			}
			else if (answer == i && state == State.UPDATE) {
				System.out.println("Enter the new publisher name.");
				String pubName = scan.nextLine();
				System.out.println("Enter the publisher's address.");
				String pubAddress = scan.nextLine();
				System.out.println("Enter the publisher's phone number.");
				String pubPhone = scan.nextLine();
				addPub(pubName, pubAddress, pubPhone);
				return pubName;
			}
			else {
				System.out.println("Invalid Input. Please try again.");
				return listPublishers();
			}
		}
		else {
			System.out.println("Invalid Input. Please try again.");
			return listPublishers();
		}
	}
	//Check deletePub?  delete pub properly on delete book? Check deletePub?
	private void deletePub(String pubName) {
		int pubId = getPubId(pubName);
		String selectQuery = "SELECT * FROM tbl_book WHERE pubId=?";
		try {
			pst = connection.prepareStatement(selectQuery);
			pst.setInt(1, pubId);
			resultSet = pst.executeQuery();
			if (!resultSet.next()) {
				String deleteQuery = "DELETE FROM tbl_publisher WHERE publisherId=?";
				pst = connection.prepareStatement(deleteQuery);
				pst.setInt(1, pubId);
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//deletePrompt for book with bookId and title
	private void deletePrompt(int bookId, String title) {
		System.out.println("*******************************************************");
		System.out.println("Deleting " + title + ", are you sure? (Y/N)");
		String answer = scan.nextLine();
		if (answer.toLowerCase().equals("y")) {
			deleteBook(bookId, title);
		}
		else if (answer.toLowerCase().equals("n")) {
			bookManagement();
		}
		else {
			System.out.println("Invalid Input. Please try again.");
			deletePrompt(bookId, title);
		}
	}
	//delete book with bookId and title
	private void deleteBook(int bookId, String title) {
		try {
			String selectQuery = "SELECT * FROM tbl_book_loans WHERE bookId=? AND dateIn IS NULL";
			pst = connection.prepareStatement(selectQuery);
			pst.setInt(1, bookId);
			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				System.out.println("This book has copies loaned out, you cannot delete it.");
				adminMenu();
			}
			else {
				String authorName = getAuthorName(bookId);
				String pubName = getPubName(bookId);
				
				String deleteBookQuery = "DELETE FROM tbl_book WHERE bookId=?";
				pst = connection.prepareStatement(deleteBookQuery);
				pst.setInt(1, bookId);
				pst.executeUpdate();
				
				deleteAuthor(authorName);
				deletePub(pubName);
				
				System.out.println(title + " is deleted from the system.");
				adminMenu();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//LibraryManagement prompt User . REPEAT method Call
	private void libraryManagement() {
		System.out.println("*******************************************************");
		System.out.println("Library Management");
		System.out.println("1) Add a branch");
		System.out.println("2) Update a branch");
		System.out.println("3) Delete a branch");
		System.out.println("4) Return to previous page.");
		String answer = scan.nextLine();
		if (answer.equals("1")) {
			addBranch();
		}
		else if (answer.equals("2")) {
			state = State.UPDATE;
			System.out.println("Choose a branch to update.");
			listBranch();
		}
		else if (answer.equals("3")) {
			state = State.DELETE;
			System.out.println("Choose a branch to delete.");
			listBranch();
		}
		else if (answer.equals("4")) {
			adminMenu();
		}
		else {
			System.out.println("Invalid Input. Please try again.");
			libraryManagement();
		}
	}
	//prompt user to add a new Branch in the library_table
	//add library Branch to the database from userInput
	
	//Statement is just a Query that runs when JDBC uses DRIVER to communicate with dataBase
	//RUN Queries. Get branchName from user. Check if it exists??
	//resultSet = results of the scanner reading the branchByName if it exists
	
	//RUN Query if it executes then branchName exists
	//Run another Query to INSERT new entry to table
	
	 
	private void addBranch() {
		System.out.println("*******************************************************");
		System.out.println("Enter the branch's name.");
		String branchName = scan.nextLine();
		String selectQuery = "SELECT * FROM tbl_library_branch WHERE branchName=?";
		try {
			pst = connection.prepareStatement(selectQuery);
			pst.setString(1, branchName);
			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				System.out.println("Branch name already exist, choose another name.");
				addBranch();
			}
			else {
				System.out.println("Enter the branch's address.");
				String branchAddress = scan.nextLine();
				String insertQuery = "INSERT INTO tbl_library_branch (branchName, branchAddress) VALUES(?,?)";
				pst = connection.prepareStatement(insertQuery);
				pst.setString(1, branchName);
				pst.setString(2, branchAddress);
				pst.executeUpdate();
				List<Integer> bookIdArray = new LinkedList<Integer>();
				String selectBookIdQuery = "SELECT * FROM tbl_book";
				pst = connection.prepareStatement(selectBookIdQuery);
				resultSet = pst.executeQuery();
				while (resultSet.next()) {
					bookIdArray.add(resultSet.getInt("bookId"));
				}
				int branchId = getBranchId(branchName);
				String insertCopiesQuery = "INSERT INTO tbl_book_copies VALUES(?,?,0)";
				for (int i = 0; i < bookIdArray.size(); i++) {
					pst = connection.prepareStatement(insertCopiesQuery);
					pst.setInt(1, bookIdArray.get(i));
					pst.setInt(2, branchId);
					pst.executeUpdate();
				}
				System.out.println("The branch " + branchName + " is successfully added into the system.");
				libraryManagement();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//get branchId with branchName from prompt
	private int getBranchId(String branchName) {
		String selectQuery = "SELECT * FROM tbl_library_branch WHERE branchName=?";
		try {
			pst = connection.prepareStatement(selectQuery);
			pst.setString(1, branchName);
			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("branchId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	//LinkedList to store data of LibraryBranches
	//get all info from table
	private void listBranch() {
		String selectQuery = "SELECT * FROM tbl_library_branch";
		int i = 1;
		List<String> branchArray = new LinkedList<String>();
		try {
			pst = connection.prepareStatement(selectQuery);
			resultSet = pst.executeQuery();
			while (resultSet.next()) {
				System.out.println(i + ") " + resultSet.getString("branchName"));
				branchArray.add(resultSet.getString("branchName"));
				i++;
			}
			System.out.println(i + ") Return to previous page.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String line = scan.nextLine();
		if (isInteger(line)) {
			int answer = Integer.parseInt(line);
			if (answer > 0 && answer < i) {
				int branchId = getBranchId(branchArray.get(answer - 1));
				if (state == State.UPDATE) {
					updateBranch(branchId);
				}
				else {
					deleteBranch(branchId);
				}
			}
			else if (answer == i) {
				libraryManagement();
			}
			else {
				System.out.println("Invalid Input. Please try again.");
				listBranch();
			}
		}
		else {
			System.out.println("Invalid Input. Please try again.");
			listBranch();
		}
	}
	// delete branch with branchId
	private void deleteBranch(int branchId) {
		System.out.println("*******************************************************");
		String selectQuery = "SELECT * FROM tbl_book_loans WHERE branchId=? AND dateIn IS NULL";
		try {
			pst = connection.prepareStatement(selectQuery);
			pst.setInt(1, branchId);
			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				System.out.println("This branch has books loaned out, you cannot delete it. ");
				libraryManagement();
			}
			else {
				String branchName = getBranchName(branchId);
				System.out.println("Deleting the branch: " + branchName);
				System.out.println("Are you sure? (Y/N)");
				String answer = scan.nextLine();
				if (answer.toLowerCase().equals("y")) {
					deleteBookLoans(branchId);
					deleteBookCopies(branchId);
					String deleteQuery = "DELETE FROM tbl_library_branch WHERE branchId=?";
					pst= connection.prepareStatement(deleteQuery);
					pst.setInt(1, branchId);
					pst.executeUpdate();
					System.out.println("The branch " + branchName + " is now deleted from the system.");
					libraryManagement();
				}
				else if (answer.toLowerCase().equals("n")) {
					libraryManagement();
				}
				else {
					System.out.println("Invalid Input. Please try again.");
					deleteBranch(branchId);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//delete book with bookId from bookLoans Table
	private void deleteBookLoans(int branchId) {
		String deleteQuery = "DELETE FROM tbl_book_loans WHERE branchId=?";
		try {
			pst = connection.prepareStatement(deleteQuery);
			pst.setInt(1, branchId);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//delete book with bookId from bookLoans Table
	private void deleteBookCopies(int branchId) {
		String deleteQuery = "DELETE FROM tbl_book_copies WHERE branchId=?";
		try {
			pst = connection.prepareStatement(deleteQuery);
			pst.setInt(1, branchId);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//update library branch on PK
	private void updateBranch(int branchId) {
		System.out.println("*******************************************************");
		System.out.println("1) Update branch name. Name: " + getBranchName(branchId));
		System.out.println("2) Update branch address. Address: " + getBranchAddress(branchId));
		System.out.println("3) Return to previous page.");
		String answer = scan.nextLine();
		if (answer.equals("1")) {
			System.out.println("Enter a new branch name.");
			System.out.println("Enter 'quit' to return to previous.");
			updateBranchName(branchId);
		}
		else if (answer.equals("2")) {
			System.out.println("Enter a new branch address.");
			System.out.println("Enter 'quit' to return to previous.");
			udpateBranchAddress(branchId);
		}
		else if (answer.equals("3")) {
			libraryManagement();
		}
		else {
			System.out.println("Invalid Input. Please try again.");
			updateBranch(branchId);
		}
	}
	//getBranchName using branchId
	private String getBranchName(int branchId) {
		String selectQuery = "SELECT * FROM tbl_library_branch WHERE branchId=?";
		try {
			pst = connection.prepareStatement(selectQuery);
			pst.setInt(1, branchId);
			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("branchName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	//gets branchAddress with branchId
	private String getBranchAddress(int branchId) {
		String selectQuery = "SELECT * FROM tbl_library_branch WHERE branchId=?";
		try {
			pst = connection.prepareStatement(selectQuery);
			pst.setInt(1, branchId);
			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("branchAddress");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	//updates branchName with branchId
	//prompt for LibraryMenu for UserInput
    private void updateBranchName(int branchId) {
		String branchName = scan.nextLine();
		if (branchName.toLowerCase().equals("quit")) {
			libraryManagement();
		}
		else {
			String selectQuery = "SELECT * FROM tbl_library_branch WHERE branchName=?";
			try {
				pst = connection.prepareStatement(selectQuery);
				pst.setString(1, branchName);
				resultSet = pst.executeQuery();
				if (resultSet.next()) {
					System.out.println("This branch name already exists. Choose another one.");
					updateBranchName(branchId);
				}
				else {
					String updateQuery = "UPDATE tbl_library_branch SET branchName=? WHERE branchId=?";
					pst = connection.prepareStatement(updateQuery);
					pst.setString(1, branchName);
					pst.setInt(2, branchId);
					pst.executeUpdate();
					System.out.println("Update complete.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	//updates branchAddress with branchId
	private void udpateBranchAddress(int branchId) {
		String branchAddress = scan.nextLine();
		if (branchAddress.toLowerCase().equals("quit")) {
			libraryManagement();
		}
		else {
			try {
				String updateQuery = "UPDATE tbl_library_branch SET branchAddress=? WHERE branchId=?";
				pst = connection.prepareStatement(updateQuery);
				pst.setString(1, branchAddress);
				pst.setInt(2, branchId);
				pst.executeUpdate();
				System.out.println("Update complete.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	//menu to manage publisher
	private void publisherManagement() {
		System.out.println("*******************************************************");
		System.out.println("Choose a publisher to update.");
		state = State.UPDATE;
		String pubName = listPublishers();
		int pubId = getPubId(pubName);
		updatePubPrompt(pubId);
	}
	//prompt to updatePublisher(pubId)
	private void updatePubPrompt(int pubId) {
		System.out.println("*******************************************************");
		System.out.println("1) Update publisher name.");
		System.out.println("2) Update publisher address.");
		System.out.println("3) Update publisher phone number.");
		System.out.println("4) Return to previous page.");
		String answer = scan.nextLine();
		if (answer.equals("1")) {
			updatePubName(pubId);
		}
		else if (answer.equals("2")) {
			
		}
		else if (answer.equals("3")) {
			
		}
		else if (answer.equals("4")) {
			publisherManagement();
		}
		else {
			System.out.println("Invalid Input. Please try again.");
			updatePubPrompt(pubId);
		}
	}
	//updates pubName with pubId
	private void updatePubName(int pubId) {
		System.out.println("*******************************************************");
		System.out.println("Enter a new publisher name.");
		System.out.println("Enter 'quit' to return to previous page.");
		String pubName = scan.nextLine();
		String selectQuery = "SELECT * FROM tbl_publisher WHERE pubName=?";
		try {
			pst = connection.prepareStatement(selectQuery);
			pst.setString(1, pubName);
			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				System.out.println("Invalid Input. Please try again.");
				updatePubName(pubId);
			}
			else {
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//Borrower menu to prompt for UserInput
	private void borrowerManagement() {
		System.out.println("*******************************************************");
		System.out.println("Borrower Management");
		System.out.println("1) Add a borrower");
		System.out.println("2) Update a borrower");
		System.out.println("3) Delete a borrower");
		System.out.println("4) Return to previous page.");
		if (scan.hasNextInt()) {
			int answer = scan.nextInt();
			if (answer == 1) {
				addBorrower();
			}
			else if (answer == 2) {
				updateBorrowerPrompt();
			}
			else if (answer == 3) {
				deleteBorrowerPrompt();
			}
			else if (answer == 4) {
				adminMenu();
			}
			else {
				System.out.println("Invalid Input. Please try again.");
				borrowerManagement();
			}
		}
		else {
			System.out.println("Invalid Input. Please try again.");
			borrowerManagement();
		}
	}
	
	//adds a borrower using UserInput turns it into Object. work with object
	private void addBorrower() {
		System.out.println("*******************************************************");
		System.out.println("Enter the borrower's name.");
		System.out.println("Enter quit at any prompt to exit.");
		try {
			String name = scan.nextLine();
			if (name.toLowerCase().equals("quit")) {
				borrowerManagement();
				scan.close();
				return;
			}
			String selectQuery = "SELECT * FROM tbl_borrower WHERE name=?";
			pst = connection.prepareStatement(selectQuery);
			pst.setString(1, name);
			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				System.out.println("Borrower with this name already exist, enter a new name.");
				addBorrower();
			}
			System.out.println("Enter the borrower's address.");
			String address = scan.nextLine();
			if (address.toLowerCase().equals("quit")) {
				borrowerManagement();
				scan.close();
				return;
			}
			System.out.println("Enter the borrower's phone number.");
			String phone = scan.nextLine();
			if (phone.toLowerCase().equals("quit")) {
				borrowerManagement();
				scan.close();
				return;
			}
			String insertQuery = "INSERT INTO tbl_borrower (name, address, phone) VALUES (?,?,?)";
			pst = connection.prepareStatement(insertQuery);
			pst.setString(1,name);
			pst.setString(2, address);
			pst.setString(3, phone);
			pst.executeUpdate();
			System.out.println("New borrower added.");
			borrowerManagement();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	//Prompt to update a borrower in the tbl_borrower
	private void updateBorrowerPrompt() {
		System.out.println("*******************************************************");
		System.out.println("Choose a borrower to update.");
		String selectQuery = "SELECT * FROM tbl_borrower";
		try {
			pst = connection.prepareStatement(selectQuery);
			resultSet = pst.executeQuery();
			List<Integer> cardArray = new LinkedList<Integer>();
			int i = 1;
			while (resultSet.next()) {
				System.out.println(i + ") " + resultSet.getString("name"));
				cardArray.add(resultSet.getInt("cardNo"));
				i++;
			}
			System.out.println(i + ") Return to previous page.");
			Scanner scan = new Scanner(System.in);
			if (scan.hasNextInt()) {
				int answer = scan.nextInt();
				if (answer > 0 && answer < i) {
					int cardNo = cardArray.get(answer - 1);
					updateBorrower(cardNo);
				}
				else if (answer == i) {
					borrowerManagement();
				}
				else {
					System.out.println("Invalid Input. Please try again.");
					updateBorrowerPrompt();
				}
			}
			else {
				System.out.println("Invalid Input. Please try again.");
				updateBorrowerPrompt();
			}
			scan.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//updates Borrower using cardNumber
	private void updateBorrower(int cardNo) {
		System.out.println("*******************************************************");
		System.out.println("Which information do ou want to update.");
		System.out.println("1) Name: " + getBorrowerName(cardNo));
		System.out.println("2) Address: " + getBorrowerAddress(cardNo));
		System.out.println("3) Phone: " + getBorrowerPhone(cardNo));
		System.out.println("4) Return to previous page.");
		if (scan.hasNextInt()) {
			int answer = scan.nextInt();
			if (answer == 1) {
				updateName(cardNo);
			}
			else if (answer == 2) {
				updateAddress(cardNo);
			}
			else if (answer == 3) {
				updatePhone(cardNo);
			}
			else if (answer == 4) {
				borrowerManagement();
			}
			else {
				System.out.println("Invalid Input. Please try again.");
				updateBorrower(cardNo);
			}
		}
		else {
			System.out.println("Invalid Input. Please try again.");
			updateBorrower(cardNo);
		}
	}
	//updates borrowerName with cardNumber
	private void updateName(int cardNo) {
		System.out.println("*******************************************************");
		System.out.println("Enter a new name.");
		String name = scan.nextLine();
		String selectQuery = "SELECT * FROM tbl_borrower WHERE name=?";
		try {
			pst = connection.prepareStatement(selectQuery);
			pst.setString(1, name);
			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				System.out.println("A borrower with this name already exist. Please choose another name.");
				updateBorrower(cardNo);
			}
			String updateQuery = "UPDATE tbl_borrower SET name=? WHERE cardNo=?";
			pst = connection.prepareStatement(updateQuery);
			pst.setString(1, name);
			pst.setInt(2, cardNo);
			pst.executeUpdate();
			System.out.println("Update complete.");
			updateBorrower(cardNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//updates Borrower address using cardNumber{PK}
	private void updateAddress(int cardNo) {
		System.out.println("*******************************************************");
		System.out.println("Enter a new address.");
		System.out.println("Enter quit to return to previous page.");
		String address = scan.nextLine();
		if (address.toLowerCase().equals("quit")) {
			updateBorrower(cardNo);
		}
		try {
			String updateQuery = "UPDATE tbl_borrower SET address=? WHERE cardNo=?";
			pst = connection.prepareStatement(updateQuery);
			pst.setString(1, address);
			pst.setInt(2, cardNo);
			pst.executeUpdate();
			System.out.println("Update complete.");
			updateBorrower(cardNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//updates phone of Borrower in Borrower table with CardNumber
	private void updatePhone(int cardNo) {
		System.out.println("*******************************************************");
		System.out.println("Enter a new phone number.");
		System.out.println("Enter quit to return to previous page.");
		String phone = scan.nextLine();
		if (phone.toLowerCase().equals("quit")) {
			updateBorrower(cardNo);
		}
		try {
			String updateQuery = "UPDATE tbl_borrower SET phone=? WHERE cardNo=?";
			pst = connection.prepareStatement(updateQuery);
			pst.setString(1, phone);
			pst.setInt(2, cardNo);
			pst.executeUpdate();
			System.out.println("Update complete.");
			updateBorrower(cardNo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//Prompt to delete a Borrower from Borrower table
	private void deleteBorrowerPrompt() {
		System.out.println("*******************************************************");
		System.out.println("Choose a borrower to delete.");
		String selectQuery = "SELECT * FROM tbl_borrower";
		try {
			pst = connection.prepareStatement(selectQuery);
			resultSet = pst.executeQuery();
			List<Integer> cardArray = new LinkedList<Integer>();
			int i = 1;
			while (resultSet.next()) {
				System.out.println(i + ") " + resultSet.getString("name"));
				cardArray.add(resultSet.getInt("cardNo"));
				i++;
			}
			System.out.println(i + ") Return to previous page.");
			if (scan.hasNextInt()) {
				int answer = scan.nextInt();
				if (answer > 0 && answer < i) {
					int cardNo = cardArray.get(answer - 1);
					deleteBorrower(cardNo);
				}
				else if (answer == i) {
					borrowerManagement();
				}
				else {
					System.out.println("Invalid Input. Please try again.");
					deleteBorrowerPrompt();
				}
			}
			else {
				System.out.println("Invalid Input. Please try again.");
				deleteBorrowerPrompt();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//delete Borrower with cardNo
	private void deleteBorrower(int cardNo) {
		System.out.println("*******************************************************");
		System.out.println("Deleting the borrower " + getBorrowerName(cardNo));
		String selectQuery = "SELECT * FROM tbl_book_loans WHERE cardNo=? AND dateIn IS NULL";
		try {
			pst = connection.prepareStatement(selectQuery);
			pst.setInt(1, cardNo);
			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				System.out.println("This borrower has books loaned out, you cannot delete this borrower.");
				adminMenu();
				return;
			}
			System.out.println("Are you sure? (Y/N)");
			String answer = scan.nextLine();
			if (answer.toLowerCase().equals("y")) {
				
				String deleteBorrowerLoanQuery = "DELETE FROM tbl_book_loans WHERE cardNo=?";
				pst = connection.prepareStatement(deleteBorrowerLoanQuery);
				pst.setInt(1, cardNo);
				pst.executeUpdate();
				String deleteBorrowerQuery = "DELETE FROM tbl_borrower WHERE cardNo=?";
				pst = connection.prepareStatement(deleteBorrowerQuery);
				pst.setInt(1, cardNo);
				pst.executeUpdate();
				System.out.println("The borrower is deleted from the system.");
				borrowerManagement();
			}
			else if (answer.toLowerCase().equals("n")) {
				borrowerManagement();
			}
			else {
				System.out.println("Invalid Input. Please try again.");
				deleteBorrower(cardNo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//Menu to display LoanedBookManagement Options
	private void bookLoansManagement() {
		System.out.println("*******************************************************");
		System.out.println("Override Book Loan Due Date");
		String selectQuery = "SELECT * FROM tbl_book_loans WHERE dateIn IS NULL";
		try {
			pst = connection.prepareStatement(selectQuery);
			resultSet = pst.executeQuery();
			List<Integer> bookIdArray = new LinkedList<Integer>();
			List<Integer> branchIdArray = new LinkedList<Integer>();
			List<Integer> cardNoArray = new LinkedList<Integer>();
			int i = 1;
			while (resultSet.next()) {
				bookIdArray.add(resultSet.getInt("bookId"));
				branchIdArray.add(resultSet.getInt("branchId"));
				cardNoArray.add(resultSet.getInt("cardNo"));
				System.out.println(i + ") bookId: " + resultSet.getInt("bookId") + " branchId: " + resultSet.getInt("branchId") + " cardNo: " + resultSet.getInt("cardNo") + " due date: " + resultSet.getString("dueDate"));
				i++;
			}
			System.out.println(i + ") Return to previous page.");
			if (scan.hasNextInt()) {
				int answer = scan.nextInt();
				if (answer > 0 && answer < i) {
					int bookId = bookIdArray.get(answer - 1);
					int branchId = branchIdArray.get(answer - 1);
					int cardNo = cardNoArray.get(answer - 1);
					overrideDueDate(bookId, branchId, cardNo);
				}
				else if (answer == i) {
					adminMenu();
				}
				else {
					System.out.println("Invalid Input. Please try again.");
					bookLoansManagement();
				}
			}
			else {
				System.out.println("Invalid Input. Please try again.");
				bookLoansManagement();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//OverRide the due-date of book with bookId, branchId, cardNo
	private void overrideDueDate(int bookId, int branchId, int cardNo) {
		System.out.println("*******************************************************");
		System.out.println("Enter the new due date.");
		String dueDate = scan.nextLine();
		try {
			String updateQuery = "UPDATE tbl_book_loans SET dueDate=? WHERE bookId=? AND branchId=? AND cardNo=?";
			pst = connection.prepareStatement(updateQuery);
			pst.setString(1, dueDate);
			pst.setInt(2, bookId);
			pst.setInt(3, branchId);
			pst.setInt(4, cardNo);
			pst.executeUpdate();
			System.out.println("Override complete.");
			adminMenu();
		} catch (SQLException e) {
			System.out.println("Invalid Input. Please try again.");
			overrideDueDate(bookId, branchId, cardNo);
		}
	}
	//Prompt to add a Book. Start with Title..
	private void addBookPrompt() {
		System.out.println("*******************************************************");
		System.out.println("Enter the book's title.");
		String title = scan.nextLine();
		if (title.equals("0")) {
			bookManagement();
			return;
		}
		try {
			String selectTitleQuery = "SELECT * FROM tbl_book WHERE title=?";
			pst = connection.prepareStatement(selectTitleQuery);
			pst.setString(1, title);
			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				System.out.println("The book with the title " + title + " already exist in the system.");
				addBookPrompt();
			}
			else {
				System.out.println("Choose the author.");
				String author = listAuthors();
				if (author.equals("0")) {
					bookManagement();
					return;
				}
				System.out.println("Enter the name of the book's publisher.");
				String pubName = listPublishers();
				
				
				addBookReview(title, author, pubName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Review Transaction with Console to show User 
	private void addBookReview(String title, String author, String pubName) {
		System.out.println("You have successfully added the book " + title + ". ");
		System.out.println("Book title: " + title);
		System.out.println("Book author: " + author);
		System.out.println("Publisher: " + pubName);
		int authorId = getAuthorId(author);
		int pubId = getPubId(pubName);	
		
		addBook(title, pubId);
		int bookId = getBookId(title);
		labelAuthor(bookId, authorId);
		addBookCopies(bookId);
		bookManagement();	
	}
	//Use UncheckedException to throw by Parsing the data 
	//Try reformat the input as an Integer?? ERRORS? Exceptions
	//Take a String Object
	//Try it as an Integer. Return with the data
	//Catch on false
	private boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	//gets borrowerName with cardNo
	private String getBorrowerName(int cardNo) {
		String selectQuery = "SELECT * FROM tbl_borrower WHERE cardNo=?";
		try {
			pst = connection.prepareStatement(selectQuery);
			pst.setInt(1, cardNo);
			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";	
	}
	
	//get borrower phone and address on cardNo
	private String getBorrowerAddress(int cardNo) {
		String selectQuery = "SELECT * FROM tbl_borrower WHERE cardNo=?";
		try {
			pst = connection.prepareStatement(selectQuery);
			pst.setInt(1, cardNo);
			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("address");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";	
	}
	
	private String getBorrowerPhone(int cardNo) {
		String selectQuery = "SELECT * FROM tbl_borrower WHERE cardNo=?";
		try {
			pst = connection.prepareStatement(selectQuery);
			pst.setInt(1, cardNo);
			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("phone");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";	
	}
	//add copies of book with bookId. Redundant. Scan input
	//check nextToken of the expression. Matches? Convert it using ASCII
	private void addBookCopies(int bookId) {
		List<Integer> branchIdArray = new LinkedList<Integer>();
		try {
			String selectQuery = "SELECT * FROM tbl_library_branch";
			pst = connection.prepareStatement(selectQuery);
			resultSet = pst.executeQuery();
			while (resultSet.next()) {
				branchIdArray.add(resultSet.getInt("branchId"));
			}
			String insertQuery = "INSERT INTO tbl_book_copies VALUES(?,?,0)";
			pst = connection.prepareStatement(insertQuery);
			pst.setInt(1, bookId);
			for (int i = 0; i < branchIdArray.size(); i++) {
				pst.setInt(2, branchIdArray.get(i));
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	//add modify or retrieve book info from database
	private void addBook(String title, int pubId) {
		String insertQuery = "INSERT INTO tbl_book (title, pubId) VALUES(?,?)";
		try {
			pst = connection.prepareStatement(insertQuery);
			pst.setString(1, title);
			pst.setInt(2, pubId);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	private int getBookId(String title) {
		String selectQuery = "SELECT * FROM tbl_book WHERE title=?";
		try {
			pst = connection.prepareStatement(selectQuery);
			pst.setString(1, title);
			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("bookId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private String getBookTitle(int bookId) {
		String selectQuery = "SELECT * FROM tbl_book WHERE bookId=?";
		try {
			pst = connection.prepareStatement(selectQuery);
			pst.setInt(1, bookId);
			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("title");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private void updateTitle(int bookId, String title) {
		try {
			String updateQuery = "UPDATE tbl_book SET title=? WHERE bookId=?";
			pst = connection.prepareStatement(updateQuery);
			pst.setString(1, title);
			pst.setInt(2, bookId);
			pst.executeUpdate();
			System.out.println("Update complete.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Author state changes with CRUD
	private void labelAuthor(int bookId, int authorId) {
		try {
			if (state == State.ADD) {
				String insertQuery = "INSERT INTO tbl_book_authors VALUES(?,?)";
				pst = connection.prepareStatement(insertQuery);
				pst.setInt(1, bookId);
				pst.setInt(2, authorId);
			}
			else if (state == State.UPDATE) {
				String updateQuery = "UPDATE tbl_book_authors SET authorId=? WHERE bookId=?";
				pst = connection.prepareStatement(updateQuery);
				pst.setInt(1, authorId);
				pst.setInt(2, bookId);
			}
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    //gets pubName and pubId from publisher table
	private int getPubId(String pubName) {
		String selectQuery = "SELECT * FROM tbl_publisher WHERE publisherName=?";
		try {
			pst = connection.prepareStatement(selectQuery);
			pst.setString(1, pubName);
			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("publisherId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private String getPubName(int bookId) {
		try {
			String selectQuery = "SELECT * FROM tbl_publisher JOIN tbl_book ON tbl_publisher.publisherId=tbl_book.pubId WHERE bookId=?";
			pst = connection.prepareStatement(selectQuery);
			pst.setInt(1, bookId);
			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("publisherName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private void addPub(String pubName, String pubAddress, String pubPhone) {
		String insertQuery = "INSERT INTO tbl_publisher (publisherName, publisherAddress, publisherPhone) VALUES(?,?,?)";
		try {
			pst = connection.prepareStatement(insertQuery);
			pst.setString(1, pubName);
			pst.setString(2, pubAddress);
			pst.setString(3, pubPhone);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	private void updatePub(int pubId, int bookId) {
		String updateQuery = "UPDATE tbl_book SET pubId=? WHERE bookId=?";
		try {
			pst = connection.prepareStatement(updateQuery);
			pst.setInt(1, pubId);
			pst.setInt(2, bookId);
			pst.executeUpdate();
			System.out.println("Update complete.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	

	
	private int getAuthorId(String author) {
		String selectAuthorQuery = "SELECT * FROM tbl_author WHERE authorName=?";
		try {
			pst = connection.prepareStatement(selectAuthorQuery);
			pst.setString(1, author);
			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("authorId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private void addAuthor(String author) {
		String insertAuthorQuery = "INSERT INTO tbl_author (authorName) VALUES (?)";
		try {
			pst = connection.prepareStatement(insertAuthorQuery);
			pst.setString(1, author);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	private String getAuthorName(int bookId) {
		String selectQuery = "SELECT * FROM tbl_author JOIN tbl_book_authors ON tbl_author.authorId=tbl_book_authors.authorId WHERE bookId=?";
		try {
			pst = connection.prepareStatement(selectQuery);
			pst.setInt(1, bookId);
			resultSet = pst.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("authorName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	private void updateAuthorName(int bookId, String authorName) {
		String selectQuery = "SELECT * FROM tbl_author WHERE authorName=?";
		try {
			pst = connection.prepareStatement(selectQuery);
			pst.setString(1, authorName);
			resultSet = pst.executeQuery();
			int authorId = 0;
			if (!resultSet.next()) {
				addAuthor(authorName);
			}
			authorId = getAuthorId(authorName);
			labelAuthor(bookId, authorId);
			System.out.println("Update complete.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
