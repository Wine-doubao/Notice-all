package user.uinfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.alibaba.fastjson.JSONObject;

import entity.User;

public class UinfoDAOImpl implements IUinfoDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	public UinfoDAOImpl(Connection conn) {
		this.conn = conn;
	}
	//获取用户信息
	public JSONObject getUinfo(String name) throws Exception{
		JSONObject obj = new JSONObject();
		QueryRunner runner = new QueryRunner();
		String sql = "select u_id,u_name,u_email,u_introduction from user_info where u_id=?";
		BeanHandler<User> handler = new BeanHandler<>(User.class);
		User user = runner.query(conn, sql, handler, name);
		
		String sql1 = "select d.org_name,count(*) as count from accept as c,organization as d where c.u_id=? and c.org_id in (select a.org_id from org_member as a,organization as b where u_id=? and a.org_id=b.org_id and a.org_flag=1) and c.org_id=d.org_id group by d.org_name";
		MapListHandler handler1 = new MapListHandler();
		List<Map<String, Object>> orgs = runner.query(conn, sql1, handler1, name, name);
		
		obj.put("info",user);
		obj.put("orgs",orgs);
		return obj;
	}
	
	//获取未办通知
	public JSONObject getAllmes(String name) throws Exception{
		JSONObject obj = new JSONObject();
		QueryRunner runner = new QueryRunner();
		String sql = "select count(c.org_name) as count,c.org_name from notice as a,accept as b,organization as c where b.u_id=? and b.org_id=c.org_id and b.see<>2 and b.n_id=a.n_id group by org_name order by c.org_name desc";
		MapListHandler handler = new MapListHandler();
		List<Map<String, Object>> ncount = runner.query(conn, sql, handler, name);
		
		String sql1 = "select a.content from notice as a,accept as b,organization as c where b.u_id=? and b.org_id=c.org_id and b.see<>2 and b.n_id=a.n_id order by c.org_name desc";
		MapListHandler handler1 = new MapListHandler();
		List<Map<String, Object>> content = runner.query(conn, sql1, handler1, name);
		
		String sql2 = "select count(c.org_name) as count,c.org_name from notice as a,accept as b,organization as c where b.u_id=? and b.org_id=c.org_id and b.see=2 and b.n_id=a.n_id group by org_name order by c.org_name desc";
		MapListHandler handler2 = new MapListHandler();
		List<Map<String, Object>> ncount2 = runner.query(conn, sql2, handler2, name);
		
		String sql3 = "select a.content from notice as a,accept as b,organization as c where b.u_id=? and b.org_id=c.org_id and b.see=2 and b.n_id=a.n_id order by c.org_name desc";
		MapListHandler handler3 = new MapListHandler();
		List<Map<String, Object>> content2 = runner.query(conn, sql3, handler3, name);
		
		obj.put("ncount", ncount);
		obj.put("content", content);
		obj.put("ncount2", ncount2);
		obj.put("content2", content2);
		return obj;
	}
	//获取该组织的通知
	public JSONObject getNotices(String name,String org) throws Exception{
		JSONObject obj = new JSONObject();
		QueryRunner runner = new QueryRunner();
		String sql = "select a.a_name as author,b.title,b.content,b.release_time as relDate,b.start_time as startDate,b.end_time as endDate,b.place,c.see,b.n_id from administrators as a,notice as b,accept as c,organization as d where c.u_id=? and c.n_id=b.n_id and b.a_id=a.a_id and d.org_id=c.org_id and d.org_name=? order by b.release_time desc";
		MapListHandler handler = new MapListHandler();
		List<Map<String, Object>> notices = runner.query(conn, sql, handler, name, org);
		
		obj.put("notice", notices);
		return obj;
	}
	//修改通知状态为已办
	public JSONObject getSetDone(String name,String nid) throws Exception{
		JSONObject obj = new JSONObject();
		QueryRunner runner = new QueryRunner();
		String sql = "update accept set see=2 where u_id=? and n_id=?";
		int row = runner.update(conn,sql,name,nid);//返回影响记录的条数
		
		if(row==1) {
			obj.put("flag", true);
		}else {
			obj.put("flag", false);
		}
		
		return obj;
	}
	//获取没有加入的组织名，给申请加入页面
	public JSONObject getOrgName(String name) throws Exception{
		JSONObject obj = new JSONObject();
		QueryRunner runner = new QueryRunner();
		String sql = "select org_name as label from organization where org_id not in (select a.org_id from org_member as a,organization as b where a.u_id=? and a.org_id=b.org_id and a.org_flag=1)";
		MapListHandler handler = new MapListHandler();
		List label = runner.query(conn, sql, handler, name);
		
		String sql1 = "select count(*) as total from organization";
		this.pstmt = this.conn.prepareStatement(sql1);
		ResultSet rs = this.pstmt.executeQuery();

		if(rs.next()) {
			obj.put("total", rs.getInt(1));
		}
		obj.put("label", label);
		rs.close();
		this.pstmt.close();
		return obj;
	}
	//提交申请加入
	public JSONObject postAttend(String name, String org, String reason) throws Exception{
		JSONObject obj = new JSONObject();
		QueryRunner runner = new QueryRunner();
		String sql = "select count(*) as count from org_member as a,organization as b where a.u_id=? and a.org_id=b.org_id and b.org_name=?";
		ScalarHandler handler = new ScalarHandler();
		Number count =(Number) runner.query(conn, sql, handler, name, org);
		
		if(count.intValue()==0) {//如果没有申请过
			String sql1 = "insert into org_member values (?,(select org_id from organization where org_name=?),?,0) ";
			runner.update(conn,sql,name, org, reason);
			obj.put("flag", true);
			obj.put("mes","已申请，请耐心等待管理员审核！");
		}else if(count.intValue() == 1){//如果已经申请过
			String sql2 = "update org_member as a,organization as b set a.org_apply=? where a.u_id=? and a.org_id=b.org_id and b.org_name=?";
			runner.update(conn,sql2,reason,name,org);
			obj.put("flag", true);
			obj.put("mes","已申请，请耐心等待管理员审核！");
		}else {
			obj.put("flag",false);
		}

		return obj;
	}
}
