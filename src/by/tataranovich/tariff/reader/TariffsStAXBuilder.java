package by.tataranovich.tariff.reader;

import static by.tataranovich.tariff.reader.Tariff.DATE_FORMAT;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class TariffsStAXBuilder {

    private HashSet<Tariff> tariffs = new HashSet<>();
    private XMLInputFactory inputFactory;

    public TariffsStAXBuilder() {
	inputFactory = XMLInputFactory.newInstance();
    }

    public Set<Tariff> getTariffs() {
	return tariffs;
    }

    public void buildSetTariffs(String fileName) {
	FileInputStream inputStream = null;
	XMLStreamReader reader = null;
	String name;
	try {
	    inputStream = new FileInputStream(new File(fileName));
	    reader = inputFactory.createXMLStreamReader(inputStream);
	    // StAX parsing
	    while (reader.hasNext()) {
		int type = reader.next();
		if (type == XMLStreamConstants.START_ELEMENT) {
		    name = reader.getLocalName();
		    if (TariffEnum.valueOf(name.toUpperCase()) == TariffEnum.TARIFF) {
			Tariff tariff = buildTariff(reader);
			tariffs.add(tariff);
		    }
		}
	    }
	} catch (XMLStreamException ex) {
	    System.err.println("StAX parsing error! " + ex.getMessage());
	} catch (FileNotFoundException ex) {
	    System.err.println("File " + fileName + " not found! " + ex);
	} finally {
	    try {
		if (inputStream != null) {
		    inputStream.close();
		}
	    } catch (IOException e) {
		System.err.println("Impossible close file " + fileName + " : " + e);
	    }
	}
    }

    private Tariff buildTariff(XMLStreamReader reader) throws XMLStreamException {
	Tariff tariff = new Tariff();
	tariff.setId(reader.getAttributeValue(null, TariffEnum.ID.getValue()));
	tariff.setOperator(reader.getAttributeValue(null, TariffEnum.OPERATOR.getValue())); // проверить на null
	String name;
	while (reader.hasNext()) {
	    int type = reader.next();
	    switch (type) {
	    case XMLStreamConstants.START_ELEMENT:
		name = reader.getLocalName();
		switch (TariffEnum.valueOf(name.toUpperCase())) {
		case NAME:
		    tariff.setName(getXMLText(reader));
		    break;
		case PAYROLL:
		    name = getXMLText(reader);
		    tariff.setPayroll(BigDecimal.valueOf(Double.parseDouble(name)));
		    break;
		case DATEOFCREATION:
		    try {
			tariff.setDateOfCreation(DATE_FORMAT.parse(getXMLText(reader)));
		    } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		    break;
		case CALLPRICE:
		    tariff.setCallprice(getXMLCallPrice(reader));
		    break;
		case PARAMETERS:
		    tariff.setParameter(getXMLParameters(reader));
		    break;
		}
		break;
	    case XMLStreamConstants.END_ELEMENT:
		name = reader.getLocalName();
		if (TariffEnum.valueOf(name.toUpperCase()) == TariffEnum.TARIFF) {
		    return tariff;
		}
		break;
	    }

	}
	throw new XMLStreamException("Unknown element in tag Tariff");
    }

    private Tariff.CallPrice getXMLCallPrice(XMLStreamReader reader) throws XMLStreamException {
	Tariff.CallPrice callPrice = new Tariff.CallPrice();
	int type;
	String name;
	while (reader.hasNext()) {
	    type = reader.next();
	    switch (type) {
	    case XMLStreamConstants.START_ELEMENT:
		name = reader.getLocalName();
		switch (TariffEnum.valueOf(name.toUpperCase())) {
		case INNETWORKCALLPRICE:
		    callPrice.setInNetworkCallPrice(BigDecimal.valueOf(Double.parseDouble(getXMLText(reader))));
		    break;
		case OUTNETWORKCALLPRICE:
		    callPrice.setOutNetworkCallPrice(BigDecimal.valueOf(Double.parseDouble(getXMLText(reader))));
		    break;
		case TOSTATIONARYCALLPRICE:
		    callPrice.setOutNetworkCallPrice(BigDecimal.valueOf(Double.parseDouble(getXMLText(reader))));
		    break;
		}
		break;
	    case XMLStreamConstants.END_ELEMENT:
		name = reader.getLocalName();
		if (TariffEnum.valueOf(name.toUpperCase()) == TariffEnum.CALLPRICE) {
		    return callPrice;
		}
		break;
	    }
	}
	throw new XMLStreamException("Unknown element in tag CallPrice");
    }

    private Tariff.Parameters getXMLParameters(XMLStreamReader reader) throws XMLStreamException {
	Tariff.Parameters parameters = new Tariff.Parameters();
	int type;
	String name;
	while (reader.hasNext()) {
	    type = reader.next();
	    switch (type) {
	    case XMLStreamConstants.START_ELEMENT:
		name = reader.getLocalName();
		switch (TariffEnum.valueOf(name.toUpperCase())) {
		case FAVOURITENUMBER:
		    // parameters.setF(reader);
		    break;
		case TIMETARIFICATION:
		    name = getXMLText(reader);
		    parameters.setTimeTarification(Integer.parseInt(name));
		    break;
		case TARIFFTOCONNECTPRICE:
		    parameters.setTariffToConnectPrice(BigDecimal.valueOf(Double.parseDouble(getXMLText(reader))));
		    break;
		}
		break;
	    case XMLStreamConstants.END_ELEMENT:
		name = reader.getLocalName();
		if (TariffEnum.valueOf(name.toUpperCase()) == TariffEnum.PARAMETERS) {
		    return parameters;
		}
		break;
	    }
	}
	throw new XMLStreamException("Unknown element in tag CallPrice");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
	String text = null;
	if (reader.hasNext()) {
	    reader.next();
	    text = reader.getText();
	}
	return text;
    }
}
