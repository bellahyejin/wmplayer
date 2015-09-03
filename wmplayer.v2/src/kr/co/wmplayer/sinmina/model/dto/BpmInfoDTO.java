package kr.co.wmplayer.sinmina.model.dto;

public class BpmInfoDTO {
	private int bpm_seq;
	private int min_bpm;
	private int max_bpm;
	private double min_temp;
	private double max_temp;
	
	public BpmInfoDTO() {
		// TODO Auto-generated constructor stub
	}
	public BpmInfoDTO(int bpm_seq, int min_bpm, int max_bpm, double min_temp,
			double max_temp) {
		super();
		this.bpm_seq = bpm_seq;
		this.min_bpm = min_bpm;
		this.max_bpm = max_bpm;
		this.min_temp = min_temp;
		this.max_temp = max_temp;
	}

	public int getBpm_seq() {
		return bpm_seq;
	}

	public void setBpm_seq(int bpm_seq) {
		this.bpm_seq = bpm_seq;
	}

	public int getMin_bpm() {
		return min_bpm;
	}

	public void setMin_bpm(int min_bpm) {
		this.min_bpm = min_bpm;
	}

	public int getMax_bpm() {
		return max_bpm;
	}

	public void setMax_bpm(int max_bpm) {
		this.max_bpm = max_bpm;
	}

	public double getMin_temp() {
		return min_temp;
	}

	public void setMin_temp(double min_temp) {
		this.min_temp = min_temp;
	}

	public double getMax_temp() {
		return max_temp;
	}

	public void setMax_temp(double max_temp) {
		this.max_temp = max_temp;
	}
	
	
}
