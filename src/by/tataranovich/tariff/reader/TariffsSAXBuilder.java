package by.tataranovich.tariff.reader;

import java.io.IOException;
import java.util.Set;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class TariffsSAXBuilder {
    private Set<Tariff> tariffs;
    private TariffHandler tariffHandler;
    private XMLReader reader;

    public TariffsSAXBuilder() {
	// создание SAX-анализатора
	tariffHandler = new TariffHandler();
	try {
	    // создание объекта-обработчика
	    reader = XMLReaderFactory.createXMLReader();
	    reader.setContentHandler(tariffHandler);
	} catch (SAXException e) {
	    System.err.print("ошибка SAX парсера: " + e);
	}
    }

    public Set<Tariff> getTariffs() {
	return tariffs;
    }

    public void buildSetTariffs(String fileName) {
	try {
	    // разбор XML-документа
	    reader.parse(fileName);
	} catch (SAXException e) {
	    System.err.print("ошибка SAX парсера: " + e);
	} catch (IOException e) {
	    System.err.print("ошибка I/О потока: " + e);
	}
	tariffs = tariffHandler.getTariffs();
    }
}
