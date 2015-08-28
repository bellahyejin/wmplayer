function setLink(form_name, url, action, params)
{
	var form;
	if (form_name == null) form = $("<form></form>");
	else form = $(form_name);

	form.attr("action", "/SpringWeb/" + url).attr("method", "post");
	if (action != null) form.append($("<input></input>").attr("type", "hidden").attr("name", "action").attr("value", action));
	if (params != null) for (var key in params) form.append($("<input></input>").attr("type", "hidden").attr("name", key).attr("value", params[key]));
	form.submit();
}
