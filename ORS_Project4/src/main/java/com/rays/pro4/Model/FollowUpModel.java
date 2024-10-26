package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

//import org.apache.log4j.Logger;

import com.rays.pro4.Bean.FollowUpBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

/**
 * JDBC Implementation of UserModel.
 * 
 * @author Madhumita Rajarwal
 *
 */

public class FollowUpModel {
	private static Logger log = Logger.getLogger(UserModel.class);

	/**
	 * Find next PK of User
	 *
	 * @throws DatabaseException
	 */

	public int nextPK() throws DatabaseException {

		log.debug("Model nextPK Started");

		String sql = " select max(id) from st_followup ";
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {

			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK Started");
		return pk + 1;

	}

	/**
	 * Add a User
	 *
	 * @param bean
	 * @throws DatabaseException
	 *
	 */

	public long add(FollowUpBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");

		String sql = " insert into st_followup values(?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getPatient());
			pstmt.setString(3, bean.getDoctor());
			pstmt.setDate(4, new java.sql.Date(bean.getVisitDate().getTime()));
			pstmt.setLong(5, bean.getFees());
			
			int a = pstmt.executeUpdate();
			System.out.println(a);
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception ...", e);
			try {
				e.printStackTrace();
				conn.rollback();

			} catch (Exception e2) {
				e2.printStackTrace();
				// application exception
				throw new ApplicationException("Exception : add rollback exceptionn" + e2.getMessage());
			}
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Add End");
		return pk;

	}

	/**
	 * Delete a User
	 *
	 * @param bean
	 * @throws DatabaseException
	 */
	public void delete(FollowUpBean bean) throws ApplicationException {
		log.debug("Model delete start");
		String sql = "delete from st_followup where id=?";
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			log.error("DataBase Exception", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: Delete rollback Exception" + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Delete End");
	}

	/**
	 * Find User by Login
	 *
	 * @param login : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */

	

	/**
	 * Find User by PK
	 *
	 * @param pk : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */

	
	public FollowUpBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findBy PK start");
		String sql = "select * from st_followup where id=?";
		FollowUpBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new FollowUpBean();
				bean.setId(rs.getLong(1));
				bean.setPatient(rs.getString(2));
				bean.setDoctor(rs.getString(3));
				bean.setVisitDate(rs.getDate(4));
				bean.setFees(rs.getLong(5));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DataBase Exception ", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Method Find By PK end");
		return bean;
	}

	/**
	 * Update a user
	 *
	 * @param bean
	 * @throws DatabaseException
	 */

	public void update(FollowUpBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model Update Start");
		String sql = "update st_followup set patient=?, doctor=?,visit_date=? , fees=? where id=?";
		Connection conn=null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, bean.getPatient());
			pstmt.setString(2, bean.getDoctor());
			pstmt.setDate(3, new java.sql.Date(bean.getVisitDate().getTime()));
			pstmt.setLong(4, bean.getFees());
			pstmt.setLong(5, bean.getId());
			int i = pstmt.executeUpdate();
			System.out.println("update user>> " + i);
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DataBase Exception", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new ApplicationException("Exception : Update Rollback Exception " + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Update End ");
	}

	
	public List search(FollowUpBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	
	public List search(FollowUpBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model Search Start");
		StringBuffer sql = new StringBuffer("select * from st_followup where 1=1");
		if (bean != null) {

			if (bean.getPatient() != null && bean.getPatient().length() > 0) {
				sql.append(" and patient like '" + bean.getPatient() + "%'");
			}
			if (bean.getDoctor() != null && bean.getDoctor().length() > 0) {
				sql.append(" and doctor like '" + bean.getDoctor() + "%'");
			}
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getVisitDate() != null && bean.getVisitDate().getTime() > 0) {
				System.out.println("done");
				Date d = new Date(bean.getVisitDate().getTime());
				sql.append(" and visit_date = '" + d+ "'");
				
			}
			if (bean.getFees() != null && bean.getFees() > 0) {
				sql.append(" and fees = " + bean.getFees());
			}
		}
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}
		System.out.println("sql query search >>= " + sql.toString());
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new FollowUpBean();
				bean.setId(rs.getLong(1));
				bean.setPatient(rs.getString(2));
				bean.setDoctor(rs.getString(3));
				bean.setVisitDate(rs.getDate(4));
				bean.setFees(rs.getLong(5));
				

				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new ApplicationException("Exception: Exception in Search User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Search end");
		return list;

	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_followup");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				FollowUpBean bean = new FollowUpBean();
				bean.setId(rs.getLong(1));
				bean.setPatient(rs.getString(2));
				bean.setDoctor(rs.getString(3));
				bean.setVisitDate(rs.getDate(4));
				bean.setFees(rs.getLong(5));

				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception...", e);
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list End");
		return list;
	}
}