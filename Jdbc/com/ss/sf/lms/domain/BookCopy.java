/**
 * 
 */
package com.ss.sf.lms.domain;

import java.io.Serializable;

/**
 * 
 * Domain Object to store BookCopy data. Transfer data with BookCopyDAO to communicate with Object from database.
 * 
 */
public class BookCopy implements Serializable {


	
	private static final long serialVersionUID = 6888468835360464095L;
	

	private Integer bookId;
	private Integer branchId;
	private Integer noOfCopies;

	/**
	 * @return
	 */
	public Integer getBookId() {
		return bookId;
	}

	/**
	 * @param
	 */
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	/*
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

	/*
	 * @return 
	 */

	public Integer getNoOfCopies() {
		return noOfCopies;
	}

	/**
	 * @param 
	 */
	public void setNoOfCopies(Integer noOfCopies) {
		this.noOfCopies = noOfCopies;
	}
	/*
	 * makes sure two instances of book copy with same bookId and branchId will have the same hashcode.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		return result;
	}
/*
 * makes sure .equals returns true only if two instances have same bookId and branchId.
 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookCopy other = (BookCopy) obj;
		if (bookId == null) {
			if (other.bookId != null)
				return false;
		} else if (!bookId.equals(other.bookId))
			return false;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		return true;
	}
}