package kr.co.wmplayer.sinmina.model.dto.board;

import kr.co.wmplayer.sinmina.interfaces.WMPlayerDTO;

public class ColumnBoardDTO implements WMPlayerDTO{
	private int column_seq;
	private String title;
	private String update_day;
	private int view_cnt;
	private String contents;
	
	public ColumnBoardDTO() {

	}
	
	public ColumnBoardDTO(int column_seq, String title, int view_cnt) {
		this.column_seq = column_seq;
		this.title = title;
		this.view_cnt = view_cnt;
	}
	public ColumnBoardDTO(int column_seq, String title, String update_day, 
			int view_cnt, String contents) {
		super();
		this.column_seq = column_seq;
		this.title = title;
		this.update_day = update_day;
		this.view_cnt = view_cnt;
		this.contents = contents;
	}
	public int getColumn_seq() {
		return column_seq;
	}
	public void setColumn_seq(int column_seq) {
		this.column_seq = column_seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUpdate_day() {
		return update_day;
	}
	public void setUpdate_day(String update_day) {
		this.update_day = update_day;
	}
	public int getView_cnt() {
		return view_cnt;
	}
	public void setView_cnt(int view_cnt) {
		this.view_cnt = view_cnt;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	
}
