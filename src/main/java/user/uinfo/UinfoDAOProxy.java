package user.uinfo;

import com.alibaba.fastjson.JSONObject;

import common.DatabaseConnection;

public class UinfoDAOProxy implements IUinfoDAO {
	private DatabaseConnection dbc = null;
	private IUinfoDAO dao = null;
	public UinfoDAOProxy() {
		try {
			this.dbc = new DatabaseConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.dao = new UinfoDAOImpl(dbc.getConnection());
	}
	//获取用户信息
	public JSONObject getUinfo(String name) throws Exception{
		JSONObject obj = null;
		try {
			obj = this.dao.getUinfo(name);
		}catch(Exception e) {
			throw e;
		}
		return obj;
	}
	public JSONObject getAllmes(String name) throws Exception{
		JSONObject obj = null;
		try {
			obj = this.dao.getAllmes(name);
		}catch(Exception e) {
			throw e;
		}
		return obj;
	}
	public JSONObject getNotices(String name,String org) throws Exception{
		JSONObject obj = null;
		try {
			obj = this.dao.getNotices(name,org);
		}catch(Exception e) {
			throw e;
		}
		return obj;
	}
	public JSONObject getSetDone(String name,String nid) throws Exception{
		JSONObject obj = null;
		try {
			obj = this.dao.getSetDone(name,nid);
		}catch(Exception e) {
			throw e;
		}
		return obj;
	}
	public JSONObject getOrgName(String name) throws Exception{
		JSONObject obj = null;
		try {
			obj = this.dao.getOrgName(name);
		}catch(Exception e) {
			throw e;
		}
		return obj;
	}
	public JSONObject postAttend(String name, String org, String reason) throws Exception{
		JSONObject obj = null;
		try {
			obj = this.dao.postAttend(name,org,reason);
		}catch(Exception e) {
			throw e;
		}
		return obj;
	}
}
