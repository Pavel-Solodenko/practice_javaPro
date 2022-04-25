import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TrainsWorkerXml {
	public static Trains getTrainsFromXML(File file) {
		Trains trains = new Trains();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);
			Element root = document.getDocumentElement();
			NodeList trains1 = root.getChildNodes();
			for (int i = 0; i < trains1.getLength(); ++i) {
				Node node = trains1.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					Train train= getTrainFromNode(element);
					if (node.hasAttributes()) {
						Node atr_node = node.getAttributes().item(0);
						String id = atr_node.getNodeValue();
						train.setId(Integer.parseInt(id));
					}
					if (train != null) {
						trains.addTrain(train);
					}
				}
			}
		}
		catch (Exception e) {
			return null;
		}
		return trains;
	}
	
	private static Train getTrainFromNode(Element trainElement) {
		if (!trainElement.getTagName().equals("train")) {
			return null;
		}
		String from = trainElement.getElementsByTagName("from").item(0).getTextContent();
		String to = trainElement.getElementsByTagName("to").item(0).getTextContent();
		String departure_text = trainElement.getElementsByTagName("departure").item(0).getTextContent();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		String date_text = trainElement.getElementsByTagName("date").item(0).getTextContent();
		Date date = new Date();
		Date departure = new Date();
		try {
			date = sdf.parse(date_text);
			sdf.applyLocalizedPattern("kk:mm");
			departure = sdf.parse(departure_text);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return new Train(from,to,date,departure);
	}

	public static void saveToXML(Trains trains,String path) {
		DocumentBuilderFactory documentBuiderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder docBuilder = documentBuiderFactory.newDocumentBuilder();
			Document document = docBuilder.newDocument();
			Element root = document.createElement("trains");
			document.appendChild(root);
			for (Train a : trains.getTrains()) {
				Element trainElement = elementFromTrain(a,document);
				root.appendChild(trainElement);
			}
			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer transformer = transFact.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult stRes = new StreamResult(path);
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(source, stRes);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public static Element elementFromTrain(Train train,Document document) {
		Element trainElement = document.createElement("train");
		trainElement.setAttribute("id",String.valueOf(train.getId()));
		Element from = document.createElement("from");
		from.setTextContent(train.getFrom());
		Element to = document.createElement("to");
		to.setTextContent(train.getTo());
		Element date = document.createElement("date");
		date.setTextContent(train.getDate());
		Element departure = document.createElement("departure");
		departure.setTextContent(train.getDeparture());
		trainElement.appendChild(from);
		trainElement.appendChild(to);
		trainElement.appendChild(date);
		trainElement.appendChild(departure);
		return trainElement;
	}
}
