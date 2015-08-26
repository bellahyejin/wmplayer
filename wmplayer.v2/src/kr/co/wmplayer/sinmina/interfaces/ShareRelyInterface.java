package kr.co.wmplayer.sinmina.interfaces;

import java.util.List;

import kr.co.wmplayer.sinmina.model.dto.reply.ShareReplyDTO;


public interface ShareRelyInterface {
	
	public void select();
	
	public List<ShareReplyDTO> selectAll(int board_seq, int pageNo);
	
	public boolean insert(int shareNumber, String content, String id);
	
	public boolean delete(int delNum);
	
	public boolean update(int upNum, String updatedContents);
}
