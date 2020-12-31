package com.ss.sf.lms.service;
/**
 * 
 */

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.ss.sf.lms.dao.AuthorDAO;
import com.ss.sf.lms.dao.BookCopyDAO;
import com.ss.sf.lms.dao.BookLoanDAO;
import com.ss.sf.lms.dao.BorrowerDAO;
import com.ss.sf.lms.dao.LibraryBranchDAO;
import com.ss.sf.lms.domain.Author;
import com.ss.sf.lms.domain.Book;
import com.ss.sf.lms.domain.BookCopy;
import com.ss.sf.lms.domain.BookLoan;
import com.ss.sf.lms.domain.Borrower;
import com.ss.sf.lms.domain.LibraryBranch;
import com.ss.sf.lms.dao.BookDAO;
import com.ss.sf.lms.presentation.LibraryManagementSystem;

/**
 * This class is provoked when Borrower option is picked
 * Instantiate DAOs. Keep prompting user for CardNo, search tbl_borrower for cardNo. Let borrower
 * Checkout, return a book by removing the book from tbl_book_copies and adding the corresponding loan.
 * Update the Library Management System with the noOfCopies. 
 * 
 */
public class BorrowerService extends Service {
	
	public List<String> borrOneOptions = new ArrayList<>(Arrays.asList("Check out a book","Return a book"));	
	Scanner sc = new Scanner(System.in);
	
	BookDAO myBookDAO = new BookDAO();				
	BookCopyDAO myBCDAO = new BookCopyDAO();
	AuthorDAO myAuthDAO = new AuthorDAO();
	BookLoanDAO myLoanDAO = new BookLoanDAO();
	LibraryBranchDAO myLBDAO = new LibraryBranchDAO();
	BorrowerDAO myBorrDAO = new BorrowerDAO();
	
	
	public void validate() throws IOException, ClassNotFoundException, SQLException {
		
		Boolean validated = false;
		try (Scanner sc = new Scanner(System.in)) {
			Borrower currentBorrower = null;
			
			while (!validated) {								
				System.out.println("Enter your Card Number:");
				String input = sc.nextLine();
				Integer inputNumber;
				List<Borrower> matchedBorr = new ArrayList<>();
				inputNumber = Integer.parseInt(input);
				matchedBorr = myBorrDAO.readBorrowersByCardNo(inputNumber);		
				if (matchedBorr.size() == 1) {									
					currentBorrower = matchedBorr.get(0);
					validated = true;
				} else {
					System.out.println("Invalid Card Number. Please try again.");
				}
			}
			this.showBorrOne(currentBorrower);									
		}
	}
	
	
	
	public void showBorrOne(Borrower currentBorrower) throws ClassNotFoundException, IOException, SQLException {
		Integer c = this.makeMenu(borrOneOptions, sc);
		switch(c) {
		case 1:
			this.checkOut(currentBorrower);
			break;
		case 2:
			this.returnBook(currentBorrower);
			break;
		case 3:
			LibraryManagementSystem.mainMenu();
			break;
			
		}
	}
	
	
	public void checkOut(Borrower currentBorrower) throws IOException, ClassNotFoundException, SQLException {

		List<LibraryBranch> allLibraries = new ArrayList<>();		

		allLibraries = myLBDAO.readLibraryBranches();
		LibraryBranch myLibrary = new LibraryBranch();

		System.out.println("Pick the Branch you want to check out from:");
		Integer c = this.makeMenu(this.getBranchChoices(), sc);
		if (c == this.getBranchChoices().size()+1) {				
			this.showBorrOne(currentBorrower);
		}
		myLibrary = allLibraries.get(c - 1);
		this.chooseBook(currentBorrower, myLibrary);			
	}
	
	
	
