package beforelogin.index;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import entity.Administrator;
import entity.Notice;

public class IntroDAOImpl implements IIntroDAO{
	private Connection conn = null;
	public IntroDAOImpl(Connection conn) {
		this.conn = conn;
	}
	public JSONObject getIntro() throws Exception{
		JSONObject res = new JSONObject();
		QueryRunner runner = new QueryRunner();
		String sql = "select a.org_name,b.a_id,b.a_name,b.a_introduction,b.a_email from organization as a,administrators as b where a.org_id=b.org_id limit 3";
		BeanListHandler<Administrator> handler = new BeanListHandler<>(Administrator.class);
		List<Administrator> list = runner.query(conn, sql, handler);
			
		String sql1 = "select a.content,b.org_name,c.a_name from notice as a,organization as b,administrators as c where a.a_id=c.a_id and b.org_id=c.org_id group by c.a_name limit 4 ";
		MapListHandler handler1 = new MapListHandler();
		List<Map<String, Object>> list1 = runner.query(conn, sql1, handler1);
		
		res.put("intro",list);
		res.put("notice",list1);
		return res;
	}
}
