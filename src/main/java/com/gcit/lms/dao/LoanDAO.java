/**
 * 
 */
package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gcit.lms.entity.BookLoan;


/**
 * @author awene
 *
 */
public class LoanDAO extends BaseDAO<BookLoan> {
	
	
//	public List<BookLoan> readAllActiveBorrowStatus() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		return read("Select * from tbl_book_loans where dateIn is null", null);
//		
//	}
//
//	@Override
//	public List<BookLoan> extractData(ResultSet rs)
//			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
//		List<BookLoan> bookloans = new ArrayList<>();
//		while (rs.next()) {
//			BookLoan bookloan = new BookLoan();
//			bookloan.setCardNo(rs.getInt("cardNo"));
//			bookloan.setBookId(rs.getInt("bookId"));
//			bookloan.setBranchId(rs.getInt("branchId"));
//			bookloan.setDueDate(rs.getDate("dueDate"));
//			bookloan.setDateout(rs.getDate("dateOut"));
//			bookloan.setDateIn(rs.getDate("dateIn"));
//			bookloans.add(bookloan);
//		}			
//		return bookloans;	
//	}
//	
//
//	public void updateDueDate(BookLoan bookLoan, Date date) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		save("update tbl_book_loans set dueDate = ? where cardNo = ? and bookId = ? and branchId = ? and dateOut = ?", new Object[] {date, bookLoan.getCardNo(), bookLoan.getBookId(), bookLoan.getBranchId(), bookLoan.getDateout()});
//	}
}
