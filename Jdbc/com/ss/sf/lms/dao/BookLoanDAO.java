/**
 * 
 */
package com.ss.sf.lms.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ss.sf.lms.domain.BookLoan;

/**
 *	Perform CRUD Operations on Books Loaned in tbl_book_loans.
 *  Queries, Save query transactions tbl_book_loans
 *
 */
public class BookLoanDAO extends BaseDAO<BookLoan> {
	
	

	public void addBookLoan(BookLoan bookLoan) throws ClassNotFoundException, SQLException, IOException {
		save("Insert into tbl_book_loans (bookId,branchId,cardNo,dateOut,dueDate) values (?,?,?,?,?)",
				new Object[] { bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo(),
						Timestamp.valueOf(bookLoan.getDateOut().toLocalDateTime()), Timestamp.valueOf(bookLoan.getDueDate().toLocalDateTime()) });
	}
	
	

	public void updateBookLoan(BookLoan bookLoan) throws ClassNotFoundException, SQLException, IOException {
		save("update tbl_book_loans set dueDate = ? where (bookId = ? AND branchId = ? AND cardNo = ?)", new Object[] {
				Timestamp.valueOf(bookLoan.getDueDate().toLocalDateTime()), bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo() });
	}
	
	

	public void deleteBookLoan(BookLoan bookLoan) throws ClassNotFoundException, SQLException, IOException {
		save("delete from tbl_book_loans where (bookId = ? AND branchId = ? AND cardNo = ?)",
				new Object[] { bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getCardNo() });
	}
	
	

	public List<BookLoan> readBookLoans() throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_book_loans ", null);
	}
	
	

	public List<BookLoan> readBookLoansByBookId(Integer bookId) throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_book_loans where bookId  = ? ", new Object[] { bookId });
	}
	
	

	public List<BookLoan> readBookLoansByBranchId(Integer branchId) throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_book_loans where branchId  = ? ", new Object[] { branchId });
	}
	
	

	public List<BookLoan> readBookLoansByBookIdAndBranchId(Integer bookId, Integer branchId) throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_book_loans where (bookId = ? AND branchId = ?) ", new Object[] { bookId, branchId });
	}
	
	
	
	public List<BookLoan> readBookLoansByCardNo(Integer cardNo) throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_book_loans where cardNo = ? ", new Object[] { cardNo });
	}

	
	@Override
	List<BookLoan> extractData(ResultSet rs) throws SQLException, ClassNotFoundException, IOException {
		List<BookLoan> bookLoans = new ArrayList<>();
		while (rs.next()) {
			BookLoan bookLoan = new BookLoan();
			bookLoan.setBookId(rs.getInt("bookId"));
			bookLoan.setBranchId(rs.getInt("branchId"));
			bookLoan.setCardNo(rs.getInt("cardNo"));
			bookLoan.setDateOut(ZonedDateTime.ofInstant(rs.getTimestamp("dueDate").toInstant(), ZoneId.of("UTC-5")));
			bookLoan.setDueDate(ZonedDateTime.ofInstant(rs.getTimestamp("dueDate").toInstant(), ZoneId.of("UTC-5")));
			bookLoans.add(bookLoan);
		}
		return bookLoans;
	}

}
