package com.gcit.lms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.util.ErrorResponse;

@RestController
public class AdminService {

	@Autowired
	BookDAO bdao;

	@Autowired
	AuthorDAO adao;
	
	@Autowired
	GenreDAO gdao;
	
	@Autowired
	PublisherDAO pdao;

	@Transactional
	@RequestMapping(value = "/saveBook", method = RequestMethod.POST, consumes = "application/json", produces="application/json")
	public ErrorResponse saveBook(@RequestBody Book book) {
		ErrorResponse resp = new ErrorResponse();
		try {
			if (book.getBookId() != null && book.getTitle() != null) {
				bdao.editBook(book);
				resp.setErrorMessage("Book updated sucessfully");
			} else if (book.getBookId() != null) {
				bdao.deleteBook(book);
				resp.setErrorMessage("Book deleted sucessfully");
			} else {
				bdao.addBook(book);
				Book bookWithID = bdao.readAllBooksByTitle(book.getTitle()).get(0);
				if(book.getAuthors() != null) {
					for(Author a: book.getAuthors()){
						bdao.addBookAuthorByAuthorName(bookWithID.getBookId(), a.getAuthorName());
					}
				}
				if(book.getGenres() != null) {
					for(Genre g: book.getGenres()){
						bdao.addBookGenreByGenreName(bookWithID.getBookId(), g.getGenreName());
					}
				}
				if(book.getPublisher() !=null ) {
					bdao.addBookPublisherByPubName(bookWithID.getBookId(), book.getPublisher().getPubName());
				}
				resp.setErrorMessage("Book added sucessfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setErrorMessage("Error Occured while saving");
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/readAllAuthors", method = RequestMethod.GET, produces = "application/json")
	public List<Author> readAllAuthors(@RequestParam String searchString) {
		List<Author> authors = new ArrayList<>();
		try {
			if (!searchString.isEmpty()) {
				authors = adao.readAllAuthorsByName(searchString);
			} else {
				authors = adao.readAllAuthors();
			}
			for (Author a : authors) {
				a.setBooks(bdao.getBooksByAuthorID(a.getAuthorId()));
			}
			return authors;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/readAuthorsByName/{searchString}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Author> readAuthorsByName(@PathVariable("searchString") String searchString) {
		try {
			List<Author> authors = adao.readAllAuthorsByName(searchString);
			for (Author a : authors) {
				a.setBooks(bdao.getBooksByAuthorID(a.getAuthorId()));
			}
			return authors;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/saveNewAuthor", method = RequestMethod.POST, consumes = "application/json", produces="application/json")
	public ErrorResponse saveNewAuthor(@RequestBody List<Author> authors) {
		ErrorResponse resp = new ErrorResponse();
		try {
				for(Author a:authors) {
					if(a.getAuthorId() == null)
						adao.addAuthor(a);
				}
				resp.setErrorMessage("Author added sucessfully");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setErrorMessage("Error Occured while saving");
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@RequestMapping(value = "/saveNewGenre", method = RequestMethod.POST, consumes = "application/json", produces="application/json")
	public ErrorResponse saveNewGenre(@RequestBody List<Genre> genres) {
		ErrorResponse resp = new ErrorResponse();
		try {
				for(Genre a:genres) {
					if(a.getGenreId() == null)
						gdao.addGenre(a);
				}
				resp.setErrorMessage("Genre added sucessfully");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setErrorMessage("Error Occured while saving");
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@RequestMapping(value = "/saveNewPublisher", method = RequestMethod.POST, consumes = "application/json", produces="application/json")
	public ErrorResponse saveNewPublisher(@RequestBody Publisher publisher) {
		ErrorResponse resp = new ErrorResponse();
		try {
			pdao.addPublisher(publisher);				
			resp.setErrorMessage("Publisher added sucessfully");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setErrorMessage("Error Occured while saving");
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@RequestMapping(value = "/saveAuthor", method = RequestMethod.POST, consumes = "application/json", produces="application/json")
	public ErrorResponse saveAuthor(@RequestBody Author author) {
		ErrorResponse resp = new ErrorResponse();
		try {
			if (author.getAuthorId() != null && author.getAuthorName() != null) {
				adao.editAuthor(author);
				resp.setErrorMessage("Author updated sucessfully");
			} else if (author.getAuthorId() != null) {
				adao.deleteAuthor(author);
				resp.setErrorMessage("Author deleted sucessfully");
			} else {
				adao.addAuthor(author);
				List<Author> authors = adao.readAllAuthorsByName(author.getAuthorName());
				if(author.getBooks()!=null) {
					for(Book b: author.getBooks()){
						bdao.addBookAuthor(b.getBookId(), authors.get(0).getAuthorId());
					}
				}
				resp.setErrorMessage("Author added sucessfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setErrorMessage("Error Occured while saving");
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@RequestMapping(value = "/initAuthor", method = RequestMethod.GET, produces="application/json")
	public Author initAuthor(){
		return new Author();
	}
	@RequestMapping(value = "/initBook", method = RequestMethod.GET, produces="application/json")
	public Book initBook(){
		return new Book();
	}
	@RequestMapping(value = "/initBorrower", method = RequestMethod.GET, produces="application/json")
	public Borrower initBorrower(){
		return new Borrower();
	}
	
	@RequestMapping(value = "/readAllBooks", method = RequestMethod.GET, produces = "application/json")
	public List<Book> readAllBooks(@RequestParam String searchString) {
		List<Book> books = new ArrayList<>();
		try {
			if (!searchString.isEmpty()) {
				books = bdao.readAllBooksByTitle(searchString);
			} else {
				books = bdao.readAllBooks();
			}
			for (Book a : books) {
				a.setAuthors(adao.getAuthorsByBookID(a.getBookId()));
				a.setGenres(gdao.getGenresByBookID(a.getBookId()));
				a.setPublisher(pdao.getPublisherByBookID(a.getBookId()));
			}
			
			return books;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	@RequestMapping(value = "/removeAuthor", method = RequestMethod.POST, consumes = "application/json", produces="application/json")
	public ErrorResponse removeAuthor(@RequestBody Author author) {
		ErrorResponse resp = new ErrorResponse();
		Integer authorId = author.getAuthorId() ;
		Integer bookId = author.getBooks().get(0).getBookId();
		try {
				if(bookId !=null &&  authorId != null) {
					bdao.removeBookAuthor(bookId, authorId);
				}
				
		} catch (Exception e) {
			e.printStackTrace();
			resp.setErrorMessage("Error Occured while removing author");
			resp.setStatus(Boolean.FALSE);
			return resp;
		}
		return null;		
	}
	
	@RequestMapping(value = "/updateBook", method = RequestMethod.POST, consumes = "application/json", produces="application/json")
	public ErrorResponse updateBook(@RequestBody Book book) {
		ErrorResponse resp = new ErrorResponse();
		try {
			if (book.getBookId() != null && book.getTitle() != null) {
				bdao.editBook(book);
			}
			for(Author author : book.getAuthors()) {
				if (author.getAuthorId() != null && author.getAuthorName() != null) {
					adao.editAuthor(author);
				}
				else if(author.getAuthorId() == null && author.getAuthorName()!=null){
					adao.addAuthor(author);
				}
			}
			if(book.getAuthors()!=null) {
				bdao.deleteBookIDinBookAuthors(book.getBookId());
				for(Author b: book.getAuthors()){
					bdao.addBookAuthorByAuthorName(book.getBookId(), b.getAuthorName());
				}
			}
			resp.setErrorMessage("Book updated sucessfully");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setErrorMessage("Error Occured while updating");
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@RequestMapping(value = "/readAllGenres", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity <List<Genre>> readAllGenres(@RequestParam String searchString) {
		List<Genre> genres = new ArrayList<>();
		try {
			if (!searchString.isEmpty()) {
				genres = gdao.readAllGenresByName(searchString);
			} else {
				genres = gdao.readAllGenres();
			}
			for (Genre g : genres) {
				g.setBooks(gdao.getBooksByGenreID(g.getGenreId()));
			}
			return new ResponseEntity<>(genres,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/readAllPublishers", method = RequestMethod.GET, produces = "application/json")
	public List<Publisher> readAllPublishers(@RequestParam String searchString) {
		List<Publisher> publishers = new ArrayList<>();
		try {
			if (!searchString.isEmpty()) {
				publishers = pdao.readAllPublishersByName(searchString);
			} else {
				publishers = pdao.readAllPublishers();
			}
			for (Publisher g : publishers) {
				g.setBooks(pdao.getBooksByPublisherID(g.getPublisherId()));
			}
			return publishers;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
