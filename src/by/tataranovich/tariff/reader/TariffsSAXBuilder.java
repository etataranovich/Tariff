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
	// �������� SAX-�����������
	tariffHandler = new TariffHandler();
	try {
	    // �������� �������-�����������
	    reader = XMLReaderFactory.createXMLReader();
	    reader.setContentHandler(tariffHandler);
	} catch (SAXException e) {
	    System.err.print("������ SAX �������: " + e);
	}
    }

    public Set<Tariff> getTariffs() {
	return tariffs;
    }

    public void buildSetTariffs(String fileName) {
	try {
	    // ������ XML-���������
	    reader.parse(fileName);
	} catch (SAXException e) {
	    System.err.print("������ SAX �������: " + e);
	} catch (IOException e) {
	    System.err.print("������ I/� ������: " + e);
	}
	tariffs = tariffHandler.getTariffs();
    }
}
