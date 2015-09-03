package kr.co.wmplayer.sinmina.dao.reply;

import java.util.HashMap;
import java.util.List;
import kr.co.wmplayer.sinmina.model.dto.reply.ShareReplyDTO;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class ShareReplyDAO
{
	@Autowired
	private SqlSession session;

	public List<ShareReplyDTO> selectAll(int board_seq, int idx)
	{
		if (idx >= 0) return session.selectList("share.repleSelectAll", board_seq, new RowBounds(idx, 10));
		else return session.selectList("share.repleSelectAll", board_seq);
	}

	public boolean insert(int board_seq, String content, String id)
	{
		ShareReplyDTO repleDTO = new ShareReplyDTO(board_seq, content, id);
		int t = session.insert("share.inReple", repleDTO);
		if (t == 1) return true;
		return false;
	}

	public boolean delete(int delNum)
	{
		int t = session.delete("share.deleteReple", delNum);
		if (t == 1) return true;
		else return false;
	}

	public boolean update(int upNum, String updatedContents)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("upNum", upNum);
		map.put("updatedContents", updatedContents);

		int t = session.update("share.updateColumn", map);
		if (t == 1) return true;
		else return false;
	}
}
