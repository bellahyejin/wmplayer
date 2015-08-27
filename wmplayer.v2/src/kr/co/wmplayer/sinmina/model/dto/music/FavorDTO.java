package kr.co.wmplayer.sinmina.model.dto.music;

import kr.co.wmplayer.sinmina.interfaces.WMPlayerDTO;

public class FavorDTO implements WMPlayerDTO{
	private String musicID;
	private String userID;
	private String favor_date;
	
	public FavorDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public FavorDTO(String musicID, String userID){
		this.musicID = musicID;
		this.userID = userID;
	}
	
	public FavorDTO(String musicID, String userID, String favor_date,
			String location) {
		super();
		this.musicID = musicID;
		this.userID = userID;
		this.favor_date = favor_date;
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

	public String getFavor_date() {
		return favor_date;
	}

	public void setFavor_date(String favor_date) {
		this.favor_date = favor_date;
	}

}