	public void chooseBook(Borrower currentBorrower, LibraryBranch myLibrary) throws IOException, ClassNotFoundException, SQLException {
		
		List<BookCopy> branchCopies = new ArrayList<>();
		List<String> branchTitles = new ArrayList<>();
		List<String> branchAuthors = new ArrayList<>();
		List<String> branchTitlesAndAuthors = new ArrayList<>();
		List<BookLoan> duplicateLoan = new ArrayList<>();
		
		Book bookOption = new Book();
		Author authorOption = new Author();
		BookLoan myLoan = new BookLoan();
		BookCopy myCopy = new BookCopy();
		Boolean hasCopies = false;
		
		branchCopies = myBCDAO.readBookCopiesByBranchId(myLibrary.getBranchId());	
		
		for (int i = 0; i < branchCopies.size(); i++) {								
			if (branchCopies.get(i).getNoOfCopies() > 0) {
				bookOption = myBookDAO.readBooksByBookId(branchCopies.get(i).getBookId()).get(0);
				branchTitles.add(bookOption.getTitle());
				authorOption = myAuthDAO.readAuthorsById(bookOption.getAuthId()).get(0);
				branchAuthors.add(authorOption.getAuthorName());
				hasCopies = true;
			}
		}

		for(int i = 0; i < branchTitles.size(); i++) {
			branchTitlesAndAuthors.add(branchTitles.get(i) + " by " + branchAuthors.get(i));	
		}
		
		if(!hasCopies) {
			System.out.println("This Branch has no books.");					
			this.checkOut(currentBorrower);									
		}
		
		Integer c = this.makeMenu(branchTitlesAndAuthors, sc);				

		if(c > branchCopies.size()) {											
			this.checkOut(currentBorrower);
		}
		
		Integer originalNoOfCopies = branchCopies.get(c-1).getNoOfCopies();
		//sets up loan details
		myLoan.setBookId(branchCopies.get(c-1).getBookId());					
		myLoan.setBranchId(myLibrary.getBranchId());
		myLoan.setCardNo(currentBorrower.getCardNo());
		myLoan.setDateOut(ZonedDateTime.now());
		myLoan.setDueDate(myLoan.getDateOut().plusWeeks(1));
		//check for duplicate users
		duplicateLoan = myLoanDAO.readBookLoansByCardNo(currentBorrower.getCardNo());	
		
		for(int i = 0; i < duplicateLoan.size(); i++) {
			if (myLoan.getBookId().equals(duplicateLoan.get(i).getBookId())){
				System.out.println("You already have a copy of this book.");
				this.chooseBook(currentBorrower, myLibrary);
			}
		}
		
		myCopy.setBookId(myLoan.getBookId());									
		myCopy.setBranchId(myLoan.getBranchId());
		myCopy.setNoOfCopies(originalNoOfCopies-1);
		
		myBCDAO.updateBookCopy(myCopy);											
		myLoanDAO.addBookLoan(myLoan);											
		this.deleteZeroCopyEntries();											
		System.out.println("Thanks for using the library!");
		this.showBorrOne(currentBorrower);
		
	}
	
	//to return the borrowed book
	public void returnBook(Borrower currentBorrower) throws IOException, ClassNotFoundException, SQLException{
		
		System.out.println("Which book would you like to return?");
		
		Boolean hasLoans = false;
		
		
		BookLoan myLoan = new BookLoan();
		BookCopy myCopy = new BookCopy();
		List<BookCopy> otherCopies = new ArrayList<>();
		
		List<String> myTitles = new ArrayList<>();
		List<String> myAuthors = new ArrayList<>();
		List<String> myTitlesAndAuthors = new ArrayList<>();
		List<BookLoan> myLoans = myLoanDAO.readBookLoansByCardNo(currentBorrower.getCardNo());
		myLoans.forEach(m -> {
			try {
				myTitles.add(this.getTitle(m.getBookId()));
				myAuthors.add(this.getAuthorName(this.bookIdToAuthorId(m.getBookId())));
			} catch (ClassNotFoundException | IOException | SQLException e) {
				e.printStackTrace();
			}
		});
		
		for(int i = 0; i < myTitles.size(); i++) {
			myTitlesAndAuthors.add(myTitles.get(i)+ " by "+myAuthors.get(i));
			hasLoans = true;
		}
		
		if(!hasLoans) {
			System.out.println("You have no loans currently.");
			this.showBorrOne(currentBorrower);
		}
		Integer c = this.makeMenu(myTitlesAndAuthors, sc);
		if(c > myTitlesAndAuthors.size()) {
			this.showBorrOne(currentBorrower);
		}
		
		myLoan = myLoans.get(c-1);
		
		myCopy.setBookId(myLoan.getBookId());
		myCopy.setBranchId(myLoan.getBranchId());
		
		otherCopies = myBCDAO.readBookCopiesByBookIdAndBranchId(myCopy.getBookId(), myCopy.getBranchId());
		
		if(otherCopies.isEmpty()) {						
			myCopy.setNoOfCopies(1);
			myBCDAO.addBookCopy(myCopy);
		} else {
			myCopy.setNoOfCopies(otherCopies.get(0).getNoOfCopies()+1);
			myBCDAO.updateBookCopy(myCopy);
		}
		myLoanDAO.deleteBookLoan(myLoan);
		System.out.println("Thank you for returning!");
		this.showBorrOne(currentBorrower);
		
		
	}
	
	/*
	 * Update the LMS with noOfCopies constantly
	 */
	
	public void deleteZeroCopyEntries() throws ClassNotFoundException, SQLException, IOException {
		
		List<BookCopy> zeroCopyEntries = myBCDAO.readBookCopiesByNoOfCopies(0);
		zeroCopyEntries.forEach(e -> {
			try {
				myBCDAO.deleteBookCopy(e);
			} catch (ClassNotFoundException | SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

}
