package kr.co.wmplayer.sinmina.dao.user;

import java.util.Map;
import kr.co.wmplayer.sinmina.model.dto.user.UserInfoDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class UserInfoDAO {

	@Autowired
	private SqlSession session;

	public boolean loginLogInsert(String id) {
		int t = session.insert("user.loginloginsert", id);
		if (t == 1) return true;
		else return false;
	}

	public boolean dropReason(String drop) {
		if (Integer.parseInt(session.selectOne("user.reasonselect", drop).toString()) > 0)
			session.update("user.reasonupdate", drop);
		else
			session.insert("user.dropinsert", drop);
		return true;
	}

	public boolean infoInsert(UserInfoDTO user) {
		int t = session.insert("user.userinfoinsert", user);
		if (t == 1)
			return true;
		else
			return false;
	}// insert

	public boolean update(UserInfoDTO user) {
		int t = session.update("user.userinfoupdate", user);
		if (t == 1)
			return true;
		else
			return false;
	}// update

	public boolean dropupdate(int drop) {
		int t = session.update("user.dropupdate", drop);
		if (t == 1)
			return true;
		else
			return false;
	}

	public boolean delete(String userID) {
		int t = session.delete("user.userinfodrop", userID);
		if (t == 1)
			return true;
		else
			return false;
	}// delete

	public UserInfoDTO select(String userID) {
		UserInfoDTO user = session.selectOne("user.userselect", userID);
		return user;
	}// select

	public int selectcheck(String userID) {
		return (int) session.selectOne("user.useridcheck", userID);
	}

	public int loginproccess(UserInfoDTO user)
	{
		int t = session.selectOne("user.logincheck", user);
		return t;
	}
	
	public String findid(UserInfoDTO user){
		System.out.println(user.getName());
		String id = session.selectOne("user.findid",user);
		System.out.println(id);
		return id;
	}
	
	public String findpass(UserInfoDTO user){
		return session.selectOne("user.findpass", user);
	}

	public boolean statusupdate(Map<String, Object> map)
	{
		if (session.update("user.statusupdate", map) == 1) return true;
		else return false;
	}

	public boolean loginlog(String userid)
	{
		if (session.insert("user.loginloginsert", userid) == 1) return true;
		else return false;
	}
}
