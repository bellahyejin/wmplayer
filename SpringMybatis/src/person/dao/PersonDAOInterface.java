package person.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import person.beans.Person;

public interface PersonDAOInterface {
	public boolean insert(Person person);
	public boolean delete(String name);
	public boolean update(Person person);
	public Person select(String name);
	public List<Person> selectAll();
	public List<Person> searchResult(Map<String, Object> personmap);
}
