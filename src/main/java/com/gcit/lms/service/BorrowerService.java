package com.gcit.lms.service;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.LibBranchDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookBranch;
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibBranch;
import com.gcit.lms.util.ErrorResponse;

@RestController
public class BorrowerService {
	
	@Autowired
	BorrowerDAO brdao;
	
	@Autowired
	BookDAO bdao;
	
	@Autowired
	LibBranchDAO ldao;
	
	@Transactional
	@RequestMapping(value = "/verifyCardNo", method = RequestMethod.POST, consumes = "application/json", produces="application/json")	
	public List <Borrower> verifyCardNo(@RequestBody Borrower borrower) {		
		List<Borrower> retborrower = new ArrayList<>();
		try {			
			retborrower = brdao.readBorrowerByPK(borrower.getCardNo());
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return retborrower;
	}
	
	@RequestMapping(value = "/readAllBooksBranch", method = RequestMethod.POST, consumes = "application/json", produces="application/json")
	public List <Book> readAllBooksBorrowed(@RequestBody BookBranch bookBranch){

		List <Book> books = new ArrayList<Book>();		
		try {
			books = bdao.readAllBooksBorrowed(bookBranch.getCardNo(), bookBranch.getBranchId());
					
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		return books;
	}
	@RequestMapping(value = "/readAllBooksCheckOut", method = RequestMethod.POST, consumes = "application/json", produces="application/json")
	public List <Book> readAllBooksForCheckOut(@RequestBody BookBranch bookBranch){

		List <Book> books = new ArrayList<Book>();		
		try {
			books = bdao.readAllBooksForCheckOut(bookBranch.getBranchId(), bookBranch.getCardNo());
					
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		return books;
	}
	@RequestMapping(value = "/processBookCheckOut", method = RequestMethod.POST, consumes = "application/json", produces="application/json")		
	public void processBookCheckOut( @RequestBody BookLoan bloan ) {
		
		try {			
			brdao.updateBookCopiesForCheckOut(bloan.getBookId(), bloan.getBranchId());
			brdao.updateBookLoansForCheckOut(bloan.getCardNo(), bloan.getBookId(), bloan.getBranchId());	
			System.out.println("Check Out Completed!");
		} catch (Exception e) {			
			e.printStackTrace();
		} 	
	}
	
	@RequestMapping(value = "/readAllBranchesborrowed", method = RequestMethod.POST, consumes = "application/json", produces="application/json")	
	public List<LibBranch> readAllBranchesborrowed(@RequestBody Borrower borrower) {
		
		List<LibBranch> branches = new ArrayList<>();
		try {			
			branches = ldao.readAllBranchesborrowed(borrower.getCardNo());		
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return branches;
	}
	
	@RequestMapping(value = "/readAllBranches", method = RequestMethod.GET, produces="application/json")	
	public List<LibBranch> readAllBranchesborrowed() {
		
		List<LibBranch> branches = new ArrayList<>();
		try {			
			branches = ldao.readAllBranches();		
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return branches;
	}

//	public List<Book> readAllBooksBorrowed(Integer cardNo, Integer branchId)  {
//		
//		List <Book> books = new ArrayList<Book>();		
//		try {			
//			books = bdao.readAllBooksBorrowed(cardNo, branchId);		
//		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} 
//		return books;
//	}
	@RequestMapping(value = "/bookReturn", method = RequestMethod.POST, consumes = "application/json", produces="application/json")
	public ErrorResponse processBookReturn(@RequestBody BookLoan bloan) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ErrorResponse resp = new ErrorResponse();
		try {			
			brdao.updateBookCopiesForReturn(bloan.getBookId(), bloan.getBranchId());
			brdao.updateBookLoansForReturn(bloan.getCardNo(), bloan.getBookId(), bloan.getBranchId());	
			resp.setErrorMessage("Book Return Completed!");
			resp.setStatus(Boolean.TRUE);
		} catch (SQLException e) {
			e.printStackTrace();
			resp.setErrorMessage("Error Occured while returning book");
			resp.setStatus(Boolean.FALSE);
		} 
		return resp;
	}
	
	
	
}
