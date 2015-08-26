package util;

public class StringOperate
{
	private static StringOperate operate;

	public static final StringOperate getInstance()
	{
		return operate == null ? operate = new StringOperate() : operate;
	}

	private String fnLowerCase(String str, int start_idx, int end_idx)
	{
		return (start_idx == 0 ? "" : str.substring(0, start_idx)) + str.substring(start_idx, end_idx).toLowerCase() + (end_idx == str.length() ? "" : str.substring(end_idx));
	}

	public String toLowerCase(String str, int idx)
	{
		if (str == null) throw new NullPointerException();
		if (idx < 0 || idx >= str.length()) throw new StringIndexOutOfBoundsException(idx);
		return fnLowerCase(str, idx, idx + 1);
	}

	public String toLowerCase(String str, int start_idx, int end_idx)
	{
		if (str == null) throw new NullPointerException(str);
		if (start_idx < 0) throw new StringIndexOutOfBoundsException(start_idx);
		if (end_idx > str.length()) throw new StringIndexOutOfBoundsException(end_idx);
		int i = end_idx - start_idx;
		if (i < 0) throw new StringIndexOutOfBoundsException(i);
		if (i == 0) return str;
		return fnLowerCase(str, start_idx, end_idx);
	}

	private String fnUpperCase(String str, int start_idx, int end_idx)
	{
		return (start_idx == 0 ? "" : str.substring(0, start_idx)) + str.substring(start_idx, end_idx).toUpperCase() + (end_idx == str.length() ? "" : str.substring(end_idx));
	}

	public String toUpperCase(String str, int idx)
	{
		if (str == null) throw new NullPointerException();
		if (idx < 0 || idx >= str.length()) throw new StringIndexOutOfBoundsException(idx);
		return toUpperCase(str, idx, idx + 1);
	}

	public String toUpperCase(String str, int start_idx, int end_idx)
	{
		if (str == null) throw new NullPointerException(str);
		if (start_idx < 0) throw new StringIndexOutOfBoundsException(start_idx);
		if (end_idx > str.length()) throw new StringIndexOutOfBoundsException(end_idx);
		int i = end_idx - start_idx;
		if (i < 0) throw new StringIndexOutOfBoundsException(i);
		if (i == 0) return str;
		return fnUpperCase(str, start_idx, end_idx);
	}
}
