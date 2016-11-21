package javaweb.xuzm.course.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javaweb.xuzm.course.meta.User;

public class CookieOp {

	//这里的Cookie没有实际上的用途，只是为Session提供生存环境
	// 为合法用户创建cookie
	public void setCookie(User user, HttpServletResponse response) {
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
