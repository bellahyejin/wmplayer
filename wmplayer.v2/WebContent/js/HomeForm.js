$("document").ready(
	function ()
	{
		var i = 1;
		var notice = $(".notice");
		var temp = (notice.prop("clientHeight")) * 2;
		var scroll_time = 2000;
		var stop_time = 3000;

		setInterval(function ()
			{
				if (notice.scrollTop() <= 0 || notice.scrollTop() >= notice.prop("scrollHeight") - temp)
				{
					i = 1;
					notice.scrollTop(0);
				}

				i++;
				notice.animate(
					{
						scrollTop : (i - 1) * temp
					}, scroll_time);

			}, scroll_time + stop_time);

	});
