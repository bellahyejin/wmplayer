package kr.co.wmplayer.sinmina.interfaces;

import java.util.Map;

public interface WMPlayerDAO<Bean extends WMPlayerDTO>
{
	public abstract boolean insert(Bean bean);
	public abstract boolean update(Bean bean);
	public abstract boolean delete(Object key);
	public abstract Object select(Object key, int start, int limit);

	public abstract boolean insert(Map<String, Object> map);
	public abstract boolean update(Map<String, Object> map);
	public abstract boolean delete(Map<String, Object> map);
	public abstract Object select(Map<String, Object> map, int start, int limit);
	public abstract Object count(Map<String, Object> map);
}
