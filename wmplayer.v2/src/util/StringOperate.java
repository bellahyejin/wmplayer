package util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

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

	public String substring(String str, int start_idx, int byte_length, boolean isNoTag, boolean isAddDot)
	{
		if (str == null) throw new NullPointerException("str");

		if (start_idx < 0 || start_idx > str.length())  throw new StringIndexOutOfBoundsException(start_idx);
		if (byte_length <= 0)  throw new StringIndexOutOfBoundsException(byte_length);

		String value = str.substring(start_idx);
		Pattern p = Pattern.compile("<(/?)([^<>]*)?>", Pattern.CASE_INSENSITIVE);

		if (isNoTag) value = p.matcher(value).replaceAll("");
		value = value.replaceAll("&amp;", "&").replaceAll("(!/|\r|\n|&nbsp;&nbsp;)", "&nbsp;");

		try
		{
			byte[]  bytes = value.getBytes("utf-8");
			int real_length = calcByte(bytes, byte_length);

			return new String(bytes, 0, real_length, "utf-8").concat(isAddDot && real_length < bytes.length ? "..." : "");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return str;
		}
	}

	private int calcByte(byte[] bytes, int byte_length)
	{
		int original_length = 0, real_length = 0, original_incre = 0, real_incre = 0;

		loop : while (real_length < bytes.length)
		{
			if ((bytes[real_length] & 0x80) != 0)
			{
				original_incre = 2;
				real_incre = 3;
			}
			else original_incre = real_incre = 1;

			original_length += original_incre;
			if (original_length > byte_length) break loop;
			real_length += real_incre;
			if (original_length > bytes.length) break loop;
		}

		return real_length;
	}
}
