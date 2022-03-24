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

public class Attend extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
				
		PrintWriter out = response.getWriter();
		Cookie[] cookies = request.getCookies();
		String name = cookies[1].getValue();//cookie中的账号
		String org = request.getParameter("uorg");
		String reason = request.getParameter("ureason");
//		System.out.println(name);
//		System.out.println(org);
//		System.out.println(reason);
		try {
			JSONObject obj = DAOFactory.getIUinfoDAOInstance().postAttend(name, org, reason);
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
