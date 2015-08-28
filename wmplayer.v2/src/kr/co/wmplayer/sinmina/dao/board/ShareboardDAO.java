package kr.co.wmplayer.sinmina.dao.board;

import java.util.List;
import java.util.Map;
import kr.co.wmplayer.sinmina.model.dto.board.BoardUserDTO;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class ShareboardDAO
{
	@Autowired
	private SqlSession session;

	public boolean insert(BoardUserDTO bean)
	{
		return session.insert("share.insert", bean) > 0;
	}

	/**
	 * require : action(count, update), board_seq<br>
	 * option : action = count → none<br>
	 * 		action = update → data = BoardUserDTO(준비중)
	 */
	public boolean update(Map<String, Object> map)
	{
		return session.update("share.update", map) > 0;
	}

	/**
	 * require : get(one, ten, all)<br>
	 * option : get = one → compare_column, value<br>
	 * 		get = ten, all → none
	 */
	@SuppressWarnings("unchecked")
	public <E> E select(Map<String, Object> map, int start, int limit)
	{
		if (start >= 0 && limit > 0) return (E) session.selectList("share.select", map, new RowBounds(start, limit));
		else return (E) session.selectList("share.select", map);
	}

	public Map<String, Object> count(Map<String, Object> map)
	{
		return session.selectOne("common.count", map);
	}

	public boolean updateCnt(int board_seq)
	{
		return session.update("share.updatecnt", board_seq) > 0;
	}

	public List<BoardUserDTO> selectPop()
	{
		return session.selectList("share.selectPop");
	}

	public List<BoardUserDTO> selectMyboard(String userID)
	{
		return session.selectList("share.myboard", userID);
	}
}
