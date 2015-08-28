package kr.co.wmplayer.sinmina.dao.board;

import java.util.List;
import java.util.Map;

import kr.co.wmplayer.sinmina.model.dto.board.NoticeBoardDTO;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class NoticeboardDAO {
	
	@Autowired
	private SqlSession session;
	
	public List<String> hometitle(){
		List<String> notice = session.selectList("notice.noticeHomeTitle", null, new RowBounds(0, 5));
		return notice;
	}
	
	public NoticeBoardDTO select(int notice_seq){
		NoticeBoardDTO notice = session.selectOne("notice.boardSelect",notice_seq);
		return notice;
		
	}
	
	public void updateView(int notice_seq){
		session.update("notice.cntplus",notice_seq);
	}
	
	public List<String> selectSeq(){
		List<String> notice = session.selectList("notice.selectSeq");
		return notice;
	}
	
	public int dataSize(){
		return session.selectOne("notice.noticeSelectAll");
	}
	
	public List<NoticeBoardDTO> selectAll(int start, int max){
		List<NoticeBoardDTO> notice = session.selectList("notice.boardList",null,new RowBounds(start,max));
		return notice;
	}
	
	public void insert(Map<String, Object> map){
		session.insert("notice.noticeInsert",map);
	}
}
