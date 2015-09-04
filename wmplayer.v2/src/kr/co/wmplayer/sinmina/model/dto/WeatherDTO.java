package kr.co.wmplayer.sinmina.model.dto;

public class WeatherDTO {
	private int weather_seq;
	private String weather_name;
	private double min_bpm;
	private double max_bpm;
	
	public WeatherDTO() {
		// TODO Auto-generated constructor stub
	}

	public WeatherDTO(int weather_seq, String weather_name, double min_bpm,
			double max_bpm) {
		super();
		this.weather_seq = weather_seq;
		this.weather_name = weather_name;
		this.min_bpm = min_bpm;
		this.max_bpm = max_bpm;
	}

	public int getWeather_seq() {
		return weather_seq;
	}

	public void setWeather_seq(int weather_seq) {
		this.weather_seq = weather_seq;
	}

	public String getWeather_name() {
		return weather_name;
	}

	public void setWeather_name(String weather_name) {
		this.weather_name = weather_name;
	}

	public double getMin_bpm() {
		return min_bpm;
	}

	public void setMin_bpm(double min_bpm) {
		this.min_bpm = min_bpm;
	}

	public double getMax_bpm() {
		return max_bpm;
	}

	public void setMax_bpm(double max_bpm) {
		this.max_bpm = max_bpm;
	}
	
	
}
