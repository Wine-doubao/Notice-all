package admin.ainfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.alibaba.fastjson.JSONObject;

import entity.Administrator;
import entity.Notice;
import entity.User;

public class AinfoDAOImpl implements IAinfoDAO{
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	public AinfoDAOImpl(Connection conn) {
		this.conn = conn;
	}
	//获取用户信息
	public JSONObject getAinfo(String name) throws Exception{
		JSONObject obj = new JSONObject();
		QueryRunner runner = new QueryRunner();
		String sql = "select b.org_name,a.a_name,a.a_introduction,a.a_email,a.a_id from administrators as a,organization as b where a.a_id=? and a.org_id=b.org_id";
		BeanHandler<Administrator> handler = new BeanHandler<>(Administrator.class);
		Administrator admin = runner.query(conn, sql, handler, name);
	
		obj.put("info",admin);
		return obj;
	}
	//获取通知对象
	public JSONObject getNobj(String name) throws Exception{
		JSONObject obj = new JSONObject();
		QueryRunner runner = new QueryRunner();
		String sql = "select c.u_name,c.u_id from administrators as a,org_member as b,user_info as c where a.a_id=? and a.org_id=b.org_id and b.org_flag=1 and b.u_id=c.u_id";
		MapListHandler handler = new MapListHandler();
		List nobj = runner.query(conn, sql, handler, name);
	
		obj.put("nobj",nobj);
		return obj;
	}
	//发布通知
	public synchronized JSONObject Publish(Notice not,List<String> mem) throws Exception{
		JSONObject obj = new JSONObject();
		QueryRunner runner = new QueryRunner();
		try {
			String sql = "insert into notice values (null,?,?,?,?,?,?,?) ";
			runner.update(conn,sql,not.getTitle(),not.getContent(),not.getNtime(),not.getStime(),not.getEtime(),not.getPlace(),not.getAid());
			
			String sql1 = "select max(a.n_id) as max,b.org_id as orgid from notice as a,administrators as b where b.a_id=?";
			MapHandler handler1 = new MapHandler();
			Map<String, Object> map = runner.query(conn, sql1, handler1, not.getAid());
			for (String uid : mem) {
				String sql2 = "insert into accept values (?,?,?,0)";
				runner.update(conn,sql2,map.get("max"),uid,map.get("orgid"));
			}
			obj.put("flag", true);
		}catch(Exception e) {
			obj.put("flag", false);
		}
		
		return obj;
	}
	//获取管理成员页的信息
	public JSONObject getManage(String name) throws Exception{
		JSONObject obj = new JSONObject();
		QueryRunner runner = new QueryRunner();
		String sql = "select c.u_id as uid,c.u_name as name,c.u_email as email,b.org_apply as apply from administrators as a,org_member as b,user_info as c where a.org_id=b.org_id and a.a_id=? and b.u_id=c.u_id and b.org_flag=0";
		MapListHandler handler = new MapListHandler();
		List verify = runner.query(conn, sql, handler, name);
	
		String sql1 = "select c.u_id as uid,c.u_name as name,c.u_introduction as intro from administrators as a,org_member as b,user_info as c where a.org_id=b.org_id and a.a_id=? and b.u_id=c.u_id and b.org_flag=1";
		MapListHandler handler1 = new MapListHandler();
		List allmem = runner.query(conn, sql1, handler1, name);
		
		obj.put("verify",verify);
		obj.put("allmem",allmem);
		return obj;
	}
	//获取往期通知
	public JSONObject getPrevious(String name) throws Exception{
		JSONObject obj = new JSONObject();
		QueryRunner runner = new QueryRunner();
		String sql = "select a.title,a.content,a.release_time as time,a.start_time as startTime,a.end_time as endTime,a.place,count(b.see=2 or null) as do from notice as a,accept as b where a.a_id=? and b.n_id=a.n_id group by a.n_id";
		MapListHandler handler = new MapListHandler();
		List pre = runner.query(conn, sql, handler, name);
	
		obj.put("pre",pre);
		return obj;
	}
	//删除组织成员
	public JSONObject delete(String name,String uid) throws Exception{
		JSONObject obj = new JSONObject();
		QueryRunner runner = new QueryRunner();
		try {
			String sql = "delete a.* from org_member a,administrators b where u_id=? and a.org_id=b.org_id and b.a_id=?";
			runner.update(conn,sql,uid,name);//返回影响记录的条数
			obj.put("flag", true);
		}catch(Exception e){
			obj.put("flag", false);
		}
		return obj;
	}
	//是否同意该成员加入组织
	public JSONObject isAgree(String name,String flag,String uid) throws Exception{
		JSONObject obj = new JSONObject();
		QueryRunner runner = new QueryRunner();
		try {
			if(flag.equals("true")) {
				String sql ="update org_member as a,administrators as b set a.org_flag=1 where a.u_id=? and a.org_id=b.org_id and b.a_id=?";
				runner.update(conn,sql,uid,name);
			}else {
				String sql1 = "delete a.* from org_member a,administrators b where u_id=? and a.org_id=b.org_id and b.a_id=?";
				runner.update(conn,sql1,uid,name);
			}
			obj.put("flag", true);
		}catch(Exception e){
			e.printStackTrace();
			obj.put("flag", false);
		}
		return obj;
	}
}
