package javaweb.xuzm.course.dao;

import org.apache.ibatis.annotations.Select;

import javaweb.xuzm.course.meta.Person;

public interface PersonDao {

	// 登录
	@Select("SELECT username FROM person where userName = #{userName} and password = #{password}")
	public String login(Person person);

	// 查询type
	@Select("SELECT userType FROM person where userName = #{userName} and password = #{password}")
	public int type(Person person);

	// 查询用户id
	@Select("SELECT id FROM person where userName = #{userName}")
	public int id(String name);
}
