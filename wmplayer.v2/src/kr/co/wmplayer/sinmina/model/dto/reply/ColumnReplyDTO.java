package kr.co.wmplayer.sinmina.model.dto.reply;

import kr.co.wmplayer.sinmina.interfaces.WMPlayerDTO;

public class ColumnReplyDTO implements WMPlayerDTO{
	private int columnreply_seq;
	private int column_seq;
	private String userID;
	private String contents;
	private String submit_date;
	
	public ColumnReplyDTO() {
		// TODO Auto-generated constructor stub
	}

	
	
	public ColumnReplyDTO(int column_seq, String userID, String contents) {
		super();
		this.column_seq = column_seq;
		this.userID = userID;
		this.contents = contents;
	}

	public ColumnReplyDTO(int columnreply_seq, int column_seq, String userID,
			String contents, String submit_date) {
		super();
		this.columnreply_seq = columnreply_seq;
		this.column_seq = column_seq;
		this.userID = userID;
		this.contents = contents;
		this.submit_date = submit_date;
	}

	public int getColumnreply_seq() {
		return columnreply_seq;
	}

	public void setColumnreply_seq(int columnreply_seq) {
		this.columnreply_seq = columnreply_seq;
	}

	public int getColumn_seq() {
		return column_seq;
	}

	public void setColumn_seq(int column_seq) {
		this.column_seq = column_seq;
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
