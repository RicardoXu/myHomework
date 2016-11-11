package javaweb.xuzm.course.meta;

public class User {
	// 用户名
	private String username;
	// 0为买家1为卖家
	private int usertype;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUsertype() {
		return usertype;
	}

	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}
}
