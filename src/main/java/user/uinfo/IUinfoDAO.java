package user.uinfo;

import com.alibaba.fastjson.JSONObject;

public interface IUinfoDAO {
	public JSONObject getUinfo(String name) throws Exception;
	public JSONObject getAllmes(String name) throws Exception;
	public JSONObject getNotices(String name,String org) throws Exception;
	public JSONObject getSetDone(String name,String nid) throws Exception;
	public JSONObject getOrgName(String name) throws Exception;
	public JSONObject postAttend(String name, String org, String reason) throws Exception;
}
