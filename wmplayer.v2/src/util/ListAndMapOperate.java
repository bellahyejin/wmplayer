package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ListAndMapOperate
{
	private static ListAndMapOperate operate;

	public static final ListAndMapOperate getInstance()
	{
		return operate == null ? operate = new ListAndMapOperate() : operate;
	}

	public <E> List<Object> changeType(List<E> obj)
	{
		List<Object> list = new ArrayList<Object>();
		for (Object temp : obj)
		{
			list.add(temp);
		}
		return list;
	}

	public <E> List<List<E>> wrappingObject(List<E> obj)
	{
		List<List<E>> list = new ArrayList<List<E>>();
		for (E e : obj)
		{
			List<E> temp = new ArrayList<E>();
			temp.add(e);
			list.add(temp);
		}
		return list;
	}

	public <K, V> List<Map<K, V>> wrappingObject(List<V> obj, K default_column_name)
	{
		List<Map<K, V>> list = new ArrayList<Map<K, V>>();
		for (V v : obj)
		{
			Map<K, V> map = new ListMap<K, V>();
			map.put(default_column_name, v);
			list.add(map);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public <K, V extends Object> List<Map<K, V>> addKeyName(List<Map<K, V>> list, List<K> key_column, List<V> default_value, boolean index, boolean count, int increment, int multiple, boolean multiple_where)
	{
		if (key_column != null && default_value != null && key_column.size() == default_value.size())
		{
			MakeInstance instance = MakeInstance.getInstance();

			for (int i = 0; i < list.size(); i++)
			{
				Map<K, V> map = list.get(i);

				if (map == null) map = new HashMap<K, V>();

				for (int j = 0; j < key_column.size(); j++)
				{
					K key = key_column.get(j);
					V value = default_value.get(j);
					if (value instanceof CharSequence)
					{
						Map<String, String> insert = new ListMap<String, String>();
						String temp =  (value.toString() + (index ? ((i + (count ? 1 : 0) * (multiple_where ? multiple : 1)) + increment) * (multiple_where ? 1 : multiple) : ""));
						insert.put("temp", temp);
						map.put(key, (V) instance.newInstance(value.getClass(), insert));
					}
					else map.put(key, value);
				}
			}
		}

		return list;
	}

	public <K1, K2, V> List<Map<K2, V>> changeKeyName(List<Map<K1, V>> before_list, Map<K1, K2> change_map)
	{
		if (before_list != null && before_list.size() > 0)
		{
			List<Map<K2, V>> after_list = new ArrayList<Map<K2, V>>();
			int map_size = change_map.size();

			for (Map<K1, V> before_map : before_list)
			{
				if (before_map.size() == map_size)
				{
					Iterator<K1> s1 = before_map.keySet().iterator(), s2 = change_map.keySet().iterator();
					boolean b1 = true, b2 = true;

					while (s1.hasNext()) b1 &= change_map.containsKey(s1.next());
					while (s2.hasNext()) b2 &= before_map.containsKey(s2.next());

					if (b1 && b2)
					{
						Iterator<K1> s = before_map.keySet().iterator();

						Map<K2, V> after_map = new HashMap<K2, V>();
						K1 before_key;
						while (s.hasNext()) after_map.put(change_map.get(before_key = s.next()), before_map.get(before_key));
						after_list.add(after_map);
					}
				}
			}

			return after_list;
		}

		return null;
	}
}
