package kr.co.wmplayer.sinmina.dao.board;

import java.util.List;

import kr.co.wmplayer.sinmina.model.dto.board.BoardUserDTO;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class ShareboardDAO {
	@Autowired
	private SqlSession session;
	
	public boolean insertShare(BoardUserDTO board){
		int t =	session.insert("share.input",board);
		if(t == 1)return true;
		else return false;
	}
	
	public List<BoardUserDTO> selecAllShare(int start){
		List<BoardUserDTO> list = session.selectList("share.selectCard", start);
		return list;
	}
	
	public int dataSize(String data){
		int size = session.selectOne("share.selectSize",data);
		return size;
	}
	
	public BoardUserDTO selectDetail(int board_seq){
		BoardUserDTO detail = session.selectOne("share.detail",board_seq);
		return detail;
	}
	
	public void updateCnt(int board_seq){
			session.update("share.updatecnt",board_seq);
	}
	
	public List<String> selectSeq(){
		List<String> seqlist = session.selectList("share.selectSeq");
		return seqlist;
	}
	
	public List<BoardUserDTO> selectPop(){
		List<BoardUserDTO> list = session.selectList("share.selectPop");
		return list;
	}
	public List<BoardUserDTO> selectMyboard(String userID){
		List<BoardUserDTO> list = session.selectList("share.myboard",userID);
		return list;
	}
	
	public List<BoardUserDTO> selectSortboard(String map, int start){
		List<BoardUserDTO> list = session.selectList("share.selectSortCard",map,new RowBounds(start,9));
		return list;
	}
}
