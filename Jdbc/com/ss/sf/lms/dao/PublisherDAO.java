/**
 * 
 */
package com.ss.sf.lms.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.sf.lms.domain.Publisher;

/**
 *	Perform CRUD Operations on publishers in tbl_publisher.
 *  Queries, Save query transactions 
 *
 */
public class PublisherDAO extends BaseDAO<Publisher> {
	
	

	public void addPublisher(Publisher publisher) throws ClassNotFoundException, SQLException, IOException {
		save("Insert into tbl_publisher (publisherID,publisherName,publisherAddress,publisherPhone) values (?,?,?,?)",
				new Object[] { publisher.getPublisherId(), publisher.getPublisherName(),
						publisher.getPublisherAddress(), publisher.getPublisherPhone() });
	}
	
	

	public void updatePublisher(Publisher publisher) throws ClassNotFoundException, SQLException, IOException {
		save("update tbl_publisher set publisherName = ?, publisherAddress = ?, publisherPhone = ? where publisherId = ?",
				new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress(),
						publisher.getPublisherPhone(), publisher.getPublisherId() });
	}
	
	

	public void deletePublisher(Publisher publisher) throws ClassNotFoundException, SQLException, IOException {
		save("delete from tbl_publisher where publisherId = ?", new Object[] { publisher.getPublisherId() });
	}
	
	

	public List<Publisher> readPublishers() throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_publisher ", null);
	}

	
	public List<Publisher> readPublishersByPublisherName(String publisherName)
			throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_publisher where publisherName  = ? ", new Object[] { publisherName });
	}
	
	

	public List<Publisher> readPublishersByPublisherId(Integer publisherId)
			throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_publisher where publisherId  = ? ", new Object[] { publisherId });
	}
	
	
	@Override
	List<Publisher> extractData(ResultSet rs) throws SQLException, ClassNotFoundException, IOException {
		List<Publisher> publishers = new ArrayList<>();
		while (rs.next()) {
			Publisher publisher = new Publisher();
			publisher.setPublisherId(rs.getInt("publisherId"));
			publisher.setPublisherName(rs.getString("publisherName"));
			publisher.setPublisherAddress(rs.getString("publisherAddress"));
			publisher.setPublisherPhone(rs.getString("publisherPhone"));
			publishers.add(publisher);
		}
		return publishers;
	}

}
