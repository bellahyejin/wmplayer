package paser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLFileDOMParser
{
	private Document doc;
	private DocumentBuilder builder;
	
	private String xmlData;
	private List<HashMap<String, Object>> data;
	private Map<String, Object> map;
	private List<String> location_list;
	
	public XMLFileDOMParser()
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			builder = factory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean setUrl(String url)
	{
		try
		{
			return (doc = builder.parse(new URL(url).openStream())) != null;
		}
		catch (SAXException | IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean readFile(String file_path)
	{
		try
		{
			return (doc = builder.parse(new FileInputStream(new File(file_path)))) != null;
		}
		catch (SAXException | IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean writeFile(String file_path)
	{
		boolean isNull = true;
		OutputStream osw = null;
		
		try
		{
			isNull &= (osw = new FileOutputStream(new File(file_path))) != null;
			if (isNull &= xmlData != null) osw.write(xmlData.getBytes());
			osw.close();
			
			return isNull;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public List<HashMap<String, Object>> parser(String root_key, Map<String, Boolean> key_name)
	{
		data = new ArrayList<HashMap<String, Object>> ();
		location_list = new ArrayList<String> ();
		processNode(new String(), doc, 0, root_key, key_name);
		return data;
	}
	
	private void processNode(String location, Node node, int depth, String root_key, Map<String, Boolean> key_name)
	{
		xmlData = new String();
		
		switch (node.getNodeType())
		{
			case Node.ELEMENT_NODE : 
				Element element = (Element) node;
				String tag_name = element.getNodeName();
				
				location = location.concat("/" + tag_name);
				xmlData = xmlData.concat(addIndent(depth) + ("<" + tag_name));
				
				if (location.equals(root_key))
				{
					map = new HashMap<String, Object> ();
				}
				
				if (element.hasAttributes())
				{
					NamedNodeMap element_attr = element.getAttributes();
					for (int i = 0; i < element_attr.getLength(); i++)
					{
						processNode(location, element_attr.item(i), depth, root_key, key_name);
						System.out.print("");
					}
					xmlData += ">\n";
				}
				
				if (element.hasChildNodes())
				{
					NodeList element_child = element.getChildNodes();
					for (int i = 0; i < element_child.getLength(); i++)
					{
						processNode(location, element_child.item(i), depth + 1, root_key, key_name);
						System.out.print("");
					}
				}
				
				if (location.equals(root_key))
				{
					data.add((HashMap<String, Object>) map);
				}
				
				location_list.remove(location);
				
				location = location.substring(0, location.lastIndexOf("/"));
				
				xmlData = xmlData.concat(addIndent(depth) + "</" + tag_name + ">" + (depth == 0 ? "" : "\n"));
				break;
			case Node.ATTRIBUTE_NODE : 
				String attr_name = node.getNodeName();
				String attr_value = node.getNodeValue();
				
				location = location.concat("." + attr_name);
				
				for (String key : key_name.keySet()) if (location.equals(root_key.concat(key))) map.put(key, attr_value);
				xmlData = xmlData.concat(" " + attr_name + " = \"" + attr_value + "\"");
				
				location = location.substring(0, location.lastIndexOf("."));
				break;
			case Node.TEXT_NODE : 
				String text_value = node.getNodeValue().replace("&", "&amp;").replace("&amp;amp;", "&amp;").trim();
				
				if (location_list.indexOf(location) == -1) for (String key : key_name.keySet()) if (location.equals(root_key.concat(key))) map.put(key, text_value);
				xmlData = xmlData.concat(addIndent(depth) + text_value + "\n");
				
				location_list.add(location);
				break;
			case Node.CDATA_SECTION_NODE : 
				xmlData = xmlData.concat(addIndent(depth) + "<![CDATA[" + node.getNodeValue() + "]]>\n");
				break;
			case Node.ENTITY_REFERENCE_NODE : 
				// xmlData = xmlData.concat(addIndent(depth) + "Entity Reference = " + n.getNodeName() + "\n");
				break;
			case Node.ENTITY_NODE : 
				// xmlData = xmlData.concat(addIndent(depth) + "<!ENTITY " + n.getNodeName() + " " + ">\n");
				break;
			case Node.PROCESSING_INSTRUCTION_NODE : 
				// xmlData = xmlData.concat(addIndent(depth) + "Processing Instruction = " + n.getNodeName() + ", " + n.getNodeValue() + "\n");
				break;
			case Node.COMMENT_NODE : 
				// xmlData = xmlData.concat(addIndent(depth) + "<!-- " + n.getNodeValue() + " -->\n");
				break;
			case Node.DOCUMENT_NODE : 
				if (node.hasChildNodes())
				{
					NodeList document_list = node.getChildNodes();
					location = "/document";
					xmlData.concat("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
					for (int i = 0; i < document_list.getLength(); i++) processNode(location, document_list.item(i), depth + 1, root_key, key_name);
				}
				break;
			case Node.DOCUMENT_TYPE_NODE : 
				// xmlData = xmlData.concat(addIndent(depth) + "Document Type = " + n.getNodeName() + "\n");
				break;
			case Node.DOCUMENT_FRAGMENT_NODE : 
				// xmlData = xmlData.concat(addIndent(depth) + "Document Fragment = " + n.getNodeName() + "\n");
				break;
			case Node.NOTATION_NODE : 
				// xmlData = xmlData.concat(addIndent(depth) + "<!NOTATION " + n.getNodeName() + " " + ">\n");
				break;
			default : 
				return;
		}
	}
	
	private String addIndent(int depth)
	{
		String temp = new String();
		
		for (int i = 0; i < depth - 1; i++) temp += "\t";
		
		return temp;
	}

	public static void main(String[] args)
	{
		XMLFileDOMParser xfp = new XMLFileDOMParser();
		
		String url = "http://www.kma.go.kr/wid/queryDFS.jsp?gridx=50&gridy=55";
		String root_key = "/document/wid/body/data";
		Map<String, Boolean> key_name = new HashMap<String, Boolean> ();
		key_name.put(".seq", false);
		key_name.put("/hour", false);
		key_name.put("/day", false);
		
		if (xfp.setUrl(url))
		{
			List<HashMap<String, Object>> data = xfp.parser(root_key, key_name);
			
			for (Map<String, Object> map : data) for (String key : key_name.keySet()) System.out.println(key + " " + map.get(key));
		}
		else return;
		
		String file_path = "WebContent/TodayMusic/receiveData.jsp";
		root_key = "/document/todaymusic/music";
		key_name.clear();
		key_name.put("/title", false);
		key_name.put("/artist", false);
		key_name.put("/image", false);
		
		if (xfp.readFile(file_path))
		{
			List<HashMap<String, Object>> list = xfp.parser(root_key, key_name);
			
			System.out.println(list);
		}
	}
}
