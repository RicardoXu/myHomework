package javaweb.xuzm.course.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import javaweb.xuzm.course.dao.PersonDao;
import javaweb.xuzm.course.meta.Person;
import javaweb.xuzm.course.service.PersonService;

@Service
public class PersonSerciveImpl implements PersonService {
	@Resource
	private PersonDao personDao;

	public String userlogin(Person person) {
		return personDao.login(person);
	}

	public int findtype(Person person) {
		return personDao.type(person);
	}

	public int id(String name) {
		return personDao.id(name);
	}
}
