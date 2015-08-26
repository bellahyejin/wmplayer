package kr.co.wmplayer.sinmina.dao.music;

import java.util.List;

import kr.co.wmplayer.sinmina.model.dto.music.FavorDTO;
import kr.co.wmplayer.sinmina.model.dto.music.LikeMusicDTO;
import kr.co.wmplayer.sinmina.model.dto.music.MusicInfoDTO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class MusicDAO {
	
	@Autowired
	private SqlSession session;
	
	public List<MusicInfoDTO> selectTodayList(double temperature){
		System.out.println("selectTodayList session: "+ session);
		List<MusicInfoDTO> list = session.selectList("music.todaylist", temperature);
		return list;
	}
	
	public MusicInfoDTO selectShare(MusicInfoDTO music){
		MusicInfoDTO share = session.selectOne("music.share",music);
		return share;
	}
	
	public void addLike(LikeMusicDTO like){
			session.insert("like.add",like);
	}
	
	public void deleteLike(LikeMusicDTO like){
			session.delete("like.deletelike", like);
	}

	public boolean selectLike(LikeMusicDTO like) {
		int result = session.selectOne("like.selectlike", like);
		if (result == 1) {
			return true;
		} else
			return false;
	}
	
	public boolean addFavor(FavorDTO favor){
		int t = session.insert("like.favoradd",favor);
		if(t == 1) return true;
		else return false;
	}
	
	public boolean insertMusic(MusicInfoDTO musicInfo){
		int t = session.insert("music.addmusic",musicInfo);
		if(t == 1)	return true;
		else return false;
	}
}
