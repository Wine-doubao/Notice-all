package entity;

public class Administrator {
	private String org_name;
	private String a_name;
	private String a_introduction;
	private String a_email;
	private String a_id;
	public Administrator() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getA_name() {
		return a_name;
	}
	public void setA_name(String a_name) {
		this.a_name = a_name;
	}
	public String getA_introduction() {
		return a_introduction;
	}
	public void setA_introduction(String a_introduction) {
		this.a_introduction = a_introduction;
	}
	public String getA_email() {
		return a_email;
	}
	public void setA_email(String a_email) {
		this.a_email = a_email;
	}
	public String getA_id() {
		return a_id;
	}
	public void setA_id(String a_id) {
		this.a_id = a_id;
	}
	@Override
	public String toString() {
		return "Administrator [org_name=" + org_name + ", a_name=" + a_name + ", a_introduction=" + a_introduction
				+ ", a_email=" + a_email + ", a_id=" + a_id + "]";
	}
	
}
