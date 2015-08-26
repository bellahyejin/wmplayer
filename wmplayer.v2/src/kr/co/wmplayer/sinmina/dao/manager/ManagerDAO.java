package kr.co.wmplayer.sinmina.dao.manager;

import java.util.List;
import java.util.Map;
import kr.co.wmplayer.sinmina.model.dto.manager.DropReasonDTO;
import kr.co.wmplayer.sinmina.model.dto.user.UserInfoDTO;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class ManagerDAO
{
	@Autowired
	private SqlSession session;

	public List<UserInfoDTO> selectmemberinfo(Map<String, Object> map, int rownum, int max)
	{
		return session.selectList("manager.memberInfo", map, new RowBounds(rownum, max));
	}

	public int membercount(Map<String, Object> map)
	{
		return session.selectOne("manager.memberCount", map);
	}

	public List<DropReasonDTO> reasonselect(Map<String, Object> map)
	{
		return session.selectList("manager.reasonSelect", map);
	}

	public Map<String, Object> statcount(Map<String, Object> map)
	{
		return session.selectOne("manager.statCount", map);
	}
}
