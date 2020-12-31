/**
 * 
 */
package com.ss.sf.lms.domain;

import java.io.Serializable;

/**
 * 
 * 
 * Domain Object to store Borrower data. Transfer data with BorrowerDAO to communicate with Object from database.
 */
public class Borrower implements Serializable {

	
	private static final long serialVersionUID = -8041922200501119099L;
	
	private Integer cardNo;
	private String name;
	private String address;
	private String phone;

	/**
	 * @return the cardNo
	 */
	public Integer getCardNo() {
		return cardNo;
	}

	/**
	 * @param cardNo theCardNo to set
	 */
	public void setCardNo(Integer cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param setAddress the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/*
	 * @return the phone
	 */

	public String getPhone() {
		return phone;
	}

	/**
	 * @param setPhone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Borrower other = (Borrower) obj;
		if (cardNo == null) {
			if (other.cardNo != null)
				return false;
		} else if (!cardNo.equals(other.cardNo))
			return false;
		return true;
	}
}