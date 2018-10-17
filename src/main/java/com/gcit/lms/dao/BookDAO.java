package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;


import com.gcit.lms.entity.Book;

@Component
public class BookDAO extends BaseDAO<Book> implements ResultSetExtractor<List<Book>>{

	public void addBook(Book book)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("insert into tbl_book (title) values(?)", new Object[] { book.getTitle() });
	}
	
	public Integer addBookWithID(Book book)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.update("insert into tbl_book (title) values(?)", new Object[] { book.getTitle() });
	}

	public void editBook(Book book)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("update tbl_book set title = ? where bookId = ?", new Object[]{book.getTitle(), book.getBookId()});
	}

	public void deleteBook(Book book)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("delete from tbl_book where bookId = ?", new Object[]{book.getBookId()});
	}
	
	public void addBookAuthor(Integer bookId, Integer authorId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("insert into tbl_book_authors (bookId, authorId) values(?,?)", new Object[] { bookId, authorId });
			}
	
	public void addBookAuthorByAuthorName(Integer bookId, String authorName)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("insert into tbl_book_authors (bookId, authorId) values(?, (select authorId  from tbl_author where authorName = ? limit 1))", new Object[] { bookId, authorName });
			}
	public void addBookGenreByGenreName(Integer bookId, String genreName) {
		libraryTemplate.update("insert into tbl_book_genres (bookId, genre_Id) values(?, (select genre_Id  from tbl_genre where genre_name = ? limit 1))", new Object[] { bookId, genreName});		
		}
	
	public void addBookPublisherByPubName(Integer bookId, String pubName) {
		libraryTemplate.update("update tbl_book set pubId = (select publisherId  from tbl_publisher where publisherName = ? limit 1) where bookId = ?", new Object[] { pubName, bookId});		
		}
	
	public List<Book> readAllBooks()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select * from tbl_book", this);
	}
	public List<Book> readAllBooksByTitle(String searchString)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		searchString = "%"+searchString+"%";
		return libraryTemplate.query("Select * from tbl_book where title like ?", new Object[]{searchString}, this);
	}
	
	public List<Book> readAllBooksBorrowed(Integer cardNo, Integer branchId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select a.* from tbl_book a inner join tbl_book_loans b on a.bookId = b.bookId where b.cardNo = ? and branchId = ? and dateIn is null", new Object[] {cardNo, branchId},this);
	}
	
	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			books.add(book);
		}
		return books;
	}

	public List<Book> getBooksByAuthorID(Integer authorId) {
		return libraryTemplate.query("Select * from tbl_book where bookId IN (select bookId from tbl_book_authors where authorId = ?)", new Object[]{authorId}, this);
		
	}

	public void removeBookAuthor(Integer bookId, Integer authorId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("delete from tbl_book_authors where bookId = ? and authorId =?", new Object[]{bookId, authorId});
	}

	public void deleteBookIDinBookAuthors(Integer bookId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("delete from tbl_book_authors where bookId = ?", new Object[]{bookId});
	}
	
	public List<Book> readAllBooksForCheckOut(Integer branchId, Integer cardNo)
	// retrieves all the books the user do not have from a selected branch
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("select tbl_book.* from tbl_book  inner join \r\n" + 
				"	(select bookId from tbl_book_copies where bookId not in \r\n" + 
				"		(Select a.bookId from tbl_book a inner join \r\n" + 
				"			tbl_book_loans b on a.bookId = b.bookId \r\n" + 
				"			where b.cardNo = ? and b.branchId = ? and b.dateIn is null) and branchId = ?) \r\n" + 
				"	subq on tbl_book.bookId = subq.bookId", new Object[] {cardNo, branchId, branchId}, this);
	}

	
}
