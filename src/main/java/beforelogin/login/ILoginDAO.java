package beforelogin.login;

import com.alibaba.fastjson.JSONObject;

public interface ILoginDAO {
	public JSONObject patch(String name,String psw,String check) throws Exception;
}
