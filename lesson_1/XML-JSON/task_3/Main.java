import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {
	public static void main(String[] args) {
		String url ="https://api.getgeoapi.com/v2/currency/list?api_key=ce801679b79df8f2cae56179c759b0cb7a14d411&format=xml";
//		String response = sentRequest(url);
		DocumentBuilderFactory documentBuiderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder = documentBuiderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(url);
			Element root = document.getDocumentElement();
			System.out.println("Root element: " + root.getNodeName());
			NodeList nodes = root.getChildNodes();
			for (int i = 0;i < nodes.getLength();++i) {
				Node node = nodes.item(i);
				if (node.getChildNodes().getLength() > 1) {
					String name_1 = node.getNodeName();
					System.out.println("Element name: "+name_1+"\tValue: ");
					NodeList nodes_1 = node.getChildNodes();
					for (int k = 0;k < nodes_1.getLength();++k) {
						Node node_1 = nodes_1.item(k);
						if (node_1.getNodeType() == Node.ELEMENT_NODE) {
							String node_name = node_1.getNodeName();
							String node_value = node_1.getChildNodes().item(0).getNodeValue();
							System.out.println("Element name: "+node_name+"\tValue: "+node_value);
						}
					}
				} 
				else {
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) node;
						String name = element.getNodeName();
						String value = element.getChildNodes().item(0).getNodeValue();
						System.out.println("\r\nElement name: "+name+"\tValue: "+value);
					}
				}
				
			}
		} catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
		}
	}
	public static String sentRequest(String urlStr) {
		String response = "";
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			InputStreamReader isr = new InputStreamReader(connection.getInputStream());
			char[] buffer = new char[1024];
			while (isr.ready()) {
				System.out.println(isr.read(buffer));
				response = response + new String(buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
}
