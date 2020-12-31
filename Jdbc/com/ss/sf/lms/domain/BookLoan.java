/**
 * 
 */
package com.ss.sf.lms.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * 
 * Domain Object to store BookCopy data. Transfer data with BookCopyDAO to communicate with Object from database.
*/
public class BookLoan implements Serializable{

	
   private static final long serialVersionUID = -1134891893431126678L;
	
	

	private Integer bookId;
	private Integer branchId;
	private Integer cardNo;
	private ZonedDateTime dateOut;
	private ZonedDateTime dueDate;

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public Integer getCardNo() {
		return cardNo;
	}

	public void setCardNo(Integer cardNo) {
		this.cardNo = cardNo;
	}
	
	public ZonedDateTime getDateOut() {
		return this.dateOut;
	}
	
	public void setDateOut(ZonedDateTime dateOut) {
		this.dateOut = dateOut;
	}
	
	public ZonedDateTime getDueDate() {
		return this.dueDate;
	}
	
	public void setDueDate(ZonedDateTime dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result + ((cardNo == null) ? 0 : cardNo.hashCode());
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
		BookLoan other = (BookLoan) obj;
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
		if (cardNo == null) {
			if (other.cardNo != null)
				return false;
		} else if (!cardNo.equals(other.cardNo))
			return false;
		return true;
	}


}