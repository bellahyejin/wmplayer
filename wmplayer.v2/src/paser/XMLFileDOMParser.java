package paser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import util.ListMap;

public class XMLFileDOMParser
{
	private Document doc;
	private DocumentBuilder builder;
	private StringBuffer xmlData;
	private List<Map<CharSequence, Object>> data;
	private Map<CharSequence, Object> map;
	private Set<CharSequence> column;

	private XMLFileDOMParser()
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

	public static XMLFileDOMParser newInstance()
	{
		return new XMLFileDOMParser();
	}

	public static void main(String[] args)
	{
		XMLFileDOMParser xfp = new XMLFileDOMParser();

		String url = "http://www.kma.go.kr/wid/queryDFS.jsp?gridx=50&gridy=55";
		String root_key = "wid/body/data";
		Map<CharSequence, Boolean> key_name = new ListMap<CharSequence, Boolean> ();
		key_name.put(".seq", false);
		key_name.put("/hour", false);
		key_name.put("/day", false);
		key_name.put("/temp", false);

		if (xfp.setUrl(url))
		{
			//System.out.println(xfp.parser());
			for (CharSequence key : xfp.parser(null)) System.out.println(key);
			for (Map<CharSequence, Object> map : xfp.parser(root_key, key_name)) System.out.println(map);
		}
		else return;
	}

