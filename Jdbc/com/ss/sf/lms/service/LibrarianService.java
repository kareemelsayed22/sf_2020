package com.ss.sf.lms.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.ss.sf.lms.dao.BookCopyDAO;
import com.ss.sf.lms.dao.BookDAO;
import com.ss.sf.lms.dao.LibraryBranchDAO;
import com.ss.sf.lms.domain.Book;
import com.ss.sf.lms.domain.BookCopy;
import com.ss.sf.lms.domain.LibraryBranch;
import com.ss.sf.lms.presentation.LibraryManagementSystem;
/**
 * This class handles the service for librarian. Prompt user with menus. 
 * Update, delete branches based of UserInput.
 * updates BookCopy entry if already exists, adds it if not. Lets Librarians pick the branch they manage.
 * Allows Librarians to change library name and address. 
 * Exit on inputs "quit" and "N/A".
 * 
 *
 */
public class LibrarianService extends Service {

	
	LibraryBranchDAO myLBDAO = new LibraryBranchDAO();				
	BookDAO myBookDAO = new BookDAO();								
	BookCopyDAO myBCDAO = new BookCopyDAO();
	public List<String> libOneOptions = new ArrayList<>(Arrays.asList("Enter Branch you manage"));
	public List<String> libThreeOptions = new ArrayList<>(
			Arrays.asList("Update the details of the Library", "Add copies of Book to the Branch"));
	Scanner sc = new Scanner(System.in);															
	

	//First menu to be displayed
	//Option to return to mainMenu
	public void libMenuOne() throws ClassNotFoundException, SQLException, IOException {
		Integer c = this.makeMenu(this.libOneOptions, sc);
		switch (c) {
		case 1:
			this.libMenuTwo();
			break;
		case 2:
			LibraryManagementSystem.mainMenu();		
			break;
		}
	}
	
	
	public void libMenuTwo() throws ClassNotFoundException, SQLException, IOException {

		List<String> branchNames = new ArrayList<>();		
		List<LibraryBranch> branches = new ArrayList<>();
		branches = myLBDAO.readLibraryBranches();
		
		myLBDAO.readLibraryBranches().forEach(b -> {		
			branchNames.add(b.getBranchName());
		});
		
		Integer c = this.makeMenu(this.getBranchChoices(), sc);			

		if (c == branches.size() + 1) {						
			this.libMenuOne();
		}
		
		LibraryBranch library = branches.get(c - 1);		
		this.libMenuThree(library);							
	}
	
	

	public void libMenuThree(LibraryBranch library) throws ClassNotFoundException, SQLException, IOException {

		Integer c = this.makeMenu(this.libThreeOptions, sc);
		switch (c) {
		case 1:
			this.updateBranchAsLibrarian(library);
			break;
		case 2:
			this.addCopiesAsLibrarian(library);
			break;
		case 3:
			this.libMenuTwo();
			break;
		}
	}

	

	public void updateBranchAsLibrarian(LibraryBranch library)
			throws ClassNotFoundException, SQLException, IOException {
		
		System.out.println("You have chosen to update the Branch with Branch Id: " + library.getBranchId()
				+ " and Branch Name: " + library.getBranchName());
		
		System.out.println("Enter 'quit' at any prompt to cancel operation");
		
		System.out.println("Please enter new branch name or enter N/A for no change:");		
		String name = "update failed";								
		try (Scanner sc = new Scanner(System.in)) {
			String s = sc.nextLine();
			
			if (s.equals("N/A")) {					
				name = library.getBranchName();		
			} else if (s.equals("quit")) {
				this.libMenuThree(library);
			} else {
				name = s;
			}
			
			System.out.println("Please enter new branch address or enter N/A for no change:");	
			String address = "update failed";								
			String t = sc.nextLine();
			
			if (t.equals("N/A")) {								
				address = library.getBranchAddress();			
			} else if (t.equals("quit")) {
				this.libMenuThree(library);
			} else {
				address = t;
			}
			
			library.setBranchName(name);		
			library.setBranchAddress(address);
		}
		myLBDAO.updateLibraryBranch(library);
		this.libMenuThree(library);			
	}

	
	public void addCopiesAsLibrarian(LibraryBranch library) throws ClassNotFoundException, SQLException, IOException {
		
		System.out.println("Pick the Book you want to add copies of to your branch:");

		List<Book> allBooks = new ArrayList<>();		

		List<String> allTitles = new ArrayList<>();	

		allBooks = myBookDAO.readBooks();				
		myBookDAO.readBooks().forEach(b -> {
			allTitles.add(b.getTitle());
		});
		
		List<String> allAuthorNames = new ArrayList<>();
		allBooks.forEach(b -> {
			try {
				allAuthorNames.add(this.getAuthorName(this.bookIdToAuthorId(b.getBookId())));
			} catch (ClassNotFoundException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		List<String> allTitlesAndAuthorNames = new ArrayList<>();
		
		for(int i = 0; i < allTitles.size(); i++) {
			allTitlesAndAuthorNames.add(allTitles.get(i) + " by " + allAuthorNames.get(i));
		}

		Integer c = this.makeMenu(allTitlesAndAuthorNames, sc);

		if (c > allTitles.size()) {				
			this.libMenuThree(library);
		}

		Book addedBook = allBooks.get(c - 1);			
		
		
		List<BookCopy> existingCopies = new ArrayList<>();			
																	
		existingCopies = myBCDAO.readBookCopiesByBookIdAndBranchId(addedBook.getBookId(), library.getBranchId());	
	
		Integer currentCopies;							
		if (existingCopies.size() == 0) {
			currentCopies = 0;
		} else {
			currentCopies = existingCopies.get(0).getNoOfCopies();
		}
		BookCopy myCopies = new BookCopy();				

		if (existingCopies.size() == 1) {
			myCopies = existingCopies.get(0);			
		} else {
			myCopies.setBookId(addedBook.getBookId());	
			myCopies.setBranchId(library.getBranchId());
			myCopies.setNoOfCopies(0);
		}
		
		System.out.println("Existing Number of Copies: " + currentCopies);
		System.out.println("Enter new number of copies: ");
		
		Integer newCopies = currentCopies;				
		try (Scanner sc = new Scanner(System.in)) {
			newCopies = sc.nextInt();						
		}
		myCopies.setNoOfCopies(newCopies);

		if (existingCopies.size() == 1) {				
			myBCDAO.updateBookCopy(myCopies);
		} else {
			myBCDAO.addBookCopy(myCopies);
		}

		this.libMenuThree(library);

	}

}
