package kr.co.wmplayer.sinmina.model.dto.manager;

import kr.co.wmplayer.sinmina.interfaces.WMPlayerDTO;

public class DropReasonDTO implements WMPlayerDTO{
	private int drop_seq;
	private String reason;
    private int count_reason;
    
	public DropReasonDTO() {
		// TODO Auto-generated constructor stub
	}
    
	public DropReasonDTO(int drop_seq, String reason, int count_reason) {
		super();
		this.drop_seq = drop_seq;
		this.reason = reason;
		this.count_reason = count_reason;
	}
	public int getDrop_seq() {
		return drop_seq;
	}
	public void setDrop_seq(int drop_seq) {
		this.drop_seq = drop_seq;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getCount_reason() {
		return count_reason;
	}
	public void setCount_reason(int count_reason) {
		this.count_reason = count_reason;
	}
    
    
}
