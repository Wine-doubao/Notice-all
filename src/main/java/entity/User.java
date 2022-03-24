package entity;

public class User {
	private String u_id;
	private String u_name;
	private String u_introduction;
	private String u_email;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getU_name() {
		return u_name;
	}
	public void setU_name(String u_name) {
		this.u_name = u_name;
	}
	public String getU_introduction() {
		return u_introduction;
	}
	public void setU_introduction(String u_introduction) {
		this.u_introduction = u_introduction;
	}
	public String getU_email() {
		return u_email;
	}
	public void setU_email(String u_email) {
		this.u_email = u_email;
	}
	@Override
	public String toString() {
		return "User [u_id=" + u_id + ", u_name=" + u_name + ", u_introduction=" + u_introduction + ", u_email="
				+ u_email + "]";
	}
	
}
