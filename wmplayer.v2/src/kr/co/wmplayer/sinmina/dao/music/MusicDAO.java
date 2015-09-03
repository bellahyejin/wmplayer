package kr.co.wmplayer.sinmina.dao.music;

import java.util.List;

import kr.co.wmplayer.sinmina.model.dto.BpmInfoDTO;
import kr.co.wmplayer.sinmina.model.dto.music.FavorDTO;
import kr.co.wmplayer.sinmina.model.dto.music.LikeMusicDTO;
import kr.co.wmplayer.sinmina.model.dto.music.MusicInfoDTO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class MusicDAO {
	
	@Autowired
	private SqlSession session;
	
	public List<MusicInfoDTO> selectTodayList(double temperature){
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
	
	public List<LikeMusicDTO> selectLikeMusic(String userid){
		List<LikeMusicDTO> like = session.selectList("like.selectLikeMusic", userid);
		return like;
	}
	
	public MusicInfoDTO likemusic(String musicid){
		MusicInfoDTO like = session.selectOne("music.likemusic", musicid);
		return like;
	}
	
	public double selectBpm(String musicID){
		return session.selectOne("music.selectbpm",musicID);
	}
	
	public double avgBpm(String userID){
		return session.selectOne("like.avgBpm",userID);
	}
	
	public BpmInfoDTO todaybpm(double temperature){
		return session.selectOne("music.todaybpm", temperature);
	}
}
