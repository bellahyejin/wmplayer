package util;

public final class Time
{
	public static final long MILLISECOND = 1000L;
	public static final long SECOND = 60L;
	public static final long MINUTE = 60L;
	public static final long HOUR = 24L;
	public static final long DAY = 365L;

	public static enum Field
	{
		Millisecond, Second, Minute, Hour, Day
	}

	public static final long getTime(Field f)
	{
		long temp = 1L;

		switch (f)
		{
			case Day :
				temp *= DAY;
			case Hour :
				temp *= HOUR;
			case Minute :
				temp *= MINUTE;
			case Second :
				temp *= SECOND;
			case Millisecond :
				temp *= MILLISECOND;
			default :
		}

		return temp;
	}
}
