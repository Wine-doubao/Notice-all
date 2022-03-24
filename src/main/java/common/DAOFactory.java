package common;

import admin.ainfo.AinfoDAOProxy;
import admin.ainfo.IAinfoDAO;
import beforelogin.index.IIntroDAO;
import beforelogin.index.IntroDAOProxy;
import beforelogin.login.ILoginDAO;
import beforelogin.login.LoginDAOProxy;
import user.uinfo.IUinfoDAO;
import user.uinfo.UinfoDAOProxy;

public class DAOFactory {
	public static IIntroDAO getIIntroDAOInstance() {
		return new IntroDAOProxy();
	}
	public static ILoginDAO getILoginDAOInstance() {
		return new LoginDAOProxy();
	}
	public static IUinfoDAO getIUinfoDAOInstance() {
		return new UinfoDAOProxy();
	}
	public static IAinfoDAO getIAinfoDAOInstance() {
		return new AinfoDAOProxy();
	}
}
