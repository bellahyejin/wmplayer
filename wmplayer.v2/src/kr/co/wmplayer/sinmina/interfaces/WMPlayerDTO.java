package kr.co.wmplayer.sinmina.interfaces;

public interface WMPlayerDTO
{
	public static int DEFAULT_VALUE = -1 << 15;

	default int length()
	{
		return 0;
	}

	public abstract String toString();
}
