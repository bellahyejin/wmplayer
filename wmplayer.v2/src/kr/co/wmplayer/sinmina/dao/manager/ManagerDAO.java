package kr.co.wmplayer.sinmina.dao.manager;

import java.util.List;

import kr.co.wmplayer.sinmina.model.dto.user.UserInfoDTO;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import util.ListMap;

public class ManagerDAO {
	
	@Autowired
	private SqlSession session;
	
	public List<UserInfoDTO> selectmemberinfo(ListMap<String, Object> map, int rownum, int max){
		return session.selectList("manager.memberInfo", map,new RowBounds(rownum,max));
	}
	
	public int membercount(ListMap<String, Object> map){
		return session.selectOne("manager.memeberCount",map);
	}
}
