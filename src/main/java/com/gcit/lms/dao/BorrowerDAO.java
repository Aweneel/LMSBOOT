package com.gcit.lms.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.gcit.lms.entity.Borrower;

@Component
public class BorrowerDAO extends BaseDAO<Borrower> implements ResultSetExtractor<List<Borrower>> {


	public void addBorrower(Borrower borrower)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("insert into tbl_borrower (name, address, phone) values(?, ?, ?)", new Object[] {borrower.getName(),borrower.getAddress(), borrower.getPhone()});
	}

	public void editBorrower(Borrower borrower)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?", new Object[]{borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getCardNo()});
	}
	
	public void deleteBorrower(Borrower borrower)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("delete from tbl_borrower where cardNo = ?", new Object[]{borrower.getCardNo()});
	}

	public List<Borrower> readAllBorrowers()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select * from tbl_borrower", this);
	}
		
	public List<Borrower> readAllBorrowersByName(String searchString)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select * from tbl_borrower where name like ?", new Object[]{"%" + searchString + "%"}, this);
	}
	
	
	public List<Borrower> readBorrowerByPK(Integer pk)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select * from tbl_borrower where cardNo = ?", new Object[]{pk}, this);
		
	}

	@Override
	public List<Borrower> extractData(ResultSet rs)
			throws SQLException{
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower borrow = new Borrower();
			borrow.setCardNo(rs.getInt("cardNo"));
			borrow.setName(rs.getString("name"));
			borrow.setAddress(rs.getString("address"));
			borrow.setPhone(rs.getString("phone"));
			borrowers.add(borrow);
		}			
		return borrowers;	
	}

		
	public void updateBookCopiesForCheckOut(Integer bookId, Integer branchId )
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("update tbl_book_copies set noOfCopies = noOfCopies-1 where bookId = ? and branchId = ?", new Object[]{bookId, branchId});
	}
	public void updateBookLoansForCheckOut(Integer cardNo, Integer bookId, Integer branchId )
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) values (?, ?, ?, now(), DATE_ADD(NOW(), INTERVAL 7 DAY))", new Object[]{bookId, branchId, cardNo});
	}

	public void updateBookCopiesForReturn(Integer bookId, Integer branchId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("update tbl_book_copies set noOfCopies = noOfCopies+1 where bookId = ? and branchId = ?", new Object[]{bookId, branchId});
		
	}

	public void updateBookLoansForReturn(Integer cardNo, Integer bookId, Integer branchId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("update tbl_book_loans set dateIn = now() where bookId = ? and branchId = ? and cardNo = ?", new Object[]{bookId, branchId, cardNo});
		
	}

}