	public boolean setUrl(CharSequence url_string)
	{
		try
		{
			int status_code;
			URL url = new URL(url_string.toString());

			while ((status_code = ((HttpURLConnection) url.openConnection()).getResponseCode()) != 200)
			{
				if (status_code == 400) return false;
				int sleep = (int) (Math.random() * 10000) + 5000;
				System.out.println(status_code + " error " + sleep + " millisecond sleep");
				Thread.sleep(sleep);
			}

			return (doc = builder.parse(url.openStream())) != null;
		}
		catch (SAXException | IOException | InterruptedException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean readFile(CharSequence file_path)
	{
		try
		{
			return (doc = builder.parse(new FileInputStream(new File(file_path.toString())))) != null;
		}
		catch (SAXException | IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean writeFile(CharSequence file_path)
	{
		boolean isNull = true;
		OutputStream osw = null;

		try
		{
			isNull &= (osw = new FileOutputStream(new File(file_path.toString()))) != null;
			if (isNull &= xmlData != null) osw.write(xmlData.toString().getBytes());
			osw.close();

			return isNull;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public String parser()
	{
		parser(doc, null, null);
		return xmlData.toString();
	}

	public Set<CharSequence> parser(CharSequence root_key)
	{
		parser(doc, root_key, null);
		return column;
	}

	public List<Map<CharSequence, Object>> parser(CharSequence root_key, Map<CharSequence, Boolean> key_name)
	{
		parser(doc, root_key, key_name);
		return data;
	}

	private void parser(Document doc, CharSequence root_key, Map<CharSequence, Boolean> key_name)
	{
		init();
		processNode(new String(), doc, 0, "document".concat(root_key == null ? "" : "/".concat(root_key.toString())), key_name);
	}

	private void init()
	{
		data = new ArrayList<Map<CharSequence, Object>>();
		column = new LinkedHashSet<CharSequence>();
		xmlData = new StringBuffer();
	}

	private void processNode(CharSequence location, Node node, int depth, CharSequence root_key, Map<CharSequence, Boolean> key_name)
	{
		boolean flag = key_name != null;

		switch (node.getNodeType())
		{
			case Node.ELEMENT_NODE :
				Element element = (Element) node;
				String tag_name = element.getNodeName();

				location = location.toString().concat("/").concat(tag_name);
				if (location.equals(root_key)) map = new ListMap<CharSequence, Object>();
				xmlData = xmlData.append(addIndent(depth)).append("<" + tag_name);

				if (element.hasAttributes())
				{
					NamedNodeMap element_attr = element.getAttributes();
					for (int i = 0; i < element_attr.getLength(); i++) processNode(location, element_attr.item(i), depth, root_key, key_name);
				}
				xmlData = xmlData.append(">");

				if (element.hasChildNodes())
				{
					NodeList element_child = element.getChildNodes();
					for (int i = 0; i < element_child.getLength(); i++) processNode(location, element_child.item(i), depth + 1, root_key, key_name);
				}

				if (location.equals(root_key)) data.add(map);
				xmlData = xmlData.append(addIndent(depth)).append("</").append(tag_name).append(">");

				location = location.toString().substring(0, location.toString().lastIndexOf("/"));
				break;
			case Node.ATTRIBUTE_NODE :
				String attr_name = node.getNodeName();
				String attr_value = node.getNodeValue();

				location = location.toString().concat("." + attr_name);
				if (location.toString().contains(root_key)) column.add(location.toString().substring(location.toString().indexOf("/") + 1));
				if (flag) for (CharSequence key : key_name.keySet()) if (location.equals(root_key.toString().concat(key.toString()))) map.put(key, attr_value);
				xmlData = xmlData.append(" ").append(attr_name).append(" = \"").append(attr_value).append("\"");

				location = location.toString().substring(0, location.toString().lastIndexOf("."));
				break;
			case Node.TEXT_NODE :
				String text_value = node.getNodeValue().replace("&", "&amp;").replace("&amp;amp;", "&amp;").trim();

				if (flag) for (CharSequence key : key_name.keySet()) if (location.equals(root_key.toString().concat(key.toString()))) map.put(key, text_value);
				xmlData = xmlData.append(text_value).append("\n");

				if (location.toString().contains(root_key)) column.add(location.toString().substring(location.toString().indexOf("/") + 1));
				break;
			case Node.CDATA_SECTION_NODE :
				String cdata_value = node.getNodeValue();

				if (flag) for (CharSequence key : key_name.keySet()) if (location.equals(root_key.toString().concat(key.toString()))) map.put(key, cdata_value);
				xmlData = xmlData.append(addIndent(depth)).append("<![CDATA[").append(cdata_value).append("]]>\n");

				if (location.toString().contains(root_key)) column.add(location.toString().substring(location.toString().indexOf("/") + 1));
				break;
			case Node.ENTITY_REFERENCE_NODE :
				// xmlData = xmlData.append(addIndent(depth) + "Entity Reference = " + n.getNodeName() + "\n");
				break;
			case Node.ENTITY_NODE :
				// xmlData = xmlData.append(addIndent(depth) + "<!ENTITY " + n.getNodeName() + " " + ">\n");
				break;
			case Node.PROCESSING_INSTRUCTION_NODE :
				// xmlData = xmlData.append(addIndent(depth) + "Processing Instruction = " + n.getNodeName() + ", " + n.getNodeValue() + "\n");
				break;
			case Node.COMMENT_NODE :
				// xmlData = xmlData.append(addIndent(depth) + "<!-- " + n.getNodeValue() + " -->\n");
				break;
			case Node.DOCUMENT_NODE :
				if (node.hasChildNodes())
				{
					NodeList document_list = node.getChildNodes();
					location = "document";
					xmlData = xmlData.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
					for (int i = 0; i < document_list.getLength(); i++) processNode(location, document_list.item(i), depth + 1, root_key, key_name);
				}
				break;
			case Node.DOCUMENT_TYPE_NODE :
				// xmlData = xmlData.append(addIndent(depth) + "Document Type = " + n.getNodeName() + "\n");
				break;
			case Node.DOCUMENT_FRAGMENT_NODE :
				// xmlData = xmlData.append(addIndent(depth) + "Document Fragment = " + n.getNodeName() + "\n");
				break;
			case Node.NOTATION_NODE :
				// xmlData = xmlData.append(addIndent(depth) + "<!NOTATION " + n.getNodeName() + " " + ">\n");
				break;
			default :
				return;
		}
	}

	private String addIndent(int depth)
	{
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < depth - 1; i++) temp = temp.append("\t");
		return temp.toString();
	}
}
