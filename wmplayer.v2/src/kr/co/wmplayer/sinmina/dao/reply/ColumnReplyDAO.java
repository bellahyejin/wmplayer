package kr.co.wmplayer.sinmina.dao.reply;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import kr.co.wmplayer.sinmina.interfaces.ColumnRelyInterface;
import kr.co.wmplayer.sinmina.model.dto.reply.ColumnReplyDTO;

public class ColumnReplyDAO implements ColumnRelyInterface{
	
	@Autowired
	private SqlSession session;
	
	@Override
	public void select() {
		
	}

	@Override
	public List<ColumnReplyDTO> selectAll(int Column_seq, int pageNo) {
		int firstPage;
		if(pageNo==1)firstPage=0;
		else firstPage = (pageNo-1)*10;
		List<ColumnReplyDTO> reples=null;
			reples = session.selectList("column.repleSelectAll", Column_seq, new RowBounds(firstPage, 10));
		return reples;
	}

	@Override
	public boolean insert(int column_seq, String content, String id) {
		
		ColumnReplyDTO repleDTO = new ColumnReplyDTO(column_seq,id,content);
			
		int	t = session.insert("column.inReple", repleDTO);
		if(t == 1) return true;
		else
			return false;
	}

	@Override
	public boolean delete(int delNum) {
		
		int t =	session.delete("column.deleteReple", delNum);
		if(t == 1) return true;
		else return false;
	}

	@Override
	public boolean update(int upNum, String updatedContents) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("upNum", upNum);
		map.put("updatedContents", updatedContents);
		
		int t = session.update("column.updateColumn", map);
		if(t == 1 )return true;
		else return false;
	}
	
	

}
