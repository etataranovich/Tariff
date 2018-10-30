package by.tataranovich.tariff.reader;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import static by.tataranovich.tariff.reader.Tariff.DATE_FORMAT;

public class TariffsDOMBuilder {

    private Set<Tariff> tariffs;
    private DocumentBuilder docBuilder;

    public TariffsDOMBuilder() {
	this.tariffs = new HashSet<Tariff>();
	// создание DOM-анализатора
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	try {
	    docBuilder = factory.newDocumentBuilder();
	} catch (ParserConfigurationException e) {
	    System.err.println("Ошибка конфигурации парсера: " + e);
	}
    }

    public Set<Tariff> getTariffs() {
	return tariffs;
    }

    public void buildSetTariffs(String fileName) {
	Document doc = null;
	try {
	    // parsing XML-документа и создание древовидной структуры
	    doc = docBuilder.parse(fileName);
	    Element root = doc.getDocumentElement();
	    // получение списка дочерних элементов <tariff>
	    NodeList tariffsList = root.getElementsByTagName("tariff");
	    for (int i = 0; i < tariffsList.getLength(); i++) {
		Element tariffElement = (Element) tariffsList.item(i);
		Tariff tariff = buildTariff(tariffElement);
		tariffs.add(tariff);
	    }
	} catch (IOException e) {
	    System.err.println("File error or I/O error: " + e);
	} catch (SAXException e) {
	    System.err.println("Parsing failure: " + e);
	}
    }

    private Tariff buildTariff(Element tariffElement) {
	Tariff tariff = new Tariff();
	// заполнение объекта tariff
	tariff.setOperator(tariffElement.getAttribute("operator"));
	tariff.setName(getElementTextContent(tariffElement, "name"));
	Double payroll = Double.parseDouble(getElementTextContent(tariffElement, "payroll"));
	tariff.setPayroll(BigDecimal.valueOf(payroll));
	
	Date dateOfCreation = null;
	try {
	    dateOfCreation = DATE_FORMAT.parse(getElementTextContent(tariffElement, "dateOfCreation"));
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	tariff.setDateOfCreation(dateOfCreation);
	
	// tariff.setFaculty(cutleryElement.getAttribute("faculty")); // проверка на
	// null

	
	Tariff.CallPrice callPrice = tariff.getCallPrice();
	
	
	// заполнение объекта Callprice
	Element callPriceElement = (Element) tariffElement.getElementsByTagName("callPrice").item(0);
	
	Double inNetworkCallPrice = Double.parseDouble(getElementTextContent(callPriceElement, "inNetworkCallPrice"));
	callPrice.setInNetworkCallPrice(BigDecimal.valueOf(inNetworkCallPrice));
	Double outNetworkCallPrice = Double.parseDouble(getElementTextContent(callPriceElement, "outNetworkCallPrice"));
	callPrice.setOutNetworkCallPrice(BigDecimal.valueOf(outNetworkCallPrice));
	Double toStationaryCallPrice = Double.parseDouble(getElementTextContent(callPriceElement, "toStationaryCallPrice"));
	callPrice.setToStationaryCallPrice(BigDecimal.valueOf(toStationaryCallPrice));
	Double smsPrice = Double.parseDouble(getElementTextContent(tariffElement, "smsPrice"));
	callPrice.setSmsPrice(BigDecimal.valueOf(smsPrice));

	
	Tariff.Parameters parameters = tariff.getParameters();
	// заполнение объекта Parameters
	Element parametersElement = (Element) tariffElement.getElementsByTagName("parameters").item(0);
	
	
	Integer timeTarification = Integer.parseInt(getElementTextContent(parametersElement, "timeTarification"));
	parameters.setTimeTarification(timeTarification);
	parameters.setFavouriteNumber(getElementTextContentNullSafe(parametersElement, "favouriteNumber"));
	Double tariffToConnectPrice = Double.parseDouble(getElementTextContent(parametersElement, "tariffToConnectPrice"));
	parameters.setTariffToConnectPrice(BigDecimal.valueOf(tariffToConnectPrice));
	
	tariff.setId(tariffElement.getAttribute("id"));
	return tariff;
    }

    // получение текстового содержимого тега
    private static String getElementTextContent(Element element, String elementName) {
	NodeList nList = element.getElementsByTagName(elementName);
	Node node = nList.item(0);
	String text = node.getTextContent();
	return text;
    }
    
    private static String getElementTextContentNullSafe(Element element, String elementName) {
	NodeList nList = element.getElementsByTagName(elementName);
	Node node = nList.item(0);
	if (node != null) {
	    return node.getTextContent();
	}
	return null;
    }
}