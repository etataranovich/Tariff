package by.tataranovich.tariff.reader.saxsimple;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;


public class Main {

    public static void main(String[] args) throws ParserConfigurationException {

   
	try {
	    // создание SAX-анализатора
//	    XMLReader reader = XMLReaderFactory.createXMLReader();
//	    SimpleCatalogueHandler handler = new SimpleCatalogueHandler();
	    SAXParserFactory factory=SAXParserFactory.newInstance();
	    SAXParser parser=factory.newSAXParser();
	    XMLReader xmlReader=parser.getXMLReader();
	    SimpleTariffsHandler handler = new SimpleTariffsHandler();
	    xmlReader.setContentHandler(handler);
	    xmlReader.parse("TariffHandler/tariffs.xml");
	} catch (SAXException e) {
	    System.err.print("ошибка SAX парсера " + e);
	} catch (IOException e) {
	    System.err.print("ошибка I/О потока " + e);
	}
    }


}





