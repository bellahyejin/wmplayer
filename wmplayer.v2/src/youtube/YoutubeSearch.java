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


public class YoutubeSearch {
	private static final String google_key = "AIzaSyALdcD4nkhqTMbfNnfnxmCv2Lhjo9uqCew";

	public static ArrayList<HashMap<String, Object>> findYoutubeId(String music_title, String music_artist) throws MalformedURLException, IOException, InterruptedException {
		ArrayList<HashMap<String, Object>> youtube_music_lists = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> data_list = new ArrayList<HashMap<String, Object>>();
		JSONFileParser jfp = new JSONFileParser();
		String youtube_music_url_root_key = "/root/items";
		// echonest music_list get youtube music_url_list
		Map<String, Boolean> youtube_music_url_key_name_list = new HashMap<String, Boolean>();
		youtube_music_url_key_name_list.put("/id/videoId", false); // url
		youtube_music_url_key_name_list.put("/snippet/title", false);
		youtube_music_url_key_name_list.put("/snippet/thumbnails/high/url",
				false);
		String encd = "ms949", decd = "euc-kr";
		String coded_artist = null;
		String coded_music_name = null;
		try {
			coded_artist = URLEncoder.encode(music_artist, "utf-8");
			coded_music_name = URLEncoder.encode(music_title, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return null;
		}
		
		String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&regionCode=KR&type=video&maxResults=1&q="
				+ coded_artist.replace(" ", "%20")
				+ "-"
				+ coded_music_name.replace(" ", "%20") + "&key=" + google_key;

		if (jfp.setUrl(url)) {
			data_list = jfp.parser(youtube_music_url_root_key,
					youtube_music_url_key_name_list);
			if (data_list.size() == 0) {
				youtube_music_lists.add(new HashMap<String, Object>());
			} else {
				boolean found = false;

				if (youtube_music_lists.size() == 0) {
					if (found = data_list.get(0).get("/snippet/title")
							.toString().toLowerCase()
							.contains(music_title.toLowerCase()))
						youtube_music_lists
								.add((HashMap<String, Object>) data_list.get(0));
					else {
						youtube_music_lists.add(new HashMap<String, Object>());
					}
				} else {
					if (found = data_list.get(0).get("/snippet/title")
							.toString().toLowerCase()
							.contains(music_title.toLowerCase()))
						youtube_music_lists
								.add((HashMap<String, Object>) data_list.get(0));
					else {
						youtube_music_lists.add(new HashMap<String, Object>());
					}
				}

				System.out.println(music_artist + " " + music_title
						+ (found ? " found" : " not found"));
				System.out.println(youtube_music_lists.get(0).toString());
			}
		} else {
			return null;
		}
		return youtube_music_lists;
	}
	public static String getTitle(String title, String artist) throws MalformedURLException, IOException, InterruptedException{
		ArrayList<HashMap<String, Object>> videoinfo = YoutubeSearch.findYoutubeId(title, artist);
		return (String) videoinfo.get(0).get("/id/title");
	}
	public static String getYoutubeId(String title, String artist) throws MalformedURLException, IOException, InterruptedException{
		ArrayList<HashMap<String, Object>> videoinfo = YoutubeSearch.findYoutubeId(title, artist);
		return (String) videoinfo.get(0).get("/id/videoId");
	}
	
	public static String getThumnailAddr(String title, String artist) throws MalformedURLException, IOException, InterruptedException{
		ArrayList<HashMap<String, Object>> videoinfo = YoutubeSearch.findYoutubeId(title, artist);
		return (String) videoinfo.get(0).get("/snippet/thumbnails/high/url");
	}
}

