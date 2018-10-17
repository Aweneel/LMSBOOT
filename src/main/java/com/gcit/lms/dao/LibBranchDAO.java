package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.LibBranch;

@Component
public class LibBranchDAO extends BaseDAO<LibBranch> implements ResultSetExtractor<List<LibBranch>> {
	
	
	public void addBranch(LibBranch branch)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("insert into tbl_library_branch (branchName, branchAddress) values(?, ?)", new Object[] { branch.getBranchName(), branch.getBranchAddress() });
	}
	

	public void editBranch(LibBranch branch)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?", new Object[]{branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId()});
	}

	public void deleteBranch(LibBranch branch)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("delete from tbl_library_branch where branchId = ?", new Object[]{branch.getBranchId()});
	}
	
	public void editNoOfCopies(BookCopies bcopies, Integer newCopies) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("update tbl_book_copies set noOfCopies = noOfCopies + ? where bookId = ? and branchId = ?", new Object[] {newCopies, bcopies.getBookId(),bcopies.getBranchId()});
	}
	public List<LibBranch> readAllBranches()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select * from tbl_library_branch", this);
	}
	public List<LibBranch> readAllBranchesborrowed(Integer cardNo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select distinct a.* from tbl_library_branch a inner join tbl_book_loans b on a.branchId = b.branchId where b.dateIn is null and b.cardNo = ?", new Object[] {cardNo}, this);
	}
	
	@Override
	public List<LibBranch> extractData(ResultSet rs) throws SQLException{
			List<LibBranch> branches = new ArrayList<>();
			while (rs.next()) {
				LibBranch branch = new LibBranch();
				branch.setBranchId(rs.getInt("branchId"));
				branch.setBranchName(rs.getString("branchName"));
				branch.setBranchAddress(rs.getString("branchAddress"));
				branches.add(branch);
			}			
			return branches;	
	}
	

//	public Integer findBookCopiesInABranch(BookCopies copies) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		return readAFieldInARow("select count(*) as count from tbl_book_copies where bookId = ? and branchId = ?", new Object[] {copies.getBookId(),copies.getBranchId()}, "count");
//	}

	public void addBookCopyRecord(BookCopies bcopies) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("insert into tbl_book_copies (bookId, branchId, noOfCopies) values(?, ?, ?)", new Object[] { bcopies.getBookId(), bcopies.getBranchId(), bcopies.getNoOfCopies()});
	}

	public List<LibBranch> readBranchByPK(Integer branchId)
		throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select * from tbl_library_branch where branchId = ?", new Object[]{branchId}, this);
			
	}
	
}
