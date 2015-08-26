package kr.co.wmplayer.sinmina.dao.board;

import java.util.List;
import java.util.Map;

import kr.co.wmplayer.sinmina.model.dto.board.ColumnBoardDTO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class ColumnboardDAO {
	
	@Autowired
	private SqlSession session;
	
	public List<ColumnBoardDTO> selectHomerank(){
		List<ColumnBoardDTO> list = session.selectList("column.homerank"); 
		return list;
	}
	
	public boolean columnInsert(Map<String, String> map){
		int t = session.insert("column.columnInsert", map);
			
		if(t == 1)	return true;
		else return false;
	}
	
	public List<ColumnBoardDTO> columnSelectAll(int start){
		List<ColumnBoardDTO> columnDTO = session.selectList("column.columnSelectAll", start);
		return columnDTO;
	}
	
	public  ColumnBoardDTO select(int column_seq){
		ColumnBoardDTO column =	session.selectOne("column.boardSelect", column_seq);
		return column;
	}
	
	public int dataSize(){
		int size = (int) session.selectOne("column.columnSize");
		return size;
	}
	
	public void updatecnt(int column_seq){
		session.update("column.cntplus", column_seq);
	}
	
	public List<String> selectSeq(){
		List<String> column = session.selectList("column.selectSeq");
		return column;
	}
	
	public int countReply(int column_seq){
		return session.selectOne("column.countReple");
	}
	
}
