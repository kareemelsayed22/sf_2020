/**
 * 
 */
package com.ss.sf.lms.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.sf.lms.domain.LibraryBranch;

/**
 *	Perform CRUD Operations on Libraries in tbl_library_branch.
 *  Queries, Save query transactions 
 *
 */
public class LibraryBranchDAO extends BaseDAO<LibraryBranch> {
	
	

	public void addLibraryBranch(LibraryBranch libraryBranch) throws ClassNotFoundException, SQLException, IOException {
		save("Insert into tbl_library_branch (branchId,branchName,branchAddress) values (?,?,?)",
				new Object[] { libraryBranch.getBranchId(), libraryBranch.getBranchName(), libraryBranch.getBranchAddress() });
	}

	
	public void updateLibraryBranch(LibraryBranch libraryBranch)
			throws ClassNotFoundException, SQLException, IOException {
		save("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?", new Object[] {
				libraryBranch.getBranchName(), libraryBranch.getBranchAddress(), libraryBranch.getBranchId() });
	}
	
	

	public void deleteLibraryBranch(LibraryBranch libraryBranch) throws ClassNotFoundException, SQLException, IOException {
		save("delete from tbl_library_branch where branchId = ?", new Object[] { libraryBranch.getBranchId() });
	}
	
	

	public List<LibraryBranch> readLibraryBranches() throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_library_branch ", null);
	}
	
	

	public List<LibraryBranch> readLibraryBranchesByBranchId(Integer branchId) throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_library_branch where branchId  = ? ", new Object[] { branchId });
	}
	
	

	public List<LibraryBranch> readLibraryBranchesByBranchAddress(String branchAddress) throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_library_branch where branchAddress  = ? ", new Object[] { branchAddress });
	}
	
	

	public List<LibraryBranch> readLibraryBranchesByBranchName(String branchName) throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_library_branch where branchName  = ? ", new Object[] { branchName });
	}
	
	
	@Override
	List<LibraryBranch> extractData(ResultSet rs) throws SQLException, ClassNotFoundException, IOException {
		List<LibraryBranch> libraryBranches = new ArrayList<>();
		while (rs.next()) {
			LibraryBranch libraryBranch = new LibraryBranch();
			libraryBranch.setBranchId(rs.getInt("branchId"));
			libraryBranch.setBranchName(rs.getString("branchName"));
			libraryBranch.setBranchAddress(rs.getString("branchAddress"));
			libraryBranches.add(libraryBranch);
		}
		return libraryBranches;
	}

}
