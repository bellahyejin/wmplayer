package kr.co.wmplayer.sinmina.interfaces;

import java.util.List;
import java.util.Map;

import kr.co.wmplayer.sinmina.model.dto.reply.ColumnReplyDTO;


public interface ColumnRelyInterface {
	
	public void select();
	
	public List<ColumnReplyDTO> selectAll(int Column_seq, int pageNo);
	
	public boolean insert(int columnNumber, String content, String id);
	
	public boolean delete(Map<String, Object> map);
	
	public boolean update(int upNum, String updatedContents);
}
