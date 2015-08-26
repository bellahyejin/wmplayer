package kr.co.wmplayer.sinmina.model.dto.music;

import kr.co.wmplayer.sinmina.interfaces.WMPlayerDTO;

public class MusicInfoDTO implements WMPlayerDTO{
	private String musicID;
	private String title;
	private String artist;
	private String image;
	private double bpm;
	private String location;
	
	public MusicInfoDTO() {
		// TODO Auto-generated constructor stub
	}

	public MusicInfoDTO(String title, String artist) {
		super();
		this.title = title;
		this.artist = artist;
	}

	public MusicInfoDTO(String musicID, String title, String artist,
			String image, double bpm, String location) {
		super();
		this.musicID = musicID;
		this.title = title;
		this.artist = artist;
		this.image = image;
		this.bpm = bpm;
		this.location = location;
	}

	public String getMusicID() {
		return musicID;
	}

	public void setMusicID(String musicID) {
		this.musicID = musicID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getBpm() {
		return bpm;
	}

	public void setBpm(double bpm) {
		this.bpm = bpm;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
