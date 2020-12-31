/**
 * 
 */
package com.ss.sf.lms.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ss.sf.lms.dao.AuthorDAO;
import com.ss.sf.lms.dao.BookDAO;
import com.ss.sf.lms.dao.LibraryBranchDAO;
import com.ss.sf.lms.dao.PublisherDAO;
import com.ss.sf.lms.domain.Author;
import com.ss.sf.lms.domain.Book;
import com.ss.sf.lms.domain.LibraryBranch;
import com.ss.sf.lms.domain.Publisher;

/**
 * Abstract User class that all classes of user extend. Comes with a helper function that creates menus with
 * options and ensures that each menu has a "Quit to previous" option at the
 * bottom. Shows options and returns with the userInput as an integer. Get book title with bookId. Get all branches. 
 * get the author name. Connect author table and book table on bookId and authId. 
 */
public abstract class Service {

	
	public Integer makeMenu(List<String> options, Scanner sc) {

		System.out.println("Please choose an option:");
		int i = 1;
		for (String o : options) {
			System.out.println(i + ") " + o);
			i++;
		}
		System.out.println(i + ") Quit to previous");
		Integer choice = 0;
		try {
			choice = sc.nextInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return choice;
	}

	/*
	 * Lists the Library Branches.
	 */
	public List<String> getBranchChoices() throws ClassNotFoundException, SQLException, IOException {

		LibraryBranchDAO myLBDAO = new LibraryBranchDAO();
		List<LibraryBranch> allLibraries = new ArrayList<>();
		allLibraries = myLBDAO.readLibraryBranches();

		List<String> libraryNames = new ArrayList<>();
		List<String> libraryAddresses = new ArrayList<>();
		allLibraries.forEach(lib -> {
			libraryNames.add(lib.getBranchName());
			libraryAddresses.add(lib.getBranchAddress());
		});

		List<String> libNamesAndAddresses = new ArrayList<>();
		for (int i = 0; i < libraryNames.size(); i++) {
			libNamesAndAddresses.add(libraryNames.get(i) + ", " + libraryAddresses.get(i));
		}
		return libNamesAndAddresses;
	}
	
	

	public String getTitle(Integer bookId) throws IOException, ClassNotFoundException, SQLException {
		BookDAO myBookDAO = new BookDAO();
		List<Book> myBook = myBookDAO.readBooksByBookId(bookId);
		String myTitle = myBook.get(0).getTitle();
		return myTitle;
	}

	
	public String getAuthorName(Integer authorId) throws IOException, ClassNotFoundException, SQLException {
		AuthorDAO myAuthorDAO = new AuthorDAO();
		List<Author> myAuthor = myAuthorDAO.readAuthorsById(authorId);
		String myName = myAuthor.get(0).getAuthorName();
		return myName;
	}

	
	public Integer bookIdToAuthorId(Integer bookId) throws IOException, ClassNotFoundException, SQLException {
		BookDAO myBookDAO = new BookDAO();
		List<Book> myBook = myBookDAO.readBooksByBookId(bookId);
		Integer myAuthorId = myBook.get(0).getAuthId();
		return myAuthorId;
	}

	
	public String getPublisherName(Integer publisherId) throws IOException, ClassNotFoundException, SQLException {
		PublisherDAO myPublisherDAO = new PublisherDAO();
		List<Publisher> myPublisher = myPublisherDAO.readPublishersByPublisherId(publisherId);
		String myName = myPublisher.get(0).getPublisherName();
		return myName;
	}

}
