package paser;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLParser
{
	private Document d;
	
	public XMLParser(String url)
	{
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			d = db.parse(url);
		}
		catch (ParserConfigurationException | SAXException | IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public Document getDocument()
	{
		return d;
	}
}
