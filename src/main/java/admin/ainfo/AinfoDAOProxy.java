package admin.ainfo;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import common.DatabaseConnection;
import entity.Notice;
import user.uinfo.IUinfoDAO;
import user.uinfo.UinfoDAOImpl;

public class AinfoDAOProxy implements IAinfoDAO{
	private DatabaseConnection dbc = null;
	private IAinfoDAO dao = null;
	public AinfoDAOProxy() {
		try {
			this.dbc = new DatabaseConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.dao = new AinfoDAOImpl(dbc.getConnection());
	}
	//获取用户信息
	public JSONObject getAinfo(String name) throws Exception{
		JSONObject obj = null;
		try {
			obj = this.dao.getAinfo(name);
		}catch(Exception e) {
			throw e;
		}
		return obj;
	}
	public JSONObject getNobj(String name) throws Exception{
		JSONObject obj = null;
		try {
			obj = this.dao.getNobj(name);
		}catch(Exception e) {
			throw e;
		}
		return obj;
	}
	public JSONObject Publish(Notice not,List<String> mem) throws Exception{
		JSONObject obj = null;
		try {
			obj = this.dao.Publish(not,mem);
		}catch(Exception e) {
			throw e;
		}
		return obj;
	}
	public JSONObject getManage(String name) throws Exception{
		JSONObject obj = null;
		try {
			obj = this.dao.getManage(name);
		}catch(Exception e) {
			throw e;
		}
		return obj;
	}
	public JSONObject getPrevious(String name) throws Exception{
		JSONObject obj = null;
		try {
			obj = this.dao.getPrevious(name);
		}catch(Exception e) {
			throw e;
		}
		return obj;	
	}
	public JSONObject delete(String name,String uid) throws Exception{
		JSONObject obj = null;
		try {
			obj = this.dao.delete(name,uid);
		}catch(Exception e) {
			throw e;
		}
		return obj;	
	}
	public JSONObject isAgree(String name,String flag,String uid) throws Exception{
		JSONObject obj = null;
		try {
			obj = this.dao.isAgree(name,flag,uid);
		}catch(Exception e) {
			throw e;
		}
		return obj;	
	}
}
