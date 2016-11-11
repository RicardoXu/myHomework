package javaweb.xuzm.course.service;

import javaweb.xuzm.course.meta.Person;

public interface PersonService {

	// 用户登录
	String userlogin(Person person);

	// 查询type
	int findtype(Person person);

	// 查询用户id
	int id(String name);
}
