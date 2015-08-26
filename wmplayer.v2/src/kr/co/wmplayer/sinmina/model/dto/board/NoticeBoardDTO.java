package kr.co.wmplayer.sinmina.model.dto.board;

import kr.co.wmplayer.sinmina.interfaces.WMPlayerDTO;

public class NoticeBoardDTO implements WMPlayerDTO {
	private int notice_seq;
	private String title;
	private String update_day;
	private int view_cnt;
	private String contents;
	
	public NoticeBoardDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public NoticeBoardDTO(int notice_seq, String title, String update_day,
			int view_cnt, String contents) {
		super();
		this.notice_seq = notice_seq;
		this.title = title;
		this.update_day = update_day;
		this.view_cnt = view_cnt;
		this.contents = contents;
	}
	public int getNotice_seq() {
		return notice_seq;
	}
	public void setNotice_seq(int notice_seq) {
		this.notice_seq = notice_seq;
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
