package kr.co.wmplayer.sinmina.dao;

import java.util.Map;
import kr.co.wmplayer.sinmina.interfaces.DAOImpl;
import kr.co.wmplayer.sinmina.model.dto.user.UserInfoDTO;

public class UserInfoDAO_1 extends DAOImpl<UserInfoDTO>
{

	private final String namespace = "user.";

	@Override
	public boolean insert(UserInfoDTO bean)
	{
		// join
		return (boolean) executeQuery(namespace + "insert", bean, null);
	}

	@Override
	public boolean delete(Object key)
	{
		// delete
		return (boolean) executeQuery(namespace + "delete", key, null);
	}

	@Override
	public boolean insert(Map<String, Object> map)
	{
		// log
		return (boolean) executeQuery(namespace + "loginlog", map, null);
	}

	@Override
	public boolean update(Map<String, Object> map)
	{
		// info update
		return (boolean) executeQuery(namespace + "update", map, null);
	}

	@Override
	public Object select(Map<String, Object> map, int start, int limit)
	{
		// login
		return executeQuery(namespace + "count", map, null);
	}

	@Override
	public Object count(Map<String, Object> map)
	{
		// duplication
		return executeQuery(namespace + "count", map, null);
	}
}
