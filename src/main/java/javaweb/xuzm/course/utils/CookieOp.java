package javaweb.xuzm.course.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javaweb.xuzm.course.meta.User;

public class CookieOp {

	// 为合法用户创建cookie
	public void setCookie(User user, HttpServletResponse response) {
		int userType = user.getUsertype();
		Cookie userNameCookie = new Cookie("userName", user.getUsername());
		userNameCookie.setMaxAge(24 * 60 * 60);
		response.addCookie(userNameCookie);
	}

	// 提取Cookie中的数据
	public User getCookie(HttpServletRequest request) {
		User user = new User();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userName")) {
					user.setUsername(cookie.getValue());
				}
			}
		}
		return user;
	}
}
