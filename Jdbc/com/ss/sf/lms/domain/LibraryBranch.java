/**
 * 
 */
package com.ss.sf.lms.domain;

import java.io.Serializable;

/**
 * 
 * Domain Object to store LibraryBranch data. Transfer data with LibraryBranchDAO to communicate with Object from database.
 *
 */
public class LibraryBranch implements Serializable {

	
	private static final long serialVersionUID = 4480127037647037845L;
	

	private Integer branchId;
	private String branchName;
	private String branchAddress;

	/**
	 * @return
	 */
	public Integer getBranchId() {
		return branchId;
	}

	/**
	 * @param
	 */
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/*
	 * @return
	 */
	public String getBranchAddress() {
		return branchAddress;
	}

	/**
	 * @param
	 */
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LibraryBranch other = (LibraryBranch) obj;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		return true;
	}
}