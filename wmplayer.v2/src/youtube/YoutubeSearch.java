package youtube;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import paser.JSONFileParser;

public class YoutubeSearch
{
	private static final String google_key = "AIzaSyALdcD4nkhqTMbfNnfnxmCv2Lhjo9uqCew";
	private static YoutubeSearch youtubeSearch;

	public static YoutubeSearch getInstance()
	{
		return youtubeSearch == null ? youtubeSearch = new YoutubeSearch() : youtubeSearch;
	}

	public ArrayList<HashMap<String, Object>> findYoutubeId(String music_title, String music_artist) throws MalformedURLException, IOException, InterruptedException
	{
		ArrayList<HashMap<String, Object>> temp = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> data_list;
		JSONFileParser jfp = new JSONFileParser();
		String youtube_music_url_root_key = "/items";

		Map<String, Boolean> youtube_music_url_key_name_list = new HashMap<String, Boolean>();
		youtube_music_url_key_name_list.put("/id/videoId", false); // url
		youtube_music_url_key_name_list.put("/snippet/title", false);
		youtube_music_url_key_name_list.put("/snippet/thumbnails/high/url", false);
		String coded_artist = null;
		String coded_music_name = null;
		try
		{
			coded_artist = URLEncoder.encode(music_artist, "utf-8");
			coded_music_name = URLEncoder.encode(music_title, "utf-8");
		}
		catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
			return null;
		}

		String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&regionCode=KR&type=video&maxResults=1&q=" + coded_artist.replace(" ", "%20") + "-" + coded_music_name.replace(" ", "%20") + "&key=" + google_key;

		if (jfp.setUrl(url))
		{
			data_list = jfp.parser(youtube_music_url_root_key, youtube_music_url_key_name_list);
			if (data_list.size() == 0) temp.add(new HashMap<String, Object>());
			else
			{
				if (data_list.get(0).get("/snippet/title").toString().toLowerCase().contains(music_title.toLowerCase())) temp.add((HashMap<String, Object>) data_list.get(0));
				else temp.add(new HashMap<String, Object>());
			}
		}
		else return null;

		return temp;
	}

	ArrayList<HashMap<String, Object>> videoinfo;

	public String getTitle(String title, String artist) throws MalformedURLException, IOException, InterruptedException
	{
		videoinfo = videoinfo == null ? videoinfo = findYoutubeId(title, artist) : videoinfo;
		return getData(videoinfo, "/id/title");
	}

	public String getYoutubeId(String title, String artist) throws MalformedURLException, IOException, InterruptedException
	{
		videoinfo = videoinfo == null ? videoinfo = findYoutubeId(title, artist) : videoinfo;
		return getData(videoinfo, "/id/videoId");
	}

	public String getThumnailAddr(String title, String artist) throws MalformedURLException, IOException, InterruptedException
	{
		videoinfo = videoinfo == null ? videoinfo = findYoutubeId(title, artist) : videoinfo;
		return getData(videoinfo, "/snippet/thumbnails/high/url");
	}

	public String getData(List<HashMap<String, Object>> data, String key)
	{
		return data == null ? null : (String) data.get(0).get(key);
	}
}
