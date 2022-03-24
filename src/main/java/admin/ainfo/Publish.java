package admin.ainfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import common.DAOFactory;
import entity.Notice;

public class Publish extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
				
		PrintWriter out = response.getWriter();
		Cookie[] cookies = request.getCookies();
		String name = cookies[1].getValue();
//		Enumeration enu=request.getParameterNames();  
//		while(enu.hasMoreElements()){  
//			String paraName=(String)enu.nextElement();  
//			System.out.println(paraName+": "+request.getParameter(paraName));  
//		}

		Notice not = new Notice();
		not.setAid(name);
		not.setContent(request.getParameter("content"));
		not.setPlace(request.getParameter("place"));
		not.setTitle(request.getParameter("title"));
		List<String> mem = Arrays.asList(request.getParameter("mem").split(","));
		try {
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			//ft.format将时间转成年月日型的字符串，valueOf将其转成Date型的年月日格式
			not.setEtime(java.sql.Date.valueOf(ft.format(ft.parse(request.getParameter("etime")))));
			not.setNtime(java.sql.Date.valueOf(ft.format(ft.parse(request.getParameter("ntime")))));
			not.setStime(java.sql.Date.valueOf(ft.format(ft.parse(request.getParameter("stime")))));
		}catch(ParseException e) {
			e.printStackTrace();
		}
				
		try {
			JSONObject obj = DAOFactory.getIAinfoDAOInstance().Publish(not,mem);
			if(obj!=null) {
				out.write(obj.toString());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}

