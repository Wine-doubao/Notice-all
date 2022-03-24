package admin.ainfo;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import entity.Notice;

public interface IAinfoDAO {
	public JSONObject getAinfo(String name) throws Exception;
	public JSONObject getNobj(String name) throws Exception;
	public JSONObject Publish(Notice not,List<String> mem) throws Exception;
	public JSONObject getManage(String name) throws Exception;
	public JSONObject getPrevious(String name) throws Exception;
	public JSONObject delete(String name,String uid) throws Exception;
	public JSONObject isAgree(String name,String flag,String uid) throws Exception;
}
