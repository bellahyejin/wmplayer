package Weather;

public class WeatherBeans // ���� ���� ���ϰ� �������� ���� ��������
{
	private int hour, day;
	private double temp;
	private String weather;
	
	public int getHour()
	{
		return hour;
	}
	
	public int getDay()
	{
		return day;
	}
	
	public double getTemp()
	{
		return temp;
	}
	
	public String getWeather()
	{
		return weather;
	}
	
	public void setHour(int hour)
	{
		this.hour = hour;
	}
	
	public void setDay(int day)
	{
		this.day = day;
	}
	
	public void setTemp(double temp)
	{
		this.temp = temp;
	}
	
	public void setWeather(String weather)
	{
		this.weather = weather;
	}
}
