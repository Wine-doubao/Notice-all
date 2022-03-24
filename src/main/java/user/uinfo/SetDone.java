package user.uinfo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import common.DAOFactory;

public class SetDone extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
				
		PrintWriter out = response.getWriter();
		Cookie[] cookies = request.getCookies();
		String nid = request.getParameter("id");
		String name="";
		name = cookies[1].getValue();
//		System.out.println(nid.getClass().toString());
				
		try {
			JSONObject obj = DAOFactory.getIUinfoDAOInstance().getSetDone(name,nid);
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
