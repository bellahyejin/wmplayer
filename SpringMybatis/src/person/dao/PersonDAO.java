package person.dao;

import java.util.List;
import java.util.Map;

import javax.websocket.Session;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import person.beans.Person;

public class PersonDAO implements PersonDAOInterface{
	
	@Autowired
	private SqlSession session;
	
	@Override
	public boolean insert(Person person) {
		int t = session.insert("person.addperson",person);
		
		if( t == 1)return true;
		return false;
	}

	@Override
	public boolean delete(String name) {
		
		int t = session.delete("person.delete",name);
		if(t == 1) return true;
		return false;
	}

	@Override
	public boolean update(Person person) {
		
		int t = session.update("person.editperson", person);
		if( t == 1) return true;
		return false;
	}

	@Override
	public Person select(String name) {
		
		Person one = new Person();
		one = session.selectOne("person.selectPerson", name);
		
		return one;
	}

	@Override
	public List<Person> selectAll() {
		List<Person> personlist = session.selectList("person.selectAll");
		
		return personlist;
	}

	@Override
	public List<Person> searchResult(Map<String, Object> personmap) {

		List<Person> search = session.selectList("person.search",personmap);
		
		return search;
	}

}
