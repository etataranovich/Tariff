package by.tataranovich.tariff.reader;

import static by.tataranovich.tariff.reader.Tariff.DATE_FORMAT;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class TariffHandler extends DefaultHandler {
    private Set<Tariff> tariffs;
    private Tariff current = null;
    private TariffEnum currentEnum = null;
    private EnumSet<TariffEnum> withText;

    public TariffHandler() {
	tariffs = new HashSet<Tariff>();
	withText = EnumSet.range(TariffEnum.NAME, TariffEnum.DATEOFCREATION);
    }

    public Set<Tariff> getTariffs() {
	return tariffs;
    }

    public void startElement(String uri, String localName, String qName, Attributes attrs) {
	if ("tariff".equals(localName)) {
	    current = new Tariff();
	    current.setId(attrs.getValue(0));
	    if (attrs.getLength() == 2) {
		current.setOperator(attrs.getValue(1));
	    }
	} else {
	    TariffEnum temp = TariffEnum.valueOf(localName.toUpperCase());
	    if (withText.contains(temp)) {
		currentEnum = temp;
	    }
	}
    }

    public void endElement(String uri, String localName, String qName) {
	if ("tariff".equals(localName)) {
	    tariffs.add(current);
	}
    }

    public void characters(char[] ch, int start, int length) {
	String s = new String(ch, start, length).trim();
	if (currentEnum != null) {
	    switch (currentEnum) {
	    case NAME:
		current.setName(s);
		break;
	    case PAYROLL:
		current.setPayroll(BigDecimal.valueOf(Double.parseDouble(s)));
		break;
	    case DATEOFCREATION:
		try {
		    current.setDateOfCreation(DATE_FORMAT.parse(s));
		} catch (ParseException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		break;
	    case INNETWORKCALLPRICE:
		current.getCallPrice().setInNetworkCallPrice(BigDecimal.valueOf(Double.parseDouble(s)));
		break;
	    case OUTNETWORKCALLPRICE:
		current.getCallPrice().setOutNetworkCallPrice(BigDecimal.valueOf(Double.parseDouble(s)));
		break;
	    case TOSTATIONARYCALLPRICE:
		current.getCallPrice().setToStationaryCallPrice(BigDecimal.valueOf(Double.parseDouble(s)));
		break;
	    case FAVOURITENUMBER:
		current.getParameters().setFavouriteNumber(s);
		break;
	    case TIMETARIFICATION:
		current.getParameters().setTimeTarification(Integer.parseInt(s));
		break;
	    case TARIFFTOCONNECTPRICE:
		current.getParameters().setTariffToConnectPrice(BigDecimal.valueOf(Double.parseDouble(s)));
		break;
	    case PARAMETERS:
	    case CALLPRICE:
	    default:
		throw new EnumConstantNotPresentException(currentEnum.getDeclaringClass(), currentEnum.name());
	    }
	}
	currentEnum = null;
    }
}


