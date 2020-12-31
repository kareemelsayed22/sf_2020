/**
 * 
 */
package com.ss.sf.lms.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.sf.lms.domain.BookCopy;

/**
 *	Perform CRUD Operations on BookCopies in tbl_book_copies.
 *  Queries, Save query transactions 
 */
public class BookCopyDAO extends BaseDAO<BookCopy> {
	
	

	public void addBookCopy(BookCopy bookCopy) throws ClassNotFoundException, SQLException, IOException {
		save("Insert into tbl_book_copies (bookId,branchId,noOfCopies) values (?,?,?)",
				new Object[] { bookCopy.getBookId(), bookCopy.getBranchId(), bookCopy.getNoOfCopies()});
	}
	
	

	public void updateBookCopy(BookCopy bookCopy) throws ClassNotFoundException, SQLException, IOException {
		save("update tbl_book_copies set noOfCopies = ? where (bookId = ? AND branchId = ?)",
				new Object[] { bookCopy.getNoOfCopies(), bookCopy.getBookId(), bookCopy.getBranchId() });
	}
	
	

	public void deleteBookCopy(BookCopy bookCopy) throws ClassNotFoundException, SQLException, IOException {
		save("delete from tbl_book_copies where (bookId = ? AND branchId = ?)",
				new Object[] { bookCopy.getBookId(), bookCopy.getBranchId() });
	}
	
	

	public List<BookCopy> readBookCopies() throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_book_copies ", null);
	}
	
	

	public List<BookCopy> readBookCopiesByBookId(Integer bookId) throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_book_copies where bookId  = ? ", new Object[] { bookId });
	}
	
	

	public List<BookCopy> readBookCopiesByBranchId(Integer branchId) throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_book_copies where branchId  = ? ", new Object[] { branchId });
	}
	
	

	public List<BookCopy> readBookCopiesByBookIdAndBranchId(Integer bookId, Integer branchId) throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_book_copies where (bookId = ? AND branchId = ?) ", new Object[] { bookId, branchId });
	}
	
	
	
	public List<BookCopy> readBookCopiesByNoOfCopies(Integer noOfCopies) throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_book_copies where noOfCopies = ? ", new Object[] { noOfCopies });
	}
	


	@Override
	List<BookCopy> extractData(ResultSet rs) throws SQLException, ClassNotFoundException, IOException {
		List<BookCopy> bookCopies = new ArrayList<>();
		while (rs.next()) {
			BookCopy bookCopy = new BookCopy();
			bookCopy.setBookId(rs.getInt("bookId"));
			bookCopy.setBranchId(rs.getInt("branchId"));
			bookCopy.setNoOfCopies(rs.getInt("noOfCopies"));
			bookCopies.add(bookCopy);
		}
		return bookCopies;
	}

}
