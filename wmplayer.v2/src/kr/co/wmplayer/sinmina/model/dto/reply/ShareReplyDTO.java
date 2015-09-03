package kr.co.wmplayer.sinmina.model.dto.reply;

import kr.co.wmplayer.sinmina.interfaces.WMPlayerDTO;

public class ShareReplyDTO implements WMPlayerDTO{
	private int sharereple_seq;
	private int board_seq;
	private String userID;
	private String contents;
	private String submit_date;

	public ShareReplyDTO() {
		// TODO Auto-generated constructor stub
	}

	public ShareReplyDTO(int board_seq, String userID, String contents) {
		super();
		this.board_seq = board_seq;
		this.userID = userID;
		this.contents = contents;
	}

	public ShareReplyDTO(int sharereple_seq, int board_seq, String userID,
			String contents, String submit_date) {
		super();
		this.sharereple_seq = sharereple_seq;
		this.board_seq = board_seq;
		this.userID = userID;
		this.contents = contents;
		this.submit_date = submit_date;
	}

	public int getSharereple_seq() {
		return sharereple_seq;
	}

	public void setSharereple_seq(int sharereple_seq) {
		this.sharereple_seq = sharereple_seq;
	}

	public int getBoard_seq() {
		return board_seq;
	}

	public void setBoard_seq(int board_seq) {
		this.board_seq = board_seq;
	}

	public String getuserID() {
		return userID;
	}

	public void setuserID(String userID) {
		this.userID = userID;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getSubmit_date() {
		return submit_date;
	}

	public void setSubmit_date(String submit_date) {
		this.submit_date = submit_date;
	}


}
