/**
 * 
 */
package com.ss.sf.lms.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.sf.lms.domain.Book;

/**
 *	Perform CRUD Operations on Books in tbl_book.
 *  Queries, Save query transactions 
 *
 */
public class BookDAO extends BaseDAO<Book> {
	
	

	public void addBook(Book book) throws ClassNotFoundException, SQLException, IOException {
		save("Insert into tbl_book (bookId,title,authId,pubId) values (?,?,?,?)",
				new Object[] { book.getBookId(), book.getTitle(), book.getAuthId(), book.getPubId() });
	}
	
	

	public void updateBook(Book book) throws ClassNotFoundException, SQLException, IOException {
		save("update tbl_book set title = ?, authId = ?, pubId = ? where bookId = ?",
				new Object[] { book.getTitle(), book.getAuthId(), book.getPubId(), book.getBookId() });
	}
	
	

	public void deleteBook(Book book) throws ClassNotFoundException, SQLException, IOException {
		save("delete from tbl_book where bookId = ?", new Object[] { book.getBookId() });
	}
	
	

	public List<Book> readBooks() throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_book ", null);
	}

	
	public List<Book> readBooksByTitle(String title) throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_book where title  = ? ", new Object[] { title });
	}

	
	public List<Book> readBooksByBookId(Integer bookId) throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_book where bookId  = ? ", new Object[] { bookId });
	}
	
	

	public List<Book> readBooksByAuthId(Integer authId) throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_book where authId  = ? ", new Object[] { authId });
	}
	
	
	
	public List<Book> readBooksByPubId(Integer pubId) throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_book where pubId  = ? ", new Object[] { pubId });
	}
	
	

	@Override
	List<Book> extractData(ResultSet rs) throws SQLException, ClassNotFoundException, IOException {
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			book.setAuthId(rs.getInt("authId"));
			book.setPubId(rs.getInt("pubId"));
			books.add(book);
		}
		return books;
	}

}
