/**
 * 
 */
package com.ss.sf.lms.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.sf.lms.domain.Borrower;

/**
 *	Perform CRUD Operations on Borrowers in tbl_borrower.
 *  Queries, Save query transactions 
 *
 */
public class BorrowerDAO extends BaseDAO<Borrower> {
	
	

	public void addBorrower(Borrower borrower) throws ClassNotFoundException, SQLException, IOException {
		save("Insert into tbl_borrower (cardNo,name,address,phone) values (?,?,?,?)",
				new Object[] { borrower.getCardNo(), borrower.getName(),
						borrower.getAddress(), borrower.getPhone() });
	}
	
	

	public void updateBorrower(Borrower borrower) throws ClassNotFoundException, SQLException, IOException {
		save("update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?",
				new Object[] { borrower.getName(), borrower.getAddress(),
						borrower.getPhone(), borrower.getCardNo() });
	}
	
	

	public void deleteBorrower(Borrower borrower) throws ClassNotFoundException, SQLException, IOException {
		save("delete from tbl_borrower where cardNo = ?", new Object[] { borrower.getCardNo() });
	}
	
	

	public List<Borrower> readBorrowers() throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_borrower ", null);
	}

	
	public List<Borrower> readBorrowersByName(String name)
			throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_borrower where name  = ? ", new Object[] { name });
	}
	
	

	public List<Borrower> readBorrowersByCardNo(Integer cardNo)
			throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_borrower where cardNo  = ? ", new Object[] { cardNo });
	}
	
	
	@Override
	List<Borrower> extractData(ResultSet rs) throws SQLException, ClassNotFoundException, IOException {
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower borrower = new Borrower();
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setName(rs.getString("name"));
			borrower.setAddress(rs.getString("address"));
			borrower.setPhone(rs.getString("phone"));
			borrowers.add(borrower);
		}
		return borrowers;
	}

}
