package kr.co.wmplayer.sinmina.model.dto.board;

import kr.co.wmplayer.sinmina.interfaces.WMPlayerDTO;

public class BoardUserDTO implements WMPlayerDTO{
	private int board_seq;
	private String userID;
	private String board_title;
	private String board_artist;
	private String videoID;
	private String albumcover;
	private String weather_custom;
	private String regi_day;
	private String board_desc;
	private int check_cnt;

	public BoardUserDTO() {

	}

	@Override
	public String toString()
	{
		return "BoardUserDTO [board_seq=" + board_seq + ", userID=" + userID + ", board_title=" + board_title + ", board_artist=" + board_artist + ", videoID=" + videoID + ", albumcover=" + albumcover + ", weather_custom=" + weather_custom + ", regi_day=" + regi_day + ", board_desc=" + board_desc + ", check_cnt=" + check_cnt + "]";
	}

	public BoardUserDTO(int board_seq, String board_title, String board_artist){
		this.board_seq = board_seq;
		this.board_title = board_title;
		this.board_artist = board_artist;
	}
	public BoardUserDTO(int board_seq, String userID, String board_title,
					String board_artist, String albumcover){
		this.board_seq = board_seq;
		this.board_title = board_title;
		this.board_artist = board_artist;
		this.userID = userID;
		this.albumcover = albumcover;
	}
	public BoardUserDTO(int board_seq, String userID, String board_title,
			String board_artist, String videoID, String albumcover,
			String weather_custom, String board_desc, int check_cnt) {
		this.board_seq = board_seq;
		this.userID = userID;
		this.board_title = board_title;
		this.board_artist = board_artist;
		this.videoID = videoID;
		this.albumcover = albumcover;
		this.weather_custom = weather_custom;
		this.board_desc = board_desc;
		this.check_cnt = check_cnt;
	}

	public BoardUserDTO(int board_seq, String userID, String board_title, String board_artist,
						String albumcover, String weather_custom, String regi_day){
		this.board_seq = board_seq;
		this.userID = userID;
		this.board_title = board_title;
		this.board_artist = board_artist;
		this.albumcover = albumcover;
		this.weather_custom = weather_custom;
		this.regi_day=regi_day;

	}
	public BoardUserDTO(int board_seq, String userID, String board_title,
			String board_artist, String videoID, String albumcover
			,String weather_custom, String board_desc) {
		this.board_seq = board_seq;
		this.userID = userID;
		this.board_title = board_title;
		this.board_artist = board_artist;
		this.videoID = videoID;
		this.albumcover = albumcover;
		this.weather_custom = weather_custom;
		this.board_desc = board_desc;
	}


	public BoardUserDTO(int board_seq, String userID, String board_title,
			String board_artist, String videoID, String albumcover,
			String weather_custom, String regi_day, String board_desc,
			int check_cnt) {
		super();
		this.board_seq = board_seq;
		this.userID = userID;
		this.board_title = board_title;
		this.board_artist = board_artist;
		this.videoID = videoID;
		this.albumcover = albumcover;
		this.weather_custom = weather_custom;
		this.regi_day = regi_day;
		this.board_desc = board_desc;
		this.check_cnt = check_cnt;
	}

	public int getBoard_seq() {
		return board_seq;
	}

	public void setBoard_seq(int board_seq) {
		this.board_seq = board_seq;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getBoard_title() {
		return board_title;
	}

	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}

	public String getBoard_artist() {
		return board_artist;
	}

	public void setBoard_artist(String board_artist) {
		this.board_artist = board_artist;
	}

	public String getVideoID() {
		return videoID;
	}

	public void setVideoID(String videoID) {
		this.videoID = videoID;
	}

	public String getAlbumcover() {
		return albumcover;
	}

	public void setAlbumcover(String albumcover) {
		this.albumcover = albumcover;
	}

	public String getWeather_custom() {
		return weather_custom;
	}

	public void setWeather_custom(String waether_custom) {
		this.weather_custom = waether_custom;
	}

	public String getRegi_day() {
		return regi_day;
	}

	public void setRegi_day(String regi_day) {
		this.regi_day = regi_day;
	}

	public String getBoard_desc() {
		return board_desc;
	}

	public void setBoard_desc(String board_desc) {
		this.board_desc = board_desc;
	}

	public int getCheck_cnt() {
		return check_cnt;
	}

	public void setCheck_cnt(int check_cnt) {
		this.check_cnt = check_cnt;
	}

}
