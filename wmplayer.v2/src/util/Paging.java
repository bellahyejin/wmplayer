package util;

import java.util.HashMap;
import java.util.Map;

public class Paging
{
	private static Paging instance;
	Map<String, Integer> map;

	private Paging() { }

	public static Paging getInstance()
	{
		return instance == null ? instance = new Paging() : instance;
	}

	private void init()
	{
		map = new HashMap<String, Integer>();
	}

	/**
	 * @param total_element
	 * @param present_page
	 * @param max_element : page per max element<br>
	 * ex) total_element = 46, max_element = 5<br>
	 * -> max_page = 10<br>
	 * ex) total_element = 45, max_element = 5<br>
	 * -> max_page = 9<br>
	 * @param page_divisor
	 * @param mode<br>
	 * mode = "prev&next"<br>
	 * ex) 1 prev_page(1) 1(start_page) 2 3 4(end_page) next_page(6) last_page(max_page)<br>
	 * and 1 prev_page(4) 5(start_page) 6 7 8(end_page) next_page(9) last_page(max_page)<br>
	 * -> page_divisor = 4<br>
	 * ex) 1 prev_page(7) 8 9 10 11 12 13 14 next_page(15) last_page(max_page)<br>
	 * and 1 prev_page(14) 15 16 17 18 19 20 21 next_page(22) last_page(max_page)<br>
	 * -> page_divisor = 7<br><br>
	 * mode = "begin&last"<br>
	 * ex) 1 3(start_page) 4 5 6 7 8 9(end_page) last_page(max_page)<br>
	 * or prev_page(2) 3(start_page) 4 5 6 7 8 9(end_page) next_page(10) or<br>
	 * or prev_page(5) 1 6(start_page) 7 8 9 10 11 12(end_page) last_page(max_page) next_page(13)<br>
	 * -> page_divisor = 3<br>
	 * ex) 1 10(start_page) 11 12 13 14(end_page) last_page(max_page) or<br>
	 * or prev_page(9) 10(start_page) 11 12 13 14(end_page) next(15) or<br>
	 * or prev_page(5) 1 6(start_page) 7 8 9 10(end_page) last_page(max_page) next_page(13)<br>
	 * -> page_divisor = 2
	 * @return map<br>
	 * returns key : item_start, item_end, begin, last, start, end, prev, next
	 * returns value :  item_start_index, item_end_index, begin_page, last_page, start_page, end_page, previous_page, next_page
	 */
	public Map<String, Integer> setData(int total_element, int present_page, int max_element, int page_divisor, String mode, boolean flag)
	{
		if (total_element < 0 || present_page <= 0 || max_element <= 0 || page_divisor <= 0) throw new IndexOutOfBoundsException();
		calcData(total_element, present_page, max_element, page_divisor, mode, flag);
		return map;
	}

	private void calcData(int total, int pres_page, int max_elem, int page_div, String mode, boolean flag)
	{
		int start_idx, end_idx, start_page, end_page, begin_page = 1, last_page, prev_page, next_page;
		init();

		if (total > max_elem)
		{
			start_idx = (pres_page - 1) * max_elem + (flag ? 1 : 0);
			end_idx = (end_idx = start_idx + max_elem - 1) > total ? total : end_idx;
			last_page = ((int) total / max_elem) + (total % max_elem == 0 ? 0 : 1);

			switch (mode)
			{
				case "prev&next" :
					start_page = ((int)((pres_page - 1) / page_div)) * page_div + 1;
					end_page = (end_page = start_page + page_div - 1) > last_page ? last_page : end_page;
					break;
				case "begin&last" :
					start_page = (pres_page <= 1 + page_div || 1 + page_div * 2 >= last_page ? 1 : (pres_page + page_div >= last_page ? page_div * 2 : pres_page - page_div));
					end_page = (end_page = start_page + page_div * 2) > last_page ? last_page : end_page;
					break;
				default :
					throw new UnsupportedOperationException("must set flag = \"prev&next\" or \"begin&last\"");
			}

			prev_page = (prev_page = start_page - 1) <= 0 ? 1 : prev_page;
			next_page = (next_page = end_page + 1) > last_page ? last_page : next_page;
		}
		else
		{
			start_idx = 0;
			end_idx = total;
			start_page = end_page = last_page = prev_page = next_page = 1;
		}

		map.put("begin", begin_page);
		map.put("last", last_page);
		map.put("item_start", start_idx);
		map.put("item_end", end_idx);
		map.put("start", start_page);
		map.put("end", end_page);
		map.put("prev", prev_page);
		map.put("next", next_page);
	}
}
