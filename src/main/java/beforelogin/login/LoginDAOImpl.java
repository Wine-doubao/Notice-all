package beforelogin.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.alibaba.fastjson.JSONObject;

public class LoginDAOImpl  implements ILoginDAO{
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	public LoginDAOImpl(Connection conn) {
		this.conn = conn;
	}
	public JSONObject patch(String name,String psw,String check) throws Exception{
		JSONObject obj = new JSONObject();
		String sql;
		boolean isAdmin;
		
		if(check.equals("true")) {
			isAdmin=true;
			sql = "select a_id from ad_account where a_id=? and a_password=?";
		}else {
			isAdmin=false;
			sql = "select u_id from user_account where u_id=? and u_password=?";
		}
		
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setString(1,name);
		this.pstmt.setString(2,psw);
		ResultSet rs = this.pstmt.executeQuery();

		if(rs.next()) {
			obj.put("name",rs.getString(1));
			obj.put("flag",true);
			obj.put("isAdmin", isAdmin);
		}else {
			obj.put("flag", false);
		}
		rs.close();
		this.pstmt.close();
		return obj;
	}
}
