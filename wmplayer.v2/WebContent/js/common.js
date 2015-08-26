function setLink(form_name, url, action)
{
	$(form_name == null ? "<form></form>" : form_name).attr("action", "${ initParam.root }/" + url).attr("method", "post").append($("<input></input>").attr("name", "action").attr("type", "hidden").attr("value", action)).submit();
}
