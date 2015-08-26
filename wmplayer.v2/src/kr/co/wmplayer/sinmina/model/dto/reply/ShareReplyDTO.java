package kr.co.wmplayer.sinmina.model.dto.reply;

import kr.co.wmplayer.sinmina.interfaces.WMPlayerDTO;

public class ShareReplyDTO implements WMPlayerDTO{
	private int sharereply_seq;
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

	public ShareReplyDTO(int sharereply_seq, int board_seq, String userID,
			String contents, String submit_date) {
		super();
		this.sharereply_seq = sharereply_seq;
		this.board_seq = board_seq;
		this.userID = userID;
		this.contents = contents;
		this.submit_date = submit_date;
	}

	public int getSharereply_seq() {
		return sharereply_seq;
	}

	public void setSharereply_seq(int sharereply_seq) {
		this.sharereply_seq = sharereply_seq;
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
