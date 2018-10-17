package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.LibBranch;
import com.gcit.lms.dao.BookDAO;

import com.gcit.lms.dao.LibBranchDAO;;

public class LibraryService {
//	ConnectionUtil connUtil = new ConnectionUtil();
//	
//	public List<LibBranch> getAllBranches() throws SQLException{
//		Connection conn = null;
//		List<LibBranch> allBranches = new ArrayList<>();
//		try {
//			conn = connUtil.getConnection();
//			LibBranchDAO ldao = new LibBranchDAO(conn);
//			allBranches = ldao.readAllBranches();			
//			conn.commit();
//		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
//			if(conn!=null){
//				conn.rollback();
//			}
//			e.printStackTrace();
//		} finally{
//			if(conn!=null){
//				conn.close();
//			}
//		}
//		if(allBranches == null)
//			System.out.println("No result");
//		return allBranches; 
//	}
//	
//	public void updateBranch(LibBranch branch) throws SQLException {
//		Connection conn = null;
//		
//		try {
//			conn = connUtil.getConnection();
//			LibBranchDAO ldao = new LibBranchDAO(conn);			
//			ldao.editBranch(branch);			
//			conn.commit();
//			System.out.println("Successfully Updated!!");
//		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
//			if(conn!=null){
//				conn.rollback();
//			}
//			e.printStackTrace();
//		} finally{
//			if(conn!=null){
//				conn.close();
//			}
//		}		
//	}
//	
//	public List <Book> readAllBooks() throws SQLException{
//		
//		Connection conn = null;
//		List <Book> books = new ArrayList<Book>();		
//		try {
//			conn = connUtil.getConnection();
//			BookDAO bdao = new BookDAO(conn);
//			books = bdao.readAllBooks();
//			conn.commit();			
//		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
//			if(conn!=null){
//				conn.rollback();
//			}
//			e.printStackTrace();
//		} finally{
//			if(conn!=null){
//				conn.close();
//			}
//		}
//		return books;
//	}
//
//	
//	public Integer findNoOfCopies(BookCopies copies) throws SQLException {
//		Connection conn = null;
//		Integer noOfCopies = null;	
//		try {
//			conn = connUtil.getConnection();
//			LibBranchDAO ldao = new LibBranchDAO(conn);
//			 noOfCopies = ldao.findBookCopiesInABranch(copies);
//			conn.commit();			
//		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
//			if(conn!=null){
//				conn.rollback();
//			}
//			e.printStackTrace();
//		} finally{
//			if(conn!=null){
//				conn.close();
//			}
//		}
//		return noOfCopies;
//	}
//
//	public void updateNoOfCopies(BookCopies bcopies, Integer newCopies) throws SQLException {
//		Connection conn = null;		
//		try {
//			conn = connUtil.getConnection();
//			LibBranchDAO ldao = new LibBranchDAO(conn);
//			ldao.editNoOfCopies(bcopies, newCopies);
//			conn.commit();		
//			System.out.println("Copies added Successfully!");
//		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
//			if(conn!=null){
//				conn.rollback();
//			}
//			e.printStackTrace();
//		} finally{
//			if(conn!=null){
//				conn.close();
//			}
//		}		
//	}
//
//	public void addBookCopyRecord(BookCopies bcopies) throws SQLException {
//		Connection conn = null;		
//		try {
//			conn = connUtil.getConnection();
//			LibBranchDAO ldao = new LibBranchDAO(conn);
//			ldao.addBookCopyRecord(bcopies);
//			conn.commit();		
//		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
//			if(conn!=null){
//				conn.rollback();
//			}
//			e.printStackTrace();
//		} finally{
//			if(conn!=null){
//				conn.close();
//			}
//		}		
//		
//	}
//
//	public LibBranch verifyBranchId(Integer branchId) throws SQLException {
//		Connection conn = null;
//		LibBranch branch = new LibBranch();
//		try {
//			conn = connUtil.getConnection();
//			LibBranchDAO ldao = new LibBranchDAO(conn);
//			branch = ldao.readBranchByPK(branchId);
//			conn.commit();
//		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
//			if(conn!=null){
//				conn.rollback();
//			}
//			e.printStackTrace();
//		} finally{
//			if(conn!=null){
//				conn.close();
//			}
//		}
//		return branch;
//	}
}