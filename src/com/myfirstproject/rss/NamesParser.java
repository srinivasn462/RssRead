package com.myfirstproject.rss;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NamesParser {

	Item objItem;
	List<Item> listArray;

	public List<Item> getData(String url) {

		try {

			listArray = new ArrayList<Item>();

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new URL(url).openStream());

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("item");

			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					objItem = new Item();

					objItem.setTitle(getTagValue("title", eElement));
					objItem.setDesc(getTagValue("description", eElement));
					objItem.setLink(getTagValue("link", eElement));

					listArray.add(objItem);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listArray;
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();

	}
}
