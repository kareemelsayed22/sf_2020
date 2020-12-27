/**
 * 
 */
package com.ss.sf.lms.domain;

/**
 * @author kareemelsayed
 * Use Serializable not needed? Store with cells that have pointers? 
 * Entity Class for library. Same library name. Different branches??
 */
public class Library {
	private int branchId;
	private String branchName;
	private String branchAddress;
	
	public Library(int branchId, String branchName, String branchAddress) {
		this.branchId = branchId;
		this.branchName = branchName;
		this.branchAddress = branchAddress;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}
}
