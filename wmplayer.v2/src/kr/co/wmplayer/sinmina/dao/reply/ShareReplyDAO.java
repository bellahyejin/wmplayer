package kr.co.wmplayer.sinmina.dao.reply;

import java.util.List;
import kr.co.wmplayer.sinmina.model.dto.reply.ShareReplyDTO;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class ShareReplyDAO
{
	@Autowired
	private SqlSession session;

	public List<ShareReplyDTO> select(int board_seq, int idx)
	{
		if (idx >= 0) return session.selectList("share.repleselect", board_seq, new RowBounds(idx, 10));
		else return session.selectList("share.repleselect", board_seq);
	}

	public boolean insert(ShareReplyDTO bean)
	{
		return session.insert("share.repleinsert", bean) > 0;
	}

	public boolean delete(ShareReplyDTO bean)
	{
		return session.delete("share.repledelete", bean) > 0;
	}

	public boolean update(ShareReplyDTO bean)
	{
		return session.update("share.repleupdate", bean) > 0;
	}
}
