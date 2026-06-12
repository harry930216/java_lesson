package tw.harry.apis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MemberDaoImpl implements MemberDao{
	private static final String URL = "jdbc:mysql://localhost:3306/brad";
	private static final String USER = "root";
	private static final String PASSWD = "root";
	
	private static final String SQL_ADD = """
			INSERT INTO member
			(account, passwd)
			VALUES
			(?,?)
			""";	
	@Override
	public boolean addMember(Member member) throws Exception {
		try(Connection conn = DriverManager.getConnection(URL, USER, PASSWD);
			PreparedStatement pstmt = conn.prepareStatement(SQL_ADD)){

			pstmt.setString(1, member.getAccount());
			pstmt.setString(2, BCrypt.hashpw(member.getPasswd(), BCrypt.gensalt()));
			return pstmt.executeUpdate() > 0;
		}
	}

	private static final String SQL_UPDATE = """
			UPDATE member
			SET passwd = ?
			WHERE id = ?
			""";	
	@Override
	public boolean updateMember(Member member) throws Exception {
		try(Connection conn = DriverManager.getConnection(URL, USER, PASSWD);
			PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE)){

			pstmt.setString(1, BCrypt.hashpw(member.getPasswd(), BCrypt.gensalt()));
			pstmt.setLong(2, member.getId());
			return pstmt.executeUpdate() > 0;
		}
	}

	private static final String SQL_DELETE = """
			DELETE FROM member
			WHERE id = ?
			""";	
	@Override
	public boolean delMember(Member member) throws Exception {
		try(Connection conn = DriverManager.getConnection(URL, USER, PASSWD);
			PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE)){

			pstmt.setLong(1, member.getId());
			return pstmt.executeUpdate() > 0;
		}
	}

	private static final String SQL_QUERY_ID = """
			SELECT id, account, passwd
			FROM member
			WHERE id = ?
			""";	
	@Override
	public Member findById(long id) throws Exception {
		try(Connection conn = DriverManager.getConnection(URL, USER, PASSWD);
			PreparedStatement pstmt = conn.prepareStatement(SQL_QUERY_ID)){
			pstmt.setLong(1, id);
			
			try(ResultSet rs = pstmt.executeQuery();){
				if (!rs.next()) {
					return new Member(rs.getLong("id"), rs.getString("account"), null);
				}
			}
			return null;
		}
	}

	private static final String SQL_QUERY_ALL = """
			SELECT id, account, passwd
			FROM member
			""";	
	@Override
	public List<Member> findAll() throws Exception {
		List<Member> list = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(URL, USER, PASSWD);
			PreparedStatement pstmt = conn.prepareStatement(SQL_QUERY_ALL)){
			
			try(ResultSet rs = pstmt.executeQuery();){
				while (rs.next()) {
					list.add(new Member(rs.getLong("id"), rs.getString("account"), null));
				}
			}
			return list;
		}
	}

	private static final String SQL_QUERY_ACCOUNT = """
			SELECT id, account, passwd
			FROM member
			WHERE account = ?
			""";	
	private Member findByAccount(String account) throws Exception{
		try(Connection conn = DriverManager.getConnection(URL, USER, PASSWD);
			PreparedStatement pstmt = conn.prepareStatement(SQL_QUERY_ACCOUNT)){
			pstmt.setString(1, account);
			
			try(ResultSet rs = pstmt.executeQuery();){
				if (!rs.next()) {
					return new Member(rs.getLong("id"), rs.getString("account"), rs.getString("passwd"));
				}
			}
			return null;
		}		
	}
	
	
	@Override
	public Member login(String account, String passwd) throws Exception {
		return null;
	}

}