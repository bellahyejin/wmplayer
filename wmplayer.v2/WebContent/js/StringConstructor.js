String.prototype.trim = function()
{
	return this.replace(/(^\s*)|(\s\s*\s\s)|(\s*$)/gi, "");
}

String.prototype.ltrim = function()
{
	return this.replace(/(^\s*)/gi, "");
}

String.prototype.ctrim = function()
{
	return this.replace(/(\s\s*\s\s)/gi, "");
}

String.prototype.rtrim = function()
{
	return this.replace(/(\s*$)/gi, "");
}

String.prototype.equals = function(com_str)
{
	var str = this;

	if (str.length != com_str.length) return false;
	else
	{
		var check = true;
		for (var i = 0; i < str.length; i++) check &= str.charAt(i) == com_str.charAt(i);
		return check;
	}
}

String.prototype.split = function(split_str)
{
	var array = new Array();
	var str = this;

	if (str != null && split_str != null && split_str != "")
	{
		var array_idx = 0;
		var split_idx = str.indexOf(split_str);

		do
		{
			array[array_idx++] = str.substring(0, split_idx == -1 ? str.length : split_idx);
			str = split_idx == -1 ? "" : str.substring(split_idx + split_str.length);
		}
		while (!((split_idx = str.indexOf(split_str)) == -1 && str == ""));
	}
	else array[0] = split_str;

	return array;
}

String.prototype.contains = function(str)
{
	return this.indexOf(str) != -1;
}
