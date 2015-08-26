package kr.co.wmplayer.sinmina.model.dto.music;

import kr.co.wmplayer.sinmina.interfaces.WMPlayerDTO;

public class LikeMusicDTO implements WMPlayerDTO{
	private String musicID;
	private String userID;
	private String like_date;
	private String location;
	
	public LikeMusicDTO() {
		// TODO Auto-generated constructor stub
	}

	public LikeMusicDTO(String musicID, String userID, String location) {
		super();
		this.musicID = musicID;
		this.userID = userID;
		this.location = location;
	}

	public LikeMusicDTO(String musicID, String userID, String like_date,
			String location) {
		super();
		this.musicID = musicID;
		this.userID = userID;
		this.like_date = like_date;
		this.location = location;
	}

	public String getMusicID() {
		return musicID;
	}

	public void setMusicID(String musicID) {
		this.musicID = musicID;
	}

	public String getuserID() {
		return userID;
	}

	public void setuserID(String userID) {
		this.userID = userID;
	}

	public String getLike_date() {
		return like_date;
	}

	public void setLike_date(String like_date) {
		this.like_date = like_date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
