package util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JSONOperate
{
	private static JSONOperate operate;

	public static final JSONOperate getInstance()
	{
		return operate == null ? operate = new JSONOperate() : operate;
	}

	public <K, V> String generateJSONData(Map<K, V> m, Map<K, ? extends CharSequence> mapping_columns, boolean add_index, boolean index_set, boolean add_list_cut, boolean add_key_cut, boolean add_value_cut, boolean list_branket, String branket)
	{
		StringBuffer sb = new StringBuffer();
		Iterator<K> keys = m.keySet().iterator();

		sb = sb.append("{");
		while (keys.hasNext())
		{
			K key = keys.next();

			if (key instanceof Map) sb = sb.append(generateJSONData((Map<?, ?>) key, null, add_index, index_set, add_list_cut, add_key_cut, add_value_cut, list_branket, branket));
			else if (key instanceof List) sb = sb.append(generateJSONData((List<?>) key, null, add_index, index_set, add_list_cut, add_key_cut, add_value_cut, list_branket, branket));
			else
			{
				boolean flag = true;
				if (mapping_columns != null && mapping_columns.size() > 0) for (Object column : mapping_columns.keySet()) flag &= key.equals(column);
				sb = sb.append((add_key_cut ? "\"" : "") + (flag ? key.toString() : mapping_columns.get(key).toString()) + (add_key_cut ? "\"" : ""));
			}

			sb = sb.append(" : ");

			V value = m.get(key);
			boolean flag = add_value_cut || (!(value instanceof Number) && !(value instanceof Boolean));

			if (value instanceof Map) sb = sb.append(generateJSONData((Map<?, ?>) value, null, add_index, index_set, add_list_cut, add_key_cut, add_value_cut, list_branket, branket));
			else if (value instanceof List) sb = sb.append(generateJSONData((List<?>) value, null, add_index, index_set, add_list_cut, add_key_cut, add_value_cut, list_branket, branket));
			else sb = sb.append((flag ? "\"" : "") + (value == null ? "" : value.toString()) + (flag ? "\"" : ""));

			sb = sb.append(", ");
		}
		sb = new StringBuffer(sb.subSequence(0, sb.lastIndexOf(",")));
		sb = sb.append("}");

		return sb.toString();
	}

	public <E> String generateJSONData(List<E> l, CharSequence list_default_column, boolean add_index, boolean index_set, boolean add_list_cut, boolean add_key_cut, boolean add_value_cut, boolean list_branket, String branket)
	{
		StringBuffer sb = new StringBuffer();

		sb = sb.append("[");
		if (list_branket) sb = sb.append(branket.split("")[0]);
		for (int i = 0; i < l.size(); i++)
		{
			E obj = l.get(i);

			if (obj instanceof Map) sb = sb.append(generateJSONData((Map<?, ?>) obj, null, add_index, index_set, add_list_cut, add_key_cut, add_value_cut, list_branket, branket));
			else if (obj instanceof List) sb = sb.append(generateJSONData((List<?>) obj, null, add_index, index_set, add_list_cut, add_key_cut, add_value_cut, list_branket, branket));
			else sb = sb.append("{").append((add_list_cut ? "\"" : "") + list_default_column == null ? "item" : list_default_column).append((add_index ? (index_set ? i + 1 : i) : "") + (add_list_cut ? "\"" : "")).append(" : ").append((add_list_cut ? "\"" : "") + (obj == null ? "" : obj.toString()) + (add_list_cut ? "\"" : "")).append("}");

			if (i != l.size() - 1) sb.append(", ");
		}
		if (list_branket) sb = sb.append(branket.split("")[1]);
		sb = sb.append("]");

		return sb.toString();
	}
}
