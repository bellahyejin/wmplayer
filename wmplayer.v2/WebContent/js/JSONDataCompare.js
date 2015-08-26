function JSONDataCompare(a, b)
{
	var i = 0, j;

	if (typeof a == "object" && a)
	{
		if (Array.isArray(a))
		{
			if (!Array.isArray(b) || a.length != b.length) return false;

			for (j = a.length; i < j; i++) if (!JSONDataCompare(a[i], b[i])) return false;

			return true;
		}
		else
		{
			for (j in b) if (b.hasOwnProperty(j)) i++;
			for (j in a) if (a.hasOwnProperty(j))
			{
				if (!JSONDataCompare(a[j], b[j])) return false;
				i--;
			}
			return !i;
		}
	}
	return a === b;
}
