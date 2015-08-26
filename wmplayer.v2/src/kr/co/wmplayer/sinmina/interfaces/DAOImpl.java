package kr.co.wmplayer.sinmina.interfaces;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.co.wmplayer.sinmina.interfaces.WMPlayerDAO;
import kr.co.wmplayer.sinmina.interfaces.WMPlayerDTO;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DAOImpl<Bean extends WMPlayerDTO> implements WMPlayerDAO<Bean>
{
	@Autowired
	protected SqlSession sqlSession;

	public boolean insert(Bean bean)
	{
		return false;
	}

	public boolean update(Bean bean)
	{
		return false;
	}

	public boolean delete(Object key)
	{
		return false;
	}

	public Object select(Object key, int start, int limit)
	{
		return null;
	}

	public boolean insert(Map<String, Object> map)
	{
		return false;
	}

	public boolean update(Map<String, Object> map)
	{
		return false;
	}

	public boolean delete(Map<String, Object> map)
	{
		return false;
	}

	public Object select(Map<String, Object> map, int start, int limit)
	{
		return null;
	}

	public Object count(Map<String, Object> map)
	{
		return null;
	}

	public RowBounds setRowBounds(int start, int limit)
	{
		if (start >= 0 && limit > 0) return new RowBounds(start, limit);
		return null;
	}

	@SuppressWarnings("unchecked")
	public Object executeQuery(String identifier, Object obj, RowBounds row)
	{
		Class<?> SqlSessionClass = sqlSession.getClass(), classes[];
		Object[] arguments;

		List<Class<?>> classes_temp = new ArrayList<Class<?>>();
		List<Object> arguments_temp = new ArrayList<Object>();
		Map<String, Object> map;
		String methodName;

		if (identifier.contains("select") || identifier.contains("count"))
		{
			if (obj != null && obj instanceof Map)
			{
				map = (Map<String, Object>) obj;
				if (row == null) methodName = "selectOne";
				else methodName = "selectList";
			}
			else methodName = "selectList";
		}
		else if (identifier.contains("insert")) methodName = "insert";
		else if (identifier.contains("update")) methodName = "update";
		else if (identifier.contains("delete")) methodName = "delete";
		else return null;

		arguments_temp.add(identifier);
		classes_temp.add(String.class);
		if (obj != null)
		{
			arguments_temp.add(obj);
			classes_temp.add(Object.class);
		}
		if (row != null)
		{
			arguments_temp.add(row);
			classes_temp.add(RowBounds.class);
		}

		Object[] temp = classes_temp.toArray();
		classes = new Class<?>[temp.length];
		for (int i = 0; i < classes.length; i++) classes[i] = (Class<?>) temp[i];
		arguments = arguments_temp.toArray();

		try
		{
			return SqlSessionClass.getDeclaredMethod(methodName, classes).invoke(sqlSession, arguments);
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
