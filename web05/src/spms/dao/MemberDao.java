package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import spms.util.DBConnectionPool;
import spms.vo.Member;

public class MemberDao {

	DBConnectionPool connPool;
	
	public void setDbConeectionPool(DBConnectionPool connPool) {
		this.connPool = connPool;
	}

	public List<Member> selectList() throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			connection = connPool.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select mno , mname, email , cre_date from members order by mno asc");

			ArrayList<Member> members = new ArrayList<>();

			while (rs.next()) {
				members.add(new Member().setNo(rs.getInt("mno")).setName(rs.getString("mname"))
						.setEmail(rs.getString("email")).setCreatedDate(rs.getDate("cre_date")));
			}

			return members;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
		}
	}

	public int insert(Member member) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;

		try {
			connection = connPool.getConnection();
			stmt = connection.prepareStatement(
					"insert into members(email,pwd,name,cre_date, mod_date) values(?,?,?,now(),now() )");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getName());
			return stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
		}

	}

	public int delete(int no) throws Exception {
		Statement stmt = null;
		Connection connection = null;
		try {
			connection = connPool.getConnection();
			stmt = connection.createStatement();
			return stmt.executeUpdate("delete from members where mno=" + no);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
		}

	}

	public Member selectOne(int no) throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			connection = connPool.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select mno, email, mname, cre_date from members where mno=" + no);
			if (rs.next()) {
				return new Member().setNo(rs.getInt("MNO")).setEmail(rs.getString("EMAIL"))
						.setName(rs.getString("MNAME")).setCreatedDate(rs.getDate("CRE_DATE"));
			} else {
				throw new Exception("해당 번호의 회원을 찾을 수 없습니다.");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
		}
	}

	public int update(Member member) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = connPool.getConnection();
			stmt = connection.prepareStatement("UPDATE MEMBERS SET EMAIL=?,MNAME=?,MOD_DATE=now()" + " WHERE MNO=?");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getName());
			stmt.setInt(3, member.getNo());
			return stmt.executeUpdate();

		} catch (Exception e) {
			throw e;

		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
		}
	}

	public Member exist(String email, String password) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			connection = connPool.getConnection();
			stmt = connection.prepareStatement("SELECT MNAME,EMAIL FROM MEMBERS" + " WHERE EMAIL=? AND pwd=?");
			stmt.setString(1, email);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return new Member().setName(rs.getString("MNAME")).setEmail(rs.getString("EMAIL"));
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
		}
	}

}
