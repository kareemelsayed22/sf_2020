/**
 * 
 */
package com.ss.sf.lms.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.sf.lms.domain.Author;

/**
 *	Perform CRUD Operations on Authors in tbl_author.
 *  Queries, Save query transactions 
 */
public class AuthorDAO extends BaseDAO<Author> {
	
	

	public void addAuthor(Author author) throws ClassNotFoundException, SQLException, IOException {
		save("Insert into tbl_author (authorId,authorName) values (?,?)", new Object[] { author.getAuthorId(), author.getAuthorName() });
	}
	
	

	public void updateAuthor(Author author) throws ClassNotFoundException, SQLException, IOException {
		save("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}
	
	
	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException, IOException {
		save("delete from tbl_author where authorId = ?", new Object[] {author.getAuthorId()});
	}
	
	

	public List<Author> readAuthors() throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_author ", null);
	}
	
	
	public List<Author> readAuthorsByName(String authorName) throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_author where authorName  = ? ", new Object[] {authorName});
	}
	
	
	
	public List<Author> readAuthorsById(Integer authorId) throws SQLException, ClassNotFoundException, IOException {
		return read("select * from tbl_author where authorId  = ? ", new Object[] {authorId});
	}
	
	
	@Override
	List<Author> extractData(ResultSet rs)  throws SQLException, ClassNotFoundException, IOException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));
			authors.add(author);
		}
		return authors;
	}

}
