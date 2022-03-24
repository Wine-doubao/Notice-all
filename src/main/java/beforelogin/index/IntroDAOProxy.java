package beforelogin.index;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import common.DatabaseConnection;

public class IntroDAOProxy implements IIntroDAO{
	private DatabaseConnection dbc = null;
	private IIntroDAO dao = null;
	public IntroDAOProxy() {
		try {
			this.dbc = new DatabaseConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.dao = new IntroDAOImpl(dbc.getConnection());
	}
	public JSONObject getIntro() throws Exception{
		JSONObject obj = null;
		try {
			obj = this.dao.getIntro();
		}catch(Exception e) {
			throw e;
		}
		return obj;
	}
}
