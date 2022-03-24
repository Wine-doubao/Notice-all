package beforelogin.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;

import common.DAOFactory;


public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		String checked = request.getParameter("checked");
		
		try {
			JSONObject obj = DAOFactory.getILoginDAOInstance().patch(name,pass,checked);
			if(obj!=null) {
				if(obj.getString("flag").equals("true")) {
					//存入session
//					session.setAttribute("name",obj.getString("name"));
//					session.setMaxInactiveInterval(30*60);
					Cookie cookie = new Cookie("name",obj.getString("name"));
					response.addCookie(cookie);
				}
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
