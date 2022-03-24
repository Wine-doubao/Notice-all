package beforelogin.login;

import com.alibaba.fastjson.JSONObject;

import common.DatabaseConnection;


public class LoginDAOProxy implements ILoginDAO{
	private DatabaseConnection dbc = null;
	private ILoginDAO dao = null;
	public LoginDAOProxy() {
		try {
			this.dbc = new DatabaseConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.dao = new LoginDAOImpl(dbc.getConnection());
	}
	public JSONObject patch(String name,String psw,String check) throws Exception{
		JSONObject obj = null;
		try {
			obj = this.dao.patch(name,psw,check);
		}catch(Exception e) {
			throw e;
		}
		return obj;
	}
}
