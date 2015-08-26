package kr.co.wmplayer.sinmina.dao.reply;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import kr.co.wmplayer.sinmina.interfaces.ShareRelyInterface;
import kr.co.wmplayer.sinmina.model.dto.reply.ShareReplyDTO;

public class ShareReplyDAO implements ShareRelyInterface{

	@Autowired
	private SqlSession session;
	
	@Override
	public void select() {
		
	}

	@Override
	public List<ShareReplyDTO> selectAll(int board_seq, int pageNo) {
		int firstPage;
		if(pageNo==1)firstPage=0;
		else firstPage = (pageNo-1)*10;
		List<ShareReplyDTO> reples= session.selectList("column.repleSelectAll", board_seq,new RowBounds(firstPage,10));
		return reples;
	}

	@Override
	public boolean insert(int board_seq, String content, String id) {
		
		ShareReplyDTO repleDTO = new ShareReplyDTO(board_seq,content,id);
		int t = session.insert("share.inReple", repleDTO);
		if(t == 1) return true;
		return false;
	}

	@Override
	public boolean delete(int delNum) {
		
		int t = session.delete("share.deleteReple", delNum);
		if(t ==1)
			return true;
		else 
			return false;
	}

	@Override
	public boolean update(int upNum, String updatedContents) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("upNum", upNum);
		map.put("updatedContents", updatedContents);
		
		
		int t = session.update("share.updateColumn", map);
		if(t == 1) return true;
		else return false;
	}
}
